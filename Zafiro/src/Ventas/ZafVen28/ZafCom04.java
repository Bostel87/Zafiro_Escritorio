/*
 * 
 * 
 * Created on 6 de septiembre de 2004, 15:54 
 */ 
               
package Ventas.ZafVen28;   
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.*;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
    
/*
 * @author javier ayapata 
 * 
 */ 
public class ZafCom04 extends javax.swing.JDialog
{
     //Constantes: Jtable   
    private static final int INT_TBL_LINEA    = 0 ; 
    private static final int INT_TBL_ITMALT   = 1 ;
    private static final int INT_TBL_BUTITM   = 2 ;
    private static final int INT_TBL_DESITM   = 3 ;
    private static final int INT_TBL_UNIDAD   = 4 ;
    private static final int INT_TBL_CODBOD   = 5 ;    
    private static final int INT_TBL_BUTBOD   = 6 ;
    private static final int INT_TBL_CANCOM   = 7;
    private static final int INT_TBL_CANMOV   = 8 ;            //Cantidad del movimiento (venta o compra)
    private static final int INT_TBL_PREVEN   = 9 ;            //Precio de Venta
    private static final int INT_TBL_PORDES   = 10 ;           //Porcentaje de descuento
    private static final int INT_TBL_BLNIVA   = 11;            //Boolean Iva
    private static final int INT_TBL_TOTAL    = 12;            //Total de la venta o compra del producto
    private static final int INT_TBL_CODITM   = 13;
    private static final int INT_TBL_ITMORI   = 14;            //Codigo del item de una o/c cargada
    private static final int INT_TBL_BODORI   = 15;            //Codigo del bodega de una o/c cargada
    private static final int INT_TBL_CANORI   = 16;            //Codigo del bodega de una o/c cargada
    private static final int INT_TBL_CANDEV   = 17;            //Codigo del bodega de una o/c cargada
    private static final int INT_TBL_ESTADO   = 18;
    private static final int INT_TBL_IVATXT   = 19;
    private static final int INT_TBL_ITMALT2  = 20 ;
    private static final int INT_TBL_ITMSER   = 21;            //Columna que contiene SI el item es de servicio S o N
    private static final int INT_TBL_CODLINREL   = 22;
    
    //Constantes del ArrayList Elementos Eliminados  
    final int INT_ARR_CODITM   = 0;
    final int INT_ARR_CODBOD   = 1;
    final int INT_ARR_CANMOV   = 2;
    final int INT_ARR_ITMORI   = 3;
    final int INT_ARR_BODORI   = 4;
    final int INT_ARR_CANORI   = 5;
    
    //Manejo de Stock
    final int INT_ARL_STK_INV_STK_DIS=10;  // nd_stkDisp
    final int INT_ARL_STK_INV_STK=0;  // nd_stk
    
    //Variables
    private ZafUtil objUti;
    private ZafInvItm objInvItm;  
    private ZafParSis objParSis;
    private ZafCiudad_dat objCiudad_dat;                     // Para Obtener la informacion de una ciudad
    private UltDocPrint objUltDocPrint;                      // Para trabajar con la informacion de tipo de documento
    private ZafCtaCtb objCtaCtb;                             // Para obtener  los codigos y nombres de ctas ctbles
    private ZafTipDoc objTipDoc;
    private ZafAsiDia objDiario;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;                //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;                //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafTipItm objZafTipItm;                        // Para saber si un producto es o no Servicio 
    private ZafVenCon objVenConTipdoc; 
    private java.util.Date datFecAux;
    private boolean blnChangeData=false;
    private String strBeforeValue,strAfterValue;
    
    String fileName="C://Zafiro//Reportes_impresos//RptDevCom.jasper";
    
    LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    JDialog jfrThis; //Hace referencia a this
    Connection conCab, CONN_GLO=null;  //Coneccion a la base donde se encuentra la cotizacion
    Statement stmCab,stm;   //Statement para la cotizacion 
    Statement stmCabDet;   //Statement para la cotizacion 
    ResultSet rstCab;//Resultset que tendra los datos de la cabecera
    ResultSet rstDocDet;//Resultset que tendra los datos del Detalle
    String sSQL, strFiltro,strAux;//EL filtro de la Consulta actual
    String strFecSis;
    double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    int intNumDec; //Numero de decimales a presentar
    boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento     
    Vector vecTipDoc,vecDetDiario; //Vector que contiene el codigo de tipos de documentos    

     
    private String  STR_ESTREG="", stIvaCom="S";
    private String strCodTipDoDevDen="";
       
    final ArrayList arreBod=new ArrayList();
     
    int intCodEmp;
    int intCodLoc;
    int intTipDoc;  
    int intCodDoc; 
    int intCodMnu; 
    int intCodDocDevVen;
    int intRegSel=0;
    int intSecuencialFac=0;
    int intNunDocCom=0;
    
    StringBuffer STRINS=null;
    
    public boolean blnEst=false;

    JTextField txtIdePrv = new JTextField();
    JTextField txtTelPrv = new JTextField();
    JTextField txtCodCiuPrv = new JTextField();
    
    /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
    private JTextField txtSubTotIvaGra=new JTextField();
    private JTextField txtSubTotIvaCero=new JTextField();
    private double dblPorComSol=0.0d;
    /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
    
    
    private static final String VERSION = " v2.71";

    /** Creates new form zafCotCom */
    public ZafCom04(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCodDocOC, int intNummmOrc ,int intTipDocOC , int intCodEmpOC,  int intCodLocOC, java.sql.Connection con, StringBuffer stbins , int intCodDocDevVenD, String strCodTipDoDevDenD )
    { 
        super(parent, modal);
        try
        {
            intCodEmp = intCodEmpOC;
            intCodLoc = intCodLocOC;
            intTipDoc = intTipDocOC;
            intCodDoc = intCodDocOC;
            intCodMnu = intNummmOrc; 
            
            CONN_GLO = con; 
            STRINS = stbins; 
            intCodDocDevVen = intCodDocDevVenD;
            strCodTipDoDevDen=strCodTipDoDevDenD;
              
            this.objParSis = (Librerias.ZafParSis.ZafParSis)obj.clone();
            jfrThis = this;
            initComponents();
            objUti = new ZafUtil();
            this.setTitle(objParSis.getNombreMenu()+VERSION);        
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objParSis);
            
            cargaTipoDocPredeterminado();
            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ), "DevCom");
            
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panCotGenNor.add(txtFecDoc);
            txtFecDoc.setBounds(520, 5, 92, 20);

            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            java.util.Date dateObj =datFecAux;
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
            calObj.get(java.util.Calendar.MONTH)+1     ,
            calObj.get(java.util.Calendar.YEAR)        );

            intNumDec = objParSis.getDecimalesMostrar();
            txtCodTipDoc.setVisible(false);
            txtNomTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDetTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            objZafTipItm     = new Librerias.ZafUtil.ZafTipItm(objParSis); 

            //Nombres de los tabs
            tabDevCom.setTitleAt(0,"General");
            tabDevCom.addTab("Asiento Diario",objDiario);    
            // objDiario.setEditable(false);
            // objTooBar = new mitoolbar(this);        
            getIva();
            // this.getContentPane().add(objTooBar,"South");
            pack();

            vecTipDoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
            String sqlPrede = " Select distinct cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar "+
                              " from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)" +
                              " where   docprg.co_emp = " + intCodEmp + " and " +
                              " docprg.co_loc = " + intCodLoc   + " and  docprg.co_mnu = 45 and  docprg.st_reg='S'";
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                    objParSis.getClaveBaseDatos(), sqlPrede, cboTipDoc, vecTipDoc);      

            cboTipDoc.setSelectedIndex(0);

            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));

            CargarBodegas();
            //  objUti.desactivarCom(this);
            // Configura_ventana_consulta();
             Configurar_tabla();
           
            //*********CargarDatosParaDevolucion(intCodEmp, intCodLoc, intTipDoc, intCodDoc);
            this.setBounds(10,10, 700,450);
            
            objParSis.setCodigoEmpresa(intCodEmp);
            objParSis.setCodigoLocal(intCodLoc);
        }
        catch (CloneNotSupportedException e){       objUti.mostrarMsgErr_F1(this, e);      }         
    }

    
      
    /**
     * ZafVen28 => 1 [Genera Devolucion de Compra]
     */
    public ZafCom04(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCodDocOC, int intNummmOrc ,int intTipDocOC , int intCodEmpOC,  int intCodLocOC, java.sql.Connection con, StringBuffer stbins , int intCodDocDevVenD, String strCodTipDoDevDenD , String strval , int intTipCruOcr  )
    { 
        super(parent, modal);
        try
        {
            intCodEmp = intCodEmpOC;
            intCodLoc = intCodLocOC;
            intTipDoc = intTipDocOC;
            intCodDoc = intCodDocOC;
            intCodMnu = intNummmOrc; 
            
            intRegSel = intTipCruOcr;
            CONN_GLO = con;
            STRINS = stbins; 
            intCodDocDevVen = intCodDocDevVenD;
            strCodTipDoDevDen=strCodTipDoDevDenD;
           
            this.objParSis = (Librerias.ZafParSis.ZafParSis)obj.clone();
            jfrThis = this;
            initComponents();
            objUti = new ZafUtil();
        
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
   
            this.setTitle(objParSis.getNombreMenu()+VERSION);        
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objParSis);
            
            cargaTipoDocPredeterminado();
            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ), "DevCom");
            
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panCotGenNor.add(txtFecDoc);
            txtFecDoc.setBounds(520, 5, 92, 20);
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            java.util.Date dateObj =datFecAux;
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),calObj.get(java.util.Calendar.MONTH)+1   , calObj.get(java.util.Calendar.YEAR)        );

            intNumDec = objParSis.getDecimalesMostrar();
            txtCodTipDoc.setVisible(false);
            txtNomTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDetTipDoc.setBackground(objParSis.getColorCamposObligatorios());
             
            objZafTipItm     = new Librerias.ZafUtil.ZafTipItm(objParSis); 
        
            //Nombres de los tabs
            tabDevCom.setTitleAt(0,"General");
            tabDevCom.addTab("Asiento Diario",objDiario);    
            getIva();
            pack();
        
            vecTipDoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
            String sqlPrede=" Select distinct cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)" +
                            " where docprg.co_emp = " + intCodEmp + "  " +
                            " and docprg.co_loc = " + intCodLoc   + " and  docprg.co_mnu = 45 and  docprg.st_reg='S' ";
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                    objParSis.getClaveBaseDatos(), sqlPrede, cboTipDoc, vecTipDoc);      
        
            cboTipDoc.setSelectedIndex(0);
        
            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
            Calendar calFecha=Calendar.getInstance();
            dblPorComSol=(objParSis.getPorcentajeCmpSolVentas(intCodEmp, intCodLoc, calFecha.getTime())!=null?objParSis.getPorcentajeCmpSolVentas(intCodEmp, intCodLoc, calFecha.getTime()):0).doubleValue();
            CargarBodegas();
            Configurar_tabla();
            CargarDatosParaDevolucion(intCodEmp, intCodLoc, intTipDoc, intCodDoc);

            
            

            
         
            this.setBounds(10,10, 700,450);
            
            System.out.println("intCodEmpOC: "+intCodEmpOC+" intCodLoc: "+intCodLoc);
            objParSis.setCodigoEmpresa(intCodEmp);
            objParSis.setCodigoLocal(intCodLoc);
            
            
