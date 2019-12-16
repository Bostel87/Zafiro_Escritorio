/* 
 *  Created on 04 de Junio de 2007
 */ 
package Ventas.ZafVen24;  
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRepInv.ZafRepInv;
import Librerias.ZafRptSis.ZafRptSis;
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
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafTipDoc;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

 /** 
 * @author  Javier Ayapata Garate    
 * REEMPLAZO DE FACTURAS.
 */
public class ZafVen24 extends javax.swing.JInternalFrame
{ 
    //Constantes
    final int INT_TBL_DIACRE   = 1 ;            //Codigo del item (codigo interno del sistema)
    final int INT_TBL_LINEA    = 0 ; 
    final int INT_TBL_ITMALT   = 1 ;
    final int INT_TBL_BUTITM   = 2 ;
    final int INT_TBL_DESITM   = 3 ;
    final int INT_TBL_UNIDAD   = 4 ;
    final int INT_TBL_CODBOD   = 5 ;
    final int INT_TBL_BUTBOD   = 6 ;
    final int INT_TBL_CANMOV   = 7 ;            //Cantidad del movimiento (venta o compra)
    final int INT_TBL_STKBOD   = 8 ; //16       //Stock actual de la bodega
    final int INT_TBL_ULTCOS   = 9 ;  //17      //Ultimo costo
    final int INT_TBL_PREUNI   = 10 ; //8       //Precio de Venta
    final int INT_TBL_UTILIDAD = 11 ;           //Utilidad 
    final int INT_TBL_PREUNI2  = 12 ;           //Precio Unitario
    final int INT_TBL_PORDES   = 13 ; //11      //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 14;            //Boolean Iva
    final int INT_TBL_TOTAL    = 15;            //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 16;
    final int INT_TBL_ESTADO   = 17;
    final int INT_TBL_IVATXT   = 18;
    final int INT_TBL_BLNPRE   = 19;            //Columna que contiene verdadero si es problema de precio
    final int INT_TBL_CANORI   = 20;            //Codigo del bodega de una o/c cargada
    final int INT_TBL_BODORI   = 21;
    final int INT_TBL_CODORI   = 22;
    final int INT_TBL_ITMALT2  = 23;    
    final int INT_TBL_ITMSER   = 24;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_ITMTER   = 25;            //Columna que contiene "S" (el item es terminal L ) "N"  no corresponde a la terminal L
    
    final int INT_TBL_CODLOCREL = 26;
    final int INT_TBL_CODTIPREL = 27;
    final int INT_TBL_CODDOCREL = 28;
    final int INT_TBL_CODREGREL = 29;
    final int INT_TBL_CODITMACT = 30; // codigo del item actual..
    
    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODITM   = 0;
    final int INT_ARR_CODBOD   = 1;
    final int INT_ARR_CANMOV   = 2;
    final int INT_ARR_ITMORI   = 3;
    final int INT_ARR_BODORI   = 4;
    final int INT_ARR_CANORI   = 5;
    //*****************************    
    
    //Variables
    private MiToolBar objTooBar;
    private ZafParSis objParSis;
    private ZafAutPrg objAutPrg;   
    private UltDocPrint objUltDocPrintVen24;    //Para trabajar con la informacion de tipo de documento
    private Librerias.ZafUtil.UltDocPrint objUltDocPrintUtil;  // Para trabajar con la informacion de tipo de documento       
    private Librerias.ZafUtil.ZafCliente_dat objCliDat;        //Para Obtener la informacion del proveedor  
    private Ventas.ZafVen24.ZafCiudad_dat objCiuDat;           // Para Obtener la informacion de una ciudad    
    private ZafObtConCen  objObtConCen;
    private ZafThreadGUI objThrGUI;
    private ZafAsiDia objAsiDia; // Asiento de Diario
    private ZafCtaCtb_dat objCtaCta_Dat;  //Datos de Cuentas contables
    private ZafTipDoc objTipDoc;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;    
    private ZafTblPopMnu ZafTblPopMnu;  
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;   //Editor: JButton en celda.
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafUtil objUti;    
    private ZafVenCon vcoItm; //*****************  
    private ZafVenCon vcoCli; //*****************  
    private ZafVenCon vcoVen; //*****************  
    private ZafVenCon vcoTipDoc; //*****************   
    private ZafTblCelEdiTxtVco2 objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.
    private ZafRepInv objZafRep;   //Clase que maneja las reposiciones de inventario    
    private ZafInvItm objInvItm; 
    
    private boolean blnChangeData=false;
    private boolean blnClean = true; //Limpiar o no en el metodo clnTextos
    private String strBeforeValue,strAfterValue;
    private String strCodCli, strDesLarCli;   
    private String strCodVen, strDesLarVen;   
    private int intCodMnu;
    Connection conCot, con_remota=null , CONN_GLO=null;  //Coneccion a la base donde se encuentra la cotizacion
    Statement stmCotDet2;    //Statement para la cotizacion 
    ResultSet rstCotCab2=null;    //Resultset que tendra los datos de la cabecera
    ResultSet rstCotDet2;    //Resultset que tendra los datos del Detalle
    
    JInternalFrame jfrThis; //Hace referencia a this
    Connection conCab=null;  //Coneccion a la base donde se encuentra la cotizacion
    Statement stmCab, STMCAB=null;   //Statement para la cotizacion 
    ResultSet rstCab=null, RSTCAB=null;//Resultset que tendra los datos de la cabecera
    String sSQL,strSQL, strFiltro;//EL filtro de la Consulta actual
    double dblPorIva,dblPorIvaaux,dblPorIvaCon ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    double dblTotalCot, dblIvaCot;
    int intNumDec= 2 ; //Numero de decimales a presentar
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    LisTextos objLisCam;     // Instancia de clase que detecta cambios
    Vector vecForPag; //Vector que contiene el codigo del pago
    Vector vecTransporte; //Vector que contiene el codigo del Transporte
    String strAux,strstReg_glo="";
    int  INTCODREGCEN=0;
    double dblSubtotalCot; 
    Statement stmCot;   //Statement para la cotizacion 

    String strFecSisBase= "";
    String strMerIngEgr="", strTipIngEgr="";
    
    /*
      *Variables que se almacena los datos cuando quieran ver la cotizacion desde la ventana autorizacion
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
    String strFecSis;  
               
    final String VERSION = " v0.6.2";
    final int intarreglonum[] = new int[10]; 
    final int intarreglodia[] = new int[10]; 
    final int intCanArr[]= new int[1];
    String strTipPer_glo = ""; 
    String strTipPer_emp="";
           
    String GLO_strnomEmp="";
    String GLO_strdirEmp="";
    
    String STR_ESTREG="";
    String stIvaVen="S";
    String GLO_strArreItm="";
    int Glo_intCodSec=0;
   
    int intRegSelLoc=0;
    String strNumFacRee="",strCodDocRee="", strCodDocReeDev="",strNumDocReeDev="";
    int intCodMotDoc=0;  //  se almacena el codigo del motivo del codumento para las retenciones.
    int intCodTipPerEmp;
    String strarreglosop[] = new String[10]; 
    String strCodTipPerCli; 

    StringBuffer stbDocRelEmpLoc;
    StringBuffer stbDocRelEmpRem;
    int intDocRelEmpLoc=0;
   
    private final String strTit="Mensaje del Sistema Zafiro";
    
    /** Creates new form ZafVen24 */
    public ZafVen24(Librerias.ZafParSis.ZafParSis obj)
    {
      try{  
        this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        jfrThis = this;
        objUti = new ZafUtil();
        this.setTitle(objParSis.getNombreMenu()+VERSION);
        objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objParSis);
        INTCODREGCEN=objObtConCen.intCodReg;
        objInvItm=new Librerias.ZafInventario.ZafInvItm(this, objParSis);
        objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
        intCodMnu = 14;
        objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);        
        initComponents();


        Abrir_Conexion();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */
          
