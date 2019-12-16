/*
 *  zafCotVen.java         
 */         
package Compras.ZafCom40;

import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco2.ZafTblCelEdiTxtVco2;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import java.util.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
  
 /*
  * @author  jayapata
  *
  */
public class ZafVen01 extends javax.swing.JDialog {

    //mitoolbar objTooBar;
    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;  // Para trabajar con la informacion de tipo de documento
    Librerias.ZafInventario.ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    Librerias.ZafObtConCen.ZafObtConCen  objObtConCen;
    Librerias.ZafUtil.ZafUtil objUti;
    
    private Librerias.ZafDate.ZafDatePicker txtFecRec;
    private ZafAutPrg objAutPrg;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;   //Editor: JButton en celda.
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblModPag;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtCom;        //Editor: JTextField en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut objTblCelEdiBut;        //Editor: JButton en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon objTblCelEdiTxtCon;  //Editor: JTextField de consulta en celda.
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPrv;   //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco2 objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
      
    ZafVenCon objVenCon; //*****************
    ZafVenCon objVenCon2; //*****************
    ZafVenCon objVenConCli; //*****************
    ZafVenCon objVenConVen; //*****************
    ZafVenCon objVenConDirCli; //*****************
    
    String strCodLocSol=null, strCodTipDocSol=null, strCodDocSol=null, strNumCotSol="";
    String DIRECCION_REPORTE="//zafiro//reportes_impresos//RptCotVta.jrxml";
    String DIRECCION_REPORTE2="//zafiro//reportes_impresos//RptFacVta.jrxml";
    String DIRECCION_REPORTE_OC="//zafiro//reportes_impresos//RptOrdComAut.jrxml";
    String DIRECCION_REPORTE_TRANS="C://Zafiro//Reportes_impresos//grupo//listado2.jrxml"; 
    String DIRECCION_REPORTE_TRANS1="C://Zafiro//Reportes_impresos//grupo//detalle.jrxml"; 
    String DIRECCION_REPORTE_GUIAREM="C://zafiro//reportes_impresos//RptGuiRemAut.jrxml";
    String strImpDirecta="printguia"; //"\\\\sistemasa\\Epson LX-300+";
    String strImpDirectaOC="compras"; //"\\\\sistemasa\\Epson LX-300+";
    String strImpDirectaFac="facturacion"; //"\\\\sistemasa\\Epson LX-300+";
    String STR_ESTREG="";
    String sSQL,strSQL, strFiltro;//EL filtro de la Consulta actual
    String dateFecCot; //Variable que contiene la fecha de cotizacion.
    String stIvaVen="S";
    String strFecSis, strFecSisBase;
    String strFecModCot="";
    String strFecHisCot="";
    String GLO_strnomEmp="";
    String GLO_strdirEmp="";
    String GLO_strArreItm="";
    String strNomFuncValPre="isPreUniVtaValForPagAut"; 
    String strCodTipPerCli;
    String strTipPer_emp = "";
    String strarreglosop[] = new String[10];
    String strCodPrv, strDesLarPrv;
    String strCodCom, strDesLarCom;
    String strBeforeValue,strAfterValue;
    String strAux,strstReg_glo="";
    String VERSION = " v22.8 ";
      
    double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    double dblTotalCot, dblIvaCot;
    double dblSubtotalCot;
    double dblPes=0;
    double dblPes2=0;
    double dblDiaGraCli = 0; 
    double Glo_dlbMaxDes=0;
    double Glo_dlbMarUti=0;
    double dblMonMaxVenCon = 0;
    double bldivaEmp=0;
   
    int intCodMotDoc=0;  //  se almacena el codigo del motivo del codumento para las retenciones.
    int Glo_intCodSec=0;
    int intTipForPag2=0;
    int intTipForPag=0;
    int intCodEmp =0;
    int intCodLoc =0;
    int intCodCot=0;
    int intValBus = 0;
    int NumLineas=0;
    int intCodBodPre=0;
    int intNumDiaVal=0;   // Variable que define el numero de dias validos de la cotizacion...
    int intTipMod = 0; // Varible que define si es una modificacion
    int INTCODREGCEN=0;
    int INTVERCONCEN=0;
    int intNumMaxVenCon = 0;
    int INTPARSISCONF=0;
    int intCodMenuOri=0;
    int intTipEmpSel=0;
    int intMosVenAut=0; 
    int intCodTipPerEmp;
    int INTGLONUMOC=0;
    int intNumDec= 2 ; //Numero de decimales a presentar
   
    boolean blnChangeData=false;
    boolean BLN_ESTADO=true;
    boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    boolean blnHayCam_glo = false; //Detecta ke se hizo cambios en el documento
    boolean blnHayCam_glo_tbl = false; //Detecta ke se hizo cambios en el documento
    boolean blnHayCam_Pag = false; //Detecta ke se hizo cambios en pagos
    boolean blnLimpiar = true; //Limpiar o no en el metodo clnTextos
    
    final int intarreglonum[] = new int[10];
    final int intarreglodia[] = new int[10];
    final int intCanArr[]= new int[1];
     
    LisTextos objlisCambios;     // Instancia de clase que detecta cambios
   
    Connection conCot;  //Coneccion a la base donde se encuentra la cotizacion
    Connection conCab=null;  //Coneccion a la base donde se encuentra la cotizacion
    Statement stmCab=null;   //Statement para la cotizacion
    Statement stmCotDet2;    //Statement para la cotizacion
    Statement stmCot;   //Statement para la cotizacion
    ResultSet rstCotCab2=null;    //Resultset que tendra los datos de la cabecera
    ResultSet rstCotDet2;    //Resultset que tendra los datos del Detalle
    ResultSet rstCab;//Resultset que tendra los datos de la cabecera
    
    final int INT_TBL_DIACRE   = 1 ;            //Codigo del item (codigo interno del sistema)
    final int INT_TBL_LINEA    = 0 ;
    final int INT_TBL_ITMALT   = 1 ;
    final int INT_TBL_BUTITM   = 2 ;
    final int INT_TBL_DESITM   = 3 ;
    final int INT_TBL_UNIDAD   = 4 ;
    final int INT_TBL_CODBOD   = 5 ;
    final int INT_TBL_BUTBOD   = 6 ;
    final int INT_TBL_TRATOT   = 7 ;            // transferencia total
    final int INT_TBL_CANMOV   = 8 ;            //Cantidad del movimiento (venta o compra)
    final int INT_TBL_PREUNI   = 9 ;           //Precio de Venta
    final int INT_TBL_PORDES   = 10 ;           //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 11;           //Boolean Iva
    final int INT_TBL_TOTAL    = 12;           //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 13;
    final int INT_TBL_ESTADO   = 14;
    final int INT_TBL_IVATXT   = 15;
    final int INT_TBL_PRE_COS   = 16;            //Columna que contiene  precio de compra
    final int INT_TBL_DESPRECOM   = 17;            //Columna que contiene  el descuento de precio de compra  nd_porDesPreCom
    final int INT_TBL_COD_PRO    = 18;            //Columna que contiene el codigo del proveedor
    final int INT_TBL_NOM_PRO    = 19;            //Columna que contiene el Nombre del proveedor
    final int INT_TBL_BUT_PRO    = 20;            //Columna que contiene para busqueda del proveedor
    final int INT_TBL_BLNPRE   = 21;            //Columna que contiene verdadero si es problema de precio
    final int INT_TBL_ITMALT2   = 22;            //Columna que contiene verdadero si es problema de precio
    final int INT_TBL_ITMSER   = 23;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_ITMTER   = 24;            //Columna que contiene "S" (el item es terminal L ) "N"  no corresponde a la terminal L
    final int INT_TBL_MARUTI   = 25;
    final int INT_TBL_IEBODFIS   = 26;  // estado que dice si ingresa/egresa fisicamente en bodega
    
    final int INT_TBL_PAGLIN=0; //Linea de pago
    final int INT_TBL_PAGCRE=1; // Dias de credito
    final int INT_TBL_PAGFEC=2; // Fecha de vencimiento
    final int INT_TBL_PAGRET=3; // porcentaje de retencion
    final int INT_TBL_PAGMON=4; // monto de pago
    final int INT_TBL_PAGGRA=5; // dias de gracias    
    final int INT_TBL_PAGCOD=6; // codigo de retencion
    final int INT_TBL_PAGSOP=7; // soporte de cheque
    
    javax.swing.JTextArea txtcli_identificacion = new javax.swing.JTextArea();
    javax.swing.JTextArea txtcli_telefono = new javax.swing.JTextArea();
    javax.swing.JTextArea txtcli_ciudad = new javax.swing.JTextArea();
    javax.swing.JTextArea txtEstNomCli = new javax.swing.JTextArea();
    javax.swing.JTextArea txtsql=new javax.swing.JTextArea();
    javax.swing.JTextField txtCodForPag = new javax.swing.JTextField();
    javax.swing.JTextField txtCodForPag2 = new javax.swing.JTextField("");
    java.util.Vector vecLoc, vecEmp;
    javax.swing.JDialog jfrThis; //Hace referencia a this
    java.sql.Connection CONN_GLO, conRemGlo=null;
    java.util.Vector vecForPag, vecForPag2; //Vector que contiene el codigo del pago
   
    /** Creates new form zafVen01 */
//    public ZafVen01(Librerias.ZafParSis.ZafParSis obj) {
   
    
    String strCodEmpGlo="";
    String strCodLocGlo="";
    String strCodCotGlo="";

    
  public ZafVen01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCocCot ) {
       super(parent, modal);

       try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new ZafUtil();
            this.setTitle(objZafParSis.getNombreMenu()+VERSION);
            jfrThis = this;
            
            strCodEmpGlo=strCodEmp;
            strCodLocGlo=strCodLoc;
            strCodCotGlo=strCocCot;
                    
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
            
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;            
            initComponents();
            abrirCon();   /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */
            
//            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//              butSol.setVisible(false);
//              lblSol.setVisible(false);
//            }
            
            txtNomCliVar.setVisible(false);
            intCodEmp = objZafParSis.getCodigoEmpresa();
            intCodLoc = objZafParSis.getCodigoLocal();
            
           // objAutPrg=new ZafAutPrg(this);
            txtCodLoc.setVisible(false);
//            if(objZafParSis.getCodigoEmpresa()==1 || objZafParSis.getCodigoEmpresa()==3){
//               chkSolTraTot.setVisible(false);
//	    } 
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
   
       strFecSis =     objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
       strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText(strFecSis);
        panCotGenNorNor.add(txtFecDoc);
        txtFecDoc.setBounds(116, 30, 92, 20);

        txtFecRec = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
        txtFecRec.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecRec.setText("");
        panCotGenNorNor.add(txtFecRec);
        txtFecRec.setBounds(320, 30, 92, 20);

        //Nombres de los tabs
        tabCotCom.setTitleAt(0,"General");
        tabCotCom.setTitleAt(1,"Forma de Pago");
        //objTooBar = new mitoolbar(this);

        vecForPag = new java.util.Vector();
        vecForPag2 = new java.util.Vector();
               
//          this.getContentPane().add(objTooBar,"South");
        //  pack();
                
           if(System.getProperty("os.name").equals("Linux")){
                    DIRECCION_REPORTE="//zafiro//reportes_impresos//RptCotVta.jrxml";
                    DIRECCION_REPORTE2="//zafiro//reportes_impresos//RptFacVta.jrxml";
                    DIRECCION_REPORTE_OC="//zafiro//reportes_impresos//RptOrdComAut.jrxml";
                    DIRECCION_REPORTE_TRANS="//zafiro//reportes_impresos//grupo//listado2.jrxml"; 
                    DIRECCION_REPORTE_TRANS1="//zafiro//reportes_impresos//grupo//detalle.jrxml"; 
                    DIRECCION_REPORTE_GUIAREM="//zafiro//reportes_impresos//RptGuiRemAut.jrxml";
            }else{
                    DIRECCION_REPORTE="C://zafiro//reportes_impresos//RptCotVta.jrxml";
                    DIRECCION_REPORTE2="C://zafiro//reportes_impresos//RptFacVta.jrxml";
                    DIRECCION_REPORTE_OC="C://zafiro//reportes_impresos//RptOrdComAut.jrxml";
                    DIRECCION_REPORTE_TRANS="C://zafiro//reportes_impresos//grupo//listado2.jrxml"; 
                    DIRECCION_REPORTE_TRANS1="C://zafiro//reportes_impresos//grupo//detalle.jrxml";
                    DIRECCION_REPORTE_GUIAREM="C://zafiro//reportes_impresos//RptGuiRemAut.jrxml";
            }

        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
                spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
         
                objUti.verDecimalesEnTabla(tblPag,4, intNumDec);
                addListenerCambio();
                 
                new Librerias.ZafColNumerada.ZafColNumerada(tblPag,0);
                tblPag.getModel().addTableModelListener(new LisCambioTbl());
                 
                cargarTipEmp();  
                cargarTipParSis();
                FormaRetencion(CONN_GLO);
                // cargarCanMonVtaUsr();
                  
                if(objZafParSis.getCodigoEmpresa() != 1 )  txtCodLoc.setVisible(false);
                    
                intCodMenuOri=objZafParSis.getCodigoMenu();
                objZafParSis.setCodigoMenu(4);
               
                
               // objUti.desactivarCom(this);
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
    
    public ZafVen01(Librerias.ZafParSis.ZafParSis objZafParSis,Integer CodEmp,Integer CodLoc,Integer CodCot) {
        //this(objZafParSis);
        intCodEmp = CodEmp.intValue();
        intCodLoc = CodLoc.intValue();
        intCodCot= CodCot.intValue();
        intValBus = 1;
        intTipEmpSel=1;
        
    }
    
    //************************//*************************************
    
    
    
     //************************//*************************************
    
    public ZafVen01(Librerias.ZafParSis.ZafParSis objZafParSis,Integer CodEmp,Integer CodLoc,Integer CodCot, int intVal) {
        //this(objZafParSis);
        intCodEmp = CodEmp.intValue();
        intCodLoc = CodLoc.intValue();
        intCodCot= CodCot.intValue();
        intValBus = intVal;
        intTipEmpSel=1;
    }
    
    //************************//*************************************
    
    
    
    
    
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
    
    
    
    
    
    
     
    
    public boolean FormaRetencion(java.sql.Connection conTmp){
        boolean blnRes=false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try {
            if (conTmp!=null) {
                stmTmp = conTmp.createStatement();
                String sql = "SELECT co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_bie='S'";
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
    
    
    
    
    
    //      public void cargarCanMonVtaUsr(){
    //        Statement stmTipEmp;
    //        ResultSet rstEmp;
    //        String sSql;
    //        try{
    //                if (CONN_GLO!=null){
    //                       stmTipEmp=CONN_GLO.createStatement();
    //
    //                    sSql="SELECT ne_nummaxvencon,nd_monmaxvencon FROM tbr_usremp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
    //                    " co_usr="+objZafParSis.getCodigoUsuario()+" AND upper(st_ven)='S'";
    //
    //                    rstEmp = stmTipEmp.executeQuery(sSql);
    //                    if(rstEmp.next()){
    //                        intNumMaxVenCon = rstEmp.getInt("ne_nummaxvencon");
    //                        dblMonMaxVenCon   =  rstEmp.getDouble("nd_monmaxvencon");
    //                    }
    //                    rstEmp.close();
    //                    stmTipEmp.close();
    //                    stmTipEmp = null;
    //                    rstEmp = null;
    //                }
    //            }catch(SQLException Evt){
    //                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
    //            }catch(Exception Evt){
    //                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
    //            }
    //    }
    //
    
    
    
    
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
                " where a.ne_mod='1' and b.co_emp="+objZafParSis.getCodigoEmpresa();
                
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
            arlCam.add("a.st_ivaven"); 
            arlCam.add("a.st_peringnomclicotven"); 
            
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
            arlAli.add("IvaVen");
            arlAli.add("EstNomCli");
            
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
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            
            //Armar la sentencia SQL.
            String  strSQL;
            
            strSQL="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper," +
            "  ne_tipforpag,  nd_pes , ne_diagra , co_forpag , tx_des, st_ivaven , st_peringnomclicotven  " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
            " , b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag , b.tx_des, a.st_ivaven, a.st_peringnomclicotven  FROM tbr_cliloc AS a1 " +
            " INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "+
            " WHERE a1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc ="+objZafParSis.getCodigoLocal()+" and a.st_reg IN('A','N')  and a.st_cli='S' order by a.tx_nom  " +
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
           // intColOcu[9]=19;
            
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
            " where a1.st_reg in('A','N')  AND   a1.st_prv = 'S' and a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" order by a1.tx_nom";
            
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
            arlCam.add("a7.proconf");
            
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
            arlAli.add("I/E.Bod.Fis.");
            
            ArrayList arlAncCol=new ArrayList();
            
            /* 
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
            " ELSE 'N' END  as isterL ";            
            
            String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
            " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
            " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
            " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
            " THEN 'S' ELSE 'N' END AS proconf  ";
            */
          
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            if(objZafParSis.getCodigoEmpresa()==2 || objZafParSis.getCodigoEmpresa()==4){
                /*
                Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser";
                Str_Sql+=strAux;
                Str_Sql+=" , case when (trim(SUBSTR (UPPER(a7.tx_codAlt), length(a7.tx_codAlt) ,1)) = 'S' ) then 0 else   a7.nd_stkActcen  end as nd_stkActcen  , a7.nd_maruti";
                Str_Sql+=strAux2;
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
                */
                
                
                Str_Sql = objInvItm.getSqlInvVen();
                
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
                /*
                Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser, a7.nd_maruti";
                Str_Sql+=strAux;
                Str_Sql+=", 0 as nd_stkActcen ";
                Str_Sql+=strAux2;
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
                */
                Str_Sql = objInvItm.getSqlInvVen();
                
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
        Compras.ZafCom40.ZafVen01_01 obj1 = new  Compras.ZafCom40.ZafVen01_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        String  strSQL="";
        int intCodMod=1;
        strSQL+="select distinct(b.co_doc),a.ne_numcot,a.ne_numdoc,a.tx_nomcli,a.fe_doc,b.co_tipdoc, ( b.nd_can * -1 ) as can, b.nd_preuni,b.nd_pordes";
        strSQL+=" from tbm_detmovinv as b,tbm_cabmovinv as a,tbm_cabtipdoc as x";
        strSQL+=" where";
        strSQL+=" x.ne_mod='"+intCodMod+"' and b.co_tipdoc = x.co_tipdoc  and ";
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
    
    
    
    
    
    
    
    
    
    
private boolean configurarTablaPago(){
 boolean blnRes=false;
 try{
      Vector vecCabPAg=new Vector();    //Almacena cabeceras
      vecCabPAg.clear();
      vecCabPAg.add(INT_TBL_PAGLIN,"");
      vecCabPAg.add(INT_TBL_PAGCRE,"Días.Crédito");
      vecCabPAg.add(INT_TBL_PAGFEC,"Fec.Vencimiento");
      vecCabPAg.add(INT_TBL_PAGRET,"%Retención");
      vecCabPAg.add(INT_TBL_PAGMON,"Monto");
      vecCabPAg.add(INT_TBL_PAGGRA,"Días.Gracia");
      vecCabPAg.add(INT_TBL_PAGCOD,"Cod.Ret");
      vecCabPAg.add(INT_TBL_PAGSOP,"Tx_Sop");
      objTblModPag=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
      objTblModPag.setHeader(vecCabPAg);
            
      tblPag.setModel(objTblModPag);
      tblPag.setRowSelectionAllowed(true);
      tblPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      new Librerias.ZafColNumerada.ZafColNumerada(tblPag,INT_TBL_PAGLIN);
      
      objTblModPag.setColumnDataType(INT_TBL_PAGCRE, objTblModPag.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGRET, objTblModPag.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGMON, objTblModPag.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGGRA, objTblModPag.INT_COL_DBL, new Integer(0), null);
           
      tblPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
      javax.swing.table.TableColumnModel tcmAux=tblPag.getColumnModel();
      //Configurar JTable: Establecer el ancho de las columnas.
      //Tamaño de las celdas
      tcmAux.getColumn(INT_TBL_PAGLIN).setPreferredWidth(25);
      tcmAux.getColumn(INT_TBL_PAGCRE).setPreferredWidth(90);      
      tcmAux.getColumn(INT_TBL_PAGFEC).setPreferredWidth(100);
      tcmAux.getColumn(INT_TBL_PAGRET).setPreferredWidth(90);      
      tcmAux.getColumn(INT_TBL_PAGMON).setPreferredWidth(90);
      tcmAux.getColumn(INT_TBL_PAGGRA).setPreferredWidth(90);      
      tcmAux.getColumn(INT_TBL_PAGCOD).setPreferredWidth(60);
      tcmAux.getColumn(INT_TBL_PAGSOP).setPreferredWidth(60);      
      
      tblPag.getTableHeader().setReorderingAllowed(false);
      tcmAux=null; 
      
  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  return blnRes;          
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
            vecCab.add(INT_TBL_BUTITM,"..");
            vecCab.add(INT_TBL_DESITM,"Descripcion");
            vecCab.add(INT_TBL_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_CODBOD,"Bodega");
            vecCab.add(INT_TBL_BUTBOD,"..");
	    vecCab.add(INT_TBL_TRATOT,"Tranf.Tot.");
            vecCab.add(INT_TBL_CANMOV,"Cantidad");
            vecCab.add(INT_TBL_PREUNI,"Precio");
            vecCab.add(INT_TBL_PORDES,"%Desc");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");
            vecCab.add(INT_TBL_CODITM,"coditm");
            vecCab.add(INT_TBL_ESTADO,"estado");
            vecCab.add(INT_TBL_IVATXT,"ivatxt");
            vecCab.add(INT_TBL_PRE_COS,"Pre.Com.");            //Columna que contiene  precio de compra
            vecCab.add(INT_TBL_DESPRECOM , " %Desc.");
            vecCab.add(INT_TBL_COD_PRO,"Cod.prv.");            //Columna que contiene el codigo del proveedor
            vecCab.add(INT_TBL_NOM_PRO,"Proveedor");            //Columna que contiene el Nombre del proveedor
            vecCab.add(INT_TBL_BUT_PRO, "..");
            vecCab.add(INT_TBL_BLNPRE,"blnpre");
            vecCab.add(INT_TBL_ITMALT2,"ItmAlt2");
            vecCab.add(INT_TBL_ITMSER,"Itm.SER.");
            vecCab.add(INT_TBL_ITMTER," isTerL");
            vecCab.add(INT_TBL_MARUTI,"Mar.Uti.");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");
            
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
	    tcmAux.getColumn(INT_TBL_TRATOT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PRE_COS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESPRECOM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_NOM_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUT_PRO).setPreferredWidth(10);
               
                 
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas 
            * */  
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_TRATOT);
            arlColHid.add(""+INT_TBL_CODBOD);
            arlColHid.add(""+INT_TBL_BUTBOD);
            arlColHid.add(""+INT_TBL_CODITM);
            arlColHid.add(""+INT_TBL_IVATXT);
            arlColHid.add(""+INT_TBL_COD_PRO);
            arlColHid.add(""+INT_TBL_BLNPRE);
            arlColHid.add(""+INT_TBL_ESTADO);
            arlColHid.add(""+INT_TBL_ITMALT2);
            arlColHid.add(""+INT_TBL_ITMSER);
            arlColHid.add(""+INT_TBL_ITMTER);
            arlColHid.add(""+INT_TBL_IEBODFIS);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
             
            tblDat.getTableHeader().setReorderingAllowed(false);
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_ITMALT);
            vecAux.add("" + INT_TBL_BUTITM);
            vecAux.add("" + INT_TBL_CODBOD);
            vecAux.add("" + INT_TBL_BUTBOD);
	    vecAux.add("" + INT_TBL_TRATOT);
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
            tcmAux.getColumn(INT_TBL_TRATOT).setCellRenderer(objTblCelRenChk);
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
            new ButFndPrv(tblDat, INT_TBL_BUT_PRO);   //*****
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            
            
            
             vecLoc =  new  java.util.Vector();
             vecEmp =  new  java.util.Vector();
                
                   //System.out.println("intTipEmpSel ==>> "+ intTipEmpSel );  
               
//              if(intTipEmpSel==0){     
//                     if(objZafParSis.getCodigoEmpresa()!=objZafParSis.getCodigoEmpresaGrupo()){
//                            String sql = "select co_loc , tx_nom from tbm_loc where co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal();
//
//                            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
//                            objZafParSis.getClaveBaseDatos(), sql, local, vecLoc);
//
//                            local.setSelectedIndex(0);
//
//
//                            sql = "select co_emp , tx_nom from tbm_emp where co_emp="+objZafParSis.getCodigoEmpresa();
//                            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
//                            objZafParSis.getClaveBaseDatos(), sql, empresa, vecEmp);
//                            empresa.setSelectedIndex(0);
//
//                            empresa.setVisible(false);
//                            lblEmp.setVisible(false);
//                        }else{
//                            String sql = "select co_emp , tx_nom from tbm_emp where co_emp<>"+objZafParSis.getCodigoEmpresaGrupo();
//                            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
//                            objZafParSis.getClaveBaseDatos(), sql, empresa, vecEmp);
//                            empresa.setSelectedIndex(0);
//
//                        }
//             }else{
//                       
//                            String sql = "select co_loc , tx_nom from tbm_loc where co_emp="+intCodEmp+" AND co_loc="+intCodLoc;
//                            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
//                            objZafParSis.getClaveBaseDatos(), sql, local, vecLoc);
//
//                            local.setSelectedIndex(0);
//                            
//
//                            sql = "select co_emp , tx_nom from tbm_emp where co_emp="+intCodEmp;
//                            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
//                            objZafParSis.getClaveBaseDatos(), sql, empresa, vecEmp);
//                            empresa.setSelectedIndex(0);
//
//             }   
            
            int intColVen2[]=new int[11];
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
            intColVen2[10]=13;
            
            int intColTbl2[]=new int[11];
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
            intColTbl2[10]=INT_TBL_IEBODFIS;
            
            
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
        

                tblDat.setValueAt(new Boolean(false), intNumFil, INT_TBL_TRATOT);
       
	
	
        if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV);
        if(tblDat.getValueAt(intNumFil,INT_TBL_PRE_COS)==null) tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PRE_COS);
        if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES);
      
	
	 if(!(tblDat.getValueAt( intNumFil , INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))){
   	    tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PRE_COS);
	    tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_DESPRECOM);
	    tblDat.setValueAt( null , intNumFil,INT_TBL_COD_PRO);
	    tblDat.setValueAt( null ,intNumFil,INT_TBL_NOM_PRO);
	 }
	
 
	 
	    
	    
	    
	
        tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);
        
        if(tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
          
         if(stIvaVen.equals("N")){
            tblDat.setValueAt(new Boolean(false), intNumFil, INT_TBL_BLNIVA);  
          }else{   
            
           if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).toString().trim().equalsIgnoreCase("S"))
                tblDat.setValueAt(new Boolean(true), intNumFil, INT_TBL_BLNIVA);
            else
                tblDat.setValueAt(new Boolean(false), intNumFil, INT_TBL_BLNIVA);
          } 
        
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
        lblFecDoc1 = new javax.swing.JLabel();
        panCotGenNorSur = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtCliDir = new javax.swing.JTextField();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        butCliCon = new javax.swing.JButton();
        txtCodLoc = new javax.swing.JTextField();
        butDirCliCon = new javax.swing.JButton();
        txtNomCliVar = new javax.swing.JTextField();
        PanEmpLoc = new javax.swing.JPanel();
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
        tblPag = new javax.swing.JTable();
        panCotForPagNo = new javax.swing.JPanel();
        panCotForPagNorCen = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        txtNomForPag = new javax.swing.JTextField();
        butCliCon1 = new javax.swing.JButton();
        panCotNor = new javax.swing.JPanel();
        lblCotNumDes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
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
        lblCom.setText("Vendedor:"); // NOI18N
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblCom);
        lblCom.setBounds(430, 10, 70, 15);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha Recordatorio:"); // NOI18N
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNorNor.add(lblFecDoc);
        lblFecDoc.setBounds(218, 30, 108, 15);
        panCotGenNorNor.add(txtAte);
        txtAte.setBounds(500, 30, 144, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte.setText("Atención:"); // NOI18N
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte);
        lblAte.setBounds(430, 30, 60, 15);

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
        txtVenCod.setBounds(500, 10, 35, 20);

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
        txtVenNom.setBounds(530, 10, 135, 20);

        butVenCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butVenCon.setText("..."); // NOI18N
        butVenCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butVenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenConActionPerformed(evt);
            }
        });
        panCotGenNorNor.add(butVenCon);
        butVenCon.setBounds(665, 10, 20, 20);

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
        jLabel1.setText("No. Cotización:"); // NOI18N
        panCotGenNorNor.add(jLabel1);
        jLabel1.setBounds(6, 12, 110, 15);

        tx_numped.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        panCotGenNorNor.add(tx_numped);
        tx_numped.setBounds(320, 10, 92, 20);

        lblAte1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte1.setText("No. Pedido:"); // NOI18N
        lblAte1.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte1);
        lblAte1.setBounds(260, 10, 60, 15);

        lblFecDoc1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc1.setText("Fecha documento:"); // NOI18N
        lblFecDoc1.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNorNor.add(lblFecDoc1);
        lblFecDoc1.setBounds(6, 30, 108, 15);

        panCotGenNor.add(panCotGenNorNor, java.awt.BorderLayout.CENTER);

        panCotGenNorSur.setPreferredSize(new java.awt.Dimension(600, 45));
        panCotGenNorSur.setLayout(null);

        lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPrv.setText("Cliente::"); // NOI18N
        panCotGenNorSur.add(lblPrv);
        lblPrv.setBounds(6, 0, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Dirección:"); // NOI18N
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
        butCliCon.setText("..."); // NOI18N
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
        txtCodLoc.setBounds(484, 16, 30, 10);

        butDirCliCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butDirCliCon.setText("..."); // NOI18N
        butDirCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butDirCliCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDirCliConActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(butDirCliCon);
        butDirCliCon.setBounds(450, 20, 22, 20);
        panCotGenNorSur.add(txtNomCliVar);
        txtNomCliVar.setBounds(480, 0, 210, 20);

        panCotGenNor.add(panCotGenNorSur, java.awt.BorderLayout.SOUTH);

        PanEmpLoc.setPreferredSize(new java.awt.Dimension(0, 25));
        PanEmpLoc.setLayout(null);
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
        lblObs2.setText("Observación 1:"); // NOI18N
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observación 2:"); // NOI18N
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCotGenSurEst.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSubTot.setText("SubTotal:"); // NOI18N
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenSurEst.add(lblSubTot);

        txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblIva.setText("IVA 12%:"); // NOI18N
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);

        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtIva);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12));
        lblTot.setText("Total:"); // NOI18N
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12));
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panCotGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabCotCom.addTab("tab1", panCotGen);

        panCotForPag.setLayout(new java.awt.BorderLayout());

        tblPag.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPag.setColumnSelectionAllowed(true);
        spnForPag.setViewportView(tblPag);

        panCotForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        panCotForPagNo.setEnabled(false);
        panCotForPagNo.setPreferredSize(new java.awt.Dimension(249, 60));
        panCotForPagNo.setLayout(new java.awt.BorderLayout());

        panCotForPagNorCen.setPreferredSize(new java.awt.Dimension(249, 40));

        lblForPag.setText("Forma de Pago:"); // NOI18N
        panCotForPagNorCen.add(lblForPag);

        txtNomForPag.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomForPag.setPreferredSize(new java.awt.Dimension(180, 21));
        panCotForPagNorCen.add(txtNomForPag);

        butCliCon1.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCliCon1.setText("..."); // NOI18N
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

        lblCotNumDes.setText("Cotizaciones"); // NOI18N
        lblCotNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCotNumDes.setOpaque(true);
        panCotNor.add(lblCotNumDes);

        getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
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
            
    private void txtCodLocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodLocKeyPressed
        // TODO add your handling code here:
        
