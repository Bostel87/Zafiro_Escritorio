/*
 * ZafVen25.java
 *
 * Created on 06 de Agosto de 2008
 */
package Ventas.ZafVen11;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jayapata MODIFICADO EFLORESA PROGRAMA DE SOLICITUDES DE DEVOLUCION DE
 * VENTAS
 *
 */
public class ZafVen11 extends JInternalFrame {

    private ZafParSis objZafParSis;
    private ToolBar objTooBar;
    private String strAux = "";
    private ZafTblMod objTblMod;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtDenConSi, objTblCelEdiTxtDenConNo, objTblCelEdiTxtCanVolFac, objTblCelEdiTxtCanSolNoPrv;
    private ZafTblCelEdiTxt objTblCelNoEdi;
    private ZafDatePicker txtFecDoc;
    private ZafTblCelRenLbl objTblCelRenLblColor;
    private String strFecSis;
    private ZafUtil objUti;
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODITM = 1;        // CODIGO DEL ITEM
    private static final int INT_TBL_CODREG = 2;        // CODIGO DE REGISTRO
    private static final int INT_TBL_CODALT = 3;        // CODIGO ALTERNO DEL ITEM
    private static final int INT_TBL_NOMITM = 4;        // NOMBRE DEL ITEM
    private static final int INT_TBL_CODUNI = 5;        // CODIGO DE LA UNIDAD
    private static final int INT_TBL_DESUNI = 6;        // DESCRIPCION DE LA UNIDAD DE MEDIDA
    private static final int INT_TBL_BUTBOD = 7;        // BOTON PARA BUSCAR BODEGAS
    private static final int INT_TBL_CODBOD = 8;        // CODIGO DE LA BODEGA
    private static final int INT_TBL_NOMBOD = 9;        // NOMBRE DE LA BODEGA
    private static final int INT_TBL_CANMOV = 10;       // CANTIDAD VENDIDA
    /*agregado por proyecto de reservas CMATEO*/
    private static final int INT_TBL_CANRES = 11;       // CANTIDAD RESERVADA
    /*agregado por proyecto de reservas CMATEO*/
    private static final int INT_TBL_CANDEV = 12;       // CANTIDAD QUE SE DEVOLVERA
    private static final int INT_TBL_PREUNI = 13;       // PRECIO DEL ITEM VENDIDO
    private static final int INT_TBL_PREDEV = 14;       // DEVOLUCION POR PRECIO
    private static final int INT_TBL_PORDES = 15;       // PORCENTAJE DE DESCUENTO DE LA VENTA
    private static final int INT_TBL_DESDEV = 16;       // DEVOLUCION POR DESCUENTO
    private static final int INT_TBL_BLNIVA = 17;       // SI MARCA IVA TIPO BOOLEAN
    private static final int INT_TBL_TOTAL = 18;        // TOTAL DE LA DEVOLUCIOM
    private static final int INT_TBL_REVTEC = 19;       // REVICION TECNICA
    private static final int INT_TBL_CANTOTDEV = 20;    // CANTIDAD TOTAL DE LA DEVOLUCION
    private static final int INT_TBL_CANDEV_ORI = 21;   //  CANTIDAD DEVUELTA ORIGEN
    private static final int INT_TBL_FALSTKFIC = 22;    // FALTANTE DE STOCK FISICO  TIPO BOOLEAN
    private static final int INT_TBL_CANVOLFAC = 23;    //  CANTIDAD QUE SE VOLVERA A FACTURAR
    private static final int INT_TBL_CAN_SOL_NODEPRV = 24;  // CANTIDAD SOLICITADA QUE NO SE DEVUELVE AL PROVEEDOR
    private static final int INT_TBL_SEDEVPRV = 25;     //  CANTIDAD QUE SE DEVOLVERA AL PROVEEDOR
    private static final int INT_TBL_NODEVPRV = 26;     // CANTIDAD QUE NO SE DEVUELVE AL PROVEEDOR
    private static final int INT_TBL_NOMPRV = 27;       //  NOMBRE DEL PROVEEDOR
    private static final int INT_TBL_DEVPRV = 28;       // CANTIDAD QUE SE DEVUELVE AL PROVEEDOR
    private static final int INT_TBL_PRELISREAL = 29;   // PRECIO DE LISTA DEL ITEM
    private static final int INT_TBL_DESMAXREAL = 30;   // MAXIMO DESCUENTO DEL CLIENTE
    
    
    //int intColSigDim=2 ;
    //int intNumReg=0;
    //int IntValOrdVisBue=0;
    //int IntValIni=0;
    //int intNumVisBue=2;  // especifíca el numero de vistos buenos que aparescan.
    //String strArrVisBue[]={"Autorizar","Vis.Bue.Pre."};
    private String strCodCliVolFac = "", strNomCliVolFac = "", strDirCliVolFac="";
    private ZafVenCon objVenConTipdoc; // OBJETO DE VENTANA DE CONSULTA DE TIPOS DE DOCUEMENTO
    private ZafVenCon objVenConVen;   //  OBJETO DE VENTANA DE CONSULTA DE VENEDEDOR
    private ZafVenCon objVenConCli;
    private ZafVenCon objVenConDirCli;
    private String strCodTipDoc = "", strDesCorTipDoc = "", strDesLarTipDoc = "";
    private JTextField txtCodTipDoc = new JTextField();
    private JTextField txtCodTipDocFac = new JTextField();
    private JTextField txtNumDocSolOcu = new JTextField();
    private Connection CONN_GLO = null, conCab = null;
    private Statement STM_GLO = null;
    private ResultSet rstCab = null;
    private Vector vecCab = new Vector();    //Almacena las cabeceras  /**/
    // int[] intCodCodUsr;
    //int intSecCodUsr=0;
    private String strCodSol = "";
    private String strDesCodTitpDoc = "";
    private String strDesLarTipDocSol = "";
    private String strDesSol = "";
    private String stIvaVen = "S";
    private String strEstAut = "";
    private String VERSION = " v3.20 ";
    //José Marín 5 AGOSTO 2015
    private int intEstDev1 = 0, intEstDev2 = 1, intEstDev3 = 1, intEstDev4 = 1;
    private int intEstCon = 0;
    private int intCodEmpSol = 0;
    private int intCodLocSol = 0;
    private int intCodTipDocSol = 0;
    private int intCodDocSol = 0;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private boolean blnCarSol = false;
    private boolean blnHayCam = false;                    // SI HAY CAMBIOS EN LA TABLA
    private boolean blnMarTodCanTblDat = true;            //coloca en todas las celdas la cantidad vendida.
    private double dblPorIva = 0;
    /*cambio por compensacion solidaria porcentaje de iva */
    private double dblCompSol=0;
    /*cambio por compensacion solidaria porcentaje de iva */
    /*Agregado para el 1 de Junio 2017 (vuelve al 12% iva)*/
    private double dblPorComSol=0;
    /*Agregado para el 1 de Junio 2017 (vuelve al 12% iva)*/
    private double dblPorDesEsp = 0.00;
    private String strSQL;
    private ZafVenCon objVenConMot;
    private String strCodMot = "";
    private String strDesLarMot = "";

    private String strCodigoCliente;
    private JTextField txtSubTotIva;
    private JTextField txtSubTotIvaCero;
    
    
    private BigDecimal bigPorComSolVen;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;  
    private java.util.Date datFecAux; 
    
    
    /**
     * Creates new form ZafCom33
     */
    public ZafVen11(Librerias.ZafParSis.ZafParSis obj) {
        try { /**/
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            txtSubTotIva=new JTextField();
            txtSubTotIvaCero=new JTextField();

            this.setTitle(objZafParSis.getNombreMenu() + VERSION); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            objUti = new ZafUtil(); /**/
            objTooBar = new ToolBar(this);
            this.getContentPane().add(objTooBar, "South");

            intCodEmpSol = objZafParSis.getCodigoEmpresa();
            intCodLocSol = objZafParSis.getCodigoLocal();
            
            Calendar calFecha=Calendar.getInstance();
            bigPorComSolVen=objZafParSis.getPorcentajeCmpSolVentas(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), calFecha.getTime());

            bigPorComSolVen=bigPorComSolVen!=null?bigPorComSolVen:new BigDecimal(0);
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objZafParSis);    

            txtFecDoc = new ZafDatePicker(((JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenSol.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            //chkVolFac.setEnabled(false);  // JoséMarín 10/Marzo/2015
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }  
    }

    private boolean configurarVenConDirCli() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel1");
            arlCam.add("a.tx_tel2");
            arlCam.add("a.tx_tel3");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Dirección");
            arlAli.add("Telefóno1");
            arlAli.add("Telefóno2");
            arlAli.add("Telefóno3");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("45");
            arlAncCol.add("300");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");

            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = "SELECT a.co_cli, a.tx_dir, a.tx_tel1, a.tx_tel2, a.tx_tel3  FROM  tbm_dircli as a WHERE  a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_cli=0 ";

            objVenConDirCli = new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConClientes() {
        boolean blnRes = true;
        try {
            objVenConCli=null;
            ArrayList arlCam = new ArrayList();
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
            arlCam.add("a.ne_diamesmaxemifacven");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("Tipo");
            arlAli.add("Desc");
            arlAli.add("Utili");
            arlAli.add("Ciudad");
            arlAli.add("co_vendedor");
            arlAli.add("Vendedor");
            arlAli.add("CoDTipPer");
            arlAli.add("TipoForPag");
            arlAli.add("Peso");
            arlAli.add("Dias Gracia");
            arlAli.add("CodForPag");
            arlAli.add("Descri");
            arlAli.add("IvaVen");
            arlAli.add("EstNomCli");
            arlAli.add("DiaMaxFacVen");

            ArrayList arlAncCol = new ArrayList();
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
            arlAncCol.add("20");

            //Armar la sentencia SQL.
            String strSQL;
            int intCodCliente=0, intRel=0;
            /*3;3029
            4;3370
            1;3938  "INDUSTRIAS COSENCO S.A."
            2;3080
            */
            
            if(txtCodCli.getText().length()>0){
                intCodCliente=Integer.parseInt(txtCodCli.getText());
            }
            
            if(objZafParSis.getCodigoEmpresa()==1){
                if(intCodCliente==3938 /* intCodCliente==3516 JOTA  || intCodCliente==603 || intCodCliente==1039*/){
                    intRel=1;
                }
            }
            if(objZafParSis.getCodigoEmpresa()==2){
                if(intCodCliente==3080 /*intCodCliente==2854  ||*/ /*intCodCliente==446 JOTA*//*|| intCodCliente==789*/){
                    intRel=1;
                }
            }
            if(objZafParSis.getCodigoEmpresa()==4){
                if(/*intCodCliente==3117  || intCodCliente==498 ||   JOTA intCodCliente==886 */ intCodCliente ==3370){
                    intRel=1;
                }
            }
            if(intRel==1){
                strSQL = " SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti, tx_desLar, co_ven, vendedor, co_tipper,"
                   + " ne_tipforpag,  nd_pes, ne_diagra, co_forpag, tx_des, st_ivaven, st_peringnomclicotven, ne_diamesmaxemifacven  "
                   + " FROM ( "
                   + " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor, a.co_tipper, "
                   + " b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag, b.tx_des, a.st_ivaven, a.st_peringnomclicotven, a.ne_diamesmaxemifacven FROM tbr_cliloc AS a1 "
                   + " INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   "
                   + " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  "
                   + " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)"
                   + " left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "
                   + " WHERE a1.co_emp =" + objZafParSis.getCodigoEmpresa() + " and a1.co_loc = " + objZafParSis.getCodigoLocal() + " and a.st_reg IN('A','N')  and a.st_cli='S' order by a.tx_nom  "
                   + ") AS a";
            }
            else{
              strSQL = " SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti, tx_desLar, co_ven, vendedor, co_tipper,"
                   + " ne_tipforpag,  nd_pes, ne_diagra, co_forpag, tx_des, st_ivaven, st_peringnomclicotven, ne_diamesmaxemifacven  "
                   + " FROM ( "
                   + " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor, a.co_tipper, "
                   + " b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag, b.tx_des, a.st_ivaven, a.st_peringnomclicotven, a.ne_diamesmaxemifacven FROM tbr_cliloc AS a1 "
                   + " INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   "
                   + " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  "
                   + " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)"
                   + " left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "
                   + " WHERE a1.co_emp =" + objZafParSis.getCodigoEmpresa() + " and a1.co_loc = " + objZafParSis.getCodigoLocal() + " and a.st_reg IN('A','N')  and a.st_cli='S'   "
                   + " and ( a.co_grp=(\n" +
                        " SELECT a.co_grp \n" +
                        " FROM tbm_grpCli as a \n" +
                        " INNER JOIN tbm_cli as b ON (a.co_emp=b.co_emp AND a.co_grp=b.co_grp) "
                      + " WHERE b.co_emp="+objZafParSis.getCodigoEmpresa()+" AND  b.co_cli="+intCodCliente+" ) OR a.co_cli="+intCodCliente+" )  "    
                                              + "order by a.tx_nom  )  AS a";
            }
            System.out.println("configurarVenConClientes J" +strSQL );
            int intColOcu[] = new int[9];
            intColOcu[0] = 6;
            intColOcu[1] = 7;
            intColOcu[2] = 8;
            intColOcu[3] = 9;
            intColOcu[4] = 10;
            intColOcu[5] = 11;
            intColOcu[6] = 12;
            intColOcu[7] = 16;
            intColOcu[8] = 17;
            // intColOcu[9]=19;

            objVenConCli = new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public ZafVen11(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu() + VERSION);
            lblTit.setText(objZafParSis.getNombreMenu());

            txtSubTotIva=new JTextField();
            txtSubTotIvaCero=new JTextField();

            objUti = new ZafUtil();
            objTooBar = new ToolBar(this);
            //this.getContentPane().add(objTooBar,"South");

            blnCarSol = true;

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenSol.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            intCodEmpSol = Integer.parseInt(strCodEmp);
            intCodLocSol = Integer.parseInt(strCodLoc);
            intCodTipDocSol = Integer.parseInt(strCodTipDoc);;
            intCodDocSol = Integer.parseInt(strCodDoc);;
            //chkVolFac.setEnabled(false);  // JoséMarín 10/Marzo/2015
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((screenSize.width - 750) / 2, (screenSize.height - 450) / 2, 750, 450);
            
            Calendar calFecha=Calendar.getInstance();
            bigPorComSolVen=objZafParSis.getPorcentajeCmpSolVentas(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), calFecha.getTime());

            bigPorComSolVen=bigPorComSolVen!=null?bigPorComSolVen:new BigDecimal(0);
            

            txtCodTipDoc.setText(strCodTipDoc);
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /////////FALTANTES
    public ZafVen11(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu() + VERSION);
            lblTit.setText(objZafParSis.getNombreMenu());

            objUti = new ZafUtil();
            objTooBar = new ToolBar(this);
            this.getContentPane().add(objTooBar, "South");


            txtSubTotIva=new JTextField();
            txtSubTotIvaCero=new JTextField();
            
            
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenSol.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            intEstCon = 1;
            intCodEmpSol = intCodEmp;
            intCodLocSol = intCodLoc;
            intCodTipDocSol = intCodTipDoc;
            intCodDocSol = intCodDoc;
            //chkVolFac.setEnabled(false);  // JoséMarín 10/Marzo/2015
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((screenSize.width - 750) / 2, (screenSize.height - 450) / 2, 750, 450);
            
            Calendar calFecha=Calendar.getInstance();
            if(objZafParSis.getCodigoEmpresa()==0){
                bigPorComSolVen=objZafParSis.getPorcentajeCmpSolVentas(intCodEmpSol, intCodLocSol, calFecha.getTime());
            }else{
                bigPorComSolVen=objZafParSis.getPorcentajeCmpSolVentas(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), calFecha.getTime());
            }
            bigPorComSolVen=bigPorComSolVen!=null?bigPorComSolVen:new BigDecimal(0);
            

            txtCodTipDoc.setText("" + intCodTipDoc);
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public ZafVen11(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intTipEst) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu() + VERSION);
            lblTit.setText(objZafParSis.getNombreMenu());

            objUti = new ZafUtil();
            objTooBar = new ToolBar(this);
            this.getContentPane().add(objTooBar, "South");

            txtSubTotIva=new JTextField();
            txtSubTotIvaCero=new JTextField();
            

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenSol.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);
            intEstCon = intTipEst;
            intCodEmpSol = intCodEmp;
            intCodLocSol = intCodLoc;
            intCodTipDocSol = intCodTipDoc;
            intCodDocSol = intCodDoc;
            //chkVolFac.setEnabled(false);  // JoséMarín 10/Marzo/2015
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((screenSize.width - 750) / 2, (screenSize.height - 450) / 2, 750, 450);
            
            Calendar calFecha=Calendar.getInstance();
            bigPorComSolVen=objZafParSis.getPorcentajeCmpSolVentas(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), calFecha.getTime());

            bigPorComSolVen=bigPorComSolVen!=null?bigPorComSolVen:new BigDecimal(0);
            

            txtCodTipDoc.setText("" + intCodTipDoc);
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * PREMITE ABRIR CONECCON LOCAL
     */
    public void abrirCon() {
        try {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    /**
     * PERMITE CERRAR CONECCION LOCAL
     */
    public void CerrarCon() {
        try {
            CONN_GLO.close();
            CONN_GLO = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    /**
     * CONFIGURACION DE LA TABLA PRINCIPAL
     *
     * @return true si todo se cargo con exito false si hay algun error
     */
    private boolean Configurartabla() {
        boolean blnRes = false;
        try {
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODREG, "Cod.Reg.");
            vecCab.add(INT_TBL_CODALT, "Cod.Alt.");
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_CODUNI, "Cod.Uni.");
            vecCab.add(INT_TBL_DESUNI, "Des.Uni.");
            vecCab.add(INT_TBL_BUTBOD, "Bodega");
            vecCab.add(INT_TBL_CODBOD, "Cod.Bod.");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod");
            vecCab.add(INT_TBL_CANMOV, "Can.Mov.");
			/*agregado por proyecto de reservas CMATEO*/
            vecCab.add(INT_TBL_CANRES,"Cant. Reservada");
			/*agregado por proyecto de reservas CMATEO*/
            vecCab.add(INT_TBL_CANDEV, "Can.Dev.");
            vecCab.add(INT_TBL_PREUNI, "Pre.Uni.");
            vecCab.add(INT_TBL_PREDEV, "Can.Pre.");
            vecCab.add(INT_TBL_PORDES, "Por.Des.");
            vecCab.add(INT_TBL_DESDEV, "Can.Des.");
            vecCab.add(INT_TBL_BLNIVA, "Iva.");
            vecCab.add(INT_TBL_TOTAL, "Total.");
            vecCab.add(INT_TBL_REVTEC, "Rev.Tec.");
            vecCab.add(INT_TBL_CANTOTDEV, "Can.Tot.Dev");
            vecCab.add(INT_TBL_CANDEV_ORI, "Can.Ori.Dev");
            vecCab.add(INT_TBL_FALSTKFIC, "Fal.Fis");  // José Marín 06/05/2014
            vecCab.add(INT_TBL_CANVOLFAC, "Can.Vol.Fac");
            vecCab.add(INT_TBL_CAN_SOL_NODEPRV, "Can.Sol.NoDevPrv");
            vecCab.add(INT_TBL_SEDEVPRV, "Se.Dev.Prv");
            vecCab.add(INT_TBL_NODEVPRV, "No.Dev.Prv");
            vecCab.add(INT_TBL_NOMPRV, "Nom.Prv");
            vecCab.add(INT_TBL_DEVPRV, "");
            vecCab.add(INT_TBL_PRELISREAL, "");
            vecCab.add(INT_TBL_DESMAXREAL, "");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            Color colFonCol;
            colFonCol = new Color(228, 228, 203);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            ArrayList arlAux = new ArrayList();
            arlAux.add("" + INT_TBL_CANDEV);
            arlAux.add("" + INT_TBL_PREDEV);
            arlAux.add("" + INT_TBL_DESDEV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux = null;

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  /**/
            TableColumnModel tcmAux = tblDat.getColumnModel();  /**/

            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DESDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREDEV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_SEDEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_NODEVPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANVOLFAC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CAN_SOL_NODEPRV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
			/*agregado por proyecto de reservas CMATEO*/
            objTblMod.setColumnDataType(INT_TBL_CANRES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
			/*agregado por proyecto de reservas CMATEO*/
            Vector vecAux = new Vector();

            if (intEstCon == 1) {
                vecAux.add("" + INT_TBL_BUTBOD);
                vecAux.add("" + INT_TBL_REVTEC);
                vecAux.add("" + INT_TBL_FALSTKFIC);
            } else {
                vecAux.add("" + INT_TBL_BUTBOD);
                vecAux.add("" + INT_TBL_CANDEV);
                vecAux.add("" + INT_TBL_DESDEV);
                vecAux.add("" + INT_TBL_PREDEV);
                vecAux.add("" + INT_TBL_REVTEC);
                vecAux.add("" + INT_TBL_FALSTKFIC);
                vecAux.add("" + INT_TBL_SEDEVPRV);
                vecAux.add("" + INT_TBL_NODEVPRV);


                //<editor-fold defaultstate="collapsed" desc="/* Solicitado por Werner Campoverde: Habilitar Opción Volver a Facturar, 24 Junio 2015 */">                
//                vecAux.add("" + INT_TBL_CANVOLFAC);           // Rose: Comentar esta línea en caso de desear Ocultar Columna Can. Volv. Fac.
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="/* Habilitar/InHabilitar Opción Devolver a Proveedor Códigos L */">
                //vecAux.add("" + INT_TBL_CAN_SOL_NODEPRV);   // José Marín M. 8 Sep 2014 Comentado para Ocultar la Columna de Can.Sol.NoDevPrv.
                //</editor-fold>
            }

            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            //tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(objTblCelRenLbl);
            //tcmAux.getColumn(INT_TBL_PREDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            //tcmAux.getColumn(INT_TBL_DESDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_SEDEVPRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NODEVPRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANVOLFAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CAN_SOL_NODEPRV).setCellRenderer(objTblCelRenLbl);

			/*agregado por proyecto de reservas CMATEO*/
            tcmAux.getColumn(INT_TBL_CANRES).setCellRenderer(objTblCelRenLbl);
            /*agregado por proyecto de reservas CMATEO*/
			
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(colFonCol);
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREDEV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESDEV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(165);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(18);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESDEV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PREDEV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_REVTEC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_FALSTKFIC).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_NOMPRV).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_SEDEVPRV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NODEVPRV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANVOLFAC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CAN_SOL_NODEPRV).setPreferredWidth(50);
            
			/*agregado por proyecto de reservas CMATEO*/
            tcmAux.getColumn(INT_TBL_CANRES).setPreferredWidth(50);
			/*agregado por proyecto de reservas CMATEO*/

            /**
             * ***********************************************************************
             */
            if (intEstCon == 1) {

                objTblCelRenLblColor = new ZafTblCelRenLbl();
                objTblCelRenLblColor.setHorizontalAlignment(JLabel.RIGHT);
                objTblCelRenLblColor.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLblColor.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
                tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(objTblCelRenLblColor);
                tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLblColor);
                objTblCelRenLblColor.addTblCelRenListener(new ZafTblCelRenAdapter() {
                    @Override
                    public void beforeRender(ZafTblCelRenEvent evt) {
                        //Mostrar de color gris las columnas impares.

                        if (radDes.isSelected() || radDesEsp.isSelected()) {

                            int intCell = objTblCelRenLblColor.getRowRender();
                            String strPreUni = (tblDat.getValueAt(intCell, INT_TBL_PREUNI) == null ? "" : tblDat.getValueAt(intCell, INT_TBL_PREUNI).toString());
                            String strDes = (tblDat.getValueAt(intCell, INT_TBL_PORDES) == null ? "" : tblDat.getValueAt(intCell, INT_TBL_PORDES).toString());
                            String strPreLis = (tblDat.getValueAt(intCell, INT_TBL_PRELISREAL) == null ? "" : tblDat.getValueAt(intCell, INT_TBL_PRELISREAL).toString());
                            String strMaxDes = (tblDat.getValueAt(intCell, INT_TBL_DESMAXREAL) == null ? "" : tblDat.getValueAt(intCell, INT_TBL_DESMAXREAL).toString());
                            String strDevDes = (tblDat.getValueAt(intCell, INT_TBL_DESDEV) == null ? "" : tblDat.getValueAt(intCell, INT_TBL_DESDEV).toString());
                            double dblPreUni = Double.parseDouble(((strPreUni == null) ? "0" : (strPreUni.equals("") ? "0" : strPreUni)));
                            double dblDes = Double.parseDouble(((strDes == null) ? "0" : (strDes.equals("") ? "0" : strDes)));
                            double dblPreLis = Double.parseDouble(((strPreLis == null) ? "0" : (strPreLis.equals("") ? "0" : strPreLis)));
                            double dblMaxDes = Double.parseDouble(((strMaxDes == null) ? "0" : (strMaxDes.equals("") ? "0" : strMaxDes)));
                            double dblDevDes = Double.parseDouble(((strDevDes == null) ? "0" : (strDevDes.equals("") ? "0" : strDevDes)));
                            double dlbPre1 = dblPreUni * (1 - ((dblDes + dblDevDes) / 100));
                            double dlbPre2 = dblPreLis * (1 - (dblMaxDes / 100));
                            if (dlbPre2 > dlbPre1) {
                                objTblCelRenLblColor.setBackground(Color.ORANGE);
                                objTblCelRenLblColor.setForeground(Color.BLACK);
                                objTblCelRenLblColor.setToolTipText(" Precio de Lista menos Descuento :" + objUti.redondear(dlbPre2, 2) + " \n Precio de Venta menos Descuento :" + objUti.redondear(dlbPre1, 2) + " ");
                            } else {
                                objTblCelRenLblColor.setBackground(Color.WHITE);
                                objTblCelRenLblColor.setForeground(Color.BLACK);
                                objTblCelRenLblColor.setToolTipText("");
                            }

                        }

                    }
                });
            }
            /**
             * ***********************************************************************
             */
            ocultaCol(INT_TBL_DESDEV);
            ocultaCol(INT_TBL_PREDEV);

            /* Aqui se agrega las columnas que van
             ha hacer ocultas
             * */
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODITM);
            arlColHid.add("" + INT_TBL_CODREG);
            arlColHid.add("" + INT_TBL_CODUNI);
            arlColHid.add("" + INT_TBL_CODBOD);
            arlColHid.add("" + INT_TBL_REVTEC);
            arlColHid.add("" + INT_TBL_CANDEV_ORI);
            arlColHid.add("" + INT_TBL_FALSTKFIC);
            arlColHid.add("" + INT_TBL_CANTOTDEV);
            arlColHid.add("" + INT_TBL_DEVPRV);
            arlColHid.add("" + INT_TBL_PRELISREAL);
            arlColHid.add("" + INT_TBL_DESMAXREAL);
            arlColHid.add("" + INT_TBL_SEDEVPRV);
            arlColHid.add("" + INT_TBL_NODEVPRV);

            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;
            objTblCelNoEdi = new ZafTblCelEdiTxt(tblDat);
            objTblCelNoEdi.setEditable(false);

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxt.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREDEV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DESDEV).setCellEditor(objTblCelEdiTxt);


            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