        objAutPrg=new ZafAutPrg(this);
        objUltDocPrintVen24 = new UltDocPrint(objParSis);
        objCiuDat    = new ZafCiudad_dat(this.objParSis);
        objUltDocPrintUtil = new Librerias.ZafUtil.UltDocPrint(objParSis);        
        strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());      
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText(strFecSis);
        objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis); 
        strFecSisBase = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());

        
        //********************** 
        lblfecha.setText(" ");
        //**********************
       
        lblfecha.setVisible(false);
        pan2.add(txtFecDoc);
        txtFecDoc.setBounds(550, 10, 92, 20);
        
        //Nombres de los tabs
        tabCotCom.setTitleAt(0,"General");
        tabCotCom.setTitleAt(1,"Forma de Pago");
        tabCotCom.setTitleAt(2,"Otros");
        tabCotCom.setTitleAt(3,"Asiento de Diario");
        
        objTooBar = new MiToolBar(this);
        
        vecForPag = new java.util.Vector();
        vecTransporte = new java.util.Vector();

        objTooBar.agregarSeparador();
        objTooBar.agregarBoton(burRee);
        
        dblPorIvaaux = getIva(CONN_GLO);
        this.getContentPane().add(objTooBar,"South");
        panAsiento.add(objAsiDia,java.awt.BorderLayout.CENTER);
        pack();
       
        
        objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                objParSis.getClaveBaseDatos(), "SELECT co_reg, tx_deslar FROM tbm_var where co_grp = 7 order by co_reg", cboMotivoTransporte, vecTransporte);      
        
       
        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
        /*
         * Listener para detectar cambios en combo de pagos
         */
         ActLisForPag objActLis = new ActLisForPag();
         cboForPag.addActionListener(objActLis);
        
        
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
 
        objUti.verDecimalesEnTabla(tblCotForPag,4, intNumDec);
        addListenerCambio();
         Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblCotForPag,0);        
         tblCotForPag.getModel().addTableModelListener(new LisCambioTbl());
         
          objZafRep = new Librerias.ZafRepInv.ZafRepInv(objParSis);
          objZafRep.setTipoDoc(-1); 
           cargarTipEmp(CONN_GLO);
          FormaRetencion(CONN_GLO);
          intCodBodPre =  getBodPrede(CONN_GLO);
        // ObtenerNumLineas();
          
        Cerrar_Conexion();
          
         objUti.desactivarCom(this);
    
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }         
    }
     
    private void Abrir_Conexion(){
        try{
            CONN_GLO=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());          
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    private void Cerrar_Conexion(){
        try{
            CONN_GLO.close();
            CONN_GLO=null;  
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    private boolean Abrir_Conexion_Remota(){
        boolean blnres=false;
        try{
            int intIndEmp=INTCODREGCEN;
            if(INTCODREGCEN!=0){
                con_remota=DriverManager.getConnection(objParSis.getStringConexion(intIndEmp), objParSis.getUsuarioBaseDatos(intIndEmp), objParSis.getClaveBaseDatos(intIndEmp));
                con_remota.setAutoCommit(false);
            }
            blnres=true;
        }
        catch (java.sql.SQLException e) {  
               mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            return false;   
        }
       return blnres;
    }
    
    private void mostrarMsg(String strMsg)
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
        ZafUtil objutil = new ZafUtil();
        if(isSelected){
            setBorder(new LineBorder(UIManager.getDefaults().getColor("Button.focus"), 1));
        }else{
            setBorder(null);
        }

        this.setText("" + objutil.redondeo(dblSubPro, intNumDecimales));
        this.setFont(new Font("SansSerif", 0, 11));
        this.setOpaque(true);
        setBackground(new Color(201,223,245));
        setForeground(new Color(0, 0, 0));

       // this.setOpaque(false);
        if(tblDat.getValueAt(row, INT_TBL_BLNPRE)!=null){
           if((tblDat.getValueAt(row, INT_TBL_BLNPRE).toString().equals("true"))){
                this.setOpaque(true);
                setBackground(new Color(0, 0, 0));
                setForeground(new Color(255,255,255));
                setFont(new Font("MS Sans Serif", 1, 18));                   
            }
        }
        return this;
    }
  }      
    
  //**********************************************************************************************
     
    private void cargarTipEmp(java.sql.Connection conTmp){
       Statement stmTipEmp;
       ResultSet rstEmp;
       String sSql;
         try{
              if (conTmp!=null){  
                       stmTipEmp=conTmp.createStatement();
                    
                    sSql="select b.co_tipper,  b.tx_descor from tbm_emp as a " +
                    " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
                    " where a.co_emp="+objParSis.getCodigoEmpresa();
                 
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
                       " co_emp =        " + objParSis.getCodigoEmpresa() + " and "+  
                       " co_loc =        " + objParSis.getCodigoLocal()   + " and " +
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
     
    private void Configura_ventana_consulta(){
        configurarVenConItem();
        configurarVenConClientes();   //**********************
        configurarVenConVendedor();  //************************
        configurarVenConTipDoc();
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
                 
            if(objParSis.getCodigoUsuario()==1){
              Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
              " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
              " where   b.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
              " b.co_loc = " + objParSis.getCodigoLocal()   + " and b.co_mnu = " + intCodMnu;
            }else {     
                   Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+
                   " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a " +
                   " ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                   " WHERE "+
                   "  a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                   " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                   " AND a1.co_mnu=" + intCodMnu+""+
                   " AND a1.co_usr=" + objParSis.getCodigoUsuario();
            }   
                  
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
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
           
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
            arlCam.add("a.co_tipper");
            arlCam.add("a.st_ivaven");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("TIPO");
            arlAli.add("CO_TIPRET");
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
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select co_cli,tx_nom,tx_dir,tx_tel,tx_ide,tx_tipper, co_tipper, st_ivaven  from tbm_cli as a where a.co_emp = " + objParSis.getCodigoEmpresa() +" and  a.st_reg='A' and  a.st_cli='S' order by a.tx_nom"; 
                           
            int intColOcu[]=new int[2];
            intColOcu[0]=6;
            intColOcu[1]=7;
             
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
       
    private boolean configurarVenConItem()
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
            " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" " +
            " AND co_tipdoc=1 AND co_usr="+objParSis.getCodigoUsuario()+" AND st_reg='A' " +
            " ))) THEN 'S' ELSE 'N' END  as isterL";

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            if(objParSis.getCodigoEmpresa()==2 || objParSis.getCodigoEmpresa()==4){
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
                Str_Sql+=" WHERE b3.co_emp=" + objParSis.getCodigoEmpresa();
                Str_Sql+=" AND b3.co_loc=" + objParSis.getCodigoLocal();
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
                Str_Sql+=" WHERE c1.st_reg not in ('T') and  c2.co_emp=" + objParSis.getCodigoEmpresa();
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
                vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
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
                Str_Sql+=" WHERE b3.co_emp=" + objParSis.getCodigoEmpresa();
                Str_Sql+=" AND b3.co_loc=" + objParSis.getCodigoLocal();
                Str_Sql+=" GROUP BY b1.co_itmMae";
                Str_Sql+=" ) AS a1 " +
                ", (";
                Str_Sql+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser";
                Str_Sql+=" FROM tbm_inv AS c1";
                Str_Sql+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
                Str_Sql+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                Str_Sql+=" WHERE c1.st_reg not in ('T') and  c2.co_emp=" + objParSis.getCodigoEmpresa();
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
                vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            }
                   
            //Ocultar columnas.
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.CENTER); 
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarTblDat() {
        boolean blnRes=false;
        try{
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
            vecCab.add(INT_TBL_CODLOCREL ," CODLOC");
            vecCab.add(INT_TBL_CODTIPREL ," CODTIPDOC");
            vecCab.add(INT_TBL_CODDOCREL ," CODDOC");
            vecCab.add(INT_TBL_CODREGREL ," CODREG");
            vecCab.add(INT_TBL_CODITMACT , "CodItmAct");
           
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
           //Configurar JTable: Establecer la fila de cabecera. 
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
            objCliDat = new Librerias.ZafUtil.ZafCliente_dat(objParSis); 
            
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
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
       
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
           
            HideColumn(INT_TBL_CODBOD, tcmAux);
            HideColumn(INT_TBL_BUTBOD, tcmAux);
            HideColumn(INT_TBL_CODITM, tcmAux);
            HideColumn(INT_TBL_IVATXT, tcmAux);
            HideColumn(INT_TBL_BLNPRE, tcmAux);
            HideColumn(INT_TBL_STKBOD, tcmAux);
            HideColumn(INT_TBL_ULTCOS, tcmAux);
            HideColumn(INT_TBL_UTILIDAD, tcmAux);
            HideColumn(INT_TBL_PREUNI2, tcmAux);
            HideColumn(INT_TBL_ESTADO, tcmAux);
            HideColumn(INT_TBL_CANORI, tcmAux);
            HideColumn(INT_TBL_BODORI, tcmAux);
            HideColumn(INT_TBL_CODORI, tcmAux);
            HideColumn(INT_TBL_ITMALT2, tcmAux);
            HideColumn(INT_TBL_ITMSER, tcmAux);
            HideColumn(INT_TBL_ITMTER, tcmAux);
            HideColumn(INT_TBL_CODLOCREL, tcmAux);
            HideColumn(INT_TBL_CODTIPREL, tcmAux);
            HideColumn(INT_TBL_CODDOCREL, tcmAux);
            HideColumn(INT_TBL_CODREGREL, tcmAux);
            HideColumn(INT_TBL_CODITMACT, tcmAux); 
        
            
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
            vecAux.add("" + INT_TBL_CANMOV);
            vecAux.add("" + INT_TBL_PREUNI);
            vecAux.add("" + INT_TBL_PORDES);
            vecAux.add("" + INT_TBL_DESITM);
         
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null; 
            //Configurar JTable: Editor de la tabla. 
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
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
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
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
            
            
           objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco2(tblDat, vcoItm, intColVen2, intColTbl2,objParSis);  //********
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
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen2, intColTbl2);
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
                        }
                        if (!tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                            blnChangeData = true; 
                            calculaSubtotal();
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
            ZafTblPopMnu = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            ZafTblPopMnu.setInsertarFilaVisible(false);
            ZafTblPopMnu.setInsertarFilasVisible(false);
            
        //***********************************************************
            ZafTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                @Override
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    
                    String strTipTer = ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMTER).toString());
                    int i, intFilSel[];
                    intFilSel=tblDat.getSelectedRows();
                    
                     
                       
                         
                    if (ZafTblPopMnu.isClickInsertarFila())
                    {
                        //System.out.println("beforeClick: isClickInsertarFila");
                        //Cancelar la edición cuado sea necesario.
                        if (tblDat.getSelectedRow()==1)
                            ZafTblPopMnu.cancelarClick();
                    } 
                    else if (ZafTblPopMnu.isClickEliminarFila())  
                    {
                        for (i=0; i<intFilSel.length; i++){
                            strTipTer = ((tblDat.getValueAt((intFilSel[i]), INT_TBL_ITMTER)==null)?"":tblDat.getValueAt((intFilSel[i]), INT_TBL_ITMTER).toString());
                          //  System.out.println(">> "+ strTipTer +" <> "+ (intFilSel[i])  +" >> "+ intFilSel.length +" <> "+ i );
                            if(strTipTer.trim().equals("S")){
                                 javax.swing.JOptionPane.showMessageDialog(null, "No es posible eliminar la fila por que corresponde a una terminal L .");
                                 ZafTblPopMnu.cancelarClick(); 
                                 break;
                       }}
                    }
                       
                   intFilSel=null;
                    
                }
                @Override
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (ZafTblPopMnu.isClickInsertarFila())
                    {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                       // System.out.println("afterClick: isClickInsertarFila");
                    }
                    else if (ZafTblPopMnu.isClickEliminarFila())
                    {
                        //System.out.println("afterClick: isClickEliminarFila");
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
    
    //*************************************************************************************
    private void setEditable(boolean editable)
    {
        if (editable==true){
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);  // INT_TBL_INS
        }else{
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }       
    }
    
    //*************************************************************************************
    private void HideColumn(int Columna, javax.swing.table.TableColumnModel tcmAux){
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
    private void calculaSubtotal(){
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
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtNumCot = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblCom = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVenCon = new javax.swing.JButton();
        lblfecha = new javax.swing.JLabel();
        panCotGenNorNor = new javax.swing.JPanel();
        txtNumDoc = new javax.swing.JTextField();
        lblDoc = new javax.swing.JLabel();
        txtNumFac = new javax.swing.JTextField();
        lblFac = new javax.swing.JLabel();
        lblAte = new javax.swing.JLabel();
        txtPed = new javax.swing.JTextField();
        txtAte = new javax.swing.JTextField();
        lblPed = new javax.swing.JLabel();
        burRee = new javax.swing.JButton();
        panCotGenNorSur = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtDirCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
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
        spnForPag = new javax.swing.JScrollPane();
        tblCotForPag = new javax.swing.JTable();
        panCotForPagNo = new javax.swing.JPanel();
        panCotForPagNorCen = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        cboForPag = new javax.swing.JComboBox();
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

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 135));
        panCotGenNor.setLayout(new java.awt.BorderLayout());

        pan2.setPreferredSize(new java.awt.Dimension(600, 50));
        pan2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setText("Tipo Documento:");
        pan2.add(jLabel2);
        jLabel2.setBounds(4, 12, 110, 15);

        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        pan2.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(100, 10, 64, 20);

        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        pan2.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(164, 10, 240, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("...");
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

        objParSis.getColorCamposObligatorios();
        txtNumCot.setMaximumSize(null);
        txtNumCot.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumCotActionPerformed(evt);
            }
        });
        pan2.add(txtNumCot);
        txtNumCot.setBounds(100, 30, 92, 20);

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

        txtCodVen.setBackground(objParSis.getColorCamposObligatorios());
        txtCodVen.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodVen.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        pan2.add(txtCodVen);
        txtCodVen.setBounds(515, 30, 35, 20);

        txtNomVen.setBackground(objParSis.getColorCamposObligatorios());
        txtNomVen.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        pan2.add(txtNomVen);
        txtNomVen.setBounds(550, 30, 110, 20);

        butVenCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVenCon.setText("...");
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

        txtNumDoc.setBackground(objParSis.getColorCamposSistema()
        );
        txtNumDoc.setMaximumSize(null);
        txtNumDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtNumDoc);
        txtNumDoc.setBounds(100, 0, 92, 20);

        lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDoc.setText("No. Documento:");
        panCotGenNorNor.add(lblDoc);
        lblDoc.setBounds(6, 0, 110, 15);

        txtNumFac.setBackground(objParSis.getColorCamposObligatorios()
        );
        txtNumFac.setMaximumSize(null);
        txtNumFac.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumFacActionPerformed(evt);
            }
        });
        txtNumFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumFacKeyPressed(evt);
            }
        });
        panCotGenNorNor.add(txtNumFac);
        txtNumFac.setBounds(100, 20, 92, 20);

        lblFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFac.setText("No. Factura:");
        panCotGenNorNor.add(lblFac);
        lblFac.setBounds(6, 20, 110, 15);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte);
        lblAte.setBounds(450, 0, 60, 15);
        panCotGenNorNor.add(txtPed);
        txtPed.setBounds(552, 20, 110, 20);

        txtAte.setMaximumSize(null);
        txtAte.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtAte);
        txtAte.setBounds(534, 0, 146, 20);

        lblPed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPed.setText("No. Pedido:");
        panCotGenNorNor.add(lblPed);
        lblPed.setBounds(450, 20, 72, 15);

        burRee.setText("Reemplazo de Factura");
        burRee.setToolTipText("Permite hacer el reemplazo de la factura");
        burRee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                burReeActionPerformed(evt);
            }
        });
        panCotGenNorNor.add(burRee);
        burRee.setBounds(220, 10, 160, 20);

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

        txtDirCli.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDirCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDirCli.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDirCliCaretUpdate(evt);
            }
        });
        txtDirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirCliActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(txtDirCli);
        txtDirCli.setBounds(100, 20, 320, 20);

        txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodCli.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodCli.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panCotGenNorSur.add(txtCodCli);
        txtCodCli.setBounds(100, 0, 35, 20);

        txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNomCli.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtNomCliVetoableChange(evt);
            }
        });
        panCotGenNorSur.add(txtNomCli);
        txtNomCli.setBounds(134, 0, 270, 20);

        butCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCli.setText("...");
        butCli.setPreferredSize(new java.awt.Dimension(20, 20));
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(butCli);
        butCli.setBounds(403, 0, 22, 20);

        lblGuia.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblGuia.setText("No. Guia Remisión:");
        lblGuia.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorSur.add(lblGuia);
        lblGuia.setBounds(450, 0, 100, 15);
        panCotGenNorSur.add(txtGuia);
        txtGuia.setBounds(552, 0, 80, 20);

        lblGuia1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblGuia1.setText("No. Ord.Des.:"); // NOI18N
        lblGuia1.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorSur.add(lblGuia1);
        lblGuia1.setBounds(450, 20, 100, 15);
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

        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

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

        txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblIva.setText("IVA 12%:");
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);

        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtIva);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
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

        cboForPag.setPreferredSize(new java.awt.Dimension(200, 25));
        cboForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboForPagActionPerformed(evt);
            }
        });
        panCotForPagNorCen.add(cboForPag);

        panCotForPagNo.add(panCotForPagNorCen, java.awt.BorderLayout.CENTER);

        panCotForPag.add(panCotForPagNo, java.awt.BorderLayout.NORTH);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumFacActionPerformed
    
    private void burReeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_burReeActionPerformed
        stbDocRelEmpLoc=new StringBuffer();
        stbDocRelEmpRem=new StringBuffer();
        intDocRelEmpLoc=0;

         if( verificaSolAplFac()){
              if(verificaItmUni())
                   realizaCanFac();    
         }else
              MensajeInf("ESTA FACTURA TIENE UNA SOLICITUD DE DEVOLUCION ACTIVA, NO SE PUDO REALIZAR EL REEMPLAZO DE FACTURA.");

        stbDocRelEmpLoc=null;
        stbDocRelEmpRem=null;
        intDocRelEmpLoc=0;
    }//GEN-LAST:event_burReeActionPerformed

    
    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        // TODO add your handling code here:
        strDesLarVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        // TODO add your handling code here:
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    
    private void masInformacion(boolean blnMostrar){
        double dblCosUni=0;
         double dblStock=0;
         if(blnMostrar){
             
              for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                  dblCosUni =  getCosUni2((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                  dblStock =  StockGrupo((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                  tblDat.setValueAt(new Double(dblCosUni), i, INT_TBL_ULTCOS);    
                  tblDat.setValueAt(new Double(dblStock), i, INT_TBL_STKBOD);   
              }}         
          
             MostrarCol(INT_TBL_STKBOD) ; 
             MostrarCol(INT_TBL_ULTCOS) ; 
             MostrarCol(INT_TBL_UTILIDAD); 
             MostrarCol(INT_TBL_PREUNI2) ; 
         
         }else{
             ocultaCol(INT_TBL_STKBOD) ;    
             ocultaCol(INT_TBL_ULTCOS) ;    
             ocultaCol(INT_TBL_UTILIDAD);    
             ocultaCol(INT_TBL_PREUNI2);    
         }
   
    }
    
    
    private void MostrarCol(int intCol){ 
              tblDat.getColumnModel().getColumn(intCol).setWidth(80);
              tblDat.getColumnModel().getColumn(intCol).setMaxWidth(80);
              tblDat.getColumnModel().getColumn(intCol).setMinWidth(80);
              tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(80);
              tblDat.getColumnModel().getColumn(intCol).setResizable(false); 
     }
    
     private void ocultaCol(int intCol){
              tblDat.getColumnModel().getColumn(intCol).setWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setResizable(false); 
     }
     
    
      private double getCosUni2(String int_co_itm){
       double dblPreUni = 0;
        try{
               java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                 String sSQL = "  SELECT a1.nd_cosUni"+
                               "  FROM tbm_inv AS a1"+
                               "  INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                               "  WHERE a2.co_emp="+ objParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
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
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
           
             String Str_Sql = "SELECT SUM(a2.nd_stkAct) AS nd_stkAct" +
             " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
             " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
             " WHERE a3.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
             " WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_itm="+ int_co_itm +")";

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
      
     
     
      
    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtNumCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumCotActionPerformed
        // TODO add your handling code here:
         buscarCotizacion();
        
    }//GEN-LAST:event_txtNumCotActionPerformed

    public void buscarCotizacion(){
                   Connection conCot;
                   Statement  stmCot;
                   ResultSet rstCotCab;
                   try{//odbc,usuario,password
                            conCot=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                            if (conCot!=null){
                                stmCot=conCot.createStatement();
                                                             
                                 // Agregando el Sql de Consulta para la cotizacion
                               
                                 sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                                      " Usr.co_usr as co_com, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                                      " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                                      " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                                      " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                                      " CotCab.st_reg " + // Campo para saber si esta anulado o no
                                      " FROM tbm_cabCotven as CotCab left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                      " Where CotCab.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                                      " and CotCab.co_loc = " + objParSis.getCodigoLocal() +// Consultando en el local en el ke se esta trabajando
                                      " and CotCab.co_cot = " + txtNumCot.getText();
                                
                             
                                rstCotCab=stmCot.executeQuery(sSQL);
                                if(rstCotCab.next()){
                                    cargarRegdeCotizacion(rstCotCab);
                                     java.awt.Color color = txtNumCot.getBackground();
                                                             txtNumCot.setEditable(false);
                                                             txtNumCot.setBackground(color);
                                       objCliDat.setCliente(Integer.parseInt( txtCodCli.getText()  ));                       
                                }
                                else{
                                    clnTextos();
                                }
                                
                                rstCotCab.close();
                                stmCot.close();
                                conCot.close();
                                
                                rstCotCab = null;
                                stmCot = null;
                                conCot = null;
                            }
                   }
                   catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
                   catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); }                               
    }
    
    
    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
          listaTipdoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed

      private void listaTipdoc(String campo,String strDesBusqueda ,int intTipo){
           vcoTipDoc.setTitle("Listado Tipos de Documentos"); 
                 switch (intTipo){
                  case 0:
                  break;
                  case 1:
                        vcoTipDoc.setCampoBusqueda(1);
                        if (vcoTipDoc.buscar(campo, strDesBusqueda )){ }
                  break;
                  case 2:
                        vcoTipDoc.setCampoBusqueda(2); 
                        if (vcoTipDoc.buscar(campo, strDesBusqueda )){ }                
                   break;
                }
                 vcoTipDoc.cargarDatos();
                 vcoTipDoc.show();
              if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
              {
               if(!vcoTipDoc.getValueAt(1).equals("")){
                   cargarCabTipoDoc(Integer.parseInt(vcoTipDoc.getValueAt(1)));            
                
                
                }
             }      
    }
    
    
     private void cargarCabTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtTipDocCod.setText(""+objTipDoc.getco_tipdoc());
        txtDesLarTipDoc.setText(objTipDoc.gettx_deslar());
        txtDesCorTipDoc.setText(objTipDoc.gettx_descor());        
       
        objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
       
        
        // getIva();
    }
      
    private void cboForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboForPagActionPerformed
        // TODO add your handling code here:
        
        
          CalculoPago();
        
        
    }//GEN-LAST:event_cboForPagActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
          configurarTblDat();
      
          
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
          vcoItm.dispose(); //***
          vcoItm=null;  //****
          vcoVen.dispose();
          vcoVen=null;  
          vcoCli.dispose();
          vcoCli=null;
          
         objUti=null;
         objCiuDat=null;
         objTooBar=null;
         objAutPrg=null;
         objZafRep=null;
         objLisCam=null;
         objInvItm=null;
         objParSis=null;
         txtFecDoc=null;
        
         objTblCelEdiTxtVcoItm=null;
         objUltDocPrintVen24=null;
       ///  System.out.println("CERRANDO LOS OBJETOS..");
        }
         catch (Exception e)
         {
          objUti.mostrarMsgErr_F1(this, e);
         }
        
        
       
     }
    
    
    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
         if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                 BuscarCliente("a.tx_nom",txtNomCli.getText(),1);
            }
        }
        else
            txtNomCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        // TODO add your handling code here:
          if (!txtNomVen.getText().equalsIgnoreCase(strDesLarVen))
         {
            if (txtNomVen.getText().equals(""))
            {
               txtCodVen.setText("");
               txtNomVen.setText("");
            }
            else
            {
                 BuscarVendedor("a.tx_nom",txtNomVen.getText(),1);
            }
        }
        else
            txtNomVen.setText(strDesLarVen);  
      
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtDirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirCliActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtDirCliCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDirCliCaretUpdate

    }//GEN-LAST:event_txtDirCliCaretUpdate

    private void txtNomCliVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtNomCliVetoableChange
        // Prueba
    }//GEN-LAST:event_txtNomCliVetoableChange

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
 
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                  BuscarCliente("a.co_cli",txtCodCli.getText(),0);
            }
        }
        else
            txtCodCli.setText(strCodCli);
      
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
   
           if (!txtCodVen.getText().equalsIgnoreCase(strCodVen))
         {
            if (txtCodVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                BuscarVendedor("a.co_usr",txtCodVen.getText(),0);
            }
        }
        else
            txtCodVen.setText(strCodVen);  
        
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
         txtNomCli.transferFocus();
       
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
           txtCodVen.transferFocus();
        
    }//GEN-LAST:event_txtCodVenActionPerformed

  
  private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
       txtCodCli.transferFocus(); 
     
  }//GEN-LAST:event_txtCodCliActionPerformed
 

    
    public void BuscarCliente(String campo,String strBusqueda,int tipo){
       vcoCli.setTitle("Listado de Clientes"); 
        if (vcoCli.buscar(campo, strBusqueda ))
        {
              txtCodCli.setText(vcoCli.getValueAt(1));
              txtNomCli.setText(vcoCli.getValueAt(2));
              txtDirCli.setText(vcoCli.getValueAt(3));
              strTipPer_glo = vcoCli.getValueAt(6);
              strCodTipPerCli =  vcoCli.getValueAt(7);  
              stIvaVen=vcoCli.getValueAt(8);  
              FndVenToCli();
              CalculoPago(); 
        }
        else
        {     vcoCli.setCampoBusqueda(tipo);
              vcoCli.cargarDatos();
              vcoCli.show();
             if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCodCli.setText(vcoCli.getValueAt(1));
                txtNomCli.setText(vcoCli.getValueAt(2));
                txtDirCli.setText(vcoCli.getValueAt(3));
                strTipPer_glo = vcoCli.getValueAt(6);
                strCodTipPerCli =  vcoCli.getValueAt(7);   
                stIvaVen=vcoCli.getValueAt(8);  
                FndVenToCli();
                CalculoPago(); 
             }
              else{
                    txtCodCli.setText(strCodCli);
                    txtNomCli.setText(strDesLarCli);
                  }
        }
         
          if(!txtCodCli.getText().equals(""))
             objCliDat.setCliente(Integer.parseInt( txtCodCli.getText()  ));
           else
               objCliDat = new Librerias.ZafUtil.ZafCliente_dat(objParSis);
   
       
     
   }
    
  
    
    
     private void CalculoPago(){
           try{
                    java.sql.Connection conPag = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if (conPag!=null){     

                         if(cboForPag.getSelectedIndex()!=-1){

                          String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre , A2.st_sop " +
                                          " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) +// Clausulas Where para las tablas maestras
                                          "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                                          "       and A1.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                                          "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                                          "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                            String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
                                          " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) +// Clausulas Where para las tablas maestras
                                          "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                                          "       and A1.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
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
                           }
                         conPag.close();
                         conPag = null ;
                    }
                }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
        
         calculaPagos();    
     }
    
    
    
  
  private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed

          txtNomVen.transferFocus();
   
  }//GEN-LAST:event_txtNomVenActionPerformed
    
 

   private void FndVenToCli(){
            /*
             * Obteniendo el maximo porcentaje de descuento para el cliente 
             */
                try{
                    if(objTooBar.getEstado() == 'n' ||  objTooBar.getEstado()=='m'){
                        java.sql.Connection conVenToCli = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if (conVenToCli!=null){   
                            Statement stmCliVen = conVenToCli.createStatement();
                            ResultSet rstCliVen = stmCliVen.executeQuery(
                                        "select cli.co_ven, ven.tx_nom from tbm_cli as cli , tbm_usr as ven where " +
                                               " cli.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                                               " cli.co_cli = " + Integer.parseInt(txtCodCli.getText()) +  " and " +
                                               " ven.co_usr = cli.co_ven "                        
                            );

                            if(rstCliVen.next()){
                                txtCodVen.setText(rstCliVen.getInt("co_ven")+"");
                                txtNomVen.setText(
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
               
               catch(SQLException Evt)
               {
                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
                }

                catch(Exception Evt)
                {
                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
                }                       
                              
        
    }
   
   
    /*
     * Agrega el listener para detectar que hubo algun cambio en la caja de texto
     */
    private void addListenerCambio(){
        objLisCam = new LisTextos();
        //Cabecera
            txtNumCot.setText("");
            txtNumCot.getDocument().addDocumentListener(objLisCam);
            txtCodCli.getDocument().addDocumentListener(objLisCam);
            txtNomCli.getDocument().addDocumentListener(objLisCam);
            txtDirCli.getDocument().addDocumentListener(objLisCam);
            //txtFecDoc.getDocument().addDocumentListener(objLisCam);
            txtCodVen.getDocument().addDocumentListener(objLisCam);
            txtNomVen.getDocument().addDocumentListener(objLisCam);
            txtAte.getDocument().addDocumentListener(objLisCam);
        

        //Pie de pagina
            txaObs1.getDocument().addDocumentListener(objLisCam);
            txaObs2.getDocument().addDocumentListener(objLisCam);
            txtSub.getDocument().addDocumentListener(objLisCam);
            txtIva.getDocument().addDocumentListener(objLisCam);
            //txtDes.setText("0");
            txtTot.getDocument().addDocumentListener(objLisCam);
        
    }   
    
    /*
     * Clase de tipo documenet listener para detectar los cambios en 
     * los textcomponent
     */
    class LisTextos implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent e) {
            blnHayCam = true;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            blnHayCam = true;
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            blnHayCam = true;
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
        vcoVen.setTitle("Listado de vendedores"); 
        if (vcoVen.buscar(campo, strBusqueda ))
        {
            txtCodVen.setText(vcoVen.getValueAt(1));
            txtNomVen.setText(vcoVen.getValueAt(2));
        }
        else
        {     vcoVen.setCampoBusqueda(tipo);
              vcoVen.cargarDatos();
              vcoVen.show();
             if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
             {
                txtCodVen.setText(vcoVen.getValueAt(1));
                txtNomVen.setText(vcoVen.getValueAt(2));
             }
              else{
                    txtCodVen.setText(strCodVen);
                    txtNomVen.setText(strDesLarVen);
                  }
        }
      
    }
    
   
    
    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        
               vcoCli.setTitle("Listado de Clientes");         
              vcoCli.setCampoBusqueda(1);
              vcoCli.show();
             if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCodCli.setText(vcoCli.getValueAt(1));
                txtNomCli.setText(vcoCli.getValueAt(2));
                txtDirCli.setText(vcoCli.getValueAt(3));
                strTipPer_glo = vcoCli.getValueAt(6);
                strCodTipPerCli =  vcoCli.getValueAt(7);  
                stIvaVen=vcoCli.getValueAt(8);  
                FndVenToCli();
                 CalculoPago();
             } 
        
    }//GEN-LAST:event_butCliActionPerformed

    private void txtNumFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFacKeyPressed
	// TODO add your handling code here:
       if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
            objTooBar.consultar();
            objTooBar.setEstado('w');
        }
    }//GEN-LAST:event_txtNumFacKeyPressed
  /*
   * Obtiene el Iva que se debe cobrar en la empresa actual
   */

    
   
   
      private double getIva(java.sql.Connection ConTem){
            double dblIvaActual = 0;
              try{
                    if (ConTem!=null){
                        stmCot=ConTem.createStatement();
                        sSQL= "SELECT nd_ivaVen as porIva from tbm_emp " +
                               "where co_emp = " + objParSis.getCodigoEmpresa();  //<== Verificando que seleccione la empresa actual
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
        java.awt.Color colBack = txtNumCot.getBackground();

            txtNumDoc.setEditable(blnEditable);
            
            colBack = txtSub.getBackground();
      

            txtSub.setEditable(blnEditable);
            txtSub.setBackground(colBack);        
            
            txtIva.setEditable(blnEditable);
            txtIva.setBackground(colBack);        
            
            txtTot.setEditable(blnEditable);
            txtTot.setBackground(colBack);        
    }
  
    public void  clnTextos(){
          if(blnClean){
        //Cabecera
           txtTipDocCod.setText("");
           txtDesCorTipDoc.setText("");
           txtDesLarTipDoc.setText("");
           txtNumFac.setText("");
           txtGuia.setText("");
           txtNumDoc.setText("");
           txtAte.setText("");
           txtPed.setText("");
          
           strCodCli="";
              strDesLarCli="";   
              strCodCli="";
              strDesLarCli="";
           
           
            txtNumCot.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtDirCli.setText("");
            txtFecDoc.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            
        
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
            while(tblCotForPag.getRowCount()>0)
                ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).removeRow(0);

            
            
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
     
//        if(stIvaVen.equals("N") ){
//             txtSub.setText( "" + dblSubtotalCot );
//             txtIva.setText( "0.00" );
//             txtTot.setText( ""+ dblSubtotalCot );
//             dblTotalCot=dblSubtotalCot;
//               dblIvaCot=0;
//        }else{
           txtSub.setText( "" + dblSubtotalCot );
           txtIva.setText( "" + dblIvaCot );
           txtTot.setText( ""+ dblTotalCot);
//        }
        /////////////////////////////////////////////////////////////
        
    }   
 
  
   
   
   
   
    public String getUnidad(String int_co_itm){
           String strDesUni = "";
           try{
                  java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                   if (conUni!=null){
                        java.sql.Statement stmUni = conUni.createStatement();
                         String sSQL= "SELECT var.tx_descor from tbm_inv as inv " +
                                        " inner join tbm_var as var on (inv.co_uni = var.co_reg) "+ 
                                        " where inv.co_emp = " + objParSis.getCodigoEmpresa() +  " and " +
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
   
        private boolean FormaRetencion(java.sql.Connection conTmp){
         boolean blnRes=false; 
         java.sql.Statement stmTmp;
         java.sql.ResultSet rst;
         try
          {
           if (conTmp!=null)
           {
              stmTmp = conTmp.createStatement();
              String sql = "SELECT co_mot FROM tbm_motdoc WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND tx_tipmot='B'";
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
    
 private boolean refrescaDatos(java.sql.Connection conn){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strAux="";
  String strSql="";
  double dblCan=0, dblPre=0, dblPorDes=0, dblValDes=0, dblTotal=0;
  String strUnidad="",strCodAlt="",strSer="", strTer="";
  try{
    if(conn!=null){
     if(RSTCAB != null){
       stmLoc=conn.createStatement();  
          
       strAux = ",CASE WHEN (" +
       " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
       " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+ objParSis.getCodigoLocal() +" " +
       " AND co_tipdoc=1 AND co_usr="+objParSis.getCodigoUsuario()+" AND st_reg='A' " +
       " ))) THEN 'S' ELSE 'N' END  as isterL";

       strSql="SELECT a.tx_codalt, a.tx_unimed, a.tx_nomitm, a.co_bod, a.nd_can, a.nd_preuni , inv.st_ser " +
       " ,a.nd_pordes, a.st_ivaCom, a.co_itm, a.tx_codalt2 ";
       strSql +=strAux;
       //strSql +=" ,tr.co_locrel ,tr.co_tipdocrel , tr.co_docrel  ,tr.co_regrel "
       strSql +=" , a.co_itmact  FROM tbm_detMovInv AS a  " +
       " INNER JOIN tbm_inv AS inv on (a.co_emp = inv.co_emp and a.co_itm = inv.co_itm) " +
       //" LEFT JOIN tbr_detmovinv AS tr ON (tr.co_emp=a.co_emp and tr.co_loc=a.co_loc and tr.co_tipdoc=a.co_tipdoc and tr.co_doc=a.co_doc and tr.co_reg=a.co_reg) "+
       " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and "+
       " a.co_loc="+objParSis.getCodigoLocal()+" and a.co_doc="+RSTCAB.getInt("co_doc")+" and " +
       " a.co_tipDoc="+txtTipDocCod.getText()+" order by a.co_reg";
       rstLoc=stmLoc.executeQuery(strSql);              
       Vector vecData = new Vector();
       while(rstLoc.next()){
         java.util.Vector vecReg = new java.util.Vector();
         strCodAlt =  rstLoc.getString("tx_codalt"); // (rstAux.getString("tx_codalt")==null?"":rstAux.getString("tx_codalt"));
         strUnidad = rstLoc.getString("tx_unimed"); // (rstAux.getString("tx_descor")==null?"":rstAux.getString("tx_descor"));
         strSer = (rstLoc.getString("st_ser")==null?"":rstLoc.getString("st_ser"));
         strTer = (rstLoc.getString("isterl")==null?"":rstLoc.getString("isterl"));

         vecReg.add(INT_TBL_LINEA, "");
         vecReg.add(INT_TBL_ITMALT, strCodAlt);
         vecReg.add(INT_TBL_BUTITM, "");
         vecReg.add(INT_TBL_DESITM, rstLoc.getString("tx_nomitm"));
         vecReg.add(INT_TBL_UNIDAD, strUnidad);
         vecReg.add(INT_TBL_CODBOD, new Integer(rstLoc.getInt("co_bod")));
         vecReg.add(INT_TBL_BUTBOD, "");
         dblCan    = Math.abs(rstLoc.getDouble("nd_can")) ;
         vecReg.add(INT_TBL_CANMOV, new Double(dblCan));                                                                           
         vecReg.add(INT_TBL_STKBOD,"");
         vecReg.add(INT_TBL_ULTCOS,"");
         vecReg.add(INT_TBL_PREUNI, new Double(rstLoc.getDouble("nd_preuni")));
         vecReg.add(INT_TBL_UTILIDAD,"");
         vecReg.add(INT_TBL_PREUNI2,"");
         vecReg.add(INT_TBL_PORDES, new Double(rstLoc.getDouble("nd_pordes")));
         String strIva = rstLoc.getString("st_ivaCom");
         Boolean blnIva = strIva.equals("S")?true:false;                         
         vecReg.add(INT_TBL_BLNIVA, blnIva);
         dblPre    = rstLoc.getDouble("nd_preuni");
         dblPorDes = rstLoc.getDouble("nd_pordes");
         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
         dblTotal  = (dblCan * dblPre)- dblValDes ;
         dblTotal =  objUti.redondear(dblTotal,3);
         dblTotal =  objUti.redondear(dblTotal,2);
         vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
         vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
         vecReg.add(INT_TBL_ESTADO, "E");
         vecReg.add(INT_TBL_IVATXT, "");
         vecReg.add(INT_TBL_BLNPRE,"");
         vecReg.add(INT_TBL_CANORI,new Double(dblCan));
         vecReg.add(INT_TBL_BODORI,new Integer(rstLoc.getInt("co_bod")));
         vecReg.add(INT_TBL_CODORI,rstLoc.getString("co_itm"));
         vecReg.add(INT_TBL_ITMALT2, rstLoc.getString("tx_codalt2"));
         vecReg.add(INT_TBL_ITMSER, strSer );
         vecReg.add(INT_TBL_ITMTER, strTer );
         vecReg.add(INT_TBL_CODLOCREL, null ); // rstLoc.getString("co_locrel") );
         vecReg.add(INT_TBL_CODTIPREL, null ); //rstLoc.getString("co_tipdocrel") );
         vecReg.add(INT_TBL_CODDOCREL, null ); //rstLoc.getString("co_docrel") );
         vecReg.add(INT_TBL_CODREGREL, null ); //rstLoc.getString("co_regrel") );
         vecReg.add(INT_TBL_CODITMACT, rstLoc.getString("co_itmact") );
         vecData.add(vecReg);                         
      }
      rstLoc.close();
      rstLoc=null;
      
     objTblMod.setData(vecData);
     tblDat.setModel(objTblMod);                                 
    
     calculaTotal(dblPorIva);
                  
     lblCotNumDes.setText("Factura No. " + txtNumFac.getText() +  " (" + txtNomCli.getText() + ") ");
     lblIva.setText("IVA " + dblPorIva + "%");
                    
     String strCo_ForPag = (RSTCAB.getString("co_forPag")==null)?"":RSTCAB.getString("co_forPag");
     objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                            objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag, strCo_ForPag);      
     refrescaPagos(conn, RSTCAB.getInt("co_doc"), RSTCAB.getInt("co_forPag"));
     objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal() , Integer.parseInt(txtTipDocCod.getText()), RSTCAB.getInt("co_doc"));
                        
    /*
     * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
     */
        String strStatus = RSTCAB.getString("st_reg");

           switch(strStatus.charAt(0)){
                case 'I':
                    objTooBar.setEstadoRegistro("Anulado");    
                    break;
                case 'A':
                    objTooBar.setEstadoRegistro("Activo");    
                    break;
                case 'P':
                    objTooBar.setEstadoRegistro("Pendiente");    
                    break;
                case 'D':
                    objTooBar.setEstadoRegistro("Denegado");    
                    break;
                case 'R':
                    objTooBar.setEstadoRegistro("Falta Imprimir");    
                    break;
                default:
                    objTooBar.setEstadoRegistro(strStatus);    
                    break;
            }
                    
        stmLoc.close();
        stmLoc=null;
        blnRes=true;       
        blnHayCam = false; // Seteando que no se ha hecho cambios

     }}}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  return blnRes;     
 }
    
 
 
      private void refrescaPagos(java.sql.Connection con, int intNumeroDocumento, int intCo_ForPag){
      
        try{       
                    java.sql.Statement stmDetPag = con.createStatement();
                    java.sql.ResultSet rstDetPag ;
                    String strCo_ForPag = intCo_ForPag+"";
                    /*
                     * LLenando el combo de descripciones de pagos 
                     */
                    cboForPag.setSelectedIndex(vecForPag.indexOf(strCo_ForPag));

                    //Extrayendo los datos del detalle respectivo a ESTE PAGO
                    String sqlDetPag  = "Select * from tbm_pagMovInv where " +
                                        " co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                                        " co_loc = " + objParSis.getCodigoLocal()+ " and " +
                                        " co_doc = " + intNumeroDocumento              + " and " +
                                        " co_tipDoc =" + txtTipDocCod.getText()        + " and st_reg IN ('A','C') order by co_reg";
                                
                    rstDetPag = stmDetPag.executeQuery(sqlDetPag);
                    
                        
                    //Borrando los datos actuales del jtable
                    
                    javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
                    modTbl.setRowCount(0);
                    
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
                         
                        ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(dataPagos);    
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
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
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
  
    private boolean cargarRegdeCotizacion(ResultSet rstCotCab)
    {
       boolean blnRes=true;
        try
        {
            if (cargarCabReg2(rstCotCab))
            { 
                //System.out.println("Aqui........");
                refrescaDatos2(rstCotCab);  
                generaAsiento();
            }
            else
            {
                MensajeInf("Error al cargar registro");
                blnHayCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }        
    
      
 
  private boolean cargarCabReg2(ResultSet rstCotCab)
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";
                strSQL= "SELECT cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                      " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                      " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                      " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                      " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                      " CotCab.st_reg, CotCab.ne_val " + // Campo para saber si esta anulado o no
                      " FROM tbm_cabCotVen as CotCab left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                      " Where  CotCab.co_emp=" +  rstCotCab.getString("co_emp") +
                      " AND CotCab.co_loc=" +  rstCotCab.getString("co_loc") +
                      " AND CotCab.co_cot=" +  rstCotCab.getString("co_cot") ;
                
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtNumCot.setText(rst.getString("co_cot"));                   
                    txtCodCli.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtDirCli.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    
                    
                    String sql = "select tx_tipper from tbm_cli where co_cli="+ txtCodCli.getText() +" and co_emp="+objParSis.getCodigoEmpresa();
                    java.sql.ResultSet rst2=stm.executeQuery(sql);
                     if (rst2.next())
                         strTipPer_glo=rst2.getString(1);
                    
                   
                    
                    rst2.close();
                    rst2=null;
                    
                    //*****************
                     intNumDiaVal = rst.getInt("ne_val");  
                     dateFecCot = objUti.formatearFecha(rst.getDate("fe_cot"),"dd/MM/yyyy");
                     intTipMod = 2; 
                    //*****************
                    
                    if(rst.getDate("fe_cot")==null){
                      txtFecDoc.setText("");  
                    }else{
                        java.util.Date dateObj = rst.getDate("fe_cot");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                    }

                    txtCodVen.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                    txtNomVen.setText(((rst.getString("nomcom")==null)?"":rst.getString("nomcom")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
                    double dblSub = ((rst.getString("nd_sub")==null)?0:objUti.redondear(rst.getDouble("nd_sub"),objParSis.getDecimalesMostrar()));
                    txtSub.setText(""+dblSub);
                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    strstReg_glo = strAux; 
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
                }
            
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
           
                blnHayCam=false;
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

      
       
    private void refrescaDatos2(ResultSet rstCotCab){
        try{
            int intNumCot = 0;
            java.sql.Connection con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());                                                                       
                if(rstCotCab != null){
                //Detalle        
                   
                    
                     String strAux = ",CASE WHEN (" +
                   " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                   " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" " +
                   " AND co_tipdoc=1 AND co_usr="+objParSis.getCodigoUsuario()+" AND st_reg='A' " +
                   " ))) THEN 'S' ELSE 'N' END  as isterL";
 
                        
                    
                    sSQL  = "SELECT a.tx_codalt, a.tx_codalt2, var.tx_descor ,a.tx_nomitm, a.co_bod, a.nd_can ,a.nd_preuni " +
                    " ,a.nd_pordes, a.st_ivaven, a.co_itm , inv.st_ser "; 
                    sSQL +=strAux;
                    sSQL +="  FROM tbm_detCotven AS a " +
                    "  INNER JOIN tbm_inv AS inv ON (a.co_emp = inv.co_emp and a.co_itm = inv.co_itm) " +
                    "  LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                    "WHERE " +
                            " a.co_emp = "+objParSis.getCodigoEmpresa()+" and " +
                            " a.co_loc = "+objParSis.getCodigoLocal()+"   and " +
                            " a.co_cot = "+rstCotCab.getInt("co_cot")+" order by a.co_reg";
                    
                   
                  //  System.out.println("Aqui........"+ sSQL );
                    
                    java.sql.Statement stmCab=con.createStatement();
                    java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                     double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                                           
                    java.sql.Statement stmAux;
                    java.sql.ResultSet rstAux;
                    stmAux = con.createStatement();
                    String strUnidad="",strCodAlt="", strCodAlt2="",strSer="", strTer="";
                    Vector vecData = new Vector();
                    for(int i=0 ; rst.next() ; i++){
                         java.util.Vector vecReg = new java.util.Vector();
                         
//                         strSQL = "";
//                         strSQL = " Select detcot.tx_codalt,detcot.tx_codalt2,tx_descor from tbm_detcotven as detcot left outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) left outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
//                                 " where detcot.co_emp  = " + objParSis.getCodigoEmpresa() +" and detcot.co_loc = " + objParSis.getCodigoLocal() + " and detcot.co_cot = " + rst.getInt("co_cot") +" and detcot.co_itm = " + rst.getString("co_itm") ;                        
//                         
//                        rstAux = stmAux.executeQuery(strSQL);
//                         if (rstAux.next())
//                         {
                             strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                             strCodAlt2 = (rst.getString("tx_codalt2")==null?"":rst.getString("tx_codalt2"));
                             strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                             strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                             strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                             
//                         }
//                         rstAux.close();

                         vecReg.add(INT_TBL_LINEA, "");
                         vecReg.add(INT_TBL_ITMALT, strCodAlt);
                         vecReg.add(INT_TBL_BUTITM, "");
                         vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                         vecReg.add(INT_TBL_UNIDAD, strUnidad);
                         vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                         vecReg.add(INT_TBL_BUTBOD, "");
                         vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));                                                                           
                        
                         vecReg.add(INT_TBL_STKBOD,"");
                         vecReg.add(INT_TBL_ULTCOS,"");
                         
                         vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                         
                         vecReg.add(INT_TBL_UTILIDAD,"");
                         vecReg.add(INT_TBL_PREUNI2,"");
                         
                         vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                         String strIva = rst.getString("st_ivaven");
                         Boolean blnIva = strIva.equals("S")?true:false;                         
                         vecReg.add(INT_TBL_BLNIVA, blnIva);
                         dblCan    = rst.getDouble("nd_can");
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
                         
                          vecReg.add(INT_TBL_ITMALT2, strCodAlt2);
                          vecReg.add(INT_TBL_ITMSER, strSer );
                          vecReg.add(INT_TBL_ITMTER, strTer );
                          
                          vecReg.add(INT_TBL_CODLOCREL, "" );
                          vecReg.add(INT_TBL_CODTIPREL, "" );
                          vecReg.add(INT_TBL_CODDOCREL, "" );
                          vecReg.add(INT_TBL_CODREGREL, "" );
                          
                          
                         vecData.add(vecReg);                         
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);                                 
                    stmAux.close();
                    rst.close();
                    calculaTotal(dblPorIva);
                    lblCotNumDes.setText("Factura No. " + txtNumFac.getText() +  " (" + txtNomCli.getText() + ") ");
                    lblIva.setText("IVA " + dblPorIva + "%");
                    
                    
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */
                     
                    String strCo_ForPag = (rstCotCab.getString("co_forPag")==null)?"":rstCotCab.getString("co_forPag") ;
                    
                    
                    
                    //Extrayendo los datos del detalle respectivo a ESTE PAGO
                    sSQL  = "Select * from tbm_pagCotCom where " +
                            " co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                            " co_loc = " + objParSis.getCodigoLocal()   + " and " +
                            " co_cot = " + intNumCot                       + " order by co_reg";
                    
                    rst = stmAux.executeQuery(sSQL);
                    
                    /*
                     * LLenando el combo de descripciones de pagos 
                     * 
                     */
                    objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                            objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag, strCo_ForPag);      
                    
                    
                    
                    
                    
                    calculaPagos();
              
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                    String strStatus = rstCotCab.getString("st_reg");
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
                    con.close();                    
                }
            blnHayCam = false; // Seteando que no se ha hecho cambios

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
    
   
          
      
      
      //*****************************/*//////////////////////////////////////////////////////////////
      
      
private boolean cargarReg(java.sql.Connection conn){
 boolean blnRes=false;
 try{
     if(cargarCabReg(conn)){ 
       if(refrescaDatos(conn)){
           //burRee.setEnabled(true);
        blnRes=true;
      }}
       
      if(!blnRes){
         MensajeInf("Error al cargar registro");
         blnHayCam=false;
     }
  }catch (Exception evt){ objUti.mostrarMsgErr_F1(jfrThis, evt); blnRes=false; }
  return blnRes;
 }        
    
    
    

    
//    
//  private boolean cargarCabReg()
//    {
//        int intPosRel;
//        boolean blnRes=true;
//        try
//        {
//            java.sql.Connection con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null)
//            {
//                java.sql.Statement stm=con.createStatement();
//                String strSQL="";
//               
//                 int intNumCot = 0;
//                intNumCot = rstCab.getInt("co_doc");
//                
//                 strSQL= "SELECT DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
//                          " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
//                          " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
//                          " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numped, DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
//                          " DocCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
//                          " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
//                          " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
//                          " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra, DocCab.nd_poriva, DocCab.ne_secgrp "   + //Campos del Tab OTROS 
//                          " ,DocCab.st_regrep   FROM tbm_cabMovInv as DocCab " + // Tabla enla cual se trabajara y sus respectivo alia
//                          " Where  DocCab.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
//                          "        and DocCab.co_loc = " + objParSis.getCodigoLocal() + 
//                          "   and DocCab.st_reg not in ('E')  and DocCab.co_doc = " + intNumCot +// Consultando en el local en el ke se esta trabajando
//                          "        and DocCab.co_tipdoc = " + rstCab.getInt("co_tipdoc") ;// Consultando en el local en el ke se esta trabajando
//
//                
//                 
//                
//                java.sql.ResultSet rst=stm.executeQuery(strSQL);
//                if (rst.next())
//                {
//                   
//                       dblPorIva=rst.getDouble("nd_poriva");  //)?0:rst.getDouble("nd_poriva");
//                     
//                        STR_ESTREG=rst.getString("st_regrep");
//                        txtDoc.setText(""+intNumCot);
//                        txtTipDocCod.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
//                        txtCot.setText(""+rst.getInt("ne_numCot"));
//                        txtFac.setText(""+rst.getInt("ne_numDoc"));
//                        txtPed.setText(((rst.getString("tx_numped")==null)?"":rst.getString("tx_numped")+""));
//                        txtGuia.setText(""+rst.getInt("ne_numGui"));
//                        txtCliCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
//                        txtCliNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
//                        txtCliDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
//
//                       
//                        Glo_intCodSec = rst.getInt("ne_secgrp");
//                        
//                    
//                    String sql = "select tx_tipper from tbm_cli where co_cli="+ txtCliCod.getText() +" and co_emp="+objParSis.getCodigoEmpresa();
//                    java.sql.ResultSet rst2=stm.executeQuery(sql);
//                     if (rst2.next())
//                         strTipPer_glo=rst2.getString(1);
//                    
//                   
//                    
//                    rst2.close();
//                    rst2=null;
//                    
//                    //*****************
//                   
//                     dateFecCot = objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy");
//                     intTipMod = 2; 
//                    //*****************
//                        if(rst.getDate("fe_doc")==null){
//                          txtFecDoc.setText("");  
//                          lblfecha.setText("");
//                        }else{
//                            java.util.Date dateObj = rst.getDate("fe_doc");
//                            java.util.Calendar calObj = java.util.Calendar.getInstance();
//                            calObj.setTime(dateObj);
//                            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
//                                              calObj.get(java.util.Calendar.MONTH)+1     ,
//                                              calObj.get(java.util.Calendar.YEAR)        );
//                            
//                            lblfecha.setText(dateFecCot);
//                            
//                        }
//
//                    
//                    
//                        txtVenCod.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
//                        txtVenNom.setText(((rst.getString("nomven")==null)?"":rst.getString("nomven")));
//                        txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
//                        
//                 
//                        
//                   //Pie de pagina
//                     txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
//                     txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
//                    
//                     
//                      /*
//                     * CARGANDO DATOS DEL TAB MOTIVO DE TRANSPORTE
//                     */
//                        txtPartida.setText(((rst.getString("tx_ptoPar")==null)?"":rst.getString("tx_ptoPar")));
//                        txtTransporte.setText(((rst.getString("tx_tra")==null)?"":rst.getString("tx_tra")));
//                        cboMotivoTransporte.setSelectedIndex(vecTransporte.indexOf(rst.getInt("co_motTra")+""));   
//                     
//                     
//                    //Mostrar el estado del registro.
//                    strAux=rst.getString("st_reg");
//                    strstReg_glo = strAux; 
//                    if (strAux.equals("A"))
//                        strAux="Activo";
//                    else if (strAux.equals("I"))
//                        strAux="Anulado";
//                    else
//                        strAux="Otro";
//                    objTooBar.setEstadoRegistro(strAux);
//                }
//                else
//                {
//                    objTooBar.setEstadoRegistro("Eliminado");
//                    clnTextos();
//                }
//            
//                rst.close();
//                stm.close();
//                con.close();
//                rst=null;
//                stm=null;
//                con=null;
//                //Mostrar la posición relativa del registro.
//                intPosRel=rstCab.getRow();
//                rstCab.last();
//                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
//                rstCab.absolute(intPosRel);
//                blnHayCam=false;
//                
//            }
//        }
//        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//        return blnRes;
//    }
//
//    

    
private boolean cargarCabReg(java.sql.Connection conn){
  boolean blnRes=true;
  int intPosRel;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
      if(conn!=null){
          stmLoc=conn.createStatement();
                 
          strSql="SELECT DocCab.ne_numorddes,  DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
          " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
          " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
          " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numped, DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
          " DocCab.co_forPag, DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
          " DocCab.st_reg, DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra, DocCab.nd_poriva, DocCab.ne_secgrp "   + //Campos del Tab OTROS 
          " ,DocCab.st_regrep  ,Cli.tx_tipper , Cli.co_tipper, Cli.st_ivaven  FROM tbm_cabMovInv as DocCab " +
          " LEFT JOIN  tbm_cli AS Cli on (cli.co_emp = DocCab.co_emp  AND Cli.co_cli = DocCab.co_cli) " + 
          " Where  DocCab.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
          " and DocCab.co_loc = " +objParSis.getCodigoLocal() +
          " and DocCab.st_reg not in ('E')  and DocCab.co_doc = " +RSTCAB.getInt("co_doc")+// Consultando en el local en el ke se esta trabajando
          " and DocCab.co_tipdoc = " + RSTCAB.getInt("co_tipdoc") ;// Consultando en el local en el ke se esta trabajando
          rstLoc=stmLoc.executeQuery(strSql);
          if(rstLoc.next()){
            dblPorIva=rstLoc.getDouble("nd_poriva");  
            STR_ESTREG=rstLoc.getString("st_regrep");
            txtNumDoc.setText(""+rstLoc.getString("co_doc"));
            txtTipDocCod.setText(((rstLoc.getString("co_tipdoc")==null)?"":rstLoc.getString("co_tipdoc")));
            txtNumCot.setText(""+rstLoc.getInt("ne_numCot"));
            txtNumFac.setText(""+rstLoc.getInt("ne_numDoc"));
            txtPed.setText(((rstLoc.getString("tx_numped")==null)?"":rstLoc.getString("tx_numped")+""));
            txtGuia.setText(""+rstLoc.getInt("ne_numGui"));
            txtOD.setText(""+rstLoc.getInt("ne_numorddes"));
            txtCodCli.setText(((rstLoc.getString("co_cli")==null)?"":rstLoc.getString("co_cli")));
            txtNomCli.setText(((rstLoc.getString("nomcli")==null)?"":rstLoc.getString("nomcli")));
            txtDirCli.setText(((rstLoc.getString("dircli")==null)?"":rstLoc.getString("dircli")));
            Glo_intCodSec = rstLoc.getInt("ne_secgrp");
            strTipPer_glo=rstLoc.getString("tx_tipper");
            strCodTipPerCli =  rstLoc.getString("co_tipper");
            stIvaVen=rstLoc.getString("st_ivaven");
            txtCodVen.setText(((rstLoc.getString("co_ven")==null)?"":rstLoc.getString("co_ven")));
            txtNomVen.setText(((rstLoc.getString("nomven")==null)?"":rstLoc.getString("nomven")));
            txtAte.setText(((rstLoc.getString("tx_ate")==null)?"":rstLoc.getString("tx_ate")));
            txaObs1.setText(((rstLoc.getString("tx_obs1")==null)?"":rstLoc.getString("tx_obs1")));
            txaObs2.setText(((rstLoc.getString("tx_obs2")==null)?"":rstLoc.getString("tx_obs2")));
            txtPartida.setText(((rstLoc.getString("tx_ptoPar")==null)?"":rstLoc.getString("tx_ptoPar")));
            txtTransporte.setText(((rstLoc.getString("tx_tra")==null)?"":rstLoc.getString("tx_tra")));
            cboMotivoTransporte.setSelectedIndex(vecTransporte.indexOf(rstLoc.getInt("co_motTra")+"")); 
            //*****************
            dateFecCot = objUti.formatearFecha(rstLoc.getDate("fe_doc"),"dd/MM/yyyy");
            intTipMod = 2; 
            //*****************
            if(rstLoc.getDate("fe_doc")==null){
              txtFecDoc.setText("");  
              lblfecha.setText("");
            }else{
                java.util.Date dateObj = rstLoc.getDate("fe_doc");
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH), calObj.get(java.util.Calendar.MONTH)+1, calObj.get(java.util.Calendar.YEAR));
                lblfecha.setText(dateFecCot);
            }
            //Mostrar el estado del registro.
            strAux=rstLoc.getString("st_reg");
            strstReg_glo = strAux; 
            if (strAux.equals("A"))
                strAux="Activo";
            else if (strAux.equals("I"))
                strAux="Anulado";
            else
                strAux="Otro";
            objTooBar.setEstadoRegistro(strAux);
         }else{
              objTooBar.setEstadoRegistro("Eliminado");
              clnTextos();
              }
        //Mostrar la posición relativa del registro.
        intPosRel=RSTCAB.getRow();
        RSTCAB.last();
        objTooBar.setPosicionRelativa("" + intPosRel + " / " + RSTCAB.getRow());
        RSTCAB.absolute(intPosRel);
        blnHayCam=false;
                
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
    }}catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
      catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
  return blnRes;
}

    
    
    
    public class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objParSis);
        }
        
       @Override
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
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
                
             
             if (objUltDocPrintUtil.isPagoDocumento_aso(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal() ,objTipDoc.getco_tipdoc(),Integer.parseInt(txtNumDoc.getText())))
                       return false;
              
            
             if (!anularReg())
                  return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        @Override
        public void clickAceptar()
        {
          //  setEstadoBotonMakeFac();
        }

         
        
      
       @Override
       public void clickAnterior(){ 
        try{
          if(RSTCAB != null ){
           if(!RSTCAB.isFirst()){
              Abrir_Conexion();
               if(blnHayCam){
                 if(isRegPro()){
                   RSTCAB.previous();
                   cargarReg(CONN_GLO);
                 }}else{
                     RSTCAB.previous();
                     cargarReg(CONN_GLO);
                 }
              Cerrar_Conexion(); 
              
            }}}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
               catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
         }

        
        
        @Override
        public void clickAnular()
        {
            setEditable(false);
        }
      

        @Override
        public void clickConsultar() 
        {
            txtCodVen.setEnabled(true);
          
            cargarTipoDoc();
        
            java.awt.Color color = txtSub.getBackground();
            txtSub.setEditable(false);
            txtIva.setEditable(false);
            txtTot.setEditable(false);
            txtSub.setBackground(color);
            txtIva.setBackground(color);
            txtTot.setBackground(color);
            txtFecDoc.setText("");
           
        }

        @Override
        public void clickEliminar()
        {
            setEditable(false);
        }

        @Override
        public void clickFin(){ 
          try{
             if(RSTCAB != null ) {
               if(!RSTCAB.isLast()){
                 Abrir_Conexion(); 
                    if (blnHayCam){
                        if (isRegPro()){
                            RSTCAB.last();
                            cargarReg(CONN_GLO);
                    }}
                    else{
                        RSTCAB.last();
                        cargarReg(CONN_GLO);
                    }
                 Cerrar_Conexion(); 
            }}}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
               catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
          }
   
        
        
      @Override
      public void clickInicio(){
        try{
           if(RSTCAB != null ){
             if(!RSTCAB.isFirst()){
               Abrir_Conexion();   
                 if(blnHayCam){
                   if(isRegPro()){
                      RSTCAB.first();
                      cargarReg(CONN_GLO);
                    }}else{
                        RSTCAB.first();
                        cargarReg(CONN_GLO);
                    }
                 Cerrar_Conexion(); 
            }}}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
               catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
        }

        
        
        
        
        
        
        
        

        
         private void cargaNum_Doc_Preimpreso(){
          int intNumeroDoc = objUltDocPrintVen24.getUltDoc(Integer.parseInt(txtTipDocCod.getText()));
          intNumeroDoc++;
          txtNumFac.setText("" + intNumeroDoc);
          txtGuia.setText("" + intNumeroDoc);
         }
        
        
         public void cargarTipoDoc(){
              try{
                    
                    java.sql.Connection  conDefault =  DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    java.sql.Statement stmPrede=conDefault.createStatement();
                    java.sql.ResultSet rstPrede;
                    
                    String sqlPrede = "";
                    
                    
                    
               if(objParSis.getCodigoUsuario()==1){
                 sqlPrede = "Select distinct doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, doc.st_meringegrfisbod, doc.tx_natdoc " +
                 " FROM tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                 " where   menu.co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                 " menu.co_loc = " + objParSis.getCodigoLocal()   + " and " +
                 " menu.co_mnu = " + intCodMnu  + " and " +
                 " menu.co_tipdoc = doc.co_tipdoc and menu.st_reg = 'S'";
                }else{
                    sqlPrede ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a.st_meringegrfisbod, a.tx_natdoc "+
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE a1.st_reg = 'S'  "+
                    " AND a1.co_emp=" + objParSis.getCodigoEmpresa()+""+
                    " AND a1.co_loc=" + objParSis.getCodigoLocal()+""+
                    " AND a1.co_mnu=" + intCodMnu+""+
                    " AND a1.co_usr=" + objParSis.getCodigoUsuario();
                }       
                    
                    
                    rstPrede=stmPrede.executeQuery(sqlPrede);
                    
                    if(rstPrede.next()){
                        txtTipDocCod.setText(((rstPrede.getString("co_tipdoc")==null)?"":rstPrede.getString("co_tipdoc")));
                        txtDesCorTipDoc.setText(((rstPrede.getString("tx_descor")==null)?"":rstPrede.getString("tx_descor")));
                        txtDesLarTipDoc.setText(((rstPrede.getString("tx_deslar")==null)?"":rstPrede.getString("tx_deslar")));
                     
                        objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));            
       
                    }
                    
                    stmPrede.close();
                    rstPrede.close();
                    conDefault.close();
                    stmPrede   = null;
                    rstPrede   = null;
                    conDefault = null;
                }
                catch(SQLException Evt)  {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
                catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  } 
             
         }
         
         
         
         
         
            @Override
            public void clickInsertar() {
                //Limpiando las cajas de textos y el detalle
                clnTextos();
                noEditable(false);                
               // txtFecDoc.setHoy();
                objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                      objParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);      
                cboForPag.setSelectedIndex(0);
               
                
                cboMotivoTransporte.setSelectedIndex(0);
                
              
                
                setEditable(true);
                blnHayCam=false;
                               
                cargarTipoDoc();
                
                
                 cargaNum_Doc_Preimpreso();
                
                 
                // System.out.println("CodMenu "+ objParSis.getCodigoMenu() );
                 
                  dblPorIva=dblPorIvaaux;
                  lblIva.setText("IVA " + dblPorIva + "%");
                 
                /*
                 * Estableciendo el Tipo de documentoque se va a utilizar
                 */
             
         
            }
            
        
        
     
@Override
public void clickSiguiente(){
  try{
   if(RSTCAB != null ){
    if(!RSTCAB.isLast()){
       Abrir_Conexion(); 
         if(blnHayCam){
          if(isRegPro()){
               RSTCAB.next();
               cargarReg(CONN_GLO);
           }}else{
                RSTCAB.next();
                cargarReg(CONN_GLO);
           }
         Cerrar_Conexion(); 

    }}}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
       catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}
 


        @Override
        public boolean eliminar()
        {
            try
            {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                
                if(!objUltDocPrintUtil.verificarsiesconfirmado(txtNumDoc.getText(),txtTipDocCod.getText()))
                   return false;
                    
                
                 if(!objUltDocPrintUtil.Verificar_Doc_Asociado(txtNumDoc.getText(),txtTipDocCod.getText()))
                      return false;
                
                
              if (objUltDocPrintUtil.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtNumDoc.getText())))
                      return false;
           
                
                
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    //rstCab.next();
                    //cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        @Override
        public boolean insertar()
        {  strstReg_glo=""; 
            if (!validaCampos())
                return false;
            if (!insertarReg())
                return false;
            blnHayCam=false;
            
         ///    calcularSaldo();
             
            return true;
        }

         private void calcularSaldo(){
            try
             {
              java.sql.Connection con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
              if (con!=null)
              {   
              //  System.out.println(">>Glo "+ GLO_strArreItm +"  >> "+ Glo_intCodSec);
                java.sql.PreparedStatement pst;
                 java.sql.ResultSet rstMae;
                 java.sql.ResultSet rstMae2; 
                 java.sql.Statement stmMonCre = con.createStatement();
                 java.sql.Statement stmMonCre2 = con.createStatement();
                 String sSQL="select distinct(a.co_itm) from tbm_inv as a where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_itm in("+GLO_strArreItm+") order by co_itm";
                rstMae2 = stmMonCre2.executeQuery(sSQL);
                 
                 while(rstMae2.next()){
                  
                 String strSec="";    
                /*
                 sSQL="select max(a.ne_secemp) " +
                 " from tbm_cabmovinv as a " +
                 " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                 " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                 " where  a.co_emp="+objParSis.getCodigoEmpresa()+"  and a.st_reg<>'I'  and b.co_itm="+rstMae2.getInt(1)+" and a.ne_secemp <  "+Glo_intCodSec; //+" "+
                 //" order by a.ne_secemp";
               */
                 
                sSQL=" SELECT max(ne_secgrp) FROM (" +
                " SELECT a1.ne_secGrp  FROM tbm_cabMovInv AS a1 " +
                " INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                " INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)" +
                " INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod) " +
                " INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)" +
                " INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm) " +
                " WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_itm="+rstMae2.getInt(1)+")" +
                " ORDER BY a1.ne_secGrp, a2.co_reg " +
                " ) as x  WHERE x.ne_secgrp<"+Glo_intCodSec;
                 
                  
                  rstMae = stmMonCre.executeQuery(sSQL);    
                  if(rstMae.next()) strSec=rstMae.getString(1);
                  rstMae.close();  
                     
                  
               
                    
               /*      
                 sSQL="select a.ne_secemp ,round(b.nd_exigrp,2) as ndexigrp,round(b.nd_valexiGRP,2) as ndvalexigrp" +
                 " from tbm_cabmovinv as a " +
                 " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                 " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                 " where  b.co_itm="+rstMae2.getInt(1)+" and a.ne_secgrp="+strSec+" "+
                 " order by a.ne_secemp,b.co_reg  desc";
               */
                   
              sSQL="select * from ( " +
              "SELECT a1.ne_secGrp ,a2.nd_exigrp, a2.nd_valexigrp, a2.nd_cosprogrp " +
              "FROM tbm_cabMovInv AS a1 " +
              "INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)" +
              "INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)" +
              "INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)" +
              "INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)" +
              "INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)" +
              " WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_itm="+rstMae2.getInt(1)+") " +
              " AND  a1.ne_secgrp = "+strSec+" "+
              " ORDER BY a1.ne_secGrp, a2.co_reg  DESC ) as x";  
                   
              //   System.out.println(" SQL 3 >> "+sSQL);
                      rstMae = stmMonCre.executeQuery(sSQL);
                      double ndexigrp=0;
                      double ndvalexigrp=0;
                         
                      
                      if(rstMae.next()){
                        //   System.out.println("SEC>> "+ rstMae.getInt(1) );
                         ndexigrp=rstMae.getDouble(2);
                         ndvalexigrp=rstMae.getDouble(3);
                     }
                         
                        
                      
                    sSQL= "select a.ne_secemp" +
                    ",a.co_Emp,a.co_loc ,a.co_doc,a.co_tipdoc,b.co_itm,b.nd_can,b.nd_pordes,b.nd_cosuni,b.nd_exi,b.nd_valexi" +
                    ", b.nd_costotgrp as  total" +
                    " ,a.st_reg,b.co_reg as coreg FROM  tbm_cabmovinv as a" +
                    " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                    " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                    " where  b.co_itm is not null and a.co_emp="+objParSis.getCodigoEmpresa()+" " +
                    " and c.co_itm="+rstMae2.getInt(1)+"  and a.ne_secgrp >= "+Glo_intCodSec+"  order by a.ne_secemp,a.co_emp"; 
                  
                  ////   System.out.println("SQL 4 >> "+ sSQL );
                          
                    rstMae = stmMonCre.executeQuery(sSQL);  //and a.ne_secemp >=  "+rstMae.getInt(1)+"
                  
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
                        ///  System.out.println("SQL 5 >> "+ sSQL );
                                 pst = con.prepareStatement(sSQL);
                                 pst.executeUpdate();
                            
                      }
                    //   System.out.println(">> "+ rstMae2.getInt(1) );
                    } 
                 rstMae=null;
                 rstMae2.close();
                 rstMae2=null;
               }
           ///  System.out.println("FIN ......OK ");
              con.close();
              con=null;
            }catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
        }
        
        @Override
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
            
           if(!objUltDocPrintUtil.verificarsiesconfirmado(txtNumDoc.getText(),txtTipDocCod.getText()))
               return false;
              
            
              if(!objUltDocPrintUtil.Verificar_Doc_Asociado(txtNumDoc.getText(),txtTipDocCod.getText()))
                      return false;
            
            
          if (objUltDocPrintUtil.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtNumDoc.getText()))){
                       return false;
           } 
              
            if (!validaCampos())
                return false;
            
            if (!actualizarReg())
                return false;
            blnHayCam=false;
            
         //   calcularSaldo();
            
            return true;
        }
                
        @Override
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                  //  stmCab.close();
                  //  conCab.close();
                    rstCab=null;
                  //  stmCab=null;
                   // conCab=null;
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
            clnTextos();
            blnHayCam=false;
            return blnRes;
        }       

        @Override
        public boolean aceptar() {
            return true;
        }
        
        @Override
        public boolean afterAceptar() {
            return true;
        }
        
        @Override
        public boolean afterAnular() {
            return true;
        }
        
        @Override
        public boolean afterCancelar() {
            return true;
        }
        
        @Override
        public boolean afterConsultar() {
            burRee.setEnabled(true);  
            return true;
        }
        
        @Override
        public boolean afterEliminar() {
            return true;
        }
        
        @Override
        public boolean afterImprimir() {
            return true;
        }
        
        @Override
        public boolean afterInsertar() {
        
            this.setEstado('w');
         
            return true;
        }
        
        @Override
        public boolean afterModificar() {
             blnHayCam=false;
            return true;
        }
        
        @Override
        public boolean afterVistaPreliminar() {
            return true;
        }
                
    private boolean PreVtaMenorPreUni(){
        
       
        
        boolean blnRes = true;
        int INT_TBL_COITM = 17 ;
        int INT_TBL_PREVEN= 11 ;
        double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = getDesCli(txtCodCli.getText()); 
        double dblMinimo, dblValorVenta, dblCostoItm, dblPorGanancia = getMarUti(txtCodCli.getText()); 
        int intCoItm;
        for(int i = 0 ; i < tblDat.getRowCount();i++){
          if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
            dblPreUsr    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString()))),6);
            dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
            dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);                       
            dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
            if(dblPreUniItm!=0){
               dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
            }else{
               double dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
               if(dblStkAct != 0)
                     dblCostoItm  =   getCosUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                  else {
                      
                       dblCostoItm = 0.00;
                        }
                dblMinimo    =   dblCostoItm * dblPorGanancia;
                dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
            }
          ///  System.out.println("OKKK " + dblValorVenta  + " --- " + dblMinimo  );
            if(dblValorVenta<dblMinimo){
                   blnRes = false;
                   tblDat.setValueAt(true,i,INT_TBL_BLNPRE);                
            }
        }}
        return blnRes;
    }
      
    
       
     private double getCosUni(String int_co_itm){
        double dblPreUni = 0;
          try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                     String sSQL = "  SELECT a1.nd_cosUni FROM tbm_inv AS a1"+
                               " INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                               " WHERE a2.co_emp="+ objParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
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
       catch(Exception Evt) {  return dblPreUni; }                  
       return dblPreUni;
     }      

 
    
    
    
      private double getMarUti(String int_co_cli){
       double dblMarUti = 0;
       try{
             java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
              if (conUni!=null){
                   java.sql.Statement stmUni = conUni.createStatement();
                   String sSQL= "SELECT nd_maruti from tbm_cli as cli " +
                                    " where cli.co_emp = " + objParSis.getCodigoEmpresa()+" and cli.co_cli = " + int_co_cli; 
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
       catch(java.sql.SQLException Evt) {   return dblMarUti;  }
       catch(Exception Evt) {  return dblMarUti;  }                  
       return dblMarUti;
     }
    
    
      private double getDesCli(String int_co_cli){
        double dblDesCli = 0;
         try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                      String sSQL= "SELECT nd_maxdes from tbm_cli as cli " +
                                    " where cli.co_emp = "+objParSis.getCodigoEmpresa()+" and cli.co_cli = "+int_co_cli; 
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
        catch(java.sql.SQLException Evt) {  return dblDesCli;  }
        catch(Exception Evt) {  return dblDesCli;  }                  
        return dblDesCli;
     }
        
      
      
      
      
       private void mostrarMsg(String strMsg)
    {
        //JOptionPane oppMsg=new JOptionPane();
        /*String strTit;
        strTit="Mensaje del sistema Zafiro";*/
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.OK_OPTION);
    }
      
      
       
       
            
        private boolean insertarReg(){
             boolean blnRes=false;                    
              String Estado="A";
              int ESTPRE=0;
              int  Est=0;
              calculaPagos();
              try{
                     java.sql.Connection conInsCot=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());          
                      if (conInsCot!=null){
                            conInsCot.setAutoCommit(false);
                            dblPorIva = objUti.redondeo(getIva(conInsCot),2);
                          
                            
                            
                            
                            
   //*******************************************************************************************************
     
            if(!Abrir_Conexion_Remota())
               return false;            
                              
   //*******************************************************************************************************
                           
                            
                            //********nuevo 
                            ESTPRE = objAutPrg.checkCtlsCot("tbm_cabautcotven", "tbm_detautcotven" , Integer.parseInt((txtNumCot.getText().equals("")?"0":txtNumCot.getText())), conInsCot);
                           //************ 
                           int intNumDoc = getUltCoDoc(conInsCot);
                           if(ESTPRE == 2 )
                                 Est = 2; 
                           if(ESTPRE == 3 )
                              Est = 3;   
                             
                     if(Est==0){
                                if( intNumDoc !=-1 ){
                                   if(insertarCab(conInsCot, intNumDoc, false, Estado )){
                                          if(insertarDet(conInsCot, con_remota, intNumDoc )){
                                               if(insertDetPag(intNumDoc, conInsCot )){ 
                                                   int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());                                                        
                                                   java.util.Date dteFecha = new java.util.Date(Fecha[2] , Fecha[1], Fecha[0]); 
                                                           
                                                    //  System.out.println("FACHA CUADRA =>> "+ objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") +" -- "+ Fecha[2] +" -- "+ Fecha[1] +" -- "+ Fecha[0] );
                                                               
                                                    if (objAsiDia.insertarDiario(conInsCot,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), Integer.parseInt(txtTipDocCod.getText()), txtNumDoc.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") ))                                     
                                                     {
                                                       if(CambiarEstCot(conInsCot)){
                                                         if(INTCODREGCEN!=0) con_remota.commit(); 
                                                         conInsCot.commit();
                                                         STR_ESTREG="I"; 
                                                          blnRes = true;
                                                          txtNumDoc.setText(intNumDoc+"");
                                                          
                                                           consultar3();  //******************
                                                           /*String strTit, strMsg;
                                                           strTit="Mensaje del sistema Zafiro";*/
                                                           String strMsg="¿Desea imprimir la Factura?";
                                                           //JOptionPane obj =new JOptionPane();
                                                           if(JOptionPane.showConfirmDialog(jfrThis ,strMsg,strTit,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE) == 0){
                                                                imprimir(); 
                                                           }
                                                         }else{ if(INTCODREGCEN!=0) con_remota.rollback();
                                                             conInsCot.rollback(); }
                                                      }else { if(INTCODREGCEN!=0) con_remota.rollback();
                                                        conInsCot.rollback(); }
                                                    }else { if(INTCODREGCEN!=0) con_remota.rollback();
                                                        conInsCot.rollback(); }
                                                }else { if(INTCODREGCEN!=0) con_remota.rollback();
                                                    conInsCot.rollback(); }
                                            }else { if(INTCODREGCEN!=0) con_remota.rollback();
                                                conInsCot.rollback(); }
                                
                                 }else
                                            conInsCot.rollback();
                           }
                            
                  
                  if(Est == 2 ) {  ///************
                            Estado="P";
                                        if( intNumDoc !=-1 ){
                                          //***************** INSERTA COTIZACION SI TIENE PROBLEMA DE AUTORIZACION   
                                            if(txtNumCot.getText().equals("")){
                                             int intNumCot = getUltCoDocCot(conInsCot);
                                             if(insertarCabCot(conInsCot, intNumCot, false, Estado )){
                                                if(insertarDetCot(conInsCot, intNumCot )){
                                                    if(insertDetPagCot(intNumCot, conInsCot )){ 
                                                        int intCods[] = {objParSis.getCodigoLocal(),   intNumCot };
                                                        if(objAutPrg.insertarCabDetAut(conInsCot, intCods , 3) ){
                                                             conInsCot.commit();
                                                             blnRes = false;
                                                             txtNumCot.setText(intNumCot+"");
                                                             java.awt.Color color = txtNumCot.getBackground();
                                                             txtNumCot.setEditable(false);
                                                             txtNumCot.setBackground(color);
                                                            

                                                              
                                                             /*String strTit, strMsg;
                                                             strTit="Mensaje del sistema Zafiro";*/
                                                             String strMsg="Se Genero la Cotizacion #"+ intNumCot +" Pendiente de Autorizar.\n La Factura no fue Ingresada";
                                                             //JOptionPane obj =new JOptionPane();
                                                             JOptionPane.showMessageDialog(jfrThis ,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                                                             
                                                             
                                                        }else
                                                            conInsCot.rollback();                                                        
                                                    }else
                                                        conInsCot.rollback();
                                                }else
                                                    conInsCot.rollback();
                                            }else
                                                conInsCot.rollback();
                                       }
                                            else{  
                                                      int intCods[] = {objParSis.getCodigoLocal(),   Integer.parseInt((txtNumCot.getText().equals("")?"0":txtNumCot.getText())) };
                                                            if(objAutPrg.insertarCabDetAut(conInsCot, intCods , 3) ){
                                                               conInsCot.commit();
                                                               blnRes = false;
                                                               blnHayCam = false;
                                                               /*String strTit, strMsg;
                                                               strTit="Mensaje del sistema Zafiro";*/
                                                               String strMsg="La Cotizacion #"+ txtNumCot.getText() +" Esta Pendiente de Autorizar.\n La Factura no fue Ingresada";
                                                               //JOptionPane obj =new JOptionPane();
                                                               JOptionPane.showMessageDialog(jfrThis ,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                                                         }else
                                                              conInsCot.rollback();    
                                      }
                                                
                                      ///////////********************************////////////////////      
                                         }else
                                            conInsCot.rollback();
                           }
                           if(Est == 3 ) {  
                                 blnRes=false;
                           }      
                           ///************ 
                            if(INTCODREGCEN!=0)  con_remota.close();
                            conInsCot.close();
                            if(INTCODREGCEN!=0)  con_remota=null;
                            conInsCot=null;
                   }  ///*********
               }
              catch(SQLException  Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;  }
              catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }               
             return blnRes;  
        }
        
       
     
  //////////*************** INSERTA COTIZACION ****///////////////
        
        
         private boolean insertarCabCot(java.sql.Connection conIns, int intNumDoc,  boolean blnSolAut, String Estado ){
                boolean blnRes = false;
                try{                
                    String strFecha = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                    String sqlCab = "INSERT INTO  tbm_cabCotVen" +
                           "(co_emp, co_loc, co_cot, " + //CAMPOS PrimayKey
                           " fe_cot, "                 + //Fecha de la cotizacion
                           " co_cli, co_ven, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                           " tx_obs1, tx_obs2, nd_sub, nd_porIva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                           " co_forPag, fe_ing, fe_ultmod, co_usring ,co_usrmod, st_reg, nd_tot,ne_val " + 
                           " ,st_regrep )" +
                           "VALUES (" +
                            objParSis.getCodigoEmpresa() + ", " +
                            objParSis.getCodigoLocal()   + ", " + 
                            intNumDoc + ", '" + 
                            strFecha  + "', " +
                            txtCodCli.getText() + ",   " +
                            txtCodVen.getText() + ",  '" +
                            txtAte.getText()    + "', '" + 
                            txaObs1.getText()   + "', '" + 
                            txaObs2.getText()   + "',  " + 
                            dblSubtotalCot    + ",   " +
                            dblPorIva   + " , " +
                            vecForPag.get(cboForPag.getSelectedIndex()) + ",CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, " +
                            objParSis.getCodigoUsuario() + " , " +
                            objParSis.getCodigoUsuario() + " , " +
                            ((blnSolAut)?"'P'":"'"+ Estado +"'") +  ","+ txtTot.getText() + ",15,'I')";   //);
   
                            java.sql.PreparedStatement pstCabCot = conIns.prepareStatement(sqlCab);
                            pstCabCot.executeUpdate();
                    blnRes=true;
                }
                catch (java.sql.SQLException e){
                  blnRes=false;
                 objUti.mostrarMsgErr_F1(this, e);
                } catch (Exception e) {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, e);
                }
                return blnRes;                      
            }
        
         
         
         
          
         private boolean insertarDetCot(java.sql.Connection conInsCot, int intNumDoc){
                boolean blnRes = false;
                try{
                     int Co_Bod = getBodPrede(conInsCot);
                  
                     for(int i=0; i<tblDat.getRowCount();i++){
                         if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                        String strQry =
                                  "INSERT INTO  tbm_detCotVen" +
                                   "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                                   " co_itm, tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                   " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                   " ,st_regrep  )" +
                                   "VALUES ("  +
                                   objParSis.getCodigoEmpresa() + ", " +
                                   objParSis.getCodigoLocal()   + ", " + 
                                   intNumDoc + ", " +
                                   (i+1)       + ", " +
                                   tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
                                   tblDat.getValueAt(i, INT_TBL_ITMALT) + "','" +
                                   tblDat.getValueAt(i, INT_TBL_ITMALT2) + "','" +
                                   tblDat.getValueAt(i, INT_TBL_DESITM) + "'," +
                                   Co_Bod +", "+
                                   tblDat.getValueAt(i, INT_TBL_CANMOV) + " ," +
                                   Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) + ", " +
                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),6) + ", '" +
                                   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "' " +
                                   ",'I' )";
                                  java.sql.PreparedStatement pstDetCot = conInsCot.prepareStatement(strQry); 
                                  pstDetCot.executeUpdate();
                       } }                   
                
                    blnRes=true;
                }
                catch (Exception e)  {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, e);
                }
                return blnRes;                  
            }
         
         
         
