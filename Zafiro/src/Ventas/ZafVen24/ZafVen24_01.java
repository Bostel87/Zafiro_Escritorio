/*
 * 
 *  Created on 08 de Junio de 2006     txtGuia.set
 */   
            
package Ventas.ZafVen24;  
   
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTableColBut.ZafTableColBut;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco; 
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco2.ZafTblCelEdiTxtVco2;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafTipDoc;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
 /*
 * @author  Javier Ayapata Garate
 */

public class ZafVen24_01 extends javax.swing.JDialog {
    private ZafParSis objZafParSis;
    private Librerias.ZafUtil.ZafCliente_dat objCliente_dat; // Para Obtener la informacion del proveedor
     
    private ZafInvItm objInvItm; 
    private Ventas.ZafVen24.UltDocPrint objUltDocPrint;    // Para trabajar con la informacion de tipo de documento
  
    private final String DIRECCION_REPORTE="C://Zafiro//Reportes_impresos//RptFacVta.jrxml";
    private final String DIRECCION_REPORTE_CAS="C://Zafiro//Reportes_impresos//castek//RptFacVta.jrxml";
    private final String DIRECCION_REPORTE_DIM="C://Zafiro//Reportes_impresos//dimulti//RptFacVta.jrxml";
    private final String DIRECCION_REPORTE2="C://Zafiro//Reportes_impresos//nositol//RptFacVta.jrxml";
    private final String DIRECCION_REPORTE_COSENCO="C://Zafiro//Reportes_impresos//cosenco//RptFacVta.jrxml";
    private ZafObtConCen  objObtConCen;
    private ZafAsiDia objAsiDia; // Asiento de Diario
    private ZafCtaCtb_dat objCtaCta_Dat;  //Datos de Cuentas contables
    private Librerias.ZafUtil.UltDocPrint  objUltDocPrint2;  // Para trabajar con la informacion de tipo de documento  
   
    private int intCodMnu;
    private ZafTipDoc objTipDoc;
    private ZafTblMod objTblMod, objTblModPag;
    private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private boolean blnChangeData=false;
    private String strBeforeValue,strAfterValue;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;   //Editor: JButton en celda.
    private ZafTblPopMnu ZafTblPopMn;  
    private String strCodCli, strDesLarCli;   
    private String strCodVen, strDesLarVen;   
    private Connection conCot, con_remota=null, CONN_GLO=null;      //Coneccion a la base donde se encuentra la cotizacion
   
    private Statement stmCotDet2;    //Statement para la cotizacion 
    private ResultSet rstCotCab2=null;    //Resultset que tendra los datos de la cabecera
    private ResultSet rstCotDet2;    //Resultset que tendra los datos del Detalle
    private boolean blnLimpiar = true; //Limpiar o no en el metodo clnTextos
    private Ventas.ZafVen24.ZafCiudad_dat objCiudad_dat;     // Para Obtener la informacion de una ciudad    
    private String strFecSisBase= "";

    private ZafUtil objUti;
    private JDialog jfrThis; 
    private Connection conCab;  //Coneccion a la base donde se encuentra la cotizacion
    private Statement stmCab;   //Statement para la cotizacion 
    private ResultSet rstCab;//Resultset que tendra los datos de la cabecera
    private String sSQL,strSQL, strFiltro;//EL filtro de la Consulta actual
    private double dblPorIva,dblPorIvaaux,dblPorIvaCon ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    private double dblTotalCot, dblIvaCot;
    private int intNumDec= 2 ; //Numero de decimales a presentar
    private LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    
    private Vector vecForPag; //Vector que contiene el codigo del pago
    private Vector vecTransporte; //Vector que contiene el codigo del Transporte
    private String strAux,strstReg_glo="";
    
    private int  INTCODREGCEN=0;
    private double dblSubtotalCot; 
  
    private Statement stmCot;   //Statement para la cotizacion 
    
    private ZafVenCon objVenCon; //*****************
    private ZafVenCon objVenCon2; //*****************  
    private ZafVenCon objVenConCli; //*****************  
    private ZafVenCon objVenConVen; //*****************  
    private ZafVenCon objVenConTipdoc; //*****************   

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
    final int INT_TBL_STKBOD   = 8 ; //16            //Stock actual de la bodega
    final int INT_TBL_ULTCOS   = 9 ;  //17          //Ultimo costo
    final int INT_TBL_PREUNI   = 10 ; //8           //Precio de Venta
    final int INT_TBL_UTILIDAD = 11 ;           //Utilidad 
    final int INT_TBL_PREUNI2  = 12 ;           //Precio Unitario
    final int INT_TBL_PORDES   = 13 ; //11           //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 14;           //Boolean Iva
    final int INT_TBL_TOTAL    = 15;           //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 16;
    final int INT_TBL_ESTADO   = 17;
    final int INT_TBL_IVATXT   = 18;
    final int INT_TBL_BLNPRE   = 19;            //Columna que contiene verdadero si es problema de precio
    final int INT_TBL_CANORI   = 20;    //Codigo del bodega de una o/c cargada
    final int INT_TBL_BODORI   = 21;
    final int INT_TBL_CODORI   = 22;
    final int INT_TBL_ITMALT2  = 23;    
    final int INT_TBL_ITMSER   = 24;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_ITMTER   = 25;            //Columna que contiene "S" (el item es terminal L ) "N"  no corresponde a la terminal L
    
    final int INT_TBL_CODEMPREL = 26;
    final int INT_TBL_CODLOCREL = 27;
    final int INT_TBL_CODTIPREL = 28;
    final int INT_TBL_CODDOCREL = 29;
    final int INT_TBL_CODREGREL = 30;
    
    //************  
    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODITM   = 0;
    final int INT_ARR_CODBOD   = 1;
    final int INT_ARR_CANMOV   = 2;
    final int INT_ARR_ITMORI   = 3;
    final int INT_ARR_BODORI   = 4;
    final int INT_ARR_CANORI   = 5;
    //*****************************

   final int INT_TBL_PAGLIN=0; //Linea de pago
   final int INT_TBL_PAGCRE=1; // Dias de credito
   final int INT_TBL_PAGFEC=2; // Fecha de vencimiento
   final int INT_TBL_PAGRET=3; // porcentaje de retencion
   final int INT_TBL_PAGMON=4; // monto de pago
   final int INT_TBL_PAGGRA=5; // dias de gracias
   final int INT_TBL_PAGCOD=6; // codigo de retencion
   final int INT_TBL_PAGSOP=7; // soporte de cheque    
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
     int intCodCliGrp=0;
     String strFecSis,strFecSis2;  
          
     public boolean blnAcepta = false;
     public boolean blnEst=false;
     
     public String strNumFac="";
     public String strCodDocFac="";       
     
    private final String strVersion = " v. 6.0 ";  
    private final int intarreglonum[] = new int[10]; 
    private final int intarreglodia[] = new int[10]; 
    private final int intCanArr[]= new int[1];
    private String strTipPer_glo = ""; 
    private String strTipPer_emp="";
      
    private String GLO_strnomEmp="";
    private String GLO_strdirEmp="";
    
    private String STR_ESTREG="";
    private String stIvaVen="S";
    private String GLO_strArreItm="";
    private int Glo_intCodSec=0;
      
    private ZafDatePicker txtFecDoc;

    private JTextField txtCodForPag = new JTextField();
    
    private String strCodDoc="";
    private String strCodTipDoc="";

     private int intTipForPag2=0;

    private double dblPes=0;
    private double dblPes2=0;

    private JTextField txtcli_identificacion = new JTextField();
    private JTextField txtcli_telefono = new JTextField();
    private JTextField txtcli_ciudad = new JTextField();    
    
    private String strarreglosop[] = new String[10]; 
    private String strCodTipPerCli; 

    private int intCodMotBien=0;  //  se almacena el codigo del motivo del codumento para las retenciones para un bien
    private int intCodMotServ=0;  //  se almacena el codigo del motivo del codumento para las retenciones para un servicio
    private int intCodMotTran=0;  //  se almacena el codigo del motivo del codumento para las retenciones para un transporte
    
    private int intCodTipPerEmp;
    private int intLocalFac=0;
    
    public StringBuffer stbDocDevEmp=new StringBuffer();
    private final String strTit="Mensaje del Sistema Zafiro";

    private ZafVenCon objVenConForPag; 
    private ZafVenCon objVenConTarCre;
    private ZafVenCon objVenConTipCre;
    private ZafVenCon objVenConMesCre;
    private ZafVenCon objVenConEmisor;
    private String strCodForPag="", strDesLarForPag="", strCodTarCre="", strNomTarCre="", strCodTipCre="", strNomTipCre="", strCodEmisor="", strNomEmisor="", strCodMesCre="", strNomMesCre="";
    private double dblPorDesTarCre=0, Glo_dlbMaxDes=0;
    private JTextField txtCodRedTarCre = new JTextField();
    private JTextField txtTipCre = new JTextField();    
    private JTextField txtPorComTarCre = new JTextField();
    private JTextField txtPorDesVenTarCre = new JTextField();    
    