//                      if(radCan.isSelected()){
//                         if(tblDat.getValueAt(intCelSel, INT_TBL_CANDEV)!=null){
//                           String strDev=(tblDat.getValueAt(intCelSel, INT_TBL_CANDEV)==null?"":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString());
//                           String strTotDev=(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTDEV)==null?"":tblDat.getValueAt(intCelSel, INT_TBL_CANTOTDEV).toString());
//                           String strDevOri=(tblDat.getValueAt(intCelSel, INT_TBL_CANDEV_ORI)==null?"":tblDat.getValueAt(intCelSel, INT_TBL_CANDEV_ORI).toString());
//
//                           double dblCanTotDev = objUti.redondear((strTotDev.equals("")?"0":strTotDev),4);
//                           double dblCanDev = objUti.redondear((strDev.equals("")?"0":strDev),4);
//                           double dblCanDevOri = objUti.redondear((strDevOri.equals("")?"0":strDevOri),4);
//
//                          String strTipTer =  ((tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV)==null)?"":tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV).toString());
//                          if(strTipTer.trim().equals("S")) {
//                              tblDat.setValueAt( strDev, intCelSel, INT_TBL_SEDEVPRV);
//                          }
//
//                           //System.out.println("TotDev: "+ dblCanTotDev+ " + "+ dblCanDev +" =: "+ (dblCanTotDev+dblCanDev) );
//                          if(dblCanDev < dblCanDevOri ){
//                               calculaSubtotal();
//                          }else{
//
//                           if( (dblCanTotDev+(dblCanDev-dblCanDevOri)) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(),4) ){
//                              if(dblCanTotDev > 0 )
//                                 MensajeInf("Este item ya tiene solicitud y No puede exceder a la cantidad origen.\nVerifique los datos he intente nuevamente.");
//                              else   MensajeInf("No puede exceder a la cantidad origen.\nVerifique los datos he intente nuevamente.");
//                                 tblDat.setValueAt( ""+dblCanDevOri , intCelSel, INT_TBL_CANDEV);
//                                 tblDat.setValueAt( ""+dblCanDevOri , intCelSel, INT_TBL_SEDEVPRV);
//                                 calculaSubtotal();
//                             }else{
//                                /*if( objUti.redondear((strDev.equals("")?"0":strDev),4) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(),4) ){
//                                   MensajeInf("No puede exceder a la cantidad origen.\nVerifique los datos he intente nuevamente.");
//                                   tblDat.setValueAt( null , intCelSel, INT_TBL_CANDEV);
//                                   calculaSubtotal();
//                                 }else*/
//                                  calculaSubtotal();
//                             }
//                          }
//
//
//                       }}

                    if (radCan.isSelected()) {
                        if (tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) != null) {
                            //Jose Marin
                            //  tblDat.setValueAt( false , intCelSel, INT_TBL_FALSTKFIC);

                            String strDev = (tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString());  //Cant a devolver
                            String strTotDev = (tblDat.getValueAt(intCelSel, INT_TBL_CANTOTDEV) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_CANTOTDEV).toString());//
                            String strDevOri = (tblDat.getValueAt(intCelSel, INT_TBL_CANDEV_ORI) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV_ORI).toString());

                            double dblCanTotDev = objUti.redondear((strTotDev.equals("") ? "0" : strTotDev), 4);
                            double dblCanDev = objUti.redondear((strDev.equals("") ? "0" : strDev), 4);
                            double dblCanDevOri = objUti.redondear((strDevOri.equals("") ? "0" : strDevOri), 4);

                            if (chkVolFac.isSelected()) {
                                tblDat.setValueAt("" + dblCanDev, intCelSel, INT_TBL_CANVOLFAC);
                            }

                            String strTipTer = ((tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV).toString());
                            if ((strTipTer.trim().equals("S"))) {
                                if (chkVolFac.isSelected()) {
                                    tblDat.setValueAt("0", intCelSel, INT_TBL_SEDEVPRV);
                                    tblDat.setValueAt("0", intCelSel, INT_TBL_CAN_SOL_NODEPRV);
                                    tblDat.setValueAt("" + dblCanDev, intCelSel, INT_TBL_NODEVPRV);
                                } else {
                                    tblDat.setValueAt("" + dblCanDev, intCelSel, INT_TBL_SEDEVPRV);
                                }
                            }

                            if (dblCanDev < dblCanDevOri) {
                                calculaSubtotal();
                            } else {

                                if ((dblCanTotDev + (dblCanDev - dblCanDevOri)) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) {
                                    if (dblCanTotDev > 0) {
                                        MensajeInf("Este item ya tiene solicitud y No puede exceder a la cantidad origen.\nVerifique los datos e intente nuevamente.");
                                    } else {
                                        MensajeInf("No puede exceder a la cantidad origen.\nVerifique los datos e intente nuevamente.");
                                    }
                                    tblDat.setValueAt("" + dblCanDevOri, intCelSel, INT_TBL_CANDEV);

                                    if (chkVolFac.isSelected()) {
                                        tblDat.setValueAt("" + dblCanDevOri, intCelSel, INT_TBL_CANVOLFAC);
                                    }
                                    if ((strTipTer.trim().equals("S"))) {
                                        if (chkVolFac.isSelected()) {
                                            tblDat.setValueAt("0", intCelSel, INT_TBL_SEDEVPRV);
                                            tblDat.setValueAt("0", intCelSel, INT_TBL_CAN_SOL_NODEPRV);
                                            tblDat.setValueAt("" + dblCanDevOri, intCelSel, INT_TBL_NODEVPRV);
                                        }
                                    } else {
                                        tblDat.setValueAt("" + dblCanDevOri, intCelSel, INT_TBL_SEDEVPRV);
                                    }
                                    //calculaSubtotal();
                                    calculaSubtotalBig();
                                } else {
                                    //calculaSubtotal();
                                    calculaSubtotalBig();
                                }
                            }
                        }
                    }
                    if (radPre.isSelected()) {
                        if (tblDat.getValueAt(intCelSel, INT_TBL_PREDEV) != null) {
                            String strDev = tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString();
                            if (objUti.redondear((strDev.equals("") ? "0" : strDev), 4) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString(), 4)) {
                                MensajeInf("No puede exceder al precio origen.\nVerifique los datos e intente nuevamente.");
                                tblDat.setValueAt(null, intCelSel, INT_TBL_PREDEV);
                                //calculaSubValores();
                                calculaSubValoresBig();
                            } else {
                                //calculaSubValores();
                                calculaSubValoresBig();
                            }
                        }
                    }

                    if (radDes.isSelected()) {
                        if (tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) != null) {
                            String strDev = tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString();
                            if (objUti.redondear((strDev.equals("") ? "0" : strDev), 4) > 100.00) { //objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString(),4) ){
                                MensajeInf("No puede exceder al porcentaje maximo de descuento 100% .\nVerifique los datos e intente nuevamente.");
                                tblDat.setValueAt(null, intCelSel, INT_TBL_DESDEV);
                                //calculaDescuento();
                                calculaDescuentoBig();
                            } else {
                                //calculaDescuento();
                                calculaDescuentoBig();
                            }
                            // calculaDescuento();
                        }
                    }

                    if (radDesEsp.isSelected()) {
                        if (tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) != null) {
                            String strDev = tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString();
                            //dblPorDesEsp = (dblPorDesEsp==0.00)?100:dblPorDesEsp;
                            if (objUti.redondear((strDev.equals("") ? "0" : strDev), 2) != dblPorDesEsp) {
                                MensajeInf("No puede cambiar el porcentaje de descuento para este cliente " + String.valueOf(dblPorDesEsp) + "% \nVerifique los datos e intente nuevamente.");
                                tblDat.setValueAt(String.valueOf(dblPorDesEsp), intCelSel, INT_TBL_DESDEV);
                                //calculaDescuento();
                                calculaDescuentoBig();
                            } else {
                                //calculaDescuento();
                                calculaDescuentoBig();
                            }
                            // calculaDescuento();
                        }
                    }
                }
            });

            objTblCelEdiTxtDenConSi = new ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxtDenConSi.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_SEDEVPRV).setCellEditor(objTblCelEdiTxtDenConSi);
            objTblCelEdiTxtDenConSi.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if (intNumFil >= 0) {
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intNumFil, INT_TBL_DEVPRV).toString());
                        if (!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtDenConSi.setCancelarEdicion(true);
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (intCelSel >= 0) {
                        String strTipTer = ((tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV).toString());
                        if (!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtDenConSi.setCancelarEdicion(true);
                        } else {

                            double dblDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 4);
                            double dblDevNoPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_NODEVPRV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_NODEVPRV).toString()), 4);
                            double dblDevSiPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_SEDEVPRV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_SEDEVPRV).toString()), 4);

                            if ((dblDevNoPrv + dblDevSiPrv) > dblDev) {
                                MensajeInf("No puede exceder a la cantidad que se devolvera.\nVerifique los datos e intente nuevamente.");
                                tblDat.setValueAt(null, intCelSel, INT_TBL_SEDEVPRV);
                            }
                        }
                    }

                }
            });

            objTblCelEdiTxtDenConNo = new ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxtDenConNo.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_NODEVPRV).setCellEditor(objTblCelEdiTxtDenConNo);
            objTblCelEdiTxtDenConNo.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if (intNumFil >= 0) {
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intNumFil, INT_TBL_DEVPRV).toString());
                        if (!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtDenConNo.setCancelarEdicion(true);
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (intCelSel >= 0) {
                        String strTipTer = ((tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV).toString());
                        if (!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtDenConNo.setCancelarEdicion(true);
                        } else {

                            if (!chkVolFac.isSelected()) {
                                MensajeInf("NO ESTA MARCADO VOLVER A FACTURAR.\n VERIFIQUE LOS DATOS.");
                            }

                            double dblDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 4);
                            double dblDevNoPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_NODEVPRV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_NODEVPRV).toString()), 4);
                            double dblDevSiPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_SEDEVPRV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_SEDEVPRV).toString()), 4);

                            if ((dblDevNoPrv + dblDevSiPrv) > dblDev) {
                                MensajeInf("No puede exceder a la cantidad que se devolvera.\nVerifique los datos e intente nuevamente.");
                                tblDat.setValueAt(null, intCelSel, INT_TBL_NODEVPRV);
                            }
                        }
                    }
                }
            });

            objTblCelEdiTxtCanVolFac = new ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxtCanVolFac.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_CANVOLFAC).setCellEditor(objTblCelEdiTxtCanVolFac);
            objTblCelEdiTxtCanVolFac.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    if (!chkVolFac.isSelected()) {
                        objTblCelEdiTxtCanVolFac.setCancelarEdicion(true);
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {

                    int intNumFil = tblDat.getSelectedRow();
                    int intCelSel = tblDat.getSelectedRow();
                    if (intCelSel >= 0) {

                        String strTipTer = ((tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV).toString());

                        /* CANTIDAD A DEVOLVER*/
                        double dblDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("")) ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 4);
                        /* CANTIDAD SOLICITADA QUE NO SE DEVUELVE AL PROVEEDOR */
                        double dblDevNoPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_SOL_NODEPRV) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CAN_SOL_NODEPRV).toString().equals("")) ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_SOL_NODEPRV).toString()), 4);
                        /* CANTIDAD PARA VOLVER A FACTURAR */
                        double dblDevSiPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC).toString().equals("")) ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC).toString()), 4);

                        if ((dblDevNoPrv + dblDevSiPrv) > dblDev) {
                            MensajeInf("No puede exceder a la cantidad que se devolvera.\nVerifique los datos e intente nuevamente.");
                            tblDat.setValueAt(null, intCelSel, INT_TBL_CANVOLFAC);
                            tblDat.setValueAt(null, intCelSel, INT_TBL_NODEVPRV);
                            tblDat.setValueAt(null, intCelSel, INT_TBL_SEDEVPRV);
                        } else {

                            if ((strTipTer.trim().equals("S"))) {
                                tblDat.setValueAt("" + (dblDevNoPrv + dblDevSiPrv), intCelSel, INT_TBL_NODEVPRV);
                                tblDat.setValueAt("" + (dblDev - (dblDevNoPrv + dblDevSiPrv)), intCelSel, INT_TBL_SEDEVPRV);
                            }

                        }
                    }
                    //AKI SACAR EL CHKBOX DE FALTA DE STOCK
                    // José Marín 6/Mayo/2014
                    //tblDat.setValueAt(false, intNumFil, INT_TBL_FALSTKFIC);  
                }
            });

            objTblCelEdiTxtCanSolNoPrv = new ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxtCanSolNoPrv.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_CAN_SOL_NODEPRV).setCellEditor(objTblCelEdiTxtCanSolNoPrv);
            objTblCelEdiTxtCanSolNoPrv.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if (intNumFil >= 0) {
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intNumFil, INT_TBL_DEVPRV).toString());
                        if (!(strTipTer.trim().equals("S"))) {
                            objTblCelEdiTxtCanSolNoPrv.setCancelarEdicion(true);
                        }
                        if (!(strTipTer.trim().equals("L"))) {
                            objTblCelEdiTxtCanSolNoPrv.setCancelarEdicion(false);
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if (intCelSel >= 0) {
                        double dblDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("")) ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 4);
                        double dblDevNoPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CAN_SOL_NODEPRV) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CAN_SOL_NODEPRV).toString().equals("")) ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CAN_SOL_NODEPRV).toString()), 4);
                        double dblDevSiPrv = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC) == null ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC).toString().equals("")) ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC).toString()), 4);
                        if ((dblDevNoPrv + dblDevSiPrv) > dblDev) {
                            MensajeInf("No puede exceder a la cantidad que se devolvera.\nVerifique los datos e intente nuevamente.");
                            tblDat.setValueAt(null, intCelSel, INT_TBL_CAN_SOL_NODEPRV);
                        } else {
                            tblDat.setValueAt("" + (dblDevNoPrv + dblDevSiPrv), intCelSel, INT_TBL_NODEVPRV);
                            tblDat.setValueAt("" + (dblDev - (dblDevNoPrv + dblDevSiPrv)), intCelSel, INT_TBL_SEDEVPRV);
                        }
                    }
                }
            });

            ZafTblCelRenChk objTblCelRenChk;
            ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;

            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_FALSTKFIC).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_FALSTKFIC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                public void afterEdit(ZafTableEvent evt) {
                    //SI SELECIONO EL CHKBOX
                    int intNumFil = tblDat.getSelectedRow();

                    //<editor-fold defaultstate="collapsed" desc="/* Solicitado por Werner Campoverde: Habilitar Opción Volver a Facturar, 29 Abril 2015 */">
                    tblDat.setValueAt("", intNumFil, INT_TBL_CANVOLFAC); // Rose: Comentar esta línea en caso de que se quiera Bloquear la edición de la Columna Can. Volv. Fac., es decir,  NO sea Editable.
                    //</editor-fold>
                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;


            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_REVTEC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();

                    if (tblDat.getValueAt(intCelSel, INT_TBL_FALSTKFIC).toString().equals("true")) {
                        tblDat.setValueAt(false, intCelSel, INT_TBL_REVTEC);
                        MensajeInf("Error! No es posible establecer revisión técnica porque este ítem tiene faltante físico de stock. ");
                    } else {

                        double dblDev = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString()), 4);
                        double dblVolFac = objUti.redondear((tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC) == null ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANVOLFAC).toString()), 4);
                        if (dblVolFac >= dblDev) {
                            tblDat.setValueAt(false, intCelSel, INT_TBL_REVTEC);
                            MensajeInf("Error! No es posible establecer revisión técnica porque se esta volviendo a facturar toda las mercaderia.");
                        }

                    }
                }
            });
            objTblCelRenChk = null;
            objTblCelEdiChk = null;

            ZafTblCelRenBut objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;
            ButBod butBod = new ButBod(tblDat, INT_TBL_BUTBOD); //*****
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            if (!(intEstCon == 1)) {

                //Configurar JTable: Establecer los listener para el TableHeader.
                tblDat.getTableHeader().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        tblDatMouseClicked(evt);
                    }
                });

            }

            tcmAux = null;

            setEditable(true);

            blnRes = true;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

//      /**
//     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
//     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
//     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
//            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_CANDEV){
//              if (blnMarTodCanTblDat){
//                   //Mostrar todas las columnas.
//                    for (i=0; i<intNumFil; i++)
//                    {
//                        String strCanven= (tblDat.getValueAt(i, INT_TBL_CANMOV)==null?"":tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
//                        tblDat.setValueAt( strCanven, i, INT_TBL_CANDEV);
//
//                        String strTipTer =  ((tblDat.getValueAt(i, INT_TBL_DEVPRV)==null)?"":tblDat.getValueAt(i, INT_TBL_DEVPRV).toString());
//                        if(strTipTer.trim().equals("S")) {
//                          tblDat.setValueAt( strCanven, i, INT_TBL_SEDEVPRV);
//                        }
//
//
//                    }
//                    blnMarTodCanTblDat=false;
//               }else{
//                    //Ocultar todas las columnas.
//                    for (i=0; i<intNumFil; i++)
//                    {
//                        tblDat.setValueAt("0", i, INT_TBL_CANDEV);
//                        tblDat.setValueAt("0", i, INT_TBL_SEDEVPRV);
//                        tblDat.setValueAt("0", i, INT_TBL_NODEVPRV);
//                    }
//                    blnMarTodCanTblDat=true;
//                }
//                CalculaTotales();
//            }

            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_CANDEV) {

                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        // jota
                        //tblDat.setValueAt( false , i, INT_TBL_FALSTKFIC);
                        String strCanven = (tblDat.getValueAt(i, INT_TBL_CANMOV) == null ? "" : tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
                        tblDat.setValueAt(strCanven, i, INT_TBL_CANDEV);

                        if (chkVolFac.isSelected()) {
                            tblDat.setValueAt(strCanven, i, INT_TBL_CANVOLFAC);
                        }

                        String strTipTer = ((tblDat.getValueAt(i, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(i, INT_TBL_DEVPRV).toString());
                        if ((strTipTer.trim().equals("S"))) {
                            if (radCan.isSelected()) {
                                if (chkVolFac.isSelected()) {
                                    tblDat.setValueAt("0", i, INT_TBL_SEDEVPRV);
                                    tblDat.setValueAt("0", i, INT_TBL_CAN_SOL_NODEPRV);
                                    tblDat.setValueAt(strCanven, i, INT_TBL_NODEVPRV);

                                } else {
                                    tblDat.setValueAt(strCanven, i, INT_TBL_SEDEVPRV);
                                }

                            }
                        }

                    }
                    blnMarTodCanTblDat = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        tblDat.setValueAt("0", i, INT_TBL_CANDEV);
                        tblDat.setValueAt("0", i, INT_TBL_SEDEVPRV);
                        tblDat.setValueAt("0", i, INT_TBL_NODEVPRV);
                        tblDat.setValueAt("0", i, INT_TBL_CANVOLFAC);
                        tblDat.setValueAt("0", i, INT_TBL_CAN_SOL_NODEPRV);


                    }
                    blnMarTodCanTblDat = true;
                }
                //CalculaTotales();
                CalculaTotalesBig();
            }


            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblDat.columnAtPoint(evt.getPoint()) == INT_TBL_SEDEVPRV) {
                if (blnMarTodCanTblDat) {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        String strTipTer = ((tblDat.getValueAt(i, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(i, INT_TBL_DEVPRV).toString());
                        if ((strTipTer.trim().equals("S"))) {
                            String strCanven = (tblDat.getValueAt(i, INT_TBL_CANDEV) == null ? "" : tblDat.getValueAt(i, INT_TBL_CANDEV).toString());
                            tblDat.setValueAt(strCanven, i, INT_TBL_SEDEVPRV);
                        }
                    }
                    blnMarTodCanTblDat = false;
                } else {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) {
                        tblDat.setValueAt("0", i, INT_TBL_SEDEVPRV);
                    }
                    blnMarTodCanTblDat = true;
                }
            }


        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void CalculaTotales() {

        if (radCan.isSelected()) {
            calculaSubtotalTod();
        }
        if (radDes.isSelected() || radDesEsp.isSelected()) {
            calculaDescuentoTod();
        }
        if (radPre.isSelected()) {
            calculaSubValoresTod();
        }

    }
    
    
    private void CalculaTotalesBig() {

        if (radCan.isSelected()) {
            calculaSubtotalTodBig();
        }
        if (radDes.isSelected() || radDesEsp.isSelected()) {
            calculaDescuentoTodBig();
        }
        if (radPre.isSelected()) {
            calculaSubValoresTodBig();
        }

    }    

    public void calculaSubtotalTod() {
        try {
            for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {

                double dblCanFac = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2),
                        dblCanDev = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString())))), 2),
                        dblCan = dblCanFac - dblCanDev,
                        dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString()))),
                        dblPorDes = objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))), 2),
                        dblValDes = ((dblCanDev * dblPre) == 0) ? 0 : ((dblCanDev * dblPre) * dblPorDes / 100),
                        dblTotal = (dblCanDev * dblPre) - dblValDes;
                dblTotal = objUti.redondear(dblTotal, 3);
                dblTotal = objUti.redondear(dblTotal, 2);

                tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);
            }

            calculaTotal();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
    
    
    public void calculaSubtotalTodBig() {
        try {
            for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {

                BigDecimal bigCanFac = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))).setScale(2, RoundingMode.HALF_UP),
                        bigCanDev = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString())))).setScale(2, RoundingMode.HALF_UP),
                        bigCan = bigCanFac.subtract(bigCanDev) ,
                        bigPre = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString()))),
                        bigPorDes = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))).setScale(2, RoundingMode.HALF_UP),
                        bigValDes = ((bigCanDev.multiply(bigPre).compareTo(BigDecimal.ZERO)) == 0) ? BigDecimal.ZERO : (bigCanDev.multiply(bigPre).multiply(bigPorDes.divide(BigDecimal.valueOf(100))) ),
                        bigTotal = ((bigCanDev.multiply(bigPre)).subtract(bigValDes)).setScale(2, RoundingMode.HALF_UP);

                tblDat.setValueAt(bigTotal, intCelSel, INT_TBL_TOTAL);
            }

            //calculaTotal();
            calculaTotalBig();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }    

    public void calculaSubValoresTod() {
        try {
            for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {

                double dblCan = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2);
                double dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
                double dblPorDes = objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString())))), 2);

                double dblValDes = (dblCan * dblPre);
                double dblTotal = (dblValDes * (dblPorDes / 100));

                dblTotal = objUti.redondear(dblTotal, 3);
                dblTotal = objUti.redondear(dblTotal, 2);

                tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);
            }
            calculaTotal();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
    
    public void calculaSubValoresTodBig() {
        try {
            for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {

                BigDecimal bigCan = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))).setScale(2, RoundingMode.HALF_UP);
                BigDecimal bigPre = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
                BigDecimal bigPorDes = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString())))).setScale(2, RoundingMode.HALF_UP);

                BigDecimal bigValDes = (bigCan.multiply(bigPre));
                BigDecimal bigTotal = (bigValDes.multiply(bigPorDes.divide(BigDecimal.valueOf(100)))).setScale(2, RoundingMode.HALF_UP);

                tblDat.setValueAt(bigTotal, intCelSel, INT_TBL_TOTAL);
            }
            calculaTotalBig();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void calculaDescuentoTod() {
        try {
            for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {
                double dblCan = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2);
                double dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
                double dblPorDes = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString())))), objZafParSis.getDecimalesBaseDatos());


                double dblDesItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString())))), objZafParSis.getDecimalesBaseDatos());
                double dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblDesItm / 100));
                dblValDes = (dblCan * dblPre) - dblValDes;

                System.out.println("" + (dblCan * dblPre));

                System.out.println("" + dblValDes);

                //double dblValDes =   (dblCan * dblPre);
                double dblTotal = (dblValDes * (dblPorDes / 100));

                dblTotal = objUti.redondear(dblTotal, 3);
                dblTotal = objUti.redondear(dblTotal, 2);

                tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);

            }
            calculaTotal();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
    

    public void calculaDescuentoTodBig() {
        try {
            for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {
                BigDecimal bigCan = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))).setScale(2, RoundingMode.HALF_UP);
                BigDecimal bigPre = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
                BigDecimal bigPorDes = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString())))).setScale(2, RoundingMode.HALF_UP);


                BigDecimal bigDesItm = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString())))).setScale(2, RoundingMode.HALF_UP);
                BigDecimal bigValDes = ((bigCan.multiply(bigPre).compareTo(BigDecimal.ZERO)) == 0) ? BigDecimal.ZERO : ((bigCan.multiply(bigPre)).multiply(bigDesItm.divide(BigDecimal.valueOf(100))));
                bigValDes = (bigCan.multiply(bigPre)).subtract(bigValDes);

                /*System.out.println("" + (dblCan * dblPre));

                System.out.println("" + dblValDes);*/

                //double dblValDes =   (dblCan * dblPre);
                BigDecimal bigTotal = (bigValDes.multiply(bigPorDes.divide(BigDecimal.valueOf(100)))).setScale(2, RoundingMode.HALF_UP);


                tblDat.setValueAt(bigTotal, intCelSel, INT_TBL_TOTAL);

            }
            calculaTotalBig();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
    

    private class ButBod extends ZafTableColBut_uni {

        public ButBod(JTable tbl, int intIdx) {
            super(tbl, intIdx, "Guía de remisión.");
        }

        @Override
        public void butCLick() {
            int intCol = tblDat.getSelectedRow();
            llamarVentanaFac(intCol);
        }
    }

    private void llamarVentanaFac(int intCol) {
        String strSql = "";

        strSql = "SELECT a.co_bod, a1.tx_nom FROM tbr_bodloc as a "
                + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "
                + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " AND  a.st_reg='P' ";

//   strSql="SELECT  a.co_bod, a1.tx_nom   FROM  tbr_bodempbodgrp AS a " +
//   " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "+
//   " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa();

        ZafVen11_02 obj = new ZafVen11_02(JOptionPane.getFrameForComponent(this), true, objZafParSis, strSql);
        obj.show();

        if (obj.acepta()) {
            tblDat.setValueAt(obj.GetCamSel(1), intCol, INT_TBL_CODBOD);
            tblDat.setValueAt(obj.GetCamSel(2), intCol, INT_TBL_NOMBOD);
        }
        obj.dispose();
        obj = null;

    }

    public void calculaSubtotal() {
        int intCelSel = tblDat.getSelectedRow();
        double dblCanFac = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2),
                dblCanDev = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString())))), 2),
                dblCan = dblCanFac - dblCanDev,
                dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString()))),
                dblPorDes = objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))), 2),
                dblValDes = ((dblCanDev * dblPre) == 0) ? 0 : ((dblCanDev * dblPre) * dblPorDes / 100),
                dblTotal = (dblCanDev * dblPre) - dblValDes;
        dblTotal = objUti.redondear(dblTotal, 3);
        dblTotal = objUti.redondear(dblTotal, 2);

        tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);
        calculaTotal();
    }

    
