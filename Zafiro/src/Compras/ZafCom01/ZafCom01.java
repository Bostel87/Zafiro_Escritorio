/*
 * zafCotCom.java 
 *   
 * Created on 6 de septiembre de 2004, 15:54
 */          

package Compras.ZafCom01;
                 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafConsulta.ZafConsulta;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTableColBut.ZafTableColBut;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafCliente_dat;
import Librerias.ZafUtil.ZafCtaCtb;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Ventas.ZafVen02.ZafVen02_02;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
/*  
 * @author  Javier Ayapata
 * COTIZACIONES DE COMPRA.
 */    

public class ZafCom01 extends JInternalFrame {
    
    private static final int INT_TBL_LINEA    = 0 ; 
    private static final int INT_TBL_ITMALT   = 1 ;
    private static final int INT_TBL_BUTITM   = 2 ;
    private static final int INT_TBL_DESITM   = 3 ;
    private static final int INT_TBL_UNIDAD   = 4 ;
    private static final int INT_TBL_CODBOD   = 5 ;
    private static final int INT_TBL_BUTBOD   = 6 ;
    private static final int INT_TBL_CANMOV   = 7 ;            //Cantidad del movimiento (venta o compra)
    private static final int INT_TBL_COSTO    = 8 ;           //Precio de Venta
    private static final int INT_TBL_PORDES   = 9 ;           //Porcentaje de descuento
    private static final int INT_TBL_BLNIVA   = 10;           //Boolean Iva
    private static final int INT_TBL_TOTAL    = 11;           //Total de la venta o compra del producto
    private static final int INT_TBL_CODITM   = 12;
    private static final int INT_TBL_ESTADO   = 13;
    private static final int INT_TBL_IVATXT   = 14;

    private ToolBar objTooBar;
    private ZafParSis objZafParSis;
    private ZafCliente_dat objCliente_dat; // Para Obtener la informacion del proveedor
    private ZafCtaCtb objCtaCtb;
    private ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafUtil objUti;
    private LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    private ZafVenCon objVenCon2; //*****************  
    private ZafVenCon objVenConTipdoc; //***************** 
    private ZafVenCon objVenConPrv; //*****************  
    private ZafVenCon objVenConVen; //*****************  
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.

    private Connection conCab;  //Coneccion a la base donde se encuentra la cotizacion
    private Statement stmCab;   //Statement para la cotizacion 
    private ResultSet rstCab;//Resultset que tendra los datos de la cabecera
    
    private JInternalFrame jfrThis; //Hace referencia a this
    
    private Vector vecForPag; //Vector que contiene el codigo del pago
    
    private String strBeforeValue,strAfterValue;
    private String sSQL,strSQL, strFiltro;//EL filtro de la Consulta actual
    private String strAux;
    private String STR_REGREP="";
    private String strCodPrv, strDesLarPrv;   
    private String strCodCom, strDesLarCom;   
    private String strTipPer_glo = ""; 
    private String strCodLoc="";
    private String strCodCot="";
    private String strItm;
    private final String strVersion = " v2.9";

    private double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    private double dblTotalCot, dblIvaCot;
    
    private boolean blnChangeData=false;
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    private boolean blnIsCosenco=false;//Conocer si la empresa por la que ingreso es COSENCO

    private final int intarreglonum[] = new int[10]; 
    private final int intarreglodia[] = new int[10]; 
    private final int intCanArr[]= new int[1];
    private int intCodBodPre=0;
    private int intNumDec= 2 ; //Numero de decimales a presentar
    private int intCodEmp=0;
    private int intEstBus=0;
    private int intIndItm =0;
    
    private final String strTit="Mensaje del Sistema Zafiro";
    
    /** Creates new form zafCotCom */
    public ZafCom01(Librerias.ZafParSis.ZafParSis obj) {
      try{  
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        jfrThis = this;

        objUti = new ZafUtil();
        objCtaCtb  = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis);
        this.setTitle(objZafParSis.getNombreMenu()+strVersion);
        
        initComponents();
        
        objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
        objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
  
         tblDat.addKeyListener(new java.awt.event.KeyAdapter(){
                @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                
               if(java.awt.event.KeyEvent.VK_F8==evt.getKeyCode()) {
                    int a = tblDat.getSelectedRow(); 
                   if(tblDat.getValueAt(a,INT_TBL_ITMALT) != null ) {
                    String ItmALt  =   tblDat.getValueAt(a,INT_TBL_CODITM).toString();
                    // Obtengo el Codigo del cliente  
                   int CodCli;
                    if(txtPrvCod.getText().equalsIgnoreCase(""))
                       CodCli=0;
                     else 
                          CodCli =  Integer.parseInt(txtPrvCod.getText());
                      
                   LlamrVentana(ItmALt,CodCli); 
               }
             }
            }});                
  
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 

        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText("");
        panCotGenNorNor.add(txtFecDoc);
        txtFecDoc.setBounds(116, 30, 92, 20);
        
        //Nombres de los tabs
        tabCotCom.setTitleAt(0,"General");
        tabCotCom.setTitleAt(1,"Forma de Pago");
        objTooBar = new ToolBar(this);
        
        vecForPag = new java.util.Vector();
        getIva();
        this.getContentPane().add(objTooBar,"South");
        pack();
        
       
        cargarTipEmpBod();
        
        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
        //Configurar_tabla();  //************
        /*
         * Listener para detectar cambios en combo de pagos
         */
         ActLisForPag objActLis = new ActLisForPag();
         cboForPag.addActionListener(objActLis);
        
        
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
 
        objUti.verDecimalesEnTabla(tblCotForPag,3, intNumDec);
        addListenerCambio();
        ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblCotForPag,0);
        tblCotForPag.getModel().addTableModelListener(new LisCambioTbl());
        
        objUti.desactivarCom(this);
   
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }         
    }

    public ZafCom01(Librerias.ZafParSis.ZafParSis obj, int intCodEmpCot, String strCodLocCot, String strCodCotCot) {
       this(obj);
       intCodEmp = intCodEmpCot;
       strCodLoc = strCodLocCot;
       strCodCot = strCodCotCot;
       intEstBus = 1;
    }  
    
     private void cargarTipEmpBod(){
        Connection conLoc;
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
         try{
            conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
             
            if (conLoc!=null){
                stmTipEmp=conLoc.createStatement();
                
                if(objZafParSis.getCodigoUsuario()==1){
                     sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod FROM  tbm_emp as a " +
                    " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
                    " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+objZafParSis.getCodigoLocal()+" and bod.st_reg='P')  " +
                    " where a.co_emp="+objZafParSis.getCodigoEmpresa();
                }
                else{
                    sSql="";
                    sSql+=" select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , a2.co_bod --,bod.co_emp, bod.co_loc \n";
                    sSql+=" FROM  tbm_emp as a \n";
                    sSql+=" left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper) \n";
                    sSql+=" INNER JOIN tbr_bodTipDocPrgUsr as a2 ON (b.co_emp=a2.co_emp AND "+objZafParSis.getCodigoLocal()+"=a2.co_loc \n";
                    sSql+=" AND a2.co_tipDoc=2 AND a2.co_mnu=35 AND a2.co_usr="+ objZafParSis.getCodigoUsuario() + ")  \n";
                    sSql+=" where a.co_emp="+ objZafParSis.getCodigoEmpresa() + " AND a2.st_reg='P' \n";
                }
               
           
                
                System.out.println(" >>>>>>>>>>>>>>>>>>.  ZafCom01.cargarTipEmpBod " + sSql);
                rstEmp = stmTipEmp.executeQuery(sSql);
                if(rstEmp.next()){
                    intCodBodPre = rstEmp.getInt("co_bod");
                }
                rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
                conLoc.close();
                conLoc=null;
            }
        }catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
        
     public void Configura_ventana_consulta(){
        configurarVenConProducto();
        configurarVenConProveedor();
        configurarVenConVendedor();
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
            strSQL+="select a.co_usr,a.tx_nom from tbm_usr as a  WHERE a.st_reg NOT IN('I')  order by a.tx_nom"; 

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
       
           
       
    private boolean configurarVenConProveedor()
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
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            String  strSQL="";
             strSQL="SELECT a.co_cli,tx_nom,tx_dir,tx_tel,tx_ide FROM tbr_cliloc as a1  " +
             " INNER JOIN tbm_cli AS a on (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli) " +
             " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc="+objZafParSis.getCodigoLocal()+" and a.st_reg IN('A','N') AND  a.st_prv='S' order by a.tx_nom ";   
            objVenConPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
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
        String Str_Sql="";
        try
        {
             
             //********************************************************************************** 
            
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.co_itm");
            arlCam.add("a7.tx_nomItm");
            arlCam.add("a7.nd_stkAct");
            arlCam.add("a7.nd_CosUni");
            arlCam.add("a7.st_ivaCom");
            arlCam.add("a7.tx_descor");
            arlCam.add("a7.st_ser");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cód.Sis.");
            arlAli.add("Nombre");
            arlAli.add("Stock");
            arlAli.add("Costo");
            arlAli.add("Iva.");
            arlAli.add("Uni.");
            arlAli.add("Servicio");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("210");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("35");
            arlAncCol.add("40");
            arlAncCol.add("60");
              
              
            Str_Sql = objInvItm.getSqlInvCom();
                  
             //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=7;
               
            objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenCon2.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            objVenCon2.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
            
            
        }catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
        
    }
     
        
       
      public void  LlamrVentana(String codalt,int codcli) {
        Compras.ZafCom01.ZafCom01_01 obj1 = new  Compras.ZafCom01.ZafCom01_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        
        
           String  strSQL="";
           int intCodMod=2;
               strSQL+="select distinct(b.co_doc),a.ne_numcot,a.ne_numdoc,a.tx_nomcli,a.fe_doc,b.co_tipdoc,  b.nd_can  as can, b.nd_cosuni,b.nd_pordes"; 
               strSQL+=" from tbm_detmovinv as b,tbm_cabmovinv as a,tbm_cabtipdoc as x";
               strSQL+=" where";  
               strSQL+=" x.ne_mod="+ intCodMod +" and b.co_tipdoc = x.co_tipdoc  and ";
               strSQL+=" b.co_itm= '"+ codalt +"' and b.co_emp="+ objZafParSis.getCodigoEmpresa() +" "; // and b.co_loc= "+ objZafParSis.getCodigoLocal();
               strSQL+=" and a.co_doc = b.co_doc and a.co_emp = b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc = b.co_tipdoc "; 
               strSQL+=" and a.st_reg IN ('A','R','D','C','F') ";
               
               if(codcli != 0 )
                   strSQL+=" and  a.co_cli = "+ codcli;   
               
               strSQL+= " order by a.fe_doc";
     
        System.out.println("ZafCom01.LlamrVentana " + strSQL );
        obj1.cargaTexto(strSQL,codcli);
        obj1.show();
        obj1.dispose();
        obj1=null;
     }
       
      
      
      
        
      
    /*
     * MODIFICADO EFLORESA 2012-05-09
     * Se modifica para que en COSENCO se permita hacer la cotizacion de items terminal L 
     */

   private boolean Configurar_tabla() {
        boolean blnRes=false;
        try{
           
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
            vecCab.add(INT_TBL_CANMOV,"Cantidad");
            vecCab.add(INT_TBL_COSTO,"Costo");            
            vecCab.add(INT_TBL_PORDES,"Descuento");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");            
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ESTADO,"");
            vecCab.add(INT_TBL_IVATXT,"");
            
            objTblMod=new ZafTblMod(); 
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(false);
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_COSTO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
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
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(145);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(25);         
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_COSTO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(80);         
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
                                
            
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_ITMALT);
            vecAux.add("" + INT_TBL_BUTITM);
            //vecAux.add("" + INT_TBL_CODBOD);
            vecAux.add("" + INT_TBL_BUTBOD);
            vecAux.add("" + INT_TBL_CANMOV);
            vecAux.add("" + INT_TBL_COSTO);
            vecAux.add("" + INT_TBL_PORDES);
            vecAux.add("" + INT_TBL_BLNIVA);
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
                    if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA) != null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA).toString();
                   
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }        
                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                     if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA) != null)
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
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_COSTO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.        
            
            int intColVen2[]=new int[6];
            intColVen2[0]=1;
            intColVen2[1]=3;
            intColVen2[2]=5;
            intColVen2[3]=2;            
            intColVen2[4]=6;          
            intColVen2[5]=7;   
            int intColTbl2[]=new int[6];
            intColTbl2[0]=INT_TBL_ITMALT;
            intColTbl2[1]=INT_TBL_DESITM;
            intColTbl2[2]=INT_TBL_COSTO;
            intColTbl2[3]=INT_TBL_CODITM;
            intColTbl2[4]=INT_TBL_IVATXT;
            intColTbl2[5]=INT_TBL_UNIDAD;            

              objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, objVenCon2, intColVen2, intColTbl2);  //********
              tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);  //******
              
              objTblCelEdiTxtVcoItm.addTableEditorListener(new ZafTableAdapter() {   //******  
                @Override
              public void beforeEdit(ZafTableEvent evt){
                    blnChangeData = false;                            
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else 
			strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else
                        strAfterValue ="";
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
                    
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_IVATXT)!=null){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_IVATXT).equals("S"))
                            tblDat.setValueAt(true, tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                        else
                            tblDat.setValueAt(false, tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                    }
                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;        
                        tblDat.setValueAt( ""+intCodBodPre , tblDat.getSelectedRow(), INT_TBL_CODBOD);
                        calculaSubtotal();
                    }     
                    /*
                     * EFLORESA 2012-05-07
                     * Se modifica para que no se permita hacer cotizaciones/ordenes de compra para items terminal L              
                     * MODIFICADO EFLORESA 2012-05-09
                     * Se modifica para que en COSENCO se permita hacer la cotizacion de items terminal L 
                     */                    
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null){
                        strItm = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                        //intIndItm = strItm.indexOf("L");
                        //if (intIndItm > -1 && !blnIsCosenco){
                        if (strItm.endsWith("L") && !blnIsCosenco){
                            MensajeInf("No se puede ingresar una cotizacion de compra de items con terminal L");
                            objTblMod.removeRow(tblDat.getSelectedRow());  
                        }
                    }
                }
            });
              
            ButFndItm butFndItm = new ButFndItm(tblDat, INT_TBL_BUTITM); //*****
            
            intColVen2=null;
            intColTbl2=null;
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_COSTO).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_COSTO).setCellRenderer(new RenderDecimales(intNumDec) );
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    blnChangeData = false;
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strAfterValue ="";
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
                    if (tblDat.getSelectedColumn()==INT_TBL_PORDES){
                        if(strAfterValue.equals("")) strAfterValue="0.00"; 
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            MensajeInf("Error! Porcentaje de Descuento ");
                            tblDat.setValueAt(strBeforeValue, tblDat.getSelectedRow(), INT_TBL_PORDES);
                        }
                    }
                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true; 
                        calculaSubtotal();
                    }                                        
                }
            });
            
            ButBodEmp butBodEmp = new ButBodEmp(tblDat, INT_TBL_BUTBOD);
              
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
            //Configurar JTable: Centrar columnas.
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //System.out.println("Empresa logueada : " + objZafParSis.getNombreEmpresa());
            //Saber si la empresa que ingreso es COSENCO
            blnIsCosenco = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;
            
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;                        
    }  
   
