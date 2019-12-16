/*
 * Created on 16 de marzo de 2006, 12:07    jSplitPane1   
 */        
             
package Compras.ZafCom09;  

import Librerias.ZafVenCon.ZafVenCon;  //**************************
import java.util.ArrayList;  //***********************  
 
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;

import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;

import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author  Javier Ayapata         
 * 
 */
public class ZafCom09 extends javax.swing.JInternalFrame {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_BUT_KAR=1;    // BOTON PARA EL KARDES..
    final int INT_TBL_BUT_STK=2;    // BOTON PARA EL STOCK..
    final int INT_TBL_BUT_INV=3;    // BOTON PARA ACTUALIZAR  INV..
    final int INT_TBL_COD_MAE=4;                    //Código maestro del item.
    final int INT_TBL_COD_ITM=5;                    //Código del item (Sistema).
    final int INT_TBL_COD_ALT=6;                    //Código del item (Alterno).
    final int INT_TBL_UNI_ITM=7;                    //Código del item (Alterno).
    final int INT_TBL_NOM_ITM=8;                    //Nombre del item.
    final int INT_TBL_COD_ITM2=9;                    //Código del item (Sistema).
    final int INT_TBL_COD_ALT2=10;                    //Código del item (Alterno).
   
    final int INT_TBL_LIN1=0;                        //Línea
    final int INT_TBL_BUT_KAR1=1;    // BOTON PARA EL KARDES..
    final int INT_TBL_BUT_STK1=2;    // BOTON PARA EL STOCK..
    final int INT_TBL_BUT_INV1=3;    // BOTON PARA ACTUALIZAR  INV..
    final int INT_TBL_COD_MAE1=4;                    //Código maestro del item.
    final int INT_TBL_COD_ITM1=5;                    //Código del item (Sistema).
    final int INT_TBL_COD_ALT1=6;                    //Código del item (Alterno).
    final int INT_TBL_UNI_ITM1=7;                    //Código del item (Alterno).
    final int INT_TBL_NOM_ITM1=8;                    //Nombre del item.
    final int INT_TBL_COD_ITM3=9;                    //Código del item (Sistema).
    final int INT_TBL_COD_ALT3=10;                    //Código del item (Alterno).
      
    int intVerifi = 0; 
     
     private ZafTblBus objTblBus1;
     private ZafTblBus objTblBus2;
     
     ZafVenCon objVenCon2; //*****************
    //Variables

    ArrayList arlCam=new ArrayList();
    private java.util.Date datFecAux;
     
    private ZafThreadGUI3 objThrGUI3;
    private ZafThreadGUI4 objThrGUI4;
      
    private ZafTblOrd objTblOrd1;                        //JTable de ordenamiento tabla1.
    private ZafTblOrd objTblOrd2;                        //JTable de ordenamiento tabla2.
     
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod, objTblMod2;
    private ZafThreadGUI objThrGUI;
    private ZafThreadGUI objThrGUI2;
  
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    private ZafTblCelEdiBut objTblCelEdiBut;            //Editor: JButton en celda.
    private ZafTblCelEdiTxtCon objTblCelEdiTxtCon;      //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader de tabla 1.
    private ZafMouMotAda2 objMouMotAda2;                  //ToolTipText en TableHeader de tabla 2.
   
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con,con2,con3, CONN_LOCAL, CONN_TUVAL, CONN_CASTEK;
    private Statement stm,stm2,stmemp,stmemp2;
    private ResultSet rst,rst2,rstemp,rstemp2,rstinvbod;
    private String strSQL, strAux,strAux2;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDat2, vecCab2, vecReg2;
    private Vector vecAux;
    private Vector vecAux2;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
   
    String strdes1="",strdes2="",strhas1="",strhas2="";
    String strcod1="",strcod2=""; 
    
    int intControlTuval=0;
    int intControlCastek=0;
    
    String Version = "1.3";    
    
    Compras.ZafCom09.ZafCom09Msg obj;
    
     String str_conloc="";
     String str_user="";
     String str_clacon="";
      
     javax.swing.JTextArea txtsql = new javax.swing.JTextArea();
      //Variable  
     
    private ArrayList arlDat, arlReg;
    private int INT_CO_REG=0;
    private int INT_CO_REGORG=1;
    private int INT_CO_REGDES=2;
    private int INT_TX_FREAC=3;
    private int INT_ST_REG=4;
  
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
           
    private Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu ZafTblPopMn1;
    private Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu ZafTblPopMn2;
    
    private String strCorEle="sistemas6@tuvalsa.com", strItems; // 
       
    /** Creates new form prueba */
    public ZafCom09(ZafParSis obj) {
       objParSis=obj;

         initComponents();
         bgrFil = new javax.swing.ButtonGroup();
         bgrFil.add(optTod);
         bgrFil.add(optFil);
         str_conloc = objParSis.getStringConexionCentral();
         str_user = objParSis.getUsuarioConexionCentral();
         str_clacon = objParSis.getClaveConexionCentral();
         arlDat=new ArrayList();
         
    }
    
    
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
       
      
    private void mostrarMsg(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
    }
    
    
    
    
       