//        if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
//            objTooBar.consultarLoc();
//            
//            objTooBar.cierraConnections();
//            blnLimpiar = false;
//            txtCot.setText("");
//            txtFecDoc.setHoy();
//            objTooBar.setEstado('n');
//            blnLimpiar = true;
//        }
//        
//        
        
    }//GEN-LAST:event_txtCodLocKeyPressed
    
    private void butCliCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliCon1ActionPerformed
        // TODO add your handling code here:
        Compras.ZafCom40.ZafVen01_04 obj = new  Compras.ZafCom40.ZafVen01_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        
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
                rstDocCab2.close();
                rstDocCab2=null;
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
            //objTooBar.consultar();
            //objTooBar.setEstado('w');
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
    
        
    
private void facturar(){
 int intValFac=1; 
 try{   
   abrirCon();

    if(intCodMenuOri == 1612){
        if(!validacionfechaFac(CONN_GLO)){
            MensajeInf("NO ES POSIBLE FACTURAR TIENE QUE SER 5 DIAS ANTES DE TERMINAR EL MES. ");
            intValFac=0;
       }  
    
      if(!FechaPriDiaMes(CONN_GLO) ){
          MensajeInf("NO ES POSIBLE FACTURAR TIENE QUE SER DEL MISMO AÑO EN CURSO. ");
          intValFac=0;
      }
        
    }

//    if(intValFac==1) {
//       blnHayCam_glo=blnHayCam;
//       blnHayCam_glo_tbl=objTblMod.isDataModelChanged();
//
//       if(!objTooBar.EstadoFacturado("Facturar",CONN_GLO)){
//        if (validaCamFac()){
//          calculaSubTotTodLosItm();
//          if(blnHayCam_glo_tbl==false)
//             objTblMod.setDataModelChanged(false);
//             objTooBar.insertaCotToFac(CONN_GLO);
//     }}}
  CerrarCon();
 }catch(Exception evt){ objUti.mostrarMsgErr_F1(jfrThis, evt);}  
}




        private boolean FechaPriDiaMes(java.sql.Connection conTmp){
            boolean blnRes=false;
            String sql="";
            java.sql.Statement stmTmp;
            java.sql.ResultSet rst;
            try{
                sql = "select * from ( " +
                " select extract(year from fecha) as fe1, extract(year from current_date) as fe2  from ( " +
                "" +
                        "SELECT min(fecha) as fecha FROM (" +
                "   select  fecha, " +
                "   case extract(dow from fecha) " +
                "   when 1 then 1  " +
                "   when 2 then 2  " +
                "   when 3 then 3  " +
                "   when 4 then 4  " +
                "   when 5 then 5  " +
                "   when 6 then 6  " +
                "   else 0   " +
                "   end as dia,  " +
                " ( " +
//                "     select count(fe_diafer) from tbm_loc as a  " +
//                "     inner join tbm_ciu as b on (b.co_ciu=a.co_ciu) " +
//                "     inner join tbr_diaFerCiu as  c on (c.co_ciu=b.co_ciu) " +
//                "     inner join tbm_diaFer as  d on (d.co_diafer=c.co_diafer)  " +
//                "     where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and d.fe_diafer=fecha " +
                "     select count(fe_dia) from tbm_loc as a  " +
                "     inner join tbm_calciu as b on (b.co_ciu=a.co_ciu) " +
                "     where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and b.fe_dia=fecha and b.tx_tipdia = 'F' " +
                " ) as xx " +
                " FROM ( " +
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 01)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 02)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 03)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 04)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 05)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 06)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 07)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 08)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 09)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 10)  as fecha            "+
                " )  as x "+
                " ) as y   where  y.dia!=0 and xx=0" +
                        "" +
                " ) as x ) as  x  where fe1 = fe2 ";
                
              //  System.out.println("FECHA ==>  "+ sql );
                
                if (conTmp!=null){
                    stmTmp = conTmp.createStatement();
                    rst=stmTmp.executeQuery(sql);
                    if(rst.next()){
                       blnRes=true; 
                    }
                    rst.close();
                    stmTmp.close();
                    stmTmp=null;
                    rst=null;
                }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
            catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
            return blnRes;
        }
        
        

    
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
    
    
    
    
    
    
    
     
    /**
     * Esta Funcion permite realizar ordenes de compra de manera automatica 
     * esta funcion llama a la clase ZafVen01_OC que es la que se presenta con los datos
     * cargados que va a permitir que el usuario presione Guardar y luego de guardar continuara con el 
     * proceso de facturacion  que luego se manda a imprimir la factura.
     * @param  conLoc  : recive la coneccion de la base local 
     * @parem  conRem  : recive la coneccion de la base remota
     * @param  intTipDoc : recive el tipo de documento 
     * @param  intCodDoc : recive codigo del documento
     */
    private boolean realizaOrdComAut(java.sql.Connection conLoc, java.sql.Connection conRem, int intTipDoc, int intCodDoc){
        int intCodCot=Integer.parseInt(txtCot.getText());
        String sql="";
        boolean blnRes=true;
        
        try{
            if (conLoc!=null){
                
                //*****************************************************************************
                java.sql.Statement stm = conLoc.createStatement();
                java.sql.Statement stm_det = conLoc.createStatement();
                
                //******************************************************************************
                
                StringBuffer stb=new StringBuffer();
                int intSer=0;
                double dblCan=0;
                for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                    String stritem="", strcodalt="";
                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                        if(tblDat.getValueAt(intRowVal,INT_TBL_ITMSER).toString().equalsIgnoreCase("N")){   // SI NO ES DE SERVICIO
                            if(tblDat.getValueAt(intRowVal,INT_TBL_ITMTER).toString().equalsIgnoreCase("N")){
                                stritem=tblDat.getValueAt(intRowVal,INT_TBL_CODITM).toString();
                                strcodalt=tblDat.getValueAt(intRowVal,INT_TBL_ITMALT).toString();
                                
                                dblCan= Double.parseDouble(tblDat.getValueAt(intRowVal,INT_TBL_CANMOV).toString());
                                if (intSer>0)
                                    stb.append(" UNION ALL ");
                                stb.append("SELECT (SUM(a2.nd_stkAct)- "+dblCan+") AS nd_stkAct,"+stritem+" as coitm, '"+strcodalt+"' as codalt"+
                                " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                                " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                                " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+stritem+")");
                                intSer=1;
                            }}} }
                
                int intValStock =0;
                String item="";
                java.sql.ResultSet rst2;
                
                if(intSer==1){
                    String sql2 = "select coitm, codalt from ("+stb.toString()+") as x where nd_stkact<0";
                    
                    
                    if(conRemGlo!=null) {
                        java.sql.Statement stmRem = conRemGlo.createStatement();
                        rst2=stmRem.executeQuery(sql2);
                        stmRem=null;
                    } else {
                        rst2=stm.executeQuery(sql2);
                    }
                    
                    while(rst2.next()){
                        item=item+" \n "+ rst2.getString(2);
                        intValStock=1;
                        
                    }}
                
                
                if( intValStock ==  1 ){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="<< No Hay stock "+item+"\n Estos items no cumple la terminal para la compra   >> ";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                //********************************************************************************************************************
                
                INTGLONUMOC=0;
                
                int intCodPrv=0;
                int intTipo=0;
                int x=0;
                     
                //*******************************************  REALIZA ORDEN DE COMPRA ***************************************************/
                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";
                
                sql="SELECT distinct(co_prv) from (" +
                " SELECT a.co_itm, a.nd_can, a.co_prv";
                sql+=strAux;
                sql+=" FROM tbm_detcotven AS a " +
                "INNER JOIN tbm_inv as inv ON (inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) " +
                "WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_cot="+intCodCot+" " +
                " And co_prv in ( " +
                " SELECT  distinct(co_prv) FROM tbm_detcotven WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+intCodCot+" and co_prv<>0 " +
                " )) AS x where x.isterl='S' ";
                
                stm = conLoc.createStatement();
                rst2=stm.executeQuery(sql);
                while(rst2.next()){
                        
                    intCodPrv = rst2.getInt(1);
                    Compras.ZafCom40.ZafVen01_OC obj1 = new  Compras.ZafCom40.ZafVen01_OC(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, intCodCot, intCodPrv, 45, txtVenCod.getText(), txtVenNom.getText() , conLoc , conRem , intTipDoc , intCodDoc  , INTCODREGCEN, 0 );
                    obj1.show();
                    if(obj1.acepta()){
                        
                        obj1.dispose();
                        obj1=null;
                        blnRes=false;
                        break;
                    }
                    
                    obj1.dispose();
                    obj1=null;
                    x++;
                    INTGLONUMOC++;
                }
                //**********************************************************************************************************************/
                //*******************************************  REALIZA TRANSFERENCIA ***************************************************/
                 
                stb=null;    
                
                   
                 
                
         if(objZafParSis.getCodigoEmpresa()==2 || objZafParSis.getCodigoEmpresa()==4){
                    
                    stb=new StringBuffer();
                     
                    intSer=0;
                    for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                        String stritem="", strcodalt="", strTraTot="";
                        if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                            if(tblDat.getValueAt(intRowVal,INT_TBL_ITMSER).toString().equalsIgnoreCase("N")){   // SI NO ES DE SERVICIO
                                if(tblDat.getValueAt(intRowVal,INT_TBL_ITMTER).toString().equalsIgnoreCase("I")){
                                    stritem=tblDat.getValueAt(intRowVal,INT_TBL_CODITM).toString();
                                    strcodalt=tblDat.getValueAt(intRowVal,INT_TBL_ITMALT).toString();
                                    strTraTot=(tblDat.getValueAt(intRowVal,INT_TBL_TRATOT).toString().equals("true")?"S":"N");
                                    
                                    
                                            
                                    
                                    dblCan= Double.parseDouble(tblDat.getValueAt(intRowVal,INT_TBL_CANMOV).toString());
                                    if (intSer>0)
                                        stb.append(" UNION ALL ");
                                    stb.append("SELECT "+dblCan+" AS can ,"+stritem+" as coitm , text '"+strTraTot+"' as esttra ");
                                    intSer=1;
                    }}} }
                    
                    if(intSer==1){
                        
                        String sql2 = " select * from (  select coitm,   " +
                                " case when esttra in ('N') then (nd_stkAct - abs(can)) else abs(can)*-1 end as valor " +
                                " from ( select  coitm,  esttra,  sum(can) as can from (  " +
                                " "+stb.toString()+"  " +
                        " ) as x group by coitm, esttra  ) as  x " +
                        " inner join tbm_equInv as a1 ON(a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_itm=x.coitm)" +
                        " inner join tbm_equInv as eq ON(eq.co_itmmae=a1.co_itmmae and eq.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+")" +
                        " INNER JOIN tbm_invBod AS a2 ON (eq.co_emp=a2.co_emp AND eq.co_itm=a2.co_itm ) " +
                        " INNER JOIN tbr_bodEmp AS a3 ON (a3.co_emp="+objZafParSis.getCodigoEmpresa()+" and a3.co_loc="+objZafParSis.getCodigoLocal()+" and  a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer ) " +
                        " ) as  x where  valor <0";
                        
                                             
                        if(conRem!=null) {
                            java.sql.Statement stm_remota = conRem.createStatement();
                            rst2=stm_remota.executeQuery(sql2);
                            stm_remota=null;
                        } else {
                            rst2=stm.executeQuery(sql2);
                        }
                        
                        if(rst2.next()){
                            
                            strAux = ",CASE WHEN (" +
                            " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                            " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                            " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
                            " ))) THEN 'S' ELSE 'N' END  as isterL";
                            
                            sql="SELECT count(*) from (" +
                            " SELECT a.co_itm, a.nd_can, a.co_prv";
                            sql+=strAux;
                            sql+=" FROM tbm_detcotven AS a " +
                            "INNER JOIN tbm_inv as inv ON (inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) " +
                            "WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_cot="+intCodCot+" " +
                            " And co_prv in ( " +
                            " SELECT  distinct(co_prv) FROM tbm_detcotven WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+intCodCot+"  " +
                            " )) AS x where x.isterl='S' ";
                            
                            
                            stm = conLoc.createStatement();
                            rst2=stm.executeQuery(sql);
                            while(rst2.next()){
                                if(rst2.getInt(1)>0){
                                    
                                    Compras.ZafCom40.ZafVen01_TRA obj1;
                                   
                                    obj1 = new  Compras.ZafCom40.ZafVen01_TRA(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, intCodCot, 779 ,  conRem, conLoc , intTipDoc , intCodDoc  );
                                       
                                    obj1.show();
                                    if(obj1.acepta()){
                                        
                                        obj1.dispose();
                                        obj1=null;
                                        blnRes=false;
                                        break;
                                    }
                                    
                                    obj1.dispose();
                                    obj1=null;
                                    x++;
                                    INTGLONUMOC++;  
                                }}
                        } }
        }   
     //***************************************************************************************************************/
           
            }
            
        }
        catch (java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  blnRes=false;  }
      
        return blnRes;
    }
    
    
    
    
    
    
      
    
      
    
    
    
    /**
     * Esta Funcion permite realizar ordenes de compra de manera automatica 
     * esta funcion llama a la clase ZafVen01_OC que es la que se presenta con los datos
     * cargados que va a permitir que el usuario presione Guardar y luego de guardar continuara con el 
     * proceso de facturacion  que luego se manda a imprimir la factura.
     * @param  conLoc  : recive la coneccion de la base local 
     * @parem  conRem  : recive la coneccion de la base remota
     * @param  intTipDoc : recive el tipo de documento 
     * @param  intCodDoc : recive codigo del documento
     */
    private boolean realizaOrdComVisPre(java.sql.Connection conLoc, java.sql.Connection conRem, int intTipDoc, int intCodDoc){
        int intCodCot=Integer.parseInt(txtCot.getText());
        String sql="";
        boolean blnRes=true;
        
        try{
            if (conLoc!=null){
                
                //*****************************************************************************
                java.sql.Statement stm = conLoc.createStatement();
                java.sql.Statement stm_det = conLoc.createStatement();
                
                //******************************************************************************
                
                StringBuffer stb=new StringBuffer();
                int intSer=0;
                double dblCan=0;
                for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                    String stritem="", strcodalt="";
                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                        if(tblDat.getValueAt(intRowVal,INT_TBL_ITMSER).toString().equalsIgnoreCase("N")){   // SI NO ES DE SERVICIO
                            if(tblDat.getValueAt(intRowVal,INT_TBL_ITMTER).toString().equalsIgnoreCase("N")){
                                stritem=tblDat.getValueAt(intRowVal,INT_TBL_CODITM).toString();
                                strcodalt=tblDat.getValueAt(intRowVal,INT_TBL_ITMALT).toString();
                                
                                dblCan= Double.parseDouble(tblDat.getValueAt(intRowVal,INT_TBL_CANMOV).toString());
                                if (intSer>0)
                                    stb.append(" UNION ALL ");
                                stb.append("SELECT (SUM(a2.nd_stkAct)- "+dblCan+") AS nd_stkAct,"+stritem+" as coitm, '"+strcodalt+"' as codalt"+
                                " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                                " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                                " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+stritem+")");
                                intSer=1;
                            }}} }
                
                int intValStock =0;
                String item="";
                java.sql.ResultSet rst2;
                
                if(intSer==1){
                    String sql2 = "select coitm, codalt from ("+stb.toString()+") as x where nd_stkact<0";
                    
                    
                    if(conRemGlo!=null) {
                        java.sql.Statement stmRem = conRemGlo.createStatement();
                        rst2=stmRem.executeQuery(sql2);
                        stmRem=null;
                    } else {
                        rst2=stm.executeQuery(sql2);
                    }
                    
                    while(rst2.next()){
                        item=item+" \n "+ rst2.getString(2);
                        intValStock=1;
                        
                    }}
                
                
                if( intValStock ==  1 ){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="<< No Hay stock "+item+"\n Estos items no cumple la terminal para la compra   >> ";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                
                //********************************************************************************************************************
                
                INTGLONUMOC=0;
                
                int intCodPrv=0;
                int intTipo=0;
                int x=0;
                  
                //*******************************************  REALIZA ORDEN DE COMPRA ***************************************************/
                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";
                
                sql="SELECT distinct(co_prv) from (" +
                " SELECT a.co_itm, a.nd_can, a.co_prv";
                sql+=strAux;
                sql+=" FROM tbm_detcotven AS a " +
                "INNER JOIN tbm_inv as inv ON (inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm) " +
                "WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_cot="+intCodCot+" " +
                " And co_prv in ( " +
                " SELECT  distinct(co_prv) FROM tbm_detcotven WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+intCodCot+" and co_prv<>0 " +
                " )) AS x where x.isterl='S' ";
                
                stm = conLoc.createStatement();
                rst2=stm.executeQuery(sql);
                while(rst2.next()){
                         
                    intCodPrv = rst2.getInt(1);
                    Compras.ZafCom40.ZafVen01_OC obj1 = new  Compras.ZafCom40.ZafVen01_OC(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, intCodCot, intCodPrv, 45, txtVenCod.getText(), txtVenNom.getText() , conLoc , conRem , intTipDoc , intCodDoc  , INTCODREGCEN, 1 );
                    obj1.show();

                    
                    obj1.dispose();
                    obj1=null;
                    x++;
                    INTGLONUMOC++;
                }
                //**********************************************************************************************************************/
                //*******************************************  REALIZA TRANSFERENCIA ***************************************************/
                 
                stb=null;    
                
                   
               
            }
            
        }
        catch (java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  blnRes=false;  }
        return blnRes;
    }
    
    
    
    
    
        
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
       
        
//        if(intValBus==1){
//            
//            txtCot.setText(""+ intCodCot);
//            consultar2(new Integer(intCodEmp),new Integer(intCodLoc));
//            objTooBar.setEstado('w');
//           
//        }
//        
            