//         
//         private boolean insertarDetCot(java.sql.Connection conInsCot, int intNumDoc){
//                boolean blnRes = false;
//                try{
//                     int Co_Bod = getBodPrede();
//                  
//                     for(int i=0; i<tblDat.getRowCount();i++){
//                         if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                        String strQry =
//                                  "INSERT INTO  tbm_detCotVen" +
//                                   "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
//                                   " co_itm, tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                                   " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                                   " ,st_regrep  )" +
//                                   "VALUES ("  +
//                                   objParSis.getCodigoEmpresa() + ", " +
//                                   objParSis.getCodigoLocal()   + ", " + 
//                                   intNumDoc + ", " +
//                                   (i+1)       + ", " +
//                                   tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT) + "','" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT2) + "','" +
//                                   tblDat.getValueAt(i, INT_TBL_DESITM) + "'," +
//                                   Co_Bod +", "+
//                                   tblDat.getValueAt(i, INT_TBL_CANMOV) + " ," +
//                                   Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) + ", " +
//                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),6) + ", '" +
//                                   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "' " +
//                                   ",'I' )";
//                                  java.sql.PreparedStatement pstDetCot = conInsCot.prepareStatement(strQry); 
//                                  pstDetCot.executeUpdate();
//                       } }                   
//                
//                    blnRes=true;
//                }
//                catch (Exception e)  {
//                    blnRes=false;
//                    objUti.mostrarMsgErr_F1(this, e);
//                }
//                return blnRes;                  
//            }
//         
//         
         
         
         
         private boolean insertDetPagCot(int intNumDoc, java.sql.Connection conInsCot ){
               try{
                 
                    for(int i=0; i<tblCotForPag.getRowCount();i++){
                        
                        int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
                        String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                        java.sql.PreparedStatement pstDetCot = conInsCot.prepareStatement(
                                 "INSERT INTO  tbm_pagCotVen" +
                                   "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                                   " ne_diaCre, fe_ven, " +//<==
                                   " mo_pag, ne_diaGra, nd_porRet ,st_regrep " +//<==
                                   "  )" +
                                   "VALUES (" +
                                   objParSis.getCodigoEmpresa() + ", " +
                                   objParSis.getCodigoLocal()   + ", " + 
                                   intNumDoc + ", " +
                                   (i+1)       + ", " +
                                   tblCotForPag.getValueAt(i, 1) + ",'#" +
                                   strFechaPag + "#'," +
                                   objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) + ", " +
                                   ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
                                   ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"null":tblCotForPag.getValueAt(i, 3).toString()) +
                                   ",'I' )"); 
                                  pstDetCot.executeUpdate();
                      }                   
               }
                catch(SQLException Evt)
                {   objUti.mostrarMsgErr_F1(jfrThis, Evt); 
                   return false;
                }
                catch(Exception Evt)
                {   objUti.mostrarMsgErr_F1(jfrThis, Evt);
                    return false;
                 }                       
              return true;                   
            }
           
         
         ///*************** MODIFICACION DE COTIZACION EN CASO AUTORIZADO
         
         
            private boolean modificarCabCot(java.sql.Connection conModCot, boolean blnSolAut, String Estado){
                boolean blnRes = false;
                try{                
                    int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                    dateFecCot = Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0]; 
                    dateFecCot = objUti.formatearFecha(dateFecCot,"yyyy/MM/dd","dd/MM/yyyy");  
                    /*********************************************\    
                     * Armando el Update para los datos          *   
                     * de la cabecera de cotizacion              *
                    \*********************************************/
                    java.sql.PreparedStatement pstCabCot = conModCot.prepareStatement(
                          "Update tbm_cabCotVen set " +
                           " fe_cot  = '" + strFecha            + "', " +
                           " co_cli  = "  + txtCodCli.getText() + ",  " + 
                           " co_ven  = "  + txtCodVen.getText() + ",  " +
                           " tx_ate  = '" + txtAte.getText()    + "', " + 
                           " nd_tot  = '" + txtTot.getText()    + "', " + 
                           " tx_obs1 = '" + txaObs1.getText()   + "', " + 
                           " tx_obs2 = '" + txaObs2.getText()   + "', " + 
                           " nd_sub  = "  + objUti.redondeo(Double.parseDouble(txtSub.getText()),6)    + ",  " +
                           " nd_porIva = "+ dblPorIva           +  ", " +
                           " co_forPag = "+ vecForPag.get(cboForPag.getSelectedIndex()) + ", " +
                           " fe_ultMod   =  CURRENT_TIMESTAMP                , " +
                           " co_usrMod   = " + objParSis.getCodigoUsuario() + ", " +
                           " st_reg   = " + "'"+ Estado +"'" + " " +
                           "  where " +
                           " co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
                           " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
                           " co_cot = " +  txtNumCot.getText());      
                    /* Ejecutando el Update */
                    pstCabCot.executeUpdate();
                    blnRes=true;
                }
                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }       
                catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                return blnRes;                      
            }
        
            
