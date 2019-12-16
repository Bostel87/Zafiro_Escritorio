

/*
 * Created on 21 de Septiembre del 2017
 */
           
package Ventas.ZafVen42; 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
                 
/**     
 * @author  Jota
 */
public class ZafVen42_10 extends javax.swing.JDialog {
    Connection CONN_GLO=null;
    Connection CONN_GLO_REM=null;
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    private UltDocPrint objUltDocPrint;
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafUtil objUti;
    private Connection con;
    private java.util.Date datFecAux;      
    private Statement stm;
    private ResultSet rst;
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_COD_EMP=1;      //CODIGO DEL ITEM 
    final int INT_TBL_COD_LOC=2;      //CODIGO DE LA EMPRESA
    final int INT_TBL_COD_COT=3;      //CODIGO DE LA BODEGA
    final int INT_TBL_COD_REG=4;      //NOMBRE DE LA EMPRESA
    final int INT_TBL_COD_ITM=5;      //NOMBRE DE LA BODEGA
    final int INT_TBL_COD_ALT=6;      //STOCK ACTUAL 
    final int INT_TBL_NOM_ITM=7;      //
    final int INT_TBL_CAN=8;      //CANTIDAD QUE SE COMPR
    final int INT_TBL_CAN_AUT=9;      //CODIGO ALTERNO 
    final int INT_TBL_CAN_PEN_FAC=10;     //NOMBRE DEL ITEM 
    final int INT_TBL_CAN_CAN=11;     //UNIDAD DE MEDIDA 
    final int INT_TBL_CHK_CAN=12;  //
    final int INT_TBL_NOUSAR=13;  //CODIGO DEL ITEM MAESTRO
    final int INT_TBL_NOUSAR2=14;  // 
    final int INT_TBL_NOUSAR3=15;  //TIPO DE MEDIDA
    
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
    
    private String strTitu="Cancelacion de Reservas";
    private String strVersion=" v0.01";
    
    
    ArrayList arlItmRec;
 
    
//    private final boolean blnReaDevComVen=true;
    private final boolean blnReaDevComVen=false;  /// NO DEVOLUCIONES EN VENTA
    
    private boolean blnGenFacIni=false;
   
    private String strEmpCompVen="";
    private String strEmpTrans="";
    

/* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
   private final int INT_ARL_COD_EMP=0;
   private final int INT_ARL_COD_LOC=1;
   private final int INT_ARL_COD_COT=2;   
   private final int INT_ARL_COD_ITM=3;
   private final int INT_ARL_CAN_COM=4;
   private final int INT_ARL_COD_EMP_EGR=5;
   private final int INT_ARL_COD_BOD_EMP_EGR=6;
   private final int INT_ARL_COD_ITM_EGR=7;
   private final int INT_ARL_CAN_UTI=8;
   private final int INT_ARL_COD_BOD_EGR_GRP=9;
   
   
   
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
   

    public ZafVen42_10() {
    }
    
    private int intCodEmp, intCodLoc, intCodCot;
    /** Creates new form pantalla dialogo */
    