    /** Creates new form zafCotCom */  
    public ZafVen24_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis objParsis, String CodDoc, String CodTipdoc, java.sql.Connection con_local, java.sql.Connection con_remoto, int intLocal) {
      super(parent, modal);
      
        try{  
        this.objZafParSis = objParsis;
        jfrThis = this;

        strCodDoc=CodDoc;
        strCodTipDoc=CodTipdoc;  
       
        con_remota = con_remoto;
        CONN_GLO = con_local;
        
        intLocalFac=intLocal;
        
        objUti = new ZafUtil();
       // objCtaCtb  = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis);
        this.setTitle(objZafParSis.getNombreMenu()+strVersion);
        
             objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
             intCodMnu = 14;
             objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);        
            
        initComponents();
        
         objUltDocPrint = new Ventas.ZafVen24.UltDocPrint(objZafParSis);
         objCiudad_dat    = new ZafCiudad_dat(this.objZafParSis);
         objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objZafParSis);        
         
       strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
        
        strFecSis2 = objUti.formatearFecha(strFecSis , "yyyy-MM-dd" ,  "dd/MM/yyyy");
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText(strFecSis2);

        strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

        
        //********************** 
          
        lblfecha.setText( strFecSis2 );
        
       /**/ lblfecha.setVisible(false);
       /**/ pan2.add(txtFecDoc);
       /**/ txtFecDoc.setBounds(550, 10, 92, 20);
        
       
        
        //Nombres de los tabs
        tabCotCom.setTitleAt(0,"General");
        tabCotCom.setTitleAt(1,"Forma de Pago");
        tabCotCom.setTitleAt(2,"Otros");
        tabCotCom.setTitleAt(3,"Asiento de Diario");
        
       // objTooBar = new mitoolbar(this);
        
        vecForPag = new java.util.Vector();
        vecTransporte = new java.util.Vector();
         
        dblPorIvaaux = getIva(CONN_GLO);
       // this.getContentPane().add(objTooBar,"South");
        panAsiento.add(objAsiDia,java.awt.BorderLayout.CENTER);
        pack();
        
        
        objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                objZafParSis.getClaveBaseDatos(), "SELECT co_reg, tx_deslar FROM tbm_var where co_grp = 7 order by co_reg", cboMotivoTransporte, vecTransporte);      
         
       
        
        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
        /*
         * Listener para detectar cambios en combo de pagos
         */
       
        
        
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
 
        objUti.verDecimalesEnTabla(tblPag,4, intNumDec);
        addListenerCambio();
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblPag,0);
        tblPag.getModel().addTableModelListener(new LisCambioTbl());
         
        objInvItm=new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
        
          cargarTipEmp(CONN_GLO);
          FormaRetencion(CONN_GLO);
          intCodBodPre =  getBodPrede(CONN_GLO);
         
        
           this.setSize(700, 450);
           
            objTipDoc.cargarTipoDoc( Integer.parseInt(strCodTipDoc) );
            txtTipDocCod.setText(strCodTipDoc);
            txtTipDocNom.setText(objTipDoc.gettx_deslar());
            txtTipDocAli.setText(objTipDoc.gettx_descor());        
       
        objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));                   
           
        txtDoc.setText(strCodDoc);
           
         objUti.desactivarCom(this);
          
         butacep.setEnabled(true);
         butCan.setEnabled(true);
     
         cboMotivoTransporte.setEnabled(true);
         cboMotivoTransporte.setSelectedIndex(0);
           
         txtCliCod.setEditable(false);  //*****
         txtCliNom.setEditable(false);  //*****
         butCliCon.setEnabled(true);  //*****
         txaObs1.setEnabled(true);
         txaObs1.setEditable(true);
         butCliCon1.setEnabled(true);

      }catch (Exception e){
          objUti.mostrarMsgErr_F1(this, e);
      }         
    }                  
    
    public boolean consultar2(int CodEmp,int CodLoc) { 
        return _consultar(FilSql()); 
    }
         
     public void mostrarMsg(String strMsg)
    {
        //JOptionPane oppMsg=new JOptionPane();
        /*String strTit;
        strTit="Mensaje del sistema Zafiro";*/
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.OK_OPTION);
    } 
    
    //**************************** CLASE PARA DARLE COLOR AL FONDO Y COLOR AL TEXTO CUANO HAY PROBLEMAS DE PRECIO *****////////
    private class RenderDecimales extends JLabel implements TableCellRenderer {
        int intNumDecimales = 0;
        public RenderDecimales(int intNumDecimales) {
            this.intNumDecimales=intNumDecimales ;
            setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            setOpaque(true);
            setBackground(new java.awt.Color(255, 255, 255));
     }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
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
            
           // this.setOpaque(false);
            if(tblDat.getValueAt(row, INT_TBL_BLNPRE)!=null){
               if((tblDat.getValueAt(row, INT_TBL_BLNPRE).toString().equals("true"))){
                    this.setOpaque(true);
                    setBackground(new java.awt.Color(0, 0, 0));
                    setForeground(new java.awt.Color(255,255,255));
                    setFont(new java.awt.Font("MS Sans Serif", 1, 18));                   
                }
            }
            return this;
        }
      }      
    
  //**********************************************************************************************
     
      
     private boolean FormaRetencion(java.sql.Connection conTmp){
         boolean blnRes=false; 
         java.sql.Statement stmTmp;
         java.sql.ResultSet rst;
         try
          {
           if (conTmp!=null)
           {
              stmTmp = conTmp.createStatement();
              String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND tx_tipmot  in ('B','S','T')  ";
              rst=stmTmp.executeQuery(sql);
              while(rst.next()){
                   if(rst.getString("tx_tipmot").equals("B")) intCodMotBien= rst.getInt("co_mot");
                    else if(rst.getString("tx_tipmot").equals("S")) intCodMotServ= rst.getInt("co_mot");
                    else if(rst.getString("tx_tipmot").equals("T")) intCodMotTran= rst.getInt("co_mot");
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
    
    
     
      private void cargarTipEmp(java.sql.Connection conTmp){
       Statement stmTipEmp;
       ResultSet rstEmp;
       String sSql;
         try{
              if (conTmp!=null){  
                       stmTipEmp=conTmp.createStatement();
                    
                    sSql="select b.co_tipper,  b.tx_descor from tbm_emp as a " +
                    " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
                    " where a.co_emp="+objZafParSis.getCodigoEmpresa();
                 
                    rstEmp = stmTipEmp.executeQuery(sSql);
                    if(rstEmp.next()){
                        strTipPer_emp=rstEmp.getString("tx_descor");
                        intCodTipPerEmp = rstEmp.getInt("co_tipper");
                    } rstEmp.close();
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

      
    
   
    private int getBodPrede(java.sql.Connection conTmp){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia            
        int intBodPre = 0;
        String sSQLBod;
        try{
            if (conTmp!=null){ 
                stmInv=conTmp.createStatement();

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
     
     
    
     public void Configura_ventana_consulta(){
        configurarVenConProducto();
        configurarVenConClientes();   //**********************
        configurarVenConVendedor();  //************************
        configurarVenConTipDoc();
        
        /** TARJETA DE CREDITO. */
        configurarVentanaConsultaFormaPago();
        configurarVentanaConsultaTarjetaCredito();
        configurarVentanaConsultaTipoCredito();
        configurarVentanaConsultaEmisor();
        configurarVentanaConsultaMesesCredito();
        deshabilitarCamposTarCre();                
          
    }
    
     
       private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Descripción");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");
            
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
                  Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                          " where   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                          " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                          " b.co_mnu = 14";
                  
            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
       
       
     
       private boolean configurarVenConVendedor()
      {
        boolean blnRes=true;
        try
        {
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
             strSQL+="select a.co_usr,a.tx_nom from tbm_usr as a order by a.tx_nom"; 
           
            objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
          
    private boolean configurarVenConClientes()
    {
        boolean blnRes=true;
        try
        {
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
            arlCam.add("a.st_ivaven");
             
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
            arlAli.add("Iva.Ven");
             
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("40");
            arlAncCol.add("20");
            arlAncCol.add("20");
             
            //Armar la sentencia SQL.
             String  strSQL="";
           
            strSQL+="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper, st_ivaven " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper, a.st_ivaven " +
            " FROM tbm_cli as a  " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " WHERE a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.st_reg='A'  and a.st_cli='S' order by a.tx_nom  " +
            ") AS a";
             
            int intColOcu[]=new int[7];
            intColOcu[0]=6;
            intColOcu[1]=7;
            intColOcu[2]=8;
            intColOcu[3]=9;
            intColOcu[4]=10;
            intColOcu[5]=11;
            intColOcu[6]=12;  
            
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    private boolean configurarVenConProducto()
    {
        boolean blnRes=true;
        try
        {
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
             
            ArrayList arlAncCol=new ArrayList();
            
                
           String strAux = ",CASE WHEN (" +
           " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
           " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
           " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
           " ))) THEN 'S' ELSE 'N' END  as isterL";
            
                 //Armar la sentencia SQL.   a7.nd_stkTot,
             String Str_Sql="";
            if(objZafParSis.getCodigoEmpresa()==2 || objZafParSis.getCodigoEmpresa()==4){
                  Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser";
                  Str_Sql+=strAux;
                  Str_Sql+=" , a7.nd_stkActcen";
                  Str_Sql+=" FROM (";
                  Str_Sql+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser , d3.nd_stkActcen";
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
                  "" +
                  ", (";
                  Str_Sql+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser";
                  Str_Sql+=" FROM tbm_inv AS c1";
                  Str_Sql+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                  Str_Sql+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                  Str_Sql+=" WHERE c1.st_reg not in ('T') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
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
                
               int intColOcu[] =new int[4];
                  intColOcu[0]=8;  
                  intColOcu[1]=9;
                  intColOcu[2]=10;
                  intColOcu[3]=11;
                 objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
           
                  
            } else { 
              
                  Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser";
                  Str_Sql+=strAux;
                  Str_Sql+=", 0 as nd_stkActcen ";
                  Str_Sql+=" FROM (";
                  Str_Sql+=" SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen,a2.tx_descor,a2.tx_codAlt2,a2.st_ser";
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
                  Str_Sql+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser";
                  Str_Sql+=" FROM tbm_inv AS c1";
                  Str_Sql+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                  Str_Sql+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                  Str_Sql+=" WHERE c1.st_reg not in ('T') and  c2.co_emp=" + objZafParSis.getCodigoEmpresa();
                  Str_Sql+=" ) AS a2";
                  Str_Sql+=" WHERE a1.co_itmMae=a2.co_itmMae";
                  Str_Sql+=" ) AS a7  order by a7.tx_codalt";
                
                arlAncCol.add("80");
                arlAncCol.add("80");
                arlAncCol.add("220");
                arlAncCol.add("55");
                arlAncCol.add("50");
                arlAncCol.add("55");
                arlAncCol.add("30");
                arlAncCol.add("33");
                arlAncCol.add("33"); 
                arlAncCol.add("47"); 
                arlAncCol.add("40");
                  
               int intColOcu[]=new int[5];
                  intColOcu[0]=5;  
                  intColOcu[1]=8;
                  intColOcu[2]=9;
                  intColOcu[3]=10;  
                  intColOcu[4]=11;  
                 objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
           
            }
              
                   
             //Ocultar columnas.
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenCon2.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(7, javax.swing.JLabel.CENTER); 
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    private boolean configurarVentanaConsultaFormaPago(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_forpag");
            arlCam.add("tx_des");
            arlCam.add("ne_tipforpag");
            arlCam.add("nd_pes");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.For.Pag");
            arlAli.add("Descripcion");
            arlAli.add("Tip.Pag");
            arlAli.add("Tip.Pes");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("260");
            arlAncCol.add("0");
            arlAncCol.add("0");
            
            int intColOcu[] =new int[2];
            intColOcu[0]=3;
            intColOcu[1]=4;

            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="SELECT co_forpag, tx_des, ne_tipforpag, nd_pes FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " and st_reg not in ('I','E') order by co_forpag";
            
            objVenConForPag=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    private boolean mostrarVenConFormaPago(int intTipBus){
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConForPag.setCampoBusqueda(0);
                    objVenConForPag.setVisible(true);
                    if (objVenConForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodForPag.setText(objVenConForPag.getValueAt(1));
                        txtNomForPag.setText(objVenConForPag.getValueAt(2));
                        txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                        intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                        //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                        dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                        if (intTipForPag2==4){
                            habilitarCamposTarCre();
                        }else{
                            deshabilitarCamposTarCre();
                            calculaDescuento();
                            CalculoPago();                            
                        }                                   
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConForPag.buscar("co_forpag", txtCodigoFormaPago.getText())) {
                        txtCodForPag.setText(objVenConForPag.getValueAt(1));
                        txtNomForPag.setText(objVenConForPag.getValueAt(2));
                        txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                        intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                        //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                        dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                        if (intTipForPag2==4){
                            habilitarCamposTarCre();
                        }else{
                            deshabilitarCamposTarCre(); 
                            calculaDescuento(); 
                            CalculoPago();            
                        }                                   
                    }else{
                        objVenConForPag.setCampoBusqueda(0);
                        objVenConForPag.setCriterio1(11);
                        objVenConForPag.cargarDatos();
                        objVenConForPag.setVisible(true);
                        if (objVenConForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodForPag.setText(objVenConForPag.getValueAt(1) );
                            txtNomForPag.setText(objVenConForPag.getValueAt(2) );
                            txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1) );
                            intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                            //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                            dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                            if (intTipForPag2==4){
                                habilitarCamposTarCre();
                            }else{
                                deshabilitarCamposTarCre(); 
                                calculaDescuento(); 
                                CalculoPago();            
                            }                                   
                        }else 
                            txtCodForPag.setText(strCodForPag);
                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (objVenConForPag.buscar("tx_des", txtNomForPag.getText())) {
                        txtCodForPag.setText(objVenConForPag.getValueAt(1));
                        txtNomForPag.setText(objVenConForPag.getValueAt(2));
                        txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                        intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                        //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                        dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                        if (intTipForPag2==4){
                            habilitarCamposTarCre();
                        }else{
                            deshabilitarCamposTarCre(); 
                            calculaDescuento(); 
                            CalculoPago();            
                        }                                   
                    }else {
                        objVenConForPag.setCampoBusqueda(1);
                        objVenConForPag.setCriterio1(11);
                        objVenConForPag.cargarDatos();
                        objVenConForPag.setVisible(true);
                        if (objVenConForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodForPag.setText(objVenConForPag.getValueAt(1));
                            txtNomForPag.setText(objVenConForPag.getValueAt(2));
                            txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                            intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                            //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                            dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                            if (intTipForPag2==4){
                                habilitarCamposTarCre();
                            }else{
                                deshabilitarCamposTarCre(); 
                                calculaDescuento(); 
                                CalculoPago();            
                            }                                   
                        }else 
                            txtNomForPag.setText(strDesLarForPag);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVentanaConsultaTarjetaCredito(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_tarcre");
            arlCam.add("tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Tar.Cre");
            arlAli.add("Tar.Cre");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("260");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select co_tarcre, tx_deslar from tbm_tarCre where st_reg != 'I' ";
            
            objVenConTarCre=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    private boolean mostrarVenConTarjetaCredito(int intTipBus){
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConTarCre.setCampoBusqueda(0);
                    objVenConTarCre.setVisible(true);
                    if (objVenConTarCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodTarCre.setText(objVenConTarCre.getValueAt(1));
                        txtNomTarCre.setText(objVenConTarCre.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConTarCre.buscar("co_tarcre", txtCodTarCre.getText())) {
                        txtCodTarCre.setText(objVenConTarCre.getValueAt(1));
                        txtNomTarCre.setText(objVenConTarCre.getValueAt(2));
                    }else{
                        objVenConTarCre.setCampoBusqueda(0);
                        objVenConTarCre.setCriterio1(11);
                        objVenConTarCre.cargarDatos();
                        objVenConTarCre.setVisible(true);
                        if (objVenConTarCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodTarCre.setText(objVenConTarCre.getValueAt(1));
                            txtNomTarCre.setText(objVenConTarCre.getValueAt(2));
                        }else 
                            txtCodTarCre.setText(strCodTarCre);                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (objVenConTarCre.buscar("tx_deslar", txtNomTarCre.getText())) {
                        txtCodTarCre.setText(objVenConTarCre.getValueAt(1));
                        txtNomTarCre.setText(objVenConTarCre.getValueAt(2));
                    }else {
                        objVenConTarCre.setCampoBusqueda(1);
                        objVenConTarCre.setCriterio1(11);
                        objVenConTarCre.cargarDatos();
                        objVenConTarCre.setVisible(true);
                        if (objVenConTarCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodTarCre.setText(objVenConTarCre.getValueAt(1));
                            txtNomTarCre.setText(objVenConTarCre.getValueAt(2));
                        }else 
                            txtNomTarCre.setText(strNomTarCre);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVentanaConsultaTipoCredito(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_redtarcre");
            arlCam.add("redtarcre");
            arlCam.add("co_tipcre");
            arlCam.add("destipcre");
            arlCam.add("tx_tipcre");
            arlCam.add("ne_mesgra");
            arlCam.add("nd_pordesven");
            arlCam.add("nd_porcom");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Red.Tar.Cre");
            arlAli.add("Red.Tar.Cre");
            arlAli.add("Cod.Tip.Cre");
            arlAli.add("Nom.Tip.Cre");
            arlAli.add("Tip.Cre");
            arlAli.add("Mes.Gra");
            arlAli.add("% Des");
            arlAli.add("% Des.Com");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("100");
            arlAncCol.add("70");
            arlAncCol.add("150");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            
            int intColOcu[] =new int[2];
            intColOcu[0]=5;
            intColOcu[1]=8;

            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL=" select a.co_redtarcre, a.tx_deslar as redtarcre, b.co_tipcre, b.tx_deslar as destipcre, b.tx_tipcre, b.ne_mesgra, b.nd_pordesven, b.nd_porcom "
                    + " from tbm_redTarCre as a "
                    + " inner join tbm_tipCreRedTarCre as b on (b.co_redtarcre=a.co_redtarcre) "
                    + " where b.co_emp= " + objZafParSis.getCodigoEmpresa() + " "
                    + " and a.st_reg != 'I' "
                    + " and b.st_reg != 'I'  " 
                    + " order by b.co_emp, b.co_tipcre ";
            
            objVenConTipCre=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    private boolean mostrarVenConTipoCredito(int intTipBus){
        boolean blnRes=true;
        String strTipCredAnt=new String("");
        strTipCredAnt=txtCodTipCre.getText();
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConTipCre.setCampoBusqueda(0);
                    objVenConTipCre.setVisible(true);
                    if (objVenConTipCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodRedTarCre.setText(objVenConTipCre.getValueAt(1));
                        txtCodTipCre.setText(objVenConTipCre.getValueAt(3));
                        txtNomTipCre.setText(objVenConTipCre.getValueAt(4));
                        txtTipCre.setText(objVenConTipCre.getValueAt(5));
                        txtMesGra.setText(objVenConTipCre.getValueAt(6));
                        dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTipCre.getValueAt(7) ), 2 );
                        txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) ); 
                        txtPorComTarCre.setText(objVenConTipCre.getValueAt(8) );
                        /*txtCodMesCre.setText(""); b.co_emp= " + objZafP
                        txtNomMesCre.setText(""); b.co_emp= " + objZafP b.co_emp= " + objZafP
                        txtCodEmiTar.setText("");
                        txtNomEmiTar.setText("");*/
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConTipCre.buscar("co_tipcre", txtCodTipCre.getText())) {
                        txtCodRedTarCre.setText(objVenConTipCre.getValueAt(1));
                        txtCodTipCre.setText(objVenConTipCre.getValueAt(3));
                        txtNomTipCre.setText(objVenConTipCre.getValueAt(4));
                        txtTipCre.setText(objVenConTipCre.getValueAt(5));
                        txtMesGra.setText(objVenConTipCre.getValueAt(6));
                        dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTipCre.getValueAt(7) ), 2 );
                        txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) );
                        txtPorComTarCre.setText(objVenConTipCre.getValueAt(8) );
                        /*txtCodMesCre.setText("");
                        txtNomMesCre.setText("");
                        txtCodEmiTar.setText("");
                        txtNomEmiTar.setText("");*/
                    }else{
                        objVenConTipCre.setCampoBusqueda(2);
                        objVenConTipCre.setCriterio1(11);
                        objVenConTipCre.cargarDatos();
                        objVenConTipCre.setVisible(true);
                        if (objVenConTipCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodRedTarCre.setText(objVenConTipCre.getValueAt(1));
                            txtCodTipCre.setText(objVenConTipCre.getValueAt(3));
                            txtNomTipCre.setText(objVenConTipCre.getValueAt(4));
                            txtTipCre.setText(objVenConTipCre.getValueAt(5));
                            txtMesGra.setText(objVenConTipCre.getValueAt(6));
                            dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTipCre.getValueAt(7) ), 2 );
                            txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) );
                            txtPorComTarCre.setText(objVenConTipCre.getValueAt(8) );
                            /*txtCodMesCre.setText("");
                            txtNomMesCre.setText("");
                            txtCodEmiTar.setText("");
                            txtNomEmiTar.setText("");*/
                        }else 
                            txtCodTipCre.setText(strCodTipCre);                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (objVenConTipCre.buscar("destipcre", txtNomTipCre.getText())) {
                        txtCodRedTarCre.setText(objVenConTipCre.getValueAt(1));
                        txtCodTipCre.setText(objVenConTipCre.getValueAt(3));
                        txtNomTipCre.setText(objVenConTipCre.getValueAt(4));
                        txtTipCre.setText(objVenConTipCre.getValueAt(5));
                        txtMesGra.setText(objVenConTipCre.getValueAt(6));
                        dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTipCre.getValueAt(7) ), 2 );
                        txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) );
                        txtPorComTarCre.setText(objVenConTipCre.getValueAt(8) );
                        /*txtCodMesCre.setText("");
                        txtNomMesCre.setText("");
                        txtCodEmiTar.setText("");
                        txtNomEmiTar.setText("");*/
                    }else {
                        objVenConTipCre.setCampoBusqueda(3);
                        objVenConTipCre.setCriterio1(11);
                        objVenConTipCre.cargarDatos();
                        objVenConTipCre.setVisible(true);
                        if (objVenConTipCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodRedTarCre.setText(objVenConTipCre.getValueAt(1));
                            txtCodTipCre.setText(objVenConTipCre.getValueAt(3));
                            txtNomTipCre.setText(objVenConTipCre.getValueAt(4));
                            txtTipCre.setText(objVenConTipCre.getValueAt(5));
                            txtMesGra.setText(objVenConTipCre.getValueAt(6));
                            dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTipCre.getValueAt(7) ), 2 );
                            txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) );
                            txtPorComTarCre.setText(objVenConTipCre.getValueAt(8) );
                            /*txtCodMesCre.setText("");
                            txtNomMesCre.setText("");
                            txtCodEmiTar.setText("");
                            txtNomEmiTar.setText("");*/
                        }else 
                            txtNomTipCre.setText(strNomTipCre);                        
                    }
                break;
            }
            //Glo_dlbMaxDes=dblPorDesTarCre;
            if (!strTipCredAnt.equals(txtCodTipCre.getText())){
                txtCodMesCre.setText("");
                txtNomMesCre.setText("");            
                txtCodEmiTar.setText("");
                txtNomEmiTar.setText("");
            }            
            
            calculaDescuento();
            
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVentanaConsultaEmisor(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_emitarcre");
            arlCam.add("tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Emi.Tar.Cre");
            arlAli.add("Emi.Tar.Cre");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("200");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select co_emitarcre, tx_deslar " +
                              " from( " +
                              " select co_reg as co_emitarcre, tx_deslar " +
                              " from tbm_var " + 
                              " where co_grp=8 and st_reg != 'I' " + 
                              " union all " + 
                              " select a.co_emitarcre, b.tx_deslar " + 
                              " from tbr_emiRedTarCre as a " +
                              " inner join tbm_var as b on (a.co_emitarcre=b.co_reg) " +
                              " where a.co_redtarcre in (1, 2) " +
                              " and b.st_reg != 'I' " + 
                              " ) as x " +
                              " group by co_emitarcre, tx_deslar " + 
                              " order by 1 ";
            
            objVenConEmisor=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    private boolean mostrarVenConEmisor(int intTipBus){
        boolean blnRes=true;
        String strSql="";
        try {
            if (txtCodTipCre.getText().equals("")){
                this.mostrarMsg("Debe escoger el tipo de credito.");
                return false;
            /*}else{
                if (txtTipCre.getText().equals("C")){
                    strSql=" select co_reg as co_emitarcre, tx_deslar from tbm_var where co_grp=8 and st_reg != 'I' order by 1 ";
                }else{
                    strSql=" select a.co_emitarcre, b.tx_deslar "
                    + " from tbr_EmiRedTarCre as a "
                    + " inner join tbm_var as b on (a.co_emitarcre=b.co_reg) "
                    + " where a.co_redtarcre = " + txtCodRedTarCre.getText() + " "
                    + " and b.st_reg != 'I' "
                    + " order by 1 ";
                }*/
            }
            
            /*objVenConEmisor.setSentenciaSQL(strSql);
            objVenConEmisor.cargarDatos(); */
            
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConEmisor.setCampoBusqueda(0);
                    objVenConEmisor.setVisible(true);
                    if (objVenConEmisor.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodEmiTar.setText(objVenConEmisor.getValueAt(1));
                        txtNomEmiTar.setText(objVenConEmisor.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConEmisor.buscar("co_emitarcre", txtCodEmiTar.getText())) {
                        txtCodEmiTar.setText(objVenConEmisor.getValueAt(1));
                        txtNomEmiTar.setText(objVenConEmisor.getValueAt(2));
                    }else{
                        objVenConEmisor.setCampoBusqueda(0);
                        objVenConEmisor.setCriterio1(11);
                        objVenConEmisor.cargarDatos();
                        objVenConEmisor.setVisible(true);
                        if (objVenConEmisor.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodEmiTar.setText(objVenConEmisor.getValueAt(1));
                            txtNomEmiTar.setText(objVenConEmisor.getValueAt(2));
                        }else 
                            txtCodEmiTar.setText(strCodEmisor);                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (objVenConEmisor.buscar("tx_deslar", txtNomEmiTar.getText())) {
                        txtCodEmiTar.setText(objVenConEmisor.getValueAt(1));
                        txtNomEmiTar.setText(objVenConEmisor.getValueAt(2));
                    }else {
                        objVenConEmisor.setCampoBusqueda(1);
                        objVenConEmisor.setCriterio1(11);
                        objVenConEmisor.cargarDatos();
                        objVenConEmisor.setVisible(true);
                        if (objVenConEmisor.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodEmiTar.setText(objVenConEmisor.getValueAt(1));
                            txtNomEmiTar.setText(objVenConEmisor.getValueAt(2));
                        }else 
                            txtNomEmiTar.setText(strNomEmisor);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVentanaConsultaMesesCredito(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_mescre");
            arlCam.add("tx_desmescre");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Mes.Cre");
            arlAli.add("Des.Mes.Cre");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("100");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select 3 as co_mescre, '3 Meses' :: text as tx_desmescre union all "
                    + " select 6 as co_mescre, '6 Meses' :: text as tx_desmescre union all "
                    + " select 9 as co_mescre, '9 Meses' :: text as tx_desmescre union all "
                    + " select 12 as co_mescre, '12 Meses' :: text as tx_desmescre union all "
                    + " select 15 as co_mescre, '15 Meses' :: text as tx_desmescre union all "
                    + " select 18 as co_mescre, '18 Meses' :: text as tx_desmescre union all "
                    + " select 24 as co_mescre, '24 Meses' :: text as tx_desmescre union all "
                    + " select 36 as co_mescre, '36 Meses' :: text as tx_desmescre ";
            
            objVenConMesCre=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    private boolean mostrarVenConMesesCredito(int intTipBus){
        boolean blnRes=true;
        try {
            
            if (txtCodTipCre.getText().equals("")){
                this.mostrarMsg("Debe escoger el tipo de credito. ");
                return false;
            }else {
                if (txtTipCre.getText().equals("C")){
                    this.mostrarMsg("Credito Corriente no aplica a meses de credito. ");
                    return false;
                }                                
            }
            
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConMesCre.setCampoBusqueda(0);
                    objVenConMesCre.setVisible(true);
                    if (objVenConMesCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodMesCre.setText(objVenConMesCre.getValueAt(1));
                        txtNomMesCre.setText(objVenConMesCre.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConMesCre.buscar("co_mescre", txtCodMesCre.getText())) {
                        txtCodMesCre.setText(objVenConMesCre.getValueAt(1));
                        txtNomMesCre.setText(objVenConMesCre.getValueAt(2));
                    }else{
                        objVenConMesCre.setCampoBusqueda(0);
                        objVenConMesCre.setCriterio1(11);
                        objVenConMesCre.cargarDatos();
                        objVenConMesCre.setVisible(true);
                        if (objVenConMesCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodMesCre.setText(objVenConMesCre.getValueAt(1));
                            txtNomMesCre.setText(objVenConMesCre.getValueAt(2));
                        }else 
                            txtCodMesCre.setText(strCodMesCre);                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (objVenConMesCre.buscar("tx_desmescre", txtNomMesCre.getText())) {
                        txtCodMesCre.setText(objVenConMesCre.getValueAt(1));
                        txtNomMesCre.setText(objVenConMesCre.getValueAt(2));
                    }else {
                        objVenConMesCre.setCampoBusqueda(1);
                        objVenConMesCre.setCriterio1(11);
                        objVenConMesCre.cargarDatos();
                        objVenConMesCre.setVisible(true);
                        if (objVenConMesCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodMesCre.setText(objVenConMesCre.getValueAt(1));
                            txtNomMesCre.setText(objVenConMesCre.getValueAt(2));
                        }else 
                            txtNomMesCre.setText(strNomMesCre);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void habilitarCamposTarCre(){
        this.txtCodTarCre.setEnabled(true);
        this.txtNomTarCre.setEnabled(true);
        this.butConTarCre.setEnabled(true);
        
        this.txtCodTipCre.setEnabled(true);
        this.txtNomTipCre.setEnabled(true);
        this.butConTipCre.setEnabled(true);
        
        this.txtCodEmiTar.setEnabled(true);
        this.txtNomEmiTar.setEnabled(true);
        this.butConEmiTar.setEnabled(true);
        
        this.txtCodMesCre.setEnabled(true);
        this.txtNomMesCre.setEnabled(true);
        this.butConMesCre.setEnabled(true);
        
        //this.txtCodRedTarCre.setText("");
        //this.txtTipCre.setText("");
        //this.txtPorComTarCre.setText("");
        //this.txtPorDesVenTarCre.setText("");
        //this.txtMesGra.setEnabled(true);        
    }
    
    private void deshabilitarCamposTarCre(){
        this.txtCodTarCre.setEnabled(false); txtCodTarCre.setText("");
        this.txtNomTarCre.setEnabled(false); txtNomTarCre.setText("");
        this.butConTarCre.setEnabled(false);
        
        this.txtCodTipCre.setEnabled(false); txtCodTipCre.setText("");
        this.txtNomTipCre.setEnabled(false); txtNomTipCre.setText("");
        this.butConTipCre.setEnabled(false);
        
        this.txtCodEmiTar.setEnabled(false); txtCodEmiTar.setText("");
        this.txtNomEmiTar.setEnabled(false); txtNomEmiTar.setText("");
        this.butConEmiTar.setEnabled(false);
        
        this.txtCodMesCre.setEnabled(false); txtCodMesCre.setText("");
        this.txtNomMesCre.setEnabled(false); txtNomMesCre.setText("");
        this.butConMesCre.setEnabled(false);
        
        this.txtCodRedTarCre.setText("");
        this.txtTipCre.setText("");
        
        this.txtMesGra.setEnabled(false); txtMesGra.setText(""); 
        
        this.dblPorDesTarCre=0;
        this.txtPorComTarCre.setText("");
        this.txtPorDesVenTarCre.setText("");
    }    
    
    public void calculaDescuento(){        
        double dblPorDesCli=Glo_dlbMaxDes;
        if (intTipForPag2 == 4){
            for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                    tblDat.setValueAt(""+objUti.redondear(dblPorDesTarCre, 2) , i, INT_TBL_PORDES);
                }
            }            
//        }else{
//            for(int i=0; i<tblDat.getRowCount();i++){
//                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
//                    tblDat.setValueAt(""+objUti.redondear(dblPorDesCli, 2) , i, INT_TBL_PORDES);
//                }
//            }            
        }
        
        //calculaSubtotal();
        calculaSubTotTodLosItm2();
    }           
    
     
    

   private boolean Configurar_tabla() {
        boolean blnRes=false;
        try{
            
            //dblPorIvaaux = getIva();
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
            vecCab.add(INT_TBL_STKBOD,"Stock Con");
            vecCab.add(INT_TBL_ULTCOS, "Ult. Costo"); 
            vecCab.add(INT_TBL_PREUNI,"Precio");            
            vecCab.add(INT_TBL_UTILIDAD,"Utilidad %");
            vecCab.add(INT_TBL_PREUNI2, "Precio Unitario"); 
            vecCab.add(INT_TBL_PORDES,"%Desc");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");            
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ESTADO,"");
            vecCab.add(INT_TBL_IVATXT,"");
            vecCab.add(INT_TBL_BLNPRE,"");
            vecCab.add(INT_TBL_CANORI,"Can.Ori");
            vecCab.add(INT_TBL_BODORI,"Bod.Ori");
            vecCab.add(INT_TBL_CODORI,"Cod.Ori");
            vecCab.add(INT_TBL_ITMALT2,"ItmAlt2");
            vecCab.add(INT_TBL_ITMSER,"Itm.SER.");
            vecCab.add(INT_TBL_ITMTER," isTerL");
            vecCab.add(INT_TBL_CODEMPREL ," CODEMP"); 
            vecCab.add(INT_TBL_CODLOCREL ," CODLOC");
            vecCab.add(INT_TBL_CODTIPREL ," CODTIPDOC");
            vecCab.add(INT_TBL_CODDOCREL ," CODDOC");
            vecCab.add(INT_TBL_CODREGREL ," CODREG");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
            //Configurar JTable: Establecer la fila de cabecera. 
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
            objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_ULTCOS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI2, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANORI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODORI);
            arlAux.add("" + INT_TBL_BODORI);
            arlAux.add("" + INT_TBL_CANORI);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
       
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
              
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);         
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(55);

            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);         
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(55);
           
            OcultarColumna(INT_TBL_CODBOD, tcmAux);
            OcultarColumna(INT_TBL_BUTBOD, tcmAux);
            OcultarColumna(INT_TBL_CODITM, tcmAux);
            OcultarColumna(INT_TBL_IVATXT, tcmAux);
            OcultarColumna(INT_TBL_BLNPRE, tcmAux);
            OcultarColumna(INT_TBL_STKBOD, tcmAux);
            OcultarColumna(INT_TBL_ULTCOS, tcmAux);
            OcultarColumna(INT_TBL_UTILIDAD, tcmAux);
            OcultarColumna(INT_TBL_PREUNI2, tcmAux);
            OcultarColumna(INT_TBL_ESTADO, tcmAux);
            OcultarColumna(INT_TBL_CANORI, tcmAux);
            OcultarColumna(INT_TBL_BODORI, tcmAux);
            OcultarColumna(INT_TBL_CODORI, tcmAux);
            OcultarColumna(INT_TBL_ITMALT2, tcmAux);
          //  OcultarColumna(INT_TBL_ITMSER, tcmAux);
            OcultarColumna(INT_TBL_ITMTER, tcmAux);
            OcultarColumna(INT_TBL_CODEMPREL, tcmAux);
            OcultarColumna(INT_TBL_CODLOCREL, tcmAux);
            OcultarColumna(INT_TBL_CODTIPREL, tcmAux);
            OcultarColumna(INT_TBL_CODDOCREL, tcmAux);
            OcultarColumna(INT_TBL_CODREGREL, tcmAux);
             
        
            
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            
          /*
          * Dando tamano a la tabla de pagos
          */
         tblPag.getTableHeader().setReorderingAllowed(false);
         tblPag.getColumnModel().getColumn(0).setPreferredWidth(20);
         tblPag.getColumnModel().getColumn(0).setResizable(false);
         tblPag.getColumnModel().getColumn(1).setPreferredWidth(70);
         tblPag.getColumnModel().getColumn(1).setResizable(false);
         tblPag.getColumnModel().getColumn(2).setPreferredWidth(140);
         tblPag.getColumnModel().getColumn(2).setResizable(false);
         tblPag.getColumnModel().getColumn(3).setPreferredWidth(70);
         tblPag.getColumnModel().getColumn(3).setResizable(false);
         tblPag.getColumnModel().getColumn(4).setPreferredWidth(140);
         tblPag.getColumnModel().getColumn(4).setResizable(false);
         tblPag.getColumnModel().getColumn(5).setPreferredWidth(130);
         tblPag.getColumnModel().getColumn(5).setResizable(false);
            
            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
//            vecAux.add("" + INT_TBL_ITMALT);
//            vecAux.add("" + INT_TBL_BUTITM);
//            vecAux.add("" + INT_TBL_CODBOD); 
//            vecAux.add("" + INT_TBL_BUTBOD);
            vecAux.add("" + INT_TBL_CANMOV);
            vecAux.add("" + INT_TBL_PREUNI);
            vecAux.add("" + INT_TBL_PORDES);
            vecAux.add("" + INT_TBL_DESITM);
         
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    
                    blnChangeData = false;        
                    if(tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA) != null)
                        strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA).toString();
                   
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }
                }        
                
                @Override
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
                            calculaSubtotal();
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
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(objTblCelRenLbl);

            tcmAux.getColumn(INT_TBL_ULTCOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREUNI2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANORI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            
            //Configurar JTable: Editor de celdas.
                                     
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////            
                                                             
            int intColVen2[]=new int[9];
            intColVen2[0]=1;
            intColVen2[1]=3;
            intColVen2[2]=6;
            intColVen2[3]=11;            
            intColVen2[4]=7;          
            intColVen2[5]=8;      
            intColVen2[6]=2;    
            intColVen2[7]=9;
            intColVen2[8]=10;
           
            int intColTbl2[]=new int[9];
            intColTbl2[0]=INT_TBL_ITMALT;
            intColTbl2[1]=INT_TBL_DESITM;
            intColTbl2[2]=INT_TBL_PREUNI;
            intColTbl2[3]=INT_TBL_CODITM;
            intColTbl2[4]=INT_TBL_IVATXT;
            intColTbl2[5]=INT_TBL_UNIDAD;
            intColTbl2[6]=INT_TBL_ITMALT2;
            intColTbl2[7]=INT_TBL_ITMSER;
            intColTbl2[8]=INT_TBL_ITMTER;            
            
           objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco2(tblDat, objVenCon2, intColVen2, intColTbl2,objZafParSis);  //********
           tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);  //******
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******  
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                   int intNumFil = tblDat.getSelectedRow();
                   blnChangeData = false;  
                   
                            if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                                strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                            else 
                                strBeforeValue = "";
                            if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                                tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                            } 
                }                  
                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                      int intNumFil = tblDat.getSelectedRow();
                      
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                        strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
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
                     
                   String strValpre="0.00";
                   strValpre = tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).toString();
                   if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).equals("")) strValpre="0"; 
                   if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI)==null) strValpre="0";     
                   tblDat.setValueAt(strValpre,intNumFil,INT_TBL_PREUNI);
               
                   if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)
                         tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV); 
                 
                   
                   if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)
                         tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES);                                       
                   
                     tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);  
                     
                    if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
                        if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).equals("S")) {
                            tblDat.setValueAt(true, intNumFil, INT_TBL_BLNIVA);
                            if(tblDat.getValueAt(0,INT_TBL_PORDES) != null)
                                  tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
                        } else {
                            tblDat.setValueAt(false, intNumFil, INT_TBL_BLNIVA);
                            if(tblDat.getValueAt(0,INT_TBL_PORDES) != null)
                               tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
                        }
                       }
                     
                    if (!tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;        
                        tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);
                       
                    }
           
                     calculaSubtotal(); 
                     
                      tblDat.repaint();    
                      tblDat.requestFocus();
                      tblDat.editCellAt(intNumFil, INT_TBL_CANMOV);
                }
            });
            
             //**************************************************************************
            // ButFndItm ObjFndItm = new ButFndItm(tblDat, INT_TBL_BUTITM);   //*****
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, objVenCon2, intColVen2, intColTbl2);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada())
                    {
                       eventoVenCon();
                    }
                }
            });
           //*******************************************************************************
            
            
            intColVen2=null;
            intColTbl2=null;
        
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(new RenderDecimales(intNumDec) );  //*********
            
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    blnChangeData = false;
                    String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                    
                    if(strTipTer.trim().equals("S"))
                    {
                          objTblCelEdiTxt.setCancelarEdicion(true);
                    }else {
                          
                            if (tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn())!=null)
                                strBeforeValue = tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn()).toString();
                            else
                                strBeforeValue = "";
                            if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                                tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }}
                }   
                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     int intNumFil = tblDat.getSelectedRow();
                    
                    String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                      
                    if(strTipTer.trim().equals("S"))
                    {
                          objTblCelEdiTxt.setCancelarEdicion(true);
                    }else {
                     
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
                            if (intTipForPag2 == 4){
                               calculaSubTot2();                          
                            }
                        }
                        if (!tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                            blnChangeData = true; 
                            calculaSubtotal();
                        }
                        
                        if (intTipForPag2 == 4){
                           calculaSubTot2();                          
                        }                                        
                }}
            });
                   
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
            //Configurar JTable: Centrar columnas.
            ZafTblPopMn = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            ZafTblPopMn.setInsertarFilaVisible(false);
            ZafTblPopMn.setInsertarFilasVisible(false);
            
        //***********************************************************
            ZafTblPopMn.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                @Override
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    
                    String strTipTer = ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMTER).toString());
                    int i, intFilSel[];
                    intFilSel=tblDat.getSelectedRows();
                    
                     
                       
                         
                    if (ZafTblPopMn.isClickInsertarFila())
                    {
                       // System.out.println("beforeClick: isClickInsertarFila");
                        //Cancelar la edición cuado sea necesario.
                        if (tblDat.getSelectedRow()==1)
                            ZafTblPopMn.cancelarClick();
                    } 
                    else if (ZafTblPopMn.isClickEliminarFila())  
                    {
                        for (i=0; i<intFilSel.length; i++){
                            strTipTer = ((tblDat.getValueAt((intFilSel[i]), INT_TBL_ITMTER)==null)?"":tblDat.getValueAt((intFilSel[i]), INT_TBL_ITMTER).toString());
                          //  System.out.println(">> "+ strTipTer +" <> "+ (intFilSel[i])  +" >> "+ intFilSel.length +" <> "+ i );
                            if(strTipTer.trim().equals("S")){
                                 javax.swing.JOptionPane.showMessageDialog(null, "No es posible eliminar la fila por que corresponde a una terminal L .");
                                 ZafTblPopMn.cancelarClick(); 
                                 break;
                       }}
                    }
                       
                   intFilSel=null;
                    
                }
                @Override
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (ZafTblPopMn.isClickInsertarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                        //System.out.println("afterClick: isClickInsertarFila");
                    }
                    else if (ZafTblPopMn.isClickEliminarFila())
                    {
                       // System.out.println("afterClick: isClickEliminarFila");
                      //  javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                    }
                }
            });
            
          //***********************************************************
          
              
            
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;                        
    }    
   
    public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);  // INT_TBL_INS
            
        }else{
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }       
   }
  
    
    
    