//            
//            private boolean modificarDetCot(java.sql.Connection conModCot){
//                boolean blnRes = false;
//                try{                
//                    /*********************************************\    
//                     * Armando el Delete para quitar los         *   
//                     * registro de detalle de cotizacion         *
//                     * actuales.                                 *
//                    \*********************************************/
//                    java.sql.PreparedStatement pstDelDetCot = conModCot.prepareStatement(                                        
//                                  " DELETE FROM tbm_detCotVen " +
//                                   "  where " +
//                                   " co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
//                                   " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
//                                   " co_cot = " +  txtCot.getText());      
//                    /* Ejecutando el Delete */
//                    pstDelDetCot.executeUpdate();
//                    /*********************************************\    
//                     * Armando el Insert para los datos          *   
//                     * del detalle de cotizacion                 *
//                    \*********************************************/
//                    String strSqlDet="";
//                    
//                 
//                    
//                    int Co_Bod = getBodPrede();
//                    
//                   ///  System.out.println(">> 01 "+ Co_Bod );          
//                    
//                    for(int i=0; i<tblDat.getRowCount();i++){
//                           if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                  
//                        String sql = "INSERT INTO  tbm_detCotVen" +
//                           "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
//                           " co_itm, tx_codalt, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                           " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                           " )" +
//                           "VALUES ("  +
//                                   objParSis.getCodigoEmpresa() + ", " +
//                                   objParSis.getCodigoLocal()   + ", " + 
//                                   txtCot.getText() + ", " +
//                                   (i+1)       + " , ";
//                              sql +=  tblDat.getValueAt(i, INT_TBL_CODITM) + ",'";
//                              sql +=  tblDat.getValueAt(i, INT_TBL_ITMALT) + "','"; 
//                              sql +=  tblDat.getValueAt(i, INT_TBL_DESITM) + "',";
//                              sql +=   Co_Bod + ", ";
//                              sql +=    tblDat.getValueAt(i, INT_TBL_CANMOV) + " ,";
//                              sql +=    Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) + ", ";
//                              sql +=    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", '";
//                              sql +=   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'";
//                              sql +=    " )"; 
//                        /* Ejecutando el insert */
//                       java.sql.PreparedStatement pstDetCot = conModCot.prepareStatement(sql);
//                       pstDetCot.executeUpdate();
//                    } }
//                    blnRes=true;
//                }
//                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
//                catch (Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
//                return blnRes;                      
//            }
//         
            
            
            
               private boolean modificarDetCot(java.sql.Connection conModCot){
                boolean blnRes = false;
                try{                
                    /*********************************************\    
                     * Armando el Delete para quitar los         *   
                     * registro de detalle de cotizacion         *
                     * actuales.                                 *
                    \*********************************************/
                    java.sql.PreparedStatement pstDelDetCot = conModCot.prepareStatement(                                        
                                  " DELETE FROM tbm_detCotVen " +
                                   "  where " +
                                   " co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
                                   " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
                                   " co_cot = " +  txtNumCot.getText());      
                    /* Ejecutando el Delete */
                    pstDelDetCot.executeUpdate();
                    /*********************************************\    
                     * Armando el Insert para los datos          *   
                     * del detalle de cotizacion                 *
                    \*********************************************/
                    String strSqlDet="";
                    
                 
                    
                    int Co_Bod = getBodPrede(conModCot);
                    
                   ///  System.out.println(">> 01 "+ Co_Bod );          
                    
                    for(int i=0; i<tblDat.getRowCount();i++){
                           if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                  
                        String sql = "INSERT INTO  tbm_detCotVen" +
                           "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                           " co_itm, tx_codalt, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                           " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen " +//<==Campos que aparecen en la parte inferior del 1er Tab
                           " )" +
                           "VALUES ("  +
                                   objParSis.getCodigoEmpresa() + ", " +
                                   objParSis.getCodigoLocal()   + ", " + 
                                   txtNumCot.getText() + ", " +
                                   (i+1)       + " , ";
                              sql +=  tblDat.getValueAt(i, INT_TBL_CODITM) + ",'";
                              sql +=  tblDat.getValueAt(i, INT_TBL_ITMALT) + "','"; 
                              sql +=  tblDat.getValueAt(i, INT_TBL_DESITM) + "',";
                              sql +=   Co_Bod + ", ";
                              sql +=    tblDat.getValueAt(i, INT_TBL_CANMOV) + " ,";
                              sql +=    Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) + ", ";
                              sql +=    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", '";
                              sql +=   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'";
                              sql +=    " )"; 
                        /* Ejecutando el insert */
                       java.sql.PreparedStatement pstDetCot = conModCot.prepareStatement(sql);
                       pstDetCot.executeUpdate();
                    } }
                    blnRes=true;
                }
                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
                catch (Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
                return blnRes;                      
            }
         
            
            
            private boolean modificarPagCot(java.sql.Connection conModCot ){
                boolean blnRes = false;
                try{                
                    /*********************************************\    
                     * Armando el Delete para quitar los         *   
                     * registro de detalle de Pago               *
                     * actuales.                                 *
                    \*********************************************/
                    java.sql.PreparedStatement pstDelCabPagCot = conModCot.prepareStatement(                                        
                          " DELETE FROM tbm_pagCotVen " +
                           "  where " +
                           " co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
                           " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
                           " co_cot = " +  txtNumCot.getText());      

                    /* Ejecutando el Delete */
                    pstDelCabPagCot.executeUpdate();
                    /*********************************************\    
                     * Armando el Insert para los datos          *   
                     * del detalle de PAgo                       *
                    \*********************************************/
                    for(int i=0; i<tblCotForPag.getRowCount();i++){
                        int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
                        String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                                    java.sql.PreparedStatement pstInsPagCot = conModCot.prepareStatement(                                        
                                          "INSERT INTO  tbm_pagCotVen" +
                                           "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                                           " ne_diaCre, fe_ven, " +//<==
                                           " mo_pag, ne_diaGra, nd_porRet   " +//<==
                                           "  )" +
                                           "VALUES (" +
                                           objParSis.getCodigoEmpresa() + ", " +
                                           objParSis.getCodigoLocal()   + ", " + 
                                           txtNumCot.getText() + ", " +
                                           (i+1)       + ", " +
                                           tblCotForPag.getValueAt(i, 1) + ",'#" +
                                           strFechaPag + "#'," +
                                           objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) + ", " +
                                           ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
                                           ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"null":tblCotForPag.getValueAt(i, 3).toString()) +                                                                       
                                   " )"); 
                        /* Ejecutando el insert */
                        pstInsPagCot.executeUpdate();
                      }
                    blnRes=true;
                }
                catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
                return blnRes;                      
            }         
            
            
           
        
        
  /////////////////////**********************************///////////////
  /////////////////////////////////////////////////////////////////////      
        
           
        
        
   /////////////////////////////////////*********************************/////////////////////////////////////////
  /////////////////////////////////////*********************************/////////////////////////////////////////

          
        private boolean insertarCab(java.sql.Connection conIns, int intNumDoc,  boolean blnSolAut, String Estado ){
                boolean blnRes = false;
                try{
                     ///******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
                                       int intSecGrp = 0;
                                       int intSecEmp = 0;
//                                       intSecEmp = objUltDocPrintUtil.getNumeroOrdenEmpresa(conIns);
//                                       intSecGrp = objUltDocPrintUtil.getNumeroOrdenGrupo(conIns);
                                       Glo_intCodSec = intSecGrp;
                   ///*******************************************************************///
                  //************  PERMITE SABER SI EL NUMERO DE FACTURA ESTA DUPLICADO  *****************/
                String sql = "select count(ne_numdoc) as num from tbm_cabmovinv  where " +
                           " co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" " +
                           "and co_tipdoc="+txtTipDocCod.getText()+" and ne_numdoc="+txtNumFac.getText();
                  java.sql.Statement stm2 = conIns.createStatement();
                  java.sql.ResultSet rstNum2 = stm2.executeQuery(sql);
                  if(rstNum2.next()){
                      if(rstNum2.getInt(1) >= 1 ){
                            //JOptionPane oppMsg = new JOptionPane();
                            /*String strTit, strMsg;
                            strTit="Mensaje del sistema Zafiro";*/
                            String strMsg=" No. de Factura ya existe... ?";
                            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE,null);
                            return false;
                      }
                  }
               ////************************************************************************************   
                rstNum2.close();
                rstNum2=null;
                stm2.close();
                stm2=null;
                                         
                                       
                    java.sql.PreparedStatement pstCabMovInv = conIns.prepareStatement(
                          "INSERT INTO  tbm_cabMovInv" +
                           "(co_emp, co_loc, co_doc, co_tipDoc, " + //CAMPOS PrimayKey
                           " ne_secemp, ne_secgrp, fe_doc, "                 + //secuencial de documentos y Fecha 
                           " co_cli, co_com, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                           " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, " + //<-- Campos de info. del cliente
                           " tx_nomven, "                                   +//INformacion del vendedor
                           " ne_numDoc, ne_numCot, tx_numped, ne_numGui, " +//<==Campos que aparecen en la parte superior del 1er Tab
                           " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot, nd_valiva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                           " co_forPag,  " + 
                           " tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, "   + //Campos del Tab OTROS 
                           " fe_ing, co_usrIng, fe_ultMod, co_usrMod, tx_desforpag, st_reg , st_regrep " + //Campos de Auditoria Fecha de Ingreso Y Usuario q ingreso.
                           "  )" +
                           "VALUES ( ? , ? , ? , ? , ? , ? , " +
                                   " ? , ? , ? , ? , ? , ? , " +
                                   " ? , ? , ? , ? , ? , ? , ?, ?, " +
                                   " ? , ? , ? , ? , ? , ? , " +
                                   " ? , ? , ? , ? , ? , CURRENT_TIMESTAMP , " +
                                   " ? , CURRENT_TIMESTAMP, ?, ?, 'A' ,'I' )");
                                   
                                   
                                   
                    dblPorIva = objUti.redondeo(getIva(conIns),2);
                    java.sql.Statement stmDoc=conIns.createStatement();
                    
                    
                   //  String strFecha = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";       
                   
                      int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                      String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                                        
                     
                    pstCabMovInv.setInt(1,    objParSis.getCodigoEmpresa() );
                    pstCabMovInv.setInt(2,    objParSis.getCodigoLocal() );
                    pstCabMovInv.setInt(3,    intNumDoc);
                    pstCabMovInv.setInt(4,    Integer.parseInt(txtTipDocCod.getText()) );
                    pstCabMovInv.setInt(5,    intSecEmp );
                    pstCabMovInv.setInt(6,    intSecGrp );
                    pstCabMovInv.setString(7, strFecha  );
                    pstCabMovInv.setInt(8,  Integer.parseInt(txtCodCli.getText()) );
                    pstCabMovInv.setInt(9,  Integer.parseInt(txtCodVen.getText()) );
                    pstCabMovInv.setString(10,txtAte.getText() );
                    pstCabMovInv.setString(11,txtNomCli.getText() );
                    pstCabMovInv.setString(12,txtDirCli.getText() );
                    pstCabMovInv.setString(13,objCliDat.getIdentificacion() );
                    pstCabMovInv.setString(14,objCliDat.getTelefono() );
                    pstCabMovInv.setString(15,objCiuDat.getNombrelargo() );
                    pstCabMovInv.setString(16,txtNomVen.getText() );
                    pstCabMovInv.setString(17,txtNumFac.getText() );
                    pstCabMovInv.setString(18,((txtNumCot.getText().equals(""))?"0":txtNumCot.getText()) );
                    pstCabMovInv.setString(19,((txtPed.getText().equals(""))?"0":txtPed.getText()) );
                    pstCabMovInv.setString(20,((txtGuia.getText().equals(""))?"0":txtGuia.getText()) );
                    pstCabMovInv.setString(21,txaObs1.getText() );
                    pstCabMovInv.setString(22,txaObs2.getText() );
                    pstCabMovInv.setDouble(23,objUti.redondeo(dblSubtotalCot,6)* -1 );
                    pstCabMovInv.setDouble(24,objUti.redondeo(dblPorIva , 2) );
                    pstCabMovInv.setDouble(25,objUti.redondeo(dblTotalCot , 6)* -1);
                    pstCabMovInv.setDouble(26,Double.parseDouble(txtIva.getText())* -1);
                    pstCabMovInv.setInt(27,   Integer.parseInt( vecForPag.get(cboForPag.getSelectedIndex())+"") );
                    pstCabMovInv.setString(28,txtPartida.getText() );
                    pstCabMovInv.setString(29,txtTransporte.getText() );
                    pstCabMovInv.setInt(30,   Integer.parseInt(vecTransporte.get(cboMotivoTransporte.getSelectedIndex())+"") );
                    pstCabMovInv.setString(31,cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex())+"" );
                    pstCabMovInv.setInt(32,   objParSis.getCodigoUsuario() );
                    pstCabMovInv.setInt(33,   objParSis.getCodigoUsuario() );
                    pstCabMovInv.setString(34,   cboForPag.getSelectedItem() + "" );
                    
                    pstCabMovInv.executeUpdate();   
                    
                   blnRes=true;
                }
                catch (java.sql.SQLException e){
                  blnRes=false;
                  objUti.mostrarMsgErr_F1(this, e);
                } catch (Exception e) {
                   blnRes=false;
                  objUti.mostrarMsgErr_F1(this, e);
               }
            return blnRes;                      
         }
       


        
        
       