    public ZafVen42_10(Frame parent,  ZafParSis ZafParSis, int CodEmp, int CodLoc, int CodCot) {
        super(parent, true);
        try{ 
          Frame=parent;
            System.out.println("ZafVen42_10");
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
          objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
          

          objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
          objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

          ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
          stbLisBodItm= new StringBuffer();
          stbDocRelEmp= new StringBuffer();

          initComponents();
          intCodEmp=CodEmp;intCodLoc=CodLoc;intCodCot=CodCot;
          configurarFrm();       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
    private void configurarFrm(){

          configuraTbl();      
          cargarDat(intCodEmp,intCodLoc,intCodCot);      
    } 
  
   
    private Vector vecData, vecReg;
 
    private boolean cargarDat(int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{    
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();  
                vecData = new Vector();
                strSql="";
                strSql+=" SELECT a2.co_emp, a2.co_loc,a2.co_cot, a2.co_reg,a2.co_itm,a2.tx_codAlt, a2.tx_nomItm, \n";
                strSql+="        ROUND(SUM(a2.nd_can),2) AS nd_can,    \n";
                strSql+="        ROUND(SUM(CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END),2) AS nd_canFac, \n";
                strSql+="        ROUND(SUM(CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END),2) AS nd_canCan, \n";
                strSql+="        ROUND(SUM(CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END),2) AS nd_canPenFac, \n";
                strSql+="        ROUND(SUM(CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END),2) AS nd_valFac , \n";
                strSql+="        ROUND(SUM(CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END),2) AS nd_valCan,  \n";
                strSql+="        ROUND(SUM(CASE WHEN a2.nd_canAutRes IS NULL THEN 0 ELSE a2.nd_canAutRes END),2) AS nd_canAutRes \n";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" \n";
                strSql+=" GROUP BY a2.co_emp, a2.co_loc,a2.co_cot, a2.co_reg \n";
                strSql+=" \n";
                System.out.println("ZafVen42_10.cargarDat Cancelacion: \n"+ strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add( INT_TBL_COD_EMP, rstLoc.getString("co_emp") );
                    vecReg.add( INT_TBL_COD_LOC, rstLoc.getString("co_loc")); 
                    vecReg.add( INT_TBL_COD_COT, rstLoc.getString("co_cot")); 
                    vecReg.add( INT_TBL_COD_REG, rstLoc.getString("co_reg") );   
                    vecReg.add( INT_TBL_COD_ITM, rstLoc.getString("co_itm") );   
                    vecReg.add( INT_TBL_COD_ALT, rstLoc.getString("tx_codAlt") );
                    vecReg.add( INT_TBL_NOM_ITM, rstLoc.getString("tx_nomItm") );
                    vecReg.add( INT_TBL_CAN,  rstLoc.getString("nd_can"));  
                    vecReg.add( INT_TBL_CAN_AUT,  rstLoc.getString("nd_canAutRes") );
                    vecReg.add( INT_TBL_CAN_PEN_FAC,  rstLoc.getString("nd_canPenFac") );
                    vecReg.add( INT_TBL_CAN_CAN,  rstLoc.getString("nd_canPenFac") );
                    vecReg.add( INT_TBL_CHK_CAN ,true);
                    vecReg.add( INT_TBL_NOUSAR, "" );
                    vecReg.add( INT_TBL_NOUSAR2, "" );
                    vecReg.add( INT_TBL_NOUSAR3, "" );
                    vecData.add(vecReg);
                }  
                
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);         
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);    
                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);

                blnRes=true;
            }
      }
      catch(java.sql.SQLException Evt) {  
          blnRes=false; 
          objUti.mostrarMsgErr_F1(Frame, Evt);  
      }
      catch(Exception Evt) { 
          blnRes=false; 
          objUti.mostrarMsgErr_F1(Frame, Evt); 
      }
       return blnRes;          
}   

   
 
    private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        String strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(Frame,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

    
    
  
  

  
    private boolean configuraTbl(){
           boolean blnRes=false;
           try{
                //Configurar JTable: Establecer el modelo.
                vecCab.clear();
                vecCab.add(INT_TBL_LINEA,"..");
                vecCab.add(INT_TBL_COD_EMP,"Cód.Emp.");
                vecCab.add(INT_TBL_COD_LOC,"Cód.Loc.");
                vecCab.add(INT_TBL_COD_COT,"Cód.Cot.");
                vecCab.add(INT_TBL_COD_REG,"Cód.Reg.");
                vecCab.add(INT_TBL_COD_ITM,"Cód.Itm.");
                vecCab.add(INT_TBL_COD_ALT,"Cód.Alt.");
                vecCab.add(INT_TBL_NOM_ITM,"Nom.Itm." );  
                vecCab.add(INT_TBL_CAN,"Cantidad" );
                vecCab.add(INT_TBL_CAN_AUT,"Can.Aut." );
                vecCab.add(INT_TBL_CAN_PEN_FAC,"Can.Pen.Fac." );
                vecCab.add(INT_TBL_CAN_CAN,"Can.Cancelada" );
                vecCab.add(INT_TBL_CHK_CAN,"Cancelar");
                vecCab.add(INT_TBL_NOUSAR,"");
                vecCab.add(INT_TBL_NOUSAR2,"" );
                vecCab.add(INT_TBL_NOUSAR3,"" );

                objTblMod=new ZafTblMod();
                objTblMod.setHeader(vecCab);
                tblDat.setModel(objTblMod);

                //Configurar JTable: Establecer tipo de selección.
                tblDat.setRowSelectionAllowed(true);
                tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);


                ZafMouMotAda objMouMotAda=new ZafMouMotAda();
                tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                TableColumnModel tcmAux=tblDat.getColumnModel();

                //Tamaño de las celdas
                tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
                tcmAux.getColumn(INT_TBL_COD_EMP).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_COD_REG).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_COD_ITM).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_COD_ALT).setPreferredWidth(70);
                tcmAux.getColumn(INT_TBL_NOM_ITM).setPreferredWidth(100);
                tcmAux.getColumn(INT_TBL_CAN).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CAN_AUT).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CAN_PEN_FAC).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CAN_CAN).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CHK_CAN).setPreferredWidth(30);

                tcmAux.getColumn(INT_TBL_NOUSAR3).setPreferredWidth(10);

                /////////////////////////////////////

              

               objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
                    @Override
                    public void beforeRender(ZafTblCelRenEvent evt) {
                        //Mostrar de color gris las columnas impares.

                        int intCell=objTblCelRenLbl.getRowRender();

                        String str=tblDat.getValueAt(intCell, INT_TBL_COD_LOC).toString();
                        if(str.equals("")){
                            objTblCelRenLbl.setBackground(Color.BLUE);
                            objTblCelRenLbl.setFont(new Font(objTblCelRenLbl.getFont().getFontName(), Font.BOLD,   objTblCelRenLbl.getFont().getSize()));
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        } 
                        if(str.equals("1")){
                            objTblCelRenLbl.setBackground(Color.ORANGE);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        } 
                        if(str.equals("2")){
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        } 
                        if(str.equals("4")){
                            objTblCelRenLbl.setBackground(Color.YELLOW);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        }

                        if(str.equals("0")){
                            objTblCelRenLbl.setBackground(Color.BLACK);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        } 

                    }
                });

              objTblCelRenLbl2.setBackground(objParSis.getColorCamposObligatorios());
              objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
              objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
              objTblCelRenLbl2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
              objTblMod.setColumnDataType(INT_TBL_CAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_CAN_AUT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN_AUT).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_CAN_PEN_FAC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN_PEN_FAC).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_CAN_CAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN_CAN).setCellRenderer(objTblCelRenLbl2);
              
              


              objTblCelRenLbl2.addTblCelRenListener(new ZafTblCelRenAdapter() {
                    @Override
                    public void beforeRender(ZafTblCelRenEvent evt) {
                        //Mostrar de color gris las columnas impares.

                        int intCell=objTblCelRenLbl2.getRowRender();

                        String str=tblDat.getValueAt(intCell, INT_TBL_COD_LOC).toString();
                        if(str.equals("")){
                            objTblCelRenLbl2.setBackground(Color.BLUE);
                            objTblCelRenLbl2.setFont(new Font(objTblCelRenLbl2.getFont().getFontName(), Font.BOLD,   objTblCelRenLbl2.getFont().getSize()));
                            objTblCelRenLbl2.setForeground(Color.WHITE);
                        } 
                        if(str.equals("1")){
                            objTblCelRenLbl2.setBackground(Color.ORANGE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);
                        } 
                        if(str.equals("2")){
                            objTblCelRenLbl2.setBackground(Color.WHITE);
                            objTblCelRenLbl2.setForeground(Color.BLACK);
                        } 
                        if(str.equals("4")){
                            objTblCelRenLbl2.setBackground(Color.YELLOW);
                            objTblCelRenLbl2.setForeground(Color.BLACK);
                        }

                        if(str.equals("0")){
                            objTblCelRenLbl2.setBackground(Color.BLACK);
                            objTblCelRenLbl2.setForeground(Color.BLACK);
                        } 
                       //objTblCelRenLbl2.setToolTipText(objTblCelRenLbl.getText());
                    }
                });

              objTblCelRenLbl3.setBackground(objParSis.getColorCamposObligatorios());
              objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
              objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);

              objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
              objTblMod.setColumnDataType(INT_TBL_COD_ALT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_COD_ALT).setCellRenderer(objTblCelRenLbl3);

              objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
              objTblMod.setColumnDataType(INT_TBL_CAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN).setCellRenderer(objTblCelRenLbl3);
              objTblCelRenLbl3.addTblCelRenListener(new ZafTblCelRenAdapter() {
                    @Override
                    public void beforeRender(ZafTblCelRenEvent evt) {
                        //Mostrar de color gris las columnas impares.

                        int intCell=objTblCelRenLbl3.getRowRender();

                        String strEstBod = (( (tblDat.getValueAt(intCell, INT_TBL_NOM_ITM)==null) || (tblDat.getValueAt(intCell, INT_TBL_NOM_ITM).toString().equals("")))?"V":tblDat.getValueAt(intCell, INT_TBL_NOM_ITM).toString());
                        String str=tblDat.getValueAt(intCell, INT_TBL_COD_LOC).toString();


                        String strCanCom = (( (tblDat.getValueAt(intCell, INT_TBL_CHK_CAN)==null) || (tblDat.getValueAt(intCell, INT_TBL_CHK_CAN).toString().equals("")))?"":tblDat.getValueAt(intCell, INT_TBL_CHK_CAN).toString());


                        if(str.equals("")){

                            objTblCelRenLbl3.setToolTipText(" "+strCanCom  );

                            objTblCelRenLbl3.setBackground(Color.BLUE);
                            objTblCelRenLbl3.setFont(new Font(objTblCelRenLbl2.getFont().getFontName(), Font.BOLD, objTblCelRenLbl2.getFont().getSize()));
                            objTblCelRenLbl3.setForeground(Color.WHITE);
                        } 
                        if(str.equals("1")){
                         if(!(strEstBod.trim().equals("V"))) {
                            objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                            objTblCelRenLbl3.setBackground(Color.LIGHT_GRAY);
                            objTblCelRenLbl3.setForeground(Color.BLACK);
                         }else{
                            objTblCelRenLbl3.setBackground(Color.ORANGE);
                            objTblCelRenLbl3.setForeground(Color.BLACK);
                         }  
                        } 
                        if(str.equals("2")){
                         if(!(strEstBod.trim().equals("V"))) {
                            objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                            objTblCelRenLbl3.setBackground(Color.LIGHT_GRAY);
                            objTblCelRenLbl3.setForeground(Color.BLACK);
                         }else{
                            objTblCelRenLbl3.setBackground(Color.WHITE);
                            objTblCelRenLbl3.setForeground(Color.BLACK);
                         }

                        }
                        if(str.equals("4")){
                         if(!(strEstBod.trim().equals("V"))) {  
                            objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                            objTblCelRenLbl3.setBackground(Color.LIGHT_GRAY);
                            objTblCelRenLbl3.setForeground(Color.BLACK);
                         }else{
                            objTblCelRenLbl3.setBackground(Color.YELLOW);
                            objTblCelRenLbl3.setForeground(Color.BLACK);
                        }}

                        if(str.equals("0")){
                            objTblCelRenLbl3.setBackground(Color.BLACK);
                            objTblCelRenLbl3.setForeground(Color.BLACK);
                        } 

                    }

               });

                /* Aqui se agrega las columnas que van 
                    ha hacer ocultas
                 * */

                ArrayList arlColHid=new ArrayList();
                arlColHid.add(""+INT_TBL_COD_EMP);
                arlColHid.add(""+INT_TBL_COD_LOC);
                arlColHid.add(""+INT_TBL_COD_COT);
                arlColHid.add(""+INT_TBL_COD_REG);
                arlColHid.add(""+INT_TBL_COD_ITM);

                arlColHid.add(""+INT_TBL_NOUSAR);
                arlColHid.add(""+INT_TBL_NOUSAR2);
                arlColHid.add(""+INT_TBL_NOUSAR3);

                objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
                arlColHid=null;

                tblDat.getTableHeader().setReorderingAllowed(false);

                ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

               blnRes=true;
             }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(Frame,e);  }
            return blnRes;
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
        blnAcepta = true; 
        //dispose();
        this.setVisible(false);     
}//GEN-LAST:event_butAceActionPerformed
 
    
    
    private String strSql;
    
    
    
 
       
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

    public boolean isBlnCliRet() {
        return blnCliRet;
    }

    public void setBlnCliRet(boolean blnCliRet) {
        this.blnCliRet = blnCliRet;
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
            case INT_TBL_COD_EMP:
                strMsg="";
            break;
            case INT_TBL_COD_REG:
                strMsg="Código de Registro";
            break;
            case INT_TBL_COD_ITM:
                strMsg="Código del Item";
            break;
            case INT_TBL_COD_ALT:
                strMsg="Código Alterno del Item";
            break;
            case INT_TBL_CAN:
                strMsg="Cantidad en la Cotizacion";
            break;
            case INT_TBL_CAN_AUT:
                strMsg="Cantidad Autorizada";
            break;
            case INT_TBL_CAN_PEN_FAC:
                strMsg="Cantidad Pendiente de Facturar";
            break;
            case INT_TBL_CAN_CAN:
                strMsg="Cantidad a Cancelar";
            break;
            case INT_TBL_CHK_CAN:
                strMsg="Item a Cancelar";
            break;
            default:
                strMsg=" ";
            break;
            
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }    
 

     
    
}

