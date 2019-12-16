/*
 * ZafImp34.java
 *
 */
package Importaciones.ZafImp34;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafImp.ZafPagImp;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
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
import Librerias.ZafStkInv.ZafStkInv;
import java.util.HashMap;
/**
 *
 * @author  Gigi
 */
public class ZafImp34 extends javax.swing.JInternalFrame
{   
    //ArrayList para consultar
    private ArrayList arlDatConIngImp, arlRegConIngImp;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USRING=4; 
    private static final int INT_ARL_CON_TXT_USRMOD=5; 
    private int intIndiceIngImp=0;
	
	 
    /**
    * Indice que permite obtener el nombre del campo que se desea actualizar
    * @param indiceNombreCampo 
    *          <HTML>
    *              <BR>  0: Actualiza en campo "nd_stkAct"
    *              <BR>  1: Actualiza en campo "nd_canPerIng"
    *              <BR>  2: Actualiza en campo "nd_canPerEgr"
    *              <BR>  3: Actualiza en campo "nd_canBodIng"
    *              <BR>  4: Actualiza en campo "nd_canBodEgr"
    *              <BR>  5: Actualiza en campo "nd_canDesIng"
    *              <BR>  6: Actualiza en campo "nd_canDesEgr"
    *              <BR>  7: Actualiza en campo "nd_canTra"
    *              <BR>  8: Actualiza en campo "nd_canRev"
    *              <BR>  9: Actualiza en campo "nd_canRes"
    *              <BR> 10: Actualiza en campo "nd_canDis"
    *          </HTML>
    * @return true: Si se pudo obtener el nombre del campo
    * <BR> false: Caso contrario
    */
    final int INT_ARL_STK_INV_STK_ACT=0;      // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
    final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng --> transferencia afectar ingreso 
    final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr --> transferencia afectar egreso
    final int INT_ARL_STK_INV_CAN_DES_ENT_BOD=5;
    final int INT_ARL_STK_INV_CAN_DES_ENT_CLI=6;
    final int INT_ARL_STK_INV_CAN_TRA=7;
    final int INT_ARL_STK_INV_CAN_REV=8;
    final int INT_ARL_STK_INV_CAN_RES=9;
    final int INT_ARL_STK_INV_CAN_DIS=10;     // nd_canDis
    final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 
    
    /* NUEVO CONTENEDOR PARA ITEMS ZafStkInv MovimientoStock */
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;    
    private static final int INT_ARL_STK_INV_COD_ITM_GRP = 0;
    private static final int INT_ARL_STK_INV_COD_ITM_EMP = 1;
    private static final int INT_ARL_STK_INV_COD_ITM_MAE = 2;
    private static final int INT_ARL_STK_INV_COD_LET_ITM = 3;
    private static final int INT_ARL_STK_INV_CAN_ITM = 4;
    private static final int INT_ARL_STK_INV_COD_BOD_EMP = 5;
    
    //Constantes
    private ArrayList arlRegMel, arlDatMel;
    private int INT_ARL_MEL_DIR=0;  
    
    private ArrayList arlDatFilEliImp34_03;   //ArrayList: para las filas que fueron eliminadas en el detalle
    private ArrayList arlRegCodItmMaeConInv, arlDatCodItmMaeConInv;
    private int INT_ARL_COD_ITM_MAE_CON_INV=0;
    private int INT_COD_CAR_PAG_OTR_GAS=14;
    private int INT_COD_TIP_DOC_ORD_CON=117;
    private int INT_COD_TIP_DOC_ORD_DIS_INM=203;
    private int INT_COD_BOD_INM_GRP=15;
    private static final int INT_ARL_COD_CFG_IVA_COM=1;  //Código de Configuracion de Impuestos Iva Compras.

    private int intCodDocOrdConImp, intCodRegOrdConImp;
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDatePicker dtpFecDoc;
    private ZafDocLis objDocLis;
    private Date datFecAux;
    private Date datFecDoc;                                     //Fecha del documento.
	
    private ZafVenCon vcoTipDoc, vcoImp, vcoPrv, vcoExp, vcoPedEmb, vcoBod;    

    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;
    private ZafStkInv objStkInv;
    private ZafTblMod objTblModImp34_01, objTblModImp34_02;
    private Librerias.ZafUtil.UltDocPrint  objUltDocPrint2;
    private ZafImp objImp;
    private ZafPagImp objPagImp;
    private ZafNotCorEle objNotCorEle;
	
    private ZafAsiDia objAsiDia;    
    private ZafSegMovInv objSegMovInv;
    private ZafImp34_01 objImp34_01;
    private ZafImp34_02 objImp34_02;    
	
    private Object objCodSegInsAnt;    
    private Vector vecDat, vecReg;
     
    private String strSQL, strAux;
    private String strDesCorTipDoc, strDesLarTipDoc, strTipDocNecAutAnu;   
    private String strTipNotPed;
    private String strCodImp, strNomImp;
    private String strCodExp, strNomExp;
    private String strCodPag, strNomPag;
    private String strNumDoc, strMonDoc;
    private String strCodCtaActPedEmb, strNumCtaActPedEmb, strNomCtaActPedEmb;
    private String strCodCtaActIva, strNumCtaActIva, strNomCtaActIva;
    private String strCodCtaPasPedEmb, strNumCtaPasPedEmb, strNomCtaPasPedEmb;
    private String strEstCiePagImp;
    private String strCodEmpPedEmb, strCodLocPedEmb, strCodTipDocPedEmb, strCodDocPedEmb;
    private String strCodLocPre; 
    private String strCodBod, strNomBod;
    
    private boolean blnHayCam;
    private boolean blnConVenEme=false;
    
    //Rangos para calcular la cantidad permitida en Pzas cuando es pedido en Toneladas.
    private BigDecimal bgdMin= new BigDecimal ("0.95");
    private BigDecimal bgdMax= new BigDecimal ("1.05");        
    
    private int intSig=1;
    private int intTipNotPed;
    private int intSecGrp, intSecEmp;
    private int intNumIngImpDia;    
    
    private int intCodCtaBodEmpAsiDia;
    private String strNumCtaBodEmpAsiDia, strNomCtaBodEmpAsiDia;
    
    private int intCodDocOrdDis;
    private int intNumDocOrdDis;

    private BigDecimal bgdPorIva=new BigDecimal("0");
    
    private String strVer=" v0.8.4";
    
    /** Creates new form ZafImp34 */
    public ZafImp34(ZafParSis obj) 
    {
        try{
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            configurarFrm();
            agregarDocLis();
            arlDatCodItmMaeConInv=new ArrayList();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            arlDatMel=new ArrayList();                

            objSegMovInv=new ZafSegMovInv(objParSis, this);
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
       
    /**
     * Constructor es usado en los siguientes fuentes:
     * ZafImp03, ZafImp04, ZafImp05, ZafImp11, ZafImp25, ZafImp26
     * @param obj
     * @param codigoEmpresa
     * @param codigoLocal
     * @param codigoTipoDocumento
     * @param codigoDocumento 
     */
    public ZafImp34(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento) {
        try{
            objParSis=(ZafParSis)obj.clone();
            objParSis.setCodigoEmpresa(codigoEmpresa);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(2889);
            blnConVenEme=true;
            initComponents();
            configurarFrm();
            agregarDocLis();
            arlDatMel=new ArrayList();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            lblTit.setText("Ingreso por Importación");

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);
            objSegMovInv=new ZafSegMovInv(objParSis, this);

        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Constructor para Botón Consultar en el reporte de Seguimiento de Solicitud de Transferencia de Inventario(Cabecera) */
    public ZafImp34(HashMap map) 
    {
        String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";
        try{
            ZafParSis obj  = (ZafParSis)map.get("objParSis");
            
            strCodEmp  = (String)map.get("strCodEmp");
            strCodLoc  = (String)map.get("strCodLoc");
            strCodTipDoc  = (String)map.get("strCodTipDoc");
            strCodDoc     = (String)map.get("strCodDoc");
            
            objParSis=(ZafParSis)obj.clone();
            objParSis.setCodigoMenu(2889);
            blnConVenEme=true;
            
            initComponents();
            configurarFrm();
            agregarDocLis();
            arlDatMel=new ArrayList();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            lblTit.setText("Ingreso por Importación");

            txtCodTipDoc.setText(""+strCodTipDoc);
            txtCodDoc.setText(""+strCodDoc);
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.afterConsultar();
            objTooBar.setEstado('w');
            objTooBar.setVisible(false);
            objSegMovInv=new ZafSegMovInv(objParSis, this);

        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
    }     

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objPagImp = new ZafPagImp(objParSis, this);  //Pagos de Impuestos
            
            objTooBar=new MiToolBar(this);
            
            //Se deshabilito las opciones de Modificar, Eliminar para el proyecto de costos de importaciones.
            /* NOTA IMPORTANTE: Al habilitar estas opciones se incrementará el disponible.*/
            objTooBar.setVisibleModificar(false); 
            objTooBar.setVisibleEliminar(false); 
            objTooBar.setVisibleAnular(false); 
            //Se habilita la opción de anular sólo para Admin, Alfredo autorizó se anule un pedido por tipo de cambio.
            //Solo se anularán los pedidos que no hayan sido arribados.
            if(objParSis.getCodigoUsuario()==1) {
                objTooBar.setVisibleAnular(true); 
            }
            
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())  {
                objTooBar.setVisibleInsertar(false);
            }
            
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);

            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butConPedEmbLis);
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);

            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(584, 4, 100, 20);
            
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
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumPed.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtPesTotKgr.setBackground(objParSis.getColorCamposSistema());
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);

            configurarVenConTipDoc();
            configurarVenConImp();
            configurarVenConPrv();
            configurarVenConExp();           
            configurarVenConBod();      
            configurarVenConPedEmb();


            //tabla de cargos a pagar
            objImp34_01=new ZafImp34_01(objParSis);
            panCarPagImp.add(objImp34_01,java.awt.BorderLayout.CENTER);


            //tabla de items y sus valores
            objImp34_02=new ZafImp34_02(objParSis, objImp34_01);
            panDetPagImp.add(objImp34_02,java.awt.BorderLayout.CENTER);
            objImp34_01.setObjectoImp34_02(objImp34_02);

            
            dtpFecDoc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    //txtDesCorTipDocFocusLost(evt);
                    objImp34_02.setFechaDocumento(dtpFecDoc.getText());
                }
            });
            
            
            txtCodTipDoc.addFocusListener(new java.awt.event.FocusAdapter(){
                public void focusLost(java.awt.event.FocusEvent evt){
                    objImp34_02.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });


            vecDat=new Vector();    //Almacena los datos



            objImp34_02.addImp34_02Listener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                 int intCodCtaBodEmp=-1;
                public void beforeInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("beforeInsertar");
                }
                public void afterInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("afterInsertar");
                }
                public void beforeEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("beforeEditarCelda");
                }
                public void afterEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("afterEditarCelda");
                    txtValDoc.setText("" + objImp34_02.getValorTotalCosto());
                    txtValDoc.setText( "" + (objUti.redondearBigDecimal(txtValDoc.getText(), objParSis.getDecimalesMostrar()))  );
                    txtPesTotKgr.setText("" + objImp34_02.getCalcularPesoTotal()); 
                    generaAsiDiaLoc();
                    //objAsiDia.generarDiario(Integer.parseInt(txtCodImp.getText()), intCodCtaBodEmp, Double.parseDouble(txtValDoc.getText()), Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodCtaActPedEmb), Double.parseDouble(txtValDoc.getText()));
                }
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("beforeConsultarCuentas");
                }
                public void afterConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("afterConsultarCuentas");
                }
            });

            txtNumCorEle.setVisible(false);
            butNumCorEle.setVisible(false);
            if(objParSis.getCodigoUsuario()==1){
                txtNumCorEle.setVisible(true);
                butNumCorEle.setVisible(true);
            }            

            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            butConPedEmbLis.setEnabled(true);

        }
        catch(Exception e){
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
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtPesTotKgr.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);

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
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
                        
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                        if(txtCodTipDoc.getText().equals("14"))
                            butExp.setEnabled(false);
                        else
                            butExp.setEnabled(true);
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
                            
                            txtCodBod.setText("");
                            txtNomBod.setText("");

                            if(txtCodTipDoc.getText().equals("14"))
                                butExp.setEnabled(false);
                            else
                                butExp.setEnabled(true);
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
                            
                            txtCodBod.setText("");
                            txtNomBod.setText("");

                            if(txtCodTipDoc.getText().equals("14"))
                                butExp.setEnabled(false);
                            else
                                butExp.setEnabled(true);
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText()))
                    {
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
     * mostrar las "Importadores".
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
            strSQL+=" WHERE a1.st_reg NOT IN('I','E') AND a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='P'";
            strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
            strSQL+=" ORDER BY a1.co_emp";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
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
                        strCodLocPre=vcoImp.getValueAt(5);
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoImp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                        strCodLocPre=vcoImp.getValueAt(5);
                    }
                    else{
                        vcoImp.setCampoBusqueda(0);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                            strCodLocPre=vcoImp.getValueAt(5);
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
                        strCodLocPre=vcoImp.getValueAt(5);
                    }
                    else{
                        vcoImp.setCampoBusqueda(2);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                            strCodLocPre=vcoImp.getValueAt(5);
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
            strSQL="";
            strSQL+="SELECT a1.co_prv, a1.tx_nom";
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
            strSQL="";
//            strSQL+="SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
//            strSQL+=" FROM tbm_expImp AS a1";
//            strSQL+=" WHERE a1.st_reg='A'";
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
        vcoExp.limpiar();
        try{
            if(txtCodImp.getText().length()>0){
                if(txtCodTipDoc.getText().equals("14")){
                    strSQLTmp+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
                    strSQLTmp+=" FROM tbm_expImp AS a1";
                    strSQLTmp+=" WHERE a1.st_reg='A'";
                }
                else{
                    strSQLTmp+=" SELECT a1.co_cli AS co_exp, a1.tx_ide AS tx_nom2, a1.tx_nom AS tx_nom";
                    strSQLTmp+=" FROM tbm_cli AS a1";
                    strSQLTmp+=" WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQLTmp+=" AND a1.st_reg='A' AND a1.st_prv='S'";
                }                
                strSQLTmp+=" ORDER BY a1.tx_nom";                
                
                vcoExp.setCondicionesSQL(strSQLTmp);
                
                switch (intTipBus){
                    case 0: //Mostrar la ventana de consulta.
                        vcoExp.setCampoBusqueda(1);
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp2.setText(vcoExp.getValueAt(2));
                            txtNomExp.setText(vcoExp.getValueAt(3));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp2.setText(vcoExp.getValueAt(2));
                            txtNomExp.setText(vcoExp.getValueAt(3));
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
                                txtNomExp2.setText(vcoExp.getValueAt(2));
                                txtNomExp.setText(vcoExp.getValueAt(3));
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
                            txtNomExp2.setText(vcoExp.getValueAt(2));
                            txtNomExp.setText(vcoExp.getValueAt(3));
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
                                txtNomExp2.setText(vcoExp.getValueAt(2));
                                txtNomExp.setText(vcoExp.getValueAt(3));
                            }
                            else
                            {
                                txtNomExp2.setText(strNomExp);
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
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBod() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega");
            arlAli.add("Nombre de Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            if(objParSis.getCodigoUsuario()==1 ){
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a1.st_reg='A'";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
            }
            else{
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1";
                strSQL += " INNER JOIN tbr_bodTipDocPrgUsr AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod ";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a1.st_reg='A'";
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL += " AND a2.tx_natBod IN('I','A')";
                strSQL += " AND a2.st_reg IN('A','P')";

            }

            vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConBod(int intTipBus) {
        boolean blnRes = true;
        try {
            vcoBod.limpiar();
            if(objParSis.getCodigoUsuario()!=1){
                String strMe="";
                strMe += " AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strMe += " ORDER BY a1.co_bod, a1.tx_nom";
                vcoBod.setCondicionesSQL(strMe);
            }
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoBod.setCampoBusqueda(2);
                    vcoBod.show();
                    if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".

                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtNomBod.setText(strNomBod);
                        }
                    }
                    break;


                case 3: //Búsqueda directa por "Código".
                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } 
                    break;
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos embarcados".
     */
    private boolean configurarVenConPedEmb()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a2.tx_desCor");
            arlCam.add("a2.tx_desLar");
            arlCam.add("a1.ne_numdoc");
            arlCam.add("a1.fe_doc");
            arlCam.add("a1.fe_emb");
            arlCam.add("co_imp");
            arlCam.add("tx_nomImp");
            arlCam.add("a5.co_exp");
            arlCam.add("tx_nomExp");
            arlCam.add("a1.nd_valdoc");
            arlCam.add("a1.tx_numdoc2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Núm.Doc.");
            arlAli.add("Fec.Doc.");
            arlAli.add("Fec.Emb.");
            arlAli.add("Cód.Imp.");
            arlAli.add("Importador");
            arlAli.add("Cód.Exp.");
            arlAli.add("Exportador");
            arlAli.add("Valor");
            arlAli.add("Núm.Ped.");
            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("30");
            arlAncCol.add("30");
            arlAncCol.add("30");
            arlAncCol.add("60");
            arlAncCol.add("10");
            arlAncCol.add("60");
            arlAncCol.add("72");
            arlAncCol.add("72");
            arlAncCol.add("30");
            arlAncCol.add("76");
            arlAncCol.add("30");
            arlAncCol.add("110");
            arlAncCol.add("68");
            arlAncCol.add("70");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
            strSQL+=" 	   , a2.tx_desCor, a2.tx_desLar, a1.ne_numdoc, a1.fe_doc, a1.fe_emb";
            strSQL+=" 	   , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
            strSQL+=" 	   , a5.co_exp, a5.tx_nom AS tx_nomExp, a1.nd_valdoc, a1.tx_numdoc2";
            strSQL+=" FROM (tbm_cabPedEmbImp AS a1";            
            strSQL+="       LEFT OUTER JOIN tbm_cabMovInv AS b2";
            strSQL+="       ON a1.co_emp=b2.co_emprelpedembimp AND a1.co_loc=b2.co_locrelpedembimp";
            strSQL+="       AND a1.co_tipDoc=b2.co_tipdocrelpedembimp AND a1.co_doc=b2.co_docrelpedembimp AND b2.st_reg NOT IN('E','I')";
            strSQL+=" )";  
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
            strSQL+=" INNER JOIN tbm_detPedEmbImp AS a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc)";
            strSQL+=" INNER JOIN tbm_inv AS a7 ON (a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm)";               
            strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
            strSQL+=" INNER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
            strSQL+=" WHERE a1.st_reg NOT IN('E','I') ";
            strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
            strSQL+=" AND b2.co_emp IS NULL"; /* Muestra los que NO tienen Ingreso por Importación */
            strSQL+=" AND (a7.tx_codAlt LIKE '%I' AND a1.co_exp NOT IN (32) )  ";  /* Sólo Importaciones */
            strSQL+=" AND (CASE WHEN a1.st_ciePagImp IS NULL THEN 'P' ELSE a1.st_ciePagImp END) NOT IN ('N')";  //Muestra sólo los pedidos que NO esten abiertos para ingresar pago de aranceles.
            strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
            strSQL+=" 	     , a2.tx_desCor, a2.tx_desLar, a1.ne_numdoc, a1.fe_doc, a1.fe_emb";
            strSQL+=" 	     , a3.co_emp, a3.tx_nom, a5.co_exp, a5.tx_nom, a1.nd_valdoc, a1.tx_numdoc2";
            //Ocultar columnas.
            int intColOcu[]=new int[7];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            intColOcu[4]=10;
            intColOcu[5]=12;
            intColOcu[6]=14;
            vcoPedEmb=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de pedidos embarcados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPedEmb.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPedEmb.setConfiguracionColumna(14, javax.swing.JLabel.RIGHT, vcoPedEmb.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
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
    private boolean mostrarVenConPedEmb(int intTipBus)
    {
        boolean blnRes=true;
        strCodEmpPedEmb=""; strCodLocPedEmb=""; strCodTipDocPedEmb=""; strCodDocPedEmb="";
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPedEmb.setCampoBusqueda(6);
                    vcoPedEmb.show();
                    if (vcoPedEmb.getSelectedButton()==vcoPedEmb.INT_BUT_ACE){
                        strCodEmpPedEmb=(vcoPedEmb.getValueAt(1));
                        strCodLocPedEmb=(vcoPedEmb.getValueAt(2));
                        strCodTipDocPedEmb=(vcoPedEmb.getValueAt(3));
                        strCodDocPedEmb=(vcoPedEmb.getValueAt(4));
                        cargarRegPedEmb(strCodEmpPedEmb, strCodLocPedEmb, strCodTipDocPedEmb, strCodDocPedEmb);
                        mostrarVenConImp(1);                        
                        mostrarNumDocEmpTipDoc();
                        objImp34_01.colocaTotalGastosAdicionales(new BigDecimal(BigInteger.ZERO), INT_COD_CAR_PAG_OTR_GAS);
                        txtNumPed.setText(vcoPedEmb.getValueAt(15));
                        
                        generaAsiDiaLoc();

                        //objAsiDia.generarDiario(Integer.parseInt(txtCodImp.getText()), intCodCtaBodEmp, Double.parseDouble(txtValDoc.getText()), Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodCtaActPedEmb), Double.parseDouble(txtValDoc.getText()));
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
        

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        txtNomExp2 = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblNumPed = new javax.swing.JLabel();
        txtNumPed = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        optTmFob = new javax.swing.JRadioButton();
        optTmCfr = new javax.swing.JRadioButton();
        optPzaFob = new javax.swing.JRadioButton();
        optPzaCfr = new javax.swing.JRadioButton();
        lblPesTotKgr = new javax.swing.JLabel();
        txtPesTotKgr = new javax.swing.JTextField();
        butConPedEmbLis = new javax.swing.JButton();
        txtNumCorEle = new javax.swing.JTextField();
        butNumCorEle = new javax.swing.JButton();
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
        panBar = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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

        tabFrm.setAutoscrolls(true);

        panGen.setAutoscrolls(true);
        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(0, 128));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 120, 20);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(98, 4, 32, 20);

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
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 4, 78, 20);

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
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(208, 4, 230, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(438, 4, 20, 20);

        lblImp.setText("Importador:");
        lblImp.setToolTipText("Cuenta");
        panCab.add(lblImp);
        lblImp.setBounds(4, 24, 90, 20);

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
        panCab.add(txtCodImp);
        txtCodImp.setBounds(130, 24, 78, 20);

        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        panCab.add(txtNomImp);
        txtNomImp.setBounds(208, 24, 230, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panCab.add(butImp);
        butImp.setBounds(438, 24, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Proveedor");
        panCab.add(lblExp);
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
        panCab.add(txtCodExp);
        txtCodExp.setBounds(130, 44, 30, 20);
        panCab.add(txtNomExp);
        txtNomExp.setBounds(160, 44, 78, 20);

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
        panCab.add(txtNomExp2);
        txtNomExp2.setBounds(238, 44, 200, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panCab.add(butExp);
        butExp.setBounds(438, 44, 20, 20);

        lblBod.setText("Bodega:");
        lblBod.setToolTipText("Proveedor");
        panCab.add(lblBod);
        lblBod.setBounds(4, 64, 70, 20);

        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        panCab.add(txtCodBod);
        txtCodBod.setBounds(130, 64, 78, 20);

        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        panCab.add(txtNomBod);
        txtNomBod.setBounds(208, 64, 230, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panCab.add(butBod);
        butBod.setBounds(438, 64, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(460, 4, 116, 20);

        lblNumDoc.setText("Número de documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCab.add(lblNumDoc);
        lblNumDoc.setBounds(460, 24, 124, 20);

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
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(584, 24, 100, 20);

        lblNumPed.setText("Número de Pedido:");
        lblNumPed.setToolTipText("Número alterno 1");
        panCab.add(lblNumPed);
        lblNumPed.setBounds(460, 44, 124, 20);

        txtNumPed.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumPed.setToolTipText("Número de egreso");
        panCab.add(txtNumPed);
        txtNumPed.setBounds(584, 44, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(460, 64, 130, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(584, 64, 100, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Valor del documento");
        panCab.add(lblValDoc);
        lblValDoc.setBounds(460, 84, 122, 20);

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
        panCab.add(txtValDoc);
        txtValDoc.setBounds(584, 84, 100, 20);

        optTmFob.setText("TM FOB");
        optTmFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmFobActionPerformed(evt);
            }
        });
        panCab.add(optTmFob);
        optTmFob.setBounds(0, 90, 90, 20);

        optTmCfr.setText("TM CFR");
        optTmCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmCfrActionPerformed(evt);
            }
        });
        panCab.add(optTmCfr);
        optTmCfr.setBounds(100, 90, 90, 20);

        optPzaFob.setText("PZA FOB");
        optPzaFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaFobActionPerformed(evt);
            }
        });
        panCab.add(optPzaFob);
        optPzaFob.setBounds(200, 90, 90, 20);

        optPzaCfr.setText("PZA CFR");
        optPzaCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaCfrActionPerformed(evt);
            }
        });
        panCab.add(optPzaCfr);
        optPzaCfr.setBounds(300, 90, 90, 20);

        lblPesTotKgr.setText("Peso(kg):");
        panCab.add(lblPesTotKgr);
        lblPesTotKgr.setBounds(460, 104, 100, 14);

        txtPesTotKgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtPesTotKgr);
        txtPesTotKgr.setBounds(584, 104, 100, 20);

        butConPedEmbLis.setText("Pedido Embarcado");
        butConPedEmbLis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConPedEmbLisActionPerformed(evt);
            }
        });
        panCab.add(butConPedEmbLis);
        butConPedEmbLis.setBounds(310, 110, 140, 20);
        panCab.add(txtNumCorEle);
        txtNumCorEle.setBounds(460, 115, 30, 20);

        butNumCorEle.setText("CorEle");
        butNumCorEle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butNumCorEleActionPerformed(evt);
            }
        });
        panCab.add(butNumCorEle);
        butNumCorEle.setBounds(510, 115, 70, 23);

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

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

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
    //Validar el contenido de la celda sálo si ha cambiado.
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
    //Validar el contenido de la celda sálo si ha cambiado.
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
    //Validar el contenido de la celda sálo si ha cambiado.
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
    //Validar el contenido de la celda sálo si ha cambiado.
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
    //Validar el contenido de la celda sálo si ha cambiado.
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
    //Validar el contenido de la celda sálo si ha cambiado.
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
    //Validar el contenido de la celda sálo si ha cambiado.
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
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomExp2.getText().equalsIgnoreCase(strNomExp)) {
        if (txtNomExp2.getText().equals("")) {
            txtCodExp.setText("");
        } else {
            mostrarVenConExp(2);
        }
    } else
        txtNomExp2.setText(strNomExp);
}//GEN-LAST:event_txtNomExp2FocusLost

