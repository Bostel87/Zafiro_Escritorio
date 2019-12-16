

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Ventas.ZafVen01;
 
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
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
                 
/**     
 * @author  Jose Mario Marin
 */
public class ZafVen01_42 extends javax.swing.JDialog {
    
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
   
 
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafUtil objUti;
     
    private java.util.Date datFecAux;      
     
    
 
    
    
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
    
    private String strTitu="Zafiro: Sistema de Reservas";
    private String strVersion=" v0.03";
    
    private ZafTblCelEdiButVco objTblCelEdiButVcoForPag;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoForPag;           //Editor: JTextField de consulta en celda.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblEdi objTblEdi;       
    

    ArrayList arlItmRec;
  
 
   
   /*Datos para enviar al ZafVen01 */
   private final int INT_TBL_LINEA=0;                       //Numero de linea de la tabla
   
   private final int INT_TBL_CODEMP=1;                       //Numero de linea de la tabla
   private final int INT_TBL_CODLOC=2;                       //Numero de linea de la tabla
   private final int INT_TBL_CODCOT=3;                       //Numero de linea de la tabla
   private final int INT_TBL_CODREGCOT=4;                       //Numero de linea de la tabla
   private final int  INT_TBL_ITMALT=5;                     //Codigo del item alterno
   private final int  INT_TBL_DESITM=6;
   private final int  INT_TBL_PREUNI=7;
   private final int  INT_TBL_CODITM=8;
   private final int  INT_TBL_IVATXT=9;
   private final int  INT_TBL_UNIDAD=10;
   private final int  INT_TBL_ITMALT2=11; 
   private final int  INT_TBL_ITMSER=12;
   private final int  INT_TBL_ITMTER=13;
   private final int  INT_TBL_MARUTI=14;
   private final int  INT_TBL_IEBODFIS=15;
   private final int  INT_TBL_MODNOMITM=16;
   private final int  INT_TBL_PRELISITM=17;
   private final int  INT_TBL_PRELISITM2=18;
   private final int  INT_TBL_TIPUNIMED=19;
   private final int  INT_TBL_BLOPREVTA=20;
   private final int  INT_TBL_DESITMORI=21;
   private final int  INT_TBL_CODCTAEGR=22;
   private final int  INT_TBL_PESITM=23;
   private final int  INT_TBL_CANRES=24;
   private final int  INT_TBL_CANRESUTI=25;
   private final int  INT_TBL_CAN=26;
   private final int  INT_TBL_CAN_RES_INVBOD=27;
   private final int  INT_TBL_CAN_STK_INVBOD=28;
   private final int  INT_TBL_CAN_DIS_INVBOD=29; 
   
   
    ArrayList arlSolTraDat = new ArrayList();
    ArrayList arlSolTraReg = new ArrayList();
    private ZafTblOrd objTblOrd;
   
 
    
             
   private java.awt.Frame Frame;
   
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
   

    public ZafVen01_42() {
    }
    
    private int intCodEmpGenVen, intCodLocGenVen, intCodCotCliGenVen;
    