//*************************************************************************************
    

    public void OcultarColumna(int Columna, javax.swing.table.TableColumnModel tcmAux){
        try{
            tcmAux.getColumn(Columna).setWidth(0);
            tcmAux.getColumn(Columna).setMaxWidth(0);
            tcmAux.getColumn(Columna).setMinWidth(0);
            tcmAux.getColumn(Columna).setPreferredWidth(0);
            tcmAux.getColumn(Columna).setResizable(false);                         
                                
        }catch(Exception e){ objUti.mostrarMsgErr_F1(this,e);  }
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

                  //** if(tblDat.getValueAt(intNumFil,INT_TBL_PRE_COS)==null) tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PRE_COS);    
                   
                   if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES); 
                   
                   tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);  
                     
                   if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
                          if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).toString().trim().equalsIgnoreCase("S")) 
                               tblDat.setValueAt(true, intNumFil, INT_TBL_BLNIVA);
                             else 
                                 tblDat.setValueAt(false, intNumFil, INT_TBL_BLNIVA);
                    }
                     
                   tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);
                 
                   if(tblDat.getValueAt(0,INT_TBL_PORDES) != null) tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
                      
                    calculaSubtotal(); 
                      
                    tblDat.repaint();    
                    tblDat.requestFocus();
                    tblDat.editCellAt(intNumFil, INT_TBL_CANMOV);
          }
             
 //*********************************************************************************************************************  
 //********************************************************************************************************************* 
    
    public void calculaSubtotal(){
          double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;

          int intFilSel=tblDat.getSelectedRow();
          
                   dblCan    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV).toString()))); 
                   dblPre    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI).toString())));
                   dblDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PORDES).toString()))),2);
                   dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100));
                   dblTotal  = (dblCan * dblPre)- dblValDes;  
                   dblTotal =  objUti.redondear(dblTotal,3);  
                   dblTotal =  objUti.redondear(dblTotal,2);  
                   tblDat.setValueAt(dblTotal+"",intFilSel,INT_TBL_TOTAL);
                   calculaTotal(dblPorIva);
         calculaPagos();  
         generaAsiento();
    }    
     
    public void calculaSubTot2(){
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00; 
        double dblPorDesReal=0, dblPreLis=0 ;
        double dblValDesTarCre=0, dblTotalTarCre=0, dblPorDesRealTarCre=0;
        int intColActPre=0, intFilSel=tblDat.getSelectedRow();

        /*boolean blnActPreDes=objTooBar.getValidaVigencia();
        if(blnActPreDes) intColActPre=INT_TBL_PRELISITM2;
         else intColActPre=INT_TBL_PRELISITM; */
        
        dblCan = Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV).toString())));
        dblPre = Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI).toString())));
        dblDes = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PORDES).toString()))),2);
        dblPreLis=objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI2)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI2).toString()))),2);
        
        dblValDes = objUti.redondear(((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100)), 6);
        dblTotal  = (dblCan * dblPre)- dblValDes;
        dblTotal =  objUti.redondear(dblTotal, 6 );
        dblTotal =  objUti.redondear(dblTotal, 6 );        
        dblPorDesReal=objUti.redondear(((dblTotal - dblPreLis)/ ((dblPreLis==0)?1:dblPreLis) ), 6 ) ;
                
        dblValDesTarCre = objUti.redondear(((dblCan * dblPreLis)==0)?0:((dblCan * dblPreLis) * (dblPorDesTarCre / 100)), 6 );
        dblTotalTarCre = (dblCan * dblPreLis)- dblValDesTarCre;
        dblTotalTarCre =  objUti.redondear(dblTotalTarCre, 6 );
        dblTotalTarCre =  objUti.redondear(dblTotalTarCre, 6 );
        dblPorDesRealTarCre=objUti.redondear( ((dblTotalTarCre - dblPreLis)/((dblPreLis==0)?1:dblPreLis)), 6 );
        
        dblPorDesReal=Math.abs(dblPorDesReal);
        dblPorDesRealTarCre=Math.abs(dblPorDesRealTarCre);
        
        System.out.println("ZafVen24_01.calculaSubTot2: Porcentaje Modificado " + dblPorDesReal + " vs. Porcentaje Tarjeta " + dblPorDesRealTarCre);
        
        if (dblPorDesReal > dblPorDesRealTarCre){
            MensajeInf("No puede exceder el porcentaje de descuento asignado a la tarjeta"); 
            calculaDescuento();
        }
        
        //tblDat.setValueAt(dblTotal+"",intFilSel,INT_TBL_TOTAL);
        //calculaTot();
        //calculaPag(); 
    }

    public void calculaSubTotTodLosItm2(){
        String strIva="S";
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
                
               // if(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)==null)
                //    dblIva = dblIva + 0;
                //else{
                    
                    strIva=((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)==null?"S":(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("")?"S":tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true")?"S":"N")));
                    if(strIva.equals("S")){   
                       dblTmp=((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString()));
                       dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                    }
                    else  dblIva = dblIva + 0;
                    
                    //dblTmp = ((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString())) : 0 );
                   
                //}
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
        
         calculaPagos();  
         generaAsiento();
         
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
        pan2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTipDocAli = new javax.swing.JTextField();
        txtTipDocNom = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCot = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblCom = new javax.swing.JLabel();
        txtVenCod = new javax.swing.JTextField();
        txtVenNom = new javax.swing.JTextField();
        butVenCon = new javax.swing.JButton();
        lblfecha = new javax.swing.JLabel();
        panCotGenNorNor = new javax.swing.JPanel();
        txtDoc = new javax.swing.JTextField();
        lblDoc = new javax.swing.JLabel();
        txtFac = new javax.swing.JTextField();
        lblFac = new javax.swing.JLabel();
        lblAte = new javax.swing.JLabel();
        txtPed = new javax.swing.JTextField();
        txtAte = new javax.swing.JTextField();
        lblPed = new javax.swing.JLabel();
        panCotGenNorSur = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtCliDir = new javax.swing.JTextField();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        butCliCon = new javax.swing.JButton();
        lblGuia = new javax.swing.JLabel();
        txtGuia = new javax.swing.JTextField();
        lblGuia1 = new javax.swing.JLabel();
        txtOD = new javax.swing.JTextField();
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
        panCotForPagNor = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        txtNomForPag = new javax.swing.JTextField();
        butCliCon1 = new javax.swing.JButton();
        txtCodigoFormaPago = new javax.swing.JTextField();
        lblTarCre = new javax.swing.JLabel();
        txtCodTarCre = new javax.swing.JTextField();
        txtNomTarCre = new javax.swing.JTextField();
        butConTarCre = new javax.swing.JButton();
        lblTipCre = new javax.swing.JLabel();
        txtCodTipCre = new javax.swing.JTextField();
        txtNomTipCre = new javax.swing.JTextField();
        butConTipCre = new javax.swing.JButton();
        lblEmiTar = new javax.swing.JLabel();
        txtCodEmiTar = new javax.swing.JTextField();
        txtNomEmiTar = new javax.swing.JTextField();
        butConEmiTar = new javax.swing.JButton();
        lblMesCre = new javax.swing.JLabel();
        txtCodMesCre = new javax.swing.JTextField();
        txtNomMesCre = new javax.swing.JTextField();
        butConMesCre = new javax.swing.JButton();
        lblMesGra = new javax.swing.JLabel();
        txtMesGra = new javax.swing.JTextField();
        panCotForPagCen = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblPag = new javax.swing.JTable();
        PanOtro = new javax.swing.JPanel();
        panNorOtros = new javax.swing.JPanel();
        lblPartida = new javax.swing.JLabel();
        txtPartida = new javax.swing.JTextField();
        lblTransporte = new javax.swing.JLabel();
        txtTransporte = new javax.swing.JTextField();
        lblMotivo = new javax.swing.JLabel();
        cboMotivoTransporte = new javax.swing.JComboBox();
        panAsiento = new javax.swing.JPanel();
        panCotNor = new javax.swing.JPanel();
        lblCotNumDes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        butacep = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

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

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 135));
        panCotGenNor.setLayout(new java.awt.BorderLayout());

        pan2.setPreferredSize(new java.awt.Dimension(600, 50));
        pan2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setText("Tipo Documento:");
        pan2.add(jLabel2);
        jLabel2.setBounds(4, 12, 110, 15);

        txtTipDocAli.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipDocAli.setEditable(false);
        txtTipDocAli.setMinimumSize(new java.awt.Dimension(0, 0));
        txtTipDocAli.setPreferredSize(new java.awt.Dimension(25, 20));
        txtTipDocAli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocAliActionPerformed(evt);
            }
        });
        pan2.add(txtTipDocAli);
        txtTipDocAli.setBounds(100, 10, 64, 20);

        txtTipDocNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipDocNom.setEditable(false);
        txtTipDocNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtTipDocNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocNomActionPerformed(evt);
            }
        });
        pan2.add(txtTipDocNom);
        txtTipDocNom.setBounds(164, 10, 240, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.setEnabled(false);
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        pan2.add(butTipDoc);
        butTipDoc.setBounds(403, 10, 20, 20);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel1.setText("No. Cotización:");
        pan2.add(jLabel1);
        jLabel1.setBounds(6, 30, 110, 15);

        objZafParSis.getColorCamposObligatorios();
        txtCot.setEditable(false);
        txtCot.setMaximumSize(null);
        txtCot.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCotActionPerformed(evt);
            }
        });
        pan2.add(txtCot);
        txtCot.setBounds(100, 30, 92, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        pan2.add(lblFecDoc);
        lblFecDoc.setBounds(450, 12, 108, 15);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCom.setText("Vendedor:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        pan2.add(lblCom);
        lblCom.setBounds(450, 30, 64, 15);

        txtVenCod.setEditable(false);
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
        pan2.add(txtVenCod);
        txtVenCod.setBounds(515, 30, 35, 20);

        txtVenNom.setEditable(false);
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
        pan2.add(txtVenNom);
        txtVenNom.setBounds(550, 30, 110, 20);

        butVenCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVenCon.setText("...");
        butVenCon.setEnabled(false);
        butVenCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butVenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenConActionPerformed(evt);
            }
        });
        pan2.add(butVenCon);
        butVenCon.setBounds(660, 30, 20, 20);

        lblfecha.setBackground(new java.awt.Color(255, 255, 255));
        lblfecha.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblfecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblfecha.setOpaque(true);
        pan2.add(lblfecha);
        lblfecha.setBounds(550, 6, 77, 18);

        panCotGenNor.add(pan2, java.awt.BorderLayout.NORTH);

        panCotGenNorNor.setPreferredSize(new java.awt.Dimension(600, 55));
        panCotGenNorNor.setLayout(null);

        txtDoc.setBackground(objZafParSis.getColorCamposSistema()
        );
        txtDoc.setEditable(false);
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtDoc);
        txtDoc.setBounds(100, 0, 92, 20);

        lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDoc.setText("No. Documento:");
        panCotGenNorNor.add(lblDoc);
        lblDoc.setBounds(6, 0, 110, 15);

        txtFac.setBackground(objZafParSis.getColorCamposObligatorios()
        );
        txtFac.setEditable(false);
        txtFac.setMaximumSize(null);
        txtFac.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtFac);
        txtFac.setBounds(100, 20, 92, 20);

        lblFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFac.setText("No. Factura:");
        panCotGenNorNor.add(lblFac);
        lblFac.setBounds(6, 20, 110, 15);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte);
        lblAte.setBounds(450, 0, 60, 15);

        txtPed.setEditable(false);
        panCotGenNorNor.add(txtPed);
        txtPed.setBounds(552, 20, 110, 20);

        txtAte.setEditable(false);
        txtAte.setMaximumSize(null);
        txtAte.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtAte);
        txtAte.setBounds(534, 0, 146, 20);

        lblPed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPed.setText("No. Pedido:");
        panCotGenNorNor.add(lblPed);
        lblPed.setBounds(450, 20, 72, 15);

        panCotGenNor.add(panCotGenNorNor, java.awt.BorderLayout.CENTER);

        panCotGenNorSur.setPreferredSize(new java.awt.Dimension(600, 45));
        panCotGenNorSur.setLayout(null);

        lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPrv.setText("Cliente::");
        panCotGenNorSur.add(lblPrv);
        lblPrv.setBounds(6, 0, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDir.setText("Dirección:");
        panCotGenNorSur.add(lblDir);
        lblDir.setBounds(6, 20, 60, 15);

        txtCliDir.setEditable(false);
        txtCliDir.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCliDir.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCliDir.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCliDirCaretUpdate(evt);
            }
        });
        txtCliDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliDirActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(txtCliDir);
        txtCliDir.setBounds(100, 20, 320, 20);

        txtCliCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliCod.setEditable(false);
        txtCliCod.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
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
        txtCliCod.setBounds(100, 0, 35, 20);

        txtCliNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliNom.setEditable(false);
        txtCliNom.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
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
        txtCliNom.setBounds(134, 0, 270, 20);

        butCliCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCliCon.setText("...");
        butCliCon.setEnabled(false);
        butCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butCliCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliConActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(butCliCon);
        butCliCon.setBounds(403, 0, 22, 20);

        lblGuia.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblGuia.setText("No. Guia Remisión:");
        lblGuia.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorSur.add(lblGuia);
        lblGuia.setBounds(450, 0, 100, 15);

        txtGuia.setEditable(false);
        panCotGenNorSur.add(txtGuia);
        txtGuia.setBounds(552, 0, 80, 20);

        lblGuia1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblGuia1.setText("No. Ord.Des.:"); // NOI18N
        lblGuia1.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorSur.add(lblGuia1);
        lblGuia1.setBounds(450, 20, 100, 15);

        txtOD.setEditable(false);
        panCotGenNorSur.add(txtOD);
        txtOD.setBounds(552, 20, 80, 20);

        panCotGenNor.add(panCotGenNorSur, java.awt.BorderLayout.SOUTH);

        panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Linea", "Codigo", "...", "Descripción", "Unidad", "Cantidad", "Precio", "%Desc", "Iva", "Total", "Codigo", "BLVPRE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.Short.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, true, true, true, false, false, false
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

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs2.setText("Observación 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCotGenSurEst.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblSubTot.setText("SubTotal:");
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenSurEst.add(lblSubTot);

        txtSub.setEditable(false);
        txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblIva.setText("IVA 12%:");
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);

        txtIva.setEditable(false);
        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtIva);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setEditable(false);
        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panCotGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabCotCom.addTab("tab1", panCotGen);

        panCotForPag.setLayout(new java.awt.BorderLayout());

        panCotForPagNor.setPreferredSize(new java.awt.Dimension(249, 150));
        panCotForPagNor.setLayout(null);

        lblForPag.setText("Forma de Pago:"); // NOI18N
        panCotForPagNor.add(lblForPag);
        lblForPag.setBounds(110, 10, 76, 14);

        txtNomForPag.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomForPag.setPreferredSize(new java.awt.Dimension(180, 21));
        panCotForPagNor.add(txtNomForPag);
        txtNomForPag.setBounds(270, 10, 190, 21);

        butCliCon1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCliCon1.setText("..."); // NOI18N
        butCliCon1.setPreferredSize(new java.awt.Dimension(20, 20));
        butCliCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliCon1ActionPerformed(evt);
            }
        });
        panCotForPagNor.add(butCliCon1);
        butCliCon1.setBounds(460, 10, 25, 20);

        txtCodigoFormaPago.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodigoFormaPago.setPreferredSize(new java.awt.Dimension(180, 21));
        txtCodigoFormaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoFormaPagoActionPerformed(evt);
            }
        });
        txtCodigoFormaPago.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoFormaPagoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFormaPagoFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtCodigoFormaPago);
        txtCodigoFormaPago.setBounds(240, 10, 30, 21);

        lblTarCre.setText("Tarjeta de Credito:"); // NOI18N
        panCotForPagNor.add(lblTarCre);
        lblTarCre.setBounds(110, 33, 120, 19);

        txtCodTarCre.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodTarCre.setPreferredSize(new java.awt.Dimension(180, 21));
        txtCodTarCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTarCreActionPerformed(evt);
            }
        });
        txtCodTarCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTarCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTarCreFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtCodTarCre);
        txtCodTarCre.setBounds(240, 33, 30, 21);

        txtNomTarCre.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomTarCre.setPreferredSize(new java.awt.Dimension(180, 21));
        txtNomTarCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTarCreActionPerformed(evt);
            }
        });
        txtNomTarCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTarCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTarCreFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtNomTarCre);
        txtNomTarCre.setBounds(270, 33, 190, 21);

        butConTarCre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butConTarCre.setText("..."); // NOI18N
        butConTarCre.setPreferredSize(new java.awt.Dimension(20, 20));
        butConTarCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConTarCreActionPerformed(evt);
            }
        });
        panCotForPagNor.add(butConTarCre);
        butConTarCre.setBounds(460, 33, 25, 20);

        lblTipCre.setText("Tipo de Credito:"); // NOI18N
        panCotForPagNor.add(lblTipCre);
        lblTipCre.setBounds(110, 55, 100, 19);

        txtCodTipCre.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodTipCre.setPreferredSize(new java.awt.Dimension(180, 21));
        txtCodTipCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipCreActionPerformed(evt);
            }
        });
        txtCodTipCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTipCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTipCreFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtCodTipCre);
        txtCodTipCre.setBounds(240, 55, 30, 21);

        txtNomTipCre.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomTipCre.setPreferredSize(new java.awt.Dimension(180, 21));
        txtNomTipCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipCreActionPerformed(evt);
            }
        });
        txtNomTipCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTipCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTipCreFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtNomTipCre);
        txtNomTipCre.setBounds(270, 55, 190, 21);

        butConTipCre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butConTipCre.setText("..."); // NOI18N
        butConTipCre.setPreferredSize(new java.awt.Dimension(20, 20));
        butConTipCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConTipCreActionPerformed(evt);
            }
        });
        panCotForPagNor.add(butConTipCre);
        butConTipCre.setBounds(460, 55, 25, 20);

        lblEmiTar.setText("Emisor de la Tarjeta:"); // NOI18N
        panCotForPagNor.add(lblEmiTar);
        lblEmiTar.setBounds(110, 78, 130, 19);

        txtCodEmiTar.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodEmiTar.setPreferredSize(new java.awt.Dimension(180, 21));
        txtCodEmiTar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmiTarActionPerformed(evt);
            }
        });
        txtCodEmiTar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmiTarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmiTarFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtCodEmiTar);
        txtCodEmiTar.setBounds(240, 78, 30, 21);

        txtNomEmiTar.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomEmiTar.setPreferredSize(new java.awt.Dimension(180, 21));
        txtNomEmiTar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmiTarActionPerformed(evt);
            }
        });
        txtNomEmiTar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmiTarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmiTarFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtNomEmiTar);
        txtNomEmiTar.setBounds(270, 78, 190, 21);

        butConEmiTar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butConEmiTar.setText("..."); // NOI18N
        butConEmiTar.setPreferredSize(new java.awt.Dimension(20, 20));
        butConEmiTar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConEmiTarActionPerformed(evt);
            }
        });
        panCotForPagNor.add(butConEmiTar);
        butConEmiTar.setBounds(460, 78, 25, 20);

        lblMesCre.setText("Meses de Credito:"); // NOI18N
        panCotForPagNor.add(lblMesCre);
        lblMesCre.setBounds(110, 101, 130, 19);

        txtCodMesCre.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodMesCre.setPreferredSize(new java.awt.Dimension(180, 21));
        txtCodMesCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMesCreActionPerformed(evt);
            }
        });
        txtCodMesCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMesCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMesCreFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtCodMesCre);
        txtCodMesCre.setBounds(240, 101, 30, 21);

        txtNomMesCre.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomMesCre.setPreferredSize(new java.awt.Dimension(180, 21));
        txtNomMesCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomMesCreActionPerformed(evt);
            }
        });
        txtNomMesCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomMesCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomMesCreFocusLost(evt);
            }
        });
        panCotForPagNor.add(txtNomMesCre);
        txtNomMesCre.setBounds(270, 101, 190, 21);

        butConMesCre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butConMesCre.setText("..."); // NOI18N
        butConMesCre.setPreferredSize(new java.awt.Dimension(20, 20));
        butConMesCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConMesCreActionPerformed(evt);
            }
        });
        panCotForPagNor.add(butConMesCre);
        butConMesCre.setBounds(460, 101, 25, 20);

        lblMesGra.setText("Meses de Gracia:"); // NOI18N
        panCotForPagNor.add(lblMesGra);
        lblMesGra.setBounds(110, 125, 130, 19);

        txtMesGra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtMesGra.setEnabled(false);
        txtMesGra.setPreferredSize(new java.awt.Dimension(180, 21));
        panCotForPagNor.add(txtMesGra);
        txtMesGra.setBounds(240, 125, 220, 21);

        panCotForPag.add(panCotForPagNor, java.awt.BorderLayout.NORTH);

        panCotForPagCen.setEnabled(false);
        panCotForPagCen.setPreferredSize(new java.awt.Dimension(249, 50));
        panCotForPagCen.setLayout(new java.awt.BorderLayout());

        tblPag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "Retención", "Monto de Pago", "Días de gracia", "CodRet", "tx_sop"
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

        panCotForPagCen.add(spnForPag, java.awt.BorderLayout.CENTER);

        panCotForPag.add(panCotForPagCen, java.awt.BorderLayout.CENTER);

        tabCotCom.addTab("tab2", panCotForPag);

        PanOtro.setLayout(new java.awt.BorderLayout());

        panNorOtros.setMinimumSize(new java.awt.Dimension(600, 200));
        panNorOtros.setPreferredSize(new java.awt.Dimension(600, 300));
        panNorOtros.setLayout(null);

        lblPartida.setText("Punto de partida");
        panNorOtros.add(lblPartida);
        lblPartida.setBounds(0, 0, 344, 21);
        panNorOtros.add(txtPartida);
        txtPartida.setBounds(344, 0, 344, 20);

        lblTransporte.setText("Transporte");
        panNorOtros.add(lblTransporte);
        lblTransporte.setBounds(0, 21, 344, 21);
        panNorOtros.add(txtTransporte);
        txtTransporte.setBounds(344, 21, 344, 20);

        lblMotivo.setText("Motivo de Transporte");
        panNorOtros.add(lblMotivo);
        lblMotivo.setBounds(0, 42, 344, 21);
        panNorOtros.add(cboMotivoTransporte);
        cboMotivoTransporte.setBounds(344, 42, 344, 20);

        PanOtro.add(panNorOtros, java.awt.BorderLayout.NORTH);

        tabCotCom.addTab("tab3", PanOtro);

        panAsiento.setLayout(new java.awt.BorderLayout());
        tabCotCom.addTab("tab4", panAsiento);

        getContentPane().add(tabCotCom, java.awt.BorderLayout.CENTER);

        lblCotNumDes.setText("Factura");
        lblCotNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCotNumDes.setOpaque(true);
        panCotNor.add(lblCotNumDes);

        getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

        jPanel1.setMinimumSize(new java.awt.Dimension(40, 10));
        jPanel1.setPreferredSize(new java.awt.Dimension(60, 45));
        jPanel1.setLayout(new java.awt.BorderLayout());

        butacep.setText("Aceptar");
        butacep.setPreferredSize(new java.awt.Dimension(90, 25));
        butacep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butacepActionPerformed(evt);
            }
        });
        jPanel2.add(butacep);

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

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
            cerrarVentana(); 
            
    }//GEN-LAST:event_formWindowClosing
  
    private void butacepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butacepActionPerformed
        // TODO add your handling code here:
        
        if(insertar()){
          cerrarVentana2();
        }else
            cerrarVentana3();
        
    }//GEN-LAST:event_butacepActionPerformed
             
    public boolean insertar()
        {  strstReg_glo=""; 
            if (!validaCampos())
                return false;
            if (!insertarReg())
                return false;
          
            return true;
        }
            
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
         Configura_ventana_consulta();   
          Configurar_tabla();
          configurarTablaPago();
        
          if(consultar2(objZafParSis.getCodigoEmpresa() , objZafParSis.getCodigoLocal() )){
             // System.out.println("OKi");
          }
                                   
    }//GEN-LAST:event_formWindowOpened



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
      ZafColNumerada zafColNumerada = new ZafColNumerada(tblPag,INT_TBL_PAGLIN);

      objTblModPag.setColumnDataType(INT_TBL_PAGCRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGMON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGGRA, ZafTblMod.INT_COL_DBL, new Integer(0), null);


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

      Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLblPag=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

      objTblCelRenLblPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
      objTblCelRenLblPag.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
      objTblCelRenLblPag.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
      tcmAux.getColumn(INT_TBL_PAGMON).setCellRenderer(objTblCelRenLblPag);
      tcmAux.getColumn(INT_TBL_PAGGRA).setCellRenderer(objTblCelRenLblPag);
      tcmAux.getColumn(INT_TBL_PAGRET).setCellRenderer(objTblCelRenLblPag);

      objTblCelRenLblPag=null;

      tblPag.getTableHeader().setReorderingAllowed(false);
      tcmAux=null;

  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  return blnRes;
}

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
    
         cerrarVentana(); 
  
    }//GEN-LAST:event_butCanActionPerformed

    
      public boolean acepta(){
        return blnEst;
    }
    
    
         public String txtNumFac(){
                return strNumFac;
            }
         
         
           public String txtCodDoc(){
                return strCodDocFac;
            } 
    
    
      private void cerrarVentana(){
        String strMsg = "¿Está Seguro que desea cancelar el proceso de Facturación.?";
        //JOptionPane oppMsg=new JOptionPane();
        /*String strTit;
        strTit="Mensaje del sistema Zafiro";*/
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
               blnAcepta = false;
               blnEst=false;
              
           cerrarObj(); 
           System.gc(); 
           dispose(); 
        }    
    }
    
    

       private void cerrarVentana3(){

            blnAcepta = false;
            blnEst=false;

           cerrarObj();
           System.gc();
           dispose();

    }
      
       private void cerrarVentana2(){

            blnAcepta = true;
            blnEst=true;

           cerrarObj(); 
           System.gc(); 
           dispose(); 
       
    }
    
      
    private void txtVenNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusGained
        // TODO add your handling code here:
        strDesLarVen=txtVenNom.getText();
        txtVenNom.selectAll();
    }//GEN-LAST:event_txtVenNomFocusGained

    private void txtVenCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusGained
        // TODO add your handling code here:
        strCodVen=txtVenCod.getText();
        txtVenCod.selectAll();
    }//GEN-LAST:event_txtVenCodFocusGained

    private void txtCliCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusGained
        // TODO add your handling code here:
        strCodCli=txtCliCod.getText();
        txtCliCod.selectAll();
    }//GEN-LAST:event_txtCliCodFocusGained

    private void txtCliNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtCliNom.getText();
        txtCliNom.selectAll();
    }//GEN-LAST:event_txtCliNomFocusGained

    

    
      private double getCosUni2(String int_co_itm){
       double dblPreUni = 0;
        try{
               java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                 String sSQL = "  SELECT a1.nd_cosUni"+
                               "  FROM tbm_inv AS a1"+
                               "  INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                               "  WHERE a2.co_emp="+ objZafParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objZafParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
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
       catch(java.sql.SQLException Evt) {  return dblPreUni;  }
       catch(Exception Evt) { return dblPreUni;  }                  
       return dblPreUni;
     }      
    
      
      
     public double StockGrupo(String int_co_itm){
       double dblStock = 0;
       try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
           
             String Str_Sql = "SELECT SUM(a2.nd_stkAct) AS nd_stkAct" +
             " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
             " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
             " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
             " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+ int_co_itm +")";

              java.sql.ResultSet rstUni = stmUni.executeQuery(Str_Sql);
                 if(rstUni.next()){
                       dblStock = rstUni.getDouble(1);
                    } 
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }    
            }
       catch(java.sql.SQLException Evt)  {  return dblStock;   }
       catch(Exception Evt) { return dblStock;   }                  
       return dblStock;
     } 
      
     
     
      
    private void txtTipDocNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipDocNomActionPerformed

    private void txtTipDocAliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocAliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipDocAliActionPerformed

    private void txtCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCotActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_txtCotActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
          listaTipdoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed

      private void listaTipdoc(String campo,String strDesBusqueda ,int intTipo){
           objVenConTipdoc.setTitle("Listado Tipos de Documentos"); 
                 switch (intTipo){
                  case 0:
                  break;
                 
                 
                     
                   case 1:
                        objVenConTipdoc.setCampoBusqueda(1);
                        if (objVenConTipdoc.buscar(campo, strDesBusqueda )){ }
                  break;
                  case 2:
                        objVenConTipdoc.setCampoBusqueda(2); 
                        if (objVenConTipdoc.buscar(campo, strDesBusqueda )){ }                
                   break;
                }
             
            
                 
                 objVenConTipdoc.cargarDatos();
                 objVenConTipdoc.show();
              if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
              {
               if(!objVenConTipdoc.getValueAt(1).equals("")){
                   cargarCabTipoDoc(Integer.parseInt(objVenConTipdoc.getValueAt(1)));            
                
                
                }
             }      
    }
    
    
     private void cargarCabTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtTipDocCod.setText(""+objTipDoc.getco_tipdoc());
        txtTipDocNom.setText(objTipDoc.gettx_deslar());
        txtTipDocAli.setText(objTipDoc.gettx_descor());        
       
        objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
       
        
        // getIva();
    }
      
    
    
    
    
     private void CalculoPago(){
           try{
                    java.sql.Connection conPag = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    if (conPag!=null){     

                        

                          String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre , A2.st_sop " +
                                          " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_forPag = "  +txtCodForPag.getText() +// Clausulas Where para las tablas maestras
                                          "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                                          "       and A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                                          "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                                          "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                            String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
                                          " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_forPag = "  +txtCodForPag.getText()+// Clausulas Where para las tablas maestras
                                          "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                                          "       and A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
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
        
         calculaPagos();    
     }
    
    
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
       

    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameIconified

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        // TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        /*String strTit;
        strTit="Mensaje del sistema Zafiro";*/
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
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
         objCiudad_dat=null;
         objInvItm=null;
       //  objZafRep=null;
         objlisCambios=null;
        // objSisCon=null;
         objZafParSis=null;
         txtFecDoc=null;
        
         objTblCelEdiTxtVcoItm=null;
         objUltDocPrint=null;
        }
         catch (Exception e)
         {
          objUti.mostrarMsgErr_F1(this, e);
         }
                
     }
    
    
    private void txtCliNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusLost
        // TODO add your handling code here:
         if (!txtCliNom.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtCliNom.getText().equals(""))
            {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else
            {
                 BuscarCliente("a.tx_nom",txtCliNom.getText(),1);
            }
        }
        else
            txtCliNom.setText(strDesLarCli);
    }//GEN-LAST:event_txtCliNomFocusLost

    private void txtVenNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusLost
        // TODO add your handling code here:
          if (!txtVenNom.getText().equalsIgnoreCase(strDesLarVen))
         {
            if (txtVenNom.getText().equals(""))
            {
               txtVenCod.setText("");
               txtVenNom.setText("");
            }
            else
            {
                 BuscarVendedor("a.tx_nom",txtVenNom.getText(),1);
            }
        }
        else
            txtVenNom.setText(strDesLarVen);  
      
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
 
        if (!txtCliCod.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCliCod.getText().equals(""))
            {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else
            {
                  BuscarCliente("a.co_cli",txtCliCod.getText(),0);
            }
        }
        else
            txtCliCod.setText(strCodCli);
      
    }//GEN-LAST:event_txtCliCodFocusLost

    private void txtVenCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusLost
   
           if (!txtVenCod.getText().equalsIgnoreCase(strCodVen))
         {
            if (txtVenCod.getText().equals(""))
            {
                txtVenCod.setText("");
                txtVenNom.setText("");
            }
            else
            {
                BuscarVendedor("a.co_usr",txtVenCod.getText(),0);
            }
        }
        else
            txtVenCod.setText(strCodVen);  
        
    }//GEN-LAST:event_txtVenCodFocusLost

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

         String strSql="";

            strSql="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper, st_ivaven " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper, a.st_ivaven " +
            " FROM tbm_cli as a  " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " WHERE a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.st_reg='A'  and a.st_cli='S' " +
            "" +
            "  and case when (select co_grp from tbm_cli where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_cli="+intCodCliGrp+") is null then " +
            "  a.co_cli="+intCodCliGrp+" " +
            "   else  co_grp in ( select co_grp from tbm_cli where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_cli="+intCodCliGrp+" ) end  " +
            " " +
            " order by a.tx_nom  " +
            ") AS a";



       objVenConCli.setTitle("Listado de Clientes");
       objVenConCli.setSentenciaSQL(strSql);
       
        if (objVenConCli.buscar(campo, strBusqueda ))
        {
              txtCliCod.setText(objVenConCli.getValueAt(1));
              txtCliNom.setText(objVenConCli.getValueAt(2));
              txtCliDir.setText(objVenConCli.getValueAt(3));
              strTipPer_glo = objVenConCli.getValueAt(6);
              // Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
              // Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
               txtcli_telefono.setText(objVenConCli.getValueAt(4));
               txtcli_identificacion.setText(objVenConCli.getValueAt(5));
               txtcli_ciudad.setText(objVenConCli.getValueAt(9)); 
             // txtVenCod.setText(objVenConCli.getValueAt(10));
             //  txtVenNom.setText(objVenConCli.getValueAt(11));
               strCodTipPerCli =  objVenConCli.getValueAt(12);  
                stIvaVen  =  objVenConCli.getValueAt(13); 
                
                 verificaIvaCli(stIvaVen);
                
                
                 calculaSubTotTodLosItm();
                                    
                 CalculoPago();
                                     
                 generaAsiento();
                 
                
               
        }
        else
        {     objVenConCli.setCampoBusqueda(tipo);
              objVenConCli.cargarDatos();
              objVenConCli.show();
             if (objVenConCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCliCod.setText(objVenConCli.getValueAt(1));
                txtCliNom.setText(objVenConCli.getValueAt(2));
                txtCliDir.setText(objVenConCli.getValueAt(3));
                strTipPer_glo = objVenConCli.getValueAt(6);
               // Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
               // Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
                txtcli_telefono.setText(objVenConCli.getValueAt(4));
                txtcli_identificacion.setText(objVenConCli.getValueAt(5));
                txtcli_ciudad.setText(objVenConCli.getValueAt(9));
              //  txtVenCod.setText(objVenConCli.getValueAt(10));
              //  txtVenNom.setText(objVenConCli.getValueAt(11));
                strCodTipPerCli =  objVenConCli.getValueAt(12); 
                stIvaVen  =  objVenConCli.getValueAt(13); 
                
                 verificaIvaCli(stIvaVen);
                
                
                 calculaSubTotTodLosItm();
                                    
                 CalculoPago();
                                     
                 generaAsiento();
                 
                
             }
              else{
                    txtCliCod.setText(strCodCli);
                    txtCliNom.setText(strDesLarCli);
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
            //txtFecDoc.getDocument().addDocumentListener(objlisCambios);
            txtVenCod.getDocument().addDocumentListener(objlisCambios);
            txtVenNom.getDocument().addDocumentListener(objlisCambios);
            txtAte.getDocument().addDocumentListener(objlisCambios);
        

        //Pie de pagina
            txaObs1.getDocument().addDocumentListener(objlisCambios);
            txaObs2.getDocument().addDocumentListener(objlisCambios);
            txtSub.getDocument().addDocumentListener(objlisCambios);
            txtIva.getDocument().addDocumentListener(objlisCambios);
            //txtDes.setText("0");
            txtTot.getDocument().addDocumentListener(objlisCambios);
        
    }   
    
    /*
     * Clase de tipo documenet listener para detectar los cambios en 
     * los textcomponent
     */
    class LisTextos implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent e) {
          
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
          
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
           
        }
    }        
        
    class LisCambioTbl implements TableModelListener{
        @Override
        public void tableChanged(TableModelEvent e){
            calculaTotal(dblPorIva);
        }
    }
    
    private void butVenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenConActionPerformed
          BuscarVendedor("a.co_usr","",0);
    }//GEN-LAST:event_butVenConActionPerformed

    
    
    
    public void BuscarVendedor(String campo,String strBusqueda,int tipo){
      // configurarVenConVendedor();  //************************
        objVenConVen.setTitle("Listado de vendedores"); 
        if (objVenConVen.buscar(campo, strBusqueda ))
        {
            txtVenCod.setText(objVenConVen.getValueAt(1));
            txtVenNom.setText(objVenConVen.getValueAt(2));
        }
        else
        {     objVenConVen.setCampoBusqueda(tipo);
              objVenConVen.cargarDatos();
              objVenConVen.show();
             if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
             {
                txtVenCod.setText(objVenConVen.getValueAt(1));
                txtVenNom.setText(objVenConVen.getValueAt(2));
             }
              else{
                    txtVenCod.setText(strCodVen);
                    txtVenNom.setText(strDesLarVen);
                  }
        }
      
    }
  
  
    
    private void butCliConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliConActionPerformed

        String strSql="";

            strSql="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper, st_ivaven " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper, a.st_ivaven " +
            " FROM  tbr_cliloc as x1 "+
            " inner join tbm_cli as a ON (a.co_emp=x1.co_emp and a.co_cli=x1.co_cli)   " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " WHERE x1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and x1.co_loc="+objZafParSis.getCodigoLocal()+"  and a.st_reg='A'  and a.st_cli='S' " +
            "" +
            "  and case when (select co_grp from tbm_cli where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_cli="+intCodCliGrp+") is null then " +
            "  a.co_cli="+intCodCliGrp+" " +
            "   else  co_grp in ( select co_grp from tbm_cli where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_cli="+intCodCliGrp+" ) end  " +
            " " +
            " order by a.tx_nom  " +
            ") AS a";

            //System.out.println("--> "+strSql);
              
              objVenConCli.setTitle("Listado de Clientes");
              objVenConCli.setSentenciaSQL(strSql);
              objVenConCli.setCampoBusqueda(1);
              objVenConCli.show();
            if (objVenConCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCliCod.setText(objVenConCli.getValueAt(1));
                txtCliNom.setText(objVenConCli.getValueAt(2));
                txtCliDir.setText(objVenConCli.getValueAt(3));
                strTipPer_glo = objVenConCli.getValueAt(6);
               // Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
               // Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
                txtcli_telefono.setText(objVenConCli.getValueAt(4));
                txtcli_identificacion.setText(objVenConCli.getValueAt(5));
                txtcli_ciudad.setText(objVenConCli.getValueAt(9));  
              //  txtVenCod.setText(objVenConCli.getValueAt(10));
              //  txtVenNom.setText(objVenConCli.getValueAt(11));
                strCodTipPerCli =  objVenConCli.getValueAt(12); 
                stIvaVen  =  objVenConCli.getValueAt(13); 
                
                verificaIvaCli(stIvaVen);
                
                
                 calculaSubTotTodLosItm();
                                    
                 CalculoPago();
                                     
                 generaAsiento();
                                     
                 
                
                        
               
             } 
        
              
              
    }//GEN-LAST:event_butCliConActionPerformed

    private void butCliCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliCon1ActionPerformed
        // TODO add your handling code here:
        /*Ventas.ZafVen01.ZafVen01_04 obj = new  Ventas.ZafVen01.ZafVen01_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

        int codcli=Integer.parseInt((((txtCliCod.getText()==null) || (txtCliCod.getText().equalsIgnoreCase("")))?"0":txtCliCod.getText()));
        obj.SetvalCodCli(codcli);

        obj.show();
        if(obj.acepta()){
            txtCodForPag.setText(obj.GetCamSel(1));
            txtNomForPag.setText(obj.GetCamSel(2));

            intTipForPag2 = Integer.parseInt(obj.GetCamSel(3));
            dblPes2 = Double.parseDouble(obj.GetCamSel(4));

            
            CalculoPago();

        }*/
        mostrarVenConFormaPago(0);
        if (intTipForPag2==4){
            habilitarCamposTarCre();
        }else{
            deshabilitarCamposTarCre();
        }
        CalculoPago();    
        
    }//GEN-LAST:event_butCliCon1ActionPerformed

    private void txtCodigoFormaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoFormaPagoActionPerformed
        // TODO add your handling code here:
        txtCodigoFormaPago.transferFocus();
    }//GEN-LAST:event_txtCodigoFormaPagoActionPerformed

    private void txtCodigoFormaPagoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFormaPagoFocusGained
        // TODO add your handling code here:
        strCodForPag=txtCodigoFormaPago.getText();
        txtCodigoFormaPago.selectAll();
    }//GEN-LAST:event_txtCodigoFormaPagoFocusGained

    private void txtCodigoFormaPagoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFormaPagoFocusLost
        // TODO add your handling code here:
        if (txtCodigoFormaPago.getText().equals("")) {
            txtCodigoFormaPago.setText("");
            txtNomForPag.setText("");
        }else
        mostrarVenConFormaPago(1);

        CalculoPago();
    }//GEN-LAST:event_txtCodigoFormaPagoFocusLost

    private void txtCodTarCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTarCreActionPerformed
        // TODO add your handling code here:
        txtCodTarCre.transferFocus();
    }//GEN-LAST:event_txtCodTarCreActionPerformed

    private void txtCodTarCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTarCreFocusGained
        // TODO add your handling code here:
        strCodTarCre=txtCodTarCre.getText();
        txtCodTarCre.selectAll();
    }//GEN-LAST:event_txtCodTarCreFocusGained

    private void txtCodTarCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTarCreFocusLost
        // TODO add your handling code here:
        if (txtCodTarCre.getText().equals("")) {
            txtCodTarCre.setText("");
            txtNomTarCre.setText("");
        }else
        mostrarVenConTarjetaCredito(1);
    }//GEN-LAST:event_txtCodTarCreFocusLost

    private void txtNomTarCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTarCreActionPerformed
        // TODO add your handling code here:
        txtNomTarCre.transferFocus();
    }//GEN-LAST:event_txtNomTarCreActionPerformed

    private void txtNomTarCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTarCreFocusGained
        // TODO add your handling code here:
        strNomTarCre=txtNomTarCre.getText();
        txtNomTarCre.selectAll();
    }//GEN-LAST:event_txtNomTarCreFocusGained

    private void txtNomTarCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTarCreFocusLost
        // TODO add your handling code here:
        if (txtNomTarCre.getText().equals("")) {
            txtCodTarCre.setText("");
            txtNomTarCre.setText("");
        }else
        mostrarVenConTarjetaCredito(3);
    }//GEN-LAST:event_txtNomTarCreFocusLost

    private void butConTarCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConTarCreActionPerformed
        // TODO add your handling code here:
        mostrarVenConTarjetaCredito(0);
    }//GEN-LAST:event_butConTarCreActionPerformed

    private void txtCodTipCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipCreActionPerformed
        // TODO add your handling code here:
        txtCodTipCre.transferFocus();
    }//GEN-LAST:event_txtCodTipCreActionPerformed

    private void txtCodTipCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipCreFocusGained
        // TODO add your handling code here:
        strCodTipCre=txtCodTipCre.getText();
        txtCodTipCre.selectAll();
    }//GEN-LAST:event_txtCodTipCreFocusGained

    private void txtCodTipCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipCreFocusLost
        // TODO add your handling code here:
        if (txtCodTipCre.getText().equals("")) {
            txtCodTipCre.setText("");
            txtNomTipCre.setText("");
        }else
        mostrarVenConTipoCredito(1);
    }//GEN-LAST:event_txtCodTipCreFocusLost

    private void txtNomTipCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipCreActionPerformed
        // TODO add your handling code here:
        txtNomTipCre.transferFocus();
    }//GEN-LAST:event_txtNomTipCreActionPerformed

    private void txtNomTipCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipCreFocusGained
        // TODO add your handling code here:
        strNomTipCre=txtNomTipCre.getText();
        txtNomTipCre.selectAll();
    }//GEN-LAST:event_txtNomTipCreFocusGained

    private void txtNomTipCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipCreFocusLost
        // TODO add your handling code here:
        if (txtNomTipCre.getText().equals("")) {
            txtCodTipCre.setText("");
            txtNomTipCre.setText("");
        }else
        mostrarVenConTipoCredito(3);
    }//GEN-LAST:event_txtNomTipCreFocusLost

    private void butConTipCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConTipCreActionPerformed
        // TODO add your handling code here:
        mostrarVenConTipoCredito(0);
    }//GEN-LAST:event_butConTipCreActionPerformed

    private void txtCodEmiTarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmiTarActionPerformed
        // TODO add your handling code here:
        txtCodEmiTar.transferFocus();
    }//GEN-LAST:event_txtCodEmiTarActionPerformed

    private void txtCodEmiTarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmiTarFocusGained
        // TODO add your handling code here:
        strCodEmisor=txtCodEmiTar.getText();
        txtCodEmiTar.selectAll();
    }//GEN-LAST:event_txtCodEmiTarFocusGained

    private void txtCodEmiTarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmiTarFocusLost
        // TODO add your handling code here:
        if (txtCodEmiTar.getText().equals("")) {
            txtCodEmiTar.setText("");
            txtNomEmiTar.setText("");
        }else
        mostrarVenConEmisor(1);
    }//GEN-LAST:event_txtCodEmiTarFocusLost

    private void txtNomEmiTarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmiTarActionPerformed
        // TODO add your handling code here:
        txtNomEmiTar.transferFocus();
    }//GEN-LAST:event_txtNomEmiTarActionPerformed

    private void txtNomEmiTarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmiTarFocusGained
        // TODO add your handling code here:
        strNomEmisor=txtNomEmiTar.getText();
        txtNomEmiTar.selectAll();
    }//GEN-LAST:event_txtNomEmiTarFocusGained

    private void txtNomEmiTarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmiTarFocusLost
        // TODO add your handling code here:
        if (txtNomEmiTar.getText().equals("")) {
            txtCodEmiTar.setText("");
            txtNomEmiTar.setText("");
        }else
        mostrarVenConEmisor(3);
    }//GEN-LAST:event_txtNomEmiTarFocusLost

    private void butConEmiTarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConEmiTarActionPerformed
        // TODO add your handling code here:
        String strSql="";
        if (txtCodTipCre.getText().equals("")){
            this.mostrarMsg("Debe escoger el tipo de credito.");
            return;
        }else{
            if (txtTipCre.getText().equals("C")){
                strSql=" select co_reg as co_emitarcre, tx_deslar from tbm_var where co_grp=8 and st_reg != 'I' order by 1 ";
            }else{
                strSql=" select a.co_emitarcre, b.tx_deslar "
                + " from tbr_EmiRedTarCre as a "
                + " inner join tbm_var as b on (a.co_emitarcre=b.co_reg) "
                + " where a.co_redtarcre = " + txtCodRedTarCre.getText() + " "
                + " and b.st_reg != 'I' "
                + " order by 1 ";
            }
        } 
        objVenConEmisor.setSentenciaSQL(strSql);
        objVenConEmisor.cargarDatos(); 
        mostrarVenConEmisor(0);
    }//GEN-LAST:event_butConEmiTarActionPerformed

    private void txtCodMesCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMesCreActionPerformed
        // TODO add your handling code here:
        txtCodMesCre.transferFocus();
    }//GEN-LAST:event_txtCodMesCreActionPerformed

    private void txtCodMesCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMesCreFocusGained
        // TODO add your handling code here:
        strCodMesCre=txtCodMesCre.getText();
        txtCodMesCre.selectAll();
    }//GEN-LAST:event_txtCodMesCreFocusGained

    private void txtCodMesCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMesCreFocusLost
        // TODO add your handling code here:
        if (txtCodMesCre.getText().equals("")) {
            txtCodMesCre.setText("");
            txtNomMesCre.setText("");
        }else
        mostrarVenConMesesCredito(1);
    }//GEN-LAST:event_txtCodMesCreFocusLost

    private void txtNomMesCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomMesCreActionPerformed
        // TODO add your handling code here:
        txtNomMesCre.transferFocus();
    }//GEN-LAST:event_txtNomMesCreActionPerformed

    private void txtNomMesCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesCreFocusGained
        // TODO add your handling code here:
        strNomMesCre=txtNomMesCre.getText();
        txtNomMesCre.selectAll();
    }//GEN-LAST:event_txtNomMesCreFocusGained

    private void txtNomMesCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesCreFocusLost
        // TODO add your handling code here:
        if (txtNomMesCre.getText().equals("")) {
            txtCodMesCre.setText("");
            txtNomMesCre.setText("");
        }else
        mostrarVenConMesesCredito(3);
    }//GEN-LAST:event_txtNomMesCreFocusLost

    private void butConMesCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConMesCreActionPerformed
        // TODO add your handling code here:
        mostrarVenConMesesCredito(0);
    }//GEN-LAST:event_butConMesCreActionPerformed
  
    
    
    
   private void verificaIvaCli(String stIvaVen ){
     if(stIvaVen.equals("S")){ 
      for(int i=0; i < tblDat.getRowCount(); i++ ){
       if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
         tblDat.setValueAt("S", i, INT_TBL_IVATXT);
         tblDat.setValueAt( true, i, INT_TBL_BLNIVA);
       }} 
     }else{
      for(int i=0; i < tblDat.getRowCount(); i++ ){
       if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
         tblDat.setValueAt("N", i, INT_TBL_IVATXT);
         tblDat.setValueAt( false, i, INT_TBL_BLNIVA);
       }} 
     } 
    }
    
    
    
    /*
   * Obtiene el Iva que se debe cobrar en la empresa actual
   */
   
       
   
   
      private double getIva(java.sql.Connection ConTem){
            double dblIvaActual = 0;
              try{
                    if (ConTem!=null){
                        stmCot=ConTem.createStatement();
                        sSQL= "SELECT nd_ivaVen as porIva from tbm_emp " +
                               "where co_emp = " + objZafParSis.getCodigoEmpresa();  //<== Verificando que seleccione la empresa actual
                        ResultSet rstIva = stmCot.executeQuery(sSQL);
                        if(rstIva.next()){
                            dblIvaActual = objUti.redondeo(rstIva.getDouble("porIva"),2);
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

            txtDoc.setEditable(blnEditable);
            
            colBack = txtSub.getBackground();
      

            txtSub.setEditable(blnEditable);
            txtSub.setBackground(colBack);        
            
            txtIva.setEditable(blnEditable);
            txtIva.setBackground(colBack);        
            
            txtTot.setEditable(blnEditable);
            txtTot.setBackground(colBack);        
    }
  
    public void  clnTextos(){
          if(blnLimpiar){
        //Cabecera
           txtTipDocCod.setText("");
           txtTipDocAli.setText("");
           txtTipDocNom.setText("");
           txtFac.setText("");
           txtGuia.setText("");
           txtDoc.setText("");
           txtAte.setText("");
           txtPed.setText("");
          
           strCodCli="";
              strDesLarCli="";   
              strCodCli="";
              strDesLarCli="";
           
           
            txtCot.setText("");
            txtCliCod.setText("");
            txtCliNom.setText("");
            txtCliDir.setText("");
           // txtFecDoc.setText("");
            txtVenCod.setText("");
            txtVenNom.setText("");
            
        
        //Detalle        
            objTblMod.removeAllRows();         

        //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            //txtDes.setText("0");
            txtTot.setText("0");
            lblCotNumDes.setText("Factura");

         //Detalle  de PAGOS
            while(tblPag.getRowCount()>0)
                ((javax.swing.table.DefaultTableModel)tblPag.getModel()).removeRow(0);            
            
           // Tab de Otros   
            txtPartida.setText("");
            txtTransporte.setText("");
            
         //Tab de Asiento Contable
            objAsiDia.inicializar();
            objAsiDia.setEditable(false);            
            
          } 

    }
   
   public void  calculaTotal(double dblPorIvaCon){
       double PorIva=dblPorIvaCon; 
       double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0, dblSub2=0;
         
        for (int i=0;i<tblDat.getRowCount();i++){ 
             dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));  
             dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
 
           
             if(tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)
                dblIva = dblIva + 0;
            else{
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * PorIva)==0)?0:(dblTmp * PorIva)/100) ;
            }
             
        }
     
        /////////////////////////////////////////////////////////////
        dblSubtotalCot = dblSub;
        dblIvaCot = objUti.redondear(dblIva,intNumDec);
        dblTotalCot = dblSubtotalCot + dblIvaCot;
        dblTotalCot = objUti.redondear(dblTotalCot ,intNumDec);
        dblSubtotalCot = objUti.redondear(dblSubtotalCot ,intNumDec);
     
       
        
       if(stIvaVen.equals("N") ){
             txtSub.setText( "" + dblSubtotalCot );
             txtIva.setText( "0.00" );
             txtTot.setText( ""+ dblSubtotalCot );
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
                  java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   if (conUni!=null){
                        java.sql.Statement stmUni = conUni.createStatement();
                         String sSQL= "SELECT var.tx_descor from tbm_inv as inv " +
                                        " inner join tbm_var as var on (inv.co_uni = var.co_reg) "+ 
                                        " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                                        "   var.co_grp = 5  and inv.co_itm = " + int_co_itm ; 
                        java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                        if(rstUni.next())
                            strDesUni = (rstUni.getString("tx_descor")==null)?"":rstUni.getString("tx_descor");
                        rstUni.close();
                        stmUni.close();
                        conUni.close();
                        rstUni = null;
                        stmUni = null;
                        conUni = null;
                    }
           }
           catch(java.sql.SQLException Evt) {  System.out.println(Evt); return strDesUni+"";  }
           catch(Exception Evt) {  System.out.println(Evt);  return strDesUni+"";  }                  
           return strDesUni;
    }  
   
   
   
    
   
    public void  refrescaDatos(){  
        try{
            int intNumCot = 0;
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());                                                                       
                if(rstCab != null){
                //Detalle        
                    intNumCot = rstCab.getInt("co_doc");
                    
                    
                    String strAux = ",CASE WHEN (" +
                   " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                   " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                   " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                   " ))) THEN 'S' ELSE 'N' END  as isterL";

                    
                     
                     sSQL  = "SELECT a.tx_codalt, a.tx_unimed, a.tx_nomitm, a.co_bod, a.nd_can, a.nd_preuni , inv.st_ser " +
                     " ,a.nd_pordes,  inv.st_ivaven as st_ivaCom, a.co_itm, a.tx_codalt2 ";
                     sSQL +=strAux;
                     //sSQL +=" ,tr.co_emprel, tr.co_locrel ,tr.co_tipdocrel , tr.co_docrel  ,tr.co_regrel "
                     sSQL +="  FROM tbm_detMovInv AS a  " +
                     " INNER JOIN tbm_inv AS inv on (a.co_emp = inv.co_emp and a.co_itm = inv.co_itm) " +
                    // " LEFT JOIN tbr_detmovinv AS tr ON (tr.co_emp=a.co_emp and tr.co_loc=a.co_loc and tr.co_tipdoc=a.co_tipdoc and tr.co_doc=a.co_doc and tr.co_reg=a.co_reg) "+
                     "WHERE " +
                                " a.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                " a.co_loc = " + intLocalFac   + " and " +
                                " a.co_doc = " + intNumCot                       + " and " +
                                " a.co_tipDoc = " + txtTipDocCod.getText() + " order by a.co_reg";
                    
                    
                  
                    // System.out.println("TOTAL >>  "+ sSQL );
                     
                    java.sql.Statement stmCab=con.createStatement();
                    java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                     double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                                           
                    java.sql.Statement stmAux;
                    java.sql.ResultSet rstAux;
                    stmAux = con.createStatement();
                    String strUnidad="",strCodAlt="",strSer="", strTer="";
                    Vector vecData = new Vector();
                    for(int i=0 ; rst.next() ; i++){
                         java.util.Vector vecReg = new java.util.Vector();
                        
                             strCodAlt =  rst.getString("tx_codalt"); // (rstAux.getString("tx_codalt")==null?"":rstAux.getString("tx_codalt"));
                             strUnidad = rst.getString("tx_unimed"); // (rstAux.getString("tx_descor")==null?"":rstAux.getString("tx_descor"));
                             strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                             strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                             

                         vecReg.add(INT_TBL_LINEA, "");
                         vecReg.add(INT_TBL_ITMALT, strCodAlt);
                         vecReg.add(INT_TBL_BUTITM, "");
                         vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                         vecReg.add(INT_TBL_UNIDAD, strUnidad);
                         vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                         vecReg.add(INT_TBL_BUTBOD, "");
                         dblCan    = Math.abs(rst.getDouble("nd_can")) ;
                         vecReg.add(INT_TBL_CANMOV, new Double(dblCan));                                                                           
                        
                         vecReg.add(INT_TBL_STKBOD,"");
                         vecReg.add(INT_TBL_ULTCOS,"");
                          
                         vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                        
                          vecReg.add(INT_TBL_UTILIDAD,"");
                          vecReg.add(INT_TBL_PREUNI2,new Double(rst.getDouble("nd_preuni")) );
                         
                         vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                         
                         String strIva = rst.getString("st_ivaCom");
                         
                         if(stIvaVen.equals("S"))
                            vecReg.add(INT_TBL_BLNIVA, strIva.equals("S")?true:false);                     
                         else
                            vecReg.add(INT_TBL_BLNIVA, false); 
                         
                         
                         
                       
                         dblPre    = rst.getDouble("nd_preuni");
                         dblPorDes = rst.getDouble("nd_pordes");
                        
                         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                         dblTotal  = (dblCan * dblPre)- dblValDes ;
                         //*********************System.out.println("TOTAL >>  "+dblValDes +" >> "+ dblTotal);
                         dblTotal =  objUti.redondear(dblTotal,3);
                         dblTotal =  objUti.redondear(dblTotal,2);
                         
                         
                         vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                         vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                         vecReg.add(INT_TBL_ESTADO, "E");
                         vecReg.add(INT_TBL_IVATXT, "");
                        
                          vecReg.add(INT_TBL_BLNPRE,"");
                          vecReg.add(INT_TBL_CANORI,new Double(dblCan));
                          vecReg.add(INT_TBL_BODORI,new Integer(rst.getInt("co_bod")));
                          vecReg.add(INT_TBL_CODORI,rst.getString("co_itm"));
                          
                         vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                         vecReg.add(INT_TBL_ITMSER, strSer );
                         vecReg.add(INT_TBL_ITMTER, strTer );
                           
			 
			 vecReg.add(INT_TBL_CODEMPREL, null );  // rst.getString("co_emprel") );
                         vecReg.add(INT_TBL_CODLOCREL, null );  // rst.getString("co_locrel") );
                         vecReg.add(INT_TBL_CODTIPREL, null );  //  rst.getString("co_tipdocrel") );
                         vecReg.add(INT_TBL_CODDOCREL, null );  // rst.getString("co_docrel") );
                         vecReg.add(INT_TBL_CODREGREL, null );  // rst.getString("co_regrel") );
             
                              
                         vecData.add(vecReg);                         
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);                                 
                    stmAux.close();
                    rst.close();
                  //  System.out.println("DETALLE>>>>OKI ");
                    calculaTotal(dblPorIva);
                 //  System.out.println("DETALLE>>>>OKI 1 ");
                   
                    lblCotNumDes.setText("Factura No. " + txtFac.getText() +  " (" + txtCliNom.getText() + ") ");
                    lblIva.setText("IVA " + dblPorIva + "%");
                    
                  //  System.out.println("DETALLE>>>>OKI 3 ");
                    
                 /*
                  * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */
                   // refrescaPagos(intNumCot, rstDatosDocCab.getInt("co_forPag"));
                    String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag");
                    

                        //txtCodForPag.setText(""+strCo_ForPag);
                        //txtNomForPag.setText(""+ rstCab.getString("tx_des")); 

                  
                    CalculoPago();

                    
                
                
                  // refrescaPagos(con, intNumCot, rstCab.getInt("co_forPag"));
                      
                                  
                      
                    /*
                     * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                     */
                   objAsiDia.consultarDiario(objZafParSis.getCodigoEmpresa(), intLocalFac , Integer.parseInt(txtTipDocCod.getText()), intNumCot);
                        
                      
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                    String strStatus = rstCab.getString("st_reg");
                   

                    
                   
                    stmAux.close();
                    stmCab.close();
                    con.close();                    
                }
           

        }//fin Try
       catch(SQLException Evt)
       {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
              
        }

        catch(Exception Evt)
        {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                       
      
    }
    
    
      public void refrescaPagos_old(java.sql.Connection con, int intNumeroDocumento, int intCo_ForPag){
      
        try{       
                    java.sql.Statement stmDetPag = con.createStatement();
                    java.sql.ResultSet rstDetPag ;
                    String strCo_ForPag = intCo_ForPag+"";
                    /*
                     * LLenando el combo de descripciones de pagos 
                     */
                  

                    //Extrayendo los datos del detalle respectivo a ESTE PAGO
                    String sqlDetPag  = "Select * from tbm_pagMovInv where " +
                                        " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        " co_loc = " + intLocalFac  + " and " +
                                        " co_doc = " + intNumeroDocumento              + " and " +
                                        " co_tipDoc =" + txtTipDocCod.getText()        + " and st_reg IN ('A','C') order by co_reg";
                                
                    rstDetPag = stmDetPag.executeQuery(sqlDetPag);
                    
                        
                    //Borrando los datos actuales del jtable
                    
                    
                    
                    /*
                     * LLenando la tabla de pagos
                     */

                    java.util.Calendar calFecPag = java.util.Calendar.getInstance();
                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                        
                    while(rstDetPag.next()){
                        
                        java.util.Vector dataPagos = new java.util.Vector();

                        dataPagos.addElement(new Object());
                        dataPagos.addElement(rstDetPag.getObject("ne_diaCre"));
                        /*
                         * Formateando la fecha que viene desde la base para presentarla en d/m/y
                         */
                                calFecPag.setTime(rstDetPag.getDate("fe_ven"));
                                dtePckPag.setAnio(calFecPag.get(java.util.Calendar.YEAR));
                                dtePckPag.setMes((calFecPag.get(java.util.Calendar.MONTH)+1));
                                dtePckPag.setDia(calFecPag.get(java.util.Calendar.DAY_OF_MONTH));
                        
                        dataPagos.addElement(dtePckPag.getText());
                        dataPagos.addElement(rstDetPag.getObject("nd_porRet"));
                        dataPagos.addElement((rstDetPag.getDouble("mo_pag") * -1)+ "" );
                        dataPagos.addElement(rstDetPag.getObject("ne_diaGra"));
                        dataPagos.addElement(rstDetPag.getObject("co_tipret"));
                        dataPagos.addElement(rstDetPag.getString("st_sop"));
                         
                        ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(dataPagos);
                     }

                    rstDetPag.close();  
                    stmDetPag.close();
        }
       catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); }
       catch(Exception Evt)  { objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    
    
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        //JOptionPane oppMsg=new JOptionPane();
        /*String strTit;
        strTit="Mensaje del sistema Zafiro";*/
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
//                switch (objTooBar.getEstado())
//                {
//                    case 'n': //Insertar
//                        blnRes=objTooBar.insertar();
//                        break;
//                    case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
//                        break;
//                }
                break;
            case 1: //NO_OPTION
               
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
  
  
      
      
      //*****************************/*//////////////////////////////////////////////////////////////
      
      
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                refrescaDatos();     
                
                cboMotivoTransporte.setSelectedIndex(0);

            }
            else
            {
                MensajeInf("Error al cargar registro");
              
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
        int intTipForPag=0;
        boolean blnRes=true;
        try
        {
            Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";
               
                 int intNumCot = 0;
                intNumCot = rstCab.getInt("co_doc");
                intTipForPag=rstCab.getInt("ne_tipforpag");
                
                 /*strSQL= "SELECT  DocCab.ne_numorddes, DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                          " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                          " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                          " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numped, DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                          " DocCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                          " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                          " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                          " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra, DocCab.nd_poriva, DocCab.ne_secgrp "   + //Campos del Tab OTROS 
                          " ,DocCab.st_regrep  ,Cli.tx_tipper , Cli.co_tipper " +
                          " , Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar,  Cli.st_ivaven " +
                          " FROM tbm_cabMovInv as DocCab " +
                          " LEFT JOIN  tbm_cli AS Cli on (cli.co_emp = DocCab.co_emp  AND Cli.co_cli = DocCab.co_cli) " + 
                          " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) " +
                          " Where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                          "        and DocCab.co_loc = " + intLocalFac + 
                          "   and DocCab.st_reg not in ('E')  and DocCab.co_doc = " + intNumCot +// Consultando en el local en el ke se esta trabajando
                          "        and DocCab.co_tipdoc = " + rstCab.getInt("co_tipdoc") ;// Consultando en el local en el ke se esta trabajando*/
                
                 /*strSQL= " SELECT  DocCab.ne_numorddes, DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                          " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                          " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                          " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numped, DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                          " DocCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                          " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                          " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                          " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra, DocCab.nd_poriva, DocCab.ne_secgrp, "   + //Campos del Tab OTROS 
                          " DocCab.st_regrep  ,Cli.tx_tipper , Cli.co_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar,  Cli.st_ivaven " +
                          " FROM tbm_cabMovInv as DocCab " +
                          " LEFT JOIN  tbm_cli AS Cli on (cli.co_emp = DocCab.co_emp  AND Cli.co_cli = DocCab.co_cli) " + 
                          " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) " +
                          " where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                          " and DocCab.co_loc = " + intLocalFac + 
                          " and DocCab.st_reg not in ('E')  and DocCab.co_doc = " + intNumCot +// Consultando en el local en el ke se esta trabajando
                          " and DocCab.co_tipdoc = " + rstCab.getInt("co_tipdoc") ;// Consultando en el local en el ke se esta trabajando                 */
                
                 strSQL= " SELECT  DocCab.ne_numorddes, DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                        " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                        " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                        " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numped, DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                        " DocCab.co_forPag, forpag.tx_des as desForPag, forpag.ne_tipforpag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                        " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                        " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                        " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra, DocCab.nd_poriva, DocCab.ne_secgrp, "   + //Campos del Tab OTROS 
                        " DocCab.st_regrep  ,Cli.tx_tipper , Cli.co_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar,  Cli.st_ivaven ";
                        if (intTipForPag==4){ 
                           strSQL+=" ,DocCab.co_tarCre, a1.tx_deslar as nomTarCre, DocCab.co_redTarCre, a2.tx_deslar as nomRedTarCre, "
                                   + " DocCab.co_tipCre, a3.tx_deslar as nomTipCre, a3.tx_tipcre, a3.ne_mesgra, a3.nd_pordesven, "
                                   + " DocCab.co_emiTarCre, a4.tx_deslar as nomEmiTar, DocCab.ne_mesCre, a3.ne_mesgra, DocCab.nd_porComTarCre, DocCab.nd_porDesVenTarCre ";
                        }
                        strSQL+=" FROM tbm_cabMovInv as DocCab " +
                        " LEFT JOIN  tbm_cli AS Cli on (cli.co_emp = DocCab.co_emp  AND Cli.co_cli = DocCab.co_cli) " + 
                        " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) " +
                        " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag ) " ;
                        if (intTipForPag==4){ 
                            strSQL+=" left outer join tbm_tarCre as a1 on (a1.co_tarcre=DocCab.co_tarCre ) " +   
                                    " left outer join tbm_redTarCre as a2 on (a2.co_redtarcre=DocCab.co_redTarCre ) " + 
                                    " left outer join tbm_tipCreRedTarCre as a3 on (a3.co_emp=DocCab.co_emp and a3.co_redtarcre=DocCab.co_redTarCre and a3.co_tipcre=DocCab.co_tipCre ) " + 
                                    " left outer join tbm_var as a4 on (a4.co_reg=DocCab.co_emiTarCre) " ;
                        }
                        strSQL+=" where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                        " and DocCab.co_loc = " + intLocalFac + 
                        " and DocCab.st_reg not in ('E')  and DocCab.co_doc = " + intNumCot +// Consultando en el local en el ke se esta trabajando
                        " and DocCab.co_tipdoc = " + rstCab.getInt("co_tipdoc") ;// Consultando en el local en el ke se esta trabajando                 

                ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())  {                   
                       dblPorIva=rst.getDouble("nd_poriva");  //)?0:rst.getDouble("nd_poriva");
                     
                        STR_ESTREG=rst.getString("st_regrep");
                        txtDoc.setText(""+intNumCot);
                        txtTipDocCod.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
                        txtCot.setText(""+rst.getInt("ne_numCot"));
                        txtFac.setText(""+rst.getInt("ne_numDoc"));
                        txtPed.setText(((rst.getString("tx_numped")==null)?"":rst.getString("tx_numped")+""));
                        txtGuia.setText(""+rst.getInt("ne_numGui"));
                        txtOD.setText(""+rst.getInt("ne_numorddes"));
                        intCodCliGrp=rst.getInt("co_cli");
                        txtCliCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                        txtCliNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                        txtCliDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                       
                        Glo_intCodSec = rst.getInt("ne_secgrp");
                        
                        txtcli_telefono.setText( rst.getString("tx_tel") );
                        txtcli_identificacion.setText( rst.getString("tx_ide") );
                        txtcli_ciudad.setText( rst.getString("tx_desLar") );                        
                       
                   // String sql = "select tx_tipper from tbm_cli where co_cli="+ txtCliCod.getText() +" and co_emp="+objZafParSis.getCodigoEmpresa();
                   // java.sql.ResultSet rst2=stm.executeQuery(sql);
                  //   if (rst2.next())
                         strTipPer_glo=rst.getString("tx_tipper");
                         strCodTipPerCli =  rst.getString("co_tipper");
                    
                         stIvaVen =  rst.getString("st_ivaven");
                    
                //    rst2.close();
               //     rst2=null;
                    
                    //*****************
                   
                     dateFecCot = objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy");
                     intTipMod = 2; 
                    
                        txtVenCod.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                        txtVenNom.setText(((rst.getString("nomven")==null)?"":rst.getString("nomven")));
                        txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));                                        
                        
                   //Pie de pagina
                     txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                     txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));                    
                     
                      /*
                     * CARGANDO DATOS DEL TAB MOTIVO DE TRANSPORTE
                     */
                        txtPartida.setText(((rst.getString("tx_ptoPar")==null)?"":rst.getString("tx_ptoPar")));
                        txtTransporte.setText(((rst.getString("tx_tra")==null)?"":rst.getString("tx_tra")));
                        cboMotivoTransporte.setSelectedIndex(vecTransporte.indexOf(rst.getInt("co_motTra")+""));                        
                     
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    this.Glo_dlbMaxDes=objUti.redondear(objUti.parseDouble((rst.getString("nd_maxdes")==null)?"0":rst.getString("nd_maxdes") ), 2 ); 
                    this.intTipForPag2=rst.getInt("ne_tipforpag");
                    
                    txtCodForPag.setText(rst.getString("co_forPag"));
                    txtNomForPag.setText(rst.getString("desForPag"));                    
                    txtCodigoFormaPago.setText(txtCodForPag.getText());
                    
                    /** TARJETA DE CREDITO. */ 
                    if (intTipForPag==4){
                        txtCodTarCre.setText(rst.getString("co_tarCre"));
                        txtNomTarCre.setText(rst.getString("nomTarCre"));

                        txtCodRedTarCre.setText(rst.getString("co_redTarCre"));
                        txtCodTipCre.setText(rst.getString("co_tipCre")); 
                        txtNomTipCre.setText(rst.getString("nomTipCre"));
                        txtTipCre.setText(rst.getString("tx_tipcre"));
                        dblPorDesTarCre= objUti.redondear(objUti.parseDouble((rst.getString("nd_pordesven")==null)?"0":rst.getString("nd_pordesven") ), 2 ); 

                        txtCodEmiTar.setText(rst.getString("co_emiTarCre"));
                        txtNomEmiTar.setText(rst.getString("nomEmiTar"));

                        txtCodMesCre.setText(rst.getString("ne_mesCre")==null?"":rst.getString("ne_mesCre") ); 
                        txtNomMesCre.setText(rst.getString("ne_mesCre")==null?"":rst.getString("ne_mesCre") + " Meses ");
                        txtMesGra.setText(rst.getString("ne_mesgra")); 
                        
                        txtPorComTarCre.setText(rst.getString("nd_porComTarCre") ); 
                        txtPorDesVenTarCre.setText(rst.getString("nd_porDesVenTarCre") ); 
                    }else{
                        txtCodTarCre.setText("");
                        txtNomTarCre.setText("");

                        txtCodRedTarCre.setText("");
                        txtCodTipCre.setText(""); 
                        txtNomTipCre.setText("");
                        txtTipCre.setText("");
                        dblPorDesTarCre= 0; 

                        txtCodEmiTar.setText("");
                        txtNomEmiTar.setText("");

                        txtCodMesCre.setText(""); 
                        txtNomMesCre.setText("");
                        txtMesGra.setText("");                        
                        
                        txtPorComTarCre.setText(""); 
                        txtPorDesVenTarCre.setText(""); 
                    }
                    
                    strstReg_glo = strAux; 
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                  //  objTooBar.setEstadoRegistro(strAux);
                }else {
                   // objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
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
               //objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
            }
        }catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }catch (Exception e) {  
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        return blnRes;
    }  
      