    private boolean configurarVenConProducto()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.co_itm");
            arlCam.add("a7.tx_nomItm");
            arlCam.add("a7.nd_stkAct");
            arlCam.add("a7.nd_preVta1");
            arlCam.add("a7.st_ivaVen");
            arlCam.add("a7.tx_descor");
            arlCam.add("a7.tx_codAlt2");
            arlCam.add("a7.st_ser");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cód.Sis.");
            arlAli.add("Nombre");
            arlAli.add("Stock");
            arlAli.add("Precio");
            arlAli.add("Iva.");
            arlAli.add("Uni.");
            arlAli.add("Código2");
            arlAli.add("Itm.Ser.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("210");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("35");
            arlAncCol.add("40");
            arlAncCol.add("64");
            arlAncCol.add("20");
            
                 //Armar la sentencia SQL.   a7.nd_stkTot,
             String Str_Sql="";
            Str_Sql="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct,  a7.nd_preVta1, a7.st_ivaVen,a7.tx_descor,a7.tx_codAlt2,a7.st_ser";
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
              Str_Sql+=" ) AS a1, (";
              Str_Sql+=" SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen,var.tx_descor,c1.tx_codAlt2,c1.st_ser";
              Str_Sql+=" FROM tbm_inv AS c1";
              Str_Sql+=" left outer join tbm_var as var on (c1.co_uni=var.co_reg and var.co_grp=5 )  ";
              Str_Sql+=" INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
              Str_Sql+=" WHERE c2.co_emp=" + objParSis.getCodigoEmpresa();
              Str_Sql+=" ) AS a2";
              Str_Sql+=" WHERE a1.co_itmMae=a2.co_itmMae";
              Str_Sql+=" ) AS a7 order by a7.tx_codalt";
              System.out.println(">> "+Str_Sql);
                 
             //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=7;
            intColOcu[1]=9;
               
            objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenCon2.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            objVenCon2.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, objVenCon2.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, objVenCon2.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
         
         
         
    private boolean configurarFrm()
    {  
        boolean blnRes=true;
        try
        {
           //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
             this.setTitle(strAux +" "+ Version );
             lblTit.setText(strAux);
              
          
             ///********************** TABLA  1 **//////////
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(9);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_BUT_KAR," ");
            vecCab.add(INT_TBL_BUT_STK," ");
            vecCab.add(INT_TBL_BUT_INV," ");
            vecCab.add(INT_TBL_COD_MAE,"Cód.Mas.");
            vecCab.add(INT_TBL_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_UNI_ITM,"Unidad.");
            vecCab.add(INT_TBL_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_COD_ITM2,"Cód.Itm2.");
            vecCab.add(INT_TBL_COD_ALT2,"Cód.Alt2.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
             tblDat.setRowSelectionAllowed(true);
             tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);

              tcmAux.getColumn(INT_TBL_UNI_ITM).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_BUT_KAR).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUT_STK).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUT_INV).setPreferredWidth(10);
            
           
            tblDat.getTableHeader().setReorderingAllowed(false);
            
             tcmAux.getColumn(INT_TBL_COD_MAE).setWidth(0);
             tcmAux.getColumn(INT_TBL_COD_MAE).setMaxWidth(0);
             tcmAux.getColumn(INT_TBL_COD_MAE).setMinWidth(0);
             tcmAux.getColumn(INT_TBL_COD_MAE).setPreferredWidth(0);
             tcmAux.getColumn(INT_TBL_COD_MAE).setResizable(false);
             
          
             

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            
            //Configurar JTable: Establecer el ordenamiento en la tabla.
            objTblOrd1=new ZafTblOrd(tblDat);
            objTblBus1=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUT_KAR);
            vecAux.add("" + INT_TBL_BUT_STK);
            vecAux.add("" + INT_TBL_BUT_INV);
              
                
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
               
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT_KAR).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUT_STK).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUT_INV).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut=null;
            
            
            //Configurar JTable: Editor de la tabla.
             objTblEdi=new ZafTblEdi(tblDat);
             objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            
            
            ButFndPrv ObjFndPrv = new ButFndPrv(tblDat, INT_TBL_BUT_KAR);   //*****
            ButFndS ObjFndS = new ButFndS(tblDat, INT_TBL_BUT_STK);
            ButFndI ObjFndI = new ButFndI(tblDat,INT_TBL_BUT_INV); 
              
              
        ///********************** TABLA  2 ********************//////////
            
            vecDat2=new Vector();    //Almacena los datos
            vecCab2=new Vector(9);   //Almacena las cabeceras
            vecCab2.clear();
            vecCab2.add(INT_TBL_LIN1,"");
            vecCab2.add(INT_TBL_BUT_KAR1," ");
            vecCab2.add(INT_TBL_BUT_STK1," ");
            vecCab2.add(INT_TBL_BUT_INV1," ");
            vecCab2.add(INT_TBL_COD_MAE1,"Cód.Mas.");
            vecCab2.add(INT_TBL_COD_ITM1,"Cód.Itm.");
            vecCab2.add(INT_TBL_COD_ALT1,"Cód.Alt.");
            vecCab2.add(INT_TBL_UNI_ITM1,"Unidad."); 
            vecCab2.add(INT_TBL_NOM_ITM1,"Nombre");
           
            
            objTblMod2=new ZafTblMod();
            objTblMod2.setHeader(vecCab2);
            tblDat2.setModel(objTblMod2);
            //Configurar JTable: Establecer tipo de selección.
             tblDat2.setRowSelectionAllowed(true);
             tblDat2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat2,INT_TBL_LIN1);
           //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux2 = tblDat2.getColumnModel();
            tcmAux2.getColumn(INT_TBL_LIN1).setPreferredWidth(25);
            
           tcmAux2.getColumn(INT_TBL_BUT_KAR1).setPreferredWidth(10);
           tcmAux2.getColumn(INT_TBL_BUT_STK1).setPreferredWidth(10);
           tcmAux2.getColumn(INT_TBL_BUT_INV1).setPreferredWidth(10);
            
            
           
            tcmAux2.getColumn(INT_TBL_COD_ITM1).setPreferredWidth(40);
            tcmAux2.getColumn(INT_TBL_COD_ALT1).setPreferredWidth(75);
            
            tcmAux2.getColumn(INT_TBL_UNI_ITM1).setPreferredWidth(60);
            
            tcmAux2.getColumn(INT_TBL_NOM_ITM1).setPreferredWidth(150);
            tblDat2.getTableHeader().setReorderingAllowed(false);
            
             tcmAux2.getColumn(INT_TBL_COD_MAE1).setWidth(0);
             tcmAux2.getColumn(INT_TBL_COD_MAE1).setMaxWidth(0);
             tcmAux2.getColumn(INT_TBL_COD_MAE1).setMinWidth(0);
             tcmAux2.getColumn(INT_TBL_COD_MAE1).setPreferredWidth(0);
             tcmAux2.getColumn(INT_TBL_COD_MAE1).setResizable(false);
    
             
             
   
            objMouMotAda2=new ZafMouMotAda2();
            tblDat2.getTableHeader().addMouseMotionListener(objMouMotAda2);
           
            //Configurar JTable: Establecer el ordenamiento en la tabla.
            objTblOrd2=new ZafTblOrd(tblDat2);
            objTblBus2=new ZafTblBus(tblDat2);
            
            
            //Configurar JTable: Establecer columnas editables.
            vecAux2=new Vector();
            vecAux2.add("" + INT_TBL_BUT_KAR1);
            vecAux2.add("" + INT_TBL_BUT_STK1);
            vecAux2.add("" + INT_TBL_BUT_INV1);
            objTblMod2.setColumnasEditables(vecAux2);
            vecAux2=null;
            
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux2.getColumn(INT_TBL_BUT_KAR1).setCellRenderer(objTblCelRenBut);
            tcmAux2.getColumn(INT_TBL_BUT_STK1).setCellRenderer(objTblCelRenBut);
            tcmAux2.getColumn(INT_TBL_BUT_INV1).setCellRenderer(objTblCelRenBut);
            
            
            objTblCelRenBut=null;
            
            
            //Configurar JTable: Editor de la tabla.
             objTblEdi=new ZafTblEdi(tblDat2);
             objTblMod2.setModoOperacion(objTblMod2.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux2=null;
  
            ButFnd2 ObjFnd2 = new ButFnd2(tblDat2, INT_TBL_BUT_KAR1);   //*****
            ButFndS2 ObjFndS2 = new ButFndS2(tblDat2, INT_TBL_BUT_STK1);
            ButFndI2 ObjFndI2 = new ButFndI2(tblDat2,INT_TBL_BUT_INV1); 
            
            
         
            ZafTblPopMn1 = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            ZafTblPopMn1.setEliminarFilaVisible(false);
            ZafTblPopMn1.setInsertarFilasVisible(false);
            ZafTblPopMn1.setInsertarFilaVisible(false);
            
            
            ZafTblPopMn2 = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat2);
            ZafTblPopMn2.setEliminarFilaVisible(false);
            ZafTblPopMn2.setInsertarFilasVisible(false);
            ZafTblPopMn2.setInsertarFilaVisible(false);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
          
          
    
    
    
    
    
      
    private class ButFndPrv extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButFndPrv(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Kardes.");
           
        }
        public void butCLick() {
           Kardes();
        }
    }
    
    
     private class ButFndS extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButFndS(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Stock Consolidado.");
        }
        public void butCLick() {
           StockGrp();
        }
    }
         

      private class ButFndS2 extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButFndS2(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Stock Consolidado.");
        }
        public void butCLick() {
           StockGrp2();
        }
     }
     
     
      private class ButFndI extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButFndI(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Actualizar Item.");
        }
        public void butCLick() {
           ActInv();
        }
    }
    
    
        private class ButFndI2 extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButFndI2(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Actualizar Item.");
        }
        public void butCLick() {
           ActInv2();
        }
    }
      
    
    private void Kardes(){
            
            String strItem1 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_ITM).toString();
            String strItem2 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_ALT).toString();
            String strItem3 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NOM_ITM).toString();
           
            
            System.out.println("  >>>> "+ strItem1 );   
            
            Compras.ZafCom05.ZafCom05 obj = new  Compras.ZafCom05.ZafCom05(objParSis, strItem1, strItem2, strItem3 );
            this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
        
    }
    
    
      private void StockGrp(){
            
            String strItem1 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_ITM).toString();
            String strItem2 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_ALT).toString();
            String strItem3 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NOM_ITM).toString();
           
            
            System.out.println("  >>>> "+ strItem1 );   
               
            Compras.ZafCom11.ZafCom11 obj = new  Compras.ZafCom11.ZafCom11(objParSis, strItem1, strItem2, strItem3 );
            this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
        
    }
    
      
      
        private void StockGrp2(){
            
            String strItem1 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_COD_ITM1).toString();
            String strItem2 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_COD_ALT1).toString();
            String strItem3 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_NOM_ITM1).toString();
           
            
            System.out.println("  >>>> "+ strItem1 );   
               
            Compras.ZafCom11.ZafCom11 obj = new  Compras.ZafCom11.ZafCom11(objParSis, strItem1, strItem2, strItem3 );
            this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
        
    }
    
     
      
      
        private void ActInv(){
            
            String strItem1 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_ITM).toString();
            String strItem2 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_COD_ALT).toString();
            String strItem3 = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NOM_ITM).toString();
             
             
            System.out.println("  >>>> "+ strItem1 );   
            
            int intCodMM = objParSis.getCodigoMenu();
            objParSis.setCodigoMenu(106);
            
            Maestros.ZafMae06.ZafMae06 obj = new  Maestros.ZafMae06.ZafMae06(objParSis, strItem1); // , strItem2, strItem3 );
            this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            objParSis.setCodigoMenu(intCodMM);
        
      }      
      
           
        
        private void ActInv2(){  
            
             String strItem1 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_COD_ITM1).toString();
             String strItem2 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_COD_ALT1).toString();
             String strItem3 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_NOM_ITM1).toString();
           
            
            System.out.println("  >>>> "+ strItem1 );   
            
            int intCodMM = objParSis.getCodigoMenu();
            objParSis.setCodigoMenu(106);
            Maestros.ZafMae06.ZafMae06 obj = new  Maestros.ZafMae06.ZafMae06(objParSis, strItem1); // , strItem2, strItem3 );
            this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            objParSis.setCodigoMenu(intCodMM);
    }   
      
    
          
        
        
      private class ButFnd2 extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButFnd2(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx , "Kardes.");
        }
        public void butCLick() {
           Kardes2();
        }
    }
    
    
    
    private void Kardes2(){
            
            String strItem1 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_COD_ITM1).toString();
            String strItem2 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_COD_ALT1).toString();
            String strItem3 = tblDat2.getValueAt(tblDat2.getSelectedRow(), INT_TBL_NOM_ITM1).toString();
         
            Compras.ZafCom05.ZafCom05 obj = new  Compras.ZafCom05.ZafCom05(objParSis, strItem1, strItem2, strItem3 );
            this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
        
    }
    
         
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panNor = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        chkSer = new javax.swing.JCheckBox();
        panCen = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        spnDat1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTblBut1 = new javax.swing.JPanel();
        panConBut1 = new javax.swing.JPanel();
        butCon1 = new javax.swing.JButton();
        panButCen = new javax.swing.JPanel();
        butEnvDat = new javax.swing.JButton();
        butEnvDat1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        spnDat2 = new javax.swing.JScrollPane();
        tblDat2 = new javax.swing.JTable();
        panBut2 = new javax.swing.JPanel();
        panButCon2 = new javax.swing.JPanel();
        butCon2 = new javax.swing.JButton();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panSur = new javax.swing.JPanel();
        panButPro = new javax.swing.JPanel();
        butProce = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        butCerrar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                CerrarForm(evt);
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

        panGen.setLayout(new java.awt.BorderLayout());

        panNor.setLayout(new java.awt.BorderLayout());

        panNor.setPreferredSize(new java.awt.Dimension(800, 120));
        panNor.setRequestFocusEnabled(false);
        panCab.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });

        panCab.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        optFil.setText("S\u00f3lo los items que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });

        panCab.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panCab.add(lblItm);
        lblItm.setBounds(24, 44, 120, 20);

        panCab.add(txtCodItm);
        txtCodItm.setBounds(88, 44, 56, 20);

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });

        panCab.add(txtCodAlt);
        txtCodAlt.setBounds(144, 44, 92, 20);

        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });

        panCab.add(txtNomItm);
        txtNomItm.setBounds(236, 44, 424, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });

        panCab.add(butItm);
        butItm.setBounds(660, 44, 20, 20);

        panNomCli.setLayout(null);

        panNomCli.setBorder(new javax.swing.border.TitledBorder("C\u00f3digo alterno del item"));
        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 44, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });

        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(56, 20, 268, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(336, 20, 44, 20);

        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(380, 20, 268, 20);

        panCab.add(panNomCli);
        panNomCli.setBounds(24, 64, 660, 52);

        chkSer.setText("Item de Servicio.");
        panCab.add(chkSer);
        chkSer.setBounds(524, 8, 152, 23);

        panNor.add(panCab, java.awt.BorderLayout.CENTER);

        panGen.add(panNor, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panCen.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        jSplitPane1.setResizeWeight(0.6);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(471, 200));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
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
        spnDat1.setViewportView(tblDat);

        jPanel1.add(spnDat1, java.awt.BorderLayout.CENTER);

        panTblBut1.setLayout(new java.awt.BorderLayout());

        panTblBut1.setPreferredSize(new java.awt.Dimension(91, 35));
        butCon1.setText("Consultar");
        butCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCon1ActionPerformed(evt);
            }
        });

        panConBut1.add(butCon1);

        panTblBut1.add(panConBut1, java.awt.BorderLayout.EAST);

        jPanel1.add(panTblBut1, java.awt.BorderLayout.SOUTH);

        panButCen.setLayout(null);

        panButCen.setPreferredSize(new java.awt.Dimension(40, 0));
        butEnvDat.setText("<<");
        butEnvDat.setBorder(new javax.swing.border.EtchedBorder());
        butEnvDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEnvDatActionPerformed(evt);
            }
        });

        panButCen.add(butEnvDat);
        butEnvDat.setBounds(4, 4, 32, 20);

        butEnvDat1.setText(">>");
        butEnvDat1.setBorder(new javax.swing.border.EtchedBorder());
        butEnvDat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEnvDat1ActionPerformed(evt);
            }
        });

        panButCen.add(butEnvDat1);
        butEnvDat1.setBounds(4, 50, 32, 20);

        jPanel1.add(panButCen, java.awt.BorderLayout.EAST);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(454, 200));
        tblDat2.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat2.setViewportView(tblDat2);

        jPanel2.add(spnDat2, java.awt.BorderLayout.CENTER);

        panBut2.setLayout(new java.awt.BorderLayout());

        butCon2.setText("Consultar");
        butCon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCon2ActionPerformed(evt);
            }
        });

        panButCon2.add(butCon2);

        panBut2.add(panButCon2, java.awt.BorderLayout.EAST);

        jPanel2.add(panBut2, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel2);

        panCen.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        panGen.add(panCen, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        lblTit.setText("unificacion");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panSur.setLayout(new java.awt.BorderLayout());

        butProce.setText("Procesar");
        butProce.setToolTipText("Procesar la Unificaci\u00f3n de Item");
        butProce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProceActionPerformed(evt);
            }
        });

        panButPro.add(butProce);

        jButton1.setText("Inventario");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        panButPro.add(jButton1);

        butCerrar.setText("Cerrar");
        butCerrar.setToolTipText("Cerrar ventana");
        butCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrarActionPerformed(evt);
            }
        });

        panButPro.add(butCerrar);

        panSur.add(panButPro, java.awt.BorderLayout.EAST);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
          
            Maestros.ZafMae06.ZafMae06 obj1 = new  Maestros.ZafMae06.ZafMae06(objParSis);
            this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();

        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        // TODO add your handling code here:
         BuscarItem("a.tx_nomitm",txtNomItm.getText(),2);
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        // TODO add your handling code here:
         BuscarItem("a.tx_codalt",txtCodAlt.getText(),0);
    }//GEN-LAST:event_txtCodAltActionPerformed
      
      
      
      public void BuscarItem(String campo,String strBusqueda,int tipo){
  
            objVenCon2.setTitle("Listado de Productos");         
            if (objVenCon2.buscar(campo, strBusqueda ))
            {
                   txtCodItm.setText(objVenCon2.getValueAt(2));
                   txtCodAlt.setText(objVenCon2.getValueAt(1));
                   txtNomItm.setText(objVenCon2.getValueAt(3));
                   optFil.setSelected(true);  
             }  
             else   
               {     
                   objVenCon2.setCampoBusqueda(tipo);
                   objVenCon2.cargarDatos();
                   objVenCon2.show();
                  if (objVenCon2.getSelectedButton()==objVenCon2.INT_BUT_ACE)
                  {
                     txtCodItm.setText(objVenCon2.getValueAt(2));
                     txtCodAlt.setText(objVenCon2.getValueAt(1));
                     txtNomItm.setText(objVenCon2.getValueAt(3));
                     optFil.setSelected(true);
                   }
              }
      }
  
       
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        
        configurarVenConProducto();
        
         configurarFrm();
         
    }//GEN-LAST:event_formInternalFrameOpened

    private void butEnvDat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEnvDat1ActionPerformed
        // TODO add your handling code here:
        
          int a=-1;
        
            a = tblDat.getSelectedRow();
       
        
        if(a != -1 )  {      
          tblDat.setValueAt("",a,INT_TBL_COD_ITM2);
          tblDat.setValueAt("",a,INT_TBL_COD_ALT2);
        }
       
        
        
    }//GEN-LAST:event_butEnvDat1ActionPerformed

    private void CerrarForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarForm
        // TODO add your handling code here:
        CerrarVentana();
    }//GEN-LAST:event_CerrarForm

    private void butCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrarActionPerformed
        // TODO add your handling code here:
        CerrarVentana(); 
    }//GEN-LAST:event_butCerrarActionPerformed

    private void butProceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProceActionPerformed
        // TODO add your handling code here:
                
     try
        {
          
      if(mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
        {

            
            if (objThrGUI3==null){
                  obj = new Compras.ZafCom09.ZafCom09Msg(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                  objThrGUI3=new ZafThreadGUI3();
                  objThrGUI3.start();                                
             }    
            
              if (objThrGUI4==null){
                  objThrGUI4=new ZafThreadGUI4();
                  objThrGUI4.start();                                
              }    
        }
      
      }
      catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
      
    }//GEN-LAST:event_butProceActionPerformed

    
    
     public void Abrir_Conexion1(String str_strcon, String str_usrcon, String str_cla){
        try{
            //System.out.println("ABRIR CONEXION...."); 
            CONN_LOCAL=DriverManager.getConnection( str_strcon, str_usrcon, str_cla );          
            CONN_LOCAL.setAutoCommit(false);
           }
           catch(SQLException  Evt){   System.out.println("Error "+Evt.toString() ); }
    }
    
    
     public void Cerrar_Conexion1(){
        try{
           ///System.out.println("CERRANDO CONEXION....");
            CONN_LOCAL.close();
            CONN_LOCAL=null; 
        }
           catch(SQLException  Evt){   System.out.println("Error "+Evt.toString() ); }
    }
    
    
    
    public boolean ProcesoUnificacion(java.sql.Connection con){
      boolean blnres=true;
        try{
            if (con!=null)
            {
               intVerifi = 0; 
               
               for(int x=0; x < tblDat.getRowCount(); x++ )
               {
                  if( tblDat.getValueAt(x,INT_TBL_COD_ITM2).toString().equals("") ) {}
                  else { 
                       if(procesar(con,x)){
                           intVerifi=3;
                       }else{
                              intVerifi=2;
                              break;
               }}}
                     
            if(intVerifi==3) {
                blnres=true;
            }
            if(intVerifi==0){
                blnres=true; 
            }
               
            if(intVerifi==2) {
                // mostrarMsg("Hubo Problemas al realizar el Proceso de Unificación ..");    
                 con.rollback();
                 blnres=false;
            }  
           }}
          catch (java.sql.SQLException e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
          catch (Exception e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnres;
    }
    
    
    
    
    private void butEnvDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEnvDatActionPerformed
        // TODO add your handling code here:
        int b=-1;
        
        int a = tblDat2.getSelectedRow();
            b = tblDat.getSelectedRow();
        
        if(b != -1 )  {      
          tblDat.setValueAt(tblDat2.getValueAt(a,INT_TBL_COD_ITM1).toString(),b,INT_TBL_COD_ITM2);
          tblDat.setValueAt(tblDat2.getValueAt(a,INT_TBL_COD_ALT1).toString(),b,INT_TBL_COD_ALT2);
        }
       
        
     
        
        
    }//GEN-LAST:event_butEnvDatActionPerformed

    private void butCon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCon2ActionPerformed
        // TODO add your handling code here:
        
        
                 strdes2 = txtCodAltDes.getText().replaceAll("'", "''").toLowerCase();
                 strhas2 = txtCodAltHas.getText().replaceAll("'", "''").toLowerCase();
                 strcod2=txtCodItm.getText();
            
        
         cargarDetReg2();
    
        
    }//GEN-LAST:event_butCon2ActionPerformed

    private void butCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCon1ActionPerformed
        // TODO add your handling code here:
        
       
                 strdes1=txtCodAltDes.getText().replaceAll("'", "''").toLowerCase();
                 strhas1 = txtCodAltHas.getText().replaceAll("'", "''").toLowerCase();
                 strcod1=txtCodItm.getText();
               
                 
        
       actbut1();
        
        
    }//GEN-LAST:event_butCon1ActionPerformed

    private void actbut1(){
          
          if (butCon1.getText().equals("Consultar"))
        {
            blnCon = true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
        
    }
    
    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        // TODO add your handling code here:
          if (txtCodAltDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        // TODO add your handling code here:
          txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        // TODO add your handling code here:
         if (optTod.isSelected()) {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
          
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
              
        
              objVenCon2.setTitle("Listado de Productos");         
              objVenCon2.setCampoBusqueda(1);
              objVenCon2.show();
             if (objVenCon2.getSelectedButton()==objVenCon2.INT_BUT_ACE)
             {
                   txtCodItm.setText(objVenCon2.getValueAt(2));
                   txtCodAlt.setText(objVenCon2.getValueAt(1));
                   txtNomItm.setText(objVenCon2.getValueAt(3));
                   optFil.setSelected(true);
             } 
              
      
        
              
    }//GEN-LAST:event_butItmActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilActionPerformed
    
    
         
        
    
      public boolean ProcesaDatos(java.sql.Connection con,  int CodItm1, int CodItm2){
          boolean blnRes = false;
                try{ 
                     String SQL_MAE="SELECT co_itmMae, co_emp , co_itm" +
                                    " FROM tbm_equInv " +
                                    " WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" " +
                                    " AND co_itm="+CodItm2+")";
                     stmemp=con.createStatement();
                     rstemp = stmemp.executeQuery(SQL_MAE);
                     while(rstemp.next()){
                    
                             java.sql.PreparedStatement stmPro; 
                                                                                         
                          //****************REALIZA UNIFICACION DE INVENTARIO******************************////
                                
                            String SQL_MAE2="SELECT co_emp , co_itm" +
                                    " FROM tbm_equInv " +
                                    " WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" " +
                                    " AND co_itm="+CodItm1+") AND co_emp="+rstemp.getString(2); 
                                stmemp2=con.createStatement();
                               rstemp2 = stmemp2.executeQuery(SQL_MAE2);
                               if(rstemp2.next()){
                                //**********************PASO I TRANSFIERE LOS MOVIMIENTOS AL NUEVO ITEM ********************************///
                                   stm=con.createStatement();
                                   String SQL1 = "update tbm_detmovinv set co_itm="+ rstemp2.getString(2) +" where co_itm= "+ rstemp.getString(3)+" AND co_emp="+rstemp.getString(2); 
                                   //txtsql.append( SQL1 +"; \n ");
                                  
                                   String SQL2 = "update tbm_detcotven set co_itm="+ rstemp2.getString(2) +" where  co_itm= "+ rstemp.getString(3) +" AND co_emp="+rstemp.getString(2); 
                                   //txtsql.append( SQL2 +"; \n ");
                                  
                                   String SQL3 = "update tbm_detcotcom set co_itm="+ rstemp2.getString(2) +" where  co_itm= "+ rstemp.getString(3) +" AND co_emp="+rstemp.getString(2); 
                                   //txtsql.append( SQL3 +"; \n ");
                                   
                                   if(rstemp.getInt(2)==0){  
                                     SQL3 = "INSERT INTO tbt_unificacion_empresa(co_emp, co_itmmae, co_itmuni, co_itmpre, st_reg, fe_uni, co_usruni) " +
                                     " VALUES("+rstemp.getString(2)+", "+rstemp.getString(1)+","+ rstemp2.getString(2)+","+rstemp.getString(3)+",'A' , CURRENT_TIMESTAMP,"+objParSis.getCodigoUsuario()+")"; 
                                        System.out.println("==> " + SQL3);
                                        strItems+=SQL3 + "\n  ====== \n";
                                        stmPro = con.prepareStatement(SQL3); 
                                      stmPro.executeUpdate();
                                   }
                                      
                                   
                                  if(!Uni_Itm_Tbh_Inv(con, rstemp.getInt(2), rstemp2.getInt(2), rstemp.getInt(3) )) { 
                                      con.rollback();
                                      con.close();
                                      return false; 
                                  }
                                //****************PASO II TABLA INVBOD******************************////
                                 String SQL4=" update tbm_invbod set nd_stkact=nd_stkact+b.sact from(" +
                                " Select nd_stkact as sact ,co_emp as coemp, co_bod as cobod" +
                                " from  tbm_invbod where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2)+" order by co_bod ) as b" +
                                " where co_emp=b.coemp and co_bod=b.cobod and co_itm="+rstemp2.getString(2);
                                // txtsql.append( SQL4 +"; \n ");
                                                   
                                 SQL4="delete from  tbm_invbod where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2);
                                 //txtsql.append( SQL4 +"; \n ");
                                
                                 //****************PASO III TABLA INV******************************////  
                                   String SQL5 ="update tbm_inv set  fe_ultMod=CURRENT_TIMESTAMP,co_usrMod="+objParSis.getCodigoUsuario()+""+
                                   ",nd_stkact=nd_stkact+b.sact from(" +
                                   " select nd_stkact as sact ,co_emp as coemp from  tbm_inv where co_itm="+rstemp.getString(3)+" " +
                                   " and co_Emp="+rstemp.getString(2)+" ) as b where co_emp=b.coemp and co_itm="+rstemp2.getString(2);
                                   //txtsql.append( SQL5 +"; \n ");
                                          
                                   SQL5 = "delete from tbm_inv where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2);
                                   //txtsql.append( SQL5 +"; \n ");
                               
                               }
                       
                       //*******************************************************************************//  
                
                            String SQL7 = "delete from tbm_equinv where co_itmmae="+rstemp.getString(1)+"" +
                                        " and co_Emp="+rstemp.getString(2)+" and co_itm="+rstemp.getString(3);
                            //txtsql.append( SQL7 +"; \n ");
                         
                           String SQL8 = "delete from tbm_invmae where co_itmmae="+rstemp.getString(1); 
                           //txtsql.append( SQL8 +"; \n ");
                       }
                        
                   rstemp.close();
                   stmemp.close();
                   rstemp2.close();
                   stmemp2.close();
                   blnRes=true;
                }
                catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
                return blnRes;      
        }
    
           
      
//     public boolean ProcesaDatosPorEmpresa(java.sql.Connection con,  int CodItm1, int CodItm2, int intCodEmp){
//          boolean blnRes = false;
//                try{ 
//                    
//                     String SQL_MAE="SELECT co_itmMae, co_emp , co_itm" +
//                                    " FROM tbm_equInv " +
//                                    " WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+intCodEmp+" " +
//                                    " AND co_itm="+CodItm2+")";
//                     System.out.println("1.. >>>> "+SQL_MAE);
//                     stmemp=con.createStatement();
//                     rstemp = stmemp.executeQuery(SQL_MAE);
//                     while(rstemp.next()){
//                    
//                             java.sql.PreparedStatement stmPro; 
//                                                                                         
//                          //****************REALIZA UNIFICACION DE INVENTARIO******************************////
//                          
//                            String SQL_MAE2="SELECT co_emp , co_itm" +
//                                    " FROM tbm_equInv " +
//                                    " WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+intCodEmp+" " +
//                                    " AND co_itm="+CodItm1+") AND co_emp="+rstemp.getString(2); 
//                               System.out.println("2...>>>> "+SQL_MAE2);
//                               stmemp2=con.createStatement();
//                               rstemp2 = stmemp2.executeQuery(SQL_MAE2);
//                               if(rstemp2.next()){
//                           
//                                //**********************PASO I TRANSFIERE LOS MOVIMIENTOS AL NUEVO ITEM ********************************///
//                                   stm=con.createStatement();
//                                   String SQL1 = "update tbm_detmovinv set co_itm="+ rstemp2.getString(2) +" where co_itm= "+ rstemp.getString(3)+" AND co_emp="+rstemp.getString(2); 
//                                   //System.out.println("SQL DETMOV "+ SQL1);
//                                   stmPro = con.prepareStatement(SQL1); 
//                                   stmPro.executeUpdate();
//                
//                                   String SQL2 = "update tbm_detcotven set co_itm="+ rstemp2.getString(2) +" where  co_itm= "+ rstemp.getString(3) +" AND co_emp="+rstemp.getString(2); 
//                                  // System.out.println("SQL DETCOT "+ SQL2);
//                                   stmPro = con.prepareStatement(SQL2); 
//                                   stmPro.executeUpdate();
//                      
//                                   String SQL3 = "update tbm_detcotcom set co_itm="+ rstemp2.getString(2) +" where  co_itm= "+ rstemp.getString(3) +" AND co_emp="+rstemp.getString(2); 
//                                  // System.out.println("SQL DETCOTCOM "+ SQL3);
//                                   stmPro = con.prepareStatement(SQL3); 
//                                   stmPro.executeUpdate();
//                                   
//                                   
//                                     
//                                  if(!Uni_Itm_Tbh_Inv(con, rstemp.getInt(2), rstemp2.getInt(2), rstemp.getInt(3) )) { 
//                                      con.rollback();
//                                      con.close();
//                                      mostrarMsg("Error al momento de almacenar en tabla historica ..");   
//                                      return false; 
//                                  }
//                                //****************PASO II TABLA INVBOD******************************////
//                                 String SQL4=" update tbm_invbod set nd_stkact=nd_stkact+b.sact from(" +
//                                " Select nd_stkact as sact ,co_emp as coemp, co_bod as cobod" +
//                                " from  tbm_invbod where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2)+" order by co_bod ) as b" +
//                                " where co_emp=b.coemp and co_bod=b.cobod and co_itm="+rstemp2.getString(2);
//                                stmPro = con.prepareStatement(SQL4); 
//                                stmPro.executeUpdate();
//                                                   
//                                SQL4="delete from  tbm_invbod where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2);
//                                stmPro = con.prepareStatement(SQL4); 
//                                stmPro.executeUpdate();
//                                   //****************PASO III TABLA INV******************************////  
//                                   String SQL5 ="update tbm_inv set  fe_ultMod=CURRENT_TIMESTAMP,co_usrMod="+objParSis.getCodigoUsuario()+""+
//                                   ",nd_stkact=nd_stkact+b.sact from(" +
//                                   " select nd_stkact as sact ,co_emp as coemp from  tbm_inv where co_itm="+rstemp.getString(3)+" " +
//                                   " and co_Emp="+rstemp.getString(2)+" ) as b where co_emp=b.coemp and co_itm="+rstemp2.getString(2);
//                                   stmPro = con.prepareStatement(SQL5); 
//                                   stmPro.executeUpdate();
//                                   
//                                   SQL5 = "delete from tbm_inv where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2);
//                                   stmPro = con.prepareStatement(SQL5); 
//                                   stmPro.executeUpdate();
//                         
//                               }
//                         
//                       //*******************************************************************************//  
//                
//                          String SQL7 = "delete from tbm_equinv where co_itmmae="+rstemp.getString(1)+"" +
//                                        " and co_Emp="+rstemp.getString(2)+" and co_itm="+rstemp.getString(3);
//                           stmPro = con.prepareStatement(SQL7); 
//                           stmPro.executeUpdate();
//                    
//                           String SQL8 = "delete from tbm_invmae where co_itmmae="+rstemp.getString(1); 
//                           stmPro = con.prepareStatement(SQL8); 
//                           stmPro.executeUpdate();
//                         
//                           stmPro.close();
//                           stmPro=null;
//                     }
//                      
//                   rstemp.close();
//                   stmemp.close();
//                   rstemp2.close();
//                   stmemp2.close();
//                   blnRes=true;
//                }
//                catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//                catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
//                return blnRes;      
//        }
//   
//    
//     public boolean ProcesaDatos(java.sql.Connection con,  int CodItm1, int CodItm2){
//          boolean blnRes = false;
//                try{ 
//                    
//                     String SQL_MAE="SELECT co_itmMae, co_emp , co_itm" +
//                                    " FROM tbm_equInv " +
//                                    " WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" " +
//                                    " AND co_itm="+CodItm2+")";
//                     System.out.println("1.. >>>> "+SQL_MAE);
//                     stmemp=con.createStatement();
//                     rstemp = stmemp.executeQuery(SQL_MAE);
//                     while(rstemp.next()){
//                    
//                             java.sql.PreparedStatement stmPro; 
//                                                                                         
//                          //****************REALIZA UNIFICACION DE INVENTARIO******************************////
//                          
//                            String SQL_MAE2="SELECT co_emp , co_itm" +
//                                    " FROM tbm_equInv " +
//                                    " WHERE co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objParSis.getCodigoEmpresa()+" " +
//                                    " AND co_itm="+CodItm1+") AND co_emp="+rstemp.getString(2); 
//                               System.out.println("2...>>>> "+SQL_MAE2);
//                               stmemp2=con.createStatement();
//                               rstemp2 = stmemp2.executeQuery(SQL_MAE2);
//                               if(rstemp2.next()){
//                           
//                                //**********************PASO I TRANSFIERE LOS MOVIMIENTOS AL NUEVO ITEM ********************************///
//                                   stm=con.createStatement();
//                                   String SQL1 = "update tbm_detmovinv set co_itm="+ rstemp2.getString(2) +" where co_itm= "+ rstemp.getString(3)+" AND co_emp="+rstemp.getString(2); 
//                                   //System.out.println("SQL DETMOV "+ SQL1);
//                                   stmPro = con.prepareStatement(SQL1); 
//                                   stmPro.executeUpdate();
//                
//                                   String SQL2 = "update tbm_detcotven set co_itm="+ rstemp2.getString(2) +" where  co_itm= "+ rstemp.getString(3) +" AND co_emp="+rstemp.getString(2); 
//                                  // System.out.println("SQL DETCOT "+ SQL2);
//                                   stmPro = con.prepareStatement(SQL2); 
//                                   stmPro.executeUpdate();
//                 
//                                   String SQL3 = "update tbm_detcotcom set co_itm="+ rstemp2.getString(2) +" where  co_itm= "+ rstemp.getString(3) +" AND co_emp="+rstemp.getString(2); 
//                                  // System.out.println("SQL DETCOTCOM "+ SQL3);
//                                   stmPro = con.prepareStatement(SQL3); 
//                                   stmPro.executeUpdate();
//                                   
//                                  if(!Uni_Itm_Tbh_Inv(con, rstemp.getInt(2), rstemp2.getInt(2), rstemp.getInt(3) )) { 
//                                      con.rollback();
//                                      con.close();
//                                      return false; 
//                                  }
//                                //****************PASO II TABLA INVBOD******************************////
//                                 String SQL4=" update tbm_invbod set nd_stkact=nd_stkact+b.sact from(" +
//                                " Select nd_stkact as sact ,co_emp as coemp, co_bod as cobod" +
//                                " from  tbm_invbod where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2)+" order by co_bod ) as b" +
//                                " where co_emp=b.coemp and co_bod=b.cobod and co_itm="+rstemp2.getString(2);
//                                stmPro = con.prepareStatement(SQL4); 
//                                stmPro.executeUpdate();
//                                                   
//                                SQL4="delete from  tbm_invbod where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2);
//                                stmPro = con.prepareStatement(SQL4); 
//                                stmPro.executeUpdate();
//                                   //****************PASO III TABLA INV******************************////  
//                                   String SQL5 ="update tbm_inv set  fe_ultMod=CURRENT_TIMESTAMP,co_usrMod="+objParSis.getCodigoUsuario()+""+
//                                   ",nd_stkact=nd_stkact+b.sact from(" +
//                                   " select nd_stkact as sact ,co_emp as coemp from  tbm_inv where co_itm="+rstemp.getString(3)+" " +
//                                   " and co_Emp="+rstemp.getString(2)+" ) as b where co_emp=b.coemp and co_itm="+rstemp2.getString(2);
//                                   stmPro = con.prepareStatement(SQL5); 
//                                   stmPro.executeUpdate();
//                                   
//                                   SQL5 = "delete from tbm_inv where co_itm="+rstemp.getString(3)+" and co_Emp="+rstemp.getString(2);
//                                   stmPro = con.prepareStatement(SQL5); 
//                                   stmPro.executeUpdate();
//                         
//                               }
//                       
//                       //*******************************************************************************//  
//                
//                          String SQL7 = "delete from tbm_equinv where co_itmmae="+rstemp.getString(1)+"" +
//                                        " and co_Emp="+rstemp.getString(2)+" and co_itm="+rstemp.getString(3);
//                           stmPro = con.prepareStatement(SQL7); 
//                           stmPro.executeUpdate();
//                    
//                           String SQL8 = "delete from tbm_invmae where co_itmmae="+rstemp.getString(1); 
//                           stmPro = con.prepareStatement(SQL8); 
//                           stmPro.executeUpdate();
//                         
//                           stmPro.close();
//                           stmPro=null;
//                     }
//                      
//                   rstemp.close();
//                   stmemp.close();
//                   rstemp2.close();
//                   stmemp2.close();
//                   blnRes=true;
//                }
//                catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//                catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
//                return blnRes;      
//        }
//      
          
    //*****************************************************************************************************//
    //*****************************************************************************************************//
     
     private boolean Uni_Itm_Tbh_Inv(java.sql.Connection conn,int coemp, int coditmPre, int coditmDes){
         boolean blnres=true;
          try{ 
                int intCorRegPre=0,intCorRegDes=0;
                stm=conn.createStatement();
                java.sql.PreparedStatement stmPro; 
                java.sql.ResultSet rst1,rst2;
                String sql="select count(co_reg)+1 as co_reg from tbh_inv where co_emp="+coemp+" and co_itm="+coditmPre;
                rst1 = stm.executeQuery(sql);
                if(rst1.next()) intCorRegPre=rst1.getInt(1);
                sql="select count(co_reg)+1 as co_reg from tbh_inv where co_emp="+coemp+" and co_itm="+coditmDes;
                rst2 = stm.executeQuery(sql);
                if(rst2.next()) intCorRegDes=rst2.getInt(1);
                
                
                sql="INSERT INTO tbh_inv(co_emp,co_itm,co_reg,tx_codAlt,tx_codAlt2,tx_nomItm,co_cla1,co_cla2,co_cla3,co_uni,nd_itmUni," +
                "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod," +
                "co_usrIng,co_usrMod,nd_stkact)" +
                " SELECT co_emp,co_itm, "+intCorRegPre+",tx_codAlt,tx_codAlt2,tx_nomItm,co_cla1,co_cla2,co_cla3,co_uni,nd_itmUni," +
                "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing," +
                "fe_ultMod,co_usrIng,co_usrMod,nd_stkact FROM tbm_inv WHERE co_emp="+coemp+" and co_itm="+coditmPre;
                 //txtsql.append( sql +"; \n ");
            
                 sql="INSERT INTO tbh_inv(co_emp,co_itm,co_reg,tx_codAlt,tx_codAlt2,tx_nomItm,co_cla1,co_cla2,co_cla3,co_uni,nd_itmUni," +
                "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod," +
                "co_usrIng,co_usrMod,nd_stkact)" +
                " SELECT co_emp,co_itm, "+intCorRegDes+",tx_codAlt,tx_codAlt2,tx_nomItm,co_cla1,co_cla2,co_cla3,co_uni,nd_itmUni," +
                "st_ser,nd_preVta1,nd_preVta2,nd_preVta3,nd_stkMin,nd_stkMax,st_ivaCom,st_ivaVen,tx_obs1,tx_obs2,st_reg,fe_ing," +
                "fe_ultMod,co_usrIng,co_usrMod,nd_stkact FROM tbm_inv WHERE co_emp="+coemp+" and co_itm="+coditmDes;
                //txtsql.append( sql +"; \n ");  
                  
                   
                sql="INSERT INTO tbh_uniinv(co_emp,co_itm,co_reg,co_itmUni,co_regUni,fe_uni,co_usruni)" +
                " SELECT "+coemp+","+coditmPre+","+intCorRegPre+","+coditmDes+","+intCorRegDes+",CURRENT_TIMESTAMP,"+objParSis.getCodigoUsuario();
                //txtsql.append( sql +"; \n "); 
                
                blnres=true;
                
             }
                catch (java.sql.SQLException e) { blnres=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e)  {  blnres=false;  objUti.mostrarMsgErr_F1(this, e); }
         return blnres;
     }
     
     
  //*****************************************************************************************************//
  //*****************************************************************************************************//
 
     
    
    
     public void CerrarVentana(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
     }
     
     
       
     /**
      *Esta Funcion se encarga de hacer el proceso de unificacion de Item
     */
       
    
    public boolean procesar(java.sql.Connection conn, int x)
    {  boolean blnres=true;

        int CodItm1 = Integer.parseInt(tblDat.getValueAt(x,INT_TBL_COD_ITM).toString() );
        int CodItm2 = Integer.parseInt(tblDat.getValueAt(x,INT_TBL_COD_ITM2).toString());
        arlCam.add(tblDat.getValueAt(x,INT_TBL_COD_ITM).toString());
        try
        {
           int intCodEmp=objParSis.getCodigoEmpresa();
           int intCodLoc=objParSis.getCodigoLocal();
           if (conn!=null)
            {
                
               if(CodItm1==CodItm2) return false;
                
                if(ProcesaDatos(conn,CodItm1,CodItm2)){
                    
                    blnres=true; 
                } else{
                       blnres=false;  
                       }
              }
          }
          catch (Exception e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
         return blnres;
   }
    
    
    
    
      
    
    
      /**   
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus)
    {
        String strAli, strCam;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {  
            strAli="Código, Alterno, Nombre";
            strCam="a1.co_itm, a1.tx_codAlt, a1.tx_nomItm";
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de inventario");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodItm.setText(objVenCon.GetCamSel(1));
                        txtCodAlt.setText(objVenCon.GetCamSel(2));
                        txtNomItm.setText(objVenCon.GetCamSel(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenCon.buscar("LOWER(a1.tx_codAlt) LIKE '" + txtCodAlt.getText().toLowerCase() + "'"))
                    {
                        txtCodItm.setText(objVenCon.GetCamSel(1));
                        txtCodAlt.setText(objVenCon.GetCamSel(2));
                        txtNomItm.setText(objVenCon.GetCamSel(3));
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtCodAlt.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodItm.setText(objVenCon.GetCamSel(1));
                            txtCodAlt.setText(objVenCon.GetCamSel(2));
                            txtNomItm.setText(objVenCon.GetCamSel(3));
                        }
                        else  
                        {
                            txtCodItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (objVenCon.buscar("LOWER(a1.tx_nomItm) LIKE '" + txtNomItm.getText().toLowerCase() + "'"))
                    {
                        txtCodItm.setText(objVenCon.GetCamSel(1));
                        txtCodAlt.setText(objVenCon.GetCamSel(2));
                        txtNomItm.setText(objVenCon.GetCamSel(3));
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtNomItm.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodItm.setText(objVenCon.GetCamSel(1));
                            txtCodAlt.setText(objVenCon.GetCamSel(2));
                            txtNomItm.setText(objVenCon.GetCamSel(3));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerrar;
    private javax.swing.JButton butCon1;
    private javax.swing.JButton butCon2;
    private javax.swing.JButton butEnvDat;
    private javax.swing.JButton butEnvDat1;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butProce;
    private javax.swing.JCheckBox chkSer;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBut2;
    private javax.swing.JPanel panButCen;
    private javax.swing.JPanel panButCon2;
    private javax.swing.JPanel panButPro;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panConBut1;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTblBut1;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane spnDat1;
    private javax.swing.JScrollPane spnDat2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDat2;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables
     private javax.swing.ButtonGroup bgrFil;
    
     
  /*
   *Esta Funcion de encarga de Llenar datos en la Tabla 1 
   *que corresponde a tblDat1 datos del lado izquierdo 
   */
     

   private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        double dblSub, dblIva;
        boolean blnRes=true;
        try
        {
         //   butCon.setText("Detener");
          //  lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
            
                
                if (strcod1.length()>0)
                    strAux+=" AND b.co_itm=" + strcod1;
                if (strdes1.length()>0 || strhas1.length()>0)
                    strAux+=" AND ((LOWER(b.tx_codAlt) BETWEEN '" + strdes1 + "' AND '" + strhas1 + "') OR LOWER(b.tx_codAlt) LIKE '" + strhas1 + "%')";
             
                
                strSQL="";
              
               strSQL = " SELECT co_itmmae,co_itm,tx_codalt,tx_nomitm,tx_descor FROM ( select a.co_itmmae,b.co_itm,b.tx_codalt,b.tx_nomitm,c.tx_descor , b.st_Ser  FROM  tbm_equinv as a"; 
               strSQL +=" inner join tbm_inv as b on (a.co_emp=b.co_emp and a.co_itm=b.co_itm )";
               strSQL +=" LEFT OUTER JOIN tbm_var AS c ON (b.co_uni=c.co_reg) ";
               strSQL +=" WHERE b.st_reg='A' and b.co_emp="+intCodEmp;
               strSQL+=strAux;
               strSQL+=" ORDER BY b.tx_codAlt ) AS  x  WHERE x.co_itmmae not in (" +
               " select co_itmmae from tbt_unificacion_empresa where st_reg='A' )";


               if(chkSer.isSelected()){
                   strSQL = strSQL+ " and st_Ser='S'";
                   
               }
               
                
                System.out.println(">>>>> "+strSQL);
               
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
             
                i=0;
                while (rst.next())
                {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LIN,"");
                        vecReg.add(INT_TBL_BUT_KAR," ");
                        vecReg.add(INT_TBL_BUT_STK," ");
                        vecReg.add(INT_TBL_BUT_INV," ");
                        vecReg.add(INT_TBL_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_UNI_ITM,rst.getString("tx_descor"));  //*******
                        vecReg.add(INT_TBL_NOM_ITM,rst.getString("tx_nomItm"));
                          
                        vecReg.add(INT_TBL_COD_ITM2,"");
                        vecReg.add(INT_TBL_COD_ALT2,"");
                        
                        vecDat.add(vecReg);
                        i++;
                       // pgrSis.setValue(i);
                 
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
////////                if (intNumTotReg==tblDat.getRowCount())
////////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
////////                else
////////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
////////                pgrSis.setValue(0);
////////                butCon.setText("Consultar");
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
    
     
  
   
    /*
   *Esta Funcion de encarga de Llenar datos en la Tabla 2 
   *que corresponde a tblDat2 datos del lado derecho 
   */
   private boolean cargarDetReg2()
    {
        
        int intCodEmp, intCodLoc, intNumTotReg, i;
        double dblSub, dblIva;
        boolean blnRes=true;
        
        //System.out.println("OKIIIIII");
        
        try
        {
         //   butCon.setText("Detener");
          //  lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            con2=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con2!=null)
            {
                stm2=con2.createStatement();
                //Obtener la condición.
                strAux2="";
               
                
                if (strcod2.length()>0)
                    strAux2+=" AND b.co_itm=" + strcod2;
                if (strdes2.length()>0 || strdes2.length()>0)
                    strAux2+=" AND ((LOWER(b.tx_codAlt) BETWEEN '" + strdes2 + "' AND '" + strdes2 + "') OR LOWER(b.tx_codAlt) LIKE '" + strdes2 + "%')";
            
                //Obtener el número total de registros.
                strSQL="";
                
        
                //Armar la sentencia SQL.
         
               
               strSQL = "SELECT co_itmmae,co_itm,tx_codalt,tx_nomitm,tx_descor FROM ( select a.co_itmmae,b.co_itm,b.tx_codalt,b.tx_nomitm,c.tx_descor , b.st_Ser from tbm_equinv as a"; 
               strSQL +=" inner join tbm_inv as b on (a.co_emp=b.co_emp and a.co_itm=b.co_itm )";
               strSQL +=" LEFT OUTER JOIN tbm_var AS c ON (b.co_uni=c.co_reg) ";
               strSQL +=" WHERE  b.st_reg='A' and  b.co_emp="+intCodEmp;
               strSQL+=strAux2;
               strSQL+=" ORDER BY b.tx_codAlt ) AS  x WHERE x.co_itmmae not in ( " +
               " select co_itmmae from tbt_unificacion_empresa where st_reg='A' )";
                            
            
               
               if(chkSer.isSelected()){
                   strSQL = strSQL+ " and st_Ser='S'";
                   
               }
               
               
               System.out.println("CONSULTA >>> "+ strSQL );
               
                rst2=stm2.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat2.clear();
                //Obtener los registros.
              ////  lblMsgSis.setText("Cargando datos...");
               //// pgrSis.setMinimum(0);  
                ////pgrSis.setMaximum(intNumTotReg);
               //// pgrSis.setValue(0);
                i=0;
                while (rst2.next())
                {   
                  
                        vecReg2=new Vector();
                        vecReg2.add(INT_TBL_LIN1,"");
                        vecReg2.add(INT_TBL_BUT_KAR1," ");
                        vecReg2.add(INT_TBL_BUT_STK1," ");
                        vecReg2.add(INT_TBL_BUT_INV1," ");
                        vecReg2.add(INT_TBL_COD_MAE1,rst2.getString("co_itmMae"));
                        vecReg2.add(INT_TBL_COD_ITM1,rst2.getString("co_itm"));
                        vecReg2.add(INT_TBL_COD_ALT1,rst2.getString("tx_codAlt"));
                        vecReg2.add(INT_TBL_UNI_ITM1,rst2.getString("tx_descor"));  //*******
                        vecReg2.add(INT_TBL_NOM_ITM1,rst2.getString("tx_nomItm"));
                        
                        vecDat2.add(vecReg2);
                        i++;
                       // pgrSis.setValue(i);
                   // }
                   
                }
                rst2.close();
                stm2.close();
              
                  
                con2.close();
                rst2=null;
                stm2=null;
                con2=null;
                //Asignar vectores al modelo.
                objTblMod2.setData(vecDat2);
                tblDat2.setModel(objTblMod2);
                vecDat2.clear();
////////                if (intNumTotReg==tblDat.getRowCount())
////////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
////////                else
////////                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
////////                pgrSis.setValue(0);
////////                butCon.setText("Consultar");
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
    
     
   
   
   
     
      private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
               // lblMsgSis.setText("Listo");
              //  pgrSis.setValue(0);
              //  butCon.setText("Consultar");
            ///}
            //Establecer el foco en el JTable sólo cuando haya datos.
           /// if (tblDat.getRowCount()>0)
           //// {
               // tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
     
     
     
      
      
      
      
      private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LIN:
                    strMsg="";
                    break;
                    
                 case INT_TBL_COD_ALT:
                    strMsg="Código de Alternativo del Item";
                    break;
                 
                case INT_TBL_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;                
                    
                 case INT_TBL_COD_ALT2:
                    strMsg="Código de Alternativo del Item 2";
                    break;
             
                    
             
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
         private class ZafMouMotAda2 extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat2.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LIN1:
                    strMsg="";
                    break;
                    
                 case INT_TBL_COD_ALT1:
                    strMsg="Código de Alternativo del Item";
                    break;
                 
                case INT_TBL_NOM_ITM1:
                    strMsg="Nombre del Item";
                    break;                
                    
             
                default:
                    strMsg="";
                    break;
            }
            tblDat2.getTableHeader().setToolTipText(strMsg);
        }
    }
         
         
         
         
         
         private void recostearItmGrp()
         {
            int i,j;
            String strFecRan[][];
            
             try
             {    
              java.sql.Connection con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
              if (con!=null)
              {  
                  
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            System.out.println(""+  objUti.formatearFecha(datFecAux,"dd/MM/yyyy")   );             
                strFecRan=objUti.getIntervalosMensualesRangoFechas(  "01/12/2005",   objUti.formatearFecha(datFecAux,"dd/MM/yyyy") , "dd/MM/yyyy");
                          
                      for(int y=0; y<arlCam.size(); y++){
                             System.out.println(""+ arlCam.get(y).toString() );
             
                            for (j=0; j<strFecRan.length; j++)
                               objUti.recostearItmGrp(this, objParSis, con,  objParSis.getCodigoEmpresa(), arlCam.get(y).toString() , strFecRan[j][0], strFecRan[j][1], "yyyy/MM/dd");
                         // System.out.println(">> "+ strFecRan[j][0] +" >> "+  strFecRan[j][1]  ); 
                     }   
                 arlCam.clear();
              }  
              con.close();
              con=null;
             }
            catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
         }
             
         
         
          
    private class ZafThreadGUI3 extends Thread
    {
        public void run()
        {
            
            obj.show();
            objThrGUI3=null;
        }
    } 
 
           
 
    
           
    private class ZafThreadGUI4 extends Thread
    {
        public void run()
        {
            txtsql.setText("");
            RealizaUnificacion();
            obj.dispose();
            obj=null;
            objThrGUI3=null;
            objThrGUI4=null;
        }
    } 
 
    
    
    
     public boolean RealizaUnificacion(){
      boolean blnres=true;
        try{
           con3=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con3!=null)
            {
                
               con3.setAutoCommit(false);    
                
               if(ProcesoUnificacion(con3))
               {
                    System.out.println("FINALIZADO UNIFICACIOIN....... ");
                    if(intVerifi==3){
                        intVerifi=4;
                        if(verificaError1(con3)){
                            if(verificaError2(con3)){
                               con3.commit(); 
                               enviarCorreoMasivo(strCorEle,"Unificacion de Items",strItems + " \n Procesar " + objUti.getFechaServidor(objParSis.getStringConexion(), 
                                            objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos()));
                               intVerifi=3;
                            }else 
                               con3.rollback(); 
                        }else 
                            con3.rollback(); 
                    }   
                                                          
                               System.out.println("OK");
                    if(intVerifi==3){     
                        cargarDetReg2();
                        actbut1();
                    }
                if(intVerifi==3)  mostrarMsg("El Proceso de Unificación fue satisfactoria..");
                if(intVerifi==0)  mostrarMsg("No hubo datos para el proceso de Unificación. ");
                if(intVerifi==2)  mostrarMsg("Hubo Problemas al realizar el Proceso de Unificación ..");    
                if(intVerifi==4)  mostrarMsg("Hubo Problemas al realizar el Proceso de Unificación ..\n hay codigo que ya estan es espera de Unificacion... ");    
             }
            else { 
                System.out.println("ERROR AL UNIFICAR");
                con3.rollback();  
                mostrarMsg("ERROR...............");
            }  
         
             con3.close();
             con3=null;
           }}
           catch (java.sql.SQLException e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
           catch (Exception e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnres;
    }
    
    
     
     
     private boolean verificaError1(java.sql.Connection conn){
         boolean blnres=false;
         try{
             
              String sql ="SELECT * FROM (" +
                            " select  count(co_itmuni) as valor  from tbt_unificacion_empresa where st_reg='A' group by co_itmuni" +
                            " ) as x where valor >=2";
                            java.sql.Statement stm;
                            java.sql.ResultSet rst;
                            stm = con3.createStatement();  
                            rst = stm.executeQuery(sql);
                            if(rst.next()){
                              blnres=false;
                            }else  blnres=true; 
             rst=null;
             stm=null;
         } catch (java.sql.SQLException e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
       return blnres;
     }
     
     
     
      
      private boolean verificaError2(java.sql.Connection conn){
         boolean blnres=false;
         try{
             
              String sql ="SELECT * FROM tbt_unificacion_empresa WHERE st_reg='A' and co_itmuni  in (" +
              " SELECT co_itmpre from tbt_unificacion_empresa where st_reg='A' )";
                            java.sql.Statement stm;
                            java.sql.ResultSet rst;
                            stm = con3.createStatement();  
                            rst = stm.executeQuery(sql);
                            if(rst.next()){
                              blnres=false;
                            }else  blnres=true; 
             rst=null;
             stm=null;
         } catch (java.sql.SQLException e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
       return blnres;
     }
     
    
    
//     public boolean RealiUnificacionPorEmpresa(){
//       String sql="";
//       boolean blnres=true;
//          int intcodreg=0; 
//          int intregorg=0;
//          int intregdes=0;
//        try{
//           
//              
//            //***************************
//          
//              for (int j=0;j<arlDat.size();j++){
//                 intcodreg=objUti.getIntValueAt(arlDat,j, INT_CO_REG );
//                 intregorg=objUti.getIntValueAt(arlDat,j, INT_CO_REGORG );
//                 intregdes=objUti.getIntValueAt(arlDat,j, INT_CO_REGDES );
//                
//                   if(j==0){  
//                       java.sql.PreparedStatement stmTuval;
//                       System.out.println("TUVAL >> "+ objParSis.getStringConexion(intregdes) );
//                       if(Abrir_Conexion2( objParSis.getStringConexion(intregdes), objParSis.getUsuarioBaseDatos(intregdes), objParSis.getClaveBaseDatos(intregdes) )){
//
//                           stmTuval = CONN_TUVAL.prepareStatement(txtsql.getText()); 
//                           stmTuval.executeUpdate();
//                           System.out.println("TUVAL OKI >> ");
//                           intControlTuval=1; 
//                       //Cerrar_Conexion2();  
//                    }else { intControlTuval=0; intVerifi=2;  blnres=false;  break; }
//                   }
//                   
//                   
//                   
//                   if(j==1){  
//                       java.sql.PreparedStatement stmCastek;
//                       System.out.println("CASTEK >> "+ objParSis.getStringConexion(intregdes) );
//                       if(Abrir_Conexion3( objParSis.getStringConexion(intregdes), objParSis.getUsuarioBaseDatos(intregdes), objParSis.getClaveBaseDatos(intregdes) )){
//  
//                           stmCastek = CONN_CASTEK.prepareStatement(txtsql.getText()); 
//                           stmCastek.executeUpdate();
//                           System.out.println("CASTEK OKI >> ");
//                          intControlCastek=1;
//                      /// Cerrar_Conexion3();  
//                    }else  { intControlCastek=0; intVerifi=2;  blnres=false; break; }
//                   }
//                         
//                    
//              }
//             //***************************  
//           
//           
//           }
//      //     catch (java.sql.SQLException e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
//           catch (Exception e) { blnres=false; intVerifi=2; objUti.mostrarMsgErr_F1(this, e);  }
//       
//      return blnres;   
//    }
//   
//   
//     
     
     
     
      public boolean Abrir_Conexion2(String str_strcon, String str_usrcon, String str_cla){
        boolean bln=false;
           try{
            //System.out.println("ABRIR CONEXION...."); 
            CONN_TUVAL = DriverManager.getConnection( str_strcon, str_usrcon, str_cla );          
            CONN_TUVAL.setAutoCommit(false);
            bln=true;
           }
           catch(SQLException  Evt){  System.out.println("Error "+Evt.toString() ); }
         return bln;
      }
    
      
    
     public void Cerrar_Conexion2(){
        try{
           ///System.out.println("CERRANDO CONEXION....");
            CONN_TUVAL.close();
            CONN_TUVAL=null; 
        }
           catch(SQLException  Evt){   System.out.println("Error "+Evt.toString() );  }
    }
    
     
    
     
      public boolean Abrir_Conexion3(String str_strcon, String str_usrcon, String str_cla){
        boolean bln=false;
           try{
            //System.out.println("ABRIR CONEXION...."); 
            CONN_CASTEK = DriverManager.getConnection( str_strcon, str_usrcon, str_cla );          
            CONN_CASTEK.setAutoCommit(false);
            bln=true;
           }
           catch(SQLException  Evt){  System.out.println("Error "+Evt.toString() ); }
         return bln;
      }
    
    
    
     public void Cerrar_Conexion3(){
        try{
           ///System.out.println("CERRANDO CONEXION....");
            CONN_CASTEK.close();
            CONN_CASTEK=null; 
        }
           catch(SQLException  Evt){   System.out.println("Error "+Evt.toString() );  }
    }
    
     
     
    
//    public boolean RealiUnificacionPorEmpresa(){
//       String sql="";
//       boolean blnres=true;
//       Connection conLoc;
//       Connection conEmp;
//       Statement stmLoc;
//       Statement stmEmp;
//       ResultSet rstEmp;
//       java.sql.PreparedStatement stmLoc2; 
//        
//       try{
//           conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (conLoc!=null)
//            {
//                
//               conLoc.setAutoCommit(false);    
//               intVerifi = 0; 
//       
//        for(int i=1; i<objParSis.getNumeroFilas(); i++){
//                System.out.println(">>" + objParSis.getCodigoEmpresa( Byte.parseByte(""+i) ) + " <> "+  objParSis.getNombreEmpresa(i) );
//                sql="select co_itmuni,co_itmpre from tbt_unificacion_empresa where co_emp="+objParSis.getCodigoEmpresa(Byte.parseByte(""+i));
//                stmLoc=conLoc.createStatement();
//                rstEmp = stmLoc.executeQuery(sql);
//                while(rstEmp.next()){
//                  
//                  if(ProcesoUnificacionPorEmpresa(i,rstEmp.getInt(1),rstEmp.getInt(2), objParSis.getCodigoEmpresa(Byte.parseByte(""+i)) ))
//                      intVerifi=1;
//                  else intVerifi=0;
//                     
//                }
//                  
//                  sql="DELETE FROM tbt_unificacion_empresa where co_emp="+objParSis.getCodigoEmpresa(Byte.parseByte(""+i));
//                  stmLoc2 = conLoc.prepareStatement(sql); 
//                  stmLoc2.executeUpdate();
//                  
//                  
//                if(intVerifi==1){
//                    conLoc.commit();
//                    conLoc.setAutoCommit(false);    
//                }else conLoc.rollback();
//        }
//           conLoc.close();
//           conLoc=null;
//           }}
//           catch (java.sql.SQLException e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
//           catch (Exception e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
//       
//      return blnres;   
//    }
//    
    
      
    
       
  //*************************************************************************************** 
    
//      public boolean ProcesoUnificacionPorEmpresa(int intIndEmp, int co_itmuni, int co_itmpre, int intCodEmp){
//      boolean blnres=true;
//        try{
//           con=DriverManager.getConnection(objParSis.getStringConexion(intIndEmp), objParSis.getUsuarioBaseDatos(intIndEmp), objParSis.getClaveBaseDatos(intIndEmp));
//            if (con!=null)
//            {
//                
//               con.setAutoCommit(false);    
//               intVerifi = 0; 
//                              
//                       int CodItm1 = co_itmuni;
//                       int CodItm2 = co_itmpre;
//                       
//            
//                            if(ProcesaDatosPorEmpresa(con,CodItm1,CodItm2, intCodEmp)){
//                                blnres=true; 
//                                intVerifi=3;
//                            } else{
//                                  blnres=false;  
//                                  intVerifi=2;
//                                  //conn.rollback(); 
//                            }
//             
//                     
//            if(intVerifi==3) {
//                con.commit();
//                mostrarMsg("El Proceso de Unificación fue satisfactoria..");
//                blnres=true;
//            }
//            if(intVerifi==0){
//                //mostrarMsg("No hubo datos para el proceso de Unificación. ");
//                blnres=true; 
//            }
//               
//            if(intVerifi==2) {
//                 mostrarMsg("Hubo Problemas al realizar el Proceso de Unificación ..");    
//                 con.rollback();
//                 blnres=false;
//            }  
//               
//               
//             con.close();
//             con=null;
//           }}
//           catch (java.sql.SQLException e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
//           catch (Exception e) { blnres=false; objUti.mostrarMsgErr_F1(this, e);  }
//        return blnres;
//    }
//    
//    
    
    /**
    * EFLORESA - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: cuentaFicticia@tuvalsa.com; cuentaFicticia2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String subject, String strMsj ){
        try {

            String server = "mail.tuvalsa.com";
            String userName = "zafiro@tuvalsa.com";
            String password = "#tuv*sis28/10=";
            String fromAddres = "zafiro@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = "";
            String bcc = "";
            boolean htmlFormat = false;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }

    /**
    * EFLORESA - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: correoFicticio@tuvalsa.com; correoFicticio2@tuvalsa.com
    * @param strCorEleCC - Cuentas de correo a las que se enviara copia del email; estas deben estar separadas por ";". Ej: correoFicticio3@tuvalsa.com; correoFicticio4@tuvalsa.com
    * @param strCorEleCCO - Cuentas de correo a las que se enviara copia oculta del email; estas deben estar separadas por ";". Ej: correoOculto@tuvalsa.com; correoOculto2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String strCorEleCC, String strCorEleCCO, String subject, String strMsj ){
        try {

            String server = "mail.tuvalsa.com";
            String userName = "zafiro@tuvalsa.com";
            String password = "#tuv*sis28/10=";
            String fromAddres = "zafiro@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = (strCorEleCC==null?"":(strCorEleCC.equals("")?"":strCorEleCC));
            String bcc = (strCorEleCCO==null?"":(strCorEleCCO.equals("")?"":strCorEleCCO));
            boolean htmlFormat = false;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }

    public boolean sendMailTuvMas(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) {
        boolean blnRes=false;
        try{

           Properties props = System.getProperties();
//           props.put("mail.smtp.auth", "true");
//           props.put("mail.smtp.ssl.trust", server);
//           props.put("mail.smtp.port", "587");
//           props.put("mail.smtp.starttls.enable", "true");
           //System.out.println("---2---");
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.ssl.trust", server);
           props.put("mail.smtp.host", server);
           props.put("mail.smtp.user", userName);
           props.put("mail.smtp.password", password);
           props.put("mail.smtp.port", "587");
           props.put("mail.smtp.auth", "true");  

            Authenticator auth = new MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);
            session.setDebug(true);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to addresses
            StringTokenizer tokens = new StringTokenizer(toAddress, ";");
            while (tokens.hasMoreTokens())
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(tokens.nextToken()));
            tokens=null;
            
            if (! ccAddress.equals("")){
                tokens = new StringTokenizer(ccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }
            
            if (! bccAddress.equals("")){
                tokens = new StringTokenizer(bccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }

            // Set the subject
            message.setSubject(subject);

            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            Transport.send(message);

            blnRes=true;

        }catch(MessagingException e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }catch(Exception e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }
        return blnRes;
    }
    
    
        static class MyAuthenticator extends Authenticator {
        PasswordAuthentication l = new PasswordAuthentication("zafiro@tuvalsa.com", "#tuv*sis28/10=");
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return l;
        }
    }
    
    
    
}

    