//        
//        private boolean insertarCab(java.sql.Connection conIns, int intNumDoc,  boolean blnSolAut, String Estado ){
//                boolean blnRes = false;
//                try{
//                     ///******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
//                                       int intSecGrp = 0;
//                                       int intSecEmp = 0;
//                                       intSecEmp = objUltDocPrintUtil.getNumeroOrdenEmpresa(conIns);                                      
//                                       intSecGrp = objUltDocPrintUtil.getNumeroOrdenGrupo(conIns);
//                                       Glo_intCodSec = intSecGrp;
//                   ///*******************************************************************///
//                  //************  PERMITE SABER SI EL NUMERO DE FACTURA ESTA DUPLICADO  *****************/
//                String sql = "select count(ne_numdoc) as num from tbm_cabmovinv  where " +
//                           " co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" " +
//                           "and co_tipdoc="+txtTipDocCod.getText()+" and ne_numdoc="+txtFac.getText();
//                  java.sql.Statement stm2 = conIns.createStatement();
//                  java.sql.ResultSet rstNum2 = stm2.executeQuery(sql);
//                  if(rstNum2.next()){
//                      if(rstNum2.getInt(1) >= 1 ){
//                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
//                            String strTit, strMsg;
//                            strTit="Mensaje del sistema Zafiro";
//                            strMsg=" No. de Factura ya existe... ?";
//                            oppMsg.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE,null);
//                            return false;
//                      }
//                  }
//               ////************************************************************************************   
//                rstNum2.close();
//                rstNum2=null;
//                stm2.close();
//                stm2=null;
//                                         
//                                       
//                    java.sql.PreparedStatement pstCabMovInv = conIns.prepareStatement(
//                          "INSERT INTO  tbm_cabMovInv" +
//                           "(co_emp, co_loc, co_doc, co_tipDoc, " + //CAMPOS PrimayKey
//                           " ne_secemp, ne_secgrp, fe_doc, "                 + //secuencial de documentos y Fecha 
//                           " co_cli, co_com, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                           " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, " + //<-- Campos de info. del cliente
//                           " tx_nomven, "                                   +//INformacion del vendedor
//                           " ne_numDoc, ne_numCot, tx_numped, ne_numGui, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                           " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot, nd_valiva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                           " co_forPag,  " + 
//                           " tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, "   + //Campos del Tab OTROS 
//                           " fe_ing, co_usrIng, fe_ultMod, co_usrMod, tx_desforpag, st_reg , st_regrep " + //Campos de Auditoria Fecha de Ingreso Y Usuario q ingreso.
//                           "  )" +
//                           "VALUES ( ? , ? , ? , ? , ? , ? , " +
//                                   " ? , ? , ? , ? , ? , ? , " +
//                                   " ? , ? , ? , ? , ? , ? , ?, ?, " +
//                                   " ? , ? , ? , ? , ? , ? , " +
//                                   " ? , ? , ? , ? , ? , CURRENT_TIMESTAMP , " +
//                                   " ? , CURRENT_TIMESTAMP, ?, ?, "  + ((blnSolAut)?"'P'":"'C'")  + ",'I' )");
//                                   
//                                   
//                                   
//                    dblPorIva = objUti.redondeo(getIva(),2);
//                    java.sql.Statement stmDoc=conIns.createStatement();
//                    
//                    
//                   //  String strFecha = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";       
//                   
//                      int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
//                      String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
//                                        
//                     
//                    pstCabMovInv.setInt(1,    objParSis.getCodigoEmpresa() );
//                    pstCabMovInv.setInt(2,    objParSis.getCodigoLocal() );
//                    pstCabMovInv.setInt(3,    intNumDoc);
//                    pstCabMovInv.setInt(4,    Integer.parseInt(txtTipDocCod.getText()) );
//                    pstCabMovInv.setInt(5,    intSecEmp );
//                    pstCabMovInv.setInt(6,    intSecGrp );
//                    pstCabMovInv.setString(7, strFecha  );
//                    pstCabMovInv.setInt(8,  Integer.parseInt(txtCliCod.getText()) );
//                    pstCabMovInv.setInt(9,  Integer.parseInt(txtVenCod.getText()) );
//                    pstCabMovInv.setString(10,txtAte.getText() );
//                    pstCabMovInv.setString(11,txtCliNom.getText() );
//                    pstCabMovInv.setString(12,txtCliDir.getText() );
//                    pstCabMovInv.setString(13,objCliDat.getIdentificacion() );
//                    pstCabMovInv.setString(14,objCliDat.getTelefono() );
//                    pstCabMovInv.setString(15,objCiuDat.getNombrelargo() );
//                    pstCabMovInv.setString(16,txtVenNom.getText() );
//                    pstCabMovInv.setString(17,txtFac.getText() );
//                    pstCabMovInv.setString(18,((txtCot.getText().equals(""))?"0":txtCot.getText()) );
//                    pstCabMovInv.setString(19,((txtPed.getText().equals(""))?"0":txtPed.getText()) );
//                    pstCabMovInv.setString(20,((txtGuia.getText().equals(""))?"0":txtGuia.getText()) );
//                    pstCabMovInv.setString(21,txaObs1.getText() );
//                    pstCabMovInv.setString(22,txaObs2.getText() );
//                    pstCabMovInv.setDouble(23,objUti.redondeo(dblSubtotalCot,6)* -1 );
//                    pstCabMovInv.setDouble(24,objUti.redondeo(dblPorIva , 2) );
//                    pstCabMovInv.setDouble(25,objUti.redondeo(dblTotalCot , 6)* -1);
//                    pstCabMovInv.setDouble(26,Double.parseDouble(txtIva.getText())* -1);
//                    pstCabMovInv.setInt(27,   Integer.parseInt( vecForPag.get(cboForPag.getSelectedIndex())+"") );
//                    pstCabMovInv.setString(28,txtPartida.getText() );
//                    pstCabMovInv.setString(29,txtTransporte.getText() );
//                    pstCabMovInv.setInt(30,   Integer.parseInt(vecTransporte.get(cboMotivoTransporte.getSelectedIndex())+"") );
//                    pstCabMovInv.setString(31,cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex())+"" );
//                    pstCabMovInv.setInt(32,   objParSis.getCodigoUsuario() );
//                    pstCabMovInv.setInt(33,   objParSis.getCodigoUsuario() );
//                    pstCabMovInv.setString(34,   cboForPag.getSelectedItem() + "" );
//                    
//                    pstCabMovInv.executeUpdate();   
//                    
//                   blnRes=true;
//                }
//                catch (java.sql.SQLException e){
//                  blnRes=false;
//                  objUti.mostrarMsgErr_F1(this, e);
//                } catch (Exception e) {
//                   blnRes=false;
//                  objUti.mostrarMsgErr_F1(this, e);
//               }
//            return blnRes;                      
//         }
//       


          private boolean insertarDet(java.sql.Connection conIns, java.sql.Connection con_remota, int intNumDoc){
                boolean blnRes = false;
//                Librerias.ZafUtil.ZafTipItm  objZafTipItm = new Librerias.ZafUtil.ZafTipItm(objParSis);   
//                StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
//                int intest=0; 
//                  
//                try{
//                     int Co_Bod = getBodPrede(conIns);
//                     
//                     int intTipCli=3;
//                         intTipCli= objUltDocPrintUtil.ValidarCodigoCliente(txtCliCod.getText(),conIns);
//                              
//                      double dlbcostouni=0.00;
//                      double bldcanmov=0.00; 
//                      double bldcostot=0.00;
//                     
//                      int intControl=0; 
//                         GLO_strArreItm="";
//                                       
//                      
//                      for(int i=0; i<tblDat.getRowCount();i++){
//                         if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                                                          
//                             
//                      int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM)==null || tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString());
//                      int intCodBod = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));                     
//                      double dlbCan = Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV));
//                      
//                     //  if(!objZafTipItm.isServicio(intCodItmAct)){ 
//                         if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){  
//                           
//                           
//                              
//                                     if(intTipCli==0){   
//                                         if(!objSisCon.existeStockItemEmpresa_Factura_solo_FAC(conIns, con_remota, objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), intCodItmAct , dlbCan , intCodBod )){
//                                                javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();  
//                                                String strTit, strMsg;
//                                                strTit="Mensaje del sistema Zafiro";
//                                                strMsg=" ***El producto : " +  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString()) + " No tiene stock suficiente "+ "\n No se puede facturar!!" ;
//                                                oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                                                tblDat.editCellAt(i,INT_TBL_CANMOV);
//                                                tblDat.changeSelection(i, INT_TBL_CANMOV, false, false);					
//                                                conIns.rollback();                                             
//                                                conIns.setAutoCommit(true);     
//                                                return false;
//                                       }}      
//                              
//                                     objZafInv.movInventario_solo_FAC(con_remota, intCodItmAct, intCodBod, -1, dlbCan, conIns, "", "", "" );
//                             
//                                   //**************************************************************************************
//                                   
//                                      if(intTipCli==1){  
//                                          
//                                           if(!objSisCon.existeStockItemGrupo_Factura_solo_FAC(conIns, con_remota, objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), intCodItmAct , dlbCan , intCodBod )){
//                                                javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();  
//                                                String strTit, strMsg;
//                                                strTit="Mensaje del sistema Zafiro";
//                                                strMsg=" ***El producto : " +  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString()) + " No tiene stock suficiente "+ "\n No se puede facturar!!" ;
//                                                oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                                                tblDat.editCellAt(i,INT_TBL_CANMOV);
//                                                tblDat.changeSelection(i, INT_TBL_CANMOV, false, false);					
//                                                conIns.rollback(); 
//                                                if(INTCODREGCEN!=0) con_remota.rollback(); 
//                                                conIns.setAutoCommit(true);     
//                                                return false;
//                                            }    
//                                           
//                                            if(!objSisCon.actualizarInventario_2_solo_OC(conIns, con_remota, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(),intCodItmAct,(dlbCan *-1 ),intCodBod, "", "", "" )){
//                                                     conIns.rollback();
//                                                     if(INTCODREGCEN!=0) con_remota.rollback(); 
//                                                     return false;
//                                            }
//                                      }
//                               
//                                //*************************************************************************************************
//                                    
//                                     
//                              }
//                          
//                                
//                       
//                               dlbcostouni = objSisCon.getCostoConsolidado2(conIns , objParSis.getCodigoEmpresa(), Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM )));
//                               bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV ).toString());
//                               bldcostot = (dlbcostouni * bldcanmov);
//                               bldcostot = (bldcostot*-1);
//                               bldcanmov = (bldcanmov * -1);   
//                                           
//                    //******************************************************************************************
//                               
//                                 if(intControl!=0)    
//                                             GLO_strArreItm=GLO_strArreItm+",";
//                                     
//                                             GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//                                    
//                                           intControl++;
//                               
//                               
//                                if (i>0)
//                                   stb.append(" UNION ALL ");
//                                   stb.append("SELECT "+objParSis.getCodigoEmpresa() + ", " +
//                                   objParSis.getCodigoLocal()   + ", " + 
//                                   txtTipDocCod.getText()          + ", " + 
//                                   intNumDoc + ", " +
//                                   (i+1)       + ",'" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT) + "', '" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT2) + "', " +
//                                   tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
//                                   tblDat.getValueAt(i, INT_TBL_DESITM) + "','" +
//                                   tblDat.getValueAt(i, INT_TBL_UNIDAD) + "'," +
//                                   tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " + 
//                                   bldcanmov + ", " + objUti.redondear((Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i,  INT_TBL_TOTAL).toString())*-1),2)+", "+
//                                   dlbcostouni + ", " + bldcostot+ ", " +
//                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), 6) + ", " +
//                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null  )?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString())  , 2) + ", '" +
//                                   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
//                                   ",'P',"+bldcostot+",'I'");
//                               
//                                   intest=1;
//                         
//                         }
//                      }  
//                     
//                        
//                       if(intest==1){
//                            String strQry ="INSERT INTO tbm_detMovInv" +
//                                       "(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
//                                       " tx_codAlt, tx_codAlt2, co_itm, tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                                       " co_bod, "            +//Codigo de la bodega de donde se saco el producto
//                                       " nd_can, nd_tot, nd_cosUnigrp, nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                                       " ,st_reg , nd_costotgrp , st_regrep )" +stb.toString();
//                         //  System.out.println(">>>>>> "+ strQry );
//                            java.sql.PreparedStatement pstDetMovInv = conIns.prepareStatement(strQry); 
//                            pstDetMovInv.executeUpdate();
//                       }
//                         
//           
//                    blnRes=true;
//                }
//                catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//                catch (Exception e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
                return blnRes;                  
             }
          
            
//        
//          private boolean insertarDet(java.sql.Connection conIns, java.sql.Connection con_remota, int intNumDoc){
//                boolean blnRes = false;
//                Librerias.ZafUtil.ZafTipItm  objZafTipItm = new Librerias.ZafUtil.ZafTipItm(objParSis);   
//                StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
//                int intest=0; 
//                  
//                try{
//                     int Co_Bod = getBodPrede();
//                     
//                     int intTipCli=3;
//                         intTipCli= objUltDocPrintUtil.ValidarCodigoCliente(txtCliCod.getText(),conIns);
//                              
//                      double dlbcostouni=0.00;
//                      double bldcanmov=0.00; 
//                      double bldcostot=0.00;
//                     
//                      int intControl=0; 
//                         GLO_strArreItm="";
//                                       
//                      
//                      for(int i=0; i<tblDat.getRowCount();i++){
//                         if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                                                          
//                             
//                      int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM)==null || tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString());
//                      int intCodBod = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));                     
//                      double dlbCan = Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV));
//                      
//                       if(!objZafTipItm.isServicio(intCodItmAct)){ 
//                        
//                           
//                           
//                              
//                                     if(intTipCli==0){   
//                                         if(!objSisCon.existeStockItemEmpresa_Factura_solo_FAC(conIns, con_remota, objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), intCodItmAct , dlbCan , intCodBod )){
//                                                javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();  
//                                                String strTit, strMsg;
//                                                strTit="Mensaje del sistema Zafiro";
//                                                strMsg=" ***El producto : " +  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString()) + " No tiene stock suficiente "+ "\n No se puede facturar!!" ;
//                                                oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                                                tblDat.editCellAt(i,INT_TBL_CANMOV);
//                                                tblDat.changeSelection(i, INT_TBL_CANMOV, false, false);					
//                                                conIns.rollback();                                             
//                                                conIns.setAutoCommit(true);     
//                                                return false;
//                                       }}      
//                              
//                                     objZafInv.movInventario_solo_FAC(con_remota, intCodItmAct, intCodBod, -1, dlbCan, conIns );
//                             
//                                   //**************************************************************************************
//                                   
//                                      if(intTipCli==1){  
//                                          
//                                           if(!objSisCon.existeStockItemGrupo_Factura_solo_FAC(conIns, con_remota, objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), intCodItmAct , dlbCan , intCodBod )){
//                                                javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();  
//                                                String strTit, strMsg;
//                                                strTit="Mensaje del sistema Zafiro";
//                                                strMsg=" ***El producto : " +  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString()) + " No tiene stock suficiente "+ "\n No se puede facturar!!" ;
//                                                oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                                                tblDat.editCellAt(i,INT_TBL_CANMOV);
//                                                tblDat.changeSelection(i, INT_TBL_CANMOV, false, false);					
//                                                conIns.rollback(); 
//                                                if(INTCODREGCEN!=0) con_remota.rollback(); 
//                                                conIns.setAutoCommit(true);     
//                                                return false;
//                                            }    
//                                           
//                                            if(!objSisCon.actualizarInventario_2_solo_OC(conIns, con_remota, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(),intCodItmAct,(dlbCan *-1 ),intCodBod )){
//                                                     conIns.rollback();
//                                                     if(INTCODREGCEN!=0) con_remota.rollback(); 
//                                                     return false;
//                                            }
//                                      }
//                               
//                                //*************************************************************************************************
//                                    
//                                     
//                              }
//                          
//                                
//                       
//                               dlbcostouni = objSisCon.getCostoConsolidado2(conIns , objParSis.getCodigoEmpresa(), Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM )));
//                               bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV ).toString());
//                               bldcostot = (dlbcostouni * bldcanmov);
//                               bldcostot = (bldcostot*-1);
//                               bldcanmov = (bldcanmov * -1);   
//                                           
//                    //******************************************************************************************
//                               
//                                 if(intControl!=0)    
//                                             GLO_strArreItm=GLO_strArreItm+",";
//                                     
//                                             GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//                                    
//                                           intControl++;
//                               
//                               
//                                if (i>0)
//                                   stb.append(" UNION ALL ");
//                                   stb.append("SELECT "+objParSis.getCodigoEmpresa() + ", " +
//                                   objParSis.getCodigoLocal()   + ", " + 
//                                   txtTipDocCod.getText()          + ", " + 
//                                   intNumDoc + ", " +
//                                   (i+1)       + ",'" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT) + "', '" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT2) + "', " +
//                                   tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
//                                   tblDat.getValueAt(i, INT_TBL_DESITM) + "','" +
//                                   tblDat.getValueAt(i, INT_TBL_UNIDAD) + "'," +
//                                   tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " + 
//                                   bldcanmov + ", " + objUti.redondear((Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i,  INT_TBL_TOTAL).toString())*-1),2)+", "+
//                                   dlbcostouni + ", " + bldcostot+ ", " +
//                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), 6) + ", " +
//                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null  )?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString())  , 2) + ", '" +
//                                   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
//                                   ",'P',"+bldcostot+",'I'");
//                               
//                                   intest=1;
//                         
//                         }
//                      }  
//                     
//                        
//                       if(intest==1){
//                            String strQry ="INSERT INTO tbm_detMovInv" +
//                                       "(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
//                                       " tx_codAlt, tx_codAlt2, co_itm, tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                                       " co_bod, "            +//Codigo de la bodega de donde se saco el producto
//                                       " nd_can, nd_tot, nd_cosUnigrp, nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                                       " ,st_reg , nd_costotgrp , st_regrep )" +stb.toString();
//                         //  System.out.println(">>>>>> "+ strQry );
//                            java.sql.PreparedStatement pstDetMovInv = conIns.prepareStatement(strQry); 
//                            pstDetMovInv.executeUpdate();
//                       }
//                         
//           
//                    blnRes=true;
//                }
//                catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//                catch (Exception e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
//                return blnRes;                  
//             }
//          
         
      
        
        private boolean CambiarEstCot(java.sql.Connection conIns ){
           String  Estado="F";
           boolean blnRes=true;
            try{   
                  if(txtNumCot.getText().equals("")){
                  }else{
                          if(modificarCabCot(conIns, false,Estado) ){
                               if(modificarDetCot(conIns)){
                                     if(modificarPagCot( conIns )){
                                          blnRes=true;
                                       }else
                                           return false;
                                 }else
                                     return false;                                         
                            }else 
                                return false;
                 }}
              catch(Exception Evt)   {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;   }                       
              return blnRes;
        }                          
             
      
          
        
//        private boolean insertDetPag(int intNumDoc, java.sql.Connection conIns ){
//               try{
//                      for(int i=0; i<tblCotForPag.getRowCount();i++){
//                          
//                           int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
//                            String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
//
//                            String sql = "INSERT INTO  tbm_pagMovInv" +
//                               "(co_emp, co_loc, co_doc, co_tipDoc, co_reg, " + //CAMPOS PrimayKey
//                               " ne_diaCre, fe_ven, " +//<==
//                               " mo_pag, ne_diaGra, nd_porRet , st_regrep " +//<==
//                               "  )" +
//                               "VALUES (" +
//                                objParSis.getCodigoEmpresa() + ", " +
//                                       objParSis.getCodigoLocal()   + ", " + 
//                                       intNumDoc + ", " +
//                                       txtTipDocCod.getText() + ", " +
//                                       (i+1)       + ", " +
//                                       tblCotForPag.getValueAt(i, 1) + ",'" +
//                                       strFechaPag + "'," +
//                                       (objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) * -1 )+ ", " +
//                                       ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
//                                       ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 3).toString()) +
//                                       " ,'I')"; 
//                            // System.out.println("PAGO: "+ sql );   
//                             java.sql.PreparedStatement pstDetPag = conIns.prepareStatement(sql);
//                             pstDetPag.executeUpdate();
//                      }
//                 }
//                catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
//                catch(Exception Evt) {   objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;   }                       
//                return true;                   
//            }
//        
//        
//     
         
        
           private boolean insertDetPag(int intNumDoc, java.sql.Connection conIns ){
               try{
                    for(int i=0; i<tblCotForPag.getRowCount();i++){
           
                                  int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
                                  String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                                  String strSop = ((tblCotForPag.getValueAt(i, 7)==null)?"":tblCotForPag.getValueAt(i, 7).toString() );
                                             
                                  String sql = "INSERT INTO  tbm_pagMovInv" +
                                               "(co_emp, co_loc, co_doc, co_tipDoc, co_reg, " + //CAMPOS PrimayKey
                                               " ne_diaCre, fe_ven, " +//<==
                                               " mo_pag, ne_diaGra, nd_porRet , st_regrep ,st_sop, co_tipret" +//<==
                                               "  )" +
                                               "VALUES (" +
                                                objParSis.getCodigoEmpresa() + ", " +
                                                       objParSis.getCodigoLocal()   + ", " + 
                                                       intNumDoc + ", " +
                                                       txtTipDocCod.getText() + ", " +
                                                       (i+1)       + ", " +
                                                 ((tblCotForPag.getValueAt(i, 1)==null || tblCotForPag.getValueAt(i, 1).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 1)) + ",'" +
                                                 strFechaPag + "'," +
                                                 (objUti.redondear(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),intNumDec) * -1 )+ ", " +
                                                 ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
                                                 ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 3).toString()) +
                                                 " ,'I','"+strSop+"' ,"+Integer.parseInt(((tblCotForPag.getValueAt(i, 6)==null)?"0":tblCotForPag.getValueAt(i, 6).toString()))+")"; 
                                            java.sql.PreparedStatement pstDetPag = conIns.prepareStatement(sql);
                                            pstDetPag.executeUpdate();
                                   }   
                   