    /** Creates new form pantalla dialogo */
    public ZafVen01_42(Frame parent,  ZafParSis ZafParSis, int intCodEmp, int intCodLoc, int intCodCli) {
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
          intCodEmpGenVen=intCodEmp;intCodLocGenVen=intCodLoc;intCodCotCliGenVen=intCodCli;
          configurarFrm();       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
    private void configurarFrm(){
          if(configuraTbl()){
              if(cargarDat(intCodEmpGenVen,intCodLocGenVen,intCodCotCliGenVen)){
                  blnAcepta = true;
                  dispose();
              }else{
                  System.err.println("Error configurarFrm");
                  blnAcepta=false;
              }
          }
    } 

    private Vector vecDat,vecReg, vecAux ;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    
    private boolean configuraTbl(){
      //Configurar JTable: Establecer el modelo.
        boolean blnRes=true;
        try{
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");
            
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_CODCOT,"Cod.Cot.");
            vecCab.add(INT_TBL_CODREGCOT,"Cod.Cot.");
            
            
            vecCab.add(INT_TBL_ITMALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DESITM,"Nom.Itm.");
            vecCab.add(INT_TBL_PREUNI,"Pre.Uni.");
            vecCab.add(INT_TBL_CODITM,"Cod.Itm.");
            vecCab.add(INT_TBL_IVATXT,"IVA");
            vecCab.add(INT_TBL_UNIDAD,"Uni");
            vecCab.add(INT_TBL_ITMALT2,"Cod.Alt.2");
            vecCab.add(INT_TBL_ITMSER,"servicio");
            vecCab.add(INT_TBL_ITMTER,"terminal");
            vecCab.add(INT_TBL_MARUTI,"Mar.Uti.");
            vecCab.add(INT_TBL_IEBODFIS,"IE");
            vecCab.add(INT_TBL_MODNOMITM,"modNomItm");
            vecCab.add(INT_TBL_PRELISITM,"Pre.Lis.");
            vecCab.add(INT_TBL_PRELISITM2,"Pre.Lis2");
            vecCab.add(INT_TBL_TIPUNIMED,"Tip.Uni.Med.");
            vecCab.add(INT_TBL_BLOPREVTA,"bloPreVen");
            vecCab.add(INT_TBL_DESITMORI,"DesItmOri");
            vecCab.add(INT_TBL_CODCTAEGR,"CodCtaEgr");
            vecCab.add(INT_TBL_PESITM,"Pes.Itm.");
            vecCab.add(INT_TBL_CANRES,"Can.Res.");
            vecCab.add(INT_TBL_CANRESUTI,"Can.Uti.");
            vecCab.add(INT_TBL_CAN,"Cantidad");
            
            vecCab.add(INT_TBL_CAN_RES_INVBOD,"Can.Res.Dis.");
            vecCab.add(INT_TBL_CAN_STK_INVBOD,"StkAct:InvBod");
            vecCab.add(INT_TBL_CAN_DIS_INVBOD,"canDis:InvBod");
            
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
            
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODCOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODREGCOT).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_IVATXT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_ITMALT2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_ITMSER).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_ITMTER).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_MARUTI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_IEBODFIS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_MODNOMITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_PRELISITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_PRELISITM2).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TIPUNIMED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BLOPREVTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESITMORI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODCTAEGR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_PESITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANRES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANRESUTI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CAN).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_CAN_RES_INVBOD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CAN_STK_INVBOD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CAN_DIS_INVBOD).setPreferredWidth(70);
            
             
            
             //columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_CODREGCOT);
            arlColHid.add(""+INT_TBL_CODITM);
            arlColHid.add(""+INT_TBL_IVATXT);  
            arlColHid.add(""+INT_TBL_ITMSER);  
            arlColHid.add(""+INT_TBL_ITMTER);  
            arlColHid.add(""+INT_TBL_MARUTI);  
            arlColHid.add(""+INT_TBL_IEBODFIS);  
            arlColHid.add(""+INT_TBL_MODNOMITM);  
            arlColHid.add(""+INT_TBL_PRELISITM);  
            arlColHid.add(""+INT_TBL_PRELISITM2);  
            arlColHid.add(""+INT_TBL_TIPUNIMED);  
            arlColHid.add(""+INT_TBL_BLOPREVTA);  
            arlColHid.add(""+INT_TBL_DESITMORI);  
            arlColHid.add(""+INT_TBL_CODCTAEGR);  
            
//            arlColHid.add(""+INT_TBL_CAN_RES_INVBOD);
            arlColHid.add(""+INT_TBL_CAN_STK_INVBOD);
            arlColHid.add(""+INT_TBL_CAN_DIS_INVBOD);
            
            
   
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            //Configurar JTable: Alineamiento de columnas, formato a las columnas para los decimales por ejemplo
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            tcmAux.getColumn(INT_TBL_PESITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_CANRES).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_CANRESUTI).setCellRenderer(objTblCelRenLblNum);
//            tcmAux.getColumn(INT_TBL_CAN).setCellRenderer(objTblCelRenLblNum); 
            
            tcmAux.getColumn(INT_TBL_CAN_RES_INVBOD).setCellRenderer(objTblCelRenLblNum); 
            tcmAux.getColumn(INT_TBL_CAN_STK_INVBOD).setCellRenderer(objTblCelRenLblNum); 
            tcmAux.getColumn(INT_TBL_CAN_DIS_INVBOD).setCellRenderer(objTblCelRenLblNum); 
            
            
            objTblCelRenLbl3=new ZafTblCelRenLbl();
            objTblCelRenLbl3.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CAN).setCellRenderer(objTblCelRenLbl3); 
            
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_CAN);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
            
            
            
            /* Validaciones */
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        double dlbVal=0, dblReservado=0, dblReservaAutorizada=0, dblReservaUtilizada=0;
                        if(tblDat.getValueAt(intNumFil,INT_TBL_CAN)!=null ){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_CAN).toString().length()>0){
                                dlbVal=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_CAN).toString(), 4 );
                            }
                        }
                        if(tblDat.getValueAt(intNumFil,INT_TBL_CAN_RES_INVBOD)!=null ){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_CAN_RES_INVBOD).toString().length()>0){
                                dblReservado=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_CAN_RES_INVBOD).toString(), 4 );
                            }
                        }
                        if(tblDat.getValueAt(intNumFil,INT_TBL_CAN_RES_INVBOD)!=null ){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_CAN_RES_INVBOD).toString().length()>0){
                                dblReservado=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_CAN_RES_INVBOD).toString(), 4 );
                            }
                        }
                        if(tblDat.getValueAt(intNumFil,INT_TBL_CANRES)!=null &&  tblDat.getValueAt(intNumFil,INT_TBL_CANRESUTI)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_CANRES).toString().length()>0){
                                dblReservaAutorizada=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_CANRES).toString(), 4 );
                            }
                            if(tblDat.getValueAt(intNumFil,INT_TBL_CANRESUTI).toString().length()>0){
                                dblReservaUtilizada=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_CANRESUTI).toString(), 4 );
                            }
                        }
                        
