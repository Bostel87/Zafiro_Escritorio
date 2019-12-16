/*
 * ZafImp33.java
 *
 * Created on March 15, 2012
 */
package Importaciones.ZafImp33; 
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafToolBar.ZafToolBar; 
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTextField;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import java.awt.Color;

/**
 *
 * @author  Gigi
 */
public class ZafImp33 extends javax.swing.JInternalFrame 
{
    //ArrayList para consultar Pedido Embarcado
    private ArrayList arlDatConPedEmb, arlRegConPedEmb;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIP_DOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USRING=4;  
    private static final int INT_ARL_CON_TXT_USRMOD=5;
    private static final int INT_ARL_CON_COD_IMP=6;  
    private static final int INT_ARL_CON_COD_CTAACT=7;  
    private static final int INT_ARL_CON_COD_CTAPAS=8;  
    private int intIndicePedEmb=0;    
    
    //ArrayList para Agrupar datos de Exportadores
    //private ArrayList arlDatExp, arlRegExp;
    //private static final int INT_ARL_DET_COD_EXP=0;  
    
    //ArrayList
    private ArrayList arlDatCabImp33_03, arlRegCabImp33_03;  //ArrayList: Cabecera "ZafImp33_03"
    private ArrayList arlDatDetImp33_03, arlRegDetImp33_03;  //ArrayList: Detalle "ZafImp33_03"
    private ArrayList arlDatFilEliImp33_03;                  //ArrayList: Para las filas que fueron eliminadas en el detalle    
    
    //ArrayList
    private ArrayList arlRegCtaDeb, arlDatCtaDeb;
    private static final int INT_ARL_CTA_DEB_COD=0;
    private static final int INT_ARL_CTA_DEB_NUM=1;
    private static final int INT_ARL_CTA_DEB_NOM=2;
    
    //ArrayList
    private ArrayList arlRegCtaHab, arlDatCtaHab;
    private static final int INT_ARL_CTA_HAB_COD=0;
    private static final int INT_ARL_CTA_HAB_NUM=1;
    private static final int INT_ARL_CTA_HAB_NOM=2;    
    
    //Variables
    private Connection con, conExt;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDatePicker dtpFecDoc, dtpFecColPed, dtpFecEmb, dtpFecPue, dtpFecArr;
    private ZafDocLis objDocLis;
    private Date datFecAux;
    private Date datFecEmbIni, datFecEmbFin, datFecArrIni, datFecArrFin;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;    
    private ZafVenCon vcoTipDoc, vcoImp, vcoPrv, vcoExp, vcoPto, vcoSeg, vcoForPag;
    private ZafPerUsr objPerUsr;
    private ZafAsiDia objAsiDiaPE;    //Diario Nuevo de Pedido Embarcado
    private ZafAsiDia objAsiDiaPro;   //Diario de Provisión del Pedido Embarcado (Si se cerro)
    private ZafAsiDia objAsiDiaAjuNP; //Diario de Ajuste de la Nota de Pedido
    private ZafAsiDia objAsiDiaAjuPE; //Diario de Ajuste del Pedido Embarcado
    private ZafAsiDia objAsiDiaNueNP; //Diario Nuevo de Nota de Pedido cuando se modifica el Pedido Embarcado
    
    private ZafSegMovInv objSegMovInv;
    private ZafNotCorEle objNotCorEle;
    private ZafImp33_01 objImp33_01;
    private ZafImp33_02 objImp33_02;
    private ZafImp33_03 objImp33_03;  
    private ZafImp objZafImp;
    public ZafTblMod objTblModImp33_01, objTblModImp33_02;     
    
    private Object objCodSegInsAnt;
    private Object objNivAct1, objNivAct2, objNivAct3, objNivAct4, objNivAct5, objNivAct6;
    private Object objNivPas1, objNivPas2, objNivPas3, objNivPas4, objNivPas5, objNivPas6;    
    
    private boolean blnHayCam;
    private boolean blnVenConEme=false;          //true: Se llama desde otra ventana. false: Consulta directamente desde el programa.
    
    public boolean blnUpdatePedEmbAutPag=false; //true: Se continua con el proceso de inserción. false: Cancela el proceso de inserción. //Autorizaciones de Pagos y Bancos
    public boolean blnIsPerModCarPag=true;    
    
    private Vector vecDat, vecReg;
    
    //Rangos para calcular la cantidad permitida en Pzas cuando es pedido en Toneladas.
    private BigDecimal bgdMin= new BigDecimal ("0.95");
    private BigDecimal bgdMax= new BigDecimal ("1.05");    
    
    private int intSig=1;
    private int intTipNotPed;
    private int intNivCtaPadAct, intNivCtaPadPas;
    private int intCodCtaPedAct, intCodCtaPedPas;    
    private int intCodCtaActCreNotPed, intCodCtaPasCreNotPed;
    private int intCodLocPre;
    private int intCodCtaActMerTra_Pro;
    private int intCodCtaPasMerTra_Pro;
    private int intNumPedEmbDia;    
    private String strSQL, strAux;
    private String strTipNotPed;
    private String strCodCtaPadPedAct, strCodCtaPadPedPas;
    private String strNumCtaPedAct, strNumCtaPedPas;
    private String strNomCtaPedAct, strNomCtaPedPas;
    private String strEstNotPedCer, strSQLUpdNotPedCie;
    private String strCodImpAnt;
    private String strCodImp, strNomImp;
    private String strCodExp, strNomExp;
    private String strCodPto, strNomPto;
    private String strCodSeg, strNomSeg;
    private String strCodPag, strNomPag;
    private String strNumDoc, strMonDoc;
    private String strDesCorTipDoc, strDesLarTipDoc, strTipDocNecAutAnu;
    private String strCodLocPreNotPed, strCodLocPrePedEmb;
    
    private BigDecimal bgdSumValTotFobCfrDb;
    private String strCodEmpNotPed="", strCodLocNotPed="", strCodTipDocNotPed="", strCodDocNotPed="", strNumDocNotPed="", strCodDocAjuNotPedPre="", strNumDocAjuNotPedPre="";
    private String strCodEmpNotPed_antAsiDia="", strCodLocNotPed_antAsiDia="", strCodTipDocNotPed_antAsiDia="", strCodDocNotPed_antAsiDia="";
    private String strCodEmpNotPed_nueAsiDia="", strCodLocNotPed_nueAsiDia="", strCodTipDocNotPed_nueAsiDia="", strCodDocNotPed_nueAsiDia="", strNumDocNotPed_nueAsiDia="";
    
    private int intCodEmpPedEmbOri, intCodLocPedEmbOri, intCodTipDocPedEmbOri, intCodDocPedEmbOri;
    private String strEstDocOri;//Indica si el documento fue inactivado, es de reversion o es el nuevo documento
    private String strEstFacEmb;
    
    private String strVer=" v0.12.7";  
    private String strGenAsiDiaRevNotPed;
    
    private String strCodEmpAsiDiaAjuNotPed;
    private String strCodLocAsiDiaAjuNotPed;
    private String strCodImpNotPed, strCodImpPedEmb;
    
    private int intCodEmpCon, intCodLocCon;
    
    
    //Provienen de ZafImp36 - luego mejorar
    //private ArrayList arlRegInsPedCar, arlDatInsPedCar;//esto no, solo los indices
    public static final int INT_ARL_INS_PED_CAR_COD_EMP=0;
    public static final int INT_ARL_INS_PED_CAR_COD_LOC=1;
    public static final int INT_ARL_INS_PED_CAR_COD_TIP_DOC=2;
    public static final int INT_ARL_INS_PED_CAR_COD_DOC=3;
    public static final int INT_ARL_INS_PED_CAR_COD_CAR=4;
    public static final int INT_ARL_INS_PED_CAR_TIP_CAR=5;
    public static final int INT_ARL_INS_PED_CAR_VAL_CAR=6;
    
    private ZafHisTblBasDat objHisTblBasDat;                                    //Histórico    
    private String strPerIngRevNue;
    private boolean blnGenAsiDiaNueNP;
    
    private boolean blnCiePedRef;
    private boolean blnCnvPedFacEmb;
    
    
    private int intCodImpOri;

    /** Creates new form ZafImp33 */
    public ZafImp33(ZafParSis obj) 
    {
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatCtaDeb=new ArrayList();
            arlDatCtaHab=new ArrayList();
            strPerIngRevNue="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                configurarFrm();
                agregarDocLis();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    public ZafImp33(ZafParSis obj, int codigoTipoDocumento, int codigoDocumento) 
    {
        try{
            blnVenConEme=true;           //Se esta llamando desde otro programa            
            objParSis=(ZafParSis)obj.clone();
            arlDatCtaDeb=new ArrayList();
            arlDatCtaHab=new ArrayList();
            strPerIngRevNue="";
            initComponents();
            configurarFrm();
            agregarDocLis();
            
            lblTit.setText("Pedido Embarcado");
            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objTooBar.setEstado('c');
            objTooBar.beforeConsultar();
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /**
     * Constructor para Pagos de Impuestos.
     * Se envia la empresa y el local para que consulte con toda la pk el pedido embarcado.
     * @param obj
     * @param codigoEmpresa
     * @param codigoLocal
     * @param codigoTipoDocumento
     * @param codigoDocumento 
     */
    public ZafImp33(ZafParSis obj, Connection conexionExt, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento) 
    {
        try{
            blnVenConEme=true;//Se esta llamando desde otro programa
            blnUpdatePedEmbAutPag=false; //Por defecto no se podrá realizar la actualización del pedido embarcado, primero debe pasar todas las validaciones.
            objParSis=(ZafParSis)obj.clone();
            arlDatCtaDeb=new ArrayList();
            arlDatCtaHab=new ArrayList();
            strPerIngRevNue="";
            conExt=conexionExt;
            initComponents();
            if(configurarFrm())
            {
                lblTit.setText("Pedido Embarcado");
                agregarDocLis();
                objParSis.setCodigoEmpresa(codigoEmpresa);
                objParSis.setCodigoLocal(codigoLocal);
                txtCodTipDoc.setText(""+codigoTipoDocumento);
                txtCodDoc.setText(""+codigoDocumento);
                objTooBar.setEstado('c');
                if(objTooBar.beforeConsultar()){
                    if(objTooBar.consultar()){
                        if(objTooBar.afterConsultar()){
                            blnUpdatePedEmbAutPag=true;
                            objTooBar.setEstado('w');
                            objTooBar.setVisible(false);    
                        }
                    }
                }
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    
    
    /**
     * Constructor para Pedidos consolidados.
     * Se envia la empresa y el local para que consulte con toda la pk el pedido embarcado.
     * @param obj
     * @param codigoEmpresa
     * @param codigoLocal
     * @param codigoTipoDocumento
     * @param codigoDocumento 
     * @param codigoMnu 
     *//*javax.swing.JInternalFrame parent*/
    public ZafImp33(ZafParSis obj, Connection conexionExt, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMnu) 
    {
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatCtaDeb=new ArrayList();
            arlDatCtaHab=new ArrayList();
            objParSis.setCodigoMenu(codigoMnu);
            strPerIngRevNue="";
            //conExt=conexionExt;
            initComponents();
            if(configurarFrm()){
                agregarDocLis();
                
                txtCodTipDoc.setText(""+codigoTipoDocumento);
                txtCodDoc.setText(""+codigoDocumento);
                
                conExt=conexionExt;
                
                objTooBar.setEstado('c');

                //System.out.println("NUMERO: " + txtNumPedOri.getText());
                                
                objTooBar.clickConsultar();
                objTooBar.consultar();
                objTooBar.afterConsultar();
                objTooBar.setEstado('w');
                objTooBar.setVisible(false);
                intCodEmpCon=objParSis.getCodigoEmpresa();
                intCodLocCon=objParSis.getCodigoLocal();                
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    /**
     * Constructor para Pedidos consolidados.
     * Se envia la empresa y el local para que consulte con toda la pk el pedido embarcado.
     * @param obj
     * @param codigoEmpresa
     * @param codigoLocal
     * @param codigoTipoDocumento
     * @param codigoDocumento 
     * @param codigoMnu 
     *//*javax.swing.JInternalFrame parent*/
    public ZafImp33(ZafParSis obj, Connection conexionExt, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMnu, Boolean estadoVersion, String numeroPedido, int numeroVersion, String numeroPedidoVersion, String tipoOpeRev_Nue)
    {
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatCtaDeb=new ArrayList();
            arlDatCtaHab=new ArrayList();
            objParSis.setCodigoMenu(codigoMnu);
            strPerIngRevNue=tipoOpeRev_Nue;//Puede contener  R o N
            //conExt=conexionExt;
            initComponents();
            if(configurarFrm()){
                agregarDocLis();
                
                txtCodTipDoc.setText(""+codigoTipoDocumento);
                txtCodDoc.setText(""+codigoDocumento);
                chkMosTodVerPed.setSelected(estadoVersion);
                //chkCerPedEmb.setSelected(blnCiePedEmbCon);
                
                if(chkMosTodVerPed.isSelected())
                    System.out.println("SI esta marcado");
                else
                    System.out.println("NO esta marcado");
                
                conExt=conexionExt;
                
                objTooBar.setEstado('c');

                
                objTooBar.clickConsultar();
                objTooBar.consultar();
                objTooBar.afterConsultar();
                objTooBar.setEstado('w');
                objTooBar.setVisible(false);
                intCodEmpCon=objParSis.getCodigoEmpresa();
                intCodLocCon=objParSis.getCodigoLocal();
                
                
                System.out.println("txtNumPedOri: " + numeroPedido);
                txtNumPedOri.setText(numeroPedido);
                System.out.println("txtVerPed: " + (numeroVersion));
                txtVerPed.setText(""+(numeroVersion+1));
                System.out.println("txtVerNumPed: " + numeroPedidoVersion);
                txtVerNumPed.setText(numeroPedidoVersion);
                
//                //Cierre de Pedido Embarcado
//                chkCerPedEmb.setSelected(blnCiePedEmbCon);
//                if(chkCerPedEmb.isSelected())
//                    System.out.println("blnCiePedEmbCon-false:" + blnCiePedEmbCon);
//                else
//                    System.out.println("blnCiePedEmbCon-true:" + blnCiePedEmbCon);
                
//                //Pedido Facturado a Embarcado
//                chkEstPedEmb.setSelected(blnPedFac_Emb_Con);
//                if(chkEstPedEmb.isSelected())
//                    System.out.println("blnPedFac_Emb_Con-false:" + blnPedFac_Emb_Con);
//                else
//                    System.out.println("blnPedFac_Emb_Con-true:" + blnPedFac_Emb_Con);
                
                        
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    
    
    
    
    

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        
        try{
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            strEstFacEmb="";
            this.setTitle(strAux + strVer + strEstFacEmb);
            lblTit.setText(strAux + strEstFacEmb);
            
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            objHisTblBasDat=new ZafHisTblBasDat();

            
            objPerUsr=new ZafPerUsr(objParSis);
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objSegMovInv=new ZafSegMovInv(objParSis, this);                
            
            //ToolBar
            panBar.add(objTooBar);
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butConNotPedLis);

            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabPed.add(dtpFecDoc);
            dtpFecDoc.setBounds(590, 4, 90, 20);
            
            //Configurar ZafDatePicker:
            dtpFecColPed=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecColPed.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecColPed.setText("");
            panCabPed.add(dtpFecColPed);
            dtpFecColPed.setBounds(590, 24, 90, 20);        
            dtpFecColPed.setEnabled(false);

            //Configurar ZafDatePicker:
            dtpFecEmb=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecEmb.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecEmb.setText("");
            panCabPed.add(dtpFecEmb);
            dtpFecEmb.setBounds(590, 44, 90, 20);
            
            //Configurar ZafDatePicker:
            dtpFecPue=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecPue.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecPue.setText("");
            panCabPed.add(dtpFecPue);
            dtpFecPue.setBounds(590, 64, 90, 20);
            
            //Configurar ZafDatePicker:
            dtpFecArr=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecArr.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecArr.setText("");
            panCabPed.add(dtpFecArr);
            dtpFecArr.setBounds(590, 84, 90, 20);            
            
            /*Libreria para enviar correos electronicos a usuario.*/
            objNotCorEle = new ZafNotCorEle(objParSis);            
            
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodImp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomImp.setBackground(objParSis.getColorCamposObligatorios());
            txtCodExp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomExp2.setBackground(objParSis.getColorCamposObligatorios());
            txtNomExp.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecColPed.setBackground(objParSis.getColorCamposSistema());
            dtpFecEmb.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecPue.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecArr.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumPedOri.setBackground(objParSis.getColorCamposObligatorios());
            txtVerNumPed.setBackground(objParSis.getColorCamposSistema());
            txtVerPed.setBackground(objParSis.getColorCamposSistema());
            txtNumFacAdu.setBackground(objParSis.getColorCamposObligatorios());
            txtDiaCre.setBackground(objParSis.getColorCamposObligatorios());
            txtEnvDes.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtValFacConIva.setBackground(objParSis.getColorCamposSistema());
            txtPesTotKgr.setBackground(objParSis.getColorCamposSistema());
            txtNumCon.setBackground(objParSis.getColorCamposSistema());
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodExp.setVisible(false);
            txtCodExp.setEditable(false);
            txtVerPed.setEditable(false);
            
            //Asiento de Diario
            objAsiDiaPE=new ZafAsiDia(objParSis);
            objAsiDiaPE.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDiaPE.setCodigoTipoDocumento(-1);
                    else
                        objAsiDiaPE.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaNorIns.add(objAsiDiaPE,java.awt.BorderLayout.CENTER);
            
            //Asiento de Diario     
            objAsiDiaPro=new ZafAsiDia(objParSis);
            objAsiDiaPro.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDiaPro.setCodigoTipoDocumento(-1);
                    else
                        objAsiDiaPro.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaNorPro.add(objAsiDiaPro,java.awt.BorderLayout.CENTER);            
            
            //Reversión NP
            objAsiDiaAjuNP=new ZafAsiDia(objParSis);
            objAsiDiaAjuNP.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDiaAjuNP.setCodigoTipoDocumento(-1);
                    else
                        objAsiDiaAjuNP.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaCenAjuNP.add(objAsiDiaAjuNP,java.awt.BorderLayout.CENTER);
            
            //Reversión PE
            objAsiDiaAjuPE=new ZafAsiDia(objParSis);
            objAsiDiaAjuPE.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDiaAjuPE.setCodigoTipoDocumento(-1);
                    else
                        objAsiDiaAjuPE.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaCenAjuPE.add(objAsiDiaAjuPE,java.awt.BorderLayout.CENTER);
                    
            //Nuevo NP
            objAsiDiaNueNP=new ZafAsiDia(objParSis);
            objAsiDiaNueNP.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDiaNueNP.setCodigoTipoDocumento(-1);
                    else
                        objAsiDiaNueNP.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaCenAjuNP.add(objAsiDiaNueNP,java.awt.BorderLayout.CENTER); //OJO
            
            //Configurar Ventanas de COnsulta
            configurarVenConTipDoc();
            configurarVenConImp();
            configurarVenConPrv();
            configurarVenConExp();
            configurarVenConPto();
            configurarVenConSeg();
            configurarVenConForPag();

            //tabla de cargos a pagar
            objImp33_01=new ZafImp33_01(objParSis);
            panCarPagImp.add(objImp33_01,java.awt.BorderLayout.CENTER);

            //tabla de items y sus valores
            objImp33_02=new ZafImp33_02(objParSis, objImp33_01);
            panDetPagImp.add(objImp33_02,java.awt.BorderLayout.CENTER);
            objImp33_01.setObjectoImp33_02(objImp33_02);

            dtpFecDoc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    //txtDesCorTipDocFocusLost(evt);
                    objImp33_02.setFechaDocumento(dtpFecDoc.getText());
                }
            });

            dtpFecEmb.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    objImp33_02.setFechaEmbarque(dtpFecEmb.getText());
                }
            });

            vecDat=new Vector();    //Almacena los datos

            objImp33_02.addImp33_02Listener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    //System.out.println("beforeInsertar");
                }
                public void afterInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    //System.out.println("afterInsertar");
                }
                public void beforeEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    //System.out.println("beforeEditarCelda");
                }
                public void afterEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    //System.out.println("afterEditarCelda");
                    txtValDoc.setText("" + objImp33_02.getValorTotalCosto());
                    txtValFacConIva.setText("" + objImp33_02.getValorTotalFacturaConIva());
                    txtPesTotKgr.setText("" + objImp33_02.getCalcularPesoTotal());

                    //cuando se cambia la cantidad de embarque, se debe refresar el arraylist que contiene los datos en ZafImp33 para enviarselos
                    //a ZafImp33_03
                }
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    //System.out.println("beforeConsultarCuentas");
                }
                public void afterConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    //System.out.println("afterConsultarCuentas");
                }
            });
            
            butCorEle.setVisible(false);
            butGenPro.setVisible(false);
            if(objParSis.getCodigoUsuario()==1){
                txtNumCorEle.setVisible(true);
                butCorEle.setVisible(true);
                butGenPro.setVisible(true);
            }            

            bgdSumValTotFobCfrDb=BigDecimal.ZERO;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }

                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strTipDocNecAutAnu=rst.getString("st_necautanudoc");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+="       ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+="       ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_ruc");
            arlCam.add("a2.tx_dir");
            arlCam.add("a2.co_loc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód. Emp.");
            arlAli.add("Nombre");
            arlAli.add("Ruc");
            arlAli.add("Dirección");
            arlAli.add("Local");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("130");
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("80");

            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
            strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_loc AS a2 ON a1.co_emp=a2.co_emp";
            strSQL+=" WHERE a1.st_reg NOT IN('I','E') AND a1.co_emp<>" + objParSis.getCodigoEmpresa() + " AND a2.st_reg='P'";
            strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
            strSQL+=" ORDER BY a1.co_emp";
            vcoImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConImp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoImp.setCampoBusqueda(2);
                    vcoImp.show();
                    if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                        strCodLocPrePedEmb=vcoImp.getValueAt(5);
                        strCodImpPedEmb=vcoImp.getValueAt(1);
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoImp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                        strCodLocPrePedEmb=vcoImp.getValueAt(5);
                        strCodImpPedEmb=vcoImp.getValueAt(1);
                    }
                    else{
                        vcoImp.setCampoBusqueda(0);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                            strCodLocPrePedEmb=vcoImp.getValueAt(5);
                            strCodImpPedEmb=vcoImp.getValueAt(1);
                        }
                        else{
                            txtCodImp.setText(strCodPag);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoImp.buscar("a1.tx_nom", txtNomImp.getText())){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                        strCodLocPrePedEmb=vcoImp.getValueAt(5);
                        strCodImpPedEmb=vcoImp.getValueAt(1);
                    }
                    else{
                        vcoImp.setCampoBusqueda(2);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                            strCodLocPrePedEmb=vcoImp.getValueAt(5);
                            strCodImpPedEmb=vcoImp.getValueAt(1);
                        }
                        else{
                            txtNomImp.setText(strNomPag);
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

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_prv");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_prv, a1.tx_nom";
            strSQL+=" FROM tbm_prvExtImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Exportadores".
     */
    private boolean configurarVenConExp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_nom2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Nombre alterno");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300");
            arlAncCol.add("150");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            vcoExp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de exportadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoExp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        String strMsg="<HTML>¿Está seguro que desea actualizar el exportador en el detalle de los ítems?</HTML>";
        try{
            if(txtCodImp.getText().length()>0){

                strSQLTmp="";
                strSQLTmp+=" ORDER BY a1.tx_nom";
                vcoExp.setCondicionesSQL(strSQLTmp);
                
                switch (intTipBus){
                    case 0: //Mostrar la ventana de consulta.
                        vcoExp.setCampoBusqueda(1);
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                            txtNomExp2.setText(vcoExp.getValueAt(3));
                            
                            if(mostrarMsgYesNo(strMsg)==0){ //Si
                                objImp33_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                            }                            
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));                            
                            txtNomExp.setText(vcoExp.getValueAt(2));
                            txtNomExp2.setText(vcoExp.getValueAt(3));
                            if(mostrarMsgYesNo(strMsg)==0){ //Si
                                objImp33_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                            }                            
                        }
                        else
                        {
                            vcoExp.setCampoBusqueda(0);
                            vcoExp.setCriterio1(11);
                            vcoExp.cargarDatos();
                            vcoExp.show();
                            if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                            {
                                txtCodExp.setText(vcoExp.getValueAt(1));
                                txtNomExp.setText(vcoExp.getValueAt(2));
                                txtNomExp2.setText(vcoExp.getValueAt(3));
                                if(mostrarMsgYesNo(strMsg)==0){ //Si
                                    objImp33_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                                }                                
                            }
                            else
                            {
                                txtCodExp.setText(strCodExp);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoExp.buscar("a1.tx_nom", txtNomExp2.getText()))
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                            txtNomExp2.setText(vcoExp.getValueAt(3));
                            if(mostrarMsgYesNo(strMsg)==0){ //Si
                                objImp33_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                            }                            
                        }
                        else
                        {
                            vcoExp.setCampoBusqueda(2);
                            vcoExp.setCriterio1(11);
                            vcoExp.cargarDatos();
                            vcoExp.show();
                            if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                            {
                                txtCodExp.setText(vcoExp.getValueAt(1));
                                txtNomExp.setText(vcoExp.getValueAt(2));
                                txtNomExp2.setText(vcoExp.getValueAt(3));
                                if(mostrarMsgYesNo(strMsg)==0){ //Si
                                    objImp33_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                                }                                
                            }
                            else
                            {
                                txtNomExp.setText(strNomExp);
                            }
                        }
                        break;
                }
            }
             else{
                mostrarMsgInf("Se debe seleccionar el Importador previamente.");
             }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Puerto".
     */
    private boolean configurarVenConPto()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_ciupueemb");
            arlCam.add("tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_ciu AS co_ciupueemb, a1.tx_desLar";
            strSQL+=" FROM tbm_ciu AS a1 LEFT OUTER JOIN tbm_expImp AS a2";
            strSQL+=" ON a1.co_ciu=a2.co_ciu AND a2.st_reg='A'";
            strSQL+=" WHERE a1.st_reg='A' ";
            strSQL+=" ORDER BY a1.tx_desLar";

            vcoPto=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de puertos de embarque", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPto.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPto(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPto.setCampoBusqueda(2);
                    vcoPto.show();
                    if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                        txtCodPue.setText(vcoPto.getValueAt(1));
                        txtNomPue.setText(vcoPto.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoPto.buscar("a1.co_ciupueemb", txtCodPue.getText())){
                        txtCodPue.setText(vcoPto.getValueAt(1));
                        txtNomPue.setText(vcoPto.getValueAt(2));
                    }
                    else{
                        vcoPto.setCampoBusqueda(0);
                        vcoPto.setCriterio1(11);
                        vcoPto.cargarDatos();
                        vcoPto.show();
                        if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                            txtCodPue.setText(vcoPto.getValueAt(1));
                            txtNomPue.setText(vcoPto.getValueAt(2));
                        }
                        else{
                            txtCodPue.setText(strCodPto);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoPto.buscar("a1.tx_desLar", txtNomPue.getText())){
                        txtCodPue.setText(vcoPto.getValueAt(1));
                        txtNomPue.setText(vcoPto.getValueAt(2));
                    }
                    else{
                        vcoPto.setCampoBusqueda(2);
                        vcoPto.setCriterio1(11);
                        vcoPto.cargarDatos();
                        vcoPto.show();
                        if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                            txtCodPue.setText(vcoPto.getValueAt(1));
                            txtNomPue.setText(vcoPto.getValueAt(2));
                        }
                        else{
                            txtNomPue.setText(strNomPto);
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


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConSeg()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_seg");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_seg, a1.tx_desLar";
            strSQL+=" FROM tbm_segImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_desLar";
            vcoSeg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de seguros", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoSeg.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConSeg(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoSeg.setCampoBusqueda(2);
                    vcoSeg.show();
                    if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoSeg.buscar("a1.co_seg", txtCodSeg.getText())){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    else{
                        vcoSeg.setCampoBusqueda(0);
                        vcoSeg.setCriterio1(11);
                        vcoSeg.cargarDatos();
                        vcoSeg.show();
                        if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                            txtCodSeg.setText(vcoSeg.getValueAt(1));
                            txtNomSeg.setText(vcoSeg.getValueAt(2));
                        }
                        else{
                            txtCodSeg.setText(strCodSeg);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoSeg.buscar("a1.tx_desLar", txtNomSeg.getText())){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    else{
                        vcoSeg.setCampoBusqueda(2);
                        vcoSeg.setCriterio1(11);
                        vcoSeg.cargarDatos();
                        vcoSeg.show();
                        if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                            txtCodSeg.setText(vcoSeg.getValueAt(1));
                            txtNomSeg.setText(vcoSeg.getValueAt(2));
                        }
                        else{
                            txtNomSeg.setText(strNomSeg);
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

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forPag");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_forPag, a1.tx_desLar";
            strSQL+=" FROM tbm_forPagImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_desLar";
            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de forma de pago", strSQL, arlCam, arlAli, arlAncCol);
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

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPag(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoForPag.setCampoBusqueda(2);
                    vcoForPag.show();
                    if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText())){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else{
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else{
                            txtCodForPag.setText(strCodPag);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoForPag.buscar("a1.tx_desLar", txtNomForPag.getText())){
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else{
                        vcoForPag.setCampoBusqueda(2);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE){
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else{
                            txtNomForPag.setText(strNomPag);
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
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodImp.getDocument().addDocumentListener(objDocLis);
        txtNomImp.getDocument().addDocumentListener(objDocLis);
        txtCodExp.getDocument().addDocumentListener(objDocLis);
        txtNomExp2.getDocument().addDocumentListener(objDocLis);
        txtEmpExp.getDocument().addDocumentListener(objDocLis);
        txtCodPue.getDocument().addDocumentListener(objDocLis);
        txtNomPue.getDocument().addDocumentListener(objDocLis);
        txtCodSeg.getDocument().addDocumentListener(objDocLis);
        txtNomSeg.getDocument().addDocumentListener(objDocLis);
        txtCodForPag.getDocument().addDocumentListener(objDocLis);
        txtNomForPag.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtValFacConIva.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtPesTotKgr.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        panCabPed = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblNomImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        txtNomExp2 = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        lblEmpExp = new javax.swing.JLabel();
        txtEmpExp = new javax.swing.JTextField();
        lblPto = new javax.swing.JLabel();
        txtCodPue = new javax.swing.JTextField();
        txtNomPue = new javax.swing.JTextField();
        butPue = new javax.swing.JButton();
        lblSeg = new javax.swing.JLabel();
        txtCodSeg = new javax.swing.JTextField();
        txtNomSeg = new javax.swing.JTextField();
        butSeg = new javax.swing.JButton();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtNomForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblFecEmb = new javax.swing.JLabel();
        lblFecPue = new javax.swing.JLabel();
        lblFecArr = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblVerNumPed = new javax.swing.JLabel();
        txtVerNumPed = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        lblPesTotKgr = new javax.swing.JLabel();
        txtPesTotKgr = new javax.swing.JTextField();
        lblValFacConIva = new javax.swing.JLabel();
        txtValFacConIva = new javax.swing.JTextField();
        butConNotPedLis = new javax.swing.JButton();
        optTmFob = new javax.swing.JRadioButton();
        optTmCfr = new javax.swing.JRadioButton();
        optPzaFob = new javax.swing.JRadioButton();
        optPzaCfr = new javax.swing.JRadioButton();
        chkCreCta = new javax.swing.JCheckBox();
        chkModSolFecEmbArr = new javax.swing.JCheckBox();
        chkCerNotPed = new javax.swing.JCheckBox();
        chkFleApr = new javax.swing.JCheckBox();
        chkCerPedEmb = new javax.swing.JCheckBox();
        txtNumCorEle = new javax.swing.JTextField();
        butCorEle = new javax.swing.JButton();
        txtVerPed = new javax.swing.JTextField();
        txtNumPedOri = new javax.swing.JTextField();
        chkEstPedEmb = new javax.swing.JCheckBox();
        chkEmbTot = new javax.swing.JCheckBox();
        lblFecColPed = new javax.swing.JLabel();
        lblNumPed = new javax.swing.JLabel();
        butGenPro = new javax.swing.JButton();
        chkMosTodVerPed = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        txtNumCon = new javax.swing.JTextField();
        panDatFac = new javax.swing.JPanel();
        lblNumFacAdu = new javax.swing.JLabel();
        txtNumFacAdu = new javax.swing.JTextField();
        lblEnvDes = new javax.swing.JLabel();
        txtEnvDes = new javax.swing.JTextField();
        lblDiaCre = new javax.swing.JLabel();
        txtDiaCre = new javax.swing.JTextField();
        lblForEnv = new javax.swing.JLabel();
        cboForEnv = new javax.swing.JComboBox();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JSplitPane();
        panCarPagImp = new javax.swing.JPanel();
        panDetPagImp = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panAsiDiaNor = new javax.swing.JPanel();
        panAsiDiaNorIns = new javax.swing.JPanel();
        panAsiDiaNorPro = new javax.swing.JPanel();
        panAsiDiaCen = new javax.swing.JPanel();
        panAsiDiaCenAjuNP = new javax.swing.JPanel();
        panAsiDiaCenAjuPE = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

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
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);

        panGen.setAutoscrolls(true);
        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(100, 300));
        panCab.setLayout(new java.awt.BorderLayout());

        panCabPed.setPreferredSize(new java.awt.Dimension(0, 208));
        panCabPed.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabPed.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 100, 20);
        panCabPed.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 4, 20, 20);

        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panCabPed.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(110, 4, 70, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panCabPed.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(180, 4, 258, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabPed.add(butTipDoc);
        butTipDoc.setBounds(438, 4, 20, 20);

        lblNomImp.setText("Importador:");
        lblNomImp.setToolTipText("Cuenta");
        panCabPed.add(lblNomImp);
        lblNomImp.setBounds(4, 24, 90, 20);

        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        panCabPed.add(txtCodImp);
        txtCodImp.setBounds(110, 24, 70, 20);

        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomImp);
        txtNomImp.setBounds(180, 24, 258, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panCabPed.add(butImp);
        butImp.setBounds(438, 24, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Proveedor");
        panCabPed.add(lblExp);
        lblExp.setBounds(4, 44, 80, 20);

        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodExp);
        txtCodExp.setBounds(90, 44, 20, 20);
        panCabPed.add(txtNomExp);
        txtNomExp.setBounds(110, 44, 70, 20);

        txtNomExp2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExp2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExp2FocusLost(evt);
            }
        });
        txtNomExp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExp2ActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomExp2);
        txtNomExp2.setBounds(180, 44, 258, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panCabPed.add(butExp);
        butExp.setBounds(438, 44, 20, 20);

        lblEmpExp.setText("Empaque:");
        lblEmpExp.setToolTipText("Código del documento");
        panCabPed.add(lblEmpExp);
        lblEmpExp.setBounds(4, 64, 110, 20);
        panCabPed.add(txtEmpExp);
        txtEmpExp.setBounds(110, 64, 328, 20);

        lblPto.setText("Puerto:");
        lblPto.setToolTipText("Proveedor");
        panCabPed.add(lblPto);
        lblPto.setBounds(4, 84, 100, 20);

        txtCodPue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPueFocusLost(evt);
            }
        });
        txtCodPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPueActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodPue);
        txtCodPue.setBounds(110, 84, 70, 20);

        txtNomPue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPueFocusLost(evt);
            }
        });
        txtNomPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPueActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomPue);
        txtNomPue.setBounds(180, 84, 258, 20);

        butPue.setText("...");
        butPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPueActionPerformed(evt);
            }
        });
        panCabPed.add(butPue);
        butPue.setBounds(438, 84, 20, 20);

        lblSeg.setText("Seguro:");
        lblSeg.setToolTipText("Proveedor");
        panCabPed.add(lblSeg);
        lblSeg.setBounds(4, 104, 100, 20);

        txtCodSeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodSegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSegFocusLost(evt);
            }
        });
        txtCodSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSegActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodSeg);
        txtCodSeg.setBounds(110, 104, 70, 20);

        txtNomSeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomSegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomSegFocusLost(evt);
            }
        });
        txtNomSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomSegActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomSeg);
        txtNomSeg.setBounds(180, 104, 258, 20);

        butSeg.setText("...");
        butSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSegActionPerformed(evt);
            }
        });
        panCabPed.add(butSeg);
        butSeg.setBounds(438, 104, 20, 20);

        lblForPag.setText("Pago:");
        lblForPag.setToolTipText("Proveedor");
        panCabPed.add(lblForPag);
        lblForPag.setBounds(4, 124, 100, 20);

        txtCodForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusLost(evt);
            }
        });
        txtCodForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodForPagActionPerformed(evt);
            }
        });
        panCabPed.add(txtCodForPag);
        txtCodForPag.setBounds(110, 124, 70, 20);

        txtNomForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusLost(evt);
            }
        });
        txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomForPagActionPerformed(evt);
            }
        });
        panCabPed.add(txtNomForPag);
        txtNomForPag.setBounds(180, 124, 258, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panCabPed.add(butForPag);
        butForPag.setBounds(438, 124, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabPed.add(lblFecDoc);
        lblFecDoc.setBounds(460, 4, 130, 20);

        lblFecEmb.setText("Fecha de embarque:");
        lblFecEmb.setToolTipText("Número alterno 1");
        panCabPed.add(lblFecEmb);
        lblFecEmb.setBounds(460, 44, 130, 20);

        lblFecPue.setText("Fecha de puerto:");
        lblFecPue.setToolTipText("Número alterno 1");
        panCabPed.add(lblFecPue);
        lblFecPue.setBounds(460, 64, 130, 20);

        lblFecArr.setText("Fecha de arribo:");
        lblFecArr.setToolTipText("Número alterno 1");
        panCabPed.add(lblFecArr);
        lblFecArr.setBounds(460, 84, 130, 20);

        lblNumDoc.setText("Número de documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCabPed.add(lblNumDoc);
        lblNumDoc.setBounds(460, 104, 130, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panCabPed.add(txtNumDoc);
        txtNumDoc.setBounds(590, 104, 90, 20);

        lblVerNumPed.setText("Versión número pedido:");
        lblVerNumPed.setToolTipText("Versión del número de pedido");
        panCabPed.add(lblVerNumPed);
        lblVerNumPed.setBounds(460, 144, 130, 20);

        txtVerNumPed.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtVerNumPed.setToolTipText("Versión del número de pedido");
        panCabPed.add(txtVerNumPed);
        txtVerNumPed.setBounds(590, 144, 65, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabPed.add(lblCodDoc);
        lblCodDoc.setBounds(4, 146, 120, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabPed.add(txtCodDoc);
        txtCodDoc.setBounds(110, 144, 70, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Valor del documento");
        panCabPed.add(lblValDoc);
        lblValDoc.setBounds(460, 164, 130, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValDocFocusLost(evt);
            }
        });
        txtValDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValDocActionPerformed(evt);
            }
        });
        panCabPed.add(txtValDoc);
        txtValDoc.setBounds(590, 164, 90, 20);

        lblPesTotKgr.setText("Peso(kg):");
        panCabPed.add(lblPesTotKgr);
        lblPesTotKgr.setBounds(460, 204, 50, 20);

        txtPesTotKgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabPed.add(txtPesTotKgr);
        txtPesTotKgr.setBounds(590, 204, 92, 20);

        lblValFacConIva.setText("Valor Factura con IVA:");
        panCabPed.add(lblValFacConIva);
        lblValFacConIva.setBounds(460, 184, 130, 20);

        txtValFacConIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabPed.add(txtValFacConIva);
        txtValFacConIva.setBounds(590, 184, 90, 20);

        butConNotPedLis.setText("Nota de Pedido");
        butConNotPedLis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConNotPedLisActionPerformed(evt);
            }
        });
        panCabPed.add(butConNotPedLis);
        butConNotPedLis.setBounds(560, 60, 120, 20);

        optTmFob.setText("TM FOB");
        optTmFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmFobActionPerformed(evt);
            }
        });
        panCabPed.add(optTmFob);
        optTmFob.setBounds(2, 166, 74, 14);

        optTmCfr.setText("TM CFR");
        optTmCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmCfrActionPerformed(evt);
            }
        });
        panCabPed.add(optTmCfr);
        optTmCfr.setBounds(76, 166, 74, 14);

        optPzaFob.setText("PZA FOB");
        optPzaFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaFobActionPerformed(evt);
            }
        });
        panCabPed.add(optPzaFob);
        optPzaFob.setBounds(150, 166, 74, 14);

        optPzaCfr.setText("PZA CFR");
        optPzaCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaCfrActionPerformed(evt);
            }
        });
        panCabPed.add(optPzaCfr);
        optPzaCfr.setBounds(230, 166, 74, 14);

        chkCreCta.setText("Crear cuentas");
        panCabPed.add(chkCreCta);
        chkCreCta.setBounds(340, 148, 106, 14);

        chkModSolFecEmbArr.setText("Modificar sólo fecha embarque y arribo");
        panCabPed.add(chkModSolFecEmbArr);
        chkModSolFecEmbArr.setBounds(0, 184, 270, 14);

        chkCerNotPed.setText("Cerrar Nota de Pedido");
        panCabPed.add(chkCerNotPed);
        chkCerNotPed.setBounds(270, 184, 170, 14);

        chkFleApr.setText("Cambiar sólo el valor del flete aproximado");
        panCabPed.add(chkFleApr);
        chkFleApr.setBounds(0, 198, 270, 14);

        chkCerPedEmb.setText("Cerrar Pedido Embarcado");
        panCabPed.add(chkCerPedEmb);
        chkCerPedEmb.setBounds(270, 198, 180, 14);
        panCabPed.add(txtNumCorEle);
        txtNumCorEle.setBounds(410, 212, 18, 20);

        butCorEle.setText("CorEle");
        butCorEle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCorEleActionPerformed(evt);
            }
        });
        panCabPed.add(butCorEle);
        butCorEle.setBounds(340, 220, 70, 20);
        panCabPed.add(txtVerPed);
        txtVerPed.setBounds(655, 144, 25, 20);

        txtNumPedOri.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumPedOri.setToolTipText("Número de Pedido Original");
        panCabPed.add(txtNumPedOri);
        txtNumPedOri.setBounds(590, 124, 90, 20);

        chkEstPedEmb.setText("Estado de Pedido Embarcado");
        chkEstPedEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEstPedEmbActionPerformed(evt);
            }
        });
        panCabPed.add(chkEstPedEmb);
        chkEstPedEmb.setBounds(0, 212, 220, 14);

        chkEmbTot.setText("Embarque Total");
        panCabPed.add(chkEmbTot);
        chkEmbTot.setBounds(270, 212, 170, 14);

        lblFecColPed.setText("Fecha colocación pedido:");
        lblFecColPed.setToolTipText("Fecha del documento");
        panCabPed.add(lblFecColPed);
        lblFecColPed.setBounds(458, 24, 136, 20);

        lblNumPed.setText("Número de pedido:");
        lblNumPed.setToolTipText("Número de pedido");
        panCabPed.add(lblNumPed);
        lblNumPed.setBounds(460, 124, 130, 20);

        butGenPro.setText("Provision");
        butGenPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGenProActionPerformed(evt);
            }
        });
        panCabPed.add(butGenPro);
        butGenPro.setBounds(230, 220, 90, 20);

        chkMosTodVerPed.setText("Mostrar \"Todas las versiones del pedido\".");
        panCabPed.add(chkMosTodVerPed);
        chkMosTodVerPed.setBounds(0, 226, 270, 14);

        jLabel1.setText("Consolidado:");
        panCabPed.add(jLabel1);
        jLabel1.setBounds(460, 226, 80, 14);
        panCabPed.add(txtNumCon);
        txtNumCon.setBounds(590, 224, 90, 20);

        panCab.add(panCabPed, java.awt.BorderLayout.CENTER);

        panDatFac.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Factura"));
        panDatFac.setPreferredSize(new java.awt.Dimension(100, 54));
        panDatFac.setLayout(null);

        lblNumFacAdu.setText("Número factura Aduana:");
        panDatFac.add(lblNumFacAdu);
        lblNumFacAdu.setBounds(24, 14, 129, 14);
        panDatFac.add(txtNumFacAdu);
        txtNumFacAdu.setBounds(170, 10, 150, 20);

        lblEnvDes.setText("Envío desde:");
        panDatFac.add(lblEnvDes);
        lblEnvDes.setBounds(24, 30, 72, 14);
        panDatFac.add(txtEnvDes);
        txtEnvDes.setBounds(170, 30, 150, 20);

        lblDiaCre.setText("Días de crédito:");
        panDatFac.add(lblDiaCre);
        lblDiaCre.setBounds(340, 10, 118, 14);
        panDatFac.add(txtDiaCre);
        txtDiaCre.setBounds(450, 10, 130, 20);

        lblForEnv.setText("Forma de envío:");
        panDatFac.add(lblForEnv);
        lblForEnv.setBounds(340, 30, 90, 14);

        cboForEnv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--- Seleccione envío --", "Land", "Sea", "Air" }));
        panDatFac.add(cboForEnv);
        cboForEnv.setBounds(450, 30, 130, 20);

        panCab.add(panDatFac, java.awt.BorderLayout.SOUTH);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setAutoscrolls(true);
        panDet.setLayout(new java.awt.BorderLayout());

        spnDet.setDividerLocation(258);
        spnDet.setOneTouchExpandable(true);

        panCarPagImp.setLayout(new java.awt.BorderLayout());
        spnDet.setLeftComponent(panCarPagImp);

        panDetPagImp.setLayout(new java.awt.BorderLayout());
        spnDet.setRightComponent(panDetPagImp);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 62));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panDet.add(panObs, java.awt.BorderLayout.SOUTH);

        panGen.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.GridLayout(2, 1));

        panAsiDiaNor.setPreferredSize(new java.awt.Dimension(100, 150));
        panAsiDiaNor.setLayout(new java.awt.GridLayout(2, 1, 2, 0));

        panAsiDiaNorIns.setLayout(new java.awt.BorderLayout());
        panAsiDiaNor.add(panAsiDiaNorIns);

        panAsiDiaNorPro.setLayout(new java.awt.BorderLayout());
        panAsiDiaNor.add(panAsiDiaNorPro);

        panAsiDia.add(panAsiDiaNor);

        panAsiDiaCen.setLayout(new java.awt.GridLayout(2, 1));

        panAsiDiaCenAjuNP.setLayout(new java.awt.BorderLayout());
        panAsiDiaCen.add(panAsiDiaCenAjuNP);

        panAsiDiaCenAjuPE.setLayout(new java.awt.BorderLayout());
        panAsiDiaCen.add(panAsiDiaCenAjuPE);

        panAsiDia.add(panAsiDiaCen);

        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
    String strTit, strMsg;
    try
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }
    catch (Exception e){
        dispose();
    }
}//GEN-LAST:event_exitForm

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc=txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
        if (txtDesCorTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(1);
        }
    } else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
    txtCodImp.transferFocus();
}//GEN-LAST:event_txtCodImpActionPerformed

private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
    strCodImp=txtCodImp.getText();
    txtCodImp.selectAll();
}//GEN-LAST:event_txtCodImpFocusGained

private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodImp.getText().equalsIgnoreCase(strCodImp)) {
        if (txtCodImp.getText().equals("")) {
            txtNomImp.setText("");
        } else {
            mostrarVenConImp(1);

        }
    } else
        txtCodImp.setText(strCodImp);
}//GEN-LAST:event_txtCodImpFocusLost

private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
    txtNomImp.transferFocus();
}//GEN-LAST:event_txtNomImpActionPerformed

private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
    strNomImp=txtNomImp.getText();
    txtNomImp.selectAll();
}//GEN-LAST:event_txtNomImpFocusGained

private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomImp.getText().equalsIgnoreCase(strNomImp)) {
        if (txtNomImp.getText().equals("")) {
            txtCodImp.setText("");
        } else {
            mostrarVenConImp(2);
        }
    } else
        txtNomImp.setText(strNomImp);
}//GEN-LAST:event_txtNomImpFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc=txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
        if (txtDesLarTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(2);
        }
    } else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
    mostrarVenConImp(0);
}//GEN-LAST:event_butImpActionPerformed

private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
    strNumDoc=txtNumDoc.getText();
    txtNumDoc.selectAll();
}//GEN-LAST:event_txtNumDocFocusGained

private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNumDoc.getText().equalsIgnoreCase(strNumDoc)) {

    }
}//GEN-LAST:event_txtNumDocFocusLost

private void txtValDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValDocActionPerformed
    txtValDoc.transferFocus();
}//GEN-LAST:event_txtValDocActionPerformed

private void txtValDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusGained
    strMonDoc=txtValDoc.getText();
    txtValDoc.selectAll();
}//GEN-LAST:event_txtValDocFocusGained

private void txtValDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtValDoc.getText().equalsIgnoreCase(strMonDoc)){
    }
}//GEN-LAST:event_txtValDocFocusLost

private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
    txtCodExp.transferFocus();
}//GEN-LAST:event_txtCodExpActionPerformed

private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
    strCodExp=txtCodExp.getText();
    txtCodExp.selectAll();
}//GEN-LAST:event_txtCodExpFocusGained

private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodExp.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)){
            if (txtCodExp.getText().equals("")){
                txtNomExp2.setText("");
            }
            else
            {
                mostrarVenConExp(1);
            }
        }
        else
            txtCodExp.setText(strCodExp);
    }
}//GEN-LAST:event_txtCodExpFocusLost

private void txtNomExp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExp2ActionPerformed
    txtNomExp2.transferFocus();
}//GEN-LAST:event_txtNomExp2ActionPerformed

private void txtNomExp2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExp2FocusGained
    strNomExp=txtNomExp2.getText();
    txtNomExp2.selectAll();
}//GEN-LAST:event_txtNomExp2FocusGained

private void txtNomExp2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExp2FocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomExp2.getText().equalsIgnoreCase(strNomExp)) {
        if (txtNomExp2.getText().equals("")) {
            txtCodExp.setText("");
        } else {
            mostrarVenConExp(2);
        }
    } 
    else
        txtNomExp2.setText(strNomExp);
}//GEN-LAST:event_txtNomExp2FocusLost

private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
    mostrarVenConExp(0);
}//GEN-LAST:event_butExpActionPerformed

private void txtCodPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPueActionPerformed
    txtCodPue.transferFocus();
}//GEN-LAST:event_txtCodPueActionPerformed

private void txtCodPueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPueFocusGained
    strCodPto=txtCodPue.getText();
    txtCodPue.selectAll();
}//GEN-LAST:event_txtCodPueFocusGained

private void txtCodPueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPueFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodPue.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPue.getText().equalsIgnoreCase(strCodPto)){
            if (txtCodPue.getText().equals("")){
                txtNomPue.setText("");
            }
            else
            {
                mostrarVenConPto(1);
            }
        }
        else
            txtCodPue.setText(strCodPto);
    }
}//GEN-LAST:event_txtCodPueFocusLost

private void txtNomPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPueActionPerformed
    txtNomPue.transferFocus();
}//GEN-LAST:event_txtNomPueActionPerformed

private void txtNomPueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPueFocusGained
    strNomPto=txtNomPue.getText();
    txtNomPue.selectAll();
}//GEN-LAST:event_txtNomPueFocusGained

private void txtNomPueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPueFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomPue.getText().equalsIgnoreCase(strNomPto)) {
        if (txtNomPue.getText().equals("")) {
            txtCodPue.setText("");
        } else {
            mostrarVenConPto(2);
        }
    } 
    else
        txtNomPue.setText(strNomPto);
}//GEN-LAST:event_txtNomPueFocusLost

private void butPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPueActionPerformed
    mostrarVenConPto(0);
}//GEN-LAST:event_butPueActionPerformed

private void txtCodSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSegActionPerformed
    txtCodSeg.transferFocus();
}//GEN-LAST:event_txtCodSegActionPerformed

private void txtCodSegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSegFocusGained
    strCodSeg=txtCodSeg.getText();
    txtCodSeg.selectAll();
}//GEN-LAST:event_txtCodSegFocusGained

private void txtCodSegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSegFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodSeg.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodSeg.getText().equalsIgnoreCase(strCodSeg)){
            if (txtCodSeg.getText().equals("")){
                txtNomSeg.setText("");
            }
            else
            {
                mostrarVenConSeg(1);
            }
        }
        else
            txtCodSeg.setText(strCodSeg);
    }
}//GEN-LAST:event_txtCodSegFocusLost

private void txtNomSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomSegActionPerformed
    txtNomSeg.transferFocus();
}//GEN-LAST:event_txtNomSegActionPerformed

private void txtNomSegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusGained
    strNomSeg=txtNomSeg.getText();
    txtNomSeg.selectAll();
}//GEN-LAST:event_txtNomSegFocusGained

private void txtNomSegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomSeg.getText().equalsIgnoreCase(strNomSeg)) {
        if (txtNomSeg.getText().equals("")) {
            txtCodSeg.setText("");
        } else {
            mostrarVenConSeg(2);
        }
    } 
    else
        txtNomSeg.setText(strNomSeg);
}//GEN-LAST:event_txtNomSegFocusLost

private void butSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSegActionPerformed
    mostrarVenConSeg(0);
}//GEN-LAST:event_butSegActionPerformed

private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
    txtCodForPag.transferFocus();
}//GEN-LAST:event_txtCodForPagActionPerformed

private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
    strCodPag=txtCodForPag.getText();
    txtCodForPag.selectAll();
}//GEN-LAST:event_txtCodForPagFocusGained

private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (txtCodForPag.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodPag)){
            if (txtCodForPag.getText().equals("")){
                txtNomForPag.setText("");
            }
            else
            {
                mostrarVenConPag(1);
            }
        }
        else
            txtCodForPag.setText(strCodPag);
    }
}//GEN-LAST:event_txtCodForPagFocusLost

private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
    txtNomForPag.transferFocus();
}//GEN-LAST:event_txtNomForPagActionPerformed

private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
    strNomPag=txtNomForPag.getText();
    txtNomForPag.selectAll();
}//GEN-LAST:event_txtNomForPagFocusGained

private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomForPag.getText().equalsIgnoreCase(strNomPag)) {
        if (txtNomForPag.getText().equals("")) {
            txtCodForPag.setText("");
        } else {
            mostrarVenConPag(2);
        }
    } 
    else
        txtNomForPag.setText(strNomPag);
}//GEN-LAST:event_txtNomForPagFocusLost

private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
    mostrarVenConPag(0);
}//GEN-LAST:event_butForPagActionPerformed

private void optTmFobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTmFobActionPerformed
    if(optTmFob.isSelected()){
        optTmFob.setSelected(true);//siempre que se da click se selecciona.
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(false);
    }
    else
        optTmFob.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=1;
    objImp33_01.setTipoNotaPedido(intTipNotPed);
    objImp33_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp33_01.calculaTotalFletes();
    objImp33_02.regenerarCalculos();    
    
}//GEN-LAST:event_optTmFobActionPerformed

private void optTmCfrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTmCfrActionPerformed
    if(optTmCfr.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(true);//siempre que se da click se selecciona.
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(false);
    }
    else
        optTmCfr.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=2;
    objImp33_01.setTipoNotaPedido(intTipNotPed);
    objImp33_01.setValorFleteInactivo();
    objImp33_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp33_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objImp33_01.calculaTotalFletes();
    objImp33_02.regenerarCalculos();    
    
}//GEN-LAST:event_optTmCfrActionPerformed

private void optPzaFobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPzaFobActionPerformed
    if(optPzaFob.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(true);//siempre que se da click se selecciona.
        optPzaCfr.setSelected(false);
    }
    else
        optPzaFob.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=3;
    objImp33_01.setTipoNotaPedido(intTipNotPed);
    objImp33_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp33_01.calculaTotalFletes();
    //objImp33_02.seteaColumnaPiezas();
    objImp33_02.regenerarCalculos();    
    
}//GEN-LAST:event_optPzaFobActionPerformed

private void optPzaCfrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPzaCfrActionPerformed
    if(optPzaCfr.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(true);//siempre que se da click se selecciona.
    }
    else
        optPzaCfr.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=4;
    objImp33_01.setTipoNotaPedido(intTipNotPed);
    objImp33_01.setValorFleteInactivo();
    objImp33_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp33_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objImp33_01.calculaTotalFletes();
    //objImp33_02.seteaColumnaPiezas();
    objImp33_02.regenerarCalculos();    
    
}//GEN-LAST:event_optPzaCfrActionPerformed

private void butConNotPedLisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConNotPedLisActionPerformed
    if(arlDatCabImp33_03==null){ //Crea el objeto
        arlDatCabImp33_03=new ArrayList();
        arlDatDetImp33_03=new ArrayList();
        arlDatCabImp33_03.clear();
        arlDatDetImp33_03.clear();        
    }
    else { //Reutiliza el objeto antes creado
        //Se envia la informacion de ZafImp33 a ZafImp33_03
        objImp33_03.setNotasPedidoSeleccionadas(arlDatCabImp33_03); //Envia los datos de la cabecera a Imp33_03
        objImp33_03.setDetalleSeleccionado(arlDatDetImp33_03);      //Devuelve los datos del detalle a Imp33_03
    }

    if(objImp33_03.getBotonPresionado()==0){ //No se ha presionado ningun boton, este caso se va a dar cuando se consulte una nota de embarque, solo en ese caso no se ha presionado ese boton
        //Se envia la informacion de ZafImp33 a ZafImp33_03
        objImp33_03.setNotasPedidoSeleccionadas(arlDatCabImp33_03); //Envia los datos de la cabecera a Imp33_03
        objImp33_03.setDetalleSeleccionado(arlDatDetImp33_03);      //Devuelve los datos del detalle a Imp33_03
    }

    objImp33_03.setVisible(true);
    if(objImp33_03.getBotonPresionado()==1){ //Se presiono el boton ACEPTAR
        //Se coloca la informacion en los arreglos
        arlDatCabImp33_03=objImp33_03.getCabeceraSeleccionada(); //Devuelve los datos de la cabecera
        arlDatDetImp33_03=objImp33_03.getDetalleSeleccionado();  //Devuelve los datos del detalle
        //la informacion de los arreglos se la coloca en los campos y en las tablas respectivas
        setCabeceraPedidoEmbarcado();
        setCargosPagarPedidoEmbarcado(); //este metodo esta haciendo que se caiga el programa
        setDetallePedidoEmbarcado();
        objImp33_02.setTipoNotaPedido(intTipNotPed);
        objImp33_02.regenerarISD();
        objImp33_02.regenerarCalculos();
        txtValDoc.setText("" + objImp33_02.getValorTotalCosto());
        txtValFacConIva.setText("" + objImp33_02.getValorTotalFacturaConIva());
        txtPesTotKgr.setText("" + objImp33_02.getCalcularPesoTotal());
        objImp33_01.setFleteItem(objImp33_02.getValorTotalFlete());
        objImp33_01.setValorFleteTotal(objImp33_02.getValorTotalFlete());
        txtNumPedOri.setText(objImp33_03.getNotaPedidoSeleccionado());
        txtVerNumPed.setText(objImp33_03.getNotaPedidoSeleccionado());
        
        //nuevo
        objImp33_03.getDatExpPedSelGenDia();
        
        cargarClaPriNPSel();
        
    }

}//GEN-LAST:event_butConNotPedLisActionPerformed

    private void butCorEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCorEleActionPerformed
        enviarCorElePreVta();
    }//GEN-LAST:event_butCorEleActionPerformed

    private void chkEstPedEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEstPedEmbActionPerformed
        // TODO add your handling code here:
//        if(chkEstPedEmb.isSelected())
//            strEstFacEmb="S";
//        else
//            strEstFacEmb="N";        
    }//GEN-LAST:event_chkEstPedEmbActionPerformed

    private void butGenProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGenProActionPerformed
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            if(con!=null){
                if(generaDiarioProvision()) {
                    mostrarMsgInf("<HTML>Se generó provisión correctamente.</HTML>");
                }
            con.close();
            con=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
        }
    }//GEN-LAST:event_butGenProActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butConNotPedLis;
    private javax.swing.JButton butCorEle;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butForPag;
    private javax.swing.JButton butGenPro;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butPue;
    private javax.swing.JButton butSeg;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboForEnv;
    private javax.swing.JCheckBox chkCerNotPed;
    private javax.swing.JCheckBox chkCerPedEmb;
    private javax.swing.JCheckBox chkCreCta;
    private javax.swing.JCheckBox chkEmbTot;
    private javax.swing.JCheckBox chkEstPedEmb;
    private javax.swing.JCheckBox chkFleApr;
    private javax.swing.JCheckBox chkModSolFecEmbArr;
    private javax.swing.JCheckBox chkMosTodVerPed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblDiaCre;
    private javax.swing.JLabel lblEmpExp;
    private javax.swing.JLabel lblEnvDes;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblFecArr;
    private javax.swing.JLabel lblFecColPed;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecEmb;
    private javax.swing.JLabel lblFecPue;
    private javax.swing.JLabel lblForEnv;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblNomImp;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumFacAdu;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPesTotKgr;
    private javax.swing.JLabel lblPto;
    private javax.swing.JLabel lblSeg;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JLabel lblValFacConIva;
    private javax.swing.JLabel lblVerNumPed;
    private javax.swing.JRadioButton optPzaCfr;
    private javax.swing.JRadioButton optPzaFob;
    private javax.swing.JRadioButton optTmCfr;
    private javax.swing.JRadioButton optTmFob;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panAsiDiaCen;
    private javax.swing.JPanel panAsiDiaCenAjuNP;
    private javax.swing.JPanel panAsiDiaCenAjuPE;
    private javax.swing.JPanel panAsiDiaNor;
    private javax.swing.JPanel panAsiDiaNorIns;
    private javax.swing.JPanel panAsiDiaNorPro;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabPed;
    private javax.swing.JPanel panCarPagImp;
    private javax.swing.JPanel panDatFac;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDetPagImp;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JSplitPane spnDet;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodPue;
    private javax.swing.JTextField txtCodSeg;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDiaCre;
    private javax.swing.JTextField txtEmpExp;
    private javax.swing.JTextField txtEnvDes;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomExp2;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtNomImp;
    private javax.swing.JTextField txtNomPue;
    private javax.swing.JTextField txtNomSeg;
    private javax.swing.JTextField txtNumCon;
    private javax.swing.JTextField txtNumCorEle;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumFacAdu;
    private javax.swing.JTextField txtNumPedOri;
    public javax.swing.JTextField txtPesTotKgr;
    public javax.swing.JTextField txtValDoc;
    public javax.swing.JTextField txtValFacConIva;
    private javax.swing.JTextField txtVerNumPed;
    private javax.swing.JTextField txtVerPed;
    // End of variables declaration//GEN-END:variables

  
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgYesNo(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            blnRes=objTooBar.insertar();
                            objTooBar.afterInsertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            blnRes=objTooBar.modificar();
                            objTooBar.afterModificar();
                        }
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp2.setText("");
            txtNomExp.setText("");
            txtEmpExp.setText("");
            txtCodPue.setText("");
            txtNomPue.setText("");
            txtCodSeg.setText("");
            txtNomSeg.setText("");
            txtCodForPag.setText("");
            txtNomForPag.setText("");
            dtpFecDoc.setText("");
            dtpFecEmb.setText("");
            dtpFecPue.setText("");
            dtpFecArr.setText("");
            txtNumPedOri.setText("");
            txtVerNumPed.setText("");
            txtVerPed.setText("");
            chkMosTodVerPed.setSelected(false); 
            txtNumDoc.setText("");
            txtCodDoc.setText(""); 
            txtValDoc.setText("");
            txtValFacConIva.setText("");
            txtPesTotKgr.setText("");
            optTmFob.setSelected(false);
            optTmCfr.setSelected(false);
            optPzaFob.setSelected(false);
            optPzaCfr.setSelected(false);
            txtCodDoc.setEditable(false);
            txtValDoc.setEditable(false);
            txtValFacConIva.setEditable(false);
            txtPesTotKgr.setEditable(false);
            txtVerPed.setEditable(false);    
            txaObs1.setText("");
            txaObs2.setText("");
            
            txtNumFacAdu.setText("");
            txtDiaCre.setText("");
            txtEnvDes.setText("");
            cboForEnv.setSelectedIndex(0);
            
            objImp33_01.limpiarTablaImp33_01();
            objImp33_02.limpiarTablaImp33_02();
            objImp33_01.setBlnHayCamCamImp33_01(false);
            objImp33_02.setBlnHayCamCamImp33_02(false);

            if(arlDatCabImp33_03!=null){
                arlDatCabImp33_03=null;
                arlDatCabImp33_03.clear();
            }
            if(arlDatDetImp33_03!=null){
                arlDatDetImp33_03=null;
                arlDatDetImp33_03.clear();
            }

            chkCerNotPed.setSelected(false);
            chkFleApr.setSelected(false);
            chkModSolFecEmbArr.setSelected(false);
            chkFleApr.setSelected(false);
            chkCreCta.setEnabled(false);
            txtNumCon.setEditable(false);
            
            objAsiDiaPE.inicializar();
            objAsiDiaPro.inicializar();
            objAsiDiaAjuNP.inicializar();
            objAsiDiaAjuPE.inicializar();
            blnGenAsiDiaNueNP=false;
            chkCerPedEmb.setSelected(false);
            chkEmbTot.setSelected(false);
            chkEstPedEmb.setSelected(false);
            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio() {
            try{
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb>0){
                        if (blnHayCam || (objImp33_01.isBlnHayCamImp33_01())  || (objImp33_02.isBlnHayCamImp33_02())  ) {
                            if (isRegPro()) {
                                intIndicePedEmb=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }                
        }

        public void clickAnterior() {
            try{
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb>0){
                        if (blnHayCam || (objImp33_01.isBlnHayCamImp33_01())  || (objImp33_02.isBlnHayCamImp33_02()) ){
                            if (isRegPro()) {
                                intIndicePedEmb--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }              
        }

        public void clickSiguiente() {
            try{
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb < arlDatConPedEmb.size()-1){
                        if (blnHayCam || (objImp33_01.isBlnHayCamImp33_01())  || (objImp33_02.isBlnHayCamImp33_02())){
                            if (isRegPro()) {
                                intIndicePedEmb++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }
      
        public void clickFin() {
            try{
                if(arlDatConPedEmb.size()>0){
                    if(intIndicePedEmb<arlDatConPedEmb.size()-1){
                        if (blnHayCam || (objImp33_01.isBlnHayCamImp33_01())  || (objImp33_02.isBlnHayCamImp33_02()) ) {
                            if (isRegPro()) {
                                intIndicePedEmb=arlDatConPedEmb.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndicePedEmb=arlDatConPedEmb.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickInsertar() {
            try{
                if ((blnHayCam) || (objImp33_01.isBlnHayCamImp33_01())  || (objImp33_02.isBlnHayCamImp33_02())   ){
                    isRegPro();
                }
                limpiarFrm();
                txtEmpExp.setText("El adecuado");
                mostrarTipDocPre();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                
                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                
                dtpFecColPed.setEnabled(false);
                dtpFecColPed.setText("");   

                dtpFecEmb.setEnabled(true);
                dtpFecEmb.setText("");

                dtpFecPue.setEnabled(true);
                dtpFecPue.setText("");
                
                dtpFecArr.setEnabled(true);
                dtpFecArr.setText("");                

                objImp33_02.setFechaDocumento(dtpFecDoc.getText());
                objImp33_02.setFechaEmbarque(dtpFecEmb.getText());
                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();
                objImp33_01.inicializar();
                objImp33_01.generarDatosCargoPagar();
                objImp33_01.setEditable(true);
                objImp33_02.inicializar();
                strTipNotPed="";
                intTipNotPed=0;
                strCodExp="";

                objImp33_03=new ZafImp33_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strTipNotPed, strCodExp, objImp33_02, objTooBar.getEstado());

                objTblModImp33_01=objImp33_01.getTblModImp33_01();
                objTblModImp33_02=objImp33_02.getTblModImp33_02();

                objImp33_01.setModoOperacionTooBar(objTooBar.getEstado());
                objImp33_02.setModoOperacionTooBar(objTooBar.getEstado());
                objImp33_03.setModoOperacionTooBar(objTooBar.getEstado());
                
                chkFleApr.setEnabled(false);
                chkModSolFecEmbArr.setEnabled(false);
                chkCreCta.setEnabled(true);

                //Inicializar las variables que indican cambios.
                blnHayCam=false;

                if(objTblModImp33_01.isDataModelChanged()){
                    System.out.println("el modelo ha cambiado");
                }
                else{
                    System.out.println("el modelo NO ha cambiado");
                }

                butConNotPedLis.setEnabled(true);
                
                //Permiso de Usuaris
                if(!(objPerUsr.isOpcionEnabled(4180))){//Modificar Cargos a Pagar
                    blnIsPerModCarPag=false;
                }
                
                objImp33_01.setBlnIsPerModCarPag(blnIsPerModCarPag);
                chkCreCta.setSelected(true);
                chkCreCta.setEnabled(false);
                blnGenAsiDiaNueNP=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {
            objTblModImp33_01=objImp33_01.getTblModImp33_01();
            objTblModImp33_02=objImp33_02.getTblModImp33_02();
        }

        public void clickModificar() 
        {
            chkCreCta.setEnabled(false);
            
            chkFleApr.setEnabled(false);
            chkModSolFecEmbArr.setEnabled(false);
            chkCerPedEmb.setEnabled(false);
            
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if(strEstDocOri.equals("A")){
                
                if(!chkCerPedEmb.isSelected())
                {
                    txtNumCon.setEditable(false);
                    chkCerPedEmb.setEnabled(true);
                    txtCodTipDoc.setEditable(false);
                    txtDesCorTipDoc.setEditable(false);
                    txtDesLarTipDoc.setEditable(false);
                    butTipDoc.setEnabled(false);
                    dtpFecDoc.setEnabled(false);
                    objImp33_02.setFechaDocumento(dtpFecDoc.getText());
                    objImp33_02.setFechaEmbarque(dtpFecEmb.getText());
                    objImp33_01.setEditable(true);
                    txtNumDoc.setEditable(false);
                    txtCodDoc.setEditable(false);
                    txtValDoc.setEditable(false);
                    txtValFacConIva.setEditable(false);
                    txtPesTotKgr.setEditable(false);
                    
                    if(chkCerPedEmb.isSelected()){
                        txtCodImp.setEditable(false);
                        txtNomImp.setEditable(false);
                        butImp.setEnabled(false);
                    }
                    else{
                        txtCodImp.setEditable(true);
                        txtNomImp.setEditable(true);
                        butImp.setEnabled(true);
                    }
                    
                    
//                    txtCodImp.setEditable(false);
//                    txtNomImp.setEditable(false);
//                    butImp.setEnabled(false);
                    txtCodExp.setEditable(false);
                    txtNomExp2.setEditable(false);
                    txtNomExp.setEditable(false);
                    butExp.setEnabled(false);
                    optTmFob.setEnabled(false);
                    optTmCfr.setEnabled(false);
                    optPzaFob.setEnabled(false);
                    optPzaCfr.setEnabled(false);
                    txtNumPedOri.setEditable(false);
                    txtVerNumPed.setEditable(false);                    

                    if(optTmFob.isSelected()){
                        strTipNotPed="1";//ne_tipnotped
                        intTipNotPed=1;//ne_tipnotped
                    } 
                    else if(optTmCfr.isSelected()){
                        strTipNotPed="2"; //ne_tipnotped
                        intTipNotPed=2;//ne_tipnotped
                    }                            
                    else if(optPzaFob.isSelected()){
                        strTipNotPed="3"; //ne_tipnotped
                        intTipNotPed=3;//ne_tipnotped
                    }
                    else if(optPzaCfr.isSelected()){
                        strTipNotPed="4"; //ne_tipnotped
                        intTipNotPed=4;//ne_tipnotped
                    }            

                    strCodExp=txtCodExp.getText();

                    //objImp33_03=new ZafImp33_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strTipNotPed, strCodExp, objImp33_02, objTooBar.getEstado());
                    //objImp33_03.getDatExpPedSelGenDia(con, intCodTipDocPedEmbOri, intCodDocPedEmbOri);
                    //System.out.println("MODIFICAR-getArregloDatoExpPedEmbDb: " + objImp33_03.getArregloDatoExpPedEmbDb());

                    objImp33_01.setTipoNotaPedido(intTipNotPed);
                    objImp33_02.setTipoNotaPedido(intTipNotPed);

                    objImp33_01.setModoOperacionTooBar(objTooBar.getEstado());
                    objImp33_02.setModoOperacionTooBar(objTooBar.getEstado());
                    objImp33_03.setModoOperacionTooBar(objTooBar.getEstado());

                    butConNotPedLis.setEnabled(true);

                    objImp33_02.regenerarISD();  //Se recalcula el valor de ISD.

                    //Permisos de Usuarios
                    if((objPerUsr.isOpcionEnabled(3980))){ //Ficha "General": Casilla "Cambiar sólo el valor del flete aproximado"
                        chkFleApr.setEnabled(true);
                    }
                    if(objParSis.getCodigoUsuario()==1)
                        chkFleApr.setEnabled(true);
                    
                    if((objPerUsr.isOpcionEnabled(3981))){ //Ficha "General": Casilla "Modificar sólo fecha embarque y arribo"
                        chkModSolFecEmbArr.setEnabled(true);
                    }
                    if(objParSis.getCodigoUsuario()==1)
                        chkModSolFecEmbArr.setEnabled(true);
                    
                    if(!(objPerUsr.isOpcionEnabled(4180))){ //Modificar Cargos a Pagar
                        blnIsPerModCarPag=false;
                    }
                    if((objPerUsr.isOpcionEnabled(4443))){ //Ficha "General": Casilla "Cerrar pedido embarcado"
                        chkCerPedEmb.setEnabled(true);
                    }
                    if(objParSis.getCodigoUsuario()==1)
                        chkCerPedEmb.setEnabled(true);
                        
                    objImp33_01.setBlnIsPerModCarPag(blnIsPerModCarPag);
                    chkCreCta.setEnabled(false);
                    
                    objImp33_01.setNumPedCon(txtNumCon.getText());
                }
                else{
                    objTblModImp33_01.setModoOperacion(objTblModImp33_01.INT_TBL_NO_EDI);
                    objTblModImp33_02.setModoOperacion(objTblModImp33_02.INT_TBL_NO_EDI);
                    mostrarMsgInf("<HTML>El Pedido Embarcado fue cerrado.<BR>No se puede modificar un Pedido Embarcado que se encuetra cerrado.<BR>Por favor verifique y vuelva a intentarlo.</HTML>");
                }
            }
            else{
                objTooBar.setEstado('w');
                
            }
            blnGenAsiDiaNueNP=false;

            
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarCabReg();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
            if(!consultarReg())
                return false;
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndicePedEmb<arlDatConPedEmb.size()-1){
                    intIndicePedEmb++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                  
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }
            return true;
        }

        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            if (objThrGUIVisPre==null){
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if ((blnHayCam) || (objImp33_01.isBlnHayCamImp33_01())  || (objImp33_02.isBlnHayCamImp33_02())   ){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)  {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
            arlDatFilEliImp33_03=objImp33_02.getArregloFilasEliminadas();

            if (!isCamVal())
                return false;

            return true;
        }

        public boolean beforeConsultar() {
//            objTblModImp33_01=objImp33_01.getTblModImp33_01();
//            objTblModImp33_02=objImp33_02.getTblModImp33_02();
            
            arlDatFilEliImp33_03=objImp33_02.getArregloFilasEliminadas();
            return true;
        }

        public boolean beforeModificar(){
            arlDatFilEliImp33_03=objImp33_02.getArregloFilasEliminadas();
            System.out.println("***blnCiePedRef: " + blnCiePedRef);
            System.out.println("***blnCnvPedFacEmb: " + blnCnvPedFacEmb);
            
            blnCiePedRef=false;
            blnCnvPedFacEmb=false;
            
            System.out.println("+++blnCiePedRef: " + blnCiePedRef);
            System.out.println("+++blnCnvPedFacEmb: " + blnCnvPedFacEmb);
            
            System.out.println("beforeModificar");
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if(  (chkModSolFecEmbArr.isSelected()) ||  (chkFleApr.isSelected())  ){

            }
            else{

                if (!isCamVal())
                    return false;
            }
            System.out.println("D5");
            //MICHELLE
            /* Se valida que no permita cambiar de importador, cuando ya han habido movimientos contables.*/
//            if(!strCodImpAnt.equalsIgnoreCase(txtCodImp.getText())){
//                System.out.println("D6");
//                if(isCuentaAsociadaSaldo()){
//                    System.out.println("D7");
//                    mostrarMsgInf("<HTML>No se puede cambiar de Importador por que la(s) cuenta(s) asociada(s) tienen movimientos contables.<BR>Verifique movimientos contables y vuelva a intentarlo.</HTML>");
//                    return false;
//                }
//            }
            System.out.println("D8");
            if(objParSis.getCodigoMenu()==2879){
                System.out.println("D9");
                if(isCargosPagarConsolidados()){
                    System.out.println("D10");
                    mostrarMsgInf("<HTML>Existen cargos a pagar consolidados que se desean afectar.<BR>No se puede modificar cargos a pagar consolidados.<BR>Verifique y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }

                        
            return true;
        }

        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            if(isIngresoImportacion()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido Embarcado tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                return false;
            }
            
//            if(isFacturaImportacion()){
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El Pedido Embarcado tiene una <FONT COLOR=\"blue\">Factura de Importación</FONT> asociada.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
//                return false;
//            }
            
            
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            if(isIngresoImportacion()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido Embarcado tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                return false;
            }
            
//            if(isFacturaImportacion()){
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El Pedido Embarcado tiene una <FONT COLOR=\"blue\">Factura de Importación</FONT> asociada.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
//                return false;
//            }
            
            if(isCuentaAsociadaSaldo()){
                mostrarMsgInf("<HTML>Existe(n) cuenta(s) asociada(s) con saldo en el Pedido Embarcado.<BR>Verifique saldos y vuelva a intentarlo.</HTML>");
                return false;
            }
            
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');
            
            //solo enviar por Importaciones el correo, por Compras Locales no enviar correo.
            if(!isItemTerminalesCompraLocal()){
                enviarCorElePreVta(); ////solo se la llama. Se envia el correo
            }            
            
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            
            bgdSumValTotFobCfrDb=BigDecimal.ZERO;
            BigDecimal bgdValTotFobCfrDb=BigDecimal.ZERO;
            if(objTblModImp33_02.getRowCountTrue()>0){
                for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                    bgdValTotFobCfrDb=new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                    
                    bgdSumValTotFobCfrDb=bgdSumValTotFobCfrDb.add(bgdValTotFobCfrDb);//
                }
            }
            //System.out.println("afterConsultar-bgdSumValTotFobCfrDb: " + bgdSumValTotFobCfrDb);
            return true;
        }

        public boolean afterModificar() {
            blnHayCam=false;
            objTooBar.setEstado('w');
            cargarReg();
            objTooBar.afterConsultar();
            objAsiDiaPE.inicializar();
            objAsiDiaPro.inicializar();
            objAsiDiaAjuNP.inicializar();
            objAsiDiaAjuPE.inicializar();
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //SOLO cuando es admin se determinará Left, para que muestre pedidos embarcados aun cuando no tengan detalle.
                String strFilJoin=" INNER JOIN"; 
//                if(objParSis.getCodigoUsuario()==1) {   strFilJoin="LEFT OUTER JOIN ";           }
//                else {    strFilJoin="INNER JOIN ";            }
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas";
                strSQL+="      , a1.co_usrIng, a2.tx_usr AS tx_nomUsrIng, a1.co_usrMod, a3.tx_usr AS tx_nomUsrMod, a1.st_cie";
                
                strSQL+="      , CAST(a1.fe_ing AS DATE) AS fe_ing";
                strSQL+="      , (CASE WHEN (CAST(a1.fe_ing AS DATE)<='2019-06-16') THEN 'N' ELSE 'S' END ) AS st_genAsiDiaRev";
                
                strSQL+=" FROM (  (tbm_cabPedEmbImp AS a1 INNER JOIN tbm_usr AS a2 ON a1.co_usrIng=a2.co_usr)";
                strSQL+="          INNER JOIN tbm_usr AS a3 ON a1.co_usrMod=a3.co_usr";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" "+strFilJoin+" tbm_detPedEmbImp AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc AND a1.co_doc=a5.co_doc)";
                strSQL+=" "+strFilJoin+" tbm_inv AS a6 ON (a1.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
                if(!blnVenConEme) { 
                    strSQL+=" AND a6.tx_codAlt LIKE '%I' "; //Mostrar Solo items con Terminal I
                }                
                strSQL+=" )";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                
                if(!chkMosTodVerPed.isSelected()){ //Filtro: Oculta o muestra todas las versiones del pedido
                    strSQL+=" AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL) "; //Sólo mostrará la última versión del pedido. No mostrará ni la inactiva, ni la de reversión.
                }
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                strAux=txtCodImp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_imp = " + strAux + "";
                strAux=txtCodExp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_exp = " + strAux + "";
                strAux=txtCodPue.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_ciupueemb = " + strAux + "";
                strAux=txtCodSeg.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_seg = " + strAux + "";
                strAux=txtCodForPag.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_forpag = " + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (dtpFecEmb.isFecha())
                    strSQL+=" AND a1.fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (dtpFecPue.isFecha())
                    strSQL+=" AND a1.fe_pue='" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (dtpFecArr.isFecha())
                    strSQL+=" AND a1.fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";

                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";

                
                strAux=txtNumPedOri.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND (CASE WHEN a1.tx_numDoc4 IS NOT NULL THEN a1.tx_numDoc4 ELSE a1.tx_numDoc2 END) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                                
                strAux=txtVerNumPed.getText();
                if (!strAux.equals("")) {
                    strSQL+=" AND (  UPPER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+="        OR LOWER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+=" )";
                }
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas";
                strSQL+="        , a1.co_usrIng, a2.tx_usr, a1.co_usrMod, a3.tx_usr, a1.st_cie";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConPedEmb = new ArrayList();
                while(rst.next())
                {
                    arlRegConPedEmb = new ArrayList();
                    arlRegConPedEmb.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_TIP_DOC,rst.getInt("co_tipDoc"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_doc"));
                    arlRegConPedEmb.add(INT_ARL_CON_TXT_USRING,rst.getString("tx_nomUsrIng"));
                    arlRegConPedEmb.add(INT_ARL_CON_TXT_USRMOD,rst.getString("tx_nomUsrMod"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_IMP,   rst.getString("co_imp"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_CTAACT,rst.getString("co_ctaAct"));
                    arlRegConPedEmb.add(INT_ARL_CON_COD_CTAPAS,rst.getString("co_ctaPas"));
                    arlDatConPedEmb.add(arlRegConPedEmb);
                    
                    strGenAsiDiaRevNotPed=rst.getString("st_genAsiDiaRev");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;   
                con.close();
                con=null;
                                
                if(arlDatConPedEmb.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConPedEmb.size()) + " registros");
                    intIndicePedEmb=arlDatConPedEmb.size()-1;
                    if(!cargarReg()){
                        blnRes=false;
                    }
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }                 
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                if (cargarCarPagImp()){
                    if (cargarDetRegItmVal()){                                        
                        if(objAsiDiaPE.consultarDiario(Integer.parseInt(txtCodImp.getText()), getCodigoLocalPredeterminado(Integer.parseInt(txtCodImp.getText())), objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC), objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC))) {  
//                        if(objAsiDiaPE.consultarDiario(Integer.parseInt(txtCodImp.getText()), getCodigoLocalPredeterminado(Integer.parseInt(txtCodImp.getText())), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){ //Antes
//                            if(objAsiDiaPro.consultarDiario(Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){
//                                if(objAsiDiaAjuNP.consultarDiario(Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){
//                                    if(objAsiDiaAjuPE.consultarDiario(Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){
//                                        if(objAsiDiaNueNP.consultarDiario(Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){
                                            blnRes=true;
                                            if(optTmFob.isSelected()){
                                                strTipNotPed="1";//ne_tipnotped
                                                intTipNotPed=1;//ne_tipnotped
                                            } 
                                            else if(optTmCfr.isSelected()){
                                                strTipNotPed="2"; //ne_tipnotped
                                                intTipNotPed=2;//ne_tipnotped
                                            }                            
                                            else if(optPzaFob.isSelected()){
                                                strTipNotPed="3"; //ne_tipnotped
                                                intTipNotPed=3;//ne_tipnotped
                                            }
                                            else if(optPzaCfr.isSelected()){
                                                strTipNotPed="4"; //ne_tipnotped
                                                intTipNotPed=4;//ne_tipnotped
                                            }
                                            strCodExp=txtCodExp.getText();

                                            if(setearArreglosCabeceraDetalle()){                           
                                                objImp33_02.setBlnHayCamCamImp33_02(false);
                                                objImp33_01.setBlnHayCamCamImp33_01(false);
                                            }
//
//                                        }                                            
//                                    }                                        
//                                }
//                            }
                        }
                    }
                }
            }
            objImp33_01.setTipoNotaPedido(intTipNotPed);
            objImp33_02.setTipoNotaPedido(intTipNotPed);
            
            objImp33_02.setBlnHayCamCamImp33_02(false);
            objImp33_01.setBlnHayCamCamImp33_01(false);
            
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_colPed, a1.fe_emb, a1.fe_pue, a1.fe_arr, a1.ne_numdoc, a1.tx_numDoc2, a1.ne_tipnotped ";
                strSQL+="      , a1.co_imp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag ";
                strSQL+="      , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+="      , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                strSQL+="      , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
                strSQL+="      , a1.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp";
                strSQL+="      , a6.co_ciu, a6.tx_deslar AS tx_nomPueEmb";
                strSQL+="      , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                strSQL+="      , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                strSQL+="      , a1.tx_numdoc3, a1.tx_direnv, a1.ne_forenv, a1.ne_diacre";
                strSQL+="      , a1.co_ctaAct, a1.co_ctaPas, a1.st_cie, a1.ne_verNumPed, a1.st_doc, a1.st_emb, a1.st_embTot";
                strSQL+="      , (CASE WHEN a1.tx_numdoc4 IS NOT NULL THEN a1.tx_numdoc4 ELSE a1.tx_numdoc2 END) AS tx_numPed";
                strSQL+="      , a1.tx_numDocCon";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                strSQL+=" INNER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a6 ON(a1.co_ciupueemb=a6.co_ciu)";
                strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);     
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);     
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);     
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                
                intCodEmpPedEmbOri=objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                intCodLocPedEmbOri=objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                intCodTipDocPedEmbOri=objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                intCodDocPedEmbOri=objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                
                //System.out.println("cargarCabReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_imp");
                    txtCodImp.setText((strAux==null)?"":strAux);
                    strCodImpAnt = ((strAux==null)?"":strAux);  /* Se captura el código de importador, para obtener el anterior importador, previo a alguna modificación. */           
                    
                    strAux=rst.getString("tx_nomImp");
                    txtNomImp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_exp");
                    txtCodExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomExp");
                    txtNomExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_aliExp");
                    txtNomExp2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tipemp");
                    txtEmpExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_ciupueemb");
                    txtCodPue.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomPueEmb");
                    txtNomPue.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_seg");
                    txtCodSeg.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomSeg");
                    txtNomSeg.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_forpag");
                    txtCodForPag.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomForPag");
                    txtNomForPag.setText((strAux==null)?"":strAux);
       
                    dtpFecDoc.setText("");
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    dtpFecColPed.setText("");
                    dtpFecColPed.setText(objUti.formatearFecha(rst.getDate("fe_colPed"),"dd/MM/yyyy"));                      
                    
                    dtpFecEmb.setText("");
                    datFecEmbIni=rst.getDate("fe_emb");
                    dtpFecEmb.setText(objUti.formatearFecha(rst.getDate("fe_emb"),"dd/MM/yyyy"));
   
                    dtpFecPue.setText("");
                    dtpFecPue.setText(objUti.formatearFecha(rst.getDate("fe_pue"),"dd/MM/yyyy"));
                    
                    dtpFecArr.setText("");
                    datFecArrIni=rst.getDate("fe_arr");
                    dtpFecArr.setText(objUti.formatearFecha(rst.getDate("fe_arr"),"dd/MM/yyyy"));
                    
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_numPed");
                    txtNumPedOri.setText((strAux==null)?"":strAux);  
                    
                    strAux=rst.getString("tx_numDoc2");
                    txtVerNumPed.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("ne_verNumPed");
                    txtVerPed.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getObject("nd_valdoc")==null?"0":(rst.getString("nd_valdoc").equals("")?"0":rst.getString("nd_valdoc"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    BigDecimal bgdPorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                    BigDecimal bgdValDoc=BigDecimal.valueOf(Double.valueOf(strAux));
                    txtValFacConIva.setText("" + (objUti.redondearBigDecimal( bgdValDoc.multiply(bgdPorIva), objParSis.getDecimalesMostrar())) );                    
                    strAux=rst.getObject("nd_pestotkgr")==null?"0":(rst.getString("nd_pestotkgr").equals("")?"0":rst.getString("nd_pestotkgr"));
                    txtPesTotKgr.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));

                    strAux=rst.getString("ne_tipnotped");
                    if(strAux.equals("1")){
                        optTmFob.setSelected(true);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(false);
                    }                        
                    else if(strAux.equals("2")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(true);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(false);
                    }
                    else if(strAux.equals("3")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(true);
                        optPzaCfr.setSelected(false);
                    }
                    else if(strAux.equals("4")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(true);
                    }

                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    
                    strAux=rst.getString("tx_numdoc3");
                    txtNumFacAdu.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_direnv");
                    txtEnvDes.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getObject("ne_forenv")==null?"0":(rst.getString("ne_forenv").equals("")?"0":rst.getString("ne_forenv"));
                    cboForEnv.setSelectedIndex(Integer.parseInt(strAux));
                    
                    strAux=rst.getString("ne_diacre");
                    txtDiaCre.setText((strAux==null)?"":strAux);
                    
                    chkCreCta.setSelected(false);
                    strAux=(rst.getObject("co_ctaAct")==null?"":rst.getString("co_ctaAct"));
                    if(! strAux.equals(""))
                        chkCreCta.setSelected(true);
                    
                    strAux=(rst.getObject("co_ctaPas")==null?"":rst.getString("co_ctaPas"));
                    if(!strAux.equals(""))
                        chkCreCta.setSelected(true);
                    
                    strAux=(rst.getObject("st_cie")==null?"":rst.getString("st_cie"));
                    if(strAux.equals("S"))
                        chkCerPedEmb.setSelected(true);
                    else
                        chkCerPedEmb.setSelected(false);
                    
                    strAux=rst.getObject("st_doc")==null?"A":rst.getString("st_doc");
                    strEstDocOri=strAux;
                                        
                    strAux=rst.getObject("st_emb")==null?"":rst.getString("st_emb");
                    if(strAux.equals("S")){
                        strAux=objParSis.getNombreMenu();
                        chkEstPedEmb.setSelected(true);
                        strEstFacEmb="";
                        this.setTitle(strAux + strVer + strEstFacEmb);
                        lblTit.setText(strAux + strEstFacEmb);
                    }
                    else{
                        strAux=objParSis.getNombreMenu();
                        chkEstPedEmb.setSelected(false);
                        strEstFacEmb="(Facturado)";
                        this.setTitle(strAux + strVer + strEstFacEmb);
                        lblTit.setText(strAux + strEstFacEmb);
                    }
                    
                    strAux=rst.getObject("st_embTot")==null?"":rst.getString("st_embTot");
                    if(strAux.equals("S"))
                        chkEmbTot.setSelected(true);
                    else
                        chkEmbTot.setSelected(false);
                    
                    strAux=rst.getString("tx_numDocCon");
                    txtNumCon.setText((strAux==null)?"":strAux);
                    
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
                
                objImp33_03=new ZafImp33_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strTipNotPed, strCodExp, objImp33_02, objTooBar.getEstado());
                objImp33_03.getDatExpPedSelGenDia(con, intCodTipDocPedEmbOri, intCodDocPedEmbOri);
                //System.out.println("CONSULTA-getArregloDatoExpPedEmbDb: " + objImp33_03.getArregloDatoExpPedEmbDb());
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndicePedEmb+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConPedEmb.size()) );            
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

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegItmVal(){
        boolean blnRes=true;
        strEstNotPedCer="";
        try{
            objTblModImp33_02.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_DesCor as tx_uniMed, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+="      , b1.co_emp, b1.co_locrel, b1.co_tipdocrel, b1.co_docrel, b1.co_regrel";
                    strSQL+="      , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara, CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                    strSQL+="      , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des, b1.co_exp, b3.tx_nom as tx_nomExp";
                    strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                    strSQL+="      , b1.nd_can";
                    strSQL+="      , b2.nd_can AS nd_canNotPed";
                    //strSQL+=" , ((b2.nd_can*0.10)+b2.nd_can) AS nd_canNotPed";//Alfredo dijo que se puede ingresar hasta el 10% del valor de la nota de pedido adicional
                    strSQL+="      , b1.nd_preUni, b1.nd_valTotFobCfr";
                    strSQL+="      , b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra";
                    strSQL+="      , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, CASE WHEN b2.nd_canUtiPedEmb IS NULL THEN 0 ELSE b2.nd_canUtiPedEmb END AS nd_canUtiPedEmb";
                    strSQL+="      , d1.st_cie, CASE WHEN a1.nd_pesitmkgr > 0 THEN ROUND((b1.nd_cosUni/a1.nd_pesitmkgr),2) ELSE 0 END as nd_cosKgr";
                    strSQL+="      , b1.nd_valISD, b1.st_impISDCreTri";
                    strSQL+=" FROM (tbm_detPedEmbImp AS b1 LEFT OUTER JOIN tbm_detNotPedImp AS b2";
                    strSQL+="       ON b1.co_emp=b2.co_emp AND b1.co_locRel=b2.co_loc AND b1.co_tipDocRel=b2.co_tipDoc AND b1.co_docRel=b2.co_doc AND b1.co_regRel=b2.co_reg";
                    strSQL+="       LEFT OUTER JOIN tbm_cabNotPedImp AS d1 ON b2.co_emp=d1.co_emp AND b2.co_loc=d1.co_loc AND b2.co_tipDoc=d1.co_tipDoc AND b2.co_doc=d1.co_doc";
                    strSQL+=" )";
                    strSQL+=" INNER JOIN";
                    strSQL+=" (";
                    strSQL+=" 	((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                    strSQL+=" 	  LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                    strSQL+=" 	)";
                    strSQL+=" 	LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                    strSQL+=" 		INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                    strSQL+=" 	) ON a1.co_grpImp=a4.co_grp";
                    strSQL+=" ) ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+=" INNER JOIN tbm_expImp AS b3 ON(b1.co_exp=b3.co_exp)";
                    strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_DesCor, a1.nd_pesitmkgr";
                    strSQL+="        , b1.co_emp, b1.co_locrel, b1.co_tipdocrel, b1.co_docrel, b1.co_regrel";
                    strSQL+="        , a5.co_parara, a5.tx_par, a5.tx_des, b1.co_exp, b3.tx_nom, b1.nd_ara";
                    strSQL+="        , b1.nd_can, b2.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                    strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, b2.nd_canUtiPedEmb, d1.st_cie";
                    strSQL+="        , b1.nd_valISD, b1.st_impISDCreTri";
                    strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt"; 
                    //System.out.println("cargarDetRegItmVal: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objImp33_02.INT_TBL_DAT_LIN,"");
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_ITM_MAE,    rst.getString("co_itmMae"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_ITM,        rst.getString("co_itm"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codAlt"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_LET_ITM,    rst.getString("tx_codAlt2"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_BUT_ITM,        null);
                        vecReg.add(objImp33_02.INT_TBL_DAT_NOM_ITM,        rst.getString("tx_nomItm"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_UNI_MED,        rst.getString("tx_uniMed"));  
                        vecReg.add(objImp33_02.INT_TBL_DAT_PES,            rst.getString("nd_pesitmkgr"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_ARA,        rst.getString("co_parara"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_NOM_ARA,        rst.getString("tx_par"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_DES_ARA,        rst.getString("tx_des"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_POR_ARA,        rst.getString("nd_ara"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_EXP,        rst.getString("co_Exp"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_NOM_EXP,        rst.getString("tx_nomExp"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_BUT_EXP,        null);
                        vecReg.add(objImp33_02.INT_TBL_DAT_CAN_TON_MET,    rst.getString("nd_canTonMet"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_CAN_PZA,        rst.getString("nd_can"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_PRE_UNI,        rst.getString("nd_preUni"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_TOT_FOB_CFR,    rst.getString("nd_valTotFobCfr"));                        
                        vecReg.add(objImp33_02.INT_TBL_DAT_VAL_FLE,        rst.getString("nd_valFle"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_VAL_CFR,        rst.getString("nd_valCfr"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_VAL_CFR_ARA,    "0");
                        vecReg.add(objImp33_02.INT_TBL_DAT_VAL_ISD,        rst.getString("nd_valISD"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_CHK_ISD_CRE_TRI,null);                        
                        vecReg.add(objImp33_02.INT_TBL_DAT_TOT_ARA,        rst.getString("nd_valTotAra"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_TOT_GAS,        rst.getString("nd_valTotGas"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_TOT_COS,        rst.getString("nd_valTotCos"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COS_UNI,        rst.getString("nd_cosUni"));                        
                        vecReg.add(objImp33_02.INT_TBL_DAT_COS_KGR,        rst.getString("nd_cosKgr"));  
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_EMP_REL,    rst.getString("co_emp"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_LOC_REL,    rst.getString("co_locrel"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL,rst.getString("co_tipdocrel"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_DOC_REL,    rst.getString("co_docrel"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_COD_REG_REL,    rst.getString("co_regrel"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_CAN_NOT_PED,    (rst.getObject("nd_canNotPed")==null?new BigDecimal("0"):(rst.getString("nd_canNotPed").equals("")?new BigDecimal("0"):rst.getBigDecimal("nd_canNotPed"))));
                        vecReg.add(objImp33_02.INT_TBL_DAT_CAN_PEN_NOT_PED,((rst.getObject("nd_canNotPed")==null?new BigDecimal("0"):(rst.getString("nd_canNotPed").equals("")?new BigDecimal("0"):rst.getBigDecimal("nd_canNotPed"))).subtract(rst.getBigDecimal("nd_canUtiPedEmb"))));
                        vecReg.add(objImp33_02.INT_TBL_DAT_CAN_UTI_PED_EMB,rst.getString("nd_canUtiPedEmb"));                        
                        vecReg.add(objImp33_02.INT_TBL_DAT_CAN_PED_EMB_AUX,rst.getString("nd_can"));
                        vecReg.add(objImp33_02.INT_TBL_DAT_REV_ITM,        "");
                        vecDat.add(vecReg);
                        
                        if(strEstNotPedCer.equals(""))
                            strEstNotPedCer=rst.getObject("st_cie")==null?"":rst.getString("st_cie");
                        
                        String strChkISD=rst.getObject("st_impISDCreTri")==null?"N":(rst.getString("st_impISDCreTri").equals("")?"N":rst.getString("st_impISDCreTri"));
                        if(strChkISD.equals("S")){
                            vecReg.setElementAt( new Boolean(true), objImp33_02.INT_TBL_DAT_CHK_ISD_CRE_TRI);
                        }                                 
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objImp33_02.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                    }
                    
                    if(strEstNotPedCer.equals("S"))
                        chkCerNotPed.setSelected(true);
                    else
                        chkCerNotPed.setSelected(false);
                    
                    objImp33_02.regenerarCalculoCfrAra();
                }
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

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCarPagImp(){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        Object objValFleSav=null;
        Object objValAraSav=null;
        Object objValGasSav=null;
        String strTipCarPag="";
        try{
            objTblModImp33_01.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                if(conExt!=null){ /* Consulta al momento de Autorizaciones de Pagos y Bancos. */
                    stm=conExt.createStatement();
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carpag, a1.nd_valcarpag";
                    strSQL+="      , a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag, a2.st_genPro, a2.st_genAsiDia, a2.st_carPagPedCon";
                    strSQL+="      , (CASE WHEN a2.st_pagExt IS NULL THEN 'N' ELSE a2.st_pagExt END) AS st_pagExt";  
                    strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                    strSQL+=" ON a1.co_carpag=a2.co_carpag";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" ORDER BY a2.ne_ubi";
                    //System.out.println("1-cargarCarPagImp: " + strSQL);
                    rst=stm.executeQuery(strSQL);                    
                }
                else{
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null){
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL ="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carpag, a1.nd_valcarpag";
                        strSQL+="      , a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag, a2.st_genPro, a2.st_genAsiDia, a2.st_carPagPedCon";
                        strSQL+="      , (CASE WHEN a2.st_pagExt IS NULL THEN 'N' ELSE a2.st_pagExt END) AS st_pagExt";
                        strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                        strSQL+=" ON a1.co_carpag=a2.co_carpag";
                        strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                        strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                        strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                        strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                        strSQL+=" ORDER BY a2.ne_ubi";
                        //System.out.println("2-cargarCarPagImp: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                    }                
                }
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(objImp33_01.INT_TBL_DAT_LIN,"");
                    vecReg.add(objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP,    rst.getString("tx_tipcarpag"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP,    rst.getString("co_carpag"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_NOM_CAR_PAG_IMP,    rst.getString("tx_nomCarPag"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP,    rst.getString("nd_valcarpag"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,rst.getString("nd_valcarpag"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_TIP_NOT_PED,        "");

                    if(optTmFob.isSelected())
                        vecReg.setElementAt("1", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);
                    else if(optTmCfr.isSelected())
                        vecReg.setElementAt("2", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);
                    else if(optPzaFob.isSelected())
                        vecReg.setElementAt("3", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);
                    else if(optPzaCfr.isSelected())
                        vecReg.setElementAt("4", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);

                    strTipCarPag=rst.getObject("tx_tipcarpag")==null?"":rst.getString("tx_tipcarpag");
                    if(strTipCarPag.equals("F")){
                        //bgdValFleSav=bgdValFleSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        objValFleSav=rst.getObject("nd_valCarPag");
                        if((objValFleSav==null) || (objValFleSav.equals("")) ){                                
                        }
                        else{
                            bgdValFleSav=bgdValFleSav.add(new BigDecimal(objValFleSav.toString()));
                        }
                    }
                    else if(strTipCarPag.equals("A")){
                        //bgdValAraSav=bgdValAraSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        objValAraSav=rst.getObject("nd_valcarpag");
                        if((objValAraSav==null) || (objValAraSav.equals("")) ){                                
                        }
                        else{
                            bgdValAraSav=bgdValAraSav.add(new BigDecimal(objValAraSav.toString()));
                        }                            
                    }
                    else if(strTipCarPag.equals("G")){
                        //bgdValGasSav=bgdValGasSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        objValGasSav=rst.getObject("nd_valcarpag");
                        if((objValGasSav==null) || (objValGasSav.equals("")) ){                                
                        }
                        else{
                            bgdValGasSav=bgdValGasSav.add(new BigDecimal(objValGasSav.toString()));
                        }
                    }
                    vecReg.add(objImp33_01.INT_TBL_DAT_GEN_PRO,rst.getString("st_genPro"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_TIP_PAG_EXT,rst.getString("st_pagExt"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_GEN_ASI_DIA,rst.getString("st_genAsiDia"));
                    vecReg.add(objImp33_01.INT_TBL_DAT_EST_CAR_PAG_CON,rst.getString("st_carPagPedCon"));
                    vecDat.add(vecReg);
                }

                if(bgdValFleSav.compareTo(BigDecimal.ZERO)>=0)
                    objImp33_01.setFleteItem(bgdValFleSav);
                if(bgdValAraSav.compareTo(BigDecimal.ZERO)>=0)
                    objImp33_01.setDerechosArancelarios(bgdValAraSav);
                if(bgdValGasSav.compareTo(BigDecimal.ZERO)>=0)
                    objImp33_01.setTotalGastos(bgdValGasSav);

                rst.close();
                stm.close();
                rst=null;
                stm=null;
                
                if(con!=null){
                    con.close();
                    con=null;
                }

                if(objImp33_01.asignarVectorModeloDatos(vecDat)){
                    objImp33_01.calculaTotalFletes();
                    objImp33_01.calculaTotalAranceles();
                    objImp33_01.calculaTotalGastos();
                    objImp33_01.calculaTotalCargosProvision();
                    vecDat.clear();
                }
                else{
                    mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                }                
            }
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean insertarReg(){
        boolean blnRes=false;
        java.util.Date datTmp;
        int intCodLocImpTxt=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            objCodSegInsAnt=null;

            if(con!=null){
                if(cargarClaPriNPSel()){
                    if(insertar_tbmCabPedEmbImp()){
                        if(insertar_tbmDetPedEmbImp()){
                            if(modificar_tbmCabNotPedImp()){
                                if(modificar_tbmDetNotPedImp()){
                                    if(insertar_tbmCarPagPedEmbImp()){
                                        if(actualizar_tbmCabTipDoc()){
                                            if(insertar_creacionCuentas()){ //Provision
                                                if(getCodSegNotPedImp()){
                                                    datTmp=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");  //Asignar fecha actual
                                                    if(generaAsiDia()){
                                                        intCodLocImpTxt=Integer.parseInt(getCodLocPre(con, txtCodImp.getText()));
                                                        if(objAsiDiaPE.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), intCodLocImpTxt, Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), datTmp)){
                                                            if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 2, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null
                                                                , 8, Integer.parseInt(txtCodImp.getText()), intCodLocImpTxt, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){




                                                                //inicio
                                                                if(generaAsiDia_AjuNP()){
                                                                    //Diario de Ajuste de Nota de Pedido
                                                                    if(objAsiDiaAjuNP.insertarDiario(con, Integer.parseInt(strCodEmpAsiDiaAjuNotPed), Integer.parseInt(strCodLocAsiDiaAjuNotPed), objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB, strCodDocAjuNotPedPre, strNumDocAjuNotPedPre, objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
                                                                        if(modificar_tbmCabNotPedImp_asiDiaAjuPedEmb(Integer.parseInt(strCodEmpAsiDiaAjuNotPed), Integer.parseInt(strCodLocAsiDiaAjuNotPed), objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB, Integer.parseInt(strCodDocAjuNotPedPre) )){
                                                                            if(actualizar_tbmCabTipDocAjuNotPed(strCodEmpAsiDiaAjuNotPed, strCodLocAsiDiaAjuNotPed, objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB)){
                                                                                if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 1, Integer.parseInt(strCodEmpNotPed), Integer.parseInt(strCodLocNotPed), Integer.parseInt(strCodTipDocNotPed), Integer.parseInt(strCodDocNotPed), null
                                                                                    , 8, Integer.parseInt(strCodEmpAsiDiaAjuNotPed), Integer.parseInt(strCodLocAsiDiaAjuNotPed), objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB, Integer.parseInt(strCodDocAjuNotPedPre), null)){
                                                                                    //fin

                                                                                    //INICIO - NUEVO
                                                                                    if(!chkEmbTot.isSelected()){ //Parcial: Genera asiento de diario de ajuste a la NP nuevo, si es total sólo se realiza el diario de ajuste(reversión) según



                                                                                        if(blnGenAsiDiaNueNP){
                                                                                            if(generaAsiDia_InsNueNP()){
                                                                                                if(objAsiDiaNueNP.insertarDiario(con, Integer.parseInt(strCodEmpAsiDiaAjuNotPed), Integer.parseInt(strCodLocAsiDiaAjuNotPed), objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB, strCodDocNotPed_nueAsiDia, strNumDocNotPed_nueAsiDia, objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
                                                                                                        if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 1, Integer.parseInt(strCodEmpNotPed), Integer.parseInt(strCodLocNotPed), Integer.parseInt(strCodTipDocNotPed), Integer.parseInt(strCodDocNotPed), null
                                                                                                            , 8, Integer.parseInt(strCodEmpAsiDiaAjuNotPed), Integer.parseInt(strCodLocAsiDiaAjuNotPed), objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB, Integer.parseInt(strCodDocNotPed_nueAsiDia), null)){




                                                                                                            if(actualizaTotFobCfr_Ara()){
                                                                                                                con.commit();
                                                                                                                blnRes=true;
                                                                                                            }
                                                                                                            else
                                                                                                                con.rollback();
                                                                                                        }
                                                                                                        else
                                                                                                            con.rollback();
                                                                                                }
                                                                                                else
                                                                                                    con.rollback();
                                                                                            }
                                                                                            else
                                                                                                con.rollback();
                                                                                        }
                                                                                        else{
                                                                                            if(actualizaTotFobCfr_Ara()){
                                                                                                con.commit();
                                                                                                blnRes=true;
                                                                                            }
                                                                                            else
                                                                                                con.rollback();
                                                                                        }
                                                                                    }
                                                                                    else{
                                                                                        con.commit();
                                                                                        blnRes=true;
                                                                                    }  
                                                                                    //FIN - NUEVO
                                                                                }
                                                                                else
                                                                                    con.rollback();
                                                                            }
                                                                            else
                                                                                con.rollback();
                                                                        }
                                                                        else
                                                                            con.rollback();
                                                                    }
                                                                    else
                                                                        con.rollback();
                                                                }
                                                                else
                                                                    con.rollback();
                                                            }
                                                            else
                                                                con.rollback();
                                                        }
                                                        else
                                                            con.rollback();
                                                    }
                                                    else
                                                        con.rollback();
                                                }
                                                else
                                                    con.rollback();
                                            }
                                            else
                                                con.rollback();
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();

            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabPedEmbImp(){
        boolean blnRes=false;
        intNumPedEmbDia=-1;
        int intUltReg=-1;
        int intVerNumPed=0;
        int intNumUltDoc=0;
        try{

            txtPesTotKgr.setText(""+objImp33_02.calcularV20PesoTotal());

            if (con!=null){
                stm=con.createStatement();
                
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){//modificar
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()) {
                        intNumUltDoc=(rst.getInt("ne_ultDoc")+1);
                    }
                    txtNumDoc.setText("" + intNumUltDoc);
                    
                    intVerNumPed=(txtVerPed.getText().length()==0?1:(Integer.parseInt(txtVerPed.getText())+1));
                    txtVerPed.setText(""+intVerNumPed);
                    
                }
                else if(objTooBar.getEstado()=='n'){
                    txtVerPed.setText("");
                }
                
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT (CASE WHEN MAX(a1.co_doc)+1 IS NULL THEN 0 ELSE MAX(a1.co_doc)+1 END ) AS co_doc";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                rst=stm.executeQuery(strSQL); 
                if(rst.next()){
                    intUltReg=rst.getInt("co_doc");
                }
                if(intUltReg==-1)
                    return false;
                txtCodDoc.setText("" + intUltReg);                     
                
                //////////////////////////////////////////////////////////////
                strSQL="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocPedEmb";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumPedEmbDia=rst.getInt("ne_numDocPedEmb");
                }
                intNumPedEmbDia++;

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabPedEmbImp(";
                strSQL+="       co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_emb, fe_pue, fe_arr, ne_numdoc, tx_numDoc2";
                strSQL+="     , ne_tipnotped, co_imp, co_exp, co_ciupueemb, co_seg, co_forpag";
                strSQL+="     , tx_tipemp, nd_valdoc, nd_pestotkgr, st_imp, tx_obs1, tx_obs2";
                strSQL+="     , st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod";
                strSQL+="     , tx_numdoc3, tx_direnv, ne_forenv, ne_diacre, st_cie, st_ciePagImp, ne_verNumPed, tx_numDoc4, st_doc, st_emb, st_embTot, nd_porISD";
                if(dtpFecColPed.isFecha())  
                    strSQL+=" , fe_colPed";
                strSQL+=" , tx_numDocCon";
                strSQL+=" )";
                strSQL+=" VALUES (";
                strSQL+="   " + objParSis.getCodigoEmpresa(); //co_emp
                strSQL+=",  " + objParSis.getCodigoLocal();   //co_loc
                strSQL+=",  " + txtCodTipDoc.getText();       //co_tipdoc                
                strSQL+=",  " + txtCodDoc.getText();          //co_doc
                
                if(objTooBar.getEstado()=='n')
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                else
                    strSQL+=", '" + objUti.formatearFecha(datFecAux,"yyyy-MM-dd") + "'"; //fe_doc
                
                strSQL+=", '" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_emb
                strSQL+=", '" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_pue
                strSQL+=", '" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_arr
                strSQL+=",  " + objUti.codificar(txtNumDoc.getText(),2) + ""; //ne_numdoc
                
                if(objTooBar.getEstado()=='n')
                    strSQL+=",  " + objUti.codificar(txtVerNumPed.getText(),1) + ""; //tx_numDoc2
                else{
                    strSQL+=", " + objUti.codificar(txtNumPedOri.getText() + "_" + "" + txtVerPed.getText()); //tx_numdoc2
                    txtVerNumPed.setText(txtNumPedOri.getText() + "_" + "" + txtVerPed.getText());
                }
                
                if(optTmFob.isSelected())
                    strSQL+=",'1'"; //ne_tipnotped
                else if(optTmCfr.isSelected())
                    strSQL+=",'2'"; //ne_tipnotped
                else if(optPzaFob.isSelected())
                    strSQL+=",'3'"; //ne_tipnotped
                else if(optPzaCfr.isSelected())
                    strSQL+=",'4'"; //ne_tipnotped     
                
                strSQL+=", " + objUti.codificar(txtCodImp.getText()) + "";    //co_imp
                strSQL+=", " + objUti.codificar(txtCodExp.getText()) + "";    //co_exp
                strSQL+=", " + objUti.codificar(txtCodPue.getText()) + "";    //co_ciupueemb
                strSQL+=", " + objUti.codificar(txtCodSeg.getText()) + "";    //co_seg
                strSQL+=", " + objUti.codificar(txtCodForPag.getText()) + ""; //co_forpag
                strSQL+=", " + objUti.codificar(txtEmpExp.getText()) + "";    //tx_tipemp
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + ""; //nd_valdoc
                strSQL+=", " + objUti.codificar(txtPesTotKgr.getText()) + ""; //nd_pestotkgr
                strSQL+=", 'N'"; //st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText() + " " + strVer) + " "; //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + ""; //tx_obs2
                strSQL+=", 'A'"; //st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+="," + objUti.codificar(txtNumFacAdu.getText()) + "";//tx_numdoc3
                strSQL+="," + objUti.codificar(txtEnvDes.getText()) + "";   //tx_direnv
                strSQL+="," + cboForEnv.getSelectedIndex() + "";            //ne_forenv
                strSQL+="," + objUti.codificar(txtDiaCre.getText()) + "";   //ne_diacre
                strSQL+=" ,'"+(chkCerPedEmb.isSelected()?"S":"N")+"'";      //st_cie
                strSQL+=", 'N'";   //st_ciePagImp
                
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') )//modificar
                    strSQL+=", " + txtVerPed.getText() + "";//ne_verNumPed
                else
                    strSQL+=", Null"; //ne_verNumPed
                
                strSQL+=", " + objUti.codificar(txtNumPedOri.getText()) + "";   //tx_numdoc4
                strSQL+=", 'A'"; //st_doc
                                                
                strSQL+=" ,'"+(chkEstPedEmb.isSelected()?"S":"N")+"'";  //st_emb
                strSQL+=" ,'"+(chkEmbTot.isSelected()?"S":"N")+"'";     //st_embTot
                
                BigDecimal bgdPorISD=objZafImp.getPorcentajeISD(Integer.parseInt(txtCodImp.getText()), getCodigoLocalPredeterminado(Integer.parseInt(txtCodImp.getText()))).multiply(BigDecimal.valueOf(100));                
                strSQL+=", "+bgdPorISD+""; //nd_porISD                 
                
                if(dtpFecColPed.isFecha())  
                    strSQL+=", '" + objUti.formatearFecha(dtpFecColPed.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_colPed
                
                strSQL+=", " + objUti.codificar(txtNumCon.getText()) + "";//tx_numDocCon
                
                strSQL+="); ";
                
//                System.out.println("insertar_tbmCabPedEmbImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
                
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPedEmbImp", "tbh_cabPedEmbImp", "WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "");
                
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabPedEmbImp_RV(){
        boolean blnRes=true;
        intNumPedEmbDia=-1;
        int intUltReg=-1;
        int intNumUltDoc=0;
        int intVerNumPed=0;
        try{
            txtPesTotKgr.setText(""+objImp33_02.calcularV20PesoTotal());

            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=";
                strSQL+=" (";
                strSQL+=" SELECT co_tipDoc";
                strSQL+=" FROM tbr_tipDocPrg";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND st_reg='S'";
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    intNumUltDoc=(rst.getInt("ne_ultDoc")+1);
                }
                txtNumDoc.setText("" + intNumUltDoc);
                                
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT (CASE WHEN MAX(a1.co_doc)+1 IS NULL THEN 0 ELSE MAX(a1.co_doc)+1 END ) AS co_doc";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                rst=stm.executeQuery(strSQL); 
                if(rst.next()){
                    intUltReg=rst.getInt("co_doc");
                }
                if(intUltReg==-1)
                    return false;
                txtCodDoc.setText("" + intUltReg);                     
                
                //////////////////////////////////////////////////////////////
                strSQL="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocPedEmb";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumPedEmbDia=rst.getInt("ne_numDocPedEmb");
                }
                intNumPedEmbDia++;
                
                intVerNumPed=(txtVerPed.getText().length()==0?1:(Integer.parseInt(txtVerPed.getText())+1));
                txtVerPed.setText(""+intVerNumPed);
                

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabPedEmbImp(";
                strSQL+="          co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_emb, fe_pue, fe_arr, ne_numdoc, tx_numDoc2";
                strSQL+="        , ne_tipnotped, co_imp, co_exp, co_ciupueemb, co_seg, co_forpag";
                strSQL+="        , tx_tipemp, nd_valdoc, nd_pestotkgr, st_imp, tx_obs1, tx_obs2";
                strSQL+="        , st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod";
                strSQL+="        , tx_numdoc3, tx_direnv, ne_forenv, ne_diacre, st_cie, st_ciePagImp";
                strSQL+="        , ne_verNumPed, tx_numdoc4, st_doc, st_emb, st_embTot, co_ctaAct, co_ctaPas, nd_porISD, nd_totFobCfr, nd_totCfrAra, tx_numDocCon ";
                if(dtpFecColPed.isFecha())  
                    strSQL+="    , fe_colPed";            
                strSQL+="  )"; 
                strSQL+=" (";
                strSQL+="   SELECT co_emp, co_loc, co_tipdoc, " + txtCodDoc.getText() + " AS co_doc";
                strSQL+="        , '" + objUti.formatearFecha(datFecAux,"yyyy-MM-dd") + "' AS fe_doc";
                strSQL+="        , fe_emb, fe_pue, fe_arr";
                strSQL+="        , ne_numdoc, (tx_numdoc4||'_'||" + objUti.codificar(txtVerPed.getText()) + ")";
                strSQL+="        , ne_tipnotped, co_imp, co_exp, co_ciupueemb, co_seg, co_forpag";
                strSQL+="        , tx_tipemp, (nd_valdoc*-1), nd_pestotkgr, st_imp, tx_obs1, tx_obs2";
                strSQL+="        , st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod";
                strSQL+="        , tx_numdoc3, tx_direnv, ne_forenv, ne_diacre, st_cie, st_ciePagImp";
                strSQL+="        , " + objUti.codificar(txtVerPed.getText()) + "";
                strSQL+="        , tx_numdoc4";
                strSQL+="        , 'R'";
                strSQL+="        , st_emb";
                strSQL+="        , st_embTot";
                strSQL+="        , co_ctaAct";
                strSQL+="        , co_ctaPas";
                strSQL+="        , nd_porISD, nd_totFobCfr, nd_totCfrAra";
                                
                strSQL+=", " + objUti.codificar(txtNumCon.getText()) + " AS tx_numDocCon";//
                
                if(dtpFecColPed.isFecha()) {
                    strSQL+="    , fe_colPed"; 
                }
                 
                strSQL+="   FROM tbm_cabPedEmbImp";
                strSQL+="   WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+="   AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+="   AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="   AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=");";
                
                //Se actualiza el estado del documento origen
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET st_doc='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";
                
//                System.out.println("insertar_tbmCabPedEmbImp_RV: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetPedEmbImp(){
        boolean blnRes=false;
        String strSQLIns="";
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                objImp33_02.regenerarISD();                
                for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++) {
                    blnRes=true; /* Si existen datos en el detalle, se continua el proceso. */
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_detPedEmbImp(";
                    strSQL+="          co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel";
                    strSQL+="        , co_docrel, co_regrel, co_itm, co_exp, nd_ara, nd_can, nd_preuni, nd_valtotfobcfr";
                    strSQL+="        , nd_canTonMet, nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_valtotcos, nd_cosuni, nd_valISD, st_impISDCreTri)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                    strSQL+=", " + txtCodTipDoc.getText();      //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText();         //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL);     //co_locrel
                    strSQL+=", " + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL); //co_tipdocrel
                    strSQL+=", " + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL);     //co_docrel
                    strSQL+=", " + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_REG_REL);     //co_regrel
                    strSQL+=", " + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ITM);         //co_itm
                    strSQL+=", " + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EXP);         //co_exp
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_POR_ARA)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_POR_ARA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_ara
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_can
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PRE_UNI)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PRE_UNI).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PRE_UNI).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_preuni
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotfobcfr
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_canTonMet
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_FLE)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_FLE).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valfle
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valcfr          
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_ARA)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_ARA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotara
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_GAS)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_GAS).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_GAS).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotgas
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_COS).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotcos
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_cosuni
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_ISD)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_ISD).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_ISD).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valISD       
                    
                    if(objTblModImp33_02.isChecked(i, objImp33_02.INT_TBL_DAT_CHK_ISD_CRE_TRI))
                        strSQL+=", 'S'";//st_impISDCreTri
                    else
                        strSQL+=", 'N'";//st_impISDCreTri                    
                    
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;   
                    
                }
                //System.out.println("insertar_tbmDetPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_detPedEmbImp", "tbh_detPedEmbImp", "WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "");
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */

    
    private boolean insertar_tbmDetPedEmbImp_RV(){
        boolean blnRes=true;
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                objImp33_02.regenerarISD();                
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_detPedEmbImp(";
                strSQL+="          co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel";
                strSQL+="        , co_docrel, co_regrel, co_itm, co_exp, nd_ara, nd_can, nd_preuni, nd_valtotfobcfr";
                strSQL+="        , nd_canTonMet, nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_valtotcos, nd_cosuni, nd_valISD, st_impISDCreTri)";
                strSQL+=" (";
                strSQL+=" SELECT co_emp, co_loc, co_tipdoc, " + txtCodDoc.getText() + " AS co_doc, co_reg, co_locrel, co_tipdocrel";
                strSQL+=" , co_docrel, co_regrel, co_itm, co_exp, nd_ara, (nd_can*-1)";
                strSQL+=" , nd_preuni, (nd_valtotfobcfr*-1), (nd_canTonMet*-1), (nd_valfle*-1), (nd_valcfr*-1), (nd_valtotara*-1)";
                strSQL+=" , (nd_valtotgas*-1), (nd_valtotcos*-1)";
                strSQL+=" , nd_cosuni, nd_valISD, st_impISDCreTri";
                strSQL+="   FROM tbm_detPedEmbImp";
                strSQL+="   WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+="   AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+="   AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="   AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=");";
                //System.out.println("insertar_tbmDetPedEmbImp_RV: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
    

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean modificar_tbmDetNotPedImp(){
        boolean blnRes=true;
        String strSQLIns="";
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        BigDecimal bgdVarUtiNotPedAnt=new BigDecimal("0");
        int intCodEmpFilEstEli=-1, intCodLocFilEstEli=-1, intCodTipDocFilEstEli=-1, intCodDocFilEstEli=-1, intCodRegFilEstEli=-1;
        BigDecimal bgdVarValTmpFilEli=new BigDecimal("0");
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PED_EMB_AUX)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PED_EMB_AUX).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PED_EMB_AUX).toString()), objParSis.getDecimalesBaseDatos());
                    bgdVarUtiNotPedAnt=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString()), objParSis.getDecimalesBaseDatos());

                    strSQL ="";
                    strSQL+="UPDATE tbm_detNotPedImp";
                    if( (objTooBar.getEstado()=='z') || (objTooBar.getEstado()=='a') || (objTooBar.getEstado()=='y') || (objTooBar.getEstado()=='e')  ){
                        strSQL+=" SET nd_canUtiPedEmb=((CASE WHEN nd_canUtiPedEmb IS NULL THEN 0 ELSE nd_canUtiPedEmb END)-" + bgdVarValTmp + ")";
                    }
                    else{
                        strSQL+=" SET nd_canUtiPedEmb=((CASE WHEN nd_canUtiPedEmb IS NULL THEN 0 ELSE nd_canUtiPedEmb END)-" + bgdVarValTmp + "+" + bgdVarUtiNotPedAnt + ")";
                    }
                    
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
                    strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                    strSQL+=" AND co_doc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + "";
                    strSQL+=" AND co_reg=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_REG_REL) + "";
                    strSQL+=";";
                    strSQLIns+=strSQL;
                }

                for(int h=0; h<arlDatFilEliImp33_03.size(); h++){
                   intCodEmpFilEstEli=objUti.getIntValueAt(arlDatFilEliImp33_03, h, objImp33_02.INT_CFE_COD_EMP);
                   intCodLocFilEstEli=objUti.getIntValueAt(arlDatFilEliImp33_03, h, objImp33_02.INT_CFE_COD_LOC);
                   intCodTipDocFilEstEli=objUti.getIntValueAt(arlDatFilEliImp33_03, h, objImp33_02.INT_CFE_COD_TIP_DOC);
                   intCodDocFilEstEli=objUti.getIntValueAt(arlDatFilEliImp33_03, h, objImp33_02.INT_CFE_COD_DOC);
                   intCodRegFilEstEli=objUti.getIntValueAt(arlDatFilEliImp33_03, h, objImp33_02.INT_CFE_COD_REG);
                   bgdVarValTmpFilEli=objUti.getBigDecimalValueAt(arlDatFilEliImp33_03, h, objImp33_02.INT_CFE_CAN_PED_EMB_AUX);
                   //bgdVarValCanUtiPedEmb=objUti.getBigDecimalValueAt(arlDatFilEliImp33_03, h, objImp33_02.INT_CFE_CAN_UTI_PED_EMB);

                   strSQL ="";
                   strSQL+=" UPDATE tbm_detNotPedImp";
                   strSQL+=" SET nd_canUtiPedEmb=((CASE WHEN nd_canUtiPedEmb IS NULL THEN 0 ELSE nd_canUtiPedEmb END)-" + bgdVarValTmpFilEli + ")";
                   strSQL+=" WHERE co_emp=" + intCodEmpFilEstEli + "";
                   strSQL+=" AND co_loc=" + intCodLocFilEstEli + "";
                   strSQL+=" AND co_tipDoc=" + intCodTipDocFilEstEli + "";
                   strSQL+=" AND co_doc=" + intCodDocFilEstEli + "";
                   strSQL+=" AND co_reg=" + intCodRegFilEstEli + "";
                   strSQL+=" ;";
                   strSQLIns+=strSQL;
                }
//                System.out.println("modificar_tbmDetNotPedImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean modificar_tbmCabNotPedImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                if(chkCerNotPed.isSelected()){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="" + getQueryUpdate_tbmCabNotPedImp();
                    strSQL+=";";
                    System.out.println("modificar_tbmCabNotPedImp: " + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                }
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean modificar_tbmCabNotPedImp_asiDiaAjuPedEmb(int codEmpresa, int codLocal, int codTipoDoc, int codDoc){
        boolean blnRes=true;
        try{
            if (con!=null){
//                if(chkCerNotPed.isSelected()){
                if(objTooBar.getEstado()=='n'){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL ="";
                    if(chkCerNotPed.isSelected()){
                        strSQL+="" + getQueryUpdate_tbmCabNotPedImp();
                        strSQL+=";";
                    }
                    
                    strSQL+=" UPDATE tbm_cabNotPedImp";
                    strSQL+=" SET co_empDiaAju=" + codEmpresa + "";
                    strSQL+="   , co_locDiaAju=" + codLocal + "";
                    strSQL+="   , co_tipDocDiaAju=" + codTipoDoc + "";
                    strSQL+="   , co_diaDiaAju=" + codDoc + "";
//                    if(objTooBar.getEstado()=='n'){
                        strSQL+=" WHERE co_emp=" + objTblModImp33_02.getValueAt(0, objImp33_02.INT_TBL_DAT_COD_EMP_REL) + "";
                        strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(0, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
                        strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(0, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                        strSQL+=" AND co_doc=" + objTblModImp33_02.getValueAt(0, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + "";
//                    }
//                    else{
//                        strSQL+=" WHERE co_emp=" + txtCodImp.getText() + "";
//                        strSQL+=" AND co_loc=" + strCodLocPre + "";
//                        strSQL+=" AND co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB + "";
//                        strSQL+=" AND co_doc=" + strCodDocNotPed_nueAsiDia + "";                        
//                    }
                    strSQL+=";";
                    System.out.println("modificar_tbmCabNotPedImp_asiDiaAjuPedEmb: " + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                }

//                }
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCarPagPedEmbImp(){
        boolean blnRes=true;
        String strSQLIns="";
        Object objVarValTmp=null;
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp33_01.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_carPagPedEmbImp(co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal(); //co_loc
                    strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText(); //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                    //objVarValTmp=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).equals("")?null:objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    objVarValTmp=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    strSQL+=", " + objVarValTmp; //nd_valcarpag
                    
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;
                }
//                System.out.println("insertar_tbmCarPagPedEmbImp: " + strSQLIns); 
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_carPagPedEmbImp", "tbh_carPagPedEmbImp", "WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "");
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCarPagPedEmbImp_RV(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_carPagPedEmbImp(co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag)";
                strSQL+=" (";
                strSQL+="  SELECT co_emp, co_loc, co_tipDoc, " + txtCodDoc.getText() + " AS co_doc, co_reg, co_carPag, (nd_valCarPag*-1)"; //co_emp
                strSQL+="  FROM tbm_carPagPedEmbImp"; //co_loc
                strSQL+="   WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+="   AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+="   AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="   AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=");";
                //System.out.println("insertar_tbmCarPagPedEmbImp: " + strSQL); 
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
   /**
     * Esta función permite actualizar los cargos, cuando existen los actualiza y cuando no existe los inserta.
     * @return true: Si se pudo actualizar los cargos.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCarPagPedEmbImp(){
        boolean blnRes=true;
        ResultSet rstLoc;
        Statement stmLoc;
        Connection conLoc;
        String strSQLIns="";
        Object objVarValTmp=null;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp33_01.getRowCountTrue(); i++){
                    objVarValTmp=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    //Cuando existe el cargo, Actualiza el valor del cargo.
                    strSQL ="";
                    strSQL+=" UPDATE tbm_carPagPedEmbImp ";
                    strSQL+=" SET nd_valCarPag="+objVarValTmp;
                    strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=" AND co_loc="+ objParSis.getCodigoLocal();     //co_loc
                    strSQL+=" AND co_tipDoc="+ txtCodTipDoc.getText();      //co_tipDoc
                    strSQL+=" AND co_doc="+ txtCodDoc.getText();            //co_doc
                    strSQL+=" AND co_carpag=" + objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                    strSQL+=" ;"; 
                    strSQLIns+=strSQL;
                    
                    //Inserta solo los que no existen
                    Boolean blnExiCar=false;
                    stmLoc=con.createStatement();    
                    //Verifica si existe o no el cargo a pagar.
                    strSQL ="";
                    strSQL+=" SELECT * FROM tbm_carPagPedEmbImp as x"; 
                    strSQL+=" WHERE x.co_emp="+objParSis.getCodigoEmpresa();
                    strSQL+=" AND x.co_loc="+ objParSis.getCodigoLocal();
                    strSQL+=" AND x.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND x.co_doc=" + txtCodDoc.getText();
                    strSQL+=" AND x.co_carPag="+objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP);                        
                    rstLoc=stmLoc.executeQuery(strSQL);
                    if(rstLoc.next()){
                        blnExiCar=true;
                    }
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;

                    if(!blnExiCar){  //Sólo si cargo NO EXISTE
                        //System.out.println("No existe");
                        //Obtener el código del último registro.
                        strSQL="";
                        strSQL+=" SELECT MAX(a1.co_reg)";
                        strSQL+=" FROM tbm_carPagPedEmbImp AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                        int intUltRegCar=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        if (intUltRegCar==-1)
                            return false;
                        intUltRegCar++;
                        //Inserta solo los que no existen
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_carPagPedEmbImp(co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag)";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                        strSQL+=", " + objParSis.getCodigoLocal(); //co_loc
                        strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                        strSQL+=", " + txtCodDoc.getText(); //co_doc
                        strSQL+=", " + intUltRegCar; //co_reg
                        strSQL+=", " + objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                        strSQL+=", " + objVarValTmp; //nd_valcarpag
                        strSQL+=");";
                        strSQLIns+=strSQL;                            
                    }
                }
                System.out.println("actualizar_tbmCarPagPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    


    /**
     * Esta función permite modificar el valor del Flete Aproximado
     * @return true: Si se pudo insertar el valor del flete.
     * <BR>false: En el caso contrario.
     */
    private boolean modifica_fleAprTbmCarPagPedEmbImp(){
        boolean blnRes=true;
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        String strTipCarPagImp="";
        int intCodCarPagFleApr=-1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp33_01.getRowCountTrue(); i++){
                    
                    strTipCarPagImp=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
                    intCodCarPagFleApr=Integer.parseInt(objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));
                    if(strTipCarPagImp.equals("O")){
                        if(intCodCarPagFleApr==13){
                            bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()), objParSis.getDecimalesBaseDatos());
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" UPDATE tbm_carPagPedEmbImp";
                            strSQL+=" SET nd_valCarPag=" + bgdVarValTmp + "";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() +"";
                            strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                            strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                            strSQL+=" AND co_carPag=" + objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP) + "";
                            strSQL+=" AND co_carPag=13";
                            strSQL+=";";
                            System.out.println("modifica_fleAprTbmCarPagPedEmbImp: " + strSQL);
                            stm.executeUpdate(strSQL);
                            stm.close();
                            stm=null;
                        }
                    }
                }
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean actualizarReg(){
        boolean blnRes=false;
        boolean blnResAct=false;
        try{
            if(conExt!=null){
                con=conExt;
                con.setAutoCommit(false);
            }
            else{
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
            }
            
            if (con!=null){
                /* Actualizar: Sólo Fechas*/
                if(  (chkModSolFecEmbArr.isSelected()) ||  (chkFleApr.isSelected())  )
                {
                    if((chkModSolFecEmbArr.isSelected())){
                        if(actualizar_tbmCabPedEmbImp_FecEmbArr()){
                            con.commit();
                            blnRes=true;
                            if(isChangeFecha()){
                                enviarCorEleDptoVta();
                            }
                        }
                        else
                            con.rollback();
                    }
                    else if(chkFleApr.isSelected()){
                        if(modifica_fleAprTbmCarPagPedEmbImp()){
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                }
                else{
                    
                    if(  (strPerIngRevNue.equals("")) || strPerIngRevNue.equals("R") ){
                        //<editor-fold defaultstate="collapsed" desc="/* Documento y Diario Reversión - PE */">
                        if(insertar_tbmCabPedEmbImp_RV()){
                            if(insertar_tbmDetPedEmbImp_RV()){
                                if(insertar_tbmCarPagPedEmbImp_RV()){
                                    if(actualizar_tbmCabTipDoc_RV()){
                                        if(getCodSegPedEmbImp_DB()){                                        
                                            if(strGenAsiDiaRevNotPed.equals("S")){
                                                //PEDIDO EMBARCADO
                                                if(generaAsiDia_RevPE()){//intCodImpOri
                                                    //if(objAsiDiaAjuPE.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPrePedEmb), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
                                                    if(objAsiDiaAjuPE.insertarDiario(con, intCodImpOri, Integer.parseInt(getCodLocPre(con, (""+intCodImpOri))), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
//                                                        if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 2, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null
//                                                            , 8, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPrePedEmb), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){
                                                        if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 2, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null
                                                            , 8, intCodImpOri, Integer.parseInt(getCodLocPre(con, (""+intCodImpOri))), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){
                                                            blnResAct=true;
                                                            blnRes=true;
                                                        }
                                                        else
                                                            con.rollback();
                                                    }
                                                    else
                                                        con.rollback();
                                                }
                                                else
                                                    con.rollback();
                                            }
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                        //</editor-fold>
                    }
                    
                    if(  (strPerIngRevNue.equals("")) || strPerIngRevNue.equals("N") ){
                        
                        System.out.println("***blnCiePedRef: " + blnCiePedRef);
                        System.out.println("***blnCnvPedFacEmb: " + blnCnvPedFacEmb);
                        
                        if(blnCiePedRef)
                            chkCerPedEmb.setSelected(blnCiePedRef);
                        
                        if(blnCnvPedFacEmb)
                            chkEstPedEmb.setSelected(blnCnvPedFacEmb);
                        
//                    chkCerPedEmb.setSelected(blnCiePedEmb_Con);
//                    chkEstPedEmb.setSelected(blnPedFac_Emb_Con);
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Documento y Diario Nuevo */">
                        if(insertar_tbmCabPedEmbImp()){
                            if(insertar_tbmDetPedEmbImp()){
                                if(insertar_tbmCarPagPedEmbImp()){
                                    if(actualizar_tbmCabTipDoc()){
//                                        if(generaDiarioProvision()){
                                            if(modifica_tbrDetConIntCarPagInsImp()){
                                                if(getCodSegPedEmbImp_DB()){
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    if(Integer.parseInt(txtCodImp.getText())==intCodImpOri){//se mantiene el importador
                                                        if(generaAsiDiaNuePE()){
                                                            if(objAsiDiaPE.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPrePedEmb), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
                                                                if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 2, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null, 8, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPrePedEmb), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){
                                                                    if(actualizaTotFobCfr_Ara()){
                                                                        if(generaDiarioProvision()){
                                                                            blnResAct=true;
                                                                            blnRes=true;
                                                                        }
                                                                        else
                                                                            con.rollback();
                                                                    }
                                                                    else
                                                                        con.rollback();
                                                                }
                                                                else
                                                                    con.rollback();
                                                            }
                                                            else
                                                                con.rollback();
                                                        }
                                                        else
                                                            con.rollback(); 
                                                    }
                                                    else{//se cambia el importador
                                                        if(insertar_creacionCuentasOtrImp()){
                                                            if(generaAsiDiaNuePEOtrImp()){
                                                                if(objAsiDiaPE.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPrePedEmb), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
                                                                    if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 2, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null, 8, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPrePedEmb), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){
                                                                        if(actualizaTotFobCfr_Ara()){
//                                                                            if(generaDiarioProvision()){
                                                                                blnResAct=true;
                                                                                blnRes=true;
//                                                                            }
//                                                                            else
//                                                                                con.rollback();
                                                                        }
                                                                        else
                                                                            con.rollback();
                                                                    }
                                                                    else
                                                                        con.rollback();
                                                                }
                                                                else
                                                                    con.rollback();
                                                            }
                                                            else
                                                                con.rollback();
                                                        }
                                                        else
                                                            con.rollback();
                                                    }
                                                    

                                                    
                                                    
                                                    
                                                    
                                                    
                                                    

                                                }
                                                else
                                                    con.rollback();
                                            }
                                            else
                                                con.rollback();
//                                        }
//                                        else
//                                            con.rollback();

                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                        //</editor-fold>
                        
                    }
                    

                    
                    if(blnResAct==true){
                        con.commit();
                        blnRes=true;
                    }
                    
                }
            }
            if(conExt==null){
                con.close();
                con=null;
            }

        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
//                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET ne_numdoc=" + objUti.codificar(txtNumDoc.getText(),2) + "";
                strSQL+="   , tx_numDoc2=" + objUti.codificar(txtVerNumPed.getText(),1) + "";

                if(optTmFob.isSelected())
                    strSQL+=", ne_tipnotped='1'";//ne_tipnotped
                else if(optTmCfr.isSelected())
                    strSQL+=", ne_tipnotped='2'";//ne_tipnotped
                else if(optPzaFob.isSelected())
                    strSQL+=", ne_tipnotped='3'"; //ne_tipnotped
                else if(optPzaCfr.isSelected())
                    strSQL+=", ne_tipnotped='4'";//ne_tipnotped
                
                strSQL+=", co_imp=" + objUti.codificar(txtCodImp.getText()) + "";
                strSQL+=", co_exp=" + objUti.codificar(txtCodExp.getText()) + "";
                strSQL+=", co_ciupueemb=" + objUti.codificar(txtCodPue.getText()) + "";
                strSQL+=", co_seg=" + objUti.codificar(txtCodSeg.getText()) + "";
                strSQL+=", co_forpag=" + objUti.codificar(txtCodForPag.getText()) + "";
                strSQL+=", fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                if (dtpFecPue.isFecha()){
                    strSQL+=", fe_pue='" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                
                strSQL+=", tx_tipemp=" + objUti.codificar(txtEmpExp.getText()) + "";
                strSQL+=", nd_valdoc=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=", nd_pestotkgr=" + objUti.codificar(txtPesTotKgr.getText()) + "";               
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText() + " - " +strVer) + ""; 
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", fe_ultmod='" + strAux + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";                
                strSQL+=", tx_numdoc3=" + objUti.codificar(txtNumFacAdu.getText()) + "";
                strSQL+=", tx_direnv=" + objUti.codificar(txtEnvDes.getText()) + "";
                strSQL+=", ne_forenv=" + cboForEnv.getSelectedIndex() + "";
                strSQL+=", ne_diacre=" + objUti.codificar(txtDiaCre.getText()) + "";
                strSQL+=" , st_cie='"+(chkCerPedEmb.isSelected()?"S":"N")+"'";    
                strSQL+=" , st_emb='"+(chkEstPedEmb.isSelected()?"S":"N")+"'";    
                strSQL+=" , st_embTot='"+(chkEmbTot.isSelected()?"S":"N")+"'";    
                     
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";

                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabPedEmbImp_FecEmbArr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
//                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET ";
                strSQL+="  fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                if (dtpFecPue.isFecha()){
                    strSQL+=", fe_pue='" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                }

                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", fe_ultmod='" + strAux + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";

                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmDetPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_detPedEmbImp";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCarPagPedmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_carPagPedEmbImp";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {        
        if (txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if (txtNomImp.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Importador</FONT> es obligatorio.<BR>Seleccione un Importador y vuelva a intentarlo.</HTML>");
            txtNomImp.requestFocus();
            return false;
        }
        if (txtNomExp2.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Exportador</FONT> es obligatorio.<BR>Seleccione un Exportador y vuelva a intentarlo.</HTML>");
            txtNomExp2.requestFocus();
            return false;
        }
        if (txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un Número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        if (txtNumPedOri.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> es obligatorio.<BR>Escriba un Número de pedido y vuelva a intentarlo.</HTML>");
            txtNumPedOri.requestFocus();
            return false;
        }
        
        if( isExisteNumeroPedido(txtNumPedOri.getText()) ) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> está repetido.<BR>Escriba un Número de pedido diferente y vuelva a intentarlo.</HTML>");
            txtNumPedOri.requestFocus();
            txtNumPedOri.selectAll();
            return false;
        }            
        
        //Validar que se ingresen solo pedidos con items Terminal 'I' de Importaciones
        if(isExisteItemTerminalDiferente()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existe uno o varios items cuyo terminal es diferente a 'I'<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }            

        if(objTooBar.getEstado()=='n'){
            if(!objTblModImp33_01.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Cargos a Pagar no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(!objTblModImp33_02.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(objTblModImp33_02.getRowCountTrue()<=0){
                objTblModImp33_02.removeEmptyRows();
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }
        }

        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        //Validar el "Fecha de embarque".
        if (!dtpFecEmb.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de embarque</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de embarque y vuelva a intentarlo.</HTML>");
            dtpFecEmb.requestFocus();
            return false;
        }
        
        //Validar el "Fecha de puerto".
        if (!dtpFecPue.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de puerto</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de puerto y vuelva a intentarlo.</HTML>");
            dtpFecPue.requestFocus();
            return false;
        }
        
        //Validar el "Fecha de arribo".
        if (!dtpFecArr.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de arribo</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de arribo y vuelva a intentarlo.</HTML>");
            dtpFecArr.requestFocus();
            return false;
        }        
        
        //si es TM-FOB o TM-CFR deben tener datos cada fila de las piezas, en PZA-FOB o PZA-CFR no deben tener valores.
        BigDecimal bgdCanPie=new BigDecimal("0");
        if( (optTmFob.isSelected())  || (optTmCfr.isSelected())  ){
            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                bgdCanPie=new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString()));
                
                if(bgdCanPie.compareTo(new BigDecimal("0"))<=0){
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El valor de la(s) <FONT COLOR=\"blue\">pieza(s)</FONT> es obligatorio.<BR>Escriba el(los) valor(es) y vuelva a intentarlo.</HTML>");
                    return false;                    
                }
            }
        }
        
        if(cboForEnv.getSelectedIndex()==0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Forma de envío</FONT> es obligatorio.<BR>Seleccione una Forma de envío y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(txtNumFacAdu.getText().length()<=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de factura de Aduana</FONT> es obligatorio.<BR>Escriba un Número de factura de Aduana y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if (txtDiaCre.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Días de crédito</FONT> es obligatorio.<BR>Escriba el número de días de crédito y vuelva a intentarlo.</HTML>");
            return false;
        }        
       
        if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m')  ){
            if(!chkModSolFecEmbArr.isSelected()){
                if(isIngresoImportacion()){
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El Pedido Embarcado tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>Seleccione un Pedido Embarcado diferente y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
        }
        
        if(isExisteItemRepetido()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        
//        if(chkCerPedEmb.isSelected()){
//            if(isValNulCarPag_PedEmbCer()){
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El Pedido Embarcado no puede ser cerrado porque existen valores pendientes de ingresar en Cargos a Pagar.<BR>Verifique y vuelva a intentarlo.</HTML>");
//                return false;
//            }            
//        }

        if(chkCerPedEmb.isSelected()){
            if(!objImp33_01.calculaTotalFletes()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existen valores nulos en los rubros de Flete.<BR>Verifique los valores ingresados y haga la corrección respectiva<BR>si no se realizan los cambios, los valores no se calcularán correctamente.</HTML>");
                return false;
            }

            if(!objImp33_01.calculaTotalAranceles()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existen valores nulos en los rubros de Arancel.<BR>Verifique los valores ingresados y haga la corrección respectiva<BR>si no se realizan los cambios, los valores no se calcularán correctamente.</HTML>");
                return false;
            }

            if(!objImp33_01.calculaTotalGastos()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existen valores nulos en los rubros de Gasto.<BR>Verifique los valores ingresados y haga la corrección respectiva<BR>si no se realizan los cambios, los valores no se calcularán correctamente.</HTML>");
                return false;
            }
            
            if(!objImp33_01.calculaTotalCargosProvision()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existen valores nulos en los rubros de Provisión.<BR>Verifique los valores ingresados y haga la corrección respectiva<BR>si no se realizan los cambios, los valores no se calcularán correctamente.</HTML>");
                return false;
            }
            
            
            //Valida el valor de Tot.Fob/Cfr y el Total de Gastos registrados en el panel de Cargos a Pagar
//            if(objImp33_02.getValorTotalFobCfr().compareTo(objImp33_01.getTotalGastos())<0){//Si el valor de Tot.Fob/Cfr es menor, no debe permitir guardar
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El valor de la Factura(Tot.Fob/Cfr) es menor al Total de Gastos(Cargos a Pagar).<BR>Verifique los valores ingresados y vuelva a intentarlo.</HTML>");
//                return false;
//            }

            //Valida el valor de la sumas de todos los DxP asociados al Pedido y el Total de Gastos registrados en el panel de Cargos a Pagar
            if(objImp33_02.getValorTotalFobCfr().compareTo(getValTotDxP())<0){//Si el valor de Tot.Fob/Cfr es menor, no debe permitir guardar
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El valor de la Factura(Tot.Fob/Cfr) es menor al Total de Gastos(Cargos a Pagar).<BR>Verifique los valores ingresados y vuelva a intentarlo.</HTML>");
                return false;
            }            
        }
        
        
        
//        System.out.println("**objImp33_02.getValorTotalArancel: " + objImp33_02.getValorTotalArancel());
//        System.out.println("**objImp33_01.getValorTotalArancel: " + objImp33_01.getValorTotalArancel());
        
        
        
        if ((objUti.redondearBigDecimal(objImp33_02.getValorTotalArancel(), objParSis.getDecimalesMostrar())).compareTo((objUti.redondearBigDecimal(objImp33_01.getValorTotalArancel(), objParSis.getDecimalesMostrar())))!=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El valor de Total de Aranceles del Cargo a Pagar es diferente al valor de Total de Aranceles del detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        /* Valida que el valor de factura sea igual al valor de la Columna Tot.Fob/Cfr. */
        if ((objUti.redondearBigDecimal(objImp33_02.getValorTotalFactura(), objParSis.getDecimalesMostrar())).compareTo((objUti.redondearBigDecimal(objImp33_01.getValorTotalPagoExterior(), objParSis.getDecimalesMostrar())))!=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El Valor de Total de Factura del Cargo a Pagar es diferente al Valor de Tot.FOB/CFR del detalle + Gastos Origen + Intereses.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }           

//        //solo enviar por Importaciones el correo, por Compras Locales no enviar correo.
//        if(!isItemTerminalesCompraLocal()){
//            enviarCorElePreVta(); //solo se la llama. Se envia el correo
//        }

        //Validar rando de piezas cuando es pedido por tonelada.
        if(!validarRangoPzas()) {  
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existen items fuera del rango "+bgdMin+" - "+bgdMax+" en la cantidad de piezas.<BR>Verifique y vuelva a intentarlo.</HTML>");
            objTblModImp33_02.fireTableDataChanged();
            return false;
        }
        

        return true;
    }
    
    /**
     * Función que valida en cada fila que la cantidad ingresada en la columna "Can.Pza." no sea distinta al rango permitido
     * El rango minimo es 0.95 y el rango máximo es 1.05 
     * Esta validación se deberá realizar solo cuando es un pedido por Toneladas.
     * @return 
     */
    private boolean validarRangoPzas(){
        boolean blnRes=true;
        try{
            //si es TM-FOB o TM-CFR debe validarse los datos de cada fila de las piezas, antes de guardar
            if( (optTmFob.isSelected())  || (optTmCfr.isSelected())  ){
                for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                    BigDecimal bgdCanPza=new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString()));
                    BigDecimal bgdCanTon=new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString()));
                    BigDecimal bgdPesKgr=new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PES)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PES).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PES).toString()));
                    //System.out.println("Fila#"+i+" <> bgdCanTon: "+bgdCanTon+" <> bgdCanPza: "+bgdCanPza+" <> bgdPesKgr:"+bgdPesKgr);
                    if(bgdPesKgr.compareTo(BigDecimal.ZERO)<=0){ /* Valida que siempre se asigne un peso al item, antes de ingresar */
                        mostrarMsgInf("<HTML>Existen items sin peso.<BR>Verifique, asigne peso y vuelva a intentarlo.</HTML>");
                        objTblModImp33_02.setValueAt("S", i, objImp33_02.INT_TBL_DAT_REV_ITM); //Marcar columnas para revisión
                        blnRes=false;
                    }
                    else{ /* Cuando existe un peso en el item, se valida el rango permitido*/
                        BigDecimal bgdCanCal= objUti.redondearBigDecimal( ( ( bgdCanTon.multiply(new BigDecimal("1000")) ).divide(bgdPesKgr, BigDecimal.ROUND_HALF_UP) ).divide(bgdCanPza, BigDecimal.ROUND_HALF_UP), objParSis.getDecimalesMostrar());
                        //System.out.println("cantidadCalculada: "+bgdCanCal);
                        if( (bgdCanCal.compareTo(bgdMin)<0) || (bgdCanCal.compareTo(bgdMax)>0) ) {
                            objTblModImp33_02.setValueAt("S", i, objImp33_02.INT_TBL_DAT_REV_ITM); //Marcar columnas para revisión
                            //System.out.println("Fuera del rango permitido. Fila#"+i+" => bgdCanCal: "+bgdCanCal);
                            blnRes=false;
                        }
                        else{
                            objTblModImp33_02.setValueAt("N", i, objImp33_02.INT_TBL_DAT_REV_ITM); 
                        }                    
                    }
                }
            }
        } 
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }        
        return blnRes;    
    }    
    
    /**
     * Función que permite determinar si algún item tiene un terminal que no debe estar asociado al tipo de documento(Ejm: 'I' sólo para INIMPO ; 'S' sólo para INCOLO
     * @return true: Si el terminal del item no corresponde al tipo de documento
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemTerminalDiferente(){
        boolean blnRes=false;
        String strCodAltItmTer="";
        int intCodAltItmTer=0;
        int intTipTerItm_regTblDat_i=0, intTipTerItm_regTblDat_s=0;
        try{
            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                strCodAltItmTer=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM).toString();
                intCodAltItmTer=(strCodAltItmTer.length()-1);
                
                if(strCodAltItmTer.charAt(intCodAltItmTer) == 'I'){
                    intTipTerItm_regTblDat_i++;
                }
                else if(strCodAltItmTer.charAt(intCodAltItmTer) == 'S'){
                    intTipTerItm_regTblDat_s++;
                }
                
                if( (intTipTerItm_regTblDat_i!=0)  && (intTipTerItm_regTblDat_s!=0) ){
                    break;
                }                
            }
            
            if(intTipTerItm_regTblDat_s>0){ //debería ser "cero" porque el INIMPO sólo maneja terminales 'I' y esta variable refleja que hay items con terminales 'S' en la tabla
                blnRes=true;
            }
          
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    

    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(anular_tbmCabPedEmbImp()){
                    if(modificar_tbmDetNotPedImp()){
                        if(anular_tbmPlaCta()){
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(eliminar_tbmCabPedEmbImp()){
                    if(modificar_tbmDetNotPedImp()){
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCabPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);

                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCabPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET st_reg='E'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    public JTextField getTxtPesTotKgs() {
        return txtPesTotKgr;
    }

    public void setTxtPesTotKgs(String strPesTotKgs) {
        this.txtPesTotKgr.setText(strPesTotKgs);
    }



    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexiï¿½n a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDoc(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                //System.out.println("actualizarCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

    
    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexiï¿½n a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDocAjuNotPed(String empresa, String local, int tipoDocumento){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + empresa + "";
                strSQL+=" AND co_loc=" + local + "";
                strSQL+=" AND co_tipDoc=" + tipoDocumento;
                System.out.println("actualizar_tbmCabTipDocAjuNotPed: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    
    
    
    
    
    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexiï¿½n a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDoc_RV(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
//                System.out.println("actualizarCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

    private boolean setCabeceraPedidoEmbarcado(){
        boolean blnRes=true;
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";
        try{
            if(arlDatCabImp33_03.size()>0){
                strCodEmp=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_EMP);
                strCodLoc=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_LOC);
                strCodTipDoc=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_TIP_DOC);
                strCodDoc=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_DOC);

                txtCodExp.setText(objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_EXP));
                txtNomExp.setText(objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_NOM_EXP));
                txtNomExp2.setText(objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_NOM_ALT_EXP));

                strAux=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_TIP_NOT_PED);
                
                if(strAux.equals("1")){
                    optTmFob.setSelected(true);                optTmCfr.setSelected(false);
                    optPzaFob.setSelected(false);                optPzaCfr.setSelected(false);
                    intTipNotPed=1;
                }
                else if(strAux.equals("2")){
                    optTmFob.setSelected(false);                optTmCfr.setSelected(true);
                    optPzaFob.setSelected(false);                optPzaCfr.setSelected(false);
                    intTipNotPed=2;
                }
                else if(strAux.equals("3")){
                    optTmFob.setSelected(false);                optTmCfr.setSelected(false);
                    optPzaFob.setSelected(true);                optPzaCfr.setSelected(false);
                    intTipNotPed=3;
                }
                else if(strAux.equals("4")){
                    optTmFob.setSelected(false);                optTmCfr.setSelected(false);
                    optPzaFob.setSelected(false);                optPzaCfr.setSelected(true);
                    intTipNotPed=4;
                }

                objImp33_01.setTipoNotaPedido(intTipNotPed);
                objImp33_01.setValorFleteInactivo();
                objImp33_02.setTipoNotaPedido(intTipNotPed);

                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_colPed, a1.ne_numdoc, a1.ne_numdoc AS tx_numDoc2, a1.ne_tipnotped";
                    strSQL+="      , a1.co_imp, a1.co_prv, a1.co_exp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag, a1.co_mesemb";
                    strSQL+="      , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_notpedlis, a1.st_imp, a1.tx_obs1";
                    strSQL+="      , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                    strSQL+="      , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
                    strSQL+="      , a4.co_prv, a4.tx_nom AS tx_nomPrvExt";
                    strSQL+="      , a5.co_exp, a5.tx_nom AS tx_nomExp";
                    strSQL+="      , a6.co_ciu, a6.tx_deslar AS tx_nomPueEmb";
                    strSQL+="      , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                    strSQL+="      , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                    strSQL+="      , a9.co_mesemb, a9.tx_deslar AS tx_nomMesEmb, a1.st_cie, a11.co_loc AS co_locImp";
                    strSQL+=" FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_loc AS a11 ON a1.co_imp=a11.co_emp AND a11.st_reg='P')";
                    strSQL+=" INNER JOIN tbm_detNotPedImp as a10 ON a1.co_emp=a10.co_emp AND a1.co_loc=a10.co_loc AND a1.co_tipDoc=a10.co_tipDoc AND a1.co_doc=a10.co_doc";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                    strSQL+=" INNER JOIN tbm_prvExtImp AS a4 ON(a1.co_prv=a4.co_prv)";
                    strSQL+=" INNER JOIN tbm_expImp AS a5 ON(a10.co_exp=a5.co_exp)";
                    strSQL+=" LEFT OUTER JOIN tbm_ciu AS a6 ON(a1.co_ciupueemb=a6.co_ciu)";
                    strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                    strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                    strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a9 ON(a1.co_mesemb=a9.co_mesemb)";
                    strSQL+=" WHERE a1.co_emp=" + strCodEmp;
                    strSQL+=" AND a1.co_loc=" + strCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + strCodTipDoc;
                    strSQL+=" AND a1.co_doc=" + strCodDoc;
                    strSQL+=" AND a1.st_cie='N'";
                    System.out.println("setCabeceraPedidoEmbarcado: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    if (rst.next()){
                        strAux=rst.getString("co_imp");
                        strCodImpNotPed=strAux;
                        txtCodImp.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomImp");
                        txtNomImp.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_tipemp");
                        txtEmpExp.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("co_ciupueemb");
                        txtCodPue.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomPueEmb");
                        txtNomPue.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("co_seg");
                        txtCodSeg.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomSeg");
                        txtNomSeg.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("co_forpag");
                        txtCodForPag.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nomForPag");
                        txtNomForPag.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("co_locImp");
                        strCodLocPreNotPed=strAux;
                        
                        dtpFecColPed.setText(""); 
                        dtpFecColPed.setText(objUti.formatearFecha(rst.getDate("fe_colPed"),"dd/MM/yyyy"));                            
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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


    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean setCargosPagarPedidoEmbarcado(){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        
        Object objValFleSav=null;
        Object objValAraSav=null;
        Object objValGasSav=null;
        
        String strTipCarPag="";

        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";

        BigDecimal bgdValFleTbl=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraTbl=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasTbl=new BigDecimal(BigInteger.ZERO);
        String strCodCarPagTbl="", strCodCarPagSav="";
        try{
            
            if(arlDatCabImp33_03.size()==1){
                objImp33_01.getTblModImp33_01().removeAllRows();
                strCodEmp=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_EMP);
                strCodLoc=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_LOC);
                strCodTipDoc=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_TIP_DOC);
                strCodDoc=objUti.getStringValueAt(arlDatCabImp33_03, 0, objImp33_03.INT_TBL_ARL_DAT_COD_DOC);

                if (!txtCodTipDoc.getText().equals("")){
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null){
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_carpag, a1.nd_valcarpag";
                        strSQL+="      , a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag, a2.ne_ubi, a2.st_genPro, a2.st_genAsiDia, a2.st_carPagPedCon";
                        strSQL+="      , (CASE WHEN a2.st_pagExt IS NULL THEN 'N' ELSE a2.st_pagExt END) AS st_pagExt";
                        strSQL+=" FROM tbm_carPagNotPedImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                        strSQL+=" ON a1.co_carpag=a2.co_carpag";
                        strSQL+=" WHERE a1.co_emp=" + strCodEmp + "";
                        strSQL+=" AND a1.co_loc=" + strCodLoc + "";
                        strSQL+=" AND a1.co_tipDoc IN(" + strCodTipDoc + ")";
                        strSQL+=" AND a1.co_doc IN(" + strCodDoc + ")";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_carpag, a1.nd_valcarpag, a2.tx_nom, a2.tx_tipcarpag, a2.ne_ubi, a2.st_genPro, a2.st_genAsiDia, a2.st_carPagPedCon, a2.st_pagExt";
                        //strSQL+=" ORDER BY a1.co_carpag";
                        strSQL+=" ORDER BY a2.ne_ubi";
                        System.out.println("setCargosPagarPedidoEmbarcado: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                        while (rst.next()){
                            vecReg=new Vector();
                            vecReg.add(objImp33_01.INT_TBL_DAT_LIN,"");
                            vecReg.add(objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP,    rst.getString("tx_tipcarpag"));
                            vecReg.add(objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP,    rst.getString("co_carpag"));
                            strCodCarPagSav=rst.getString("co_carpag");
                            vecReg.add(objImp33_01.INT_TBL_DAT_NOM_CAR_PAG_IMP,    rst.getString("tx_nomCarPag"));
                            vecReg.add(objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP,    rst.getObject("nd_valcarpag"));
                            vecReg.add(objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,rst.getObject("nd_valcarpag"));
                            vecReg.add(objImp33_01.INT_TBL_DAT_TIP_NOT_PED,        "");

                            if(optTmFob.isSelected())
                                vecReg.setElementAt("1", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);
                            else if(optTmCfr.isSelected())
                                vecReg.setElementAt("2", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);
                            else if(optPzaFob.isSelected())
                                vecReg.setElementAt("3", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);
                            else if(optPzaCfr.isSelected())
                                vecReg.setElementAt("4", objImp33_01.INT_TBL_DAT_TIP_NOT_PED);

                            strTipCarPag=rst.getObject("tx_tipcarpag")==null?"":rst.getString("tx_tipcarpag");
                            if(strTipCarPag.equals("F")){
                                //objValFleSav=rst.getString("nd_valcarpag").equals("")?null:rst.getObject("nd_valcarpag");
                                objValFleSav=rst.getObject("nd_valcarpag");
                                if(objValFleSav==null){
                                }
                                else{
                                    bgdValFleSav=bgdValFleSav.add((new BigDecimal(objValFleSav.toString())));
                                }
                            }
                            else if(strTipCarPag.equals("A")){
                                //bgdValAraSav=bgdValAraSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                                //objValAraSav=(rst.getObject("nd_valcarpag").toString().equals("")?null:rst.getObject("nd_valcarpag"));
                                objValAraSav=rst.getObject("nd_valcarpag");
                                if(objValAraSav==null){
                                }
                                else{
                                    bgdValAraSav=bgdValAraSav.add((new BigDecimal(objValAraSav.toString())));
                                }
                            }
                            else if(strTipCarPag.equals("G")){
                                //objValGasSav=rst.getString("nd_valcarpag").equals("")?null:rst.getObject("nd_valcarpag");
                                objValGasSav=rst.getObject("nd_valcarpag");
                                if(objValGasSav==null){
                                }
                                else{
                                    bgdValGasSav=bgdValGasSav.add((new BigDecimal(objValGasSav.toString())));
                                }
                            }

                            vecReg.add(objImp33_01.INT_TBL_DAT_GEN_PRO, rst.getObject("st_genPro"));
                            vecReg.add(objImp33_01.INT_TBL_DAT_TIP_PAG_EXT, rst.getObject("st_pagExt"));
                            vecReg.add(objImp33_01.INT_TBL_DAT_GEN_ASI_DIA, rst.getObject("st_genAsiDia"));
                            vecReg.add(objImp33_01.INT_TBL_DAT_EST_CAR_PAG_CON, rst.getObject("st_carPagPedCon"));
                            vecDat.add(vecReg);
                        }
                        objImp33_01.setFleteItem(bgdValFleSav);
                        objImp33_01.setDerechosArancelarios(bgdValAraSav);
                        objImp33_01.setTotalGastos(bgdValGasSav);
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                        if(objImp33_01.asignarVectorModeloDatos(vecDat)){
                            objImp33_01.calculaTotalFletes();
                            objImp33_01.calculaTotalAranceles();
                            objImp33_01.calculaTotalGastos();
                            objImp33_01.calculaTotalCargosProvision();
                            vecDat.clear();
                        }
                        else{
                            mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                        }
                    }
                }
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


    private boolean setDetallePedidoEmbarcado(){
        boolean blnRes=true;
        try{
            for(int k=0; k<arlDatDetImp33_03.size(); k++){
                vecReg=new Vector();
                vecReg.add(objImp33_02.INT_TBL_DAT_LIN,"");
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_ITM_MAE,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_ITM_MAE));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_ITM,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_ITM));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_ALT_ITM,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_ALT_ITM));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_LET_ITM,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_LET_ITM));
                vecReg.add(objImp33_02.INT_TBL_DAT_BUT_ITM,        null);
                vecReg.add(objImp33_02.INT_TBL_DAT_NOM_ITM,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_NOM_ITM));
                vecReg.add(objImp33_02.INT_TBL_DAT_UNI_MED,        "");
                vecReg.add(objImp33_02.INT_TBL_DAT_PES,            objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_PES));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_ARA,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_ARA));
                vecReg.add(objImp33_02.INT_TBL_DAT_NOM_ARA,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_NOM_ARA));
                vecReg.add(objImp33_02.INT_TBL_DAT_DES_ARA,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_DES_ARA));
                vecReg.add(objImp33_02.INT_TBL_DAT_POR_ARA,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_POR_ARA));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_EXP,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_EXP));
                vecReg.add(objImp33_02.INT_TBL_DAT_NOM_EXP,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_NOM_EXP));                          
                vecReg.add(objImp33_02.INT_TBL_DAT_BUT_EXP,        null);
                vecReg.add(objImp33_02.INT_TBL_DAT_CAN_TON_MET,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_CAN_TON_MET));
                vecReg.add(objImp33_02.INT_TBL_DAT_CAN_PZA,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_CAN_PZA));                
                vecReg.add(objImp33_02.INT_TBL_DAT_PRE_UNI,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_PRE_UNI));
                vecReg.add(objImp33_02.INT_TBL_DAT_TOT_FOB_CFR,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_TOT_FOB_CFR));                
                vecReg.add(objImp33_02.INT_TBL_DAT_VAL_FLE,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_VAL_FLE));
                vecReg.add(objImp33_02.INT_TBL_DAT_VAL_CFR,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_VAL_CFR));
                vecReg.add(objImp33_02.INT_TBL_DAT_VAL_CFR_ARA,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_VAL_CFR_ARA));
                
                vecReg.add(objImp33_02.INT_TBL_DAT_VAL_ISD,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_VAL_ISD));
                vecReg.add(objImp33_02.INT_TBL_DAT_CHK_ISD_CRE_TRI,null);
                
                vecReg.add(objImp33_02.INT_TBL_DAT_TOT_ARA,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_TOT_ARA));
                vecReg.add(objImp33_02.INT_TBL_DAT_TOT_GAS,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_TOT_GAS));
                vecReg.add(objImp33_02.INT_TBL_DAT_TOT_COS,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_TOT_COS));
                vecReg.add(objImp33_02.INT_TBL_DAT_COS_UNI,        objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COS_UNI));
                vecReg.add(objImp33_02.INT_TBL_DAT_COS_KGR,        "");
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_EMP_REL,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_EMP));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_LOC_REL,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_LOC));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL,objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_TIP_DOC));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_DOC_REL,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_DOC));
                vecReg.add(objImp33_02.INT_TBL_DAT_COD_REG_REL,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_COD_REG));
                vecReg.add(objImp33_02.INT_TBL_DAT_CAN_NOT_PED,    objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_CAN_NOT_PED));
                vecReg.add(objImp33_02.INT_TBL_DAT_CAN_PEN_NOT_PED,objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_CAN_PEN_NOT_PED));
                vecReg.add(objImp33_02.INT_TBL_DAT_CAN_UTI_PED_EMB,objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_CAN_UTI_PED_EMB));                
                vecReg.add(objImp33_02.INT_TBL_DAT_CAN_PED_EMB_AUX,objUti.getStringValueAt(arlDatDetImp33_03, k, objImp33_03.INT_TBL_ARL_DET_CAN_PED_EMB_AUX));
                vecReg.add(objImp33_02.INT_TBL_DAT_REV_ITM,        "");
                vecDat.add(vecReg);
            }

            if(objImp33_02.asignarVectorModeloDatos(vecDat)){
                vecDat.clear();
            }
            else{
                mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
            }
            objImp33_02.regenerarCalculoCfrAra();

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;



    }

    /**
     * Función que permite llenar el arreglo con datos consultados(Para cabecera)
     * @return true: Si se pudo efectuar la operación de carga de datos
     * <BR> false: Caso contrario.
     */
    private boolean setearArreglosCabeceraDetalle(){
        boolean blnRes=true;
        String strTipNotPedDb="";
        try{

            if(arlDatCabImp33_03==null){//crea el objeto
                arlDatCabImp33_03=new ArrayList();
            }
            arlDatCabImp33_03.clear();

            if(arlDatDetImp33_03==null){//crea el objeto
                arlDatDetImp33_03=new ArrayList();
            }
            arlDatDetImp33_03.clear();

            if(optTmFob.isSelected())
                strTipNotPedDb="1";
            else if(optTmCfr.isSelected())
                strTipNotPedDb="2";
            else if(optPzaFob.isSelected())
                strTipNotPedDb="3";
            else if(optPzaCfr.isSelected())
                strTipNotPedDb="4";

            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                arlRegCabImp33_03=new ArrayList();
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_COD_EMP,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL));
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_COD_LOC,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL));
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_COD_TIP_DOC,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL));
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_DES_COR_TIP_DOC,  "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_DES_LAR_TIP_DOC,  "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_COD_DOC,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL));
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_NUM_DOC,          "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_NUM_PED,          "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_FEC_DOC,          "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_COD_EXP,          "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_NOM_EXP,          "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_NOM_ALT_EXP,      "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_VAL_DOC,          "");
                arlRegCabImp33_03.add(objImp33_03.INT_TBL_ARL_DAT_TIP_NOT_PED,      strTipNotPedDb);
                arlDatCabImp33_03.add(arlRegCabImp33_03);
            }
            //objImp33_03.setNotasPedidoSeleccionadas(arlDatCabImp33_03);//envia los datos de la cabecera a Imp33_03

            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                arlRegDetImp33_03=new ArrayList();
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_ITM_MAE,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ITM_MAE).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_ITM,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ITM).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_ALT_ITM,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_LET_ITM,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LET_ITM).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_NOM_ITM,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_NOM_ITM).toString());
                
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_CAN_TON_MET,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_CAN_NOT_PED,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_NOT_PED).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_CAN_PZA,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PZA).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_CAN_PEN_NOT_PED,  objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PEN_NOT_PED).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_CAN_UTI_PED_EMB,  objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_UTI_PED_EMB).toString());

                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_EXP,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EXP));
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_NOM_EXP,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_NOM_EXP));
                
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_EMP,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL));
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_LOC,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL));
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_TIP_DOC,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL));
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_DOC,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL));
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_REG,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_REG_REL));
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_PES,              objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PES).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COD_ARA,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ARA).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_NOM_ARA,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_NOM_ARA).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_DES_ARA,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_DES_ARA).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_POR_ARA,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_POR_ARA).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_PRE_UNI,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_PRE_UNI).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_TOT_FOB_CFR,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString());                
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_VAL_FLE,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_FLE).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_VAL_CFR,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_VAL_CFR_ARA,      objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR_ARA).toString());
                
                BigDecimal bgdValISD=objUti.redondearBigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_ISD)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_ISD).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_ISD).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4);
                
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_VAL_ISD,          bgdValISD);
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_CHK_ISD_CRE_TRI,  null);
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_TOT_ARA,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_ARA).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_TOT_GAS,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_GAS).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_TOT_COS,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_COS).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_COS_UNI,          objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COS_UNI).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_CAN_PED_EMB_AUX,  objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_CAN_PED_EMB_AUX).toString());
                arlRegDetImp33_03.add(objImp33_03.INT_TBL_ARL_DET_EST_REG,          "");

                arlDatDetImp33_03.add(arlRegDetImp33_03);
            }
            objImp33_03.setDetalleSeleccionado(arlDatDetImp33_03);//devuelve los datos del detalle a Imp33_03
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean crearCuentaPedido(boolean crearCtaPas){
        boolean blnRes=true;
        boolean blnCreCtaPas=crearCtaPas;
        intNivCtaPadAct=-1; intNivCtaPadPas=-1;
        strCodCtaPadPedAct="";
        strCodCtaPadPedPas="";
        objNivAct1=null;        objNivAct2=null;        objNivAct3=null;
        objNivAct4=null;        objNivAct5=null;        objNivAct6=null;
        objNivPas1=null;        objNivPas2=null;        objNivPas3=null;
        objNivPas4=null;        objNivPas5=null;        objNivPas6=null;
        try{
            if(con!=null){
                if(blnCreCtaPas)
                {
                    stm=con.createStatement();

                    //para obtener el nivel de la cuenta padre
                    //ACTIVO
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.co_ctaCabActImp AS co_ctaPad, a2.ne_niv";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp";
                    strSQL+=" WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQL+=" AND a2.co_cta=a1.co_ctaCabActImp";

                    System.out.println("##intNivCtaPadAct: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNivCtaPadAct=rst.getInt("ne_niv");

                    //se obtiene la informacion para armar la cuenta de activo del pedido
                    strSQL="";
                    strSQL+=" SELECT b1.co_emp,";
                    for(int j=1; j<=intNivCtaPadAct; j++){
                        if(j==1)
                            strSQL+=" b1.tx_niv" + j + "";
                        else
                            strSQL+=", b1.tx_niv" + j + "";
                    }
                    strSQL+=", b1.co_ctaPad";
                    strSQL+=", b2.tx_niv" + (intNivCtaPadAct+1) + "";
                    strSQL+=" FROM(";
                    strSQL+="        SELECT a1.co_emp,";
                    for(int j=1; j<=intNivCtaPadAct; j++){
                        if(j==1)
                            strAux="        a1.tx_niv" + j + "";
                        else
                            strAux+="       , a1.tx_niv" + j + "";
                    }
                    strSQL+=strAux;
                    strSQL+="       , a2.co_ctaPad";
                    strSQL+="       FROM tbm_plaCta AS a1 INNER JOIN(";
                    strSQL+="               SELECT a1.co_emp, a1.co_ctaCabActImp AS co_ctaPad, a2.ne_niv";
                    strSQL+="               FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                    strSQL+="               ON a1.co_emp=a2.co_emp";
                    strSQL+="               WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQL+="               AND a2.co_cta=a1.co_ctaCabActImp";
                    strSQL+="       ) AS a2";
                    strSQL+="       ON a1.co_emp=a2.co_emp AND a1.ne_pad=a2.co_ctaPad";
                    strSQL+="       WHERE a1.tx_tipCta='D' AND a1.ne_niv=(a2.ne_niv+1)";
                    strSQL+="       GROUP BY a1.co_emp, ";
                    strSQL+="       " + strAux;
                    strSQL+="       , a2.co_ctaPad";
                    strSQL+=" ) AS b1";
                    strSQL+=" INNER JOIN(";
                    strSQL+="        SELECT a1.co_emp,";
                    strSQL+="              " + strAux;
                    strSQL+="              , (MAX(CAST(a1.tx_niv" + (intNivCtaPadAct+1) + " AS SMALLINT))+1) AS tx_niv" + (intNivCtaPadAct+1) + "";
                    strSQL+="              , a1.ne_pad AS co_ctaPad";
                    strSQL+=" 	     FROM tbm_plaCta AS a1";
                    strSQL+=" 	     WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQL+="        GROUP BY a1.co_emp, ";
                    strSQL+="        " + strAux;
                    strSQL+="        , a1.ne_pad";
                    strSQL+=" ) AS b2";
                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_ctaPad=b2.co_ctaPad";
                    for(int j=1; j<=intNivCtaPadAct; j++){
                        strSQL+=" AND b1.tx_niv" + j + "=b2.tx_niv" + j;
                    }
                    System.out.println("##ACTIVO: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        for(int j=1; j<=(intNivCtaPadAct+1); j++){
                            if(j==1)
                                objNivAct1=j==1?rst.getObject("tx_niv" + j):null;
                            else if(j==2)
                                objNivAct2=j==2?rst.getObject("tx_niv" + j):null;
                            else if(j==3)
                                objNivAct3=j==3?rst.getObject("tx_niv" + j):null;
                            else if(j==4)
                                objNivAct4=j==4?rst.getObject("tx_niv" + j):null;
                            else if(j==5)
                                objNivAct5=j==5?rst.getObject("tx_niv" + j):null;
                            else if(j==6)
                                objNivAct6=j==6?rst.getObject("tx_niv" + j):null;
                        }
                        strCodCtaPadPedAct=rst.getString("co_ctaPad");
                    }

//                if(blnCreCtaPas){
                    //PASIVO
                    //nivel de la cuenta padre del pasivo
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.co_ctaCabPasImp AS co_ctaPad, a2.ne_niv";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp";
                    strSQL+=" WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQL+=" AND a2.co_cta=a1.co_ctaCabPasImp";
                    //System.out.println("intNivCtaPadPas: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNivCtaPadPas=rst.getInt("ne_niv");

                    //se obtiene la informacion para armar la cuenta de pasivo del pedido
                    strSQL="";
                    strSQL+=" SELECT b1.co_emp,";
                    for(int j=1; j<=intNivCtaPadPas; j++){
                        if(j==1)
                            strSQL+=" b1.tx_niv" + j + "";
                        else
                            strSQL+=", b1.tx_niv" + j + "";
                    }
                    strSQL+=", b1.co_ctaPad";
                    strSQL+=", b2.tx_niv" + (intNivCtaPadPas+1) + "";
                    strSQL+=" FROM(";
                    strSQL+="        SELECT a1.co_emp,";
                    for(int j=1; j<=intNivCtaPadPas; j++){
                        if(j==1)
                            strAux="        a1.tx_niv" + j + "";
                        else
                            strAux+="       , a1.tx_niv" + j + "";
                    }
                    strSQL+=strAux;
                    strSQL+="               , a2.co_ctaPad";
                    strSQL+="       FROM tbm_plaCta AS a1 INNER JOIN(";
                    strSQL+="               SELECT a1.co_emp, a1.co_ctaCabPasImp AS co_ctaPad, a2.ne_niv";
                    strSQL+="               FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp";
                    strSQL+="               WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQL+="               AND a2.co_cta=a1.co_ctaCabPasImp";
                    strSQL+="       ) AS a2";
                    strSQL+="       ON a1.co_emp=a2.co_emp AND a1.ne_pad=a2.co_ctaPad";
                    strSQL+="       WHERE a1.tx_tipCta='D' AND a1.ne_niv=(a2.ne_niv+1)";
                    strSQL+="       GROUP BY a1.co_emp, ";
                    strSQL+="               " + strAux;
                    strSQL+="              , a2.co_ctaPad";
                    strSQL+=" ) AS b1";
                    strSQL+=" INNER JOIN(";
                    strSQL+="        SELECT a1.co_emp,";
                    strSQL+="              " + strAux;
                    strSQL+="              , (MAX(CAST(a1.tx_niv" + (intNivCtaPadPas+1) + " AS SMALLINT))+1) AS tx_niv" + (intNivCtaPadPas+1) + "";
                    strSQL+="              , a1.ne_pad AS co_ctaPad";
                    strSQL+="        FROM tbm_plaCta AS a1";
                    strSQL+=" 	     WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQL+="        GROUP BY a1.co_emp, ";
                    strSQL+="                " + strAux;
                    strSQL+="               , a1.ne_pad";
                    strSQL+=" ) AS b2";
                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_ctaPad=b2.co_ctaPad";
                    for(int j=1; j<=intNivCtaPadPas; j++){
                        strSQL+=" AND b1.tx_niv" + j + "=b2.tx_niv" + j;
                    }
                    System.out.println("##PASIVO: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        for(int j=1; j<=(intNivCtaPadPas+1); j++){
                            if(j==1)
                                objNivPas1=j==1?rst.getObject("tx_niv" + j):null;
                            else if(j==2)
                                objNivPas2=j==2?rst.getObject("tx_niv" + j):null;
                            else if(j==3)
                                objNivPas3=j==3?rst.getObject("tx_niv" + j):null;
                            else if(j==4)
                                objNivPas4=j==4?rst.getObject("tx_niv" + j):null;
                            else if(j==5)
                                objNivPas5=j==5?rst.getObject("tx_niv" + j):null;
                            else if(j==6)
                                objNivPas6=j==6?rst.getObject("tx_niv" + j):null;
                        }
                        strCodCtaPadPedPas=rst.getString("co_ctaPad");
                    }
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCtaPed(boolean crearCtaPas){
        int intCodUsr;
        boolean blnRes=true;
        String strSQLIns="";
        boolean blnCreCtaPas=crearCtaPas;
        String strNivTmp="";
        //Inicializa las variables de las cuentas.
        strNumCtaPedAct=""; strNumCtaPedPas="";
        strNomCtaPedAct=""; strNomCtaPedPas="";
        String strNomCta="";
        try{
            if (con!=null){
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //CUENTA DE ACTIVO
                //Obtener el código de la cuenta contable
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_cta) AS co_ctaPed";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + txtCodImp.getText();
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaPedAct=rst.getInt("co_ctaPed");
                }
                intCodCtaPedAct++;
                rst.close();
                rst=null;

                System.out.println("***objImp33_03.getDatExpPedSelGenDia(): " + objImp33_03.getDatExpPedSelGenDia());
                
                if(objImp33_03.getDatExpPedSelGenDia().size()==1){ //Si existe 1 solo exportador en el detalle.
                    //27 Nov 2014 ; Alfredo solicita através de Michelle cambiar de txtNomExp2 al txtNomExp
                    strNomCta+=""+objUti.codificar(txtNomExp.getText() + " (" +  txtNumPedOri.getText() + ")"); //txtNumPed
                }
                else {  //Pedidos Consolidados: Si existe más de un exportador en el detalle.
                    //Se creará una cuenta por pedido y no por exportador
                    strNomCta+=""+ objUti.codificar("Pedido Consolidado (" +  txtNumPedOri.getText() + ")"); //txtNumPed
                }       
                strNomCtaPedAct=strNomCta; strNomCtaPedPas=strNomCta;

                //Obtener la fecha del servidor.
//                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;
                
                if(blnCreCtaPas){
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_plaCta (";
                    strSQL+="       co_emp, co_cta, tx_codAlt, tx_desLar, ne_pad, ne_niv, tx_niv1, tx_niv2, tx_niv3";
                    strSQL+="     , tx_niv4, tx_niv5, tx_niv6, tx_codCta, tx_tipCta, tx_natCta, st_aut, st_ctaFluEfe, st_ctaFluFon, st_ctaPre";
                    strSQL+="     , tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + txtCodImp.getText(); //co_emp
                    strSQL+=", " + intCodCtaPedAct;     //co_cta
                    strSQL+=", Null" ; //tx_codAlt
                    strSQL+=", " + strNomCtaPedAct + ""; //tx_desLar
                    strSQL+=", " + strCodCtaPadPedAct;   //ne_pad  cuenta de tbm_emp
                    strSQL+=", " + (intNivCtaPadAct+1) + ""; //ne_niv

                    strNivTmp=(objNivAct1==null?"":(objNivAct1.toString().length()==1?""+objNivAct1:""+objNivAct1));
                    strSQL+=", " + objUti.codificar((objNivAct1==null?null:(objNivAct1.toString().length()==1?""+objNivAct1:objNivAct1)), 0); //tx_niv1
                    strNumCtaPedAct+=strNivTmp;

                    strNivTmp=(objNivAct2==null?"":(objNivAct2.toString().length()==1?"0"+objNivAct2:""+objNivAct2));
                    strSQL+=", " + objUti.codificar((objNivAct2==null?null:(objNivAct2.toString().length()==1?"0"+objNivAct2:""+objNivAct2)), 0); //tx_niv2
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct3==null?"":(objNivAct3.toString().length()==1?"0"+objNivAct3:""+objNivAct3));
                    strSQL+=", " + objUti.codificar((objNivAct3==null?null:(objNivAct3.toString().length()==1?"0"+objNivAct3:""+objNivAct3)), 0); //tx_niv3
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct4==null?"":(objNivAct4.toString().length()==1?"0"+objNivAct4:""+objNivAct4));
                    strSQL+=", " + objUti.codificar((objNivAct4==null?null:(objNivAct4.toString().length()==1?"0"+objNivAct4:""+objNivAct4)), 0); //tx_niv4
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct5==null?"":(objNivAct5.toString().length()==1?"0"+objNivAct5:""+objNivAct5));
                    strSQL+=", " + objUti.codificar((objNivAct5==null?null:(objNivAct5.toString().length()==1?"0"+objNivAct5:""+objNivAct5)), 0); //tx_niv5
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct6==null?"":(objNivAct6.toString().length()==1?"0"+objNivAct6:""+objNivAct6));
                    strSQL+=", " + objUti.codificar((objNivAct6==null?null:(objNivAct6.toString().length()==1?"0"+objNivAct6:""+objNivAct6)), 0); //tx_niv6
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strSQL+=", '" + strNumCtaPedAct + "'"; //tx_codCta
                    strSQL+=", 'D'"; //tx_tipCta
                    strSQL+=", 'D'"; //tx_natCta
                    strSQL+=", 'N'"; //st_aut
                    strSQL+=", 'N'"; //st_ctaFluEfe
                    strSQL+=", 'N'"; //st_ctaFluFon
                    strSQL+=", 'N'"; //st_ctaPre
                    strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                    strSQL+=", 'A'"; //st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_ing
                    strSQL+=", '" + strAux + "'"; //fe_ultMod
                    strSQL+=", " + intCodUsr; //co_usrIng
                    strSQL+=", " + intCodUsr; //co_usrMod
                    strSQL+=");";
                    strSQL+=" UPDATE tbm_cabpedembimp";
                    strSQL+=" SET co_ctaAct=" + intCodCtaPedAct + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=";";

                    strSQLIns+=strSQL;

//                if(blnCreCtaPas){
                    //CUENTA DE PASIVO
                    intCodCtaPedPas=(intCodCtaPedAct+1);
                    //Obtener la fecha del servidor.
//                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux==null)
//                        return false;

                    strSQL="";
                    strSQL+=" INSERT INTO tbm_plaCta (";
                    strSQL+="       co_emp, co_cta, tx_codAlt, tx_desLar, ne_pad, ne_niv, tx_niv1, tx_niv2, tx_niv3";
                    strSQL+="     , tx_niv4, tx_niv5, tx_niv6, tx_codCta, tx_tipCta, tx_natCta, st_aut, st_ctaFluEfe, st_ctaFluFon, st_ctaPre";
                    strSQL+="     , tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + txtCodImp.getText(); //co_emp
                    strSQL+=", " + intCodCtaPedPas;     //co_cta
                    strSQL+=", Null" ;                  //tx_codAlt
                    strSQL+=", " + strNomCtaPedPas + ""; //tx_desLar
                    strSQL+=", " + strCodCtaPadPedPas; //ne_pad  cuenta de tbm_emp
                    strSQL+=", " + (intNivCtaPadPas+1) + ""; //ne_niv

                    strNivTmp=(objNivPas1==null?"":(objNivPas1.toString().length()==1?""+objNivPas1:""+objNivPas1));
                    strSQL+=", " + objUti.codificar((objNivPas1==null?null:(objNivPas1.toString().length()==1?""+objNivPas1:objNivPas1)), 0); //tx_niv1
                    strNumCtaPedPas+=strNivTmp;

                    strNivTmp=(objNivPas2==null?"":(objNivPas2.toString().length()==1?"0"+objNivPas2:""+objNivPas2));
                    strSQL+=", " + objUti.codificar((objNivPas2==null?null:(objNivPas2.toString().length()==1?"0"+objNivPas2:""+objNivPas2)), 0); //tx_niv2
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas3==null?"":(objNivPas3.toString().length()==1?"0"+objNivPas3:""+objNivPas3));
                    strSQL+=", " + objUti.codificar((objNivPas3==null?null:(objNivPas3.toString().length()==1?"0"+objNivPas3:""+objNivPas3)), 0); //tx_niv3
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas4==null?"":(objNivPas4.toString().length()==1?"0"+objNivPas4:""+objNivPas4));
                    strSQL+=", " + objUti.codificar((objNivPas4==null?null:(objNivPas4.toString().length()==1?"0"+objNivPas4:""+objNivPas4)), 0); //tx_niv4
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas5==null?"":(objNivPas5.toString().length()==1?"0"+objNivPas5:""+objNivPas5));
                    strSQL+=", " + objUti.codificar((objNivPas5==null?null:(objNivPas5.toString().length()==1?"0"+objNivPas5:""+objNivPas5)), 0); //tx_niv5
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas6==null?"":(objNivPas6.toString().length()==1?"0"+objNivPas6:""+objNivPas6));
                    strSQL+=", " + objUti.codificar((objNivPas6==null?null:(objNivPas6.toString().length()==1?"0"+objNivPas6:""+objNivPas6)), 0); //tx_niv6
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strSQL+=", '" + strNumCtaPedPas + "'"; //tx_codCta
                    strSQL+=", 'D'"; //tx_tipCta
                    strSQL+=", 'H'"; //tx_natCta
                    strSQL+=", 'N'"; //st_aut
                    strSQL+=", 'N'"; //st_ctaFluEfe
                    strSQL+=", 'N'"; //st_ctaFluFon
                    strSQL+=", 'N'"; //st_ctaPre
                    strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                    strSQL+=", 'A'"; //st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_ing
                    strSQL+=", '" + strAux + "'"; //fe_ultMod
                    strSQL+=", " + intCodUsr; //co_usrIng
                    strSQL+=", " + intCodUsr; //co_usrMod
                    strSQL+=");";
                    strSQL+=" UPDATE tbm_cabpedembimp";
                    strSQL+=" SET co_ctaPas=" + intCodCtaPedPas + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=";";
                    strSQLIns+=strSQL;
                }
                System.out.println("insertarCtaPed: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCtaPedOtrImp(boolean crearCtaPas){
        int intCodUsr;
        boolean blnRes=true;
        String strSQLIns="";
        boolean blnCreCtaPas=crearCtaPas;
        String strNivTmp="";
        //Inicializa las variables de las cuentas.
        strNumCtaPedAct=""; strNumCtaPedPas="";
        strNomCtaPedAct=""; strNomCtaPedPas="";
        String strNomCta="";
        try{
            if (con!=null){
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //CUENTA DE ACTIVO
                //Obtener el código de la cuenta contable
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_cta) AS co_ctaPed";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + txtCodImp.getText();
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaPedAct=rst.getInt("co_ctaPed");
                }
                intCodCtaPedAct++;
                rst.close();
                rst=null;
                                

                System.out.println("***objImp33_03.getDatExpPedSelGenDia(parametros): " + objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())));
                
                if(objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())).size()==1){ //Si existe 1 solo exportador en el detalle.
                    //27 Nov 2014 ; Alfredo solicita através de Michelle cambiar de txtNomExp2 al txtNomExp
                    strNomCta+=""+objUti.codificar(txtNomExp.getText() + " (" +  txtNumPedOri.getText() + ")"); //txtNumPed
                }
                else {  //Pedidos Consolidados: Si existe más de un exportador en el detalle.
                    //Se creará una cuenta por pedido y no por exportador
                    strNomCta+=""+ objUti.codificar("Pedido Consolidado (" +  txtNumPedOri.getText() + ")"); //txtNumPed
                }       
                strNomCtaPedAct=strNomCta; strNomCtaPedPas=strNomCta;
                

                //Obtener la fecha del servidor.
//                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;
                
                if(blnCreCtaPas){
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_plaCta (";
                    strSQL+="       co_emp, co_cta, tx_codAlt, tx_desLar, ne_pad, ne_niv, tx_niv1, tx_niv2, tx_niv3";
                    strSQL+="     , tx_niv4, tx_niv5, tx_niv6, tx_codCta, tx_tipCta, tx_natCta, st_aut, st_ctaFluEfe, st_ctaFluFon, st_ctaPre";
                    strSQL+="     , tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + txtCodImp.getText(); //co_emp
                    strSQL+=", " + intCodCtaPedAct;     //co_cta
                    strSQL+=", Null" ; //tx_codAlt
                    strSQL+=", " + strNomCtaPedAct + ""; //tx_desLar
                    strSQL+=", " + strCodCtaPadPedAct;   //ne_pad  cuenta de tbm_emp
                    strSQL+=", " + (intNivCtaPadAct+1) + ""; //ne_niv

                    strNivTmp=(objNivAct1==null?"":(objNivAct1.toString().length()==1?""+objNivAct1:""+objNivAct1));
                    strSQL+=", " + objUti.codificar((objNivAct1==null?null:(objNivAct1.toString().length()==1?""+objNivAct1:objNivAct1)), 0); //tx_niv1
                    strNumCtaPedAct+=strNivTmp;

                    strNivTmp=(objNivAct2==null?"":(objNivAct2.toString().length()==1?"0"+objNivAct2:""+objNivAct2));
                    strSQL+=", " + objUti.codificar((objNivAct2==null?null:(objNivAct2.toString().length()==1?"0"+objNivAct2:""+objNivAct2)), 0); //tx_niv2
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct3==null?"":(objNivAct3.toString().length()==1?"0"+objNivAct3:""+objNivAct3));
                    strSQL+=", " + objUti.codificar((objNivAct3==null?null:(objNivAct3.toString().length()==1?"0"+objNivAct3:""+objNivAct3)), 0); //tx_niv3
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct4==null?"":(objNivAct4.toString().length()==1?"0"+objNivAct4:""+objNivAct4));
                    strSQL+=", " + objUti.codificar((objNivAct4==null?null:(objNivAct4.toString().length()==1?"0"+objNivAct4:""+objNivAct4)), 0); //tx_niv4
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct5==null?"":(objNivAct5.toString().length()==1?"0"+objNivAct5:""+objNivAct5));
                    strSQL+=", " + objUti.codificar((objNivAct5==null?null:(objNivAct5.toString().length()==1?"0"+objNivAct5:""+objNivAct5)), 0); //tx_niv5
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct6==null?"":(objNivAct6.toString().length()==1?"0"+objNivAct6:""+objNivAct6));
                    strSQL+=", " + objUti.codificar((objNivAct6==null?null:(objNivAct6.toString().length()==1?"0"+objNivAct6:""+objNivAct6)), 0); //tx_niv6
                    strNumCtaPedAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strSQL+=", '" + strNumCtaPedAct + "'"; //tx_codCta
                    strSQL+=", 'D'"; //tx_tipCta
                    strSQL+=", 'D'"; //tx_natCta
                    strSQL+=", 'N'"; //st_aut
                    strSQL+=", 'N'"; //st_ctaFluEfe
                    strSQL+=", 'N'"; //st_ctaFluFon
                    strSQL+=", 'N'"; //st_ctaPre
                    strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                    strSQL+=", 'A'"; //st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_ing
                    strSQL+=", '" + strAux + "'"; //fe_ultMod
                    strSQL+=", " + intCodUsr; //co_usrIng
                    strSQL+=", " + intCodUsr; //co_usrMod
                    strSQL+=");";
                    strSQL+=" UPDATE tbm_cabpedembimp";
                    strSQL+=" SET co_ctaAct=" + intCodCtaPedAct + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=";";

                    strSQLIns+=strSQL;
                    
                    System.out.println("##CUENTA DE ACTIVO: " + intCodCtaPedAct);

//                if(blnCreCtaPas){
                    //CUENTA DE PASIVO
                    intCodCtaPedPas=(intCodCtaPedAct+1);
                    //Obtener la fecha del servidor.
//                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux==null)
//                        return false;

                    strSQL="";
                    strSQL+=" INSERT INTO tbm_plaCta (";
                    strSQL+="       co_emp, co_cta, tx_codAlt, tx_desLar, ne_pad, ne_niv, tx_niv1, tx_niv2, tx_niv3";
                    strSQL+="     , tx_niv4, tx_niv5, tx_niv6, tx_codCta, tx_tipCta, tx_natCta, st_aut, st_ctaFluEfe, st_ctaFluFon, st_ctaPre";
                    strSQL+="     , tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + txtCodImp.getText(); //co_emp
                    strSQL+=", " + intCodCtaPedPas;     //co_cta
                    strSQL+=", Null" ;                  //tx_codAlt
                    strSQL+=", " + strNomCtaPedPas + ""; //tx_desLar
                    strSQL+=", " + strCodCtaPadPedPas; //ne_pad  cuenta de tbm_emp
                    strSQL+=", " + (intNivCtaPadPas+1) + ""; //ne_niv

                    strNivTmp=(objNivPas1==null?"":(objNivPas1.toString().length()==1?""+objNivPas1:""+objNivPas1));
                    strSQL+=", " + objUti.codificar((objNivPas1==null?null:(objNivPas1.toString().length()==1?""+objNivPas1:objNivPas1)), 0); //tx_niv1
                    strNumCtaPedPas+=strNivTmp;

                    strNivTmp=(objNivPas2==null?"":(objNivPas2.toString().length()==1?"0"+objNivPas2:""+objNivPas2));
                    strSQL+=", " + objUti.codificar((objNivPas2==null?null:(objNivPas2.toString().length()==1?"0"+objNivPas2:""+objNivPas2)), 0); //tx_niv2
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas3==null?"":(objNivPas3.toString().length()==1?"0"+objNivPas3:""+objNivPas3));
                    strSQL+=", " + objUti.codificar((objNivPas3==null?null:(objNivPas3.toString().length()==1?"0"+objNivPas3:""+objNivPas3)), 0); //tx_niv3
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas4==null?"":(objNivPas4.toString().length()==1?"0"+objNivPas4:""+objNivPas4));
                    strSQL+=", " + objUti.codificar((objNivPas4==null?null:(objNivPas4.toString().length()==1?"0"+objNivPas4:""+objNivPas4)), 0); //tx_niv4
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas5==null?"":(objNivPas5.toString().length()==1?"0"+objNivPas5:""+objNivPas5));
                    strSQL+=", " + objUti.codificar((objNivPas5==null?null:(objNivPas5.toString().length()==1?"0"+objNivPas5:""+objNivPas5)), 0); //tx_niv5
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas6==null?"":(objNivPas6.toString().length()==1?"0"+objNivPas6:""+objNivPas6));
                    strSQL+=", " + objUti.codificar((objNivPas6==null?null:(objNivPas6.toString().length()==1?"0"+objNivPas6:""+objNivPas6)), 0); //tx_niv6
                    strNumCtaPedPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strSQL+=", '" + strNumCtaPedPas + "'"; //tx_codCta
                    strSQL+=", 'D'"; //tx_tipCta
                    strSQL+=", 'H'"; //tx_natCta
                    strSQL+=", 'N'"; //st_aut
                    strSQL+=", 'N'"; //st_ctaFluEfe
                    strSQL+=", 'N'"; //st_ctaFluFon
                    strSQL+=", 'N'"; //st_ctaPre
                    strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                    strSQL+=", 'A'"; //st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_ing
                    strSQL+=", '" + strAux + "'"; //fe_ultMod
                    strSQL+=", " + intCodUsr; //co_usrIng
                    strSQL+=", " + intCodUsr; //co_usrMod
                    strSQL+=");";
                    strSQL+=" UPDATE tbm_cabpedembimp";
                    strSQL+=" SET co_ctaPas=" + intCodCtaPedPas + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=";";
                    strSQLIns+=strSQL;
                    
                    
                    System.out.println("##CUENTA DE PASIVO: " + intCodCtaPedPas);
                    
                }
                System.out.println("insertarCtaPed: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    
    
    
    private boolean insertaTbmSalCtaPed(boolean crearCta)
    {
        boolean blnRes=true;
        int intAni=0;
        String strMes="";
        BigDecimal bgdSalCtaPed=new BigDecimal(BigInteger.ZERO);
        boolean blnCreCta=crearCta;
        String strSQLIns="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL=" select extract('year' FROM CURRENT_TIMESTAMP) as anio";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                    intAni=rst.getInt("anio");

                if(blnCreCta)
                {
                    //Activo
                    for(int i=1; i<=12; i++){
                        strMes=(i<=9?"0"+i:""+i);
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_salCta( co_emp, co_cta, co_per, tx_codCta, tx_desLar, nd_salcta, ne_pad, ne_niv, st_regrep)";
                        strSQL+=" VALUES(";
                        strSQL+="  " + txtCodImp.getText() + "";   //co_emp
                        strSQL+=", " + intCodCtaPedAct + "";       //co_cta
                        strSQL+=", " + intAni + "" + strMes + "";  //co_per
                        strSQL+=", '" + strNumCtaPedAct + "'";     //tx_codCta
                        strSQL+=", " + strNomCtaPedAct + "";       //tx_desLar
                        strSQL+=", " + bgdSalCtaPed + "";          //nd_salcta
                        strSQL+=", " + strCodCtaPadPedAct + "";    //ne_pad
                        strSQL+=", " + (intNivCtaPadAct+1)+ "";    //ne_niv
                        strSQL+=", 'I'";                           //st_regrep
                        strSQL+=");";
                        strSQLIns+=strSQL;
                    }

                    //Pasivo
                    for(int i=1; i<=12; i++){
                        strMes=(i<=9?"0"+i:""+i);
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_salCta( co_emp, co_cta, co_per, tx_codCta, tx_desLar, nd_salcta, ne_pad, ne_niv, st_regrep)";
                        strSQL+=" VALUES(";
                        strSQL+="  " + txtCodImp.getText() + "";   //co_emp
                        strSQL+=", " + intCodCtaPedPas + "";       //co_cta
                        strSQL+=", " + intAni + "" + strMes + "";  //co_per
                        strSQL+=", '" + strNumCtaPedPas + "'";     //tx_codCta
                        strSQL+=", " + strNomCtaPedPas + "";       //tx_desLar                        
                        strSQL+=", " + bgdSalCtaPed + "";          //nd_salcta
                        strSQL+=", " + strCodCtaPadPedPas + "";    //ne_pad
                        strSQL+=", " + (intNivCtaPadPas+1) + "";   //ne_niv
                        strSQL+=", 'I'";                           //st_regrep
                        strSQL+=");";
                        strSQLIns+=strSQL;
                    }
                }
                System.out.println("SQL DE insertaTbmSalCtaPed: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean setPermisosUsrCtaPed(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" INSERT INTO tbr_ctaTipDocUsr(co_emp, co_loc, co_tipDoc, co_usr, co_cta)";
                strSQL+=" (	SELECT co_emp, co_loc, co_tipDoc, co_usr, " + intCodCtaPedAct + " AS co_cta";
                strSQL+=" 	FROM tbr_ctaTipDocUsr";
                strSQL+="       WHERE co_emp=" + txtCodImp.getText() + "";
                strSQL+="	AND co_cta=(SELECT ne_pad FROM tbm_plaCta WHERE co_emp=" + txtCodImp.getText() + " AND co_cta=" + intCodCtaPedAct + ")";
                strSQL+="	AND NOT EXISTS(SELECT *FROM tbr_ctaTipDocUsr WHERE co_emp=" + txtCodImp.getText() + " AND co_cta=" + intCodCtaPedAct + ")";
                strSQL+="	GROUP BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+="	ORDER BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+=" );";
                strSQL+=" INSERT INTO tbr_ctaTipDocUsr(co_emp, co_loc, co_tipDoc, co_usr, co_cta)";
                strSQL+=" (	SELECT co_emp, co_loc, co_tipDoc, co_usr, " + intCodCtaPedPas + " AS co_cta";
                strSQL+=" 	FROM tbr_ctaTipDocUsr";
                strSQL+="       WHERE co_emp=" + txtCodImp.getText() + "";
                strSQL+="	AND co_cta=(SELECT ne_pad FROM tbm_plaCta WHERE co_emp=" + txtCodImp.getText() + " AND co_cta=" + intCodCtaPedPas + ")";
                strSQL+="	AND NOT EXISTS(SELECT *FROM tbr_ctaTipDocUsr WHERE co_emp=" + txtCodImp.getText() + " AND co_cta=" + intCodCtaPedPas + ")";
                strSQL+="	GROUP BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+="	ORDER BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+=" );";
                System.out.println("SQL INSERT DE setPermisosUsuarioCuenta : " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean isIngresoImportacion(){
        boolean blnRes=false;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        try{
            if(arlDatCabImp33_03.size()>0){
                conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conIngImp!=null){
                    stmIngImp=conIngImp.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT *FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emprelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_locrelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipdocrelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND a1.co_docrelpedembimp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a1.st_reg NOT IN('I', 'E')";
                    rstIngImp=stmIngImp.executeQuery(strSQL);
                    if(rstIngImp.next())
                        blnRes=true;

                    conIngImp.close();
                    conIngImp=null;
                    stmIngImp.close();
                    stmIngImp=null;
                    rstIngImp.close();
                    rstIngImp=null;
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    private boolean isFacturaImportacion(){
        boolean blnRes=false;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        try{
            if(arlDatCabImp33_03.size()>0){
                conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conIngImp!=null){
                    stmIngImp=conIngImp.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a3.co_empRelPedEmbImp AS co_empPedEmb, a3.co_locRelPedEmbImp AS co_locPedEmb, a3.co_tipDocRelPedEmbImp AS co_tipPedEmb";
                    strSQL+="      , a3.co_docRelPedEmbImp AS co_docPedEmb";
                    strSQL+="      , a3.co_empRelDia AS co_empFacImp, a3.co_locRelDia AS co_locFacImp, a3.co_tipDocRelDia AS co_tipFacImp, a3.co_diaRelDia AS co_diaFacImp";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" INNER JOIN tbm_datGenImp AS a3 ";
                    strSQL+=" ON(a1.co_emp=a3.co_empRelDia AND a1.co_loc=a3.co_locrelDia AND a1.co_tipDoc=a3.co_tipdocrelDia AND a1.co_dia=a3.co_diarelDia)";
                    strSQL+=" WHERE a3.co_empRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a3.co_locRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a3.co_tipDocRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND a3.co_docRelPedEmbImp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_tipDoc, a1.co_dia";                    
                    rstIngImp=stmIngImp.executeQuery(strSQL);
                    if(rstIngImp.next())
                        blnRes=true;

                    conIngImp.close();
                    conIngImp=null;
                    stmIngImp.close();
                    stmIngImp=null;
                    rstIngImp.close();
                    rstIngImp=null;
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    private String getQueryUpdate_tbmCabNotPedImp(){
        boolean blnRes=true;
        strSQLUpdNotPedCie="";
        String strCodDocSelAct="", strCodDocSelAnt="";
        try{
            for(int i=(objTblModImp33_02.getRowCountTrue()-1); i>=0; i--){
                strCodDocSelAct=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();
                if(i==(objTblModImp33_02.getRowCountTrue()-1)){
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabNotPedImp";
                    strSQL+=" SET st_cie='S'";
                    strSQL+=" WHERE co_emp=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL) + "";
                    strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
                    strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                    strSQL+=" AND co_doc IN(" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                    strSQL+=";";
                    strSQLUpdNotPedCie+=strSQL;
                }
                else{
                    if(i==0)
                        strCodDocSelAnt=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();
                    else
                        strCodDocSelAnt=objTblModImp33_02.getValueAt((i-1), objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();

                    if(!strCodDocSelAct.equals(strCodDocSelAnt)){
                        strSQL="";
                        strSQL+=" UPDATE tbm_cabNotPedImp";
                        strSQL+=" SET st_cie='S'";
                        strSQL+=" WHERE co_emp=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL) + "";
                        strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
                        strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                        strSQL+=" AND co_doc IN(" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                        strSQL+=";";
                        strSQLUpdNotPedCie+=strSQL;
                    }  
                }
            }
            System.out.println("strSQLUpdNotPedCie: " + strSQLUpdNotPedCie);
        }
        catch(Exception e){
            blnRes=false;
        }
        return strSQLUpdNotPedCie;
    }
    
    private boolean isChangeFecha(){
        boolean blnRes=false;
        try{
            datFecEmbFin=objUti.parseDate(dtpFecEmb.getText(), "dd/MM/yyyy");
            datFecArrFin=objUti.parseDate(dtpFecArr.getText(), "dd/MM/yyyy");
            
            if( (datFecEmbIni.compareTo(datFecEmbFin)!=0)  ||  (datFecArrIni.compareTo(datFecArrFin)!=0)  ){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean enviarCorEleDptoVta(){
        boolean blnRes=false;
        int intCodCfgCorEle=15;
        try{
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
            
            String strSbjMsg="Sistema Zafiro:   ";
            String strMsg="";
            strMsg+="<html>";
            strMsg+="<body>";
            strMsg+="<h3>Se ha modificado el " + objParSis.getNombreMenu() + "<hr></h3>";
            strMsg+="<table id='objTable' bgcolor='#ffffcc' width='120%' border='1'>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td rowspan='2'>Datos</td>";
            strMsg+="	<td colspan='2'>" + objParSis.getNombreMenu() + " # " + txtNumPedOri.getText() + " ("+txtVerPed.getText()+") </td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Anterior</td>";
            strMsg+="	<td>Actual</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Fecha de embarque:</td>";
            strMsg+="	<td>" + objUti.formatearFecha(datFecEmbIni, "dd/MM/yyyy") + "</td>";
            strMsg+="	<td>" + dtpFecEmb.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Fecha de arribo:</td>";
            strMsg+="	<td>" + objUti.formatearFecha(datFecArrIni, "dd/MM/yyyy") + "</td>";
            strMsg+="	<td>" + dtpFecArr.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#BCA9F5'>";
            strMsg+="	<td>Modificado:</td>";
            strMsg+="	<td>" + objParSis.getNombreUsuario() + "</td>";
            strMsg+="	<td>" + strAux + "</td>";
            strMsg+="</tr>";
            strMsg+="</table>";
            strMsg+="</body>";
            strMsg+="</html> ";

            //Envía el correo.
            if(strMsg.length()>0){
                if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                    //mostrarMsgInf("<HTML>Correo de notificación de Cambio de fecha.</HTML>");
                    blnRes=true;
                }
            }     
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que permite conocer si el Pedido que se está embarcando ya tiene cuentas de activo y pasivo asociadas
     * @return true: Si existen cuentas asociadas.
     * <BR>false: En el caso contrario.
     */    
    private boolean isCuentaNotaPedido(){
        boolean blnRes=false;
        String strCodDocSelAct="", strCodDocSelAnt="";
        try{
            if(con!=null){
                stm=con.createStatement();
                
                for(int i=(objTblModImp33_02.getRowCountTrue()-1); i>=0; i--){
                    strCodDocSelAct=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();
                    if(i==(objTblModImp33_02.getRowCountTrue()-1)){//un solo item
                        strSQL ="";
                        strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
                        strSQL+=" WHERE co_emp=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL) + "";
                        strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
                        strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                        strSQL+=" AND co_doc IN(" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                        strSQL+=" AND a1.co_ctaAct IS NOT NULL AND a1.co_ctaPas IS NOT NULL";
                        strSQL+=";";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            intCodCtaActCreNotPed=rst.getInt("co_ctaAct");
                            intCodCtaPasCreNotPed=rst.getInt("co_ctaPas");
                            blnRes=true;
                            break;
                        }
                    }
                    else{
                        if(i==0)
                            strCodDocSelAnt=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();
                        else
                            strCodDocSelAnt=objTblModImp33_02.getValueAt((i-1), objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();

                        if(!strCodDocSelAct.equals(strCodDocSelAnt)){
                            strSQL ="";
                            strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
                            strSQL+=" WHERE co_emp=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL) + "";
                            strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
                            strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
                            strSQL+=" AND co_doc IN(" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + ")";
                            strSQL+=" AND a1.co_ctaAct IS NOT NULL AND a1.co_ctaPas IS NOT NULL";
                            strSQL+=";";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                intCodCtaActCreNotPed=rst.getInt("co_ctaAct");
                                intCodCtaPasCreNotPed=rst.getInt("co_ctaPas");
                                blnRes=true;
                                break;
                            }
                        }  
                    }
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    /**
     * Función que permite conocer si el Importador de la Nota de Pedido y Pedido Embarcado es el mismo
     * @return true: Si el importador es el mismo en ambas instancias
     * <BR>false: En el caso contrario.
     */    
    private boolean isEqualImportador(){
        boolean blnRes=true;
//        String strCodDocSelAct="", strCodDocSelAnt="";
//        try{
//            if(con!=null){
//                stm=con.createStatement();
//                
//                for(int i=(objTblModImp33_02.getRowCountTrue()-1); i>=0; i--){
//                    strCodDocSelAct=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();
//                    if(i==(objTblModImp33_02.getRowCountTrue()-1)){//un solo item
//                        strSQL="";
//                        strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
//                        strSQL+=" WHERE co_emp=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL) + "";
//                        strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
//                        strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
//                        strSQL+=" AND co_doc IN(" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + ")";
//                        strSQL+=" AND co_imp NOT IN(" + txtCodImp.getText() + ")";
//                        strSQL+=" AND a1.co_ctaAct IS NOT NULL AND a1.co_ctaPas IS NOT NULL";
//                        strSQL+=";";
//                        System.out.println("Fallo por cuenta ya creada: " + strSQL);
//                        rst=stm.executeQuery(strSQL);
//                        if(rst.next()){
//                            System.out.println("ENTRO!!!!");
//                            blnRes=false;
//                            break;
//                        }
//                    }
//                    else{
//                        if(i==0)
//                            strCodDocSelAnt=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();
//                        else
//                            strCodDocSelAnt=objTblModImp33_02.getValueAt((i-1), objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString();
//
//                        if(!strCodDocSelAct.equals(strCodDocSelAnt)){
//                            strSQL="";
//                            strSQL+=" SELECT a1.co_imp, a1.co_ctaAct, a1.co_ctaPas FROM tbm_cabNotPedImp AS a1";
//                            strSQL+=" WHERE co_emp=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL) + "";
//                            strSQL+=" AND co_loc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL) + "";
//                            strSQL+=" AND co_tipDoc=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL) + "";
//                            strSQL+=" AND co_doc IN(" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL) + ")";
//                            strSQL+=" AND co_imp NOT IN(" + txtCodImp.getText() + ")";
//                            strSQL+=";";
//                            System.out.println("2 - Fallo por cuenta ya creada: " + strSQL);
//                            rst=stm.executeQuery(strSQL);
//                            if(rst.next()){
//                                System.out.println("2 - ENTRO!!!!");
//                                blnRes=false;
//                                break;
//                            }
//                        }  
//                    }
//                }
//            }
//        }
//        catch(java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
        return blnRes;
    }
    
    /**
     * Función que permite conocer si la cuenta creada en la Nota de P
     * @return 
     */
    private boolean isCuentaAsociadaSaldo(){
        boolean blnRes=false;
        Connection conPedEmb;
        Statement stmPedEmb;
        ResultSet rstPedEmb;
        try{
            conPedEmb=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPedEmb!=null){
                stmPedEmb=conPedEmb.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_salcta";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_imp=a2.co_emp AND a1.co_ctaAct=a2.co_cta";
                strSQL+=" INNER JOIN tbm_salCta AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc="+ objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" AND a3.nd_salcta<>0";
                strSQL+=" UNION";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_salcta";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_imp=a2.co_emp AND a1.co_ctaPas=a2.co_cta";
                strSQL+=" INNER JOIN tbm_salCta AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" AND a3.nd_salcta<>0";
                //System.out.println("isCuentaAsociadaSaldo: " + strSQL);
                rstPedEmb=stmPedEmb.executeQuery(strSQL);
                if(rstPedEmb.next())
                    blnRes=true;
                conPedEmb.close();
                conPedEmb=null;
                stmPedEmb.close();
                stmPedEmb=null;
                rstPedEmb.close();
                rstPedEmb=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
        
    }

    /**
     * Esta función permite anular un registro.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmPlaCta(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_plaCta";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_IMP);
                strSQL+=" AND co_cta=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_CTAACT);
                strSQL+=";";
                strSQL+=" UPDATE tbm_plaCta";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_IMP);
                strSQL+=" AND co_cta=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_CTAPAS);
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean agregaCtaActCtaPas_NotPed(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabpedembimp";
                strSQL+=" SET co_ctaAct=" + intCodCtaActCreNotPed + "";
                strSQL+="   , co_ctaPas=" + intCodCtaPasCreNotPed + "";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=";";
                System.out.println("agregaCtaActCtaPas_NotPed: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean isItemTerminalesImportacion(){
        boolean blnRes=false;
        String strCodAltItmTer="";
        int intCodAltItmTer=0;
        int intTipTerItm_regTblDat_i=0;
        try{
            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                strCodAltItmTer=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM).toString();
                intCodAltItmTer=(strCodAltItmTer.length()-1);                
                if(strCodAltItmTer.charAt(intCodAltItmTer) == 'I'){
                    intTipTerItm_regTblDat_i++;
                    break;
                }
            }
            if(intTipTerItm_regTblDat_i>0)
                blnRes=true;
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean isItemTerminalesCompraLocal(){
        boolean blnRes=false;
        String strCodAltItmTer="";
        int intCodAltItmTer=0;
        int intTipTerItm_regTblDat_s=0;
        try{
            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                strCodAltItmTer=objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM).toString();
                intCodAltItmTer=(strCodAltItmTer.length()-1);
                if(intCodAltItmTer>=0){
                    if(strCodAltItmTer.charAt(intCodAltItmTer) == 'S'){
                        intTipTerItm_regTblDat_s++;
                        break;
                    }
                }
            }
            if(intTipTerItm_regTblDat_s>0)
                blnRes=true;
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite validar si el precio de venta está acorde al margen de utilidad según 
     * costos de importaciones(Nota de Pedido, Pedido Embarcado, Ingreso por Importación).
     * @return 
     */
    private boolean enviarCorElePreVta(){
        boolean blnRes=false;
        int intNumFil=0;
        Connection conValPreVta;
        Statement stmValPreVta;
        ResultSet rstValPreVta;
        BigDecimal bgdPreVtaItm=new BigDecimal("0");
        //BigDecimal bgdPreVtaDscItm=new BigDecimal("0");//precio de venta menos el 25%
        BigDecimal bgdMarUtiItm=new BigDecimal("0");
        BigDecimal bgdCosUniItm=new BigDecimal("0");//costo unitario del item
        BigDecimal bgdCosUniInsPed=new BigDecimal("0");//Costo unitario de la instancia del pedido
        BigDecimal bgdRelCosUniMarPreVta=new BigDecimal("0");//para la formula    Costo Unitario /    (   1 - (Margen precio vta/100)    )
        String strSbjMsg="" + intNumPedEmbDia + ") " + txtNumPedOri.getText() + " ("+txtVerPed.getText()+") (" + objParSis.getNombreMenu() + ")";
        //String strSbjMsg="2) " + txtNumDoc2.getText() + " (" + objParSis.getNombreMenu() + " - Modificado)";
        boolean blnEstExiPreFueMar=false;//Para saber si existen items que esten fuera del margen del precio de venta
        int intCodCfgCorEle=14;
        try{
            conValPreVta=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conValPreVta!=null){
                stmValPreVta=conValPreVta.createStatement();
                
                String strMsg="";
                strMsg+="<html>";
                strMsg+="<body>";
                strMsg+="<h3>Revisar precios<hr></h3>";
                strMsg+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";
                strMsg+="<tr bgcolor='#F2E0F7'>";
                strMsg+="	<th colspan='7'>Pedido # " + txtNumPedOri.getText() + " ("+txtVerPed.getText()+") </th>";
                strMsg+="</tr>";
                
                strMsg+="<tr bgcolor='#F2E0F7'>";
                strMsg+="	<th>Nº</th>";
                strMsg+="	<th>Cod.Alt.Itm</th>";
                strMsg+="	<th>Cod.Let.Itm</th>";
                strMsg+="	<th>Nom.Itm.</th>";
                strMsg+="	<th>Cos.Uni.Ped.</th>";
                strMsg+="	<th>Pre.Vta.</th>";
                strMsg+="	<th>Cos.Uni.Itm.</th>";
                strMsg+="	<th>Pre.Sug.</th>";
                strMsg+="	<th>Mar.Pre.Vta.M&iacuten.</th>";                        
                strMsg+="</tr>";
                
                //Se ordena por código alterno, para enviar el correo ordenado.
                objImp33_02.ordenarDatPorCodAlt();
                objTblModImp33_02.setModoOperacion(objTblModImp33_02.INT_TBL_NO_EDI);
                objTblModImp33_02.removeEmptyRows();                
                
                for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                    intNumFil++;
                    strSQL ="";
                    strSQL+=" SELECT a1.nd_preVta1, a1.nd_marUti, a1.nd_cosUni FROM tbm_inv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_itm=" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ITM) + "";
                    strSQL+=";";
                    rstValPreVta=stmValPreVta.executeQuery(strSQL);
                    if(rstValPreVta.next()){
                        bgdPreVtaItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_preVta1"), objParSis.getDecimalesMostrar());
                        //bgdPreVtaDscItm=objUti.redondearBigDecimal(bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))), objParSis.getDecimalesMostrar());
                        bgdMarUtiItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_marUti"),objParSis.getDecimalesMostrar());
                        bgdCosUniItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_cosUni"), objParSis.getDecimalesMostrar());
                    }
                    bgdCosUniInsPed=objUti.redondearBigDecimal((new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COS_UNI).toString()))), objParSis.getDecimalesMostrar());
                    //obtencion de la formula
                    bgdRelCosUniMarPreVta=objUti.redondearBigDecimal((bgdCosUniInsPed.divide((   (new BigDecimal("1")).subtract(   bgdMarUtiItm.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)        )        ), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesMostrar());

                    if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("1.005"))), objParSis.getDecimalesMostrar()))>0){//bgdPreVtaDscItm    por debajo del margen  - rojo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FF4000'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                    else if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("0.995"))), objParSis.getDecimalesMostrar()))<0){//bgdPreVtaDscItm     por encima del margen - amarillo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FFFF00'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                    else{//son iguales  - verde
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#D0F5A9'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                }
                
                strMsg+="</table>";
                strMsg+="</body>";
                strMsg+="</html> ";
                
                if(strMsg.length()>0){
                    if(blnEstExiPreFueMar){                    
                        if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                            mostrarMsgInf("<HTML>Estimado usuario, <FONT COLOR=\"blue\">REVISAR PRECIOS</FONT></HTML>");
                            blnRes=true;
                        }
                    }
                }                  
                
                conValPreVta.close();
                conValPreVta=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;     
    }
    
    /**
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veeces
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemRepetido(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblModImp33_02.getRowCountTrue(); j++){
                    if(objTblModImp33_02.compareStringCells(i, objImp33_02.INT_TBL_DAT_COD_ITM, j, objImp33_02.INT_TBL_DAT_COD_ITM)){
                        blnRes=true;
                        break;
                    }
                }
                if(blnRes)
                    break;
            }
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido (instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior al Pedido Embarcado(Instancia anterior: Nota de Pedido)
     * <BR> false Caso contrario
     */
    private boolean getCodSegNotPedImp(){
        boolean blnRes=true;
        int intCodEmpNotPedSel=-1, intCodLocNotPedSel=-1, intCodTipDocNotPedSel=-1, intCodDocNotPedSel=-1;
        try{
            if(con!=null){                
                for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                    if(objTblModImp33_02.getRowCountTrue()>0){
                        intCodEmpNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL).toString());
                        intCodLocNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL).toString());
                        intCodTipDocNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL).toString());
                        intCodDocNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString());
                    }
                    i=objTblModImp33_02.getRowCountTrue(); 
                }                
                
                stm=con.createStatement();
                strSQL="";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPed, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabNotPedImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabNotPedImp AND a1.co_loc=a3.co_locRelCabNotPedImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedImp AND a1.co_doc=a3.co_docRelCabNotPedImp";
                strSQL+=" 	WHERE a1.co_emp=" + intCodEmpNotPedSel + "";
                strSQL+=" 	AND a1.co_loc=" + intCodLocNotPedSel + "";
                strSQL+=" 	AND a1.co_tipDoc=" + intCodTipDocNotPedSel + "";
                strSQL+=" 	AND a1.co_doc=" + intCodDocNotPedSel + "";
                System.out.println("getCodSegNotPedPreImp_DB: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegInsAnt=rst.getObject("co_segNotPed");
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - getCodSegNotPedImp SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - getCodSegNotPedImp: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite realizar la creación de cuentas para el pedido.
     * @return true: Si se pudo realizar proceso de crear cuentas
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_creacionCuentas(){
        boolean blnRes=false;
        boolean blnOptCreCtaPas=true;//SI ES TRUE CREA CUENTA  DE ACTIVO Y PASIVO
        String strCodEmpPedExp="", strCodLocPedExp="", strCodTipDocPedExp="", strCodDocPedExp="", strCodExpPedExp="";
        try{
            System.out.println("A1");
            if (con!=null){
                System.out.println("A2");
                if(chkCreCta.isSelected()){ //siempre se crea la cuenta contable
                    System.out.println("A3");
                    if(isItemTerminalesCompraLocal()){
                        mostrarMsgInf("<HTML>El Pedido Embarcado contiene items con terminales 'S'.<BR>No se debe crear cuentas en el Pedido Embarcado si tiene items con terminales 'S'.<BR>Favor verificar los datos ingresados y volver a intentarlo</HTML>");
                    }
                    else{
                        System.out.println("A4");
                        if(isEqualImportador()){
                            System.out.println("A5");
                            if(isCuentaNotaPedido())
                                mostrarMsgAdv("<HTML>Existen cuentas creadas en la(s) Nota(s) de Pedido.<BR>Recuerde que debe modificar los diarios ya existentes.</HTML>");
                            
                            for(int i=0; i<objImp33_03.getDatExpPedSelGenDia().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                                strCodEmpPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                                strCodLocPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                                strCodTipDocPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                                strCodDocPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                                strCodExpPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                                                                                                
                                if(crearCuentaPedido(blnOptCreCtaPas)){
                                    if(insertarCtaPed(blnOptCreCtaPas)){
                                        if(insertaTbmSalCtaPed(blnOptCreCtaPas)){
                                            if(setPermisosUsrCtaPed()){
                                                if(generaDiarioProvision()){ //Provisión
                                                    blnRes=true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{ //No es el mismo importador en la Nota de Pedido y el Pedido Embarcado
                            mostrarMsgAdv("<HTML>El Pedido Embarcado contiene un importador diferente al de la Nota de Pedido.<BR>Verifique la información y vuelva a intentarlo</HTML>");
                        }
                    }
                }
                else{
                    if(isCuentaNotaPedido()){
                        if(agregaCtaActCtaPas_NotPed()){
                            if(generaDiarioProvision()){ //Provisión
                                blnRes=true;
                            }
                        }                                         
                    }
                    else{
                        //Validar si la cuenta existe en la NOTA DE PEDIDO
                        mostrarMsgInf("<HTML>EL Pedido Embarcado contiene items con terminales 'I'.<BR>No se puede crear un Pedido Embarcado si tiene items con terminales 'I' y no tiene las cuentas asociadas.<BR>Favor verificar los datos ingresados y volver a intentarlo</HTML>");
                    }
                }               
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta función permite realizar la creación de cuentas para el pedido cuando cambia de importador
     * @return true: Si se pudo realizar proceso de crear cuentas
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_creacionCuentasOtrImp(){
        boolean blnRes=false;
        boolean blnOptCreCtaPas=true;//SI ES TRUE CREA CUENTA  DE ACTIVO Y PASIVO
        String strCodEmpPedExp="", strCodLocPedExp="", strCodTipDocPedExp="", strCodDocPedExp="", strCodExpPedExp="";
        try{
            if (con!=null){
                if(chkCreCta.isSelected()){ //siempre se crea la cuenta contable
                    if(isItemTerminalesCompraLocal()){
                        mostrarMsgInf("<HTML>El Pedido Embarcado contiene items con terminales 'S'.<BR>No se debe crear cuentas en el Pedido Embarcado si tiene items con terminales 'S'.<BR>Favor verificar los datos ingresados y volver a intentarlo</HTML>");
                    }
                    else{
                        if(isEqualImportador()){
                            if(isCuentaNotaPedido())
                                mostrarMsgAdv("<HTML>Existen cuentas creadas en la(s) Nota(s) de Pedido.<BR>Recuerde que debe modificar los diarios ya existentes.</HTML>");
                            
                            for(int i=0; i<objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())).size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                                strCodEmpPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                                strCodLocPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                                strCodTipDocPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                                strCodDocPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                                strCodExpPedExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(con, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                                                                                           
                                if(crearCuentaPedido(blnOptCreCtaPas)){
                                    if(insertarCtaPedOtrImp(blnOptCreCtaPas)){
                                        if(insertaTbmSalCtaPed(blnOptCreCtaPas)){
                                            if(setPermisosUsrCtaPed()){
                                                if(generaDiarioProvision()){ //Provisión
                                                    blnRes=true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{ //No es el mismo importador en la Nota de Pedido y el Pedido Embarcado
                            mostrarMsgAdv("<HTML>El Pedido Embarcado contiene un importador diferente al de la Nota de Pedido.<BR>Verifique la información y vuelva a intentarlo</HTML>");
                        }
                    }
                }
                else{
                    if(isCuentaNotaPedido()){
                        if(agregaCtaActCtaPas_NotPed()){
                            if(generaDiarioProvision()){ //Provisión
                                blnRes=true;
                            }
                        }                                         
                    }
                    else{
                        //Validar si la cuenta existe en la NOTA DE PEDIDO
                        mostrarMsgInf("<HTML>EL Pedido Embarcado contiene items con terminales 'I'.<BR>No se puede crear un Pedido Embarcado si tiene items con terminales 'I' y no tiene las cuentas asociadas.<BR>Favor verificar los datos ingresados y volver a intentarlo</HTML>");
                    }
                }               
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite conocer si existe un Valor de Cargo a Pagar con valores nulos, cuando el Pedido Embarcado se está cerrando
     * @return true Si existen valores nulos 
     * <BR> false Caso contrario
     */
    private boolean isValNulCarPag_PedEmbCer(){
        boolean blnRes=false;
        Object objValCarPag=null;
        int intCodReg=-1;
        String strTipCarPagValNul="";
        
        try{            
            for(int i=0; i<objTblModImp33_01.getRowCountTrue(); i++){
                objValCarPag=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                strTipCarPagValNul=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();                
                intCodReg=i;
                
                if(strTipCarPagValNul.equals("G")){
                    if(chkCerPedEmb.isSelected()){
                        if(objValCarPag==null){
                            blnRes=true;
                            objTblModImp33_01.setBackgroundIncompleteRows(Color.RED);
                            //break;
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error - getCodSegNotPedImp: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    /**
     * Función que permite obtener el Código de Local Predeterminado
     * @param conexion Conexión a la base de datos
     * @param codigoEmpresa Código de la empresa
     * @return int El código de local predeterminada
     */
    public boolean getCodLocPre(Connection conexion, int codigoEmpresa){
        boolean blnRes=true;
        intCodLocPre=-1;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_loc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp";
                strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a1.co_emp=" + codigoEmpresa + " AND a2.st_reg='P'";
                strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
                strSQL+=" ORDER BY a1.co_emp";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodLocPre=rst.getInt(("co_loc"));
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error SQL - getCodLocPre: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - getCodLocPre: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite obtener el Código de Local Predeterminado
     * @param conexion Conexión a la base de datos
     * @param codigoEmpresa Código de la empresa
     * @return int El código de local predeterminada
     */
    public String getCodLocPre(Connection conexion, String codigoEmpresa){
        strCodLocAsiDiaAjuNotPed="";
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_loc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp";
                strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a1.co_emp=" + codigoEmpresa + " AND a2.st_reg='P'";
                strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
                strSQL+=" ORDER BY a1.co_emp";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strCodLocAsiDiaAjuNotPed=rst.getString(("co_loc"));
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            strCodLocAsiDiaAjuNotPed="";
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            strCodLocAsiDiaAjuNotPed="";
        }
        return strCodLocAsiDiaAjuNotPed;
    }

    /**
     * Función que permite generar el diario de la provisión
     * @param conexion Conexión a la base de datos
     * @return true Si se pudo efectuar la operación
     * <BR> false Caso contrario
     */
    private boolean generaDiarioProvision(){  
        boolean blnRes=false;
        String strAuxGlo="";
        try{
            //Glosa
            strAuxGlo="";
            strAuxGlo+="" + txtDesCorTipDoc.getText();
            strAuxGlo+=" # " + txtNumDoc.getText();
            strAuxGlo+="; Importador: " + txtNomImp.getText();
            strAuxGlo+="; Exportador: " + txtNomExp.getText();
            strAuxGlo+="; Pedido: " + txtNumPedOri.getText() + "";
            if(txtNumCon.getText().length()>0)
                strAuxGlo+="; Consolidado: " + txtNumCon.getText() + "";
            
            if(con!=null){
                if(chkCerPedEmb.isSelected()){
                    objAsiDiaPro.inicializar();
                    if(getCodLocPre(con, Integer.parseInt(txtCodImp.getText()))){
                        if(objZafImp.getCodDocPro(con, Integer.parseInt(txtCodImp.getText()), intCodLocPre, objZafImp.INT_COD_TIP_DOC_PRO)){
                            if(getCodCtaPedInsExi()){                                
                                if(objAsiDiaPro.generarDiario(con, txtCodImp.getText(), ("" + intCodLocPre), ("" + objZafImp.INT_COD_TIP_DOC_PRO), ("" + objZafImp.getIntCodDocPro())
                                                           , ("" + objParSis.getCodigoEmpresa()), ("" + objParSis.getCodigoLocal()), txtCodTipDoc.getText(), txtCodDoc.getText()
                                                           , objCodSegInsAnt, intCodCtaActMerTra_Pro, txtNumPedOri.getText(), getValGenDiaPro(), getValGenDiaPro(), "N" ))
                                {
                                    objAsiDiaPro.setGlosa(strAuxGlo);
                                    if(objAsiDiaPro.insertarDiarioProvision(con, Integer.parseInt(txtCodImp.getText()), intCodLocPre, objZafImp.INT_COD_TIP_DOC_PRO, objZafImp.getIntCodDocPro()
                                                                          , objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){
                                        System.out.println("****generaDiarioProvision");
                                        blnRes=true;
                                    }
                                }
                            }                        
                        }
                    }
                }
                else
                    blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    /**
     * Función que permite obtener el código de la cuenta de activo del pedido.
     * Si existe el Pedido Embarcado obtiene la cuenta de activo de esta instancia, sino obtiene la cuenta de activo de la Nota de Pedido
     * @param conexion
     * @return 
     */
    private boolean getCodCtaPedInsExi(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_ctaAct, c1.co_ctaPas";
                strSQL+="      , c1.co_empEmb, c1.co_locEmb, c1.co_tipDocEmb, c1.co_docEmb, c1.co_ctaActEmb, c1.co_ctaPasEmb, c1.tx_numDoc2Emb";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_imp, b1.co_ctaAct, b1.co_ctaPas, b1.tx_numDoc2";
                strSQL+=" 	     , b2.co_emp AS co_empEmb, b2.co_loc AS co_locEmb, b2.co_tipDoc AS co_tipDocEmb";
                strSQL+=" 	     , b2.co_doc AS co_docEmb, b2.co_reg AS co_regEmb, b2.co_imp AS co_impEmb, b2.co_ctaAct AS co_ctaActEmb";
                strSQL+=" 	     , b2.co_ctaPas AS co_ctaPasEmb, b2.tx_numDoc2 AS tx_numDoc2Emb";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas, a1.tx_numDoc2";
                strSQL+=" 		FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 	) AS b1";
                strSQL+=" 	LEFT OUTER JOIN(";
                strSQL+=" 		SELECT  a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas, a1.tx_numDoc2";
                strSQL+=" 		      , a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_detPedEmbImp AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 		AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="		AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" 	) AS b2";
                strSQL+=" 	ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_reg=b2.co_regRel";
                strSQL+=" 	WHERE b2.co_emp IS NOT NULL";
                strSQL+=" ) AS c1";
                strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_ctaAct, c1.co_ctaPas";
                strSQL+="        , c1.co_empEmb, c1.co_locEmb, c1.co_tipDocEmb, c1.co_docEmb, c1.co_ctaActEmb, c1.co_ctaPasEmb, c1.tx_numDoc2Emb";
                System.out.println("getCodCtaPedInsExi: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    
                    System.out.println("co_ctaActEmb: " + rst.getObject("co_ctaActEmb"));
                    
                    
                    if(rst.getObject("co_ctaActEmb")==null){
                        System.out.println("IF-CUENTA PEDIDO EMBARCADO NULO");
                        intCodCtaActMerTra_Pro=rst.getInt("co_ctaAct");//Cuenta de Activo de Pedido Embarcado
                        intCodCtaPasMerTra_Pro=rst.getInt("co_ctaPas");//Cuenta de Pasivo de Pedido Embarcado
                    }
                    else{
                        System.out.println("ELSE - CUENTA PEDIDO EMBARCADO NO NULO");
                        intCodCtaActMerTra_Pro=rst.getInt("co_ctaActEmb");//Cuenta de Activo de Nota de Pedido
                        intCodCtaPasMerTra_Pro=rst.getInt("co_ctaPasEmb");//Cuenta de Pasivo de Nota de Pedido
                    }
                }
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - getCodLocPre: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - getCodLocPre: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite obtener el valor Total Fob/Cfr
     * @return BigDecimal el valor
     */
    private BigDecimal getValGenDiaPro(){
        BigDecimal bgdTotFobCfr=BigDecimal.ZERO;
        BigDecimal bgdValTotGas=BigDecimal.ZERO;
        BigDecimal bgdValPro=BigDecimal.ZERO;
        try{
            //bgdTotFobCfr=objUti.redondearBigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesMostrar());
            bgdTotFobCfr=getValTotDxP();
            //System.out.println("SUMA DE DXP: " + bgdTotFobCfr);
            bgdValTotGas=objImp33_01.getTotalProvision();
            //System.out.println("CARGOS-PROV: " + bgdValTotGas);
            bgdValPro=bgdValTotGas.subtract(bgdTotFobCfr);
            //System.out.println("PROVISION: " + bgdValPro);
            
            bgdValPro=objUti.redondearBigDecimal(bgdValPro, objParSis.getDecimalesMostrar());
        }
        catch(Exception e){
            System.out.println("Error - getTotFobCfr: " + e);
            objUti.mostrarMsgErr_F1(null, e);
        }
        return bgdValPro;
    }
    
    /**
     * Función que permite 
     * @return 
     */
    private BigDecimal getValTotDxP(){
        BigDecimal bgdValTotDxP=BigDecimal.ZERO;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc";
                strSQL+="      , c1.tx_numDoc2, SUM(c1.nd_monDebDxP) AS nd_valTotDxP";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc";
                strSQL+=" 	     , b1.tx_numDoc2, b1.co_imp, b1.co_ctaAct, b1.co_ctaPas, b1.st_reg";
                strSQL+=" 	     , b2.co_emp AS co_empDxP, b2.co_loc AS co_locDxP, b2.co_tipDoc AS co_tipDocDxP";
                strSQL+=" 	     , b2.co_doc AS co_docDxP, b2.co_reg AS co_regDxP, b2.st_reg AS st_regDxP";
                strSQL+=" 	     , b2.co_cta AS co_ctaDxP, b2.nd_monDeb AS nd_monDebDxP, b2.nd_monHab AS nd_monHabDxP";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+=" 		     , a1.tx_numDoc2, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas, a1.st_reg";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="               AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" 	) AS b1";
                strSQL+=" 	INNER JOIN(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_reg, a1.st_reg";
                strSQL+=" 		     , a1.nd_valIva, a3.co_cta, a3.nd_monDeb, a3.nd_monHab";
                strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_cabDia AS a2";
                strSQL+=" 		INNER JOIN tbm_detDia AS a3";
                strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia";
                strSQL+=" 		WHERE a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_DOC_POR_PAG + "";
                strSQL+=" 		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_reg";
                strSQL+=" 	) AS b2";
                strSQL+=" 	ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                strSQL+=" ) AS c1";
                strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.tx_numDoc2";
                //System.out.println("getValTotDxP: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    bgdValTotDxP=new BigDecimal(rst.getObject("nd_valTotDxP")==null?"0":(rst.getString("nd_valTotDxP").equals("")?"0":rst.getString("nd_valTotDxP")));
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(Exception e){          
            objUti.mostrarMsgErr_F1(null, e);
        }
        return bgdValTotDxP;
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
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
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
        boolean blnRes=true;
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        strAux="";
        String strSQLRep="", strSQLSubRepAra="", strSQLSubRepItm="", strSQLSubRepCarPag="";
        Connection conRpt;
        Statement stmRpt;
        ResultSet rstRpt;
        String strNomPaiEmbPto="";
        String strValTotDetExp="";
        String strValDocPal="0";
        String strRutImg="";
        String strValFle="";
        ArrayList arlDatExpRep, arlRegExpRep;
        try
        {
            conRpt =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conRpt!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    
                    //Armar sentencia SQL para obtener exportadores
                    stmRpt=conRpt.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT a1.co_exp";
                    strSQL+=" FROM tbm_detPedEmbImp as a1";
                    strSQL+=" INNER JOIN tbm_expImp AS a2 ON(a1.co_exp=a2.co_exp)";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND a1.co_doc="  + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                    strSQL+=" GROUP BY a1.co_exp";
                    strSQL+=" ORDER BY a1.co_Exp";
                    rstRpt=stmRpt.executeQuery(strSQL);
                    arlDatExpRep = new ArrayList();
                    while(rstRpt.next())
                    {
                        arlRegExpRep = new ArrayList();
                        arlRegExpRep.add(0, rstRpt.getInt("co_exp"));
                        arlDatExpRep.add(arlRegExpRep);
                    }
                    rstRpt.close();
                    rstRpt=null;
                    stmRpt.close();
                    stmRpt=null;                       
                    
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)) {
                            
                            for(int k=0;k<arlDatExpRep.size();k++){  
                                stmRpt=conRpt.createStatement();
                                //Cabecera
                                strSQL ="";
                                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.ne_numdoc, a1.tx_numDoc2, a1.ne_tipnotped";
                                strSQL+="      , a1.co_imp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag";
                                strSQL+="      , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                                strSQL+="      , a1.tx_obs2, a1.st_reg, a1.tx_tipemp, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                                strSQL+="      , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp, a3.tx_ruc AS tx_rucEmp";

                                if( (Integer.parseInt(objRptSis.getCodigoReporte(i))==455) || (Integer.parseInt(objRptSis.getCodigoReporte(i))==456) ){
                                    strSQL+="      , (SELECT d1.tx_dir FROM tbm_emp AS d1 WHERE d1.co_emp=1) AS tx_dirEmp";//siempre se muestra la informacion de TUVAL
                                    strSQL+="      , (SELECT d1.tx_tel FROM tbm_emp AS d1 WHERE d1.co_emp=1) AS tx_telEmp";//siempre se muestra la informacion de TUVAL
                                    strSQL+="      , (SELECT d1.tx_fax FROM tbm_emp AS d1 WHERE d1.co_emp=1) AS tx_faxEmp";//siempre se muestra la informacion de TUVAL
                                }
                                else
                                    strSQL+="      , a3.tx_dir AS tx_dirEmp, a3.tx_tel AS tx_telEmp, a3.tx_fax AS tx_faxEmp";

                                strSQL+="      , CAST('ECUADOR' AS CHARACTER VARYING) AS tx_paiEmp";
                                strSQL+="      , a5.co_exp";
                                if(Integer.parseInt(objRptSis.getCodigoReporte(i))==421)//seguro
                                    strSQL+="      , a5.tx_nom2 AS tx_nomExp";
                                else
                                    strSQL+="      , a5.tx_nom AS tx_nomExp";

                                strSQL+="      , a5.tx_dir AS tx_dirExp, a5.tx_tel1 AS tx_telExp1";
                                strSQL+="      , a5.tx_tel2 AS tx_telExp2, a5.tx_fax AS tx_faxExp, a5.tx_perCon AS tx_perConExp";
                                strSQL+="      , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                                strSQL+="      , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                                strSQL+="      , a11.co_pai, a11.tx_desCor AS tx_desCorPaiExp, a11.tx_desLar AS tx_desLarPaiExp";
                                strSQL+="      , a13.co_ciu, a13.tx_desCor AS tx_desCorPueEmb, a13.tx_deslar AS tx_desLarPueEmb";
                                strSQL+="      , a14.co_seg, a14.tx_deslar AS tx_nomSeg";
                                strSQL+="      , a15.co_forpag, a15.tx_deslar AS tx_nomForPag";
                                strSQL+="      , a16.co_pai, a16.tx_desCor AS tx_desCorPaiPto, a16.tx_desLar AS tx_desLarPaiPto";
                                strSQL+="      , a1.tx_numdoc3, a1.tx_direnv, a1.ne_forenv";
                                strSQL+="      , CASE WHEN a1.ne_forenv=1 THEN 'LAND' WHEN a1.ne_forenv=2 THEN 'SEA' WHEN a1.ne_forenv=3 THEN 'AIR' END AS tx_forEnv";
                                strSQL+="      , a1.ne_diacre, (a1.fe_emb + a1.ne_diacre) AS fe_pag";
                                strSQL+="      , SUM(a9.nd_valCfr) AS nd_ValTotCfr";
                                
                                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                                strSQL+=" INNER JOIN tbm_detPedEmbImp as a9 ON (a1.co_emp=a9.co_emp AND a1.co_loc=a9.co_loc AND a1.co_tipDoc=a9.co_tipDoc AND a1.co_doc=a9.co_doc)";
                                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a13 ON(a1.co_ciupueemb=a13.co_ciu)";
                                strSQL+=" LEFT OUTER JOIN tbm_pai AS a16 ON(a13.co_pai=a16.co_pai)";
                                strSQL+=" LEFT OUTER JOIN tbm_segImp AS a14 ON(a1.co_seg=a14.co_seg)";
                                strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a15 ON(a1.co_forpag=a15.co_forpag)";
                                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                                strSQL+=" LEFT OUTER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                                strSQL+=" LEFT OUTER JOIN tbm_expImp AS a5 ON(a9.co_exp=a5.co_exp)";
                                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a10 ON (a5.co_ciu=a10.co_ciu)";
                                strSQL+=" LEFT OUTER JOIN tbm_pai AS a11 ON(a10.co_pai=a11.co_pai)";
                                strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                                strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                                strSQL+=" AND a1.co_doc="  + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                                strSQL+=" AND a9.co_exp=" +  objUti.getStringValueAt(arlDatExpRep, k, 0) ;
                                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.ne_numdoc, a1.tx_numDoc2, a1.ne_tipnotped";
                                strSQL+="        , a1.co_imp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag";
                                strSQL+="        , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                                strSQL+="        , a1.tx_obs2, a1.st_reg, a1.tx_tipemp, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                                strSQL+="        , a3.co_emp, a3.tx_nom, a3.tx_ruc, a3.tx_dir, a3.tx_tel, a3.tx_fax, a5.co_exp";
                                strSQL+="        , a5.tx_nom, a5.tx_dir, a5.tx_tel1, a5.tx_tel2, a5.tx_fax, a5.tx_perCon";
                                strSQL+="        , a7.co_seg, a7.tx_deslar, a8.co_forpag, a8.tx_deslar, a11.co_pai, a11.tx_desCor, a11.tx_desLar";
                                strSQL+="        , a13.co_ciu, a13.tx_desCor, a13.tx_deslar, a14.co_seg, a14.tx_deslar, a15.co_forpag, a15.tx_deslar";
                                strSQL+="        , a16.co_pai, a16.tx_desCor, a16.tx_desLar, a1.tx_numdoc3, a1.tx_direnv, a1.ne_forenv, a1.ne_diacre";   
                                strSQLRep=strSQL;
                                rstRpt=stmRpt.executeQuery(strSQLRep);
                                if(rstRpt.next()){
                                    strNomPaiEmbPto=rstRpt.getObject("tx_desLarPaiPto")==null?"":rstRpt.getString("tx_desLarPaiPto");
                                    strValTotDetExp=rstRpt.getObject("nd_ValTotCfr")==null?"0":rstRpt.getString("nd_ValTotCfr").equals("")?"0":rstRpt.getString("nd_ValTotCfr");
                                }
                                rstRpt.close();
                                rstRpt=null;
                                stmRpt.close();
                                stmRpt=null;                            

                                //Aranceles
                                strSQL ="";
                                strSQL+=" SELECT b1.co_parara, b1.tx_par, CAST ((b1.tx_des || b1.tx_tna) AS CHARACTER VARYING) AS tx_des FROM(";
                                strSQL+=" 	SELECT CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara";
                                strSQL+=" 	     , CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                                strSQL+=" 	     , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                                strSQL+=" 	     , CASE WHEN a5.tx_destna IS NULL THEN '' ELSE '  TNAN:'||a6.nd_ara END AS tx_tna";
                                strSQL+=" 	 FROM (tbm_detpedEmbimp AS b1 INNER JOIN tbm_cabPedEmbImp AS c1";
                                strSQL+=" 		ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc)";
                                strSQL+=" 	 INNER JOIN";
                                strSQL+=" 	 (";
                                strSQL+=" 	    ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                                strSQL+=" 	      LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                                strSQL+=" 	    )";
                                strSQL+=" 	    LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                                strSQL+=" 		            INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                                strSQL+=" 	    ) ON a1.co_grpImp=a4.co_grp";
                                strSQL+=" 	 )";
                                strSQL+=" 	 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                                strSQL+="        INNER JOIN tbm_expImp AS b2 ON (b1.co_exp=b2.co_exp)";
                                strSQL+="    WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                                strSQL+="    AND b1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                                strSQL+="    AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                                strSQL+="    AND b1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                                strSQL+="    AND b2.co_exp=" +  objUti.getStringValueAt(arlDatExpRep, k, 0) ;
                                strSQL+="    AND c1.st_reg='A'";
                                strSQL+="    AND c1.fe_doc BETWEEN a6.fe_vigDes";
                                strSQL+="    AND (CASE WHEN a6.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a6.fe_vigHas END)";
                                strSQL+="    GROUP BY a5.co_parara, a5.tx_par, a5.tx_des,a5.tx_destna, a6.nd_ara";
                                strSQL+="    ORDER BY a5.co_parara";
                                strSQL+=" ) AS b1";                            
                                strSQLSubRepAra=strSQL;

                                //Detalle
                                strSQL ="";
                                strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                                strSQL+="      , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara";
                                strSQL+="      , CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                                strSQL+="      , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                                strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                                if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                    strSQL+=" , ROUND(b1.nd_can,4) AS nd_can";
                                else
                                    strSQL+=" , ROUND(b1.nd_can,0) AS nd_can";
                                strSQL+="     , b1.nd_preUni, b1.nd_valTotFobCfr";
                                if( (optTmFob.isSelected())  ||  (optTmCfr.isSelected()))
                                    strSQL+=" , b1.nd_canTonMet";
                                else
                                    strSQL+=" , b1.nd_can AS nd_canTonMet";
                                strSQL+="     , b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                                strSQL+="     , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                                strSQL+=" FROM tbm_detPedEmbImp AS b1 INNER JOIN";
                                strSQL+=" (";
                                strSQL+="   ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                                strSQL+="   LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                                strSQL+="   )";
                                strSQL+="   LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                                strSQL+=" 	                INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                                strSQL+="   ) ON a1.co_grpImp=a4.co_grp";
                                strSQL+=" )";
                                strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                                strSQL+=" INNER JOIN tbm_expImp AS b2 ON (b1.co_exp=b2.co_exp)";
                                strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                                strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                                strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                                strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                                strSQL+=" AND b2.co_exp=" +  objUti.getStringValueAt(arlDatExpRep, k, 0) ;

                                if(  (Integer.parseInt(objRptSis.getCodigoReporte(i))!=455) && (Integer.parseInt(objRptSis.getCodigoReporte(i))!=456)  )//packing list
                                    strSQL+=" AND (CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END)=$P!{co_parAraItm}";                            

                                strSQL+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                                strSQL+="        , a5.co_parara, a5.tx_par, a5.tx_des, b1.nd_ara";
                                strSQL+="        , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                                strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                                strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                                strSQLSubRepItm=strSQL;

                                //Cargos a Pagar
                                strSQL="";
                                strSQL+=" SELECT a2.co_carPag, a2.tx_nom AS tx_nomCarPag, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                                strSQL+="      , CASE WHEN a2.tx_tipCarPag='O' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleAprCFR";
                                strSQL+="      , CASE WHEN a2.tx_tipCarPag='F' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleFOB";
                                strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                                strSQL+=" ON a1.co_carPag=a2.co_carPag";
                                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                                strSQL+=" AND a2.st_reg IN('A')";
                                strSQL+=" GROUP BY a2.co_carPag, a2.tx_nom, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                                strSQL+=" ORDER BY a2.ne_ubi";
                                strSQLSubRepCarPag=strSQL;

                                stmRpt=conRpt.createStatement();
                                strSQL="";
                                strSQL+=" SELECT a2.co_carPag, a2.tx_nom AS tx_nomCarPag, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                                strSQL+="      , CASE WHEN a2.tx_tipCarPag='O' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleAprCFR";
                                strSQL+="      , CASE WHEN a2.tx_tipCarPag='F' THEN a1.nd_valCarPag ELSE 0 END AS nd_valFleFOB";
                                strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                                strSQL+=" ON a1.co_carPag=a2.co_carPag";
                                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC);
                                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC);
                                strSQL+=" AND a2.st_reg IN('A')  "; 
    //                            if( (optTmFob.isSelected()) || optPzaFob.isSelected() )           //Se comento porque Michelle solicita siempre se imprima el Flete Aproximado y ya no Flete dependiendo del tipo FOB ó CFR
    //                                strSQL+=" AND a2.tx_tipCarPag IN('F')";                       //Se comento porque Michelle solicita siempre se imprima el Flete Aproximado y ya no Flete dependiendo del tipo FOB ó CFR
    //                            else if ( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )     //Se comento porque Michelle solicita siempre se imprima el Flete Aproximado y ya no Flete dependiendo del tipo FOB ó CFR
                                    strSQL+=" AND a2.tx_tipCarPag IN('O')";
                                strSQL+=" GROUP BY a2.co_carPag, a2.tx_nom, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                                strSQL+=" ORDER BY a2.ne_ubi";
                                rstRpt=stmRpt.executeQuery(strSQL);
                                strValFle="0";
                                if(rstRpt.next()){

                                    //Michelle Sánchez solicita que el valor de la impresión se tome del campo de "Flete Aproximado", indiferente que sea FOB o CFR(Esquema anterior si validaba) 

    //                                if( (optTmFob.isSelected()) || optPzaFob.isSelected() ){
    //                                    if(rstRpt.getString("tx_tipCarPag").equals("F"))
    //                                        strValFle=rstRpt.getObject("nd_valFleFOB")==null?"0":rstRpt.getString("nd_valFleFOB").equals("")?"0":rstRpt.getString("nd_valFleFOB");
    //                                }
    //                                else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() ){
    //                                    if(rstRpt.getString("tx_tipCarPag").equals("O"))
                                            strValFle=rstRpt.getObject("nd_valFleAprCFR")==null?"0":rstRpt.getString("nd_valFleAprCFR").equals("")?"0":rstRpt.getString("nd_valFleAprCFR");
    //                                }
                                }                            

                                rstRpt.close();
                                rstRpt=null;
                                stmRpt.close();
                                stmRpt=null;
                                //System.out.println("***PARAMETROS***");
                                //System.out.println("strSQLRep: "+strSQLRep);
                                //System.out.println("strSQLSubRepAra: "+strSQLSubRepAra);
                                //System.out.println("strSQLSubRepItm: "+strSQLSubRepItm);
                                //System.out.println("strSQLSubRepCarPag: "+strSQLSubRepCarPag);

                                //Cantidad en palabras.
                                try{
                                    Librerias.ZafUtil.ZafNumLetra numero;
                                    //double cantidad= objUti.redondear(txtValDoc.getText(), objParSis.getDecimalesMostrar());
                                    //antes exportador por detalle//double cantidad= objUti.redondear(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesMostrar());
                                    double cantidad =objUti.redondear(strValTotDetExp, objParSis.getDecimalesMostrar()); //Valor: Exportador por detalle
                                    String decimales=String.valueOf(cantidad).toString();
                                    decimales=decimales.substring(decimales.indexOf('.') + 1);
                                    decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
                                    int deci= Integer.parseInt(decimales);
                                    int m_pesos=(int)cantidad;
                                    numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
                                    strValDocPal = numero.convertirLetras(m_pesos);
                                    strValDocPal = strValDocPal + " "+decimales+"/100  DOLARES";
                                    strValDocPal=strValDocPal.toUpperCase();
                                    numero=null;
                                }
                                catch(Exception e){
                                    objUti.mostrarMsgErr_F1(this, e);
                                }

                                //strRutImg="D:\\DesaFuentes_Zafiro\\Reportes\\Compras\\ZafImp33";
                                strRutImg=objRptSis.getRutaReporte(i);

                                java.util.Map mapPar = new java.util.HashMap();
                                switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                {
                                    case 421://nota de pedido incompleta(se envia al seguro y a ....)
                                        strRutRpt=objRptSis.getRutaReporte(i);
                                        strNomRpt=objRptSis.getNombreReporte(i);
                                        //Inicializar los parametros que se van a pasar al reporte.
                                        mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "  "+strVer);
                                        mapPar.put("codEmp", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP) );
                                        mapPar.put("codLoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC) );
                                        mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC) );
                                        mapPar.put("codDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC) );
                                        mapPar.put("strSQLRep", strSQLRep);
                                        mapPar.put("strSQLSubRepAra", strSQLSubRepAra);
                                        mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                        mapPar.put("strSQLSubRepCarPag", strSQLSubRepCarPag);
                                        mapPar.put("SUBREPORT_DIR", strRutRpt);
                                        mapPar.put("imgCodImp", txtCodImp.getText());
                                        mapPar.put("NomPaiEmbPto", strNomPaiEmbPto);
                                        //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                        mapPar.put("totMts", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                        mapPar.put("fleApr", new BigDecimal("0"));
                                        mapPar.put("cfrApr", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
                                        mapPar.put("fobApr", new BigDecimal("0"));
                                        mapPar.put("valTotPal", strValDocPal);
                                        //para colocar en la columna de cantidad
                                        if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                            mapPar.put("medCan", "TM");
                                        else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                            mapPar.put("medCan", "PCS");
                                        else
                                            mapPar.put("medCan", "");
                                        //para colocar en el pie de pagina
                                        if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                            mapPar.put("cfrFob", "fob");
                                        else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                            mapPar.put("cfrFob", "cfr");
                                        else
                                            mapPar.put("cfrFob", "");

                                        mapPar.put("imgRut", strRutImg);

                                        objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;                                

                                    case 422://nota de pedido incompleta(se envia al seguro y a ....)
                                        strRutRpt=objRptSis.getRutaReporte(i);
                                        strNomRpt=objRptSis.getNombreReporte(i);
                                        //Inicializar los parametros que se van a pasar al reporte.
                                        mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                        mapPar.put("codEmp", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP) );
                                        mapPar.put("codLoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC) );
                                        mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC) );
                                        mapPar.put("codDoc", objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC) );
                                        mapPar.put("strSQLRep", strSQLRep);
                                        mapPar.put("strSQLSubRepAra", strSQLSubRepAra);
                                        mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                        mapPar.put("SUBREPORT_DIR", strRutRpt);
                                        mapPar.put("imgCodImp", txtCodImp.getText());
                                        mapPar.put("NomPaiEmbPto", strNomPaiEmbPto);
                                        //el valor me sale mal en el reporte asi q mejor lo envio por parametro

                                        if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                            mapPar.put("totMts", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                        }
                                        else{
                                            mapPar.put("totMts", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                        }

                                        mapPar.put("fleApr", new BigDecimal("0"));
                                        mapPar.put("cfrApr", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
                                        mapPar.put("fobApr", new BigDecimal("0"));
                                        mapPar.put("valTotPal", strValDocPal);
                                        //para colocar en la columna de cantidad
                                        if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                            mapPar.put("medCan", "TM");
                                        else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                            mapPar.put("medCan", "PCS");
                                        else
                                            mapPar.put("medCan", "");
                                        //para colocar en el pie de pagina
                                        if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                            mapPar.put("cfrFob", "fob");
                                        else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                            mapPar.put("cfrFob", "cfr");
                                        else
                                            mapPar.put("cfrFob", "");

                                        mapPar.put("imgRut", strRutImg);

                                        objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;

                                    case 455://packing list
                                        if(k==0){ //Solo genera 1 vez el reporte, sin importar la cantidad de exportadores
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);
                                            //Inicializar los parametros que se van a pasar al reporte.
                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                            mapPar.put("SUBREPORT_DIR", strRutRpt);
                                            mapPar.put("imgCodImp", txtCodImp.getText());
                                             //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                            if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                                mapPar.put("totMts", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                            }
                                            else{
                                                mapPar.put("totMts", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                            }                                    
                                            //para colocar en la columna de cantidad
                                            if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                                mapPar.put("medCan", "TM");
                                            else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                                mapPar.put("medCan", "PCS");
                                            else
                                                mapPar.put("medCan", "");
                                            mapPar.put("imgRut", strRutImg);
                                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);                                        
                                        }
                                        break;

                                    case 456://factura
                                        if(k==0){ //Solo genera 1 vez el reporte, sin importar la cantidad de exportadores
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);
                                            //Inicializar los parametros que se van a pasar al reporte.
                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                            mapPar.put("SUBREPORT_DIR", strRutRpt);
                                            mapPar.put("imgCodImp", txtCodImp.getText());
                                             //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                            if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                                mapPar.put("totMts", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                            }
                                            else{
                                                mapPar.put("totMts", new BigDecimal(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp33_02.getTblTot().getValueAt(0, objImp33_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                            }                                    
                                            //para colocar en la columna de cantidad
                                            if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                                mapPar.put("medCan", "TM");
                                            else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                                mapPar.put("medCan", "PCS");
                                            else
                                                mapPar.put("medCan", "");
                                            mapPar.put("imgRut", strRutImg);                                    
                                            mapPar.put("valFle", new BigDecimal(strValFle));

                                            if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                                mapPar.put("isFobCfr", "FOB");
                                            else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                                mapPar.put("isFobCfr", "CFR");

                                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        }
                                        break;                                    

                                    default:
                                        break;

                                }
                            
                            }
                        }
                    }
                }
                conRpt.close();
                conRpt=null;
            }

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }    
    

    public boolean setValoresPedEmbPagImp(){
        boolean blnRes=false;
        try{
            con=conExt;
            if (con!=null){ 
                if(regenerarCarPagDxPImp()){
                    if(actualizar_tbmCabPedEmbImp()){
                        if(eliminar_tbmDetPedEmbImp()){
                            if(insertar_tbmDetPedEmbImp()){
                                if(actualizar_tbmCarPagPedEmbImp()) {
                                    System.out.println("regeneración Pedido Embarcado!!!!!!!!!!!!!!!!!!");
                                    blnRes=true;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar el Pedido Embarcado, con los valores del DxP
     * @return true: Si se pudo actualizar.
     * <BR>false: En el caso contrario.
     */
    public boolean regenerarCarPagDxPImp(){
        boolean blnRes=true;
        try{
            /* Regenera Tabla de Cargos a Pagar */
            objImp33_01.calcularTotalesCargosPagar();
//            objImp33_01.setValorFleteTotal(bgdValFleSav);
//            objImp33_01.setDerechosArancelarios(bgdValAraSav);
//            objImp33_01.setTotalGastos(bgdValGasSav);
            objImp33_02.regenerarCalculos();
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }      
    
    /**
     * Función que permite conocer si el numero de pedido ya existe
     * @return 
     */
    private boolean isExisteNumeroPedido(String strNumPed){
        boolean blnRes=false;
        Connection conExiNumPed;
        Statement stmExiNumPed;
        ResultSet rstExiNumPed;
        try{
            conExiNumPed=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conExiNumPed!=null){
                stmExiNumPed=conExiNumPed.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" WHERE a1.st_Reg IN ('A') AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL)";
                //strSQL+=" AND (CASE WHEN a1.tx_numDoc4 IS NOT NULL THEN a1.tx_numDoc4 ELSE a1.tx_numDoc2 END) LIKE "+objUti.codificar(strNumPed)+"";
                strSQL+=" AND a1.tx_numDoc2 LIKE "+objUti.codificar(strNumPed)+"";
                if(!txtCodDoc.getText().equals("")){
                    strSQL+=" AND a1.co_doc <> "+txtCodDoc.getText(); /* Para que valide entre los demas documentos. */
                }
                System.out.println("strSQL:" + strSQL);
                rstExiNumPed=stmExiNumPed.executeQuery(strSQL);
                if(rstExiNumPed.next())
                    blnRes=true;
                conExiNumPed.close();
                conExiNumPed=null;
                stmExiNumPed.close();
                stmExiNumPed=null;
                rstExiNumPed.close();
                rstExiNumPed=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }    
    
    
    /**
     * Esta función devuelve el local predeterminado de la empresa seleccionada
     * @return entero: si devuelve el local predeterminado
     * <BR>false: En el caso contrario.
     */
    private int getCodigoLocalPredeterminado(int codigoEmpresa){
        int intCodLocPrePedEmb=-1;
        Connection conLocPre;
        Statement stmLocPre;
        ResultSet rstLocPre;
        strCodLocPrePedEmb="";
        String strSqlLocPre="";
        try{
            conLocPre=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLocPre!=null){
                stmLocPre=conLocPre.createStatement();
                strSqlLocPre="";
                strSqlLocPre+=" SELECT co_loc FROM tbm_loc";
                strSqlLocPre+=" WHERE co_emp=" + codigoEmpresa + "";
                strSqlLocPre+=" AND st_reg='P'";
                //System.out.println("getCodigoLocalPredeterminado: " + strSqlLocPre);
                rstLocPre=stmLocPre.executeQuery(strSqlLocPre);
                if(rstLocPre.next()){
                    intCodLocPrePedEmb=rstLocPre.getInt("co_loc");
                    strCodLocPrePedEmb=rstLocPre.getString("co_loc");
                }
                stmLocPre.close();
                stmLocPre=null;
                rstLocPre.close();
                rstLocPre=null;
                conLocPre.close();
                conLocPre=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodLocPrePedEmb;
    }
    
    /**
     * Función que permtie generar el asiento de diario
     * @return 
     */
    private boolean generaAsiDia(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        BigDecimal bgdValTotFobCfr=new BigDecimal("0");
        BigDecimal bgdSumValTotFobCfr=new BigDecimal("0");
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodExp="";
        String strCodEmpTbl="", strCodLocTbl="", strCodTipDocTbl="", strCodDocTbl="", strCodExpTbl="";
        String strCodCtaAct="", strNumCtaAct="", strNomCtaAct="";
        String strCodCtaPas="", strNumCtaPas="", strNomCtaPas="";
        String strGenAsiDia="";
        //Intereses
        BigDecimal bgdValCarInt=BigDecimal.ZERO;
        int intCodCtaCarPag=-1;
        String strCodCtaCarPag="", strNumCtaCarPag="", strNomCtaCarPag="";
        
        try{
            if(con!=null){
                if( (objTooBar.getEstado()=='n') ){
                    ///para generar el asiento de diario
                    objAsiDiaPE.inicializar();            
                    vecDatDia.clear();

                    //por proveedor y pedido - inicio
                    for(int i=0; i<objImp33_03.getDatExpPedSelGenDia().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                        strCodEmp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                        strCodLoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                        strCodTipDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                        strCodDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                        strCodExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                        
                        stm=con.createStatement();
                        strSQL="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                        strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas";
                        strSQL+=" FROM tbm_cabNotPedImp AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                        strSQL+=" WHERE b1.co_emp=" + strCodEmp + "";
                        strSQL+=" AND b1.co_loc=" + strCodLoc + "";
                        strSQL+=" AND b1.co_tipDoc=" + strCodTipDoc + "";
                        strSQL+=" AND b1.co_doc=" + strCodDoc + "";
                        System.out.println("*+ACTIVO/PASIVO: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strCodCtaAct=rst.getString("co_ctaAct");
                            strNumCtaAct=rst.getString("tx_codCtaAct");
                            strNomCtaAct=rst.getString("tx_nomCtaAct");
                            strCodCtaPas=rst.getString("co_ctaPas");
                            strNumCtaPas=rst.getString("tx_codCtaPas");
                            strNomCtaPas=rst.getString("tx_nomCtaPas");
                        }
                        rst.close();
                        rst=null;

                        for(int j=0; j<objTblModImp33_02.getRowCountTrue(); j++){//se lee el detalle del pedido embarcado que consta en la tabla del detalle
                            strCodEmpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)).toString());
                            strCodLocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)).toString());
                            strCodTipDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)).toString());
                            strCodDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)).toString());
                            strCodExpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)).toString());
                            
                            if(strCodEmp.equals(strCodEmpTbl)){
                                if(strCodLoc.equals(strCodLocTbl)){
                                    if(strCodTipDoc.equals(strCodTipDocTbl)){
                                        if(strCodDoc.equals(strCodDocTbl)){
                                            if(strCodExp.equals(strCodExpTbl)){
                                                objUti.setStringValueAt(objImp33_03.getDatExpPedSelGenDia(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_ACT, "");
                                                objUti.setStringValueAt(objImp33_03.getDatExpPedSelGenDia(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_PAS, "");
                                                objUti.setStringValueAt(objImp33_03.getDatExpPedSelGenDia(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_TOT_DIA, "");
                                                
                                                bgdValTotFobCfr=new BigDecimal(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                                                bgdSumValTotFobCfr=bgdSumValTotFobCfr.add(bgdValTotFobCfr);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if( bgdSumValTotFobCfr.compareTo(BigDecimal.ZERO)>0  ){
                            if(chkCreCta.isSelected()){
                                //Cuenta 1
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedAct);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPedAct);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPedAct);
                                vecRegDia.add(INT_VEC_DIA_DEB, "" + (objUti.redondearBigDecimal(bgdSumValTotFobCfr, objParSis.getDecimalesMostrar())));
                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                                
                                //Validacion para la cuenta 2
                                bgdValCarInt=BigDecimal.ZERO;
                                for(int j=0; j<objTblModImp33_01.getRowCountTrue(); j++){//obtengo la información la tabla de cargos a pagar - INTERES DE LA FACTURA -
                                    strGenAsiDia=objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA)==null?"":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA).toString();
                                    if(strGenAsiDia.equals("S")){
                                        bgdValCarInt=new BigDecimal(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()));
                                        intCodCtaCarPag=Integer.parseInt(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));
                                        if(intCodCtaCarPag!=0){
                                            strSQL="";
                                            strSQL+=" SELECT a3.co_cta, a3.tx_codCta, a3.tx_desLar";
                                            strSQL+=" FROM tbm_carPagImp AS a1";
                                            strSQL+=" INNER JOIN tbr_carPagImpPlaCta AS a2";
                                            strSQL+=" ON a1.co_carPag=a2.co_carPag";
                                            strSQL+=" INNER JOIN tbm_plaCta AS a3";
                                            strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                                            strSQL+=" WHERE a2.co_emp=" + txtCodImp.getText() + "";//revisar cuando es modificacion - pueden haber cambiado la empresa que importa la mercaderia
                                            strSQL+=" AND a2.co_carPag=" + intCodCtaCarPag + "";
                                            rst=stm.executeQuery(strSQL);
                                            if(rst.next()){
                                                strCodCtaCarPag=rst.getString("co_cta");
                                                strNumCtaCarPag=rst.getString("tx_codCta");
                                                strNomCtaCarPag=rst.getString("tx_desLar");
                                            }
                                            rst.close();
                                            rst=null;
                                                                                        
                                            //Cuenta 2
                                            if(bgdValCarInt.compareTo(BigDecimal.ZERO)>0){
                                                vecRegDia=new java.util.Vector();
                                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaCarPag);//intCodCtaTot
                                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_DEB, "" + (objUti.redondearBigDecimal(bgdValCarInt, objParSis.getDecimalesMostrar())));
                                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                                vecDatDia.add(vecRegDia);                                    
                                            }
                                        }
                                    }
                                }

                                //Cuenta 3    total - haber
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedPas);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPedPas);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPedPas);
                                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal((bgdSumValTotFobCfr.add(bgdValCarInt)), objParSis.getDecimalesMostrar()) );
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                            }
                        }
                        bgdSumValTotFobCfr=BigDecimal.ZERO;
                    }
                    //por proveedor y pedido - fin

                    System.out.println("generaAsiDia-vecDatDia: " + vecDatDia.toString());
                    objAsiDiaPE.setDetalleDiario(vecDatDia);
                    objAsiDiaPE.setGeneracionDiario((byte)2);
                    
                    strAux="";
                    strAux+="" + txtDesCorTipDoc.getText();
                    strAux+=" # " + txtNumDoc.getText();
                    strAux+="; Importador: " + txtNomImp.getText();
                    strAux+="; Exportador: " + txtNomExp2.getText();
                    strAux+="; Pedido: " + txtNumPedOri.getText()+" ("+txtVerPed.getText()+")";
                    //strAux+="; Pedido: " + txtVerNumPed.getText()+"";
                    objAsiDiaPE.setGlosa(strAux);
                    stm.close();
                    stm=null;
                }
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean generaAsiDiaNuePE(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        BigDecimal bgdValTotFobCfr=new BigDecimal("0");
        BigDecimal bgdSumValTotFobCfr=new BigDecimal("0");
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodExp="";
        String strCodEmpTbl="", strCodLocTbl="", strCodTipDocTbl="", strCodDocTbl="", strCodExpTbl="";
        String strCodCtaAct="", strNumCtaAct="", strNomCtaAct="";
        String strCodCtaPas="", strNumCtaPas="", strNomCtaPas="";
        String strGenAsiDia="";
        //Intereses
        BigDecimal bgdValCarInt=BigDecimal.ZERO;
        int intCodCtaCarPag=-1;
        String strCodCtaCarPag="", strNumCtaCarPag="", strNomCtaCarPag="";
        
        try{
            if(con!=null){
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') || (objTooBar.getEstado()=='a') || (objTooBar.getEstado()=='e') || (objTooBar.getEstado()=='j')  ){//x,m: modificar, a:anular, e:eliminar
                    ///para generar el asiento de diario
                    objAsiDiaPE.inicializar();
                    vecDatDia.clear();

                    //por proveedor y pedido - inicio
                    for(int i=0; i<objImp33_03.getArregloDatoExpPedEmbDb().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                        System.out.println("**DD");
                        strCodEmp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                        strCodLoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                        strCodTipDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                        strCodDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                        strCodExp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                        
                        stm=con.createStatement();
                        strSQL="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                        strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas";
                        strSQL+=" FROM tbm_cabPedEmbImp AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                        strSQL+=" WHERE b1.co_emp=" + intCodEmpPedEmbOri + "";
                        strSQL+=" AND b1.co_loc=" + intCodLocPedEmbOri + "";
                        strSQL+=" AND b1.co_tipDoc=" + intCodTipDocPedEmbOri + "";
                        strSQL+=" AND b1.co_doc=" + intCodDocPedEmbOri + "";
                        System.out.println("ACTIVO/PASIVO: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strCodCtaAct=rst.getString("co_ctaAct");
                            strNumCtaAct=rst.getString("tx_codCtaAct");
                            strNomCtaAct=rst.getString("tx_nomCtaAct");
                            strCodCtaPas=rst.getString("co_ctaPas");
                            strNumCtaPas=rst.getString("tx_codCtaPas");
                            strNomCtaPas=rst.getString("tx_nomCtaPas");
                        }
                        
                        strSQL="";
                        strSQL+=" UPDATE tbm_cabpedembimp";
                        strSQL+=" SET co_ctaAct=" + strCodCtaAct + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=";";
                        strSQL+=" UPDATE tbm_cabpedembimp";
                        strSQL+=" SET co_ctaPas=" + strCodCtaPas + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=";";
                        System.out.println("CUENTAS update ***: " + strSQL);
                        stm.executeUpdate(strSQL);
                        
                        System.out.println("strCodCtaAct: " + strCodCtaAct);
                        System.out.println("strNumCtaAct: " + strNumCtaAct);
                        System.out.println("strNomCtaAct: " + strNomCtaAct);
                        System.out.println("strCodCtaPas: " + strCodCtaPas);
                        System.out.println("strNumCtaPas: " + strNumCtaPas);
                        System.out.println("strNomCtaPas: " + strNomCtaPas);

                        for(int j=0; j<objTblModImp33_02.getRowCountTrue(); j++){//se lee el detalle del pedido embarcado que consta en la tabla del detalle
                            strCodEmpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)).toString());
                            strCodLocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)).toString());
                            strCodTipDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)).toString());
                            strCodDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)).toString());
                            strCodExpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)).toString());
                            
                            if(strCodEmp.equals(strCodEmpTbl)){
                                if(strCodLoc.equals(strCodLocTbl)){
                                    if(strCodTipDoc.equals(strCodTipDocTbl)){
                                        if(strCodDoc.equals(strCodDocTbl)){
                                            if(strCodExp.equals(strCodExpTbl)){
                                                objUti.setStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_ACT, "");
                                                objUti.setStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_PAS, "");
                                                objUti.setStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_TOT_DIA, "");
                                                
                                                bgdValTotFobCfr=new BigDecimal(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                                                bgdSumValTotFobCfr=bgdSumValTotFobCfr.add(bgdValTotFobCfr);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if( bgdSumValTotFobCfr.compareTo(BigDecimal.ZERO)>0  ){
                            if(chkCreCta.isSelected()){
                                //Cuenta 1
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaAct);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaAct);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaAct);
                                vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal(bgdSumValTotFobCfr, objParSis.getDecimalesMostrar()));
                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                                
                                //Validacion para la cuenta 2
                                bgdValCarInt=BigDecimal.ZERO;
                                for(int j=0; j<objTblModImp33_01.getRowCountTrue(); j++){//obtengo la información la tabla de cargos a pagar - INTERES DE LA FACTURA -
                                    strGenAsiDia=objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA)==null?"":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA).toString();
                                    if(strGenAsiDia.equals("S")){
                                        bgdValCarInt=new BigDecimal(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()));
                                        intCodCtaCarPag=Integer.parseInt(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));
                                        if(intCodCtaCarPag!=0){
                                            strSQL="";
                                            strSQL+=" SELECT a3.co_cta, a3.tx_codCta, a3.tx_desLar";
                                            strSQL+=" FROM tbm_carPagImp AS a1";
                                            strSQL+=" INNER JOIN tbr_carPagImpPlaCta AS a2";
                                            strSQL+=" ON a1.co_carPag=a2.co_carPag";
                                            strSQL+=" INNER JOIN tbm_plaCta AS a3";
                                            strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                                            strSQL+=" WHERE a2.co_emp=" + txtCodImp.getText() + "";//revisar cuando es modificacion - pueden haber cambiado la empresa que importa la mercaderia
                                            strSQL+=" AND a2.co_carPag=" + intCodCtaCarPag + "";
                                            //System.out.println("FALLO: " + strSQL);
                                            rst=stm.executeQuery(strSQL);
                                            if(rst.next()){
                                                strCodCtaCarPag=rst.getString("co_cta");
                                                strNumCtaCarPag=rst.getString("tx_codCta");
                                                strNomCtaCarPag=rst.getString("tx_desLar");
                                            }
                                                                                        
                                            //Cuenta 2
                                            if(bgdValCarInt.compareTo(BigDecimal.ZERO)>0){
                                                vecRegDia=new java.util.Vector();
                                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaCarPag);//intCodCtaTot
                                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal(bgdValCarInt, objParSis.getDecimalesMostrar()));
                                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                                vecDatDia.add(vecRegDia);                                    
                                            }
                                        }
                                    }
                                }

                                //Cuenta 3    total - haber
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPas);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPas);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPas);
                                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal((bgdSumValTotFobCfr.add(bgdValCarInt)), objParSis.getDecimalesMostrar()));//
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                            }
                        }
                        bgdSumValTotFobCfr=BigDecimal.ZERO;
                    }
                    //por proveedor y pedido - fin

                    System.out.println("generaAsiDiaNuePE-vecDatDia: " + vecDatDia.toString());
                    objAsiDiaPE.setDetalleDiario(vecDatDia);
                    objAsiDiaPE.setGeneracionDiario((byte)2);
                    
                    strAux="";
                    strAux+="" + txtDesCorTipDoc.getText();
                    strAux+=" # " + txtNumDoc.getText();
                    strAux+="; Importador: " + txtNomImp.getText();
                    strAux+="; Exportador: " + txtNomExp2.getText();
                    strAux+="; Pedido: " + txtNumPedOri.getText()+" ("+txtVerPed.getText()+")";
                    //strAux+="; Pedido: " + txtVerNumPed.getText()+"";
                    objAsiDiaPE.setGlosa(strAux);
                }
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean generaAsiDiaNuePEOtrImp(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        BigDecimal bgdValTotFobCfr=new BigDecimal("0");
        BigDecimal bgdSumValTotFobCfr=new BigDecimal("0");
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodExp="";
        String strCodEmpTbl="", strCodLocTbl="", strCodTipDocTbl="", strCodDocTbl="", strCodExpTbl="";
        String strCodCtaAct="", strNumCtaAct="", strNomCtaAct="";
        String strCodCtaPas="", strNumCtaPas="", strNomCtaPas="";
        String strGenAsiDia="";
        //Intereses
        BigDecimal bgdValCarInt=BigDecimal.ZERO;
        int intCodCtaCarPag=-1;
        String strCodCtaCarPag="", strNumCtaCarPag="", strNomCtaCarPag="";
        
        try{
            if(con!=null){
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') || (objTooBar.getEstado()=='a') || (objTooBar.getEstado()=='e') || (objTooBar.getEstado()=='j')  ){//x,m: modificar, a:anular, e:eliminar
                    ///para generar el asiento de diario
                    objAsiDiaPE.inicializar();
                    vecDatDia.clear();

                    //por proveedor y pedido - inicio
                    for(int i=0; i<objImp33_03.getArregloDatoExpPedEmbDb().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                        System.out.println("**DD");
                        strCodEmp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                        strCodLoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                        strCodTipDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                        strCodDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                        strCodExp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                        
                        stm=con.createStatement();
                        
                        strSQL="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                        strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas";
                        strSQL+=" FROM tbm_cabPedEmbImp AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                        strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND b1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND b1.co_doc=" + txtCodDoc.getText() + "";
                        System.out.println("ACTIVO/PASIVO: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strCodCtaAct=rst.getString("co_ctaAct");
                            strNumCtaAct=rst.getString("tx_codCtaAct");
                            strNomCtaAct=rst.getString("tx_nomCtaAct");
                            strCodCtaPas=rst.getString("co_ctaPas");
                            strNumCtaPas=rst.getString("tx_codCtaPas");
                            strNomCtaPas=rst.getString("tx_nomCtaPas");
                        }
                        

                        
                        System.out.println("strCodCtaAct: " + strCodCtaAct);
                        System.out.println("strNumCtaAct: " + strNumCtaAct);
                        System.out.println("strNomCtaAct: " + strNomCtaAct);
                        System.out.println("strCodCtaPas: " + strCodCtaPas);
                        System.out.println("strNumCtaPas: " + strNumCtaPas);
                        System.out.println("strNomCtaPas: " + strNomCtaPas);

                        for(int j=0; j<objTblModImp33_02.getRowCountTrue(); j++){//se lee el detalle del pedido embarcado que consta en la tabla del detalle
                            strCodEmpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)).toString());
                            strCodLocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)).toString());
                            strCodTipDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)).toString());
                            strCodDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)).toString());
                            strCodExpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)).toString());
                            
                            if(strCodEmp.equals(strCodEmpTbl)){
                                if(strCodLoc.equals(strCodLocTbl)){
                                    if(strCodTipDoc.equals(strCodTipDocTbl)){
                                        if(strCodDoc.equals(strCodDocTbl)){
                                            if(strCodExp.equals(strCodExpTbl)){
                                                objUti.setStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_ACT, "");
                                                objUti.setStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_PAS, "");
                                                objUti.setStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_TOT_DIA, "");
                                                
                                                bgdValTotFobCfr=new BigDecimal(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                                                bgdSumValTotFobCfr=bgdSumValTotFobCfr.add(bgdValTotFobCfr);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if( bgdSumValTotFobCfr.compareTo(BigDecimal.ZERO)>0  ){
                            if(chkCreCta.isSelected()){
                                //Cuenta 1
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaAct);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaAct);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaAct);
                                vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal(bgdSumValTotFobCfr, objParSis.getDecimalesMostrar()));
                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                                
                                //Validacion para la cuenta 2
                                bgdValCarInt=BigDecimal.ZERO;
                                for(int j=0; j<objTblModImp33_01.getRowCountTrue(); j++){//obtengo la información la tabla de cargos a pagar - INTERES DE LA FACTURA -
                                    strGenAsiDia=objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA)==null?"":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA).toString();
                                    if(strGenAsiDia.equals("S")){
                                        bgdValCarInt=new BigDecimal(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()));
                                        intCodCtaCarPag=Integer.parseInt(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));
                                        if(intCodCtaCarPag!=0){
                                            strSQL="";
                                            strSQL+=" SELECT a3.co_cta, a3.tx_codCta, a3.tx_desLar";
                                            strSQL+=" FROM tbm_carPagImp AS a1";
                                            strSQL+=" INNER JOIN tbr_carPagImpPlaCta AS a2";
                                            strSQL+=" ON a1.co_carPag=a2.co_carPag";
                                            strSQL+=" INNER JOIN tbm_plaCta AS a3";
                                            strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                                            strSQL+=" WHERE a2.co_emp=" + txtCodImp.getText() + "";//revisar cuando es modificacion - pueden haber cambiado la empresa que importa la mercaderia
                                            strSQL+=" AND a2.co_carPag=" + intCodCtaCarPag + "";
                                            //System.out.println("FALLO: " + strSQL);
                                            rst=stm.executeQuery(strSQL);
                                            if(rst.next()){
                                                strCodCtaCarPag=rst.getString("co_cta");
                                                strNumCtaCarPag=rst.getString("tx_codCta");
                                                strNomCtaCarPag=rst.getString("tx_desLar");
                                            }
                                                                                        
                                            //Cuenta 2
                                            if(bgdValCarInt.compareTo(BigDecimal.ZERO)>0){
                                                vecRegDia=new java.util.Vector();
                                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaCarPag);//intCodCtaTot
                                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal(bgdValCarInt, objParSis.getDecimalesMostrar()));
                                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                                vecDatDia.add(vecRegDia);                                    
                                            }
                                        }
                                    }
                                }

                                //Cuenta 3    total - haber
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPas);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPas);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPas);
                                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal((bgdSumValTotFobCfr.add(bgdValCarInt)), objParSis.getDecimalesMostrar()));
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                            }
                        }
                        bgdSumValTotFobCfr=BigDecimal.ZERO;
                    }
                    //por proveedor y pedido - fin

                    System.out.println("generaAsiDiaNuePE-vecDatDia: " + vecDatDia.toString());
                    objAsiDiaPE.setDetalleDiario(vecDatDia);
                    objAsiDiaPE.setGeneracionDiario((byte)2);
                    
                    strAux="";
                    strAux+="" + txtDesCorTipDoc.getText();
                    strAux+=" # " + txtNumDoc.getText();
                    strAux+="; Importador: " + txtNomImp.getText();
                    strAux+="; Exportador: " + txtNomExp2.getText();
                    strAux+="; Pedido: " + txtNumPedOri.getText()+" ("+txtVerPed.getText()+")";
                    //strAux+="; Pedido: " + txtVerNumPed.getText()+"";
                    objAsiDiaPE.setGlosa(strAux);
                }
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
   
    
    /**
     * Función que permtie generar el asiento de diario
     * @return 
     */
    private boolean generaAsiDia_RevNP(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodExp="";
        String strCodEmpTbl="", strCodLocTbl="", strCodTipDocTbl="", strCodDocTbl="", strCodExpTbl="";
        String strCodCtaAct="", strNumCtaAct="", strNomCtaAct="";
        String strCodCtaPas="", strNumCtaPas="", strNomCtaPas="";
        try{
            System.out.println("FALLA: " + objImp33_03.getArregloDatoExpPedEmbDb());
            System.out.println("objTooBar: " + objTooBar.getEstado());
            if(con!=null){
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') || (objTooBar.getEstado()=='a') || (objTooBar.getEstado()=='e') || (objTooBar.getEstado()=='j') ){//x,m: modificar, a:anular, e:eliminar
                    ///para generar el asiento de diario
                    objAsiDiaAjuNP.inicializar();
                    vecDatDia.clear();
                    
                    //por proveedor y pedido - inicio
                    for(int i=0; i<objImp33_03.getArregloDatoExpPedEmbDb().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                        
                        strCodEmp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                        strCodLoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                        strCodTipDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                        strCodDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                        strCodExp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                        
                        strCodEmpNotPed=strCodEmp;//co_emp NP
                        strCodLocNotPed=strCodLoc;//co_loc NP
                        strCodTipDocNotPed=strCodTipDoc;//co_tipDoc
                        strCodDocNotPed=strCodDoc;
                        
                        System.out.println("strCodEmpNotPed: " + strCodEmpNotPed);
                        System.out.println("strCodLocNotPed: " + strCodLocNotPed);
                        System.out.println("strCodTipDocNotPed: " + strCodTipDocNotPed);
                        System.out.println("strCodDocNotPed: " + strCodDocNotPed);
                        
                        stm=con.createStatement();
                        strSQL="";
                        strSQL+=" SELECT a1.co_empDiaAju, a1.co_locDiaAju, a1.co_tipDocDiaAju, a1.co_diaDiaAju";
                        strSQL+=" FROM tbm_cabNotPedImp AS a1";
                        strSQL+=" WHERE a1.co_emp=" + strCodEmpNotPed + "";
                        strSQL+=" AND a1.co_loc=" + strCodLocNotPed + "";
                        strSQL+=" AND a1.co_tipDoc=" + strCodTipDocNotPed + "";
                        strSQL+=" AND a1.co_doc=" + strCodDocNotPed + "";
                        strSQL+=";";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strCodEmpNotPed_antAsiDia=rst.getString("co_empDiaAju");
                            strCodLocNotPed_antAsiDia=rst.getString("co_locDiaAju");
                            strCodTipDocNotPed_antAsiDia=rst.getString("co_tipDocDiaAju");
                            strCodDocNotPed_antAsiDia=rst.getString("co_diaDiaAju");
                        }
                        
                        System.out.println("strCodEmpNotPed_antAsiDia: " + strCodEmpNotPed_antAsiDia);
                        System.out.println("strCodLocNotPed_antAsiDia: " + strCodLocNotPed_antAsiDia);
                        System.out.println("strCodTipDocNotPed_antAsiDia: " + strCodTipDocNotPed_antAsiDia);
                        System.out.println("strCodDocNotPed_antAsiDia: " + strCodDocNotPed_antAsiDia);

                        //Nuevo
                        strSQL="";
                        strSQL+=" SELECT (MAX(CASE WHEN b1.co_dia IS NULL THEN 1 ELSE b1.co_dia END)+1) AS co_dia";
                        strSQL+=" FROM(";
                        strSQL+="       SELECT MAX(CASE WHEN a1.co_dia IS NULL THEN 1 ELSE a1.co_dia END) AS co_dia";
                        strSQL+="       FROM tbm_cabDia AS a1";
                        strSQL+="       WHERE a1.co_emp=" + strCodEmpNotPed_antAsiDia + "";
                        strSQL+="       AND a1.co_loc=" + strCodLocNotPed_antAsiDia + "";
                        strSQL+="       AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB + "";
                        strSQL+=" ) AS b1";
                        System.out.println("codigo: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next())
                            strCodDocNotPed_nueAsiDia=rst.getString("co_dia");
                        
                        strSQL="";
                        strSQL+=" SELECT (a1.ne_ultDoc+1) AS ne_ultDoc FROM tbm_cabTipDoc AS a1";
                        strSQL+=" WHERE a1.co_emp=" + strCodEmpNotPed_antAsiDia + "";
                        strSQL+=" AND a1.co_loc=" + strCodLocNotPed_antAsiDia + "";
                        strSQL+=" AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB + "";
                        System.out.println("numero: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next())
                            strNumDocNotPed_nueAsiDia=rst.getString("ne_ultDoc");

                        strSQL="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                        strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas";
                        strSQL+=" FROM tbm_cabNotPedImp AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                        strSQL+=" WHERE b1.co_emp=" + strCodEmp + "";
                        strSQL+=" AND b1.co_loc=" + strCodLoc + "";
                        strSQL+=" AND b1.co_tipDoc=" + strCodTipDoc + "";
                        strSQL+=" AND b1.co_doc=" + strCodDoc + "";
                        System.out.println("CUENTAS ACTIVO/PASIVO: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strCodCtaAct=rst.getString("co_ctaAct");
                            strNumCtaAct=rst.getString("tx_codCtaAct");
                            strNomCtaAct=rst.getString("tx_nomCtaAct");
                            strCodCtaPas=rst.getString("co_ctaPas");
                            strNumCtaPas=rst.getString("tx_codCtaPas");
                            strNomCtaPas=rst.getString("tx_nomCtaPas");
                        }
                        System.out.println("bgdSumValTotFobCfrDb: " + bgdSumValTotFobCfrDb);
                        for(int j=0; j<objTblModImp33_02.getRowCountTrue(); j++){//se lee el detalle del pedido embarcado que consta en la tabla del detalle
                            strCodEmpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)).toString());
                            strCodLocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)).toString());
                            strCodTipDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)).toString());
                            strCodDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)).toString());
                            strCodExpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)).toString());
                        }

                        if(bgdSumValTotFobCfrDb.compareTo(BigDecimal.ZERO)>0  ){
                            if(chkCreCta.isSelected()){
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPas);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPas);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPas);
                                vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal(bgdSumValTotFobCfrDb, objParSis.getDecimalesMostrar()));
                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);

                                //total - haber
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaAct);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaAct);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaAct);
                                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal(bgdSumValTotFobCfrDb, objParSis.getDecimalesMostrar()));
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                            }
                        }
                    }
                    //por proveedor y pedido - fin

                    System.out.println("generaAsiDia_RevNP-vecDatDia: " + vecDatDia.toString());
                    objAsiDiaAjuNP.setDetalleDiario(vecDatDia);
                    objAsiDiaAjuNP.setGeneracionDiario((byte)2);

                    strAux="";
                    strAux+="" + txtDesCorTipDoc.getText();
                    strAux+=" # " + txtNumDoc.getText();
                    strAux+="; Importador: " + txtNomImp.getText();
                    strAux+="; Exportador: " + txtNomExp2.getText();
                    strAux+="; Pedido: " + txtNumPedOri.getText()+" ("+txtVerPed.getText()+")";
                    //strAux+="; Pedido: " + txtVerNumPed.getText()+"";                    
                    objAsiDiaAjuNP.setGlosa(strAux);                    
                }
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
   
    
    /**
     * Función que permtie generar el asiento de diario
     * @return 
     */
    private boolean generaAsiDia_AjuNP(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodExp="";
        String strCodEmpTbl="", strCodLocTbl="", strCodTipDocTbl="", strCodDocTbl="", strCodExpTbl="";
        String strCodCtaAct="", strNumCtaAct="", strNomCtaAct="";
        String strCodCtaPas="", strNumCtaPas="", strNomCtaPas="";
        try{
            if(con!=null){
                
                
                //Ingrid: Problemas cuando es mas de un pedido en el detalle, aunque es el mismo proveedor del exterior
                
                
                
                
                for(int i=0; i<objImp33_03.getDatExpPedSelGenDia().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                    strCodEmp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                    strCodLoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                    strCodTipDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                    strCodDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                    strCodExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);

                    strCodEmpAsiDiaAjuNotPed=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_IMP);
                    strCodLocAsiDiaAjuNotPed=getCodLocPre(con, strCodEmpAsiDiaAjuNotPed);

                    strCodEmpNotPed=strCodEmp;//co_emp NP
                    strCodLocNotPed=strCodLoc;//co_loc NP
                    strCodTipDocNotPed=strCodTipDoc;//co_tipDoc
                    strCodDocNotPed=strCodDoc;
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT (MAX(CASE WHEN b1.co_dia IS NULL THEN 1 ELSE b1.co_dia END)+1) AS co_dia";
                    strSQL+=" FROM(";
                    strSQL+="       SELECT MAX(CASE WHEN a1.co_dia IS NULL THEN 1 ELSE a1.co_dia END) AS co_dia";
                    strSQL+="       FROM tbm_cabDia AS a1";
                    strSQL+="       WHERE a1.co_emp=" + strCodEmpAsiDiaAjuNotPed + "";
                    strSQL+="       AND a1.co_loc=" + strCodLocAsiDiaAjuNotPed + "";
                    strSQL+="       AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB + "";//strCodTipDocNotPed
                    strSQL+=" ) AS b1";
                    System.out.println("codigo: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        strCodDocAjuNotPedPre=rst.getString("co_dia");

                    strSQL="";
                    strSQL+=" SELECT (a1.ne_ultDoc+1) AS ne_ultDoc FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + strCodEmpAsiDiaAjuNotPed + "";
                    strSQL+=" AND a1.co_loc=" + strCodLocAsiDiaAjuNotPed + "";
                    strSQL+=" AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB + "";
                    System.out.println("numero: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        strNumDocAjuNotPedPre=rst.getString("ne_ultDoc");

                }
                ///para generar el asiento de diario
                objAsiDiaAjuNP.inicializar();            
                vecDatDia.clear();
                //por proveedor y pedido - inicio
                for(int i=0; i<objImp33_03.getDatExpPedSelGenDia().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                    strCodEmp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                    strCodLoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                    strCodTipDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                    strCodDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                    strCodExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);

                    System.out.println("***************************************");
                    System.out.println("strCodEmp: " + strCodEmp);
                    System.out.println("strCodLoc: " + strCodLoc);
                    System.out.println("strCodTipDoc: " + strCodTipDoc);
                    System.out.println("strCodDoc: " + strCodDoc);
                    System.out.println("***************************************");


                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                    strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                    strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas";
                    strSQL+=" FROM tbm_cabNotPedImp AS b1";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                    strSQL+=" 	FROM tbm_plaCta AS a1";
                    strSQL+=" ) AS b2";
                    strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                    strSQL+=" 	FROM tbm_plaCta AS a1";
                    strSQL+=" ) AS b3";
                    strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                    strSQL+=" WHERE b1.co_emp=" + strCodEmp + "";
                    strSQL+=" AND b1.co_loc=" + strCodLoc + "";
                    strSQL+=" AND b1.co_tipDoc=" + strCodTipDoc + "";
                    strSQL+=" AND b1.co_doc=" + strCodDoc + "";
                    System.out.println("*cuentas contables: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        strCodCtaAct=rst.getString("co_ctaAct");
                        strNumCtaAct=rst.getString("tx_codCtaAct");
                        strNomCtaAct=rst.getString("tx_nomCtaAct");
                        strCodCtaPas=rst.getString("co_ctaPas");
                        strNumCtaPas=rst.getString("tx_codCtaPas");
                        strNomCtaPas=rst.getString("tx_nomCtaPas");
                    }

                    System.out.println("bgdSumValTotFobCfrDb: " + bgdSumValTotFobCfrDb);

                    if( bgdSumValTotFobCfrDb.compareTo(BigDecimal.ZERO)>0  ){
                        if(chkCreCta.isSelected()){
                            vecRegDia=new java.util.Vector();
                            vecRegDia.add(INT_VEC_DIA_LIN, "");
                            vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPas);//intCodCtaTot
                            vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPas);
                            vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                            vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPas);
                            vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal(bgdSumValTotFobCfrDb, objParSis.getDecimalesMostrar()));
                            vecRegDia.add(INT_VEC_DIA_HAB, "0");
                            vecRegDia.add(INT_VEC_DIA_REF, "");
                            vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                            vecDatDia.add(vecRegDia);

                            //total - haber
                            vecRegDia=new java.util.Vector();
                            vecRegDia.add(INT_VEC_DIA_LIN, "");
                            vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaAct);//intCodCtaTot
                            vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaAct);
                            vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                            vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaAct);
                            vecRegDia.add(INT_VEC_DIA_DEB, "0");
                            vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal(bgdSumValTotFobCfrDb, objParSis.getDecimalesMostrar()));
                            vecRegDia.add(INT_VEC_DIA_REF, "");
                            vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                            vecDatDia.add(vecRegDia);
                        }
                    }
                }
                //por proveedor y pedido - fin

                System.out.println("vecDatDia-generaAsiDia_AjuNP: " + vecDatDia.toString());
                objAsiDiaAjuNP.setDetalleDiario(vecDatDia);
                objAsiDiaAjuNP.setGeneracionDiario((byte)2);

                strAux="";
                strAux+="" + txtDesCorTipDoc.getText();
                strAux+=" # " + txtNumDoc.getText();
                strAux+="; Importador: " + txtNomImp.getText();
                strAux+="; Exportador: " + txtNomExp2.getText();
                strAux+="; Pedido: " + txtNumPedOri.getText()+" ("+txtVerPed.getText()+")";
                //strAux+="; Pedido: " + txtVerNumPed.getText()+"";                               
                objAsiDiaAjuNP.setGlosa(strAux);
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
   
    
    
    
    
    //CAMBIAR LOS DATOS PARA QUE NO LEA LAS CUENTAS CONTABLES DE LA NOTA DE PEDIDO POR LOS DATOS DE CUENTAS DE PEDIDO EMBARCADO
    
    /**
     * Función que permtie generar el asiento de diario
     * @return 
     *///generaAsiDiaNuePE
    private boolean generaAsiDia_RevPE(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodExp="";
        String strCodEmpTbl="", strCodLocTbl="", strCodTipDocTbl="", strCodDocTbl="", strCodExpTbl="";
        String strCodCtaAct="", strNumCtaAct="", strNomCtaAct="";
        String strCodCtaPas="", strNumCtaPas="", strNomCtaPas="";
        
        //Intereses
        String strGenAsiDia="";
        BigDecimal bgdValCarInt=BigDecimal.ZERO;
        int intCodCtaCarPag=-1;
        String strCodCtaCarPag="", strNumCtaCarPag="", strNomCtaCarPag="";
        
        try{
            if(con!=null){
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') || (objTooBar.getEstado()=='a') || (objTooBar.getEstado()=='e') || (objTooBar.getEstado()=='j')  ){//x,m: modificar, a:anular, e:eliminar
                    ///para generar el asiento de diario
                    objAsiDiaAjuPE.inicializar();
                    vecDatDia.clear();

                    //por proveedor y pedido - inicio
                    for(int i=0; i<objImp33_03.getArregloDatoExpPedEmbDb().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                        strCodEmp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                        strCodLoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                        strCodTipDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                        strCodDoc=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                        strCodExp=objUti.getStringValueAt(objImp33_03.getArregloDatoExpPedEmbDb(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                        
                        //cambiar lo de aqui arriba
                        
                        
                        stm=con.createStatement();
                        strSQL="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                        strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas, b1.co_imp";
                        strSQL+=" FROM tbm_cabPedEmbImp AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                        strSQL+=" WHERE b1.co_emp=" + intCodEmpPedEmbOri + "";
                        strSQL+=" AND b1.co_loc=" + intCodLocPedEmbOri + "";
                        strSQL+=" AND b1.co_tipDoc=" + intCodTipDocPedEmbOri + "";
                        strSQL+=" AND b1.co_doc=" + intCodDocPedEmbOri + "";
//                        System.out.println("strCodCtaAct-strNumCtaAct: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strCodCtaAct=rst.getString("co_ctaAct");
                            strNumCtaAct=rst.getString("tx_codCtaAct");
                            strNomCtaAct=rst.getString("tx_nomCtaAct");
                            strCodCtaPas=rst.getString("co_ctaPas");
                            strNumCtaPas=rst.getString("tx_codCtaPas");
                            strNomCtaPas=rst.getString("tx_nomCtaPas");
                            intCodImpOri=rst.getInt("co_imp");
                        }
                        
                        strSQL="";
                        strSQL+=" UPDATE tbm_cabpedembimp";
                        strSQL+=" SET co_ctaAct=" + strCodCtaAct + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=";";
                        strSQL+=" UPDATE tbm_cabpedembimp";
                        strSQL+=" SET co_ctaPas=" + strCodCtaPas + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=";";
                        //System.out.println("CUENTAS ***: " + strSQL);
                        stm.executeUpdate(strSQL);
                        
                        

                        for(int j=0; j<objTblModImp33_02.getRowCountTrue(); j++){//se lee el detalle del pedido embarcado que consta en la tabla del detalle
                            strCodEmpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)).toString());
                            strCodLocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)).toString());
                            strCodTipDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)).toString());
                            strCodDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)).toString());
                            strCodExpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)).toString());
                            
                        }

                        if( bgdSumValTotFobCfrDb.compareTo(BigDecimal.ZERO)>0  ){
                            if(chkCreCta.isSelected()){
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaAct);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaAct);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaAct);
                                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal(bgdSumValTotFobCfrDb, objParSis.getDecimalesMostrar()));//
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                                
                                //Validacion para la cuenta 2
                                bgdValCarInt=BigDecimal.ZERO;
                                for(int j=0; j<objTblModImp33_01.getRowCountTrue(); j++){//obtengo la información la tabla de cargos a pagar - INTERES DE LA FACTURA -
                                    strGenAsiDia=objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA)==null?"":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_GEN_ASI_DIA).toString();
                                    if(strGenAsiDia.equals("S")){
                                        
                                        intCodCtaCarPag=Integer.parseInt(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));
                                        
                                        //bgdValCarInt=new BigDecimal(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()));
                                        
                                        strSQL="";
                                        strSQL+=" SELECT a1.nd_valCarPag ";
                                        strSQL+=" FROM tbm_carPagPedEmbImp AS a1";
                                        strSQL+=" WHERE a1.co_emp=" + intCodEmpPedEmbOri + "";
                                        strSQL+=" AND a1.co_loc=" + intCodLocPedEmbOri + "";
                                        strSQL+=" AND a1.co_tipDoc=" + intCodTipDocPedEmbOri + "";
                                        strSQL+=" AND a1.co_doc=" + intCodDocPedEmbOri + "";
                                        strSQL+=" AND a1.co_carPag=" + intCodCtaCarPag + "";
                                        rst=stm.executeQuery(strSQL);
                                        if(rst.next()){
                                            bgdValCarInt=new BigDecimal(rst.getObject("nd_valCarPag")==null?"0":rst.getString("nd_valCarPag"));  
                                            //bgdValCarInt= rst.getBigDecimal("nd_valCarPag");
                                        }
                                        
                                        if(intCodCtaCarPag!=0){
                                            strSQL="";
                                            strSQL+=" SELECT a3.co_cta, a3.tx_codCta, a3.tx_desLar";
                                            strSQL+=" FROM tbm_carPagImp AS a1";
                                            strSQL+=" INNER JOIN tbr_carPagImpPlaCta AS a2";
                                            strSQL+=" ON a1.co_carPag=a2.co_carPag";
                                            strSQL+=" INNER JOIN tbm_plaCta AS a3";
                                            strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                                            strSQL+=" WHERE a2.co_emp=" + txtCodImp.getText() + "";//revisar cuando es modificacion - pueden haber cambiado la empresa que importa la mercaderia
                                            strSQL+=" AND a2.co_carPag=" + intCodCtaCarPag + "";
                                            //System.out.println("FALLO: " + strSQL);
                                            rst=stm.executeQuery(strSQL);
                                            if(rst.next()){
                                                strCodCtaCarPag=rst.getString("co_cta");
                                                strNumCtaCarPag=rst.getString("tx_codCta");
                                                strNomCtaCarPag=rst.getString("tx_desLar");
                                            }
                                                                                        
                                            //Cuenta 2
                                            if(bgdValCarInt.compareTo(BigDecimal.ZERO)>0){
                                                vecRegDia=new java.util.Vector();
                                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaCarPag);//intCodCtaTot
                                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaCarPag);
                                                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                                vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal(bgdValCarInt, objParSis.getDecimalesMostrar()));
                                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                                vecDatDia.add(vecRegDia);                                    
                                            }
                                        }
                                    }
                                }
                                
                                //total - haber
                                vecRegDia=new java.util.Vector();
                                vecRegDia.add(INT_VEC_DIA_LIN, "");
                                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPas);//intCodCtaTot
                                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPas);
                                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPas);
                                vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal((bgdSumValTotFobCfrDb.add(bgdValCarInt)), objParSis.getDecimalesMostrar()));
                                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                vecRegDia.add(INT_VEC_DIA_REF, "");
                                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                vecDatDia.add(vecRegDia);
                            }
                        }
                    }
                    //por proveedor y pedido - fin

                    System.out.println("vecDatDia: " + vecDatDia.toString());
                    objAsiDiaAjuPE.setDetalleDiario(vecDatDia);
                    objAsiDiaAjuPE.setGeneracionDiario((byte)2);

                    strAux="";
                    strAux+="" + txtDesCorTipDoc.getText();
                    strAux+=" # " + txtNumDoc.getText();
                    strAux+="; Importador: " + txtNomImp.getText();
                    strAux+="; Exportador: " + txtNomExp2.getText();
                    strAux+="; Pedido: " + txtNumPedOri.getText()+" ("+txtVerPed.getText()+")";
                    //strAux+="; Pedido: " + txtVerNumPed.getText()+"";         
                    objAsiDiaAjuPE.setGlosa(strAux);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    

    
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido previa(instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior a la Nota de Pedido(Instancia anterior: Nota de Pedido Previa)
     * <BR> false Caso contrario
     */
    private boolean getCodSegPedEmbImp_DB(){
        boolean blnRes=false;
        Object objCodSegNotPedPre=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPed, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp";
                strSQL+=" 	WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" 	AND a1.co_loc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" 	AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC) + "";
                strSQL+=" 	AND a1.co_doc=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC) + "";
                //System.out.println("getCodSegPedEmbImp_DB: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegNotPedPre=rst.getObject("co_segNotPed");
                    blnRes=true;
                }
                objCodSegInsAnt=objCodSegNotPedPre;
                //System.out.println("objCodSegInsAnt: " + objCodSegInsAnt);
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
           
    

    
    private boolean generaAsiDia_InsNueNP(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        BigDecimal bgdValTotFobCfr=new BigDecimal("0");
        BigDecimal bgdSumValTotFobCfr=new BigDecimal("0");
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodExp="";
        String strCodEmpTbl="", strCodLocTbl="", strCodTipDocTbl="", strCodDocTbl="", strCodExpTbl="";
        String strCodCtaAct="", strNumCtaAct="", strNomCtaAct="";
        String strCodCtaPas="", strNumCtaPas="", strNomCtaPas="";
        int intCodDocNotPed_nueAsiDia;
        try{
            blnGenAsiDiaNueNP=false;
            intCodDocNotPed_nueAsiDia=0;
            if(con!=null){
                for(int i=0; i<objImp33_03.getDatExpPedSelGenDia().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                    strCodEmp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                    strCodLoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                    strCodTipDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                    strCodDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                    strCodExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);

                    strCodEmpNotPed=strCodEmp;//co_emp NP
                    strCodLocNotPed=strCodLoc;//co_loc NP
                    strCodTipDocNotPed=strCodTipDoc;//co_tipDoc
                    strCodDocNotPed=strCodDoc;
                    
                    strCodEmpAsiDiaAjuNotPed=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_IMP);
                    strCodLocAsiDiaAjuNotPed=getCodLocPre(con, strCodEmpAsiDiaAjuNotPed);

                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="       SELECT MAX(a1.co_dia) AS co_dia";
                    strSQL+="       FROM tbm_cabDia AS a1";
                    strSQL+="       WHERE a1.co_emp=" + strCodEmpAsiDiaAjuNotPed + "";
                    strSQL+="       AND a1.co_loc=" + strCodLocAsiDiaAjuNotPed + "";
                    strSQL+="       AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB + "";
                    System.out.println("SQL-strCodDocNotPed_nueAsiDia: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intCodDocNotPed_nueAsiDia=rst.getInt("co_dia");
                    
                    strCodDocNotPed_nueAsiDia="" + (intCodDocNotPed_nueAsiDia+1);
                    System.out.println("strCodDocNotPed_nueAsiDia: " + strCodDocNotPed_nueAsiDia);

                    strSQL="";
                    strSQL+=" SELECT (a1.ne_ultDoc+1) AS ne_ultDoc FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + strCodEmpAsiDiaAjuNotPed + "";
                    strSQL+=" AND a1.co_loc=" + strCodLocAsiDiaAjuNotPed + "";
                    strSQL+=" AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_AJU_NOT_PED_PED_EMB + "";
                    System.out.println("SQL-strNumDocNotPed_nueAsiDia: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        strNumDocNotPed_nueAsiDia=rst.getString("ne_ultDoc");
                    
                    

                                        
                }
                
                
                
                ///para generar el asiento de diario
                objAsiDiaNueNP.inicializar();            
                vecDatDia.clear();
                //por proveedor y pedido - inicio
                
                for(int i=0; i<objImp33_03.getDatExpPedSelGenDia().size(); i++){//obtengo la información de la o las Notas de Pedido seleccionadas
                    strCodEmp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EMP);
                    strCodLoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_LOC);
                    strCodTipDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC);
                    strCodDoc=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_DOC);
                    strCodExp=objUti.getStringValueAt(objImp33_03.getDatExpPedSelGenDia(),i,objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_EXP);
                    
                    stm=con.createStatement();
                    //No se cambia el importador
//                    if(strCodImpPedEmb.equals(strCodImpNotPed)){
                        strSQL="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                        strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas";
                        strSQL+=" FROM tbm_cabNotPedImp AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                        strSQL+=" 	FROM tbm_plaCta AS a1";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                        strSQL+=" WHERE b1.co_emp=" + strCodEmp + "";
                        strSQL+=" AND b1.co_loc=" + strCodLoc + "";
                        strSQL+=" AND b1.co_tipDoc=" + strCodTipDoc + "";
                        strSQL+=" AND b1.co_doc=" + strCodDoc + "";
                        System.out.println("-cuentas contables: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strCodCtaAct=rst.getString("co_ctaAct");
                            strNumCtaAct=rst.getString("tx_codCtaAct");
                            strNomCtaAct=rst.getString("tx_nomCtaAct");
                            strCodCtaPas=rst.getString("co_ctaPas");
                            strNumCtaPas=rst.getString("tx_codCtaPas");
                            strNomCtaPas=rst.getString("tx_nomCtaPas");
                        }
//                    }                                      

                    for(int j=0; j<objTblModImp33_02.getRowCountTrue(); j++){//se lee el detalle del pedido embarcado que consta en la tabla del detalle
                        strCodEmpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EMP_REL)).toString());
                        strCodLocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_LOC_REL)).toString());
                        strCodTipDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)).toString());
                        strCodDocTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_DOC_REL)).toString());
                        strCodExpTbl=(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)==null?"":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_COD_EXP)).toString());

//                        if(strCodEmp.equals(strCodEmpTbl)){
//                            if(strCodLoc.equals(strCodLocTbl)){
//                                if(strCodTipDoc.equals(strCodTipDocTbl)){
//                                    if(strCodDoc.equals(strCodDocTbl)){
//                                        if(strCodExp.equals(strCodExpTbl)){
                                            objUti.setStringValueAt(objImp33_03.getDatExpPedSelGenDia(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_ACT, "");
                                            objUti.setStringValueAt(objImp33_03.getDatExpPedSelGenDia(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_COD_CTA_PAS, "");
                                            objUti.setStringValueAt(objImp33_03.getDatExpPedSelGenDia(), i, objImp33_03.INT_ARL_EXP_PED_IMP33_03_TOT_DIA, "");

                                            bgdValTotFobCfr=new BigDecimal(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(j, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                                            bgdSumValTotFobCfr=bgdSumValTotFobCfr.add(bgdValTotFobCfr);
//                                        }
//                                    }
//                                }
//                            }
//                        }
                    }

                    if( (bgdSumValTotFobCfrDb.subtract(bgdSumValTotFobCfr)).compareTo(BigDecimal.ZERO)>0  ){
                        
                        blnGenAsiDiaNueNP=true;
                        
                        
                        if(chkCreCta.isSelected()){                            
                            vecRegDia=new java.util.Vector();
                            vecRegDia.add(INT_VEC_DIA_LIN, "");
                            vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaAct);//intCodCtaTot
                            vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaAct);
                            vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                            vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaAct);
                            vecRegDia.add(INT_VEC_DIA_DEB, "" + objUti.redondearBigDecimal((bgdSumValTotFobCfrDb.subtract(bgdSumValTotFobCfr)), objParSis.getDecimalesMostrar()));//
                            vecRegDia.add(INT_VEC_DIA_HAB, "0");
                            vecRegDia.add(INT_VEC_DIA_REF, "");
                            vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                            vecDatDia.add(vecRegDia);

                            //total - haber
                            vecRegDia=new java.util.Vector();
                            vecRegDia.add(INT_VEC_DIA_LIN, "");
                            vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPas);//intCodCtaTot
                            vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPas);
                            vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                            vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPas);
                            vecRegDia.add(INT_VEC_DIA_DEB, "0");
                            vecRegDia.add(INT_VEC_DIA_HAB, "" + objUti.redondearBigDecimal((bgdSumValTotFobCfrDb.subtract(bgdSumValTotFobCfr)), objParSis.getDecimalesMostrar()));//
                            vecRegDia.add(INT_VEC_DIA_REF, "");
                            vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                            vecDatDia.add(vecRegDia);
                        }
                    }
                    bgdSumValTotFobCfr=BigDecimal.ZERO;


                }
                //por proveedor y pedido - fin

                System.out.println("vecDatDia-generaAsiDia_InsNueNP: " + vecDatDia.toString());
                objAsiDiaNueNP.setDetalleDiario(vecDatDia);
                objAsiDiaNueNP.setGeneracionDiario((byte)2);

                strAux="";
                strAux+="" + txtDesCorTipDoc.getText();
                strAux+=" # " + txtNumDoc.getText();
                strAux+="; Importador: " + txtNomImp.getText();
                strAux+="; Exportador: " + txtNomExp2.getText();
                strAux+="; Pedido: " + txtNumPedOri.getText()+" ("+txtVerPed.getText()+")";
                //strAux+="; Pedido: " + txtVerNumPed.getText()+"";         
                objAsiDiaNueNP.setGlosa(strAux);
//                }
                
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    private boolean cargarClaPriNPSel(){
        boolean blnRes=true;
        int intCodEmpNotPedSel=-1, intCodLocNotPedSel=-1, intCodTipDocNotPedSel=-1, intCodDocNotPedSel=-1;
        try{
            bgdSumValTotFobCfrDb=BigDecimal.ZERO;
            
            //esto esta mal, debe ser el de la NP del valor de la DB
            for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                if(objTblModImp33_02.getRowCountTrue()>0){
                    intCodEmpNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_EMP_REL).toString());
                    intCodLocNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_LOC_REL).toString());
                    intCodTipDocNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_TIP_DOC_REL).toString());
                    intCodDocNotPedSel=Integer.parseInt(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL)==null?"":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_COD_DOC_REL).toString());
                }
                i=objTblModImp33_02.getRowCountTrue(); 
            }
            
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a2.nd_valTotFobCfr) AS nd_valTotFobCfr";
                strSQL+=" FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpNotPedSel + "";
                strSQL+=" AND a1.co_loc=" + intCodLocNotPedSel + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocNotPedSel + "";
                strSQL+=" AND a1.co_doc=" + intCodDocNotPedSel + "";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    bgdSumValTotFobCfrDb=rst.getBigDecimal("nd_valTotFobCfr");
                }
                System.out.println("butConNotPedLisActionPerformed-bgdSumValTotFobCfrDb: " + bgdSumValTotFobCfrDb);

                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    


    /**
     * Esta función permite modificar la relación de los pagos con los pedidos.
     * <BR> Cada vez que se realice una modificación del pedido, se generará un nuevo documento
     * Este nuevo pedido, quedará enlazado a todos los pagos anteriores.
     * @return true: Si se pudo modificar la relación de los pagos con los pedidos.
     * <BR>false: En el caso contrario.
     */
    private boolean modifica_tbrDetConIntCarPagInsImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbr_DetConIntCarPagInsImp";
                strSQL+=" SET co_empRelPedEmb=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="   , co_locRelPedEmb=" + objParSis.getCodigoLocal() + "";
                strSQL+="   , co_tipDocRelPedEmb=" + txtCodTipDoc.getText() + "";
                strSQL+="   , co_docRelPedEmb=" + txtCodDoc.getText() + "";
                strSQL+=" WHERE co_empRelPedEmb=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_locRelPedEmb=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDocRelPedEmb=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_TIP_DOC); 
                strSQL+=" AND co_docRelPedEmb=" + objUti.getIntValueAt(arlDatConPedEmb, intIndicePedEmb, INT_ARL_CON_COD_DOC); 
                strSQL+=";";
                System.out.println("modifica_tbrDetConIntCarPagInsImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    
    private boolean actualizaTotFobCfr_Ara(){
        boolean blnRes=true;
        BigDecimal bdeTotFobCfr=BigDecimal.ZERO;
        BigDecimal bdeTotCfrAra=BigDecimal.ZERO;
        try{
            if(con!=null){
                for(int i=0; i<objTblModImp33_02.getRowCountTrue(); i++){
                    bdeTotFobCfr=bdeTotFobCfr.add(new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_TOT_FOB_CFR).toString())));
                    bdeTotCfrAra=bdeTotCfrAra.add(new BigDecimal(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR_ARA)==null?"0":(objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR_ARA).toString().equals("")?"0":objTblModImp33_02.getValueAt(i, objImp33_02.INT_TBL_DAT_VAL_CFR_ARA).toString())));
                }
                //redondeando los valores  
                bdeTotFobCfr=objUti.redondearBigDecimal(bdeTotFobCfr, objParSis.getDecimalesMostrar());
                bdeTotCfrAra=objUti.redondearBigDecimal(bdeTotCfrAra, objParSis.getDecimalesMostrar());

                //Armar la sentencia SQL.
                stm=con.createStatement();
                strSQL="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET nd_totFobCfr=" + bdeTotFobCfr + "";
                strSQL+=" , nd_totCfrAra=" + bdeTotCfrAra + "";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   
    /**
     * Función que permite conocer si los cargos a  pagar están configurados como consolidados
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean isCargosPagarConsolidados(){
        boolean blnRes=false;
        String strEstCarPagCon="";
        try{
            for(int i=0; i<objTblModImp33_01.getRowCountTrue(); i++){//obtengo la información la tabla de cargos a pagar - INTERES DE LA FACTURA -
                strEstCarPagCon=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_EST_CAR_PAG_CON)==null?"":objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_EST_CAR_PAG_CON).toString();
                if(strEstCarPagCon.equals("S")){
                    blnRes=true;
                    break;
                }
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
/**
 * Esta función permite cargar el detalle del registro seleccionado.
 * @param arlDatCarPagCon Contiene el arreglo de los cargos a pagar desde el consolidado
 * @return true: Si se pudo cargar el detalle del registro.
 * <BR>false: En el caso contrario.
 */
    public boolean setCargosPagarPedidoEmbarcado(int intCodCarPagConArl, Object objValCarPagArl){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        
        Object objValFleSav=null;
        Object objValAraSav=null;
        Object objValGasSav=null;
        
        int intCodCarPagConTbl=-1;
        String strTipCarPag="";

        try{
            objTooBar.setEstado('m');
            for(int j=0; j<objTblModImp33_01.getRowCountTrue(); j++){
                intCodCarPagConTbl=Integer.parseInt(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"-1":(objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"-1":objTblModImp33_01.getValueAt(j, objImp33_01.INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));

                if(intCodCarPagConArl==intCodCarPagConTbl){
                    objTblModImp33_01.setValueAt(objValCarPagArl, j, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    break;
                }       
            }                    
          
            
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
/**
 * Esta función permite cargar el detalle del registro seleccionado.
 * @param arlDatCarPagCon Contiene el arreglo de los cargos a pagar desde el consolidado
 * @return true: Si se pudo cargar el detalle del registro.
 * <BR>false: En el caso contrario.
 */
    public boolean setTotalCargosPagarPedidoEmbarcado(){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        
        Object objValFleSav=null;
        Object objValAraSav=null;
        Object objValGasSav=null;
        
        String strTipCarPag="";

        try{
            for(int i=0; i<objTblModImp33_01.getRowCountTrue(); i++){
                strTipCarPag=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
                

                if(strTipCarPag.equals("F")){
                    //objValFleSav=rst.getString("nd_valcarpag").equals("")?null:rst.getObject("nd_valcarpag");
                    objValFleSav=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    System.out.println("objValFleSav: " + objValFleSav);
                    if(objValFleSav==null){
                    }
                    else{
                        bgdValFleSav=bgdValFleSav.add((new BigDecimal(objValFleSav.toString())));
                    }
                }
                else if(strTipCarPag.equals("A")){
                    objValAraSav=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    System.out.println("objValAraSav: " + objValAraSav);
                    if(objValAraSav==null){
                    }
                    else{
                        bgdValAraSav=bgdValAraSav.add((new BigDecimal(objValAraSav.toString())));
                    }
                }
                else if(strTipCarPag.equals("G")){
                    objValGasSav=objTblModImp33_01.getValueAt(i, objImp33_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    System.out.println("objValGasSav: " + objValGasSav);
                    if(objValGasSav==null){
                    }
                    else{
                        bgdValGasSav=bgdValGasSav.add((new BigDecimal(objValGasSav.toString())));
                    }
                }
            }

            objImp33_01.setFleteItem(bgdValFleSav);
            objImp33_01.setDerechosArancelarios(bgdValAraSav);
            objImp33_01.setTotalGastos(bgdValGasSav);

            objImp33_01.calculaTotalFletes();
            objImp33_01.calculaTotalAranceles();
            objImp33_01.calculaTotalGastos();
            objImp33_01.calculaTotalCargosProvision();
            System.out.println("setTotalCargosPagarPedidoEmbarcado-final ");
            
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    public void setNumPedConsolidado(String numeroPedidoConsolidado){
        txtNumCon.setText(numeroPedidoConsolidado);
    }
    
    


    public boolean actualizaPedidoEmbarcado(Connection conexion, boolean blnCiePedEmb_Con, boolean blnPedFac_Emb_Con){
        boolean blnRes=false;
        try{
            conExt=conexion;
            if(conExt!=null){
                objTooBar.setEstado('m');
                objTooBar.clickModificar();
                if(objTooBar.beforeModificar()){
//                    chkCerPedEmb.setSelected(blnCiePedEmb_Con);
//                    chkEstPedEmb.setSelected(blnPedFac_Emb_Con);


                    blnCiePedRef=blnCiePedEmb_Con;
                    blnCnvPedFacEmb=blnPedFac_Emb_Con;
            

                    if(objTooBar.modificar()){
//                        if(objTooBar.afterModificar()){
                            blnRes=true;
//                        }
//                        else{
//                            blnRes=false;
//                        }
                    }
                    else{
                        blnRes=false;
                    }
                }
                else{
                    blnRes=false;
                }
                
            }
            
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    public void setValorFleteTotal(BigDecimal bgdFleItmCarPagCon){
        objImp33_01.setFleteItem(bgdFleItmCarPagCon);
        //objImp33_01.setValorFleteTotal(bgdFleItmCarPagCon);
    }
    
    
    public void calculaTotalAranceles(){
        objImp33_01.calculaTotalAranceles();
    }
    
    
    public void calculaTotalGastos(){
        objImp33_01.calculaTotalGastos();
    }
    
    
    public void cambiaEstadoChkVersiones(Boolean estado){
        chkMosTodVerPed.setSelected(estado);
    }
    
}