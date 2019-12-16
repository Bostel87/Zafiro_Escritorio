/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenFacAut;

import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafGetDat.ZafDatItm;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPulFacEle.ZafPulFacEle;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author sistemas6
 */
public class ZafGenFacAut {
    
    
    private ZafParSis objParSis;
    private ZafCorEle objCorEle;
    private ZafUtil objUti;
   
    private String strCorEleTo="sistemas6@tuvalsa.com"; 
    private String strTitCorEle="Sistemas: Generación de Facturas Automaticas"; 
    private String strNomArchHis; 
   
    StringTokenizer stbTokens=null;

    private javax.swing.JTable tblPag;
    private javax.swing.JTable tblDat;
    
    private int intEstMens=0;
    private int intCodBodGrp=0;
    private int intCodEmpOrdDes_GuiRem=0;
    private int intCodLocOrdDes_GuiRem=0;
    private int intCodTipDoc= 0;
    private int intCodDoc=0;
    private boolean blnEnvNumODBod=true, blnEstVolFacGuiImp=false;

   
    private String strTipMovFG="";
    ServerSocket puerto=null;
 
    private int intCodTipDocFacEle=228;  // 
 
    
    private String strNomCli,strCliIdentificacion,strCliCiudad,strNumPed;
    private String strCodLocSol,strCodTipDocSol,strCodDocSol, strCliDir,strCliDirGui,strCliTelefono;
    private String strCodTipPerCli,strDateFecCot,strIdeCli,strConfInv="";
    private int intCodCli,intTipForPag,intTipForPag2,intNumDiaVal,intTipMod;
    private double Glo_dlbMaxDes, Glo_dlbMarUti,dblPes,dblDiaGraCli,dblPes2;
    private String strNomVen;
    private int intCodVen,intReaGuiaRem;
    private int intCodBodPre;
    private String strNomBodPtaPar;
    private Date datFecDoc, datFecAuxGuia;     
    private int intCodMnuFac = 14; /* MENU DE FATURACION DE VENTAS */
    private String strFecSis,strFecSistema, strFecModCot, strSql, strFecSisBase, strSQL;
    private double dblRetFueGlo = 0, dblRetIvaGlo = 0;
    private Vector vecDataTblPag;
    
    private ZafInvItm objInvItm;                                                // Para trabajar con la informacion de tipo de documento
    private String strODxConf="";
    private final int intCanArr[] = new int[1];
    private final int intarreglodia[] = new int[10];
    private final int intarreglonum[] = new int[10];
    private String strarreglosop[] = new String[10];
    
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private ZafTblMod objTblMod,objTblModPag;
    private ArrayList arlRegAniMes, arlDatAniMes;
    
    private String strTipPer_emp = "";
    private int intCodTipPerEmp;
    private String strNomBodPrv = "";
    
    //Calculos Cotización
    private double dblPorIva;                                                  //Porcentaje de Iva para la empresa enviado en ZafParSis
    private double dblTotalCot, dblIvaCot;
    private double dblSubtotalCot;
    private double dblMonMaxVenCon = 0;
    private double bldivaEmp = 0;
    
    private String stIvaVen = "S";
    
     //Códigos de Motivos del documento para las retenciones
    private int intCodMotBien = 0;                                                //Bien
    private int intCodMotServ = 0;                                                //Servicio
    private int intCodMotTran = 0;                                                //Transporte
        
    private int intNumDec=2;  // NUMERO DE DECIMALES CON LOS QUE TRABAJA EL SERVICIO
    private ZafCtaCtb_dat objZafCtaCtb_dat;  // Para obtener  los codigos y nombres de ctas ctbles
    
    private GenOD.ZafGenOdPryTra objZafGenOD;  // ZafReglas Genera OD Local
    
    private UltDocPrint objUltDocPrint;                                         // Para trabajar con la informacion de tipo de documento
    
    private static final int INT_ARL_COD_EMP=0;
    private static final int INT_ARL_COD_LOC=1; 
    private static final int INT_ARL_COD_TIP_DOC=2;
    private static final int INT_ARL_COD_DOC=3; 
    private static final int INT_ARL_COD_REG=4;
    private static final int INT_ARL_COD_REG_REL=5; 
    private static final int INT_ARL_COD_ITM=6;
    private static final int INT_ARL_CAN_ITM=7;
    private static final int INT_ARL_BOD_ING=8;  /* Bodega de Ingreso */
    private static final int INT_ARL_BOD_EGR=9;  /* Bodega de Egreso */
    private static final int INT_ARL_CAN_UTI=10;  /* Cantidad Utilizada */

    private ArrayList arlDatIng;
    
    
     /**
     * Función que permite obtener el nombre del campo que se desea actualizar
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
    final int INT_ARL_STK_INV_STK_ACT=0;  // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
    final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng --> transferencia afectar ingreso 
    final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr --> transferencia afectar egreso
    final int INT_ARL_STK_INV_CAN_DES_ENT_BOD=5;
    final int INT_ARL_STK_INV_CAN_DES_ENT_CLI=6;
    final int INT_ARL_STK_INV_CAN_TRA=7;
    final int INT_ARL_STK_INV_CAN_REV=8;
    final int INT_ARL_STK_INV_CAN_RES=9;  // nd_canRes
    final int INT_ARL_STK_INV_CAN_DIS=10;  // nd_canDis
    final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 
    
    
    /* NUEVO CONTENEDOR PARA ITEMS ZafStkInv MovimientoStock */

    private static final int INT_STK_INV_COD_ITM_GRP=0;
    private static final int INT_STK_INV_COD_ITM_EMP=1;
    private static final int INT_STK_INV_COD_ITM_MAE=2;    
    private static final int INT_STK_INV_COD_LET_ITM=3;     
    private static final int INT_STK_INV_CAN_ITM=4;
    private static final int INT_STK_INV_COD_BOD_EMP=5; 
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    
    
    private static final int INT_TBL_LINEA = 0;                       //Numero de linea de la tabla
    private static final int INT_TBL_ITMALT = 1;                       //Codigo del item alterno
    private static final int INT_TBL_BUTITM = 2;                       //Boton para buscar item
    private static final int INT_TBL_BUTSTK = 3;                       //Boton para ver el stock
    private static final int INT_TBL_BUTSOL = 4;                       //Boton para ver solicitud
    private static final int INT_TBL_DESITM = 5;                       //Descripcion del item
    private static final int INT_TBL_UNIDAD = 6;                       //Unidad de medida
    private static final int INT_TBL_CODBOD = 7;                       //Codigo de bodega
    private static final int INT_TBL_BUTBOD = 8;                       //Boton para buscar bodega
    private static final int INT_TBL_TRATOT = 9;                       //Transferencia total
    private static final int INT_TBL_CANMOV = 10;                      //Cantidad del movimiento (venta o compra)
    private static final int INT_TBL_PREUNI = 11;                      //Precio de Venta
    private static final int INT_TBL_PORDES = 12;                      //Porcentaje de descuento
    private static final int INT_TBL_BLNIVA = 13;                       //Boolean Iva
    private static final int INT_TBL_TOTAL = 14;                       //Total de la venta o compra del producto
    private static final int INT_TBL_PESTOT = 15;
    private static final int INT_TBL_CODITM = 16;                       //Codigo del item
    private static final int INT_TBL_ESTADO = 17;
    private static final int INT_TBL_IVATXT = 18;
    private static final int INT_TBL_PRE_COS = 19;                       //Columna que contiene  precio de compra
    private static final int INT_TBL_DESPRECOM = 20;                       //Columna que contiene  el descuento de precio de compra  nd_porDesPreCom
    private static final int INT_TBL_COD_PRO = 21;                       //Columna que contiene el codigo del proveedor
    private static final int INT_TBL_NOM_PRO = 22;                       //Columna que contiene el Nombre del proveedor
    private static final int INT_TBL_BUT_PRO = 23;                       //Columna que contiene para busqueda del proveedor
    private static final int INT_TBL_BLNPRE = 24;                       //Columna que contiene verdadero si es problema de precio
    private static final int INT_TBL_ITMALT2 = 25;                       //Columna que contiene verdadero si es problema de precio
    private static final int INT_TBL_ITMSER = 26;                       //Columna que contiene SI el item es de servicio S o N
    private static final int INT_TBL_ITMTER = 27;                       //Columna que contiene "S" (el item es terminal L ) "N"  no corresponde a la terminal L
    private static final int INT_TBL_CODBODPRV = 28;
    private static final int INT_TBL_NOMBODPRV = 29;
    private static final int INT_TBL_BUTBODPRV = 30;
    private static final int INT_TBL_MARUTI = 31;                       //Margen de utilidad
    private static final int INT_TBL_IEBODFIS = 32;                       //Estado que dice si ingresa/egresa fisicamente en bodega
    private static final int INT_TBL_MODNOMITM = 33;                       //Nombre del item modificado
    private static final int INT_TBL_COLOCREL = 34;
    private static final int INT_TBL_COTIPDOCREL = 35;
    private static final int INT_TBL_CODOCREL = 36;
    private static final int INT_TBL_COREGREL = 37;
    private static final int INT_TBL_COLOCRELSOL = 38;
    private static final int INT_TBL_COTIPDOCRELSOL = 39;
    private static final int INT_TBL_CODOCRELSOL = 40;
    private static final int INT_TBL_COREGRELSOL = 41;
    private static final int INT_TBL_COLOCRELOC = 42;
    private static final int INT_TBL_COTIPDOCRELOC = 43;
    private static final int INT_TBL_CODOCRELOC = 44;
    private static final int INT_TBL_COREGRELOC = 45;
    private static final int INT_TBL_PRELISITM = 46;
    private static final int INT_TBL_PRELISITM2 = 47;
    private static final int INT_TBL_CANORI = 48;                       //Cantidad de ventas origen
    private static final int INT_TBL_PREORI = 49;                       //Precio de venta origen
    private static final int INT_TBL_DESORI = 50;                       //Descuento de venta origen
    private static final int INT_TBL_DESVENMAX = 51;                       //Maximo descuento de ventas
    private static final int INT_TBL_NUMFILCOMPVEN = 52;                       //Sirve para saber que item se realizara compra venta entre compaÃ±ias
    private static final int INT_TBL_MAXDESCOM = 53;                       //Maximo descuento de compras
    private static final int INT_TBL_DATBODCOM = 54;                       //Dato de las bodegas donde se realiza la compra
    private static final int INT_TBL_CODREGCOT = 55;
    private static final int INT_TBL_CANVENRES = 56;
    private static final int INT_TBL_PREVTARES = 57;
    private static final int INT_TBL_PORDESRES = 58;
    private static final int INT_TBL_CODITMRES = 59;
    private static final int INT_TBL_TIPUNIMED = 60;                       //Tipo de Unidad de Medida E=Entero F=Fraccion.
    private static final int INT_TBL_BLOPREVTA = 61;                       //Bloquea que no se pueda cambiar precio de lista y porcentaje.
    private static final int INT_TBL_DESITMORI = 62;                      //Descripcion del item origen
    private static final int INT_TBL_CLIRETBOD = 63;                      //Item se retira en otra empresa
    private static final int INT_TBL_CANCLIRETBOD = 64;                      //Cantidad Item se retira en otra empresa
    private static final int INT_TBL_CODCTAEGR = 65;
    private static final int INT_TBL_PESITM = 66;
    

    
    
    //Tabla Forma de Pago
    private static final int INT_TBL_PAGLIN = 0;                                  //Linea de pago
    private static final int INT_TBL_PAGCRE = 1;                                  //Dias de credito
    private static final int INT_TBL_PAGFEC = 2;                                  //Fecha de vencimiento
    private static final int INT_TBL_PAGRET = 3;                                  //Porcentaje de retencion
    private static final int INT_TBL_PAGMON = 4;                                  //Monto de pago
    private static final int INT_TBL_PAGGRA = 5;                                  //Dias de gracias    
    private static final int INT_TBL_PAGCOD = 6;                                  //Codigo de retencion
    private static final int INT_TBL_PAGSOP = 7;                                  //Soporte de cheque
    private static final int INT_TBL_COMSOL = 8;                                  //Soporte de cheque
    
    // PARA EL DIARIO
    private static final int INT_ARL_ANI_CIE = 0;
    private static final int INT_ARL_MES_CIE = 1;
    private static final int INT_ARL_TIP_CIE = 2;
    private int intNumFacElec=0;
    
    /*PARA LOS DOCUMENTOS GENERADOS*/
    private ArrayList arlDocGenReg, arlDocGenDat;
    private static final int INT_ARL_COD_EMP_COT = 0;
    private static final int INT_ARL_COD_LOC_COT = 1;
    private static final int INT_ARL_COD_COT = 2;
    private static final int INT_ARL_COD_EMP_FAC = 3;
    private static final int INT_ARL_COD_LOC_FAC = 4;
    private static final int INT_ARL_COD_TIP_DOC_FAC = 5;
    private static final int INT_ARL_COD_DOC_FAC = 6;
    private static final int INT_ARL_NUM_FAC = 7;
    private static final int INT_ARL_NOM_CLI=8;
    private static final int INT_ARL_COR_ELE_VEN=9;
    private ZafCfgSer objCfgSer;
    private int intCodSer=16;// Impresiones mateo
     
    private ZafCnfDoc objCnfDoc;
    private ZafStkInv objStkInv;
    private ZafPulFacEle objPulFacEle;                                          //  José Marín 06/Nov/2014
    
    
    private java.awt.Component jifCnt;
    
    public String strVer = " v 0.09";  // JM 28/Ago/2017
    private ZafCalDatFac objCalDatFac;
    
    private double dblPorComSol=0.00,dblBaseIva=0.00, dblBaseCero=0.00, dblComSol=0.00, dblValorPagar=0.00;
    Librerias.ZafGenDocCobAut.ZafGenDocCobAut objGenDocCobAut;
    private ZafModDatGenFac objModDatGenFac;
    
    
    private ZafDatItm objDatItm;
    