public boolean insertarReg(){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intNumFac=0;
 calculaPagos();
 try{
  if (CONN_GLO!=null){
   dblPorIva = objUti.redondeo(getIva(CONN_GLO),2);
   int intCodDoc = getUltCoDoc(CONN_GLO);
   stmLoc=CONN_GLO.createStatement();
   
     if(intCodDoc !=-1 ){
      if(insertarCab(CONN_GLO, intCodDoc )){
       if(insertarDet(CONN_GLO, con_remota, intCodDoc )){
        if(insertDetPag(intCodDoc, CONN_GLO )){ 
         if(objAsiDia.insertarDiario_OC(CONN_GLO,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),Integer.parseInt(txtTipDocCod.getText()) , String.valueOf(intCodDoc),  String.valueOf(intCodDoc) , objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), 14 ) ){
           blnRes = true;
           txtDoc.setText(intCodDoc+"");
           //********************************************************************
            strSql="SELECT ne_ultDoc, st_predoc  FROM tbm_cabTipDoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+ 
            " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+txtTipDocCod.getText();
            rstLoc=stmLoc.executeQuery(strSql);
            if(rstLoc.next()){  
              intNumFac = rstLoc.getInt("ne_ultDoc");
            }
            rstLoc.close();
            rstLoc=null;
            intNumFac++;
            
            strSql="UPDATE tbm_cabTipDoc SET ne_ultDoc="+intNumFac+" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND "+  
            " co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+txtTipDocCod.getText();      
            strSql +="; UPDATE tbm_cabmovinv SET ne_numdoc="+intNumFac+", st_reg='A' " +
            " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtTipDocCod.getText()+" AND co_doc="+intCodDoc;
            stmLoc.executeUpdate(strSql);      

            txtFac.setText(""+intNumFac);
            strCodDocFac=String.valueOf(intCodDoc);
            strNumFac=String.valueOf(intNumFac);  
           //********************************************************************
        }}}}
    
     }
    
    objInvItm.limpiarObjeto();
   stmLoc.close();
   stmLoc=null;
  }}catch(SQLException  Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }               
   return blnRes;  
}




