/*
 *  zafCotVen.java    
 *  Created on 08 de Junio de 2006   st_bie
 */  
package Ventas.ZafVen01;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco2.ZafTblCelEdiTxtVco2;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import java.util.HashMap;
import java.util.Map; 
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
  

 /*
  * @author  jayapata
  *
  */
public class ZafVen01_His extends javax.swing.JInternalFrame{
    mitoolbar objTooBar;
    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;  // Para trabajar con la informacion de tipo de documento
 
        
     String DIRECCION_REPORTE="//zafiro//reportes_impresos//RptCotVta.jrxml";
     String DIRECCION_REPORTE2="//zafiro//reportes_impresos//RptFacVta.jrxml";
     String DIRECCION_REPORTE_OC="//zafiro//reportes_impresos//RptOrdCom.jrxml";
    
    
    String STR_ESTREG="";
    javax.swing.JTextArea txtsql=new javax.swing.JTextArea();
    javax.swing.JTextField txtCodForPag = new javax.swing.JTextField();
    javax.swing.JTextField txtCodForPag2 = new javax.swing.JTextField("");
    private ZafAutPrg objAutPrg;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;   //Editor: JButton en celda.
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtCom;        //Editor: JTextField en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut objTblCelEdiBut;        //Editor: JButton en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon objTblCelEdiTxtCon;  //Editor: JTextField de consulta en celda.
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private boolean blnChangeData=false;
    private String strBeforeValue,strAfterValue;
    public boolean BLN_ESTADO=true;
    private String strCodPrv, strDesLarPrv;
    private String strCodCom, strDesLarCom;
    Connection conCot;  //Coneccion a la base donde se encuentra la cotizacion
    Statement stmCotDet2;    //Statement para la cotizacion
    ResultSet rstCotCab2=null;    //Resultset que tendra los datos de la cabecera
    ResultSet rstCotDet2;    //Resultset que tendra los datos del Detalle
    boolean blnLimpiar = true; //Limpiar o no en el metodo clnTextos
    ZafUtil objUti;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    Connection conCab=null;  //Coneccion a la base donde se encuentra la cotizacion
    Statement stmCab=null;   //Statement para la cotizacion
    ResultSet rstCab;//Resultset que tendra los datos de la cabecera
    String sSQL,strSQL, strFiltro;//EL filtro de la Consulta actual
    double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    double dblTotalCot, dblIvaCot;
    int intNumDec= 2 ; //Numero de decimales a presentar
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    private boolean blnHayCam_glo = false; //Detecta ke se hizo cambios en el documento
    private boolean blnHayCam_glo_tbl = false; //Detecta ke se hizo cambios en el documento
    private double bldivaEmp=0;
    LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    java.sql.Connection CONN_GLO, conRemGlo=null;
    java.util.Vector vecForPag, vecForPag2; //Vector que contiene el codigo del pago
    String strAux,strstReg_glo="";
    
    double dblSubtotalCot;
    Librerias.ZafObtConCen.ZafObtConCen  objObtConCen;
    
    Statement stmCot;   //Statement para la cotizacion
    ZafVenCon objVenCon; //*****************
    ZafVenCon objVenCon2; //*****************
    ZafVenCon objVenConCli; //*****************
    ZafVenCon objVenConVen; //*****************
    ZafVenCon objVenConDirCli; //*****************
    
    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;   //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco2 objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.
    
    final int INT_TBL_DIACRE   = 1 ;            //Codigo del item (codigo interno del sistema)
    
    final int INT_TBL_LINEA    = 0 ;
    final int INT_TBL_ITMALT   = 1 ;
    final int INT_TBL_BUTITM   = 2 ;
    final int INT_TBL_DESITM   = 3 ;
    final int INT_TBL_UNIDAD   = 4 ;
    final int INT_TBL_CODBOD   = 5 ;
    final int INT_TBL_BUTBOD   = 6 ;
    final int INT_TBL_CANMOV   = 7 ;            //Cantidad del movimiento (venta o compra)
    final int INT_TBL_PREUNI   = 8 ;           //Precio de Venta
    final int INT_TBL_PORDES   = 9 ;           //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 10;           //Boolean Iva
    final int INT_TBL_TOTAL    = 11;           //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 12;
    final int INT_TBL_ESTADO   = 13;
    final int INT_TBL_IVATXT   = 14;
    final int INT_TBL_PRE_COS   = 15;            //Columna que contiene  precio de compra
    final int INT_TBL_DESPRECOM   = 16;            //Columna que contiene  el descuento de precio de compra  nd_porDesPreCom
    final int INT_TBL_COD_PRO    = 17;            //Columna que contiene el codigo del proveedor
    final int INT_TBL_NOM_PRO    = 18;            //Columna que contiene el Nombre del proveedor
    final int INT_TBL_BUT_PRO    = 19;            //Columna que contiene para busqueda del proveedor
    final int INT_TBL_BLNPRE   = 20;            //Columna que contiene verdadero si es problema de precio
    final int INT_TBL_ITMALT2   = 21;            //Columna que contiene verdadero si es problema de precio
    final int INT_TBL_ITMSER   = 22;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_ITMTER   = 23;            //Columna que contiene "S" (el item es terminal L ) "N"  no corresponde a la terminal L
    final int INT_TBL_MARUTI   = 24;


    final int INT_TBL_CANITM   = 25;
    final int INT_TBL_CANPRE   = 26;
    final int INT_TBL_CANPOR   = 27;
    final int INT_TBL_CANDITM   = 28;
    final int INT_TBL_CANCAN   = 29;
    final int INT_TBL_CANTOT   = 30;




     /*
      *Valiables que se almacena los datos cuando quieran ver la cotizacion desde la ventana autorizacion
      */
    int intCodEmp =0;
    int intCodLoc =0;
    int intCodCot=0;
    int intValBus = 0;
    int NumLineas=0;
    int intCodBodPre=0;
    
    int intNumDiaVal=0;   // Variable que define el numero de dias validos de la cotizacion...
    int intTipMod = 0; // Varible que define si es una modificacion
    String dateFecCot; //Variable que contiene la fecha de cotizacion.
    
    String strFecSis, strFecSisBase;
      
    int  INTCODREGCEN=0;
    int  INTVERCONCEN=0;
      
    final String VERSION = " Ver. 20.0 ";
    final int intarreglonum[] = new int[10];
    final int intarreglodia[] = new int[10];
    String strarreglosop[] = new String[10];
      
    int INTGLONUMOC=0;
    
    final int intCanArr[]= new int[1];
    String strTipPer_glo = "";
    String strTipPer_emp = "";
    int intCodTipPerEmp;
    String strCodTipPerCli;
    
    int intTipForPag=0;
    double dblPes=0;
    
    
    int intTipForPag2=0;
    double dblPes2=0;
    double dblDiaGraCli = 0; 
    
    
    int intCodMotDoc=0;  //  se almacena el codigo del motivo del codumento para las retenciones.
    
    double Glo_dlbMaxDes=0;
    double Glo_dlbMarUti=0;
    String GLO_strnomEmp="";
    String GLO_strdirEmp="";
    String GLO_strArreItm="";
    int Glo_intCodSec=0;
    
    javax.swing.JTextArea txtcli_identificacion = new javax.swing.JTextArea();
    javax.swing.JTextArea txtcli_telefono = new javax.swing.JTextArea();
    javax.swing.JTextArea txtcli_ciudad = new javax.swing.JTextArea();
    
    
    int  intNumMaxVenCon = 0;
    double dblMonMaxVenCon = 0;
    
    String strNomFuncValPre="isPreUniVtaValForPagAut"; 
    
    // String strNomFuncValPre="isPreVtaMenorPreUni";
    
    int INTPARSISCONF=0;
    int intCodHis=0;
     
    int intCodMenuOri=0;
    java.util.Vector vecLoc, vecEmp;
   
   
  
    int intControlValTbl=0;
    
    /** Creates new form zafVen01 */
    public ZafVen01_His(Librerias.ZafParSis.ZafParSis obj) {
        try{
            
            System.out.println("ZafVen01_His 1");
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;
            
            objUti = new ZafUtil();
            this.setTitle(objZafParSis.getNombreMenu()+VERSION);
            
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;
            initComponents();
            abrirCon();   /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */
            
            
            intCodEmp = objZafParSis.getCodigoEmpresa();
            intCodLoc = objZafParSis.getCodigoLocal();
            
            objAutPrg=new ZafAutPrg(this);
            
            
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
             
             tblDat.addKeyListener(new java.awt.event.KeyAdapter(){
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    
                    if(java.awt.event.KeyEvent.VK_F8==evt.getKeyCode()) {
                        int a = tblDat.getSelectedRow();
                        if(tblDat.getValueAt(a,INT_TBL_ITMALT) != null ) {
                            String ItmALt  =   tblDat.getValueAt(a,INT_TBL_CODITM).toString();
                            int CodCli;
                            if(txtCliCod.getText().equalsIgnoreCase(""))
                                CodCli=0;
                            else
                                CodCli =  Integer.parseInt(txtCliCod.getText());
                            
                            LlamrVentana(ItmALt,CodCli);
                        }
                    }
                }});
                     
                 
                 
                strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                
            strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());
                
                
                txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
                txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
                txtFecDoc.setText(strFecSis);
                panCotGenNorNor.add(txtFecDoc);
                txtFecDoc.setBounds(116, 30, 92, 20);
                
              
                
                
                //Nombres de los tabs
                tabCotCom.setTitleAt(0,"General");
                tabCotCom.setTitleAt(1,"Forma de Pago");
                objTooBar = new mitoolbar(this);
                
                vecForPag = new java.util.Vector();
                vecForPag2 = new java.util.Vector();
                
               
                objTooBar.setVisibleModificar(false);

                this.getContentPane().add(objTooBar,"South");
                pack();
                
                
              
               
                   if(System.getProperty("os.name").equals("Linux")){
                            DIRECCION_REPORTE="//zafiro//reportes_impresos//RptCotVta.jrxml";
                            DIRECCION_REPORTE2="//zafiro//reportes_impresos//RptFacVta.jrxml";
                            DIRECCION_REPORTE_OC="//zafiro//reportes_impresos//RptOrdCom.jrxml";
                    }else{
                            DIRECCION_REPORTE="C://zafiro//reportes_impresos//RptCotVta.jrxml";
                            DIRECCION_REPORTE2="C://zafiro//reportes_impresos//RptFacVta.jrxml";
                            DIRECCION_REPORTE_OC="C://zafiro//reportes_impresos//RptOrdCom.jrxml";
                    }
                
                
                 
                
        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
                spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
         
               
         
