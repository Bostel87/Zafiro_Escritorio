
package CxP.ZafCxP29;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafImp.ZafPagExt;
import Librerias.ZafImp.ZafPagImp;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafToolBar.ZafToolBar; 
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;  
import java.util.ArrayList;  
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;  
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 * @author sistemas9
 */
public class ZafCxP29 extends javax.swing.JInternalFrame
{
    //Constantes: Tab General
    private static final int INT_TBL_DAT_LIN  = 0 ; 
    private static final int INT_TBL_DAT_COD_EMP_PED = 1;              
    private static final int INT_TBL_DAT_COD_LOC_PED = 2;              
    private static final int INT_TBL_DAT_COD_TIPDOC_PED = 3;               
    private static final int INT_TBL_DAT_COD_DOC_PED = 4;     
    private static final int INT_TBL_DAT_NUM_PED_IMP = 5;
    private static final int INT_TBL_DAT_BUT_PED = 6; 
    private static final int INT_TBL_DAT_COD_EMP_IMP = 7;    
    private static final int INT_TBL_DAT_COD_REG_CAR = 8;               
    private static final int INT_TBL_DAT_COD_CAR_PAG = 9;              
    private static final int INT_TBL_DAT_NOM_CAR_PAG = 10;              
    private static final int INT_TBL_DAT_BUT_CAR_PAG = 11; 
    private static final int INT_TBL_DAT_COD_INS_PED = 12;   /* Código de Instancia del pedido. */
    private static final int INT_TBL_DAT_NOM_INS_PED = 13;   /* Nombre de Instancia del pedido. */
    private static final int INT_TBL_DAT_VAL_CAR_PAG_TOT = 14;
    private static final int INT_TBL_DAT_VAL_PAG_EXT = 15;
    private static final int INT_TBL_DAT_COD_CTA_CAR_PAG = 16;
    
    
    //Constantes: Tab Forma de Pago
    private static final int INT_TBL_FOR_PAG_DAT_LIN=0;
    private static final int INT_TBL_FOR_PAG_DAT_DIA_CRE=1;
    private static final int INT_TBL_FOR_PAG_DAT_FEC_VEN=2;
    private static final int INT_TBL_FOR_PAG_DAT_COD_TIP_RET=3;
    private static final int INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET=4;
    private static final int INT_TBL_FOR_PAG_DAT_BUT_TIP_RET=5;
    private static final int INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET=6;
    private static final int INT_TBL_FOR_PAG_DAT_POR_RET=7;
    private static final int INT_TBL_FOR_PAG_DAT_APL=8;
    private static final int INT_TBL_FOR_PAG_DAT_EST_APL=9;
    private static final int INT_TBL_FOR_PAG_DAT_BAS_IMP=10;
    private static final int INT_TBL_FOR_PAG_DAT_COD_SRI=11;
    private static final int INT_TBL_FOR_PAG_DAT_MON_PAG=12;    
    
    //Constantes: Asiento de Diario.
    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;
    
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USR_ING=4;  
    private static final int INT_ARL_CON_TXT_USR_MOD=5;  
    private int intIndiceCon=0;    
    
    //ArrayList para Instancias de Pedido
    //ArrayList arlDatInsPed, arlRegInsPed;
    final int INT_ARL_INS_PED_COD_EMP=0;
    final int INT_ARL_INS_PED_COD_LOC=1;
    final int INT_ARL_INS_PED_COD_TIP_DOC=2;
    final int INT_ARL_INS_PED_COD_DOC=3;
    final int INT_ARL_INS_PED_NUM_PED=4;    
    final int INT_ARL_INS_PED_INS_IMP=5;    
    
    //ArrayList: Forma de Pago
    private ArrayList arlDatDiaPag_ForPag, arlRegDiaPag_ForPag;
    private int INT_ARL_DIA_PAG_COD_EMP=0;
    private int INT_ARL_DIA_PAG_COD_FOR_PAG=1;
    private int INT_ARL_DIA_PAG_NUM_DIA_CRE=2;    
    
    private int INT_NUM_TOT_FIL_PED=3;    //Total de Filas por Pedido: 1=Valor Factura; 2=Valor ISD Cre Tri; 3=Valor ISD Gastos
    
    //Variables
    private Connection con;                           //Coneccion a la base donde se encuentra la Documento
    private Statement stm;                            //Statement para la Documento 
    private ResultSet rst;                            //Resultset que tendra los datos de la cabecera de la Documento
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafRptSis objRptSis;                       //Reportes del Sistema.
    private ZafAsiDia objAsiDia, objAsiDiaTraBan;
    private ZafSegMovInv objSegMovInv;
    private UltDocPrint objUltDocPrint;                //Para trabajar con la informacion de tipo de documento
    private ZafVenCon vcoTipDoc; 
    private ZafVenCon vcoPedImp;                       //Ventana de consulta "Pedidos".
    private ZafVenCon vcoPrvExt;                       //Ventana de consulta "Proveedores al exterior".
    private ZafVenCon vcoCtaBan; 
    private ZafVenCon vcoForPag;
    private ZafImp objZafImp;
    private ZafPagImp objPagImp;
    private ZafPagExt objPagExt;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModForPag;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPedImp;
    private ZafTblCelEdiButVco objTblCelEdiButVcoPedImp;
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JCheckBox en celda.    
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenBut objTblCelRenButForPag;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                    //Editor: Editor del JTable.
    private ZafDocLis objDocLis;                    //Instancia de clase que detecta cambios
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafDatePicker dtpFecCar;
    private java.util.Date datFecAux;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafPerUsr objPerUsr;                    //Permisos Usuarios.
    private ZafNotCorEle objNotCorEle;
    
    private MiToolBar objTooBar;
    private Vector vecDat,  vecCab,  vecReg;
    //private Vector vecDatForPag, vecRegForPag;         
           
    private boolean blnHayCam = false;   //Detecta ke se hizo cambios en el documento
    
    private BigDecimal bgdSig=new BigDecimal("0");
    private String strCodEmpDxP;  //Focus
    private int intCodEmpDxP; 
    private int intCodLocDxP;
    private int intCodEmpTraBan, intCodLocTraBan, intCodTipDocTraBan, intCodDocTraBan;
    private int intCodEmpMovBan, intCodLocMovBan, intCodTipDocMovBan, intCodDocMovBan;
    
    private int intCodCtaDocPag=0;
    
    private Object objCodSeg;
    
    private String strSQL, strAux;//EL filtro de la Consulta actual
    private String strDesCorTipDoc, strDesLarTipDoc;                  //Contenido del campo al obtener el foco.
    private String strCodCtaBan, strNumCtaBan, strNomBanCtaBan;       //Contenido del campo al obtener el foco.
    private String strCodPrvExt, strNomPrvExt; 
    private String strNumCtaDocPag="", strNomCtaDocPag="";  
    private String strCodForPag, strNomForPag;
    
    private String strVer = " v0.3.4"; 
        
    /** Crea una nueva instancia de la clase ZafCxP29. */
    public ZafCxP29(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                initComponents();    
                if (!configurarFrm())
                    exitForm();
                agregarDocLis();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }  
        }
        catch (CloneNotSupportedException e) 
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
        
    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            //Título de la ventana.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objPagImp = new ZafPagImp(objParSis, this);  //Pagos de Impuestos
            objPagExt = new ZafPagExt(objParSis, this);  //Pagos al Exterior 
            objUltDocPrint = new UltDocPrint(objParSis);  
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objAsiDia = new ZafAsiDia(objParSis);
            objAsiDiaTraBan = new ZafAsiDia(objParSis);
            objSegMovInv=new ZafSegMovInv(objParSis, this);
                        
            //Fecha del documento.
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setBounds(582, 4, 100, 20);
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
          
            //Fecha de carta.
            dtpFecCar = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecCar.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecCar.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecCar.setBounds(125, 15, 100, 20);
            dtpFecCar.setText("");
            panDatCar.add(dtpFecCar);            
  
            //Campos 
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodPrvExt.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCtaBan.setBackground(objParSis.getColorCamposObligatorios());
            txtCodEmpCtaBan.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBanCtaBan.setBackground(objParSis.getColorCamposObligatorios());
            txtNumCtaBan.setBackground(objParSis.getColorCamposObligatorios());            
            txtValCom.setBackground(objParSis.getColorCamposSistema()); 
            txtValInt.setBackground(objParSis.getColorCamposSistema()); 
            txtCodTipDoc.setVisible(false);  
            txtCodCtaBan.setVisible(false); 
            //txtValDoc.setEnabled(false); 
            txtValInt.setEditable(false);
            
            //Botón Correo Electrónico          
            butCorEle.setVisible(false);            
            if(objParSis.getCodigoUsuario()==1) { 
                butCorEle.setVisible(true);
            }
            
            //ToolBar
            objTooBar=new MiToolBar(this);
            //objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            panBar.add(objTooBar);
            if(objParSis.getCodigoUsuario()==1){  //Sólo admin podrá anular documento.
                objTooBar.setVisibleAnular(true);
            }
       
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            if (!objPerUsr.isOpcionEnabled(4449)) 
            {
                objTooBar.setVisibleInsertar(false);
            }
            if (!objPerUsr.isOpcionEnabled(4450)) 
            {
                objTooBar.setVisibleConsultar(false);
            }
            if (!objPerUsr.isOpcionEnabled(4454)) 
            {
                objTooBar.setVisibleImprimir(false);
            }
            if (!objPerUsr.isOpcionEnabled(4455)) 
            {
                objTooBar.setVisibleVistaPreliminar(false);
            }            
            
            /*Libreria para enviar correos electronicos a usuario.*/
            objNotCorEle = new ZafNotCorEle(objParSis);
            
            //***********************************************************************
            //Asiento de diario: Pago de Impuesto
            vecDatDia = new Vector();
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
             });
            
            objAsiDia.setEditable(false);
            panAsiDiaPagExt.add(objAsiDia,java.awt.BorderLayout.CENTER);
            //tabFrm.remove(panAsiDiaPagExt);  //No mostrar Tab Asiento de Diario: Pago de Impuestos. No se muestra porque es un asiento nulo.
            
            //***********************************************************************
            pack();
            
            //Asiento de diario: Transferencia Bancaria
            objAsiDiaTraBan.setEditable(false);
            panAsiDiaTraBan.add(objAsiDiaTraBan,java.awt.BorderLayout.CENTER);

            //Metodo para agregar o eliminar lineas con enter y con escape
            agregarDocLis();
            objUti.desactivarCom(this);
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConPrvExt();
            configurarVenConCtaBan();
            configurarVenConPedImp();
            configurarVenConForPag();
            
            //Configurar los JTables.
            configurarTblDat();   
            configurarTblForPag();
            
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
        
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");  
            vecCab.add(INT_TBL_DAT_COD_EMP_PED,"Cód.Emp."); 
            vecCab.add(INT_TBL_DAT_COD_LOC_PED,"Cód.Loc."); 
            vecCab.add(INT_TBL_DAT_COD_TIPDOC_PED,"Cód.Tip.Doc."); 
            vecCab.add(INT_TBL_DAT_COD_DOC_PED,"Cód.Doc."); 
            vecCab.add(INT_TBL_DAT_NUM_PED_IMP,"Pedido"); 
            vecCab.add(INT_TBL_DAT_BUT_PED,"");
            vecCab.add(INT_TBL_DAT_COD_EMP_IMP,"Cód.Emp.Imp."); 
            vecCab.add(INT_TBL_DAT_COD_REG_CAR,"Cód.Reg.Car."); 
            vecCab.add(INT_TBL_DAT_COD_CAR_PAG,"Cód.Car."); 
            vecCab.add(INT_TBL_DAT_NOM_CAR_PAG,"Cargo"); 
            vecCab.add(INT_TBL_DAT_BUT_CAR_PAG,""); 
            vecCab.add(INT_TBL_DAT_COD_INS_PED,"Cód.Ins."); 
            vecCab.add(INT_TBL_DAT_NOM_INS_PED,"Instancia");
            vecCab.add(INT_TBL_DAT_VAL_CAR_PAG_TOT,"Val.Car.Pag.Ped.");
            vecCab.add(INT_TBL_DAT_VAL_PAG_EXT,"Valor a Pagar");
            vecCab.add(INT_TBL_DAT_COD_CTA_CAR_PAG,"Cód.Cta.Car.Pag.");
            
            objTblMod = new ZafTblMod(); 
            objTblMod.setHeader(vecCab); 

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod); 
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true); 
            tblDat.getTableHeader().setReorderingAllowed(false); 
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat); 

            //Configurar JTable: Editor de bósqueda.
            objTblBus = new ZafTblBus(tblDat); 

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);    
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_IMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_PED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_IMP).setPreferredWidth(30);  
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_CAR).setPreferredWidth(65);     
            tcmAux.getColumn(INT_TBL_DAT_COD_CAR_PAG).setPreferredWidth(65);     
            tcmAux.getColumn(INT_TBL_DAT_NOM_CAR_PAG).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CAR_PAG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_INS_PED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_INS_PED).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_TOT).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_EXT).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA_CAR_PAG).setPreferredWidth(60);
                      
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_PED, tblDat);            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIPDOC_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_PED, tblDat);   
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_CAR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CAR_PAG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_INS_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_CAR_PAG, tblDat);
                        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_EXT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_PED);            
            //vecAux.add("" + INT_TBL_DAT_NOM_CAR_PAG);
            //vecAux.add("" + INT_TBL_DAT_BUT_CAR_PAG);
            vecAux.add("" + INT_TBL_DAT_VAL_PAG_EXT); 
            objTblMod.setColumnasEditables(vecAux); 
            vecAux=null;

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_VAL_PAG_EXT);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;            
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_CAR_PAG_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_PAG_EXT, objTblMod.INT_COL_DBL, new Integer(0), null);

            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_PED).setCellRenderer(objTblCelRenBut);                    
            tcmAux.getColumn(INT_TBL_DAT_BUT_CAR_PAG).setCellRenderer(objTblCelRenBut); 
            objTblCelRenBut=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_EXT).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strCarISD="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Ingrese el campo tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtCodPrvExt.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Ingrese el campo Proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        txtNomPrvExt.requestFocus();
                    }   
                    else if (txtCodCtaBan.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Cuenta Bancaria no ha sido ingresado.<BR>Ingrese el campo Cuenta Bancaria y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        txtNomBanCtaBan.requestFocus();
                    }                       

                    /*Valida que no se pueda ingresa valor de ISD, debe calcularse en base del valor de la factura*/
                    strCarISD=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CAR_PAG));
                    if (strCarISD.equals("22")){//Valor de la factura se edita
                        objTblCelEdiTxt.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxt.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblEdi.seleccionarCelda((intFil+2), INT_TBL_DAT_VAL_PAG_EXT);
                    calcularTot();
                }
            });

            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Pedidos de Importación.*/">
            int intColVen[]=new int[5];            
            intColVen[0]=1; //co_emp
            intColVen[1]=2; //co_loc
            intColVen[2]=3; //co_tipDoc
            intColVen[3]=4; //co_doc
            intColVen[4]=6; //tx_numDoc2
            int intColTbl[]=new int[5];            
            intColTbl[0]=INT_TBL_DAT_COD_EMP_PED;
            intColTbl[1]=INT_TBL_DAT_COD_LOC_PED;
            intColTbl[2]=INT_TBL_DAT_COD_TIPDOC_PED;
            intColTbl[3]=INT_TBL_DAT_COD_DOC_PED;
            intColTbl[4]=INT_TBL_DAT_NUM_PED_IMP;

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoPedImp=new ZafTblCelEdiTxtVco(tblDat, vcoPedImp, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_IMP).setCellEditor(objTblCelEdiTxtVcoPedImp);
            objTblCelEdiTxtVcoPedImp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCodEmp=-1, intCodLoc=-1, intCodTipDoc=-1, intCodDoc=-1, intInsPed=-1;
                BigDecimal bgdCan = new BigDecimal("1"); 
                String strCarISD="";
                String strSQLFil="";  
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Para agregar detalle ingrese el campo tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoPedImp.setCancelarEdicion(true);
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtCodPrvExt.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un pedido ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoPedImp.setCancelarEdicion(true); 
                        txtCodPrvExt.requestFocus();
                    }
                    else if (txtCodCtaBan.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Cuenta Bancaria no ha sido ingresado.<BR>Ingrese el campo Cuenta Bancaria y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        txtNomBanCtaBan.requestFocus();
                    }                            
                    
                    strCarISD=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CAR_PAG));
                    if (strCarISD.equals("23")){ /* Cargo ISD: No debe editarse. */
                        objTblCelEdiTxtVcoPedImp.setCancelarEdicion(true);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoPedImp.limpiar();
                    vcoPedImp.setCampoBusqueda(5);
                    vcoPedImp.setCriterio1(11); 
                    strSQLFil="";    
                    if(txtCodPrvExt.getText().length()>0) {
                        strSQLFil+=" AND y.co_Exp = "+txtCodPrvExt.getText();   
                    } 

                    if(intCodEmpDxP>0) {
                        strSQLFil+=" AND y.co_Imp = "+intCodEmpDxP;   
                    }                     
                    strSQLFil+=" ORDER BY y.fe_doc, y.tx_numPed";    
                    vcoPedImp.setCondicionesSQL(strSQLFil);    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoPedImp.isConsultaAceptada()) {
                        intCodEmp=Integer.parseInt(vcoPedImp.getValueAt(1));
                        intCodLoc=Integer.parseInt(vcoPedImp.getValueAt(2));
                        intCodTipDoc=Integer.parseInt(vcoPedImp.getValueAt(3));
                        intCodDoc=Integer.parseInt(vcoPedImp.getValueAt(4));
                        intInsPed=Integer.parseInt(vcoPedImp.getValueAt(7));                        
                        cargarDatPedIns(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intInsPed, intFil);
                        if(validarMismoImportadorPedido(intFil)){
                            cargarForPagPre(intCodEmpDxP); /* Cargar forma de pago predeterminada. */
                        }
                    }
                }
            });
            //Búsqueda por botón.
            objTblCelEdiButVcoPedImp=new ZafTblCelEdiButVco(tblDat, vcoPedImp, intColVen, intColTbl); 
            tcmAux.getColumn(INT_TBL_DAT_BUT_PED).setCellEditor(objTblCelEdiButVcoPedImp);
            objTblCelEdiButVcoPedImp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCodEmp=-1, intCodLoc=-1, intCodTipDoc=-1, intCodDoc=-1, intInsPed=-1;
                BigDecimal bgdCan = new BigDecimal("1");
                String strCarISD="";
                String strSQLFil="";    
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Para agregar detalle ingrese el campo tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoPedImp.setCancelarEdicion(true); 
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtCodPrvExt.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un pedido ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoPedImp.setCancelarEdicion(true); 
                        txtCodPrvExt.requestFocus();
                    }
                                       
                    strCarISD=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CAR_PAG));
                    if (strCarISD.equals("23")) { /* Cargo ISD: No debe editarse. */
                        objTblCelEdiButVcoPedImp.setCancelarEdicion(true);
                    }
                    if (strCarISD.equals("25")) { /* Cargo ISD: No debe editarse. */
                        objTblCelEdiButVcoPedImp.setCancelarEdicion(true);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoPedImp.limpiar();
                    vcoPedImp.setCampoBusqueda(5);
                    vcoPedImp.setCriterio1(11); 
                    strSQLFil="";    
                    if(txtCodPrvExt.getText().length()>0) {
                        strSQLFil+=" AND y.co_Exp = "+txtCodPrvExt.getText();   
                    } 
                    if(intCodEmpDxP>0) {
                        strSQLFil+=" AND y.co_Imp = "+intCodEmpDxP;   
                    }                       
                    strSQLFil+=" ORDER BY y.fe_doc, y.tx_numPed";    
                    vcoPedImp.setCondicionesSQL(strSQLFil);    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoPedImp.isConsultaAceptada()){ 
                        intCodEmp=Integer.parseInt(vcoPedImp.getValueAt(1));
                        intCodLoc=Integer.parseInt(vcoPedImp.getValueAt(2));
                        intCodTipDoc=Integer.parseInt(vcoPedImp.getValueAt(3));
                        intCodDoc=Integer.parseInt(vcoPedImp.getValueAt(4));
                        intInsPed=Integer.parseInt(vcoPedImp.getValueAt(7));
                        cargarDatPedIns(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intInsPed, intFil); 
                        if(validarMismoImportadorPedido(intFil)){
                            cargarForPagPre(intCodEmpDxP); /* Cargar forma de pago predeterminada. */
                        }
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            //</editor-fold>
            
            //Libero los objetos auxiliares.
            tcmAux = null;
            
            setEditable(false);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {                   
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP_PED:
                    strMsg="Código de empresa";                  
                    break;   
                case INT_TBL_DAT_COD_LOC_PED:
                    strMsg="Código de local";                  
                    break;       
                case INT_TBL_DAT_COD_TIPDOC_PED:
                    strMsg="Código de tipo de documento";                  
                    break;      
                case INT_TBL_DAT_COD_DOC_PED:
                    strMsg="Código de documento";                  
                    break;        
                case INT_TBL_DAT_NUM_PED_IMP:
                    strMsg="Número de pedido";                  
                    break;   
                case INT_TBL_DAT_BUT_PED:
                    strMsg="Pedido";                  
                    break;   
                case INT_TBL_DAT_COD_EMP_IMP:
                    strMsg="Código de Empresa Importadora";                  
                    break;                            
                case INT_TBL_DAT_COD_REG_CAR:
                    strMsg="Código de registro del cargo";                  
                    break;
                case INT_TBL_DAT_COD_CAR_PAG:
                    strMsg="Código de cargo";
                    break;                       
                case INT_TBL_DAT_NOM_CAR_PAG:
                    strMsg="Nombre del cargo";
                    break;
                case INT_TBL_DAT_BUT_CAR_PAG:
                    strMsg="Cargo";
                    break;
                case INT_TBL_DAT_COD_INS_PED:
                    strMsg="Código de Instancia del pedido";
                    break;    
                case INT_TBL_DAT_NOM_INS_PED:
                    strMsg="Instancia del pedido";
                    break;
                case INT_TBL_DAT_VAL_CAR_PAG_TOT:
                    strMsg="Valor a pagar";
                    break;
                case INT_TBL_DAT_VAL_PAG_EXT:
                    strMsg="Valor a pagar";
                    break;
                case INT_TBL_DAT_COD_CTA_CAR_PAG:
                    strMsg="Código de cuenta de cargo a pagar";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    } 
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblForPag()
    {
        boolean blnRes=true;
        try
        {   
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(13);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FOR_PAG_DAT_LIN,"");
            vecCab.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,"Día.Cré.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,"Cód.Tip.Ret.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,"Tip.Ret.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,"");
            vecCab.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,"Tipo de retención");
            vecCab.add(INT_TBL_FOR_PAG_DAT_POR_RET,"%Ret.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_APL,"Aplicado");
            vecCab.add(INT_TBL_FOR_PAG_DAT_EST_APL,"Aplicado");
            vecCab.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,"Bas.Imp.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_COD_SRI,"Cód.Sri.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_MON_PAG,"Monto a pagar");

            objTblModForPag=new ZafTblMod();
            objTblModForPag.setHeader(vecCab);

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            //objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_BAS_IMP, objTblModForPag.INT_COL_DBL, new Integer(0), null);
            //objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_MON_PAG, objTblModForPag.INT_COL_DBL, new Integer(0), null);
            //objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_POR_RET, objTblModForPag.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblForPag.setModel(objTblModForPag);
            //Configurar JTable: Establecer tipo de seleccián.
            tblForPag.setRowSelectionAllowed(true);
            tblForPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblForPag.getColumnModel();
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DIA_CRE).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_FEC_VEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_POR_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_APL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_EST_APL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BAS_IMP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_SRI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_MON_PAG).setPreferredWidth(130);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET, tblForPag);
            //objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_POR_RET, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_APL, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_EST_APL, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_COD_SRI, tblForPag);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setResizable(false);

            //tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_EST_AUT_PAG).setResizable(false);
            //tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_CTA_PAG).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblForPag.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblForPag);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenButForPag=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET).setCellRenderer(objTblCelRenButForPag);
            objTblCelRenButForPag=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_SRI).setCellRenderer(objTblCelRenLbl);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=null;
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BAS_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_MON_PAG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Inicializa el arrayList de la forma de pago.
            arlDatDiaPag_ForPag=new ArrayList();
            
            txtNumForPag.setVisible(false);
            txtTipForPag.setVisible(false);      
            
            txtForPagDif.setEnabled(false);
            txtForPagDif.setBackground(objParSis.getColorCamposSistema());

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e) {
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
    class ZafDocLis implements javax.swing.event.DocumentListener {
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
        
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtValCar.getDocument().addDocumentListener(objDocLis);
        txtValCom.getDocument().addDocumentListener(objDocLis);
        txtValInt.getDocument().addDocumentListener(objDocLis);
    }
    
    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        else 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
    }    
        
    public void noEditable(boolean blnEditable)
    {
        txtCodDoc.setEditable(blnEditable);  
        txaObs2.setEditable(blnEditable);  
        txtValDoc.setEditable(blnEditable);
        txtValDoc.setBackground(Color.WHITE);
//        txtValCar.setEditable(blnEditable);
//        txtValCar.setBackground(Color.WHITE);
//        txtValCom.setEditable(blnEditable);
//        txtValCom.setBackground(Color.WHITE);
        txtValInt.setEditable(blnEditable);
        txtValInt.setBackground(Color.WHITE);
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("325");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT distinct a.co_tipdoc, a.tx_descor, a.tx_deslar, a.ne_ultDoc, a.tx_natDoc ";
                strSQL+=" FROM tbr_tipDocPrg AS a1 LEFT OUTER JOIN tbm_cabTipDoc as a";
                strSQL+=" ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc)" ;
                strSQL+=" WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() ;
                strSQL+=" AND a1.co_loc = " + objParSis.getCodigoLocal() ;
                strSQL+=" AND a1.co_mnu = " + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a.tx_desCor";
            }
            else 
            {     
                strSQL ="";
                strSQL+=" SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a.ne_ultDoc, a.tx_natDoc ";
                strSQL+=" FROM tbr_tipDocUsr AS a1 INNER JOIN  tbm_cabTipDoc AS a";
                strSQL+=" ON (a.co_emp=a1.co_Emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a.tx_desCor";
            }
           
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=4;
            intColOcu[1]=5;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos".
     */
    private boolean configurarVenConPedImp()
    {
        boolean blnRes=true;
        Connection conLoc;
        String strSQLVcoPedImp="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.fe_doc");
            arlCam.add("a1.tx_numPed");
            arlCam.add("a1.st_ins");
            arlCam.add("a1.tx_insPedImp");
            arlCam.add("a1.co_imp");
            arlCam.add("a1.tx_nomEmpImp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Fec.Doc.");
            arlAli.add("Pedido");
            arlAli.add("Cód.Ins.");
            arlAli.add("Instancia");
            arlAli.add("Cód.Imp.");
            arlAli.add("Importador");            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("45");
            arlAncCol.add("45");
            arlAncCol.add("45");
            arlAncCol.add("45");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("50");
            arlAncCol.add("100");
            
            //Armar la sentencia SQL.
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null) { 
                objZafImp.getUltimaInstanciaPedidos(conLoc);  
                strSQLVcoPedImp="";
                strSQLVcoPedImp+=" SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y.co_Exp, y.co_imp, y.tx_nomEmpImp";
                strSQLVcoPedImp+="      , y.ne_numDoc, y.tx_numPed, y.st_ins, y.tx_insPedImp, y.st_notPedLis, y.co_cta";
                strSQLVcoPedImp+=" FROM( ";
                strSQLVcoPedImp+="   SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.fe_doc, x.co_Exp, x.co_imp, x.tx_nomEmpImp";
                strSQLVcoPedImp+="        , x.ne_numDoc, x.tx_numPed, x.st_ins, x.tx_insPedImp, x.st_notPedLis, x.co_cta";
                strSQLVcoPedImp+="   FROM ( "+objZafImp.getSQLUltimaInstanciaPedido()+" ) AS x";
                strSQLVcoPedImp+="   GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.fe_doc, x.co_Exp, x.tx_nomEmpImp";
                strSQLVcoPedImp+="          , x.co_imp, x.ne_numDoc, x.tx_numPed, x.st_ins, x.tx_insPedImp, x.st_notPedLis, x.co_cta";
                strSQLVcoPedImp+=" ) AS y";
                strSQLVcoPedImp+=" WHERE y.st_notPedLis='N' AND y.co_cta IS NOT NULL";
                System.out.println("Consulta de Pedidos: "+strSQLVcoPedImp);
                conLoc.close(); 
                conLoc=null;
            }

            //Ocultar columnas.
            int intColOcu[]=new int[7];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            intColOcu[4]=7;
            intColOcu[5]=9;
            intColOcu[6]=10;
            