//                      for(int i=0; i<tblCotForPag.getRowCount();i++){
//                          
//                           int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
//                            String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
//
//                            String sql = "INSERT INTO  tbm_pagMovInv" +
//                               "(co_emp, co_loc, co_doc, co_tipDoc, co_reg, " + //CAMPOS PrimayKey
//                               " ne_diaCre, fe_ven, " +//<==
//                               " mo_pag, ne_diaGra, nd_porRet , st_regrep " +//<==
//                               "  )" +
//                               "VALUES (" +
//                                objParSis.getCodigoEmpresa() + ", " +
//                                       objParSis.getCodigoLocal()   + ", " + 
//                                       intNumDoc + ", " +
//                                       txtTipDocCod.getText() + ", " +
//                                       (i+1)       + ", " +
//                                       tblCotForPag.getValueAt(i, 1) + ",'" +
//                                       strFechaPag + "'," +
//                                       (objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) * -1 )+ ", " +
//                                       ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
//                                       ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 3).toString()) +
//                                       " ,'I')"; 
//                            // System.out.println("PAGO: "+ sql );   
//                             java.sql.PreparedStatement pstDetPag = conIns.prepareStatement(sql);
//                             pstDetPag.executeUpdate();
//                      }
                 }
                catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
                catch(Exception Evt) {   objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;   }                       
                return true;                   
            }
        
        
             
         
           private int getUltCoDoc(java.sql.Connection con){
                int valReturn = 0;
                try{
                    Statement stmMaxDoc = con.createStatement();
                    String strSQLMax= "SELECT Max(co_doc) as co_doc  FROM tbm_cabMovInv where " +
                          " co_emp = " + objParSis.getCodigoEmpresa() + 
                          " and co_loc = " + objParSis.getCodigoLocal() +
                          " and co_tipDoc = " + txtTipDocCod.getText() ;
                    ResultSet rstNum = stmMaxDoc.executeQuery(strSQLMax);
                    if(rstNum.next()){
                        valReturn = rstNum.getInt("co_doc");
                    }
                    valReturn++;
                }
                catch(java.sql.SQLException e){ valReturn= -1; }
                catch(Exception e){ valReturn= -1;  }
                return valReturn;
            }
         
         
         private int getUltCoDocCot(java.sql.Connection conMAX){
                int valReturn = 0;
                try{
                     java.sql.Statement stmMaxCot = conMAX.createStatement();
                      sSQL= "SELECT Max(co_cot) as co_cot FROM tbm_cabCotVen where " +
                           " co_emp = "+objParSis.getCodigoEmpresa()+" and co_loc = " + objParSis.getCodigoLocal();
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
        
        
        
        
        
         private boolean consultar3() {
            return _consultar(FilSql2());
        }
        
        
        
        
        @Override
        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
            return _consultar(FilSql());
        }
                                                
        @Override
        public void clickModificar(){
            setEditable(true);
           // txtFecDoc.setHoy();
            java.awt.Color color = txtSub.getBackground();
            
            txtNumDoc.setEditable(false);
            txtNumCot.setEditable(false);
            txtNumCot.setBackground(color); 
            txtNumFac.setEditable(false);
            txtGuia.setEditable(false);
            txtGuia.setBackground(color);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false); 
            butTipDoc.setEnabled(false);
            txtSub.setEditable(false);
            txtIva.setEditable(false);
            txtTot.setEditable(false);
            txtSub.setBackground(color);
            txtIva.setBackground(color);
            txtTot.setBackground(color);
           
            strCodCli="";
              strDesLarCli="";   
              strCodCli="";
              strDesLarCli="";
              
            dblPorIva=dblPorIvaaux;
            lblIva.setText("IVA " + dblPorIva + "%");
            
            
        }
          
        
        
           private boolean InvertirInventario(java.sql.Connection conAnu, java.sql.Connection con_remota, int intCodItm, int intCodBod, double intCodCan, Librerias.ZafUtil.ZafTipItm objZafTipItm, String strEstFisBod ){
            boolean blnRes=true;
            try{
                String  strActInv = "", strActInv2 = "", strOrAct= "";
                
                if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO
                    
                    
                                String strAux="";
                                if(strEstFisBod.equals("N")){
                                 if(strMerIngEgr.equals("S")){
                                   if(strTipIngEgr.equals("I")){ 
                                       if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
                                       if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
                                   }
                                   if(strTipIngEgr.equals("E")){
                                       if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
                                       if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
                                   } 
                                 }} 
                                
                                
                    String sql ="update  TBM_INVBOD set  " +
                    "     nd_stkact = nd_stkact + " + (intCodCan)  +
                    " "+strAux+" "+        
                    " WHERE " +
                    " co_emp = " + objParSis.getCodigoEmpresa() + "and " +
                    " co_bod = " + intCodBod + " and " +
                    " co_itm = " + intCodItm;
                    
                    java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                    pstUptInvBod.executeUpdate();
                    if(INTCODREGCEN!=0){
                        pstUptInvBod = con_remota.prepareStatement(sql);
                        pstUptInvBod.executeUpdate();
                    }
                    strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
                    strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
                    strOrAct  =" or ";
                    
                    
                    int intTipCli=3;
                    intTipCli= objUltDocPrintUtil.ValidarCodigoCliente(txtCodCli.getText(),conAnu);
                    
                    
//                    if(intTipCli==1){
//                        if(!objSisCon.actualizarInventario_2_solo_OC(conAnu, con_remota, objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), intCodItm, Math.abs(intCodCan), intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
//                            return false;
//                        }}
                    
                    
                } //********  FIN DE ITEM SERVICIO
                
                
                if(!strActInv.equals("")){
                    String strUpdate = " UPDATE tbm_inv " +
                    " SET st_regrep='M',  nd_stkAct=b1.nd_stkAct " +
                    " FROM  " +
                    "( " +
                    " SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct " +
                    " FROM tbm_invBod AS a2 " +
                    " WHERE a2.co_emp=1     " +
                    " and ( " + strActInv2 +" ) " +
                    " GROUP BY a2.co_emp, a2.co_itm " +
                    " ORDER BY a2.co_itm " +
                    ") AS b1 " +
                    " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " +
                    " AND ( " + strActInv +" ) " +
                    " AND tbm_inv.co_emp=1";
                    java.sql.PreparedStatement pstActStkInv = conAnu.prepareStatement(strUpdate);
                    pstActStkInv.executeUpdate();
                    if(INTCODREGCEN!=0){
                        pstActStkInv = con_remota.prepareStatement(strUpdate);
                        pstActStkInv.executeUpdate();
                    }
                }
                
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
                private boolean AnularRegEliminados(java.sql.Connection conAnu, java.sql.Connection con_remota, Librerias.ZafUtil.ZafTipItm  objZafTipItm){
                    boolean blnRes=true;
                     try{
                            java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                            if (arlAux!=null){
                                  for (int i=0;i<arlAux.size();i++){
                                       int intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
                                       int intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
                                       double intCodCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
                                     //// System.out.println(">>>  ELEMENTO ELIMINADO >> ");
                                       
                                        if(InvertirInventario(conAnu, con_remota,  intCodItm, intCodBod, intCodCan, objZafTipItm, "" )){
                                               //No actualiza grupo
                                         }else{
                                             return false;
                                         }
                                  }
                             }
                    blnRes=true;
                 }
                 catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
                 return blnRes;   
            }
                  
                
          private boolean actualizarReg(){
               boolean blnRes = false;
                  return blnRes;      
            }
          
          
          
       
          
          
      //************************************ MODIFICAR DATOS DE LA FACTURA 
          
          
              private boolean modificaPag(java.sql.Connection conMod){
                boolean blnRes=false;
                try{
                    java.sql.PreparedStatement pstPagMovInv = conMod.prepareStatement(
                          " DELETE FROM tbm_pagMovInv " +
                           "  where " +
                           " co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
                           " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
                           " co_doc = " +  txtNumDoc.getText() + " and "+
                           " co_tipDoc = " + txtTipDocCod.getText()) ;
                    pstPagMovInv.executeUpdate();   /* Ejecutando el Delete */
                    blnRes=insertDetPag(Integer.parseInt(txtNumDoc.getText()), conMod);                    
                }
                catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
                catch (Exception e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }                
                return blnRes;                   
             }
          
          
        //*****************************************************************************************************      
          
              
     private boolean InvertirDet(java.sql.Connection conMod){
            boolean blnRes=true;
            try{
                ///********** ELIMINA EL DETALLLE
                
                
                java.sql.PreparedStatement pstDelDetMovInv = conMod.prepareStatement(
                " DELETE FROM tbr_detMovInv " +
                "  where co_emp = " + objParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objParSis.getCodigoLocal()   + " and "+
                " co_doc = " +  txtNumDoc.getText() + " and co_tipDoc = " + txtTipDocCod.getText()) ;
                pstDelDetMovInv.executeUpdate();   /* Ejecutando el Delete */
                
                
                pstDelDetMovInv = conMod.prepareStatement(
                " DELETE FROM tbm_detMovInv " +
                "  where co_emp = " + objParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objParSis.getCodigoLocal()   + " and "+
                " co_doc = " +  txtNumDoc.getText() + " and co_tipDoc = " + txtTipDocCod.getText()) ;
                pstDelDetMovInv.executeUpdate();   /* Ejecutando el Delete */
                
                ///********** INSERTA EL DETALLLE
                double dlbcostouni=0.00;
                double bldcanmov=0.00;
                double bldcostot=0.00;
                
                int intControl=0;
                GLO_strArreItm="";
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                        
                        
                        dlbcostouni = 0; //objSisCon.getCostoConsolidado2(conMod , objParSis.getCodigoEmpresa(), Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM )));
                        bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV ).toString());
                        bldcostot = (dlbcostouni * bldcanmov);
                        bldcanmov = (bldcanmov * -1);
                        
                        if(intControl!=0)
                            GLO_strArreItm=GLO_strArreItm+",";
                        
                        GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                        
                        intControl++;
                        
                        java.sql.PreparedStatement pstDetMovInv = conMod.prepareStatement(
                        "INSERT INTO  tbm_detMovInv" +
                        "(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                        " tx_codAlt,tx_codAlt2, co_itm,co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
                        " co_bod, "            +//Codigo de la bodega de donde se saco el producto
                        " nd_can, nd_tot, nd_cosUnigrp, nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                        " ,st_reg )" +
                        "VALUES ( "  +
                        objParSis.getCodigoEmpresa() + ", " +
                        objParSis.getCodigoLocal()   + ", " +
                        txtTipDocCod.getText()          + ", " +
                        txtNumDoc.getText() + ", " +
                        (i+1)       + ",'" +
                        tblDat.getValueAt(i, INT_TBL_ITMALT) + "', '" +
                        tblDat.getValueAt(i, INT_TBL_ITMALT2) + "', " +
                        tblDat.getValueAt(i, INT_TBL_CODITM) + "," +
                        tblDat.getValueAt(i, INT_TBL_CODITMACT) + ",'" +
                        tblDat.getValueAt(i, INT_TBL_DESITM) + "','" +
                        tblDat.getValueAt(i, INT_TBL_UNIDAD) + "'," +
                        tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " +
                        bldcanmov + ", "+objUti.redondear(Double.parseDouble((tblDat.getValueAt(i, INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL ).toString()),2) + ", " +
                        //objZafInv.getCosto(Integer.parseInt(""+tblFacDet.getValueAt(i, INT_TBL_CODITM )))+ ", " +
                        dlbcostouni + ", " + bldcostot + ", "+
                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), 6) + ", " +
                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null  )?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString())  , 2) + ", '" +
                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
                        ",'P' )");
                        
                        pstDetMovInv.executeUpdate();
                        
                        
                        if(tblDat.getValueAt(i, INT_TBL_CODDOCREL) != null){
                            
                            
                          sSQL ="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel)"+
                          " values("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
                           txtTipDocCod.getText()+","+txtNumDoc.getText()+"," +
                           (i+1)+",'A',"+objParSis.getCodigoLocal()+","+
                           tblDat.getValueAt(i, INT_TBL_CODTIPREL).toString()+","+tblDat.getValueAt(i, INT_TBL_CODDOCREL).toString()+", " +
                           " "+tblDat.getValueAt(i, INT_TBL_CODREGREL).toString()+")";
                          java.sql.PreparedStatement tbrMovInv = conMod.prepareStatement(sSQL); 
                           tbrMovInv.executeUpdate();
                           
                        }
                        
                        
                        
                    }}
                
                blnRes=true;
            }
            catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
            catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
            return blnRes;
        }
        
              
              
              
//           private boolean InvertirDet(java.sql.Connection conMod){
//                boolean blnRes=true;
//                try{
//                    ///********** ELIMINA EL DETALLLE 
//                    
//                      
//                       java.sql.PreparedStatement pstDelDetMovInv = conMod.prepareStatement(
//                               " DELETE FROM tbr_detMovInv " +
//                               "  where co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
//                               " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
//                               " co_doc = " +  txtDoc.getText() + " and co_tipDoc = " + txtTipDocCod.getText()) ;
//                      pstDelDetMovInv.executeUpdate();   /* Ejecutando el Delete */
//                      
//                      
//                      pstDelDetMovInv = conMod.prepareStatement(
//                               " DELETE FROM tbm_detMovInv " +
//                               "  where co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
//                               " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
//                               " co_doc = " +  txtDoc.getText() + " and co_tipDoc = " + txtTipDocCod.getText()) ;
//                      pstDelDetMovInv.executeUpdate();   /* Ejecutando el Delete */
//                      
//                    ///********** INSERTA EL DETALLLE 
//                       double dlbcostouni=0.00;
//                       double bldcanmov=0.00; 
//                       double bldcostot=0.00;
//                                               
//                        int intControl=0; 
//                         GLO_strArreItm="";
//                       
//                      for(int i=0; i<tblDat.getRowCount();i++){
//                         if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                              
//                             
//                               dlbcostouni = objSisCon.getCostoConsolidado2(conMod , objParSis.getCodigoEmpresa(), Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM )));
//                               bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV ).toString());
//                               bldcostot = (dlbcostouni * bldcanmov);
//                               bldcanmov = (bldcanmov * -1);   
//                             
//                                  if(intControl!=0)    
//                                                GLO_strArreItm=GLO_strArreItm+",";
//                                     
//                                             GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//                                    
//                                           intControl++;
//                               
//                              java.sql.PreparedStatement pstDetMovInv = conMod.prepareStatement(
//                                  "INSERT INTO  tbm_detMovInv" +
//                                       "(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
//                                       " tx_codAlt,tx_codAlt2, co_itm, tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                                       " co_bod, "            +//Codigo de la bodega de donde se saco el producto
//                                       " nd_can, nd_tot, nd_cosUnigrp, nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                                       " ,st_reg )" +
//                                       "VALUES ( "  +
//                                   objParSis.getCodigoEmpresa() + ", " +
//                                   objParSis.getCodigoLocal()   + ", " + 
//                                   txtTipDocCod.getText()          + ", " + 
//                                   txtDoc.getText() + ", " +
//                                   (i+1)       + ",'" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT) + "', '" +
//                                   tblDat.getValueAt(i, INT_TBL_ITMALT2) + "', " +
//                                   tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
//                                   tblDat.getValueAt(i, INT_TBL_DESITM) + "','" +
//                                   tblDat.getValueAt(i, INT_TBL_UNIDAD) + "'," +
//                                   tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " + 
//                                  bldcanmov + ", "+objUti.redondear(Double.parseDouble((tblDat.getValueAt(i, INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL ).toString()),2) + ", " +
//                                   //objZafInv.getCosto(Integer.parseInt(""+tblFacDet.getValueAt(i, INT_TBL_CODITM )))+ ", " +
//                                    dlbcostouni + ", " + bldcostot + ", "+
//                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), 6) + ", " +
//                                   objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null  )?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString())  , 2) + ", '" +
//                                   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
//                                   ",'P' )"); 
//                             
//                               pstDetMovInv.executeUpdate();
//                               
//                               
//                                 if(tblDat.getValueAt(i, INT_TBL_CODDOCREL) != null){
//                                       
//                                      sSQL ="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel)"+
//                                      " values("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
//                                       txtTipDocCod.getText()+","+txtDoc.getText()+"," +
//                                       (i+1)+",'A',"+objParSis.getCodigoLocal()+","+
//                                       tblDat.getValueAt(i, INT_TBL_CODTIPREL).toString()+","+tblDat.getValueAt(i, INT_TBL_CODDOCREL).toString()+", " +
//                                       " "+tblDat.getValueAt(i, INT_TBL_CODREGREL).toString()+")";
//                                      java.sql.PreparedStatement tbrMovInv = conMod.prepareStatement(sSQL); 
//                                      tbrMovInv.executeUpdate();
//                                 }
//                               
//                     
//                               
//                      }}
//                     
//                  blnRes=true;
//                }
//                catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
//                catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }                
//                return blnRes;
//            }
//          
          
          //***************************************************************************************************** 
           
          
           private boolean modificaCab(java.sql.Connection conMod, String str_estreg){
                boolean blnRes=false;
                try{
                    String strFecha = "#" + txtFecDoc.getFecha("/","y/m/d") + "#";
                    
                    java.sql.PreparedStatement pstCabMovInv = conMod.prepareStatement(
                          "Update tbm_cabMovInv set " +
                           " fe_doc  = '" + strFecha            + "', " +
                           " co_cli  = "  + txtCodCli.getText() + ",  " + 
                           " tx_numped='"  + ((txtPed.getText().equals(""))?"0":txtPed.getText())    + "',  " + 
                           " tx_nomCli='" + txtNomCli.getText() + "', " +
                           " tx_dirCli='" + txtDirCli.getText() + "', " +
                           " tx_ruc='"    + objCliDat.getIdentificacion() + "', " +
                           " tx_telCli='" + objCliDat.getTelefono()       + "', " +
                           " tx_ciuCli='" + objCiuDat.getNombrelargo() + "', " +
                           " co_com  = "  + txtCodVen.getText() + ",  " +
                           " tx_nomven='" + txtNomVen.getText() + "', " +
                           " tx_ate  = '" + txtAte.getText()    + "', " + 
                           " tx_obs1 = '" + txaObs1.getText()   + "', " + 
                           " tx_obs2 = '" + txaObs2.getText()   + "', " + 
                           " nd_sub  = "  + (dblSubtotalCot * -1 ) + ",  " +
                           " nd_porIva = "+ dblPorIva           +  " , " +
                           " nd_tot    = "+ (dblTotalCot * -1 )  +  " , " +
                           " nd_valiva    = "+ (Double.parseDouble(txtIva.getText()) * -1 )  +  " , " +
                           " co_forPag = "+ vecForPag.get(cboForPag.getSelectedIndex()) + ", " +
                           " tx_desforpag = '"+ cboForPag.getSelectedItem() +  "' , " +
                           " tx_ptoPar = '" + txtPartida.getText()       + "', " +
                           " tx_tra    = '" + txtTransporte.getText()    + "', " +
                           " co_motTra = "  + vecTransporte.get(cboMotivoTransporte.getSelectedIndex())             + " , " +
                           " tx_desMotTra='"+ cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex()) + "', " +
                           " fe_ultMod   =  CURRENT_TIMESTAMP                , " +
                           " co_usrMod   = "+ objParSis.getCodigoUsuario() + ", " +
                           " st_reg      = 'C'" +
                           " ,st_regrep='"+str_estreg+"' "+
                           "  where " +
                           " co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
                           " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
                           " co_doc = " +  txtNumDoc.getText()               + " and "+  
                           " co_tipDoc = " + txtTipDocCod.getText());
                    pstCabMovInv.executeUpdate(); /* Ejecutando el Update */
                    blnRes=true;
                }
                catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
                catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }                
                return blnRes;
            }
          
   

           
private boolean eliminarReg(){
 boolean blnRes=false;
 java.sql.Connection conAnu;
 String strCodOc="";
 int i=0;
 try{
    conAnu=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
    if(conAnu!=null){
      conAnu.setAutoCommit(false);
      /*******************************************************************************************************/
       if(!Abrir_Conexion_Remota())
        return false;
      /*******************************************************************************************************/
        String sql = "SELECT b.ne_numdoc FROM tbr_cabmovinv AS a " +
        " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
        " WHERE " +
        " a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" AND a.co_tipdoc="+txtTipDocCod.getText()+" AND a.co_doc="+txtNumDoc.getText();
        java.sql.Statement stm=conAnu.createStatement();
        java.sql.ResultSet rst = stm.executeQuery(sql);
        while(rst.next()){
            i=1;
            strCodOc +=rst.getString(1)+"\n";
        }
        rst.close(); rst=null;
        stm.close(); stm=null;
        if(i>0)
          if(( mensaje("LAS SIGUIENTES ORDENES DE COMPRA SERAN ELIMINADAS \n"+strCodOc +" \n ¿DESEA SEGUIR CON EL PROCESO DE ELIMINACIÓN ?")!=javax.swing.JOptionPane.YES_OPTION)){
              return false;
          }
      /*******************************************************/
        
        
      if(anueliCab(conAnu,"E")){
       if(objAsiDia.eliminarDiario(conAnu, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtNumDoc.getText()))){
        if( EliminarDet_OC(conAnu, con_remota,  objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), txtTipDocCod.getText() , txtNumDoc.getText() ) ){
            
             if(!isAnulada()){
                if(invertirInvDet(conAnu, con_remota)){
                 if(EliminarDet_OC_2(conAnu, con_remota,  objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), txtTipDocCod.getText() , txtNumDoc.getText() ) ){
                    
                   if(INTCODREGCEN!=0) con_remota.commit();
                   conAnu.commit();
                   objTooBar.setEstadoRegistro("Anulado");
                   blnRes=true;
                   objInvItm.limpiarObjeto();   
                 }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }   
                }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback();  }  
             }else{            
                 conAnu.commit();
                 blnRes=true;
             }
        }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }    
       }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback();  }  
      }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback();  }  
          
    if(blnRes==true){
      if(i>0) MensajeInf("LAS SIGUIENTES ÓRDENES DE COMPRA FUERON ELIMINADAS \n"+ strCodOc + " ");
    }
    objInvItm.limpiarObjeto();                     
    if(INTCODREGCEN!=0){ 
        con_remota.close();
        con_remota=null;
    }
    conAnu.close();
    conAnu=null;
  
  }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    catch(Exception Evt){   blnRes=false;   objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
  return blnRes;
}
      
              
    private boolean validacionfechaFac(java.sql.Connection conn){
        boolean blnRes=false;
        String sql="";
        java.sql.Statement stm;
        java.sql.ResultSet rst;
          try{
              if(conn!= null){
            sql = "SELECT count(*) as valor FROM ( " +
            "   SELECT  fe_doc, " +
            "	case extract(dow from fecha) " +
            "   when 1 then 1 " +
            "   when 2 then 2 " +
            "   when 3 then 3 " +
            "   when 4 then 4 " +
            "   when 5 then 5 " +
            "   when 6 then 6 " +
            "   else  0 " +
            " end as dia " +
            ", fecha , 	(" +
//            "   select count(fe_diafer) from tbm_loc as a  " +
//            "   inner join tbm_ciu as b on (b.co_ciu=a.co_ciu) " +
//            "   inner join tbr_diaFerCiu as  c on (c.co_ciu=b.co_ciu) " +
//            "   inner join tbm_diaFer as  d on (d.co_diafer=c.co_diafer) " +
//            "   where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and d.fe_diafer=fecha " +
            "     select count(fe_dia) from tbm_loc as a  " +
            "     inner join tbm_calciu as b on (b.co_ciu=a.co_ciu) " +
            "     where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and b.fe_dia=fecha and b.tx_tipdia = 'F' " +
            " ) as xx " +
            "  FROM ( " +
            "   select fe_doc, (current_date-5) as fecha ,current_date as fe2  from tbm_cabmovinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_tipdoc="+txtTipDocCod.getText()+"  and co_doc="+ txtNumDoc.getText()+" union all " +
            "   select fe_doc, (current_date-6) as fecha ,current_date as fe2  from tbm_cabmovinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_tipdoc="+txtTipDocCod.getText()+"  and co_doc="+ txtNumDoc.getText()+" union all " +
            "   select fe_doc, (current_date-7) as fecha ,current_date as fe2  from tbm_cabmovinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_tipdoc="+txtTipDocCod.getText()+"  and co_doc="+ txtNumDoc.getText()+" union all " +
            "   select fe_doc, (current_date-8) as fecha ,current_date as fe2  from tbm_cabmovinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_tipdoc="+txtTipDocCod.getText()+"  and co_doc="+ txtNumDoc.getText()+" union all " +
            "   select fe_doc, (current_date-9) as fecha ,current_date as fe2  from tbm_cabmovinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_tipdoc="+txtTipDocCod.getText()+"  and co_doc="+ txtNumDoc.getText()+" union all " +
            "   select fe_doc, (current_date-10) as fecha ,current_date as fe2  from tbm_cabmovinv where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_tipdoc="+txtTipDocCod.getText()+"  and co_doc="+ txtNumDoc.getText()+"  " +
            " ) as x  " +
            " WHERE fe_doc >= fecha  ) AS x       WHERE  dia!=0 AND  xx=0";   
             stm=conn.createStatement();
             rst=stm.executeQuery(sql);
             if(rst.next()){
                 if( rst.getInt(1) > 0 ) blnRes=true;     
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
    
      
    
    


   
        
private boolean anueliCab(java.sql.Connection conn, String strEst){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
  if(conn!=null){
   stmLoc=conn.createStatement();
    
   strSql="UPDATE tbm_cabMovInv SET st_regrep='M', st_reg='"+strEst+"', fe_ultMod="+objParSis.getFuncionFechaHoraBaseDatos()+", "+
   " co_usrMod="+objParSis.getCodigoUsuario()+" "+
   " WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" AND "+
   " co_tipDoc="+txtTipDocCod.getText()+" AND co_doc="+txtNumDoc.getText();
   stmLoc.executeUpdate(strSql);
   stmLoc.close();
   stmLoc=null; 
   blnRes=true;
 }}catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}
              

        
private boolean invertirInvDet(java.sql.Connection conn, java.sql.Connection con_remota){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;  
 String strSQL="";
 int intTipCli=3;
 int intCodItm =0;
 int intCodBod=0;
 int intTipStk=0;
 double dlbCanMov=0.00;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();
    intTipCli= objUltDocPrintUtil.ValidarCodigoCliente( txtCodCli.getText(), conn );

    objInvItm.limpiarObjeto(); 
    objInvItm.inicializaObjeto();
      
    String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
    " THEN 'S' ELSE 'N' END AS proconf  ";

    strSQL="SELECT a.co_itm, a.co_bod, abs(a.nd_can) as nd_can  " +
    strAux2+" FROM tbm_detMovInv as a " +
    " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+     
    " WHERE " +
    " a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc ="+objParSis.getCodigoLocal()+" " +
    " AND a.co_tipDoc = "+txtTipDocCod.getText()+" AND a.co_doc ="+txtNumDoc.getText()+" AND b.st_ser='N'";
    rstLoc = stmLoc.executeQuery(strSQL);
    while(rstLoc.next()){
       String strEstFisBod=rstLoc.getString("proconf");                          
       
       intCodItm=rstLoc.getInt("co_itm");
       intCodBod=rstLoc.getInt("co_bod");
       dlbCanMov=rstLoc.getDouble("nd_can");
       
       strEstFisBod=rstLoc.getString("proconf");
       objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, 1, strEstFisBod, strMerIngEgr, strTipIngEgr, -1);  
       intTipStk=1; 
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    
        if(intTipStk==1){
           if(!objInvItm.ejecutaActStock(conn, intTipCli))
             return false;
          
           if(con_remota!=null){
              if(!objInvItm.ejecutaActStock(con_remota, intTipCli))
              return false;
              if(!objInvItm.ejecutaVerificacionStock(con_remota, intTipCli ))
              return false;
           }else{    
              if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
               return false;
            } 
         }
         objInvItm.limpiarObjeto();  
         blnRes=true;
     }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
       catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
   return blnRes;         
 }
             
  
   

        
private boolean anularReg(){
 boolean blnRes=false;
 java.sql.Connection conAnu;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
       
   if(isAnulada()){
      MensajeInf("El Documento esta anulado no se puede reemplazar.");
      return false;
   }

    conAnu=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
    if(conAnu!=null){
     conAnu.setAutoCommit(false);

      if(!validacionfechaFac(conAnu)){
         MensajeInf("NO ES POSIBLE ANULAR FACTURA QUE TENGAN MAS DE 5 DIAS LABORABLES. "); 
         conAnu.close();
         conAnu=null;
         return false;         
      }
    //*******************************************************************************************************
    if(!Abrir_Conexion_Remota())
      return false;          
                                        
    strSql="SELECT co_doc from tbr_cabmovinv where co_emp="+objParSis.getCodigoEmpresa()+" and " +
    " co_locrel="+ objParSis.getCodigoLocal() +" and co_tipdocrel="+txtTipDocCod.getText()+" and co_docrel="+txtNumDoc.getText()+" and st_reg not in('I','E')";
    if(INTCODREGCEN!=0) {
      stmLoc = con_remota.createStatement();
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         MensajeInf("LAS FACTURA YA TIENE NOTA DE CREDITO NO ES POSIBLE REALIZAR EL REEMPLAZO. "); 
         con_remota.close();
         con_remota=null;
         return false;                    
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    }else{
      stmLoc = conAnu.createStatement();
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
        MensajeInf("LAS FACTURA YA TIENE NOTA DE CREDITO NO ES POSIBLE REALIZAR EL REEMPLAZO. "); 
        conAnu.close();
        conAnu=null;
        return false;                    
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    }
                                         
   //*******************************************************************************************************
   if(anueliCab(conAnu, "I")){
    if(invertirInvDet(conAnu, con_remota) ){
     if(objAsiDia.anularDiario(conAnu, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal() , Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtNumDoc.getText()))){
       if(RealizaCambioFactura(conAnu, con_remota, txtTipDocCod.getText(), txtNumDoc.getText() )) { 
          if(INTCODREGCEN!=0){
              con_remota.commit();
              con_remota.setAutoCommit(true);
          }
           conAnu.commit();
           conAnu.setAutoCommit(true);


            if(INTCODREGCEN!=0) asignaSecEmpGrp( conAnu, con_remota, stbDocRelEmpLoc);
            else  asignaSecEmpGrp( conAnu, conAnu, stbDocRelEmpLoc);


             MensajeInf("LA FACTURA No "+txtNumFac.getText()+ " SE HA REEMPLAZADO POR LA FACTURA "+strNumFacRee+"  "); 
                                                      
             objTooBar.setEstadoRegistro("Anulado");
             blnRes=true;
                                                         
      }else { if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }
     }else { if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }
    }else { if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }
   }else { if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }
     
   objInvItm.limpiarObjeto();  
                                                
        if(INTCODREGCEN!=0) con_remota.close();
        conAnu.close();
        if(INTCODREGCEN!=0) con_remota=null;
        conAnu=null;
                                
   }}catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
     catch(Exception Evt){   objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }                       
 return blnRes;
}    
         
            


                
private boolean RealizaCambioFactura(java.sql.Connection con_local, java.sql.Connection con_remota, String strTipDoc, String strCodDoc){
 boolean blnRes=false;
 try{ 
  if (con_local!=null){  
      Ventas.ZafVen24.ZafVen24_01 obj1 = new  Ventas.ZafVen24.ZafVen24_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis , txtNumDoc.getText() , txtTipDocCod.getText() , con_local ,con_remota ,  objParSis.getCodigoLocal()  );
      obj1.show(); 
      if(obj1.acepta()){

          blnRes=true;

          strNumFacRee = obj1.txtNumFac();
          strCodDocRee = obj1.txtCodDoc();

          stbDocRelEmpLoc=obj1.stbDocDevEmp;

       }
      obj1.dispose();
      obj1=null;
  }}catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e);  blnRes=false;  } 
return blnRes;
}

           
           private boolean EliminarDet_OC(java.sql.Connection conAnu, java.sql.Connection conRemoto, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
             boolean blnRes=false;
             String sql = "";
                 try{
                         sql = "SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel FROM tbr_cabmovinv AS a " +
                         " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                         " WHERE " +
                         " a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;        
                         java.sql.Statement stm=conAnu.createStatement();
                         java.sql.ResultSet rst = stm.executeQuery(sql);
                        
                         while(rst.next()){
                                
                               //----------------  PROCESO PARA OC 
                                 if(!objUltDocPrintUtil.Verificar_Doc_Asociado_OC_anu_Fac(conAnu, rst.getString(3),rst.getString(2), strCodTipDoc ))
                                          return false;
                                 if(!objUltDocPrintUtil.verificarsiesconfirmado(rst.getString(3),rst.getString(2) ))
                                          return false;
                                 if (objUltDocPrintUtil.isPagoDocumento_aso(objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),rst.getInt(2), rst.getInt(3) ))
                                           return false;
                                //----------------------------------
                              
                                if(!EliminarDet_Doc_OC(conAnu, intCodEmp, intCodLoc, rst.getString(2) , rst.getString(3) ) )
                                      return false;
                              
                         }
                  
                     blnRes=true;
                }
                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
                return blnRes;   
         }
       
           
           
       
           
           
           
            private boolean EliminarDet_OC_2(java.sql.Connection conAnu, java.sql.Connection conRemoto, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
             boolean blnRes=false;
             String sql = "";
                 try{
                         sql = "SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel FROM tbr_cabmovinv AS a " +
                         " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                         " WHERE " +
                         " a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;        
                         java.sql.Statement stm=conAnu.createStatement();
                         java.sql.ResultSet rst = stm.executeQuery(sql);
                        
                         while(rst.next()){
                             
                                if(!anulaDet_Itm_OC(conAnu, conRemoto,  intCodEmp, intCodLoc, rst.getString(2) , rst.getString(3) ) )
                                      return false;          
                                
                              
                         }
                  
                     blnRes=true;
                }
                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
                return blnRes;   
         }
       
       
           
           
           
           private boolean EliminarDet_Doc_OC(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
             boolean blnRes=false;
             String sql = "";
                 try{
                               sql="UPDATE tbm_cabMovInv SET " +
                               " st_reg  = 'E', fe_ultMod ='"+strFecSis+"', co_usrMod = "+objParSis.getCodigoUsuario()+" " +
                               "  WHERE co_emp = "+intCodEmp+" AND co_loc = "+intCodLoc+" AND "+  
                               " co_doc = "+strCodDoc+" AND co_tipDoc = "+strCodTipDoc;
                               java.sql.PreparedStatement Pstm = conAnu.prepareStatement(sql);        
                               Pstm.executeUpdate();     
                         
                               if (!objAsiDia.eliminarDiario(conAnu, intCodEmp, intCodLoc, Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)))
                                    return false;
                               
                        
                     blnRes=true;
                }
                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
                return blnRes;   
         }
         
       
           
           
           
           
           
           private boolean anulaDet_Doc_OC(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
             boolean blnRes=false;
             String sql = "";
                 try{
                               sql="UPDATE tbm_cabMovInv SET " +
                               " st_reg  = 'I', fe_ultMod ='"+strFecSis+"', co_usrMod = "+objParSis.getCodigoUsuario()+" " +
                               "  WHERE co_emp = "+intCodEmp+" AND co_loc = "+intCodLoc+" AND "+  
                               " co_doc = "+strCodDoc+" AND co_tipDoc = "+strCodTipDoc;
                               java.sql.PreparedStatement Pstm = conAnu.prepareStatement(sql);        
                               Pstm.executeUpdate();     
                         
                               if (!objAsiDia.anularDiario(conAnu, intCodEmp, intCodLoc, Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)))
                                    return false;
                               
                  
                     blnRes=true;
                }
                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
                return blnRes;   
         }
         
         
         
       
    