private boolean insertarCab(Connection conIns, int intCodDoc ){
 boolean blnRes = false;
 Statement stmLoc;
 String strSql="";
 try{
     //******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
     int intSecGrp = 0;
     int intSecEmp = 0;
//     intSecEmp = objUltDocPrint2.getNumeroOrdenEmpresa(conIns);
//     intSecGrp = objUltDocPrint2.getNumeroOrdenGrupo(conIns);
     Glo_intCodSec = intSecGrp;
     //************************************************************************************   
                                       
     stmLoc = conIns.createStatement(); 
     /*strSql = "INSERT INTO  tbm_cabMovInv (co_emp, co_loc, co_doc, co_tipDoc, " + 
     " ne_secemp, ne_secgrp, fe_doc, co_cli, co_com, tx_ate, " +
     " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, "  +    
     " ne_numDoc, ne_numCot, tx_numped, ne_numGui, " +
     " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot, nd_valiva, " +
     " co_forPag, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, "   + 
     " fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_desforpag, st_reg , st_regrep, ne_numdocree, st_coninv, ne_numorddes  )"+
     " VALUES("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+intCodDoc+", "+
     " "+txtTipDocCod.getText()+","+intSecEmp+", "+intSecGrp +", '"+strFecSis+"',"+txtCliCod.getText()+","+ 
     " "+txtVenCod.getText()+", '"+txtAte.getText()+"', '"+txtCliNom.getText()+"', '"+txtCliDir.getText()+"',"+ 
     " '"+txtcli_identificacion.getText()+"', '"+txtcli_telefono.getText()+"','"+txtcli_ciudad.getText()+"','"+txtVenNom.getText()+"',"+
     " 0, '"+((txtCot.getText().equals(""))?"0":txtCot.getText()) +"', '"+((txtPed.getText().equals(""))?"0":txtPed.getText())+"',"+
     " "+(txtGuia.getText().equals("")?"0":txtGuia.getText())+", '"+txaObs1.getText()+"','"+txaObs2.getText()+"',"+objUti.redondeo(dblSubtotalCot, objZafParSis.getDecimalesBaseDatos())* -1 +","+
     objUti.redondeo(dblPorIva , 2)+", "+objUti.redondeo(dblTotalCot , objZafParSis.getDecimalesBaseDatos())* -1 +","+
     Double.parseDouble(txtIva.getText())* -1 +","+txtCodForPag.getText()+","+
     " '"+txtPartida.getText()+"', '"+txtTransporte.getText()+"', "+
     Integer.parseInt(vecTransporte.get(cboMotivoTransporte.getSelectedIndex())+"")+","+
     " '"+cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex())+"', "+
     " "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ,"+objZafParSis.getCodigoUsuario()+","+
     objZafParSis.getCodigoUsuario()+", '"+txtNomForPag.getText()+"', 'A' ,'I', "+txtFac.getText()+" , null, "+(txtOD.getText().equals("")?"0":txtOD.getText())+" ) ";*/
     
     strSql = "INSERT INTO  tbm_cabMovInv (co_emp, co_loc, co_doc, co_tipDoc, " + 
     " ne_secemp, ne_secgrp, fe_doc, co_cli, co_com, tx_ate, " +
     " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, "  +    
     " ne_numDoc, ne_numCot, tx_numped, ne_numGui, " +
     " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot, nd_valiva, " +
     " co_forPag, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, "   + 
     " fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_desforpag, st_reg , st_regrep, ne_numdocree, st_coninv, ne_numorddes "; 
    if (intTipForPag2 == 4) {
        strSql+=" , co_tarCre, co_redTarCre, co_tipCre, co_emiTarCre, ne_mesCre, nd_porComTarCre, nd_porDesVenTarCre ";
    }
     strSql+=" )";
     strSql+=" VALUES("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+intCodDoc+", "+
     " "+txtTipDocCod.getText()+","+intSecEmp+", "+intSecGrp +", '"+strFecSis+"',"+txtCliCod.getText()+","+ 
     " "+txtVenCod.getText()+", '"+txtAte.getText()+"', '"+txtCliNom.getText()+"', '"+txtCliDir.getText()+"',"+ 
     " '"+txtcli_identificacion.getText()+"', '"+txtcli_telefono.getText()+"','"+txtcli_ciudad.getText()+"','"+txtVenNom.getText()+"',"+
     " 0, '"+((txtCot.getText().equals(""))?"0":txtCot.getText()) +"', '"+((txtPed.getText().equals(""))?"0":txtPed.getText())+"',"+
     " "+(txtGuia.getText().equals("")?"0":txtGuia.getText())+", '"+txaObs1.getText()+"',"+objUti.codificar("Reemplazo de Factura. Factura Origen #" + txtFac.getText() )+","+
     objUti.redondeo(dblSubtotalCot, objZafParSis.getDecimalesBaseDatos())* -1 +","+
     objUti.redondeo(dblPorIva , 2)+", "+objUti.redondeo(dblTotalCot , objZafParSis.getDecimalesBaseDatos())* -1 +","+
     Double.parseDouble(txtIva.getText())* -1 +","+txtCodForPag.getText()+","+
     " '"+txtPartida.getText()+"', '"+txtTransporte.getText()+"', "+
     Integer.parseInt(vecTransporte.get(cboMotivoTransporte.getSelectedIndex())+"")+","+
     " '"+cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex())+"', "+
     " "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getFuncionFechaHoraBaseDatos()+" ,"+objZafParSis.getCodigoUsuario()+","+
    objZafParSis.getCodigoUsuario()+", '"+txtNomForPag.getText()+"', 'A' ,'I', "+txtFac.getText()+" , null, "+(txtOD.getText().equals("")?"0":txtOD.getText()) + " ";
    if (intTipForPag2 == 4) { 
        strSql+=" ," + objUti.codificar(txtCodTarCre.getText() ) + ", " + 
                objUti.codificar(txtCodRedTarCre.getText() )  + ", " + 
                objUti.codificar(txtCodTipCre.getText() )  + ", " + 
                objUti.codificar(txtCodEmiTar.getText() )  + ", " + 
                objUti.codificar(txtCodMesCre.getText() ) + ", " +
                objUti.codificar(txtPorComTarCre.getText() ) + ", " +
                objUti.codificar(txtPorDesVenTarCre.getText() ) + " ";
    }
     strSql+=" ) ";
     
     stmLoc.executeUpdate(strSql);

     stbDocDevEmp.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp, "+objZafParSis.getCodigoLocal()+" AS coloc, "+txtTipDocCod.getText()+" AS cotipdoc, "+intCodDoc+" AS codoc ");

     stmLoc.close();
     stmLoc=null;
     blnRes=true;
 
  }catch(java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;                      
}
       