    public ZafGenFacAut(ZafParSis obj, java.awt.Component componente){
        try{
            System.out.println(strVer);
            this.objParSis = (ZafParSis) obj.clone();
            cargarDrv_F1(objParSis.getDriverConexion());
            jifCnt=componente;
            objCorEle = new ZafCorEle(objParSis);
            objInvItm = new ZafInvItm(objParSis);
            objUti = new ZafUtil();
            tblPag = new javax.swing.JTable();
            tblDat = new javax.swing.JTable();
            objUltDocPrint = new UltDocPrint(objParSis);
            objCfgSer = new ZafCfgSer(objParSis);
            arlDatAniMes = new ArrayList();
            txtFecDoc = new ZafDatePicker("d/m/y");
            objDatItm = new Librerias.ZafGetDat.ZafDatItm(objParSis, jifCnt);  /* JM: (19/Feb/2018) Modificacion obligatoria del nombre del item  */
            System.out.println(strVer);
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        catch(Exception  e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        } 
    }      
    

    /**
     * Clase creada para generar factura
     * 
     * @param conn
     * @param intCodSeg
     * @return 
     */
        

    public boolean iniciarProcesoGeneraFactura(java.sql.Connection conExt, int intCodSeg){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(consultarCotizacionesParaFacturar(conExt,intCodSeg)){
                    blnRes=true;
                }
                else{
                    blnRes=false;
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }
    
    /**
     * Proceso de facturacion parcial.
     * Llega el codigo de la cotizacion directamente que se desea facturar
     * JM 3/Oct/2018
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    
    public boolean InciaProcesoGeneraFactura(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(consultarCotizacionParaFacturar(conExt,CodEmp, CodLoc, CodCot)){
                    blnRes=true;
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;        
    }
    
    
    
    private boolean consultarCotizacionParaFacturar(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        stbDocRelEmpRem = new StringBuffer();
        arlDocGenDat = new ArrayList();
        objZafGenOD = new GenOD.ZafGenOdPryTra() ;
        objModDatGenFac = new Librerias.ZafGenFacAut.ZafModDatGenFac(objParSis, jifCnt);
        objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
        objCnfDoc = new Librerias.ZafCnfDoc.ZafCnfDoc(objParSis,null);
        objCfgSer.cargaDatosIpHostServicios(0, intCodSer);                   
        String strMerIngEgr="",strTipIngEgr="";
        blnIsComSol = false;
        int intCodSeg=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a5.co_seg, a3.tx_corEle, \n";
                strSql+="        a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot, a1.co_cli, a1.co_ven, a1.tx_ate, a1.co_forpag, \n";
                strSql+="        a1.nd_sub, a1.nd_poriva, a1.nd_valdes, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing,  \n";
                strSql+="        a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.nd_tot, a1.ne_val, a1.nd_valiva, a1.tx_numped,  \n";
                strSql+="        a1.st_regrep, a1.tx_obssolaut, a1.tx_obsautsol, a1.st_aut, a1.tx_nomcli, a1.fe_procon,  \n";
                strSql+="        a1.co_locrelsoldevven, a1.co_tipdocrelsoldevven, a1.co_docrelsoldevven, \n";
                strSql+="        a1.st_docconmersaldemdebfac, a1.fe_val, a1.co_tipcre, a1.tx_dirclifac, a1.tx_dircliguirem, \n";
                strSql+="         a1.co_forret, a1.tx_vehret, a1.tx_choret, \n";
                strSql+="        CASE WHEN a1.tx_momgenfac IS NULL THEN 'M' ELSE a1.tx_momgenfac END as tx_momgenfac,  \n";
                strSql+="        a1.nd_valComSol,a1.nd_subIvaCer,a1.nd_subIvaGra,a1.nd_porComSol, a1.st_solFacPar, a1.tx_tipCot  \n";
                strSql+=" FROM tbm_cabCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_usr as a3 ON (a1.co_usrIng=a3.co_usr) \n";
                strSql+=" INNER JOIN tbr_cabCotVen as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cot=a4.co_cot)  \n";
                strSql+=" INNER JOIN tbm_cabSegMovInv as a5 ON (a4.co_empRel=a5.co_empRelCabCotVen AND a4.co_locRel=a5.co_locRelCabCotVen AND   \n";
                strSql+="                                       a4.co_cotRel=a5.co_cotRelCabCotVen ) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n"; 
                strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot \n";
                System.out.println("consultarCotizacionParaFacturar (2 Nuevo Metodo)... \n" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    CodEmpGenFac = rstLoc.getInt("co_emp"); intCodSeg = rstLoc.getInt("co_seg");
                    obtenerDatosFactura(conExt, rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"),rstLoc.getInt("co_cot"));
                    objZafCtaCtb_dat = new ZafCtaCtb_dat(objParSis,rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle);
                    cargarTipEmp(conExt, rstLoc);
                    Configurartabla();
                    configurarTablaPago();
                    refrescaDatos2(conExt,rstLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"));
                    CalculoPago(conExt, rstLoc); // TOTALES CARGADO????? 
                    CalculoPago2(conExt, rstLoc);
                    FormaRetencion(conExt, rstLoc.getInt("co_emp"));
                    stmLoc01=conExt.createStatement();
                    strSql = "SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp=" + rstLoc.getInt("co_emp") + " "
                            + " and co_loc="+rstLoc.getInt("co_loc")+" and co_tipDoc=" + intCodTipDocFacEle;
                    rstLoc01 = stmLoc01.executeQuery(strSql);
                    if (rstLoc01.next()) {
                        strMerIngEgr = rstLoc01.getString("st_meringegrfisbod");
                        strTipIngEgr = rstLoc01.getString("tx_natdoc");
                    }
                    rstLoc01.close();
                    rstLoc01=null;
                    stmLoc01.close();
                    stmLoc01=null;
                    
                    if(insertarCabFac(conExt,rstLoc)){
                       if (insertarDetFac(conExt,rstLoc,intCodSeg)) {
                           calculaPag(conExt, rstLoc);
                           if (insertarPagFac(conExt,rstLoc, intCodTipDocFacEle, intCodDoc)) {
                               if(insertarDiario(conExt,rstLoc, intCodTipDocFacEle, intCodDoc)) {
                                   if (objInvItm._getExiItmSer(conExt, rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)) {
                                       if(asignaNumeroFac(conExt, rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc, rstLoc.getInt("co_cot") )){
                                           if(insertarTablaSeguimiento(conExt,rstLoc,intCodTipDocFacEle,intCodDoc)){
                                               if(prepararEgreso(conExt,rstLoc,rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)  ){
                                                       System.err.println("<----- antes costear ----->");
                                                       if(objUti.costearDocumento(jifCnt, objParSis, conExt, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
                                                           System.err.println("<----- despues costear ----->");
                                                           if(revisarInvBodNegativos(conExt, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
                                                               System.out.println("ANTES OD ");
                                                               if(objZafGenOD.generarODLocal(conExt, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc,true)){
                                                                   if(objDatItm.preLiberarItems(conExt, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"))){
                                                                        blnRes=true;
                                                                        guardaDocumentosGenerado(rstLoc,intCodTipDocFacEle,intNumFacElec,intCodDoc,rstLoc.getString("tx_corEle"));  // PARA EL CORREO ELECTRONICO
                                                                        System.out.println("GENERO: "+rstLoc.getInt("co_emp")+" - "+rstLoc.getInt("co_loc")+" - "+rstLoc.getInt("co_cot")+"FACTURA: "+intCodTipDocFacEle+"-"+intCodDoc);
                                                                   }else{blnRes=false;}
                                                               }else{blnRes=false;}
                                                           }else{blnRes=false;}
                                                       }else{System.out.println("costeo Error"); blnRes=false; }
                                               }else{blnRes=false;}
                                           }else{blnRes=false;}
                                       }else{blnRes=false;}
                                   }else{blnRes=false;}
                               }else{blnRes=false;}
                           }else{blnRes=false;}
                       }else{blnRes=false;}
                   }else{blnRes=false;}
                    
                    if(blnRes){
                        enviaCorreosElectronicos();
                        enviarPulsoFacturacionElectronica();
                        System.err.println("GUARDA!!!!  ");
                    }
                    else{
                        System.err.println("ERROR!!!!  ");
                    } 
                    
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;   
    }
    
    


    /**
     * Carga driver de conceccion a base.
     * @param driver
     * @return
     */
    private boolean cargarDrv_F1(String driver){
        boolean blnRes=true;
        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e){
            blnRes=false;
             
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return blnRes;
    }

    private StringBuffer stbDocRelEmpRem;
    private int CodEmpGenFac=-1;

    /**
     * A=Activo;I=Anulado;E=Pendiente por facturar;F=Facturada;R=Rechazada al facturar;P=Pendiente por autorizar;U=Autorizado;D=Denegado
     * 
     * 
     * 
     * @return 
     */
    private boolean consultarCotizacionesParaFacturar(java.sql.Connection conn, int intCodSeg){
        boolean blnRes=false;
        java.sql.Statement stmLoc,stmLoc01;
        stbDocRelEmpRem = new StringBuffer();
        arlDocGenDat = new ArrayList();
        objZafGenOD = new GenOD.ZafGenOdPryTra() ;
        objModDatGenFac = new Librerias.ZafGenFacAut.ZafModDatGenFac(objParSis, jifCnt);
        objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
        objCnfDoc = new Librerias.ZafCnfDoc.ZafCnfDoc(objParSis,null);
        objCfgSer.cargaDatosIpHostServicios(0, intCodSer);
        java.sql.ResultSet rstLoc,rstLoc01;
        String strMerIngEgr="",strTipIngEgr="";
        blnIsComSol = false;
        try{
            stmLoc=conn.createStatement();
            strSql="";
            strSql+=" SELECT a2.co_seg, a3.tx_corEle, \n";
            strSql+="        a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot, a1.co_cli, a1.co_ven, a1.tx_ate, a1.co_forpag, \n";
            strSql+="        a1.nd_sub, a1.nd_poriva, a1.nd_valdes, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing,  \n";
            strSql+="        a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.nd_tot, a1.ne_val, a1.nd_valiva, a1.tx_numped,  \n";
            strSql+="        a1.st_regrep, a1.tx_obssolaut, a1.tx_obsautsol, a1.st_aut, a1.tx_nomcli, a1.fe_procon,  \n";
            strSql+="        a1.co_locrelsoldevven, a1.co_tipdocrelsoldevven, a1.co_docrelsoldevven, \n";
            strSql+="        a1.st_docconmersaldemdebfac, a1.fe_val, a1.co_tipcre, a1.tx_dirclifac, a1.tx_dircliguirem, \n";
            strSql+="         a1.co_forret, a1.tx_vehret, a1.tx_choret, \n";
            strSql+="        CASE WHEN a1.tx_momgenfac IS NULL THEN 'M' ELSE a1.tx_momgenfac END as tx_momgenfac,  \n";
            strSql+="        a1.nd_valComSol,a1.nd_subIvaCer,a1.nd_subIvaGra,a1.nd_porComSol,a1.st_solFacPar, a1.tx_tipCot  \n";
            strSql+=" FROM tbm_cabCotVen as a1  \n";
            strSql+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND \n";
            strSql+="                                       a1.co_cot=a2.co_cotRelCabCotVen) \n";
            strSql+=" INNER JOIN tbm_usr as a3 ON (a1.co_usrIng=a3.co_usr) \n";
            strSql+=" WHERE a2.co_seg="+intCodSeg+" AND a2.co_empRelCabCotVen IS NOT NULL  AND a1.st_reg!='F'     \n"; /* LISTO PARA FACTURAR */
            strSql+="       AND a1.st_autSolResInv IS NULL  ";
            strSql+=" ORDER BY a1.co_emp, a1.co_loc \n";
            System.out.println("consultarCotizacionesParaFacturar... \n" + strSql);
            rstLoc=stmLoc.executeQuery(strSql);
            while(rstLoc.next()){
                CodEmpGenFac = rstLoc.getInt("co_emp");
                if(rstLoc.getInt("co_emp")==2 && rstLoc.getInt("co_loc")==4){
                    blnIsComSol=true;
                }
//                    dblPorComSol=objUti.redondear(rstLoc.getDouble("nd_porComSol"), objParSis.getDecimalesMostrar());
//                    dblBaseIva=objUti.redondear(rstLoc.getDouble("nd_subIvaGra"), objParSis.getDecimalesMostrar());

                obtenerDatosFactura(conn, rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"),rstLoc.getInt("co_cot"));
                objZafCtaCtb_dat = new ZafCtaCtb_dat(objParSis,rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle);
                cargarTipEmp(conn, rstLoc);
                Configurartabla();
                configurarTablaPago();
                refrescaDatos2(conn,rstLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"));
                CalculoPago(conn, rstLoc); // TOTALES CARGADO????? 
                CalculoPago2(conn, rstLoc);
//                    refrescaDatos2(conn,rstLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"));
                FormaRetencion(conn, rstLoc.getInt("co_emp"));
                stmLoc01=conn.createStatement();
                strSql = "SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp=" + rstLoc.getInt("co_emp") + " "
                        + " and co_loc="+rstLoc.getInt("co_loc")+" and co_tipDoc=" + intCodTipDocFacEle;
                rstLoc01 = stmLoc01.executeQuery(strSql);
                if (rstLoc01.next()) {
                    strMerIngEgr = rstLoc01.getString("st_meringegrfisbod");
                    strTipIngEgr = rstLoc01.getString("tx_natdoc");
                }
                rstLoc01.close();
                rstLoc01=null;
                stmLoc01.close();
                stmLoc01=null;
                //dblPorComSol=0.00,dblBaseIva=0.00;

                System.out.println("st_reg= " + rstLoc.getString("st_reg").toString());

                if(rstLoc.getString("st_reg").equals("L") && (rstLoc.getString("tx_momGenFac").equals("F") || rstLoc.getString("tx_momGenFac").equals("M")  )){  // INMACONSA SE FACTURA POR AKI
                    if(insertarCabFac(conn,rstLoc)){
                        if (insertarDetFac(conn,rstLoc,intCodSeg)) {
                            calculaPag(conn, rstLoc);
                            if (insertarPagFac(conn,rstLoc, intCodTipDocFacEle, intCodDoc)) {
                                if(insertarDiario(conn,rstLoc, intCodTipDocFacEle, intCodDoc)) {
                                    if (objInvItm._getExiItmSer(conn, rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)) {
                                        if(asignaNumeroFac(conn, rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc, rstLoc.getInt("co_cot") )){
                                            if(insertarTablaSeguimiento(conn,rstLoc,intCodTipDocFacEle,intCodDoc)){
                                                if(prepararEgreso(conn,rstLoc,rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)  ){
//                                                    if(objModDatGenFac.cuadraStockSegunMovimientos(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
                                                        System.err.println("<----- antes costear ----->");
                                                        if(objUti.costearDocumento(jifCnt, objParSis, conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
                                                            System.err.println("<----- despues costear ----->");
                                                            if(revisarInvBodNegativos(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
                                                                System.out.println("ANTES OD ");
                                                                if(objZafGenOD.generarODLocal(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc,true)){
                                                                    if(objDatItm.preLiberarItems(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"))){
                                                                        blnRes=true;
                                                                        guardaDocumentosGenerado(rstLoc,intCodTipDocFacEle,intNumFacElec,intCodDoc,rstLoc.getString("tx_corEle"));  // PARA EL CORREO ELECTRONICO
                                                                        System.out.println("GENERO: "+rstLoc.getInt("co_emp")+" - "+rstLoc.getInt("co_loc")+" - "+rstLoc.getInt("co_cot")+"FACTURA: "+intCodTipDocFacEle+"-"+intCodDoc);
                                                                    }else{blnRes=false;}
                                                                }else{blnRes=false;}
                                                            }else{blnRes=false;}
                                                        }else{System.out.println("costeo Error"); blnRes=false; }
//                                                    }else{ System.out.println("cuadraStockSegunMovimientos Error"); blnRes = false;}
                                                }else{blnRes=false;}
                                            }else{blnRes=false;}
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{blnRes=false;}

                    if(blnRes==false){
                        objCorEle.enviarCorreoMasivo(strCorEleTo,"ERROR.... ","Revisar Seguimiento: "+rstLoc.getInt("co_seg")+"cotizacion Emp:"+rstLoc.getInt("co_emp")+" Loc:"+rstLoc.getInt("co_loc")+" Cot:" + rstLoc.getInt("co_cot"));
                    }
                }
                else if(rstLoc.getString("tx_momGenFac").equals("P")){
//                        if(obtenerFacturaSeguimiento(conn,intCodSeg)){
//                            if(actualizarDetFac(conn,rstLoc)){
//                                if(prepararEgreso(conn,rstLoc,rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)  ){
//                                    if(!objZafGenOD.validarODExs(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
//                                        if(objZafGenOD.generarNumODFacIni(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
//                                            if(revisarInvBodNegativos(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), intCodTipDocFacEle, intCodDoc)){
//                                                blnRes=true;
//                                            }else{blnRes=false;}
//                                        }else{blnRes=false;}
//                                    }else{ blnRes=true; }
//                                }
//                            }
//                        }
                    blnRes=false;
                }
                else{
                    objCorEle.enviarCorreoMasivo(strCorEleTo, "FACTURA AUTOMATICA ESTADO INCORRECTO.... ","Revisar Seguimiento: "+rstLoc.getInt("co_seg"));
                }




                if(blnRes==false){
                    System.out.println("FALLO!!! "+rstLoc.getInt("co_emp")+" - "+rstLoc.getInt("co_loc")+" - "+rstLoc.getInt("co_cot"));
                }
                objZafCtaCtb_dat = null;
            }
            if(blnRes){
                enviaCorreosElectronicos();
//                    enviaImprimirOdLocal(conn);
                enviarPulsoFacturacionElectronica();
                System.err.println("GUARDA!!!!  ");
            }
            else{
                System.err.println("ERROR!!!!  ");
            }
                
            stmLoc.close();
            stmLoc=null;
            rstLoc.close();
            rstLoc=null;
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
    
        
        
         private boolean modificaPagoCompensacion(java.sql.Connection conExt,int CodEmp, int CodLoc,int CodTipDoc,int CodDoc){
             boolean blnRes=false;
            java.sql.Statement stmLoc;
            String strCadena;
            try{
                if(conExt!=null){
                    stmLoc = conExt.createStatement();
                    strCadena = "";
                    strCadena+="UPDATE tbm_pagMovInv SET nd_abo=(mo_pag*-1) WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" ";
                    strCadena+=" AND tx_tipReg='S' ";
                    System.out.println("modificaPagoCompensacion: " + strCadena);
                    stmLoc.executeUpdate(strCadena);
                    stmLoc.close();
                    stmLoc=null;
                    blnRes=true;
                }
            }
            catch(java.sql.SQLException Evt){ 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            catch(Exception Evt){ 
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                blnRes=false;
            }
            return blnRes;
        }
        
         /**
          * 
          * @param conExt
          * @param intCodEmp
          * @param intCodLoc
          * @param intCodTipDoc
          * @param intCodDoc
          * @return 
          */
        
    private boolean revisarInvBodNegativos(java.sql.Connection conExt,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_bod, a1.co_itm,a1.tx_codAlt, a1.nd_can , a3.nd_stkAct, a3.nd_canDis \n";
                strSql+=" FROM tbm_detMovInv AS a1 \n";
                strSql+=" INNER JOIN tbm_inv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql+=" INNER JOIN tbm_invBod AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a1.co_itm=a3.co_itm) \n";
                strSql+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_tipDoc="+intCodTipDoc+" AND a1.co_doc="+intCodDoc+" \n";
                strSql+="       AND a2.st_ser='N' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S') AND ( a3.nd_stkAct<0   OR a3.nd_canDis<0  ) ";
                System.out.println("revisarInvBodNegativos ::: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    objCorEle.enviarCorreoMasivo(strCorEleTo,strTitCorEle + "NO SE GENERO... GENERARIA NEGATIVOS PILAS!!!  ",rstLoc.getString("tx_codAlt")+"  --  "+strSql );
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
         return blnRes;
    }
        
        
        
   // José Marín M. 6/Nov/2014 // Facturación Electronica
   
    private void enviarPulsoFacturacionElectronica() {
        objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciar();
        System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica  ");
    }
        
    
    private boolean obtenerFacturaSeguimiento(java.sql.Connection conn,int intCodSeg){
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                strCadena="";
                strCadena+=" SELECT  a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a2.ne_numDoc  \n";
                strCadena+=" FROM tbm_cabSegMovInv AS a1 \n";
                strCadena+=" INNER JOIN tbm_cabMovInv AS a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND \n";
                strCadena+="                                     a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                strCadena+=" INNER JOIN tbm_cabCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.ne_numCot=a3.co_cot) \n";
                strCadena+=" WHERE a1.co_seg="+intCodSeg+"  AND \n";
                strCadena+="       a2.co_tipDoc=228 AND a3.tx_momGenFac='P' ";
                System.out.println("obtenerFacturaSeguimiento " + strCadena);
                rstLoc = stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    intCodTipDocFacEle = rstLoc.getInt("co_tipDoc");
                    intCodDoc = rstLoc.getInt("co_doc");
                    intNumFacElec = rstLoc.getInt("ne_numDoc");
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            
        }
        catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
        

    /**
     * Para que lo llamen 
     * @param conn
     * @param intCodSeg
     * @return 
     */
    
    public String obtenerFacturaSeguimientoInventario(java.sql.Connection conn,int intCodSeg){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena="";
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                strCadena="";
                strCadena+=" SELECT  a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc  \n";
                strCadena+=" FROM tbm_cabSegMovInv AS a1 \n";
                strCadena+=" INNER JOIN tbm_cabMovInv AS a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND \n";
                strCadena+="                                     a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                strCadena+=" INNER JOIN tbm_cabCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.ne_numCot=a3.co_cot) \n";
                strCadena+=" WHERE a1.co_seg="+intCodSeg+"  AND \n";
                strCadena+="       a2.co_tipDoc=228 ";
                rstLoc = stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    strCadena="";
                    strCadena+=rstLoc.getInt("co_emp")+"-";
                    strCadena+=rstLoc.getInt("co_loc")+"-";
                    strCadena+=rstLoc.getInt("co_tipDoc")+"-";
                    strCadena+=rstLoc.getInt("co_doc");
                }
                else{
                    strCadena="";
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return strCadena;
    }
    
    /**
     * Nuevo metodo JM, para obtener el vendedor asignado a cada cliente
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCli
     * @return 
     */
    
    private int intCodVenAsig=-1;
    private String strNomVenAsig="";
    
    private boolean getVendedorAsignado(int CodEmp, int CodLoc, int CodCli){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());; 
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_cli, a1.tx_nom as tx_nomCli, a3.co_usr, a3.tx_nom as tx_nomVen  \n";
                strSQL+=" FROM tbm_cli as a1  \n";
                strSQL+=" INNER JOIN tbr_cliLoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSQL+=" INNER JOIN tbm_usr as a3 ON (a2.co_ven=a3.co_usr) \n";
                strSQL+=" WHERE a2.co_emp="+CodEmp+" and a2.co_loc="+CodLoc+" AND a1.co_cli="+CodCli+" \n";
                System.out.println("getVendedorAsignado: "+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodVenAsig= rstLoc.getInt("co_usr");
                    strNomVenAsig=rstLoc.getString("tx_nomVen");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                blnRes=true;
            }
        }
        catch (SQLException Evt) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
             
        }
        catch (Exception Evt) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
    
        private boolean insertarCabFac(java.sql.Connection conn,java.sql.ResultSet rstExt) {
            boolean blnRes = false;
            java.sql.Statement stmLoc,  stmLocIns;
            java.sql.ResultSet rstLoc;
            String  strSqlIns = "";
            String strMerIngEgr,strTipIngEgr;
            int intSecGrp = 0;
            int intSecEmp = 0;
            String strFecSem = "";
            try {
              //  System.out.println("insertarCabFac");
                stmLoc = conn.createStatement();
                datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecDoc == null) {
                    return false;
                }
                strFecSistema = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
                strFecModCot = strFecSistema;

                datFecAuxGuia = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAuxGuia == null) {
                    return false;
                }
                /**
                * ********************** OBTIENE MAX CODIGO DE CABMOVINVV ****************************************
                * 
                */
                strSql =" SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc ";
                strSql+=" FROM tbm_cabMovInv ";
                strSql+=" WHERE co_emp="+rstExt.getString("co_emp")+" AND co_loc="+rstExt.getString("co_loc")+" AND co_tipDoc="+intCodTipDocFacEle;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodDoc=rstLoc.getInt("co_doc");
                }        

               
                strSql = "SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp=" + rstExt.getString("co_emp") + " "
                        + " and co_loc=" + rstExt.getString("co_emp") + " and co_tipDoc=" + intCodTipDocFacEle;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    strMerIngEgr = rstLoc.getString("st_meringegrfisbod");
                    strTipIngEgr = rstLoc.getString("tx_natdoc");
                }
                rstLoc.close();
                rstLoc = null;
                
                intSecEmp = objUltDocPrint.getNumSecDoc(conn, rstExt.getInt("co_emp"));
                intSecGrp = objUltDocPrint.getNumSecDoc(conn, objParSis.getCodigoEmpresaGrupo());
               
                
                if(!getVendedorAsignado(rstExt.getInt("co_emp"), rstExt.getInt("co_loc"), rstExt.getInt("co_cli") )){
                    System.out.println("Error vendedor asignado error");
                    return false;
                }
                
                strSqlIns = "INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, ";
                strSqlIns+="             tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, ";
                strSqlIns+="             tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, tx_comIng, fe_ing, co_usrIng, fe_ultMod, ";
                strSqlIns+="             co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu";
                strSqlIns+="             ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem,tx_tipMov,st_genOrdDes, ";
                strSqlIns+="             nd_subIvaCer,nd_subIvaGra,nd_porComSol,nd_valComSol ";  /* JoseMario 31/Ene/2017 */
                if (intTipForPag2 == 4) {
                    strSqlIns += " , co_tipCre ";
                }
                strSqlIns += " ) ";
                strSqlIns+=" VALUES(" + rstExt.getString("co_emp") + ", " + rstExt.getString("co_loc") + ", ";
                strSqlIns+= intCodTipDocFacEle + ", " + intCodDoc + ", '" + datFecDoc + "', " + rstExt.getString("co_cli") + " ,"+intCodVenAsig+",null,";
                strSqlIns+= objUti.codificar(strNomCli) + ","+objUti.codificar(rstExt.getString("tx_dirCliFac"))+ ",'" + strIdeCli + "','" + strCliTelefono + "','";
                strSqlIns+= strCliCiudad + "','" + strNomVenAsig + "',0 ,"+rstExt.getInt("co_cot")+ ",";
                strSqlIns+= "  "+objUti.codificar(rstExt.getString("tx_obs1"))+" ,"+objUti.codificar(rstExt.getString("tx_obs2") + " - " + strVer)+ "," + dblSubtotalCot * -1 + " ," + dblPorIva + " ," + dblTotalCot * -1 + ", ";
                strSqlIns+= dblIvaCot * -1 + " , " + rstExt.getInt("co_forPag") + ",'"+strNomForPag+"',"+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+",'" + strFecSistema + "', ";
                strSqlIns+= rstExt.getString("co_usrIng") + ", '" + strFecSistema + "',/*USUARIO_INSERT*/ null , "+rstExt.getString("co_forRet")+ ", '"+ rstExt.getString("tx_vehRet") + "',";
                strSqlIns+= " "+objUti.codificar(rstExt.getString("tx_choret"))+", 'A', " + intSecGrp + ", " + intSecEmp + ", '" + rstExt.getString("tx_numPed") + "', 'I' ,'C' , 'N', " + intCodMnuFac + " ";
                strSqlIns+= " ,'S', 'N', null ,'S','E','S' ";
                
                strSqlIns += ","+(dblBaseCero*-1)+","+(dblBaseIva*-1)+","+dblPorComSol+",";
                 strSqlIns += dblComSol;
                if (intTipForPag2 == 4) {
                    strSqlIns += " ," + intCodTarCre + " ";
                }
               
                strSqlIns += " );";
                
                strSql = "  UPDATE tbm_cabcotven SET st_reg='F' WHERE co_emp=" + rstExt.getString("co_emp") + " "  // FACTURADO!!! xD
                        + " AND co_loc=" +rstExt.getString("co_loc")+ " AND co_cot=" + rstExt.getInt("co_cot");
                stmLoc.executeUpdate(strSql);
                
                strSql = "SELECT a.co_locrelsoldevven,  a.co_tipdocrelsoldevven, a.co_docrelsoldevven "
                        + ", a1.co_locrel, a1.co_tipdocrel, a1.co_docrel , a2.co_locrel as colococ, a2.co_tipdocrel as cotipdococ, a2.co_docrel as codococ "
                        + " FROM "
                        + " tbm_cabcotven as a "
                        + " INNER JOIN tbm_cabsoldevven AS a1 ON(a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelsoldevven AND "
                        + " a1.co_tipdoc=a.co_tipdocrelsoldevven AND a1.co_doc=a.co_docrelsoldevven ) "
                        + " left join tbr_cabmovinv as a2 ON(a2.co_emp=a.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_tipdocrel=2 ) "
                        + " WHERE a.co_emp=" + rstExt.getString("co_emp") + " and a.co_loc=" +rstExt.getString("co_loc")+ " and a.co_cot=" +rstExt.getInt("co_cot");

                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    strSqlIns += " ; INSERT INTO tbr_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, st_regrep) "
                            + " VALUES(" + rstExt.getString("co_emp") + "," +rstExt.getString("co_loc")+ ", " + intCodTipDocFacEle + ", " + intCodDoc + ", 'A', "
                            + " " + rstExt.getString("co_emp") + ", " + rstLoc.getString("co_locrel") + ", " + rstLoc.getString("co_tipdocrel") + ", " + rstLoc.getString("co_docrel") + ", 'I' )";
                  
                    if (!(rstLoc.getString("colococ") == null)) {
                        if (!(rstLoc.getString("colococ").toString().equals(""))) {
                            strSqlIns += " ; INSERT INTO tbr_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, st_regrep) "
                                    + " VALUES(" +rstExt.getString("co_emp")+ "," +rstExt.getString("co_loc")+ ", " + intCodTipDocFacEle + ", " + intCodDoc + ", 'A', "
                                    + " " +rstExt.getString("co_emp")+ ", " + rstLoc.getString("colococ") + ", " + rstLoc.getString("cotipdococ") + ", " + rstLoc.getString("codococ") + ", 'I' )";
                        }
                    }
                }
                System.out.println("insertarCabFac " + strSqlIns);
                stmLocIns = conn.createStatement();
                stmLocIns.executeUpdate(strSqlIns);
                stmLocIns.close();
                stmLocIns = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                blnRes = true;
            } 
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } 
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
        }


    
        
         /**
         * Verifica si todos los items son de tranporte
         *
         * @param conn
         * @param intCodEmp
         * @return true todos son de servicio false hay un item que no es de
         * servicio
         */
        private boolean _getVerificaTodItmTrans(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodCot) {
            boolean blnRes = true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strCadena;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strCadena="";
                    strCadena+=" SELECT a2.* ";
                    strCadena+=" FROM tbm_cabCotVen as a1 ";
                    strCadena+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND";
                    strCadena+="                                    a1.co_cot=a2.co_cot)";
                    strCadena+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.co_cot="+intCodCot;
                    strCadena+=" ";
                    rstLoc = stmLoc.executeQuery(strCadena);
                    while(rstLoc.next()) {
                        if (!_getEstItmTrans(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_itm"))) {
                            blnRes = false;
                            break;
                        }
                    }
                }
            } 
            catch (Exception e) {
                blnRes = false;
                System.err.println("ERROR " + e.toString());
                objUti.mostrarMsgErr_F1(jifCnt, e);
            }
            return blnRes;
        }
        
                /**
         * Verifica si el item es de servicio
         *
         * @param conn
         * @param intCodEmp
         * @param intCodItm
         * @return true es de serivio false no es de servicio
         */
        private boolean _getEstItmTrans(java.sql.Connection conn, int intCodEmp, int intCodItm) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strCadena = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strCadena = "SELECT st_ser FROM tbm_inv WHERE co_emp=" + intCodEmp + " AND co_itm=" + intCodItm + " AND st_ser='T'";
                    rstLoc = stmLoc.executeQuery(strCadena);
                    if (rstLoc.next()) {
                        blnRes = true;
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } catch (java.sql.SQLException ex) {
                objUti.mostrarMsgErr_F1(jifCnt, ex);
                System.err.println("ERROR " + ex.toString());
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(jifCnt, e);
                System.err.println("ERROR " + e.toString());
            }
            return blnRes;
        }
        
        private String strNomForPag;
        private int intCodTarCre;
        
        private void obtenerDatosFactura(java.sql.Connection conn,int intCodEmp,int intCodLoc, int intCodCot ){
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strCadena = "";
            try{
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strCadena= " SELECT CotCab.co_locrelsoldevven,  CotCab.co_tipdocrelsoldevven, CotCab.co_docrelsoldevven, \n";
                    strCadena+="        CotCab.fe_procon,  CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, \n"; //<== Campos con los datos del CLiente para la cabecera
                    strCadena+="        Usr.co_usr as co_ven, Usr.tx_nom as nomcom, \n"; //<==Campos con los datos del Vendedor para poner en cabecera
                    strCadena+="        CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  \n"; //<==Campos que aparecen en la parte superior del 1er Tab
                    strCadena+="        CotCab.co_forPag,CotCab.co_tipCre, \n"; //<==Campos que aparecen en la parte superior del 2do Tab // TC
                    strCadena+="        CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, \n"; //<==Campos que aparecen en la parte Inferior del 1er Tab
                    strCadena+="        CotCab.st_reg , forpag.tx_des \n"; // NOMBRE FORMA DE PAGO
                    strCadena+="        ,CotCab.ne_val , CotCab.st_regrep  ,Cli.tx_tipper, Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar  \n";
                    strCadena+="        ,Cli.co_tipper , CotCab.tx_numped ,forpag.ne_tipforpag , forpag.nd_pes , forpagCli.ne_tipforpag as ne_tipforpagcli , forpagCli.nd_pes as nd_pescli   \n";
                    strCadena+="        ,Cli.ne_Diagra, Cli.st_ivaven, CotCab.tx_nomcli, cli.st_peringnomclicotven  \n";
                    strCadena+=" FROM tbm_cabCotven as CotCab   \n";
                    strCadena+=" LEFT OUTER JOIN  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) \n";
                    strCadena+=" LEFT OUTER JOIN tbm_cabForPag as forpagCli on (forpagCli.co_emp=Cli.co_emp and forpagCli.co_forpag = Cli.co_forPag )  \n";
                    strCadena+=" LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  \n";
                    strCadena+=" left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) \n"; // Tablas enlas cuales se trabajara y sus respectivos alias
                    strCadena+=" left outer join tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  \n";
                    strCadena+=" Where CotCab.co_emp = " +intCodEmp+" \n";
                    strCadena+="       and CotCab.co_loc = "+intCodLoc+"  /*and CotCab.st_reg not in ('E')*/ \n";
                    strCadena+=" AND  CotCab.co_cot= " +intCodCot+ "  \n ORDER BY CotCab.co_cot";
                    System.out.println("obtenerDatosFactura... \n\n " + strCadena);
                    rstLoc = stmLoc.executeQuery(strCadena);
                    if (rstLoc.next()) {
                        
                        strCodLocSol = rstLoc.getString("co_locrelsoldevven");
                        strCodTipDocSol = rstLoc.getString("co_tipdocrelsoldevven");
                        strCodDocSol = rstLoc.getString("co_docrelsoldevven");
                        
                        intCodCli = rstLoc.getInt("co_cli");
                        strIdeCli=(rstLoc.getString("tx_ide"));
                        strNomCli=(rstLoc.getString("nomcli") == null) ? "" : rstLoc.getString("nomcli");
                        strCliDir=(((rstLoc.getString("dircli") == null) ? "" : rstLoc.getString("dircli")));
                        strCliDirGui=(((rstLoc.getString("dircli") == null) ? "" : rstLoc.getString("dircli")));
                        strCliTelefono=(rstLoc.getString("tx_tel"));
                        strCliIdentificacion=(rstLoc.getString("tx_ide"));
                        strCliCiudad=(rstLoc.getString("tx_desLar"));
                        
                        intCodVen = rstLoc.getInt("co_ven");
                        strNomVen = rstLoc.getString("nomcom")==null?"":rstLoc.getString("nomcom");
                        
                        //masInformacion(false);
                        strNumPed=(rstLoc.getString("tx_numped"));
                        Glo_dlbMaxDes = rstLoc.getDouble("nd_maxdes");
                        Glo_dlbMarUti = rstLoc.getDouble("nd_maruti");
                        strCodTipPerCli = rstLoc.getString("co_tipper");
                        intTipForPag = Integer.parseInt(rstLoc.getString("ne_tipforpagcli"));
                        dblPes = Double.parseDouble(rstLoc.getString("nd_pescli"));
                        dblDiaGraCli = Double.parseDouble(((rstLoc.getString("ne_diagra") == null) ? "0" : rstLoc.getString("ne_diagra")));
                        
                        intTipForPag2 = Integer.parseInt(rstLoc.getString("ne_tipforpag"));
                        strNomForPag=rstLoc.getString("tx_des");
                        
                        dblPes2 = Double.parseDouble(((rstLoc.getString("nd_pes") == null) ? "0" : rstLoc.getString("nd_pes")));
                        intNumDiaVal = rstLoc.getInt("ne_val");
                        strDateFecCot = objUti.formatearFecha(rstLoc.getDate("fe_cot"), "dd/MM/yyyy");
                        intTipMod = 2;
                    }
                    
                    if(intTipForPag2==4){
                        //Tarjeta de credito
                        intCodTarCre=rstLoc.getInt("co_tipCre");
                    }
                    strCadena="";
                    strCadena+=" SELECT a1.*, a2.tx_nom FROM tbr_bodLoc as a1 "
                            + " INNER JOIN tbm_bod as a2 ON (a1.co_emp=a2.co_emp and a1.co_bod=a2.co_bod) "
                            + "WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc=" +intCodLoc+" AND a1.st_reg = 'P'";
                    rstLoc = stmLoc.executeQuery(strCadena);
                    if (rstLoc.next()) {
                        intCodBodPre=rstLoc.getInt("co_bod");
                        strNomBodPtaPar=rstLoc.getString("tx_nom");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            }
            catch (java.sql.SQLException ex) {
                objUti.mostrarMsgErr_F1(jifCnt, ex);
                System.err.println("ERROR " + ex.toString());
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(jifCnt, e);
                System.err.println("ERROR " + e.toString());
            }
        }
        
       private String GLO_strArreItm = "";
        /*       
          
         * USO DE LA FUNCION OBJUTI.CODIFICAR
         * 
         */
        private boolean insertarDetFac(Connection conn,java.sql.ResultSet rstExt, int intCodSeg) {
            boolean blnRes = false;
            boolean blnActPreDes = false;
            Statement stmLoc, stmLocIns, stmLoc01;
            String strSqlIns = "", strUpdateFacPar="";
            String str_MerIEFisBod = "S",strMerIngEgr="S";
            String strEstFisBod = "";
            int intCodItm = 0;
            int intCodBod = 0;
            int intEstIns = 0;
            int intEstInsGuia = 0;
            int intControl = 0;
            int intControlTbr = 0;
            int intColActPre = 0;
            double dlbCanMov = 0.00;
            double dlbcostouni = 0.00;
            double bldcostot = 0.00;
            double dbl_canConIE = 0.00;
            double dblCanPen=0.00, dblCanNunRec=0.00;  /* JM */
            boolean blnEstItmGenGuia = true; //  true = genera guia el item   false = no gerena guia el item
            String items[][] = null;
            double subtot = 0.00;
            //int cantReg=0;
            double canRegPend = 0, canItmPend = 0;
            java.sql.ResultSet rstDetCot,rstLoc01;
            int i=1;
             boolean blnIsSer=false;
            int intCodEmpCot,intCodLocCot,intCodCot,intCodRegCot,intCodItmCot,intCodBodCot,intCodPrv,intCodBodCom;
            String strCodAlt,strCodAlt2,strNomItm,strIvaVen,strTraAutTot,strEstSer,strDesUni,strStSer="";       
            double dblCanCot,dblColActPreCot,dblPorDesVenMax,dblPreUni,dblPorDes,dblPreCom,dblPorDesPreCom,dblCanEgrBod=0.00,dblCanTot;
            
            BigDecimal bgdCanItm;
            BigDecimal bgdPreItm;
            BigDecimal bgdValDesItm;
            BigDecimal bgdPorDesItm;
            BigDecimal bgdTotItm=BigDecimal.ZERO;
            
            try {
                stmLoc = conn.createStatement();
                stmLoc01 = conn.createStatement();
                StringBuffer stbins = new StringBuffer(); //VARIABLE TIPO BUFFER
               

                strSql="";
                strSql+=" SELECT a1.*,a2.st_ser, a3.tx_desCor as tx_desUni \n ";
                strSql+=" FROM tbm_detCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql+=" LEFT OUTER JOIN tbm_var as a3 ON (a2.co_uni=a3.co_reg)";
                strSql+=" WHERE a1.co_emp="+rstExt.getString("co_emp")+ " AND ";
                strSql+=" a1.co_loc="+rstExt.getString("co_loc")+" AND a1.co_cot="+rstExt.getString("co_cot");
                strSql+=" AND a1.nd_canPenFac > 0 ";
                System.out.println("insertarDetFac" + strSql);
                rstDetCot=stmLoc.executeQuery(strSql);
              
                while(rstDetCot.next()){
                    intCodEmpCot=rstDetCot.getInt("co_emp");intCodLocCot=rstDetCot.getInt("co_loc");
                    intCodCot=rstDetCot.getInt("co_cot");intCodRegCot=rstDetCot.getInt("co_reg");
                    intCodItmCot=rstDetCot.getInt("co_itm");strCodAlt=rstDetCot.getString("tx_codAlt");
                    strCodAlt2=rstDetCot.getString("tx_codAlt2");strNomItm=rstDetCot.getString("tx_nomItm");
                    intCodBodCot=rstDetCot.getInt("co_bod"); 
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */                    
                    //dblCanCot=rstDetCot.getDouble("nd_can");
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */
                    dblColActPreCot=rstDetCot.getDouble("nd_preUniVenLis");dblPorDesVenMax=rstDetCot.getDouble("nd_porDesVenMax");
                    dblPreUni=rstDetCot.getDouble("nd_preUni");dblPorDes=rstDetCot.getDouble("nd_porDes");
                    strIvaVen=rstDetCot.getString("st_ivaVen");dblPreCom=rstDetCot.getDouble("nd_preCom");
                    dblPorDesPreCom=rstDetCot.getDouble("nd_porDesPreCom");intCodPrv=rstDetCot.getInt("co_prv");
                    intCodBodCom=rstDetCot.getInt("co_bodCom");strTraAutTot=rstDetCot.getString("st_traAutTot");
                    strEstSer=rstDetCot.getString("st_ser");strDesUni=rstDetCot.getString("tx_desUni");
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */
//                    System.out.println("FACTURA PARCIAL estado"  + rstExt.getString("st_solFacPar") );
                    if(rstExt.getString("st_solFacPar")!=null){  
                        if(rstExt.getString("st_solFacPar").equals("S")){
//                            strSQL+=" detCot.nd_canFac, detCot.nd_canCan, detCot.nd_canPenFac  ";
                            dblCanCot= rstDetCot.getDouble("nd_canPenFac");
                            strUpdateFacPar+="; UPDATE tbm_detCotVen SET nd_canPenFac= (CASE WHEN nd_canPenFac IS NULL THEN 0 ELSE nd_canPenFac END - "+dblCanCot+"),  ";
                            strUpdateFacPar+="                           nd_canFac= ( CASE WHEN nd_canFac IS NULL THEN 0 ELSE nd_canFac END  + "+dblCanCot+" ) ";
                            strUpdateFacPar+=" WHERE co_emp="+rstExt.getString("co_emp")+" AND co_loc="+rstExt.getString("co_loc")+" AND ";
                            strUpdateFacPar+="       co_cot="+rstExt.getString("co_cot")+" AND co_reg="+rstDetCot.getInt("co_reg")+"  ;   "; 
                        }
                        else{
                            dblCanCot=rstDetCot.getDouble("nd_can");
                        }
                    }
                    else{
                        dblCanCot=rstDetCot.getDouble("nd_can");
                    }
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */
                    bgdCanItm=BigDecimal.valueOf(dblCanCot) ;
                    bgdPreItm=BigDecimal.valueOf(dblPreUni) ;
                    bgdPorDesItm=BigDecimal.valueOf(dblPorDes) ;
                    
                    //DESCUENTO
                    bgdValDesItm = bgdPorDesItm.multiply((bgdCanItm.multiply(bgdPreItm))); 
                     bgdValDesItm = bgdValDesItm.divide(new BigDecimal("100"),objParSis.getDecimalesBaseDatos(),BigDecimal.ROUND_HALF_UP);
                    ///TOTAL
                    bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)).subtract(bgdValDesItm), objParSis.getDecimalesMostrar());    
                    dblCanTot = bgdTotItm.doubleValue();
                    
                    //SUM(  ROUND(  ROUND( a1.nd_preUni* a1.nd_can,2)  - ROUND( (a1.nd_preUni* a1.nd_can)*a1.nd_porDes/100,2  )  ,2))  as subtotal
                    //dblCanTot=objUti.redondear( (dblPreUni*dblCanCot) - ((dblPreUni*dblCanCot) * (dblPorDes/100)),2 );  // JM 1/Feb/2017
                            
                    if(dblCanTot>0){  // SI ES POSITIVO
                        dblCanTot=dblCanTot*-1;
                    }
                    
                    blnEstItmGenGuia = true;
                    
                    intCodBod = intCodBodCot;
                    intCodItm = intCodItmCot;
                    dlbCanMov = dblCanCot;
                    //dbl_canConIE = dlbCanMov;  JM 8Jul2016
                    dblCanPen=dlbCanMov*-1;
                    //dblCanEgrBod=dlbCanMov*-1;

                    if (intControl != 0) {
                        GLO_strArreItm = GLO_strArreItm + ",";
                    }
                    GLO_strArreItm = GLO_strArreItm + String.valueOf(intCodItm);
                    intControl++;
                    /**
                     * ********* VERFIFICA SI EL ITEM IE MER FIS BOD  ***********************
                     */
                    strEstFisBod = (rstDetCot.getString("st_ser")== null ? "" : rstDetCot.getString("st_ser"));
                    str_MerIEFisBod = "N";
                    /**
                     * ********* VERFIFICA SI EL ITEM ES DE SERVICIO  ***********************
                     */
                    strSql = "SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE co_emp="+rstExt.getString("co_emp")+" "
                            + " and co_loc=" +rstExt.getString("co_loc")+ " and co_tipDoc=" + intCodTipDocFacEle;
                    rstLoc01 = stmLoc01.executeQuery(strSql);
                    if (rstLoc01.next()) {
                        strMerIngEgr = rstLoc01.getString("st_meringegrfisbod");
                    }

                    rstLoc01.close();
                    rstLoc01=null;//JM 4/Jul/2016
                    
                    if (strEstFisBod.equals("N")) { 
                        if (strMerIngEgr.equals("S")) { // SI LA EMPRESA NECESITA CONFIRMAR EGRESOS
                            str_MerIEFisBod = "S";
                          //  dbl_canConIE = 0;  //JM 8/Jul/2016
                        }
                    }

                    /*JoseMario 10/Jun/2016 Si no se confirma*/
                    if(str_MerIEFisBod.equals("N")){
                        //dblCanNunRec=dlbCanMov*-1;   //JM 8/JUL/2016
                        dblCanEgrBod=0.00;
                        //dblCanPen=0.00;
                    }
                        
                    
                    /**
                     * *********************** FIN DE VERIFICA ITEM DE
                     * SERVICIO ******************************************************
                     */
                    /**
                     * ********* VERFIFICA SI EL ITEM ES DE TRANSPORTE  ***********************
                     */
                    if (strEstFisBod.trim().equalsIgnoreCase("T")) { // no es de servicio.
                        blnEstItmGenGuia = false;
                    }
                    /**
                     * *********************** FIN DE VERIFICA ITEM DE
                     * TRANSPORTE ******************************************************
                     */;
                    dlbCanMov = dlbCanMov * -1;
                    dlbcostouni = objInvItm.getCostoItm(conn, rstExt.getInt("co_emp"), intCodItm);
                    bldcostot = (dlbcostouni * dlbCanMov);
                    bldcostot = (bldcostot * -1);
                    if (intEstIns > 0) {
                        stbins.append(" UNION ALL ");
                    }
                    if (strEstSer.equals("N")) {
                        strConfInv = "P";
                    }
                    if (strConfInv.equals("P")) {
                        if (strMerIngEgr.equals("N")) {
                            strConfInv = "F";
                        }
                    }
                    /* JM New 6/Agos/2016 */
                         String strItmSer="";
                        String strCadena = "SELECT st_ser FROM tbm_inv WHERE co_emp="+rstExt.getInt("co_emp")+" ";
                               strCadena+=" AND co_itm="+intCodItmCot ;
                               System.out.println("SERVICIO " + strCadena);
                        rstLoc01 = stmLoc01.executeQuery(strCadena);
                        if(rstLoc01.next()){
                            strItmSer=rstLoc01.getString("st_ser");
                            blnIsSer=rstLoc01.getString("st_ser").equals("N")?false:true;
                        }
                        rstLoc01.close();
                        rstLoc01=null;
                        
                        
                        String strTerminal=strCodAlt.substring(strCodAlt.length()-1);
                        int intBodGrp = intBodGrp(rstExt.getInt("co_emp"), intCodBod);
                        String strCfgCon = objUti.getCfgConfirma(jifCnt, objParSis,  objParSis.getCodigoEmpresaGrupo(), intBodGrp, strTerminal);                    
                        if(strCfgCon.equals("S") && blnIsSer==false){
                            if(strCodAlt.substring(strCodAlt.length()-1).equals("L") && intBodGrp==15){
                                if(obtenerSt_merIngEgrFisBodOC(conn, intCodSeg,rstExt.getInt("co_emp") ,intCodItmCot)){
                                    dblCanPen=dlbCanMov;
                                    dblCanEgrBod = dblCanPen;
                                }else{
                                    dblCanPen=0.00;
                                    dblCanEgrBod=0.00;
                                    str_MerIEFisBod="A";
                                }
                            }
                            else{
                                dblCanPen=dlbCanMov;
                                dblCanEgrBod = dblCanPen;
                                
                            }
                        }
                        else{
                            dblCanPen=0.00;
                            dblCanEgrBod=0.00;
                            str_MerIEFisBod="A";
                        }
                        
                        
                        if(strItmSer.equals("S")){
                            System.out.println("servicio xD");
                            dblCanPen=dlbCanMov;
                            str_MerIEFisBod="S";
                        }
                        /* JM 26/Sep/2017 */
 
                        /* JM 6/Agos/2016 */
                    
                    
                    stbins.append("SELECT "+rstExt.getString("co_emp") + "," + rstExt.getString("co_loc")+","+intCodTipDocFacEle+","+intCodDoc + ","+intCodRegCot+",'"
                            +strCodAlt+ "','" +strCodAlt2+ "',"+intCodItmCot+ ","+intCodItmCot+ ","
                            + objUti.codificar(strNomItm) + ",'"+strDesUni+ "',"+intCodBod + ","
                            + dlbCanMov + ","
                            + objUti.redondear(dblCanTot, intNumDec) + ", "  
                            + dlbcostouni + ", 0 , "
                            + objUti.redondear(dblPreUni, 4) + ", "
                            + objUti.redondear(dblPorDes, 2) + ", '"
                            + (strIvaVen) + "' "
                            + "," + bldcostot + ",'I', '" + str_MerIEFisBod + "', " + dbl_canConIE + ", "
                            + objInvItm.getIntDatoValidado(dblColActPreCot) + ", " /* PRECIO EN LA COTIZACION */
                            + ((blnActPreDes) ? "" + Glo_dlbMaxDes : objInvItm.getIntDatoValidado(dblPorDesVenMax)) + ", "
                            + " 1,"  +dblCanNunRec+","+(dblCanPen) +","
                            + (strIvaVen.equals("S")?dblPorIva:0) + ","
                            + (strIvaVen.equals("S")?0:dblCanTot) + ","
                            + (strIvaVen.equals("S")?dblCanTot:0) + " "
                        );
                   
                    intEstIns = 1;                    
                }
                
                if (intEstIns == 1) {
                    strSqlIns += " ; INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " ; //CAMPOS PrimayKey
                    strSqlIns+=" tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, ";//<==Campos que aparecen en la parte superior del 1er Tab
                    strSqlIns+=" co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " ;//<==Campos que aparecen en la parte inferior del 1er Tab
                    strSqlIns+=",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil,nd_canNunRec,nd_canPen, " ;
                    strSqlIns+=" nd_porIva,nd_basImpIvaCer,nd_basImpIvaGra ";
                    strSqlIns+=" )"+  stbins.toString();
                    strSqlIns+="  "+strUpdateFacPar;

                }

                if (conn != null) {
                    stmLocIns = conn.createStatement();
                    System.out.println("insertarDetFac \n" +strSqlIns);
                    stmLocIns.executeUpdate(strSqlIns);
                    
                    stmLocIns.close();
                    stmLocIns = null;
                }
                stmLoc01.close();
                stmLoc01=null;
                stmLoc.close();
                stmLoc = null;
                stbins = null;
               

                blnRes = true;
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
        
          
        
        double dblSubSerNoSer = 0, dblIvaSerNoSer = 0;
        
    /**
     * Configura la tabla de pago
     *
     * @return true si OK 
     * @return false si hay algun error
     */
    private boolean configurarTablaPago() 
    {
        boolean blnRes = false;
        try 
        {
            //Almacena cabeceras
            Vector vecCabPAg = new Vector();                           
            vecCabPAg.clear();
            vecCabPAg.add(INT_TBL_PAGLIN, "");
            vecCabPAg.add(INT_TBL_PAGCRE, "Días.Crédito");
            vecCabPAg.add(INT_TBL_PAGFEC, "Fec.Vencimiento");
            vecCabPAg.add(INT_TBL_PAGRET, "%Retención");
            vecCabPAg.add(INT_TBL_PAGMON, "Monto");
            vecCabPAg.add(INT_TBL_PAGGRA, "Días.Gracia");
            vecCabPAg.add(INT_TBL_PAGCOD, "Cod.Ret");
            vecCabPAg.add(INT_TBL_PAGSOP, "Tx_Sop");
            vecCabPAg.add(INT_TBL_COMSOL, "Compensacion");
            objTblModPag = new ZafTblMod();
            objTblModPag.setHeader(vecCabPAg);

            tblPag.setModel(objTblModPag);
            tblPag.setRowSelectionAllowed(true);
            tblPag.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblPag, INT_TBL_PAGLIN);

            objTblModPag.setColumnDataType(INT_TBL_PAGCRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGMON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_PAGGRA, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            tblPag.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblPag.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_PAGLIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_PAGCRE).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGFEC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_PAGRET).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGMON).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGGRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PAGCOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PAGSOP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_COMSOL).setPreferredWidth(60);

            tblPag.getTableHeader().setReorderingAllowed(false);
            tcmAux = null;

        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
            System.err.println("configurarTablaPago.ERROR " + e.toString());
        }
        return blnRes;
    }

        
        private boolean insertarPagFac(java.sql.Connection conn,java.sql.ResultSet rstExt, int intCodTipDocFacEle, int intCodDoc) {
            boolean blnRes = false, blnpagoscompensacion = false;
            java.sql.Statement stmLocIns;
            java.util.Calendar objFec;
            int intVal = intCanArr[0];
            int intsizearre = intarreglodia.length;
            int i = 0;
            double dblBaseDePagos = 0, dblRetIva = 0, dblRetFue = 0, dblRetFueFle = 0;
			BigDecimal bigBaseDePagos=BigDecimal.ZERO;
            double dblPago = 0.00;
            BigDecimal bigPago=BigDecimal.ZERO;
		    
            double dblPagos = 0.00;
            BigDecimal bigPagos=BigDecimal.ZERO;
			 
            double dblRete = 0;
            BigDecimal bigRete=BigDecimal.ZERO;
            String strSql = "", strSqlIns = "";
            String strFec = "";
            String strFecSem = "";
            
            try {
//                System.out.println("insertarPagFac");
                if (conn != null) {

                        intVal = intsizearre - (intsizearre - intVal);
                    
                        
                        strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());

                        dblRetFueGlo = 0.0;
                        dblRetIvaGlo = 0.00;
                        vecDataTblPag = new Vector();

                        strFec = objUti.formatearFecha(strFecSis, "yyyy-MM-dd", "dd/MM/yyyy");
                        
                        datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecSisBase = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
                        java.util.Date dateObj = datFecDoc;
                        Calendar calObj = Calendar.getInstance();
                        calObj.setTime(dateObj);

                        objFec = java.util.Calendar.getInstance();
                        
                        if (objFec != null) {
                            objFec.set(java.util.Calendar.DAY_OF_MONTH, calObj.get(Calendar.DAY_OF_MONTH));
                            objFec.set(java.util.Calendar.MONTH, calObj.get(Calendar.MONTH));
                            objFec.set(java.util.Calendar.YEAR, calObj.get(Calendar.YEAR));
                        }
                        
                        if(dblValorPagar>0){
                            dblBaseDePagos =dblValorPagar ;
                        }
                        else{
                            dblBaseDePagos =dblTotalCot ;
                        }
                        
                        if (dblBaseDePagos > 0) {
                            Vector vecData = new Vector();

                            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                            /**
                             * **********************************************************************************
                             *///

                            calculaSubtotalServiNoServi(conn, rstExt,"T");

                            if (dblSubSerNoSer > 0.00) {
                                cargaForPag(conn,rstExt, intCodMotTran);
                            }

                            /**
                             * ***************************************************************************************************************************
                             */
                            calculaSubtotalServiNoServi(conn, rstExt, "N");

                            if (dblSubSerNoSer > 0.00) {
                                cargaForPag(conn,rstExt, intCodMotBien);
                            }

                            /**
                             * ***************************************************************************************************************************
                             */
                            calculaSubtotalServiNoServi(conn, rstExt, "S");

                            if (dblSubSerNoSer > 0.00) {
                                cargaForPag(conn,rstExt, intCodMotServ);
                            }

                               
                            if(blnIsComSol && dblPorIva==14.00){
                                  cargaForPagComSol();
                            }
                         
                            /**
                             * **********************************************************************************
                             */
                            bigRete= new BigDecimal(dblRetFueGlo+dblRetIvaGlo).setScale(2, RoundingMode.HALF_UP);
//                            bigBaseDePagos =new BigDecimal(dblTotalCot).subtract(bigRete).setScale(2, RoundingMode.HALF_UP);     // JoseMario 31/Ene/2017                      
                            if(dblValorPagar>0){
//                                dblBaseDePagos = objUti.redondear((dblValorPagar  - dblRete), intNumDec);
                                bigBaseDePagos =new BigDecimal(dblValorPagar).subtract(bigRete).setScale(2, RoundingMode.HALF_UP);       
                            }
                            else{
//                                 dblBaseDePagos = objUti.redondear((dblTotalCot - dblRete), intNumDec);
                                 bigBaseDePagos =new BigDecimal(dblTotalCot).subtract(bigRete).setScale(2, RoundingMode.HALF_UP);   
                            }
                            
                            for (i = 0; i < intVal; i++) {
                                java.util.Calendar objFecPagActual = Calendar.getInstance();
                                objFecPagActual.setTime(objFec.getTime());

                                int diaCre = intarreglodia[i];
                                int numPag = intarreglonum[i];
                                String strSop = ((strarreglosop[i] == null) ? "N" : strarreglosop[i]);

                                if (diaCre != 0) {
                                    objFecPagActual.add(java.util.Calendar.DATE, diaCre);
                                }
                                System.out.println("..." + java.util.Calendar.DAY_OF_MONTH);
                                                               
                                dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
                                dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);  //AnteS Tony 2Jul2016
                                //dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH));    //Tony 2Jul2016
                                dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
                                
                                System.out.println("FECHA VENCIMIENTO " + dtePckPag.getText().toString());
                                                                
                                java.util.Vector vecReg = new java.util.Vector();
                                vecReg.add(INT_TBL_PAGLIN, "");
                                vecReg.add(INT_TBL_PAGCRE, "" + diaCre);
                                vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText());

                                bigPagos=((numPag==0)?BigDecimal.ZERO:(bigBaseDePagos.divide(new BigDecimal(numPag),2,RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP);
                                bigPago=bigPago.add(bigPagos);
                           
                                vecReg.add(INT_TBL_PAGRET, "");
                                if (i == (intVal - 1)) {
//                                    bigPagos=  bigPagos.add(new BigDecimal(dblTotalCot).subtract(bigPago.add(bigRete))).setScale(2, RoundingMode.HALF_UP);
                                    if(dblValorPagar > 0){
//                                        dblPagos = objUti.redondear(dblPagos + (dblValorPagar   - (dblPago + dblRete)), intNumDec);
                                        bigPagos=  bigPagos.add(new BigDecimal(dblValorPagar).subtract(bigPago.add(bigRete))).setScale(2, RoundingMode.HALF_UP);
                                    }
                                    else{
//                                        dblPagos = objUti.redondear(dblPagos + (dblTotalCot - (dblPago + dblRete)), intNumDec);
                                        bigPagos=  bigPagos.add(new BigDecimal(dblTotalCot).subtract(bigPago.add(bigRete))).setScale(2, RoundingMode.HALF_UP);
                                    }
                                }
                                System.err.println("PAGO: " + bigPagos.toString());
                                vecReg.add(INT_TBL_PAGMON, ""+bigPagos);
                                vecReg.add(INT_TBL_PAGGRA, "0");
                                vecReg.add(INT_TBL_PAGCOD, "");
                                vecReg.add(INT_TBL_PAGSOP, strSop);
                                vecReg.add(INT_TBL_COMSOL,"N"); 
                                vecDataTblPag.add(vecReg);
                            }
                            objTblModPag.setData(vecDataTblPag);
                            tblPag.setModel(objTblModPag);

                            vecDataTblPag = null;

                            double dblValRet = 0;
                            String strFecCor = "";
                            for (int x = 0; x < tblPag.getRowCount(); x++) {
                                dblValRet = Double.parseDouble(objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                                System.out.println("INT_TBL_COMSOL==>  "+tblPag.getValueAt(x, INT_TBL_COMSOL).toString() );
                                if (dblValRet == 0.00  && (tblPag.getValueAt(x, INT_TBL_COMSOL).toString().equals("N"))) {
                                    strFecCor = tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
                                    break;
                                }
                            }

                            String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
                            java.util.Date fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
                            int intAnioAct = (fac1.getYear() + 1900);

                            //  por alfredo.  año nuevo 31 dic año anterior
                            String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
                            java.util.Date fac = objUti.parseDate(strF, "yyyy/MM/dd");
                            int intAnioCre = (fac.getYear() + 1900);

                            if (intAnioCre > intAnioAct) {
                                strFecCor = "31/12/" + intAnioAct;
                            }

                            for (int x = 0; x < tblPag.getRowCount(); x++) {
                                dblValRet = Double.parseDouble(objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                                if ((dblValRet > 0.00) && (tblPag.getValueAt(x, INT_TBL_COMSOL).toString().equals("N"))) {
                                    tblPag.setValueAt(strFecCor, x, INT_TBL_PAGFEC);
                                }
                            }

                            /**
                             * ********************************************************************************************
                             */
                            //javax.swing.text.JTextComponent txtFecDoc ;
                            
                            for (int x = 0; x < tblPag.getRowCount(); x++) {
                                i = x;
                                
                                int FecPagDoc[] = txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString());///////JOTA AKIIIII!!!!
                                String strFechaPag = "#" + FecPagDoc[2] + "/" + FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                                String strSop = ((tblPag.getValueAt(i, INT_TBL_PAGSOP) == null) ? "" : tblPag.getValueAt(i, INT_TBL_PAGSOP).toString());
                                String strCodTipRet = objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCOD));
                                
                                System.out.println("1 " + tblPag.getValueAt(i, INT_TBL_PAGFEC).toString() );
                                System.out.println("2 " + txtFecDoc.getFecha(tblPag.getValueAt(i, INT_TBL_PAGFEC).toString()) );
                                System.out.println("3 " + strFechaPag );
                                
                                strSqlIns += " ; INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " ; //CAMPOS PrimayKey
                                strSqlIns+=" ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" ;//<==
                                strSqlIns+=" ,co_tipret,tx_tipReg ) VALUES (";
                                strSqlIns+=rstExt.getString("co_emp") + ", " + rstExt.getString("co_loc") + ", " + intCodTipDocFacEle + ", " + intCodDoc + ", " + (x + 1) + ", ";
                                strSqlIns+=objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCRE)) + ", '" + strFechaPag + "',";
                                if(tblPag.getValueAt(i, INT_TBL_COMSOL).equals("S")){
                                        strSqlIns += (new BigDecimal( objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON))).multiply(new BigDecimal(-1)))+", ";
                                        blnpagoscompensacion=true;
                                }
                                else{
                                    strSqlIns += (new BigDecimal( objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON))).multiply(new BigDecimal(-1)))+", ";
                                }
                                strSqlIns+= objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGGRA)) + ", ";
                                strSqlIns+=objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGRET)) + ", 'I', '" + strSop + "', ";
                                strSqlIns+=(strCodTipRet.equals("0") ? null : strCodTipRet) + ", ";
                                if(blnpagoscompensacion){
                                    strSqlIns += "'S')";
                                    blnpagoscompensacion=false;
                                }
                                else{
                                    strSqlIns += " null)";
                                }
                                
                                //strSqlIns+=") ";
                            }
                            blnRes = true;
                        }
                    System.out.println("PAGOS!!  " + strSqlIns);
                    stmLocIns = conn.createStatement();
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;

                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
        
        
        private boolean blnIsComSol=false;
        
    public void calculaSubtotalServiNoServi(java.sql.Connection conn,java.sql.ResultSet rstExt, String strServi) {
        double dblCan, dblDes, dblTotal = 0.00, dblPre = 0.00, dblValDes = 0.00;
        double dblSub = 0, dblIva = 0, dblTmp = 0;
        int intFilSel = 0;
        dblSubSerNoSer = 0;
        dblIvaSerNoSer = 0;
        try {
           if (conn != null) {

                for (intFilSel = 0; intFilSel < tblDat.getRowCount(); intFilSel++) {
                    if (tblDat.getValueAt(intFilSel, INT_TBL_CODITM) != null) {
                        if (objInvItm.getEstItm(conn, rstExt.getInt("co_emp"), Integer.parseInt(tblDat.getValueAt(intFilSel, INT_TBL_CODITM).toString()), strServi)) {
                            dblCan = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)));
                            dblPre = Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)));
                            dblDes = objUti.redondear(Double.parseDouble(objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PORDES))), 2);
                            dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblDes / 100));
                            dblTotal = (dblCan * dblPre) - dblValDes;
                            dblTotal = objUti.redondear(dblTotal, 3);
                            dblTotal = objUti.redondear(dblTotal, 2);
                            dblSub += dblTotal;
                            
                            if (objInvItm.getStringDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)).equals("true")) {
                                dblTmp = dblTotal;
                                dblIva = dblIva + (((dblTmp * dblPorIva) == 0) ? 0 : (dblTmp * dblPorIva) / 100);
                            } else {
                                dblIva = dblIva + 0;
                            }

                        }
                    }
                }

                if (stIvaVen.equals("N")) {
                    dblIvaSerNoSer = 0.00;
                } else {
                    dblIvaSerNoSer = objUti.redondear(dblIva, intNumDec);
                }


                dblSubSerNoSer = objUti.redondear(dblSub, intNumDec);
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            System.err.println("ERROR " + e.toString());
        }
    }
    
    
    
    
    private void cargaForPag(java.sql.Connection conn,java.sql.ResultSet rstExt, int intCodMot) {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL = "";
        double dblRetFue = 0, dblRetIva = 0;
        try {
            if (intTipForPag2 == 4) {
                return;
            }
            if (conn != null) {
               stm = conn.createStatement();
                strSQL = " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta \n";
                strSQL += " from tbm_polret as polret \n "
                        + "left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) \n  "
                        + "left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret) \n";
                strSQL += " where polret.co_emp = " + rstExt.getInt("co_emp") + " and co_mot = " + intCodMot + " and "
                        + "co_sujret = " + intCodTipPerEmp + " and co_ageret  = " + strCodTipPerCli + " "
                        + " AND polret.st_reg='A' AND  \n "
                        + "'" + strFecSisBase + "'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' "
                        + " else polret.fe_vighas end ";
                rst = stm.executeQuery(strSQL);
                while (rst.next()) {

                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_PAGLIN, "");
                    vecReg.add(INT_TBL_PAGCRE, "");
                    System.out.println("cargaForPag 1820 " + strFecSisBase);
                    vecReg.add(INT_TBL_PAGFEC, strFecSisBase);
                    if (rst.getString("tx_aplret").equals("S")) {

                        dblRetFue = objUti.redondeo((dblSubSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                        dblRetFueGlo += dblRetFue;
                        vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret"));
                        vecReg.add(INT_TBL_PAGMON, "" + dblRetFue);
                        vecReg.add(INT_TBL_PAGGRA, "");
                    }
                    if (rst.getString("tx_aplret").equals("I")) {
                        if (dblIvaSerNoSer > 0) {
//                            dblRetIva = objUti.redondeo((dblIvaSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                            if(blnIsComSol){
                                dblRetIva = objUti.redondeo(((dblIvaSerNoSer - dblComSol) * (rst.getDouble("nd_porret") / 100)), 2); 
                            }else{
                                dblRetIva = objUti.redondeo((dblIvaSerNoSer * (rst.getDouble("nd_porret") / 100)), 2);
                            }
                            dblRetIvaGlo += dblRetIva;
                            vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret"));
                            vecReg.add(INT_TBL_PAGMON, "" + dblRetIva);
                            vecReg.add(INT_TBL_PAGGRA, "");
                        }
                    }

                    vecReg.add(INT_TBL_PAGCOD, "" + rst.getString("co_tipret"));
                    vecReg.add(INT_TBL_PAGSOP, "N");
                    vecReg.add(INT_TBL_COMSOL,"N");

                    vecDataTblPag.add(vecReg);

                }
                stm.close();
                stm=null;
                rst.close();
                rst = null;

            }
        } catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        }
    }
        
    
    private void CalculoPago(java.sql.Connection conPag, java.sql.ResultSet rstExt) {
        try {
            //java.sql.Connection conPag = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conPag != null) {
                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + rstExt.getInt("co_forPag") +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + rstExt.getInt("co_emp") + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                        "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                String sSQL3 = "SELECT count(A2.ne_diaCre) as c "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + rstExt.getInt("co_forPag") +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + rstExt.getInt("co_emp") + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando

                Statement stmDoc2 = conPag.createStatement();
                System.out.println("CalculoPago.UNO " + sSQL2);
                System.out.println("CalculoPago.DOS " + sSQL3);
                
                ResultSet rstDocCab2 = stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);

                stmDoc2 = conPag.createStatement();
                rstDocCab2 = stmDoc2.executeQuery(sSQL2);
                int x = 0;
                while (rstDocCab2.next()) {
                    intarreglodia[x] = rstDocCab2.getInt(2);
                    intarreglonum[x] = rstDocCab2.getInt(1);
                    strarreglosop[x] = rstDocCab2.getString("st_sop");
                    x++;
                }
            }
        } 
        catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        calculaPag(conPag,rstExt);
    }
    
    
    private void CalculoPago2(java.sql.Connection conPag, java.sql.ResultSet rstExt) {
        try {
           // java.sql.Connection conPag = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conPag != null) {

                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + rstExt.getInt("co_forPag") +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + rstExt.getInt("co_emp") + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                        "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                String sSQL3 = "SELECT count(A2.ne_diaCre) as c "
                        + " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_forPag = " + rstExt.getInt("co_forPag") +// Clausulas Where para las tablas maestras
                        "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                        "       and A1.co_emp = " + rstExt.getInt("co_emp") + // Consultando en la empresa en la ke se esta trabajando
                        "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando

                Statement stmDoc2 = conPag.createStatement();
                ResultSet rstDocCab2 = stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);

                stmDoc2 = conPag.createStatement();
                rstDocCab2 = stmDoc2.executeQuery(sSQL2);
                int x = 0;
                while (rstDocCab2.next()) {
                    intarreglodia[x] = rstDocCab2.getInt(2);
                    intarreglonum[x] = rstDocCab2.getInt(1);
                    strarreglosop[x] = rstDocCab2.getString("st_sop");
                    x++;
                }
                rstDocCab2.close();
                rstDocCab2 = null;
//                conPag.close();
//                conPag = null;
            }
        }
        catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } 
        catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }

    
    public void calculaPag(java.sql.Connection conLoc, java.sql.ResultSet rstExt) {
        int intVal = intCanArr[0];
        int intsizearre = intarreglodia.length;
        intVal = intsizearre - (intsizearre - intVal);
        double dblBaseDePagos = 0, dblRetIva = 0, dblRetFue = 0, dblRetFueFle = 0;
        double dblPago = 0.00;
        double dblPagos = 0.00;
        double dblRete = 0;
        int i = 0;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
       
        try {
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            datFecDoc = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strFecSisBase = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
            java.util.Date dateObj = datFecDoc;
            Calendar calObj = Calendar.getInstance();
            calObj.setTime(dateObj);

            objFec.set(java.util.Calendar.DAY_OF_MONTH, calObj.get(Calendar.DAY_OF_MONTH));
            objFec.set(java.util.Calendar.MONTH, calObj.get(Calendar.MONTH) + 1 );
            objFec.set(java.util.Calendar.YEAR, calObj.get(Calendar.YEAR));
                
            //dblBaseDePagos = dblTotalCot;

            if(dblValorPagar>0){
                dblBaseDePagos = dblValorPagar;
            }
            else{
                dblBaseDePagos = dblTotalCot;
            }

            
            dblRetFueGlo = 0.0;
            dblRetIvaGlo = 0.00;

            
            if (strCodTipPerCli.equals("")) {
                objCorEle.enviarCorreoMasivo(strCorEleTo,strTitCorEle," Cliente sin tipo de persona..." + rstExt.getString("co_emp")+ "-"+
                                                rstExt.getString("co_loc")+"-"+rstExt.getString("co_cot"));
                System.err.println("ERROR: CLIENTE SIN TIPO DE PERSONA");
                objTblModPag.removeAllRows();
            } 
            else {
                if(dblBaseDePagos > 0) {
                    vecDataTblPag = new Vector();
                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
                    //*************************************************************************************///
                    //java.sql.Connection conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (conLoc != null) {
                        stmLoc = conLoc.createStatement();
                        strSql="";
                        strSql+=" SELECT a1.*,a2.st_ser, a3.tx_desCor as tx_desUni \n ";
                        strSql+=" FROM tbm_detCotVen as a1 \n";
                        strSql+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSql+=" LEFT OUTER JOIN tbm_var as a3 ON (a2.co_uni=a3.co_reg) \n";
                        strSql+=" WHERE a1.co_emp="+rstExt.getString("co_emp")+ " AND ";
                        strSql+=" a1.co_loc="+rstExt.getString("co_loc")+" AND a1.co_cot="+rstExt.getString("co_cot");
                        System.out.println("tbm_detCotVen \n" + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);
                        while(rstLoc.next()){
                            /**
                            * ***************************************************************************************************************************
                            */
                           calculaSubtotalServiNoServi(conLoc, rstLoc, "T");

                           if (dblSubSerNoSer > 0.00) {
                               cargaForPag(conLoc,rstExt, intCodMotTran);
                           }

                           /**
                            * ***************************************************************************************************************************
                            */
                           calculaSubtotalServiNoServi(conLoc,rstLoc, "N");

                           if (dblSubSerNoSer > 0.00) {
                               cargaForPag(conLoc,rstExt, intCodMotBien);
                           }

                           calculaSubtotalServiNoServi(conLoc,rstLoc, "S");

                           if (dblSubSerNoSer > 0.00) {
                               cargaForPag(conLoc,rstExt, intCodMotServ);
                            }
                        }
                        
                        if(blnIsComSol && dblPorIva==14.00){
                            cargaForPagComSol();
                        }
 
                    }

                    dblRete = dblRetFueGlo + dblRetIvaGlo;
                    if(dblValorPagar>0){
                        //dblBaseDePagos = dblTotalCot;
                        dblBaseDePagos = objUti.redondear((dblValorPagar  - dblRete), intNumDec);
                    }
                    else{
                        dblBaseDePagos = objUti.redondear((dblTotalCot - dblRete), intNumDec);
                    }
//                    dblBaseDePagos = objUti.redondear((dblTotalCot - dblRete), intNumDec);



                    for (i = 0; i < intVal; i++) {
                        java.util.Calendar objFecPagActual = Calendar.getInstance();
                        objFecPagActual.setTime(objFec.getTime());

                        int diaCre = intarreglodia[i];
                        int numPag = intarreglonum[i];
                        String strSop = ((strarreglosop[i] == null) ? "N" : strarreglosop[i]);

                        if (diaCre != 0) {
                            objFecPagActual.add(java.util.Calendar.DATE, diaCre);
                        }

                        dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
                        dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
                        dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_PAGLIN, "");
                        vecReg.add(INT_TBL_PAGCRE, "" + diaCre);
                        vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText());

                        dblPagos = objUti.redondear((numPag == 0) ? 0 : (dblBaseDePagos / numPag), intNumDec);
                        dblPago += dblPagos;
                        dblPagos = objUti.redondear(dblPagos, intNumDec);

                        vecReg.add(INT_TBL_PAGRET, "");

                        if (i == (intVal - 1)) {
                            if(dblValorPagar>0){
                                dblPagos = objUti.redondear(dblPagos + (dblValorPagar  - (dblPago + dblRete)), intNumDec);
                            }
                            else{
                                dblPagos = objUti.redondear(dblPagos + (dblTotalCot - (dblPago + dblRete)), intNumDec);
                            }
                        }

                        vecReg.add(INT_TBL_PAGMON, "" + dblPagos);
                        vecReg.add(INT_TBL_PAGGRA, "0");
                        vecReg.add(INT_TBL_PAGCOD, "");
                        vecReg.add(INT_TBL_PAGSOP, strSop);
                        vecReg.add(INT_TBL_COMSOL,"N");
                        vecDataTblPag.add(vecReg);
                    }
                    objTblModPag.setData(vecDataTblPag);
                    tblPag.setModel(objTblModPag);

                    vecDataTblPag = null;

                    /**
                     * ********************************************************************************************
                     */
                    
                    double dblValRet = 0;
                    String strFecCor = "";
                    
                    for (int x = 0; x < tblPag.getRowCount(); x++) {
                        dblValRet = Double.parseDouble(objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                        if (dblValRet == 0.00) {
                            strFecCor = tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
                            break;
                        }
                    }

                    String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
                    java.util.Date fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
                    int intAnioAct = (fac1.getYear() + 1900);
                    if(strFecCor.length()==0)
                        strFecCor=strFecSisBase;    
                    //  por alfredo.  año nuevo 31 dic año anterior
                    String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
                    java.util.Date fac = objUti.parseDate(strF, "yyyy/MM/dd");
                    int intAnioCre = (fac.getYear() + 1900);


                    if (intAnioCre > intAnioAct) {
                        strFecCor = "31/12/" + intAnioAct;
                    }

                    for (int x = 0; x < tblPag.getRowCount(); x++) {
                        dblValRet = Double.parseDouble(objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)));
                        if (dblValRet > 0.00) {
                            tblPag.setValueAt(strFecCor, x, INT_TBL_PAGFEC);
                        }
                    }
                }
            }
        } catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }
    
    
    
    private void cargaForPagComSol() {
        double dblRetFue = 0;
        try {
            Date datFecDocComSol;
            
            datFecDocComSol = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            System.out.println("datFecDocComSol " + datFecDocComSol.toString());
            System.out.println("---->>> "+ objUti.formatearFecha(datFecDocComSol, "dd/MM/yyyy"));
            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_PAGLIN, "");
            vecReg.add(INT_TBL_PAGCRE, "");
            vecReg.add(INT_TBL_PAGFEC, objUti.formatearFecha(datFecDocComSol, "dd/MM/yyyy"));
            dblRetFue = objUti.redondear((dblBaseIva * (dblPorComSol) / 100), 2);
            vecReg.add(INT_TBL_PAGRET, "");
            vecReg.add(INT_TBL_PAGMON, "" + dblRetFue);
            vecReg.add(INT_TBL_PAGGRA, "");
            vecReg.add(INT_TBL_PAGCOD, "");
            vecReg.add(INT_TBL_PAGSOP, "N");
            vecReg.add(INT_TBL_COMSOL, "S");  // <<< === SI ES COMPENSACION
            vecDataTblPag.add(vecReg);
        }  
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
    }
     
     
    /**
     * Permite cargar datos de la empresa en variables
     */
    private void cargarTipEmp(java.sql.Connection conn, java.sql.ResultSet rstExt) {
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        try {
            if (conn != null) {
                stmTipEmp = conn.createStatement();
                sSql = "select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, a1.tx_dir, a1.tx_nom as nombod  FROM  tbm_emp as a "
                        + " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)"
                        + " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc=" + rstExt.getString("co_loc") + " and bod.st_reg='P')  "
                        + " inner join tbm_bod as a1 on (a1.co_emp=bod.co_emp and a1.co_bod=bod.co_bod ) "
                        + " where a.co_emp=" + rstExt.getString("co_emp");
                rstEmp = stmTipEmp.executeQuery(sSql);
                if (rstEmp.next()) {
                    strTipPer_emp = rstEmp.getString("tx_descor");
                    intCodBodPre = rstEmp.getInt("co_bod");
                    intCodTipPerEmp = rstEmp.getInt("co_tipper");
                    strNomBodPtaPar = rstEmp.getString("tx_dir");
                    strNomBodPrv = rstEmp.getString("nombod");
                    bldivaEmp = objZafCtaCtb_dat.getPorIvaVen();
                    dblPorIva = bldivaEmp;
                    dblPorComSol = objZafCtaCtb_dat.getPorComSol();
                }
                rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
            }
        } 
        catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        }
    }
    
    
    
    
    
        private boolean insertarDiario(java.sql.Connection conn ,java.sql.ResultSet rstExt, int intCodTipDocFacEle, int intCodDoc) {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLocIns;
            String strSql = "", strSqlIns = "";
            String strPer = null, strMes = "";
            String strFecSistema = "";
            int intCodPer = 0;
            int intMes = 0;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    int intArlAni = 0;
                    int intArlMes = 0;
                    String strArlTipCie = "";
                    int intRefAniNew = 0;
                    int intRefMesNew = 0;
                    int intTipPro = 0;
                    intRefAniNew = (datFecDoc.getYear() + 1900);
                    intRefMesNew = (datFecDoc.getMonth() + 1);
                    String strMensaje;

                    //SI EL Aï¿½O NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
                    if (!(objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))) {
                        strMensaje=("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                        objCorEle.enviarCorreoMasivo(strCorEleTo,strTitCorEle,strMensaje);
                        return false;
                    }
                    //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
                    if (!(retAniMesCie(conn,rstExt.getInt("co_emp"), intRefAniNew))) {
                        return false;
                    }
                    for (int k = 0; k < arlDatAniMes.size(); k++) {
                        intArlAni = objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                        intArlMes = objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                        strArlTipCie = (objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE) == null ? "" : objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                        if ((strArlTipCie.toString().equals("M"))) {
                            if (intRefAniNew == intArlAni) {
                                if (intRefMesNew == intArlMes) {
                                    strMensaje=("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                                    objCorEle.enviarCorreoMasivo(strCorEleTo,strTitCorEle,strMensaje);
                                    return false;
                                }
                            }
                        } else {
                            strMensaje=("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            objCorEle.enviarCorreoMasivo(strCorEleTo,strTitCorEle,strMensaje);
                            return false;
                        }
                    }

                    /**
                     * *****************************************************************************************
                     */
                    strFecSistema = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());

                    strSqlIns = "INSERT INTO  tbm_cabdia(co_emp, co_loc, co_tipDoc, co_dia, fe_dia, tx_glo, "
                            + " fe_ing, co_usrIng, fe_ultMod, co_usrMod ) "
                            + " VALUES(" + rstExt.getString("co_emp") + ", " +rstExt.getString("co_loc")+ ", " + intCodTipDocFacEle + ", "
                            + intCodDoc + ", '" + datFecDoc + "','','" + strFecSistema + "',"+rstExt.getString("co_usrIng")+" , "
                            + " '" + strFecSistema + "', null)";
                    
                    intMes = datFecDoc.getMonth() + 1;
                    if (intMes < 10) {
                        strMes = "0" + String.valueOf(intMes);
                    } else {
                        strMes = String.valueOf(intMes);
                    }

                    strPer = String.valueOf((datFecDoc.getYear() + 1900)) + strMes;
                    intCodPer = Integer.parseInt(strPer);
                    
                    stmLocIns = conn.createStatement();
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                    
                    if(insertarBigDetDia(conn,rstExt , intCodTipDocFacEle, intCodDoc, intCodPer)){
                        blnRes = true;
                    } else {
                        blnRes = false;
                    }

                    stmLoc.close();
                    stmLoc = null;
                }
            } 
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            } 
            catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }
        
        
        
        private boolean retAniMesCie(java.sql.Connection conn,int intCodEmp, int anio) {
            boolean blnRes = true;
            arlDatAniMes.clear();
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSQL = "";
                    strSQL += "select a1.ne_ani, a2.ne_mes, a1.tx_tipCie";
                    strSQL += " from tbm_cabciesis as a1 left outer join tbm_detciesis as a2";
                    strSQL += " on a1.co_emp=a2.co_emp and a1.ne_ani=a2.ne_ani";
                    strSQL += " where a1.co_emp=" + intCodEmp + "";
                    strSQL += " and a1.ne_ani=" + anio + "";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    while (rstLoc.next()) {
                        arlRegAniMes = new ArrayList();
                        arlRegAniMes.add(INT_ARL_ANI_CIE, "" + rstLoc.getInt("ne_ani"));
                        arlRegAniMes.add(INT_ARL_MES_CIE, "" + rstLoc.getInt("ne_mes"));
                        arlRegAniMes.add(INT_ARL_TIP_CIE, "" + rstLoc.getString("tx_tipCie"));
                        arlDatAniMes.add(arlRegAniMes);
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } 
            catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, e);
                System.err.println("ERROR " + e.toString());
            } 
            catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, e);
                System.err.println("ERROR " + e.toString());
            }
            return blnRes;
        }
        
        
        private String strarritmotr[][] = null;

        private boolean insertarBigDetDia(java.sql.Connection conn,java.sql.ResultSet rstExt, int intCodTipDocFacEle, int intCodDoc, int intCodPer) {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLocIns;
            String srtSql = "", strSqlIns = "",strCodCta="",strNomCta="";
            String srtSqlSal = "";
            int intCodCta = -1;
            BigDecimal bigValTotDes = BigDecimal.ZERO, bigValTotOtr = BigDecimal.ZERO;
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    
                     /*AGREGADO POR COMPENSACION SOLIDARIA*/
                    if(rstExt.getInt("co_emp")==1){
                        intCodCta=4680;
                        strCodCta="4.01.01.04";
                        strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                    }else if(rstExt.getInt("co_emp")==2){
                        intCodCta=2303;
                        strCodCta="4.01.01.04";
                        strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                    }else if(rstExt.getInt("co_emp")==4){
                        intCodCta=3210;
                        strCodCta="4.01.01.04";
                        strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                    }
                    
                    srtSql = "INSERT INTO tbm_detdia(co_emp, co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab )"
                            + " VALUES(" + rstExt.getString("co_emp") + ", " +rstExt.getString("co_loc")+ ", " + intCodTipDocFacEle + ", " + intCodDoc + ", ";

                   
                    
                    if (dblTotalCot > 0) {
                        strSqlIns += srtSql + " 1," + objZafCtaCtb_dat.getCtaDeb() + "," + dblTotalCot + ", 0 )  ; ";
//                        srtSqlSal = "UPDATE tbm_salcta SET nd_salcta=nd_salcta+" + dblTotalCot + "  WHERE co_emp=" +rstExt.getString("co_emp")+ " AND co_cta=" + objZafCtaCtb_dat.getCtaDeb() + " AND co_per=" + intCodPer;
//                        strSqlIns += srtSqlSal + "  ; ";
                    }
                    
                    /**
                     * Agregando la cuenta de Ventas
                     */
                     if(dblBaseIva>0){
                        strSqlIns+=srtSql+" 2, "+objZafCtaCtb_dat.getCtaHab()+", 0, "+dblBaseIva+" ) ; ";

//                        srtSqlSal = "UPDATE tbm_salcta SET nd_salcta=nd_salcta+" + dblBaseIva + "  WHERE co_emp=" +  rstExt.getString("co_emp") + " AND co_cta=" + objZafCtaCtb_dat.getCtaHab() + " AND co_per=" + intCodPer;
//                        strSqlIns += srtSqlSal + "  ; ";
                    }
                    if(dblBaseCero>0){
                        strSqlIns+=srtSql+"3, "+intCodCta+", 0, "+dblBaseCero+" ) ; ";

//                        srtSqlSal = "UPDATE tbm_salcta SET nd_salcta=nd_salcta+" + dblBaseCero + "  WHERE co_emp=" + rstExt.getString("co_emp") + " AND co_cta=" + intCodCta + " AND co_per=" + intCodPer;
//                        strSqlIns += srtSqlSal + "  ; ";
                    }
                     
                    if (dblIvaCot > 0) {
			strSqlIns+=srtSql+" 4 , "+objZafCtaCtb_dat.getCtaIvaVentas()+", 0, "+new BigDecimal(dblIvaCot).setScale(2, RoundingMode.HALF_UP)+" ) ; ";
//                        srtSqlSal = "UPDATE tbm_salcta SET nd_salcta=nd_salcta+" + (BigDecimal.valueOf(dblIvaCot).multiply(new BigDecimal(-1))) + "  WHERE co_emp=" +rstExt.getString("co_emp")+ " AND co_cta=" + objZafCtaCtb_dat.getCtaIvaVentas() + " AND co_per=" + intCodPer;
//                        strSqlIns += srtSqlSal + "  ; ";
                    }


                    //PARA ACTUALIZAR SALDOS DE LOS NODOS PADRES
//                    for (int j = 6; j > 1; j--) {
//                        strSQL = "";
//                        strSQL += "UPDATE tbm_salCta";
//                        strSQL += " SET nd_salCta=b1.nd_salCta";
//                        strSQL += " FROM (";
//                        strSQL += " SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
//                        strSQL += " FROM tbm_plaCta AS a1";
//                        strSQL += " INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
//                        strSQL += " WHERE a1.co_emp=" + rstExt.getString("co_emp");
//                        strSQL += " AND a1.ne_niv=" + j;
//                        strSQL += " AND a2.co_per=" + intCodPer + "";
//                        strSQL += " GROUP BY a1.co_emp, a1.ne_pad";
//                        strSQL += " ) AS b1";
//                        strSQL += " WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
//                        strSqlIns += strSQL + " ;  ";
//
//                    }
                    //PARA ACTUALIZAR EL SALDO DE LA CUENTA DE ESTADO DE RESULTADOS
//                    strSQL = "UPDATE tbm_salCta";
//                    strSQL += " SET nd_salCta=b1.nd_salCta";
//                    strSQL += " FROM (";
//                    strSQL += " SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
//                    strSQL += " FROM tbm_plaCta AS a1";
//                    strSQL += " INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
//                    strSQL += " INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
//                    strSQL += " WHERE a1.co_emp=" +rstExt.getString("co_emp");
//                    strSQL += " and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
//                    strSQL += " AND a2.co_per=" + intCodPer + "";
//                    strSQL += " GROUP BY a1.co_emp, a3.co_ctaRes";
//                    strSQL += " ) AS b1";
//                    strSQL += " WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
//                    strSqlIns += strSQL + " ;  ";

                    //PARA ACTUALIZAR SALDOS DE LOS NODOS PADRES
//                    for (int j = 6; j > 1; j--) {
//                        strSQL = "";
//                        strSQL += "UPDATE tbm_salCta";
//                        strSQL += " SET nd_salCta=b1.nd_salCta";
//                        strSQL += " FROM (";
//                        strSQL += " SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intCodPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
//                        strSQL += " FROM tbm_plaCta AS a1";
//                        strSQL += " INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
//                        strSQL += " WHERE a1.co_emp=" +rstExt.getString("co_emp");
//                        strSQL += " AND a1.ne_niv=" + j;
//                        strSQL += " AND a2.co_per=" + intCodPer + "";
//                        strSQL += " GROUP BY a1.co_emp, a1.ne_pad";
//                        strSQL += " ) AS b1";
//                        strSQL += " WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
//                        strSqlIns += strSQL + " ;  ";
//                    }

                    stmLocIns = conn.createStatement();
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            }
            return blnRes;
        }        
        
        
        
        public BigDecimal getBigTotalItemOtros(java.sql.ResultSet rstExt) {

            BigDecimal bigSub=BigDecimal.ZERO;
            BigDecimal bigTotalItemOtros=BigDecimal.ZERO;
            BigDecimal bigSubTot=BigDecimal.ZERO;
            strarritmotr = new String[tblDat.getRowCount()][2];
            for (int filaActual = 0; filaActual < tblDat.getRowCount(); filaActual++) {
                if ((tblDat.getValueAt(filaActual, INT_TBL_CODITM) != null) && (tblDat.getValueAt(filaActual, INT_TBL_ITMSER).equals("O")) && (tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR) != null)) {
                    bigSub= ((tblDat.getValueAt(filaActual, INT_TBL_TOTAL) == null || tblDat.getValueAt(filaActual, INT_TBL_TOTAL).toString().equals("")) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.parseDouble(tblDat.getValueAt(filaActual, INT_TBL_TOTAL).toString())));
                    bigTotalItemOtros=bigTotalItemOtros.add(bigSub);
                    for (int x = 0; x < strarritmotr.length; x++) {
                        if (strarritmotr[x][0] != null) {
                            if (strarritmotr[x][0].equals(tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR))) {
                                //subtot = dblSub + Double.parseDouble(strarritmotr[x][1]);
                                bigSubTot = bigSub.add(BigDecimal.valueOf(Double.parseDouble(strarritmotr[x][1])));
                                //strarritmotr[x][1] = Double.toString(subtot);
                                strarritmotr[x][1] = bigSubTot.toString();
                                break;
                            }
                        } else {
                            strarritmotr[x][0] = tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR).toString();
                            //subtot = dblSub;
                            bigSubTot = bigSub;
                            //strarritmotr[x][1] = Double.toString(subtot);
                            strarritmotr[x][1] = bigSubTot.toString();
                            break;
                        }
                    }

                }
            }
            return bigTotalItemOtros;
        }        
        
        
        
        
        public BigDecimal getBigTotalDescuento(java.sql.ResultSet rstExt) {
            BigDecimal bigTotalDescuento = BigDecimal.ZERO;
            for (int filaActual = 0; filaActual < tblDat.getRowCount(); filaActual++) {
                if (tblDat.getValueAt(filaActual, INT_TBL_CODITM) != null) {
                    BigDecimal bigCan= BigDecimal.valueOf(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_CANMOV) == null) ? "0" : (tblDat.getValueAt(filaActual, INT_TBL_CANMOV).toString())))).setScale(6, RoundingMode.HALF_UP);
                    BigDecimal bigPre= BigDecimal.valueOf(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PREUNI) == null) ? "0" : (tblDat.getValueAt(filaActual, INT_TBL_PREUNI).toString())))).setScale(6, RoundingMode.HALF_UP);
                    BigDecimal bigPorDes=BigDecimal.valueOf(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PORDES) == null) ? "0" : (tblDat.getValueAt(filaActual, INT_TBL_PORDES).toString())))).setScale(6, RoundingMode.HALF_UP);
                    BigDecimal bigValDes=(bigCan.multiply(bigPre)==BigDecimal.ZERO)?BigDecimal.ZERO:(bigCan.multiply(bigPre).multiply(bigPorDes).setScale(6, RoundingMode.HALF_UP)).divide(new BigDecimal(100).setScale(6, RoundingMode.HALF_UP));
                    
                    bigTotalDescuento = bigTotalDescuento.add(bigValDes);
                }
            }
            bigTotalDescuento=bigTotalDescuento.setScale(2, RoundingMode.HALF_UP);
            return bigTotalDescuento;
        }        
        
        
        
        
        
        
        
        /* ==============================  */
        
        
    public void refrescaDatos2(java.sql.Connection conn, java.sql.ResultSet rstExt, Integer CodEmp, Integer CodLoc) {
        java.sql.Statement stmCab;
        java.sql.ResultSet rst;
        try {
            int intNumCot = 0;
            double dblCan = 0, dblPre = 0, dblPorDes = 0, dblValDes = 0, dblTotal = 0;
            String strUnidad = "", strCodAlt = "", strSer = "", strTer = "";
            Vector vecData = new Vector();
            int inttratot = 0;

            BigDecimal bgdCanItm;
            BigDecimal bgdPreItm;
            BigDecimal bgdValDesItm;
            BigDecimal bgdPorDesItm;
            BigDecimal bgdTotItm=BigDecimal.ZERO;
            
            if (conn != null) {
                //FATURACION ELECTRONICA - José Marín M 3/Oct/2014
                stmCab = conn.createStatement();
                String strAux = ",CASE WHEN ("
                        + " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN ("
                        + " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp=" + rstExt.getString("co_emp")+" AND co_loc="+rstExt.getString("co_loc")+" "
                        + " AND co_tipdoc=" + intCodTipDocFacEle + " AND co_usr=" + rstExt.getString("co_usrIng")+" AND st_reg='A' "
                        + " ))) THEN 'S' ELSE 'N' END  as isterL";

                String strAux2 = " , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( "
                        + " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a "
                        + " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) "
                        + " WHERE a.co_emp="+rstExt.getString("co_emp")+ " and a.co_loc="+rstExt.getString("co_loc")+" and a1.st_reg='A' and  a.st_reg='P' ))) "
                        + " THEN 'S' ELSE 'N' END AS proconf  ";

                strSQL = "SELECT var.tx_tipunimed, detcot.tx_nomitm, detcot.co_bod ,detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.st_ivaven ";
                strSQL+=",detcot.nd_can ,detcot.nd_preuni ,detcot.nd_pordes ,detcot.co_itm ,detcot.nd_precom ,detcot.co_prv ,detcot.tx_codalt2 ";
                strSQL+=",detcot.tx_codalt , detcot.nd_pordesprecom, detcot.st_traauttot,   var.tx_descor , inv.st_permodnomitmven, inv.st_ser , cli.tx_nom";
                
                strSQL+=strAux;
                strSQL+=strAux2;
                
                strSQL+=" , ROUND(detCot.nd_can*inv.nd_pesItmKgr,2) as nd_pesTot, inv.nd_pesItmKgr, CASE WHEN inv.tx_codAlt2 IS NULL THEN '' ELSE inv.tx_codAlt2 END as tx_codLet ";  /* JoséMario 5/Ene/2016 */
                
                if(rstExt.getString("st_solFacPar")!=null){
                    if(rstExt.getString("st_solFacPar").equals("S")){
                        strSQL+=", detCot.nd_canFac, detCot.nd_canCan, detCot.nd_canPenFac  ";
                    }
                }
                
                strSQL+=" FROM tbm_detcotven as detcot ";
                strSQL+=" LEFT outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) ";
                strSQL+=" LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)";
                strSQL+=" LEFT JOIN tbm_cli as cli on(cli.co_emp=detcot.co_emp and cli.co_cli=detcot.co_prv)";
                strSQL+=" WHERE detcot.co_emp="+rstExt.getString("co_emp")+" and detcot.co_loc="+rstExt.getString("co_loc")+" ";
                strSQL+=" AND detcot.co_cot="+rstExt.getString("co_cot")+" ";
                strSQL+=" AND detCot.nd_canPenFac > 0  order by detcot.co_reg ";
                System.out.println("refrescaDatos2 \n" + strSQL);
                rst = stmCab.executeQuery(strSQL);
                for (int i = 0; rst.next(); i++) {
                    java.util.Vector vecReg = new java.util.Vector();
                    strCodAlt = (rst.getString("tx_codalt") == null ? "" : rst.getString("tx_codalt"));
                    strUnidad = (rst.getString("tx_descor") == null ? "" : rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser") == null ? "" : rst.getString("st_ser"));
                    strTer = (rst.getString("isterl") == null ? "" : rst.getString("isterl"));
                    if (inttratot == 0) {
                        if (rst.getString("st_traauttot").equals("S")) {
                            inttratot = 1;
                        }
                    }
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_BUTSTK, "");
                    vecReg.add(INT_TBL_BUTSOL, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_TRATOT, (rst.getString("st_traauttot").equals("S")) ? true : false);
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */
                    if(rstExt.getString("st_solFacPar")!=null){  
                        if(rstExt.getString("st_solFacPar").equals("S")){
//                            strSQL+=" detCot.nd_canFac, detCot.nd_canCan, detCot.nd_canPenFac  ";
                            vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_canPenFac")));
                        }
                        else{
                            vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                        }
                    }
                    else{
                        vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                    }
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */
                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivaven");
                    Boolean blnIva;
                    if (stIvaVen.equals("N")) {
                        blnIva = false;
                    } else {
                        blnIva = (strIva.equals("S")) ? true : false;
                    }
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */
                    if(rstExt.getString("st_solFacPar")!=null){  
                        if(rstExt.getString("st_solFacPar").equals("S")){
//                            strSQL+=" detCot.nd_canFac, detCot.nd_canCan, detCot.nd_canPenFac  ";
//                            vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_canPenFac")));
                            dblCan = rst.getDouble("nd_canPenFac");
                        }
                        else{
                            dblCan = rst.getDouble("nd_can");
                        }
                    }
                    else{
                        dblCan = rst.getDouble("nd_can");
                    }
                    /* JM: 2018/Oct/12 Modificaciones para facturas parciales */
                    
                    
                    dblPre = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    
                    bgdCanItm=BigDecimal.valueOf(dblCan) ;
                    bgdPreItm=BigDecimal.valueOf(dblPre) ;
                    bgdPorDesItm=BigDecimal.valueOf(dblPorDes) ;

                    //DESCUENTO
                    bgdValDesItm = bgdPorDesItm.multiply((bgdCanItm.multiply(bgdPreItm))); 
                    bgdValDesItm = bgdValDesItm.divide(new BigDecimal("100"),objParSis.getDecimalesBaseDatos(),BigDecimal.ROUND_HALF_UP);
                    ///TOTAL
                    bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)).subtract(bgdValDesItm), objParSis.getDecimalesMostrar());    
                    dblTotal = bgdTotItm.doubleValue();
                    
                    /* JM: 30/Marzo/2017 Cambio de calculo a BigDecimal */
//                    dblValDes = ((dblCan * dblPre) == 0) ? 0 : ((dblCan * dblPre) * (dblPorDes / 100));
//                    dblTotal = (dblCan * dblPre) - dblValDes;
//                    //dblTotal = objUti.redondear(dblTotal, 3);
//                    dblTotal = objUti.redondear(dblTotal, 2);
                    /* JM: 30/Marzo/2017 Cambio de calculo a BigDecimal */
                    
                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_PESTOT, rst.getString("nd_pesTot"));/* José Marín - 20/Oct/2015 */
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, strIva);
                    vecReg.add(INT_TBL_PRE_COS, new Double(rst.getDouble("nd_precom")));            //Columna que contiene  precio de compra
                    vecReg.add(INT_TBL_DESPRECOM, new Double(rst.getDouble("nd_pordesprecom")));
                    vecReg.add(INT_TBL_COD_PRO, rst.getString("co_prv"));            //Columna que contiene el codigo del proveedor

                    strCodAlt = (rst.getString("tx_nom") == null ? "" : rst.getString("tx_nom"));

                    vecReg.add(INT_TBL_NOM_PRO, strCodAlt);            //Columna que contiene el Nombre del proveedor
                    vecReg.add(INT_TBL_BUT_PRO, "");               //Columna que contiene para busqueda del proveedor
                    vecReg.add(INT_TBL_BLNPRE, "");
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer);
                    vecReg.add(INT_TBL_ITMTER, strTer);

                    vecReg.add(INT_TBL_CODBODPRV, "");
                    vecReg.add(INT_TBL_NOMBODPRV, "");
                    vecReg.add(INT_TBL_BUTBODPRV, "");

                    vecReg.add(INT_TBL_MARUTI, "");
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                    vecReg.add(INT_TBL_MODNOMITM, rst.getString("st_permodnomitmven"));
                    vecReg.add(INT_TBL_COLOCREL, "");
                    vecReg.add(INT_TBL_COTIPDOCREL, "");
                    vecReg.add(INT_TBL_CODOCREL, "");
                    vecReg.add(INT_TBL_COREGREL, "");
                    vecReg.add(INT_TBL_COLOCRELSOL, "");
                    vecReg.add(INT_TBL_COTIPDOCRELSOL, "");
                    vecReg.add(INT_TBL_CODOCRELSOL, "");
                    vecReg.add(INT_TBL_COREGRELSOL, "");
                    vecReg.add(INT_TBL_COLOCRELOC, null);
                    vecReg.add(INT_TBL_COTIPDOCRELOC, null);
                    vecReg.add(INT_TBL_CODOCRELOC, null);
                    vecReg.add(INT_TBL_COREGRELOC, null);
                    vecReg.add(INT_TBL_PRELISITM, null);

                    vecReg.add(INT_TBL_PRELISITM2, null);
                    vecReg.add(INT_TBL_CANORI, null);
                    vecReg.add(INT_TBL_PREORI, null);
                    vecReg.add(INT_TBL_DESORI, null);
                    vecReg.add(INT_TBL_DESVENMAX, null);
                    vecReg.add(INT_TBL_NUMFILCOMPVEN, null);
                    vecReg.add(INT_TBL_MAXDESCOM, null);
                    vecReg.add(INT_TBL_DATBODCOM, null);
                    vecReg.add(INT_TBL_CODREGCOT, null);

                    vecReg.add(INT_TBL_CANVENRES, new Double(rst.getDouble("nd_can")));
                    vecReg.add(INT_TBL_PREVTARES, new Double(rst.getDouble("nd_preuni")));
                    vecReg.add(INT_TBL_PORDESRES, new Double(rst.getDouble("nd_pordes")));
                    vecReg.add(INT_TBL_CODITMRES, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_TIPUNIMED, rst.getString("tx_tipunimed"));

                    vecReg.add(INT_TBL_BLOPREVTA, null);
                    vecReg.add(INT_TBL_DESITMORI, null);

                    vecReg.add(INT_TBL_CLIRETBOD, null);
                    vecReg.add(INT_TBL_CANCLIRETBOD, null);

                    vecReg.add(INT_TBL_CODCTAEGR, null);
                    vecReg.add(INT_TBL_PESITM, "nd_pesItmKgr");
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                 
                rst.close();
                
                calculaTot();
                
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */

                String strCo_ForPag = (rstExt.getString("co_forPag") == null) ? "" : rstExt.getString("co_forPag");

                cargarDetPag(conn, String.valueOf(CodEmp), String.valueOf(CodLoc), rstExt.getString("co_cot"));

                stmCab.close();

            }
            objTblMod.setDataModelChanged(false);

        } catch (SQLException Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            System.err.println("ERROR " + Evt.toString());
        }

    }
    
    
    

        
    
    boolean blnEstBufTbl = false;
    
    public void calculaTot() {
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp = 0, dblSub2 = 0, dblCom=0, dblIvaCero=0;
        String strIva = "S";
        int intFilSel = 0;
        blnEstBufTbl = false;
        try
        {
            for (int i = 0; i < tblDat.getRowCount(); i++) {
                if (tblDat.getValueAt(i, INT_TBL_CODITM) != null) {
                    intFilSel = i;
                    dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL) == null || tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals("")) ? 0 : Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
                    dblSub = dblSub + objUti.redondear(dblSub2, intNumDec);
                    strIva = ((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA) == null ? "S" : (tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("") ? "S" : tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true") ? "S" : "N")));
                    if (strIva.equals("S")) {
                        dblTmp = ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL) == null || tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals("")) ? 0 : Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString()));
//                        dblIva = dblIva + (((dblTmp * dblPorIva) == 0) ? 0 : (dblTmp * dblPorIva) / 100);
                        dblIva = dblTmp+dblIva;
                        dblCom = dblTmp+dblCom;
                        
                    } else {
                        dblTmp = ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL) == null || tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals("")) ? 0 : Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString()));
                        dblIva = dblIva + 0;
                        dblCom = dblCom + 0;
                        dblIvaCero = dblTmp + dblIvaCero;
                    }
                    blnEstBufTbl = true;
                }
            }

            dblSubtotalCot = dblSub;
            dblIvaCot = objUti.redondear(dblIva, intNumDec);
            dblTotalCot = dblSubtotalCot + dblIvaCot;
            dblTotalCot = objUti.redondear(dblTotalCot, intNumDec);
            dblSubtotalCot = objUti.redondear(dblSubtotalCot, intNumDec);
            
            dblBaseCero = dblIvaCero;
            dblBaseIva = dblIva;
            
            dblSubtotalCot= objUti.redondear( (dblBaseCero+dblBaseIva), objParSis.getDecimalesMostrar());
            dblIvaCot = objUti.redondear((dblBaseIva*dblPorIva)/100, objParSis.getDecimalesMostrar());
            dblComSol = objUti.redondear((dblBaseIva*dblPorComSol)/100, objParSis.getDecimalesMostrar());

            if(blnIsComSol && dblPorIva==14.00){
                dblTotalCot = objUti.redondear((dblSubtotalCot + dblIvaCot), objParSis.getDecimalesMostrar()) ; 
                dblValorPagar = objUti.redondear(dblTotalCot - dblComSol, objParSis.getDecimalesMostrar()) ;
            }else{
                dblTotalCot = objUti.redondear( dblSubtotalCot + dblIvaCot, objParSis.getDecimalesMostrar()); 
            }
            
            if (stIvaVen.equals("N")) {
                dblTotalCot = dblSubtotalCot;
                dblIvaCot = 0;
            }  
            calcularPesTotDoc();
            blnEstBufTbl = false;
        }
        catch (Exception evt) {
            System.err.println("ERROR " + evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, evt);
        }
    }

    
    
    
    /**
     * Esta función calcula el total del documento.
     */
    private void calcularPesTotDoc()
    {
        int intNumTotFil, i;
        BigDecimal bgdPesTot;
        try
        {
            bgdPesTot=BigDecimal.ZERO;
            intNumTotFil=objTblMod.getRowCount();
            for (i=0; i<intNumTotFil; i++)
            {
                bgdPesTot=bgdPesTot.add(BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_PESTOT)==null?0:objTblMod.getValueAt(i, INT_TBL_PESTOT))));
            }
            
        }
        catch (NumberFormatException e)
        {
            objUti.mostrarMsgErr_F1(jifCnt, e);
            System.err.println("ERROR " + e.toString());
        }
    }
    
    
    private boolean cargarDetPag(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodCot) {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try {

            stmLoc = conn.createStatement();
            Vector vecData = new Vector();
            strSql = "SELECT ne_diacre, fe_ven, nd_porret, mo_pag, ne_diagra , co_tipret  FROM tbm_pagCotven "
                    + " WHERE co_emp=" + strCodEmp + " AND co_loc=" + strCodLoc + " AND co_cot=" + strCodCot + " ORDER BY co_reg";
//            System.out.println("ZafVen01.cargarDetPag " + strSql);
            rstLoc = stmLoc.executeQuery(strSql);

            while (rstLoc.next()) {
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_PAGLIN, "");
                vecReg.add(INT_TBL_PAGCRE, rstLoc.getString("ne_diacre"));
                vecReg.add(INT_TBL_PAGFEC, rstLoc.getString("fe_ven"));
                vecReg.add(INT_TBL_PAGRET, objUti.redondear(rstLoc.getString("nd_porret"), 2));
                vecReg.add(INT_TBL_PAGMON, objUti.redondear(rstLoc.getString("mo_pag"), 2));
                vecReg.add(INT_TBL_PAGGRA, rstLoc.getString("ne_diagra"));
                vecReg.add(INT_TBL_PAGCOD, rstLoc.getString("co_tipret"));
                vecReg.add(INT_TBL_PAGSOP, "");
                vecReg.add(INT_TBL_COMSOL,"N");
                vecData.add(vecReg);
            }
            objTblModPag.setData(vecData);
            tblPag.setModel(objTblModPag);
            vecData = null;
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
            blnRes = true;
        } catch (SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
        } catch (Exception evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, evt);
            System.err.println("ERROR " + evt.toString());
        }
        return blnRes;
    }
       
        
    
    
    
    
        private boolean generaNuevoContenedorItemsMovimientoStock(int intCodEmp, int intCodItm, double dlbCanMov,int intCodBod){
        boolean blnRes=true;
        double dblAux;
        int intCodigoItemGrupo=0, intCodigoItemMaestro=0;
        String strCodTresLetras="";
        try{
            arlDatStkInvItm = new ArrayList(); 
            intCodigoItemGrupo=getCodigoItemGrupo(intCodEmp,intCodItm);
            intCodigoItemMaestro=getCodigoMaestroItemGrupo(intCodEmp,intCodItm);
            strCodTresLetras=getCodigoLetraItem(intCodEmp,intCodItm);
            if(intCodigoItemGrupo==0 || intCodigoItemMaestro==0 || strCodTresLetras.equals("")){
                blnRes=false;
            }
            
            arlRegStkInvItm = new ArrayList();
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,intCodigoItemGrupo);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,intCodItm);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,intCodigoItemMaestro);
            arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM, strCodTresLetras);
            dblAux=dlbCanMov;
            if(dblAux<0){
                dblAux=dblAux*-1;
            }
            arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
            arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,intCodBod );
            arlDatStkInvItm.add(arlRegStkInvItm);
            
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(jifCnt, e);
                blnRes=false;
        }
        return blnRes;
    } 
        
         private String getCodigoLetraItem(int intCodEmp, int intCodItm){
            String strCodLetItm="";
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2 \n";
                    strCadena+=" FROM tbm_inv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
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
            catch(Exception e){
                objUti.mostrarMsgErr_F1(jifCnt, e);
                strCodLetItm="";
            }
            return strCodLetItm;
        }
        
        private int getCodigoItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmGru=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT co_itm \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_itmMae = ( \n";
                    strCadena+="                        select co_itmMae  \n";
                    strCadena+="                        from tbm_Equinv as a1 \n";
                    strCadena+="                        where co_emp="+intCodEmp+" and co_itm="+intCodItm+")  \n";
                    strCadena+=" and x1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        intCodItmGru=rstLoc.getInt("co_itm");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(jifCnt, e);
                intCodItmGru=0;
            }
            return intCodItmGru;
        }
        
        
        private int getCodigoMaestroItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmMae=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT x1.co_itmMae \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" and x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
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
            catch(Exception e){
                objUti.mostrarMsgErr_F1(jifCnt, e);
                intCodItmMae=0;
            }
            return intCodItmMae;
        }
        
        
    
        private double _getObtenerCanGuiaItm(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodCot, int intCodItm) {
            double dblRes = 0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();
                    strSql = "select * from ( select sum(dif) as dif from (  "
                            + " select  a.co_itm,  ( a.nd_can - case when (abs(nd_canvolfac) - abs( nd_canvuefaccli + nd_cannunvuefaccli )) is null then 0 else "
                            + " (abs(nd_canvolfac) - abs( nd_canvuefaccli + nd_cannunvuefaccli )) end  "
                            + " ) as dif   from tbm_detcotven as a  "
                            + " left join tbm_detsoldevven as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelsoldevven and a1.co_tipdoc=a.co_tipdocrelsoldevven and a1.co_doc=a.co_docrelsoldevven "
                            + " and a1.co_reg=a.co_regrelsoldevven )   "
                            + " where a.co_emp=" + intCodEmp + " and a.co_loc=" + intCodLoc + "  and a.co_cot=" + intCodCot + "  and a.co_itm=" + intCodItm + " "
                            + " ) as x ) as x where dif > 0 ";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        dblRes = rstLoc.getDouble("dif");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                }
            } 
            catch (SQLException Evt) {
                dblRes = -1;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            } 
            catch (Exception Evt) {
                dblRes = -1;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
                System.err.println("ERROR " + Evt.toString());
            }

            return dblRes;
        }
    
        
        
        
    private boolean Configurartabla() 
    {
        boolean blnRes = false;
        try {
            

            //Configurar JTable: Establecer el modelo.
            //Almacena los datos
            Vector vecDat = new Vector();   
            //Almacena las cabeceras
            Vector vecCab = new Vector();    
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_ITMALT, "Cod. Item");
//            vecCab.add(INT_TBL_ITMALTCOD3LET,"Cód.Let.");
            vecCab.add(INT_TBL_BUTITM, "..");
            vecCab.add(INT_TBL_BUTSTK, "..");
            vecCab.add(INT_TBL_BUTSOL, "..");
            vecCab.add(INT_TBL_DESITM, "Descripcion");
            vecCab.add(INT_TBL_UNIDAD, "Unidad");
            vecCab.add(INT_TBL_CODBOD, "Bodega");
            vecCab.add(INT_TBL_BUTBOD, "..");
            vecCab.add(INT_TBL_TRATOT, "Tranf.Tot.");
            vecCab.add(INT_TBL_CANMOV, "Cantidad");
            vecCab.add(INT_TBL_PREUNI, "Precio");
            vecCab.add(INT_TBL_PORDES, "%Desc");
            vecCab.add(INT_TBL_BLNIVA, "IVA");
            vecCab.add(INT_TBL_TOTAL, "Total");
            vecCab.add(INT_TBL_PESTOT, "Pes.Tot.");   /* José Marín - 20/Oct/2015 */
            vecCab.add(INT_TBL_CODITM, "coditm");
            vecCab.add(INT_TBL_ESTADO, "estado");
            vecCab.add(INT_TBL_IVATXT, "ivatxt");
            vecCab.add(INT_TBL_PRE_COS, "Pre.Com.");                            //Columna que contiene  precio de compra
            vecCab.add(INT_TBL_DESPRECOM, " %Desc.");
            vecCab.add(INT_TBL_COD_PRO, "Cod.prv.");                            //Columna que contiene el codigo del proveedor
            vecCab.add(INT_TBL_NOM_PRO, "Proveedor");                           //Columna que contiene el Nombre del proveedor
            vecCab.add(INT_TBL_BUT_PRO, "..");
            vecCab.add(INT_TBL_BLNPRE, "blnpre");
            vecCab.add(INT_TBL_ITMALT2, "ItmAlt2");
            vecCab.add(INT_TBL_ITMSER, "Itm.SER.");
            vecCab.add(INT_TBL_ITMTER, " isTerL");
            vecCab.add(INT_TBL_CODBODPRV, "Cód.Bod");
            vecCab.add(INT_TBL_NOMBODPRV, "Nom.Bod");
            vecCab.add(INT_TBL_BUTBODPRV, "");
            vecCab.add(INT_TBL_MARUTI, "Mar.Uti.");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod");
            vecCab.add(INT_TBL_MODNOMITM, "Mod.Nom.Itm");
            vecCab.add(INT_TBL_COLOCREL, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_COTIPDOCREL, "Tip.Doc.Rel");
            vecCab.add(INT_TBL_CODOCREL, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_COREGREL, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_COLOCRELSOL, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_COTIPDOCRELSOL, "Tip.Doc.Rel");
            vecCab.add(INT_TBL_CODOCRELSOL, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_COREGRELSOL, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_COLOCRELOC, "Cod.Loc.Rel");
            vecCab.add(INT_TBL_COTIPDOCRELOC, "Tip.Doc.Rel");
            vecCab.add(INT_TBL_CODOCRELOC, "Cod.Doc.Rel");
            vecCab.add(INT_TBL_COREGRELOC, "Cod.Reg.Rel");
            vecCab.add(INT_TBL_PRELISITM, "Pre.Lis.Itm");
            vecCab.add(INT_TBL_PRELISITM2, "Pre.Lis.Itm2");
            vecCab.add(INT_TBL_CANORI, "");
            vecCab.add(INT_TBL_PREORI, "");
            vecCab.add(INT_TBL_DESORI, "");
            vecCab.add(INT_TBL_DESVENMAX, "Por.Des.Ven.MAx");
            vecCab.add(INT_TBL_NUMFILCOMPVEN, "");
            vecCab.add(INT_TBL_MAXDESCOM, "MaxDesCom");
            vecCab.add(INT_TBL_DATBODCOM, "datCom");
            vecCab.add(INT_TBL_CODREGCOT, "CodReg");
            vecCab.add(INT_TBL_CANVENRES, "CANVENRES");
            vecCab.add(INT_TBL_PREVTARES, "PREVTARES");
            vecCab.add(INT_TBL_PORDESRES, "PORDESRES");
            vecCab.add(INT_TBL_CODITMRES, "CODITMRES");
            vecCab.add(INT_TBL_TIPUNIMED, "TIPUNIMED");
            vecCab.add(INT_TBL_BLOPREVTA, "Blo.Pre.Vta");
            vecCab.add(INT_TBL_DESITMORI, "Des.Itm.");
            vecCab.add(INT_TBL_CLIRETBOD, "Item ret otr bod");
            vecCab.add(INT_TBL_CANCLIRETBOD, "can Itm ret bod");
            vecCab.add(INT_TBL_CODCTAEGR, "Cód.Cta.Egr.");
            vecCab.add(INT_TBL_PESITM, "Pes.Itm."); /* José Marín 2015/Oct/21 */
            

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LINEA);

            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PESTOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);  /* José Marín - 21/Oct/2015 */
            objTblMod.setColumnDataType(INT_TBL_PRE_COS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DESPRECOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PESITM, ZafTblMod.INT_COL_DBL, new Integer(0), null);  /* José Marín - 21-Oct-2015 */
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            ArrayList arlAux = new ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux = null;
            
            

            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUTSTK).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUTSOL).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_TRATOT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(55);
            
            tcmAux.getColumn(INT_TBL_PESTOT).setPreferredWidth(55);  /* José Marín - 20/Oct/2015 */
            
            tcmAux.getColumn(INT_TBL_PRE_COS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESPRECOM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_NOM_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUT_PRO).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_BUTBODPRV).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_PESITM).setPreferredWidth(10);
            
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
           
            
            blnRes = true;
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(jifCnt, e);
            System.err.println("Configurartabla.ERROR " + e.toString());
        }
        return blnRes;
    }

    
    
    private boolean FormaRetencion(java.sql.Connection conn, int intCodEmp) {
        boolean blnRes = false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rstTmp;
        try {
            if (conn != null) {
                stmTmp = conn.createStatement();
                String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp=" + intCodEmp+ " AND tx_tipmot in ('B','S','T') ";
                rstTmp = stmTmp.executeQuery(sql);
                while (rstTmp.next()) {
                    if (rstTmp.getString("tx_tipmot").equals("B")) {
                        intCodMotBien = rstTmp.getInt("co_mot");
                    } else if (rstTmp.getString("tx_tipmot").equals("S")) {
                        intCodMotServ = rstTmp.getInt("co_mot");
                    } else if (rstTmp.getString("tx_tipmot").equals("T")) {
                        intCodMotTran = rstTmp.getInt("co_mot");
                    }
                    blnRes = true;
                }
                rstTmp.close();
                stmTmp.close();
                rstTmp = null;
                stmTmp = null;
            }
        } catch (SQLException Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        } catch (Exception Evt) {
            System.err.println("ERROR " + Evt.toString());
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
        }
        return blnRes;
    }
    
    
    /**
     * asignaSecEmpGrpRelacionada
     * Asigna numeracion secuencial por empresa y grupo 
     * @param connLoc
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDocFacEle
     * @param intCodDoc
     * @return 
     */

        private boolean asignaSecEmpGrpRelacionada(java.sql.Connection connLoc,int intCodEmp, int intCodLoc, int intCodTipDocFacEle, int intCodDoc) {
            boolean blnRes = true;
            java.sql.Statement stmLoc01;
            int intSecEmp = 0, intSecGrp = 0;
            try {
                if (connLoc != null) {
                    stmLoc01 = connLoc.createStatement();
                    
                    intSecEmp = objUltDocPrint.getNumSecDoc(connLoc, intCodEmp);
                    intSecGrp = objUltDocPrint.getNumSecDoc(connLoc, objParSis.getCodigoEmpresaGrupo());

                    strSql = " UPDATE tbm_cabmovinv SET ne_secEmp="+intSecEmp+", ne_SecGrp="+intSecGrp+" "
                           + " WHERE co_emp=" +intCodEmp+ " AND co_loc=" +intCodLoc+ " "
                           + " AND co_tipDoc="+intCodTipDocFacEle+" AND co_doc="+intCodDoc+"";
//                    System.out.println("asignaSecEmpGrpRelacionada "+strSql);
                    stmLoc01.executeUpdate(strSql);
                    stmLoc01.close();
                    stmLoc01 = null;

                }
            } catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
        }

        /**
         * asignaNumeroFac
         * Asigna el numero de la factura,en tbm_cabMovInv y en tbm_cabDia, aumenta la numeracion
         * @param conIns
         * @param intCodEmp
         * @param intCodLoc
         * @param intCodTipDocFacEle
         * @param intCodDoc
         * @param intCodCot
         * @return 
         */
        public boolean asignaNumeroFac(java.sql.Connection conIns, int intCodEmp,int intCodLoc, int intCodTipDocFacEle, int intCodDoc, int intCodCot) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc, stmLocIns;
            java.sql.ResultSet rstLoc;
            String strSql = "";
            String strSqlIns = "";
            int intNumDoc = 0;
            int intNumDocGuia = 0;
            try 
            {
                if (conIns != null){
                    stmLoc = conIns.createStatement();
                    strSql = "SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum "
                            + " FROM tbm_cabTipDoc WHERE co_emp=" +intCodEmp+ " "
                            + " AND co_loc=" +intCodLoc+ " AND co_tipDoc=" + intCodTipDocFacEle;
//                    System.out.println("numero " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intNumDoc = rstLoc.getInt("ultnum");
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    
                    
                    strSqlIns += "   UPDATE tbm_cabTipDoc SET ne_ultDoc=" + intNumDoc + " WHERE co_emp=" +intCodEmp+ " "
                            + " AND co_loc=" +intCodLoc+ " AND co_tipDoc=" + intCodTipDocFacEle;
                    strSqlIns += ";UPDATE tbm_cabmovinv SET ne_numdoc=" +intNumDoc+", ne_numgui="+intNumDocGuia+", st_reg='A' , st_imp='S'  "
                            + " WHERE co_emp=" +intCodEmp+ " AND co_loc=" +intCodLoc+ " AND co_tipdoc=" + intCodTipDocFacEle + " "
                            + " AND co_doc=" + intCodDoc + " and st_reg not in ('I','E')  AND ne_numcot=" + intCodCot;
                    strSqlIns += " ; UPDATE tbm_cabdia SET tx_numdia='" + intNumDoc + "' WHERE co_emp=" +intCodEmp+ " AND co_loc=" +intCodLoc+ " AND co_tipdoc=" + intCodTipDocFacEle + " "
                            + " AND co_dia=" + intCodDoc + " and st_reg not in ('I','E')  ";
                    System.out.println("asignaNumeroFac "+strSqlIns);
                    stmLocIns = conIns.createStatement();
                    stmLocIns.executeUpdate(strSqlIns);
                    stmLocIns.close();
                    stmLocIns = null;
                    intNumFacElec=intNumDoc;
                    blnRes = true;
                }
            } 
            catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } 
            catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
        }
        
        /**
         * 
         * Va almacenando las cotizaciones-facturas que son generadas por el servicio
         * 
         * @param rstExt viene cargado con las cotizaciones a procesar
         * @param intCodTipDoc
         * @param intNumDoc numero de la factura
         * @param intCodDoc codigo de la factura
         */
        private void guardaDocumentosGenerado(java.sql.ResultSet rstExt,int intCodTipDoc,int intNumDoc,int intCodDoc,String strCorEle){
            try 
            {
                arlDocGenReg = new ArrayList();
                arlDocGenReg.add(INT_ARL_COD_EMP_COT,rstExt.getInt("co_emp") );
                arlDocGenReg.add(INT_ARL_COD_LOC_COT,rstExt.getInt("co_loc") );
                arlDocGenReg.add(INT_ARL_COD_COT,rstExt.getInt("co_cot") );
                arlDocGenReg.add(INT_ARL_COD_EMP_FAC,rstExt.getInt("co_emp") );
                arlDocGenReg.add(INT_ARL_COD_LOC_FAC,rstExt.getInt("co_loc") );
                arlDocGenReg.add(INT_ARL_COD_TIP_DOC_FAC,intCodTipDoc );
                arlDocGenReg.add(INT_ARL_COD_DOC_FAC,intCodDoc );
                arlDocGenReg.add(INT_ARL_NUM_FAC,intNumDoc );
                arlDocGenReg.add(INT_ARL_NOM_CLI,strNomCli );
                arlDocGenReg.add(INT_ARL_COR_ELE_VEN,strCorEle);
                arlDocGenDat.add(arlDocGenReg);     
            }
            catch (SQLException Evt) {
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } catch (Exception Evt) {
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }                                              
        }
        
        
        private String strMsjCorEle;
        
        /***
         * 
         * Para envios de correo electronico a los vendedores 
         * y saber que su factura ha sido generada
         * 
         */
         
         
        private void enviaCorreosElectronicos(){
            try{
                for(int i=0;i<arlDocGenDat.size();i++){
                    strMsjCorEle="";
                    strMsjCorEle+="<HTML> La cotizacion Numero: "+ objUti.getStringValueAt(arlDocGenDat, i,INT_ARL_COD_COT) + ", ";
                    strMsjCorEle+=" del cliente: "+objUti.getStringValueAt(arlDocGenDat, i,INT_ARL_NOM_CLI);
                    strMsjCorEle+=" se ha Facturado con Numero: <b>" + objUti.getStringValueAt(arlDocGenDat, i,INT_ARL_NUM_FAC)+"</b> ";
                    strMsjCorEle+=" </HTML> ";
                    switch(objUti.getIntValueAt(arlDocGenDat, i,INT_ARL_COD_EMP_COT))
                    {
                        case 1: objCorEle.enviarCorreoMasivo(objUti.getStringValueAt(arlDocGenDat, i,INT_ARL_COR_ELE_VEN),strTitCorEle + " TUVAL ",strMsjCorEle ); break; 
                        case 2: objCorEle.enviarCorreoMasivo(objUti.getStringValueAt(arlDocGenDat, i,INT_ARL_COR_ELE_VEN),strTitCorEle + " CASTEK ",strMsjCorEle ); break;
                        case 4: objCorEle.enviarCorreoMasivo(objUti.getStringValueAt(arlDocGenDat, i,INT_ARL_COR_ELE_VEN),strTitCorEle + " DIMULTI ",strMsjCorEle ); break;    
                    }
                }
            }
            catch (Exception Evt) {
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } 
            
        }
        
        
        /**
         * Solicita la impresion de todas las OD generadas 
         * 
         */
         
         private void enviaImprimirOdLocal(java.sql.Connection conIns){
            try{
                for(int i=0;i<arlDocGenDat.size();i++){
                    objZafGenOD.imprimirOdLocal(conIns,objUti.getIntValueAt(arlDocGenDat, i,INT_ARL_COD_EMP_FAC),
                                                       objUti.getIntValueAt(arlDocGenDat, i,INT_ARL_COD_LOC_FAC),
                                                       objUti.getIntValueAt(arlDocGenDat, i,INT_ARL_COD_TIP_DOC_FAC),
                                                       objUti.getIntValueAt(arlDocGenDat, i,INT_ARL_COD_DOC_FAC),objCfgSer.getIpHost());
                }
            }
            catch (Exception Evt) {
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } 
        }
    
     
        
   
        private boolean insertarTablaSeguimiento(java.sql.Connection conn,java.sql.ResultSet rstExt,int intCodTipDoc,int intCodDoc){
            boolean blnRes = true, blnNueIns=true;
            java.sql.Statement stmLoc, stmLoc01;
            java.sql.ResultSet rstLoc;    
            int intNumDoc=0,intCodSeg=0;
            try{
                if (conn != null) 
                {
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();
                    
                    strSql = " SELECT MAX(co_reg)+1 as co_reg, a1.co_seg  \n";
                    strSql+= " FROM tbm_cabSegMovInv as a1 \n";
                    strSql+= " WHERE a1.co_seg = ( \n";
                    strSql+= "     SELECT co_seg \n";
                    strSql+= "     FROM tbm_cabSegMovInv \n";
                    strSql+= "     WHERE co_empRelCabCotVen="+rstExt.getString("co_emp")+" AND  \n";
                    strSql+= "     co_locRelCabCotVen="+rstExt.getString("co_loc")+" AND co_cotRelCabCotVen="+rstExt.getString("co_cot")+" \n  ";
                    strSql+= " ) \n GROUP BY a1.co_seg";                        
                                    
                    System.out.println("ObtenerNumeroSeguimiento: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        intNumDoc = rstLoc.getInt("co_reg");
                        intCodSeg = rstLoc.getInt("co_seg");
                    }
                    strSql=" ";
                    strSql+=" INSERT INTO tbm_cabSegMovInv (co_seg,co_reg,co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv) ";
                    strSql+=" VALUES ("+intCodSeg+", "+intNumDoc+", "+rstExt.getInt("co_emp")+", "+rstExt.getInt("co_loc")+","+intCodTipDoc+","+intCodDoc+" );";
                    System.out.println("InsertarEnTablaSeguimiento: "+strSql);
                    stmLoc01.executeUpdate(strSql);
                    
                    
                    
                    if(blnNueIns){
                        if(insertarTablaRelacionadas(conn,rstExt.getInt("co_emp"), rstExt.getInt("co_loc"),intCodTipDoc,intCodDoc,rstExt.getInt("co_cot"))){
                            System.out.println("InsertarRelacion OK: ");
                        }
                        else{
                            blnRes=false;
                        }
                    }
                    else{
                        /* ya no se usa JM  */
                        /* INSERTAR LA FACTURA CONTRA EL INGRESO tbr_detMovInv */
//                        strSql="";
//                        strSql+=" INSERT INTO tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,st_reg, \n";
//                        strSql+="                           co_emprel,co_locrel,co_tipdocrel,co_docrel,co_regrel,st_regrep) \n";
//                        strSql+=" SELECT a.co_emp,a.co_loc,a.co_tipDoc,a.co_doc,a.co_reg,'A' as st_reg,b.co_empRelCabMovInv, \n";
//                        strSql+="        b.co_locRelCabMovInv,b.co_tipDocRelCabMovInv, b.co_docRelCabMovInv,b.co_regRel,'I' \n";
//                        strSql+=" FROM ( \n";
//                        strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
//                        strSql+="       FROM( \n";
//                        strSql+="            SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv \n";
//                        strSql+="            FROM tbm_cabSegMovInv \n";
//                        strSql+="            WHERE co_empRelCabMovInv="+rstExt.getInt("co_emp")+" AND co_locRelCabMovInv="+rstExt.getInt("co_loc")+" AND \n";
//                        strSql+="                  co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" \n";
//                        strSql+="       ) as a1  \n";
//                        strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
//                        strSql+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n";
//                        strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
//                        strSql+=" ) as a \n";
//                        strSql+=" INNER JOIN ( \n";
//                        strSql+="   SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, \n";
//                        strSql+="          a1.co_docRelCabMovInv,a3.co_reg as co_regRel, a2.co_cli, a3.co_itm, a3.nd_can \n";
//                        strSql+="   FROM tbm_cabSegMovInv as a1 \n";
//                        strSql+="   INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
//                        strSql+="                                      a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
//                        strSql+="   INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
//                        strSql+="   RIGHT JOIN tbr_detMovInv as a5 ON (a2.co_emp=a5.co_empRel AND a2.co_loc=a5.co_locRel AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel) \n";
//                        strSql+="   WHERE a1.co_seg=(select co_seg from tbm_cabSegMovInv \n";
//                        strSql+="                    where co_empRelCabCotVen="+rstExt.getInt("co_emp")+" and co_locRelCabCotVen="+rstExt.getInt("co_loc")+" \n";
//                        strSql+="                          and co_cotRelCabCotVen="+rstExt.getString("co_cot")+" ) and a3.nd_can > 0 \n ";
//                        strSql+="    GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,  ";
//                        strSql+="               a1.co_docRelCabMovInv,a3.co_reg , a2.co_cli, a3.co_itm, a3.nd_can  ";
//                        strSql+=" ) as b ON (a.co_itm=b.co_itm); \n";
//                        System.out.println("INSERTAR LA FACTURA CONTRA EL INGRESO \n" + strSql);
//                        /* INSERTAR LA FACTURA CONTRA EL INGRESO tbr_detMovInv */
//                        stmLoc01.executeUpdate(strSql);

                        
                    }
                    
                    
                    if( isExisteOC(conn, rstExt,intCodTipDoc,intCodDoc)  ){
                            strSql="";
                            strSql+=" INSERT INTO tbr_cabMovInv ( co_emp, co_loc, co_tipdoc,  co_doc , st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, st_regrep  ) \n";
                            strSql+=" SELECT DISTINCT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, 'A' as st_reg, b.co_empRelCabMovInv,b.co_locRelCabMovInv, b.co_tipDocRelCabMovInv, b.co_docRelCabMovInv,'I'   \n";
                            strSql+=" FROM ( \n";
                            strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
                            strSql+="       FROM ( \n";
                            strSql+="               SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv \n";
                            strSql+="               FROM tbm_cabSegMovInv  \n";
                            strSql+="               WHERE co_empRelCabMovInv="+rstExt.getInt("co_emp")+" AND co_locRelCabMovInv="+rstExt.getInt("co_loc")+" AND \n";
                            strSql+="                  co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" \n";
                            strSql+="       ) as a1 \n";
                            strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND \n";
                            strSql+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n";
                            strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                            strSql+="       INNER JOIN tbm_inv as a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm) \n";
                            strSql+="       WHERE a3.tx_codAlt like '%L'  \n";
                            strSql+=" ) as a \n";
                            strSql+=" INNER JOIN ( \n";
                            strSql+="   SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv,a3.co_reg as co_regRel, a2.co_cli, a3.co_itm, a3.nd_can \n";
                            strSql+="   FROM tbm_cabSegMovInv as a1 \n";
                            strSql+="   INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                            strSql+="                                       a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                            strSql+="   INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                            strSql+="   WHERE a1.co_seg=(select co_seg from tbm_cabSegMovInv \n";
                            strSql+="                    where co_empRelCabCotVen="+rstExt.getInt("co_emp")+" and co_locRelCabCotVen="+rstExt.getInt("co_loc")+" \n";
                            strSql+="                          and co_cotRelCabCotVen="+rstExt.getString("co_cot")+" ) and a3.nd_can > 0 \n ";
                            strSql+="    GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,  ";
                            strSql+="               a1.co_docRelCabMovInv,a3.co_reg , a2.co_cli, a3.co_itm, a3.nd_can          ";
                            strSql+=" ) as b ON (a.co_itm=b.co_itm ); \n";

                            strSql+=" INSERT INTo tbr_detMovInv ( co_emp, co_loc, co_tipdoc,  co_doc, co_reg , st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep  ) \n";
                            strSql+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc,a.co_reg, 'A' as st_reg, b.co_empRelCabMovInv,b.co_locRelCabMovInv, b.co_tipDocRelCabMovInv, b.co_docRelCabMovInv,b.co_regRel,'I'   \n";
                            strSql+=" FROM ( \n";
                            strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
                            strSql+="       FROM ( \n";
                            strSql+="               SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv \n";
                            strSql+="               FROM tbm_cabSegMovInv  \n";
                            strSql+="               WHERE co_empRelCabMovInv="+rstExt.getInt("co_emp")+" AND co_locRelCabMovInv="+rstExt.getInt("co_loc")+" AND \n";
                            strSql+="                  co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" \n";
                            strSql+="       ) as a1 \n";
                            strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND \n";
                            strSql+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n";
                            strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                            strSql+="       INNER JOIN tbm_inv as a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm) \n";
                            strSql+="       WHERE a3.tx_codAlt like '%L'  \n";
                            strSql+=" ) as a \n";
                            strSql+=" INNER JOIN ( \n";
                            strSql+="   SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv,a3.co_reg as co_regRel, a2.co_cli, a3.co_itm, a3.nd_can \n";
                            strSql+="   FROM tbm_cabSegMovInv as a1 \n";
                            strSql+="   INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                            strSql+="                                       a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                            strSql+="   INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                            strSql+="   WHERE a1.co_seg=(select co_seg from tbm_cabSegMovInv \n";
                            strSql+="                    where co_empRelCabCotVen="+rstExt.getInt("co_emp")+" and co_locRelCabCotVen="+rstExt.getInt("co_loc")+" \n";
                            strSql+="                          and co_cotRelCabCotVen="+rstExt.getString("co_cot")+" GROUP BY co_seg) and a3.nd_can > 0 \n ";
                            strSql+="    GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,  ";
                            strSql+="               a1.co_docRelCabMovInv,a3.co_reg , a2.co_cli, a3.co_itm, a3.nd_can ";                    
                            strSql+=" ) as b ON (a.co_itm=b.co_itm ); \n";
                            System.out.println("isExisteOC: " + strSql);
                            stmLoc01.executeUpdate(strSql);
                    }
                    rstLoc.close();
                    rstLoc = null;
                    stmLoc.close();
                    stmLoc = null;
                    stmLoc01.close();
                    stmLoc01 = null;
                    
                }
            }
            catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
            
        }
        
        
            /*PARA LOS DOCUMENTOS GENERADOS*/
    private ArrayList arlRegFacVen, arlDatFacVen;
    private ArrayList arlRegIngGen, arlDatIngGen;
    private static final int INT_ARL_COD_EMP_REL=0;
    private static final int INT_ARL_COD_LOC_REL=1;
    private static final int INT_ARL_COD_TIP_DOC_REL=2;
    private static final int INT_ARL_COD_DOC_REL=3;
    private static final int INT_ARL_COD_REG_REL_AUX=4;
    private static final int INT_ARL_COD_ITM_REL=5;
    private static final int INT_ARL_CAN_REL=6;
    private static final int INT_ARL_COD_CAN_UTI=7;
    
        
        /**
         * Nueva forma de insert
         * 
         * 
         * @param conn
         * @param rstExt
         * @param intCodTipDoc
         * @param intCodDoc
         * @return 
         */
        
        private boolean insertarTablaRelacionadas(java.sql.Connection conn,int intCodEmp, int intCodLoc,int intCodTipDoc,int intCodDoc,int intCodCot){
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLocFacVen,rstLocIng;    
            boolean blnRes = true;
            try{
                if(conn!=null){
                    stmLoc = conn.createStatement();
                    arlDatFacVen = new ArrayList();
                    arlDatIngGen = new ArrayList();
                    
                    strSql="";
                    strSql+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can \n";
                    strSql+="       FROM( \n";
                    strSql+="            SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv \n";
                    strSql+="            FROM tbm_cabSegMovInv \n";
                    strSql+="            WHERE co_empRelCabMovInv="+intCodEmp+" AND co_locRelCabMovInv="+intCodLoc+" AND \n";
                    strSql+="                  co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" \n";
                    strSql+="       ) as a1  \n";
                    strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                    strSql+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n";
                    strSql+="       INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                    System.out.println("insertarTablaRelacionadas... "+strSql);
                    rstLocFacVen = stmLoc.executeQuery(strSql);
                    while(rstLocFacVen.next()){
                        arlRegFacVen = new ArrayList();
                        arlRegFacVen.add(INT_ARL_COD_EMP_REL, rstLocFacVen.getInt("co_emp"));
                        arlRegFacVen.add(INT_ARL_COD_LOC_REL, rstLocFacVen.getInt("co_loc"));
                        arlRegFacVen.add(INT_ARL_COD_TIP_DOC_REL, rstLocFacVen.getInt("co_tipDoc"));
                        arlRegFacVen.add(INT_ARL_COD_DOC_REL, rstLocFacVen.getInt("co_doc"));
                        arlRegFacVen.add(INT_ARL_COD_REG_REL_AUX, rstLocFacVen.getInt("co_reg"));
                        arlRegFacVen.add(INT_ARL_COD_ITM_REL, rstLocFacVen.getInt("co_itm"));
                        arlRegFacVen.add(INT_ARL_CAN_REL, rstLocFacVen.getDouble("nd_can"));
                        arlRegFacVen.add(INT_ARL_COD_CAN_UTI, 0.00);
                        arlDatFacVen.add(arlRegFacVen);     
                    }
                    rstLocFacVen.close();
                    rstLocFacVen=null;

                    strSql="";
                    strSql+="   SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, \n";
                    strSql+="          a1.co_docRelCabMovInv,a3.co_reg as co_regRel, a3.co_itm, a3.nd_can \n";
                    strSql+="   FROM tbm_cabSegMovInv as a1 \n";
                    strSql+="   INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n";
                    strSql+="                                      a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) \n";
                    strSql+="   INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                    strSql+="   RIGHT JOIN tbr_detMovInv as a5 ON (a2.co_emp=a5.co_empRel AND a2.co_loc=a5.co_locRel AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel) \n";
                    strSql+="   WHERE a1.co_seg=(select co_seg from tbm_cabSegMovInv \n";
                    strSql+="                    where co_empRelCabCotVen="+intCodEmp+" and co_locRelCabCotVen="+intCodLoc+" \n";
                    strSql+="                          and co_cotRelCabCotVen="+intCodCot+" GROUP BY co_seg ) and a3.nd_can > 0 \n ";
                    strSql+="    GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv,  ";
                    strSql+="               a1.co_docRelCabMovInv,a3.co_reg , a2.co_cli, a3.co_itm, a3.nd_can  ";
                    strSql+="    ORDER BY a3.nd_can DESC ";
                    System.out.println("insertarTablaRelacionadas 2 ... "+strSql);
                    rstLocIng = stmLoc.executeQuery(strSql);
                    while(rstLocIng.next()){
                        arlRegIngGen = new ArrayList();
                        arlRegIngGen.add(INT_ARL_COD_EMP_REL, rstLocIng.getInt("co_empRelCabMovInv"));
                        arlRegIngGen.add(INT_ARL_COD_LOC_REL, rstLocIng.getInt("co_locRelCabMovInv"));
                        arlRegIngGen.add(INT_ARL_COD_TIP_DOC_REL, rstLocIng.getInt("co_tipDocRelCabMovInv"));
                        arlRegIngGen.add(INT_ARL_COD_DOC_REL, rstLocIng.getInt("co_docRelCabMovInv"));
                        arlRegIngGen.add(INT_ARL_COD_REG_REL_AUX, rstLocIng.getInt("co_regRel"));
                        arlRegIngGen.add(INT_ARL_COD_ITM_REL, rstLocIng.getInt("co_itm"));
                        arlRegIngGen.add(INT_ARL_CAN_REL, rstLocIng.getDouble("nd_can"));
                        arlRegIngGen.add(INT_ARL_COD_CAN_UTI, 0.00);
                        arlDatIngGen.add(arlRegIngGen);     
                    }
                    rstLocIng.close();
                    rstLocIng=null;
                    
                    String strIng="";
                    for(int i=0;i<arlDatFacVen.size();i++){
                        for(int b=0;b<arlDatIngGen.size();b++){
                            if(objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_ITM_REL)==objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_ITM_REL)){
                                if(objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_CAN_REL)>objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI)){
                                    strSql="";
                                    strSql+=" INSERT INTO tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,st_reg, \n";
                                    strSql+="                           co_emprel,co_locrel,co_tipdocrel,co_docrel,co_regrel) \n";
                                    strSql+=" VALUES ("+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_EMP_REL)+",";
                                    strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_LOC_REL)+"," ;
                                    strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_TIP_DOC_REL)+",";
                                    strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_DOC_REL)+",";
                                    strSql+=" "+objUti.getIntValueAt(arlDatFacVen, i, INT_ARL_COD_REG_REL_AUX)+",'A', \n";
                                    strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_EMP_REL)+",";
                                    strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_LOC_REL)+",";
                                    strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_TIP_DOC_REL)+","; 
                                    strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_DOC_REL)+",";
                                    strSql+=" "+objUti.getIntValueAt(arlDatIngGen, b, INT_ARL_COD_REG_REL_AUX)+");";
                                    strIng+=strSql;
                                    if(objUti.getDoubleValueAt(arlDatFacVen, i, INT_ARL_CAN_REL)<=objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_CAN_REL)){
                                        // FACTURA 5    INGRESO 10 // le sumo lo de la factura
                                        objUti.setDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI, (objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI)+objUti.getDoubleValueAt(arlDatFacVen, i, INT_ARL_CAN_REL)));
                                    }
                                    else{
                                        //factura 10 ing 4// le sumo el ingreso
                                        objUti.setDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI, (objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_COD_CAN_UTI)+objUti.getDoubleValueAt(arlDatIngGen, b, INT_ARL_CAN_REL)));
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("insertarTablaRelacionadas: \n" + strIng);
                    stmLoc.executeUpdate(strIng);
                    
                    stmLoc.close();
                    stmLoc=null;
                }
            }
            catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } 
            catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
        }
        
        
    
        
        private boolean isExisteOC(java.sql.Connection conn, java.sql.ResultSet rstExt,int intCodTipDoc,int intCodDoc){
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strCadena;
            try{
                stmLoc=conn.createStatement();
                strCadena="";
                strCadena+=" SELECT a1.co_itm ";
                strCadena+=" FROM tbm_detMovInv as a1  ";
                strCadena+=" WHERE a1.co_emp="+rstExt.getString("co_emp")+" AND a1.co_loc="+rstExt.getString("co_loc")+" AND ";
                strCadena+=" a1.co_tipDoc="+intCodTipDoc+" AND a1.co_doc="+intCodDoc+" AND a1.tx_codAlt like '%L' ";
                System.out.println("IsExisteOC" + strCadena);
                rstLoc = stmLoc.executeQuery(strCadena);
                if (rstLoc.next()) {
                   blnRes=true;
                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
            catch (SQLException Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                System.err.println("ERROR " + Evt.toString());
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
        }
       
        
    private int bodegaPorEmpresaGrupo(int intCodEmp, int intCodBodEmp){
        int intCodBodGrp=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strCadena; 
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strCadena="";
                strCadena+=" SELECT co_emp, co_bod, co_empGrp, co_bodGrp  ";
                strCadena+="  FROM tbr_bodEmpBodGrp  ";
                strCadena+=" WHERE co_bod="+ intCodBodEmp ;
                strCadena+=" AND co_emp="+intCodEmp +" AND co_empGrp="+objParSis.getCodigoEmpresaGrupo();
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                        intCodBodGrp=rstLoc.getInt("co_bodGrp");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(Exception Evt) { 
            objUti.mostrarMsgErr_F1(null, Evt);  
            System.err.println("ERROR "  + Evt.toString());
            intCodBodGrp=0;
        }  
        
        return intCodBodGrp;
    }
    
    
    
        private int intBodGrp(int intCodEmp,int intCodBodEmp){
            int intCodBodGrp=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT co_empGrp, co_bodGrp  ";
                    strCadena+="  FROM tbr_bodEmpBodGrp  ";
                    strCadena+=" WHERE co_bod="+ intCodBodEmp ;
                    strCadena+=" AND co_emp="+intCodEmp +" AND co_empGrp="+objParSis.getCodigoEmpresaGrupo();
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                            intCodBodGrp=rstLoc.getInt("co_bodGrp");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodBodGrp=0;
            }
            return intCodBodGrp; 
        }
        
        
        private boolean prepararEgreso(java.sql.Connection conn,java.sql.ResultSet rstExt,int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
            java.sql.Statement stmLoc,stmLoc01;
            java.sql.ResultSet rstLoc,rstLoc01;
            boolean blnRes=true, blnCanNecSerCon=false, blnIsServicio=false, blnIsSer=false, blnIsRes=false, blnIsParcial=false;
            double dblCanRem=0.00 , dblCanFac=0.00, dblCanEgrBod=0;
            double dblCanCotOrigen=0.00, dblCanFacCotOrigen=0.00, dblCanCanFacOrigen=0.00;
            int intCodBodIng=0, intCodBodEgr=0, intCodBodGrp;
            String strCodAlt="",strItmSer="",strCadena;
            try{
                if(conn!=null){
                    System.out.println("MOMENTO DE FACUTRACION: " + rstExt.getString("tx_momGenFac"));
                    stmLoc = conn.createStatement();
                    stmLoc01 = conn.createStatement();
 
                    arlDatIng = obtenerCantidadRemota(conn, CodEmp, CodLoc, CodTipDoc, CodDoc);
                    /* JM: 2018-Oct-16 */
                    strSql="";
                    strSql+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg,a2.co_bod, a2.co_itm,a2.tx_codAlt, a2.nd_can, \n";
                    strSql+="        CASE WHEN a5.co_empRel IS NULL THEN 'N' ELSE 'S' END AS IsRes, CASE WHEN a6.co_empRel IS NULL THEN 'N' ELSE 'S' END AS IsParcial,  \n";
                    strSql+="        CASE WHEN a7.nd_canFac IS NULL THEN 0 ELSE a7.nd_canFac END AS nd_canFacCotVenOrigen,   \n";
                    strSql+="        CASE WHEN a7.nd_can IS NULL THEN 0 ELSE a7.nd_can END AS nd_canCotVenOrigen,   \n";
                    strSql+="        CASE WHEN a7.nd_canCan IS NULL THEN 0 ELSE a7.nd_canCan END AS nd_canCanCotVenOrigen   \n";
                    strSql+=" FROM tbm_cabMovInv AS a1  \n";
                    strSql+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                    strSql+="                                    a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)    \n";
                    strSql+=" INNER JOIN tbm_cabCotVen as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.ne_numCot=a3.co_cot) \n";
                    strSql+=" INNER JOIN tbm_detCotVen as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_cot=a4.co_cot AND a2.co_itm=a4.co_itm) \n";
                    strSql+=" LEFT OUTER JOIN tbr_detCotVen as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND  \n";
                    strSql+="                                         a4.co_cot=a5.co_cot AND a4.co_reg=a5.co_reg AND a5.tx_tipRel='R') \n";
                    strSql+=" LEFT OUTER JOIN tbr_detCotVen as a6 ON (a4.co_emp=a6.co_emp AND a4.co_loc=a6.co_loc AND  \n";
                    strSql+="                                         a4.co_cot=a6.co_cot AND a4.co_reg=a6.co_reg AND a6.tx_tipRel='P') \n";
                    strSql+=" LEFT OUTER JOIN tbm_detCotVen as a7 ON (a6.co_empRel=a7.co_emp AND a6.co_locRel=a7.co_loc AND a6.co_cotRel=a7.co_cot AND a6.co_regRel=a7.co_reg)  \n";
                    strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" AND a1.co_doc="+CodDoc+" \n";
                    strSql+=" ORDER BY a2.co_reg  ";
                    System.out.println("J: " + strSql);
                    rstLoc=stmLoc.executeQuery(strSql);     
                    while(rstLoc.next()){
                        strCodAlt = rstLoc.getString("tx_codAlt");
                        dblCanFac=(rstLoc.getDouble("nd_can")*-1);
                        if(rstLoc.getString("IsRes").equals("S")){
                            blnIsRes=true;
                            blnIsParcial=false;
                        }
                        else if(rstLoc.getString("IsParcial").equals("S")){
                            blnIsRes=false;
                            blnIsParcial=true;
                        }
                        else{
                            blnIsParcial=false;
                            blnIsRes=false;
                        }
                        
                        strItmSer="";
                        strCadena = "SELECT st_ser FROM tbm_inv WHERE co_emp="+rstLoc.getInt("co_emp")+" ";
                        strCadena+=" AND co_itm="+rstLoc.getInt("co_itm") ; // NO SI TRANSPORTE T
                        rstLoc01 = stmLoc01.executeQuery(strCadena);
                        if(rstLoc01.next()){
                            blnIsSer=rstLoc01.getString("st_ser").equals("N")?false:true;
                            strItmSer = rstLoc01.getString("st_ser"); 
                        }
                        rstLoc01.close();
                        rstLoc01=null;
                        
                        String strTerminal=strCodAlt.substring(strCodAlt.length()-1);
                        int intBodGrp = intBodGrp(CodEmp, rstLoc.getInt("co_bod"));
                        
                        String strCfgCon = objUti.getCfgConfirma(jifCnt, objParSis,  objParSis.getCodigoEmpresaGrupo(), intBodGrp, strTerminal);                    
                        if(strCfgCon.equals("S") && blnIsSer==false){
                            if(strCodAlt.substring(strCodAlt.length()-1).equals("L") && intBodGrp==15){
                                if(obtenerSt_merIngEgrFisBodOC(conn, rstExt.getInt("co_seg"),rstExt.getInt("co_emp") ,rstLoc.getInt("co_itm"))){
                                    blnIsServicio=false;
                                    blnCanNecSerCon=true;
                                }else{
                                    blnIsServicio=true; blnCanNecSerCon=false; 
                                }
                            }else{
                                blnIsServicio=false;
                                blnCanNecSerCon=true;
                            }
                        }else{
                            blnIsServicio=true;/*SI ES UN SERVICIO*/ blnCanNecSerCon=false; /* NO NECESITA CONFIRMACION */ 
                        }
                          
                        intCodBodGrp=bodegaPorEmpresaGrupo(rstLoc.getInt("co_emp"), rstLoc.getInt("co_bod"));  
                        dblCanRem=0.00;
                        for(int i=0;i<arlDatIng.size();i++){
                            if(rstLoc.getInt("co_reg")==objUti.getIntValueAt(arlDatIng, i,INT_ARL_COD_REG_REL)){//PRIMERO ENCONTRAMOS EL REGISTRO DE LA RELACION
//                                dblCanRem+=objUti.getDoubleValueAt(arlDatIng, i, INT_ARL_CAN_ITM); // LA CANTIDAD DEL INGRESO
                                intCodBodEgr = objUti.getIntValueAt(arlDatIng, i,INT_ARL_BOD_EGR);
                                intCodBodIng = objUti.getIntValueAt(arlDatIng, i,INT_ARL_BOD_ING);
                                if(intCodBodEgr==intCodBodIng){
                                     
                                }
                                else{
                                    dblCanRem+=objUti.getDoubleValueAt(arlDatIng, i, INT_ARL_CAN_ITM); // LA CANTIDAD DEL INGRESO
                                }
                            }
                        }
                        if(blnIsParcial){
                            System.out.println("nd_canCotVenOrigen " + rstLoc.getDouble("nd_canCotVenOrigen"));
                            System.out.println("nd_canFacCotVenOrigen " + rstLoc.getDouble("nd_canFacCotVenOrigen"));
                            System.out.println("nd_canCanCotVenOrigen " + rstLoc.getDouble("nd_canCanCotVenOrigen"));
                            
                            dblCanCotOrigen=rstLoc.getDouble("nd_canCotVenOrigen"); 
                            dblCanFacCotOrigen=rstLoc.getDouble("nd_canFacCotVenOrigen"); 
                            dblCanCanFacOrigen=rstLoc.getDouble("nd_canCanCotVenOrigen");
                            dblCanEgrBod=(dblCanCotOrigen-dblCanRem-dblCanFacCotOrigen)>=dblCanFac?dblCanFac:(dblCanCotOrigen-dblCanRem-dblCanFacCotOrigen);
                            dblCanEgrBod=(dblCanEgrBod)>=0?dblCanEgrBod:0;
                            dblCanRem=dblCanFac-dblCanEgrBod;
                            /* Las cantidades REMOTAS se ponen disponibles en la clase de ingrid, pero cuando son llamados por una facturacion parcial, se deben poner disponibles */
                            if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanRem,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm) ){
                                    arlDatStkInvItm.clear();
                                }else{blnRes=false;}
                            }
                        }
                        else{
                             dblCanEgrBod = dblCanFac - dblCanRem;
                        }
                       
                        System.out.println("ITEM: "+strCodAlt+"-"+"CANFAC: "+dblCanFac+"-CAN.REM.:"+dblCanRem+"-CAN.EGRE.BOD.:"+dblCanEgrBod );
                       
                        if(intCodBodGrp==15 || intCodBodGrp==3){   /* DOS AMBIENTES ===> INMACONSA - QUITO  */  
                            if(blnCanNecSerCon){
                                if(blnIsRes){
                                    strSql=" UPDATE tbm_detMovInv SET nd_canEgrBod="+((dblCanFac)*-1)+"  ";
                                    strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+"; ";
                                    System.out.println("prepararEgreso.DosAmbientes:RESERVA" + strSql);
                                }
                                else{
                                    strSql=" UPDATE tbm_detMovInv SET nd_canEgrBod="+((dblCanEgrBod)*-1)+"  ";
                                    strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+"; ";
                                    strSql+=" UPDATE tbm_detMovInv SET nd_canDesEntCli="+((dblCanRem)*-1)+"  ";
                                    strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+"; ";
                                    System.out.println("prepararEgreso..DOS AMBIENTES " + strSql);
                                }
                                stmLoc01.executeUpdate(strSql);
                            }
                            if(strItmSer.equals("S")){
                                strSql=" UPDATE tbm_detMovInv SET nd_canDesEntCli="+((dblCanFac)*-1)+" ";
                                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+"; ";
                                System.out.println("prepararEgreso..DOS AMBIENTES SERVICIO" + strSql);
                                stmLoc01.executeUpdate(strSql);
                            }

                            if(rstExt.getString("tx_momGenFac").equals("M")){  /* INMACONSA!!!   */
                                if(blnIsServicio==false){
                                    
                                        
                                    /* Misma bodega prestamos entre empresas en la misma bodega */
                                    if(blnIsRes){
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    else{
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanEgrBod,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES_VEN, "-", 0, arlDatStkInvItm)){  // SALE DE RESERVA
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }else{blnRes=false;}
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanRem,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanEgrBod,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                     
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanRem,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_DES_ENT_CLI, "-", 0, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                    
                                    /* JM 30/Nov/2017 El stock se baja al final para bajarlo tambien en tbm_inv con el total de la factura */
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;} 
                                    }else{blnRes=false;}
                                    
                                    
                                }
                               
                                
                                // Si es la misma bodega no debe sacar nada de reservas
                            }else if(rstExt.getString("tx_momGenFac").equals("F")){
                                if(blnIsServicio==false){ 
                                     
                                    
                                    if(blnIsRes){
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    else{
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanEgrBod,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES_VEN, "-", 0, arlDatStkInvItm)){  // SALE DE RESERVA
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }else{blnRes=false;}
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanRem,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    
                                
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanEgrBod,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}

                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanRem,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_DES_ENT_CLI, "-",0, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;} 
                                    
                                    /* JM 30/Nov/2017 El stock se baja al final para bajarlo tambien en tbm_inv con el total de la factura */
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){  
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                
                                }
                            }
                            else if(rstExt.getString("tx_momGenFac").equals("P")){
                                // LO QUE EGRESA DE BODEGA O DE DESPACHO
                                objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "ALERTA FACTURA AL INICIO ",rstExt.getString("co_seg"));
                                blnRes=false;
 
                            }
                            dblCanRem=0;  
                            dblCanEgrBod=0;
                             
                        }
                        else{  /*  BODEGAS QUE SOLO POSEEN UN AMBIENTE  -- QUITO - MANTA - SANTA DOMINGO --> CALIFORNIA - VIA DAULE--- */
                            if(blnCanNecSerCon){
                                strSql=" UPDATE tbm_detMovInv SET nd_canEgrBod="+(dblCanFac*-1)+" ";
                                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+" ; ";
                                System.out.println("prepararEgreso. 2 . (UN AMBIENTE) " + strSql);
                                stmLoc01.executeUpdate(strSql);
                            }
                            if(strItmSer.equals("S")){
                                strSql=" UPDATE tbm_detMovInv SET nd_canEgrBod="+(dblCanFac*-1)+" ";
                                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" AND co_reg="+rstLoc.getInt("co_reg")+" ; ";
                                System.out.println("prepararEgreso.. SERVICIO" + strSql);
                                stmLoc01.executeUpdate(strSql);
                            }
                            if(rstExt.getString("tx_momGenFac").equals("F")){ /* SOLO HAY RESERVA CON FACTURA AL FINAL  */
                                if(blnIsServicio==false){ 
                                    
                                    
                                    if(blnIsRes){
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    else{
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanEgrBod,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES_VEN, "-", 0, arlDatStkInvItm)){  // SALE DE RESERVA
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }else{blnRes=false;}
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanRem,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                    
                                    /* JM 30/Nov/2017 El stock se baja al final para bajarlo tambien en tbm_inv con el total de la factura */
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){  
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;} 
                                    
                                }
                            }
                            else if(rstExt.getString("tx_momGenFac").equals("P")){
                                blnRes=false;
                                objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "ALERTA FACTURA AL INICIO ",rstExt.getString("co_seg"));
 
                            }
                            else if(rstExt.getString("tx_momGenFac").equals("M")){  // ENTRO SIN SER INMACONSA PRESTAMOS ENTRE OTRAS EMPRESAS 
                                 if(blnIsServicio==false){
                                    
                                        
                                    /* Misma bodega prestamos entre empresas en la misma bodega */
                                    if(blnIsRes){
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    else{
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanEgrBod,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_RES_VEN, "-", 0, arlDatStkInvItm)){  // SALE DE RESERVA
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }else{blnRes=false;}
                                        if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanRem,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                            if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm) ){
                                                arlDatStkInvItm.clear();
                                            }else{blnRes=false;}
                                        }
                                    }
                                    
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_CAN_EGR_BOD, "-", 0, arlDatStkInvItm)){
                                            arlDatStkInvItm.clear();
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                    
                                    /* JM 30/Nov/2017 El stock se baja al final para bajarlo tambien en tbm_inv con el total de la factura */
                                    if(generaNuevoContenedorItemsMovimientoStock(rstLoc.getInt("co_emp"),rstLoc.getInt("co_itm"),dblCanFac,bodegaPredeterminada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc")))){
                                        if(objStkInv.actualizaInventario(conn, rstExt.getInt("co_emp"),INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
                                            System.out.println("ok stock"); 
                                        }else{blnRes=false;} 
                                    }else{blnRes=false;}
                                    
                                }
                            }
                            dblCanRem=0;  
                        }
                    }
                    stmLoc.close();
                    stmLoc01.close();
                    rstLoc.close();
                    stmLoc=null;
                    stmLoc01=null;
                    rstLoc=null;
                }
            }
            catch (SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(jifCnt, Evt);
            }
            return blnRes;
        }
           
        
     
    
    
    private ArrayList arlRegIng;
    
    /**
     * Segun la solicitud 
     * obtiene todos los ingresos de cada item, se lo hizo para la cotizacion
     * 
     * @param con
     * @param CodEmp Datos de la solicitud de transferencia
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    public ArrayList<String> obtenerCantidadRemota(java.sql.Connection con, int CodEmp, int CodLoc,int CodTipDoc, int CodDoc){
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            if(con!=null){
                arlDatIng = new ArrayList();
                stmLoc=con.createStatement();
                strSql="";/* LOS INGRESOS AGRUPADOS POR ITEM */
                strSql+=" SELECT a2.co_emp , a2.co_loc, a2.co_tipDoc, a2.co_doc,a8.co_bodGrp as co_bodIng, a7.co_bodEgr,  \n";
                strSql+="        a3.co_reg,a6.co_reg as coRegRel,a3.co_itm, a3.nd_can \n";
                strSql+=" FROM tbm_cabMovInv as a2  /*EL INGRESO */  \n";
                strSql+=" INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND   \n";
                strSql+="                                    a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) \n";
                strSql+=" INNER JOIN tbr_detMovInv as a5 ON (a3.co_emp=a5.co_empRel AND a3.co_loc=a5.co_locRel AND a3.co_tipDoc=a5.co_tipDocRel \n";
                strSql+="                                    AND a3.co_doc=a5.co_docRel AND a3.co_reg=a5.co_regRel)  \n";
                strSql+=" INNER JOIN tbm_detMovInv as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_tipDoc=a6.co_tipDoc AND  \n";
                strSql+="                                    a5.co_doc=a6.co_doc AND a5.co_reg=a6.co_reg)/*LA FACTURA*/  \n";
                strSql+=" LEFT OUTER JOIN ( \n";
                strSql+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a4.co_bodGrp as co_bodEgr, a1.co_reg,  \n";
                strSql+="                  a3.co_emp  as co_empIng, a3.co_loc as co_locIng, a3.co_tipDoc as co_tipDocIng,  \n";
                strSql+="                  a3.co_doc as co_docIng,a3.co_reg as co_regIng\n";
                strSql+="           FROM tbm_detMovInv AS a1 \n";
                strSql+="           INNER JOIN tbr_detMovInv AS a2 ON (a1.co_emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND \n";
                strSql+="                                               a1.co_doc=a2.co_doc AND a1.co_reg=a2.co_reg) \n";
                strSql+="           INNER JOIN (	 \n";
                strSql+="                       SELECT a3.co_emp , a3.co_loc, a3.co_tipDoc, a3.co_doc ,a3.co_reg   \n";
                strSql+="                       FROM tbm_detMovInv as a3   \n";
                strSql+="                       INNER JOIN tbr_detMovInv as a5 ON (a3.co_emp=a5.co_empRel AND a3.co_loc=a5.co_locRel AND  \n";
                strSql+="                                                          a3.co_tipDoc=a5.co_tipDocRel AND a3.co_doc=a5.co_docRel   \n";
                strSql+="                                                           AND a3.co_reg=a5.co_regRel) \n";
                strSql+="                       WHERE  a5.co_emp="+CodEmp+" AND a5.co_loc="+CodLoc+" AND a5.co_tipDoc="+CodTipDoc+" AND a5.co_doc="+CodDoc+"  \n";
                strSql+="           ) as a3 ON (a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND \n";
                strSql+="                        a2.co_docRel=a3.co_doc AND a2.co_regRel=a3.co_reg) \n";
                strSql+="           INNER JOIN tbr_bodEmpBodGrp AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod AND a4.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+") \n";
                strSql+="           WHERE NOT (a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+"  AND a1.co_tipDoc="+CodTipDoc+" AND a1.co_doc="+CodDoc+" ) \n";
                strSql+=" ) as a7 ON (a3.co_emp=a7.co_empIng AND a3.co_loc=a7.co_locIng AND a3.co_tipDoc=a7.co_tipDocIng AND \n";
                strSql+="             a3.co_doc=a7.co_docIng AND a3.co_reg=a7.co_regIng)  \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp AS a8 ON (a3.co_emp=a8.co_emp AND a3.co_bod=a8.co_bod AND a8.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" )\n";
                strSql+=" WHERE a6.co_emp="+CodEmp+" AND a6.co_loc="+CodLoc+" AND a6.co_tipDoc="+CodTipDoc+" AND a6.co_doc="+CodDoc+"  \n";
                strSql+="       AND a3.nd_can > 0 AND (a2.tx_tipMov='V' OR a2.tx_tipMov='I' OR a2.tx_tipMov='R') \n";
                strSql+=" GROUP BY a6.co_reg, a2.co_emp , a2.co_loc, a2.co_tipDoc,  a2.co_doc,a8.co_bodGrp,a7.co_bodEgr,a3.co_reg,a3.co_reg, a3.co_itm, a3.nd_can \n";
                strSql+=" ORDER BY a6.co_reg \n"; // IMPORTANTE ORDENADO POR LOS REGISTROS DE LA FACTURA                 
                System.out.println("obtenerCantidadRemota:... \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlRegIng=new ArrayList();
                    arlRegIng.add(INT_ARL_COD_EMP,rstLoc.getInt("co_emp")); // (DATOS DEL INGRESO)
                    arlRegIng.add(INT_ARL_COD_LOC,rstLoc.getInt("co_loc"));
                    arlRegIng.add(INT_ARL_COD_TIP_DOC,rstLoc.getInt("co_tipDoc"));
                    arlRegIng.add(INT_ARL_COD_DOC,rstLoc.getInt("co_doc"));
                    arlRegIng.add(INT_ARL_COD_REG,rstLoc.getInt("co_reg"));
                    arlRegIng.add(INT_ARL_COD_REG_REL,rstLoc.getInt("coRegRel")); // RELACIONAL (LA FACTURA)
                    arlRegIng.add(INT_ARL_COD_ITM,rstLoc.getInt("co_itm"));
                    arlRegIng.add(INT_ARL_CAN_ITM,rstLoc.getDouble("nd_can"));
                    arlRegIng.add(INT_ARL_BOD_ING,rstLoc.getInt("co_bodIng"));  /* JoséMario 12/Sep/2016 */
                    arlRegIng.add(INT_ARL_BOD_EGR,rstLoc.getInt("co_bodEgr"));  /* JoséMario 12/Sep/2016 */
                    arlRegIng.add(INT_ARL_CAN_UTI,0.00);
                    arlDatIng.add(arlRegIng);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
        return arlDatIng;
    }
        
        
        
    private int bodegaPredeterminada(int intCodEmp, int intCodLoc){
        int intBodPre=0;
        String strSql;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" SELECT a2.co_emp, a2.co_bod, a2.co_empGrp, a2.co_bodGrp ";
                strSql+=" FROM tbr_bodloc as a1 ";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) ";
                strSql+=" WHERE a1.co_loc="+intCodLoc+" and a1.st_reg='P' AND ";
                strSql+=" a1.co_emp="+intCodEmp+" ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intBodPre = rstLoc.getInt("co_bod");
                }
                strSql="";
                strSql="";
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            intBodPre=0;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            intBodPre=0;
        }
        return intBodPre;
    }
        
    private boolean obtenerSt_merIngEgrFisBodOC(java.sql.Connection conExt, int intCodSeg, int intCodEmp, int intCodItm){
        boolean blnRes=false;
        String strSql, strMerIngEgrFisBod="";
        java.sql.Statement stmLoc, stmLoc01; 
        java.sql.ResultSet rstLoc, rstLoc01;
        try{
             if(conExt!=null){
                stmLoc=conExt.createStatement();
                
                strSql =" SELECT co_seg,co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv  ";
                strSql+=" FROM tbm_cabSegMovInv ";
                strSql+=" WHERE co_seg="+intCodSeg+" and co_empRelCabMovInv IS NOT NULL";
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    stmLoc01 = conExt.createStatement();
                    strSql ="";
                    strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.co_itm, a2.st_merIngEgrFisBod, a2.nd_can ";
                    strSql+=" FROM tbm_cabMovInv as a1 ";
                    strSql+=" INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND";
                    strSql+="                                   a1.co_doc=a2.co_doc)";
                    strSql+=" WHERE a1.co_emp="+rstLoc.getInt("co_empRelCabMovInv")+" AND a1.co_loc="+rstLoc.getInt("co_locRelCabMovInv")+" AND ";
                    strSql+="       a1.co_tipDoc="+rstLoc.getInt("co_tipDocRelCabMovInv")+" AND a1.co_doc="+rstLoc.getInt("co_docRelCabMovInv")+" AND ";
                    strSql+="       a1.co_emp="+intCodEmp+" AND a2.co_itm="+intCodItm;
                    System.out.println("obtenerSt_merIngEgrFisBodOC " + strSql);
                    rstLoc01=stmLoc01.executeQuery(strSql);
                    if(rstLoc01.next()){
                        System.out.println("Encontro ");
                        strMerIngEgrFisBod=rstLoc01.getString("st_merIngEgrFisBod");
                        blnRes=true;
                    }
                    rstLoc01.close();rstLoc01=null;
                    stmLoc01.close();stmLoc01=null;
                }
                rstLoc.close();
                rstLoc=null;
                
                if(blnRes){
                    if(strMerIngEgrFisBod.equals("S")){
                        blnRes=true;
                    }
                    else{
                        blnRes=false;
                    }
                }
                
                stmLoc.close();
                stmLoc=null;
             }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(jifCnt, Evt);
            blnRes=false;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(jifCnt, Evt); 
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private ArrayList arlRegDocIng, arlDatDocIng, arlRegDocFac, arlDatDocFac;
    /*PARA LOS DOCUMENTOS GENERADOS*/
    private static final int INT_ARL_COD_EMP_DOC = 0;
    private static final int INT_ARL_COD_LOC_DOC = 1;
    private static final int INT_ARL_COD_TIP_DOC_DOC = 2;
    private static final int INT_ARL_COD_DOC_DOC = 3;
    private static final int INT_ARL_COD_REG_DOC = 4;
    private static final int INT_ARL_COD_ITM_DOC = 5;
    private static final int INT_ARL_CAN_DOC = 6;
    private static final int INT_ARL_CAN_PRO = 7;
    
    
    private boolean generarRelaciones(java.sql.Connection conExt,java.sql.ResultSet rstExt, int intCodSeg, int intCodEmp,int intCodLoc,int intCodTipDoc, int intCodDoc){
        boolean blnRes=false;
        String strCadena="", strIns="";
        java.sql.Statement stmLoc,stmLoc01; 
        java.sql.ResultSet rstLoc;
        int intNumDoc=0;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                stmLoc01 = conExt.createStatement();
                strSql = " SELECT MAX(co_reg)+1 as co_reg, a1.co_seg  \n";
                strSql+= " FROM tbm_cabSegMovInv as a1 \n";
                strSql+= " WHERE a1.co_seg = ( \n";
                strSql+= "     SELECT co_seg \n";
                strSql+= "     FROM tbm_cabSegMovInv \n";
                strSql+= "     WHERE co_empRelCabCotVen="+rstExt.getString("co_emp")+" AND  \n";
                strSql+= "     co_locRelCabCotVen="+rstExt.getString("co_loc")+" AND co_cotRelCabCotVen="+rstExt.getString("co_cot")+" GROUP BY co_seg \n  ";
                strSql+= " ) \n GROUP BY a1.co_seg";                        

                System.out.println("ObtenerNumeroSeguimiento: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    intNumDoc = rstLoc.getInt("co_reg");
                    intCodSeg = rstLoc.getInt("co_seg");
                }
                rstLoc.close();
                rstLoc=null;
                strSql=" ";
                strSql+=" INSERT INTO tbm_cabSegMovInv (co_seg,co_reg,co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv) ";
                strSql+=" VALUES ("+intCodSeg+", "+intNumDoc+", "+rstExt.getInt("co_emp")+", "+rstExt.getInt("co_loc")+","+intCodTipDoc+","+intCodDoc+" );";
                System.out.println("InsertarEnTablaSeguimiento: "+strSql);
                stmLoc01.executeUpdate(strSql);
                stmLoc01.close();
                stmLoc01=null;
                strCadena ="";
                strCadena+=" SELECT a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, ";
                strCadena+="        a1.co_docRelCabMovInv,a3.co_reg,  a3.co_itm, a3.nd_can ";
                strCadena+=" FROM tbm_cabSegMovInv as a1 ";
                strCadena+=" INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND ";
                strCadena+="                                    a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc) ";
                strCadena+=" INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND ";
                strCadena+="                                    a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) ";
                strCadena+=" RIGHT JOIN tbr_detMovInv as a5 ON (a2.co_emp=a5.co_empRel AND a2.co_loc=a5.co_locRel AND ";
                strCadena+="                                    a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel)  ";
                strCadena+=" WHERE a1.co_seg=(SELECT co_seg FROM tbm_cabSegMovInv WHERE co_empRelCabCotVen="+rstExt.getInt("co_emp");
                strCadena+="                    and co_locRelCabCotVen="+rstExt.getInt("co_loc")+" and co_cotRelCabCotVen="+rstExt.getInt("co_cot");
                strCadena+="                    GROUP BY co_seg ) and a3.nd_can > 0 ";
                strCadena+=" GROUP BY a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, ";
                strCadena+="           a1.co_docRelCabMovInv,a3.co_reg  , a3.co_itm, a3.nd_can  ";  
                strCadena+=" ";
                rstLoc=stmLoc.executeQuery(strCadena);
                arlDatDocIng=new ArrayList();
                while(rstLoc.next()){
                    arlRegDocIng=new ArrayList();
                    arlRegDocIng.add(INT_ARL_COD_EMP_DOC,rstLoc.getInt("co_empRelCabMovInv")); // (DATOS DEL INGRESO)
                    arlRegDocIng.add(INT_ARL_COD_LOC_DOC,rstLoc.getInt("co_locRelCabMovInv")); // (DATOS DEL INGRESO)
                    arlRegDocIng.add(INT_ARL_COD_TIP_DOC_DOC,rstLoc.getInt("co_tipDocRelCabMovInv")); // (DATOS DEL INGRESO)
                    arlRegDocIng.add(INT_ARL_COD_DOC_DOC,rstLoc.getInt("co_docRelCabMovInv")); // (DATOS DEL INGRESO)
                    arlRegDocIng.add(INT_ARL_COD_REG_DOC,rstLoc.getInt("co_reg")); // (DATOS DEL INGRESO)
                    arlRegDocIng.add(INT_ARL_COD_ITM_DOC,rstLoc.getInt("co_itm")); // (DATOS DEL INGRESO)
                    arlRegDocIng.add(INT_ARL_CAN_DOC,rstLoc.getInt("nd_can")); // (DATOS DEL INGRESO)
                    arlRegDocIng.add(INT_ARL_CAN_PRO,0); // (DATOS DEL INGRESO)
                    arlDatDocIng.add(arlRegDocIng);
                }
                rstLoc.close();
                rstLoc=null;
                strCadena ="";
                strCadena+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc,a3.co_reg, a3.co_itm, ABS(a3.nd_can) as nd_can  ";
                strCadena+=" FROM(  ";
                strCadena+="        SELECT co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv ";
                strCadena+="        FROM tbm_cabSegMovInv  ";
                strCadena+="        WHERE co_empRelCabMovInv="+intCodEmp+" AND co_locRelCabMovInv="+intCodLoc+" AND  ";
                strCadena+="                co_tipDocRelCabMovInv="+intCodTipDoc+" AND co_docRelCabMovInv="+intCodDoc+" ";
                strCadena+="  ) as a1  ";
                strCadena+="  INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND ";
                strCadena+="                                     a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) ";
                strCadena+="  INNER JOIN tbm_detMovInv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND ";
                strCadena+="                                     a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc) "; 
                strCadena+=" ORDER BY a3.co_reg ";
                rstLoc=stmLoc.executeQuery(strCadena);
                arlDatDocFac=new ArrayList();
                while(rstLoc.next()){
                    arlRegDocFac=new ArrayList();
                    arlRegDocFac.add(INT_ARL_COD_EMP_DOC,rstLoc.getInt("co_emp")); // (DATOS DEL EGRESO)
                    arlRegDocFac.add(INT_ARL_COD_LOC_DOC,rstLoc.getInt("co_loc")); // (DATOS DEL EGRESO)
                    arlRegDocFac.add(INT_ARL_COD_TIP_DOC_DOC,rstLoc.getInt("co_tipDoc")); // (DATOS DEL EGRESO)
                    arlRegDocFac.add(INT_ARL_COD_DOC_DOC,rstLoc.getInt("co_doc")); // (DATOS DEL EGRESO)
                    arlRegDocFac.add(INT_ARL_COD_REG_DOC,rstLoc.getInt("co_reg")); // (DATOS DEL EGRESO)
                    arlRegDocFac.add(INT_ARL_COD_ITM_DOC,rstLoc.getInt("co_itm")); // (DATOS DEL EGRESO)
                    arlRegDocFac.add(INT_ARL_CAN_DOC,rstLoc.getInt("nd_can")); // (DATOS DEL EGRESO)
                    arlRegDocFac.add(INT_ARL_CAN_PRO,0); // (DATOS DEL EGRESO)
                    arlDatDocFac.add(arlRegDocFac);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                int intCanIng,intCanIngPro;
                int intCanFac,intCanFacPro;
                // COMPARAMOS
                for(int ing=0;ing<arlDatDocIng.size();ing++){  // INGRESO 
                    intCanIng = objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_CAN_DOC); // CANTIDAD DEL INGRESO 
                    intCanIngPro = objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_CAN_PRO); // CANTIDAD DEL INGRESO PROCESADO 
                    
                    for(int fac=0;fac<arlDatDocFac.size();fac++){  // FACTURA
                        intCanFac = objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_CAN_DOC); // CANTIDAD DE LA FACTURA
                        intCanFacPro =  objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_CAN_PRO); // CANTIDAD DEL FACTURA PROCESADO 
                        if(intCanIng<intCanIngPro && intCanFac<intCanFacPro){
                            if(objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_ITM_DOC)==objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_ITM_DOC)){//PRIMERO ENCONTRAMOS EL REGISTRO DE LA RELACION
                                if(objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_CAN_DOC)>=objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_CAN_DOC)){//PRIMERO ENCONTRAMOS EL REGISTRO DE LA RELACION
                                    objUti.setIntValueAt(arlDatDocIng, ing, INT_ARL_CAN_PRO, intCanIng); // SE PROCESA EL INGRESO
                                    objUti.setIntValueAt(arlDatDocFac, fac, INT_ARL_CAN_PRO, intCanIng); // SE PROCESA EL FACTURA
                                    strIns+=" INSERT INTO tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,st_reg, \n";
                                    strIns+="                           co_emprel,co_locrel,co_tipdocrel,co_docrel,co_regrel) \n";
                                    strIns+=" ("+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_EMP_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_LOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_TIP_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_REG_DOC)+",'A',";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_EMP_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_LOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_TIP_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_REG_DOC)+");";
                                }
                                else{ // LA CANTIDAD DE LA FACTURA ES MENOR QUE EL INGRESO 
                                    objUti.setIntValueAt(arlDatDocIng, ing, INT_ARL_CAN_PRO, intCanFac); // SE PROCESA EL INGRESO
                                    objUti.setIntValueAt(arlDatDocFac, fac, INT_ARL_CAN_PRO, intCanFac); // SE PROCESA EL FACTURA
                                    strIns+=" INSERT INTO tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,st_reg, \n";
                                    strIns+="                           co_emprel,co_locrel,co_tipdocrel,co_docrel,co_regrel) \n";
                                    strIns+=" ("+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_EMP_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_LOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_TIP_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocFac, fac,INT_ARL_COD_REG_DOC)+",'A',";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_EMP_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_LOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_TIP_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_DOC_DOC)+",";
                                    strIns+=""+objUti.getIntValueAt(arlDatDocIng, ing,INT_ARL_COD_REG_DOC)+");";
                                }
                            }
                        }
                    }
                }
                
                 
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            blnRes=false;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            blnRes=false;
        }
        return blnRes;
    }
    
    
}