public void calculaSubtotalBig() {
        int intCelSel = tblDat.getSelectedRow();
        BigDecimal bigCanFac = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))).setScale(2, RoundingMode.HALF_UP),
        bigCanDev = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString())))).setScale(2, RoundingMode.HALF_UP),
        bigCan = bigCanFac.subtract(bigCanDev) ,
        bigPre = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString()))),
        bigPorDes = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))).setScale(2, RoundingMode.HALF_UP),
        bigValDes = (bigCanDev.multiply(bigPre).compareTo(BigDecimal.ZERO)==0) ? BigDecimal.ZERO : ((bigCanDev.multiply(bigPre)).multiply((bigPorDes.divide(new BigDecimal(100))))),
        bigTotal = ((bigCanDev.multiply(bigPre)).subtract(bigValDes)).setScale(2, RoundingMode.HALF_UP);
        /*dblTotal = objUti.redondear(dblTotal, 3);
        dblTotal = objUti.redondear(dblTotal, 2);*/

        tblDat.setValueAt(bigTotal, intCelSel, INT_TBL_TOTAL);
        //calculaTotal();
        calculaTotalBig();
    }    
    
    
    public void calculaSubValores() {
        int intCelSel = tblDat.getSelectedRow();
        double dblCanFac = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2);
        double dblCanDev = dblCanFac,
                dblCan = dblCanFac - dblCanDev,
                dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString())))),
                dblPorDes = objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))), 2),
                dblValDes = ((dblCanDev * dblPre) == 0) ? 0 : ((dblCanDev * dblPre) * dblPorDes / 100),
                dblTotal = (dblCanDev * dblPre) - dblValDes;

        dblTotal = objUti.redondear(dblTotal, 3);
        dblTotal = objUti.redondear(dblTotal, 2);

        tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);
        calculaTotal();
    }
    
    public void calculaSubValoresBig() {
        int intCelSel = tblDat.getSelectedRow();
        //double dblCanFac = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2);
        BigDecimal bigCanFac = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))).setScale(2, RoundingMode.HALF_UP);
        //double dblCanDev = dblCanFac,
        BigDecimal bigCanDev = bigCanFac,
                //dblCan = dblCanFac - dblCanDev,
                bigCan = bigCanFac.subtract(bigCanDev),
                //dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString())))),
                bigPre = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PREDEV).toString())))),
                //dblPorDes = objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))), 2),
                bigPorDes = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString()))).setScale(2, RoundingMode.HALF_UP),
                //dblValDes = ((dblCanDev * dblPre) == 0) ? 0 : ((dblCanDev * dblPre) * dblPorDes / 100),
                bigValDes = (((bigCanDev.multiply(bigPre)).compareTo(BigDecimal.ZERO)) == 0) ? BigDecimal.ZERO : ((bigCanDev.multiply(bigPre)).multiply((bigPorDes.divide(BigDecimal.valueOf(100))))),
                bigTotal = ((bigCanDev.multiply(bigPre)).subtract(bigValDes)).setScale(2, RoundingMode.HALF_UP);

        /*dblTotal = objUti.redondear(dblTotal, 3);
        dblTotal = objUti.redondear(dblTotal, 2);*/

        //tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);
        tblDat.setValueAt(bigTotal, intCelSel, INT_TBL_TOTAL);
        calculaTotalBig();
    }    

    public void calculaDescuento() {
        int intCelSel = tblDat.getSelectedRow();
        double dblCan = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2);
        double dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
        double dblPorDes = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString())))), objZafParSis.getDecimalesBaseDatos());

        double dblDesItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString())))), objZafParSis.getDecimalesBaseDatos());
        double dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblDesItm / 100));
        dblValDes = (dblCan * dblPre) - dblValDes;


        //double dblValDes =   (dblCan * dblPre);
        double dblTotal = (dblValDes * (dblPorDes / 100));

        dblTotal = objUti.redondear(dblTotal, 3);
        dblTotal = objUti.redondear(dblTotal, 2);

        tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);
        calculaTotal();
    }


    public void calculaDescuentoBig() {
        int intCelSel = tblDat.getSelectedRow();
        //double dblCan = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))), 2);
        BigDecimal bigCan = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString()))).setScale(2, RoundingMode.HALF_UP);
        //double dblPre = Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
        BigDecimal bigPre = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(intCelSel, INT_TBL_PREUNI).toString())));
        //double dblPorDes = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString())))), objZafParSis.getDecimalesBaseDatos());
        BigDecimal bigPorDes = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_DESDEV).toString())))).setScale(2, RoundingMode.HALF_UP);        
        //double dblDesItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString())))), objZafParSis.getDecimalesBaseDatos());
        BigDecimal bigDesItm = new BigDecimal(((tblDat.getValueAt(intCelSel, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(intCelSel, INT_TBL_PORDES).toString())))).setScale(2, RoundingMode.HALF_UP);        
        //double dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblDesItm / 100));
        BigDecimal bigValDes= ((bigCan.multiply(bigPre)).compareTo(BigDecimal.ZERO))==0?BigDecimal.ZERO:(bigCan.multiply(bigPre)).multiply(bigDesItm.divide(BigDecimal.valueOf(100)));
        //dblValDes = (dblCan * dblPre) - dblValDes;
        bigValDes=(bigCan.multiply(bigPre)).subtract(bigValDes);


        //double dblValDes =   (dblCan * dblPre);
        //double dblTotal = (dblValDes * (dblPorDes / 100));
        BigDecimal bigTotal = (bigValDes.multiply(bigPorDes.divide(BigDecimal.valueOf(100)))).setScale(2, RoundingMode.HALF_UP);

        /*dblTotal = objUti.redondear(dblTotal, 3);
        dblTotal = objUti.redondear(dblTotal, 2);*/

        //tblDat.setValueAt(new Double(dblTotal), intCelSel, INT_TBL_TOTAL);
        tblDat.setValueAt(bigTotal, intCelSel, INT_TBL_TOTAL);
        calculaTotalBig();
    }    
    
    
    /*
     * MODIFICADO EFLORESA 2012-04-17
     * DESCUENTO ESPECIAL
     */
    public void calculaDescuento2() {
        try {
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                double dblCan = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_CANMOV).toString()))), 2);
                double dblPre = Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_PREUNI).toString())));
                double dblPorDes = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(i, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_DESDEV).toString())))), objZafParSis.getDecimalesBaseDatos());

                double dblDesItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(i, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_PORDES).toString())))), objZafParSis.getDecimalesBaseDatos());
                double dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblDesItm / 100));
                dblValDes = (dblCan * dblPre) - dblValDes;

                //double dblValDes =   (dblCan * dblPre);
                double dblTotal = (dblValDes * (dblPorDes / 100));

                //dblTotal =  objUti.redondear(dblTotal,3);
                //dblTotal =  objUti.redondear(dblTotal,2);

                tblDat.setValueAt(new Double(dblTotal), i, INT_TBL_TOTAL);
            }
            calculaTotal();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void calculaDescuento2Big() {
        try {
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                //double dblCan = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_CANMOV).toString()))), 2);
                BigDecimal bigCan = new BigDecimal(((tblDat.getValueAt(i, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_CANMOV).toString()))).setScale(2, RoundingMode.HALF_UP);
                //double dblPre = Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_PREUNI).toString())));
                BigDecimal bigPre = new BigDecimal(((tblDat.getValueAt(i, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(i, INT_TBL_PREUNI).toString())));
                //double dblPorDes = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(i, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_DESDEV).toString())))), objZafParSis.getDecimalesBaseDatos());
                BigDecimal bigPorDes = new BigDecimal(((tblDat.getValueAt(i, INT_TBL_DESDEV) == null) ? "0" : ((tblDat.getValueAt(i, INT_TBL_DESDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_DESDEV).toString())))).setScale(2, RoundingMode.HALF_UP);

                //double dblDesItm = objUti.redondear(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(i, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_PORDES).toString())))), objZafParSis.getDecimalesBaseDatos());
                BigDecimal bigDesItm = new BigDecimal(((tblDat.getValueAt(i, INT_TBL_PORDES) == null) ? "0" : ((tblDat.getValueAt(i, INT_TBL_PORDES).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_PORDES).toString())))).setScale(2, RoundingMode.HALF_UP);
                //double dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblDesItm / 100));
                BigDecimal bigValDes = ((bigCan.multiply(bigPre).compareTo(BigDecimal.ZERO)) == 0) ? BigDecimal.ZERO : ((bigCan.multiply(bigPre)).multiply(bigDesItm.divide(BigDecimal.valueOf(100))));
                //dblValDes = (dblCan * dblPre) - dblValDes;
                bigValDes=(bigCan.multiply(bigPre)).subtract(bigValDes);
                //double dblValDes =   (dblCan * dblPre);
                //double dblTotal = (dblValDes * (dblPorDes / 100));
                BigDecimal bigTotal = (bigValDes.multiply(bigPorDes.divide(BigDecimal.valueOf(100))));

                //dblTotal =  objUti.redondear(dblTotal,3);
                //dblTotal =  objUti.redondear(dblTotal,2);

                //tblDat.setValueAt(new Double(dblTotal), i, INT_TBL_TOTAL);
                tblDat.setValueAt(bigTotal, i, INT_TBL_TOTAL);
            }
            //calculaTotal();
            calculaTotalBig();
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }    
    
    public void calculaTotal() {
        double dblSub = 0, dblIva = 0, dblSubtotalDoc = 0, dblTmp = 0, dblSub2 = 0, dblIvaDoc = 0, dblTotalDoc = 0, dblComSol=0;
        int intNumDec = 2;
        //double dblPorCompSol=2d;
        double dblSubTotIva=0, dblSubTotIvaCero=0;
        
        for (int i = 0; i < tblDat.getRowCount(); i++) {
            if (tblDat.getValueAt(i, INT_TBL_CODITM) != null) {
                if (tblDat.getValueAt(i, INT_TBL_CANDEV) != null) {

                    dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL) == null || tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("")) ? 0 : Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
                    dblSub = dblSub + objUti.redondear(dblSub2, intNumDec);
                    if (tblDat.getValueAt(i, INT_TBL_BLNIVA) == null) {
                        dblIva = dblIva + 0;
                    } else {
                        if(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true")){
                            dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true")) ? ((tblDat.getValueAt(i, INT_TBL_TOTAL) == null || tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("")) ? 0 : Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) : 0);
                            dblIva = dblIva + (((dblTmp * dblPorIva) == 0) ? 0 : (dblTmp * dblPorIva) / 100);
                            dblSubTotIva+=dblTmp;
                            dblSubTotIva=objUti.redondear(dblSubTotIva, intNumDec);
                            if(objZafParSis.getCodigoEmpresa()==2 && objZafParSis.getCodigoLocal()==4){
                                if(dblCompSol==0){
                                    dblComSol=dblComSol + 0;
                                    /*AGREGADO PARA PONER EL PORCENTAJE DE COMPENSACION CORRECTO*/
                                    bigPorComSolVen=BigDecimal.ZERO;
                                    /*AGREGADO PARA PONER EL PORCENTAJE DE COMPENSACION CORRECTO*/
                                }else{
                                    Calendar calFecha=Calendar.getInstance();

                                    //BigDecimal bigPorIvaVenNoMan=objZafParSis.getPorcentajeIvaVentas(1, 4, calFecha.getTime());
                                    //bigPorComSolVen=objZafParSis.getPorcentajeCmpSolVentas(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), calFecha.getTime());
                                    bigPorComSolVen=new BigDecimal(dblPorComSol);

                                    bigPorComSolVen=bigPorComSolVen!=null?bigPorComSolVen:new BigDecimal(0);


                                    //dblComSol =  dblComSol + (((dblTmp * bigPorIvaVenNoMan.doubleValue()) == 0) ? 0 : (dblTmp * bigPorIvaVenNoMan.doubleValue()) / 100) - (((dblTmp * (bigPorIvaVenNoMan.doubleValue()-2) ) == 0) ? 0 : (dblTmp * (bigPorIvaVenNoMan.doubleValue()-2)) / 100);

                                    dblComSol = dblComSol + (((dblTmp * bigPorComSolVen.doubleValue()) == 0) ? 0 : (dblTmp * bigPorComSolVen.doubleValue()) / 100);
                                }
                            }else{
                                dblComSol = dblComSol+0;
                            }
                        }else{
                            dblSubTotIvaCero+=dblSub2;
                            dblSubTotIvaCero=objUti.redondear(dblSubTotIvaCero, intNumDec);
                            dblComSol=dblComSol+0;
                        }
                    }
                }
            }
        }
        /////////////////////////////////////////////////////////////
        dblSubtotalDoc = dblSub;
        dblIvaDoc = objUti.redondear(dblIva, intNumDec);
        
        /*AGREGADO PARA COMPENSACION SOLIDARIA*/
        dblComSol =objUti.redondear(dblComSol, 2);
        /*AGREGADO PARA COMPENSACION SOLIDARIA*/
        
        //dblTotalDoc = dblSubtotalDoc + dblIvaDoc;
        dblTotalDoc = (dblSubtotalDoc + dblIvaDoc)- dblComSol;
        dblTotalDoc = objUti.redondear(dblTotalDoc, intNumDec);
        dblSubtotalDoc = objUti.redondear(dblSubtotalDoc, intNumDec);
        
        //System.out.println(" Iva:  "+ stIvaVen + " - "+ dblPorIva +" - "+ dblIvaDoc );

        if (stIvaVen.equals("N")) {
            txtSub.setText("" + dblSubtotalDoc);
            txtIva.setText("0.00");
            txtTot.setText("" + dblSubtotalDoc);
            dblTotalDoc = dblSubtotalDoc;
            dblIvaDoc = 0;

            /*AGREGADO PARA COMPENSACION SOLIDARIA*/
            txtCompSol.setText("0.00");
            dblComSol=0;
            txtSubTotIva.setText("0.00");
            txtSubTotIvaCero.setText("" + dblSubtotalDoc);
            /*AGREGADO PARA COMPENSACION SOLIDARIA*/            
        } else {
            txtSub.setText("" + dblSubtotalDoc);
            txtIva.setText("" + dblIvaDoc);
            txtTot.setText("" + dblTotalDoc);
            /*AGREGADO PARA COMPENSACION SOLIDARIA*/
            txtCompSol.setText(""+dblComSol);
            txtSubTotIva.setText(""+dblSubTotIva);
            txtSubTotIvaCero.setText(""+dblSubTotIvaCero);
            /*AGREGADO PARA COMPENSACION SOLIDARIA*/
        }
        /////////////////////////////////////////////////////////////
    }
    
    
    public void calculaTotalBig() {
        BigDecimal bigSub = BigDecimal.ZERO, bigIva = BigDecimal.ZERO, bigSubtotalDoc = BigDecimal.ZERO, bigTmp = BigDecimal.ZERO, bigSub2 = BigDecimal.ZERO, bigIvaDoc = BigDecimal.ZERO, bigTotalDoc = BigDecimal.ZERO, bigComSol=BigDecimal.ZERO;
        int intNumDec = 2;
        //double dblPorCompSol=2d;
        BigDecimal bigSubTotIva=BigDecimal.ZERO, bigSubTotIvaCero=BigDecimal.ZERO;
        
        for (int i = 0; i < tblDat.getRowCount(); i++) {
            if (tblDat.getValueAt(i, INT_TBL_CODITM) != null) {
                if (tblDat.getValueAt(i, INT_TBL_CANDEV) != null) {

                    bigSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL) == null || tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("")) ? BigDecimal.ZERO : new BigDecimal(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
                    bigSub = bigSub.add(bigSub2.setScale(2, RoundingMode.HALF_UP));
                    if (tblDat.getValueAt(i, INT_TBL_BLNIVA) == null) {
                        bigIva = bigIva.add(BigDecimal.ZERO);
                    } else {
                        if(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true")){
                            bigTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true")) ? ((tblDat.getValueAt(i, INT_TBL_TOTAL) == null || tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("")) ? BigDecimal.ZERO : new BigDecimal(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) : BigDecimal.ZERO);
                            //dblIva = dblIva + (((dblTmp * dblPorIva) == 0) ? 0 : (dblTmp * dblPorIva) / 100);
                            bigIva = bigIva.add(((bigTmp.multiply(new BigDecimal(dblPorIva))).compareTo(BigDecimal.ZERO)==0)?BigDecimal.ZERO:bigTmp.multiply((new BigDecimal(dblPorIva)).divide(BigDecimal.valueOf(100))));
                            bigSubTotIva= (bigSubTotIva.add(bigTmp)).setScale(2, RoundingMode.HALF_UP);
                            
                            //dblSubTotIva=objUti.redondear(dblSubTotIva, intNumDec);
                            if(objZafParSis.getCodigoEmpresa()==2 && objZafParSis.getCodigoLocal()==4){
                                if(dblCompSol==0){
                                    //dblComSol=dblComSol + 0;
                                    bigComSol=bigComSol.add(BigDecimal.ZERO);
                                    /*AGREGADO PARA PONER EL PORCENTAJE DE COMPENSACION CORRECTO*/
                                    bigPorComSolVen=BigDecimal.ZERO;
                                    /*AGREGADO PARA PONER EL PORCENTAJE DE COMPENSACION CORRECTO*/
                                }else{
                                    Calendar calFecha=Calendar.getInstance();

                                    //BigDecimal bigPorIvaVenNoMan=objZafParSis.getPorcentajeIvaVentas(1, 4, calFecha.getTime());
                                    //bigPorComSolVen=objZafParSis.getPorcentajeCmpSolVentas(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), calFecha.getTime());
                                    bigPorComSolVen=new BigDecimal(dblPorComSol);

                                    bigPorComSolVen=bigPorComSolVen!=null?bigPorComSolVen:new BigDecimal(0);


                                    //dblComSol =  dblComSol + (((dblTmp * bigPorIvaVenNoMan.doubleValue()) == 0) ? 0 : (dblTmp * bigPorIvaVenNoMan.doubleValue()) / 100) - (((dblTmp * (bigPorIvaVenNoMan.doubleValue()-2) ) == 0) ? 0 : (dblTmp * (bigPorIvaVenNoMan.doubleValue()-2)) / 100);

                                    //dblComSol = dblComSol + (((dblTmp * bigPorComSolVen.doubleValue()) == 0) ? 0 : (dblTmp * bigPorComSolVen.doubleValue()) / 100);
                                    bigComSol = bigComSol.add(((bigTmp.multiply(bigPorComSolVen)).compareTo(BigDecimal.ZERO))==0?BigDecimal.ZERO:(bigTmp.multiply(bigPorComSolVen.divide(BigDecimal.valueOf(100)))));
                                }
                            }else{
                                //dblComSol = dblComSol+0;
                                bigComSol=bigComSol.add(BigDecimal.ZERO);
                            }
                        }else{
                            //dblSubTotIvaCero+=dblSub2;
                            bigSubTotIvaCero=bigSubTotIvaCero.add(bigSub2);
                            //dblSubTotIvaCero=objUti.redondear(dblSubTotIvaCero, intNumDec);
                            bigSubTotIvaCero=bigSubTotIvaCero.setScale(2, RoundingMode.HALF_UP);
                            //dblComSol=dblComSol+0;
                            bigComSol=bigComSol.add(BigDecimal.ZERO);
                        }
                    }
                }
            }
        }
        /////////////////////////////////////////////////////////////
        //dblSubtotalDoc = dblSub;
        bigSubtotalDoc=bigSub;
        //dblIvaDoc = objUti.redondear(dblIva, intNumDec);
        bigIvaDoc=bigIva.setScale(2, RoundingMode.HALF_UP);
        
        /*AGREGADO PARA COMPENSACION SOLIDARIA*/
        //dblComSol =objUti.redondear(dblComSol, 2);
        bigComSol=bigComSol.setScale(2, RoundingMode.HALF_UP);
        /*AGREGADO PARA COMPENSACION SOLIDARIA*/
        
        //dblTotalDoc = dblSubtotalDoc + dblIvaDoc;
        //dblTotalDoc = (dblSubtotalDoc + dblIvaDoc)- dblComSol;
        bigTotalDoc=(bigSubtotalDoc.add(bigIvaDoc)).subtract(bigComSol);
        //dblTotalDoc = objUti.redondear(dblTotalDoc, intNumDec);
        bigTotalDoc=bigTotalDoc.setScale(2, RoundingMode.HALF_UP);
        //dblSubtotalDoc = objUti.redondear(dblSubtotalDoc, intNumDec);
        bigSubtotalDoc=bigSubtotalDoc.setScale(2, RoundingMode.HALF_UP);
        
        //System.out.println(" Iva:  "+ stIvaVen + " - "+ dblPorIva +" - "+ dblIvaDoc );

        if (stIvaVen.equals("N")) {
            //txtSub.setText("" + dblSubtotalDoc);
            txtSub.setText("" + bigSubtotalDoc);
            txtIva.setText("0.00");
            //txtTot.setText("" + dblSubtotalDoc);
            txtTot.setText("" + bigSubtotalDoc);
            //dblTotalDoc = dblSubtotalDoc;
            bigTotalDoc=bigSubtotalDoc;
            //dblIvaDoc = 0;
            bigIvaDoc=BigDecimal.ZERO;

            /*AGREGADO PARA COMPENSACION SOLIDARIA*/
            txtCompSol.setText("0.00");
            //dblComSol=0;
            bigComSol=BigDecimal.ZERO;
            txtSubTotIva.setText("0.00");
            //txtSubTotIvaCero.setText("" + dblSubtotalDoc);
            txtSubTotIvaCero.setText("" + bigSubtotalDoc);
            /*AGREGADO PARA COMPENSACION SOLIDARIA*/            
        } else {
            //txtSub.setText("" + dblSubtotalDoc);
            txtSub.setText("" + bigSubtotalDoc);
            //txtIva.setText("" + dblIvaDoc);
            txtIva.setText("" + bigIvaDoc);
            //txtTot.setText("" + dblTotalDoc);
            txtTot.setText("" + bigTotalDoc);
            /*AGREGADO PARA COMPENSACION SOLIDARIA*/
            //txtCompSol.setText(""+dblComSol);
            txtCompSol.setText(""+bigComSol);
            //txtSubTotIva.setText(""+dblSubTotIva);
            txtSubTotIva.setText(""+bigSubTotIva);
            //txtSubTotIvaCero.setText(""+dblSubTotIvaCero);
            txtSubTotIvaCero.setText(""+bigSubTotIvaCero);
            /*AGREGADO PARA COMPENSACION SOLIDARIA*/
        }
        /////////////////////////////////////////////////////////////
    }    

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);

        } else {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }

    public void Configura_ventana_consulta() {
        boolean configurarVenConTipDoc = configurarVenConTipDoc();
        configurarVenConVendedor();
        configurarVenConClientes();
        configurarVenConDirCli();
        configurarVenConMotivo();

        Configurartabla();

        if (intEstCon == 1) {
            MostrarColChk(INT_TBL_REVTEC);

            objTooBar.setEstado('m');
            objTooBar.setVisibleAceptar(true);

            objTooBar._consultar(intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol);
            //objTooBar.cargarTipoDocSol(intCodTipDocSol, intCodLocSol);

            javax.swing.JLabel objlbl = new javax.swing.JLabel();
            objlbl.setFont(new java.awt.Font("SansSerif Bold", 1, 16));
            objlbl.setForeground(new java.awt.Color(255, 0, 0));
            objlbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            if (_getVerMerStk(intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol)) {
                objlbl.setText("HAY MERCADERIA QUE ESTA SOLICITANDO QUEDAR EN STOCK  ");
                this.setGlassPane(objlbl);
                objlbl.setVisible(true);
                objlbl = null;
            }
            objlbl = null;

        }
        if (intEstCon == 2) {
            intEstCon = 1;
            // objTooBar.setEstado('m');
            // objTooBar.setVisibleAceptar(true);

            objTooBar._consultar(intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol);
            //objTooBar.cargarTipoDocSol(intCodTipDocSol, intCodLocSol);

        }

        if (blnCarSol) {
            objTooBar._consultar(intCodEmpSol, intCodLocSol, intCodTipDocSol, intCodDocSol);
        }


    }

    /**
     *
     * @param conn c
     * @return
     *
     * verifica si hay mercaderia que no se volvera a facturar y si queda en
     * stock esto esta orientado para terminales L
     *
     */
    private boolean _getVerMerStk(int intCodEmpSol, int intCodLocSol, int intCodTipDocSol, int intCodDocSol) {
        boolean blnRes = false;
        String strSql = "";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            abrirCon();
            if (CONN_GLO != null) {
                stmLoc = CONN_GLO.createStatement();

                strSql = "select a.co_doc from tbm_cabsoldevven as a "
                        + " inner join tbm_detsoldevven as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                        + " where a.co_emp=" + intCodEmpSol + " and a.co_loc=" + intCodLocSol + " and a.co_tipdoc=" + intCodTipDocSol + " and a.co_doc=" + intCodDocSol + " "
                        + " and a1.nd_cansolnodevprv > 0  ";

                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    blnRes = true;
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                CerrarCon();

            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean configurarVenConTipDoc() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql = "";
            Str_Sql = "Select a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b "
                    + " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                    + " where   b.co_emp=" + objZafParSis.getCodigoEmpresa() + " and "
                    + " b.co_loc = " + objZafParSis.getCodigoLocal() + " and "
                    + " b.co_mnu = " + objZafParSis.getCodigoMenu();

            objVenConTipdoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConVendedor() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = "select a.co_usr, a.tx_nom  from tbr_usremp as b"
                    + " inner join tbm_usr as a on (a.co_usr=b.co_usr) "
                    + " where b.co_emp=" + objZafParSis.getCodigoEmpresa() + " and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

            objVenConVen = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Motivos".
     *
     */
    private boolean configurarVenConMotivo() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_motDev");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Motivo");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");

            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "SELECT a1.co_motDev, a1.tx_desLar ";
            strSQL += "FROM tbm_motDevInv AS a1 ";
            strSQL += "WHERE a1.co_emp = " + objZafParSis.getCodigoEmpresa() + " ";
            strSQL += "AND a1.st_reg = 'A' order by a1.tx_desLar";

            //System.out.println("ZafVen11.configurarVenConMotivo: " + strSQL);

            objVenConMot = new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Motivos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpRdaBut = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panGenSol = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTipDev = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtDesLarTipDocSol = new javax.swing.JTextField();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtCodDoc = new javax.swing.JTextField();
        txtDesSol = new javax.swing.JTextField();
        butSol = new javax.swing.JButton();
        txtCodSol = new javax.swing.JTextField();
        radCan = new javax.swing.JRadioButton();
        radDes = new javax.swing.JRadioButton();
        radPre = new javax.swing.JRadioButton();
        radDesEsp = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        chkVolFac = new javax.swing.JCheckBox();
        lblPrv = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtDirCliVolFac = new javax.swing.JTextField();
        txtCodCliVolFac = new javax.swing.JTextField();
        txtNomCliVolFac = new javax.swing.JTextField();
        butCliCon = new javax.swing.JButton();
        butDirCliCon = new javax.swing.JButton();
        lblMotDev = new javax.swing.JLabel();
        txtCodMot = new javax.swing.JTextField();
        txtDesLarMot = new javax.swing.JTextField();
        butMot = new javax.swing.JButton();
		/*agregado por proyecto de reservas CMATEO*/
        chkcanresdev = new javax.swing.JCheckBox();
		/*agregado por proyecto de reservas CMATEO*/
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panDatTabGen = new javax.swing.JPanel();
        panTabGenNor = new javax.swing.JPanel();
        panTabGenNorCab = new javax.swing.JPanel();
        lblLoc = new javax.swing.JLabel();
        lblTipDocFac = new javax.swing.JLabel();
        lblCli = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtDesLoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDocFac = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        butBusFac = new javax.swing.JButton();
        lblCodDoc1 = new javax.swing.JLabel();
        txtCodDocFac = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        txtFecDocFac = new javax.swing.JTextField();
        panTabGenCen = new javax.swing.JPanel();
        scrollTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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
        lblSubTot = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblCompSol = new javax.swing.JLabel();
        txtCompSol = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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
        getContentPane().setLayout(new java.awt.BorderLayout());

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panGenSol.setPreferredSize(new java.awt.Dimension(100, 230));
        panGenSol.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setText("Solicitante:"); // NOI18N
        panGenSol.add(jLabel2);
        jLabel2.setBounds(10, 30, 90, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Tipo de documento:"); // NOI18N
        panGenSol.add(jLabel3);
        jLabel3.setBounds(10, 10, 110, 20);

        lblTipDev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDev.setText("Tipo de devolucion:"); // NOI18N
        panGenSol.add(lblTipDev);
        lblTipDev.setBounds(10, 80, 110, 20);
        lblTipDev.getAccessibleContext().setAccessibleName("Tipo de devolución:");

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc.setText("Código del documento:"); // NOI18N
        panGenSol.add(lblCodDoc);
        lblCodDoc.setBounds(10, 50, 120, 20);

        txtDesLarTipDocSol.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarTipDocSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocSolActionPerformed(evt);
            }
        });
        txtDesLarTipDocSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocSolFocusLost(evt);
            }
        });
        panGenSol.add(txtDesLarTipDocSol);
        txtDesLarTipDocSol.setBounds(190, 10, 230, 20);

        txtDesCodTitpDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCodTitpDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCodTitpDocActionPerformed(evt);
            }
        });
        txtDesCodTitpDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusLost(evt);
            }
        });
        panGenSol.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(120, 10, 70, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenSol.add(butTipDoc);
        butTipDoc.setBounds(420, 10, 20, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        panGenSol.add(txtCodDoc);
        txtCodDoc.setBounds(120, 50, 90, 20);

        txtDesSol.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesSolActionPerformed(evt);
            }
        });
        txtDesSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesSolFocusLost(evt);
            }
        });
        panGenSol.add(txtDesSol);
        txtDesSol.setBounds(190, 30, 230, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panGenSol.add(butSol);
        butSol.setBounds(420, 30, 20, 20);

        txtCodSol.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSolActionPerformed(evt);
            }
        });
        txtCodSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSolFocusLost(evt);
            }
        });
        panGenSol.add(txtCodSol);
        txtCodSol.setBounds(120, 30, 70, 20);

        grpRdaBut.add(radCan);
        radCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radCan.setSelected(true);
        radCan.setText("Por Item."); // NOI18N
        radCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCanActionPerformed(evt);
            }
        });
        panGenSol.add(radCan);
        radCan.setBounds(120, 80, 80, 23);

        grpRdaBut.add(radDes);
        radDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radDes.setText("Por Descuento."); // NOI18N
        radDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radDesActionPerformed(evt);
            }
        });
        panGenSol.add(radDes);
        radDes.setBounds(210, 80, 100, 23);

        grpRdaBut.add(radPre);
        radPre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPre.setText("Por Precio."); // NOI18N
        radPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPreActionPerformed(evt);
            }
        });
        panGenSol.add(radPre);
        radPre.setBounds(340, 80, 90, 23);

        grpRdaBut.add(radDesEsp);
        radDesEsp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radDesEsp.setText("Por Descuento Especial."); // NOI18N
        radDesEsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radDesEspActionPerformed(evt);
            }
        });
        panGenSol.add(radDesEsp);
        radDesEsp.setBounds(450, 80, 160, 23);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Fecha del documento:"); // NOI18N
        panGenSol.add(jLabel6);
        jLabel6.setBounds(460, 10, 120, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        panGenSol.add(txtNumDoc);
        txtNumDoc.setBounds(580, 30, 90, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Número de documento:"); // NOI18N
        panGenSol.add(lblNumDoc);
        lblNumDoc.setBounds(460, 30, 120, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        chkVolFac.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        chkVolFac.setText("Volver a facturar la mercadería que no se va a devolver");
        chkVolFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVolFacActionPerformed(evt);
            }
        });
        jPanel1.add(chkVolFac);
        chkVolFac.setBounds(10, 10, 460, 20);

        lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPrv.setText("Cliente::"); // NOI18N
        jPanel1.add(lblPrv);
        lblPrv.setBounds(40, 30, 50, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDir.setText("Dirección:"); // NOI18N
        jPanel1.add(lblDir);
        lblDir.setBounds(40, 50, 60, 20);

        txtDirCliVolFac.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDirCliVolFac.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDirCliVolFac.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDirCliVolFacCaretUpdate(evt);
            }
        });
        txtDirCliVolFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirCliVolFacActionPerformed(evt);
            }
        });
        txtDirCliVolFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDirCliVolFacFocusGained(evt);
            }
        });
        jPanel1.add(txtDirCliVolFac);
        txtDirCliVolFac.setBounds(90, 50, 350, 20);

        txtCodCliVolFac.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodCliVolFac.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodCliVolFac.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodCliVolFac.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCodCliVolFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliVolFacActionPerformed(evt);
            }
        });
        txtCodCliVolFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliVolFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliVolFacFocusLost(evt);
            }
        });
        jPanel1.add(txtCodCliVolFac);
        txtCodCliVolFac.setBounds(90, 30, 50, 20);

        txtNomCliVolFac.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomCliVolFac.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNomCliVolFac.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNomCliVolFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliVolFacActionPerformed(evt);
            }
        });
        txtNomCliVolFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliVolFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliVolFacFocusLost(evt);
            }
        });
        txtNomCliVolFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliVolFacActionPerformed(evt);
            }
        });
        txtNomCliVolFac.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtNomCliVolFacVetoableChange(evt);
            }
        });
        jPanel1.add(txtNomCliVolFac);
        txtNomCliVolFac.setBounds(140, 30, 300, 20);

        butCliCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCliCon.setText("..."); // NOI18N
        butCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butCliCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliConActionPerformed(evt);
            }
        });
        jPanel1.add(butCliCon);
        butCliCon.setBounds(440, 30, 22, 20);

        butDirCliCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butDirCliCon.setText("..."); // NOI18N
        butDirCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
        butDirCliCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDirCliConActionPerformed(evt);
            }
        });
        jPanel1.add(butDirCliCon);
        butDirCliCon.setBounds(440, 50, 22, 20);

        panGenSol.add(jPanel1);
        jPanel1.setBounds(10, 140, 520, 80);

        lblMotDev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMotDev.setText("Motivo de devolución:");
        panGenSol.add(lblMotDev);
        lblMotDev.setBounds(10, 110, 110, 20);

        txtCodMot.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panGenSol.add(txtCodMot);
        txtCodMot.setBounds(120, 110, 70, 20);

        txtDesLarMot.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panGenSol.add(txtDesLarMot);
        txtDesLarMot.setBounds(190, 110, 230, 20);

        butMot.setText("...");
        butMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMotActionPerformed(evt);
            }
        });
        panGenSol.add(butMot);
        butMot.setBounds(420, 110, 20, 20);
		/*agregado por proyecto de reservas CMATEO*/
        chkcanresdev.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkcanresdev.setText("Cantidad reservada a devolver");
        panGenSol.add(chkcanresdev);
        chkcanresdev.setBounds(460, 55, 210, 23);
		/*agregado por proyecto de reservas CMATEO*/
        panGenTabGen.add(panGenSol, java.awt.BorderLayout.NORTH);

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

        panGenTabGen.add(panObs, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panGenTabGen);

        panDatTabGen.setLayout(new java.awt.BorderLayout());

        panTabGenNor.setPreferredSize(new java.awt.Dimension(100, 100));
        panTabGenNor.setLayout(new java.awt.BorderLayout());

        panTabGenNorCab.setPreferredSize(new java.awt.Dimension(100, 20));
        panTabGenNorCab.setLayout(null);

        lblLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblLoc.setText("Local:"); // NOI18N
        panTabGenNorCab.add(lblLoc);
        lblLoc.setBounds(10, 10, 110, 20);

        lblTipDocFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDocFac.setText("Tipo de Documento:"); // NOI18N
        panTabGenNorCab.add(lblTipDocFac);
        lblTipDocFac.setBounds(10, 30, 110, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente:"); // NOI18N
        panTabGenNorCab.add(lblCli);
        lblCli.setBounds(10, 50, 100, 20);
        panTabGenNorCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(120, 30, 60, 20);
        panTabGenNorCab.add(txtCodLoc);
        txtCodLoc.setBounds(120, 10, 60, 20);
        panTabGenNorCab.add(txtDesLoc);
        txtDesLoc.setBounds(180, 10, 270, 20);
        panTabGenNorCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(180, 30, 270, 20);

        lblNumDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc1.setText("Número de documento:"); // NOI18N
        panTabGenNorCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(460, 30, 120, 20);

        txtNumDocFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocFacActionPerformed(evt);
            }
        });
        txtNumDocFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumDocFacKeyPressed(evt);
            }
        });
        panTabGenNorCab.add(txtNumDocFac);
        txtNumDocFac.setBounds(580, 30, 80, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Fecha del documento:"); // NOI18N
        panTabGenNorCab.add(jLabel7);
        jLabel7.setBounds(460, 10, 120, 20);

        butBusFac.setText(".."); // NOI18N
        butBusFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusFacActionPerformed(evt);
            }
        });
        panTabGenNorCab.add(butBusFac);
        butBusFac.setBounds(660, 30, 20, 20);

        lblCodDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc1.setText("Código del documento:"); // NOI18N
        panTabGenNorCab.add(lblCodDoc1);
        lblCodDoc1.setBounds(10, 70, 120, 20);
        panTabGenNorCab.add(txtCodDocFac);
        txtCodDocFac.setBounds(120, 70, 90, 20);
        panTabGenNorCab.add(txtCodCli);
        txtCodCli.setBounds(120, 50, 60, 20);
        panTabGenNorCab.add(txtNomCli);
        txtNomCli.setBounds(180, 50, 270, 20);
        panTabGenNorCab.add(txtFecDocFac);
        txtFecDocFac.setBounds(580, 10, 80, 20);

        panTabGenNor.add(panTabGenNorCab, java.awt.BorderLayout.CENTER);

        panDatTabGen.add(panTabGenNor, java.awt.BorderLayout.NORTH);

        panTabGenCen.setLayout(new java.awt.BorderLayout());

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
        scrollTbl.setViewportView(tblDat);

        panTabGenCen.add(scrollTbl, java.awt.BorderLayout.CENTER);

        panDatTabGen.add(panTabGenCen, java.awt.BorderLayout.CENTER);

        panCotGenSur.setLayout(new java.awt.BorderLayout());

        panCotGenSurCen.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs3.setText("Observación 1:"); // NOI18N
        jPanel4.add(lblObs3, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs4.setText("Observación 2:"); // NOI18N
        jPanel3.add(lblObs4, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCotGenSurEst.setLayout(new java.awt.GridLayout(4, 2));

        lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblSubTot.setText("SubTotal:"); // NOI18N
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenSurEst.add(lblSubTot);

        txtSub.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblIva.setText("IVA 12%:"); // NOI18N
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);

        txtIva.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtIva);

        lblCompSol.setText("Comp. Sol. ");
        panCotGenSurEst.add(lblCompSol);

        txtCompSol.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtCompSol);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTot.setText("Total:"); // NOI18N
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panDatTabGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("Datos del documento a devolver", panDatTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 435);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
	// TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
	// TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
           // cerrarObj();
            //System.gc();
            Runtime.getRuntime().gc();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	// TODO add your handling code here:
	Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butBusFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusFacActionPerformed
        // TODO add your handling code here:

        cargarVenBusFac();

}//GEN-LAST:event_butBusFacActionPerformed

    private void cargarVenBusFac() {
        ZafVen11_01 obj = new ZafVen11_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        obj.show();
        if (obj.acepta()) {
            txtNumDocFac.setText(obj.GetCamSel(4));
            if (!cargarFac(txtNumDocFac.getText())) {
                cargarVenBusFac();
            }
        }
        obj.dispose();
        obj = null;
    }

    private void txtNumDocFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocFacKeyPressed
        // TODO add your handling code here:
        if (KeyEvent.VK_ENTER == evt.getKeyCode()) {
            if (!cargarFac(txtNumDocFac.getText())) {
                cargarVenBusFac();
            }
        }
}//GEN-LAST:event_txtNumDocFacKeyPressed

    private void butSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolActionPerformed
        // TODO add your handling code here:
        objVenConVen.setTitle("Listado de Solicitantes");
        objVenConVen.setCampoBusqueda(1);
        objVenConVen.show();
        if (objVenConVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtCodSol.setText(objVenConVen.getValueAt(1));
            txtDesSol.setText(objVenConVen.getValueAt(2));
        }
    }//GEN-LAST:event_butSolActionPerformed

    private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
        // TODO add your handling code here:
        txtDesCodTitpDoc.transferFocus();
    }//GEN-LAST:event_txtDesCodTitpDocActionPerformed

    private void txtDesLarTipDocSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocSolActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDocSol.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocSolActionPerformed

    private void txtCodSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSolActionPerformed
        txtCodSol.transferFocus();
    }//GEN-LAST:event_txtCodSolActionPerformed

    private void txtDesSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesSolActionPerformed
        // TODO add your handling code here:
        txtDesSol.transferFocus();
    }//GEN-LAST:event_txtDesSolActionPerformed

    private void txtCodSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSolFocusGained
        strCodSol = txtCodSol.getText();
        txtCodSol.selectAll();
    }//GEN-LAST:event_txtCodSolFocusGained

    private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
        // TODO add your handling code here:
        strDesCodTitpDoc = txtDesCodTitpDoc.getText();
        txtDesCodTitpDoc.selectAll();
    }//GEN-LAST:event_txtDesCodTitpDocFocusGained

    private void txtDesLarTipDocSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocSolFocusGained
        // TODO add your handling code here:
        strDesLarTipDocSol = txtDesLarTipDocSol.getText();
        txtDesLarTipDocSol.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocSolFocusGained

    private void txtDesSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesSolFocusGained
        // TODO add your handling code here:
        strDesSol = txtDesSol.getText();
        txtDesSol.selectAll();
    }//GEN-LAST:event_txtDesSolFocusGained

    private void txtDesSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesSolFocusLost
        if (!txtDesSol.getText().equalsIgnoreCase(strDesSol)) {
            if (txtDesSol.getText().equals("")) {
                txtCodSol.setText("");
                txtDesSol.setText("");
            } else {
                BuscarVendedor("a.tx_nom", txtDesSol.getText(), 1);
            }
        } else {
            txtDesSol.setText(strDesSol);
        }
    }//GEN-LAST:event_txtDesSolFocusLost

    private void txtCodSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSolFocusLost
        if (!txtCodSol.getText().equalsIgnoreCase(strCodSol)) {
            if (txtCodSol.getText().equals("")) {
                txtCodSol.setText("");
                txtDesSol.setText("");
            } else {
                BuscarVendedor("a.co_usr", txtCodSol.getText(), 0);
            }
        } else {
            txtCodSol.setText(strCodSol);
        }
    }//GEN-LAST:event_txtCodSolFocusLost

    private void txtDesLarTipDocSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocSolFocusLost
        if (!txtDesLarTipDocSol.getText().equalsIgnoreCase(strDesLarTipDocSol)) {
            if (txtDesLarTipDocSol.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDocSol.setText("");
            } else {
                BuscarTipDoc("a.tx_deslar", txtDesLarTipDocSol.getText(), 2);
            }
        } else {
            txtDesLarTipDocSol.setText(strDesLarTipDocSol);
        }
    }//GEN-LAST:event_txtDesLarTipDocSolFocusLost

    private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
        if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCodTitpDoc)) {
            if (txtDesCodTitpDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDocSol.setText("");
            } else {
                BuscarTipDoc("a.tx_descor", txtDesCodTitpDoc.getText(), 1);
            }
        } else {
            txtDesCodTitpDoc.setText(strDesCodTitpDoc);
        }
    }//GEN-LAST:event_txtDesCodTitpDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDocSol.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc = objVenConTipdoc.getValueAt(1);
        }
    }//GEN-LAST:event_butTipDocActionPerformed

    private void radCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCanActionPerformed
        if (intEstDev1 == 1) {
            ocultaCol(INT_TBL_DESDEV);
            ocultaCol(INT_TBL_PREDEV);
            MostrarCol(INT_TBL_CANDEV);
            MostrarCol(INT_TBL_SEDEVPRV);
            MostrarCol(INT_TBL_NODEVPRV);
            MostrarCol(INT_TBL_CANVOLFAC);
            MostrarCol(INT_TBL_CAN_SOL_NODEPRV);

            chkVolFac.setEnabled(true);

            LimpiarDatos(0);
            intEstDev1 = 0;
            intEstDev2 = 1;
            intEstDev3 = 1;
            intEstDev4 = 1;
        }
    }//GEN-LAST:event_radCanActionPerformed

    private void radDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radDesActionPerformed
        if (intEstDev2 == 1) {
            ocultaCol(INT_TBL_CANDEV);
            ocultaCol(INT_TBL_PREDEV);
            ocultaCol(INT_TBL_SEDEVPRV);
            ocultaCol(INT_TBL_NODEVPRV);
            ocultaCol(INT_TBL_CANVOLFAC);
            ocultaCol(INT_TBL_CAN_SOL_NODEPRV);
            MostrarCol(INT_TBL_DESDEV);

            desactivarVolFac();

            LimpiarDatos(0);

            tblDat.getColumnModel().getColumn(INT_TBL_DESDEV).setCellEditor(objTblCelEdiTxt);

            intEstDev1 = 1;
            intEstDev2 = 0;
            intEstDev3 = 1;
            intEstDev4 = 1;
        }
}//GEN-LAST:event_radDesActionPerformed

    private void desactivarVolFac() {

        chkVolFac.setSelected(false);

        for (int i = 0; i < tblDat.getRowCount(); i++) {
            tblDat.setValueAt(null, i, INT_TBL_CANVOLFAC);
        }

        chkVolFac.setEnabled(false);
        txtCodCliVolFac.setText("");
        txtNomCliVolFac.setText("");
        strCodCliVolFac = "";
        strNomCliVolFac = "";
        txtCodCliVolFac.setEditable(false);
        txtNomCliVolFac.setEditable(false);
        butCliCon.setEnabled(false);

        java.awt.Color colBack;
        colBack = txtDirCliVolFac.getBackground();
        txtDirCliVolFac.setEditable(false);
        txtDirCliVolFac.setBackground(colBack);
        txtDirCliVolFac.setText("");
        butDirCliCon.setEnabled(false);
    }

    private void radPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPreActionPerformed
        if (intEstDev3 == 1) {
            ocultaCol(INT_TBL_CANDEV);
            ocultaCol(INT_TBL_DESDEV);
            ocultaCol(INT_TBL_SEDEVPRV);
            ocultaCol(INT_TBL_NODEVPRV);
            ocultaCol(INT_TBL_CANVOLFAC);
            ocultaCol(INT_TBL_CAN_SOL_NODEPRV);
            MostrarCol(INT_TBL_PREDEV);

            desactivarVolFac();

            LimpiarDatos(0);
            intEstDev1 = 1;
            intEstDev2 = 1;
            intEstDev3 = 0;
            intEstDev4 = 1;
        }
    }//GEN-LAST:event_radPreActionPerformed

    private void txtNumDocFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocFacActionPerformed

    private void chkVolFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVolFacActionPerformed
        // <editor-fold defaultstate="collapsed" desc=" /* Rose: Habilitar Opción Volver a Facturar */ ">
        if (chkVolFac.isSelected()) 
        {
            txtCodCliVolFac.setText("");
            txtNomCliVolFac.setText("");
            txtDirCliVolFac.setText("");
            txtCodCliVolFac.setEditable(true);
            txtNomCliVolFac.setEditable(true);
            butCliCon.setEnabled(true);
            txtDirCliVolFac.setEditable(true);
            butDirCliCon.setEnabled(true);
            configurarVenConClientes();
            volverFacturar();
            
            /*AGREGADO PARA PROBAR 15-MAR-2016*/
            if (radCan.isSelected()) {
                for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {
                    //dblCan += objUti.redondear(((tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV) == null) ? "0" : (tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV).toString().equals("") ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV).toString())), 4);

                    String strTipTer = ((tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV).toString());
                    String strDev = (tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString());  //Cant a devolver
                    double dblCanDev = objUti.redondear((strDev.equals("") ? "0" : strDev), 4);            
                    if ((strTipTer.trim().equals("S"))) {
                        if (chkVolFac.isSelected()) {
                            tblDat.setValueAt("0", intCelSel, INT_TBL_SEDEVPRV);
                            tblDat.setValueAt("0", intCelSel, INT_TBL_CAN_SOL_NODEPRV);
                            tblDat.setValueAt("" + dblCanDev, intCelSel, INT_TBL_NODEVPRV);
                        } 
                    } 

                }
            }            
            /*AGREGADO PARA PROBAR 15-MAR-2016*/
        } 
        else if (!chkVolFac.isSelected()) 
        {
            chkVolFac.setSelected(false);
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                tblDat.setValueAt(null, i, INT_TBL_CANVOLFAC);
            }
            txtCodCliVolFac.setText("");
            txtNomCliVolFac.setText("");
            txtDirCliVolFac.setText("");
            strCodCliVolFac = "";
            strNomCliVolFac = "";
            strDirCliVolFac = "";
            txtCodCliVolFac.setEditable(false);
            txtNomCliVolFac.setEditable(false);
            txtDirCliVolFac.setEditable(false);
            butCliCon.setEnabled(false);
            butDirCliCon.setEnabled(false);
          
            java.awt.Color colBack;
            colBack = txtDirCliVolFac.getBackground();
            txtDirCliVolFac.setBackground(colBack);
            txtCodCliVolFac.setBackground(colBack);
            txtNomCliVolFac.setBackground(colBack);
            
            
            
            /*AGREGADO PARA PROBAR 15-MAR-2016*/
            if (radCan.isSelected()){
                for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) {
                    //dblCan += objUti.redondear(((tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV) == null) ? "0" : (tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV).toString().equals("") ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV).toString())), 4);

                    String strTipTer = ((tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intCelSel, INT_TBL_DEVPRV).toString());
                    String strDev = (tblDat.getValueAt(intCelSel, INT_TBL_CANDEV) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_CANDEV).toString());  //Cant a devolver
                    double dblCanDev = objUti.redondear((strDev.equals("") ? "0" : strDev), 4);            
                    if ((strTipTer.trim().equals("S"))) {                        
                        tblDat.setValueAt("" + dblCanDev, intCelSel, INT_TBL_SEDEVPRV);                        
                        tblDat.setValueAt("0", intCelSel, INT_TBL_NODEVPRV);                        
                    }                 
                }
            }
            /*AGREGADO PARA PROBAR 15-MAR-2016*/            
        }
        
        //</editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" /* José Marín: Quitar Opción Volver a Facturar */ ">