private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
    mostrarVenConExp(0);
}//GEN-LAST:event_butExpActionPerformed

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
    objImp34_01.setTipoNotaPedido(intTipNotPed);
    objImp34_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp34_01.calculaTotalFletes();
    objImp34_02.regenerarCalculos();    
    
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
    objImp34_01.setTipoNotaPedido(intTipNotPed);
    objImp34_01.setValorFleteInactivo();
    objImp34_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp34_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objImp34_01.calculaTotalFletes();
    objImp34_02.regenerarCalculos();    
    
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
    objImp34_01.setTipoNotaPedido(intTipNotPed);
    objImp34_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp34_01.calculaTotalFletes();
    //objImp34_02.seteaColumnaPiezas();
    objImp34_02.regenerarCalculos();    
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
    objImp34_01.setTipoNotaPedido(intTipNotPed);
    objImp34_01.setValorFleteInactivo();
    objImp34_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    
    //NUEVO
    objImp34_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objImp34_01.calculaTotalFletes();
    //objImp34_02.seteaColumnaPiezas();
    objImp34_02.regenerarCalculos();    
}//GEN-LAST:event_optPzaCfrActionPerformed

private void butConPedEmbLisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConPedEmbLisActionPerformed
    if(txtCodBod.getText().length()>0){
        mostrarVenConPedEmb(0);
        objImp34_01.setTipoNotaPedido(intTipNotPed);
        objImp34_02.setTipoNotaPedido(intTipNotPed);
        if(!txtCodTipDoc.getText().equals("14")){
            txtCodExp.setText("");
            txtNomExp.setText("");
            txtNomExp2.setText("");
        }
    }
    else{
        mostrarMsgInf("<HTML>Debe seleccionar la bodega antes de seleccionar el pedido embarcado</HTML>");
    }
}//GEN-LAST:event_butConPedEmbLisActionPerformed

private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
    txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
        if (txtCodBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(1);
        }
    } else
        txtCodBod.setText(strCodBod);
}//GEN-LAST:event_txtCodBodFocusLost

private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
    txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
        if (txtNomBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(2);
        }
    }
    else
        txtNomBod.setText(strNomBod);
}//GEN-LAST:event_txtNomBodFocusLost