//        
//         if(intValBus==2){
//            txtCot.setText(""+ intCodCot);
//            consultarHis(new Integer(intCodEmp),new Integer(intCodLoc));
//            objTooBar.setEstado('w');
//          
//        }
//        
        
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
            objInvItm=null;
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
            //objTooBar=null;
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
        //  strTipPer_glo = objVenConCli.getValueAt(6);
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
             stIvaVen=objVenConCli.getValueAt(18);
             txtEstNomCli.setText(objVenConCli.getValueAt(19));
            intTipForPag2 = intTipForPag;
            dblPes2 =dblPes;
            
            
            txtNomCliVar.setVisible((objVenConCli.getValueAt(19).equals("S")?true:false));
            
            CalculoPago();
            
          calculaPag();
          
          llamarVenAut();
          
      }
      else
      {     objVenConCli.setCampoBusqueda(tipo);
            objVenConCli.cargarDatos();
            objVenConCli.show();
            if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
                txtCliCod.setText(objVenConCli.getValueAt(1));
                txtCliNom.setText(objVenConCli.getValueAt(2));
                txtCliDir.setText(objVenConCli.getValueAt(3));
              //  strTipPer_glo = objVenConCli.getValueAt(6);
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
                         stIvaVen=objVenConCli.getValueAt(18);
                         txtEstNomCli.setText(objVenConCli.getValueAt(19));
                        intTipForPag2 = intTipForPag;
                        dblPes2 =dblPes;
                           
                        txtNomCliVar.setVisible((objVenConCli.getValueAt(19).equals("S")?true:false));
                           
                        CalculoPago();
                   
                calculaPag();
                llamarVenAut();
                
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
          //  strTipPer_glo = objVenConCli.getValueAt(6);
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
	    stIvaVen=objVenConCli.getValueAt(18);
            txtEstNomCli.setText(objVenConCli.getValueAt(19));
            intTipForPag2 = intTipForPag;
            dblPes2 =dblPes;
            
               txtNomCliVar.setVisible((objVenConCli.getValueAt(19).equals("S")?true:false));
               
            CalculoPago();
            
            
            calculaPag();
            
            llamarVenAut();
        }
        
        
        
    }//GEN-LAST:event_butCliConActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
        cerrarVen();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
        
        ConfiguraVenCon();
        Configurartabla();
        configurarTablaPago();
        
      
                    
          this._consultar(strCodEmpGlo, strCodLocGlo, strCodCotGlo );
          
    }//GEN-LAST:event_formWindowOpened

    
    
           
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        dispose();
    }
 }
    
    private void llamarVenAut(){
      //   Est = 
      try{    
        
         if(intMosVenAut == 1 ){
            abrirCon();
        
                 objAutPrg.checkCtls(CONN_GLO);
                    
             CerrarCon();
         }
       
                         
//                    if(Est==0){
//                        if( intNumCot !=-1 ){
//                            
//                            int intValor =  objAutPrg.checkCtls_2(CONN_GLO);
       
       
      }//catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
       catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
        
    }
    
        
    
    
//      java.math.BigDecimal j1 = new java.math.BigDecimal("0.00");
//    j1 = new java.math.BigDecimal("9.23").divide( new java.math.BigDecimal( "12.3" ), 6, java.math.BigDecimal.ROUND_HALF_UP );
//    System.out.println (  j1 );  
//    java.math.BigDecimal j2 = new java.math.BigDecimal("0.00");
//    j2= new java.math.BigDecimal("1.0").subtract(j1);  
//    System.out.println (  j2 );  
//    java.math.BigDecimal j3 = new java.math.BigDecimal("0.00");
//    j3 = j2.multiply( new java.math.BigDecimal("100.0") );  
//    System.out.println (   j3.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)  ) ;
//    
    
  //impresionFactura(1, 19460 );  
          
//public boolean impresionFactura(int codtipdoc,int codoc){
// Connection conIns;
// int intCodEmp=objZafParSis.getCodigoEmpresa();
// int intCodLoc=objZafParSis.getCodigoLocal();
// retNomEmp(intCodEmp);
// String nomEmp= GLO_strnomEmp;
// String dirEmp = GLO_strdirEmp;
// String fecha="";
// java.util.Date dtFecHor;
// try{
//    conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//    if(conIns!=null){
//        dtFecHor=new java.util.Date();
//        fecha=objUti.formatearFecha(dtFecHor,"dd-MM-yyyy-hh-mm-ss");
//        JasperDesign jasperDesign = JRXmlLoader.load(DIRECCION_REPORTE2);
//        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//       //para factura
//        String codtipdoc2=String.valueOf(codtipdoc);
//        String codoc2=String.valueOf(codoc);
//        Map parameters = new HashMap();
//        parameters.put("codEmp", ""+intCodEmp);
//        parameters.put("codLoc", ""+intCodLoc);
//        parameters.put("codTipDoc", codtipdoc2);
//        parameters.put("codDoc", codoc2);
//        parameters.put("dirEmp", dirEmp);
//        parameters.put("nomEmp", nomEmp);
//        parameters.put("fechaHor", fecha);
//        parameters.put("obs1", txaObs1.getText());
//        parameters.put("subtot",new Double("0"));
//        parameters.put("iva", new Double("0"));
//        parameters.put("total", new Double("0"));
//                        
//       
//                     
//        if(intCodEmp==1){
//           javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
//           objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
//          // objPriReqAttSet.add(javax.print.attribute.standard.MediaSize.findMedia( (float)100.731, (float)100.720, javax.print.attribute.standard.MediaPrintableArea.MM));
//           JasperDesign jasperDesignGuiaRem = JRXmlLoader.load(DIRECCION_REPORTE_GUIAREM);
//           JasperReport jasperReportGuiaRem = JasperCompileManager.compileReport(jasperDesignGuiaRem);
//          // strImpDirecta=txaObs1.getText().trim();
//           JasperPrint reportGuiaRem =JasperFillManager.fillReport(jasperReportGuiaRem, parameters,  conIns);
//            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strImpDirecta , null);
//            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
//            printServiceAttributeSet.add(printerName);
//            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//            objJRPSerExp.exportReport();
//        }
//        
//        conIns.close();
//        conIns=null;
//
// }}catch (JRException e) {  objUti.mostrarMsgErr_F1(jfrThis, e);  }
//   catch(SQLException ex) { objUti.mostrarMsgErr_F1(jfrThis, ex);   }
// return true;
//}
//








      

