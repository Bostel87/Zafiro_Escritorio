/*
 * ZafAjuInv.java
 *
 *  Created on 02 de septiembre de 2017
 */
package Librerias.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import java.math.BigDecimal;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import java.awt.Color;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.UltDocPrint;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafAjuInv extends javax.swing.JDialog
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP_ING_IMP=2;
    final int INT_TBL_DAT_COD_LOC_ING_IMP=3;
    final int INT_TBL_DAT_COD_TIP_DOC_ING_IMP=4;
    final int INT_TBL_DAT_COD_DOC_ING_IMP=5;
    final int INT_TBL_DAT_COD_REG_ING_IMP=6;
    final int INT_TBL_DAT_COD_ITM_GRP=7;
    final int INT_TBL_DAT_COD_ITM_EMP=8;
    final int INT_TBL_DAT_COD_ITM_MAE=9;
    final int INT_TBL_DAT_COD_ALT_ITM=10;
    final int INT_TBL_DAT_COD_ITM_LET=11;
    final int INT_TBL_DAT_BUT_ITM=12;
    final int INT_TBL_DAT_NOM_ITM=13;
    final int INT_TBL_DAT_DES_COR_UNI_MED=14;
    final int INT_TBL_DAT_TIP_MOV_DES=15;
    final int INT_TBL_DAT_TIP_MOV_LET=16;
    final int INT_TBL_DAT_CAN_AJU=17;
    final int INT_TBL_DAT_COS_UNI_ING_IMP=18;
    final int INT_TBL_DAT_COS_TOT=19;
    final int INT_TBL_DAT_PES_UNI=20;
    final int INT_TBL_DAT_PES_TOT=21;
    
    //Campos utiles para calculos
    final int INT_TBL_DAT_CAN_ING_IMP=22;
    final int INT_TBL_DAT_CAN_CON=23;
    final int INT_TBL_DAT_CAN_TRS=24;
    final int INT_TBL_DAT_EST_EXI_ITM=25;
    final int INT_TBL_DAT_CAN_ING_EGR=26;

    //ArrayList
    private ArrayList arlRegAju, arlDatAju;
    //CONTEO
    private final int INT_ARL_AJU_COD_EMP_CON=0;
    private final int INT_ARL_AJU_COD_LOC_CON=1;
    private final int INT_ARL_AJU_COD_TIP_DOC_CON=2;
    private final int INT_ARL_AJU_COD_DOC_CON=3;
    private final int INT_ARL_AJU_COD_REG_CON=4;
    private final int INT_ARL_AJU_CAN_CON=5;
    private final int INT_ARL_AJU_COD_ITM_MAE=6;
    private final int INT_ARL_AJU_COD_ITM_GRP=7;
    //SOLICITUD DE TRANSFERENCIA
    private final int INT_ARL_AJU_COD_EMP_SOL_TRS=8;
    private final int INT_ARL_AJU_COD_LOC_SOL_TRS=9;
    private final int INT_ARL_AJU_COD_TIP_DOC_SOL_TRS=10;
    private final int INT_ARL_AJU_CAN_SOL_TRS=11;
    //TRANSFERENCIA
    private final int INT_ARL_AJU_COD_EMP_TRS=12;
    private final int INT_ARL_AJU_COD_LOC_TRS=13;
    private final int INT_ARL_AJU_COD_TIP_DOC_TRS=14;
    private final int INT_ARL_AJU_COD_DOC_TRS=15;
    private final int INT_ARL_AJU_CAN_TRS=16;
    //INGRESO POR IMPORTACION - PEDIDO
    private final int INT_ARL_AJU_COD_EMP_ING_IMP=17;
    private final int INT_ARL_AJU_COD_LOC_ING_IMP=18;
    private final int INT_ARL_AJU_COD_TIP_DOC_ING_IMP=19;
    private final int INT_ARL_AJU_COD_DOC_ING_IMP=20;
    private final int INT_ARL_AJU_COD_REG_ING_IMP=21;
    private final int INT_ARL_AJU_NUM_DOC_ING_IMP=22;
    private final int INT_ARL_AJU_FEC_DOC_ING_IMP=23;
    private final int INT_ARL_AJU_NUM_PED_ING_IMP=24;
    private final int INT_ARL_AJU_CAN_ING_IMP=25;
    private final int INT_ARL_AJU_COS_UNI_ING_IMP=26;
    private final int INT_ARL_AJU_PES_UNI_ING_IMP=27;
    private final int INT_ARL_AJU_NOM_ITM_ING_IMP=28;
    private final int INT_ARL_AJU_COD_ALT_ITM_ING_IMP=29;
    private final int INT_ARL_AJU_COD_ITM_LET_ING_IMP=30;
    private final int INT_ARL_AJU_UNI_MED_ING_IMP=31;
    private final int INT_ARL_AJU_COD_ITM_EMP_ING_IMP=32;
    //CANTIDAD QUE SE DEBE AJUSTAR
    private final int INT_ARL_AJU_CAN_AJU=33;
    private final int INT_ARL_AJU_EST_PRO=34;
    private final int INT_ARL_AJU_CAN_ING_IMP_ORI=35;   
    
    //ArrayList: Para arreglo de stock de inventario
    private ArrayList arlRegStkInvItm, arlDatStkInvItm, arlRegDisInvItm, arlDatDisInvItm;
    private static final int INT_ARL_STK_INV_COD_ITM_GRP=0;
    private static final int INT_ARL_STK_INV_COD_ITM_EMP=1;
    private static final int INT_ARL_STK_INV_COD_ITM_MAE=2;
    private static final int INT_ARL_STK_INV_COD_LET_ITM=3;
    private static final int INT_ARL_STK_INV_CAN_ITM=4;
    private static final int INT_ARL_STK_INV_COD_BOD_EMP=5;

    //ArrayList: Tipos de documentos de ajustes
    ArrayList arlRegVisBueUsrTipDoc, arlDatVisBueUsrTipDoc;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_EMP=0;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_LOC=1;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_TIP_DOC=2;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_VIS_BUE=3;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_USR=4;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE=5;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE=6;

    //ArrayList: 
    private int intNumVisBueObl;
    private ArrayList arlRegVisBueUsrTipDocObl, arlDatVisBueUsrTipDocObl;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_OBL_COD_EMP=0;
    final int INT_ARL_COD_VIS_BUE_OBL_OBL_COD_LOC=1;
    final int INT_ARL_COD_VIS_BUE_OBL_OBL_COD_TIP_DOC=2;
    final int INT_ARL_COD_VIS_BUE_OBL_OBL_COD_VIS_BUE=3;

    private final int INT_ARL_ITM_COD_EMP=0;
    private final int INT_ARL_ITM_COD_ITM_GRP=1;
    private final int INT_ARL_ITM_COD_ITM_EMP=2;
    private final int INT_ARL_ITM_COD_ITM_MAE=3;
    private final int INT_ARL_ITM_COD_ALT_ITM=4;
    private final int INT_ARL_ITM_COD_ITM_LET=5;
    private final int INT_ARL_ITM_NOM_ITM=6;
    private final int INT_ARL_ITM_UNI_MED=7;

    //ArrayList: 
    private ArrayList arlRegFilEli, arlDatFilEli;
    final int INT_ARL_FIL_ELI_COD_EMP=0;
    final int INT_ARL_FIL_ELI_COD_ITM_GRP=1;
    final int INT_ARL_FIL_ELI_COD_ITM_EMP=2;
    final int INT_ARL_FIL_ELI_COD_ITM_MAE=3;
    final int INT_ARL_FIL_ELI_COD_REG=4;
    final int INT_ARL_FIL_ELI_EST_ITM=5;

    //ArrayList: 
    private ArrayList arlRegDiaAju, arlDatDiaAju;
    private static final int INT_ARL_ASI_DIA_AJU_COD_EMP=0;
    private static final int INT_ARL_ASI_DIA_AJU_COD_CFG=1;
    private static final int INT_ARL_ASI_DIA_AJU_NOM_PRG=2;
    private static final int INT_ARL_ASI_DIA_AJU_COD_LOC=3;
    private static final int INT_ARL_ASI_DIA_AJU_COD_TIP_DOC=4;
    private static final int INT_ARL_ASI_DIA_AJU_TIP_MOV=5;
    private static final int INT_ARL_ASI_DIA_AJU_OBS=6;
    private static final int INT_ARL_ASI_DIA_AJU_COD_MNU=7;
    private static final int INT_ARL_ASI_DIA_AJU_COD_DEB=8;
    private static final int INT_ARL_ASI_DIA_AJU_NUM_DEB=9;
    private static final int INT_ARL_ASI_DIA_AJU_NOM_DEB=10;
    private static final int INT_ARL_ASI_DIA_AJU_COD_HAB=11;
    private static final int INT_ARL_ASI_DIA_AJU_NUM_HAB=12;
    private static final int INT_ARL_ASI_DIA_AJU_NOM_HAB=13;
    private static final int INT_ARL_ASI_DIA_AJU_EST_REG=14;
    
    private int INT_COD_BOD_INM_GRP=15;
    
    //Constantes: Botones
    public static final int INT_BUT_DEN=0;                      /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;                      /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/
    public static final int INT_BUT_CER=2;                      /**Un valor para getSelectedButton: Indica "Botón Cerrar".*/        
    
    
    
    //Variables generales.
    private Connection conRef;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private java.util.Date datFecAux;                                           //Auxiliar: Para almacenar fechas.
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblTxt;                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtCan, objTblCelEdiTxtCos;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafMouMotAda objMouMotAda;                                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PeopuMená en JTable.
    private ZafVenCon vcoTipDoc, vcoIngImp, vcoItm;                                                //Ventana de consulta "Tipo de documento".
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafRptSis objRptSis;
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItmAlt, objTblCelEdiTxtVcoItmLet;
    private ZafTblCelRenCbo objTblCelRenCmbBoxMot;
    private ZafTblCelEdiCbo objTblCelEdiCmbBoxMot;
    private ZafTblCelRenChk objTblCelRenChk;                                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                                    //Editor: JCheckBox en celda.
    private ZafAsiDia objAsiDia;
    private ZafImp objZafImp;
    private UltDocPrint objUltDocPrint2;
    private ZafStkInv objStkInv;  
    private ZafSegMovInv objSegMovInv;
    private ZafPerUsr objPerUsr;
    private Object objCodSegInsAnt;    
    private Librerias.ZafImp.ZafAjuInv_01 objAjuInv_01;
    
    private Vector vecDat,  vecCab,  vecReg;
    private Vector vecAux;
    
    private boolean blnCon;                                                     //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                                                  //Determina si hay cambios en el formulario.

    private boolean isAjuPro;
    private boolean isModDoc;
    private boolean isAjuModChg;    
    
    private char chrTipOpe;
    
    private java.awt.Frame cpmDlg;
    
    private int intInsVisBue;//0: Indica que aún falta un visto bueno por conceder;   1: Indica que todos los vistos buenos están concedidos

    private int intCodEmpAju;
    private int intCodLocAju;
    private int intCodTipDocAju;
    private int intCodDocAju;
    
    //Clave de la Orden de Conteo asociada al Ingreso por Importación/Compra Local
    private int intCodEmpOrdCon;
    private int intCodLocOrdCon;
    private int intCodTipDocOrdCon;
    private int intCodDocOrdCon;
    
    private int intCodDocOrdDis;
    private int intNumDocOrdDis;
    private int intInsOrdDis;//Permite conocer si se debe ingresar o no la Orden de Distribución. 0: No genera Orden Distribución, 1: Si genera Orden Distribución
    private int intCodVisBueUsr;

    private int intButSelDlg;                                   //Botón seleccionado en el JDialog.

    private int intSecGrp, intSecEmp;
    private int intSig=1;
    private int intCodEmpIngImp=-1;
    private int intCodLocIngImp=-1;
    private int intCodTipDocIngImp=-1;
    private int intCodDocIngImp=-1;
    
    private String strSQL, strAux;
    private String strSQLInsItm;
    private String strSQLInsTodItmAgr;
    private String strDocActivo, strEstIngImpDocAju, strEstAutDenVisBue, strEstIngImpDocIngImp;    
    private String strNumDocIngImp, strNumPedIngImp;
    private String strDesCorTipDoc, strDesLarTipDoc;                            //Contenido del campo al obtener el foco.
    
    public String strVer=" v0.1.21";
    
    /** Crea una nueva instancia de la clase ZafAjuInv. */
    public ZafAjuInv(ZafParSis obj){
        try{
            System.out.println("ZafAjuInv - frame");
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objPerUsr=new ZafPerUsr(objParSis);
            strSQLInsItm="";
            strSQLInsTodItmAgr="";
            intInsOrdDis=0;
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            arlDatAju=new ArrayList();
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            arlDatStkInvItm=new ArrayList();
            arlDatDisInvItm=new ArrayList();
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            intButSelDlg=INT_BUT_DEN;
            isAjuModChg=false;
            arlDatDiaAju=new ArrayList();
            
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }


    /** Crea una nueva instancia de la clase ZafAjuInv. */
    /**
     * Función que permite modificación
     * @param parent
     * @param obj
     * @param intCodMnu
     */
    public ZafAjuInv(java.awt.Frame parent, ZafParSis obj, int intCodMnu, char tipoOperacion){
        super(parent, true);
        cpmDlg=parent;
        try{
            System.out.println("ZafAjuInv - modificacion");
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objPerUsr=new ZafPerUsr(objParSis);
            strSQLInsItm="";
            strSQLInsTodItmAgr="";
            intInsOrdDis=0;
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            arlDatAju=new ArrayList();
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            arlDatStkInvItm=new ArrayList();
            arlDatDisInvItm=new ArrayList();
            objParSis.setCodigoMenu(intCodMnu);
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            conRef=null;//se le asigna la conexion pero a traves de parametros enviados por metodo "setDatAju" al dar click en boton de consulta del Ajuste
            intButSelDlg=INT_BUT_DEN;
            chrTipOpe=tipoOperacion;
            isAjuModChg=false;
            arlDatDiaAju=new ArrayList();

        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * Función que permite inserción
     * @param obj
     * @param conexion   Si se desea trabajar con commit del Ajuste de Inventario desde afuera se deberá enviar la conexión, caso contrario enviar null
     * @param codEmpDocOri Código de empresa del Ingreso por Importación
     * @param codLocDocOri Código de local del Ingreso por Importación
     * @param codTipDocOri Código de Tipo de Documento del Ingreso por Importación
     * @param codDocOri    Código de documento del Ingreso por Importación
     * @param codTipDocAju Código de Tipo de Documento del Ajuste de Importación
     */
    public ZafAjuInv(ZafParSis obj, Connection conexion, int codEmpDocOri, int codLocDocOri, int codTipDocOri, int codDocOri, int codTipDocAju, int intCodMnu, char tipoOperacion){
        try{
            System.out.println("ZafAjuInv - insercion");
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objPerUsr=new ZafPerUsr(objParSis);
            objParSis.setCodigoMenu(intCodMnu);
            strSQLInsItm="";
            strSQLInsTodItmAgr="";
            intCodEmpIngImp=codEmpDocOri;
            intCodLocIngImp=codLocDocOri;
            intCodTipDocIngImp=codTipDocOri;
            intCodDocIngImp=codDocOri;
            intCodTipDocAju=codTipDocAju;
                    
                    
            conRef=conexion;
            intInsOrdDis=0;
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            chrTipOpe=tipoOperacion;
            
            isAjuModChg=false;
            arlDatDiaAju=new ArrayList();

            if( (intCodEmpIngImp!=-1) || (intCodLocIngImp!=-1) || (intCodTipDocIngImp!=-1) || (intCodDocIngImp!=-1)  ){
                if(conRef!=null){
                    if(isIngresoImportacion()){
                        initComponents();
                        if (!configurarFrm())
                            exitForm();
                        agregarDocLis();
                        clickInsertar();//limpia el formulario
                        txtCodTipDoc.setText(""+codTipDocAju);
                        objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                        arlDatAju=new ArrayList();
                        objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
                        objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
                        arlDatStkInvItm=new ArrayList();
                        arlDatDisInvItm=new ArrayList();

                        if(txtCodTipDoc.getText().length()>0)
                            mostrarVenConTipDoc(3);
                        txtCodIngImp.setText(""+codDocOri);
                        txtCodImp.setText(""+intCodEmpIngImp);
                        if(txtCodIngImp.getText().length()>0)
                            mostrarVenConIngImp(5);
                        beforeInsertar();//Validaciones
                        
                        if(isValAjuIngImp()){
                            if(insertar()){
                            }
                        }
                        else
                            System.out.println("*-GG");
                    }
                    else
                        mostrarMsgInf("<HTML>El documento no se ha encontrado.<BR>Verifique y vuelva a intentarlo.");
                }
                else
                    mostrarMsgInf("<HTML>Conexión nula.<BR>Verifique y vuelva a intentarlo.");

            }
            else{
                mostrarMsgInf("<HTML>La información no se pudo cargar.<BR>Verifique y vuelva a intentarlo.");
            }


        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Crea una nueva instancia de la clase ZafImp23. */
    /**
     * Función que permite consulta
     * @param obj
     * @param conexion
     * @param intCodEmpDoc
     * @param intCodLocDoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @param intCodMnu
     * @param chrModTooBar
     */
    public ZafAjuInv(java.awt.Frame parent, ZafParSis obj, Connection conexion, int intCodEmpDoc, int intCodLocDoc, int intCodTipDoc, int intCodDoc, int intCodMnu, char chrModTooBar){
        super(parent, true);
        cpmDlg=parent;
        try{
            System.out.println("ZafAjuInv - consulta");
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            chrTipOpe=chrModTooBar;
            
            intCodEmpAju=intCodEmpDoc;
            intCodLocAju=intCodLocDoc;
            intCodTipDocAju=intCodTipDoc;
            intCodDocAju=intCodDoc;
            intInsOrdDis=0;
            arlDatDiaAju=new ArrayList();

            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            strSQLInsItm="";
            strSQLInsTodItmAgr="";
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            arlDatAju=new ArrayList();
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            arlDatStkInvItm=new ArrayList();
            arlDatDisInvItm=new ArrayList();
            objParSis.setCodigoMenu(intCodMnu);
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            conRef=conexion;
            
            txtCodTipDoc.setText(""+intCodTipDoc);
            txtCodDoc.setText(""+intCodDoc);

            isAjuModChg=false;
            
            //Consulta
            if(chrModTooBar=='c'){
                consultar();
                isInaCamFrm();
            }//Modificación
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }


    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes = true;
        try {
            //arlDatItmModTbl=new ArrayList();
            objUti = new ZafUtil();
            arlDatFilEli=new ArrayList();
            arlDatFilEli.clear();
            //String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker:
            //DESDE
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(550, 6, 120, 20);

            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + "<> ZafAjuInv:"+strVer + " - ZafImp:"+objZafImp.strVer+"");
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());

            //txtCodTipDoc.setVisible(false);

            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarIngImp();
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblDat();

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
            //tabFrm.remove(panAsiDia);

            txtCodImp.setEnabled(false);
            txtCodImp.setBackground(Color.WHITE);
            txtNomImp.setEnabled(false);
            txtNomImp.setBackground(Color.WHITE);
            txtCodExp.setEnabled(false);
            txtCodExp.setBackground(Color.WHITE);
            txtNomExp2.setEnabled(false);
            txtNomExp2.setBackground(Color.WHITE);
            txtNomExp.setEnabled(false);
            txtNomExp.setBackground(Color.WHITE);
            txtCodIngImp.setVisible(false);
            txtCodExp.setVisible(false);

            arlDatVisBueUsrTipDoc=new ArrayList();
            arlDatVisBueUsrTipDocObl=new ArrayList();
            txtCosTotDoc.setVisible(false);

        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura el JTable "tblDat".
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
            vecCab = new Vector(27);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK, "");
            vecCab.add(INT_TBL_DAT_COD_EMP_ING_IMP, "Cód.Emp.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ING_IMP, "Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, "Cód.Tip.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ING_IMP, "Cód.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_REG_ING_IMP, "Cód.Reg.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP, "Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP, "Cód.Itm.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_LET, "Cód.Itm.Let.");
            vecCab.add(INT_TBL_DAT_BUT_ITM, "");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_TIP_MOV_DES, "Tip.Mov.");
            vecCab.add(INT_TBL_DAT_TIP_MOV_LET, "Tip.Mov.Let.");
            vecCab.add(INT_TBL_DAT_CAN_AJU, "Can.Ing.Egr.");
            vecCab.add(INT_TBL_DAT_COS_UNI_ING_IMP, "Cos.Uni.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COS_TOT, "Cos.Tot.");
            vecCab.add(INT_TBL_DAT_PES_UNI, "Pes.Uni.");
            vecCab.add(INT_TBL_DAT_PES_TOT, "Pes.Tot.");
            vecCab.add(INT_TBL_DAT_CAN_ING_IMP, "Can.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CAN_CON, "Can.Cnf.");
            vecCab.add(INT_TBL_DAT_CAN_TRS, "Can.Trs.");
            vecCab.add(INT_TBL_DAT_EST_EXI_ITM, "Est.Exi.Itm.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CAN_ING_EGR, "Can.Ing.Egr.");


            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_AJU, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_UNI_ING_IMP, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_UNI, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_ING_IMP, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CON, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_TRS, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_LET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_DES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_LET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ING_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST_EXI_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_EGR).setPreferredWidth(70);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TRS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_EXI_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_EGR, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_MOV_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_MOV_LET, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDat);

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_ITM_LET);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_TIP_MOV_DES);
            vecAux.add("" + INT_TBL_DAT_CAN_AJU);
            vecAux.add("" + INT_TBL_DAT_COS_UNI_ING_IMP);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_EXI_ITM)==null?"":objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_EXI_ITM).toString();

                    if (strEstExiItm.equals("N")){
                        objTblCelRenChk.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });


            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });


            //Configurar JTable: Renderizar celdas.
            objTblCelRenButItm=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenButItm);
            objTblCelRenButItm=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[8];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            intColVen[5]=6;
            intColVen[6]=7;
            intColVen[7]=8;
            int intColTbl[]=new int[8];
            intColTbl[0]=INT_TBL_DAT_COD_ITM_MAE;
            intColTbl[1]=INT_TBL_DAT_COD_ITM_GRP;
            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[3]=INT_TBL_DAT_COD_ITM_LET;
            intColTbl[4]=INT_TBL_DAT_NOM_ITM;
            intColTbl[5]=INT_TBL_DAT_PES_UNI;
            intColTbl[6]=INT_TBL_DAT_COD_ITM_EMP;
            intColTbl[7]=INT_TBL_DAT_DES_COR_UNI_MED;

            objTblCelEdiTxtVcoItmAlt=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoItmAlt);
            objTblCelEdiTxtVcoItmAlt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                int intCodItmMae=-1;
                boolean blnResBefCon=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    intCodItmMae=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString()));
                    if(strLin.equals("I")){//el item no existe
                        objTblCelEdiTxtVcoItmAlt.setCancelarEdicion(false);
                        //vcoItm.limpiar();
                    }                        
                    else
                        objTblCelEdiTxtVcoItmAlt.setCancelarEdicion(true);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if(strLin.equals("I")){//el item no existe
                        if(isMsgCreItm()){//crear nuevo item
                            objTblCelEdiTxtVcoItmAlt.setMosVenCon(false);
                            blnResBefCon=false;
                        }
                        else{
                            blnResBefCon=true;
                            objTblCelEdiTxtVcoItmAlt.setMosVenCon(true);
                        }

                        if(blnResBefCon){
                            vcoItm.setCampoBusqueda(2);
                            vcoItm.setCriterio1(11);
                            strSQLAdi="";
                            if(txtCodTipDoc.getText().equals(""+objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%I'";
                            else if(txtCodTipDoc.getText().equals("" + objZafImp.INT_COD_TIP_DOC_AJU_COM_LOC))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%S'";

                            strSQLAdi+="        AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                            strSQLAdi+="        GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr, a2.tx_desCor";
                            strSQLAdi+="        ORDER BY a1.tx_codAlt";
                            strSQLAdi+=" ) AS b1";
                            strSQLAdi+=" INNER JOIN(";
                            strSQLAdi+=" 	SELECT a1.co_itm AS co_itmEmp, a2.co_itmMae";
                            strSQLAdi+=" 	FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                            strSQLAdi+=" 	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                            strSQLAdi+=" 		WHERE a1.co_emp=" + intCodEmpAju + "";
                            strSQLAdi+=" 	) AS b2";
                            strSQLAdi+=" 	ON b1.co_itmMae=b2.co_itmMae";
                            strSQLAdi+=" ORDER BY a1.tx_codAlt";
                            vcoItm.setCondicionesSQL(strSQLAdi);
                        }
                    }
                    else{
                        objTblCelEdiTxtVcoItmAlt.setCancelarEdicion(true);
                    }
                }
                
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strLin.equals("I")){//el item no existe
                        if(!blnResBefCon)
                            afterAceCreItm(intFil);
                    } 
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularPesTot();
                    //objTblMod.setValueAt("I", intFil, INT_TBL_DAT_LIN);
                }
            });


            ////INICIO
            objTblCelEdiTxtVcoItmLet=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_LET).setCellEditor(objTblCelEdiTxtVcoItmLet);
            objTblCelEdiTxtVcoItmLet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                int intCodItmMae=-1;
                boolean blnResBefCon=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    intCodItmMae=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString()));

                    if(strLin.equals("I"))//el item no existe
                        objTblCelEdiTxtVcoItmLet.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtVcoItmLet.setCancelarEdicion(true);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //////////

                    if(strLin.equals("I")){//el item no existe
                        if(isMsgCreItm()){//crear nuevo item
                            objTblCelEdiTxtVcoItmLet.setMosVenCon(false);
                            blnResBefCon=false;
                        }
                        else{
                            blnResBefCon=true;
                            objTblCelEdiTxtVcoItmLet.setMosVenCon(true);
                        }

                        if(blnResBefCon){
                            vcoItm.setCampoBusqueda(3);
                            vcoItm.setCriterio1(11);
                            strSQLAdi="";
                            if(txtCodTipDoc.getText().equals(""+objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%I'";
                            else if(txtCodTipDoc.getText().equals("" + objZafImp.INT_COD_TIP_DOC_AJU_COM_LOC))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%S'";

                            strSQLAdi+="        AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                            strSQLAdi+="        GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr, a2.tx_desCor";
                            strSQLAdi+="        ORDER BY a1.tx_codAlt";
                            strSQLAdi+=" ) AS b1";
                            strSQLAdi+=" INNER JOIN(";
                            strSQLAdi+=" 	SELECT a1.co_itm AS co_itmEmp, a2.co_itmMae";
                            strSQLAdi+=" 	FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                            strSQLAdi+=" 	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                            strSQLAdi+=" 		WHERE a1.co_emp=" + intCodEmpAju + "";
                            strSQLAdi+=" 	) AS b2";
                            strSQLAdi+=" 	ON b1.co_itmMae=b2.co_itmMae";
                            strSQLAdi+=" ORDER BY a1.tx_codAlt";
                            vcoItm.setCondicionesSQL(strSQLAdi);
                        }

                    }
                    else{
                        objTblCelEdiTxtVcoItmLet.setCancelarEdicion(true);
                    }
                }
                
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strLin.equals("I")){//el item no existe
                        if(!blnResBefCon)
                            afterAceCreItm(intFil);
                    } 
                }                
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularPesTot();
                    //objTblMod.setValueAt("I", intFil, INT_TBL_DAT_LIN);
                }
            });

            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                int intCodItmMae=-1;
                boolean blnResBefCon=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    intCodItmMae=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString()));

                    if(strLin.equals("I"))//el item no existe
                        objTblCelEdiButVcoItm.setCancelarEdicion(false);
                    else
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strLin.equals("I")){//el item no existe
                        if(isMsgCreItm()){//crear nuevo item
                            objTblCelEdiButVcoItm.setMosVenCon(false);
                            blnResBefCon=false;
                        }
                        else{
                            blnResBefCon=true;
                            objTblCelEdiButVcoItm.setMosVenCon(true);
                        }
                        
                        if(blnResBefCon){
                            vcoItm.setCampoBusqueda(2);
                            vcoItm.setCriterio1(11);
                            strSQLAdi="";
                            if(txtCodTipDoc.getText().equals(""+objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%I'";
                            else if(txtCodTipDoc.getText().equals("" + objZafImp.INT_COD_TIP_DOC_AJU_COM_LOC))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%S'";

                            strSQLAdi+="        AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                            strSQLAdi+="        GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr, a2.tx_desCor";
                            strSQLAdi+="        ORDER BY a1.tx_codAlt";
                            strSQLAdi+=" ) AS b1";
                            strSQLAdi+=" INNER JOIN(";
                            strSQLAdi+=" 	SELECT a1.co_itm AS co_itmEmp, a2.co_itmMae";
                            strSQLAdi+=" 	FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                            strSQLAdi+=" 	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                            strSQLAdi+=" 		WHERE a1.co_emp=" + intCodEmpAju + "";
                            strSQLAdi+=" 	) AS b2";
                            strSQLAdi+=" 	ON b1.co_itmMae=b2.co_itmMae";
                            strSQLAdi+=" ORDER BY b1.tx_codAlt";
//                            System.out.println("strSQLAdi: " + strSQLAdi);
                            vcoItm.setCondicionesSQL(strSQLAdi);
                        }
                    }
                    else{
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                    }
                }
                
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strLin.equals("I")){//el item no existe
                        if(!blnResBefCon)
                            afterAceCreItm(intFil);
                    } 
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //objTblMod.setValueAt("I", intFil, INT_TBL_DAT_LIN);
                    calcularPesTot();
