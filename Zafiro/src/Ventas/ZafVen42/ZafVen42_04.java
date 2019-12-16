

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Ventas.ZafVen42;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Frame;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
                 
/**     
 * @author  Jose Mario Marin
 */
public class ZafVen42_04 extends javax.swing.JDialog {
    
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
   
 
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafUtil objUti;
     
    private java.util.Date datFecAux;      
     
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODPRO=1;      //CODIGO DEl Proveedor
    final int INT_TBL_NOMPRO=2;      //Nombre del Proveedor
    final int INT_TBL_CODFORPAG=3;      //CODIGO ALTERNO 
    final int INT_TBL_NOMFORPAG=4;     //NOMBRE DEL ITEM 
    final int INT_TBL_BTNFORPAG=5;     //UNIDAD DE MEDIDA 
    final int INT_TBL_OBS=6;        //
    final int INT_TBL_BTNDATCODL=7; // CODIGO DEL ITEM MAESTRO
    final int INT_TBL_COD_FOR_DET=8;    //  Forma de despacho
    final int INT_TBL_FOR_DET=9;    //  Forma de despacho.
    final int INT_TBL_BTNFORDES=10;  //  Boton forma de despacho
    
    private ZafVenCon vcoForPag;                                   // Items 
    private ZafVenCon vcoForDes;                                   // Items 
    private String strSql;
    
    int intNumCotBus=0;
    int intConStbBod=0;
    int[][] intColBodEmp = new int[30][2];
    
    private String strTit="Mensaje del Sistema Zafiro";
    
    private boolean blnCliRet;
     
    Vector vecCab=new Vector(); 
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen; 
    String strCodCli="";
    String strDesCli="";
    String strCodSol="";
    String strDesSol="";
    String strTipPer_emp="";
    StringBuffer stbDevTemp, stbDevInvTemp, stbLisBodItmTemp, stbDocRelEmpTemp;
    public StringBuffer stbLisBodItm;
    public StringBuffer stbDocRelEmp;
    double bldivaEmp=0;
    double Glo_dblCanFalCom=0.00;
    double Glo_dblCanFalDevCom=0.00;
    double Glo_dblCanFalDevVen=0.00;

    private ZafDatePicker txtFecCliRet;

    
    double Glo_dblTotDevCom=0.00;
    double Glo_dblTotDevVen=0.00;

    boolean blnEstDevVen=false;
    boolean blnEstDevCom=false;

    double Glo_dblCanTotDevCom=0.00;
    int intConStbBodTemp=0;
    int intCodBodPre=0;
    int intCodTipPerEmp=0;
    int intEstReaOcFac=0;
    int INTCODREGCEN=0;
    int intDocRelEmp=0, intDocRelEmpTemp=0;
    int intBodPreDevCom=0;
    double dblCanDevComPar=0;
    private String Str_RegSel[]; 
    public boolean blnAcepta = false; 
    ArrayList arlAuxColEdi=new java.util.ArrayList();
    private ArrayList arlDat;
    private ArrayList arlReg;

    StringBuffer stbDatVen=null;
    StringBuffer stbDatDevVenCom=null;
    StringBuffer stbDatInsVen=null;
    int intDatVen=0;
    int intDatDevVenCom=0;
     
    String StrEstConfDevVen="P";
    String strNomBodIng="";

    int codDoc;
    StringBuffer stbDatDevCom=null;
    StringBuffer stbDatDevVen=null;
    int intDatDevCom=0;
    int intDatDevVen=0;
    
    private String strTitu="Pedidos de Terminales L";
    private String strVersion=" v0.1";
    
    private ZafTblCelEdiButVco objTblCelEdiButVcoForPag;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoForDes;//Editor: JButton en celda (Bodega origen).
    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoForPag;           //Editor: JTextField de consulta en celda.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblEdi objTblEdi;       
    private int  intCodTipDocFacEle_ZafVen01_06 = 228;

    ArrayList arlItmRec;
  

/* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
   final int INT_ARL_COD_EMP=0;
   final int INT_ARL_COD_LOC=1;
   final int INT_ARL_COD_COT=2;   
   final int INT_ARL_COD_ITM=3;
   final int INT_ARL_CAN_COM=4;
   final int INT_ARL_COD_EMP_EGR=5;
   final int INT_ARL_COD_BOD_EMP_EGR=6;
   final int INT_ARL_COD_ITM_EGR=7;
   final int INT_ARL_CAN_UTI=8;
   
   
    ArrayList arlSolTraDat = new ArrayList();
    ArrayList arlSolTraReg = new ArrayList();
    private ZafTblOrd objTblOrd;
   
   
      /* CONSTANTES PARA CONTENEDOR A ENVIAR SOLICITUD DE TRANSFERENCIA JoséMario 26/Abril/2015  */
   final int INT_ARL_SOL_TRA_COD_BOD_ING=0;
   final int INT_ARL_SOL_TRA_COD_BOD_EGR=1;
   final int INT_ARL_SOL_TRA_ITM_GRP=2;
   final int INT_ARL_SOL_TRA_CAN=3;
   final int INT_ARL_SOL_PES_UNI=4;
   final int INT_ARL_SOL_PES_TOT=5;
   