private boolean anulaDet_Itm_OC(java.sql.Connection conAnu, java.sql.Connection con_remota, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strMerIngEgrOc="", strTipIngEgrOc="";
 String strSql="";
 int i=0;
 int intTipCli=3;
 int intCodItm =0;
 int intCodBod=0;
 int intTipStk=0;
 double dlbCanMov=0;
 try{
    stmLoc=conAnu.createStatement();
   
    strSql="SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp="+intCodEmp+" "+
    " AND co_loc="+intCodLoc+" and co_tipDoc="+strCodTipDoc;
    rstLoc = stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
       strMerIngEgrOc=rstLoc.getString("st_meringegrfisbod");  
       strTipIngEgrOc=rstLoc.getString("tx_natdoc");  
    }
    rstLoc.close();
    rstLoc=null;
     objInvItm.limpiarObjeto(); 
     objInvItm.inicializaObjeto();
     String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
    " THEN 'S' ELSE 'N' END AS proconf  ";

     strSql="SELECT co_itm, abs(nd_can) as nd_can, co_bod ,tx_codalt ,b.co_cli " +
    " "+strAux2+" FROM tbm_detmovinv AS a" +
    " INNER JOIN tbm_cabmovinv as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc)" +
    " WHERE  a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+"  " +
    " AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
    rstLoc = stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
      i++;
      if(i==1)
        intTipCli= objUltDocPrintUtil.ValidarCodigoCliente(rstLoc.getString("co_cli"),conAnu);
                    
       String strEstFisBod=rstLoc.getString("proconf");                          
       
       intCodItm=rstLoc.getInt("co_itm");
       intCodBod=rstLoc.getInt("co_bod");
       dlbCanMov=rstLoc.getDouble("nd_can");
       
       objInvItm.generaQueryStock(objParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, strMerIngEgrOc, strTipIngEgrOc, -1);  
       intTipStk=1; 
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    
    if(intTipStk==1){
       if(!objInvItm.ejecutaActStock(conAnu, intTipCli))
         return false;
          
        if(con_remota!=null){
          if(!objInvItm.ejecutaActStock(con_remota, intTipCli))
            return false;
          if(!objInvItm.ejecutaVerificacionStock(con_remota, intTipCli ))
            return false;
        }else{    
           if(!objInvItm.ejecutaVerificacionStock(conAnu, intTipCli ))
             return false;
         } 
    }
    objInvItm.limpiarObjeto(); 
    blnRes=true;
  
  }catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}
        

                 
           