//        boolean blnNoUsar = false;
//        if (chkVolFac.isSelected()) {
//            MensajeInf("Admin: No se puede realizar volver a facturar..");
//            chkVolFac.setSelected(false);
//        }
//
//        if (blnNoUsar) {
//            if (!chkVolFac.isSelected()) {
//                for (int i = 0; i < tblDat.getRowCount(); i++) {
//                    tblDat.setValueAt(null, i, INT_TBL_CANVOLFAC);
//                }
//                txtCodCliVolFac.setText("");
//                txtNomCliVolFac.setText("");
//                txtDirCliVolFac.setText("");
//                strCodCliVolFac = "";
//                strNomCliVolFac = "";
//                txtCodCliVolFac.setEditable(false);
//                txtNomCliVolFac.setEditable(false);
//                butCliCon.setEnabled(false);
//                java.awt.Color colBack;
//                colBack = txtDirCliVolFac.getBackground();
//                txtDirCliVolFac.setEditable(false);
//                txtDirCliVolFac.setBackground(colBack);
//                butDirCliCon.setEnabled(false);
//            } else {
//                txtCodCliVolFac.setEditable(true);
//                txtNomCliVolFac.setEditable(true);
//                butCliCon.setEnabled(true);
//
//                txtDirCliVolFac.setEditable(true);
//                butDirCliCon.setEnabled(true);
//            }
//        }
        //</editor-fold>
    }//GEN-LAST:event_chkVolFacActionPerformed

    private void txtCodCliVolFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliVolFacActionPerformed
        txtCodCliVolFac.transferFocus();
    }//GEN-LAST:event_txtCodCliVolFacActionPerformed

    private void txtCodCliVolFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliVolFacFocusGained
        strCodCliVolFac = txtCodCliVolFac.getText();
        txtCodCliVolFac.selectAll();
}//GEN-LAST:event_txtCodCliVolFacFocusGained

    private void txtCodCliVolFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliVolFacFocusLost
       if (!txtCodCliVolFac.getText().equalsIgnoreCase(strCodCliVolFac)) {
            if (txtCodCliVolFac.getText().equals("")) {
                txtCodCliVolFac.setText("");
                txtNomCliVolFac.setText("");
                txtDirCliVolFac.setText("");
                stIvaVen = "S";
            } else {
                BuscarCliente("a.co_cli", txtCodCliVolFac.getText(), 0);
            }
        } else {
            txtCodCliVolFac.setText(strCodCliVolFac);
        }
    }//GEN-LAST:event_txtCodCliVolFacFocusLost

    private void txtNomCliVolFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliVolFacActionPerformed
        txtNomCliVolFac.transferFocus();
    }//GEN-LAST:event_txtNomCliVolFacActionPerformed

    private void txtNomCliVolFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliVolFacFocusGained
        strNomCliVolFac = txtNomCliVolFac.getText();
        txtNomCliVolFac.selectAll();
}//GEN-LAST:event_txtNomCliVolFacFocusGained

    private void txtNomCliVolFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliVolFacFocusLost
        if (!txtNomCliVolFac.getText().equalsIgnoreCase(strNomCliVolFac)) {
            if (txtNomCliVolFac.getText().equals("")) {
                txtCodCliVolFac.setText("");
                txtNomCliVolFac.setText("");
                txtDirCliVolFac.setText("");
            } else {
                BuscarCliente("a.tx_nom", txtNomCliVolFac.getText(), 1);
            }
        } else {
            txtNomCliVolFac.setText(strNomCliVolFac);
        }
}//GEN-LAST:event_txtNomCliVolFacFocusLost

    public void BuscarCliente(String campo, String strBusqueda, int tipo) 
    {
        objVenConCli.setTitle("Listado de Clientes");
        if (objVenConCli.buscar(campo, strBusqueda)) 
        {
            txtCodCliVolFac.setText(objVenConCli.getValueAt(1));
            txtNomCliVolFac.setText(objVenConCli.getValueAt(2));
            txtDirCliVolFac.setText(objVenConCli.getValueAt(3));
        } 
        else 
        {
            objVenConCli.setCampoBusqueda(tipo);
            objVenConCli.cargarDatos();
            objVenConCli.show();
            if (objVenConCli.getSelectedButton() == objVenConCli.INT_BUT_ACE) 
            {
                txtCodCliVolFac.setText(objVenConCli.getValueAt(1));
                txtNomCliVolFac.setText(objVenConCli.getValueAt(2));
                txtDirCliVolFac.setText(objVenConCli.getValueAt(3));
            }
            else 
            {
                txtCodCliVolFac.setText(strCodCliVolFac);
                txtNomCliVolFac.setText(strNomCliVolFac);
                txtDirCliVolFac.setText(strDirCliVolFac);
            }
        }    
    }

    private void txtNomCliVolFacVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtNomCliVolFacVetoableChange
        // Prueba
}//GEN-LAST:event_txtNomCliVolFacVetoableChange

    private void butCliConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliConActionPerformed
        configurarVenConClientes();
        objVenConCli.setTitle("Listado de Clientes");
        objVenConCli.setCampoBusqueda(1);
        objVenConCli.show();
        if (objVenConCli.getSelectedButton() == objVenConCli.INT_BUT_ACE) 
        {
            txtCodCliVolFac.setText(objVenConCli.getValueAt(1));
            txtNomCliVolFac.setText(objVenConCli.getValueAt(2));
            txtDirCliVolFac.setText(objVenConCli.getValueAt(3));
            strCodCliVolFac = objVenConCli.getValueAt(1);         
        }
    }//GEN-LAST:event_butCliConActionPerformed

    private void txtDirCliVolFacCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDirCliVolFacCaretUpdate
}//GEN-LAST:event_txtDirCliVolFacCaretUpdate

    private void txtDirCliVolFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirCliVolFacActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtDirCliVolFacActionPerformed

    private void butDirCliConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDirCliConActionPerformed
        // TODO add your handling code here:

        objVenConDirCli.setTitle("Listado de Direcciones de Clientes");
        objVenConDirCli.setSentenciaSQL("select co_cli, tx_dir, tx_tel1, tx_tel2, tx_tel3 from tbm_dircli where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_cli=" + (txtCodCliVolFac.getText().equals("") ? "0" : txtCodCliVolFac.getText()));
        objVenConDirCli.setCampoBusqueda(1);
        objVenConDirCli.cargarDatos();
        objVenConDirCli.show();
        if (objVenConDirCli.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtDirCliVolFac.setText(objVenConDirCli.getValueAt(2));
            // txtcli_telefono.setText( objVenConDirCli.getValueAt(3).trim() +" "+ objVenConDirCli.getValueAt(4).trim() +" "+ objVenConDirCli.getValueAt(5).trim() );
        }

    }//GEN-LAST:event_butDirCliConActionPerformed

    private void radDesEspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radDesEspActionPerformed
        if (intEstDev4 == 1) {
            ocultaCol(INT_TBL_CANDEV);
            ocultaCol(INT_TBL_PREDEV);
            ocultaCol(INT_TBL_SEDEVPRV);
            ocultaCol(INT_TBL_NODEVPRV);
            ocultaCol(INT_TBL_CANVOLFAC);
            ocultaCol(INT_TBL_CAN_SOL_NODEPRV);
            MostrarCol(INT_TBL_DESDEV);

            desactivarVolFac();

            LimpiarDatos(0);

            tblDat.getColumnModel().getColumn(INT_TBL_DESDEV).setCellEditor(objTblCelNoEdi);

            for (int i = 0; i < tblDat.getRowCount(); i++) {
                tblDat.setValueAt(dblPorDesEsp, i, INT_TBL_DESDEV);
            }

            if (tblDat.getRowCount() > 0) {
                if (dblPorDesEsp > 0.00) {
                    //calculaDescuento2();
                    calculaDescuento2Big();
                } else {
                    MensajeInf("CLIENTE NO TIENE CONFIGURADO PORCENTAJE DE DESCUENTO ESPECIAL. POR FAVOR REVISE LA INFORMACION. ");
                }
            }
            intEstDev1 = 1;
            intEstDev2 = 1;
            intEstDev3 = 1;
            intEstDev4 = 0;
        }

    }//GEN-LAST:event_radDesEspActionPerformed

    private void txtCodMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMotActionPerformed
        txtCodMot.transferFocus();
    }//GEN-LAST:event_txtCodMotActionPerformed

    private void txtCodMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusGained
        strCodMot = txtCodMot.getText();
        txtCodMot.selectAll();
    }//GEN-LAST:event_txtCodMotFocusGained

    private void txtCodMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusLost
        if (!txtCodMot.getText().equalsIgnoreCase(strCodMot)) {
            if (txtCodMot.getText().equals("")) {
                txtCodMot.setText("");
                txtDesLarMot.setText("");
            } else {
                BuscarMotivo("a1.co_motdev", txtCodMot.getText(), 1);
            }
        } else {
            txtCodMot.setText(strCodMot);
        }
    }//GEN-LAST:event_txtCodMotFocusLost

    private void txtDesLarMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarMotActionPerformed
        txtDesLarMot.transferFocus();
    }//GEN-LAST:event_txtDesLarMotActionPerformed

    private void txtDesLarMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotFocusGained
        strDesLarMot = txtDesLarMot.getText();
        txtDesLarMot.selectAll();
    }//GEN-LAST:event_txtDesLarMotFocusGained

    private void txtDesLarMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotFocusLost
        // TODO add your handling code here:
        if (!txtDesLarMot.getText().equalsIgnoreCase(strDesLarMot)) {
            if (txtDesLarMot.getText().equals("")) {
                txtCodMot.setText("");
                txtDesLarMot.setText("");
            } else {
                BuscarMotivo("a1.tx_desLar", txtDesLarMot.getText(), 2);
            }
        } else {
            txtDesLarMot.setText(strDesLarMot);
        }
    }//GEN-LAST:event_txtDesLarMotFocusLost

    private void butMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMotActionPerformed
        objVenConMot.setTitle("Listado de Motivos");
        objVenConMot.setCampoBusqueda(1);
        objVenConMot.show();
        if (objVenConMot.getSelectedButton() == objVenConMot.INT_BUT_ACE) {
            txtCodMot.setText(objVenConMot.getValueAt(1));
            txtDesLarMot.setText(objVenConMot.getValueAt(2));
            strCodMot = objVenConMot.getValueAt(1);
        }
    }//GEN-LAST:event_butMotActionPerformed

    private void txtDirCliVolFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirCliVolFacFocusGained
        strDirCliVolFac = txtDirCliVolFac.getText();
        txtDirCliVolFac.selectAll();        
    }//GEN-LAST:event_txtDirCliVolFacFocusGained

    private void MostrarColChk(int intCol) {
        tblDat.getColumnModel().getColumn(intCol).setWidth(35);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(35);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(35);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(35);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private void MostrarCol(int intCol) {
        tblDat.getColumnModel().getColumn(intCol).setWidth(50);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(50);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(50);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(50);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private void ocultaCol(int intCol) {
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }

    private void LimpiarDatos(int intVal) {
        for (int i = 0; i < tblDat.getRowCount(); i++) {
            tblDat.setValueAt("", i, INT_TBL_CANDEV);
            tblDat.setValueAt("", i, INT_TBL_DESDEV);
            tblDat.setValueAt("", i, INT_TBL_PREDEV);
            tblDat.setValueAt("", i, INT_TBL_TOTAL);
        }
        txtSub.setText("0.00");
        txtIva.setText("0.00");
        /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
        txtCompSol.setText("0.0");
        txtSubTotIvaCero.setText("0.0");
        txtSubTotIva.setText("0.0");
        /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
        txtTot.setText("0.00");
        if (intVal == 1) {
            chkVolFac.setSelected(false);
        }
    }

    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) {
        objVenConTipdoc.setTitle("Listado de Vendedores");
        if (objVenConTipdoc.buscar(campo, strBusqueda)) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDocSol.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc = objVenConTipdoc.getValueAt(1);
        } else {
            objVenConTipdoc.setCampoBusqueda(tipo);
            objVenConTipdoc.cargarDatos();
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDocSol.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc = objVenConTipdoc.getValueAt(1);
            } else {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCodTitpDoc.setText(strDesCodTitpDoc);
                txtDesLarTipDocSol.setText(strDesLarTipDocSol);
            }
        }
    }

    public void BuscarVendedor(String campo, String strBusqueda, int tipo) {
        objVenConVen.setTitle("Listado de Vendedores");
        if (objVenConVen.buscar(campo, strBusqueda)) {
            txtCodSol.setText(objVenConVen.getValueAt(1));
            txtDesSol.setText(objVenConVen.getValueAt(2));
        } else {
            objVenConVen.setCampoBusqueda(tipo);
            objVenConVen.cargarDatos();
            objVenConVen.show();
            if (objVenConVen.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                txtCodSol.setText(objVenConVen.getValueAt(1));
                txtDesSol.setText(objVenConVen.getValueAt(2));
            } else {
                txtCodSol.setText(strCodSol);
                txtDesSol.setText(strDesSol);
            }
        }
    }

    public void BuscarMotivo(String campo, String strBusqueda, int tipo) {
        objVenConMot.setTitle("Listado de Motivos");

        if (objVenConMot.buscar(campo, strBusqueda)) {
            txtCodMot.setText(objVenConMot.getValueAt(1));
            txtDesLarMot.setText(objVenConMot.getValueAt(2));
        } else {
            objVenConMot.setCampoBusqueda(tipo);
            objVenConMot.cargarDatos();
            objVenConMot.show();
            if (objVenConMot.getSelectedButton() == objVenConMot.INT_BUT_ACE) {
                txtCodMot.setText(objVenConMot.getValueAt(1));
                txtDesLarMot.setText(objVenConMot.getValueAt(2));
            } else {
                txtCodMot.setText(strCodMot);
                txtDesLarMot.setText(strDesLarMot);
            }
        }
    }

    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst) {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

    /**
     * Esta funcion se encarga de buscar la factura y cargar los datos
     * MODIFICADO EFLORESA 2012-04-17 DESCUENTO ESPECIAL
     */
    private boolean cargarFac(String strNumDoc) {
        boolean blnRes = false;
        Connection conn;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try {
            conn = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) {
                stmLoc = conn.createStatement();

                txtSub.setText("0.00");
                txtIva.setText("0.00");
                /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
                txtCompSol.setText("0.0");
                txtSubTotIvaCero.setText("0.0");
                txtSubTotIva.setText("0.0");
                /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
                
                txtTot.setText("0.00");
                /*cambio por compensacion solidaria porcentaje de iva */
                //strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_cli, a.tx_nomcli, a.co_doc, a.fe_Doc, a1.tx_nom, a2.tx_descor, a2.tx_deslar, a.tx_obs1, a.tx_obs2, a.nd_poriva, a3.st_ivaven "
                strSql = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_cli, a.tx_nomcli, a.co_doc, a.fe_Doc, a1.tx_nom, a2.tx_descor, a2.tx_deslar, a.tx_obs1, a.tx_obs2, a.nd_poriva, a3.st_ivaven, a.nd_valcomsol, a.nd_porcomsol "
                /*cambio por compensacion solidaria porcentaje de iva */                        
                        + " FROM tbm_cabmovinv AS a "
                        + " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc) "
                        + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) "
                        + " INNER JOIN tbm_cli AS a3 ON(a3.co_emp=a.co_emp AND a3.co_cli=a.co_cli )  "
                        + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                        + " AND  a.co_tipdoc IN("
                        + " SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND "
                        + " b.co_loc = " + objZafParSis.getCodigoLocal() + " AND  b.co_mnu =" + objZafParSis.getCodigoMenu() + " ) "
                        + " AND a.ne_numdoc=" + strNumDoc + " AND a.st_reg NOT IN('I','E')";
                //System.out.println("ZafVen11.cargarFac: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {

                    //  Alfredo solicita quitar validacion  17 Agosto 2011   if( _verificaGuiNoImp( conn, rstLoc.getString("co_emp"), rstLoc.getString("co_loc"), rstLoc.getString("co_tipdoc"), rstLoc.getString("co_doc") ) ){
                    if (cargarFacCab(conn, rstLoc)) {
                        if (cargarFacDet(conn, rstLoc)) {
                            bloquea(txtNumDocFac, false);
                            blnRes = true;

                            txtCodCliVolFac.setText("");
                            txtNomCliVolFac.setText("");
                            txtDirCliVolFac.setText("");
                            strCodCliVolFac = "";
                            strNomCliVolFac = "";

                            if (radDesEsp.isSelected()) {
                                if (dblPorDesEsp > 0.00) {
                                    //calculaDescuento2();
                                    calculaDescuento2Big();
                                } else {
                                    MensajeInf("CLIENTE NO TIENE CONFIGURADO PORCENTAJE DE DESCUENTO ESPECIAL. POR FAVOR REVISE LA INFORMACION. ");
                                }
                            }

                        }
                    }
                }//}
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        } catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean _verificaGuiNoImp(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();

                strSql = "select a2.ne_numdoc from tbm_cabsoldevven as a "
                        + " inner join tbm_detguirem as a1 on ( a1.co_emprel=a.co_emp and a1.co_locrel=a.co_locrel and a1.co_tipdocrel=a.co_tipdocrel and a1.co_docrel=a.co_docrel ) "
                        + " inner join tbm_cabguirem as a2 on ( a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "
                        + " where a.co_emp=" + strCodEmp + " and a.co_locrel=" + strCodLoc + " and a.co_tipdocrel=" + strCodTipDoc + " and a.co_docrel=" + strCodDoc + "  and a2.st_reg='A' and a2.ne_numdoc=0  ";
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    this.MensajeInf("LA GUIA CORRESPONDIENTE A ESTA FACTURA NO ESTA IMPRESA Y NO PUEDE HACERSE MAS DE UNA SOLICITUD.");
                    blnRes = false;
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private boolean cargarFacCab(java.sql.Connection conn, java.sql.ResultSet rstDat) {
        boolean blnRes = false;
        try {
            if (conn != null) {
                txtCodLoc.setText(rstDat.getString("co_loc"));
                txtDesLoc.setText(rstDat.getString("tx_nom"));
                txtCodTipDocFac.setText(rstDat.getString("co_tipdoc"));
                txtDesCorTipDoc.setText(rstDat.getString("tx_descor"));
                txtDesLarTipDoc.setText(rstDat.getString("tx_deslar"));
                txtCodCli.setText(rstDat.getString("co_cli"));
                txtNomCli.setText(rstDat.getString("tx_nomcli"));
                txtCodDocFac.setText(rstDat.getString("co_doc"));
                txaObs1.setText(rstDat.getString("tx_obs1"));
                txaObs2.setText(rstDat.getString("tx_obs2"));
                dblPorIva = rstDat.getDouble("nd_poriva");
                /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
                dblCompSol = rstDat.getDouble("nd_valcomsol");
                /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
                /*AGREGADO PARA EL CAMBIO DEL IVA DEL 1 DE JUNIO DEL 2017*/
                dblPorComSol=rstDat.getDouble("nd_porcomsol");
                /*AGREGADO PARA EL CAMBIO DEL IVA DEL 1 DE JUNIO DEL 2017*/
                stIvaVen = rstDat.getString("st_ivaven");
                txtFecDocFac.setText(rstDat.getString("fe_Doc"));
                blnRes = true;
            }
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    /*
     * MODIFICADO EFLORESA 2012-04-17
     * DESCUENTO ESPECIAL
     */
    private boolean cargarFacDet(Connection conn, ResultSet rstDat) {
        boolean blnRes = false;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        Vector vecData;
        try {
            if (conn != null) {
                stmLoc = conn.createStatement();

                vecData = new Vector();
                /*strSql="SELECT a.co_itmact, a.co_reg, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_bod,  abs(a.nd_can) as nd_can, a.nd_preuni, a.nd_pordes, a.st_ivacom ,a1.tx_nom  " +
                 " "+
                 " ,( "+
                 " SELECT sum(b1.nd_candev) AS canDev FROM tbm_cabsoldevven AS b " +
                 " INNER JOIN tbm_detsoldevven AS b1 ON(b1.co_emp=b.co_emp AND b1.co_loc=b.co_loc AND b1.co_tipdoc=b.co_tipdoc AND b1.co_doc=b.co_doc) " +
                 " WHERE b.co_emp=a.co_emp AND b.co_locrel=a.co_loc AND b.co_tipdocrel=a.co_tipdoc AND b.co_docrel=a.co_doc AND b.st_tipdev='C' AND  b.st_reg ='A' AND b.st_aut IN('P','A') AND b1.co_regrel=a.co_reg " +
                 " ) AS canDev " +
                 ",( select  a2.tx_nomcli from tbr_detmovinv as a3 " +
                 " inner join tbr_cabmovinv as a1 on( " +
                 " a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_doc=a3.co_doc and  " +
                 " a1.co_emprel=a3.co_emprel and a1.co_locrel=a3.co_locrel and a1.co_tipdocrel=a3.co_tipdocrel and a1.co_docrel=a3.co_docrel) " +
                 " inner join tbm_cabmovinv as a2 on(a2.co_emp=a1.co_emprel and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel ) " +
                 " where a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_Doc and a3.co_reg=a.co_reg " +
                 " ) as nomprv "+
                 "  ,case when ( " +
                 " select   a1.co_docrel from tbr_detmovinv as a3 " +
                 " inner join tbr_cabmovinv as a1 on( " +
                 " a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_doc=a3.co_doc and " +
                 " a1.co_emprel=a3.co_emprel and a1.co_locrel=a3.co_locrel and a1.co_tipdocrel=a3.co_tipdocrel and a1.co_docrel=a3.co_docrel) " +
                 " where a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_Doc and a3.co_reg=a.co_reg " +
                 " ) is null then 'N' else 'S' end as st_sel "+
                 " ,a.nd_preunivenlis, a.nd_pordesvenmax " +
                 " FROM tbm_detmovinv AS a   INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)" +
                 " WHERE a.co_emp="+rstDat.getString("co_emp")+" AND  a.co_loc="+rstDat.getString("co_loc")+"  AND a.co_tipdoc="+rstDat.getString("co_tipdoc")+" AND a.co_doc="+rstDat.getString("co_doc");*/

                strSql = " SELECT a.co_itmact, "
                        + " a.co_reg, "
                        + " a.tx_codalt, "
                        + " a.tx_nomitm, "
                        + " a.tx_unimed, "
                        + " a.co_bod,  "
                        + " abs(a.nd_can) as nd_can, "
                        + " a.nd_preuni, "
                        + " a.nd_pordes, "
                        + " a.st_ivacom, "
                        + " a1.tx_nom, "
                        + " (SELECT sum(b1.nd_candev) AS canDev FROM tbm_cabsoldevven AS b INNER JOIN tbm_detsoldevven AS b1 ON(b1.co_emp=b.co_emp AND b1.co_loc=b.co_loc AND b1.co_tipdoc=b.co_tipdoc AND b1.co_doc=b.co_doc) WHERE b.co_emp=a.co_emp AND b.co_locrel=a.co_loc AND b.co_tipdocrel=a.co_tipdoc AND b.co_docrel=a.co_doc AND b.st_tipdev='C' AND b.st_reg ='A' AND b.st_aut IN('P','A') AND b1.co_regrel=a.co_reg ) AS canDev, "
                        + " (select a2.tx_nomcli from tbr_detmovinv as a3 inner join tbr_cabmovinv as a1 on( a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_doc=a3.co_doc and  a1.co_emprel=a3.co_emprel and a1.co_locrel=a3.co_locrel and a1.co_tipdocrel=a3.co_tipdocrel and a1.co_docrel=a3.co_docrel and a1.co_tipdocrel!=293) inner join tbm_cabmovinv as a2 on(a2.co_emp=a1.co_emprel and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel ) where a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_Doc and a1.co_tipdocrel!=293 and a3.co_reg=a.co_reg ) as nomprv, "
                        + " case when (select a1.co_docrel from tbr_detmovinv as a3 inner join tbr_cabmovinv as a1 on( a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_doc=a3.co_doc and a1.co_emprel=a3.co_emprel and a1.co_locrel=a3.co_locrel and a1.co_tipdocrel=a3.co_tipdocrel and a1.co_docrel=a3.co_docrel and a3.co_tipdocrel!=293) where a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_Doc and a3.co_reg=a.co_reg ) is null then 'N' else 'S' end as st_sel, "
                        + " a.nd_preunivenlis, "
                        + " a.nd_pordesvenmax, "
                        + " a3.co_cli, "
                        + " case when a3.nd_desesp is null then 0 else a3.nd_desesp end as nd_desesp,  "
                        /*Agregado por proyecto de reservas CMATEO*/
                        + " a.nd_canres"
                		/*Agregado por proyecto de reservas CMATEO*/
                        + " FROM tbm_detmovinv AS a  "
                        + " INNER JOIN tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "
                        + " INNER JOIN tbm_cabmovinv AS a2 ON (a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                        + " INNER JOIN tbm_cli AS a3 ON (a3.co_emp=a2.co_emp and a3.co_cli=a2.co_cli and a3.st_reg='A') "
                        + " WHERE a.co_emp=" + rstDat.getString("co_emp")
                        + " AND a.co_loc=" + rstDat.getString("co_loc")
                        + " AND a.co_tipdoc=" + rstDat.getString("co_tipdoc")
                        + " AND a.co_doc=" + rstDat.getString("co_doc");

                //System.out.println("ZafVen11.cargarFacDet: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while (rstLoc.next()) {
                    Vector vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itmact"));
                    vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                    vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                    vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_CODUNI, "");
                    vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                    vecReg.add(INT_TBL_BUTBOD, "...");
                    vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                    vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                    vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
					/*Agregado por proyecto de reservas CMATEO*/
                    vecReg.add(INT_TBL_CANRES, rstLoc.getString("nd_canres"));
					/*Agregado por proyecto de reservas CMATEO*/
                    vecReg.add(INT_TBL_CANDEV, "");
                    vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni"));
                    vecReg.add(INT_TBL_PREDEV, "");
                    vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes"));

                    dblPorDesEsp = objUti.redondear(rstLoc.getString("nd_desesp"), 2);
                    if (radDesEsp.isSelected()) {
                        vecReg.add(INT_TBL_DESDEV, dblPorDesEsp);
                    } else {
                        vecReg.add(INT_TBL_DESDEV, "");
                    }

                    vecReg.add(INT_TBL_BLNIVA, ((rstLoc.getString("st_ivacom").equals("S")) ? true : false));
                    vecReg.add(INT_TBL_TOTAL, "");
                    vecReg.add(INT_TBL_REVTEC, false);
                    vecReg.add(INT_TBL_CANTOTDEV, rstLoc.getString("canDev"));
                    vecReg.add(INT_TBL_CANDEV_ORI, "");
                    vecReg.add(INT_TBL_FALSTKFIC, false);
                    
                    /* JoséMario 29/Dic/2015 */
                    if(chkVolFac.isSelected()){
                        vecReg.add(INT_TBL_CANVOLFAC, rstLoc.getString("canDev"));
                    }
                    else{
                        vecReg.add(INT_TBL_CANVOLFAC, "0");
                    }
                    /* JoséMario 29/Dic/2015 */
                    
                    vecReg.add(INT_TBL_CAN_SOL_NODEPRV, "0");
                    vecReg.add(INT_TBL_SEDEVPRV, "0");
                    vecReg.add(INT_TBL_NODEVPRV, "0");
                    vecReg.add(INT_TBL_NOMPRV, rstLoc.getString("nomprv"));
                    vecReg.add(INT_TBL_DEVPRV, rstLoc.getString("st_sel"));
                    vecReg.add(INT_TBL_PRELISREAL, rstLoc.getString("nd_preunivenlis"));
                    vecReg.add(INT_TBL_DESMAXREAL, rstLoc.getString("nd_pordesvenmax"));

                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                blnRes = true;
            }
        } catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBusFac;
    private javax.swing.JButton butCliCon;
    private javax.swing.JButton butDirCliCon;
    private javax.swing.JButton butMot;
    private javax.swing.JButton butSol;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkVolFac;
	/*agregado por proyecto de reservas CMATEO*/
    private javax.swing.JCheckBox chkcanresdev;
	/*agregado por proyecto de reservas CMATEO*/
    private javax.swing.ButtonGroup grpRdaBut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCodDoc1;
    private javax.swing.JLabel lblCompSol;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMotDev;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblObs3;
    private javax.swing.JLabel lblObs4;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTipDev;
    private javax.swing.JLabel lblTipDocFac;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panDatTabGen;
    private javax.swing.JPanel panGenSol;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTabGenCen;
    private javax.swing.JPanel panTabGenNor;
    private javax.swing.JPanel panTabGenNorCab;
    private javax.swing.JPanel panTxa;
    private javax.swing.JRadioButton radCan;
    private javax.swing.JRadioButton radDes;
    private javax.swing.JRadioButton radDesEsp;
    private javax.swing.JRadioButton radPre;
    private javax.swing.JScrollPane scrollTbl;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodCliVolFac;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodDocFac;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodMot;
    private javax.swing.JTextField txtCodSol;
    private javax.swing.JTextField txtCompSol;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarMot;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLarTipDocSol;
    private javax.swing.JTextField txtDesLoc;
    private javax.swing.JTextField txtDesSol;
    private javax.swing.JTextField txtDirCliVolFac;
    private javax.swing.JTextField txtFecDocFac;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomCliVolFac;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumDocFac;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje) {
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }

    public class ToolBar extends ZafToolBar {

        public ToolBar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objZafParSis);
        }

        @Override
        public boolean anular() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }
                    if (strAux.equals("Anulado")) {
                        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                        blnRes = true;
                    }