//            if(objParSis.getCodigoUsuario()==1){
//                vcoPedImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQLVcoPedImp, arlCam, arlAli, arlAncCol);
//            }
//            else {
                vcoPedImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQLVcoPedImp, arlCam, arlAli, arlAncCol,intColOcu);
//            }
            
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPedImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPedImp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoPedImp.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            vcoPedImp.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
            vcoPedImp.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoPedImp.setConfiguracionColumna(7, javax.swing.JLabel.RIGHT);
            vcoPedImp.setConfiguracionColumna(9, javax.swing.JLabel.RIGHT);
            vcoPedImp.setCampoBusqueda(4);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores del exterior".
     */
    private boolean configurarVenConPrvExt()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nomExp");
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
            strSQL+=" SELECT a1.co_Exp, a1.tx_nom AS tx_nomExp";
            strSQL+=" FROM tbm_expImp as a1";
            strSQL+=" WHERE st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";

            vcoPrvExt=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores del exterior", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPrvExt.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Cuentas Bancarias".
     */
    private boolean configurarVenConCtaBan()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_Emp");
            arlCam.add("a1.tx_nomEmpCta");
            arlCam.add("a1.co_ctaBan");
            arlCam.add("a1.tx_desLarBco");
            arlCam.add("a1.tx_numCtaBan");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Empresa");
            arlAli.add("Cód.Cta.Ban.");
            arlAli.add("Banco");
            arlAli.add("Núm.Cta.Ban.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("60");
            arlAncCol.add("200");
            arlAncCol.add("110");
            
            //Armar sentencia SQL
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a4.tx_nom AS tx_nomEmpCta, a2.tx_desLar AS tx_desLarBco";
            strSQL+="      , a1.co_ctaBan, a1.tx_numCtaBan, a1.co_ban";
            strSQL+=" FROM";
            strSQL+=" (  tbm_ctaban AS a1 INNER JOIN tbm_var AS a2 ON a1.co_ban=a2.co_reg";
            strSQL+="    INNER JOIN tbm_expImp AS a3 ON a1.co_ctaExp=a3.co_exp";
            strSQL+=" )";
            strSQL+=" INNER JOIN tbm_emp AS a4 ON a1.co_Emp=a4.co_emp";
            strSQL+=" WHERE a1.st_reg NOT IN('E')";
            strSQL+=" AND a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
            if(!txtCodPrvExt.getText().equals("")){
                strSQL+=" AND a3.co_exp="+txtCodPrvExt.getText(); 
            }
            //strSQL+=" ORDER BY a2.tx_desLar, a1.co_Emp, a1.co_ctaBan"; 
            strSQL+=" ORDER BY a1.co_Emp, a2.tx_desLar, a1.co_ctaBan"; 
            //System.out.println("configurarVenConCtaBan: "+strSQL);
           
            vcoCtaBan=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas bancarias", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCtaBan.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCtaBan.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
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
     * mostrar las "Formas de Pagos".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.co_forPag");
            arlCam.add("a8.tx_desForPag");
            arlCam.add("a8.ne_numPag");
            arlCam.add("a8.ne_tipForPag");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            arlAli.add("Núm.Pag.");
            arlAli.add("Tipo de pago");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");
            arlAncCol.add("150");
            arlAncCol.add("80");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a.co_forPag, a.tx_des AS tx_desForPag, a.ne_numPag, a.ne_tipForPag";
            strSQL+=" FROM tbm_cabForPag AS a";
            strSQL+=" WHERE a.st_reg NOT IN('E','I')";
            strSQL+=" AND ( (a.co_emp=1 AND a.co_forPag=109 ) OR ( a.co_emp=2 AND a.co_forPag=127 )  OR (a.co_emp=4 AND a.co_forPag=107 ) )"; /* Mostrar sólo transferencias. */
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;

            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de forma de pago", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
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
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //Se eliminan los datos del proveedor
                        txtCodPrvExt.setText("");
                        txtNomPrvExt.setText("");
                        chkMosParRes.setSelected(false);
                        txtValDoc.setText("");
                        txtValCar.setText("");
                        //Número de documento
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //Se eliminan los datos del proveedor
                        txtCodPrvExt.setText("");
                        txtNomPrvExt.setText(""); 
                        chkMosParRes.setSelected(false);
                        txtValDoc.setText("");    
                        txtValCar.setText("");
                        //Número de documento
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //Se eliminan los datos del proveedor
                            txtCodPrvExt.setText("");
                            txtNomPrvExt.setText("");    
                            chkMosParRes.setSelected(false);
                            txtValDoc.setText("");   
                            txtValCar.setText("");

                            //Número de documento
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //Se eliminan los datos del proveedor
                        txtCodPrvExt.setText("");
                        txtNomPrvExt.setText("");       
                        chkMosParRes.setSelected(false);
                        txtValDoc.setText("");                        
                        txtValCar.setText("");
                        //Número de documento
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //Se eliminan los datos del proveedor
                            txtCodPrvExt.setText("");
                            txtNomPrvExt.setText("");    
                            chkMosParRes.setSelected(false);
                            txtValDoc.setText("");                        
                            txtValCar.setText("");
                            //Número de documento
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
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
    
    private boolean mostrarVenConPrvExt(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrvExt.setCampoBusqueda(2);
                    vcoPrvExt.show();
                    if (vcoPrvExt.getSelectedButton()==vcoPrvExt.INT_BUT_ACE)
                    {
                        txtCodPrvExt.setText(vcoPrvExt.getValueAt(1));
                        txtNomPrvExt.setText(vcoPrvExt.getValueAt(2));   
                        txtCodCtaBan.setText("");
                        txtCodEmpCtaBan.setText("");
                        txtNomBanCtaBan.setText("");
                        txtNumCtaBan.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código de Proveedor".
                    if (vcoPrvExt.buscar("a1.co_exp", txtCodPrvExt.getText()))
                    {
                        txtCodPrvExt.setText(vcoPrvExt.getValueAt(1));
                        txtNomPrvExt.setText(vcoPrvExt.getValueAt(2));
                        txtCodCtaBan.setText("");
                        txtCodEmpCtaBan.setText("");
                        txtNomBanCtaBan.setText("");
                        txtNumCtaBan.setText("");
                    }
                    else
                    {
                        vcoPrvExt.setCampoBusqueda(0);
                        vcoPrvExt.setCriterio1(11);
                        vcoPrvExt.cargarDatos();
                        vcoPrvExt.show();
                        if (vcoPrvExt.getSelectedButton()==vcoPrvExt.INT_BUT_ACE)
                        {
                            txtCodPrvExt.setText(vcoPrvExt.getValueAt(1));
                            txtNomPrvExt.setText(vcoPrvExt.getValueAt(2));
                            txtCodCtaBan.setText("");
                            txtCodEmpCtaBan.setText("");
                            txtNomBanCtaBan.setText("");
                            txtNumCtaBan.setText("");                            
                        }
                        else
                        {
                            txtCodPrvExt.setText(strCodPrvExt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre de Proveedor del exterior".
                    if (vcoPrvExt.buscar("a1.tx_nomExp", txtNomPrvExt.getText()))
                    {
                        txtCodPrvExt.setText(vcoPrvExt.getValueAt(1));
                        txtNomPrvExt.setText(vcoPrvExt.getValueAt(2));
                        txtCodCtaBan.setText("");
                        txtCodEmpCtaBan.setText("");
                        txtNomBanCtaBan.setText("");
                        txtNumCtaBan.setText("");                        
                    }
                    else
                    {
                        vcoPrvExt.setCampoBusqueda(1);
                        vcoPrvExt.setCriterio1(11);
                        vcoPrvExt.cargarDatos();
                        vcoPrvExt.show();
                        if (vcoPrvExt.getSelectedButton()==vcoPrvExt.INT_BUT_ACE)
                        {
                            txtCodPrvExt.setText(vcoPrvExt.getValueAt(1));
                            txtNomPrvExt.setText(vcoPrvExt.getValueAt(2));
                            txtCodCtaBan.setText("");
                            txtCodEmpCtaBan.setText("");
                            txtNomBanCtaBan.setText("");
                            txtNumCtaBan.setText("");                            
                        }
                        else
                        {
                            txtNomPrvExt.setText(strNomPrvExt);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCtaBan(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que se pueda seleccionar el "Cuenta Bancaria" sólo si se seleccionó el "Proveedor al exterior".
            if (txtCodPrvExt.getText().equals("")){
                txtCodCtaBan.setText("");
                txtCodEmpCtaBan.setText("");
                txtNomBanCtaBan.setText("");
                txtNumCtaBan.setText("");
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Proveedor del exterior</FONT>.<BR>Escriba o seleccione un proveedor y vuelva a intentarlo.</HTML>");
                txtCodPrvExt.requestFocus();
            }
            else{
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCtaBan.setCampoBusqueda(3);
                        vcoCtaBan.setVisible(true);
                        if (vcoCtaBan.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                            strCodEmpDxP=vcoCtaBan.getValueAt(1);
                            lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                            txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                            txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                            txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                            txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));
                            
                            //Limpiar "Detalle"
                            objTblMod.removeAllRows();    
                            //Limpiar "Forma de Pago"
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                            objTblModForPag.removeAllRows();                
                            //Limpiar "Diario: DxP"
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(false);
                        }
                        break;
                    case 1: //Búsqueda directa por "Descripción corta".
                        if (vcoCtaBan.buscar("a1.co_ctaBan", txtCodCtaBan.getText()))
                        {
                            intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                            strCodEmpDxP=vcoCtaBan.getValueAt(1);
                            lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                            txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                            txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                            txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                            txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));
                            
                            //Limpiar "Detalle"
                            objTblMod.removeAllRows();    
                            //Limpiar "Forma de Pago"
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                            objTblModForPag.removeAllRows();                
                            //Limpiar "Diario: DxP"
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(false);                            
                        }
                        else
                        {
                            vcoCtaBan.setCampoBusqueda(2);
                            vcoCtaBan.setCriterio1(11);
                            vcoCtaBan.cargarDatos();
                            vcoCtaBan.setVisible(true);
                            if (vcoCtaBan.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                            {
                                intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                                strCodEmpDxP=vcoCtaBan.getValueAt(1);
                                lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                                txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                                txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                                txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                                txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));
                                
                                //Limpiar "Detalle"
                                objTblMod.removeAllRows();    
                                //Limpiar "Forma de Pago"
                                txtCodForPag.setText("");
                                txtNomForPag.setText("");
                                txtNumForPag.setText("");
                                txtTipForPag.setText("");
                                objTblModForPag.removeAllRows();                
                                //Limpiar "Diario: DxP"
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(false);                                
                            }
                            else
                            {
                                txtCodCtaBan.setText(strNumCtaBan);
                            }
                        }
                        break;
                    case 2: //Búsqueda directa por "Número de Cuenta Bancaria".
                        if (vcoCtaBan.buscar("a1.tx_numCtaBan", txtNumCtaBan.getText()))
                        {
                            intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                            strCodEmpDxP=vcoCtaBan.getValueAt(1);
                            lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                            txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                            txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                            txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                            txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));
                            
                            //Limpiar "Detalle"
                            objTblMod.removeAllRows();    
                            //Limpiar "Forma de Pago"
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                            objTblModForPag.removeAllRows();                
                            //Limpiar "Diario: DxP"
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(false);                            
                        }
                        else
                        {
                            vcoCtaBan.setCampoBusqueda(4);
                            vcoCtaBan.setCriterio1(11);
                            vcoCtaBan.cargarDatos();
                            vcoCtaBan.setVisible(true);
                            if (vcoCtaBan.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                            {
                                intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                                strCodEmpDxP=vcoCtaBan.getValueAt(1);
                                lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                                txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                                txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                                txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                                txtNumCtaBan.setText(vcoCtaBan.getValueAt(5)); 
                                
                                //Limpiar "Detalle"
                                objTblMod.removeAllRows();    
                                //Limpiar "Forma de Pago"
                                txtCodForPag.setText("");
                                txtNomForPag.setText("");
                                txtNumForPag.setText("");
                                txtTipForPag.setText("");
                                objTblModForPag.removeAllRows();                
                                //Limpiar "Diario: DxP"
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(false);                                
                            }
                            else
                            {
                                txtNumCtaBan.setText(strNumCtaBan);
                            }
                        }
                        break;
                    case 3: //Búsqueda directa por "Banco de Cuenta Bancaria".
                        if (vcoCtaBan.buscar("a1.tx_desLarBco", txtNomBanCtaBan.getText()))
                        {
                            intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                            strCodEmpDxP=vcoCtaBan.getValueAt(1);
                            lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                            txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                            txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                            txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                            txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));
                            
                            //Limpiar "Detalle"
                            objTblMod.removeAllRows();    
                            //Limpiar "Forma de Pago"
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                            objTblModForPag.removeAllRows();                
                            //Limpiar "Diario: DxP"
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(false);                            
                        }
                        else
                        {
                            vcoCtaBan.setCampoBusqueda(3);
                            vcoCtaBan.setCriterio1(11);
                            vcoCtaBan.cargarDatos();
                            vcoCtaBan.setVisible(true);
                            if (vcoCtaBan.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                            {
                                intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                                strCodEmpDxP=vcoCtaBan.getValueAt(1);
                                lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                                txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                                txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                                txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                                txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));    
                                
                                //Limpiar "Detalle"
                                objTblMod.removeAllRows();    
                                //Limpiar "Forma de Pago"
                                txtCodForPag.setText("");
                                txtNomForPag.setText("");
                                txtNumForPag.setText("");
                                txtTipForPag.setText("");
                                objTblModForPag.removeAllRows();                
                                //Limpiar "Diario: DxP"
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(false);                                
                            }
                            else
                            {
                                txtNomBanCtaBan.setText(strNomBanCtaBan);
                            }
                        }
                        break;    
                    case 4: //Búsqueda directa por "Código de empresa de cuenta bancaria".
                        if (vcoCtaBan.buscar("a1.co_Emp", txtCodEmpCtaBan.getText()))
                        {
                            intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                            strCodEmpDxP=vcoCtaBan.getValueAt(1);
                            lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                            txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                            txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                            txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                            txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));
                            
                            //Limpiar "Detalle"
                            objTblMod.removeAllRows();    
                            //Limpiar "Forma de Pago"
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                            objTblModForPag.removeAllRows();                
                            //Limpiar "Diario: DxP"
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(false);                            
                        }
                        else
                        {
                            vcoCtaBan.setCampoBusqueda(0);
                            vcoCtaBan.setCriterio1(11);
                            vcoCtaBan.cargarDatos();
                            vcoCtaBan.setVisible(true);
                            if (vcoCtaBan.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                            {
                                intCodEmpDxP=Integer.parseInt(vcoCtaBan.getValueAt(1));
                                strCodEmpDxP=vcoCtaBan.getValueAt(1);
                                lblNomEmpImp.setText("***"+vcoCtaBan.getValueAt(2));
                                txtCodCtaBan.setText(vcoCtaBan.getValueAt(3));
                                txtCodEmpCtaBan.setText(vcoCtaBan.getValueAt(1));
                                txtNomBanCtaBan.setText(vcoCtaBan.getValueAt(4));
                                txtNumCtaBan.setText(vcoCtaBan.getValueAt(5));
                                
                                //Limpiar "Detalle"
                                objTblMod.removeAllRows();    
                                //Limpiar "Forma de Pago"
                                txtCodForPag.setText("");
                                txtNomForPag.setText("");
                                txtNumForPag.setText("");
                                txtTipForPag.setText("");
                                objTblModForPag.removeAllRows();                
                                //Limpiar "Diario: DxP"
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(false);                                
                            }
                            else
                            {
                                txtCodEmpCtaBan.setText(strCodEmpDxP); 
                            }
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
    
    private boolean mostrarVenConForPag(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try
        {
            if(intCodEmpDxP!=-1) 
            {
                strSQLTmp ="";
                strSQLTmp+=" AND a.co_emp=" + intCodEmpDxP + "";
                strSQLTmp+=" GROUP BY a.co_forPag, a.tx_des, a.ne_numPag, a.ne_tipForPag";
                strSQLTmp+=" ORDER BY a.tx_des";
                
                vcoForPag.setCondicionesSQL(strSQLTmp);            

                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoForPag.setCampoBusqueda(1);
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                            txtNumForPag.setText(vcoForPag.getValueAt(3));
                            txtTipForPag.setText(vcoForPag.getValueAt(4));
                            objTblModForPag.removeAllRows();
                            generarForPag();
                        }
                        break;
                    case 1: //Búsqueda directa por "Número de cuenta".
                        if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText()))
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                            txtNumForPag.setText(vcoForPag.getValueAt(3));
                            txtTipForPag.setText(vcoForPag.getValueAt(4));
                            objTblModForPag.removeAllRows();
                            generarForPag();
                        }
                        else
                        {
                            vcoForPag.setCampoBusqueda(0);
                            vcoForPag.setCriterio1(11);
                            vcoForPag.cargarDatos();
                            vcoForPag.show();
                            if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                            {
                                txtCodForPag.setText(vcoForPag.getValueAt(1));
                                txtNomForPag.setText(vcoForPag.getValueAt(2));
                                txtNumForPag.setText(vcoForPag.getValueAt(3));
                                txtTipForPag.setText(vcoForPag.getValueAt(4));
                                objTblModForPag.removeAllRows();
                                generarForPag();
                            }
                            else  {
                                txtCodForPag.setText(strCodForPag);
                            }
                        }
                        break;
                    case 2: //Búsqueda directa por "Descripción larga".
                        if (vcoForPag.buscar("a1.tx_desForPag", txtNomForPag.getText()))
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                            txtNumForPag.setText(vcoForPag.getValueAt(3));
                            txtTipForPag.setText(vcoForPag.getValueAt(4));
                            objTblModForPag.removeAllRows();
                            generarForPag();
                        }
                        else
                        {
                            vcoForPag.setCampoBusqueda(1);
                            vcoForPag.setCriterio1(11);
                            vcoForPag.cargarDatos();
                            vcoForPag.show();
                            if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                            {
                                txtCodForPag.setText(vcoForPag.getValueAt(1));
                                txtNomForPag.setText(vcoForPag.getValueAt(2));
                                txtNumForPag.setText(vcoForPag.getValueAt(3));
                                txtTipForPag.setText(vcoForPag.getValueAt(4));
                                objTblModForPag.removeAllRows();
                                generarForPag();
                            }
                            else {
                                txtNomForPag.setText(strNomForPag);
                            }
                        }
                        break;
                }
            }
            else{
                mostrarMsgInf("Se debe seleccionar un pedido previamente");
            }
        }
        catch (Exception e) { 
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
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" +  objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocPrg";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="    AND co_loc=" +  objParSis.getCodigoLocal();
                    strSQL+="    AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocUsr";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="    AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+="    AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="    AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n') {
                        txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    }
                    bgdSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?1:-1);
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
      
    private boolean getDiaPag_ForPag(){
        boolean blnRes=true;
        Connection conDiaPag;
        Statement stmDiaPag;
        ResultSet rstDiaPag;
        String strDiaPag="";
        arlDatDiaPag_ForPag.clear();
        try{
            conDiaPag=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conDiaPag!=null){
                stmDiaPag=conDiaPag.createStatement();
                strDiaPag="";
                strDiaPag+=" SELECT a.co_emp, a.co_forPag, a.tx_des AS tx_desForPag, a.ne_numPag, a.ne_tipForPag, a1.ne_diaCre";
                strDiaPag+=" FROM tbm_cabForPag AS a ";
                strDiaPag+=" INNER JOIN tbm_detForPag AS a1 ON a.co_emp=a1.co_emp AND a.co_forPag=a1.co_forPag";
                strDiaPag+=" WHERE a.st_reg NOT IN('E','I')";
                if(intCodEmpDxP!=-1) {
                    strDiaPag+=" AND a.co_emp=" + intCodEmpDxP + "";
                }
                if(txtCodForPag.getText().length()>0){
                    strDiaPag+=" AND a.co_forPag=" + txtCodForPag.getText() + "";
                }
                strDiaPag+=" GROUP BY a.co_emp, a.co_forPag, a.tx_des, a.ne_numPag, a.ne_tipForPag, a1.ne_diaCre, a1.co_reg";
                strDiaPag+=" ORDER BY a.co_forPag, a1.co_reg";
                rstDiaPag=stmDiaPag.executeQuery(strDiaPag);
                while(rstDiaPag.next()){
                    arlRegDiaPag_ForPag=new ArrayList();
                    arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_COD_EMP,     "" + rstDiaPag.getString("co_emp"));
                    arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_COD_FOR_PAG, "" + rstDiaPag.getString("co_forPag"));
                    arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_NUM_DIA_CRE, "" + rstDiaPag.getInt("ne_diaCre")==null?0:rstDiaPag.getInt("ne_diaCre"));
                    arlDatDiaPag_ForPag.add(arlRegDiaPag_ForPag);
                }
                conDiaPag.close();
                conDiaPag=null;
                stmDiaPag.close();
                stmDiaPag=null;
                rstDiaPag.close();
                rstDiaPag=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }  
    
    /**
     * Obtiene fecha de vencimiento de pago.
     * @param intValDia
     * @param fecha
     * @return 
     */
    private String getFecVenPag(int intValDia, String fecha){
        String strFecVncPag="";
        Connection conVctPag;
        Statement stmVctPag;
        ResultSet rstVctPag;
        try{
            conVctPag=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conVctPag!=null){
                stmVctPag=conVctPag.createStatement();
                strSQL="";
                strSQL+=" SELECT (CAST('" + objUti.formatearFecha(fecha, "dd/MM/yyyy", "yyyy-MM-dd") + "' AS DATE) + " + intValDia + ") AS fe_ven";
                rstVctPag=stmVctPag.executeQuery(strSQL);
                if(rstVctPag.next()){
                    strFecVncPag=rstVctPag.getString("fe_ven");
                }
                conVctPag.close();
                conVctPag=null;
                stmVctPag.close();
                stmVctPag=null;
                rstVctPag.close();
                rstVctPag=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecVncPag;
    }        
    
    /**
     * Función que calcula pagos en la pestaña "Forma de Pago" del documento por pagar.
     * @return 
     */
    private boolean generarForPag(){
        boolean blnRes=false;
        Connection conLoc;
        BigDecimal bgdTot=new BigDecimal("0");
        BigDecimal bgdValCer=new BigDecimal("0");
        BigDecimal bgdPorRet=new BigDecimal("0");
        BigDecimal bgdMonPag=new BigDecimal("0");
        BigDecimal bgdMonPagTot=new BigDecimal("0");
        BigDecimal bgdValApl=new BigDecimal("0");
        int intNumDiaForPag=Integer.parseInt(txtNumForPag.getText()==null?"0":(txtNumForPag.getText().equals("")?"0":txtNumForPag.getText()));
        int intTipDiaForPag=Integer.parseInt(txtTipForPag.getText()==null?"0":(txtTipForPag.getText().equals("")?"0":txtTipForPag.getText()));
        int intIndDiaForPag_Db=0;
        int intValDia=0;
        String strValTipApl="";
        getDiaPag_ForPag();
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){  
                if(intNumDiaForPag>0){
                    bgdTot=txtValDoc.getText()==null?bgdValCer:(txtValDoc.getText().equals("")?bgdValCer:new BigDecimal(txtValDoc.getText()));
                    for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
                        bgdPorRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                        if(bgdPorRet.compareTo(bgdValCer)>0){
                            bgdMonPag=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG).toString()));
                            bgdMonPagTot=bgdMonPagTot.add(bgdMonPag);
                        }
                    }
                    objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_INS);
                    for(int i=(objTblModForPag.getRowCountTrue()-1); i>=0; i--){
                        bgdPorRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                        strValTipApl=objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL)==null?"":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL).toString().equals("")?"":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL).toString());
                        if( (bgdPorRet.compareTo(bgdValCer)<=0) &&  (strValTipApl.equals(""))  ){
                            objTblModForPag.removeRow(i);
                        }
                    }
                    objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_EDI);
                    objTblModForPag.removeEmptyRows();
                    bgdValApl=objUti.redondearBigDecimal((bgdTot.subtract(bgdMonPagTot)), objParSis.getDecimalesMostrar());
                    bgdValApl=(bgdValApl.divide(new BigDecimal("" + intNumDiaForPag), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));
                    int intFil=-1;
                    int intInicio=0;
                    int intFinal=0;
                    if(intTipDiaForPag==1){
                        intInicio=0;
                        intFinal=intNumDiaForPag;
                    }
                    else{
                        intInicio=1;
                        intFinal=intNumDiaForPag+1;
                    }

                    intIndDiaForPag_Db=0;
                    //System.out.println("arlDatDiaPag_ForPag: "+arlDatDiaPag_ForPag);
                    for(int i=intInicio; i<intFinal; i++ ){
                        intValDia=objUti.getIntValueAt(arlDatDiaPag_ForPag, intIndDiaForPag_Db, INT_ARL_DIA_PAG_NUM_DIA_CRE);
                        objTblModForPag.insertRow();
                        intFil=(objTblModForPag.getRowCountTrue()-1);
                        objTblModForPag.setValueAt("" + intValDia, intFil, INT_TBL_FOR_PAG_DAT_DIA_CRE);
                        //Calcula la fecha de vencimiento de los pagos y retenciones
                        objTblModForPag.setValueAt(objUti.formatearFecha(getFecVenPag(intValDia,dtpFecDoc.getText()), "yyyy-MM-dd", "dd/MM/yyyy"), intFil, INT_TBL_FOR_PAG_DAT_FEC_VEN);
                        objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_POR_RET);
                        objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_BAS_IMP);
                        objTblModForPag.setValueAt(bgdValApl, intFil, INT_TBL_FOR_PAG_DAT_MON_PAG);
                        intIndDiaForPag_Db++;
                    }

                    //por si no cuadra la division de los pagos, se coloca la diferencia al ultimo registro
                    BigDecimal bgdValDif=new BigDecimal("0");
                    BigDecimal bgdValUltPag=new BigDecimal("0");
                    bgdMonPagTot=new BigDecimal("0");
                    for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
                        bgdMonPag=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG).toString()));
                        bgdMonPagTot=bgdMonPagTot.add(bgdMonPag);
                    }
                    
                    if(bgdTot.compareTo(bgdMonPagTot)!=0){
                        bgdValDif=bgdTot.subtract(bgdMonPagTot);
                        bgdValUltPag=new BigDecimal(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_MON_PAG)==null?"0":(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_MON_PAG).toString().equals("")?"0":objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_MON_PAG).toString()));
                        objTblModForPag.setValueAt((bgdValUltPag.add(bgdValDif)), (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_MON_PAG);
                    }
                    txtForPagDif.setText("" + bgdTot.subtract(bgdMonPagTot.add(bgdValDif)));
                }
                conLoc.close();
                conLoc=null;    
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }        
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblPrvExt = new javax.swing.JLabel();
        txtCodPrvExt = new javax.swing.JTextField();
        txtNomPrvExt = new javax.swing.JTextField();
        butPrvExt = new javax.swing.JButton();
        lblCtaBan = new javax.swing.JLabel();
        txtCodCtaBan = new javax.swing.JTextField();
        txtNumCtaBan = new javax.swing.JTextField();
        txtNomBanCtaBan = new javax.swing.JTextField();
        butCtaBan = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panDatCar = new javax.swing.JPanel();
        lblFecCar = new javax.swing.JLabel();
        lblValCar = new javax.swing.JLabel();
        txtValCar = new javax.swing.JTextField();
        chkMosParRes = new javax.swing.JCheckBox();
        lblNomEmpImp = new javax.swing.JLabel();
        panOtrVal = new javax.swing.JPanel();
        lblValCom = new javax.swing.JLabel();
        txtValCom = new javax.swing.JTextField();
        lblValInt = new javax.swing.JLabel();
        txtValInt = new javax.swing.JTextField();
        txtCodEmpCtaBan = new javax.swing.JTextField();
        butCorEle = new javax.swing.JButton();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panForPag = new javax.swing.JPanel();
        panFilForPag = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtNomForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        txtNumForPag = new javax.swing.JTextField();
        txtTipForPag = new javax.swing.JTextField();
        panDatForPag = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblForPag = new javax.swing.JTable();
        panPieForPag = new javax.swing.JPanel();
        lblDif = new javax.swing.JLabel();
        txtForPagDif = new javax.swing.JTextField();
        panAsiDiaPagExt = new javax.swing.JPanel();
        panAsiDiaTraBan = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabFrm.setName("General"); // NOI18N

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setPreferredSize(new java.awt.Dimension(452, 350));
        panGenDet.setLayout(new java.awt.BorderLayout());

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
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGenCab.setPreferredSize(new java.awt.Dimension(800, 165));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 125, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(115, 4, 10, 20);

        txtDesCorTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(125, 4, 70, 20);

        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(195, 4, 240, 20);

        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(435, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(457, 4, 125, 20);

        lblPrvExt.setText("Proveedor del exterior:");
        lblPrvExt.setToolTipText("");
        panGenCab.add(lblPrvExt);
        lblPrvExt.setBounds(0, 24, 125, 20);

        txtCodPrvExt.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCodPrvExt.setPreferredSize(new java.awt.Dimension(25, 20));
        txtCodPrvExt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrvExtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvExtFocusLost(evt);
            }
        });
        txtCodPrvExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvExtActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodPrvExt);
        txtCodPrvExt.setBounds(125, 24, 70, 20);

        txtNomPrvExt.setBackground(objParSis.getColorCamposObligatorios());
        txtNomPrvExt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPrvExtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrvExtFocusLost(evt);
            }
        });
        txtNomPrvExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrvExtActionPerformed(evt);
            }
        });
        panGenCab.add(txtNomPrvExt);
        txtNomPrvExt.setBounds(195, 24, 240, 20);

        butPrvExt.setText("...");
        butPrvExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvExtActionPerformed(evt);
            }
        });
        panGenCab.add(butPrvExt);
        butPrvExt.setBounds(435, 24, 20, 20);

        lblCtaBan.setText("Cuenta Bancaria:");
        panGenCab.add(lblCtaBan);
        lblCtaBan.setBounds(0, 44, 110, 20);
        panGenCab.add(txtCodCtaBan);
        txtCodCtaBan.setBounds(115, 44, 10, 20);

        txtNumCtaBan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumCtaBanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumCtaBanFocusLost(evt);
            }
        });
        txtNumCtaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumCtaBanActionPerformed(evt);
            }
        });
        panGenCab.add(txtNumCtaBan);
        txtNumCtaBan.setBounds(315, 44, 120, 20);

        txtNomBanCtaBan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBanCtaBanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBanCtaBanFocusLost(evt);
            }
        });
        txtNomBanCtaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBanCtaBanActionPerformed(evt);
            }
        });
        panGenCab.add(txtNomBanCtaBan);
        txtNomBanCtaBan.setBounds(155, 44, 160, 20);

        butCtaBan.setText("...");
        butCtaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaBanActionPerformed(evt);
            }
        });
        panGenCab.add(butCtaBan);
        butCtaBan.setBounds(435, 44, 20, 20);

        lblNumDoc.setText("Número de documento:");
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(457, 24, 125, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setMaximumSize(null);
        txtNumDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(582, 24, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setInheritsPopupMenu(false);
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(457, 44, 127, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(582, 44, 100, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setPreferredSize(new java.awt.Dimension(60, 14));
        panGenCab.add(lblValDoc);
        lblValDoc.setBounds(457, 64, 125, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtValDoc);
        txtValDoc.setBounds(582, 64, 100, 20);

        panDatCar.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Carta"));
        panDatCar.setPreferredSize(new java.awt.Dimension(100, 38));
        panDatCar.setLayout(null);

        lblFecCar.setText("Fecha de la carta:");
        lblFecCar.setPreferredSize(new java.awt.Dimension(110, 15));
        panDatCar.add(lblFecCar);
        lblFecCar.setBounds(15, 15, 125, 20);

        lblValCar.setText("Valor de la carta:");
        lblValCar.setPreferredSize(new java.awt.Dimension(60, 14));
        panDatCar.add(lblValCar);
        lblValCar.setBounds(240, 15, 110, 20);

        txtValCar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panDatCar.add(txtValCar);
        txtValCar.setBounds(340, 15, 100, 20);

        chkMosParRes.setText("Mostrar párrafo de responsabilidad");
        chkMosParRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosParResActionPerformed(evt);
            }
        });
        panDatCar.add(chkMosParRes);
        chkMosParRes.setBounds(15, 35, 230, 20);

        lblNomEmpImp.setText("***");
        panDatCar.add(lblNomEmpImp);
        lblNomEmpImp.setBounds(260, 35, 180, 20);

        panGenCab.add(panDatCar);
        panDatCar.setBounds(0, 94, 450, 60);

        panOtrVal.setBorder(javax.swing.BorderFactory.createTitledBorder("Otros Valores"));
        panOtrVal.setLayout(null);

        lblValCom.setText("Valor comisión:");
        panOtrVal.add(lblValCom);
        lblValCom.setBounds(20, 20, 90, 20);

        txtValCom.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValComFocusLost(evt);
            }
        });
        panOtrVal.add(txtValCom);
        txtValCom.setBounds(110, 20, 100, 20);

        lblValInt.setText("Valor interes:");
        panOtrVal.add(lblValInt);
        lblValInt.setBounds(20, 45, 90, 20);

        txtValInt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panOtrVal.add(txtValInt);
        txtValInt.setBounds(110, 45, 100, 20);

        panGenCab.add(panOtrVal);
        panOtrVal.setBounds(454, 85, 228, 75);

        txtCodEmpCtaBan.setToolTipText("Código de empresa de cuenta bancaria");
        txtCodEmpCtaBan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpCtaBanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpCtaBanFocusLost(evt);
            }
        });
        txtCodEmpCtaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpCtaBanActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodEmpCtaBan);
        txtCodEmpCtaBan.setBounds(125, 44, 30, 20);

        butCorEle.setText("CorEle");
        butCorEle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCorEleActionPerformed(evt);
            }
        });
        panGenCab.add(butCorEle);
        butCorEle.setBounds(380, 70, 73, 20);

        panGenDet.add(panGenCab, java.awt.BorderLayout.PAGE_START);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panForPag.setLayout(new java.awt.BorderLayout());

        panFilForPag.setPreferredSize(new java.awt.Dimension(100, 30));
        panFilForPag.setLayout(null);

        lblForPag.setText("Forma de pago:");
        lblForPag.setToolTipText("Beneficiario");
        panFilForPag.add(lblForPag);
        lblForPag.setBounds(10, 8, 90, 14);

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
        panFilForPag.add(txtCodForPag);
        txtCodForPag.setBounds(110, 4, 60, 20);

        txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomForPagActionPerformed(evt);
            }
        });
        txtNomForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusLost(evt);
            }
        });
        panFilForPag.add(txtNomForPag);
        txtNomForPag.setBounds(170, 4, 300, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panFilForPag.add(butForPag);
        butForPag.setBounds(470, 4, 20, 20);
        panFilForPag.add(txtNumForPag);
        txtNumForPag.setBounds(500, 4, 30, 20);
        panFilForPag.add(txtTipForPag);
        txtTipForPag.setBounds(540, 4, 30, 20);

        panForPag.add(panFilForPag, java.awt.BorderLayout.NORTH);

        panDatForPag.setLayout(new java.awt.BorderLayout());

        tblForPag.setModel(new javax.swing.table.DefaultTableModel(
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
        spnForPag.setViewportView(tblForPag);

        panDatForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        panForPag.add(panDatForPag, java.awt.BorderLayout.CENTER);

        panPieForPag.setPreferredSize(new java.awt.Dimension(100, 30));
        panPieForPag.setLayout(null);

        lblDif.setText("Diferencia:");
        panPieForPag.add(lblDif);
        lblDif.setBounds(490, 10, 70, 14);
        panPieForPag.add(txtForPagDif);
        txtForPagDif.setBounds(570, 6, 110, 20);

        panForPag.add(panPieForPag, java.awt.BorderLayout.PAGE_END);

        tabFrm.addTab("Forma de Pago", panForPag);

        panAsiDiaPagExt.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento Diario", panAsiDiaPagExt);

        panAsiDiaTraBan.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab(" Transferencia Bancaria", panAsiDiaTraBan);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
     
    
    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
    }//GEN-LAST:event_CerrarVentana

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus(); 
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
            {
                objTblMod.removeAllRows();          
                txtValDoc.setText("");
                txtValCar.setText("");
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();   
                objAsiDia.inicializar();
                objAsiDia.setEditable(true);
                if (txtDesCorTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesLarTipDoc.setText("");
                }
                else
                {
                    mostrarVenConTipDoc(1);
                }
            }
            else
                txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    }//GEN-LAST:event_txtNumDocFocusLost

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
            {
                objTblMod.removeAllRows();          
                txtValDoc.setText("");
                txtValCar.setText("");
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();   
                objAsiDia.inicializar();
                objAsiDia.setEditable(true);   
                if (txtDesLarTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesCorTipDoc.setText("");
                }
                else
                {
                    mostrarVenConTipDoc(2);
                }
            }
            else
                txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void chkMosParResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosParResActionPerformed
    }//GEN-LAST:event_chkMosParResActionPerformed

    private void butPrvExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvExtActionPerformed
        configurarVenConPrvExt();
        mostrarVenConPrvExt(0);
    }//GEN-LAST:event_butPrvExtActionPerformed

    private void txtNomPrvExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrvExtActionPerformed
        txtNomPrvExt.transferFocus();
    }//GEN-LAST:event_txtNomPrvExtActionPerformed

    private void txtNomPrvExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvExtFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomPrvExt.getText().equalsIgnoreCase(strNomPrvExt))
        {
            //Limpiar la "Tabla" sólo si se ha cambiado el "Proveedor".
            objTblMod.removeAllRows();     
            objAsiDia.inicializar();
            objAsiDia.setEditable(true);                 
            if (txtNomPrvExt.getText().equals(""))
            {
                txtCodPrvExt.setText("");
                txtNomPrvExt.setText("");           
            }
            else
            {
                configurarVenConPrvExt();
                mostrarVenConPrvExt(2);
            }
        }
        else
            txtNomPrvExt.setText(strNomPrvExt);
    }//GEN-LAST:event_txtNomPrvExtFocusLost

    private void txtNomPrvExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvExtFocusGained
        strNomPrvExt=txtNomPrvExt.getText();
        txtNomPrvExt.selectAll();
    }//GEN-LAST:event_txtNomPrvExtFocusGained

    private void txtCodPrvExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvExtActionPerformed
        txtCodPrvExt.transferFocus();
    }//GEN-LAST:event_txtCodPrvExtActionPerformed

    private void txtCodPrvExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvExtFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPrvExt.getText().equalsIgnoreCase(strCodPrvExt))
        {
            //Limpiar la "Tabla" sólo si se ha cambiado el "Proveedor".
            objTblMod.removeAllRows();   
            objAsiDia.inicializar();
            objAsiDia.setEditable(true);
            if (txtCodPrvExt.getText().equals(""))
            {
                txtCodPrvExt.setText("");
                txtNomPrvExt.setText("");
            }
            else
            {
                configurarVenConPrvExt();
                mostrarVenConPrvExt(1);
            }
        }
        else
            txtCodPrvExt.setText(strCodPrvExt);
    }//GEN-LAST:event_txtCodPrvExtFocusLost

    private void txtCodPrvExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvExtFocusGained
        strCodPrvExt=txtCodPrvExt.getText();
        txtCodPrvExt.selectAll();
    }//GEN-LAST:event_txtCodPrvExtFocusGained

    private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
        txtCodForPag.transferFocus();
    }//GEN-LAST:event_txtCodForPagActionPerformed

    private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
        strCodForPag=txtCodForPag.getText();
        txtCodForPag.selectAll();
    }//GEN-LAST:event_txtCodForPagFocusGained

    private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodForPag))
        {
            if (txtCodForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();
            }
            else
            {
                configurarVenConForPag();
                mostrarVenConForPag(1);
            }
        }
        else
        txtCodForPag.setText(strCodForPag);
    }//GEN-LAST:event_txtCodForPagFocusLost

    private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
        txtNomForPag.transferFocus();
    }//GEN-LAST:event_txtNomForPagActionPerformed

    private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
        strNomForPag=txtNomForPag.getText();
        txtNomForPag.selectAll();
    }//GEN-LAST:event_txtNomForPagFocusGained

    private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomForPag.getText().equalsIgnoreCase(strNomForPag))
        {
            if (txtNomForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();
            }
            else
            {
                configurarVenConForPag();
                mostrarVenConForPag(2);
            }
        }
        else
        txtNomForPag.setText(strNomForPag);
    }//GEN-LAST:event_txtNomForPagFocusLost

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        strCodForPag=txtCodForPag.getText();
        configurarVenConForPag();
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void txtNumCtaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumCtaBanActionPerformed
        txtNumCtaBan.transferFocus();
    }//GEN-LAST:event_txtNumCtaBanActionPerformed

    private void txtNomBanCtaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBanCtaBanActionPerformed
        txtNomBanCtaBan.transferFocus();
    }//GEN-LAST:event_txtNomBanCtaBanActionPerformed

    private void txtNumCtaBanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCtaBanFocusGained
        strNumCtaBan=txtNumCtaBan.getText();
        txtNumCtaBan.selectAll();
    }//GEN-LAST:event_txtNumCtaBanFocusGained

    private void txtNomBanCtaBanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBanCtaBanFocusGained
        strNomBanCtaBan=txtNomBanCtaBan.getText();
        txtNomBanCtaBan.selectAll();
    }//GEN-LAST:event_txtNomBanCtaBanFocusGained

    private void butCtaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaBanActionPerformed
        configurarVenConCtaBan();
        mostrarVenConCtaBan(0);
    }//GEN-LAST:event_butCtaBanActionPerformed

    private void txtNumCtaBanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCtaBanFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNumCtaBan.getText().equalsIgnoreCase(strNumCtaBan))
        {
            if (txtNumCtaBan.getText().equals(""))
            {
                txtCodCtaBan.setText("");
                txtCodEmpCtaBan.setText("");
                txtNumCtaBan.setText("");
                txtNomBanCtaBan.setText("");
            }
            else
            {
                configurarVenConCtaBan();
                mostrarVenConCtaBan(2);
            }
        }
        else
            txtNumCtaBan.setText(strNumCtaBan);
    }//GEN-LAST:event_txtNumCtaBanFocusLost

    private void txtNomBanCtaBanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBanCtaBanFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomBanCtaBan.getText().equalsIgnoreCase(strNomBanCtaBan))
        {
            if (txtNomBanCtaBan.getText().equals(""))
            {
                txtCodCtaBan.setText("");
                txtCodEmpCtaBan.setText("");
                txtNomBanCtaBan.setText("");
                txtNumCtaBan.setText("");
            }
            else
            {
                configurarVenConCtaBan();
                mostrarVenConCtaBan(3);
            }
        }
        else
            txtNomBanCtaBan.setText(strNomBanCtaBan);
    }//GEN-LAST:event_txtNomBanCtaBanFocusLost

    private void txtValComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValComFocusLost
        calcularTot();
    }//GEN-LAST:event_txtValComFocusLost

    private void txtCodEmpCtaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpCtaBanActionPerformed
        txtCodEmpCtaBan.transferFocus();
    }//GEN-LAST:event_txtCodEmpCtaBanActionPerformed

    private void txtCodEmpCtaBanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpCtaBanFocusGained
        strCodEmpDxP=txtCodEmpCtaBan.getText();
        txtCodEmpCtaBan.selectAll();
    }//GEN-LAST:event_txtCodEmpCtaBanFocusGained

    private void txtCodEmpCtaBanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpCtaBanFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodEmpCtaBan.getText().equalsIgnoreCase(strCodEmpDxP))
        {
            if (txtCodEmpCtaBan.getText().equals(""))
            {
                txtCodCtaBan.setText("");
                txtCodEmpCtaBan.setText("");
                txtNomBanCtaBan.setText("");
                txtNumCtaBan.setText("");
            }
            else
            {
                configurarVenConCtaBan();
                mostrarVenConCtaBan(4);
            }
        }
        else
            txtCodEmpCtaBan.setText(strCodEmpDxP); 
    }//GEN-LAST:event_txtCodEmpCtaBanFocusLost

    private void butCorEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCorEleActionPerformed
        if(enviarCorEle()) {
            mostrarMsgInf("<HTML>Correo enviado con exito.</HTML>");
        }
    }//GEN-LAST:event_butCorEleActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCorEle;
    private javax.swing.JButton butCtaBan;
    private javax.swing.JButton butForPag;
    private javax.swing.JButton butPrvExt;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkMosParRes;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCtaBan;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblFecCar;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblNomEmpImp;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrvExt;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValCar;
    private javax.swing.JLabel lblValCom;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JLabel lblValInt;
    private javax.swing.JPanel panAsiDiaPagExt;
    private javax.swing.JPanel panAsiDiaTraBan;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panDatCar;
    private javax.swing.JPanel panDatForPag;
    private javax.swing.JPanel panFilForPag;
    private javax.swing.JPanel panForPag;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JPanel panOtrVal;
    private javax.swing.JPanel panPieForPag;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblForPag;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCtaBan;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodEmpCtaBan;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtCodPrvExt;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtForPagDif;
    private javax.swing.JTextField txtNomBanCtaBan;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtNomPrvExt;
    private javax.swing.JTextField txtNumCtaBan;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumForPag;
    private javax.swing.JTextField txtTipForPag;
    private javax.swing.JTextField txtValCar;
    private javax.swing.JTextField txtValCom;
    private javax.swing.JTextField txtValDoc;
    private javax.swing.JTextField txtValInt;
    // End of variables declaration//GEN-END:variables
    
    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /* Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". 
     * Presenta las opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    
    
    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        String strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        }
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    } 
  
    /**
     * Esta función muestra un mensaje de validación de campos.
     * @param strNomCampo 
     */
    private void mostrarMsgValCam(String strNomCampo){
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
         
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux, true))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            if(objTooBar.insertar()){  
                                if(objTooBar.afterInsertar()){  
                                    blnRes=true;
                                }
                            }                      
                        }                         
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            if(objTooBar.modificar()){  
                                if(objTooBar.afterModificar()){  
                                    blnRes=true;
                                }
                            }                      
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
    
    private void limpiarFrm()
    {
        //Cabecera
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodDoc.setText("");
        txtNumDoc.setText("");
        txtCodPrvExt.setText("");
        txtNomPrvExt.setText("");
        txtCodCtaBan.setText("");
        txtCodEmpCtaBan.setText("");
        txtNomBanCtaBan.setText("");
        txtNumCtaBan.setText("");
        dtpFecDoc.setText("");
        dtpFecCar.setText("");
        txtValCar.setText("0");
        txtValDoc.setText("0");
        txtValCom.setText("0");
        txtValInt.setText("0");
        chkMosParRes.setSelected(false);
        lblNomEmpImp.setText("***");
        //Detalle
        objTblMod.removeAllRows();    
        //Forma de Pago
        txtCodForPag.setText("");
        txtNomForPag.setText("");
        txtNumForPag.setText("");
        txtTipForPag.setText("");
        objTblModForPag.removeAllRows();                
        //Diario: DxP
        objAsiDia.inicializar();
        objAsiDia.setEditable(false);
        //Diario: Tranferencia Bancaria
        objAsiDiaTraBan.inicializar();
        objAsiDiaTraBan.setEditable(false);        
        //Pie de pagina
        txaObs1.setText("");
        txaObs2.setText("");
        
        intCodEmpDxP=-1;
        intCodLocDxP=-1;
    }       
       
    /**
     * Función que obtiene el porcentaje del interes de la factura de los pedidos
     * @return 
     */
    private BigDecimal getCargoValorFactura (int intFil){ 
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;     
        String strSqlValFac="";
        BigDecimal bgdCarValFac=BigDecimal.ZERO;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                String strInsPed=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_INS_PED)); 
                String strTblInsPed=""; 
                switch(strInsPed){
                    case "0": strTblInsPed="NotPedImp";    break;
                    case "1": strTblInsPed="PedEmbImp";    break;
                    case "2": strTblInsPed="MovInv";       break;  
                }     

                //Armar sentencia SQL que determina si existe valor de la factura.
                strSqlValFac ="";
                strSqlValFac+=" SELECT * ";
                strSqlValFac+=" FROM( ";
                strSqlValFac+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a.tx_tipCarPag, SUM(nd_valCarPag) AS nd_ValCarFac";
                strSqlValFac+="    FROM tbm_carPagImp AS a";
                strSqlValFac+="    INNER JOIN tbm_carPag"+strTblInsPed+" AS a1 ON a.co_carPag=a1.co_carPag";
                strSqlValFac+="    WHERE a.st_reg IN ('A') AND a.tx_tipCarPag IN ('V') ";
                strSqlValFac+="    AND a1.co_emp=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_EMP_PED)) ;
                strSqlValFac+="    AND a1.co_loc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_LOC_PED)) ;
                strSqlValFac+="    AND a1.co_tipDoc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_TIPDOC_PED)) ;
                strSqlValFac+="    AND a1.co_doc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_DOC_PED)) ;                    
                strSqlValFac+="    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a.tx_tipCarPag";
                strSqlValFac+=" ) AS x ";
                //System.out.println("getCargoValorFactura: "+strSqlValFac);
                rstLoc=stmLoc.executeQuery(strSqlValFac);
                if(rstLoc.next()) {
                    bgdCarValFac=new BigDecimal(rstLoc.getObject("nd_ValCarFac")==null?"0":rstLoc.getString("nd_ValCarFac")); 
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)    {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdCarValFac;
    }    
    
    /**
     * Función que obtiene el valor de interes en el cargo.
     * @return bgdValInt
     */
    private BigDecimal getCargoValorInteres(int intFil){ 
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;     
        String strSqValInt="";
        BigDecimal bgdCarValInt=BigDecimal.ZERO;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                //Armar sentencia SQL que determina si existe valor de interes en la factura
                strSqValInt ="";
                strSqValInt+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a4.co_imp, a1.co_cta, a3.nd_ValCarPag AS nd_ValCarInt, a2.tx_nom AS tx_nomCar";
                strSqValInt+=" FROM tbr_CarPagImpPlaCta AS a1";   
                strSqValInt+=" INNER JOIN tbm_carPagImp AS a2 ON a1.co_CarPag=a2.co_CarPag AND a2.st_reg IN ('A') ";
                strSqValInt+=" AND a2.co_carPag="+objPagExt.INT_COD_CAR_PAG_INT_FAC;
                strSqValInt+=" INNER JOIN tbm_carPagPedEmbImp AS a3 ON a2.co_CarPag=a3.co_CarPag ";
                strSqValInt+=" INNER JOIN (";
                strSqValInt+="    SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_imp";
                strSqValInt+="    FROM tbm_cabPedEmbImp AS a";
                strSqValInt+="    WHERE a.st_reg IN ('A') AND (a.st_doc IN('A') OR a.st_doc IS NULL )"; 
                strSqValInt+="    AND a.co_emp=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_EMP_PED)) ;
                strSqValInt+="    AND a.co_loc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_LOC_PED)) ;
                strSqValInt+="    AND a.co_tipDoc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_TIPDOC_PED)) ;
                strSqValInt+="    AND a.co_doc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_DOC_PED)) ;
                strSqValInt+="    UNION ALL";                
                strSqValInt+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_imp";
                strSqValInt+="    FROM tbm_cabMovInv AS a";
                strSqValInt+="    INNER JOIN tbm_cabPedEmbImp AS a1 ON a.co_empRelPedEmbImp=a1.co_emp AND a.co_locRelPedEmbImp=a1.co_loc AND a.co_tipDocRelPedEmbImp=a1.co_tipDoc AND a.co_docRelPedEmbImp=a1.co_doc";
                strSqValInt+="    WHERE a.st_reg IN ('A') AND a1.st_reg IN ('A') AND (a1.st_doc IN('A') OR a1.st_doc IS NULL )";	
                strSqValInt+="    AND a.co_emp=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_EMP_PED)) ;
                strSqValInt+="    AND a.co_loc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_LOC_PED)) ;
                strSqValInt+="    AND a.co_tipDoc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_TIPDOC_PED)) ;
                strSqValInt+="    AND a.co_doc=" + objUti.codificar(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_DOC_PED)) ;       
                strSqValInt+=" ) AS a4";
                strSqValInt+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc AND a1.co_emp=a4.co_imp";
                strSqValInt+=" WHERE a3.nd_ValCarPag >0";
                System.out.println("***getCargoValorInteres: "+strSqValInt);
                rstLoc=stmLoc.executeQuery(strSqValInt);
                if(rstLoc.next()){
                    bgdCarValInt=new BigDecimal(rstLoc.getObject("nd_ValCarInt")==null?"0":rstLoc.getString("nd_ValCarInt")); 
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)    {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdCarValInt;
    }       
    
    private void calcularInteres() 
    {
        BigDecimal bgdValTotInt = new BigDecimal("0"); 
        BigDecimal bgdValPagExt = new BigDecimal("0"); 
        BigDecimal bgdValCarFac = new BigDecimal("0"); 
        BigDecimal bgdValCarInt = new BigDecimal("0"); 
        BigDecimal bgdPorInt = new BigDecimal("0"); 
        try{
            for (int i=0;i<objTblMod.getRowCountTrue();i=i+INT_NUM_TOT_FIL_PED){ 
                BigDecimal bgdValIntPed=BigDecimal.ZERO; 
                bgdValPagExt=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()));
                bgdValCarFac=getCargoValorFactura(i);  
                bgdValCarInt=getCargoValorInteres(i); 
                
                if(bgdValCarInt.compareTo(BigDecimal.ZERO)>0) { 
                    bgdPorInt=(bgdValPagExt.divide(bgdValCarFac, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP));
                    bgdValIntPed= bgdValCarInt.multiply(bgdPorInt); //Valor de Intereres por Pedido
                    //Si el valor de interes calculado es mayor al total de interes establecido, se asignará el valor de interes establecido.
                    bgdValIntPed=(bgdValIntPed.compareTo(bgdValCarInt)>0? bgdValCarInt: bgdValIntPed);
                    bgdValTotInt=bgdValTotInt.add(bgdValIntPed);  //Valor Total de Interes
                    
                }
            }
            //Se actualiza el valor de interes.
            txtValInt.setText(""+objUti.redondearBigDecimal(bgdValTotInt, objParSis.getDecimalesMostrar()));
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e );
        }
    }    
    
    /*
    private void calcularISD()
    {
        BigDecimal bgdValFac = new BigDecimal("0");
        BigDecimal bgdValISD = new BigDecimal("0");
        BigDecimal bgdPorISD = new BigDecimal("0");//Porcentaje ISD
        try{
            //Obtener la fecha y hora del servidor.
            bgdPorISD=objZafImp.getPorcentajeISD(intCodEmpDxP, intCodLocDxP);
            for (int i=0;i<objTblMod.getRowCountTrue();i=i+INT_NUM_TOT_FIL_PED){ 
                bgdValFac=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()));
                bgdValISD=bgdValFac.multiply(bgdPorISD);
                objTblMod.setValueAt("" + (objUti.redondearBigDecimal(bgdValISD, objParSis.getDecimalesMostrar())), (i+1), INT_TBL_DAT_VAL_PAG_EXT); 
            }
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e );
        }
    }
*/
    
    
    private void calcularISD()
    {
        BigDecimal bgdValFacAbo = new BigDecimal("0");
        BigDecimal bgdValISDCreTri = new BigDecimal("0");
        BigDecimal bgdValISDGto = new BigDecimal("0");
        BigDecimal bgdPorISD = new BigDecimal("0"); 
        int intCodCarPag=-1;
        BigDecimal bgdValFacTot = new BigDecimal("0");
        BigDecimal bgdValISDCreTriTot = new BigDecimal("0");
        BigDecimal bgdValISDGtoTot = new BigDecimal("0");
        try{
            bgdPorISD=objZafImp.getPorcentajeISD(intCodEmpDxP, intCodLocDxP);//Porcentaje ISD
            
            for (int i=0;i<objTblMod.getRowCountTrue();i++){
                intCodCarPag=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG)==null?"":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG).toString()));
                if(intCodCarPag==22){//Valor de la factura
                    bgdValFacTot=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_TOT).toString()));
                    bgdValFacAbo=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()));
                    //ISD Crédito Tributario
                    bgdValISDCreTriTot=new BigDecimal(objTblMod.getValueAt((i+1), INT_TBL_DAT_VAL_CAR_PAG_TOT)==null?"0":(objTblMod.getValueAt((i+1), INT_TBL_DAT_VAL_CAR_PAG_TOT).toString().equals("")?"0":objTblMod.getValueAt((i+1), INT_TBL_DAT_VAL_CAR_PAG_TOT).toString()));
                    //bgdValISDCreTri=(((bgdValFacAbo.multiply(new BigDecimal("100"))).divide(bgdValFacTot, BigDecimal.ROUND_HALF_UP)).multiply(bgdValISDCreTriTot)).divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
                    bgdValISDCreTri=(((bgdValFacAbo.multiply(new BigDecimal("100"))).divide(bgdValFacTot, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_EVEN)).multiply(bgdValISDCreTriTot)).divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_EVEN);
                    
                    objTblMod.setValueAt("" + (objUti.redondearBigDecimal(bgdValISDCreTri, objParSis.getDecimalesMostrar())), (i+1), INT_TBL_DAT_VAL_PAG_EXT);
                    
                    //ISD Gasto
                    bgdValISDGtoTot=new BigDecimal(objTblMod.getValueAt((i+2), INT_TBL_DAT_VAL_CAR_PAG_TOT)==null?"0":(objTblMod.getValueAt((i+2), INT_TBL_DAT_VAL_CAR_PAG_TOT).toString().equals("")?"0":objTblMod.getValueAt((i+2), INT_TBL_DAT_VAL_CAR_PAG_TOT).toString()));
                    //bgdValISDGto=(((bgdValFacAbo.multiply(new BigDecimal("100"))).divide(bgdValFacTot, BigDecimal.ROUND_HALF_UP)).multiply(bgdValISDGtoTot)).divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
                    bgdValISDGto=(((bgdValFacAbo.multiply(new BigDecimal("100"))).divide(bgdValFacTot, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_EVEN)).multiply(bgdValISDGtoTot)).divide(new BigDecimal("100"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_EVEN);
                    objTblMod.setValueAt("" + (objUti.redondearBigDecimal(bgdValISDGto, objParSis.getDecimalesMostrar())), (i+2), INT_TBL_DAT_VAL_PAG_EXT);
                }
 
            }
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e );
        }
    }
    
    
    
    private void calcularTot()
    {
        BigDecimal bgdValPag = new BigDecimal("0");
        BigDecimal bgdValCom = new BigDecimal("0");
        BigDecimal bgdTotValDoc = new BigDecimal("0");
        try{            
            calcularISD(); 
            calcularInteres();
            for (int i=0;i<objTblMod.getRowCountTrue();i++) { 
                bgdValPag=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()));
                bgdTotValDoc=bgdTotValDoc.add(bgdValPag);
            }
            
            /* Se agrega a la sumatoria el valor de comisión. */
            bgdValCom=objUti.redondearBigDecimal(new BigDecimal(txtValCom.getText()==null?"0":(txtValCom.getText().equals("")?"0":txtValCom.getText())) , objParSis.getDecimalesMostrar());
            bgdTotValDoc=bgdTotValDoc.add(bgdValCom);
            
            txtValDoc.setText( "" + (objUti.redondearBigDecimal(bgdTotValDoc, objParSis.getDecimalesMostrar())) );
            
            generarForPag();
            generarAsiDia();
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e );
        }
    }    
    
    private boolean isCamVal()
    {
        if(txtCodTipDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Tipo de documento");
            txtCodTipDoc.requestFocus();
            return false;
        }
        
        if(txtCodPrvExt.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Proveedor del exterior");
            txtCodPrvExt.requestFocus();
            return false;
        }        
            
        if(txtCodCtaBan.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Cuenta Bancaria");
            txtCodPrvExt.requestFocus();
            return false;
        }     
        
        if(txtNumDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Número de documento");
            txtNumDoc.requestFocus();
            return false;
        }
        
        if( objUltDocPrint.getExisteDoc(Integer.parseInt( txtNumDoc.getText()) ,  Integer.parseInt(txtCodTipDoc.getText()) ) ){
            mostrarMsgInf("El número de documento ya existe.");
            txtNumDoc.requestFocus();
            txtNumDoc.selectAll();
            return false;
        }        

        if(!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha del Documento");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(!dtpFecCar.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha de la carta");
            dtpFecCar.requestFocus();
            return false;
        }        

        if (!objAsiDia.isDiarioCuadrado()){
            mostrarMsgInf("Asiento descuadrado.");
            tabFrm.setSelectedIndex(2);
            return false;                                    
        }
        
        if (!objAsiDia.isDocumentoCuadrado(txtValDoc.getText())){
            mostrarMsgInf("Asiento y Total del documento esta descuadrado.");
            tabFrm.setSelectedIndex(1);
            return false;                                                       
        }
        
        /* Validar: Forma de Pago */
        if(txtCodForPag.getText().equals("")){
            mostrarMsgInf("<HTML>El documento no tiene asociado una forma de pago.<BR>Verifique y seleccione la forma de pago y vuelva a intentarlo.</HTML>");
            txtNomForPag.selectAll();
            txtNomForPag.requestFocus();
            return false;
        }
        
        /* Verifica: Detalle del DxP. */
        objTblMod.removeEmptyRows();
        int intCodCarPag=-1;
        for(int intFil=0; intFil<objTblMod.getRowCountTrue(); intFil++){
            intCodCarPag=Integer.parseInt(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_CAR_PAG)==null?"0":(objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_CAR_PAG).toString().equals("")?"0":objTblMod.getValueAt(intFil,INT_TBL_DAT_COD_CAR_PAG).toString()));
            if(intCodCarPag==22){
                BigDecimal bgdCan=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(intFil,INT_TBL_DAT_VAL_PAG_EXT)==null)?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_PAG_EXT).toString()),objParSis.getDecimalesBaseDatos());                    
                if(bgdCan.compareTo(BigDecimal.ZERO)<=0) {
                    mostrarMsgInf("<HTML>Es obligatorio ingresar un valor en el detalle.<BR>Escriba en el campo y vuelva a intentarlo.</BR></HTML>");
                    return false; 
                }
            }

        }
               
        if(!objAsiDia.isDocumentoCuadrado(txtValDoc.getText()))               
            return false;

