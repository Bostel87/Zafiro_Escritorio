/*
 * 
 *
 * Created on 13 de agosto de 2008, 11:41  
 */
 
package Ventas.ZafVen28;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafEvt.ZafAsiDiaAdapter;
import Librerias.ZafEvt.ZafAsiDiaEvent;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafEstFacEle.ZafCabEstFacEle;
import Librerias.ZafEstFacEle.ZafEstFacEle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import Librerias.ZafPulFacEle.ZafPulFacEle;  //////José Marín 19/Dic/2014
import Librerias.ZafStkInv.ZafStkInv;
import ZafReglas.ZafGenFac;
import ZafReglas.ZafImp;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import GenOD.ZafGenOdDaoPryTra;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafGenDocCobAut.ZafGenDocCobAut;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.File;

/**  
 *   
 * @author  
 * MODIFICADO EFLORESA
 * PROGRAMA DE NOTAS DE CREDITO. 
 * 
 */
public class ZafVen28 extends JInternalFrame 
{
    //Constantes: Jtable
    private static final int INT_TBL_LINEA=0;
    private static final int INT_TBL_CODITM=1;
    private static final int INT_TBL_CODALT=2;
    private static final int INT_TBL_NOMITM=3;
    private static final int INT_TBL_DESUNI=4;
    private static final int INT_TBL_CODBOD=5;
    private static final int INT_TBL_NOMBOD=6;
    private static final int INT_TBL_CANVEN=7;
    private static final int INT_TBL_CANDEV=8;
    private static final int INT_TBL_PREUNI=9;
    private static final int INT_TBL_PREDEV=10; 
    private static final int INT_TBL_PORDES=11;
    private static final int INT_TBL_CANDES=12;
    private static final int INT_TBL_IVA=13;
    private static final int INT_TBL_TOT=14;
    private static final int INT_TBL_CODITMACT=15;
    private static final int INT_TBL_COSTO=16;
    private static final int INT_TBL_CODALT2=17;
    private static final int INT_TBL_ITMSER=18;
    private static final int INT_TBL_CANDIFDEV=19;
    private static final int INT_TBL_CODREGSOL=20;
    private static final int INT_TBL_CODLOC_OC  = 21;  
    private static final int INT_TBL_CODTIP_OC  = 22;  
    private static final int INT_TBL_CODDOC_OC  = 23;  
    private static final int INT_TBL_CODREG_OC  = 24;
    private static final int INT_TBL_CODREGDEV  = 25;
    private static final int INT_TBL_CANDEV_ORI = 26;
    private static final int INT_TBL_CANDEVACEBOD=27;
    private static final int INT_TBL_ACEINGSIS = 28;
    private static final int INT_TBL_SUMTOTINGSIS=29;
    private static final int INT_TBL_SUMTOTBODACE=30;
    private static final int INT_TBL_CANDEVPREDES_SOL = 31;
    private static final int INT_TBL_CANDEVPREDES_ORI = 32;
    private static final int INT_TBL_SUMTOTCANDEV = 33;
    private static final int INT_TBL_IEBODFIS   = 34;  // estado que dice si ingresa/egresa fisicamente en bodega
    private static final int INT_TBL_DEVPRVAUT   = 35;  // estado que dice si ingresa/egresa fisicamente en bodega
    private static final int INT_TBL_SEDEVPRV=36;
    private static final int INT_TBL_NODEVPRV=37;
    private static final int INT_TBL_CODLOC_OT  = 38;  
    private static final int INT_TBL_CODTIP_OT  = 39;  
    private static final int INT_TBL_CODDOC_OT  = 40;  
    private static final int INT_TBL_CODREG_OT  = 41;
    private static final int INT_TBL_CANSOLSTOCK=42;
    private static final int INT_TBL_CODCTAEGR=43;
    private static final int INT_TBL_TXTCTAEGR=44;
    private static final int INT_TBL_DESCTAEGR=45;
    /*private static final int INT_TBL_CANNUNRECFAC=46;
    private static final int INT_TBL_CANFAC=47;*/
    
    Connection CONN_GLO=null;
    Statement  STM_GLO=null;
    ResultSet  rstCab=null;
    
    private ZafParSis objParSis;
    private UltDocPrint objUltDocPrint; 
    private ZafInvItm objInvItm;  
    private ZafTblMod objTblMod;
    private ZafAsiDia objAsiDia; // Asiento de Diario
    private ZafCtaCtb_dat objCtaCta_Dat;  //Datos de Cuentas contables
    private ZafDatePicker txtFecDoc;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private Date datFecAux;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafVenCon vcoTipDoc; 
    private ZafVenCon vcoVen;
    private ZafPulFacEle objPulFacEle;  /////  José Marín 19/Dic/2014
    private ZafCorEle objCorEle;
    
    String strAux="";
    String strDesCorTipDoc="";
    String strDesLarTipDoc="";
    String strCodTipDoc="";
    String strCodSol="";
    String strDesSol="";
    String strDesCorTipDocSol="";
    String strDesLarTipDocSol="";
    String strCodTipDocSol="";
    String strCodCli="";
    String strNomCli="";
    String strMerIngEgr="", strTipIngEgr="";
    String stIvaVen="S";
    String GLO_strArreItm="";
    String strFecFac="";

    Vector vecCab=new Vector();

    double dblTotalDoc=0;
    double dblPorIva=0;

    int intTipDocTraAut=116;  // ORDER DE TRABAJO AUTOMATICO..
    int Glo_intCodSec=0;  
    int intSecuencialFac=0;

    ZafUtil objUti;
    mitoolbar objTooBar;

    boolean blnHayCam=false;

    JTextField txtCodTipDoc = new JTextField();
    JTextField txtCodTipDocSol = new JTextField();
    JTextField txtCodLocRelFac = new JTextField();
    JTextField txtCodTipDocRelFac = new JTextField();
    JTextField txtCodDocRelFac = new JTextField();
    JTextField txtCodComFac = new JTextField();
    JTextField txtNomVenFac = new JTextField();
    JTextField txtAteFac = new JTextField();
    JTextField txtDirCliFac = new JTextField();
    JTextField txtCiuFac = new JTextField();
    JTextField txtTelCliFac = new JTextField();
    JTextField txtRucFac = new JTextField();
    JTextField txtConInv = new JTextField();      
    JTextArea txtsql = new JTextArea();
    JTextField txtCodLocSol = new JTextField();

    StringBuffer stbDocRelEmpLoc;
    int intDocRelEmpLoc=0;

    String strCodEmpOC="";
    String strCodLocOC="";
    String strCodTipOC="";
    String strCodDocOC="";
    boolean blnEstCarOC=false;

    String glo_strImpGui="S";

    private String strarritmotr[][] = null;  
    java.util.ArrayList arlItmRec;
    boolean blnMerFal=false;
    String strMensMerFal="";
    private String strSQL;
    private int INT_COD_TIP_DOC_ORD_CON=117;
    private int INT_COD_LOC_ORD_CON=1;
    private int intCodDocOrdCon;
    private int intNumDocOrdConImp;
    private int intCodBodDes = 0;
    String strCorEleTo = "";
    String strFacDev="";
    
    //Constantes para Manejar inventario segun proyecto de transferencias.
    final int INT_ARL_STK_INV_STK=0;        // nd_stk
    final int INT_ARL_STK_INV_STK_DIS=10;   // nd_stkDisp
    
    private  boolean blnPruebas=false;    // blnPruebas= false para pasar a produccion.
    
    boolean blnisDevAut = false;
    private int intCodMen;
    private int intCodEmpDevVen, intCodLocDevVen, intCodDocDevVen; //Rose

    private boolean blnIsCosenco,blnIsEcuatosa, blnIsDetopacio;
    
    private double dblPorComSol=0d;
    
    private String strVer = " v6.38";
    
    /**
     * Constructor Principal.
     */
    public ZafVen28(ZafParSis obj) 
    {
        System.out.println("CONSTRUCTOR PRINCIPAL - ZAFVEN28");
        try 
        {
            this.objParSis = (ZafParSis) obj.clone();
            intCodEmpDevVen    = objParSis.getCodigoEmpresa();
            intCodLocDevVen    = objParSis.getCodigoLocal();
            System.out.println("0000 codemp ok: "+intCodEmpDevVen+"   "+intCodLocDevVen);
            
            initComponents();
            objCorEle = new ZafCorEle(objParSis); 
            objUti = new ZafUtil();
            objAsiDia = new ZafAsiDia(this.objParSis);
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objParSis);
            objAsiDia.addAsiDiaListener(new ZafAsiDiaAdapter() 
            {
                @Override
                public void beforeConsultarCuentas(ZafAsiDiaEvent evt) 
                {
                   if (txtCodTipDoc.getText().equals(""))
                       objAsiDia.setCodigoTipoDocumento(-1);
                   else
                       objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                 }  
            });
 
            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar,"South");
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butFacEle);
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butGetXmlDevVta);

            panAsiDia.add(objAsiDia,BorderLayout.CENTER);

            txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y"); 
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            objUltDocPrint = new UltDocPrint(objParSis);   
            objInvItm = new ZafInvItm(this, objParSis);
  
            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu()); 
            intCodMen=objParSis.getCodigoMenu();   
            
            /*AGREGADO POR FACTURACION ELECTRONICA EN COSENCO, ECUATOSA, DETOPACIO*/
            blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;  // Josémario 3/Feb/2016
            blnIsEcuatosa = (objParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") > -1)?true:false;
            blnIsDetopacio = (objParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") > -1)?true:false;
            /*AGREGADO POR FACTURACION ELECTRONICA EN COSENCO, ECUATOSA, DETOPACIO*/
            
            /*Agtegado para los casos en que se hace la devolucion desde la ventana de devoluciones de forma no automatica*/
            /*para corregir el error del cast en la devolucion de compra*/
            jfrPadre = this;
            /*Agtegado para los casos en que se hace la devolucion desde la ventana de devoluciones de forma no automatica*/
            /*para corregir el error del cast en la devolucion de compra*/
            
        }
        catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);}
    }



    /** Creates new form revisionTecMer */
    public ZafVen28(Librerias.ZafParSis.ZafParSis obj, Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, Integer intCodDoc )
    {
        try
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            
            objParSis.setCodigoEmpresa(intCodEmp);
            objParSis.setCodigoLocal(intCodLoc);

            intCodEmpDevVen    = objParSis.getCodigoEmpresa();
            intCodLocDevVen    = objParSis.getCodigoLocal();
            System.out.println("1111 seteado co_emp: "+intCodEmpDevVen+"  co_loc: "+intCodLocDevVen);
            
            initComponents();
            objUti = new ZafUtil();

            strCodEmpOC= String.valueOf( intCodEmp.intValue() );
            strCodLocOC= String.valueOf( intCodLoc.intValue() );
            strCodTipOC= String.valueOf( intCodTipDoc.intValue() );
            strCodDocOC= String.valueOf( intCodDoc.intValue() );
            blnEstCarOC=true;
    
            objRptSis=new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objParSis);

            objAsiDia=new ZafAsiDia(this.objParSis);
            objAsiDia.addAsiDiaListener(new ZafAsiDiaAdapter() {
              @Override
              public void beforeConsultarCuentas(ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                     objAsiDia.setCodigoTipoDocumento(Integer.parseInt( strCodTipOC ));
               }
            });
  
            objTooBar = new mitoolbar(this);
            panAsiDia.add(objAsiDia,BorderLayout.CENTER);

            txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            objUltDocPrint = new UltDocPrint(objParSis);
            objInvItm = new ZafInvItm(this, objParSis);

            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            intCodMen=objParSis.getCodigoMenu();
        }
        catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);}
    }



    public ZafVen28(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ) 
    {
        try
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objParSis.setCodigoEmpresa(Integer.parseInt(strCodEmp));
            objParSis.setCodigoLocal(Integer.parseInt(strCodLoc));

            intCodEmpDevVen    = objParSis.getCodigoEmpresa();
            intCodLocDevVen    = objParSis.getCodigoLocal();
            System.out.println("2222 seteado co_emp: "+intCodEmpDevVen+"  co_loc: "+intCodLocDevVen);
            
            initComponents();
            objUti = new ZafUtil();

            strCodEmpOC= strCodEmp;
            strCodLocOC= strCodLoc;
            strCodTipOC= strCodTipDoc;
            strCodDocOC= strCodDoc;
            blnEstCarOC=true;

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);


            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
            @Override
            public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
               if (txtCodTipDoc.getText().equals(""))
                   objAsiDia.setCodigoTipoDocumento(-1);
               else
                   objAsiDia.setCodigoTipoDocumento(Integer.parseInt( strCodTipOC ));
             }
            });

            objTooBar = new mitoolbar(this);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);

            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            intCodMen=objParSis.getCodigoMenu();
        }
        catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);}
    }

    //<editor-fold defaultstate="collapsed" desc="/* José Marín M 08/Abr/2016 - Devolucion Automatica: Genera automaticamente la Nota de Credito*/">
    public JInternalFrame jfrPadre;

    public ZafVen28(Librerias.ZafParSis.ZafParSis obj,int intCodEmpSolDev, int intCodLocSolDev,int intCodTipDocSolDev, int intCodDocSolDev, 
                    String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, JInternalFrame padre )
    {
        try 
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();       
            objCorEle = new ZafCorEle(objParSis);
            
            objParSis.setCodigoEmpresa(intCodEmpSolDev);
            objParSis.setCodigoLocal(intCodLocSolDev);
            objParSis.setCodigoMenu(1918);  // Para cuando se realicen devoluciones automaticas.

            intCodEmpDevVen    = objParSis.getCodigoEmpresa();
            intCodLocDevVen    = objParSis.getCodigoLocal();
            intCodMen    = objParSis.getCodigoMenu(); 
            System.out.println("33333 seteado dev.aut. co_emp: "+intCodEmpDevVen+"  co_loc: "+intCodLocDevVen+" co_mnu: "+intCodMen);
            
            initComponents();

            objUti = new ZafUtil();
            strCodEmpOC = strCodEmp;
            strCodLocOC = strCodLoc;
            strCodTipOC = strCodTipDoc;
            strCodDocOC = strCodDoc;
            blnEstCarOC = true;

            jfrPadre = padre;

            objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            objAsiDia = new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(strCodTipOC));
                    }
                }
            });


            objTooBar = new mitoolbar(this);

            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);

            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            Configura_ventana_consulta();
            
            /*AGREGADO POR FACTURACION ELECTRONICA EN COSENCO, ECUATOSA, DETOPACIO*/
            blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;  // Josémario 3/Feb/2016
            blnIsEcuatosa = (objParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") > -1)?true:false;
            blnIsDetopacio = (objParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") > -1)?true:false;
            /*AGREGADO POR FACTURACION ELECTRONICA EN COSENCO, ECUATOSA, DETOPACIO*/
            

            if (isDevolucionAutomatica(intCodEmpSolDev, intCodLocSolDev, intCodTipDocSolDev, intCodDocSolDev)) {
                System.out.println("DEVOLUCION GENERADA EXITOSAMENTE");
            }
        }
        catch (CloneNotSupportedException e) {  
            objUti.mostrarMsgErr_F1(this, e); 
        }catch(Exception ex){
            objUti.mostrarMsgErr_F1(this, ex); 
            objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error constructor ZaVen28 catch "+ intCodEmpSolDev+" "+intCodLocSolDev+" "+intCodTipDocSolDev+" "+intCodDocSolDev+" Exception: "+ex.getMessage());
            //throw ex;
        }
    }
    //</editor-fold>



    /** Constructor para Botón Consultar en el reporte de Seguimiento de Solicitud de Transferencia de Inventario(Cabecera) */
    public ZafVen28(HashMap map) 
    {
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";
        try
        {
            ZafParSis obj  = (ZafParSis)map.get("objParSis");
            
            strCodEmp  = (String)map.get("strCodEmp");
            strCodLoc  = (String)map.get("strCodLoc");
            strCodTipDoc  = (String)map.get("strCodTipDoc");
            strCodDoc     = (String)map.get("strCodDoc");
            
            objParSis=(ZafParSis)obj.clone();
            
            objParSis.setCodigoEmpresa(Integer.parseInt(strCodEmp));
            objParSis.setCodigoLocal(Integer.parseInt(strCodLoc));

            intCodEmpDevVen    = objParSis.getCodigoEmpresa();
            intCodLocDevVen    = objParSis.getCodigoLocal();
            System.out.println("SeguimientoCabecera: "+intCodEmpDevVen+"  co_loc: "+intCodLocDevVen);
            
            initComponents();
            objUti = new ZafUtil();

            strCodEmpOC= strCodEmp;
            strCodLocOC= strCodLoc;
            strCodTipOC= strCodTipDoc;
            strCodDocOC= strCodDoc;
            blnEstCarOC=true;

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);


            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
            @Override
            public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
               if (txtCodTipDoc.getText().equals(""))
                   objAsiDia.setCodigoTipoDocumento(-1);
               else
                   objAsiDia.setCodigoTipoDocumento(Integer.parseInt( strCodTipOC ));
             }
            });

            objTooBar = new mitoolbar(this);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);

            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());
            intCodMen=objParSis.getCodigoMenu();
        }
        catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);}
    }






public void setEditable(boolean editable) {
 if(editable==true) objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
  else objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
}

public void abrirCon(){
 try{
    CONN_GLO=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
 }catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}

public void CerrarCon(){
 try{
    CONN_GLO.close();
    CONN_GLO=null;
 }catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}

    
     private boolean configurarVenConTipDoc() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.st_meringegrfisbod");
            arlCam.add("a.tx_natdoc");
            arlCam.add("a.ne_ultDoc");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("IE.FisBod.");
            arlAli.add("Tip.IE");
            arlAli.add("Ult.Doc.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");
            arlAncCol.add("15");
            arlAncCol.add("15");
            arlAncCol.add("15");
            
            //Armar la sentencia SQL.   a7.nd_stkTot,        
            String Str_Sql="";
           
             if(objParSis.getCodigoUsuario()==1){
              Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar, a.st_meringegrfisbod, a.tx_natdoc,a.ne_ultDoc from tbr_tipdocprg as b " +//José Marín 18/Dic/2014
              " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
              " where   b.co_emp = " + intCodEmpDevVen + " and " +
              " b.co_loc = " + intCodLocDevVen   + " and " +
              " b.co_mnu = " + objParSis.getCodigoMenu();
             }else {     
                    Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a.st_meringegrfisbod, a.tx_natdoc,a.ne_ultDoc "+ //José Marín 18/Dic/2014
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE "+
                    "  a1.co_emp=" + intCodEmpDevVen +""+
                    " AND a1.co_loc=" + intCodLocDevVen+""+
                    " AND a1.co_mnu=" + objParSis.getCodigoMenu()+""+
                    " AND a1.co_usr=" + objParSis.getCodigoUsuario();
             }
             System.out.println("configurarVenConTipDoc: " + Str_Sql);       
              
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=6;  // ultimo documento
          //José Marín 18/Dic/2014
            
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
     
     
     
     
     
   private boolean configurarVenConSolicitante() {
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
        " where b.co_emp="+intCodEmpDevVen+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";
        vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }
   
   
   

    private boolean configurarTabla(){
        boolean blnRes=false;
      try{
     vecCab.clear();
     vecCab.add(INT_TBL_LINEA,"");
     vecCab.add(INT_TBL_CODITM,"Cod.Itm.");
     vecCab.add(INT_TBL_CODALT,"Cod.Alt.");
     vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
     vecCab.add(INT_TBL_DESUNI,"Des.Uni.");
     vecCab.add(INT_TBL_CODBOD,"Cod.Bod.");
     vecCab.add(INT_TBL_NOMBOD,"Nom.Bod.");
     vecCab.add(INT_TBL_CANVEN,"Can.Ven.");
     vecCab.add(INT_TBL_CANDEV,"Can.Dev.");
     vecCab.add(INT_TBL_PREUNI,"Pre.Uni.");
     vecCab.add(INT_TBL_PREDEV,"Dev.PreUni.");
     vecCab.add(INT_TBL_PORDES,"%Dto.");
     vecCab.add(INT_TBL_CANDES,"Dev.Dto.");
     vecCab.add(INT_TBL_IVA,"Iva.");
     vecCab.add(INT_TBL_TOT,"Total.");
     vecCab.add(INT_TBL_CODITMACT,"Cod.Itm.Act");
     vecCab.add(INT_TBL_COSTO,"Costo.");
     vecCab.add(INT_TBL_CODALT2,"");
     vecCab.add(INT_TBL_ITMSER,"");
     vecCab.add(INT_TBL_CANDIFDEV,"");
     vecCab.add(INT_TBL_CODREGSOL, "");
     vecCab.add(INT_TBL_CODLOC_OC,"Cod.Loc.OC");
     vecCab.add(INT_TBL_CODTIP_OC,"Cod.TipDoc.OC");
     vecCab.add(INT_TBL_CODDOC_OC,"Cod.Doc.OC");
     vecCab.add(INT_TBL_CODREG_OC, "Cod.Reg.OC");
     vecCab.add(INT_TBL_CODREGDEV, "Cod.Reg.Dev");
     vecCab.add(INT_TBL_CANDEV_ORI, "Can.Dev.Ori");
     vecCab.add(INT_TBL_CANDEVACEBOD, "Dev·Bod.");         
     vecCab.add(INT_TBL_ACEINGSIS, "Ace.Ing.Sis");
     vecCab.add(INT_TBL_SUMTOTINGSIS, "Sum.Tot.IngSis");
     vecCab.add(INT_TBL_SUMTOTBODACE, "Sum.Tot.BodAce");
     vecCab.add(INT_TBL_CANDEVPREDES_SOL, "DesPre.Sol");
     vecCab.add(INT_TBL_CANDEVPREDES_ORI, "DevCanPreDes_Ori");
     vecCab.add(INT_TBL_SUMTOTCANDEV,"Sum.Tot.Can.Dev.");
     vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod"); 
     vecCab.add(INT_TBL_DEVPRVAUT,"Dev.Prv."); 
     vecCab.add(INT_TBL_SEDEVPRV,"Si.Dev.Prv."); 
     vecCab.add(INT_TBL_NODEVPRV,"NO.Dev.Prv."); 
     vecCab.add(INT_TBL_CODLOC_OT,"Cod.Loc.OT");
     vecCab.add(INT_TBL_CODTIP_OT,"Cod.TipDoc.OT");
     vecCab.add(INT_TBL_CODDOC_OT,"Cod.Doc.OT");
     vecCab.add(INT_TBL_CODREG_OT, "Cod.Reg.OT");
     vecCab.add(INT_TBL_CANSOLSTOCK,"Can.Sol.Stk");
     vecCab.add(INT_TBL_CODCTAEGR,"Cód.Cta.Egr.");
     vecCab.add(INT_TBL_TXTCTAEGR,"Txt.Cta.Egr.");
     vecCab.add(INT_TBL_DESCTAEGR,"Des.Cta.Egr.");
     
     /*vecCab.add(INT_TBL_CANNUNRECFAC,"Can.Nun.Rec.");
     vecCab.add(INT_TBL_CANFAC,"Can.Fac");*/
     
     
     objTblMod=new ZafTblMod();
     objTblMod.setHeader(vecCab);
     tblDat.setModel(objTblMod); 
     
     tblDat.setRowSelectionAllowed(true);
     tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
     
    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
     tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
     TableColumnModel tcmAux=tblDat.getColumnModel(); 
  
     //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
     objMouMotAda=new ZafMouMotAda();
     tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
    
     objTblMod.setColumnDataType(INT_TBL_CANDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
     objTblMod.setColumnDataType(INT_TBL_PREDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
     objTblMod.setColumnDataType(INT_TBL_CANDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
     objTblMod.setColumnDataType(INT_TBL_SEDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
     objTblMod.setColumnDataType(INT_TBL_NODEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
     objTblMod.setColumnDataType(INT_TBL_CANSOLSTOCK, ZafTblMod.INT_COL_DBL, new Integer(0), null);
     
     ZafTblCelRenLbl objTblCelRenLbl=new ZafTblCelRenLbl();
     objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn(INT_TBL_CANVEN).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_PREDEV).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_CANDES).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_TOT).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_SEDEVPRV).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_NODEVPRV).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn(INT_TBL_CANSOLSTOCK).setCellRenderer(objTblCelRenLbl);
     
     objTblCelRenLbl=null;
         
      Vector vecAux=new Vector();
      //  vecAux.add("" + INT_TBL_PREDEV);
      //  vecAux.add("" + INT_TBL_CANDES);
      //  vecAux.add("" + INT_TBL_CANDEV);
      objTblMod.setColumnasEditables(vecAux);
      vecAux=null;
           
         /* Aqui se agrega las columnas que van 
                ha hacer ocultas 
             * */  
         ArrayList arlColHid=new ArrayList();
         arlColHid.add(""+INT_TBL_CODITM);
         arlColHid.add(""+INT_TBL_CODITMACT);
         arlColHid.add(""+INT_TBL_COSTO);
         arlColHid.add(""+INT_TBL_CODALT2);
         arlColHid.add(""+INT_TBL_ITMSER);
         arlColHid.add(""+INT_TBL_CANDIFDEV);
         arlColHid.add(""+INT_TBL_CODREGSOL);
         arlColHid.add(""+INT_TBL_CODLOC_OC);
         arlColHid.add(""+INT_TBL_CODTIP_OC);
         arlColHid.add(""+INT_TBL_CODDOC_OC);
         arlColHid.add(""+INT_TBL_CODREG_OC);
         arlColHid.add(""+INT_TBL_CODREGDEV);
         arlColHid.add(""+INT_TBL_CANDEV_ORI);
         arlColHid.add(""+INT_TBL_CANDEVACEBOD);
         arlColHid.add(""+INT_TBL_ACEINGSIS);
         arlColHid.add(""+INT_TBL_SUMTOTINGSIS);
         arlColHid.add(""+INT_TBL_SUMTOTBODACE);
         arlColHid.add(""+INT_TBL_CANDEVPREDES_SOL);
         arlColHid.add(""+INT_TBL_CANDEVPREDES_ORI);
         arlColHid.add(""+INT_TBL_SUMTOTCANDEV);
         arlColHid.add(""+INT_TBL_IEBODFIS);
         arlColHid.add(""+INT_TBL_DEVPRVAUT);
         arlColHid.add(""+INT_TBL_CODLOC_OT);
         arlColHid.add(""+INT_TBL_CODTIP_OT);
         arlColHid.add(""+INT_TBL_CODDOC_OT);
         arlColHid.add(""+INT_TBL_CODREG_OT);
         arlColHid.add(""+INT_TBL_NODEVPRV);
         arlColHid.add(""+INT_TBL_CODCTAEGR);
         arlColHid.add(""+INT_TBL_TXTCTAEGR);
         arlColHid.add(""+INT_TBL_DESCTAEGR);
         
         /*arlColHid.add(""+INT_TBL_CANNUNRECFAC);
         arlColHid.add(""+INT_TBL_CANFAC);*/
 
         objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
         arlColHid=null;  
         
         
          ocultaCol(INT_TBL_PREDEV) ; 
          ocultaCol(INT_TBL_CANDES) ; 
         
           ZafTblCelEdiTxt objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
           tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiTxt);
           objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intCelSel=tblDat.getSelectedRow();
                    double dlbTotRec=0.00;
                    double dlbCanAceOri=0.00;
                    double dlbCanAce=0.00;
                    double dlbCanIngSis=0.00;
                    
                        
                    dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV_ORI)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV_ORI).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV_ORI).toString()), 2);
                    dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 2);
                    dlbTotRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVACEBOD)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVACEBOD).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVACEBOD).toString()), 2);
        
                    dlbCanIngSis = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS).toString()), 2);
                    
                    if( dlbCanAce > dlbCanAceOri ){
                        if( (( ( dlbCanAce-dlbCanAceOri ))+dlbCanIngSis) > dlbTotRec  ){
                           MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad total recibida. ");
                           if(dlbCanAceOri>0)  
                             tblDat.setValueAt(""+dlbCanAceOri , intCelSel, INT_TBL_CANDEV);
                           else {
                               dlbCanAceOri = dlbTotRec-dlbCanIngSis;
                               tblDat.setValueAt(""+dlbCanAceOri , intCelSel, INT_TBL_CANDEV);
                            }
                    }}
                    calculaSubtotal();   
                  
                }   
            });
           objTblCelEdiTxt=null;             
           
           objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
           tcmAux.getColumn(INT_TBL_PREDEV).setCellEditor(objTblCelEdiTxt);
           objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intCelSel=tblDat.getSelectedRow();
                    double dlbTotRec=0.00;
                    double dlbCanAceOri=0.00;
                    double dlbCanAce=0.00;
                    double dlbCanIngSis=0.00;
                       
                    dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_ORI)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_ORI).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_ORI).toString()), 2);
                    dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString()), 2);
                    dlbTotRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_SOL)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_SOL).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_SOL).toString()), 2);
                    dlbCanIngSis = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS).toString()), 2);
                   
                    if( dlbCanAce > dlbCanAceOri ){
                        if( (( ( dlbCanAce-dlbCanAceOri ))+dlbCanIngSis) > dlbTotRec  ){
                           MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad total recibida. ");
                           if(dlbCanAceOri>0)  
                             tblDat.setValueAt(""+dlbCanAceOri , intCelSel, INT_TBL_PREDEV);
                           else {
                               dlbCanAceOri = dlbTotRec-dlbCanIngSis;
                               tblDat.setValueAt(""+dlbCanAceOri , intCelSel, INT_TBL_PREDEV);
                            }
                    }}
                    //calculaSubValores();   
                    calculaSubValoresBig();
                    
                }   
            });
           objTblCelEdiTxt=null; 
         
         
          objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
           tcmAux.getColumn(INT_TBL_CANDES).setCellEditor(objTblCelEdiTxt);
           objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                }                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                   int intCelSel=tblDat.getSelectedRow();
                    double dlbTotRec=0.00;
                    double dlbCanAceOri=0.00;
                    double dlbCanAce=0.00;
                    double dlbCanIngSis=0.00;
                       
                    dlbCanAceOri = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_ORI)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_ORI).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_ORI).toString()), 2);
                    dlbCanAce = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDES)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString()), 2);
                    dlbTotRec = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_SOL)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_SOL).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEVPREDES_SOL).toString()), 2);
                    dlbCanIngSis = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS)==null?"0":tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_ACEINGSIS).toString()), 2);
                   
                    if( dlbCanAce > dlbCanAceOri ){
                        if( (( ( dlbCanAce-dlbCanAceOri ))+dlbCanIngSis) > dlbTotRec  ){
                           MensajeInf("Error! La cantidad a Aceptada a sobrepasado la cantidad total recibida. ");
                           if(dlbCanAceOri>0)  
                             tblDat.setValueAt(""+dlbCanAceOri , intCelSel, INT_TBL_CANDES);
                           else {
                               dlbCanAceOri = dlbTotRec-dlbCanIngSis;
                               tblDat.setValueAt(""+dlbCanAceOri , intCelSel, INT_TBL_CANDES);
                            }
                    }}
                    //calculaDescuento();   
                    calculaDescuentoBig();
                }   
            });
           objTblCelEdiTxt=null; 
         
         
    //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(160);
        tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(15);
        tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_CANVEN).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_PREDEV).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_CANDES).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_IVA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_TOT).setPreferredWidth(70);
       

       
        ZafTblCelRenChk objTblCelRenChk;
        ZafTblCelEdiChk objTblCelEdiChk; 
        objTblCelRenChk = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_IVA).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_IVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
            @Override
            public void beforeEdit(ZafTableEvent evt){
            }        
            @Override
            public void afterEdit(ZafTableEvent evt) {
            }
           });
        objTblCelRenChk=null;
        objTblCelEdiChk=null; 
        
        
     tcmAux=null;
     setEditable(true); 
     blnRes=true;
      
      }catch (Exception e){
          blnRes=false;
          objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
      }
    
   public void Configura_ventana_consulta(){
        configurarVenConTipDoc();
        configurarVenConSolicitante();
       
        configurarTabla();

        if(blnEstCarOC){

           cargarDatos( strCodEmpOC, strCodLocOC, strCodTipOC, strCodDocOC  );

        }

    }


private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc ){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){

       cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
       cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );

      conn.close();
      conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

/*
 * MODIFICADO EFLORESA 2012-04-17
 * DESCUENTO ESPECIAL
 */
private boolean cargarCabReg(Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
    boolean blnRes=false;
    Statement stmLoc;
    ResultSet rstDatSol;
    String strSql="";
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();

   strSql="SELECT dev.tx_descor as descordeven, dev.tx_deslar as deslardevven, a.ne_numcot,  d.co_locrel, d.co_tipdocrel, d.co_docrel, d.st_volfacmersindev " +
   "  ,a.co_locrelsoldevven, a.co_tipdocrelsoldevven, a.co_docrelsoldevven  " +
   " ,a1.tx_nom as tx_desloc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, d.co_usrsol, d.tx_nomusrsol, a.fe_doc, d.fe_doc as fe_docsol, d.ne_numdoc, d.co_doc  "+
   " ,a.st_tipdev, a.st_reg, a.co_com, a.tx_nomven, a.tx_ate, a.tx_dircli, a.tx_ciucli, a.tx_telcli, a.tx_ruc, a.tx_obs1, a.tx_obs2  "+
   " ,a.nd_sub, a.nd_valiva, a.nd_tot, a.nd_poriva, c.st_ivaven, a.co_loc, a.co_doc as co_docDev, a.ne_numdoc as ne_numdocDev "+
   " ,a.co_motDev, motdev.tx_deslar as tx_desLarMotDev "+           
   " FROM tbm_cabmovinv AS a " +
   " LEFT JOIN  tbm_cabsoldevven AS d ON(d.co_emp=a.co_emp AND d.co_loc=a.co_locrelsoldevven AND d.co_tipdoc=a.co_tipdocrelsoldevven AND d.co_doc=a.co_docrelsoldevven ) "+
   " LEFT JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrelsoldevven) " +
   " LEFT JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_locrelsoldevven AND a2.co_tipdoc=a.co_tipdocrelsoldevven) " +
   " LEFT JOIN tbm_cabtipdoc AS dev ON(dev.co_emp=a.co_emp AND dev.co_loc=a.co_loc AND dev.co_tipdoc=a.co_tipdoc) " +
   " INNER JOIN tbm_cli AS c ON (c.co_emp=a.co_emp AND c.co_cli=a.co_cli) "+
   " LEFT JOIN tbm_motdevinv AS motdev ON (motdev.co_emp=a.co_emp AND motdev.co_motdev=a.co_motdev) "+
   " WHERE a.co_emp="+strCodEmp+" AND a.co_loc="+strCodLoc+"  AND " +
   " a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
   rstDatSol=stmLoc.executeQuery(strSql);
   if(rstDatSol.next()){
     strAux=rstDatSol.getString("st_reg");

       txtDesCorTipDoc.setText(rstDatSol.getString("descordeven"));
       txtDesLarTipDoc.setText(rstDatSol.getString("deslardevven"));

       txtCodLoc.setText(rstDatSol.getString("co_locrelsoldevven"));
       txtNomLoc.setText(rstDatSol.getString("tx_desloc"));
       txtCodTipDocSol.setText(rstDatSol.getString("co_tipdocrelsoldevven"));
       txtDesCorTipDocSol.setText(rstDatSol.getString("tx_descor"));
       txtDesLarTipDocSol.setText(rstDatSol.getString("tx_deslar"));
       txtCodCli.setText(rstDatSol.getString("co_cli"));
       txtNomCli.setText(rstDatSol.getString("tx_nomcli"));
       txtCodSol.setText(rstDatSol.getString("co_usrsol"));
       txtDesSol.setText(rstDatSol.getString("tx_nomusrsol"));
       txtFecDocSol.setText(rstDatSol.getString("fe_docsol"));
       txtCodDocSol.setText(rstDatSol.getString("co_doc"));
       txtNumDocSol.setText(rstDatSol.getString("ne_numdoc"));
       txtCodComFac.setText( rstDatSol.getString("co_com"));
       txtNomVenFac.setText( rstDatSol.getString("tx_nomven"));
       txtAteFac.setText( rstDatSol.getString("tx_ate"));
       txtDirCliFac.setText( rstDatSol.getString("tx_dircli"));
       txtCiuFac.setText( rstDatSol.getString("tx_ciucli"));
       txtTelCliFac.setText( rstDatSol.getString("tx_telcli"));
       txtRucFac.setText( rstDatSol.getString("tx_ruc"));

       txtNumDocFac.setText( rstDatSol.getString("ne_numcot"));

       txtCodMot.setText(rstDatSol.getString("co_motdev"));
       txtDesLarMot.setText(rstDatSol.getString("tx_deslarmotdev"));
       
       chkVolFac.setSelected( ( rstDatSol.getString("st_volfacmersindev")==null?false:(rstDatSol.getString("st_volfacmersindev").equals("S")?true:false)) );

       if(rstDatSol.getString("st_tipdev").equals("C")){
           radCan.setSelected(true);
           ocultaCol(INT_TBL_PREDEV) ;
           ocultaCol(INT_TBL_CANDES) ;
           MostrarCol(INT_TBL_CANDEV) ;
       }
       if(rstDatSol.getString("st_tipdev").equals("P")){
           radDes.setSelected(true);
           ocultaCol(INT_TBL_PREDEV) ;
           MostrarCol(INT_TBL_CANDES) ;
           ocultaCol(INT_TBL_CANDEV) ;
       }
       if(rstDatSol.getString("st_tipdev").equals("V")){
           radPre.setSelected(true);
           MostrarCol(INT_TBL_PREDEV) ;
           ocultaCol(INT_TBL_CANDES) ;
           ocultaCol(INT_TBL_CANDEV) ;
       }

       if(rstDatSol.getString("st_tipdev").equals("E")){
           radDesEsp.setSelected(true);
           ocultaCol(INT_TBL_PREDEV) ;
           MostrarCol(INT_TBL_CANDES) ;
           ocultaCol(INT_TBL_CANDEV) ;
       }

       txtObs1.setText(rstDatSol.getString("tx_obs1"));
       txtObs2.setText(rstDatSol.getString("tx_obs2"));
       txtCodDoc.setText(rstDatSol.getString("co_docDev"));
       txtNumDoc.setText(rstDatSol.getString("ne_numdocDev"));

       txtSub.setText(""+ objUti.redondear( rstDatSol.getString("nd_sub") , 2) );
       txtIva.setText(""+ objUti.redondear( rstDatSol.getString("nd_valiva") , 2) );
       txtTot.setText(""+ objUti.redondear( rstDatSol.getString("nd_tot") , 2) );

       dblPorIva=rstDatSol.getDouble("nd_poriva");
       //lblIva.setText("IVA "+dblPorIva+" %");
       stIvaVen = rstDatSol.getString("st_ivaven");

        Date dateObj = rstDatSol.getDate("fe_doc");
        if(dateObj==null){
          txtFecDoc.setText("");
        }else{
            Calendar calObj = Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH)+1, calObj.get(Calendar.YEAR)        );
        }

        txtCodLocRelFac.setText(rstDatSol.getString("co_locrel"));
        txtCodTipDocRelFac.setText(rstDatSol.getString("co_tipdocrel"));
        txtCodDocRelFac.setText(rstDatSol.getString("co_docrel"));

  }
 rstDatSol.close();
 rstDatSol=null;


   objAsiDia.consultarDiario( Integer.parseInt( strCodEmp ), Integer.parseInt( strCodLoc ), Integer.parseInt( strCodTipDoc ), Integer.parseInt( strCodDoc ) );

    if (strAux.equals("A"))
        strAux="Activo";
     else if (strAux.equals("I"))
         strAux="Anulado";
      else if (strAux.equals("E"))
         strAux="Eliminado";
      else
          strAux="Otro";
     objTooBar.setEstadoRegistro(strAux);

}}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
 catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

private boolean cargarDetReg(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    Vector vecData;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();

   vecData = new Vector();

    String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(a.tx_codalt), length(a.tx_codalt) ,1)) IN ( " +
    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
    " THEN 'S' ELSE 'N' END AS proconf  ";


   strSql="SELECT a2.co_locrel, a2.co_tipdocrel ,a2.co_Docrel, a2.co_regrel," +
   "  x.co_locrel as colocot, x.co_tipdocrel as cotipdocot ,x.co_Docrel as codocot, x.co_regrel as coregot, " +
   " a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a3.nd_candevprv, a3.nd_cannodevprv, a3.nd_cansolnodevprv  " +

   "  ,case when a3.nd_canRevBodAce >=  a3.nd_candevprv then a3.nd_candevprv  else  a3.nd_canRevBodAce end  as nd_candevprv_1 "+
   "  , case when a3.nd_cansolnodevprv > 0 then  a3.nd_canRevBodAce - ( case when a3.nd_canRevBodAce >=  a3.nd_candevprv then a3.nd_candevprv  else  a3.nd_canRevBodAce end ) " +
   " else  0  end  as nd_cansolnodevprv_1  "+

   " ,a.co_bod ,a1.tx_nom, a.nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
   " ,b.st_ser, a.co_reg, a.co_regrelsoldevven,  a.nd_tot ,a3.nd_canRevBodAce, a3.nd_CanAceIngSis, a3.nd_candev, a3.st_devAutMerPrv  "+
   " ,( SELECT sum(s.nd_CanAceIngSis) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotIngSis "+
   " ,( SELECT sum(s.nd_canRevBodAce) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotBodAce "+
   " ,( SELECT sum(s.nd_candev) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotCanDev "+
   " "+strAux2+" "+
   " FROM tbm_detmovinv AS a " +
   " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +
   " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+
   "  LEFT JOIN tbr_detmovinv  AS a2 ON(a2.co_emp=a.co_emp and a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc and a2.co_reg=a.co_reg and a2.co_tipdocrel= 4  )   "+ // tipdoc_rel  = 4  se quema codigo 4 que es devoluciones de compra 
   "  LEFT JOIN tbr_detmovinv  AS x ON(x.co_emp=a.co_emp and x.co_loc=a.co_loc AND x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_reg=a.co_reg and x.co_tipdocrel=116)    "+ /* "OTAUTO";"Ordenes de trabajo automáticas" */
   "  LEFT JOIN tbm_detsoldevven  AS a3 ON(a3.co_emp=a.co_emp and a3.co_loc=a.co_locrelsoldevven AND a3.co_tipdoc=a.co_tipdocrelsoldevven and a3.co_doc=a.co_docrelsoldevven and a3.co_reg=a.co_regrelsoldevven ) "+
   " WHERE a.co_emp="+strCodEmp+" AND a.co_loc="+strCodLoc+"  AND " +
   " a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
   rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
     java.util.Vector vecReg = new java.util.Vector();

          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
          vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
          vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed") );
          vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod") );
          vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
          vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_canorg") );
          vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_can") );
          vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni") );

            vecReg.add(INT_TBL_PREDEV, rstLoc.getString("nd_can") );
          vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes") );
            vecReg.add(INT_TBL_CANDES, rstLoc.getString("nd_can") );



          vecReg.add(INT_TBL_IVA, (rstLoc.getString("st_ivacom").equals("S")?true:false));
          vecReg.add(INT_TBL_TOT, rstLoc.getString("nd_tot") );
          vecReg.add(INT_TBL_CODITMACT, rstLoc.getString("co_itmact") );
          vecReg.add(INT_TBL_COSTO, rstLoc.getString("nd_cosunigrp") );
          vecReg.add(INT_TBL_CODALT2, rstLoc.getString("tx_codalt2") );
          vecReg.add(INT_TBL_ITMSER, rstLoc.getString("st_ser") );
          vecReg.add(INT_TBL_CANDIFDEV, "" );
          vecReg.add(INT_TBL_CODREGSOL, rstLoc.getString("co_regrelsoldevven") );
          vecReg.add(INT_TBL_CODLOC_OC, rstLoc.getString("co_locrel") );
          vecReg.add(INT_TBL_CODTIP_OC, rstLoc.getString("co_tipdocrel") );
          vecReg.add(INT_TBL_CODDOC_OC, rstLoc.getString("co_Docrel") );
          vecReg.add(INT_TBL_CODREG_OC, rstLoc.getString("co_regrel") );
          vecReg.add(INT_TBL_CODREGDEV, rstLoc.getString("co_reg") );
          vecReg.add(INT_TBL_CANDEV_ORI, rstLoc.getString("nd_can") );
          vecReg.add(INT_TBL_CANDEVACEBOD, rstLoc.getString("nd_canRevBodAce") );
          vecReg.add(INT_TBL_ACEINGSIS, rstLoc.getString("nd_CanAceIngSis") );
          vecReg.add(INT_TBL_SUMTOTINGSIS, rstLoc.getString("sumTotIngSis") );
          vecReg.add(INT_TBL_SUMTOTBODACE, rstLoc.getString("sumTotBodAce") );
          vecReg.add(INT_TBL_CANDEVPREDES_SOL, rstLoc.getString("nd_candev") );
          vecReg.add(INT_TBL_CANDEVPREDES_ORI, rstLoc.getString("nd_can") );
          vecReg.add(INT_TBL_SUMTOTCANDEV, rstLoc.getString("sumTotCanDev") );
          vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));
          vecReg.add(INT_TBL_DEVPRVAUT, rstLoc.getString("st_devAutMerPrv"));
          vecReg.add(INT_TBL_SEDEVPRV, rstLoc.getString("nd_candevprv_1"));   // nd_candevprv_1
          vecReg.add(INT_TBL_NODEVPRV, rstLoc.getString("nd_cannodevprv"));

          vecReg.add(INT_TBL_CODLOC_OT, rstLoc.getString("colocot") );
          vecReg.add(INT_TBL_CODTIP_OT, rstLoc.getString("cotipdocot") );
          vecReg.add(INT_TBL_CODDOC_OT, rstLoc.getString("codocot") );
          vecReg.add(INT_TBL_CODREG_OT, rstLoc.getString("coregot") );
          vecReg.add(INT_TBL_CANSOLSTOCK, rstLoc.getString("nd_cansolnodevprv_1") );  // nd_cansolnodevprv_1

          vecReg.add(INT_TBL_CODCTAEGR, null );
          vecReg.add(INT_TBL_TXTCTAEGR, null );
          vecReg.add(INT_TBL_DESCTAEGR, null );
          /*vecReg.add(INT_TBL_CANNUNRECFAC,null);
          vecReg.add(INT_TBL_CANFAC,null);*/

         vecData.add(vecReg);
   }
     objTblMod.setData(vecData);
     tblDat .setModel(objTblMod);
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;


    if (strAux.equals("A"))
        strAux="Activo";
     else if (strAux.equals("I"))
         strAux="Anulado";
      else if (strAux.equals("E"))
         strAux="Eliminado";
      else
          strAux="Otro";
     objTooBar.setEstadoRegistro(strAux);


}}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
 catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}




   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
   
   
     private void MostrarCol(int intCol){ 
              tblDat.getColumnModel().getColumn(intCol).setWidth(50);
              tblDat.getColumnModel().getColumn(intCol).setMaxWidth(50);
              tblDat.getColumnModel().getColumn(intCol).setMinWidth(50);
              tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(50);
              tblDat.getColumnModel().getColumn(intCol).setResizable(false); 
     }
    
     private void ocultaCol(int intCol){
              tblDat.getColumnModel().getColumn(intCol).setWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
              tblDat.getColumnModel().getColumn(intCol).setResizable(false); 
     }
   

   
 private void CalculaAsientoDiario(){
    
     dblTotalDoc = objUti.redondear(txtTot.getText(),4);
      
     if(radCan.isSelected()){
         //calculaSubtotal();
         calculaSubtotalBig();
         generaAsiento();
     }
     
     
     if(radDes.isSelected() || radDesEsp.isSelected()){
          //calculaDescuento();
          calculaDescuentoBig();
         generaAsientoPorDescuento();
     }
     
     if(radPre.isSelected()){
         //calculaSubValores();
         calculaSubValoresBig();
         generaAsiento();
     }
 }
 
 
    private void CalculaAsientoDiarioEcuaCoseDeto(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){

         dblTotalDoc = objUti.redondear(txtTot.getText(),4);


         if(radCan.isSelected()){
             //calculaSubtotal();
             calculaSubtotalBig();
             //generaAsiento();
             generaAsientoDevEcuaCosDeto(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
         }


         if(radDes.isSelected() || radDesEsp.isSelected()){
              //calculaDescuento();
              calculaDescuentoBig();
             //generaAsientoPorDescuento();
         }

         if(radPre.isSelected()){
             //calculaSubValores();
             calculaSubValoresBig();
             //generaAsiento();
         }
     } 

   

   
   
   
   
public double getTotalDescuento(){
double dblTotalDescuento=0;
 try{   
 for(int filaActual=0; filaActual< tblDat.getRowCount(); filaActual++){
   if(tblDat.getValueAt(filaActual, INT_TBL_CODITM)!=null){

         if(tblDat.getValueAt(filaActual, INT_TBL_CANDEV ) != null){
           if(!tblDat.getValueAt(filaActual, INT_TBL_CANDEV ).equals("")){
               if( Double.parseDouble(tblDat.getValueAt(filaActual, INT_TBL_CANDEV).toString()) > 0.00 ){

       double dblCan    =  objUti.redondear( ((tblDat.getValueAt(filaActual, INT_TBL_CANDEV)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_CANDEV).toString())) ,6), 
              dblPre    =  objUti.redondear( ((tblDat.getValueAt(filaActual, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PREUNI).toString())) ,6),
              dblPorDes =  objUti.redondear( ((tblDat.getValueAt(filaActual, INT_TBL_PORDES )==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PORDES).toString())),2),
              dblValDes =  objUti.redondear(  ((dblCan * dblPre)==0)?0:(objUti.redondear((dblCan * dblPre)* dblPorDes, 6)  / 100),6);
                                
       dblTotalDescuento =  dblTotalDescuento + dblValDes;      

   }}}}}  

 dblTotalDescuento =  objUti.redondear(objUti.redondear(dblTotalDescuento,4),2);  
 } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }      
 return dblTotalDescuento;
}

    public double getTotalItemOtros() {
        
        double dblSub = 0, dblTotalItemOtros = 0;
        double subtot = 0.00;
        strarritmotr = new String[tblDat.getRowCount()][4];
        for (int filaActual=0; filaActual< tblDat.getRowCount(); filaActual++) {
            if ((tblDat.getValueAt(filaActual, INT_TBL_CODITM)!=null) && (tblDat.getValueAt(filaActual, INT_TBL_ITMSER).equals("O")) && (tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR)!=null)) {
                dblSub = ((tblDat.getValueAt(filaActual, INT_TBL_TOT)==null||tblDat.getValueAt(filaActual, INT_TBL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(filaActual, INT_TBL_TOT).toString()));
                dblTotalItemOtros = dblTotalItemOtros + dblSub;
                
                for (int x=0; x<strarritmotr.length; x++){
                    if ( strarritmotr[x][0] != null ) {
                        if ( strarritmotr[x][0].equals(tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR))) {
                            subtot = dblSub + Double.parseDouble(strarritmotr[x][1]);
                            strarritmotr[x][1] = Double.toString(subtot);
                            break;                            
                        }
                    }else{                            
                        strarritmotr[x][0] = tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR).toString();
                        subtot = dblSub;
                        strarritmotr[x][1] = Double.toString(subtot);
                        strarritmotr[x][2] = tblDat.getValueAt(filaActual, INT_TBL_TXTCTAEGR).toString();
                        strarritmotr[x][3] = tblDat.getValueAt(filaActual, INT_TBL_DESCTAEGR).toString();
                        break;                            
                    }
                }
                
            }
        }
        return dblTotalItemOtros;
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
     

    private List cargarAsientoFacEcuDetoCos(Connection con,int intCodEmp, int intCodLoc,int intCodTipDoc, int intCodDoc){
        List lisAsiento=null;
        List lisReg=null;
        Statement stmAsi=null;
        ResultSet rsAsi=null;
        String strSQL=" select p.co_cta,p.tx_codcta,p.tx_deslar, d.nd_mondeb, d.nd_monhab \n" +
                      " from tbm_detdia as d\n" +
                      " inner join tbm_placta as p\n" +
                      " on(d.co_emp=p.co_emp and d.co_cta=p.co_cta )\n" +
                      " where d.co_emp="+intCodEmp+" and d.co_loc="+intCodLoc+" and d.co_tipdoc="+intCodTipDoc+" and d.co_dia="+intCodDoc;
        try{
            stmAsi=con.createStatement();
            rsAsi=stmAsi.executeQuery(strSQL); 
            lisAsiento=new ArrayList();
            while(rsAsi.next()){
                lisReg=new ArrayList();
                lisReg.add(rsAsi.getInt("co_cta"));
                lisReg.add(rsAsi.getString("tx_codcta"));
                lisReg.add(rsAsi.getString("tx_deslar"));
                lisReg.add(rsAsi.getBigDecimal("nd_mondeb"));
                lisReg.add(rsAsi.getBigDecimal("nd_monhab"));
                lisAsiento.add(lisReg);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return lisAsiento;
    }
      
    public void generaAsientoDevEcuaCosDeto(Connection conn,int intCodEmpFac, int intCodLocFac, int intCodTipDocFac, int intCodDocFac){
        int intCta;
        String strCodCtaLar="", strNomCta="";
        BigDecimal bgdMonDeb=BigDecimal.ZERO, bgdMonHab=BigDecimal.ZERO;
        java.util.Vector vecRowAsientoDeb, vecRowAsientoHab;
        java.util.Vector vecAsiento = new java.util.Vector();         
        try{
            
            List lisCtasFac=cargarAsientoFacEcuDetoCos(conn,intCodEmpFac,intCodLocFac,intCodTipDocFac,intCodDocFac);
            Iterator itCtas=lisCtasFac.iterator();
           
            while(itCtas.hasNext()){
                List lstReg=(List)itCtas.next();
                intCta=(Integer)lstReg.get(0);
                strCodCtaLar=(String)lstReg.get(1);
                strNomCta=(String)lstReg.get(2);
                bgdMonDeb=(BigDecimal)lstReg.get(3);
                bgdMonHab=(BigDecimal)lstReg.get(4);
                if(bgdMonDeb.compareTo(bgdMonHab)>0){
                    vecRowAsientoHab = vecFila2(intCta,strCodCtaLar,strNomCta);
                    vecRowAsientoHab.setElementAt(bgdMonDeb, 6);
                    vecAsiento.add(vecRowAsientoHab);
                }else{
                    vecRowAsientoDeb = vecFila2(intCta,strCodCtaLar,strNomCta);
                    vecRowAsientoDeb.setElementAt(bgdMonHab, 5);
                    vecAsiento.add(vecRowAsientoDeb);
                }
                
            }
            objAsiDia.setDetalleDiario(vecAsiento);  
        } catch(Exception Evt) { 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }  
            
    }


     
public void generaAsientoDev(){
 try{
   double dblIvaCot=0,dblSubtotalCot=0,dblTotalCot=0;
   dblTotalCot = Double.parseDouble(txtTot.getText());
   dblSubtotalCot = Double.parseDouble(txtSub.getText());
   dblIvaCot = Double.parseDouble(txtIva.getText());

   objAsiDia.inicializar();
   java.util.Vector vecAsiento = new java.util.Vector();         

   double dblValTotDes =  getTotalDescuento();
           
	if(dblTotalCot>0){
            java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb());
            vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblTotalCot,6)), 5);
            vecAsiento.add(vecRowAsientoDeb);
        }
        /*
         * Agregando la cuenta de descuento
         */
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
            vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblValTotDes+objUti.redondear(dblSubtotalCot,6),6)), 6);
            vecAsiento.add(vecRowAsientoHab);
        }
        /*
         * Agregando la cuenta Iva en Ventas
         */
        if( dblIvaCot > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen());
            vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblIvaCot,6)), 6);
            vecAsiento.add(vecRowAsientoHab);
        }
	
        objAsiDia.setDetalleDiario(vecAsiento);  
       } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }       
    }
     
       
    public void generaAsiento()
    {
        try
        {
            double dblIvaCot=0,dblSubtotalCot=0,dblTotalCot=0;
            dblTotalCot = Double.parseDouble(txtTot.getText());
            dblSubtotalCot = Double.parseDouble(txtSub.getText());
            dblIvaCot = Double.parseDouble(txtIva.getText());
            int intCodCta=0;
            String strCodCta="", strNomCta="";

            objAsiDia.inicializar();
            java.util.Vector vecAsiento = new java.util.Vector();         

            double dblValTotOtr = getTotalItemOtros();
            double dblValTotDes =  getTotalDescuento();

            for (int x=0; x<strarritmotr.length; x++) 
            {
                if ( strarritmotr[x][0] != null ) 
                {
                    java.util.Vector vecRowAsientoDeb = vecFila2(Integer.parseInt(strarritmotr[x][0]),strarritmotr[x][2],strarritmotr[x][3]); 
                    vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(strarritmotr[x][1],2)), 5);
                    vecAsiento.add(vecRowAsientoDeb);
                }
            }

            //if((dblValTotDes+dblSubtotalCot-dblValTotOtr)>0)
            if((dblValTotDes+dblSubtotalCot-dblValTotOtr)>0 && (txtSubTotIvaGra2.getText()!=null && Double.parseDouble(txtSubTotIvaGra2.getText())>0 ))
            { 
                // if(dblTotalCot>0){
                java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb()); 
                //vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblValTotDes+dblSubtotalCot-dblValTotOtr,2)), 5);
                vecRowAsientoDeb.setElementAt(new Double(txtSubTotIvaGra2.getText()), 5);
                vecAsiento.add(vecRowAsientoDeb);
            }
            

            /*AGREGADO POR COMPENSACION SOLIDARIA*/
            if(intCodEmpDevVen==1){
                intCodCta=4680;
                strCodCta="4.01.01.04";
                strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
            }else if(intCodEmpDevVen==2){
                intCodCta=2303;
                strCodCta="4.01.01.04";
                strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
            }else if(intCodEmpDevVen==4){
                intCodCta=3210;
                strCodCta="4.01.01.04";
                strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
            }

            
            /* Agregando la cuenta Iva en Ventas */
            /*if( dblValTotDes > 0)
            { 
                //if( dblIvaCot > 0){
                java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes()); 
                vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblValTotDes,2)), 6);
                vecAsiento.add(vecRowAsientoHab);
            } */
            
            //java.util.Vector vecRowAsientoHab = vecFila2(intCodCta,strCodCta,strNomCta); 
            if((txtSubTotIvaCero2.getText()==null?0:new Double(objUti.redondear(txtSubTotIvaCero2.getText(),2)))>0){
                java.util.Vector vecRowAsientoDebIvaCero = vecFila2(intCodCta,strCodCta,strNomCta); 
                vecRowAsientoDebIvaCero.setElementAt(new Double(objUti.redondear(txtSubTotIvaCero2.getText(),2)), 5);
                vecAsiento.add(vecRowAsientoDebIvaCero);            
            }
            /* Agregando la cuenta de descuento  */
            if( dblIvaCot > 0)
            { 
                // if( dblValTotDes > 0){
                int intCtaIva=objParSis.getCodigoCuentaContableIvaVentas(intCodEmpDevVen, intCodLocDevVen, objUti.parseDate(strFecFac,"yyyy-MM-dd"));
                //java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen()); 
                java.util.Vector vecRowAsientoDeb = vecFila2(intCtaIva,objCtaCta_Dat.obtenerNumCta(intCodEmpDevVen, intCtaIva),objCtaCta_Dat.obtenerNomCta(intCodEmpDevVen, intCtaIva));
                vecRowAsientoDeb.setElementAt(new Double(dblIvaCot), 5);
                vecAsiento.add(vecRowAsientoDeb);
            }

            /* Agregando la cuenta de Ventas */
            if( dblTotalDoc > 0)
            { 
                //if( (dblValTotDes+dblSubtotalCot) > 0){
                //java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab()); 
                java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab()); 
                /*AGREGADO POR COMPENSACION SOLIDARIA*/
                //vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblTotalCot,2)), 6);
                //vecRowAsientoHab.setElementAt(new Double(objUti.redondear(txtTotSinCom.getText(),2)), 6);
                vecRowAsientoHab.setElementAt(new Double(objUti.redondear(txtTot.getText(),2)), 6);
                /*AGREGADO POR COMPENSACION SOLIDARIA*/
                vecAsiento.add(vecRowAsientoHab);
            }
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/
//            if(intCodEmpDevVen==1){
//                intCodCta=4680;
//                strCodCta="4.01.01.04";
//                strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
//            }else if(intCodEmpDevVen==2){
//                intCodCta=2303;
//                strCodCta="4.01.01.04";
//                strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
//            }else if(intCodEmpDevVen==4){
//                intCodCta=3210;
//                strCodCta="4.01.01.04";
//                strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
//            }
//
//            
//            /* Agregando la cuenta Iva en Ventas */
//            /*if( dblValTotDes > 0)
//            { 
//                //if( dblIvaCot > 0){
//                java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes()); 
//                vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblValTotDes,2)), 6);
//                vecAsiento.add(vecRowAsientoHab);
//            } */
//            
//            //java.util.Vector vecRowAsientoHab = vecFila2(intCodCta,strCodCta,strNomCta); 
//            java.util.Vector vecRowAsientoDeb = vecFila2(intCodCta,strCodCta,strNomCta); 
//            vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(txtSubTotIvaCero2.getText(),2)), 6);
//            vecAsiento.add(vecRowAsientoDeb);
            
/*
                java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb()); 
                //vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblValTotDes+dblSubtotalCot-dblValTotOtr,2)), 5);
                vecRowAsientoDeb.setElementAt(new Double(txtSubTotIvaGra2.getText()), 5);
                vecAsiento.add(vecRowAsientoDeb);
            
            */            
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/
            
            objAsiDia.setDetalleDiario(vecAsiento);     
        }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }       
    }
     
    
    public void generaAsientoPorDescuento()
    {
        try
        {
            double dblIvaCot=0,dblSubtotalCot=0,dblTotalCot=0;
            dblTotalCot    = Double.parseDouble(txtTot.getText());
            dblSubtotalCot = Double.parseDouble(txtSub.getText());
            dblIvaCot      = Double.parseDouble(txtIva.getText());

            objAsiDia.inicializar();
            java.util.Vector vecAsiento = new java.util.Vector();         

            ///  double dblValTotDes =  getTotalDescuento();

            if(dblSubtotalCot > 0)
            {   
                //// if((dblValTotDes+dblSubtotalCot)>0){ 
                java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb()); 
                vecRowAsientoDeb.setElementAt(new Double(objUti.redondear(dblSubtotalCot,2)), 5);
                vecAsiento.add(vecRowAsientoDeb);
            }

            /* Agregando la cuenta de descuento  */
            if( dblIvaCot > 0)
            { 
                int intCtaIva=objParSis.getCodigoCuentaContableIvaVentas(intCodEmpDevVen, intCodLocDevVen, objUti.parseDate(strFecFac,"yyyy-MM-dd"));
                //java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen()); 
	 	java.util.Vector vecRowAsientoDeb = vecFila2(intCtaIva,objCtaCta_Dat.obtenerNumCta(intCodEmpDevVen, intCtaIva),objCtaCta_Dat.obtenerNomCta(intCodEmpDevVen, intCtaIva));
                vecRowAsientoDeb.setElementAt(new Double(dblIvaCot), 5);
                vecAsiento.add(vecRowAsientoDeb);
            }

            /* Agregando la cuenta de Ventas */
            if( dblTotalDoc > 0)
            {
                java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab()); 
                vecRowAsientoHab.setElementAt(new Double(objUti.redondear(dblTotalCot,2)), 6);
                vecAsiento.add(vecRowAsientoHab);
            }
         /*
          * Agregando la cuenta Iva en Ventas
          */
         objAsiDia.setDetalleDiario(vecAsiento);    
      } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }        
    }
     
      
      
   
   
   
   
   
   
   
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jFrame1 = new javax.swing.JFrame();
      buttonGroup1 = new javax.swing.ButtonGroup();
      panCen = new javax.swing.JPanel();
      tabGen = new javax.swing.JTabbedPane();
      panTabGen = new javax.swing.JPanel();
      PanTabGen = new javax.swing.JPanel();
      lblTipDoc = new javax.swing.JLabel();
      lblCodDoc = new javax.swing.JLabel();
      txtCodDoc = new javax.swing.JTextField();
      txtDesLarTipDoc = new javax.swing.JTextField();
      butBusTipDoc = new javax.swing.JButton();
      lblFecDoc = new javax.swing.JLabel();
      txtDesCorTipDoc = new javax.swing.JTextField();
      txtNumDoc = new javax.swing.JTextField();
      lblNumDoc = new javax.swing.JLabel();
      lblNumFac = new javax.swing.JLabel();
      txtNumDocFac = new javax.swing.JTextField();
      butFacEle = new javax.swing.JButton();
      butGetXmlDevVta = new javax.swing.JButton();
      panObs = new javax.swing.JPanel();
      panLbl = new javax.swing.JPanel();
      lblObs1 = new javax.swing.JLabel();
      lblObs2 = new javax.swing.JLabel();
      panTxa = new javax.swing.JPanel();
      spnObs3 = new javax.swing.JScrollPane();
      txtObs1 = new javax.swing.JTextArea();
      spnObs4 = new javax.swing.JScrollPane();
      txtObs2 = new javax.swing.JTextArea();
      panSolDev = new javax.swing.JPanel();
      panGenDatSolDev = new javax.swing.JPanel();
      panTblSolDev = new javax.swing.JPanel();
      srcTblSolDev = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      panDatSolDev = new javax.swing.JPanel();
      lblLoc = new javax.swing.JLabel();
      lblTipdoc = new javax.swing.JLabel();
      lblCli = new javax.swing.JLabel();
      lblSol = new javax.swing.JLabel();
      txtCodLoc = new javax.swing.JTextField();
      txtNomLoc = new javax.swing.JTextField();
      txtDesCorTipDocSol = new javax.swing.JTextField();
      txtCodCli = new javax.swing.JTextField();
      txtCodSol = new javax.swing.JTextField();
      txtDesLarTipDocSol = new javax.swing.JTextField();
      txtNomCli = new javax.swing.JTextField();
      txtDesSol = new javax.swing.JTextField();
      butBusNumDocSol = new javax.swing.JButton();
      lblFecDto = new javax.swing.JLabel();
      lblCodDto = new javax.swing.JLabel();
      txtFecDocSol = new javax.swing.JTextField();
      txtCodDocSol = new javax.swing.JTextField();
      txtNumDocSol = new javax.swing.JTextField();
      lblNumDto = new javax.swing.JLabel();
      radCan = new javax.swing.JRadioButton();
      radDes = new javax.swing.JRadioButton();
      radPre = new javax.swing.JRadioButton();
      radDesEsp = new javax.swing.JRadioButton();
      jLabel2 = new javax.swing.JLabel();
      chkVolFac = new javax.swing.JCheckBox();
      lblMotDev = new javax.swing.JLabel();
      txtCodMot = new javax.swing.JTextField();
      txtDesLarMot = new javax.swing.JTextField();
      panCotGenSur = new javax.swing.JPanel();
      panCotGenSurCen = new javax.swing.JPanel();
      jPanel5 = new javax.swing.JPanel();
      jPanel4 = new javax.swing.JPanel();
      lblObs3 = new javax.swing.JLabel();
      spnObs1 = new javax.swing.JScrollPane();
      txaObs1 = new javax.swing.JTextArea();
      jPanel3 = new javax.swing.JPanel();
      lblObs4 = new javax.swing.JLabel();
      spnObs2 = new javax.swing.JScrollPane();
      txaObs2 = new javax.swing.JTextArea();
      panCotGenSurEst = new javax.swing.JPanel();
      lblSubtotIvaCero = new javax.swing.JLabel();
      txtSubTotIvaCero2 = new javax.swing.JTextField();
      lbSubTotIvaGra = new javax.swing.JLabel();
      txtSubTotIvaGra2 = new javax.swing.JTextField();
      lblSubTot = new javax.swing.JLabel();
      txtSub = new javax.swing.JTextField();
      lblIva = new javax.swing.JLabel();
      txtIva = new javax.swing.JTextField();
      lblTotPag = new javax.swing.JLabel();
      txtTot = new javax.swing.JTextField();
      lblCmpSol = new javax.swing.JLabel();
      txtComSol = new javax.swing.JTextField();
      lblTot = new javax.swing.JLabel();
      txtTotSinCom = new javax.swing.JTextField();
      panAsiDia = new javax.swing.JPanel();
      panTit = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();

      javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
      jFrame1.getContentPane().setLayout(jFrame1Layout);
      jFrame1Layout.setHorizontalGroup(
         jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 400, Short.MAX_VALUE)
      );
      jFrame1Layout.setVerticalGroup(
         jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 300, Short.MAX_VALUE)
      );

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
            formInternalFrameClosing(evt);
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

      panCen.setLayout(new java.awt.BorderLayout());

      tabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

      panTabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
      panTabGen.setLayout(new java.awt.BorderLayout());

      PanTabGen.setPreferredSize(new java.awt.Dimension(100, 100));
      PanTabGen.setLayout(null);

      lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblTipDoc.setText("Tipo de Documento:"); // NOI18N
      PanTabGen.add(lblTipDoc);
      lblTipDoc.setBounds(10, 10, 120, 20);

      lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblCodDoc.setText("Código del Documento:"); // NOI18N
      PanTabGen.add(lblCodDoc);
      lblCodDoc.setBounds(10, 30, 130, 20);

      txtCodDoc.setBackground(objParSis.getColorCamposSistema());
      txtCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      PanTabGen.add(txtCodDoc);
      txtCodDoc.setBounds(140, 30, 90, 20);

      txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
      txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesLarTipDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesLarTipDocFocusLost(evt);
         }
      });
      txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesLarTipDocActionPerformed(evt);
         }
      });
      PanTabGen.add(txtDesLarTipDoc);
      txtDesLarTipDoc.setBounds(210, 10, 230, 20);

      butBusTipDoc.setText("jButton1"); // NOI18N
      butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
      butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butBusTipDocActionPerformed(evt);
         }
      });
      PanTabGen.add(butBusTipDoc);
      butBusTipDoc.setBounds(440, 10, 20, 20);

      lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblFecDoc.setText("Fecha del Documento:"); // NOI18N
      PanTabGen.add(lblFecDoc);
      lblFecDoc.setBounds(470, 10, 110, 20);

      txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
      txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesCorTipDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesCorTipDocFocusLost(evt);
         }
      });
      txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesCorTipDocActionPerformed(evt);
         }
      });
      PanTabGen.add(txtDesCorTipDoc);
      txtDesCorTipDoc.setBounds(140, 10, 70, 20);

      txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
      PanTabGen.add(txtNumDoc);
      txtNumDoc.setBounds(590, 30, 80, 20);

      lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblNumDoc.setText("Número de Documento:"); // NOI18N
      PanTabGen.add(lblNumDoc);
      lblNumDoc.setBounds(470, 30, 120, 20);

      lblNumFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblNumFac.setText("Numero de Factura:"); // NOI18N
      PanTabGen.add(lblNumFac);
      lblNumFac.setBounds(10, 50, 130, 20);

      txtNumDocFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      PanTabGen.add(txtNumDocFac);
      txtNumDocFac.setBounds(140, 50, 90, 20);

      butFacEle.setText("Enviar SRI");
      butFacEle.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butFacEleActionPerformed(evt);
         }
      });
      PanTabGen.add(butFacEle);
      butFacEle.setBounds(240, 30, 110, 23);

      butGetXmlDevVta.setText("Obtener XML");
      butGetXmlDevVta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butGetXmlDevVtaActionPerformed(evt);
         }
      });
      PanTabGen.add(butGetXmlDevVta);
      butGetXmlDevVta.setBounds(350, 30, 110, 23);

      panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

      panObs.setPreferredSize(new java.awt.Dimension(100, 80));
      panObs.setLayout(new java.awt.BorderLayout());

      panLbl.setLayout(new java.awt.GridLayout(2, 1));

      lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
      lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
      lblObs1.setText("Observación 1:"); // NOI18N
      lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
      lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
      lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
      panLbl.add(lblObs1);

      lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
      lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
      lblObs2.setText("Observación 2:"); // NOI18N
      lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
      lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
      lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
      lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
      panLbl.add(lblObs2);

      panObs.add(panLbl, java.awt.BorderLayout.WEST);

      panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

      txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
      txtObs1.setLineWrap(true);
      spnObs3.setViewportView(txtObs1);

      panTxa.add(spnObs3);

      txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
      txtObs2.setLineWrap(true);
      spnObs4.setViewportView(txtObs2);

      panTxa.add(spnObs4);

      panObs.add(panTxa, java.awt.BorderLayout.CENTER);

      panTabGen.add(panObs, java.awt.BorderLayout.SOUTH);

      tabGen.addTab("General", panTabGen);

      panSolDev.setLayout(new java.awt.BorderLayout());

      panGenDatSolDev.setPreferredSize(new java.awt.Dimension(100, 150));
      panGenDatSolDev.setLayout(new java.awt.BorderLayout());

      panTblSolDev.setPreferredSize(new java.awt.Dimension(100, 120));
      panTblSolDev.setLayout(new java.awt.BorderLayout());

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
      srcTblSolDev.setViewportView(tblDat);

      panTblSolDev.add(srcTblSolDev, java.awt.BorderLayout.CENTER);

      panDatSolDev.setPreferredSize(new java.awt.Dimension(100, 130));
      panDatSolDev.setLayout(null);

      lblLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblLoc.setText("Local:"); // NOI18N
      panDatSolDev.add(lblLoc);
      lblLoc.setBounds(10, 0, 60, 20);

      lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblTipdoc.setText("Tipo de Documento:"); // NOI18N
      panDatSolDev.add(lblTipdoc);
      lblTipdoc.setBounds(10, 20, 100, 20);

      lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblCli.setText("Cliente:"); // NOI18N
      panDatSolDev.add(lblCli);
      lblCli.setBounds(10, 40, 60, 20);

      lblSol.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblSol.setText("Solicitante:"); // NOI18N
      panDatSolDev.add(lblSol);
      lblSol.setBounds(10, 60, 60, 20);

      txtCodLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panDatSolDev.add(txtCodLoc);
      txtCodLoc.setBounds(140, 0, 70, 20);

      txtNomLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panDatSolDev.add(txtNomLoc);
      txtNomLoc.setBounds(210, 0, 240, 20);

      txtDesCorTipDocSol.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panDatSolDev.add(txtDesCorTipDocSol);
      txtDesCorTipDocSol.setBounds(140, 20, 70, 20);

      txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panDatSolDev.add(txtCodCli);
      txtCodCli.setBounds(140, 40, 70, 20);

      txtCodSol.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panDatSolDev.add(txtCodSol);
      txtCodSol.setBounds(140, 60, 70, 20);

      txtDesLarTipDocSol.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panDatSolDev.add(txtDesLarTipDocSol);
      txtDesLarTipDocSol.setBounds(210, 20, 240, 20);

      txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      txtNomCli.addHierarchyListener(new java.awt.event.HierarchyListener() {
         public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
            txtNomCliHierarchyChanged(evt);
         }
      });
      panDatSolDev.add(txtNomCli);
      txtNomCli.setBounds(210, 40, 240, 20);

      txtDesSol.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panDatSolDev.add(txtDesSol);
      txtDesSol.setBounds(210, 60, 240, 20);

      butBusNumDocSol.setText("jButton1"); // NOI18N
      butBusNumDocSol.setPreferredSize(new java.awt.Dimension(20, 20));
      butBusNumDocSol.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butBusNumDocSolActionPerformed(evt);
         }
      });
      panDatSolDev.add(butBusNumDocSol);
      butBusNumDocSol.setBounds(650, 20, 20, 20);

      lblFecDto.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblFecDto.setText("Fecha del Documento:"); // NOI18N
      panDatSolDev.add(lblFecDto);
      lblFecDto.setBounds(460, 0, 120, 20);

      lblCodDto.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblCodDto.setText("Código del Documento:"); // NOI18N
      panDatSolDev.add(lblCodDto);
      lblCodDto.setBounds(460, 40, 120, 20);
      panDatSolDev.add(txtFecDocSol);
      txtFecDocSol.setBounds(580, 0, 70, 20);
      panDatSolDev.add(txtCodDocSol);
      txtCodDocSol.setBounds(580, 40, 70, 20);

      txtNumDocSol.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNumDocSolActionPerformed(evt);
         }
      });
      txtNumDocSol.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
            txtNumDocSolKeyPressed(evt);
         }
      });
      panDatSolDev.add(txtNumDocSol);
      txtNumDocSol.setBounds(580, 20, 70, 20);

      lblNumDto.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblNumDto.setText("Número de Documento:"); // NOI18N
      panDatSolDev.add(lblNumDto);
      lblNumDto.setBounds(460, 20, 112, 20);

      buttonGroup1.add(radCan);
      radCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      radCan.setText("Cantidad"); // NOI18N
      radCan.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            radCanActionPerformed(evt);
         }
      });
      panDatSolDev.add(radCan);
      radCan.setBounds(130, 80, 67, 20);

      buttonGroup1.add(radDes);
      radDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      radDes.setText("Porcentaje"); // NOI18N
      panDatSolDev.add(radDes);
      radDes.setBounds(200, 80, 77, 20);

      buttonGroup1.add(radPre);
      radPre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      radPre.setText("Precio Unitario"); // NOI18N
      panDatSolDev.add(radPre);
      radPre.setBounds(280, 80, 95, 20);

      buttonGroup1.add(radDesEsp);
      radDesEsp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      radDesEsp.setText("Desc. Especial"); // NOI18N
      radDesEsp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            radDesEspActionPerformed(evt);
         }
      });
      panDatSolDev.add(radDesEsp);
      radDesEsp.setBounds(380, 80, 97, 20);

      jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jLabel2.setText("Tipo de Devolución"); // NOI18N
      panDatSolDev.add(jLabel2);
      jLabel2.setBounds(10, 80, 120, 20);

      chkVolFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      chkVolFac.setText("Volver a facturar la mercadería que no se va a devolver");
      panDatSolDev.add(chkVolFac);
      chkVolFac.setBounds(480, 80, 190, 20);

      lblMotDev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblMotDev.setText("Motivo de devolución:");
      panDatSolDev.add(lblMotDev);
      lblMotDev.setBounds(10, 105, 110, 20);

      txtCodMot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      txtCodMot.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodMotActionPerformed(evt);
         }
      });
      txtCodMot.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCodMotFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodMotFocusLost(evt);
         }
      });
      panDatSolDev.add(txtCodMot);
      txtCodMot.setBounds(140, 105, 70, 20);

      txtDesLarMot.setFont(new java.awt.Font("SansSerif", 0, 11));
      txtDesLarMot.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesLarMotActionPerformed(evt);
         }
      });
      txtDesLarMot.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesLarMotFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesLarMotFocusLost(evt);
         }
      });
      panDatSolDev.add(txtDesLarMot);
      txtDesLarMot.setBounds(210, 105, 240, 20);

      panTblSolDev.add(panDatSolDev, java.awt.BorderLayout.PAGE_START);

      panGenDatSolDev.add(panTblSolDev, java.awt.BorderLayout.CENTER);

      panSolDev.add(panGenDatSolDev, java.awt.BorderLayout.CENTER);

      panCotGenSur.setLayout(new java.awt.BorderLayout());

      panCotGenSurCen.setPreferredSize(new java.awt.Dimension(250, 25));

      jPanel5.setLayout(new java.awt.GridLayout(2, 1));

      jPanel4.setLayout(new java.awt.BorderLayout());

      lblObs3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblObs3.setText("Observación 1:"); // NOI18N
      jPanel4.add(lblObs3, java.awt.BorderLayout.WEST);

      txaObs1.setLineWrap(true);
      txaObs1.setPreferredSize(new java.awt.Dimension(420, 22));
      spnObs1.setViewportView(txaObs1);

      jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

      jPanel5.add(jPanel4);

      jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
      jPanel3.setLayout(new java.awt.BorderLayout());

      lblObs4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblObs4.setText("Observación 2:"); // NOI18N
      jPanel3.add(lblObs4, java.awt.BorderLayout.WEST);

      txaObs2.setLineWrap(true);
      txaObs2.setPreferredSize(new java.awt.Dimension(420, 22));
      spnObs2.setViewportView(txaObs2);

      jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

      jPanel5.add(jPanel3);

      javax.swing.GroupLayout panCotGenSurCenLayout = new javax.swing.GroupLayout(panCotGenSurCen);
      panCotGenSurCen.setLayout(panCotGenSurCenLayout);
      panCotGenSurCenLayout.setHorizontalGroup(
         panCotGenSurCenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      );
      panCotGenSurCenLayout.setVerticalGroup(
         panCotGenSurCenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(panCotGenSurCenLayout.createSequentialGroup()
            .addGap(50, 50, 50)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(13, Short.MAX_VALUE))
      );

      panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

      panCotGenSurEst.setLayout(new java.awt.GridLayout(7, 2));

      lblSubtotIvaCero.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblSubtotIvaCero.setText("Subtotal IVA 0%");
      panCotGenSurEst.add(lblSubtotIvaCero);

      txtSubTotIvaCero2.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      panCotGenSurEst.add(txtSubTotIvaCero2);

      lbSubTotIvaGra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lbSubTotIvaGra.setText("Subtotal IVA 14%");
      panCotGenSurEst.add(lbSubTotIvaGra);

      txtSubTotIvaGra2.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      panCotGenSurEst.add(txtSubTotIvaGra2);

      lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblSubTot.setText("SubTotal:"); // NOI18N
      lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
      panCotGenSurEst.add(lblSubTot);

      txtSub.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      panCotGenSurEst.add(txtSub);

      lblIva.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblIva.setText("IVA 14%:"); // NOI18N
      lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
      panCotGenSurEst.add(lblIva);

      txtIva.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      panCotGenSurEst.add(txtIva);

      lblTotPag.setText("Total");
      panCotGenSurEst.add(lblTotPag);

      txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
      txtTot.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      panCotGenSurEst.add(txtTot);

      lblCmpSol.setText("Com. Sol");
      panCotGenSurEst.add(lblCmpSol);

      txtComSol.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      panCotGenSurEst.add(txtComSol);

      lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
      lblTot.setText("Total a pagar:"); // NOI18N
      lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
      panCotGenSurEst.add(lblTot);

      txtTotSinCom.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
      panCotGenSurEst.add(txtTotSinCom);

      panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

      panSolDev.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

      tabGen.addTab("Datos de la Solicitud de Devoluci\u00f3n", panSolDev);

      panAsiDia.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      panAsiDia.setLayout(new java.awt.BorderLayout());
      tabGen.addTab("Asiento de Diario", panAsiDia);

      panCen.add(tabGen, java.awt.BorderLayout.CENTER);

      getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

      panTit.setPreferredSize(new java.awt.Dimension(100, 30));

      lblTit.setText("titulo"); // NOI18N
      panTit.add(lblTit);

      getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-550)/2, 700, 550);
   }// </editor-fold>//GEN-END:initComponents

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
   txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void radCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCanActionPerformed

    
}//GEN-LAST:event_radCanActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
         strDesCorTipDoc=txtDesCorTipDoc.getText();
         txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
     if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else 
                BuscarTipDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
        }else
            txtDesCorTipDoc.setText(strDesCorTipDoc);                      
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
 strDesLarTipDoc=txtDesLarTipDoc.getText();
 txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
  if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else 
                BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
        }else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost
  
private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
/*Agregado por Christian Mateo para control en insertar Devoluciones de ventas el tipo de documento sea electronico*/
        configurarVenConTipDoc();
        vcoTipDoc.intModoEje=objTooBar.getEstAct();
/*Agregado por Christian Mateo para control en insertar Devoluciones de ventas el tipo de documento sea electronico*/
        
  vcoTipDoc.setTitle("Listado de Tipo de Documentos");
        vcoTipDoc.setCampoBusqueda(1);
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
           txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
           txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
           strCodTipDoc=vcoTipDoc.getValueAt(1);
           strMerIngEgr=vcoTipDoc.getValueAt(4);
           strTipIngEgr=vcoTipDoc.getValueAt(5);
			/*Agregado por Christian Mateo para presentar la numeracion del documento cuando sea un ingreso*/
           if(vcoTipDoc.intModoEje==5){
                txtNumDoc.setText(String.valueOf(Integer.parseInt(vcoTipDoc.getValueAt(6))+1));
           }
           /*Agregado por Christian Mateo para presentar la numeracion del documento cuando sea un ingreso*/
           objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
        }          
}//GEN-LAST:event_butBusTipDocActionPerformed


private void txtNomCliHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txtNomCliHierarchyChanged
// TODO add your handling code here:
}//GEN-LAST:event_txtNomCliHierarchyChanged

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void txtNumDocSolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocSolKeyPressed
    // TODO add your handling code here:
//     if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
//            if(!cargarSol(txtNumDocSol.getText()))
//                 cargarVenBusSol();
//        } 
}//GEN-LAST:event_txtNumDocSolKeyPressed

private void butBusNumDocSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusNumDocSolActionPerformed
     cargarVenBusSol();
}//GEN-LAST:event_butBusNumDocSolActionPerformed

private void txtNumDocSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocSolActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_txtNumDocSolActionPerformed

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
     String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {

            if(rstCab!=null) 
               rstCab=null;
            if(STM_GLO!=null)
               STM_GLO=null;

            //System.gc();
            Runtime.getRuntime().gc();
            dispose();
        }
}//GEN-LAST:event_formInternalFrameClosing

    private void radDesEspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radDesEspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radDesEspActionPerformed

    private void txtCodMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodMotActionPerformed

    private void txtCodMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodMotFocusGained

    private void txtCodMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodMotFocusLost

    private void txtDesLarMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarMotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarMotActionPerformed

    private void txtDesLarMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarMotFocusGained

    private void txtDesLarMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLarMotFocusLost

   private void butFacEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butFacEleActionPerformed
      // TODO add your handling code here:
      enviarDevolucionVentaEnLineaSRI();
   }//GEN-LAST:event_butFacEleActionPerformed

   private void butGetXmlDevVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGetXmlDevVtaActionPerformed
      // TODO add your handling code here:
      obtenerXMLDevolucionVenta();
   }//GEN-LAST:event_butGetXmlDevVtaActionPerformed


private void cargarVenBusSol(){
    
    Ventas.ZafVen27.ZafVen27_01 obj = new Ventas.ZafVen27.ZafVen27_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis );
       obj.show();
        if(obj.acepta()){ 
             txtNumDocSol.setText(obj.GetCamSel(4));
             txtCodLocSol.setText(obj.GetCamSel(1));
             
             if(!cargarSol(txtNumDocSol.getText(), txtCodLocSol.getText() ))
                 cargarVenBusSol();
        }
       obj.dispose();
       obj=null;
 }

    
private boolean cargarSol(String strNumDoc, String strCodLocSol){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
   conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
     
        String strAux="SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp="+intCodEmpDevVen+" AND " +
        " b.co_loc = "+intCodLocDevVen+" AND  b.co_mnu ="+objParSis.getCodigoMenu()+" ";

    

          
          strSql="SELECT cli.fe_doc as fec_fac, a.co_locrel, a.co_tipdocrel, a.co_docrel , a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc,    a1.tx_nom as tx_desloc ,a.tx_obs1, a.tx_obs2 " +
                  ",a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc  ,a.nd_sub, a.nd_valiva, a.nd_tot, a.st_tipdev, cli.nd_poriva " +
                  ",c.st_ivaven ,a.st_volfacmersindev   ,cli.co_com,  cli.tx_nomven, cli.tx_ate, cli.tx_dircli, cli.tx_ciucli, cli.tx_telcli,  cli.tx_ruc , cli.ne_numdoc as numfac, cli.st_coninv, a.st_impguiremaut " +
                  ",a.co_motdev, motdev.tx_deslar as tx_deslarmotdev " +

                  
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/
                  //", cli.co_tipdoc as tipdocfac, cli.co_loc as locfac, cli.co_doc as docfac "+
                  ", cli.co_emp as empfac, cli.co_tipdoc as tipdocfac, cli.co_loc as locfac, cli.co_doc as docfac, a.nd_valcomsol, a.nd_porcomsol, a.nd_subivacer, a.nd_subivagra "+
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/

                  
                  "FROM( SELECT * FROM( " +
                  " SELECT a.tx_nomusrsol ,a.fe_doc,a.ne_numdoc ,a.nd_sub, a.nd_valiva, a.nd_tot, a.st_volfacmersindev, " +
                  " a.co_cli, a.tx_nomcli,  a.co_usrsol, a.co_emp , a.co_loc, a.co_tipdoc, a.co_doc, a.co_locrel, a.tx_obs1, a.tx_obs2,  a.co_tipdocrel, a.co_docrel, a.st_tipDev, a.st_RevBodMerDev, a.st_meraceingsis, a1.nd_canRevBodAce,  " +
                  //" a.st_impguiremaut, a.co_motdev, CASE WHEN ( " +
                  " a.st_impguiremaut, a.co_motdev, a.nd_valcomsol, a.nd_porcomsol, a.nd_subivacer, a.nd_subivagra, CASE WHEN ( " +                  
                  "  select sum((x1.nd_candev - x1.nd_canvolfac)) as dat from  tbm_cabsoldevven as x  " +
                  " inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  " +
                  " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc " +
                  " ) != 0  THEN 'N' ELSE 'S' END as st_volfac " +
                  " FROM tbm_cabsoldevven as a  " +
                  " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) " +
                  " WHERE " +
                  " a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+strCodLocSol+" AND a.co_tipdoc IN ("+strAux+ " ) " +
                  "  AND a.ne_numdoc="+strNumDoc+" AND a.st_reg = 'A' AND a.st_aut ='A' " +
                  " ) AS x WHERE  " +
                   " CASE WHEN  x.st_impguiremaut IN ('S') THEN    "+
                  " CASE WHEN x.st_volfac IN ('N') THEN  " +
                  " CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0 ELSE x.st_meraceingsis='N' END " +
                  " ELSE x.st_meraceingsis='N' END " +
                    " ELSE x.st_meraceingsis='N' END  "+
                  " ) AS a " +
                  " INNER JOIN tbm_cabmovinv AS cli ON(cli.co_emp=a.co_emp AND cli.co_loc=a.co_locrel AND cli.co_tipdoc=a.co_tipdocrel AND cli.co_doc=a.co_docrel )  " +
                  " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel)  " +
                  " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc)  " +
                  " INNER JOIN tbm_cli AS c ON (c.co_emp=a.co_emp AND c.co_cli=a.co_cli)  " +
                  " LEFT JOIN tbm_motdevinv AS motdev ON (motdev.co_emp=a.co_emp AND motdev.co_motdev=a.co_motdev) "+
                  " GROUP BY cli.fe_doc, a.co_locrel, a.co_tipdocrel, a.co_docrel , a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom ,a.tx_obs1, a.tx_obs2,   " +
                  "  a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc, a.nd_sub, a.nd_valiva, a.nd_tot, a.st_tipdev , " +
                  "   cli.nd_poriva, c.st_ivaven, a.st_volfacmersindev   ,cli.co_com,  cli.tx_nomven, cli.tx_ate, cli.tx_dircli, cli.tx_ciucli, cli.tx_telcli,  cli.tx_ruc , " +
                  "  cli.ne_numdoc, cli.st_coninv, a.st_impguiremaut, a.co_motdev, motdev.tx_deslar "+
                  
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/
                  //" ,cli.co_tipdoc,cli.co_loc,cli.co_doc";
                  " ,cli.co_emp,cli.co_tipdoc,cli.co_loc,cli.co_doc, a.nd_valcomsol, a.nd_porcomsol, a.nd_subivacer, a.nd_subivagra";
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/
                   
          
     System.out.println("cargarSol: " + strSql );

      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
       if(cargarSolCab(conn, rstLoc)){  
        if(cargarSolDet(conn, rstLoc)){
            if(blnIsCosenco || blnIsDetopacio || blnIsEcuatosa){
               CalculaAsientoDiarioEcuaCoseDeto(conn,rstLoc.getInt("empfac"),rstLoc.getInt("locfac"), rstLoc.getInt("tipdocfac"),  rstLoc.getInt("docfac"));
               bloquea(txtNumDocSol, false);
               blnRes=true;               
            }else{              
               CalculaAsientoDiario();
               bloquea(txtNumDocSol, false);
               blnRes=true;
            }            

      }}}
      rstLoc.close(); 
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    conn.close();
    conn=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
   




    private void bloquea(javax.swing.JTextField txtFiel,  boolean blnEst){
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }



    private boolean cargarSolCab(java.sql.Connection conn, java.sql.ResultSet rstDat)
    {
        boolean blnRes=false;
        try
        {
          if(conn!=null)
          {

                strFecFac=rstDat.getString("fec_fac");
                System.out.println("-->"+ strFecFac );
                txtCodLoc.setText(rstDat.getString("co_loc"));
                txtNomLoc.setText(rstDat.getString("tx_desloc"));
                txtCodTipDocSol.setText(rstDat.getString("co_tipdoc"));
                txtDesCorTipDocSol.setText(rstDat.getString("tx_descor"));
                txtDesLarTipDocSol.setText(rstDat.getString("tx_deslar"));
                txtCodCli.setText(rstDat.getString("co_cli"));
                txtNomCli.setText(rstDat.getString("tx_nomcli"));
                txtCodSol.setText(rstDat.getString("co_usrsol"));
                txtDesSol.setText(rstDat.getString("tx_nomusrsol"));
                txtFecDocSol.setText(rstDat.getString("fe_doc"));
                txtCodDocSol.setText(rstDat.getString("co_doc"));

                txtCodComFac.setText( rstDat.getString("co_com"));
                txtNomVenFac.setText( rstDat.getString("tx_nomven"));
                txtAteFac.setText( rstDat.getString("tx_ate"));
                txtDirCliFac.setText( rstDat.getString("tx_dircli"));
                txtCiuFac.setText( rstDat.getString("tx_ciucli"));
                txtTelCliFac.setText( rstDat.getString("tx_telcli"));
                txtRucFac.setText( rstDat.getString("tx_ruc"));
                txtNumDocFac.setText( rstDat.getString("numfac"));

                /**/
                strFacDev=rstDat.getString("locfac")+"-"+rstDat.getString("tipdocfac")+"-"+rstDat.getString("docfac");
                /**/

                txtCodMot.setText(rstDat.getString("co_motdev"));
                txtDesLarMot.setText(rstDat.getString("tx_deslarmotdev"));

                chkVolFac.setSelected((rstDat.getString("st_volfacmersindev").equals("S")?true:false));

                if(rstDat.getString("st_tipdev").equals("C")){ 
                    radCan.setSelected(true);
                    ocultaCol(INT_TBL_PREDEV) ; 
                    ocultaCol(INT_TBL_CANDES) ; 
                    MostrarCol(INT_TBL_CANDEV) ;
                }
                if(rstDat.getString("st_tipdev").equals("P")){ 
                    radDes.setSelected(true);
                    ocultaCol(INT_TBL_PREDEV) ; 
                    MostrarCol(INT_TBL_CANDES) ; 
                    ocultaCol(INT_TBL_CANDEV) ;
                }
                if(rstDat.getString("st_tipdev").equals("V")){ 
                    radPre.setSelected(true);
                    MostrarCol(INT_TBL_PREDEV) ; 
                    ocultaCol(INT_TBL_CANDES) ; 
                    ocultaCol(INT_TBL_CANDEV) ;
                }
                if(rstDat.getString("st_tipdev").equals("E")){ 
                    radDesEsp.setSelected(true);
                    ocultaCol(INT_TBL_PREDEV) ; 
                    MostrarCol(INT_TBL_CANDES) ; 
                    ocultaCol(INT_TBL_CANDEV) ;
                }

                txaObs1.setText(rstDat.getString("tx_obs1"));
                txaObs2.setText(rstDat.getString("tx_obs2"));

                txtSub.setText(""+ objUti.redondear( rstDat.getString("nd_sub") , 2) );
                txtIva.setText(""+ objUti.redondear( rstDat.getString("nd_valiva") , 2) );
                
                /*AGREGADO POR COMPENSACION SOLIDARIA*/
                /*txtComSol.setText(""+objUti.redondear( rstDat.getString("nd_valcomsol") , 2));
                txtSubTotIvaCero2.setText(""+rstDat.getString("nd_subivacer"));
                txtSubTotIvaGra2.setText(""+rstDat.getString("nd_subivagra"));*/
                
                
                txtComSol.setText(""+objUti.redondear((rstDat.getString("nd_valcomsol")!=null?rstDat.getString("nd_valcomsol"):"0"), 2));
                txtSubTotIvaCero2.setText(""+ objUti.redondear(rstDat.getString("nd_subivacer")!=null?rstDat.getString("nd_subivacer"):"0",2));
                txtSubTotIvaGra2.setText(""+ objUti.redondear(rstDat.getString("nd_subivagra")!=null?rstDat.getString("nd_subivagra"):"0",2));
                txtTotSinCom.setText(""+objUti.redondear(objUti.redondear( rstDat.getString("nd_sub") , 2)+objUti.redondear( rstDat.getString("nd_valiva") , 2) - objUti.redondear( (rstDat.getString("nd_valcomsol")!=null?rstDat.getString("nd_valcomsol"):"0") , 2),2));
                dblPorComSol=rstDat.getDouble("nd_porcomsol");
                
                /*AGREGADO POR COMPENSACION SOLIDARIA*/
                
                
                txtTot.setText(""+ objUti.redondear( rstDat.getString("nd_tot") , 2) );

                dblPorIva=rstDat.getDouble("nd_poriva");
                stIvaVen = rstDat.getString("st_ivaven");

                txtCodLocRelFac.setText(rstDat.getString("co_locrel"));
                txtCodTipDocRelFac.setText(rstDat.getString("co_tipdocrel"));
                txtCodDocRelFac.setText(rstDat.getString("co_docrel"));

                txtConInv.setText(rstDat.getString("st_coninv"));

                blnRes=true;
          }
       }
       catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
       return blnRes;          
   }   



 
/*
 * MODIFICADO EFLORESA 2012-04-17
 * DESCUENTO ESPECIAL
 */
private boolean cargarSolDet(Connection conn, ResultSet rstDat){
 boolean blnRes=false;
 try{
   if(conn!=null){

       glo_strImpGui =  rstDat.getString("st_impguiremaut");
       
      if(rstDat.getString("st_tipDev").equals("P") || rstDat.getString("st_tipDev").equals("V") || rstDat.getString("st_tipDev").equals("E")){

         if(_cargarSolDetDesPre(conn, rstDat.getInt("co_emp"), rstDat.getInt("co_loc"), rstDat.getInt("co_tipdoc"), rstDat.getInt("co_doc") )){
              blnRes=true;
         }

      /*}else if(rstDat.getString("st_tipDev").equals("V")){

         if(_cargarSolDetDesPre(conn, rstDat.getInt("co_emp"), rstDat.getInt("co_loc"), rstDat.getInt("co_tipdoc"), rstDat.getInt("co_doc") )){
              blnRes=true;
         }*/

      }else if(rstDat.getString("st_tipDev").equals("C")){

         if(cargarSolDetCanItm(conn, rstDat.getInt("co_emp"), rstDat.getInt("co_loc"), rstDat.getInt("co_tipdoc"), rstDat.getInt("co_doc") )){
              blnRes=true;
         }
      }
     
  }}catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
     
private boolean _cargarSolDetDesPre(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
   if(conn!=null){
     stmLoc=conn.createStatement();

     vecData = new Vector();

     strSql="SELECT  ( a.nd_candev - a.nd_CanAceIngSis ) as candevPreDes, "
     + " a.nd_canRec as nd_cantotRec, a.nd_canNunRec,  a.co_reg,  a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, "
     + "  a.co_bod ,a1.tx_nom , a.nd_can, a.nd_candev, a.st_revtec  ,a.nd_CanAceIngSis,  a.nd_preuni, a.nd_pordes, a.st_iva, "
     + "  a.nd_tot, d1.co_itmact  ,d1.nd_cosunigrp ,d1.tx_codalt2 , b.st_ser ,  "
     + "  a.st_devAutMerPrv ,d1.st_meringegrfisbod as proconf  "
     + "  FROM tbm_detsoldevven AS a   "
     + "  INNER JOIN tbm_detmovinv AS d1 ON (d1.co_emp=a.co_emp AND d1.co_loc=a.co_locrel AND d1.co_tipdoc=a.co_tipdocrel AND d1.co_doc=a.co_docrel AND d1.co_reg=a.co_regrel)  "
     + " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm )  "
     + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)  "
     + " WHERE a.co_emp="+intCodEmp+" AND  a.co_loc="+intCodLoc+" AND a.co_tipdoc="+intCodTipDoc+" AND a.co_doc="+intCodDoc+"  "
     + " ORDER BY a.co_reg "; 
     
     System.out.println("ZafVen28._cargarSolDetDesPre: "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();

          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
          vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
          vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed") );
          vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod") );
          vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
          vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_can") );
          vecReg.add(INT_TBL_CANDEV,  "0"  );
          vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni") );
          vecReg.add(INT_TBL_PREDEV, rstLoc.getString("candevPreDes") );
          vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes") );
          vecReg.add(INT_TBL_CANDES, rstLoc.getString("candevPreDes") );
          vecReg.add(INT_TBL_IVA, (rstLoc.getString("st_iva").equals("S")?true:false));
          vecReg.add(INT_TBL_TOT, "" );
          vecReg.add(INT_TBL_CODITMACT, rstLoc.getString("co_itmact") );
          vecReg.add(INT_TBL_COSTO, rstLoc.getString("nd_cosunigrp") );
          vecReg.add(INT_TBL_CODALT2, rstLoc.getString("tx_codalt2") );
          vecReg.add(INT_TBL_ITMSER, rstLoc.getString("st_ser") );
          vecReg.add(INT_TBL_CANDIFDEV, "" );
          vecReg.add(INT_TBL_CODREGSOL, rstLoc.getString("co_reg") );
          vecReg.add(INT_TBL_CODLOC_OC, null );
          vecReg.add(INT_TBL_CODTIP_OC, null );
          vecReg.add(INT_TBL_CODDOC_OC, null );
          vecReg.add(INT_TBL_CODREG_OC, null );
          vecReg.add(INT_TBL_CODREGDEV, "" );
          vecReg.add(INT_TBL_CANDEV_ORI,"");
          vecReg.add(INT_TBL_CANDEVACEBOD, "");
          vecReg.add(INT_TBL_ACEINGSIS, rstLoc.getString("nd_CanAceIngSis") );
          vecReg.add(INT_TBL_SUMTOTINGSIS, "" );
          vecReg.add(INT_TBL_SUMTOTBODACE, "" );
          vecReg.add(INT_TBL_CANDEVPREDES_SOL, rstLoc.getString("nd_candev") );
          vecReg.add(INT_TBL_CANDEVPREDES_ORI, "" );
          vecReg.add(INT_TBL_SUMTOTCANDEV, "" );
          vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));
          vecReg.add(INT_TBL_DEVPRVAUT, "");
          vecReg.add(INT_TBL_SEDEVPRV, "0");
          vecReg.add(INT_TBL_NODEVPRV, "");
          vecReg.add(INT_TBL_CODLOC_OT, "" );
          vecReg.add(INT_TBL_CODTIP_OT, "" );
          vecReg.add(INT_TBL_CODDOC_OT, "" );
          vecReg.add(INT_TBL_CODREG_OT, "" );
          vecReg.add(INT_TBL_CANSOLSTOCK, "");

          vecReg.add(INT_TBL_CODCTAEGR, null );
          vecReg.add(INT_TBL_TXTCTAEGR, null );
          vecReg.add(INT_TBL_DESCTAEGR, null );
          
         vecData.add(vecReg);
       }
         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

    private boolean cargarSolDetCanItm(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        Vector vecData;
        double dblCanDev = 0;
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                vecData = new Vector();

                strSql  = " SELECT  "
                        + " a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod ,a1.tx_nom , a.nd_can, "
                        + " ( a.nd_candev - (a.nd_CanAceIngSis + a.nd_canNunRec + a.nd_canrevbodrec ) ) as candevven, "
                        + " a.nd_preuni, a.nd_pordes, a.st_iva, d1.co_itmact, d1.nd_cosunigrp ,d1.tx_codalt2 , b.st_ser, "
                        + " b.co_ctaegr, cta.tx_codcta, cta.tx_deslar, "
                        + " a.co_reg, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel,  "
                        + " a.nd_canRevBodAce, a.nd_CanAceIngSis, a.nd_candev, "
                        + " a.nd_cansolnodevprv,   a.nd_candevprv ,  a.nd_cannodevprv, a.st_devAutMerPrv, "
                        + " case when a.nd_cansolnodevprv > 0 then  a.nd_canRevBodAce  else  0  end  as canSolStkAce,    "
                        //+ " d1.st_meringegrfisbod as proconf, d1.nd_cannunrec, d1.nd_can    " //Mateo
                        + " d1.st_meringegrfisbod as proconf "
                        + " FROM tbm_detsoldevven AS a   "
                        + " INNER JOIN tbm_detmovinv AS d1 ON (d1.co_emp=a.co_emp AND d1.co_loc=a.co_locrel AND d1.co_tipdoc=a.co_tipdocrel AND d1.co_doc=a.co_docrel AND d1.co_reg=a.co_regrel)   "
                        + " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm )   "
                        + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)   "
                        + " LEFT JOIN tbr_detmovinv AS a2 ON(a2.co_emp=a.co_emp and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel and a2.co_reg=a.co_regrel    "
                        + " and a2.co_emprel="+intCodEmp+" and a2.co_locrel="+intCodLoc+"  and a2.co_tipdocrel= 2   )   "
                        + " LEFT JOIN tbm_placta cta on (cta.co_emp=b.co_emp and cta.co_cta=b.co_ctaegr) "
                        + " WHERE a.co_emp="+intCodEmp+" AND  a.co_loc="+intCodLoc+"  AND a.co_tipdoc="+intCodTipDoc+" AND a.co_doc="+intCodDoc
                        + " ORDER BY a.co_reg ";

                System.out.println("cargarSolDetCanItm-> "+strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();

                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
                    vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
                    vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
                    vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed") );
                    vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod") );
                    vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
                    vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_CANDEV, rstLoc.getString("candevven") );
                    vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni") );
                    vecReg.add(INT_TBL_PREDEV, "0" );
                    vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes") );
                    vecReg.add(INT_TBL_CANDES, "0" );
                    vecReg.add(INT_TBL_IVA, (rstLoc.getString("st_iva").equals("S")?true:false));
                    vecReg.add(INT_TBL_TOT, "" );
                    vecReg.add(INT_TBL_CODITMACT, rstLoc.getString("co_itmact") );
                    vecReg.add(INT_TBL_COSTO, rstLoc.getString("nd_cosunigrp") );
                    vecReg.add(INT_TBL_CODALT2, rstLoc.getString("tx_codalt2") );
                    vecReg.add(INT_TBL_ITMSER, rstLoc.getString("st_ser") );
                    vecReg.add(INT_TBL_CANDIFDEV, "" );
                    vecReg.add(INT_TBL_CODREGSOL, rstLoc.getString("co_reg") );
                    vecReg.add(INT_TBL_CODLOC_OC, rstLoc.getString("co_locrel") );
                    vecReg.add(INT_TBL_CODTIP_OC, rstLoc.getString("co_tipdocrel") );
                    vecReg.add(INT_TBL_CODDOC_OC,  rstLoc.getString("co_docrel") );
                    vecReg.add(INT_TBL_CODREG_OC,   rstLoc.getString("co_regrel") );
                    vecReg.add(INT_TBL_CODREGDEV, "" );
                    vecReg.add(INT_TBL_CANDEV_ORI,"");
                    vecReg.add(INT_TBL_CANDEVACEBOD, rstLoc.getString("nd_canRevBodAce") );
                    vecReg.add(INT_TBL_ACEINGSIS, rstLoc.getString("nd_CanAceIngSis") );
                    vecReg.add(INT_TBL_SUMTOTINGSIS, "0");
                    vecReg.add(INT_TBL_SUMTOTBODACE, "0");
                    vecReg.add(INT_TBL_CANDEVPREDES_SOL, rstLoc.getString("nd_candev") );
                    vecReg.add(INT_TBL_CANDEVPREDES_ORI, "" );
                    vecReg.add(INT_TBL_SUMTOTCANDEV, "0");
                    vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));
                    vecReg.add(INT_TBL_DEVPRVAUT, rstLoc.getString("st_devAutMerPrv"));
                    vecReg.add(INT_TBL_SEDEVPRV, rstLoc.getString("nd_candevprv"));
                    vecReg.add(INT_TBL_NODEVPRV, rstLoc.getString("nd_cannodevprv"));
                    vecReg.add(INT_TBL_CODLOC_OT, "" );
                    vecReg.add(INT_TBL_CODTIP_OT, "" );
                    vecReg.add(INT_TBL_CODDOC_OT, "" );
                    vecReg.add(INT_TBL_CODREG_OT, "" );
                    vecReg.add(INT_TBL_CANSOLSTOCK, rstLoc.getString("canSolStkAce"));  //    nd_cansolnodevprv

                    vecReg.add(INT_TBL_CODCTAEGR, rstLoc.getString("co_ctaegr"));  //    nd_cansolnodevprv
                    vecReg.add(INT_TBL_TXTCTAEGR, rstLoc.getString("tx_codcta"));  //    nd_cansolnodevprv
                    vecReg.add(INT_TBL_DESCTAEGR, rstLoc.getString("tx_deslar"));  //    nd_cansolnodevprv

                    /*vecReg.add(INT_TBL_CANNUNRECFAC,rstLoc.getString("nd_cannunrec"));
                    vecReg.add(INT_TBL_CANFAC,rstLoc.getString("nd_can"));*/

                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }





   
     public void calculaSubtotal(){
        try{
        for(int intCelSel=0; intCelSel < tblDat.getRowCount(); intCelSel++){
       
        double dblCanFac =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))),2),
                dblCanDev =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString())  ))),2),
                dblCan    =  dblCanFac - dblCanDev , 
                dblPre    =  Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString()))),
                dblPorDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))),2),
                dblValDes =   ((dblCanDev * dblPre)==0)?0:((dblCanDev * dblPre) * dblPorDes / 100) ,
                dblTotal  = (dblCanDev * dblPre)- dblValDes;
                dblTotal =  objUti.redondear(dblTotal,3);  
                dblTotal =  objUti.redondear(dblTotal,2);  
                   
               tblDat.setValueAt(new Double(dblTotal),intCelSel, INT_TBL_TOT); 
        }       
        
        calculaTotal();
        dblTotalDoc = objUti.redondear(txtTot.getText(),4);
        generaAsiento();
        } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }        
      }    
    
     
     public void calculaSubtotalBig(){
        try{
        for(int intCelSel=0; intCelSel < tblDat.getRowCount(); intCelSel++){
       
        BigDecimal bigCanFac =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))).setScale(2, RoundingMode.HALF_UP),
                bigCanDev =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString())  ))).setScale(2, RoundingMode.HALF_UP),
                bigCan    =  bigCanFac.subtract(bigCanDev) , 
                bigPre    =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString()))),
                bigPorDes =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))).setScale(2, RoundingMode.HALF_UP),
                
                //dblValDes =   ((dblCanDev * dblPre)==0)?0:((dblCanDev * dblPre) * dblPorDes / 100) ,
                bigValDes =  (((bigCanDev.multiply(bigPre)).compareTo(BigDecimal.ZERO))==0)?BigDecimal.ZERO:((bigCanDev.multiply(bigPre)).multiply(bigPorDes.divide(BigDecimal.valueOf(100)))) ,
                
                //dblTotal  = (dblCanDev * dblPre)- dblValDes;
                bigTotal  = ((bigCanDev.multiply(bigPre)).subtract(bigValDes)).setScale(2, RoundingMode.HALF_UP);
                
                /*dblTotal =  objUti.redondear(dblTotal,3);  
                dblTotal =  objUti.redondear(dblTotal,2);  */
                   
                //tblDat.setValueAt(new Double(dblTotal),intCelSel, INT_TBL_TOT); 
               tblDat.setValueAt(bigTotal,intCelSel, INT_TBL_TOT); 
        }       
        
        //calculaTotal();
        calculaTotalBig();
        dblTotalDoc = objUti.redondear(txtTot.getText(),4);
        generaAsiento();
        } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }        
      }     
     
   
         
     
       public void calculaSubValores(){
         try{  
         for(int intCelSel=0; intCelSel < tblDat.getRowCount(); intCelSel++){
        
             double dblCanFac =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))),2);
           double dblCanDev = dblCanFac, 
                  dblCan    =  dblCanFac - dblCanDev , 
                  dblPre    =  Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString()) ))),
                  dblPorDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))),2),
                  dblValDes = ((dblCanDev * dblPre)==0)?0:((dblCanDev * dblPre) * dblPorDes / 100) ,
                  dblTotal  = (dblCanDev * dblPre)- dblValDes;
                    
                  dblTotal =  objUti.redondear(dblTotal,3);  
                  dblTotal =  objUti.redondear(dblTotal,2);  
                   
            tblDat.setValueAt(new Double(dblTotal),intCelSel, INT_TBL_TOT);    
         }   
            calculaTotal();
            dblTotalDoc = objUti.redondear(txtTot.getText(),4);
            generaAsiento();
        } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }      
      }    
    
   
    public void calculaSubValoresBig(){
         try{  
         for(int intCelSel=0; intCelSel < tblDat.getRowCount(); intCelSel++){
        
             //double dblCanFac =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))),2);
             BigDecimal bigCanFac =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))).setScale(2, RoundingMode.HALF_UP);
           //double dblCanDev = dblCanFac, 
            BigDecimal bigCanDev=bigCanFac,
                  //dblCan    =  dblCanFac - dblCanDev , 
                    bigCan    =  bigCanFac.subtract(bigCanDev) , 
                  //dblPre    =  Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString()) ))),
                  bigPre    =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString()) ))),                    
                  //dblPorDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))),2),
                  bigPorDes =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))).setScale(2, RoundingMode.HALF_UP),
                  //dblValDes = ((dblCanDev * dblPre)==0)?0:((dblCanDev * dblPre) * dblPorDes / 100) ,
                  bigValDes = ((bigCanDev.multiply(bigPre)).compareTo(BigDecimal.ZERO)==0)?BigDecimal.ZERO:((bigCanDev.multiply(bigPre)).multiply(bigPorDes.divide(BigDecimal.valueOf(100)))) ,
                  //dblTotal  = (dblCanDev * dblPre)- dblValDes;
                  bigTotal  = ((bigCanDev.multiply(bigPre)).subtract(bigValDes)).setScale(2, RoundingMode.HALF_UP);
                   
                //tblDat.setValueAt(new Double(dblTotal),intCelSel, INT_TBL_TOT);    
                tblDat.setValueAt(bigTotal,intCelSel, INT_TBL_TOT);
         }   
            //calculaTotal();
            calculaTotalBig();
            dblTotalDoc = objUti.redondear(txtTot.getText(),4);
            generaAsiento();
        } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }      
      }       
     
     
     
      public void calculaDescuento(){
          try{
         for(int intCelSel=0; intCelSel < tblDat.getRowCount(); intCelSel++){
                
          double dblCan =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))),2);
          double dblPre    =  Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
          double dblPorDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANDES)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString()) ))), objParSis.getDecimalesBaseDatos() );
              
          double dblDesItm =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()) ))), objParSis.getDecimalesBaseDatos() );
          double dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDesItm / 100));
          dblValDes = (dblCan * dblPre)- dblValDes;
          
          //double dblValDes =   (dblCan * dblPre); 
          double dblTotal  = (dblValDes * (dblPorDes/100));
                    
                 dblTotal =  objUti.redondear(dblTotal,3);  
                 dblTotal =  objUti.redondear(dblTotal,2);  
                   
            tblDat.setValueAt(new Double(dblTotal),intCelSel, INT_TBL_TOT );   
         }    
            calculaTotal();
            dblTotalDoc = objUti.redondear(txtTot.getText(),4);
            generaAsientoPorDescuento();
        } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }          
    }    
      
     
      
      public void calculaDescuentoBig(){
          try{
         for(int intCelSel=0; intCelSel < tblDat.getRowCount(); intCelSel++){
                
          //double dblCan =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))),2);
          BigDecimal bigCan =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANVEN).toString()))).setScale(2, RoundingMode.HALF_UP);
          //double dblPre    =  Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
          BigDecimal bigPre    =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
          //double dblPorDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANDES)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString()) ))), objParSis.getDecimalesBaseDatos() );
          BigDecimal bigPorDes =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANDES)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDES).toString()) ))).setScale(2, RoundingMode.HALF_UP);
              
          //double dblDesItm =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()) ))), objParSis.getDecimalesBaseDatos() );
          BigDecimal bigDesItm =  new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES)==null)?"0":((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()) ))).setScale(2, RoundingMode.HALF_UP);
//          double dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDesItm / 100));
          BigDecimal bigValDes =   ((bigCan.multiply(bigPre).compareTo(BigDecimal.ZERO) )==0)?BigDecimal.ZERO:((bigCan.multiply(bigPre)).multiply((bigDesItm.divide(BigDecimal.valueOf(100)))));
          //dblValDes = (dblCan * dblPre)- dblValDes;
          bigValDes=(bigCan.multiply(bigPre)).subtract(bigValDes);
          
          //double dblValDes =   (dblCan * dblPre); 
          //double dblTotal  = (dblValDes * (dblPorDes/100));
          BigDecimal bigTotal  = (bigValDes.multiply(bigPorDes.divide(BigDecimal.valueOf(100))));
          bigTotal=bigTotal.setScale(2, RoundingMode.HALF_UP);
          /*dblTotal =  objUti.redondear(dblTotal,3);  
          dblTotal =  objUti.redondear(dblTotal,2);  */
                   
            //tblDat.setValueAt(new Double(dblTotal),intCelSel, INT_TBL_TOT );   
            tblDat.setValueAt(bigTotal,intCelSel, INT_TBL_TOT );   
         }    
            //calculaTotal();
            calculaTotalBig();
            dblTotalDoc = objUti.redondear(txtTot.getText(),4);
            generaAsientoPorDescuento();
        } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }          
    }       
     
     
public void  calculaTotal(){
   try{ 
        double dblSub = 0, dblIva = 0, dblSubtotalDoc = 0, dblTmp=0, dblSub2=0, dblIvaDoc=0, dblTotalDoc=0;
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
        double dblSubIvaGra=0, dblValCom=0;
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
        
        int intNumDec=2;
        for( int i=0;i<tblDat.getRowCount();i++ ){
         if (tblDat.getValueAt(i,INT_TBL_CODITM)!=null){
          if (tblDat.getValueAt(i,INT_TBL_CANDEV)!=null){
               
            dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOT)==null||tblDat.getValueAt(i, INT_TBL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOT).toString()));  
            dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
            
            if( (tblDat.getValueAt(i, INT_TBL_IVA)==null?true:tblDat.getValueAt(i, INT_TBL_IVA).equals("false")?true:false) ) 
                dblIva = dblIva + 0;
            else{
                 dblTmp = ((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOT)==null||tblDat.getValueAt(i, INT_TBL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOT).toString())) : 0 );
                 dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                 /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
                 dblSubIvaGra+=dblTmp;                 
                 /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
             }
         }}}
        /////////////////////////////////////////////////////////////
        dblSubtotalDoc = dblSub;
        dblIvaDoc   = objUti.redondear(dblIva,intNumDec);
        dblTotalDoc = dblSubtotalDoc + dblIvaDoc;
        dblTotalDoc = objUti.redondear(dblTotalDoc ,intNumDec);
        dblSubtotalDoc = objUti.redondear(dblSubtotalDoc ,intNumDec);
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
        dblSubIvaGra=objUti.redondear(dblSubIvaGra,intNumDec);
        dblValCom=dblSubIvaGra*(dblPorComSol/100);
        dblValCom=objUti.redondear(dblValCom,intNumDec);
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
       
        if(stIvaVen.equals("N")){
          txtSub.setText( "" + dblSubtotalDoc );
          txtIva.setText( "0.00" );
          txtTot.setText( ""+ dblSubtotalDoc);
          txtTotSinCom.setText( "" + dblSubtotalDoc);
          dblTotalDoc=dblSubtotalDoc;
          dblIvaDoc=0;
        }else{
           txtSub.setText( "" + dblSubtotalDoc );
           txtIva.setText( "" + dblIvaDoc );
           txtTot.setText( ""+ dblTotalDoc);
           /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
           txtSubTotIvaGra2.setText( "" + dblSubIvaGra);
           txtTotSinCom.setText(""+objUti.redondear(((dblSubtotalDoc+dblIvaDoc)-dblValCom),2));
           txtComSol.setText(""+dblValCom);
           /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
           
        }
       /////////////////////////////////////////////////////////////
    } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }          
}


public void  calculaTotalBig(){
   try{ 
        BigDecimal bigSub = BigDecimal.ZERO, bigIva = BigDecimal.ZERO, bigSubtotalDoc = BigDecimal.ZERO, bigTmp=BigDecimal.ZERO, bigSub2=BigDecimal.ZERO, bigIvaDoc=BigDecimal.ZERO, bigTotalDoc=BigDecimal.ZERO;
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
        BigDecimal bigSubIvaGra=BigDecimal.ZERO, bigValCom=BigDecimal.ZERO;
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
        
        int intNumDec=2;
        for( int i=0;i<tblDat.getRowCount();i++ ){
         if (tblDat.getValueAt(i,INT_TBL_CODITM)!=null){
          if (tblDat.getValueAt(i,INT_TBL_CANDEV)!=null){
               
            bigSub2 = ((tblDat.getValueAt(i, INT_TBL_TOT)==null||tblDat.getValueAt(i, INT_TBL_TOT).toString().equals(""))?BigDecimal.ZERO:new BigDecimal(tblDat.getValueAt(i, INT_TBL_TOT).toString()));  
            bigSub = (bigSub.add(bigSub2)).setScale(2, RoundingMode.HALF_UP) ;
            
            if( (tblDat.getValueAt(i, INT_TBL_IVA)==null?true:tblDat.getValueAt(i, INT_TBL_IVA).equals("false")?true:false) ) 
                //dblIva = dblIva + 0;
                bigIva=bigIva.add(BigDecimal.ZERO);
            else{
                 //dblTmp = ((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOT)==null||tblDat.getValueAt(i, INT_TBL_TOT).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOT).toString())) : 0 );
                 bigTmp = ((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOT)==null||tblDat.getValueAt(i, INT_TBL_TOT).toString().equals(""))?BigDecimal.ZERO:new BigDecimal(tblDat.getValueAt(i, INT_TBL_TOT).toString())) : BigDecimal.ZERO );
                 //dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                 bigIva = bigIva.add(((bigTmp.multiply(new BigDecimal(dblPorIva))).compareTo(BigDecimal.ZERO))==0?BigDecimal.ZERO:(bigTmp.multiply((new BigDecimal(dblPorIva)).divide(BigDecimal.valueOf(100))))) ;
                 /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
                 //dblSubIvaGra+=dblTmp;                 
                 bigSubIvaGra=bigSubIvaGra.add(bigTmp);
                 /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
             }
         }}}
        /////////////////////////////////////////////////////////////
        //dblSubtotalDoc = dblSub;
        bigSubtotalDoc=bigSub;
        //dblIvaDoc   = objUti.redondear(dblIva,intNumDec);
        bigIvaDoc=bigIva.setScale(2, RoundingMode.HALF_UP);
        //dblTotalDoc = dblSubtotalDoc + dblIvaDoc;
        bigTotalDoc=(bigSubtotalDoc.add(bigIvaDoc)).setScale(2, RoundingMode.HALF_UP);
        //dblTotalDoc = objUti.redondear(dblTotalDoc ,intNumDec);
        //dblSubtotalDoc = objUti.redondear(dblSubtotalDoc ,intNumDec);
        bigSubtotalDoc=bigSubtotalDoc.setScale(2, RoundingMode.HALF_UP);
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
        //dblSubIvaGra=objUti.redondear(dblSubIvaGra,intNumDec);
        bigSubIvaGra= bigSubIvaGra.setScale(2, RoundingMode.HALF_UP);
        //dblValCom=dblSubIvaGra*(dblPorComSol/100);
        bigValCom=(bigSubIvaGra.multiply((BigDecimal.valueOf(dblPorComSol).divide(BigDecimal.valueOf(100))))).setScale(2, RoundingMode.HALF_UP);
        //dblValCom=objUti.redondear(dblValCom,intNumDec);
        /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
       
        if(stIvaVen.equals("N")){
          //txtSub.setText( "" + dblSubtotalDoc );
          txtSub.setText( "" + bigSubtotalDoc );
          txtIva.setText( "0.00" );
          //txtTot.setText( ""+ dblSubtotalDoc);
          txtTot.setText( ""+ bigSubtotalDoc);
          //txtTotSinCom.setText( "" + dblSubtotalDoc);
          txtTotSinCom.setText( "" + bigSubtotalDoc);
          //dblTotalDoc=dblSubtotalDoc;
          bigTotalDoc=bigSubtotalDoc;
          //dblIvaDoc=0;
          bigIvaDoc=BigDecimal.ZERO;
        }else{
           //txtSub.setText( "" + dblSubtotalDoc );
           txtSub.setText( "" + bigSubtotalDoc );
           //txtIva.setText( "" + dblIvaDoc );
           txtIva.setText( "" + bigIvaDoc );
           //txtTot.setText( ""+ dblTotalDoc);
           txtTot.setText( ""+ bigTotalDoc);
           /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
           //txtSubTotIvaGra2.setText( "" + dblSubIvaGra);
           txtSubTotIvaGra2.setText( "" + bigSubIvaGra);
           //txtTotSinCom.setText(""+objUti.redondear(((dblSubtotalDoc+dblIvaDoc)-dblValCom),2));
           txtTotSinCom.setText(""+((((bigSubtotalDoc.add(bigIvaDoc)).subtract(bigValCom))).setScale(2, RoundingMode.HALF_UP)));
           //txtComSol.setText(""+dblValCom);
           txtComSol.setText(""+bigValCom);
           /*CAMBIO REALIZADO POR EL MOTIVO DE RECEPCION DE MENOR CANTIDAD DE ITEMS DE LA SOLICITUD 27-03-2017*/
           
        }
       /////////////////////////////////////////////////////////////
    } catch(Exception Evt) { objUti.mostrarMsgErr_F1(this, Evt); }          
}


    
public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  vcoTipDoc.setTitle("Listado de Tipo de Documentos");
  if(vcoTipDoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
      txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
      txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
      strCodTipDoc=vcoTipDoc.getValueAt(1);
      strMerIngEgr=vcoTipDoc.getValueAt(4);
      strTipIngEgr=vcoTipDoc.getValueAt(5);
        txtNumDoc.setText(vcoTipDoc.getValueAt(6));
      objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
  }else{
        vcoTipDoc.setCampoBusqueda(tipo);
        vcoTipDoc.cargarDatos();
        vcoTipDoc.show();
        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
           txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
           txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
           strCodTipDoc=vcoTipDoc.getValueAt(1);
           strMerIngEgr=vcoTipDoc.getValueAt(4);
           strTipIngEgr=vcoTipDoc.getValueAt(5);
           txtNumDoc.setText(vcoTipDoc.getValueAt(6));       
           objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
        }else{
           txtCodTipDoc.setText(strCodTipDoc); 
           txtDesCorTipDoc.setText(strDesCorTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}
    
 
 
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel PanTabGen;
   private javax.swing.JButton butBusNumDocSol;
   private javax.swing.JButton butBusTipDoc;
   private javax.swing.JButton butFacEle;
   private javax.swing.JButton butGetXmlDevVta;
   private javax.swing.ButtonGroup buttonGroup1;
   private javax.swing.JCheckBox chkVolFac;
   private javax.swing.JFrame jFrame1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JPanel jPanel5;
   private javax.swing.JLabel lbSubTotIvaGra;
   private javax.swing.JLabel lblCli;
   private javax.swing.JLabel lblCmpSol;
   private javax.swing.JLabel lblCodDoc;
   private javax.swing.JLabel lblCodDto;
   private javax.swing.JLabel lblFecDoc;
   private javax.swing.JLabel lblFecDto;
   private javax.swing.JLabel lblIva;
   private javax.swing.JLabel lblLoc;
   private javax.swing.JLabel lblMotDev;
   private javax.swing.JLabel lblNumDoc;
   private javax.swing.JLabel lblNumDto;
   private javax.swing.JLabel lblNumFac;
   private javax.swing.JLabel lblObs1;
   private javax.swing.JLabel lblObs2;
   private javax.swing.JLabel lblObs3;
   private javax.swing.JLabel lblObs4;
   private javax.swing.JLabel lblSol;
   private javax.swing.JLabel lblSubTot;
   private javax.swing.JLabel lblSubtotIvaCero;
   private javax.swing.JLabel lblTipDoc;
   private javax.swing.JLabel lblTipdoc;
   private javax.swing.JLabel lblTit;
   private javax.swing.JLabel lblTot;
   private javax.swing.JLabel lblTotPag;
   private javax.swing.JPanel panAsiDia;
   private javax.swing.JPanel panCen;
   private javax.swing.JPanel panCotGenSur;
   private javax.swing.JPanel panCotGenSurCen;
   private javax.swing.JPanel panCotGenSurEst;
   private javax.swing.JPanel panDatSolDev;
   private javax.swing.JPanel panGenDatSolDev;
   private javax.swing.JPanel panLbl;
   private javax.swing.JPanel panObs;
   private javax.swing.JPanel panSolDev;
   private javax.swing.JPanel panTabGen;
   private javax.swing.JPanel panTblSolDev;
   private javax.swing.JPanel panTit;
   private javax.swing.JPanel panTxa;
   private javax.swing.JRadioButton radCan;
   private javax.swing.JRadioButton radDes;
   private javax.swing.JRadioButton radDesEsp;
   private javax.swing.JRadioButton radPre;
   private javax.swing.JScrollPane spnObs1;
   private javax.swing.JScrollPane spnObs2;
   private javax.swing.JScrollPane spnObs3;
   private javax.swing.JScrollPane spnObs4;
   private javax.swing.JScrollPane srcTblSolDev;
   private javax.swing.JTabbedPane tabGen;
   private javax.swing.JTable tblDat;
   private javax.swing.JTextArea txaObs1;
   private javax.swing.JTextArea txaObs2;
   private javax.swing.JTextField txtCodCli;
   private javax.swing.JTextField txtCodDoc;
   private javax.swing.JTextField txtCodDocSol;
   private javax.swing.JTextField txtCodLoc;
   private javax.swing.JTextField txtCodMot;
   private javax.swing.JTextField txtCodSol;
   private javax.swing.JTextField txtComSol;
   private javax.swing.JTextField txtDesCorTipDoc;
   private javax.swing.JTextField txtDesCorTipDocSol;
   private javax.swing.JTextField txtDesLarMot;
   private javax.swing.JTextField txtDesLarTipDoc;
   private javax.swing.JTextField txtDesLarTipDocSol;
   private javax.swing.JTextField txtDesSol;
   private javax.swing.JTextField txtFecDocSol;
   private javax.swing.JTextField txtIva;
   private javax.swing.JTextField txtNomCli;
   private javax.swing.JTextField txtNomLoc;
   private javax.swing.JTextField txtNumDoc;
   private javax.swing.JTextField txtNumDocFac;
   private javax.swing.JTextField txtNumDocSol;
   private javax.swing.JTextArea txtObs1;
   private javax.swing.JTextArea txtObs2;
   private javax.swing.JTextField txtSub;
   private javax.swing.JTextField txtSubTotIvaCero2;
   private javax.swing.JTextField txtSubTotIvaGra2;
   private javax.swing.JTextField txtTot;
   private javax.swing.JTextField txtTotSinCom;
   // End of variables declaration//GEN-END:variables

    
    
  private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }   
    

  public class mitoolbar extends ZafToolBar{
    public mitoolbar(javax.swing.JInternalFrame jfrThis){
        super(jfrThis, objParSis);
    }
   

@Override
public boolean anular() {
 boolean blnRes=false;
java.sql.Connection  conn;
try{
 conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
 if(conn!=null){
   conn.setAutoCommit(false);

    strAux=objTooBar.getEstadoRegistro();
    if (strAux.equals("Eliminado")) {
        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
        blnRes=true;
    }
    if (strAux.equals("Anulado")) {
        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
        blnRes=true;
    }
   if(!blnRes){
       
        if(!verificaItmUni())
             return false;
 
        if(!objUltDocPrint.verificarsiesconfirmado(txtCodDoc.getText(),txtCodTipDoc.getText()))
             return false;

        if (objUltDocPrint.isPagoDocumento_aso(intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtCodDoc.getText())))
             return false;

        if(anularReg(conn)){
         if(objAsiDia.anularDiario(conn, intCodEmpDevVen, intCodLocDevVen , Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){

            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
         }else conn.rollback();   
        }else conn.rollback();
   }else blnRes=false;

  conn.close();
  conn=null;
}}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}




private int mensajeConf(String strMsg){
  JOptionPane oppMsg=new JOptionPane();
  String strTit="Sistema Zafiro";        
  return JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);                
}   




    private boolean anularReg(java.sql.Connection conn)
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc, stmLoc01, stmLocAct;
        java.sql.ResultSet rstDocDet;
        String strSql="",strSqlFic="";
        String strCodOc="";
        int intCon=0;
        int intTipCli=3;
        int intTipMov=-1;
        int intCodItm =0;
        int intCodBod=0;
        int intTipStk=0;
        double dlbCanMov=0.00;
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();
                stmLocAct=conn.createStatement();
                stmLoc01=conn.createStatement();

                //************************************************************************************      
                strSql= "SELECT b.ne_numdoc FROM tbr_cabmovinv AS a " +
                        " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emprel AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                        " WHERE " +
                        " a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+" AND a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText()+" "+
                        " AND a.co_tipdocrel=4"; /* "DEVCOM";"Devolución de compras" */
                rstDocDet = stmLoc.executeQuery(strSql);
                while(rstDocDet.next())
                {
                    intCon=1;
                    strCodOc +=rstDocDet.getString(1)+"\n";
                }
                rstDocDet.close(); rstDocDet=null;
                if(intCon>0)
                {
                    if (( mensajeConf("LAS SIGUIENTES DEVOLUCIONES DE COMPRA SERAN ANULADAS \n"+strCodOc +" \n ¿DESEA SEGUIR CON EL PROCESO DE ANULACION ?")!=javax.swing.JOptionPane.YES_OPTION))
                        return false;
                }                         
                if( !anulaDet_OC(conn, intCodEmpDevVen, intCodLocDevVen, txtCodTipDoc.getText() , txtCodDoc.getText() ) )
                    return false;

                if(!revertirOrdTraAut(conn, intCodEmpDevVen, intCodLocDevVen, txtCodTipDoc.getText() , txtCodDoc.getText() ) )
                   return false;
                //************************************************************************************      

                strSql="UPDATE tbr_cabMovInv SET st_reg='I', st_regrep='M' WHERE co_emp="+intCodEmpDevVen+" AND co_loc="+intCodLocDevVen+" " +
                       "AND  co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtCodDoc.getText();
                stmLoc.executeUpdate(strSql);

                strSql="UPDATE tbm_cabMovInv SET st_reg='I', fe_ultMod="+objParSis.getFuncionFechaHoraBaseDatos()+", co_usrMod="+objParSis.getCodigoUsuario()+", st_regrep='M' "+
                       " WHERE co_emp="+intCodEmpDevVen+" AND co_loc="+intCodLocDevVen+" " +
                       "AND  co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtCodDoc.getText();
                stmLoc.executeUpdate(strSql);


                if(!(txtCodTipDocSol.getText().equals("")))
                {
                    strSql="UPDATE tbm_cabsoldevven SET  st_MerAceIngSis='N' WHERE co_emp="+intCodEmpDevVen+" " +
                           " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText();
                    stmLoc.executeUpdate(strSql);
                }        

                intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCodCli.getText(),conn);


                if(radCan.isSelected())
                {
                    //objInvItm.inicializaObjeto(); //Mateo

                    String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
                                    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                                    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                                    " WHERE a.co_emp="+intCodEmpDevVen+" and a.co_loc="+intCodLocDevVen+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                                    " THEN 'S' ELSE 'N' END AS proconf  ";


                    strSql= " SELECT a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed " +
                            " ,a.co_bod ,a1.tx_nom, abs(a.nd_can) as nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
                            " ,b.st_ser, a.co_reg, a.nd_tot, a.co_regrelsoldevven "+
                            " "+strAux2+" FROM tbm_detmovinv AS a " +
                            " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +         
                            " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+       
                            " WHERE a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+"  AND " +
                            " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText();
                    rstDocDet=stmLoc.executeQuery(strSql);

                    // System.out.println(" dev 1: "+strSql+" intTipCli: "+intTipCli); 

                    while(rstDocDet.next())
                    {
                        String strEstFisBod=rstDocDet.getString("proconf");                          

                        //**************************************************************           
                        if(rstDocDet.getString("st_ser").equals("N"))
                        { 
                            intCodItm=rstDocDet.getInt("co_itm");
                            intCodBod=rstDocDet.getInt("co_bod");
                            dlbCanMov=rstDocDet.getDouble("nd_can");

                            strEstFisBod=rstDocDet.getString("proconf");
                            //objInvItm.generaQueryStock(intCodEmpDevVen, intCodItm, intCodBod, dlbCanMov, -1, "S", strMerIngEgr, strTipIngEgr, -1); //Mateo
                            intTipStk=1; 
                            
                            // Mateo
                            ZafStkInv invStock= new ZafStkInv(objParSis);
                            if(rstDocDet.getString("tx_codalt")!=null  &&   (rstDocDet.getString("tx_codalt").substring((rstDocDet.getString("tx_codalt")).length()-1).equalsIgnoreCase("L") ))
                            {
                                if(!invStock.actualizaInventario(conn, intCodEmpDevVen, INT_ARL_STK_INV_STK, "-", 1, obtenerArrItmInv(conn, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                                          return false;
                                }            
                            }
                            else 
                            {       
                                if(invStock.actualizaInventario(conn, intCodEmpDevVen, INT_ARL_STK_INV_STK_DIS, "-", 1, obtenerArrItmInv(conn, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod)))
                                {
                                    if(!invStock.actualizaInventario(conn, intCodEmpDevVen, INT_ARL_STK_INV_STK, "-", 0, obtenerArrItmInv(conn, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                                          return false;
                                    }
                                }
                                else
                                {
                                    return false;
                                }
                            }
                            //Mateo
                            
                        }

                        //*********************************************************************    
                        if(!(txtCodTipDocSol.getText().equals("")))
                        {  
                            strSql=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis-"+rstDocDet.getDouble("nd_can")+"  WHERE co_emp="+intCodEmpDevVen+" " +
                                   " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+rstDocDet.getString("co_regrelsoldevven")+" ";
                            stmLocAct.executeUpdate(strSql);  
                        }   
                    }
                    rstDocDet.close();
                    rstDocDet=null;  
                    
                    // Mateo
                    /*if(intTipStk==1)
                    {
                        if(!objInvItm.ejecutaActStock(conn, intTipCli))
                          return false;
                        if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
                          return false;
                    }
                    objInvItm.limpiarObjeto(); */ //Mateo  
                }

                if(radDes.isSelected() || radDesEsp.isSelected())
                {
                    strSql= " SELECT a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed " +
                            " ,a.co_bod ,a1.tx_nom, abs(a.nd_can) as nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
                            " ,b.st_ser, a.co_reg, a.nd_tot, a.co_regrelsoldevven "+
                            " FROM tbm_detmovinv AS a " +
                            " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +         
                            " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+       
                            " WHERE a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+"  AND " +
                            " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText();
                    rstDocDet=stmLoc.executeQuery(strSql);

                    while(rstDocDet.next())
                    {
                        strSql=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis-"+rstDocDet.getDouble("nd_can")+"  WHERE co_emp="+intCodEmpDevVen+" " +
                        " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+rstDocDet.getString("co_regrelsoldevven")+" ";
                        stmLocAct.executeUpdate(strSql);  
                    }
                    rstDocDet.close();
                    rstDocDet=null; 
                }

                if(radPre.isSelected())
                {
                    strSql= " SELECT a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed " +
                            " ,a.co_bod ,a1.tx_nom, abs(a.nd_can) as nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
                            " ,b.st_ser, a.co_reg, a.nd_tot, a.co_regrelsoldevven "+
                            " FROM tbm_detmovinv AS a " +
                            " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +         
                            " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+       
                            " WHERE a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+"  AND " +
                            " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText();
                    rstDocDet=stmLoc.executeQuery(strSql);

                    while(rstDocDet.next())
                    {
                        strSql=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis-"+rstDocDet.getDouble("nd_can")+"  WHERE co_emp="+intCodEmpDevVen+" " +
                               " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+rstDocDet.getString("co_regrelsoldevven")+" ";
                        stmLocAct.executeUpdate(strSql);  
                    }
                    rstDocDet.close();
                    rstDocDet=null;  
                }

                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }

    private boolean anulaDet_OC(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc)
    {
        boolean blnRes=false;
        String sql = "";
        try
        {
            sql=" SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel FROM tbr_cabmovinv AS a " +
                " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emprel AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                " WHERE " +
                " a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc+" "+
                " AND a.co_tipdocrel=4";       /* "DEVCOM";"Devolución de compras" */   
            //System.out.println("Paso 1 : "+ sql );
            java.sql.Statement stm=conAnu.createStatement();
            java.sql.ResultSet rst = stm.executeQuery(sql);

            while(rst.next())
            {
                //----------------  PROCESO PARA OC 
                // if(!objUltDocPrint2.Verificar_Doc_Asociado_OC_anu_Fac(conAnu, rst.getString(3),rst.getString(2), strCodTipDoc ))
                //        return false;
                if(!objUltDocPrint.verificarsiesconfirmado(rst.getString(3),rst.getString(2) ))
                    return false;
                if (objUltDocPrint.isPagoDocumento_aso(intCodEmp, intCodLoc,rst.getInt(2), rst.getInt(3) ))
                    return false;
                //----------------------------------

                if(!anulaDet_Itm_OC(conAnu, intCodEmp, intCodLoc, rst.getString("co_tipdocrel") , rst.getString("co_Docrel") ) )
                    return false;          
                if(!anulaDet_Doc_OC(conAnu, intCodEmp, intCodLoc, rst.getString(2) , rst.getString(3) ) )
                    return false;

                objInvItm.limpiarObjeto(); 
            }
            blnRes=true;
        }
        catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
        return blnRes;   
    }
       


 
    private boolean anulaDet_Itm_OC(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc)
    {
        boolean blnRes=false;
        String sql = "", GLO_strArreItm="", strMsgError="";
        int intControl=0,i=0;
        int intTipMov = 1;
        int intTipCli=3;
        int intCodItm =0;
        int intCodBod=0;
        int intTipStk=0;
        double dlbCanMov=0.00;
        try
        {
            // java.util.ArrayList arlActInv=new java.util.ArrayList();
            objInvItm.inicializaObjeto();

            String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
                            " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                            " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                            " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                            " THEN 'S' ELSE 'N' END AS proconf  ";

            sql =" SELECT co_itm, abs(nd_can) as nd_can, co_bod ,tx_codalt ,b.co_cli " +
                 " "+strAux2+" FROM tbm_detmovinv AS a" +
                 " INNER JOIN tbm_cabmovinv as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc)" +
                 " WHERE  a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+"  " +
                 " AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;        

            java.sql.Statement stm=conAnu.createStatement();
            java.sql.ResultSet rst = stm.executeQuery(sql);
            while(rst.next())
            {
                i++;
                if(i==1)
                   intTipCli= objUltDocPrint.ValidarCodigoCliente(rst.getString("co_cli"),conAnu);

                String strEstFisBod=rst.getString("proconf");  
                intCodItm=rst.getInt("co_itm");

                if(objInvItm.isItmServicio(conAnu, intCodItm))
                {
                    intCodBod=rst.getInt("co_bod");
                    dlbCanMov=rst.getDouble("nd_can");
                    
                    //Mateo
                    /*objInvItm.generaQueryStock(intCodEmpDevVen, intCodItm, intCodBod, dlbCanMov, 1, "S", strMerIngEgr, strTipIngEgr, 1);
                    intTipStk=1; */ 
                    
                    ZafStkInv invStock= new ZafStkInv(objParSis);
            
                    if(rst.getString("tx_codalt")!=null  &&   (rst.getString("tx_codalt").substring((rst.getString("tx_codalt")).length()-1).equalsIgnoreCase("L") ))
                    {
                        if(!invStock.actualizaInventario(conAnu, intCodEmpDevVen, INT_ARL_STK_INV_STK, "+", 1, obtenerArrItmInv(conAnu, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                            return false;
                        }            
                    }
                    else
                    {
                        if(invStock.actualizaInventario(conAnu, intCodEmpDevVen, INT_ARL_STK_INV_STK_DIS, "+", 1, obtenerArrItmInv(conAnu, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                            if(!invStock.actualizaInventario(conAnu, intCodEmpDevVen, INT_ARL_STK_INV_STK, "+", 0, obtenerArrItmInv(conAnu, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                                  return false;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                    //Mateo
                    
                }
            }                                            
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            
            //Mateo
            /*if(intTipStk==1)
            {
                if(!objInvItm.ejecutaActStock(conAnu, intTipCli))
                    return false;
                if(!objInvItm.ejecutaVerificacionStock(conAnu, intTipCli ))
                    return false;
            }
            objInvItm.limpiarObjeto();  */
            //Mateo

            blnRes=true;
        }
        catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
        return blnRes;   
    }
       
           


    private boolean anulaDet_Doc_OC(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc)
    {
        boolean blnRes=false;
        String sql = "";
        try
        { 
            String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());      

            sql = " update tbr_cabMovInv set st_reg='I', st_regrep='M' where " +
                  " co_emp="+intCodEmp+" and "+
                  " co_loc = "+intCodLoc+ " and "+  
                  " co_doc = " +strCodDoc+" and "+  
                  " co_tipDoc = " +strCodTipDoc;      
            java.sql.PreparedStatement Pstm = conAnu.prepareStatement(sql);        
            Pstm.executeUpdate();    

            sql=" UPDATE tbm_cabMovInv SET " +
                 " st_reg  = 'I', fe_ultMod ='"+strFecSis+"', co_usrMod = "+objParSis.getCodigoUsuario()+" " +
                 " WHERE co_emp = "+intCodEmp+" AND co_loc = "+intCodLoc+" AND "+  
                 " co_doc = "+strCodDoc+" AND co_tipDoc = "+strCodTipDoc;
            Pstm = conAnu.prepareStatement(sql);        
            Pstm.executeUpdate();     

            if (!objAsiDia.anularDiario(conAnu, intCodEmp, intCodLoc, Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)))
                return false;

            blnRes=true;
        }
        catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
        return blnRes;   
    }



    @Override
    public void clickAceptar() {
        setEstadoBotonMakeFac();
    }



    @Override
    public void clickAnterior() 
    {
        try
        {
            if(rstCab != null ) 
            {
                abrirCon();
                if(!rstCab.isFirst()) 
                {
                    if(blnHayCam) 
                    {
                        if(isRegPro()) 
                        {
                           rstCab.previous();
                           refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    else 
                    {
                          rstCab.previous();
                          refrescaDatos(CONN_GLO, rstCab); 
                    }
                }
                CerrarCon();
            }
        }
        catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }


    @Override
    public void clickAnular() {
            
    }


    @Override
    public void clickConsultar() 
    {
        clnTextos();
        noEditable(false);
        java.awt.Color colBack;
        colBack = txtNumDocSol.getBackground();
        bloquea(txtNumDocSol, colBack, false);
        butBusNumDocSol.setEnabled(false);
        cargarTipoDoc (2);
    }

    @Override
    public void clickEliminar() {
//          noEditable(false);
    }





@Override
public void clickFin(){
try{
if(rstCab != null ){
 abrirCon();
  if(!rstCab.isLast()){
   if (blnHayCam){
    if(isRegPro()){
       rstCab.last();
       refrescaDatos(CONN_GLO, rstCab);  
   }}else{
        rstCab.last();
        refrescaDatos(CONN_GLO, rstCab);
  }}
 CerrarCon();
}}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
 catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}






@Override
public void clickInicio(){
try{
if(rstCab != null ){
   abrirCon();
 if(!rstCab.isFirst()) {
  if(blnHayCam){
   if(isRegPro()){
      rstCab.first();
      refrescaDatos(CONN_GLO, rstCab);  
    }}else{
        rstCab.first();
       refrescaDatos(CONN_GLO, rstCab);  
    }}
  CerrarCon();
}}catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
}




    @Override
    public void clickInsertar() 
    {
        try
        {
            clnTextos();
            noEditable(false);
            Color colBack;
            colBack = txtCodDoc.getBackground();
            txtCodDoc.setEditable(false);
            txtCodDoc.setBackground(colBack);
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            Date dateObj =datFecAux;
            Calendar calObj = Calendar.getInstance();
            calObj.setTime(dateObj);

            txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH)+1, calObj.get(Calendar.YEAR));
            //txtFecDoc.setHoy();

            cargarTipoDoc(1);

            // txtCodRes.setText(""+ objParSis.getCodigoUsuario() );
            // cargarResponsable();

            //BuscarVendedor("a.co_usr",txtCodRes.getText(),0);

            if(rstCab!=null) 
            {
                rstCab.close();
                rstCab=null;
            }

        }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
    }




    public void setEstadoBotonMakeFac(){
        switch(getEstado()) {
            case 'l'://Estado 0 => Listo
                break;
            case 'x'://Estado click modificar
                break;
            case 'c'://Estado Consultar
                break;
            case 'y':
                break;
            case 'z':
                break;
            default:
                break;
        }
    }



@Override
public void clickSiguiente(){ 
try{
if(rstCab != null ){
   abrirCon();
  if(!rstCab.isLast()) {
   if(blnHayCam || objTblMod.isDataModelChanged()) {
      if(isRegPro()) {
          rstCab.next();
          refrescaDatos(CONN_GLO, rstCab);
      }}else{
           rstCab.next();
           refrescaDatos(CONN_GLO, rstCab);
   }}
CerrarCon();
}}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}

@Override
public boolean eliminar() {
boolean blnRes=false;
java.sql.Connection  conn;
try{
 conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
 if(conn!=null){
   conn.setAutoCommit(false);

    strAux=objTooBar.getEstadoRegistro();
    if (strAux.equals("Eliminado")) {
        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
        blnRes=true;
    }

   if(!blnRes){ 
//    if(eliminarReg(conn)){
        conn.commit();
        blnRes=true;
        objTooBar.setEstadoRegistro("Eliminado");
        blnHayCam=false;
//    }else conn.rollback();
   }else blnRes=false;

  conn.close();
  conn=null;
}}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
return blnRes;
}



//
//private boolean eliminarReg(java.sql.Connection conn){
//boolean blnRes=false;
//java.sql.Statement stmLoc;
//String strSql=""; 
//try{
// if(conn!=null){
//    stmLoc=conn.createStatement();
//    strSql="UPDATE tbm_cabRecMerSolDevVen SET st_reg='E' WHERE co_emp="+intCodEmpDevVen+" AND co_loc="+intCodLocDevVen+" " +
//     "AND  co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtCodDoc.getText();
//    stmLoc.executeUpdate(strSql);
//
//    for(int i=0; i<tblDat.getRowCount(); i++){
//      if(objParSis.getCodigoMenu() == 1898){    
//         strSql="UPDATE tbm_detsoldevven SET  " +
//         " nd_canrec=nd_canrec-"+(tblDat.getValueAt(i, INT_TBL_CANREC_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANREC_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANREC_ORI).toString()) )+",  " +
//         " nd_canNunRec="+(tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANTOTNUNREC_ORI).toString() ) )+" " +
//         " ,nd_canRevBodAce=nd_canRevBodAce-"+(tblDat.getValueAt(i, INT_TBL_CANACE_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString() ) )+" " +
//         " ,nd_canRevBodRec=nd_canRevBodRec-"+(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString() ) )+" " +
//         " WHERE co_emp="+intCodEmpDevVen+" " +
//         " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREG).toString();
//      }  
//     if(objParSis.getCodigoMenu() == 1898){    
//          strSql="UPDATE tbm_detsoldevven SET  " +
//         " nd_canrevtecace=nd_canrevtecace-"+(tblDat.getValueAt(i, INT_TBL_CANACE_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString() ) )+" " +
//         " ,nd_canrevtecrec=nd_canrevtecrec-"+(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString() ) )+" " +
//         " WHERE co_emp="+intCodEmpDevVen+" " +
//         " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREG).toString();
//     } 
//      if(objParSis.getCodigoMenu() == 1908){    
//          strSql="UPDATE tbm_detsoldevven SET  " +
//         " nd_canrevtecace=nd_canrevtecace-"+(tblDat.getValueAt(i, INT_TBL_CANACE_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANACE_ORI).toString() ) )+" " +
//         " ,nd_canrevtecrec=nd_canrevtecrec-"+(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANRCH_ORI).toString() ) )+" " +
//         " WHERE co_emp="+intCodEmpDevVen+" " +
//         " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREG).toString();
//     }  
//      //System.out.println(">>"+ strSql );
//      stmLoc.executeUpdate(strSql);
// }
//    
//    
//   stmLoc.close();
//   stmLoc=null;
// blnRes=true;
//}}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
//return blnRes;
//}
//



          

 

//
//private boolean validaCampos(){
//    if(txtDesCorTipDoc.getText().equals("") ){
//    tabGen.setSelectedIndex(0);
//    MensajeInf("El campo << Tipo Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
//    txtDesCorTipDoc.requestFocus();
//    return false;
//    }
//
//    if(!txtFecDoc.isFecha()){
//      tabGen.setSelectedIndex(0);
//      MensajeInf("El campo << Fecha Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
//      txtFecDoc.requestFocus();
//      return false;
//    }
//
//
//    if(txtNumDoc.getText().equals("") ){
//    tabGen.setSelectedIndex(0);
//    MensajeInf("El campo << Número de Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
//    txtNumDoc.requestFocus();
//    return false;
//    }
//
//return true;  
//}
//
//



          
  
  
 
private boolean verificaEstConf(java.sql.Connection conn){
  boolean blnRes=true;
  String strSql="";
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();
     
     strSql="SELECT st_coninv FROM tbm_cabmovinv AS a " +
     " WHERE co_emp="+intCodEmpDevVen+" " +
     " AND co_loc="+txtCodLocRelFac.getText()+" AND co_tipdoc="+txtCodTipDocRelFac.getText()+" AND co_Doc="+txtCodDocRelFac.getText()+" AND st_coninv <> 'C' ";
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         blnRes=false;
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
   catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
 return blnRes;
}



    @Override
    public boolean insertar() 
    {
        if (!insertarReg())
            return false;
        return true;
    }
    

   /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        java.sql.Connection conIns; 
        int intGenCruAut=1;
        try
        {
            conIns =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            conIns.setAutoCommit(false);
            if(conIns!=null)
            {
                String strFecha = getFecDoc();
                stbDocRelEmpLoc = new StringBuffer();
                intDocRelEmpLoc=0;
                txtsql.setText("");
                blnMerFal=false;
                arlItmRec = new java.util.ArrayList();
                arlItmRec.clear();
                if(getUltCodDoc(conIns)){
                    if (insertarCabReg(conIns, strFecha)) {
                        if (insertarDetReg(conIns)) {
                            //if (insertarPagReg(conIns, intGenCruAut, strFecha)) {
                            if (insertarPagRegBig(conIns, intGenCruAut, strFecha)) {
                                if (objInvItm._getExiItmSer(conIns, intCodEmpDevVen, intCodLocDevVen, Integer.parseInt( txtCodTipDoc.getText() ), intCodDocDevVen)) {
                                    if (objAsiDia.insertarDiario(conIns, intCodEmpDevVen, intCodLocDevVen, Integer.parseInt( txtCodTipDoc.getText() ), String.valueOf(intCodDocDevVen), txtNumDoc.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"))) {
                                        if (consultarTransAut(conIns, null, intCodEmpDevVen, Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDocSol.getText()), Integer.parseInt(txtCodDocSol.getText()))) {
                                            if (insertar_tbmCabOrdConInv(conIns)) {
                                                if (insertar_tbmDetOrdConInv(conIns)) {
                                                    if (actualizarUtlDocOcAut(conIns)) {
                                                        if (asignaSecEmpGrp(conIns, stbDocRelEmpLoc)) {
															//RECOSTEO CMATEO 20-NOV-2017
                                                            if(objUti.costearDocumento(this, objParSis, conIns, intCodEmpDevVen, intCodLocDevVen, Integer.parseInt(txtCodTipDoc.getText()), intCodDocDevVen)){                                                            
                                                                if (actualizarCabSegMovInv(conIns, intCodEmpDevVen, intCodLocDevVen, Integer.parseInt( txtCodTipDoc.getText() ), intCodDocDevVen)) {
                                                                    if (chkVolFac.isSelected()) 
                                                                    {
                                                                        //if(generaFacturaVolverFacturar(conIns))
                                                                        int intCodUsrIngFac=obtenerUsrIngFac(intCodEmpDevVen, Integer.parseInt(txtCodLoc.getText()), 
                                                                                                                            Integer.parseInt(txtCodTipDocSol.getText()), Integer.parseInt(txtCodDocSol.getText()), 
                                                                                                                            conIns);	                
                                                                        
                                                                        if(generaFacturaVolverFacturarBig(conIns, intCodUsrIngFac))
                                                                        {
                                                                            conIns.commit();
                                                                            blnRes=true;
                                                                        }
                                                                        else
                                                                        {
                                                                            conIns.rollback();
                                                                            blnRes=false;
                                                                        }  
                                                                    }
                                                                    else
                                                                    {
                                                                        conIns.commit();
                                                                        blnRes=true;
                                                                        enviarPulsoFacturacionElectronica();
                                                                    }
                                                                }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - Actualizar seguimiento"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));} 
													   		   //RECOSTEO CMATEO 20-NOV-2017
                                                            }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - Costear documento"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                                                        }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - asignaSecEmpGrp"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                                                    }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - actualizarUtlDocOcAut"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                                                }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - insertar_tbmDetOrdConInv"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                                            }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - insertar_tbmCabOrdConInv"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                                        }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - consultarTransAut"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                                    }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - insertarDiario"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                                }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg -_getExiItmSer"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));} 
                            }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - insertarPagRegBig"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                        }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - insertarDetReg"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                    }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - insertarCabReg"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}
                }else {conIns.rollback();objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error en metodo insertarReg - getUltCodDoc"+ intCodEmpDevVen+" "+intCodLocDevVen+" "+Integer.parseInt(txtCodTipDocSol.getText())+" "+Integer.parseInt(txtCodDocSol.getText()));}

                conIns.close();
                conIns=null;
            }
        }
        catch(java.sql.SQLException Evt) {      blnRes=false;       objUti.mostrarMsgErr_F1(this, Evt);        }
        catch(Exception Evt)  {        blnRes=false;          objUti.mostrarMsgErr_F1(this, Evt);        }
        return blnRes;    
    }
    
    
    
    
    
    private void recostearItmGrp() 
    {
        java.sql.Connection conLoc;
        try 
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            if(conLoc!=null)
            {
                for(int i=0; i<tblDat.getRowCount(); i++)
                {
                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null )
                    {
                        objUti.recostearItm2009DesdeFecha( this, objParSis, conLoc,  intCodEmpDevVen, tblDat.getValueAt(i,INT_TBL_CODITM).toString(), txtFecDoc.getText(), "dd/MM/yyyy");
                    }
                }
            }
            conLoc.close();
            conLoc=null;  
        }
        catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }




    private boolean asignaSecEmpGrp(java.sql.Connection conIns, StringBuffer stbDocRelSec)
    {
        //boolean blnRes=false;
        boolean blnRes = true;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc, stmLoc01;
        int intSecEmp = 0, intSecGrp = 0;
        try 
        {
            if(conIns!=null)
            {
                stmLoc=conIns.createStatement();
                stmLoc01=conIns.createStatement();
                strSQL="SELECT * FROM( "+stbDocRelSec+" ) AS x";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next())
                {
                    intSecEmp=objUltDocPrint.getNumSecDoc(conIns, rstLoc.getInt("coemp") );
                    intSecGrp=objUltDocPrint.getNumSecDoc(conIns, objParSis.getCodigoEmpresaGrupo() );

                    strSQL=" UPDATE tbm_cabmovinv SET ne_SecEmp="+intSecEmp+", ne_SecGrp="+intSecGrp+
                           " WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" " +
                           " AND co_tipdoc="+rstLoc.getInt("cotipdoc")+" AND  co_doc="+rstLoc.getInt("codoc")+"";
                    stmLoc01.executeUpdate(strSQL);
                }
                rstLoc.close();
                rstLoc=null;

                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;
            }
            stbDocRelEmpLoc = null;
        }
        catch(java.sql.SQLException Evt){       blnRes=false;       objUti.mostrarMsgErr_F1(this, Evt);    }
        catch(Exception Evt){      blnRes=false;        objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }


    private boolean cargarDatIng( int intCodDoc)
    {  
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc; 
        String strSql="";
        Vector vecData;
        // double dblCanTotBodDev=0;
        // double dblCanTotDev=0;
        // double dblCanTotIngSis=0;
        // String strMerAceIngSis="N";
        // int intHayDat=0;
        try
        {
            conLoc =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                vecData = new Vector();   

                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(a.tx_codalt), length(a.tx_codalt) ,1)) IN ( " +
                                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                                " WHERE a.co_emp="+intCodEmpDevVen+" and a.co_loc="+intCodLocDevVen+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                                " THEN 'S' ELSE 'N' END AS proconf  ";


                strSql= " SELECT a2.co_locrel, a2.co_tipdocrel ,a2.co_Docrel, a2.co_regrel,  " +
                        " x.co_locrel as colocot, x.co_tipdocrel as cotipdocot ,x.co_Docrel as codocot, x.co_regrel as coregot, "+        
                        " a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed " +
                        " ,a.co_bod ,a1.tx_nom, a.nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
                        " ,b.st_ser, a.co_reg, a.co_regrelsoldevven,  a.nd_tot ,a3.nd_canRevBodAce, a3.nd_cansolnodevprv, a3.nd_CanAceIngSis, a3.st_devAutMerPrv  "+
                        " ,a3.nd_candevprv as nd_candevprv_1 "+
                        " ,case when a3.nd_cansolnodevprv > 0 then  a3.nd_canRevBodAce  else  0  end  as nd_cansolnodevprv_1  "+
                        " ,a3.nd_candev, a3.nd_candevprv, a3.nd_cannodevprv "+
                        " ,( SELECT sum(s.nd_CanAceIngSis) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotIngSis "+
                        " ,( SELECT sum(s.nd_canRevBodAce+ s.nd_canvolfac +  s.nd_candevprv ) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotBodAce "+
                        " ,( SELECT sum(s.nd_candev) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotCanDev "+
                        " "+strAux2+" "+
                        " FROM tbm_detmovinv AS a " +
                        " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +         
                        " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+       
                        " LEFT JOIN tbr_detmovinv  AS a2 ON(a2.co_emp=a.co_emp and a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc and a2.co_reg=a.co_reg and a2.co_tipdocrel=4)   "+  /* "DEVCOM";"Devolución de compras" */
                        " LEFT JOIN tbr_detmovinv  AS x ON(x.co_emp=a.co_emp and x.co_loc=a.co_loc AND x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_reg=a.co_reg and x.co_tipdocrel=116)    "+   /* "OTAUTO";"Ordenes de trabajo automáticas" */
                        "  LEFT JOIN tbm_detsoldevven  AS a3 ON(a3.co_emp=a.co_emp and a3.co_loc=a.co_locrelsoldevven AND a3.co_tipdoc=a.co_tipdocrelsoldevven and a3.co_doc=a.co_docrelsoldevven and a3.co_reg=a.co_regrelsoldevven ) "+
                        " WHERE a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+"  AND " +
                        " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+intCodDoc;


                //System.out.println("cargarDatIng: "+ strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();

                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
                    vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
                    vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
                    vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed") );
                    vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod") );
                    vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
                    vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_canorg") );
                    vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni") );

                    vecReg.add(INT_TBL_PREDEV, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes") );
                    vecReg.add(INT_TBL_CANDES, rstLoc.getString("nd_can") );

                    vecReg.add(INT_TBL_IVA, (rstLoc.getString("st_ivacom").equals("S")?true:false));
                    vecReg.add(INT_TBL_TOT, rstLoc.getString("nd_tot") );
                    vecReg.add(INT_TBL_CODITMACT, rstLoc.getString("co_itmact") );
                    vecReg.add(INT_TBL_COSTO, rstLoc.getString("nd_cosunigrp") );
                    vecReg.add(INT_TBL_CODALT2, rstLoc.getString("tx_codalt2") );
                    vecReg.add(INT_TBL_ITMSER, rstLoc.getString("st_ser") );
                    vecReg.add(INT_TBL_CANDIFDEV, "" );
                    vecReg.add(INT_TBL_CODREGSOL, rstLoc.getString("co_regrelsoldevven") );

                    vecReg.add(INT_TBL_CODLOC_OC, rstLoc.getString("co_locrel") );
                    vecReg.add(INT_TBL_CODTIP_OC, rstLoc.getString("co_tipdocrel") );
                    vecReg.add(INT_TBL_CODDOC_OC, rstLoc.getString("co_Docrel") );
                    vecReg.add(INT_TBL_CODREG_OC, rstLoc.getString("co_regrel") );
                    vecReg.add(INT_TBL_CODREGDEV, rstLoc.getString("co_reg") );
                    vecReg.add(INT_TBL_CANDEV_ORI, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_CANDEVACEBOD, rstLoc.getString("nd_canRevBodAce") );
                    vecReg.add(INT_TBL_ACEINGSIS, rstLoc.getString("nd_CanAceIngSis") );

                    vecReg.add(INT_TBL_SUMTOTINGSIS, rstLoc.getString("sumTotIngSis") );
                    vecReg.add(INT_TBL_SUMTOTBODACE, rstLoc.getString("sumTotBodAce") );

                    vecReg.add(INT_TBL_CANDEVPREDES_SOL, rstLoc.getString("nd_candev") );
                    vecReg.add(INT_TBL_CANDEVPREDES_ORI, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_SUMTOTCANDEV, rstLoc.getString("sumTotCanDev") );
                    vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));
                    vecReg.add(INT_TBL_DEVPRVAUT, rstLoc.getString("st_devAutMerPrv"));

                    vecReg.add(INT_TBL_SEDEVPRV, rstLoc.getString("nd_candevprv_1"));   // nd_candevprv
                    vecReg.add(INT_TBL_NODEVPRV, rstLoc.getString("nd_cannodevprv"));

                    vecReg.add(INT_TBL_CODLOC_OT, rstLoc.getString("colocot") );
                    vecReg.add(INT_TBL_CODTIP_OT, rstLoc.getString("cotipdocot") );
                    vecReg.add(INT_TBL_CODDOC_OT, rstLoc.getString("codocot") );
                    vecReg.add(INT_TBL_CODREG_OT, rstLoc.getString("coregot") );
                    vecReg.add(INT_TBL_CANSOLSTOCK, rstLoc.getString("nd_cansolnodevprv_1") );  //nd_cansolnodevprv

                    vecReg.add(INT_TBL_CODCTAEGR, null );
                    vecReg.add(INT_TBL_TXTCTAEGR, null );
                    vecReg.add(INT_TBL_DESCTAEGR, null );    
                    /*vecReg.add(INT_TBL_CANNUNRECFAC,null);
                    vecReg.add(INT_TBL_CANFAC,null);*/

                   //dblCanTotDev  =    objUti.redondear( rstLoc.getDouble("sumTotCanDev") ,2 );
                   //dblCanTotBodDev  =  objUti.redondear( rstLoc.getDouble("sumTotBodAce") ,2 );  // sumTotBodAce
                   //dblCanTotIngSis =   objUti.redondear( rstLoc.getDouble("sumTotIngSis") ,2 );
                   //intHayDat=1;
                    vecData.add(vecReg); 
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                rstLoc.close(); 
                rstLoc=null;

              //***************************************************************************
       //       if(intHayDat==1){
       //         if(radCan.isSelected()){
       //           if(glo_strImpGui.equals("N"))  strMerAceIngSis="S";
       //           else { if(dblCanTotBodDev==dblCanTotIngSis) strMerAceIngSis="S";  }
       //         }else
       //              if(dblCanTotDev==dblCanTotIngSis) strMerAceIngSis="S";
       //
       //            strSql="UPDATE tbm_cabsoldevven SET  st_MerAceIngSis='"+strMerAceIngSis+"' WHERE co_emp="+intCodEmpDevVen+" " +
       //            " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText();
       //            //System.out.println(" "+ strSql );
       //            stmLoc.executeUpdate(strSql);
       //            stmLoc.close();
       //            stmLoc=null;
       //            conLoc.commit();
       //        }
              //***************************************************************************

                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }





 
    public boolean actualizarUtlDocOcAut(java.sql.Connection conIns)
    {  
        boolean blnRes=false;
        java.sql.Statement stmLoc, stmLocOc;
        java.sql.ResultSet rstLoc, rstLocOc; 
        String strSQL="";
        int intNumDoc_OC=0;
        try
        {
            if (conIns!=null)
            {

                stmLoc=conIns.createStatement();
                stmLocOc=conIns.createStatement();

                // STR_ESTREG="I";
                //conIns.setAutoCommit(true);

                //JOptionPane oppMsg = new JOptionPane();
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";

                if(radCan.isSelected())
                {
                    strSQL= " SELECT co_locrel,co_tipdocrel,co_docrel FROM tbr_cabmovinv  " +
                            " WHERE co_emp="+intCodEmpDevVen+" AND co_loc="+intCodLocDevVen+" AND co_tipdoc="+ txtCodTipDoc.getText() +"" +
                            " AND co_doc="+intCodDocDevVen+" AND co_tipdocrel=4 AND co_emprel="+intCodEmpDevVen; /* "DEVCOM";"Devolución de compras" */
                    System.out.println("ZafVen28.actualizarUtlDocOcAut (1) " + strSQL);
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next())
                    {
                        strSQL=" SELECT ne_ultDoc, st_predoc  FROM tbm_cabTipDoc  " +
                               " WHERE co_emp = " +intCodEmpDevVen+ 
                               " AND co_loc = "+rstLoc.getString("co_locrel") +
                               " AND co_tipDoc = "+rstLoc.getString("co_tipdocrel");
                        System.out.println("ZafVen28.actualizarUtlDocOcAut (2) " + strSQL);
                        rstLocOc = stmLocOc.executeQuery(strSQL);
                        if(rstLocOc.next())  
                            intNumDoc_OC = rstLocOc.getInt("ne_ultDoc");
                        intNumDoc_OC++;
                        rstLocOc.close(); 
                        rstLocOc=null;

                        strSQL= " UPDATE tbm_cabTipDoc SET ne_ultDoc ="+intNumDoc_OC+
                                " WHERE  co_emp="+intCodEmpDevVen + "  "+  
                                " AND co_loc = "+rstLoc.getString("co_locrel")+" "+  
                                " AND co_tipDoc = " +rstLoc.getString("co_tipdocrel");      
                        System.out.println("ZafVen28.actualizarUtlDocOcAut (3) " + strSQL);
                        stmLocOc.executeUpdate(strSQL);

                        strSQL = " UPDATE tbm_cabmovinv SET ne_numdoc="+intNumDoc_OC+"  WHERE co_emp="+intCodEmpDevVen+"  " +
                                 " AND co_loc="+rstLoc.getString("co_locrel")+" AND co_tipdoc="+rstLoc.getString("co_tipdocrel")+" AND co_doc="+rstLoc.getString("co_docrel");
                        System.out.println("ZafVen28.actualizarUtlDocOcAut (4) " + strSQL); 
                        stmLocOc.executeUpdate(strSQL); 

                        if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");

                        stbDocRelEmpLoc.append(" SELECT "+intCodEmpDevVen+" AS COEMP, "+rstLoc.getString("co_locrel")+" AS COLOC , "+rstLoc.getString("co_tipdocrel")+" AS COTIPDOC, "+rstLoc.getString("co_docrel")+" AS CODOC ");
                        intDocRelEmpLoc=1;

//                        strMsg="Imprima\n la devolución de compra numero : " + intNumDoc_OC;
//                        JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE); 

                    }
                    rstLoc.close(); 
                    rstLoc=null;
                    stmLocOc.close();
                    stmLocOc=null;
                    stmLoc.close();
                    stmLocOc=null;

                   // calcularSaldo(conIns);
                }         

                //objeto.setEstado('w');
                //consultar2();                  
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }       

 /**
  * MODIFICADO EFLORESA 
  * CRUCE AUTOMATICO DE DOCUMENTOS
  * 
  */      
 public boolean insertarPagReg(Connection conIns ,int intGenCruAut, String strFecha ){
     boolean blnRes=false;
     Statement stmLoc, stmLocGenCru;
     ResultSet rstLoc, rstLocGenCru; 
     String strSQL="";
     String strFecSis="";
     String VarN = "N";
     String VarA = "A";
     double dblBaseDePagos=0; 
     int VarC = 0;
     int intTipDocCru=90;
     int intCodDocCru=0;
     int intSecc=1;
     int intCruAut = intGenCruAut;
     double dblMonPag=0;
     double dblTotPag=0;
     double dblTotMonPag=0;
     double dblRes=0;
     double dblAbono=0;
     double dblMonto = 0;
     try{
        if (conIns!=null){
             
            stmLoc=conIns.createStatement();
            stmLocGenCru=conIns.createStatement();
            
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            //dblBaseDePagos = Double.parseDouble(txtTot.getText()); // - dblValRet_1 - dblValRet_30;
            dblBaseDePagos = Double.parseDouble(txtTotSinCom.getText()); // - dblValRet_1 - dblValRet_30;
             
            strSQL = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep ) " +
            " values("+ intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+", 1 ,"+
            VarC+",'"+strFecha+"',"+dblBaseDePagos+","+VarC+","+VarC+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I')"; 
            System.out.println("ZafVen28.insertarPagReg (1) " + strSQL);
            stmLoc.executeUpdate(strSQL);
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/
            
            if(intCodEmpDevVen==2 && intCodLocDevVen==4){
                if (Double.parseDouble(txtComSol.getText())>0){
                    strSQL = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep, tx_tipreg ) " +
                    " values("+ intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+", 2 ,"+
                    VarC+",'"+strFecha+"',"+txtComSol.getText()+","+VarC+","+Double.parseDouble(txtComSol.getText())*(-1)+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I','S')"; 
                    System.out.println("ZafVen28.insertarPagReg (1) " + strSQL);
                    stmLoc.executeUpdate(strSQL);

                    Date datFecServ=objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos());
                    ZafGenDocCobAut zafGenComSol= new ZafGenDocCobAut(objParSis, this);
                    zafGenComSol.generaDocumentoCobroAutomatico(conIns,intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(txtCodTipDoc.getText()), intCodDocDevVen, datFecServ, 2);
                }
            }            
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/
            


            strSQL = "select sum(abs(mo_pag)) as mo_pag, sum(abs(nd_abo)) as nd_abo, (sum(abs(mo_pag)) - sum(abs(nd_abo))) as dif from tbm_pagmovinv where co_Emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and co_tipdoc="+txtCodTipDocRelFac.getText()+" and co_doc="+txtCodDocRelFac.getText()+" AND st_reg IN('A','C') and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END) ";
            System.out.println("ZafVen28.insertarPagReg (2) " + strSQL);
            rstLoc = stmLoc.executeQuery(strSQL);
            if (rstLoc.next()){
                dblAbono = objUti.parseDouble(rstLoc.getDouble("nd_abo"));
                dblMonto = objUti.parseDouble(rstLoc.getDouble("mo_pag"));
            }
            
            if (dblAbono > 0) {
                intCruAut=0; // si tiene algun pago no se hace el cruce automatico.                
            }
            else {
                if (dblMonto > dblBaseDePagos ) {
                    intCruAut = 0; // si la nota de crédito es parcial no se hace el cruce automático.
                }
            }
            dblTotMonPag=dblBaseDePagos;
            dblTotPag = dblBaseDePagos;
              //**************************************************************************************
               if(intCruAut==1){ 
                   intCodDocCru = objUltDocPrint.getCodDocTbmCabPag(conIns, intTipDocCru);
                   strSQL ="INSERT INTO TBM_CABPAG(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, co_cli, tx_nomcli, nd_mondoc, st_reg, fe_ing , co_usring, st_regrep, st_imp)" +
                   " VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", "+intTipDocCru+", "+intCodDocCru+" " +
                   ", '"+strFecha+"', "+intCodDocCru+", "+txtCodCli.getText()+", '"+txtNomCli.getText()+"',"+dblBaseDePagos+",'A','"+ strFecSis +"',"+objParSis.getCodigoUsuario()+",'I','P')";
                   System.out.println("ZafVen28.insertarPagReg (3) " + strSQL);
                   stmLoc.executeUpdate(strSQL);

                    strSQL ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                    " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", " +
                    " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+" "+
                    " ,1, "+dblBaseDePagos*-1+","+txtCodTipDoc.getText()+")";
                    System.out.println("ZafVen28.insertarPagReg (4) " + strSQL);
                    stmLoc.executeUpdate(strSQL);

                   strSQL ="select * from tbm_pagmovinv where co_Emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and co_tipdoc="+txtCodTipDocRelFac.getText()+" and co_doc="+txtCodDocRelFac.getText()+" AND st_reg IN('A','C') and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END)";
                   System.out.println("ZafVen28.insertarPagReg (5) " + strSQL);
                   rstLocGenCru = stmLoc.executeQuery(strSQL);
                   //System.out.println(""+ strSQL );
                   while(rstLocGenCru.next()){
                       intSecc++;
                       
                       dblRes = objUti.redondear((objUti.redondear(dblTotPag, 2) - objUti.redondear((objUti.parseDouble(rstLocGenCru.getDouble("mo_pag")) * -1), 2)), 2); 
                       if (objUti.redondear(dblTotPag, 2) >= objUti.redondear((objUti.parseDouble(rstLocGenCru.getDouble("mo_pag")) * -1), 2) ) {
                           dblMonPag = (objUti.parseDouble(rstLocGenCru.getDouble("mo_pag")) * -1);                           
                       }else{
                           dblMonPag = dblTotPag;
                       }
                       
                       //System.out.println(""+ intSecc );
                       /*strSQL ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                       " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", " +
                       " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+rstLocGenCru.getString("co_loc")+","+rstLocGenCru.getString("co_tipdoc")+","+rstLocGenCru.getString("co_doc")+" "+
                       " ,"+rstLocGenCru.getString("co_reg")+",abs("+rstLocGenCru.getDouble("mo_pag")+"),"+rstLocGenCru.getString("co_tipdoc")+")";*/

                       /*strSQL ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag) where " +
                       "  co_emp="+rstLocGenCru.getString("co_emp")+" and co_loc="+rstLocGenCru.getString("co_loc")+" and  co_tipdoc="+rstLocGenCru.getString("co_tipdoc")+" and co_doc="+rstLocGenCru.getString("co_doc")+" and co_reg="+rstLocGenCru.getString("co_reg");*/
                       if (dblTotPag > 0) {
                            strSQL ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                            " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", " +
                            " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+rstLocGenCru.getString("co_loc")+","+rstLocGenCru.getString("co_tipdoc")+","+rstLocGenCru.getString("co_doc")+" "+
                            " ,"+rstLocGenCru.getString("co_reg")+",abs("+dblMonPag+"),"+rstLocGenCru.getString("co_tipdoc")+")";
                            System.out.println("ZafVen28.insertarPagReg (6) " + strSQL);
                            stmLocGenCru.executeUpdate(strSQL);

                            strSQL=" UPDATE tbm_pagmovinv SET nd_abo=abs(" + dblMonPag +  ") "
                                    + " where co_emp="+rstLocGenCru.getString("co_emp")+" and co_loc="+rstLocGenCru.getString("co_loc")+" and  co_tipdoc="+rstLocGenCru.getString("co_tipdoc")+" and co_doc="+rstLocGenCru.getString("co_doc")+" and co_reg="+rstLocGenCru.getString("co_reg");
                            System.out.println("ZafVen28.insertarPagReg (7) " + strSQL);
                            stmLocGenCru.executeUpdate(strSQL);

                       }
                       
                       dblTotPag = dblRes;
                      // System.out.println(""+ strSQL );
                    }
                   rstLocGenCru.close();
                   rstLocGenCru=null;
                   
                    strSQL ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag)*-1 where " +
                    "  co_emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and  co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDocDevVen+" and co_reg=1";
                    /*strSQL ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag)*-1 where " +
                    "  co_emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and  co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDocDevVen+" and co_reg=1";*/
                    System.out.println("ZafVen28.insertarPagReg (8) " + strSQL);
                    stmLoc.executeUpdate(strSQL);
             }      
           //**************************************************************************************
           
/*AGREGADO POR COMPENSACION SOLIDARIA*/
            
            /*if(intCodEmpDevVen==2 && intCodLocDevVen==4){
                if (Double.parseDouble(txtComSol.getText())>0){
                    strSQL = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep, tx_tipreg ) " +
                    " values("+ intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+", 2 ,"+
                    VarC+",'"+strFecha+"',"+txtComSol.getText()+","+VarC+","+txtComSol.getText()+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I','S')"; 
                    System.out.println("ZafVen28.insertarPagReg (1) " + strSQL);
                    stmLoc.executeUpdate(strSQL);

                    Date datFecServ=objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos());
                    ZafGenDocCobAut zafGenComSol= new ZafGenDocCobAut(objParSis, this);
                    zafGenComSol.generaDocumentoCobroAutomatico(conIns,intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(txtCodTipDoc.getText()), intCodDocDevVen, datFecServ, 2);
                }
            }        */    
            
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/           
        stmLoc.close();
        stmLoc=null;
        stmLocGenCru.close();
        stmLocGenCru=null;
        blnRes=true;
           
      }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
       catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
   return blnRes;         
 }
 


public boolean insertarPagRegBig(Connection conIns ,int intGenCruAut, String strFecha ){
     boolean blnRes=false;
     Statement stmLoc, stmLocGenCru;
     ResultSet rstLoc, rstLocGenCru; 
     String strSQL="";
     String strFecSis="";
     String VarN = "N";
     String VarA = "A";
     BigDecimal bigBaseDePagos=BigDecimal.ZERO; 
     int VarC = 0;
     int intTipDocCru=90;
     int intCodDocCru=0;
     int intSecc=1;
     int intCruAut = intGenCruAut;
     BigDecimal bigMonPag=BigDecimal.ZERO;
     BigDecimal bigTotPag=BigDecimal.ZERO;
     BigDecimal bigTotMonPag=BigDecimal.ZERO;
     BigDecimal bigRes=BigDecimal.ZERO;
     BigDecimal bigAbono=BigDecimal.ZERO;
     BigDecimal bigMonto = BigDecimal.ZERO;
     try{
        if (conIns!=null){
             
            stmLoc=conIns.createStatement();
            stmLocGenCru=conIns.createStatement();
            
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            //dblBaseDePagos = Double.parseDouble(txtTot.getText()); // - dblValRet_1 - dblValRet_30;
            //dblBaseDePagos = Double.parseDouble(txtTotSinCom.getText()); // - dblValRet_1 - dblValRet_30;
            bigBaseDePagos = new BigDecimal(txtTotSinCom.getText()); // - dblValRet_1 - dblValRet_30;
             
            strSQL = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep ) " +
            " values("+ intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+", 1 ,"+
            //VarC+",'"+strFecha+"',"+dblBaseDePagos+","+VarC+","+VarC+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I')"; 
            VarC+",'"+strFecha+"',"+bigBaseDePagos+","+VarC+","+VarC+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I')"; 
            System.out.println("ZafVen28.insertarPagReg (1) " + strSQL);
            stmLoc.executeUpdate(strSQL);
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/
            
            if(intCodEmpDevVen==2 && intCodLocDevVen==4){
                //if (Double.parseDouble(txtComSol.getText())>0){
                if (new BigDecimal(txtComSol.getText()).compareTo(BigDecimal.ZERO)>0){
                    strSQL = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep, tx_tipreg ) " +
                    " values("+ intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+", 2 ,"+
                    //VarC+",'"+strFecha+"',"+txtComSol.getText()+","+VarC+","+Double.parseDouble(txtComSol.getText())*(-1)+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I','S')"; 
                    VarC+",'"+strFecha+"',"+txtComSol.getText()+","+VarC+","+(new BigDecimal(txtComSol.getText())).multiply(BigDecimal.valueOf(-1))+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I','S')";                             
                    System.out.println("ZafVen28.insertarPagReg (1) " + strSQL);
                    stmLoc.executeUpdate(strSQL);

                    Date datFecServ=objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos());
                    ZafGenDocCobAut zafGenComSol= new ZafGenDocCobAut(objParSis, this);
                    zafGenComSol.generaDocumentoCobroAutomatico(conIns,intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(txtCodTipDoc.getText()), intCodDocDevVen, datFecServ, 2);
                }
            }            
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/
            

            /*CAMBIO POR COSENCO DETOPACIO ECUATOSA ELECTRONICO*/
            //strSQL = "select sum(abs(mo_pag)) as mo_pag, sum(abs(nd_abo)) as nd_abo, (sum(abs(mo_pag)) - sum(abs(nd_abo))) as dif from tbm_pagmovinv where co_Emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and co_tipdoc="+txtCodTipDocRelFac.getText()+" and co_doc="+txtCodDocRelFac.getText()+" AND st_reg IN('A','C') and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END) ";
            strSQL = "select sum(abs(mo_pag)) as mo_pag, sum(abs(nd_abo)) as nd_abo, (sum(abs(mo_pag)) - sum(abs(nd_abo))) as dif from tbm_pagmovinv where co_Emp="+intCodEmpDevVen+" and co_loc="+txtCodLocRelFac.getText()+" and co_tipdoc="+txtCodTipDocRelFac.getText()+" and co_doc="+txtCodDocRelFac.getText()+" AND st_reg IN('A','C') and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END) ";
            /*CAMBIO POR COSENCO DETOPACIO ECUATOSA ELECTRONICO*/            
            System.out.println("ZafVen28.insertarPagReg (2) " + strSQL);
            rstLoc = stmLoc.executeQuery(strSQL);
            if (rstLoc.next()){
                //dblAbono = objUti.parseDouble(rstLoc.getDouble("nd_abo"));
                bigAbono = ((rstLoc.getBigDecimal("nd_abo")==null)?BigDecimal.ZERO:rstLoc.getBigDecimal("nd_abo"));
                //dblMonto = objUti.parseDouble(rstLoc.getDouble("mo_pag"));
                bigMonto = ((rstLoc.getBigDecimal("mo_pag")==null)?BigDecimal.ZERO:rstLoc.getBigDecimal("mo_pag"));
            }
            
            //if (dblAbono > 0) {
            if(bigAbono.compareTo(BigDecimal.ZERO)>0){
                intCruAut=0; // si tiene algun pago no se hace el cruce automatico.                
            }
            else {
                //if (dblMonto > dblBaseDePagos ) {
                if (bigMonto.compareTo(bigBaseDePagos)> 0 ) {
                    intCruAut = 0; // si la nota de crédito es parcial no se hace el cruce automático.
                }
            }
            /*dblTotMonPag=dblBaseDePagos;
            dblTotPag = dblBaseDePagos;*/

            bigTotMonPag=bigBaseDePagos;
            bigTotPag = bigBaseDePagos;            
            
              //**************************************************************************************
               if(intCruAut==1){ 
                   intCodDocCru = objUltDocPrint.getCodDocTbmCabPag(conIns, intTipDocCru);
                   strSQL ="INSERT INTO TBM_CABPAG(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc1, co_cli, tx_nomcli, nd_mondoc, st_reg, fe_ing , co_usring, st_regrep, st_imp)" +
                   " VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", "+intTipDocCru+", "+intCodDocCru+" " +
                   //", '"+strFecha+"', "+intCodDocCru+", "+txtCodCli.getText()+", '"+txtNomCli.getText()+"',"+dblBaseDePagos+",'A','"+ strFecSis +"',"+objParSis.getCodigoUsuario()+",'I','P')";
                           ", '"+strFecha+"', "+intCodDocCru+", "+txtCodCli.getText()+", '"+txtNomCli.getText()+"',"+bigBaseDePagos+",'A','"+ strFecSis +"',"+objParSis.getCodigoUsuario()+",'I','P')";
                   System.out.println("ZafVen28.insertarPagReg (3) " + strSQL);
                   stmLoc.executeUpdate(strSQL);

                    strSQL ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                    " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", " +
                    " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+" "+
                    //" ,1, "+dblBaseDePagos*-1+","+txtCodTipDoc.getText()+")";
                    " ,1, "+bigBaseDePagos.multiply(BigDecimal.valueOf(-1))+","+txtCodTipDoc.getText()+")";
                    System.out.println("ZafVen28.insertarPagReg (4) " + strSQL);
                    stmLoc.executeUpdate(strSQL);
                    /*CAMBIO POR COSENCO DETOPACIO ECUATOSA ELECTRONICO*/
                    strSQL ="select * from tbm_pagmovinv where co_Emp="+intCodEmpDevVen+" and co_loc="+txtCodLocRelFac.getText()+" and co_tipdoc="+txtCodTipDocRelFac.getText()+" and co_doc="+txtCodDocRelFac.getText()+" AND st_reg IN('A','C') and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END)";
                   //strSQL ="select * from tbm_pagmovinv where co_Emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and co_tipdoc="+txtCodTipDocRelFac.getText()+" and co_doc="+txtCodDocRelFac.getText()+" AND st_reg IN('A','C') and (CASE WHEN tx_tipreg IS NOT NULL THEN tx_tipReg <> 'S' ELSE TRUE END)";
                   /*CAMBIO POR COSENCO DETOPACIO ECUATOSA ELECTRONICO*/
                   System.out.println("ZafVen28.insertarPagReg (5) " + strSQL);
                   rstLocGenCru = stmLoc.executeQuery(strSQL);
                   //System.out.println(""+ strSQL );
                   while(rstLocGenCru.next()){
                       intSecc++;
                       
                       //dblRes = objUti.redondear((objUti.redondear(dblTotPag, 2) - objUti.redondear((objUti.parseDouble(rstLocGenCru.getDouble("mo_pag")) * -1), 2)), 2); 
                       bigRes = (bigTotPag.setScale(2, RoundingMode.HALF_UP).subtract( ((rstLocGenCru.getBigDecimal("mo_pag")).multiply(BigDecimal.valueOf(-1))).setScale(2, RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP); 
                       //if (objUti.redondear(dblTotPag, 2) >= objUti.redondear((objUti.parseDouble(rstLocGenCru.getDouble("mo_pag")) * -1), 2) ) {
                       if ((bigTotPag.setScale(2, RoundingMode.HALF_UP)).compareTo((rstLocGenCru.getBigDecimal("mo_pag").multiply(BigDecimal.valueOf(-1))).setScale(2, RoundingMode.HALF_UP)) >= 0 ) {
                           //dblMonPag = (objUti.parseDouble(rstLocGenCru.getDouble("mo_pag")) * -1);                           
                           bigMonPag=(rstLocGenCru.getBigDecimal("mo_pag").multiply(BigDecimal.valueOf(-1)));
                       }else{
                           //dblMonPag = dblTotPag;
                           bigMonPag=bigTotPag;
                       }
                       
                       //System.out.println(""+ intSecc );
                       /*strSQL ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                       " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", " +
                       " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+rstLocGenCru.getString("co_loc")+","+rstLocGenCru.getString("co_tipdoc")+","+rstLocGenCru.getString("co_doc")+" "+
                       " ,"+rstLocGenCru.getString("co_reg")+",abs("+rstLocGenCru.getDouble("mo_pag")+"),"+rstLocGenCru.getString("co_tipdoc")+")";*/

                       /*strSQL ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag) where " +
                       "  co_emp="+rstLocGenCru.getString("co_emp")+" and co_loc="+rstLocGenCru.getString("co_loc")+" and  co_tipdoc="+rstLocGenCru.getString("co_tipdoc")+" and co_doc="+rstLocGenCru.getString("co_doc")+" and co_reg="+rstLocGenCru.getString("co_reg");*/
                       //if (dblTotPag > 0) {
                       if (bigTotPag.compareTo(BigDecimal.ZERO) > 0) {
                            strSQL ="insert into tbm_detpag(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag,  " +
                            " co_regpag, nd_abo, co_tipdoccon) VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+", " +
                            " "+intTipDocCru+", "+intCodDocCru+","+intSecc+", "+rstLocGenCru.getString("co_loc")+","+rstLocGenCru.getString("co_tipdoc")+","+rstLocGenCru.getString("co_doc")+" "+
                            //" ,"+rstLocGenCru.getString("co_reg")+",abs("+dblMonPag+"),"+rstLocGenCru.getString("co_tipdoc")+")";
                            " ,"+rstLocGenCru.getString("co_reg")+",abs("+bigMonPag+"),"+rstLocGenCru.getString("co_tipdoc")+")";
                            System.out.println("ZafVen28.insertarPagReg (6) " + strSQL);
                            stmLocGenCru.executeUpdate(strSQL);

                            //strSQL=" UPDATE tbm_pagmovinv SET nd_abo=abs(" + dblMonPag +  ") "
                            strSQL=" UPDATE tbm_pagmovinv SET nd_abo=abs(" + bigMonPag +  ") "
                                    + " where co_emp="+rstLocGenCru.getString("co_emp")+" and co_loc="+rstLocGenCru.getString("co_loc")+" and  co_tipdoc="+rstLocGenCru.getString("co_tipdoc")+" and co_doc="+rstLocGenCru.getString("co_doc")+" and co_reg="+rstLocGenCru.getString("co_reg");
                            System.out.println("ZafVen28.insertarPagReg (7) " + strSQL);
                            stmLocGenCru.executeUpdate(strSQL);

                       }
                       
                       //dblTotPag = dblRes;
                       bigTotPag = bigRes;
                      // System.out.println(""+ strSQL );
                    }
                   rstLocGenCru.close();
                   rstLocGenCru=null;
                   
                    strSQL ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag)*-1 where " +
                    "  co_emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and  co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDocDevVen+" and co_reg=1";
                    /*strSQL ="UPDATE tbm_pagmovinv SET nd_abo=abs(mo_pag)*-1 where " +
                    "  co_emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" and  co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDocDevVen+" and co_reg=1";*/
                    System.out.println("ZafVen28.insertarPagReg (8) " + strSQL);
                    stmLoc.executeUpdate(strSQL);
             }      
           //**************************************************************************************
           
/*AGREGADO POR COMPENSACION SOLIDARIA*/
            
            /*if(intCodEmpDevVen==2 && intCodLocDevVen==4){
                if (Double.parseDouble(txtComSol.getText())>0){
                    strSQL = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep, tx_tipreg ) " +
                    " values("+ intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+", 2 ,"+
                    VarC+",'"+strFecha+"',"+txtComSol.getText()+","+VarC+","+txtComSol.getText()+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I','S')"; 
                    System.out.println("ZafVen28.insertarPagReg (1) " + strSQL);
                    stmLoc.executeUpdate(strSQL);

                    Date datFecServ=objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos());
                    ZafGenDocCobAut zafGenComSol= new ZafGenDocCobAut(objParSis, this);
                    zafGenComSol.generaDocumentoCobroAutomatico(conIns,intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(txtCodTipDoc.getText()), intCodDocDevVen, datFecServ, 2);
                }
            }        */    
            
            
            /*AGREGADO POR COMPENSACION SOLIDARIA*/           
        stmLoc.close();
        stmLoc=null;
        stmLocGenCru.close();
        stmLocGenCru=null;
        blnRes=true;
           
        }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
       catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
   return blnRes;         
 } 
             
    /*
     * Inserta Detalle del Registro de Devolucion.
     */         
    public boolean insertarDetReg(Connection conIns)
    {
        boolean blnRes=false;
        Statement stmLoc;
        ResultSet rstLoc; 
        String strSQL="";
        int intTipCli=3;
        double dlbcostouni=0.00;
        double bldcanmov=0.00; 
        double bldcostot=0.00;
        double dblCanDev=0; 
        double dblcanmov=0;
        double dlbCanMov=0.00;
        int intCodItm =0;
        int intCodBod=0;
        int intTipStk=0;
        int intControl=0;
        int intDevCom=0;
        int intDevTrans=0;
        int intTipMov=1;
        int intValStr=0;
        int intValStrTrans=0;
        int intValStrOrdTra=0;
        int intFilStrTrans=1;
        int intReaOrdTraAut=0;
        StringBuffer stbins=null, stbinsTrans=null, stbinsOrdTra=null;
        try
        {
            if (conIns!=null)
            {
                stmLoc=conIns.createStatement();

                /*********************************************\    
                 * Armando el Insert para los datos          *   
                 * del detalle de Documento                  *
                \*********************************************/
                GLO_strArreItm="";
                stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
                stbinsTrans =new StringBuffer(); //VARIABLE TIPO BUFFER  
                stbinsOrdTra =new StringBuffer(); //VARIABLE TIPO BUFFER  

                intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCodCli.getText(),conIns);
                objInvItm.inicializaObjeto();

                // INT_TBL_NODEVPRV
                if(radCan.isSelected())
                {
                    System.out.println("DET : " +  txtCodTipDocRelFac.getText());                       
                    if((Integer.parseInt( txtCodTipDocRelFac.getText() )==3) || (Integer.parseInt( txtCodTipDocRelFac.getText() )==229)) 
                    {
                        intTipMov=-1;
                    }

                    for(int i=0; i<tblDat.getRowCount();i++)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                            if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                                if(!tblDat.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                    if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString()) > 0.00 ){
                                        System.out.println("o");
                                        String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          

                                        //if(tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD ) != null){
                                        //if(!tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD ).equals("")){
                                        //if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD).toString()) > 0.00 ){

                                        dlbcostouni = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_COSTO )==null)?"0":tblDat.getValueAt(i, INT_TBL_COSTO ).toString());
                                        bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDEV )==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDEV ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDEV ).toString())  );
                                        bldcostot = (dlbcostouni * bldcanmov);
                                        dblcanmov = objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDEV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDEV ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDEV ).toString())   ),6);

                                        strSQL =" select sum(abs(b.nd_can)) as total from tbr_cabmovinv as a"+ 
                                                " inner join tbm_cabmovinv as b1 on(b1.co_emp=a.co_emp and b1.co_loc=a.co_loc and  b1.co_doc=a.co_doc and b1.co_tipdoc=a.co_tipdoc and b1.st_tipdev not in('P','V') )  "+ 
                                                " inner join tbm_detmovinv as b on(b.co_emp=b1.co_emp and b.co_loc=b1.co_loc and  b.co_doc=b1.co_doc and b.co_tipdoc=b1.co_tipdoc)  "+
                                                " where a.co_docrel="+txtCodDocRelFac.getText()+" and a.co_emp="+intCodEmpDevVen+" and a.co_loc="+intCodLocDevVen+
                                                " and a.co_tipdocrel="+txtCodTipDocRelFac.getText()+" and co_itm="+tblDat.getValueAt(i, INT_TBL_CODITM)+" and a.st_reg='A'";
                                        System.out.println("ZafVen28.insertarDetReg " + strSQL);
                                        rstLoc = stmLoc.executeQuery(strSQL);
                                        // System.out.println("Suma: "+ strSQL );
                                        if(rstLoc.next())
                                            dblCanDev = rstLoc.getDouble("total"); 
                                        rstLoc.close();
                                        rstLoc=null;

                                        //System.out.println("Suma: "+ bldcanmov+ " + "+  dblCanDev  );

                                        dblCanDev=dblCanDev+bldcanmov;
                                        if(intControl!=0)
                                            GLO_strArreItm=GLO_strArreItm+",";

                                        GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        intControl++;

                                        strSQL =" INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                                " tx_unimed,co_itm, co_itmact, tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                                //" co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes, st_ivaCom, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                                " co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes,  " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                                " st_reg, nd_tot, nd_cosunigrp, nd_costot, nd_costotgrp, nd_candev, st_regrep, " +
                                                /*AGREGADO POR COMPENSACION SOLIDARIA*/
                                                //" co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven, st_meringegrfisbod "+                                                
                                                " co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven, st_meringegrfisbod, nd_basimpivacer,nd_basimpivagra,nd_poriva, st_ivacom "+                                                
                                                /*AGREGADO POR COMPENSACION SOLIDARIA*/                                                
                                                " )" +
                                                "VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+","+(i+1)+", '"+
                                                        tblDat.getValueAt(i, INT_TBL_DESUNI)+"',"+tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM)+",'"+ 
                                                        tblDat.getValueAt(i, INT_TBL_CODALT)+"','"+tblDat.getValueAt(i, INT_TBL_CODALT2)+"',"+objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM))+","+
                                                        tblDat.getValueAt(i, INT_TBL_CODBOD)+","+(dblcanmov * intTipMov) + ","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANVEN).toString()),6)+", "+
                                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()),6) + ", " +
                                                        objInvItm.getCostoItm(conIns, intCodEmpDevVen, Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM))) +", "+
                                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) +// ", '" +
                                                        //((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"S":"N") + "'  " +
                                                        //",'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString()),2) + ","+
 //                                                       ",'P',"+ new BigDecimal(((tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString())).setScale(2, RoundingMode.HALF_UP) + ","+
                                                        ",null,"+ new BigDecimal(((tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString())).setScale(2, RoundingMode.HALF_UP) + ","+
                                                        dlbcostouni+","+bldcostot * intTipMov+","+bldcostot* intTipMov+" , "+ dblCanDev +" , 'I'" +
                                                        " ,"+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ 
                                                        /*AGREGADO POR COMPENSACION SOLIDARIA*/
                                                
                                                        //" , 'N' )"; 
                                                        //" , 'N', " + ( Double.valueOf( ((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("false"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):"0"))*(-1)) + 
                                                        " , 'N', " + ( new BigDecimal( ((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("false"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):"0"))) + 
                                                        //" , " + ( Double.valueOf(((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):"0"))*(-1)) + 
                                                        " , " + ( new BigDecimal(((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):"0"))) +                                                 
                                                        ","+((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?dblPorIva:0)+
                                                        ","+((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"'S'":"'N'")+
                                                        " )";                        
                                        
                                                        /*AGREGADO POR COMPENSACION SOLIDARIA*/
                                        txtsql.append( strSQL + " ; \n");
                                        System.out.println("ZafVen28.insertarDetReg (2) " + strSQL);
                                        strSQL=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis+"+bldcanmov+"  WHERE co_emp="+intCodEmpDevVen+" " +
                                               " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+" ";
                                        txtsql.append( strSQL + " ; \n");
                                        System.out.println("ZafVen28.insertarDetReg (3) " +   strSQL);
                                        
                                        // if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                                        //  if(!tblDat.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                        //   if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString()) > 0.00 ){

                                        // ORDEN DE COMPRA
                                        if(tblDat.getValueAt(i, INT_TBL_CODREG_OC ) != null){
                                            if(!tblDat.getValueAt(i, INT_TBL_CODREG_OC ).equals(""))
                                            {
                                                double dblDevSiPrv=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_SEDEVPRV)==null?"0":tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString()),4);
                                                //double dblDevNoPrv=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_NODEVPRV)==null?"0":tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString()),4);
                                                double dblDevNoPrv=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_CANSOLSTOCK)==null?"0":tblDat.getValueAt(i, INT_TBL_CANSOLSTOCK).toString()),4);
                                                System.out.println("--> "+dblDevSiPrv );

                                                if( dblDevSiPrv > 0 ) 
                                                {
                                                    strSQL="SELECT "+(i+1)+" AS coregdevven ,"+ tblDat.getValueAt(i, INT_TBL_CODITM)+"," +
                                                           "'"+tblDat.getValueAt(i, INT_TBL_CODALT)+"',"+dblDevSiPrv+" AS candev,"+tblDat.getValueAt(i, INT_TBL_CODDOC_OC)+" AS codoc " +
                                                           " ,"+tblDat.getValueAt(i, INT_TBL_CODREG_OC)+" AS coreg,"+tblDat.getValueAt(i, INT_TBL_CODTIP_OC)+" AS codtipdoc,"+tblDat.getValueAt(i, INT_TBL_CODLOC_OC)+" AS  coloc";

                                                    if(intValStr>0)  
                                                       stbins.append(" UNION ALL ");
                                                    stbins.append(strSQL);

                                                   intValStr=1;  
                                                   intDevCom=1;
                                                }

                                                if( dblDevNoPrv > 0 )
                                                {   
                                                    //if( !(chkVolFac.isSelected())){
                                                    strSQL="SELECT "+intCodEmpDevVen+" as coemp, "+tblDat.getValueAt(i, INT_TBL_CODITM)+" as coitm, " +
                                                            " "+dblDevNoPrv+" as ndcan, "+tblDat.getValueAt(i, INT_TBL_CODBOD)+" as  cobod, "+(i+1)+" as coreg ";

                                                    if(intValStrOrdTra>0)  
                                                        stbinsOrdTra.append(" UNION ALL ");
                                                        stbinsOrdTra.append(strSQL);

                                                    intValStrOrdTra=1;  
                                                    intReaOrdTraAut=1;
                                                }//}
                                            }
                                        }    

                                        //<editor-fold defaultstate="collapsed" desc="/* comentado */">
                              // TRANSFERENCIA
        //                   if(tblFacDet.getValueAt(i, INT_TBL_MARTRANS ) != null){
        //                     if(tblFacDet.getValueAt(i, INT_TBL_MARTRANS ).toString().equals("true")){
        //
        //                            strSQL="SELECT "+intFilStrTrans+" as numfil, "+(i+1)+" AS coregdevven ,"+ tblFacDet.getValueAt(i, INT_TBL_CODITM)+" as coitm ," +
        //                            "text('"+tblFacDet.getValueAt(i, INT_TBL_ALTITM)+"') as codalt ,"+dblcanmov+" AS candev,"+tblFacDet.getValueAt(i, INT_TBL_CODDOC2)+" AS codoc " +
        //                            " ,"+tblFacDet.getValueAt(i, INT_TBL_CODREG2)+" AS coreg,"+tblFacDet.getValueAt(i, INT_TBL_CODTIP2)+" AS codtipdoc,"+tblFacDet.getValueAt(i, INT_TBL_CODLOC2)+" AS  coloc";
        //
        //                      if (intValStrTrans>0)  
        //                          stbinsTrans.append(" UNION ALL ");
        //                          stbinsTrans.append(strSQL);
        //
        //                      intValStrTrans=1;  
        //                      intDevTrans=1;
        //                      intFilStrTrans++;
        //                   }}    
                            //</editor-fold>       

                                        //-------->Realiza el aumento o disminución en el inventario y el recosteo     
                                        if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().equalsIgnoreCase("N"))
                                        {
                                            intCodItm=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM));
                                            intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
                                            dlbCanMov=objUti.redondear( objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANDEV)) , 6);  

                                            //objInvItm.generaQueryStock(intCodEmpDevVen, intCodItm, intCodBod, dlbCanMov, 1, "S", strMerIngEgr, strTipIngEgr, 1);
                                            /*AGREGADO PARA STOCK DISPONIBLE LIBRERIAS NUEVAS*/
                                            ZafStkInv invStock= new ZafStkInv(objParSis);
                                            //Double dblCanNunRec=Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_CANNUNRECFAC)==null)?"0":tblDat.getValueAt(i,INT_TBL_CANNUNRECFAC).toString());
                                            //Double dblCanFac=Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_CANFAC)==null)?"0":tblDat.getValueAt(i,INT_TBL_CANFAC).toString());

                                            //if(Math.abs(dblCanNunRec)>0){                      
                                              if(invStock.actualizaInventario(conIns, intCodEmpDevVen, INT_ARL_STK_INV_STK_DIS, "+", 1, obtenerArrItmInv(conIns, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                                                  if(!invStock.actualizaInventario(conIns, intCodEmpDevVen, INT_ARL_STK_INV_STK, "+", 0, obtenerArrItmInv(conIns, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                                                        return false;
                                                  }
                                              }else{
                                                  return false;
                                              }
                                           // }else{
                                           //     if(!invStock.actualizaInventario(conIns, intCodEmpDevVen, INT_ARL_STK_INV_STK, "+", 1, obtenerArrItmInv(conIns, intCodEmpDevVen, intCodItm,dlbCanMov,intCodBod))){
                                           //           return false;
                                           //     }
                                           // }
                                            //}
                                            /*AGREGADO PARA STOCK DISPONIBLE LIBRERIAS NUEVAS */

                                            intTipStk=1;  
                                        }    
                                       /***********************************************/        

                                    }
                                }
                            }
                        }
                    }

                     if(intTipStk==1)
                     {
                        //System.out.println("--> Aqui "+objInvItm.getQueryEjecutaActStock() );

                         /*if(!objInvItm.ejecutaActStock(conIns, intTipCli))
                            return false;

                         if(!objInvItm.ejecutaVerificacionStock(conIns, intTipCli ))
                          return false;*/
                    } 
                    objInvItm.limpiarObjeto();
                }      

                //***************************************************************************************  
                if(radDes.isSelected() || radDesEsp.isSelected()){           
                    for(int i=0; i<tblDat.getRowCount();i++){
                     if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                      if(tblDat.getValueAt(i, INT_TBL_CANDES ) != null){
                       if(!tblDat.getValueAt(i, INT_TBL_CANDES ).equals("")){
                        if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDES).toString()) > 0.00 ){

                         dlbcostouni = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_COSTO )==null)?"0":(tblDat.getValueAt(i, INT_TBL_COSTO ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_COSTO ).toString()) );
                         bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDES )==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDES ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDES ).toString()) );
                         bldcostot = 0 ; // (dlbcostouni * bldcanmov);

                         if(intControl!=0)    
                            GLO_strArreItm=GLO_strArreItm+",";

                            GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                            intControl++;
                        
                            strSQL= " INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " tx_unimed ,co_itm, co_itmact,  tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                    //" co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                    " co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                    " ,st_reg,nd_tot,nd_cosunigrp,nd_costot ,nd_costotgrp , nd_candev,  st_regrep " +
                                    
                                    /*AGREGADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/
                                    //" ,co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven  )" +
                                    ",co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven, nd_basimpivacer, nd_basimpivagra, nd_poriva,st_ivacom )"+ 
                                    /*AGREGADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/
                                    
                                    " VALUES("+intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+","+(i+1)+",'"+ 
                                    tblDat.getValueAt(i, INT_TBL_DESUNI)+"',"+tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
                                    tblDat.getValueAt(i, INT_TBL_CODALT)+"','"+tblDat.getValueAt(i, INT_TBL_CODALT2) + "'," +
                                    objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM)) + ","+tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " + 
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDES).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDES).toString()) ),6) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANVEN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANVEN).toString()) ),6) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString().equals("0")?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) ),6) + ", " +
                                    objInvItm.getCostoItm(conIns, intCodEmpDevVen, Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM))) +", "+
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", " +
                                    
                                    /*COMENTADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/
                                    
                                    //((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"S":"N") + "'  " +
                                    //",'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i,INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString()) ),2) + ","+
                                    //"'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i,INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString()) ),2) + ","+
                                    "'P',"+ new BigDecimal( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i,INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString()) ).setScale(2, RoundingMode.HALF_UP) + ","+
                                    
                                    /*COMENTADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/                                    
                                    
                                    dlbcostouni+","+bldcostot+","+bldcostot+"," +
                                    Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_CANDIFDEV)==null)?"0":(tblDat.getValueAt(i,INT_TBL_CANDIFDEV).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_CANDIFDEV).toString()) )+", 'I' " +

                                    /*AGREGADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/
                                    
                                    //" ,"+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ " )"; 
                                    " ," + txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ 
                                    " ," + ((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("false"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):0)+ 
                                    " ," + ((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):0)+ 
                                                                       
                                    " ," +((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?dblPorIva:0)+
                                    " ," +((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"'S'":"'N'")
                                    +" )";
                                    
                                    /*AGREGADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/
                                    
                                    txtsql.append( strSQL + " ; \n");
                            System.out.println("ZafVen28.insertarDetReg (3) " + strSQL);
                            strSQL=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis+"+bldcanmov+"  WHERE co_emp="+intCodEmpDevVen+" " +
                                   " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+" ";
                            txtsql.append( strSQL + " ; \n");
                            System.out.println("ZafVen28.insertarDetReg (4) " + strSQL);
                        }
                       }
                      }
                     }
                    }
                }    
                //***************************************************************************************  


                //***************************************************************************************  
                 if(radPre.isSelected()){           
                    for(int i=0; i<tblDat.getRowCount();i++){
                     if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                      if(tblDat.getValueAt(i, INT_TBL_PREDEV ) != null){
                       if(!tblDat.getValueAt(i, INT_TBL_PREDEV ).equals("")){
                        if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_PREDEV).toString()) > 0.00 ){
                            dlbcostouni = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_COSTO )==null)?"0":tblDat.getValueAt(i, INT_TBL_COSTO ).toString());
                            bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREDEV )==null)?"0":tblDat.getValueAt(i, INT_TBL_PREDEV ).toString());
                            bldcostot = 0; //(dlbcostouni * bldcanmov);

                            strSQL= " INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                    " tx_unimed ,co_itm, co_itmact,  tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                    //" co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                    " co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                    " ,st_reg,nd_tot,nd_cosunigrp,nd_costot ,nd_costotgrp , nd_candev,  st_regrep " +
                                    //" ,co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven  )" +
                                    " ,co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven, nd_basimpivacer, nd_basimpivagra, nd_poriva,st_ivacom )" +
                                    " VALUES("+intCodEmpDevVen+ ","+intCodLocDevVen+","+txtCodTipDoc.getText()+ ","+intCodDocDevVen+", "+(i+1)+", '"+ 
                                    tblDat.getValueAt(i, INT_TBL_DESUNI)+ "',"+tblDat.getValueAt(i, INT_TBL_CODITM) + "," +
                                    tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +tblDat.getValueAt(i, INT_TBL_CODALT) + "','" +
                                    tblDat.getValueAt(i, INT_TBL_CODALT2) + "'," +objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM)) + "," +tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " + 
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREDEV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREDEV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PREDEV).toString()) ),6) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANVEN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANVEN).toString()) ),6) + ", " +
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) ),6) + ", " +
                                    objInvItm.getCostoItm(conIns, intCodEmpDevVen, Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM))) +", "+
                                    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()) ),2) + ", " +
                                    
                                    /*COMENTADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/
                                    //((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"S":"N") + "'  " +
                                    //",'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TOT).toString()) ),2) + ","+
                                    //"'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TOT).toString()) ),2) + ","+
                                    "'P',"+ new BigDecimal( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TOT).toString()) ).setScale(2, RoundingMode.HALF_UP) + ","+
                                    /*COMENTADO POR CAMBIOS DE COMPENSACION SOLIDARIA*/
                                    
                                    dlbcostouni+","+bldcostot+"," +bldcostot+" ,"+
                                    Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_CANDIFDEV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDIFDEV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDIFDEV).toString()) ) +",'I' " +
                                    
                                    /* AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA */
                                    
                                    //" ,"+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ " )"; 
                                    " ," + txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ 
                                    " ," + ((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("false"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):0)+ 
                                    " ," + ((tblDat.getValueAt(i, INT_TBL_IVA) != null && tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?(tblDat.getValueAt(i, INT_TBL_TOT) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOT).toString())):0)+ 
                                    " ," +((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?dblPorIva:0)+
                                    " ," +((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"'S'":"'N'")
                                    +" )";
                                    
                                    /* AGREGADO POR CAMBIOS EN COMPENSACION SOLIDARIA */
                                    
                                    
                                    txtsql.append( strSQL + " ; \n");
                            System.out.println("ZafVen28.insertarDetReg (5) " + strSQL);
                            strSQL= " UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis+"+bldcanmov+"  WHERE co_emp="+intCodEmpDevVen+" " +
                                    " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+" ";
                                    txtsql.append( strSQL + " ; \n");
                            System.out.println("ZafVen28.insertarDetReg (6) " + strSQL);
                    }}}}}
                 }   
               
                //***************************************************************************************  

                //             if(intDevTrans==1){
                //               if(intCodEmpDevVen==4 || intCodEmpDevVen==2){
                //                 if(!invertir_Trans( intCodLocDevVen, conIns, conIns, txtCodTipDocRelFac.getText()  , Integer.parseInt(txtCodDocRelFac.getText()) , stbinsTrans , txtCodTipDoc.getText() , intCodDocDevVen )) {
                //                        conIns.rollback();
                //                        return false;     
                //               }}}

                stmLoc.executeUpdate(txtsql.getText());
                txtsql.setText("");

                blnRes=true;

                if(intDevCom==1){
                  if(!RealizarDevolucionOC(conIns, stbins, intCodDocDevVen,  txtCodTipDoc.getText() )){
                      blnRes=false;
                 }}               

                if(intReaOrdTraAut==1)
                { 

                    javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe mercadería que no se Devuelve al proveedor por tanto no se puede realizar la devolución de venta. \n"+
                           " Se bebe solicitar Autorización a gerencía y dicha autorización acudir al Departamento de Sistemas. \n ";
                   

                    if(blnRes)
                    {    
                       recostearItmGrp();
                       if(!RealizarOrdTraAut(conIns, stbinsOrdTra, intCodDocDevVen,  txtCodTipDoc.getText() )){
                             blnRes=false;
                       }
                    }

                }              

                stbins=null;   
                stbinsTrans=null;   
                stbinsOrdTra=null;                      

            }
        }
        catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }
       
    
    private ArrayList obtenerArrItmInv(Connection cnx, int intCodEmp,int intCodItm, double dblCan, int intCodBod){
        String strSql="";
        Statement stmSql=null;
        ResultSet rsItmInv=null;
        ArrayList lstItmInv=new ArrayList();
        ArrayList lstItm=new ArrayList();
        try{
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
            while (rsItmInv.next()){
               lstItmInv.add(rsItmInv.getString("co_itmGru"));
               lstItmInv.add(rsItmInv.getString("co_itm"));
               lstItmInv.add(rsItmInv.getString("co_itmMae"));
               lstItmInv.add(rsItmInv.getString("tx_codAlt"));
               lstItmInv.add((Double.valueOf(dblCan<0?(dblCan*-1):dblCan)).toString());
               lstItmInv.add(Integer.valueOf(intCodBod).toString());
               lstItm.add(lstItmInv);
            }         
        }catch(Exception ex){
            ex.printStackTrace();     
        }
        return lstItm;
    }
 
    private boolean RealizarOrdTraAut(java.sql.Connection conn, StringBuffer stbins, int intCodDocDev, String strTipDoc )
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        int intCodDoc =0;
        double dblTot=0;
        double dblTotCab=0, dblCosUni=0;
        int intCodReg=0;
        int intEst=0;
        int intSer=0;
        int INTCODEMPGRP=0;
        try
        {
            if(conn!=null)
            {
                stmLoc = conn.createStatement();

                INTCODEMPGRP=objParSis.getCodigoEmpresaGrupo();

                StringBuffer stbDetOrd=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbDetRel=new StringBuffer(); //VARIABLE TIPO BUFFER

                StringBuffer stbinvemp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodemp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodgrp=new StringBuffer(); //VARIABLE TIPO BUFFER

                StringBuffer stbinvemp2=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodemp2=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvgrp2=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodgrp2=new StringBuffer(); //VARIABLE TIPO BUFFER

                int intSecGrp = 0;
                int intSecEmp = 0;
      //         intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(conn);
      //         intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(conn);
                //System.out.println(" "+  stbins.toString() );

                strSql= "SELECT case when Max(co_doc) is null then  1 else Max(co_doc)+1  end  as codoc  FROM tbm_cabMovInv where " +
                        " co_emp = "+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" "+
                        " and co_tipdoc = "+intTipDocTraAut ;
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()) intCodDoc=rstLoc.getInt("codoc");
                rstLoc.close();
                rstLoc=null;   

                strSql= "select inv.tx_codalt, inv.tx_nomitm, var.tx_desCor, coemp, coitm, ndcan, cobod, coreg " +
                        " ,( " +
                        "    select count(tx_codalt) from tbm_inv where co_emp=1 and  trim(upper(tx_codalt)) in ( " +
                        "    select trim((SUBSTR(UPPER(tx_codalt),1,length(tx_codalt)-1) || 'S')) from tbm_inv where co_emp=x.coemp and co_itm=x.coitm ) " +
                        "   ) as cant " +
                        "  ,( " +
                        "  SELECT a1.nd_cosuni FROM tbm_equInv as a " +
                        "  INNER JOIN tbm_inv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) " +
                        "  WHERE a.co_emp="+objParSis.getCodigoEmpresaGrupo()+" and a.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=x.coemp  AND co_itm=x.coitm) " +
                        "  ) as cosuni " +
                        ",( " +
                        "  select co_itm from tbm_inv where co_emp=x.coemp and  trim(upper(tx_codalt)) in ( " +
                        " select trim((SUBSTR(UPPER(tx_codalt),1,length(tx_codalt)-1) || 'S')) from tbm_inv where co_emp=x.coemp and co_itm=x.coitm ) " +
                        " ) as coitm2 "+     
                        " ,( " +         
                        "  select tx_codalt from tbm_inv where co_emp=x.coemp and  trim(upper(tx_codalt)) in ( " +
                        "  select trim((SUBSTR(UPPER(tx_codalt),1,length(tx_codalt)-1) || 'S')) from tbm_inv where co_emp=x.coemp and co_itm=x.coitm ) " +
                        "  ) as txcodalt " +
                        " ,( " +
                        "  select tx_nomitm from tbm_inv where co_emp=x.coemp and  trim(upper(tx_codalt)) in ( " +
                        "  select trim((SUBSTR(UPPER(tx_codalt),1,length(tx_codalt)-1) || 'S')) from tbm_inv where co_emp=x.coemp and co_itm=x.coitm ) " +
                        " ) as txnomitm " +
                        " ,( " +
                        " select var.tx_desCor from tbm_inv as inv  " +
                        " LEFT OUTER JOIN tbm_var AS var ON (var.co_reg=inv.co_uni) " +
                        " where inv.co_emp=1 and  trim(upper(inv.tx_codalt)) in (  " +
                        " select trim((SUBSTR(UPPER(tx_codalt),1,length(tx_codalt)-1) || 'S')) from tbm_inv where co_emp=x.coemp and co_itm=x.coitm ) " +
                        " ) as txdesCor " +
                        "   FROM ( " +
                        "    "+stbins.toString()+" "+  //  SELECT 1 as coemp, 6653 as coitm,  34.0 as ndcan   
                        "    )AS x  " +
                        "  inner join tbm_inv as inv on(inv.co_emp=x.coemp and inv.co_itm=x.coitm) " +
                        " LEFT OUTER JOIN tbm_var AS var ON (var.co_reg=inv.co_uni)  ";
                int intNumFil=0;
                System.out.println("RealizarOrdTraAut >> "+strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {  
                    if(rstLoc.getInt("cant")==1)
                    {

                        intNumFil++;

                        dblCosUni=rstLoc.getDouble("cosuni");
                        intCodReg++;
                        dblTot= objUti.redondear( (rstLoc.getDouble("ndcan") * dblCosUni ),2 );
                        dblTotCab+=dblTot;
                        dblTot=dblTot*-1;

                        if(intEst>0) stbDetOrd.append(" UNION ALL ");
                           stbDetOrd.append("SELECT "+intCodEmpDevVen+","+intCodLocDevVen+", "+intTipDocTraAut+", "+intCodDoc+"  , "+intCodReg+","+rstLoc.getInt("coitm")+" " +
                           ",'"+rstLoc.getString("tx_codalt")+"' ,'"+rstLoc.getString("tx_codalt")+"' " +
                           ",'"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_desCor")+"',"+rstLoc.getInt("cobod")+"  " +
                           ","+(rstLoc.getDouble("ndcan") *-1 )+"," +
                           ""+dblCosUni+", "+dblCosUni+" ,0.00,'N',"+intNumFil+","+dblTot+","+dblTot+","+dblTot+","+rstLoc.getInt("coitm")+" ,'N'" );

                        if(intEst>0) stbDetRel.append(" UNION ALL ");
                           stbDetRel.append("SELECT "+intCodEmpDevVen+", "+intCodLocDevVen+", "+strTipDoc+", "+intCodDocDev+", "+rstLoc.getInt("coreg")+", "+
                           "        "+intCodEmpDevVen+","+intCodLocDevVen+", "+intTipDocTraAut+", "+intCodDoc+"  ,"+intCodReg+", 'I' "  );
                          intEst=1;   

                          dblTot=dblTot*-1;
                          intCodReg++;
                        if(intEst>0) stbDetOrd.append(" UNION ALL ");
                           stbDetOrd.append("SELECT "+intCodEmpDevVen+","+intCodLocDevVen+", "+intTipDocTraAut+", "+intCodDoc+"  , "+intCodReg+","+rstLoc.getInt("coitm2")+" " +
                           ",'"+rstLoc.getString("txcodalt")+"' ,'"+rstLoc.getString("txcodalt")+"' " +
                           ",'"+rstLoc.getString("txnomitm")+"', '"+rstLoc.getString("txdesCor")+"',"+rstLoc.getInt("cobod")+"  " +
                           ","+(rstLoc.getDouble("ndcan") )+"," +
                           ""+dblCosUni+", "+dblCosUni+" ,0.00,'N',"+intNumFil+","+dblTot+","+dblTot+","+dblTot+","+rstLoc.getInt("coitm2")+" ,'N'" );


                        ///***************** EMPRESA ************************/   
                        if (intSer>0)
                           stbinvemp.append(" UNION ALL ");
                           stbinvemp.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("coitm")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                           stbinvbodemp.append(" UNION ALL ");
                           stbinvbodemp.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("cobod")+" as cobod,"+rstLoc.getInt("coitm")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock ");

                        if (intSer>0)
                           stbinvemp2.append(" UNION ALL ");
                           stbinvemp2.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("coitm2")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                           stbinvbodemp2.append(" UNION ALL ");
                           stbinvbodemp2.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("cobod")+" as cobod,"+rstLoc.getInt("coitm2")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock ");


                        //***********************************GRUPO***********************/
                        if (intSer>0)
                           stbinvgrp.append(" UNION ALL ");
                           stbinvgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                           "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm")+")) as coitm," +
                           ""+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                           stbinvbodgrp.append(" UNION ALL ");
                           stbinvbodgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                           "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm")+")) as coitm," +
                           "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmpDevVen+" and co_bod="+rstLoc.getDouble("cobod")+") as cobod, "+
                           ""+rstLoc.getDouble("ndcan")+" AS stock  " );

                        if (intSer>0)
                           stbinvgrp2.append(" UNION ALL ");
                           stbinvgrp2.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                           "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm2")+")) as coitm," +
                           ""+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                           stbinvbodgrp2.append(" UNION ALL ");
                           stbinvbodgrp2.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                           "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm2")+")) as coitm," +
                           "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmpDevVen+" and co_bod="+rstLoc.getDouble("cobod")+") as cobod, "+
                           ""+rstLoc.getDouble("ndcan")+" AS stock  " );

                        intSer=1; 
                        /*******************************************************************/
                    }
                    else
                    {
                          //JOptionPane oppMsg = new JOptionPane();
                          String strTit, strMsg;
                          strTit="Mensaje del sistema Zafiro";
                          strMsg="El item. "+rstLoc.getString("tx_codalt")+" no existe en Terminal S. \n"+ 
                           " ó esta repetido. \n"+
                           " Comuníquese con el departemento de Sistemas.";
                          JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE); 

                        blnRes=false;
                        intEst=0;
                        break;
                    }        
               }
               rstLoc.close();
               rstLoc=null;   

                if(intEst==1)
                {
                    strSql="INSERT INTO tbm_cabmovinv(co_emp,co_loc,co_tipdoc,co_doc, ne_numdoc, fe_Doc, nd_sub, nd_tot, st_reg, fe_ing, co_usring, ne_secemp, ne_Secgrp, " +
                    " st_tipdev, st_coninv ) " +
                    " VALUES("+intCodEmpDevVen+","+intCodLocDevVen+", "+intTipDocTraAut+", "+intCodDoc+", "+intCodDoc+", " +
                    " CURRENT_DATE, "+dblTotCab+", "+dblTotCab+" ,  'A',  CURRENT_TIMESTAMP  , 1 , "+intSecEmp+" , "+intSecGrp+",'C','N')";
                    stmLoc.executeUpdate(strSql); 

                    if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
                      stbDocRelEmpLoc.append(" SELECT "+intCodEmpDevVen+" AS COEMP, "+intCodLocDevVen+" AS COLOC , "+intTipDocTraAut+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
                    intDocRelEmpLoc=1;

                    strSql ="INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel, st_regrep)"+
                            " values("+intCodEmpDevVen+","+intCodLocDevVen+","+
                             strTipDoc+","+intCodDocDev+",'A',"+intCodEmpDevVen+","+intCodLocDevVen+","+
                             intTipDocTraAut+","+intCodDoc+",'I')";
                    stmLoc.executeUpdate(strSql); 

                    strSql="INSERT INTO tbm_detmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg, co_itm, tx_codalt, tx_codalt2, tx_nomitm , tx_unimed, co_bod, nd_can, nd_cosuni, nd_cosunigrp, nd_pordes," +
                           " st_ivacom, ne_numfil, nd_tot, nd_costot, nd_costotgrp, co_itmact, st_meringegrfisbod ) " + stbDetOrd.toString();
                    // System.out.println(""+strSql);
                    stmLoc.executeUpdate(strSql); 

                    strSql="INSERT INTO tbr_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg,    co_emprel, co_locrel, co_tipdocrel , co_docrel " +
                    ", co_regrel, st_regrep ) " + stbDetRel.toString();
                     //System.out.println(""+strSql);
                    stmLoc.executeUpdate(strSql); 

                    strSql ="Update tbm_inv set st_regrep='M', nd_stkAct = nd_stkAct - a.stock " +
                            " FROM ( " +
                            "       SELECT sum(stock) as stock, coemp, coitm FROM ( "+stbinvemp.toString()+" ) AS x GROUP BY coemp, coitm" +
                            " ) AS a where  co_emp=a.coemp AND co_itm=a.coitm";
                    strSql+=" ; Update tbm_invBod set  nd_stkAct  = nd_stkAct - a.stock  "+ //, nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ( "+
                            "       SELECT sum(stock) as stock, coemp, cobod, coitm FROM ( "+stbinvbodemp.toString()+" ) AS x GROUP BY coemp, cobod,  coitm" +     
                            " ) AS a where  co_emp=a.coemp  AND  co_bod=a.cobod and co_itm=a.coitm";
                    strSql+=" ; Update tbm_inv set st_regrep='M', nd_stkAct = nd_stkAct + a.stock " +
                            " FROM ("+ 
                            "       SELECT sum(stock) as stock, coemp, coitm FROM ( "+stbinvemp2.toString()+" ) AS x GROUP BY coemp, coitm" +    
                            " ) AS a where  co_emp=a.coemp AND co_itm=a.coitm";
                    strSql+=" ; Update tbm_invBod set  nd_stkAct  = nd_stkAct + a.stock  "+ //, nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ("+
                            "      SELECT sum(stock) as stock, coemp, cobod, coitm FROM ( "+stbinvbodemp2.toString()+" ) AS x GROUP BY coemp, cobod,  coitm" +     
                            " ) AS a where  co_emp=a.coemp  AND  co_bod=a.cobod and co_itm=a.coitm";
                    strSql+=" ; Update tbm_inv  set  st_regrep='M', nd_stkact = nd_stkact - a.stock " +
                            " FROM ( "+ 
                            "      SELECT sum(stock) as stock, coempgrp, coitm FROM ( "+stbinvgrp.toString()+" ) AS x GROUP BY coempgrp, coitm" +     
                            " ) AS a where co_emp=a.coempgrp and co_itm = a.coitm";
                    strSql+=" ; Update tbm_invbod  set nd_stkact = nd_stkact - a.stock "+ // , nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ( "+
                            "     SELECT sum(stock) as stock, coempgrp, cobod, coitm FROM ( "+stbinvbodgrp.toString()+" ) AS x GROUP BY coempgrp, cobod, coitm" +     
                            " ) AS a where co_emp=a.coempgrp AND co_itm=a.coitm AND co_bod=a.cobod";
                    strSql+=" ; Update tbm_inv  set  st_regrep='M', nd_stkact = nd_stkact + a.stock " +
                            " FROM ( "+
                            "    SELECT sum(stock) as stock, coempgrp, coitm FROM ( "+stbinvgrp2.toString()+" ) AS x GROUP BY coempgrp, coitm" +     
                            " ) AS a where co_emp=a.coempgrp and co_itm = a.coitm";
                    strSql+=" ; Update tbm_invbod  set nd_stkact = nd_stkact + a.stock "+ // , nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ( "+
                            "     SELECT sum(stock) as stock, coempgrp, cobod, coitm FROM ( "+stbinvbodgrp2.toString()+" ) AS x GROUP BY coempgrp, cobod, coitm" +     
                            " ) AS a where co_emp=a.coempgrp AND co_itm=a.coitm AND co_bod=a.cobod";

                    stmLoc.executeUpdate(strSql);  

                }                

                stmLoc.close();
                stmLoc=null;

                stbinvemp=null;
                stbinvbodemp=null;
                stbinvgrp=null;
                stbinvbodgrp=null;
                stbinvemp2=null;
                stbinvbodemp2=null;
                stbinvgrp2=null;
                stbinvbodgrp2=null;
                stbDetOrd=null;
                stbDetRel=null;
            }
        }
        catch(java.sql.SQLException e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    private boolean revertirOrdTraAut(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc )
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        String strSql = "";
        int intCodDoc =0;
        double dblTot=0;
        double dblTotCab=0, dblCosUni=0;
        int intCodReg=0;
        int intEst=0;
        int intSer=0;
        int INTCODEMPGRP=0;
        int intEstMov=0;
        String strItmPro="";
        try
        {
            if(conAnu!=null)
            {
                stmLoc = conAnu.createStatement();
                stmLoc01 = conAnu.createStatement();
                INTCODEMPGRP=objParSis.getCodigoEmpresaGrupo();

                StringBuffer stbDetOrd=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvemp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodemp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbConStk=new StringBuffer(); //VARIABLE TIPO BUFFER

                int intSecGrp = 0;
                int intSecEmp = 0;
       //         intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(conAnu);
       //         intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(conAnu);


                strSql= "SELECT case when Max(co_doc) is null then  1 else Max(co_doc)+1  end  as codoc  FROM tbm_cabMovInv where " +
                        " co_emp = "+intCodEmp+" and co_loc="+intCodLoc+" "+
                        " and co_tipdoc = "+intTipDocTraAut ;
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()) intCodDoc=rstLoc.getInt("codoc");
                rstLoc.close();
                rstLoc=null;   

                strSql= " SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel FROM tbr_cabmovinv AS a " +
                        " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emprel AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                        " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc+" "+
                        " AND a.co_tipdocrel="+intTipDocTraAut;        
                rstLoc01=stmLoc.executeQuery(strSql);
                while(rstLoc01.next())
                {
                    strSql= " select * from( " +
                            " select co_loc, co_itm, co_itmact, tx_codalt, tx_codalt2, tx_nomitm, tx_unimed, co_bod, (nd_can*-1) as ndcan, abs(nd_can) as ndcandev, ne_numfil, nd_tot, nd_costot" +
                            " ,( " +
                            " SELECT a1.nd_cosuni FROM tbm_equInv as a " +
                            " INNER JOIN tbm_inv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) " +
                            " WHERE a.co_emp=0 and a.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=x.co_Emp  AND co_itm=x.co_itm) " +
                            " ) as cosuni " +
                            " from tbm_detmovinv as x where x.co_emp="+intCodEmp+" and x.co_loc="+rstLoc01.getString("co_locrel")+" " +
                            " and x.co_tipdoc="+rstLoc01.getString("co_tipdocrel")+" and x.co_doc="+rstLoc01.getString("co_Docrel")+" " +
                            "  order by ne_numfil, nd_can*-1 " +
                            " ) as x "; 
                    int intNumFil=0;
                    intCodReg=0;
                    System.out.println("Paso 1 : "+ strSql );
                    rstLoc=stmLoc01.executeQuery(strSql);
                    while(rstLoc.next())
                    {

                        if(rstLoc.getDouble("ndcan") < 0 )
                        {
                           intNumFil++; 
                           dblCosUni=rstLoc.getDouble("cosuni");
                        }
                        intCodReg++;
                        dblTot= objUti.redondear( (rstLoc.getDouble("ndcandev") * dblCosUni ),2 );
                        dblTotCab+=objUti.redondear( (rstLoc.getDouble("ndcan") * dblCosUni ),2 );

                        if(intEst>0) stbDetOrd.append(" UNION ALL ");
                           stbDetOrd.append("SELECT "+intCodEmp+","+rstLoc.getString("co_loc")+", "+intTipDocTraAut+", "+intCodDoc+"  , "+intCodReg+","+rstLoc.getInt("co_itm")+" " +
                           ",'"+rstLoc.getString("tx_codalt")+"' ,'"+rstLoc.getString("tx_codalt2")+"' " +
                           ",'"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+rstLoc.getInt("co_bod")+"  " +
                           ","+rstLoc.getDouble("ndcan")+"," +
                           ""+dblCosUni+", "+dblCosUni+" ,0.00,'N',"+intNumFil+","+dblTot+","+dblTot+","+dblTot+","+rstLoc.getInt("co_itmact") );
                        intEst=1;   


                        ///***************** EMPRESA ************************/   
                        if (intSer>0)
                            stbinvemp.append(" UNION ALL ");
                            stbinvemp.append("SELECT "+intCodEmp+" AS coemp,"+rstLoc.getInt("co_itmact")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                            stbinvbodemp.append(" UNION ALL ");
                            stbinvbodemp.append("SELECT "+intCodEmp+" AS coemp,"+rstLoc.getInt("co_bod")+" as cobod,"+rstLoc.getInt("co_itmact")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock ");

                        //***********************************GRUPO***********************/
                        if (intSer>0)
                            stbinvgrp.append(" UNION ALL ");
                            stbinvgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                            "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+rstLoc.getInt("co_itmact")+")) as coitm," +
                            ""+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                            stbinvbodgrp.append(" UNION ALL ");
                            stbinvbodgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                            "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmp+" and co_itm="+rstLoc.getInt("co_itmact")+")) as coitm," +
                            "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmp+" and co_bod="+rstLoc.getDouble("co_bod")+") as cobod, "+
                            ""+rstLoc.getDouble("ndcan")+" AS stock  " );


                        if(intSer>0)
                           stbConStk.append(" UNION ALL ");

                        strSql= " SELECT SUM(a2.nd_stkAct) AS nd_stkAct, "+rstLoc.getInt("co_itmact")+" as coitm " +
                                " FROM tbm_equInv AS a1 " +
                                " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) " +
                                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer) " +
                                " WHERE a3.co_emp="+intCodEmp+" AND a3.co_loc="+intCodLoc+" " +
                                " AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv " +
                                " WHERE co_emp="+intCodEmp+" AND co_itm="+rstLoc.getInt("co_itmact")+" )   ";   
                        stbConStk.append(strSql);

                        intSer=1;     
                    }
                    rstLoc.close();
                    rstLoc=null;
                }
                rstLoc01.close();
                rstLoc01=null;   

                if(intEst==1)
                {
                    strSql= " INSERT INTO tbm_cabmovinv(co_emp,co_loc,co_tipdoc,co_doc, ne_numdoc, fe_Doc, nd_sub, nd_tot, st_reg, fe_ing, co_usring, ne_secemp, ne_Secgrp, " +
                            " st_tipdev, st_coninv ) " +
                            " VALUES("+intCodEmp+","+intCodLoc+", "+intTipDocTraAut+", "+intCodDoc+", "+intCodDoc+", " +
                            " CURRENT_DATE, "+dblTotCab+", "+dblTotCab+" ,  'A',  CURRENT_TIMESTAMP  , 1 , "+intSecEmp+" , "+intSecGrp+",'C','N')";
                   stmLoc.executeUpdate(strSql); 

                   strSql ="INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel, st_regrep)"+
                   " values("+intCodEmp+","+intCodLoc+","+
                   strCodTipDoc+","+strCodDoc+",'A',"+intCodEmp+","+intCodLoc+","+
                   intTipDocTraAut+","+intCodDoc+",'I')";
                   stmLoc.executeUpdate(strSql); 

                   strSql="INSERT INTO tbm_detmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg, co_itm, tx_codalt, tx_codalt2, tx_nomitm , tx_unimed, co_bod, nd_can, nd_cosuni, nd_cosunigrp, nd_pordes," +
                   " st_ivacom, ne_numfil, nd_tot, nd_costot, nd_costotgrp, co_itmact ) " + stbDetOrd.toString();
                   // System.out.println(""+strSql);
                   stmLoc.executeUpdate(strSql); 

                    strSql ="Update tbm_inv set st_regrep='M', nd_stkAct = nd_stkAct + a.stock " +
                    " FROM ( " +
                            "SELECT sum(stock) as stock, coemp, coitm FROM ( "+stbinvemp.toString()+" ) AS x GROUP BY coemp, coitm" +
                    " ) AS a where  co_emp=a.coemp AND co_itm=a.coitm";
                    strSql +=" ; Update tbm_invBod set  nd_stkAct  = nd_stkAct + a.stock  "+ //, nd_caningbod=nd_caningbod+a.caningbod " +
                    " FROM ( "+
                       "SELECT sum(stock) as stock, coemp, cobod, coitm FROM ( "+stbinvbodemp.toString()+" ) AS x GROUP BY coemp, cobod,  coitm" +     
                    " ) AS a where  co_emp=a.coemp  AND  co_bod=a.cobod and co_itm=a.coitm";


                    strSql +=" ; Update tbm_inv  set  st_regrep='M', nd_stkact = nd_stkact + a.stock " +
                    " FROM ( "+ 
                         "SELECT sum(stock) as stock, coempgrp, coitm FROM ( "+stbinvgrp.toString()+" ) AS x GROUP BY coempgrp, coitm" +     
                    " ) AS a where co_emp=a.coempgrp and co_itm = a.coitm";
                    strSql +=" ; Update tbm_invbod  set nd_stkact = nd_stkact + a.stock "+ // , nd_caningbod=nd_caningbod+a.caningbod " +
                    " FROM ( "+
                       "SELECT sum(stock) as stock, coempgrp, cobod, coitm FROM ( "+stbinvbodgrp.toString()+" ) AS x GROUP BY coempgrp, cobod, coitm" +     
                    " ) AS a where co_emp=a.coempgrp AND co_itm=a.coitm AND co_bod=a.cobod";
                    System.out.println("Paso 1 : "+ strSql ); 
                    stmLoc.executeUpdate(strSql);  

                    strSql="SELECT * FROM ( "+stbConStk.toString()+"  ) as x  where x.nd_stkAct < 0 ";
                    System.out.println("Paso 1 : "+ strSql );
                    rstLoc=stmLoc.executeQuery(strSql);
                    while(rstLoc.next()){
                        intEstMov=1;
                        strItmPro=rstLoc.getString("coitm");
                    }  

                    if(intEstMov==1)
                    {
                        //JOptionPane oppMsg = new JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="El item. "+strItmPro+" hay problema de stock. \n"+ 
                        " Comuníquese con el departemento de Sistemas.";
                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE); 
                        blnRes=false;
                    }

                }                

                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;

                stbinvemp=null;
                stbinvbodemp=null;
                stbinvgrp=null;
                stbinvbodgrp=null;
                stbDetOrd=null;
                stbConStk=null;
            }
        }
        catch(java.sql.SQLException e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }


 
    private boolean RealizarDevolucionOC(java.sql.Connection conn, StringBuffer stbins, int intCodDoc, String strCodTipDoc) 
    {
        boolean blnRes = true;
        int intNumDoc = 0, intTipDoc = 0, intCodLoc = 0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        int intval=0;
        int intRegSel=0;
        try
        {
            if(conn!=null)
            {
                stmLoc = conn.createStatement();

                strSql="SELECT count(*) FROM ("+stbins.toString()+") AS x WHERE  x.codoc is not null";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intval=rstLoc.getInt(1);
                rstLoc.close();
                rstLoc=null;

                strSql="SELECT codoc,codtipdoc,coloc,cab.st_reg ,cab.ne_numdoc, cab.st_coninv FROM ( " +
                       " SELECT distinct(codoc),codtipdoc,coloc FROM ("+stbins.toString()+") AS x WHERE  x.codoc is not null " +
                       " ) AS x LEFT JOIN  tbm_cabMovInv aS cab ON (cab.co_emp="+intCodEmpDevVen+" and cab.co_loc=x.coloc AND cab.co_tipdoc=x.codtipdoc AND cab.co_doc=x.codoc) "; 

                if(intval>0)
                {
                    rstLoc=stmLoc.executeQuery(strSql);
                    while(rstLoc.next())
                    {
                        if( (rstLoc.getString("st_coninv").equals("C")) || (rstLoc.getString("st_coninv").equals("F")) )
                        {
                            intNumDoc=rstLoc.getInt("codoc");
                            intTipDoc=rstLoc.getInt("codtipdoc");
                            intCodLoc=rstLoc.getInt("coloc");

                            boolean blnPag=true;
//                            if(!objUltDocPrint.isPagoDocumento_aso(conn, intCodEmpDevVen, intCodLoc ,  intTipDoc , intNumDoc, rstLoc.getString("ne_numdoc")  ))  
//                            {
//                                blnPag=mensajeConfir("¿Desea Continuar de todas forma.. ?");
//                            }
//                            if(!blnPag)
//                            {   
//                                blnRes=false;
//                                break;
//                            }


                            //if(objUltDocPrint.isPagoDocRet(intCodEmpDevVen, intCodLoc ,  intTipDoc , intNumDoc  ))
                            //{
                            //  blnRes=false;
                            //  break;
                            //}


                            //********************************************************************************************************
                            intRegSel=0;

                            Ventas.ZafVen28.ZafCom04 obj = new  Ventas.ZafVen28.ZafCom04(javax.swing.JOptionPane.getFrameForComponent(jfrPadre), true, objParSis , intNumDoc , 65 , intTipDoc, intCodEmpDevVen, intCodLoc, conn , stbins, intCodDoc, strCodTipDoc , "DevVen",  intRegSel );

                            if(obj.acepta())
                                blnRes=true;
                            else
                            { 
                                blnRes=false;
                                break;
                            }
                            obj.dispose();
                            obj=null;
                        }
                        else 
                        {
                            String strTit, strMsg;
                            strTit= "Mensaje del sistema Zafiro";
                            strMsg= " La orden de compra No."+rstLoc.getString("ne_numdoc")+" no está CONFIRMADO. \n"+ 
                                    " No es posible realizar la devolucion de la compra si no está confirmado. \n"+
                                    " Comuníquese con la persona que realiza las confirmaciones para poder realizar la devolucion.";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE); 
                            blnRes=false; 
                            break;
                        }
                    }
                }
            }
        }
        catch(java.sql.SQLException e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
                                         
            
            
          
    /**
     * Función que inserta Cabecera de registro.
     * @param conIns
     * @param strFecha
     * @return 
     */
    public boolean insertarCabReg(Connection conIns,  String strFecha )
    {
        boolean blnRes=false;
        String strFecSis="";   
        String strSql="";
        Statement stmLoc;
        ResultSet rstLoc; 
        try
        {
            if(conIns!=null)
            {
                stmLoc=conIns.createStatement();

                //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/ 
                strSql =" SELECT count(ne_numdoc) as num FROM tbm_cabmovinv   " +
                        " WHERE co_emp="+intCodEmpDevVen+" AND co_loc="+intCodLocDevVen+" " +
                        " AND co_tipdoc="+txtCodTipDoc.getText()+" AND ne_numdoc="+txtNumDoc.getText();
                System.out.println("ZafVen28.insertarCabReg (1)" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                    if(rstLoc.getInt("num") >= 1 )
                    {
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg=" No. de Devolucion ya existe... ?";
                        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE,null);
                        return false;
                    }
                } 
                rstLoc.close();
                rstLoc=null;
                ///*******************************************************************///
                
                strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

                ///******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
                int intSecGrp = 0;
                int intSecEmp = 0;
                //intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(conIns);
                //intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(conIns);
                Glo_intCodSec=intSecGrp;
                ///*****************************************************************///
               
                String strTipDev="";
                if(radCan.isSelected())    strTipDev="C";   
                if(radDes.isSelected())    strTipDev="P";  
                if(radPre.isSelected())    strTipDev="V";
                if(radDesEsp.isSelected()) strTipDev="E";  

                String strCodBen=null;
                String strNomBen="";
                strSql=" SELECT co_reg, tx_benchq FROM tbm_benchq WHERE co_emp="+intCodEmpDevVen+" " +
                       " AND co_cli="+txtCodCli.getText()+" and st_reg='P'";
                System.out.println("ZafVen28.insertarCabReg (2)" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                  strCodBen=rstLoc.getString("co_reg");
                  strNomBen=rstLoc.getString("tx_benchq");
                }
                rstLoc.close();
                rstLoc=null;
                
                int intCodUsrIngFac=obtenerUsrIngFac(intCodEmpDevVen, Integer.parseInt(txtCodLoc.getText()), 
                                                                    Integer.parseInt(txtCodTipDocSol.getText()), Integer.parseInt(txtCodDocSol.getText()), 
                                                                    conIns);	                

                strSql =" INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, " + //CAMPOS PrimayKey
                        "   ne_secemp, fe_doc, ne_secgrp, "+            // ne_secgrpant, ne_secempant,  "  + //secuencial de documentos y Fecha 
                        "   co_cli, co_com, tx_ate, " +                 // <==Campos que aparecen en la parte superior del 1er Tab
                        "   tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, " + //<-- Campos de info. del cliente
                        "   tx_nomven, "                                          + //Informacion del vendedor
                        "   ne_numDoc, ne_numCot, "                               + //<==Campos que aparecen en la parte superior del 1er Tab
                        /* COMENTADO POR CAMBIO EN COMPENSACION SOLIDARIA */
                        "   tx_obs1, tx_obs2, nd_sub,nd_tot,nd_valiva, nd_porIva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                        //"   tx_obs1, tx_obs2, nd_sub, nd_tot, nd_valiva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                        /* COMENTADO POR CAMBIO EN COMPENSACION SOLIDARIA */
                        "   fe_ing, co_usrIng, fe_ultMod, co_usrMod,  " + //Campos de Auditoria Fecha de Ingreso Y Usuario q ingreso.
                        "   st_reg, st_tipdev , st_regrep, co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, st_coninv, co_mnu, " +
                        //"   co_ben, tx_benchq, co_motdev, co_forPag   )" +
                        /*AGREGADO POR COMPENSACION SOLIDARIA*/
                        "   co_ben, tx_benchq, co_motdev, co_forPag,nd_valcomsol, nd_porcomsol,nd_subivacer, nd_subivagra)" +
                        /*AGREGADO POR COMPENSACION SOLIDARIA*/
                        "VALUES ("+intCodEmpDevVen+", "+intCodLocDevVen+", "+txtCodTipDoc.getText()+", "+intCodDocDevVen+ ", "+ 
                                intSecEmp+ ",'"+strFecha+ "',"+intSecGrp+ ", "+
                                // (intSecGrp-1) + ", "+(intSecEmp-1)+" , " +
                                txtCodCli.getText()+", "+((txtCodComFac.getText().equals(""))?"null":txtCodComFac.getText())+",'"+txtAteFac.getText()+"', '"+ 
                                txtNomCli.getText()+"','"+txtDirCliFac.getText()+"','"+txtRucFac.getText()+"','"+txtTelCliFac.getText()+"', '"+ 
                                txtCiuFac.getText()+"', '"+txtNomVenFac.getText()+ "', "+txtNumDoc.getText()+",  "+((txtNumDocFac.getText().equals(""))?"null":txtNumDocFac.getText())+", '"+
                                //txtObs1.getText()+"','"+txtObs2.getText()+"',"+Double.parseDouble(txtSub.getText())+","+Double.parseDouble(txtTot.getText())+ ", "+
                                txtObs1.getText()+"','"+txtObs2.getText()+"',"+new BigDecimal(txtSub.getText())+","+new BigDecimal(txtTot.getText())+ ", "+
                        
                                /* COMENTADO POR CAMBIO EN COMPENSACION SOLIDARIA */
                                //Double.parseDouble(txtIva.getText())+", "+objUti.redondeo(dblPorIva , 2)+" , '"+strFecSis+"' ,"+objParSis.getCodigoUsuario()+" ,"+
                                //new BigDecimal(txtIva.getText())+", "+objUti.redondeo(dblPorIva , 2)+" , '"+strFecSis+"' ,"+objParSis.getCodigoUsuario()+" ,"+
                                //CAMBIO REALIZADO POR PROYECTO COMISIONES CMATEO
                                new BigDecimal(txtIva.getText())+", "+objUti.redondeo(dblPorIva , 2)+" , '"+strFecSis+"' ,"+intCodUsrIngFac+" ,"+
                                //CAMBIO REALIZADO POR PROYECTO COMISIONES CMATEO
                                //Double.parseDouble(txtIva.getText())+" , '"+strFecSis+"' ,"+objParSis.getCodigoUsuario()+" ,"+
                                /* COMENTADO POR CAMBIO EN COMPENSACION SOLIDARIA */
                        
                                " '"+ strFecSis +"' , "+objParSis.getCodigoUsuario()+",'A', '"+strTipDev+"', 'I', " +
                                " "+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+ ", 'F', "+objParSis.getCodigoMenu()+" " +
                                //" , "+strCodBen+", '"+strNomBen+"', "+((txtCodMot.getText().equals(""))?"null":txtCodMot.getText())+", 1  )";
                                //" , "+strCodBen+", '"+strNomBen+"', "+((txtCodMot.getText().equals(""))?"null":txtCodMot.getText())+", 1 ,"+Double.parseDouble(txtComSol.getText()) + " , " +dblPorComSol+" , "+Double.parseDouble(txtSubTotIvaCero2.getText())+" , "+Double.parseDouble(txtSubTotIvaGra2.getText())+"  )";
                                " , "+strCodBen+", '"+strNomBen+"', "+((txtCodMot.getText().equals(""))?"null":txtCodMot.getText())+", 1 ,"+new BigDecimal(txtComSol.getText()) + " , " +dblPorComSol+" , "+new BigDecimal(txtSubTotIvaCero2.getText())+" , "+new BigDecimal(txtSubTotIvaGra2.getText())+"  )";
                System.out.println("ZafVen28.insertarCabReg (3)" + strSql);
                stmLoc.executeUpdate(strSql);

                if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
                
                stbDocRelEmpLoc.append(" SELECT "+intCodEmpDevVen+" AS COEMP, "+intCodLocDevVen+" AS COLOC , "+txtCodTipDoc.getText()+" AS COTIPDOC, "+intCodDocDevVen+" AS CODOC ");
                intDocRelEmpLoc=1;


                strSql= " UPDATE tbm_cabsoldevven SET  st_MerAceIngSis='S' WHERE co_emp="+intCodEmpDevVen+" " +
                        " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText();
                txtsql.append( strSql + " ; \n");
                System.out.println("ZafVen28.insertarCabReg (4)" + strSql);
                strSql =" INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel , st_regrep)"+
                        " VALUES("+intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+",'A',"+intCodEmpDevVen+","+
                                   txtCodLocRelFac.getText()+","+txtCodTipDocRelFac.getText()+","+txtCodDocRelFac.getText()+", 'I')";
                txtsql.append( strSql + " ; \n");
                blnRes=true;
            }
        }
        catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }
    
    
    private int obtenerUsrIngFac(int intCodEmpSol, int intCodLocSol, int intCodTipDocSol, int intCodDocSol, Connection cnx){
        String strQry="";
        ResultSet rstUsrIng=null;
        Statement stmUsrIng=null;
        int intUsrIngFac=0;
        try{
            stmUsrIng=cnx.createStatement();
            strQry="select c.co_usring \n" +
                " from tbm_cabsoldevven as s \n" +
                " inner join tbm_cabmovinv as c \n" +
                " on (c.co_emp=s.co_emp and c.co_loc=s.co_locrel and c.co_tipdoc=s.co_tipdocrel and c.co_doc=s.co_docrel) \n" +
                " where s.co_emp="+intCodEmpSol+" \n" +
                " and s.co_loc="+intCodLocSol+" \n" +
                " and s.co_tipdoc="+intCodTipDocSol+" \n" +
                " and s.co_doc="+intCodDocSol;
            rstUsrIng=stmUsrIng.executeQuery(strQry);
            while(rstUsrIng.next()){
                intUsrIngFac=rstUsrIng.getInt("co_usring");
            }
            rstUsrIng.close();
            rstUsrIng=null;
            stmUsrIng.close();
            stmUsrIng=null;                    
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intUsrIngFac;
    }    
    

    @Override
    public boolean modificar(){
        boolean blnRes=false;
        
          strAux=objTooBar.getEstadoRegistro();
          if(strAux.equals("Eliminado")) {
              MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
              return false;
          }
          if(strAux.equals("Anulado")) {
              MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
              return false;
          }

         
          if(!verificaItmUni())
              return false;
         
          if(!validaCampos())
               return false;
       
         if(modificarReg()) blnRes=true;
         
      return blnRes;
    }
    
    
       
    public boolean modificarReg()
    {
        boolean blnRes=false;
        java.sql.Connection conMod; 
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc; 
        String strSql="";
        String strCodOc="";  
        int intCon=0;
        int intEstDevCom=0;
        try
        {
                conMod =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if(conMod!=null)
                { 
                    conMod.setAutoCommit(false);
                    stmLoc=conMod.createStatement();   

                    //************************************************************************************      
                    strSql= " SELECT b.ne_numdoc FROM tbr_cabmovinv AS a " +
                            " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emprel AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                            " WHERE " +
                            " a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+" AND a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText()+" "+
                            " AND a.co_tipdocrel=4"; /* "DEVCOM";"Devolución de compras" */
                    rstLoc = stmLoc.executeQuery(strSql);
                    while(rstLoc.next())
                    {
                        strCodOc +=rstLoc.getString("ne_numdoc")+"\n";
                        intCon=1;
                    }
                    rstLoc.close(); 
                    rstLoc=null;
                    if(intCon>0)
                    {
                        String strMsg2 = "LAS SIGUIENTES DEVOLUCIONES DE COMPRA SERAN MODIFICADOS \n"+strCodOc +" \n ¿DESEA SEGUIR CON EL PROCESO DE MODIFICACION ?";
                        //JOptionPane oppMsg2=new JOptionPane();
                        String strTit2="Mensaje del sistema Zafiro";
                        if(!(JOptionPane.showConfirmDialog(this,strMsg2,strTit2,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 1 )){
                            intEstDevCom=0; //  0 = Acepta modificacion.
                        }
                        else intEstDevCom=1; // 1 = Cancela modificacion.
                    }                         
                    //************************************************************************************      

                    if(intEstDevCom==0)
                    {     
                        txtsql.setText("");
                        //ObtenerNumDocFac(conMod);  
                        if(objUltDocPrint.verificarsiesconfirmado(conMod, intCodEmpDevVen, intCodLocDevVen, txtCodTipDoc.getText(), txtCodDoc.getText() )){ 
                          if(!objUltDocPrint.isPagoDocumento_aso(conMod, intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){ 
                           if(validarDevMod(conMod)){  
                            if(modificarRegCab(conMod, Integer.parseInt(txtCodDoc.getText()))){    System.out.println(" invertirInvDet  : ");
                             if(invertirInvDet(conMod, Integer.parseInt(txtCodDoc.getText()))){
                                System.out.println(" modificarRegDet  : ");
                                if(modificarRegDet(conMod, Integer.parseInt(txtCodDoc.getText()))){  
                                  if(modificarRegPag(conMod, Integer.parseInt(txtCodDoc.getText()))){ 
                                    if(objAsiDia.actualizarDiario(conMod, intCodEmpDevVen, intCodLocDevVen, Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtCodDoc.getText()),txtCodDoc.getText(),objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                    {
                                        objInvItm.limpiarObjeto(); 
                                        conMod.commit(); 
                                        blnRes=true;
                                        //if(item.isSelected()) 
                                        //   calcularSaldo(conMod);
                                        // refrescaDatos();   
                                        cargarDatIng( Integer.parseInt(txtCodDoc.getText()) ); 
                                    }else conMod.rollback();  
                                   }else conMod.rollback(); 
                                  }else conMod.rollback();
                                 }else conMod.rollback();  
                                }else conMod.rollback();       
                               }else conMod.rollback();      
                              }else conMod.rollback();     
                             }else conMod.rollback();    
                    }
                    stmLoc.close();
                    stmLoc=null;
                    conMod.close();
                    conMod=null;
                }
        }
        catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }
   
   

    public boolean modificarRegCab(java.sql.Connection conn, int intCodDoc)
    {
        boolean blnRes=false;
        String strSql="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc; 
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/ 
                strSql ="select count(ne_numdoc) as num from tbm_cabmovinv  where " +
                       " co_emp="+intCodEmpDevVen+" and co_loc="+intCodLocDevVen+" " +
                       "and co_tipdoc="+txtCodTipDoc.getText()+" and ne_numdoc="+txtNumDoc.getText()+" and co_doc <> "+intCodDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                    if(rstLoc.getInt("num") >= 1 ){
                          //JOptionPane oppMsg = new JOptionPane();
                          String strTit, strMsg;
                          strTit="Mensaje del sistema Zafiro";
                          strMsg=" No. de Devolucion ya existe... ?";
                          JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE,null);
                          return false;
                    }
                } 
                rstLoc.close();
                rstLoc=null;

                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

                strSql = "Update tbm_cabMovInv set " +
                          " fe_doc  = '"+strFecha+"', ne_numDoc="+txtNumDoc.getText()+", "+ 
                          " tx_obs1 = '"+txaObs1.getText()+"', "+ 
                          " tx_obs2 = '"+txaObs2.getText()+"', nd_sub = "+Double.parseDouble(txtSub.getText())+ ", "+
                          " nd_tot  = "+Double.parseDouble(txtTot.getText())+", nd_valiva="+Double.parseDouble(txtIva.getText())+", "+
                          " nd_porIva = "+dblPorIva+",  fe_ultMod = "+objParSis.getFuncionFechaHoraBaseDatos()+", "+
                          " co_usrMod = "+objParSis.getCodigoUsuario()+" "+
                          " WHERE "+
                          " co_emp = "+intCodEmpDevVen+" AND  co_loc ="+intCodLocDevVen+" "+  
                          " AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc ="+intCodDoc;
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }


    public boolean invertirInvDet(java.sql.Connection conn, int intCodDoc)
    {
     boolean blnRes=true;
     java.sql.Statement stmLoc, stmLocPri;
     java.sql.ResultSet rstLoc; 
     String strSQL="";
     int intTipCli=3;
     int intTipMov=1;
     int intCodItm =0;
     int intCodBod=0;
     int intTipStk=0;
     double dlbCanMov=0.00;
     try{
      if(conn!=null){
         stmLoc=conn.createStatement();
         stmLocPri=conn.createStatement();
         intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCodCli.getText(),conn);

       if(radCan.isSelected()){
          intTipMov=-1;
          if((Integer.parseInt( txtCodTipDocRelFac.getText() )==3))
            intTipMov=1;

         objInvItm.inicializaObjeto();

         String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
         " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
         " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
         " WHERE a.co_emp="+intCodEmpDevVen+" and a.co_loc="+intCodLocDevVen+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
         " THEN 'S' ELSE 'N' END AS proconf  ";

        strSQL="SELECT a.co_itm, a.co_bod, abs(a.nd_can) as nd_can ,b.st_ser " +
        " ,a.co_locrelsoldevven, a.co_tipdocrelsoldevven, a.co_docrelsoldevven, a.co_regrelsoldevven " +
        strAux2+" FROM tbm_detMovInv as a " +
        " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+     
        " WHERE " +
        " a.co_emp="+intCodEmpDevVen+" AND a.co_loc ="+intCodLocDevVen+" " +
        " AND a.co_tipDoc = "+txtCodTipDoc.getText()+" AND a.co_doc ="+intCodDoc;
        rstLoc = stmLoc.executeQuery(strSQL);
        while(rstLoc.next()){
         if(rstLoc.getString("st_ser").equals("N")){

           String strEstFisBod=rstLoc.getString("proconf");                          

           intCodItm=rstLoc.getInt("co_itm");
           intCodBod=rstLoc.getInt("co_bod");
           dlbCanMov=rstLoc.getDouble("nd_can");

           strEstFisBod=rstLoc.getString("proconf");
           objInvItm.generaQueryStock(intCodEmpDevVen, intCodItm, intCodBod, dlbCanMov, -1, "S", strMerIngEgr, strTipIngEgr, -1);
           intTipStk=1; 

           strSQL=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis-"+rstLoc.getDouble("nd_can")+"  WHERE co_emp="+intCodEmpDevVen+" " +
           " AND co_loc="+rstLoc.getString("co_locrelsoldevven")+" AND co_tipdoc="+rstLoc.getString("co_tipdocrelsoldevven")+" AND co_Doc="+rstLoc.getString("co_docrelsoldevven")+" AND co_reg="+rstLoc.getString("co_regrelsoldevven")+" ";
           stmLocPri.executeUpdate(strSQL);

          }}
          rstLoc.close();
          rstLoc=null;

             if(intTipStk==1){
                if(!objInvItm.ejecutaActStock(conn, intTipCli))
                  return false;
                if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
                  return false;
             }
            objInvItm.limpiarObjeto();  
            stmLocPri.close();
            stmLocPri=null;
           }



       if(radDes.isSelected()){
         strSQL="SELECT a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed " +
         " ,a.co_bod ,a1.tx_nom, abs(a.nd_can) as nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
         " ,b.st_ser, a.co_reg, a.nd_tot, a.co_regrelsoldevven "+
         " FROM tbm_detmovinv AS a " +
         " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +         
         " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+       
         " WHERE a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+"  AND " +
         " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText();
         rstLoc = stmLoc.executeQuery(strSQL);
         while(rstLoc.next()){

           strSQL=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis-"+rstLoc.getDouble("nd_can")+"  WHERE co_emp="+intCodEmpDevVen+" " +
           " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+rstLoc.getString("co_regrelsoldevven")+" ";
           stmLocPri.executeUpdate(strSQL);

       }

          rstLoc.close();
          rstLoc=null;
          stmLocPri.close();
          stmLocPri=null;
      }


      if(radPre.isSelected()){
         strSQL="SELECT a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed " +
         " ,a.co_bod ,a1.tx_nom, abs(a.nd_can) as nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
         " ,b.st_ser, a.co_reg, a.nd_tot, a.co_regrelsoldevven "+
         " FROM tbm_detmovinv AS a " +
         " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +         
         " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+       
         " WHERE a.co_emp="+intCodEmpDevVen+" AND a.co_loc="+intCodLocDevVen+"  AND " +
         " a.co_tipdoc="+txtCodTipDoc.getText()+" AND a.co_doc="+txtCodDoc.getText();
         rstLoc = stmLoc.executeQuery(strSQL);
         while(rstLoc.next()){

           strSQL=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis-"+rstLoc.getDouble("nd_can")+"  WHERE co_emp="+intCodEmpDevVen+" " +
           " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+rstLoc.getString("co_regrelsoldevven")+" ";
           stmLocPri.executeUpdate(strSQL);

       }

          rstLoc.close();
          rstLoc=null;
          stmLocPri.close();
          stmLocPri=null;
      }


            stmLoc.close();
            stmLoc=null;
          }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
           catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
       return blnRes;         
    }
             
  

    public boolean modificarRegDet(java.sql.Connection conn, int intCodDoc) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSQL = "";
        int intTipCli = 3;
        double dlbcostouni=0.00;
        double bldcanmov=0.00; 
        double bldcostot=0.00;
        double dblCanDev=0; 
        double dblcanmov=0;
        double dlbCanMov=0.00;
        int intCodItm =0;
        int intCodBod=0;
        int intTipStk=0;
        int intControl=0;
        int intDevCom=0;
        int intTipMov=1;
        int intValStr=0;
        int intReaOrdTraAut=0;
        StringBuffer stbins=null, stbinsOrdTra=null;
        int intValStrOrdTra=0;
        try
        {

            if (conn!=null)
            {

                stmLoc=conn.createStatement();


                /*********************************************\    
                 * Armando el Insert para los datos          *   
                 * del detalle de Documento                  *
                \*********************************************/
                GLO_strArreItm="";
                stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
                stbinsOrdTra =new StringBuffer(); //VARIABLE TIPO BUFFER  

                intTipCli= objUltDocPrint.ValidarCodigoCliente(txtCodCli.getText(),conn);

                strSQL ="DELETE FROM tbr_detMovInv WHERE " +
                        " co_emp ="+intCodEmpDevVen+" AND co_loc ="+intCodLocDevVen+"" +
                        " AND co_tipDoc ="+txtCodTipDoc.getText()+" AND co_doc ="+intCodDoc;  
                txtsql.append( strSQL + " ; \n");   

                strSQL ="DELETE FROM tbm_detMovInv WHERE "+
                        " co_emp ="+intCodEmpDevVen+" AND co_loc ="+intCodLocDevVen+" " +
                        " AND co_tipDoc = "+txtCodTipDoc.getText()+" AND co_doc ="+intCodDoc;
                txtsql.append( strSQL + " ; \n");   

                //if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";      

                //***************************************************************************************  
                if(radCan.isSelected())
                {   
                    if((Integer.parseInt( txtCodTipDocRelFac.getText() )==3))
                       intTipMov=-1;

                    objInvItm.inicializaObjeto();

                    for(int i=0; i<tblDat.getRowCount();i++)
                    {
                        if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                            if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                                if(!tblDat.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                    if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString()) > 0.00 ){
                                        //if(tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD ) != null){
                                           //if(!tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD ).equals("")){
                                                //if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD).toString()) > 0.00 ){
                                        dlbcostouni = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_COSTO )==null)?"0":tblDat.getValueAt(i, INT_TBL_COSTO ).toString());
                                        bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDEV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANDEV ).toString());
                                        bldcostot = (dlbcostouni * bldcanmov);
                                        dblcanmov = objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDEV)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANDEV).toString()),6);

                                        if(intControl!=0)    
                                            GLO_strArreItm=GLO_strArreItm+",";
                                        GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        intControl++;

                                        strSQL =" select sum(abs(b.nd_can)) as total from tbr_cabmovinv as a"+ 
                                                " inner join tbm_cabmovinv as b1 on(b1.co_emp=a.co_emp and b1.co_loc=a.co_loc and  b1.co_doc=a.co_doc and b1.co_tipdoc=a.co_tipdoc and b1.st_tipdev not in('P','V') )  "+ 
                                                " inner join tbm_detmovinv as b on(b.co_emp=b1.co_emp and b.co_loc=b1.co_loc and  b.co_doc=b1.co_doc and b.co_tipdoc=b1.co_tipdoc)  "+
                                                " where a.co_docrel="+txtCodDocRelFac.getText()+" and a.co_emp="+intCodEmpDevVen+" and "+
                                                " a.co_loc="+intCodLocDevVen+" and "+       
                                                " a.co_tipdocrel="+txtCodTipDocRelFac.getText()+" and a.co_doc != "+intCodDoc+
                                                " and co_itm="+tblDat.getValueAt(i, INT_TBL_CODITM)+" and a.st_reg='A'";

                                        System.out.println(" sss "+ strSQL );

                                        rstLoc = stmLoc.executeQuery(strSQL);
                                        if(rstLoc.next())
                                             dblCanDev = rstLoc.getDouble("total"); 
                                         dblCanDev=dblCanDev+bldcanmov;

                                        strSQL= "INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                                " tx_unimed ,co_itm, co_itmact, tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                                " co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                                " ,st_reg,nd_tot,nd_cosunigrp,nd_costot ,nd_costotgrp , nd_candev  , st_regrep " +
                                                " ,co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven  )" +
                                                "VALUES("+intCodEmpDevVen+", "+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDoc+","+(i+1)+", '"+
                                                tblDat.getValueAt(i, INT_TBL_DESUNI)+"',"+tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM)+",'"+ 
                                                tblDat.getValueAt(i, INT_TBL_CODALT)+"','"+tblDat.getValueAt(i, INT_TBL_CODALT2)+"',"+objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM))+","+
                                                tblDat.getValueAt(i, INT_TBL_CODBOD)+","+(dblcanmov * intTipMov) + ","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANVEN)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANVEN).toString()),6)+", "+
                                                objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()),6) + ", " +
                                                objInvItm.getCostoItm(conn, intCodEmpDevVen, Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM))) +", "+
                                                objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", '" +
                                                ((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"S":"N") + "'  " +
                                                ",'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString()),2) + ","+
                                                dlbcostouni+","+bldcostot * intTipMov+","+bldcostot* intTipMov+" , "+ dblCanDev +" , 'I'" +
                                                " ,"+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ " )"; 
                                                txtsql.append( strSQL + " ; \n");

                                        strSQL= " UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis+"+bldcanmov+"  WHERE co_emp="+intCodEmpDevVen+" " +
                                                " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+" ";
                                                txtsql.append( strSQL + " ; \n");   

                                        double dblDevSiPrv=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_SEDEVPRV)==null?"0":tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString()),4);
                                        //double dblDevNoPrv=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_NODEVPRV)==null?"0":tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString()),4);
                                        double dblDevNoPrv=objUti.redondear( (tblDat.getValueAt(i, INT_TBL_CANSOLSTOCK)==null?"0":tblDat.getValueAt(i, INT_TBL_CANSOLSTOCK).toString()),4);


                                        // DEVOLUCION DE COMPRA
                                        if(tblDat.getValueAt(i, INT_TBL_CODREG_OC ) != null)
                                        {
                                            if(!tblDat.getValueAt(i, INT_TBL_CODREG_OC ).equals("")){
                                                if(tblDat.getValueAt(i, INT_TBL_CODTIP_OC ).equals("4")) // DEVCOM
                                                {  
                                                    strSQL= "SELECT  "+tblDat.getValueAt(i,INT_TBL_CODREGDEV)+" AS codregdev , "+(i+1)+" AS coregdevven ,"+ tblDat.getValueAt(i, INT_TBL_CODITM)+"," +
                                                            "'"+tblDat.getValueAt(i, INT_TBL_CODALT)+"',"+dblDevSiPrv+" AS candev,"+tblDat.getValueAt(i, INT_TBL_CODDOC_OC)+" AS codoc " +
                                                            " ,"+tblDat.getValueAt(i, INT_TBL_CODREG_OC)+" AS coreg,"+tblDat.getValueAt(i, INT_TBL_CODTIP_OC)+" AS codtipdoc,"+tblDat.getValueAt(i, INT_TBL_CODLOC_OC)+" AS  coloc";

                                                    if (intValStr>0)  
                                                        stbins.append(" UNION ALL ");
                                                        stbins.append(strSQL);

                                                    intValStr=1;  
                                                    intDevCom=1;
                                                }
                                            }
                                        }

                                        // ORDEN DE TRABAJO
                                        if(tblDat.getValueAt(i, INT_TBL_CODREG_OT ) != null){
                                            if(!tblDat.getValueAt(i, INT_TBL_CODREG_OT ).equals("")){
                                                if(tblDat.getValueAt(i, INT_TBL_CODTIP_OT ).equals(""+intTipDocTraAut)){  

                                                    //double dblcanDevOrdTra = objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDEV)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANDEV).toString()),6);
                                                    //double dblcanDevOrdTra_Ori = objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDEV_ORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANDEV_ORI).toString()),6);
                                                    //dblcanDevOrdTra = dblcanDevOrdTra-dblcanDevOrdTra_Ori;

                                                    strSQL="SELECT "+intCodEmpDevVen+" as COEMP, " +
                                                    tblDat.getValueAt(i, INT_TBL_CODLOC_OT)+" as COLOCREL, " +
                                                    tblDat.getValueAt(i, INT_TBL_CODTIP_OT)+" as COTIPDOCREL, " +
                                                    tblDat.getValueAt(i, INT_TBL_CODDOC_OT)+" as CODOCREL, " +
                                                    tblDat.getValueAt(i, INT_TBL_CODREG_OT)+" as COREGREL, " +
                                                    tblDat.getValueAt(i, INT_TBL_CODITM)+" as coitm, " +
                                                    tblDat.getValueAt(i, INT_TBL_CODBOD)+" as cobod, " +
                                                    dblDevNoPrv+" as ndcan, "+(i+1)+" as coreg ";

                                                   if(intValStrOrdTra>0)  
                                                       stbinsOrdTra.append(" UNION ALL ");  
                                                       stbinsOrdTra.append(strSQL);

                                                   intValStrOrdTra=1;  
                                                   intReaOrdTraAut=1;
                                                }
                                            }
                                        }    

                                        //////////////////////////////////////////////
                                        String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          

                                        //-------->Realiza el aumento o disminución en el inventario y el recosteo     
                                        if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().equalsIgnoreCase("N"))
                                        {
                                            intCodItm=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM));
                                            intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
                                            dlbCanMov=objUti.redondear( objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANDEV)) , 6);  

                                            objInvItm.generaQueryStock(intCodEmpDevVen, intCodItm, intCodBod, dlbCanMov, 1, "S", strMerIngEgr, strTipIngEgr, 1);
                                            intTipStk=1;  
                                        }
                                    }
                                }
                            }
                        }
                    }

                   if(intTipStk==1)
                   {
                     if(!objInvItm.ejecutaActStock(conn, intTipCli))
                        return false;

                     if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
                      return false;
                   } 

                   System.out.println("AQui No es : ");
                   objInvItm.limpiarObjeto();

                }                             

                //***************************************************************************************  
                if(radDes.isSelected())
                {           
                    for(int i=0; i<tblDat.getRowCount();i++){
                        if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                            if(tblDat.getValueAt(i, INT_TBL_CANDES ) != null){
                                if(!tblDat.getValueAt(i, INT_TBL_CANDES ).equals("")){
                                    if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDES).toString()) > 0.00 )
                                    {
                                        dlbcostouni = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_COSTO )==null)?"0":(tblDat.getValueAt(i, INT_TBL_COSTO ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_COSTO ).toString()) );
                                        bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDES )==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDES ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDES ).toString()) );
                                        bldcostot = (dlbcostouni * bldcanmov);

                                        if(intControl!=0)    
                                          GLO_strArreItm=GLO_strArreItm+",";

                                        GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                                        intControl++;

                                        strSQL= "INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                                " tx_unimed ,co_itm, co_itmact,  tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                                " co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                                " ,st_reg,nd_tot,nd_cosunigrp,nd_costot ,nd_costotgrp , nd_candev,  st_regrep " +
                                                " ,co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven  )" +
                                                " VALUES("+intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDoc+","+(i+1)+",'"+ 
                                        tblDat.getValueAt(i, INT_TBL_DESUNI)+"',"+tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
                                        tblDat.getValueAt(i, INT_TBL_CODALT)+"','"+tblDat.getValueAt(i, INT_TBL_CODALT2) + "'," +
                                        objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM)) + ","+tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " + 

                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDES).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDES).toString()) ),6) + ", " +
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANVEN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANVEN).toString()) ),6) + ", " +
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString().equals("0")?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) ),6) + ", " +
                                        objInvItm.getCostoItm(conn, intCodEmpDevVen, Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM))) +", "+
                                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()) ),2) + ", '" +

                                        ((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"S":"N") + "'  " +
                                        ",'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i,INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_TOT).toString()) ),2) + ","+
                                        dlbcostouni+","+bldcostot+","+bldcostot+"," +
                                        Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_CANDIFDEV)==null)?"0":(tblDat.getValueAt(i,INT_TBL_CANDIFDEV).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_CANDIFDEV).toString()) )+", 'I' " +
                                        " ,"+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ " )"; 
                                        txtsql.append( strSQL + " ; \n");

                                        strSQL=" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis+"+bldcanmov+"  WHERE co_emp="+intCodEmpDevVen+" " +
                                        " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+" ";
                                        txtsql.append( strSQL + " ; \n");
                                    }
                                }
                            }
                        }
                    }
                }    
                //***************************************************************************************  
                //***************************************************************************************  
                if(radPre.isSelected())
                {           
                    for(int i=0; i<tblDat.getRowCount();i++){
                        if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                            if(tblDat.getValueAt(i, INT_TBL_PREDEV ) != null){
                                if(!tblDat.getValueAt(i, INT_TBL_PREDEV ).equals("")){
                                    if( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_PREDEV).toString()) > 0.00 )
                                    {
                                        dlbcostouni = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_COSTO )==null)?"0":(tblDat.getValueAt(i, INT_TBL_COSTO ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_COSTO ).toString()) );
                                        bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREDEV )==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREDEV ).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PREDEV ).toString()) );
                                        bldcostot = 0; //(dlbcostouni * bldcanmov);

                                        strSQL="INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
                                                " tx_unimed ,co_itm, co_itmact,  tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                                                " co_bod, nd_can, nd_canorg, nd_preUni, nd_cosUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                                                " ,st_reg,nd_tot,nd_cosunigrp,nd_costot ,nd_costotgrp , nd_candev,  st_regrep " +
                                                " ,co_locrelsoldevven, co_tipdocrelsoldevven, co_docrelsoldevven, co_regrelsoldevven  )" +
                                                " VALUES("+intCodEmpDevVen+ ","+intCodLocDevVen+","+txtCodTipDoc.getText()+ ","+intCodDoc+", "+(i+1)+", '"+ 
                                                tblDat.getValueAt(i, INT_TBL_DESUNI)+ "',"+tblDat.getValueAt(i, INT_TBL_CODITM) + "," +
                                                tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +tblDat.getValueAt(i, INT_TBL_CODALT) + "','" +
                                                tblDat.getValueAt(i, INT_TBL_CODALT2) + "'," +objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM)) + "," +tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " + 
                                                objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREDEV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREDEV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PREDEV).toString()) ),6) + ", " +
                                                objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANVEN)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANVEN).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANVEN).toString()) ),6) + ", " +
                                                objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) ),6) + ", " +
                                                objInvItm.getCostoItm(conn, intCodEmpDevVen, Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM))) +", "+
                                                objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()) ),2) + ", '" +
                                                ((tblDat.getValueAt(i, INT_TBL_IVA).toString().equals("true"))?"S":"N") + "'  " +
                                                ",'P',"+ objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_TOT)==null)?"0":(tblDat.getValueAt(i, INT_TBL_TOT).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TOT).toString()) ),2) + ","+
                                                dlbcostouni+","+bldcostot+"," +bldcostot+" ,"+
                                                Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_CANDIFDEV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANDIFDEV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANDIFDEV).toString()) ) +",'I' " +
                                                " ,"+txtCodLoc.getText()+", "+txtCodTipDocSol.getText()+", "+txtCodDocSol.getText()+","+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+ " )"; 
                                        txtsql.append( strSQL + " ; \n");

                                        strSQL =" UPDATE tbm_detsoldevven SET nd_CanAceIngSis=nd_CanAceIngSis+"+bldcanmov+"  WHERE co_emp="+intCodEmpDevVen+" " +
                                                " AND co_loc="+txtCodLoc.getText()+" AND co_tipdoc="+txtCodTipDocSol.getText()+" AND co_Doc="+txtCodDocSol.getText()+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREGSOL)+" ";
                                        txtsql.append( strSQL + " ; \n");
                                    }
                                }
                            }
                        }
                    }
                }   
                //***************************************************************************************  


                stmLoc.executeUpdate(txtsql.getText());
                txtsql.setText("");

                if(intDevCom==1)
                {
                    System.out.println("DEV COM  : ");
                    if(!RealizarModficacionDevolucionOC(conn, stbins, intCodDoc,  txtCodTipDoc.getText() ))
                    {
                        blnRes=false;
                    }
                }

                System.out.println("Estado Ord Tra: "+intReaOrdTraAut );

                if(intReaOrdTraAut==1)
                { 
                    recostearItmGrp();
                    if(!RealizarModificacionOrdTraAut(conn, stbinsOrdTra, intCodDoc,  txtCodTipDoc.getText() ))
                    {
                        blnRes=false;
                    }
                } 
                stmLoc.close();
                stmLoc=null;
                stbins=null;
                stbinsOrdTra=null;     
            }
        }
        catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }



    private boolean RealizarModificacionOrdTraAut(java.sql.Connection conn, StringBuffer stbins, int intCodDocDev, String strTipDoc )
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        double dblTot=0;
        double dblTotCab=0, dblCosUni=0;
        int intEst=0;
        int intSer=0;
        int intEstMov=0;
        int INTCODEMPGRP=0;
        String strItmPro="";
        try
        {
            if(conn!=null)
            {
                stmLoc = conn.createStatement();
                INTCODEMPGRP=objParSis.getCodigoEmpresaGrupo();

                StringBuffer stbDetOrd=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbConStk=new StringBuffer(); //VARIABLE TIPO BUFFER

                StringBuffer stbinvemp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodemp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodgrp=new StringBuffer(); //VARIABLE TIPO BUFFER

                StringBuffer stbinvemp2=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodemp2=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvgrp2=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbinvbodgrp2=new StringBuffer(); //VARIABLE TIPO BUFFER
                StringBuffer stbDetRel=new StringBuffer(); //VARIABLE TIPO BUFFER

                strSql= "select * " +
                        " ,( select co_itm from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and ne_numfil in ( " +
                        "  select ne_numfil from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and co_reg=x.coregrel " +
                        " ) and nd_can < 0 " +
                        " ) as coitm2 " +
                        ",( " +
                        " select tx_codalt from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and ne_numfil in ( " +
                        "  select ne_numfil from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and co_reg=x.coregrel " +
                        " ) and nd_can < 0 " +
                        " ) as tx_codalt2 " +
                        ",(  " +
                        " select co_reg from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and ne_numfil in ( " +
                        "  select ne_numfil from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and co_reg=x.coregrel " +
                        " ) and nd_can < 0 " +
                        " ) as co_reg2 " +
                        " ,( " +
                        " select co_itm from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and ne_numfil in ( " +
                        "   select ne_numfil from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and co_reg=x.coregrel " +
                        " ) and nd_can > 0 " +
                        " ) as coitm3 " +
                        " ,( " +
                        " select tx_codalt from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and ne_numfil in ( " +
                        "   select ne_numfil from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and co_reg=x.coregrel " +
                        " ) and nd_can > 0 " +
                        " ) as tx_codalt3 " +
                        " ,( " +
                        " select co_reg from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and ne_numfil in ( " +
                        "   select ne_numfil from tbm_detmovinv where co_emp=x.coemp and co_loc=x.colocrel and co_tipdoc=x.cotipdocrel and co_doc=x.codocrel and co_reg=x.coregrel " +
                        " ) and nd_can > 0 " +
                        " ) as co_reg3      " +           
                        " from( "+
                          "    "+stbins.toString()+" "+ 
                        //" select 1 as coemp, 4 as colocrel, 56 as cotipdocrel, 33 as codocrel, 1 as coregrel, 6639 as coitm, 1 as cobod, 10 as ndcan  " +
                        " ) as x  "; 

                System.out.println(" Orden de Trabajo : "+ strSql );

                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {  
                    if(intSer>0) stbDetRel.append(" UNION ALL ");
                       stbDetRel.append("SELECT "+intCodEmpDevVen+", "+intCodLocDevVen+", "+strTipDoc+", "+intCodDocDev+", "+rstLoc.getInt("coreg")+", "+
                                        "  "+intCodEmpDevVen+","+intCodLocDevVen+", "+intTipDocTraAut+", "+rstLoc.getDouble("codocrel")+"  ,"+rstLoc.getDouble("co_reg2")+", 'I' "  );


                        strSql= " UPDATE tbm_detmovinv SET nd_can=((abs(nd_can)+"+rstLoc.getDouble("ndcan")+")*-1) " +
                                " , nd_tot=(((abs(nd_can)+"+rstLoc.getDouble("ndcan")+")*-1)*nd_cosuni) "+
                                " , nd_costot=(((abs(nd_can)+"+rstLoc.getDouble("ndcan")+")*-1)*nd_cosuni)  "+
                                " , nd_costotgrp=(((abs(nd_can)+"+rstLoc.getDouble("ndcan")+")*-1)*nd_cosuni) "+ 
                                "  WHERE co_emp="+rstLoc.getDouble("coemp")+" and co_loc="+rstLoc.getDouble("colocrel")+" " +
                                "  and co_tipdoc="+rstLoc.getDouble("cotipdocrel")+" and co_doc="+rstLoc.getDouble("codocrel")+" " +
                                "  and co_reg="+rstLoc.getDouble("co_reg2")+" and co_itm="+rstLoc.getDouble("coitm2")+" ; ";
                        stbDetOrd.append(strSql);

                        strSql= " UPDATE tbm_detmovinv SET nd_can=((abs(nd_can)+"+rstLoc.getDouble("ndcan")+")) " +
                                ", nd_tot=(((abs(nd_can)+"+rstLoc.getDouble("ndcan")+"))*nd_cosuni) "+
                                ", nd_costot=(((abs(nd_can)+"+rstLoc.getDouble("ndcan")+"))*nd_cosuni)  "+
                                ", nd_costotgrp=(((abs(nd_can)+"+rstLoc.getDouble("ndcan")+"))*nd_cosuni) "+ 
                                "  WHERE co_emp="+rstLoc.getDouble("coemp")+" and co_loc="+rstLoc.getDouble("colocrel")+" " +
                                "  and co_tipdoc="+rstLoc.getDouble("cotipdocrel")+" and co_doc="+rstLoc.getDouble("codocrel")+" " +
                                "  and co_reg="+rstLoc.getDouble("co_reg3")+" and co_itm="+rstLoc.getDouble("coitm3")+" ; ";
                        stbDetOrd.append(strSql); 


                        ///***************** EMPRESA ************************/   
                        if (intSer>0)
                            stbinvemp.append(" UNION ALL ");
                            stbinvemp.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("coitm2")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                            stbinvbodemp.append(" UNION ALL ");
                            stbinvbodemp.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("cobod")+" as cobod,"+rstLoc.getInt("coitm2")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock ");

                        if (intSer>0)
                            stbinvemp2.append(" UNION ALL ");
                            stbinvemp2.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("coitm3")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                            stbinvbodemp2.append(" UNION ALL ");
                            stbinvbodemp2.append("SELECT "+intCodEmpDevVen+" AS coemp,"+rstLoc.getInt("cobod")+" as cobod,"+rstLoc.getInt("coitm3")+" AS coitm,"+rstLoc.getDouble("ndcan")+" AS stock ");


                        //***********************************GRUPO***********************/
                        if (intSer>0)
                            stbinvgrp.append(" UNION ALL ");
                            stbinvgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                                             "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm2")+")) as coitm," +
                                             ""+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                            stbinvbodgrp.append(" UNION ALL ");
                            stbinvbodgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                                                "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm2")+")) as coitm," +
                                                "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmpDevVen+" and co_bod="+rstLoc.getDouble("cobod")+") as cobod, "+
                                                ""+rstLoc.getDouble("ndcan")+" AS stock  " );

                        if (intSer>0)
                            stbinvgrp2.append(" UNION ALL ");
                            stbinvgrp2.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                                              "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm3")+")) as coitm," +
                                              ""+rstLoc.getDouble("ndcan")+" AS stock");

                        if (intSer>0)
                            stbinvbodgrp2.append(" UNION ALL ");
                            stbinvbodgrp2.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                                                 "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+intCodEmpDevVen+" and co_itm="+rstLoc.getInt("coitm3")+")) as coitm," +
                                                 "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+intCodEmpDevVen+" and co_bod="+rstLoc.getDouble("cobod")+") as cobod, "+
                                                 ""+rstLoc.getDouble("ndcan")+" AS stock  " );

                        if(intSer>0)
                            stbConStk.append(" UNION ALL ");

                        strSql= "SELECT SUM(a2.nd_stkAct) AS nd_stkAct, "+rstLoc.getInt("coitm2")+" as coitm " +
                                " FROM tbm_equInv AS a1 " +
                                " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) " +
                                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer) " +
                                " WHERE a3.co_emp="+intCodEmpDevVen+" AND a3.co_loc="+intCodLocDevVen+" " +
                                " AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv " +
                                " WHERE co_emp="+intCodEmpDevVen+" AND co_itm="+rstLoc.getInt("coitm2")+" )   ";   
                        stbConStk.append(strSql);

                        strSql= " UNION ALL SELECT SUM(a2.nd_stkAct) AS nd_stkAct, "+rstLoc.getInt("coitm3")+" as coitm " +
                                " FROM tbm_equInv AS a1 " +
                                " INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) " +
                                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer) " +
                                " WHERE a3.co_emp="+intCodEmpDevVen+" AND a3.co_loc="+intCodLocDevVen+" " +
                                " AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv " +
                                " WHERE co_emp="+intCodEmpDevVen+" AND co_itm="+rstLoc.getInt("coitm3")+" )   ";   
                        stbConStk.append(strSql);   

                        intSer=1;   

                        /*******************************************************************/
                }
                rstLoc.close();
                rstLoc=null;   

                if(intSer==1) // aumenta 
                {  
                    stmLoc.executeUpdate(stbDetOrd.toString()); 

                    strSql= "INSERT INTO tbr_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg,      co_emprel, co_locrel, co_tipdocrel , co_docrel " +
                            ", co_regrel, st_regrep ) " + stbDetRel.toString();
                    System.out.println(""+strSql);
                    stmLoc.executeUpdate(strSql); 

                    strSql ="Update tbm_inv set st_regrep='M', nd_stkAct = nd_stkAct - a.stock " +
                            " FROM ( " +
                            "SELECT sum(stock) as stock, coemp, coitm FROM ( "+stbinvemp.toString()+" ) AS x GROUP BY coemp, coitm" +
                            " ) AS a where  co_emp=a.coemp AND co_itm=a.coitm";
                    strSql+=" ; Update tbm_invBod set  nd_stkAct  = nd_stkAct - a.stock  "+ //, nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ( "+
                            " SELECT sum(stock) as stock, coemp, cobod, coitm FROM ( "+stbinvbodemp.toString()+" ) AS x GROUP BY coemp, cobod,  coitm" +     
                            " ) AS a where  co_emp=a.coemp  AND  co_bod=a.cobod and co_itm=a.coitm";
                    strSql+=" ; Update tbm_inv set st_regrep='M', nd_stkAct = nd_stkAct + a.stock " +
                            " FROM ("+ 
                            "SELECT sum(stock) as stock, coemp, coitm FROM ( "+stbinvemp2.toString()+" ) AS x GROUP BY coemp, coitm" +    
                            " ) AS a where  co_emp=a.coemp AND co_itm=a.coitm";
                    strSql+=" ; Update tbm_invBod set  nd_stkAct  = nd_stkAct + a.stock  "+ //, nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ("+
                            " SELECT sum(stock) as stock, coemp, cobod, coitm FROM ( "+stbinvbodemp2.toString()+" ) AS x GROUP BY coemp, cobod,  coitm" +     
                            " ) AS a where  co_emp=a.coemp  AND  co_bod=a.cobod and co_itm=a.coitm";

                    strSql+=" ; Update tbm_inv  set  st_regrep='M', nd_stkact = nd_stkact - a.stock " +
                            " FROM ( "+ 
                            " SELECT sum(stock) as stock, coempgrp, coitm FROM ( "+stbinvgrp.toString()+" ) AS x GROUP BY coempgrp, coitm" +     
                            " ) AS a where co_emp=a.coempgrp and co_itm = a.coitm";
                    strSql+=" ; Update tbm_invbod  set nd_stkact = nd_stkact - a.stock "+ // , nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ( "+
                            " SELECT sum(stock) as stock, coempgrp, cobod, coitm FROM ( "+stbinvbodgrp.toString()+" ) AS x GROUP BY coempgrp, cobod, coitm" +     
                            " ) AS a where co_emp=a.coempgrp AND co_itm=a.coitm AND co_bod=a.cobod";
                    strSql+=" ; Update tbm_inv  set  st_regrep='M', nd_stkact = nd_stkact + a.stock " +
                            " FROM ( "+
                            " SELECT sum(stock) as stock, coempgrp, coitm FROM ( "+stbinvgrp2.toString()+" ) AS x GROUP BY coempgrp, coitm" +     
                            " ) AS a where co_emp=a.coempgrp and co_itm = a.coitm";
                    strSql+=" ; Update tbm_invbod  set nd_stkact = nd_stkact + a.stock "+ // , nd_caningbod=nd_caningbod+a.caningbod " +
                            " FROM ( "+
                            " SELECT sum(stock) as stock, coempgrp, cobod, coitm FROM ( "+stbinvbodgrp2.toString()+" ) AS x GROUP BY coempgrp, cobod, coitm" +     
                            " ) AS a where co_emp=a.coempgrp AND co_itm=a.coitm AND co_bod=a.cobod";
                    System.out.println(" "+  strSql ); 
                    stmLoc.executeUpdate(strSql);  

                    strSql="SELECT * FROM ( "+stbConStk.toString()+"  ) as x  where x.nd_stkAct < 0 ";
                    rstLoc=stmLoc.executeQuery(strSql);
                    while(rstLoc.next())
                    {
                        intEstMov=1;
                        strItmPro=rstLoc.getString("coitm");
                    }  

                    if(intEstMov==1)
                    {
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="El item. "+strItmPro+" hay problema de stock. \n"+ 
                        " Comuníquese con el departemento de Sistemas.";
                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE); 
                        blnRes=false;
                    }
                }                

                stmLoc.close();
                stmLoc=null;

                stbinvemp=null;
                stbinvbodemp=null;
                stbinvgrp=null;
                stbinvbodgrp=null;
                stbinvemp2=null;
                stbinvbodemp2=null;
                stbinvgrp2=null;
                stbinvbodgrp2=null;
                stbDetOrd=null;
                stbConStk=null;
                stbDetRel=null;
            }
        }
        catch(java.sql.SQLException e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }




    private boolean RealizarModficacionDevolucionOC(java.sql.Connection con, StringBuffer stbins, int intCodDoc, String strCodTipDoc)
    {
        boolean blnRes=false;
        int intNumDoc=0,intTipDoc=0,intCodLoc=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        int intval=0;
        try
        {
            if(con!=null)
            {
                stmLoc = con.createStatement();

                strSql ="SELECT count(*) FROM ("+stbins.toString()+") AS x WHERE  x.codoc is not null";
                intval=0;
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                    intval=rstLoc.getInt(1);
                rstLoc.close();
                rstLoc=null;

                strSql =" SELECT codoc,codtipdoc,coloc,cab.st_reg ,cab.ne_numdoc FROM ( " +
                        " SELECT distinct(codoc),codtipdoc,coloc FROM ("+stbins.toString()+") AS x WHERE  x.codoc is not null " +
                        " ) AS x LEFT JOIN  tbm_cabMovInv aS cab ON (cab.co_emp="+intCodEmpDevVen+" and cab.co_loc=x.coloc AND cab.co_tipdoc=x.codtipdoc AND cab.co_doc=x.codoc) "; 
                if(intval>0) 
                {
                    rstLoc=stmLoc.executeQuery(strSql);
                    while(rstLoc.next())
                    {
                        intNumDoc=rstLoc.getInt("codoc");
                        intTipDoc=rstLoc.getInt("codtipdoc");
                        intCodLoc=rstLoc.getInt("coloc");

                        Ventas.ZafVen28.ZafCom04 obj = new  Ventas.ZafVen28.ZafCom04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis , intNumDoc , 65 , intTipDoc, intCodEmpDevVen, intCodLoc, con , stbins, intCodDoc, strCodTipDoc , 1 );
                        if(obj.acepta())
                            blnRes=true;
                        else
                        { 
                            blnRes=false;
                            break;
                        }

                         obj.dispose();
                         obj=null;
                    }
                    rstLoc.close();
                    rstLoc=null;
                }
            }
        }
        catch(java.sql.SQLException e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception e ){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }




    public boolean modificarRegPag(java.sql.Connection conn, int intCodDoc) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        String strSQL = "";
        String strFecha = "";
        try 
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();

                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

                strSQL= " UPDATE tbm_pagmovinv SET  fe_ven='"+strFecha+"', mo_pag="+txtTot.getText()+" "+
                        " WHERE co_emp = "+ intCodEmpDevVen+" AND co_loc = "+intCodLocDevVen+" "+ 
                        " AND co_tipDoc = "+txtCodTipDoc.getText()+" and co_doc ="+intCodDoc;
                stmLoc.executeUpdate(strSQL);

                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;         
    }







    public boolean validarDevMod(java.sql.Connection conn) 
    {
        boolean blnRes=true;
        java.sql.Statement strmLoc;
        java.sql.ResultSet rstLoc;
        String  strSql="";
        double dblCanMov=0;
        int intCodItm=0;
        double intNumDev=0;
        double dblCanOri=0;
        int intEst=0;
        String strItmPro="";
        int intEstMov=0;
        try
        {
            strmLoc=conn.createStatement();
            StringBuffer stbDatTbl=new StringBuffer(); //VARIABLE TIPO BUFFER

            if(radCan.isSelected())
            {
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                        double dblCanAceBod = objUti.redondear((tblDat.getValueAt(i,INT_TBL_CANDEVACEBOD)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_CANDEVACEBOD).toString())), 4 );    
                        if(dblCanAceBod > 0 )
                        {
                            intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                            double dblCanDev = objUti.redondear((tblDat.getValueAt(i,INT_TBL_CANDEV)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_CANDEV).toString())), 4 );    
                            strSql="SELECT "+intCodEmpDevVen+" AS coemp, "+txtCodLocRelFac.getText()+" AS coloc, "+
                                   " "+txtCodTipDocRelFac.getText()+" AS  cotipdoc, "+txtCodDocRelFac.getText()+" AS codoc, "+
                                   " "+intCodItm+" AS coitm, "+dblCanAceBod+" AS ndcan, "+dblCanDev+" AS ndcaningdev, "+txtCodDoc.getText()+" AS codocdev";     
                            if(intEst>0) stbDatTbl.append(" UNION ALL ");
                            stbDatTbl.append(strSql);
                            intEst=1;
                        }         
                    }
                }
                if(intEst==1)
                {
                    strSql= "SELECT coemp, coloc, cotipdoc, codoc, coitm, ndcan, (ndcandev+ndcaningdev) as ndcantotdev, inv.tx_codalt FROM ( " +
                            "   SELECT *, " +
                            "   ( " +
                            "        SELECT sum(abs(a2.nd_can)) as total  FROM tbr_cabmovinv as a " +
                            "        inner join tbm_cabmovinv as a1 on(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_doc=a.co_doc and a1.co_tipdoc=a.co_tipdoc  and a1.st_reg='A' and a1.st_tipdev not in('P','V') ) " +
                            "        inner join tbm_detmovinv as a2 on(a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_itm=x.coitm) " +
                            "        WHERE a.co_emprel=x.coemp and a.co_locrel=x.coloc and a.co_tipdocrel=x.cotipdoc and a.co_docrel=x.codoc and a.st_reg='A'  and a.co_doc != x.codocdev  " +
                            "   ) AS ndcandev " +
                            "   FROM ( " +
                            "     SELECT sum(ndcan) AS ndcan, sum(ndcaningdev) AS ndcaningdev,  coemp, coloc, cotipdoc, codoc, coitm, codocdev   " +
                            "     FROM ( "+stbDatTbl.toString()+" "+
                            "      ) AS x GROUP BY coemp, coloc, cotipdoc, codoc, coitm, codocdev " +
                            "   ) AS x " +
                            " ) AS x INNER JOIN tbm_inv AS inv ON(inv.co_emp=x.coemp AND inv.co_itm=x.coitm)";
                            System.out.println(""+strSql );
                            rstLoc = strmLoc.executeQuery(strSql);
                            while(rstLoc.next())
                            {
                                if( rstLoc.getDouble("ndcantotdev") > rstLoc.getDouble("ndcan") ) 
                                {
                                    intEstMov=1;
                                    strItmPro=rstLoc.getString("tx_codalt");
                                }
                            }
                } 
                if(intEstMov==1)
                {
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Este Item  "+strItmPro+"  Ya tiene devoluciones y la cantidad a devolver es mayor";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                    blnRes=false;
                }  

                //<editor-fold defaultstate="collapsed" desc="/* Comentado */">
                /*
                for(int i=0; i<tblDat.getRowCount();i++){
                 if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                  if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                   if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                     dblCanMov=Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString());
                     if(dblCanMov > 0.00 ){
                          intCodItm = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                           strSql="select sum(b.nd_can) as total from tbr_cabmovinv as a"+ 
                           " inner join tbm_cabmovinv as b1 on(a.co_emp=b1.co_emp and a.co_loc=b1.co_loc and a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev not in('P','V') )" +
                           " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
                           " where a.co_docrel="+txtCodDocRelFac.getText()+" and a.co_emp="+intCodEmpDevVen+" and "+
                           " a.co_tipdocrel="+txtCodTipDocRelFac.getText()+" and a.co_doc != "+txtCodDoc.getText()+" and co_itm="+intCodItm+" and a.st_reg='A'";
                           System.out.println(" >>  "+ strSql  );
                            rstLoc = strmLoc.executeQuery(strSql);
                            intNumDev=0; 
                            if(rstLoc.next())
                                intNumDev = rstLoc.getDouble("total"); 
                            rstLoc.close();
                            rstLoc=null;
                            intNumDev=intNumDev+dblCanMov;
                            dblCanOri = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANVEN).toString());

                            if(intNumDev > dblCanOri ){
                                javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                                String strTit, strMsg;
                                strTit="Mensaje del sistema Zafiro";
                                strMsg="Este Item  "+ tblDat.getValueAt(i, 1) + "  Ya tiene devoluciones y la cantidad a devolver es mayor";
                                oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                blnRes=false;
                                break;
                }}}}}}
                */
                //</editor-fold>
            }   

            if(radDes.isSelected())
            {
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                        if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                            if(!tblDat.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString());
                                if( dblCanMov > 0.00 )
                                {
                                    intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                                    strSql= " select sum(b.nd_can) as TotDes from tbr_cabmovinv as a"+ 
                                            " inner join tbm_cabmovinv as b1 on(a.co_emp=b1.co_emp and a.co_loc=b1.co_loc and a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev in('P') )" +
                                            " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
                                            " where a.co_docrel="+txtCodDocRelFac.getText()+" and a.co_emp="+intCodEmpDevVen+" and "+
                                            " a.co_tipdocrel="+txtCodTipDocRelFac.getText()+" and a.co_doc != "+txtCodDoc.getText()+" and co_itm="+intCodItm+" and a.st_reg='A'";
                                    rstLoc = strmLoc.executeQuery(strSql);
                                    intNumDev=0; 
                                    if(rstLoc.next())
                                          intNumDev = rstLoc.getDouble("TotDes"); //Almacena el total de descuentos 
                                     rstLoc.close();
                                     rstLoc=null;                            
                                     intNumDev=intNumDev+dblCanMov;
                                     double dblPorDes = 100; // 100 es el Porcentaje maximo.
                                     if(intNumDev > dblPorDes )
                                     {
                                        String strTit, strMsg;
                                        strTit="Mensaje del sistema Zafiro";
                                        strMsg="Este Item  "+ tblDat.getValueAt(i, 1) + "  Ya tiene devoluciones por descuento y la cantidad a devolver es mayor al 100% ";
                                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        blnRes=false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(radPre.isSelected())
            {
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                        if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                            if(!tblDat.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString());
                                if( dblCanMov > 0.00 )
                                {
                                    intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                                    strSql =" select sum(b.nd_can) as TotVal from tbr_cabmovinv as a"+ 
                                            " inner join tbm_cabmovinv as b1 on(a.co_emp=b1.co_emp and a.co_loc=b1.co_loc and a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev in('V') )" +
                                            " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
                                            " where a.co_docrel="+txtCodDocRelFac.getText()+" and a.co_emp="+intCodEmpDevVen+" and "+
                                            " a.co_tipdocrel="+txtCodTipDocRelFac.getText()+" and a.co_doc != "+txtCodDoc.getText()+" and co_itm="+intCodItm+" and a.st_reg='A'";
                                    rstLoc = strmLoc.executeQuery(strSql);
                                    intNumDev=0; 
                                    if(rstLoc.next())
                                        intNumDev = rstLoc.getDouble("TotVal"); 
                                    rstLoc.close();
                                    rstLoc=null;                         
                                    intNumDev=intNumDev+dblCanMov;
                                    double dblPreOri = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_PREUNI).toString());
                                    if(intNumDev > dblPreOri ) 
                                    {
                                        String strTit, strMsg;
                                        strTit="Mensaje del sistema Zafiro";
                                        strMsg="Este Item  "+ tblDat.getValueAt(i, 1) + "  Ya tiene devoluciones por precio y la cantidad a devolver es mayor a "+tblDat.getValueAt(i,INT_TBL_PREUNI);
                                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        blnRes=false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }     
            stbDatTbl=null;
            strmLoc.close();
            strmLoc=null;
        }
        catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }


    private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst)
    {
       colBack = txtFiel.getBackground();
       txtFiel.setEditable(blnEst);
       txtFiel.setBackground(colBack);
    }


    private void noEditable(boolean blnEst)
    {
        Color colBack = txtCodLoc.getBackground();
        bloquea(txtCodLoc, colBack, blnEst);
        bloquea(txtNomLoc, colBack, blnEst);
        bloquea(txtDesCorTipDocSol, colBack, blnEst);
        bloquea(txtDesLarTipDocSol, colBack, blnEst);
        bloquea(txtCodCli, colBack, blnEst);
        bloquea(txtNomCli, colBack, blnEst);
        bloquea(txtCodSol, colBack, blnEst);
        bloquea(txtDesSol, colBack, blnEst);
        bloquea(txtFecDocSol, colBack, blnEst);
        bloquea(txtCodDocSol, colBack, blnEst);
        bloquea(txtNumDocFac, colBack, blnEst);
        bloquea(txtCodMot, colBack, blnEst);
        bloquea(txtDesLarMot, colBack, blnEst);

        bloquea(txtSub, colBack, blnEst);
        bloquea(txtIva, colBack, blnEst);
        bloquea(txtTot, colBack, blnEst);

        radCan.setEnabled(blnEst);
        radDes.setEnabled(blnEst);
        radPre.setEnabled(blnEst);
        radDesEsp.setEnabled(blnEst);

        chkVolFac.setEnabled(blnEst);

        txaObs1.setEditable(false);
        txaObs2.setEditable(false);
    }






    public void cargarTipoDoc(int intVal) 
    {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                if (objParSis.getCodigoUsuario() == 1) 
                {
                    strSql= " SELECT doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
                            "        case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
                            "        ,doc.tx_natdoc, doc.st_meringegrfisbod " +
                            " FROM tbr_tipdocprg as menu " +
                            " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
                            " WHERE menu.co_emp = "+intCodEmpDevVen + 
                            " AND menu.co_loc = " + intCodLocDevVen+
                            " AND menu.co_mnu = "+objParSis.getCodigoMenu()+
                            " AND menu.st_reg = 'S' ";
                }
                else
                {
                    strSql= " SELECT doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
                            "        case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
                            "        ,doc.tx_natdoc, doc.st_meringegrfisbod " +
                            " FROM tbr_tipDocUsr as menu " +
                            " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
                            " WHERE menu.co_emp =" +intCodEmpDevVen +
                            " AND menu.co_loc   =" + intCodLocDevVen+
                            " AND menu.co_mnu   =" +objParSis.getCodigoMenu()+
                            " AND menu.co_usr   =" +objParSis.getCodigoUsuario()+
                            " AND menu.st_reg   = 'S' ";
                }          
                System.out.println("ZafVen28.cargarTipoDoc: "+ strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc")==null)?"":rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor")==null)?"":rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar")==null)?"":rstLoc.getString("tx_deslar")));
                    strCodTipDoc=txtCodTipDoc.getText();
                    strMerIngEgr=((rstLoc.getString("st_meringegrfisbod")==null)?"":rstLoc.getString("st_meringegrfisbod"));
                    strTipIngEgr=((rstLoc.getString("tx_natdoc")==null)?"":rstLoc.getString("tx_natdoc"));

                    if(intVal==1)
                       txtNumDoc.setText(((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc")));
                    objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc=null;
                rstLoc=null;
                conn.close();
                conn=null;
            }
        }
        catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
    }

    public void  clnTextos()
    {
        strCodTipDoc=""; strDesCorTipDoc=""; strDesLarTipDoc="";
        //strCodRes=""; strNomRes="";
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        //txtCodRes.setText("");
        //txtNomRes.setText("");
        txtCodDoc.setText("");
        txtNumDoc.setText("");
        txtFecDoc.setText("");
        txtObs1.setText("");
        txtObs2.setText("");
        txaObs1.setText("");
        txaObs2.setText("");

        txtCodLoc.setText("");
        txtNomLoc.setText("");
        txtCodTipDocSol.setText("");
        txtDesCorTipDocSol.setText("");
        txtDesLarTipDocSol.setText("");
        txtCodCli.setText("");
        txtNomCli.setText("");
        txtCodSol.setText("");
        txtDesSol.setText("");
        txtFecDocSol.setText("");
        txtNumDocSol.setText("");
        txtCodDocSol.setText("");
        txtNumDocFac.setText("");
        txtCodMot.setText("");
        txtDesLarMot.setText("");
        txtSub.setText("");
        txtIva.setText("");
        txtTot.setText("");
        
        /*AGREGADO POR COMPENSACION SOLIDARIA*/
        txtSubTotIvaGra2.setText("");
        txtSubTotIvaCero2.setText("");
        txtComSol.setText("");
        txtTotSinCom.setText("");
        /*AGREGADO POR COMPENSACION SOLIDARIA*/

        objTblMod.removeAllRows();
        objAsiDia.inicializar();
        objAsiDia.setEditable(false);
    }




    @Override
    public boolean cancelar() 
    {
        boolean blnRes=true;
        try 
        {
            if (blnHayCam || objTblMod.isDataModelChanged()) {
                if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
                    if (!isRegPro())
                        return false;
                }
            }
            if (rstCab!=null) {
                rstCab.close();
                if (STM_GLO!=null){
                    STM_GLO.close();
                    STM_GLO=null;
                }
                rstCab=null;

            }
        }
        catch (java.sql.SQLException e) {        objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {          objUti.mostrarMsgErr_F1(this, e);       }
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

        butFacEle.setEnabled(true);
        butGetXmlDevVta.setEnabled(true);
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
    public boolean afterInsertar() 
    {
        //<editor-fold defaultstate="collapsed" desc="/* 15/05/2015 CMATEO PARA ACTUALIZAR CAMPO CANTIDAD POR EGRESAR*/ ">
        //conIns.setAutoCommit(true);
        //Thread.sleep(20000);
        //Connection conUpd =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());                            
        //ZafThreadActInvBod objThrActInvBod= new ZafThreadActInvBod(conUpd);
        //objThrActInvBod.start();
        //</editor-fold>

        //Recosteo
        if (radCan.isSelected()) 
        {
            recostearItmGrp();
        }

        this.setEstado('w');
        cargarDatIng(intCodDocDevVen);
        txtCodDoc.setText(""+intCodDocDevVen);
        enviarCorreoFaltantes();
        //enviarPulsoFacturacionElectronica();

        return true;
    }

    @Override
    public boolean afterModificar() 
    {
       this.setEstado('w');
       return true;
    }

    @Override
    public boolean afterVistaPreliminar() {
        return true;
    }


    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
       String strTit;
       strTit="Mensaje del sistema Zafiro";
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
                        // blnRes=objTooBar.modificar();
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


    @Override
    public boolean consultar() 
    {
       return _consultar(FilSql());  //Solo modo Consulta.
    }


    private boolean _consultar(String strFil)
    {
        boolean blnRes=false;
        String strSql="";
        try
        {
            if(!validarTipDoc()) return false;
            abrirCon();
            if(CONN_GLO!=null) 
            {
                STM_GLO=CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

                strSql= " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " +
                        " FROM tbm_cabmovinv AS a  " +
                        " WHERE a.co_emp="+intCodEmpDevVen+" and a.co_loc="+intCodLocDevVen+" " +
                        " AND a.st_reg NOT IN('E') "+strFil+" ORDER BY a.ne_numdoc ";

                rstCab=STM_GLO.executeQuery(strSql);
                if(rstCab.next())
                {
                    rstCab.last();
                    setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    refrescaDatos(CONN_GLO, rstCab);
                    blnRes=true;
                }
                else
                {
                    setMenSis("0 Registros encontrados");
                    clnTextos();
                } 

                CerrarCon();
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

        //System.gc();
        Runtime.getRuntime().gc();
        return blnRes;
    }



    /*
     *
     * Refresca Datos
     */
    private boolean refrescaDatos(Connection conn, ResultSet rstDatSolCon)
    {
        boolean blnRes=false;
        Statement stmLoc;
        ResultSet rstLoc;
        ResultSet rstDatSol;
        String strSql="";
        Vector vecData;
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                strSql= " SELECT a.ne_numcot,  d.co_locrel, d.co_tipdocrel, d.co_docrel, d.st_volfacmersindev " +
                        " ,a.co_locrelsoldevven, a.co_tipdocrelsoldevven, a.co_docrelsoldevven  " +
                        " ,a1.tx_nom as tx_desloc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, d.co_usrsol, d.tx_nomusrsol, a.fe_doc, d.fe_doc as fe_docsol, d.ne_numdoc, d.co_doc  "+      
                        " ,a.st_tipdev, a.st_reg, a.co_com, a.tx_nomven, a.tx_ate, a.tx_dircli, a.tx_ciucli, a.tx_telcli, a.tx_ruc, a.tx_obs1, a.tx_obs2  "+       
                        " ,a.nd_sub, a.nd_valiva, a.nd_tot, a.nd_poriva, c.st_ivaven, a.co_loc, a.co_doc as co_docDev, a.ne_numdoc as ne_numdocDev "+
                        //" ,a.co_motDev, motdev.tx_deslar as tx_desLarMotDev "+
                        " ,a.co_motDev, motdev.tx_deslar as tx_desLarMotDev, a.nd_valcomsol, a.nd_subivacer, a.nd_subivagra "+
                        " FROM tbm_cabmovinv AS a " +
                        " LEFT JOIN  tbm_cabsoldevven AS d ON(d.co_emp=a.co_emp AND d.co_loc=a.co_locrelsoldevven AND d.co_tipdoc=a.co_tipdocrelsoldevven AND d.co_doc=a.co_docrelsoldevven ) "+
                        " LEFT JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrelsoldevven) " +
                        " LEFT JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_locrelsoldevven AND a2.co_tipdoc=a.co_tipdocrelsoldevven) " +        
                        " INNER JOIN tbm_cli AS c ON (c.co_emp=a.co_emp AND c.co_cli=a.co_cli) "+
                        " LEFT JOIN tbm_motdevinv AS motdev ON (motdev.co_emp=a.co_emp AND motdev.co_motdev=a.co_motdev) "+
                        " WHERE a.co_emp="+rstDatSolCon.getString("co_emp")+" AND a.co_loc="+rstDatSolCon.getString("co_loc")+"  AND " +
                        " a.co_tipdoc="+rstDatSolCon.getString("co_tipdoc")+" AND a.co_doc="+rstDatSolCon.getString("co_doc");
                        //System.out.println( " > "+ strSql );
                rstDatSol=stmLoc.executeQuery(strSql);
                if(rstDatSol.next())
                {
                    strAux=rstDatSol.getString("st_reg");

                    txtCodLoc.setText(rstDatSol.getString("co_locrelsoldevven"));
                    txtNomLoc.setText(rstDatSol.getString("tx_desloc"));
                    txtCodTipDocSol.setText(rstDatSol.getString("co_tipdocrelsoldevven"));
                    txtDesCorTipDocSol.setText(rstDatSol.getString("tx_descor"));
                    txtDesLarTipDocSol.setText(rstDatSol.getString("tx_deslar"));
                    txtCodCli.setText(rstDatSol.getString("co_cli"));
                    txtNomCli.setText(rstDatSol.getString("tx_nomcli"));
                    txtCodSol.setText(rstDatSol.getString("co_usrsol"));
                    txtDesSol.setText(rstDatSol.getString("tx_nomusrsol"));
                    txtFecDocSol.setText(rstDatSol.getString("fe_docsol"));
                    txtCodDocSol.setText(rstDatSol.getString("co_doc"));
                    txtNumDocSol.setText(rstDatSol.getString("ne_numdoc"));
                    txtCodComFac.setText( rstDatSol.getString("co_com"));
                    txtNomVenFac.setText( rstDatSol.getString("tx_nomven"));
                    txtAteFac.setText( rstDatSol.getString("tx_ate"));
                    txtDirCliFac.setText( rstDatSol.getString("tx_dircli"));
                    txtCiuFac.setText( rstDatSol.getString("tx_ciucli"));
                    txtTelCliFac.setText( rstDatSol.getString("tx_telcli"));
                    txtRucFac.setText( rstDatSol.getString("tx_ruc"));

                    txtNumDocFac.setText( rstDatSol.getString("ne_numcot"));

                    txtCodMot.setText(rstDatSol.getString("co_motdev"));
                    txtDesLarMot.setText(rstDatSol.getString("tx_deslarmotdev"));

                    chkVolFac.setSelected( ( rstDatSol.getString("st_volfacmersindev")==null?false:(rstDatSol.getString("st_volfacmersindev").equals("S")?true:false)) );


                    // if(rstDatSol.getString("st_tipdev").equals("C")) radCan.setSelected(true);
                    // if(rstDatSol.getString("st_tipdev").equals("P")) radDes.setSelected(true);
                    // if(rstDatSol.getString("st_tipdev").equals("V")) radPre.setSelected(true);

                    if(rstDatSol.getString("st_tipdev").equals("C"))
                    { 
                         radCan.setSelected(true);
                         ocultaCol(INT_TBL_PREDEV) ; 
                         ocultaCol(INT_TBL_CANDES) ; 
                         MostrarCol(INT_TBL_CANDEV) ;
                    }
                    if(rstDatSol.getString("st_tipdev").equals("P"))
                    { 
                        radDes.setSelected(true);
                        ocultaCol(INT_TBL_PREDEV) ; 
                        MostrarCol(INT_TBL_CANDES) ; 
                        ocultaCol(INT_TBL_CANDEV) ;
                    }
                    if(rstDatSol.getString("st_tipdev").equals("V"))
                    { 
                        radPre.setSelected(true);
                        MostrarCol(INT_TBL_PREDEV) ; 
                        ocultaCol(INT_TBL_CANDES) ; 
                        ocultaCol(INT_TBL_CANDEV) ;
                    }
                    if(rstDatSol.getString("st_tipdev").equals("E"))
                    { 
                        radDesEsp.setSelected(true);
                        ocultaCol(INT_TBL_PREDEV) ; 
                        MostrarCol(INT_TBL_CANDES) ; 
                        ocultaCol(INT_TBL_CANDEV) ;
                    }           

                    txtObs1.setText(rstDatSol.getString("tx_obs1"));
                    txtObs2.setText(rstDatSol.getString("tx_obs2"));
                    txtCodDoc.setText(rstDatSol.getString("co_docDev"));
                    txtNumDoc.setText(rstDatSol.getString("ne_numdocDev"));

                    txtSub.setText(""+ objUti.redondear( rstDatSol.getString("nd_sub") , 2) );
                    txtIva.setText(""+ objUti.redondear( rstDatSol.getString("nd_valiva") , 2) );
                    txtTot.setText(""+ objUti.redondear( rstDatSol.getString("nd_tot") , 2) );
                    
                    /*COMPENSACION SOLIDARIA*/
                    txtComSol.setText(""+objUti.redondear((rstDatSol.getString("nd_valcomsol")!=null?rstDatSol.getString("nd_valcomsol"):"0"), 2));
                    txtSubTotIvaCero2.setText(""+ objUti.redondear(rstDatSol.getString("nd_subivacer")!=null?rstDatSol.getString("nd_subivacer"):"0",2));
                    txtSubTotIvaGra2.setText(""+ objUti.redondear(rstDatSol.getString("nd_subivagra")!=null?rstDatSol.getString("nd_subivagra"):"0",2));
                    txtTotSinCom.setText(""+objUti.redondear((objUti.redondear( rstDatSol.getString("nd_sub") , 2)+objUti.redondear( rstDatSol.getString("nd_valiva") , 2) - objUti.redondear( rstDatSol.getString("nd_valcomsol")!=null?rstDatSol.getString("nd_valcomsol"):"0" , 2) ),2));
                    /*COMPENSACION SOLIDARIA*/

                    dblPorIva=rstDatSol.getDouble("nd_poriva");
                    //lblIva.setText("IVA "+dblPorIva+" %");
                    stIvaVen = rstDatSol.getString("st_ivaven");


                    Date dateObj = rstDatSol.getDate("fe_doc");
                    if(dateObj==null)
                    {
                      txtFecDoc.setText("");  
                    }
                    else
                    {
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH)+1, calObj.get(Calendar.YEAR));
                    }

                    txtCodLocRelFac.setText(rstDatSol.getString("co_locrel"));
                    txtCodTipDocRelFac.setText(rstDatSol.getString("co_tipdocrel"));
                    txtCodDocRelFac.setText(rstDatSol.getString("co_docrel"));
                }
                rstDatSol.close();
                rstDatSol=null;


                vecData = new Vector();


                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(a.tx_codalt), length(a.tx_codalt) ,1)) IN ( " +
                                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                                " WHERE a.co_emp="+intCodEmpDevVen+" and a.co_loc="+intCodLocDevVen+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                                " THEN 'S' ELSE 'N' END AS proconf  ";


                strSql= " SELECT a2.co_locrel, a2.co_tipdocrel ,a2.co_Docrel, a2.co_regrel," +
                        "  x.co_locrel as colocot, x.co_tipdocrel as cotipdocot ,x.co_Docrel as codocot, x.co_regrel as coregot, " +
                        "  a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a3.nd_candevprv, a3.nd_cannodevprv, a3.nd_cansolnodevprv  " +
                        // "  ,case when a3.nd_canRevBodAce >=  a3.nd_candevprv then a3.nd_candevprv  else  a3.nd_canRevBodAce end  as nd_candevprv_1 "+
                        "  ,a3.nd_candevprv  as nd_candevprv_1 "+
                        "  , case when a3.nd_cansolnodevprv > 0 then  a3.nd_canRevBodAce  else 0 end  as nd_cansolnodevprv_1  "+
                        " ,a.co_bod ,a1.tx_nom, a.nd_can, a.nd_canorg, a.nd_preuni, a.nd_pordes, a.st_ivacom, a.co_itmact, a.nd_cosunigrp, a.tx_codalt2  " +
                        " ,b.st_ser, a.co_reg, a.co_regrelsoldevven,  a.nd_tot ,a3.nd_canRevBodAce, a3.nd_CanAceIngSis, a3.nd_candev, a3.st_devAutMerPrv  "+
                        " ,( SELECT sum(s.nd_CanAceIngSis) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotIngSis "+
                        " ,( SELECT sum(s.nd_canRevBodAce) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotBodAce "+
                        " ,( SELECT sum(s.nd_candev) FROM tbm_detsoldevven as s WHERE s.co_emp=a.co_emp AND  s.co_loc=a.co_locrelsoldevven  AND s.co_tipdoc=a.co_tipdocrelsoldevven AND s.co_doc=a.co_docrelsoldevven ) as sumTotCanDev "+
                        " "+strAux2+" "+        
                        " FROM tbm_detmovinv AS a " +
                        " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) " +         
                        " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+       
                        " LEFT JOIN tbr_detmovinv  AS a2 ON(a2.co_emp=a.co_emp and a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc and a2.co_reg=a.co_reg and a2.co_tipdocrel=4)   "+ /*co_tipDoc=4  "DEVCOM"*/
                        " LEFT JOIN tbr_detmovinv  AS x ON(x.co_emp=a.co_emp and x.co_loc=a.co_loc AND x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_reg=a.co_reg and x.co_tipdocrel=116)    "+ /*co_tipDoc=4  OTAUTO ; Ordenes de trabajo automáticas*/
                        " LEFT JOIN tbm_detsoldevven  AS a3 ON(a3.co_emp=a.co_emp and a3.co_loc=a.co_locrelsoldevven AND a3.co_tipdoc=a.co_tipdocrelsoldevven and a3.co_doc=a.co_docrelsoldevven and a3.co_reg=a.co_regrelsoldevven ) "+
                        " WHERE a.co_emp="+rstDatSolCon.getString("co_emp")+" AND a.co_loc="+rstDatSolCon.getString("co_loc")+"  AND " +
                        " a.co_tipdoc="+rstDatSolCon.getString("co_tipdoc")+" AND a.co_doc="+rstDatSolCon.getString("co_doc");
                System.out.println(" =: "+ strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    Vector vecReg = new Vector();

                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
                    vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
                    vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
                    vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed") );
                    vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod") );
                    vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
                    vecReg.add(INT_TBL_CANVEN, rstLoc.getString("nd_canorg") );
                    vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni") );

                    vecReg.add(INT_TBL_PREDEV, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes") );
                    vecReg.add(INT_TBL_CANDES, rstLoc.getString("nd_can") );

                    vecReg.add(INT_TBL_IVA, (rstLoc.getString("st_ivacom").equals("S")?true:false));
                    vecReg.add(INT_TBL_TOT, rstLoc.getString("nd_tot") );
                    vecReg.add(INT_TBL_CODITMACT, rstLoc.getString("co_itmact") );
                    vecReg.add(INT_TBL_COSTO, rstLoc.getString("nd_cosunigrp") );
                    vecReg.add(INT_TBL_CODALT2, rstLoc.getString("tx_codalt2") );
                    vecReg.add(INT_TBL_ITMSER, rstLoc.getString("st_ser") );
                    vecReg.add(INT_TBL_CANDIFDEV, "" );
                    vecReg.add(INT_TBL_CODREGSOL, rstLoc.getString("co_regrelsoldevven") );
                    vecReg.add(INT_TBL_CODLOC_OC, rstLoc.getString("co_locrel") );
                    vecReg.add(INT_TBL_CODTIP_OC, rstLoc.getString("co_tipdocrel") );
                    vecReg.add(INT_TBL_CODDOC_OC, rstLoc.getString("co_Docrel") );
                    vecReg.add(INT_TBL_CODREG_OC, rstLoc.getString("co_regrel") );
                    vecReg.add(INT_TBL_CODREGDEV, rstLoc.getString("co_reg") );
                    vecReg.add(INT_TBL_CANDEV_ORI, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_CANDEVACEBOD, rstLoc.getString("nd_canRevBodAce") );
                    vecReg.add(INT_TBL_ACEINGSIS, rstLoc.getString("nd_CanAceIngSis") );
                    vecReg.add(INT_TBL_SUMTOTINGSIS, rstLoc.getString("sumTotIngSis") );
                    vecReg.add(INT_TBL_SUMTOTBODACE, rstLoc.getString("sumTotBodAce") );
                    vecReg.add(INT_TBL_CANDEVPREDES_SOL, rstLoc.getString("nd_candev") );
                    vecReg.add(INT_TBL_CANDEVPREDES_ORI, rstLoc.getString("nd_can") );
                    vecReg.add(INT_TBL_SUMTOTCANDEV, rstLoc.getString("sumTotCanDev") );
                    vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));
                    vecReg.add(INT_TBL_DEVPRVAUT, rstLoc.getString("st_devAutMerPrv"));
                    vecReg.add(INT_TBL_SEDEVPRV, rstLoc.getString("nd_candevprv_1"));   // nd_candevprv_1
                    vecReg.add(INT_TBL_NODEVPRV, rstLoc.getString("nd_cannodevprv"));

                    vecReg.add(INT_TBL_CODLOC_OT, rstLoc.getString("colocot") );
                    vecReg.add(INT_TBL_CODTIP_OT, rstLoc.getString("cotipdocot") );
                    vecReg.add(INT_TBL_CODDOC_OT, rstLoc.getString("codocot") );
                    vecReg.add(INT_TBL_CODREG_OT, rstLoc.getString("coregot") );
                    vecReg.add(INT_TBL_CANSOLSTOCK, rstLoc.getString("nd_cansolnodevprv_1") );  // nd_cansolnodevprv_1

                    vecReg.add(INT_TBL_CODCTAEGR, null );
                    vecReg.add(INT_TBL_TXTCTAEGR, null );
                    vecReg.add(INT_TBL_DESCTAEGR, null );

                    /*vecReg.add(INT_TBL_CANNUNRECFAC,null );
                    vecReg.add(INT_TBL_CANFAC,null );*/

                    vecData.add(vecReg); 
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                rstLoc.close(); 
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                objAsiDia.consultarDiario(intCodEmpDevVen, intCodLocDevVen, Integer.parseInt(txtCodTipDoc.getText()), rstDatSolCon.getInt("co_doc") );

                if (strAux.equals("A"))
                    strAux="Activo";
                else if (strAux.equals("I"))
                    strAux="Anulado";
                else if (strAux.equals("E"))
                    strAux="Eliminado";
                else
                    strAux="Otro";
                objTooBar.setEstadoRegistro(strAux);

                int intPosRel=rstDatSolCon.getRow();
                rstDatSolCon.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatSolCon.getRow());
                rstDatSolCon.absolute(intPosRel);
            }
        }
        catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }





    private String FilSql() 
    {
        String sqlFiltro = "";
        //Agregando filtro por Numero de Cotizacion
        if(!txtCodTipDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and a.co_tipdoc="+txtCodTipDoc.getText();

        if(!txtCodDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and a.co_doc="+txtCodDoc.getText();

        if(!txtNumDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and a.ne_numdoc="+txtNumDoc.getText();

        //Agregando filtro por Fecha
        if(txtFecDoc.isFecha())
        {
            int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and a.fe_doc = '" +  strFecSql + "'";
        }

        return sqlFiltro ;
    }

    @Override
    public void clickModificar()
    {
        setEditable(true);
        noEditable(false);

        java.awt.Color colBack;
        colBack = txtCodDoc.getBackground();

        bloquea(txtCodDoc, colBack, false);
        bloquea(txtDesCorTipDoc, colBack, false);
        bloquea(txtDesLarTipDoc, colBack, false);
        bloquea(txtNumDocSol, colBack, false);

        butBusTipDoc.setEnabled(false);
        butBusNumDocSol.setEnabled(false);

        this.setEnabledConsultar(false);

        objTblMod.setDataModelChanged(false);
        blnHayCam=false;
    }

    private boolean  verificaNumSRI()
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try
        {
            abrirCon();
            if(CONN_GLO!=null)
            {
                stmLoc=CONN_GLO.createStatement();

                strSql= " select tx_numserfac from tbm_datautsri where co_emp="+intCodEmpDevVen+" and " +
                        " co_loc="+intCodLocDevVen+" and co_tipdoc IN (1,228) and "+txtNumDocFac.getText()+" between ne_numdocdes and ne_numdochas ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                  blnRes=true;
                }
                else
                    MensajeInf("EL NÚMERO DE AUTORIZACIÓN DEL SRI NO ESTA INGRESADO\nCOMUNIQUESE CON LA PERSONA QUE INGRESA ESTA INFORMACIÓN (Cecilia)  ");

                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                CerrarCon();
            }
        }
        catch (java.sql.SQLException  Evt)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
        return blnRes;
    }


    @Override
    public boolean vistaPreliminar()
    {
        // José Marín 18/Nov/2014 PARCHE
        //enviarPulsoFacturacionElectronica();
        if(txtDesCorTipDoc.getText().equals("FACVEN"))
        {
            if(verificaNumSRI())
               cargarReporte(1);
        }
        else
        {
            cargarReporte(1);
        } // José Marín 18/Nov/2014 PARCHE
        return true;
    }


    @Override
    public boolean imprimir()
    {
        //enviarPulsoFacturacionElectronica();
        if(txtDesCorTipDoc.getText().equals("FACVEN")) // José Marín 18/Nov/2014 PARCHE
        {
            if(verificaNumSRI())
                cargarReporte(0);
        }
        else
        {
            cargarReporte(0);
        }// José Marín 18/Nov/2014 PARCHE

    return true;
    }


    private void cargarReporte(int intTipo)
    {
       if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.setIndFunEje(intTipo);
            objThrGUI.start();
        }
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

    public void cierraConnections(){

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
        if (!isCamVal())
            return false;

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

    //   Para adicionar el Tooltip
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_CODITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_CODALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_NOMITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DESUNI:
                    strMsg="Unidad de Medida";
                    break;
                case INT_TBL_CODBOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_NOMBOD:
                    strMsg="Nombre de la Bodega";
                    break;
                case INT_TBL_CANVEN:
                    strMsg="Cantidad vendida";
                    break;
                case INT_TBL_CANDEV:
                    strMsg="Cantidad a devolver";
                    break;
                case INT_TBL_PREUNI:
                    strMsg="Precio unitario";
                    break;
                case INT_TBL_PORDES:
                    strMsg="Porcentaje de descuento";
                    break;
                case INT_TBL_IVA:
                    strMsg="Iva";
                    break;
               case INT_TBL_TOT:
                    strMsg="Total";
                    break;   
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    public boolean consultarTransAut (java.sql.Connection conn, java.sql.Connection connRemota, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDocSol) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        String str_MerIEFisBod = "N";
        int intCodDoc = 0;
        int intNumDoc = 0;
        int intSecEmp = 0;
        int intSecGrp = 0;
        int intCodTipDocTra = 46;
        int intCodBodOri = 0;
        java.util.ArrayList arlReg;
        
        try 
        {
            if (conn!=null) 
            {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();

                StringBuffer stbTbl1;

                double dblTot;
                int intReg;
                int intNumFil;
                int intControl;
                int intActInv;

                dblTot=0;
                intReg=1;
                intNumFil=1;
                stbTbl1=new StringBuffer();
                intControl=0;
                intCodDoc=0;
                intActInv=0;
                Double dblCanVolFac=0.00;
                String strDevFalStk="";
                
                objInvItm.limpiarObjeto();
                objInvItm.inicializaObjeto();
                strCorEleTo = "";

                switch (intCodEmp) 
                {
                    case 1:
                        if (intCodLoc == 4) 
                        {
                            intCodBodDes = 14;
                        }
                        break;
                    case 2:
                        switch (intCodLoc) 
                        {
                            case 1:
                                intCodBodDes = 16;
                                strCorEleTo = ";administracion@castek.ec";
                                break;
                            case 4:
                                intCodBodDes = 17;
                                strCorEleTo = ";ventasmanta@castek.ec";
                                break;
                            case 6:
                                intCodBodDes = 18;
                                strCorEleTo = ";ventas_stodgo@castek.ec";
                                break;
                            /* AGREGADO CUENCA 04/nov/2014 */
 //                           case 10:
 //                               intCodBodDes = 28;
 //                               strCorEleTo = ";ventascuenca@castek.ec";
 //                               break;
                                /* AGREGADO CUENCA 04/nov/2014 */
                        }
                        break;
                    case 4:
                        if (intCodLoc == 3) 
                        {
                            intCodBodDes = 19;
                            strCorEleTo = ";ventas@dimulti.com";
                        }
                       break;
                }

                strSql =" SELECT a1.st_ser, a5.co_itmmae, a6.co_itm as co_itmgrp, a.co_itm, a1.tx_codalt, a1.tx_nomitm, a2.tx_descor as tx_unimed, a.co_bod as co_bodori, " + 
                        " a3.tx_nom as tx_bodori, "+intCodBodDes+" as co_boddes, a4.tx_nom as tx_boddst, " +
                        " a.nd_can, a.nd_candev, a1.nd_cosuni, (a.nd_can * a1.nd_cosuni) as costot , a.nd_canVolFac, a.st_devFalStk " + /* José Marín 7/May/2014 */
                        " FROM tbm_detsoldevven as a " +
                        " INNER JOIN tbm_inv as a1 on (a1.co_emp=a.co_emp  and a1.co_itm=a.co_itm) " +
                        " LEFT JOIN  tbm_var as a2 on (a2.co_reg=a1.co_uni) " +
                        " INNER JOIN tbm_bod as a3 on (a3.co_emp=a.co_emp and a3.co_bod=a.co_bod) " +
                        " INNER JOIN tbm_bod as a4 on (a4.co_emp=a.co_emp and a4.co_bod="+intCodBodDes+") " +
                        " INNER JOIN tbm_equinv as a5 on (a5.co_emp=a1.co_emp  and a5.co_itm=a1.co_itm) " +
                        " INNER JOIN tbm_equinv as a6 on (a6.co_emp=0  and a6.co_itmmae=a5.co_itmmae) " +
                        " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDocSol+
                        " and st_devfalstk = 'S' ";
                System.out.println("zafVen28.consultarTransAut" + strSql );
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next())
                {
                    if (intControl==0) 
                    {
                        intCodDoc = getCodigoDoc(conn, intCodEmp, intCodLoc, intCodTipDocTra );
                        strMensMerFal="<br>Se envia a Bodega de " + rstLoc.getString("tx_boddst") + " : <br><br>"
                        + txtDesLarTipDocSol.getText() + " No.: " + txtNumDocSol.getText() + "<br>"
                        + "Cliente: " + txtNomCli.getText() + "<br><br>"
                        + "<table border=1> <tr><td>ITEM</td><td>DESCRIPCION</td><td>CANTIDAD</td></tr>";                        
                    }
                    
                    strDevFalStk = rstLoc.getString("st_devFalStk");  // DEVOLUCION POR FALTA DE STOCK JOTA 
                    
                    dblCanVolFac = rstLoc.getDouble("nd_canVolFac"); //CANTIDAD VOLVER A FACTURAR JOTA 
                    
                    dblTot += rstLoc.getDouble("costot");
                    intCodBodOri = rstLoc.getInt("co_bodori");
                    Double dblCanDev=0.00;
                    
                    strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " ;
                    strSql+=" tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " ;
                    strSql+=" nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " ;
                    strSql+=" nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec, tx_nombodorgdes ) " ;
                    strSql+=" VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDocTra+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," ;
                    strSql+=" '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',";
                    strSql+=" "+rstLoc.getInt("co_bodori")+", " ;
                    if(strDevFalStk.equals("S"))
                    {
                        dblCanDev=rstLoc.getDouble("nd_candev");
                        dblCanDev=dblCanDev-dblCanVolFac;
                        strSql+=" " + dblCanDev*-1+", ";
                    }
                    else
                    {
                        strSql+=" " + rstLoc.getDouble("nd_candev")*-1+", ";
                    }
                    strSql+=""+rstLoc.getString("nd_cosuni")+", ";
                    strSql+=""+rstLoc.getString("nd_cosuni")+",";
                    strSql+=" 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")*-1+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")*-1+", "+rstLoc.getString("nd_cosuni")+"," ;
                    strSql+=" "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")*-1+",  0, 'I', "+rstLoc.getInt("co_itm")+", " ;
                    strSql+=" 'N', 0, '"+rstLoc.getString("tx_boddst")+"'  ) ; ";
                    System.out.println("zafVen28.consultarTransAut" + strSql );
                    stbTbl1.append( strSql );
                    intReg++;
                    str_MerIEFisBod="E";
                    if(rstLoc.getString("st_ser").equals("N"))
                    {
                        //intActInv=1;
                        //objInvItm.generaQueryStock(intCodEmp, rstLoc.getInt("co_itm"), rstLoc.getInt("co_bodori"), rstLoc.getDouble("nd_candev"),  -1 , "N", "N", str_MerIEFisBod, 1);
                        //Mateo
                        ZafStkInv invStock= new ZafStkInv(objParSis);
                        if(invStock.actualizaInventario(conn, intCodEmp, INT_ARL_STK_INV_STK_DIS, "-", 1, obtenerArrItmInv(conn, intCodEmp, rstLoc.getInt("co_itm"),rstLoc.getDouble("nd_candev"),rstLoc.getInt("co_bodori"))))
                        {
                            if(!invStock.actualizaInventario(conn, intCodEmp, INT_ARL_STK_INV_STK, "-", 0, obtenerArrItmInv(conn, intCodEmp, rstLoc.getInt("co_itm"),rstLoc.getDouble("nd_candev"),rstLoc.getInt("co_bodori")))){
                                  return false;
                            }
                        }
                        else
                        {
                            return false;
                        }     
                        //Mateo
                       
                    }

                    strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " ;
                    strSql+=" tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " ;
                    strSql+=" nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " ;
                    strSql+=" nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec, tx_nombodorgdes ) " ;
                    strSql+=" VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDocTra+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+",";
                    strSql+=" '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',";
                    strSql+=" "+rstLoc.getInt("co_boddes")+", " ;
                    
                    if(strDevFalStk.equals("S"))
                    {
                        dblCanDev=rstLoc.getDouble("nd_candev");
                        dblCanDev=dblCanDev-dblCanVolFac;
                        strSql+=" " + dblCanDev+", ";
                    }
                    else
                    {
                        strSql+=" " + rstLoc.getDouble("nd_candev") +", ";
                    }
                    //strSql+=" "+rstLoc.getDouble("nd_candev")+", ";
                    
                    strSql+=" "+rstLoc.getString("nd_cosuni")+", ";
                    strSql+=" "+rstLoc.getString("nd_cosuni")+",";
                    strSql+=" 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")+", "+rstLoc.getString("nd_cosuni")+"," ;
                    strSql+=" "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")+",  0, 'I', "+rstLoc.getInt("co_itm")+", " ;
                    strSql+=" 'N', 0, '"+rstLoc.getString("tx_bodori")+"'  ) ; ";
                    System.out.println("zafVen28.consultarTransAut" + strSql );
                    stbTbl1.append( strSql );
                    intReg++;
                    str_MerIEFisBod="I";
                    if(rstLoc.getString("st_ser").equals("N"))
                    {
                        intActInv=1;
                        objInvItm.generaQueryStock(intCodEmp, rstLoc.getInt("co_itm"), rstLoc.getInt("co_boddes"), rstLoc.getDouble("nd_candev"),  1 , "N", "N", str_MerIEFisBod, 1);
                    }

                    arlReg = new java.util.ArrayList();
                    arlReg.add(0, rstLoc.getInt("co_itmgrp") );
                    arlItmRec.add(arlReg);

                    strMensMerFal+="<tr><td>"+rstLoc.getString("tx_codalt")+"</td><td>"+rstLoc.getString("tx_nomitm")+"</td><td>";
                    if(strDevFalStk.equals("S"))
                    {
                        dblCanDev=rstLoc.getDouble("nd_candev");
                        dblCanDev=dblCanDev-dblCanVolFac;
                        strMensMerFal+=" " + dblCanDev+", ";
                    }
                    else
                    {
                        strMensMerFal+=" " + rstLoc.getDouble("nd_candev") +", ";
                    }
                    //  strMensMerFal+=" "+rstLoc.getDouble("nd_candev");
                    strMensMerFal+=" "+" </td></tr> ";
                    blnMerFal=true;
                    
                    intNumFil++;
                    intControl=1;
                }
                rstLoc.close();
                rstLoc=null;

                if (intControl==1) 
                {
                    intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDocTra );
                    if( insertarCabTrans(conn, intCodEmp, intCodLoc, intCodTipDocTra, intCodDoc, intNumDoc, dblTot, intSecEmp, intSecGrp ) )
                    { 
                        if(insertarCabDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDocTra, intCodDoc, intNumDoc ) ){  
                            if(insertarDetDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDocTra, intCodDoc, dblTot, intCodBodOri , intCodBodDes ) ){ 
                                stmLoc.executeUpdate(stbTbl1.toString());
                            }
                        }
                    }
                }
                stbTbl1=null;

                /*if (intActInv==1) 
                {
                    if (!objInvItm.ejecutaActStock(conn, 3))
                        return false;
                    
                    if (connRemota!=null) 
                    {
                        if(!objInvItm.ejecutaActStock(connRemota, 3 ))
                            return false;
                        if(!objInvItm.ejecutaVerificacionStock(connRemota, 3))
                            return false;
                    }
                    else
                    { 
                        if(!objInvItm.ejecutaVerificacionStock(conn, 3 )){ 
                           return false;
                        }
                    }
                }*/
                
                objInvItm.limpiarObjeto();

                blnRes=true;
                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;
            }
        }
        catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return  blnRes;
    }

    private int getCodigoDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc )
    {
        int intCodDoc=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                strSql=" SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
                       " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()) intCodDoc = rstLoc.getInt("co_doc");

                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
        catch(Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
        return intCodDoc;
    }

    private int getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc )
    {
        int intUltNumDoc=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try
        {
            if(conn!=null)
            {
                stmLoc=conn.createStatement();

                strSql="SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()) intUltNumDoc = rstLoc.getInt("numdoc");

                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
        catch(Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
        return intUltNumDoc;
    }

    private boolean insertarCabTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, double dlbTot , int intSecEmp, int intSecGrp  )
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        try
        {
            if(conIns!=null)
            {
                stmLoc=conIns.createStatement();

                java.util.Date datFecAux =objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                if(intDocRelEmpLoc==1) stbDocRelEmpLoc.append(" UNION ALL ");
                stbDocRelEmpLoc.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC " );
                intDocRelEmpLoc=1;

                strSql="INSERT INTO tbm_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, " +
                " nd_sub, nd_tot, nd_poriva, st_reg, fe_ing, co_usring, " +
                " ne_secemp, ne_secgrp, nd_valiva, st_recdoc, co_mnu, st_tipdev, st_coninv, " +
                " st_autingegrinvcon, st_imp, st_creguirem, st_coninvtraaut, st_docconmersaldemdebfac, st_regrep, tx_obs2   ) " +
                " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intNumDoc+" ,'"+datFecAux+"', " +
                " "+(dlbTot*-1)+", "+(dlbTot*-1)+", 0, 'A', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+objParSis.getCodigoUsuario()+", " +
                " "+intSecEmp+", "+intSecGrp+", 0, 'P', 779, 'C', 'C', 'N' , 'S', 'S', 'N', 'N', 'I', 'Transferencia generada por solicitud de devolución de ventas no. "+txtNumDocSol.getText()+" indicada como faltante de inventario.' ) ";

                strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" ";

                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

        return blnRes;
    }

    private boolean insertarCabDiaTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc )
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        try
        {
            if(conIns!=null)
            {
                stmLoc=conIns.createStatement();

                java.util.Date datFecAux =objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                strSql= " INSERT INTO tbm_cabdia( co_emp, co_loc, co_tipdoc, co_dia, tx_numdia, fe_dia, " +
                        " st_reg, fe_ing , co_usring, st_regrep, st_imp, st_usrmodasidiapre, st_asidiareg ) "+
                        " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+intNumDoc+"' ,'"+datFecAux+"', " +
                        " 'A', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+objParSis.getCodigoUsuario()+", 'I','N','N','S' )";
                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

        return blnRes;
    }

    private boolean insertarDetDiaTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dlbTot, int intBodOri, int intBodDes )
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        String strSql="", strSql2="";
        int intCodReg=1;
        int intCtaOri=0;
        int intCtaDes=0;

        try
        {
            if(conIns!=null)
            {
                stmLoc=conIns.createStatement();
                stmLoc01=conIns.createStatement();

                strSql2="select co_ctaexi from tbm_bod where co_emp="+intCodEmp+" and co_bod="+intBodOri+" ";
                rstLoc=stmLoc01.executeQuery(strSql2);
                if(rstLoc.next()){
                    intCtaOri= rstLoc.getInt("co_ctaexi");
                }
                rstLoc.close();
                rstLoc=null;

                strSql2="select co_ctaexi from tbm_bod where co_emp="+intCodEmp+" and co_bod="+intBodDes+" ";
                rstLoc=stmLoc01.executeQuery(strSql2);
                if(rstLoc.next()){
                    intCtaDes= rstLoc.getInt("co_ctaexi");
                }
                rstLoc.close();
                rstLoc=null;

                strSql="INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
                " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaOri+", " +
                " 0, "+dlbTot+", 'I' ) ; ";

                intCodReg++;
                strSql +=" INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
                " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaDes+", " +
                " "+dlbTot+", 0,  'I' ) ; ";
                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc=null;
                blnRes=true;
       
            }
        }
        catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

        return blnRes;
    }

    private boolean insertar_tbmCabOrdConInv(Connection con)
    {
        boolean blnRes=true;
        //int intNumDocOrdConImp;
        Statement stm;
        ResultSet rst;
        try
        {
            if(con!=null && blnMerFal)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) AS co_doc";
                strSQL+=" FROM tbm_cabordconinv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_ORD_CON;
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_CON;
                System.out.println("ZafVen28.insertar_tbmCabOrdConInv (1) " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodDocOrdCon=rst.getObject("co_doc")==null?0:rst.getInt("co_doc");
                else
                    intCodDocOrdCon=0;
                intCodDocOrdCon++;
                
                strSQL="";
                strSQL+="SELECT a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_ORD_CON;
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_CON;
                
                System.out.println("ZafVen28.insertar_tbmCabOrdConInv (2) " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumDocOrdConImp=rst.getObject("ne_ultDoc")==null?0:rst.getInt("ne_ultDoc");
                else
                    intNumDocOrdConImp=0;
                intNumDocOrdConImp++;
                
                strSQL="";
                strSQL+="INSERT INTO tbm_cabordconinv(";
                strSQL+=" co_emp, co_loc, co_tipdoc,";
                strSQL+=" co_doc, fe_doc, ne_numdoc,";
                strSQL+=" co_usrrescon, co_bod, tx_obs1,";
                strSQL+=" tx_obs2, st_reg, fe_ing,";
                strSQL+=" fe_ultmod, co_usring,";
                strSQL+=" co_usrmod, st_regrep";
                strSQL+=" , co_empRel, co_locRel, co_tipDocRel, co_docRel)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresaGrupo() + ",";//co_emp
                strSQL+="" + INT_COD_LOC_ORD_CON + ",";//co_loc
                strSQL+="" + INT_COD_TIP_DOC_ORD_CON + ",";//co_tipdoc
                strSQL+="" + intCodDocOrdCon + ",";//co_doc
                strSQL+="'" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL+="" + intNumDocOrdConImp + ",";//ne_numdoc
                strSQL+="Null,";//co_usrrescon
                strSQL+="" + intCodBodDes + ",";//co_bod
                strSQL+="Null,";//tx_obs1
                strSQL+="Null,";//tx_obs2
                strSQL+="'A',";//st_reg
                strSQL+="CURRENT_TIMESTAMP,";//fe_ing
                strSQL+="CURRENT_TIMESTAMP,";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL+="'I',";//st_regrep
                strSQL+="null,";//co_empRel
                strSQL+="null,";//co_locRel
                strSQL+="null,";//co_tipDocRel
                strSQL+="null";//co_docRel
                strSQL+=");";
                
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc="+intNumDocOrdConImp;
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND co_loc=" + INT_COD_LOC_ORD_CON;
                strSQL+=" AND co_tipDoc=" + INT_COD_TIP_DOC_ORD_CON;
                
                System.out.println("ZafVen28.insertar_tbmCabOrdConInv (3) " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){    objUti.mostrarMsgErr_F1(this, e);        blnRes=false;      }
        catch(Exception e){       objUti.mostrarMsgErr_F1(this, e);        blnRes=false;       }
        return blnRes;
    }

    private boolean insertar_tbmDetOrdConInv(Connection con)
    {
        boolean blnRes=true;
        String strSQLIns="";
        int j=0;
        int intUltReg=0;
        Statement stm;
        ResultSet rst;
        try
        {
            if(con!=null && blnMerFal)
            {
                stm=con.createStatement();
                
                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg) AS co_reg";
                strSQL+=" FROM tbm_coninv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                System.out.println("ZafVen28.insertar_tbmDetOrdConInv (1) " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltReg=rst.getObject("co_reg")==null?0:rst.getInt("co_reg");
                
                for(int i=0; i<arlItmRec.size(); i++)
                {
                    j++;
                    intUltReg++;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detordconinv(";
                    strSQL+=" co_emp, co_loc, co_tipdoc,";
                    strSQL+=" co_doc, co_reg, co_itm, st_regrep";
                    strSQL+=")";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresaGrupo() + ",";//co_emp
                    strSQL+="" + INT_COD_LOC_ORD_CON + ",";//co_loc
                    strSQL+="" + INT_COD_TIP_DOC_ORD_CON + ",";//co_tipdoc
                    strSQL+="" + intCodDocOrdCon + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + objUti.getStringValueAt(arlItmRec, i, 0) + ",";//co_itm
                    strSQL+="'I'";//st_regrep
                    strSQL+=");";
                    strSQL+="INSERT INTO tbm_coninv(";
                    strSQL+=" co_emp, co_reg, co_usrrescon,";
                    strSQL+=" co_bod, co_itm, fe_solcon,";
                    strSQL+=" tx_obs1, st_regrep, co_locRel, co_tipDocRel, co_docRel, co_regRel";                    
                    strSQL+=")";                    
                    strSQL+="VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresaGrupo() + ",";//co_emp
                    strSQL+="" + intUltReg + ",";//co_reg
                    strSQL+="Null,";//co_usrrescon;
                    strSQL+="" + intCodBodDes + ",";//co_bod;
                    strSQL+="" + objUti.getStringValueAt(arlItmRec, i, 0) + ",";//co_itm;
                    strSQL+="CURRENT_TIMESTAMP,";//fe_solcon;
                    strSQL+=" Null,";//tx_obs1;
                    strSQL+="'I',";//st_regrep;
                    strSQL+="" + INT_COD_LOC_ORD_CON + ",";//co_locRel
                    strSQL+="" + INT_COD_TIP_DOC_ORD_CON + ",";//co_tipDocRel
                    strSQL+="" + intCodDocOrdCon + ",";//co_docRel
                    strSQL+="" + j + "";//co_regRel            
                    strSQL+=");";
                    strSQLIns+=strSQL;
                }                
                System.out.println("ZafVen28.insertar_tbmDetOrdConInv (2) " + strSQL);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){        objUti.mostrarMsgErr_F1(this, e);        blnRes=false;    }
        catch(Exception e){         objUti.mostrarMsgErr_F1(this, e);         blnRes=false;        }
        return blnRes;
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
        String strRutRpt, strNomRpt, strFecHorSer, strLogo="", strNomEmpAux, strNomEmpRep;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            System.out.println("tipDoc" + txtCodTipDoc.getText() + " doc: " + txtCodDoc.getText());
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {  strNomEmpRep = "";
               strNomEmpAux = objParSis.getNombreEmpresa().toUpperCase();

               if (strNomEmpAux.indexOf("TUVAL") != -1)
               {  
                  strNomEmpRep = "Tuv";
               }
               else if (strNomEmpAux.indexOf("CASTEK") != -1)
               {  
                  strNomEmpRep = "Cas";
               }
               else if (strNomEmpAux.indexOf("DIMULTI") != -1)
               {  
                  strNomEmpRep = "Dim";
               }
               else if (strNomEmpAux.indexOf("COSENCO") != -1)
               {  
                  strNomEmpRep = "Cos";
               }
               else if (strNomEmpAux.indexOf("ECUATOSA") != -1)
               {  
                  strNomEmpRep = "Ecu";
               }
               else if (strNomEmpAux.indexOf("DETOPACIO") != -1)
               {  
                  strNomEmpRep = "Det";
               }

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
                                strLogo=objRptSis.getRutaReporte(i);
                                
                                System.out.println(" reporte " + strLogo );
                                //Inicializar los parametros que se van a pasar al reporte.

                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("codEmp", new Integer(intCodEmpDevVen) );
                                mapPar.put("codLoc", new Integer(intCodLocDevVen) );
                                mapPar.put("codTipDoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
                                mapPar.put("codDoc", new Integer( Integer.parseInt(txtCodDoc.getText())) );
                                mapPar.put("LOGO_DIR", strRutRpt + "Logos" + File.separator + "log" + strNomEmpRep + ".png");
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
    protected void finalize() throws Throwable {   
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
    }
    
    
    //--- Inicio bloque comentado el 22/Mar/2018 ---
    //Esta linea fue comentada en la version del programa v6.27 (22/Mar/2018)
    //
    // José Marín M. 19/Dic/2014 // Facturación Electronica
    /*
    private void enviarPulsoFacturacionElectronica()
    {
        objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciar();
        System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica DEVVENE  ");
    }
    */
    //--- Fin bloque comentado el 22/Mar/2018 ------
    
    /*15/05/2015 CMATEO PARA ACTUALIZAR CAMPO CANTIDAD POR EGRESAR*/
    private class ZafThreadActInvBod extends Thread
    {
        Connection conn=null;

        public ZafThreadActInvBod(Connection con)
        {
                this.conn=con;
        }

        @Override
        public void run()
        {
            try
            {
                System.out.println("ZafThreadActInvBod  ");
                //iniciarProceso();
                //sleep(intTiempoHiloDormido);  //   tiempo que se ejecuta el proceso, depende del tiempo que le pongan en configuracion se recomienda que se ponga una hora como minimo.
                actualizarTbmInvBod(conn);
                this.conn.close();
                this.conn=null;
            }
            catch(Exception e) {    e.printStackTrace();          }
        }
    }
 
 
 
    /**
      * Metodo que actualiza cada item del detalle de la devolucion (El campo actualizado es nd_canegrbod)
      * @param con conexion de acceso a datos.
      * @return boolean
      */   
    private boolean actualizarTbmInvBod( Connection con)
    {
        Statement stmLoc;
        ResultSet rstLoc;
        int intDoc=0;
        boolean retorno=true;
        String strUpd="";
        try
        {
            for(int i=0; i<tblDat.getRowCount();i++)
            {
                if(tblDat.getValueAt(i,INT_TBL_CODITM) != null )
                {
                    String itm=(String)tblDat.getValueAt(i,INT_TBL_CODALT);
                    strUpd=" update tbm_invbod set  nd_canegrbod=a.dif from ("+
                           " select x.co_emp, x.co_ptopar, x.co_itm, dif,  x1.nd_canegrbod  from ("+
                           " select x.co_emp, x.co_ptopar,  x.co_itm "+
                           " ,SUM(abs((abs(can) -  (abs(cancon)+abs(cannunrec)) ))) as dif "+
                           " from ("+
                           " select a1.co_emp, a.co_ptopar,  a1.co_itm"+
                           ", CASE WHEN a.fe_doc<='2012-07-31' THEN 0 ELSE sum(  (CASE WHEN a1.nd_can          IS NULL THEN 0 ELSE a1.nd_can          END) ) END AS can"+
                           ", CASE WHEN a.fe_doc<='2012-07-31' THEN 0 ELSE sum(  (CASE WHEN a1.nd_cancon        IS NULL THEN 0 ELSE a1.nd_cancon       END) ) END AS cancon"+
                           ", CASE WHEN a.fe_doc<='2012-07-31' THEN 0 ELSE sum(  (CASE WHEN a1.nd_cannunrec    IS NULL THEN 0 ELSE a1.nd_cannunrec    END) ) END AS cannunrec"+
                           ", CASE WHEN a.fe_doc<='2012-07-31' THEN 0 ELSE sum(  (CASE WHEN a1.nd_cantotguisec IS NULL THEN 0 ELSE a1.nd_cantotguisec END) ) END AS totguisec"+
                           " from tbm_cabguirem as a"+
                           " inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )"+
                           " inner join tbm_inv as inv1 on (a1.co_emp=inv1.co_emp and a1.co_itm=inv1.co_itm)"+
                           " where  "+
                           " a.co_ptopar in ( 1,2,3,4,5,6,7,8,9,10,11,12,13,15,28 )"+
                           " and a1.st_meregrfisbod='S' and a.st_coninv in ('P','E') and a.st_reg not in ('E','I')  "+
                           //" and a.fe_doc=current_date"+
                           //" and a1.co_itm ="+itm+
                           " and inv1.tx_codalt ='"+itm+"'"+
                           " group by a1.co_emp, a.co_ptopar,  a1.co_itm, a.fe_doc"+
                           "        ) as x"+
                           " GROUP BY x.co_emp, x.co_ptopar,  x.co_itm"+
                           ") as x  "+
                           " inner join tbm_invbod as x1 on (x1.co_emp=x.co_emp and x1.co_bod=x.co_ptopar and x1.co_itm=x.co_itm )"+
                           " where dif  <> x1.nd_canegrbod"+
                           " ORDER BY x.co_emp, x.co_itm, x.co_ptoPar"+
                           ") as a "+
                           "where tbm_invbod.co_emp=a.co_emp and tbm_invbod.co_bod=a.co_ptopar and tbm_invbod.co_itm=a.co_itm";

                    stmLoc=con.createStatement();
                    int intR=stmLoc.executeUpdate(strUpd);
                    //System.out.println("eje "+intR );
                }
            }
        }
        catch(Exception ex){   ex.printStackTrace();      retorno=false;      }
        return retorno;
    }
    /*15/05/2015 CMATEO PARA ACTUALIZAR CAMPO CANTIDAD POR EGRESAR*/ 

 
    /**
     * Genera una Devolución Automática (DEVVENE) al momento de autorizar o realizar la recepción de bodega.
     * @param intCodEmpSolDev
     * @param intCodLocSolDev
     * @param intCodTipDocSolDev
     * @param intCodDocSolDev
     * @return 
     */
    private boolean isDevolucionAutomatica(int intCodEmpSolDev, int intCodLocSolDev, int intCodTipDocSolDev, int intCodDocSolDev){
        boolean blnRes=false;
        blnisDevAut = true;
        try{
            if(preparaInsert()){
                if(cargarTipoDoc(1)){
                    if(cargarSolicitud(intCodEmpSolDev, intCodLocSolDev, intCodTipDocSolDev, intCodDocSolDev)){
                        if(objTooBar.insertar()){
                            blnRes=true;
                        }else{
                            blnRes=false;
                            objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error controlado metodo isDevolucionAutomatica"+ intCodEmpSolDev+" "+intCodLocSolDev+" "+intCodTipDocSolDev+" "+intCodDocSolDev);                                
                        }
                    }
                }
            }
            if(isImprimeODAlmacenamiento(intCodEmpSolDev, intCodLocSolDev, intCodTipDocSolDev, intCodDocSolDev)){
                System.out.println("IMPRIME OD ALMACENAMIENTO");
            }
            
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt); 
            objCorEle.enviarCorreoMasivo("sistemas5@tuvalsa.com", "Devolucion automatica","Devolucion no se genera por error cae en el metodo isDevolucionAutomatica ZafVen28 catch "+ intCodEmpSolDev+" "+intCodLocSolDev+" "+intCodTipDocSolDev+" "+intCodDocSolDev+" Mensaje: "+Evt.getMessage());                                
        }
        return blnRes;
    }
 
    private boolean preparaInsert()
    {
        boolean blnRes=true;
        Color colBack;
        try
        {
            colBack = txtCodDoc.getBackground();
            txtCodDoc.setEditable(false);
            txtCodDoc.setBackground(colBack);
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            Date dateObj =datFecAux;
            Calendar calObj = Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH)+1, calObj.get(Calendar.YEAR));
            if(blnisDevAut)
                txtObs2.setText("Devolución generada automáticamente por el sistema zafiro. "+txtObs2.getText());
            else
                txtObs2.setText(txtObs2.getText());
        }
        catch(Exception Evt)
        { 
            objUti.mostrarMsgErr_F1(this, Evt); 
            blnRes=false;
        }
        return blnRes;
    }
 
    public boolean cargarTipoDoc(int intVal)
    {
        java.sql.Connection  conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql=""; 
        boolean blnRes=true;
        try
        {
            conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conn!=null)
            {
                stmLoc=conn.createStatement();
                strSql= "SELECT  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, " +
                        " case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc "+
                        " ,doc.tx_natdoc, doc.st_meringegrfisbod " +
                        " FROM tbr_tipdocprg as menu " +
                        " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=menu.co_emp AND doc.co_loc=menu.co_loc AND doc.co_tipdoc=menu.co_tipdoc ) "+
                        " WHERE   menu.co_emp = "+intCodEmpDevVen + " and " +
                        " menu.co_loc = " + intCodLocDevVen+" AND  " +
                        " menu.co_mnu =1918 AND  menu.st_reg = 'S' ";  /* DEVOLUCIONES DE VENTA */
                System.out.println("ZafVen28.cargarTipoDoc: "+ strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc")==null)?"":rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor")==null)?"":rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar")==null)?"":rstLoc.getString("tx_deslar")));
                    strCodTipDoc=txtCodTipDoc.getText();
                    strMerIngEgr=((rstLoc.getString("st_meringegrfisbod")==null)?"":rstLoc.getString("st_meringegrfisbod"));
                    strTipIngEgr=((rstLoc.getString("tx_natdoc")==null)?"":rstLoc.getString("tx_natdoc"));
                    if(intVal==1)
                       txtNumDoc.setText(((rstLoc.getString("numDoc")==null)?"":rstLoc.getString("numDoc")));
                    objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
                }
                else{
                    blnRes=false;
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc=null;
                rstLoc=null;
                conn.close();
                conn=null;
           }
        }
        catch(java.sql.SQLException e)  {   
            objUti.mostrarMsgErr_F1(this, e);blnRes=false;  
        }
        catch(Exception Evt)  {  
            objUti.mostrarMsgErr_F1(this, Evt);blnRes=false;  
        }
        return blnRes;
    }
    
    
    private boolean cargarSolicitud(int intCodEmpSolDev ,int intCodLocSolDev ,int intCodTipDocSolDev ,int intCodDocSolDev)
    {
        boolean blnRes=false;
        java.sql.Connection  conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conn!=null)
            {
                stmLoc=conn.createStatement();
                String strSql = "";
                strSql= "SELECT cli.fe_doc as fec_fac, a.co_locrel, a.co_tipdocrel, a.co_docrel , a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc,    a1.tx_nom as tx_desloc ,a.tx_obs1, a.tx_obs2 " +
                        ",a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc  ,a.nd_sub, a.nd_valiva, a.nd_tot, a.st_tipdev, cli.nd_poriva " +
                        ",c.st_ivaven ,a.st_volfacmersindev   ,cli.co_com,  cli.tx_nomven, cli.tx_ate, cli.tx_dircli, cli.tx_ciucli, cli.tx_telcli,  cli.tx_ruc , cli.ne_numdoc as numfac, cli.st_coninv, a.st_impguiremaut " +
                        ",a.co_motdev, motdev.tx_deslar as tx_deslarmotdev " +
                  
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/
                  //", cli.co_tipdoc as tipdocfac, cli.co_loc as locfac, cli.co_doc as docfac "+
                  ", cli.co_emp as empfac,cli.co_tipdoc as tipdocfac, cli.co_loc as locfac, cli.co_doc as docfac, a.nd_valcomsol, a.nd_porcomsol, a.nd_subivacer, a.nd_subivagra "+                        
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/

                  "FROM( SELECT * FROM( " +

                        "SELECT a.co_cli, a.tx_nomcli,  a.co_usrsol, a.co_emp , a.co_loc, a.co_tipdoc, a.co_doc, a.co_locrel, a.tx_obs1, a.tx_obs2,  a.co_tipdocrel, a.co_docrel, a.st_tipDev, a.st_RevBodMerDev, a.st_meraceingsis, a1.nd_canRevBodAce,  " +
                  //" a.st_impguiremaut, a.co_motdev, CASE WHEN ( " +
                  " a.st_impguiremaut, a.co_motdev, a.nd_valcomsol,a.nd_porcomsol, a.nd_subivacer, a.nd_subivagra, CASE WHEN ( " +                                          
                  "  select sum((x1.nd_candev - x1.nd_canvolfac)) as dat from  tbm_cabsoldevven as x  " +
                  " inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  " +
                  " where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc " +
                  " ) != 0  THEN 'N' ELSE 'S' END as st_volfac ,a.tx_nomusrsol,a.fe_doc,a.ne_numdoc,a.nd_sub,a.nd_valiva,a.nd_tot,a.st_volfacmersindev,a.st_aut " +
                  " FROM tbm_cabsoldevven as a  " +
                  " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) " +
                  " WHERE " +
                  " a.co_emp="+intCodEmpSolDev+" AND a.co_loc="+intCodLocSolDev+" AND a.co_tipdoc = "+intCodTipDocSolDev+ " " +
                  "  AND a.co_doc="+intCodDocSolDev+" AND a.st_reg = 'A' AND a.st_aut ='A' " +
                  " ) AS x WHERE  " +
                   " CASE WHEN  x.st_impguiremaut IN ('S') THEN    "+
                  " CASE WHEN x.st_volfac IN ('N') THEN  " +
                  " CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0  ELSE x.st_meraceingsis='N' END " +
                  " ELSE x.st_meraceingsis='N' END " +
                    " ELSE x.st_meraceingsis='N' END  "+
                  " ) AS a " +
                  " INNER JOIN tbm_cabmovinv AS cli ON(cli.co_emp=a.co_emp AND cli.co_loc=a.co_locrel AND cli.co_tipdoc=a.co_tipdocrel AND cli.co_doc=a.co_docrel )  " +
                  " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel)  " +
                  " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc)  " +
                  " INNER JOIN tbm_cli AS c ON (c.co_emp=a.co_emp AND c.co_cli=a.co_cli)  " +
                  " LEFT JOIN tbm_motdevinv AS motdev ON (motdev.co_emp=a.co_emp AND motdev.co_motdev=a.co_motdev) "+
                  " GROUP BY cli.fe_doc, a.co_locrel, a.co_tipdocrel, a.co_docrel , a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a1.tx_nom ,a.tx_obs1, a.tx_obs2,   " +
                  "  a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli,a.co_usrsol, a.tx_nomusrsol , a.fe_doc,  a.ne_numdoc, a.nd_sub, a.nd_valiva, a.nd_tot, a.st_tipdev , " +
                  "   cli.nd_poriva, c.st_ivaven, a.st_volfacmersindev   ,cli.co_com,  cli.tx_nomven, cli.tx_ate, cli.tx_dircli, cli.tx_ciucli, cli.tx_telcli,  cli.tx_ruc , " +
                  "  cli.ne_numdoc, cli.st_coninv, a.st_impguiremaut, a.co_motdev, motdev.tx_deslar "+
                  
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/
                  //" ,cli.co_tipdoc,cli.co_loc,cli.co_doc";
                  " ,cli.co_emp, cli.co_tipdoc,cli.co_loc,cli.co_doc, a.nd_valcomsol, a.nd_porcomsol, a.nd_subivacer, a.nd_subivagra";                
                  /* Agregado para identificar el tipo de documento de la solicitud de devolucion (factura normal o factura electronica)*/
          
                System.out.println("--> " + strSql );
                 rstLoc=stmLoc.executeQuery(strSql);
                 if(rstLoc.next()){
                    if(cargarSolCab(conn, rstLoc)){  
                        if(cargarSolDet(conn, rstLoc)){ 
                                 if(blnIsCosenco || blnIsDetopacio || blnIsEcuatosa){
                                    CalculaAsientoDiarioEcuaCoseDeto(conn,rstLoc.getInt("empfac"),rstLoc.getInt("locfac"), rstLoc.getInt("tipdocfac"),  rstLoc.getInt("docfac"));
                                    bloquea(txtNumDocSol, false);
                                    blnRes=true;                                    
                                 }else{              
                                    CalculaAsientoDiario();
                                    bloquea(txtNumDocSol, false);
                                    blnRes=true;
                                 }
                        }
                    }
                 }
                 conn.close();
                 conn=null;
                 stmLoc.close();
                 stmLoc=null;
                 rstLoc.close();
                 rstLoc=null;
                 
            }
            
        }
        catch(java.sql.SQLException e)  {   
            objUti.mostrarMsgErr_F1(this, e);blnRes=false;  
        }
        catch(Exception Evt)  {  
            objUti.mostrarMsgErr_F1(this, Evt);blnRes=false;  
        }
        
        return blnRes;
    }

    
    
    private boolean isImprimeODAlmacenamiento(int intCodEmpSolDev,int intCodLocSolDev,int intCodTipDocSolDev,int intCodDocSolDev)
    {
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCodEmpDevVen=0, intCodLocDevVen=0, intCodTipDocDevVen=0, intCodDevVen=0,intCodBod=0;
        String strStVolFac="", strStTieGuiRem="";
        try{
            conLoc =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                String strSql = "";
                strSql +=" SELECT 'FACVENE 'as FACVENE ,a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_Doc, \n";
                strSql +="        'DEVVENE' as DEVVENE, a2.co_emp as co_empDevVen, a2.co_loc as co_locDevVen, a2.co_tipDoc as co_tipDocDevVen, a2.co_doc as co_docDevVen,  \n";
                strSql +="        'OD' as OD, a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc, a5.st_conInv, a5.co_ptoPar, a9.co_bodgrp,   \n";
                strSql +="        'SODEVE' as SODEVE, a6.co_emp, a6.co_locRelSolDevVen, a6.co_tipDocRelSolDevVen, a6.co_DocRelSolDevVen, a7.st_volFacMerSinDev, a5.st_tieGuiSec\n";
                strSql +=" FROM tbm_cabMovInv as a1  \n";
                strSql +=" INNER JOIN tbr_cabMovInv as a2 ON (a1.co_emp=a2.co_empRel AND a1.co_loc=a2.co_locRel AND a1.co_tipDoc=a2.co_tipDocRel AND a1.co_doc=a2.co_docRel)  \n";
                strSql +=" INNER JOIN tbm_detMovInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)  \n";
                strSql +=" INNER JOIN tbm_detGuiRem as a4 ON (a3.co_emp=a4.co_empRel AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND \n";
                strSql +="                                    a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)    \n";
                strSql +=" INNER JOIN tbm_cabGuiRem as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc) \n";
                strSql +=" INNER JOIN tbm_cabMovInv as a6 ON (a6.co_emp=a2.co_emp AND a6.co_loc=a2.co_loc AND a6.co_tipDoc=a2.co_tipDoc AND a6.co_doc=a2.co_doc)\n";
                strSql +=" INNER JOIN tbm_cabSolDevVen as a7 ON (a6.co_emp=a7.co_Emp AND a6.co_locRelSolDevVen=a7.co_loc  AND a6.co_tipDocRelSolDevVen=a7.co_tipDoc  AND a6.co_DocRelSolDevVen=a7.co_Doc)\n";
                strSql +=" INNER JOIN tbr_bodEmpBodGrp as a9 ON (a9.co_Emp=a5.co_Emp and a9.co_bod=a5.co_ptoPar) ";
                strSql +=" WHERE a1.co_tipDoc=228 AND a7.co_emp="+intCodEmpSolDev+" AND a7.co_loc="+intCodLocSolDev+" AND a7.co_tipDoc="+intCodTipDocSolDev+" AND a7.co_doc="+intCodDocSolDev+"  \n";
                strSql +=" \n";
                System.out.println("isImprimeODAlmacenamiento: \n" + strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodEmpDevVen=rstLoc.getInt("co_empDevVen");
                    intCodLocDevVen=rstLoc.getInt("co_locDevVen");
                    intCodTipDocDevVen=rstLoc.getInt("co_tipDocDevVen");
                    intCodDevVen=rstLoc.getInt("co_docDevVen");
                    strStVolFac=rstLoc.getString("st_volFacMerSinDev");
                    strStTieGuiRem=rstLoc.getString("st_tieGuiSec");
                    intCodBod=rstLoc.getInt("co_bodgrp");
                }
                if(strStTieGuiRem.equals("N") && strStVolFac.equals("N")){
                    imprimirOrdenAlmacenamiento(intCodEmpDevVen,intCodLocDevVen,intCodTipDocDevVen,intCodDevVen, intCodBod);
                }
                stmLoc.close();
                rstLoc.close();
                stmLoc=null;
                rstLoc=null;
            }
            conLoc.close();
            conLoc=null;
            
        }
        catch(java.sql.SQLException e)  {    objUti.mostrarMsgErr_F1(this, e);blnRes=false;       }
        catch(Exception Evt)  {     objUti.mostrarMsgErr_F1(this, Evt);blnRes=false;        }
        
        return blnRes;
    } 
    
    /**
     * Función que permite imprimir la Orden de Almacenamiento
     */
    private boolean imprimirOrdenAlmacenamiento(int intCodEmpDevVen,int intCodLocDevVen,int intCodTipDocDevVen,int intCodDocDevVen, int intCodBod) 
    {
        boolean blnRes = true;
        String strSQLRep = "";
        Connection conImpOrdAlm;
        Statement stmImpOrdAlm;
        ResultSet rstImpOrdAlm;
        String strRutImp="", strRutRpt ="", strNomRpt="", strRutRelRpt="", strRutAbsRpt="" ;   
        String strUsu = "", strFecHorSer = "";
        String strNomPrnSer = "";
        String strRutRepOrdAlm = "";
        java.util.Date datFecAux;
        int intNumTotRpt=-1; 
        try 
        {
            conImpOrdAlm =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conImpOrdAlm != null) 
            {
                //Obtener la fecha y hora del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                datFecAux = null;
                
                if(blnPruebas) {
                   strNomPrnSer = "laser_sistemas";
                }
                else
                {
                    switch (intCodBod)  //BODEGA GRUPO
                    {
                        case 1://California
                            strNomPrnSer = "od_califormia";
                            break;
                        case 2://Vía Daule
                            strNomPrnSer = "od_dimulti";
                            break;
                        case 3://Quito Norte
                            strNomPrnSer = "od_quito";
                            break;
                        case 5://Manta
                            strNomPrnSer="od_manta";
                            break;
                        case 11://Santo Domingo
                            strNomPrnSer="od_stodgo";
                            break;
                        case 15://Inmaconsa
                            strNomPrnSer = "od_inmaconsa";
                            break;
                        case 28://Cuenca
                            strNomPrnSer="od_cuenca";
                            break;
                        default:
                            break;
                    }
                }
                
                objRptSis.cargarListadoReportes(null);
                intNumTotRpt=objRptSis.getNumeroTotalReportes();  
                for (int i=0;i<intNumTotRpt;i++)   
                {                
                    switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                    { 
                        case 476:    //Orden de Almacenamiento
                            strRutRpt=objRptSis.getRutaReporte(i);  
                            strRutRelRpt=objRptSis.getRutaRelativaReporte(i);  
                            strRutAbsRpt=objRptSis.getRutaAbsolutaReporte(i);  
                            strNomRpt=objRptSis.getNombreReporte(i); //Nombre del Reporte  
                            
                            //Ruta del Reporte
                            if (System.getProperty("os.name").equals("Linux")) { //Linux
                                strRutRepOrdAlm = "/Zafiro"+strRutRelRpt+"";  
                            }
                            else { //Windows
                                strRutRepOrdAlm = strRutRpt;  
                            }        
                            strRutImp=strRutRepOrdAlm+strNomRpt;                
                
                            //Armar sentencia SQL
                            stmImpOrdAlm = conImpOrdAlm.createStatement();
                            strSQLRep ="";
                            strSQLRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.co_itm, a9.co_bodgrp as co_bod, ";
                            strSQLRep+="        CASE WHEN a5.st_impord='S' THEN ";
                            strSQLRep+="        (substring(a4.tx_codalt2 from 1 for 1 ) || ' ' || substring(a4.tx_codalt2 from 2 for 1 ) || ' ' || substring(a4.tx_codalt2 from 3 for  1 )) ";
                            strSQLRep+="        ELSE a4.tx_codAlt END AS tx_codAlt , ";
                            strSQLRep+="        CASE WHEN a5.st_impord='S' THEN a5.tx_ubi ELSE '' END AS tx_ubi, ";
                            strSQLRep+="        CASE WHEN a5.st_impord='N' THEN a4.tx_nomItm ELSE '' END AS tx_nomItm, ";
                            strSQLRep+="        a2.nd_can, a6.tx_desLar as tx_uniMed, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc,  ";
                            strSQLRep+="        a1.ne_numDoc,a3.tx_desLar as tx_desTipDoc, ";
                            strSQLRep+="        b1.co_usr, b1.tx_usr, b1.tx_nom AS tx_nomUsrIng ";
                            strSQLRep+=" FROM (tbm_cabMovInv  as a1 INNER JOIN tbm_usr AS b1 ON a1.co_usrIng=b1.co_usr)";
                            strSQLRep+=" INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_TipDoc AND a1.co_doc=a2.co_doc)";
                            strSQLRep+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_TipDoc)";
                            strSQLRep+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)";
                            strSQLRep+=" INNER JOIN tbm_invBod as a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod AND a2.co_itm=a5.co_itm)";
                            strSQLRep+=" INNER JOIN tbr_bodEmpBodGrp as a9 ON (a9.co_Emp=a2.co_Emp and a9.co_bod=a2.co_bod) ";
                            strSQLRep+=" LEFT OUTER JOIN tbm_var as a6 ON (a4.co_uni=a6.co_reg)";
                            strSQLRep+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp)";
                            strSQLRep+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc)";
                            strSQLRep+=" WHERE a1.co_emp=" + intCodEmpDevVen + "";
                            strSQLRep+=" AND a1.co_loc=" + intCodLocDevVen + "";
                            strSQLRep+=" AND a1.co_tipDoc=" +intCodTipDocDevVen + "";
                            strSQLRep+=" AND a1.co_doc=" + intCodDocDevVen + "";
                            strSQLRep+=" AND a2.nd_can NOT IN(0)";
                            strSQLRep+=" ORDER BY a2.co_reg ";
                            rstImpOrdAlm = stmImpOrdAlm.executeQuery(strSQLRep);
                            if (rstImpOrdAlm.next())    {
                                strUsu = rstImpOrdAlm.getString("tx_usr");
                            }
                            stmImpOrdAlm.close();
                            stmImpOrdAlm = null;
                            rstImpOrdAlm.close();
                            rstImpOrdAlm = null;                                

                            //Inicializar los parametros que se van a pasar al reporte.
                            java.util.Map mapPar = new java.util.HashMap();
                            mapPar.put("strSQLRep", strSQLRep);
                            mapPar.put("strCamAudRpt", "" + strUsu + "    " + strFecHorSer + "");

                            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                            JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutImp, mapPar, conImpOrdAlm);
                            javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strNomPrnSer, null);
                            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                            printServiceAttributeSet.add(printerName);
                            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                            objJRPSerExp.exportReport();
                            objPriReqAttSet = null;
                            break;
                    }
                }
                conImpOrdAlm.close();
                conImpOrdAlm = null;
            }
        } 
        catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
 
 
    /*15/05/2015 CMATEO PARA ACTUALIZAR CAMPO CANTIDAD POR EGRESAR*/ 
    private boolean actualizarCabSegMovInv(Connection cnx,int intEmp, int intLoc, int intTipDoc, int intDoc)
    {
        boolean booRet=false;
        String strQry="";
        Statement stmQry=null;
        ResultSet rs=null;
        ZafGenOdDaoPryTra daoOD=new ZafGenOdDaoPryTra();
        try
        {
            strQry=" select cot.co_emp, cot.co_loc, cot.co_cot, sum(detcot.nd_canrem)\n" +
                   " from tbm_cabsoldevven as s \n" +
                   " inner join tbm_cabmovinv as c\n" +
                   " on (s.co_emp= c.co_emp and s.co_locrel=c.co_loc and s.co_tipdocrel=c.co_tipdoc and s.co_docrel=c.co_doc)\n" +
                   " inner join tbm_cabcotven as cot\n" +
                   " on (cot.co_emp= c.co_emp and cot.co_loc=c.co_loc and cot.co_cot=c.ne_numcot)\n" +
                   " inner join tbm_detcotven as detcot\n" +
                   " on (cot.co_emp= detcot.co_emp and cot.co_loc=detcot.co_loc and cot.co_cot=detcot.co_cot)\n" +
                   " inner join tbm_cabmovinv as dev\n" +
                   " on(dev.co_emp=s.co_emp and dev.co_locrelsoldevven=s.co_loc  and dev.co_tipdocrelsoldevven=s.co_tipdoc and dev.co_docrelsoldevven=s.co_doc)\n" +
                   " where dev.co_emp="+intEmp+"\n" +
                   " and dev.co_loc="+intLoc+"\n" +
                   " and dev.co_tipdoc="+intTipDoc+"\n" +
                   " and dev.co_doc="+intDoc+"\n"+
                   " group by cot.co_emp, cot.co_loc, cot.co_cot";
        
            stmQry=cnx.createStatement();
            rs=stmQry.executeQuery(strQry);
            if(rs.next())
            {                
                int intSeg=obtenerCodSegODxCot(cnx,rs.getInt("co_emp"), rs.getInt("co_loc"),rs.getInt("co_cot"));
                if(intSeg>0)
                {
                    int intSec=daoOD.obtenerSecSegOD(cnx, intSeg);                
                    if(insertarSegODxDev(cnx,  intEmp, intLoc, intTipDoc, intDoc, intSeg, intSec)){
                        booRet=true;
                        stmQry.close();
                        rs.close();
                        stmQry=null;
                        rs=null;                    
                    }
                }else{
                    booRet=true;
                }
            }else{
                booRet=true;
            }            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;    
    }
    
    

/**
     * Metodo que obtiene el codigo del seguimiento por la cotizacion.
     * @param cnx conexion de acceso a datos.
     * @param intEmpCot Empresa de cotizacion.
     * @param intLocCot Local de cotizacion.
     * @param intDocCot Codigo de Documento de cotizacion.
     * @return int Codigo del seguimiento.
     */
    public int obtenerCodSegODxCot(Connection cnx, int intEmpCot, int intLocCot, int intDocCot)
    {
        Statement stm=null;
        String strQry="";
        int intSeg=0;
        try
        {
            stm=cnx.createStatement();
            strQry="select co_seg from tbm_cabsegmovinv where co_emprelcabcotven="+intEmpCot+" and co_locrelcabcotven="+intLocCot+" and co_cotrelcabcotven="+intDocCot;            
            ResultSet rsSeg=stm.executeQuery(strQry);            
            if(rsSeg!=null && rsSeg.next()){
                intSeg=rsSeg.getInt("co_seg");
            }
        }
        catch(Exception ex){       ex.printStackTrace();        intSeg=-1;        }        
        return intSeg;    
    }  
    
    /**
     * Metodo que inserta el seguimiento de la devolucion.
     * @param cnx Conexion de acceso a datos.
     * @param intCodEmpDev Codigo Empresa de la devolucion.
     * @param intCodLocDev Codigo local de la devolucion.
     * @param intCodTipDocDev Codigo Tipo documento de la devolucion.
     * @param intCodDocDev Codigo documento de la devolucion.
     * @param intCodSeg Codigo seguimiento.
     * @param intSec Codigo secuencia del seguimiento.
     * @return boolean true si es correcto, false incorrecto.
     */
    public boolean insertarSegODxDev(Connection cnx, int intCodEmpDev, int intCodLocDev, int intCodTipDocDev,int intCodDocDev, int intCodSeg, int intSec)
    {
        boolean booRet=false;        
        Statement stm=null;
        String strIns="";
        try
        {
            stm=cnx.createStatement();
            strIns="insert into  tbm_cabsegmovinv(co_seg, co_reg, co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv,co_docrelcabmovinv) values("+intCodSeg+","+intSec+","+intCodEmpDev+","+intCodLocDev+","+intCodTipDocDev+","+intCodDocDev+")";
            stm.execute(strIns);
            booRet=true;
        }
        catch(Exception ex){        ex.printStackTrace();     }
        return booRet;    
    }   
 
    
     /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        boolean blnRes=true;

        if(!verificaItmUni())
           blnRes=false;

//        if (objUltDocPrint.isPagoDocumento_aso(intCodEmpDevVen, intCodLocDevVen,  Integer.parseInt(txtCodTipDocRelFac.getText()) , Integer.parseInt(txtCodDocRelFac.getText()) ))
//        {
//            blnRes=mensajeConfir("¿Desea Continuar de todas forma.. ?");
//        }
        
        if(!validaCampos())
            blnRes=false;
        
        if(!validarDevIng())
            blnRes=false;
        
        return blnRes;
    }
    
    private boolean mensajeConfir(String strMsg2)
    {
        boolean blnRes=false;
        String strTit="Mensaje del sistema Zafiro";
        if(!(JOptionPane.showConfirmDialog(this,strMsg2,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 1 ))
            blnRes=true;  // SI
        else
            blnRes=false;  // NO 
        return blnRes;
    }
    
    
    private boolean verificaItmUni()
    {
        boolean  blnRes=true;
        try
        {
            for(int i=0; i<tblDat.getRowCount();i++)
            {
                if(tblDat.getValueAt(i, INT_TBL_CODITM) != null)
                {
                    int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                    int intCodItmAct = Integer.parseInt( (tblDat.getValueAt(i, INT_TBL_CODITMACT)==null?"0":tblDat.getValueAt(i, INT_TBL_CODITMACT).toString().toString()));
                    if(intCodItm != intCodItmAct)
                    {
                        MensajeInf("El Item "+tblDat.getValueAt(i, INT_TBL_CODALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                        blnRes=false;
                        break;
                    }
                }
            }
        }
        catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
        return blnRes;     
    }
    
    
    private boolean validaCampos()
    {
        if(txtCodTipDoc.getText().equals("") )
        {
            tabGen.setSelectedIndex(0);                 
            MensajeInf("Tipo de Documento");
            txtCodTipDoc.requestFocus();
            return false;
        }

        if(!txtFecDoc.isFecha())
        {
           tabGen.setSelectedIndex(0);                 
           MensajeInf("Fecha de Devolución");
           txtFecDoc.requestFocus();
           return false;
         }

        if(!txtNumDocSol.getText().equals(""))
        {
            if(!objUti.isNumero(txtNumDocSol.getText()))
            {
               tabGen.setSelectedIndex(1);                
               MensajeInf("Devolución");
               txtNumDocSol.requestFocus();
              return false;
            }
        }

        if(txtNumDoc.getText().equals("") || !objUti.isNumero(txtNumDoc.getText()))
        {
           tabGen.setSelectedIndex(0);                  
           MensajeInf("Número de devolucion");
           txtNumDoc.selectAll();
           txtNumDoc.requestFocus();
           return false;
        }

        //******************************************************************************
        //******************************************************************************
        if(radCan.isSelected())
        {
            for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++)
            {
                if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null )
                {
                    if(tblDat.getValueAt(intRowVal,INT_TBL_CANDEV) != null )
                    {
                        if(Double.parseDouble((tblDat.getValueAt(intRowVal,INT_TBL_CANDEV)==null?"0":(tblDat.getValueAt(intRowVal,INT_TBL_CANDEV).toString().equals("")?"0":tblDat.getValueAt(intRowVal,INT_TBL_CANDEV).toString()))) > 0.00  )
                        {
                            double dblCantFac = Double.parseDouble(tblDat.getValueAt(intRowVal,INT_TBL_CANVEN).toString());
                            double dblCant   =  Double.parseDouble(tblDat.getValueAt(intRowVal,INT_TBL_CANDEV).toString());     

                            if(dblCant>dblCantFac)
                            {
                               String strTit, strMsg;
                               strTit="Mensaje del sistema Zafiro";
                               strMsg="El valor debe estar entre 0 y "+dblCantFac ;
                               JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this),strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                               tblDat.repaint();    
                               tblDat.requestFocus();
                               tblDat.editCellAt(intRowVal, INT_TBL_CANVEN);
                               return false; 
                            }

                            double dblPordes = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PORDES).toString());

                            if(dblPordes >= 100.00 )
                            {
                                String strTit, strMsg;

                                strTit="Mensaje del sistema Zafiro";
                                strMsg="El campo de porcentaje debe de estar entre 0  y 100  Corrija y vuelva a intentarlo. ";
                                JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                                tblDat.repaint();    
                                tblDat.requestFocus();
                                tblDat.editCellAt(intRowVal, INT_TBL_PORDES);
                                return false; 
                            }
                        }
                    }
                }
            }
        }  
        //******************************************************************************
        //******************************************************************************

        if(!objAsiDia.isDiarioCuadrado())
        {
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="EL diario no esta cuadrado, no se puede grabar";
            JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return true;
    }

    
    public boolean validarDevIng()
    {
        boolean blnRes = true;
        java.sql.Connection conLoc;
        java.sql.Statement strmLoc;
        java.sql.ResultSet rstLoc;
        //String strSql = "";
        int intEst = 0;
        String strItmPro = "";
        int intEstMov = 0;
        try 
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            strmLoc=conLoc.createStatement();

            StringBuffer stbDatTbl=new StringBuffer(); //VARIABLE TIPO BUFFER

            //Tipo Devolucion Cantidad.
            if(radCan.isSelected())
            {
                for(int i=0; i<tblDat.getRowCount();i++)
                {
                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null )
                    {
                        double dblCanAceBod = objUti.redondear((tblDat.getValueAt(i,INT_TBL_CANDEVACEBOD)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANDEVACEBOD).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_CANDEVACEBOD).toString())), 4 );    
                        if(dblCanAceBod > 0 )
                        {
                            int intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                            double dblCanDev = objUti.redondear((tblDat.getValueAt(i,INT_TBL_CANDEV)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_CANDEV).toString())), 4 );

                            double dblCanVen = objUti.redondear((tblDat.getValueAt(i,INT_TBL_CANVEN)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANVEN).toString().equals("")?"0":tblDat.getValueAt(i,INT_TBL_CANVEN).toString())), 4 );

                            strSQL="SELECT "+intCodEmpDevVen+" AS coemp, "+txtCodLocRelFac.getText()+" AS coloc, "+
                                   " "+txtCodTipDocRelFac.getText()+" AS  cotipdoc, "+txtCodDocRelFac.getText()+" AS codoc, "+
                                   //" "+intCodItm+" AS coitm, "+dblCanAceBod+" AS ndcan, "+dblCanDev+" AS ndcaningdev ";
                                   " "+intCodItm+" AS coitm, "+dblCanVen+" AS ndcan, "+dblCanDev+" AS ndcaningdev ";
                            if(intEst>0) stbDatTbl.append(" UNION ALL ");
                            stbDatTbl.append(strSQL);
                            intEst=1;
                        }  
                    }
                }
                if(intEst==1)
                {
                    strSQL=" SELECT coemp, coloc, cotipdoc, codoc, coitm, ndcan, (ndcandev+ndcaningdev) as ndcantotdev, inv.tx_codalt " +
                           " FROM (     " +
                           "        SELECT * ,(  " +
                            "            SELECT sum(abs(a2.nd_can)) as total  FROM tbr_cabmovinv as a " +
                           "             INNER JOIN tbm_cabmovinv as a1 on(a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_doc=a.co_doc and a1.co_tipdoc=a.co_tipdoc  and a1.st_reg='A' and a1.st_tipdev not in('P','V', 'E') ) " +
                           "             INNER JOIN tbm_detmovinv as a2 on(a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_itm=x.coitm) " +
                           "             WHERE a.co_emprel=x.coemp and a.co_locrel=x.coloc and a.co_tipdocrel=x.cotipdoc and a.co_docrel=x.codoc  and  a.st_reg='A' and a.co_tipdoc="+txtCodTipDoc.getText()+"  " +
                           "        ) AS ndcandev " +
                           "        FROM ( " +
                           "             SELECT sum(ndcan) AS ndcan, sum(ndcaningdev) AS ndcaningdev,  coemp, coloc, cotipdoc, codoc, coitm   " +
                           "             FROM ( "+stbDatTbl.toString()+" "+
                           "             ) AS x GROUP BY coemp, coloc, cotipdoc, codoc, coitm " +
                           "       ) AS x " +
                           " ) AS x INNER JOIN tbm_inv AS inv ON(inv.co_emp=x.coemp AND inv.co_itm=x.coitm)";

                    System.out.println("ZafVen28.validarDevIng: " + strSQL );
                    rstLoc = strmLoc.executeQuery(strSQL);
                    while(rstLoc.next())
                    {
                        if( rstLoc.getDouble("ndcantotdev") > rstLoc.getDouble("ndcan") ) 
                        {
                            intEstMov=1;
                            strItmPro=rstLoc.getString("tx_codalt");
                        }
                    }
                } 
                if(intEstMov==1)
                {
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="Este Item  "+strItmPro+"  Ya tiene devoluciones y la cantidad a devolver es mayor";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                    blnRes=false;
                }  
            }

            //Tipo Devolucion Descuento o Descuento Especial.
            if(radDes.isSelected() || radDesEsp.isSelected() )
            {
                for(int i=0; i<tblDat.getRowCount();i++)
                {
                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                        if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                            if(!tblDat.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                double dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString());
                                if( dblCanMov > 0.00 )
                                {
                                    int intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                                    strSQL =" select sum(b.nd_can) as total from tbr_cabmovinv as a"+ 
                                            " inner join tbm_cabmovinv as b1 on(a.co_emp=b1.co_emp and a.co_loc=b1.co_loc and a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev in('P', 'E') )" +
                                            " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
                                            " where a.co_docrel="+txtCodDocRelFac.getText()+" and a.co_emp="+intCodEmpDevVen+" "+
                                            " and a.co_tipdocrel="+txtCodTipDocRelFac.getText()+" and co_itm="+intCodItm+" and a.st_reg='A' and b1.st_reg='A' ";
                                    rstLoc = strmLoc.executeQuery(strSQL);
                                    double intNumDev=0; 
                                    if(rstLoc.next()) intNumDev = rstLoc.getDouble("total"); 
                                    rstLoc.close();
                                    rstLoc=null;
                                    intNumDev=intNumDev+dblCanMov;
                                    double dblCanOri = 100;  
                                    if(intNumDev > dblCanOri ) 
                                    {
                                        String strTit, strMsg;
                                        strTit="Mensaje del sistema Zafiro";
                                        strMsg="Este Item  "+ tblDat.getValueAt(i, INT_TBL_CODALT) + "  Ya tiene devoluciones por descuento y la cantidad a devolver es mayor al 100%";
                                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        blnRes=false; 
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(radPre.isSelected())
            {
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                        if(tblDat.getValueAt(i, INT_TBL_CANDEV ) != null){
                            if(!tblDat.getValueAt(i, INT_TBL_CANDEV ).equals("")){
                                double dblCanMov = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV).toString());
                                if( dblCanMov > 0.00 )
                                {
                                    int intCodItm =  Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_CODITM).toString()); 
                                    strSQL =" select sum(b.nd_can) as total from tbr_cabmovinv as a"+ 
                                            " inner join tbm_cabmovinv as b1 on(a.co_doc=b1.co_doc and a.co_tipdoc=b1.co_tipdoc and b1.st_tipdev in('V') )" +
                                            " inner join tbm_detmovinv as b on(b1.co_doc=b.co_doc and b1.co_tipdoc=b.co_tipdoc)"+
                                            " where a.co_docrel="+txtCodDocRelFac.getText()+" and a.co_emp="+intCodEmpDevVen+"  "+
                                            " and a.co_tipdocrel="+txtCodTipDocRelFac.getText()+" and co_itm="+intCodItm+" and a.st_reg='A' and b1.st_reg='A' ";
                                    rstLoc = strmLoc.executeQuery(strSQL);
                                    double intNumDev=0; 
                                    if(rstLoc.next()) intNumDev = rstLoc.getDouble("total"); 
                                    rstLoc.close();
                                    rstLoc=null; 
                                    intNumDev=intNumDev+dblCanMov;
                                    double dblCanOri =  Double.parseDouble(tblDat.getValueAt(i, INT_TBL_PREUNI).toString());
                                    if(intNumDev > dblCanOri ) 
                                    {
                                        String strTit, strMsg;
                                        strTit="Mensaje del sistema Zafiro";
                                        strMsg="Este Item  "+ tblDat.getValueAt(i, INT_TBL_CODALT) + "  Ya tiene devoluciones por precio y la cantidad a devolver es mayor a "+tblDat.getValueAt(i, INT_TBL_PREUNI);
                                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        blnRes=false; 
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            stbDatTbl=null;          
            strmLoc.close();
            strmLoc=null;
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }


    private boolean validarTipDoc() 
    {
        boolean blnRes=true;
        if(txtCodTipDoc.getText().trim().equals("")) { MensajeInf("Ingrese tipo el documento");  return false; }

        return blnRes;
    }
    
    
    /**
     * Función que retorna el último código de documento disponible para la devolucion de venta a insertar.
     * @param conLoc Conexión.
     * @return 
     */
    private boolean getUltCodDoc(java.sql.Connection conIns) 
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        
        try 
        {
            if (conIns != null) 
            {
                stmLoc = conIns.createStatement();

                strSQL =" SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 END as co_doc  ";
                strSQL+=" FROM tbm_cabMovInv ";
                strSQL+=" WHERE co_emp=" + intCodEmpDevVen ;
                strSQL+=" AND co_loc=" + intCodLocDevVen ;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() ;
                
                System.out.println("getUltCodDoc:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                
                if (rstLoc.next()) 
                {
                    intCodDocDevVen = rstLoc.getInt("co_doc");
                    System.out.println("intCodDocDevVen: "+intCodDocDevVen);
                    blnRes=true;
                }

                rstLoc.close();
                stmLoc.close();
                rstLoc = null;
                stmLoc = null;
            }
        } 
        catch (java.sql.SQLException ex) { blnRes=false; objUti.mostrarMsgErr_F1(this, ex); } 
        catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return  blnRes;
    }
    
    private String getFecDoc()
    {
        int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
        String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
        String FecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(),"yyyy/MM/dd");
        String fecha = objUti.formatearFecha( strFecFac,"yyyy-MM-dd","yyyy/MM/dd");
        java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
        java.util.Date fe2 = objUti.parseDate(FecAux,"yyyy/MM/dd");
        if(fe1.equals(fe2))
            System.out.println("Igual ");
        else if(fe1.after(fe2))
            strFecha=strFecFac;  // System.out.println(" mayor ");
        else
            System.out.println("  ");
    
        return strFecha;
    }
    
    private void enviarCorreoFaltantes()
    {
        strMensMerFal+="</table>";
        if (blnMerFal) 
        {
            String subject = "Zafiro: Envio a bodega Faltante. Orden de conteo: " + intNumDocOrdConImp;
            //strCorEleTo="inventario@tuvalsa.com;operaciones@tuvalsa.com;gerenciatuval@tuvalsa.com;comptroller@tuvalsa.com"+strCorEleTo;  
            strCorEleTo="sistemas9@tuvalsa.com";  
            objCorEle.enviarCorreoMasivo(strCorEleTo, subject, strMensMerFal );
        }
    }
    
    
    
    private boolean generaFacturaVolverFacturar(java.sql.Connection conIns) 
    {
        boolean blnRes=false;
        
        try 
        {
            if (conIns != null) 
            {
                String[] strArrFac=strFacDev.split("-");
                /*CMATEO MODIFICACION PARA EVITAR DESCUADRE EN FACTURA VOLVER A FACTURAR*/
                ZafImp objZafImp = new ZafImp(intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(strArrFac[1]),0,false);
                ZafGenFac objZafGenFac=new ZafGenFac(objZafImp,conIns,objParSis);
                int intCodDocFac=objZafGenFac.traeCoDoc(Integer.parseInt(txtNumDocFac.getText()), intCodEmpDevVen, intCodLocDevVen, Integer.parseInt(strArrFac[1]),conIns);
                objZafGenFac.getObjZafImp().setCoDoc(intCodDocFac);
                /*CMATEO MODIFICACION PARA EVITAR DESCUADRE EN FACTURA VOLVER A FACTURAR*/
                objZafGenFac.traerDat(conIns);
                objParSis.cargarConfiguracionImpuestos();
                boolean booCab=objZafGenFac.ingresarCabMovInv(conIns);//Cabecera y Detalle Factura.
                boolean booDia=objZafGenFac.insertarAsiDiaCnt(objZafImp.getEmp(), objZafImp.getLoc(), Integer.parseInt((strArrFac[1].equals("1")?"228":strArrFac[1])),((Double)objZafGenFac.getHasTot().get("CODFAC")).intValue(),conIns, objParSis );
                boolean booPag=objZafGenFac.insertarPagFac(conIns);
                boolean booActStk=objZafGenFac.actualizarStock(conIns);
                boolean booRetNum=objZafGenFac.asignarNumFacNue(objZafImp.getEmp(), objZafImp.getLoc(), Integer.parseInt((strArrFac[1].equals("1")?"228":strArrFac[1])),((Double)objZafGenFac.getHasTot().get("CODFAC")).intValue(),conIns);							

                if(booCab && booDia && booPag && booRetNum && booActStk)
                {
                   blnRes=true;
                   //enviarPulsoFacturacionElectronica();                                                
                }
            }
        } 
        catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return  blnRes;
    }
    

    private boolean generaFacturaVolverFacturarBig(java.sql.Connection conIns, int intCodUsuarioNOADMIN) 
    {
        boolean blnRes=false;
        
        try 
        {
            if (conIns != null) 
            {
                String[] strArrFac=strFacDev.split("-");
                /*CMATEO MODIFICACION PARA EVITAR DESCUADRE EN FACTURA VOLVER A FACTURAR*/
                ZafImp objZafImp = new ZafImp(intCodEmpDevVen,intCodLocDevVen,Integer.parseInt(strArrFac[1]),0,false);
                ZafGenFac objZafGenFac=new ZafGenFac(objZafImp,conIns,objParSis);
                int intCodDocFac=objZafGenFac.traeCoDoc(Integer.parseInt(txtNumDocFac.getText()), intCodEmpDevVen, intCodLocDevVen, Integer.parseInt(strArrFac[1]),conIns);
                objZafGenFac.getObjZafImp().setCoDoc(intCodDocFac);
                /*CMATEO MODIFICACION PARA EVITAR DESCUADRE EN FACTURA VOLVER A FACTURAR*/
                objZafGenFac.traerDat(conIns);
                objParSis.cargarConfiguracionImpuestos();
                boolean booCab=objZafGenFac.ingresarCabMovInvBig(conIns, intCodUsuarioNOADMIN);//Cabecera y Detalle Factura.
                boolean booDia=objZafGenFac.insertarAsiDiaCntBig(objZafImp.getEmp(), objZafImp.getLoc(), Integer.parseInt((strArrFac[1].equals("1")?"228":strArrFac[1])),((BigDecimal)objZafGenFac.getHasTotBig().get("CODFAC")).intValue(),conIns, objParSis );
                boolean booPag=objZafGenFac.insertarPagFacBig(conIns);
                boolean booActStk=objZafGenFac.actualizarStock(conIns);
                boolean booRetNum=objZafGenFac.asignarNumFacNue(objZafImp.getEmp(), objZafImp.getLoc(), Integer.parseInt((strArrFac[1].equals("1")?"228":strArrFac[1])),((BigDecimal)objZafGenFac.getHasTotBig().get("CODFAC")).intValue(),conIns);							
				/*RECOSTEO 20-NOV-2017*/
                boolean booCosteo=objUti.costearDocumento(this, objParSis, conIns, objZafImp.getEmp(), objZafImp.getLoc(), Integer.parseInt((strArrFac[1].equals("1")?"228":strArrFac[1])), ((BigDecimal)objZafGenFac.getHasTotBig().get("CODFAC")).intValue());
				/*RECOSTEO 20-NOV-2017*/
                if(booCab && booDia && booPag && booRetNum && booActStk && booCosteo)
                {
                   blnRes=true;
                   enviarPulsoFacturacionElectronica();
                }
            }
        } 
        catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        return  blnRes;
    }    
    
    
    //Mateo
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
        catch(Exception ex){    ex.printStackTrace();      }
        return lstItm;
    }  
    
    
    private void enviarDevolucionVentaEnLineaSRI(){
        java.sql.Connection conLoc;
        String strMsg;
        
        try
        {
           conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());        
           if(consultarEstadoDevolucionVentaContingencia(conLoc)){
               if(cambiarEstadoFacturacionElectronica(conLoc)){
                  if(enviarPulsoFacturacionElectronica()){
                      butFacEle.setEnabled(false);
                      strMsg="<html> Su devolución de venta ha sido enviada con éxito al SRI. <BR>" ;
                      strMsg+="Por favor espere pacientemente a que el SRI la autorice. <html>";
                      String strTit="Mensaje del sistema Zafiro";
                      JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                  } 
               }
           }
        }
        catch (java.sql.SQLException e) { 
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
    }
    
    private void obtenerXMLDevolucionVenta()
    {
        int intCodTipDoc, intCodDoc;
        java.sql.Connection conLoc;
        String strMsg, strEstFacEle, strPrmSer;
        
        try
        {
           if (txtDesCorTipDoc.getText().equals("") || txtCodDoc.getText().equals(""))
           {  strMsg =  "<HTML>Debe tener los datos de la devolución de venta cargados en pantalla.<BR>";
              strMsg += "Verifique esto y vuelva a intentarlo.<HTML>";
              String strTit = "Mensaje del sistema Zafiro";
              JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
              return;
           }
           
           conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           intCodTipDoc = 229;
           intCodDoc = Integer.parseInt(txtCodDoc.getText());
           
           ZafCabEstFacEle objZafCabEstFacEle = new ZafCabEstFacEle();
           objZafCabEstFacEle.setCodEmp(objParSis.getCodigoEmpresa());
           objZafCabEstFacEle.setCodLoc(objParSis.getCodigoLocal());
           objZafCabEstFacEle.setCodTipDoc(intCodTipDoc);
           objZafCabEstFacEle.setCodDoc(intCodDoc);
           objZafCabEstFacEle.setNomTbl("tbm_cabmovinv");
           ZafEstFacEle objZafEstFacEle = new ZafEstFacEle(objZafCabEstFacEle, conLoc);
           strEstFacEle = objZafEstFacEle.getEstadoDocEle();
           
           if (!strEstFacEle.equals("A"))
           {  strMsg =  "<HTML>La devolución de venta no está autorizada.<BR>";
              strMsg += "Verifique esto y vuelva a intentarlo.<HTML>";
              String strTit = "Mensaje del sistema Zafiro";
              JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
              return;
           }
           
           //Ejemplo de strPrmSer = "4-3-229-1849-DEVVTA-admin"
           strPrmSer = objParSis.getCodigoEmpresa() + "-" + objParSis.getCodigoLocal() + "-" + intCodTipDoc + "-" + intCodDoc + "-DEVVTA-" + objParSis.getNombreUsuario();
           
           if (enviarPulsoServicio15(strPrmSer))
           {  butGetXmlDevVta.setEnabled(false);
              strMsg =  "<HTML>Su requerimiento para obtener los archivos XML y PDF ha sido enviado con éxito.<BR>";
              strMsg += "Por favor espere pacientemente la recepción del correo con los archivos solicitados.<HTML>";
              String strTit = "Mensaje del sistema Zafiro";
              JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
           }
        }
        
        catch (java.sql.SQLException e)
        { 
           objUti.mostrarMsgErr_F1(this, e);  
        }
        
        catch(Exception  Evt)
        {
           objUti.mostrarMsgErr_F1(this, Evt);
        }  
    } //Funcion obtenerXMLDevolucionVenta()
    
    
    private boolean consultarEstadoDevolucionVentaContingencia(java.sql.Connection conn)
    {
        boolean blnRes=false;
        String strSql;
        String strMsg, strRes;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strSql="";
                strSql+=" SELECT tx_deserrfacele,st_autfacele, fe_ing, * \n";
                strSql+=" FROM tbm_cabmovinv  \n";
                strSql+=" WHERE co_emp= "+objParSis.getCodigoEmpresa()+" AND \n";
                strSql+="       co_loc= "+objParSis.getCodigoLocal()+" AND \n";
                strSql+="       co_tipDoc=229 AND co_doc="+txtCodDoc.getText()+" \n";
                strSql+=" \n";
                System.out.println("consultarEstadoDevolucionVentaContingencia: \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("st_autfacele")!=null){
                        strRes=rstLoc.getString("st_autfacele");
                        if(strRes.equals("C")){
                           blnRes=true;
                        }
                        else if(strRes.equals("X") || strRes.equals("E")){
                            strMsg="<html> Su devolución de venta fue enviada al SRI, pero presentó errores. <BR>" ;
                            strMsg+="Por Favor comuniquese con el administrador del sistema. <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                            blnRes=false;
                        }
                        else if(strRes.equals("A")){
                            strMsg="<html> Su devolución de venta ya fue Autorizada. " ;
                            strMsg+=" <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                            blnRes=false;
                        }
                        else if(strRes.equals("S")){
                            strMsg="<html> Su devolución de venta ya fue enviada al SRI. <BR>" ;
                        strMsg+="Por favor espere pacientemente a que el SRI la autorice. <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                            blnRes=false;
                        }
                        else{
                            strMsg="<html> Su devolución de venta fue enviada al SRI, pero presentó errores. <BR>" ;
                            strMsg+="Por Favor comuniquese con el administrador del sistema. <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                            blnRes=false;
                        }
                    }
                    else{
                        strMsg="<html> Su devolución de venta está en proceso. <BR>" ;
                        strMsg+="Por favor espere pacientemente a que el Sistema la procese. <html>";
                        String strTit="Mensaje del sistema Zafiro";
                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                        blnRes=false;
                    }
                        
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc=null;
                rstLoc=null;
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
    } //Funcion consultarEstadoDevolucionVentaContingencia()
    
      
    private boolean cambiarEstadoFacturacionElectronica(java.sql.Connection conn)
    {
        boolean blnRes=false;
        String strSql;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabmovinv SET st_autfacele='S' \n";
                strSql+=" WHERE co_emp= "+objParSis.getCodigoEmpresa()+" AND \n";
                strSql+="       co_loc= "+objParSis.getCodigoLocal()+" AND \n";
                strSql+="       co_tipDoc=229 AND co_doc="+txtCodDoc.getText()+" ;\n";
                System.out.println("cambiarEstadoFacturacionElectronica" + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
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
    } //Funcion cambiarEstadoFacturacionElectronica()
    
    
    private boolean enviarPulsoFacturacionElectronica()
    {
        objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciar();
        //System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica  ");
        return true;
    }
    
    private boolean enviarPulsoServicio15(String strPrmSer)
    {
       objPulFacEle = new ZafPulFacEle();
       objPulFacEle.iniciarEnvioPulsoServicio15(strPrmSer);
       //System.out.println(" PULSO::::::  enviarPulsoServicio15  ");
       return true;
    }
}