//            this.insertar();
            
            System.out.println("ZafCom04.CodigoEmpresa.seteado: "+objParSis.getCodigoEmpresa());
            System.out.println("ZafCom04.CodigoLocal.seteado: "+objParSis.getCodigoLocal());
            
        }
        catch (CloneNotSupportedException e)
        {
          objUti.mostrarMsgErr_F1(this, e);
        }
    }

    
    /**
     * ZafVen28 => 2 [Modificacion Devolucion de Compra]
     */
    public ZafCom04(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCodDocOC, int intNummmOrc ,int intTipDocOC , int intCodEmpOC, int intCodLocOC, java.sql.Connection con, StringBuffer stbins , int intCodDocDevVenD, String strCodTipDoDevDenD , int intval  )
    { 
        super(parent, modal); 
        try
        {
            intCodEmp = intCodEmpOC;
            intCodLoc = intCodLocOC;
            intTipDoc = intTipDocOC;
            intCodDoc = intCodDocOC;
            intCodMnu = intNummmOrc; 

            CONN_GLO = con;
            STRINS = stbins; 
            intCodDocDevVen = intCodDocDevVenD;
            strCodTipDoDevDen=strCodTipDoDevDenD;

            this.objParSis = (Librerias.ZafParSis.ZafParSis)obj.clone();
            jfrThis = this;
            initComponents();
            objUti = new ZafUtil();
            this.setTitle(objParSis.getNombreMenu()+VERSION);        
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objParSis);
            cargaTipoDocPredeterminado(); 
            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ), "DevCom");

            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panCotGenNor.add(txtFecDoc);
            txtFecDoc.setBounds(520, 5, 92, 20);

            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            java.util.Date dateObj =datFecAux;
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                 calObj.get(java.util.Calendar.MONTH)+1     ,
                                 calObj.get(java.util.Calendar.YEAR)        );

            intNumDec = objParSis.getDecimalesMostrar();
            txtCodTipDoc.setVisible(false);
            txtNomTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDetTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            objZafTipItm     = new Librerias.ZafUtil.ZafTipItm(objParSis); 

            //Nombres de los tabs
            tabDevCom.setTitleAt(0,"General");
            tabDevCom.addTab("Asiento Diario",objDiario);    
           // objDiario.setEditable(false);
            getIva();
            pack();

            vecTipDoc =  new  java.util.Vector() ; //Vector que contiene el codigo de tipos de documentos
            String sqlPrede = "Select distinct cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)" +
                    " where   docprg.co_emp = " + intCodEmp + " and " +
                            " docprg.co_loc = " + intCodLoc   + " and  docprg.co_mnu = 45 and  docprg.st_reg='S' ";
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), 
                                    objParSis.getClaveBaseDatos(), sqlPrede, cboTipDoc, vecTipDoc);      

            cboTipDoc.setSelectedIndex(0);

            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
               NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));


            CargarBodegas();
            Configurar_tabla();

            cargarReg(intCodEmp, intCodLoc, intTipDoc, intCodDoc);
            this.setBounds(10,10, 700,450);
            
            objParSis.setCodigoEmpresa(intCodEmpOC);
            objParSis.setCodigoLocal(intCodLocOC);
        }
        catch (CloneNotSupportedException e){    objUti.mostrarMsgErr_F1(this, e);     }
    }

    public boolean acepta()
    {
        return blnEst;
    }
      
    public void CargarBodegas()
    {
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try
        {
            if (CONN_GLO!=null)
            {
                stmTmp = CONN_GLO.createStatement();
                String sql = "SELECT co_bod FROM tbr_bodLoc WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc;
                rst=stmTmp.executeQuery(sql);
                while(rst.next())
                {
                    arreBod.add(""+rst.getInt(1));    
                }
                rst.close();
                stmTmp.close();
                rst=null;
                stmTmp=null;
           }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    }
            
    private void CargarDatosParaDevolucion(int intCodEmpl, int intCodLocl, int intTipDocl, int intCodDocl)
    {
        try
        {   //odbc,usuario,password
                /*
                 * Agregando el Sql de Consulta para la cotizacion
                 */
                sSQL=   " SELECT a.co_cli, a.tx_nomcli as nomcli, a.tx_dircli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                        "        a.co_com as co_ven, a.tx_nomven as nomven, cli.tx_ide, cli.tx_tel, cli.co_ciu, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                        "        a.co_doc, a.fe_doc, a.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                        "        a.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                        "        a.tx_obs1, a.tx_obs2, a.nd_sub, a.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                        "        a.st_reg , a.ne_numdoc, cli.st_ivacom  " + // Campo para saber si esta anulado o no
                        " FROM tbm_cabMovInv as a" + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " inner join tbm_cli as cli on (cli.co_emp=a.co_emp and cli.co_cli=a.co_cli) "+
                        " Where a.co_emp = " +intCodEmpl+ // Consultando en la empresa en la ke se esta trabajando
                        "       and a.co_loc = " +intCodLocl+// Consultando en el local en el ke se esta trabajando
                        "       and a.co_tipdoc = " +intTipDocl+
                        "       and a.co_doc = " +intCodDocl+// Consultando en el numero de documento                        
                        "       and a.st_reg not in ('I','E') and a.st_coninv IN ('C','F')";

                if(objUti.getNumeroRegistro(jfrThis,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),sSQL)>0)
                {
                    // cargaTipoDocPredeterminado();
                    
                    cargaFactura(sSQL, intCodEmpl, intCodLocl, intTipDocl, intCodDocl);
                    noEditable2(false);  
                    java.awt.Color colBack = txtFromFac.getBackground(); 
                    txtFromFac.setEditable(false);
                    txtFromFac.setBackground(colBack);  

                    blnEst=false;
                    if(insertar())
                    {
                        //blnRes=false;
                        blnEst=true;
                    }
                }
                else
                {
                    //JOptionPane oppMsg = new JOptionPane();
                    String strTit, strMsg, strMsg2;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El documento no está CONFIRMADO. \n"+ 
                     " No es posible realizar la devolucion de la compra si no está confirmado. \n"+
                     " Comuníquese con la persona que realiza las confirmaciones para poder realizar la devolucion.";
                    JOptionPane.showMessageDialog(jfrThis, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE); 
                }
        }                                
        catch(Exception Evt) {   blnEst=false;        objUti.mostrarMsgErr_F1(jfrThis, Evt);    }
    }
       
    public void Configura_ventana_consulta()
    {
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
                   Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                           " where   b.co_emp = " + intCodEmp + " and " +
                           " b.co_loc = " + intCodLoc  + " and " +
                           " b.co_mnu = " + intCodMnu;
                  
            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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
    
    
    private boolean Configurar_tabla() 
    {
        boolean blnRes=false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");            
            vecCab.add(INT_TBL_ITMALT,"Cod. Item");
            vecCab.add(INT_TBL_BUTITM,"");
            vecCab.add(INT_TBL_DESITM,"Descripcion");            
            vecCab.add(INT_TBL_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_CODBOD,"Bodega");            
            vecCab.add(INT_TBL_BUTBOD,"");
            vecCab.add(INT_TBL_CANCOM,"Cantidad");
            vecCab.add(INT_TBL_CANMOV,"Devolucion");
            vecCab.add(INT_TBL_PREVEN,"Costo");            
            vecCab.add(INT_TBL_PORDES,"Desc.");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");            
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ITMORI,"");
            vecCab.add(INT_TBL_BODORI,"");
            vecCab.add(INT_TBL_CANORI,"Can.Ori.");
            vecCab.add(INT_TBL_CANDEV,"");
            vecCab.add(INT_TBL_ESTADO,"");
            vecCab.add(INT_TBL_IVATXT,"");
             vecCab.add(INT_TBL_ITMALT2,"Cod.Item2");
            vecCab.add(INT_TBL_ITMSER,"Ser.");
            vecCab.add(INT_TBL_CODLINREL, "COD_REG");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
   
            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.            
//            objTblMod.setColumnDataType(INT_TBL_CANDEV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANCOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREVEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL , ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            ArrayList arlAux=new ArrayList();
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer las columnas eleminadas
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODITM);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            arlAux.add("" + INT_TBL_ITMORI);
            arlAux.add("" + INT_TBL_BODORI);
            arlAux.add("" + INT_TBL_CANORI);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
                  

            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(145);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_CANCOM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_PREVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(50);         
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);         
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(80);         
            tcmAux.getColumn(INT_TBL_CODITM).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_CANORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_CANDEV).setWidth(0);
            tcmAux.getColumn(INT_TBL_CANDEV).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CANDEV).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(0);
               
            
            //Configurar JTable: Ocultar columnas del sistema.
            /**
             * Estado : 
                V:vacio.- si al momento de carga no tenia codigo asociado
                N:Nuevo.- Si fue modificado despues de la carga y estaba vacio
                E:Existe.- Si al momento de carga tenia codigo asociado
                M:Modificado.- Si fue modificado despues de la carga y estaba existe
             */
            tcmAux.getColumn(INT_TBL_ESTADO).setWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setResizable(false);                         
                                
            
            tcmAux.getColumn(INT_TBL_BUTBOD).setWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBOD).setResizable(false); 
            
            
            tcmAux.getColumn(INT_TBL_BUTITM).setWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_BUTITM).setResizable(false); 
            
            
              tcmAux.getColumn(INT_TBL_ITMALT2).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITMALT2).setResizable(false);                         
                                
            
            
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CANMOV);

            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){

                    blnChangeData = false;        
                    strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA).toString();
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA).toString();
                        if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null){
                                tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V")){
                            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)!=null)
                                tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("N")){
        	            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null)
                                tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("D")){
        	            tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }
                                
                        if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                            calculaSubtotal();
                            blnChangeData = true;
                        }                        
                }
            });

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UNIDAD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                       
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREVEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
   
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    blnChangeData = false;
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strBeforeValue = "0";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strAfterValue ="0";
                    if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null){
                            tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }
                           
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).equals(""))
                        tblDat.setValueAt("0",tblDat.getSelectedRow(),tblDat.getSelectedColumn());
                    if (!(strBeforeValue.equals(strAfterValue))){
                         if (Double.parseDouble(strAfterValue) - Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CANORI).toString()) > Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CANDEV).toString())){                                                      
                             String strMsg = "<html>La  <FONT COLOR=\"blue\">cantidad </font>a devolver es mayor a la orden de compra." +
                                             "\n Verifique si tiene otras devoluciones </html> " ;                            
                            MensajeInf(strMsg);
                            tblDat.setValueAt(strBeforeValue,tblDat.getSelectedRow(),INT_TBL_CANMOV);
                            blnChangeData = true; 
                        }
                        if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !blnChangeData){
                            blnChangeData = true; 
                            calculaSubtotal();
                        }
                    }
                    
                }
            });

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Centrar columnas.
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
             objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
         }else{
             objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
         }       
    }
       
    private void cargaTipoDocPredeterminado()
    {
        objTipDoc.DocumentoPredeterminado(intCodEmp, intCodLoc, intCodMnu);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomTipDoc.setText(objTipDoc.gettx_descor());
        txtDetTipDoc.setText(objTipDoc.gettx_deslar());
         
        getIva();
    }
    
    
    private void cargarDataTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomTipDoc.setText(objTipDoc.gettx_descor());
        txtDetTipDoc.setText(objTipDoc.gettx_deslar());   
        
        getIva();
    }    
    
    private void listaTipdoc(String campo,String strDesBusqueda ,int intTipo)
    {
        objVenConTipdoc.setTitle("Listado Tipos de Documentos"); 
        switch (intTipo)
        {
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
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE)
        {
            if(!objVenConTipdoc.getValueAt(1).equals(""))
            {
                cargarDataTipoDoc(Integer.parseInt(objVenConTipdoc.getValueAt(1)));
                //if (objTooBar.getEstado() == 'n') cargaNum_Doc_Preimpreso();
            }
        }      
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabDevCom = new javax.swing.JTabbedPane();
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
        txtCodTipDoc = new javax.swing.JTextField();
        txtDetTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFromFac = new javax.swing.JLabel();
        txtFromFac = new javax.swing.JTextField();
        txtFecFac = new javax.swing.JTextField();
        lblFecFac = new javax.swing.JLabel();
        cboTipDoc = new javax.swing.JComboBox();
        txtNomTipDoc = new javax.swing.JTextField();
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
        lblComSol = new javax.swing.JLabel();
        txtComSol = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        panCotNor = new javax.swing.JPanel();
        lblFacNumDes = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tabDevCom.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabDevCom.setName("General"); // NOI18N

        panCotGen.setLayout(new java.awt.BorderLayout());

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 135));
        panCotGenNor.setLayout(null);

        txtCliDir.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCliDir.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtCliDir);
        txtCliDir.setBounds(104, 108, 326, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Proveedor:");
        panCotGenNor.add(lblCli);
        lblCli.setBounds(8, 90, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDir.setText("Dirección:");
        panCotGenNor.add(lblDir);
        lblDir.setBounds(8, 110, 60, 15);

        txtCliCod.setBackground(objParSis.getColorCamposObligatorios());
        txtCliCod.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCliCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCliCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNor.add(txtCliCod);
        txtCliCod.setBounds(104, 88, 35, 20);

        txtCliNom.setBackground(objParSis.getColorCamposObligatorios());
        txtCliNom.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCliNom.setPreferredSize(new java.awt.Dimension(100, 20));
        panCotGenNor.add(txtCliNom);
        txtCliNom.setBounds(140, 88, 290, 20);
        panCotGenNor.add(txtAte);
        txtAte.setBounds(490, 70, 140, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNor.add(lblAte);
        lblAte.setBounds(420, 70, 60, 15);

        txtDoc.setBackground(objParSis.getColorCamposSistema());
        objParSis.getColorCamposObligatorios();
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtDoc);
        txtDoc.setBounds(104, 48, 92, 20);

        lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDoc.setText("Cod Documento:");
        panCotGenNor.add(lblDoc);
        lblDoc.setBounds(8, 50, 110, 15);

        txtDev.setBackground(objParSis.getColorCamposObligatorios()
        );
        txtDev.setMaximumSize(null);
        txtDev.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtDev);
        txtDev.setBounds(104, 68, 92, 20);

        lblDev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDev.setText("No. Devolucion:");
        panCotGenNor.add(lblDev);
        lblDev.setBounds(8, 70, 110, 15);

        txtVenNom.setBackground(objParSis.getColorCamposObligatorios());
        txtVenNom.setPreferredSize(new java.awt.Dimension(100, 20));
        panCotGenNor.add(txtVenNom);
        txtVenNom.setBounds(520, 50, 110, 20);

        txtVenCod.setBackground(objParSis.getColorCamposObligatorios());
        txtVenCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtVenCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNor.add(txtVenCod);
        txtVenCod.setBounds(490, 50, 35, 20);

        lblVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVen.setText("Comprador:");
        lblVen.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNor.add(lblVen);
        lblVen.setBounds(420, 50, 70, 15);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNor.add(lblFecDoc);
        lblFecDoc.setBounds(420, 10, 100, 15);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setText("Tipo Documento:");
        panCotGenNor.add(jLabel2);
        jLabel2.setBounds(8, 10, 100, 15);

        txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtCodTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNor.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(370, 30, 35, 20);

        txtDetTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDetTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDetTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDetTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtDetTipDoc);
        txtDetTipDoc.setBounds(195, 5, 190, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(butTipDoc);
        butTipDoc.setBounds(390, 5, 20, 20);

        lblFromFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFromFac.setText("No Doc O/C");
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
        txtFecFac.setBounds(520, 30, 110, 20);

        lblFecFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecFac.setText("Fecha de Orden:");
        panCotGenNor.add(lblFecFac);
        lblFecFac.setBounds(420, 30, 100, 15);

        cboTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panCotGenNor.add(cboTipDoc);
        cboTipDoc.setBounds(196, 28, 156, 20);

        txtNomTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtNomTipDoc);
        txtNomTipDoc.setBounds(105, 5, 90, 20);

        panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        spnCon.setPreferredSize(new java.awt.Dimension(453, 418));

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Codigo", "Descripción", "Unidad", "Bodega", "Cantidad", "Devolucion", "Costo", "%Desc", "Iva", "Total", "co_itm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, false, false, false
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

        spnObs1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spnObs1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txaObs1.setLineWrap(true);
        txaObs1.setWrapStyleWord(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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

        panCotGenSurEst.setLayout(new java.awt.GridLayout(4, 2));

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

        lblComSol.setText("Comp. Solidaria");
        panCotGenSurEst.add(lblComSol);

        txtComSol.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtComSol);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panCotGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabDevCom.addTab("tab1", panCotGen);

        getContentPane().add(tabDevCom, java.awt.BorderLayout.CENTER);

        lblFacNumDes.setText("Devolución");
        lblFacNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblFacNumDes.setOpaque(true);
        panCotNor.add(lblFacNumDes);

        getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // Configura_ventana_consulta();
        // Configurar_tabla();
        // CargarDatosParaDevolucion(intCodEmp, intCodLoc, intTipDoc, intCodDoc);
    }//GEN-LAST:event_formWindowOpened

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
         String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
    }//GEN-LAST:event_CerrarVentana

    private void txtNomTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipDocActionPerformed
         listaTipdoc("a.tx_descor",txtNomTipDoc.getText(),1);
    }//GEN-LAST:event_txtNomTipDocActionPerformed

    private void txtDetTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDetTipDocActionPerformed
        listaTipdoc("a.tx_deslar",txtDetTipDoc.getText(),2);
    }//GEN-LAST:event_txtDetTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
       listaTipdoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtFromFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFromFacActionPerformed
       
    }//GEN-LAST:event_txtFromFacActionPerformed
    
    private void MensajeInf(String strMsg) 
    {
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";            
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);        
    }
        
   
    /*
     * Clase de tipo documenet listener para detectar los cambios en 
     * los textcomponent
     */
    class LisTextos implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
    }        
    
    public void noEditable2(boolean blnEditable)
    {
        java.awt.Color colBack = txtDoc.getBackground();
 
        colBack = txtCodTipDoc.getBackground();
        txtCodTipDoc.setEditable(blnEditable);
        txtCodTipDoc.setBackground(colBack);        

        colBack = txtNomTipDoc.getBackground();
        txtNomTipDoc.setEditable(blnEditable);
        txtNomTipDoc.setBackground(colBack);        

        colBack = txtDoc.getBackground();
        txtDoc.setEditable(blnEditable);
        txtDoc.setBackground(colBack);  

        colBack = txtFromFac.getBackground();
        txtFromFac.setEditable(!blnEditable);
        txtFromFac.setBackground(colBack);    

        cboTipDoc.setEnabled(!blnEditable);
    }
    
    public void noEditable(boolean blnEditable)
    {
        java.awt.Color colBack = txtDoc.getBackground();

        txtDoc.setEditable(blnEditable);
        txtDoc.setBackground(colBack);

        colBack = txtCliCod.getBackground();
        txtCliCod.setEnabled(blnEditable);
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
        txtFecFac.setEnabled(blnEditable);
        txtFecFac.setBackground(colBack);        


        colBack = txtSub.getBackground();
        txtSub.setEditable(blnEditable);
        txtSub.setBackground(colBack);        

        txtIva.setEditable(blnEditable);
        txtIva.setBackground(colBack);        

        txtTot.setEditable(blnEditable);
        txtTot.setBackground(colBack);  
    }
  
    public void clnTextos()
    {
        //Cabecera
        objDiario.inicializar();
        
        txtDoc.setText("");
        txtFromFac.setText("");
        txtDev.setText("");

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
        lblFacNumDes.setText("Devolucion");
    }   
    
    
    /*
     * Retorna el numero secuencial asociados a ese numero y tipo de documento.
     */
    public int[] getNumTipDoc(int int_OrdDoc)
    {
        int intSec[] = new int[2];
        try
        {
            ResultSet rstFromFac;
            Statement stmFromFac = CONN_GLO.createStatement();
            String SqlSecuencial =" Select ne_numdoc, co_tipdoc from tbm_cabmovinv " +
                                  " where ne_numdoc   = " + int_OrdDoc ;
                                   
            rstFromFac = stmFromFac.executeQuery(SqlSecuencial);
            
            if(rstFromFac.next())
            {
                intSec[0] = rstFromFac.getInt(1);
                intSec[1] = rstFromFac.getInt(2);
            }
            
            rstFromFac.close();
            stmFromFac.close();
        }
        catch(SQLException Evt) {      objUti.mostrarMsgErr_F1(jfrThis, Evt);      }
        catch(Exception Evt)  {      objUti.mostrarMsgErr_F1(jfrThis, Evt);      }                      
        return intSec;
    }
    /**
     * Metodo que verifica la naturaleza del documento verificando del objTooBar Tipo Doc
     * devuleve 1 si es tipo Ingreso y -1 si es Egreso
     *@autor: jsalazar
     */
    private int getAccionDoc()
    {
        if (objTipDoc.gettx_natdoc().equals("I"))
            return 1;
        else
            return -1;
    } 

    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
 
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
                case INT_TBL_CODBOD:
                    strMsg="Codigo Bodega";
                    break;
                case INT_TBL_CANMOV:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_PREVEN:
                    strMsg="Costo";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    } 

    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.  
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calculaTotal();
                    generaAsiento();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
        }
    }
    
    private void MensajeValidaCampo(String strNomCampo)
    {
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }     
    
    //<editor-fold defaultstate="collapsed" desc="/* comentado */">