private class ButBodEmp extends ZafTableColBut{
 public ButBodEmp(JTable tbl, int intIdx){
 super(tbl,intIdx);
 }
 
 @Override
 public void butCLick() {
  int intNumFil = tblDat.getSelectedRow();
  if(intNumFil >= 0 ) {
    blnChangeData = false;
    String strCodItm = ((tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_CODITM).toString());
    llamarVentanaFac(intNumFil, strCodItm);
     
}}}


private void llamarVentanaFac(int intCol, String strCodItm){
  String strSql="";
  
  if(!strCodItm.equals("")){    
  
  if(objZafParSis.getCodigoUsuario()==1){
      strSql="select a.co_bod, a1.tx_nom, a.nd_stkact from tbm_invbod as a " +
      " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)  " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm="+strCodItm+" order by a.co_bod";
  }
  else{  // José Marín M. 5/Nov/2014 --> Solicitado por Ingrid Lino, por cambio en manejo de OC 
      strSql="";
      strSql+=" SELECT a1.co_emp, a.co_bod, a1.tx_nom, a.nd_stkact \n";
      strSql+=" FROM tbm_invbod as a \n";
      strSql+=" INNER JOIN tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) \n";
      strSql+=" INNER JOIN tbr_bodTipDocPrgUsr as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod AND  \n";
      strSql+="                                          a2.co_tipDoc=2 AND a2.co_mnu=35 AND a2.co_usr="+ objZafParSis.getCodigoUsuario() +") \n";
      strSql+=" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm="+strCodItm+"  \n";
      strSql+=" ORDER BY a.co_bod \n";
  }  // José Marín M. 5/Nov/2014 --> Solicitado por Ingrid Lino, por cambio en manejo de OC 
  System.out.println("-------ZafCom01.llamarVentanaFac " + strSql);
   ZafVen02_02 obj = new  ZafVen02_02(JOptionPane.getFrameForComponent(this), true,  objZafParSis, strSql);
   obj.show();  
       if(obj.acepta()){ 
           tblDat.setValueAt(obj.GetCamSel(1), intCol, INT_TBL_CODBOD);
        }     
    obj.dispose();
    obj=null;
    
   }else 
        Mensaje("AGREGE UN ITEM EN LA COTIZACIÓN. ");
}


private void Mensaje(String strNomCampo){
        //JOptionPane obj =new JOptionPane();
        /*String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";*/
        String strMsg=strNomCampo;
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
         
   public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
            
        }else{
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }            
   }

   
    public void calculaSubtotal(){
        double dblCan,dblDes,dblCosto,dblTotal;
        int intNumFil = tblDat.getSelectedRow();
        if (tblDat.getValueAt( intNumFil , INT_TBL_CANMOV)==null)
            tblDat.setValueAt("0",intNumFil, INT_TBL_CANMOV);
        if (tblDat.getValueAt(intNumFil, INT_TBL_COSTO)==null)
            tblDat.setValueAt("0",intNumFil, INT_TBL_COSTO);
        if (tblDat.getValueAt(intNumFil, INT_TBL_PORDES)==null)
            tblDat.setValueAt("0",intNumFil, INT_TBL_PORDES);
        dblCan = Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_CANMOV).toString());
        dblCosto = Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_COSTO).toString());
        if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_PORDES).toString())/100);
        else dblDes = 0;
        
        dblTotal = (dblCan * dblCosto)- dblDes ;
        dblTotal = objUti.redondear(dblTotal,3);
        dblTotal = objUti.redondear(dblTotal,objZafParSis.getDecimalesMostrar());
        
        tblDat.setValueAt(""+dblTotal,intNumFil, INT_TBL_TOTAL);