private boolean cargarSolPenFac() {
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstCab;
 String strSql="";
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      Vector vecData = new Vector(); //txtCodForPag
      strSql="SELECT CotCab.fe_procon, CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, "+ 
      " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate, "+
      " CotCab.co_forPag, CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, "+
      " CotCab.st_reg , forpag.tx_des, CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, " +
      " Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar, Cli.co_tipper , CotCab.tx_numped , forpag.ne_tipforpag , forpag.nd_pes, " +
      " forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli, Cli.ne_diagra, Cli.st_ivaven, " +
      " CotCab.tx_nomcli, cli.st_peringnomclicotven " +
      " FROM tbm_cabCotven as CotCab " +
      " LEFT JOIN tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
      " LEFT JOIN tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  " +
      " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
      " LEFT JOIN tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
      " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  " +
      " Where CotCab.co_emp ="+objZafParSis.getCodigoEmpresa()+" "+
      " AND CotCab.co_loc="+strCodLocSol+" AND CotCab.co_cot="+strNumCotSol+" AND CotCab.st_reg not in ('E') "+
      " ORDER BY CotCab.co_cot";
     // System.out.println(""+ strSql );
      rstCab=stmLoc.executeQuery(strSql);
      while(rstCab.next()){
        //txtCot.setText(rstCab.getString("co_cot"));
        txtCliCod.setText(((rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli")));
        txtCliNom.setText(((rstCab.getString("nomcli")==null)?"":rstCab.getString("nomcli")));
        txtCliDir.setText(((rstCab.getString("dircli")==null)?"":rstCab.getString("dircli")));
        txtNomCliVar.setText(((rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli")));
        txtNomCliVar.setVisible(((rstCab.getString("st_peringnomclicotven").toString().equals("S")?true:false)));
        //chkSolTraTot.setSelected(false);
        masInformacion(false);
	txtcli_telefono.setText( rstCab.getString("tx_tel") );
        txtcli_identificacion.setText( rstCab.getString("tx_ide") );
        txtcli_ciudad.setText( rstCab.getString("tx_desLar") );
        tx_numped.setText( rstCab.getString("tx_numped") );
        STR_ESTREG=rstCab.getString("st_regrep");
        Glo_dlbMaxDes = rstCab.getDouble("nd_maxdes");
        Glo_dlbMarUti = rstCab.getDouble("nd_maruti");
        strCodTipPerCli =  rstCab.getString("co_tipper");
        stIvaVen = rstCab.getString("st_ivaven");
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
//        if(rstCab.getDate("fe_cot")==null){
            txtFecDoc.setHoy();
//        }else{
//            java.util.Date dateObj = rstCab.getDate("fe_cot");
//            java.util.Calendar calObj = java.util.Calendar.getInstance();
//            calObj.setTime(dateObj);
//            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
//            calObj.get(java.util.Calendar.MONTH)+1     ,
//            calObj.get(java.util.Calendar.YEAR)        );
//        }
        if(rstCab.getDate("fe_procon")==null){
            txtFecRec.setText("");
        }else{
                java.util.Date dateObj = rstCab.getDate("fe_procon");
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecRec.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                calObj.get(java.util.Calendar.MONTH)+1     ,
                calObj.get(java.util.Calendar.YEAR)        );
            }
            
        txtCodForPag.setText(""+rstCab.getString("co_forPag"));
        txtCodForPag2.setText(""+rstCab.getString("co_forPag"));
        txtNomForPag.setText(""+ rstCab.getString("tx_des"));
                
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
       // objTooBar.setEstadoRegistro(strAux);
      }
      rstCab.close();
      rstCab=null;
       
       String strAux = ",CASE " +
       " WHEN ((trim(SUBSTR (UPPER(a.tx_codalt), length(a.tx_codalt) ,1))  IN (" +
       " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
       " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
       " ))) THEN 'S' " +
       "" +
       " WHEN ((trim(SUBSTR (UPPER(a.tx_codalt), length(a.tx_codalt) ,1))  IN (" +
       " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
       " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
       " ))) THEN 'I' " +
       " ELSE 'N' END  as isterL";
      
      String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(a.tx_codalt), length(a.tx_codalt) ,1)) IN ( " +
      " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
      " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
      " THEN 'S' ELSE 'N' END AS proconf  ";
 
      strSql="SELECT a.co_itm, a.tx_codalt, a.tx_nomitm, a2.tx_descor, a.nd_canaceingsis, a.nd_preuni, a.nd_pordes, a.st_iva " +
      ", ( (a.nd_canaceingsis * a.nd_preuni) - ((a.nd_canaceingsis * a.nd_preuni) * (a.nd_pordes / 100)) ) as total, a1.st_ser "+ 
      ", a1.nd_maruti, a1.tx_codalt2  "+strAux+" "+strAux2+" FROM tbm_detsoldevven as a" +
      " LEFT JOIN tbm_inv AS a1 on (a.co_emp = a1.co_emp and a.co_itm = a1.co_itm) " +
      " LEFT JOIN tbm_var AS a2 on (a2.co_reg=a1.co_uni) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+strCodLocSol+" AND a.co_doc="+strCodDocSol+" AND a.nd_canaceingsis >0 ";
      rstCab=stmLoc.executeQuery(strSql);
      while(rstCab.next()){
         java.util.Vector vecReg = new java.util.Vector();
          
         //chkSolTraTot.setSelected(false);
	 //masInformacion(false);
		    
        vecReg.add(INT_TBL_LINEA, "");
        vecReg.add(INT_TBL_ITMALT, rstCab.getString("tx_codalt") );
        vecReg.add(INT_TBL_BUTITM, "");
        vecReg.add(INT_TBL_DESITM, rstCab.getString("tx_nomitm"));
        vecReg.add(INT_TBL_UNIDAD, rstCab.getString("tx_descor") );
        vecReg.add(INT_TBL_CODBOD, new Integer(intCodBodPre));
        vecReg.add(INT_TBL_BUTBOD, "");
        vecReg.add(INT_TBL_TRATOT, new Boolean(false));
        vecReg.add(INT_TBL_CANMOV, new Double(rstCab.getDouble("nd_canaceingsis")));
        vecReg.add(INT_TBL_PREUNI, new Double(rstCab.getDouble("nd_preuni")));
        vecReg.add(INT_TBL_PORDES, new Double(rstCab.getDouble("nd_pordes")));
        vecReg.add(INT_TBL_BLNIVA, new Boolean((rstCab.getString("st_iva").equals("S")?true:false)));
        vecReg.add(INT_TBL_TOTAL, new Double(rstCab.getDouble("total")));
        vecReg.add(INT_TBL_CODITM, rstCab.getString("co_itm"));
        vecReg.add(INT_TBL_ESTADO, "E");
        vecReg.add(INT_TBL_IVATXT, rstCab.getString("st_iva") );
        vecReg.add(INT_TBL_PRE_COS, "0" );            //Columna que contiene  precio de compra
        vecReg.add(INT_TBL_DESPRECOM, "0" );
        vecReg.add(INT_TBL_COD_PRO, "0" );            //Columna que contiene el codigo del proveedor
        vecReg.add(INT_TBL_NOM_PRO, "" );
        vecReg.add(INT_TBL_BUT_PRO,"");
        vecReg.add(INT_TBL_BLNPRE,"");
        vecReg.add(INT_TBL_ITMALT2, rstCab.getString("tx_codalt2"));
        vecReg.add(INT_TBL_ITMSER, rstCab.getString("st_ser") );
        vecReg.add(INT_TBL_ITMTER, rstCab.getString("isterL") );
        vecReg.add(INT_TBL_MARUTI, rstCab.getString("nd_maruti") );
        vecReg.add(INT_TBL_IEBODFIS, rstCab.getString("proconf") );
        vecData.add(vecReg);
    }
   objTblMod.setData(vecData);
   tblDat .setModel(objTblMod);
   rstCab.close();
   rstCab=null;             
                
       CalculoPago2();
       calculaSubTotTodLosItm();
      
      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;
      blnHayCam=false;
 }}catch(SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
   catch(Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
 return blnRes;
}
    
    
    
    
        
    
    
      
     


    



    
    
    
    
     private void masInformacion(boolean blnMostrar){
      
      if(!blnMostrar){
	for(int i=0; i<tblDat.getRowCount();i++){
                    //if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
			 tblDat.setValueAt(new Boolean(false), i, INT_TBL_TRATOT);
		    }//}
       }
      
	 if(blnMostrar)
            MostrarCol(INT_TBL_TRATOT);
          else
            ocultaCol(INT_TBL_TRATOT);
     }
    
    
     private void MostrarCol(int intCol){
        tblDat.getColumnModel().getColumn(intCol).setWidth(30);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(30);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(30);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(30);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    private void ocultaCol(int intCol){
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
       
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
            
            txtFecRec.setText("");
            
            //Detalle
            objTblMod.removeAllRows();
            
            //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            txtTot.setText("0");
            lblCotNumDes.setText("Cotizaciones");
            
            txtNomCliVar.setVisible(false);
                    
            //Detalle  de PAGOS
            objTblModPag.removeAllRows();
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
       
       if(stIvaVen.equals("N")){ 
          txtSub.setText( "" + dblSubtotalCot );
          txtIva.setText( "0.00" );
          txtTot.setText( ""+ dblSubtotalCot);
          dblTotalCot=dblSubtotalCot;
          dblIvaCot=0;
       }else{
            txtSub.setText( "" + dblSubtotalCot );
            txtIva.setText( "" + dblIvaCot );
            txtTot.setText( ""+ dblTotalCot);
       }
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
            String strCodEmp="";
            String strCodLoc="";
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
               
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
            
                
                sSQL = "SELECT detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
                ",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
                ",detcot.tx_codalt , detcot.nd_pordesprecom, detcot.st_traauttot,   var.tx_descor , inv.st_ser , cli.tx_nom, inv.nd_maruti ";
                
                sSQL +=strAux;
                sSQL +=strAux2;
                sSQL +=" FROM tbm_detcotven as detcot " +
                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
                " WHERE detcot.co_emp="+rstCab.getInt("co_emp")+" and detcot.co_loc="+rstCab.getInt("co_loc")+" " +
                " and detcot.co_cot="+rstCab.getInt("co_cot")+" order by detcot.co_reg";
                
                java.sql.Statement stmCab=CONN_GLO.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = CONN_GLO.createStatement();
                String strUnidad="",strCodAlt="",strSer="", strTer="", strMarUti="";
                Vector vecData = new Vector();
                int inttratot=0;
		for(int i=0 ; rst.next() ; i++){
                    java.util.Vector vecReg = new java.util.Vector();
                    
                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                    strMarUti = (rst.getString("nd_maruti")==null?"":rst.getString("nd_maruti"));
                  
//		  if(inttratot==0){  
//		   if(rst.getString("st_traauttot").equals("S")){
//		     chkSolTraTot.setSelected(true);
//		     masInformacion(true);
//		     inttratot=1;
//		   }} 
		    
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
		     vecReg.add(INT_TBL_TRATOT, new Boolean(rst.getString("st_traauttot").equals("S")));
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivaven");
                    Boolean blnIva;
                    if(stIvaVen.equals("N"))
                      blnIva = new Boolean(false);
                    else  blnIva = new Boolean(strIva.equals("S"));
                    
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
                    
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf") );
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
                
                
                cargarDetPag(CONN_GLO, rstCab.getString("co_emp"), rstCab.getString("co_loc"), rstCab.getString("co_cot")  );
                /*
                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra , co_tipret  from tbm_pagCotven where " +
                " co_emp = " + vecEmp.elementAt(empresa.getSelectedIndex()).toString() + " and " +
                " co_loc = " + vecLoc.elementAt(local.getSelectedIndex()).toString()   + " and " +
                " co_cot = "+rstCab.getString("co_cot")+" order by co_reg";
                
                rst = stmAux.executeQuery(sSQL);
                int i=0;
                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblPag.getModel());
                modTbl.setRowCount(0);
                
                while(rst.next()){
                    
                    ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
                    
                    tblPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
                    tblPag.setValueAt( rst.getString(2), i, 2);
                    tblPag.setValueAt( rst.getString(3), i, 3);
                    tblPag.setValueAt( new Double(rst.getString(4)) , i, 4);
                    tblPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);
                    tblPag.setValueAt(new Integer(rst.getInt("co_tipret")),  i, 6);
                    i++;
                }
                */
                
                
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
//                    if (objTooBar.getEstado()== 'm'){
//                        //objUti.activarCom(jfrThis);
//                        noEditable(false);
//                    }
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
    
    
    
    
private boolean cargarDetPag(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodCot ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
     stmLoc=conn.createStatement();
     Vector vecData = new Vector();
      strSql="SELECT ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra , co_tipret  FROM tbm_pagCotven " +
      " WHERE co_emp="+strCodEmp+ " AND co_loc="+strCodLoc+" AND co_cot="+strCodCot+" ORDER BY co_reg";
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
           java.util.Vector vecReg = new java.util.Vector();
              vecReg.add(INT_TBL_PAGLIN, "");
              vecReg.add(INT_TBL_PAGCRE, rstLoc.getString("ne_diacre") );
              vecReg.add(INT_TBL_PAGFEC, rstLoc.getString("fe_ven") );
              vecReg.add(INT_TBL_PAGRET, rstLoc.getString("nd_porret") );
              vecReg.add(INT_TBL_PAGMON, rstLoc.getString("mo_pag") );
              vecReg.add(INT_TBL_PAGGRA, rstLoc.getString("ne_diagra") );
              vecReg.add(INT_TBL_PAGCOD, rstLoc.getString("co_tipret") );
              vecReg.add(INT_TBL_PAGSOP, "" );
             vecData.add(vecReg);  
      }
     objTblModPag.setData(vecData);
     tblPag.setModel(objTblModPag);
     vecData=null;
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    blnRes=true;
 }catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  catch(Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, evt); }
 return blnRes;     
}    
    
    
//    
//    
//    public void  refrescaDatosLoc(){
//        try{
//            int intNumCot = 0;
//            if (CONN_GLO!=null){
//                
//                
//                String strAux = ",CASE " +
//                " WHEN ((trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
//                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
//                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
//                " ))) THEN 'S' " +
//                "" +
//                " WHEN ((trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
//                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
//                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
//                " ))) THEN 'I' " +
//                " ELSE 'N' END  as isterL";
//                
//                
//                sSQL = "SELECT detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
//                ",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
//                ",detcot.tx_codalt , detcot.nd_pordesprecom, detcot.st_traauttot,  var.tx_descor , inv.st_ser , cli.tx_nom";
//                
//                sSQL +=strAux;
//                sSQL +=" FROM tbm_detcotven as detcot " +
//                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
//                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
//                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
//                " WHERE detcot.co_emp="+objZafParSis.getCodigoEmpresa()+" and detcot.co_loc=1 " +
//                " and detcot.co_cot="+rstCab.getInt("co_cot")+" order by detcot.co_reg";
//                
//                
//                java.sql.Statement stmCab=CONN_GLO.createStatement();
//                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
//                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
//                
//                java.sql.Statement stmAux;
//                java.sql.ResultSet rstAux;
//                stmAux = CONN_GLO.createStatement();
//                String strUnidad="",strCodAlt="",strSer="", strTer="";
//                Vector vecData = new Vector();
//		int inttratot=0;
//                for(int i=0 ; rst.next() ; i++){
//                    java.util.Vector vecReg = new java.util.Vector();
//                    
//                    
//                    
//                    
//                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
//                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
//                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
//                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
//                    
//                      
//		  if(inttratot==0){  
//		   if(rst.getString("st_traauttot").equals("S")){
//		     chkSolTraTot.setSelected(true);
//		     masInformacion(true);
//		     inttratot=1;
//		   }} 
//		    
//                    
//                    vecReg.add(INT_TBL_LINEA, "");
//                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
//                    vecReg.add(INT_TBL_BUTITM, "");
//                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
//                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
//                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
//                    vecReg.add(INT_TBL_BUTBOD, "");
//                    vecReg.add(INT_TBL_TRATOT, new Boolean(rst.getString("st_traauttot").equals("S")));
//		    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
//                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
//                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
//                    String strIva = rst.getString("st_ivaven");
//                    Boolean blnIva; 
//                    if(stIvaVen.equals("N"))
//                      blnIva = new Boolean(false);
//                    else  blnIva = new Boolean(strIva.equals("S"));
//                          
//                    
//                    vecReg.add(INT_TBL_BLNIVA, blnIva);
//                    dblCan    = rst.getDouble("nd_can");
//                    dblPre    = rst.getDouble("nd_preuni");
//                    dblPorDes = rst.getDouble("nd_pordes");
//                    
//                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
//                    dblTotal  = (dblCan * dblPre)- dblValDes ;
//                    dblTotal =  objUti.redondear(dblTotal,3);
//                    dblTotal =  objUti.redondear(dblTotal,2);
//                    
//                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
//                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
//                    vecReg.add(INT_TBL_ESTADO, "E");
//                    vecReg.add(INT_TBL_IVATXT, strIva );
//                    vecReg.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")) );            //Columna que contiene  precio de compra
//                    vecReg.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")) );
//                    vecReg.add(INT_TBL_COD_PRO, new Integer(rst.getInt("co_prv")) );            //Columna que contiene el codigo del proveedor
//                    strCodAlt = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
//                    vecReg.add(INT_TBL_NOM_PRO, strCodAlt );
//                    vecReg.add(INT_TBL_BUT_PRO,"");
//                    vecReg.add(INT_TBL_BLNPRE,"");
//                    
//                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
//                    vecReg.add(INT_TBL_ITMSER, strSer );
//                    
//                    vecReg.add(INT_TBL_ITMTER, strTer );
//                    
//                    vecData.add(vecReg);
//                }
//                objTblMod.setData(vecData);
//                tblDat .setModel(objTblMod);
//                rst.close();
//                calculaPag();
//                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
//                lblIva.setText("IVA " + dblPorIva + "%");
//                
//                
//                /*
//                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
//                 */
//                
//                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;
//                
//                cargarDetPag(CONN_GLO, String.valueOf(objZafParSis.getCodigoEmpresa()), "1", rstCab.getString("co_cot") );
//                /*        
//                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra , co_tipret  from tbm_pagCotven where " +
//                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
//                " co_loc = 1  and " +
//                " co_cot = "+rstCab.getInt("co_cot")+" order by co_reg";
//                
//                rst = stmAux.executeQuery(sSQL);
//                int i=0;
//                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblPag.getModel());
//                modTbl.setRowCount(0);
//                
//                while(rst.next()){
//                    
//                    ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
//                    
//                    tblPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
//                    tblPag.setValueAt( rst.getString(2), i, 2);
//                    tblPag.setValueAt( rst.getString(3), i, 3);
//                    tblPag.setValueAt( new Double(rst.getString(4)) , i, 4);
//                    tblPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);
//                    tblPag.setValueAt(new Integer(rst.getInt("co_tipret")),  i, 6);
//                    i++;
//                }
//                
//                */
//                
//                txtCodForPag.setText(""+strCo_ForPag);
//                txtCodForPag2.setText(""+strCo_ForPag);
//                txtNomForPag.setText(""+ rstCab.getString("tx_des"));
//                
//                CalculoPago2();
//                
//                
//                
//                /*
//                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
//                 */
//                String strStatus = rstCab.getString("st_reg");
//                if(strStatus.equals("I")){
//                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
//                    objUti.desactivarCom(jfrThis);
//                }else{
//                    if (objTooBar.getEstado()== 'm'){
//                        objUti.activarCom(jfrThis);
//                        noEditable(false);
//                    }
//                }
//                stmAux.close();
//                stmCab.close();
//                
//            }
//            objTblMod.setDataModelChanged(false);
//            blnHayCam=false;
//            
//        }//fin Try
//        catch(SQLException Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//            
//        }
//        
//        catch(Exception Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//        }
//        
//    }
//    
//    
//       
       
      
//    public void  refrescaDatoshis(Integer CodEmp,Integer CodLoc){
//        try{
//            int intNumCot = 0;
//            if (CONN_GLO!=null){
//                
//                String strAux = ",CASE WHEN (" +
//                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
//                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
//                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
//                " ))) THEN 'S' ELSE 'N' END  as isterL";
//                
//                    String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
//                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
//                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
//                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and  a.st_reg='P' ))) " +
//                " THEN 'S' ELSE 'N' END AS proconf  ";
//                  
//                
//                sSQL = "SELECT detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
//                ",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
//                ",detcot.tx_codalt , detcot.nd_pordesprecom, detcot.st_traauttot,  var.tx_descor , inv.st_ser , cli.tx_nom";
//                
//                sSQL +=strAux;
//                sSQL +=strAux2;
//                sSQL +=" FROM tbh_detcotven as detcot " +
//                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
//                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
//                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
//                " WHERE detcot.co_emp="+CodEmp+" and detcot.co_loc="+CodLoc+" " +
//                " and detcot.co_cot="+rstCab.getInt("co_cot")+"  and detcot.co_his="+rstCab.getInt("co_his")+" order by detcot.co_reg";
//                
//                
//                java.sql.Statement stmCab=CONN_GLO.createStatement();
//                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
//                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
//                
//                java.sql.Statement stmAux;
//                java.sql.ResultSet rstAux;
//                stmAux = CONN_GLO.createStatement();
//                String strUnidad="",strCodAlt="",strSer="", strTer="";
//                Vector vecData = new Vector();
//		int inttratot=0;
//                for(int i=0 ; rst.next() ; i++){
//                    java.util.Vector vecReg = new java.util.Vector();
//                    
//                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
//                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
//                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
//                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
//                    
//		      
//		  if(inttratot==0){  
//		   if(rst.getString("st_traauttot").equals("S")){
//		    // chkSolTraTot.setSelected(true);
//		     masInformacion(true);
//		     inttratot=1;
//		   }} 
//		    
//		    
//                    vecReg.add(INT_TBL_LINEA, "");
//                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
//                    vecReg.add(INT_TBL_BUTITM, "");
//                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
//                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
//                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
//                    vecReg.add(INT_TBL_BUTBOD, "");
//		    vecReg.add(INT_TBL_TRATOT, new Boolean(rst.getString("st_traauttot").equals("S")));
//                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
//                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
//                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
//                    String strIva = rst.getString("st_ivaven");
//                     Boolean blnIva; 
//                    if(stIvaVen.equals("N"))
//                      blnIva = new Boolean(false);
//                    else  blnIva = new Boolean(strIva.equals("S"));
//                     
//                    vecReg.add(INT_TBL_BLNIVA, blnIva);
//                    dblCan    = rst.getDouble("nd_can");
//                    dblPre    = rst.getDouble("nd_preuni");
//                    dblPorDes = rst.getDouble("nd_pordes");
//                    
//                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
//                    dblTotal  = (dblCan * dblPre)- dblValDes ;
//                    dblTotal =  objUti.redondear(dblTotal,3);
//                    dblTotal =  objUti.redondear(dblTotal,2);
//                    
//                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
//                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
//                    vecReg.add(INT_TBL_ESTADO, "E");
//                    vecReg.add(INT_TBL_IVATXT, strIva );
//                    vecReg.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")) );            //Columna que contiene  precio de compra
//                    
//                    vecReg.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")) );
//                    
//                    vecReg.add(INT_TBL_COD_PRO, new Integer(rst.getInt("co_prv")) );            //Columna que contiene el codigo del proveedor
//                    
//                    strCodAlt = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
//                    
//                    vecReg.add(INT_TBL_NOM_PRO, strCodAlt );            //Columna que contiene el Nombre del proveedor
//                    vecReg.add(INT_TBL_BUT_PRO,"");               //Columna que contiene para busqueda del proveedor
//                    vecReg.add(INT_TBL_BLNPRE,"");
//                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
//                    vecReg.add(INT_TBL_ITMSER, strSer );
//                    vecReg.add(INT_TBL_ITMTER, strTer );
//                    vecReg.add(INT_TBL_MARUTI, "");
//                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf") ); 
//                    vecData.add(vecReg);
//                }
//                objTblMod.setData(vecData);
//                tblDat .setModel(objTblMod);
//                //stmAux.close();
//                rst.close();
//                calculaTot();
//                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
//                lblIva.setText("IVA " + dblPorIva + "%");
//                
//                
//                /*
//                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
//                 */
//                
//                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;
//                
//                cargarDetPag(CONN_GLO, String.valueOf(CodEmp), String.valueOf(CodLoc), rstCab.getString("co_cot")  );
//                /*
//                //Extrayendo los datos del detalle respectivo a ESTE PAGO
//                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra  from tbh_pagCotven where " +
//                " co_emp = " + CodEmp + " and " +
//                " co_loc = " + CodLoc   + " and " +
//                " co_cot = "+rstCab.getInt("co_cot")+" and co_his="+rstCab.getInt("co_his")+" order by co_reg";
//                
//                rst = stmAux.executeQuery(sSQL);
//                int i=0;
//                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblPag.getModel());
//                modTbl.setRowCount(0);
//                
//                while(rst.next()){
//                    
//                    
//                    ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
//                    
//                    tblPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
//                    tblPag.setValueAt( rst.getString(2), i, 2);
//                    tblPag.setValueAt( rst.getString(3), i, 3);
//                    tblPag.setValueAt( new Double(rst.getString(4)) , i, 4);
//                    tblPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);
//                    
//                    i++;
//                }
//                
//                */
//                
//                    /*
//                     * LLenando el combo de descripciones de pagos
//                     *
//                     */
//                
//                txtCodForPag.setText(""+strCo_ForPag);
//                txtCodForPag2.setText(""+strCo_ForPag);
//                txtNomForPag.setText(""+ rstCab.getString("tx_des"));
//                
//                
//                
//                /*
//                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
//                 */
//                String strStatus = rstCab.getString("st_reg");
//                if(strStatus.equals("I")){
//                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
//                    objUti.desactivarCom(jfrThis);
//                }else{
//                    if (objTooBar.getEstado()== 'm'){
////                        objUti.activarCom(jfrThis);
//                        noEditable(false);
//                    }
//                }
//                stmAux.close();
//                stmCab.close();
//                
//            }
//            objTblMod.setDataModelChanged(false);
//            blnHayCam=false;
//            
//        }
//        catch(SQLException Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//        }
//        
//        catch(Exception Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//        }
//        
//    }
//    
    
//    
//   public void  refrescaDatos2(Integer CodEmp,Integer CodLoc){
//        try{
//            int intNumCot = 0;
//            if (CONN_GLO!=null){
//                
//                String strAux = ",CASE WHEN (" +
//                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
//                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
//                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
//                " ))) THEN 'S' ELSE 'N' END  as isterL";
//                
//                   String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
//                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
//                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
//                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and  a.st_reg='P' ))) " +
//                " THEN 'S' ELSE 'N' END AS proconf  ";
//                  
//                sSQL = "SELECT detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven " +
//                ",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 " +
//                ",detcot.tx_codalt , detcot.nd_pordesprecom, detcot.st_traauttot,   var.tx_descor , inv.st_ser , cli.tx_nom";
//                
//                sSQL +=strAux;
//                 sSQL +=strAux2;
//                sSQL +=" FROM tbm_detcotven as detcot " +
//                " LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
//                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
//                " LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)" +
//                " WHERE detcot.co_emp="+CodEmp+" and detcot.co_loc="+CodLoc+" " +
//                " and detcot.co_cot="+rstCab.getInt("co_cot")+" order by detcot.co_reg";
//                
//                
//                java.sql.Statement stmCab=CONN_GLO.createStatement();
//                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
//                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
//                
//                java.sql.Statement stmAux;
//                java.sql.ResultSet rstAux;
//                stmAux = CONN_GLO.createStatement();
//                String strUnidad="",strCodAlt="",strSer="", strTer="";
//                Vector vecData = new Vector();
//		int inttratot=0;
//                for(int i=0 ; rst.next() ; i++){
//                    java.util.Vector vecReg = new java.util.Vector();
//                    
//                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
//                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
//                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
//                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
//                    
//		      
//		  if(inttratot==0){  
//		   if(rst.getString("st_traauttot").equals("S")){
//		     chkSolTraTot.setSelected(true);
//		     masInformacion(true);
//		     inttratot=1;
//		   }} 
//		    
//		    
//                    vecReg.add(INT_TBL_LINEA, "");
//                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
//                    vecReg.add(INT_TBL_BUTITM, "");
//                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
//                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
//                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
//                    vecReg.add(INT_TBL_BUTBOD, "");
//		     vecReg.add(INT_TBL_TRATOT, new Boolean(rst.getString("st_traauttot").equals("S")));
//                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
//                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
//                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
//                    String strIva = rst.getString("st_ivaven");
//                     Boolean blnIva; 
//                    if(stIvaVen.equals("N"))
//                      blnIva = new Boolean(false);
//                    else  blnIva = new Boolean(strIva.equals("S"));
//                     
//                    vecReg.add(INT_TBL_BLNIVA, blnIva);
//                    dblCan    = rst.getDouble("nd_can");
//                    dblPre    = rst.getDouble("nd_preuni");
//                    dblPorDes = rst.getDouble("nd_pordes");
//                    
//                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
//                    dblTotal  = (dblCan * dblPre)- dblValDes ;
//                    dblTotal =  objUti.redondear(dblTotal,3);
//                    dblTotal =  objUti.redondear(dblTotal,2);
//                    
//                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
//                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
//                    vecReg.add(INT_TBL_ESTADO, "E");
//                    vecReg.add(INT_TBL_IVATXT, strIva );
//                    vecReg.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")) );            //Columna que contiene  precio de compra
//                    
//                    vecReg.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")) );
//                    
//                    vecReg.add(INT_TBL_COD_PRO, new Integer(rst.getInt("co_prv")) );            //Columna que contiene el codigo del proveedor
//                    
//                    strCodAlt = (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
//                    
//                    vecReg.add(INT_TBL_NOM_PRO, strCodAlt );            //Columna que contiene el Nombre del proveedor
//                    vecReg.add(INT_TBL_BUT_PRO,"");               //Columna que contiene para busqueda del proveedor
//                    vecReg.add(INT_TBL_BLNPRE,"");
//                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
//                    vecReg.add(INT_TBL_ITMSER, strSer );
//                    vecReg.add(INT_TBL_ITMTER, strTer );
//                    vecReg.add(INT_TBL_MARUTI, "");
//                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf") ); 
//                    vecData.add(vecReg);
//                }
//                objTblMod.setData(vecData);
//                tblDat .setModel(objTblMod);
//                //stmAux.close();
//                rst.close();
//                calculaTot();
//                lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtCliNom.getText() + ") ");
//                lblIva.setText("IVA " + dblPorIva + "%");
//                
//                
//                /*
//                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
//                 */
//                
//                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;
//                
//                cargarDetPag(CONN_GLO, String.valueOf(CodEmp), String.valueOf(CodLoc), rstCab.getString("co_cot")  );
//              
//                 /* 
//                //Extrayendo los datos del detalle respectivo a ESTE PAGO
//                sSQL  = "Select ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra  from tbm_pagCotven where " +
//                " co_emp = " + CodEmp + " and " +
//                " co_loc = " + CodLoc   + " and " +
//                " co_cot = "+rstCab.getInt("co_cot")+" order by co_reg";
//                
//                rst = stmAux.executeQuery(sSQL);
//                int i=0;
//                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblPag.getModel());
//                modTbl.setRowCount(0);
//                
//                while(rst.next()){
//                    
//                    
//                    ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
//                    
//                    tblPag.setValueAt(new Integer(rst.getInt(1)) , i, 1);
//                    tblPag.setValueAt( rst.getString(2), i, 2);
//                    tblPag.setValueAt( rst.getString(3), i, 3);
//                    tblPag.setValueAt( new Double(rst.getString(4)) , i, 4);
//                    tblPag.setValueAt(new Integer(rst.getInt(5)),  i, 5);
//                    
//                    i++;
//                }
//                
//                */
//                
//                    /*
//                     * LLenando el combo de descripciones de pagos
//                     *
//                     */
//                
//                txtCodForPag.setText(""+strCo_ForPag);
//                txtCodForPag2.setText(""+strCo_ForPag);
//                txtNomForPag.setText(""+ rstCab.getString("tx_des"));
//                
//              
//                
//                /*
//                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
//                 */
//                String strStatus = rstCab.getString("st_reg");
//                if(strStatus.equals("I")){
//                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
//                    objUti.desactivarCom(jfrThis);
//                }else{
//                    if (objTooBar.getEstado()== 'm'){
//                        objUti.activarCom(jfrThis);
//                        noEditable(false);
//                    }
//                }
//                stmAux.close();
//                stmCab.close();
//                
//            }
//            objTblMod.setDataModelChanged(false);
//            blnHayCam=false;
//            
//        }
//        catch(SQLException Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//        }
//        
//        catch(Exception Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//        }
//        
//    }
//    
//    
    
    
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
//    private boolean isRegPro() {
//        boolean blnRes=true;
//        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
//        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
//        switch (mostrarMsgCon(strAux)) {
//            case 0: //YES_OPTION
//                switch (objTooBar.getEstado()) {
//                    case 'n': //Insertar
//                        blnRes=objTooBar.insertar();
//                        break;
//                    case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
//                        break;
//                }
//                break;
//            case 1: //NO_OPTION
//                objTblMod.setDataModelChanged(false);
//                blnHayCam=false;
//                blnRes=true;
//                break;
//            case 2: //CANCEL_OPTION
//                blnRes=false;
//                break;
//        }
//        return blnRes;
//    }
    
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
    
    
    
    
    
//    
//    private boolean cargarRegLoc() {
//        boolean blnRes=true;
//        try {
//            if (cargarCabRegLoc()) {
////                refrescaDatosLoc();
//            }
//            else {
//                MensajeInf("Error al cargar registro");
//                objTblMod.setDataModelChanged(false);
//                blnHayCam=false;
//            }
//        }
//        catch (Exception e) {
//            blnRes=false;
//        }
//        return blnRes;
//    }
//    
//      
//      
//      private boolean cargarReghis(Integer CodEmp,Integer CodLoc) {
//        boolean blnRes=true;
//        try {
//            
//            if (cargarCabReg()) {
//                refrescaDatoshis(CodEmp,CodLoc);
//            }
//            else {
//                MensajeInf("Error al cargar registro");
//                objTblMod.setDataModelChanged(false);
//                blnHayCam=false;
//            }
//            
//        }
//        catch (Exception e) {
//            blnRes=false;
//        }
//        return blnRes;
//    }
    
    
      
       
    
      
        
        
     
        
        
    
    private boolean cargarReg2(Integer CodEmp,Integer CodLoc) {
        boolean blnRes=true;
        try {
            
            if (cargarCabReg()) {
//                refrescaDatos2(CodEmp,CodLoc);
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
    
    
    
    
    
    
    private boolean cargarCabReg() {
        int intPosRel;
        boolean blnRes=true;
        try {
            strCodLocSol=rstCab.getString("co_locrelsoldevven");
            strCodTipDocSol=rstCab.getString("co_tipdocrelsoldevven");
            strCodDocSol=rstCab.getString("co_docrelsoldevven");
            
            txtCot.setText(rstCab.getString("co_cot"));
            txtCliCod.setText(((rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli")));
            txtCliNom.setText(((rstCab.getString("nomcli")==null)?"":rstCab.getString("nomcli")));
            txtCliDir.setText(((rstCab.getString("dircli")==null)?"":rstCab.getString("dircli")));
            
            txtNomCliVar.setText(((rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli")));
            txtNomCliVar.setVisible(((rstCab.getString("st_peringnomclicotven").toString().equals("S")?true:false)));
              
            
            //chkSolTraTot.setSelected(false);
            masInformacion(false);
		     
            txtcli_telefono.setText( rstCab.getString("tx_tel") );
            txtcli_identificacion.setText( rstCab.getString("tx_ide") );
            txtcli_ciudad.setText( rstCab.getString("tx_desLar") );
            tx_numped.setText( rstCab.getString("tx_numped") );
            
            STR_ESTREG=rstCab.getString("st_regrep");
            
         //   strTipPer_glo=rstCab.getString("tx_tipper");
            Glo_dlbMaxDes = rstCab.getDouble("nd_maxdes");
            Glo_dlbMarUti = rstCab.getDouble("nd_maruti");
            strCodTipPerCli =  rstCab.getString("co_tipper");
            stIvaVen = rstCab.getString("st_ivaven");
            
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
            
            
             if(rstCab.getDate("fe_procon")==null){
                txtFecRec.setText("");
            }else{
                java.util.Date dateObj = rstCab.getDate("fe_procon");
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecRec.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
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
           // objTooBar.setEstadoRegistro(strAux);
            
            intPosRel=rstCab.getRow();
            rstCab.last();
           // objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
            blnHayCam=false;
        }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
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
    
    
//    
//    
//    public boolean consultar2(Integer CodEmp,Integer CodLoc) {
//        return _consultar2(FilSql2(),CodEmp,CodLoc);
//    }
//    
//    
//    
//    public boolean consultarHis(Integer CodEmp,Integer CodLoc) {
//        return _consultarHis(FilSql2(),CodEmp,CodLoc);
//    }
//    
    
    
    
    
    
//       
//    private boolean _consultarHis(String strFil,Integer CodEmp,Integer CodLoc){
//        
//        strFiltro= strFil;
//         int intCodHis=0;
//        try{//odbc,usuario,password
//            
//            abrirCon();
//            if(CONN_GLO!=null) {
//                
//                stmCot=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//                
//                 sSQL = "select max(co_his) from tbh_cabcotven where co_emp="+CodEmp+"  and co_loc="+CodLoc+" aND co_cot="+intCodCot;
//                java.sql.ResultSet rstHis=stmCot.executeQuery(sSQL);
//                if(rstHis.next())
//                    intCodHis=rstHis.getInt(1);
//                 rstHis.close();
//                 rstHis=null;
//                  
//                sSQL= "SELECT CotCab.co_locrelsoldevven,  CotCab.co_tipdocrelsoldevven, CotCab.co_docrelsoldevven  , " +
//                " CotCab.fe_procon, CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
//                " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
//                " CotCab.co_cot, CotCab.co_his, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
//                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
//                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
//                " CotCab.st_reg , forpag.tx_des " +
//                " , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  " +
//                " ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli   " +
//                " ,Cli.ne_diagra, Cli.st_ivaven , CotCab.tx_nomcli, cli.st_peringnomclicotven  FROM tbh_cabCotven as CotCab left outer " +
//                " join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
//                  " left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  " +
//                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
//                " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
//                " left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  " +
//                " Where CotCab.co_emp="+CodEmp+ // Consultando en la empresa en la ke se esta trabajando
//                "       and CotCab.co_loc="+CodLoc+" and CotCab.co_his="+intCodHis+"   and CotCab.st_reg not in ('E')";// Consultando en el local en el ke se esta trabajando
//                sSQL= sSQL + strFiltro + " ORDER BY CotCab.co_cot";
//                 
//                rstCab=stmCot.executeQuery(sSQL);
//                
//                if(rstCab.next()){
//                    rstCab.last();
//                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
//                    cargarReghis(CodEmp, CodLoc);
//                }
//                else{
//                    objTooBar.setMenSis("0 Registros encontrados");
//                    clnTextos();
//                    return false;
//                }
//            }
//            CerrarCon();
//        }
//        catch(SQLException Evt) {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;   }
//        catch(Exception Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     return false;   }
////        butMakeFactura.setEnabled(true);
////        butCopyCot.setEnabled(true);
////        butVisOc.setEnabled(true);
//        System.gc();
//        return true;
//        
//    }
//    
//    
//    
//    
//    private boolean _consultar2(String strFil,Integer CodEmp,Integer CodLoc){
//        
//        strFiltro= strFil;
//        try{//odbc,usuario,password
//            
//            abrirCon();
//            if(CONN_GLO!=null) {
//                
//                stmCot=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//                
//                  
//                sSQL= "SELECT CotCab.co_locrelsoldevven,  CotCab.co_tipdocrelsoldevven, CotCab.co_docrelsoldevven  , " +
//                " CotCab.fe_procon, CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
//                " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
//                " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
//                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
//                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
//                " CotCab.st_reg , forpag.tx_des " +
//                " , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  " +
//                " ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli   " +
//                " ,Cli.ne_diagra, Cli.st_ivaven, CotCab.tx_nomcli, cli.st_peringnomclicotven  FROM tbm_cabCotven as CotCab left outer " +
//                " join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
//                  " left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  " +
//                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
//                " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
//                " left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  " +
//                " Where CotCab.co_emp="+CodEmp+ // Consultando en la empresa en la ke se esta trabajando
//                "       and CotCab.co_loc="+CodLoc+" and CotCab.st_reg in ('A','I','P','C','R','F','U','D')";// Consultando en el local en el ke se esta trabajando
//                sSQL= sSQL + strFiltro + " ORDER BY CotCab.co_cot";
//                 
//                rstCab=stmCot.executeQuery(sSQL);
//                
//                if(rstCab.next()){
//                    rstCab.last();
//                    //objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
//                    cargarReg2(CodEmp, CodLoc);
//                }
//                else{
//                    //objTooBar.setMenSis("0 Registros encontrados");
//                    clnTextos();
//                    return false;
//                }
//            }
//            CerrarCon();
//        }
//        catch(SQLException Evt) {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;   }
//        catch(Exception Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     return false;   }
////        butMakeFactura.setEnabled(true);
////        butCopyCot.setEnabled(true);
////        butVisOc.setEnabled(true);
//        System.gc();
//        return true;
//        
//    }
//    
//    
    
    
    
    
    
    private boolean _consultar(String strCodEmp, String strCodLoc, String strCodCot ){
      //  strFiltro= strFil;
        try{
            abrirCon();
            if(CONN_GLO!=null) {
                
                
                
                stmCab=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
                sSQL= "SELECT CotCab.fe_procon, CotCab.co_locrelsoldevven,  CotCab.co_tipdocrelsoldevven, CotCab.co_docrelsoldevven  , " +
                " CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                " CotCab.st_reg , forpag.tx_des " +
                " , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  " +
                " ,Cli.co_tipper , CotCab.tx_numped , forpag.ne_tipforpag , forpag.nd_pes, forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli    " +
                " ,Cli.ne_diagra, Cli.st_ivaven, CotCab.tx_nomcli, cli.st_peringnomclicotven   " +
                " FROM tbm_cabCotven as CotCab left outer " +
                " join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
                " left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  " +
                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
                " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  " +
                " Where CotCab.co_emp = " +strCodEmp + // Consultando en la empresa en la ke se esta trabajando
                "       and CotCab.co_loc = " + strCodLoc+"  and CotCab.co_cot ="+strCodCot+" and CotCab.st_reg in ('A','I','P','C','R','F','U','D')";// Consultando en el local en el ke se esta trabajando
                sSQL= sSQL + " ORDER BY CotCab.co_cot";
                System.out.println(""+sSQL);
                rstCab=stmCab.executeQuery(sSQL);
                 
                if(rstCab.next()){
                    rstCab.last();
                    //objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                }  
                else{
                    //objTooBar.setMenSis("0 Registros encontrados");  
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
    
    
    
    
    
    
//     
//    private boolean _consultarLoc(){
//        
//        try{
//            abrirCon();
//            if(CONN_GLO!=null) {
//                
//                stmCab=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//                
//                sSQL= "SELECT CotCab.co_locrelsoldevven,  CotCab.co_tipdocrelsoldevven, CotCab.co_docrelsoldevven, " +
//                " CotCab.fe_procon,  CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
//                " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
//                " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
//                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
//                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
//                " CotCab.st_reg , forpag.tx_des " +
//                " , CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  " +
//                " ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli   " +
//                " ,Cli.ne_Diagra, Cli.st_ivaven, CotCab.tx_nomcli, cli.st_peringnomclicotven  FROM tbm_cabCotven as CotCab left outer " +
//                " join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
//                  " left outer join tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  " +
//                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
//                " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
//                " left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  " +
//                " Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
//                "       and CotCab.co_loc = 1  and CotCab.st_reg not in ('E')"+ // Consultando en el local en el ke se esta trabajando
//                " AND  CotCab.co_cot = "+txtCodLoc.getText()+"  ORDER BY CotCab.co_cot";
//             
//                rstCab=stmCab.executeQuery(sSQL);
//                
//                if(rstCab.next()){
//                    rstCab.last();
//                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
//                    cargarRegLoc();
//                }
//                else{
//                    objTooBar.setMenSis("0 Registros encontrados");
//                    clnTextos();
//                    return false;
//                }
//            }
//            CerrarCon();
//            
//        }
//        
//        
//        catch(SQLException Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//            return false;
//        }
//        
//        catch(Exception Evt) {
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//            return false;
//        }
//        return true;
//        
//    }
//    
//    
//    
    
    
    
//    
//    private void cargarRegistroInsert(){
//        try{
//            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if (conCab!=null){
//                
//                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//                
//                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,CotCab.co_cot,CotCab.st_reg " + // Campo para saber si esta anulado o no
//                " FROM tbm_cabCotven as CotCab " +
//                " Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
//                "  and CotCab.co_loc = "  + objZafParSis.getCodigoLocal() + // Consultando en el local en el ke se esta trabajando
//                "  and CotCab.co_cot = "  + txtCot.getText();
//                
//                rstCab=stmCab.executeQuery(sSQL);
//                
//                if(rstCab.next()){
//                    rstCab.last();
//                    //objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
//                }
//                else{
//                   // objTooBar.setMenSis("0 Registros encontrados");
//                    clnTextos();
//                }
//            }
//        }catch(SQLException Evt){
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//        }catch(Exception Evt){
//            objUti.mostrarMsgErr_F1(jfrThis, Evt);
//        }
//    }
//    
//    
    
    
    
    
    
    
    
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
        if( !objUti.verifyTbl(tblPag,intColObligadasPag)){
            tabCotCom.setSelectedIndex(1);
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            objUti.verifyTbl(tblPag,intColObligadasPag);
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
        for(int rowIdx = 0; rowIdx<tblPag.getRowCount() ;rowIdx++){
            double dblSubMonto = Double.parseDouble((tblPag.getValueAt(rowIdx, 4)==null)?"0":tblPag.getValueAt(rowIdx, 4).toString());
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
        
        
       if(tblPag.getRowCount() <= 0 ){
             tabCotCom.setSelectedIndex(1);
             javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
             String strTit, strMsg;
             strTit="Mensaje del sistema Zafiro";
             strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
             obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
        for(int rowIdx = 0; rowIdx<tblPag.getRowCount() ;rowIdx++){
            double dblSubMonto = Double.parseDouble((tblPag.getValueAt(rowIdx, 4)==null)?"0":tblPag.getValueAt(rowIdx, 4).toString());
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
        
        
        
         if(tblPag.getRowCount() <= 0 ){
             tabCotCom.setSelectedIndex(1);
             javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
             String strTit, strMsg;
             strTit="Mensaje del sistema Zafiro";
             strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
             obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
        for(int rowIdx = 0; rowIdx<tblPag.getRowCount() ;rowIdx++){
            double dblSubMonto = objUti.redondear( ((tblPag.getValueAt(rowIdx, INT_TBL_PAGMON)==null)?"0":tblPag.getValueAt(rowIdx, INT_TBL_PAGMON).toString()), intNumDec ) ;
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
   double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
   double dblPago=0.00;
   double dblPagos=0.00;
   double dblRete=0;
   int i=0;
   try{
      java.util.Calendar objFec = java.util.Calendar.getInstance();
      int fecDoc [] = txtFecDoc.getFecha(txtFecDoc.getText());
      if(fecDoc!=null){
         objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
         objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
         objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
      }
      dblBaseDePagos = dblTotalCot;
     
     if(dblBaseDePagos>0){
     Vector vecData = new Vector();
     if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
         Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
        //*************************************************************************************///
         java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
         if(conn!=null){
           dblRetFueFle = getSubtotalTrans();
          if(!(strCodTipPerCli==null)){
           if(!(strCodTipPerCli.trim().equals("1"))){
            if(dblRetFueFle>0){
              java.util.Vector vecReg = new java.util.Vector();
              dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
              vecReg.add(INT_TBL_PAGLIN, "");
              vecReg.add(INT_TBL_PAGCRE, "");
              vecReg.add(INT_TBL_PAGFEC, txtFecDoc.getText() );
              vecReg.add(INT_TBL_PAGRET, "1.00");
              vecReg.add(INT_TBL_PAGMON, ""+dblRetFueFle);
              vecReg.add(INT_TBL_PAGGRA, "");
              vecReg.add(INT_TBL_PAGCOD, "1");
              vecReg.add(INT_TBL_PAGSOP, "N");
             vecData.add(vecReg);  
           }} else { dblRetFueFle=0;  }
          }else { dblRetFueFle=0;  }
                        
         String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
         strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
         strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
         " AND polret.st_reg='A' AND  '"+strFecSisBase+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
         stm = conn.createStatement();
         rst = stm.executeQuery(strSQL);
         while (rst.next()){
           java.util.Vector vecReg = new java.util.Vector();
           vecReg.add(INT_TBL_PAGLIN, "");
           vecReg.add(INT_TBL_PAGCRE, "");
           vecReg.add(INT_TBL_PAGFEC, txtFecDoc.getText() );
           if (rst.getString("tx_aplret").equals("S")){
             dblRetFue = getSubtotalSinTrans();
            if(!(dblRetFueFle>0))
                dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
            else
                dblRetFue = objUti.redondeo( (    dblRetFue  * ( rst.getDouble("nd_porret")/100)),2 );

            vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret") );
            vecReg.add(INT_TBL_PAGMON, ""+dblRetFue);
            vecReg.add(INT_TBL_PAGGRA, "");
          }
          if(rst.getString("tx_aplret").equals("I")){
            dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 );
            vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret") );
            vecReg.add(INT_TBL_PAGMON, ""+dblRetIva);
            vecReg.add(INT_TBL_PAGGRA, "");
          }
         vecReg.add(INT_TBL_PAGCOD, rst.getString("co_tipret") );
         vecReg.add(INT_TBL_PAGSOP, "N");
        vecData.add(vecReg);  
      }
     rst.close();
     rst=null;
     stm.close();
     stm=null;
     conn.close();
     conn=null;
    }
    //*************************************************************************************///
    dblRete = dblRetFueFle+dblRetFue+dblRetIva;
    dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);
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

        java.util.Vector vecReg = new java.util.Vector();
        vecReg.add(INT_TBL_PAGLIN, "");
        vecReg.add(INT_TBL_PAGCRE, ""+diaCre);
        vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText() );

        dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
        dblPago += dblPagos;
        dblPagos = objUti.redondear(dblPagos ,intNumDec);

        vecReg.add(INT_TBL_PAGRET, "");

        if(i==(intVal-1))
            dblPagos= objUti.redondear( dblPagos + (dblTotalCot  - (dblPago + dblRete )), intNumDec);
       
        vecReg.add(INT_TBL_PAGMON, ""+dblPagos);
        vecReg.add(INT_TBL_PAGGRA, "0");
        vecReg.add(INT_TBL_PAGCOD, "");
        vecReg.add(INT_TBL_PAGSOP, strSop);
       vecData.add(vecReg);  
    }
   objTblModPag.setData(vecData);
   tblPag.setModel(objTblModPag);
 }}}catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
    catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}
    
    
    
    
    
  
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanEmpLoc;
    private javax.swing.JButton butCliCon;
    private javax.swing.JButton butCliCon1;
    private javax.swing.JButton butDirCliCon;
    private javax.swing.JButton butVenCon;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblAte1;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblCotNumDes;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecDoc1;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTot;
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
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblPag;
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
    private javax.swing.JTextField txtNomCliVar;
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
        java.sql.Statement stmAct;
        java.sql.ResultSet rstAct;
        int intCodHis=1;
        String sql="";
        String strFeSis="";
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                conTipDoc.setAutoCommit(false);
               sql="UPDATE tbm_cabcotven set nd_sub="+txtSub.getText()+", nd_valiva="+txtIva.getText()+",nd_tot="+txtTot.getText()+" where co_emp="+objZafParSis.getCodigoEmpresa()+"" +
                " and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+txtCot.getText();
                stmAct = conTipDoc.createStatement();
                stmAct.executeUpdate(sql);
                
                if(!strFecHisCot.equals(strFecModCot)){
                   sql="SELECT case when (max(co_his)+1) is null then 1 else max(co_his)+1 end  as co_his  FROM TBh_CABCOTVEN  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and  co_cot="+txtCot.getText();  
                   rstAct=stmAct.executeQuery(sql);
                   if(rstAct.next()){
                      intCodHis=rstAct.getInt("co_his");
                      strFeSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
               
                       sql="INSERT INTO tbh_cabcotven( " +
                           " co_emp, co_loc, co_cot, co_his, fe_cot, co_cli, co_ven, tx_ate, " + 
                           " tx_numped, co_forpag, nd_sub, nd_poriva, nd_valiva, nd_valdes,  " +
                           " nd_tot, ne_val, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, " +
                           " co_usring, co_usrmod, tx_obssolaut, tx_obsautsol, st_aut, " + 
                           " fe_his, co_usrhis, co_unicli, st_regrep, tx_nomcli )" +
                           " SELECT " +
                           " co_emp, co_loc, co_cot, "+intCodHis+",  fe_cot, co_cli,  co_ven,  tx_ate, " +
                           " tx_numped, co_forpag, nd_sub, nd_poriva, nd_valiva, nd_valdes, " + 
                           " nd_tot, ne_val, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, " +
                           " co_usring, co_usrmod, tx_obssolaut, tx_obsautsol, st_aut, " +
                           "  '"+strFeSis+"', "+objZafParSis.getCodigoUsuario()+", null, st_regrep , tx_nomcli  " +
                           " FROM tbm_cabcotven   WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and  co_cot="+txtCot.getText();

                        sql +=" ; INSERT INTO tbh_detcotven( "+ 
                            " co_emp, co_loc, co_cot, co_his, co_reg, co_itm, tx_codalt, tx_codalt2, "+ 
                            " tx_nomitm, co_bod, nd_can, nd_preuni, nd_pordes, st_ivaven, nd_precom, "+ 
                            " nd_pordesprecom, co_prv, fe_his, co_usrhis, st_regrep) "+
                            " SELECT  "+
                            " co_emp, co_loc, co_cot,  "+intCodHis+",   co_reg, co_itm, tx_codalt,  tx_codalt2, "+
                            " tx_nomitm, co_bod, nd_can, nd_preuni, nd_pordes, st_ivaven, nd_precom,  "+
                            " nd_pordesprecom, co_prv, '"+strFeSis+"', "+objZafParSis.getCodigoUsuario()+"  , st_regrep  "+
                            " FROM tbm_detcotven   WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and  co_cot="+txtCot.getText();
                        
                        sql +="; INSERT INTO tbh_pagcotven( "+ 
                            " co_emp, co_loc, co_cot, co_his, co_reg, ne_diacre, fe_ven, nd_porret, "+ 
                            " mo_pag, ne_diagra, fe_his, co_usrhis,  co_tipret, st_regrep) "+ 
                            " SELECT  "+ 
                            " co_emp, co_loc, co_cot, "+intCodHis+",  co_reg, ne_diacre, fe_ven, nd_porret,  "+ 
                            " mo_pag, ne_diagra, '"+strFeSis+"' ,  "+objZafParSis.getCodigoUsuario()+",  co_tipret, st_regrep  "+ 
                            " FROM tbm_pagcotven  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and  co_cot="+txtCot.getText();
                        stmAct.executeUpdate(sql);
                    }
                    strFecHisCot = strFecModCot;
                  }
                 stmAct.close();
                 stmAct=null;
                 conTipDoc.commit();
                 conTipDoc.close();
                 conTipDoc=null;
            }
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
		    
		case INT_TBL_TRATOT:
		     strMsg="Transferencia Total";
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
    
    
    
    
    // public class ZafAutPrg
    private class creaFac {
        private int intTipDoc = 1;
        private Librerias.ZafUtil.ZafCtaCtb_dat  objZafCtaCtb_dat;  // Para obtener  los codigos y nombres de ctas ctbles
        /**
         * Crea objeto del tipo creaFac
         * @param intTipDocuemto un entero con el tipo de documento a crear
         */
        public creaFac(int intTipDocuemto){
            this.intTipDoc = intTipDoc;
         
            objZafCtaCtb_dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis, intTipDoc);
        }
        /**
         * Crea objeto del tipo creaFac ,
         * que sirve para crear una factura
         */
        public creaFac(){
          
            objZafCtaCtb_dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis, intTipDoc);
        }
        /////////******************************//////////////////////////////***************/////////////
        private void MensajeValidaCampo(String strNomCampo){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        
        
        
        
        private String FechaPriDiaMes(java.sql.Connection conTmp){
            String fecha="";
            String sql="";
            java.sql.Statement stmTmp;
            java.sql.ResultSet rst;
            try{
                sql = "SELECT min(fecha) as fecha FROM (" +
                "   select  fecha, " +
                "   case extract(dow from fecha) " +
                "   when 1 then 1  " +
                "   when 2 then 2  " +
                "   when 3 then 3  " +
                "   when 4 then 4  " +
                "   when 5 then 5  " +
                "   when 6 then 6  " +
                "   else 0   " +
                "   end as dia,  " +
                " ( " +
//                "     select count(fe_diafer) from tbm_loc as a  " +
//                "     inner join tbm_ciu as b on (b.co_ciu=a.co_ciu) " +
//                "     inner join tbr_diaFerCiu as  c on (c.co_ciu=b.co_ciu) " +
//                "     inner join tbm_diaFer as  d on (d.co_diafer=c.co_diafer)  " +
//                "     where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and d.fe_diafer=fecha " +
                "     select count(fe_dia) from tbm_loc as a  " +
                "     inner join tbm_calciu as b on (b.co_ciu=a.co_ciu) " +
                "     where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and b.fe_dia=fecha and b.tx_tipdia = 'F' " +
                " ) as xx " +
                " FROM ( " +
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 01)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 02)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 03)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 04)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 05)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 06)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 07)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 08)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 09)  as fecha union all  "+
                " select  date( extract(year from date(current_date+interval'1 month')) || '-' || extract(month from date(current_date+interval'1 month')) || '-' || 10)  as fecha            "+
                " )  as x "+
                " ) as y   where  y.dia!=0 and xx=0";
                
               // System.out.println("FECHA ==>  "+ sql );
                
                if (conTmp!=null){
                    stmTmp = conTmp.createStatement();
                    rst=stmTmp.executeQuery(sql);
                    if(rst.next()){
                        fecha=rst.getString("fecha");
                        
                        datFecAux = rst.getDate("fecha");
                        //System.out.println("FECHA ==>  "+ datFecAux );
                        
                        
                    }
                    rst.close();
                    stmTmp.close();
                    stmTmp=null;
                    rst=null;
                }}catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
            catch(Exception Evt)   {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
            return fecha;
        }
        
        
        
     
        
        
private boolean insertarCabFac(java.sql.Connection conn, int intCodTipDoc, int intCodDoc, String strConfInv ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intCodMnuFac=0;
  int intSecGrp = 0;
  int intSecEmp = 0;
  try{
      stmLoc=conn.createStatement();
      
       if(intCodMenuOri == 1612){
        //java.text.SimpleDateFormat objSimDatFor;
        //objSimDatFor=new java.text.SimpleDateFormat("yyyy-MM-dd");
        strFecSis = FechaPriDiaMes(CONN_GLO);
        intCodMnuFac=intCodMenuOri;
       }else{
         datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
         if (datFecAux==null)
             return false;
         intCodMnuFac=14; 
       }
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
      strFecModCot=strFecSistema;
      strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
      " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ne_numgui, " +
      " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
      " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
      " ,st_coninvtraaut, st_excDocConVenCon, st_coninv ) " +
      " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+
      intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+txtCliCod.getText()+" ,'"+txtVenCod.getText()+"','"+txtAte.getText()+"','"+
      txtCliNom.getText()+"','"+txtCliDir.getText()+"','"+txtcli_identificacion.getText()+ "','"+txtcli_telefono.getText()+"','"+
      txtcli_ciudad.getText()+ "','"+txtVenNom.getText()+"',0 ,"+((txtCot.getText().equals(""))?"0":txtCot.getText())+","+
      " 0, '"+txaObs1.getText()+"' ,'"+txaObs2.getText()+"' ,"+dblSubtotalCot * -1 + " ,"+dblPorIva+" ,"+dblTotalCot * -1 +", "+
      Double.parseDouble(txtIva.getText()) * -1 + " , "+txtCodForPag.getText()+" ,'"+txtNomForPag.getText()+"', '"+strFecSistema+"', "+
      objZafParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+" , "+txtCod.getText()+", '"+txtVehRet.getText()+"'," +
      " '"+txtChoRet.getText()+"', 'R', "+intSecGrp+", "+intSecEmp+", '"+tx_numped.getText()+"', 'I' ,'C' , 'N', "+intCodMnuFac+" "+
      " ,'S', 'S', '"+strConfInv+"' )";
      stmLoc.executeUpdate(strSql);
      
      strSql="UPDATE tbm_cabcotven SET st_reg='F' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
      " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_cot="+((txtCot.getText().equals(""))?"0":txtCot.getText());
      stmLoc.executeUpdate(strSql);
      
      strSql="SELECT a.co_locrelsoldevven,  a.co_tipdocrelsoldevven, a.co_docrelsoldevven " +
      ", a1.co_locrel, a1.co_tipdocrel, a1.co_docrel FROM " +
      " tbm_cabcotven as a " +
      " INNER JOIN tbm_cabsoldevven AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelsoldevven AND " +
      " a1.co_tipdoc=a.co_tipdocrelsoldevven AND a1.co_doc=a.co_docrelsoldevven ) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_cot="+((txtCot.getText().equals(""))?"0":txtCot.getText()); 
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
        strSql="UPDATE tbm_cabsoldevven SET st_mersindevfac='S', fe_mersindevfac="+objZafParSis.getFuncionFechaHoraBaseDatos()+" " +
        " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+rstLoc.getString("co_locrelsoldevven")+" AND " +
        " co_tipdoc="+rstLoc.getString("co_tipdocrelsoldevven")+" AND co_doc="+rstLoc.getString("co_docrelsoldevven");
        stmLoc.executeUpdate(strSql);
       
        strSql="INSERT INTO tbr_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, st_regrep) " +
        " VALUES("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+intCodDoc+", 'A', " +
        " "+objZafParSis.getCodigoEmpresa()+", "+rstLoc.getString("co_locrel")+", "+rstLoc.getString("co_tipdocrel")+", "+rstLoc.getString("co_docrel")+", 'I' )";
        stmLoc.executeUpdate(strSql);
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
   }catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
 return blnRes;
}        
        
        
 


        
private boolean actualizaStock(java.sql.Connection conn, java.sql.Connection connRemota, String strMerIngEgr ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String str_MerIEFisBod="S";
  String strEstFisBod="";
  int intTipCli=3;
  int  intCodItm =0;
  int intCodBod=0;
  int intTipStk=0;
  double dlbCanMov=0.00;
  try{
      stmLoc=conn.createStatement();
      intTipCli = objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(), conn);
      objInvItm.inicializaObjeto();
      
      strSql="SELECT a.co_itm,a.co_bod,sum(a.nd_can) as can " +
      " ,CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
      "  SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a  " +
      "  INNER JOIN tbm_reginvmernuningegrfisbod AS a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and  a.st_reg='P' ))) " +
      " THEN 'S' ELSE 'N' END AS proconf " +
      "  FROM tbm_detcotven AS a " +
      " INNER JOIN tbm_inv AS inv ON(a.co_emp=inv.co_emp AND a.co_itm=inv.co_itm) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
      " AND a.co_cot="+txtCot.getText()+" AND inv.st_ser='N' " +
      " GROUP BY inv.tx_codalt, a.co_itm,a.co_bod ";
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          intCodItm=rstLoc.getInt("co_itm");
          intCodBod=rstLoc.getInt("co_bod");
          dlbCanMov=rstLoc.getDouble("can");
          str_MerIEFisBod="N";
          if(strEstFisBod.equals("N")){
            if(strMerIngEgr.equals("S")) str_MerIEFisBod="S";
           }
          strEstFisBod=rstLoc.getString("proconf");
          objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, strMerIngEgr, str_MerIEFisBod, 1);  
          intTipStk=1;  
      }
      rstLoc.close();
      rstLoc=null;
      if(intTipStk==1){
         System.out.println("ConRem "+ connRemota ); 
         if(!objInvItm.ejecutaActStock(conn, intTipCli))
            return false;
         
         if(connRemota!=null){
         
           if(!objInvItm.ejecutaActStock(connRemota, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, intTipCli))
             return false;
         
         }else{
           if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
              return false;
         } 
      }
      objInvItm.limpiarObjeto();
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
   }catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
 return blnRes;
}        
        
        



 
        
private boolean insertarDetFac(java.sql.Connection conn, int intCodTipDoc, int intCodDoc, String strMerIngEgr ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  String str_MerIEFisBod="S";
  String strEstFisBod="";
  int  intCodItm =0;
  int intCodBod=0;
  int intEstIns=0;
  int intControl=0;
  double dlbCanMov=0.00;
  double dlbcostouni=0.00;
  double bldcostot=0.00;
  double dbl_canConIE=0.00;                     
  try{
      StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
      for(int i=0; i<tblDat.getRowCount();i++){
       if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
          intCodBod = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null || tblDat.getValueAt(i, INT_TBL_CODBOD).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString());
          intCodItm = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM));
          dlbCanMov  = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
          dbl_canConIE=dlbCanMov;
          
            if(intControl!=0) GLO_strArreItm=GLO_strArreItm+",";
             GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
            intControl++;
          /***********   VERFIFICA SI EL ITEM IE MER FIS BOD  ************************/
           strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
           str_MerIEFisBod="N";
          /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
          if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                if(strEstFisBod.equals("N")){
                  if(strMerIngEgr.equals("S")){   
                    str_MerIEFisBod="S";
                    dbl_canConIE=0;
          }}}
          /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
           dlbCanMov=dlbCanMov*-1;
           dlbcostouni = objInvItm.getCostoItm(conn, objZafParSis.getCodigoEmpresa(), intCodItm);
           bldcostot=(dlbcostouni * dlbCanMov);
           bldcostot=(bldcostot*-1);
           if (intEstIns > 0) stbins.append(" UNION ALL ");
             stbins.append("SELECT "+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+intCodTipDoc+","+intCodDoc+","+(i+1)+",'" +
             tblDat.getValueAt(i,INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"',"+tblDat.getValueAt(i, INT_TBL_CODITM)+", "+tblDat.getValueAt(i, INT_TBL_CODITM)+", '"+
             tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+intCodBod+","+
             dlbCanMov+","+
             objUti.redondear((Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i,  INT_TBL_TOTAL).toString())*-1),2)+", "+
             dlbcostouni + ", 0 , "+
             objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI).toString()),4) + ", " +
             objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", '" +
             ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
             ",'P',"+bldcostot+",'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" ");
         intEstIns=1;    
       }}
                        
        if(intEstIns==1){
            strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
            " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
            " co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom,st_reg " +//<==Campos que aparecen en la parte inferior del 1er Tab
            ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon )" + stbins.toString();
            stmLoc=conn.createStatement();
            stmLoc.executeUpdate(strSql);
            stmLoc.close();
            stmLoc=null;
            stbins=null;
        }
      blnRes=true;
   }catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
 return blnRes;
}        
        
        

        
               
       
private boolean insertarPagFac(java.sql.Connection conn, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false; 
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  java.util.Calendar objFec;
  int intVal =  intCanArr[0];
  int intsizearre = intarreglodia.length;
  int i=0;
  double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
  double dblPago=0.00;
  double dblPagos=0.00;
  double dblRete=0;
  String strSql="";
  String strFec=""; 
  try{
     if(conn!=null){
      stmLoc= conn.createStatement(); 
      intVal= intsizearre - (intsizearre-intVal);
      
      if(intCodMenuOri == 1612) strFecSis = FechaPriDiaMes(CONN_GLO);
       else strFecSis=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());
                
      strFec=objUti.formatearFecha(strFecSis ,"yyyy-MM-dd","dd/MM/yyyy");
      objFec = java.util.Calendar.getInstance();
      int fecDoc [] = txtFecDoc.getFecha(strFec);
                
      if(fecDoc!=null){
         objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
         objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
         objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
       }
                 
     dblBaseDePagos = dblTotalCot;
     
     if(dblBaseDePagos>0){
        Vector vecData = new Vector();
          
       if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
         Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
         /*************************************************************************************///
         dblRetFueFle = getSubtotalTrans();
         if(!(strCodTipPerCli==null)){
          if(!(strCodTipPerCli.trim().equals("1"))){
           if(dblRetFueFle>0){
              java.util.Vector vecReg = new java.util.Vector();
              dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
              vecReg.add(INT_TBL_PAGLIN, "");
              vecReg.add(INT_TBL_PAGCRE, "");
              vecReg.add(INT_TBL_PAGFEC, txtFecDoc.getText() );
              vecReg.add(INT_TBL_PAGRET, "1.00");
              vecReg.add(INT_TBL_PAGMON, ""+dblRetFueFle);
              vecReg.add(INT_TBL_PAGGRA, "");
              vecReg.add(INT_TBL_PAGCOD, "1");
              vecReg.add(INT_TBL_PAGSOP, "N");
             vecData.add(vecReg);  
            }} else { dblRetFueFle=0; }
         }else { dblRetFueFle=0; }

          strSql="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta "+
          " FROM tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)"+ 
          " WHERE polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = " + intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
          " AND polret.st_reg='A'  AND  '"+strFecSisBase+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
          rstLoc = stmLoc.executeQuery(strSql);
          while(rstLoc.next()){
             java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_PAGLIN, "");
             vecReg.add(INT_TBL_PAGCRE, "");
             vecReg.add(INT_TBL_PAGFEC, strFec );
             
             if(rstLoc.getString("tx_aplret").equals("S")){
                dblRetFue = getSubtotalSinTrans();
                if(!(dblRetFueFle>0))
                  dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rstLoc.getDouble("nd_porret")/100)),2 );
                 else
                  dblRetFue = objUti.redondeo( (    dblRetFue  * ( rstLoc.getDouble("nd_porret")/100)),2 );
                
               vecReg.add(INT_TBL_PAGRET, rstLoc.getString("nd_porret") );
               vecReg.add(INT_TBL_PAGMON, ""+dblRetFue);
               vecReg.add(INT_TBL_PAGGRA, "");  
            }
             if(rstLoc.getString("tx_aplret").equals("I")){
               dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rstLoc.getDouble("nd_porret")/100)),2 );
               
                vecReg.add(INT_TBL_PAGRET, rstLoc.getString("nd_porret") );
                vecReg.add(INT_TBL_PAGMON, ""+dblRetIva);
                vecReg.add(INT_TBL_PAGGRA, "");
             }
              vecReg.add(INT_TBL_PAGCOD, rstLoc.getString("co_tipret") );
              vecReg.add(INT_TBL_PAGSOP, "N");
              vecData.add(vecReg);  
          }
          rstLoc.close();
          rstLoc=null;
       /*************************************************************************************///
         dblRete = dblRetFueFle+dblRetFue+dblRetIva;
         dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);
         for(i=0; i < intVal; i++){
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
            
            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_PAGLIN, "");
            vecReg.add(INT_TBL_PAGCRE, ""+diaCre);
            vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText() );
            
            dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
            dblPago += dblPagos;
            dblPagos = objUti.redondear(dblPagos ,intNumDec);
            
            vecReg.add(INT_TBL_PAGRET, "");
            if(i==(intVal-1))
              dblPagos= objUti.redondear( dblPagos + (dblTotalCot  - (dblPago + dblRete )), intNumDec);
       
            vecReg.add(INT_TBL_PAGMON, ""+dblPagos);
            vecReg.add(INT_TBL_PAGGRA, "0");
            vecReg.add(INT_TBL_PAGCOD, "");
            vecReg.add(INT_TBL_PAGSOP, strSop);
            vecData.add(vecReg);  
        }
       objTblModPag.setData(vecData);
       tblPag.setModel(objTblModPag);
       vecData=null;
       /*************************************************************************************///
                        
       for(int x=0; x<tblPag.getRowCount();x++){
         i=x;
         int FecPagDoc[] =  txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString());
         String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
         String strSop = ((tblPag.getValueAt(i, INT_TBL_PAGSOP)==null)?"":tblPag.getValueAt(i, INT_TBL_PAGSOP).toString() );
         strSql="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
         " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
         " ,co_tipret ) VALUES (" +
         objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+intCodDoc+", "+(x+1)+", "+
         objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCRE))+", '"+strFechaPag+"',"+
         (objUti.redondear( objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON)), intNumDec ) * -1)+", "+       
         objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGGRA))+", "+       
         objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGRET))+", 'I', '"+strSop+"', "+       
         objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCOD))+")";
         stmLoc.executeUpdate(strSql);
      }
      blnRes=true;   
     /*************************************************************************************///
    }}
    stmLoc.close();
    stmLoc=null;
   }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
     catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
   return blnRes;    
 }        
        