//        if(!validarRangoValor()) {
//            tabFrm.setSelectedIndex(0);
//            mostrarMsgInf("<HTML>El documento por pagar tiene valores fuera de rango<BR>Revise los valores y vuelva a intentarlo.</HTML>");
//            return false;
//        }
        
        if(!validarValorFactura()) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El documento por pagar tiene el valor de la factura fuera de rango.<BR>Revise los valores en el pedido y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        return true; 
    }  
    
    private boolean eliminaItmValExtCero()
    {
        boolean blnRes=true;
        BigDecimal bgdCan=new BigDecimal("0");
        try
        {
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--)
            {
                bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()));
                if(bgdCan.compareTo(new BigDecimal("0"))<=0)
                    objTblMod.removeRow(i);
            }
        }
        catch (Exception e)  {
            blnRes=false;
            System.err.println("ERROR eliminaItmValExtCero: " + e);
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
        
        public void clickInicio()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickAnterior() 
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }
        
        public void clickSiguiente()
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }        

        public void clickFin() 
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }     

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
                
                limpiarFrm();
                noEditable(false);   
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecCar.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                setEditable(true);
                objAsiDia.setEditable(true);
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;                
                dtpFecDoc.setEnabled(true);
                dtpFecCar.setEnabled(true);
                txtNumDoc.setEnabled(false);
                blnHayCam=false;
                txaObs1.setText("USD");
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickConsultar(){
            mostrarTipDocPre();
            setEditable(false);
            objAsiDia.setEditable(false);
            objAsiDiaTraBan.setEditable(false);
        }  
        
        public void clickModificar()
        {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodPrvExt.setEditable(false);
            txtNomPrvExt.setEditable(false);
            butPrvExt.setEnabled(false);
            txtCodCtaBan.setEditable(false);
            txtCodEmpCtaBan.setEditable(false);
            txtNomBanCtaBan.setEditable(false);
            txtNumCtaBan.setEditable(false);
            butCtaBan.setEnabled(false);
            
            dtpFecDoc.setEnabled(false);
            txtNumDoc.setEditable(false);
            txtCodDoc.setEditable(false);
            
            txtValCom.setEditable(false);
            txtValInt.setEditable(false);
            
            txaObs1.setEditable(true);
            txaObs2.setEditable(true);   
            
            noEditable(false); 

            //Forma de Pago
            txtCodForPag.setEnabled(false);
            txtNomForPag.setEnabled(false);
            butForPag.setEnabled(false);                
            objTblModForPag.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            //Asiento de Diario
            objAsiDia.setEditable(false);
            objAsiDiaTraBan.setEditable(false);
            //Inicializar las variables que indican cambios.
            objAsiDia.setDiarioModificado(false);
            objAsiDiaTraBan.setDiarioModificado(false);
            blnHayCam=false;
            //cargarReg();
        }   
                    
        public void clickEliminar(){
        }
                    
        public void clickAnular(){
        }

        public void clickImprimir(){
        }
        
        public void clickVisPreliminar(){
        }
        
        public void clickAceptar(){
        }
        
        public void clickCancelar(){
        }
        
        public boolean insertar()
        {
            if(!insertarReg())
              return false;

            blnHayCam=false;
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
            return true;
        }        
                
        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;            
        }

        public boolean imprimir() 
        {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(0);
                objThrGUIVisPre.start();
            }
            return true;
        }
        
        public boolean vistaPreliminar() {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }

        public boolean cancelar()
        {
            boolean blnRes=true;
            try{
                if ((blnHayCam) ){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }           
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;            
        }
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean beforeInsertar()
        {
            if(!isCamVal())
                return false;            

            return true;
        }   
                
        public boolean beforeConsultar() {
            return true;
        } 
        
        public boolean beforeModificar()
        {
            /* Valida que no exista Transferencias Bancarias Asociadas al pago al exterior. */
            if(existeTransferenciaBancariaExterior()){ 
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Documento por pagar ya tiene asociada una<FONT COLOR=\"blue\">Transferencia Bancaria a Proveedores del exterior</FONT>.<BR>Verifique y vuelva a intentarlo.</HTML>");
                return false;
            }   
            return true;
        }    
        
        public boolean beforeEliminar() 
        {
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
            
            /* Valida que no exista Transferencias Bancarias Asociadas al pago del exterior. */
            if(existeTransferenciaBancariaExterior()){ 
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Documento por pagar tiene <FONT COLOR=\"blue\">Transferencias Bancarias al exterior </FONT> asociadas.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                return false;
            }                
              
//            /* Valida que no exista Cierre de Pago de Impuestos del pedido embarcado. */
//            if(objPagImp.existeCierrePagoExterior()){   
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El Pedido Embarcado ha sido cerrado para pago de impuestos.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
//                return false;
//            }               
        
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
            consultarReg();            
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterModificar() {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('w');
            cargarReg();
            return true;
        }
                
        public boolean afterEliminar()
        {
            return true;
        }
        
        public boolean afterAnular() 
        {
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
    //Fin ToolBar 
      

    /**
     * Esta función permite cargar el detalle del pedido seleccionado
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private void cargarDatPedIns(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intInsPed, int intFilSel)
    {
        String strTblInsPed="", strInsPed="";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                switch(intInsPed){
                    case 0:   strTblInsPed="notPedImp";   strInsPed="Nota Pedido";              break;
                    case 1:   strTblInsPed="pedEmbImp";   strInsPed="Pedido Embarcado";         break;
                    case 2:   strTblInsPed="movInv";      strInsPed="Ingreso por Importación";  break;
                }
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numPed, b1.co_Exp, b1.co_empImp, b4.co_locImp";
                strSQL+="      , b1.tx_nomEmpImp, b2.co_reg AS co_regCar, b3.co_carPag, b3.tx_nom AS tx_nomCarPag,( b5.ne_ultDoc+1) AS ne_ultDoc, b2.nd_valCarPag, d1.co_cta";
                strSQL+=" FROM(";
                strSQL+="      SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.co_Exp, a4.co_Emp as co_empImp, a4.tx_nom as tx_nomEmpImp";		
                if(intInsPed==0) {
                    strSQL+="      , (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) AS tx_numPed ";
                }
                else if(intInsPed==1) {
                    strSQL+="      , (CASE WHEN a1.tx_numDoc4 IS NOT NULL THEN a1.tx_numDoc4 ELSE a1.tx_numDoc2 END) AS tx_numPed ";
                }
                else if(intInsPed==2) {
                    strSQL+="      , (a1.tx_numDoc2) AS tx_numPed ";
                }
                strSQL+="      FROM tbm_cab"+strTblInsPed+" AS a1";
                strSQL+="      INNER JOIN tbm_det"+strTblInsPed+" AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc AND a1.co_doc=a5.co_doc)";
                if(intInsPed==2) {
                    strSQL+="  INNER JOIN tbm_emp AS a4 ON a1.co_emp=a4.co_emp";
                }
                else{
                    strSQL+="  INNER JOIN tbm_emp AS a4 ON a1.co_imp=a4.co_emp";
                }
                strSQL+="      WHERE a1.co_emp=" +intCodEmp+" AND a1.co_loc=" +intCodLoc;
                strSQL+="      AND a1.co_tipDoc=" +intCodTipDoc+" AND a1.co_doc=" +intCodDoc;
                strSQL+="      GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.co_Exp, a4.co_Emp, a4.tx_nom";	
                strSQL+="             , a1.tx_numDoc2";
                if(intInsPed==0) {
                    strSQL+="         , a1.tx_numDoc3";
                }
                else if(intInsPed==1) {
                    strSQL+="         , a1.tx_numDoc4";
                }         
                strSQL+=" ) AS b1";   
                strSQL+=" INNER JOIN tbm_carPag"+strTblInsPed+" AS b2 ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" INNER JOIN (tbm_carPagImp AS b3";
                strSQL+="           LEFT OUTER JOIN tbr_carPagImpPlaCta AS d1";
                strSQL+="           ON b3.co_carPag=d1.co_carPag AND d1.co_emp=" + txtCodEmpCtaBan.getText() + "";
                strSQL+=" )";
                strSQL+=" ON b2.co_carPag=b3.co_CarPag";
                
                
                
                
                
                
                strSQL+=" INNER JOIN (SELECT co_emp as co_empImp, co_loc as co_locImp FROM tbm_loc as a WHERE a.st_Reg IN ('P') ) AS b4";  
                strSQL+=" ON b1.co_empImp=b4.co_empImp";    
                strSQL+=" INNER JOIN tbm_cabTipDoc AS b5 ON b1.co_empImp=b5.co_emp AND b4.co_locImp=b5.co_loc AND "+txtCodTipDoc.getText()+"=b5.co_tiPDoc";
                strSQL+=" WHERE ( b3.tx_tipCarPag IN ('V') OR ( b3.st_imp='S' and b3.co_carPag IN(23, 26)) )";
                strSQL+=" AND b1.co_Exp="+txtCodPrvExt.getText();
                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numPed, b1.co_Exp, b1.co_empImp, b4.co_locImp";
                strSQL+="        , b1.tx_nomEmpImp, b2.co_reg, b3.co_carPag, b3.tx_nom, b5.ne_ultDoc, b2.nd_valCarPag, d1.co_cta";
                strSQL+=" ORDER BY b3.co_carPag";
                System.out.println("cargarDatPedIns: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTblMod.removeEmptyRows();
                while (rstLoc.next()){
                    intCodLocDxP=rstLoc.getInt("co_locImp");
                    txtNumDoc.setText("" + (rstLoc.getInt("ne_ultDoc")));
                    objTblMod.insertRow();
                    objTblMod.setValueAt(rstLoc.getString("co_emp"),       intFilSel, INT_TBL_DAT_COD_EMP_PED);
                    objTblMod.setValueAt(rstLoc.getString("co_loc"),       intFilSel, INT_TBL_DAT_COD_LOC_PED);
                    objTblMod.setValueAt(rstLoc.getString("co_tipDoc"),    intFilSel, INT_TBL_DAT_COD_TIPDOC_PED);
                    objTblMod.setValueAt(rstLoc.getString("co_doc"),       intFilSel, INT_TBL_DAT_COD_DOC_PED);
                    objTblMod.setValueAt(rstLoc.getString("tx_numPed"),    intFilSel, INT_TBL_DAT_NUM_PED_IMP);
                    objTblMod.setValueAt(rstLoc.getString("co_empImp"),    intFilSel, INT_TBL_DAT_COD_EMP_IMP);
                    objTblMod.setValueAt(rstLoc.getString("co_regCar"),    intFilSel, INT_TBL_DAT_COD_REG_CAR);
                    objTblMod.setValueAt(rstLoc.getString("co_carPag"),    intFilSel, INT_TBL_DAT_COD_CAR_PAG);
                    objTblMod.setValueAt(rstLoc.getString("tx_nomCarPag"), intFilSel, INT_TBL_DAT_NOM_CAR_PAG);
                    objTblMod.setValueAt(""+intInsPed,                     intFilSel, INT_TBL_DAT_COD_INS_PED);
                    objTblMod.setValueAt(""+strInsPed,                     intFilSel, INT_TBL_DAT_NOM_INS_PED);
                    objTblMod.setValueAt(rstLoc.getString("nd_valCarPag"), intFilSel, INT_TBL_DAT_VAL_CAR_PAG_TOT);
                    objTblMod.setValueAt("0",                              intFilSel, INT_TBL_DAT_VAL_PAG_EXT);
                    objTblMod.setValueAt(rstLoc.getString("co_cta"),       intFilSel, INT_TBL_DAT_COD_CTA_CAR_PAG);
                    
                    intFilSel++;
                }
                objTblMod.removeEmptyRows();
                rstLoc.close();
                stmLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)    {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }       
    
    /**
     * Esta función permite cargar la forma de pago predeterminada por empresa.
     * @return true: Si se pudo cargar.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarForPagPre(int codigoEmpresaDxP)
    {
        Boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_forPag, a.tx_des AS tx_desForPag, a.ne_numPag, a.ne_tipForPag";
                strSQL+=" FROM tbm_cabForPag AS a";
                strSQL+=" WHERE a.st_reg NOT IN('E','I')";
                strSQL+=" AND ( (a.co_emp=1 AND a.co_forPag=109 ) OR ( a.co_emp=2 AND a.co_forPag=127 )  OR (a.co_emp=4 AND a.co_forPag=107 ) )"; /* Mostrar sólo Forma de Pago: Transferencias. */
                if(codigoEmpresaDxP!=-1) {
                    strSQL+=" AND a.co_emp="+codigoEmpresaDxP; 
                }
                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTblModForPag.removeEmptyRows();
                if (rstLoc.next()){
                    txtCodForPag.setText(""+rstLoc.getString("co_forPag"));
                    txtNomForPag.setText(""+rstLoc.getString("tx_desForPag"));
                    txtNumForPag.setText(""+rstLoc.getString("ne_numPag"));
                    txtTipForPag.setText(""+rstLoc.getString("ne_tipForPag"));
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                calcularTot();                
            }
        }
        catch (java.sql.SQLException e)    {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }      
    
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="      , a1.co_usrIng, a10.tx_usr AS tx_nomUsrIng, a1.co_usrMod, a11.tx_usr AS tx_nomUsrMod";
                strSQL+=" FROM ( (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS a10 ON a1.co_usrIng=a10.co_usr)";
                strSQL+="         LEFT OUTER JOIN tbm_usr AS a11 ON a1.co_usrMod=a11.co_usr";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_detConIntMovInv AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_DOC=a3.co_doc)";
                strSQL+=" WHERE a1.st_reg<>'E'";
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc="+ objParSis.getCodigoLocal();
                }
                //Validar que sólo se muestre los documentos asignados al programa.
                if(txtCodTipDoc.getText().equals("")) {
                    if(objParSis.getCodigoUsuario()==1)
                        strSQL+=" AND a1.co_TipDoc IN ( select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+")";
                    else
                        strSQL+=" AND a1.co_TipDoc IN ( select co_tipDoc from tbr_tipDocUsr where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+" AND co_usr="+objParSis.getCodigoUsuario()+")";
                }
                else 
                    strSQL+=" AND a1.co_tipDoc =" + txtCodTipDoc.getText() + "";
                    
                if (!txtCodDoc.getText().equals(""))
                    strSQL+=" AND a1.co_doc =" + txtCodDoc.getText() + "";
                if (!txtNumDoc.getText().equals(""))
                    strSQL+=" AND a1.ne_numDoc =" + txtNumDoc.getText() + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                         
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="       , a1.co_usrIng, a10.tx_usr , a1.co_usrMod, a11.tx_usr";
                strSQL+=" ORDER BY a1.fe_doc, a1.fe_ing, a1.ne_numDoc, a1.co_tipDoc, a1.co_doc"; 
                //System.out.println("consultarReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,     rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,     rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC,  rst.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,     rst.getInt("co_doc"));
                    arlRegCon.add(INT_ARL_CON_TXT_USR_ING, rst.getString("tx_nomUsrIng"));
                    arlRegCon.add(INT_ARL_CON_TXT_USR_MOD, rst.getString("tx_nomUsrMod"));
                    arlDatCon.add(arlRegCon);
                }                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;                

                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
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
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg())
            {
                if(cargarDetReg())
                {
                    blnRes=true;               
                }                
            }
            else
            {
                mostrarMsgInf("Error al cargar registro");
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
        boolean blnRes=true;
        int intPosRel;
        try
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp as co_empImp, a6.tx_nom AS tx_nomEmpImp, a1.co_tipDoc, a2.tx_desCor as tx_desCorTipDoc";
                strSQL+="      , a2.tx_desLar as tx_desLarTipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc";
                strSQL+="      , a7.fe_venChqAutPag AS fe_Car, a1.nd_ValCar, a1.nd_ValComExt, a1.nd_ValIntExt, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+="      , a1.co_exp AS co_prvExt, a4.tx_nom AS tx_nomPrvExt, a1.nd_tot AS nd_valDoc, a1.tx_ate AS st_mosParRes";
                strSQL+="      , a1.co_ctaBanExp, a8.tx_numCtaBan, a9.tx_desLar AS tx_nomCtaBan";
                strSQL+=" FROM tbm_cabMovInv AS a1 ";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_detConIntMovInv AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_DOC=a3.co_doc)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a7 ON (a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_tipDoc=a7.co_tipDoc AND a1.co_Doc=a7.co_doc)";
                strSQL+=" INNER JOIN tbm_expImp AS a4 ON (a1.co_Exp=a4.co_Exp)";
                strSQL+=" LEFT OUTER JOIN ( tbm_ctaBan AS a8 INNER JOIN tbm_var AS a9 ON a8.co_ban=a9.co_reg ) ON (a1.co_emp=a8.co_emp AND a1.co_ctaBanExp=a8.co_ctaBan)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_com=a5.co_usr";
                strSQL+=" INNER JOIN tbm_emp AS a6 ON a1.co_emp=a6.co_emp";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);  
                strSQL+=" GROUP BY a1.co_emp, a6.tx_nom, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.fe_doc, a1.ne_numDoc";
                strSQL+="        , a7.fe_venChqAutPag, a1.nd_ValCar, a1.nd_ValComExt, a1.nd_ValIntExt, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp, a1.co_exp, a4.tx_nom, a1.nd_tot, a1.tx_ate ";
                strSQL+="        , a1.co_ctaBanExp, a8.tx_numCtaBan, a9.tx_desLar";
                //System.out.println("cargarCabReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {    
                    txtCodTipDoc.setText(rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getObject("tx_desCorTipDoc")==null?"":rst.getString("tx_desCorTipDoc"));
                    txtDesLarTipDoc.setText(rst.getObject("tx_desLarTipDoc")==null?"":rst.getString("tx_desLarTipDoc"));
                    txtCodDoc.setText(rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                    txtNumDoc.setText(rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc"));   
                    lblNomEmpImp.setText(rst.getObject("tx_nomEmpImp")==null?"***":("***"+rst.getString("tx_nomEmpImp")));
                    txtCodPrvExt.setText(rst.getObject("co_prvExt")==null?"":rst.getString("co_prvExt"));
                    txtNomPrvExt.setText(rst.getObject("tx_nomPrvExt")==null?"":rst.getString("tx_nomPrvExt"));
                    txtCodCtaBan.setText(rst.getObject("co_ctaBanExp")==null?"":rst.getString("co_ctaBanExp"));
                    
                    intCodEmpDxP=rst.getObject("co_empImp")==null?-1:rst.getInt("co_empImp");
                    txtCodEmpCtaBan.setText(""+intCodEmpDxP);     

                    txtNomBanCtaBan.setText(rst.getObject("tx_nomCtaBan")==null?"":rst.getString("tx_nomCtaBan"));
                    txtNumCtaBan.setText(rst.getObject("tx_numCtaBan")==null?"":rst.getString("tx_numCtaBan"));
                    
                    dtpFecDoc.setText("");
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    dtpFecCar.setText("");
                    dtpFecCar.setText(objUti.formatearFecha(rst.getDate("fe_car"),"dd/MM/yyyy"));

                    txtValDoc.setText(rst.getObject("nd_valDoc")==null?"":rst.getString("nd_valDoc"));
                    txtValCar.setText(rst.getObject("nd_valCar")==null?"":rst.getString("nd_valCar"));  
                    txtValCom.setText(rst.getObject("nd_ValComExt")==null?"":rst.getString("nd_ValComExt"));                      
                    txtValCom.setText(rst.getObject("nd_ValIntExt")==null?"":rst.getString("nd_ValIntExt"));         
                    
                    //Pie de pagina
                    txaObs1.setText(rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    txaObs2.setText(rst.getObject("tx_obs2")==null?"":rst.getString("tx_obs2"));
                    
                    strAux=rst.getString("st_mosParRes");
                    chkMosParRes.setSelected((strAux==null)?false:true); 
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("I")){
                        strAux="Anulado";
                        objUti.desactivarCom(this);
                    }
                    else if (strAux.equals("E")){
                        strAux="Eliminado";
                        objUti.desactivarCom(this);
                    }
                    else {
                        strAux="Otro";
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(this);
                            noEditable(false);
                        }
                    }
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
            
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                           
                //Mostrar la posición relativa del registro.
                intPosRel = intIndiceCon+1;
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
                blnHayCam=false;
            }
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)       {
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
    private boolean cargarDetReg(){
        boolean blnRes=false;
        try{
            if (cargarTabGen()){ 
                if (cargarTabForPag()){ 
                    if (cargarTabAsiDiaDxP()){ 
                        if (cargarTabAsiDiaTraBan()){ 
                            blnRes=true;
                        }
                    }
                }
            }                    
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabGen()
    {
        boolean blnRes=true;
        BigDecimal bgdAux=new BigDecimal(BigInteger.ZERO);
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT (CASE WHEN a5.co_empRel IS NULL THEN (CASE WHEN a4.co_empRel IS NULL THEN a3.co_empRel ELSE a4.co_EmpRel END) ELSE a5.co_empRel END) AS co_empPed ";
                strSQL+="      , (CASE WHEN a5.co_locRel IS NULL THEN (CASE WHEN a4.co_locRel IS NULL THEN a3.co_locRel ELSE a4.co_locRel END) ELSE a5.co_locRel END) AS co_locPed ";
                strSQL+="      , (CASE WHEN a5.co_tipDocRel IS NULL THEN (CASE WHEN a4.co_tipDocRel IS NULL THEN a3.co_tipDocRel ELSE a4.co_tipDocRel END) ELSE a5.co_tipDocRel END) AS co_tipDocPed ";
                strSQL+="      , (CASE WHEN a5.co_docRel IS NULL THEN (CASE WHEN a4.co_docRel IS NULL THEN a3.co_docRel ELSE a4.co_docRel END) ELSE a5.co_docRel END) AS co_docPed ";
                strSQL+="      , (CASE WHEN a5.tx_numPed IS NULL THEN (CASE WHEN a4.tx_numPed IS NULL THEN a3.tx_numPed ELSE a4.tx_numPed END) ELSE a5.tx_numPed END) AS tx_numPed ";
                strSQL+="      , (CASE WHEN a5.co_regRel IS NULL THEN (CASE WHEN a4.co_regRel IS NULL THEN a3.co_regRel ELSE a4.co_regRel END) ELSE a5.co_regRel END) AS co_regCarPag ";
                strSQL+="      , (CASE WHEN a5.co_carPag IS NULL THEN (CASE WHEN a4.co_carPag IS NULL THEN a3.co_carPag ELSE a4.co_carPag END) ELSE a5.co_carPag END) AS co_carPag ";
                strSQL+="      , (CASE WHEN a5.nd_valCarPag IS NULL THEN (CASE WHEN a4.nd_valCarPag IS NULL THEN a3.nd_valCarPag ELSE a4.nd_valCarPag END) ELSE a5.nd_valCarPag END) AS nd_valCarPag ";
                strSQL+="      , (CASE WHEN a5.st_ins IS NULL THEN (CASE WHEN a4.st_ins IS NULL THEN a3.st_ins ELSE a4.st_ins END) ELSE a5.st_ins END) AS st_ins ";
                strSQL+="      , (CASE WHEN a5.tx_nomInsPed IS NULL THEN (CASE WHEN a4.tx_nomInsPed IS NULL THEN a3.tx_nomInsPed ELSE a4.tx_nomInsPed END) ELSE a5.tx_nomInsPed END) AS tx_nomInsPed ";
                strSQL+="      , (CASE WHEN a5.co_cta IS NULL THEN (CASE WHEN a4.co_cta IS NULL THEN a3.co_cta ELSE a4.co_cta END) ELSE a5.co_cta END) AS co_cta ";
                strSQL+="      , a1.co_emp as co_empImp, a2.co_reg, a2.tx_des AS tx_nomCarPag, a2.nd_tot AS nd_valPagExt";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" LEFT OUTER JOIN ";
                strSQL+=" (   SELECT (CASE WHEN a2.tx_numDoc3 IS NOT NULL THEN a2.tx_numDoc3 ELSE a2.tx_numDoc2 END) AS tx_numPed";
                strSQL+="          , a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_Reg, a.co_empRelNotPed as co_empRel, a.co_locRelNotPed as co_locRel";
                strSQL+="          , a.co_tipDocRelNotPed as co_tipDocRel, a.co_docRelNotPed as co_docRel, a.co_regRelNotPed as co_regRel, a1.co_CarPag, a1.nd_valCarPag";
                strSQL+="          , CAST('0' AS CHARACTER VARYING) AS st_ins, CAST('Nota Pedido' AS CHARACTER VARYING) AS tx_nomInsPed, d1.co_cta ";
                strSQL+="     FROM tbr_detConIntCarPagInsImp AS a INNER JOIN (tbm_carPagNotPedImp AS a1 LEFT OUTER JOIN tbr_carPagImpPlaCta AS d1 ON a1.co_carPag=d1.co_carPag AND d1.co_emp= " + txtCodEmpCtaBan.getText() + ")"; 
                strSQL+="     ON a.co_empRelNotPed=a1.co_emp AND a.co_locRelNotPed=a1.co_loc AND a.co_tipDocRelNotPed=a1.co_tipDoc AND a.co_docRelNotPed=a1.co_Doc AND a.co_regRelNotPed=a1.co_reg";
                strSQL+="     INNER JOIN tbm_cabNotPedImp AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_Doc AND a2.co_reg=a3.co_Reg)";
                strSQL+=" LEFT OUTER JOIN ";
                strSQL+=" (   SELECT (CASE WHEN a2.tx_numDoc4 IS NOT NULL THEN a2.tx_numDoc4 ELSE a2.tx_numDoc2 END) AS tx_numPed";
                strSQL+="          , a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_Reg, a.co_empRelPedEmb as co_empRel, a.co_locRelPedEmb as co_locRel";
                strSQL+="          , a.co_tipDocRelPedEmb as co_tipDocRel, a.co_docRelPedEmb as co_docRel, a.co_regRelPedEmb as co_regRel, a1.co_CarPag, a1.nd_valCarPag";
                strSQL+="          , CAST('1' AS CHARACTER VARYING) AS st_ins, CAST('Pedido Embarcado' AS CHARACTER VARYING) AS tx_nomInsPed, d1.co_cta ";
                strSQL+="     FROM tbr_detConIntCarPagInsImp AS a INNER JOIN (tbm_carPagPedEmbImp AS a1 LEFT OUTER JOIN tbr_carPagImpPlaCta AS d1 ON a1.co_carPag=d1.co_carPag AND d1.co_emp= " + txtCodEmpCtaBan.getText() + ")";
                strSQL+="     ON a.co_empRelPedEmb=a1.co_emp AND a.co_locRelPedEmb=a1.co_loc AND a.co_tipDocRelPedEmb=a1.co_tipDoc AND a.co_docRelPedEmb=a1.co_Doc AND a.co_regRelPedEmb=a1.co_reg";
                strSQL+="     INNER JOIN tbm_cabPedEmbImp AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" ) AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_Doc AND a2.co_reg=a4.co_Reg)";                
                strSQL+=" LEFT OUTER JOIN ";
                strSQL+=" (   SELECT a2.tx_numDoc2 AS tx_numPed";
                strSQL+="          , a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_Reg, a.co_empRelMovInv as co_empRel, a.co_locRelMovInv as co_locRel";
                strSQL+="          , a.co_tipDocRelMovInv as co_tipDocRel, a.co_docRelMovInv as co_docRel, a.co_regRelMovInv as co_regRel, a1.co_CarPag, a1.nd_valCarPag";
                strSQL+="          , CAST('2' AS CHARACTER VARYING) AS st_ins, CAST('Ingreso por Importación' AS CHARACTER VARYING) AS tx_nomInsPed, d1.co_cta ";
                strSQL+="     FROM tbr_detConIntCarPagInsImp AS a INNER JOIN (tbm_carPagMovInv AS a1 LEFT OUTER JOIN tbr_carPagImpPlaCta AS d1 ON a1.co_carPag=d1.co_carPag AND d1.co_emp= " + txtCodEmpCtaBan.getText() + ")";
                strSQL+="     ON a.co_empRelMovInv=a1.co_emp AND a.co_locRelMovInv=a1.co_loc AND a.co_tipDocRelMovInv=a1.co_tipDoc AND a.co_docRelMovInv=a1.co_Doc AND a.co_regRelMovInv=a1.co_reg";
                strSQL+="     INNER JOIN tbm_cabMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" ) AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc AND a2.co_doc=a5.co_Doc AND a2.co_reg=a5.co_Reg)";   
                strSQL+=" WHERE a1.co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a1.st_reg NOT IN('E')"; 
                strSQL+=" GROUP BY a5.co_empRel, a4.co_empRel, a3.co_empRel";
                strSQL+="        , a5.co_locRel, a4.co_locRel, a3.co_locRel";
                strSQL+="        , a5.co_tipDocRel, a4.co_tipDocRel, a3.co_tipDocRel";
                strSQL+="        , a5.co_docRel, a4.co_docRel, a3.co_docRel";
                strSQL+="        , a5.tx_numPed, a4.tx_numPed, a3.tx_numPed";
                strSQL+="        , a5.co_regRel, a4.co_regRel, a3.co_regRel";
                strSQL+="        , a5.co_carPag, a4.co_carPag, a3.co_carPag";
                strSQL+="        , a5.nd_valCarPag, a4.nd_valCarPag, a3.nd_valCarPag";
                strSQL+="        , a5.st_ins, a4.st_ins, a3.st_ins";
                strSQL+="        , a5.tx_nomInsPed, a4.tx_nomInsPed, a3.tx_nomInsPed";
                strSQL+="        , a5.co_cta, a4.co_cta, a3.co_cta";
                strSQL+="        , a1.co_emp, a2.co_reg, a2.tx_des, a2.nd_tot";
                strSQL+=" ORDER BY a2.co_reg";
                System.out.println("cargarTabGen: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP_PED,     rst.getString("co_empPed"));      
                    vecReg.add(INT_TBL_DAT_COD_LOC_PED,     rst.getString("co_locPed"));      
                    vecReg.add(INT_TBL_DAT_COD_TIPDOC_PED,  rst.getString("co_tipDocPed"));      
                    vecReg.add(INT_TBL_DAT_COD_DOC_PED,     rst.getString("co_docPed"));      
                    vecReg.add(INT_TBL_DAT_NUM_PED_IMP,     rst.getString("tx_numPed"));      
                    vecReg.add(INT_TBL_DAT_BUT_PED,         "");   
                    vecReg.add(INT_TBL_DAT_COD_EMP_IMP,     rst.getString("co_empImp")); 
                    vecReg.add(INT_TBL_DAT_COD_REG_CAR,     rst.getString("co_regCarPag"));                            
                    vecReg.add(INT_TBL_DAT_COD_CAR_PAG,     rst.getString("co_carPag"));
                    vecReg.add(INT_TBL_DAT_NOM_CAR_PAG,     rst.getString("tx_nomCarPag"));
                    vecReg.add(INT_TBL_DAT_BUT_CAR_PAG,     "");
                    vecReg.add(INT_TBL_DAT_COD_INS_PED,     rst.getString("st_ins"));
                    vecReg.add(INT_TBL_DAT_NOM_INS_PED,     rst.getString("tx_nomInsPed"));
                    bgdAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_valPagExt")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_valPagExt"))  , objParSis.getDecimalesBaseDatos());
                    vecReg.add(INT_TBL_DAT_VAL_CAR_PAG_TOT, (rst.getObject("nd_valCarPag")==null?"0":(rst.getString("nd_valCarPag").equals("")?"0":rst.getString("nd_valCarPag"))));
                    vecReg.add(INT_TBL_DAT_VAL_PAG_EXT,     ""+bgdAux);
                    vecReg.add(INT_TBL_DAT_COD_CTA_CAR_PAG, rst.getString("co_cta"));
                    
                    vecDat.add(vecReg);      
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
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
    private boolean cargarTabForPag(){
        boolean blnRes=true;
        String strApl="";
        try{
            if (!txtCodPrvExt.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()); 
                if (con!=null){
                    stm=con.createStatement(); 
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+="      , a2.ne_diaCre, a2.fe_ven, a2.co_tipRet, a3.tx_desCor AS tx_desCorTipRet, a3.tx_desLar AS tx_desLarTipRet";
                    strSQL+="      , a2.nd_porRet, a2.tx_aplRet, a2.nd_basImp, a2.tx_codSri, a2.mo_pag, a2.co_reg";
                    strSQL+="      , a1.co_forPag, a4.tx_des AS tx_desForPag, a4.ne_numpag, a4.ne_tipforpag, ABS(a2.nd_abo) AS nd_abo";
                    strSQL+="      , a2.st_autpag, a2.co_ctaautpag";
                    strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabForPag AS a4 ON a1.co_emp=a4.co_emp AND a1.co_forPag=a4.co_forPag)";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_tipRet=a3.co_tipRet AND a3.st_reg NOT IN('I','E')";
                    strSQL+=" WHERE a1.co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND a1.co_doc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a1.st_reg NOT IN('E')";
                    strSQL+=" AND a2.st_reg IN('A', 'C')";
                    strSQL+=" ORDER BY a2.co_reg";
                    //System.out.println("cargarTabForPag: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_FOR_PAG_DAT_LIN, "");
                        vecReg.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,          "" + rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,          objUti.formatearFecha(rst.getString("fe_ven"), "yyyy-MM-dd", "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,      rst.getString("co_tipRet"));
                        vecReg.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,  rst.getString("tx_desCorTipRet"));
                        vecReg.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,      null);
                        vecReg.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,  rst.getString("tx_desLarTipRet"));
                        vecReg.add(INT_TBL_FOR_PAG_DAT_POR_RET,          rst.getBigDecimal("nd_porRet"));

                        strApl=rst.getObject("tx_aplRet")==null?"":rst.getString("tx_aplRet");

                        vecReg.add(INT_TBL_FOR_PAG_DAT_APL,              strApl.equals("S")?"Subtotal":(strApl.equals("I")?"Iva":(strApl.equals("O")?"Otros":"")));
                        vecReg.add(INT_TBL_FOR_PAG_DAT_EST_APL,          strApl);
                        vecReg.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,          objUti.redondearBigDecimal(rst.getBigDecimal("nd_basImp"), objParSis.getDecimalesMostrar())   );
                        vecReg.add(INT_TBL_FOR_PAG_DAT_COD_SRI,          rst.getString("tx_codSri"));
                        vecReg.add(INT_TBL_FOR_PAG_DAT_MON_PAG,          objUti.redondearBigDecimal(rst.getBigDecimal("mo_pag"), objParSis.getDecimalesMostrar()));
                        vecDat.add(vecReg);

                        txtCodForPag.setText(rst.getString("co_forPag"));
                        txtNomForPag.setText(rst.getString("tx_desForPag"));
                        txtNumForPag.setText(rst.getString("ne_numpag"));
                        txtTipForPag.setText(rst.getString("ne_tipforpag"));
                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    con.close();
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModForPag.setData(vecDat);
                    tblForPag.setModel(objTblModForPag);
                    vecDat.clear();
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
     * Esta función permite cargar el asiento de diario del registro seleccionado.
     * @return true: Si se pudo cargar el asiento de diario del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabAsiDiaDxP()
    {
        boolean blnRes=false;
        try{
            objAsiDia.inicializar();
            if(objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)  
                                       , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                       , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                       , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)))
            {
                blnRes=true;    
            }
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    private boolean cargarTabAsiDiaTraBan()
    {
        boolean blnRes=false;
        intCodEmpTraBan=0;
        intCodLocTraBan=0;
        intCodTipDocTraBan=0;
        intCodDocTraBan=0;
        intCodEmpMovBan=0;
        intCodLocMovBan=0;
        intCodTipDocMovBan=0;
        intCodDocMovBan=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                objAsiDiaTraBan.inicializar(); //Limpiar el panel de Asiento de Diario de transferencias bancarias.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia";
                strSQL+="      , a5.co_emp as co_empMovBan, a5.co_loc as co_locMovBan, a5.co_tipDoc as co_tipDocMovBan, a5.co_doc as co_docMovBan";
                strSQL+=" FROM tbm_cabDia AS a";
                strSQL+=" INNER JOIN tbm_detDia AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_dia=a1.co_dia";
                //strSQL+=" INNER JOIN tbm_docGenOtrMovBan AS a2 ON a.co_emp=a2co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                strSQL+=" INNER JOIN tbm_cabSegMovInv AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_diaRelCabDia";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                strSQL+=" INNER JOIN tbm_docGenOtrMovBan AS a5 ON a.co_emp=a5.co_empRelCabDia AND a.co_loc=a5.co_locRelCabDia AND a.co_tipDoc=a5.co_tipDocRelCabDia AND a.co_dia=a5.co_docRelCabDia";
                strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a.st_reg NOT IN ('E', 'I')";
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia";   
                strSQL+="        , a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc ";   
                //System.out.println("cargarTabAsiDiaTraBan: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodEmpTraBan=rst.getInt("co_emp");
                    intCodLocTraBan=rst.getInt("co_loc");
                    intCodTipDocTraBan=rst.getInt("co_tipDoc");
                    intCodDocTraBan=rst.getInt("co_dia");
                    intCodEmpMovBan=rst.getInt("co_empMovBan");
                    intCodLocMovBan=rst.getInt("co_locMovBan");
                    intCodTipDocMovBan=rst.getInt("co_tipDocMovBan");
                    intCodDocMovBan=rst.getInt("co_docMovBan");
                    //if(objAsiDiaTraBan.consultarDiario(rst.getInt("co_emp"), rst.getInt("co_loc"), rst.getInt("co_tipDoc"), rst.getInt("co_dia") ) )
                    if(objAsiDiaTraBan.consultarDiario(intCodEmpTraBan, intCodLocTraBan, intCodTipDocTraBan, intCodDocTraBan) )
                    {
                        blnRes=true;
                    }                      
                }                
                rst.close();
                rst=null;
                stm.close();
                stm=null;
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
    
    private boolean insertarReg()
    {   
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
            if(con!=null)
            {
                con.setAutoCommit(false); 
                if(asignarValorCarta()){
                    if(insertarCabMovInv()){
                        if (insertarDetConIntMovInv()){
                            if (insertarRelDetConIntCarPag()){
                                if(insertarPagMovInv()){
                                    if(insertarDiario()){
                                        if(insertarCabSegMovInv()) {
                                            if(enviarCorEle()) {
                                                con.commit();
                                                blnRes=true;
                                            }else con.rollback();
                                        }else con.rollback();
                                    }else con.rollback();
                                }else con.rollback();
                            }else con.rollback();
                        }else con.rollback();
                    }else con.rollback();
                }else con.rollback();
                
                con.close();                                             
                con=null;
                objTblMod.clearDataSavedBeforeRemoveRow();
                objTblMod.initRowsState();
            }
        }
        catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); } 
        catch(Exception Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); } 
        return blnRes;                  
    }  
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabMovInv(){
        boolean blnRes=true;
        int intUltDoc=-1;
        BigDecimal bgdValTot=new BigDecimal ("0");
        BigDecimal bgdValCar=new BigDecimal ("0");
        BigDecimal bgdValCom=new BigDecimal ("0");
        BigDecimal bgdValInt=new BigDecimal ("0");
        String strFecUltMod="";
        try{
            if (con!=null){
                calcularTot();
                stm=con.createStatement();
                //Obtener el código del último registro. 
                //Se verifica en la tabla de diarios, porque ya existen registros con el tipo de documento TRBAEX.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc) as co_doc";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpDxP;
                strSQL+=" AND a1.co_loc=" + intCodLocDxP;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltDoc==-1)
                    return false;
                intUltDoc++;
                txtCodDoc.setText(""+intUltDoc);
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                
                bgdValTot=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesMostrar());
                bgdValCar=objUti.redondearBigDecimal(new BigDecimal(txtValCar.getText()==null?"0":(txtValCar.getText().equals("")?"0":txtValCar.getText())) , objParSis.getDecimalesMostrar());
                bgdValCom=objUti.redondearBigDecimal(new BigDecimal(txtValCom.getText()==null?"0":(txtValCom.getText().equals("")?"0":txtValCom.getText())) , objParSis.getDecimalesMostrar());
                bgdValInt=objUti.redondearBigDecimal(new BigDecimal(txtValInt.getText()==null?"0":(txtValInt.getText().equals("")?"0":txtValInt.getText())) , objParSis.getDecimalesMostrar());
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabmovinv(";
                strSQL+="       co_emp, co_loc, co_tipDoc, co_doc, ne_secGrp, ne_secEmp, ne_numCot, ne_numDoc";
                strSQL+="     , tx_numPed, ne_numGui, co_dia, fe_doc, fe_ven, co_Exp, co_com, tx_nomVen, tx_ate";
                strSQL+="     , co_forPag, tx_desForPag, nd_sub";
                strSQL+="     , nd_valIva, nd_tot, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, co_cta";
                strSQL+="     , co_motDoc, co_mnu, st_imp, tx_obs1, tx_obs2, st_reg";
                strSQL+="     , fe_ing, co_usrIng, tx_comIng, fe_ultMod, co_usrMod , tx_comMod";
                strSQL+="     , fe_con, tx_obs3, co_forRet, tx_vehRet, tx_choRet";
                strSQL+="     , ne_numVecRecDoc, fe_ultRecDoc, tx_obsSolAut, tx_obsAutSol, st_aut, ne_valAut";
                strSQL+="     , st_tipDev, st_conInv, st_regRep, ne_numDocRee";
                strSQL+="     , st_creGuiRem, st_conInvTraAut, co_locRelSolDevVen, co_tipDocRelSolDevVen";
                strSQL+="     , co_docRelSolDevVen, st_excDocConVenCon, fe_autExcDocConVenCon";
                strSQL+="     , co_usrAutExcDocConVenCon, tx_comAutExcDocConVenCon, st_docConMerSalDemDebFac, st_autAnu, fe_autAnu";
                strSQL+="     , co_usrAutAnu, tx_comAutAnu, st_emiChqAntRecFacPrv, st_docMarLis, nd_ValCar, nd_ValComExt, nd_ValIntExt, co_ctaBanExp)";
                strSQL+=" VALUES (";
                strSQL+="  " + intCodEmpDxP + "";  //co_emp
                strSQL+=", " + intCodLocDxP + "";  //co_loc
                strSQL+=", " + txtCodTipDoc.getText() + "";        //co_tipdoc
                strSQL+=", " + txtCodDoc.getText() + "";           //co_doc
                strSQL+=", Null"; //ne_secgrp
                strSQL+=", Null"; //ne_secemp
                strSQL+=", Null"; //ne_numcot
                strSQL+=", " + txtNumDoc.getText() + "";           //ne_numdoc
                strSQL+=", Null"; //tx_numped
                strSQL+=", Null"; //ne_numgui
                strSQL+=", Null"; //co_dia
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL+=", Null"; //fe_ven
                strSQL+=", " + txtCodPrvExt.getText() + "";    //co_Exp
                strSQL+=", " + objParSis.getCodigoUsuario() + ""; //co_com
                strSQL+=", '" + objParSis.getNombreUsuario().toLowerCase() + "'"; //tx_nomven
                if(chkMosParRes.isSelected()) //tx_ate: Se usará para indicar si muestra el párrafo de responsabilidad.
                    strSQL+=", 'S'";   //tx_ate
                else
                    strSQL+=", Null "; //tx_ate
                
                strSQL+=", " + txtCodForPag.getText() + ""; //co_forpag
                strSQL+=", " + objUti.codificar(txtNomForPag.getText()) + "";  //tx_desforpag
                strSQL+=", " + bgdValTot.multiply(bgdSig); //nd_sub
                strSQL+=", 0";//nd_valiva
                strSQL+=", " + bgdValTot.multiply(bgdSig); //nd_tot
                strSQL+=", Null";//tx_ptopar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_mottra
                strSQL+=", Null";//tx_desmottra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motdoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText() + strVer); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=", 'O'";//st_reg   /* Predocumento. */
                strSQL+=", '" + strFecUltMod + "'";//fe_ing
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+=", '" + strFecUltMod + "'";//fe_ultMod 
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_commod
                strSQL+=", Null";//fe_con
                strSQL+=", Null";//tx_obs3
                strSQL+=", Null";//co_forret
                strSQL+=", Null";//tx_vehret
                strSQL+=", Null";//tx_choret
                strSQL+=", 0";   //ne_numvecrecdoc
                strSQL+=", Null";//fe_ultrecdoc
                strSQL+=", Null";//tx_obssolaut
                strSQL+=", Null";//tx_obsautsol
                strSQL+=", Null";//st_aut
                strSQL+=", Null";//ne_valaut
                strSQL+=", 'C'"; //st_tipdev
                strSQL+=", 'P'"; //st_coninv
                strSQL+=", 'I'"; //st_regrep
                strSQL+=", Null";//ne_numdocree
                strSQL+=", 'N'"; //st_creguirem
                strSQL+=", 'N'"; //st_coninvtraaut
                strSQL+=", Null";//co_locrelsoldevven
                strSQL+=", Null";//co_tipdocrelsoldevven
                strSQL+=", Null";//co_docrelsoldevven
                strSQL+=", Null";//st_excdocconvencon
                strSQL+=", Null";//fe_autexcdocconvencon
                strSQL+=", Null";//co_usrautexcdocconvencon
                strSQL+=", Null";//tx_comautexcdocconvencon
                strSQL+=", 'N'"; //st_docconmersaldemdebfac
                strSQL+=", Null";//st_autanu
                strSQL+=", Null";//fe_autanu
                strSQL+=", Null";//co_usrautanu
                strSQL+=", Null";//tx_comautanu
                strSQL+=", 'S'"; //st_emiChqAntRecFacPrv
                strSQL+=", 'S'"; //st_docMarLis
                strSQL+=", " + bgdValCar; //nd_ValCar
                strSQL+=", " + bgdValCom; //nd_ValComExt
                strSQL+=", " + bgdValInt; //nd_ValIntExt
                strSQL+=", " + txtCodCtaBan.getText() + ""; //co_ctaBanExp  
                strSQL+=");"; 

                System.out.println("insertarCabMovInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDetConIntMovInv(){
        boolean blnRes=true;
        BigDecimal bgdValPag=BigDecimal.ZERO;
        int j=1;
        String strSQLUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                objTblMod.removeEmptyRows();
                
                for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    bgdValPag=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_VAL_PAG_EXT)==null)?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()),objParSis.getDecimalesBaseDatos());                    