//        tblDat.setValueAt(String.valueOf(objUti.redondear(((dblCan * dblCosto )-dblDes), objZafParSis.getDecimalesMostrar())),intNumFil, INT_TBL_TOTAL);
        calculaTotal();
        calculaPagos();             
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
        txtComCod = new javax.swing.JTextField();
        txtComNom = new javax.swing.JTextField();
        butVenCon = new javax.swing.JButton();
        txtCot = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt = new javax.swing.JTextArea();
        panCotGenNorSur = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtPrvDir = new javax.swing.JTextField();
        txtPrvCod = new javax.swing.JTextField();
        txtPrvNom = new javax.swing.JTextField();
        butCliCon = new javax.swing.JButton();
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
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        tabCotCom.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabCotCom.setName("General"); // NOI18N

        panCotGen.setLayout(new java.awt.BorderLayout());

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 90));
        panCotGenNor.setLayout(new java.awt.BorderLayout());

        panCotGenNorNor.setPreferredSize(new java.awt.Dimension(600, 50));
        panCotGenNorNor.setLayout(null);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCom.setText("Comprador:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblCom);
        lblCom.setBounds(220, 10, 70, 15);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNorNor.add(lblFecDoc);
        lblFecDoc.setBounds(6, 30, 108, 15);
        panCotGenNorNor.add(txtAte);
        txtAte.setBounds(294, 30, 144, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte);
        lblAte.setBounds(220, 30, 60, 15);

        txtComCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtComCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtComCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtComCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComCodActionPerformed(evt);
            }
        });
        txtComCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComCodFocusLost(evt);
            }
        });
        panCotGenNorNor.add(txtComCod);
        txtComCod.setBounds(294, 10, 35, 20);

        txtComNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtComNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtComNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComNomActionPerformed(evt);
            }
        });
        txtComNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComNomFocusLost(evt);
            }
        });
        panCotGenNorNor.add(txtComNom);
        txtComNom.setBounds(328, 10, 135, 20);

        butVenCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butVenCon.setText("...");
        butVenCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butVenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenConActionPerformed(evt);
            }
        });
        panCotGenNorNor.add(butVenCon);
        butVenCon.setBounds(465, 10, 20, 20);

        txtCot.setBackground(objZafParSis.getColorCamposSistema()
        );
        objZafParSis.getColorCamposObligatorios();
        txtCot.setMaximumSize(null);
        txtCot.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtCot);
        txtCot.setBounds(116, 10, 92, 20);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel1.setText("No. Cotización:");
        panCotGenNorNor.add(jLabel1);
        jLabel1.setBounds(6, 12, 110, 15);
        panCotGenNorNor.add(txt);
        txt.setBounds(520, 50, 50, 60);

        panCotGenNor.add(panCotGenNorNor, java.awt.BorderLayout.NORTH);

        panCotGenNorSur.setPreferredSize(new java.awt.Dimension(600, 55));
        panCotGenNorSur.setLayout(null);

        lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPrv.setText("Proveedor:");
        panCotGenNorSur.add(lblPrv);
        lblPrv.setBounds(6, 0, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Dirección:");
        panCotGenNorSur.add(lblDir);
        lblDir.setBounds(6, 20, 60, 15);

        txtPrvDir.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtPrvDir.setPreferredSize(new java.awt.Dimension(70, 20));
        txtPrvDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvDirActionPerformed(evt);
            }
        });
        txtPrvDir.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPrvDirCaretUpdate(evt);
            }
        });
        panCotGenNorSur.add(txtPrvDir);
        txtPrvDir.setBounds(116, 20, 350, 20);

        txtPrvCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPrvCod.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtPrvCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtPrvCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtPrvCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvCodActionPerformed(evt);
            }
        });
        txtPrvCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrvCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrvCodFocusLost(evt);
            }
        });
        panCotGenNorSur.add(txtPrvCod);
        txtPrvCod.setBounds(116, 0, 35, 20);

        txtPrvNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPrvNom.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtPrvNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtPrvNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvNomActionPerformed(evt);
            }
        });
        txtPrvNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrvNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrvNomFocusLost(evt);
            }
        });
        txtPrvNom.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtPrvNomVetoableChange(evt);
            }
        });
        panCotGenNorSur.add(txtPrvNom);
        txtPrvNom.setBounds(150, 0, 315, 20);

        butCliCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCliCon.setText("...");
        butCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butCliCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliConActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(butCliCon);
        butCliCon.setBounds(465, 0, 22, 20);

        panCotGenNor.add(panCotGenNorSur, java.awt.BorderLayout.CENTER);

        panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Linea", "Codigo", "...", "Descripción", "Unidad", "Cantidad", "Precio", "%Desc", "Iva", "Total", "Codigo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.Short.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, true, true, true, false, false
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
        tblDat.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        panCotGen.add(spnCon, java.awt.BorderLayout.CENTER);

        panCotGenSur.setLayout(new java.awt.BorderLayout());

        panCotGenSurCen.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observación 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
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
                {null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "Monto de Pago", "Días de gracia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
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
        panCotForPagNorCen.add(cboForPag);

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
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtComCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComCodFocusGained
        // TODO add your handling code here:
        strCodCom=txtComCod.getText();
        txtComCod.selectAll();
    }//GEN-LAST:event_txtComCodFocusGained

    private void txtComNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComNomFocusGained
        // TODO add your handling code here:
        strDesLarCom=txtComNom.getText();
        txtComNom.selectAll();
    }//GEN-LAST:event_txtComNomFocusGained

    private void txtPrvNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvNomFocusGained
        // TODO add your handling code here:
        strDesLarPrv=txtPrvNom.getText();
        txtPrvNom.selectAll();
    }//GEN-LAST:event_txtPrvNomFocusGained

    private void txtPrvCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvCodFocusGained
        // TODO add your handling code here:
        strCodPrv=txtPrvCod.getText();
        txtPrvCod.selectAll();
    }//GEN-LAST:event_txtPrvCodFocusGained

    private void txtPrvDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrvDirActionPerformed

    private void txtComCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComCodFocusLost
      
          if (!txtComCod.getText().equalsIgnoreCase(strCodCom))
         {
            if (txtComCod.getText().equals(""))
            {
                txtComCod.setText("");
                txtComNom.setText("");
            }
            else
            {
                BuscarVendedor("a.co_usr",txtComCod.getText(),0);
            }
        }
        else
            txtComCod.setText(strCodCom);
       
    }//GEN-LAST:event_txtComCodFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
          Configurar_tabla();

          if(intEstBus==1){
              
             txtCot.setText(strCodCot);
            
             objTooBar.consultar();


          }

    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        // TODO add your handling code here:
          String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        /*String strTit;
        strTit="Mensaje del sistema Zafiro";*/
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
            cerrarObj();
            Runtime.getRuntime().gc();
            dispose();
        
        }  
    }//GEN-LAST:event_CerrarVentana

    
    
      public void cerrarObj(){
        try{
          objVenCon2.dispose(); //***
          objVenCon2=null;  //****
          objVenConVen.dispose();
          objVenConVen=null;  
         
         objUti=null;
         objTooBar=null;
        
         objlisCambios=null;
         objZafParSis=null;
         txtFecDoc=null;
         objTblCelEdiTxtVcoItm=null;
//         System.out.println("CERRANDO LOS OBJETOS..");
        }
         catch (Exception e)
         {
          objUti.mostrarMsgErr_F1(this, e);
         }                   
     }
    
        
    
    private void txtPrvNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvNomFocusLost
        // TODO add your handling code here:
         if (!txtPrvNom.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtPrvNom.getText().equals(""))
            {
                txtPrvCod.setText("");
                txtPrvNom.setText("");
            }
            else
            {
                BuscarProveedor("a.tx_nom",txtPrvNom.getText(),1);
            }
        }
        else
            txtPrvNom.setText(strDesLarPrv);
             
    }//GEN-LAST:event_txtPrvNomFocusLost

    private void txtComNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComNomFocusLost
        // TODO add your handling code here:
          if (!txtComNom.getText().equalsIgnoreCase(strDesLarCom))
         {
            if (txtComNom.getText().equals(""))
            {
                txtComCod.setText("");
                txtComNom.setText("");
            }
            else
            {
               BuscarVendedor("a.co_usr","",0);
            }
        }
        else
            txtComNom.setText(strDesLarCom);
       
    }//GEN-LAST:event_txtComNomFocusLost

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtPrvDirCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPrvDirCaretUpdate

    }//GEN-LAST:event_txtPrvDirCaretUpdate

    private void txtPrvNomVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtPrvNomVetoableChange
        // Prueba
    }//GEN-LAST:event_txtPrvNomVetoableChange

    private void txtPrvCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvCodFocusLost
      
        if (!txtPrvCod.getText().equalsIgnoreCase(strCodPrv))
        {
            if (txtPrvCod.getText().equals(""))
            {
                txtPrvCod.setText("");
                txtPrvNom.setText("");
              
            }
            else
            {
                BuscarProveedor("a.co_cli",txtPrvCod.getText(),0);
            }
        }
        else
            txtPrvCod.setText(strCodPrv);
        
        
      
    }//GEN-LAST:event_txtPrvCodFocusLost

    private void txtPrvNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvNomActionPerformed
       txtPrvNom.transferFocus();
   
    }//GEN-LAST:event_txtPrvNomActionPerformed

    private void txtComCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComCodActionPerformed
        txtComCod.transferFocus();
       
    }//GEN-LAST:event_txtComCodActionPerformed
     public void BuscarVendedor(String campo,String strBusqueda,int tipo){
        objVenConVen.setTitle("Listado de Compradores"); 
        if (objVenConVen.buscar(campo, strBusqueda ))
        {
            txtComCod.setText(objVenConVen.getValueAt(1));
            txtComNom.setText(objVenConVen.getValueAt(2));
        }
        else
        {     objVenConVen.setCampoBusqueda(tipo);
              objVenConVen.cargarDatos();
              objVenConVen.show();
             if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
             {
                txtComCod.setText(objVenConVen.getValueAt(1));
                txtComNom.setText(objVenConVen.getValueAt(2));
             }
              else{
                    txtComCod.setText(strCodCom);
                    txtComNom.setText(strDesLarCom);
                  }
        }
    }
    
    
    public void BuscarProveedor(String campo,String strBusqueda,int tipo){
        objVenConPrv.setTitle("Listado de Clientes"); 
        if (objVenConPrv.buscar(campo, strBusqueda ))
        {
              txtPrvCod.setText(objVenConPrv.getValueAt(1));
              txtPrvNom.setText(objVenConPrv.getValueAt(2));
              txtPrvDir.setText(objVenConPrv.getValueAt(3));
              FndVenToCli();
        }
        else
        {     objVenConPrv.setCampoBusqueda(tipo);
              objVenConPrv.cargarDatos();
              objVenConPrv.show();
             if (objVenConPrv.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtPrvCod.setText(objVenConPrv.getValueAt(1));
                txtPrvNom.setText(objVenConPrv.getValueAt(2));
                txtPrvDir.setText(objVenConPrv.getValueAt(3));
                if (txtComCod.getText().equals("")) FndVenToCli(); 
             }
              else{
                    txtPrvCod.setText(strCodPrv);
                    txtPrvNom.setText(strDesLarPrv);
              }

   }
 }    
  
  
  
    private void txtPrvCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvCodActionPerformed
      txtPrvCod.transferFocus();
     
    }//GEN-LAST:event_txtPrvCodActionPerformed

    
    private void txtComNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComNomActionPerformed
       txtComNom.transferFocus();
        
    }//GEN-LAST:event_txtComNomActionPerformed
    
    private void FndVenToCli(){
      
               
               String strSql = "select * from tbm_cli where " +
                               "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                               "co_cli = " + Integer.parseInt(txtPrvCod.getText());

               try{
                    /*
                     * Obteniendo el maximo porcentaje de descuento para el cliente 
                     */
                   Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   if (con!=null)
                   {
                        Statement stmCliVen = con.createStatement();
                        ResultSet rstCliVen = stmCliVen.executeQuery(strSql);

                        if(rstCliVen.next()){
                            int intCodVen  = rstCliVen.getInt("co_ven");
                            txtComCod.setText(rstCliVen.getString("co_ven"));
                            Librerias.ZafConsulta.ZafConsulta  objFndVen = 


                             new ZafConsulta( JOptionPane.getFrameForComponent(this),
                               "Codigo,Nombre","co_usr,tx_nom",
                               " Select co_usr,tx_nom from tbm_usr  ", txtComCod.getText(), 
                                 objZafParSis.getStringConexion(), 
                                 objZafParSis.getUsuarioBaseDatos(), 
                                 objZafParSis.getClaveBaseDatos()
                                 );
                            objFndVen.setTitle("Listado vendedores");

                            if(objFndVen.buscar("co_usr = " + intCodVen )){
                                txtComCod.setText(objFndVen.GetCamSel(1));
                                txtComNom.setText(objFndVen.GetCamSel(2));
                            }    
                        }
                        stmCliVen.close();
                        rstCliVen.close();
                        con.close();
                   }
               }catch(SQLException Evt){
                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
               }catch(Exception Evt){
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
            txtPrvCod.getDocument().addDocumentListener(objlisCambios);
            txtPrvNom.getDocument().addDocumentListener(objlisCambios);
            txtPrvDir.getDocument().addDocumentListener(objlisCambios);
            //txtFecDoc.getDocument().addDocumentListener(objlisCambios);
            txtComCod.getDocument().addDocumentListener(objlisCambios);
            txtComNom.getDocument().addDocumentListener(objlisCambios);
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
    class LisTextos implements javax.swing.event.DocumentListener {
        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
    }        
        
    class LisCambioTbl implements TableModelListener{
        @Override
        public void tableChanged(TableModelEvent e){
            //Cambiaron los datos en jtable
            calculaTotal();
        }
    }
    
    private void butVenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenConActionPerformed
         BuscarVendedor("a.co_usr","",0);
    }//GEN-LAST:event_butVenConActionPerformed

    private void butCliConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliConActionPerformed
        ////FndProveedor("",0);
         objVenConPrv.setTitle("Listado de Clientes");         
              objVenConPrv.setCampoBusqueda(1);
              objVenConPrv.show();
             if (objVenConPrv.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
             {
                txtPrvCod.setText(objVenConPrv.getValueAt(1));
                txtPrvNom.setText(objVenConPrv.getValueAt(2));
                txtPrvDir.setText(objVenConPrv.getValueAt(3));
                if (txtComCod.getText().equals("")) FndVenToCli();
             } 
              
    }//GEN-LAST:event_butCliConActionPerformed

    /*
   * Obtiene el Iva que se debe cobrar en la empresa actual
   */
    private void getIva(){
        dblPorIva = objCtaCtb.getPorcentajeIvaCompras();
        lblIva.setText("IVA " + dblPorIva + "%");
    }
    
   
    
    public void  noEditable(boolean blnEditable){
        java.awt.Color colBack = txtCot.getBackground();

            txtCot.setEditable(blnEditable);
            txtCot.setBackground(colBack);
            
            colBack = txtSub.getBackground();      

//            txtSub.setEnabled(blnEditable);
            txtSub.setEditable(blnEditable);
            txtSub.setBackground(colBack);        
            
            txtIva.setEditable(blnEditable);
            txtIva.setBackground(colBack);        
            
            txtTot.setEditable(blnEditable);
            txtTot.setBackground(colBack);        
    }
  
    
    public void  clnTextos(){
        //Cabecera
              strCodPrv="";
              strDesLarPrv="";   
              strCodCom="";
              strDesLarCom="";
              
            txtCot.setText("");
            txtPrvCod.setText("");
            txtPrvNom.setText("");
            txtPrvDir.setText("");
            txtFecDoc.setText("");
            txtComCod.setText("");
            txtComNom.setText("");
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
            lblCotNumDes.setText("Cotizaciones");

         //Detalle  de PAGOS
            while(tblCotForPag.getRowCount()>0)
                ((DefaultTableModel)tblCotForPag.getModel()).removeRow(0);
//            cboForPag.setSelectedIndex(-1);
//            cboForPag.removeAllItems();
            

    }
   
   public void  calculaTotal(){
//        double dblSubtotalCot, dblTotalDoc, dblIvaCot;
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0;
        for (int i=0;i<tblDat.getRowCount();i++){ 
            dblSub += objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesMostrar());  
            
            if(tblDat.getValueAt(i, INT_TBL_BLNIVA)!=null){
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesMostrar()): 0 );
                dblIva += (((dblTmp * dblPorIva)==0)?0:dblTmp * (dblPorIva/100)) ;
            }
        }
                    
        /*txtSub.setText( "" + objUti.redondeo(dblSub,intNumDec) );
        txtIva.setText( "" + objUti.redondeo(dblIva,intNumDec) );
        txtTot.setText( "" + objUti.redondeo(dblSub + dblIva ,intNumDec)   );*/
                    
        txtSub.setText( "" + objUti.redondear(dblSub,intNumDec) );
        txtIva.setText( "" + objUti.redondear(dblIva,intNumDec) );
        txtTot.setText( "" + objUti.redondear(dblSub + dblIva ,intNumDec)   );
    }   
 
    public void  refrescaDatos(){
        try{//odbc,usuario,password        
            int intNumCot = 0;
            Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if(rstCab != null){
                //Detalle        
                    
                  //Extrayendo los datos del detalle respectivo a esta cotizacion
                    sSQL  = "Select * from tbm_detCotCom where " +
                            " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                            " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                            " co_cot = " + rstCab.getInt("co_cot")         + " order by co_reg";
                    
                    Statement stmCab=con.createStatement();

                    ResultSet rst = stmCab.executeQuery(sSQL);
                     double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                                           
                    Statement stmAux;
                    ResultSet rstAux;
                    stmAux = con.createStatement();
                    String strUnidad="",strCodAlt="";
                    Vector vecData = new Vector();
                    vecData.clear();
                    for(int i=0 ; rst.next() ; i++){
                         java.util.Vector vecReg = new java.util.Vector();
                         
                         strSQL = "";
                         strSQL = " Select tx_codalt,tx_descor from tbm_detcotcom as detcot left outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) left outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                                 " where detcot.co_emp  = " + objZafParSis.getCodigoEmpresa() +" and detcot.co_loc = " + objZafParSis.getCodigoLocal() + " and detcot.co_cot = " + rst.getInt("co_cot") +" and detcot.co_itm = " + rst.getString("co_itm") ;                        
                         rstAux = stmAux.executeQuery(strSQL);
                         if (rstAux.next())
                         {
                             strCodAlt = (rstAux.getString("tx_codalt")==null?"":rstAux.getString("tx_codalt"));
                             strUnidad = (rstAux.getString("tx_descor")==null?"":rstAux.getString("tx_descor"));
                         }
                         rstAux.close();

                         vecReg.add(INT_TBL_LINEA, "");
                         vecReg.add(INT_TBL_ITMALT, strCodAlt);
                         vecReg.add(INT_TBL_BUTITM, "");
                         vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                         vecReg.add(INT_TBL_UNIDAD, strUnidad);
                         vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                         vecReg.add(INT_TBL_BUTBOD, "");
                         vecReg.add(INT_TBL_CANMOV, objUti.redondear(objUti.parseDouble(rst.getDouble("nd_can")),2) );
                         vecReg.add(INT_TBL_COSTO, objUti.redondear(objUti.parseDouble(rst.getDouble("nd_cosuni")),2) );
                         vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                         String strIva = rst.getString("st_ivacom");
                         Boolean blnIva = strIva.equals("S");                         
                         vecReg.add(INT_TBL_BLNIVA, blnIva);
                         dblCan    = rst.getDouble("nd_can");
                         dblPre    = rst.getDouble("nd_cosuni");
                         dblPorDes = rst.getDouble("nd_pordes");
                         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
//                         dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;
                         
                         dblTotal = (dblCan * dblPre)- dblValDes ;
                         dblTotal = objUti.redondear(dblTotal,3);
                         dblTotal = objUti.redondear(dblTotal,objZafParSis.getDecimalesMostrar());
                         
                         vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                         vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                         vecReg.add(INT_TBL_ESTADO, "E");
                         vecReg.add(INT_TBL_IVATXT, "");
                         vecData.add(vecReg);                         
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);                                 
                   
                    rst.close();
                    calculaTotal();
                    lblCotNumDes.setText("Cotización No. " + txtCot.getText() +  " (" + txtPrvNom.getText() + ") ");
                    lblIva.setText("IVA " + dblPorIva + "%");                    
                    
                /** CARGANDO DATOS DEL TAB FORMA DE PAGO  */                     

                    String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;                          
                    
                    //Extrayendo los datos del detalle respectivo a ESTE PAGO
                    sSQL  = "Select * from tbm_pagCotCom where " +
                            " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                            " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                            " co_cot = " + intNumCot                       + " order by co_reg";
                    
                    rst = stmAux.executeQuery(sSQL);                    
                    /*
                     * LLenando el combo de descripciones de pagos 
                     * 
                     */
                    objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                            objZafParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag, strCo_ForPag);      
                    calculaPagos();                                    
                    
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
                            
                        }
                    }
                    
                     noEditable(false);
                     
                    stmAux.close();
                    stmAux=null;
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
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
//                objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                refrescaDatos();                
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
    
    
    
    
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                Statement stm=con.createStatement();
                String strSQL="";
                strSQL= "SELECT cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                      " Usr.co_usr as co_com, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                      " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                      " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                      " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                      " CotCab.st_reg , CotCab.st_regrep" + // Campo para saber si esta anulado o no
                      " FROM tbm_cabCotCom as CotCab left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_prv = cli.co_cli) left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_com ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                      " Where  CotCab.co_emp=" + rstCab.getString("co_emp") +
                      " AND CotCab.co_loc=" + rstCab.getString("co_loc") +
                      " AND CotCab.co_cot=" + rstCab.getString("co_cot") ;