private boolean insertarDiario(java.sql.Connection conn, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false; 
  java.sql.Statement stmLoc;
  String strSql="";
  String strPer=null, strMes="";
  String strFecSistema="";
  int intCodPer=0;
  int intMes =0;
  try{  
     if(conn!=null){
      stmLoc=conn.createStatement();
     
      strFecSistema=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
     
      strSql="INSERT INTO  tbm_cabdia(co_emp, co_loc, co_tipDoc, co_dia, fe_dia, tx_glo, "+
      " fe_ing, co_usrIng, fe_ultMod, co_usrMod ) "+
      " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+
      intCodDoc+", '"+datFecAux +"','','"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+", "+
      " '"+strFecSistema+"', "+objZafParSis.getCodigoUsuario()+")";
      stmLoc.executeUpdate(strSql);
      
      intMes = datFecAux.getMonth()+1;
      if(intMes<10)  strMes="0"+String.valueOf(intMes);
        else  strMes=String.valueOf(intMes);
                        
      strPer =  String.valueOf((datFecAux.getYear()+1900))+strMes;
      intCodPer=Integer.parseInt(strPer);
                       
      if(insertarDetDia(conn, intCodTipDoc, intCodDoc, intCodPer)){
          blnRes=true;
      }else blnRes=false;
                        
    stmLoc.close();
    stmLoc=null;
   }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
     catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
   return blnRes;    
 }        
        
     
   
public double getTotalDescuento(){
double dblTotalDescuento=0;
for(int filaActual=0; filaActual< tblDat.getRowCount(); filaActual++){
    if(tblDat.getValueAt(filaActual, INT_TBL_CODITM)!=null){
        double dblCan    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_CANMOV).toString()))),6),
        dblPre    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PREUNI).toString()))),6),
        dblPorDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PORDES )==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PORDES).toString()))),2),
        dblValDes =  objUti.redondeo(((dblCan * dblPre)==0)?0:(objUti.redondeo((dblCan * dblPre)* dblPorDes, 6)  / 100),6);
        //dblTotalDescuento =  objUti.redondear(dblTotalDescuento + objUti.redondeo(dblValDes,6),2);
        dblTotalDescuento =  dblTotalDescuento + dblValDes;

    } }