//                    if(bgdValPag.compareTo(BigDecimal.ZERO)>0){
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_detConIntMovInv";
                        strSQL+=" (co_emp,co_loc,co_tipDoc,co_doc,co_reg,tx_des,nd_can,nd_preUni,st_iva,nd_tot,st_regRep)";
                        strSQL+="  SELECT ";
                        strSQL+="  " + intCodEmpDxP + " AS co_emp";  //co_emp
                        strSQL+=", " + intCodLocDxP + " AS co_loc";  //co_loc
                        strSQL+=", " + txtCodTipDoc.getText() + " AS co_tipDoc";   //co_tipDoc
                        strSQL+=", " + txtCodDoc.getText() + " AS co_doc";         //co_doc
                        strSQL+=", " + j + " AS co_reg"; //co_reg
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CAR_PAG)) + " AS tx_des";//tx_des
                        strSQL+=", 1 AS nd_can";   //nd_can
                        strSQL+=", " + bgdValPag.multiply(bgdSig) + " AS nd_preUni";//nd_preUni     
                        strSQL+=", 'N' AS st_iva"; //st_iva
                        strSQL+=", " + bgdValPag.multiply(bgdSig)+" AS nd_tot";     //nd_tot
                        strSQL+=", 'I' AS st_regRep";//st_regRep
                        strSQL+=" WHERE NOT EXISTS(";
                        strSQL+="    SELECT *FROM tbm_detConIntMovInv ";
                        strSQL+="    WHERE co_emp=" + intCodEmpDxP + " AND co_loc=" + intCodLocDxP + "";
                        strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+="    AND co_reg=" + j + "";
                        strSQL+=" );";
                        strSQLUpd+=strSQL;
                        j++;