   public boolean blnGenSolTra=false;
            
   private int intCodBodEmp;
   private String strNomBodEmp;
   private java.awt.Frame Frame;
   
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
   

    public ZafVen42_04() {
    }
    
    private int intCodEmpGenVen, intCodLocGenVen, intCodCotGenVen;
    /** Creates new form pantalla dialogo */
    public ZafVen42_04(Frame parent,  ZafParSis ZafParSis, int intCodEmp, int intCodLoc, int intCodCot) {
        super(parent, true);
        try{ 
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
         
          objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

          ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
          stbLisBodItm= new StringBuffer();
          stbDocRelEmp= new StringBuffer();

          initComponents();
          intCodEmpGenVen=intCodEmp;intCodLocGenVen=intCodLoc;intCodCotGenVen=intCodCot;
          configurarFrm();       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
    private void configurarFrm(){
          configurarFormaPago();
          configurarFormaDespacho();
          if(configuraTbl()){
              cargarDat(intCodEmpGenVen,intCodLocGenVen,intCodCotGenVen);
          }
          if(intEstReaOcFac==0){
                blnAcepta = true;
                dispose();
          }

    } 

    private Vector vecDat,vecReg, vecAux ;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    
    private boolean configuraTbl(){
      //Configurar JTable: Establecer el modelo.
        boolean blnRes=true;
        try{
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CODPRO,"Cód.Pro.");
            vecCab.add(INT_TBL_NOMPRO,"Nom.Pro.");
            vecCab.add(INT_TBL_CODFORPAG,"Cód.For.Pag.");
            vecCab.add(INT_TBL_NOMFORPAG,"Nom.For.Pag.");
            vecCab.add(INT_TBL_BTNFORPAG,"...");
            vecCab.add(INT_TBL_OBS,"Observacion");
            vecCab.add(INT_TBL_BTNDATCODL,"...");
            vecCab.add(INT_TBL_COD_FOR_DET,"Cod.For.Des.");
            vecCab.add(INT_TBL_FOR_DET,"For.Des.");
            vecCab.add(INT_TBL_BTNFORDES,"...");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LINEA);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODPRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMPRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CODFORPAG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMFORPAG).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_BTNFORPAG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BTNDATCODL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_COD_FOR_DET).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FOR_DET).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BTNFORDES).setPreferredWidth(30);
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_NOMFORPAG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FOR_DET).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCod.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_CODPRO).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_CODFORPAG).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_COD_FOR_DET).setCellRenderer(objTblCelRenLblCod);
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_CODFORPAG);
//            vecAux.add("" + INT_TBL_NOMFORPAG);
            vecAux.add("" + INT_TBL_BTNFORPAG);
            vecAux.add("" + INT_TBL_OBS);
            vecAux.add("" + INT_TBL_BTNDATCODL);