//                System.out.println(strSQL);
                ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCot.setText(rst.getString("co_cot"));                   
                    txtPrvCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtPrvNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtPrvDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    
                    STR_REGREP=rst.getString("st_regrep");
                    
                    if(rst.getDate("fe_cot")==null){
                      txtFecDoc.setText("");  
                    }else{
                        java.util.Date dateObj = rst.getDate("fe_cot");
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH)+1, calObj.get(Calendar.YEAR) );
                    }

                    txtComCod.setText(((rst.getString("co_com")==null)?"":rst.getString("co_com")));
                    txtComNom.setText(((rst.getString("nomcom")==null)?"":rst.getString("nomcom")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
                    double dblSub = ((rst.getString("nd_sub")==null)?0:objUti.redondear(rst.getDouble("nd_sub"),objZafParSis.getDecimalesMostrar()));
                    txtSub.setText(""+dblSub);
                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    
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

    public class ToolBar extends ZafToolBar{
        public ToolBar(JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
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
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        @Override
        public void clickAceptar()
        {
            
        }

        @Override
        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
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

        @Override
        public void clickAnular()
        {
            setEditable(false);
        }
   

        @Override
        public void clickConsultar() 
        {
            txtComCod.setEnabled(true);
            setEditable(false);
        }

        @Override
        public void clickEliminar()
        {
            setEditable(false);
        }

        @Override
        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
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
   
        @Override
        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
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

        
        
        @Override
        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                clnTextos();
                noEditable(false);                
                txtFecDoc.setHoy();
                objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                     objZafParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);      
                cboForPag.setSelectedIndex(0);
                setEditable(true);
                blnHayCam=false;
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
  
        @Override
        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
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
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
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
        {
            if (!validaCampos())
                return false;
            if (!insertarReg())
                return false;
            blnHayCam=false;
            return true;
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
            if (!validaCampos())
                return false;
            
            if (!actualizarReg())
                return false;
            blnHayCam=false;
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
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
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
            cargarRegistroInsert();
            return true;
        }
        
        @Override
        public boolean afterModificar() {
            return true;
        }
        
        @Override
        public boolean afterVistaPreliminar() {
            return true;
        }
            
//        public boolean imprimir2() {
//            Librerias.ZafRpt.ZafRptXLS.ZafRptXLS objPRINT  = new Librerias.ZafRpt.ZafRptXLS.ZafRptXLS(objZafParSis);
//            if(!txtCot.getText().equals("") ){
//                int intCoDoc    = Integer.parseInt(txtCot.getText());
//                objPRINT.GenerarDoc( -2 , intCoDoc);  
//            }
//            objPRINT = null;
//            return true;
//        }
        
        //public boolean vistaPreliminar() {
          //  return true;
        //}                       
            
        public boolean insertarReg(){
            Connection conCot=null;
            PreparedStatement pstReg=null;
            ResultSet rstNum=null;
            Statement stmCot=null;
                    /*
                     * Esto Hace en caso de que el modo de operacion sea Nuevo
                     */
                          //VAlidando los campos de la cabecera
                          if(!validaCampos())
                              return false;
                           try{//odbc,usuario,password
                                   conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                                   try{
                                    if (conCot!=null){
                                        conCot.setAutoCommit(false);
                                        
                                        getIva();

                                        int intNumCot = 0;
                                        stmCot=conCot.createStatement();

                                        /*
                                         * Convirtiendo la fecha en formato aaaa/mm/dd
                                         * para poder grabarlo en la base
                                         */
                                        int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                                        String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";                                        

                                        //Verificando que si es ke la tabla esta vacia 
                                        sSQL= "SELECT Max(co_cot) as co_cot FROM tbm_cabCotCom where " +
                                              " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                              " and co_loc = " + objZafParSis.getCodigoLocal() ;
                                        rstNum = stmCot.executeQuery(sSQL);


                                        if(rstNum.next())
                                            intNumCot = rstNum.getInt("co_cot");
                                        intNumCot++;
                                        
                                        /*********************************************\    
                                         * Armando el Insert para los datos          *   
                                         * de la cabecera de cotizacion              *
                                        \*********************************************/

                                     String   strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                               
                                        sSQL= "INSERT INTO  tbm_cabCotCom" +
                                               "(co_emp, co_loc, co_cot, " + //CAMPOS PrimayKey
                                               " fe_cot, "                 + //Fecha de la cotizacion
                                               " co_prv, co_com, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                               " tx_obs1, tx_obs2, nd_sub, nd_valiva, nd_tot, nd_porIva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                               " co_forPag ,st_regrep " + 
                                               " ,fe_ing, fe_ultmod, co_usring , co_usrmod )" +
                                               "VALUES (" +
                                                objZafParSis.getCodigoEmpresa() + ", " +
                                                objZafParSis.getCodigoLocal()   + ", " + 
                                                intNumCot + ", '" + 
                                                strFecha + "', " +
                                                txtPrvCod.getText() + ",   " +
                                                txtComCod.getText() + ",  '" +
                                                txtAte.getText()    + "', '" + 
                                                txaObs1.getText()   + "', '" + 
                                                txaObs2.getText()   + "',  " + 
                                                objUti.redondear(txtSub.getText(),objZafParSis.getDecimalesBaseDatos())    + ",   " +
                                                txtIva.getText()  + ",   " +
                                                txtTot.getText()  + ",   " +
                                                objUti.redondear(dblPorIva , objZafParSis.getDecimalesMostrar())  + " , " +
                                                vecForPag.get(cboForPag.getSelectedIndex()) + ", 'I'," +
                                                "'"+strFecSis+"','"+strFecSis+"',"+objZafParSis.getCodigoUsuario()+","+objZafParSis.getCodigoUsuario()+")";

                                            /* Ejecutando el insert */
                                            pstReg = conCot.prepareStatement(sSQL);
                                            pstReg.executeUpdate();

    
                                        /*********************************************\    
                                         * Armando el Insert para los datos          *   
                                         * del detalle de cotizacion                 *
                                        \*********************************************/

                                        sSQL= "INSERT INTO  tbm_detCotCom" +
                                               "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                                               " co_itm, tx_nomItm, co_bod, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                               " nd_can, nd_cosuni, nd_porDes, st_ivaCom ,st_regrep " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                               "  )" +
                                               "VALUES (" ;

                                        String strSqlDet="";

                                       objTblMod.removeEmptyRows();
                                        for (int i=0;i<objTblMod.getRowCountTrue();i++){              /* 25/Sep/2015 JoséMario  */                              
                                            strSqlDet= sSQL +
                                                       objZafParSis.getCodigoEmpresa() + ", " +
                                                       objZafParSis.getCodigoLocal()   + ", " + 
                                                       intNumCot + ", " +
                                                       (i+1)       + ", " +
                                                       objUti.codificar(tblDat.getValueAt(i, INT_TBL_CODITM).toString()) + ",'" +
                                                       objUti.remplazarComillaSimple(tblDat.getValueAt(i, INT_TBL_DESITM).toString()) + "'," +                                                      
                                                       tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + ","+
                                                       tblDat.getValueAt(i, INT_TBL_CANMOV).toString() + ", " +
                                                       tblDat.getValueAt(i, INT_TBL_COSTO).toString() + ", " +
                                                       tblDat.getValueAt(i, INT_TBL_PORDES).toString() + ", '" +
                                                       ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
                                                       ",'I' )"; 

                                            /* Ejecutando el insert */
                                            System.out.println("Ejecutando el insert \n"+strSqlDet);
                                            pstReg = conCot.prepareStatement(strSqlDet);
                                            pstReg.executeUpdate();

                                          }
                                        /*********************************************\    
                                         * Armando el Insert para los datos          *   
                                         * del detalle de PAgo                       *
                                        \*********************************************/

                                        sSQL= "INSERT INTO  tbm_pagCotCom" +
                                               "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                                               " ne_diaCre, fe_ven, " +//<==
                                               " mo_pag, ne_diaGra ,st_regrep  " +//<==
                                               "  )" +
                                               "VALUES (" ;

                                        strSqlDet="";

                                        
                                        for(int i=0; i<tblCotForPag.getRowCount();i++){
                                               /*
                                                 * Convirtiendo la fecha en formato aaaa/mm/dd
                                                 * para poder grabarlo en la base
                                                 */
                                                int Fec[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
                                                String strFechaPago = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                                        
                                            strSqlDet= sSQL +
                                                       objZafParSis.getCodigoEmpresa() + ", " +
                                                       objZafParSis.getCodigoLocal()   + ", " + 
                                                       intNumCot + ", " +
                                                       (i+1)       + ", " +
                                                       tblCotForPag.getValueAt(i, 1) + ",'#" +
                                                       strFechaPago + "#'," +
                                                       objUti.redondear(Double.parseDouble( (tblCotForPag.getValueAt(i, 3)==null)?"0":tblCotForPag.getValueAt(i, 3).toString() ),objZafParSis.getDecimalesBaseDatos()) + ", " +
                                                       ((tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ) + 
                                                       " ,'I')"; 
                                                       
                                            /* Ejecutando el insert */
                                            pstReg = conCot.prepareStatement(strSqlDet);
                                            pstReg.executeUpdate();

                                          }
                                        
                                          txtCot.setText(""+intNumCot);
                                          conCot.commit();
                                          STR_REGREP="I";
                                          
                                    }
                               
                             }catch(SQLException Evt){
                                conCot.rollback();
                                objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                return false;
                            }catch(Exception Evt){
                                conCot.rollback();
                                 objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                 return false;
                              }finally{
                                try{
                                    if (rstNum != null)
                                        rstNum.close();
                                    rstNum=null;
                                    
                                    if (stmCot != null)
                                        stmCot.close();
                                    stmCot=null;
                                    
                                    if (pstReg != null) 
                                        pstReg.close();
                                    pstReg=null;
                                    
                                    if (conCot != null)
                                        conCot.close();
                                    conCot=null;
                                }catch(Throwable e){
                                    ;
                                }
                            } 
                           }catch(Exception Evt){
                                objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                return false;
                           }                                                 
              return true;                                     
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
            noEditable(false);
             strCodPrv="";
              strDesLarPrv="";   
              strCodCom="";
              strDesLarCom="";
        }
            
        public boolean actualizarReg(){
            Connection conCot=null;
            PreparedStatement pstReg=null;
            Statement stmCot=null;
                    /*
                     * Esto Hace en caso de que el modo de operacion sea Modificar
                     */
                         
                          //VAlidando los campos de la cabecera
                           
                           if(!validaCampos() || isAnulada())
                              return false;

                           try{//odbc,usuario,password
                                    conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                                    try{
                                    if (conCot!=null){
                                        conCot.setAutoCommit(false);
                                        stmCot=conCot.createStatement();

                                        /*
                                         * Convirtiendo la fecha en formato aaaa/mm/dd
                                         * para poder grabarlo en la base
                                         */
                                        int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                                        String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                                        

                                        
                                        /*********************************************\    
                                         * Armando el Update para los datos          *   
                                         * de la cabecera de cotizacion              *
                                        \*********************************************/
                                        
                                           String   strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                           
                                 
                                        
                                        if(STR_REGREP.equalsIgnoreCase("P")) STR_REGREP="M";


                                        sSQL= "Update tbm_cabCotCom set " +
                                               " fe_cot  = '" + strFecha            + "', " +
                                               " co_prv  = "  + txtPrvCod.getText() + ",  " + 
                                               " co_com  = "  + txtComCod.getText() + ",  " +
                                               " tx_ate  = '" + txtAte.getText()    + "', " + 
                                               " tx_obs1 = '" + txaObs1.getText()   + "', " + 
                                               " tx_obs2 = '" + txaObs2.getText()   + "', " + 
                                               " nd_sub  = "  + objUti.redondear(txtSub.getText(),objZafParSis.getDecimalesMostrar())    + ",  " +
                                               " nd_valiva  = "  + objUti.redondear(txtIva.getText(),objZafParSis.getDecimalesMostrar())    + ",  " +
                                               " nd_tot  = "  + objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar())    + ",  " +
                                               " nd_porIva = "+ dblPorIva           +  ", " +
                                               " co_forPag = "+ vecForPag.get(cboForPag.getSelectedIndex()) + 
                                               " ,st_regrep='"+STR_REGREP+"' "+
                                               
                                               ", fe_ultMod ='"+strFecSis+"' "+
                                               ", co_usrMod = "+objZafParSis.getCodigoUsuario()+""+
                                               
                                               "  where " +
                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                                               " co_cot = " +  txtCot.getText();      
                                        

                                        /* Ejecutando el Update */
                                       pstReg = conCot.prepareStatement(sSQL);
                                        pstReg.executeUpdate();

                                        /*********************************************\    
                                         * Armando el Delete para quitar los         *   
                                         * registro de detalle de cotizacion         *
                                         * actuales.                                 *
                                        \*********************************************/
                                        sSQL= " DELETE FROM tbm_detCotCom " +
                                               "  where " +
                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                                               " co_cot = " +  txtCot.getText();      

                                        /* Ejecutando el Delete */
                                        pstReg = conCot.prepareStatement(sSQL);
                                        pstReg.executeUpdate();

                                        
                                        /*********************************************\    
                                         * Armando el Insert para los datos          *   
                                         * del detalle de cotizacion                 *
                                        \*********************************************/

                                        sSQL= "INSERT INTO  tbm_detCotCom" +
                                               "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                                               " co_itm, tx_nomItm, co_bod, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                               " nd_can, nd_cosUni, nd_porDes, st_ivaCom ,st_regrep " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                               "  )" +
                                               "VALUES (" ;

                                        String strSqlDet="";

                                       objTblMod.removeEmptyRows();
                                        for (int i=0;i<objTblMod.getRowCountTrue();i++){                                            
 

                                            strSqlDet= sSQL +
                                                       objZafParSis.getCodigoEmpresa() + ", " +
                                                       objZafParSis.getCodigoLocal()   + ", " + 
                                                       txtCot.getText() + ", " +
                                                       (i+1)       + ", " +
                                                       tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
                                                       objUti.remplazarComillaSimple(tblDat.getValueAt(i, INT_TBL_DESITM)) + "'," +
                                                       tblDat.getValueAt(i, INT_TBL_CODBOD) + ","+
                                                       tblDat.getValueAt(i, INT_TBL_CANMOV) + ", " +
                                                       tblDat.getValueAt(i, INT_TBL_COSTO) + ", " +
                                                       tblDat.getValueAt(i, INT_TBL_PORDES)+ ", '" +
                                                       ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
                                                       ",'"+STR_REGREP+"' )"; 
                                                       
                                            /* Ejecutando el insert */
                                        pstReg = conCot.prepareStatement(strSqlDet);
                                        pstReg.executeUpdate();

                                          }
                                        


                                        /*********************************************\    
                                         * Armando el Delete para quitar los         *   
                                         * registro de detalle de Pago               *
                                         * actuales.                                 *
                                        \*********************************************/
                                        sSQL= " DELETE FROM tbm_pagCotCom " +
                                               "  where " +
                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                                               " co_cot = " +  txtCot.getText();      

                                        /* Ejecutando el Delete */
                                        pstReg = conCot.prepareStatement(sSQL);
                                        pstReg.executeUpdate();

                                        
                                        /*********************************************\    
                                         * Armando el Insert para los datos          *   
                                         * del detalle de PAgo                       *
                                        \*********************************************/

                                        sSQL= "INSERT INTO  tbm_pagCotCom" +
                                               "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                                               " ne_diaCre, fe_ven, " +//<==
                                               " mo_pag, ne_diaGra ,st_regrep " +//<==
                                               "  )" +
                                               "VALUES (" ;

                                        strSqlDet="";

                                        for(int i=0; i<tblCotForPag.getRowCount();i++){
                                            
                                            /*
                                                 * Convirtiendo la fecha en formato aaaa/mm/dd
                                                 * para poder grabarlo en la base
                                                 */
                                                int Fech[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
                                                String strFecPago = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                                            
                                            strSqlDet= sSQL +
                                                       objZafParSis.getCodigoEmpresa() + ", " +
                                                       objZafParSis.getCodigoLocal()   + ", " + 
                                                       txtCot.getText() + ", " +
                                                       (i+1)       + ", " +
                                                       tblCotForPag.getValueAt(i, 1) + ",'#" +
                                                       strFecPago + "#'," +
                                                       objUti.redondear(Double.parseDouble( (tblCotForPag.getValueAt(i, 3)==null)?"0":tblCotForPag.getValueAt(i, 3).toString() ),objZafParSis.getDecimalesMostrar()) + ", " +
                                                       ((tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ) + 
                                                       ",'"+STR_REGREP+"' )"; 
                                                       
                                            /* Ejecutando el insert */
                                            pstReg = conCot.prepareStatement(strSqlDet);
                                            pstReg.executeUpdate();

                                          }
                                        
                                        conCot.commit();
                                    }
                                 }catch(SQLException Evt){ 
                                      conCot.rollback();
                                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                      return false;
                                 }catch(Exception Evt){ 
                                      conCot.rollback();
                                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                      return false;
                                 }finally{
                                        try{ 
                                            if (stmCot != null)
                                                stmCot.close();
                                            stmCot=null;
                                            
                                            if (pstReg != null)
                                                pstReg.close();
                                            pstReg=null;
                                            
                                            if (conCot != null)
                                                conCot.close();
                                            conCot=null;                                            
                                        }catch(Throwable e){
                                            ;
                                        }
                                        
                                 }

                            }catch(Exception Evt){
                                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                  return false;
                            }                       
                
                           
                return true;
            }
                 
//        public boolean eliminarReg(){
//                    /*
//                     * Esto Hace en caso de que el modo de operacion sea Nuevo
//                     */
//                         
//                          //VAlidando los campos de la cabecera
//
//                           if(!validaCampos())
//                              return false;
//                           try{//odbc,usuario,password
//                                  java.sql.Connection conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                                  try{
//                                    if (conCot!=null){
//                                        conCot.setAutoCommit(false);
//                                        java.sql.PreparedStatement pstReg;
//                                        java.sql.Statement stmCot=conCot.createStatement();
//
//                                        /*********************************************\    
//                                         * Armando el Delete para quitar los         *   
//                                         * registro de detalle de cotizacion         *
//                                         * actuales.                                 *
//                                        \*********************************************/
//                                        sSQL= " DELETE FROM tbm_pagCotCom " +
//                                               "  where " +
//                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
//                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
//                                               " co_cot = " +  txtCot.getText();      
//
//                                        /* Ejecutando el Delete */
//                                        pstReg = conCot.prepareStatement(sSQL);
//                                        pstReg.executeUpdate();
//
//                                        
//                                        sSQL= " DELETE FROM tbm_detCotCom " +
//                                               "  where " +
//                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
//                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
//                                               " co_cot = " +  txtCot.getText();      
//
//                                        /* Ejecutando el Delete */
//                                        pstReg = conCot.prepareStatement(sSQL);
//                                        pstReg.executeUpdate();
//
//
//                                        /*********************************************\    
//                                         * Armando el Delete para quitar los         *   
//                                         * registro de la Cabecera de la cotizacion  *
//                                         * actuales.                                 *
//                                        \*********************************************/
//                                        sSQL= " DELETE FROM tbm_cabCotCom " +
//                                               "  where " +
//                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
//                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
//                                               " co_cot = " +  txtCot.getText();      
//
//                                        /* Ejecutando el Delete */
//                                        pstReg = conCot.prepareStatement(sSQL);
//                                        pstReg.executeUpdate();
//                                       conCot.commit();
//                                       conCot.close();
//                                    }
//                             }catch(SQLException Evt){
//                                  conCot.rollback();
//                                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                  return false;                                  
//                            }
//                           }catch(Exception Evt){
//                                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                  return false;
//                            }                       
//                           
//                return true;
//            }      
        
        
        
        
        
           public boolean eliminarReg(){
                    /*
                     * Esto Hace en caso de que el modo de operacion sea Anular
                     */
                          if (isAnulada())
                              return false;
                           try{//odbc,usuario,password
                                  Connection conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                                  try{
                                    if (conCot!=null){
                                        conCot.setAutoCommit(false);
                                        PreparedStatement pstReg;
                                        Statement stmCot=conCot.createStatement();

                                        /*********************************************\    
                                         * Armando el Update para ANULAR             *   
                                         * la cotizacion                             *
                                        \*********************************************/
                                        
                                        
                                       String   strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                        
                                        
                                         if(STR_REGREP.equalsIgnoreCase("P")) STR_REGREP="M";
                                        sSQL= "Update tbm_cabCotCom set " +
                                               " st_reg  = '"  + "E" + "' " + 
                                               " ,st_regrep='"+STR_REGREP+"' "+
                                               
                                               ", fe_ultMod ='"+strFecSis+"' "+
                                               ", co_usrMod = "+objZafParSis.getCodigoUsuario()+""+
                                               
                                               "  where " +
                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                                               " co_cot = " +  txtCot.getText();      


                                        /* Ejecutando el Update */
                                        pstReg = conCot.prepareStatement(sSQL);
                                        pstReg.executeUpdate();
                                       conCot.commit();
                                       conCot.close();
                                    }
                             }catch(SQLException Evt){
                                  conCot.rollback();
                                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                  return false;
                             }
                           }catch(Exception Evt){
                                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
                                  return false;
                           }                       
                
                           
                return true;
                
            }    
        
        
            
        public boolean anularReg(){
                    /*
                     * Esto Hace en caso de que el modo de operacion sea Anular
                     */
                          if (isAnulada())
                              return false;
                           try{//odbc,usuario,password
                                  Connection conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                                  try{
                                    if (conCot!=null){
                                        conCot.setAutoCommit(false);
                                        PreparedStatement pstReg;
                                        Statement stmCot=conCot.createStatement();

                                        /*********************************************\    
                                         * Armando el Update para ANULAR             *   
                                         * la cotizacion                             *
                                        \*********************************************/
                                        
                                        
                                           String   strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                        
                                        
                                         if(STR_REGREP.equalsIgnoreCase("P")) STR_REGREP="M";
                                        sSQL= "Update tbm_cabCotCom set " +
                                               " st_reg  = '"  + "I" + "' " + 
                                               " ,st_regrep='"+STR_REGREP+"' "+
                                               
                                               ", fe_ultMod ='"+strFecSis+"' "+
                                               ", co_usrMod = "+objZafParSis.getCodigoUsuario()+""+
                                               
                                               "  where " +
                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                                               " co_cot = " +  txtCot.getText();      


                                        /* Ejecutando el Update */
                                        pstReg = conCot.prepareStatement(sSQL);
                                        pstReg.executeUpdate();
                                       conCot.commit();
                                       conCot.close();
                                    }
                             }catch(SQLException Evt){
                                  conCot.rollback();
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
   public void Actualizar_valores(){
        Connection conTipDoc;
        PreparedStatement pstReg;
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                String sql="UPDATE tbm_cabcotcom set nd_sub="+txtSub.getText()+", nd_valiva="+txtIva.getText()+",nd_tot="+txtTot.getText()+" where co_emp="+objZafParSis.getCodigoEmpresa()+"" +
                " and co_loc="+objZafParSis.getCodigoLocal()+" and co_cot="+txtCot.getText();
                pstReg = conTipDoc.prepareStatement(sql);
                pstReg.executeUpdate();
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (SQLException e){  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
   }


private void cargarRepote(int intTipo){
   if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.setIndFunEje(intTipo);
        objThrGUI.start();
    }
}
        
        
 @Override
 public boolean vistaPreliminar(){

      cargarRepote(1);

//        int intCodEmp=objZafParSis.getCodigoEmpresa();
//        int intCodLoc=objZafParSis.getCodigoLocal();
//        String strTit=objZafParSis.getNombreMenu();
//        String strNomEmp=retNomEmp(intCodEmp);
//        String strTelCli=""; //retTelCli();
//        String strRucCli=""; //retRucCli();
//        String strCodForPag = cboForPag.getSelectedItem() +"";
//       Actualizar_valores();
//
//
//
//        Connection conIns;
//        try
//        {
//            conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//        try
//        {
//            if(conIns!=null){
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
//
//                // Second, create a map of parameters to pass to the report.
//                Map parameters = new HashMap();
//                parameters.put("tit", strTit);
//                parameters.put("numCot", txtCot.getText());
//                parameters.put("codEmp", ""+intCodEmp);
//                parameters.put("codLoc", ""+intCodLoc);
//                parameters.put("fecCot", txtFecDoc.getText());
//                parameters.put("codCli", txtPrvCod.getText());
//                parameters.put("nomCli", txtPrvNom.getText());
//                parameters.put("rucCli", strRucCli);
//                parameters.put("dirCli", txtPrvDir.getText());
//                parameters.put("nomVen", txtComNom.getText());
//                parameters.put("obs1", txaObs1.getText());
//                parameters.put("obs2", txaObs2.getText());
//                parameters.put("subTot", txtSub.getText());
//                parameters.put("iva", txtIva.getText());
//                parameters.put("totFin", txtTot.getText());
//                parameters.put("nomEmp", strNomEmp);
//                parameters.put("telCli", strTelCli);
//                parameters.put("forPag", strCodForPag);
//                parameters.put("atencion", txtAte.getText());
//
//
//                JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//
//                //para imprimir
//               // JasperManager.printReport(report, false);
//
//                  //para vista preliminar
//                       JasperViewer.viewReport(report, false);
//
//
//            }
//        }
//        catch (JRException e)
//        {
//           objUti.mostrarMsgErr_F1(jfrThis, e);
//        }
//
//        }
//
//        catch(SQLException ex)
//        {
//           objUti.mostrarMsgErr_F1(jfrThis, ex);
//        }
               return true;
         }
        
        
       @Override
       public boolean imprimir(){

            cargarRepote(1);

//        int intCodEmp=objZafParSis.getCodigoEmpresa();
//        int intCodLoc=objZafParSis.getCodigoLocal();
//        String strTit=objZafParSis.getNombreMenu();
//        String strNomEmp=retNomEmp(intCodEmp);
//        String strTelCli=""; //retTelCli();
//        String strRucCli=""; //retRucCli();
//        String strCodForPag = cboForPag.getSelectedItem() +"";
//
//        Actualizar_valores();
//
//        Connection conIns;
//        try
//        {
//            conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//        try
//        {
//            if(conIns!=null){
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
//
//                // Second, create a map of parameters to pass to the report.
//                Map parameters = new HashMap();
//                parameters.put("tit", strTit);
//                parameters.put("numCot", txtCot.getText());
//                parameters.put("codEmp", ""+intCodEmp);
//                parameters.put("codLoc", ""+intCodLoc);
//                parameters.put("fecCot", txtFecDoc.getText());
//                parameters.put("codCli", txtPrvCod.getText());
//                parameters.put("nomCli", txtPrvNom.getText());
//                parameters.put("rucCli", strRucCli);
//                parameters.put("dirCli", txtPrvDir.getText());
//                parameters.put("nomVen", txtComNom.getText());
//                parameters.put("obs1", txaObs1.getText());
//                parameters.put("obs2", txaObs2.getText());
//                parameters.put("subTot", txtSub.getText());
//                parameters.put("iva", txtIva.getText());
//                parameters.put("totFin", txtTot.getText());
//                parameters.put("nomEmp", strNomEmp);
//                parameters.put("telCli", strTelCli);
//                parameters.put("forPag", strCodForPag);
//                parameters.put("atencion", txtAte.getText());
//
//
//                JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//
//                //para imprimir
//                 JasperManager.printReport(report, false);
//
//
////
////               JasperManager.printReportToPdfFile(report, "C:\\dos.pdf");
////            try {
////               PdfDecoder pdf = null;
////               PrintService[] service = PrinterJob.lookupPrintServices();
////               PrinterJob printJob = PrinterJob.getPrinterJob();
////               printJob.setPrintService(service[0]);
////               Paper paper = new Paper();
////               paper.setSize(595, 842);
////               paper.setImageableArea(0, 0, 595, 842);
////               PageFormat pf = printJob.defaultPage();
////               pf.setPaper(paper);
////
////                pdf = new PdfDecoder(true);
////                pdf.openPdfFile("C://dos.pdf");
////                pdf.setPageFormat(pf);
////
////                printJob.setPageable(pdf);
////                printJob.print();
////
////         }catch(Exception e){
////      System.out.println("Error: aqui >>   "+e);
////    }
//
//
//
//
//
//
//
//            }
//        }
//        catch (JRException e)
//        {
//            System.out.println("Excepción: " + e.toString());
//        }
//
//        }
//
//        catch(SQLException ex)
//        {
//            System.out.println("Error al conectarse a la base");
//        }
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
                if(!txtPrvCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CotCab.co_prv =" + txtPrvCod.getText() + "";
                    
                //Agregando filtro por Codigo de Vendedor
                if(!txtComCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CotCab.co_com = " + txtComCod.getText() + "";

                //Agregando filtro por Codigo de Vendedor
                if(!txtAte.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CotCab.tx_ate LIKE '" + txtAte.getText() + "'";
                
                /*
                 * Poniendo Filtros para direccion del cliente y otros campos que no pertenecen a 
                 * la tabla de Cotizacion 
                 */
                    //Agregando filtro por Direccion del cliente
                    if(!txtPrvDir.getText().equals(""))
                        sqlFiltro = sqlFiltro + " and Cli.tx_dir LIKE '" + txtPrvDir.getText() + "'";

                    //Agregando filtro por Nombre del cliente
                    if(!txtPrvNom.getText().equals(""))
                        sqlFiltro = sqlFiltro + " and Cli.tx_nom LIKE '" + txtPrvNom.getText() + "'";

//                        sqlFiltro = sqlFiltro + " and Cli.tx_nom LIKE " + txtPrvNom.getText();

                    //Agregando filtro por Nombre del cliente
                    if(!txtComNom.getText().equals(""))
                        sqlFiltro = sqlFiltro + " and Usr.tx_nom LIKE '" + txtComNom.getText() + "'";

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
                   try{//odbc,usuario,password
                            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                            if (conCab!=null){

                                //stmCot=conCot.createStatement();
                                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                
                                 /*
                                 * Agregando el Sql de Consulta para la cotizacion
                                 */
                                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                                      " Usr.co_usr as co_com, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                                      " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                                      " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                                      " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                                      " CotCab.st_reg " + // Campo para saber si esta anulado o no
                                      " FROM tbm_cabCotCom as CotCab left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_prv = cli.co_cli) left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_com ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                      " Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                                      "       and CotCab.co_loc = " + objZafParSis.getCodigoLocal()+" AND CotCab.st_reg not in('E')";// Consultando en el local en el ke se esta trabajando
                                sSQL= sSQL + strFiltro + " ORDER BY CotCab.co_cot";
                                //System.out.println(sSQL);
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


                   }


                   catch(SQLException Evt)
                   {
                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
                          return false;
                    }

                    catch(Exception Evt)
                    {
                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
                          return false;
                    }                       
               return true;     
            
            }
    
   private void cargarRegistroInsert(){
                   try{//odbc,usuario,password
                            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                            if (conCab!=null){

                                //stmCot=conCot.createStatement();
                                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                
                                 /*
                                 * Agregando el Sql de Consulta para la cotizacion
                                 */
                                  sSQL=" SELECT CotCab.co_emp,CotCab.co_loc,CotCab.co_cot,CotCab.st_reg " + // Campo para saber si esta anulado o no
                                  "  FROM tbm_cabCotCom as CotCab " +
                                  "  Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                                  "  and CotCab.co_loc = "  + objZafParSis.getCodigoLocal() + // Consultando en el local en el ke se esta trabajando
                                  "  and CotCab.co_cot = "  + txtCot.getText();

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
    
   /*
    * MODIFICADO EFLORESA 2012-05-07
    * Se modifica para que no se permita hacer cotizaciones/ordenes de compra para items terminal L
    * MODIFICADO EFLORESA 2012-05-09
    * Se modifica para que en COSENCO se permita hacer la cotizacion de items terminal L 
    */ 
   private boolean validaCampos(){
               if(txtPrvCod.getText().equals("") ){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Cliente");
                   txtPrvCod.requestFocus();
                   return false;
               }
               if(!txtFecDoc.isFecha()){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Fecha de Cotización");
                   txtFecDoc.requestFocus();
                   return false;
               }
               if(txtComCod.getText().equals("") ){
                   tabCotCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Vendedor");
                   txtComCod.requestFocus();
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
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                    objUti.verifyTbl(tblCotForPag,intColObligadasPag);
                    return false;
               }

               /*
                * VAlidando que la suma de los montos de pagos sean igual a total de cotizacion
                */
               
               double dblTotalMonto=0;
               for(int rowIdx = 0; rowIdx<tblCotForPag.getRowCount() ;rowIdx++){
                    double dblSubMonto = Double.parseDouble((tblCotForPag.getValueAt(rowIdx, 3)==null)?"0":tblCotForPag.getValueAt(rowIdx, 3).toString());
                    dblTotalMonto = dblTotalMonto + dblSubMonto ; 
                }
               
                if( objUti.redondear(dblTotalMonto,3)!= objUti.redondear(Double.parseDouble(txtTot.getText()), objZafParSis.getDecimalesMostrar()) ){
                      tabCotCom.setSelectedIndex(1);
                      //JOptionPane obj = new JOptionPane();
                            /*String strTit, strMsg;
                            strTit="Mensaje del sistema Zafiro";*/
                            String  strMsg="La suma de los montos a pagar es mayor al total de la Cotizacion.\nCorrija y vuelva a intentarlo.";
                            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                    return false;
                }
               
                for (int x=0; x<tblDat.getRowCount(); x++){
                    if(tblDat.getValueAt(x, INT_TBL_CODITM)!=null){
                        strItm = tblDat.getValueAt(x,INT_TBL_ITMALT).toString();
                        //intIndItm = strItm.indexOf("L");
                        //if (intIndItm > -1 && !blnIsCosenco){
                        if (strItm.endsWith("L") && !blnIsCosenco){
                            MensajeInf("No se puede ingresar una cotizacion de compra de items con terminal L");
                            //tblDat.setValueAt( null , x, INT_TBL_ITMALT);
                            return false;
                        }
                    }
                }
               
                for (int x=0; x<tblDat.getRowCount(); x++){
                    if(tblDat.getValueAt(x, INT_TBL_CODITM)!=null){
                        strItm = tblDat.getValueAt(x,INT_TBL_ITMALT).toString(); 
                        double dblCan = objUti.parseDouble(tblDat.getValueAt(x,INT_TBL_ITMALT).toString()); 
                        //if (intIndItm > -1 && !blnIsCosenco){
                        if (strItm.endsWith("L") && !blnIsCosenco){
                            MensajeInf("No se puede ingresar una cotizacion de compra de items con terminal L");
                            return false;
                        }
                    }
                }
                
               /*
                *  String para el hacer el query en la tabla de clientes y obtener 
                *  el maximo porcentaje de descuento.
                */
               
               String strSql = "select * from tbm_cli where " +
                               "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                               "co_cli = " + Integer.parseInt(txtPrvCod.getText());                         
         return true; 
          }    
    /*
     *  Listener para hacer que se calcule el pago despues segun el total de cotizacion
     */
    private boolean isAnulada()
    {
       boolean blnRes = false;
      Connection conTmp ;
      Statement stmTmp;
      String strSQL = "";
      try
      {
          conTmp = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
          if (conTmp!=null)
          {
              stmTmp = conTmp.createStatement();
              strSQL = "";
              strSQL = " Select count(*) from tbm_cabcotcom ";
              strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and co_cot = " + txtCot.getText() + " and st_reg = 'I' " ;
              if (objUti.getNumeroRegistro(this,objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0){
                  blnRes = true;
                  MensajeInf("Documento anulado no se puede modificar");
              }else
                  blnRes = false;
              stmTmp.close();
              conTmp.close();
          }

       }catch(SQLException Evt){    
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            blnRes = true;
       }catch(Exception Evt)  {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            blnRes = true;
       }
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
             
             try{
                    Connection conPag = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    if (conPag!=null){     

                         if(cboForPag.getSelectedIndex()!=-1){

                                     
                           String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre " +
                                          " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                          "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) + " Order by A2.co_reg";
                                 
                         
                            String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
                                          " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                          "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex());
                         
                        

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
                                          x++;  
                                    }
                           }
                         conPag.close();
                         conPag = null ;
                    }
                }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
             
                calculaPagos();    
         }
    }
    
    //****aqui me quede
    
   public void calculaPagos(){
       
          int intVal =  intCanArr[0];
          int intsizearre = intarreglodia.length; 
          intVal= intsizearre - (intsizearre-intVal);
          
       
        Calendar objFec = Calendar.getInstance();
        int fecCot [] = txtFecDoc.getFecha(txtFecDoc.getText());

        if(fecCot!=null){
            objFec.set(Calendar.DAY_OF_MONTH, fecCot[0]);
            objFec.set(Calendar.MONTH, fecCot[1] - 1); 
            objFec.set(Calendar.YEAR, fecCot[2]);
        }

        Vector vecPag = new Vector();
        /*double dblRetFue = objUti.redondeo(((Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText()))*1/100),objZafParSis.getDecimalesMostrar());
        double dblRetIva = txtIva.getText().equals("")?0.0:(objUti.redondeo(((Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))*30)/100),objZafParSis.getDecimalesMostrar()));
        double dblMonCal=0; //Monto a Calcular los pagos (sin retenciones)                                        
        double dblTotalDoc = objUti.redondeo((Double.parseDouble(txtTot.getText().equals("")?"0":txtTot.getText())),objZafParSis.getDecimalesMostrar());
//        dblMonCal = objUti.redondeo(((dblTotalDoc - dblRetFue)),6);
        dblMonCal = objUti.redondeo(dblTotalDoc,objZafParSis.getDecimalesMostrar());*/

        double dblRetFue = objUti.redondear(((Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText()))*1/100),objZafParSis.getDecimalesMostrar());
        double dblRetIva = txtIva.getText().equals("")?0.0:(objUti.redondear(((Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))*30)/100),objZafParSis.getDecimalesMostrar()));
        double dblMonCal=0; //Monto a Calcular los pagos (sin retenciones)                                        
        double dblTotalDoc = objUti.redondear((Double.parseDouble(txtTot.getText().equals("")?"0":txtTot.getText())),objZafParSis.getDecimalesMostrar());
//        dblMonCal = objUti.redondeo(((dblTotalDoc - dblRetFue)),6);
        dblMonCal = objUti.redondear(dblTotalDoc,objZafParSis.getDecimalesMostrar());

      
                    //Borrando los datos actuales del jtable
                    while(tblCotForPag.getRowCount()>0)
                        ((DefaultTableModel)tblCotForPag.getModel()).removeRow(0);

                     if(cboForPag.getSelectedIndex()!=-1){
                        /*
                         * Agregando el Sql de Consulta para la cotizacion
                         */
                                   ZafDatePicker dtePckPag =  new ZafDatePicker(new JFrame(),"d/m/y");
                                   int diaCre=0, numPag, diaCreAcum = 0,diaCreAnt = 0;
                                   double dblDifPago=0,dblSumPago=0;
                                   int i=0;
                                   double dblPagos=0.00;
                                   double dblPago=0.00;
                                   
                                   for(i=0; i < intVal; i++) {
                                   
                                            diaCreAnt = diaCre;
                                           
                                            diaCre = intarreglodia[i]; 
                                            numPag = intarreglonum[i];
                                            
                                            diaCreAcum = diaCre - diaCreAnt;
                                            if (diaCre!=0)
                                                objFec.add(Calendar.DATE, diaCreAcum);                                                  

                                            dtePckPag.setAnio( objFec.get(Calendar.YEAR));
                                            dtePckPag.setMes( objFec.get(Calendar.MONTH)+1);
                                            dtePckPag.setDia(objFec.get(Calendar.DAY_OF_MONTH));

//                                            double dblValPag=objUti.redondeo((dblMonCal/numPag),objZafParSis.getDecimalesMostrar());
                                            double dblValPag =0;
                                           // if (rst.isLast()){
                                            //    dblValPag = dblMonCal - dblSumPago;
                                            //}else{
                                             //   dblValPag=objUti.redondeo((dblMonCal/numPag),objZafParSis.getDecimalesMostrar());
                                              //  dblSumPago += dblValPag;
                                           // }

                                               dblPagos = objUti.redondear( (numPag==0)?0:(dblMonCal/numPag) ,intNumDec);
                                               dblPago += dblPagos; 
                                               dblPagos = objUti.redondear(dblPagos ,intNumDec);
                                               dblValPag = dblPagos;
                                               
                                            ((DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());    
                                                tblCotForPag.setValueAt(new Integer(diaCre) , i, 1);
                                                tblCotForPag.setValueAt(dtePckPag.getText(), i, 2);
                                                tblCotForPag.setValueAt(new Double(dblValPag), i, 3);
                                                tblCotForPag.setValueAt(new Integer(0),  i, 4);
                                    }
                                     double dblultpag =  objUti.redondear(Double.parseDouble(tblCotForPag.getValueAt( i-1, 3).toString()),intNumDec);
                                     dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago) ),intNumDec);
                                     tblCotForPag.setValueAt( new Double(dblultpag) , i-1, 3);
                                   

                                        //obteniendo la fecha actual
                                            int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                                            if(Fecha!=null){
                                                String strFecha = Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0];
                                            }
 
                           
                     }                             
      
    }    
   
   
   
    
//   public class ButFndTbl extends Librerias.ZafTableColBut.ZafTableColBut{
//        
//        public ButFndTbl(javax.swing.JTable tbl, int intIdx){
//            super(tbl,intIdx);
//        }
//        public void butCLick() {
//            listaItem("");
//            }
//        }        
//    
    

    
//    private void listaItem(String strDesBusqueda){
//            Librerias.ZafConsulta.ZafConsulta  obj = 
//                  new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
//                 "Codigo,Nombre,Stock,Precio,Iva[S/N],Cod.Sistema","tx_codAlt, tx_nomItm, nd_stkAct, nd_preVta1, st_ivaVen, co_itm",
//                 "select tx_codAlt,tx_nomItm,nd_stkAct,nd_preVta1,st_ivaVen,co_itm from tbm_inv where co_emp = "+ objZafParSis.getCodigoEmpresa(), "", 
//                 objZafParSis.getStringConexion(), 
//                 objZafParSis.getUsuarioBaseDatos(), 
//                 objZafParSis.getClaveBaseDatos()
//                  );        
//            
//                    obj.setTitle("Listado Productos");
//
//                    if(tblDat.getValueAt(tblDat.getSelectedRow(), 1) == null || tblDat.getValueAt(tblDat.getSelectedRow(), 1).toString().equals("") )
//                        obj.show();
//                    else
//                        if(!strDesBusqueda.equals("")){
//                            if(!obj.buscar("tx_codalt = '" + tblDat.getValueAt(tblDat.getSelectedRow(), 1).toString()+"'") )
//                                obj.show();
//                        }else 
//                            obj.show();
//                        
//                    if(!obj.GetCamSel(1).equals("")){
//                      //Poniendo el codigo de producto, que el usuario eligio en la consulta, en la tabla 
//                        tblDat.setValueAt(obj.GetCamSel(1),tblDat.getSelectedRow(),1);
//                      //Poniendo La descripcion de producto, que el usuario eligio en la consulta, en la tabla 
//                        tblDat.setValueAt(obj.GetCamSel(2),tblDat.getSelectedRow(),3);
//                        
//                        Boolean blnIva = new Boolean(false);
//
//                        if(obj.GetCamSel(5).equals("S"))
//                            blnIva = new Boolean(true);
// 
//                        tblDat.setValueAt(new Double(obj.GetCamSel(4)),tblDat.getSelectedRow(),6);
//                        tblDat.setValueAt(blnIva,tblDat.getSelectedRow(),8);
//                        tblDat.setValueAt(obj.GetCamSel(6),tblDat.getSelectedRow(),10);
//                        tblDat.changeSelection(tblDat.getSelectedRow(), 5, false, false);
//                        tblDat.editCellAt(tblDat.getSelectedRow(),5);
//                    }
//    }
//    
//    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCliCon;
    private javax.swing.JButton butVenCon;
    private javax.swing.JComboBox cboForPag;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblCotNumDes;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblFecDoc;
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
    private javax.swing.JTable tblCotForPag;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextArea txt;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtComCod;
    private javax.swing.JTextField txtComNom;
    private javax.swing.JTextField txtCot;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtPrvCod;
    private javax.swing.JTextField txtPrvDir;
    private javax.swing.JTextField txtPrvNom;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    
//    
//    
//    public String retRucCli(){
//        java.sql.Connection conTipDoc;
//        java.sql.Statement stmTipDoc;
//        java.sql.ResultSet rstTipDoc;
//        String que, auxTipDoc="";
//        try{
//            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
//            if(conTipDoc!=null){
//                stmTipDoc=conTipDoc.createStatement();
//                que="";
//                que+="select tx_ide from tbm_cli";
//                que+=" where co_emp=" + objZafParSis.getCodigoEmpresa()+ "";
//                que+=" and co_cli=" + txtPrvCod.getText() + "";
//               // System.out.println("el query del nombre de la empresa es:"+que);
//                rstTipDoc=stmTipDoc.executeQuery(que);
//                if (rstTipDoc.next()){
//                    auxTipDoc=rstTipDoc.getString("tx_ide");
//                }
//            }
//            conTipDoc.close();
//            conTipDoc=null;
//        }
//        catch (java.sql.SQLException e)
//        {
//           // objutil.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            //objutil.mostrarMsgErr_F1(this, e);
//        }
//        return auxTipDoc;
//
//    }
//
////  
//    
//   public String retTelCli(){
//        java.sql.Connection conTipDoc;
//        java.sql.Statement stmTipDoc;
//        java.sql.ResultSet rstTipDoc;
//        String que, auxTipDoc="";
//        
//        
//        try{
//            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
//            if(conTipDoc!=null){
//                stmTipDoc=conTipDoc.createStatement();
//                que="";
//                que+="select tx_tel from tbm_cli";
//                que+=" where co_emp=" + objZafParSis.getCodigoEmpresa()+ "";
//                que+=" and co_cli=" + txtPrvCod.getText() + "";
//              // System.out.println("el query del nombre de la empresa es:"+que);
//                rstTipDoc=stmTipDoc.executeQuery(que);
//                if (rstTipDoc.next()){
//                    auxTipDoc=rstTipDoc.getString("tx_tel");
//                }
//            }
//            conTipDoc.close();
//            conTipDoc=null;
//        }
//        catch (java.sql.SQLException e)
//        {
//           // objutil.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           // objutil.mostrarMsgErr_F1(this, e);
//        }
//        return auxTipDoc;
//
//    }


    
             
public String retNomEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String strSql, strNomEmp="";
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                strSql=" select tx_nom from tbm_emp where co_emp=" + codEmp + "";
           
                rstTipDoc=stmTipDoc.executeQuery(strSql);
                if (rstTipDoc.next()){
                    strNomEmp=rstTipDoc.getString("tx_nom");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(jfrThis, e); }
        catch (Exception e) {  objUti.mostrarMsgErr_F1(jfrThis, e);  }
     return strNomEmp;
}


    
    
    private class ZafMouMotAda extends MouseMotionAdapter
    {
        @Override
        public void mouseMoved(MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol) {                   
                case INT_TBL_LINEA: strMsg=""; break;
                case INT_TBL_ITMALT: strMsg="Codigo Item"; break;
                case INT_TBL_DESITM: strMsg="Nombre del Item"; break; 
                case INT_TBL_CANMOV: strMsg="Cantidad"; break;
                case INT_TBL_COSTO: strMsg="Costo"; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
 /*
  * MODIFICADO EFLORESA 2012-05-07
  * Se modifica para que no se permita hacer cotizaciones/ordenes de compra para items terminal L 
  * MODIFICADO EFLORESA 2012-05-09
  * Se modifica para que en COSENCO se permita hacer la cotizacion de items terminal L 
  */     
 private void listaProductos(String strDesBusqueda){
           
           System.out.println("Listado de Item"); 
           objVenCon2.setTitle("Listado de Item"); 
            int intNumFil=tblDat.getSelectedRow();
           
           objVenCon2.show();
           if (objVenCon2.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
           {
              tblDat.setValueAt(objVenCon2.getValueAt(1),intNumFil,INT_TBL_ITMALT);
              tblDat.setValueAt(objVenCon2.getValueAt(2),intNumFil,INT_TBL_CODITM);
              tblDat.setValueAt(objVenCon2.getValueAt(3),intNumFil,INT_TBL_DESITM);
              tblDat.setValueAt(objVenCon2.getValueAt(7),intNumFil,INT_TBL_UNIDAD);
              tblDat.setValueAt(objVenCon2.getValueAt(5),intNumFil,INT_TBL_COSTO);
              tblDat.setValueAt(objVenCon2.getValueAt(6),intNumFil,INT_TBL_IVATXT); 
              
             // tblDat.setValueAt(objVenCon2.getValueAt(8),intNumFil,INT_TBL_ITMALT2);
              
              tblDat.setValueAt( ""+intCodBodPre , intNumFil, INT_TBL_CODBOD);
              
               if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)
                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV); 
               if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)
                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES); 
               if(tblDat.getValueAt(intNumFil,INT_TBL_TOTAL)==null)
                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_TOTAL); 
              
                blnChangeData = false;                            
                if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                    strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                else 
                    strBeforeValue = "";
                if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                    tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                }


                if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                    strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                else
                    strAfterValue ="";
                if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                        tblDat.setValueAt("D",intNumFil , INT_TBL_ESTADO);                            
                }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                    if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                        tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);                            
                }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                    if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);                            
                }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                    tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);                            
                }

                if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
                    if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).equals("S"))
                        tblDat.setValueAt(true, intNumFil, INT_TBL_BLNIVA);
                    else
                        tblDat.setValueAt(false, intNumFil, INT_TBL_BLNIVA);
                }

               if(tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null){
                    strItm = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                    //intIndItm = strItm.indexOf("L");
                    //if (intIndItm > -1 && !blnIsCosenco){
                    if (strItm.endsWith("L") && !blnIsCosenco){
                        MensajeInf("No se puede ingresar una cotizacion de compra de items con terminal L");
                        objTblMod.removeRow(intNumFil);     
                        return;
                    }
               }
                
                calculaSubtotal();
           }
    }
    
 
     private class ButFndItm extends ZafTableColBut{
        public ButFndItm(JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        @Override
        public void butCLick() {
             listaProductos("");
             tblDat.requestFocus();
             tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_CANMOV, false, false);
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
                    calculaTotal();
                    calculaPagos();
                    break;
                case TableModelEvent.UPDATE:                         
                    break;
            }
        }
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

        @Override
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
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
//                //Obtener la fecha y hora del servidor.
//                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;
//                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 19:
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.

                                Map mapPar=new HashMap();
                                mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()) );
                                mapPar.put("codCot", new Integer( Integer.parseInt(txtCot.getText())) );
                                mapPar.put("SUBREPORT_DIR", strRutRpt);

//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
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

    @Override
    protected void finalize() throws Throwable{   
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
        ///    System.out.println ("Se libera Objeto...");

    }

    private class RenderDecimales extends JLabel implements TableCellRenderer {
        int intNumDecimales = 0;
        public RenderDecimales(int intNumDecimales) {
            this.intNumDecimales=intNumDecimales ;
            setHorizontalAlignment(JLabel.RIGHT);
            setOpaque(true);
            setBackground(new Color(255, 255, 255));
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
            this.setFont(new java.awt.Font("Tahoma", 0, 11));
            this.setOpaque(true);
            setBackground(new Color(201,223,245));
            setForeground(new Color(0, 0, 0));
            
            return this;
        }
    }
    
}