dblTotalDescuento =  objUti.redondear(objUti.redondear(dblTotalDescuento,4),2);
return dblTotalDescuento;
}

         

     
/**
 * Esta Funcion permite realizar el ingreso de detalle de diario.
 * @param intCodDoc : recive el codigo del documento
 * @param conDetDia : recive la coneccion de la base
 * @param intCodPer : recive perido actual
 */
private boolean insertarDetDia(java.sql.Connection conn, int intCodTipDoc, int intCodDoc, int intCodPer){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String srtSql="";
  String srtSqlSal="";
  double dblValTotDes=0;
  try{
    if(conn!=null){ 
    stmLoc=conn.createStatement();  
    
    srtSql="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab )"+
    " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodTipDoc+", "+intCodDoc+", ";
                
     dblValTotDes = getTotalDescuento();
     if(dblTotalCot>0){
        java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 1,"+objZafCtaCtb_dat.getCtaDeb()+","+
        dblTotalCot+", 0 ) " );
        pstDetDia.executeUpdate();
                    
        srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+dblTotalCot+"  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cta="+objZafCtaCtb_dat.getCtaDeb()+" AND co_per="+intCodPer;
        stmLoc.executeUpdate(srtSqlSal);
    }
    if( dblValTotDes > 0){
       java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 2, "+objZafCtaCtb_dat.getCtaDescVentas()+", "+
       dblValTotDes+", 0 )");
       pstDetDia.executeUpdate();
                    
       srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+dblValTotDes+"  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cta="+objZafCtaCtb_dat.getCtaDescVentas()+" AND co_per="+intCodPer;
       stmLoc.executeUpdate(srtSqlSal);
     }
     /** Agregando la cuenta de Ventas  */
     if( (dblValTotDes+dblSubtotalCot) > 0){
         java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 3, "+objZafCtaCtb_dat.getCtaHab()+", 0, " +
         objUti.redondear(dblValTotDes+ dblSubtotalCot,2)+" )");
         pstDetDia.executeUpdate();
                    
         srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+(objUti.redondear(dblValTotDes+ dblSubtotalCot,2)*-1)+"  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cta="+objZafCtaCtb_dat.getCtaHab()+" AND co_per="+intCodPer;
         stmLoc.executeUpdate(srtSqlSal);
     }
     /** Agregando la cuenta Iva en Ventas  */
     if( dblIvaCot > 0){
         java.sql.PreparedStatement pstDetDia = conn.prepareStatement(srtSql+" 4 , "+objZafCtaCtb_dat.getCtaIvaVentas()+", 0, "+
         dblIvaCot+" )");
         pstDetDia.executeUpdate();
                    
         srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+(dblIvaCot*-1)+"  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cta="+objZafCtaCtb_dat.getCtaIvaVentas()+" AND co_per="+intCodPer;
         stmLoc.executeUpdate(srtSqlSal);
     }
                
    //PARA ACTUALIZAR SALDOS DE LOS NODOS PADRES
    for(int j=6; j>1; j--){
        strSQL="";
        strSQL+="UPDATE tbm_salCta";
        strSQL+=" SET nd_salCta=b1.nd_salCta";
        strSQL+=" FROM (";
        strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
        strSQL+=" FROM tbm_plaCta AS a1";
        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
        strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
        strSQL+=" AND a1.ne_niv=" + j;
        strSQL+=" AND a2.co_per=" + intCodPer + "";
        strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
        strSQL+=" ) AS b1";
        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
        stmLoc.executeUpdate(strSQL);
   }
        //PARA ACTUALIZAR EL SALDO DE LA CUENTA DE ESTADO DE RESULTADOS
        strSQL="UPDATE tbm_salCta";
        strSQL+=" SET nd_salCta=b1.nd_salCta";
        strSQL+=" FROM (";
        strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
        strSQL+=" FROM tbm_plaCta AS a1";
        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
        strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
        strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
        strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
        strSQL+=" AND a2.co_per=" + intCodPer + "";
        strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
        strSQL+=" ) AS b1";
        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
        stmLoc.executeUpdate(strSQL);
       
      stmLoc.close();
      stmLoc=null;
     blnRes=true;
  }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
        
             
     
     


          