//                    if(strLin.equals("I")){//el item no existe
//                        if(!blnResBefCon)
//                            afterAceCreItm(intFil);
//                    }                    
                }

            });
            intColVen=null;
            intColTbl=null;

            //ComboBox
            objTblCelRenCmbBoxMot=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_DES).setCellRenderer(objTblCelRenCmbBoxMot);
            objTblCelRenCmbBoxMot=null;

            objTblCelEdiCmbBoxMot=new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_DES).setCellEditor(objTblCelEdiCmbBoxMot);
            objTblCelEdiCmbBoxMot.addItem("--Seleccione una opción--");
            objTblCelEdiCmbBoxMot.addItem("E: Empresa");
            objTblCelEdiCmbBoxMot.addItem("T: Reclamos a Terceros");
            objTblCelEdiCmbBoxMot.addItem("P: Reclamos a Proveedores");
            objTblCelEdiCmbBoxMot.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intComBoxSel=-1;
                int intFilSel=-1;
                String strTipMovLetAnt="";
                String strTipMovLetAct="";
                BigDecimal bgdCosTotLinSel=BigDecimal.ZERO;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("beforeEdit");
                    intFilSel=tblDat.getSelectedRow();
                    strTipMovLetAnt=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TIP_MOV_LET).toString();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit");
                    intComBoxSel=objTblCelEdiCmbBoxMot.getSelectedIndex();
//                    System.out.println("intComBoxSel: " + intComBoxSel);
                    if(intComBoxSel==1)
                        objTblMod.setValueAt("E", intFilSel, INT_TBL_DAT_TIP_MOV_LET);
                    else if(intComBoxSel==2)
                        objTblMod.setValueAt("T", intFilSel, INT_TBL_DAT_TIP_MOV_LET);
                    else if(intComBoxSel==3)
                        objTblMod.setValueAt("P", intFilSel, INT_TBL_DAT_TIP_MOV_LET);
                    
                    strTipMovLetAct=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TIP_MOV_LET).toString();
                    if( (strTipMovLetAct.equals("T")) || (strTipMovLetAct.equals("P")) ){
                        bgdCosTotLinSel=objUti.redondearBigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_TOT).toString()), 4);
                        if(bgdCosTotLinSel.compareTo(BigDecimal.ZERO)>0){
                            mostrarMsgInf("<HTML>El item seleccionado es de tipo ingreso.<BR>Este tipo de movimiento no se puede seleccionar para valores positivos.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            objTblMod.setValueAt("E: Empresa", intFilSel, INT_TBL_DAT_TIP_MOV_DES);
                            objTblMod.setValueAt("E", intFilSel, INT_TBL_DAT_TIP_MOV_LET);
                        }
                    }
                    generaDiarioAjuste();
                }
            });

            objTblCelRenLblTxt=new ZafTblCelRenLbl();
            objTblCelRenLblTxt.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblTxt.setTipoFormato(objTblCelRenLblTxt.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_LET).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_EST_EXI_ITM).setCellRenderer(objTblCelRenLblTxt);
            objTblCelRenLblTxt.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_EXI_ITM)==null?"":objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_EXI_ITM).toString();

                    if (strEstExiItm.equals("N")){
                        objTblCelRenLblTxt.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblTxt.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ING_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_EXI_ITM)==null?"":objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_EXI_ITM).toString();

                    if (strEstExiItm.equals("N")){
                        objTblCelRenLbl.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });

            objTblCelEdiTxtCan=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setCellEditor(objTblCelEdiTxtCan);
            objTblCelEdiTxtCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bgdValCanIngImp=new BigDecimal("0");
                BigDecimal bgdValCanUsr=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiTxtCan.setCancelarEdicion(true);
                    intFil=tblDat.getSelectedRow();
                    
                    if( (objPerUsr.isOpcionEnabled(3980)) || (objPerUsr.isOpcionEnabled(3980)) ){//insercion o modificacion
                        objTblCelEdiTxtCan.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxtCan.setCancelarEdicion(true);
//                    
//                    bgdValCanIngImp=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING_IMP)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING_IMP).toString()));
//                    if(bgdValCanIngImp.compareTo(BigDecimal.ZERO)<=0)
//                        objTblCelEdiTxtCan.setCancelarEdicion(false);
//                    else
//                        objTblCelEdiTxtCan.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    actualizarGlo();
                }
            });

            objTblCelEdiTxtCos=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ING_IMP).setCellEditor(objTblCelEdiTxtCos);
            objTblCelEdiTxtCos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intCodItmGrp=-1;
                int intFilSel=-1;
                String strLin="";
                BigDecimal bgdCosUni=BigDecimal.ZERO;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN).toString();
                    bgdCosUni=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI_ING_IMP).toString()));
                    if(bgdCosUni.compareTo(BigDecimal.ZERO)==0)
                        objTblCelEdiTxtCos.setCancelarEdicion(false);
                    else{
                        if(strLin.equals("I"))
                            objTblCelEdiTxtCos.setCancelarEdicion(false);
                        else
                            objTblCelEdiTxtCos.setCancelarEdicion(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularCosTot(intFilSel);
                    setValDoc();
                    calcularCosTotGenAsiDia();
                    generaDiarioAjuste();
                    actualizarGlo();
//                    setTipMov(intFilSel);
                }
            });

            if(objParSis.getCodigoUsuario()==1){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            }
            else{
                if((objPerUsr.isOpcionEnabled(3980))){//Modificar
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                else if((objPerUsr.isOpcionEnabled(3980))){//Insertar
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                }
                else if((objPerUsr.isOpcionEnabled(3980))){//Eliminar
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                }
                else{
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            }
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);
                        
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 
                int intArlColSel=-1;                 
                int intCodEmpDatPedPre, intCodLocDatPedPre, intCodTipDocDatPedPre, intCodDocDatPedPre;
                int intCodEmpDatDetPedPre, intCodLocDatDetPedPre, intCodTipDocDatDetPedPre, intCodDocDatDetPedPre, intCodItmDatDetPedPre;
                int intCodItmFilSel;
                boolean blnExiCodItm;
                int intFilEli[];
                 
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilEli=tblDat.getSelectedRows();
                    if (objTblPopMnu.isClickEliminarFila()){
                        System.out.println("isClickEliminarFila");
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickEliminarFila()){
                        setFilEliUsr(intFilEli);
                        setValDoc();
                        generaDiarioAjuste();
                        actualizarGlo();
                        //objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                    }
                }
            });

            txtValDoc.setEnabled(false);
            txtPesDoc.setEnabled(false);
            txtCodDoc.setEnabled(false);
            txtCodBodEmp.setEnabled(false);
            txtNomBodEmp.setEnabled(false);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
            butCarItm.setVisible(false);

        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_EMP_ING_IMP:
                    strMsg = "Código de empresa ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_LOC_ING_IMP:
                    strMsg = "Código de local ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ING_IMP:
                    strMsg = "Código de tipo de documento ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_DOC_ING_IMP:
                    strMsg = "Código de documento ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_REG_ING_IMP:
                    strMsg = "Código de registro ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_ITM_GRP:
                    strMsg = "Código de item de grupo";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg = "Código de item de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código maestro de item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno de item";
                    break;
                case INT_TBL_DAT_COD_ITM_LET:
                    strMsg = "Código de item en letras";
                    break;
                case INT_TBL_DAT_BUT_ITM:
                    strMsg = "Listado de Items";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre de Item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg = "Unidad de Medida";
                    break;
                case INT_TBL_DAT_TIP_MOV_DES:
                    strMsg = "Tipo de movimiento";
                    break;
                case INT_TBL_DAT_TIP_MOV_LET:
                    strMsg = "Tipo de movimiento letra";
                    break;
                case INT_TBL_DAT_CAN_AJU:
                    strMsg = "Cantidad de ingreso/egreso de ajuste";
                    break;
                case INT_TBL_DAT_COS_UNI_ING_IMP:
                    strMsg = "Costo unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg = "Costo total";
                    break;
                case INT_TBL_DAT_PES_UNI:
                    strMsg = "Peso unitario";
                    break;
                case INT_TBL_DAT_PES_TOT:
                    strMsg = "Pes total";
                    break;
                case INT_TBL_DAT_CAN_ING_IMP:
                    strMsg = "Cantidad del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_CAN_CON:
                    strMsg = "Cantidad contada";
                    break;
                case INT_TBL_DAT_CAN_TRS:
                    strMsg = "Cantidad transferida";
                    break;
                case INT_TBL_DAT_EST_EXI_ITM:
                    strMsg = "Item existe en el Ingreso por Importación";
                    break;
                case INT_TBL_DAT_CAN_ING_EGR:
                    strMsg = "Cantidad Ingreso/Egreso del Ingreso por Importación";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener {

        public void columnAdded(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn() != null) {
                intColSel = tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel >= 0) {
                    intAncCol = tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                }
            }
        }

        public void columnMoved(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
        }
    }

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }

    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a3.co_itmMae");
            arlCam.add("a3.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a1.nd_pesitmkgr");
            arlCam.add("co_itmEmp");
            arlCam.add("tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Letra");
            arlAli.add("Nombre");
            arlAli.add("Peso(kg)");
            arlAli.add("Item Empresa");
            arlAli.add("Unidad medida");

            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("40");
            arlAncCol.add("250");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.nd_pesitmkgr, b2.co_itmEmp, b1.tx_desCor";
            strSQL+=" FROM(";
            strSQL+=" 	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr, a2.tx_desCor";
            strSQL+=" 	FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+=" 	LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
//            System.out.println("configurarVenConItm: " + strSQL);

            //Ocultar columnas.            
            int intColOcu[]=new int[2];
            intColOcu[0]=1;
            intColOcu[1]=2;

            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setCampoBusqueda(3);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.co_ctaDeb");
            arlCam.add("a1.co_ctaHab");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cádigo");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Cta.Deb.");
            arlAli.add("Cta.Hab.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL = "";
                strSQL += " SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.co_ctaDeb, a1.co_ctaHab";
                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
            }
            else{
                strSQL = "";
                strSQL += " SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.co_ctaDeb, a1.co_ctaHab";
                strSQL += " FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL += " ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL += " WHERE ";
                strSQL += " a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
//                System.out.println("configurarVenConTipDoc: " + strSQL);            

            //Ocultar columnas.
            int intColOcu[] = new int[3];
            intColOcu[0] = 5;
            intColOcu[1] = 6;
            intColOcu[2] = 7;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
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
    private boolean mostrarVenConTipDoc(int intTipBus) {
        boolean blnRes = true;
        try {
//            System.out.println("mostrarVenConTipDoc: " + txtCodTipDoc.getText());
            if(txtCodTipDoc.getText().length()>0){
                strAux="";
                strAux+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                strAux+= " AND a1.st_reg NOT IN('I','E')";
                strAux+= " ORDER BY a1.tx_desCor";
//                System.out.println("strAux: " + strAux);
                vcoTipDoc.setCondicionesSQL(strAux);
            }

            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(0);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".

                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        }
                        else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }

                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    }
                    else{
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        }
                        else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Código".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    }
                    else{
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        }
//                        else {
                            //txtDesLarTipDoc.setText(strDesLarTipDoc);
//                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos".
     */
    private boolean configurarIngImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("tx_nomEmp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            arlCam.add("a1.co_exp");
            arlCam.add("tx_nomExp");
            arlCam.add("tx_nomExp2");
            arlCam.add("co_bodEmp");
            arlCam.add("tx_nomBodEmp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Imp.");
            arlAli.add("Nom.Imp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            arlAli.add("Cód.Exp.");
            arlAli.add("Nom.Exp.");
            arlAli.add("Nom.Exp2.");
            arlAli.add("Cod.Bod.Emp.");
            arlAli.add("Nom.Bod.Emp.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("40");
            arlAncCol.add("50");
            arlAncCol.add("40");
            arlAncCol.add("40");
            arlAncCol.add("40");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("40");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("40");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a2.tx_nom AS tx_nomEmp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";

            if(chrTipOpe=='n'){
                if(intCodTipDocIngImp==objZafImp.INT_COD_TIP_DOC_ING_IMP)
                    strSQL+=" 	, a3.co_exp, a3.tx_nom AS tx_nomExp, a3.tx_nom2 AS tx_nomExp2";
                else
                    strSQL+=" 	, a3.co_cli AS co_exp, a3.tx_ide AS tx_nomExp, a3.tx_nom AS tx_nomExp2";  
            }
            else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                if(intCodTipDocAju==objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP)
                    strSQL+=" 	, a3.co_exp, a3.tx_nom AS tx_nomExp, a3.tx_nom2 AS tx_nomExp2";
                else
                    strSQL+=" 	, a3.co_cli AS co_exp, a3.tx_ide AS tx_nomExp, a3.tx_nom AS tx_nomExp2";  
            }
          
            strSQL+=" , a4.co_bod AS co_bodEmp, a4.tx_nomBodOrgDes AS tx_nomBodEmp";
            strSQL+=" FROM (  (tbm_cabMovInv AS a1 INNER JOIN tbm_emp AS a2 ON a1.co_emp=a2.co_emp)";
            strSQL+="       INNER JOIN tbm_detMovInv AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc)";

            if(chrTipOpe=='n'){
                if(intCodTipDocIngImp==objZafImp.INT_COD_TIP_DOC_ING_IMP)
                    strSQL+="  INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp";
                else
                    strSQL+="  INNER JOIN tbm_cli AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
            }
            else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                if(intCodTipDocAju==objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP)
                    strSQL+="  INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp";
                else
                    strSQL+="  INNER JOIN tbm_cli AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
            }
            
            strSQL+=" WHERE a1.co_tipDoc in(14,245) and a1.co_mnu IN("+objZafImp.INT_COD_MNU_PRG_ING_IMP+", "+objZafImp.INT_COD_MNU_PRG_COM_LOC+")";
            strSQL+=" AND a1.st_reg='A' AND a1.st_ingImp IN('A', 'B')";
            strSQL+=" AND a1.co_emp=" + intCodEmpIngImp + "";
            strSQL+=" AND a1.co_loc=" + intCodLocIngImp + "";
            strSQL+=" AND a1.co_tipDoc=" + intCodTipDocIngImp + "";
            strSQL+=" AND a1.co_doc=" + intCodDocIngImp + "";
            
            strSQL+=" GROUP BY a1.co_emp, a2.tx_nom, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";

            if(chrTipOpe=='n'){
                if(intCodTipDocIngImp==objZafImp.INT_COD_TIP_DOC_ING_IMP)
                    strSQL+=" 	, a3.co_exp, a3.tx_nom, a3.tx_nom2";
                else
                    strSQL+=" 	, a3.co_cli, a3.tx_ide, a3.tx_nom";
            }
            else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                if(intCodTipDocAju==objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP)
                    strSQL+=" 	, a3.co_exp, a3.tx_nom, a3.tx_nom2";
                else
                    strSQL+=" 	, a3.co_cli, a3.tx_ide, a3.tx_nom";
            }

            strSQL+=" , a4.co_bod, a4.tx_nomBodOrgDes, a1.fe_doc";
            strSQL+=" ORDER BY a1.fe_doc DESC";
            //System.out.println("configurarIngImp: " + strSQL);

            //Ocultar columnas.
            int intColOcu[]=new int[7];
            intColOcu[0]=1;
            intColOcu[1]=3;
            intColOcu[2]=4;
            intColOcu[3]=5;
            intColOcu[4]=8;
            intColOcu[5]=11;
            intColOcu[6]=12;

            vcoIngImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Ingresos por Importación", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoIngImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoIngImp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite cargar información del Ingreso por Importación/Compras Locales
     * @param intTipBus
     * @return
     */
    private boolean mostrarVenConIngImp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoIngImp.setCampoBusqueda(5);
                    vcoIngImp.setVisible(true);
                    if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(5));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(6));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));

                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    break;
                case 1: //Búsqueda directa por "Número Documento".
                    if (vcoIngImp.buscar("a1.ne_numDoc", txtNumDocIngImp.getText()))
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(5));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(6));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));

                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(4);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodIngImp.setText(vcoIngImp.getValueAt(5));
                            txtNumDocIngImp.setText(vcoIngImp.getValueAt(6));
                            txtNumPedIngImp.setText(vcoIngImp.getValueAt(7));
                            txtCodImp.setText(vcoIngImp.getValueAt(1));
                            txtNomImp.setText(vcoIngImp.getValueAt(2));
                            txtCodExp.setText(vcoIngImp.getValueAt(8));
                            txtNomExp2.setText(vcoIngImp.getValueAt(9));
                            txtNomExp.setText(vcoIngImp.getValueAt(10));

                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                            txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                        }
                        else
                        {
                            txtNumDocIngImp.setText(strNumDocIngImp);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoIngImp.buscar("a1.tx_numDoc2", txtNumPedIngImp.getText()))
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(5));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(6));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));

                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(5);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodIngImp.setText(vcoIngImp.getValueAt(5));
                            txtNumDocIngImp.setText(vcoIngImp.getValueAt(6));
                            txtNumPedIngImp.setText(vcoIngImp.getValueAt(7));
                            txtCodImp.setText(vcoIngImp.getValueAt(1));
                            txtNomImp.setText(vcoIngImp.getValueAt(2));
                            txtCodExp.setText(vcoIngImp.getValueAt(8));
                            txtNomExp2.setText(vcoIngImp.getValueAt(9));
                            txtNomExp.setText(vcoIngImp.getValueAt(10));

                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                            txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                        }
                        else
                        {
                            txtNumPedIngImp.setText(strNumPedIngImp);
                        }
                    }
                    break;
                case 5: //Búsqueda directa por "Descripción larga".
                    if (vcoIngImp.buscar("a1.co_doc", txtCodIngImp.getText()))
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(5));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(6));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));

                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(4);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodIngImp.setText(vcoIngImp.getValueAt(5));
                            txtNumDocIngImp.setText(vcoIngImp.getValueAt(6));
                            txtNumPedIngImp.setText(vcoIngImp.getValueAt(7));
                            txtCodImp.setText(vcoIngImp.getValueAt(1));
                            txtNomImp.setText(vcoIngImp.getValueAt(2));
                            txtCodExp.setText(vcoIngImp.getValueAt(8));
                            txtNomExp2.setText(vcoIngImp.getValueAt(9));
                            txtNomExp.setText(vcoIngImp.getValueAt(10));

                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                            txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                        }
                        else
                        {
                            txtNumPedIngImp.setText(strNumPedIngImp);
                        }
                    }
                    break;
            }
            System.out.println("codigo de bodega: " + txtCodBodEmp.getText());
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta funcián muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try{
            if (conRef!=null){
                stm=conRef.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpIngImp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLocIngImp + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDocAju + "";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpIngImp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLocIngImp + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDocAju + "";
                }
                System.out.println("mostrarTipDocPre: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
            }
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblIngImp = new javax.swing.JLabel();
        txtCodIngImp = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtNumPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lbImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp2 = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        lblBod = new javax.swing.JLabel();
        txtCodBodEmp = new javax.swing.JTextField();
        txtNomBodEmp = new javax.swing.JTextField();
        lblPesDoc = new javax.swing.JLabel();
        txtPesDoc = new javax.swing.JTextField();
        butCarItm = new javax.swing.JButton();
        txtCosTotDoc = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBarBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butDen = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);

        panCon.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 108));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFil.setPreferredSize(new java.awt.Dimension(0, 118));
        panCabFil.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 100, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(108, 6, 32, 20);

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
        panCabFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 6, 56, 20);

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
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(196, 6, 210, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(406, 6, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabFil.add(lblFecDoc);
        lblFecDoc.setBounds(432, 6, 110, 20);

        lblIngImp.setText("Ingreso por Importación:");
        panCabFil.add(lblIngImp);
        lblIngImp.setBounds(4, 28, 124, 14);
        panCabFil.add(txtCodIngImp);
        txtCodIngImp.setBounds(130, 26, 10, 20);

        txtNumDocIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusLost(evt);
            }
        });
        txtNumDocIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocIngImpActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumDocIngImp);
        txtNumDocIngImp.setBounds(140, 26, 56, 20);

        txtNumPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumPedIngImpFocusLost(evt);
            }
        });
        txtNumPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumPedIngImpActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumPedIngImp);
        txtNumPedIngImp.setBounds(196, 26, 210, 20);

        butPedImp.setText("...");
        butPedImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                butPedImpFocusLost(evt);
            }
        });
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panCabFil.add(butPedImp);
        butPedImp.setBounds(406, 26, 20, 20);

        lblNumDoc.setText("Número documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCabFil.add(lblNumDoc);
        lblNumDoc.setBounds(432, 27, 100, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        panCabFil.add(txtNumDoc);
        txtNumDoc.setBounds(550, 26, 120, 20);

        lbImp.setText("Importador:");
        lbImp.setToolTipText("Cuenta");
        panCabFil.add(lbImp);
        lbImp.setBounds(4, 46, 90, 20);
        panCabFil.add(txtCodImp);
        txtCodImp.setBounds(140, 46, 56, 20);
        panCabFil.add(txtNomImp);
        txtNomImp.setBounds(196, 46, 210, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabFil.add(lblCodDoc);
        lblCodDoc.setBounds(432, 46, 110, 20);
        panCabFil.add(txtCodDoc);
        txtCodDoc.setBounds(550, 46, 120, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Proveedor");
        panCabFil.add(lblExp);
        lblExp.setBounds(4, 66, 80, 20);
        panCabFil.add(txtCodExp);
        txtCodExp.setBounds(110, 66, 30, 20);
        panCabFil.add(txtNomExp2);
        txtNomExp2.setBounds(140, 66, 56, 20);
        panCabFil.add(txtNomExp);
        txtNomExp.setBounds(196, 66, 210, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Código del documento");
        panCabFil.add(lblValDoc);
        lblValDoc.setBounds(432, 66, 110, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabFil.add(txtValDoc);
        txtValDoc.setBounds(550, 66, 120, 20);

        lblBod.setText("Bodega Ingreso/Egreso:");
        lblBod.setToolTipText("Proveedor");
        panCabFil.add(lblBod);
        lblBod.setBounds(4, 86, 130, 20);
        panCabFil.add(txtCodBodEmp);
        txtCodBodEmp.setBounds(140, 86, 56, 20);
        panCabFil.add(txtNomBodEmp);
        txtNomBodEmp.setBounds(196, 86, 210, 20);

        lblPesDoc.setText("Peso del documento:");
        lblPesDoc.setToolTipText("Código del documento");
        panCabFil.add(lblPesDoc);
        lblPesDoc.setBounds(432, 86, 110, 20);

        txtPesDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabFil.add(txtPesDoc);
        txtPesDoc.setBounds(550, 86, 120, 20);

        butCarItm.setText("Cargar items");
        butCarItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarItmActionPerformed(evt);
            }
        });
        panCabFil.add(butCarItm);
        butCarItm.setBounds(410, 80, 93, 23);
        panCabFil.add(txtCosTotDoc);
        txtCosTotDoc.setBounds(410, 46, 20, 20);

        panGenCab.add(panCabFil, java.awt.BorderLayout.NORTH);

        panCon.add(panGenCab, java.awt.BorderLayout.NORTH);

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

        panCon.add(panGenDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panCon.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panCon);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBarBot.setPreferredSize(new java.awt.Dimension(0, 50));
        panBarBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setText("Autorizar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBarBot.add(butAce);

        butDen.setText("Denegar");
        butDen.setPreferredSize(new java.awt.Dimension(92, 25));
        butDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDenActionPerformed(evt);
            }
        });
        panBarBot.add(butDen);

        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBarBot.add(butCer);

        panFrm.add(panBarBot, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();

    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                //Cerrar la conexián si está abierta.
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    rstCab = null;
                    stmCab = null;
                }
                intButSelDlg=INT_BUT_CER;
                dispose();
            }
        } catch (java.sql.SQLException e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtNumDocIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusGained
        strNumDocIngImp=txtNumDocIngImp.getText();
        txtNumDocIngImp.selectAll();
    }//GEN-LAST:event_txtNumDocIngImpFocusGained

    private void txtNumDocIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusLost
        if (!txtNumDocIngImp.getText().equalsIgnoreCase(strNumDocIngImp))
        {
            if (txtNumDocIngImp.getText().equals(""))
            {
                txtCodIngImp.setText("");
                txtNumDocIngImp.setText("");
                txtNumPedIngImp.setText("");
                txtCodImp.setText("");
                txtNomImp.setText("");
                txtCodExp.setText("");
                txtNomExp2.setText("");
                txtNomExp.setText("");
            }
            else
            {
                mostrarVenConIngImp(1);
            }
        }
        else
            txtNumDocIngImp.setText(strNumDocIngImp);
    }//GEN-LAST:event_txtNumDocIngImpFocusLost

    private void txtNumDocIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocIngImpActionPerformed
        txtNumDocIngImp.transferFocus();
    }//GEN-LAST:event_txtNumDocIngImpActionPerformed

    private void txtNumPedIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedIngImpFocusGained
        strNumPedIngImp=txtNumPedIngImp.getText();
        txtNumPedIngImp.selectAll();
    }//GEN-LAST:event_txtNumPedIngImpFocusGained

    private void txtNumPedIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedIngImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNumPedIngImp.getText().equalsIgnoreCase(strNumPedIngImp))
        {
            if (txtNumPedIngImp.getText().equals(""))
            {
                txtCodIngImp.setText("");
                txtNumPedIngImp.setText("");
                txtNumDocIngImp.setText("");
                txtCodImp.setText("");
                txtNomImp.setText("");
                txtCodExp.setText("");
                txtNomExp2.setText("");
                txtNomExp.setText("");
            }
            else
            {
                mostrarVenConIngImp(2);
            }
        }
        else
            txtNumPedIngImp.setText(strNumPedIngImp);
    }//GEN-LAST:event_txtNumPedIngImpFocusLost

    private void txtNumPedIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumPedIngImpActionPerformed
        txtNumPedIngImp.transferFocus();
    }//GEN-LAST:event_txtNumPedIngImpActionPerformed

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        mostrarVenConIngImp(0);
    }//GEN-LAST:event_butPedImpActionPerformed

    private void butPedImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butPedImpFocusLost
        // TODO add your handling code here:
//        if(txtCodIngImp.getText().length()>0)//Se seleccionó un Ingreso por Importación
//            if(!isValAjuIngImp())
//                mostrarMsgInf("<HTML>No se pudo realizar la operación correctamente.<BR>Verifique y vuelva a intentarlo.</HTML>");
//

    }//GEN-LAST:event_butPedImpFocusLost

    private void butCarItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarItmActionPerformed
        if(txtCodIngImp.getText().length()>0)//Se seleccionó un Ingreso por Importación
            if(!isValAjuIngImp())
                mostrarMsgInf("<HTML>No se pudo realizar la operación correctamente.<BR>Verifique y vuelva a intentarlo.</HTML>");

    }//GEN-LAST:event_butCarItmActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        intButSelDlg=INT_BUT_ACE;
        isAjuModChg=true;
        
        if(beforeModificar()){//validar que todos los datos esten completos en el formulario
            if(modificar()){
                if(afterModificar()){
                    mostrarMsgInf("<HTML>La operación AUTORIZAR se realizó correctamente.</HTML>");
                    dispose();
                }
                else
                    mostrarMsgInf("<HTML>La operación AUTORIZAR no se pudo realizar.</HTML>");
            }
            else
                mostrarMsgInf("<HTML>La operación AUTORIZAR no se pudo realizar.</HTML>");
        }
        else
            mostrarMsgInf("<HTML>Existen datos incompletos.<BR>Verifique los datos ingresados y vuelva a intentarlo.</HTML>");
        arlDatFilEli.clear();
    }//GEN-LAST:event_butAceActionPerformed

    private void butDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDenActionPerformed
        intButSelDlg=INT_BUT_DEN;
        isAjuModChg=false;

        if(beforeModificar()){//validar que todos los daatos esten completos en el formulario
            if(modificar()){
                if(afterModificar()){
                    mostrarMsgInf("<HTML>La operación DENEGAR se realizó correctamente.</HTML>");
                    dispose();
                }
                else
                    mostrarMsgInf("<HTML>La operación DENEGAR no se pudo realizar.</HTML>");
            }
            else
                mostrarMsgInf("<HTML>La operación DENEGAR no se pudo realizar.</HTML>");
        }
        else
            mostrarMsgInf("<HTML>Existen datos incompletos.<BR>Verifique los datos ingresados y vuelva a intentarlo.</HTML>");
        arlDatFilEli.clear();
    }//GEN-LAST:event_butDenActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        intButSelDlg=INT_BUT_CER;
        cancelar();        
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        intButSelDlg=INT_BUT_CER;
        isAjuModChg=false;
        arlDatFilEli.clear();
        cancelar();
        dispose();
    }//GEN-LAST:event_butCerActionPerformed


    /** Cerrar la aplicacián. */
    private void exitForm() {
        intButSelDlg=INT_BUT_CER;
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCarItm;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butDen;
    private javax.swing.JButton butPedImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lbImp;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblIngImp;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPesDoc;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBarBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBodEmp;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodIngImp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCosTotDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBodEmp;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomExp2;
    private javax.swing.JTextField txtNomImp;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumDocIngImp;
    private javax.swing.JTextField txtNumPedIngImp;
    private javax.swing.JTextField txtPesDoc;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta funcián obtiene la descripcián larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripcián larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la funcián devuelve una cadena vacáa.
     */
    private String getEstReg(String estado){
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0)){
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'E':
                    estado="Eliminado";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }

        return estado;
    }   

    /**
     * Esta funcián permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodIngImp.setText("");
            txtNumDocIngImp.setText("");
            txtNumPedIngImp.setText("");
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp2.setText("");
            txtNomExp.setText("");
            txtCodBodEmp.setText("");
            txtNomBodEmp.setText("");
            txtPesDoc.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            objTblMod.removeAllRows();
            txtNumDoc.setEnabled(false);
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
            txtValDoc.setEnabled(false);
            butCarItm.setEnabled(true);
            txtValDoc.setText("");
            strDocActivo="";//Ajuste
            strEstAutDenVisBue="";//Ajuste
            strEstIngImpDocIngImp="";//Ingreso por Importacion
            strEstIngImpDocAju="";//Ajuste
            arlDatFilEli.clear();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }

        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }

        if (txtNumDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
            return false;
        }

        return true;
    }
    
    /**
     * Función que permite obtener la fecha del día de hoy
     * @return strFecHoy Fecha del día
     */ 
    private String getFecHoy(){
        boolean blnRes=true;
        String strFecHoy="";
        Statement stmFecHoy;
        ResultSet rstFecHoy;
        try{
            if (conRef!=null){
                stmFecHoy=conRef.createStatement();
                //Armar la sentencia SQL.
                strSQL = "SELECT CURRENT_DATE AS fe_dia";
                rstFecHoy=stmFecHoy.executeQuery(strSQL);
                if(rstFecHoy.next())
                    strFecHoy=objUti.formatearFecha(rstFecHoy.getDate("fe_dia"), "dd/MM/yyyy");
                stmFecHoy.close();
                stmFecHoy=null;
                rstFecHoy.close();
                rstFecHoy=null;
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
        return strFecHoy;
    }
    
    
    /**
     * Función que permite obtener información del Ingreso por Importacion/Compra Local
     * que cuentan con valores que deben ajustarse a valores negativos o valores positivos
     * @return
     */
    private boolean isValAjuIngImp(){
        boolean blnRes=true;
        Statement stmValAjuIngImp;
        ResultSet rstValAjuIngImp;
        BigDecimal bgdCanIngImp=BigDecimal.ZERO;
        BigDecimal bgdCanCon=BigDecimal.ZERO;
        BigDecimal bgdCanSolTrs=BigDecimal.ZERO;
        BigDecimal bgdCanTrs=BigDecimal.ZERO;
        BigDecimal bgdCanAju=BigDecimal.ZERO;
        //isGenAjuInv=true;
        try{
            if(conRef!=null){
                arlDatAju.clear();

                if(objZafImp.isPedidoCompleto(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp))
                {
                    if(objZafImp.getEstadoDocumento(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "st_ingImp")){
                        if(objZafImp.getEstDoc().equals("B")){
                            if(!objZafImp.isSolicitudPendiente(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp)){
                                if(!objZafImp.isConteoPendienteTransferir(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp))
                                {
                                    stmValAjuIngImp=conRef.createStatement();
                                    strSQL="";
                                    strSQL+=" SELECT d1.co_empCon, d1.co_locCon, d1.co_tipdocCon, d1.co_docCon";
                                    strSQL+="      , d1.co_regCon, d1.nd_canCon, d1.co_itmGrp, d1.co_empSolTrs, d1.co_locSolTrs, d1.co_tipDocSolTrs";
                                    strSQL+="      , d1.nd_canSolTrs, d1.co_empTrs, d1.co_locTrs, d1.co_tipDocTrs, d1.co_docTrs, SUM(d1.nd_canTrs) AS nd_canTrs, d1.co_itmMae";
                                    strSQL+="      , d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.co_regIngImp";
                                    strSQL+="      , d1.ne_numDocIngImp, d1.fe_docIngImp, d1.tx_numDoc2IngImp, d1.nd_canIngImp, d1.nd_cosUni";
                                    strSQL+="      , d1.nd_preUniImp , d1.co_itmEmp, d1.tx_nomItm, d1.tx_uniMed, d1.nd_pesItmKgr, d1.tx_codAlt, d1.tx_codAlt2";
                                    strSQL+=" FROM(";
                                    //CONTEO_TRANSFERENCIA_INGRESOIMPORTACION
                                    strSQL+="     SELECT b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
                                    strSQL+="          , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itm AS co_itmGrp";
                                    strSQL+="          , b2.co_emp AS co_empSolTrs, b2.co_loc AS co_locSolTrs, b2.co_tipDoc AS co_tipDocSolTrs";
                                    strSQL+="          , CASE WHEN b2.nd_canSolTrs IS NULL THEN 0 ELSE b2.nd_canSolTrs END AS nd_canSolTrs";
                                    strSQL+="          , b3.co_empRel AS co_empTrs, b3.co_locRel AS co_locTrs, b3.co_tipdocRel AS co_tipDocTrs, b3.co_docRel AS co_docTrs";
                                    strSQL+="          , CASE WHEN b3.nd_canTrs IS NULL THEN 0 ELSE b3.nd_canTrs END AS nd_canTrs, b4.co_itmMae";
                                    strSQL+="          , b4.co_emp AS co_empIngImp, b4.co_loc AS co_locIngImp, b4.co_tipDoc AS co_tipDocIngImp, b4.co_doc AS co_docIngImp, b4.co_reg AS co_regIngImp";
                                    strSQL+="          , b4.ne_numDoc AS ne_numDocIngImp, b4.fe_doc AS fe_docIngImp, b4.tx_numDoc2 AS tx_numDoc2IngImp";
                                    strSQL+="          , CASE WHEN b4.nd_can IS NULL THEN 0 ELSE b4.nd_can END AS nd_canIngImp, b4.nd_cosUni";
                                    strSQL+="          , b4.nd_preUniImp, b1.co_itmEmp, b1.tx_nomItm, b1.tx_uniMed, b1.nd_pesItmKgr, b1.tx_codAlt, b1.tx_codAlt2";
                                    strSQL+="     FROM(";
                                    //CONTEO
                                    strSQL+="	     SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_stkCon,a1.co_itmMae, a1.co_itm";
                                    strSQL+="	          , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                                    strSQL+="	          , a2.co_itmEmp, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_uniMed, a2.nd_pesItmKgr";
                                    strSQL+="	          , CASE WHEN a2.co_emp IS NULL THEN a1.tx_obs1 ELSE a2.tx_nomItm END AS tx_nomItm";
                                    strSQL+="	     FROM(";
                                    strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                                    strSQL+="                 , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a8.tx_obs1";
                                    strSQL+="            FROM tbm_cabOrdConInv AS a1";
                                    strSQL+="            INNER JOIN (";
                                    strSQL+="                 tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8";
                                    strSQL+="                 ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel";
                                    strSQL+="            )";
                                    strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                                    strSQL+="            LEFT OUTER JOIN (";
                                    strSQL+="                 tbm_invBod AS a3 INNER JOIN";
                                    strSQL+="                         (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
                                    strSQL+="                                             INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                                    strSQL+="                                        ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                                    strSQL+="            )";
                                    strSQL+="            ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
                                    strSQL+="            WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                    strSQL+="            AND a1.co_tipDocRel IN(14,245) AND a1.st_reg='A'";
                                    strSQL+="            AND a1.co_empRel=" + intCodEmpIngImp + " AND a1.co_locRel=" + intCodLocIngImp + "";
                                    strSQL+="            AND a1.co_tipDocRel=" + intCodTipDocIngImp + " AND a1.co_docRel=" + intCodDocIngImp + "";
                                    strSQL+="	     ) AS a1";
                                    strSQL+="	     LEFT OUTER JOIN(";
                                    //Información por empresa
                                    strSQL+=" 	         SELECT a1.co_emp, a1.tx_nomItm, a1.tx_codAlt, a1.tx_codAlt2, a2.tx_desCor AS tx_uniMed, a1.co_itm AS co_itmEmp";
                                    strSQL+="                , a3.co_itmMae, a1.nd_pesItmKgr";
                                    strSQL+=" 	        FROM (tbm_inv AS a1 INNER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                                    strSQL+=" 		INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                                    strSQL+=" 	    ) AS a2";
                                    strSQL+=" 	    ON a1.co_empRel=a2.co_emp AND a1.co_itmMae=a2.co_itmMae";
                                    strSQL+=" ) AS b1";
                                    //SOLICITUD
                                    strSQL+=" LEFT OUTER JOIN(";
                                    strSQL+="        SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_itm, SUM(c1.nd_canSolTrs) AS nd_canSolTrs";
                                    strSQL+="             , c1.co_itmMae, c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+="        FROM(";
                                    strSQL+="                SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numdoc, a2.fe_doc, a2.tx_obs1";
                                    strSQL+="                     , a4.co_reg, a4.co_itm, CASE WHEN a4.nd_can IS NULL THEN 0 ELSE a4.nd_can END AS nd_canSolTrs, a6.co_itmMae";
                                    strSQL+="                     , a3.co_empRelCabMovInv, a3.co_locRelCabMovInv, a3.co_tipDocRelCabMovInv, a3.co_docRelCabMovInv";
                                    strSQL+="                FROM tbm_cabMovInv AS a1 INNER JOIN tbr_cabSolTraInvCabMovInv AS a3";
                                    strSQL+="                                ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                                    strSQL+="                                AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                                    strSQL+="                INNER JOIN tbm_cabSolTraInv AS a2";
                                    strSQL+="                ON a2.co_emp=a3.co_empRelCabSolTraInv AND a2.co_loc=a3.co_locRelCabSolTraInv AND a2.co_tipDoc=a3.co_tipDocRelCabSolTraInv AND a2.co_doc=a3.co_docRelCabSolTraInv";
                                    strSQL+="                INNER JOIN tbm_detSolTraInv AS a4";
                                    strSQL+="                ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc";
                                    strSQL+="                INNER JOIN tbm_inv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
                                    strSQL+="                INNER JOIN tbm_equInv AS a6 ON a5.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
                                    strSQL+="                WHERE a3.co_empRelCabMovInv=" + intCodEmpIngImp + " AND a3.co_locRelCabMovInv=" + intCodLocIngImp + "";
                                    strSQL+="                AND a3.co_tipDocRelCabMovInv=" + intCodTipDocIngImp + " AND a3.co_docRelCabMovInv=" + intCodDocIngImp + "";
                                    strSQL+="                AND a1.st_reg='A' AND a2.st_reg='A'";
                                    strSQL+="                AND a2.st_aut='A'";//AUTORIZADO
                                    strSQL+="        ) AS c1";
                                    strSQL+="        GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_itm";
                                    strSQL+="        , c1.co_itmMae, c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+=" ) AS b2";
                                    strSQL+=" ON b1.co_empRel=b2.co_empRelCabMovInv AND b1.co_locRel=b2.co_locRelCabMovInv AND b1.co_tipDocRel=b2.co_tipDocRelCabMovInv";
                                    strSQL+=" AND b1.co_docRel=b2.co_docRelCabMovInv AND b1.co_itmMae=b2.co_itmMae";
                                    //TRANSFERENCIA
                                    strSQL+=" LEFT OUTER JOIN(";
                                    strSQL+="        SELECT c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_itmMae, SUM(c1.nd_canTrs) AS nd_canTrs, c1.co_itm";
                                    strSQL+="             , c1.co_empRel, c1.co_locRel, c1.co_tipdocRel, c1.co_docRel";
                                    strSQL+="             , c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+="        FROM(";
                                    strSQL+="                SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a4.co_itmMae";
                                    strSQL+="                     , CASE WHEN a2.nd_can IS NULL THEN 0 ELSE ABS(a2.nd_can) END AS nd_canTrs, a2.co_itm";
                                    strSQL+="                     , a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                                    strSQL+="                     , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                                    strSQL+="                     , a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                                    strSQL+="                FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                                    strSQL+="                        ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                                    strSQL+="                INNER JOIN tbm_detMovInv AS a2";
                                    strSQL+="                ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                                    strSQL+="                INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                                    strSQL+="                INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                                    strSQL+="                WHERE a2.nd_can<0 AND (a2.st_reg='A' OR a2.st_reg IS NULL)";
                                    strSQL+="                AND a5.co_empRel=" + intCodEmpIngImp + " AND a5.co_locRel=" + intCodLocIngImp + "";
                                    strSQL+="                AND a5.co_tipDocRel=" + intCodTipDocIngImp + " AND a5.co_docRel=" + intCodDocIngImp + "";
                                    strSQL+="                AND a1.st_ingImp IS NULL";
                                    strSQL+="        ) AS c1";
                                    strSQL+="        GROUP BY c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_itmMae, c1.co_itm";
                                    strSQL+="               , c1.co_empRel, c1.co_locRel, c1.co_tipdocRel, c1.co_docRel";
                                    strSQL+="               , c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+=" ) AS b3";
                                    strSQL+=" ON b2.co_empRelCabMovInv=b3.co_empRel AND b2.co_locRelCabMovInv=b3.co_locRel AND b2.co_tipDocRelCabMovInv=b3.co_tipDocRel AND b2.co_docRelCabMovInv=b3.co_docRel AND b2.co_itmMae=b3.co_itmMae";
                                     //PEDIDO
                                    strSQL+=" LEFT OUTER JOIN(";
                                    strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
                                    strSQL+="                 , a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
                                    strSQL+="                 , a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr, a2.co_itm";
                                    strSQL+="            FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                                    strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                                    strSQL+="            INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                                    strSQL+="            INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                                    strSQL+="            WHERE a1.co_emp=" + intCodEmpIngImp + " AND a1.co_loc=" + intCodLocIngImp + "";
                                    strSQL+="            AND a1.co_tipDoc=" + intCodTipDocIngImp + " AND a1.co_doc=" + intCodDocIngImp + "";
                                    strSQL+="            AND a1.st_reg='A' AND (a2.st_reg='A' OR a2.st_reg IS NULL)";
                                    strSQL+=" ) AS b4";
                                    strSQL+=" ON b1.co_empRel=b4.co_emp AND b1.co_locRel=b4.co_loc AND b1.co_tipDocRel=b4.co_tipDoc AND b1.co_docRel=b4.co_doc AND b1.co_itmMae=b4.co_itmMae";
                                    strSQL+=" ORDER BY b1.co_reg";
                                    strSQL+=" ) AS d1";
                                    strSQL+=" GROUP BY d1.co_empCon, d1.co_locCon, d1.co_tipdocCon, d1.co_docCon";
                                    strSQL+="        , d1.co_regCon, d1.nd_canCon, d1.co_itmGrp, d1.co_empSolTrs, d1.co_locSolTrs, d1.co_tipDocSolTrs";
                                    strSQL+="        , d1.nd_canSolTrs, d1.co_empTrs, d1.co_locTrs, d1.co_tipDocTrs, d1.co_docTrs, d1.co_itmMae";
                                    strSQL+="        , d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.co_regIngImp";
                                    strSQL+="        , d1.ne_numDocIngImp, d1.fe_docIngImp, d1.tx_numDoc2IngImp, d1.nd_canIngImp, d1.nd_cosUni";
                                    strSQL+="        , d1.nd_preUniImp , d1.co_itmEmp, d1.tx_nomItm, d1.tx_uniMed, d1.nd_pesItmKgr, d1.tx_codAlt, d1.tx_codAlt2";
                                    strSQL+=" ORDER BY d1.co_regCon";
                                    System.out.println("isValAjuIngImp: " + strSQL);
                                    rstValAjuIngImp=stmValAjuIngImp.executeQuery(strSQL);
                                    while(rstValAjuIngImp.next()){
                                        arlRegAju=new ArrayList();
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_CON,           "" + rstValAjuIngImp.getString("co_empCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_CON,           "" + rstValAjuIngImp.getString("co_locCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_CON,       "" + rstValAjuIngImp.getString("co_tipdocCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_DOC_CON,           "" + rstValAjuIngImp.getString("co_docCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_REG_CON,           "" + rstValAjuIngImp.getString("co_regCon"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_CON,               "" + rstValAjuIngImp.getString("nd_canCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_MAE,           "" + rstValAjuIngImp.getString("co_itmMae"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_GRP,           "" + rstValAjuIngImp.getString("co_itmGrp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_SOL_TRS,       "" + rstValAjuIngImp.getString("co_empSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_SOL_TRS,       "" + rstValAjuIngImp.getString("co_locSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_SOL_TRS,   "" + rstValAjuIngImp.getString("co_tipDocSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_SOL_TRS,           "" + rstValAjuIngImp.getString("nd_canSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_TRS,           "" + rstValAjuIngImp.getString("co_empTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_TRS,           "" + rstValAjuIngImp.getString("co_locTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_TRS,       "" + rstValAjuIngImp.getString("co_tipDocTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_DOC_TRS,           "" + rstValAjuIngImp.getString("co_docTrs"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_TRS,               "" + rstValAjuIngImp.getString("nd_canTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_ING_IMP,       rstValAjuIngImp.getObject("co_empIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_ING_IMP,       rstValAjuIngImp.getObject("co_locIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_ING_IMP,   rstValAjuIngImp.getObject("co_tipDocIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_DOC_ING_IMP,       rstValAjuIngImp.getObject("co_docIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_REG_ING_IMP,       rstValAjuIngImp.getObject("co_regIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_NUM_DOC_ING_IMP,       rstValAjuIngImp.getObject("ne_numDocIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_FEC_DOC_ING_IMP,       rstValAjuIngImp.getObject("fe_docIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_NUM_PED_ING_IMP,       rstValAjuIngImp.getObject("tx_numDoc2IngImp"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_ING_IMP,           rstValAjuIngImp.getObject("nd_canIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COS_UNI_ING_IMP,       rstValAjuIngImp.getObject("nd_cosUni"));
                                        arlRegAju.add(INT_ARL_AJU_PES_UNI_ING_IMP,       rstValAjuIngImp.getObject("nd_pesItmKgr"));
                                        arlRegAju.add(INT_ARL_AJU_NOM_ITM_ING_IMP,       rstValAjuIngImp.getObject("tx_nomItm"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ALT_ITM_ING_IMP,   rstValAjuIngImp.getObject("tx_codAlt"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_LET_ING_IMP,   rstValAjuIngImp.getObject("tx_codAlt2"));
                                        arlRegAju.add(INT_ARL_AJU_UNI_MED_ING_IMP,       rstValAjuIngImp.getObject("tx_uniMed"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_EMP_ING_IMP,   rstValAjuIngImp.getObject("co_itmEmp"));

                                        arlRegAju.add(INT_ARL_AJU_CAN_AJU,               "");
                                        arlRegAju.add(INT_ARL_AJU_EST_PRO,               "");
                                        arlRegAju.add(INT_ARL_AJU_CAN_ING_IMP_ORI,       rstValAjuIngImp.getObject("nd_canIngImp"));
                                        arlDatAju.add(arlRegAju);

                                    }
//                                    System.out.println("arlDatAju: " + arlDatAju.toString());

                                    for(int i=0; i<arlDatAju.size(); i++){
                                        bgdCanIngImp=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP);
                                        bgdCanCon=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_CON);
                                        bgdCanSolTrs=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_SOL_TRS);
                                        bgdCanTrs=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_TRS);

                                        bgdCanAju=BigDecimal.ZERO;

                                        if(bgdCanIngImp.compareTo(BigDecimal.ZERO)==0){//No existe item que ha sido contado por eso la cantidad de Ing.Imp. es cero
                                            if(bgdCanCon.compareTo(bgdCanIngImp)!=0){ //Valida conteos dados de baja que no existian en el inimpo.
                                                objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "I");//No existe el item en ese Ing.Imp.
                                                bgdCanAju=bgdCanCon;
                                                objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, (bgdCanAju.abs()).toString());
                                            }
                                        }
                                        else if(  (bgdCanIngImp.compareTo(bgdCanCon)==0) && (bgdCanCon.compareTo(bgdCanSolTrs)==0) && (bgdCanSolTrs.compareTo(bgdCanTrs)==0)  ){
                                            objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "N");//No existe ajuste para ese item
                                        }
                                        else{
                                            if(bgdCanSolTrs.compareTo(bgdCanTrs)==0){
                                                //Todos iguales
                                                if(bgdCanCon.compareTo(bgdCanIngImp)==0){
                                                    if(bgdCanCon.compareTo(bgdCanTrs)==0){
                                                         bgdCanAju=bgdCanCon.subtract(bgdCanIngImp);
                                                         if(bgdCanAju.compareTo(BigDecimal.ZERO)!=0){
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, bgdCanAju.toString());
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "S");
                                                         }
                                                    }
                                                    else{
                                                        objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                        System.out.println("1) bgdCanIngImp: " + bgdCanIngImp+" <-> bgdCanCon: "+bgdCanCon+" <-> bgdCanSolTrs: "+bgdCanSolTrs+" <-> bgdCanTrs: "+bgdCanTrs);
                                                    }
                                                }
                                                //Conteo > Ing.Imp.
                                                else if(bgdCanCon.compareTo(bgdCanIngImp)>0){
                                                    if(bgdCanTrs.compareTo(bgdCanIngImp)==0){
                                                         bgdCanAju=bgdCanCon.subtract(bgdCanIngImp);
                                                         if(bgdCanAju.compareTo(BigDecimal.ZERO)!=0){
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, bgdCanAju.toString());
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "S");
                                                         }
                                                    }
                                                    else{
                                                        objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                        System.out.println("2) bgdCanIngImp: " + bgdCanIngImp+" <-> bgdCanCon: "+bgdCanCon+" <-> bgdCanSolTrs: "+bgdCanSolTrs+" <-> bgdCanTrs: "+bgdCanTrs);
                                                    }
                                                }
                                                //Conteo < Ing.Imp.
                                                else if(bgdCanCon.compareTo(bgdCanIngImp)<0){
                                                    if(bgdCanTrs.compareTo(bgdCanCon)==0){
                                                         bgdCanAju=bgdCanCon.subtract(bgdCanIngImp);
                                                         if(bgdCanAju.compareTo(BigDecimal.ZERO)!=0){
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, bgdCanAju.toString());
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "S");
                                                         }
                                                    }
                                                    else{
                                                        objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                        System.out.println("3) bgdCanIngImp: " + bgdCanIngImp+" <-> bgdCanCon: "+bgdCanCon+" <-> bgdCanSolTrs: "+bgdCanSolTrs+" <-> bgdCanTrs: "+bgdCanTrs);
                                                    }
                                                }
                                                else{
                                                    objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                    System.out.println("4) bgdCanIngImp: " + bgdCanIngImp+" <-> bgdCanCon: "+bgdCanCon+" <-> bgdCanSolTrs: "+bgdCanSolTrs+" <-> bgdCanTrs: "+bgdCanTrs);
                                                }
                                            }
                                            else{//la solicitud es diferente a la transferencia
                                                objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                System.out.println("5) bgdCanIngImp: " + bgdCanIngImp+" <-> bgdCanCon: "+bgdCanCon+" <-> bgdCanSolTrs: "+bgdCanSolTrs+" <-> bgdCanTrs: "+bgdCanTrs);
                                            }
                                        }
                                    }
                                    System.out.println("*arlDatAju: " + arlDatAju.toString());
                                    if(isErrorAjuste()){
                                        mostrarMsgInf("Existen items que están pendientes de procesar");
                                        blnRes=false;
                                    }
                                    else{
                                         if(isAjuste()){
                                            if(!cargarValAju()){
                                                mostrarMsgInf("<HTML>Los datos de ajuste no se cargaron correctamente.<BR>Verifique e intente nuevamente.</HTML>");
                                                blnRes=false;
                                            }
                                        }
                                        else
                                             mostrarMsgInf("El documento no ha generado ningún tipo de Ajuste.");
                                    }
                                    stmValAjuIngImp.close();
                                    stmValAjuIngImp=null;
                                    rstValAjuIngImp.close();
                                    rstValAjuIngImp=null;
                                }
                                else{
                                    //mostrarMsgInf("Existen solicitudes pendientes de autorizar(Transferir)");  no se ponen porque en la clase ya estan
                                    blnRes=false;
                                }
                            }
                            else{
                                //mostrarMsgInf("Existen solicitudes pendientes de generarse");  no se ponen porque en la clase ya estan
                                blnRes=false;
                            }
                        }
                        else
                            blnRes=false;
                    }
                    else{
                        blnRes=false;
                    }
                }
                else{
                    //isGenAjuInv=false;
                    mostrarMsgInf("El pedido no ha generado Ajuste de inventario..");
                }

                setValDoc();
                setTipMovAjuLet();
                setTipAjuLar();

                eliminarItemsCantidadCero();
                calcularCosTotGenAsiDia();
//                generaDiarioAjuste();
//                actualizarGlo();
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

    private boolean isErrorAjuste(){
        boolean blnRes=false;
        String strLin="";
        try{
            for(int i=0; i<arlDatAju.size();i++){
                strLin=objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO);
                if(strLin.equals("E")){
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite determinar si existe un ajuste de inventario
     * @return true: Si existe ajuste de inventario
     * <BR> false: Caso contrario
     */
    private boolean isAjuste(){
        boolean blnRes=false;
        String strLin="";
        try{
            for(int i=0; i<arlDatAju.size();i++){
                strLin=objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO);
                if(  (strLin.equals("S")) ||  (strLin.equals("I")) ){
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean isNotAjuste(){
        boolean blnRes=true;
        try{
            isAjuPro=true;//se pone a true pero no se ha generado ajuste
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    ////********************************************************************************************************************    

    private void clickInsertar() {
        try{
            limpiarFrm();
            datFecAux=null;
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            mostrarTipDocPre();
            objAsiDia.inicializar();

            blnHayCam=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean beforeInsertar() {
        boolean blnRes=true;
            if (!isCamVal())
                blnRes=false;
        return blnRes;
    }
    
    private boolean insertar() {
        if (!insertarReg())
            return false;
        return true;
    }    
    
    private void clickModificar() 
    {
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        txtCodTipDoc.setEditable(false);
        txtDesCorTipDoc.setEditable(false);
        txtDesLarTipDoc.setEditable(false);
        butTipDoc.setEnabled(false);
        txtCodIngImp.setEditable(false);
        txtNumDocIngImp.setEditable(false);
        txtNumPedIngImp.setEditable(false);
        butPedImp.setEnabled(false);
        dtpFecDoc.setEnabled(false);
        txtNumDoc.setEditable(false);
        txtCodDoc.setEditable(false);
        txtValDoc.setEditable(false);
        txtPesDoc.setEditable(false);
        txtCodImp.setEditable(false);
        txtNomImp.setEditable(false);
        txtCodExp.setEditable(false);
        txtNomExp.setEditable(false);
        txtNomExp2.setEditable(false);

        if(isValUsrModDoc()){
            System.out.println("Puede modificar documento");
        }
    }
    
    private boolean beforeModificar(){
       boolean blnRes=true;
       int intCodMae=-1;
       BigDecimal bgdCosUni=BigDecimal.ZERO;
       try{
            if(intButSelDlg==INT_BUT_ACE){
                objTblMod.removeEmptyRows();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    intCodMae=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?"-1":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"-1":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString()));
                    bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString()));
                    
                    if(intCodMae==-1){
                        mostrarMsgInf("<HTML>El dato de item ingresado no es el correcto.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        blnRes=false;
                        break;
                    }
                    
                    if(bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                        mostrarMsgInf("<HTML>El costo del item ingresado con valor cero.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        blnRes=false;
                        break;
                    }
                }
            }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
    }

    private boolean modificar()
    {
        boolean blnRes=true;
        if(isModDoc){
            if(actualizarReg()){
                blnRes=true;
            }
            else
                blnRes=false;
        }
        else{
            mostrarMsgInf("<HTML>Existen vistos buenos previos no concedidos<BR>o el usuario no cuenta con los permisos respectivos para modificar.</HTML>");
            blnRes=false;
        }
        return blnRes;
    }       
   
    private boolean afterModificar(){
       boolean blnRes=true;
       try{
            if(rstCab!=null){
                rstCab.close();
                rstCab=null;
            }
            if(stmCab!=null){
                stmCab.close();
                stmCab=null;
            }
       }
       catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
       return blnRes;
    }    
    
    private boolean cancelar() 
    {
        boolean blnRes=true;
        try{
            if (rstCab!=null){
                rstCab.close();
                stmCab.close();
                rstCab=null;
                stmCab=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        limpiarFrm();
        blnHayCam=false;
        return blnRes;
    }        
    
    ////********************************************************************************************************************

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try{
            objCodSegInsAnt=null;
            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;
            strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

            if (conRef!=null)
            {
                //inserta ajuste
                if(isAjuste()){
                    System.out.println("****SI EXISTE AJUSTE");
                    if(insertar_tbmCabMovInv()){
                        if(actualizar_tbmCabTipDoc()){
                            //Actualiza el estado  que indica que en el Ingreso por Importacion SI existe un ajuste de inventario
                            if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "st_cieAjuInvIngImp", "'S'")){
                                //Actualiza el estado del documento en el Ingreso por Importacion
                                if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "st_ingImp", "'C'")){
                                    if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "co_usrUltModIngImp", objParSis.getCodigoUsuario())){
                                        if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "fe_ultModIngImp", "'" + strAux + "'")){
                                            if(setAsiEstDocAju()){
                                                //Actualiza el estado del documento en el Ajuste
                                                if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "tbm_cabMovInv", "st_reg", "'" + strDocActivo + "'")){ 
                                                    //Actualiza el estado del documento en el Ajuste
                                                    if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "tbm_cabMovInv", "st_aut", "'" + strEstAutDenVisBue + "'")){
                                                        if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "tbm_cabMovInv", "co_usrIng", "" + objParSis.getCodigoUsuario())){
                                                            if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) , "tbm_cabMovInv", "fe_ing", "'" + strAux + "'")){
                                                                //Actualiza el estado del documento en el Ajuste
                                                                if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "tbm_cabMovInv", "st_ingImp", "'" + strEstIngImpDocAju + "'")){
                                                                    if(insertar_tbmDetMovInv(conRef)){
                                                                        if(isModDatGenAjuInvSob()){
                                                                            if(insertar_tbmCabOrdDis()){
                                                                                if(insertar_tbmDetOrdDis()){
                                                                                    if(setEstEliItmSob()){
                                                                                        if(insertar_detDisItm(conRef)){
                                                                                            if(calcularCosTotGenAsiDia()){
                                                                                                if(generaDiarioAjuste()){
                                                                                                    if (objAsiDia.insertarDiario(conRef, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "" + strDocActivo + "")){
                                                                                                        if(getCodSegIngImp()){
                                                                                                            if(objSegMovInv.setSegMovInvCompra(conRef, objCodSegInsAnt, 5, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                                                                                                                if(conRef!=null)
                                                                                                                    conRef.commit();
                                                                                                                blnRes=true;
                                                                                                            }
                                                                                                            else
                                                                                                                conRef.rollback();
                                                                                                        }
                                                                                                        else
                                                                                                            conRef.rollback();
                                                                                                    }
                                                                                                    else
                                                                                                        conRef.rollback();
                                                                                                }
                                                                                                else
                                                                                                    conRef.rollback();
                                                                                            }
                                                                                            else
                                                                                                conRef.rollback();
                                                                                        }
                                                                                        else
                                                                                            conRef.rollback();
                                                                                    }
                                                                                    else
                                                                                        conRef.rollback();
                                                                                }
                                                                                else
                                                                                    conRef.rollback();
                                                                            }
                                                                            else
                                                                                conRef.rollback();
                                                                        }
                                                                        else
                                                                            conRef.rollback();
                                                                    }
                                                                    else
                                                                        conRef.rollback();
                                                                }
                                                                else
                                                                    conRef.rollback();
                                                            }
                                                            else
                                                                conRef.rollback();
                                                        }
                                                        else
                                                            conRef.rollback();
                                                    }
                                                    else
                                                        conRef.rollback();
                                                }
                                                else
                                                    conRef.rollback();
                                            }
                                            else
                                                conRef.rollback();
                                        }
                                        else
                                            conRef.rollback();
                                    }
                                    else
                                        conRef.rollback();
                                }
                                else
                                    conRef.rollback();
                            }
                            else
                                conRef.rollback();
                        }

                    }
                    else
                        conRef.rollback();
                }
                else if(isNotAjuste()){//si no existe ajuste se debe insetar campos de estados y auditoria
                    System.out.println("****NO EXISTE AJUSTE");
                        //Actualiza el estado  que indica que en el Ingreso por Importacion NO existe un ajuste de inventario
                        if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "st_cieAjuInvIngImp", "'N'")){
                            //Actualiza el estado del documento en el Ingreso por Importacion
                            if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "st_ingImp", "'C'")){
                                if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "co_usrUltModIngImp", objParSis.getCodigoUsuario())){
                                    if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "fe_ultModIngImp", "'" + strAux + "'")){
                                        if(conRef!=null)
                                            conRef.commit();
                                        blnRes=true;
                                    }
                                    else
                                        conRef.rollback();
                                }
                                else
                                    conRef.rollback();
                            }
                            else
                                conRef.rollback();
                        }
                        else
                            conRef.rollback();
                }                
            }

            isAjuPro=blnRes;
            System.out.println("******-----------------------------------------------isAjuPro: " + isAjuPro);
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
     * Función que permite cargar los items que se deben ajustar de manera automática de acuerdo al Ingreso por Importación/Compra Local seleccionado
     * @return true: Si se pudo cargar la información
     * <BR>   false: Caso contrario
     */
    private boolean cargarValAju(){
        boolean blnRes=true;
        String strLin="";
        BigDecimal bgdCanItm=BigDecimal.ZERO;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        BigDecimal bgdCosTot=BigDecimal.ZERO;
        BigDecimal bgdPesUni=BigDecimal.ZERO;
        BigDecimal bgdPesTot=BigDecimal.ZERO;
        int intCodEmpIngImp=-1;//Para saber si el item existe en el Ingreso por Importacion
        try{
            vecDat.clear();
            for(int i=0; i<arlDatAju.size();i++){
                strLin=objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO);
                if( (strLin.equals("S")) || (strLin.equals("I")) ){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"M");
                    vecReg.add(INT_TBL_DAT_CHK, true);

                    intCodEmpIngImp=Integer.parseInt(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_EMP_ING_IMP)==null?"-1":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_EMP_ING_IMP).equals("")?"-1":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_EMP_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,     intCodEmpIngImp);
                    vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_LOC_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_TIP_DOC_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_DOC_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_REG_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_GRP,         objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_GRP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_EMP,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_EMP_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_MAE));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_ALT_ITM_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_LET,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_LET_ING_IMP));
                    vecReg.add(INT_TBL_DAT_BUT_ITM,             null);
                    vecReg.add(INT_TBL_DAT_NOM_ITM,             objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_NOM_ITM_ING_IMP));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_UNI_MED_ING_IMP));
                    vecReg.add(INT_TBL_DAT_TIP_MOV_DES,             null);
                    vecReg.add(INT_TBL_DAT_TIP_MOV_LET,             null);
                    bgdCanItm=new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU));
                    vecReg.add(INT_TBL_DAT_CAN_AJU,         bgdCanItm);
                    bgdCosUni=new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COS_UNI_ING_IMP)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COS_UNI_ING_IMP).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COS_UNI_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COS_UNI_ING_IMP,     bgdCosUni);
                    bgdCosTot=bgdCanItm.multiply(bgdCosUni);
                    vecReg.add(INT_TBL_DAT_COS_TOT,             bgdCosTot);
                    bgdPesUni=new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_PES_UNI_ING_IMP)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_PES_UNI_ING_IMP).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_PES_UNI_ING_IMP));
                    vecReg.add(INT_TBL_DAT_PES_UNI,             bgdPesUni);
                    vecReg.add(INT_TBL_DAT_PES_TOT,             bgdCanItm.multiply(bgdPesUni));
                    vecReg.add(INT_TBL_DAT_CAN_ING_IMP,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP));
                    vecReg.add(INT_TBL_DAT_CAN_CON,             objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_CON));
                    vecReg.add(INT_TBL_DAT_CAN_TRS,             objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_TRS));
                    vecReg.add(INT_TBL_DAT_EST_EXI_ITM,         "");
                    vecReg.add(INT_TBL_DAT_CAN_ING_EGR,         new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP_ORI)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP_ORI).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP_ORI)));

                    if(intCodEmpIngImp==-1)//No existe en el Ingreso por Importación
                        vecReg.setElementAt("N", INT_TBL_DAT_EST_EXI_ITM);
                    else//Si existe en el Ingreso por Importación
                        vecReg.setElementAt("S", INT_TBL_DAT_EST_EXI_ITM);
                    vecDat.add(vecReg);

                }
            }
            //if(!setVecModDat(vecDat))
                //mostrarMsgInf("<HTML>Los datos de ajuste no se cargaron correctamente.<BR>Verifique e intente nuevamente.</HTML>");

            setVecModDat(vecDat);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    public boolean setVecModDat(Vector vectorDatos){
        boolean blnRes=true;
        try{
            //Asignar vectores al modelo.
            objTblMod.setData(vectorDatos);
            tblDat.setModel(objTblMod);
//            System.out.println("vecDat: " + vecDat.toString());
            vecDat.clear();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean getSecuencias(){
        boolean blnRes=true;
        try{
            if(conRef!=null){
                intSecGrp=objUltDocPrint2.getNumSecDoc(conRef, objParSis.getCodigoEmpresaGrupo() );     //Secuencia de Grupo
                intSecEmp=objUltDocPrint2.getNumSecDoc(conRef, Integer.parseInt(txtCodImp.getText()) ); //la empresa-importador
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar los items que se estan generando en conteo de inventario y que habian sido ingresados como una descripcion
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarConInv(){
        boolean blnRes=true;
        String strLin="";
        String strSQLUpd="";
        try{
            if (conRef!=null){
                stm=conRef.createStatement();
                
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    //System.out.println("actualizarConInv.Item Nuevo: " + strLin);
                    if(strLin.equals("I")){
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" UPDATE tbm_conInv SET co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP) + "";
                        strSQL+=" WHERE co_emp=" + intCodEmpOrdCon + "";
                        strSQL+=" AND co_locRel=" + intCodLocOrdCon + "";
                        strSQL+=" AND co_tipDocRel=" + intCodTipDocOrdCon + "";
                        strSQL+=" AND co_docRel=" + intCodDocOrdCon + "";
                        strSQL+=" AND co_regRel=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_ING_IMP) + "";
                        strSQL+=";";
                        System.out.println("strSQL: " + strSQL);
                        strSQLUpd+=strSQL;
                    }
                }
                System.out.println("actualizarConInv: " + strSQLUpd);
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
     * Esta función permite actualizar la cantidad disponible del item cuando el documento de ajuste está activo
     * @return true: Si se pudo insertar la detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_detDisItm(Connection conDet){
        boolean blnRes=true;
        BigDecimal bgdCanAjuIngEgr=BigDecimal.ZERO;//Cantidad que se debe ajustar
        try{
            if (conDet!=null){
                if(strDocActivo.equals("A")){
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        
                        bgdCanAjuIngEgr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                        if(!_setArlStkInv(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString())
                                , Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString())
                                , objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_LET).toString()
                                , Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString())
                                , bgdCanAjuIngEgr
                                , Integer.parseInt(txtCodBodEmp.getText()))){
                            blnRes=false;
                        }
                    }//fin de for
                    
                    if(objStkInv.actualizaInventario(conDet, intCodEmpIngImp, 0, "+", 1, arlDatStkInvItm)){   //parámetro: 1 - valida y actualiza en tbm_inv
                        if(objStkInv.actualizaDisponible(conDet, intCodEmpIngImp, 10, "+", 1, arlDatDisInvItm)){
                            if(bgdCanAjuIngEgr.compareTo(BigDecimal.ZERO)<0){
                                if(objStkInv.actualizaInventario(conDet, intCodEmpIngImp, 3, "+", 0, arlDatStkInvItm)){
                                }
                            }
                            arlDatStkInvItm.clear();
                            arlDatDisInvItm.clear();
                        }
                        else
                           blnRes=false;
                    }
                    else{
                        blnRes=false;
                        mostrarMsgInf("Es posible que existan problemas de inventario.");
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
     * Función que permite cargar información que afectará a las tablas de inventario
     * @param intCodItmGrp : Código de item en el grupo
     * @param intCodItmMae : Código de item maestro
     * @param strCodItmLet : Código de item en letras
     * @param intCodItm    : Código de item en la empresa
     * @param bgdCanMov    : Cantidad
     * @param intCodBodEmp : Código de bodega de empresa
     * @return
     */
    private boolean _setArlStkInv(int intCodItmGrp, int intCodItmMae, String strCodItmLet
            , int intCodItm, BigDecimal bgdCanMov,int intCodBodEmp){
        boolean blnRes=true;
        try{
            arlRegStkInvItm = new ArrayList();
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP, intCodItmGrp);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP, intCodItm);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE, intCodItmMae);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodItmLet);
            arlRegStkInvItm.add(INT_ARL_STK_INV_CAN_ITM,     bgdCanMov);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP, intCodBodEmp);
            arlDatStkInvItm.add(arlRegStkInvItm);

            //System.out.println("bgdCanMov: " + bgdCanMov);
            
            if(bgdCanMov.compareTo(BigDecimal.ZERO)>0){//Sobrante por tanto se adiciona al arreglo para actualizar en tbm_invBod.nd_canDis
                arlRegDisInvItm = new ArrayList();
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP, intCodItmGrp);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP, intCodItm);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE, intCodItmMae);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodItmLet);
                arlRegDisInvItm.add(INT_ARL_STK_INV_CAN_ITM,     bgdCanMov);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP, intCodBodEmp);
                arlDatDisInvItm.add(arlRegDisInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    }
  
    private boolean generaDiarioAjuste()
    {
        boolean blnRes=true;
        String strSQLGenDiaAju="";
        String strTipMovLet="";
        String strEstUni="";
        int intCodCtaUni=-1;
        int intCodCtaBuc=-1;
        
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
         
        int intCodCtaDeb=-1;
        int intCodCtaHab=-1;
        String strNumCtaDeb="", strNomCtaDeb="";
        String strNumCtaHab="", strNomCtaHab="";
        
        BigDecimal bgdValDeb=BigDecimal.ZERO, bgdValHab=BigDecimal.ZERO;
//        BigDecimal bgdValDebEmp=BigDecimal.ZERO, bgdValHabEmp=BigDecimal.ZERO;
//        BigDecimal bgdValDebRecTer=BigDecimal.ZERO, bgdValHabRecTer=BigDecimal.ZERO;
//        BigDecimal bgdValDebRecPrv=BigDecimal.ZERO, bgdValHabRecPrv=BigDecimal.ZERO;

        //valor del documento según el tipo de movimiento
        BigDecimal bgdCanAsi=BigDecimal.ZERO;//Cantidad del item (Usado por tipo de movimiento "Empresa" para saber si es un Ingreso o un Egreso)
        BigDecimal bgdTotCos=BigDecimal.ZERO;//Cantidad del item (Usado por tipo de movimiento "Empresa" para saber si es un Ingreso o un Egreso)
        
        Statement stmGenDiaAju;
        ResultSet rstGenDiaAju;
        String strSQLAsiDiaUni="", strSQLAsiDiaUniAcu="";
        
        try{
            ///para generar el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();
            
            arlDatDiaAju.clear();

            int intCfgDb=-1;
            
            if(conRef!=null){
                stmGenDiaAju=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_cfg, b1.tx_nom, b1.co_loc, b1.co_tipdoc, b1.tx_tipMov, b1.tx_obs1, b1.co_mnu";
                strSQL+="      , b1.co_ctaDeb, b2.tx_codCta AS tx_codCtaDeb, b2.tx_desLar AS tx_desLarDeb";
                strSQL+="      , b1.co_ctaHab, b3.tx_codCta AS tx_codCtaHab, b3.tx_desLar AS tx_desLarHab";
                strSQL+="      , b1.st_reg";
                strSQL+=" FROM(";
                strSQL+=" 	 SELECT a1.co_emp, a1.co_cfg, a1.tx_nom, a1.co_loc, a1.co_tipdoc, a1.tx_tipMov, a1.tx_obs1, a1.co_mnu";
                strSQL+=" 	      , a1.co_ctadeb, a1.co_ctahab, a1.st_reg";
                strSQL+=" 	 FROM tbm_cfgTipDocCtaPrg AS a1";
                if(chrTipOpe=='n'){//Inserción
                    strSQL+="    WHERE a1.co_emp=" + intCodEmpIngImp + "";
                    strSQL+="    AND a1.co_loc=" + intCodLocIngImp + "";
                    strSQL+="    AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                }
                else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){//Modificación
                    strSQL+="    WHERE a1.co_emp=" + intCodEmpAju + "";
                    strSQL+="    AND a1.co_loc=" + intCodLocAju + "";
                    strSQL+="    AND a1.co_tipdoc=" + intCodTipDocAju + "";
                }
                strSQL+="       ORDER BY a1.co_cfg";
                strSQL+=" ) AS b1";
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" 	FROM tbm_plaCta AS a1";
                if(chrTipOpe=='n')
                    strSQL+=" 	WHERE a1.co_emp=" + intCodEmpIngImp + "";
                else if( (chrTipOpe=='x') || (chrTipOpe=='m') )
                    strSQL+=" 	WHERE a1.co_emp=" + intCodEmpAju + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_ctaDeb=b2.co_cta";
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" 	FROM tbm_plaCta AS a1";
                if(chrTipOpe=='n')
                    strSQL+=" 	WHERE a1.co_emp=" + intCodEmpIngImp + "";
                else if( (chrTipOpe=='x') || (chrTipOpe=='m') )
                    strSQL+=" 	WHERE a1.co_emp=" + intCodEmpAju + "";
                strSQL+=" ) AS b3";
                strSQL+=" ON b1.co_emp=b3.co_emp AND b1.co_ctaHab=b3.co_cta";
                strSQL+=" ORDER BY b1.co_cfg";
                System.out.println("generaDiarioAjuste: " + strSQL);
                rstGenDiaAju=stmGenDiaAju.executeQuery(strSQL);
                while(rstGenDiaAju.next()){
                    arlRegDiaAju=new ArrayList();
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_COD_EMP, rstGenDiaAju.getString("co_emp"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_COD_CFG, rstGenDiaAju.getString("co_cfg"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_NOM_PRG, rstGenDiaAju.getString("tx_nom"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_COD_LOC, rstGenDiaAju.getString("co_loc"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_COD_TIP_DOC, rstGenDiaAju.getString("co_tipdoc"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_TIP_MOV, rstGenDiaAju.getString("tx_tipMov"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_OBS, rstGenDiaAju.getString("tx_obs1"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_COD_MNU, rstGenDiaAju.getString("co_mnu"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_COD_DEB, rstGenDiaAju.getString("co_ctadeb"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_NUM_DEB, rstGenDiaAju.getString("tx_codCtaDeb"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_NOM_DEB, rstGenDiaAju.getString("tx_desLarDeb"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_COD_HAB, rstGenDiaAju.getString("co_ctaHab"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_NUM_HAB, rstGenDiaAju.getString("tx_codCtaHab"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_NOM_HAB, rstGenDiaAju.getString("tx_desLarHab"));
                    arlRegDiaAju.add(INT_ARL_ASI_DIA_AJU_EST_REG, rstGenDiaAju.getString("st_reg"));
                    arlDatDiaAju.add(arlRegDiaAju);
                }
                System.out.println("arlDatDiaAju: " + arlDatDiaAju.toString());
                
                strSQLAsiDiaUni="";
                strSQLAsiDiaUni+=" SELECT c1.co_cta, c1.tx_numCta, c1.tx_nomCta";
                strSQLAsiDiaUni+="      , CASE WHEN c1.nd_monDeb<c1.nd_monHab THEN 0 ELSE ABS(c1.nd_monDeb-c1.nd_monHab) END AS nd_monDeb";
                strSQLAsiDiaUni+="      , CASE WHEN c1.nd_monDeb<c1.nd_monHab THEN ABS(c1.nd_monDeb-c1.nd_monHab) ELSE 0 END AS nd_monHab";
                strSQLAsiDiaUni+=" FROM(";
                strSQLAsiDiaUni+=" 	SELECT b1.co_cta, b1.tx_numCta, b1.tx_nomCta, SUM(b1.nd_monDeb) AS nd_monDeb, SUM(b1.nd_monHab) AS nd_monHab";
                strSQLAsiDiaUni+=" 	FROM(";
                strSQLAsiDiaUniAcu+=strSQLAsiDiaUni;
                
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strTipMovLet=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET).toString();
                    bgdTotCos=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString());
                    bgdCanAsi=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString());
                    
                    switch(strTipMovLet){
                        case "E":
                            bgdValDeb=bgdTotCos;
                            bgdValHab=bgdTotCos;

                            if(bgdCanAsi.compareTo(BigDecimal.ZERO)>0){//"I"
                                for(int j=0; j<arlDatDiaAju.size(); j++){
                                    intCfgDb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_CFG);//1
                                    if( (intCfgDb==1) || (intCfgDb==5) || (intCfgDb==9) || (intCfgDb==13) || (intCfgDb==15) || (intCfgDb==17) ){
                                        //Debe
                                        intCodCtaDeb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_DEB);
                                        strNumCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_DEB);
                                        strNomCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_DEB);
                                        //Haber
                                        intCodCtaHab=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_HAB);
                                        strNumCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_HAB);
                                        strNomCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_HAB);
                                    }
                                }
                            }
                            else if(bgdCanAsi.compareTo(BigDecimal.ZERO)<0){//"E"
                                for(int j=0; j<arlDatDiaAju.size(); j++){
                                    intCfgDb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_CFG);//2
                                    if( (intCfgDb==2) || (intCfgDb==6) || (intCfgDb==10) || (intCfgDb==14) || (intCfgDb==16) || (intCfgDb==18) ){
                                        //Debe
                                        intCodCtaDeb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_DEB);
                                        strNumCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_DEB);
                                        strNomCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_DEB);
                                        //Haber
                                        intCodCtaHab=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_HAB);
                                        strNumCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_HAB);
                                        strNomCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_HAB);
                                    }
                                }
                            }

                            break;
                        case "T":
                            bgdValDeb=bgdTotCos;
                            bgdValHab=bgdTotCos;
                            
                            if(bgdCanAsi.compareTo(BigDecimal.ZERO)<0){//T
                                for(int j=0; j<arlDatDiaAju.size(); j++){
                                    intCfgDb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_CFG);//2
                                    if( (intCfgDb==3) || (intCfgDb==7) || (intCfgDb==11) ){
                                        //Debe
                                        intCodCtaDeb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_DEB);
                                        strNumCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_DEB);
                                        strNomCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_DEB);
                                        //Haber
                                        intCodCtaHab=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_HAB);
                                        strNumCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_HAB);
                                        strNomCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_HAB);
                                    }
                                }
                            }
                            break;
                        case "P":
                            bgdValDeb=bgdTotCos;
                            bgdValHab=bgdTotCos;
                            
                            if(bgdCanAsi.compareTo(BigDecimal.ZERO)<0){//P
                                for(int j=0; j<arlDatDiaAju.size(); j++){
                                    intCfgDb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_CFG);//2
                                    if( (intCfgDb==4) || (intCfgDb==8) || (intCfgDb==12) ){
                                        //Debe
                                        intCodCtaDeb=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_DEB);
                                        strNumCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_DEB);
                                        strNomCtaDeb=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_DEB);
                                        //Haber
                                        intCodCtaHab=objUti.getIntValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_COD_HAB);
                                        strNumCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NUM_HAB);
                                        strNomCtaHab=objUti.getStringValueAt(arlDatDiaAju, j, INT_ARL_ASI_DIA_AJU_NOM_HAB);
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    
                    strSQLAsiDiaUni="";
                    if(i!=0)
                        strSQLAsiDiaUni+=" UNION ALL";
                    strSQLAsiDiaUni+=" (SELECT " + intCodCtaDeb + " AS co_cta";
                    strSQLAsiDiaUni+=" , '" + strNumCtaDeb + "' AS tx_numCta";
                    strSQLAsiDiaUni+=" , '" + strNomCtaDeb + "' AS tx_nomCta";
                    strSQLAsiDiaUni+=" , ABS(" + bgdValDeb + ") AS nd_monDeb";
                    strSQLAsiDiaUni+=" , ABS(0) AS nd_monHab)";
                    strSQLAsiDiaUni+=" UNION ALL";
                    strSQLAsiDiaUni+=" (SELECT " + intCodCtaHab + " AS co_cta";
                    strSQLAsiDiaUni+=" , '" + strNumCtaHab + "' AS tx_numCta";
                    strSQLAsiDiaUni+=" , '" + strNomCtaHab + "' AS tx_nomCta";
                    strSQLAsiDiaUni+=" , ABS(0) AS nd_monDeb";
                    strSQLAsiDiaUni+=" , ABS(" + bgdValHab + ") AS nd_monHab)";
                    strSQLAsiDiaUniAcu+=strSQLAsiDiaUni;
                    strSQLAsiDiaUni="";
                }//fin del for
                
                strSQLAsiDiaUni+=" 	) AS b1";
                strSQLAsiDiaUni+=" 	GROUP BY b1.co_cta, b1.tx_numCta, b1.tx_nomCta";
                strSQLAsiDiaUni+=" ) AS c1";
                strSQLAsiDiaUniAcu+=strSQLAsiDiaUni;
                System.out.println("strSQLAsiDiaUniAcu: " + strSQLAsiDiaUniAcu);
                rstGenDiaAju=stmGenDiaAju.executeQuery(strSQLAsiDiaUniAcu);
                while(rstGenDiaAju.next()){
                    //Debe y haber 
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, rstGenDiaAju.getInt("co_cta"));
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, rstGenDiaAju.getString("tx_numCta"));
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, rstGenDiaAju.getString("tx_nomCta"));
                    vecRegDia.add(INT_VEC_DIA_DEB, rstGenDiaAju.getBigDecimal("nd_monDeb"));
                    vecRegDia.add(INT_VEC_DIA_HAB, rstGenDiaAju.getBigDecimal("nd_monHab"));
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }
                stmGenDiaAju.close();
                stmGenDiaAju=null;
                rstGenDiaAju.close();
                rstGenDiaAju=null;
            }
            System.out.println("vecDatDia: " + vecDatDia.toString());
            objAsiDia.setDetalleDiario(vecDatDia);