/**
 * RELACIONAR EN TBR_DETMOVINV LA NUEVA FACTURA CON LOS DOCUMENTOS INTERMEDIOS ANTERIORES. 
 * CASO OPCION CLIENTE RETIRA, NO SE PUEDE CONFIRMAR EL EGRESO EN OTRA BODEGA.
 * USO DE LA FUNCION objUti.codificar  PARA LA DESCRIPCION DE ITEM. 
 */
private boolean insertarDet(Connection conIns, java.sql.Connection con_remota, int intCodDoc){
 boolean blnRes = false;
 Statement stmLoc;
 StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
 StringBuffer stbTbrDet =new StringBuffer(); //VARIABLE TIPO BUFFER
 String strSql="";
 int intest=0; 
 int intTipCli=3;
 int intControl=0;
 int intCon=0;
 int intCodItmAct=0;
 int intCodBod=0;
 int intReaActStk=0;
 double dlbcostouni=0.00;
 double bldcanmov=0.00; 
 double bldcostot=0.00;
 double dlbCan=0.00;
 try{
   intTipCli= objUltDocPrint2.ValidarCodigoCliente(txtCliCod.getText(),conIns);
   GLO_strArreItm="";
   stmLoc=conIns.createStatement();                         
   strSql ="UPDATE tbr_detMovInv SET st_reg='E', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND "+  
   " co_loc="+intLocalFac+" AND co_doc="+strCodDoc+" AND co_tipDoc="+strCodTipDoc;
   stmLoc.executeUpdate(strSql);
   
   objInvItm.limpiarObjeto();
   objInvItm.inicializaObjeto();
     
   for(int i=0; i<tblDat.getRowCount();i++){
    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
     intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM)==null || tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString());
     intCodBod = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));                     
     dlbCan = Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV));
                     
     if( objInvItm.isItmServicio(conIns, intCodItmAct) ){ 
        objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItmAct, intCodBod, dlbCan, -1, "", "", "", 1);      
        intReaActStk=1;
     }
                          
    dlbcostouni = objInvItm.getCostoItm(conIns, objZafParSis.getCodigoEmpresa(), intCodItmAct);
    bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV ).toString());
    bldcostot = (dlbcostouni * bldcanmov);
    bldcostot = (bldcostot*-1);
    bldcanmov = (bldcanmov * -1);   
    if(intControl!=0) GLO_strArreItm=GLO_strArreItm+",";
    GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
    intControl++;
    
    if(i>0)
     stb.append(" UNION ALL ");
     stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtTipDocCod.getText()+", "+intCodDoc+", "+
     (i+1)+", '"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"', '"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"', "+
     tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM)+", "+objUti.codificar(tblDat.getValueAt(i, INT_TBL_DESITM) )+",'"+
     tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+tblDat.getValueAt(i, INT_TBL_CODBOD)+", "+bldcanmov+", "+
     objUti.redondear( ((tblDat.getValueAt(i,  INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i,  INT_TBL_TOTAL).toString()), 2)*-1+", "+
     dlbcostouni+", "+bldcostot+", "+objUti.redondear( ((tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), objZafParSis.getDecimalesBaseDatos() )+", "+
     objUti.redondear( ((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()), 2)+", "+
     " '"+((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"', 'P',"+bldcostot+",'I', "+bldcanmov+" ");
     intest=1;

     /*stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtTipDocCod.getText()+", "+intCodDoc+", "+
     (i+1)+", '"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"', '"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"', "+
     tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM)+", '"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+
     tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+tblDat.getValueAt(i, INT_TBL_CODBOD)+", "+bldcanmov+", "+
     objUti.redondear( ((tblDat.getValueAt(i,  INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i,  INT_TBL_TOTAL).toString()), 2)*-1+", "+
     dlbcostouni+", "+bldcostot+", "+objUti.redondear( ((tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), objZafParSis.getDecimalesBaseDatos() )+", "+
     objUti.redondear( ((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()), 2)+", "+
     " '"+((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"', 'P',"+bldcostot+",'I', "+bldcanmov+" ");
     intest=1;*/

     /*if(tblDat.getValueAt(i, INT_TBL_CODDOCREL) != null){
       strSql="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel, co_emprel )"+
       " VALUES("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+txtTipDocCod.getText()+","+intCodDoc+","+
       (i+1)+",'A',"+tblDat.getValueAt(i, INT_TBL_CODLOCREL).toString()+","+
       tblDat.getValueAt(i, INT_TBL_CODTIPREL).toString()+","+tblDat.getValueAt(i, INT_TBL_CODDOCREL).toString()+", " +
       " "+tblDat.getValueAt(i, INT_TBL_CODREGREL).toString()+", "+tblDat.getValueAt(i, INT_TBL_CODEMPREL).toString()+" ) ;";
       stbTbrDet.append(strSql);
       intCon=1;
     }*/
   }} 
   
  if(intReaActStk==1){
    if(!objInvItm.ejecutaActStock(conIns, intTipCli))
      return false;
    if(con_remota!=null){
      if(!objInvItm.ejecutaActStock(con_remota, intTipCli))
       return false;
      if(!objInvItm.ejecutaVerificacionStock(con_remota, intTipCli))
       return false; 
    }else{
      if(!objInvItm.ejecutaVerificacionStock(conIns, intTipCli))
        return false; 
  }}
  objInvItm.limpiarObjeto();
   
  if(intest==1){
    strSql="INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ 
    " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, co_bod, "+
    " nd_can, nd_tot, nd_cosUnigrp, nd_costot, nd_preUni, nd_porDes, st_ivaCom "+
    " ,st_reg , nd_costotgrp , st_regrep, nd_cancon ) " +stb.toString();
    stmLoc.executeUpdate(strSql);
    
    if(intCon==1){
      System.out.println(" <> "+ stbTbrDet.toString() );
      java.sql.PreparedStatement tbrMovInv = conIns.prepareStatement(stbTbrDet.toString()); 
      tbrMovInv.executeUpdate();
    }
    
    strSql=" UPDATE tbm_detguirem SET co_locrel="+objZafParSis.getCodigoLocal()+", co_docrel="+intCodDoc+", st_regrep='M' where co_emprel="+objZafParSis.getCodigoEmpresa()+" " +
    " and co_locrel="+intLocalFac+" and co_tipdocrel="+strCodTipDoc+" and co_docrel= "+strCodDoc+"";
    stmLoc.executeUpdate(strSql);

    
    strSql="INSERT INTO tbr_Cabmovinv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_locrel, co_tipdocrel, co_docrel, st_regrep , co_emprel) " +
    " SELECT "+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+strCodTipDoc+","+intCodDoc+",'A', co_locrel, co_tipdocrel, co_docrel,'I', co_emprel from tbr_cabmovinv " +
    " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+intLocalFac+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc; 
    stmLoc.executeUpdate(strSql);
   
    strSql="UPDATE tbr_Cabmovinv SET st_reg='I' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
    " AND co_loc="+intLocalFac+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc; 
    stmLoc.executeUpdate(strSql);
    
    // RELACIONAR EN TBR_DETMOVINV LA NUEVA FACTURA CON LOS DOCUMENTOS INTERMEDIOS ANTERIORES. CASO OPCION CLIENTE RETIRA, NO SE PUEDE CONFIRMAR EL EGRESO EN OTRA BODEGA. 
    strSql=" insert into tbr_detmovinv "
         + " select co_emp, co_loc, co_tipdoc, " + intCodDoc + " as co_doc, co_reg, 'A' as st_reg, co_locrel, co_tipdocrel, co_docrel, co_regrel, 'I' as st_regrep, co_emprel "
         + " from tbr_detmovinv "
         + " where co_emp="+objZafParSis.getCodigoEmpresa()
         + " and co_loc="+intLocalFac 
         + " and co_doc="+strCodDoc 
         + " and co_tipDoc="+strCodTipDoc 
         + " "; 
    stmLoc.executeUpdate(strSql);      
    
 }
  stmLoc.close();
  stmLoc=null;
  blnRes=true;
 }catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
  catch (Exception e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
 return blnRes;                  
}
 
private boolean insertDetPag(int intCodDoc, java.sql.Connection conIns ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strFechaPag="";
 String strSop="";
 String strSql="";
 try{
  stmLoc=conIns.createStatement();   
  for(int i=0; i<tblPag.getRowCount();i++){

   int FecPagDoc[] =  txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString());
   strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
   strSop = ((tblPag.getValueAt(i, INT_TBL_PAGSOP)==null)?"":tblPag.getValueAt(i, INT_TBL_PAGSOP).toString() );
   strSql="INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_doc, co_tipDoc, co_reg, ne_diaCre, fe_ven, "+
   " mo_pag, ne_diaGra, nd_porRet , st_regrep ,st_sop, co_tipret )"+
   " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+intCodDoc+", "+
   txtTipDocCod.getText()+", "+(i+1)+", ";
   strSql +=  objInvItm.getIntDatoValidado( tblPag.getValueAt(i, INT_TBL_PAGCRE) ) + ", ";
   strSql +=" '"+strFechaPag + "'," +
   (objUti.redondear(Double.parseDouble( (tblPag.getValueAt(i, INT_TBL_PAGMON)==null)?"0":tblPag.getValueAt(i, INT_TBL_PAGMON).toString() ),intNumDec) * -1 )+ ", ";
    strSql +=   objInvItm.getIntDatoValidado( tblPag.getValueAt(i, INT_TBL_PAGGRA) ) + ", ";
    strSql +=  objInvItm.getIntDatoValidado( tblPag.getValueAt(i, INT_TBL_PAGRET) )  +"";
    strSql += " ,'I','"+strSop+"' ,"+(tblPag.getValueAt(i, INT_TBL_PAGCOD)==null?null:(tblPag.getValueAt(i, INT_TBL_PAGCOD).toString().equals("")?null:tblPag.getValueAt(i, INT_TBL_PAGCOD).toString()) )+")";

   stmLoc.executeUpdate(strSql);
 }   
 stmLoc.close();
 stmLoc=null;
 blnRes=true; 
}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
 catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }                       