//
//private boolean agregarCambioCuentasContables(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dblValAsi ){
//  boolean blnRes=false;
//  java.sql.Statement stmLoc, stmLoc01;
//  java.sql.ResultSet rstLoc;
//  int intCodReg=0;
//  String strSql="";
//  try{
//    if(conn!=null){
//     stmLoc=conn.createStatement();
//     stmLoc01=conn.createStatement();
//
// /*********** CASO IVA ********************/
//
//     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
//     " from tbm_detdia as a " +
//     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
//     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
//     " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   and  a.nd_mondeb > 0   ";
//     rstLoc=stmLoc.executeQuery(strSql);
//     if(rstLoc.next()){
//
//          double dblValIva =  objUti.redondear( (dblValAsi -(dblValAsi/1.12)), 2);
//
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", "+dblValIva+", nd_monhab, tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb + "+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//
//     }
//     rstLoc.close();
//     rstLoc=null;
//
// /******************************************/
// /*********** CASO PROVEEDOR VARIOS ********************/
//
//      strSql="select a2.co_ctahab, a1.co_ctafin,  a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
//      " from tbm_detdia as a " +
//      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctahab=a.co_cta ) " +
//      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
//      " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"  and  a.nd_monhab > 0     ";
//      rstLoc=stmLoc.executeQuery(strSql);
//      if(rstLoc.next()){
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", nd_mondeb, "+dblValAsi+", tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_monhab= nd_monhab + "+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     }
//     rstLoc.close();
//     rstLoc=null;
//
//  /******************************************/
//
//      stmLoc.close();
//      stmLoc=null;
//      stmLoc01.close();
//      stmLoc01=null;
//    blnRes=true;
// }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
// return blnRes;
//}


//private boolean agregarCambioCuentasContablesDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dblValAsi, int intCodEmpOC, int intCodLocOC, int intCodTipDocOC, int intCodDocOC  ){
//  boolean blnRes=false;
//  java.sql.Statement stmLoc, stmLoc01;
//  java.sql.ResultSet rstLoc;
//  int intCodReg=0;
//  String strSql="";
//  try{
//    if(conn!=null){
//     stmLoc=conn.createStatement();
//     stmLoc01=conn.createStatement();
//
//     if(intEstCruAut == 1 ){
//
// /*********** CASO IVA ********************/
//
//     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
//     " from tbm_detdia as a " +
//     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
//     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
//     " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//     rstLoc=stmLoc.executeQuery(strSql);
//     if(rstLoc.next()){
//
//       double dblValIva =  objUti.redondear( (dblValAsi -(dblValAsi/1.12)), 2);
//
//       strSql="SELECT a.nd_mondeb FROM tbm_detdia AS a   "
//       + " WHERE a.co_emp="+intCodEmpOC+" AND a.co_loc="+intCodLocOC+" AND a.co_tipdoc="+intCodTipDocOC+" AND a.co_dia="+intCodDocOC+"  and co_cta="+rstLoc.getInt("co_ctafin")+" ";
//       //if(_existeCtaIvaOC(conn, strSql, dblValIva  )){
//
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", nd_mondeb, "+dblValIva+",  tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_monhab= nd_monhab + "+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//       //}
//     }
//     rstLoc.close();
//     rstLoc=null;
//
// /******************************************/
// /*********** CASO PROVEEDOR VARIOS ********************/
//
//      strSql="select a2.co_ctahab, a1.co_ctafin,  a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
//      " from tbm_detdia as a " +
//      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctadeb=a.co_cta ) " +
//      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
//      " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//      rstLoc=stmLoc.executeQuery(strSql);
//      if(rstLoc.next()){
//
//       strSql="SELECT a.nd_monhab FROM tbm_detdia AS a   "
//       + " WHERE a.co_emp="+intCodEmpOC+" AND a.co_loc="+intCodLocOC+" AND a.co_tipdoc="+intCodTipDocOC+" AND a.co_dia="+intCodDocOC+"  and co_cta="+rstLoc.getInt("co_ctafin")+" ";
//     //  if(_existeCtaIvaOC(conn, strSql, dblValAsi  )){
//
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", "+dblValAsi+", nd_monhab, tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb + "+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     // }
//
//     }
//     rstLoc.close();
//     rstLoc=null;
//
//  /******************************************/
//     }
//      stmLoc.close();
//      stmLoc=null;
//      stmLoc01.close();
//      stmLoc01=null;
//    blnRes=true;
// }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
// return blnRes;
//}
        
        
//        
//private boolean actualizarReg(){
//  boolean blnRes=false;
//  java.sql.Statement stmLoc;
//  java.sql.ResultSet rstLoc;
//  String strSql="";
//  String strFecha ="";
//  int intTipCli=3;
//  double dlbCan=0;
//  double dblCanDev=0;
//  double dblCanMov=0;
//  int intCodItm=0;
//  try{
//      if(CONN_GLO!=null){
//          stmLoc=CONN_GLO.createStatement();
//
//          objCiudad_dat.setCiudad(objCliente_dat.getCodciudad());
//      
//          int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
//          strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
//
//          if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";
//
//           strSql ="UPDATE tbm_cabMovInv SET fe_doc  = '"+strFecha+"', co_cli="+txtCliCod.getText()+", "+ 
//           " tx_nomCli='" + txtCliNom.getText() + "', tx_dirCli='" + txtCliDir.getText() + "', " +
//           " tx_ruc='" +objCliente_dat.getIdentificacion() + "', "+" tx_telCli='" + objCliente_dat.getTelefono()+ "', " +
//           " tx_ciuCli='" + objCiudad_dat.getNombrelargo() + "', co_com  = "  + ((txtVenCod.getText().equals(""))?"9":txtVenCod.getText()) + ",  " +
//           " tx_nomven='" + txtVenNom.getText() + "', tx_ate  = '" + txtAte.getText()    + "', " + 
//           " nd_sub  = "  + (objUti.redondeo(Double.parseDouble(txtSub.getText()),6)* -1)    + ",  " +
//           " nd_valiva  = "  + (objUti.redondeo(Double.parseDouble(txtIva.getText()),6)* -1)    + ",  " +
//           " nd_tot  = "  + (objUti.redondeo(Double.parseDouble(txtTot.getText()),6)* -1)    + ",  " +
//           " nd_porIva = "+dblPorIva+  ",  fe_ultMod   = '"+ strFecSis + "', " +
//           " co_usrMod   = "+ objParSis.getCodigoUsuario()+" ,st_regrep='"+STR_ESTREG+"' "+
//           "  WHERE " +
//           " co_emp = " + intCodEmp + " and "+  
//           " co_loc = " + intCodLoc + " and "+  
//           " co_doc = " +  txtDoc.getText()               + " and "+  
//           " co_tipDoc = " + txtCodTipDoc.getText(); 
//           stmLoc.executeUpdate(strSql);                                
//
//           intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(),CONN_GLO);
//          //****************************************************************************************
//            strSql ="SELECT co_itm, co_bod, nd_can  FROM  tbm_detMovInv where " +
//            " co_emp = " + intCodEmp + " and "+  
//            " co_loc = " + intCodLoc + " and "+  
//            " co_doc = " +  txtDoc.getText()               + " and "+  
//            " co_tipDoc = " + txtCodTipDoc.getText();
//            rstLoc=stmLoc.executeQuery(strSql);  
//            while(rstLoc.next()){
//                
//               if(!objZafTipItm.isServicio(rstLoc.getInt("co_itm")) ){                                             
//                    objZafInv.movInventario(rstLoc.getInt("co_itm"), rstLoc.getInt("co_bod"), 1,rstLoc.getDouble("nd_can"), CONN_GLO, "","","");
//                    dlbCan = rstLoc.getDouble("nd_can")*-1;  
//                      
//                   //***************************************          
//                     if(intTipCli==1){
//                       if(!objSisCon.actualizarInventario_2(CONN_GLO, intCodEmp, intCodLoc, rstLoc.getInt("co_itm"), dlbCan, rstLoc.getInt("co_bod"), "","","" ) ){
//                               CONN_GLO.rollback();
//                               return false;
//                     }}         
//                  //***************************************
//             }}
//             rstLoc.close();
//             rstLoc=null;
//                                    
//             strSql=" DELETE FROM tbm_detMovInv " +
//             " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" " +
//             " AND co_doc="+txtDoc.getText()+" AND co_tipDoc="+txtCodTipDoc.getText();
//             stmLoc.executeUpdate(strSql); 
//                                    
//              for(int i=0; i<tblDat.getRowCount();i++){
//               if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null){
//                  dblCanMov=Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
//                  if( dblCanMov > 0.00 ){
//                     intCodItm = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
//                     strSql="select sum(abs(b.nd_can)) as total from tbr_cabmovinv as a"+ 
//                     " inner join tbm_detmovinv as b on(a.co_doc=b.co_doc and a.co_tipdoc=b.co_tipdoc)"+
//                     " where a.co_docrel="+intNunDocCom+" and a.co_emp="+intCodEmp+" and "+
//                     " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+intCodItm+" and a.st_reg='A'";
//                     rstLoc=stmLoc.executeQuery(strSql);  
//                     if(rstLoc.next())
//                         dblCanDev = rstLoc.getDouble("total");
//                     rstLoc.close();
//                     rstLoc=null;
//                       dblCanDev=dblCanDev+dblCanMov;
//                                    
//                     strSql="INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
//                     " co_itm, tx_codalt,tx_codalt2, tx_nomItm,  " +//<==Campos que aparecen en la parte superior del 1er Tab
//                     " co_bod,tx_unimed, nd_can, nd_canorg, nd_cosuni, nd_cosunigrp , nd_preuni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                     " ,st_reg,nd_costot ,nd_costotgrp ,nd_tot , nd_candev ,st_regrep )" +
//                     " VALUES("+intCodEmp+", "+intCodLoc + ", " + 
//                     txtCodTipDoc.getText()+ ", "+txtDoc.getText()+ ", "+(i+1)+", "+
//                     tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +tblDat.getValueAt(i, INT_TBL_ITMALT) +  "','" +
//                     tblDat.getValueAt(i, INT_TBL_ITMALT2) +  "','"+tblDat.getValueAt(i, INT_TBL_DESITM) + "'," +                                                   
//                     tblDat.getValueAt(i, INT_TBL_CODBOD) + ", '" + tblDat.getValueAt(i, INT_TBL_UNIDAD) + "'," +
//                     (objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()),objParSis.getDecimalesBaseDatos())* getAccionDoc()) + ", " +
//                     (objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()),objParSis.getDecimalesBaseDatos())) + ", " +
//                     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),objParSis.getDecimalesBaseDatos()) + ", " +
//                     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),objParSis.getDecimalesBaseDatos()) + ", " +
//                     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),objParSis.getDecimalesBaseDatos()) + ", " +
//                     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objParSis.getDecimalesMostrar()) + "," +
//                     " '" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " + ",'P'," +
//                     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objParSis.getDecimalesMostrar()) * getAccionDoc() + ","+
//                     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objParSis.getDecimalesMostrar()) * getAccionDoc()  + ","+
//                     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),2) + " , "+dblCanDev * getAccionDoc() +" , '"+STR_ESTREG+"' )";
//                     stmLoc.executeUpdate(strSql); 
//                                      
//                      strSql="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel, co_emprel)"+
//                      " values("+intCodEmp+","+intCodLoc+","+strCodTipDoDevDen +","+intCodDocDevVen+"," +
//                      tblDat.getValueAt(i,INT_TBL_CODLINREL).toString()+",'A',"+intCodLoc+","+
//                      txtCodTipDoc.getText()+","+txtDoc.getText()+","+(i+1)+", "+intCodEmp+")";
//                      stmLoc.executeUpdate(strSql); 
//                                         
//                      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                        if(!objZafTipItm.isServicio( Integer.parseInt( (tblDat.getValueAt(i, INT_TBL_CODITM)==null || tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()) ) && tblDat.getValueAt(i, INT_TBL_CANMOV)!=null){
//                                String strCanMovida  = (tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString();   
//                                objZafInv.movInventario( Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM)),Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD)), -1,Double.parseDouble(strCanMovida ), CONN_GLO , "","","");
//                      //*********************************************************          
//                        if(intTipCli==1){   
//                                   if(!objSisCon.actualizarInventario_2(CONN_GLO, intCodEmp, intCodLoc, Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM))  ,  
//                                       Math.abs(Double.parseDouble(strCanMovida ))*-1,Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))
//                                    , "","","" )){
//                                      CONN_GLO.rollback();
//                                     return false;
//                        }}
//                       //*********************************************************  
//                        }
//                       /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                }}}
//                                    
//               
//                 strSql = " DELETE FROM tbm_pagMovInv WHERE " +
//                 " co_emp = "+intCodEmp+" AND co_loc = "+intCodLoc+" AND "+  
//                 " co_doc = "+txtDoc.getText()+ " AND  co_tipDoc = " + txtCodTipDoc.getText(); 
//                 stmLoc.executeUpdate(strSql); 
//
//                   
//                strSql = " Insert Into tbm_pagmovinv (co_emp,co_loc,co_tipDoc,co_doc,co_reg,fe_ven,mo_pag ,st_regrep) values (" + 
//                intCodEmp+","+intCodLoc+ "," + 
//                objTipDoc.getco_tipdoc()+","+txtDoc.getText()+ ", 1 , '"+strFecSis+"', "+ 
//                objUti.redondeo((Double.parseDouble(txtTot.getText())*getAccionDoc()),6)+", '"+STR_ESTREG+"')";
//                stmLoc.executeUpdate(strSql); 
//                                       
//                if(!objDiario.actualizarDiario(CONN_GLO, intCodEmp, intCodLoc, 
//                    Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtDoc.getText()),
//                    txtDev.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){
//                        return false;
//                }
//              stmLoc.close();
//              stmLoc=null;
//              blnRes=true;
//             
//      }} catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }
//         catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }
//      return blnRes;
//    }  
//        