public boolean insertar(java.sql.Connection conIns){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strMerIngEgr="", strTipIngEgr="";
  String strSql="";
  String strConfInv="F";
  int intCodDoc=0;
  try{
     if(conIns!=null){
        conIns.setAutoCommit(false);

        if(!abrirConRem())
           return false;
        
          stmLoc=conIns.createStatement();
         
           /************************ OBTIENE MAX CODIGO DE CABMOVINVV *****************************************/
           strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
           " co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+intTipDoc;
           rstLoc = stmLoc.executeQuery(strSql);
           if(rstLoc.next())
               intCodDoc = rstLoc.getInt("co_doc");
           rstLoc.close();
           rstLoc=null;
           
           strSql="SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp="+objZafParSis.getCodigoEmpresa()+" "+
           " and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipDoc="+intTipDoc;
           rstLoc = stmLoc.executeQuery(strSql);
           if(rstLoc.next()){
              strMerIngEgr=rstLoc.getString("st_meringegrfisbod");  
              strTipIngEgr=rstLoc.getString("tx_natdoc");  
           }
           rstLoc.close();
           rstLoc=null;
           stmLoc.close();
           stmLoc=null; 
          /**********************************************************************************************************/             
         
         for(int i=0; i<tblDat.getRowCount();i++){
           if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
            String strEst=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
             if(strEst.equals("N")){
                  strConfInv="P";
          }}}
         if(strConfInv.equals("P"))
          if(strMerIngEgr.equals("N")) strConfInv="F";
           
        
        if(insertarFactura(conIns, conRemGlo, intTipDoc, intCodDoc, strConfInv, strMerIngEgr )){
            if(INTCODREGCEN!=0) conRemGlo.commit();
             conIns.commit();
             conIns.setAutoCommit(true);
             asignaNumeroOc(conIns, intTipDoc, intCodDoc );
             asignaNumeroFac(conIns, conRemGlo, objZafParSis.getCodigoEmpresa(), intTipDoc, intCodDoc );
             strstReg_glo="F";
             blnRes=true;
        }else{ if(INTCODREGCEN!=0) conRemGlo.rollback(); conIns.rollback(); }
        
        
        if(conRemGlo!=null) {
          conRemGlo.close();
          conRemGlo=null;
        }
        conIns.close();
        conIns=null;
  }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
        
    
     


public boolean asignaNumeroFac(java.sql.Connection conIns, java.sql.Connection connRemoto, int intCodEmp, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strMsg="", strTit="Mensaje del sistema Zafiro";
  int intNumDoc=0;
  javax.swing.JOptionPane oppMsg;
  try{
     if(conIns!=null){
        stmLoc=conIns.createStatement();
        oppMsg = new javax.swing.JOptionPane();
        strMsg="La factura se grabo correctamente   \n   Desea Imprimir la Factura   ";

       if(intCodEmp==1 || intCodEmp==2 || intCodEmp==4){
        if(oppMsg.showConfirmDialog(jfrThis,strMsg, strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
                          
          strSql="SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc FROM tbm_cabTipDoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
          " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+intCodTipDoc;
          rstLoc=stmLoc.executeQuery(strSql);
          if(rstLoc.next())
             intNumDoc = rstLoc.getInt("ultnum");
          rstLoc.close();
          rstLoc=null;
         
          strSql="UPDATE tbm_cabTipDoc SET ne_ultDoc="+intNumDoc+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
          " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+intCodTipDoc;
          strSql+=";UPDATE tbm_cabmovinv SET ne_numdoc="+intNumDoc+", ne_numgui="+intNumDoc+", st_reg='A' , st_imp='S'  " +
          " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intTipDoc+" " +
          " AND co_doc="+intCodDoc+" AND ne_numcot="+((txtCot.getText().equals(""))?"0":txtCot.getText());
          stmLoc.executeUpdate(strSql);
          //********************************************************************
          impresionFactura(intTipDoc, intCodDoc);
           strMsg="La factura se le asigno el siguiente numero : " + intNumDoc;
          this.mostrarMsg(strMsg);
            
          impresionTransferencia(conIns, connRemoto, intCodTipDoc, intCodDoc, intNumDoc );
                  
        }else this.mostrarMsg("La factura se grabo correctamente");
      }
        stmLoc.close(); 
        stmLoc=null;
       blnRes=true;
 }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
               
          
          
            
          
          
public boolean impresionTransferencia(java.sql.Connection conIns, java.sql.Connection connRemoto, int intCodTipDoc, int intCodDoc, int intNumFac ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="", strMsg="", strTit="Mensaje del sistema Zafiro";
  javax.swing.JOptionPane oppMsg;
  try{
     if(conIns!=null){
       stmLoc=conIns.createStatement();
       stmLoc01=conIns.createStatement();
       oppMsg = new javax.swing.JOptionPane();

       if( intCodEmp==2 || intCodEmp==4 ){
         strSql="SELECT a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, b.ne_numdoc FROM tbr_cabmovinv as a " +
         " INNER JOIN tbm_cabmovinv AS b on (b.co_emp=a.co_emprel and b.co_loc=a.co_locrel and b.co_tipdoc=a.co_tipdocrel and b.co_doc=a.co_docrel) "+
         " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
         " AND a.co_tipdoc="+intCodTipDoc+" AND a.co_doc="+intCodDoc+" AND a.co_emprel="+objZafParSis.getCodigoEmpresaGrupo();
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){
           strSql="UPDATE tbm_cabmovinv SET tx_obs2='Tranferencia Correspondiente a la Factura # "+intNumFac+"' " +
           " WHERE co_emp="+rstLoc.getString("co_emprel")+" AND  co_loc="+rstLoc.getString("co_locrel")+" " +
           " AND co_tipdoc="+rstLoc.getString("co_tipdocrel")+" AND co_doc="+rstLoc.getString("co_docrel");
           stmLoc01.executeUpdate(strSql);
           if(INTCODREGCEN!=0){
                java.sql.Statement stmtranRem = connRemoto.createStatement();
                stmtranRem.executeUpdate(strSql);
                stmtranRem.close();
                stmtranRem=null;
           }
           strMsg=" Desea imprimir la Transferencia automatica numero : " + rstLoc.getString("ne_numdoc");
           if(oppMsg.showConfirmDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
               imprimirTraAut(rstLoc.getInt("co_emprel"), rstLoc.getInt("co_locrel"), rstLoc.getString("co_tipdocrel"), rstLoc.getString("ne_numdoc"), conIns );
           }
         }
         rstLoc.close(); 
         rstLoc=null;
       } 
       stmLoc.close(); 
       stmLoc=null;
       stmLoc01.close(); 
       stmLoc01=null;
       oppMsg=null;
       blnRes=true;
    }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
                                        
     
   
        

public boolean asignaNumeroOc(java.sql.Connection conIns, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc, rstLoc01;
  String strSql="", strMsg="";
  int intNumDoc=0;
  try{
     if(conIns!=null){
         stmLoc=conIns.createStatement();
         stmLoc01=conIns.createStatement();
  
         strSql="SELECT co_locrel,co_tipdocrel,co_docrel FROM tbr_cabmovinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
         " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+intCodTipDoc+" "+
         " AND co_doc="+intCodDoc+" AND co_emprel="+objZafParSis.getCodigoEmpresa();
         rstLoc=stmLoc.executeQuery(strSql);
         while(rstLoc.next()){
            strSql="SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc FROM tbm_cabTipDoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
            " AND co_loc="+rstLoc.getString("co_locrel")+" AND co_tipDoc="+rstLoc.getString("co_tipdocrel");
            rstLoc01=stmLoc01.executeQuery(strSql);
            if(rstLoc01.next())
               intNumDoc = rstLoc01.getInt("ultnum");
            rstLoc01.close();
            rstLoc01=null;
                    
             strSql="UPDATE tbm_cabTipDoc SET ne_ultDoc="+intNumDoc+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
             " AND co_loc="+rstLoc.getString("co_locrel")+" AND co_tipDoc="+rstLoc.getString("co_tipdocrel");
             strSql+=";UPDATE tbm_cabmovinv SET ne_numdoc="+intNumDoc+", st_reg='A' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
             " AND co_loc="+rstLoc.getString("co_locrel")+" AND co_tipdoc="+rstLoc.getString("co_tipdocrel")+" AND co_doc="+rstLoc.getString("co_docrel");
             stmLoc01.executeUpdate(strSql);
          
             strMsg="Imprima\n la orden de compra numero : " + intNumDoc;
             this.mostrarMsg(strMsg);
             imprimeOrdCom(rstLoc.getString("co_locrel"), rstLoc.getString("co_tipdocrel"), rstLoc.getString("co_docrel"));
          }
          rstLoc.close(); 
          rstLoc=null;
          stmLoc.close(); 
          stmLoc=null;
          stmLoc01.close(); 
          stmLoc01=null;
          blnRes=true;
 }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
        
    
  


public boolean asignaSecEmpGrpFac(java.sql.Connection conIns, java.sql.Connection connRemoto, int intCodTipDoc, int intCodDoc ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  int intSecEmp=0;
  int intSecGrp=0;
  String strSql="";
  try{
      stmLoc=conIns.createStatement();
//      if(connRemoto!=null) {
//            intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(connRemoto);
//            intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(connRemoto);
//        }else{
//            intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(conIns);
//            intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(conIns);
//        }
        Glo_intCodSec=intSecGrp;   
     
     strSql="UPDATE tbm_cabmovinv SET ne_secemp="+intSecEmp+", ne_secgrp="+intSecGrp+" " +
     " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
     " AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc;
     stmLoc.executeUpdate(strSql);
     stmLoc.close();
     stmLoc=null;
    blnRes=true; 
  }catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
        



public boolean insertarFactura(java.sql.Connection conIns, java.sql.Connection connRemoto, int intTipDoc, int intCodDoc, String strConfInv, String strMerIngEgr ){
  boolean blnRes=false;
  try{
     if(conIns!=null){
        dblPorIva = bldivaEmp;
      
         if(insertarCabFac(conIns, intTipDoc, intCodDoc, strConfInv )){
          if(insertarDetFac(conIns, intTipDoc, intCodDoc, strMerIngEgr )){
           if(insertarPagFac(conIns, intTipDoc, intCodDoc )){
            if(insertarDiario(conIns, intTipDoc, intCodDoc)){           
             if(realizaOrdComAut(conIns, connRemoto, intTipDoc, intCodDoc)) {
              if(actualizaStock(conIns, connRemoto,  strMerIngEgr)){ 
               if(asignaSecEmpGrpFac(conIns, connRemoto, intTipDoc, intCodDoc)){   
                  blnRes=true;
               }else blnRes=false;   
              }else blnRes=false;
             }else blnRes=false;
            }else blnRes=false;
           }else blnRes=false;
          }else blnRes=false;
         }else blnRes=false;
     objInvItm.limpiarObjeto();   
  }}catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
        
    
    

public void mostrarMsg(String strMsg) {
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    oppMsg.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
}

   
  
          
public boolean impresionFactura(int codtipdoc,int codoc){
 Connection conIns;
 int intCodEmp=objZafParSis.getCodigoEmpresa();
 int intCodLoc=objZafParSis.getCodigoLocal();
 retNomEmp(intCodEmp);
 String nomEmp= GLO_strnomEmp;
 String dirEmp = GLO_strdirEmp;
 String fecha="";
 java.util.Date dtFecHor;
 try{
    conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    if(conIns!=null){
        dtFecHor=new java.util.Date();
        fecha=objUti.formatearFecha(dtFecHor,"dd-MM-yyyy-hh-mm-ss");
        JasperDesign jasperDesign = JRXmlLoader.load(DIRECCION_REPORTE2);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
       //para factura
        String codtipdoc2=String.valueOf(codtipdoc);
        String codoc2=String.valueOf(codoc);
        Map parameters = new HashMap();
        parameters.put("codEmp", ""+intCodEmp);
        parameters.put("codLoc", ""+intCodLoc);
        parameters.put("codTipDoc", codtipdoc2);
        parameters.put("codDoc", codoc2);
        parameters.put("dirEmp", dirEmp);
        parameters.put("nomEmp", nomEmp);
        parameters.put("fechaHor", fecha);
        parameters.put("obs1", txaObs1.getText());
        parameters.put("subtot",new Double(txtSub.getText()));
        parameters.put("iva", new Double(txtIva.getText()));
        parameters.put("total", new Double(txtTot.getText()));
                        
       
         if(intCodEmp==1){
           javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
           objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
           JasperDesign jasperDesignGuiaRem = JRXmlLoader.load(DIRECCION_REPORTE2);
           JasperReport jasperReportGuiaRem = JasperCompileManager.compileReport(jasperDesignGuiaRem);
           JasperPrint reportGuiaRem =JasperFillManager.fillReport(jasperReportGuiaRem, parameters,  conIns);
            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strImpDirectaFac , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
        }else{
             JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
             JasperPrintManager.printReport(report,true);
        }
          
        
        if(intCodEmp==1){
           javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
           objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
          // objPriReqAttSet.add(javax.print.attribute.standard.MediaSize.findMedia( (float)100.731, (float)100.720, javax.print.attribute.standard.MediaPrintableArea.MM));
           JasperDesign jasperDesignGuiaRem = JRXmlLoader.load(DIRECCION_REPORTE_GUIAREM);
           JasperReport jasperReportGuiaRem = JasperCompileManager.compileReport(jasperDesignGuiaRem);
          // strImpDirecta=txaObs1.getText().trim();
           JasperPrint reportGuiaRem =JasperFillManager.fillReport(jasperReportGuiaRem, parameters,  conIns);
            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strImpDirecta , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
        }
        
        conIns.close();
        conIns=null;

 }}catch (JRException e) {  objUti.mostrarMsgErr_F1(jfrThis, e);  }
   catch(SQLException ex) { objUti.mostrarMsgErr_F1(jfrThis, ex);   }
 return true;
}


 


private void imprimirTraAut(int intcodemp, int intcodloc, String strcodtipdoc, String strNumdoc, java.sql.Connection conImp){
try{
  if(conImp!=null){
      JasperDesign jasperDesign = JRXmlLoader.load(DIRECCION_REPORTE_TRANS);
      JasperDesign jasperDesign2 = JRXmlLoader.load(DIRECCION_REPORTE_TRANS1);
      JasperReport jasperReport2 = JasperCompileManager.compileReport(jasperDesign);
      JasperReport subReport = JasperCompileManager.compileReport(jasperDesign2);
      String desde1 = strNumdoc;
      String desde2 = strNumdoc;
      String desde3 = strNumdoc;
      desde3 = desde3+"%";

          String fecdesde="2006-05-22",fechasta="2006-05-22";  
          int intval1=1;
          int intval3=0;

          Map parameters = new HashMap();
          parameters.put("co_emp2", new Integer(intcodemp) );
          parameters.put("co_loc2", new Integer(intcodloc) );
          parameters.put("co_tipdoc2", strcodtipdoc );
          parameters.put("numdoc1", desde1 );
          parameters.put("numdoc2", desde2 );
          parameters.put("numdoc3", desde3 );
          parameters.put("fecdesde", fecdesde );
          parameters.put("fechasta", fechasta );
          parameters.put("val1",  new Integer(intval1) );
          parameters.put("val3",  new Integer(intval3) );
          parameters.put("SUBREPORT", subReport); 

          JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conImp);
          JasperViewer.viewReport(report, false);
  }}catch (JRException e){ objUti.mostrarMsgErr_F1(jfrThis, e); }
}






public boolean imprimeOrdCom(String codLoc, String codTipDoc, String codDoc){
 Connection conIns;
 int intCodEmp=objZafParSis.getCodigoEmpresa();
 String strTit=objZafParSis.getNombreMenu();
 String strNomEmp=retNomEmp(intCodEmp);
 String strTelCli=""; //retTelCli();
 String strRucCli=""; //retRucCli();
 String strCodForPag = ""; //cboForPag.getSelectedItem() +"";
 try {
    conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conIns!=null){
       JasperDesign jasperDesign = JRXmlLoader.load(DIRECCION_REPORTE_OC);
       JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map parameters = new HashMap();
        parameters.put("tit", strTit);
        parameters.put("numDoc",  codDoc );
        parameters.put("codEmp", ""+intCodEmp);
        parameters.put("codLoc", ""+codLoc);
        parameters.put("CodTipDoc", codTipDoc);
        parameters.put("fecCot", "");
        parameters.put("codCli", "");
        parameters.put("nomCli", "");
        parameters.put("rucCli", strRucCli);
        parameters.put("dirCli", "");
        parameters.put("nomVen", "");
        parameters.put("obs1", txaObs1.getText());
        parameters.put("obs2", txaObs2.getText());
        parameters.put("subTot", new Double(txtSub.getText()));
        parameters.put("iva", new Double(txtIva.getText()));
        parameters.put("totFin", new Double(txtTot.getText()));
        parameters.put("nomEmp", strNomEmp);
        parameters.put("telCli", strTelCli);
        parameters.put("forPag", strCodForPag);
        parameters.put("atencion", txtAte.getText());
        parameters.put("CodOrdCom", "");

        
       if(intCodEmp==1){
           javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
           objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
          // objPriReqAttSet.add(javax.print.attribute.standard.MediaSize.findMedia( (float)100.731, (float)100.720, javax.print.attribute.standard.MediaPrintableArea.MM));
           JasperDesign jasperDesignGuiaRem = JRXmlLoader.load(DIRECCION_REPORTE_OC);
           JasperReport jasperReportGuiaRem = JasperCompileManager.compileReport(jasperDesignGuiaRem);
          // strImpDirecta=txaObs1.getText().trim();
           JasperPrint reportGuiaRem =JasperFillManager.fillReport(jasperReportGuiaRem, parameters,  conIns);
            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strImpDirectaOC , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
        }else{
             JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
             JasperPrintManager.printReport(report,true);
        }
           
      
    conIns.close();
    conIns=null;
  }}catch (JRException e){ objUti.mostrarMsgErr_F1(jfrThis, e);  }
    catch(SQLException ex) { objUti.mostrarMsgErr_F1(jfrThis, ex); }
  return true;
}





public boolean IMP_GUIA(int codtipdoc,int codoc){

int intCodEmp=objZafParSis.getCodigoEmpresa();
int intCodLoc=objZafParSis.getCodigoLocal();
retNomEmp(intCodEmp);
String nomEmp= GLO_strnomEmp;
String dirEmp = GLO_strdirEmp;
int intCodCli=Integer.parseInt(txtCliCod.getText());
String strCodForPag = txtNomForPag.getText();  // cboForPag.getSelectedItem() +"";
java.util.Date dtFecHor=new java.util.Date();
String fecha=objUti.formatearFecha(dtFecHor,"dd-MM-yyyy-hh-mm-ss");
Connection conIns;
try {
    conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    try {
        if(conIns!=null){
            String codtipdoc2=String.valueOf(codtipdoc);
            String codoc2=String.valueOf(codoc);

            JasperDesign jasperDesign = JRXmlLoader.load("C://Zafiro//Reportes_impresos//RptGuiRem.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map parameters = new HashMap();
            parameters.put("codEmp", ""+intCodEmp);
            parameters.put("codLoc", ""+intCodLoc);
            parameters.put("codTipDoc", codtipdoc2);
            parameters.put("codDoc", codoc2);
            parameters.put("dirEmp", dirEmp);
            parameters.put("nomEmp", nomEmp);
            parameters.put("fechaHor", fecha);
            parameters.put("obs1", txaObs1.getText());
            JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
            JasperPrintManager.printReport(report,true);
        }
        conIns.close();
        conIns=null;
    }
    catch (JRException e) {  objUti.mostrarMsgErr_F1(jfrThis, e);   }
} catch(SQLException ex) { objUti.mostrarMsgErr_F1(jfrThis, ex);  }


return true;
}



        
        
        //********************************************************************************************
        /**
         * Esta funcion se encarga de insertar los pagos de la factura
         * @param intCodDoc : recive el codigo del codumento 
         * @param conDetPag : recive la coneccion de la base
         *
         */
        public void insertarPagMovInv_respaldo(int intCodDoc, java.sql.Connection conDetPag){
            int intVal =  intCanArr[0];
            int intsizearre = intarreglodia.length;
            intVal= intsizearre - (intsizearre-intVal);
            java.sql.Statement stm;
            java.sql.ResultSet rst;
            int intFilUltPag = 0;
            double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
            String sql;
            
            try{
                
                
                if(intCodMenuOri == 1612){
                    strFecSis = FechaPriDiaMes(CONN_GLO);
                }else{
                    strFecSis=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());
                }
                
                
                String fecha = objUti.formatearFecha(strFecSis ,"yyyy-MM-dd","dd/MM/yyyy");
                
                java.util.Calendar objFec = java.util.Calendar.getInstance();
                int fecDoc [] = txtFecDoc.getFecha(fecha);
                
                if(fecDoc!=null){
                    objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                    objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                    objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
                }
                 
                dblBaseDePagos = dblTotalCot;
                
                //Borrando los datos actuales del jtable
                javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblPag.getModel());
                modTbl.setRowCount(0);
                
                if(dblBaseDePagos>0){
                    
                    if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
                        
                        Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                        
                        //*************************************************************************************///
                        dblRetFueFle = getSubtotalTrans();
                        
                        if(!(strCodTipPerCli==null)){
                            if(!(strCodTipPerCli.trim().equals("1"))){
                                if(dblRetFueFle>0){
                                    ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
                                    intFilUltPag = tblPag.getRowCount()-1;
                                    tblPag.setValueAt(new String("") ,intFilUltPag , 1);
                                    tblPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
                                    tblPag.setValueAt("1.000", intFilUltPag, 3);
                                    dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
                                    tblPag.setValueAt( new Double(dblRetFueFle) , intFilUltPag, 4);
                                    tblPag.setValueAt("1" , intFilUltPag, 6);
                                    tblPag.setValueAt("N",  intFilUltPag, 7);
                                    
                                }} else { dblRetFueFle=0; }
                        }else { dblRetFueFle=0; }
                        
                           
                          
                        if (conDetPag!=null){
                            String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
                            strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
                            strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = " + intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
                            "  AND polret.st_reg='A'  AND  '"+strFecSisBase+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
                        
                            stm = conDetPag.createStatement();
                            rst = stm.executeQuery(strSQL);
                            while (rst.next()){
                                
                                ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
                                intFilUltPag = tblPag.getRowCount()-1;
                                tblPag.setValueAt(new String("") ,intFilUltPag , 1);
                                tblPag.setValueAt(fecha, intFilUltPag, 2);
                                
                                if (rst.getString("tx_aplret").equals("S")){
                                    //dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
                                    dblRetFue = getSubtotalSinTrans();
                                    if(!(dblRetFueFle>0))
                                        dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
                                    else
                                        dblRetFue = objUti.redondeo( (    dblRetFue  * ( rst.getDouble("nd_porret")/100)),2 );
                                    
                                    
                                    tblPag.setValueAt(rst.getString("nd_porret"), intFilUltPag, 3);
                                    tblPag.setValueAt( new Double(dblRetFue) , intFilUltPag, 4);
                                }
                                if (rst.getString("tx_aplret").equals("I")){
                                    dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 );
                                    tblPag.setValueAt(rst.getString("nd_porret") , intFilUltPag, 3);
                                    tblPag.setValueAt( new Double(dblRetIva) , intFilUltPag, 4);
                                }
                                
                                tblPag.setValueAt(rst.getString("co_tipret") , intFilUltPag, 6);
                                tblPag.setValueAt("N",  intFilUltPag, 7);
                            }
                            rst.close();
                            rst=null;
                            stm.close();
                            stm=null;
                        }
                        
                        //*************************************************************************************///
                        double dblPago=0.00;
                        double dblPagos=0.00;
                        int i=0;
                        double dblRete=0;
                        dblRete = dblRetFueFle+dblRetFue+dblRetIva;
                        
                        //dblBaseDePagos = objUti.redondear( ( dblTotalCot - ( dblRetFue + dblRetIva)),intNumDec);
                        dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);
                        
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
                            
                            ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
                            intFilUltPag = tblPag.getRowCount()-1;
                            tblPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
                            tblPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
                            dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
                            dblPago += dblPagos;
                            dblPagos = objUti.redondear(dblPagos ,intNumDec);
                            tblPag.setValueAt( new Double(dblPagos) , intFilUltPag, 4);
                            tblPag.setValueAt(new Integer(0),  intFilUltPag, 5);
                            tblPag.setValueAt(strSop,  intFilUltPag, 7);
                        }
                        
                        
                        double dblultpag =  objUti.redondear(Double.parseDouble(tblPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
                        //dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago +dblRetIva + dblRetFue) ),intNumDec);
                        dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago + dblRete ) ),intNumDec);
                        tblPag.setValueAt( new Double(dblultpag) , intFilUltPag , 4);
                        //*************************************************************************************///
                        
                        
                        for(int x=0; x<tblPag.getRowCount();x++){
                            i=x;
                            
                            int FecPagDoc[] =  txtFecDoc.getFecha(tblPag.getValueAt(i, 2).toString());
                            String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                            
                            String strSop = ((tblPag.getValueAt(i, 7)==null)?"":tblPag.getValueAt(i, 7).toString() );
                            
                            sql = "INSERT INTO  tbm_pagMovInv" +
                            "(co_emp, co_loc, co_doc, co_tipDoc, co_reg, " + //CAMPOS PrimayKey
                            " ne_diaCre, fe_ven, " +//<==
                            " mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
                            " ,co_tipret )" +
                            "VALUES (" +
                            objZafParSis.getCodigoEmpresa() + ", " +
                            objZafParSis.getCodigoLocal()   + ", " +
                            intCodDoc + ", " +
                            intTipDoc+ ", " +
                            (x+1)     + ", " +
                            ((tblPag.getValueAt(i, 1)==null || tblPag.getValueAt(i, 1).toString().equals(""))?"0":tblPag.getValueAt(i, 1)) + ",'" +
                            strFechaPag + "'," +
                            (objUti.redondear(Double.parseDouble( (tblPag.getValueAt(i, 4)==null)?"0":tblPag.getValueAt(i, 4).toString() ),intNumDec) * -1 )+ ", " +
                            ((tblPag.getValueAt(i, 5)==null)?"0":tblPag.getValueAt(i, 5).toString() ) + ", " +
                            ((tblPag.getValueAt(i, 3)==null || tblPag.getValueAt(i, 3).toString().equals(""))?"0":tblPag.getValueAt(i, 3).toString()) +
                            " ,'I','"+strSop+"' ,"+Integer.parseInt(((tblPag.getValueAt(i, 6)==null)?"0":tblPag.getValueAt(i, 6).toString()))+")";
                            java.sql.PreparedStatement pstDetPag = conDetPag.prepareStatement(sql);
                            pstDetPag.executeUpdate();
                            
                            
                        }
                        
                        
                        //*************************************************************************************///
                    } }
            }
            catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
            catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
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
                        
                          
                          
                       int intTipPre=0;  
                        //**********************
                        if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                            if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                dblPreUniItm=0;
                            else{
                                dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                 intTipPre=1;
                            } 
                        }else{
                            dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                             intTipPre=1;
                        }
                            //**********************
                            
                            dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                            dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
                            
                            
                            //****************
                            if(dblPreUniItm!=0){
                                dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
                               
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
                             System.out.println(" Porsentaje  >>> "+ dblPor  );
                            
                            dblPor=objUti.redondear(dblPor,3);
                            
                            
                            if(intTipPre==1){
                               // System.out.println(" oki >>> "+ dblValorVenta +" < "+ dblMinimo );
                                if(dblValorVenta<dblMinimo){
                                    blnRes = false;
                                    tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                }
                            }else{
                                System.out.println(" oki >>> "+ dblValorVenta +" < "+ dblCostoItm );
                                System.out.println(" Porsentaje  >>> "+ dblPor  );
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
            try{
            
            
            double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = Glo_dlbMaxDes,
            dblMinimo, dblValorVenta, dblCostoItm=0, dblPorDesCom=0, dblPorGanancia = Glo_dlbMarUti;
            int intCoItm;
            double dblMarUtiItm=0;
            
            
            
//           java.math.BigDecimal precio = new java.math.BigDecimal("0.00");
//           java.math.BigDecimal costo = new java.math.BigDecimal("0.00");
//           java.math.BigDecimal marguen = new java.math.BigDecimal("0.00");
//           java.math.BigDecimal valor1 = new java.math.BigDecimal("1.0");
//           java.math.BigDecimal valor2 = new java.math.BigDecimal("100.0");
//           java.math.BigDecimal result = new java.math.BigDecimal("0.0");
           
            
            
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
                       
                       //marguen = new java.math.BigDecimal( ((tblDat.getValueAt(i, INT_TBL_MARUTI)==null)?"0.0":tblDat.getValueAt(i, INT_TBL_MARUTI).toString()) );
                            
                            
                         
                       
                        int intTipPre=0;
                        //**********************
                        if (tblDat.getValueAt(i, INT_TBL_ITMTER)!=null){
                            if (tblDat.getValueAt(i, INT_TBL_ITMTER).toString().trim().equalsIgnoreCase("S"))
                                dblPreUniItm=0;
                            else{
                                dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                 intTipPre=1;
                            }
                        }else{
                            dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                            intTipPre=1;
                        } 
                        
                        
                        
                            //**********************
                            
                            dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                            
                            //desc = new java.math.BigDecimal( ((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0.0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()) );
                            
                            
                            dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
                          
                         //   precio = new java.math.BigDecimal( String.valueOf(dblValorVenta) );
                            
                                    
                            
                            
                            if(dblPreUniItm!=0){
                                dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
                                
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
                                        
                                           
                                          
                                        dblCostoItm=objUti.redondear((dblCostoItm-(dblCostoItm*(dblPorDesCom/100))), objZafParSis.getDecimalesBaseDatos() );
                                        
                                    }
                                   
                                    dblMinimo    =   dblCostoItm * dblPorGanancia;
                                    dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
                            }
                            
                               
                            dblValorVenta=objUti.redondear(dblValorVenta,3);
                            dblMinimo=objUti.redondear(dblMinimo,3);
                           
                             System.out.println("Precio Unitario >>> : "+ dblPreUniItm );
                            
                           
//                            costo= new java.math.BigDecimal( String.valueOf(dblCostoItm) );
//                            result = costo.divide( precio, 6, java.math.BigDecimal.ROUND_HALF_UP );
//                            result = valor1.subtract(result);
//                            result = result.multiply( valor2 );  
//                            result = result.setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
//                            marguen = marguen.setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
                            
                            double dblPor = (1 - (dblCostoItm/dblValorVenta))*100;
                            
                            dblPor=objUti.redondear(dblPor,3);
                            
                                       
                                   
                            if(intTipPre==1){
                                System.out.println(" TipPre >>> "+ dblValorVenta +" < "+ dblMinimo );
                               if(dblPreUniItm > 0.00 ){
                                if(dblValorVenta<dblMinimo){
                                    blnRes = false;
                                    tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                }}else{
                                   blnRes = false;
                                   tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                }
                            }else{
                                System.out.println(" Util Anterior >>> "+ dblPor +" < "+ dblMarUtiItm );
                             //   System.out.println(" Util >>> "+ result +" < "+ marguen );
                                if( dblPor < dblMarUtiItm ){
                                    System.out.println("Ingresando..."); 
                                    blnRes = false;
                                    tblDat.setValueAt("true",i,INT_TBL_BLNPRE);
                                    //tblDat.setValueAt(new Boolean(true),i,INT_TBL_BLNPRE);
                                }}

                            
                    } }
            }
            
             System.out.println("Ingresando..."+ blnRes ); 
            blnHayCam=blnHayCam_glo;
            
            
            if(blnRes) {
                //System.out.println("Valida forma de pago ");
                blnRes=isUsrForPag();
            }
            System.out.println("Ingresando..."+ blnRes ); 
            //  //System.out.println("ENTRA LA NUEVA  VALIDACION ..."+ blnRes );
            
            }catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
            
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
            for(int i= 0 ; i<tblPag.getRowCount() && blnIsVtaCon; i++){
                if( ((tblPag.getValueAt(i,  INT_TBL_DIACRE)==null)?0:Integer.parseInt(tblPag.getValueAt(i,  INT_TBL_DIACRE)+""))>0){
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
                    for(int i=0; i<tblPag.getRowCount();i++){
                        String strDiaCre = (( tblPag.getValueAt(i, 1)==null || tblPag.getValueAt(i, 1).equals(""))?"0":tblPag.getValueAt(i, 1).toString());
                        String strRet = (( tblPag.getValueAt(i, 3)==null || tblPag.getValueAt(i, 3).equals(""))?"0":tblPag.getValueAt(i, 3).toString());
                        
                        if(strDiaCre.trim().equals("0")){
                            if(strRet.trim().equals("0")){
                                dlbSumMon += Double.parseDouble( ((tblPag.getValueAt(i, 4)==null || tblPag.getValueAt(i, 4).equals(""))?"0":tblPag.getValueAt(i, 4).toString()));
                            }}
                    }
                    
                    sSQL = "SELECT numVenCon, abs(valor) ,  ne_nummaxvencon, nd_monmaxvencon  " +
                    "  ,CASE WHEN  numVenCon>=ne_nummaxvencon THEN 1 else 0  END  AS NumVenConCli  " +
                    "  ,CASE WHEN (abs(valor)+"+dlbSumMon+")>=nd_monmaxvencon THEN 1 else 0  END AS MonCanVen " +
                   
                    ",( "+
                    " SELECT count(distinct(a1.co_doc)) as numVenCon FROM tbm_cabMovInv AS a1 "+
                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   "+
                    " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_tipdoc=1 "+
                    " AND a1.co_cli="+txtCliCod.getText()+" " +
			" AND a1.st_reg NOT IN ('I','E') AND a1.ST_IMP IN ('S') AND a2.st_reg IN ('A','C')  " +
                        " AND ( a1.st_excDocConVenCon='S' OR a1.st_excDocConVenCon is null ) "+
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
                    "   AND a1.st_reg NOT IN ('I','E') AND a1.ST_IMP IN ('S')  AND a2.st_reg IN ('A','C') " +
                    " AND ( a1.st_excDocConVenCon='S' OR a1.st_excDocConVenCon is null ) "+         
                    "	AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)" +
                    "	AND (a2.nd_abo+a2.mo_pag) < 0  AND a.ne_tipforpag=1  " +
                    " ) AS x,(" +
                    "	SELECT  sum(a2.nd_abo+a2.mo_pag) as valor   FROM tbm_cabforpag as a " +
                    "   inner join tbm_cabMovInv AS a1 on (a1.co_emp=a.co_emp and a1.co_forpag=a.co_forpag)  " +
                    "	INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                    " inner join tbm_cli as cli on (cli.co_emp=a1.co_Emp and cli.co_cli=a1.co_cli and ( cli.ne_diagra<=0  or cli.ne_diagra = null )) "+  
                    "	WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_tipdoc=1 "+
                    // AND a1.co_cli="+txtCliCod.getText()+"
                    "   AND a1.st_reg NOT IN ('I','E') AND a1.ST_IMP IN ('S')  AND a2.st_reg IN ('A','C') " +
                    " AND ( a1.st_excDocConVenCon='S' OR a1.st_excDocConVenCon is null ) " +       
                    "	AND (a2.nd_porret=0 or a2.nd_porret is null ) and (a2.ne_diacre=0 or a2.ne_diacre is null)" +
                    "	AND (a2.nd_abo+a2.mo_pag) < 0  AND  a.ne_tipforpag=1   " +
                    ") AS y" +
                    ",(" +
                    "    SELECT  ne_nummaxvencon, nd_monmaxvencon FROM tbm_loc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
                    " ) AS z "+
                    ") AS x";
                    
                      
                if(dblDiaGraCli == 0 && intTipForPag2==1){  
                    
                    //System.out.println(">>> isVtaConMon:  "+sSQL );
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    while(rstMonCre.next()){
                        if(rstMonCre.getInt("NumVenConCli")==1){
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
                    " a1.co_tipDoc=a3.co_tipDoc) " +
		    " WHERE a1.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND " +
		    " a1.co_cli=" + txtCliCod.getText()+" AND "+
                    " a1.st_reg IN ('A','R','C','F') AND a3.ne_mod In ('1','3') AND a2.st_reg IN ('A','C') AND " +
		    " (a2.mo_pag+a2.nd_abo)<0  and ( a2.nd_porret = 0 or a2.nd_porret is null )   " +
                    " AND a2.fe_ven+cli.ne_diagra<="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "+
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
                    " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0  and a2.nd_porret <> 0   AND a2.fe_ven+a2.ne_diagra<="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "+
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
                    " AND a2.st_sop='S'   " +
                    //" AND a2.st_entsop='N'" +
                    " AND CASE WHEN (a2.mo_pag+a2.nd_abo) < 0 THEN  a2.st_entsop='N' END "+
                    " AND a1.fe_doc+cli.ne_diagrachqfec <= "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ";
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
        
        
        
	
	public boolean isSolTraAutTot(){
	    boolean blnRes=true;
	    for(int i=0; i<tblDat.getRowCount();i++){
                 if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
		    if(tblDat.getValueAt(i, INT_TBL_TRATOT).toString().equals("true")){ 
		     blnRes=false;
		     break;
	    }}}
	    return blnRes;
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
		
		 System.out.println(" CRECLI =>  "+ dblMonCre  + "   " + dblCxC );
		
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
//                    String sSQL ="select  sum((abs(pag.mo_pag) - pag.nd_abo)) as nd_deuda           " +
//                    "from tbm_pagmovinv as pag, tbm_cabmovinv as cab, tbm_cabtipdoc as tipdoc " +
//                    "where                                                                    " +
//                    " pag.co_emp =   "  + objZafParSis.getCodigoEmpresa() +   "   and   "  +
//                    " pag.co_emp = cab.co_emp and      " +
//                    " pag.co_emp = tipdoc.co_emp and   " +
//                    " pag.co_loc =   "  + objZafParSis.getCodigoLocal()   +   "   and   " +
//                    " pag.co_loc = cab.co_loc                              and   " +
//                    " pag.co_loc = tipdoc.co_loc                           and   " +
//                    " pag.co_tipDoc = cab.co_tipdoc                        and   " +
//                    " pag.co_tipDoc = tipdoc.co_tipdoc                     and   " +
//                    " pag.co_doc     = cab.co_doc                          and   " +
//                    " tipdoc.co_tipdoc  = " + 1                        + " and " +
//                    " pag.nd_porret  = 0  and (abs(pag.mo_pag) - pag.nd_abo) > 0  and   " +
//                    " cab.st_reg in ('C','A','R','F')                      and   " +
//                    " pag.st_reg in ('C','A')                              and   " +
//                    " cab.co_cli = " + int_co_cli  ;
                    
		     String sSQL ="select  sum((abs(pag.mo_pag) - abs(pag.nd_abo))) as nd_deuda  " +
		     " from tbm_pagmovinv as pag " +
		     " inner join tbm_cabmovinv as cab on (cab.co_emp=pag.co_emp and cab.co_loc=pag.co_loc and cab.co_tipdoc=pag.co_tipdoc and cab.co_doc=pag.co_doc )  " +
		     " inner join tbm_cabtipdoc as tipdoc on ( tipdoc.co_emp=cab.co_emp and tipdoc.co_loc=cab.co_loc and tipdoc.co_tipdoc=cab.co_tipdoc ) " +
		     "  where " +
		     " pag.co_emp = "+objZafParSis.getCodigoEmpresa()+"  and  "+ 
                     //" pag.co_loc = "+objZafParSis.getCodigoLocal()+" and "+  
                     " tipdoc.ne_mod  in ('1','3') and "+
                     "(pag.nd_porret  = 0 or pag.nd_porret is null )  and (abs(pag.mo_pag) - abs(pag.nd_abo)) > 0  and   "+
                     " cab.st_reg in ('C','A','R','F')                      and  "+
                     " pag.st_reg in ('C','A')                              and  "+
                     " cab.co_cli = " + int_co_cli  ;
		    
		     System.out.println(" CRECLI =>  "+ sSQL );
		    
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
    