//            
//          private boolean anulaDet_Itm_OC(java.sql.Connection conAnu, java.sql.Connection con_remota, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
//              boolean blnRes=false;
//              String sql = "", GLO_strArreItm="", strMsgError="";
//              int intControl=0,i=0;
//              int intTipMov = -1;
//               try{
//                        java.util.ArrayList arlActInv=new java.util.ArrayList();
//                      
//                         int intTipCli=3;
//                                         
//                                                 
//                        sql = "SELECT co_itm, abs(nd_can) as nd_can, co_bod ,tx_codalt ,b.co_cli FROM tbm_detmovinv AS a" +
//                        " INNER JOIN tbm_cabmovinv as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc)" +
//                        " WHERE  a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+"  " +
//                        " AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;        
//                        
//                      ///  System.out.println(">> "+ sql );
//                        
//                         java.sql.Statement stm=conAnu.createStatement();
//                         java.sql.ResultSet rst = stm.executeQuery(sql);
//                         while(rst.next()){
//                                   i++;
//                                   if(i==1)
//                                      intTipCli= objUltDocPrintUtil.ValidarCodigoCliente(rst.getString("co_cli"),conAnu);
//                                   
//                                  if (!objZafInv.isItemServicio(intCodEmp, rst.getInt("co_itm") )){
//                                      arlActInv.add(rst.getString("co_itm"));                                                
//                                       
//                                        if(intControl!=0)    
//                                          GLO_strArreItm=GLO_strArreItm+",";
//                                          GLO_strArreItm=GLO_strArreItm+rst.getString("co_itm");
//                                                
//                                         intControl++;
//
//                                                   
//                                             //******************************************************
//                                               if(intTipCli==0){ 
//                                                 if (!objZafInv.movInventario_nuevo_2_solo_OC(conAnu, con_remota, intCodEmp, intCodLoc, rst.getInt("co_itm") , rst.getInt("co_bod"), rst.getDouble("nd_can") , intTipMov , intTipCli, "", "", "" )){                                                        
//                                                        double dblFalta = objSisCon.getStockConsolidado()-java.lang.Math.abs(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)));                                                    
//                                                        if (strMsgError.equals("")){                                                            
//                                                            if (mensaje("Ocurrio un problema en el inventario \n ¿Desea seguir verificando?")!=javax.swing.JOptionPane.YES_OPTION){
//                                                                MensajeInf("\n "+ (i+1)+".-" + tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +" \tfalta " + dblFalta + " para completar el pedido."); 
//                                                                conAnu.rollback();
//                                                                if(INTCODREGCEN!=0) con_remota.rollback();
//                                                                if(INTCODREGCEN!=0) con_remota.close();
//                                                                conAnu.close();
//                                                                return false;
//                                                            }                                                                 
//                                                        }
//                                                         strMsgError += "\n "+ (i+1)+".-"+ tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +" \tfalta " + dblFalta + " para completar el pedido.";
//                                                }}
//                                               if(intTipCli==1){    
//                                                if (!objZafInv.movInventario_nuevo_solo_OC(conAnu, con_remota, intCodEmp, intCodLoc,  rst.getInt("co_itm") , rst.getInt("co_bod"), rst.getDouble("nd_can") , intTipMov, "", "", "" )){                                                        
//                                                        double dblFalta = objSisCon.getStockConsolidado()-java.lang.Math.abs(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)));                                                    
//                                                        if (strMsgError.equals("")){                                                            
//                                                            if (mensaje("Ocurrio un problema en el inventario \n ¿Desea seguir verificando?")!=javax.swing.JOptionPane.YES_OPTION){
//                                                                MensajeInf("\n "+ (i+1)+".-" + tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +" \tfalta " + dblFalta + " para completar el pedido."); 
//                                                                conAnu.rollback();
//                                                                if(INTCODREGCEN!=0) con_remota.rollback();
//                                                                if(INTCODREGCEN!=0) con_remota.close();
//                                                                conAnu.close();
//                                                                return false;
//                                                            }                                                                
//                                                        }
//                                                        strMsgError += "\n "+ (i+1)+".-"+ tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +" \tfalta " + dblFalta + " para completar el pedido.";
//                                                }}
//                                               //********************************************             
//                                        }}                                            
//                                       
//                         
//                                     if (arlActInv.size()>0){
//                                            if (!objZafInv.UpdateInventarioMaestro_solo_OC(conAnu, con_remota, intCodEmp, arlActInv)){
//                                                conAnu.rollback();
//                                                 if(INTCODREGCEN!=0) con_remota.rollback();
//                                                 if(INTCODREGCEN!=0) con_remota.close();
//                                                conAnu.close();
//                                                return false;                                                
//                                            }
//                                        }
//                                        arlActInv=null;
//                                        if (!strMsgError.equals("")){
//                                            MensajeInf("Los sgts items tuvieron problemas:" + strMsgError);
//                                            conAnu.rollback();
//                                             if(INTCODREGCEN!=0) con_remota.rollback();
//                                             if(INTCODREGCEN!=0) con_remota.close();
//                                            conAnu.close();
//                                            return false;
//                                        }
//                         
//                         
//                                         
//                   blnRes=true;
//                }
//                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
//                return blnRes;   
//         }
//            
     
          
       private int mensaje(String strMsg){
        //JOptionPane oppMsg=new JOptionPane();
        //String strTit="Sistema Zafiro";        
        return JOptionPane.showConfirmDialog(jfrThis,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
    }   
          
          
            
        private boolean cambiarEstadOdDetPag(java.sql.Connection conAnu, String tabla, String Estreg){
             boolean blnRes=false;
                 try{
                   
                     String sql="UPDATE "+tabla+" SET st_regrep='"+Estreg+"' WHERE " +
                     " co_emp = " + objParSis.getCodigoEmpresa() + " and "+  
                     " co_loc = " + objParSis.getCodigoLocal()   + " and "+  
                     " co_doc = " +  txtNumDoc.getText()               + " and "+  
                     " co_tipDoc = " + txtTipDocCod.getText();
                   
             
                     java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                     pstUptInvBod.executeUpdate();   
                blnRes=true;
                }
                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
                return blnRes;   
         }
        
       
        
        
        
        
        
        



        
        private boolean anulaDet(java.sql.Connection conAnu, java.sql.Connection con_remota ){
            boolean blnRes=false;
            try{
                Librerias.ZafUtil.ZafTipItm  objZafTipItm = new Librerias.ZafUtil.ZafTipItm(objParSis);
                String  strActInv = "", strActInv2 = "", strOrAct= "";
                
                
                int intTipCli=3;
                intTipCli= objUltDocPrintUtil.ValidarCodigoCliente(txtCodCli.getText(),conAnu);
                
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                        if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("N")){
                            System.out.println("ELEMENTO NUEVO NO HACE MOVIMIENDO EN LA ANULACION >> ");
                        }else {
                            System.out.println("ELEMENTO ORIGINAL O MODIFICADO >> ");
                            
                            int intCodItm =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODORI).toString()));
                            int intCodBod =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_BODORI).toString()));
                            double intCodCan = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANORI).toString()));
                            
                            String strEstFisBod=""; //(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                        
                            
                            if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO
                                
                                   
                        String strAux="";
                        if(strEstFisBod.equals("N")){
                         if(strMerIngEgr.equals("S")){
                           if(strTipIngEgr.equals("I")){ 
                               if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
                           }
                           if(strTipIngEgr.equals("E")){
                               if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
                           } 
                         }} 

                        
                                String sql ="update  TBM_INVBOD set  " +
                                "     nd_stkact = nd_stkact + " + (intCodCan)  +
                                strAux+ " WHERE " +
                                " co_emp = " + objParSis.getCodigoEmpresa() + "and " +
                                " co_bod = " + intCodBod + " and " +
                                " co_itm = " + intCodItm;
                                java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                                pstUptInvBod.executeUpdate();
                                
                                if(INTCODREGCEN!=0){
                                    pstUptInvBod = con_remota.prepareStatement(sql);
                                    pstUptInvBod.executeUpdate();
                                }
                                
                                strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
                                strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
                                strOrAct  =" or ";
                                
                                
                            } //********  FIN DE ITEM SERVICIO
                            
                        }
                    }}
                
                //********* BUSCAR ELEMENTO ELIMADOS EN LA TABLA
                java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                if (arlAux!=null){
                    for (int i=0;i<arlAux.size();i++){
                        int intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
                        int intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
                        double intCodCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
                        String strEstFisBod= ""; //objUti.getStringValueAt(arlAux, i, INT_ARR_IEBODFIS);
                        
                        if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO
                            
                            
                        String strAux="";
                        if(strEstFisBod.equals("N")){
                         if(strMerIngEgr.equals("S")){
                           if(strTipIngEgr.equals("I")){ 
                               if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
                           }
                           if(strTipIngEgr.equals("E")){
                               if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
                           } 
                         }} 

                
                            String sql="update  TBM_INVBOD set  " +
                            "     nd_stkact = nd_stkact + " + (intCodCan)  +
                            strAux+ " WHERE " +
                            " co_emp = " + objParSis.getCodigoEmpresa() + "and " +
                            " co_bod = " + intCodBod + " and " +
                            " co_itm = " + intCodItm;
                            
                            java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                            pstUptInvBod.executeUpdate();
                            if(INTCODREGCEN!=0) {
                                pstUptInvBod = con_remota.prepareStatement(sql);
                                pstUptInvBod.executeUpdate();
                            }
                            
                            strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
                            strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
                            strOrAct  =" or ";
                            
                            
                            
                        } //********  FIN DE ITEM SERVICIO
                    }
                }
                
                
                if(!strActInv.equals("")){
                    String strUpdate = " UPDATE tbm_inv " +
                    " SET st_regrep='M', nd_stkAct=b1.nd_stkAct " +
                    " FROM  " +
                    "( " +
                    " SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct " +
                    " FROM tbm_invBod AS a2 " +
                    " WHERE a2.co_emp=1     " +
                    " and ( " + strActInv2 +" ) " +
                    " GROUP BY a2.co_emp, a2.co_itm " +
                    " ORDER BY a2.co_itm " +
                    ") AS b1 " +
                    " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " +
                    " AND ( " + strActInv +" ) " +
                    " AND tbm_inv.co_emp=1";
                    java.sql.PreparedStatement pstActStkInv = conAnu.prepareStatement(strUpdate);
                    pstActStkInv.executeUpdate();
                    if(INTCODREGCEN!=0){
                        pstActStkInv = con_remota.prepareStatement(strUpdate);
                        pstActStkInv.executeUpdate();
                    }       }
                
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
             
            
//            
//         private boolean anulaDet(java.sql.Connection conAnu, java.sql.Connection con_remota ){
//             boolean blnRes=false;
//                 try{
//                    Librerias.ZafUtil.ZafTipItm  objZafTipItm = new Librerias.ZafUtil.ZafTipItm(objParSis); 
//                    String  strActInv = "", strActInv2 = "", strOrAct= "";
//                     
//                    
//                       int intTipCli=3;
//                           intTipCli= objUltDocPrintUtil.ValidarCodigoCliente(txtCliCod.getText(),conAnu);
//                             
//                     
//                    for(int i=0; i<tblDat.getRowCount();i++){
//                         if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                          if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("N")){
//                              System.out.println("ELEMENTO NUEVO NO HACE MOVIMIENDO EN LA ANULACION >> ");
//                          }else {
//                                //System.out.println("ELEMENTO ORIGINAL O MODIFICADO >> ");
//                                 
//                                 int intCodItm =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODORI).toString()));
//                                 int intCodBod =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_BODORI).toString()));
//                                 double intCodCan = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANORI).toString()));
//                                                 
//                                 if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO 
//                                     
//                                     String sql ="update  TBM_INVBOD set  " +
//                                            "     nd_stkact = nd_stkact + " + (intCodCan)  +
//                                            " WHERE " +
//                                            " co_emp = " + objParSis.getCodigoEmpresa() + "and " +
//                                            " co_bod = " + intCodBod + " and " +
//                                            " co_itm = " + intCodItm;
//                                       java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
//                                       pstUptInvBod.executeUpdate();      
//                                       
//                                       if(INTCODREGCEN!=0){
//                                          pstUptInvBod = con_remota.prepareStatement(sql);
//                                          pstUptInvBod.executeUpdate();                                         
//                                       }
//                                       
//                                       strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
//                                       strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
//                                       strOrAct  =" or ";
//                                      
//                                        if(intTipCli==1){
//                                             if(!objSisCon.actualizarInventario_2_solo_OC(conAnu, con_remota, 
//                                                            objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), 
//                                                            intCodItm, Math.abs(intCodCan), intCodBod, "", "", "" )){
//                                             if(INTCODREGCEN!=0) con_remota.rollback();
//                                             conAnu.rollback();
//                                           return false;
//                                         }}
//                                     
//                                 } //********  FIN DE ITEM SERVICIO
//                              
//                          }
//              }}
//             
//              //********* BUSCAR ELEMENTO ELIMADOS EN LA TABLA 
//              java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
//                                        if (arlAux!=null){
//                                             for (int i=0;i<arlAux.size();i++){
//                                                 int intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
//                                                 int intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
//                                                 double intCodCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
//                                                 
//                                                  if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO 
//                                                     
//                                                  String sql="update  TBM_INVBOD set  " +
//                                                       "     nd_stkact = nd_stkact + " + (intCodCan)  +
//                                                       " WHERE " +
//                                                       " co_emp = " + objParSis.getCodigoEmpresa() + "and " +
//                                                       " co_bod = " + intCodBod + " and " +
//                                                       " co_itm = " + intCodItm;
//                                                     
//                                                       java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
//                                                       pstUptInvBod.executeUpdate();   
//                                                        if(INTCODREGCEN!=0) {
//                                                           pstUptInvBod = con_remota.prepareStatement(sql);
//                                                           pstUptInvBod.executeUpdate();
//                                                        }
//                                                       
//                                                       strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
//                                                       strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
//                                                       strOrAct  =" or ";
//                                                      
//                                                       if(intTipCli==1){
//                                                            if(!objSisCon.actualizarInventario_2_solo_OC(conAnu, con_remota,
//                                                            objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(), 
//                                                            intCodItm, Math.abs(intCodCan), intCodBod, "", "", "" )){
//                                                             if(INTCODREGCEN!=0) con_remota.rollback(); 
//                                                            conAnu.rollback();
//                                                            return false;
//                                                        }}
//                                       
//                                       
//                                                  } //********  FIN DE ITEM SERVICIO
//                                                 //System.out.println("ELEMENTO ELIMINADO CODITEM >> "+intCodItm); 
//                                                // System.out.println("ELEMENTO ELIMINADO CODIBOD >> "+intCodBod);
//                                                // System.out.println("ELEMENTO ELIMINADO CODCAN >> "+intCodCan);
//                                        }
//                                    }
//              
//                              
//                         if(!strActInv.equals("")){
//                              String strUpdate = " UPDATE tbm_inv " +
//                                                 " SET st_regrep='M', nd_stkAct=b1.nd_stkAct " +
//                                                 " FROM  " +
//                                                 "( " +
//                                                 " SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct " +
//                                                 " FROM tbm_invBod AS a2 " +
//                                                 " WHERE a2.co_emp=1     " +
//                                                 " and ( " + strActInv2 +" ) " +
//                                                 " GROUP BY a2.co_emp, a2.co_itm " +
//                                                 " ORDER BY a2.co_itm " +
//                                                 ") AS b1 " +
//                                                 " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " + 
//                                                 " AND ( " + strActInv +" ) " +
//                                                 " AND tbm_inv.co_emp=1";
//                             java.sql.PreparedStatement pstActStkInv = conAnu.prepareStatement(strUpdate);
//                             pstActStkInv.executeUpdate();    
//                             if(INTCODREGCEN!=0){
//                                 pstActStkInv = con_remota.prepareStatement(strUpdate);
//                                 pstActStkInv.executeUpdate();   
//                    }       }
//              
//                blnRes=true;
//                }
//                catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
//                return blnRes;   
//         }
//        
            
              
            
            /**
             *  Devuelve todo el inventario de los productos vendidos en esta 
             * factura que se va a anular, a la base 
             */

                       
        //******************************************************************************************************
          @Override
          public boolean vistaPreliminar(){
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;            
        }
          
         
        @Override
        public boolean imprimir(){
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;                  
         }
         
        
     //******************************************************************************************************
         
           
        @Override
        public void clickImprimir(){ 
        
        }
        
        @Override
        public void clickVisPreliminar(){ 
        
        }
  
        @Override
        public void clickCancelar(){
            if(STMCAB!=null){
                STMCAB=null;
                RSTCAB=null;
            }
            System.gc();
        }
   
         private void cierraConnections(){
                    try{
                        if(conCab !=null)
                            if(!conCab.isClosed()){
                                conCab.close();
                                conCab = null;
                                rstCab = null;
                                stmCab = null;
                            }
                    }
                
                    catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
                    catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); }                 
            }
        
           
        @Override
        public boolean beforeAceptar() {
            return true;
        }        
        
        @Override
        public boolean beforeAnular() {
            return true;
        }        
        
        @Override
        public boolean beforeCancelar() {
            return true;
        }        
        
        @Override
        public boolean beforeConsultar() {
            burRee.setEnabled(true);  
            return true;
        }        
        
        @Override
        public boolean beforeEliminar() {
            return true;
        }        
        
        @Override
        public boolean beforeImprimir() {
              return true;
        }        
        
        @Override
        public boolean beforeInsertar() {
            return true;
        }        
        
        @Override
        public boolean beforeModificar() {
            return true;
        }        
        
        @Override
        public boolean beforeVistaPreliminar() {
             return true;
        }
        

        
        
        
   }   
    

    
          private String FilSql2() {
                String sqlFiltro = "";
                //Agregando filtro por codigo de Tipo de Documento
                if(!txtTipDocCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc LIKE '" + txtTipDocCod.getText()+ "'";
                
                //Agregando filtro por Numero de Documento
                if(!txtNumDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_doc LIKE '" + txtNumDoc.getText()+ "'";

                if(!txtNumFac.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc LIKE '" + txtNumFac.getText()+ "'";

                //Agregando filtro por Fecha
                if(txtFecDoc.isFecha()){
                    int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
                    sqlFiltro = sqlFiltro + " and DocCab.fe_doc = '" +  strFecSql + "'";
                }    
                //Agregando filtro por Codigo de Cliente
                if(!txtCodCli.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_cli LIKE '" + txtCodCli.getText() + "'";
                    
//               sqlFiltro = sqlFiltro +  " and " +
//                        " menu.co_mnu = " + intCodMnu                       + " and " +  
//                        " menu.co_tipdoc = doc.co_tipdoc and DocCab.co_tipdoc = doc.co_tipdoc ";
              return sqlFiltro ;
            }
       
    
          
          
            private String FilSql() {
                String sqlFiltro = "";
                //Agregando filtro por codigo de Tipo de Documento
                if(!txtTipDocCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc="+txtTipDocCod.getText();
                
                //Agregando filtro por Numero de Documento
                if(!txtNumDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_doc="+txtNumDoc.getText();

                if(!txtNumFac.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc="+txtNumFac.getText();

                //Agregando filtro por Fecha
                if(txtFecDoc.isFecha()){
                    int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
                    sqlFiltro = sqlFiltro + " and DocCab.fe_doc = '" +  strFecSql + "'";
                }    
                //Agregando filtro por Codigo de Cliente
                if(!txtCodCli.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_cli="+txtCodCli.getText();
                    
                //Agregando filtro por Codigo de Vendedor
                if(!txtCodVen.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.co_com="+txtCodVen.getText();

                //Agregando filtro por Codigo de Vendedor
                if(!txtAte.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and DocCab.tx_ate LIKE '" + txtAte.getText() + "'";
                
                    //Agregando filtro por Nombre del cliente
                    if(!txtNomVen.getText().equals(""))
                        sqlFiltro = sqlFiltro + " and DocCab.tx_nomVen LIKE '" + txtNomVen.getText() + "'";

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
  boolean blnRes=false;
  String strSql="";        
  strFiltro= strFil;
  try{
      Abrir_Conexion();
      if(CONN_GLO != null){
          STMCAB=CONN_GLO.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
          strSql="SELECT DocCab.co_doc, DocCab.co_tipdoc,DocCab.co_forPag,DocCab.st_reg   " + //<==Campos que aparecen en la parte superior del 1er Tab
          " FROM tbm_cabMovInv as DocCab" +
          " WHERE DocCab.co_emp="+objParSis.getCodigoEmpresa()+ 
          " AND  DocCab.co_loc="+objParSis.getCodigoLocal()+" AND DocCab.st_reg not in ('E') "+
          strFiltro +" ORDER BY DocCab.ne_numdoc"; 
          RSTCAB=STMCAB.executeQuery(strSql);                      
          if(RSTCAB.next()){
             RSTCAB.last();
            objTooBar.setMenSis("Se encontraron " + RSTCAB.getRow() + " registros");
            if(cargarReg(CONN_GLO)) 
                blnRes=true;
          }else{
               objTooBar.setMenSis("0 Registros encontrados");
               clnTextos();
           }
         Cerrar_Conexion();
          
      }}catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false;  }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }                       
      System.gc(); 
   return blnRes;     
}
     
   
  
    
    
    
   private void cargarRegistroInsert(){
                   try{//odbc,usuario,password
                            conCab=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                            if (conCab!=null){

                                //stmCot=conCot.createStatement();
                                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                
                                 /*
                                 * Agregando el Sql de Consulta para la cotizacion
                                 */
                                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,CotCab.co_cot,CotCab.st_reg " + // Campo para saber si esta anulado o no
                                      " FROM tbm_cabCotven as CotCab " +
                                      " Where CotCab.co_emp = " + objParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                                      "  and CotCab.co_loc = "  + objParSis.getCodigoLocal() + // Consultando en el local en el ke se esta trabajando
                                      "  and CotCab.co_cot = "  + txtNumCot.getText();
                                
                                //System.out.println(sSQL);
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
    
   
   
   
   
   
   
      private boolean validaCampos(){
               if(txtCodCli.getText().equals("") ){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Cliente");
                   txtCodCli.requestFocus();
                   return false;
               }
               if(!txtFecDoc.isFecha()){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Fecha de Cotización");
                   txtFecDoc.requestFocus();
                   return false;
               }
               if(txtCodVen.getText().equals("") ){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Vendedor");
                   txtCodVen.requestFocus();
                   return false;
               }
               /* 
                * VAlidando los datos del Pago
                */
               if(cboForPag.getSelectedIndex() ==-1 ){
                   tabCotCom.setSelectedIndex(1);
                   MensajeValidaCampo("Forma de pago");
                   cboForPag.requestFocus();
                   return false;
               }
               
               int intColObligadasPag[] = {2};
               if( !objUti.verifyTbl(tblCotForPag,intColObligadasPag)){
                  tabCotCom.setSelectedIndex(1);
                  //JOptionPane obj = new JOptionPane();
                        /*String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";*/
                        String strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                    objUti.verifyTbl(tblCotForPag,intColObligadasPag);
                    return false;
               }
               
               
               
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
               for(int rowIdx = 0; rowIdx<tblCotForPag.getRowCount() ;rowIdx++){
                    double dblSubMonto = Double.parseDouble((tblCotForPag.getValueAt(rowIdx, 4)==null)?"0":tblCotForPag.getValueAt(rowIdx, 4).toString());
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
                               "co_emp = " + objParSis.getCodigoEmpresa() + " and " +
                               "co_cli = " + Integer.parseInt(txtCodCli.getText());
               try{
                    java.sql.Connection conPorDes = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
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
               }
               catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
               catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   return false;  }                       
               return true; 
          }
   
   
       private double getStkAct(String int_co_itm){
               double dblStkAct = 0;
               try{
                        java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if (conUni!=null){
                            java.sql.Statement stmUni = conUni.createStatement();
                            String sSQL= "SELECT nd_stkact " +
                                            " from tbm_inv as inv " +
                                            " where inv.co_emp = " + objParSis.getCodigoEmpresa() +  " and " +
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
                        java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                        if (conUni!=null){
                            java.sql.Statement stmUni = conUni.createStatement();
                            String sSQL= "SELECT nd_prevta1 " +
                                            " from tbm_inv as inv " +
                                            " where inv.co_emp = " + objParSis.getCodigoEmpresa() +  " and " +
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
          conTmp = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          if (conTmp!=null)
          {
              stmTmp = conTmp.createStatement();
              strSQL = "";
              strSQL = " Select count(*) from tbm_cabmovinv ";
              strSQL += " where  co_emp = " + objParSis.getCodigoEmpresa() + " and  co_loc = " +objParSis.getCodigoLocal()+ " and co_doc = " + txtNumDoc.getText();
              strSQL += " and co_tipdoc = " + txtTipDocCod.getText() + " and st_reg = 'I' " ;
              if (objUti.getNumeroRegistro(this,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),strSQL)>0){
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
    
    
    
    
    private class ActLisForPag implements ActionListener{
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
    private void generaAsiento(){
        
        
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
    
    
    
    
    
    
      private double getTotalDescuento(){
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
         
    
      
      
      
        
    
     private double getSubtotalSinTrans(){

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
 
  
     
     private double getSubtotalTrans(){

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
 
   
      
    
    
    
     private void calculaPagos(){
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

                                if(cboForPag.getSelectedIndex()!=-1){
                                    
                                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");                                        

                                    //*************************************************************************************///
                                   
                                      java.sql.Connection  conn = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                                      if (conn!=null){
                                          
                                          dblRetFueFle = getSubtotalTrans();
                                        
                                         
                                       //   System.out.println(">>> "+ dblRetFueFle );
                                        if(!(strCodTipPerCli==null)){  
                                          if(!(strCodTipPerCli.trim().equals("1"))){     
                                            if(dblRetFueFle>0){
                                                ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());    
                                                intFilUltPag = tblCotForPag.getRowCount()-1;
                                                tblCotForPag.setValueAt("",intFilUltPag , 1);
                                                tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
                                                tblCotForPag.setValueAt("1.000", intFilUltPag, 3);
                                                dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
                                                    // System.out.println(">>> "+ dblRetFueFle );
                                                tblCotForPag.setValueAt( new Double(dblRetFueFle) , intFilUltPag, 4);
                                              }} else { dblRetFueFle=0;  }
                                         }else { dblRetFueFle=0; }
                                          
                                          
                                                
                                                
                                          
//                                          String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
//                                          strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
//                                          strSQL += " where polret.co_emp = "+ objParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli;

                                           
                                          String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
                                          strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
                                          strSQL += " where polret.co_emp = "+ objParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
                                          "  AND polret.st_reg='A'   AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";


                                          stm = conn.createStatement();
                                          rst = stm.executeQuery(strSQL);
                                           while (rst.next()){
                                                
                                               ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());    
                                                intFilUltPag = tblCotForPag.getRowCount()-1;
                                                tblCotForPag.setValueAt("",intFilUltPag , 1);
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
                                    //  System.out.println(">>> "+ dblRete );
                                     dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);
                                   
                                       //   System.out.println(">>> "+ dblBaseDePagos );
                                          
                                   //  dblBaseDePagos = objUti.redondear( ( dblTotalCot - ( dblRetFueFle + dblRetFue + dblRetIva)),intNumDec);
                                    
                                for(i=0; i < intVal; i++) {
                                           Calendar objFecPagActual = Calendar.getInstance();
                                           objFecPagActual.setTime(objFec.getTime()); 
                        
                                           int diaCre = intarreglodia[i]; 
                                           int numPag = intarreglonum[i];
                                           String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);
                                           
                                            if (diaCre!=0)
                                               objFecPagActual.add(Calendar.DATE, diaCre);

                                            dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
                                            dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH)+1);
                                            dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
                                              
                                            ((DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());    
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









/***********************************************************************************************/

    double dblValRet=0;
    String strFecCor="";
    for(int x=0; x<tblCotForPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblCotForPag.getValueAt(x, 3)) );
      if(dblValRet==0.00){
          strFecCor= tblCotForPag.getValueAt(x, 2).toString();
          break;
      }
    }




     String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
     java.util.Date  fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
     int intAnioAct= (fac1.getYear()+1900);

     //  por alfredo.  año nuevo 31 dic año anterior
     String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
     java.util.Date  fac = objUti.parseDate(strF, "yyyy/MM/dd");
     int intAnioCre= (fac.getYear()+1900);


     if(intAnioCre > intAnioAct ){
         strFecCor="31/12/"+intAnioAct;
     }




    for(int x=0; x<tblCotForPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblCotForPag.getValueAt(x, 3)) );
      if(dblValRet > 0.00 )
        tblCotForPag.setValueAt(strFecCor,  x, 2);

    }

/***********************************************************************************************/






                                    //*************************************************************************************///
                                    //*************************************************************************************///
                 } }
               }
               catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
               catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
     }
    
    
   
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
                 "select tx_codAlt,tx_nomItm,nd_stkAct,nd_preVta1,st_ivaVen,co_itm from tbm_inv where co_emp = "+ objParSis.getCodigoEmpresa(), "", 
                 objParSis.getStringConexion(), 
                 objParSis.getUsuarioBaseDatos(), 
                 objParSis.getClaveBaseDatos()
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
    private javax.swing.JButton burRee;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVenCon;
    private javax.swing.JComboBox cboForPag;
    private javax.swing.JComboBox cboMotivoTransporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblCotNumDes;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblFac;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblGuia;
    private javax.swing.JLabel lblGuia1;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPartida;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTot;
    private javax.swing.JLabel lblTransporte;
    private javax.swing.JLabel lblfecha;
    private javax.swing.JPanel pan2;
    private javax.swing.JPanel panAsiento;
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
    private javax.swing.JPanel panNorOtros;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabCotCom;
    private javax.swing.JTable tblCotForPag;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDirCli;
    private javax.swing.JTextField txtGuia;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtNumCot;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumFac;
    private javax.swing.JTextField txtOD;
    private javax.swing.JTextField txtPartida;
    private javax.swing.JTextField txtPed;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtTransporte;
    // End of variables declaration//GEN-END:variables
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    
    private javax.swing.JTextField txtTipDocCod =  new javax.swing.JTextField();
    private javax.swing.JTextField txtCod =  new javax.swing.JTextField();
    private javax.swing.JTextField txtDes =  new javax.swing.JTextField();
    private javax.swing.JTextField txtVehRet =  new javax.swing.JTextField();
    private javax.swing.JTextField txtChoRet =  new javax.swing.JTextField();
    
    



    
             
private void retNomEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_nom, tx_dir from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    GLO_strnomEmp=rstTipDoc.getString("tx_nom");
                    GLO_strdirEmp=rstTipDoc.getString("tx_dir");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
    }


    
    
    private class ZafMouMotAda extends MouseMotionAdapter
    {
        @Override
        public void mouseMoved(MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {                   
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
    private class ZafTblModLis implements TableModelListener
    {
        @Override
        public void tableChanged(TableModelEvent e)
        {
            switch (e.getType())
            {
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
    
    
    
    
    
    
    public class ZafAutPrg extends Librerias.ZafAut.ZafAut
    {
        public ZafAutPrg(javax.swing.JInternalFrame ifr)
        {
            super(ifr,objParSis);
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
    private boolean isPreVtaMenorPreUni(){
        boolean blnRes = true;
    
       //*** boolean  blnValPre=true;
       
        double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = getDesCli(txtCodCli.getText()), 
               dblMinimo, dblValorVenta, dblCostoItm, dblPorGanancia = getMarUti(txtCodCli.getText());
        int intCoItm;
        
           //**********************     
          
         
            
        for(int i = 0 ; i < tblDat.getRowCount();i++){ 
         if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
            dblPreUsr    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString()))),6);
            dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
            
            dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);                       
            dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
        
            if(dblPreUniItm!=0){
                dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
            }else{
               
                 double dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                   if(dblStkAct != 0)
                       dblCostoItm  =   getCosUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                     else{
                          // if(tblDat.getValueAt(i,INT_TBL_PRE_COS) == null) dblCostoItm=0.00;
                          //   else   dblCostoItm = Double.parseDouble(tblDat.getValueAt(i,INT_TBL_PRE_COS).toString());
                         dblCostoItm=0.00;
                          }
                dblMinimo    =   dblCostoItm * dblPorGanancia;
                dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
            }
             
            dblValorVenta=objUti.redondear(dblValorVenta,2);
            dblMinimo=objUti.redondear(dblMinimo,2);
             
            if(dblValorVenta<dblMinimo){
              ///  System.out.println("VALIDAD PRECIOS.............");
                blnRes = false;
                tblDat.setValueAt(true,i,INT_TBL_BLNPRE);                
            }
        } }
    //  }
        return blnRes;
    }
    

    
     private double getMarUti(String int_co_cli){
       double dblMarUti = 0;
       try{
             java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
              if (conUni!=null){
                   java.sql.Statement stmUni = conUni.createStatement();
                   String sSQL= "SELECT nd_maruti from tbm_cli as cli " +
                                    " where cli.co_emp = " + objParSis.getCodigoEmpresa() +  " and " +
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
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL= "SELECT nd_maxdes from tbm_cli as cli " +
                                    " where cli.co_emp = " + objParSis.getCodigoEmpresa() +  " and " +
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
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL = "  SELECT a1.nd_cosUni FROM tbm_inv AS a1"+
                               " INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                               " WHERE a2.co_emp="+ objParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
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
    private boolean isDesForVen(){
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
     * CONTROL: Que el client no tenga documentos vencidos.
     */
    private boolean isDocVen(){
       boolean blnisDocVen = true;
        try{
                java.sql.Connection conMoncre=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conMoncre!=null){
                    java.sql.Statement stmMonCre = conMoncre.createStatement();
                   if(isVtaCon()) {
                   
                       String sSQL = "SELECT a2.nd_abo  FROM tbm_cabMovInv AS a1 "+
                                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc "+
                                    " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "+
                                    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND "+
                                    " a1.co_tipDoc=a3.co_tipDoc) WHERE a1.co_emp="+ objParSis.getCodigoEmpresa() +" AND a1.co_cli=" + txtCodCli.getText()+
                                    " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0 AND a2.fe_ven<=CURRENT_TIMESTAMP"+
                                    " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                       java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                       if(rstMonCre.next())
                            blnisDocVen = false;
                       }else{
                           String sSQL = "SELECT a2.nd_abo  FROM tbm_cabMovInv AS a1 "+
                                    " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc "+
                                    " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "+
                                    " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND "+
                                    " a1.co_tipDoc=a3.co_tipDoc) WHERE a1.co_emp="+ objParSis.getCodigoEmpresa() +" AND a1.co_cli=" + txtCodCli.getText()+
                                    " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0 AND a2.fe_ven<=CURRENT_TIMESTAMP"+
                                    " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                        java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                        if(rstMonCre.next())
                            blnisDocVen = false;
                        rstMonCre.close();
                        rstMonCre = null;
                   }
                   stmMonCre.close();
                   conMoncre.close();
                   stmMonCre = null;
                   conMoncre = null;
                }
       }
       catch(java.sql.SQLException Evt) {  blnisDocVen = false;  }
       catch(Exception Evt) { blnisDocVen = false;  }                  
      
       //// System.out.println("VALIDAD DOCU VEN............."+ blnisDocVen);
        
       return blnisDocVen ;
     }    
    
    /**
     *  CONTROL: Que el cliente no sobrepase su cupo de credito establecido
     */
    private boolean isCreCli(){
        boolean blnCrecli=true;
        //Obteniendo el cupo de credito para este cliente
        double dblMonCre = getMonCre(txtCodCli.getText());
        //Obteniendo el las cuentas por cobrar a este cliente.
        double dblCxC = getCxC(txtCodCli.getText());
        if(Math.abs(dblTotalCot+dblCxC) > dblMonCre)
            blnCrecli=false;
        
      ///   System.out.println("VALIDAD DOCU VEN............."+ blnCrecli );
        return blnCrecli;
    }
    
    /**
     * Metdo que devuelve el total de las cuentas por cobrar de un cliente
     */
    private double getCxC(String int_co_cli){
       double dblCxC = 0;
       try{
                java.sql.Connection conMoncre=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conMoncre!=null){
                    java.sql.Statement stmMonCre = conMoncre.createStatement();
                String sSQL ="select  sum((abs(pag.mo_pag) - pag.nd_abo)) as nd_deuda           " +
                             "from tbm_pagmovinv as pag, tbm_cabmovinv as cab, tbm_cabtipdoc as tipdoc " +
                             "where                                                                    " +
                             " pag.co_emp =   "  + objParSis.getCodigoEmpresa() +   "   and   "  +  
                             " pag.co_emp = cab.co_emp and      " +
                             " pag.co_emp = tipdoc.co_emp and   " +
                             " pag.co_loc =   "  + objParSis.getCodigoLocal()   +   "   and   " +
                             " pag.co_loc = cab.co_loc                              and   " +
                             " pag.co_loc = tipdoc.co_loc                           and   " + 
                             " pag.co_tipDoc = cab.co_tipdoc                        and   " +
                             " pag.co_tipDoc = tipdoc.co_tipdoc                     and   " +
                             " pag.co_doc     = cab.co_doc                          and   " +
                             " tipdoc.co_tipdoc  = " + 1                        + " and " +
                             " (abs(pag.mo_pag) - pag.nd_abo) > 0                   and   " + 
                             " cab.st_reg in ('C','A','R','F')                      and   " + 
                             " pag.st_reg in ('C','A')                              and   " +
                             " cab.co_cli = " + int_co_cli  ;                      

                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                  if(rstMonCre.next()){
                        dblCxC = rstMonCre.getDouble("nd_deuda");
                    }

                    rstMonCre.close();
                    stmMonCre.close();
                    conMoncre.close();
                    rstMonCre = null;
                    stmMonCre = null;
                    conMoncre = null;
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
                java.sql.Connection conMoncre=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if (conMoncre!=null){
                    java.sql.Statement stmMonCre = conMoncre.createStatement();

                    String sSQL= "SELECT nd_monCre from tbm_cli as cli " +
                                    " where cli.co_emp = " + objParSis.getCodigoEmpresa() +  " and " +
                                    "       cli.co_cli = " + int_co_cli ; 
                    java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next()){
                        dblMonCre = rstMonCre.getDouble("nd_monCre");
                    }
                    rstMonCre.close();
                    stmMonCre.close();
                    conMoncre.close();
                    rstMonCre = null;
                    stmMonCre = null;
                    conMoncre = null;
                }
       }
       catch(java.sql.SQLException Evt) {  return dblMonCre; }
       catch(Exception Evt) { return dblMonCre; }                  
       return dblMonCre;
     }    
   }    
    

    
    
    /**
     * EFLORESA - SE MODIFICA VALIDACION PARA CASOS EN QUE COBRANZAS SOLICITA LA MODIFICACION DE COBRO EN EFECTIVO PARA ELIMINAR REGISTROS Y LUEGO SE INTENTA HACER REEMPLAZO DE FACTURA.
     */
    private boolean isPagoDocumento_aso(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
         boolean blnRes = false;
         Connection con=conn;
         try{                   
             try{
                 if (con!=null){
                     /*String strSQL = " select a.nd_abo from tbm_detpag as a" +
                     " inner join tbm_cabpag as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)" +
                     " where a.co_emp=" + intCodEmp + " and a.co_locpag=" + intCodLoc + " and a.co_docpag="+ intCodDoc+" and a.co_tipdocpag="+ intCodTipDoc +" " +
                     " and b.st_reg not in ('I','E')";*/

                     String strSQL = " select a.nd_abo from tbm_detpag as a " +
                     " inner join tbm_cabpag as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) " +
                     " where a.co_emp=" + intCodEmp + " and a.co_locpag=" + intCodLoc + " and a.co_docpag="+ intCodDoc+" and a.co_tipdocpag="+ intCodTipDoc +" " +
                     " and b.st_reg not in ('I','E') " + 
                     " and a.st_reg not in ('I','E') ";                         

                     Statement stm = con.createStatement();
                     ResultSet rst = stm.executeQuery(strSQL);

                   if (rst.next()){
                            MensajeInf("<html>Este documento <FONT COLOR=\"blue\"> tiene Pagos aplicados </FONT> </html>");
                            blnRes = true;
                     }
                     rst.close();
                     stm.close();
                 }
             }catch(SQLException e){
                 blnRes = true;
                 objUti.mostrarMsgErr_F1(new JInternalFrame(),e);                    
             }
         }catch(Exception e){
             blnRes = true;
             objUti.mostrarMsgErr_F1(new JInternalFrame(),e);
         }
         return blnRes; 
    } 

              
    private boolean verificaItmUni(){
        boolean  blnRes=true;
        try{
        for(int i=0; i<tblDat.getRowCount();i++){
           if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                  int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                  int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));
                      if(intCodItm != intCodItmAct){
                          MensajeInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                          blnRes=false;
                          break;
                      }
          }}
        }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
        return blnRes;     
    }

    private void realizaCanFac(){
     Ventas.ZafVen24.ZafVen24_02 obj = new  Ventas.ZafVen24.ZafVen24_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
     obj.show();  
     if(obj.acepta()){ 
      if(obj.GetCamSel()==1){
        objTooBar.anular(); 
      }if(obj.GetCamSel()==2){
        if(strAux.equals("Anulado")){
            MensajeInf("El documento ya está ANULADO.\nNo es posible realizar N/C a un documento anulado.");
        }else{
             intRegSelLoc=obj.GetCamSel2();
             if(!GenerarDevolucion(intRegSelLoc))
               MensajeInf("ERROR NO SE PUDO REALIZAR EL REEMPLAZO DE FACTURA.");
      }}}  
     obj.dispose();
     obj=null;         
    }

    private boolean verificaSolAplFac(){
 boolean blnRes=true;
 java.sql.Connection conAnu;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";

 try{
  conAnu=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
  if(conAnu!=null){
    stmLoc=conAnu.createStatement();

     strSql="select  st_aut  from tbm_cabsoldevven where co_emp="+objParSis.getCodigoEmpresa()+" " +
     " and co_locrel="+objParSis.getCodigoLocal()+" and co_tipdocrel="+txtTipDocCod.getText()+" " +
     " and co_docrel= "+txtNumDoc.getText()+"  and st_reg not in ('E','I')  and st_aut in ('P','A') ";
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        blnRes=false;
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
     conAnu.close();
     conAnu=null;

}}catch(java.sql.SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);   }
  catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  return blnRes;
}

    private boolean GenerarDevolucion(int intCodLocDev){
        boolean blnRes=false;  
        java.sql.Connection conAnu;
        java.sql.Statement stmLoc;
        java.sql.Statement stmLoc01;
        java.sql.ResultSet rst;
        String strSql="";
        try{ 
         conAnu=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
         if(conAnu!=null){
         conAnu.setAutoCommit(false);
         stmLoc01=conAnu.createStatement();
         //*******************************************************************************************************
          if(!Abrir_Conexion_Remota())
           return false;          
         //*******************************************************************************************************

          strSql="SELECT co_doc FROM tbr_cabmovinv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND " +
          " co_locrel="+objParSis.getCodigoLocal()+" " +
          " AND co_tipdocrel="+txtTipDocCod.getText()+" AND co_docrel="+txtNumDoc.getText()+" AND st_reg not in('I','E')";

          if(INTCODREGCEN!=0) {
           stmLoc = con_remota.createStatement();
           rst = stmLoc.executeQuery(strSql);
           if(rst.next()){
            MensajeInf("LAS FACTURA YA TIENE NOTA DE CREDITO NO ES POSIBLE REALIZAR EL REEMPLAZO. "); 
            return false;                    
          }}else{
           stmLoc = conAnu.createStatement();
           rst = stmLoc.executeQuery(strSql);
           if(rst.next()){
             MensajeInf("LAS FACTURA YA TIENE NOTA DE CREDITO NO ES POSIBLE REALIZAR EL REEMPLAZO. "); 
             return false;                    
          }}

         if(INTCODREGCEN!=0) {
           if(isPagoDocumento_aso(con_remota, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal() ,Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtNumDoc.getText())))
             return false;
         }else{
          if(isPagoDocumento_aso(conAnu, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal() ,Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtNumDoc.getText())))
           return false;
         }

        String strMsg2 = "¿Desea realizar de forma automatica el cruce de la devolucion con la factura ?";
        //JOptionPane oppMsg2=new JOptionPane();
        //String strTit2="Mensaje del sistema Zafiro";
        int intRegSel=0;
        if(!(JOptionPane.showConfirmDialog(this,strMsg2,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 1 ))
         intRegSel=1;  // SI
        else
         intRegSel=2;  // NO

        Ventas.ZafVen24.ZafVen24_03 objDev;
        if(INTCODREGCEN!=0) 
          objDev = new  Ventas.ZafVen24.ZafVen24_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, txtNumDoc.getText() , txtTipDocCod.getText() ,  objParSis.getCodigoLocal(), intCodLocDev  , con_remota, intRegSel );
        else 
          objDev = new  Ventas.ZafVen24.ZafVen24_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, txtNumDoc.getText() , txtTipDocCod.getText() ,  objParSis.getCodigoLocal() , intCodLocDev  , conAnu , intRegSel );

        objDev.show();  

        if(objDev.acepta()){ 
         strCodDocReeDev = objDev.txtCodDoc();
         strNumDocReeDev = objDev.txtNumDoc();

         stbDocRelEmpRem=objDev.stbDocDevEmp;


         objDev.dispose();
         objDev=null;


         if(objTooBar.RealizaCambioFactura(conAnu, con_remota, txtTipDocCod.getText(), txtNumDoc.getText() )) { 
            if(INTCODREGCEN!=0){
                con_remota.commit();
                con_remota.setAutoCommit(true);
            }
            conAnu.commit();
            conAnu.setAutoCommit(true);


            if(INTCODREGCEN!=0){
               asignaSecEmpGrp( con_remota, con_remota, stbDocRelEmpRem);
               asignaSecEmpGrp( conAnu, con_remota, stbDocRelEmpLoc);
            }
            else{
              asignaSecEmpGrp( conAnu, conAnu, stbDocRelEmpRem);
              asignaSecEmpGrp( conAnu, conAnu, stbDocRelEmpLoc);
            }


            blnRes=true; 
           if(!strNumDocReeDev.equals("")) 
             MensajeInf("SE GENERO LA DEVOLUCION No "+strNumDocReeDev+ "  "); 
             MensajeInf("LA FACTURA No "+txtNumFac.getText()+ " SE HA REEMPLAZADO POR LA FACTURA  "+strNumFacRee+"  "); 



         }else{ 
             if(INTCODREGCEN!=0) con_remota.rollback();
             conAnu.rollback(); 
           }


       }else{
         if(INTCODREGCEN!=0) con_remota.rollback();
          conAnu.rollback();    
         objDev.dispose();
         objDev=null;
         blnRes=false;
       }

       stmLoc01.close();
       stmLoc01=null;
       if(INTCODREGCEN!=0){
         con_remota.close();
         con_remota=null;
       }
       conAnu.close();
       conAnu=null;

       }}catch(java.sql.SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);   }            
         catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
        return blnRes;            
}

    private boolean asignaSecEmpGrp(java.sql.Connection connLoc, java.sql.Connection connRem,  StringBuffer stbDocRelSec ){
        boolean blnRes=false;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc, stmLoc01;
        String strSql="";
        int intSecEmp=0,intSecGrp=0;
        try{
         if(connRem!=null){
           stmLoc=connLoc.createStatement();
           stmLoc01=connLoc.createStatement();
           strSql="SELECT * FROM( "+stbDocRelSec.toString()+" ) AS x";
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

              intSecEmp=objUltDocPrintUtil.getNumSecDoc(connRem, objParSis.getCodigoEmpresa()  ); 
              intSecGrp=objUltDocPrintUtil.getNumSecDoc(connRem, objParSis.getCodigoEmpresaGrupo() ); 

               strSql="UPDATE tbm_cabmovinv SET ne_SecEmp="+intSecEmp+", ne_SecGrp="+intSecGrp+" WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" " +
               " AND co_tipdoc="+rstLoc.getInt("cotipdoc")+" AND  co_doc="+rstLoc.getInt("codoc")+"";
               stmLoc01.executeUpdate(strSql);
           }
           rstLoc.close();
           rstLoc=null;

          stmLoc.close();
          stmLoc=null;
          stmLoc01.close();
          stmLoc01=null;

        }}
        catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        return blnRes;
    }

    private boolean GeneraFactura(java.sql.Connection conn, String strNumDev){
        boolean blnRes=false;
        try{
          if(objTooBar.RealizaCambioFactura(conn, con_remota, txtTipDocCod.getText(), txtNumDoc.getText() )) { 
           if(!strNumDev.equals("")) 
             MensajeInf("SE GENERO LA DEVOLUCION No "+strNumDev+ "  "); 

            MensajeInf("LA FACTURA No "+txtNumFac.getText()+ " SE HA REEMPLAZADO POR LA FACTURA  "+strNumFacRee+"  "); 
            blnRes=true;
           }else{ 
             if(INTCODREGCEN!=0) con_remota.rollback();
             conn.rollback(); 
           }
        }
        catch(Exception e) { objUti.mostrarMsgErr_F1(this, e); }                
        return blnRes;
    }


    @Override
    protected void finalize() throws Throwable { 
        //System.gc(); 
        Runtime.getRuntime().gc();
        super.finalize();
   ///    System.out.println ("Se libera Objeto...");

   }
    
    
       /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        boolean blnRes=false;
        String strRutRpt, strNomRpt;
        int intNumTotRpt;
        int intCodEmp;
        String strNomEmp, strDirEmp, fecha, strAuxNomEmp;
        java.util.Date dtFecHor=new java.util.Date();
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (int i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            default:
                                strNomRpt=objRptSis.getNombreReporte(i);
                                fecha=objUti.formatearFecha(dtFecHor,"dd-MM-yyyy-hh-mm-ss");
                                retNomEmp(intCodEmp);
                                strNomEmp= GLO_strnomEmp;
                                strDirEmp = GLO_strdirEmp;
                                strAuxNomEmp = GLO_strnomEmp.substring(0,3);
                                
                                if(strAuxNomEmp.equals("COS")){ /* Cuando es Cosenco. */
                                    strRutRpt="C://Zafiro//Reportes_impresos//cosenco//RptFacVta.jrxml";
                                }
                                else{ /* Otras empresas. */
                                    switch(intCodEmp){ 
                                        case 1:  strRutRpt="C://Zafiro//Reportes_impresos//tuval//RptFacVta.jrxml";  break;
                                        case 2:  strRutRpt="C://Zafiro//Reportes_impresos//castek//RptFacVta.jrxml";  break;
                                        case 4:  strRutRpt="C://Zafiro//Reportes_impresos//dimulti//RptFacVta.jrxml";  break;                                        
                                        default:  
                                            //strRutRpt=objRptSis.getRutaReporte(i);   
                                            break;
                                    }                                
                                }
                                
                                //--strAuxNomEmp
                                strRutRpt=objRptSis.getRutaReporte(i);
                                
                                //Inicializar los parametros que se van a pasar al reporte.
                                Map mapPar = new HashMap();
                                mapPar.put("codEmp", ""+intCodEmp);
                                mapPar.put("codLoc", ""+objParSis.getCodigoLocal());
                                mapPar.put("codTipDoc", txtTipDocCod.getText());
                                mapPar.put("codDoc", txtNumDoc.getText());
                                mapPar.put("dirEmp", strDirEmp);
                                mapPar.put("nomEmp", strNomEmp);
                                mapPar.put("fechaHor", fecha);
                                mapPar.put("obs1", txaObs1.getText());
                                mapPar.put("subtot",new Double(txtSub.getText()));
                                mapPar.put("iva", new Double(txtIva.getText()));
                                mapPar.put("total", new Double(txtTot.getText()));

                                if (objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt)) 
                                {
                                    blnRes = true;
                                    System.out.println("Se genero reporte de Reemplazo de Factura.");
                                }
                                break;
                        }
                    }
                }
            } 
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
}