//                System.out.println("getDetalleDiario: " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
            actualizarGlo();
            unificarCuentasContablesDiario();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()
    {
        strAux="";
        strAux+=" Ajuste: " + txtDesCorTipDoc.getText();
        strAux+=" # " + txtNumDoc.getText();
        strAux+="; Pedido: " + txtNumPedIngImp.getText();
        objAsiDia.setGlosa(strAux);
    }    
    
    /**
     * Función que permite calcular el valor del documento y colocarlo en el campo de Valor del Documento
     * @return
     */
    private boolean setValDoc(){
        boolean blnRes=true;
        BigDecimal bgdValDoc=BigDecimal.ZERO;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        BigDecimal bgdValTotDoc=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdValDoc=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()));
                bgdValTotDoc=bgdValTotDoc.add(bgdValDoc.multiply(bgdCosUni));
            }
            bgdValTotDoc=objUti.redondearBigDecimal(bgdValTotDoc, objParSis.getDecimalesMostrar());
            txtValDoc.setText(bgdValTotDoc.toString());
            bgdValTotDoc=BigDecimal.ZERO;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que permite conocer si existe el ingreso por importacion que se desea ajustar
     * @return
     */
    private boolean isIngresoImportacion(){
        boolean blnRes=false;
        Statement stm1;
        ResultSet rst1;
        try{
            if(conRef!=null){
                stm1=conRef.createStatement();
                
                System.out.println("isIngresoImportacion - intCodTipDocIngImp: " + intCodTipDocIngImp);
                
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a2.tx_nom AS tx_nomEmp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" , a4.co_bod AS co_bodEmp, a4.tx_nomBodOrgDes AS tx_nomBodEmp";                
                
                if(intCodTipDocIngImp==objZafImp.INT_COD_TIP_DOC_ING_IMP)
                    strSQL+=" 	, a3.co_exp, a3.tx_nom AS tx_nomExp, a3.tx_nom2 AS tx_nomExp2";
                else
                    strSQL+=" 	, a3.co_cli, a3.tx_ide AS tx_nomExp, a3.tx_nom AS tx_aliExp";                
                strSQL+=" FROM (  (tbm_cabMovInv AS a1 INNER JOIN tbm_emp AS a2 ON a1.co_emp=a2.co_emp)";
                strSQL+="       INNER JOIN tbm_detMovInv AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc)";
                
                if(intCodTipDocIngImp==objZafImp.INT_COD_TIP_DOC_ING_IMP)
                    strSQL+="  INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp";
                else
                    strSQL+="  INNER JOIN tbm_cli AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";                
                strSQL+=" WHERE a1.co_tipDoc in(14,245) and a1.co_mnu IN("+objZafImp.INT_COD_MNU_PRG_ING_IMP+", "+objZafImp.INT_COD_MNU_PRG_COM_LOC+")";
                strSQL+=" AND a1.st_reg='A' AND a1.st_ingImp IN('A', 'B')";
                strSQL+=" AND a1.co_emp=" + intCodEmpIngImp + " AND a1.co_loc=" + intCodLocIngImp + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocIngImp + " AND a1.co_doc=" + intCodDocIngImp + "";
                strSQL+=" GROUP BY a1.co_emp, a2.tx_nom, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" , a4.co_bod, a4.tx_nomBodOrgDes";
                
                if(intCodTipDocIngImp==objZafImp.INT_COD_TIP_DOC_ING_IMP)
                    strSQL+=" 	, a3.co_exp, a3.tx_nom, a3.tx_nom2";
                else
                    strSQL+=" 	, a3.co_cli, a3.tx_ide, a3.tx_nom";
                System.out.println("isIngresoImportacion:: " + strSQL);
                rst1=stm1.executeQuery(strSQL);
                if(rst1.next()){
//                    System.out.println("true ");
                    blnRes=true;
                }
                stm1.close();
                stm1=null;
                rst1.close();
                rst1=null;
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
     * Función permite calcular el costo total de un item
     * @return true Si se realizó completamente
     * <BR> false Caso contrario
     */
    private boolean calcularCosTot(int fila){
        boolean blnRes=true;
        BigDecimal bgdCan=BigDecimal.ZERO;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        BigDecimal bgdCosTot=BigDecimal.ZERO;
        try{
            bgdCan=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU).toString()));
            bgdCosUni=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI_ING_IMP).toString()));
            bgdCosTot=bgdCosTot.add(bgdCan.multiply(bgdCosUni));
            objTblMod.setValueAt(bgdCosTot, fila, INT_TBL_DAT_COS_TOT);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función permite calcular el costo total en el modelo de datos
     * @return true Si se realizó completamente
     * <BR> false Caso contrario
     */
    private boolean calcularCosTotGenAsiDia(){
        boolean blnRes=true;
        BigDecimal bgdCan=BigDecimal.ZERO;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        BigDecimal bgdCosTot=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()));
                bgdCosTot=objUti.redondearBigDecimal((bgdCosTot.add(bgdCan.multiply(bgdCosUni))), objParSis.getDecimalesMostrar());
                txtCosTotDoc.setText(bgdCosTot.toString());
                //objTblMod.setValueAt(bgdCosTot, i, INT_TBL_DAT_COS_TOT);
            }