//                    }
                }
                System.out.println("insertarDetConIntMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar la relación del detalle de un registro y cargos a pagar.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarRelDetConIntCarPag(){
        boolean blnRes=true;
        int j=1;
        String strSQLUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                objTblMod.removeEmptyRows();
                for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    String strInsPed=objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_INS_PED));
                    String strTblInsPed="";
                    switch(strInsPed){
                        case "0": strTblInsPed="NotPed";  break;
                        case "1": strTblInsPed="PedEmb";  break;
                        case "2": strTblInsPed="MovInv";  break;
                    }     
                    
                    strSQL="";
                    strSQL+=" INSERT INTO tbr_detConIntCarPagInsImp";
                    strSQL+=" ( co_emp,co_loc,co_tipDoc,co_doc,co_reg";
                    strSQL+="  ,co_empRel"+strTblInsPed+",co_locRel"+strTblInsPed+",co_tipDocRel"+strTblInsPed+",co_docRel"+strTblInsPed+",co_regRel"+strTblInsPed+""; 
                    strSQL+=" )";
                    strSQL+="  SELECT ";
                    strSQL+="  " + intCodEmpDxP + " AS co_emp";                 //co_emp
                    strSQL+=", " + intCodLocDxP + " AS co_loc";                 //co_loc
                    strSQL+=", " + txtCodTipDoc.getText() + " AS co_tipDoc";    //co_tipDoc
                    strSQL+=", " + txtCodDoc.getText() + " AS co_doc";          //co_doc
                    strSQL+=", " + j + " AS co_reg";                            //co_reg
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED)) + " AS co_empRel"+strTblInsPed+"";           //co_empRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED)) + " AS co_locRel"+strTblInsPed+"";           //co_locRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED)) + " AS co_tipDocRel"+strTblInsPed+"";     //co_tipDocRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED)) + " AS co_docRel"+strTblInsPed+"";           //co_docRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG_CAR)) + " AS co_regRel"+strTblInsPed+"";           //co_regRel
                    strSQL+=" WHERE NOT EXISTS( "; 
                    strSQL+="    SELECT * FROM tbr_detConIntCarPagInsImp ";
                    strSQL+="    WHERE co_emp=" + intCodEmpDxP + " AND co_loc=" + intCodLocDxP + "";
                    strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="    AND co_reg=" + j + "";
                    strSQL+=" );";
                    j++;
                    strSQLUpd+=strSQL;
                }
                System.out.println("insertarRelDetConIntCarPag: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarPagMovInv(){
        boolean blnRes=true;
        int j=1;
        String strSQLUpd="";
        BigDecimal bgdMonPag= new BigDecimal("-1");
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_NO_EDI);
                objTblModForPag.removeEmptyRows();
                for (int i=0;i<objTblModForPag.getRowCountTrue();i++){
                    bgdMonPag=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_MON_PAG).toString()));
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_pagMovInv(";
                    strSQL+="            co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_diaCre, fe_ven";
                    strSQL+="          , co_tipRet, nd_porRet, tx_aplRet, nd_basImp, mo_pag, ne_diaGra";
                    strSQL+="          , nd_abo, st_sop, st_entSop, st_pos, co_banChq, tx_numCtaChq";
                    strSQL+="          , fe_recChq, nd_monChq, co_proChq, st_reg";
                    strSQL+="          , fe_ree, co_usrRee, tx_comRee, st_autPag, co_ctaAutPag, tx_obs1";
                    strSQL+="          , tx_numChq, tx_numSer, tx_numAutSRI, tx_fecCad, fe_venChq, nd_valIva, tx_codSRI, st_regRep";
                    if (dtpFecCar.isFecha()){
                        strSQL+="      , fe_venChqAutPag";
                    }
                    strSQL+=" )";         
                    strSQL+=" VALUES (";
                    strSQL+="  " + intCodEmpDxP;    //co_emp
                    strSQL+=", " + intCodLocDxP;    //co_loc
                    strSQL+=", " + txtCodTipDoc.getText();  //co_tipDoc
                    strSQL+=", " + txtCodDoc.getText();     //co_doc
                    strSQL+=", " + j;                       //co_reg
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_DIA_CRE) + ""; //ne_diaCre
                    strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_FEC_VEN).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  )+ ""; //fe_ven
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_TIP_RET) + ""; //co_tipRet
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET) + "";     //nd_porRet
                    strSQL+=", " + objUti.codificar(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_EST_APL)) + ""; //tx_aplRet
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_BAS_IMP).toString(), objParSis.getDecimalesMostrar()) + ""; //nd_basImp
                    strSQL+=", " + objUti.redondearBigDecimal(bgdMonPag, objParSis.getDecimalesMostrar()) + ""; //mo_pag
                    strSQL+=", 0";                        //ne_diaGra
                    strSQL+=", " + BigDecimal.ZERO + "";  //nd_abo
                    strSQL+=", 'N'"; //st_sop
                    strSQL+=", 'N'"; //st_entSop
                    strSQL+=", 'N'"; //st_pos
                    strSQL+=", Null";//co_banChq
                    strSQL+=", Null";//tx_numCtaChq
                    strSQL+=", Null";//fe_recChq
                    strSQL+=", Null";//nd_monChq
                    strSQL+=", Null";//co_proChq
                    strSQL+=", 'A'"; //st_reg
                    strSQL+=", Null";//fe_ree
                    strSQL+=", Null";//co_usrRee
                    strSQL+=", Null";//tx_comRee
                    strSQL+=", 'N'"; //st_autPag
                    strSQL+=", Null";//co_ctaAutPag
                    strSQL+=", Null";//tx_obs1
                    strSQL+=", Null";//tx_numChq
                    strSQL+=", Null";//tx_numSer
                    strSQL+=", Null";//tx_numAutSRI
                    strSQL+=", Null";//tx_fecCad
                    strSQL+=", Null";//fe_venChq
                    strSQL+=", " + BigDecimal.ZERO + "";//nd_valIva
                    strSQL+=", Null";//tx_codSRI
                    strSQL+=", 'I'"; //st_regRep
                    if (dtpFecCar.isFecha()){
                        strSQL+=", '" + objUti.formatearFecha(dtpFecCar.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_Car
                    }
                    strSQL+=");";
                    j++;
                    strSQLUpd+=strSQL;
                }
                System.out.println("insertarPagMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
    
    private boolean insertarDiario(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if (objAsiDia.insertarDiario(con, intCodEmpDxP, intCodLocDxP, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "O" ))  
                {
                    System.out.println("insertarDiario");
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
     * Función que permite relación entre Pago al exterior e Instancia del Pedido de Importación
     * @return 
     */
    private boolean insertarCabSegMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                objTblMod.removeEmptyRows();
                for (int i=0;i<objTblMod.getRowCountTrue();i=i+INT_NUM_TOT_FIL_PED){
                    stm=con.createStatement();
                    objCodSeg=null;
                    String strInsPed=objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_INS_PED));
                    String strTblInsPed=""; 
                    int intInsPed=-1;
                    switch(strInsPed){
                        case "0": strTblInsPed="NotPedImp";   intInsPed=1;      break;
                        case "1": strTblInsPed="PedEmbImp";   intInsPed=2;      break;
                        case "2": strTblInsPed="MovInv";      intInsPed=8;      break;  /* Cuando la instancia es el ingreso por importación, se debe relacionar con tbm_CabDia */
                    }     
                    
                    //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                    strSQL="";
                    strSQL+=" SELECT *FROM(";
                    strSQL+=" 	SELECT a3.co_seg AS co_seg"+strTblInsPed+", a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" 	FROM ( ";
                    strSQL+=" 	   tbm_cab"+strTblInsPed+" AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                    strSQL+="      ON a1.co_emp=a3.co_empRelCab"+strTblInsPed+" AND a1.co_loc=a3.co_locRelCab"+strTblInsPed+"";
                    strSQL+=" 	   AND a1.co_tipDoc=a3.co_tipDocRelCab"+strTblInsPed+" AND a1.co_doc=a3.co_docRelCab"+strTblInsPed+"";
                    strSQL+=" 	)";
                    strSQL+=" 	WHERE a1.co_emp=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED)) ;
                    strSQL+=" 	AND a1.co_loc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED)) ;
                    strSQL+=" 	AND a1.co_tipDoc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED)) ;
                    strSQL+=" 	AND a1.co_doc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED)) ;
                    strSQL+=" ) AS b1";    
                    System.out.println("insertarCabSegMovInv: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        objCodSeg=rst.getObject("co_seg"+strTblInsPed+"");  //Ya existe seguimiento del pedido.
                    }
                    rst.close();
                    rst=null;
                    stm.close();
                    stm=null;
                    
                    //Insertar en Seguimiento - Relación: Documento por pagar vs Instancia Pedido
                    if(!objSegMovInv.setSegMovInvCompra(con, objCodSeg 
                                                       ,  5, intCodEmpDxP, intCodLocDxP, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "Null"
                                                       ,  intInsPed
                                                       ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED).toString() )
                                                       ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED).toString() )
                                                       ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED).toString() )
                                                       ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED).toString() ) , "Null"
                       ))
                    {
                        blnRes=false;
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
    
    private boolean enviarCorEle()
    {
        boolean blnRes=false;
        int intCodCfgCorEle=13;
        String strBan="BANCO INTERNACIONAL";
        BigDecimal bgdValTotDoc=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesMostrar());
        BigDecimal bgdValTotCom=objUti.redondearBigDecimal(new BigDecimal(txtValCom.getText()==null?"0":(txtValCom.getText().equals("")?"0":txtValCom.getText())) , objParSis.getDecimalesMostrar());
        BigDecimal bgdValTotInt=objUti.redondearBigDecimal(new BigDecimal(txtValInt.getText()==null?"0":(txtValInt.getText().equals("")?"0":txtValInt.getText())) , objParSis.getDecimalesMostrar());
        String strSubject=""+txaObs2.getText()+" "+lblNomEmpImp.getText()+" ---- $ "+bgdValTotDoc+" ---- "+txtNomPrvExt.getText()+" - "+strBan;  
        int intCodCarPag=-1;
        
        
        BigDecimal bgdValFac=BigDecimal.ZERO;
        BigDecimal bgdValISDCreTri=BigDecimal.ZERO;
        BigDecimal bgdValISDGto=BigDecimal.ZERO;
        BigDecimal bgdSumTot=BigDecimal.ZERO;
        
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
            
            //Arma Cabecera Tabla
            String strMsg ="";
            strMsg+="<HTML> ";
            strMsg+=" <HEAD> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> </HEAD>";
            strMsg+=" <BODY>"; 
            strMsg+="<STRONG> "+lblNomEmpImp.getText()+" *** <BR><BR>";
            strMsg+="<STRONG> REALIZAR PAGO POR:  "+strBan+"  <BR><BR>";
            strMsg+=""+txtDesCorTipDoc.getText()+": "+txtDesLarTipDoc.getText().toUpperCase()+"  # "+txtNumDoc.getText()+" <BR><BR>";
            strMsg+=""+lblPrvExt.getText()+"      "+txtNomPrvExt.getText().toUpperCase()+" <BR>";
            strMsg+="<STRONG> Forma de Pago: </STRONG> "+txtNomForPag.getText().toUpperCase()+"  -  <STRONG>Valor Total: </STRONG> $ "+bgdValTotDoc+" <BR>";
            
            if(bgdValTotInt.compareTo(BigDecimal.ZERO)>0) 
                strMsg+="<BR><STRONG> Interes en Factura: </STRONG> $ "+bgdValTotInt+" <BR>";

            strMsg+=" <BR>Detalle del pago: <BR><BR>";
            strMsg+="<TABLE BORDER=1> ";   
            for (int i=0;i<objTblMod.getRowCountTrue();i++) {
                intCodCarPag=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG)==null?"":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG).toString()));
                if(intCodCarPag==22){
                    bgdValFac=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_VAL_PAG_EXT)==null)?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()),objParSis.getDecimalesMostrar());
                    bgdValISDCreTri=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt( (i+1),INT_TBL_DAT_VAL_PAG_EXT)==null)?"0":objTblMod.getValueAt((i+1), INT_TBL_DAT_VAL_PAG_EXT).toString()),objParSis.getDecimalesMostrar());
                    bgdValISDGto=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt( (i+2),INT_TBL_DAT_VAL_PAG_EXT)==null)?"0":objTblMod.getValueAt((i+2), INT_TBL_DAT_VAL_PAG_EXT).toString()),objParSis.getDecimalesMostrar());

                    bgdSumTot=bgdValFac.add(bgdValISDCreTri).add(bgdValISDGto);
                    strMsg+="  <tr> <td align='center'> <STRONG>"+objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED_IMP).toString()+" </STRONG> </td> <td align='right'> <STRONG> $ "+bgdSumTot+"</STRONG> </td> </tr>"; 
                    strMsg+="  <tr> <td>"+objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CAR_PAG).toString()+"</td> <td align='right'>$ "+bgdValFac+"</td> </tr>"; 
                    strMsg+="  <tr> <td>"+objTblMod.getValueAt((i+1),INT_TBL_DAT_NOM_CAR_PAG).toString()+"</td> <td align='right'>$ "+bgdValISDCreTri+"</td> </tr>"; 
                    strMsg+="  <tr> <td>"+objTblMod.getValueAt((i+2),INT_TBL_DAT_NOM_CAR_PAG).toString()+"</td> <td align='right'>$ "+bgdValISDGto+"</td> </tr>"; 
                }

            }
            if(bgdValTotCom.compareTo(BigDecimal.ZERO)>0) 
                strMsg+="  <tr> <td align='center'> <STRONG>COMISION</STRONG> </td> <td align='right'> <STRONG> $ "+bgdValTotCom+"</STRONG> </td> </tr>"; 
            strMsg+=" </TABLE> ";
            strMsg+=" </BODY>";
            strMsg+=" </HTML>";
            //Envía el correo.
            if(strMsg.length()>0){
                if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSubject )){
                    mostrarMsgInf("<HTML>Correo de notificación de Pago a proveedores del exterior.</HTML>");
                    blnRes=true;
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
     * Genera Asiento de Diario del documento
     * @return 
     */
    private boolean generarAsiDia()
    {  
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        intCodCtaDocPag=-1;
        strNumCtaDocPag="";
        strNomCtaDocPag="";
        try
        { 
            if(objTooBar.getEstado()=='n') //Se valida que solo genere asiento de diario cuando se inserte y NO modo Consulta.
            { 
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conLoc!=null){
                    stmLoc=conLoc.createStatement();               

                    //Asiento de DxP: Obtiene Cuenta Iva y Total.
                    strSQL ="";
                    strSQL+=" SELECT x.co_ctaDocPag, x.tx_codCtaDocPag, x.tx_nomCtaDocPag";
                    strSQL+=" FROM(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_ctaHab as co_ctaDocPag, a1.co_tipDoc";
                    strSQL+=" 	     , a2.tx_codCta AS tx_codCtaDocPag, a2.tx_desLar AS tx_nomCtaDocPag";
                    strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                    strSQL+=" 	INNER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp AND a1.co_ctaHab=a2.co_cta";
                    strSQL+="   WHERE a1.co_emp=" + intCodEmpDxP + "";
                    strSQL+="   AND a1.co_loc=" + intCodLocDxP + "";
                    strSQL+="   AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" ) AS x";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    if(rstLoc.next()){
                        intCodCtaDocPag=rstLoc.getInt("co_ctaDocPag");
                        strNumCtaDocPag=rstLoc.getString("tx_codCtaDocPag");
                        strNomCtaDocPag=rstLoc.getString("tx_nomCtaDocPag");
                    }
                    else{
                        mostrarMsgInf("<HTML>Configurar cuentas contables en el Tipo de Documento.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        limpiarTabla();
                        blnRes=false;
                    }
                    rstLoc.close();
                    rstLoc=null;                    
                    stmLoc.close();
                    stmLoc=null;
                    conLoc.close();
                    conLoc=null;
                }
                
                if( new BigDecimal(txtValDoc.getText()).compareTo(BigDecimal.ZERO)>=0 )
                {
                    //Se inicializa el asiento de diario
                    objAsiDia.inicializar();
                    vecDatDia.clear();

                    //Debe
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN,     "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_DEB,     ""+ new BigDecimal(txtValDoc.getText()) );
                    vecRegDia.add(INT_VEC_DIA_HAB,     "0");
                    vecRegDia.add(INT_VEC_DIA_REF,     "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);

                    //Haber
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN,     "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_DEB,     "0" );
                    vecRegDia.add(INT_VEC_DIA_HAB,     ""+ new BigDecimal(txtValDoc.getText()));
                    vecRegDia.add(INT_VEC_DIA_REF,     "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }
                objAsiDia.setDetalleDiario(vecDatDia);
                objAsiDia.setGeneracionDiario((byte)2);
                actualizarGlo();
            }
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }  
        catch(Exception e){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private boolean actualizarGlo(){
        boolean blnRes=true;
        String strObs="";
        try{
            int intTotFil=objTblMod.getRowCountTrue();
            for(int i=0; i<intTotFil; i=i+INT_NUM_TOT_FIL_PED){
                strObs+="" + (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_IMP).toString()) + "";
                if(i!= (intTotFil-INT_NUM_TOT_FIL_PED) ){
                    strObs+=" / ";
                }
            }
            strObs+="  - Pago al exterior";
            txaObs2.setText(strObs);
            objAsiDia.setGlosa(strObs);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
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
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarDatCar()){  
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        } 
        catch (java.sql.SQLException e) { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar los datos de la carta.
     * Sólo se actualizará cuando no exista transferencia bancaria.
     * @return true: Si se pudo actualizar
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDatCar(){
        boolean blnRes=true;
        BigDecimal bgdValCar;
        String strFecUltMod=""; 
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;                
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());                
                bgdValCar=objUti.redondearBigDecimal(new BigDecimal(txtValCar.getText()==null?"0":(txtValCar.getText().equals("")?"0":txtValCar.getText())) , objParSis.getDecimalesMostrar());
                
                //Armar la sentencia SQL.
                strSQL="";
                /* Actualiza Valor de la Carta */
                strSQL+=" UPDATE tbm_cabMovInv ";
                strSQL+=" SET nd_ValCar="+bgdValCar;
                if(chkMosParRes.isSelected()) //tx_ate: Se usará para indicar si muestra el párrafo de responsabilidad.
                    strSQL+=", tx_ate='S'";   //tx_ate
                else
                    strSQL+=", tx_ate=Null";  //tx_ate    
                
                strSQL+=", fe_ultMod='" + strFecUltMod + "'";  
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + ""; 
                strSQL+=", tx_commod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ""; 
                
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ;";
                /* Actualiza Fecha de la Carta */
                if (dtpFecCar.isFecha()){
                    strSQL+=" UPDATE tbm_pagMovInv ";
                    strSQL+=" SET fe_venChqAutPag='" + objUti.formatearFecha(dtpFecCar.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_Car
                    strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                    strSQL+=" ;";
                }
                System.out.println("actualizarDatCar: "+strSQL);
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
                    if(anularDiario()){
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
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
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
                strSQL+=" UPDATE tbm_CabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
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
       
    private boolean anularDiario(){
        boolean blnRes=false;
        try{ 
            if(con!=null){
                if(objAsiDia.anularDiario(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                        ))
                {
                    //System.out.println("anularDiario");
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
        boolean blnRpt=false;
        int intNumTotRpt=0;
        String strRutImg="";
        String strSQLRep="", strSQLSubRep="";
        String strRutRpt="", strNomRpt="", strFecHorSer="";        
        java.util.Map mapPar = new java.util.HashMap();
        String strNumDia="", strDesCor="", strDesLar="", strUsrIng="", strUsrMod="";
        try
        {
            con =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
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
                    for (int i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            blnRpt=false;
                            strRutRpt=objRptSis.getRutaReporte(i);
                            strNomRpt=objRptSis.getNombreReporte(i);
                     
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                            {   
                                case 276: 
                                    //<editor-fold defaultstate="collapsed" desc="/* Otros movimientos bancarios (Asientos Diario)... */"> 
                                    stm=con.createStatement();
                                    //Cabecera de Reporte 
                                    strSQL ="";
                                    strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.tx_numDia, a.fe_Dia, a.tx_glo, a5.tx_descor, a5.tx_deslar";
                                    strSQL+="      , a.co_usring, a6.tx_usr AS tx_nomUsrIng, a.co_usrMod, a7.tx_usr AS tx_nomUsrMod, a.st_Reg, a.st_imp";
                                    strSQL+=" FROM tbm_cabDia as a";
                                    strSQL+=" INNER JOIN tbm_detDia as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_dia=a1.co_dia";
                                    //strSQL+=" INNER JOIN tbm_docgenotrmovban AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                                    strSQL+=" INNER JOIN tbm_cabSegMovInv AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_diaRelCabDia";
                                    strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a5 ON a.co_emp=a5.co_emp AND a.co_loc=a5.co_loc AND a.co_tipDoc=a5.co_tipDoc ";
                                    strSQL+=" INNER JOIN tbm_usr AS a6 ON a3.co_usring=a6.co_usr "; /* El usuario de ingreso, siempre se presentará el usuario que ingreso el OPIMIM (DXP)*/
                                    strSQL+=" INNER JOIN tbm_usr AS a7 ON a.co_usrMod=a7.co_usr ";  /* El usuario de modificación siempre se presentará el usuario que autorizó el OPIMP, o el usuario que modificó el Asiento de diario*/
                                    strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                    strSQL+=" AND a.co_tipDoc="+objZafImp.INT_COD_TIP_DOC_TRA_BAN_EXT;
                                    strSQL+=" AND a.st_reg NOT IN ('E', 'I')";
                                    strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.fe_Dia, a.tx_glo, a5.tx_Descor, a5.tx_Deslar";
                                    strSQL+="      , a.co_usring, a6.tx_usr, a.co_usrMod, a7.tx_usr, a.st_Reg, a.st_imp";
                                    strSQLRep=strSQL;
                                    rst = stm.executeQuery(strSQLRep);
                                    if (rst.next()) {
                                        blnRpt = true;
                                        strNumDia = rst.getString("tx_numdia");
                                        strDesCor = rst.getString("tx_descor");
                                        strDesLar = rst.getString("tx_deslar");
                                        strUsrIng = rst.getString("tx_nomUsrIng");
                                        strUsrMod = rst.getString("tx_nomUsrMod");
                                    }
                                    rst.close();
                                    rst=null;
                                    stm.close();
                                    stm=null;

                                    //Detalle de Reporte 
                                    strSQL ="";
                                    strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a1.co_reg";
                                    strSQL+="      , a4.co_cta, a4.tx_CodCta, a4.tx_Deslar AS tx_nomCta, a1.nd_monDeb, a1.nd_monHab";
                                    strSQL+=" FROM tbm_cabDia as a";
                                    strSQL+=" INNER JOIN tbm_detDia as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_dia=a1.co_dia";
                                    //strSQL+=" INNER JOIN tbm_docgenotrmovban AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                                    strSQL+=" INNER JOIN tbm_cabSegMovInv AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_diaRelCabDia";
                                    strSQL+=" INNER JOIN tbm_cabMovInv as a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                                    strSQL+=" INNER JOIN tbm_placta as a4 ON a.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta ";
                                    strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                    strSQL+=" AND a.co_tipDoc="+objZafImp.INT_COD_TIP_DOC_TRA_BAN_EXT;
                                    strSQL+=" AND a.st_reg NOT IN ('E', 'I')";
                                    strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a1.co_reg";
                                    strSQL+="        , a4.co_cta, a4.tx_CodCta, a4.tx_Deslar, a1.nd_monDeb, a1.nd_monHab"; 
                                    strSQLSubRep=strSQL;

                                    //Inicializar los parametros que se van a pasar al reporte.        
                                    mapPar.put("strCamAudRpt", "" + ( strUsrIng  + " / " + strUsrMod + " / " + objParSis.getNombreUsuario() ) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + "   "+strVer);
                                    mapPar.put("codUsrImp", "" + objParSis.getCodigoUsuario());
                                    mapPar.put("codEmp", intCodEmpTraBan);
                                    mapPar.put("codLoc", intCodLocTraBan);
                                    mapPar.put("codTipDoc", intCodTipDocTraBan);
                                    mapPar.put("codDia", intCodDocTraBan);
                                    mapPar.put("nomEmp", getNomEmp(intCodEmpTraBan));
                                    mapPar.put("rucEmp", getRucEmp(intCodEmpTraBan));
                                    mapPar.put("nomCorTipDoc", strDesCor);
                                    mapPar.put("nomLarTipDoc", strDesLar);
                                    mapPar.put("fecAct", "" + strFecHorSer);
                                    mapPar.put("fecDoc", objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                                    mapPar.put("numDia", strNumDia);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);

                                    //</editor-fold>
                                    break;
                            }
                            if(blnRpt)
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                        }
                    }
                }
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }  
    
    private String getNomEmp(int intCodEmp) {
        String strNomEmp= "";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSqlAux="";
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSqlAux = " SELECT tx_nom FROM tbm_emp WHERE co_emp=" + intCodEmp;
                rstLoc = stmLoc.executeQuery(strSqlAux);
                if (rstLoc.next()) {
                    strNomEmp = rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);} 
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return strNomEmp;
    }

    private String getRucEmp(int intCodEmp) {
        String strRucEmp= "";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSqlAux="";
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSqlAux=" SELECT tx_ruc FROM tbm_emp WHERE co_emp=" + intCodEmp;
                rstLoc = stmLoc.executeQuery(strSqlAux);
                if (rstLoc.next()) {
                    strRucEmp = rstLoc.getString("tx_ruc");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);} 
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return strRucEmp;
    }       
    
    /**
     * Función que valida en cada fila que la cantidad ingresada en la columna "Val.Pag." no este fuera del rango permitido
     * El rango minimo es 0.995 y el rango máximo es 1.005 
     * @return 
     */
    private boolean validarRangoValor(){
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();  
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    BigDecimal bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()));
                    
                    //Verificar
                    strSQL ="";
                    strSQL+=" SELECT * ";
                    strSQL+=" FROM ("; 
                    strSQL+="    SELECT ("+bgdCan+" /(CASE WHEN a.nd_valCarPag IS NULL THEN 0 ELSE a.nd_valCarPag END) )AS valorCalculadoRango";
                    strSQL+="          , CAST (0.995 AS NUMERIC) AS min, CAST (1.005 AS NUMERIC) AS max";
                    strSQL+="    FROM tbm_carPagPedEmbImp as a";
//                    strSQL+="    WHERE a.co_emp="+intCodEmpPedEmb;
//                    strSQL+="    AND a.co_loc="+intCodLocPedEmb;
//                    strSQL+="    AND a.co_tipDoc="+intCodTipDocPedEmb;
//                    strSQL+="    AND a.co_doc="+intCodDocPedEmb;
                    strSQL+="    AND a.co_carPag="+objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CAR_PAG));
                    strSQL+=" ) AS x";
                    strSQL+=" WHERE (CASE WHEN (x.valorCalculadoRango < x.min) or (x.valorCalculadoRango > x.max) THEN TRUE ELSE FALSE END )  ";
                    rstLoc=stmLoc.executeQuery(strSQL); 
                    if(rstLoc.next()){
                        blnRes=false;
                    }
                    rstLoc.close();
                    rstLoc=null;
                }                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        } 
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }        
        return blnRes;    
    }    

    private void limpiarTabla() 
    {
        objTblMod.setDataModelChanged(false);
        objTblMod.removeAllRows();
        objAsiDia.inicializar();
        objAsiDiaTraBan.inicializar();
        lblNomEmpImp.setText("***");
    }    
    
    /**
     * Esta función valida que todos los pedidos tengan el mismo importador.
     * @param intFilSel
     * @return true: Si es el mismo importador
     * <BR>false: En el caso contrario.
     */
    private boolean validarMismoImportadorPedido(int intFilSel){
        boolean blnRes = true;
        int intCodEmpFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP_IMP).toString());
        
        if (intCodEmpFilSel!=intCodEmpDxP){
            blnRes=false;
            mostrarMsgInf("<HTML>El pedido no puede seleccionarse porque tiene un empresa importadora distinta a la empresa de la cuenta bancaria seleccionada.</HTML>");
            objTblMod.removeRow(intFilSel); /*Elimina el cargo de Valor de la Factura, que se agrega cada vez que se elige un pedido. */
            objTblMod.removeRow(intFilSel); /*Elimina el cargo de ISD, que se agrega cada vez que se elige un pedido. */
            objTblEdi.seleccionarCelda(INT_TBL_DAT_NUM_PED_IMP);
        }
        return blnRes;
    }   
    