//            vecAux.add("" + INT_TBL_COD_FOR_DET);
//            vecAux.add("" + INT_TBL_FOR_DET);
            vecAux.add("" + INT_TBL_BTNFORDES);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
             
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BTNFORPAG).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BTNDATCODL).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BTNFORDES).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            
            
            
            
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_CODFORPAG;
            intColTbl[1]=INT_TBL_NOMFORPAG;
            
            objTblCelEdiTxtVcoForPag=new ZafTblCelEdiTxtVco(tblDat, vcoForPag, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_CODFORPAG).setCellEditor(objTblCelEdiTxtVcoForPag);
            objTblCelEdiTxtVcoForPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoForPag.setCampoBusqueda(0);
                    vcoForPag.setCriterio1(11);
                }
            });
            objTblCelEdiButVcoForPag=new ZafTblCelEdiButVco(tblDat, vcoForPag, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_BTNFORPAG).setCellEditor(objTblCelEdiButVcoForPag);
            objTblCelEdiButVcoForPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            intColVen=null;
            intColTbl=null;
             
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_COD_FOR_DET;
            intColTbl[1]=INT_TBL_FOR_DET;
            objTblCelEdiButVcoForDes=new ZafTblCelEdiButVco(tblDat, vcoForDes, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_BTNFORDES).setCellEditor(objTblCelEdiButVcoForDes);
            objTblCelEdiButVcoForDes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            intColVen=null;
            intColTbl=null;
            
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BTNDATCODL).setCellEditor(objTblCelEdiButGen);
             
             
            new ButDetalleItemsL(tblDat, INT_TBL_BTNDATCODL);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);           
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }    
        
        return blnRes;
  }
    private ZafTblBus objTblBus;
    private ZafDocLis objDocLis;
    private boolean blnHayCam;
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    
    
    
    private class ButDetalleItemsL extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButDetalleItemsL(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl, intIdx, "Solicitud de Reservas.");
        }

        public void butCLick(){
            cargarVentanaDetalleItemsL();
        }
    }
     
    private void cargarVentanaDetalleItemsL()
    {
        int  intCodPrv =  Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODPRO).toString()) ;
        Ventas.ZafVen42.ZafVen42_05 obj = new Ventas.ZafVen42.ZafVen42_05(javax.swing.JOptionPane.getFrameForComponent(this),objParSis,intCodEmpGenVen,intCodLocGenVen,intCodCotGenVen,intCodPrv) ;
        //this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
          obj.show();
        //obj.setVisible(true);
    }
     
    
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }
    }
    
    
     /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener{
        public void tableChanged(javax.swing.event.TableModelEvent e){
            switch (e.getType()){
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

    /*
     * llenar la consulta de marcas  
     */
    
    private boolean configurarFormaPago()
    {
        boolean blnRes=true;
        try
        {
            System.out.println("configurarFormaPago::....");
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forPag");
            arlCam.add("a1.tx_des");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_forpag, a1.tx_des FROM tbm_cabForPag as a1 ";
            strSQL+=" Where a1.co_emp = " + intCodEmpGenVen + " order by a1.co_forpag";
            
            System.out.println("configurarMarcas:.." + strSQL);
            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Formas de Pago", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarFormaDespacho()
    {
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_reg");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL="select co_reg,tx_deslar from tbm_var where co_grp=10 ";
            System.out.println("configurarFormaDespacho:.." + strSQL);
            vcoForDes=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Formas de Despacho", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
     
    private boolean cargarDat(  int intCodEmp, int intCodLoc, int intCodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         Statement stmLoc;
         ResultSet rstLoc;
         try{    
             System.out.println("cargarDat");
             conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
             if(conLoc!=null){
                stmLoc=conLoc.createStatement();  
                strSql="";
                strSql+=" SELECT a2.co_prv, a3.tx_nom \n";
                strSql+=" FROM tbm_cabCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_cli as a3 ON (a2.co_emp=a3.co_emp AND a2.co_prv=a3.co_cli) \n";
                strSql+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot+" and a2.tx_codAlt like '%L' \n";
                strSql+=" GROUP BY a2.co_prv, a3.tx_nom \n";
                System.out.println("cargar ZafCom92:::.. " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODPRO,rstLoc.getString("co_prv")==null?"":rstLoc.getString("co_prv"));
                    vecReg.add(INT_TBL_NOMPRO,rstLoc.getString("tx_nom")==null?"":rstLoc.getString("tx_nom"));
                    vecReg.add(INT_TBL_CODFORPAG,"");
                    vecReg.add(INT_TBL_NOMFORPAG,"");
                    vecReg.add(INT_TBL_BTNFORPAG,"");
                    vecReg.add(INT_TBL_OBS,"");
                    vecReg.add(INT_TBL_BTNDATCODL,"");
                    vecReg.add(INT_TBL_COD_FOR_DET,"");
                    vecReg.add(INT_TBL_FOR_DET,"");
                    vecReg.add(INT_TBL_BTNFORDES,"");
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                rstLoc=null;
                
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear(); 
                
                stmLoc.close();
                stmLoc=null;
                
                

                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);    

                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
                conLoc.close();
                conLoc=null;
                blnRes=true;
            }
           
         }
         catch(java.sql.SQLException Evt) {  
             blnRes=false; 
             objUti.mostrarMsgErr_F1(this, Evt);  
         }
         catch(Exception Evt) { 
             blnRes=false; 
             objUti.mostrarMsgErr_F1(this, Evt); 
         }
        return blnRes;          
    }   




 
 
 

 
private void MensajeInf(String strMensaje){
 //JOptionPane obj =new JOptionPane();
 String strTit="Mensaje del sistema Zafiro";
 JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
}

     

  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpInsGenFac = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(100, 48));
        panTit.setLayout(null);

        lblTit.setText("titulo");
        panTit.add(lblTit);
        lblTit.setBounds(210, 7, 300, 14);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

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
        scrTbl.setViewportView(tblDat);

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-641)/2, (screenSize.height-470)/2, 641, 470);
    }// </editor-fold>//GEN-END:initComponents

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
         
}//GEN-LAST:event_formWindowOpened

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
     
    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
    try{
        blnAcepta = false; 
        if(validacion()){
            if(generaContenedor()){
                System.out.println(">>> = ZafVen42_03 arlDatGenCom " + arlDatGenCom.toString());
                blnAcepta = true; 
                this.setVisible(false);
            } 
        }
        else{
            MensajeInf("FALTA INGRESAR UNA FORMA DE PAGO");
        }
               
        
         //dispose();
        
    }  
      catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(null, Evt); 
          blnAcepta = false; 
      }
}//GEN-LAST:event_butAceActionPerformed



    private boolean validacion(){
        boolean blnRes=true;
        try{ 
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i, INT_TBL_CODPRO)!=null) {
                    if(tblDat.getValueAt(i, INT_TBL_CODFORPAG)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_CODFORPAG).toString().equals("")) {
                            blnRes=false;
                            MensajeInf("Debe seleccionar una forma de pago");
                        } 
                    }else{
                        blnRes=false;
                        MensajeInf("Debe seleccionar una forma de pago");
                    } 
                    
                    if(tblDat.getValueAt(i, INT_TBL_COD_FOR_DET)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_COD_FOR_DET).toString().equals("")) {
                            blnRes=false;
                            MensajeInf("Debe seleccionar una forma de despacho");
                        } 
                    }
                    else{
                        blnRes=false;
                        MensajeInf("Debe seleccionar una forma de despacho");
                    }
                }
            }
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }

    final int INT_DAT_COD_EMP =0; 
    final int INT_DAT_COD_LOC =1;       
    final int INT_DAT_COD_COT =2;       
    final int INT_DAT_COD_FOR_PAG=3;       
    final int INT_DAT_OBS=4;      
    final int INT_DAT_COD_PRV=5;
    final int INT_DAT_COD_FOR_DES=6;
    
    
    public ArrayList arlDatGenCom, arlRegGenCom;
    
    private boolean generaContenedor(){
        boolean blnRes=false;
        try{          
            arlDatGenCom = new ArrayList();
            for(int i=0; i<tblDat.getRowCount(); i++){
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                    if(tblDat.getValueAt(i, INT_TBL_CODPRO)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_CODFORPAG)!=null) {
                           arlRegGenCom = new ArrayList();
                           arlRegGenCom.add(INT_DAT_COD_EMP, intCodEmpGenVen);
                           arlRegGenCom.add(INT_DAT_COD_LOC, intCodLocGenVen);
                           arlRegGenCom.add(INT_DAT_COD_COT, intCodCotGenVen);    
                           arlRegGenCom.add(INT_DAT_COD_FOR_PAG, Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODFORPAG).toString()));
                           arlRegGenCom.add(INT_DAT_OBS, tblDat.getValueAt(i, INT_TBL_OBS)!=null?tblDat.getValueAt(i, INT_TBL_OBS).toString():"");
                           arlRegGenCom.add(INT_DAT_COD_PRV, Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODPRO).toString()));
                           arlRegGenCom.add(INT_DAT_COD_FOR_DES, Integer.parseInt(tblDat.getValueAt(i, INT_TBL_COD_FOR_DET).toString()));
                           arlDatGenCom.add(arlRegGenCom);
                        }
                    }
                }
            }     
            blnRes=true;
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    } 

    
    public ArrayList getArlDatResTerL() {
        return arlDatGenCom;
    }
     
 
       
    private void cerrarVen(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            System.gc();
            blnAcepta=false;
            dispose();
        }
    }
    

   
    public boolean acepta(){
      return blnAcepta;
    }
 
    
 
    public String GetCamSel(int Idx){
        if(!(Str_RegSel==null)){
            if(Idx <= 0 || Idx > Str_RegSel.length)
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length) ;
            else
                return Str_RegSel[Idx-1];
        }else{
            return "";
        }
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup grpInsGenFac;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
 



    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
          
                case INT_TBL_CODPRO: strMsg="Código del Proveedor"; break;
                case INT_TBL_NOMPRO: strMsg="Nombre del Proveedor"; break;
                case INT_TBL_CODFORPAG: strMsg="Código de la Forma de Pago"; break;
                case INT_TBL_NOMFORPAG: strMsg="Forma de Pago"; break;
                case INT_TBL_BTNFORPAG: strMsg="Botón: Muestra las forma de pago"; break;
                case INT_TBL_OBS: strMsg="Observacion de la Forma de Pago"; break;
                case INT_TBL_BTNDATCODL: strMsg="Muestra los items que iran en la Orden de Compra"; break;
                
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 

    
    
}