//            System.out.println("bgdCosTot: " + bgdCosTot);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    /**
     * Función permite calcular el peso total en el modelo de datos
     * @return true Si se realizó completamente
     * <BR> false Caso contrario
     */
    private boolean calcularPesTot(){
        boolean blnRes=true;
        BigDecimal bgdCan=BigDecimal.ZERO;
        BigDecimal bgdPesUni=BigDecimal.ZERO;
        BigDecimal bgdPesTot=BigDecimal.ZERO;
        try{
//            System.out.println("calcularPesTot");
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdPesTot=BigDecimal.ZERO;
                bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                bgdPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI).toString()));
                bgdPesTot=bgdPesTot.add(bgdCan.multiply(bgdPesUni));
//                System.out.println("bgdCan: " + bgdCan);
//                System.out.println("bgdPesUni: " + bgdPesUni);
                //System.out.println("bgdPesTot: " + bgdPesTot);
                objTblMod.setValueAt(bgdPesTot, i, INT_TBL_DAT_PES_TOT);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función permite calcular el peso total en el modelo de datos
     * @return true Si se realizó completamente
     * <BR> false Caso contrario
     */
    private boolean calcularPesTotDoc(){
        boolean blnRes=true;
        BigDecimal bdgPesUni=BigDecimal.ZERO;
        BigDecimal bdgPesTot=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdgPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT).toString()));
                bdgPesTot=bdgPesTot.add(bdgPesUni);
            }
            txtPesDoc.setText(""+objUti.redondearBigDecimal(bdgPesTot, objParSis.getDecimalesMostrar()));
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que permite conocer si el usuario puede dar visto bueno a ese tipo de documento.
     * Se arma un ArrayList con datos del usuario y tipo de documento de ese visto bueno
     * @return
     */
    private boolean isUsrPerModVisBueUsrTipDoc(char almacenaArreglo, int codigoUsuario){
        boolean blnRes=false;
        try{
            if(conRef!=null){
                stm=conRef.createStatement();
                //--Código de Vistos Buenos por usuario y si necesitan autorización previa
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue, a1.co_usr, a1.st_necVisBuePre, a1.nd_valAut";
                strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" WHERE a1.st_reg='A' ";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a1.co_usr=" + codigoUsuario + "";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                System.out.println("isUsrPerModVisBueUsrTipDoc: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatVisBueUsrTipDoc.clear();
                if(rst.next()){
                    blnRes=true;
                    if(almacenaArreglo=='S'){
                        ///llenado del arreglo que contiene los datos del visto bueno por usuario
                        arlRegVisBueUsrTipDoc=new ArrayList();
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_EMP, rst.getInt("co_emp"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_LOC, rst.getInt("co_loc"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_TIP_DOC, rst.getInt("co_tipDoc"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_VIS_BUE, rst.getInt("co_visBue"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_USR, rst.getInt("co_usr"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE, rst.getString("st_necVisBuePre"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE, rst.getBigDecimal("nd_valAut"));
                        arlDatVisBueUsrTipDoc.add(arlRegVisBueUsrTipDoc);
                    }
                }
                //System.out.println("*arlDatVisBueUsrTipDoc: " + arlDatVisBueUsrTipDoc.toString());
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
     * Función que permite conocer si el usuario ha concedido el visto bueno
     * @return
     */
    private boolean isVisBueMovInv(int codigoVistoBueno){
        boolean blnRes=false;
        try{
            if(conRef!=null){
                stm=conRef.createStatement();
                //--Código de Vistos Buenos por usuario y si necesitan autorización previa
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_visbue, a1.co_usr, a1.st_reg";
                strSQL+=" FROM tbm_visbuemovinv AS a1";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" AND a1.co_emp=" + intCodEmpAju + "";
                strSQL+=" AND a1.co_loc=" + intCodLocAju + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocAju + "";
                strSQL+=" AND a1.co_doc=" + intCodDocAju + "";
                strSQL+=" AND a1.co_visBue=" + codigoVistoBueno + "";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                System.out.println("isVisBueMovInv: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatVisBueUsrTipDoc.clear();
                if(rst.next()){
                    blnRes=true;
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
     * Función que permite conocer si el usuario puede modificar el documento
     * @return true: Si se puede modificar el documento
     * <BR> false: Caso contrario
     */
    private boolean isValUsrModDoc(){
        boolean blnRes=false;
        boolean isNecVisBuePre;
        BigDecimal bgdValAut=BigDecimal.ZERO;
        int intCodVisBue=-1;
        try{
            if(conRef!=null){
                if(isUsrPerModVisBueUsrTipDoc('S', objParSis.getCodigoUsuario())) //el usuario si puede dar visto bueno (S-> Indica que llena el arreglo)
                {
                    blnRes=true;
                    System.out.println("arlDatVisBueUsrTipDoc: " + arlDatVisBueUsrTipDoc.toString());
                    for(int i=0; i<arlDatVisBueUsrTipDoc.size(); i++){ //El arreglo contiene solo el dato asociado al usuario
                        intCodVisBue=objUti.getIntValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_COD_VIS_BUE);
                        isNecVisBuePre=(objUti.getObjectValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE)==null?false:objUti.getStringValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE)).toString().equals("S")?true:false;
                        bgdValAut=new BigDecimal(objUti.getObjectValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE)==null?"0":(objUti.getStringValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE).equals("")?"0":objUti.getStringValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE)));
                        System.out.println("intCodVisBue: "+intCodVisBue+" <> isNecVisBuePre: " + isNecVisBuePre+" <> bgdValAut: " + bgdValAut);

                        if(isNecVisBuePre){ //SI necesita visto bueno previo
                            if(isVisBueMovInv(intCodVisBue-1)){ //Ya fue concedido el visto bueno previo
                                if(!isValPerVisBueUsrTipDoc(intCodVisBue)) //puede modificar ?  segun valor
                                    blnRes=false;
                            }
                            else 
                                blnRes=false; //NO ha sido concedido el visto bueno previo
                        }
                        else{ //NO necesita visto bueno previo
                            if(!isValPerVisBueUsrTipDoc(intCodVisBue)) //puede modificar ?  segun valor
                                blnRes=false;
                        }
                    }
                }
            }

            isModDoc=blnRes;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

   /**
    * Función que permite saber si el valor del documento puede ser autorizado por el usuario
    * @return true Si se puede proceder con el visto bueno
    * <BR> false: Caso contrario
    */
   private boolean isValPerVisBueUsrTipDoc( int codigoVistoBueno){
        boolean blnRes=false;
        Statement stmValVisBue;
        ResultSet rstValVisBue;
        BigDecimal bgdValDoc=BigDecimal.ZERO;
        try{
            if(conRef!=null){
                bgdValDoc=new BigDecimal(txtValDoc.getText().length()>0?"0":txtValDoc.getText()).abs();
                stmValVisBue=conRef.createStatement();
                //Numero de Vistos Buenos
                strSQL="";
                strSQL+=" SELECT x.* ";
                strSQL+=" FROM (";
                strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                strSQL+="             , CASE WHEN a1.nd_valAut IS NULL THEN 0 ELSE a1.nd_valAut END";
                strSQL+="        FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+="        WHERE a1.st_reg='A'";
                strSQL+="        AND a1.co_emp=" + objParSis.getCodigoEmpresa() +"";
                strSQL+="        AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="        AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="        AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+="        AND a1.co_visBue=" + codigoVistoBueno + "";
                strSQL+=" ) AS x ";
                //Valida que el valor autorizado por el usuario sea mayor 0 igual que el valor del documento.
                strSQL+=" WHERE (CASE WHEN x.nd_ValAut>0 THEN (CASE WHEN "+bgdValDoc+" <=x.nd_ValAut THEN TRUE ELSE FALSE END) ELSE TRUE END  )";
                strSQL+=" ORDER BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_visBue";
                System.out.println("isValPerVisBueUsrTipDoc: " + strSQL);
                rstValVisBue=stmValVisBue.executeQuery(strSQL);
                if(rstValVisBue.next()){
                    blnRes=true;//si retorna algo, el usuario si puede dar VB a ese valor
                }
                stmValVisBue.close();
                stmValVisBue=null;
                rstValVisBue.close();
                rstValVisBue=null;
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
     * Función que permite determinar si existe código de seguimiento asociado al Ingreso de Importación
     * @return true Si existe seguimiento para la instancia anterior del Ajuste
     * <BR> false Caso contrario
     */
    private boolean getCodSegIngImp(){
        boolean blnRes=false;
        try{
            if(conRef!=null){
                stm=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT a3.co_seg AS co_segIngImp, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" , a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
                strSQL+=" FROM tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+=" AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpIngImp + "";
                strSQL+=" AND a1.co_loc=" + intCodLocIngImp + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocIngImp + "";
                strSQL+=" AND a1.co_doc=" + intCodDocIngImp + "";
//                System.out.println("getCodSegIngImp: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegInsAnt=rst.getObject("co_segIngImp");
                    blnRes=true;
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
//            System.out.println("Error - getCodSegIngImp SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
//            System.out.println("Error - getCodSegIngImp: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean isAjuPro() {
        return isAjuPro;
    }


    private boolean isCreInsVenItm(){
        boolean blnRes=true;
        try{
            objAjuInv_01=new Librerias.ZafImp.ZafAjuInv_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, conRef);
            objAjuInv_01.setVisible(true);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean afterAceCreItm(int intFilSel){
        boolean blnRes=true;
        int intCodEmp=-1;
        int intCodItm=-1;
        int intCodItmMae=-1;
        String strCodItmAlt="", strCodItmLet="", strNomItm="", strUniMed="";
        try{
            if(objAjuInv_01.getBotonPresionado()==0){//no se ha presionado ningun boton, este caso se va a dar cuando se consulte una nota de embarque, solo en ese caso no se ha presionado ese boton
                System.out.println("Cero");
            }
            if(objAjuInv_01.getBotonPresionado()==1){//se presiono el boton Guardar
//                System.out.println("Guardar");
//                System.out.println("getDatItm: " + objAjuInv_01.getDatItm());
                strSQLInsItm=objAjuInv_01.getSQLInsItm();
                strSQLInsTodItmAgr=strSQLInsTodItmAgr + strSQLInsItm;
//                System.out.println("**strSQLInsTodItmAgr: " + strSQLInsTodItmAgr);

                if(objAjuInv_01.getDatItm().size()>0){
                    for(int i=0; i<objAjuInv_01.getDatItm().size(); i++){
                        intCodEmp=objUti.getIntValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_COD_EMP);
//                        System.out.println("intCodEmp: " + intCodEmp);

                        if(intCodEmp==objParSis.getCodigoEmpresaGrupo()){//el registro es el registro que contiene los datos del grupo
                            intCodItm=objUti.getIntValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_GRP);
                            objTblMod.setValueAt(intCodItm, intFilSel, INT_TBL_DAT_COD_ITM_GRP);
//                            System.out.println("GRUPO: " + intCodEmp);
                        }
                        if(intCodEmp==intCodEmpAju){//el registro es el registro que contiene los datos de la empresa
//                            System.out.println("EMPRESA: " + intCodEmp);

                            //me esta cargando el item anterior(si existe ) y no el que estoy creando
                            intCodItm=objUti.getIntValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_EMP);
                            intCodItmMae=objUti.getIntValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_MAE);
                            strCodItmAlt=objUti.getStringValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_COD_ALT_ITM);
                            strCodItmLet=objUti.getStringValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_LET);
                            strNomItm=objUti.getStringValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_NOM_ITM);
                            strUniMed=objUti.getStringValueAt(objAjuInv_01.getDatItm(), i, INT_ARL_ITM_UNI_MED);

//                            System.out.println("intCodItm: " + intCodItm);
//                            System.out.println("intCodItmMae: " + intCodItmMae);
//                            System.out.println("strCodItmAlt: " + strCodItmAlt);
//                            System.out.println("strCodItmLet: " + strCodItmLet);
//                            System.out.println("strNomItm: " + strNomItm);
//                            System.out.println("strUniMed: " + strUniMed);
//                            System.out.println("intFilSel: " + intFilSel);

                            objTblMod.setValueAt(intCodItm, intFilSel, INT_TBL_DAT_COD_ITM_EMP);
                            objTblMod.setValueAt(intCodItmMae, intFilSel, INT_TBL_DAT_COD_ITM_MAE);
                            objTblMod.setValueAt(strCodItmAlt, intFilSel, INT_TBL_DAT_COD_ALT_ITM);
                            objTblMod.setValueAt(strCodItmLet, intFilSel, INT_TBL_DAT_COD_ITM_LET);
                            objTblMod.setValueAt(strNomItm, intFilSel, INT_TBL_DAT_NOM_ITM);
                            objTblMod.setValueAt(strUniMed, intFilSel, INT_TBL_DAT_DES_COR_UNI_MED);
                        }
                    }
                }
            }
            if(objAjuInv_01.getBotonPresionado()==2){//no se ha presionado ningun boton, este caso se va a dar cuando se consulte una nota de embarque, solo en ese caso no se ha presionado ese boton
                System.out.println("Dos");
            }
        }
        catch(Exception e){
//            System.out.println("Error - isCreInsVenItm: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean isMsgCreItm(){
        boolean blnRes=false;
        String strTit="";
        String strMsg="";
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="Desea crear el item de la fila seleccionada?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                blnRes=true;
                isCreInsVenItm();
            }            
        }
        catch(Exception e){
//            System.out.println("Error - getCodSegIngImp: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            if (conRef!=null){
                if(setAsiEstDocAju()){
                    if(insertarInventario()){
                        if(actualizarConInv()){
                            if(actualizarCabMovInv()){
                                if(objZafImp.setCampoTabla(conRef, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, "tbm_cabMovInv", "st_aut", "'" + strEstAutDenVisBue + "'")){
                                    if(objZafImp.setCampoTabla(conRef, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, "tbm_cabMovInv", "st_ingImp", "'" + strEstIngImpDocAju + "'")){
                                        if (intButSelDlg==INT_BUT_ACE)  //Autorizar  
                                        {  
                                            //Actualiza la fecha del documento en el Ajuste
                                            if(objZafImp.setCampoTabla(conRef, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, "tbm_cabMovInv", "fe_doc", "'" + objUti.formatearFecha(getFecHoy(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'") ) {
                                                if(objZafImp.setCampoTabla(conRef, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, "tbm_cabMovInv", "st_reg", "'" + strDocActivo + "'")){//actualiza el estado del documento en el Ajuste
                                                    if(insertar_tbmDetMovInv(conRef)){
                                                        if(setEstEliItmSob()){
                                                            if(insertar_detDisItm(conRef)){
                                                                if(insertar_tbmVisBueMovInv()){
                                                                    if(calcularCosTotGenAsiDia()){
                                                                        if(generaDiarioAjuste()){
                                                                            if(eliminar_tbmDetMovInv()){
                                                                                if(objZafImp.setCampoTabla(conRef, "co_emp", "co_loc", "co_tipDoc", "co_dia", intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, "tbm_cabDia", "fe_Dia", "'" + objUti.formatearFecha(getFecHoy(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'")){
                                                                                    if(objZafImp.setCampoTabla(conRef, "co_emp", "co_loc", "co_tipDoc", "co_dia", intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, "tbm_cabDia", "st_reg", "'" + strDocActivo + "'")){
                                                                                        //if(costearItm(conRef)){
                                                                                            blnRes=true;
                                                                                        //}
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }                                            
                                        else if(intButSelDlg==INT_BUT_DEN) //Denegar
                                        {   
                                            if(objZafImp.setCampoTabla(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "st_ingImp", "'" + strEstIngImpDocIngImp + "'")){
                                                blnRes=true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
    private boolean insertarInventario(){
        boolean blnRes=true;
        try{
            if (conRef!=null){
                if(strSQLInsTodItmAgr.equals("")){
                    stm=conRef.createStatement();
//                    System.out.println("insertarInventario: " + strSQLInsTodItmAgr);
                    stm.executeUpdate(strSQLInsItm);
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
    private boolean actualizarCabMovInv(){
        boolean blnRes=true;
        try{
            if (conRef!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                getSecuencias();

                stm=conRef.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabMovInv";
                strSQL+=" SET ne_secGrp=" + intSecGrp;      
                strSQL+=" , ne_secEmp=" + intSecEmp;                 
                strSQL+=" , nd_sub=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=" , nd_tot=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=" , nd_pesTotKgr=" + objUti.codificar(txtPesDoc.getText()) + "";
                strSQL+=" , tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=" , tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strSQL+=" , fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" , tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + intCodEmpAju + "";
                strSQL+=" AND co_loc=" + intCodLocAju + "";
                strSQL+=" AND co_tipDoc=" + intCodTipDocAju + "";
                strSQL+=" AND co_doc=" + intCodDocAju + "";
                strSQL+=";";
                System.out.println("actualizarCabMovInv: " + strSQL);
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

    private boolean setAsiEstDocAju(){
        boolean blnRes=false;
        int intNumVisBueTipDoc=-1, intNumVisBueOblTipDoc=-1, intNumVisBueAutUsr=-1;
        BigDecimal bgdValDocFrm=new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText()));
        BigDecimal bgdValAutUsr=BigDecimal.ZERO; Object objValAutUsr=null;
        int intCodEmpPar=-1;
        int intCodLocPar=-1;
        int intCodTipDocPar=-1;
        int intCodDocPar=-1;
        boolean blnAjuFalCom=false;
        try{
            if(conRef!=null){
                if(chrTipOpe=='n')  /* Insertar */
                {
                    //PK: Ingreso por Importación.
                    intCodEmpPar=intCodEmpIngImp; //co_emp
                    intCodLocPar=intCodLocIngImp; //co_loc
                    intCodTipDocPar=Integer.parseInt(txtCodTipDoc.getText()); //co_tipdoc
                    intCodDocPar=Integer.parseInt(txtCodDoc.getText());       //co_doc
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    strDocActivo="O";
                    strEstIngImpDocAju="U";
                    strEstAutDenVisBue="P";
                    blnRes=true;
                }
                else if((chrTipOpe=='x') || (chrTipOpe=='m') )   /* Modificar */
                {
                    //PK: Documento de Ajuste.
                    intCodEmpPar=intCodEmpAju;       //co_emp
                    intCodLocPar=intCodLocAju;       //co_loc
                    intCodTipDocPar=intCodTipDocAju; //co_tipdoc
                    intCodDocPar=intCodDocAju;       //co_doc
                    
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    /* Verifica si existe un faltante dentro del ajuste. */
                    blnAjuFalCom = objZafImp.isAjusteFaltanteCompleto(conRef, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp)?true:false; 
                    
                    intNumVisBueTipDoc=objZafImp.getNumVisBueTipDoc(conRef, intCodEmpPar, intCodLocPar, intCodTipDocPar);  //Numero de vistos buenos: Tipo de documento.
                    intNumVisBueOblTipDoc=objZafImp.getNumVisBueObl(conRef, intCodEmpPar, intCodLocPar, intCodTipDocPar);  //Numero de vistos buenos obligatorios.
                    intNumVisBueAutUsr=objZafImp.getNumVisBueAutUsr(conRef, intCodEmpPar, intCodLocPar, intCodTipDocPar, intCodDocPar); //Números de vistos buenos autorizados por usuario.
                    //System.out.println("intNumVisBueTipDoc: " + intNumVisBueTipDoc +" / intNumVisBueOblTipDoc:"+intNumVisBueOblTipDoc+" / intNumVisBueAutUsr:"+intNumVisBueAutUsr);
                    
                    /* El usuario tiene un valor minimo para autorizar ajustes. Es decir, puede autorizar documentos que tengan hasta el valor del documento permitido. */
                    objValAutUsr=objZafImp.getValAutPorUsr(conRef, intCodEmpPar, intCodLocPar, intCodTipDocPar, intCodDocPar, objParSis.getCodigoUsuario());
                    bgdValAutUsr=new BigDecimal(objValAutUsr==null?"0":(objValAutUsr.toString().equals("")?"0":objValAutUsr.toString()));
                    //System.out.println("objValAutUsr: "+objValAutUsr+" / bgdValAutUsr: "+bgdValAutUsr);                    
                    
                    if(objValAutUsr!=null) /* Indica que el usuario SI tiene configuración en vistos buenos para autorizar documento */
                    { 
                        if(intNumVisBueTipDoc>=intNumVisBueOblTipDoc)  /* */
                        {  
                            /* Valida que los vistos buenos obligatorios sean los mismos que han sido autorizados por los usuarios. */
                            if( (intNumVisBueOblTipDoc<=intNumVisBueAutUsr) ) //Tiene los vistos buenos necesarios para el tipo de documento
                            {
                                if(bgdValAutUsr.compareTo(BigDecimal.ZERO)>0)  //El usuario tiene un valor minimo para autorizar.
                                {
                                    /* Valida que el valor del documento sea menor o igual al valor permitido por el usuario.*/
                                    if( (bgdValDocFrm.abs()).compareTo(bgdValAutUsr)<=0 ) //Este Visto Bueno es suficiente para autorización.
                                    { 
                                        strDocActivo="A";
                                        strEstIngImpDocAju = blnAjuFalCom? "C":"T"; //Cuando el ajuste solo genera faltantes el proceso debe cambiar a C=Completo.
                                        intInsVisBue=1;
                                        setEstAutDenVisBue(intInsVisBue);                                    
                                    }
                                    else{ //Necesita un Visto Bueno adicional para autorización.
                                        strDocActivo="O";
                                        strEstIngImpDocAju="U";
                                        intInsVisBue=0;
                                        setEstAutDenVisBue(intInsVisBue);                                
                                    }   
                                    blnRes=true;
                                }
                                else{  // No existe un minimo de valor a autorizar para el usuario. Es decir, puede autorizar cualquier valor de documento.
                                    strDocActivo="A";
                                    strEstIngImpDocAju = blnAjuFalCom? "C":"T"; //Cuando el ajuste solo genera faltantes el proceso debe cambiar a C=Completo.
                                    intInsVisBue=1;
                                    setEstAutDenVisBue(intInsVisBue);    
                                    blnRes=true;
                                }
                            }
                            else{  //Cuando están pendientes vistos buenos de los obligatorios.
                                strDocActivo="O";
                                strEstIngImpDocAju="U";
                                intInsVisBue=0;
                                setEstAutDenVisBue(intInsVisBue);
                                blnRes=true;
                            }
                        }
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

    private boolean setTipMovAjuLet(){
        boolean blnRes=true;
        BigDecimal bgdValDoc=BigDecimal.ZERO;
        BigDecimal bgdCan=BigDecimal.ZERO;
        try{
//            if(txtValDoc.getText().length()>0)
//                bgdValDoc=new BigDecimal(txtValDoc.getText().equals("")?"0":txtValDoc.getText());

//            if(bgdValDoc.compareTo(new BigDecimal("500"))>0){
//                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//                    bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
//                    if(bgdCan.compareTo(BigDecimal.ZERO)>0){
//                        objTblMod.setSelectedItem("S", i, INT_TBL_DAT_TIP_MOV_LET);
//                    }
//                    else if(bgdCan.compareTo(BigDecimal.ZERO)<0){
//                        objTblMod.setSelectedItem("R", i, INT_TBL_DAT_TIP_MOV_LET);
//                    }
//                }
//            }
//            else{
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    objTblMod.setSelectedItem("E", i, INT_TBL_DAT_TIP_MOV_LET);
                }
//            }
            calcularCosTotGenAsiDia();
//            generaDiarioAjuste();

        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean setEstFilMod(){
        boolean blnRes=true;
        int intCodItmGrp=-1;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intCodItmGrp=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP)==null?"-1":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString().equals("")?"-1":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString()));
                if(intCodItmGrp==-1)
                    objTblMod.setValueAt("I", i, INT_TBL_DAT_LIN);
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean setTipAjuLar(){
        boolean blnRes=true;
        String strTipAjuLet="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipAjuLet=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET).toString();
                if(strTipAjuLet.equals("E"))
                    objTblMod.setSelectedItem("E: Empresa", i, INT_TBL_DAT_TIP_MOV_DES);
                else if(strTipAjuLet.equals("T"))
                    objTblMod.setSelectedItem("T: Reclamos a Terceros", i, INT_TBL_DAT_TIP_MOV_DES);
                else if(strTipAjuLet.equals("P"))
                    objTblMod.setSelectedItem("P: Reclamos a Proveedores", i, INT_TBL_DAT_TIP_MOV_DES);
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean consultar() {
        System.out.println("**consultar**");
        consultarReg();
        return true;
    }

    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            if (conRef!=null){
                stmCab=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+="      , a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng, a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a5.co_mnu";
                strSQL+="      , a17.co_empRel, a17.co_locRel, a17.co_tipDocRel, a17.co_docRel";
                strSQL+="      , d1.co_emp AS co_empOrdCon, d1.co_loc AS co_locOrdCon, d1.co_tipDoc AS co_tipDocOrdCon, d1.co_doc AS co_docOrdCon";
                strSQL+=" FROM ( ";
                strSQL+="        ( ( ( tbm_cabMovInv AS a1 ";
                strSQL+="              INNER JOIN (tbr_cabMovInv AS a17 INNER JOIN tbm_cabOrdConInv AS d1 ON a17.co_empRel=d1.co_empRel AND a17.co_locRel=d1.co_locRel AND a17.co_tipDocRel=d1.co_tipDocRel AND a17.co_docRel=d1.co_docRel )";
                strSQL+="              ON a1.co_emp=a17.co_emp AND a1.co_loc=a17.co_loc AND a1.co_tipDoc=a17.co_tipDoc AND a1.co_doc=a17.co_doc";
                strSQL+="             )INNER JOIN tbm_detMovInv AS a6";
                strSQL+=" 	       ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+=" 	    )";
                strSQL+=" 	    LEFT OUTER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
                strSQL+="	    LEFT OUTER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                if(objParSis.getCodigoUsuario()==1){
                    strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc AND a5.co_mnu=" + objParSis.getCodigoMenu() + ") ";
                }
                else{
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc AND a5.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.co_usr=" + objParSis.getCodigoUsuario() + ")";
                }
                strSQL+=" WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";

                strSQL+=" AND a1.co_emp=" + intCodEmpAju + "";
                strSQL+=" AND a1.co_loc=" + intCodLocAju + "";

                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";

                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";

                //se selecciono un pedido especifico
//                if(intCboPedSelCodEmp!=0){
//                    strSQL+=" AND a17.co_empRel IN(" + intCboPedSelCodEmp + ")";//se filtra la empresa
//                    strSQL+=" AND a17.co_locRel IN(" + intCboPedSelCodLoc + ")";//se filtra el local
//                    strSQL+=" AND a17.co_tipDocRel IN(" + intCboPedSelCodTipDoc + ")";//se filtra el tipo de documento
//                    strSQL+=" AND a17.co_docRel IN(" + intCboPedSelCodDoc + ")";//se filtra el documento
//                }

                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E' AND a1.co_tipDoc<>a17.co_tipDocRel";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+="        , a1.co_usrIng, a15.tx_usr, a1.co_usrMod, a16.tx_usr, a5.co_mnu";
                strSQL+="        , a17.co_empRel, a17.co_locRel, a17.co_tipDocRel, a17.co_docRel";
                strSQL+="        , d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc";
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg:  " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next()){
                    intCodEmpOrdCon=rstCab.getInt("co_empOrdCon");
                    intCodLocOrdCon=rstCab.getInt("co_locOrdCon");
                    intCodTipDocOrdCon=rstCab.getInt("co_tipDocOrdCon");
                    intCodDocOrdCon=rstCab.getInt("co_docOrdCon");
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
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
     * Esta funcián permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    if(objAsiDia.consultarDiario(intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju)){
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
     * Esta funcián permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            if (conRef!=null){
                txtCodImp.setText(""+intCodEmpAju);

                stm=conRef.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+="      , a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.tx_desCor";
                strSQL+="      , a1.tx_desLar, a1.tx_natDoc, a2.co_bodIng, a2.tx_nomBodIng, a3.co_bodEgr, a3.tx_nomBodEgr, a1.nd_tot";
                strSQL+="      , CASE WHEN a2.co_bodIng IS NULL THEN a3.co_bodEgr ELSE a2.co_bodIng END AS co_bodEmp";
                strSQL+="      , CASE WHEN a2.tx_nomBodIng IS NULL THEN a3.tx_nomBodEgr ELSE a2.tx_nomBodIng END AS tx_nomBodEmp";
                strSQL+="      , a1.co_docIngImp, a1.ne_numDocIngImp, a1.tx_numIngImp, a1.nd_pesTotKgrIngImp";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" 	     , a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a2.tx_desCor";
                strSQL+=" 	     , a2.tx_desLar, a2.tx_natDoc, a10.co_doc AS co_docIngImp, a10.ne_numDoc AS ne_numDocIngImp, a10.tx_numDoc2 AS tx_numIngImp, a10.nd_pesTotKgr AS nd_pesTotKgrIngImp";
                strSQL+=" 	FROM ( (tbm_cabMovInv AS a1 ";
                strSQL+="               LEFT OUTER JOIN (tbr_cabMovInv AS a9 INNER JOIN tbm_cabMovInv AS a10 ON a9.co_empRel=a10.co_emp AND a9.co_locRel=a10.co_loc AND a9.co_tipDocRel=a10.co_tipDoc AND a9.co_docRel=a10.co_doc)";
                strSQL+=" 	        ON a1.co_emp=a9.co_emp AND a1.co_loc=a9.co_loc AND a1.co_tipDoc=a9.co_tipDoc AND a1.co_doc=a9.co_doc";
                strSQL+=" 	       )";
                strSQL+=" 	       INNER JOIN ( tbm_detMovInv AS a6 INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
                strSQL+=" 			    INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
                strSQL+=" 	       )";
                strSQL+=" 	       ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+=" 	)";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                strSQL+="       WHERE a1.co_emp=" + intCodEmpAju;
                strSQL+="       AND a1.co_loc=" + intCodLocAju;
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDocAju;
                strSQL+="       AND a1.co_doc=" + intCodDocAju;
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" 	       , a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+=" 	       , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a10.co_doc, a10.ne_numDoc, a10.tx_numDoc2, a10.nd_pesTotKgr";
                strSQL+=" ) AS a1";
                
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_bod AS co_bodIng, a2.tx_nom AS tx_nomBodIng";
                strSQL+=" 	FROM tbm_detMovInv AS a1 INNER JOIN tbm_bod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+="       WHERE a1.co_emp=" + intCodEmpAju;
                strSQL+="       AND a1.co_loc=" + intCodLocAju;
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDocAju;
                strSQL+="       AND a1.co_doc=" + intCodDocAju;
                strSQL+=" 	AND a1.nd_can>0";
                strSQL+=") AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_bod AS co_bodEgr, a2.tx_nom AS tx_nomBodEgr";
                strSQL+=" 	FROM tbm_detMovInv AS a1 INNER JOIN tbm_bod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+="       WHERE a1.co_emp=" + intCodEmpAju;
                strSQL+="       AND a1.co_loc=" + intCodLocAju;
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDocAju;
                strSQL+="       AND a1.co_doc=" + intCodDocAju;
                strSQL+=" 	AND a1.nd_can<0";
                strSQL+=" ) AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2 , a1.nd_tot, a1.nd_pestotkgr";
                strSQL+="        , a1.st_imp, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.tx_desCor , a1.tx_desLar, a1.tx_natDoc, a2.co_bodIng, a2.tx_nomBodIng";
                strSQL+="        , a3.co_bodEgr, a3.tx_nomBodEgr, a1.nd_tot , a1.co_docIngImp, a1.ne_numDocIngImp, a1.tx_numIngImp, a1.nd_pesTotKgrIngImp ";
                System.out.println("cargarCabReg: " + strSQL);
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
                    strAux=rst.getString("tx_obs2");

                    strAux=rst.getString("co_docIngImp");
                    txtCodIngImp.setText(strAux);

                    strAux=rst.getString("ne_numDocIngImp");
                    txtNumDocIngImp.setText(strAux);

                    strAux=rst.getString("tx_numIngImp");
                    txtNumPedIngImp.setText(strAux);

                    strAux=rst.getString("co_bodEmp");
                    txtCodBodEmp.setText(strAux);
                    strAux=rst.getString("tx_nomBodEmp");
                    txtNomBodEmp.setText(strAux);
//                    strAux=rst.getString("co_bodEgr");
//                    strAux=rst.getString("tx_nomBodEgr");

                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    strAux=rst.getObject("nd_pestotkgr")==null?"0":(rst.getString("nd_pestotkgr").equals("")?"0":rst.getString("nd_pestotkgr"));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("nd_pesTotKgrIngImp");
                    txtPesDoc.setText(strAux);

                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                }
                else{
                    limpiarFrm();
                    blnRes=false;
                }

            }
            rst.close();
            stm.close();
            rst=null;
            stm=null;
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
     * Esta funcián permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){//por consulta
        boolean blnRes=true;
        String strCodItmGrp="";
        try{
            objTblMod.removeAllRows();
            if (conRef!=null){
                stm=conRef.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.tx_codAlt";
                strSQL+="      , b1.tx_codAlt2, b1.tx_nomItm, b1.tx_uniMed, b1.nd_can";
                strSQL+="      , b1.nd_cosUni, b1.nd_preUni, b1.nd_tot, b1.nd_cosUniGrp, b1.nd_cosTotGrp";
                strSQL+="      , b1.nd_pesitmkgr, b1.nd_pesItmKgrTot, b1.co_itmMae, b2.co_itmGrp, b1.st_tipAju";
                strSQL+=" FROM(";
                strSQL+=" 	 SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_itm, a2.tx_codAlt";
                strSQL+=" 	      , a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, (a2.nd_can) AS nd_can";
                strSQL+=" 	      , (a2.nd_cosUni) AS nd_cosUni, a2.nd_preUni, a2.nd_tot, a2.nd_cosUniGrp, ABS(a2.nd_cosTotGrp) AS nd_cosTotGrp";
                strSQL+=" 	      , a3.nd_pesitmkgr, (ABS(a2.nd_can)*a3.nd_pesitmkgr) AS nd_pesItmKgrTot";
                strSQL+=" 	      , a4.co_itmMae, a2.st_tipAju";
                strSQL+=" 	 FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 	 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	 LEFT OUTER JOIN (tbm_inv AS a3 INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
                strSQL+=" 	 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 	 WHERE a1.co_emp=" + intCodEmpAju + "";
                strSQL+="        AND a1.co_loc=" + intCodLocAju + "";
                strSQL+="        AND a1.co_tipDoc=" + intCodTipDocAju + "";
                strSQL+="        AND a1.co_doc=" + intCodDocAju + "";
                strSQL+="        AND (a2.st_reg='A' OR a2.st_reg IS NULL)";
                strSQL+=" ) AS b1";
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+=" 	SELECT a1.co_itm AS co_itmGrp, a1.co_itmMae";
                strSQL+=" 	FROM tbm_equInv AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_itmMae=b2.co_itmMae";
                strSQL+=" ORDER BY b1.co_reg";
                System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,                  "");
                    vecReg.add(INT_TBL_DAT_CHK,                  new Boolean(true));
                    vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,      "" + rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,      "" + rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP,  "" + rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,      "" + rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,      "" + rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_GRP,          rst.getObject("co_itmGrp"));//
                    vecReg.add(INT_TBL_DAT_COD_ITM_EMP,          "" + rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,          rst.getObject("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,          "" + (rst.getObject("tx_codAlt")==null?"":rst.getString("tx_codAlt")));
                    vecReg.add(INT_TBL_DAT_COD_ITM_LET,          "" + (rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2")));
                    vecReg.add(INT_TBL_DAT_BUT_ITM,              null);
                    vecReg.add(INT_TBL_DAT_NOM_ITM,              "" + rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,      "" + (rst.getObject("tx_uniMed")==null?"":rst.getString("tx_uniMed")));
                    vecReg.add(INT_TBL_DAT_TIP_MOV_DES,          "");
                    vecReg.add(INT_TBL_DAT_TIP_MOV_LET,          "" + rst.getString("st_tipAju"));
                    vecReg.add(INT_TBL_DAT_CAN_AJU,              "" + rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_COS_UNI_ING_IMP,      "" + rst.getString("nd_cosUni"));
                    vecReg.add(INT_TBL_DAT_COS_TOT,              "" + rst.getString("nd_tot"));
                    vecReg.add(INT_TBL_DAT_PES_UNI,              "" + (rst.getObject("nd_pesitmkgr")==null?"":rst.getString("nd_pesitmkgr")));
                    vecReg.add(INT_TBL_DAT_PES_TOT,              "" + (rst.getObject("nd_pesItmKgrTot")==null?"":rst.getString("nd_pesItmKgrTot")));
                    vecReg.add(INT_TBL_DAT_CAN_ING_IMP,          "");
                    vecReg.add(INT_TBL_DAT_CAN_CON,              "");
                    vecReg.add(INT_TBL_DAT_CAN_TRS,              "");
                    vecReg.add(INT_TBL_DAT_EST_EXI_ITM,          "");
                    vecReg.add(INT_TBL_DAT_CAN_ING_EGR,          null);
                    vecDat.add(vecReg);
                }

                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                setTipAjuLar();
                objTblMod.initRowsState();
                setEstFilMod();
                calcularPesTotDoc();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
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

    public boolean setDatAju(Connection conexion, int codigoEmpresaIngImp, int codigoLocalIngImp, int codigoTipoDocIngImp, int codigoDocumentoIngImp
                           , int codigoEmpresaAju, int codigoLocalAju, int codigoTipoDocAju, int codigoDocumentoAju, int codigoVistoBuenoUsuario){
        boolean blnRes=true;
        try{
            conRef=conexion;

            intCodEmpIngImp=codigoEmpresaIngImp;
            intCodLocIngImp=codigoLocalIngImp;
            intCodTipDocIngImp=codigoTipoDocIngImp;
            intCodDocIngImp=codigoDocumentoIngImp;

            intCodEmpAju=codigoEmpresaAju;
            intCodLocAju=codigoLocalAju;
            intCodTipDocAju=codigoTipoDocAju;
            intCodDocAju=codigoDocumentoAju;

            strSQLInsTodItmAgr="";

            txtCodTipDoc.setText(""+codigoTipoDocAju);
            txtCodDoc.setText(""+codigoDocumentoAju);
            intCodVisBueUsr=codigoVistoBuenoUsuario;
            
            //System.out.println("PK INGRESO POR IMPORTACION>> codigoEmpresaIngImp: "+codigoEmpresaIngImp+" - codigoLocalIngImp: "+codigoLocalIngImp+" - codigoTipoDocIngImp: "+codigoTipoDocIngImp+" - codigoDocumentoIngImp: "+codigoDocumentoIngImp);
            //System.out.println("PK AJUSTE>> codigoEmpresaAju: "+codigoEmpresaAju+" - codigoLocalAju: "+codigoLocalAju+" - codigoTipoDocAju: "+codigoTipoDocAju+" - codigoDocumentoAju: "+codigoDocumentoAju);
            //System.out.println("codigoVistoBuenoUsuario: "+codigoVistoBuenoUsuario);
            
            consultar(); //Consultar
            clickModificar(); //Modificar: Después de haber realizado los cambis en el modelo
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar (INT_BUT_DEN).
     * <LI>1: Click en el botón Aceptar (INT_BUT_ACE).
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getSelectedButton()
    {
        return intButSelDlg;
    }

    /**
     * Esta función obtiene las filas que han sido seleccionadas por el usuario en el JTable.
     * @return Un arreglo de enteros que contiene la(s) fila(s) seleccionadas.
     */
    public int[] getFilasSeleccionadas(){
        int i=0, j=0;
        int intRes[];
        for (i=0; i<objTblMod.getRowCountTrue(); i++){
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                j++;
            }
        }
        intRes=new int[j];
        j=0;
        for (i=0; i<objTblMod.getRowCountTrue(); i++){
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                intRes[j]=i;
                j++;
            }
        }
        return intRes;
    }

    /**
     * Esta función obtiene el valor que se encuentra en la posición especificada.
     * @param row La fila de la que se desea obtener el valor.
     * @param col La columna de la que se desea obtener el valor.
     * @return El valor que se encuentra en la posición especificada.
     */
    public String getValueAt(int row, int col)
    {
        if (row!=-1)
            return objUti.parseString(objTblMod.getValueAt(row, col));
        else
            return "";
    }


    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabMovInv(){
        int intUltReg;
        boolean blnRes=true;
        //int intNumIngImpDia=-1;
        try{
            if (conRef!=null){
                stm=conRef.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                getSecuencias();

                //Obtener el código del último registro.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpIngImp + "";
                strSQL+=" AND a1.co_loc=" + intCodLocIngImp + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
//                System.out.println("error: " + strSQL);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv(";
                strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,ne_secGrp,ne_secEmp,ne_numCot,ne_numDoc,tx_numDoc2,";
                strSQL+="        tx_numPed,ne_numOrdDes,ne_numGui,co_dia,fe_doc,fe_ven,co_cli,tx_ruc,tx_nomCli,tx_dirCli,";
                strSQL+="        tx_telCli,tx_ciuCli,co_com,tx_nomVen,tx_ate,co_forPag,tx_desForPag,nd_sub,nd_porIva,";
                strSQL+="        nd_valIva,nd_tot,nd_pesTotKgr,tx_ptoPar,tx_tra,co_motTra,tx_desMotTra,co_cta,co_motDoc,";
                strSQL+="        co_mnu,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,co_usrIng,co_usrMod,tx_comIng,";
                strSQL+="        tx_comMod,fe_con,tx_obs3,co_forRet,tx_vehRet,tx_choRet,ne_numVecRecDoc,fe_ultRecDoc,";
                strSQL+="        tx_obsSolAut,tx_obsAutSol,st_aut,st_tipDev,st_conInv,st_docGenDevMerMalEst,";
                strSQL+="        st_regRep,ne_numDocRee,st_creGuiRem,st_conInvTraAut,co_locRelSolDevVen,co_tipDocRelSolDevVen,";
                strSQL+="        co_docRelSolDevVen,st_excDocConVenCon,fe_autExcDocConVenCon,co_usrAutExcDocConVenCon,";
                strSQL+="        tx_comAutExcDocConVenCon,co_ben,tx_benChq,st_docConMerSalDemDebFac,st_autAnu,fe_autAnu,";
                strSQL+="        co_usrAutAnu,tx_comAutAnu,co_ptoDes,st_emiChqAntRecFacPrv,st_docMarLis,st_itmSerPro,";
                strSQL+="        co_exp,st_cieDis)";
                strSQL+=" VALUES (";
                strSQL+=" " + txtCodImp.getText(); //co_emp
                strSQL+=", " + intCodLocIngImp; //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                strSQL+=", " + txtCodDoc.getText(); //co_doc
                strSQL+=", " + intSecGrp;  //ne_secGrp
                strSQL+=", " + intSecEmp;  //ne_secEmp
                strSQL+=", Null";//ne_numCot
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);//ne_numDoc
                strSQL+=", " + objUti.codificar((txtNumPedIngImp.getText()+"_AJ"),1);//tx_numDoc2
                strSQL+=", Null";//tx_numPed
                strSQL+=", Null";//ne_numOrdDes
                strSQL+=", Null";//ne_numGui
                strSQL+=", " + txtCodDoc.getText();//co_dia
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//fe_ven
                if(txtCodTipDoc.getText().equals("" + objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP)){//INIMPO
                    strSQL+=", Null";//co_cli
                    strSQL+=", Null";//tx_ruc
                    strSQL+=", Null";//tx_nomCli
                }
                else{//COMPRAS LOCALES
                    strSQL+=", " + txtCodExp.getText() + "";//co_cli
                    strSQL+=", " + objUti.codificar(txtNomExp2.getText()) + "";//tx_ruc
                    strSQL+=", " + objUti.codificar(txtNomExp.getText()) + "";//tx_nomCli
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
                strSQL+=", " + objUti.codificar(txtPesDoc.getText());//nd_pesTotKgr
                strSQL+=", Null";//tx_ptoPar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_motTra
                strSQL+=", Null";//tx_desMotTra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu();//co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText());//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText());//tx_obs2
                strSQL+=", 'O'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultMod
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrIng
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comIng
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comMod
                strSQL+=", Null";//fe_con
                strSQL+=", Null";//tx_obs3
                strSQL+=", Null";//co_forRet
                strSQL+=", Null";//tx_vehRet
                strSQL+=", Null";//tx_choRet
                strSQL+=", 0";//ne_numVecRecDoc
                strSQL+=", Null";//fe_ultRecDoc
                strSQL+=", Null";//tx_obsSolAut
                strSQL+=", Null";//tx_obsAutSol
                strSQL+=", 'P'";//st_aut
                //strSQL+=", " + INT_DAT_VAL_AUT_DOC_AJU + "";//nd_valAut
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

                if(txtCodTipDoc.getText().equals("" + objZafImp.INT_COD_TIP_DOC_AJU_ING_IMP))//INIMPO
                    strSQL+=", " + objUti.codificar(txtCodExp.getText());//co_exp
                else//Compras Locales
                    strSQL+=", Null";//co_exp

                strSQL+=", 'N'";//st_cieDis

                strSQL+=");";

                //Inserta la relación entre el Ingreso por Ajuste  y el Importación/Compras Locales
                strSQL+="INSERT INTO tbr_cabmovinv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, ";
                strSQL+="   co_docrel, st_regrep, co_emprel)";
                strSQL+="VALUES (";
                strSQL+="" + intCodEmpIngImp + ",";//co_emp
                strSQL+="" + intCodLocIngImp + ",";//co_loc
                strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipdoc
                strSQL+="" + txtCodDoc.getText() + ",";//co_doc
                strSQL+="'A',";//st_reg
                strSQL+="" + intCodLocIngImp + ",";//co_locrel
                strSQL+="" + intCodTipDocIngImp + ",";//co_tipdocrel
                strSQL+="" + intCodDocIngImp + ",";//co_docrel
                strSQL+="'I',";//st_regrep
                strSQL+="" + intCodEmpIngImp + "";//co_emprel
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

    /**
     * Esta función permite insertar la detalle de un registro.
     * @return true: Si se pudo insertar la detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetMovInv(Connection conDet){
        boolean blnRes=true;
        String strSQLIns="";
        int j=-1;
        String strLin="";
        BigDecimal bgdCanAjuIngEgr=BigDecimal.ZERO;//Cantidad que se debe ajustar
        try{
            if (conDet!=null){
                stm=conDet.createStatement();

                if(chrTipOpe=='n'){
                    j=1;
                }
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();

                    if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                        j=new Integer(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_ING_IMP).toString());
                    }


                    if(  (strLin.equals("I")) || (strLin.equals("M"))  ){
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            //Armar la sentencia SQL.
                            //Insertar
                            strSQL="";
                            strSQL+="INSERT INTO tbm_detMovInv(";
                            strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,co_reg,ne_numFil,co_itm,co_itmAct,tx_codAlt,tx_codAlt2,";
                            strSQL+="        tx_nomItm,tx_uniMed,co_bod,tx_nomBodOrgDes,nd_can,nd_canOrg,nd_canDev,nd_cosUni,nd_preUniVenLis,";
                            strSQL+="        nd_porDesVenMax,nd_preUni,nd_porDes,st_ivaCom,nd_tot,nd_cosTot,nd_exi,nd_valExi,nd_cosPro,";
                            strSQL+="        nd_cosUniGrp,nd_cosTotGrp,nd_exiGrp,nd_valExiGrp,nd_cosProGrp,st_merIngEgrFisBod,nd_canCon,";
                            strSQL+="        nd_canNunRec,nd_canTotNunRecPro,nd_canTotMalEst,nd_canTotMalEstPro,tx_obs1,co_usrCon,";
                            strSQL+="        co_locRelSolDevVen,co_tipDocRelSolDevVen,co_docRelSolDevVen,co_regRelSolDevVen,co_locRelSolSalTemMer,";
                            strSQL+="        co_tipDocRelSolSalTemMer,co_docRelSolSalTemMer,co_regRelSolSalTemMer,st_cliRetEmpRel,tx_obsCliRetEmpRel,";
                            strSQL+="        nd_ara,nd_preUniImp,nd_valTotFobCfr,nd_cantonmet,nd_valFle,nd_valCfr,nd_valTotAra,nd_valTotGas,st_regRep, st_tipAju)";
                            strSQL+=" (SELECT ";
                            if(chrTipOpe=='n'){
                                strSQL+=" " + intCodEmpIngImp; //co_emp
                                strSQL+=", " + intCodLocIngImp; //co_loc
                                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                                strSQL+=", " + txtCodDoc.getText(); //co_doc
                            }
                            else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                                strSQL+=" " + intCodEmpAju; //co_emp
                                strSQL+=", " + intCodLocAju; //co_loc
                                strSQL+=", " + intCodTipDocAju; //co_tipdoc
                                strSQL+=", " + intCodDocAju; //co_doc
                            }

                            strSQL+=", " + j; //co_reg
                            strSQL+=", Null";//ne_numFil
                            strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP);//co_itm
                            strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP);//co_itmAct
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)) + "";//tx_codAlt
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_LET)) + "";//tx_codAlt2
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)) + "";//tx_nomItm
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED)) + "";//tx_uniMed
                            strSQL+=", " + txtCodBodEmp.getText() + "";//co_bod
                            strSQL+=", " + objUti.codificar(txtNomBodEmp.getText()) + "";//tx_nomBodOrgDes

                            bgdCanAjuIngEgr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));

                            strSQL+=", " + bgdCanAjuIngEgr + "";//nd_can

                            strSQL+=", Null";//nd_canOrg
                            strSQL+=", 0";//nd_canDev
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), 4) + "";//nd_cosUni
                            strSQL+=", Null";//nd_preUniVenLis
                            strSQL+=", Null";//nd_porDesVenMax
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), 4) + "";//nd_preUni
                            strSQL+=", 0";//nd_porDes
                            strSQL+=", 'N'";//st_ivaCom
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), 4) + "";//nd_tot 'precio al publico'
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), 4) + "";//nd_cosTot 'precio de compra para vender'
                            strSQL+=", 0";//nd_exi
                            strSQL+=", 0";//nd_valExi
                            strSQL+=", 0";//nd_cosPro
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), 4) + "";//nd_cosUniGrp
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), 4) + "";//nd_cosTotGrp
                            strSQL+=", 0";//nd_exiGrp
                            strSQL+=", 0";//nd_valExiGrp
                            strSQL+=", 0";//nd_cosProGrp
                            //strSQL+=", 'S'";//st_merIngEgrFisBod
                            strSQL+=", " + (bgdCanAjuIngEgr.compareTo(BigDecimal.ZERO)==0?"'N'":"'S'") + "";//st_merIngEgrFisBod
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
                            strSQL+=", Null";//nd_ara
                            strSQL+=", Null";//nd_preUniImp
                            strSQL+=", Null";//nd_valTotFobCfr
                            strSQL+=", Null";//nd_cantonmet
                            strSQL+=", Null";//nd_valFle
                            strSQL+=", Null";//nd_valCfr
                            strSQL+=", Null";//nd_valTotAra
                            strSQL+=", Null";//nd_valTotGas
                            strSQL+=", 'I'";//st_regRep
                            strSQL+=", '" + objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET) + "'";//st_tipAju
                            strSQL+="	    WHERE NOT EXISTS(";
                            strSQL+="           SELECT *FROM tbm_detMovInv";

                            if(chrTipOpe=='n'){
                                strSQL+="           WHERE co_emp=" + intCodEmpIngImp + "";
                                strSQL+="           AND co_loc=" + intCodLocIngImp + "";
                                strSQL+="           AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                                strSQL+="           AND co_doc=" + txtCodDoc.getText() + "";
                            }
                            else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                                strSQL+="           WHERE co_emp=" + intCodEmpAju + "";
                                strSQL+="           AND co_loc=" + intCodLocAju + "";
                                strSQL+="           AND co_tipDoc=" + intCodTipDocAju + "";
                                strSQL+="           AND co_doc=" + intCodDocAju + "";
                            }

                            //strSQL+="           AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+="           AND co_reg=" + j + "";
                            strSQL+="	    )";
                            strSQL+=")";
                            strSQL+=";";
//                            System.out.println("INSERT: " + strSQL);
                            strSQLIns+=strSQL;

                            //Actualizar
                            strSQL="";
                            strSQL+="UPDATE tbm_detMovInv";
                            strSQL+=" SET nd_can=" + bgdCanAjuIngEgr + "";
                            strSQL+=", nd_cosUni=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_tot=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), objParSis.getDecimalesBaseDatos()) + "";
                            strSQL+=", nd_cosTot=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_cosUniGrp=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_cosTotGrp=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_preUniImp=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), objParSis.getDecimalesBaseDatos()) + "";
                            strSQL+=", nd_preUni=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";                            
                            strSQL+=", co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+=", co_itmAct=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+=", tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)) + "";
                            strSQL+=", tx_codAlt2=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_LET)) + "";
                            strSQL+=", tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)) + "";
                            strSQL+=", tx_uniMed=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED)) + "";
                            strSQL+=", st_tipAju=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET)) + "";


                            if(chrTipOpe=='n'){
                                strSQL+=" WHERE co_emp=" + intCodEmpIngImp + "";
                                strSQL+=" AND co_loc=" + intCodLocIngImp + "";
                                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                            }
                            else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                                strSQL+=" WHERE co_emp=" + intCodEmpAju + "";
                                strSQL+=" AND co_loc=" + intCodLocAju + "";
                                strSQL+=" AND co_tipDoc=" + intCodTipDocAju + "";
                                strSQL+=" AND co_doc=" + intCodDocAju + "";
                            }

                            //strSQL+=" AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+=" AND co_reg=" + j + "";
                            strSQL+=" AND EXISTS(";
                            strSQL+="           SELECT *FROM tbm_detMovInv";
                            strSQL+="           WHERE co_emp=" + intCodEmpAju + "";
                            strSQL+="           AND co_loc=" + intCodLocAju + "";
                            strSQL+="           AND co_tipDoc=" + intCodTipDocAju + "";
                            strSQL+="           AND co_doc=" + intCodDocAju + "";
                            //strSQL+="           AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+="           AND co_reg=" + j + "";
                            strSQL+="            )";
                            strSQL+=";";
//                            System.out.println("UPDATE: " + strSQL);
                            strSQLIns+=strSQL;                            
                            
                            j++;

//                            }

                        }
                    }
                }//fin de for

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
    


   private boolean insertar_tbmVisBueMovInv(){
       boolean blnRes=true;
       String strSQLIns="";
       try{
           if(conRef!=null){
                stm=conRef.createStatement();
                strSQL="";
                strSQL+=" INSERT INTO tbm_visBueMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_visbue, co_usr, st_reg, st_regrep)";
                strSQL+=" ( SELECT ";
                strSQL+="    " + intCodEmpAju + "";   //co_emp
                strSQL+="   ," + intCodLocAju + "";   //co_loc
                strSQL+="   ," + intCodTipDocAju + "";//co_tipdoc
                strSQL+="   ," + intCodDocAju + "";   //co_doc
                strSQL+="   ," + intCodVisBueUsr + "";//co_visbue
                strSQL+="   ," + objParSis.getCodigoUsuario() + "";//co_usr
                if(intButSelDlg==INT_BUT_ACE)
                    strSQL+=", 'A'";//st_reg
                else if(intButSelDlg==INT_BUT_DEN)
                    strSQL+=", 'D'";//st_reg
                strSQL+=", 'I'";//st_regrep
                strSQL+="   WHERE NOT EXISTS(";
                strSQL+="      SELECT *FROM tbm_visbuemovinv";
                if(chrTipOpe=='n'){
                    strSQL+="  WHERE co_emp=" + intCodEmpIngImp + "";
                    strSQL+="  AND co_loc=" + intCodLocIngImp + "";
                    strSQL+="  AND co_tipDoc=" + intCodTipDocIngImp + "";
                    strSQL+="  AND co_doc=" + intCodDocIngImp + "";
                    strSQL+="  AND co_visbue=" + intCodVisBueUsr + "";
                    strSQL+="  AND co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                    strSQL+="  WHERE co_emp=" + intCodEmpAju + "";
                    strSQL+="  AND co_loc=" + intCodLocAju + "";
                    strSQL+="  AND co_tipDoc=" + intCodTipDocAju + "";
                    strSQL+="  AND co_doc=" + intCodDocAju + "";
                    strSQL+="  AND co_visbue=" + intCodVisBueUsr + "";
                    strSQL+="  AND co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                strSQL+="  )";
                strSQL+=");";
                strSQLIns+=strSQL;
                
                strSQL="";
                strSQL+=" UPDATE tbm_visBueMovInv";
                strSQL+=" SET";
                if(intButSelDlg==INT_BUT_ACE)
                    strSQL+=" st_reg='A'";//st_reg
                else if(intButSelDlg==INT_BUT_DEN)
                    strSQL+=" st_reg='D'";//st_reg
                                
                if(chrTipOpe=='n'){
                    strSQL+=" WHERE co_emp=" + intCodEmpIngImp + "";
                    strSQL+=" AND co_loc=" + intCodLocIngImp + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=" AND co_visbue=" + intCodVisBueUsr + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                else if( (chrTipOpe=='x') || (chrTipOpe=='m') ){
                    strSQL+=" WHERE co_emp=" + intCodEmpAju + "";
                    strSQL+=" AND co_loc=" + intCodLocAju + "";
                    strSQL+=" AND co_tipDoc=" + intCodTipDocAju + "";
                    strSQL+=" AND co_doc=" + intCodDocAju + "";
                    strSQL+=" AND co_visbue=" + intCodVisBueUsr + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                strSQL+=" AND EXISTS(";
                strSQL+="    SELECT *FROM tbm_visBueMovInv";
                strSQL+="    WHERE co_emp=" + intCodEmpAju + "";
                strSQL+="    AND co_loc=" + intCodLocAju + "";
                strSQL+="    AND co_tipDoc=" + intCodTipDocAju + "";
                strSQL+="    AND co_doc=" + intCodDocAju + "";
                strSQL+="    AND co_visbue=" + intCodVisBueUsr + "";
                strSQL+="    AND co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" )";
                strSQL+=";";
                strSQLIns+=strSQL;
                
                System.out.println("insertar_tbmVisBueMovInv: " + strSQLIns);
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


   private void isInaCamFrm(){
        objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

        txtCodTipDoc.setEditable(false);
        txtDesCorTipDoc.setEditable(false);
        txtDesLarTipDoc.setEditable(false);
        butTipDoc.setEnabled(false);
        txtCodIngImp.setEditable(false);
        txtNumDocIngImp.setEditable(false);
        txtNumPedIngImp.setEditable(false);
        butPedImp.setEnabled(false);
        dtpFecDoc.setEnabled(false);
        txtNumDoc.setEditable(false);
        txtCodDoc.setEditable(false);
        txtValDoc.setEditable(false);
        txtPesDoc.setEditable(false);
        txtCodImp.setEditable(false);
        txtNomImp.setEditable(false);
        txtCodExp.setEditable(false);
        txtNomExp.setEditable(false);
        txtNomExp2.setEditable(false);
        butAce.setVisible(false);
        butDen.setVisible(false);
   }

   /**
    * Función que permite establecer estados de denegar/autorizar ajustes de inventario de importaciones.
    * <BR> Para Aceptar: Sólo se aplica a Ajustes.
    * <BR> Para Denegar: Se aplica a Ajuste y a Ingreso por Importación
    * @param estadoDocumento
    * @param estadoCampoIngImp
    * @param instanciaVistoBueno 0: Indica que aún falta un visto bueno por conceder;   1: Indica que todos los vistos buenos están concedidos
    * @return
    */
   private boolean setEstAutDenVisBue(int instanciaVistoBueno){
       boolean blnRes=false;
       try{
            if(instanciaVistoBueno==0) /* TIENE UN VISTO BUENO, PERO FALTA 1 O MAS VISTOS BUENOS. */
            { 
                if(intButSelDlg==INT_BUT_DEN) { //Denegar
                    strDocActivo="I";            //Ajuste
                    strEstAutDenVisBue="D";      //Ajuste
                    strEstIngImpDocIngImp="B";   //Ingreso por Importacion
                    //strEstIngImpDocAju="C";    //Ingreso por Importacion   No se da porque en el último VB se cambia este estado, por eso se mantiene el estado C, por tanto como ya tiene ese estado el Ingreso por Importación, no se lo toca
                }
                else if(intButSelDlg==INT_BUT_ACE){ //Autorización - PARCIAL
                    strDocActivo="O";           //Ajuste
                    strEstAutDenVisBue="E";     //Ajuste
                }
                blnRes=true;
            }
            else if(instanciaVistoBueno==1) /* TODOS LOS VISTOS BUENOS ESTÁN COMPLETOS.*/
            {
                if(intButSelDlg==INT_BUT_DEN){ //Denegar
                    strDocActivo="I";            //Ajuste
                    strEstAutDenVisBue="D";      //Ajuste
                    strEstIngImpDocIngImp="B";   //Ingreso por Importacion
                }
                else if(intButSelDlg==INT_BUT_ACE){ //Autorización - TOTAL - TODO OK
                    strEstAutDenVisBue="A";   //Ajuste
                    strDocActivo="A";         //Ajuste
                }
                blnRes=true;
           }
           System.out.println("strDocActivo: "+strDocActivo+" - strEstAutDenVisBue: "+strEstAutDenVisBue+" - strEstIngImpDocIngImp: "+strEstIngImpDocIngImp);
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   

/**
 * Función que permite eliminar del detalle del documento items eliminados de forma manual por el usuario
 * @return true Si se pudo realizar la operación
 * <BR> false Caso contrario
 */   
   /*
    private boolean eliminar_tbmDetMovInv(){
        boolean blnRes=true;
        String strSQLIns="";
        String strEstEliItm="";
        int intCodRegItm=-1;
        try{
            if (conRef!=null){
                stm=conRef.createStatement();                
                for(int i=0; i<arlDatFilEli.size(); i++){
                    intCodRegItm=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_REG);
                    strEstEliItm=objUti.getStringValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_EST_ITM);
                    
                    strSQL="";
                    strSQL+=" UPDATE tbm_detMovInv";
                    strSQL+=" SET";
                    if(strEstEliItm.equals("S"))
                         strSQL+=" st_reg='I'";
                    else
                        strSQL+=" st_itmEli='A'";
                    strSQL+=" WHERE co_emp=" + intCodEmpAju + "";
                    strSQL+=" AND co_loc=" + intCodLocAju + "";
                    strSQL+=" AND co_tipDoc=" + intCodTipDocAju + "";
                    strSQL+=" AND co_doc=" + intCodDocAju + "";
                    strSQL+=" AND co_reg=" + intCodRegItm + "";
                    strSQL+=" AND EXISTS(";
                    strSQL+="           SELECT *FROM tbm_detMovInv";
                    strSQL+="           WHERE co_emp=" + intCodEmpAju + "";
                    strSQL+="           AND co_loc=" + intCodLocAju + "";
                    strSQL+="           AND co_tipDoc=" + intCodTipDocAju + "";
                    strSQL+="           AND co_doc=" + intCodDocAju + "";
                    strSQL+="           AND co_reg=" + intCodRegItm + "";
                    strSQL+="            )";
                    strSQL+=" ;";
                    strSQLIns+=strSQL;
                }
//                System.out.println("eliminar_tbmDetMovInv: " + strSQLIns);
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
    */

   private boolean setFilEliUsr(int filasSeleccionadas[]){
        boolean blnRes=true;
        int intFilEli[]=filasSeleccionadas;
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            for(int p=(intFilEli.length-1);p>=0; p--){
                System.out.println("intFilEli[]: " + intFilEli[p]);
                System.out.println("dato[]: " + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM_MAE));
                //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
                arlRegFilEli=new ArrayList();
                arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM_GRP, "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM_GRP));
                arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM_EMP, "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM_EMP));
                arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM_MAE, "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM_MAE));
                arlRegFilEli.add(INT_ARL_FIL_ELI_COD_REG, "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_REG_ING_IMP));
                arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
                arlDatFilEli.add(arlRegFilEli);
                //ahora si se remueve la fila seleccionada
                objTblMod.removeRow(intFilEli[p]);
            }
            System.out.println("arlDatFilEli: " + arlDatFilEli.toString());
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeEmptyRows();
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }
    
   /**
    * Función que permite conocer si el modelo ha cambiado
    * @return true Si el modelo ha cambiado y se da click en Aceptar
    * <BR> false Si el modelo no ha cambiado, o si el modelo ha cambiado pero se da click en Denegar
    */
    public boolean isAjuModChg() {
//        System.out.println("isAjuModChg: " + isAjuModChg);
        return isAjuModChg;
    }

    
    /**
     * Función que permite eliminar lógicamente los items en la tabla de detalle, se los deja inactivos
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    public boolean eliminar_tbmDetMovInv(){
        boolean blnRes=true;
        String strCodReg="";
        java.util.ArrayList arlAux;
        try{
            if (conRef!=null){
                arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                if(arlAux!=null){                
                    for(int i=0;i<arlDatFilEli.size();i++){
                        if(i==0){
                            strCodReg+=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_REG);
                        }
                        else{
                            strCodReg+=", " + objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_REG);
                        }
                    }
                    if(!strCodReg.equals("")){
                        stm=conRef.createStatement();
                        strSQL="";
                        strSQL+=" UPDATE tbm_detMovInv";
                        strSQL+=" SET st_reg='I'";
                        strSQL+=" WHERE co_emp=" + intCodEmpAju + "";
                        strSQL+=" AND co_loc=" + intCodLocAju + "";
                        strSQL+=" AND co_tipDoc=" + intCodTipDocAju + "";
                        strSQL+=" AND co_doc=" + intCodDocAju + "";
                        strSQL+=" AND co_reg IN(" + strCodReg + ");";
                        System.out.println("eliminarItems: " + strSQL);
                        stm.executeUpdate(strSQL);
                        stm.close();
                        stm=null;
                    }
                }
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

    private void unificarCuentasContablesDiario(){
        //Vector vecTmp;
        try{
            //objAsiDia.getDetalleDiario()
            
            for(int i=0; i<objAsiDia.getDetalleDiario().size(); i++){
                System.out.println("i: " + objAsiDia.getDetalleDiario().elementAt(i));
                System.out.println("i: " + objAsiDia.getDetalleDiario().get(i));
            }
            
//            //System.out.println("Detalle diario: " + objAsiDia.getDetalleDiario());
//            strSQL="";
//            strSQL+=" SELECT d1.co_cta, d1.tx_codCta, d1.tx_nomCta";
//            strSQL+=" , CASE WHEN d1.nd_monDeb>d1.nd_monHab THEN (d1.nd_monDeb - d1.nd_monHab) END AS nd_monDeb";
//            strSQL+=" , CASE WHEN d1.nd_monHab>d1.nd_monDeb THEN (d1.nd_monHab - d1.nd_monDeb) END AS nd_monHab";
//            strSQL+=" FROM(";
//            strSQL+=" 	SELECT b1.co_cta, b1.tx_codCta, b1.tx_nomCta, SUM(b1.nd_monDeb) AS nd_monDeb, SUM(b1.nd_monHab) AS nd_monHab";
//            strSQL+=" 	FROM(";
//            for(int i=0; i<objAsiDia.getDetalleDiario().size(); i++){
//                if(i>0){
//                    strSQL+=" UNION";
//
//                }
//                strSQL+="";
//                strSQL+="";
//                strSQL+="";
//                strSQL+="";
//                
//                
//                
//		SELECT 2253 AS co_cta, '1.01.06.01.15' AS tx_codCta, 'BODEGA  GENERAL' AS tx_nomCta, 371.86 AS nd_monDeb, 0 AS nd_monHab
//		
//		SELECT 1386 AS co_cta, '5.02.01.01' AS tx_codCta, 'COSTO DE VENTAS - GQUIL.' AS tx_nomCta, 0 AS nd_monDeb, 371.86 AS nd_monHab
//		UNION
//		SELECT 3320 AS co_cta, '1.01.06.02.559' AS tx_codCta, 'Reclamos a terceros/Faltantes' AS tx_nomCta, 53.95 AS nd_monDeb, 0 AS nd_monHab
//		UNION
//		SELECT 2253 AS co_cta, '1.01.06.01.15' AS tx_codCta, 'BODEGA  GENERAL' AS tx_nomCta, 0 AS nd_monDeb, 53.95 AS nd_monHab
//                
//                
//                
//                
//            }
//            
//            strSQL+=" 	) AS b1";
//            strSQL+=" 	GROUP BY co_cta, tx_codCta, tx_nomCta";
//            strSQL+=" ) AS d1";
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        

    }

    /**
     * Función que permite eliminar los registros con cantidad cero
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean eliminarItemsCantidadCero(){
        boolean blnRes=true;
        BigDecimal bgdCan=BigDecimal.ZERO;
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            for (int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                System.out.println("i: " + i);
                bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                if(bgdCan.compareTo(BigDecimal.ZERO)==0)
                    objTblMod.removeRow(i);
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeEmptyRows();
            
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
     * <BR> false en el caso contrario
     */
    private boolean insertar_tbmCabOrdDis(){
        boolean blnRes=true;
        intCodDocOrdDis=0;
        intNumDocOrdDis=0;
        try{
            if(intInsOrdDis==1){
                if(conRef!=null){
                    stm=conRef.createStatement();
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_doc) AS co_doc";
                    strSQL+=" FROM tbm_cabOrdDis AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_ORD_DIS_INM;
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intCodDocOrdDis=rst.getObject("co_doc")==null?0:rst.getInt("co_doc");
                    intCodDocOrdDis++;

                    strSQL="";
                    strSQL+="SELECT MAX(a1.ne_ultDoc) AS ne_numDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + objZafImp.INT_COD_TIP_DOC_ORD_DIS_INM;
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumDocOrdDis=rst.getObject("ne_numDoc")==null?0:rst.getInt("ne_numDoc");

                    strSQL="";
                    strSQL+="INSERT INTO tbm_caborddis(";
                    strSQL+=" co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, ";
                    strSQL+=" tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, ";
                    strSQL+=" st_regrep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                    strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                    strSQL+="" + objZafImp.INT_COD_TIP_DOC_ORD_DIS_INM + ",";//co_tipdoc
                    strSQL+="" + intCodDocOrdDis + ",";//co_doc
                    strSQL+=" CURRENT_DATE,";//fe_doc
                    strSQL+="" + intNumDocOrdDis + ",";//ne_numdoc
                    strSQL+="" + INT_COD_BOD_INM_GRP + ", ";//co_bod
                    strSQL+=" Null, ";//tx_obs1
                    strSQL+=" Null,";//tx_obs2
                    strSQL+="'A',";//st_reg
                    strSQL+="CURRENT_TIMESTAMP,";//fe_ing
                    strSQL+="CURRENT_TIMESTAMP,";//fe_ultmod
                    strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                    strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                    strSQL+="'I'";//st_regrep
                    strSQL+=");";
                    System.out.println("insertar_tbmCabOrdDis: " + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
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
     * Función que permite guardar información
     * return true Si se pudo realizar la operación
     * <BR> false en el caso contrario
     */    
    private boolean insertar_tbmDetOrdDis(){
        boolean blnRes=true;
        String strSQLUpd="";
        BigDecimal bgdCanOrdDis=new BigDecimal("0");
        int j=0;
        try{
            if(intInsOrdDis==1){
                if(conRef!=null){
                    stm=conRef.createStatement();
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        j++;
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detorddis(";
                        strSQL+=" co_emp, co_loc, co_tipdoc,";
                        strSQL+=" co_doc, co_reg, co_itm, nd_can";
                        strSQL+=" , co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel)";
                        strSQL+=" VALUES (";
                        strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                        strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                        strSQL+="" + objZafImp.INT_COD_TIP_DOC_ORD_DIS_INM + ",";//co_tipdoc
                        strSQL+="" + intCodDocOrdDis + ",";//co_doc
                        strSQL+="" + j + ",";//co_reg
                        strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP) + ",";//co_itm
                        bgdCanOrdDis=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString())   );
                        strSQL+="" + bgdCanOrdDis + ",";//nd_can
                        strSQL+="" + txtCodImp.getText() + ",";//co_empRel
                        strSQL+="" + intCodLocIngImp + ",";//co_locRel
                        strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipDocRel
                        strSQL+="" + txtCodDoc.getText() + ",";//co_docRel
                        strSQL+="" + j + "";//co_regRel
                        strSQL+=");";
                        strSQLUpd+=strSQL;
                    }
                    System.out.println("insertar_tbmDetOrdDis: " + strSQLUpd);
                    stm.executeUpdate(strSQLUpd);
                    stm.close();
                    stm=null;
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
     * Función que permite establecer en el arreglo de items para stock y el arreglo de items para disponible cuales son los itmes que deben eliminarse
     * según los items eliminados por el usuario
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean setEstEliItmSob(){
        boolean blnRes=true;
        int intCodMaeItmEli=-1;
        int intCodMaeItmTbl=-1;
        String strEstItmEli="";
        try{
            //Para stock de inventario
            for(int i=0; i<arlDatFilEli.size(); i++){
                intCodMaeItmEli=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_ITM_MAE);
                strEstItmEli=objUti.getStringValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_EST_ITM);
                if(strEstItmEli.equals("S")){
                    for(int j=0; j<arlDatStkInvItm.size(); j++){
                        intCodMaeItmEli=objUti.getIntValueAt(arlDatStkInvItm, j, INT_ARL_STK_INV_COD_ITM_MAE);
                        if(intCodMaeItmEli==intCodMaeItmTbl){
                            //objUti.setStringValueAt(arlDatStkInvItm, i, INT_ARL_STK_INV_EST_REG, "E");
                            arlDatStkInvItm.remove(i);
                        }
                    }
                }
            }
            //Para disponible
            for(int i=0; i<arlDatFilEli.size(); i++){
                intCodMaeItmEli=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_ITM_MAE);
                strEstItmEli=objUti.getStringValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_EST_ITM);
                if(strEstItmEli.equals("S")){
                    for(int j=0; j<arlDatDisInvItm.size(); j++){
                        intCodMaeItmEli=objUti.getIntValueAt(arlDatDisInvItm, j, INT_ARL_STK_INV_COD_ITM_MAE);
                        if(intCodMaeItmEli==intCodMaeItmTbl){
                            arlDatDisInvItm.remove(i);
                            //objUti.setStringValueAt(arlDatDisInvItm, i, INT_ARL_STK_INV_EST_REG, "E");
                        }
                    }
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
     * Función que permite conocer si el modelo de datos va a generar un ajuste de inventario con items sobrantes
     * Cabe recalcar que valida según los datos que se muestran en el momento en que se llama al método, que no 
     * necesariamente son todos los items que se deberían ajustar ( eliminación de items por el usuario ).
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean isModDatGenAjuInvSob(){
        boolean blnRes=true;
        BigDecimal bgdValAju=BigDecimal.ZERO;
        try{
            intInsOrdDis=0;
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdValAju=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                if(bgdValAju.compareTo(BigDecimal.ZERO)>0){//Sobrante en el modelo de datos
                    intInsOrdDis=1;
                    break;
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
     * Función que permite conocer si el modelo de datos va a generar un ajuste de inventario con items faltantes
     * Cabe recalcar que valida según los datos que se muestran en el momento en que se llama al método, que no 
     * necesariamente son todos los items que se deberían ajustar ( eliminación de items por el usuario ).
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean isModDatGenAjuInvFal(){
        boolean blnRes=false;
        BigDecimal bgdValAju=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdValAju=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                if(bgdValAju.compareTo(BigDecimal.ZERO)<0){//faltantes en el modelo de datos
                    blnRes=true;
                    break;
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
     * Esta función permite costear los items de inventario.
     * @return true: Si se pudo costear.
     * <BR>false: En el caso contrario.
     */
    public boolean costearItm(Connection conRef)
    {
        boolean blnRes=false;
        try
        {
            if (conRef!=null)
            {
                if(strDocActivo.equals("A")){
                    switch (chrTipOpe){
                        case 'n':
                            System.out.println("costeando...");
                            blnRes=true;
                            break;                        
                        case 'm':
                            System.out.println("costeando...");
                            if(objUti.costearDocumento(this, objParSis, conRef, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju ) )  {
                                blnRes=true;
                            }
                            break;
                    }
                }
                else{ //Cuando no se debe costear igual se retorna true.
                    blnRes=true;
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
            if (conRef!=null){
                stm=conRef.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + intCodEmpIngImp;
                strSQL+=" AND co_loc=" + intCodLocIngImp;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                System.out.println("actualizarCabTipDoc: " + strSQL);
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
    
    ////********************************************************************************************************************    

    
    

}