//    private boolean validarMismoImportadorPedidoAntes(int intFilSel){
//        boolean blnRes = true;
//        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
//            if (objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_IMP) != null){
//                if (i != intFilSel){
//                    if (!objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_IMP).toString().equals(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP_IMP).toString())){
//                        blnRes = false;
//                        mostrarMsgInf("<HTML>El pedido no puede seleccionarse porque tiene un empresa importadora distinta" + (i+1) + ".</HTML>");
//                        objTblMod.removeRow(intFilSel); /*Elimina el cargo de Valor de la Factura, que se agrega cada vez que se elige un pedido. */
//                        objTblMod.removeRow(intFilSel); /*Elimina el cargo de ISD, que se agrega cada vez que se elige un pedido. */
//                        objTblEdi.seleccionarCelda(INT_TBL_DAT_NUM_PED_IMP);
//                        break;
//                    }
//                }
//            }
//        }
//        return blnRes;
//    }   
    
    /**
     * Función que valida si ya existe una transferencia bancaria a proveedores del exterior.
     * @return true: Si existe Transferencia Bancaria.
     * <BR> false: Caso contrario.
     */
    private boolean existeTransferenciaBancariaExterior() {
        boolean blnRes=false;
        Connection conTraBan;
        Statement stmTraBan;
        ResultSet rstTraBan;
        try{
            conTraBan=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTraBan!=null){
                stmTraBan=conTraBan.createStatement();
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia";
                strSQL+="      , a2.co_emp as co_empMovBan, a2.co_loc as co_locMovBan, a2.co_tipDoc as co_tipDocMovBan, a2.co_doc as co_docMovBan";
                strSQL+="      , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";     
                strSQL+=" FROM tbm_cabDia as a";
                strSQL+=" INNER JOIN tbm_docGenOtrMovBan AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                strSQL+=" INNER JOIN tbm_cabMovInv as a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)";
                strSQL+=" WHERE a.st_reg NOT IN ('E', 'I')";
                strSQL+=" AND a4.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP); 
                strSQL+=" AND a4.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC); 
                strSQL+=" AND a4.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC); 
                strSQL+=" AND a4.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC); 
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia";   
                strSQL+="        , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc";  
                strSQL+="        , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                rstTraBan=stmTraBan.executeQuery(strSQL);
                if(rstTraBan.next())
                    blnRes=true;

                conTraBan.close();
                conTraBan=null;
                stmTraBan.close();
                stmTraBan=null;
                rstTraBan.close();
                rstTraBan=null;
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
     * Función que valida que el valor de la factura del pedido, no exceda el valor ingresado en el cargo a pagar.
     * Está validación se realiza incluyendo los valores de DxP anteriores.
     * @return 
     */
    private boolean validarValorFactura()
    {
        Boolean blnRes=true;
        BigDecimal bgdValDxPAct = BigDecimal.ZERO; /* Valor de factura DxP actual. */
        BigDecimal bgdValDxPAnt = BigDecimal.ZERO; /* Valor de factura DxP anteriores. */
        BigDecimal bgdValTotDxP = BigDecimal.ZERO; /* Incluye valor de todos los DxP + Valor DxP Actual. */
        BigDecimal bgdValCarPag = BigDecimal.ZERO; /* Valor de factura registrado en el cargo del pedido, es decir, lo máximo permitido. */
        try{
            for (int i=0;i<objTblMod.getRowCountTrue();i=i+INT_NUM_TOT_FIL_PED){
                bgdValDxPAct=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_EXT).toString()));
                bgdValDxPAnt=getValTotDxP(i);
                bgdValTotDxP=bgdValDxPAct.add(bgdValDxPAnt);
                bgdValCarPag=getValCarPagPedImp(i);
                
                System.out.println("bgdValDxPAct: " + bgdValDxPAct);
                System.out.println("bgdValDxPAnt: " + bgdValDxPAnt);
                System.out.println("bgdValTotDxP: " + bgdValTotDxP);
                System.out.println("bgdValCarPag: " + bgdValCarPag);

                if(bgdValCarPag.compareTo(bgdValTotDxP)<0){ /* Si el valor del cargo es menor a lo pagado, retornar falso*/
                    blnRes=false;
                    
                    mostrarMsgInf("<HTML> El pedido <FONT COLOR=\"blue\">" + objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_IMP)  + "</FONT> tiene valor aplicados que suman" + objUti.redondearBigDecimal(bgdValDxPAnt, objParSis.getDecimalesMostrar()) + "</HTML>"   );
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e );
        }
        return blnRes;
    }    

    /**
     * Función que obtiene el valor total de los DxP enlazados al pedido.
     * Esta función solo lee los DxP relacionados al pedidos, los pedidos antiguos no se reflejan.
     * @param fila 
     * @return bgdValDxP
     */
    private BigDecimal getValTotDxP(int fila)
    {              
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        BigDecimal bgdValDxP=new BigDecimal("0");
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                String strInsPed=objUti.parseString(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_INS_PED));
                String strTblInsPed="", strImp="";                
                switch(strInsPed){ 
                    case "0": strTblInsPed="NotPed"; strImp="Imp"; break; 
                    case "1": strTblInsPed="PedEmb"; strImp="Imp"; break; 
                    case "2": strTblInsPed="MovInv"; strImp="";    break; 
                }   
                
                //Armar sentencia SQL 
                strSQL="";
                strSQL+=" SELECT a.co_empRel"+strTblInsPed+" AS co_emp, a.co_locRel"+strTblInsPed+" AS co_loc, a.co_tipDocRel"+strTblInsPed+" AS co_tipDoc, a.co_docRel"+strTblInsPed+" AS co_doc";
                strSQL+="      , a1.co_CarPag, a2.tx_tipCarPag, SUM (a3.nd_tot) AS nd_totDxP";
                strSQL+=" FROM tbr_detConIntCarPagInsImp AS a INNER JOIN tbm_carPag"+strTblInsPed+""+strImp+" AS a1";
                strSQL+=" ON a.co_empRel"+strTblInsPed+"=a1.co_emp AND a.co_locRel"+strTblInsPed+"=a1.co_loc AND a.co_tipDocRel"+strTblInsPed+"=a1.co_tipDoc AND a.co_docRel"+strTblInsPed+"=a1.co_Doc AND a.co_regRel"+strTblInsPed+"=a1.co_reg";
                strSQL+=" INNER JOIN tbm_carPagImp AS a2 ON a1.co_carPag=a2.co_carPag";
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a3 ON a.co_emp=a3.co_emp AND a.co_loc=a3.co_loc AND a.co_tipDoc=a3.co_TipDoc AND a.co_doc=a3.co_doc AND a.co_Reg=a3.co_Reg";
                strSQL+=" WHERE a2.tx_tipCarPag IN ('V')";
                strSQL+=" AND a.co_empRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP_PED);
                strSQL+=" AND a.co_locRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC_PED);
                strSQL+=" AND a.co_tipDocRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIPDOC_PED);
                strSQL+=" AND a.co_docRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC_PED);
                strSQL+=" GROUP BY a.co_empRel"+strTblInsPed+", a.co_locRel"+strTblInsPed+", a.co_tipDocRel"+strTblInsPed+", a.co_docRel"+strTblInsPed+", a1.co_CarPag, a2.tx_tipCarPag";
                System.out.println("getValTotDxP: " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    bgdValDxP=new BigDecimal(rstLoc.getString("nd_totDxP"));
                }  
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdValDxP;
    }
    
    /**
     * Función que obtiene el valor total de los DxP enlazados a la cuenta contable.
     * Está función refleja todos los DxP de la cuenta contable.
     * @param fila 
     * @return bgdValDxP
     */
    private BigDecimal getValTotDxPRose(int fila)
    {              
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        BigDecimal bgdValDxP=new BigDecimal("0");
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                String strInsPed=objUti.parseString(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_INS_PED));
                String strTblInsPed="", strTblImp="";                
                switch(strInsPed){ 
                    case "0": strTblInsPed="NotPed"; strTblImp="Imp"; break;  
                    case "1": strTblInsPed="PedEmb"; strTblImp="Imp"; break;  
                    case "2": strTblInsPed="MovInv"; strTblImp="";    break;  
                }   
                
                //Armar sentencia SQL 
                strSQL="";
                strSQL+=" SELECT a.co_empRel"+strTblInsPed+" AS co_emp, a.co_locRel"+strTblInsPed+" AS co_loc, a.co_tipDocRel"+strTblInsPed+" AS co_tipDoc, a.co_docRel"+strTblInsPed+" AS co_doc";
                strSQL+="      , a1.co_CarPag, a2.tx_tipCarPag, SUM (a3.nd_tot) AS nd_totDxP"; 
                strSQL+=" FROM tbr_detConIntCarPagInsImp AS a INNER JOIN tbm_carPag"+strTblInsPed+""+strTblImp+" AS a1"; 
                strSQL+=" ON a.co_empRel"+strTblInsPed+"=a1.co_emp AND a.co_locRel"+strTblInsPed+"=a1.co_loc AND a.co_tipDocRel"+strTblInsPed+"=a1.co_tipDoc AND a.co_docRel"+strTblInsPed+"=a1.co_Doc AND a.co_regRel"+strTblInsPed+"=a1.co_reg";
                strSQL+=" INNER JOIN tbm_carPagImp AS a2 ON a1.co_carPag=a2.co_carPag";
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a3 ON a.co_emp=a3.co_emp AND a.co_loc=a3.co_loc AND a.co_tipDoc=a3.co_TipDoc AND a.co_doc=a3.co_doc AND a.co_Reg=a3.co_Reg";
                strSQL+=" WHERE a2.tx_tipCarPag IN ('V')";
                strSQL+=" AND a.co_empRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP_PED);
                strSQL+=" AND a.co_locRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC_PED);
                strSQL+=" AND a.co_tipDocRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIPDOC_PED);
                strSQL+=" AND a.co_docRel"+strTblInsPed+"="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC_PED);
                strSQL+=" GROUP BY a.co_empRel"+strTblInsPed+", a.co_locRel"+strTblInsPed+", a.co_tipDocRel"+strTblInsPed+", a.co_docRel"+strTblInsPed+", a1.co_CarPag, a2.tx_tipCarPag";
                System.out.println("getValTotDxP: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    bgdValDxP=new BigDecimal(rstLoc.getString("nd_totDxP"));
                }  
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdValDxP;
    }
    
    /**
     * Función que obtiene el valor total de los DxP enlazados al pedido.
     * @param fila 
     * @return bgdValDxP
     */
    private BigDecimal getValCarPagPedImp(int fila)
    {              
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        BigDecimal bgdValCarPag=new BigDecimal("0");
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                String strInsPed=objUti.parseString(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_INS_PED));
                String strTblInsPed="";                
                switch(strInsPed){ 
                    case "0": strTblInsPed="NotPedImp";  break; 
                    case "1": strTblInsPed="PedEmbImp";  break; 
                    case "2": strTblInsPed="MovInv";  break; 
                }   
                
                //Armar sentencia SQL 
                strSQL="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_CarPag, a1.tx_tipCarPag";
                strSQL+="      , SUM (CASE WHEN a.nd_valCarPag IS NULL THEN 0 ELSE a.nd_valCarPag END) AS nd_valCarPag";
                strSQL+=" FROM tbm_carPag"+strTblInsPed+" AS a"; 
                strSQL+=" INNER JOIN tbm_carPagImp AS a1 ON a.co_carPag=a1.co_carPag";
                strSQL+=" WHERE a1.tx_tipCarPag IN ('V')";
                strSQL+=" AND a.co_emp="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP_PED);
                strSQL+=" AND a.co_loc="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC_PED);
                strSQL+=" AND a.co_tipDoc="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIPDOC_PED);
                strSQL+=" AND a.co_doc="+objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC_PED);
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_CarPag, a1.tx_tipCarPag";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    bgdValCarPag=new BigDecimal(rstLoc.getString("nd_valCarPag"));
                }  
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdValCarPag;
    }
    
    /**
     * Se asigna valor del documento a al valor de la carta
     * @return 
     */
    private boolean asignarValorCarta(){ 
        boolean blnRes=true;

        /* Validar: Valor de la carta */
        BigDecimal bgdValCar=objUti.redondearBigDecimal(new BigDecimal(txtValCar.getText()==null?"0":(txtValCar.getText().equals("")?"0":txtValCar.getText())) , objParSis.getDecimalesMostrar());
        BigDecimal bgdValDoc=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesMostrar());

        /* Si el valor de la carta es menor o igual a 0, entonces se le asigna el valor total del documento. */
        if(bgdValCar.compareTo(BigDecimal.ZERO)<=0) {
            txtValCar.setText( "" + (objUti.redondearBigDecimal(bgdValDoc, objParSis.getDecimalesMostrar())) );
        }                 

        return blnRes;
    }
        
    
    
        
}