//                        if(controlMismoItemDiferenteCotizacion(intNumFil)){
//                            MensajeInf("No se permite seleccionar cantidades sobre el mismo item dos veces ");
//                            tblDat.setValueAt("0", intNumFil,INT_TBL_CAN);
//                        }
                        
                        if(dlbVal < 0 ){
                            MensajeInf("Solo se permite ingresar valores enteros ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_CAN);
                        }
                        if(dlbVal>dblReservado){
                            MensajeInf("La cantidad ingresada excede el valor en Reserva disponoble ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_CAN);
                        }
                        if(dlbVal>(dblReservaAutorizada-dblReservaUtilizada)){
                            MensajeInf("La cantidad ingresada excede el valor en Reserva disponible para el cliente");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_CAN);
                        }
                        
                        
                        
                    }                   
                }
            });
            /*-----*/
            

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
    
    
    /**
     * Validacion para el mismo item
     * @param FilSel
     * @return 
     * 
     */
    
    private boolean controlMismoItemDiferenteCotizacion(int FilSel){
        boolean blnRes = false;
        try{
            for (int i = 0; i < tblDat.getRowCount(); i++){
                if (tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                    if(tblDat.getValueAt(i, INT_TBL_CAN)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_CAN).toString().length()>0) {
                            if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CAN).toString())>0 ) {
                                if (i != FilSel){
                                    if (tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(tblDat.getValueAt(FilSel, INT_TBL_CODITM).toString())){
                                        blnRes = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
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

     private String strSql;
     
    private boolean cargarDat(  int intCodEmp, int intCodLoc, int intCodCli){
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
                strSql+=" SELECT RES.co_emp,RES.co_loc,RES.co_cot,RES.co_reg,a.tx_codalt, a.co_itm, a.tx_nomitm,  ROUND(a.nd_prevta1,2) as nd_preVta1, a.st_ivaven, a1.tx_descor, \n";
                strSql+="        a.tx_codalt as tx_codalt2, a.st_ser, a.nd_maruti, a.st_permodnomitmven, case when a.nd_pesitmkgr is null then 0 else ROUND(a.nd_pesitmkgr,2) end as nd_pesItmKgr  \n";
                strSql+="        ,CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN  \n";
                strSql+="        ( SELECT UPPER(trim(tx_cad)) FROM tbm_reginv WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+"  AND co_tipdoc IN (1,228) AND co_usr="+objParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C'  ))) THEN 'S'  WHEN  \n";
                strSql+="        ( (TRIM(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN  \n";
                strSql+="        ( SELECT UPPER(trim(tx_cad))  FROM tbm_reginv  \n";
                strSql+="        WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+"  AND co_tipdoc IN (1,228) AND co_usr="+objParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I'  ))) THEN 'I'  ELSE 'N' END  as isterL ,  \n";
                strSql+="        0 as nd_stkActcen,  \n";
                strSql+="        CASE WHEN ( (TRIM(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN (   \n";
                strSql+="        SELECT  UPPER(trim(a1.tx_cad))  \n";
                strSql+="        FROM tbr_bodloc as a  \n";
                strSql+="        inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod)   \n";
                strSql+="        WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' )))  THEN 'S' ELSE 'N' END AS proconf, \n";
                strSql+="        a1.tx_tipunimed  , a.st_blqprevta, a2.nd_caningbod, a.co_ctaegr, \n";
                strSql+="        CASE WHEN a2.st_impOrd='S'  THEN a.tx_codAlt2 ELSE '' END as tx_codAlt3Letras  , \n";
                strSql+="        a2.co_emp,a2.co_bod,a2.co_itm,CASE WHEN a2.nd_stkAct IS NULL THEN 0 ELSE a2.nd_stkAct END AS nd_stkAct, \n";
                strSql+="        CASE WHEN a2.nd_canDis IS NULL THEN 0 ELSE a2.nd_canDis END AS nd_canDis,   \n";
                strSql+="        CASE WHEN a2.nd_canResVen IS NULL THEN 0 ELSE a2.nd_canResVen END AS nd_canResVen,  \n";
                strSql+="        CASE WHEN a2.nd_canRes IS NULL THEN 0 ELSE a2.nd_canRes END AS nd_canRes,   \n";
                strSql+="        CASE WHEN RES.nd_canAutRes IS NULL THEN 0 ELSE RES.nd_canAutRes END AS nd_canAutRes,    \n";
                strSql+="        CASE WHEN RES.nd_canFacRes IS NULL THEN 0 ELSE RES.nd_canFacRes END AS nd_canFacRes  \n";
                strSql+=" FROM tbm_inv AS a  \n";
                strSql+=" LEFT JOIN tbm_var AS a1 ON (a1.co_reg=a.co_uni and a1.co_grp=5)  \n";
                strSql+=" INNER JOIN tbm_invbod AS a2 ON (a2.co_emp = a.co_emp AND a2.co_itm=a.co_itm AND a2.co_bod = \n";
                strSql+="                                 ( select co_bod from tbr_bodloc where co_emp="+intCodEmp+" and co_loc="+intCodLoc+"   and st_reg='P'  )  )  \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a1.co_emp, a1.co_loc, a1.co_cot,a2.co_reg, a2.co_itm, SUM( CASE WHEN a3.nd_canAut IS NULL THEN 0 ELSE a3.nd_canAut END) as nd_canAutRes, \n";
                strSql+="               SUM(CASE WHEN a3.nd_canFac IS NULL THEN 0 ELSE a3.nd_canFac END) as nd_canFacRes ,  \n";
                strSql+="               SUM(CASE WHEN a3.nd_canAut IS NULL THEN 0 ELSE a3.nd_canAut END) as nd_canAut   \n";
                strSql+="       FROM tbm_cabCotVen AS a1 \n";
                strSql+="       INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="       INNER JOIN tbm_pedOtrBodCotVen AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cot=a3.co_cot AND a2.co_reg=a3.co_reg) \n";
                strSql+="       INNER JOIN tbm_TipSolResInv as a4 ON (a1.co_tipSolResInv=a4.co_tipSolResInv) \n";
                strSql+="       WHERE a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+" and a1.co_cli="+intCodCli+" AND a1.st_solResInv='P' AND   \n";
                strSql+="             a1.st_autSolResInv  = 'A' AND a4.tx_tipResInv = 'R'  \n";
                strSql+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_cot,a2.co_reg,a2.co_itm  \n";
                strSql+=" ) as RES ON (a2.co_emp=RES.co_emp AND a2.co_itm=RES.co_itm AND a2.co_bod= ( select co_bod from tbr_bodloc where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and st_reg='P'  )  ) \n";
                strSql+=" INNER JOIN tbm_equInv as a4 ON (a.co_emp=a4.co_emp AND a.co_itm=a4.co_itm)  \n";
                strSql+=" WHERE a.co_emp="+intCodEmp+" AND  a.st_reg = ('A') \n";
                strSql+=" ORDER BY a.tx_codalt \n";
                strSql+=" \n";
                System.out.println("cargar ZafVen01_42:::.. " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    
                    vecReg.add(INT_TBL_CODEMP,rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC,rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_CODCOT,rstLoc.getString("co_cot"));
                    vecReg.add(INT_TBL_CODREGCOT,rstLoc.getString("co_reg"));
                    
                    vecReg.add(INT_TBL_ITMALT,rstLoc.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DESITM,rstLoc.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_PREUNI,rstLoc.getString("nd_preVta1"));
                    vecReg.add(INT_TBL_CODITM,rstLoc.getString("co_itm"));
                    vecReg.add(INT_TBL_IVATXT,rstLoc.getString("st_ivaVen"));
                    vecReg.add(INT_TBL_UNIDAD,rstLoc.getString("tx_descor"));
                    vecReg.add(INT_TBL_ITMALT2,rstLoc.getString("tx_codAlt3Letras"));
                    vecReg.add(INT_TBL_ITMSER,rstLoc.getString("st_ser"));
                    vecReg.add(INT_TBL_ITMTER,rstLoc.getString("isterL"));
                    vecReg.add(INT_TBL_MARUTI,rstLoc.getString("nd_maruti"));
                    vecReg.add(INT_TBL_IEBODFIS,rstLoc.getString("proconf"));
                    vecReg.add(INT_TBL_MODNOMITM,rstLoc.getString("st_permodnomitmven"));
                    vecReg.add(INT_TBL_PRELISITM,rstLoc.getString("nd_preVta1"));
                    vecReg.add(INT_TBL_PRELISITM2,rstLoc.getString("nd_preVta1"));
                    vecReg.add(INT_TBL_TIPUNIMED,rstLoc.getString("tx_tipunimed"));
                    vecReg.add(INT_TBL_BLOPREVTA,rstLoc.getString("st_blqprevta"));
                    vecReg.add(INT_TBL_DESITMORI,rstLoc.getString("nd_caningbod"));
                    vecReg.add(INT_TBL_CODCTAEGR,rstLoc.getString("co_ctaegr"));
                    vecReg.add(INT_TBL_PESITM,rstLoc.getString("nd_pesitmkgr"));
                    vecReg.add(INT_TBL_CANRES,rstLoc.getString("nd_canAutRes"));
                    vecReg.add(INT_TBL_CANRESUTI,rstLoc.getString("nd_canFacRes"));
                    vecReg.add(INT_TBL_CAN,"");
                    vecReg.add(INT_TBL_CAN_RES_INVBOD,rstLoc.getString("nd_canRes"));
                    vecReg.add(INT_TBL_CAN_STK_INVBOD,rstLoc.getString("nd_stkAct"));
                    vecReg.add(INT_TBL_CAN_DIS_INVBOD,rstLoc.getString("nd_canDis"));
                   
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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
        if(generaContenedor()){
            System.out.println(">>> = ZafVen01_42 arlDatConRes " + arlDatConRes.toString());
            blnAcepta = true; 
            this.setVisible(false);
        } 
    }  
      catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(null, Evt); 
          blnAcepta = false; 
      }
}//GEN-LAST:event_butAceActionPerformed


  
    
    public ArrayList arlDatConRes, arlRegConRes;
    
   /*Datos para enviar al ZafVen01 */
   private final int INT_CON_LINEA=0;                       //Numero de linea de la tabla
   private final int  INT_CON_CODEMP=1;
   private final int  INT_CON_CODLOC=2;
   private final int  INT_CON_CODCOT=3;
   private final int  INT_CON_CODREGCOT=4;
   private final int  INT_CON_ITMALT=5;                     //Codigo del item alterno
   private final int  INT_CON_DESITM=6;
   private final int  INT_CON_PREUNI=7;
   private final int  INT_CON_CODITM=8;
   private final int  INT_CON_IVATXT=9;
   private final int  INT_CON_UNIDAD=10;
   private final int  INT_CON_ITMALT2=11; 
   private final int  INT_CON_ITMSER=12;
   private final int  INT_CON_ITMTER=13;
   private final int  INT_CON_MARUTI=14;
   private final int  INT_CON_IEBODFIS=15;
   private final int  INT_CON_MODNOMITM=16;
   private final int  INT_CON_PRELISITM=17;
   private final int  INT_CON_PRELISITM2=18;
   private final int  INT_CON_TIPUNIMED=19;
   private final int  INT_CON_BLOPREVTA=20;
   private final int  INT_CON_DESITMORI=21;
   private final int  INT_CON_CODCTAEGR=22;
   private final int  INT_CON_PESITM=23;
   private final int  INT_CON_CANRES=24;
   private final int  INT_CON_CANRESUTI=25;
   private final int  INT_CON_CAN=26;
    
    private boolean generaContenedor(){
        boolean blnRes=false;
        try{          
            arlDatConRes = new ArrayList();
            for(int i=0; i<tblDat.getRowCount(); i++){
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_CAN)!=null) {
                            if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CAN).toString())>0 ) {
                                arlRegConRes = new ArrayList();
                                arlRegConRes.add(INT_CON_LINEA,"");
                                arlRegConRes.add(INT_CON_CODEMP, tblDat.getValueAt(i, INT_TBL_CODEMP).toString() );
                                arlRegConRes.add(INT_CON_CODLOC, tblDat.getValueAt(i, INT_TBL_CODLOC).toString() );
                                arlRegConRes.add(INT_CON_CODCOT, tblDat.getValueAt(i, INT_TBL_CODCOT).toString() );
                                arlRegConRes.add(INT_CON_CODREGCOT, tblDat.getValueAt(i, INT_TBL_CODREGCOT).toString() );
                                
                                arlRegConRes.add(INT_CON_ITMALT, tblDat.getValueAt(i, INT_TBL_ITMALT).toString() );
                                arlRegConRes.add(INT_CON_DESITM, tblDat.getValueAt(i, INT_TBL_DESITM).toString() );
                                arlRegConRes.add(INT_CON_PREUNI, tblDat.getValueAt(i, INT_TBL_PREUNI).toString() );
                                arlRegConRes.add(INT_CON_CODITM, tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                                arlRegConRes.add(INT_CON_IVATXT, tblDat.getValueAt(i, INT_TBL_IVATXT).toString() );
                                arlRegConRes.add(INT_CON_UNIDAD, tblDat.getValueAt(i, INT_TBL_UNIDAD).toString() );
                                arlRegConRes.add(INT_CON_ITMALT2,  tblDat.getValueAt(i, INT_TBL_ITMALT2).toString() );
                                arlRegConRes.add(INT_CON_ITMSER,  tblDat.getValueAt(i, INT_TBL_ITMSER).toString() );
                                arlRegConRes.add(INT_CON_ITMTER,  tblDat.getValueAt(i, INT_TBL_ITMTER).toString() );
                                arlRegConRes.add(INT_CON_MARUTI,  tblDat.getValueAt(i, INT_TBL_MARUTI).toString() );
                                arlRegConRes.add(INT_CON_IEBODFIS,  tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString() );
                                arlRegConRes.add(INT_CON_MODNOMITM,  tblDat.getValueAt(i, INT_TBL_MODNOMITM).toString() );
                                arlRegConRes.add(INT_CON_PRELISITM,  tblDat.getValueAt(i, INT_TBL_PRELISITM).toString() );
                                arlRegConRes.add(INT_CON_PRELISITM2,  tblDat.getValueAt(i, INT_TBL_PRELISITM2).toString() );
                                arlRegConRes.add(INT_CON_TIPUNIMED,  tblDat.getValueAt(i, INT_TBL_TIPUNIMED).toString() );
                                arlRegConRes.add(INT_CON_BLOPREVTA,  tblDat.getValueAt(i, INT_TBL_BLOPREVTA)  );
                                arlRegConRes.add(INT_CON_DESITMORI,  tblDat.getValueAt(i, INT_TBL_DESITMORI).toString() );
                                arlRegConRes.add(INT_CON_CODCTAEGR,  tblDat.getValueAt(i, INT_TBL_CODCTAEGR) );
                                arlRegConRes.add(INT_CON_PESITM,  tblDat.getValueAt(i, INT_TBL_PESITM).toString() );
                                arlRegConRes.add(INT_CON_CANRES, "" );
                                arlRegConRes.add(INT_CON_CANRESUTI, "" );
                                arlRegConRes.add(INT_CON_CAN,  tblDat.getValueAt(i, INT_TBL_CAN).toString() );
                                arlDatConRes.add(arlRegConRes);
                            }
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

    
    public ArrayList getArlDatRes() {
        return arlDatConRes;
    }
    




/*******************************************************************************************/


 
       
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
          
                case INT_TBL_ITMALT: strMsg="Código Alterno"; break;
                case INT_TBL_DESITM: strMsg="Nombre del Item"; break;
                case INT_TBL_PREUNI: strMsg="Precio Unitario"; break;
                case INT_TBL_CANRES: strMsg="Cantiadad Reservada"; break;
                case INT_TBL_CANRESUTI: strMsg="Cantidad que ya ha sido Facturada"; break;
                case INT_TBL_CAN: strMsg="Cantidad a Facturar"; break;
                case INT_TBL_CAN_RES_INVBOD: strMsg="Cantidad a Reservada disponible"; break;
                     
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 

    
    
}