private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
    mostrarVenConBod(0);
}//GEN-LAST:event_butBodActionPerformed

    private void butNumCorEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNumCorEleActionPerformed
        enviarCorElePreVta();
    }//GEN-LAST:event_butNumCorEleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBod;
    private javax.swing.JButton butConPedEmbLis;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butNumCorEle;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblImp;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPesTotKgr;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JRadioButton optPzaCfr;
    private javax.swing.JRadioButton optPzaFob;
    private javax.swing.JRadioButton optTmCfr;
    private javax.swing.JRadioButton optTmFob;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCarPagImp;
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
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomExp2;
    private javax.swing.JTextField txtNomImp;
    private javax.swing.JTextField txtNumCorEle;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumPed;
    public javax.swing.JTextField txtPesTotKgr;
    public javax.swing.JTextField txtValDoc;
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
            txtCodBod.setText("");
            txtNomBod.setText("");            
            dtpFecDoc.setText("");
            txtNumDoc.setText("");
            txtNumPed.setText("");
            txtCodDoc.setText("");
            txtValDoc.setText("");
            txtPesTotKgr.setText("");

            optTmFob.setSelected(false);
            optTmCfr.setSelected(false);
            optPzaFob.setSelected(false);
            optPzaCfr.setSelected(false);

            optPzaCfr.setEnabled(false);
            optPzaFob.setEnabled(false);
            optTmCfr.setEnabled(false);
            optTmFob.setEnabled(false);

            txtCodDoc.setEditable(false);
            txtValDoc.setEditable(false);
            txtPesTotKgr.setEditable(false);

            txaObs1.setText("");
            txaObs2.setText("");

            objImp34_01.limpiarTablaImp34_01();
            objImp34_02.limpiarTablaImp34_02();
            objImp34_01.setBlnHayCamCamImp34_01(false);
            objImp34_02.setBlnHayCamCamImp34_02(false);

            objImp34_01.colocaTotalGastosAdicionales(new BigDecimal(BigInteger.ZERO), INT_COD_CAR_PAG_OTR_GAS);

            butExp.setEnabled(false);
        }
        catch (Exception e){
            blnRes=false;
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
    private boolean isCamVal(){
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
        if (txtNumPed.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> es obligatorio.<BR>Escriba un Número de pedido y vuelva a intentarlo.</HTML>");
            txtNumPed.requestFocus();
            return false;
        }
        
        if( isExisteNumeroPedido(txtNumPed.getText()) ) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> está repetido.<BR>Escriba un Número de pedido diferente y vuelva a intentarlo.</HTML>");
            txtNumPed.requestFocus();
            txtNumPed.selectAll();
            return false;
        }               

        if(objTooBar.getEstado()=='n'){
            if(!objTblModImp34_02.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(objTblModImp34_02.getRowCountTrue()<=0){
                objTblModImp34_02.setModoOperacion(objTblModImp34_02.INT_TBL_EDI);
                objTblModImp34_02.removeEmptyRows();
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
        
        //si es TM-FOB o TM-CFR deben tener datos cada fila de las piezas, en PZA-FOB o PZA-CFR no deben tener valores.
        BigDecimal bgdCanPza=new BigDecimal("0");
        if( (optTmFob.isSelected())  || (optTmCfr.isSelected())  ){
            for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                bgdCanPza=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString()));
                
                if(bgdCanPza.compareTo(new BigDecimal("0"))<0){
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El valor de la(s) <FONT COLOR=\"blue\">pieza(s)</FONT> es obligatorio.<BR>Escriba el(los) valor(es) y vuelva a intentarlo.</HTML>");
                    return false;                    
                }
            }
        }
        
        if(isExisteItemRepetido()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
                
        if(isExisteItemTerminalDiferente()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existe uno o varios items cuyo terminal es diferente a la naturaleza del tipo de documento seleccionado.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }

        if ((objUti.redondearBigDecimal(objImp34_02.getValorTotalArancel(), objParSis.getDecimalesMostrar())).compareTo((objUti.redondearBigDecimal(objImp34_01.getValorTotalArancel(), objParSis.getDecimalesMostrar())))!=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El valor de Total de Aranceles del Cargo a Pagar es diferente al valor de Total de Aranceles del detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        /* Valida que el valor de factura sea igual al valor de la Columna Tot.Fob/Cfr. */
        if ((objUti.redondearBigDecimal(objImp34_02.getValorTotalFactura(), objParSis.getDecimalesMostrar())).compareTo((objUti.redondearBigDecimal(objImp34_01.getValorTotalPagoExterior(), objParSis.getDecimalesMostrar())))!=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El valor de Total de Factura del Cargo a Pagar es diferente al Valor de Tot.FOB/CFR del detalle + Gastos Origen.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }             
        
        //Validar rando de piezas cuando es pedido por tonelada.
        if(!validarRangoPzas()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existen items fuera del rango "+bgdMin+" - "+bgdMax+" en la cantidad de piezas.<BR>Verifique y vuelva a intentarlo.</HTML>");
            objTblModImp34_02.fireTableDataChanged();
            return false;
        }       
        
        if(strEstCiePagImp.equals("S")) {
            /* Valida que EXISTA Pago de Arancel generado. El Ingreso por importación puede generarse cuando se han realizado todos los pagos de aranceles.*/
            if(!objPagImp.existePagoArancel(Integer.parseInt(strCodEmpPedEmb), Integer.parseInt(strCodLocPedEmb), Integer.parseInt(strCodTipDocPedEmb), Integer.parseInt(strCodDocPedEmb))){  
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido Embarcado no tiene <FONT COLOR=\"blue\">Pago de Aranceles </FONT>asociado.<BR>Verifique y vuelva a intentarlo.</HTML>");
                return false;
            }          
            
            /* Valida que no exista Transferencias Bancarias Asociadas al pago de impuesto. */
            if(!objPagImp.existeTransferenciaBancariaArancel(Integer.parseInt(strCodEmpPedEmb), Integer.parseInt(strCodLocPedEmb), Integer.parseInt(strCodTipDocPedEmb), Integer.parseInt(strCodDocPedEmb))){ 
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido Embarcado NO tiene <FONT COLOR=\"blue\">Transferencias Bancarias por Arancel </FONT> asociadas.<BR>Verifique y vuelva a intentarlo.</HTML>");
                return false;
            }                 
        }

        /* Valida que NO esté abierto el Pedido Embarcado para realizar Pago de Arancel. */
        if(!objPagImp.existeCierrePagoImpuestos(Integer.parseInt(strCodEmpPedEmb), Integer.parseInt(strCodLocPedEmb), Integer.parseInt(strCodTipDocPedEmb), Integer.parseInt(strCodDocPedEmb))){ 
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El Pedido Embarcado NO ha sido cerrado para Pago de Aranceles.<BR>Verifique y vuelva a intentarlo.</HTML>");
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
                for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                    BigDecimal bgdCanPza=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString()));
                    BigDecimal bgdCanTon=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString()));
                    BigDecimal bgdPesKgr=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PES)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PES).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PES).toString()));
                    //System.out.println("Fila#"+i+" <> bgdCanTon: "+bgdCanTon+" <> bgdCanPza: "+bgdCanPza+" <> bgdPesKgr:"+bgdPesKgr);
                    if(bgdPesKgr.compareTo(BigDecimal.ZERO)<=0){ /* Valida que siempre se asigne un peso al item, antes de ingresar */
                        mostrarMsgInf("<HTML>Existen items sin peso.<BR>Verifique, asigne peso y vuelva a intentarlo.</HTML>");
                        objTblModImp34_02.setValueAt("S", i, objImp34_02.INT_TBL_DAT_REV_ITM); //Marcar columnas para revisión
                        blnRes=false;
                    }
                    else{ /* Cuando existe un peso en el item, se valida el rango permitido*/
                        BigDecimal bgdCanCal= objUti.redondearBigDecimal( ( ( bgdCanTon.multiply(new BigDecimal("1000")) ).divide(bgdPesKgr, BigDecimal.ROUND_HALF_UP) ).divide(bgdCanPza, BigDecimal.ROUND_HALF_UP), objParSis.getDecimalesMostrar());
                        //System.out.println("cantidadCalculada: "+bgdCanCal);
                        if( (bgdCanCal.compareTo(bgdMin)<0) || (bgdCanCal.compareTo(bgdMax)>0) ) {
                            objTblModImp34_02.setValueAt("S", i, objImp34_02.INT_TBL_DAT_REV_ITM); //Marcar columnas para revisión
                            //System.out.println("Fuera del rango permitido. Fila#"+i+" => bgdCanCal: "+bgdCanCal);
                            blnRes=false;
                        }
                        else{
                            objTblModImp34_02.setValueAt("N", i, objImp34_02.INT_TBL_DAT_REV_ITM); 
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
                if(arlDatConIngImp.size()>0){
                    if(intIndiceIngImp>0){
                        if ((blnHayCam) || (objImp34_01.isBlnHayCamImp34_01())  || (objImp34_02.isBlnHayCamImp34_02())   ){
                            if (isRegPro()) {
                                intIndiceIngImp=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceIngImp=0;
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
                if(arlDatConIngImp.size()>0){
                    if(intIndiceIngImp>0){
                        if ((blnHayCam) || (objImp34_01.isBlnHayCamImp34_01())  || (objImp34_02.isBlnHayCamImp34_02())   ){
                            if (isRegPro()) {
                                intIndiceIngImp--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceIngImp--;
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
                 if(arlDatConIngImp.size()>0){
                    if(intIndiceIngImp < arlDatConIngImp.size()-1){
                        if ((blnHayCam) || (objImp34_01.isBlnHayCamImp34_01())  || (objImp34_02.isBlnHayCamImp34_02())   ){
                            if (isRegPro()) {
                                intIndiceIngImp++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceIngImp++;
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
                 if(arlDatConIngImp.size()>0){
                    if(intIndiceIngImp<arlDatConIngImp.size()-1){
                        if ((blnHayCam) || (objImp34_01.isBlnHayCamImp34_01())  || (objImp34_02.isBlnHayCamImp34_02())   ){
                            if (isRegPro()) {
                                intIndiceIngImp=arlDatConIngImp.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceIngImp=arlDatConIngImp.size()-1;
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
                if ((blnHayCam) || (objImp34_01.isBlnHayCamImp34_01())  || (objImp34_02.isBlnHayCamImp34_02())   ){
                    isRegPro();
                }

                //para insertar no se los puede cambiar
                txtCodImp.setEditable(false);
                txtNomImp.setEditable(false);
                butImp.setEnabled(false);
                txtCodExp.setEditable(false);
                txtNomExp2.setEditable(false);
                txtNomExp.setEditable(false);
                butExp.setEnabled(false);

                limpiarFrm();
                mostrarTipDocPre();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

                objImp34_02.setFechaDocumento(dtpFecDoc.getText());
                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();
                objImp34_01.inicializar();
                objImp34_01.generarDatosCargoPagar();
                objImp34_01.setEditable(true);
                objImp34_02.inicializar();
                strTipNotPed="";
                strCodExp="";

                objTblModImp34_01=objImp34_01.getTblModImp34_01();
                objTblModImp34_02=objImp34_02.getTblModImp34_02();

                objImp34_02.setModoOperacionTooBar(objTooBar.getEstado());

                butConPedEmbLis.setEnabled(true);
                
                //Inicializar las variables que indican cambios.
                blnHayCam=false;

            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {
            objTblModImp34_01=objImp34_01.getTblModImp34_01();
            objTblModImp34_02=objImp34_02.getTblModImp34_02();
            
            txtCodImp.setEditable(true);
            txtNomImp.setEditable(true);
            butImp.setEnabled(true);

            txtCodExp.setEditable(true);
            txtNomExp2.setEditable(true);
            txtNomExp.setEditable(true);
            butExp.setEnabled(true);
        }

        public void clickModificar() {
            txtCodTipDoc.setEditable(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            dtpFecDoc.setEnabled(false);
            objImp34_02.setFechaDocumento(dtpFecDoc.getText());
            objImp34_01.setEditable(true);
            txtNumDoc.setEditable(false);
            txtCodDoc.setEditable(false);
            txtValDoc.setEditable(false);
            txtPesTotKgr.setEditable(false);
            txtCodImp.setEditable(false);
            txtNomImp.setEditable(false);
            butImp.setEnabled(false);
            txtCodExp.setEditable(false);
            txtNomExp2.setEditable(false);
            txtNomExp.setEditable(false);
            butExp.setEnabled(false);
            
            if(optTmFob.isSelected()){
                strTipNotPed="1";//ne_tipnotped
                intTipNotPed=1;
                objImp34_01.setTipoNotaPedido(Integer.parseInt(strTipNotPed));
                objImp34_02.setTipoNotaPedido(Integer.parseInt(strTipNotPed));//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
            } 
            else if(optTmCfr.isSelected()){
                strTipNotPed="2";//ne_tipnotped
                intTipNotPed=2;
                objImp34_01.setTipoNotaPedido(Integer.parseInt(strTipNotPed));
                objImp34_01.setValorFleteInactivo();
                objImp34_02.setTipoNotaPedido(Integer.parseInt(strTipNotPed));//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
            }
            else if(optPzaFob.isSelected()){
                strTipNotPed="3";//ne_tipnotped
                intTipNotPed=3;
                objImp34_01.setTipoNotaPedido(Integer.parseInt(strTipNotPed));
                objImp34_02.setTipoNotaPedido(Integer.parseInt(strTipNotPed));//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
            } 
            else if(optPzaCfr.isSelected()){
                strTipNotPed="4";//ne_tipnotped
                intTipNotPed=4;
                objImp34_01.setTipoNotaPedido(Integer.parseInt(strTipNotPed));
                objImp34_01.setValorFleteInactivo();
                objImp34_02.setTipoNotaPedido(Integer.parseInt(strTipNotPed));//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
            } 

            strCodExp=txtCodExp.getText();

            objImp34_02.setModoOperacionTooBar(objTooBar.getEstado());

            optPzaCfr.setEnabled(false);
            optPzaFob.setEnabled(false);
            optTmCfr.setEnabled(false);
            optTmFob.setEnabled(false);

            butConPedEmbLis.setEnabled(false);

            objImp34_01.colocaTotalGastosAdicionales(new BigDecimal(BigInteger.ZERO), INT_COD_CAR_PAG_OTR_GAS);
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarCabReg();
            txaObs2.setEditable(true);
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
            consultarReg();
            return true;
        }

        /**
         * Función que permite modificar el ingreso por Importación.
         * En esta función ya se maneja esquema de transferencias de inventario.
         * @return 
         */
        public boolean modificar() 
        {
            if (!actualizarReg())
                return false;           

            blnHayCam=false;
            return true;
        }

        /**
         * Función que permite eliminar lógicamente el ingreso por importación.
         * En esta función ya se maneja esquema de transferencias de inventario.
         * @return 
         */
        public boolean eliminar() 
        {
            try
            {               
                if(!eliminarReg())
                    return false;
            
                //Desplazarse al siguiente registro si es posible.
                if(arlDatConIngImp.size()>0){
                   if(intIndiceIngImp < arlDatConIngImp.size()-1){
                        intIndiceIngImp++;
                        cargarReg();
                   }
                   else
                   {
                       objTooBar.setEstadoRegistro("Eliminado");
                       limpiarFrm();
                   }
                }

                blnHayCam=false;            
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }

            return true;
        }

        /**
         * Función que permite anular el ingreso por importación.
         * En esta función ya se maneja esquema de transferencias de inventario.
         * @return 
         */
        public boolean anular()
        {
            if(!anularReg())
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
                if ((blnHayCam) || (objImp34_01.isBlnHayCamImp34_01())  || (objImp34_02.isBlnHayCamImp34_02())   ){
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
            arlDatFilEliImp34_03=objImp34_02.getArregloFilasEliminadas();

            if (!isCamVal())
                return false;

            return true;
        }

        public boolean beforeConsultar() {
//            objTblModImp34_01=objImp34_01.getTblModImp34_01();
//            objTblModImp34_02=objImp34_02.getTblModImp34_02();
            
            arlDatFilEliImp34_03=objImp34_02.getArregloFilasEliminadas();
            return true;
        }

        public boolean beforeModificar() 
        {
            boolean blnRes=true;

            arlDatFilEliImp34_03=objImp34_02.getArregloFilasEliminadas();

            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }            
            
            if (!isCamVal())
                return false;
            
            if(!verificaItmUni())
                return false;
            
            //Solo se modificarán los pedidos que no hayan sido arribados.
            if(!isPedidoArribado())
                return false;
            
            return blnRes;
        }

        public boolean beforeEliminar() 
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            
            if (!isCamVal())
                return false;
                          
            if(!verificaItmUniAnu())
                return false;          
            
            //if(!isDocumentoConfirmado())
                //return false;
            
            //Solo se eliminarán los pedidos que no hayan sido arribados.
            if(!isPedidoArribado())
                return false;
            
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            if (!isCamVal())
                return false;
            
            if(!verificaItmUniAnu())
                return false;
            
            //if(!isDocumentoConfirmado())
                //return false;
            
            //Solo se anularán los pedidos que no hayan sido arribados.
            if(!isPedidoArribado())
                return false;
            
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
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');            
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            blnHayCam=false;
            objTooBar.setEstado('w');
            cargarReg();
            objTooBar.afterConsultar();
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
        int intCodEmp=objParSis.getCodigoEmpresa();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+="      , a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
                strSQL+="      , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a1.co_mnu, a6.co_bod";
                strSQL+=" FROM ( (  (tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                strSQL+=" 	     ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+=" 	 )";
                strSQL+=" 	 LEFT OUTER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
                strSQL+="	 LEFT OUTER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                if(!blnConVenEme){
                    if (txtCodTipDoc.getText().equals("")){ //Validar que sólo se muestre los documentos asignados al programa.
                        if(objParSis.getCodigoUsuario()==1){
                            strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc ";
                            strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu() + " )";
                        }
                        else{
                            strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc ";
                            strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.co_usr=" + objParSis.getCodigoUsuario() + ")";
                        }
                    }
                }
                strSQL+=" WHERE a1.tx_numDoc2 IS NOT NULL ";
                if(!blnConVenEme){
                    strSQL+=" AND a6.tx_codAlt like '%I'"; //Mostrar Soslo items con Terminal I
                }
                if(intCodEmp==objParSis.getCodigoEmpresaGrupo()){
                    strAux=txtCodBod.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a6.co_bod=" + getCodigoBodegaEmpresa() + "";//la bodega que se trae query es la de empresa por tanto se debe hacer algo mas para obtener la del grupo
                }
                else{
                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strAux=txtCodBod.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a6.co_bod= " + strAux + "";
                }
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                strAux=txtCodImp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_emp = " + strAux + "";
                
                if(txtCodTipDoc.getText().equals("14")){
                    strAux=txtCodExp.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a1.co_exp = " + strAux + "";
                }
                else{
                    strAux=txtCodExp.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a1.co_cli = " + strAux + "";
                }
                
                //strAux=txtCodBod.getText();
                //if (!strAux.equals(""))
                //    strSQL+=" AND a6.co_bod = " + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                strAux=txtNumPed.getText();
                //if (!strAux.equals(""))
                //    strSQL+=" AND a1.tx_numDoc2 = '" + strAux.replaceAll("'", "''") + "'";
                if (!strAux.equals("")){
                    strSQL+=" AND (  UPPER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+="        OR LOWER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+=" )";
                }
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+="        , a1.co_usrIng, a15.tx_usr, a1.co_usrMod, a16.tx_usr, a1.co_mnu, a6.co_bod";
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc";
                //System.out.println("consultarReg:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConIngImp = new ArrayList();
                while(rst.next())
                {
                    arlRegConIngImp = new ArrayList();
                    arlRegConIngImp.add(INT_ARL_CON_COD_EMP,    rst.getInt("co_emp"));
                    arlRegConIngImp.add(INT_ARL_CON_COD_LOC,    rst.getInt("co_loc"));
                    arlRegConIngImp.add(INT_ARL_CON_COD_TIPDOC, rst.getInt("co_tipDoc"));
                    arlRegConIngImp.add(INT_ARL_CON_COD_DOC,    rst.getInt("co_doc"));
                    arlRegConIngImp.add(INT_ARL_CON_TXT_USRING, rst.getString("tx_nomUsrIng"));
                    arlRegConIngImp.add(INT_ARL_CON_TXT_USRMOD, rst.getString("tx_nomUsrMod"));
                    arlDatConIngImp.add(arlRegConIngImp);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConIngImp.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConIngImp.size()) + " registros");
                    intIndiceIngImp=arlDatConIngImp.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarCarPagImp()){
                    if (cargarDetRegItmVal()){            
                        if(objAsiDia.consultarDiario( objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP)
                                                    , objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC)
                                                    , objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC)
                                                    , objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) )){
                            if(optTmFob.isSelected()){
                                strTipNotPed="1";
                                intTipNotPed=1;
                            } //ne_tipnotped
                            else if(optTmCfr.isSelected()){
                                strTipNotPed="2";
                                intTipNotPed=2;
                            } //ne_tipnotped
                            else if(optPzaFob.isSelected()){
                                strTipNotPed="3";
                                intTipNotPed=3;
                            } //ne_tipnotped
                            else if(optPzaCfr.isSelected()){
                                strTipNotPed="4";
                                intTipNotPed=4;
                            } //ne_tipnotped

                            strCodExp=txtCodExp.getText();

                            objImp34_02.setBlnHayCamCamImp34_02(false);
                            objImp34_01.setBlnHayCamCamImp34_01(false);
                            if(!blnConVenEme)
                            {
                                mostrarVenConBod(1);
                            }
                            mostrarVenConImp(1);
                            mostrarVenConTipDoc(3);
                        }                      
                    }
                }
            }
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
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" 	, a1.ne_tipnotped";
                if(objUti.getStringValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC).equals("14"))
                    strSQL+=" 	, a1.co_exp, a5.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp";
                else
                    strSQL+=" 	, a1.co_cli, a5.co_cli AS co_exp, a5.tx_ide AS tx_nomExp/*rucCliente*/, a5.tx_nom AS tx_aliExp/*nombreCliente*/";
                
                strSQL+=" 	, a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+=" 	, a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp AS co_imp";
                strSQL+=" 	, a3.tx_nom AS tx_nomImp, a8.co_bodGrp AS co_bod, a7.tx_nom AS tx_nomBod";
                strSQL+="  FROM (  tbm_cabMovInv AS a1 ";
                strSQL+="          INNER JOIN (tbm_detMovInv AS a6  ";
		strSQL+="	               INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
		strSQL+="                      INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
		strSQL+="	    )";
                strSQL+=" 	    ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+="  )";
                strSQL+="  INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+="  INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                
                if(objUti.getStringValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC).equals("14"))
                    strSQL+="  INNER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                else
                    strSQL+="  INNER JOIN tbm_cli AS a5 ON(a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)";

                strSQL+="  WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP);
                strSQL+="  AND a1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC);
                strSQL+="  AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC);
                strSQL+="  AND a1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC);
                strSQL+="  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" 	, a1.ne_tipnotped";
                if(objUti.getStringValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC).equals("14"))
                    strSQL+=" 	, a1.co_exp, a5.co_exp, a5.tx_nom, a5.tx_nom2";
                else
                    strSQL+=" 	, a1.co_cli, a5.co_cli, a5.tx_ide, a5.tx_nom";
                
                strSQL+=" 	, 	a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+=" 	, a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp";
                strSQL+=" 	, a3.tx_nom, a8.co_bodGrp, a7.tx_nom";
                //System.out.println("cargarCabReg: " + strSQL);
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
                    objImp34_02.setStrCodImpPedEmbSel(strAux);
                    strAux=rst.getString("tx_nomImp");
                    txtNomImp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_exp");
                    txtCodExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomExp");
                    txtNomExp2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_aliExp");
                    txtNomExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_bod");
                    txtCodBod.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomBod");
                    txtNomBod.setText((strAux==null)?"":strAux);

                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    datFecDoc=rst.getDate("fe_doc");
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_numDoc2");
                    txtNumPed.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    strAux=rst.getObject("nd_pestotkgr")==null?"0":(rst.getString("nd_pestotkgr").equals("")?"0":rst.getString("nd_pestotkgr"));
                    txtPesTotKgr.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));

                    strAux=rst.getString("ne_tipnotped");
                                        
                    objImp34_01.setTipoNotaPedido(rst.getInt("ne_tipnotped"));
                    objImp34_02.setTipoNotaPedido(rst.getInt("ne_tipnotped"));
                                                           
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
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }

            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceIngImp+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConIngImp.size()) );
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)     {
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
    private boolean cargarDetRegItmVal(){ //por consulta
        boolean blnRes=true;
        try{
            objTblModImp34_02.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT x.co_itmMae, x.co_itm AS co_itmEmp, x.tx_codAlt, x.tx_codAlt2, x.tx_nomItm, x.nd_pesitmkgr , y.co_parara, y.tx_par, y.tx_des, x.nd_ara, x.nd_can";
                    strSQL+="      , x.nd_preUniImp, x.nd_valTotFobCfr,  x.nd_canTonMet, x.nd_valFle, x.nd_valCfr, x.nd_valTotAra, x.nd_valTotGas";
                    strSQL+="      , x.nd_cosTot, x.nd_cosUni, x.co_reg, x.tx_desCorUniMed, x.co_itmAct  , x.co_emprelpedembimp";
                    strSQL+="     , x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp  , x.co_ctaAct, x.co_ctaPas, y.co_itmGrp, x.co_Exp, x.tx_nomExp, x.nd_cosKgr";
                    strSQL+=" FROM(";
                    strSQL+="      SELECT a3.co_itmMae, a3.co_itm, b1.tx_codAlt, a1.tx_codAlt2, b1.tx_nomItm";
                    strSQL+="           , CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+="           , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara, b1.co_exp, b3.tx_nom as tx_nomExp";
                    strSQL+="           , b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr , b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr";
                    strSQL+="           , CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra";
                    strSQL+="           , b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor AS tx_desCorUniMed";
                    strSQL+="           , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp, b1.co_itmAct, d1.co_ctaAct, d1.co_ctaPas";
                    strSQL+="           , CASE WHEN a1.nd_pesitmkgr > 0 THEN ROUND((b1.nd_cosUni/a1.nd_pesitmkgr),2) ELSE 0 END as nd_cosKgr";
                    strSQL+="       FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1 ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc";
                    strSQL+="             INNER JOIN tbm_cabPedEmbImp AS d1 ON c1.co_emprelpedembimp=d1.co_emp AND c1.co_locrelpedembimp=d1.co_loc AND c1.co_tipdocrelpedembimp=d1.co_tipDoc AND c1.co_docrelpedembimp=d1.co_doc ";
                    strSQL+="       )";
                    strSQL+="       INNER JOIN ( ";
                    strSQL+="         ( (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg) )";
                    strSQL+="       ) ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+="       INNER JOIN tbm_expImp AS b3 ON(b1.co_exp=b3.co_exp)";
                    strSQL+="       WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                    strSQL+="       AND b1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                    strSQL+="       AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+="       AND b1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                    strSQL+="       GROUP BY a3.co_itmMae, a3.co_itm, b1.tx_codAlt, a1.tx_codAlt2, b1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="              , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                    strSQL+="              , b1.nd_ara, b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle";
                    strSQL+="              , b1.nd_valCfr, b1.nd_valTotAra, b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor, b1.co_itmAct";
                    strSQL+="              , d1.co_ctaAct, d1.co_ctaPas, b1.co_exp, b3.tx_nom";
                    strSQL+="       ORDER BY b1.co_reg, b1.tx_codAlt";
                    strSQL+=" ) AS x";
                    strSQL+=" INNER JOIN(";
                    strSQL+="      SELECT b1.co_itmMae, b2.co_itm AS co_itmGrp, b2.co_parara, b2.tx_par, b2.tx_des ";
                    strSQL+="      FROM( SELECT a2.co_itmMae FROM tbm_inv AS a1";
                    strSQL+="            INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="            WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+="      ) AS b1";
                    strSQL+="      INNER JOIN (";
                    strSQL+="            SELECT a2.co_itmMae, a2.co_itm";
                    strSQL+="                 , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara, CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                    strSQL+="                 , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                    strSQL+="            FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+="            LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                    strSQL+="                            INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra ";
                    strSQL+="            )  ON a1.co_grpImp=a4.co_grp";
                    strSQL+="            WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+="      ) AS b2 ON b1.co_itmMae=b2.co_itmMae";
                    strSQL+=" ) AS y ON x.co_itmMae=y.co_itmMae";
                    strSQL+=" GROUP BY x.co_itmMae, x.co_itm, x.tx_codAlt, x.tx_codAlt2, x.tx_nomItm, x.nd_pesitmkgr , y.co_parara, y.tx_par, y.tx_des, x.nd_ara, x.nd_can";
                    strSQL+="        , x.nd_preUniImp, x.nd_valTotFobCfr,  x.nd_canTonMet, x.nd_valFle, x.nd_valCfr, x.nd_valTotAra, x.nd_valTotGas";
                    strSQL+="        , x.nd_cosTot, x.nd_cosUni, x.co_reg, x.tx_desCorUniMed, x.co_itmAct  , x.co_emprelpedembimp";
                    strSQL+="        , x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp  , x.co_ctaAct, x.co_ctaPas, y.co_itmGrp, x.co_Exp, x.tx_nomExp, x.nd_cosKgr";
                    strSQL+=" ORDER BY x.co_reg, x.tx_codAlt";
                    //System.out.println("cargarDetRegItmVal: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objImp34_02.INT_TBL_DAT_LIN,"");
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_MAE,    rst.getString("co_itmMae"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM,        rst.getString("co_itmGrp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codAlt"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_LET_ITM,    rst.getString("tx_codAlt2"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_BUT_ITM,        null);
                        vecReg.add(objImp34_02.INT_TBL_DAT_NOM_ITM,        rst.getString("tx_nomItm"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_UNI_MED,        rst.getString("tx_desCorUniMed"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_PES,            rst.getString("nd_pesitmkgr"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ARA,        rst.getString("co_parara"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_NOM_ARA,        rst.getString("tx_par"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_DES_ARA,        rst.getString("tx_des"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_POR_ARA,        rst.getString("nd_ara"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_EXP,        rst.getString("co_Exp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_NOM_EXP,        rst.getString("tx_nomExp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_BUT_EXP,        null);                        
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_TON_MET,    rst.getString("nd_canTonMet"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_PZA,        rst.getString("nd_can"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_PRE_UNI,        rst.getString("nd_preUniImp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_FOB_CFR,    rst.getString("nd_valTotFobCfr"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_VAL_FLE,        rst.getString("nd_valFle"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_VAL_CFR,        rst.getString("nd_valCfr"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_VAL_CFR_ARA,    "0");
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_ARA,        rst.getString("nd_valTotAra"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_GAS,        rst.getString("nd_valTotGas"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_COS,        rst.getString("nd_cosTot"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COS_UNI,        rst.getString("nd_cosUni"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COS_KGR,        rst.getString("nd_cosKgr"));
                        
                        strCodEmpPedEmb=rst.getString("co_emprelpedembimp");
                        strCodLocPedEmb=rst.getString("co_locrelpedembimp");
                        strCodTipDocPedEmb=rst.getString("co_tipdocrelpedembimp");
                        strCodDocPedEmb=rst.getString("co_docrelpedembimp");
                        
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_EMP,    rst.getString("co_itmEmp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_EMP_ACT,rst.getString("co_itmAct"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_EMP_AUX,rst.getString("co_itmEmp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_PED_EMB,    "0");
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_PEN_PED_EMB,"0");
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_UTI_ING_IMP,"0");    
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_ING_IMP_AUX,rst.getString("nd_can"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_REV_ITM,        "");

                        strCodCtaActPedEmb=rst.getString("co_ctaAct");
                        strCodCtaPasPedEmb=rst.getString("co_ctaPas");

                        vecDat.add(vecReg);
                    }
                    
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objImp34_02.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                    }
                    objImp34_02.regenerarCalculoCfrAra();
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
        String strTipCarPag="";
        try{
            objTblModImp34_01.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carpag, a1.nd_valcarpag";
                    strSQL+="      , a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag";
                    strSQL+="      , (CASE WHEN a2.st_pagExt IS NULL THEN 'N' ELSE a2.st_pagExt END) AS st_pagExt";
                    strSQL+=" FROM tbm_carPagMovInv AS a1 INNER JOIN tbm_carPagImp AS a2";
                    strSQL+=" ON a1.co_carpag=a2.co_carpag";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                    strSQL+=" ORDER BY a2.ne_ubi";
                    //System.out.println("cargarCarPagImp: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objImp34_01.INT_TBL_DAT_LIN,"");
                        vecReg.add(objImp34_01.INT_TBL_DAT_TIP_CAR_PAG_IMP,    rst.getString("tx_tipcarpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_COD_CAR_PAG_IMP,    rst.getString("co_carpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_NOM_CAR_PAG_IMP,    rst.getString("tx_nomCarPag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_VAL_CAR_PAG_IMP,    rst.getString("nd_valcarpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,rst.getString("nd_valcarpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_TIP_NOT_PED,        "");

                        if(optTmFob.isSelected())
                            vecReg.setElementAt("1", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optTmCfr.isSelected())
                            vecReg.setElementAt("2", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaFob.isSelected())
                            vecReg.setElementAt("3", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaCfr.isSelected())
                            vecReg.setElementAt("4", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);


                        strTipCarPag=rst.getObject("tx_tipcarpag")==null?"":rst.getString("tx_tipcarpag");
                        if(strTipCarPag.equals("F")){
                            bgdValFleSav=bgdValFleSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        else if(strTipCarPag.equals("A")){
                            bgdValAraSav=bgdValAraSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        else if(strTipCarPag.equals("G")){
                            bgdValGasSav=bgdValGasSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        vecReg.add(objImp34_01.INT_TBL_DAT_TIP_PAG_EXT,rst.getString("st_pagExt"));
                        vecDat.add(vecReg);
                    }

                    objImp34_01.setFleteItem(bgdValFleSav);
                    objImp34_01.setDerechosArancelarios(bgdValAraSav);
                    objImp34_01.setTotalGastos(bgdValGasSav);

                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objImp34_01.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
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

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        objCodSegInsAnt=null;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(insertar_tbmCabMovInv()){
                    if(insertar_tbmDetMovInv()){
                        if(insertar_tbmCarPagMovInv()){
                            if(insertar_tbmCabOrdConInv()){
                                if(insertar_tbmDetOrdConInv()){
                                    if(insertar_tbmCabOrdDis()){
                                        if(insertar_tbmDetOrdDis()){
                                            //fin :   inserta documento para realizar conteo
                                            if (objAsiDia.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"))){
                                                if(getCodSegPedEmb()){
                                                    if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 5, txtCodImp.getText(), strCodLocPre, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                                                        costearItm();
                                                        con.commit();
                                                        blnRes=true;
                                                        enviarCorElePreVta();
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

    private boolean getSecuencias(){
        boolean blnRes=true;
        try{
            if(con!=null){
                intSecGrp=objUltDocPrint2.getNumSecDoc(con, objParSis.getCodigoEmpresa() );//la empresa es el grupo
                intSecEmp=objUltDocPrint2.getNumSecDoc(con, Integer.parseInt(txtCodImp.getText()) );//la empresa-importador
           }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private int getCodigoBodegaEmpresa(){
        int intCodBodEmp=-1;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_bod, a1.co_empgrp, a1.co_bodgrp";
                strSQL+=" FROM tbr_bodempbodgrp AS a1";
                strSQL+=" WHERE a1.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.co_bodGrp=" + txtCodBod.getText() + "";
                if(txtCodImp.getText().length()>0)
                    strSQL+=" AND a1.co_emp=" + txtCodImp.getText() + "";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodBodEmp=rstLoc.getInt("co_bod");
                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodBodEmp;
    }

    /**
     * Función que permite obtener la cuenta del debe para INIMPO y COMPRAS LOCALES
     * @return intCodCtaBodEmp
     */
    private boolean getCodigoCuentaBodegaEmpresa(){
        boolean blnRes=true;
        intCodCtaBodEmpAsiDia=-1;
        strNumCtaBodEmpAsiDia="";
        strNomCtaBodEmpAsiDia="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a2.co_bod, a1.tx_nom, a1.co_ctaExi, a3.tx_codcta, a3.tx_deslar";
                strSQL+=" FROM tbm_plaCta AS a3 INNER JOIN tbm_bod AS a1";
                strSQL+=" ON a3.co_emp=a1.co_emp AND a3.co_cta=a1.co_ctaExi";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" WHERE a2.co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.co_bodGrp=" + txtCodBod.getText() + "";
                    strSQL+=" AND a2.co_emp=" + txtCodImp.getText() + "";
                }
                else{
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.co_bod=" + txtCodBod.getText() + "";
                }
                strSQL+=" AND a1.st_reg='A' AND a3.st_reg='A'";
                strSQL+=" GROUP BY a2.co_bod, a1.tx_nom, a1.co_ctaExi, a3.tx_codcta, a3.tx_deslar";
                strSQL+=" ORDER BY a2.co_bod, a1.tx_nom";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaBodEmpAsiDia=rst.getInt("co_ctaExi");
                    strNumCtaBodEmpAsiDia=rst.getString("tx_codcta");
                    strNomCtaBodEmpAsiDia=rst.getString("tx_deslar");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
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
    private boolean insertar_tbmCabMovInv(){
        int intUltReg;
        boolean blnRes=true;
        intNumIngImpDia=-1;
        try{
            txtPesTotKgr.setText(""+objImp34_02.calcularV20PesoTotal());
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                getSecuencias();

                //Obtener el código del último registro.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + txtCodImp.getText() + "";
                strSQL+=" AND a1.co_loc=" + strCodLocPre + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                //System.out.println("insertar_tbmCabMovInv.tbm_cabMovInv: " + strSQL);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                strSQL ="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocIngImp";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumIngImpDia=rst.getInt("ne_numDocIngImp");
                }
                intNumIngImpDia++;

                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_cabMovInv(";
                strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,ne_secGrp,ne_secEmp,ne_numCot,ne_numDoc,tx_numDoc2,";
                strSQL+="        tx_numPed,ne_numOrdDes,ne_numGui,co_dia,fe_doc,fe_ven,co_cli,tx_ruc,tx_nomCli,tx_dirCli,";
                strSQL+="        tx_telCli,tx_ciuCli,co_com,tx_nomVen,tx_ate,co_forPag,tx_desForPag,nd_sub,nd_porIva,";
                strSQL+="        nd_valIva,nd_tot,nd_pesTotKgr,tx_ptoPar,tx_tra,co_motTra,tx_desMotTra,co_cta,co_motDoc,";
                strSQL+="        co_mnu,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,co_usrIng,co_usrMod,tx_comIng,";
                strSQL+="        tx_comMod,fe_con,tx_obs3,co_forRet,tx_vehRet,tx_choRet,ne_numVecRecDoc,fe_ultRecDoc,";
                strSQL+="        tx_obsSolAut,tx_obsAutSol,st_aut,ne_valAut,st_tipDev,st_conInv,st_docGenDevMerMalEst,";
                strSQL+="        st_regRep,ne_numDocRee,st_creGuiRem,st_conInvTraAut,co_locRelSolDevVen,co_tipDocRelSolDevVen,";
                strSQL+="        co_docRelSolDevVen,st_excDocConVenCon,fe_autExcDocConVenCon,co_usrAutExcDocConVenCon,";
                strSQL+="        tx_comAutExcDocConVenCon,co_ben,tx_benChq,st_docConMerSalDemDebFac,st_autAnu,fe_autAnu,";
                strSQL+="        co_usrAutAnu,tx_comAutAnu,co_ptoDes,st_emiChqAntRecFacPrv,st_docMarLis,st_itmSerPro,";
                strSQL+="        ne_tipNotPed,co_exp,co_empRelPedEmbImp,co_locRelPedEmbImp,co_tipDocRelPedEmbImp,co_docRelPedEmbImp, st_cieDis, st_ingImp)";
                strSQL+=" VALUES (";
                strSQL+=" " + txtCodImp.getText(); //co_emp
                strSQL+=", " + strCodLocPre; //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc                
                strSQL+=", " + txtCodDoc.getText(); //co_doc
                strSQL+=", " + intSecGrp;//ne_secGrp
                strSQL+=", " + intSecEmp;//ne_secEmp
                strSQL+=", Null";//ne_numCot
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);//ne_numDoc
                strSQL+=", " + objUti.codificar(txtNumPed.getText(),1);//tx_numDoc2
                strSQL+=", Null";//tx_numPed
                strSQL+=", Null";//ne_numOrdDes
                strSQL+=", Null";//ne_numGui
                strSQL+=", " + txtCodDoc.getText();//co_dia
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//fe_ven
                if(txtCodTipDoc.getText().equals("14")){//INIMPO
                    strSQL+=", Null";//co_cli
                    strSQL+=", Null";//tx_ruc
                    strSQL+=", Null";//tx_nomCli
                }
                else{//COMPRAS LOCALES
                    strSQL+=", " + txtCodExp.getText() + "";//co_cli
                    strSQL+=", " + objUti.codificar(txtNomExp.getText()) + "";//tx_ruc
                    strSQL+=", " + objUti.codificar(txtNomExp2.getText()) + "";//tx_nomCli
                }
                
                strSQL+=", Null";//tx_dirCli
                strSQL+=", Null";//tx_telCli
                strSQL+=", Null";//tx_ciuCli
                strSQL+=", Null";//co_com
                strSQL+=", Null";//tx_nomVen
                strSQL+=", Null";//tx_ate
                strSQL+=", Null";//co_forPag
                strSQL+=", Null";//tx_desForPag
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3);//nd_sub
                strSQL+=", 0";//nd_porIva
                strSQL+=", 0";//nd_valIva
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3);//nd_tot
                strSQL+=", " + objUti.codificar(txtPesTotKgr.getText());//nd_pesTotKgr
                strSQL+=", Null";//tx_ptoPar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_motTra
                strSQL+=", Null";//tx_desMotTra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu();//co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText() + strVer); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText());//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultMod
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrIng
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comIng
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comMod
                strSQL+=", Null";//fe_con
                strSQL+=", 'PryCos'";//tx_obs3
                strSQL+=", Null";//co_forRet
                strSQL+=", Null";//tx_vehRet
                strSQL+=", Null";//tx_choRet
                strSQL+=", 0";//ne_numVecRecDoc
                strSQL+=", Null";//fe_ultRecDoc
                strSQL+=", Null";//tx_obsSolAut
                strSQL+=", Null";//tx_obsAutSol
                strSQL+=", Null";//st_aut
                strSQL+=", Null";//ne_valAut
                strSQL+=", 'C'";//st_tipDev
                strSQL+=", 'C'";//st_conInv
                strSQL+=", Null";//st_docGenDevMerMalEst
                strSQL+=", 'I'";//st_regRep
                strSQL+=", Null";//ne_numDocRee
                strSQL+=", 'N'";//st_creGuiRem
                strSQL+=", 'N'";//st_conInvTraAut
                strSQL+=", Null";//co_locRelSolDevVen
                strSQL+=", Null";//co_tipDocRelSolDevVen
                strSQL+=", Null";//co_docRelSolDevVen
                strSQL+=", Null";//st_excDocConVenCon
                strSQL+=", Null";//fe_autExcDocConVenCon
                strSQL+=", Null";//co_usrAutExcDocConVenCon
                strSQL+=", Null";//tx_comAutExcDocConVenCon
                strSQL+=", Null";//co_ben
                strSQL+=", Null";//tx_benChq
                strSQL+=", 'N'";//st_docConMerSalDemDebFac
                strSQL+=", Null";//st_autAnu
                strSQL+=", Null";//fe_autAnu
                strSQL+=", Null";//co_usrAutAnu
                strSQL+=", Null";//tx_comAutAnu
                strSQL+=", Null";//co_ptoDes             objUti.codificar(txtCodBod.getText())
                strSQL+=", Null";//st_emiChqAntRecFacPrv
                strSQL+=", Null";//st_docMarLis
                strSQL+=", Null";//st_itmSerPro

                if(optTmFob.isSelected())
                    strSQL+=",'1'"; //ne_tipNotPed
                else if(optTmCfr.isSelected())
                    strSQL+=",'2'"; //ne_tipNotPed
                else if(optPzaFob.isSelected())
                    strSQL+=",'3'"; //ne_tipNotPed
                else if(optPzaCfr.isSelected())
                    strSQL+=",'4'"; //ne_tipNotPed

                if(txtCodTipDoc.getText().equals("14"))//INIMPO
                    strSQL+=", " + objUti.codificar(txtCodExp.getText());//co_exp
                else//Compras Locales
                    strSQL+=", Null";//co_exp
                
                strSQL+=", " + strCodEmpPedEmb;//co_empRelPedEmbImp
                strSQL+=", " + strCodLocPedEmb;//co_locRelPedEmbImp
                strSQL+=", " + strCodTipDocPedEmb;//co_tipDocRelPedEmbImp
                strSQL+=", " + strCodDocPedEmb;//co_docRelPedEmbImp
                strSQL+=", 'N'";//st_cieDis               
                strSQL+=", 'P'";//st_ingImp    
                
                strSQL+=");";
                System.out.println("insertar_tbmCabMovInv: " + strSQL);
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
        
    private boolean generaNuevoContenedorItemsMovimientoStock(int intCodEmp, int intCodItm, double dlbCanMov, int intCodBod)
    {
        boolean blnRes=true;
        double dblAux;
        int intCodigoItemGrupo=0, intCodigoItemMaestro=0;
        String strCodTresLetras="";
        try
        {
            intCodigoItemGrupo=getCodigoItemGrupo(intCodEmp,intCodItm);
            intCodigoItemMaestro=getCodigoMaestroItemGrupo(intCodEmp,intCodItm);
            strCodTresLetras=getCodigoLetraItem(intCodEmp,intCodItm);
            if(intCodigoItemGrupo==0 || intCodigoItemMaestro==0 || strCodTresLetras.equals(""))
            {
                blnRes=false;
            }
            dblAux=dlbCanMov;
            if(dblAux<0){   dblAux=dblAux*-1;        }
            
            arlRegStkInvItm = new ArrayList();
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP,intCodigoItemGrupo);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP,intCodItm);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE,intCodigoItemMaestro);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodTresLetras);
            arlRegStkInvItm.add(INT_ARL_STK_INV_CAN_ITM, dblAux );
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP,intCodBod );
            arlDatStkInvItm.add(arlRegStkInvItm);
        }
        catch(Exception e)
        {
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
        
    private String getCodigoLetraItem(int intCodEmp, int intCodItm)
    {
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strCadena;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null)
            {
                stmLoc=conLoc.createStatement();
                strCadena ="";
                strCadena+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2";
                strCadena+=" FROM tbm_inv as x1 WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm;
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next())
                {
                    strCodLetItm=rstLoc.getString("tx_codAlt2");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            strCodLetItm="";
        }
        return strCodLetItm;
   }

   private int getCodigoItemGrupo(int intCodEmp, int intCodItm)
   {
       int intCodItmGrp=0;
       java.sql.Statement stmLoc;
       java.sql.ResultSet rstLoc;
       java.sql.Connection conLoc;
       String strCadena;
       try
       {
           conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if (conLoc != null)
           {
               stmLoc=conLoc.createStatement();
               strCadena="";
               strCadena+=" SELECT co_itm FROM tbm_equInv as x1 ";
               strCadena+=" WHERE x1.co_itmMae = ( select co_itmMae from tbm_Equinv as a1 where co_emp="+intCodEmp+" and co_itm="+intCodItm+") ";
               strCadena+=" AND x1.co_emp="+objParSis.getCodigoEmpresaGrupo();
               rstLoc=stmLoc.executeQuery(strCadena);
               if(rstLoc.next())
               {
                   intCodItmGrp=rstLoc.getInt("co_itm");
               }
               rstLoc.close();
               rstLoc=null;
               stmLoc.close();
               stmLoc=null;
           }
           conLoc.close();
           conLoc=null;
       }
       catch(Exception e)
       {
           objUti.mostrarMsgErr_F1(null, e);
           intCodItmGrp=0;
       }
       return intCodItmGrp;
   }


    private int getCodigoMaestroItemGrupo(int intCodEmp, int intCodItm)
    {
        int intCodItmMae=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strCadena;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc=conLoc.createStatement();
                strCadena="";
                strCadena+=" SELECT x1.co_itmMae FROM tbm_equInv as x1 ";
                strCadena+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" ";
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next())
                {
                    intCodItmMae=rstLoc.getInt("co_itmMae");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            intCodItmMae=0;
        }
        return intCodItmMae;
    }
        
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetMovInv(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=1;
        int intCodBodEmp=-1;
        try{
            if (con!=null){
                intCodBodEmp=getCodigoBodegaEmpresa();
                arlDatCodItmMaeConInv.clear();
                stm=con.createStatement();
                
                arlDatStkInvItm = new ArrayList(); 
                for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detMovInv(";
                    strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,co_reg, co_Exp, ne_numFil,co_itm,co_itmAct,tx_codAlt,tx_codAlt2,";
                    strSQL+="        tx_nomItm,tx_uniMed,co_bod,tx_nomBodOrgDes,nd_can,nd_canOrg,nd_canDev,nd_cosUni,nd_preUniVenLis,";
                    strSQL+="        nd_porDesVenMax,nd_preUni,nd_porDes,st_ivaCom,nd_tot,nd_cosTot,nd_exi,nd_valExi,nd_cosPro,";
                    strSQL+="        nd_cosUniGrp,nd_cosTotGrp,nd_exiGrp,nd_valExiGrp,nd_cosProGrp,st_merIngEgrFisBod,nd_canCon,";
                    strSQL+="        nd_canNunRec,nd_canTotNunRecPro,nd_canTotMalEst,nd_canTotMalEstPro,tx_obs1,co_usrCon,";
                    strSQL+="        co_locRelSolDevVen,co_tipDocRelSolDevVen,co_docRelSolDevVen,co_regRelSolDevVen,co_locRelSolSalTemMer,";
                    strSQL+="        co_tipDocRelSolSalTemMer,co_docRelSolSalTemMer,co_regRelSolSalTemMer,st_cliRetEmpRel,tx_obsCliRetEmpRel,";
                    strSQL+="        nd_ara,nd_preUniImp,nd_valTotFobCfr,nd_cantonmet,nd_valFle,nd_valCfr,nd_valTotAra,nd_valTotGas,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + txtCodImp.getText(); //co_emp
                    strSQL+=", " + strCodLocPre; //co_loc
                    strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText(); //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_EXP);//co_exp
                    strSQL+=", Null";//ne_numFil
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP);//co_itm
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP);//co_itmAct
                    strSQL+=", '" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM) + "'";//tx_codAlt
                    strSQL+=", '" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_LET_ITM) + "'";//tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM)) + "";//tx_nomItm
                    strSQL+=", '" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_UNI_MED) + "'";//tx_uniMed
                    strSQL+=", " + intCodBodEmp + "";//co_bod
                    strSQL+=", '" + txtNomBod.getText() + "'";//tx_nomBodOrgDes
                    //strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA) + "";//nd_can
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA) + "";//nd_can
                    strSQL+=", Null";//nd_canOrg
                    strSQL+=", 0";//nd_canDev
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_cosUni
                    strSQL+=", Null";//nd_preUniVenLis
                    strSQL+=", Null";//nd_porDesVenMax
                    strSQL+=", Null";//nd_preUni
                    strSQL+=", 0";//nd_porDes
                    strSQL+=", 'N'";//st_ivaCom
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_tot 'precio al publico'
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_cosTot 'precio de compra para vender'
                    strSQL+=", 0";//nd_exi
                    strSQL+=", 0";//nd_valExi
                    strSQL+=", 0";//nd_cosPro
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_cosUniGrp
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_cosTotGrp
                    strSQL+=", 0";//nd_exiGrp
                    strSQL+=", 0";//nd_valExiGrp
                    strSQL+=", 0";//nd_cosProGrp
                    
                    strSQL+=", 'S'";//st_merIngEgrFisBod
                    strSQL+=", 0";//nd_canCon
                    strSQL+=", 0";//nd_canNunRec
                    strSQL+=", Null";//nd_canTotNunRecPro
                    strSQL+=", Null";//nd_canTotMalEst
                    strSQL+=", Null";//nd_canTotMalEstPro
                    strSQL+=", Null";//tx_obs1
                    strSQL+=", Null";//co_usrCon
                    strSQL+=", Null";//co_locRelSolDevVen
                    strSQL+=", Null";//co_tipDocRelSolDevVen
                    strSQL+=", Null";//co_docRelSolDevVen
                    strSQL+=", Null";//co_regRelSolDevVen
                    strSQL+=", Null";//co_locRelSolSalTemMer
                    strSQL+=", Null";//co_tipDocRelSolSalTemMer
                    strSQL+=", Null";//co_docRelSolSalTemMer
                    strSQL+=", Null";//co_regRelSolSalTemMer
                    strSQL+=", Null";//st_cliRetEmpRel
                    strSQL+=", Null";//tx_obsCliRetEmpRel
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_ara
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_preUniImp
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_valTotFobCfr
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_cantonmet
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_valFle
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_valCfr
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_valTotAra
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS).toString()), objParSis.getDecimalesBaseDatos()) + "";//nd_valTotGas
                    strSQL+=", 'I'";//st_regRep
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;
                    if(objTooBar.getEstado()=='n'){
                        if(!generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),Integer.parseInt(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString()),Double.parseDouble(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString()),intCodBodEmp)){
                            blnRes=false;
                        }
                    }
                                        
                    arlRegCodItmMaeConInv=new ArrayList();
                    arlRegCodItmMaeConInv.add(INT_ARL_COD_ITM_MAE_CON_INV, objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_MAE));
                    arlDatCodItmMaeConInv.add(arlRegCodItmMaeConInv);
                    
                }

                if(objTooBar.getEstado()=='n')
                {
                     if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
                         //Se agrega cantidad por ingresar en Importaciones.
                         if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "+", 0, arlDatStkInvItm)){
                            //Se comenta para que no genere cantidades disponibles de Ingresos por Importación, ya que se manejará disponibles en conteos de inventario(importaciones).
                            //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm)){
                                    arlDatStkInvItm.clear();
                            //}else{blnRes=false;}
                       }else{blnRes=false;}
                    }else{blnRes=false;}
                }
                System.out.println("insertar_tbmDetMovInv: " + strSQLIns);
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCarPagMovInv(){
        boolean blnRes=true;
        String strSQLIns="";
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp34_01.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_carPagMovInv(";
                    strSQL+="          co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_carpag, nd_valcarpag)";
                    strSQL+=" VALUES (";
                    strSQL+=" " + txtCodImp.getText(); //co_emp
                    strSQL+=", " + strCodLocPre; //co_loc
                    strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText(); //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblModImp34_01.getValueAt(i, objImp34_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp34_01.getValueAt(i, objImp34_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModImp34_01.getValueAt(i, objImp34_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp34_01.getValueAt(i, objImp34_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valcarpag
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;
                }
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
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes = false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if(actualizar_tbmCabMovInv()){
                    if(eliminar_registrosEliminados()){ //hace el update a tbm_inv de los registros q han sido borrados
                        if(anular_detalleModeloTabla()){ //hace el update a tbm_inv de los registros que se encuentran en el modelo
                            if(actualizar_tbmDetMovInv()){
                                if(eliminar_tbmCarPagMovInv()){
                                    if(insertar_tbmCarPagMovInv()){
                                        if(actualizar_tbmCabOrdDis()){
//                                           if(actualizar_tbmDetOrdDis()){
                                                if(objAsiDia.actualizarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()) ,Integer.parseInt(txtCodDoc.getText()),txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A")){
                                                    blnRes=true;
                                                    costearItm();
                                                    con.commit();
                                                    con.setAutoCommit(true);
                                                    objImp34_02.getArregloFilasEliminadas().clear();
                                                    objTblModImp34_02.initRowsState();                                                
                                                }
                                                else
                                                    con.rollback();
                                            //}
                                            //else
                                              //  con.rollback();
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

//                if(blnRes){
//                    for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
//                        if(objTblModImp34_02.getValueAt(i,objImp34_02.INT_TBL_DAT_COD_ITM_EMP) != null ){
//                            objTblModImp34_02.setValueAt("E", i, INT_TBL_ESTADO);
//                            objTblModImp34_02.setValueAt(objTblModImp34_02.getValueAt(i, INT_TBL_CODITM).toString() , i, INT_TBL_ITMORI);
//                            objTblModImp34_02.setValueAt(objTblModImp34_02.getValueAt(i, INT_TBL_CODBOD).toString() , i, INT_TBL_BODORI);
//                            objTblModImp34_02.setValueAt(objTblModImp34_02.getValueAt(i, INT_TBL_CANMOV).toString() , i, INT_TBL_CANORI);
//                        }
//                    }
//                }

                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException Evt){
            objUti.mostrarMsgErr_F1(this, Evt);  blnRes = false;
        }
        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(this, Evt); blnRes = false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabMovInv";
                strSQL+=" SET ne_numdoc=" + objUti.codificar(txtNumDoc.getText(),2) + "";
                strSQL+=", tx_numDoc2=" + objUti.codificar(txtNumPed.getText(),1) + "";
                strSQL+=", fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", nd_sub=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=", nd_tot=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=", nd_pesTotKgr=" + objUti.codificar(txtPesTotKgr.getText()) + "";
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", fe_ultMod='" + strAux + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=", tx_comMod=" +  objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=", st_regRep='M'";
//                if(optTmFob.isSelected())
//                    strSQL+=", ne_tipNotPed='1'"; //
//                else if(optTmCfr.isSelected())
//                    strSQL+=", ne_tipNotPed='2'"; //ne_tipNotPed
//                else if(optPzaFob.isSelected())
//                    strSQL+=", ne_tipNotPed='3'"; //ne_tipNotPed
//                else if(optPzaCfr.isSelected())
//                    strSQL+=", ne_tipNotPed='4'"; //ne_tipNotPed
                
//                if(txtCodTipDoc.getText().equals("14")){//INIMPO
//                    strSQL+=", co_exp=" + objUti.codificar(txtCodExp.getText()) + "";
//                }
//                else{//COMPRAS LOCALES
//                    strSQL+=", co_cli=" + txtCodExp.getText() + "";//
//                    strSQL+=", tx_ruc=" + objUti.codificar(txtNomExp2.getText()) + "";//
//                    strSQL+=", tx_nomCli=" + objUti.codificar(txtNomExp.getText()) + "";//
//                }
                
                //
                strSQL+=", co_empRelPedEmbImp=" + strCodEmpPedEmb + "";
                strSQL+=", co_locRelPedEmbImp=" + strCodLocPedEmb + "";
                strSQL+=", co_tipDocRelPedEmbImp=" + strCodTipDocPedEmb + "";
                strSQL+=", co_docRelPedEmbImp=" + strCodDocPedEmb + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP); //co_emp
                strSQL+=" AND co_loc= " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC); //co_loc
                strSQL+=" AND co_tipDoc= " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC); //co_tipdoc
                strSQL+=" AND co_doc= " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC); //co_doc
                strSQL+="; ";
                System.out.println("actualizar_tbmCabMovInv: " + strSQL);
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
    private boolean eliminar_tbmCarPagMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_carPagMovInv";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
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
                if(anular_tbmCabMovInv()){
                    if(anularEliminar_tbmDetMovInv()){
                        if(anularEliminar_tbmCabOrdConInv(1)){//1 = anula
//                            if(eliminar_tbmDetOrdConInv()){
//                                if(insertar_tbmDetOrdConInv()){
                                    if(objAsiDia.anularDiario(con,Integer.parseInt(txtCodImp.getText()),Integer.parseInt(strCodLocPre),Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtCodDoc.getText()))){
                                        costearItm();
                                        con.commit();
                                        blnRes=true;                            
                                    }
                                    else
                                        con.rollback();
//                                }
//                                else
//                                    con.rollback();
//                            }
//                            else
//                                con.rollback();
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
                if(!isAnulada()){
                    if(eliminar_tbmCabMovInv()){                       
                        if(anularEliminar_tbmDetMovInv()){
                            if(anularEliminar_tbmCabOrdConInv(2)){//2 = elimina                                
//                                if(eliminar_tbmDetOrdConInv()){
//                                    if(insertar_tbmDetOrdConInv()){
                                        if(objAsiDia.eliminarDiario(con,Integer.parseInt(txtCodImp.getText()),Integer.parseInt(strCodLocPre),Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtCodDoc.getText()))){
                                            costearItm();
                                            con.commit();
                                            blnRes=true;
                                        }
                                        else
                                            con.rollback();  
//                                    }
//                                    else
//                                        con.rollback();
//                                }
//                                else
//                                    con.rollback();
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
                    if(eliminar_tbmCabMovInv()){              
                        if(objAsiDia.eliminarDiario(con,Integer.parseInt(txtCodImp.getText()),Integer.parseInt(strCodLocPre),Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtCodDoc.getText()))){
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();                
                    }
                    else con.rollback();
                }

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
    private boolean anular_tbmCabMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";

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
    private boolean eliminar_tbmCabMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='E'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarRegPedEmb(String codigoEmpresa, String codigoLocal, String codigoTipoDocumento, String codigoDocumento){
        boolean blnRes=true;
        int intCodCtaBodEmp=-1;
        intTipNotPed=0;
        try{
            if (cargarCabPedEmb(codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento)){
                if (cargarCarPagImpPedEmb(codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento)){
                    if (cargarDetRegItmValPedEmb(codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento)){
                        if(optTmFob.isSelected()){
                            strTipNotPed="1";
                            intTipNotPed=1;
                        } //ne_tipnotped
                        else if(optTmCfr.isSelected()){
                            strTipNotPed="2";
                            intTipNotPed=2;
                        } //ne_tipnotped
                        else if(optPzaFob.isSelected()){
                            strTipNotPed="3";
                            intTipNotPed=3;
                        } //ne_tipnotped
                        else if(optPzaCfr.isSelected()){
                            strTipNotPed="4";
                            intTipNotPed=4;
                        } //ne_tipnotped
                        strCodExp=txtCodExp.getText();
                        objImp34_02.setBlnHayCamCamImp34_02(true);
                        objImp34_01.setBlnHayCamCamImp34_01(true);
                    }
                }
            }
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
    private boolean cargarCabPedEmb(String codigoEmpresa, String codigoLocal, String codigoTipoDocumento, String codigoDocumento){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.ne_numdoc, a1.tx_numDoc2, a1.ne_tipnotped, ";
                strSQL+="        a1.co_imp, a1.co_exp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag, ";
                strSQL+="        a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1,";
                strSQL+="        a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                strSQL+="        , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
                strSQL+="        , a5.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp";
                strSQL+="        , a1.co_ctaAct, a6.tx_codcta AS tx_numCtaAct, a6.tx_deslar AS tx_nomCtaAct";
                strSQL+="        , a1.co_ctaPas, a7.tx_codcta AS tx_numCtaPas, a7.tx_deslar AS tx_nomCtaPas";
                strSQL+="        , CASE WHEN a1.st_ciePagImp IS NULL THEN 'P' ELSE a1.st_ciePagImp END AS st_ciePagImp ";
                strSQL+=" FROM (  (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_plaCta AS a6 ON a1.co_imp=a6.co_emp AND a1.co_ctaAct=a6.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a7 ON a1.co_imp=a6.co_emp AND a1.co_ctaPas=a6.co_cta)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                strSQL+=" INNER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                strSQL+=" AND a1.co_loc=" + codigoLocal;
                strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                strSQL+=" AND a1.co_doc=" + codigoDocumento;
                strEstCiePagImp="";
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_imp");
                    txtCodImp.setText((strAux==null)?"":strAux);
                    objImp34_02.setStrCodImpPedEmbSel(strAux);
                    strAux=rst.getString("tx_nomImp");
                    txtNomImp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_exp");
                    txtCodExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomExp");
                    txtNomExp2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_aliExp");
                    strAux=rst.getObject("nd_valdoc")==null?"0":(rst.getString("nd_valdoc").equals("")?"0":rst.getString("nd_valdoc"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
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


                    strCodCtaActPedEmb=rst.getString("co_ctaAct");
                    strNumCtaActPedEmb=rst.getString("tx_numCtaAct");
                    strNomCtaActPedEmb=rst.getString("tx_nomCtaAct");
                            
                    strCodCtaPasPedEmb=rst.getString("co_ctaPas");
                    strNumCtaPasPedEmb=rst.getString("tx_numCtaPas");
                    strNomCtaPasPedEmb=rst.getString("tx_nomCtaPas");
                    
                    strEstCiePagImp = rst.getString("st_ciePagImp");
                            
                }

            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
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
    private boolean cargarCarPagImpPedEmb(String codigoEmpresa, String codigoLocal, String codigoTipoDocumento, String codigoDocumento){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        String strTipCarPag="";
        try{
            objTblModImp34_01.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carpag, a1.nd_valcarpag";
                    strSQL+="      , a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag";
                    strSQL+="      , (CASE WHEN a2.st_pagExt IS NULL THEN 'N' ELSE a2.st_pagExt END) AS st_pagExt";
                    strSQL+=" FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                    strSQL+=" ON a1.co_carpag=a2.co_carpag";
                    strSQL+=" WHERE a1.co_emp=" + codigoEmpresa + "";
                    strSQL+=" AND a1.co_loc=" + codigoLocal + "";
                    strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                    strSQL+=" AND a1.co_doc=" + codigoDocumento + "";
                    strSQL+=" ORDER BY a2.ne_ubi";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objImp34_01.INT_TBL_DAT_LIN,"");
                        vecReg.add(objImp34_01.INT_TBL_DAT_TIP_CAR_PAG_IMP,    rst.getString("tx_tipcarpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_COD_CAR_PAG_IMP,    rst.getString("co_carpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_NOM_CAR_PAG_IMP,    rst.getString("tx_nomCarPag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_VAL_CAR_PAG_IMP,    rst.getString("nd_valcarpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,rst.getString("nd_valcarpag"));
                        vecReg.add(objImp34_01.INT_TBL_DAT_TIP_NOT_PED,        "");

                        if(optTmFob.isSelected())
                            vecReg.setElementAt("1", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optTmCfr.isSelected())
                            vecReg.setElementAt("2", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaFob.isSelected())
                            vecReg.setElementAt("3", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaCfr.isSelected())
                            vecReg.setElementAt("4", objImp34_01.INT_TBL_DAT_TIP_NOT_PED);


                        strTipCarPag=rst.getObject("tx_tipcarpag")==null?"":rst.getString("tx_tipcarpag");
                        if(strTipCarPag.equals("F")){
                            bgdValFleSav=bgdValFleSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        else if(strTipCarPag.equals("A")){
                            bgdValAraSav=bgdValAraSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        else if(strTipCarPag.equals("G")){
                            bgdValGasSav=bgdValGasSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        
                        vecReg.add(objImp34_01.INT_TBL_DAT_TIP_PAG_EXT,rst.getString("st_pagExt"));
                        vecDat.add(vecReg);
                    }

                    objImp34_01.setFleteItem(bgdValFleSav);
                    objImp34_01.setDerechosArancelarios(bgdValAraSav);
                    objImp34_01.setTotalGastos(bgdValGasSav);

                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objImp34_01.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
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

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegItmValPedEmb(String codigoEmpresa, String codigoLocal, String codigoTipoDocumento, String codigoDocumento){
        boolean blnRes=true;
        try{
            objTblModImp34_02.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT x.co_itmMae, x.co_itm, x.tx_codAlt, x.tx_codAlt2, x.tx_nomItm, x.nd_pesitmkgr";
                    strSQL+="      , x.co_emp, x.co_locrel, x.co_tipdocrel, x.co_docrel, x.co_regrel";
                    strSQL+="      , x.co_parara, x.tx_par, x.tx_des, x.nd_ara, x.nd_can, x.nd_preUni";
                    strSQL+="      , x.nd_valTotFobCfr, x.nd_canTonMet, x.nd_valFle, x.nd_valCfr, x.nd_valTotAra";
                    strSQL+="      , x.nd_valTotGas, x.nd_valTotCos, x.nd_cosUni, x.co_reg";
                    strSQL+="      , x.co_loc, x.co_tipDoc, x.co_doc, x.tx_desCorUniMed, x.co_Exp, x.tx_nomExp, y.co_itmEmp, x.nd_cosKgr";
                    strSQL+=" FROM(";
                    strSQL+="       SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+="            , b1.co_emp, b1.co_locrel, b1.co_tipdocrel, b1.co_docrel, b1.co_regrel";
                    strSQL+="            , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara, CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                    strSQL+="            , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                    strSQL+="            , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                    strSQL+="            , b1.nd_can";
                    strSQL+="            , b1.nd_preUni, b1.nd_valTotFobCfr";
                    strSQL+="            , b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra";
                    strSQL+="            , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                    strSQL+="            , b1.co_loc, b1.co_tipDoc, b1.co_doc, a2.tx_desCor AS tx_desCorUniMed, b1.co_Exp, b3.tx_nom as tx_nomExp";
                    strSQL+="            , CASE WHEN a1.nd_pesitmkgr > 0 THEN ROUND((b1.nd_cosUni/a1.nd_pesitmkgr),2) ELSE 0 END as nd_cosKgr";
                    strSQL+="       FROM tbm_detPedEmbImp AS b1 INNER JOIN";
                    strSQL+="       (";
                    strSQL+="           ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                    strSQL+="           LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                    strSQL+="           )";
                    strSQL+="           LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                    strSQL+="                           INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                    strSQL+="           ) ON a1.co_grpImp=a4.co_grp";
                    strSQL+="       )";
                    strSQL+="       ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+="       INNER JOIN tbm_expImp AS b3 ON(b1.co_exp=b3.co_exp)";
                    strSQL+="       WHERE b1.co_emp=" + codigoEmpresa + "";
                    strSQL+="       AND b1.co_loc=" + codigoLocal + "";
                    strSQL+="       AND b1.co_tipDoc=" + codigoTipoDocumento + "";
                    strSQL+="       AND b1.co_doc=" + codigoDocumento + "";
                    strSQL+="       GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="              , b1.co_emp, b1.co_locrel, b1.co_tipdocrel, b1.co_docrel, b1.co_regrel";
                    strSQL+="              , a5.co_parara, a5.tx_par, a5.tx_des, b1.nd_ara";
                    strSQL+="              , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                    strSQL+="              , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                    strSQL+="              , b1.co_loc, b1.co_tipDoc, b1.co_doc, a2.tx_desCor, b1.co_Exp, b3.tx_nom";
                    strSQL+="       ORDER BY b1.co_reg, a1.tx_codAlt";
                    strSQL+=" ) AS x";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	    SELECT a1.co_itm AS co_itmEmp , a3.co_itmMae";
                    strSQL+=" 	    FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+=" 	    WHERE a1.co_emp=" + txtCodImp.getText() + "";
                    strSQL+=" ) AS y";
                    strSQL+=" ON x.co_itmMae=y.co_itmMae";
                    strSQL+=" ORDER BY x.co_reg, x.tx_codAlt";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objImp34_02.INT_TBL_DAT_LIN,"");
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_MAE,    rst.getString("co_itmMae"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM,        rst.getString("co_itm"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codAlt"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_LET_ITM,    rst.getString("tx_codAlt2"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_BUT_ITM,        null);
                        vecReg.add(objImp34_02.INT_TBL_DAT_NOM_ITM,        rst.getString("tx_nomItm"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_UNI_MED,        rst.getString("tx_desCorUniMed"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_PES,            rst.getString("nd_pesitmkgr"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ARA,        rst.getString("co_parara"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_NOM_ARA,        rst.getString("tx_par"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_DES_ARA,        rst.getString("tx_des"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_POR_ARA,        rst.getString("nd_ara"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_EXP,        rst.getString("co_Exp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_NOM_EXP,        rst.getString("tx_nomExp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_BUT_EXP,        null);                        
                        
                        if(  (optTmFob.isSelected())  ||  (optTmCfr.isSelected()) )
                            vecReg.add(objImp34_02.INT_TBL_DAT_CAN_TON_MET,    rst.getString("nd_canTonMet"));
                        else
                            vecReg.add(objImp34_02.INT_TBL_DAT_CAN_TON_MET,    rst.getString("nd_can"));
                        
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_PZA,    rst.getString("nd_can"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_PRE_UNI,        rst.getString("nd_preUni"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_FOB_CFR,    rst.getString("nd_valTotFobCfr"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_VAL_FLE,        rst.getString("nd_valFle"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_VAL_CFR,        rst.getString("nd_valCfr"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_VAL_CFR_ARA,    "0");
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_ARA,        rst.getString("nd_valTotAra"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_GAS,        rst.getString("nd_valTotGas"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_TOT_COS,        rst.getString("nd_valTotCos"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COS_UNI,        rst.getString("nd_cosUni"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_COS_KGR,        rst.getString("nd_cosKgr")); 
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_EMP,    rst.getString("co_itmEmp")); 
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_EMP_ACT,rst.getString("co_itmEmp"));//no va el campo "co_itmAct" porque en tablas de pedidos no existe, solo en tbm_detMovInv, entonces uso el mismo co_itm
                        vecReg.add(objImp34_02.INT_TBL_DAT_COD_ITM_EMP_AUX,rst.getString("co_itmEmp"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_PED_EMB,    rst.getString("nd_can"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_PEN_PED_EMB,rst.getString("nd_can"));
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_UTI_ING_IMP,"0");
                        vecReg.add(objImp34_02.INT_TBL_DAT_CAN_ING_IMP_AUX,"0");
                        vecReg.add(objImp34_02.INT_TBL_DAT_REV_ITM,        "");
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objImp34_02.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                    }
                    objImp34_02.regenerarCalculoCfrAra();
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
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo(){
        strAux="";
        strAux+="" + txtDesCorTipDoc.getText();
        strAux+=" # " + txtNumDoc.getText();
        strAux+="; Importador: " + txtNomImp.getText();
        strAux+="; Exportador: " + txtNomExp2.getText();
        strAux+="; Bodega: " + txtNomBod.getText();
        objAsiDia.setGlosa(strAux);
    }

    private boolean verificaItmUni(){
       boolean  blnRes=true;
       try{
           for(int i=0; i<objTblModImp34_02.getRowCountTrue();i++){
               if(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) != null){
                   int intCodItm    = Integer.parseInt((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString()));
                   int intCodItmAct = Integer.parseInt((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP_ACT).toString()));
                   if(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_LIN).equals("M")){
                       if(intCodItm!=intCodItmAct){
                           mostrarMsgInf("El Item "+objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM).toString()+ " esta Unificado \n No es posible realizar cambio." );
                           blnRes=false;
                           break;
                       }
                   }
               }
           }
      }
      catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
      return blnRes;
    }

    public boolean eliminar_registrosEliminados()
    {
        boolean blnRes=true;
        int intReaActStk=0;
        int intCodItm=0;
        double dblCan=0;
        String strEstFisBod="";
        java.util.ArrayList arlAux;
        try{
            arlAux=objTblModImp34_02.getDataSavedBeforeRemoveRow();
            if(arlAux!=null)
            {
                arlDatStkInvItm = new ArrayList(); 
                
                for(int i=0;i<arlDatFilEliImp34_03.size();i++)
                {
                    intCodItm = objUti.getIntValueAt(arlDatFilEliImp34_03, i, objImp34_02.INT_CFE_COD_ITM_EMP);
                    dblCan = objUti.getDoubleValueAt(arlDatFilEliImp34_03, i, objImp34_02.INT_CFE_CAN_ING_IMP_AUX);
                    
                    if(!generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()), intCodItm, dblCan ,getCodigoBodegaEmpresa())){
                        blnRes=false;
                    }
                    intReaActStk=1;
                }
                if(intReaActStk==1)
                {
                     if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
                         if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "-", 0, arlDatStkInvItm)){
                            //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                                    arlDatStkInvItm.clear();
                            //}else{blnRes=false;}
                         }else{blnRes=false;}
                    }else{blnRes=false;}
                }
            }
            blnRes=true;
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public boolean anular_detalleModeloTabla()
    {
        boolean blnRes=false;
        int intReaActStk=0;
        int intCodItmOri=0;
        int intCodBodOri=0;
        int intCodItm2=0;
        int intCodBod2=0;
        double dblCanOri=0;
        double dblCan=0;
        String strNomItem="";
        try
        {
            for(int i=0; i<objTblModImp34_02.getRowCountTrue();i++)
            {
                if(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) != null){

                    intCodItmOri=Integer.parseInt(((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP_AUX)==null)?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP_AUX).toString()));
                    intCodBodOri=getCodigoBodegaEmpresa();
                    dblCanOri=Double.parseDouble(((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_ING_IMP_AUX)==null)?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_ING_IMP_AUX).toString()));
                    intCodItm2 =   Integer.parseInt((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString()));
                    intCodBod2 =   intCodBodOri;
                    strNomItem =  ((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM)==null || objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM).toString().equals(""))?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM).toString());

                    dblCan=Double.parseDouble((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA)==null)?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString()));

                    //if(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_LIN).equals("M")){
                    
                    arlDatStkInvItm = new ArrayList(); 

                    if(intCodItmOri!=intCodItm2)  //SE HA CAMBIADO EL ITEM EN ESA FILA
                    { 
                        if(generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),intCodItmOri,dblCanOri,intCodBodOri))
                        {    
                            if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
                                if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "-", 0, arlDatStkInvItm)){
                                    //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                                        arlDatStkInvItm.clear();
                                    //}else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}

                        if(generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),intCodItm2,dblCan,intCodBod2))
                        {    
                            if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
                                if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "+", 0, arlDatStkInvItm)){
                                    //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm)){
                                        arlDatStkInvItm.clear();
                                    //}else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}

                    }
                    else if(intCodBodOri!=intCodBod2)
                    {
                        if(generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),intCodItmOri,dblCanOri,intCodBodOri))
                        {    
                            if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
                                if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "-", 0, arlDatStkInvItm)){
                                    //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                                        arlDatStkInvItm.clear();
                                    //}else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}

                        if(generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),intCodItm2,dblCan,intCodBod2))
                        {    
                            if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
                                if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "+", 0, arlDatStkInvItm)){
                                    //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm)){
                                        arlDatStkInvItm.clear();
                                    //}else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}

                    }
                    else if(dblCanOri!=dblCan)
                    {
                        if(dblCan > dblCanOri)
                        {
                            double dblCanDif = dblCan-dblCanOri;
                            dblCanDif=Math.abs(dblCanDif);
                            if(generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),intCodItm2,dblCanDif,intCodBod2))
                            {    
                                if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
                                    if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "+", 0, arlDatStkInvItm)){
                                        //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm)){
                                           arlDatStkInvItm.clear();
                                        //}else{blnRes=false;}
                                    }else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}

                        }
                        if(dblCan < dblCanOri)
                        {
                            double dblCanDif = dblCanOri-dblCan;
                            dblCanDif=Math.abs(dblCanDif);
                            if(generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),intCodItm2,dblCanDif,intCodBod2))
                            {    
                                if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
                                    if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "-", 0, arlDatStkInvItm)){
                                        //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        //}else{blnRes=false;}
                                    }else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }
                    }
                  //}
                }
            }
            blnRes=true;
        }
        catch (Exception e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite costear los items de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean costearItm()
    {
        int i;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                switch (objTooBar.getEstado())
                {                
                    case 'n':
                        System.out.println("costearDocumento");
                        objUti.costearDocumento(this, objParSis, con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        break;
                    case 'm':
                        //Validar la fecha  del documento sólo si se encuentra en el modo "Modificar".
                        //Validar si se utiliza la fecha que tenia el documento inicialmente o la que tiene actualmente.
                        if (datFecDoc.compareTo(objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy"))>0){
                            for (i=0;i<objTblModImp34_02.getRowCountTrue();i++){
                                System.out.println("recostearItm");
                                //recostea la empresa
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, Integer.parseInt(txtCodImp.getText()), objTblModImp34_02.getValueAt(i,objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                                //recostea el grupo
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblModImp34_02.getValueAt(i,objImp34_02.INT_TBL_DAT_COD_ITM).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            }
                        }
                        else{
                            for (i=0;i<objTblModImp34_02.getRowCountTrue();i++){
                                System.out.println("recostearItm");
                                //recostea la empresa
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, Integer.parseInt(txtCodImp.getText()), objTblModImp34_02.getValueAt(i,objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                                //recostea el grupo
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblModImp34_02.getValueAt(i,objImp34_02.INT_TBL_DAT_COD_ITM).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                            }
                        }
                        break;
                    default:
                        for (i=0;i<objTblModImp34_02.getRowCountTrue();i++) {
                            System.out.println("recostearItm");
                            //recostea la empresa
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con, Integer.parseInt(txtCodImp.getText()), objTblModImp34_02.getValueAt(i,objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            //recostea el grupo
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblModImp34_02.getValueAt(i,objImp34_02.INT_TBL_DAT_COD_ITM).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                        }
                        break;
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

    private boolean verificaItmUniAnu(){
        boolean  blnRes=true;
        try{
            for(int i=0; i<objTblModImp34_02.getRowCountTrue();i++){
                if(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) != null){
                    int intCodItm    = Integer.parseInt((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString()));
                    int intCodItmAct = Integer.parseInt((objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP_ACT).toString()));
                    if(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_LIN).equals("M")){
                        if(intCodItm != intCodItmAct){
                            mostrarMsgInf("El Item "+objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM).toString()+ " esta Unificado \n No es posible realizar cambio." );
                            blnRes=false;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean anularEliminar_tbmDetMovInv()
    {
        boolean blnRes=false;
        int intReaActStk=0;
        int intCodItm=0;
        int intCodBod=0;
        double dblCan=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                arlDatStkInvItm = new ArrayList(); 
                //Armar sentencia SQL obtiene items del ingreso por importación.
                strSQL ="";
                strSQL+=" SELECT a.co_itmact, a.co_bod, abs(a.nd_can) as ndcan ";
                strSQL+=" FROM tbm_detMovInv AS a " ;
                strSQL+=" INNER JOIN tbm_inv AS a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) ";
                strSQL+=" WHERE a.co_emp="+objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a.co_loc="+objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a.co_doc="+objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a.co_tipDoc="+objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.st_ser='N' ";
                rst=stm.executeQuery(strSQL);
                while(rst.next())
                {
                    intCodItm =rst.getInt("co_itmact");
                    intCodBod =rst.getInt("co_bod");
                    dblCan=rst.getDouble("ndcan");
                    if (!generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()), intCodItm, dblCan, intCodBod)) {
                        blnRes = false;
                    }
                    intReaActStk=1;
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;

                if(intReaActStk==1)
                {
                    if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
                        //Se agrega cantidad por ingresar en Importaciones.
                        if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_ING_BOD, "-", 0, arlDatStkInvItm)){
                            //Se comenta para que no mueva cantidades disponibles de Ingresos por Importación, ya que se manejará disponibles en conteos de inventario(importaciones).
                            //if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm)){
                                arlDatStkInvItm.clear();
                            //}else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{blnRes=false;}  
                }
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }

    /**
     * Esta función muestra el número del documento asociado al tipo de documento según Importador(Empresa)
     * @return true: Si se pudo mostrar el número del documento.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarNumDocEmpTipDoc(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + txtCodImp.getText();
                strSQL+=" AND a1.co_loc=" + strCodLocPre;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
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

    public boolean isDocumentoConfirmado(){
        boolean blnRes=true;
        java.sql.Connection conn;
        java.sql.Statement stm2, stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conn!=null){
                stm2 = conn.createStatement();
                stmLoc = conn.createStatement();
                strSQL = " select st_coninv, st_autingegrinvcon FROM  tbm_cabMovInv where co_emp=" + txtCodImp.getText() + "  "+
                         " and co_loc=" + strCodLocPre + " and co_doc=" + txtCodDoc.getText() + " and co_tipDoc=" + txtCodTipDoc.getText();
                rst = stm2.executeQuery(strSQL);
                if(rst.next()){
                    if( rst.getString("st_coninv")==null ){
                        strSQL=" SELECT distinct(a1.st_coninv) as stconinv FROM  tbm_detguirem as a " +
                               " inner join tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                               " where a.co_emprel="+txtCodImp.getText()+" and a.co_locrel="+strCodLocPre+" and  a.co_docrel="+txtCodDoc.getText()+"  and  a.co_tipDocrel="+txtCodTipDoc.getText();
                        rstLoc = stmLoc.executeQuery(strSQL);
                        if(rstLoc.next()){
                            if( (rstLoc.getString("stconinv").equals("E")) || (rstLoc.getString("stconinv").equals("C")) ){
                                String srt="El documento está CONFIRMADO o en proceso de confirmación. \n"+
                                           " No es posible anular un documento que está confirmado o en proceso de confirmación. \n"+
                                           " Comuníquese con la persona que realiza las confirmaciones para poder anular el documento.";
                                mostrarMsgInf( srt );
                                blnRes=false;
                            }
                        }
                        rstLoc.close();
                        rstLoc=null;
                    }
                    else{
                        if( (rst.getString("st_coninv").equals("D")) || (rst.getString("st_coninv").equals("C"))  || (rst.getString("st_coninv").equals("E")) ){
                            if(rst.getString("st_autingegrinvcon").equals("N")){
                                String srt="El documento está CONFIRMADO o en proceso de confirmación. \n"+
                                " No es posible anular un documento que está confirmado o en proceso de confirmación. \n"+
                                " Comuníquese con la persona que realiza las confirmaciones para poder anular el documento.";
                                mostrarMsgInf( srt );
                                blnRes=false;
                            }
                        }
                    }
                }
                rst.close();
                rst=null;
                stm2.close();
                stm2=null;
                stmLoc.close();
                stmLoc=null;

                conn.close();
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que valida que el pedido no haya sido arribado para poder proceder con la anulación.
     * @return true: Si el pedido NO ha sido arribado.
     * <BR> false: Si el pedido SI ha sido arribado.
     */
    public boolean isPedidoArribado(){
        boolean blnRes=true;
        Connection conLoc;
        Statement stmLoc;
        try{
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSQL =" ";
                strSQL+=" SELECT st_ingImp FROM tbm_cabMovInv";
                strSQL+=" WHERE co_emp=" + txtCodImp.getText();
                strSQL+=" AND co_loc=" + strCodLocPre;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText() ;
                strSQL+=" AND (CASE WHEN st_ingImp IS NULL THEN 'N' ELSE st_ingImp END ) NOT IN ('P')" ;
                rst = stmLoc.executeQuery(strSQL);
                if(rst.next()){
                    String srt=" El Pedido ha sido ARRIBADO. \n"+
                               " No es posible anular un pedido que ha sido arribado. \n";
                    mostrarMsgInf( srt );
                    blnRes=false;
                }
                rst.close();
                rst=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean isAnulada(){
        boolean blnRes = false;
        java.sql.Connection conTmp ;
        java.sql.Statement stmTmp;
        try{
            conTmp = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+=" Select count(*) from tbm_cabmovinv ";
                strSQL+=" where  co_emp = " + Integer.parseInt(txtCodImp.getText()) + " and  co_loc = " + Integer.parseInt(strCodLocPre)   + " and  co_tipDoc = " + txtCodTipDoc.getText() + " and  co_doc = " +  txtCodDoc.getText() + " and st_reg = 'I' " ;
                if (objUti.getNumeroRegistro(this,objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),strSQL)>0){
                    blnRes = true;
                }
                else
                    blnRes= false;
                stmTmp.close();
                conTmp.close();
            }
        }
        catch(java.sql.SQLException Evt){
            objUti.mostrarMsgErr_F1(this, Evt);
            blnRes = true;
        }
        catch(Exception Evt)  {
            objUti.mostrarMsgErr_F1(this, Evt);
            blnRes = true;
        }
        return blnRes;
    }
    
    /**Esta función permite generar una Orden de Conteo asociada a este Ingreso por Importación, 
     * Nota: Sólo ingresa la cabecera de la Orden de Conteo
     * @return true: Si se pudo ingresar la información 
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabOrdConInv(){
        boolean blnRes=true;
        int intNumDocOrdConImp;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) AS co_doc";
                strSQL+=" FROM tbm_cabordconinv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_CON;
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodDocOrdConImp=rst.getObject("co_doc")==null?0:rst.getInt("co_doc");
                else
                    intCodDocOrdConImp=0;
                intCodDocOrdConImp++;
                
                strSQL="";
                strSQL+="SELECT a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_CON;
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumDocOrdConImp=rst.getObject("ne_ultDoc")==null?0:rst.getInt("ne_ultDoc");
                else
                    intNumDocOrdConImp=0;
                intNumDocOrdConImp++;
                
                strSQL ="";
                strSQL+=" INSERT INTO tbm_cabordconinv(";
                strSQL+="  co_emp, co_loc, co_tipdoc,co_doc, fe_doc, ne_numdoc";
                strSQL+=" , co_usrrescon, co_bod, tx_obs1, tx_obs2, st_reg, fe_ing";
                strSQL+=" , fe_ultmod, co_usring,co_usrmod, st_regrep";
                strSQL+=" , co_empRel, co_locRel, co_tipDocRel, co_docRel)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                strSQL+="" + objParSis.getCodigoLocal() + ",";  //co_loc
                strSQL+="" + INT_COD_TIP_DOC_ORD_CON + ",";     //co_tipdoc
                strSQL+="" + intCodDocOrdConImp + ",";          //co_doc
                strSQL+="'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL+="" + intNumDocOrdConImp + ",";//ne_numdoc
                strSQL+="Null,";//co_usrrescon
                strSQL+="" + txtCodBod.getText() + ",";//co_bod
                strSQL+="Null,";//tx_obs1
                strSQL+="Null,";//tx_obs2
                strSQL+="'A',"; //st_reg
                strSQL+="CURRENT_TIMESTAMP,";//fe_ing
                strSQL+="CURRENT_TIMESTAMP,";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL+="'I',";//st_regrep
                strSQL+="" + txtCodImp.getText() + ",";   //co_empRel
                strSQL+="" + strCodLocPre + ",";          //co_locRel
                strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipDocRel
                strSQL+="" + txtCodDoc.getText() + "";    //co_docRel
                strSQL+=");";
                //System.out.println("insertar_tbmCabOrdConInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta función permite anular/eliminar la cabecera del registro de la Orden de Conteo.
     * @param tipoOperacion: Si es 1 la operación que se realiza es anulación, 2 si es eliminación
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularEliminar_tbmCabOrdConInv(int tipoOperacion){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabOrdConInv";
                strSQL+=" SET ";
                if(tipoOperacion==1)
                    strSQL+=" st_reg='I',";
                else if(tipoOperacion==2)
                    strSQL+=" st_reg='E',";
                strSQL+=" fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_empRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_locRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDocRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_docRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC);
                strSQL+=" ; ";
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
   
    private boolean insertar_tbmDetOrdConInv(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=0;
        int intUltReg=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_reg) AS co_reg";
                strSQL+=" FROM tbm_coninv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltReg=rst.getObject("co_reg")==null?0:rst.getInt("co_reg");
                
                for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                    j++;
                    intUltReg++;
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_detordconinv(";
                    strSQL+=" co_emp, co_loc, co_tipdoc,";
                    strSQL+=" co_doc, co_reg, co_itm, st_regrep";
                    strSQL+=" )";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                    strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                    strSQL+="" + INT_COD_TIP_DOC_ORD_CON + ",";//co_tipdoc
                    strSQL+="" + intCodDocOrdConImp + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + ",";//co_itm
                    strSQL+="'I'";//st_regrep
                    strSQL+=");";
                    strSQL+="INSERT INTO tbm_coninv(";
                    strSQL+=" co_emp, co_reg, co_usrrescon,";
                    strSQL+=" co_bod, co_itm, fe_solcon,";
                    strSQL+=" tx_obs1, st_regrep, co_locRel, co_tipDocRel, co_docRel, co_regRel";                    
                    strSQL+=")";                    
                    strSQL+="VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                    strSQL+="" + intUltReg + ",";//co_reg
                    strSQL+="Null,";//co_usrrescon;
                    strSQL+="" + txtCodBod.getText() + ",";//co_bod;
                    strSQL+="" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + ",";//co_itm;
                    strSQL+="CURRENT_TIMESTAMP,";//fe_solcon;
                    strSQL+=" Null,";//tx_obs1;
                    strSQL+="'I',";//st_regrep;
                    strSQL+="" + objParSis.getCodigoLocal() + ",";//co_locRel
                    strSQL+="" + INT_COD_TIP_DOC_ORD_CON + ",";//co_tipDocRel
                    strSQL+="" + intCodDocOrdConImp + ",";//co_docRel
                    strSQL+="" + j + "";//co_regRel            
                    strSQL+=");";
                    //System.out.println("insertar_tbmDetOrdConInv: " + strSQL);
                    strSQLIns+=strSQL;                    
                }                
                //System.out.println("insertar_tbmDetOrdConInv.strSQLIns: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
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
    private boolean actualizar_tbmDetMovInv(){
        boolean blnRes=true;
        String strSQLIns="";
        int intMaxCodReg_detOrdCon=0;
        int intCodBodEmp=-1;
        boolean blnCreItmOrdConInv;
        boolean blnCreItmOrdDisInm;
        int intUltReg_ConInv=0;
        int intMaxCodReg_detOrdCon_Inc=0;
        int intUltReg_ConInv_Inc=0;
        try{
            if (con!=null){
                intCodBodEmp=getCodigoBodegaEmpresa();
                arlDatCodItmMaeConInv.clear();
                stm=con.createStatement();

                arlDatStkInvItm = new ArrayList(); 
                
                //Obtener el maximo registro de tbm_detOrdConInv
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_reg) AS co_reg";
                strSQL+=" FROM tbm_cabOrdConInv AS a2 INNER JOIN tbm_detOrdConInv AS a1";
                strSQL+=" ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc";
                strSQL+=" WHERE a2.co_empRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a2.co_locRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a2.co_tipDocRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a2.co_docRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intMaxCodReg_detOrdCon_Inc=rst.getInt("co_reg");
                }
                intMaxCodReg_detOrdCon_Inc++;
                rst.close();
                rst=null;

                //Obtener el maximo registro de tbm_conInv
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_reg) AS co_reg";
                strSQL+=" FROM tbm_conInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltReg_ConInv_Inc=rst.getInt("co_reg");
                intUltReg_ConInv_Inc++;
                rst.close();
                rst=null;

                //para obtener el codigo del documento de la Orden de conteo
                strSQL="";
                strSQL+=" SELECT a1.co_doc";
                strSQL+=" FROM tbm_cabOrdConInv AS a2 INNER JOIN tbm_detOrdConInv AS a1";
                strSQL+=" ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc";
                strSQL+=" WHERE a2.co_empRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a2.co_locRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a2.co_tipDocRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a2.co_docRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC);
                strSQL+=" GROUP BY a1.co_doc";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodDocOrdConImp=rst.getInt("co_doc");
                rst.close();
                rst=null;

                for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                    
                    blnCreItmOrdConInv=false;
                    blnCreItmOrdDisInm=false;
                    
                    //si no existe en tbm_detMovInv se busca en tbm_detOrdConInv y se toma ese codigoRegistro
                    if( !(isExisteItem_detMovInv( Integer.parseInt(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString())     ))   ){//item empresa
                        //System.out.println("NO EXISTE EN DETALLE");
                        //si no existe en tbm_detOrdConInv y se toma el maximo co_reg de tbm_detOrdConInv, NOOOOO!!!! de tbm_detMovInv
                        if(isExisteItem_detOrdConInv( Integer.parseInt(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM).toString())     ))  {//item grupo
                            //obtener el codigo del registro de tbm_detOrdConInv
                            intMaxCodReg_detOrdCon=intCodRegOrdConImp;
                            //System.out.println("SI-intMaxCodReg_detOrdCon: " + intMaxCodReg_detOrdCon);
                            //agregar en la Orden de distribucion de Inmaconsa
                            if(isExisteItem_detOrdDis())
                                blnCreItmOrdDisInm=true;
                        }
                        else{
                            intMaxCodReg_detOrdCon=intMaxCodReg_detOrdCon_Inc;
                            //System.out.println("NO-intMaxCodReg_detOrdCon: " + intMaxCodReg_detOrdCon);
                            intMaxCodReg_detOrdCon_Inc++;

                            intUltReg_ConInv=intUltReg_ConInv_Inc;
                            intUltReg_ConInv_Inc++;
                                                        
                            //agregar en la Orden de conteo
                            blnCreItmOrdConInv=true;
                            //agregar en la Orden de distribucion de Inmaconsa
                            if(isExisteItem_detOrdDis())
                                blnCreItmOrdDisInm=true;

                        }
                    }
                    else{
                        intMaxCodReg_detOrdCon=intMaxCodReg_detOrdCon_Inc;
                        intMaxCodReg_detOrdCon_Inc++;

                        intUltReg_ConInv=intUltReg_ConInv_Inc;
                        intUltReg_ConInv_Inc++;

                        //agregar en la Orden de conteo
                        blnCreItmOrdConInv=true;
                        //agregar en la Orden de distribucion de Inmaconsa
                        if(isExisteItem_detOrdDis())
                            blnCreItmOrdDisInm=true;                

                    }
                    
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" UPDATE tbm_detMovInv";
                    strSQL+=" SET nd_can=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA) + "";
                    strSQL+=", nd_cosUni=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                    strSQL+=", nd_tot=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_cosTot=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                    strSQL+=", nd_cosUniGrp=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                    strSQL+=", nd_cosTotGrp=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                    strSQL+=", nd_ara=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_preUniImp=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_valTotFobCfr=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_cantonmet=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_valFle=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_valCfr=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_valTotAra=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=", nd_valTotGas=" + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS).toString()), objParSis.getDecimalesBaseDatos()) + "";
                    strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";                    
                    strSQL+=" AND co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) + "";
                    strSQL+=" AND EXISTS(";
                    strSQL+="           SELECT *FROM tbm_detMovInv";
                    strSQL+="           WHERE co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                    strSQL+="           AND co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                    strSQL+="           AND co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+="           AND co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                    strSQL+="           AND co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) + "";
                    strSQL+="            )";
                    strSQL+=";";

                    strSQL+="INSERT INTO tbm_detMovInv(";
                    strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,co_reg,ne_numFil,co_itm,co_itmAct,tx_codAlt,tx_codAlt2,";
                    strSQL+="        tx_nomItm,tx_uniMed,co_bod,tx_nomBodOrgDes,nd_can,nd_canOrg,nd_canDev,nd_cosUni,nd_preUniVenLis,";
                    strSQL+="        nd_porDesVenMax,nd_preUni,nd_porDes,st_ivaCom,nd_tot,nd_cosTot,nd_exi,nd_valExi,nd_cosPro,";
                    strSQL+="        nd_cosUniGrp,nd_cosTotGrp,nd_exiGrp,nd_valExiGrp,nd_cosProGrp,st_merIngEgrFisBod,nd_canCon,";
                    strSQL+="        nd_canNunRec,nd_canTotNunRecPro,nd_canTotMalEst,nd_canTotMalEstPro,tx_obs1,co_usrCon,";
                    strSQL+="        co_locRelSolDevVen,co_tipDocRelSolDevVen,co_docRelSolDevVen,co_regRelSolDevVen,co_locRelSolSalTemMer,";
                    strSQL+="        co_tipDocRelSolSalTemMer,co_docRelSolSalTemMer,co_regRelSolSalTemMer,st_cliRetEmpRel,tx_obsCliRetEmpRel,";
                    strSQL+="        nd_ara,nd_preUniImp,nd_valTotFobCfr,nd_cantonmet,nd_valFle,nd_valCfr,nd_valTotAra,nd_valTotGas,st_regRep)";
                    strSQL+=" (";
                    strSQL+=" SELECT ";
                    strSQL+="  " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + " AS co_emp"; //co_emp
                    strSQL+=", " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + " AS co_loc"; //co_loc
                    strSQL+=", " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + " AS co_tipdoc"; //co_tipdoc
                    strSQL+=", " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + " AS co_doc"; //co_doc
                    strSQL+=", " + intMaxCodReg_detOrdCon + " AS co_reg"; //co_reg
                    //System.out.println("**--intMaxCodReg_detOrdCon: " + intMaxCodReg_detOrdCon);
                    strSQL+=", Null AS ne_numFil";//ne_numFil
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) + " AS co_itm";//co_itm
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) + " AS co_itmAct";//co_itmAct
                    strSQL+=", '" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM) + "' AS tx_codAlt";//tx_codAlt
                    strSQL+=", '" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_LET_ITM) + "' AS tx_codAlt2";//tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM)) + " AS tx_nomItm";//tx_nomItm
                    strSQL+=", '" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_UNI_MED) + "' AS tx_uniMed";//tx_uniMed
                    strSQL+=", " + intCodBodEmp + " AS co_bod";//co_bod
                    strSQL+=", '" + txtNomBod.getText() + "' AS tx_nomBodOrgDes";//tx_nomBodOrgDes
                    //strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA) + " AS nd_can";//nd_can
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA) + " AS nd_can";//nd_can
                    strSQL+=", Null AS nd_canOrg";//nd_canOrg
                    strSQL+=", 0 AS nd_canDev";//nd_canDev
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + " AS nd_cosUni";//nd_cosUni
                    strSQL+=", Null AS nd_preUniVenLis";//nd_preUniVenLis
                    strSQL+=", Null AS nd_porDesVenMax";//nd_porDesVenMax
                    strSQL+=", Null AS nd_preUni";//nd_preUni
                    strSQL+=", 0 AS nd_porDes";//nd_porDes
                    strSQL+=", 'N' AS st_ivaCom";//st_ivaCom
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + " AS nd_tot";//nd_tot 'precio al publico'
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + " AS nd_cosTot";//nd_cosTot 'precio de compra para vender'
                    strSQL+=", 0 AS nd_exi";//nd_exi
                    strSQL+=", 0 AS nd_valExi";//nd_valExi
                    strSQL+=", 0 AS nd_cosPro";//nd_cosPro
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + " AS nd_cosUniGrp";//nd_cosUniGrp
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_COS).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + " AS nd_cosTotGrp";//nd_cosTotGrp
                    strSQL+=", 0 AS nd_exiGrp";//nd_exiGrp
                    strSQL+=", 0 AS nd_valExiGrp";//nd_valExiGrp
                    strSQL+=", 0 AS nd_cosProGrp";//nd_cosProGrp
                    strSQL+=", 'S' AS st_merIngEgrFisBod";//st_merIngEgrFisBod
                    strSQL+=", 0 AS nd_canCon";//nd_canCon
                    strSQL+=", 0 AS nd_canNunRec";//nd_canNunRec
                    strSQL+=", Null AS nd_canTotNunRecPro";//nd_canTotNunRecPro
                    strSQL+=", Null AS nd_canTotMalEst";//nd_canTotMalEst
                    strSQL+=", Null AS nd_canTotMalEstPro";//nd_canTotMalEstPro
                    strSQL+=", Null AS tx_obs1";//tx_obs1
                    strSQL+=", Null AS co_usrCon";//co_usrCon
                    strSQL+=", Null AS co_locRelSolDevVen";//co_locRelSolDevVen
                    strSQL+=", Null AS co_tipDocRelSolDevVen";//co_tipDocRelSolDevVen
                    strSQL+=", Null AS co_docRelSolDevVen";//co_docRelSolDevVen
                    strSQL+=", Null AS co_regRelSolDevVen";//co_regRelSolDevVen
                    strSQL+=", Null AS co_locRelSolSalTemMer";//co_locRelSolSalTemMer
                    strSQL+=", Null AS co_tipDocRelSolSalTemMer";//co_tipDocRelSolSalTemMer
                    strSQL+=", Null AS co_docRelSolSalTemMer";//co_docRelSolSalTemMer
                    strSQL+=", Null AS co_regRelSolSalTemMer";//co_regRelSolSalTemMer
                    strSQL+=", Null AS st_cliRetEmpRel";//st_cliRetEmpRel
                    strSQL+=", Null AS tx_obsCliRetEmpRel";//tx_obsCliRetEmpRel
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_POR_ARA).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_ara";//nd_ara
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_PRE_UNI).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_preUniImp";//nd_preUniImp
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_valTotFobCfr";//nd_valTotFobCfr
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_cantonmet";//nd_cantonmet
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_FLE).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_valFle";//nd_valFle
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_VAL_CFR).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_valCfr";//nd_valCfr
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_ARA).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_valTotAra";//nd_valTotAra
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_TOT_GAS).toString()), objParSis.getDecimalesBaseDatos()) + " AS nd_valTotGas";//nd_valTotGas
                    strSQL+=", 'I' AS st_regRep";//st_regRep
                    strSQL+="	    WHERE NOT EXISTS(";
                    strSQL+="           SELECT *FROM tbm_detMovInv";
                    strSQL+="           WHERE co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                    strSQL+="           AND co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                    strSQL+="           AND co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                    strSQL+="           AND co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                    strSQL+="           AND co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP) + "";
                    strSQL+="	   )";
                    strSQL+=");";
                    
                    
                    
                    //agregar en la Orden de conteo
                    if(blnCreItmOrdConInv){
                        strSQL+="INSERT INTO tbm_detordconinv(";
                        strSQL+="            co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, st_regrep)";
                        strSQL+="(";
                        strSQL+="	    SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp";
                        strSQL+="            , " + objParSis.getCodigoLocal() + " AS co_loc";
                        strSQL+="            , " + INT_COD_TIP_DOC_ORD_CON + " AS co_tipdoc";
                        strSQL+="            , " + intCodDocOrdConImp + " AS co_doc";
                        strSQL+="            , " + intMaxCodReg_detOrdCon + " AS co_reg";
                        strSQL+="            , " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + " AS co_itm";//codigoGrupo
                        strSQL+="            , 'I' AS st_regrep";
                        strSQL+="	    WHERE NOT EXISTS(";
                        strSQL+="		SELECT a4.* FROM tbm_cabOrdConInv AS a1";
                        strSQL+="		INNER JOIN tbm_cabMovInv AS a2 ON(a1.co_empRel=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_doc)";
                        strSQL+="		INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                        strSQL+="		INNER JOIN tbm_conInv AS a4 ON(a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_locRel";
                        strSQL+="						AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)";
                        strSQL+="		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="                AND a1.co_loc=" + objParSis.getCodigoLocal() +"";
                        strSQL+="		AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_CON + "";
                        strSQL+=" 		AND a1.co_doc=" + intCodDocOrdConImp + "";
                        strSQL+="		AND a1.st_reg='A'";
                        strSQL+="		AND a4.co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + "";
                        strSQL+="	   )";
                        strSQL+=")";
                        strSQL+=";";
                        //si el item no existe, no es necesario adicionar el valor en cantidad contada y mal estado porq sencillamente el ITEM NO EXISTE, por eso se crea con ese valor.
                        strSQL+="INSERT INTO tbm_conInv(";
                        strSQL+="            co_emp, co_reg, co_usrrescon, co_bod, co_itm, co_locrel, co_tipdocrel,";
                        strSQL+="            co_docrel, co_regrel, fe_solcon, fe_reacon, nd_stksis, nd_caningbod,";
                        strSQL+="            nd_canegrbod, nd_stkcon, st_conrea, tx_obs1, st_regrep, nd_cansob, nd_canMalEst)";
                        strSQL+="(";
                        strSQL+="	    SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp";
                        strSQL+="            , " + intUltReg_ConInv + " AS co_reg";
                        strSQL+="            , " + objParSis.getCodigoUsuario() + " AS co_usrrescon";
                        strSQL+="            , " + txtCodBod.getText() + " AS co_bod";
                        strSQL+="            , " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + " AS co_itm";
                        strSQL+="            , " + objParSis.getCodigoLocal() + " AS co_locrel";
                        strSQL+="            , " + INT_COD_TIP_DOC_ORD_CON + " AS co_tipdocrel";
                        strSQL+="            , " + intCodDocOrdConImp + " AS co_docrel";
                        strSQL+="            , " + intMaxCodReg_detOrdCon + " AS co_regrel, NULL AS fe_solcon";
                        strSQL+="            , " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_reacon";
                        strSQL+="            , 0 AS nd_stksis";
                        strSQL+="            , 0 AS nd_caningbod";
                        strSQL+="            , 0 AS nd_canegrbod";
                        strSQL+="            , 0 AS nd_stkcon";
                        strSQL+="            , 'S' AS st_conrea, NULL AS tx_obs1, 'I' AS st_regrep";
                        strSQL+="            , 0 AS nd_cansob";
                        strSQL+="            , 0 AS nd_canmalest";
                        strSQL+="	    WHERE NOT EXISTS(";
                        strSQL+="		SELECT a4.* FROM tbm_cabOrdConInv AS a1";
                        strSQL+="		INNER JOIN tbm_cabMovInv AS a2 ON(a1.co_empRel=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_doc)";
                        strSQL+="		INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                        strSQL+="		INNER JOIN tbm_conInv AS a4 ON(a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_locRel";
                        strSQL+="						AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)";
                        strSQL+="		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="               AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+="		AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_CON + "";
                        strSQL+="               AND a1.co_doc=" + intCodDocOrdConImp + "";
                        strSQL+=" 		AND a1.st_reg='A'";
                        strSQL+="		AND a4.co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + "";
                        strSQL+="	   )";
                        strSQL+=")";
                        strSQL+=";";
                    }
                    //agregar en la Orden de distribucion de Inmaconsa, si es que esta existe, sino no se hace nada
                    if(blnCreItmOrdDisInm){
                        
                        strSQL+="INSERT INTO tbm_detorddis(";
                        strSQL+="            co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, nd_can, co_emprel,";
                        strSQL+="            co_locrel, co_tipdocrel, co_docrel, co_regrel, nd_canutiorddis)";
                        strSQL+="(";
                        strSQL+="	    SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp";
                        strSQL+="            , " + objParSis.getCodigoLocal() + " AS co_loc";
                        strSQL+="            , " + INT_COD_TIP_DOC_ORD_DIS_INM + " AS co_tipdoc";
                        //strSQL+="            , " + intCodDocOrdConImp + " AS co_doc";
                        strSQL+="            , 	(SELECT a1.co_doc FROM tbm_caborddis AS a1 INNER JOIN tbm_detorddis AS a2";
                        strSQL+="                ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="                   WHERE a2.co_empRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                        strSQL+="                   AND a2.co_locRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                        strSQL+="                   AND a2.co_tipDocRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                        strSQL+="                   AND a2.co_docRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                        strSQL+="                   AND a1.co_bod=" + INT_COD_BOD_INM_GRP + "";//15
                        strSQL+="                   AND a1.st_reg='A'";
                        strSQL+="            GROUP BY a1.co_doc";
                        strSQL+="            ) AS co_doc";
                        strSQL+="            , " + intMaxCodReg_detOrdCon + " AS co_reg";
                        strSQL+="            , " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + " AS co_itm";//codigoGrupo
                        strSQL+="            , " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA) + " AS nd_can";//nd_can
                        strSQL+="            , " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + " AS co_emprel";//co_emprel
                        strSQL+="            , " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + " AS co_locrel";//co_locrel
                        strSQL+="            , " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + " AS co_tipdocrel";//co_tipdocrel
                        strSQL+="            , " + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "AS co_docrel";//co_docrel
                        strSQL+="            , " + intMaxCodReg_detOrdCon + " AS co_regrel";//co_regrel
                        strSQL+="            , 0 AS nd_canutiorddis";//nd_canutiorddis
                        strSQL+="	    WHERE NOT EXISTS(";
                        strSQL+="               SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        strSQL+="               FROM(";
                        strSQL+="                   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                        strSQL+="                   FROM tbm_caborddis AS a1 INNER JOIN tbm_detorddis AS a2";
                        strSQL+="                   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="                   WHERE a2.co_empRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                        strSQL+="                   AND a2.co_locRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                        strSQL+="                   AND a2.co_tipDocRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                        strSQL+="                   AND a2.co_docRel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                        strSQL+="                   AND a2.co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + "";
                        strSQL+="                   AND a1.co_bod=" + INT_COD_BOD_INM_GRP + "";//15
                        strSQL+="                   AND a1.st_reg='A'";
                        strSQL+="                   ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                        strSQL+="               ) AS b1";
                        strSQL+="	   )";
                        strSQL+=")";
                        strSQL+=";";                        
                    }                    
                    strSQLIns+=strSQL;
                    
                    //intMaxCodReg++;
                    if(objTooBar.getEstado()=='n'){
                        if(!generaNuevoContenedorItemsMovimientoStock(Integer.parseInt(txtCodImp.getText()),Integer.parseInt(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_EMP).toString()),Double.parseDouble(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString()),intCodBodEmp)){
                            blnRes=false;
                        }
                        
                    }

                    arlRegCodItmMaeConInv=new ArrayList();
                    arlRegCodItmMaeConInv.add(INT_ARL_COD_ITM_MAE_CON_INV, objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM_MAE));
                    arlDatCodItmMaeConInv.add(arlRegCodItmMaeConInv);

                }

                if(objTooBar.getEstado()=='n')
                {
                    //cuando hay conexion remota se ejecuta este
                    if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, Integer.parseInt(txtCodImp.getText()),INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm)){
                               arlDatStkInvItm.clear(); 
                        }else{blnRes=false;}
                    }else{blnRes=false;}

                }
                //System.out.println("insertar_tbmDetMovInv: " + strSQLIns);
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
     * Función que permite determinar si el item existe en tbm_detMovInv
     * @return true: Si el item existe
     * <BR> false: Caso contrario
     */
    private boolean isExisteItem_detMovInv(int codigoItemEmpresa){
        boolean blnRes=false;
        String strSQLExiItm;
//        Statement stmExiItm;
//        ResultSet rstExiItm;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQLExiItm="";
                strSQLExiItm+="SELECT *FROM tbm_detMovInv AS a2";
                strSQLExiItm+=" WHERE a2.co_emp=" + txtCodImp.getText() + " AND a2.co_loc=" + strCodLocPre + "";
                strSQLExiItm+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_doc=" + txtCodDoc.getText() + "";
                strSQLExiItm+=" AND a2.co_itm=" + codigoItemEmpresa + "";
                strSQLExiItm+=";";
                rst=stm.executeQuery(strSQLExiItm);
                if(rst.next())
                    blnRes=true;
//                stm.close();
//                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch (java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    /**
     * Función que permite determinar si el item existe en tbm_detOrdConInv
     * @return true: Si el item existe
     * <BR> false: Caso contrario
     */
    private boolean isExisteItem_detOrdConInv(int codigoItemGrupo){
        boolean blnRes=false;
        String strSQLExiItm;
//        Statement stmExiItm;
//        ResultSet rstExiItm;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQLExiItm="";
                strSQLExiItm+="SELECT a2.co_reg AS co_regItm_ordConInv FROM tbm_cabOrdConInv AS a1";
                strSQLExiItm+=" INNER JOIN tbm_detOrdConInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQLExiItm+=" INNER JOIN tbm_conInv AS a3";
                strSQLExiItm+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locrel AND a2.co_tipdoc=a3.co_tipdocrel AND a2.co_doc=a3.co_docrel AND a2.co_reg=a3.co_regrel";
                strSQLExiItm+=" WHERE a1.co_empRel=" + txtCodImp.getText() + "";
                strSQLExiItm+=" AND a1.co_locRel=" + strCodLocPre + "";
                strSQLExiItm+=" AND a1.co_tipDocRel=" + txtCodTipDoc.getText() + "";
                strSQLExiItm+=" AND a1.co_docRel=" + txtCodDoc.getText() + "";
                strSQLExiItm+=" AND a2.co_itm=" + codigoItemGrupo + "";
                strSQLExiItm+=";";
                rst=stm.executeQuery(strSQLExiItm);
                if(rst.next()){
                    intCodRegOrdConImp=rst.getInt("co_regItm_ordConInv");
                    blnRes=true;
                }
//                stm.close();
//                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean isExisteItem_detOrdDis(){
        boolean blnRes=false;
        String strSQLExiItm;
        Statement stmExiItm;
        ResultSet rstExiItm;
        try{
            if(con!=null){
                stmExiItm=con.createStatement();
                strSQLExiItm="";
                strSQLExiItm+="SELECT a1.co_doc FROM tbm_cabOrdDis AS a1";
                strSQLExiItm+=" INNER JOIN tbm_detOrdDis AS a2 ";
                strSQLExiItm+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQLExiItm+=" WHERE a2.co_empRel=" + txtCodImp.getText() + "";
                strSQLExiItm+=" AND a2.co_locRel=" + strCodLocPre + "";
                strSQLExiItm+=" AND a2.co_tipDocRel=" + txtCodTipDoc.getText() + "";
                strSQLExiItm+=" AND a2.co_docRel=" + txtCodDoc.getText() + "";
                strSQLExiItm+=" AND a1.co_bod=" + INT_COD_BOD_INM_GRP + "";//15
                strSQLExiItm+=" GROUP BY a1.co_doc";
                strSQLExiItm+=";";
                rstExiItm=stmExiItm.executeQuery(strSQLExiItm);
                if(rstExiItm.next()){
                    blnRes=true;
                }
                stmExiItm.close();
                stmExiItm=null;
                rstExiItm.close();
                rstExiItm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
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
            for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblModImp34_02.getRowCountTrue(); j++){
                    if(objTblModImp34_02.compareStringCells(i, objImp34_02.INT_TBL_DAT_COD_ITM, j, objImp34_02.INT_TBL_DAT_COD_ITM)){
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
     * Función que permite obtener las cuentas que están configuradas en el maestro de tipos de documentos
     * @return true : si se pudo obtener la información
     * <BR> false : caso contrario
     */
    private boolean configurarCuentaComprasLocales_CabTipDoc(){
        boolean blnRes=false;
        Connection conCtaHab;
        Statement stmCtaHab;
        ResultSet rstCtaHab;
        try{
            conCtaHab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCtaHab!=null){
                stmCtaHab=conCtaHab.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc";
                strSQL+=", a1.co_ctadeb AS co_ctaIva, a2.tx_codCta AS tx_numCtaIva, a2.tx_deslar AS tx_nomCtaIva";
                strSQL+=", a1.co_ctahab AS co_ctaHab, a3.tx_codCta AS tx_numCtaHab, a3.tx_deslar AS tx_nomCtaHab";
                strSQL+=" FROM (       (tbm_cabtipdoc AS a1 LEFT OUTER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp AND a1.co_ctadeb=a2.co_cta )";
                strSQL+="       LEFT OUTER JOIN tbm_plaCta AS a3 ON a1.co_emp=a3.co_emp AND a1.co_ctahab=a3.co_cta )";
                strSQL+=" WHERE a1.co_emp=" + txtCodImp.getText() + "";
                strSQL+=" AND a1.co_loc=" + strCodLocPre + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a1.st_reg='A' AND a2.st_reg='A'";
                //System.out.println("configurarCuentaComprasLocales_CabTipDoc: " + strSQL);
                rstCtaHab=stmCtaHab.executeQuery(strSQL);
                if(rstCtaHab.next()){
                    strCodCtaActPedEmb=rstCtaHab.getString("co_ctaHab");
                    strNumCtaActPedEmb=rstCtaHab.getString("tx_numCtaHab");
                    strNomCtaActPedEmb=rstCtaHab.getString("tx_nomCtaHab");
                    strCodCtaActIva=rstCtaHab.getString("co_ctaIva");
                    strNumCtaActIva=rstCtaHab.getString("tx_numCtaIva");
                    strNomCtaActIva=rstCtaHab.getString("tx_nomCtaIva");
                    blnRes=true;
                }
                conCtaHab.close();
                conCtaHab=null;
                stmCtaHab.close();
                stmCtaHab=null;
                rstCtaHab.close();
                rstCtaHab=null;
                //System.out.println("strCodCtaActPedEmb: " + strCodCtaActPedEmb);
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
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
        int intCodTipDocSelUsr=Integer.parseInt(txtCodTipDoc.getText());
        try{
            for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                strCodAltItmTer=objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM).toString();
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
            
            if(intCodTipDocSelUsr==14){//inimpo
                if(intTipTerItm_regTblDat_s>0){//debería ser "cero" porque el INIMPO sólo maneja terminales 'I' y esta variable refleja que hay items con terminales 'S' en la tabla
                    blnRes=true;
                }
            }
            else{//inbocl
                if(intTipTerItm_regTblDat_i>0){//debería ser "cero" porque el INBOCL sólo maneja terminales 'S' y esta variable refleja que hay items con terminales 'I' en la tabla 
                    blnRes=true;
                }
            }            
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void generaAsiDiaLoc(){
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
        
        BigDecimal bgdValTotDoc=new BigDecimal("0");
        BigDecimal bgdValIvaDoc=new BigDecimal("0");
        BigDecimal bgdValSubDoc=new BigDecimal("0");
        
        bgdPorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        
        try{
            getCodigoCuentaBodegaEmpresa();//cuenta del debe
            
            if(!txtCodTipDoc.getText().equals("14")){
                if(!configurarCuentaComprasLocales_CabTipDoc()){
                    System.out.println("Problemas con la cuenta de haber del proceso de compras locales");
                }
            }
                        
            ///para generar el asiento de diario
            objAsiDia.inicializar();            
            vecDatDia.clear();
            bgdValTotDoc=new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText()));
            
            if(txtCodTipDoc.getText().equals("14")){//INIMPO
                //total - debe              
                if( bgdValTotDoc.compareTo(BigDecimal.ZERO)>0  ){
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaBodEmpAsiDia);//intCodCtaTot
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaBodEmpAsiDia);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaBodEmpAsiDia);
                    vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValTotDoc);
                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                    
                    //total - haber
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaActPedEmb);//intCodCtaTot
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaActPedEmb);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaActPedEmb);
                    vecRegDia.add(INT_VEC_DIA_DEB, "0");
                    vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValTotDoc);
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }

            }
            else{//Compras Locales
                
                bgdValSubDoc=objUti.redondearBigDecimal(   (bgdValTotDoc.divide(bgdPorIva, 2, BigDecimal.ROUND_DOWN)), objParSis.getDecimalesMostrar() );
                
                bgdValIvaDoc=bgdValTotDoc.subtract(bgdValSubDoc);
                
                if( bgdValTotDoc.compareTo(BigDecimal.ZERO)>0  ){
//                    //subtotal
//                    vecRegDia=new java.util.Vector();
//                    vecRegDia.add(INT_VEC_DIA_LIN, "");
//                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaBodEmpAsiDia);
//                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaBodEmpAsiDia);
//                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
//                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaBodEmpAsiDia);
//                    vecRegDia.add(INT_VEC_DIA_DEB, "" +  bgdValSubDoc   );
//                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
//                    vecRegDia.add(INT_VEC_DIA_REF, "");
//                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
//                    vecDatDia.add(vecRegDia);
                    
//                    //iva
//                    vecRegDia=new java.util.Vector();
//                    vecRegDia.add(INT_VEC_DIA_LIN, "");
//                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaActIva);//intCodCtaTot
//                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaActIva);
//                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
//                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaActIva);
//                    vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValIvaDoc);
//                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
//                    vecRegDia.add(INT_VEC_DIA_REF, "");
//                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
//                    vecDatDia.add(vecRegDia);
                    
                    //total
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaBodEmpAsiDia);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaBodEmpAsiDia);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaBodEmpAsiDia);
                    vecRegDia.add(INT_VEC_DIA_DEB, "" +  bgdValTotDoc   );
                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);

                    //total
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaActPedEmb);//intCodCtaTot
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaActPedEmb);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaActPedEmb);
                    vecRegDia.add(INT_VEC_DIA_DEB, "0");
                    vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValTotDoc);
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }

            }
            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)2);
            actualizarGlo();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR> false en el caso contrario
     */
    private boolean insertar_tbmCabOrdDis(){
        boolean blnRes=true;
        intCodDocOrdDis=0;
        intNumDocOrdDis=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT CASE WHEN MAX (a1.co_doc) IS NULL THEN 1 ELSE MAX(a1.co_doc)+1 END AS co_doc ";
                strSQL+=" FROM tbm_cabOrdDis AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_DIS_INM;
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodDocOrdDis=rst.getObject("co_doc")==null?0:rst.getInt("co_doc");
                rst.close();
                rst = null;
                
                strSQL ="";
                strSQL+=" SELECT CASE WHEN (a1.ne_ultdoc) IS NULL THEN 1 ELSE (a1.ne_ultdoc+1) END AS ne_numDoc \n";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_DIS_INM;
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumDocOrdDis=rst.getObject("ne_numDoc")==null?0:rst.getInt("ne_numDoc");
                rst.close();
                rst = null;
                
                strSQL ="";
                strSQL+="INSERT INTO tbm_caborddis(";
                strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, tx_obs1 ";
                strSQL+="   , tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                strSQL+=", " + INT_COD_TIP_DOC_ORD_DIS_INM;//co_tipdoc
                strSQL+=", " + intCodDocOrdDis;//co_doc
                strSQL+=", CURRENT_DATE";//fe_doc
                strSQL+=", " + intNumDocOrdDis;//ne_numdoc
                strSQL+=", " + INT_COD_BOD_INM_GRP;//co_bod
                strSQL+=",  Null ";//tx_obs1
                strSQL+=",  Null ";//tx_obs2
                strSQL+=", 'A' ";//st_reg
                strSQL+=", CURRENT_TIMESTAMP";//fe_ing
                strSQL+=", CURRENT_TIMESTAMP";//fe_ultmod
                strSQL+=", " + objParSis.getCodigoUsuario() ;//co_usring
                strSQL+=", " + objParSis.getCodigoUsuario() ;//co_usrmod
                strSQL+=", 'I' ";//st_regrep
                strSQL+=" );";
                //System.out.println("insertar_tbmCabOrdDis: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Función que permite guardar información
     * return true Si se pudo realizar la operación
     * <BR> false en el caso contrario
     */
    private boolean insertar_tbmDetOrdDis(){
        boolean blnRes=true;
        String strSQLUpd="";
        BigDecimal bgdCanSel=new BigDecimal("0");
        int j=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                    j++;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detorddis(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, nd_can";
                    strSQL+=" , co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() ;//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal() ;//co_loc
                    strSQL+=", " + INT_COD_TIP_DOC_ORD_DIS_INM ;//co_tipdoc
                    strSQL+=", " + intCodDocOrdDis ;//co_doc
                    strSQL+=", " + j ;//co_reg
                    strSQL+=", " + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) ;//co_itm
                    bgdCanSel=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString())   );
                    strSQL+=", " + bgdCanSel ;//nd_can
                    strSQL+=", " + txtCodImp.getText() ;//co_empRel
                    strSQL+=", " + strCodLocPre ;//co_locRel
                    strSQL+=", " + txtCodTipDoc.getText() ;//co_tipDocRel
                    strSQL+=", " + txtCodDoc.getText() ;//co_docRel
                    strSQL+=", " + j + "";//co_regRel
                    strSQL+=" );";
                    strSQLUpd+=strSQL;
                    
                }
                //System.out.println("insertar_tbmDetOrdDis: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
                stm.close();
                stm=null;
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabOrdDis(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabOrdDis";
                strSQL+=" SET fe_ultMod='" + strAux + "'";
                strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabOrdDis AS a1 INNER JOIN tbm_detOrdDis AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	WHERE a2.co_emprel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" 	AND a2.co_locrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                strSQL+="	AND a2.co_tipdocrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+="       AND a2.co_docrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                strSQL+="       AND a1.co_bod=" + INT_COD_BOD_INM_GRP + "";
                strSQL+="	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=") AS a1";
                strSQL+=" WHERE tbm_cabOrdDis.co_emp=a1.co_emp AND tbm_cabOrdDis.co_loc=a1.co_loc AND tbm_cabOrdDis.co_tipDoc=a1.co_tipDoc AND tbm_cabOrdDis.co_doc=a1.co_doc";
                strSQL+="";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite actualizar el detalle de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmDetOrdDis(){
        boolean blnRes=true;
        String strSQLUpd="";
        BigDecimal bgdCanSelAct=new BigDecimal("0");
        BigDecimal bgdCanSelAnt=new BigDecimal("0");
        String strLin="";
        int intUltReg=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a2.co_reg) AS co_reg";
                strSQL+=" FROM tbm_cabOrdDis AS a1 INNER JOIN tbm_detOrdDis AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a2.co_emprel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND a2.co_locrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND a2.co_tipdocrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND a2.co_docrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                strSQL+=" AND a1.co_bod=" + INT_COD_BOD_INM_GRP + "";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                //System.out.println("strSQL:" + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltReg=rst.getObject("co_reg")==null?0:rst.getInt("co_reg");
                
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabOrdDis AS a1 INNER JOIN tbm_detOrdDis AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a2.co_emprel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND a2.co_locrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND a2.co_tipdocrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND a2.co_docrel=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";
                strSQL+=" AND a1.co_bod=" + INT_COD_BOD_INM_GRP + "";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodDocOrdDis=rst.getObject("co_doc")==null?0:rst.getInt("co_doc");
                
                for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                    strLin=objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_LIN)==null?"":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("I")){//son nuevos registros
                        intUltReg++;
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detorddis(";
                        strSQL+=" co_emp, co_loc, co_tipdoc,";
                        strSQL+=" co_doc, co_reg, co_itm, nd_can";
                        strSQL+=" , co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel)";
                        strSQL+=" VALUES (";
                        strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                        strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                        strSQL+="" + INT_COD_TIP_DOC_ORD_DIS_INM + ",";//co_tipdoc
                        strSQL+="" + intCodDocOrdDis + ",";//co_doc
                        strSQL+="" + intUltReg + ",";//co_reg
                        strSQL+="" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + ",";//co_itm
                        bgdCanSelAct=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString())   );
                   
                        strSQL+="" + bgdCanSelAct + ",";//nd_can
                        strSQL+="" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + ",";//co_empRel
                        strSQL+="" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + ",";//co_locRel
                        strSQL+="" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + ",";//co_tipDocRel
                        strSQL+="" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + ",";//co_docRel
                        strSQL+="" + intUltReg + "";//co_regRel
                        strSQL+=");";
                        strSQLUpd+=strSQL;
                    }
                    else if(strLin.equals("M")){// los registros que existen en la DB y que si se han modificados (el estado cambio a "M")
                        bgdCanSelAct=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_PZA).toString())   );
                        bgdCanSelAnt=new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_ING_IMP_AUX)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_ING_IMP_AUX).equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_CAN_ING_IMP_AUX).toString())   );
                    
                        strSQL="";
                        strSQL+=" UPDATE tbm_detOrdDis";
                        strSQL+=" SET nd_can=" + bgdCanSelAct;
                        strSQL+=" WHERE co_empRel=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_locRel=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipDocRel=" + INT_COD_TIP_DOC_ORD_DIS_INM + "";
                        strSQL+=" AND co_docRel=" + intCodDocOrdDis + "";
                        strSQL+=" AND co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + ";";//codigo de item de grupo
                        
                        strSQLUpd+=strSQL;
                    }
                    else{//son registros ya guardados y q no se le han hecho modificaciones (el estado esta en blanco) 
                        
                    }                   
                    
                }
                //System.out.println("insertar_tbmDetOrdDis: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
                stm.close();
                stm=null;
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
        String strSbjMsg="" + intNumIngImpDia + ") " + txtNumPed.getText() + " (" + objParSis.getNombreMenu() + ")";
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
                strMsg+="	<th colspan='7'>Pedido # " + txtNumPed.getText() + "</th>";
                strMsg+="</tr>";
                
                strMsg+="<tr bgcolor='#F2E0F7'>";
                strMsg+="	<th>Nº</th>";
                strMsg+="	<th>Cod.Alt.Itm.</th>";
                strMsg+="	<th>Cod.Let.Itm.</th>";
                strMsg+="	<th>Nom.Itm.</th>";
                strMsg+="	<th>Cos.Uni.Ped.</th>";
                strMsg+="	<th>Pre.Vta.</th>";
                strMsg+="	<th>Cos.Uni.Itm.</th>";
                strMsg+="	<th>Pre.Sug.</th>";
                strMsg+="	<th>Mar.Pre.Vta.M&iacuten.</th>";                        
                strMsg+="</tr>";
                
                //Se ordena por código alterno, para enviar el correo ordenado.
                objImp34_02.ordenarDatPorCodAlt();
                objTblModImp34_02.setModoOperacion(objTblModImp34_02.INT_TBL_NO_EDI);
                objTblModImp34_02.removeEmptyRows();                      
                
                for(int i=0; i<objTblModImp34_02.getRowCountTrue(); i++){
                    intNumFil++; 
                    strSQL ="";
                    strSQL+=" SELECT a1.nd_preVta1, a1.nd_marUti, a1.nd_cosUni FROM tbm_inv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_itm=" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ITM) + "";
                    strSQL+=";";
                    rstValPreVta=stmValPreVta.executeQuery(strSQL);
                    if(rstValPreVta.next()){
                        bgdPreVtaItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_preVta1"), objParSis.getDecimalesMostrar());
                        //bgdPreVtaDscItm=objUti.redondearBigDecimal(bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))), objParSis.getDecimalesMostrar());
                        bgdMarUtiItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_marUti"),objParSis.getDecimalesMostrar());
                        bgdCosUniItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_cosUni"), objParSis.getDecimalesMostrar());
                    }
                    bgdCosUniInsPed=objUti.redondearBigDecimal((new BigDecimal(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COS_UNI).toString()))), objParSis.getDecimalesMostrar());
                    //obtencion de la formula
                    bgdRelCosUniMarPreVta=objUti.redondearBigDecimal((bgdCosUniInsPed.divide((   (new BigDecimal("1")).subtract(   bgdMarUtiItm.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)        )        ), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesMostrar());

                    if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("1.005"))), objParSis.getDecimalesMostrar()))>0){//bgdPreVtaDscItm      por debajo del margen  - rojo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FF4000'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                    else if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("0.995"))), objParSis.getDecimalesMostrar()))<0){//bgdPreVtaDscItm       por encima del margen - amarillo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FFFF00'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM) + "</td>";
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
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp34_02.getValueAt(i, objImp34_02.INT_TBL_DAT_NOM_ITM) + "</td>";
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
     * Función que permite determinar si existe código de seguimiento asociado al Pedido Embarcado (instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior al Ingreso por Importación(Instancia anterior: Pedido Embarcado)
     * <BR> false Caso contrario
     */
    private boolean getCodSegPedEmb(){
        boolean blnRes=false;
        Object objCodSegPedEmb=null;
        Object objCodSegIngImp=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT *FROM(";
                strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a3.co_seg AS co_segIngImp, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	, a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
                strSQL+=" 	FROM tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                strSQL+="               WHERE a1.co_emp=" + txtCodImp.getText() + "";
                strSQL+="               AND a1.co_loc=" + strCodLocPre + "";
                strSQL+="               AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="               AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_empRelPedEmbImp AND b1.co_loc=b2.co_locRelPedEmbImp AND b1.co_tipDoc=b2.co_tipDocRelPedEmbImp AND b1.co_doc=b2.co_docRelPedEmbImp";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegPedEmb=rst.getObject("co_segPedEmb");
                    objCodSegIngImp=rst.getObject("co_segIngImp");
                }
                
                if(objCodSegIngImp==null){//no existe seguimiento por tanto se debe ingresar 
                    blnRes=true;
                    objCodSegInsAnt=objCodSegPedEmb;
                }
                else{//ya existe en el seguimiento la nota de pedido por tanto no se debe hacer nada
                    //blnRes=false; no es necesario porque desde el inicio está en "false"
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            //System.out.println("Error - getCodSegPedEmb SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            //System.out.println("Error - getCodSegPedEmb: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
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
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        strAux="";
        String strSQLRep="", strSQLSubRepAra="", strSQLSubRepItm="", strSQLSubRepCarPag="";;
        String strValDocPal="0";
        String strRutImg="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
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
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            strSQL="";
                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                            strSQL+=" 	   , a1.ne_tipnotped, a1.co_exp, a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                            strSQL+=" 	   , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp AS co_imp";
                            strSQL+=" 	   , a3.tx_nom AS tx_nomImp";
                            if(txtCodTipDoc.getText().equals("14")){
                                strSQL+="  , a5.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp, a5.tx_dir AS tx_dirExp";
                                strSQL+="  , a5.tx_tel1 AS tx_telExp1, a5.tx_tel2 AS tx_telExp2, a5.tx_fax AS tx_faxExp";
                            }
                            else if(txtCodTipDoc.getText().equals("245")){
                                strSQL+="  , a1.co_cli AS co_exp, a1.tx_nomCli AS tx_nomExp, a1.tx_ruc AS tx_aliExp, a1.tx_dirCli AS tx_dirExp";
                                strSQL+="  , a1.tx_telcli AS tx_telExp1, NULL AS tx_telExp2, null AS tx_faxExp";
                            }
                                
                            strSQL+=" 	   , a8.co_bodGrp AS co_bod, a7.tx_nom AS tx_nomBod ";
                            //strSQL+="    , ROUND(a3.nd_ivaCom/100, 2) as PorIva"; //Este dato lee tbm_emp.
                            strSQL+="      , Imp.nd_porImpIvaCom, ROUND(Imp.nd_porImpIvaCom/100, 2) as PorIva ";  //Nueva tabla de Configuracion de Impuestos tbm_cfgImpLoc.
                            
                            strSQL+="  FROM (tbm_cabMovInv AS a1 ";
                            strSQL+=" 	     INNER JOIN (tbm_detMovInv AS a6 INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
                            strSQL+=" 			 INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
                            strSQL+="			)";
                            strSQL+="  			ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                            strSQL+="  	     )";
                            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                            strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                            strSQL+=" INNER JOIN ";
                            strSQL+=" ( ";
                            strSQL+="     select co_emp, co_loc, co_cta as co_ctaIvaCom, nd_porImp as nd_porImpIvaCom, fe_vigHas, fe_vigDes ";    
                            strSQL+="     from tbm_cfgImpLoc  where st_reg='A' AND co_imp="+INT_ARL_COD_CFG_IVA_COM+" ";
                            strSQL+=" ) as Imp ";
                            strSQL+=" ON (Imp.co_emp=a1.co_emp AND Imp.co_loc=a1.co_loc AND (CASE WHEN Imp.fe_vigHas IS NULL THEN a1.fe_doc>=Imp.fe_vigDes ELSE a1.fe_doc>=Imp.fe_vigDes AND a1.fe_doc<=Imp.fe_vigHas END) ) ";
                            strSQL+=" LEFT OUTER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC);
                            strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                            strSQL+="  	     , a1.ne_tipnotped, a1.co_exp, a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                            strSQL+=" 	     , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp";
                            strSQL+=" 	     , a3.tx_nom, a8.co_bodGrp, a7.tx_nom";
                            if(txtCodTipDoc.getText().equals("14")){
                                strSQL+="    , a5.co_exp, a5.tx_nom, a5.tx_nom2, a5.tx_dir";
                                strSQL+="    , a5.tx_tel1, a5.tx_tel2, a5.tx_fax, Imp.nd_porImpIvaCom";
                            }
                            else{
                                strSQL+=" 	, a1.co_cli, a1.tx_nomCli, a1.tx_ruc, a1.tx_dirCli";
                                strSQL+=" 	, a1.tx_telcli, Imp.nd_porImpIvaCom";
                            }

                            strSQLRep=strSQL;

                            strSQL="";
                            strSQL+=" SELECT y.co_parara, y.tx_par, y.tx_des, x.nd_ara	";
                            strSQL+=" FROM(	";
                            strSQL+="    SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr	";
                            strSQL+="	      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara	";
                            strSQL+="	      , b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr , b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr	";
                            strSQL+="	      , CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra	";
                            strSQL+="	      , b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor AS tx_desCorUniMed	";
                            strSQL+="	      , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp, b1.co_itmAct, d1.co_ctaAct, d1.co_ctaPas	";
                            strSQL+="	 FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1  ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc ";
                            strSQL+="	       INNER JOIN tbm_cabPedEmbImp AS d1 ON c1.co_emprelpedembimp=d1.co_emp AND c1.co_locrelpedembimp=d1.co_loc AND c1.co_tipdocrelpedembimp=d1.co_tipDoc AND c1.co_docrelpedembimp=d1.co_doc  ";
                            strSQL+="	 )	";
                            strSQL+="	 INNER JOIN (  (  ";
                            strSQL+="	                 (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) ";
                            strSQL+="	                  LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg) ";
                            strSQL+="	                ) ";
                            strSQL+="	 ) ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm	";
                            strSQL+="    WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                            strSQL+="    AND b1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                            strSQL+="    AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                            strSQL+="    AND b1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC)+ "";
                            strSQL+="	 GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr	";
                            strSQL+="	        , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp	";
                            strSQL+="	        , b1.nd_ara, b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle ";
                            strSQL+="	        , b1.nd_valCfr, b1.nd_valTotAra, b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor, b1.co_itmAct	";
                            strSQL+="	        , d1.co_ctaAct, d1.co_ctaPas ";
                            strSQL+="	 ORDER BY b1.co_reg, a1.tx_codAlt ";
                            strSQL+="  ) AS x	";
                            strSQL+="  INNER JOIN( ";
                            strSQL+="	 SELECT b1.co_itmMae, b2.co_itm AS co_itmGrp, b2.co_parara, b2.tx_par, b2.tx_des ";
                            strSQL+="	 FROM(	SELECT a2.co_itmMae	";
                            strSQL+="	        FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm	";
                            strSQL+="	        WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                            strSQL+="	 ) AS b1 ";
                            strSQL+="	 INNER JOIN ( ";
                            strSQL+="	        SELECT a2.co_itmMae, a2.co_itm, CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara ";
                            strSQL+="	             , CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par	";
                            strSQL+="	             , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des	";
                            strSQL+="	        FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a2	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)	";
                            strSQL+="	        LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra	";
                            strSQL+="	                        INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra   	";
                            strSQL+="	        ) ON a1.co_grpImp=a4.co_grp	";
                            strSQL+="	        WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                            strSQL+="	 ) AS b2 ON b1.co_itmMae=b2.co_itmMae	";
                            strSQL+=" ) AS y	";
                            strSQL+=" ON x.co_itmMae=y.co_itmMae	";
                            strSQL+=" GROUP BY  y.co_parara, y.tx_par, y.tx_des, x.nd_ara ";
                            strSQL+=" ORDER BY y.co_parara	";
                            strSQLSubRepAra=strSQL;
                            
                            if(txtCodTipDoc.getText().equals("14")){
                                strSQL="";
                                strSQL+=" SELECT y.co_parara, y.tx_par, y.tx_des, x.nd_ara	";
                                strSQL+="      , x.tx_nomItm, x.nd_can, x.tx_codAlt";
                                strSQL+="      , x.nd_preUniImp, x.nd_valTotFobCfr,  x.nd_canTonMet, x.nd_valFle, x.nd_valCfr, x.nd_valTotAra, x.nd_valTotGas	";
                                strSQL+="      , x.nd_cosTot, x.nd_cosUni, x.co_reg, x.tx_desCorUniMed, x.co_itmAct  , x.co_emprelpedembimp	";
                                strSQL+="      , x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp  , x.co_ctaAct, x.co_ctaPas, y.co_itmGrp	";
                                strSQL+=" FROM(	";
                                strSQL+="	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr	";
                                strSQL+="            , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara	";
                                strSQL+="            , b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr ";
                                if( (optTmFob.isSelected())  ||  (optTmCfr.isSelected()))
                                    strSQL+="	     , b1.nd_canTonMet";
                                else
                                    strSQL+="        , b1.nd_can AS nd_canTonMet";
                                strSQL+="	     , b1.nd_valFle, b1.nd_valCfr	";
                                strSQL+="	     , CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra	";
                                strSQL+="	     , b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor AS tx_desCorUniMed	";
                                strSQL+="	     , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp, b1.co_itmAct, d1.co_ctaAct, d1.co_ctaPas	";
                                strSQL+="	FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1	";
                                strSQL+="	      ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc";
                                strSQL+="             INNER JOIN tbm_cabPedEmbImp AS d1	";
                                strSQL+="	      ON c1.co_emprelpedembimp=d1.co_emp AND c1.co_locrelpedembimp=d1.co_loc AND c1.co_tipdocrelpedembimp=d1.co_tipDoc AND c1.co_docrelpedembimp=d1.co_doc";
                                strSQL+="	) ";
                                strSQL+="	INNER JOIN (  (	";
                                strSQL+="	                (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)	";
                                strSQL+="	                 LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)	";
                                strSQL+="	               )	";
                                strSQL+="	)  ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm	";
                                strSQL+="       WHERE b1.co_emp=" +objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                                strSQL+="       AND b1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                                strSQL+="       AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                                strSQL+="       AND b1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";

                                strSQL+="	GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr	";
                                strSQL+="	       , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp	";
                                strSQL+="	       , b1.nd_ara, b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr";
                                strSQL+="              , b1.nd_canTonMet, b1.nd_valFle	";
                                strSQL+="              , b1.nd_valCfr, b1.nd_valTotAra, b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor, b1.co_itmAct	";
                                strSQL+="              , d1.co_ctaAct, d1.co_ctaPas	";
                                strSQL+="        ORDER BY b1.co_reg, a1.tx_codAlt	";
                                strSQL+=" ) AS x ";
                                strSQL+=" INNER JOIN(	";
                                strSQL+="	 SELECT b1.co_itmMae, b2.co_itm AS co_itmGrp, b2.co_parara, b2.tx_par, b2.tx_des ";
                                strSQL+="	 FROM( SELECT a2.co_itmMae FROM tbm_inv AS a1 ";
                                strSQL+="	       INNER JOIN tbm_equInv AS a2  ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm ";
                                strSQL+="	       WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                                strSQL+="	 ) AS b1 ";
                                strSQL+="	 INNER JOIN (	";
                                strSQL+="	       SELECT a2.co_itmMae, a2.co_itm	";
                                strSQL+="	            , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara, CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par	";
                                strSQL+="	            , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des	";
                                strSQL+="	       FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)	";
                                strSQL+="	       LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra	";
                                strSQL+="	                        INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra                          	";
                                strSQL+="	       )ON a1.co_grpImp=a4.co_grp	";
                                strSQL+="	       WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                                strSQL+="              AND (CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END)=$P!{co_parAraItm}";
                                strSQL+="	 ) AS b2	";
                                strSQL+="	 ON b1.co_itmMae=b2.co_itmMae	";
                                strSQL+=" ) AS y	";
                                strSQL+=" ON x.co_itmMae=y.co_itmMae	";
                                strSQL+=" GROUP BY x.co_itmMae, x.co_itm, x.tx_codAlt, x.tx_nomItm, x.nd_pesitmkgr , y.co_parara, y.tx_par, y.tx_des, x.nd_ara, x.nd_can	";
                                strSQL+="	 , x.nd_preUniImp, x.nd_valTotFobCfr,  x.nd_canTonMet, x.nd_valFle, x.nd_valCfr, x.nd_valTotAra, x.nd_valTotGas	";
                                strSQL+="	 , x.nd_cosTot, x.nd_cosUni, x.co_reg, x.tx_desCorUniMed, x.co_itmAct, x.co_emprelpedembimp	";
                                strSQL+="	 , x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp, x.co_ctaAct, x.co_ctaPas, y.co_itmGrp	";
                                strSQL+=" ORDER BY x.co_reg";
                            }
                            else if(txtCodTipDoc.getText().equals("245")){//INBOCL
                                strSQL="";
                                strSQL+=" SELECT x.nd_ara	";
                                strSQL+="      , x.tx_nomItm, x.nd_can, x.tx_codAlt";
                                strSQL+="      , x.nd_preUniImp, x.nd_valTotFobCfr,  x.nd_canTonMet, x.nd_valFle, x.nd_valCfr, x.nd_valTotAra, x.nd_valTotGas	";
                                strSQL+="      , x.nd_cosTot, x.nd_cosUni, x.co_reg, x.tx_desCorUniMed, x.co_itmAct  , x.co_emprelpedembimp	";
                                strSQL+="      , x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp  , x.co_ctaAct, x.co_ctaPas	";
                                strSQL+=" FROM(	";
                                strSQL+="	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr	";
                                strSQL+="	     , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara	";
                                strSQL+="	     , b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr ";
                                if( (optTmFob.isSelected())  ||  (optTmCfr.isSelected()))
                                    strSQL+="	     , b1.nd_canTonMet";
                                else
                                    strSQL+="        , b1.nd_can AS nd_canTonMet";
                                strSQL+="	     , b1.nd_valFle, b1.nd_valCfr	";
                                strSQL+="	     , CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra	";
                                strSQL+="	     , b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor AS tx_desCorUniMed	";
                                strSQL+="	     , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp, b1.co_itmAct, d1.co_ctaAct, d1.co_ctaPas	";
                                strSQL+="	FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1	";
                                strSQL+="	      ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc	";
                                strSQL+="	      INNER JOIN tbm_cabPedEmbImp AS d1 ON c1.co_emprelpedembimp=d1.co_emp AND c1.co_locrelpedembimp=d1.co_loc AND c1.co_tipdocrelpedembimp=d1.co_tipDoc AND c1.co_docrelpedembimp=d1.co_doc";
                                strSQL+="	)";
                                strSQL+="	INNER JOIN  (  ( (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) ";
                                strSQL+="	                  LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)	";
                                strSQL+="	               ) ";
                                strSQL+="	            )ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm	";
                                strSQL+="       WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP) + "";
                                strSQL+="       AND b1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC) + "";
                                strSQL+="       AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC) + "";
                                strSQL+="       AND b1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC) + "";

                                strSQL+="	GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr	";
                                strSQL+="	       , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp	";
                                strSQL+="	       , b1.nd_ara, b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr";

                                strSQL+="	       , b1.nd_canTonMet, b1.nd_valFle	";
                                strSQL+="	       , b1.nd_valCfr, b1.nd_valTotAra, b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor, b1.co_itmAct	";
                                strSQL+="	       , d1.co_ctaAct, d1.co_ctaPas	";
                                strSQL+="       ORDER BY b1.co_reg, a1.tx_codAlt	";
                                strSQL+=" ) AS x	";
                                strSQL+=" GROUP BY x.co_itmMae, x.co_itm, x.tx_codAlt, x.tx_nomItm, x.nd_pesitmkgr, x.nd_ara, x.nd_can	";
                                strSQL+="	 , x.nd_preUniImp, x.nd_valTotFobCfr,  x.nd_canTonMet, x.nd_valFle, x.nd_valCfr, x.nd_valTotAra, x.nd_valTotGas	";
                                strSQL+="	 , x.nd_cosTot, x.nd_cosUni, x.co_reg, x.tx_desCorUniMed, x.co_itmAct  , x.co_emprelpedembimp	";
                                strSQL+="	 , x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp  , x.co_ctaAct, x.co_ctaPas";
                                strSQL+=" ORDER BY x.co_reg";
                            }
                            
                            strSQLSubRepItm=strSQL;

                            strSQL ="";
                            strSQL+=" SELECT b2.co_carPag, b3.tx_nom AS tx_nomCarPag, b3.tx_tipCarPag, b2.nd_valCarPag, b3.ne_ubi	";
                            strSQL+=" FROM tbm_cabMovInv AS b1 INNER JOIN tbm_carPagMovInv AS b2 ";
                            strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc	";
                            strSQL+=" INNER JOIN tbm_carPagImp AS b3 ON b2.co_carpag=b3.co_carpag ";
                            strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC);
                            strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC);
                            strSQL+=" GROUP BY b2.co_carPag, b3.tx_nom, b3.tx_tipCarPag, b2.nd_valCarPag, b3.ne_ubi	";
                            strSQL+=" ORDER BY b3.ne_ubi ";
                            strSQLSubRepCarPag=strSQL;

                            //Cantidad en palabras.
                            try{
                                Librerias.ZafUtil.ZafNumLetra numero;
                                //double cantidad= objUti.redondear(txtValDoc.getText(), objParSis.getDecimalesMostrar());
                                double cantidad= objUti.redondear(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesMostrar());
                                
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

                            //strRutImg="D:\\DesaFuentes_Zafiro\\Reportes\\Compras\\ZafImp34";
                            strRutImg=objRptSis.getRutaReporte(i);

                            java.util.Map mapPar = new java.util.HashMap();
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 473: //Compras Locales
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                    mapPar.put("imgRut", strRutImg);

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;                                
                                default: //Ingreso por Importación
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("codEmp", new Integer(objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_EMP)));
                                    mapPar.put("codLoc", new Integer(objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_LOC)));
                                    mapPar.put("codTipDoc", new Integer(objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_TIPDOC)));
                                    mapPar.put("codDoc", new Integer(objUti.getIntValueAt(arlDatConIngImp, intIndiceIngImp, INT_ARL_CON_COD_DOC)));
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepAra", strSQLSubRepAra);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("strSQLSubRepCarPag", strSQLSubRepCarPag);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                    //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                    if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                        mapPar.put("totMts", new BigDecimal(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    else{
                                        mapPar.put("totMts", new BigDecimal(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    mapPar.put("fleApr", new BigDecimal("0"));
                                    mapPar.put("cfrApr", new BigDecimal(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objImp34_02.getTblTot().getValueAt(0, objImp34_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
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
                            }
                        }
                    }
                }
                con.close();
                con=null;
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
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.st_Reg IN ('A')";
                strSQL+=" AND a1.tx_numDoc2 LIKE "+objUti.codificar(strNumPed)+"";
                //System.out.println("isExisteNumeroPedido: "+strSQL);
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
    
    
    
    
}