//        if(!strEstAut.equals("P")){
//         String strEst="";
//         if(strEstAut.equals("A"))strEst="AUTORIZADO";
//         if(strEstAut.equals("D"))strEst="DENEGADO";
//         if(strEstAut.equals("C"))strEst="CANCELADO";
//         MensajeInf("El documento ya está "+strEst+".\nNo es posible modifcar un documento "+strEst+".");
//         blnRes=true;
//        }

                    if (obtenerEstAut(conn)) {
                        // if(!blnRes){
                        if (anularReg(conn)) {
                            conn.commit();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Anulado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
//       }else blnRes=false;
                    }

                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean anularReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabsoldevven SET st_reg='I' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        @Override
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }

        @Override
        public void clickAnterior() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.previous();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.previous();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public void clickAnular() {
        }

        @Override
        public void clickConsultar() {
            clnTextos();
            noEditable(false);

            java.awt.Color colBack;
            txtCodCliVolFac.setText("");
            txtNomCliVolFac.setText("");
            txtDirCliVolFac.setText("");
            strCodCliVolFac = "";
            strNomCliVolFac = "";
            chkVolFac.setSelected(false);
			/*Agregado por proyecto de reservas CMATEO*/
            chkcanresdev.setSelected(false);
			/*Agregado por proyecto de reservas CMATEO*/
            colBack = txtCodCliVolFac.getBackground();
            txtCodCliVolFac.setEditable(false);
            txtCodCliVolFac.setBackground(colBack);
            txtNomCliVolFac.setEditable(false);
            txtNomCliVolFac.setBackground(colBack);

            colBack = txtDirCliVolFac.getBackground();
            txtDirCliVolFac.setEditable(false);
            txtDirCliVolFac.setBackground(colBack);
            butDirCliCon.setEnabled(false);
            butCliCon.setEnabled(false);

            cargarTipoDoc(2);

        }

        @Override
        public void clickEliminar() {
//          noEditable(false);
        }

        @Override
        public void clickFin() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.last();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.last();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        @Override
        public void clickInicio() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.first();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.first();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            try {
                clnTextos();
                noEditable(false);

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);

                txtCodCliVolFac.setText("");
                txtNomCliVolFac.setText("");
                txtDirCliVolFac.setText("");
                strCodCliVolFac = "";
                strNomCliVolFac = "";
                chkVolFac.setSelected(false);
                /*Agregado por proyecto de reservas CMATEO*/
                chkcanresdev.setSelected(false);
                /*Agregado por proyecto de reservas CMATEO*/
                colBack = txtCodCliVolFac.getBackground();
                txtCodCliVolFac.setEditable(false);
                txtCodCliVolFac.setBackground(colBack);
                txtNomCliVolFac.setEditable(false);
                txtNomCliVolFac.setBackground(colBack);

                colBack = txtDirCliVolFac.getBackground();
                txtDirCliVolFac.setEditable(false);
                txtDirCliVolFac.setBackground(colBack);
                butDirCliCon.setEnabled(false);

                butCliCon.setEnabled(false);


                radCan.setSelected(true);
                ocultaCol(INT_TBL_DESDEV);
                ocultaCol(INT_TBL_PREDEV);
                MostrarCol(INT_TBL_CANDEV);
                LimpiarDatos(1);
                intEstDev1 = 0;
                intEstDev2 = 1;
                intEstDev3 = 1;
                intEstDev4 = 1;


                txtFecDoc.setHoy();
                cargarTipoDoc(1);

                if (rstCab != null) {
                    rstCab.close();
                    rstCab = null;
                }
                if(objZafParSis.getCodigoEmpresa()==1 && objZafParSis.getCodigoLocal()==10){
                    chkVolFac.setEnabled(false);  // JoséMarín 10/Marzo/2015
                }
                

            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void setEstadoBotonMakeFac() {
            switch (getEstado()) {
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

        public void clickSiguiente() {
            try {
                if (rstCab != null) {
                    abrirCon();
                    if (!rstCab.isLast()) {
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                rstCab.next();
                                refrescaDatos(CONN_GLO, rstCab);
                            }
                        } else {
                            rstCab.next();
                            refrescaDatos(CONN_GLO, rstCab);
                        }
                    }
                    CerrarCon();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public boolean eliminar() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }

                    if (!strEstAut.equals("P")) {
                        String strEst = "";
                        if (strEstAut.equals("A")) {
                            strEst = "AUTORIZADO";
                        }
                        if (strEstAut.equals("D")) {
                            strEst = "DENEGADO";
                        }
                        if (strEstAut.equals("C")) {
                            strEst = "CANCELADO";
                        }
                        MensajeInf("El documento ya está " + strEst + ".\nNo es posible modifcar un documento " + strEst + ".");
                        blnRes = true;
                    }


                    if (!blnRes) {
                        if (eliminarReg(conn)) {
                            conn.commit();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Eliminado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        blnRes = false;
                    }

                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean eliminarReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "UPDATE tbm_cabsoldevven SET st_reg='E' WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "AND  co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /*
         * MODIFICADO EFLORESA 2012-04-17
         * DESCUENTO ESPECIAL
         */
        private boolean validaCampos() {
            if (txtDesCodTitpDoc.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Tipo Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtDesCodTitpDoc.requestFocus();
                return false;
            }

            if (!txtFecDoc.isFecha()) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Fecha Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtFecDoc.requestFocus();
                return false;
            }

            if (txtCodSol.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Solicitente >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodSol.requestFocus();
                return false;
            }

            if (txtNumDoc.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Número de Documento >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtNumDoc.requestFocus();
                return false;
            }

            if (txtCodMot.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                MensajeInf("El campo << Motivo de devolución >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodMot.requestFocus();
                return false;
            }

            int INT_TBL_TIPDEV = 0;
            double dblCan = 0;
            double dblCanDevprv = 0;
            if (radCan.isSelected()) {
                INT_TBL_TIPDEV = INT_TBL_CANDEV;
            }
            if (radDes.isSelected()) {
                INT_TBL_TIPDEV = INT_TBL_DESDEV;
            }
            if (radPre.isSelected()) {
                INT_TBL_TIPDEV = INT_TBL_PREDEV;
            }
            if (radDesEsp.isSelected()) {
                INT_TBL_TIPDEV = INT_TBL_DESDEV;
            }

            for (int intRowVal = 0; intRowVal < tblDat.getRowCount(); intRowVal++) {
                dblCan += objUti.redondear(((tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV) == null) ? "0" : (tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV).toString().equals("") ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_TIPDEV).toString())), 4);
            }

            if (dblCan <= 0.00) {
                //JOptionPane obj = new JOptionPane();
                String strTit, strMsg;

                strTit = "Mensaje del sistema Zafiro";
                strMsg = "Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                tabGen.setSelectedIndex(1);
                tblDat.repaint();
                tblDat.requestFocus();
                return false;
            }

            if (chkVolFac.isSelected()) {
                dblCan = 0;
                for (int intRowVal = 0; intRowVal < tblDat.getRowCount(); intRowVal++) {
                    dblCan += objUti.redondear(((tblDat.getValueAt(intRowVal, INT_TBL_CANVOLFAC) == null) ? "0" : (tblDat.getValueAt(intRowVal, INT_TBL_CANVOLFAC).toString().equals("") ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_CANVOLFAC).toString())), 4);
                }
                if (dblCan <= 0) {
                    MensajeInf("ESTA SOLICITANDO VOLVER A FACTURAR\nINGRESE LA CANTIDAD QUE VA A VOLVER A FACTURAR AL MENOS UN ITEM.");
                    tabGen.setSelectedIndex(1);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    return false;
                }
            }

            if (radCan.isSelected()) {
                dblCan = 0;
                dblCanDevprv = 0;
                for (int intRowVal = 0; intRowVal < tblDat.getRowCount(); intRowVal++) {
                    String strTipTer = ((tblDat.getValueAt(intRowVal, INT_TBL_DEVPRV) == null) ? "" : tblDat.getValueAt(intRowVal, INT_TBL_DEVPRV).toString());
                    if (strTipTer.trim().equals("S")) {
                        dblCan += objUti.redondear(((tblDat.getValueAt(intRowVal, INT_TBL_CANDEV) == null) ? "0" : (tblDat.getValueAt(intRowVal, INT_TBL_CANDEV).toString().equals("") ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_CANDEV).toString())), 4);
                        dblCanDevprv += objUti.redondear(((tblDat.getValueAt(intRowVal, INT_TBL_SEDEVPRV) == null) ? "0" : (tblDat.getValueAt(intRowVal, INT_TBL_SEDEVPRV).toString().equals("") ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_SEDEVPRV).toString())), 4);
                        dblCanDevprv += objUti.redondear(((tblDat.getValueAt(intRowVal, INT_TBL_NODEVPRV) == null) ? "0" : (tblDat.getValueAt(intRowVal, INT_TBL_NODEVPRV).toString().equals("") ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_NODEVPRV).toString())), 4);
                    }
                }
                if (dblCan != dblCanDevprv) {
                    MensajeInf("La cantidad que devuelve al proveedor no coincide con la cantidad que ingreso.\nEscriba en el campo y vuelva a intentarlo.");
                    tabGen.setSelectedIndex(1);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    return false;
                }
            }

            return true;
        }

        public boolean insertar() {
            boolean blnRes = false;
            Connection conn;
            Statement stmLoc;
            ResultSet rstLoc;
            String strSql = "";
            int intCodDoc = 0;
            try {

                if (validaCampos()) {

                    conn = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);
                        stmLoc = conn.createStatement();

                        if (getVerificaSiHayDevPreDes(conn, "")) {

                            strSql = "SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabsoldevven WHERE "
                                    + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                                    + " and co_tipDoc = " + txtCodTipDoc.getText();
                            rstLoc = stmLoc.executeQuery(strSql);
                            if (rstLoc.next()) {
                                intCodDoc = rstLoc.getInt("co_doc");
                            }
                            rstLoc.close();
                            rstLoc = null;

                            if (_getVerificaDevReem(conn)) {
                                if (verficarCanDev(conn)) {
                                    if (insertarCab(conn, intCodDoc)) {
                                        if (insertarDet(conn, intCodDoc)) {
                                            conn.commit();
                                            txtCodDoc.setText("" + intCodDoc);
                                            txtNumDocSolOcu.setText(txtNumDoc.getText());
                                            blnRes = true;
                                            cargarDetIns(conn, intCodDoc);
                                        } else {
                                            conn.rollback();
                                        }
                                    } else {
                                        conn.rollback();
                                    }
                                } else {
                                    conn.rollback();
                                }
                            } else {
                                conn.rollback();
                            }


                        }
                        stmLoc.close();
                        stmLoc = null;
                        conn.close();
                        conn = null;
                    }

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * VERIFICA SI LA FACTURA TIENE REEMPLAZO DE FACTURA QUE GENERA
         * DEVOLUVION
         *
         * @param conn coneccion de la base
         * @return true si no tiene false si tiene reemplazo
         */
        private boolean _getVerificaDevReem(java.sql.Connection conn) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            int intTipDocDev = 3;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "select a1.co_emp,   a1.co_locrelsoldevven, a1.co_tipdocrelsoldevven, a1.co_docrelsoldevven from tbr_cabmovinv as a  "
                            + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
                            + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_locrel=" + txtCodLoc.getText() + " and a.co_tipdocrel=" + txtCodTipDocFac.getText() + " "
                            + " and a.co_docrel= " + txtCodDocFac.getText() + "   and  a.co_tipdoc = " + intTipDocDev + "  "
                            + " and  a1.co_docrelsoldevven is null   ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        MensajeInf("TIENE UN REEMPLAZO DE FACTURA QUE GENERO UNA DEVOLUCION, NO SE PUEDE REALIZAR LA SOLICITUD.");
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;

                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean verficarCanDev(java.sql.Connection conn) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            double dblCanDev = 0;
            double dblCanVen = 0;
            int intCon = 0;
            StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (radCan.isSelected()) {
                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            if (tblDat.getValueAt(i, INT_TBL_CANDEV) != null) {
                                if (!tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("")) {

                                    dblCanVen = objUti.redondear(tblDat.getValueAt(i, INT_TBL_CANMOV).toString(), 6);
                                    dblCanDev = objUti.redondear(tblDat.getValueAt(i, INT_TBL_CANDEV).toString(), 6);
                                    if (intCon > 0) {
                                        stbins.append(" UNION ALL ");
                                    }
                                    stbins.append("SELECT " + dblCanVen + " AS CanVen,  sum(a1.nd_candev)+" + dblCanDev + " AS canDev FROM tbm_cabsoldevven AS a "
                                            + " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc) "
                                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a.co_locrel=" + txtCodLoc.getText() + " "
                                            + " AND a.co_tipdocrel=" + txtCodTipDocFac.getText() + " AND a.co_docrel=" + txtCodDocFac.getText() + " AND a.st_tipdev='C' AND  a.st_reg ='A' AND a.st_aut IN('P','A') AND a1.co_regrel=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + " ");
                                    intCon++;
                                }
                            }
                        }

                        if (intCon > 0) {
                            strSql = "SELECT * FROM ( " + stbins.toString() + " ) AS x WHERE canDev > CanVen ";
                            rstLoc = stmLoc.executeQuery(strSql);
                            if (rstLoc.next()) {
                                MensajeInf("Hay items ya solicitados para la devolucion \n verifique y vuelva a intentarlo.");
                                blnRes = false;
                            }
                            rstLoc.close();
                            rstLoc = null;
                        }
                    }
                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }


        /*
         * MODIFICADO EFLORESA 2012-04-17
         * DESCUENTO ESPECIAL
         */
        private boolean cargarDetIns(Connection conn, int intCodDoc) {
            boolean blnRes = false;
            Statement stmLoc;
            ResultSet rstLoc;
            String strSql = "";
            Vector vecData;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    vecData = new Vector();
                    strSql = "SELECT a.st_devFalStk, a.co_itm, a.co_bod,  a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_regrel , a.nd_can, "
                            + " a.nd_candev ,  a.nd_candevprv, a.nd_cannodevprv ,a.nd_canvolfac, a.nd_cansolnodevprv "
                            + " ,st_iva , a.nd_preuni, a.nd_pordes , st_revtec , a.nd_tot ,a1.tx_nom   "
                            + ""
                            + ",( SELECT sum(b1.nd_candev) AS canDev "
                            + " FROM tbm_cabsoldevven AS b "
                            + " INNER JOIN tbm_detsoldevven AS b1 ON(b1.co_emp=b.co_emp AND b1.co_loc=b.co_loc AND b1.co_tipdoc=b.co_tipdoc AND b1.co_doc=b.co_doc) "
                            + " WHERE b.co_emp=a.co_emp AND b.co_locrel=a.co_locrel AND b.co_tipdocrel=a.co_tipdocrel AND b.co_docrel=a.co_docrel AND b.st_tipdev='C' AND b.st_reg ='A' AND b.st_aut IN('P','A') AND b1.co_regrel=a.co_regrel "
                            + ") AS canDev "
                            + ",( select  a2.tx_nomcli from tbr_detmovinv as a3 "
                            + " inner join tbr_cabmovinv as a1 on( "
                            + " a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_doc=a3.co_doc and  "
							/*modificado por proyecto de reservas CMATEO*/
                            + " a1.co_emprel=a3.co_emprel and a1.co_locrel=a3.co_locrel and a1.co_tipdocrel=a3.co_tipdocrel and a1.co_docrel=a3.co_docrel and a1.co_tipdocrel!=293) "
							/*modificado por proyecto de reservas CMATEO*/
                            + " inner join tbm_cabmovinv as a2 on(a2.co_emp=a1.co_emprel and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel ) "
                            + " where a3.co_emp=a.co_emp and a3.co_loc=a.co_locrel and a3.co_tipdoc=a.co_tipdocrel and a3.co_doc=a.co_Docrel and a3.co_reg=a.co_regrel "
                            + " ) as nomprv "
                            + " ,case when ( "
                            + " select   a4.co_docrel from tbr_detmovinv as a3 "
                            + " inner join tbr_cabmovinv as a4 on( "
                            + " a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and "
                            /*modificado por proyecto de reservas CMATEO*/
							+ " a4.co_emprel=a3.co_emprel and a4.co_locrel=a3.co_locrel and a4.co_tipdocrel=a3.co_tipdocrel and a4.co_docrel=a3.co_docrel and a4.co_tipdocrel!=293) "
                            + " where a3.co_emp=a.co_emp and a3.co_loc=a.co_locrel and a3.co_tipdoc=a.co_tipdocrel and a3.co_doc=a.co_docrel and a3.co_reg=a.co_regrel "
                            + "  ) is null then 'N' else 'S' end as st_sel   , a2.nd_preunivenlis , a2.nd_pordesvenmax , a.nd_canres"
                            + " FROM tbm_detsoldevven AS a "
                            + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "
                            + " LEFT JOIN tbm_detmovinv AS a2 ON( a2.co_emp=a.co_emp  AND a2.co_loc=a.co_locrel  AND  a2.co_tipdoc=a.co_tipdocrel  AND  a2.co_doc=a.co_docrel  AND  a2.co_reg=a.co_regrel ) "
                            + " WHERE a.co_emp=" + intCodEmpSol + " AND a.co_loc=" + intCodLocSol + "  AND "
                            + " a.co_tipdoc=" + txtCodTipDoc.getText() + " AND a.co_doc=" + intCodDoc + " ORDER BY a.co_reg  ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        Vector vecReg = new Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_regrel"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_CODUNI, "");
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_BUTBOD, "...");
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
                        vecReg.add(INT_TBL_CANRES, rstLoc.getString("nd_canres"));
                        if (radCan.isSelected()) {
                            vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_candev"));
                        } else {
                            vecReg.add(INT_TBL_CANDEV, "");
                        }
                        vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni"));
                        if (radPre.isSelected()) {
                            vecReg.add(INT_TBL_PREDEV, rstLoc.getString("nd_candev"));
                        } else {
                            vecReg.add(INT_TBL_PREDEV, "");
                        }
                        vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes"));
                        if (radDes.isSelected() || radDesEsp.isSelected()) {
                            vecReg.add(INT_TBL_DESDEV, rstLoc.getString("nd_candev"));
                        } else {
                            vecReg.add(INT_TBL_DESDEV, "");
                        }
                        vecReg.add(INT_TBL_BLNIVA, ((rstLoc.getString("st_iva").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_TOTAL, rstLoc.getString("nd_tot"));
                        vecReg.add(INT_TBL_REVTEC, ((rstLoc.getString("st_revtec").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_CANTOTDEV, rstLoc.getString("canDev"));
                        vecReg.add(INT_TBL_CANDEV_ORI, rstLoc.getString("nd_candev"));
                        vecReg.add(INT_TBL_FALSTKFIC, ((rstLoc.getString("st_devFalStk").equals("S")) ? true : false));

                        vecReg.add(INT_TBL_CANVOLFAC, rstLoc.getString("nd_canvolfac"));
                        vecReg.add(INT_TBL_CAN_SOL_NODEPRV, rstLoc.getString("nd_cansolnodevprv"));

                        vecReg.add(INT_TBL_SEDEVPRV, rstLoc.getString("nd_candevprv"));
                        vecReg.add(INT_TBL_NODEVPRV, rstLoc.getString("nd_cannodevprv"));
                        vecReg.add(INT_TBL_NOMPRV, rstLoc.getString("nomprv"));
                        vecReg.add(INT_TBL_DEVPRV, rstLoc.getString("st_sel"));
                        vecReg.add(INT_TBL_PRELISREAL, rstLoc.getString("nd_preunivenlis"));
                        vecReg.add(INT_TBL_DESMAXREAL, rstLoc.getString("nd_pordesvenmax"));

                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean insertarCab(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            String strFecSis = "";
            String strTipDev = "";
            String strRevBodMerDev = "N";
            String strRecmerdev = "N";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/
                    strSql = "select count(ne_numdoc) as num from tbm_cabsoldevven WHERE "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + "and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc=" + txtNumDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if (rstLoc.getInt("num") >= 1) {
                            //JOptionPane oppMsg = new JOptionPane();
                            String strTit, strMsg;
                            strTit = "Mensaje del sistema Zafiro";
                            strMsg = " No. de Solicitud ya existe... ?";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE, null);
                            blnRes = true;
                        }
                    }
                    rstLoc.close();
                    rstLoc = null;
                    if (blnRes) {
                        return false;
                    }
                    blnRes = false;
                    //***********************************************************************************************/

                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    if (radCan.isSelected()) {
                        strTipDev = "C";
                    }
                    if (radDes.isSelected()) {
                        strTipDev = "P";
                    }
                    if (radPre.isSelected()) {
                        strTipDev = "V";
                    }
                    if (radDesEsp.isSelected()) {
                        strTipDev = "E";
                    }

                    strSql = "INSERT INTO tbm_cabsoldevven(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_usrsol, tx_nomusrsol, st_tipdev, "
                            + " nd_sub, nd_poriva, nd_valiva, nd_tot,  "
                            + " co_cli, tx_nomcli, co_locrel, co_tipdocrel, co_docrel,  co_mnu, "
                            + " st_revtec, st_aut , tx_obs1, tx_obs2, st_reg, fe_ing, co_usring, st_revBodMerDev, st_recmerdev, st_volfacmersindev "
                            /*CAMBIO POR COMPENSACION SOLIDARIA EN LOCAL MANTA*/
                          //+ " ,co_clivolfac, tx_dirclivolfac, co_motdev ) "
                            + " ,co_clivolfac, tx_dirclivolfac, co_motdev, nd_subivacer, nd_subivagra, nd_porcomsol, nd_valcomsol ) "
                            /*CAMBIO POR COMPENSACION SOLIDARIA EN LOCAL MANTA*/
                            + " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                            + " ,'" + strFecDoc + "', " + txtNumDoc.getText() + ", " + txtCodSol.getText() + ", '" + txtDesSol.getText() + "', '" + strTipDev + "' "
                            + " ," + (txtSub.getText().equals("") ? "0" : txtSub.getText()) + "," + dblPorIva + ", " + (txtIva.getText().equals("") ? "0" : txtIva.getText()) + "," + (txtTot.getText().equals("") ? "0" : txtTot.getText()) + " "
                            + " ," + txtCodCli.getText() + ", '" + txtNomCli.getText() + "', " + txtCodLoc.getText() + ", " + txtCodTipDocFac.getText() + " "
                            + " ," + txtCodDocFac.getText() + ", " + objZafParSis.getCodigoMenu() + ",'N', 'P' "
                            + " ,'" + txtObs1.getText() + "', '" + txtObs2.getText() + "', 'A', '" + strFecSis + "', " + objZafParSis.getCodigoUsuario() + ", '" + strRevBodMerDev + "', '" + strRecmerdev + "' "
                            + " ,'" + ((chkVolFac.isSelected()) ? "S" : "N") + "'"
                            + "  , " + (txtCodCliVolFac.getText().equals("") ? null : txtCodCliVolFac.getText()) + ", '" + txtDirCliVolFac.getText() + "', " + txtCodMot.getText() +  "," +(txtSubTotIvaCero.getText().equals("") ? "0" : txtSubTotIvaCero.getText())+ "," +(txtSubTotIva.getText().equals("") ? "0" : txtSubTotIva.getText())+  "," + bigPorComSolVen + ","  +(txtCompSol.getText().equals("") ? "0" : txtCompSol.getText())+ " )";


                    stmLoc.executeUpdate(strSql);

                    strSql = "UPDATE tbm_cabtipdoc SET ne_ultdoc=" + txtNumDoc.getText() + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /*
         * MODIFICADO EFLORESA 2012-05-22
         * Uso de la fucion CODIFICAR para las descripciones de items que usan comillas simples.
         */
        private boolean insertarDet(Connection conn, int intCodDoc) {
            boolean blnRes = false;
            Statement stmLoc;
            String strSql = "";
            String strVolFac = "N";
            int intNumReg = 0;
            int INT_TBL_TIPDEV = 0;
            Double dblCanVolFac, dblCanDev, dblTmp = 0.00;
            boolean booPosseDevConRes=false;
            String strSqlUpdCabSolDev="";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (radCan.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_CANDEV;  //cantidad a devolver
                    }
                    if (radDes.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_DESDEV;
                    }
                    if (radPre.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_PREDEV;
                    }
                    if (radDesEsp.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_DESDEV;
                    }

                    strVolFac = ((chkVolFac.isSelected()) ? "S" : "N");


                    for (int i = 0; i < tblDat.getRowCount(); i++) {
//             dblCanVolFac=Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANVOLFAC)==null?"0.00":(tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString().equals(""))?"0.00":tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString());
//             dblCanDev=Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANDEV)==null?"0.00":(tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals(""))?"0.00":tblDat.getValueAt(i, INT_TBL_CANDEV).toString());
//             if(radCan.isSelected() && tblDat.getValueAt(i, INT_TBL_FALSTKFIC).toString().equals("true") && dblCanVolFac>0  && dblCanDev>=dblCanVolFac){
//                 dblTmp = dblCanDev - dblCanVolFac;
//             }
                        intNumReg++;
                        /*  strSql="INSERT INTO tbm_detsoldevven(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_itm, co_bod,  tx_codalt, tx_nomitm, tx_unimed, " +
                         " nd_can,  nd_preuni, nd_pordes, nd_candev, co_locrel,  co_tipdocrel , co_docrel, co_regrel , st_iva, nd_tot, st_revtec, st_devFalStk, nd_candevprv, nd_cannodevprv, nd_canvolfac, nd_cansolnodevprv  ) " +
                         " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
                         " ,"+intNumReg+", "+tblDat.getValueAt(i, INT_TBL_CODITM).toString()+", "+tblDat.getValueAt(i, INT_TBL_CODBOD).toString()+"  " +
                         " ,'"+tblDat.getValueAt(i, INT_TBL_CODALT).toString()+"', '"+tblDat.getValueAt(i, INT_TBL_NOMITM).toString()+"' "+
                         ", '"+tblDat.getValueAt(i, INT_TBL_DESUNI).toString()+"', "+tblDat.getValueAt(i, INT_TBL_CANMOV).toString()+" " +
                         ", "+tblDat.getValueAt(i, INT_TBL_PREUNI).toString()+", "+tblDat.getValueAt(i, INT_TBL_PORDES).toString()+" " +
                         ", "+(tblDat.getValueAt(i, INT_TBL_TIPDEV)==null?"0":(tblDat.getValueAt(i, INT_TBL_TIPDEV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TIPDEV).toString()))+" "+
                         " ,"+txtCodLoc.getText()+" , "+txtCodTipDocFac.getText()+", "+txtCodDocFac.getText()+"  " +
                         ", "+tblDat.getValueAt(i, INT_TBL_CODREG).toString()+" " +
                         ", '"+(tblDat.getValueAt(i, INT_TBL_BLNIVA)==null?"N":(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true")?"S":"N") )+"' "+
                         ", "+tblDat.getValueAt(i, INT_TBL_TOTAL).toString()+"       " +
                         ", "+(tblDat.getValueAt(i, INT_TBL_TOTAL)==null?"0":(tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()))+" " +
                         ", 'N', '"+(tblDat.getValueAt(i, INT_TBL_FALSTKFIC)==null?"N":(tblDat.getValueAt(i, INT_TBL_FALSTKFIC).toString().equals("true")?"S":"N") )+"' "+
                         " , "+(tblDat.getValueAt(i, INT_TBL_SEDEVPRV)==null?"0":(tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString()))+" " +
                         " , "+(tblDat.getValueAt(i, INT_TBL_NODEVPRV)==null?"0":(tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString()))+" " +
                         " , "+(tblDat.getValueAt(i, INT_TBL_CANVOLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString()))+" " +
                         " , "+(tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV)==null?"0":(tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString()))+" " +
                         " ) ";  */
                        strSql = "INSERT INTO tbm_detsoldevven(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_itm, co_bod,  tx_codalt, tx_nomitm, tx_unimed, ";
                        //strSql += " nd_can,  nd_preuni, nd_pordes, nd_candev, co_locrel,  co_tipdocrel , co_docrel, co_regrel , st_iva, nd_tot, st_revtec, st_devFalStk, nd_candevprv, nd_cannodevprv, nd_canvolfac, nd_cansolnodevprv  ) ";
                        strSql += " nd_can,  nd_preuni, nd_pordes, nd_candev, co_locrel,  co_tipdocrel , co_docrel, co_regrel , st_iva, nd_tot, st_revtec, st_devFalStk, nd_candevprv, nd_cannodevprv, nd_canvolfac, nd_cansolnodevprv, nd_basImpIVACer, nd_basImpIVAGra, nd_canres  ) ";
                        strSql += " VALUES(" + objZafParSis.getCodigoEmpresa() + ", " + objZafParSis.getCodigoLocal() + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " ";
                        strSql += " ," + intNumReg + ", " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  ";
                        strSql += " ,'" + tblDat.getValueAt(i, INT_TBL_CODALT).toString() + "', " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM).toString()) + " ";
                        strSql += ", '" + tblDat.getValueAt(i, INT_TBL_DESUNI).toString() + "', " + tblDat.getValueAt(i, INT_TBL_CANMOV).toString() + " ";
                        strSql += ", " + tblDat.getValueAt(i, INT_TBL_PREUNI).toString() + ", " + tblDat.getValueAt(i, INT_TBL_PORDES).toString() + " ";

//           if(radCan.isSelected() && tblDat.getValueAt(i, INT_TBL_FALSTKFIC).toString().equals("true") && dblCanVolFac>0  && dblCanDev>=dblCanVolFac){
//                 strSql+=     ", "+dblTmp+" ";
//           }
//           else{
                        strSql += ", " + (tblDat.getValueAt(i, INT_TBL_TIPDEV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TIPDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TIPDEV).toString())) + " ";
//           }

                        strSql += " ," + txtCodLoc.getText() + " , " + txtCodTipDocFac.getText() + ", " + txtCodDocFac.getText() + "  ";
                        strSql += ", " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + " ";
                        strSql += ", '" + (tblDat.getValueAt(i, INT_TBL_BLNIVA) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true") ? "S" : "N")) + "' ";
                        //", "+tblDat.getValueAt(i, INT_TBL_TOTAL).toString()+"       " +
                        strSql += ", " + (tblDat.getValueAt(i, INT_TBL_TOTAL) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) + " ";
                        strSql += ", 'N', '" + (tblDat.getValueAt(i, INT_TBL_FALSTKFIC) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_FALSTKFIC).toString().equals("true") ? "S" : "N")) + "' ";
                        strSql += " , " + (tblDat.getValueAt(i, INT_TBL_SEDEVPRV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString())) + " ";
                        strSql += " , " + (tblDat.getValueAt(i, INT_TBL_NODEVPRV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString())) + " ";
                        strSql += " , " + (tblDat.getValueAt(i, INT_TBL_CANVOLFAC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString())) + " ";
                        strSql += " , " + (tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString())) + " ";
                        
                        /*AGREGADO POR COMPENSACION SOLIDARIA*/
                        strSql += " , " + ((tblDat.getValueAt(i, INT_TBL_BLNIVA) != null && tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("false"))?(tblDat.getValueAt(i, INT_TBL_TOTAL) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOTAL).toString())):0)+ " ";
                        strSql += " , " + ((tblDat.getValueAt(i, INT_TBL_BLNIVA) != null && tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?(tblDat.getValueAt(i, INT_TBL_TOTAL) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOTAL).toString())):0)+ " ";                        
                        /*AGREGADO POR COMPENSACION SOLIDARIA*/                        
                        
                        /*AGREGADO POR RESERVA*/
                        strSql += " , " + (tblDat.getValueAt(i, INT_TBL_CANRES) == null ? "0":(tblDat.getValueAt(i, INT_TBL_CANRES).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRES).toString()))+ " ";
                        /*AGREGADO POR RESERVA*/
                        strSql += " ) ";
                        //System.out.println(" " + strSql);
                        stmLoc.executeUpdate(strSql);
                        
                        
                        
                        
						/*agregado por proyecto de reservas CMATEO*/
                        if(Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANRES)==null?"0":((tblDat.getValueAt(i, INT_TBL_CANRES)=="")?"0":tblDat.getValueAt(i, INT_TBL_CANRES).toString()))>0 
                                                && radCan.isSelected() 
                                                && Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TIPDEV)==null?"0":tblDat.getValueAt(i, INT_TBL_TIPDEV).toString())> 0){
                            
                            booPosseDevConRes=true;
                        
                        }
						/*agregado por proyecto de reservas CMATEO*/
                    }
                    /*agregado por proyecto de reservas CMATEO*/
                    if(booPosseDevConRes){ /*ACTUALIZAR EL ESTADO EN LA CABECERA DE LA SOLICITUD CAMPO st_canResDev  */
                        strSqlUpdCabSolDev="Update tbm_cabsoldevven set st_canResDev='S' where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDoc;
                    }else{
                        strSqlUpdCabSolDev="Update tbm_cabsoldevven set st_canResDev='N' where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDoc;
                    }
                    stmLoc.executeUpdate(strSqlUpdCabSolDev);
                    /*agregado por proyecto de reservas CMATEO*/
                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean validarDat() {
            boolean blnRes = true;
            if (txtCodTipDoc.getText().trim().equals("")) {
                MensajeInf("Ingrese tipo el documento");
                return false;
            }
            return blnRes;
        }

        private boolean getVerificaSiHayDevPreDes(java.sql.Connection conn, String strSqlAux) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    if (radDes.isSelected()) {
                        strSql = "select co_doc from tbm_cabsoldevven "
                                + " where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + ""
                                + "  and co_locrel=" + txtCodLoc.getText() + " and co_tipdocrel=" + txtCodTipDocFac.getText() + " and co_docrel=" + txtCodDocFac.getText() + " "
                                + " and st_tipdev IN ('P')  and st_aut IN ('A','P') and st_reg='A' " + strSqlAux + " ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            MensajeInf("LA FACTURA YA TIENE SOLICITUD DE DEVOLUCIÓN POR DESCUENTO NO SE PUEDE REALIZAR, SOLO SE PERMITE UNA SOLO VEZ.  ");
                            blnRes = false;
                        }
                        rstLoc.close();
                        rstLoc = null;
                    }

                    if (radDesEsp.isSelected()) {
                        strSql = "select co_doc from tbm_cabsoldevven "
                                + " where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + ""
                                + "  and co_locrel=" + txtCodLoc.getText() + " and co_tipdocrel=" + txtCodTipDocFac.getText() + " and co_docrel=" + txtCodDocFac.getText() + " "
                                + " and st_tipdev = 'E'  and st_aut IN ('A','P') and st_reg='A' " + strSqlAux + " ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            MensajeInf("LA FACTURA YA TIENE SOLICITUD DE DEVOLUCIÓN POR DESCUENTO ESPECIAL NO SE PUEDE REALIZAR, SOLO SE PERMITE UNA SOLA VEZ.  ");
                            blnRes = false;
                        }
                        rstLoc.close();
                        rstLoc = null;
                    }

                    if (radPre.isSelected()) {
                        strSql = "select co_doc from tbm_cabsoldevven "
                                + " where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + txtCodTipDoc.getText() + ""
                                + "  and co_locrel=" + txtCodLoc.getText() + " and co_tipdocrel=" + txtCodTipDocFac.getText() + " and co_docrel=" + txtCodDocFac.getText() + " "
                                + " and st_tipdev IN ('V')  and st_aut IN ('A','P') and st_reg='A' " + strSqlAux + " ";
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            MensajeInf("LA FACTURA YA TIENE SOLICITUD DE DEVOLUCIÓN POR PRECIO NO SE PUEDE REALIZAR, SOLO SE PERMITE UNA SOLO VEZ.  ");
                            blnRes = false;
                        }
                        rstLoc.close();
                        rstLoc = null;
                    }
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean obtenerEstAut(java.sql.Connection conn) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "", strEst = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "select st_aut from tbm_cabsoldevven "
                            + " where co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " and co_tipdoc=" + txtCodTipDoc.getText() + " and co_doc=" + txtCodDoc.getText() + " and st_aut not in('P')";

                    //System.out.println(" > " + strSql );

                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        if (rstLoc.getString("st_aut").equals("A")) {
                            strEst = "AUTORIZADO";
                        }
                        if (rstLoc.getString("st_aut").equals("D")) {
                            strEst = "DENEGADO";
                        }
                        if (rstLoc.getString("st_aut").equals("C")) {
                            strEst = "CANCELADO";
                        }
                        MensajeInf("El documento ya está " + strEst + ".\nNo es posible modifcar un documento " + strEst + ".");
                        blnRes = false;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public boolean modificar() {
            boolean blnRes = false;
            java.sql.Connection conn;
            int intCodDoc = 0;
            try {

                strAux = objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
                    return false;
                }




                if (validaCampos()) {

                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);

                        if (getVerificaSiHayDevPreDes(conn, " and co_doc NOT IN (" + txtCodDoc.getText() + ") ")) {

                            if (obtenerEstAut(conn)) {


                                intCodDoc = Integer.parseInt(txtCodDoc.getText());

                                if (verficarCanDevMod(conn)) {
                                    if (modificarCab(conn, intCodDoc)) {
                                        if (modificarDet(conn, intCodDoc)) {
                                            conn.commit();
                                            blnRes = true;
                                            cargarDetIns(conn, intCodDoc);
                                        } else {
                                            conn.rollback();
                                        }
                                    } else {
                                        conn.rollback();
                                    }
                                } else {
                                    conn.rollback();
                                }

                            }
                        }

                        conn.close();
                        conn = null;
                    }

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean verficarCanDevMod(java.sql.Connection conn) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            double dblCanDev = 0;
            double dblCanDevOri = 0;
            double dblCanVen = 0;
            int intCon = 0;
            StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (radCan.isSelected()) {
                        for (int i = 0; i < tblDat.getRowCount(); i++) {
                            if (tblDat.getValueAt(i, INT_TBL_CANDEV) != null) {
                                if (!tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("")) {

                                    String strDevOri = (tblDat.getValueAt(i, INT_TBL_CANDEV_ORI) == null ? "" : tblDat.getValueAt(i, INT_TBL_CANDEV_ORI).toString());

                                    dblCanVen = objUti.redondear(tblDat.getValueAt(i, INT_TBL_CANMOV).toString(), 6);
                                    dblCanDev = objUti.redondear(tblDat.getValueAt(i, INT_TBL_CANDEV).toString(), 6);
                                    dblCanDevOri = objUti.redondear(strDevOri, 6);
                                    if (dblCanDev > dblCanDevOri) {

                                        if (intCon > 0) {
                                            stbins.append(" UNION ALL ");
                                        }
                                        stbins.append("SELECT " + dblCanVen + " AS CanVen,  sum(a1.nd_candev)+" + (dblCanDev - dblCanDevOri) + " AS canDev FROM tbm_cabsoldevven AS a "
                                                + " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc) "
                                                + " WHERE a.co_emp=" + intCodEmpSol + " AND a.co_locrel=" + txtCodLoc.getText() + " "
                                                + " AND a.co_tipdocrel=" + txtCodTipDocFac.getText() + " AND a.co_docrel=" + txtCodDocFac.getText() + " AND a.st_tipdev='C' AND  a.st_reg ='A' AND a.st_aut IN('P','A') AND a1.co_regrel=" + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + " ");
                                        intCon++;

                                    }
                                }
                            }
                        }

                        if (intCon > 0) {
                            strSql = "SELECT * FROM ( " + stbins.toString() + " ) AS x WHERE canDev > CanVen ";
                            rstLoc = stmLoc.executeQuery(strSql);
                            if (rstLoc.next()) {
                                MensajeInf("Hay items ya solicitados para la devolucion \n verifique y vuelva a intentarlo.");
                                blnRes = false;
                            }
                            rstLoc.close();
                            rstLoc = null;
                        }
                    }
                    stmLoc.close();
                    stmLoc = null;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /*
         * MODIFICADO EFLORESA 2012-04-17
         * DESCUENTO ESPECIAL
         * MODIFICADO EFLORESA 2012-05-22
         * Uso de la fucion CODIFICAR para las descripciones de items que usan comillas simples.
         */
        private boolean modificarDet(Connection conn, int intCodDoc) {
            boolean blnRes = false;
            Statement stmLoc;
            String strSql = "";
            int intNumReg = 0;
            int INT_TBL_TIPDEV = 0;
			/*agregado por proyecto de reservas CMATEO*/
            boolean booPosseDevConRes=false;
            String strSqlUpdCabSolDev="";
			/*agregado por proyecto de reservas CMATEO*/
            try {

                if (conn != null) {
                    stmLoc = conn.createStatement();

                    if (radCan.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_CANDEV;
                    }
                    if (radDes.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_DESDEV;
                    }
                    if (radPre.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_PREDEV;
                    }
                    if (radDesEsp.isSelected()) {
                        INT_TBL_TIPDEV = INT_TBL_DESDEV;
                    }

                    strSql = "DELETE FROM tbm_detsoldevven WHERE co_emp=" + intCodEmpSol + " AND co_loc=" + intCodLocSol + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    for (int i = 0; i < tblDat.getRowCount(); i++) {
                        intNumReg++;
                        /*strSql="INSERT INTO tbm_detsoldevven(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_itm, co_bod,  tx_codalt, tx_nomitm, tx_unimed, " +
                         " nd_can,  nd_preuni, nd_pordes, nd_candev, co_locrel, co_tipdocrel , co_docrel, co_regrel , st_iva, nd_tot, st_revtec, st_devFalStk , nd_candevprv, nd_cannodevprv , nd_canvolfac, nd_cansolnodevprv  ) " +
                         " VALUES("+intCodEmpSol+", "+intCodLocSol+", "+txtCodTipDoc.getText()+", "+intCodDoc+" " +
                         " ,"+intNumReg+", "+tblDat.getValueAt(i, INT_TBL_CODITM).toString()+", "+tblDat.getValueAt(i, INT_TBL_CODBOD).toString()+"  " +
                         " ,'"+tblDat.getValueAt(i, INT_TBL_CODALT).toString()+"', '"+tblDat.getValueAt(i, INT_TBL_NOMITM).toString()+"' "+
                         ", '"+tblDat.getValueAt(i, INT_TBL_DESUNI).toString()+"', "+tblDat.getValueAt(i, INT_TBL_CANMOV).toString()+" " +
                         ", "+tblDat.getValueAt(i, INT_TBL_PREUNI).toString()+", "+tblDat.getValueAt(i, INT_TBL_PORDES).toString()+" " +
                         ", "+(tblDat.getValueAt(i, INT_TBL_TIPDEV)==null?"0":(tblDat.getValueAt(i, INT_TBL_TIPDEV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TIPDEV).toString()))+" "+
                         " ,"+txtCodLoc.getText()+" ,"+txtCodTipDocFac.getText()+", "+txtCodDocFac.getText()+"  " +
                         ", "+tblDat.getValueAt(i, INT_TBL_CODREG).toString()+" " +
                         ", '"+(tblDat.getValueAt(i, INT_TBL_BLNIVA)==null?"N":(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true")?"S":"N") )+"' "+
                         //", "+tblDat.getValueAt(i, INT_TBL_TOTAL).toString()+"       " +
                         ", "+(tblDat.getValueAt(i, INT_TBL_TOTAL)==null?"0":(tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()))+" " +
                         ", '"+(tblDat.getValueAt(i, INT_TBL_REVTEC)==null?"N":(tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true")?"S":"N") )+"' " +
                         ", '"+(tblDat.getValueAt(i, INT_TBL_FALSTKFIC)==null?"N":(tblDat.getValueAt(i, INT_TBL_FALSTKFIC).toString().equals("true")?"S":"N") )+"' "+
                         " , "+(tblDat.getValueAt(i, INT_TBL_SEDEVPRV)==null?"0":(tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString()))+" " +
                         " , "+(tblDat.getValueAt(i, INT_TBL_NODEVPRV)==null?"0":(tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString()))+" " +
                         " , "+(tblDat.getValueAt(i, INT_TBL_CANVOLFAC)==null?"0":(tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString()))+" " +
                         " , "+(tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV)==null?"0":(tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString()))+" " +
                         " ) ";*/

                        strSql = "INSERT INTO tbm_detsoldevven(co_emp, co_loc, co_tipdoc, co_doc,  co_reg,  co_itm, co_bod,  tx_codalt, tx_nomitm, tx_unimed, "
                                
                                /*AGREGADO POR VOLVER A FACTURAR*/
                                //+ " nd_can,  nd_preuni, nd_pordes, nd_candev, co_locrel, co_tipdocrel , co_docrel, co_regrel , st_iva, nd_tot, st_revtec, st_devFalStk , nd_candevprv, nd_cannodevprv , nd_canvolfac, nd_cansolnodevprv  ) "
								/*agregado por proyecto de reservas CMATEO*/
                                + " nd_can,  nd_preuni, nd_pordes, nd_candev, co_locrel,  co_tipdocrel , co_docrel, co_regrel , st_iva, nd_tot, st_revtec, st_devFalStk, nd_candevprv, nd_cannodevprv, nd_canvolfac, nd_cansolnodevprv, nd_basImpIVACer, nd_basImpIVAGra, nd_canres  ) "                                
								/*agregado por proyecto de reservas CMATEO*/
                                /*AGREGADO POR VOLVER A FACTURAR*/                                
                                
                                + " VALUES(" + intCodEmpSol + ", " + intCodLocSol + ", " + txtCodTipDoc.getText() + ", " + intCodDoc + " "
                                + " ," + intNumReg + ", " + tblDat.getValueAt(i, INT_TBL_CODITM).toString() + ", " + tblDat.getValueAt(i, INT_TBL_CODBOD).toString() + "  "
                                + " ,'" + tblDat.getValueAt(i, INT_TBL_CODALT).toString() + "', " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_NOMITM).toString()) + " "
                                + ", '" + tblDat.getValueAt(i, INT_TBL_DESUNI).toString() + "', " + tblDat.getValueAt(i, INT_TBL_CANMOV).toString() + " "
                                + ", " + tblDat.getValueAt(i, INT_TBL_PREUNI).toString() + ", " + tblDat.getValueAt(i, INT_TBL_PORDES).toString() + " "
                                + ", " + (tblDat.getValueAt(i, INT_TBL_TIPDEV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TIPDEV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TIPDEV).toString())) + " "
                                + " ," + txtCodLoc.getText() + " ," + txtCodTipDocFac.getText() + ", " + txtCodDocFac.getText() + "  "
                                + ", " + tblDat.getValueAt(i, INT_TBL_CODREG).toString() + " "
                                + ", '" + (tblDat.getValueAt(i, INT_TBL_BLNIVA) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true") ? "S" : "N")) + "' "
                                + //", "+tblDat.getValueAt(i, INT_TBL_TOTAL).toString()+"       " +
                                ", " + (tblDat.getValueAt(i, INT_TBL_TOTAL) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) + " "
                                + ", '" + (tblDat.getValueAt(i, INT_TBL_REVTEC) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true") ? "S" : "N")) + "' "
                                + ", '" + (tblDat.getValueAt(i, INT_TBL_FALSTKFIC) == null ? "N" : (tblDat.getValueAt(i, INT_TBL_FALSTKFIC).toString().equals("true") ? "S" : "N")) + "' "
                                + " , " + (tblDat.getValueAt(i, INT_TBL_SEDEVPRV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_SEDEVPRV).toString())) + " "
                                + " , " + (tblDat.getValueAt(i, INT_TBL_NODEVPRV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_NODEVPRV).toString())) + " "
                                + " , " + (tblDat.getValueAt(i, INT_TBL_CANVOLFAC) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANVOLFAC).toString())) + " "
                                + " , " + (tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CAN_SOL_NODEPRV).toString())) + " "

                                /*AGREGADO POR COMPENSACION SOLIDARIA*/
                                 + " , " + ((tblDat.getValueAt(i, INT_TBL_BLNIVA) != null && tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("false"))?(tblDat.getValueAt(i, INT_TBL_TOTAL) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOTAL).toString())):0)+ " "
                                 + " , " + ((tblDat.getValueAt(i, INT_TBL_BLNIVA) != null && tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?(tblDat.getValueAt(i, INT_TBL_TOTAL) == null ? "0" : (tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_TOTAL).toString())):0)+ " "                        
                                /*AGREGADO POR COMPENSACION SOLIDARIA*/                        
                                
                                /*AGREGADO POR RESERVA*/
                                + " , " + (tblDat.getValueAt(i, INT_TBL_CANRES) == null ? "0":(tblDat.getValueAt(i, INT_TBL_CANRES).toString().equals("") ? "0" : tblDat.getValueAt(i, INT_TBL_CANRES).toString()))+ " "
                                /*AGREGADO POR RESERVA*/
                                
                                
                                + " ) ";

                        //System.out.println(">>"+ tblDat.getValueAt(i, INT_TBL_BLNIVA) );
                        stmLoc.executeUpdate(strSql);
						/*agregado por proyecto de reservas CMATEO*/
                        System.out.println("Can res "+tblDat.getValueAt(i, INT_TBL_CANRES));
                        System.out.println("Can devolver "+Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TIPDEV)==null?"0":(tblDat.getValueAt(i, INT_TBL_TIPDEV).equals(""))?"0":tblDat.getValueAt(i, INT_TBL_TIPDEV).toString()));
                        
                        if(Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANRES)==null?"0":((tblDat.getValueAt(i, INT_TBL_CANRES)=="")?"0":tblDat.getValueAt(i, INT_TBL_CANRES).toString()))>0 
                                                && radCan.isSelected() 
                                                && Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TIPDEV)==null?"0":(tblDat.getValueAt(i, INT_TBL_TIPDEV).equals(""))?"0":tblDat.getValueAt(i, INT_TBL_TIPDEV).toString())> 0){
                            
                            booPosseDevConRes=true;
                        
                        }  
						/*agregado por proyecto de reservas CMATEO*/                      
                    }
                    /*agregado por proyecto de reservas CMATEO*/
                    if(booPosseDevConRes){ /*ACTUALIZAR EL ESTADO EN LA CABECERA DE LA SOLICITUD CAMPO st_canResDev  */
                        strSqlUpdCabSolDev="Update tbm_cabsoldevven set st_canResDev='S' where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDoc;
                    }else{
                        strSqlUpdCabSolDev="Update tbm_cabsoldevven set st_canResDev='N' where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+intCodDoc;
                    }
                    stmLoc.executeUpdate(strSqlUpdCabSolDev); 
					/*agregado por proyecto de reservas CMATEO*/                   
                    
                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean modificarCab(java.sql.Connection conn, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strFecDoc = "";
            String strFecSis = "";
            String strTipDev = "";
            String strRevTec = "N";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    //************  PERMITE SABER SI EL NUMERO DE Devolucion ESTA DUPLICADO  *****************/
                    if (!txtNumDocSolOcu.getText().equals(txtNumDoc.getText())) {
                        strSql = "select count(ne_numdoc) as num from tbm_cabsoldevven WHERE "
                                + " co_emp=" + intCodEmpSol + " and co_loc=" + intCodLocSol + " "
                                + "and co_tipdoc=" + txtCodTipDoc.getText() + " and ne_numdoc=" + txtNumDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSql);
                        if (rstLoc.next()) {
                            if (rstLoc.getInt("num") >= 1) {
                                //JOptionPane oppMsg = new JOptionPane();
                                String strTit, strMsg;
                                strTit = "Mensaje del sistema Zafiro";
                                strMsg = " No. de Solicitud ya existe... ?";
                                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE, null);
                                blnRes = true;
                            }
                        }
                        rstLoc.close();
                        rstLoc = null;
                        if (blnRes) {
                            return false;
                        }
                        blnRes = false;
                    }
                    //***********************************************************************************************/

                    strFecDoc = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    if (radCan.isSelected()) {
                        strTipDev = "C";
                    }
                    if (radDes.isSelected()) {
                        strTipDev = "P";
                    }
                    if (radPre.isSelected()) {
                        strTipDev = "V";
                    }
                    if (radDesEsp.isSelected()) {
                        strTipDev = "E";
                    }


                    for (int i = 0; i < tblDat.getRowCount(); i++) {
                        if (tblDat.getValueAt(i, INT_TBL_REVTEC) != null) {
                            if (tblDat.getValueAt(i, INT_TBL_REVTEC).toString().equals("true")) {
                                strRevTec = "S";
                            }
                        }
                    }

                    strSql = "UPDATE tbm_cabsoldevven SET   fe_doc='" + strFecDoc + "',  st_revtec='" + strRevTec + "', ne_numdoc=" + txtNumDoc.getText() + ", co_usrsol=" + txtCodSol.getText() + ",  "
                            + " tx_nomusrsol='" + txtDesSol.getText() + "', st_tipdev='" + strTipDev + "', "
                            + " nd_sub=" + txtSub.getText() + ", nd_poriva=" + dblPorIva + ", nd_valiva=" + txtIva.getText() + ", nd_tot=" + txtTot.getText() + ", "
                            + " tx_obs1='" + txtObs1.getText() + "', tx_obs2='" + txtObs2.getText() + "', fe_ultmod='" + strFecSis + "', co_usrmod=" + objZafParSis.getCodigoUsuario() + " "
                            + " ,st_volfacmersindev='" + ((chkVolFac.isSelected()) ? "S" : "N") + "' "
                            + " ,co_clivolfac= " + (txtCodCliVolFac.getText().equals("") ? null : txtCodCliVolFac.getText()) + " "
                            + " ,co_motdev= " + (txtCodMot.getText().equals("") ? null : txtCodMot.getText()) + " "
                            + " ,tx_dirclivolfac='" + txtDirCliVolFac.getText() + "'  "
                            + " ,nd_porcomsol="+ bigPorComSolVen
                            + " ,nd_valcomsol="+ (txtCompSol.getText()!=null?txtCompSol.getText():0)
                            + " ,nd_subivacer=" + (txtSubTotIvaCero.getText()!=null?txtSubTotIvaCero.getText():0)
                            + " ,nd_subivagra=" + (txtSubTotIva.getText()!=null?txtSubTotIva.getText():0)
                            + " WHERE co_emp=" + intCodEmpSol + " AND co_loc=" + intCodLocSol + " AND co_tipdoc=" + txtCodTipDoc.getText() + "  AND co_doc=" + intCodDoc;
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private void bloquea(javax.swing.JTextField txtFiel, java.awt.Color colBack, boolean blnEst) {
            colBack = txtFiel.getBackground();
            txtFiel.setEditable(blnEst);
            txtFiel.setBackground(colBack);
        }

        private void noEditable(boolean blnEst) {
            java.awt.Color colBack = txtCodLoc.getBackground();
            bloquea(txtCodLoc, colBack, blnEst);
            bloquea(txtDesLoc, colBack, blnEst);
            bloquea(txtDesCorTipDoc, colBack, blnEst);
            bloquea(txtDesLarTipDoc, colBack, blnEst);
            bloquea(txtCodCli, colBack, blnEst);
            bloquea(txtNomCli, colBack, blnEst);
            bloquea(txtCodDocFac, colBack, blnEst);
            bloquea(txtSub, colBack, blnEst);
            bloquea(txtIva, colBack, blnEst);
            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
            bloquea(txtIva, colBack, blnEst);
            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
            
            bloquea(txtTot, colBack, blnEst);
            bloquea(txtFecDocFac, colBack, blnEst);

            /*agregado por proyecto de reservas CMATEO*/
            chkcanresdev.setEnabled(false);
            /*agregado por proyecto de reservas CMATEO*/
            
            txaObs1.setEditable(blnEst);
            txaObs2.setEditable(blnEst);
        }

        public void cargarTipoDocSol(int intCodTipDoc, int intCodLoc) {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "Select  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor FROM tbm_cabtipdoc as doc "
                            + " WHERE   doc.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                            + " doc.co_loc=" + intCodLoc + " and "
                            + " doc.co_tipdoc =" + intCodTipDoc;
                    // System.out.println(""+ strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                        txtDesCodTitpDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                        txtDesLarTipDocSol.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                        strCodTipDoc = txtCodTipDoc.getText();
                    }
                    rstLoc.close();
                    stmLoc.close();
                    stmLoc = null;
                    rstLoc = null;
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }

        public void cargarTipoDoc(int intVal) {
            java.sql.Connection conn;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "Select  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor , doc.ne_numVisBue  ,case when (doc.ne_ultdoc+1) is null then 1 else doc.ne_ultdoc+1 end as numDoc  "
                            + " from tbm_cabtipdoc as doc "
                            + " inner join tbr_tipdocprg as menu on ( menu.co_emp = doc.co_emp and menu.co_loc=doc.co_loc and menu.co_tipdoc=doc.co_tipdoc)   "
                            + " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and "
                            + " menu.co_loc = " + objZafParSis.getCodigoLocal() + " and "
                            + " menu.co_mnu = " + objZafParSis.getCodigoMenu() + " and "
                            + "  menu.st_reg = 'S'";
                    //System.out.println("" + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                        txtDesCodTitpDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                        txtDesLarTipDocSol.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                        strCodTipDoc = txtCodTipDoc.getText();
                        if (intVal == 1) {
                            txtNumDoc.setText(((rstLoc.getString("numDoc") == null) ? "" : rstLoc.getString("numDoc")));
                        }
                    }
                    rstLoc.close();
                    stmLoc.close();
                    stmLoc = null;
                    rstLoc = null;
                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }

        public void clnTextos() {
            strCodTipDoc = "";
            strDesCorTipDoc = "";
            strDesLarTipDoc = "";
            strCodSol = "";
            strDesSol = "";

            txtCodLoc.setText("");
            txtDesLoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodDocFac.setText("");
            txtFecDocFac.setText("");
            txtNumDocFac.setText("");
            txtSub.setText("");
            txtIva.setText("");
            
            
            txtCompSol.setText("");
            txtSubTotIvaCero.setText("");
            txtSubTotIva.setText("");
            
            txtTot.setText("");
            txaObs1.setText("");
            txaObs2.setText("");

            txtFecDoc.setText("");
            txtCodSol.setText("");
            txtDesSol.setText("");
            txtNumDoc.setText("");
            txtCodDoc.setText("");
            txtObs1.setText("");
            txtObs2.setText("");

            radCan.setSelected(false);
            radDes.setSelected(false);
            radPre.setSelected(false);
            radDesEsp.setSelected(false);

            txtCodMot.setText("");
            txtDesLarMot.setText("");

            objTblMod.removeAllRows();
        }

        @Override
        public boolean cancelar() {
            boolean blnRes = true;

            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') {
                        if (!isRegPro()) {
                            return false;
                        }
                    }
                }
                if (rstCab != null) {
                    rstCab.close();
                    if (STM_GLO != null) {
                        STM_GLO.close();
                        STM_GLO = null;
                    }
                    rstCab = null;

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            blnHayCam = false;

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

            return true;
        }

        @Override
        public boolean afterModificar() {

            this.setEstado('w');

            return true;
        }
        private void cargarReporte(int intTipo)
        {
            if (objThrGUI == null) 
            {
                objThrGUI = new ZafVen11.ZafThreadGUI();
                objThrGUI.setIndFunEje(intTipo);
                objThrGUI.start();
            }
        }
        
        @Override
        public boolean imprimir() {
            cargarReporte(0);
            return true;
        }
        
        @Override
        public boolean afterVistaPreliminar() {
            return true;
        }