/*
    private boolean eliminaDet(java.sql.Connection conn)
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strEstFisBod="";
        int intTipCli=3;
        int intCodItm=0;
        int intCodBod=0;
        int intTipStk=0;
        double dlbCanMov=0;
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();
                objInvItm.inicializaObjeto();

                intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(),conn);

                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1)) IN ( " +
                                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                                " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                                " THEN 'S' ELSE 'N' END AS proconf  ";

                strSql= " SELECT a.co_itm, a.co_bod, a.nd_can "+strAux2+"  FROM  tbm_detMovInv as a " +
                        " INNER JOIN tbm_inv AS a1 ON(a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) " +
                        " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" " +
                        " AND a.co_doc="+txtDoc.getText()+" AND a.co_tipDoc="+txtCodTipDoc.getText()+" AND a1.st_ser='N' ";
                rstLoc=stmLoc.executeQuery(strSql);  
                while(rstLoc.next())
                {
                    intCodItm=rstLoc.getInt("co_itm");
                    intCodBod=rstLoc.getInt("co_bod");
                    dlbCanMov=rstLoc.getDouble("nd_can");

                    strEstFisBod=rstLoc.getString("proconf");
                    objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, 1, strEstFisBod, "", "", 1);  
                    intTipStk=1; 
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;

                if(intTipStk==1)
                {
                  if(!objInvItm.ejecutaActStock(conn, intTipCli))
                     return false;
                  if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
                    return false;
                }
                objInvItm.limpiarObjeto();   
                blnRes=true;    
           }
        }
        catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }   
        return  blnRes;
    }
    


    private boolean actualizaDet(java.sql.Connection conn)
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strEstFisBod="";
        int intTipCli=3;
        int intCodItm=0;
        int intCodBod=0;
        int intTipStk=0;
        double dlbCanMov=0;
        double dblCanMov=0;
        double dblCanDev=0;
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();
                objInvItm.inicializaObjeto();
                intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(),conn);

                strSql= "DELETE FROM tbm_detMovInv WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" " +
                        " AND co_doc="+txtDoc.getText()+" AND co_tipDoc="+txtCodTipDoc.getText();
                stmLoc.executeUpdate(strSql); 

                for(int i=0; i<tblDat.getRowCount();i++)
                {
                    if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null){
                        dblCanMov=Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
                        if( dblCanMov > 0.00 )
                        {
                            intCodItm = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                            strSql= "select sum(abs(b.nd_can)) as total from tbr_cabmovinv as a"+ 
                                    " inner join tbm_detmovinv as b on(a.co_doc=b.co_doc and a.co_tipdoc=b.co_tipdoc)"+
                                    " where a.co_docrel="+intNunDocCom+" and a.co_emp="+intCodEmp+" and "+
                                    " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+intCodItm+" and a.st_reg='A'";
                            rstLoc=stmLoc.executeQuery(strSql);  
                            if(rstLoc.next())
                                dblCanDev = rstLoc.getDouble("total");
                            rstLoc.close();
                            rstLoc=null;
                            dblCanDev=dblCanDev+dblCanMov;

                            strSql= " INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " co_itm, tx_codalt,tx_codalt2, tx_nomItm,  " +//<==Campos que aparecen en la parte superior del 1er Tab
                                    " co_bod,tx_unimed, nd_can, nd_canorg, nd_cosuni, nd_cosunigrp , nd_preuni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                    " ,st_reg,nd_costot ,nd_costotgrp ,nd_tot , nd_candev ,st_regrep )" +
                                    " VALUES("+intCodEmp+", "+intCodLoc+ ", " + 
                                    txtCodTipDoc.getText()+ ", "+txtDoc.getText()+ ", "+(i+1)+", "+
                                    tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +tblDat.getValueAt(i, INT_TBL_ITMALT) +  "','" + 
                                    tblDat.getValueAt(i, INT_TBL_ITMALT2) +  "',"+objUti.codificar(tblDat.getValueAt(i, INT_TBL_DESITM)) + "," + 
                                    tblDat.getValueAt(i, INT_TBL_CODBOD) + ", '" + tblDat.getValueAt(i, INT_TBL_UNIDAD) + "'," +
                                    (objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()),objParSis.getDecimalesBaseDatos())* getAccionDoc()) + ", " +
                                    (objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()),objParSis.getDecimalesBaseDatos())) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),objParSis.getDecimalesBaseDatos()) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),objParSis.getDecimalesBaseDatos()) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),objParSis.getDecimalesBaseDatos()) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objParSis.getDecimalesMostrar()) + "," +
                                    " '" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " + ",'P'," +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objParSis.getDecimalesMostrar()) * getAccionDoc() + ","+
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objParSis.getDecimalesMostrar()) * getAccionDoc()  + ","+
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),2) + " , "+dblCanDev * getAccionDoc() +" , '"+STR_ESTREG+"' )";
                            stmLoc.executeUpdate(strSql); 

                            strSql="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,   co_locrel,co_tipdocrel,co_docrel,co_regrel, co_emprel)"+
                                   " values("+intCodEmp+","+intCodLoc+","+strCodTipDoDevDen +","+intCodDocDevVen+"," +
                                    tblDat.getValueAt(i,INT_TBL_CODLINREL).toString()+", "+intCodLoc+","+
                                    txtCodTipDoc.getText()+","+txtDoc.getText()+","+(i+1)+", "+intCodEmp+")";
                            stmLoc.executeUpdate(strSql); 

                            if(objInvItm.isItmServicio(conn, intCodItm))
                            {
                                intCodBod=Integer.parseInt( tblDat.getValueAt(i, INT_TBL_CODBOD).toString() );
                                dlbCanMov=dblCanMov;

                                strEstFisBod="";
                                objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, "", "", -1);  
                                intTipStk=1; 
                            }
                        }
                    }
                }

                stmLoc.close();
                stmLoc=null;

                if(intTipStk==1)
                {
                  if(!objInvItm.ejecutaActStock(conn, intTipCli))
                     return false;
                  if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
                    return false;
                }
                objInvItm.limpiarObjeto();   
                blnRes=true;    
            }
        }
        catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }   
        return  blnRes;
    }
      
    private boolean actualizarPag(java.sql.Connection conn)
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                strSql="DELETE FROM tbm_pagMovInv WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" " +
                      " AND co_doc="+txtDoc.getText()+" AND co_tipDoc="+txtCodTipDoc.getText()+" "+ 
                      " ; Insert Into tbm_pagmovinv (co_emp,co_loc,co_tipDoc,co_doc,co_reg,fe_ven,mo_pag ,st_regrep) values (" + 
                      intCodEmp+","+intCodLoc+ "," + 
                      objTipDoc.getco_tipdoc()+","+txtDoc.getText()+ ", 1 , '"+strFecSis+"', "+ 
                      objUti.redondeo((Double.parseDouble(txtTot.getText())*getAccionDoc()),6)+", 'M')";
                stmLoc.executeUpdate(strSql); 
                stmLoc.close();
                stmLoc=null;
               blnRes=true;
       
            }
        }
        catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }
        return blnRes;
    }  
*/    
//</editor-fold>   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboTipDoc;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblComSol;
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
    private javax.swing.JPanel panCotGen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panCotNor;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabDevCom;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliDir;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtComSol;
    private javax.swing.JTextField txtDetTipDoc;
    private javax.swing.JTextField txtDev;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtFecFac;
    private javax.swing.JTextField txtFromFac;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomTipDoc;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtVenCod;
    private javax.swing.JTextField txtVenNom;
    // End of variables declaration//GEN-END:variables
    
            
    private boolean validaCampos()
    {
        if(txtCodTipDoc.getText().equals("") )
        {
            tabDevCom.setSelectedIndex(0);                  
            MensajeValidaCampo("Tipo de Documento");
            txtCodTipDoc.requestFocus();
            return false;
        }
        if(!txtFecDoc.isFecha())
        {
            tabDevCom.setSelectedIndex(0);                  
            MensajeValidaCampo("Fecha del Documento ");
            txtFecDoc.requestFocus();
            return false;
        }

        //VAlidando los campos ke deben ser numericos
        if(!txtFromFac.getText().equals(""))
            if(!objUti.isNumero(txtFromFac.getText()))
            {
                tabDevCom.setSelectedIndex(0);                  
                MensajeValidaCampo("Cotizacion");
                txtFromFac.requestFocus();
                return false;
            }

        for (int i=0;i<tblDat.getRowCount();i++)
        {
            if (tblDat.getValueAt(i,INT_TBL_CANMOV)!=null)
            {
                Double CanOri = Double.valueOf(tblDat.getValueAt(i,INT_TBL_CANCOM).toString());
                Double CanDev = Double.valueOf(tblDat.getValueAt(i,INT_TBL_CANMOV).toString());
                if (CanDev.doubleValue() > CanOri.doubleValue() )
                {
                     MensajeInf("Selecciono una cantidad de devolucion mayor que la generada por la compra.");
                     tabDevCom.setSelectedIndex(0);   
                     return false;
                }    
            }
        }
        if (!objDiario.isDiarioCuadrado())
        {
            MensajeInf("Asiento descuadrado.");
            tabDevCom.setSelectedIndex(1);
            return false;                                    
        }
        if (!objDiario.isDocumentoCuadrado(txtTot.getText()))
        {
            MensajeInf("Asiento y Total del Documento no cuadran.\nPor favor verifique!!");
            tabDevCom.setSelectedIndex(1);
            return false;                                                       
        }
               
        return true; 
    }
    
        
    public void cargaFactura(String strSQL, int intCodEmpl, int intCodLocl, int intTipDocl, int intCodDocl )
    {
        String strSer ="";
        try
        {//odbc,usuario,password        
            Statement  stm;
            double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;            

            // System.out.println("Sql--> "+ strSQL );

            //con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (CONN_GLO!=null)
            {
                stm = CONN_GLO.createStatement();
                rstCab = stm.executeQuery(strSQL);
                int intNumCot = 0;
                if(rstCab.next())
                {
                    intNumCot   = rstCab.getInt("co_doc");
                    intNunDocCom = intNumCot;                        
                    intSecuencialFac = rstCab.getInt("co_doc");

                    txtCliCod.setText(((rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli")));
                    txtCliNom.setText(((rstCab.getString("nomcli")==null)?"":rstCab.getString("nomcli")));
                    txtCliDir.setText(((rstCab.getString("dircli")==null)?"":rstCab.getString("dircli")));

                    txtIdePrv.setText(((rstCab.getString("tx_ide")==null)?"":rstCab.getString("tx_ide")));
                    txtTelPrv.setText(((rstCab.getString("tx_tel")==null)?"":rstCab.getString("tx_tel")));

                    txtCodCiuPrv.setText(((rstCab.getString("co_ciu")==null)?"":rstCab.getString("co_ciu")));

                    txtFromFac.setText(((rstCab.getString("ne_numdoc")==null)?"":rstCab.getString("ne_numdoc")));

                    txtFecFac.setText( rstCab.getString("fe_doc") );

                    txtVenCod.setText(((rstCab.getString("co_ven")==null)?"":rstCab.getString("co_ven")));
                    txtVenNom.setText(((rstCab.getString("nomven")==null)?"":rstCab.getString("nomven")));
                    txtAte.setText(((rstCab.getString("tx_ate")==null)?"":rstCab.getString("tx_ate")));

                    //Pie de pagina
                    txaObs1.setText(((rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1")));
                    txaObs2.setText(((rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2")));
                    stIvaCom =  rstCab.getString("st_ivacom");

                    /*AGREGADO POR INCREMENTO DEL IVA DEL 14% CMATEO*/
                    dblPorIva=rstCab.getDouble("nd_porIva");
                    /*AGREGADO POR INCREMENTO DEL IVA DEL 14% CMATEO*/ 

                    //Detalle         
                    sSQL =  " SELECT * FROM (" +
                            " SELECT  " +
                            " a.co_reg, a.co_itm, a.co_itmact,  a.tx_codalt, a.tx_codalt2, a.tx_nomitm, a.tx_unimed, a.co_bod, a.nd_can, a.nd_canorg," +
                            " a.nd_cosuni, a.nd_preuni, a.nd_pordes, a.st_ivacom, inv.st_ser, x.candev ,x.coregdevven  " +
                            " FROM tbm_detmovinv AS a " +
                            " INNER JOIN tbm_inv AS inv on (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) " +
                            " INNER JOIN (" +
                            " SELECT  coreg,candev, coregdevven FROM( " +
                            " "+STRINS.toString()+""+
                            " ) AS x  WHERE  x.codoc is not null AND  codoc="+intCodDocl+" ) AS x ON(x.coreg=co_reg)" +
                            " WHERE a.co_emp="+intCodEmpl+" AND " +
                            " a.co_loc="+intCodLocl+" AND a.co_tipdoc="+intTipDocl+" AND a.co_doc="+intCodDocl+" ) AS X";

                    java.sql.Statement stmaux = CONN_GLO.createStatement();
                    java.sql.ResultSet rst= stmaux.executeQuery(sSQL);

                    java.util.Vector vecData = new java.util.Vector();
                    while (rst.next())
                    {  
                        strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_BUTITM, "");
                        vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                        vecReg.add(INT_TBL_BUTBOD, "");
                        vecReg.add(INT_TBL_CANCOM, new Double(rst.getDouble("nd_can"))); 
                        vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("candev")));   //*********                                                                        
                        vecReg.add(INT_TBL_PREVEN, new Double(rst.getDouble("nd_cosuni")));
                        vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                        String strIva = rst.getString("st_ivacom");
                        Boolean blnIva = new Boolean(strIva.equals("S"));                         
                        vecReg.add(INT_TBL_BLNIVA, blnIva);
                        dblCan    = rst.getDouble("nd_canorg");
                        dblPre    = rst.getDouble("nd_cosuni");
                        dblPorDes = rst.getDouble("nd_pordes");
                        dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                        dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objParSis.getDecimalesMostrar()) ;
                        if (blnIva.booleanValue()){                            
                           dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                        }

                        vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                        vecReg.add(INT_TBL_CODITM, rst.getString("co_itmact"));
                        vecReg.add(INT_TBL_ITMORI, rst.getString("co_itmact"));
                        vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
                        vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_canorg")));

                        strSQL = " SELECT abs(abs(nd_canorg) - abs(sum(nd_can))) as nd_can "+
                                 " from tbm_cabmovinv as cab " +
                                 " LEFT outer join tbm_detmovinv as det on (cab.co_emp = det.co_emp  and cab.co_loc =det.co_loc and cab.co_tipdoc = det.co_tipdoc and cab.co_doc = det.co_doc) "+
                                 " WHERE cab.co_emp = " +intCodEmpl+ " and " +
                                 " cab.co_loc = "+intCodLocl+ " and cab.co_tipdoc = " +txtCodTipDoc.getText() + " and " +
                                 " cab.co_doc = " +intCodDocl+" and co_reg = " + rst.getInt("co_reg") + " and co_itm=" + rst.getInt("co_itm") + " and cab.st_reg ='A'" +
                                 " group by nd_canorg ";
                        java.sql.Statement stmDev = CONN_GLO.createStatement();
                        java.sql.ResultSet rstAux = stmDev.executeQuery(strSQL);
                        if (rstAux.next()){
                            vecReg.add(INT_TBL_CANDEV, new Double(rstAux.getDouble("nd_can")));
                        }else{

                            vecReg.add(INT_TBL_CANDEV, new Double(rst.getDouble("nd_can")));
                        }
                        rstAux.close();
                        stmDev.close();
                        vecReg.add(INT_TBL_ESTADO, "E");
                        vecReg.add(INT_TBL_IVATXT, "");
                        vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_ITMSER, strSer );
                        vecReg.add(INT_TBL_CODLINREL, rst.getString("coregdevven"));
                        vecData.add(vecReg);                         
                }
                rst.close();
                stmaux.close();
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);    
                txtIva.setText(""+dblIva);
                txtTot.setText(""+objUti.redondear((objUti.redondear(txtSub.getText(),objParSis.getDecimalesMostrar())+dblIva),objParSis.getDecimalesMostrar()));

                calculaSubtotal_OC();                 

                /*VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO*/
//                        
                String strStatus = (rstCab.getString("st_reg")==null)?"":rstCab.getString("st_reg");
                if(strStatus.equals("I"))
                {
                    // I
                }
                    calculaTotal();
                }
                rstCab.close();
                stm.close();
                setEditable(true);                    
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
      
       noEditable2(true);
    }
    
    /*
     * Rose: Obtiene el Iva que se debe cobrar en la empresa actual
     */
    public void getIva()
    {
        if (objTipDoc.getst_iva().equals("S"))
        {
            objParSis.cargarConfiguracionImpuestos();
            BigDecimal bdgIva=objParSis.getPorcentajeIvaCompras();
            dblPorIva= bdgIva.doubleValue();
            System.out.println("ZafCom04.Iva.Rose: "+dblPorIva);
        }
        else
        {
            dblPorIva = 0;                        
        }
        lblIva.setText("IVA: " + dblPorIva + "%");
    }

    public void calculaSubtotal()
    {
        double dblCan,dblDes,dblCosto;
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_CANMOV);
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PREVEN)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PREVEN);
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PORDES);
        dblCan = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV).toString());
        dblCosto = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PREVEN).toString());
        if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES).toString())/100);
        else dblDes = 0;
        tblDat.setValueAt(String.valueOf(objUti.redondear(((dblCan * dblCosto )-dblDes), objParSis.getDecimalesMostrar())),tblDat.getSelectedRow(), INT_TBL_TOTAL);
        calculaTotal();
        generaAsiento();
        
    }
    
    
    
     public void calculaSubtotal_OC()
     {
        double dblCan=0,dblDes=0,dblCosto=0;
        for (int i=0;i<tblDat.getRowCount();i++)
        { 
            dblCan=0; 
            dblDes=0;
            dblCosto=0;
            if (tblDat.getValueAt(i, INT_TBL_CANMOV)==null)
                tblDat.setValueAt("0",i, INT_TBL_CANMOV);
            if (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)
                tblDat.setValueAt("0",i, INT_TBL_PREVEN);
            if (tblDat.getValueAt(i, INT_TBL_PORDES)==null)
                tblDat.setValueAt("0",i, INT_TBL_PORDES);
            dblCan = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
            dblCosto = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_PREVEN).toString());
            if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblDat.getValueAt(i, INT_TBL_PORDES).toString())/100);
            else dblDes = 0;
            tblDat.setValueAt(String.valueOf(objUti.redondear(((dblCan * dblCosto )-dblDes), objParSis.getDecimalesMostrar())),i, INT_TBL_TOTAL);
        }
        calculaTotal();
        generaAsiento();
        
    }
    
    public void calculaTotal()
    {
        double dblSub = 0, dblSub2 = 0, dblIva = 0, dblDes = 0, dblTmp=0;
        double dblIvaCom=0,dblTotalCom=0,dblSubtotalCom=0;
        double dblSubIvaCero=0, dblSubIvaGra=0, dblCompSol=0;
        BigDecimal bigPorComSolVen=BigDecimal.ZERO;
        
        for (int i=0;i<tblDat.getRowCount();i++)
        { 
            dblSub2 = objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objParSis.getDecimalesMostrar());  
            dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
             
            if(tblDat.getValueAt(i, INT_TBL_BLNIVA)!=null)
            {
                if(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true")){
                    dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objParSis.getDecimalesMostrar()): 0 );
                    dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                    dblSubIvaGra+=dblTmp;
                    if(intCodEmp==2 && intCodLoc==4){
                        Calendar calFecha=Calendar.getInstance();
                        bigPorComSolVen=objParSis.getPorcentajeCmpSolVentas(intCodEmp, intCodLoc, calFecha.getTime());
                        dblCompSol+= dblTmp * bigPorComSolVen.doubleValue();
                    }
                }else{
                    dblSubIvaCero+=dblSub2;
                    dblCompSol+=0;
                }
               
            }
        }
                         
        dblSubtotalCom = dblSub;
        dblIvaCom = objUti.redondear(dblIva,intNumDec);
        dblTotalCom = dblSubtotalCom + dblIvaCom;
        dblTotalCom = objUti.redondear(dblTotalCom ,intNumDec);
        dblSubtotalCom = objUti.redondear(dblSubtotalCom ,intNumDec);
        if(stIvaCom.equals("N"))
        {
            txtSub.setText( "" + dblSubtotalCom );
            txtIva.setText( "0.00" );
            txtTot.setText( ""+ dblSubtotalCom);
            dblTotalCom=dblSubtotalCom;
            dblIvaCom =0;                   
//            txtSubTotIvaGra.setText( "" + dblSubIvaGra );
			 txtSubTotIvaGra.setText( "" + 0 );
            txtSubTotIvaCero.setText( "" + dblSubIvaCero);
            txtComSol.setText( "" + 0 );
        }
        else
        {
            txtSub.setText( "" + dblSubtotalCom );
            txtIva.setText( "" + dblIvaCom );
            txtTot.setText( ""+ dblTotalCom);     
            txtComSol.setText( "" + dblCompSol);
            txtSubTotIvaGra.setText( "" + dblSubIvaGra );
            txtSubTotIvaCero.setText( "" + dblSubIvaCero);
        }
    }
    
    /**
     * Metodo que genera vector de diario para la clase ZafAsiDia
     * Genera segun el tipo de documento y su naturaleza, bodegas y si genera o no IVA
     *@autor: jayapata
     */    
    private void generaAsiento()
    {
        objDiario.inicializar();
        double dblSubtotal=0, dblTotSer=0;
        int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE   = 5; //5) Debe. 
        int INT_VEC_HABER  = 6; //6) Haber. 
        int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO    = 8;

        int intEst=0;
          
         
        //********************************************************
         
        for(int i=0; i<tblDat.getRowCount(); i++){
            if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                 if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("S")) {
                     dblTotSer += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
                     intEst=1;
                  }
            }
        }
      
        //********************************************************
         
         
         
        if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
          else vecDetDiario.removeAllElements();

        java.util.Vector vecReg; 
         
        int intCodBodT,intCodBod;
         
        if(objTipDoc.gettx_natdoc().equals("I"))
        {
            for (int j=0;j<arreBod.size();j++)
            {
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++){            
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) 
                        {  
                            if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                            {
                                intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                                if(intCodBod==intCodBodT)
                                  dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
                            }
                        }
                    }
                }
                      
                      
                      if(dblSubtotal > 0 ){
                       vecReg = new java.util.Vector();
                       vecReg.add(INT_LINEA, null);                
                       vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                       vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                       vecReg.add(INT_VEC_BOTON, null);                
                       vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                       vecReg.add(INT_VEC_DEBE, new Double(dblSubtotal));  
                       vecReg.add(INT_VEC_HABER, new Double(0));
                       vecReg.add(INT_VEC_REF, null);
                       vecReg.add(INT_VEC_NUEVO, null);
                       vecDetDiario.add(vecReg);
                      }
            }  
            if(stIvaCom.equals("S"))
            {
                if (objTipDoc.getst_iva().equals("S"))
                {
                    //Mateo
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);      
                    int intCodIvaCtble=objParSis.getCodigoCuentaContableIvaCompras(intCodEmp, intCodLoc, objUti.parseDate(txtFecFac.getText(), "yyyy-MM-dd"));
                    //vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                    vecReg.add(INT_VEC_CODCTA,intCodIvaCtble);
                    //vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());   
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.obtenerNumCta(intCodEmp, intCodIvaCtble));                   
                    vecReg.add(INT_VEC_BOTON, null);
                    //vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                 
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.obtenerNomCta(intCodEmp, intCodIvaCtble));                
                    vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar())));
                    vecReg.add(INT_VEC_HABER, new Double(0)); 
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg); 
                }    
            }   
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(0)); 
            vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar())));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);  
        }
        else
        {      
            for (int j=0;j<arreBod.size();j++)
            {
                intCodBod = Integer.parseInt(arreBod.get(j).toString());
                dblSubtotal=0;
                for (int i=0;i<tblDat.getRowCount();i++)
                {            
                    if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) 
                        {    
                            if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                            {
                               intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                                if(intCodBod==intCodBodT)
                                  dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objParSis.getDecimalesMostrar());
                            }
                        }
                    }
                }
                if(dblSubtotal > 0 )
                {
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                    vecReg.add(INT_VEC_BOTON, null);                
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                    vecReg.add(INT_VEC_DEBE, new Double(0));  
                    vecReg.add(INT_VEC_HABER, new Double(dblSubtotal));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);
                }
            }  
            
            if(stIvaCom.equals("S"))
            {
                if (objTipDoc.getst_iva().equals("S"))
                {
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_LINEA, null);                
                    vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                    vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());  
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                
                    vecReg.add(INT_VEC_DEBE, new Double(0)); 
                    vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtIva.getText(),objParSis.getDecimalesMostrar())));   
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);                       
                }       
            }
           
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtTot.getText(),objParSis.getDecimalesMostrar()))); 
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);  
        }
         
        if(intEst==1)
        {
            if(dblTotSer > 0 )
            {
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaSerCompras()));
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaSerCom());   
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaSerCom());                 
                vecReg.add(INT_VEC_DEBE, new Double(0));
                vecReg.add(INT_VEC_HABER, new Double(dblTotSer)); 
                vecReg.add(INT_VEC_REF, null);
                vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);  
            }
        }
        objDiario.setDetalleDiario(vecDetDiario);         
    }
 
    private boolean cargarReg(int intCodEmpl, int intCodLocl, int intTipDocl, int intCodDocl)
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg(intCodEmpl, intCodLocl, intTipDocl, intCodDocl ))
            {
                refrescaDatos(intCodEmpl, intCodLocl, intTipDocl, intCodDocl);
                
                
                
                if(!modificar()){
                  blnRes=false;
                  blnEst=false;
                }else{
                    blnEst=true;
                }
                 txtNomTipDoc.setEditable(false);
                 txtDetTipDoc.setEditable(false);
                 butTipDoc.setEnabled(false);
                            
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
    
    
    private boolean cargarCabReg(int intCodEmpl, int intCodLocl, int intTipDocl, int intCodDocl)
    {
        boolean blnRes=true;
        try
        {
            if (CONN_GLO!=null)
            {
                java.sql.Statement stm=CONN_GLO.createStatement();
                String strSQL="";
                strSQL= "SELECT DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                      " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                      " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                      " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                      " DocCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                      " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                      " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                      " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra "   + //Campos del Tab OTROS 
                      " ,DocCab.st_regrep FROM tbm_cabMovInv as DocCab left outer join  tbm_cabtipdoc as doc  on (DocCab.co_emp = doc.co_emp and DocCab.co_loc = doc.co_loc and DocCab.co_tipdoc = doc.co_tipdoc) " +
                      " Where  DocCab.co_emp="+intCodEmpl+
                      " AND DocCab.co_loc="+intCodLocl+
                      " AND DocCab.co_tipDoc="+intTipDocl+
                      " and DocCab.st_reg not in('E') AND DocCab.co_doc="+intCodDocl;
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                    int intNumCot = rst.getInt("co_doc");
                    txtDoc.setText(""+intNumCot);

                    txtCodTipDoc.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
                    int intNumTipDoc[] = getNumTipDoc(rst.getInt("ne_numcot"));
                    if(intNumTipDoc != null){
                        txtFromFac.setText(""+intNumTipDoc[0]);
                       // txtFromFac.setText(rst.getString("ne_numcot")==null?"":rst.getString("ne_numcot"));
                        //cboTipDoc.setSelectedIndex(vecTipDoc.indexOf(""+intNumTipDoc[1]));
                    }
                    txtDev.setText(""+rst.getInt("ne_numDoc"));
                    txtCliCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtCliNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtCliDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    
                    STR_ESTREG=rst.getString("st_regrep");
                    
                    if(rst.getDate("fe_doc")==null){
                      txtFecDoc.setText("");  
                    }else{
                        java.util.Date dateObj = rst.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                    }

                    txtVenCod.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                    txtVenNom.setText(((rst.getString("nomven")==null)?"":rst.getString("nomven")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
             
                 
                 java.awt.Color colBack = txtFromFac.getBackground();
                 txtFromFac.setEditable(false);
                 txtFromFac.setBackground(colBack);    
                 
                }
                else
                {
                    clnTextos();
                }
            
                rst.close();
                stm.close();
                rst=null;
                stm=null;
              
                blnHayCam=false;
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
    
     
    public void  refrescaDatos(int intCodEmpl, int intCodLocl, int intTipDocl, int intCodDocl)
    {
        String strSer="";
        try
        {//odbc,usuario,password        
            if(CONN_GLO != null)
            {

                //Detalle        
                //Extrayendo los datos del detalle respectivo a esta orden de compra
                sSQL  = "Select a.co_reg, a.co_itm, a.tx_codalt, a.tx_codalt2, a.tx_nomitm, a.tx_unimed, a.co_bod, a.nd_can, " +
                        " a.nd_canorg, a.nd_cosuni, a.nd_preuni, a.nd_pordes, a.st_ivacom, inv.st_ser , x.candev ,x.coregdevven" +
                        " FROM tbm_detMovInv as a" +
                        " INNER JOIN tbm_inv as inv ON (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) " +
                        "" +
                        " LEFT JOIN ( SELECT  coreg,candev, coregdevven FROM( " +
                        " "+STRINS.toString()+""+
                        " ) AS x  WHERE  x.codoc is not null AND  codoc="+intCodDocl+" ) AS x ON(x.coreg=co_reg)" +
                        " " +
                        "WHERE " +
                        " a.co_emp ="+intCodEmpl+" and " +
                        " a.co_loc ="+intCodLocl+" and " +
                        " a.co_tipdoc = "+intTipDocl+" and " +
                        " a.co_doc = "+intCodDocl+" order by a.co_reg";

                java.sql.Statement stm = CONN_GLO.createStatement();
                java.sql.ResultSet rst= stm.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;

                java.util.Vector vecData = new java.util.Vector();
                while (rst.next())
                {   
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_CANCOM, new Double(rst.getDouble("nd_canorg"))); 
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("candev")) );                                                                           
                    vecReg.add(INT_TBL_PREVEN, new Double(rst.getDouble("nd_cosuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivacom");
                    Boolean blnIva = new Boolean(strIva.equals("S"));                         
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan    = rst.getDouble("nd_can")*getAccionDoc();
                    dblPre    = rst.getDouble("nd_cosuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                    dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objParSis.getDecimalesMostrar()) ;
                    if (blnIva.booleanValue()){                            
                       dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objParSis.getDecimalesMostrar()) ;
                    }

                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_can")*(rst.getDouble("nd_can")<0?getAccionDoc():1)));                                                                           

                    vecReg.add(INT_TBL_CANDEV, new Double(rst.getDouble("nd_can")));

                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, "");
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );

                    vecReg.add(INT_TBL_CODLINREL, rst.getString("coregdevven"));

                    vecData.add(vecReg);                         
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);                                 

                txtIva.setText(""+dblIva);
                txtTot.setText(""+objUti.redondear((objUti.redondear(txtSub.getText(),objParSis.getDecimalesMostrar())+dblIva),objParSis.getDecimalesMostrar()));

                calculaSubtotal_OC();

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
   

    public boolean insertar()
    {
        boolean blnRes=false;
        if(validaCampos())
        {
            if(insertarReg())
            {
                blnRes=true;
            }
        }
        blnHayCam=false;
        return blnRes;
    }
        
        
    public boolean modificar()
    {
        //strAux=objTooBar.getEstadoRegistro();
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

        if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
                  return false;

        if (objUltDocPrint.isPagoDocumento_aso(intCodEmp,intCodLoc,Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtDoc.getText())))
                return false;

        if (!validaCampos())
            return false;

        blnHayCam=false;
        return true;
    }
        
        
    public boolean insertarReg()
    {
        boolean blnRes=false;
        java.sql.Statement stmloc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        int intCodDocLoc=0;
        int intGenCruAut=1;  // REALIZA CRUCE AUTOMATICAMENTE
        try
        {
            if(CONN_GLO!=null)
            {
                stmloc=CONN_GLO.createStatement(); 

                Librerias.ZafCtaTrans.ZafCtaTrans objCtaTrans = new Librerias.ZafCtaTrans.ZafCtaTrans( this, objParSis);

                strSql= " SELECT CASE WHEN (Max(co_doc)+1) is null THEN 1 else Max(co_doc)+1 END as co_doc  FROM tbm_cabMovInv WHERE co_emp = " + intCodEmp + 
                        " and co_loc = " + intCodLoc +" and co_tipDoc = " + txtCodTipDoc.getText() ;
                rstLoc = stmloc.executeQuery(strSql);
                if(rstLoc.next())
                    intCodDocLoc = rstLoc.getInt("co_doc");
                rstLoc.close();
                rstLoc=null;

                intEstCruAut=0;
                dblValCruAut=0;

                if(insertarRegCab(CONN_GLO, intCodDocLoc)){
                    if(insertarRegDet(CONN_GLO, intCodDocLoc)){
                        if(insertarRegPag(CONN_GLO, intCodDocLoc, intGenCruAut)){
                            if(objDiario.insertarDiario_OC(CONN_GLO, intCodEmp, intCodLoc,Integer.parseInt(txtCodTipDoc.getText()), 
                                String.valueOf(intCodDocLoc), txtDev.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), 65 )){
                                if( objCtaTrans.cambioCtaConIvaDevCom(CONN_GLO, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), intCodDocLoc
                                    , intCodEmp, intCodLoc, intTipDoc, intCodDoc ) ){
                                    if( objCtaTrans.cambioCtaConProDevCom(CONN_GLO, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), intCodDocLoc
                                        , intCodEmp, intCodLoc, intTipDoc, intCodDoc ) )
                                    {
                                            //agregarCambioCuentasContablesDevCom(CONN_GLO, intCodEmp, intCodLoc,
                                            //Integer.parseInt(txtCodTipDoc.getText()), intCodDocLoc, java.lang.Math.abs(dblValCruAut)
                                            //, intCodEmp, intCodLoc, intTipDoc, intCodDoc
                                            // );
                                        if(objUti.costearDocumento(this, objParSis, CONN_GLO, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), intCodDocLoc)){
                                            txtDoc.setText(""+intCodDoc);
                                            blnRes=true;
                                        }
                                    }
                                    
                                }
                            }
                        }
                    }
                }
                objInvItm.limpiarObjeto();
                stmloc.close();
                stmloc=null;
                objCtaTrans=null;
            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }  
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }                       
        return blnRes;                  
    }

    int intEstCruAut=0;
    double dblValCruAut=0;
        
    public boolean insertarRegCab(java.sql.Connection conIns, int intCodDocLoc)
    {
        boolean blnRes=false;
        java.sql.Statement stmloc;
        String strSql="";
        int intSecGrp = 0;
        int intSecEmp = 0;
        try
        {
           if(conIns!=null)
           {
               stmloc=conIns.createStatement(); 

               //System.out.println("insertarRegCab----> "+ txtCodCiuPrv.getText() );

               objCiudad_dat.setCiudad(Integer.parseInt(txtCodCiuPrv.getText().equals("")?"0":txtCodCiuPrv.getText()));
               /*CAMBIO POR EL IVA 14%*/
               //getIva();  //No descomentar, para que traiga el porcentaje de iva de la orden de compra.
               /*CAMBIO POR EL IVA 14%*/

                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText()); 
                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                ///******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
                // intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(CONN_GLO);
                // intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(CONN_GLO);
                ///*******************************************************************///

//                   System.out.println("txtSubTotIvaGra "+ txtSubTotIvaGra.getText());
                   strSql = "INSERT INTO  tbm_cabMovInv" +
                            "(co_emp, co_loc, co_tipDoc, co_doc, " + //CAMPOS PrimayKey
                            " ne_secemp, fe_doc, ne_secgrp, "+ // ne_secgrpant, ne_secempant, "                 + //secuencial de documentos y Fecha 
                            " co_cli, co_com, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                            " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, " + //<-- Campos de info. del cliente
                            " tx_nomven, "                                   +//INformacion del vendedor
                            " ne_numDoc, ne_numCot, " +//<==Campos que aparecen en la parte superior del 1er Tab
                           
                           /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
                           //" tx_obs1, tx_obs2, nd_sub,nd_tot,nd_valiva,nd_porIva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                           " tx_obs1, tx_obs2, nd_sub,nd_tot,nd_valiva," +//<==Campos que aparecen en la parte inferior del 1er Tab
                           /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
                           " fe_ing, co_usrIng  " + //Campos de Auditoria Fecha de Ingreso Y Usuario q ingreso.
                            //" ,st_reg, st_regrep, st_coninv )" +
                            " ,st_reg, st_regrep, st_coninv, nd_porcomsol, nd_valcomsol, nd_subivacer, nd_subivagra )" +                            
                            "VALUES ("+intCodEmp + ", "+intCodLoc+ ","+txtCodTipDoc.getText()+","+intCodDocLoc+","+  
                            intSecEmp+" ,'"+strFecha+"',"+intSecGrp+", " +  //" "+(intSecGrp-1)+" ,"+(intSecEmp-1)+" , " +
                            txtCliCod.getText()+","+((txtVenCod.getText().equals(""))?"9":txtVenCod.getText())+",'"+txtAte.getText()+"','"+ 
                            txtCliNom.getText()+"','"+txtCliDir.getText()+"','"+txtIdePrv.getText()+"','"+
                            txtTelPrv.getText()+"','"+objCiudad_dat.getNombrelargo()+"','"+txtVenNom.getText()+"',"+
                            ((txtDev.getText().equals(""))?"0":txtDev.getText())+","+  
                            ((txtFromFac.getText().equals(""))?"0":txtFromFac.getText())+",'"+txaObs1.getText()+"','"+  
                            txaObs2.getText()+"',"+(objUti.redondeo(Double.parseDouble(txtSub.getText()),6) * getAccionDoc())+","+
                            (objUti.redondeo(Double.parseDouble(txtTot.getText()),6) * getAccionDoc()) + ","+ 
                            (objUti.redondeo(Double.parseDouble(txtIva.getText()),6) * getAccionDoc()) + ","+ 
                            
                            /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
                            //objUti.redondeo(dblPorIva , 2)+","+"'"+ strFecSis + "',"+objParSis.getCodigoUsuario()+",'A','I', 'F')";
                            "'"+ strFecSis + "',"+objParSis.getCodigoUsuario()+",'A','I', 'F',"+dblPorComSol+","+((txtComSol.getText()!=null && txtComSol.getText()!="")?txtComSol.getText():0)+","+((txtSubTotIvaCero.getText()!=null && txtSubTotIvaCero.getText()!="")?txtSubTotIvaCero.getText():0)+ "," + ((txtSubTotIvaGra.getText()!=null && txtSubTotIvaGra.getText()!="")?txtSubTotIvaGra.getText():0)+")";
                            /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
                            
                            stmloc.executeUpdate(strSql); 
                           //****************************************************************************///

                   strSql = "INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel, st_regrep)"+
                            " values("+intCodEmp+","+intCodLoc+","+
                            txtCodTipDoc.getText()+","+intCodDocLoc+",'A',"+intCodEmp+","+intCodLoc+","+
                            vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+","+intNunDocCom+",'I')";
                   stmloc.executeUpdate(strSql); 

                   strSql = "INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel, st_regrep)"+
                            " values("+intCodEmp+","+intCodLoc+","+
                            strCodTipDoDevDen+","+intCodDocDevVen+",'A',"+intCodEmp+","+intCodLoc+","+
                            txtCodTipDoc.getText()+","+intCodDocLoc+",'I')";
                   stmloc.executeUpdate(strSql); 

                   stmloc.close();
                   stmloc=null;
                   blnRes=true;
           }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }  
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }                       
        return blnRes;                  
    }            
     
                       
    private boolean insertarRegDet(java.sql.Connection conIns, int intCodDocLoc)
    {
        boolean blnRes=false;
        java.sql.Statement stmloc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strEstFisBod="";
        int intTipCli=3;
        int intCodBod=0;
        int intCodItm=0;
        int intTipStk=0;
        double  dblCanDev=0;
        double dblCanMov=0;
        double dlbCanMov=0;
        try
        {
            if(conIns!=null)
            {
                stmloc=conIns.createStatement(); 
                objInvItm.inicializaObjeto();    
                intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCliCod.getText(),CONN_GLO);
                for(int i=0; i<tblDat.getRowCount();i++)
                {
                    if(tblDat.getValueAt(i, INT_TBL_CANMOV ) != null)
                    {
                        dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
                        if( dblCanMov > 0.00 )
                        {
                            strSql= "select sum(abs(b.nd_can)) as total from tbr_cabmovinv as a"+ 
                                    " inner join tbm_detmovinv as b on(a.co_doc=b.co_doc and a.co_tipdoc=b.co_tipdoc)"+
                                    " where a.co_docrel="+intNunDocCom+" and a.co_emp="+intCodEmp+" and "+
                                    " a.co_tipdocrel="+vecTipDoc.elementAt(cboTipDoc.getSelectedIndex())+" and co_itm="+tblDat.getValueAt(i, INT_TBL_CODITM)+" and a.st_reg='A'";
                            rstLoc = stmloc.executeQuery(strSql);
                            if(rstLoc.next())
                            {
                                dblCanDev = rstLoc.getDouble(1);
                                rstLoc.close();
                                rstLoc=null;

                                dblCanDev=dblCanDev+dblCanMov;

                                strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                        " co_itm, co_itmact,  tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                        " co_bod, tx_unimed, nd_can, nd_canorg,  nd_cosUni, nd_cosUniGrp, nd_preuni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                        
                                        /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
                                        //" ,st_reg,nd_costot,nd_costotgrp,nd_tot , nd_candev ,st_regrep )" +
                                        " ,st_reg,nd_costot,nd_costotgrp,nd_tot , nd_candev ,st_regrep ,nd_poriva ,nd_basimpivacer ,nd_basimpivagra )" +
                                        /*AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA*/
                                        
                                        " VALUES("+intCodEmp + ", " +intCodLoc+ ", " + 
                                        txtCodTipDoc.getText() + ", " +intCodDocLoc + ", " +(i+1)+ ", " +
                                        tblDat.getValueAt(i, INT_TBL_CODITM) + "," +tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
                                        tblDat.getValueAt(i, INT_TBL_ITMALT) + "','" +tblDat.getValueAt(i, INT_TBL_ITMALT2) + "'," +
                                        objUti.codificar(tblDat.getValueAt(i, INT_TBL_DESITM)) + "," +tblDat.getValueAt(i, INT_TBL_CODBOD) + ", '" + tblDat.getValueAt(i, INT_TBL_UNIDAD) +"',"+
                                        (getAccionDoc() * objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()),6)) + ", " +
                                        (getAccionDoc() *-1 * objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()),6)) + ", " +
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),6) + ", " +
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),6) + ", " +
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREVEN).toString()),6) + ", " +
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", '" +
                                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
                                        ",'P'," +
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),2) * getAccionDoc() + ", "+
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),2) * getAccionDoc() + ", "+
                                        //objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),2) + " , "+ dblCanDev * getAccionDoc()   + " ,'I' )";
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),2) + " , "+ dblCanDev * getAccionDoc()   + " ,'I', "+
                                        
                                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?dblPorIva:0)+ " , " +
                                        
                                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("false"))?Double.parseDouble((tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()):0)+" , "+
                                        
                                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?Double.parseDouble((tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()):0)+
                                        
                                        ")";
                                stmloc.executeUpdate(strSql); 

                                strSql="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,    co_locrel,co_tipdocrel,co_docrel,co_regrel, co_emprel )"+
                                        " values("+intCodEmp+","+intCodLoc+","+ strCodTipDoDevDen +","+intCodDocDevVen+"," + tblDat.getValueAt(i,INT_TBL_CODLINREL).toString()+
                                        ",    "+intCodLoc+","+  txtCodTipDoc.getText()+","+intCodDocLoc+","+(i+1)+","+intCodEmp+")";
                                stmloc.executeUpdate(strSql); 

                                intCodItm=Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString());

                                if(objInvItm.isItmServicio(conIns, intCodItm))
                                {
                                    intCodBod=Integer.parseInt( tblDat.getValueAt(i, INT_TBL_CODBOD).toString() );
                                    dlbCanMov=dblCanMov;

                                    strEstFisBod="";
                                    /*objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, "", "", -1);  
                                    intTipStk=1; */

                                    //Mateo
                                    ZafStkInv invStock= new ZafStkInv(objParSis);
                                    if(invStock.actualizaInventario(conIns, intCodEmp, INT_ARL_STK_INV_STK_DIS, "-", 1, obtenerArrItmInv(conIns, intCodEmp, intCodItm,dlbCanMov,intCodBod))){
                                            if(!invStock.actualizaInventario(conIns, intCodEmp, INT_ARL_STK_INV_STK, "-", 0, obtenerArrItmInv(conIns, intCodEmp, intCodItm,dlbCanMov,intCodBod))){
                                                  return false;
                                            }
                                    }else{
                                        return false;
                                    }
                                    //Mateo
                                }
                            }
                        }
                    }
                }
                       
                stmloc.close();
                stmloc=null;

                /*if(intTipStk==1){

                    // System.out.println("--> Aqui "+objInvItm.getQueryEjecutaActStock() );

                    if(!objInvItm.ejecutaActStock(conIns, intTipCli))
                      return false;
                    if(!objInvItm.ejecutaVerificacionStock(conIns, intTipCli ))
                      return false;
                 }
                 objInvItm.limpiarObjeto();*/         
                blnRes=true;       
            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }  
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }                       
        return blnRes;                  
    }            

                           
                           
    public boolean insertarRegPag(java.sql.Connection conIns, int intCodDocLoc, int intGenCruAut)
    {
        boolean blnRes=true;
        java.sql.Statement stmloc;
        java.sql.Statement stmLocPri;
        java.sql.Statement stmLocCru;
        java.sql.ResultSet rstLoc;
        String strSql="";
        double dblTot =0;
        double dblBaseDePagos=0;   
        try
        {
            if(conIns!=null)
            {
                stmloc=conIns.createStatement();                       
                stmLocPri=conIns.createStatement();  
                stmLocCru=conIns.createStatement();  

                dblTot = Double.parseDouble(txtTot.getText());
                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText()); 
                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

                strSql="Insert Into tbm_pagmovinv (co_emp,co_loc,co_tipDoc,co_doc,co_reg,fe_ven,mo_pag, st_regrep) values (" + 
                intCodEmp + "," +intCodLoc+ ","+ txtCodTipDoc.getText()+","+intCodDocLoc+
                ", 1 ,'"+strFecSis+"', "+objUti.redondear((dblTot*getAccionDoc()),2)+", 'I')";
                stmloc.executeUpdate(strSql); 

                dblBaseDePagos=objUti.redondear((dblTot),2);

                //**************************************************************************************
                if(intGenCruAut==1)
                {
                    double dblTotDev=0;

                    strSql= " SELECT  sum(abs(a1.nd_tot)) as totdev FROM tbr_cabmovinv as a " +
                            " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
                            " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+txtCodTipDoc.getText()+" "+
                            " and a.co_doc not in ("+intCodDocLoc+") and  "+
                            " a.co_emprel="+intCodEmp+" and a.co_locrel="+intCodLoc+" " +
                            " and a.co_tipdocrel="+intTipDoc+" and a.co_docrel="+intCodDoc+" and a1.st_reg not in ('I','E') and a.st_reg ='A' ";
                    rstLoc=stmloc.executeQuery(strSql);
                    if(rstLoc.next())
                    {
                       dblTotDev=rstLoc.getDouble("totdev");
                    }
                    rstLoc.close();
                    rstLoc=null;

                    if(objUltDocPrint.isCruceComDev(conIns, intCodEmp, String.valueOf(intCodLoc), String.valueOf(intTipDoc), intCodDoc, dblBaseDePagos )){
                        if(!objUltDocPrint.isPagoDocRet( intCodEmp, intCodLoc ,  intTipDoc , intCodDoc  )){
                            if( objUltDocPrint.vericaPag( conIns, intCodEmp, String.valueOf(intCodLoc), String.valueOf(intTipDoc),  intCodDoc, dblBaseDePagos, dblTotDev ) ){
                                if( objUltDocPrint.vericaPagCru( conIns, intCodEmp, String.valueOf(intCodLoc), String.valueOf(intTipDoc), intCodDoc,  dblBaseDePagos, dblTotDev ) )
                                {
                                      realizaCruceAut( conIns, strFecha, intCodDoc, intCodDocLoc, dblBaseDePagos, " and nd_porret = 0 " );
                                }
                            }
                            else realizaCruceAut( conIns, strFecha, intCodDoc, intCodDocLoc, dblBaseDePagos, "  " );

                        }
                        else
                        {
                            realizaCruceAut( conIns, strFecha, intCodDoc, intCodDocLoc, dblBaseDePagos, "  " );
                        }
                    }
                }
                //**************************************************************************************
                stmloc.close();
                stmloc=null;
                stmLocPri.close();
                stmLocPri=null;
                stmLocCru.close();
                stmLocCru=null;
            }
        }
        catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }  
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false; }                       
        return blnRes;                  
    }            


    private boolean realizaCruceAut(java.sql.Connection conn, String strFecha, int intCodDoc, int intCodDocLoc, double dblBaseDePagos, String strAux )
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc, stmLoc01;
        String strSql="";
        int intTipDocCru=91;
        int intCodDocCru=0;
        int intSecc=1;
        double dblValCru=0;
        int intCodEmpOC=0;
        int intCodLocOC=0;
        int intCodTipDocOC=0;
        int intCodDocOC=0;
        int intCodRegOC=0;
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();
                stmLoc01=conn.createStatement();

                dblValCruAut=dblBaseDePagos;

                intCodDocCru = objUltDocPrint.getCodDocTbmCabPag(conn, intTipDocCru);
                strSql="INSERT INTO TBM_CABPAG(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, co_cli, tx_nomcli, nd_mondoc, st_reg, fe_ing , co_usring, st_regrep, st_imp)" +
                       " VALUES("+intCodEmp+", "+intCodLoc+", "+intTipDocCru+", "+intCodDocCru+" " +
                       ", '"+strFecha+"', "+intCodDocCru+", "+txtCliCod.getText()+", '"+txtCliNom.getText()+"',"+dblBaseDePagos+",'A','"+ strFecSis +"',"+objParSis.getCodigoUsuario()+",'I','P')";
                stmLoc01.executeUpdate(strSql);

                strSql ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                        " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmp+", "+intCodLoc+", " +
                        " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+intCodLoc+","+objTipDoc.getco_tipdoc()+","+intCodDocLoc+" "+
                        " ,1, "+dblBaseDePagos+","+objTipDoc.getco_tipdoc()+")";
                stmLoc01.executeUpdate(strSql);

                strSql= "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, abs((abs(mo_pag)-abs(nd_abo))) as mopag FROM tbm_pagmovinv where "+
                        " co_Emp="+intCodEmp+" " +
                        " and co_loc="+intCodLoc+" and co_tipdoc="+intTipDoc+" " +
                        " and co_doc="+intCodDoc+" and st_reg in ('C','A') "+strAux+"   order by co_reg ";
                java.sql.ResultSet rst = stmLoc.executeQuery(strSql);
                while(rst.next())
                {
                    if(dblBaseDePagos > 0)
                    {
                        if( rst.getDouble("mopag") < dblBaseDePagos )
                        {
                            dblValCru=rst.getDouble("mopag");
                            dblBaseDePagos=dblBaseDePagos-dblValCru;
                        }
                        else
                        {
                            dblValCru=dblBaseDePagos;
                            dblBaseDePagos=dblBaseDePagos-dblValCru;
                        }

                        intCodEmpOC=rst.getInt("co_emp");
                        intCodLocOC=rst.getInt("co_loc");
                        intCodTipDocOC=rst.getInt("co_tipdoc");
                        intCodDocOC=rst.getInt("co_doc");
                        intCodRegOC=rst.getInt("co_reg");

                        if(dblValCru!=0)
                        {
                            intSecc++;
                            strSql ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                                    " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmp+", "+intCodLoc+", " +
                                    " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+rst.getString("co_loc")+","+rst.getString("co_tipdoc")+","+rst.getString("co_doc")+" "+
                                    " ,"+rst.getString("co_reg")+",abs("+dblValCru+")*-1,"+rst.getString("co_tipdoc")+")";

                            strSql+=" ; UPDATE tbm_pagmovinv SET nd_abo= (abs(nd_abo)+abs("+dblValCru+"))*-1 where " +
                                    "  co_emp="+rst.getString("co_emp")+" and co_loc="+rst.getString("co_loc")+" and  co_tipdoc="+rst.getString("co_tipdoc")+" and co_doc="+rst.getString("co_doc")+" and co_reg="+rst.getString("co_reg");
                            stmLoc01.executeUpdate(strSql);
                            //   se quita esta opcion para que mantenga las cuentas cta en OC      agregarCambioCuentasContables(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, java.lang.Math.abs(dblValCru) );
                        }
                    }
                }
                rst.close();
                rst=null;

                if( dblBaseDePagos != 0 )
                {
                    strSql =" UPDATE tbm_pagmovinv SET nd_abo= (abs(nd_abo)+abs("+dblBaseDePagos+"))*-1 where " +
                            "  co_emp="+intCodEmpOC+" and co_loc="+intCodLocOC+" and  co_tipdoc="+intCodTipDocOC+" and co_doc="+intCodDocOC+" and co_reg="+intCodRegOC;
                    stmLoc01.executeUpdate(strSql);
                    //   se quita esta opcion para que mantenga las cuentas cta en OC       agregarCambioCuentasContables(conn, intCodEmpOC, intCodLocOC, intCodTipDocOC, intCodDocOC, java.lang.Math.abs(dblBaseDePagos) );
                }

                strSql="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag) WHERE co_emp="+intCodEmp+" " +
                       " AND co_loc="+intCodLoc+" AND co_tipdoc="+objTipDoc.getco_tipdoc()+" AND co_doc="+intCodDocLoc+" AND co_reg=1";
                stmLoc01.executeUpdate(strSql);

                intEstCruAut=1;

                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;

                blnRes=true;
            }
        }
        catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }
   

    /**
     * Obtiene el maximo registro de la tabla tbm_detdia  + 1
     * @param conn    coneccion de la base
     * @param intCodEmp   codigo de la empresa
     * @param intCodLoc   codigo del local
     * @param intCodTipDoc codigo del tipo documento
     * @param intCodDoc    codigo del documento
     * @return  -1  si no se hay algun error   caso contrario retorna el valor correcto
     */
    public int _getObtenerMaxCodRegDetDia(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc )
    {
        int intCodReg=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                strSql= "SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_detdia " +
                        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_dia= "+intCodDoc+" ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                      intCodReg=rstLoc.getInt("coreg");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
        catch(Exception  Evt){ objUti.mostrarMsgErr_F1(this, Evt); }
        return intCodReg;
    }
    
    
    private ArrayList obtenerArrItmInv(Connection cnx, int intCodEmp,int intCodItm, double dblCan, int intCodBod)
    {
        String strSql="";
        Statement stmSql=null;
        ResultSet rsItmInv=null;
        ArrayList lstItmInv=new ArrayList();
        ArrayList lstItm=new ArrayList();
        try
        {
            stmSql=cnx.createStatement();
            strSql=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n" +
                   "	CASE WHEN a1.nd_cosUni IS NULL THEN \n" +
                   "		0 \n" +
                   "	ELSE \n" +
                   "		a1.nd_cosUni \n" +
                   "	END as nd_cosUni, \n" +
                   "        a1.nd_preVta1, a1.st_ivaVen, \n" +
                   "        CASE WHEN a1.tx_codAlt2 IS NULL THEN \n" +
                   "		'' \n" +
                   "	ELSE \n" +
                   "		a1.tx_codAlt2 \n" +
                   "	END as tx_codAlt2, \n" +
                   "	a2.co_itmMae, \n" +
                   "        CASE WHEN a1.co_uni IS NULL THEN \n" +
                   "		0 \n" +
                   "	ELSE \n" +
                   "		a1.co_uni \n" +
                   "	END as co_uni, \n" +
                   "        CASE WHEN a1.nd_pesItmKgr IS NULL THEN \n" +
                   "		0 \n" +
                   "	ELSE \n" +
                   "		a1.nd_pesItmKgr \n" +
                   "	END as nd_pesItmKgr , \n" +
                   "	GRU.co_itm as co_itmGru,a1.st_ivaCom, a1.st_ivaVen, a3.tx_desCor\n" +
                   " FROM tbm_inv as a1 \n" +
                   " INNER JOIN tbm_equInv as a2 \n" +
                   " ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                   " INNER JOIN tbm_equInv as GRU \n" +
                   " ON (a2.co_ItmMae=GRU.co_itmMae AND GRU.co_emp=0) \n" +
                   " LEFT OUTER JOIN tbm_var as a3 \n" +
                   " ON (a1.co_uni=a3.co_reg) \n" +
                   " WHERE a1.co_emp="+intCodEmp+" and a1.st_reg='A' AND a1.co_itm="+intCodItm;
            rsItmInv=stmSql.executeQuery(strSql);
            while (rsItmInv.next())
            {
               lstItmInv.add(rsItmInv.getString("co_itmGru"));
               lstItmInv.add(rsItmInv.getString("co_itm"));
               lstItmInv.add(rsItmInv.getString("co_itmMae"));
               lstItmInv.add(rsItmInv.getString("tx_codAlt"));
               lstItmInv.add((Double.valueOf(dblCan<0?(dblCan*-1):dblCan)).toString());
               lstItmInv.add(Integer.valueOf(intCodBod).toString());
               lstItm.add(lstItmInv);
            }         
        }
        catch(Exception ex){    ex.printStackTrace();    }
        return lstItm;
    }    
    
       
}