                objUti.verDecimalesEnTabla(tblCotForPag,4, intNumDec);
                addListenerCambio();
                
               
                 
                Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblCotForPag,0);
                tblCotForPag.getModel().addTableModelListener(new LisCambioTbl());
                
               
                 
                cargarTipEmp();  
                
               
                 
                cargarTipParSis();
                
                 
                  
                // cargarCanMonVtaUsr();
                FormaRetencion(CONN_GLO);
                
                 
                  
               // if(objZafParSis.getCodigoEmpresa() != 1 )

                    txtCodLoc.setVisible(false);
                
                    
                    
                intCodMenuOri=objZafParSis.getCodigoMenu();
                objZafParSis.setCodigoMenu(4);
               
                vecLoc =  new  java.util.Vector();
                vecEmp =  new  java.util.Vector();
                
                
                if(objZafParSis.getCodigoEmpresa()!=objZafParSis.getCodigoEmpresaGrupo()){
                    String sql = "select co_loc , tx_nom from tbm_loc where co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal();
                    
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos(), sql, local, vecLoc);
                   
                    local.setSelectedIndex(0);
                    
                    
                    sql = "select co_emp , tx_nom from tbm_emp where co_emp="+objZafParSis.getCodigoEmpresa();
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos(), sql, empresa, vecEmp);
                    empresa.setSelectedIndex(0);
                    
                    empresa.setVisible(false);
                    lblEmp.setVisible(false);
                }else{
                    String sql = "select co_emp , tx_nom from tbm_emp where co_emp<>"+objZafParSis.getCodigoEmpresaGrupo();
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos(), sql, empresa, vecEmp);
                    empresa.setSelectedIndex(0);
                    
                }
                
                
                
                objUti.desactivarCom(this);
        }catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    
    
      
      
      
     
     
    
    public boolean abrirConRem(){
        boolean blnres=false;
        try{
            int intIndEmp=INTCODREGCEN;
            if(intIndEmp != 0){
                conRemGlo=DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
                conRemGlo.setAutoCommit(false);
            }
            blnres=true;
        }
        catch (java.sql.SQLException e) {
            mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            INTVERCONCEN=1;
            return false;
        }
        return blnres;
    }
    
    
    
    
      
    
    //************************//*************************************
      
    public ZafVen01_His(Librerias.ZafParSis.ZafParSis objZafParSis, Integer CodEmp, Integer CodLoc, Integer CodCot, Integer CodHis ) {
        this(objZafParSis);
        System.out.println("ZafVen01_His 2");
        intCodEmp = CodEmp.intValue();
        intCodLoc = CodLoc.intValue();
        intCodCot= CodCot.intValue();
        intCodHis= CodHis.intValue();
        intValBus = 2;
        
        this.setLocation(5, 20);
    }
      
    //************************//*************************************


     public ZafVen01_His(Librerias.ZafParSis.ZafParSis objZafParSis, Integer CodEmp, Integer CodLoc, Integer CodCot) {
        this(objZafParSis);
        System.out.println("ZafVen01_His 3");
        intCodEmp = CodEmp.intValue();
        intCodLoc = CodLoc.intValue();
        intCodCot= CodCot.intValue();
        intValBus = 1;

        this.setLocation(5, 20);
    }





    
    private class RenderDecimales extends javax.swing.JLabel implements javax.swing.table.TableCellRenderer {
        int intNumDecimales = 0;
        public RenderDecimales(int intNumDecimales) {
            this.intNumDecimales=intNumDecimales ;
            setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            setOpaque(true);
            setBackground(new java.awt.Color(255, 255, 255));
        }
         
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            try{
               
                double dblSubPro = Double.parseDouble( (value == null)?"0":""+value);
                Librerias.ZafUtil.ZafUtil objutil = new Librerias.ZafUtil.ZafUtil();
                if(isSelected){
                    setBorder(new javax.swing.border.LineBorder(javax.swing.UIManager.getDefaults().getColor("Button.focus"), 1));
                }else{
                    setBorder(null);
                }
                
                this.setText("" + objutil.redondeo(dblSubPro, intNumDecimales));
                this.setFont(new java.awt.Font("SansSerif", 0, 11));
                this.setOpaque(true);
                setBackground(new java.awt.Color(201,223,245));
                setForeground(new java.awt.Color(0, 0, 0));
                
                
                if(tblDat.getValueAt(row, INT_TBL_BLNPRE)!=null){
                    if((tblDat.getValueAt(row, INT_TBL_BLNPRE).toString().equals("true"))){
                        this.setOpaque(true);
                        setBackground(new java.awt.Color(0, 0, 0));
                        setForeground(new java.awt.Color(255,255,255));
                        setFont(new java.awt.Font("MS Sans Serif", 1, 18));
                    }
                }
            }catch(Exception e){ objUti.mostrarMsgErr_F1(jfrThis, e); 
              tblDat.setValueAt("0", row, column);        }
            return this;
        }
    }
    
    
    
    
    
      
    
    
     private class colorTexto extends javax.swing.JLabel implements javax.swing.table.TableCellRenderer {
        int intColTblDat=0;
         public colorTexto(int ColTblDat) {
             intColTblDat=ColTblDat;
        }
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            try{


             
                    String strText =  (value == null)?" ":value.toString();
                    this.setText( strText );
                    this.setFont(new java.awt.Font("SansSerif", 0, 11));
                    this.setOpaque(true);
                     setBackground(new java.awt.Color(201,223,245));
                    if(tblDat.getValueAt(row, intColTblDat).toString().equals("*")){

                     //if(!(tblDat.getValueAt(row, intColTblDat).toString().equals(((tblDatHis.getValueAt(row, intColTblDat)==null)?"":tblDatHis.getValueAt(row, intColTblDat).toString())))){

                         //   if(intControlValTbl==0){
                    // if(comparaReg(row, intColTblDat, strText)){
                            this.setOpaque(true);
                            setForeground(new java.awt.Color(249, 67, 6));
                            setFont(new java.awt.Font("MS Sans Serif", 1, 14));
                    }else{
                            this.setOpaque(true);
                            setForeground(new java.awt.Color(0, 0, 0));
                    }
              //}
            }catch(Exception e){ objUti.mostrarMsgErr_F1(jfrThis, e); 
              tblDat.setValueAt("0", row, column);        }
            return this;
        }
           
          
          
            
           
   }
    
         
         
      
       
     
     
     
     
     
    
    public boolean FormaRetencion(java.sql.Connection conTmp){
        boolean blnRes=false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try {
            if (conTmp!=null) {
                stmTmp = conTmp.createStatement();
                String sql = "SELECT co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND tx_tipmot in ('S') ";
                rst=stmTmp.executeQuery(sql);
                while(rst.next()){
                    intCodMotDoc = rst.getInt(1);
                    blnRes=true;
                }
                
                rst.close();
                stmTmp.close();
                rst=null;
                stmTmp=null;
            }
        }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        return blnRes;
    }
    
    
    
    
    
    
    public void cargarTipEmp(){
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        
        try{  
            if (CONN_GLO!=null){
                stmTipEmp=CONN_GLO.createStatement();
                
                sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod FROM  tbm_emp as a " +
                " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
                " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+objZafParSis.getCodigoLocal()+" and bod.st_reg='P')  " +
                "where a.co_emp="+objZafParSis.getCodigoEmpresa();
                
                rstEmp = stmTipEmp.executeQuery(sSql);
                if(rstEmp.next()){
                    strTipPer_emp = rstEmp.getString("tx_descor");
                    bldivaEmp   =  rstEmp.getDouble("porIva");
                    intCodBodPre = rstEmp.getInt("co_bod");
                    intCodTipPerEmp = rstEmp.getInt("co_tipper");
                }
                
                rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
            }
        }catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
    
    
    
    
       
     
      
    public void cargarTipParSis(){
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        
        try{
            if (CONN_GLO!=null){
                stmTipEmp=CONN_GLO.createStatement();
                
                sSql="select round(nd_par1) from tbm_parsis as a " +
                " inner join tbr_paremp as b on (b.co_par=a.co_par) " +
                " where a.ne_mod=1 and b.co_emp="+objZafParSis.getCodigoEmpresa();
                
                rstEmp = stmTipEmp.executeQuery(sSql);
                if(rstEmp.next()){
                    INTPARSISCONF = rstEmp.getInt(1);
                }
                  
                rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
            }
        }catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
   
      
   
    private int getBodPrede(){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        int intBodPre = 0;
        String sSQLBod;
        try{
            if (CONN_GLO!=null){
                stmInv=CONN_GLO.createStatement();
                
                sSQLBod=  " select co_bod   " +
                " from tbr_bodloc " +
                " where           " +
                " co_emp =        " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc =        " + objZafParSis.getCodigoLocal()   + " and " +
                " st_reg ='P'";
                
                rstInv=stmInv.executeQuery(sSQLBod);
                if (rstInv.next())
                    intBodPre = rstInv.getInt("co_bod");
                
                rstInv.close();
                stmInv.close();
                rstInv = null;
                stmInv = null;
            }
        }
        catch(java.sql.SQLException Evt)  {  return -1;   }
        catch(Exception Evt) {  return -1;  }
        return intBodPre;
    }
      
    
      
    public void abrirCon(){
        try{
            CONN_GLO=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    
    
    public void CerrarCon(){
        try{
            CONN_GLO.close();
            CONN_GLO=null;
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    
    
    public void ObtenerNumLineas() {
        String sql = "select ne_numlin from tbm_cabtipdoc where co_tipdoc=1 and co_emp="+ objZafParSis.getCodigoEmpresa() +" and co_loc="+ objZafParSis.getCodigoLocal();
        try{
            if (CONN_GLO!=null){
                java.sql.Statement stm = CONN_GLO.createStatement();
                java.sql.ResultSet rst = stm.executeQuery(sql);
                
                if(rst.next())
                    NumLineas=rst.getInt(1);
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;
            }
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    
    public void ConfiguraVenCon(){
        configurarVenConProducto();
        configurarVenConProveedor();  
        configurarVenConClientes();  
        configurarVenConVendedor();
        configurarVenConDirCli();
    }
    
    
    private boolean configurarVenConVendedor() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
            " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
            " where b.co_emp="+objZafParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";
            
            objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
     
    
    
    private boolean configurarVenConClientes() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_tipper");
            arlCam.add("a.nd_maxdes");
            arlCam.add("a.nd_maruti");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.co_ven");
            arlCam.add("a.vendedor");
            arlCam.add("a.co_tipper");
            arlCam.add("a.ne_tipforpag");
            arlCam.add("a.nd_pes");
            arlCam.add("a.ne_diagra");
            arlCam.add("a.co_forpag");
            arlCam.add("a.tx_des");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("TIPO");
            arlAli.add("Desc");
            arlAli.add("Utili");
            arlAli.add("Ciudad");
            arlAli.add("co_vende");
            arlAli.add("Vendedor");
            arlAli.add("CoDTipPer");
            arlAli.add("TipoForPag");
            arlAli.add("Peso");
            arlAli.add("D.Gracias");
            arlAli.add("CodForPag");
            arlAli.add("Descri");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");           
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("40");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("40");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            
            strSQL+="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper," +
            "  ne_tipforpag,  nd_pes , ne_diagra , co_forpag , tx_des  " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
            " , b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag , b.tx_des  FROM tbr_cliloc AS a1 " +
            " INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "+
            " WHERE a1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc ="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'  and a.st_cli='S' order by a.tx_nom  " +
            ") AS a";
            
            
            int intColOcu[]=new int[9];
            intColOcu[0]=6;
            intColOcu[1]=7;
            intColOcu[2]=8;
            intColOcu[3]=9;
            intColOcu[4]=10;
            intColOcu[5]=11;
            intColOcu[6]=12;
            intColOcu[7]=16;
            intColOcu[8]=17;
             
            
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    private boolean configurarVenConDirCli() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel1");
            arlCam.add("a.tx_tel2");
            arlCam.add("a.tx_tel3");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Dirección");
            arlAli.add("Telefóno1");
            arlAli.add("Telefóno2");
            arlAli.add("Telefóno3");   
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("45");
            arlAncCol.add("300");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL="SELECT a.co_cli, a.tx_dir, a.tx_tel1, a.tx_tel2, a.tx_tel3  FROM  tbm_dircli as a WHERE  a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_cli=0 ";
            
            objVenConDirCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
       
    
    private boolean configurarVenConProveedor() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Nom.Prv.");
            arlAli.add("Dirección");
            arlAli.add("Código");
            arlAli.add("RUC/CI");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("190");
            arlAncCol.add("220");
            arlAncCol.add("50");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select a1.tx_nom,a1.tx_dir,a1.co_cli,a1.tx_ide FROM " +
            " tbr_cliloc AS a " +
            " INNER JOIN tbm_cli as a1 ON (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli)   " +
            " where  a1.st_prv = 'S' and a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" order by a1.tx_nom";
            
            objVenCon=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
       
    
    
    
    
    
    
    
    
    private boolean configurarVenConProducto() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.tx_codAlt2");
            arlCam.add("a7.tx_nomItm");
            arlCam.add("a7.nd_stkAct");
            arlCam.add("a7.nd_stkActcen");
            arlCam.add("a7.nd_preVta1");
            arlCam.add("a7.st_ivaVen");
            arlCam.add("a7.tx_descor");
            arlCam.add("a7.st_ser");
            arlCam.add("a7.isterL");
            arlCam.add("a7.co_itm");
            arlCam.add("a7.nd_maruti");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Tuv.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Nombre");
            arlAli.add("Stock");
            arlAli.add("StkCen");
            arlAli.add("Precio");
            arlAli.add("Iva.");
            arlAli.add("Uni.");
            arlAli.add("Ser.");
            arlAli.add("st_Ter");
            arlAli.add("Cód.Sis.");
            arlAli.add("Mar.Uti.");
            
            ArrayList arlAncCol=new ArrayList();
            
            
            String strAux = ",CASE " +
            "WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
            " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
            " ))) THEN 'S' " +
            "" +
            " WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
            " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
            " ))) THEN 'I' " +
            " ELSE 'N' END  as isterL";
            
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            if(objZafParSis.getCodigoEmpresa()==2 || objZafParSis.getCodigoEmpresa()==4){
                
                Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser";
                Str_Sql+=strAux;
                Str_Sql+=" , a7.nd_stkActcen, a7.nd_maruti";
                Str_Sql+=" FROM (";
                Str_Sql+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser , a2.nd_maruti,  d3.nd_stkActcen";
                Str_Sql+=" FROM (";
                Str_Sql+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                Str_Sql+=" FROM tbm_equInv AS b1";
                Str_Sql+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                Str_Sql+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
                Str_Sql+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
                Str_Sql+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
                Str_Sql+=" GROUP BY b1.co_itmMae";
                Str_Sql+=" ) AS a1 " +
                "" +
                " LEFT OUTER JOIN ( SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkActcen FROM tbm_equInv AS c1 " +
                " INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm) " +
                " INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer) " +
                " WHERE c3.co_emp=1 AND c3.co_loc=1 GROUP BY c1.co_itmMae " +
                " ) AS d3 ON (a1.co_itmMae=d3.co_itmMae) " +
                ", (";
                Str_Sql+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser , c1.nd_maruti ";
                Str_Sql+=" FROM tbm_inv AS c1";
                Str_Sql+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                Str_Sql+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                Str_Sql+=" WHERE c1.st_reg not in ('T','I','E','U') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
                Str_Sql+=" ) AS a2";
                Str_Sql+=" WHERE a1.co_itmMae=a2.co_itmMae";
                Str_Sql+=" ) AS a7  order by a7.tx_codalt";
                
                arlAncCol.add("75");
                arlAncCol.add("75");
                arlAncCol.add("210");
                arlAncCol.add("50");
                arlAncCol.add("50");
                arlAncCol.add("50");
                arlAncCol.add("25");
                arlAncCol.add("33");
                arlAncCol.add("33");
                arlAncCol.add("47");
                arlAncCol.add("40");
                
                int intColOcu[] =new int[5];
                intColOcu[0]=8;
                intColOcu[1]=9;
                intColOcu[2]=10;
                intColOcu[3]=11;
                intColOcu[4]=12;
                
                objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
                
                
            } else {
                
                Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser, a7.nd_maruti";
                Str_Sql+=strAux;
                Str_Sql+=", 0 as nd_stkActcen ";
                Str_Sql+=" FROM (";
                Str_Sql+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser, a2.nd_maruti ";
                Str_Sql+=" FROM (";
                Str_Sql+=" SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                Str_Sql+=" FROM tbm_equInv AS b1";
                Str_Sql+=" INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                Str_Sql+=" INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)";
                Str_Sql+=" WHERE b3.co_emp=" + objZafParSis.getCodigoEmpresa();
                Str_Sql+=" AND b3.co_loc=" + objZafParSis.getCodigoLocal();
                Str_Sql+=" GROUP BY b1.co_itmMae";
                Str_Sql+=" ) AS a1 " +
                ", (";
                Str_Sql+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser , c1.nd_maruti ";
                Str_Sql+=" FROM tbm_inv AS c1";
                Str_Sql+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                Str_Sql+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                Str_Sql+=" WHERE c1.st_reg not in ('T','I','E','U') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
                Str_Sql+=" ) AS a2";
                Str_Sql+=" WHERE a1.co_itmMae=a2.co_itmMae";
                Str_Sql+=" ) AS a7  order by a7.tx_codalt";
                
                arlAncCol.add("80");
                arlAncCol.add("75");
                arlAncCol.add("200");
                arlAncCol.add("55");
                arlAncCol.add("50");
                arlAncCol.add("55");
                arlAncCol.add("30");
                arlAncCol.add("46");
                arlAncCol.add("33");
                arlAncCol.add("47");
                arlAncCol.add("40");
                
                int intColOcu[]=new int[5];
                intColOcu[0]=5;
                intColOcu[1]=9;
                intColOcu[2]=10;
                intColOcu[3]=11;
                intColOcu[4]=12;
                  
                objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
                
            }
            
            
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            
            objVenCon2.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, objVenCon2.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, objVenCon2.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, objVenCon2.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            
            
            objVenCon2.setConfiguracionColumna(7, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    
    public void  LlamrVentana(String codalt,int codcli) {
        Ventas.ZafVen01.ZafVen01_01 obj1 = new  Ventas.ZafVen01.ZafVen01_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        String  strSQL="";
        int intCodMod=1;
        strSQL+="select distinct(b.co_doc),a.ne_numcot,a.ne_numdoc,a.tx_nomcli,a.fe_doc,b.co_tipdoc, ( b.nd_can * -1 ) as can, b.nd_preuni,b.nd_pordes";
        strSQL+=" from tbm_detmovinv as b,tbm_cabmovinv as a,tbm_cabtipdoc as x";
        strSQL+=" where";
        strSQL+=" x.ne_mod="+ intCodMod +" and b.co_tipdoc = x.co_tipdoc  and ";
        strSQL+=" b.co_itmact= '"+ codalt +"' and b.co_emp="+ objZafParSis.getCodigoEmpresa() +" "; // and b.co_loc= "+ objZafParSis.getCodigoLocal();
        strSQL+=" and a.co_doc = b.co_doc and a.co_emp = b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc = b.co_tipdoc ";
        strSQL+=" and a.st_reg IN ('A','R','D','C','F') ";
        
        if(codcli != 0 )
            strSQL+=" and  a.co_cli = "+ codcli;
        
        strSQL+= " order by a.fe_doc";
        
        
        
        obj1.cargaTexto(strSQL,codcli);
        obj1.show();
        obj1.dispose();
        obj1=null;
    }
    
    
    
    
    
    
    
    
    
    private boolean Configurartabla() {
        boolean blnRes=false;
        try{
            dblPorIva =  bldivaEmp;
            lblIva.setText("IVA " + dblPorIva + "%");
            
            //Configurar JTable: Establecer el modelo.
            Vector vecDat=new Vector();    //Almacena los datos
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_ITMALT,"Cod. Item");
            vecCab.add(INT_TBL_BUTITM,"");
            vecCab.add(INT_TBL_DESITM,"Descripcion");
            vecCab.add(INT_TBL_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_CODBOD,"Bodega");
            vecCab.add(INT_TBL_BUTBOD,"");
            vecCab.add(INT_TBL_CANMOV,"Cantidad");
            vecCab.add(INT_TBL_PREUNI,"Precio");
            vecCab.add(INT_TBL_PORDES,"%Desc");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ESTADO,"");
            vecCab.add(INT_TBL_IVATXT,"");
            vecCab.add(INT_TBL_PRE_COS,"Pre.Com.");            //Columna que contiene  precio de compra
            vecCab.add(INT_TBL_DESPRECOM , " %Desc.");
            vecCab.add(INT_TBL_COD_PRO,"Cod.prv.");            //Columna que contiene el codigo del proveedor
            vecCab.add(INT_TBL_NOM_PRO,"Proveedor");            //Columna que contiene el Nombre del proveedor
            vecCab.add(INT_TBL_BUT_PRO, "..");
            vecCab.add(INT_TBL_BLNPRE,"");
            vecCab.add(INT_TBL_ITMALT2,"ItmAlt2");
            vecCab.add(INT_TBL_ITMSER,"Itm.SER.");
            vecCab.add(INT_TBL_ITMTER," isTerL");
            vecCab.add(INT_TBL_MARUTI,"Mar.Uti.");
            
            vecCab.add(INT_TBL_CANITM,"");
            vecCab.add(INT_TBL_CANPRE,"");
            vecCab.add(INT_TBL_CANPOR,"");
            vecCab.add(INT_TBL_CANDITM,"");
            vecCab.add(INT_TBL_CANCAN,"");
            vecCab.add(INT_TBL_CANTOT,"");








            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            
          
            
            tblDat.setModel(objTblMod);
           
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            objTblMod.setColumnDataType(INT_TBL_CANMOV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PRE_COS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DESPRECOM, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            
            
            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PRE_COS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESPRECOM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_NOM_PRO).setPreferredWidth(100);
            
               

            ArrayList arlColHid=new ArrayList();

            arlColHid.add(""+INT_TBL_BUT_PRO);
            arlColHid.add(""+INT_TBL_CODBOD);
            arlColHid.add(""+INT_TBL_BUTBOD);
            arlColHid.add(""+INT_TBL_CODITM);
            arlColHid.add(""+INT_TBL_IVATXT);
            arlColHid.add(""+INT_TBL_COD_PRO);
            arlColHid.add(""+INT_TBL_BLNPRE);
            arlColHid.add(""+INT_TBL_ESTADO);
            arlColHid.add(""+INT_TBL_ITMALT2);
            arlColHid.add(""+INT_TBL_CANITM);
            arlColHid.add(""+INT_TBL_CANPRE);
            arlColHid.add(""+INT_TBL_CANPOR);
            arlColHid.add(""+INT_TBL_CANDITM);
            arlColHid.add(""+INT_TBL_CANCAN);
            arlColHid.add(""+INT_TBL_CANTOT);
            arlColHid.add(""+INT_TBL_MARUTI);
            arlColHid.add(""+INT_TBL_ITMTER);
            arlColHid.add(""+INT_TBL_ITMSER);

            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;


            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            
          /*
           * Dando tamano a la tabla de pagos
           */
            tblCotForPag.getTableHeader().setReorderingAllowed(false);
            
            tblCotForPag.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblCotForPag.getColumnModel().getColumn(0).setResizable(false);
            
            tblCotForPag.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblCotForPag.getColumnModel().getColumn(1).setResizable(false);
            
            tblCotForPag.getColumnModel().getColumn(2).setPreferredWidth(140);
            tblCotForPag.getColumnModel().getColumn(2).setResizable(false);
            
            tblCotForPag.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblCotForPag.getColumnModel().getColumn(3).setResizable(false);
            tblCotForPag.getColumnModel().getColumn(4).setPreferredWidth(140);
            tblCotForPag.getColumnModel().getColumn(4).setResizable(false);
            
            tblCotForPag.getColumnModel().getColumn(5).setPreferredWidth(130);
            tblCotForPag.getColumnModel().getColumn(5).setResizable(false);
            
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_ITMALT);
            vecAux.add("" + INT_TBL_BUTITM);
            vecAux.add("" + INT_TBL_CODBOD);
            vecAux.add("" + INT_TBL_BUTBOD);
            vecAux.add("" + INT_TBL_CANMOV);
            vecAux.add("" + INT_TBL_PREUNI);
            vecAux.add("" + INT_TBL_PORDES);
            vecAux.add("" + INT_TBL_DESPRECOM);
            vecAux.add("" + INT_TBL_PRE_COS);
            vecAux.add("" + INT_TBL_NOM_PRO);
            vecAux.add("" + INT_TBL_BUT_PRO);
            vecAux.add("" + INT_TBL_DESITM);
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    
                    blnChangeData = false;
                    if(tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA) != null)
                        strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA).toString();  
                    
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }}
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA) != null)
                        strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA).toString();
                    
                    if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                        tblDat.setValueAt("D", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);
                    }
                    
                    if (!tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        calculaSubTot();
                        blnChangeData = true;
                    }
                }
            });
            
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UNIDAD).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl=null;
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl=null;
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_PRE_COS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESPRECOM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT_PRO).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.
            String strSQL="";
            strSQL=" select distinct bod.co_bod, bod.tx_nom from tbm_bod as bod left outer join tbr_bodLoc as bodloc on ( bod.co_emp = bodloc.co_emp and bod.co_bod = bodloc.co_bod) where bod.co_emp = " + objZafParSis.getCodigoEmpresa() +
            " and bodloc.co_loc = " + objZafParSis.getCodigoLocal();
            int intColVenBod[]=new int[1];
            intColVenBod[0]=1;
            int intColTblBod[]=new int[1];
            intColTblBod[0]=INT_TBL_CODBOD;
            objTblCelEdiTxtCon=new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon("Listado Bodega", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL,  "tbm_bod.co_bod, tbm_bod.tx_nom","Codigo,Nombre", intColVenBod, intColTblBod);
            objTblCelEdiTxtCon.setIndiceCampoBusqueda(0);
            objTblCelEdiTxtCon.setCampoBusqueda("bod.co_bod", objTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtCon.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellEditor(objTblCelEdiTxtCon);
            objTblCelEdiTxtCon=null;
            objTblCelEdiBut=new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut("Listado Bodega", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL, "tbm_bod.co_bod, tbm_bod.tx_nom","Codigo,Nombre", intColVenBod, intColTblBod);
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut=null;
            intColVenBod=null;
            intColTblBod=null;
                   
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            int intColVen3[]=new int[2];
            intColVen3[0]=3;
            intColVen3[1]=1;
            int intColTbl3[]=new int[2];
            intColTbl3[0]=INT_TBL_COD_PRO;
            intColTbl3[1]=INT_TBL_NOM_PRO;
            objTblCelEdiTxtVcoPrv=new ZafTblCelEdiTxtVco(tblDat, objVenCon, intColVen3, intColTbl3);  //********
            tcmAux.getColumn(INT_TBL_NOM_PRO).setCellEditor(objTblCelEdiTxtVcoPrv);  //******
            objTblCelEdiTxtVcoPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        blnChangeData = false;
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                        
                        if(!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
                        }}
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        blnChangeData = false;
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                        
                        if(!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
                        }}
                    
                }
            });
            ButFndPrv ObjFndPrv = new ButFndPrv(tblDat, INT_TBL_BUT_PRO);   //*****
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            
            
            int intColVen2[]=new int[10];
            intColVen2[0]=1;
            intColVen2[1]=3;
            intColVen2[2]=6;
            intColVen2[3]=11;
            intColVen2[4]=7;
            intColVen2[5]=8;
            intColVen2[6]=2;
            intColVen2[7]=9;
            intColVen2[8]=10;
            intColVen2[9]=12;
            
            int intColTbl2[]=new int[10];
            intColTbl2[0]=INT_TBL_ITMALT;
            intColTbl2[1]=INT_TBL_DESITM;
            intColTbl2[2]=INT_TBL_PREUNI;
            intColTbl2[3]=INT_TBL_CODITM;
            intColTbl2[4]=INT_TBL_IVATXT;
            intColTbl2[5]=INT_TBL_UNIDAD;
            intColTbl2[6]=INT_TBL_ITMALT2;
            intColTbl2[7]=INT_TBL_ITMSER;
            intColTbl2[8]=INT_TBL_ITMTER;
            intColTbl2[9]=INT_TBL_MARUTI;
            
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco2(tblDat, objVenCon2, intColVen2, intColTbl2,objZafParSis);
            tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objVenCon2.setCampoBusqueda(0);
                    objVenCon2.setCriterio1(11);
                    blnChangeData = false;
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoItm.isConsultaAceptada()) {
                        eventoVenCon();
                    }
                } 
            });
            //**************************************************************************
            // ButFndItm ObjFndItm = new ButFndItm(tblDat, INT_TBL_BUTITM);   //*****
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, objVenCon2, intColVen2, intColTbl2);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada()) {
                        eventoVenCon();
                    }
                }
            });
            //*******************************************************************************
            
            intColVen2=null;
            intColTbl2=null;
            intColVen3=null;
            intColTbl3=null;
            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(new RenderDecimales(intNumDec) );
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    blnChangeData = false;
                    if (tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn())!=null)
                        strBeforeValue = tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn()).toString();
                    else
                        strBeforeValue = "";
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    
                    tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);
                    
                    if (tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn())!=null)
                        strAfterValue = tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn()).toString();
                    else
                        strAfterValue ="";
                    if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                        tblDat.setValueAt("D", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);
                    }
                    
                    
                    if (tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn()).equals(""))
                        tblDat.setValueAt("0",intNumFil,tblDat.getSelectedColumn());
                    if (tblDat.getSelectedColumn()==INT_TBL_PORDES){
                        if(strAfterValue.equals("")) strAfterValue="0.00";
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            MensajeInf("Error! Porcentaje de Descuento ");
                            tblDat.setValueAt(strBeforeValue, intNumFil, INT_TBL_PORDES);
                        }
                    }
                    blnChangeData = true;
                    calculaSubTot();
                    
                    
                }
            });
            //*******************************************************************************
            
            
            
            objTblCelEdiTxtCom=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_PRE_COS).setCellEditor(objTblCelEdiTxtCom);
            tcmAux.getColumn(INT_TBL_DESPRECOM).setCellEditor(objTblCelEdiTxtCom);
            objTblCelEdiTxtCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        blnChangeData = false;
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                        
                        if(!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtCom.setCancelarEdicion(true);
                        }}
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        blnChangeData = false;
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                        
                        if(!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtCom.setCancelarEdicion(true);
                        }}
                    
                }
            });
            
              
            
            //*******************************************************************************


            
              tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(new colorTexto(INT_TBL_CANITM));
              tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(new colorTexto(INT_TBL_CANDITM));
              tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(new colorTexto(INT_TBL_CANCAN));
              tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(new colorTexto(INT_TBL_CANPRE));
              tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(new colorTexto(INT_TBL_CANPOR));
              tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(new colorTexto(INT_TBL_CANTOT));
              tcmAux.getColumn(INT_TBL_PRE_COS).setCellRenderer(new colorTexto(INT_TBL_PRE_COS));
              tcmAux.getColumn(INT_TBL_DESPRECOM).setCellRenderer(new colorTexto(INT_TBL_DESPRECOM));
              tcmAux.getColumn(INT_TBL_NOM_PRO).setCellRenderer(new colorTexto(INT_TBL_NOM_PRO));

              
            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
            //Configurar JTable: Centrar columnas.
            new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            
            
            CerrarCon();
            
            blnRes=true;
        }catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;
    }
    
    
    
    
    //*********************************************************************************************************************
    //*********************************************************************************************************************
    
     
    private void eventoVenCon(){
        int intNumFil = tblDat.getSelectedRow();
        if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
            strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
        else
            strAfterValue ="";
        
        String strValpre="0.00";
        strValpre = tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).toString();
        if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).equals("")) strValpre="0";
        if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI)==null) strValpre="0";
        tblDat.setValueAt(strValpre,intNumFil,INT_TBL_PREUNI);
        
        if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV);
        
        if(tblDat.getValueAt(intNumFil,INT_TBL_PRE_COS)==null) tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PRE_COS);
        
        if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES);
        
        tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);
        
        if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
            if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).toString().trim().equalsIgnoreCase("S"))
                tblDat.setValueAt(new Boolean(true), intNumFil, INT_TBL_BLNIVA);
            else
                tblDat.setValueAt(new Boolean(false), intNumFil, INT_TBL_BLNIVA);
        }
        
        tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);
        
        if(tblDat.getValueAt(0,INT_TBL_PORDES) != null) tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
        
        calculaSubTot();
          
        tblDat.repaint();
        tblDat.requestFocus();
        tblDat.editCellAt(intNumFil, INT_TBL_CANMOV);
    }
    
    
    //*********************************************************************************************************************
    //*********************************************************************************************************************
      
    
    public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
    
    public void calculaSubTot(){
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
          
          
        int intFilSel=tblDat.getSelectedRow();
          
        dblCan    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV).toString())));
        dblPre    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI).toString())));
        dblDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PORDES).toString()))),2);
        dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100));
        dblTotal  = (dblCan * dblPre)- dblValDes;
        
        dblTotal =  objUti.redondear(dblTotal,3);
        dblTotal =  objUti.redondear(dblTotal,2);
        
          
        
        tblDat.setValueAt(dblTotal+"",intFilSel,INT_TBL_TOTAL);
        calculaTot();
        calculaPag();
    }
       
    
    
    
    
    //****************************************************************
    
    public void calculaSubTotTodLosItm(){
        
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
        double dblSub = 0, dblIva = 0, dblDes2 = 0, dblTmp=0, dblSub2=0;
        int intFilSel=0;  //tblDat.getSelectedRow();
        for (int intNumFil=0; intNumFil<tblDat.getRowCount(); intNumFil++){
            if(tblDat.getValueAt(intNumFil,INT_TBL_CODITM) != null ){
                intFilSel=intNumFil;
                dblCan    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV).toString())));
                dblPre    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI).toString())));
                dblDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PORDES).toString()))),2);
                dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100));
                dblTotal  = (dblCan * dblPre)- dblValDes;
                dblTotal =  objUti.redondear(dblTotal,3);
                dblTotal =  objUti.redondear(dblTotal,2);
                tblDat.setValueAt(dblTotal+"",intFilSel,INT_TBL_TOTAL);
                
                dblSub2 = ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString()));
                dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                if(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)==null)
                    dblIva = dblIva + 0;
                else{
                    dblTmp = ((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString())) : 0 );
                    dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                }
            }}
        
        /////////////////////////////////////////////////////////////
        dblSubtotalCot = dblSub;
        dblIvaCot = objUti.redondear(dblIva,intNumDec);
        dblTotalCot = dblSubtotalCot + dblIvaCot;
        dblTotalCot = objUti.redondear(dblTotalCot ,intNumDec);
        dblSubtotalCot = objUti.redondear(dblSubtotalCot ,intNumDec);
        
        txtSub.setText( "" + dblSubtotalCot );
        txtIva.setText( "" + dblIvaCot );
        txtTot.setText( ""+ dblTotalCot);
        /////////////////////////////////////////////////////////////
        
        calculaPag();
    }
    
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabCotCom = new javax.swing.JTabbedPane();
        panCotGen = new javax.swing.JPanel();
        panCotGenNor = new javax.swing.JPanel();
        panCotGenNorNor = new javax.swing.JPanel();
        lblCom = new javax.swing.JLabel();
        lblFecDoc = new javax.swing.JLabel();
        txtAte = new javax.swing.JTextField();
        lblAte = new javax.swing.JLabel();
        txtVenCod = new javax.swing.JTextField();
        txtVenNom = new javax.swing.JTextField();
        butVenCon = new javax.swing.JButton();
        txtCot = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tx_numped = new javax.swing.JTextField();
        lblAte1 = new javax.swing.JLabel();
        panCotGenNorSur = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtCliDir = new javax.swing.JTextField();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        butCliCon = new javax.swing.JButton();
        txtCodLoc = new javax.swing.JTextField();
        butDirCliCon = new javax.swing.JButton();
        PanEmpLoc = new javax.swing.JPanel();
        lblEmp = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        local = new javax.swing.JComboBox();
        empresa = new javax.swing.JComboBox();
        spnCon = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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
        panCotForPag = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblCotForPag = new javax.swing.JTable();
        panCotForPagNo = new javax.swing.JPanel();
        panCotForPagNorCen = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        txtNomForPag = new javax.swing.JTextField();
        butCliCon1 = new javax.swing.JButton();
        panCotNor = new javax.swing.JPanel();
        lblCotNumDes = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(700, 450));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                CerrarVentana(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameIconified(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        tabCotCom.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabCotCom.setName("General"); // NOI18N

        panCotGen.setLayout(new java.awt.BorderLayout());

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 120));
        panCotGenNor.setLayout(new java.awt.BorderLayout());

        panCotGenNorNor.setPreferredSize(new java.awt.Dimension(600, 50));
        panCotGenNorNor.setLayout(null);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCom.setText("Vendedor:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblCom);
        lblCom.setBounds(412, 8, 70, 15);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNorNor.add(lblFecDoc);
        lblFecDoc.setBounds(6, 30, 108, 15);
        panCotGenNorNor.add(txtAte);
        txtAte.setBounds(484, 28, 144, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte);
        lblAte.setBounds(412, 28, 60, 15);

        txtVenCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtVenCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtVenCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVenCodActionPerformed(evt);
            }
        });
        txtVenCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVenCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVenCodFocusLost(evt);
            }
        });
        panCotGenNorNor.add(txtVenCod);
        txtVenCod.setBounds(484, 8, 35, 20);

        txtVenNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtVenNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVenNomActionPerformed(evt);
            }
        });
        txtVenNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVenNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVenNomFocusLost(evt);
            }
        });
        panCotGenNorNor.add(txtVenNom);
        txtVenNom.setBounds(520, 8, 135, 20);

        butVenCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butVenCon.setText("...");
        butVenCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butVenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenConActionPerformed(evt);
            }
        });
        panCotGenNorNor.add(butVenCon);
        butVenCon.setBounds(656, 8, 20, 20);

        txtCot.setBackground(objZafParSis.getColorCamposSistema()
        );
        objZafParSis.getColorCamposObligatorios();
        txtCot.setMaximumSize(null);
        txtCot.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCotActionPerformed(evt);
            }
        });
        txtCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCotKeyPressed(evt);
            }
        });
        panCotGenNorNor.add(txtCot);
        txtCot.setBounds(116, 10, 92, 20);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel1.setText("No. Cotización:");
        panCotGenNorNor.add(jLabel1);
        jLabel1.setBounds(6, 12, 110, 15);

        tx_numped.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        panCotGenNorNor.add(tx_numped);
        tx_numped.setBounds(304, 8, 96, 20);

        lblAte1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte1.setText("No. Pedido:");
        lblAte1.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte1);
        lblAte1.setBounds(244, 8, 60, 15);

        panCotGenNor.add(panCotGenNorNor, java.awt.BorderLayout.CENTER);

        panCotGenNorSur.setPreferredSize(new java.awt.Dimension(600, 45));
        panCotGenNorSur.setLayout(null);

        lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPrv.setText("Cliente::");
        panCotGenNorSur.add(lblPrv);
        lblPrv.setBounds(6, 0, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Dirección:");
        panCotGenNorSur.add(lblDir);
        lblDir.setBounds(6, 20, 60, 15);

        txtCliDir.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliDir.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCliDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliDirActionPerformed(evt);
            }
        });
        txtCliDir.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCliDirCaretUpdate(evt);
            }
        });
        panCotGenNorSur.add(txtCliDir);
        txtCliDir.setBounds(116, 20, 333, 20);

        txtCliCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliCod.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCliCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCliCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliCodActionPerformed(evt);
            }
        });
        txtCliCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliCodFocusLost(evt);
            }
        });
        panCotGenNorSur.add(txtCliCod);
        txtCliCod.setBounds(116, 0, 35, 20);

        txtCliNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliNom.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtCliNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliNomActionPerformed(evt);
            }
        });
        txtCliNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliNomFocusLost(evt);
            }
        });
        txtCliNom.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtCliNomVetoableChange(evt);
            }
        });
        panCotGenNorSur.add(txtCliNom);
        txtCliNom.setBounds(150, 0, 300, 20);

        butCliCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCliCon.setText("...");
        butCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butCliCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliConActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(butCliCon);
        butCliCon.setBounds(450, 0, 22, 20);

        txtCodLoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodLocKeyPressed(evt);
            }
        });
        panCotGenNorSur.add(txtCodLoc);
        txtCodLoc.setBounds(484, 16, 60, 20);

        butDirCliCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butDirCliCon.setText("...");
        butDirCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butDirCliCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDirCliConActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(butDirCliCon);
        butDirCliCon.setBounds(450, 20, 22, 20);

        panCotGenNor.add(panCotGenNorSur, java.awt.BorderLayout.SOUTH);

        PanEmpLoc.setPreferredSize(new java.awt.Dimension(0, 25));
        PanEmpLoc.setLayout(null);

        lblEmp.setText("Empresa:");
        PanEmpLoc.add(lblEmp);
        lblEmp.setBounds(8, 4, 80, 14);

        jLabel3.setText("Local:");
        PanEmpLoc.add(jLabel3);
        jLabel3.setBounds(224, 4, 80, 14);

        local.setFont(new java.awt.Font("SansSerif", 0, 11));
        PanEmpLoc.add(local);
        local.setBounds(260, 4, 160, 20);

        empresa.setFont(new java.awt.Font("SansSerif", 0, 11));
        empresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                empresaItemStateChanged(evt);
            }
        });
        empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresaActionPerformed(evt);
            }
        });
        PanEmpLoc.add(empresa);
        empresa.setBounds(64, 4, 156, 20);

        panCotGenNor.add(PanEmpLoc, java.awt.BorderLayout.NORTH);

        panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Linea", "Codigo", "...", "Descripción", "Unidad", "Cantidad", "Precio", "%Desc", "Iva", "Total", "Codigo", "Pre.Com", "Cod.prv", "proveedor", "...", "BLVPRE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.Short.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, true, true, true, false, false, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.setCellSelectionEnabled(true);
        spnCon.setViewportView(tblDat);

        panCotGen.add(spnCon, java.awt.BorderLayout.CENTER);

        panCotGenSur.setLayout(new java.awt.BorderLayout());

        panCotGenSurCen.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observación 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

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

        panCotForPag.setLayout(new java.awt.BorderLayout());

        tblCotForPag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "%Retención", "Monto de Pago", "Días de gracia", "Codret", "tx_sop"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCotForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblCotForPag.setColumnSelectionAllowed(true);
        spnForPag.setViewportView(tblCotForPag);

        panCotForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        panCotForPagNo.setEnabled(false);
        panCotForPagNo.setPreferredSize(new java.awt.Dimension(249, 60));
        panCotForPagNo.setLayout(new java.awt.BorderLayout());

        panCotForPagNorCen.setPreferredSize(new java.awt.Dimension(249, 40));

        lblForPag.setText("Forma de Pago:");
        panCotForPagNorCen.add(lblForPag);

        txtNomForPag.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomForPag.setPreferredSize(new java.awt.Dimension(180, 21));
        panCotForPagNorCen.add(txtNomForPag);

        butCliCon1.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCliCon1.setText("...");
        butCliCon1.setPreferredSize(new java.awt.Dimension(20, 20));
        butCliCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliCon1ActionPerformed(evt);
            }
        });
        panCotForPagNorCen.add(butCliCon1);

        panCotForPagNo.add(panCotForPagNorCen, java.awt.BorderLayout.CENTER);

        panCotForPag.add(panCotForPagNo, java.awt.BorderLayout.NORTH);

        tabCotCom.addTab("tab2", panCotForPag);

        getContentPane().add(tabCotCom, java.awt.BorderLayout.CENTER);

        lblCotNumDes.setText("Cotizaciones");
        lblCotNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCotNumDes.setOpaque(true);
        panCotNor.add(lblCotNumDes);

        getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-710)/2, (screenSize.height-452)/2, 710, 452);
    }// </editor-fold>//GEN-END:initComponents

    private void butDirCliConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDirCliConActionPerformed
        // TODO add your handling code here:
          
        objVenConDirCli.setTitle("Listado de Direcciones de Clientes");
        objVenConDirCli.setSentenciaSQL("select co_cli, tx_dir, tx_tel1, tx_tel2, tx_tel3 from tbm_dircli where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_cli="+(txtCliCod.getText().equals("")?"0":txtCliCod.getText()) );
        objVenConDirCli.setCampoBusqueda(1);
        objVenConDirCli.cargarDatos();
        objVenConDirCli.show();
        if (objVenConDirCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
            txtCliDir.setText(objVenConDirCli.getValueAt(2));
            txtcli_telefono.setText( objVenConDirCli.getValueAt(3).trim() +" "+ objVenConDirCli.getValueAt(4).trim() +" "+ objVenConDirCli.getValueAt(5).trim() );
        }
        
        
    }//GEN-LAST:event_butDirCliConActionPerformed
    
    private void empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresaActionPerformed
        // TODO add your handling code here:
        
        
        if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
            
            
            if(empresa.getSelectedIndex()>=0){
                String sql = "select co_loc , tx_nom from tbm_loc where co_emp="+vecEmp.elementAt(empresa.getSelectedIndex())+" AND st_reg='P'";
                objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                objZafParSis.getClaveBaseDatos(), sql, local, vecLoc);
                
                local.setSelectedIndex(0);
            }
        }
        
    }//GEN-LAST:event_empresaActionPerformed
    
    private void empresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_empresaItemStateChanged
        // TODO add your handling code here:
        
      
        
    }//GEN-LAST:event_empresaItemStateChanged
        
    private void butCliCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliCon1ActionPerformed
        // TODO add your handling code here:
        Ventas.ZafVen01.ZafVen01_04 obj = new  Ventas.ZafVen01.ZafVen01_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        
        int codcli=Integer.parseInt((((txtCliCod.getText()==null) || (txtCliCod.getText().equalsIgnoreCase("")))?"0":txtCliCod.getText()));
        obj.SetvalCodCli(codcli);
        
        obj.show();
        if(obj.acepta()){
            txtCodForPag.setText(obj.GetCamSel(1));
            txtNomForPag.setText(obj.GetCamSel(2));
            
            intTipForPag2 = Integer.parseInt(obj.GetCamSel(3));
            dblPes2 = Double.parseDouble(obj.GetCamSel(4));
              
            
            blnHayCam=true;
            CalculoPago();
            
        }
        
        
    }//GEN-LAST:event_butCliCon1ActionPerformed
    
    private void CalculoPago(){
        try{
            java.sql.Connection conPag = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conPag!=null){
                
                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop " +
                " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where A1.co_forPag = "  + txtCodForPag.getText() +// Clausulas Where para las tablas maestras
                "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
                "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando
                
                String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
                " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where A1.co_forPag = "  + txtCodForPag.getText() +// Clausulas Where para las tablas maestras
                "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
                "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando
                
                Statement stmDoc2   = conPag.createStatement();
                ResultSet rstDocCab2= stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);
                
                stmDoc2   = conPag.createStatement();
                rstDocCab2= stmDoc2.executeQuery(sSQL2);
                int x=0;
                while(rstDocCab2.next()){
                    intarreglodia[x]=rstDocCab2.getInt(2);
                    intarreglonum[x]=rstDocCab2.getInt(1);
                    strarreglosop[x]=rstDocCab2.getString("st_sop");
                    x++;
                }
                conPag.close();
                conPag = null ;
            }
        }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
        calculaPag();
    }
    
    
     
    
    private void CalculoPago2(){
        try{
            java.sql.Connection conPag = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conPag!=null){
                
                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop " +
                " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where A1.co_forPag = "  + txtCodForPag.getText() +// Clausulas Where para las tablas maestras
                "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
                "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando
                
                String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
                " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where A1.co_forPag = "  + txtCodForPag.getText() +// Clausulas Where para las tablas maestras
                "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
                "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando
                
                
                
                Statement stmDoc2   = conPag.createStatement();
                ResultSet rstDocCab2= stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);
                
                stmDoc2   = conPag.createStatement();
                rstDocCab2= stmDoc2.executeQuery(sSQL2);
                int x=0;
                while(rstDocCab2.next()){
                    intarreglodia[x]=rstDocCab2.getInt(2);
                    intarreglonum[x]=rstDocCab2.getInt(1);
                    strarreglosop[x]=rstDocCab2.getString("st_sop");
                    x++;
                }
                
                conPag.close();
                conPag = null ;
            }
        }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
    }
  
    
    
    
    private void txtVenNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusGained
        // TODO add your handling code here:
        strDesLarCom=txtVenNom.getText();
        txtVenNom.selectAll();
    }//GEN-LAST:event_txtVenNomFocusGained
    
    
    private void txtVenCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusGained
        // TODO add your handling code here:
        strCodCom=txtVenCod.getText();
        txtVenCod.selectAll();
    }//GEN-LAST:event_txtVenCodFocusGained
    
    private void txtCliNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusGained
        // TODO add your handling code here:
        strDesLarPrv=txtCliNom.getText();
        txtCliNom.selectAll();
    }//GEN-LAST:event_txtCliNomFocusGained
    
    private void txtCliCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusGained
        // TODO add your handling code here:
        strCodPrv=txtCliCod.getText();
        txtCliCod.selectAll();
    }//GEN-LAST:event_txtCliCodFocusGained
    
    private void txtCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCotKeyPressed
        // TODO add your handling code here:
        if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
            objTooBar.consultar();
            objTooBar.setEstado('w');
        }
    }//GEN-LAST:event_txtCotKeyPressed
    
    private void txtCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCotActionPerformed
        // TODO add your handling code here:
        
          
        
    }//GEN-LAST:event_txtCotActionPerformed
    
    
    private void txtVenCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusLost
        
        
        if (!txtVenCod.getText().equalsIgnoreCase(strCodCom)) {
            if (txtVenCod.getText().equals("")) {
                txtVenCod.setText("");
                txtVenNom.setText("");
            }
            else {
                BuscarVendedor("a.co_usr",txtVenCod.getText(),0);
            }
        }
        else
            txtVenCod.setText(strCodCom);
        
           
        
        
    }//GEN-LAST:event_txtVenCodFocusLost
    
        
    
    
    
    
    public boolean validacionfechaFac(java.sql.Connection conn){
        boolean blnRes=false;
        String sql="";
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try{
            if(conn!= null){
                sql = " SELECT * FROM ( " +
                "         select  (date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 01)-1) - current_date  as fecha  " +
                "  ) as x WHERE x.fecha <= 5 ";
                stm=conn.createStatement();
                rst=stm.executeQuery(sql);
                if(rst.next()){
                    blnRes=true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
            }
        }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);    }
        catch(Exception Evt){     objUti.mostrarMsgErr_F1(jfrThis, Evt);    }
        return blnRes;
    }
    
    
    
    
    
    
   
    
    
      
    
      
    
    
    
        
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ConfiguraVenCon();
        Configurartabla();
           
        if(intValBus==1){
            
            txtCot.setText(""+ intCodCot);
            consultar2(new Integer(intCodEmp),new Integer(intCodLoc)  );
            objTooBar.setEstado('w');


            if(intCodHis > 0){

                Ventas.ZafVen01.ZafVen01_09 obj1 = new Ventas.ZafVen01.ZafVen01_09(objZafParSis,  intCodEmp, intCodLoc , intCodCot  );

                 this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
                 obj1.setLocation(500, 500);
                 obj1.show();

            }

            

//             Ventas.ZafVen01.ZafVen01 obj;
//             if(intCodHis==0){
//                 obj = new Ventas.ZafVen01.ZafVen01(objZafParSis, new Integer(intCodEmp), new Integer(intCodLoc), new  Integer(intCodCot) , 1 );
//                 this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
//                // obj.setLocation(500, 500);
//                 obj.show();
//                 this.dispose();
//             }else{
//                 //obj = new Ventas.ZafVen01.ZafVen01(objZafParSis, new Integer(intCodEmp), new Integer(intCodLoc), new  Integer(intCodCot) , 2 );
//
//                 Ventas.ZafVen01.ZafVen01_09 obj1 = new Ventas.ZafVen01.ZafVen01_09(objZafParSis,  intCodEmp, intCodLoc , intCodCot  );
//
//                 this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
//                 obj1.setLocation(500, 500);
//                 obj1.show();
//             }
             
        }
        
        
         if(intValBus==2){

            txtCot.setText(""+ intCodCot);
            consultar3(new Integer(intCodEmp),new Integer(intCodLoc), new Integer(intCodHis)  );
            objTooBar.setEstado('w');

         }
     
        
        
    }//GEN-LAST:event_formInternalFrameOpened
    
    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameIconified
    
    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        // TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
            cerrarObj();
            System.gc();
            dispose();
        }
    }//GEN-LAST:event_CerrarVentana
      
    
    
    public void cerrarObj(){
        try{
            objVenCon2.dispose(); //***
            objVenCon2=null;  //****
            objVenConVen.dispose();
            objVenConVen=null;
            objVenConCli.dispose();
            objVenConCli=null;
            objUti=null;
            objTooBar=null;
            objAutPrg=null;
            objlisCambios=null;
            objZafParSis=null;
            txtFecDoc=null;
            objTblCelEdiTxtVcoPrv=null;
            objTblCelEdiTxtVcoItm=null;
            objUltDocPrint=null;
            
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
     




    
    private void txtCliNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusLost
        // TODO add your handling code here:
        if (!txtCliNom.getText().equalsIgnoreCase(strDesLarPrv)) {
            if (txtCliNom.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {
                BuscarCliente("a.tx_nom",txtCliNom.getText(),1);
            }
        }
        else
            txtCliNom.setText(strDesLarPrv);
    }//GEN-LAST:event_txtCliNomFocusLost
    
    private void txtVenNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusLost
        // TODO add your handling code here:
        if (!txtVenNom.getText().equalsIgnoreCase(strDesLarCom)) {
            if (txtVenNom.getText().equals("")) {
                txtVenCod.setText("");
                txtVenNom.setText("");
            }
            else {
                BuscarVendedor("a.tx_nom",txtVenNom.getText(),1);
            }
        }
        else
            txtVenNom.setText(strDesLarCom);
    }//GEN-LAST:event_txtVenNomFocusLost
    
    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed
    
    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated
    
    private void txtCliDirCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCliDirCaretUpdate
        
    }//GEN-LAST:event_txtCliDirCaretUpdate
    
    private void txtCliNomVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtCliNomVetoableChange
        // Prueba
    }//GEN-LAST:event_txtCliNomVetoableChange
    
    
    private void txtCliCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusLost
        if (!txtCliCod.getText().equalsIgnoreCase(strCodPrv)) {
            if (txtCliCod.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {
                BuscarCliente("a.co_cli",txtCliCod.getText(),0);
            }
        }
        else
            txtCliCod.setText(strCodPrv);
        
    }//GEN-LAST:event_txtCliCodFocusLost
    
    private void txtCliNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliNomActionPerformed
        txtCliNom.transferFocus();
        
    }//GEN-LAST:event_txtCliNomActionPerformed
    
    private void txtVenCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenCodActionPerformed
        txtVenCod.transferFocus();
        
    }//GEN-LAST:event_txtVenCodActionPerformed
    
    
  private void txtCliCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliCodActionPerformed
      txtCliCod.transferFocus();
      
  }//GEN-LAST:event_txtCliCodActionPerformed
  
  
  
  public void BuscarCliente(String campo,String strBusqueda,int tipo){
      objVenConCli.setTitle("Listado de Clientes");
      if (objVenConCli.buscar(campo, strBusqueda )) {
          txtCliCod.setText(objVenConCli.getValueAt(1));
          txtCliNom.setText(objVenConCli.getValueAt(2));
          txtCliDir.setText(objVenConCli.getValueAt(3));
          strTipPer_glo = objVenConCli.getValueAt(6);
          Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
          Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
          txtcli_telefono.setText(objVenConCli.getValueAt(4));
          txtcli_identificacion.setText(objVenConCli.getValueAt(5));
          txtcli_ciudad.setText(objVenConCli.getValueAt(9));
          txtVenCod.setText(objVenConCli.getValueAt(10));
          txtVenNom.setText(objVenConCli.getValueAt(11));
          strCodTipPerCli =  objVenConCli.getValueAt(12);
            intTipForPag =  Integer.parseInt(objVenConCli.getValueAt(13));
            dblPes = Double.parseDouble(objVenConCli.getValueAt(14));
            dblDiaGraCli = Double.parseDouble(objVenConCli.getValueAt(15));
            
            txtCodForPag.setText(objVenConCli.getValueAt(16));
            txtNomForPag.setText(objVenConCli.getValueAt(17));
            intTipForPag2 = intTipForPag;
            dblPes2 =dblPes;
            CalculoPago();
            
          calculaPag();
      }
      else
      {     objVenConCli.setCampoBusqueda(tipo);
            objVenConCli.cargarDatos();
            objVenConCli.show();
            if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
                txtCliCod.setText(objVenConCli.getValueAt(1));
                txtCliNom.setText(objVenConCli.getValueAt(2));
                txtCliDir.setText(objVenConCli.getValueAt(3));
                strTipPer_glo = objVenConCli.getValueAt(6);
                Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
                Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
                txtcli_telefono.setText(objVenConCli.getValueAt(4));
                txtcli_identificacion.setText(objVenConCli.getValueAt(5));
                txtcli_ciudad.setText(objVenConCli.getValueAt(9));
                txtVenCod.setText(objVenConCli.getValueAt(10));
                txtVenNom.setText(objVenConCli.getValueAt(11));
                strCodTipPerCli =  objVenConCli.getValueAt(12);
                   intTipForPag =  Integer.parseInt(objVenConCli.getValueAt(13));
                   dblPes = Double.parseDouble(objVenConCli.getValueAt(14));
                   dblDiaGraCli = Double.parseDouble(objVenConCli.getValueAt(15));
                        
                        txtCodForPag.setText(objVenConCli.getValueAt(16));
                        txtNomForPag.setText(objVenConCli.getValueAt(17));
                        intTipForPag2 = intTipForPag;
                        dblPes2 =dblPes;
                        CalculoPago();
                   
                calculaPag();
            }
            else{
                txtCliCod.setText(strCodPrv);
                txtCliNom.setText(strDesLarPrv);
            }
      }
  }
  
  
    
  
  
  private void txtVenNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenNomActionPerformed
      txtVenNom.transferFocus();
      
  }//GEN-LAST:event_txtVenNomActionPerformed
  
  
  
  private void FndVenToCli(){
            /*
             * Obteniendo el maximo porcentaje de descuento para el cliente
             */
      try{
          if(objTooBar.getEstado() == 'n' ||  objTooBar.getEstado()=='m'){
              java.sql.Connection conVenToCli = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
              if (conVenToCli!=null){
                  Statement stmCliVen = conVenToCli.createStatement();
                  ResultSet rstCliVen = stmCliVen.executeQuery(
                  "select cli.co_ven, ven.tx_nom from tbm_cli as cli , tbm_usr as ven where " +
                  " cli.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                  " cli.co_cli = " + Integer.parseInt(txtCliCod.getText()) +  " and " +
                  " ven.co_usr = cli.co_ven "
                  );
                  
                  if(rstCliVen.next()){
                      txtVenCod.setText(rstCliVen.getInt("co_ven")+"");
                      txtVenNom.setText(
                      (rstCliVen.getString("tx_nom")==null)?"":rstCliVen.getString("tx_nom")
                      );
                  }
                  rstCliVen.close();
                  stmCliVen.close();
                  conVenToCli.close();
                  rstCliVen = null;
                  stmCliVen = null;
                  conVenToCli = null;
              }
          }
      }
      
      catch(SQLException Evt) {
          objUti.mostrarMsgErr_F1(jfrThis, Evt);
      }
      
      catch(Exception Evt) {
          objUti.mostrarMsgErr_F1(jfrThis, Evt);
      }
  }
  
  
    /*
     * Agrega el listener para detectar que hubo algun cambio en la caja de texto
     */
  private void addListenerCambio(){
      objlisCambios = new LisTextos();
      //Cabecera
      txtCot.setText("");
      txtCot.getDocument().addDocumentListener(objlisCambios);
      txtCliCod.getDocument().addDocumentListener(objlisCambios);
      txtCliNom.getDocument().addDocumentListener(objlisCambios);
      txtCliDir.getDocument().addDocumentListener(objlisCambios);
      txtVenCod.getDocument().addDocumentListener(objlisCambios);
      txtVenNom.getDocument().addDocumentListener(objlisCambios);
      txtAte.getDocument().addDocumentListener(objlisCambios);
      
      //Pie de pagina
      txaObs1.getDocument().addDocumentListener(objlisCambios);
      txaObs2.getDocument().addDocumentListener(objlisCambios);
      
  }
  
    /*
     * Clase de tipo documenet listener para detectar los cambios en
     * los textcomponent
     */
  class LisTextos implements javax.swing.event.DocumentListener {
      public void changedUpdate(javax.swing.event.DocumentEvent e) {
          // //System.out.println("CAMBIA ESTADO ...1");
          blnHayCam = true;
      }
      
      public void insertUpdate(javax.swing.event.DocumentEvent e) {
          //  //System.out.println("CAMBIA ESTADO ...2");
          blnHayCam = true;
      }
      
      public void removeUpdate(javax.swing.event.DocumentEvent e) {
          // //System.out.println("CAMBIA ESTADO ...3");
          blnHayCam = true;
      }
  }
  
  class LisCambioTbl implements javax.swing.event.TableModelListener{
      public void tableChanged(javax.swing.event.TableModelEvent e){
          
          if(objTblMod.isDataModelChanged()){
              /// //System.out.println("CAMBIA ESTADO EN TABLA ...");
              calculaTot();
          }
          
      }
  }
  
    private void butVenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenConActionPerformed
        BuscarVendedor("a.co_usr","",0);
    }//GEN-LAST:event_butVenConActionPerformed
    
    
    
    
    public void BuscarVendedor(String campo,String strBusqueda,int tipo){
        // configurarVenConVendedor();  //************************
        objVenConVen.setTitle("Listado de vendedores");
        if (objVenConVen.buscar(campo, strBusqueda )) {
            txtVenCod.setText(objVenConVen.getValueAt(1));
            txtVenNom.setText(objVenConVen.getValueAt(2));
        }
        else
        {     objVenConVen.setCampoBusqueda(tipo);
              objVenConVen.cargarDatos();
              objVenConVen.show();
              if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
                  txtVenCod.setText(objVenConVen.getValueAt(1));
                  txtVenNom.setText(objVenConVen.getValueAt(2));
              }
              else{
                  txtVenCod.setText(strCodCom);
                  txtVenNom.setText(strDesLarCom);
              }
        }
    }
    
      
    
    private void butCliConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliConActionPerformed
        
        objVenConCli.setTitle("Listado de Clientes");
        objVenConCli.setCampoBusqueda(1);
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
            txtCliCod.setText(objVenConCli.getValueAt(1));
            txtCliNom.setText(objVenConCli.getValueAt(2));
            txtCliDir.setText(objVenConCli.getValueAt(3));
            strTipPer_glo = objVenConCli.getValueAt(6);
            Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
            Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
            txtcli_telefono.setText(objVenConCli.getValueAt(4));
            txtcli_identificacion.setText(objVenConCli.getValueAt(5));
            txtcli_ciudad.setText(objVenConCli.getValueAt(9));
            txtVenCod.setText(objVenConCli.getValueAt(10));
            txtVenNom.setText(objVenConCli.getValueAt(11));
            strCodTipPerCli =  objVenConCli.getValueAt(12);
            
            intTipForPag =  Integer.parseInt(objVenConCli.getValueAt(13));
            dblPes = Double.parseDouble(objVenConCli.getValueAt(14));
            dblDiaGraCli = Double.parseDouble(objVenConCli.getValueAt(15));
            
            
            
            txtCodForPag.setText(objVenConCli.getValueAt(16));
            txtNomForPag.setText(objVenConCli.getValueAt(17));
            intTipForPag2 = intTipForPag;
            dblPes2 =dblPes;
            CalculoPago();
            
            
            calculaPag();
        }
        
        
        
    }//GEN-LAST:event_butCliConActionPerformed

    private void txtCodLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodLocKeyPressed
        // TODO add your handling code here:

        if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
            objTooBar.consultarLoc();

            objTooBar.cierraConnections();
            blnLimpiar = false;
            txtCot.setText("");
            txtFecDoc.setHoy();
            objTooBar.setEstado('n');
            blnLimpiar = true;
        }


    }//GEN-LAST:event_txtCodLocKeyPressed
    
       
    //   private void CargarFormadePagoCliente(){
    //       try{
    //           conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    //           String sql=" SELECT b.co_forpag, b.tx_des FROM tbm_forpagcli as a" +
    //           " INNER JOIN tbm_cabForPag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag)" +
    //           " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_cli="+txtCliCod.getText()+" and a.st_reg='P'";
    //           java.sql.Statement stmCot=conCot.createStatement();
    //           java.sql.ResultSet rst=stmCot.executeQuery(sql);
    //           if(rst.next()){
    //               txtCodForPag.setText(""+rst.getString("co_forpag"));
    //               txtNomForPag.setText(""+rst.getString("tx_des"));
    //               CalculoPago();
    //
    //           }else {
    //               txtCodForPag.setText("");
    //               txtNomForPag.setText("");
    //           }
    //
    //           conCot.close();
    //           conCot=null;
    //           stmCot.close();
    //           stmCot=null;
    //           rst.close();
    //           rst=null;
    //       }
    //       catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    //       catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    //   }
    //
    
    
      
    
    private void cargarForPagCli(){
        try{
            conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            String sql=" SELECT b.co_forpag, b.tx_des, b.ne_tipforpag, b.nd_pes FROM tbm_cabForPag as b " +
            " WHERE b.co_emp="+objZafParSis.getCodigoEmpresa()+" and b.st_reg='P'";
            java.sql.Statement stmCot=conCot.createStatement();
            java.sql.ResultSet rst=stmCot.executeQuery(sql);
            if(rst.next()){
                txtCodForPag.setText(""+rst.getString("co_forpag"));
                txtNomForPag.setText(""+rst.getString("tx_des"));
                
                intTipForPag2 = rst.getInt("ne_tipforpag");
                dblPes2 = rst.getDouble("nd_pes");
                
                CalculoPago();
                
            }else {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
            }
            
            conCot.close();
            conCot=null;
            stmCot.close();
            stmCot=null;
            rst.close();
            rst=null;
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    }
    
    
    
    
  /*
   * Obtiene el Iva que se debe cobrar en la empresa actual
   */
    public double getIva(){
        double dblIvaActual = 0;
        try{
            if (CONN_GLO!=null){
                stmCot=CONN_GLO.createStatement();
                sSQL= "SELECT round(nd_ivaVen,2) as porIva from tbm_emp " +
                "where co_emp = " + objZafParSis.getCodigoEmpresa();  //<== Verificando que seleccione la empresa actual
                ResultSet rstIva = stmCot.executeQuery(sSQL);
                if(rstIva.next()){
                    dblIvaActual = rstIva.getDouble("porIva");
                }
                rstIva.close();
                stmCot.close();
                rstIva=null;
                stmCot=null;
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
        return dblIvaActual;
    }
    
    
    
    public void  noEditable(boolean blnEditable){
        java.awt.Color colBack = txtCot.getBackground();
        
        txtCot.setEditable(blnEditable);
        txtCot.setBackground(colBack);
        
        colBack = txtSub.getBackground();
        
        txtNomForPag.setEditable(blnEditable);
        
        txtSub.setEditable(blnEditable);
        txtSub.setBackground(colBack);
        
        txtIva.setEditable(blnEditable);
        txtIva.setBackground(colBack);
        
        txtTot.setEditable(blnEditable);
        txtTot.setBackground(colBack);
        
         txtCliDir.setEditable(blnEditable);
         txtCliDir.setBackground(colBack);
         
    }
    
    public void  clnTextos(){
        if(blnLimpiar){
            //Cabecera
            txtCot.setText("");
            txtCliCod.setText("");
            txtCliNom.setText("");
            txtCliDir.setText("");
            txtFecDoc.setText("");
            txtVenCod.setText("");
            txtVenNom.setText("");
            txtAte.setText("");
            tx_numped.setText("");
            strCodPrv="";
            strDesLarPrv="";
            strCodCom="";
            strDesLarCom="";
            
           
            
            //Detalle
            objTblMod.removeAllRows();
            
            //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            txtTot.setText("0");
            lblCotNumDes.setText("Cotizaciones");
            
            //Detalle  de PAGOS
            while(tblCotForPag.getRowCount()>0)
                ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).removeRow(0);
        }
    }
    
    
    public void  calculaTot(){
        
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        
        for (int i=0;i<tblDat.getRowCount();i++){
            dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
            dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
            
            
            if(tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)
                dblIva = dblIva + 0;
            else{
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
            }
            
        }
        
        /////////////////////////////////////////////////////////////
        dblSubtotalCot = dblSub;
        dblIvaCot = objUti.redondear(dblIva,intNumDec);
        dblTotalCot = dblSubtotalCot + dblIvaCot;
        dblTotalCot = objUti.redondear(dblTotalCot ,intNumDec);
        dblSubtotalCot = objUti.redondear(dblSubtotalCot ,intNumDec);
        
        txtSub.setText( "" + dblSubtotalCot );
        txtIva.setText( "" + dblIvaCot );
        txtTot.setText( ""+ dblTotalCot);
        /////////////////////////////////////////////////////////////
        
    }
    
    
    
    
    
    
    public String getUnidad(String int_co_itm){
        String strDesUni = "";
        try{
            if (CONN_GLO!=null){
                java.sql.Statement stmUni = CONN_GLO.createStatement();
                String sSQL= "SELECT var.tx_descor from tbm_inv as inv " +
                " inner join tbm_var as var on (inv.co_uni = var.co_reg) "+
                " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                "   var.co_grp = 5  and inv.co_itm = " + int_co_itm ;
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next())
                    strDesUni = (rstUni.getString("tx_descor")==null)?"":rstUni.getString("tx_descor");
                    rstUni.close();
                    stmUni.close();
                    rstUni = null;
                    stmUni = null;
            }
        }
        catch(java.sql.SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  return strDesUni+"";  }
        catch(Exception Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt); return strDesUni+"";  }
        return strDesUni;
    }
    
    
    

    
    public void  refrescaDatos(){
        try{
            int intNumCot = 0;
            if (CONN_GLO!=null){
                
                
                String strAux = ",CASE " +
                " WHEN ((trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
                " ))) THEN 'S' " +
                "" +
                " WHEN ((trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
                " ))) THEN 'I' " +
                
                "ELSE 'N' END  as isterL";
                
                
                sSQL = "SELECT detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
                ",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
                ",detcot.tx_codalt , detcot.nd_pordesprecom,  var.tx_descor , inv.st_ser , cli.tx_nom, inv.nd_maruti ";
                
                sSQL +=strAux;
                sSQL +=" FROM tbh_detcotven as detcot " +
                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
                " WHERE detcot.co_emp="+vecEmp.elementAt(empresa.getSelectedIndex())+" and detcot.co_loc="+vecLoc.elementAt(local.getSelectedIndex())+" " +
                " and detcot.co_cot="+rstCab.getInt("co_cot")+"   and detcot.co_his="+rstCab.getInt("co_his")+"  order by detcot.co_reg";
                
                
                java.sql.Statement stmCab=CONN_GLO.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = CONN_GLO.createStatement();
                String strUnidad="",strCodAlt="",strSer="", strTer="", strMarUti="";
                Vector vecData = new Vector();
                for(int i=0 ; rst.next() ; i++){
                    java.util.Vector vecReg = new java.util.Vector();
                    
                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                    strMarUti = (rst.getString("nd_maruti")==null?"":rst.getString("nd_maruti"));
                    
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivaven");
                    Boolean blnIva = new Boolean(strIva.equals("S"));
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan    = rst.getDouble("nd_can");
                    dblPre    = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);
                    
                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, strIva );
                    vecReg.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")) );            //Columna que contiene  precio de compra
                    vecReg.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")) );
                    vecReg.add(INT_TBL_COD_PRO, new Integer(rst.getInt("co_prv")) );            //Columna que contiene el codigo del proveedor
                    strCodAlt = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_NOM_PRO, strCodAlt );
                    vecReg.add(INT_TBL_BUT_PRO,"");
                    vecReg.add(INT_TBL_BLNPRE,"");
                    
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    
                    vecReg.add(INT_TBL_ITMTER, strTer );
                    vecReg.add(INT_TBL_MARUTI, strMarUti);
                    
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                rst.close();
                calculaTot();
                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");
                
                
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */
                
                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;
                
                
                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra , co_tipret  from tbh_pagCotven where " +
                " co_emp = " + vecEmp.elementAt(empresa.getSelectedIndex()) + " and " +
                " co_loc = " + vecLoc.elementAt(local.getSelectedIndex())   + " and " +
                " co_cot = "+rstCab.getInt("co_cot")+"  and co_his = "+rstCab.getInt("co_his")+"  order by co_reg";
                
                rst = stmAux.executeQuery(sSQL);
                int i=0;
                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
                modTbl.setRowCount(0);
                
                while(rst.next()){
                    
                    ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
                    
                    tblCotForPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
                    tblCotForPag.setValueAt( rst.getString(2), i, 2);
                    tblCotForPag.setValueAt( rst.getString(3), i, 3);
                    tblCotForPag.setValueAt( new Double(rst.getString(4)) , i, 4);
                    tblCotForPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);
                    tblCotForPag.setValueAt(new Integer(rst.getInt("co_tipret")),  i, 6);
                    i++;
                }
                
                
                
                txtCodForPag.setText(""+strCo_ForPag);
                txtCodForPag2.setText(""+strCo_ForPag);
                txtNomForPag.setText(""+ rstCab.getString("tx_des"));
                
                CalculoPago2();
                
                
                
                /*
                 *  VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCab.getString("st_reg");
                if(strStatus.equals("I")){
                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
                    objUti.desactivarCom(jfrThis);
                }else{
                    if (objTooBar.getEstado()== 'm'){
                        objUti.activarCom(jfrThis);
                        noEditable(false);
                    }
                }
                stmAux.close();
                stmCab.close();
                
            }
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            
        }//fin Try
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            
        }
        
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        
    }
    
    
    
    public void  refrescaDatosLoc(){
        try{
            int intNumCot = 0;
            if (CONN_GLO!=null){
                
                
                String strAux = ",CASE " +
                " WHEN ((trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
                " ))) THEN 'S' " +
                "" +
                " WHEN ((trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
                " ))) THEN 'I' " +
                " ELSE 'N' END  as isterL";
                
                
                sSQL = "SELECT detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
                ",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
                ",detcot.tx_codalt , detcot.nd_pordesprecom,  var.tx_descor , inv.st_ser , cli.tx_nom";
                
                sSQL +=strAux;
                sSQL +=" FROM tbm_detcotven as detcot " +
                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
                " WHERE detcot.co_emp="+objZafParSis.getCodigoEmpresa()+" and detcot.co_loc=1 " +
                " and detcot.co_cot="+rstCab.getInt("co_cot")+" order by detcot.co_reg";
                
                
                java.sql.Statement stmCab=CONN_GLO.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = CONN_GLO.createStatement();
                String strUnidad="",strCodAlt="",strSer="", strTer="";
                Vector vecData = new Vector();
                for(int i=0 ; rst.next() ; i++){
                    java.util.Vector vecReg = new java.util.Vector();
                    
                    
                    
                    
                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                    
                    
                    
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivaven");
                    Boolean blnIva = new Boolean(strIva.equals("S"));
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan    = rst.getDouble("nd_can");
                    dblPre    = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);
                    
                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, strIva );
                    vecReg.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")) );            //Columna que contiene  precio de compra
                    vecReg.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")) );
                    vecReg.add(INT_TBL_COD_PRO, new Integer(rst.getInt("co_prv")) );            //Columna que contiene el codigo del proveedor
                    strCodAlt = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_NOM_PRO, strCodAlt );
                    vecReg.add(INT_TBL_BUT_PRO,"");
                    vecReg.add(INT_TBL_BLNPRE,"");
                    
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    
                    vecReg.add(INT_TBL_ITMTER, strTer );
                    
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                rst.close();
                calculaPag();
                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");
                
                
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */
                
                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;
                
                
                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra , co_tipret  from tbm_pagCotven where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                " co_loc = 1  and " +
                " co_cot = "+rstCab.getInt("co_cot")+" order by co_reg";
                
                rst = stmAux.executeQuery(sSQL);
                int i=0;
                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
                modTbl.setRowCount(0);
                
                while(rst.next()){
                    
                    ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
                    
                    tblCotForPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
                    tblCotForPag.setValueAt( rst.getString(2), i, 2);
                    tblCotForPag.setValueAt( rst.getString(3), i, 3);
                    tblCotForPag.setValueAt( new Double(rst.getString(4)) , i, 4);
                    tblCotForPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);
                    tblCotForPag.setValueAt(new Integer(rst.getInt("co_tipret")),  i, 6);
                    i++;
                }
                
                
                
                txtCodForPag.setText(""+strCo_ForPag);
                txtCodForPag2.setText(""+strCo_ForPag);
                txtNomForPag.setText(""+ rstCab.getString("tx_des"));
                
                CalculoPago2();
                
                
                
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCab.getString("st_reg");
                if(strStatus.equals("I")){
                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
                    objUti.desactivarCom(jfrThis);
                }else{
                    if (objTooBar.getEstado()== 'm'){
                        objUti.activarCom(jfrThis);
                        noEditable(false);
                    }
                }
                stmAux.close();
                stmCab.close();
                
            }
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            
        }//fin Try
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            
        }
        
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        
    }
    
    
          
       
      
    public void  refrescaDatos2_anterior(Integer CodEmp,Integer CodLoc){
        try{
            int intNumCot = 0;
            if (CONN_GLO!=null){
                
                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";
                
                sSQL = "SELECT detcot.co_emp, detcot.co_reg,  detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
                ", detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
                ",detcot.tx_codalt , detcot.nd_pordesprecom,  var.tx_descor , inv.st_ser , cli.tx_nom";
                  
                sSQL +=strAux;
                sSQL +=" FROM tbm_detcotven as detcot " +
                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
                " WHERE detcot.co_emp="+CodEmp+" and detcot.co_loc="+CodLoc+" " +
                " and detcot.co_cot="+rstCab.getInt("co_cot")+" order by detcot.co_reg";

                /*
                String sSQL1 = " select *  from ( " +
             
                "" +
                " SELECT  detcot.co_emp, detcot.co_reg,  detcot.tx_nomitm as tx_nomitmh, detcot.co_bod as co_bodh  ,case when detcot.nd_can is null then 0 else detcot.nd_can end as nd_canh ," +
                " case when detcot.nd_preuni is null then 0 else detcot.nd_preuni end   as nd_preunih " +
                 " , case when detcot.nd_pordes is null then 0 else detcot.nd_pordes end     as nd_pordesh ,detcot.st_ivaven as st_ivavenh  ,detcot.co_itm as co_itmh  ,case when detcot.nd_precom is null then 0 else detcot.nd_precom end as nd_precomh " +
                 " ,case when detcot.co_prv is null then 0 else detcot.co_prv end as co_prvh ,detcot.tx_codalt2 as tx_codalt2h ,detcot.tx_codalt as tx_codalth , case when detcot.nd_pordesprecom is null then 0 else detcot.nd_pordesprecom end  as nd_pordesprecomh, " +
                 "  var.tx_descor as tx_descorh , inv.st_ser as st_serh , cli.tx_nom as tx_nomh ";
                sSQL1 +=strAux;
                sSQL1 +=" FROM tbh_detcotven as detcot " +
                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
                " WHERE detcot.co_emp="+CodEmp+" and detcot.co_loc="+CodLoc+" " +
                " and detcot.co_cot="+rstCab.getInt("co_cot")+" and detcot.co_his="+rstCab.getInt("co_his")+" order by detcot.co_reg";
                sSQL1 +=" ) as x ";  //, (" + sSQL + " ) as  x1 ) as x ";
               */
                
                java.sql.Statement stmCab = CONN_GLO.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = CONN_GLO.createStatement();
                String strUnidad="",strCodAlt="",strSer="", strTer="", strNomPrv="";
                Vector vecData = new Vector();
                   
                Vector vecDataHis = new Vector();
                  
                for(int i=0 ; rst.next() ; i++){
                    java.util.Vector vecReg = new java.util.Vector();
                    
//                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
//                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
//                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
//                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
//
                    String strIva = ( rst.getString("st_ivaven")==null?"N":rst.getString("st_ivaven"));
                    Boolean blnIva = new Boolean( strIva.equals("S"));
//
//                    dblCan    = Double.parseDouble( rst.getString("nd_can")==null?"0":rst.getString("nd_can"));
//                    dblPre    = Double.parseDouble( rst.getString("nd_preuni")==null?"0":rst.getString("nd_preuni"));
//                    dblPorDes = Double.parseDouble( rst.getString("nd_pordes")==null?"0":rst.getString("nd_pordes"));
//
//                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
//                    dblTotal  = (dblCan * dblPre)- dblValDes ;
//                    dblTotal =  objUti.redondear(dblTotal,3);
//                    dblTotal =  objUti.redondear(dblTotal,2);
//
//                    strNomPrv = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));

                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, ""); // (rst.getString("tx_nomitm")==null?"":rst.getString("tx_nomitm")) );
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, ""); // (rst.getString("co_bod")==null?"":rst.getString("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_CANMOV, ""); // new Double ( Double.parseDouble( rst.getString("nd_can")==null?"0":rst.getString("nd_can")) ) );
                    vecReg.add(INT_TBL_PREUNI, ""); // new Double ( Double.parseDouble( rst.getString("nd_preuni")==null?"0":rst.getString("nd_preuni")) ) );
                    vecReg.add(INT_TBL_PORDES, ""); // new Double ( Double.parseDouble( rst.getString("nd_pordes")==null?"0":rst.getString("nd_pordes")) ) );
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    vecReg.add(INT_TBL_TOTAL, ""); // new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, ""); // rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, strIva );
                    vecReg.add(INT_TBL_PRE_COS, ""); //  new Double ( Double.parseDouble(  rst.getString("nd_precom")==null?"0":rst.getString("nd_precom")) ) );            //Columna que contiene  precio de compra
                    vecReg.add(INT_TBL_DESPRECOM,  ""); //  new Double ( Double.parseDouble(  rst.getString("nd_pordesprecom")==null?"0":rst.getString("nd_pordesprecom")) )  );
                    vecReg.add(INT_TBL_COD_PRO, ""); // (rst.getString("co_prv")==null?"":rst.getString("co_prv"))   );
                    vecReg.add(INT_TBL_NOM_PRO, strNomPrv );            //Columna que contiene el Nombre del proveedor
                    vecReg.add(INT_TBL_BUT_PRO,"");               //Columna que contiene para busqueda del proveedor
                    vecReg.add(INT_TBL_BLNPRE,"");
                    vecReg.add(INT_TBL_ITMALT2, ""); // rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_ITMTER, strTer );
                    vecReg.add(INT_TBL_MARUTI, "");


                    vecData.add(vecReg);
                
                //********************Historico*****************************************************//
                    
                       //********************Historico*****************************************************//
                    /*
                    java.util.Vector vecRegHis = new java.util.Vector();
                    
                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                      
                    vecRegHis.add(INT_TBL_LINEA, "");
                    vecRegHis.add(INT_TBL_ITMALT, strCodAlt);
                    vecRegHis.add(INT_TBL_BUTITM, "");
                    vecRegHis.add(INT_TBL_DESITM, rst.getString("tx_nomitm") );
                    vecRegHis.add(INT_TBL_UNIDAD, strUnidad);
                    vecRegHis.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecRegHis.add(INT_TBL_BUTBOD, "");
                    vecRegHis.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                    vecRegHis.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    vecRegHis.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIvaHis = rst.getString("st_ivaven");
                  //  Boolean blnIvaHis = new Boolean(strIvaHis.equals("S"));
                    vecRegHis.add(INT_TBL_BLNIVA, " ");
                    dblCan    = rst.getDouble("nd_can");
                    dblPre    = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);
                    
                    vecRegHis.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecRegHis.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecRegHis.add(INT_TBL_ESTADO, "E");
                    vecRegHis.add(INT_TBL_IVATXT, strIvaHis );
                    vecRegHis.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")) );            //Columna que contiene  precio de compra
                      
                    vecRegHis.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")) );
                    
                    vecRegHis.add(INT_TBL_COD_PRO, new Integer(rst.getInt("co_prv")) );            //Columna que contiene el codigo del proveedor
                    
                    strCodAlt = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
                    
                    vecRegHis.add(INT_TBL_NOM_PRO, strCodAlt );            //Columna que contiene el Nombre del proveedor
                    vecRegHis.add(INT_TBL_BUT_PRO,"");               //Columna que contiene para busqueda del proveedor
                    vecRegHis.add(INT_TBL_BLNPRE,"");
                    vecRegHis.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2h"));
                    vecRegHis.add(INT_TBL_ITMSER, strSer );
                    vecRegHis.add(INT_TBL_ITMTER, strTer );
                    vecRegHis.add(INT_TBL_MARUTI, "");
                     
                    vecDataHis.add(vecRegHis);
                  */
                                
                }
                
                objTblMod.setData(vecData);

               //**** objTblModHis.setData(vecDataHis);


                tblDat .setModel(objTblMod);
                
               //**** tblDatHis .setModel(objTblModHis);
                
                //stmAux.close();
                rst.close();
                calculaTot();
                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");
                
            //****   compararDatDetHis();
                
                
                
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */  
                
                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;
                
                
                //Extrayendo los datos del detalle respectivo a ESTE PAGO
                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra  from tbm_pagCotven where " +
                " co_emp = " + CodEmp + " and " +
                " co_loc = " + CodLoc   + " and " +
                " co_cot = "+rstCab.getInt("co_cot")+" order by co_reg";
                
                rst = stmAux.executeQuery(sSQL);
                int i=0;
                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
                modTbl.setRowCount(0);
                
                while(rst.next()){
                    
                    
                    ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
                    
                    tblCotForPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
                    tblCotForPag.setValueAt( rst.getString(2), i, 2);
                    tblCotForPag.setValueAt( rst.getString(3), i, 3);
                    tblCotForPag.setValueAt( new Double(rst.getString(4)) , i, 4);
                    tblCotForPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);
                    
                    i++;
                }
                
                
                
                    /*
                     * LLenando el combo de descripciones de pagos
                     *
                     */
                
                txtCodForPag.setText(""+strCo_ForPag);
                txtCodForPag2.setText(""+strCo_ForPag);
                txtNomForPag.setText(""+ rstCab.getString("tx_des"));



              //***  cambiaColText(txtNomForPag,rstCab.getString("tx_des"),rstCab.getString("tx_desh"));
             //***  cambiaColText(txtSub, rstCab.getString("nd_sub").trim() ,rstCab.getString("nd_subh").trim());
             //**   cambiaColText(txtIva, rstCab.getString("nd_valiva").trim() ,rstCab.getString("nd_valivah").trim());
                
             //   System.out.println("Abc==>> "+ rstCab.getString("nd_sub") +" --  "+ rstCab.getString("nd_sub")  );
              
            //**** cambiaColText(txtTot,rstCab.getString("nd_tot").trim(),rstCab.getString("nd_toth").trim());
                
                
              //  CalculoPago2();
                
                 
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCab.getString("st_reg");
                if(strStatus.equals("I")){
                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
                    objUti.desactivarCom(jfrThis);
                }else{
                    if (objTooBar.getEstado()== 'm'){
                        objUti.activarCom(jfrThis);
                        noEditable(false);
                    }
                }
                stmAux.close();
                stmCab.close();
                
            }
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
         //   intControlValTbl=1; 
        }
        catch(SQLException Evt) {
            System.out.println(""+Evt.toString());
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        
        catch(Exception Evt) {
            System.out.println(""+Evt.toString());
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        
    }



    public void  refrescaDatos3(Integer CodEmp,Integer CodLoc, Integer CodHis, int intCodHis2 ){
           try{
            double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
            String strUnidad="",strCodAlt="",strSer="", strTer="", strNomPrv="";

            int intNumCot = 0;
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;


            Vector vecData = new Vector();
            Vector vecDataHis = new Vector();

            if (CONN_GLO!=null){

                java.sql.Statement stmCab = CONN_GLO.createStatement();
                stmAux = CONN_GLO.createStatement();

                 String strAuxCan="";

               strAuxCan=" ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+"  ) is null "+
               " then '' else "+
               "   case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+" and x.co_itm=detcot.co_itm ) is null  "+
               "   then '*' else '' end "+
               "    end as coitmCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+"  ) is null "+
               " then '' else "+
               "   case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+" and x.co_itm=detcot.co_itm and x.nd_preuni=detcot.nd_preuni ) is null   "+
               "   then '*' else '' end "+
               "    end as preuniCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+"  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+" and x.co_itm=detcot.co_itm and x.nd_pordes=detcot.nd_pordes ) is null "+
               "   then '*' else '' end "+
               "    end as pordesCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+"  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+" and x.co_itm=detcot.co_itm and x.tx_nomitm=detcot.tx_nomitm ) is null "+
               "   then '*' else '' end "+
               "    end as desItmCan "+


               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+"  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+" and x.co_itm=detcot.co_itm and x.nd_can=detcot.nd_can ) is null "+
               "   then '*' else '' end "+
               "    end as canMovCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+"  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his="+intCodHis2+" and x.co_itm=detcot.co_itm and   ((x.nd_preuni-(x.nd_preuni*(x.nd_pordes/100)))*x.nd_can) = ((detcot.nd_preuni-(detcot.nd_preuni*(detcot.nd_pordes/100)))*detcot.nd_can)  ) is null "+
               "   then '*' else '' end "+
               "    end as canTotCan ";



                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";

                sSQL = "SELECT detcot.co_emp, detcot.co_reg,  detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
                ", detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
                ",detcot.tx_codalt , detcot.nd_pordesprecom,  var.tx_descor , inv.st_ser , cli.tx_nom";

                sSQL +=strAux;
                sSQL +=strAuxCan;
                sSQL +=" FROM tbh_detcotven as detcot " +
                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
                " WHERE detcot.co_emp="+CodEmp+" and detcot.co_loc="+CodLoc+" " +
                " and detcot.co_cot="+rstCab.getInt("co_coth")+" and detcot.co_his="+CodHis+" order by detcot.co_reg";


                System.out.println("Det==> "+sSQL );


                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);


                while( rst.next() ){
                    java.util.Vector vecReg = new java.util.Vector();

                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));

                    String strIva = ( rst.getString("st_ivaven")==null?"N":rst.getString("st_ivaven"));
                    Boolean blnIva = new Boolean( strIva.equals("S"));


                    dblCan    = Double.parseDouble( rst.getString("nd_can")==null?"0":rst.getString("nd_can"));
                    dblPre    = Double.parseDouble( rst.getString("nd_preuni")==null?"0":rst.getString("nd_preuni"));
                    dblPorDes = Double.parseDouble( rst.getString("nd_pordes")==null?"0":rst.getString("nd_pordes"));
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);

                    strNomPrv = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));



                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt );
                    vecReg.add(INT_TBL_BUTITM, "..");
                    vecReg.add(INT_TBL_DESITM,  (rst.getString("tx_nomitm")==null?"":rst.getString("tx_nomitm")) );
                    vecReg.add(INT_TBL_UNIDAD, strUnidad );
                    vecReg.add(INT_TBL_CODBOD, (rst.getString("co_bod")==null?"":rst.getString("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "..");
                    vecReg.add(INT_TBL_CANMOV,  new Double ( Double.parseDouble( rst.getString("nd_can")==null?"0":rst.getString("nd_can")) ) );
                    vecReg.add(INT_TBL_PREUNI,  new Double ( Double.parseDouble( rst.getString("nd_preuni")==null?"0":rst.getString("nd_preuni")) ) );
                    vecReg.add(INT_TBL_PORDES,  new Double ( Double.parseDouble( rst.getString("nd_pordes")==null?"0":rst.getString("nd_pordes")) ) );
                    vecReg.add(INT_TBL_BLNIVA, blnIva );
                    vecReg.add(INT_TBL_TOTAL,  new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM,  rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, blnIva );
                    vecReg.add(INT_TBL_PRE_COS,   new Double ( Double.parseDouble(  rst.getString("nd_precom")==null?"0":rst.getString("nd_precom")) ) );            //Columna que contiene  precio de compra
                    vecReg.add(INT_TBL_DESPRECOM,   new Double ( Double.parseDouble(  rst.getString("nd_pordesprecom")==null?"0":rst.getString("nd_pordesprecom")) )  );
                    vecReg.add(INT_TBL_COD_PRO,   (rst.getString("co_prv")==null?"":rst.getString("co_prv"))   );
                    vecReg.add(INT_TBL_NOM_PRO, strNomPrv );            //Columna que contiene el Nombre del proveedor
                    vecReg.add(INT_TBL_BUT_PRO,"..");               //Columna que contiene para busqueda del proveedor
                    vecReg.add(INT_TBL_BLNPRE,"");
                    vecReg.add(INT_TBL_ITMALT2,  rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_ITMTER, strTer );
                    vecReg.add(INT_TBL_MARUTI, "");


                    vecReg.add(INT_TBL_CANITM, rst.getString("coitmCan"));
                    vecReg.add(INT_TBL_CANPRE, rst.getString("preuniCan"));
                    vecReg.add(INT_TBL_CANPOR, rst.getString("pordesCan"));
                    vecReg.add(INT_TBL_CANDITM, rst.getString("desItmCan"));
                    vecReg.add(INT_TBL_CANCAN, rst.getString("canMovCan"));
                    vecReg.add(INT_TBL_CANTOT, rst.getString("canTotCan"));





                    vecData.add(vecReg);

                 


                }



                objTblMod.setData(vecData);
               

                tblDat .setModel(objTblMod);
               


                rst.close();
                rst=null;

                calculaTot();
                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");

            //****   compararDatDetHis();



                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */

                String strCo_ForPag = (rstCab.getString("co_forPagh")==null)?"":rstCab.getString("co_forPagh") ;


                //Extrayendo los datos del detalle respectivo a ESTE PAGO
                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra  from tbh_pagCotven where " +
                " co_emp = " + CodEmp + " and " +
                " co_loc = " + CodLoc   + " and " +
                " co_cot = "+rstCab.getInt("co_coth")+" and co_his="+CodHis+"  order by co_reg";

                rst = stmAux.executeQuery(sSQL);
                int i=0;
                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
                modTbl.setRowCount(0);

                while(rst.next()){


                    ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());

                    tblCotForPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
                    tblCotForPag.setValueAt( rst.getString(2), i, 2);
                    tblCotForPag.setValueAt( rst.getString(3), i, 3);
                    tblCotForPag.setValueAt( new Double(rst.getString(4)) , i, 4);
                    tblCotForPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);

                    i++;
                }



                    /*
                     * LLenando el combo de descripciones de pagos
                     *
                     */

                txtCodForPag.setText(""+strCo_ForPag);
                txtCodForPag2.setText(""+strCo_ForPag);
                txtNomForPag.setText(""+ rstCab.getString("tx_desh"));



               cambiaColText(txtNomForPag,rstCab.getString("tx_des"),rstCab.getString("tx_desh"));
               cambiaColText(txtSub, rstCab.getString("nd_sub").trim() ,rstCab.getString("nd_subh").trim());
               cambiaColText(txtIva, rstCab.getString("nd_valiva").trim() ,rstCab.getString("nd_valivah").trim());
               cambiaColText(txtTot,rstCab.getString("nd_tot").trim(),rstCab.getString("nd_toth").trim());


              //  CalculoPago2();


                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCab.getString("st_regh");
                if(strStatus.equals("I")){
                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
                    objUti.desactivarCom(jfrThis);
                }else{
                    if (objTooBar.getEstado()== 'm'){
                        objUti.activarCom(jfrThis);
                        noEditable(false);
                    }
                }
                stmAux.close();
                stmCab.close();

            }
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
         //   intControlValTbl=1;
        }
        catch(SQLException Evt) {
            System.out.println(""+Evt.toString());
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }

        catch(Exception Evt) {
            System.out.println(""+Evt.toString());
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }

    }



       public void  refrescaDatos2(Integer CodEmp,Integer CodLoc){
           try{
            double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
            String strUnidad="",strCodAlt="",strSer="", strTer="", strNomPrv="";
            
            int intNumCot = 0;
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;


            Vector vecData = new Vector();
            Vector vecDataHis = new Vector();

            if (CONN_GLO!=null){

                java.sql.Statement stmCab = CONN_GLO.createStatement();
                stmAux = CONN_GLO.createStatement();


               String strAuxCan="";

               strAuxCan=" ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1  ) is null "+
               " then '' else "+
               "   case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1 and x.co_itm=detcot.co_itm ) is null  "+
               "   then '*' else '' end "+
               "    end as coitmCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1  ) is null "+
               " then '' else "+
               "   case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1 and x.co_itm=detcot.co_itm and x.nd_preuni=detcot.nd_preuni ) is null   "+
               "   then '*' else '' end "+
               "    end as preuniCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1 and x.co_itm=detcot.co_itm and x.nd_pordes=detcot.nd_pordes ) is null "+
               "   then '*' else '' end "+
               "    end as pordesCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1 and x.co_itm=detcot.co_itm and x.tx_nomitm=detcot.tx_nomitm ) is null "+
               "   then '*' else '' end "+
               "    end as desItmCan "+


               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1 and x.co_itm=detcot.co_itm and x.nd_can=detcot.nd_can ) is null "+
               "   then '*' else '' end "+
               "    end as canMovCan "+

               " ,case when  ( select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1  ) is null "+
               " then '' else "+
               "  case when  (select distinct(x.co_emp) from tbh_detcotven as x where x.co_emp=detcot.co_emp and x.co_loc=detcot.co_loc and x.co_cot=detcot.co_cot and x.co_his=1 and x.co_itm=detcot.co_itm and   ((x.nd_preuni-(x.nd_preuni*(x.nd_pordes/100)))*x.nd_can) = ((detcot.nd_preuni-(detcot.nd_preuni*(detcot.nd_pordes/100)))*detcot.nd_can)  ) is null "+
               "   then '*' else '' end "+
               "    end as canTotCan ";







                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";

                sSQL = "SELECT detcot.co_emp, detcot.co_reg,  detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
                ", detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
                ",detcot.tx_codalt , detcot.nd_pordesprecom,  var.tx_descor , inv.st_ser , cli.tx_nom";

                sSQL +=strAux;
                sSQL +=strAuxCan;
                sSQL +=" FROM tbm_detcotven as detcot " +
                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
                " WHERE detcot.co_emp="+CodEmp+" and detcot.co_loc="+CodLoc+" " +
                " and detcot.co_cot="+rstCab.getInt("co_cot")+" order by detcot.co_reg ";

    
                System.out.println("Det==> "+sSQL );
              
                
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
               

                while( rst.next() ){
                    java.util.Vector vecReg = new java.util.Vector();

                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));

                    String strIva = ( rst.getString("st_ivaven")==null?"N":rst.getString("st_ivaven"));
                    Boolean blnIva = new Boolean( strIva.equals("S"));
                   

                    dblCan    = Double.parseDouble( rst.getString("nd_can")==null?"0":rst.getString("nd_can"));
                    dblPre    = Double.parseDouble( rst.getString("nd_preuni")==null?"0":rst.getString("nd_preuni"));
                    dblPorDes = Double.parseDouble( rst.getString("nd_pordes")==null?"0":rst.getString("nd_pordes"));
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);

                    strNomPrv = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));

                   

                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt );
                    vecReg.add(INT_TBL_BUTITM, "..");
                    vecReg.add(INT_TBL_DESITM,  (rst.getString("tx_nomitm")==null?"":rst.getString("tx_nomitm")) );
                    vecReg.add(INT_TBL_UNIDAD, strUnidad );
                    vecReg.add(INT_TBL_CODBOD, (rst.getString("co_bod")==null?"":rst.getString("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "..");
                    vecReg.add(INT_TBL_CANMOV,  new Double ( Double.parseDouble( rst.getString("nd_can")==null?"0":rst.getString("nd_can")) ) );
                    vecReg.add(INT_TBL_PREUNI,  new Double ( Double.parseDouble( rst.getString("nd_preuni")==null?"0":rst.getString("nd_preuni")) ) );
                    vecReg.add(INT_TBL_PORDES,  new Double ( Double.parseDouble( rst.getString("nd_pordes")==null?"0":rst.getString("nd_pordes")) ) );
                    vecReg.add(INT_TBL_BLNIVA, blnIva );
                    vecReg.add(INT_TBL_TOTAL,  new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM,  rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, blnIva );
                    vecReg.add(INT_TBL_PRE_COS,   new Double ( Double.parseDouble(  rst.getString("nd_precom")==null?"0":rst.getString("nd_precom")) ) );            //Columna que contiene  precio de compra
                    vecReg.add(INT_TBL_DESPRECOM,   new Double ( Double.parseDouble(  rst.getString("nd_pordesprecom")==null?"0":rst.getString("nd_pordesprecom")) )  );
                    vecReg.add(INT_TBL_COD_PRO,   (rst.getString("co_prv")==null?"":rst.getString("co_prv"))   );
                    vecReg.add(INT_TBL_NOM_PRO, strNomPrv );            //Columna que contiene el Nombre del proveedor
                    vecReg.add(INT_TBL_BUT_PRO,"..");               //Columna que contiene para busqueda del proveedor
                    vecReg.add(INT_TBL_BLNPRE,"");
                    vecReg.add(INT_TBL_ITMALT2,  rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_ITMTER, strTer );
                    vecReg.add(INT_TBL_MARUTI, "");

                    vecReg.add(INT_TBL_CANITM, rst.getString("coitmCan"));
                    vecReg.add(INT_TBL_CANPRE, rst.getString("preuniCan"));
                    vecReg.add(INT_TBL_CANPOR, rst.getString("pordesCan"));
                    vecReg.add(INT_TBL_CANDITM, rst.getString("desItmCan"));
                    vecReg.add(INT_TBL_CANCAN, rst.getString("canMovCan"));
                    vecReg.add(INT_TBL_CANTOT, rst.getString("canTotCan"));





                    vecData.add(vecReg);

                    

               
                }

               

                objTblMod.setData(vecData);
               

                tblDat .setModel(objTblMod);
                

              
                rst.close();
                rst=null;
                
                calculaTot();
                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");

             compararDatDetHis();



                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */

                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;


                //Extrayendo los datos del detalle respectivo a ESTE PAGO
                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra  from tbm_pagCotven where " +
                " co_emp = " + CodEmp + " and " +
                " co_loc = " + CodLoc   + " and " +
                " co_cot = "+rstCab.getInt("co_cot")+" order by co_reg";

                rst = stmAux.executeQuery(sSQL);
                int i=0;
                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
                modTbl.setRowCount(0);

                while(rst.next()){

                    ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());

                    tblCotForPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
                    tblCotForPag.setValueAt( rst.getString(2), i, 2);
                    tblCotForPag.setValueAt( rst.getString(3), i, 3);
                    tblCotForPag.setValueAt( new Double(rst.getString(4)) , i, 4);
                    tblCotForPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);

                    i++;
                }



                    /*
                     * LLenando el combo de descripciones de pagos
                     *
                     */

                txtCodForPag.setText(""+strCo_ForPag);
                txtCodForPag2.setText(""+strCo_ForPag);
                txtNomForPag.setText(""+ rstCab.getString("tx_des"));