return blnRes;                   
}


          
          
    

private int getUltCoDoc(java.sql.Connection conn){
int valReturn = 0;
java.sql.Statement stmLoc;
java.sql.ResultSet rstLoc;
String strSql="";
try{
   if(conn!=null){
     stmLoc=conn.createStatement();

     strSql="SELECT  case when Max(co_doc)+1 is null then 1 else Max(co_doc)+1 end as codoc FROM tbm_cabMovInv where " +
     " co_emp = " + objZafParSis.getCodigoEmpresa() +
     " and co_loc = " + objZafParSis.getCodigoLocal() +
     " and co_tipDoc = " + txtTipDocCod.getText() ;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        valReturn = rstLoc.getInt("codoc");
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
}}catch(java.sql.SQLException e){ valReturn= -1; }
  catch(Exception e){ valReturn= -1;  }
return valReturn;
}


           
           
  public boolean imprimir(){
            
         return true;             
     }
 
    
          
            private String FilSql() {
                String sqlFiltro = "";
                //Agregando filtro por codigo de Tipo de Documento
                if(!txtTipDocCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc ="+txtTipDocCod.getText();
                
                //Agregando filtro por Numero de Documento
                if(!txtDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_doc = "+txtDoc.getText();

                if(!txtFac.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc="+txtFac.getText();

                //Agregando filtro por Codigo de Cliente
                if(!txtCliCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_cli="+txtCliCod.getText();
                    
                //Agregando filtro por Codigo de Vendedor
                if(!txtVenCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_com="+txtVenCod.getText();

                //Agregando filtro por Codigo de Vendedor
                if(!txtAte.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.tx_ate LIKE '" + txtAte.getText() + "'";
                
                    //Agregando filtro por Nombre del cliente
                    if(!txtVenNom.getText().equals(""))
                        sqlFiltro = sqlFiltro + " and DocCab.tx_nomVen LIKE '" + txtVenNom.getText() + "'";


              return sqlFiltro ;
            }
                             
    
    private void MensajeValidaCampo(String strNomCampo){
                    //JOptionPane obj =new JOptionPane();
                    /*String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";*/
                    String strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            }
                   
     private boolean _consultar(String strFil){
                   strFiltro= strFil;
                   try{
                            if(conCab == null)
                                    conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                            if(conCab.isClosed())
                                    conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                            
                            
                            if (conCab!=null){

                                //stmDoc=conDoc.createStatement(); stmCab
                                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//
//                                 /*
//                                 * Agregando el Sql de Consulta para el Documento
//                                  */
//                                sSQL= "SELECT " +
//                                      " DocCab.co_doc, DocCab.co_tipdoc,DocCab.co_forPag,DocCab.st_reg   " + //<==Campos que aparecen en la parte superior del 1er Tab
//                                      " FROM tbm_cabMovInv as DocCab, tbm_cabtipdoc as doc, tbr_tipdocprg as menu " + // Tabla enla cual se trabajara y sus respectivo alia
//                                      " Where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
//                                      "        and DocCab.co_loc = " + intLocalFac +
//                                      "        and doc.co_emp = DocCab.co_emp and doc.co_loc = DocCab.co_loc and " +
//                                      "        menu.co_emp = DocCab.co_emp and  menu.co_loc = DocCab.co_loc and DocCab.st_reg not in ('E') "   ;// Consultando en el local en el ke se esta trabajando
//
//                                sSQL= sSQL + strFiltro + "  ORDER BY DocCab.ne_numdoc";  //and DocCab.st_reg IN ('A','C','R','I',)


                                /*sSQL= "SELECT DocCab.co_emp, DocCab.co_loc, DocCab.co_doc, DocCab.co_tipdoc,DocCab.co_forPag,DocCab.st_reg "
                                + "  ,forpag.tx_des  "
                                +"  FROM tbm_cabMovInv as DocCab "
                                + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=DocCab.co_emp and doc.co_loc=DocCab.co_loc and doc.co_tipdoc=DocCab.co_tipdoc ) "
                                + " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag )  "
                                + " Where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() +
                                "  and DocCab.co_loc = " +intLocalFac+
                                "  and DocCab.st_reg not in ('E') "+
                                " "+ strFiltro + "  ORDER BY DocCab.ne_numdoc";*/

                                sSQL= " select DocCab.co_emp, DocCab.co_loc, DocCab.co_doc, DocCab.co_tipdoc,DocCab.co_forPag,DocCab.st_reg, forpag.tx_des, forpag.ne_tipforpag "
                                        + " FROM tbm_cabMovInv as DocCab "
                                        + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=DocCab.co_emp and doc.co_loc=DocCab.co_loc and doc.co_tipdoc=DocCab.co_tipdoc ) "
                                        + " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag )  "
                                        + " Where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() +" "
                                        + " and DocCab.co_loc = " +intLocalFac+ " "
                                        + " and DocCab.st_reg not in ('E') "
                                        + " "+ strFiltro + " "
                                        + " ORDER BY DocCab.ne_numdoc";
                                
                                //System.out.println("CONSULTA "+ sSQL);
                                rstCab=stmCab.executeQuery(sSQL);                                  
                                
                                if(rstCab.next()){
                                    rstCab.last();
                                    cargarReg();
                                    
                                    calculaSubTotTodLosItm();
                                    
                                     CalculoPago(); /// calculaPagos();
                                     
                                     generaAsiento();
                                }
                                else{
                                  //  objTooBar.setMenSis("0 Registros encontrados");
                                    clnTextos();
                                   // cierraConnections();                                    
                                    return false;
                                }
                            }
                   }
                   catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;  }
                   catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }                                                    
                 System.gc(); 
                 return true;                 
            }
     
      public void calculaSubTotTodLosItm(){
        try{  
        String strIva="S";
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
                
               // if(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)==null)
                //    dblIva = dblIva + 0;
                //else{
                    
                    strIva=((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)==null?"S":(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("")?"S":tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true")?"S":"N")));
                    if(strIva.equals("S")){   
                       dblTmp=((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString()));
                       dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                    }
                    else  dblIva = dblIva + 0;
                    
                    //dblTmp = ((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString())) : 0 );
                   
                //}
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
        
        }catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);   }   
    }
    
   
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
//               if( !objUti.verifyTbl(tblPag,intColObligadasPag)){
//                  tabCotCom.setSelectedIndex(1);
//                  javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
//                        String strTit, strMsg;
//                        strTit="Mensaje del sistema Zafiro";
//                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
//                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                    objUti.verifyTbl(tblPag,intColObligadasPag);
//                    return false;
//               }
               
               
               
                 for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                  
                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                                        
                     double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                    
                    if(dblCan <= 0.00 ){
                        //JOptionPane obj = new JOptionPane();
                        /*String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";*/
                        String strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                        return false; 
                    }
                    
                     
                    
                     double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PREUNI).toString());
                    if(dblCos <= 0.00 ){
                        //JOptionPane obj = new JOptionPane();
                        /*String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";*/
                        String strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.editCellAt(intRowVal, INT_TBL_PREUNI);
                        return false; 
                    }
                    
                      
                   }
                 }
               
               
               
               
                if(!objAsiDia.isDiarioCuadrado()){
                    //JOptionPane obj = new JOptionPane();
                        /*String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";*/
                        String strMsg="EL diario no esta cuadrado, no se puede grabar";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                   return false;
               }
               
               
               
              // System.out.println(">>>> "+ cboMotivoTransporte.getSelectedIndex() );
               if(cboMotivoTransporte.getSelectedIndex()==-1){
                        //JOptionPane obj = new JOptionPane();
                        /*String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";*/
                        String strMsg="Seleccione el Motivo de Transporte.";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                        tabCotCom.setSelectedIndex(2);
                      return false;
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
                      //JOptionPane obj = new JOptionPane();
                            /*String strTit, strMsg;
                            strTit="Mensaje del sistema Zafiro";*/
                            String strMsg="La suma de los montos a pagar es mayor al total de la CotizaciÃ³n.\nCorrija y vuelva a intentarlo.";
                            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                    
                    return false;
                }
               String strSql = "select * from tbm_cli where " +
                               "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                               "co_cli = " + Integer.parseInt(txtCliCod.getText());
               try{
                    java.sql.Connection conPorDes = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    if (conPorDes!=null){                    
                   
                        Statement stmCliDesc = conPorDes.createStatement();
                        ResultSet rstCliDesc = stmCliDesc.executeQuery(strSql);
                        if(rstCliDesc.next()){
                            double dblPorDesCli  = rstCliDesc.getDouble("nd_maxDes");

                            //*************************************
                               
                            int Estado=0;
                            
                            //*********************************
                        }
                        rstCliDesc.close();
                        stmCliDesc.close();
                        conPorDes.close();
                        rstCliDesc = null;
                        stmCliDesc = null;
                        conPorDes = null;
                        
                    }
                    
                    /** CAMPOS TARJETA DE CREDITO. */
                    if (intTipForPag2 == 4){
                        if (txtCodTarCre.getText().equals("")){
                            tabCotCom.setSelectedIndex(1);
                            MensajeValidaCampo("Tarjeta de Credito");            
                            return false;
                        }

                        if (txtCodTipCre.getText().equals("") || txtCodRedTarCre.getText().equals("")){
                            tabCotCom.setSelectedIndex(1);
                            MensajeValidaCampo("Tipo de Credito");            
                            return false;
                        }

                        if (txtCodEmiTar.getText().equals("")){
                            tabCotCom.setSelectedIndex(1);
                            MensajeValidaCampo("Emisor de la Tarjeta de Credito");            
                            return false;
                        }

                        if (txtTipCre.getText().equals("D")){
                            if (txtCodMesCre.getText().equals("")){
                                tabCotCom.setSelectedIndex(1);
                                MensajeValidaCampo("Meses de credito");            
                                return false;
                            }
                        }                    
                    }                        

               }catch(SQLException Evt) {  
                   objUti.mostrarMsgErr_F1(jfrThis, Evt);  
                   return false;  
               }catch(Exception Evt) { 
                   objUti.mostrarMsgErr_F1(jfrThis, Evt);   
                   return false;  
               }   
                              
               return true; 
          }
   
   
       private double getStkAct(String int_co_itm){
               double dblStkAct = 0;
               try{
                        java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                        if (conUni!=null){
                            java.sql.Statement stmUni = conUni.createStatement();
                            String sSQL= "SELECT nd_stkact " +
                                            " from tbm_inv as inv " +
                                            " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                                            "       inv.co_itm = " + int_co_itm ; 
                            java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                            if(rstUni.next()){
                                dblStkAct = rstUni.getDouble("nd_stkact");
                            }
                            rstUni.close();
                            stmUni.close();
                            conUni.close();
                            rstUni = null;
                            stmUni = null;
                            conUni = null;
                        }
               }
               catch(java.sql.SQLException Evt) {  return dblStkAct;  }
               catch(Exception Evt) { return dblStkAct;  }                  
               return dblStkAct;
        }        
       
       
       
       private double getPreUni(String int_co_itm){
              double dblPreUni = 0;
               try{
                        java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                        if (conUni!=null){
                            java.sql.Statement stmUni = conUni.createStatement();
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
                            conUni.close();
                            rstUni = null;
                            stmUni = null;
                            conUni = null;
                        }
               }
               catch(java.sql.SQLException Evt) {  return dblPreUni; }
               catch(Exception Evt) {  return dblPreUni;  }                  
               return dblPreUni;
        }       
      
      

    /*
     *  Listener para hacer que se calcule el pago despues segun el total de cotizacion
     */
    private boolean isAnulada()
    {
       boolean blnRes = false;
      java.sql.Connection conTmp ;
      java.sql.Statement stmTmp;
      String strSQL = "";
      try
      {
          conTmp = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
          if (conTmp!=null)
          {
              stmTmp = conTmp.createStatement();
              strSQL = "";
              strSQL = " Select count(*) from tbm_cabmovinv ";
              strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and co_doc = " + txtDoc.getText(); 
              strSQL += " and co_tipdoc = " + txtTipDocCod.getText() + " and st_reg = 'I' " ;
              if (objUti.getNumeroRegistro(this,objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0){
                  blnRes = true;
              }else
                  blnRes = false;
              stmTmp.close();
              conTmp.close();
          }
       }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);   blnRes = true;  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   blnRes = true;  }
        return blnRes;
    }   
    
    private void MensajeInf(String strMensaje){
            //JOptionPane obj =new JOptionPane();
            /*String strTit;
            strTit="Mensaje del sistema Zafiro";*/
            JOptionPane.showMessageDialog(jfrThis,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    
    public class ActLisForPag implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // calculaPagos();    
        }
    }
   
    
      /**
     * Metodos para generar el Asiento Contable
     * iNteractuan directamente con generaAsiento
     */
    private java.util.Vector vecFila(int intCodCtaCtb){
            java.util.Vector vecRowAsiento = new java.util.Vector(); 
            /**Linea*/
            vecRowAsiento.add(null);

            /** Codigo de cuenta */
            vecRowAsiento.add(intCodCtaCtb+"");

            /** Codigo de ALterno de cuenta */
            vecRowAsiento.add(objCtaCta_Dat.getCodAltCta(intCodCtaCtb));

            /** Boton buscar cuenta */
            vecRowAsiento.add(null);

            /** NOmbre de cuenta */
            vecRowAsiento.add(objCtaCta_Dat.getNomCta(intCodCtaCtb));

            /** Valor que va en el debe */
            vecRowAsiento.add("0");

            /** Valor que va en el Haber  */
            vecRowAsiento.add("0");

            /** Referencia */
            vecRowAsiento.add(null);

            return vecRowAsiento;

    }    
    
    
    
    
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
      
    /**
     * Metodo que genera el asiento Contable
     */
    public void generaAsiento(){
        
        
        objAsiDia.inicializar();
        java.util.Vector vecAsiento = new java.util.Vector();         
        
        if(dblTotalCot>0){
            java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb()); 
            vecRowAsientoDeb.setElementAt(new Double(objUti.redondeo(dblTotalCot,6)), 5);
            vecAsiento.add(vecRowAsientoDeb);
        }
        /*
         * Agregando la cuenta de descuento 
         */
        
        double dblValTotDes = getTotalDescuento();
 
      //  System.out.println(">>>>>   SOLO DESCUENTO >>>>>>>>>>>  "+ dblValTotDes );
        
        if( dblValTotDes > 0){
            
            java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes()); 
            vecRowAsientoDeb.setElementAt(new Double(dblValTotDes), 5);
            vecAsiento.add(vecRowAsientoDeb);
        }
        
        /*
         * Agregando la cuenta de Ventas
         */
        if( (dblValTotDes+dblSubtotalCot) > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab()); 
            vecRowAsientoHab.setElementAt(new Double(objUti.redondeo(dblValTotDes+objUti.redondeo(dblSubtotalCot,6),6)), 6);
            vecAsiento.add(vecRowAsientoHab);
        }
        /*
         * Agregando la cuenta Iva en Ventas
         */
        if( dblIvaCot > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen()); 
            vecRowAsientoHab.setElementAt(new Double(objUti.redondeo(dblIvaCot,6)), 6);
            vecAsiento.add(vecRowAsientoHab);
       }        
        objAsiDia.setDetalleDiario(vecAsiento);        
    }
    
      public double getTotalDescuento(){
            double dblTotalDescuento=0;
            for(int filaActual=0; filaActual< tblDat.getRowCount(); filaActual++){
               if(tblDat.getValueAt(filaActual, INT_TBL_CODITM)!=null){
                   double dblCan    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_CANMOV).toString()))),6), 
                          dblPre    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PREUNI).toString()))),6),
                          dblPorDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PORDES )==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PORDES).toString()))),2),
                          dblValDes =  objUti.redondeo(((dblCan * dblPre)==0)?0:(objUti.redondeo((dblCan * dblPre)* dblPorDes, 6)  / 100),6);
                  
                     //     System.out.println(">>>>>   SOLO DESCUENTO 1 >>>>>>>>>>>  "+ dblTotalDescuento + " >> "+ dblValDes +" >> "+ objUti.redondeo(dblValDes,6) );
                          
                         // dblTotalDescuento =  objUti.redondear(dblTotalDescuento + objUti.redondeo(dblValDes,6),2);        
                     dblTotalDescuento =  dblTotalDescuento + dblValDes;      
                  
                  
               } }
            
           // System.out.println(">>>>>   SOLO DESCUENTO >>>>>>>>>>>  "+ dblTotalDescuento );
            
            dblTotalDescuento =  objUti.redondear(objUti.redondear(dblTotalDescuento,4),2);  
            
          //  System.out.println(">>>>>   SOLO DESCUENTO >>>>>>>>>>>  "+ dblTotalDescuento );
           
            return dblTotalDescuento;
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
 
 Vector vecDataTblPag;