//          /**
//     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
//     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
//     * seleccionando una de las opciones que se presentan.
//     */
        private int mostrarMsgCon(String strMsg) {
            //JOptionPane oppMsg=new JOptionPane();
            String strTit;
            strTit = "Mensaje del sistema Zafiro";
            return JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        }

//          /**
//     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
//     * de tipo texto para poder determinar si su contenido a cambiado o no.
//     */
        private boolean isRegPro() {
            boolean blnRes = true;
            String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux)) {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado()) {
                        case 'n': //Insertar
                            blnRes = objTooBar.insertar();
                            break;
                        case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    objTblMod.setDataModelChanged(false);
                    blnHayCam = false;
                    blnRes = true;
                    break;
                case 2: //CANCEL_OPTION
                    blnRes = false;
                    break;
            }
            return blnRes;
        }

        @Override
        public boolean consultar() {
            /*
             * Esto Hace en caso de que el modo de operacion sea Consulta
             */
            return _consultar(FilSql());
        }

        private boolean _consultar(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
            boolean blnRes = false;
            String strSql = "";
            try {
                if (!validarDat()) {
                    return false;
                }

                abrirCon();
                if (CONN_GLO != null) {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);


                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a.ne_numdoc FROM tbm_cabsoldevven AS a "
                            + " WHERE a.co_emp=" + intCodEmp + " AND a.co_loc=" + intCodLoc + " AND a.co_tipdoc=" + intCodTipDoc + " AND a.co_doc=" + intCodDoc + " AND a.st_reg NOT IN('E') ";
                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    } else {
                        setMenSis("0 Registros encontrados");
                        clnTextos();
                    }

                    CerrarCon();
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            //System.gc();
            Runtime.getRuntime().gc();
            return blnRes;
        }

        private boolean _consultar(String strFil) {
            boolean blnRes = false;
            String strSql = "";
            try {
                if (!validarDat()) {
                    return false;
                }

                abrirCon();
                if (CONN_GLO != null) {
                    STM_GLO = CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);


                    strSql = "SELECT  a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc FROM tbm_cabsoldevven AS a "
                            + " WHERE a.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " AND a.st_reg NOT IN('E') "
                            + " " + strFil + " ORDER BY a.ne_numdoc ";
                    rstCab = STM_GLO.executeQuery(strSql);
                    if (rstCab.next()) {
                        rstCab.last();
                        setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        refrescaDatos(CONN_GLO, rstCab);
                        blnRes = true;
                    } else {
                        setMenSis("0 Registros encontrados");
                        clnTextos();
                    }

                    CerrarCon();
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            //System.gc();
            Runtime.getRuntime().gc();
            return blnRes;
        }

        /*
         * MODIFICADO EFLORESA 2012-04-17
         * DESCUENTO ESPECIAL
         *
         */
        private boolean refrescaDatos(Connection conn, ResultSet rstDatSolCon) {
            boolean blnRes = false;
            Statement stmLoc;
            ResultSet rstLoc;
            ResultSet rstDatSol;
            String strSql = "";
            Vector vecData;
            String strtipdev = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "SELECT a.co_clivolfac, cli.tx_nom as nomclivolfac , a.tx_dirclivolfac, a.fe_doc, a.co_usrsol, a.tx_nomusrsol, a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc, a.ne_numdoc, a.tx_obs1, a.tx_obs2, a.nd_sub, a.nd_valiva, a.nd_tot, "
                            + " a.co_locrel, a.co_cli, a.tx_nomcli, a.co_docrel, a1.tx_nom as tx_desloc, a.co_tipdocrel, a2.tx_descor, a2.tx_deslar,  "
                            + " a3.fe_Doc as fe_docfac, a3.ne_numdoc as ne_numdocfac , a3.tx_obs1 AS tx_obs1fac , a3.tx_obs2 AS tx_obs2fac, a.st_reg, a.st_tipdev, a.st_aut ,a3.nd_poriva "
                            //+ " ,a4.co_tipdoc as  tipdocsol, a4.tx_descor as descorsol , a4.tx_Deslar as deslarsol, a.st_volfacmersindev, a.co_motDev, motdev.tx_deslar as tx_desLarMotDev "
                            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
							/*agregado por proyecto de reservas CMATEO*/
                            + " ,a4.co_tipdoc as  tipdocsol, a4.tx_descor as descorsol , a4.tx_Deslar as deslarsol, a.st_volfacmersindev, a.co_motDev, motdev.tx_deslar as tx_desLarMotDev, a.nd_valcomsol , a.nd_subivacer, a.nd_subivagra, a.st_canresdev"                            
							/*agregado por proyecto de reservas CMATEO*/
                            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
                            + " FROM tbm_cabsoldevven AS a "
                            + " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_locrel AND a2.co_tipdoc=a.co_tipdocrel) "
                            + " INNER JOIN tbm_cabtipdoc AS a4 ON(a4.co_emp=a.co_emp AND a4.co_loc=a.co_loc AND a4.co_tipdoc=a.co_tipdoc)   "
                            + " INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel and a3.co_doc=a.co_docrel) "
                            + " LEFT JOIN tbm_cli AS cli ON(cli.co_emp=a.co_emp AND cli.co_cli=a.co_clivolfac )   "
                            + " LEFT JOIN tbm_motdevinv AS motdev ON (motdev.co_emp=a.co_emp AND motdev.co_motdev=a.co_motdev) "
                            + " WHERE a.co_emp=" + rstDatSolCon.getString("co_emp") + " AND a.co_loc=" + rstDatSolCon.getString("co_loc") + "  AND "
                            + " a.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc") + " AND a.co_doc=" + rstDatSolCon.getString("co_doc");
                    //System.out.println("ZafVen11.refrescaDatos: " + strSql);
                    rstDatSol = stmLoc.executeQuery(strSql);
                    if (rstDatSol.next()) {
                        strAux = rstDatSol.getString("st_reg");

                        txtCodTipDoc.setText(((rstDatSol.getString("tipdocsol") == null) ? "" : rstDatSol.getString("tipdocsol")));
                        txtDesCodTitpDoc.setText(((rstDatSol.getString("descorsol") == null) ? "" : rstDatSol.getString("descorsol")));
                        txtDesLarTipDocSol.setText(((rstDatSol.getString("deslarsol") == null) ? "" : rstDatSol.getString("deslarsol")));
                        strCodTipDoc = txtCodTipDoc.getText();

                        txtCodCliVolFac.setText(rstDatSol.getString("co_clivolfac"));
                        txtNomCliVolFac.setText(rstDatSol.getString("nomclivolfac"));
                        txtDirCliVolFac.setText(rstDatSol.getString("tx_dirclivolfac"));

                        txtCodSol.setText(rstDatSol.getString("co_usrsol"));
                        txtDesSol.setText(rstDatSol.getString("tx_nomusrsol"));
                        txtCodDoc.setText(rstDatSol.getString("co_doc"));
                        txtNumDoc.setText(rstDatSol.getString("ne_numdoc"));
                        txtNumDocSolOcu.setText(rstDatSol.getString("ne_numdoc"));
                        txtObs1.setText(rstDatSol.getString("tx_obs1"));
                        txtObs2.setText(rstDatSol.getString("tx_obs2"));
                        txtSub.setText("" + objUti.redondear(rstDatSol.getDouble("nd_sub"), 2));
                        txtIva.setText("" + objUti.redondear(rstDatSol.getDouble("nd_valiva"), 2));
                        /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
                        txtCompSol.setText("" + objUti.redondear((rstDatSol.getDouble("nd_valcomsol")), 2));
                        txtSubTotIvaCero.setText(""+objUti.redondear(rstDatSol.getDouble("nd_subivacer"), 2));
                        txtSubTotIva.setText(""+ objUti.redondear((rstDatSol.getDouble("nd_subivagra")), 2));
                        
                        /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
                        txtTot.setText("" + objUti.redondear(rstDatSol.getDouble("nd_tot"), 2));
                        dblPorIva = rstDatSol.getDouble("nd_poriva");
                        Date dateObj = rstDatSol.getDate("fe_doc");
                        if (dateObj == null) {
                            txtFecDoc.setText("");
                        } else {
                            Calendar calObj = Calendar.getInstance();
                            calObj.setTime(dateObj);
                            txtFecDoc.setText(calObj.get(Calendar.DAY_OF_MONTH), calObj.get(Calendar.MONTH) + 1, calObj.get(Calendar.YEAR));
                        }

                        if (rstDatSol.getString("st_tipdev").equals("C")) {
                            radCan.setSelected(true);
                        }
                        if (rstDatSol.getString("st_tipdev").equals("P")) {
                            radDes.setSelected(true);
                        }
                        if (rstDatSol.getString("st_tipdev").equals("V")) {
                            radPre.setSelected(true);
                        }
                        if (rstDatSol.getString("st_tipdev").equals("E")) {
                            radDesEsp.setSelected(true);
                        }

                        txtCodMot.setText(rstDatSol.getString("co_motdev"));
                        txtDesLarMot.setText(rstDatSol.getString("tx_deslarmotdev"));

                        txtCodLoc.setText(rstDatSol.getString("co_locrel"));
                        txtDesLoc.setText(rstDatSol.getString("tx_desloc"));
                        txtCodTipDocFac.setText(rstDatSol.getString("co_tipdocrel"));
                        txtDesCorTipDoc.setText(rstDatSol.getString("tx_descor"));
                        txtDesLarTipDoc.setText(rstDatSol.getString("tx_deslar"));
                        txtCodCli.setText(rstDatSol.getString("co_cli"));
                        txtNomCli.setText(rstDatSol.getString("tx_nomcli"));
                        txtCodDocFac.setText(rstDatSol.getString("co_docrel"));
                        txtFecDocFac.setText(rstDatSol.getString("fe_docfac"));
                        txtNumDocFac.setText(rstDatSol.getString("ne_numdocfac"));
                        txaObs1.setText(rstDatSol.getString("tx_obs1fac"));
                        txaObs2.setText(rstDatSol.getString("tx_obs2fac"));
                        strtipdev = rstDatSol.getString("st_tipdev");

                        strEstAut = rstDatSol.getString("st_aut");
                        chkVolFac.setSelected((rstDatSol.getString("st_volfacmersindev").equals("S") ? true : false));
                        /*agregado por proyecto de reservas CMATEO*/
                        chkcanresdev.setSelected(((rstDatSol.getString("st_canresdev")!=null && rstDatSol.getString("st_canresdev").equals("S")) ? true : false));
						/*agregado por proyecto de reservas CMATEO*/
                    }
                    rstDatSol.close();
                    rstDatSol = null;

                    vecData = new Vector();
                    strSql = "SELECT a.st_devFalStk, a.co_itm, a.co_bod,  a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.co_regrel , a.nd_can,  a.nd_candev"
                            + " ,st_iva , a.nd_preuni, a.nd_pordes , st_revtec , a.nd_tot,  a.nd_candevprv, a.nd_cannodevprv  ,a1.tx_nom   "
                            + " ,a.nd_canvolfac, a.nd_cansolnodevprv "
                            + ",( SELECT sum(b1.nd_candev) AS canDev "
                            + " FROM tbm_cabsoldevven AS b "
                            + " INNER JOIN tbm_detsoldevven AS b1 ON(b1.co_emp=b.co_emp AND b1.co_loc=b.co_loc AND b1.co_tipdoc=b.co_tipdoc AND b1.co_doc=b.co_doc) "
                            + " WHERE b.co_emp=a.co_emp AND b.co_locrel=a.co_locrel AND b.co_tipdocrel=a.co_tipdocrel AND b.co_docrel=a.co_docrel AND b.st_tipdev='C' AND b.st_reg ='A' AND b.st_aut IN('P','A') AND b1.co_regrel=a.co_regrel "
                            + ") AS canDev "
                            + ",( select  a2.tx_nomcli from tbr_detmovinv as a3 "
                            + " inner join tbr_cabmovinv as a1 on( "
                            + " a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_doc=a3.co_doc and  "
                            + " a1.co_emprel=a3.co_emprel and a1.co_locrel=a3.co_locrel and a1.co_tipdocrel=a3.co_tipdocrel and a1.co_docrel=a3.co_docrel and a1.co_tipdocrel!=293) "
                            + " inner join tbm_cabmovinv as a2 on(a2.co_emp=a1.co_emprel and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel ) "
                            + " where a3.co_emp=a.co_emp and a3.co_loc=a.co_locrel and a3.co_tipdoc=a.co_tipdocrel and a3.co_doc=a.co_Docrel and a3.co_reg=a.co_regrel "
                            + " ) as nomprv "
                            + " ,case when ( "
                            + " select   a4.co_docrel from tbr_detmovinv as a3 "
                            + " inner join tbr_cabmovinv as a4 on( "
                            + " a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and "
                            + " a4.co_emprel=a3.co_emprel and a4.co_locrel=a3.co_locrel and a4.co_tipdocrel=a3.co_tipdocrel and a4.co_docrel=a3.co_docrel and a4.co_tipdocrel!=293) "
                            + " where a3.co_emp=a.co_emp and a3.co_loc=a.co_locrel and a3.co_tipdoc=a.co_tipdocrel and a3.co_doc=a.co_docrel and a3.co_reg=a.co_regrel "
                            + "  ) is null then 'N' else 'S' end as st_sel   ,a2.nd_preunivenlis ,a2.nd_pordesvenmax,a.nd_canres "
                            + " FROM tbm_detsoldevven AS a "
                            + " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod) "
                            + " LEFT JOIN tbm_detmovinv AS a2 ON( a2.co_emp=a.co_emp  AND a2.co_loc=a.co_locrel  AND  a2.co_tipdoc=a.co_tipdocrel  AND  a2.co_doc=a.co_docrel  AND  a2.co_reg=a.co_regrel ) "
                            + " WHERE a.co_emp=" + rstDatSolCon.getString("co_emp") + " AND a.co_loc=" + rstDatSolCon.getString("co_loc") + "  AND "
                            + " a.co_tipdoc=" + rstDatSolCon.getString("co_tipdoc") + " AND a.co_doc=" + rstDatSolCon.getString("co_doc") + " ORDER BY a.co_regrel ";

                    //System.out.println("ZafVen11.refrescaDatos: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        Vector vecReg = new Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_regrel"));
                        vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_CODUNI, "");
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_BUTBOD, "...");
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bod"));
                        vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));
						/*agregado por proyecto de reservas CMATEO*/
                        vecReg.add(INT_TBL_CANRES, rstLoc.getString("nd_canres"));
						/*agregado por proyecto de reservas CMATEO*/
                        if (strtipdev.equals("C")) {
                            vecReg.add(INT_TBL_CANDEV, rstLoc.getString("nd_candev"));
                        } else {
                            vecReg.add(INT_TBL_CANDEV, "");
                        }
                        vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni"));
                        if (strtipdev.equals("V")) {
                            vecReg.add(INT_TBL_PREDEV, rstLoc.getString("nd_candev"));
                        } else {
                            vecReg.add(INT_TBL_PREDEV, "");
                        }
                        vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes"));
                        if (strtipdev.equals("P") || strtipdev.equals("E")) {
                            vecReg.add(INT_TBL_DESDEV, rstLoc.getString("nd_candev"));
                        } else {
                            vecReg.add(INT_TBL_DESDEV, "");
                        }
                        if (strtipdev.equals("E")) {
                            dblPorDesEsp = objUti.redondear(rstLoc.getString("nd_candev"), 2);
                        }
                        vecReg.add(INT_TBL_BLNIVA, ((rstLoc.getString("st_iva").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_TOTAL, rstLoc.getString("nd_tot"));
                        vecReg.add(INT_TBL_REVTEC, ((rstLoc.getString("st_revtec").equals("S")) ? true : false));
                        vecReg.add(INT_TBL_CANTOTDEV, rstLoc.getString("canDev"));
                        vecReg.add(INT_TBL_CANDEV_ORI, rstLoc.getString("nd_candev"));
                        vecReg.add(INT_TBL_FALSTKFIC, ((rstLoc.getString("st_devFalStk").equals("S")) ? true : false));

                        vecReg.add(INT_TBL_CANVOLFAC, rstLoc.getString("nd_canvolfac"));
                        vecReg.add(INT_TBL_CAN_SOL_NODEPRV, rstLoc.getString("nd_cansolnodevprv"));

                        vecReg.add(INT_TBL_SEDEVPRV, rstLoc.getString("nd_candevprv"));
                        vecReg.add(INT_TBL_NODEVPRV, rstLoc.getString("nd_cannodevprv"));
                        vecReg.add(INT_TBL_NOMPRV, rstLoc.getString("nomprv"));
                        vecReg.add(INT_TBL_DEVPRV, rstLoc.getString("st_sel"));
                        vecReg.add(INT_TBL_PRELISREAL, rstLoc.getString("nd_preunivenlis"));
                        vecReg.add(INT_TBL_DESMAXREAL, rstLoc.getString("nd_pordesvenmax"));
                        vecData.add(vecReg);
                    }
                    objTblMod.setData(vecData);
                    tblDat.setModel(objTblMod);
                    rstLoc.close();
                    rstLoc = null;

                    if (strtipdev.equals("C")) {
                        ocultaCol(INT_TBL_DESDEV);
                        ocultaCol(INT_TBL_PREDEV);
                        MostrarCol(INT_TBL_CANDEV);
                        intEstDev1 = 0;
                        intEstDev2 = 1;
                        intEstDev3 = 1;
                        intEstDev4 = 1;
                    }
                    if (strtipdev.equals("V")) {
                        ocultaCol(INT_TBL_CANDEV);
                        ocultaCol(INT_TBL_DESDEV);
                        MostrarCol(INT_TBL_PREDEV);
                        intEstDev1 = 1;
                        intEstDev2 = 1;
                        intEstDev3 = 0;
                        intEstDev4 = 1;
                    }
                    if (strtipdev.equals("P")) {
                        ocultaCol(INT_TBL_CANDEV);
                        ocultaCol(INT_TBL_PREDEV);
                        MostrarCol(INT_TBL_DESDEV);
                        intEstDev1 = 1;
                        intEstDev2 = 0;
                        intEstDev3 = 1;
                        intEstDev4 = 1;
                    }
                    if (strtipdev.equals("E")) {
                        ocultaCol(INT_TBL_CANDEV);
                        ocultaCol(INT_TBL_PREDEV);
                        MostrarCol(INT_TBL_DESDEV);
                        intEstDev1 = 1;
                        intEstDev2 = 1;
                        intEstDev3 = 1;
                        intEstDev4 = 0;
                    }

                    stmLoc.close();
                    stmLoc = null;

                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else if (strAux.equals("E")) {
                        strAux = "Eliminado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);

                    int intPosRel = rstDatSolCon.getRow();
                    rstDatSolCon.last();
                    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatSolCon.getRow());
                    rstDatSolCon.absolute(intPosRel);

                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }

            return blnRes;
        }

        /*
         * MODIFICADO EFLORESA 2012-04-17
         * DESCUENTO ESPECIAL
         */
        private String FilSql() {
            String sqlFiltro = "";
            //Agregando filtro por Numero de Cotizacion
            if (!txtCodTipDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_tipdoc=" + txtCodTipDoc.getText();
            }

            if (!txtCodSol.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_usrsol=" + txtCodSol.getText();
            }

            if (!txtCodDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.co_doc=" + txtCodDoc.getText();
            }

            if (!txtNumDoc.getText().equals("")) {
                sqlFiltro = sqlFiltro + " and a.ne_numdoc=" + txtNumDoc.getText();
            }


            if (txtFecDoc.isFecha()) {
                int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" + FecSql[0] + "#";
                sqlFiltro = sqlFiltro + " and a.fe_doc = '" + strFecSql + "'";
            }

            if (radCan.isSelected()) {
                sqlFiltro = sqlFiltro + " and a.st_TipDev='C'";
            }
            if (radDes.isSelected()) {
                sqlFiltro = sqlFiltro + " and a.st_TipDev='P'";
            }
            if (radPre.isSelected()) {
                sqlFiltro = sqlFiltro + " and a.st_TipDev='V'";
            }
            if (radDesEsp.isSelected()) {
                sqlFiltro = sqlFiltro + " and a.st_TipDev='E'";
            }


            return sqlFiltro;
        }

        @Override
        public void clickModificar() {
            setEditable(true);
            noEditable(false);

            java.awt.Color colBack;
            colBack = txtCodDoc.getBackground();

            bloquea(txtCodDoc, colBack, false);
            bloquea(txtDesCodTitpDoc, colBack, false);
            bloquea(txtDesLarTipDocSol, colBack, false);
            bloquea(txtNumDocFac, colBack, false);

            if (intEstCon == 1) {

                bloquea(txtCodSol, colBack, false);
                bloquea(txtDesSol, colBack, false);
                bloquea(txtNumDoc, colBack, false);
                txtObs1.setEditable(false);
                txtObs2.setEditable(false);

                radCan.setEnabled(false);
                radDes.setEnabled(false);
                radPre.setEnabled(false);
                radDesEsp.setEnabled(false);

                butSol.setEnabled(false);
            }

            butTipDoc.setEnabled(false);
            butBusFac.setEnabled(false);

            this.setEnabledConsultar(false);
            
            if(objZafParSis.getCodigoEmpresa()==1 && objZafParSis.getCodigoLocal()==10){
                chkVolFac.setEnabled(false);  
            }
            

            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        }

        //******************************************************************************************************
       
        @Override
        public boolean vistaPreliminar() {
            cargarReporte(1);
            return true;
        }

        //******************************************************************************************************
        @Override
        public void clickImprimir() {
        }

        @Override
        public void clickVisPreliminar() {
        }

        public void clickCancelar() {
            txtCodCliVolFac.setText("");
            txtNomCliVolFac.setText("");
            txtDirCliVolFac.setText("");
            chkVolFac.setSelected(false);
        }

        public void cierraConnections() {
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

    private class ZafMouMotAda extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODITM:
                    strMsg = "Cóidigo del item.";
                    break;
                case INT_TBL_CODALT:
                    strMsg = "Cóidigo Alterno del item.";
                    break;
                case INT_TBL_NOMITM:
                    strMsg = "Nombre del item.";
                    break;
                case INT_TBL_DESUNI:
                    strMsg = "Descripción de la unidad de medida.";
                    break;
                case INT_TBL_BUTBOD:
                    strMsg = "ver Bodegas.";
                    break;
                case INT_TBL_NOMBOD:
                    strMsg = "Nombre de la Bodega.";
                    break;
                case INT_TBL_CANMOV:
                    strMsg = "Cantidad del item Facturado.";
                    break;
                case INT_TBL_CANDEV:
                    strMsg = "Cantidad de item que se Devolvera.";
                    break;
                case INT_TBL_PREUNI:
                    strMsg = "Precio Unitario del Item.";
                    break;
                case INT_TBL_PREDEV:
                    strMsg = "Precio que se Devolvera.";
                    break;
                case INT_TBL_PORDES:
                    strMsg = "Porcentaje de Descuento.";
                    break;
                case INT_TBL_DESDEV:
                    strMsg = "Descuento que se Devolvera.";
                    break;
                case INT_TBL_BLNIVA:
                    strMsg = "Marca Iva el Item.";
                    break;
                case INT_TBL_TOTAL:
                    strMsg = "Total.";
                    break;
                case INT_TBL_REVTEC:
                    strMsg = "Requiere Revición Tecnica.";
                    break;
                case INT_TBL_FALSTKFIC:
                    strMsg = "Devolución por faltante de stock.";
                    break;
                case INT_TBL_NOMPRV:
                    strMsg = "Nombre Proveedor.";
                    break;
                case INT_TBL_SEDEVPRV:
                    strMsg = "Cantidad que se Devolvera al Proveedor.";
                    break;
                case INT_TBL_NODEVPRV:
                    strMsg = "Cantidad que no se Devolvera al Proveedor.";
                    break;
                case INT_TBL_CANVOLFAC:
                    strMsg = "Cantidad que se va a volver a Facturar..";
                    break;
                case INT_TBL_CAN_SOL_NODEPRV:
                    strMsg = "Cantidad Solicitada que no se devolvera al Proveedor para quedar en Stock.";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
    }
    
    
    private void volverFacturar(){
        try{
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CODITM) != null) {
                    if (tblDat.getValueAt(i, INT_TBL_CANDEV) != null) {
                        tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CANDEV), i, INT_TBL_CANVOLFAC);
                    }
                }
            }
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
    
 /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread 
    {
        private int intIndFun;

        public ZafThreadGUI() 
        {
            intIndFun = 0;
        }

        @Override
        public void run()
        {
            switch (intIndFun) {
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
            objThrGUI = null;
        }
        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) 
        {
            intIndFun = indice;
        }
    }
        /**
     * Esta función permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
     * @param intTipRpt El tipo de reporte a generar. <BR>Puede tomar uno de los
     * siguientes valores: <UL> <LI>0: Impresión directa. <LI>1: Impresión
     * directa (Cuadro de dialogo de impresión). <LI>2: Vista preliminar. </UL>
     * @return true: Si se pudo generar el reporte. <BR>false: En el caso
     * contrario.
     */
    private boolean generarRpt(int intTipRpt) 
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes = true;
        String strSQL, strRutSubRpt, DIRECCION_REPORTE_GUIA = "";
        String strSQL2="";
        Connection conn;
        Statement stm;
        ResultSet rst;
        
        try 
        {
            boolean cargarListadoReportes = objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            Map mapPar = new HashMap();
            if (objRptSis.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) {
                //Obtener la fecha y hora del servidor.
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux = null;
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) 
                        {
                            case 439://Orden de despacho(importaciones)
                                strSQL2+= "  select a3.co_bod,a3.co_emp, a3.co_loc\n";
                                strSQL2+= " from tbm_cabSolDevVen as a1\n";
                                strSQL2+= " inner join tbm_detmovinv as a3 on (a1.co_emp=a3.co_emp and a1.co_locrel=a3.co_loc and a1.co_tipdocrel=a3.co_tipdoc and a1.co_docrel=a3.co_doc)\n";
                                strSQL2+= " where a1.co_emp="+ objZafParSis.getCodigoEmpresa() + "";
                                strSQL2+= " and a1.co_loc="+ objZafParSis.getCodigoLocal() + "";
                                strSQL2+= " and a1.co_tipdoc="+108  + "";
                                strSQL2+= " and a1.co_doc="+ txtCodDoc.getText() + "";
                                //System.out.println("strSQLSubRep Importaciones: " + strSQLSubRep);

                                strRutRpt = objRptSis.getRutaReporte(i);
                                //System.out.println("RUTA REPORTE: " + strRutRpt);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                DIRECCION_REPORTE_GUIA=strRutRpt+strNomRpt;
                                strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptVen11.jasper"));
                                //Inicializar los parametros que se van a pasar al reporte.
                                  mapPar.put("co_emp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("co_loc", new Integer(objZafParSis.getCodigoLocal()) );
                                mapPar.put("co_tipdoc", new Integer(108) );
                                mapPar.put("co_doc",  new Integer(txtCodDoc.getText()) );
                                mapPar.put("nomUsrImp",  objZafParSis.getNombreUsuario() );

                                mapPar.put("SUBREPORT_DIR", strRutSubRpt );

                                // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                            default:
                               
                                strSQL2+= "  select a3.co_bod,a3.co_emp, a3.co_loc\n";
                                strSQL2+= " from tbm_cabSolDevVen as a1\n";
                                strSQL2+= " inner join tbm_detmovinv as a3 on (a1.co_emp=a3.co_emp and a1.co_locrel=a3.co_loc and a1.co_tipdocrel=a3.co_tipdoc and a1.co_docrel=a3.co_doc)\n";
                                strSQL2+= " where a1.co_emp="+ objZafParSis.getCodigoEmpresa() + "";
                                strSQL2+= " and a1.co_loc="+ objZafParSis.getCodigoLocal() + "";
                                strSQL2+= " and a1.co_tipdoc="+ 108 + "";
                                strSQL2+= " and a1.co_doc="+ txtCodDoc.getText() + "";
                                //System.out.println("strSQLSubRep Importaciones: " + strSQLSubRep);

                                strRutRpt = objRptSis.getRutaReporte(i);
                                //System.out.println("RUTA REPORTE: " + strRutRpt);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                               DIRECCION_REPORTE_GUIA=strRutRpt+strNomRpt;
                                strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptVen11.jasper"));
                                //Inicializar los parametros que se van a pasar al reporte.
                                  mapPar.put("co_emp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("co_loc", new Integer(objZafParSis.getCodigoLocal()) );
                                mapPar.put("co_tipdoc", new Integer(108) );
                                mapPar.put("co_doc",  new Integer(txtCodDoc.getText()) );
                                mapPar.put("nomUsrImp",  objZafParSis.getNombreUsuario() );

                                mapPar.put("SUBREPORT_DIR", strRutSubRpt );

                                // mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        } 
        catch (Exception e) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }
    
}