//
//
//              //***  cambiaColText(txtNomForPag,rstCab.getString("tx_des"),rstCab.getString("tx_desh"));
//             //***  cambiaColText(txtSub, rstCab.getString("nd_sub").trim() ,rstCab.getString("nd_subh").trim());
//             //**   cambiaColText(txtIva, rstCab.getString("nd_valiva").trim() ,rstCab.getString("nd_valivah").trim());
//
//             //   System.out.println("Abc==>> "+ rstCab.getString("nd_sub") +" --  "+ rstCab.getString("nd_sub")  );
//
//            //**** cambiaColText(txtTot,rstCab.getString("nd_tot").trim(),rstCab.getString("nd_toth").trim());
//
//
//              //  CalculoPago2();
//
//
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCab.getString("st_reg");
                if(strStatus.equals("I")){
                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
                    objUti.desactivarCom(jfrThis);
                }else{
                    if (objTooBar.getEstado()== 'm'){
                        objUti.activarCom(jfrThis);
                        noEditable(false);
                    }
                }
                stmAux.close();
                stmCab.close();

            }
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
         //   intControlValTbl=1;
        }
        catch(SQLException Evt) {
            System.out.println(""+Evt.toString());
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }

        catch(Exception Evt) {
            System.out.println(""+Evt.toString());
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }

    }


      
          
    
    private void compararDatDetHis(){
        
         for(int i=0; i<tblDat.getRowCount();i++){
             if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                   tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODITM).toString(), i, INT_TBL_CODITM );    
                    
                   // tblDat.setEditingRow(1;
                    
         }}
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
                        blnRes=objTooBar.modificar();
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
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.  
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() {
        boolean blnRes=true;
        try {
            if (cargarCabReg()) {
                refrescaDatos();
            }
            else {
                MensajeInf("Error al cargar registro");
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
    private boolean cargarRegLoc() {
        boolean blnRes=true;
        try {
            if (cargarCabRegLoc()) {
                refrescaDatosLoc();
            }
            else {
                MensajeInf("Error al cargar registro");
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    private boolean cargarReg2(Integer CodEmp,Integer CodLoc) {
        boolean blnRes=true;
        try {
            
            if (cargarCabReg()) {
                refrescaDatos2(CodEmp,CodLoc);
            }
            else {
                MensajeInf("Error al cargar registro");
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            
        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }


     private boolean cargarReg3(Integer CodEmp,Integer CodLoc, Integer CodHis,  int intCodHis2 ) {
        boolean blnRes=true;
        try {

            if (cargarCabReg3()) {
                refrescaDatos3(CodEmp,CodLoc, CodHis,  intCodHis2 );
            }
            else {
                MensajeInf("Error al cargar registro");
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }

        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }

    
    private void cambiaColText(javax.swing.JTextField txtText , String strCom1, String strCom2){
        System.out.println("cambiaColText " + txtText.getText().toString() + " + " + strCom1 + " + " + strCom2 + " END ");
        java.awt.Color objCol = new java.awt.Color(249, 67, 6);
        java.awt.Font objFont = new java.awt.Font("SansSerif", 1, 14);
        //////////
        if(strCom1==null)
            strCom1="";
        if(strCom2==null)
            strCom2="";
        ////////
        
        if(!(strCom1.equals(strCom2))){
                 txtText.setFont(objFont);
                 txtText.setForeground(objCol);
        }
    }
    
    
    private void cambiaColTextAre(javax.swing.JTextArea txtText , String strCom1, String strCom2){
        java.awt.Color objCol = new java.awt.Color(249, 67, 6);
        java.awt.Font objFont = new java.awt.Font("SansSerif", 1, 14);
        //////////
        if(strCom1==null)
            strCom1="";
        if(strCom2==null)
            strCom2="";
        ////////
        if(!(strCom1.equals(strCom2))){
                 txtText.setFont(objFont);
                 txtText.setForeground(objCol);
        }
    }
    
     private void cambiaColTextFec(Librerias.ZafDate.ZafDatePicker txtTextFec , String strCom1, String strCom2){
        java.awt.Color objCol = new java.awt.Color(249, 67, 6);
        java.awt.Font objFont = new java.awt.Font("SansSerif", 1, 14);
        //////////
        if(strCom1==null)
            strCom1="";
        if(strCom2==null)
            strCom2="";
        ////////
        if(!(strCom1.equals(strCom2))){
                 txtTextFec.setFont(objFont);
                 txtTextFec.setForeground(objCol);
        } 
    }
    
    
    
    
    
    

    private boolean cargarCabReg3() {
        int intPosRel;
        boolean blnRes=true;
        try {
             java.awt.Color objCol = new java.awt.Color(249, 67, 6);
             java.awt.Font objFont = new java.awt.Font("SansSerif", 1, 14);

            txtCot.setText(rstCab.getString("co_coth"));
            txtCliCod.setText(((rstCab.getString("co_clih")==null)?"":rstCab.getString("co_clih")));
            txtCliNom.setText(((rstCab.getString("nomclih")==null)?"":rstCab.getString("nomclih")));
            txtCliDir.setText(((rstCab.getString("dirclih")==null)?"":rstCab.getString("dirclih")));

            txtcli_telefono.setText( rstCab.getString("tx_telh") );
            txtcli_identificacion.setText( rstCab.getString("tx_ideh") );
            txtcli_ciudad.setText( rstCab.getString("tx_desLarh") );
            tx_numped.setText( rstCab.getString("tx_numpedh") );

           // STR_ESTREG=rstCab.getString("st_regrep");

            strTipPer_glo=rstCab.getString("tx_tipperh");
            Glo_dlbMaxDes = rstCab.getDouble("nd_maxdesh");
            Glo_dlbMarUti = rstCab.getDouble("nd_marutih");
            strCodTipPerCli =  rstCab.getString("co_tipperh");

            intTipForPag =  Integer.parseInt( rstCab.getString("ne_tipforpagclih") );
            dblPes = Double.parseDouble( ((rstCab.getString("nd_pesclih")==null || rstCab.getString("nd_pesclih").equals("")  )?"0":rstCab.getString("nd_pesclih") ) );
            dblDiaGraCli = Double.parseDouble(((rstCab.getString("ne_diagrah")==null)?"0":rstCab.getString("ne_diagrah")));

            intTipForPag2 =  Integer.parseInt( rstCab.getString("ne_tipforpagh") );
            dblPes2 = Double.parseDouble( ((rstCab.getString("nd_pesh")==null)?"0":rstCab.getString("nd_pesh")) );

            //*****************
            intNumDiaVal = rstCab.getInt("ne_valh");
            dateFecCot = objUti.formatearFecha(rstCab.getDate("fe_coth"),"dd/MM/yyyy");
            intTipMod = 2;
            //*****************

            if(rstCab.getDate("fe_coth")==null){
                txtFecDoc.setText("");
            }else{
                java.util.Date dateObj = rstCab.getDate("fe_coth");
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                calObj.get(java.util.Calendar.MONTH)+1     ,
                calObj.get(java.util.Calendar.YEAR)        );
            }

            txtVenCod.setText(((rstCab.getString("co_venh")==null)?"":rstCab.getString("co_venh")));
            txtVenNom.setText(((rstCab.getString("nomcomh")==null)?"":rstCab.getString("nomcomh")));
            txtAte.setText(((rstCab.getString("tx_ateh")==null)?"":rstCab.getString("tx_ateh")));
            double dblSub = ((rstCab.getString("nd_subh")==null)?0:objUti.redondear(rstCab.getDouble("nd_subh"),objZafParSis.getDecimalesMostrar()));
            txtSub.setText(""+dblSub);
            //Pie de pagina
            txaObs1.setText(((rstCab.getString("tx_obs1h")==null)?"":rstCab.getString("tx_obs1h")));
            txaObs2.setText(((rstCab.getString("tx_obs2h")==null)?"":rstCab.getString("tx_obs2h")));

            

            cambiaColTextFec(txtFecDoc,rstCab.getString("fe_cot"),rstCab.getString("fe_coth"));
            cambiaColText(txtCliCod,rstCab.getString("co_cli"),rstCab.getString("co_clih"));
            cambiaColText(txtCliNom,rstCab.getString("nomcli"),rstCab.getString("nomclih"));
            cambiaColText(txtCliDir,(rstCab.getString("dircli")==null?"":rstCab.getString("dircli")) , (rstCab.getString("dirclih")==null?"":rstCab.getString("dirclih"))  );
            cambiaColText(tx_numped,rstCab.getString("tx_numped"),rstCab.getString("tx_numpedh"));
            cambiaColText(txtVenCod,rstCab.getString("co_ven"),rstCab.getString("co_venh"));
            cambiaColText(txtVenNom,rstCab.getString("nomcom"),rstCab.getString("nomcomh"));
            cambiaColText(txtAte,rstCab.getString("tx_ate"),rstCab.getString("tx_ateh"));
            cambiaColTextAre(txaObs1,rstCab.getString("tx_obs1"),rstCab.getString("tx_obs1h"));
            cambiaColTextAre(txaObs2,rstCab.getString("tx_obs2"),rstCab.getString("tx_obs2h"));




            //Mostrar el estado del registro.
            strAux=rstCab.getString("st_regh");
            strstReg_glo = strAux;
            if (strAux.equals("A"))
                strAux="Activo";
            else if (strAux.equals("I"))
                strAux="Anulado";
            else
                strAux="Otro";
            objTooBar.setEstadoRegistro(strAux);

            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
            blnHayCam=false;
        }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

    
    
    
    private boolean cargarCabReg() {
        int intPosRel;
        boolean blnRes=true;
        try {
             java.awt.Color objCol = new java.awt.Color(249, 67, 6);
             java.awt.Font objFont = new java.awt.Font("SansSerif", 1, 14);
             
            txtCot.setText(rstCab.getString("co_cot"));
            txtCliCod.setText(((rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli")));
            txtCliNom.setText(((rstCab.getString("nomcli")==null)?"":rstCab.getString("nomcli")));
            txtCliDir.setText(((rstCab.getString("dircli")==null)?"":rstCab.getString("dircli")));
           
            txtcli_telefono.setText( rstCab.getString("tx_tel") );
            txtcli_identificacion.setText( rstCab.getString("tx_ide") );
            txtcli_ciudad.setText( rstCab.getString("tx_desLar") );
            tx_numped.setText( rstCab.getString("tx_numped") );
            
            STR_ESTREG=rstCab.getString("st_regrep");
            
            strTipPer_glo=rstCab.getString("tx_tipper");
            Glo_dlbMaxDes = rstCab.getDouble("nd_maxdes");
            Glo_dlbMarUti = rstCab.getDouble("nd_maruti");
            strCodTipPerCli =  rstCab.getString("co_tipper");
            
            intTipForPag =  Integer.parseInt( rstCab.getString("ne_tipforpagcli") );
            dblPes = Double.parseDouble( ((rstCab.getString("nd_pescli")==null || rstCab.getString("nd_pescli").equals("")  )?"0":rstCab.getString("nd_pescli") ) );
            dblDiaGraCli = Double.parseDouble(((rstCab.getString("ne_diagra")==null)?"0":rstCab.getString("ne_diagra")));  
              
            intTipForPag2 =  Integer.parseInt( rstCab.getString("ne_tipforpag") );
            dblPes2 = Double.parseDouble( ((rstCab.getString("nd_pes")==null)?"0":rstCab.getString("nd_pes")) );
            
            //*****************
            intNumDiaVal = rstCab.getInt("ne_val");
            dateFecCot = objUti.formatearFecha(rstCab.getDate("fe_cot"),"dd/MM/yyyy");
            intTipMod = 2;
            //*****************
            
            if(rstCab.getDate("fe_cot")==null){
                txtFecDoc.setText("");
            }else{
                java.util.Date dateObj = rstCab.getDate("fe_cot");
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                calObj.get(java.util.Calendar.MONTH)+1     ,
                calObj.get(java.util.Calendar.YEAR)        );
            }
            
            txtVenCod.setText(((rstCab.getString("co_ven")==null)?"":rstCab.getString("co_ven")));
            txtVenNom.setText(((rstCab.getString("nomcom")==null)?"":rstCab.getString("nomcom")));
            txtAte.setText(((rstCab.getString("tx_ate")==null)?"":rstCab.getString("tx_ate")));
            double dblSub = ((rstCab.getString("nd_sub")==null)?0:objUti.redondear(rstCab.getDouble("nd_sub"),objZafParSis.getDecimalesMostrar()));
            txtSub.setText(""+dblSub);
            //Pie de pagina
            txaObs1.setText(((rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1")));
            txaObs2.setText(((rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2")));






              java.sql.Statement stmLoc;
              java.sql.ResultSet rstLoc;
              if(CONN_GLO!=null){

                stmLoc=CONN_GLO.createStatement();
                
                String strSql = "select  round(a.nd_sub,2) as nd_sub , round(a.nd_valiva,2) as nd_valiva , round(a.nd_tot,2) as nd_tot,  a3.tx_des,  a.co_cli, a2.tx_nom as nomcli , a2.tx_dir as dircli, a.co_ven,  a1.tx_nom as nomven from tbh_cabcotven as a" +
                " left outer join tbm_usr as a1 on (a1.co_usr = a.co_ven ) "+
                " inner join tbm_cli as a2 on (a2.co_emp = a.co_emp and a2.co_cli = a.co_cli) "+
                "  left outer join tbm_cabForPag as a3 on (a3.co_emp=a.co_emp and a3.co_forpag = a.co_forPag )  "+
                " where a.co_emp="+rstCab.getString("co_emp")+" and a.co_loc="+rstCab.getString("co_loc")+" and a.co_cot="+rstCab.getString("co_cot")+" and a.co_his=1 ";
                rstLoc =stmLoc.executeQuery(strSql);
                if(rstLoc.next()){

                     cambiaColText(txtCliCod,rstCab.getString("co_cli"),rstLoc.getString("co_cli"));
                     cambiaColText(txtCliNom,rstCab.getString("nomcli"),rstLoc.getString("nomcli"));
                     cambiaColText(txtCliDir,(rstCab.getString("dircli")==null?"":rstCab.getString("dircli")),(rstLoc.getString("dircli")==null?"":rstLoc.getString("dircli")) );
                     cambiaColText(txtVenCod,rstCab.getString("co_ven"),rstLoc.getString("co_ven"));
                     cambiaColText(txtVenNom,rstCab.getString("nomcom"),rstLoc.getString("nomven"));

                     cambiaColText(txtNomForPag,rstCab.getString("tx_des"),rstLoc.getString("tx_des"));
                     cambiaColText(txtSub, rstCab.getString("nd_sub").trim() ,rstLoc.getString("nd_sub").trim());
                     cambiaColText(txtIva, rstCab.getString("nd_valiva").trim() ,rstLoc.getString("nd_valiva").trim());
                     cambiaColText(txtTot,rstCab.getString("nd_tot").trim(),rstLoc.getString("nd_tot").trim());


                }
                rstLoc.close();
                rstLoc=null;
                 
              }



           /* cambiaColTextFec(txtFecDoc,rstCab.getString("fe_cot"),rstCab.getString("fe_coth"));
            cambiaColText(txtCliCod,rstCab.getString("co_cli"),rstCab.getString("co_clih"));
            cambiaColText(txtCliNom,rstCab.getString("nomcli"),rstCab.getString("nomclih"));
            cambiaColText(txtCliDir,rstCab.getString("dircli"),rstCab.getString("dirclih"));
            cambiaColText(tx_numped,rstCab.getString("tx_numped"),rstCab.getString("tx_numpedh"));
            cambiaColText(txtVenCod,rstCab.getString("co_ven"),rstCab.getString("co_venh"));
            cambiaColText(txtVenNom,rstCab.getString("nomcom"),rstCab.getString("nomcomh"));
            cambiaColText(txtAte,rstCab.getString("tx_ate"),rstCab.getString("tx_ateh"));
            cambiaColTextAre(txaObs1,rstCab.getString("tx_obs1"),rstCab.getString("tx_obs1h"));
            cambiaColTextAre(txaObs2,rstCab.getString("tx_obs2"),rstCab.getString("tx_obs2h"));
*/
            
            
            
            //Mostrar el estado del registro.
            strAux=rstCab.getString("st_reg");
            strstReg_glo = strAux;
            if (strAux.equals("A"))
                strAux="Activo";
            else if (strAux.equals("I"))
                strAux="Anulado";
            else
                strAux="Otro";
            objTooBar.setEstadoRegistro(strAux);
            
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
            blnHayCam=false;
        }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    
    
    
    
    
    
    private boolean cargarCabRegLoc() {
        int intPosRel;
        boolean blnRes=true;
        try {
            
            txtCot.setText(rstCab.getString("co_cot"));
            txtCliCod.setText(((rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli")));
            txtCliNom.setText(((rstCab.getString("nomcli")==null)?"":rstCab.getString("nomcli")));
            txtCliDir.setText(((rstCab.getString("dircli")==null)?"":rstCab.getString("dircli")));
                         
            txtcli_telefono.setText( rstCab.getString("tx_tel") );
            txtcli_identificacion.setText( rstCab.getString("tx_ide") );
            txtcli_ciudad.setText( rstCab.getString("tx_desLar") );
            tx_numped.setText( rstCab.getString("tx_numped") );
            
            
            STR_ESTREG=rstCab.getString("st_regrep");
            
            strTipPer_glo=rstCab.getString("tx_tipper");
            Glo_dlbMaxDes = rstCab.getDouble("nd_maxdes");
            Glo_dlbMarUti = rstCab.getDouble("nd_maruti");
            strCodTipPerCli =  rstCab.getString("co_tipper");
            
            intTipForPag =  Integer.parseInt( rstCab.getString("ne_tipforpagcli") );
            dblPes = Double.parseDouble( rstCab.getString("nd_pescli") );
            dblDiaGraCli = Double.parseDouble(((rstCab.getString("ne_diagra")==null)?"0":rstCab.getString("ne_diagra")));   
            
            intTipForPag2 =  Integer.parseInt( rstCab.getString("ne_tipforpag") );
            dblPes2 = Double.parseDouble( ((rstCab.getString("nd_pes")==null)?"0":rstCab.getString("nd_pes")) );
             
                
        //******************************************************************
            intNumDiaVal = rstCab.getInt("ne_val");
            dateFecCot = objUti.formatearFecha(rstCab.getDate("fe_cot"),"dd/MM/yyyy");
            intTipMod = 2;
        //******************************************************************
             
             
            if(rstCab.getDate("fe_cot")==null){
                txtFecDoc.setText("");
            }else{
                java.util.Date dateObj = rstCab.getDate("fe_cot");
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                calObj.get(java.util.Calendar.MONTH)+1     ,
                calObj.get(java.util.Calendar.YEAR)        );
            }
            
            txtVenCod.setText(((rstCab.getString("co_ven")==null)?"":rstCab.getString("co_ven")));
            txtVenNom.setText(((rstCab.getString("nomcom")==null)?"":rstCab.getString("nomcom")));
            txtAte.setText(((rstCab.getString("tx_ate")==null)?"":rstCab.getString("tx_ate")));
            double dblSub = ((rstCab.getString("nd_sub")==null)?0:objUti.redondear(rstCab.getDouble("nd_sub"),objZafParSis.getDecimalesMostrar()));
            txtSub.setText(""+dblSub);
            //Pie de pagina
            txaObs1.setText(((rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1")));
            txaObs2.setText(((rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2")));
            
            //Mostrar el estado del registro.
            strAux=rstCab.getString("st_reg");
            strstReg_glo = strAux;
            if (strAux.equals("A"))
                strAux="Activo";
            else if (strAux.equals("I"))
                strAux="Anulado";
            else
                strAux="Otro";
            objTooBar.setEstadoRegistro(strAux);
            
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
            blnHayCam=false;
        }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    
    
    
      
    public class mitoolbar extends ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
        }
        public boolean anular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            abrirCon();
            if(estadoFacCotVen("anular")){ CerrarCon();
            return false;
            }
            if (!anularReg()) { CerrarCon();
            return false;
            }
            CerrarCon();
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }
        
        public void clickAnterior() {
            try
            {  abrirCon();
               if(rstCab != null ) {
                   if (!rstCab.isFirst()) {
                       if (blnHayCam) {
                           if (isRegPro()) {
                               rstCab.previous();
                               cargarReg();
                           }
                       }
                       else {
                           rstCab.previous();
                           cargarReg();
                       }
                   }}
               CerrarCon();
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickAnular() {
            noEditable(false);
            
        }
        
        
        public void clickConsultar() {
            txtVenCod.setEnabled(true);
            setEditable(false);
            //butCopyCot.setEnabled(true);
            noEditable(false);
            txtCot.setEditable(true);
            blnHayCam=false;
        }
        
        public void clickEliminar() {
            noEditable(false);
            blnHayCam=false;
        }
        
        public void clickFin() {
            try {
                abrirCon();
                if(rstCab != null ) {
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.last();
                                cargarReg();
                            }
                        }
                        else {
                            rstCab.last();
                            cargarReg();
                        }
                    }}
                CerrarCon();
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickInicio() {
            try
            {   abrirCon();
                if(rstCab != null ) {
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.first();
                                cargarReg();
                            }
                        }
                        else {
                            rstCab.first();
                            cargarReg();
                        }
                    }}
                CerrarCon();
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickInsertar() {
            try {
                if (objTblMod.isDataModelChanged()) {
                    
                    isRegPro();
                }
                if (rstCab!=null) {
                    rstCab.close();
                    stmCab.close();
                    
                    rstCab=null;
                    stmCab=null;
                    
                }
                clnTextos();
                txtCodForPag2.setText("");
                noEditable(false);
                
                
                txtFecDoc.setHoy();
                
                cargarForPagCli();
                
                setEstadoBotonMakeFac();
                
                setEditable(true);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblMod.setDataModelChanged(false);
                   
                blnHayCam=false;
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        
        
        
        public void setEstadoBotonMakeFac(){
            switch(getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    //butCopyCot.setEnabled(false);
                    break;
                case 'c'://Estado Consultar
                    //butCopyCot.setEnabled(false);
                    break;
                    //                        case 'n':
                    //                                    butMakeFactura.setEnabled(false);
                    //                                    break;
                case 'y':
                    //butCopyCot.setEnabled(false);
                    break;
                case 'z':
                    //butCopyCot.setEnabled(false);
                    break;
                default:
                   // butCopyCot.setEnabled(false);
                    break;
                    
            }
        }
          
        
        public void clickSiguiente() {
            try
            {  abrirCon();
               if(rstCab != null ) {
                   if (!rstCab.isLast()) {
                       if (blnHayCam || objTblMod.isDataModelChanged()) {
                           if (isRegPro()) {
                               rstCab.next();
                               cargarReg();
                           }
                       }
                       else {
                           rstCab.next();
                           cargarReg();
                       }
                   } }
               CerrarCon();
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        
          
        
        
        public boolean eliminar() {
            try {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                
                abrirCon();
                
                if(estadoFacCotVen("eliminar")) { CerrarCon();
                return false;
                }
                if (!eliminarReg()){ CerrarCon();
                return false;
                }
                
                objTooBar.setEstadoRegistro("Eliminado");
                clnTextos();
                CerrarCon();
                
                blnHayCam=false;
            }
            catch (Exception e) {
                return true;
            }
            return true;
        }
        
        
        
        
        
        public boolean insertar() {
            strstReg_glo="";
            try{
                abrirCon();
                
                if (!validaCampos()) { CerrarCon();
                return false;
                }
                
                if (!insertarReg()) { CerrarCon();
                return false;
                }
                STR_ESTREG="I";
                CerrarCon();
                
                blnHayCam=false;
            }
            catch(Exception e){ objUti.mostrarMsgErr_F1(this, e);  }
            return true;
        }
           
        
        
        
        //*** esot verifica si la cotizacion ha sido facturado  si esta facturado no se podra realizar camnbios **///
        public boolean estadoFacCotVen(String strMsg ){
            boolean  blnRes=false;
            java.sql.PreparedStatement pstUltDoc;
            java.sql.PreparedStatement pstUltNumDoc;
            java.sql.ResultSet rstNum;
            int intCoDoc=0;
            int intTipdoc=1;
            String strTit, strMsg2,  strEstTip="0";
            try {
                if(CONN_GLO!=null) {
                    
                    String sql = "select  st_reg  from tbm_cabcotven " +
                    "where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  " +
                    "co_loc = " + objZafParSis.getCodigoLocal()   + " and  " +
                    "co_cot = " + ((txtCot.getText().equals(""))?"0":txtCot.getText());
                    
                    java.sql.Statement stm=CONN_GLO.createStatement();
                    java.sql.ResultSet rst=stm.executeQuery(sql);
                    if (rst.next()){
                        if (rst.getString(1).trim().equals("F")){
                            
                            MensajeInf("La Cotización Ya esta Facturado.\nNo es posible "+ strMsg +" una Cotización Facturado.");
                            return true;
                        }
                        else  return false;
                    }
                    
                    stm.close();
                    stm=null;
                    rst=null;
                    
                    pstUltDoc=null;
                    pstUltNumDoc=null;
                    rstNum=null;
                }
            }
            catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            return blnRes;
        }
        
        
        
        
        
        
   
        
        
        
        
          
        public boolean modificar() {
            
           
            
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            
             
             
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            
             
              
            abrirCon();
            
            if(estadoFacCotVen("modificar")){ CerrarCon();
            return false;
            }
            
            if (!validaCampos()){ CerrarCon();
            return false;
            }
             System.out.println("Modifcando..1");
            if (!actualizarReg()){ CerrarCon();
            if(blnHayCam_glo_tbl==false) objTblMod.setDataModelChanged(false);
            return false;
            }
          
            CerrarCon();
            
            objTblMod.setDataModelChanged(false);
            
            blnHayCam=false;
            return BLN_ESTADO;
        }
        
         
        
        
        
        public boolean modificar_res() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            
            abrirCon();
            
            
            if(estadoFacCotVen("modificar")){ CerrarCon();
            return false;
            }
            
            
            if (!validaCampos()){ CerrarCon();
            return false;
            }
            
            
            if (!actualizarReg()){ CerrarCon();
            if(blnHayCam_glo_tbl==false) objTblMod.setDataModelChanged(false);
            return false;
            }
            objTblMod.setDataModelChanged(false);
            CerrarCon();
            
            blnHayCam=false;
            return true;
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
                    if (stmCab!=null){
                        stmCab.close();
                        stmCab=null;
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
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return true;
        }
        
        public boolean afterConsultar() {
            //butCopyCot.setEnabled(true);
             
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
           // butCopyCot.setEnabled(true);
            objTblMod.initRowsState();
            blnHayCam=false;
            return true;
        }
        
        public boolean afterModificar() {
            objTblMod.clearDataSavedBeforeRemoveRow();
            blnHayCam=false;
            this.setEstado('w');
            //butCopyCot.setEnabled(true);
            objTblMod.initRowsState();
            blnHayCam=false;
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        
        
        
        
        
        
        
        
        
        
        public boolean PreVtaMenorPreUni(){
            
            boolean blnRes = true;
            int INT_TBL_COITM = 17 ;
            int INT_TBL_PREVEN= 11 ;
            double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = Glo_dlbMaxDes; // getDesCli(txtCliCod.getText());
            double dblMinimo, dblValorVenta, dblCostoItm=0, dblPorDesCom=0, dblPorGanancia = Glo_dlbMarUti; // getMarUti(txtCliCod.getText());
            int intCoItm;
            double dblMarUtiItm=0;
            
            
            
            try{
                
                for(int i = 0 ; i < tblDat.getRowCount();i++){
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                        if(!(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().equalsIgnoreCase("S"))){
                            
                            
                            
                            
                            dblPreUsr    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString()))),6);
                            
                            dblMarUtiItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_MARUTI)==null || tblDat.getValueAt(i, INT_TBL_MARUTI).equals(""))?"0":(tblDat.getValueAt(i, INT_TBL_MARUTI).toString()))),2);
                            
                            
                            ///*******************
                            if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                                if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                    dblPreUniItm=0;
                                else
                                    dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                    
                            }else
                                dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                
                                ///*******************
                                
                                
                                
                                dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                                dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
                                int intTipPre=0;
                                if(dblPreUniItm!=0){
                                    dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
                                    intTipPre=1;
                                }else{
                                    double dblStkAct = 0;
                                    ///*******************
                                    if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                                        if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                            dblStkAct=0;
                                        else
                                            dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                    }else
                                        dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                        //*******************
                                        
                                        
                                         
                                        if(!(tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S")))  // if(dblStkAct != 0)
                                            dblCostoItm  =   getCosUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                                            
                                        else {
                                            if(tblDat.getValueAt(i,INT_TBL_PRE_COS) == null) dblCostoItm=0.00;
                                            else   dblCostoItm = Double.parseDouble( ((tblDat.getValueAt(i,INT_TBL_PRE_COS).toString().equals(""))?"0":tblDat.getValueAt(i,INT_TBL_PRE_COS).toString())  );
                                            
                                            if(tblDat.getValueAt(i,INT_TBL_DESPRECOM) == null) dblPorDesCom=0.00;
                                            else  dblPorDesCom = Double.parseDouble( ((tblDat.getValueAt(i,INT_TBL_DESPRECOM).toString().equals(""))?"0":tblDat.getValueAt(i,INT_TBL_DESPRECOM).toString()) );
                                            
                                              
                                            dblCostoItm=objUti.redondear((dblCostoItm-(dblCostoItm*(dblPorDesCom/100))),2);
                                        }
                                        
                                        
                                        //  //System.out.println("Costo "+ dblCostoItm);
                                        
                                        dblMinimo    =   dblCostoItm * dblPorGanancia;
                                        dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
                                }
                                
                                dblValorVenta=objUti.redondear(dblValorVenta,3);
                                dblMinimo=objUti.redondear(dblMinimo,3);
                                
                                
                                double dblPor = (1 - (dblCostoItm/dblValorVenta))*100;
                                //System.out.println(" Porsentaje  >>> "+ dblPor  );
                                dblPor=objUti.redondear(dblPor,3);
                                
                              
                                if(intTipPre==1){
                                    if(dblValorVenta<dblMinimo){
                                        blnRes = false;
                                        tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                    }
                                }else{
                                    if( dblPor < dblMarUtiItm ){
                                        blnRes = false;
                                        tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                        //tblDat.setValueAt(new Boolean(true),i,INT_TBL_BLNPRE);
                                    }}
                                
                                
                                
                        }}}
                
            }
            catch(Exception e){ objUti.mostrarMsgErr_F1(this, e);  }
            
            return blnRes;
        }
        
        
        
        
        
          
        
        
        
        
        private double getCosUni(String int_co_itm){
            double dblPreUni = 0;
            try{
                if(CONN_GLO!=null){
                    java.sql.Statement stmUni = CONN_GLO.createStatement();
                    String sSQL = "  SELECT a1.nd_cosUni FROM tbm_inv AS a1"+
                    " INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                    " WHERE a2.co_emp="+ objZafParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objZafParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
                    
                    
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblPreUni = rstUni.getDouble("nd_cosuni");
                    }
                    rstUni.close();
                    stmUni.close();
                    rstUni = null;
                    stmUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblPreUni; }
            catch(Exception Evt) {  return dblPreUni; }
            return dblPreUni;
        }
        
        
        
        
        
        
        
        
        
        
        
        
        public boolean insertarReg(){
            boolean blnRes=false;
            String Estado="A";
            int ESTPRE=0;
            int  Est=0;
            
            calculaSubTotTodLosItm();
            
            try{
                if (CONN_GLO!=null){
                    CONN_GLO.setAutoCommit(false);
                    dblPorIva =  bldivaEmp;
                    
                    int intNumCot = getUltCoDoc(CONN_GLO);
                    if(!PreVtaMenorPreUni())
                        ESTPRE = 1;
                    
                    txtsql.setText("");
                    
                    
                    if(ESTPRE == 1 )
                        Est = objAutPrg.checkCtls(CONN_GLO);
                    
                    
                      
                    if(Est==0){
                        if( intNumCot !=-1 ){
                            
                            int intValor =  objAutPrg.checkCtls_2(CONN_GLO);
                            
                            
                            
                            if(insertarCab(CONN_GLO, intNumCot, false, Estado )){
                                if(insertarDet(CONN_GLO, intNumCot , "I" )){
                                    if(insertDetPag(intNumCot, CONN_GLO )){
                                        int intCods[] = {objZafParSis.getCodigoLocal(),   intNumCot };
                                        if(objAutPrg.insertarCabDetAut_2(CONN_GLO, intCods , 3,intValor ) ){
                                            
                                            CONN_GLO.commit();
                                            blnRes = true;
                                            txtCot.setText(intNumCot+"");
                                            ObtenerFecNunVal(CONN_GLO, intNumCot);  //*******
                                            
                                            String strTit, strMsg;
                                            strTit="Mensaje del sistema Zafiro";
                                            strMsg="ï¿½Desea imprimir la cotizacion?";
                                            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                                            if(obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0){
                                                imprimir();
                                            }
                                            
                                        }else
                                            CONN_GLO.rollback();
                                    }else
                                        CONN_GLO.rollback();
                                }else
                                    CONN_GLO.rollback();
                            }else
                                CONN_GLO.rollback();
                        }else
                            CONN_GLO.rollback();
                        
                    }
                    
                    
                    if(Est == 2 ) {  ///************
                        Estado="P";
                        if( intNumCot !=-1 ){
                            if(insertarCab(CONN_GLO, intNumCot, false, Estado )){
                                if(insertarDet(CONN_GLO, intNumCot , "I" )){
                                    if(insertDetPag(intNumCot, CONN_GLO )){
                                        int intCods[] = {objZafParSis.getCodigoLocal(),   intNumCot };
                                        if(objAutPrg.insertarCabDetAut(CONN_GLO, intCods , 3) ){
                                            CONN_GLO.commit();
                                            blnRes = true;
                                            txtCot.setText(intNumCot+"");
                                            ObtenerFecNunVal(CONN_GLO, intNumCot);  //*******
                                            
                                        }else
                                            CONN_GLO.rollback();
                                    }else
                                        CONN_GLO.rollback();
                                }else
                                    CONN_GLO.rollback();
                            }else
                                CONN_GLO.rollback();
                        }else
                            CONN_GLO.rollback();
                        
                    }
                    
                }  ///*********
            }
            catch(SQLException  Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;  }
            catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
            return blnRes;
        }
        
        
         
        
        /////////////////////////////////////*********************************/////////////////////////////////////////
        
         
        
          
        
        /////////////////////////////////////*********************************/////////////////////////////////////////
        private void ObtenerFecNunVal(java.sql.Connection conIns, int intNumDoc){
            try{
                String sqlCab = "select ne_val,fe_cot from tbm_cabCotVen where "+
                " co_emp="+objZafParSis.getCodigoEmpresa() + " and " +
                " co_loc="+objZafParSis.getCodigoLocal()   + " and  " +
                " co_cot="+intNumDoc;
                
                java.sql.Statement stmF;
                java.sql.ResultSet rstF;
                stmF=conIns.createStatement();
                rstF=stmF.executeQuery(sqlCab);
                if(rstF.next()){
                    intNumDiaVal = rstF.getInt("ne_val");
                    dateFecCot = objUti.formatearFecha(rstF.getDate("fe_cot"),"dd/MM/yyyy");
                    intTipMod = 2;
                }
                rstF.close();
                stmF.close();
                stmF=null;
                rstF=null;
            }
            catch (java.sql.SQLException e){
                
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        
        
        
        
        
        private boolean insertarCab(java.sql.Connection conIns, int intNumDoc,  boolean blnSolAut, String Estado ){
            boolean blnRes = false;
            try{
                String strFecha = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                String sqlCab = "INSERT INTO  tbm_cabCotVen" +
                "(co_emp, co_loc, co_cot, " + //CAMPOS PrimayKey
                " fe_cot, "                 + //Fecha de la cotizacion
                " co_cli, co_ven, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                " tx_obs1, tx_obs2, nd_sub, nd_valiva, nd_tot, nd_porIva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                " co_forPag, fe_ing, fe_ultmod, co_usring ,co_usrmod, st_reg, ne_val,tx_numped " +
                " ,st_regrep )" +
                "VALUES (" +
                objZafParSis.getCodigoEmpresa() + ", " +
                objZafParSis.getCodigoLocal()   + ", " +
                intNumDoc + ", '" +
                strFecha  + "', " +
                txtCliCod.getText() + ",   " +
                txtVenCod.getText() + ",  '" +
                txtAte.getText()    + "', '" +
                txaObs1.getText()   + "', '" +
                txaObs2.getText()   + "',  " +
                dblSubtotalCot    + ",   " +
                Double.parseDouble(txtIva.getText())  + ", " +
                Double.parseDouble(txtTot.getText())   + ",  " +
                dblPorIva   + " , " +
                txtCodForPag.getText() + ",CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, " +
                objZafParSis.getCodigoUsuario() + " , " +
                objZafParSis.getCodigoUsuario() + " , " +
                ((blnSolAut)?"'P'":"'"+ Estado +"'") +  ",15,'"+tx_numped.getText()+"' ,'I' )";   //);
                
                txtsql.append( sqlCab +"; \n");
                
                blnRes=true;
                
            } catch (Exception e) {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }
        
        
       
        
        
        
        
        private boolean insertarDet(java.sql.Connection conInsCot, int intNumDoc, String st_regrep){
            boolean blnRes = false;
            try{
                int Co_Bod = getBodPrede();
                StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
                int intest=0;
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                        
                            
                        if (i>0)
                            stb.append(" UNION ALL ");
                        stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+intNumDoc+","+(i+1)+", " +
                        tblDat.getValueAt(i, INT_TBL_CODITM)+",'"+tblDat.getValueAt(i,INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"','"+
                        tblDat.getValueAt(i, INT_TBL_DESITM)+"',"+Co_Bod+","+tblDat.getValueAt(i,INT_TBL_CANMOV)+","+
                        Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString())+","+
                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),6) + ",'" +
                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'," +
                        ((tblDat.getValueAt(i, INT_TBL_COD_PRO)==null)?"0":tblDat.getValueAt(i, INT_TBL_COD_PRO).toString()) +","+
                        objUti.redondeo(Double.parseDouble((tblDat.getValueAt(i, INT_TBL_PRE_COS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRE_COS).toString()),6)+",'"+st_regrep+"', " +
                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_DESPRECOM)==null || tblDat.getValueAt(i, INT_TBL_DESPRECOM).equals("") )?"0":tblDat.getValueAt(i, INT_TBL_DESPRECOM).toString()),6)      );
                        
                        
                        intest=1;
                    } }
                
                if(intest==1){
                    String strQry ="INSERT INTO  tbm_detCotVen (co_emp, co_loc, co_cot, co_reg," + //CAMPOS PrimayKey
                    " co_itm, tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                    " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen " +//<==Campos que aparecen en la parte inferior del 1er Tab
                    " ,co_prv,nd_precom ,st_regrep , nd_pordesprecom )"+stb.toString();
                    txtsql.append( strQry +"; \n");
                }
                
                stb=null;
                blnRes=true;
            }
            catch (Exception e)  {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }
        
        
        
        
          
        
            
        private boolean insertDetPag(int intNumDoc, java.sql.Connection conInsCot ){
            String sql="";
            try{
                
                for(int i=0; i<tblCotForPag.getRowCount();i++){
                    
                    int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
                    String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                    
                    sql = "INSERT INTO  tbm_pagCotVen" +
                    "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                    " ne_diaCre, fe_ven, " +//<==
                    " mo_pag, ne_diaGra, nd_porRet " +//<==
                    " , st_regrep , co_tipret ) " +
                    " VALUES(" +
                    objZafParSis.getCodigoEmpresa() + ", " +
                    objZafParSis.getCodigoLocal()   + ", " +
                    intNumDoc + ", " +
                    (i+1)       + ", " +
                    ((tblCotForPag.getValueAt(i, 1)==null || tblCotForPag.getValueAt(i, 1).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 1)) + ",'#" +
                    strFechaPag + "#'," +
                    objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) + ", " +
                    ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
                    ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 3).toString()) +
                    ",'I' , "+ Integer.parseInt(((tblCotForPag.getValueAt(i, 6)==null)?"0":tblCotForPag.getValueAt(i, 6).toString()))+" )";
                    
                    txtsql.append( sql +"; \n");
                }  
                
                java.sql.PreparedStatement pstDetCot = conInsCot.prepareStatement(txtsql.getText());
                pstDetCot.executeUpdate();
                txtsql.setText("");
                pstDetCot.close();
                pstDetCot=null;
                
            }
            catch(SQLException Evt)
            {   objUti.mostrarMsgErr_F1(jfrThis, Evt);
                
                return false;
            }
            catch(Exception Evt)
            {   objUti.mostrarMsgErr_F1(jfrThis, Evt);
                // mostrarMsg(sql);
                return false;
            }
            return true;
        }
        
        
        
        
        
        
        
        private int getUltCoDoc(java.sql.Connection conMAX){
            int valReturn = 0;
            try{
                java.sql.Statement stmMaxCot = conMAX.createStatement();
                sSQL= "SELECT Max(co_cot) as co_cot FROM tbm_cabCotVen where " +
                " co_emp = "+objZafParSis.getCodigoEmpresa()+" and co_loc = " + objZafParSis.getCodigoLocal();
                ResultSet rstNum = stmMaxCot.executeQuery(sSQL);
                if(rstNum.next())
                    valReturn = rstNum.getInt("co_cot");
                valReturn++;
                stmMaxCot.close();
                stmMaxCot=null;
            }
            catch(java.sql.SQLException e){ valReturn= -1; }
            catch(Exception e){ valReturn= -1; }
            return valReturn;
        }
        
        
        
        /////////////////////////////////////*********************************/////////////////////////////////////////
        /////////////////////////////////////*********************************/////////////////////////////////////////
        
        
        
        
        
        
        
        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
            return _consultar(FilSql());
        }
        
          
        
        public boolean consultarLoc() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
            return _consultarLoc();
        }
        
        
        public void clickModificar(){
            setEditable(true);
            txtCot.setEditable(false);
            txtNomForPag.setEditable(false);
            
            noEditable(false);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            strCodPrv="";
            strDesLarPrv="";
            strCodCom="";
            strDesLarCom="";
            
        }
        
        
        public boolean actualizarReg(){
            boolean blnRes = false;
            String Estado="A";
            int ESTPRE=0;
            int  Est=0;
          
             try{
            
            blnHayCam_glo = blnHayCam;
            blnHayCam_glo_tbl=objTblMod.isDataModelChanged();
            
            
            calculaSubTotTodLosItm();
            
            boolean blnValPre=true;
            
           
            
            //**********************
            String FecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(),"yyyy/MM/dd");
            Librerias.ZafDate.ZafDatePicker objDate =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y") ;
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(dateFecCot);
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, intNumDiaVal);
            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
            java.util.Date fe2 = objUti.parseDate(FecAux,"yyyy/MM/dd");
            if(fe1.equals(fe2))
                blnValPre=false;
            else if(fe1.after(fe2))
                blnValPre=false;
            else  System.out.println("  ");
                //**********************
               
                  //System.out.println("Paso 2 ");
                     
                    
                    if(CONN_GLO!=null){
                        CONN_GLO.setAutoCommit(false);
                        
                        if( blnValPre==true ||  blnHayCam_glo_tbl==true ) {
                            if(!PreVtaMenorPreUni())
                                ESTPRE = 1;
                            
                             
                            if(ESTPRE == 1 )
                                Est = objAutPrg.checkCtls(CONN_GLO);
                            else {
                                java.sql.Statement stm=CONN_GLO.createStatement();
                                String sql="Update  tbm_detautcotven set st_cum='S',st_reg='A' From( " +
                                " select co_reg from tbm_regneg as a where a.tx_nomfun='"+strNomFuncValPre+"' " +
                                " and a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_mnu="+objZafParSis.getCodigoMenu()+""+
                                " ) as a where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+txtCot.getText()+" and co_regneg=a.co_reg";
                                stm.executeUpdate(sql);
                                
                            }}
                        
                        //System.out.println("Paso 1.2 ");
                        
                        if(VerificaPermisodeImpresion_cot(CONN_GLO)) Estado="A";
                        else Estado="P";
                        
                        
                        if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";
                        
                        
                        
                        if(Est==0 || Est==1 ){
                            int intValor =  objAutPrg.checkCtls_2(CONN_GLO);
                            
                            
                            
                            if(modificarCab(CONN_GLO, false,Estado , STR_ESTREG) ){
                                if(modificarDet(CONN_GLO, STR_ESTREG )){
                                    if(modificarPag( CONN_GLO , STR_ESTREG )){
                                        if( blnHayCam_glo==true ||  blnHayCam_glo_tbl==true ) {
                                            int intCods[] = {objZafParSis.getCodigoLocal(),   Integer.parseInt(txtCot.getText()) };
                                            if(objAutPrg.insertarCabDetAut_3(CONN_GLO, intCods , 3, intValor) ){
                                                CONN_GLO.commit();
                                                blnRes = true;
                                                blnHayCam = false;
                                            }else
                                                CONN_GLO.rollback();
                                        } else{
                                            CONN_GLO.commit();
                                            blnRes = true;
                                            blnHayCam = false;
                                        }
                                    }else
                                        CONN_GLO.rollback();
                                }else
                                    CONN_GLO.rollback();
                            }else
                                CONN_GLO.rollback();
                            ////************************
                        }
                        if(Est == 2 ) {
                            Estado="P";
                            if(modificarCab(CONN_GLO, false,Estado ,STR_ESTREG ) ){
                                if(modificarDet(CONN_GLO, STR_ESTREG )){
                                    if(modificarPag( CONN_GLO, STR_ESTREG )){
                                        int intCods[] = {objZafParSis.getCodigoLocal(),   Integer.parseInt(txtCot.getText()) };
                                        if(objAutPrg.insertarCabDetAut(CONN_GLO, intCods , 3) ){
                                            CONN_GLO.commit();
                                            blnRes = true;
                                            blnHayCam = false;
                                        }else
                                            CONN_GLO.rollback();
                                    }else
                                        CONN_GLO.rollback();
                                }else
                                    CONN_GLO.rollback();
                            }else
                                CONN_GLO.rollback();
                            
                        }
                    }
                }
                catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes =  false;  }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes = false; }
            return blnRes;
        }
        
        
        
        
        
        
        private boolean modificarCab(java.sql.Connection conModCot, boolean blnSolAut, String Estado, String st_regrep){
            boolean blnRes = false;
            try{
                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                dateFecCot = Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0];
                dateFecCot = objUti.formatearFecha(dateFecCot,"yyyy/MM/dd","dd/MM/yyyy");
                /*********************************************\
                 * Armando el Update para los datos          *
                 * de la cabecera de cotizacion              *
                 * \*********************************************/
                
                txtsql.setText("");
                
                String sql = "Update tbm_cabCotVen set " +
                " fe_cot  = '" + strFecha            + "', " +
                " co_cli  = "  + txtCliCod.getText() + ",  " +
                " co_ven  = "  + txtVenCod.getText() + ",  " +
                " tx_ate  = '" + txtAte.getText()    + "', " +
                " nd_tot  = '" + Double.parseDouble(txtTot.getText())    + "', " +
                " tx_obs1 = '" + txaObs1.getText()   + "', " +
                " tx_obs2 = '" + txaObs2.getText()   + "', " +
                " nd_sub  = "  + Double.parseDouble(txtSub.getText())    + ",  " +
                " nd_valiva  = "  + Double.parseDouble(txtIva.getText())    + ",  " +
                " nd_porIva = "+ dblPorIva           +  ", " +
                " co_forPag = "+ txtCodForPag.getText() + ", " +
                " fe_ultMod   =  CURRENT_TIMESTAMP                , " +
                " co_usrMod   = " + objZafParSis.getCodigoUsuario() + ", " +
                " st_reg   = " + ((blnSolAut)?"'P'":"'"+ Estado +"'") + ", " +
                " st_regrep= '"+st_regrep+"', "+
                " tx_numped='"+tx_numped.getText()+"' "+
                "  where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                " co_cot = " +  txtCot.getText();
                txtsql.append( sql + "; \n");
                
                blnRes=true;
            }
            catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            return blnRes;
        }
        
        
        
        
        
        private boolean modificarDet(java.sql.Connection conModCot, String st_regrep){
            boolean blnRes = false;
            try{
                
                String sql = " DELETE FROM tbm_detCotVen " +
                "  where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                " co_cot = " +  txtCot.getText();
                txtsql.append( sql + "; \n");
                
                String strSqlDet="";
                blnRes = insertarDet(conModCot,Integer.parseInt(txtCot.getText()),st_regrep);
                
            }
            catch (Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
        
        private boolean modificarPag(java.sql.Connection conModCot , String st_regrep ){
            boolean blnRes = false;
            try{
                /*********************************************\
                 * Armando el Delete para quitar los         *
                 * registro de detalle de Pago               *
                 * actuales.                                 *
                 * \*********************************************/
                String sql = " DELETE FROM tbm_pagCotVen " +
                "  where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                " co_cot = " +  txtCot.getText();
                
                txtsql.append( sql + "; \n");
                
                /*********************************************\
                 * Armando el Insert para los datos          *
                 * del detalle de PAgo                         *
                 * \*********************************************/
                for(int i=0; i<tblCotForPag.getRowCount();i++){
                    int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
                    String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                    //  java.sql.PreparedStatement pstInsPagCot = conModCot.prepareStatement(
                    sql =   "INSERT INTO  tbm_pagCotVen" +
                    "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                    " ne_diaCre, fe_ven, " +//<==
                    " mo_pag, ne_diaGra, nd_porRet   " +//<==
                    " ,st_regrep , co_tipret )" +
                    "VALUES (" +
                    objZafParSis.getCodigoEmpresa() + ", " +
                    objZafParSis.getCodigoLocal()   + ", " +
                    txtCot.getText() + ", " +
                    (i+1)       + ", " +
                    ((tblCotForPag.getValueAt(i, 1)==null || tblCotForPag.getValueAt(i, 1).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 1)) + ",'#" +
                    strFechaPag + "#'," +
                    objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) + ", " +
                    ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
                    ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"null":tblCotForPag.getValueAt(i, 3).toString()) +
                    ",'"+st_regrep+"' , "+Integer.parseInt(((tblCotForPag.getValueAt(i, 6)==null)?"0":tblCotForPag.getValueAt(i, 6).toString()))+" )";
                    txtsql.append( sql + "; \n");
                    
                }
                
                java.sql.PreparedStatement pstInsPagCot = conModCot.prepareStatement( txtsql.getText());
                pstInsPagCot.executeUpdate();
                txtsql.setText("");
                pstInsPagCot.close();
                pstInsPagCot=null;
                
                blnRes=true;
            }
            catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
            return blnRes;
        }
        
        
        
        
        
        
        public boolean eliminarReg(){
            
            try{
                try{
                    if (CONN_GLO!=null){
                        CONN_GLO.setAutoCommit(false);
                        java.sql.PreparedStatement pstReg;
                        java.sql.Statement stmCot=CONN_GLO.createStatement();
                        
                        
                        
                        if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";
                        
                        sSQL= "Update tbm_cabCotVen set " +
                        " st_reg  = 'E' " +
                        " ,fe_ultMod  =  CURRENT_TIMESTAMP  , " +
                        "  co_usrMod   = " + objZafParSis.getCodigoUsuario() + ", " +
                        " st_regrep = '"+STR_ESTREG+"' "+
                        "  where " +
                        " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                        " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                        " co_cot = " +  txtCot.getText();
                        
                        /* Ejecutando el Delete */
                        pstReg = CONN_GLO.prepareStatement(sSQL);
                        pstReg.executeUpdate();
                        
                        CONN_GLO.commit();
                    }
                }catch(SQLException Evt){
                    CONN_GLO.rollback();
                    objUti.mostrarMsgErr_F1(jfrThis, Evt);
                    return false;
                }
            }catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
            
            return true;
        }
        
        
        
        
        public boolean anularReg(){
            
            if (isAnulada())
                return false;
            try{
                try{
                    if(CONN_GLO!=null) {
                        CONN_GLO.setAutoCommit(false);
                        java.sql.PreparedStatement pstReg;
                        java.sql.Statement stmCot=CONN_GLO.createStatement();
                        
                        /*********************************************\
                         * Armando el Update para ANULAR             *
                         * la cotizacion                             *
                         * \*********************************************/
                        if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";
                        sSQL= "Update tbm_cabCotVen set " +
                        " st_reg  = '"  + "I" + "' " +
                        " ,fe_ultMod  =  CURRENT_TIMESTAMP  , " +
                        "  co_usrMod   = " + objZafParSis.getCodigoUsuario() + ", " +
                        " st_regrep = '"+STR_ESTREG+"' "+
                        "  where " +
                        " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                        " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                        " co_cot = " +  txtCot.getText();
                        
                        
                        /* Ejecutando el Update */
                        pstReg = CONN_GLO.prepareStatement(sSQL);
                        pstReg.executeUpdate();
                        CONN_GLO.commit();
                        
                    }
                }catch(SQLException Evt){
                    CONN_GLO.rollback();
                    objUti.mostrarMsgErr_F1(jfrThis, Evt);
                    return false;
                }
            }catch(Exception Evt){
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }
            
            
            return true;
            
        }
        
        //******************************************************************************************************
        
        public boolean vistaPreliminar(){
            int intCodEmp=objZafParSis.getCodigoEmpresa();
            int intCodLoc=objZafParSis.getCodigoLocal();
            String strTit=objZafParSis.getNombreMenu();
            String strNomEmp=retNomEmp(intCodEmp);
            String strTelEmp="";
            Actualizar_valores();
            String strTelCli="";
            String strRucCli="";
            String strCodForPag = txtNomForPag.getText();
            Connection conIns;
            try {
                conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                try {
                    if(conIns!=null){
                        
                        
                        JasperDesign jasperDesign = JRXmlLoader.load(DIRECCION_REPORTE);
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                        
                        
                        
                        Map parameters = new HashMap();
                        parameters.put("tit", strTit);
                        parameters.put("numCot", txtCot.getText());
                        parameters.put("codEmp", ""+intCodEmp);
                        parameters.put("codLoc", ""+intCodLoc);
                        parameters.put("fecCot", txtFecDoc.getText());
                        parameters.put("codCli", txtCliCod.getText());
                        parameters.put("nomCli", txtCliNom.getText());
                        parameters.put("rucCli", strRucCli);
                        parameters.put("dirCli", txtCliDir.getText());
                        parameters.put("nomVen", txtVenNom.getText());
                        parameters.put("obs1", txaObs1.getText());
                        parameters.put("obs2", txaObs2.getText());
                        parameters.put("subTot", txtSub.getText());
                        parameters.put("iva", txtIva.getText());
                        parameters.put("totFin", txtTot.getText());
                        parameters.put("nomEmp", strNomEmp);
                        parameters.put("telEmp", strTelEmp);
                        parameters.put("telCli", strTelCli);
                        parameters.put("forPag", strCodForPag);
                        parameters.put("atencion", txtAte.getText());
                        
                        
                        /// JasperPrint report=JasperFillManager.fillReport(DIRECCION_REPORTE, parameters,  conIns);
                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
                        
                        JasperViewer.viewReport(report, false);
                    }
                }
                catch (JRException e) {  objUti.mostrarMsgErr_F1(jfrThis, e);    }
            } catch(SQLException ex) {  objUti.mostrarMsgErr_F1(jfrThis, ex);   }
            return true;
        }
        
        
        
        
        
        
        public boolean imprimir(){
            
            int intCodEmp=objZafParSis.getCodigoEmpresa();
            int intCodLoc=objZafParSis.getCodigoLocal();
            String strTit=objZafParSis.getNombreMenu();
            String strNomEmp=retNomEmp(intCodEmp);
            Actualizar_valores();
            String strTelCli="";
            String strRucCli="";
            String strCodForPag = txtNomForPag.getText();
            Connection conIns;
            try {
                conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                try {
                    if(conIns!=null){
                        
                        JasperDesign jasperDesign = JRXmlLoader.load(DIRECCION_REPORTE); 
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign); 
                        
                        
                        // Second, create a map of parameters to pass to the report.
                        Map parameters = new HashMap();
                        parameters.put("tit", strTit);
                        parameters.put("numCot", txtCot.getText());
                        parameters.put("codEmp", ""+intCodEmp);
                        parameters.put("codLoc", ""+intCodLoc);
                        parameters.put("fecCot", txtFecDoc.getText());
                        parameters.put("codCli", txtCliCod.getText());
                        parameters.put("nomCli", txtCliNom.getText());
                        parameters.put("rucCli", strRucCli);
                        parameters.put("dirCli", txtCliDir.getText());
                        parameters.put("nomVen", txtVenNom.getText());
                        parameters.put("obs1", txaObs1.getText());
                        parameters.put("obs2", txaObs2.getText());
                        parameters.put("subTot", txtSub.getText());
                        parameters.put("iva", txtIva.getText());
                        parameters.put("totFin", txtTot.getText());
                        parameters.put("nomEmp", strNomEmp);
                        parameters.put("telCli", strTelCli);
                        parameters.put("forPag", strCodForPag);
                        parameters.put("atencion", txtAte.getText());
                        
                        // JasperPrint report=JasperFillManager.fillReport(DIRECCION_REPORTE, parameters,  conIns);
                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
                        
                        //para imprimir
                        JasperPrintManager.printReport(report,false);
                    }
                }
                catch (JRException e) {  objUti.mostrarMsgErr_F1(jfrThis, e);   }
            } catch(SQLException ex) {  objUti.mostrarMsgErr_F1(jfrThis, ex );  }
            return true;
        }
        
        
        //******************************************************************************************************
        
        
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
            boolean Estado = VerificaPermisodeImpresion();
            return Estado;
        }
        public boolean beforeInsertar() {
            return true;
        }
        public boolean beforeModificar() {
            return true;
        }
        public boolean beforeVistaPreliminar() {
            boolean Estado = VerificaPermisodeImpresion();
            return Estado;
        }
        
        public void MensajeNoImpreme(){
            String  strTit="Mensaje del sistema Zafiro";
            String  strMsg="¿Nesecita Autorización Para poder Imprimir";
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
        }
        
        public boolean VerificaPermisodeImpresion() {
            boolean lbnEst=false;
            int IntCodEmp=objZafParSis.getCodigoEmpresa();
            int IntCodLoc=objZafParSis.getCodigoLocal();
            String StrCodCot=txtCot.getText();
            
            
            
            String sql = "SELECT b.st_reg,b.st_cum FROM tbm_cabautcotven as a" +
            " INNER JOIN tbm_detautcotven AS b ON (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_cot=b.co_cot and a.co_aut=b.co_aut)" +
            " INNER JOIN tbm_regneg as c ON(b.co_emp=c.co_emp and b.co_loc=c.co_loc and b.co_regneg=c.co_reg and c.co_mnu="+objZafParSis.getCodigoMenu()+" and c.tx_nomfun='"+strNomFuncValPre+"')" +
            " WHERE a.co_cot="+StrCodCot+" and a.co_emp="+IntCodEmp+" and a.co_loc="+IntCodLoc+""+
            " AND b.co_aut=( select max(co_aut) FROM  tbm_cabautcotven WHERE co_emp="+IntCodEmp+" AND co_loc="+IntCodLoc+" AND  co_cot="+StrCodCot+" )";
            
           
            String sql2 ="select st_reg from tbm_cabcotven where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+ txtCot.getText();
            
            
            try{
                java.sql.Connection conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conn!=null){
                    java.sql.Statement stm2 = conn.createStatement();
                    java.sql.ResultSet rst2 = stm2.executeQuery(sql2);
                    if(rst2.next()){
                        if(rst2.getString(1).equals("P")){
                            
                            java.sql.Statement stm = conn.createStatement();
                            java.sql.ResultSet rst = stm.executeQuery(sql);
                            
                           //  System.out.println(" ZZ "+ sql );
                            
                            if(rst.next()){
                                if(rst.getString(1) != null ){
                                    if(rst.getString(2).equals("S")){
                                        lbnEst=true;
                                        
                                    }
                                    else if(rst.getString(1).equals("A")){
                                        lbnEst=true;
                                        
                                    }
                                    else {
                                        
                                       // System.out.println(" ZZ ");
                                        PreVtaMenorPreUni(); //**********************
                                        MensajeNoImpreme();
                                        lbnEst=false;
                                    }
                                }
                            }
                            else
                                lbnEst = true;
                            
                            
                            rst.close();
                            stm.close();
                            
                        }
                        else  lbnEst = true;
                        
                        rst2.close();
                        stm2.close();
                    }
                    conn.close();
                }
            }
            catch(SQLException  Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return lbnEst;  }
            return lbnEst;
        }
        
        
        
        
        
    }
    
    private String FilSql2() {
        String sqlFiltro = "";
        //Agregando filtro por Numero de Cotizacion
        if(!txtCot.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CotCab.co_cot =" + txtCot.getText()+ "";
        
        
        
        return sqlFiltro ;
    }
    
    
    
    
    
    
    private String FilSql() {
        String sqlFiltro = "";
        //Agregando filtro por Numero de Cotizacion
        if(!txtCot.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CotCab.co_cot =" + txtCot.getText()+ "";
        
        //Agregando filtro por Fecha
        if(txtFecDoc.isFecha()){
            int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and CotCab.fe_cot = '" +  strFecSql + "'";
        }
        //Agregando filtro por Codigo de Cliente
        if(!txtCliCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CotCab.co_cli =" + txtCliCod.getText() + "";
        
        //Agregando filtro por Codigo de Vendedor
        if(!txtVenCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CotCab.co_ven = " + txtVenCod.getText() + "";
        
        //Agregando filtro por Codigo de Vendedor
        if(!txtAte.getText().equals(""))
            sqlFiltro = sqlFiltro + " and CotCab.tx_ate LIKE '" + txtAte.getText() + "'";
        
                /*
                 * Poniendo Filtros para direccion del cliente y otros campos que no pertenecen a
                 * la tabla de Cotizacion
                 */
        //Agregando filtro por Direccion del cliente
        if(!txtCliDir.getText().equals(""))
            sqlFiltro = sqlFiltro + " and Cli.tx_dir LIKE '" + txtCliDir.getText() + "'";
        
        //Agregando filtro por Nombre del cliente
        if(!txtCliNom.getText().equals(""))
            sqlFiltro = sqlFiltro + " and Cli.tx_nom LIKE '" + txtCliNom.getText() + "'";
        
        //Agregando filtro por Nombre del cliente
        if(!txtVenNom.getText().equals(""))
            sqlFiltro = sqlFiltro + " and Usr.tx_nom LIKE '" + txtVenNom.getText() + "'";
        
        return sqlFiltro ;
    }
    
      
    private void MensajeValidaCampo(String strNomCampo){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    
    public boolean consultar2(Integer CodEmp,Integer CodLoc  ) {
        return _consultar2(FilSql2(),CodEmp,CodLoc  );
    }


    public boolean consultar3(Integer CodEmp,Integer CodLoc, Integer CodHis ) {
        return _consultar3(FilSql2(),CodEmp,CodLoc, CodHis  );
    }
    

   private boolean _consultar3(String strFil,Integer CodEmp,Integer CodLoc, Integer CodHis  ){

        strFiltro= strFil;
        int intCodHis2=0;
        try{//odbc,usuario,password

            abrirCon();
            if(CONN_GLO!=null) {

                stmCot=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );



                sSQL = "select max(co_his) from tbh_cabcotven where co_emp="+CodEmp+"  and co_loc="+CodLoc+" aND co_cot="+intCodCot;
                java.sql.ResultSet rstHis=stmCot.executeQuery(sSQL);
                if(rstHis.next())
                    intCodHis=rstHis.getInt(1);
                 rstHis.close();
                 rstHis=null;

                 
                 
                 if(CodHis.intValue()==1) intCodHis2=CodHis.intValue();
                 else {
                      intCodHis2=CodHis.intValue()-1;
                 }




                sSQL = " SELECT * " +
                " FROM  ( " +

                " select * from ( " +
                
                " SELECT CotCab.co_emp as co_emph ,CotCab.co_loc as co_loch ,cli.co_cli as co_clih, cli.tx_nom as nomclih, cli.tx_dir as dirclih, " +
                " Usr.co_usr as co_venh, Usr.tx_nom as nomcomh, "+
                "  CotCab.co_cot as co_coth, CotCab.co_his as co_hish,  CotCab.fe_cot as fe_coth, CotCab.tx_ate as tx_ateh,  "+
                "  CotCab.co_forPag as co_forPagh,"+
                "  CotCab.tx_obs1 as tx_obs1h, CotCab.tx_obs2 as tx_obs2h, round(CotCab.nd_sub,2) as nd_subh, round(CotCab.nd_valiva,2) as nd_valivah , round(CotCab.nd_tot,2) as nd_toth,  CotCab.nd_porIva as nd_porIvah, CotCab.nd_valDes as nd_valDesh,"+
                "  CotCab.st_reg as st_regh , forpag.tx_des as tx_desh "+
                "  , CotCab.ne_val as ne_valh , CotCab.st_regrep as st_regreph  ,Cli.tx_tipper as tx_tipperh, Cli.nd_maxdes as nd_maxdesh, Cli.nd_maruti as nd_marutih,                 Cli.tx_tel as tx_telh, Cli.tx_ide as tx_ideh , ciu.tx_desLar as tx_desLarh "+
                "  ,Cli.co_tipper as co_tipperh , CotCab.tx_numped as tx_numpedh ,forpag.ne_tipforpag as ne_tipforpagh , forpag.nd_pes as nd_pesh , forpagCli.ne_tipforpag as                 ne_tipforpagclih , forpagCli.nd_pes as nd_pesclih  "+
                "  ,Cli.ne_diagra as ne_diagrah FROM tbh_cabCotven as CotCab left outer "+
                "  join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) "+
                "  left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  "+
                "  LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) "+
                "  left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) "+
                "  left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  "+
                "  Where CotCab.co_emp="+CodEmp+" "+
                "  and CotCab.co_loc="+CodLoc+" and CotCab.co_his="+CodHis+"  and CotCab.st_reg not in ('E')"+
                "   and CotCab.co_cot="+intCodCot+"  ORDER BY CotCab.co_cot " +
                
                 
                " ) as x, " +

                " ( " + 

                 
                " SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli,"+
                "  Usr.co_usr as co_ven, Usr.tx_nom as nomcom, "+
                "  CotCab.co_cot,   CotCab.fe_cot, CotCab.tx_ate,  "+
                "  CotCab.co_forPag,"+
                " CotCab.tx_obs1, CotCab.tx_obs2, round( CotCab.nd_sub,2 ) as nd_sub, round(CotCab.nd_valiva,2)   as nd_valiva , round(CotCab.nd_tot,2) as nd_tot,  CotCab.nd_porIva, CotCab.nd_valDes, "+
                "  CotCab.st_reg , forpag.tx_des "+
                "  , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar "+
                "  ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli  "+
                "  ,Cli.ne_diagra FROM tbh_cabCotven as CotCab left outer "+
                "  join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) "+
                "  left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  "+
                "  LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) "+
                "  left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) "+
                "  left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  "+
                "  Where CotCab.co_emp="+CodEmp+" "+
                "  and CotCab.co_loc="+CodLoc+" and CotCab.co_his="+intCodHis2+"  and CotCab.st_reg not in ('E')"+
                "  and CotCab.co_cot="+intCodCot+"  ORDER BY CotCab.co_cot "+


                " ) as x1 " +
                " ) as x ";

                System.out.println(" "+sSQL);
                rstCab=stmCot.executeQuery(sSQL);

                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg3(CodEmp, CodLoc, CodHis, intCodHis2);
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                    return false;
                }
            }
            CerrarCon();
        }
        catch(SQLException Evt) {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;   }
        catch(Exception Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     return false;   }
        //butCopyCot.setEnabled(true);
        System.gc();
        return true;

    }





    private boolean _consultar2(String strFil,Integer CodEmp,Integer CodLoc  ){
        
        strFiltro= strFil;
       
        try{//odbc,usuario,password
            
            abrirCon();
            if(CONN_GLO!=null) {
                
                stmCot=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
                
                
                sSQL = "select max(co_his) from tbh_cabcotven where co_emp="+CodEmp+"  and co_loc="+CodLoc+" aND co_cot="+intCodCot;
                java.sql.ResultSet rstHis=stmCot.executeQuery(sSQL);
                if(rstHis.next())
                    intCodHis=rstHis.getInt(1);
                 rstHis.close();
                 rstHis=null;
                 
                  
                
                 
                 
                          
               /* sSQL = " SELECT * " +
                " FROM  ( " +
                
                " select * from ( " +
                " SELECT CotCab.co_emp as co_emph ,CotCab.co_loc as co_loch ,cli.co_cli as co_clih, cli.tx_nom as nomclih, cli.tx_dir as dirclih, " +
                " Usr.co_usr as co_venh, Usr.tx_nom as nomcomh, "+
                "  CotCab.co_cot as co_coth, CotCab.co_his as co_hish,  CotCab.fe_cot as fe_coth, CotCab.tx_ate as tx_ateh,  "+
                "  CotCab.co_forPag as co_forPagh,"+
                "  CotCab.tx_obs1 as tx_obs1h, CotCab.tx_obs2 as tx_obs2h, round(CotCab.nd_sub,2) as nd_subh, round(CotCab.nd_valiva,2) as nd_valivah , round(CotCab.nd_tot,2) as nd_toth,  CotCab.nd_porIva as nd_porIvah, CotCab.nd_valDes as nd_valDesh,"+
                "  CotCab.st_reg as st_regh , forpag.tx_des as tx_desh "+
                "  , CotCab.ne_val as ne_valh , CotCab.st_regrep as st_regreph  ,Cli.tx_tipper as tx_tipperh, Cli.nd_maxdes as nd_maxdesh, Cli.nd_maruti as nd_marutih,                 Cli.tx_tel as tx_telh, Cli.tx_ide as tx_ideh , ciu.tx_desLar as tx_desLarh "+
                "  ,Cli.co_tipper as co_tipperh , CotCab.tx_numped as tx_numpedh ,forpag.ne_tipforpag as ne_tipforpagh , forpag.nd_pes as nd_pesh , forpagCli.ne_tipforpag as                 ne_tipforpagclih , forpagCli.nd_pes as nd_pesclih  "+
                "  ,Cli.ne_diagra as ne_diagrah FROM tbh_cabCotven as CotCab left outer "+
                "  join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) "+
                "  left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  "+
                "  LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) "+
                "  left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) "+
                "  left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  "+
                "  Where CotCab.co_emp="+CodEmp+" "+
                "  and CotCab.co_loc="+CodLoc+" and CotCab.co_his="+CodHIs+"  and CotCab.st_reg not in ('E')"+
                "   and CotCab.co_cot="+intCodCot+"  ORDER BY CotCab.co_cot "+
                " ) as x, " +
                " ( " + */

                sSQL = " SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli,"+
                "  Usr.co_usr as co_ven, Usr.tx_nom as nomcom, "+
                "  CotCab.co_cot,   CotCab.fe_cot, CotCab.tx_ate,  "+
                "  CotCab.co_forPag,"+
                " CotCab.tx_obs1, CotCab.tx_obs2, round( CotCab.nd_sub,2 ) as nd_sub, round(CotCab.nd_valiva,2)   as nd_valiva , round(CotCab.nd_tot,2) as nd_tot,  CotCab.nd_porIva, CotCab.nd_valDes, "+
                "  CotCab.st_reg , forpag.tx_des "+
                "  , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar "+
                "  ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli  "+
                "  ,Cli.ne_diagra FROM tbm_cabCotven as CotCab left outer "+
                "  join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) "+
                "  left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  "+
                "  LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) "+
                "  left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) "+
                "  left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  "+
                "  Where CotCab.co_emp="+CodEmp+" "+
                "  and CotCab.co_loc="+CodLoc+"  and CotCab.st_reg not in ('E')"+
                "  and CotCab.co_cot="+intCodCot+"  ORDER BY CotCab.co_cot ";

                //" ) as x1 " +
                //" ) as x ";
                    
               // System.out.println(" "+CodHIs);
                rstCab=stmCot.executeQuery(sSQL);
                
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg2(CodEmp, CodLoc);
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                    return false;
                }
            }
            CerrarCon();
        }
        catch(SQLException Evt) {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;   }
        catch(Exception Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     return false;   }
        //butCopyCot.setEnabled(true);
        System.gc();
        return true;
        
    }
    
    
    
    
    
    
    
    private boolean _consultar(String strFil){
        strFiltro= strFil;
        try{
            abrirCon();
            if(CONN_GLO!=null) {
                
                stmCab=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                " CotCab.st_reg , forpag.tx_des " +
                " , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  " +
                " ,Cli.co_tipper , CotCab.tx_numped , forpag.ne_tipforpag , forpag.nd_pes, forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli    " +
                " ,Cli.ne_diagra  FROM tbm_cabCotven as CotCab left outer " +
                " join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
                " left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  " +
                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
                " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  " +
                " Where CotCab.co_emp = " + vecEmp.elementAt(empresa.getSelectedIndex()) + // Consultando en la empresa en la ke se esta trabajando
                "       and CotCab.co_loc = " + vecLoc.elementAt(local.getSelectedIndex()) +" and CotCab.st_reg in ('A','I','P','C','R','F','U','D')";// Consultando en el local en el ke se esta trabajando
                sSQL= sSQL + strFiltro + " ORDER BY CotCab.co_cot";
                rstCab=stmCab.executeQuery(sSQL);
                 
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                }  
                else{
                    objTooBar.setMenSis("0 Registros encontrados");  
                    clnTextos();
                    return false;
                }
                
            }
            CerrarCon();
            
        }  
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        return true;
        
    }
    
    
    
    
    
    
     
    private boolean _consultarLoc(){
        
        try{
            abrirCon();
            if(CONN_GLO!=null) {
                
                stmCab=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                " CotCab.st_reg , forpag.tx_des " +
                " , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  " +
                " ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli   " +
                " ,Cli.ne_Diagra FROM tbm_cabCotven as CotCab left outer " +
                " join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
                  " left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  " +
                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
                " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  " +
                " Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                "       and CotCab.co_loc = 1  and CotCab.st_reg not in ('E')"+ // Consultando en el local en el ke se esta trabajando
                " AND  CotCab.co_cot = "+txtCodLoc.getText()+"  ORDER BY CotCab.co_cot";
                //System.out.println(" >> "+ sSQL );
                rstCab=stmCab.executeQuery(sSQL);
                
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarRegLoc();
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                    return false;
                }
            }
            CerrarCon();
            
        }
        
        
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            return false;
        }
        return true;
        
    }
    
    
    
    
    
    
    
    private void cargarRegistroInsert(){
        try{
            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCab!=null){
                
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,CotCab.co_cot,CotCab.st_reg " + // Campo para saber si esta anulado o no
                " FROM tbm_cabCotven as CotCab " +
                " Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                "  and CotCab.co_loc = "  + objZafParSis.getCodigoLocal() + // Consultando en el local en el ke se esta trabajando
                "  and CotCab.co_cot = "  + txtCot.getText();
                
                rstCab=stmCab.executeQuery(sSQL);
                
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                }
            }
        }catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
    
    
    
    
    
    
    
    
    
    //  FUNCIONA SIN LAS TERMINALES
    private boolean validaCampos_local(){
        if(txtCliCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Cliente");
            txtCliCod.requestFocus();
            return false;
        }
        if(!txtFecDoc.isFecha()){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Fecha de Cotización");
            txtFecDoc.requestFocus();
            return false;
        }
        if(txtVenCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Vendedor");
            txtVenCod.requestFocus();
            return false;
        }
               /*
                * VAlidando los datos del Pago
                */
        if((txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
            tabCotCom.setSelectedIndex(1);
            MensajeValidaCampo("Forma de pago");
            return false;
        }
        
        int intColObligadasPag[] = {2};
        if( !objUti.verifyTbl(tblCotForPag,intColObligadasPag)){
            tabCotCom.setSelectedIndex(1);
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            objUti.verifyTbl(tblCotForPag,intColObligadasPag);
            return false;
        }
        
        
        
        for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
            
            if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                
                double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                
                if(dblCan <= 0.00 ){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                    return false;
                }
                
                
                
                double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PREUNI).toString());
                if(dblCos <= 0.00 ){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_PREUNI);
                    return false;
                }
                
                
            }
        }
        
        
        
                /*
                 * VAlidando que la suma de los montos de pagos sean igual a total de cotizacion
                 */
        double dblTotalMonto=0;
        for(int rowIdx = 0; rowIdx<tblCotForPag.getRowCount() ;rowIdx++){
            double dblSubMonto = Double.parseDouble((tblCotForPag.getValueAt(rowIdx, 4)==null)?"0":tblCotForPag.getValueAt(rowIdx, 4).toString());
            dblTotalMonto = dblTotalMonto + dblSubMonto ;
        }
        
        if( objUti.redondeo(dblTotalMonto,intNumDec)!= objUti.redondeo(Double.parseDouble(txtTot.getText()), intNumDec) ){
            tabCotCom.setSelectedIndex(1);
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="La suma de los montos a pagar es mayor al total de la CotizaciÃ³n.\nCorrija y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            return false;
        }
        String strSql = "select * from tbm_cli where " +
        "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
        "co_cli = " + Integer.parseInt(txtCliCod.getText());
        try{
            if (CONN_GLO!=null){
                
                Statement stmCliDesc = CONN_GLO.createStatement();
                ResultSet rstCliDesc = stmCliDesc.executeQuery(strSql);
                if(rstCliDesc.next()){
                    double dblPorDesCli  = rstCliDesc.getDouble("nd_maxDes");
                    
                    //*****************************************************************************
                    //*****************************************************************************
                    int Estado=0;
                    int intSer=0;
                    StringBuffer stb=new StringBuffer();
                    for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                        String stritem="";
                        if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                            if(tblDat.getValueAt(intRowVal,INT_TBL_ITMSER).toString().equalsIgnoreCase("N")){   // SI NO ES DE SERVICIO
                                stritem=tblDat.getValueAt(intRowVal,INT_TBL_CODITM).toString();
                                
                                if (intSer>0)
                                    stb.append(" UNION ALL ");
                                stb.append("SELECT SUM(a2.nd_stkAct) AS nd_stkAct,"+stritem+" as coitm"+
                                " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                                " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                                " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+stritem+")");
                                intSer=1;
                            }}}
                    
                    if(intSer==1){
                        ResultSet rst_stock = stmCliDesc.executeQuery(stb.toString());
                        stb=null;
                        int ValCod=0;
                        while(rst_stock.next()){
                            if(rst_stock.getFloat(1)<=0.00){
                                for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                                        if(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) != null )
                                            ValCod = Integer.parseInt(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO).toString());
                                        if( tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) == null || ValCod == 0 ) {
                                            if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM).toString().equals(rst_stock.getString(2))){
                                                tblDat.repaint();
                                                tblDat.requestFocus();
                                                tblDat.changeSelection(intRowVal, INT_TBL_NOM_PRO,false,false);
                                                tblDat.setValueAt("*",intRowVal, INT_TBL_NOM_PRO);
                                                Estado=1;
                                                break;
                                            }}}}
                            }
                        }
                        rst_stock.close();
                        rst_stock=null;
                    }
                    //*****************************************************************************
                    //*****************************************************************************
                    
                    
                    if(Estado==1){
                        String strTit, strMsg;
                        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="El campo <<Proveedor>> no puede estar vacio.";
                        
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                    
                    
                    for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                        if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                            if( tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) != null) {
                                if( !(Integer.parseInt(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO).toString())==0) ) {
                                    
                                    double valor=0.00;
                                    if(tblDat.getValueAt(intRowVal, INT_TBL_PRE_COS)!=null)
                                        valor =  Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_PRE_COS).toString());
                                    
                                    if(valor <= 0.00 ){
                                        String strTit, strMsg;
                                        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                                        strTit="Mensaje del sistema Zafiro";
                                        strMsg="El campo <<Pre.Com>> no puede estar Cero.";
                                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                        tblDat.repaint();
                                        tblDat.requestFocus();
                                        tblDat.changeSelection(intRowVal, INT_TBL_PRE_COS,true,false);
                                        tblDat.editCellAt(intRowVal, INT_TBL_PRE_COS);
                                        return false;
                                    }
                                }}
                            
                            //*****
                        }}
                    
                    //*********************************
                }
                rstCliDesc.close();
                stmCliDesc.close();
                rstCliDesc = null;
                stmCliDesc = null;
                
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   return false;  }
        return true;
    }
    
    
    
    
    
    //  FUNCIONA PARA LAS TERMINALES
    private boolean validaCampos(){
        if(txtCliCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Cliente");
            txtCliCod.requestFocus();
            return false;
        }
        if(!txtFecDoc.isFecha()){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Fecha de Cotización");
            txtFecDoc.requestFocus();
            return false;
        }
        if(txtVenCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Vendedor");
            txtVenCod.requestFocus();
            return false;
        }
               /*
                * VAlidando los datos del Pago
                */
        
        if((txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
            tabCotCom.setSelectedIndex(1);
            MensajeValidaCampo("Forma de pago");
            
            return false;
        }
        
        
        
        int intColObligadasPag[] = {2};
        if( !objUti.verifyTbl(tblCotForPag,intColObligadasPag)){
            tabCotCom.setSelectedIndex(1);
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            objUti.verifyTbl(tblCotForPag,intColObligadasPag);
            return false;
        }
        
        
        
        for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
            
            if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                
                double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                
                if(dblCan <= 0.00 ){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                    return false;
                }
                
                
                
                double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PREUNI).toString());
                if(dblCos <= 0.00 ){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_PREUNI);
                    return false;
                }
                
                
            }
        }
        
        
        
                /*
                 * VAlidando que la suma de los montos de pagos sean igual a total de cotizacion
                 */
        double dblTotalMonto=0;
        for(int rowIdx = 0; rowIdx<tblCotForPag.getRowCount() ;rowIdx++){
            double dblSubMonto = Double.parseDouble((tblCotForPag.getValueAt(rowIdx, 4)==null)?"0":tblCotForPag.getValueAt(rowIdx, 4).toString());
            dblTotalMonto = dblTotalMonto + dblSubMonto ;
        }
        
        if( objUti.redondeo(dblTotalMonto,intNumDec)!= objUti.redondeo(Double.parseDouble(txtTot.getText()), intNumDec) ){
            tabCotCom.setSelectedIndex(1);
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="La suma de los montos a pagar es mayor al total de la CotizaciÃ³n.\nCorrija y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            return false;
        }
        String strSql = "select nd_maxDes from tbm_cli where " +
        "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
        "co_cli = " + Integer.parseInt(txtCliCod.getText());
        try{
            if (CONN_GLO!=null){
                
                Statement stmCliDesc = CONN_GLO.createStatement();
                ResultSet rstCliDesc = stmCliDesc.executeQuery(strSql);
                if(rstCliDesc.next()){
                    double dblPorDesCli  = rstCliDesc.getDouble("nd_maxDes");
                    
                    //*****************************************************************************
                    //*****************************************************************************
                    int Estado=0;
                    int intSer=0;
                    
                    int ValCod=0;
                    for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                        if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                            if (tblDat.getValueAt( intRowVal , INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S")){
                                if(tblDat.getValueAt(intRowVal,INT_TBL_ITMSER).toString().equalsIgnoreCase("N")){   // SI NO ES DE SERVICIO
                                    
                                    
                                    if(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) != null )
                                        ValCod = Integer.parseInt(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO).toString());
                                    
                                    if( tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) == null || ValCod == 0 ) {
                                        tblDat.repaint();
                                        tblDat.requestFocus();
                                        tblDat.changeSelection(intRowVal, INT_TBL_NOM_PRO,false,false);
                                        tblDat.setValueAt("*",intRowVal, INT_TBL_NOM_PRO);
                                        Estado=1;
                                    }}}}  }
                    
                    
                    //*****************************************************************************
                    //*****************************************************************************
                    
                    
                    
                    if(Estado==1){
                        String strTit, strMsg;
                        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="El campo <<Proveedor>> no puede estar vacio.";
                        
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                    
                    
                    for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                        if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                            
                            if( tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) != null) {
                                if( !(Integer.parseInt(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO).toString())==0) ) {
                                    
                                    double valor=0.00;
                                    if(tblDat.getValueAt(intRowVal, INT_TBL_PRE_COS)!=null)
                                        valor =  Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_PRE_COS).toString());
                                    
                                    if(valor <= 0.00 ){
                                        String strTit, strMsg;
                                        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                                        strTit="Mensaje del sistema Zafiro";
                                        strMsg="El campo <<Pre.Com>> no puede estar Cero.";
                                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                        tblDat.repaint();
                                        tblDat.requestFocus();
                                        tblDat.changeSelection(intRowVal, INT_TBL_PRE_COS,true,false);
                                        tblDat.editCellAt(intRowVal, INT_TBL_PRE_COS);
                                        return false;
                                    }
                                }}
                            ///****
                            
                            //*****
                        }}
                    
                    //*********************************
                }
                rstCliDesc.close();
                stmCliDesc.close();
                rstCliDesc = null;
                stmCliDesc = null;
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   return false;  }
        return true;
    }
    
    
    
    
    
    private boolean validaCamFac(){
        if(txtCliCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Cliente");
            txtCliCod.requestFocus();
            return false;
        }
        if(!txtFecDoc.isFecha()){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Fecha de Cotización");
            txtFecDoc.requestFocus();
            return false;
        }
        if(txtVenCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Vendedor");
            txtVenCod.requestFocus();
            return false;
        }
               /*
                * VAlidando los datos del Pago
                */
        if((txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
            tabCotCom.setSelectedIndex(1);
            MensajeValidaCampo("Forma de pago");
            return false;
        }
        
        int intColObligadasPag[] = {2};
        if( !objUti.verifyTbl(tblCotForPag,intColObligadasPag)){
            tabCotCom.setSelectedIndex(1);
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            objUti.verifyTbl(tblCotForPag,intColObligadasPag);
            return false;
        }
        
        
        
        for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
            
            if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                
                double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                
                if(dblCan <= 0.00 ){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                    return false;
                }
                
                
                
                double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PREUNI).toString());
                if(dblCos <= 0.00 ){
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_PREUNI);
                    return false;
                }
                
                
            }
        }
        
        
        
                /*
                 * VAlidando que la suma de los montos de pagos sean igual a total de cotizacion
                 */
        double dblTotalMonto=0;
        for(int rowIdx = 0; rowIdx<tblCotForPag.getRowCount() ;rowIdx++){
            double dblSubMonto = Double.parseDouble((tblCotForPag.getValueAt(rowIdx, 4)==null)?"0":tblCotForPag.getValueAt(rowIdx, 4).toString());
            dblTotalMonto = dblTotalMonto + dblSubMonto ;
        }
        
        if( objUti.redondeo(dblTotalMonto,intNumDec)!= objUti.redondeo(Double.parseDouble(txtTot.getText()), intNumDec) ){
            tabCotCom.setSelectedIndex(1);
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="La suma de los montos a pagar es mayor al total de la CotizaciÃ³n.\nCorrija y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            return false;
        }
        
        try{
            
            //*****************************************************************************
            //*****************************************************************************
            int Estado=0;
            int ValCod=0;
            
            //*****************************************************************************
            //*****************************************************************************
            
            
            
            for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                    if (tblDat.getValueAt( intRowVal , INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S")){
                        if(tblDat.getValueAt(intRowVal,INT_TBL_ITMSER).toString().equalsIgnoreCase("N")){   // SI NO ES DE SERVICIO
                            
                            
                            if(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) != null )
                                ValCod = Integer.parseInt(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO).toString());
                            
                            if( tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) == null || ValCod == 0 ) {
                                tblDat.repaint();
                                tblDat.requestFocus();
                                tblDat.changeSelection(intRowVal, INT_TBL_NOM_PRO,false,false);
                                tblDat.setValueAt("*",intRowVal, INT_TBL_NOM_PRO);
                                Estado=1;
                            }}}}}
            //************************************************
            
            
            if(Estado==1){
                String strTit, strMsg;
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<Proveedor>> no puede estar vacio.";
                
                obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            
            
            for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                    if( tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO) != null) {
                        if( !(Integer.parseInt(tblDat.getValueAt(intRowVal, INT_TBL_COD_PRO).toString())==0) ) {
                            
                            double valor=0.00;
                            if(tblDat.getValueAt(intRowVal, INT_TBL_PRE_COS)!=null)
                                valor =  Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_PRE_COS).toString());
                            
                            if(valor <= 0.00 ){
                                String strTit, strMsg;
                                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                                strTit="Mensaje del sistema Zafiro";
                                strMsg="El campo <<Pre.Com>> no puede estar Cero.";
                                obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                tblDat.repaint();
                                tblDat.requestFocus();
                                tblDat.changeSelection(intRowVal, INT_TBL_PRE_COS,true,false);
                                tblDat.editCellAt(intRowVal, INT_TBL_PRE_COS);
                                return false;
                            }
                        }}
                    ///****
                    
                    //*****
                }}
            
            
        }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   return false;  }
        return true;
    }
    
    
    
    
    
    
    
    
    
    private double getStkAct(String int_co_itm){
        double dblStkAct = 0;
        try{
            INTVERCONCEN=0;
            //Abrir_Conexion_Remota();
            if (CONN_GLO!=null){
                
                
                java.sql.Statement stmUni= CONN_GLO.createStatement();
                
                
                String sSQL = "SELECT SUM(a2.nd_stkAct) AS nd_stkAct" +
                " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+int_co_itm+")";
                
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next()){
                    dblStkAct = rstUni.getDouble("nd_stkact");
                }
                rstUni.close();
                stmUni.close();
                rstUni = null;
                stmUni = null;
            }
        }
        catch(java.sql.SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );  return dblStkAct;  }
        catch(Exception Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt );   return dblStkAct;  }
        return dblStkAct;
    }
    
    
    
    
    
    private double getPreUni(String int_co_itm){
        double dblPreUni = 0;
        try{
            if (CONN_GLO!=null){
                java.sql.Statement stmUni = CONN_GLO.createStatement();
                String sSQL= "SELECT nd_prevta1 " +
                " from tbm_inv as inv " +
                " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                "       inv.co_itm = " + int_co_itm ;
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next()){
                    dblPreUni = rstUni.getDouble("nd_prevta1");
                }
                rstUni.close();
                stmUni.close();
                rstUni = null;
                stmUni = null;
            }
        }
        catch(java.sql.SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );  return dblPreUni; }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );  return dblPreUni;  }
        return dblPreUni;
    }
    
    
    
    /*
     *  Listener para hacer que se calcule el pago despues segun el total de cotizacion
     */
    private boolean isAnulada() {
        boolean blnRes = false;
        ///java.sql.Connection conTmp ;
        java.sql.Statement stmTmp;
        String strSQL = "";
        try {
            if(CONN_GLO!=null) {
                strSQL = "";
                strSQL = " Select count(*) from tbm_cabcotven ";
                strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and co_cot = " + txtCot.getText() + " and st_reg = 'I' " ;
                if (objUti.getNumeroRegistro(this,objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0){
                    blnRes = true;
                    MensajeInf("Documento anulado no se puede modificar");
                }else
                    blnRes = false;
                
            }
            
            
        }catch(Exception Evt)  {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            blnRes = true;
        }
        return blnRes;
    }
    
    
    
    
    private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    
    public class ActLisForPag implements java.awt.event.ActionListener{
        public void actionPerformed(java.awt.event.ActionEvent e) {
            // calculaPagos();
        }
    }
    
    
    
    
    
         
    
    public double getSubtotalSinTrans(){
        
        double dblSub = 0, dblSubCot = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        for (int i=0;i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                if(!(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("T"))){
                    
                    dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
                    dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                    
                }}}
        
        dblSubCot = objUti.redondear(dblSub ,intNumDec);
        
        return dblSubCot;
    }
    
    
    
    
    
    
    
    
    
    
      
    public double getSubtotalTrans(){
        double dblSub = 0, dblSubCot = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        for (int i=0;i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("T")){
                    
                    dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
                    dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                    
                }}}
        dblSubCot = objUti.redondear(dblSub ,intNumDec);
        return dblSubCot;
    }
    
    
    
    
     
     
     
     
     
     
     
    public void calculaPag(){
        int intVal =  intCanArr[0];
        int intsizearre = intarreglodia.length;
        intVal= intsizearre - (intsizearre-intVal);
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intFilUltPag = 0;
        double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
        try{
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            int fecDoc [] = txtFecDoc.getFecha(txtFecDoc.getText());
            
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            
            dblBaseDePagos = dblTotalCot;
            
            //Borrando los datos actuales del jtable
            javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
            modTbl.setRowCount(0);
            
            if(dblBaseDePagos>0){
                
                if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
                    
                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                    
                    //*************************************************************************************///
                      
                    java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    if (conn!=null){
                        
                        dblRetFueFle = getSubtotalTrans();
                        
                        
                        ///  //System.out.println("Tipo de Per>> "+ strCodTipPerCli );
                        if(!(strCodTipPerCli==null)){
                            if(!(strCodTipPerCli.trim().equals("1"))){
                                if(dblRetFueFle>0){
                                    ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
                                    intFilUltPag = tblCotForPag.getRowCount()-1;
                                    tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
                                    tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
                                    tblCotForPag.setValueAt("1.000", intFilUltPag, 3);
                                    dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
                                    tblCotForPag.setValueAt( new Double(dblRetFueFle) , intFilUltPag, 4);
                                    
                                    tblCotForPag.setValueAt("1" , intFilUltPag, 6);
                                    tblCotForPag.setValueAt("N",  intFilUltPag, 7);
                                    
                                }} else { dblRetFueFle=0;  }
                        }else { dblRetFueFle=0;  }
                        
                        
                        
                        
                        
                        String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
                        strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
                        strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
                        " AND  '"+strFecSisBase+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
                                      
                        
                        
                        
                        stm = conn.createStatement();
                        rst = stm.executeQuery(strSQL);
                        while (rst.next()){
                            
                            ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
                            intFilUltPag = tblCotForPag.getRowCount()-1;
                            tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
                            tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
                            
                            if (rst.getString("tx_aplret").equals("S")){
                                dblRetFue = getSubtotalSinTrans();
                                if(!(dblRetFueFle>0))
                                    dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
                                else
                                    dblRetFue = objUti.redondeo( (    dblRetFue  * ( rst.getDouble("nd_porret")/100)),2 );
                                
                                
                                
                                tblCotForPag.setValueAt(rst.getString("nd_porret"), intFilUltPag, 3);
                                tblCotForPag.setValueAt( new Double(dblRetFue) , intFilUltPag, 4);
                            }
                            if (rst.getString("tx_aplret").equals("I")){
                                dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 );
                                tblCotForPag.setValueAt(rst.getString("nd_porret") , intFilUltPag, 3);
                                tblCotForPag.setValueAt( new Double(dblRetIva) , intFilUltPag, 4);
                            }
                            
                            tblCotForPag.setValueAt(rst.getString("co_tipret") , intFilUltPag, 6);
                            tblCotForPag.setValueAt("N",  intFilUltPag, 7);
                        }
                        
                        rst.close();
                        rst=null;
                        stm.close();
                        stm=null;
                        
                        conn.close();
                        conn=null;
                        
                        
                    }
                    
                    //*************************************************************************************///
                    double dblPago=0.00;
                    double dblPagos=0.00;
                    int i=0;
                    
                    double dblRete=0;
                    dblRete = dblRetFueFle+dblRetFue+dblRetIva;
                    //   //System.out.println(">>> "+ dblRete );
                    dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);
                    
                    //// //System.out.println(">>> "+ dblBaseDePagos );
                    
                    //  dblBaseDePagos = objUti.redondear( ( dblTotalCot - ( dblRetFueFle + dblRetFue + dblRetIva)),intNumDec);
                    
                    for(i=0; i < intVal; i++) {
                        java.util.Calendar objFecPagActual = objFec.getInstance();
                        objFecPagActual.setTime(objFec.getTime());
                        
                        int diaCre = intarreglodia[i];
                        int numPag = intarreglonum[i];
                        String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);
                        
                        if (diaCre!=0)
                            objFecPagActual.add(java.util.Calendar.DATE, diaCre);
                        
                        dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
                        dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
                        dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
                        
                        ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
                        intFilUltPag = tblCotForPag.getRowCount()-1;
                        tblCotForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
                        tblCotForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
                        dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
                        dblPago += dblPagos;
                        dblPagos = objUti.redondear(dblPagos ,intNumDec);
                        tblCotForPag.setValueAt( new Double(dblPagos) , intFilUltPag, 4);
                        tblCotForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
                        
                        tblCotForPag.setValueAt(strSop,  intFilUltPag, 7);
                        
                    }
                    
                    
                    double dblultpag =  objUti.redondear(Double.parseDouble(tblCotForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
                    // dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago + dblRetIva + dblRetFue) ),intNumDec);
                    dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago + dblRete ) ),intNumDec);
                    
                    
                    tblCotForPag.setValueAt( new Double(dblultpag) , intFilUltPag , 4);
                    
                    //*************************************************************************************///
                    //*************************************************************************************///
                } }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    }
    
    
    
    
    
  
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanEmpLoc;
    private javax.swing.JButton butCliCon;
    private javax.swing.JButton butCliCon1;
    private javax.swing.JButton butDirCliCon;
    private javax.swing.JButton butVenCon;
    private javax.swing.JComboBox empresa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblAte1;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblCotNumDes;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTot;
    private javax.swing.JComboBox local;
    private javax.swing.JPanel panCotForPag;
    private javax.swing.JPanel panCotForPagNo;
    private javax.swing.JPanel panCotForPagNorCen;
    private javax.swing.JPanel panCotGen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenNorNor;
    private javax.swing.JPanel panCotGenNorSur;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panCotNor;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabCotCom;
    private javax.swing.JTable tblCotForPag;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField tx_numped;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliDir;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCot;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtVenCod;
    private javax.swing.JTextField txtVenNom;
    // End of variables declaration//GEN-END:variables
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private javax.swing.JTextField txtCod =  new javax.swing.JTextField();
    private javax.swing.JTextField txtDes =  new javax.swing.JTextField();
    private javax.swing.JTextField txtVehRet =  new javax.swing.JTextField();
    private javax.swing.JTextField txtChoRet =  new javax.swing.JTextField();
    
    
    
    public void Actualizar_valores(){
        java.sql.Connection conTipDoc;
        java.sql.PreparedStatement pstReg;
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                String sql="UPDATE tbm_cabcotven set nd_sub="+txtSub.getText()+", nd_valiva="+txtIva.getText()+",nd_tot="+txtTot.getText()+" where co_emp="+objZafParSis.getCodigoEmpresa()+"" +
                " and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+txtCot.getText();
                pstReg = conTipDoc.prepareStatement(sql);
                pstReg.executeUpdate();
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
    }
    
    
    
    
    public String retTelEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_tel from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_tel");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
        return auxTipDoc;
        
    }
    
    
    
    
    
    public String retNomEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_nom, tx_dir from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
                //   //System.out.println("el query del nombre de la empresa es:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
                    GLO_strnomEmp=rstTipDoc.getString("tx_nom");
                    GLO_strdirEmp=rstTipDoc.getString("tx_dir");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e) {
            // objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            // objUti.mostrarMsgErr_F1(this, e);
        }
        return auxTipDoc;
        
    }
    
    
    
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_ITMALT:
                    strMsg="Codigo Item";
                    break;
                case INT_TBL_DESITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_CANMOV:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_PREUNI:
                    strMsg="Precio";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
        
    }
    
    
    
    
    
    private void listaProveedor(String strDesBusqueda){
        
        objVenCon.setTitle("Listado de Proveedores");
        objVenCon.show();
        if (objVenCon.getSelectedButton()==objVenCon.INT_BUT_ACE) {
            tblDat.setValueAt(objVenCon.getValueAt(3),tblDat.getSelectedRow(),INT_TBL_COD_PRO);
            tblDat.setValueAt(objVenCon.getValueAt(1),tblDat.getSelectedRow(),INT_TBL_NOM_PRO);
        }
        
    }
    
    
    private class ButFndPrv extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButFndPrv(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        public void butCLick() {
            
            int intNumFil = tblDat.getSelectedRow();
            if(intNumFil >= 0 ) {
                blnChangeData = false;
                String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                
                if(!(strTipTer.trim().equals("S"))) {
                    objTblCelEdiTxtVcoPrv.setCancelarEdicion(true);
                }else {
                    
                    listaProveedor("");
                    if (objVenCon2.getSelectedButton()==objVenCon.INT_BUT_ACE) {
                        tblDat.requestFocus();
                        tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_PRE_COS, false, false);
                    }}}}
    }
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener {
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            switch (e.getType()) {
                case javax.swing.event.TableModelEvent.INSERT:
                    //                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calculaTot();
                    calculaPag();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    public class ZafAutPrg extends Librerias.ZafAut.ZafAut {
        public ZafAutPrg(javax.swing.JInternalFrame ifr) {
            super(ifr,objZafParSis);
        }
        
        /**
         * Esta función determina si es necesaria una autorización para grabar el documento.
         * Por lo general al hacer un cheque no es necesaria autorización. Pero, hay ocasiones
         * en las que se puede establecer el campo "tbm_plaCta.st_aut" para que se solicite
         * autorización para poder emitir un cheque de una determinada cuenta bancaria.
         * @return true: Si el documento requiere autorización.
         * <BR>false: En el caso contrario.
         */
        
        
        
        //------------------------------------------------------------------------------
        /**
         * CONTROL: de ZafVen02 (Facturas de Ventas)
         * Descripcion:
         * El precio de venta digitado
         * por el usuario no debe ser
         * menor al precio unitario del articulo.
         */
        public boolean isPreVtaMenorPreUni(){
            boolean blnRes = true;
            int INT_TBL_COITM = 17 ;
            int INT_TBL_PREVEN= 11 ;
            boolean  blnValPre=true;
            
            double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = Glo_dlbMaxDes, //getDesCli(txtCliCod.getText()),
            dblMinimo, dblValorVenta, dblCostoItm=0, dblPorDesCom=0, dblPorGanancia = Glo_dlbMarUti; //getMarUti(txtCliCod.getText());
            int intCoItm;
            double dblMarUtiItm=0;
            //**********************
            if(intTipMod==2) {
                String FecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(),"yyyy/MM/dd");
                Librerias.ZafDate.ZafDatePicker objDate =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y") ;
                java.util.Calendar objFec = java.util.Calendar.getInstance();
                Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                int fecDoc [] = txtFecDoc.getFecha(dateFecCot);
                if(fecDoc!=null){
                    objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                    objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                    objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
                }
                java.util.Calendar objFecPagActual = objFec.getInstance();
                objFecPagActual.setTime(objFec.getTime());
                objFecPagActual.add(java.util.Calendar.DATE, intNumDiaVal);
                dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
                dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
                dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
                String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
                
                java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
                java.util.Date fe2 = objUti.parseDate(FecAux,"yyyy/MM/dd");
                
                if(fe1.equals(fe2))
                    blnValPre=false;
                else if(fe1.after(fe2))
                    blnValPre=false;
                else
                    System.out.println(" ");  //NO HAY DATOS DE FECHA...");
                
            }
            
            //**********************
       
            boolean Estado = false;
            if(txtCot.getText().trim().equals("")){
                Estado = false;
                blnValPre=true;
            }
            else{
                ///  //System.out.println(" VALIDACION DE PRECIO ");
                blnRes =  VerificaPermisodeImpresion(strNomFuncValPre,CONN_GLO);
            }
            
            blnHayCam = blnHayCam_glo;
            
            if( blnValPre==true  ||  blnHayCam_glo_tbl==true  ) {
                blnRes = true;
                //   //System.out.println(" ******************************************** ENTRA A VALIDAR PRECIO 1 ");
                
                for(int i = 0 ; i < tblDat.getRowCount();i++){
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                        dblPreUsr    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString()))),6);
                        
                        
                        
                        dblMarUtiItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_MARUTI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_MARUTI).toString()))),2);
                        
                        
                        
                        
                        //**********************
                        if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                            if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                dblPreUniItm=0;
                            else
                                dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                
                        }else
                            dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                            
                            //**********************
                            
                            dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                            dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
                            
                            int intTipPre=0;
                            //****************
                            if(dblPreUniItm!=0){
                                dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
                                intTipPre=1;
                            }else{
                                
                                double dblStkAct = 0;
                                
                                //*************************
                                if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                                    if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                        dblStkAct=0;
                                    else
                                        dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                }else
                                    dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                    
                                    //**************************
                                    
                                    
                                    
                                    if(!(tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equals("S"))) { //if(dblStkAct != 0)
                                        dblCostoItm  =   getCosUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                                        //System.out.println(" Costo >>> "+ dblCostoItm );
                                    }else{
                                        
                                        //System.out.println(" Costo por else >>> " );
                                        
                                        if(tblDat.getValueAt(i,INT_TBL_PRE_COS) == null) dblCostoItm=0.00;
                                        else   dblCostoItm = Double.parseDouble(tblDat.getValueAt(i,INT_TBL_PRE_COS).toString());
                                        
                                        
                                        if(tblDat.getValueAt(i,INT_TBL_DESPRECOM) == null) dblPorDesCom=0.00;
                                        else  dblPorDesCom = Double.parseDouble(tblDat.getValueAt(i,INT_TBL_DESPRECOM).toString());
                                        
                                        dblCostoItm=objUti.redondear((dblCostoItm-(dblCostoItm*(dblPorDesCom/100))),2);
                                        
                                        
                                    }
                                    dblMinimo    =   dblCostoItm * dblPorGanancia;
                                    dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
                            }
                            
                            
                            dblValorVenta=objUti.redondear(dblValorVenta,3);
                            dblMinimo=objUti.redondear(dblMinimo,3);
                            
                            
                            
                            
                            double dblPor = (1 - (dblCostoItm/dblValorVenta))*100;
                            
                            
                            dblPor=objUti.redondear(dblPor,3);
                            
                            
                            if(intTipPre==1){
                               // System.out.println(" oki >>> "+ dblValorVenta +" < "+ dblMinimo );
                                if(dblValorVenta<dblMinimo){
                                    blnRes = false;
                                    tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                }
                            }else{
                              //  System.out.println(" oki >>> "+ dblValorVenta +" < "+ dblCostoItm );
                             //   System.out.println(" Porsentaje  >>> "+ dblPor  );
                                if( dblPor < dblMarUtiItm ){
                                    blnRes = false;
                                    tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                    //tblDat.setValueAt(new Boolean(true),i,INT_TBL_BLNPRE);
                                }}
                            
                            
                    } }
            }
            
            
            blnHayCam=blnHayCam_glo;
            
            
            return blnRes;
        }
        
        
        
        
        
        
         
        
        
        
        
    /*
     * FUNCION QUE PERMITE CONTROLAR EL PRECIO DEL ITEM
     * Y TAMBIEN LA FORMA DE PAGO
     *
     */
        public boolean isPreUniVtaValForPagAut(){
            boolean blnRes = true;
            int INT_TBL_COITM = 17 ;
            int INT_TBL_PREVEN= 11 ;
            boolean  blnValPre=true;
            
            double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = Glo_dlbMaxDes,
            dblMinimo, dblValorVenta, dblCostoItm=0, dblPorDesCom=0, dblPorGanancia = Glo_dlbMarUti;
            int intCoItm;
            double dblMarUtiItm=0;
            
            //**********************
            if(intTipMod==2) {
                String FecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(),"yyyy/MM/dd");
                Librerias.ZafDate.ZafDatePicker objDate =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y") ;
                java.util.Calendar objFec = java.util.Calendar.getInstance();
                Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                int fecDoc [] = txtFecDoc.getFecha(dateFecCot);
                if(fecDoc!=null){
                    objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                    objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                    objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
                }
                java.util.Calendar objFecPagActual = objFec.getInstance();
                objFecPagActual.setTime(objFec.getTime());
                objFecPagActual.add(java.util.Calendar.DATE, intNumDiaVal);
                dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
                dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
                dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
                String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
                
                java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
                java.util.Date fe2 = objUti.parseDate(FecAux,"yyyy/MM/dd");
                
                if(fe1.equals(fe2))
                    blnValPre=false;
                else if(fe1.after(fe2))
                    blnValPre=false;
                else
                    System.out.println(" "); //NO HAY DATOS DE FECHA...");
                
            }
            
            //**********************
              
              
            boolean Estado = false;
            if(txtCot.getText().trim().equals("")){
                Estado = false;
                blnValPre=true;
            }
            else{
             
                blnRes =  VerificaPermisodeImpresion(strNomFuncValPre,CONN_GLO);
            
            }
            
            blnHayCam = blnHayCam_glo;
            
            if( blnValPre==true  ||  blnHayCam_glo_tbl==true  || blnHayCam==true ) {
                blnRes = true;
                //   //System.out.println(" ******************************************** ENTRA A VALIDAR PRECIO 1 ");
                
                for(int i = 0 ; i < tblDat.getRowCount();i++){
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                        dblPreUsr    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString()))),6);
                        
                        dblMarUtiItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_MARUTI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_MARUTI).toString()))),2);
                        
                        
                        //**********************
                        if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                            if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                dblPreUniItm=0;
                            else
                                dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                
                        }else
                            dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                            
                            //**********************
                            
                            dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                            dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
                            int intTipPre=0;
                            if(dblPreUniItm!=0){
                                dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
                                intTipPre=1;
                            }else{
                                
                                double dblStkAct = 0;
                                
                                //*************************
                                if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                                    if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                        dblStkAct=0;
                                    else
                                        dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                }else
                                    dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                    
                                    //**************************
                                    
                                    if(!(tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S")))   //if(dblStkAct != 0)
                                        dblCostoItm  =   getCosUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                                    else{
                                        if(tblDat.getValueAt(i,INT_TBL_PRE_COS) == null) dblCostoItm=0.00;
                                        else   dblCostoItm = Double.parseDouble(tblDat.getValueAt(i,INT_TBL_PRE_COS).toString());
                                        
                                        
                                        if(tblDat.getValueAt(i,INT_TBL_DESPRECOM) == null) dblPorDesCom=0.00;
                                        else  dblPorDesCom = Double.parseDouble(tblDat.getValueAt(i,INT_TBL_DESPRECOM).toString());
                                        
                                        dblCostoItm=objUti.redondear((dblCostoItm-(dblCostoItm*(dblPorDesCom/100))),2);
                                        
                                    }
                                    dblMinimo    =   dblCostoItm * dblPorGanancia;
                                    dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
                            }
                            
                            
                            dblValorVenta=objUti.redondear(dblValorVenta,3);
                            dblMinimo=objUti.redondear(dblMinimo,3);
                            
                         
                            double dblPor = (1 - (dblCostoItm/dblValorVenta))*100;
                           // System.out.println(" Porsentaje  >>> "+ dblValorVenta  + "   -- "+  dblPor  );
                            dblPor=objUti.redondear(dblPor,3);
                            
                            if(intTipPre==1){
                                if(dblValorVenta<dblMinimo){
                                    blnRes = false;
                                    tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                }
                            }else{
                                if( dblPor < dblMarUtiItm ){
                                    blnRes = false;
                                    tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                    //tblDat.setValueAt(new Boolean(true),i,INT_TBL_BLNPRE);
                                }}
                            
                            
                    } }
            }
            
            
            blnHayCam=blnHayCam_glo;
            
            
            if(blnRes) {
                //System.out.println("Valida forma de pago ");
                blnRes=isUsrForPag();
            }
            
            //  //System.out.println("ENTRA LA NUEVA  VALIDACION ..."+ blnRes );
            
            
            return blnRes;
        }
        
        
        
        public boolean VerificaPermisodeImpresion(String funcion, java.sql.Connection conn) {
            boolean lbnEst=false;
            int IntCodEmp=objZafParSis.getCodigoEmpresa();
            int IntCodLoc=objZafParSis.getCodigoLocal();
            String StrCodCot=txtCot.getText();
            
            
            String sql = "SELECT b.st_reg,b.st_cum FROM tbm_cabautcotven as a" +
            " INNER JOIN tbm_detautcotven AS b ON (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_cot=b.co_cot and a.co_aut=b.co_aut)" +
            " INNER JOIN tbm_regneg as c ON(b.co_emp=c.co_emp and b.co_loc=c.co_loc and b.co_regneg=c.co_reg and c.co_mnu="+objZafParSis.getCodigoMenu()+" and c.tx_nomfun='"+funcion+"')" +
            " WHERE a.co_cot="+StrCodCot+" and a.co_emp="+IntCodEmp+" and a.co_loc="+IntCodLoc+""+
            " AND b.co_aut=( select max(co_aut) FROM  tbm_cabautcotven WHERE co_emp="+IntCodEmp+" AND co_loc="+IntCodLoc+" AND  co_cot="+StrCodCot+" )";
            
            
            try{
                if (conn!=null){
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(sql);
                    if(rst.next()){
                        if(rst.getString(1) != null ){
                            if(rst.getString(2).equals("S")){
                                lbnEst=true;
                            }
                            else if(rst.getString(1).equals("A")){
                                lbnEst=true;
                            }
                            else {
                                lbnEst=false;  //  TIENE PROBLEMA DE PRECIO Y NO ESTA AUTORIZADA
                            }}}
                    else
                        lbnEst = true;
                    
                    rst.close();
                    stm.close();
                }}
            catch(SQLException  Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return lbnEst;  }
            return lbnEst;
        }
        
        
        
        
        
        private double getMarUti(String int_co_cli){
            double dblMarUti = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL= "SELECT nd_maruti from tbm_cli as cli " +
                    " where cli.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                    "       cli.co_cli = " + int_co_cli ;
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblMarUti = rstUni.getDouble("nd_maruti");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblMarUti;  }
            catch(Exception Evt) { return dblMarUti; }
            return dblMarUti;
        }
        
        
        
        private double getDesCli(String int_co_cli){
            double dblDesCli = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL= "SELECT nd_maxdes from tbm_cli as cli " +
                    " where cli.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                    "       cli.co_cli = " + int_co_cli ;
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblDesCli = rstUni.getDouble("nd_maxdes");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt)  {  return dblDesCli; }
            catch(Exception Evt){ return dblDesCli;  }
            return dblDesCli;
        }
        
        
        //**********************************************//////*************************************************
        
        
        private double getCosUni(String int_co_itm){
            double dblPreUni = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL = "  SELECT a1.nd_cosUni FROM tbm_inv AS a1"+
                    " INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                    " WHERE a2.co_emp="+ objZafParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objZafParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblPreUni = rstUni.getDouble("nd_cosuni");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblPreUni; }
            catch(Exception Evt){ return dblPreUni;  }
            return dblPreUni;
        }
        
        
        //**********************************************//////*************************************************
          
        /**
         * CONTROL: de ZafVen02 (Facturas de Ventas)
         * Descripcion:(descuento para vendedor)
         * El porcentaje de descuento debe estar dentro de los paramtros
         * establecidos para este vendedor
         */
        public boolean isDesForVen(){
            for(int i = 0 ; i < tblDat.getRowCount()-1;i++){
                double dblDescAsignado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                if(dblDescAsignado>25)
                    return false;
            }
            return true;
        }
        
        /**
         * Recorre la tabla de detalle de pagos para verificar
         * ke los dias de creditp sean 0 en cuyo caso retornara true
         * @return  true si la venta se esta ralizando de contado
         */
        private boolean isVtaCon(){
            boolean blnIsVtaCon = true;
            for(int i= 0 ; i<tblCotForPag.getRowCount() && blnIsVtaCon; i++){
                if( ((tblCotForPag.getValueAt(i,  INT_TBL_DIACRE)==null)?0:Integer.parseInt(tblCotForPag.getValueAt(i,  INT_TBL_DIACRE)+""))>0){
                    blnIsVtaCon = false;
                }
            }
            return blnIsVtaCon;
        }
        
        
        
        
        /** 
         * CONTROL: para las ventas de contado.
         */
        public boolean isVtaConMon(){
            boolean blnisDocVen = true;
            String sSQL="";
            try{
                blnisDocVen = true;
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    
                    
                    double dlbSumMon=0;
                    for(int i=0; i<tblCotForPag.getRowCount();i++){
                        String strDiaCre = (( tblCotForPag.getValueAt(i, 1)==null || tblCotForPag.getValueAt(i, 1).equals(""))?"0":tblCotForPag.getValueAt(i, 1).toString());
                        String strRet = (( tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).equals(""))?"0":tblCotForPag.getValueAt(i, 3).toString());
                        
                        if(strDiaCre.trim().equals("0")){
                            if(strRet.trim().equals("0")){
                                dlbSumMon += Double.parseDouble( ((tblCotForPag.getValueAt(i, 4)==null || tblCotForPag.getValueAt(i, 4).equals(""))?"0":tblCotForPag.getValueAt(i, 4).toString()));
                            }}
                    }
                    
                    sSQL = "SELECT numVenCon, abs(valor) ,  ne_nummaxvencon, nd_monmaxvencon  " +
                    "  ,CASE WHEN numVenCon>=ne_nummaxvencon THEN 1 else 0  END  AS NumVenCon " +
                    "  ,CASE WHEN (abs(valor)+"+dlbSumMon+")>=nd_monmaxvencon THEN 1 else 0  END AS MonCanVen " +
                   
                    ",( "+
                    " SELECT count(distinct(a1.co_doc)) as numVenCon FROM tbm_cabMovInv AS a1 "+
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   "+
                    " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_tipdoc=1 "+
                    " AND a1.co_cli="+txtCliCod.getText()+" " +
                    " AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C')  " +
                    " AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)      AND (a2.nd_abo+a2.mo_pag) < 0 " +
                    " ) as maxnumvenconCli "+
                      
                    ", " +
                    "(  select ne_nummaxvencon from tbm_cli where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_cli="+txtCliCod.getText()+" ) as nummaxvenCondecli "+

                    " FROM (" +
                    " SELECT * FROM (" +
                    " 	SELECT count(distinct(a1.co_doc)) as numVenCon FROM tbm_cabforpag as a " +
                    "   inner join tbm_cabMovInv AS a1 on (a1.co_emp=a.co_emp and a1.co_forpag=a.co_forpag)  " +
                    "	INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                    " inner join tbm_cli as cli on (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli and ( cli.ne_diagra<=0  or cli.ne_diagra = null )) "+
                    "	WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_tipdoc=1 "+
                    // AND a1.co_cli="+txtCliCod.getText()+"
                    "   AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C') " +
                    "	AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)" +
                    "	AND (a2.nd_abo+a2.mo_pag) < 0  AND a.ne_tipforpag=1  " +
                    " ) AS x,(" +
                    "	SELECT  sum(a2.nd_abo+a2.mo_pag) as valor   FROM tbm_cabforpag as a " +
                    "   inner join tbm_cabMovInv AS a1 on (a1.co_emp=a.co_emp and a1.co_forpag=a.co_forpag)  " +
                    "	INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                    " inner join tbm_cli as cli on (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli and ( cli.ne_diagra<=0  or cli.ne_diagra = null )) "+  
                    "	WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_tipdoc=1 "+
                    // AND a1.co_cli="+txtCliCod.getText()+"
                    "   AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C') " +
                    "	AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)" +
                    "	AND (a2.nd_abo+a2.mo_pag) < 0  AND  a.ne_tipforpag=1   " +
                    ") AS y" +
                    ",(" +
                    "    SELECT  ne_nummaxvencon, nd_monmaxvencon FROM tbm_loc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
                    " ) AS z "+
                    ") AS x";
                    
                      
                if(dblDiaGraCli == 0 && intTipForPag2==1){  
                    
                   // System.out.println(">>> "+sSQL );
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    while(rstMonCre.next()){
                        if(rstMonCre.getInt("NumVenCon")==1){
                            blnisDocVen = false;
                            break;
                        }else if(rstMonCre.getInt("MonCanVen")==1){
                            blnisDocVen = false;
                            break;
                        }else if(rstMonCre.getInt("maxnumvenconCli") > rstMonCre.getInt("nummaxvenCondecli")){
                            blnisDocVen = false;
                            break;
                        }
                    }
                    rstMonCre.close();
                    rstMonCre = null;
                } 
                   
                    stmMonCre.close();
                    stmMonCre = null;
                }}
            catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt );  blnisDocVen = false;  }
            catch(Exception Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt );  blnisDocVen = false;  }
            
            return blnisDocVen ;
        }
        
        
        
          
        
        
        
        
        /**
         * CONTROL: Que el client no tenga documentos vencidos.   
         */
        public boolean isDocVen(){
            boolean blnisDocVen = true;
            try{
                blnisDocVen = true;
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    
                    String sSQL = "SELECT a2.nd_abo  FROM tbm_cabMovInv AS a1 " +
                    " INNER join tbm_cli as cli ON (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli) "+
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc "+
                    " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "+
                    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND "+
                    " a1.co_tipDoc=a3.co_tipDoc) WHERE a1.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND a1.co_cli=" + txtCliCod.getText()+
                    " AND a1.st_reg IN ('A','R','C','F') AND a1.co_tipdoc=1 AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0  and a2.nd_porret = 0   " +
                    " AND a2.fe_ven+cli.ne_diagra<=CURRENT_TIMESTAMP"+
                    " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                 
                    if(!(dblDiaGraCli == 0 && intTipForPag2==1)){  
                        java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                        if(rstMonCre.next())
                            blnisDocVen = false;
                        rstMonCre.close();
                        rstMonCre = null;
                       /// System.out.println("Si PASA CONTROL DE DOC VENCIDOS "+ intTipForPag2 + " <> " + dblDiaGraCli );
                    }
                    stmMonCre.close();
                    stmMonCre = null;
                }}
            catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt );  blnisDocVen = false;  }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );  blnisDocVen = false;  }
            
            return blnisDocVen ;
        }
        
        
            
        
          
        /**
         * CONTROL: De retenciones
         */
        public boolean isRet(){
            boolean blnisDocRet = true;
            try{
                blnisDocRet = true;
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    
                    String sSQL = "SELECT a2.nd_abo  FROM tbm_cabMovInv AS a1 "+
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc "+
                    " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "+
                    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND "+
                    " a1.co_tipDoc=a3.co_tipDoc) WHERE a1.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND a1.co_cli=" + txtCliCod.getText()+
                    " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0  and a2.nd_porret <> 0   AND a2.fe_ven+a2.ne_diagra<=CURRENT_TIMESTAMP"+
                    " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                    ///   //System.out.println(" por falso  >>> "+ sSQL );
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next())
                        blnisDocRet = false;
                    rstMonCre.close();
                    rstMonCre = null;
                    
                    stmMonCre.close();
                    stmMonCre = null;
                    
                }}
            catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt );  blnisDocRet = false;  }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );   blnisDocRet = false;  }
            
            return blnisDocRet;
        }
        
        
          
        
        
        
        
        
        
        
        
        /**  
         * CONTROL: De Soporte de cheque.
         */
        public boolean isDocSinSop(){
            boolean blnisDocRet = true;
            try{
                blnisDocRet = true;
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    String sSQL = "SELECT a1.co_tipdoc, a2.nd_abo, a2.st_sop, a2.st_entsop  FROM tbm_cabMovInv AS a1" +
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                    " INNER JOIN tbm_cli AS cli ON (cli.co_emp=a1.co_emp and cli.co_cli=a1.co_cli) " +
                    " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" "+  // AND a1.co_loc="+objZafParSis.getCodigoLocal()+" " +
                    "  AND (a2.mo_pag+a2.nd_abo)<>0   AND a1.co_cli="+txtCliCod.getText()+" AND a1.st_reg NOT IN ('I','E') AND a2.st_reg IN ('A','C') " +
                    " AND a2.st_sop='S' AND a2.st_entsop='N' AND a1.fe_doc+cli.ne_diagrachqfec <= CURRENT_TIMESTAMP ";
                   // System.out.println("que "+sSQL); 
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next())
                        blnisDocRet = false;
                    rstMonCre.close();
                    rstMonCre = null;
                    
                    stmMonCre.close();
                    stmMonCre = null;
                }}
            catch(java.sql.SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt );  blnisDocRet = false;  }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );   blnisDocRet = false;  }
            
            return blnisDocRet;
        }
        
        
        
        
        
        
        
    /*
     *control; controla si el cliente tiene cerrado el credito por retencion
     */
        public boolean isCreCerRet(){
            boolean blnisCieCreRet = true;
            try{
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    
                    String sSQL = "SELECT co_cli  FROM tbm_cli AS a1 WHERE a1.co_emp="+ objZafParSis.getCodigoEmpresa() +"" +
                    " AND a1.co_cli=" + txtCliCod.getText()+" and st_cieretpen='S'";
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next())
                        blnisCieCreRet = false;
                    rstMonCre.close();
                    rstMonCre = null;
                    
                    stmMonCre.close();
                    stmMonCre = null;
                    
                }}
            catch(java.sql.SQLException e) { objUti.mostrarMsgErr_F1(jfrThis, e );    }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );   }
            
            return blnisCieCreRet;
        }
        
        
        
          
          
    /*
     *control; controla si el cliente tiene la forma de pago asignado que se ha selecciono
     */
          
     
        public boolean isUsrForPag(){   // ESQUEMA NUEVO DE VALIDACION DE FORMA DE PAGO
            boolean blnisCieCreRet = false;
            try{
                 if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    
                  if(INTPARSISCONF==1){
                    if(intTipForPag2==0){
                          if(intTipForPag==intTipForPag2){
                                blnisCieCreRet = true;
                              
                          }
                    }else{
                        if(dblPes >= dblPes2 ){
                             blnisCieCreRet = true;
                         
                        }
                    }
                   // System.out.println(" forpag2=  "+ intTipForPag2 + " forpag_Cli = "+ intTipForPag + " peso2 "+ dblPes2 + " peso cli: "+ dblPes );
                  }else{
                            
                        String sSQL = "SELECT b.co_forpag, b.tx_des from tbm_forpagcli as a " +
                        " INNER JOIN tbm_cabForPag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) " +
                        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_cli="+txtCliCod.getText()+" " +
                        " and a.co_forpag="+txtCodForPag.getText();
  
                    
                        java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                        if(rstMonCre.next())
                            blnisCieCreRet = true;
                        rstMonCre.close(); 
                        rstMonCre = null;
                    
                  }
                    
                    
                 stmMonCre.close();
                 stmMonCre = null;   
              }}
            catch(java.sql.SQLException e) { objUti.mostrarMsgErr_F1(jfrThis, e );    }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );  }
            return blnisCieCreRet;
        }
        
        
             
          
    /*
     *control; controla si el cliente tiene la forma de pago asignado que se ha selecciono
     */
        public boolean isUsrForPag_esquema_anterior(){   // ESQUEMA ANTERIOR DE VALIDACION DE FORMA DE PAGO
            boolean blnisCieCreRet = false;
            try{
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    
                    String sSQL = "SELECT b.co_forpag, b.tx_des from tbm_forpagcli as a " +
                    " INNER JOIN tbm_cabForPag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) " +
                    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_cli="+txtCliCod.getText()+" " +
                    " and a.co_forpag="+txtCodForPag.getText();
                    
                 
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next())
                        blnisCieCreRet = true;
                    rstMonCre.close();
                    rstMonCre = null;
                    
                    stmMonCre.close();
                    stmMonCre = null;
                    
                }}
            catch(java.sql.SQLException e) { objUti.mostrarMsgErr_F1(jfrThis, e );    }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt );  }
            return blnisCieCreRet;
        }
        
        
        
        
        
        
        
        /**
         *  CONTROL: Que el cliente no sobrepase su cupo de credito establecido
         */
        public boolean isCreCli(){
         boolean blnRes=true; 
          if(!(dblDiaGraCli == 0 && intTipForPag2==1)){ 
                //Obteniendo el cupo de credito para este cliente
                double dblMonCre = getMonCre(txtCliCod.getText());
                //Obteniendo el las cuentas por cobrar a este cliente.
                double dblCxC = getCxC(txtCliCod.getText());
                if(Math.abs(dblTotalCot+dblCxC) > dblMonCre)
                    blnRes=false;
             //  System.out.println("Si PASA CONTROL DE CREDITO DE CLIENTE ");   
          }
              
            return blnRes;
        }
        
        
        /**
         * Metdo que devuelve el total de las cuentas por cobrar de un cliente
         */
        private double getCxC(String int_co_cli){
            double dblCxC = 0;
            try{
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    String sSQL ="select  sum((abs(pag.mo_pag) - pag.nd_abo)) as nd_deuda           " +
                    "from tbm_pagmovinv as pag, tbm_cabmovinv as cab, tbm_cabtipdoc as tipdoc " +
                    "where                                                                    " +
                    " pag.co_emp =   "  + objZafParSis.getCodigoEmpresa() +   "   and   "  +
                    " pag.co_emp = cab.co_emp and      " +
                    " pag.co_emp = tipdoc.co_emp and   " +
                    " pag.co_loc =   "  + objZafParSis.getCodigoLocal()   +   "   and   " +
                    " pag.co_loc = cab.co_loc                              and   " +
                    " pag.co_loc = tipdoc.co_loc                           and   " +
                    " pag.co_tipDoc = cab.co_tipdoc                        and   " +
                    " pag.co_tipDoc = tipdoc.co_tipdoc                     and   " +
                    " pag.co_doc     = cab.co_doc                          and   " +
                    " tipdoc.co_tipdoc  = " + 1                        + " and " +
                    " pag.nd_porret  = 0  and (abs(pag.mo_pag) - pag.nd_abo) > 0  and   " +
                    " cab.st_reg in ('C','A','R','F')                      and   " +
                    " pag.st_reg in ('C','A')                              and   " +
                    " cab.co_cli = " + int_co_cli  ;
                    
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next()){
                        dblCxC = rstMonCre.getDouble("nd_deuda");
                    }
                    
                    rstMonCre.close();
                    stmMonCre.close();
                    rstMonCre = null;
                    stmMonCre = null;
                }
            }
            catch(java.sql.SQLException Evt)  {  return dblCxC;  }
            catch(Exception Evt) {  return dblCxC;  }
            return dblCxC;
        }
        
        /**
         * Metdo que devuelve el cupo de credito de un cliente
         */
        private double getMonCre(String int_co_cli){
            double dblMonCre = 0;
            try{
                if (CONN_GLO!=null){
                    java.sql.Statement stmMonCre = CONN_GLO.createStatement();
                    
                    String sSQL= "SELECT nd_monCre from tbm_cli as cli " +
                    " where cli.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                    "       cli.co_cli = " + int_co_cli ;
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next()){
                        dblMonCre = rstMonCre.getDouble("nd_monCre");
                    }
                    rstMonCre.close();
                    stmMonCre.close();
                    rstMonCre = null;
                    stmMonCre = null;
                    
                }
            }
            catch(java.sql.SQLException Evt) {  return dblMonCre; }
            catch(Exception Evt) { return dblMonCre; }
            return dblMonCre;
        }
    }
    
    
    
    
    
    
    
    private void calcularSaldo(){
        try {
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (con!=null) {
                java.sql.PreparedStatement pst;
                java.sql.ResultSet rstMae;
                java.sql.ResultSet rstMae2;
                java.sql.Statement stmMonCre = con.createStatement();
                java.sql.Statement stmMonCre2 = con.createStatement();
                String sSQL="select distinct(a.co_itm) from tbm_inv as a where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm in("+GLO_strArreItm+") order by co_itm";
                rstMae2 = stmMonCre2.executeQuery(sSQL);
                
                while(rstMae2.next()){
                    
                    String strSec="";
                    
                    
                    sSQL=" SELECT max(ne_secgrp) FROM (" +
                    " SELECT a1.ne_secGrp  FROM tbm_cabMovInv AS a1 " +
                    " INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                    " INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)" +
                    " INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod) " +
                    " INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)" +
                    " INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm) " +
                    " WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+rstMae2.getInt(1)+")" +
                    " ORDER BY a1.ne_secGrp, a2.co_reg " +
                    " ) as x  WHERE x.ne_secgrp<"+Glo_intCodSec;
                    
                    ///     //System.out.println("SQL 2 >> "+ sSQL );
                    
                    rstMae = stmMonCre.executeQuery(sSQL);
                    if(rstMae.next()) strSec=rstMae.getString(1);
                    rstMae.close();
                    
                    
                    
                    
                    sSQL="select * from ( " +
                    "SELECT a1.ne_secGrp ,a2.nd_exigrp, a2.nd_valexigrp, a2.nd_cosprogrp " +
                    "FROM tbm_cabMovInv AS a1 " +
                    "INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)" +
                    "INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)" +
                    "INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)" +
                    "INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)" +
                    "INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)" +
                    " WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+rstMae2.getInt(1)+") " +
                    " AND  a1.ne_secgrp = "+strSec+" "+
                    " ORDER BY a1.ne_secGrp, a2.co_reg  DESC ) as x";
                    
                    rstMae = stmMonCre.executeQuery(sSQL);
                    double ndexigrp=0;
                    double ndvalexigrp=0;
                    
                    
                    if(rstMae.next()){
                        
                        ndexigrp=rstMae.getDouble(2);
                        ndvalexigrp=rstMae.getDouble(3);
                    }
                    
                    
                    
                    sSQL= "select a.ne_secemp" +
                    ",a.co_Emp,a.co_loc ,a.co_doc,a.co_tipdoc,b.co_itm,b.nd_can,b.nd_pordes,b.nd_cosuni,b.nd_exi,b.nd_valexi" +
                    ", b.nd_costotgrp as  total" +
                    " ,a.st_reg,b.co_reg as coreg FROM  tbm_cabmovinv as a" +
                    " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                    " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                    " where  b.co_itm is not null and a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                    " and c.co_itm="+rstMae2.getInt(1)+"  and a.ne_secgrp >= "+Glo_intCodSec+"  order by a.ne_secemp,a.co_emp";
                    
                    
                    rstMae = stmMonCre.executeQuery(sSQL);
                    
                    String streg="";
                    while(rstMae.next()){
                        streg = rstMae.getString("st_reg");
                        if(streg.equalsIgnoreCase("I")){ } else {
                            ndexigrp=ndexigrp + rstMae.getDouble(7);
                            ndvalexigrp = ndvalexigrp - rstMae.getDouble("total");
                        }
                        
                        sSQL= "UPDATE tbm_detmovinv SET nd_exigrp="+ndexigrp+",nd_valexigrp="+ndvalexigrp+"  WHERE co_emp="+rstMae.getInt(2)+" " +
                        " and co_loc="+rstMae.getInt(3)+" and co_tipdoc="+rstMae.getInt(5)+" and " +
                        " co_doc="+rstMae.getInt(4)+" and co_itm="+rstMae.getInt(6)+" and co_reg="+rstMae.getInt("coreg");
                        
                        pst = con.prepareStatement(sSQL);
                        pst.executeUpdate();
                        
                    }
                    
                }
                rstMae=null;
                rstMae2.close();
                rstMae2=null;
            }
            
            con.close();
            con=null;
        }catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    
    
   
    
    private void mostrarMsg(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
    }
    
    
    public boolean VerificaPermisodeImpresion_cot(java.sql.Connection conn) {
        boolean lbnEst=false;
        String sql = "select b.st_reg,b.st_cum from tbm_cabautcotven as a,tbm_detautcotven as b,tbm_regneg  as c"+
        " where a.co_cot="+ txtCot.getText() +" and a.co_cot=b.co_cot "+
        " and  c.co_emp="+ objZafParSis.getCodigoEmpresa() +" and c.co_loc="+ objZafParSis.getCodigoLocal() +" and c.co_mnu="+ objZafParSis.getCodigoMenu() +" and c.tx_nomfun='"+strNomFuncValPre+"'"+
        " and b.co_regneg=c.co_reg"+
        " and a.co_aut  = "+
        " (select max(max.co_aut) from  tbm_detautcotven   as max where  max.co_emp=b.co_emp and  max.co_loc=b.co_loc and  max.co_cot=a.co_cot )"+
        " and b.co_aut=(select max(max.co_aut) from  tbm_detautcotven   as max where  max.co_emp=b.co_emp and  max.co_loc=b.co_loc and  max.co_cot=a.co_cot)";
        try{
            if (conn!=null){
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet rst = stm.executeQuery(sql);
                if(rst.next()){
                    if(rst.getString(1) != null ){
                        if(rst.getString(2).equals("S")){ lbnEst=true; }
                        else if(rst.getString(1).equals("A")){
                            lbnEst=true;
                        }
                        else { lbnEst=false;   }
                    }
                }
                else
                    lbnEst = true;
                
                rst.close();
                stm.close();
                stm=null;
                rst=null;
            }
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);   return lbnEst; }
        return lbnEst;
    }
    
      
    
    
    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
}