double dblRetFueGlo=0, dblRetIvaGlo=0;
    

public void calculaPagos(){
   int intVal =  intCanArr[0];
   int intsizearre = intarreglodia.length;
   intVal= intsizearre - (intsizearre-intVal);
   //java.sql.Statement stm;
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

      dblRetFueGlo = 0.0;
      dblRetIvaGlo = 0.00;

    if(strCodTipPerCli.equals("")){
       if(!(txtCliCod.getText().equals("")))
          MensajeInf("AL CLIENTE LE FALTA TIPO DE PERSONA\n. REVISE LOS DATOS DEL CLIENTE Y CORRIJA ");
       objTblModPag.removeAllRows();
    }else{


     if(dblBaseDePagos>0){

     vecDataTblPag = new Vector();

     if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
         Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
        //*************************************************************************************///
         java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
         if(conn!=null){



/******************************************************************************************************************************/

                   calculaSubtotalServiNoServi(conn, "T");

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotTran );
                   }

/******************************************************************************************************************************/

                   calculaSubtotalServiNoServi(conn, "N");

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotBien );
                   }

/******************************************************************************************************************************/
                   calculaSubtotalServiNoServi(conn, "S");

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotServ );
                   }

/******************************************************************************************************************************/


     conn.close();
     conn=null;
    }
    //*************************************************************************************///
    //dblRete = dblRetFueFle+dblRetFue+dblRetIva;
    dblRete = dblRetFueGlo+dblRetIvaGlo;
    dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);



    for(i=0; i < intVal; i++) {
        Calendar objFecPagActual = Calendar.getInstance();
        objFecPagActual.setTime(objFec.getTime());

        int diaCre = intarreglodia[i];
        int numPag = intarreglonum[i];
        String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);

        if (diaCre!=0)
            objFecPagActual.add(java.util.Calendar.DATE, diaCre);

        dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH)+1);
        dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));

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
       vecDataTblPag.add(vecReg);
    }
   objTblModPag.setData(vecDataTblPag);
   tblPag.setModel(objTblModPag);

vecDataTblPag=null;



/***********************************************************************************************/

    double dblValRet=0;
    String strFecCor="";
    for(int x=0; x<tblPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)) );
      if(dblValRet==0.00){
          strFecCor= tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
          break;
      }
    }

    for(int x=0; x<tblPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)) );
      if(dblValRet > 0.00 )
        tblPag.setValueAt(strFecCor,  x, INT_TBL_PAGFEC);

    }

/***********************************************************************************************/

 }}}}catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
    catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}
   
    


double dblSubSerNoSer=0, dblIvaSerNoSer=0;

public void calculaSubtotalServiNoServi(java.sql.Connection conn, String strServi){
 double dblCan,dblDes,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
 double dblSub = 0, dblIva = 0,  dblTmp=0;
 int intFilSel=0;
 dblSubSerNoSer=0;
 dblIvaSerNoSer=0;
 try{
   if(conn!=null){

 for (intFilSel=0; intFilSel<tblDat.getRowCount(); intFilSel++){
  if(tblDat.getValueAt(intFilSel,INT_TBL_CODITM) != null ){
    if( objInvItm.getEstItm(conn, objZafParSis.getCodigoEmpresa(), Integer.parseInt(tblDat.getValueAt(intFilSel,INT_TBL_CODITM).toString()), strServi ) ){

     dblCan = Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)) );
     dblPre = Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)) );
     dblDes = objUti.redondear( Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PORDES) ) ),2);

     dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100));
     dblTotal  = (dblCan * dblPre)- dblValDes;
     dblTotal =  objUti.redondear(dblTotal,3);
     dblTotal =  objUti.redondear(dblTotal,2);

     dblSub +=  dblTotal;

    if(objInvItm.getStringDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)).equals("S")){
        dblTmp = dblTotal;
        dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
    }
    else  dblIva = dblIva + 0;

}}
}

 if(stIvaVen.equals("N")) dblIvaSerNoSer = 0.00;
 else dblIvaSerNoSer = objUti.redondear(dblIva,intNumDec);

     dblSubSerNoSer = objUti.redondear(dblSub ,intNumDec);

 }}catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}


private void cargaForPag(java.sql.Connection conn, int intCodMot ){
   java.sql.Statement stm;
   java.sql.ResultSet rst;
   String strSQL="";
   double dblRetFue=0, dblRetIva=0;
   try{
     if(conn!=null){
         
         if (this.intTipForPag2 == 4){
             return;
         }
         
      stm=conn.createStatement();

      strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
      strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
      strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMot+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
      " AND polret.st_reg='A' AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
      //System.out.println("--> "+strSQL );
      rst = stm.executeQuery(strSQL);
      while (rst.next()){

           java.util.Vector vecReg = new java.util.Vector();
           vecReg.add(INT_TBL_PAGLIN, "");
           vecReg.add(INT_TBL_PAGCRE, "");
           vecReg.add(INT_TBL_PAGFEC, txtFecDoc.getText() );
           if(rst.getString("tx_aplret").equals("S")){

             dblRetFue = objUti.redondeo( (    dblSubSerNoSer  * ( rst.getDouble("nd_porret")/100)),2 );
             dblRetFueGlo+=dblRetFue;
             vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret") );
             vecReg.add(INT_TBL_PAGMON, ""+dblRetFue);
             vecReg.add(INT_TBL_PAGGRA, "");
          }
          if(rst.getString("tx_aplret").equals("I")){
           if(dblIvaSerNoSer > 0 ){
            dblRetIva = objUti.redondeo(  (  dblIvaSerNoSer  * ( rst.getDouble("nd_porret")/100)),2 );
            dblRetIvaGlo+=dblRetIva;
            vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret") );
            vecReg.add(INT_TBL_PAGMON, ""+dblRetIva);
            vecReg.add(INT_TBL_PAGGRA, "");
           }
          }
         vecReg.add(INT_TBL_PAGCOD, rst.getString("co_tipret") );
         vecReg.add(INT_TBL_PAGSOP, "N");

        vecDataTblPag.add(vecReg);

        }
        rst.close();
        rst=null;

   }}catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
     catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}
       
//     public void calculaPagos(){
//          int intVal =  intCanArr[0];
//          int intsizearre = intarreglodia.length;
//          intVal= intsizearre - (intsizearre-intVal);
//          java.sql.Statement stm;
//          java.sql.ResultSet rst;
//          int intFilUltPag = 0;
//          double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
//          try{
//                java.util.Calendar objFec = java.util.Calendar.getInstance();
//                int fecDoc [] = txtFecDoc.getFecha(txtFecDoc.getText());
//
//                if(fecDoc!=null){
//                    objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
//                        objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
//                        objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
//                }
//
//                dblBaseDePagos = dblTotalCot;
//
//                            //Borrando los datos actuales del jtable
//                            javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
//                            modTbl.setRowCount(0);
//
//                            if(dblBaseDePagos>0){
//
//                                if(cboForPag.getSelectedIndex()!=-1){
//
//                                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
//
//                                    //*************************************************************************************///
//
//                                      java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                                      if (conn!=null){
//
//                                          dblRetFueFle = getSubtotalTrans();
//
//
//                                         // System.out.println(">>> "+ dblRetFueFle + " >> " + dblRetFue );
//                                         if(!(strCodTipPerCli==null)){
//                                          if(!(strCodTipPerCli.trim().equals("1"))){
//                                           if(dblRetFueFle>0){
//                                                ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
//                                                intFilUltPag = tblCotForPag.getRowCount()-1;
//                                                tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
//                                                tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
//                                                tblCotForPag.setValueAt("1.000", intFilUltPag, 3);
//                                                dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
//                                                //     System.out.println(">>> "+ dblRetFueFle );
//                                                tblCotForPag.setValueAt( new Double(dblRetFueFle) , intFilUltPag, 4);
//                                             }} else { dblRetFueFle=0;  }
//                                         }else { dblRetFueFle=0;  }
//
//
//
//
//
//                                          String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
//                                          strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
//                                          strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
//                                          "  AND polret.st_reg='A'   AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
//
//                                          stm = conn.createStatement();
//                                          rst = stm.executeQuery(strSQL);
//                                           // System.out.println(">>> "+ strSQL );
//                                          while (rst.next()){
//
//                                               ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
//                                                intFilUltPag = tblCotForPag.getRowCount()-1;
//                                                tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
//                                                tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
//
//                                                 if (rst.getString("tx_aplret").equals("S")){
//                                                    dblRetFue = getSubtotalSinTrans();
//                                                   if(!(dblRetFueFle>0))
//                                                      dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
//                                                   else
//                                                      dblRetFue = objUti.redondeo( (    dblRetFue  * ( rst.getDouble("nd_porret")/100)),2 );
//
//
//
//                                                    tblCotForPag.setValueAt(rst.getString("nd_porret"), intFilUltPag, 3);
//                                                    tblCotForPag.setValueAt( new Double(dblRetFue) , intFilUltPag, 4);
//                                                 }
//                                                 if (rst.getString("tx_aplret").equals("I")){
//                                                     dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 );
//                                                     tblCotForPag.setValueAt(rst.getString("nd_porret") , intFilUltPag, 3);
//                                                     tblCotForPag.setValueAt( new Double(dblRetIva) , intFilUltPag, 4);
//                                                 }
//
//                                            tblCotForPag.setValueAt(rst.getString("co_tipret") , intFilUltPag, 6);
//                                         }
//
//                                         rst.close();
//                                         rst=null;
//                                         stm.close();
//                                         stm=null;
//
//                                         conn.close();
//                                         conn=null;
//
//
//                                     }
//
//                                    //*************************************************************************************///
//                                     double dblPago=0.00;
//                                     double dblPagos=0.00;
//                                     int i=0;
//
//                                     double dblRete=0;
//                                     dblRete = dblRetFueFle+dblRetFue+dblRetIva;
//                                    //  System.out.println(">>> "+ dblRete );
//                                     dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);
//
//                                        //  System.out.println(">>> "+ dblBaseDePagos );
//
//                                   //  dblBaseDePagos = objUti.redondear( ( dblTotalCot - ( dblRetFueFle + dblRetFue + dblRetIva)),intNumDec);
//
//                                for(i=0; i < intVal; i++) {
//                                           java.util.Calendar objFecPagActual = objFec.getInstance();
//                                           objFecPagActual.setTime(objFec.getTime());
//
//                                           int diaCre = intarreglodia[i];
//                                           int numPag = intarreglonum[i];
//                                           String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);
//
//                                            if (diaCre!=0)
//                                               objFecPagActual.add(java.util.Calendar.DATE, diaCre);
//
//                                            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
//                                            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
//                                            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
//
//                                            ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
//                                            intFilUltPag = tblCotForPag.getRowCount()-1;
//                                            tblCotForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
//                                            tblCotForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
//                                            dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
//                                            dblPago += dblPagos;
//                                            dblPagos = objUti.redondear(dblPagos ,intNumDec);
//                                            tblCotForPag.setValueAt( new Double(dblPagos) , intFilUltPag, 4);
//                                            tblCotForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
//
//                                            tblCotForPag.setValueAt(strSop,  intFilUltPag, 7);
//
//                                    }
//
//
//                                     double dblultpag =  objUti.redondear(Double.parseDouble(tblCotForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
//                                    // dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago + dblRetIva + dblRetFue) ),intNumDec);
//                                    dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago + dblRete ) ),intNumDec);
//
//
//                                     tblCotForPag.setValueAt( new Double(dblultpag) , intFilUltPag , 4);
//
//
//
//
//
//
//
//
//
//
///***********************************************************************************************/
//
//    double dblValRet=0;
//    String strFecCor="";
//    for(int x=0; x<tblCotForPag.getRowCount();x++){
//      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblCotForPag.getValueAt(x, 3)) );
//      if(dblValRet==0.00){
//          strFecCor= tblCotForPag.getValueAt(x, 2).toString();
//          break;
//      }
//    }
//
//
//
//
//     String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
//     java.util.Date  fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
//     int intAnioAct= (fac1.getYear()+1900);
//
//     //  por alfredo.  año nuevo 31 dic año anterior
//     String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
//     java.util.Date  fac = objUti.parseDate(strF, "yyyy/MM/dd");
//     int intAnioCre= (fac.getYear()+1900);
//
//
//     if(intAnioCre > intAnioAct ){
//         strFecCor="31/12/"+intAnioAct;
//     }
//
//
//
//
//    for(int x=0; x<tblCotForPag.getRowCount();x++){
//      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblCotForPag.getValueAt(x, 3)) );
//      if(dblValRet > 0.00 )
//        tblCotForPag.setValueAt(strFecCor,  x, 2);
//
//    }
//
///***********************************************************************************************/
//
//
//
//
//
//
//
//
//                                    //*************************************************************************************///
//                                    //*************************************************************************************///
//                 } }
//               }
//               catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
//               catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
//     }
//
//
        
    public class ButFndTbl extends ZafTableColBut{
        
        public ButFndTbl(JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        
        @Override
        public void butCLick() {
            listaItem("");
            }
        }        
      
    
    private void listaItem(String strDesBusqueda){
            Librerias.ZafConsulta.ZafConsulta  obj = 
                  new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
                 "Codigo,Nombre,Stock,Precio,Iva[S/N],Cod.Sistema","tx_codAlt, tx_nomItm, nd_stkAct, nd_preVta1, st_ivaVen, co_itm",
                 "select tx_codAlt,tx_nomItm,nd_stkAct,nd_preVta1,st_ivaVen,co_itm from tbm_inv where co_emp = "+ objZafParSis.getCodigoEmpresa(), "", 
                 objZafParSis.getStringConexion(), 
                 objZafParSis.getUsuarioBaseDatos(), 
                 objZafParSis.getClaveBaseDatos()
                  );        
            
                    obj.setTitle("Listado Productos");

                    if(tblDat.getValueAt(tblDat.getSelectedRow(), 1) == null || tblDat.getValueAt(tblDat.getSelectedRow(), 1).toString().equals("") )
                        obj.show();
                    else
                        if(!strDesBusqueda.equals("")){
                            if(!obj.buscar("tx_codalt = '" + tblDat.getValueAt(tblDat.getSelectedRow(), 1).toString()+"'") )
                                obj.show();
                        }else 
                            obj.show();
                        
                    if(!obj.GetCamSel(1).equals("")){
                      //Poniendo el codigo de producto, que el usuario eligio en la consulta, en la tabla 
                        tblDat.setValueAt(obj.GetCamSel(1),tblDat.getSelectedRow(),1);
                      //Poniendo La descripcion de producto, que el usuario eligio en la consulta, en la tabla 
                        tblDat.setValueAt(obj.GetCamSel(2),tblDat.getSelectedRow(),3);
                        
                        Boolean blnIva = false;

                        if(obj.GetCamSel(5).equals("S"))
                            blnIva = true;
 
                        tblDat.setValueAt(new Double(obj.GetCamSel(4)),tblDat.getSelectedRow(),6);
                        tblDat.setValueAt(blnIva,tblDat.getSelectedRow(),8);
                        tblDat.setValueAt(obj.GetCamSel(6),tblDat.getSelectedRow(),10);
                        tblDat.changeSelection(tblDat.getSelectedRow(), 5, false, false);
                        tblDat.editCellAt(tblDat.getSelectedRow(),5);
                    }
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanOtro;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCliCon;
    private javax.swing.JButton butCliCon1;
    private javax.swing.JButton butConEmiTar;
    private javax.swing.JButton butConMesCre;
    private javax.swing.JButton butConTarCre;
    private javax.swing.JButton butConTipCre;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVenCon;
    private javax.swing.JButton butacep;
    private javax.swing.JComboBox cboMotivoTransporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblCotNumDes;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblEmiTar;
    private javax.swing.JLabel lblFac;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblGuia;
    private javax.swing.JLabel lblGuia1;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblMesCre;
    private javax.swing.JLabel lblMesGra;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPartida;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTarCre;
    private javax.swing.JLabel lblTipCre;
    private javax.swing.JLabel lblTot;
    private javax.swing.JLabel lblTransporte;
    private javax.swing.JLabel lblfecha;
    private javax.swing.JPanel pan2;
    private javax.swing.JPanel panAsiento;
    private javax.swing.JPanel panCotForPag;
    private javax.swing.JPanel panCotForPagCen;
    private javax.swing.JPanel panCotForPagNor;
    private javax.swing.JPanel panCotGen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenNorNor;
    private javax.swing.JPanel panCotGenNorSur;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panCotNor;
    private javax.swing.JPanel panNorOtros;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabCotCom;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblPag;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliDir;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtCodEmiTar;
    private javax.swing.JTextField txtCodMesCre;
    private javax.swing.JTextField txtCodTarCre;
    private javax.swing.JTextField txtCodTipCre;
    private javax.swing.JTextField txtCodigoFormaPago;
    private javax.swing.JTextField txtCot;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtFac;
    private javax.swing.JTextField txtGuia;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtMesGra;
    private javax.swing.JTextField txtNomEmiTar;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtNomMesCre;
    private javax.swing.JTextField txtNomTarCre;
    private javax.swing.JTextField txtNomTipCre;
    private javax.swing.JTextField txtOD;
    private javax.swing.JTextField txtPartida;
    private javax.swing.JTextField txtPed;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTipDocAli;
    private javax.swing.JTextField txtTipDocNom;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtTransporte;
    private javax.swing.JTextField txtVenCod;
    private javax.swing.JTextField txtVenNom;
    // End of variables declaration//GEN-END:variables

    private javax.swing.JTextField txtTipDocCod =  new javax.swing.JTextField();
    private javax.swing.JTextField txtCod =  new javax.swing.JTextField();
    private javax.swing.JTextField txtDes =  new javax.swing.JTextField();
    private javax.swing.JTextField txtVehRet =  new javax.swing.JTextField();
    private javax.swing.JTextField txtChoRet =  new javax.swing.JTextField();        
    
    private class ZafMouMotAda extends MouseMotionAdapter  {
        @Override
        public void mouseMoved(MouseEvent evt)  {
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
    

 /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            switch (e.getType()) {
                case TableModelEvent.INSERT:
//                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case TableModelEvent.DELETE:
                    calculaTotal(dblPorIvaCon);
                    calculaPagos();
                    break;
                case TableModelEvent.UPDATE:                         
                    break;
            }
        }
    }    
        
   
    @Override
    protected void finalize() throws Throwable {   
        //System.gc(); 
        Runtime.getRuntime().gc();
        super.finalize();
   ///    System.out.println ("Se libera Objeto...");

   }
    
}

