/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafMovIngEgrInv;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;  
import Librerias.ZafStkInv.ZafStkInv;

/**
 *
 * @author José Mario Marín
 */
public class ZafReaMovInv {
    private ZafParSis objParSis;
    private String  strSql;
    private ZafUtil objUti;
   
    Date datFecSis;
    Calendar objFec;
    private ZafMovIngEgrInv objMovInv;
    private ZafStkInv objStkInv;
    
    /* CONSTANTES PARA CONTENEDOR QUE SE RECIBE DESDE EL ZafVen01_06 JoséMario 21/Sep/2015  */
   final int INT_CON_COD_EMP=0;
   final int INT_CON_COD_LOC=1;
   final int INT_CON_COD_TIP_DOC=2;
   final int INT_CON_COD_BOD_GRP=3; // BODEGA DE GRUPO DE DONDE SALE LA MERCADERIA  
   final int INT_CON_COD_ITM=4;
   final int INT_CON_COD_ITM_MAE=5;
   final int INT_CON_COD_BOD=6; // BODEGA DE EMPRESA DE DONDE SALE LA MERCADERIA
   final int INT_CON_NOM_BOD=7;
   final int INT_CON_CAN_COM=8;
   final int INT_CON_CHK_CLI_RET=9;
   final int INT_CON_EST_BOD=10;
   final int INT_CON_ING_EGR_FIS_BOD=11;
   final int INT_CON_COD_BOD_GRP_ING=12;  // BODEGA DE GRUPO A DONDE INGRESA LA MERCADERIA 
   
   
    /* 
     *    arlDatosItemEgreso/INGRESO        <HTML>Información del item:
     *                                      <BR>Código de empresa donde se guardará el documento
     *                                      <BR>Código de local donde se guardará el documento
     *                                      <BR>Código de tipo de documento donde se guardará el documento
     *                                      <BR>Cliente/Proveedor a quien se le genera el documento
     *                                      <BR>Código de bodega donde se ingresará/egresará la mercadería
     *                                      <BR>Código del item de grupo
     *                                      <BR>Código del item de empresa
     *                                      <BR>Código del item maestro
     *                                      <BR>Código del item alterno
     *                                      <BR>Nombre del item
     *                                      <BR>Unidad de medida del item
     *                                      <BR>Código del item en letras
     *                                      <BR>Cantidad del item en negativo
     *                                      <BR>Costo unitario del item
     *                                      <BR>Precio unitario del item
     *                                      <BR>Peso del item
     *                                      <BR>Estado del item para generar o no el IVA
   */
    /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
    final int INT_ARL_COD_EMP=0;            // CODIGO DE LA EMPRESA
    final int INT_ARL_COD_LOC=1;            // CODIGO DEL LOCAL
    final int INT_ARL_COD_TIP_DOC=2;        // CODIGO DEL TIPO DE DOCUMENTO
    final int INT_ARL_COD_CLI=3;            // CODIGO DEL CLIENTE/PROVEEDOR
    final int INT_ARL_COD_BOD_ING_EGR=4;    // CODGIO DE LA BODEGA DE INGRESO/EGRESO 
    final int INT_ARL_COD_ITM_GRP=5;        // CODIGO DE ITEM DE GRUPO
    final int INT_ARL_COD_ITM=6;            // CODIGO DEL ITEM POR EMPRESA
    final int INT_ARL_COD_ITM_MAE=7;        // CODIGO DEL ITEM MAESTRO
    final int INT_ARL_COD_ITM_ALT=8;        // CODIGO DEL ITEM ALTERNO 
    final int INT_ARL_NOM_ITM=9;            // NOMBRE DEL ITEM
    final int INT_ARL_UNI_MED_ITM=10;       // UNIDAD DE MEDIDA
    final int INT_ARL_COD_LET_ITM=11;       // CODIGO ALTERNO DEL ITEM 2 (CODIGO DE TRES LETRAS)
    final int INT_ARL_CAN_MOV=12;           // CANTIDAD 
    final int INT_ARL_COS_UNI=13;           // COSTO UNITARIO DEL ITEM
    final int INT_ARL_PRE_UNI=14;           // PRECIO UNITARIO DEL ITEM
    final int INT_ARL_PES_ITM=15;           // PESO DEL ITEM
    final int INT_ARL_IVA_COM_ITM=16;       // ESTADO DEL ITEM IVA
    final int INT_ARL_EST_MER_ING_EGR_FIS_BOD=17;// SI LA MERCADERIA EGRESA FISICAMENTE / SI SE CONFIRMA
    final int INT_ARL_IND_ORG=18;           // PARA ORGANIZAR AL FINAL LOS PRESTAMOS 
    //final int INT_ARL_IND_ORG=17;   // --- 
    
    
    
    private ArrayList arlConRegPreEgrInvTuv,arlConRegPreEgrInvCas,arlConRegPreEgrInvDim;
    private ArrayList arlConDatPreEgrInvTuv,arlConDatPreEgrInvCas,arlConDatPreEgrInvDim;
    private ArrayList arlConRegPreIngInvTuv,arlConRegPreIngInvCas,arlConRegPreIngInvDim;
    private ArrayList arlConDatPreIngInvTuv,arlConDatPreIngInvCas,arlConDatPreIngInvDim;
    
    private boolean blnPreInvTuv=false, blnPreInvCas=false, blnPreInvDim=false;
    
    ArrayList arlConDat = new ArrayList();
    ArrayList arlConReg = new ArrayList();
    private ArrayList arlConRegTraInv;
    private ArrayList arlConDatTraInv;
    private ArrayList arlConRegPreEmpEgr, arlConRegPreEmpIng;
    private ArrayList arlConDatPreEmpEgr, arlConDatPreEmpIng;
    
    
    private ArrayList arlConRegPreInvIngAux;
    private ArrayList arlConDatPreInvIngAux;
    private ArrayList arlConRegPreInvEgrAux;
    private ArrayList arlConDatPreInvEgrAux;
    
    private ArrayList arlRegStkInvItm, arlDatStkInvItm, arlConDatTraInvStkEgr,  arlConDatTraInvStkIng;
    
    private int intCodBodPre;
    private boolean blnTraInv=false, blnPreInv=false;
    private int  intCodTipDocGenTra, intCodTipDocGenVen, intCodTipDocGenCom, intCodCliGenVen,intCodCliGenCom, intCodItmGru;
    private int intCodEmpGenVen, intCodLocGenVen, intCodEmpGenCom, intCodLocGenCom;
    private int intCodEmpGenDevVen, intCodLocGenDevVen, intCodTipDocGenDevVen, intCodEmpGenDevCom, intCodLocGenDevCom, intCodTipDocGenDevCom;
    private int intCodItmGenVen,intCodBodGenVen;
    private double dblCosUni, dblPreUni, dblPesItm;
    private String strCodAltItm, strCodAltItm2, strEstIva, strNomItm,strUniMedItm;
    private String strIvaCom, strIvaVen;
    
    private String strMerIngEgrFisBod,strTipConIngEgr;  // NUEVO  
    
    
    
    
    
    private int intCodLocTra;
    private int intCodigoEmpresa,intCodigoBodegaEgresoTrans, intCodigoBodegaIngresoTrans;
    private Date dtpFechaDocumento;
    private char chrGeneraIva;
    
    private int intCodEmpMovStk, intCodTipDocMovStk;
    
    /* DEVOLUCIONES DE VENTA */
    private final boolean blnReaDevComVen=false; // PARA QUE SE SE GENERE DEVOLUCIONES DE VENTA
    private double Glo_dblCanFalDevVen=0.00,Glo_dblCanFalCom,Glo_dblCanFalDevCom=0.00;
    private boolean blnGenDevCom, blnGenDevVen;
    private String strFecDevVen="2015-01-01";
    private StringBuffer stbDatDevCom=new StringBuffer();
    private StringBuffer stbDatDevVen=new StringBuffer();
    private StringBuffer stbDevTemp, stbDevInvTemp, stbLisBodItmTemp, stbDocRelEmpTemp;
    private int intDatDevCom=0,intDatDevVen=0;
    private double bldivaEmp=0;
    private double Glo_dblTotDevCom=0.00;
    private double Glo_dblTotDevVen=0.00;
    private int intConStbBod=0;
    private StringBuffer stbLisBodItm;
    private StringBuffer stbDocRelEmp;
    private double dblGenDevCom=0.00, dblGenDevVen=0.00;
    private int intConLinArr=0;
            
    
    
    /**********************************************************************************************************************/
    private String strTipProReaEmpRel; // CONFIGURACION DE LA EMPRESA PARA SI SE HACE PRESTAMOS, COMPRA/VENTA, DEVOLUCIONES
    /**********************************************************************************************************************/
    
    /* NUEVO CONTENEDOR PARA ITEMS */
    
    private static final int INT_STK_INV_COD_ITM_GRP=0;
    private static final int INT_STK_INV_COD_ITM_EMP=1;
    private static final int INT_STK_INV_COD_ITM_MAE=2;    
    private static final int INT_STK_INV_COD_LET_ITM=3;     
    private static final int INT_STK_INV_CAN_ITM=4;
    private static final int INT_STK_INV_COD_BOD_EMP=5; 
    

    
    //Constantes: Para el contenedor SOLICITUDES DE TRANSFERENCIA JoséMario 26/Abril/2015.
    final int INT_ARL_SOL_TRA_COD_EMP=0;
    final int INT_ARL_SOL_TRA_COD_LOC=1;
    final int INT_ARL_SOL_TRA_COD_TIP_DOC=2;
    final int INT_ARL_SOL_TRA_COD_DOC=3;
    final int INT_ARL_SOL_TRA_COD_BOD_EGR=4;
    
    
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
    final int INT_ARL_STK_INV_CAN_RES=9;
    final int INT_ARL_STK_INV_CAN_DIS=10;  // nd_canDis
    final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 


         
    private ZafCorEle objCorEle;
    private Object objIsGenOD;
    private ArrayList arlDatSolTra;
    private ZafCfgBod objCfgBodGlo;
    public String strVer="v 0.08";       // 13/Sep/2018
    private  java.awt.Component Jcomponente;
    private boolean blnIsSolRes=false,blnIsSolTraInv=false;
     
     public ZafReaMovInv(ZafParSis obj, java.awt.Component componente ){
        try{
             objParSis = obj;
            objUti = new ZafUtil();
            objFec = Calendar.getInstance();
            objMovInv = new ZafMovIngEgrInv(objParSis,componente);
            Jcomponente = componente;
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            objCorEle = new ZafCorEle(objParSis);
            System.out.println(strVer);
            blnIsSolRes=false;
            blnIsSolTraInv=false;
            /*PARA EL TIPO DE CONFIRMACIONES 19/Mayo/2016*/
            if(objParSis.getCodigoMenu()==3965){//3965;Cotizaciones de venta (Facturación electrónica)...;Ventas.ZafVen01.ZafVen01
                strTipConIngEgr="V";  // SI ES POR VENTA LA COMPRA VENTA NO GENERA INGRESO EN ND_CANDIS
            }else if(objParSis.getCodigoMenu()==3993){//3993;Autorización de solicitudes de transferencias de inventario...;Compras.ZafCom92.ZafCom92
                blnIsSolTraInv=true;
                strTipConIngEgr="R";  // SI ES POR SOLICITUD SOLO LA TRANSFERENCIA SI DEBE GENERA UN INGRESO EN ND_CANDIS EN LA PARTE DE COMPRA/VENTA
            }else if(objParSis.getCodigoMenu()==2336){ // REPOSICIONES DE INVENTARIO
                blnIsSolTraInv=true;
                strTipConIngEgr="R";  // SI ES POR SOLICITUD SOLO LA TRANSFERENCIA SI DEBE GENERA UN INGRESO EN ND_CANDIS EN LA PARTE DE COMPRA/VENTA
            }
            else if(objParSis.getCodigoMenu()==4146){ // RESERVAS
                blnIsSolRes=true;
                strTipConIngEgr="V";  // SI ES POR SOLICITUD SOLO LA TRANSFERENCIA SI DEBE GENERA UN INGRESO EN ND_CANDIS EN LA PARTE DE COMPRA/VENTA
            }else{
                System.err.println("NO ENTRO NI POR COTIZACIONES NI POR AUTORIZAR SOLICITUDE DE TRANSFERENCIA!!!!");
                objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "ZafReaMovInv", "ENTRO POR OTRO LADO!!! ");
            }
        }
        catch(Exception Evt) { 
            objUti.mostrarMsgErr_F1(Jcomponente, Evt);  
            System.err.println("ERROR "  + Evt.toString());
        }        
    }
    
     
    public boolean inicia(java.sql.Connection con,ArrayList arlDat,java.util.Date datFecAux,ArrayList arlSolTra, ZafCfgBod objCfgBod){
        boolean blnRes=false;
        int intCodEmp=objParSis.getCodigoEmpresa();
        try{
            limpiaBooleanos();
            arlDatSolTra=arlSolTra;
            dtpFechaDocumento = datFecAux;
            if(obtenerConfiguracionDelSistema()){//PARA SABER SI SE HACEN PRESTAMOS O FACTURAS ENtre empreSAS
                if(obtenerDatos(con,arlDat)){
                    if(!arlDat.isEmpty()){
                        if(enviarMovimiento(con,objCfgBod)){
                            objParSis.setCodigoEmpresa(intCodEmp);
                            blnRes=true;
                        }
                    }
                    else{
                        System.out.println("No necesita prestamos...");
                         blnRes=true;
                    }
                }
            }
        }
        catch(Exception Evt) { 
            objUti.mostrarMsgErr_F1(Jcomponente, Evt);  
            System.err.println("ERROR "  + Evt.toString());
            blnRes=false;
        }        
        return blnRes;        
    }
    
    /**
     * 
     * @param con
     * @param arlDat
     * @param datFecAux
     * @param arlSolTra
     * @param objCfgBod
     * @return 
     */
    
    public boolean iniciaTransferencia(java.sql.Connection con,ArrayList arlDat,java.util.Date datFecAux,ArrayList arlSolTra, ZafCfgBod objCfgBod){
        boolean blnRes=false;
        try{
            System.out.println("iniciaTransferencia " + objCfgBod.toString());
            limpiaBooleanos();
            arlDatSolTra=arlSolTra;
            dtpFechaDocumento = datFecAux;
            if(obtenerConfiguracionDelSistema()){
                if(obtenerDatosTranferencia(con,arlDat)){
                    if(enviarMovimiento(con,objCfgBod)){
                        blnRes=true;
                    }
                }
            }
            
        }
        catch(Exception Evt) { 
            objUti.mostrarMsgErr_F1(Jcomponente, Evt);  
            System.err.println("ERROR "  + Evt.toString());
            blnRes=false;
        }        
        return blnRes;        
    }
    
    /**
     * Se utiliza para obtener datos en el caso de que la bodega destino tenga dueño.
     * @param con
     * @param arlDat
     * @return 
     */
    private boolean obtenerDatos(java.sql.Connection con,ArrayList arlDat){
        boolean blnRes=true;
        java.sql.Statement stmLoc, stmLoc01;
        String strErr;
        java.sql.ResultSet rstLoc, rstLoc01;
        Double dblCanTra, dblCanNec=0.0, dblCanVenEmp=0.0, dlbStkBobDevCom;
        try{
            System.out.println("================  OBTENER DATOS  ===============  ");
            if(con!=null){
                arlConDatPreEmpEgr = new ArrayList();
                arlConDatPreEmpIng = new ArrayList();
                arlConDatTraInv = new ArrayList();
                stmLoc=con.createStatement();
                arlConDat=arlDat;
                for(int i=0;i<arlConDat.size();i++){
                    objParSis.setCodigoEmpresa(objUti.getIntValueAt(arlConDat, i, INT_CON_COD_EMP));
                    intCodBodPre=bodegaPorEmpresaIngreso(objUti.getIntValueAt(arlConDat, i, INT_CON_COD_BOD_GRP_ING));
                    
                    if(isMerNecConf( objUti.getIntValueAt(arlConDat, i, INT_CON_COD_BOD_GRP_ING), objUti.getIntValueAt(arlConDat, i, INT_CON_COD_BOD_GRP))){
                        strMerIngEgrFisBod="S";
                        objIsGenOD="S";
                    }
                    else{
                        strMerIngEgrFisBod="N";
                        objIsGenOD=null;
                    }
                    
                    dblCanNec=Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM));  // CANTIDAD QUE BUSCAMOS                    
// =================================================  DEVOLUCIONES  =================================================================== //   
                    
                    if (strTipProReaEmpRel.equals("D")){
                        // <editor-fold defaultstate="collapsed" desc=" /* José Marín:  Proceso de devoluciones NO FUNCIONA */ ">
                        Glo_dblCanFalCom=dblCanNec;//Cantidad solicitada
                        Glo_dblCanFalDevCom=Glo_dblCanFalCom;//Cantidad solicitada
                        Glo_dblCanFalDevVen=Glo_dblCanFalCom;//Cantidad solicitada
                        stbDatDevCom=new StringBuffer();
                        stbDatDevVen=new StringBuffer();

                        strSql="SELECT DISTINCT(co_empper) as empper FROM tbr_bodemp WHERE co_emp="+objParSis.getCodigoEmpresa()+" and co_empper not in ( "+objParSis.getCodigoEmpresa()+" ) ";
                        rstLoc=stmLoc.executeQuery(strSql); 
                        while(rstLoc.next()){
                            if(dblCanNec > 0 ){ // SI LA CANTIDAD SOLICITADA ESTOY SOLICITANDO ALGO
                                stmLoc01=con.createStatement();
                                strSql="SELECT a.co_emp, a3.co_empper, a.co_itm, a.co_itmrel, a3.co_loc, a3.co_locdevcom, a3.co_tipdocdevcom, a3.co_locdevven, a3.co_tipdocdevven, a2.nd_canDis, a.nd_salven "+
                                " ,a2.co_bod, a5.co_itmmae FROM tbm_invmovempgrp as a "+
                                " inner join tbm_inv as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) "+
                                " inner join tbr_bodemp as a3 on (a3.co_emp=a.co_emp and a3.co_loc="+objParSis.getCodigoLocal()+"  and a3.co_empper=a.co_emprel  and a3.st_reg='A') "+
                                " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm and a2.co_bod=a3.co_bodper) "+
                                " inner join tbm_equinv as a5 on (a5.co_emp=a2.co_emp and a5.co_itm=a2.co_itm )  " +
                                " inner join tbr_bodEmpBodGrp as a4 on (a4.co_emp=a2.co_emp and a4.co_bod=a2.co_bod) "+
                                " where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_itm="+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM)+"  AND a.nd_salven < 0   AND  a2.nd_canDis > 0 "+
                                " AND ( a4.co_empGrp=0 AND a4.co_bodGrp= "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD_GRP)+"  ) "+
                                " AND  a3.co_empper="+rstLoc.getInt("empper")+" ";
//                                System.out.println("_generaDevoluciones:  "+strSql );
                                rstLoc01=stmLoc01.executeQuery(strSql);
                                while(rstLoc01.next()){                         
                                    dlbStkBobDevCom= rstLoc01.getDouble("nd_canDis");    
                                    /* A.P CONTABILIDAD/SISTEMAS NO GENERAR DEVOLUCIONES DE COMPRAS/VENTAS */  /* OFF */
                                    if (blnReaDevComVen){
                                        MovDevCompra(con, rstLoc01.getInt("co_emp"),  rstLoc01.getInt("co_empper"), rstLoc01.getInt("co_locdevcom"), 
                                            rstLoc01.getInt("co_tipdocdevcom"), rstLoc01.getInt("co_itmmae"), Glo_dblCanFalDevCom,  rstLoc01.getInt("co_bod"), 
                                            dlbStkBobDevCom, rstLoc01.getInt("co_tipdocdevven"), objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) );
                                        MovDevVenta(con, rstLoc01.getInt("co_emp"),  rstLoc01.getInt("co_locdevven"), rstLoc01.getInt("co_tipdocdevven"),  
                                            rstLoc01.getInt("co_itmmae"), rstLoc01.getInt("co_empper"),   Glo_dblCanFalDevCom, dlbStkBobDevCom , 
                                            rstLoc01.getInt("co_bod")   );                           
                                    }
                                    if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){                               
                                        if(_RealizarDevoluciones(con, objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) , rstLoc01.getInt("co_locdevcom"), 
                                                rstLoc01.getInt("co_locdevven"), rstLoc01.getInt("co_tipdocdevcom"), rstLoc01.getInt("co_tipdocdevven"),
                                                rstLoc01.getInt("co_bod"), dlbStkBobDevCom, objUti.getStringValueAt(arlConDat, i, INT_CON_ING_EGR_FIS_BOD) , 
                                            objUti.getStringValueAt(arlConDat, i, INT_CON_NOM_BOD), false /*cliente retira */ , rstLoc01.getInt("co_itmMae")) ){
                                           blnRes=true;
                                        }
                                        else{
                                            break;
                                        }
                                       if( obtenerTipoDocumentos(rstLoc,2)){
                                            if(obtenerDatosCompraVenta(rstLoc,Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE))) ){
                                                if(obtenerDatosItem(0,Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)),0,0)){

                                                    /**
                                                     *  1;4;228;10100;4;1;5;205;2261;1;4;2;206;2053;23
                                                     *  1;4;228;10140;2;1;5;205;2265;1;4;2;206;2057;9
                                                     *  SE ENVIARA EL EGRESO intCodTipDocGenVen (206,228 FACVENE) Y EL INGRESO intCodTipDocGenCom (205,2 FACCOM)
                                                     *  EGRESO GENERA VENTA
                                                     */
                                                    //  EGRESO
                                                    intCodBodGenVen=rstLoc.getInt("co_bod"); 
                                                    arlConRegPreEmpEgr = new ArrayList();
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_EMP,intCodEmpGenDevVen);//EGRESO 206 DESDE DONDE SALE LA MERCADERIA
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_LOC,intCodLocGenDevVen );  
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenVen);  // EL EGRESO intCodTipDocGenVen (206,228 FACVENE)
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_CLI, intCodCliGenVen); // EMPRESA RELACIONADA 
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_BOD_ING_EGR,intCodBodGenVen );  // BODEGA DE DONDE SE SACA LA MERCADERIA
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_GRP, intCodItmGru); // CODIGO DEL ITEM DE GRUPO
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_ITM,intCodItmGenVen ); //ITEM CAMBIAR A OTRA EMPRESA
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE) );
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                                    arlConRegPreEmpEgr.add(INT_ARL_NOM_ITM,strNomItm );
                                                    arlConRegPreEmpEgr.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                                    arlConRegPreEmpEgr.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                                    arlConRegPreEmpEgr.add(INT_ARL_CAN_MOV, (dblGenDevCom));  // CANTIDAD POR TRANSFERENCIA
                                                    arlConRegPreEmpEgr.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                                    arlConRegPreEmpEgr.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO
                                                    arlConRegPreEmpEgr.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                                    arlConRegPreEmpEgr.add(INT_ARL_IVA_COM_ITM, strIvaVen);  // PESO DEL ITEM
                                                    arlConRegPreEmpEgr.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                                                    arlConRegPreEmpEgr.add(INT_ARL_IND_ORG, null);
                                                    arlConDatPreEmpEgr.add(arlConRegPreEmpEgr);                                    
                                                    // ----------------
                                                    //  INGRESO INGRESO GENERA COMPRA
                                                    arlConRegPreEmpIng = new ArrayList();
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_EMP,intCodEmpGenCom);
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_LOC,intCodLocGenCom);        
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenCom);  // EL INGRESO intCodTipDocGenCom (205,2 FACCOM)
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_CLI,intCodCliGenCom ); // EMPRESA RELACIONADA QUE ESTA GENERANDO EL MOVIMIENTO
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_BOD_ING_EGR,intCodBodPre );  // BODEGA DE DONDE SE SACA LA MERCADERIA    
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_ITM_GRP, intCodItmGru); // CODIGO DEL ITEM DE GRUPO
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_ITM,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) );
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE) );
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                                    arlConRegPreEmpIng.add(INT_ARL_NOM_ITM,strNomItm );
                                                    arlConRegPreEmpIng.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                                    arlConRegPreEmpIng.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                                    arlConRegPreEmpIng.add(INT_ARL_CAN_MOV, (dblGenDevVen));  // CANTIDAD POR TRANSFERENCIA
                                                    arlConRegPreEmpIng.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                                    arlConRegPreEmpIng.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO
                                                    arlConRegPreEmpIng.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                                    arlConRegPreEmpIng.add(INT_ARL_IVA_COM_ITM, strIvaCom);
                                                    arlConRegPreEmpIng.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                                                    arlConRegPreEmpIng.add(INT_ARL_IND_ORG, null);
                                                    arlConDatPreEmpIng.add(arlConRegPreEmpIng);
                                                    chrGeneraIva='S';
                                                    blnPreInv=true;
                                                } 
                                                else{
                                                    strErr="Problemas con el Item:"+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM);
                                                    System.out.println(strErr);
                                                    blnRes=false;
                                                }
                                            }
                                            else{
                                                blnRes=false;
                                            }
                                        }
                                       else{
                                           System.err.println("TiposDocumentoDevolucion");
                                           blnRes=false;
                                       }
                                    }
                                }
                                rstLoc01.close();
                                rstLoc01=null;
                                stmLoc01.close();
                                stmLoc01=null;
                            }
                        }
                        rstLoc.close();
                        rstLoc=null;
                        dblCanNec=Glo_dblCanFalDevCom;
                        
                        //</editor-fold>
                    }
  //////   =============================  TRANSFERENCIAS =============================   ////////////////////
                    if(dblCanNec>0){
                        strSql= " SELECT *   \n";
                        strSql+=" FROM ( \n";
                        strSql+="     SELECT a.co_emp , a.co_bod, a.co_itm  \n";
                        strSql+="            , CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END)  \n";
                        strSql+="               ELSE CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END END AS nd_canDis ";
                        strSql+="     FROM tbm_invbod as a \n" ;
                        strSql+="     INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod)  \n";
                        strSql+="     INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm )   \n";
                        strSql+="     LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni ) \n";
                        strSql+="     WHERE a.co_emp="+objUti.getIntValueAt(arlConDat, i, INT_CON_COD_EMP)+" AND a.co_itm= "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM);
                        strSql+="           AND a1.co_bod != "+intCodBodPre+" ";
                        strSql+="           AND ( a1.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.co_bodGrp= "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD_GRP)+"   ) \n";
                        strSql+=" ) AS x \n  ";
                        strSql+=" WHERE nd_canDis > 0 \n";
                        strSql+=" ORDER BY nd_canDis DESC \n";
                        System.out.println("TRANSFERENCIA \n" + strSql);
                        rstLoc=stmLoc.executeQuery(strSql); 
                        while(rstLoc.next()){ 
                            if(obtenerDatosItem(objUti.getIntValueAt(arlConDat, i, INT_CON_COD_EMP),Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)),
                                        Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD)),intCodBodPre)){
                                if( rstLoc.getDouble("nd_canDis") >= Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM).toString() ) ){
                                    dblCanTra=Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM).toString());
                                    dblCanNec=0.00;  // YA NO SE NECESITA NADA MAS
                                }else{ // SI EN EL STOCK HAY MENOS SE IRA POR TRANSFERENCIA TODO Y LA DIFERENCIA POR PRESTAMO
                                   dblCanTra=rstLoc.getDouble("nd_canDis");// TODO EN TRANSFERENCIA
                                   dblCanNec=Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM).toString()) - dblCanTra; //PARA SABER CUANTO QUEDARIA PENDIENTE DESPUES DE LA TRANSFERENCIA
                                }
                                if(obtenerTipoDocumentos(rstLoc,1)){
                                        /* PRIMERO EL EGRESO SOLICITO INGRIK */
                                        arlConRegTraInv = new ArrayList();
                                        intCodigoBodegaEgresoTrans=Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD));  // BODEGA DE EGRESO
                                        intCodigoBodegaIngresoTrans=intCodBodPre; // BODEGA DE INGRESO
                                        
                                        arlConRegTraInv.add(INT_ARL_COD_EMP,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );
                                        intCodigoEmpresa=Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );  // CODIGO DE EMPRESA 
                                        arlConRegTraInv.add(INT_ARL_COD_LOC,intCodLocTra );     // CODIGO DEL LOCAL  
                                        arlConRegTraInv.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenTra); // CODIGO DE TIPO DE DOCUMENTO
                                        arlConRegTraInv.add(INT_ARL_COD_CLI, null); // DEBERIA SER NULL
                                        arlConRegTraInv.add(INT_ARL_COD_BOD_ING_EGR, intCodigoBodegaEgresoTrans);  // BODEGA EN DONDE SE EGRESA LA MERCADERIA
                                        //chrGeneraIva=strEstIva.charAt(0);
//                                        chrGeneraIva='N';   /* TRANSFERENCIA NO GENERA IVA */
                                        arlConRegTraInv.add(INT_ARL_COD_ITM_GRP, intCodItmGru);  // CODIGO DEL ITEM DE GRUPO
                                        arlConRegTraInv.add(INT_ARL_COD_ITM,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) );
                                        arlConRegTraInv.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)  );
                                        arlConRegTraInv.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                        arlConRegTraInv.add(INT_ARL_NOM_ITM,strNomItm );
                                        arlConRegTraInv.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                        arlConRegTraInv.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                        arlConRegTraInv.add(INT_ARL_CAN_MOV, (-1*dblCanTra) );  // CANTIDAD POR TRANSFERENCIA EGRESA
                                        arlConRegTraInv.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                        arlConRegTraInv.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO                           
                                        arlConRegTraInv.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                        arlConRegTraInv.add(INT_ARL_IVA_COM_ITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                                        
                                        arlConRegTraInv.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                                        
                                        arlConRegTraInv.add(INT_ARL_IND_ORG,Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD))); 
                                        arlConDatTraInv.add(arlConRegTraInv);

                                        // ==============  INGRESO!!!! 19/MaYO/2016 (ASI DEBE SER PIDIO INGRIK )
                                        arlConRegTraInv = new ArrayList();
                                        arlConRegTraInv.add(INT_ARL_COD_EMP,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );
                                        intCodigoEmpresa=Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );  // CODIGO DE EMPRESA 
                                        arlConRegTraInv.add(INT_ARL_COD_LOC,intCodLocTra );     // CODIGO DEL LOCAL  
                                        arlConRegTraInv.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenTra); // CODIGO DE TIPO DE DOCUMENTO
                                        arlConRegTraInv.add(INT_ARL_COD_CLI, null); // DEBERIA SER NULL
                                        arlConRegTraInv.add(INT_ARL_COD_BOD_ING_EGR, intCodigoBodegaIngresoTrans);  // BODEGA EN DONDE SE INGRESA LA MERCADERIA
                                        arlConRegTraInv.add(INT_ARL_COD_ITM_GRP, intCodItmGru);  // CODIGO DEL ITEM DE GRUPO
                                        arlConRegTraInv.add(INT_ARL_COD_ITM,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) );
                                        arlConRegTraInv.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)  );
                                        arlConRegTraInv.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                        arlConRegTraInv.add(INT_ARL_NOM_ITM,strNomItm );
                                        arlConRegTraInv.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                        arlConRegTraInv.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                        arlConRegTraInv.add(INT_ARL_CAN_MOV, (dblCanTra) );  // CANTIDAD POR TRANSFERENCIA EGRESO
                                        arlConRegTraInv.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                        arlConRegTraInv.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO                           
                                        arlConRegTraInv.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                        arlConRegTraInv.add(INT_ARL_IVA_COM_ITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                                        arlConRegTraInv.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                                        arlConRegTraInv.add(INT_ARL_IND_ORG,Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD))); 
                                        arlConDatTraInv.add(arlConRegTraInv);
                                        blnTraInv=true;
                                        
                                        System.out.println("Transferencia: " + arlConDatTraInv.toString());
                                } 
                                else{
                                    blnRes=false;
                                }
                            }
                            else{
                                strErr="Problemas con el Item:"+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM);
                                System.out.println(strErr);
                                blnRes=false;
                            }
                        }
                    }                    
 //=========================================================== COMPRA / VENTA ====================================================================
                    if(dblCanNec>0){ /* DESPUES DE LA TRANSFERENCIA ALGO SIGUE PENDIENTE ENTRA EN PRESTAMOS O VENTAS ENTRE EMPRESA */
                        strSql= " SELECT * \n";
                        strSql+=" FROM ( \n";
                        strSql+="       SELECT a.co_emp , a.co_bod, a.co_itm \n";
                        strSql+="             ,CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END)  \n";
                        strSql+="               ELSE CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END END AS nd_canDis  \n";
                        strSql+="       FROM tbm_invbod as a \n";
                        strSql+="       INNER JOIN tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm ) \n" ;
                        strSql+="       INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) \n";
                        strSql+="       INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm )    \n";
                        strSql+="       LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni ) \n";
                        strSql+="       WHERE ( a1.co_empGrp=0 AND a1.co_bodGrp= "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD_GRP) +"  ) \n";
                        strSql+="             AND a2.co_itmmae= "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE) +"  \n";
                        strSql+="             AND a.co_emp != "+objParSis.getCodigoEmpresa()+" \n";
                        strSql+=" ) AS x \n  ";
                        strSql+=" WHERE nd_canDis > 0 \n ";
                        strSql+=" ORDER BY nd_canDis DESC"; // LA PRIORIDAD EN LA COMPRA/VENTA ES TOMAR DE DONDE HAY MAS 
                        System.out.println("COMPRA/VENTA \n " + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);
                        while(rstLoc.next()){
                            if( rstLoc.getDouble("nd_canDis") >= dblCanNec ){ // SI HAY MAS EN STOCK 
                                dblCanVenEmp=dblCanNec;
                                dblCanNec=0.0;
                            }
                            else{
                                dblCanVenEmp = rstLoc.getDouble("nd_canDis");  
                               dblCanNec = dblCanNec - rstLoc.getDouble("nd_canDis");  // Sigue faltando algo                               
                               dblCanNec=dblCanNec<0?dblCanNec*-1:dblCanNec; // SI ES NEGATIVA LA CANTIDAD 
                            }
                            if(dblCanVenEmp>0){  // si todavia hay algo pendiente
                                if(obtenerTipoDocumentos(rstLoc,2)){
                                    if(obtenerDatosCompraVenta(rstLoc,Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE))) ){
                                        if(obtenerDatosItem(intCodEmpGenVen, Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)),
                                                           rstLoc.getInt("co_bod")/*EGRESO*/,intCodBodPre/*Ingreso*/  ) ){
                                            intConLinArr++;
                                            /**
                                             *  1;4;228;10100;4;1;5;205;2261;1;4;2;206;2053;23
                                             *  1;4;228;10140;2;1;5;205;2265;1;4;2;206;2057;9
                                             *  SE ENVIARA EL EGRESO intCodTipDocGenVen (206,228 FACVENE) Y EL INGRESO intCodTipDocGenCom (205,2 FACCOM)
                                             *  EGRESO GENERA VENTA
                                             */
                                            //  EGRESO
                                            intCodBodGenVen=rstLoc.getInt("co_bod"); 
                                            arlConRegPreEmpEgr = new ArrayList();
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_EMP,intCodEmpGenVen);//EGRESO 206 DESDE DONDE SALE LA MERCADERIA
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_LOC,intCodLocGenVen );  
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenVen);  // EL EGRESO intCodTipDocGenVen (206,228 FACVENE)
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_CLI, intCodCliGenVen); // EMPRESA RELACIONADA 
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_BOD_ING_EGR,intCodBodGenVen );  // BODEGA DE DONDE SE SACA LA MERCADERIA
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_GRP, intCodItmGru); // CODIGO DEL ITEM DE GRUPO
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM,intCodItmGenVen ); //ITEM CAMBIAR A OTRA EMPRESA
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE) );
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                            arlConRegPreEmpEgr.add(INT_ARL_NOM_ITM,strNomItm );
                                            arlConRegPreEmpEgr.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                            arlConRegPreEmpEgr.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                            arlConRegPreEmpEgr.add(INT_ARL_CAN_MOV, (dblCanVenEmp*-1));  // CANTIDAD POR TRANSFERENCIA
                                            arlConRegPreEmpEgr.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                            arlConRegPreEmpEgr.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO
                                            arlConRegPreEmpEgr.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                            if(strTipProReaEmpRel.equals("P")){
                                                strIvaVen="N";
                                                arlConRegPreEmpEgr.add(INT_ARL_IVA_COM_ITM, strIvaVen);  //PRESTAMOS NO GENERA IVA
                                            }else{
                                                strIvaVen="S";
                                                arlConRegPreEmpEgr.add(INT_ARL_IVA_COM_ITM, strIvaVen);  
                                            }
                                            arlConRegPreEmpEgr.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                                            arlConRegPreEmpEgr.add(INT_ARL_IND_ORG, intConLinArr);  // PARA REORGANIZAR
                                            arlConDatPreEmpEgr.add(arlConRegPreEmpEgr);                                    
                                            // ----------------
                                            //  INGRESO INGRESO GENERA COMPRA
                                            arlConRegPreEmpIng = new ArrayList();
                                            arlConRegPreEmpIng.add(INT_ARL_COD_EMP,intCodEmpGenCom);
                                            arlConRegPreEmpIng.add(INT_ARL_COD_LOC,intCodLocGenCom);        
                                            arlConRegPreEmpIng.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenCom);  // EL INGRESO intCodTipDocGenCom (205,2 FACCOM)
                                            arlConRegPreEmpIng.add(INT_ARL_COD_CLI,intCodCliGenCom ); // EMPRESA RELACIONADA QUE ESTA GENERANDO EL MOVIMIENTO
                                            arlConRegPreEmpIng.add(INT_ARL_COD_BOD_ING_EGR,intCodBodPre );  // BODEGA DE DONDE SE SACA LA MERCADERIA    
                                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM_GRP, intCodItmGru); // CODIGO DEL ITEM DE GRUPO
                                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) );
                                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE) );
                                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                            arlConRegPreEmpIng.add(INT_ARL_NOM_ITM,strNomItm );
                                            arlConRegPreEmpIng.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                            arlConRegPreEmpIng.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                            arlConRegPreEmpIng.add(INT_ARL_CAN_MOV, (dblCanVenEmp));  // CANTIDAD POR TRANSFERENCIA
                                            arlConRegPreEmpIng.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                            arlConRegPreEmpIng.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO
                                            arlConRegPreEmpIng.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                            if(strTipProReaEmpRel.equals("P")){
                                                strIvaCom="N";
                                                arlConRegPreEmpIng.add(INT_ARL_IVA_COM_ITM, strIvaCom);  // IVA
                                            }else{
                                                strIvaCom="S";
                                                arlConRegPreEmpIng.add(INT_ARL_IVA_COM_ITM, strIvaCom);  // IVA
                                            }
                                            arlConRegPreEmpIng.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,strMerIngEgrFisBod);  // ITEM EGRESA FISICAMENTE 
                                            arlConRegPreEmpIng.add(INT_ARL_IND_ORG, intConLinArr);  // PARA REORGANIZAR 
                                            arlConDatPreEmpIng.add(arlConRegPreEmpIng);
                                            
                                            blnPreInv=true;
                                        } 
                                        else{
                                            strErr="Problemas con el Item:"+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM);
                                            System.out.println(strErr);
                                            blnRes=false;
                                        }
                                    }
                                    else{
                                        blnRes=false;
                                    }
                                
                                }////
                            }
                            dblCanVenEmp=0.0; // Ya se envio ese dato de regresa a 0 para no enviar cuando ya tenga todo pedido
                        }
                        rstLoc.close();
                        rstLoc=null;                    
                    }
                } /* FINAL DEL FOR I */
            }
            else{blnRes=false;}
        } 
        catch(Exception Evt) { 
            objUti.mostrarMsgErr_F1(Jcomponente, Evt);  
             
            blnRes=false;
        }  
        return blnRes;
    }
    
   
    
    /**
     * obtenerDatosItem
     * Se obtienen datos necesarios del item para poder enviar a ZafMovInv
     * @param codItm
     * @return 
     */
    private boolean obtenerDatosItem(int intCodEmpEgr , int codItmMae, int intCodBodEgr,int intCodBodIng /* Bodega de ingreso es con ObjParSis la empresa */){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        boolean blnRes=true;
        String strCadena;
        int intCodBodGrpIng=0,intCodBodGrpEgr;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
               stmLoc=conLoc.createStatement();
               strSql=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_cosUni IS NULL THEN 0 ELSE a1.nd_cosUni END as nd_cosUni, \n";
               strSql+="       a1.nd_preVta1, a1.st_ivaVen, CASE WHEN a1.tx_codAlt2 IS NULL THEN '' ELSE a1.tx_codAlt2 END as tx_codAlt2, a2.co_itmMae, \n";
               strSql+="       CASE WHEN a1.co_uni IS NULL THEN 0 ELSE a1.co_uni END as co_uni, ";
               strSql+="       CASE WHEN a1.nd_pesItmKgr IS NULL THEN 0 ELSE a1.nd_pesItmKgr END as nd_pesItmKgr , GRU.co_itm as co_itmGru, \n";
               strSql+="       a1.st_ivaCom, a1.st_ivaVen, a3.tx_desCor";
               strSql+=" FROM tbm_inv as a1 \n";
               strSql+=" INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
               strSql+=" INNER JOIN tbm_equInv as GRU ON (a2.co_ItmMae=GRU.co_itmMae AND GRU.co_emp="+objParSis.getCodigoEmpresaGrupo()+") \n";
               strSql+=" LEFT OUTER JOIN tbm_var as a3 ON (a1.co_uni=a3.co_reg) \n";
               strSql+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" and a1.st_reg='A' AND GRU.co_itmMae="+codItmMae;
               rstLoc=stmLoc.executeQuery(strSql);
               if(rstLoc.next()){
                   strCodAltItm=rstLoc.getString("tx_codALt");
                   strNomItm=rstLoc.getString("tx_nomItm");
                   strEstIva=rstLoc.getString("st_ivaVen");
                   strCodAltItm2=rstLoc.getString("tx_codAlt2");
                   System.err.println("\n\n\n\n\n\n\n UNIDAD + " + rstLoc.getString("tx_desCor"));
                   strUniMedItm=rstLoc.getString("tx_desCor");
                   intCodItmGru=rstLoc.getInt("co_itmGru");
                   dblPesItm=rstLoc.getDouble("nd_pesItmKgr");
                   strIvaCom=rstLoc.getString("st_ivaCom");
                   strIvaVen=rstLoc.getString("st_ivaVen");
               }
               
               strSql=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_cosUni IS NULL THEN 0 ELSE a1.nd_cosUni END as nd_cosUni, \n";
               strSql+="       a1.nd_preVta1, a1.st_ivaVen, CASE WHEN a1.tx_codAlt2 IS NULL THEN '' ELSE a1.tx_codAlt2 END as tx_codAlt2, a2.co_itmMae, \n";
               strSql+="       CASE WHEN a1.co_uni IS NULL THEN 0 ELSE a1.co_uni END as co_uni, ";
               strSql+="       CASE WHEN a1.nd_pesItmKgr IS NULL THEN 0 ELSE a1.nd_pesItmKgr END as nd_pesItmKgr , GRU.co_itm as co_itmGru, \n";
               strSql+="       a1.st_ivaCom, a1.st_ivaVen, a3.tx_desCor";
               strSql+=" FROM tbm_inv as a1 \n";
               strSql+=" INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
               strSql+=" INNER JOIN tbm_equInv as GRU ON (a2.co_ItmMae=GRU.co_itmMae AND GRU.co_emp="+objParSis.getCodigoEmpresaGrupo()+") \n";
               strSql+=" LEFT OUTER JOIN tbm_var as a3 ON (a1.co_uni=a3.co_reg) \n";
               strSql+=" WHERE a1.co_emp="+intCodEmpEgr+" and a1.st_reg='A' AND GRU.co_itmMae="+codItmMae;
               rstLoc=stmLoc.executeQuery(strSql);
               if(rstLoc.next()){
                   dblCosUni=rstLoc.getDouble("nd_cosUni");
                   dblPreUni=rstLoc.getDouble("nd_preVta1");
               }
               
                strCadena="";
                strCadena+="SELECT co_bodGrp FROM tbr_bodEmpBodGrp WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_bod="+intCodBodIng;
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    intCodBodGrpIng=rstLoc.getInt("co_bodGrp");
                }
                
               
                   
               /* Si un item se confirma */
               strSql="";
               strSql+=" SELECT st_merIngEgrFisBod FROM tbm_cfgTerInvCon " ; 
               strSql+=" WHERE tx_ter="+objUti.codificar(String.valueOf(strCodAltItm.charAt(strCodAltItm.length()-1)))+" AND co_emp="+objParSis.getCodigoEmpresa(); 
               strSql+=" AND co_bod="+intCodBodGrpIng;
               System.out.println("WHERE++++++ " + strSql);
               rstLoc=stmLoc.executeQuery(strSql);
               if(rstLoc.next()){
                   // SI SE CONFIRMA
                   if(rstLoc.getString("st_merIngEgrFisBod").equals("S")){
                        strMerIngEgrFisBod="S";
                   }
                   else{
                       strMerIngEgrFisBod="N";
                    }
               }
               
               
               conLoc.close();
               conLoc=null;
               rstLoc.close();
               rstLoc=null;
               stmLoc.close();
               stmLoc=null;
            }
      }
      catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
      }
      catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
      }
        return blnRes;
    }

    /**
    *   JoséMario:
    *        COMPRA VENTA ENTRE EMPRESA GENERA VARIOS TIPOS DE DOCUMENTOS 
    *        PRESTAMOS ENTRE EMPRESAS
    *           SE DEBE EJECUTAR DEL 1 AL 26 DE CADA MES EXCEPTO EN FEBRERO Y DICIEMBRE QUE SERA HASTA EL 25
    *           205 - 206
    *           205;"INBOPR";"Ingresos a Bodega (Préstamo relacionadas)"
    *           206;"EGBOPR";"Egresos de Bodega (Préstamo relacionadas)"
    *        VENTAS ENTRE EMPRESAS 
    *           2 - 228 = 4 - 229
    *           SE EJECUTA LOS DEMAS DIAS DEPENDIENDO DEL MES
    *           2;"FACCOM";"Factura de compras"
    *           228;"FACVENE";"Factura de ventas (Electrónica)"
    *   NOTA: MODIFICAR METODOS DEPRECATED 
    *       null=No aplica
    *       P=Préstamos
    *       C=Compras/Ventas
    *       D=Devoluciones de compras/ventas
    * 
    **/
    
    
    private boolean obtenerConfiguracionDelSistema(){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT tx_tipProReaEmpRel FROM tbm_emp WHERE co_emp="+objParSis.getCodigoEmpresaGrupo()+" ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strTipProReaEmpRel=rstLoc.getString("tx_tipProReaEmpRel");// P PRESTAMOS C COMPRA/VENTA D DEVOLUCIONES
                }
                rstLoc.close();
                stmLoc.close();
            }
            conLoc.close();
            conLoc=null;
            rstLoc=null;
            stmLoc=null;
            
            
            
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        return blnRes;
    }
    
    
    /**
     * Datos necesarios para los prestamos entre empresas     
     * 
     * @param rstLoc
     * @return 
     */
    
    private boolean obtenerDatosCompraVenta(java.sql.ResultSet rstExt, int intCodItmMae ){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{

            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT co_itmMae, co_emp, co_itm \n";
                strSql+=" FROM tbm_equInv \n";
                strSql+=" WHERE co_emp="+intCodEmpGenVen+" AND co_itmMae="+intCodItmMae+" \n";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodItmGenVen=rstLoc.getInt("co_itm");
                }
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        return blnRes;
    }
    
    
    
     /**
      * <h1>obtenerTipoDocumentos:</h1>
      *    Obtenemos la configuración que se encuentra en  tbm_emp.tx_tipProReaEmpRel, 
      *     y con eso obtenemos la configuracion de la empresa segun eso sabemos que tipo de proceso realiza la empresa 
      *     despues en la tabla de documentos usamos los del proceso correspondiente (tbm_cfgTipDocUtiProAut)
      *    null=No aplica
      *    <br> P=Préstamos </br>
      *        C=Compras/Ventas
      *        D=Devoluciones de compras/ventas
      *     //Constantes: Para el contenedor SOLICITUDES DE TRANSFERENCIA JoséMario 26/Abril/2015.
                final int INT_ARL_SOL_TRA_COD_EMP=0;
                final int INT_ARL_SOL_TRA_COD_LOC=1;
                final int INT_ARL_SOL_TRA_COD_TIP_DOC=2;
                final int INT_ARL_SOL_TRA_COD_DOC=3;
                final int INT_ARL_SOL_TRA_COD_BOD_EGR=4;
                * 
                * 1;7;Transferencias de inventario;5;46;F;TRAINV;A;--> 269 SOTRIN 
                  1;15;Transferencias de inventario (Ventas);5;153;F;TRINAU;A; --> 270 SOTRINV 
                  1;17;Transferencias de inventario (Importaciones);5;204;F;TRINIM;A; -->281 SOTRINI
                  1;16;Transferencias de inventario (Reposiciones);5;172;F;TRINRB;A;  --> 280 SOTRINR 
                  1;18;Transferencias de inventario (Compras locales);5;234;F;TRINCL;A; --> 282 SOTRINC



      * @param rstExt
      * @return 
      */   
    private boolean obtenerTipoDocumentos(java.sql.ResultSet rstExt, int intTipMov){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        int intCodCfgIng=0,intCodCfgEgr=0, intCodCfgDevVen=0, intCodCfgDevCom=0,intCodCfgTra=0;
        int intCodTipDocSolTraInv=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(intTipMov==2){  // CUANDO NO ES TRANSFERENCIA 
                    strSql="";
                    strSql+=" SELECT co_empOrg, co_cliEmpOrg \n";
                    strSql+=" FROM tbm_cfgEmpRel \n";  
                    strSql+=" WHERE co_empOrg="+rstExt.getInt("co_emp")+" AND co_empDes="+objParSis.getCodigoEmpresa()+" \n";
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        intCodCliGenVen=rstLoc.getInt("co_cliEmpOrg");
                    }
                    strSql="";
                    strSql+=" SELECT co_empOrg, co_cliEmpOrg \n";
                    strSql+=" FROM tbm_cfgEmpRel \n";  
                    strSql+=" WHERE co_empOrg="+objParSis.getCodigoEmpresa()+" AND co_empDes="+rstExt.getInt("co_emp")+" \n";
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        intCodCliGenCom=rstLoc.getInt("co_cliEmpOrg");
                    }
                    
                    rstLoc.close();
                    rstLoc=null;
                }
                
                intCodTipDocSolTraInv=objUti.getIntValueAt(arlDatSolTra, 0, INT_ARL_SOL_TRA_COD_TIP_DOC);
                
                
                /*
                objParSis EMPRESA INGRESO rstExt EMPRESA EGRESO
                */
                //VENTA = EGRESO = DEVOLUCION EN COMPRA ;
                switch(rstExt.getInt("co_emp")){
                    case 1: // TUVAL 
                        if(strTipProReaEmpRel.equals("P")){
                            intCodCfgEgr=1; // 1;Préstamos (Egreso);5;206
                        }
                        else if(strTipProReaEmpRel.equals("C")){
                            
                            intCodCfgEgr=3; // 3;Facturas;5;228
                        }
                        else if(strTipProReaEmpRel.equals("D")){
                            intCodCfgEgr=3; // 3;Facturas;5;228
                            intCodCfgDevCom=6; //6;Devoluciones de compras;5;4
                        }
                        break;
                    case 2: // CASTEK
                        if(strTipProReaEmpRel.equals("P")){
                            intCodCfgEgr=1; // 1;Préstamos (Egreso);5;206
                        }
                        else if(strTipProReaEmpRel.equals("C")){
                            intCodCfgEgr=3; // 3;Facturas;5;228
                        }
                        else if(strTipProReaEmpRel.equals("D")){
                            intCodCfgEgr=3; // 3;Facturas;5;228
                            intCodCfgDevCom=6; //6;Devoluciones de compras;5;4
                        }
                        break;
                    case 4: // DIMULTI
                        if(strTipProReaEmpRel.equals("P")){
                            intCodCfgEgr=1; // 1;Préstamos (Egreso);2;206
                        }
                        else if(strTipProReaEmpRel.equals("C")){
                            intCodCfgEgr=3; // 3;Facturas;5;228
                        }
                        else if(strTipProReaEmpRel.equals("D")){
                            intCodCfgEgr=3; // 3;Facturas;5;228
                            intCodCfgDevCom=6; //6;Devoluciones de compras;5;4
                        }
                        break;
                }
                // INGRESO = ORDEN DE COMPRA = DEVOLUCION EN VENTA 
                switch(objParSis.getCodigoEmpresa()){
                    case 1: // TUVAL 
                        if(strTipProReaEmpRel.equals("P")){
                            intCodCfgIng=2; // 2;Préstamos (Ingreso);5;205
                        }
                        else if(strTipProReaEmpRel.equals("C")){
                            intCodCfgIng=4; // 4;Ordenes de compra;5;2
                        }
                        else if(strTipProReaEmpRel.equals("D")){
                            intCodCfgIng=4; // 4;Ordenes de compra;5;2
                            intCodCfgDevVen=5; //5;Devoluciones de ventas;5;229
                        }
                        break;
                    case 2: // CASTEK
                        if(strTipProReaEmpRel.equals("P")){
                            intCodCfgIng=2; // 2;Préstamos (Ingreso);5;205
                        }
                        else if(strTipProReaEmpRel.equals("C")){
                            intCodCfgIng=4; // 4;Ordenes de compra;5;2
                        }
                        else if(strTipProReaEmpRel.equals("D")){
                            intCodCfgIng=4; // 4;Ordenes de compra;5;2
                            intCodCfgDevVen=5; //5;Devoluciones de ventas;5;229
                        }
                        break;
                    case 4: // DIMULTI
                        if(strTipProReaEmpRel.equals("P")){
                            intCodCfgIng=2; // 2;Préstamos (Ingreso);5;205
                        }
                        else if(strTipProReaEmpRel.equals("C")){
                            intCodCfgIng=4; // 4;Ordenes de compra;5;2
                        }
                        else if(strTipProReaEmpRel.equals("D")){
                            intCodCfgIng=4; // 4;Ordenes de compra;5;2
                            intCodCfgDevVen=5; //5;Devoluciones de ventas;5;229
                        }
                        break;    
                }
      /* ================================TRANSFERENCIAS SEGUN LA SOLICITUD==================================================== */
                /*1;7;Transferencias de inventario;5;46;F;TRAINV;A;--> 269 SOTRIN 
                  1;15;Transferencias de inventario (Ventas);5;153;F;TRINAU;A; --> 270 SOTRINV 
                  1;17;Transferencias de inventario (Importaciones);5;204;F;TRINIM;A; -->281 SOTRINI
                  1;16;Transferencias de inventario (Reposiciones);5;172;F;TRINRB;A;  --> 280 SOTRINR 
                  1;18;Transferencias de inventario (Compras locales);5;234;F;TRINCL;A; --> 282 SOTRINC*/
                switch(intCodTipDocSolTraInv) {
                    case 269:  intCodCfgTra=7; break; 
                       case 270:  intCodCfgTra=15; break; 
                           case 281:  intCodCfgTra=17; break; 
                               case 280:  intCodCfgTra=16; break; 
                                   case 282:  intCodCfgTra=18; break; 
                }
                /* ==================================================================================== */
                
                
                if(strTipProReaEmpRel.equals("P") || strTipProReaEmpRel.equals("C")){
                    strSql="";
                    strSql+=" SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql+=" FROM tbm_cfgTipDocUtiProAut ";
                    strSql+=" WHERE co_emp="+rstExt.getInt("co_emp")+" AND co_cfg="+intCodCfgEgr+" AND st_reg='A'" ;
//                    System.out.println("Local Venta: " + strSql);
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){ /* LA VENTA ES EL EGRESO DE LA MERCADERIA */
                        intCodEmpGenVen=rstLoc.getInt("co_emp");
                        intCodLocGenVen=rstLoc.getInt("co_loc");
                        intCodTipDocGenVen=rstLoc.getInt("co_tipDoc");
                    }
                    strSql="";
                    strSql+=" SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql+=" FROM tbm_cfgTipDocUtiProAut ";
                    strSql+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_cfg="+intCodCfgIng+" AND st_reg='A'" ;
//                    System.out.println("Local Compra: " + strSql);
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){ /* LA COMPRA ES EL INGRESO DE LA MERCADERIA */
                        intCodEmpGenCom=rstLoc.getInt("co_emp");
                        intCodLocGenCom=rstLoc.getInt("co_loc");
                        intCodTipDocGenCom=rstLoc.getInt("co_tipDoc");
                    }
                    strSql="";
                    strSql+=" SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql+=" FROM tbm_cfgTipDocUtiProAut ";
                    strSql+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_cfg="+intCodCfgTra+" AND st_reg='A'" ;
//                    System.out.println("Local transferencias: " + strSql);
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        intCodLocTra=rstLoc.getInt("co_loc");/* TRANSFERENCIA  */
                        intCodTipDocGenTra=rstLoc.getInt("co_tipDoc");
                    }
                    rstLoc.close();
                    rstLoc=null;
                }
                else if(strTipProReaEmpRel.equals("D")){
                    strSql="";
                    strSql+=" SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql+=" FROM tbm_cfgTipDocUtiProAut ";
                    strSql+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_cfg="+intCodCfgDevVen+" AND st_reg='A'" ;
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){ /* LA VENTA ES EL EGRESO DE LA MERCADERIA */
                        intCodEmpGenDevVen=rstLoc.getInt("co_emp");
                        intCodLocGenDevVen=rstLoc.getInt("co_loc");
                        intCodTipDocGenDevVen=rstLoc.getInt("co_tipDoc");
                    }
                    strSql="";
                    strSql+=" SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql+=" FROM tbm_cfgTipDocUtiProAut ";
                    strSql+=" WHERE co_emp="+rstExt.getInt("co_emp")+" AND co_cfg="+intCodCfgDevCom+" AND st_reg='A'" ;
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){ /* LA COMPRA ES EL INGRESO DE LA MERCADERIA */ 
                        intCodEmpGenDevCom=rstLoc.getInt("co_emp");
                        intCodLocGenDevCom=rstLoc.getInt("co_loc");
                        intCodTipDocGenDevCom=rstLoc.getInt("co_tipDoc");
                    }
                    strSql="";
                    strSql+=" SELECT co_emp,co_cfg, tx_nom, co_loc, co_tipDoc, tx_cfgLocUti, tx_obs1, st_reg ";
                    strSql+=" FROM tbm_cfgTipDocUtiProAut ";
                    strSql+=" WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_cfg="+intCodCfgTra+" AND st_reg='A'" ;
                    rstLoc=stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        intCodLocTra=rstLoc.getInt("co_loc");
                        intCodTipDocGenTra=rstLoc.getInt("co_tipDoc");
                    }
                    rstLoc.close();
                    rstLoc=null;
                }
                
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
         }
        return blnRes;
    }
    
    private ArrayList arlDatBodEmpTra;
    
    private boolean obtenerBodegasPorEmpresa(java.sql.Connection con){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=false;
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                arlDatBodEmpTra = new ArrayList();
                strSql="";
                strSql+=" SELECT co_bod ";
                strSql+=" FROM tbm_bod WHERE co_emp="+objParSis.getCodigoEmpresa() + " AND st_reg='A' ORDER BY co_bod";
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlDatBodEmpTra.add(rstLoc.getInt("co_bod"));
                }
                blnRes=true;
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
         }
        return blnRes;
    }
    
    
        private ArrayList arlDatBodEmpComVen;
    
    private boolean obtenerBodegasPorEmpresaCompraVenta(java.sql.Connection con, int intCodEmpComVen){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=false;
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                arlDatBodEmpComVen = new ArrayList();
                strSql="";
                strSql+=" SELECT co_bod ";
                strSql+=" FROM tbm_bod WHERE co_emp="+intCodEmpComVen+ " AND st_reg='A' ORDER BY co_bod";
//                System.out.println("BODEGAS POR EMPRESA!!! NUEVO!!! " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlDatBodEmpComVen.add(rstLoc.getInt("co_bod"));
                }
                blnRes=true;
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
         }
        return blnRes;
    }

    /**
     * Despues de llenar los ArrayList se los envia al metodo que genera los query 
     * 
     * En el caso de PRESTAMOS ENTRE EMPRESAS 
     * Se puede dar el caso de que en una venta se escoja material de las otras dos empresas en ese caso se necesitara enviar dos 
     * veces el dato a la clase ZafMovInv, pues esta solo inserta una cabecera con muchos registros
     * 
     * Forma de enviar: se envia por empresa y por bodega: 
     * 
     * @param con
     * @return 
     */
    private ArrayList arlConDatTraAux; 
    private ArrayList arlConRegTraAux;
    
    private boolean enviarMovimiento(java.sql.Connection con, ZafCfgBod objCfgBod){
        boolean blnRes=true, blnIsBodTra=false,blnIsBodComVen=false;
        arlConDatTraInvStkEgr = new ArrayList();
        arlConDatTraInvStkIng = new ArrayList();
        int intCodBodRel, intCodBodEmpTra;
      //  System.out.println("enviarMovimiento::: J objCfgBod:" + objCfgBod.toString());
        try{
            if(blnTraInv==true){  /// TRANSFERENCIAS
                arlConDatTraAux = new ArrayList();
                if(obtenerBodegasPorEmpresa(con)){
                    for(int T=0;T<arlDatBodEmpTra.size();T++){
                        intCodBodEmpTra=Integer.parseInt(arlDatBodEmpTra.get(T).toString());//TODO LO DE ESTA BODEGA 
                        for(int R=0;R<arlConDatTraInv.size();R=R+2){
                            if(objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_BOD_ING_EGR) == intCodBodEmpTra){
                                //EGRESO
                                arlConRegTraAux = new ArrayList();
                                arlConRegTraAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_EMP));
                                arlConRegTraAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_LOC));
                                arlConRegTraAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_TIP_DOC));
                                arlConRegTraAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_CLI));
                                arlConRegTraAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_BOD_ING_EGR));
                                arlConRegTraAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_ITM_GRP));
                                arlConRegTraAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_ITM));
                                arlConRegTraAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_COD_ITM_MAE));
                                arlConRegTraAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatTraInv, R, INT_ARL_COD_ITM_ALT));
                                arlConRegTraAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatTraInv, R, INT_ARL_NOM_ITM));
                                arlConRegTraAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatTraInv, R, INT_ARL_UNI_MED_ITM));
                                arlConRegTraAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatTraInv, R, INT_ARL_COD_LET_ITM));
                                arlConRegTraAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatTraInv, R, INT_ARL_CAN_MOV));
                                arlConRegTraAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatTraInv, R, INT_ARL_COS_UNI));
                                arlConRegTraAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatTraInv, R, INT_ARL_PRE_UNI));
                                arlConRegTraAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatTraInv, R, INT_ARL_PES_ITM));
                                arlConRegTraAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatTraInv, R, INT_ARL_IVA_COM_ITM));
                                arlConRegTraAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatTraInv, R, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                arlConRegTraAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatTraInv, R, INT_ARL_IND_ORG));
                                arlConDatTraInvStkEgr.add(arlConRegTraAux);
                                arlConDatTraAux.add(arlConRegTraAux);
                                //INGRESO
                                arlConRegTraAux = new ArrayList();
                                arlConRegTraAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_EMP));
                                arlConRegTraAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_LOC));
                                arlConRegTraAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_TIP_DOC));
                                arlConRegTraAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_CLI));
                                arlConRegTraAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_BOD_ING_EGR));
                                arlConRegTraAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_ITM_GRP));
                                arlConRegTraAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_ITM));
                                arlConRegTraAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_COD_ITM_MAE));
                                arlConRegTraAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatTraInv, R+1, INT_ARL_COD_ITM_ALT));
                                arlConRegTraAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatTraInv, R+1, INT_ARL_NOM_ITM));
                                arlConRegTraAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatTraInv, R+1, INT_ARL_UNI_MED_ITM));
                                arlConRegTraAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatTraInv, R+1, INT_ARL_COD_LET_ITM));
                                arlConRegTraAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatTraInv, R+1, INT_ARL_CAN_MOV));
                                arlConRegTraAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatTraInv, R+1, INT_ARL_COS_UNI));
                                arlConRegTraAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatTraInv, R+1, INT_ARL_PRE_UNI));
                                arlConRegTraAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatTraInv, R+1, INT_ARL_PES_ITM));
                                arlConRegTraAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatTraInv, R+1, INT_ARL_IVA_COM_ITM));
                                arlConRegTraAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatTraInv, R+1, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                arlConRegTraAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatTraInv, R+1, INT_ARL_IND_ORG));
                                arlConDatTraInvStkIng.add(arlConRegTraAux);
                                arlConDatTraAux.add(arlConRegTraAux);
                                blnIsBodTra=true;
                            }
                        }// YA SE TERMINO EL CONTENEDOR
                        if(blnIsBodTra){
                            for(int J=0;J<arlDatSolTra.size();J++){/*PARA PODER ENVIAR VARIAS SOLICITUDES SEGUN LA BODEGA DE DONDE SE PIDE LA MERCA JM 19/May/2016*/
                                if(isBodegaEgreso(objUti.getIntValueAt(arlConDatTraAux, 0, INT_ARL_COD_EMP),
                                                objUti.getIntValueAt(arlConDatTraAux, 0, INT_ARL_COD_BOD_ING_EGR),
                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_BOD_EGR))){
                          //BODEGA_TRANSFERENCIA = BODEGA SOLICITUD 
                                    
                                    if(generaODTrans()){
                                        objCfgBod=objCfgBodGlo;
                                        if(objCfgBod==null){
                                            System.out.println("Juta objCfgBod NULO");
                                            objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "ZafReaMovInv", "NULOO!!!! "+ arlConDatTraAux.toString()); 
                                        }
                                        else{System.out.println("Juta objCfgBod NO ES NULO");}
                                        if(objMovInv.getDatoTransferencia(con,intCodigoEmpresa,intCodLocTra,intCodTipDocGenTra, dtpFechaDocumento , 
                                                                intCodigoBodegaEgresoTrans,intCodigoBodegaEgresoTrans,'N',arlConDatTraAux,
                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_EMP),
                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_LOC),
                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_TIP_DOC),
                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_DOC),strTipConIngEgr,objIsGenOD,objCfgBod)){
                                            if(generaMovimientoInventarioTransferencia(con)){
                                                System.err.println("<----- costear transferencia----->");
                                                if(objUti.costearDocumento(Jcomponente, objParSis, con, objMovInv.intCodEmpTraAux, objMovInv.intCodLocTraAux, objMovInv.intCodTipDocTraAux, objMovInv.intCodDocTraAux)){
                                                    System.err.println("<----- fin costear transferencia----->");
                                                    System.out.println("ENVIA....  ");        
                                                }else{System.out.println("ERROR COSTEO"); blnRes=false;}
                                            }else{blnRes=false;}
                                        }
                                        else{
                                           System.err.println("FALLA EN ZafMovInv....  ");
                                           blnRes=false;
                                        }
                                    }
                                }
                            }
                            arlConDatTraInvStkEgr.clear(); // Se limpian las listas de los items
                            arlConDatTraInvStkIng.clear();
                            arlConDatTraAux.clear();
                            blnIsBodTra=false;
                        }
                        
                    }
                }// BODEGAS POR EMPRESA
            }
            //Rosa Zambrano 21/Jun/2017
            if(strTipProReaEmpRel.equals("P")){
                chrGeneraIva='N';
            }else{
                chrGeneraIva='S';
            }
            if(blnPreInv==true){ /// PRESTAMOS ENTRE EMPRESAS // ENVIA POR EMPRESA - BODEGA 
                if(ordenarListasJota()){
                    if(blnPreInvTuv){
                        arlConDatPreInvEgrAux = new ArrayList();
                        arlConDatPreInvIngAux = new ArrayList();
                        if(obtenerBodegasPorEmpresaCompraVenta(con,objUti.getIntValueAt(arlConDatPreEgrInvTuv, 0, INT_ARL_COD_EMP))){
                            for(int T=0;T<arlDatBodEmpComVen.size();T++){
                                intCodBodEmpTra=Integer.parseInt(arlDatBodEmpComVen.get(T).toString());//TODO LO DE ESTA BODEGA 
                                for(int R=0;R<arlConDatPreEgrInvTuv.size();R++){
                                    if(objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_BOD_ING_EGR) == intCodBodEmpTra){
                                        //EGRESO
                                        arlConRegPreInvEgrAux = new ArrayList();
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_EMP));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_LOC));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_TIP_DOC));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_CLI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_BOD_ING_EGR));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_ITM_GRP));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_ITM_MAE));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_ITM_ALT));
                                        arlConRegPreInvEgrAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_NOM_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_UNI_MED_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COD_LET_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_CAN_MOV));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_COS_UNI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_PRE_UNI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_PES_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_IVA_COM_ITM));
                                        arlConRegPreInvEgrAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatPreEgrInvTuv, R, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                        arlConRegPreInvEgrAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatPreEgrInvTuv, R, INT_ARL_IND_ORG));
                                        arlConDatPreInvEgrAux.add(arlConRegPreInvEgrAux);
                                        //INGRESO
                                        arlConRegPreInvIngAux = new ArrayList();
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_EMP));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_LOC));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_TIP_DOC));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_CLI));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_BOD_ING_EGR));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_ITM_GRP));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_ITM_MAE));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_ITM_ALT));
                                        arlConRegPreInvIngAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, R, INT_ARL_NOM_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatPreIngInvTuv, R, INT_ARL_UNI_MED_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COD_LET_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatPreIngInvTuv, R, INT_ARL_CAN_MOV));
                                        arlConRegPreInvIngAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatPreIngInvTuv, R, INT_ARL_COS_UNI));
                                        arlConRegPreInvIngAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatPreIngInvTuv, R, INT_ARL_PRE_UNI));
                                        arlConRegPreInvIngAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatPreIngInvTuv, R, INT_ARL_PES_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, R, INT_ARL_IVA_COM_ITM));
                                        arlConRegPreInvIngAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatPreIngInvTuv, R, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                        arlConRegPreInvIngAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatPreIngInvTuv, R, INT_ARL_IND_ORG));
                                        arlConDatPreInvIngAux.add(arlConRegPreInvIngAux);
                                        blnIsBodComVen=true;
                                    }
                                }
                                if(blnIsBodComVen){
                                    for(int J=0;J<arlDatSolTra.size();J++){/*PARA PODER ENVIAR VARIAS SOLICITUDES SEGUN LA BODEGA DE DONDE SE PIDE LA MERCA JM 19/May/2016*/
                                        if(isBodegaEgreso(objUti.getIntValueAt(arlConDatPreInvEgrAux, 0, INT_ARL_COD_EMP),
                                                          objUti.getIntValueAt(arlConDatPreInvEgrAux, 0, INT_ARL_COD_BOD_ING_EGR),
                                                          objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_BOD_EGR))){
                                            if(generaODprestamos( )){
                                                objCfgBod=objCfgBodGlo;
                                                if(objCfgBod==null){
                                                    System.out.println("Juta objCfgBod NULO");
                                                    objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "ZafReaMovInv", "NULOO!!!! "+ arlConDatPreInvEgrAux.toString()); 
                                                }
                                                else{System.out.println("Juta objCfgBod NO ES NULO");}
                                                if(objMovInv.getDatoEgresoIngreso(con,dtpFechaDocumento,chrGeneraIva,arlConDatPreInvEgrAux, arlConDatPreInvIngAux,
                                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_EMP),
                                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_LOC),
                                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_TIP_DOC),
                                                                                objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_DOC),strTipConIngEgr,objIsGenOD,objCfgBod)){
                                                    if(generaMovimientoInventarioPrestamosIngreso(con)){
                                                        if(generaMovimientoInventarioPrestamosEgreso(con)){
                                                            System.err.println("<----- costear Egreso----->");
                                                            if(objUti.costearDocumento(Jcomponente, objParSis, con, objMovInv.intCodEmpEgrAux, objMovInv.intCodLocEgrAux, objMovInv.intCodTipDocEgrAux, objMovInv.intCodDocEgrAux)){
                                                                System.err.println("<----- fin costear Egreso----->");
                                                                System.err.println("<----- costear Ingreso----->");
                                                                if(objUti.costearDocumento(Jcomponente, objParSis, con, objMovInv.intCodEmpIngAux, objMovInv.intCodLocIngAux, objMovInv.intCodTipDocIngAux, objMovInv.intCodDocIngAux)){
                                                                    System.err.println("<----- fin costear Ingreso----->");
                                                                    System.out.println("ENVIA....  "); 
                                                                }else{System.out.println("ERROR COSTEO INGRESO"); blnRes=false;}
                                                            }else{System.out.println("ERROR COSTEO EGRESO"); blnRes=false;}
                                                        }else{blnRes=false;}
                                                    }else{blnRes=false;}
                                                }
                                                else{
                                                  System.out.println("FALLA EN ZafMovInv....  ");
                                                  blnRes=false;
                                                }
                                            }
                                        }
                                    }
                                    arlConDatPreInvEgrAux.clear();
                                    arlConDatPreInvIngAux.clear();
                                    blnIsBodComVen=false;
                                }
                            }
                        } // BODEGAS POR EMPRESA 
                    } /* FIN TUVAL */
                        
    /*  ===================================== CASTEK ==================================== */
                    if(blnPreInvCas){
                        arlConDatPreInvEgrAux = new ArrayList();
                        arlConDatPreInvIngAux = new ArrayList();
                        if(obtenerBodegasPorEmpresaCompraVenta(con,objUti.getIntValueAt(arlConDatPreEgrInvCas, 0, INT_ARL_COD_EMP))){
                            for(int T=0;T<arlDatBodEmpComVen.size();T++){
                                intCodBodEmpTra=Integer.parseInt(arlDatBodEmpComVen.get(T).toString());//TODO LO DE ESTA BODEGA 
                                for(int R=0;R<arlConDatPreEgrInvCas.size();R++){
                                    if(objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_BOD_ING_EGR) == intCodBodEmpTra){
                                        //EGRESO
                                        arlConRegPreInvEgrAux = new ArrayList();
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_EMP));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_LOC));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_TIP_DOC));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_CLI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_BOD_ING_EGR));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_ITM_GRP));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_ITM_MAE));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_ITM_ALT));
                                        arlConRegPreInvEgrAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEgrInvCas, R, INT_ARL_NOM_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatPreEgrInvCas, R, INT_ARL_UNI_MED_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COD_LET_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatPreEgrInvCas, R, INT_ARL_CAN_MOV));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatPreEgrInvCas, R, INT_ARL_COS_UNI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatPreEgrInvCas, R, INT_ARL_PRE_UNI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatPreEgrInvCas, R, INT_ARL_PES_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEgrInvCas, R, INT_ARL_IVA_COM_ITM));
                                        arlConRegPreInvEgrAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatPreEgrInvCas, R, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                        arlConRegPreInvEgrAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatPreEgrInvCas, R, INT_ARL_IND_ORG));
                                        arlConDatPreInvEgrAux.add(arlConRegPreInvEgrAux);
                                        //INGRESO
                                        arlConRegPreInvIngAux = new ArrayList();
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_EMP));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_LOC));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_TIP_DOC));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_CLI));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_BOD_ING_EGR));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_ITM_GRP));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_ITM_MAE));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_ITM_ALT));
                                        arlConRegPreInvIngAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, R, INT_ARL_NOM_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatPreIngInvCas, R, INT_ARL_UNI_MED_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, R, INT_ARL_COD_LET_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatPreIngInvCas, R, INT_ARL_CAN_MOV));
                                        arlConRegPreInvIngAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatPreIngInvCas, R, INT_ARL_COS_UNI));
                                        arlConRegPreInvIngAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatPreIngInvCas, R, INT_ARL_PRE_UNI));
                                        arlConRegPreInvIngAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatPreIngInvCas, R, INT_ARL_PES_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, R, INT_ARL_IVA_COM_ITM));
                                        arlConRegPreInvIngAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatPreIngInvCas, R, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                        arlConRegPreInvIngAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatPreIngInvCas, R, INT_ARL_IND_ORG));
                                        arlConDatPreInvIngAux.add(arlConRegPreInvIngAux);
                                        blnIsBodComVen=true;
                                    }
                                }
                                if(blnIsBodComVen){
                                    for(int J=0;J<arlDatSolTra.size();J++){/*PARA PODER ENVIAR VARIAS SOLICITUDES SEGUN LA BODEGA DE DONDE SE PIDE LA MERCA JM 19/May/2016*/
                                        if(isBodegaEgreso(objUti.getIntValueAt(arlConDatPreInvEgrAux, 0, INT_ARL_COD_EMP),
                                                          objUti.getIntValueAt(arlConDatPreInvEgrAux, 0, INT_ARL_COD_BOD_ING_EGR),
                                                          objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_BOD_EGR))){
                                            if(generaODprestamos()){
                                                objCfgBod=objCfgBodGlo;
                                                if(objCfgBod==null){
                                                    System.out.println("Juta objCfgBod NULO");
                                                    objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "ZafReaMovInv", "NULOO!!!! "+ arlConDatPreInvEgrAux.toString()); 
                                                }else{System.out.println("Juta objCfgBod NO ES NULO");}
                                                if(objMovInv.getDatoEgresoIngreso(con,dtpFechaDocumento,chrGeneraIva,arlConDatPreInvEgrAux, arlConDatPreInvIngAux,
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_EMP),
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_LOC),
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_TIP_DOC),
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_DOC),strTipConIngEgr,objIsGenOD,objCfgBod)){
                                                    if(generaMovimientoInventarioPrestamosIngreso(con)){
                                                        if(generaMovimientoInventarioPrestamosEgreso(con)){
                                                            System.err.println("<----- costear Egreso----->");
                                                            if(objUti.costearDocumento(Jcomponente, objParSis, con, objMovInv.intCodEmpEgrAux, objMovInv.intCodLocEgrAux, objMovInv.intCodTipDocEgrAux, objMovInv.intCodDocEgrAux)){
                                                                System.err.println("<----- fin costear Egreso----->");
                                                                System.err.println("<----- costear Ingreso----->");
                                                                if(objUti.costearDocumento(Jcomponente, objParSis, con, objMovInv.intCodEmpIngAux, objMovInv.intCodLocIngAux, objMovInv.intCodTipDocIngAux, objMovInv.intCodDocIngAux)){
                                                                    System.err.println("<----- fin costear Ingreso----->");
                                                                    System.out.println("ENVIA....  "); 
                                                                }else{System.out.println("ERROR COSTEO INGRESO"); blnRes=false;}
                                                            }else{System.out.println("ERROR COSTEO EGRESO"); blnRes=false;}
                                                        }else{blnRes=false;}
                                                    }else{blnRes=false;}
                                                }
                                                else{
                                                    System.out.println("FALLA EN ZafMovInv....  ");
                                                    blnRes=false;
                                                }
                                            }
                                        }
                                    }
                                    arlConDatPreInvEgrAux.clear();
                                    arlConDatPreInvIngAux.clear();
                                    blnIsBodComVen=false;
                                }
                            }
                        } // BODEGAS POR EMPRESA 
                    } /* FIN CASTEK */
    /*  ===================================== DIMULTI ==================================== */
                    if(blnPreInvDim){
                        arlConDatPreInvEgrAux = new ArrayList();
                        arlConDatPreInvIngAux = new ArrayList();
                        if(obtenerBodegasPorEmpresaCompraVenta(con,objUti.getIntValueAt(arlConDatPreEgrInvDim, 0, INT_ARL_COD_EMP))){
                            for(int T=0;T<arlDatBodEmpComVen.size();T++){
                                intCodBodEmpTra=Integer.parseInt(arlDatBodEmpComVen.get(T).toString());//TODO LO DE ESTA BODEGA 
                                for(int R=0;R<arlConDatPreEgrInvDim.size();R++){
                                    if(objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_BOD_ING_EGR) == intCodBodEmpTra){
                                        //EGRESO
                                        arlConRegPreInvEgrAux = new ArrayList();
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_EMP));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_LOC));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_TIP_DOC));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_CLI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_BOD_ING_EGR));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_ITM_GRP));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_ITM_MAE));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_ITM_ALT));
                                        arlConRegPreInvEgrAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEgrInvDim, R, INT_ARL_NOM_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatPreEgrInvDim, R, INT_ARL_UNI_MED_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COD_LET_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatPreEgrInvDim, R, INT_ARL_CAN_MOV));
                                        arlConRegPreInvEgrAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatPreEgrInvDim, R, INT_ARL_COS_UNI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatPreEgrInvDim, R, INT_ARL_PRE_UNI));
                                        arlConRegPreInvEgrAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatPreEgrInvDim, R, INT_ARL_PES_ITM));
                                        arlConRegPreInvEgrAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEgrInvDim, R, INT_ARL_IVA_COM_ITM));
                                        arlConRegPreInvEgrAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatPreEgrInvDim, R, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                        arlConRegPreInvEgrAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatPreEgrInvDim, R, INT_ARL_IND_ORG));
                                        arlConDatPreInvEgrAux.add(arlConRegPreInvEgrAux);
                                        //INGRESO
                                        arlConRegPreInvIngAux = new ArrayList();
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_EMP));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_LOC,  objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_LOC));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_TIP_DOC, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_TIP_DOC));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_CLI, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_CLI));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_BOD_ING_EGR, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_BOD_ING_EGR));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_GRP, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_ITM_GRP));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_ITM_MAE));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_ITM_ALT));
                                        arlConRegPreInvIngAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, R, INT_ARL_NOM_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_UNI_MED_ITM,  objUti.getStringValueAt(arlConDatPreIngInvDim, R, INT_ARL_UNI_MED_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, R, INT_ARL_COD_LET_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_CAN_MOV, objUti.getDoubleValueAt(arlConDatPreIngInvDim, R, INT_ARL_CAN_MOV));
                                        arlConRegPreInvIngAux.add(INT_ARL_COS_UNI, objUti.getDoubleValueAt(arlConDatPreIngInvDim, R, INT_ARL_COS_UNI));
                                        arlConRegPreInvIngAux.add(INT_ARL_PRE_UNI, objUti.getDoubleValueAt(arlConDatPreIngInvDim, R, INT_ARL_PRE_UNI));
                                        arlConRegPreInvIngAux.add(INT_ARL_PES_ITM, objUti.getDoubleValueAt(arlConDatPreIngInvDim, R, INT_ARL_PES_ITM));
                                        arlConRegPreInvIngAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, R, INT_ARL_IVA_COM_ITM));
                                        arlConRegPreInvIngAux.add( INT_ARL_EST_MER_ING_EGR_FIS_BOD,objUti.getStringValueAt( arlConDatPreIngInvDim, R, INT_ARL_EST_MER_ING_EGR_FIS_BOD));  
                                        arlConRegPreInvIngAux.add(INT_ARL_IND_ORG, objUti.getIntValueAt(arlConDatPreIngInvDim, R, INT_ARL_IND_ORG));
                                        arlConDatPreInvIngAux.add(arlConRegPreInvIngAux);
                                        blnIsBodComVen=true;
                                    }
                                }
                                if(blnIsBodComVen){
                                    for(int J=0;J<arlDatSolTra.size();J++){/*PARA PODER ENVIAR VARIAS SOLICITUDES SEGUN LA BODEGA DE DONDE SE PIDE LA MERCA JM 19/May/2016*/
                                        if(isBodegaEgreso(objUti.getIntValueAt(arlConDatPreInvEgrAux, 0, INT_ARL_COD_EMP),
                                                          objUti.getIntValueAt(arlConDatPreInvEgrAux, 0, INT_ARL_COD_BOD_ING_EGR),
                                                          objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_BOD_EGR))){
                                            if(generaODprestamos( )){
                                                objCfgBod=objCfgBodGlo;
                                                if(objCfgBod==null){
                                                    System.out.println("Juta objCfgBod NULO");
                                                    objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "ZafReaMovInv", "NULOO!!!! "+ arlConDatPreInvEgrAux.toString()); 
                                                }else{System.out.println("Juta objCfgBod NO ES NULO");}
                                                if(objMovInv.getDatoEgresoIngreso(con,dtpFechaDocumento,chrGeneraIva,arlConDatPreInvEgrAux, arlConDatPreInvIngAux,
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_EMP),
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_LOC),
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_TIP_DOC),
                                                                                  objUti.getIntValueAt(arlDatSolTra, J, INT_ARL_SOL_TRA_COD_DOC),strTipConIngEgr,objIsGenOD,objCfgBod)){
                                                    if(generaMovimientoInventarioPrestamosIngreso(con)){
                                                        if(generaMovimientoInventarioPrestamosEgreso(con)){
                                                            System.err.println("<----- costear Egreso----->");
                                                            if(objUti.costearDocumento(Jcomponente, objParSis, con, objMovInv.intCodEmpEgrAux, objMovInv.intCodLocEgrAux, objMovInv.intCodTipDocEgrAux, objMovInv.intCodDocEgrAux)){
                                                                System.err.println("<----- fin costear Egreso----->");
                                                                System.err.println("<----- costear Ingreso----->");
                                                                if(objUti.costearDocumento(Jcomponente, objParSis, con, objMovInv.intCodEmpIngAux, objMovInv.intCodLocIngAux, objMovInv.intCodTipDocIngAux, objMovInv.intCodDocIngAux)){
                                                                    System.err.println("<----- fin costear Ingreso----->");
                                                                    System.out.println("ENVIA....  "); 
                                                                }else{System.out.println("ERROR COSTEO INGRESO"); blnRes=false;}
                                                            }else{System.out.println("ERROR COSTEO EGRESO"); blnRes=false;}
                                                        }else{blnRes=false;}
                                                    }else{blnRes=false;}
                                                }
                                                else{
                                                    System.out.println("FALLA EN ZafMovInv....  ");
                                                    blnRes=false;
                                                }
                                            }

                                        }
                                    }
                                    arlConDatPreInvEgrAux.clear();
                                    arlConDatPreInvIngAux.clear();
                                    blnIsBodComVen=false;
                                }
                            }
                        } // BODEGAS POR EMPRESA 
                    } /* FIN DIMULTI */
                }else{
                    System.err.println("FALLA AL ORDENAR LAS LISTAS");
                    blnRes=false;
                }
            }
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        
        return blnRes;
    }
    
    
    private boolean generaODTrans(){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        boolean blnRes=false;
        String strCadena;
        ZafCfgBod objCfgBod;
        int intCodBodGrpIng=0,intCodBodGrpEgr=0,intCodBodIng,intCodBodEgr;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                intCodBodIng=objUti.getIntValueAt(arlConDatTraInvStkIng, 0,INT_ARL_COD_BOD_ING_EGR);
                intCodBodEgr=objUti.getIntValueAt(arlConDatTraInvStkEgr, 0,INT_ARL_COD_BOD_ING_EGR);
                
                strCadena="";
                strCadena+="SELECT co_bodGrp FROM tbr_bodEmpBodGrp WHERE co_emp="+intCodigoEmpresa+" AND co_bod="+intCodBodIng;
                System.out.println("generaODTrans: intCodBodIng "+strCadena);
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    intCodBodGrpIng=rstLoc.getInt("co_bodGrp");
                }
                strCadena="";
                strCadena+="SELECT co_bodGrp FROM tbr_bodEmpBodGrp WHERE co_emp="+intCodigoEmpresa+" AND co_bod="+intCodBodEgr;
                System.out.println("generaODTrans: intCodBodEgr "+strCadena);
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    intCodBodGrpEgr=rstLoc.getInt("co_bodGrp");
                }
 
                objCfgBod = new ZafCfgBod(objParSis,conLoc,objParSis.getCodigoEmpresaGrupo(),intCodBodGrpEgr,intCodBodGrpIng,Jcomponente);
                if(objCfgBod.isConEgr()){
                    strMerIngEgrFisBod="S";
                    objIsGenOD="S";
                }
                else{
                    strMerIngEgrFisBod="N";
                    objIsGenOD=null;
                } 
                objCfgBodGlo = objCfgBod;
 
                blnRes=true;
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc.close();
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false; 
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
         return blnRes;
    }
    
    
    private boolean generaODprestamos(){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        ZafCfgBod objCfgBod;
        boolean blnRes=true;
        String strCadena; 
        int intCodBodGrpIng=0,intCodBodGrpEgr=0,intCodBodIng,intCodBodEgr,intCodEmpEgr=0,intCodEmpIng=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                
                intCodEmpIng=objUti.getIntValueAt(arlConDatPreInvIngAux, 0, INT_ARL_COD_EMP);
                intCodBodIng=objUti.getIntValueAt(arlConDatPreInvIngAux, 0,INT_ARL_COD_BOD_ING_EGR) ;
                
                intCodEmpEgr=objUti.getIntValueAt(arlConDatPreInvEgrAux, 0, INT_ARL_COD_EMP);
                intCodBodEgr=objUti.getIntValueAt(arlConDatPreInvEgrAux, 0,INT_ARL_COD_BOD_ING_EGR) ;
           
                
                strCadena="";
                strCadena+="SELECT co_bodGrp FROM tbr_bodEmpBodGrp WHERE co_emp="+intCodEmpIng+" AND co_bod="+intCodBodIng;
                System.out.println("generaODprestamos: intCodBodGrpIng "+strCadena);
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    intCodBodGrpIng=rstLoc.getInt("co_bodGrp");
                }
                strCadena="";
                strCadena+="SELECT co_bodGrp FROM tbr_bodEmpBodGrp WHERE co_emp="+intCodEmpEgr+" AND co_bod="+intCodBodEgr;
                System.out.println("generaODprestamos: intCodBodGrpEgr "+strCadena);
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    intCodBodGrpEgr=rstLoc.getInt("co_bodGrp");
                }
                objCfgBod = new ZafCfgBod(objParSis,conLoc,objParSis.getCodigoEmpresaGrupo(),intCodBodGrpEgr,intCodBodGrpIng,Jcomponente);
                if(objCfgBod.isConEgr()){
                    strMerIngEgrFisBod="S";
                    objIsGenOD="S";
                }
                else{
                    strMerIngEgrFisBod="N";
                    objIsGenOD=null;
                } 
                objCfgBodGlo = objCfgBod;
 
             
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc.close();
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false; 
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
         return blnRes;
    }
    
     /**
      * Verifica la bodega de egreso (solicitud) vs la bodega de egreso (prestamo/transferencia)
      * Nota: La bodega de la solicitud es la bodega por grupo.
      *       La bodega del prestamo/transferencia es por empresa.
      * @param intCodEmp Empresa de la que egresa la mercaderia
      * @param intCodBodEmp 
      * @param intCodBodGrpSol Código de la bodega de egreso de la solicitud.
      * @return 
      */
    private boolean isBodegaEgreso(int intCodEmp,int intCodBodEmp,int intCodBodGrpSol){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strCadena; 
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strCadena="";
                strCadena+="SELECT co_bod FROM tbr_bodEmpBodGrp WHERE co_emp="+intCodEmp+" AND co_bodGrp="+intCodBodGrpSol;
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                    if(intCodBodEmp==rstLoc.getInt("co_bod")){
                        blnRes=true;
                    }
                }
                // <editor-fold defaultstate="collapsed" desc=" /* José Marín:   */ ">
//                strCadena="";
//                strCadena+="SELECT co_bodGrp FROM tbr_bodEmpBodGrp WHERE co_emp="+intCodEmp+" AND co_bod="+intCodBodEmp;
//                rstLoc=stmLoc.executeQuery(strCadena);
//                if(rstLoc.next()){
//                    intCodBodGrp=rstLoc.getInt("co_bodGrp");
//                }
//                
//                strCadena="";
//                strCadena+=" SELECT * FROM tbm_cfgOrdDes WHERE co_emp="+objParSis.getCodigoEmpresaGrupo()+"\n";
//                strCadena+="  AND co_bodOrg=" +intCodBodGrp+" AND co_bodDes="+intCodBodGrpSol+ "\n";
//                rstLoc=stmLoc.executeQuery(strCadena);
//                if(rstLoc.next()){
//                    strMerIngEgrFisBod="S";
//                    objIsGenOD="S";
//                }
//                else{
//                    strMerIngEgrFisBod="N";
//                    objIsGenOD=null;
//                }
                //</editor-fold>
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc.close();
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
        catch(Exception Evt){ 
          blnRes=false;
          objUti.mostrarMsgErr_F1(Jcomponente, Evt); 
        }
         
        return blnRes;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="/*Jota. Datos */ ">
        //            1;4;228;10100;4;1;5;205;2261;1;4;2;206;2053;23
        //            1;4;228;10140;2;1;5;205;2265;1;4;2;206;2057;9
    
// DESPUES YA NO LO VI NECESARIO
//      private int _getCodigoBodDes(java.sql.Connection conn, int CodEmpCom, int intCodBodCom, int intCodEmpVen){
//     int intCodBodDes=-1;
//     java.sql.Statement stmLoc;
//     java.sql.ResultSet rstLoc;
//      try{
//        if(conn!=null){
//           stmLoc=conn.createStatement();
//           strSql="select  a1.co_bod  from tbr_bodEmpBodGrp as a " +
//           " inner join tbr_bodEmpBodGrp as a1 on (a1.co_empgrp=a.co_empgrp and a1.co_bodgrp=a.co_bodgrp ) " +
//           " where a.co_emp="+CodEmpCom+" and a.co_bod="+intCodBodCom+"   and a1.co_emp="+intCodEmpVen+" ";
//           rstLoc=stmLoc.executeQuery(strSql);
//           if(rstLoc.next()){
//               intCodBodDes=rstLoc.getInt("co_bod");
//           }
//           rstLoc.close();
//           rstLoc=null;
//           stmLoc.close();
//           stmLoc=null;
//        }
//      }
//      catch(java.sql.SQLException Evt){ 
//          intCodBodDes=-1;    
//      }
//      catch(Exception Evt){ 
//          intCodBodDes=-1;   
//      }
//     return intCodBodDes;
//    }
        //</editor-fold>         
    
    
    

private boolean MovDevCompra( java.sql.Connection conn,  int intCodEmpDev, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodItmmae,  double dblCanFal, int intCodBodDevCom, double dlbStkBobDevCom, int intTipDocDevVen, String strCodItm  ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    int intTipDocCom=2;
    int intCodCli=0;
    int intRegSec=0;
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();
//            if(intCodEmp==1){
//                if(intCodEmpDev==4) intCodCli=1039;
//            }
            if(intCodEmp==2){
               if(intCodEmpDev==1) intCodCli=2854;
               if(intCodEmpDev==4) intCodCli=789;
            }
            if(intCodEmp==4){
               if(intCodEmpDev==2) intCodCli=498;
               if(intCodEmpDev==1) intCodCli=3117;
            }
           strSql=" SELECT * FROM ( "
           + " select x.*, CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(x.saldo_a1)  ELSE  x.saldo_a1 END AS saldo   FROM ( " +
           " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  " +
           " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, a1.nd_can, a1.nd_preuni,  "+
           " a1.co_bod, ( abs(a1.nd_can) - abs(case when a1.nd_candev is null then 0 else a1.nd_candev end )) as saldo_a1 "
           + " FROM tbm_cabmovinv  AS a " +
           " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
           " inner join tbm_equinv as a4 on (a4.co_emp= a1.co_emp AND a4.co_itm=a1.co_itm ) "
           + "WHERE a.co_emp="+intCodEmp+
           " AND a.co_tipdoc="+intTipDocCom+" AND a.co_cli="+intCodCli+" " +
           " AND a.fe_doc>='"+strFecDevVen+"' and a.st_reg not in ('E','I') and a4.co_itmmae="+intCodItmmae+"   " +
           " ORDER BY a.fe_Doc, a.co_doc " +
           " ) as x  "
           + " INNER JOIN tbm_inv AS inv ON (inv.co_emp=x.co_emp AND inv.co_itm=x.co_itm ) "
           + " LEFT  JOIN tbm_var AS var ON (var.co_reg=inv.co_uni )  "
           + " ) AS x where saldo <> 0 ";
//            System.out.println(" ZafVen01_06 _getMovDevCompra "+strSql);        
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){
                blnGenDevCom=true;
                if(intDatDevCom==1) stbDatDevCom.append(" UNION ALL ");
                stbDatDevCom.append(" SELECT "+intRegSec+" AS regsec,  "+rstLoc.getInt("co_emp")+" AS co_emp, "+rstLoc.getInt("co_loc")+" AS co_loc, " +
                " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc, "+rstLoc.getInt("co_doc")+" AS co_doc, "+rstLoc.getInt("co_reg")+" AS co_reg," +
                " "+rstLoc.getInt("co_bod")+" AS co_bod, "+rstLoc.getDouble("saldo")+" AS saldo, " +
                " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt2," +
           //     " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed, "+rstLoc.getString("nd_pordes")+" AS nd_pordes, " +
                " "+objUti.codificar(rstLoc.getString("tx_nomitm"))+" AS tx_nomitm, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed, "+rstLoc.getString("nd_pordes")+" AS nd_pordes, " +
                " "+rstLoc.getString("co_itm")+" AS co_itm, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni, "+rstLoc.getString("nd_can")+" AS nd_can,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni  ");
//                  System.out.println("ZaVen01_06 _getMovDevCompra:  " + stbDatDevCom.toString());
                intDatDevCom=1;
           }
           rstLoc.close();
           rstLoc=null;
           stmLoc.close();
           stmLoc=null;

       }
    }
    catch(java.sql.SQLException Evt) { 
        blnRes=false; 
        System.out.println("ERROR: "+ Evt.toString());  
    }
    catch(Exception Evt) { 
        blnRes=false; 
        System.out.println("ERROR: "+ Evt.toString());  
    }
    return blnRes;
}


private boolean MovDevVenta(java.sql.Connection conn, int intCodEmp,  int intCodLoc, int  intCodTipDoc, int intCodItmmae, int intCodEmpRel, double dblCanFal, double dlbStkBobDevCom, int intCodBodDevCom ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    int intTipDocVen=228; // JoséMario Facturación Electronica 6/Oct/2014
    int intCodCli=0;
    int intRegSec=0;
    try{
        if(conn!=null){
            stmLoc=conn.createStatement();
            if(objParSis.getCodigoEmpresa()==1){
                if(intCodEmpRel==2) intCodCli=603;
                if(intCodEmpRel==4) intCodCli=1039;
            }
            if(objParSis.getCodigoEmpresa()==2){
                if(intCodEmpRel==1) intCodCli=2854;
                if(intCodEmpRel==4) intCodCli=789;
            }
            if(objParSis.getCodigoEmpresa()==4){
                if(intCodEmpRel==2) intCodCli=498;
                if(intCodEmpRel==1) intCodCli=3117;
            }
           strSql="SELECT * FROM (  "
           + "select x.*, CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(x.saldo_a1)  ELSE  x.saldo_a1 END AS saldo  FROM ( " +
           " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, " +
           " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, abs(a1.nd_can) as nd_can, a1.nd_preuni,   "+
           " a1.co_bod, ( abs(a1.nd_can) - abs( case when a1.nd_candev is null then 0 else a1.nd_candev end ) ) as saldo_a1 " +
           " FROM tbm_cabmovinv  AS a " +
           " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
           " inner join tbm_equinv as a4 on (a4.co_emp= a1.co_emp and a4.co_itm=a1.co_itm ) " +
           " WHERE a.co_emp="+intCodEmp+
           " AND a.co_tipdoc="+intTipDocVen+" AND a.co_cli="+intCodCli+" " +
           " AND a.fe_doc>='"+strFecDevVen+"' and a.st_reg not in ('E','I') and a4.co_itmmae="+intCodItmmae+"  ORDER BY a.fe_Doc, a.co_doc " +
           " ) as x "
           + " INNER JOIN tbm_inv AS inv ON (inv.co_emp=x.co_emp AND inv.co_itm=x.co_itm ) "
           + " LEFT  JOIN tbm_var AS var ON (var.co_reg=inv.co_uni )  "
           + " ) AS x where saldo <> 0 ";
//           System.out.println(" ZafVen01_06 _getMovDevVenta "+strSql);       
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){
                intRegSec++;
                if(intDatDevVen==1) stbDatDevVen.append(" UNION ALL ");
                stbDatDevVen.append(" SELECT "+intRegSec+" AS regsec1,  "+rstLoc.getInt("co_emp")+" AS co_emp1, "+rstLoc.getInt("co_loc")+" AS co_loc1, " +
                " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc1, "+rstLoc.getInt("co_doc")+" AS co_doc1, "+rstLoc.getInt("co_reg")+" AS co_reg1," +
                " "+rstLoc.getInt("co_bod")+" AS co_bod1, "+rstLoc.getDouble("saldo")+" AS saldo1, " +
                " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc1, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt1, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt21," +
           //     " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm1, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed1, "+rstLoc.getString("nd_pordes")+" AS nd_pordes1, " +
                " "+objUti.codificar(rstLoc.getString("tx_nomitm"))+" AS tx_nomitm1, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed1, "+rstLoc.getString("nd_pordes")+" AS nd_pordes1, " +
                " "+rstLoc.getString("co_itm")+" AS co_itm1, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni1, "+rstLoc.getString("nd_can")+" AS nd_can1,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni1   ");
//                  System.out.println("ZafVen01_06 _getMovDevVenta " + stbDatDevVen.toString());
                intDatDevVen=1;
                blnGenDevVen=true;
           }
           rstLoc.close();
           rstLoc=null;
           stmLoc.close();
           stmLoc=null;
        }
    }
    catch(java.sql.SQLException Evt) {  
         blnRes=false; 
         System.out.println("ERROR: "+ Evt.toString());  
    }
    catch(Exception Evt) { 
         blnRes=false; 
         System.out.println("ERROR: "+ Evt.toString());  
    }
    return blnRes;
}


    private boolean _RealizarDevoluciones(java.sql.Connection con, String strMerIngEgrFisBod, int intCodLocDevCom , int intCodLocDevVen, 
                                          int intCodTipDocDevCom, int intCodTipDocDevVen, int intCodBodDevCom, double dlbStkBobDevCom,  
                                          String strCodItm, String strNomBodSal, boolean blnEstCliRet , int intCodItmMae ){
     boolean blnRes=false;
     java.sql.Statement stmLoc;
     java.sql.ResultSet rstLoc;
     double dlbCanDev=0;
     try{
            if(con!=null){
                stmLoc=con.createStatement();
                strSql="SELECT * FROM ( " +
                " SELECT * FROM ( "+stbDatDevCom.toString()+" ) AS x "+
                " LEFT JOIN ( "+stbDatDevVen.toString()+" ) AS x1 ON(x1.regsec1=x.regsec) " +
                " ) AS x  WHERE  saldo1=saldo ";
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){

                   if(Glo_dblCanFalDevCom > 0 ){
                        if( dlbStkBobDevCom > 0 ){
                            if(rstLoc.getDouble("saldo") >= Glo_dblCanFalDevCom ) {
                                if( dlbStkBobDevCom <= Glo_dblCanFalDevCom ){
                                   dlbCanDev=dlbStkBobDevCom;
                                }
                                else{
                                   dlbCanDev = Glo_dblCanFalDevCom;
                                }

                               Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
                               dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
                            }else{
                                if( dlbStkBobDevCom <= rstLoc.getDouble("saldo") ){
                                    dlbCanDev=dlbStkBobDevCom;
                                }
                                else{
                                    dlbCanDev = rstLoc.getDouble("saldo");
                                }
                               Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
                               dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
                            }
                            if( dlbCanDev > 0 ){
                                stbDevTemp=new StringBuffer();
                                stbDevInvTemp=new StringBuffer();
                                if(generaDevCompra(con,rstLoc.getInt("co_emp"),dlbCanDev , rstLoc.getDouble("nd_pordes"),  rstLoc.getInt("co_itm"),  
                                                   rstLoc.getDouble("nd_cosuni"),objParSis.getCodigoEmpresa())){/*INGRESO*/
                                    if(generaDevVenta(con,rstLoc.getInt("co_emp1"),dlbCanDev  , rstLoc.getDouble("nd_pordes1"),  
                                                      rstLoc.getDouble("nd_preuni1"),rstLoc.getInt("co_itm1"),rstLoc.getInt("co_emp"))){/*EGRESO*/
                                        blnRes=true;
                                        Glo_dblCanFalDevVen = Glo_dblCanFalDevCom;
                                    }
                                }
                                stbDevTemp=null;
                                stbDevInvTemp=null;
                            }
                        }
                   }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            System.out.println("ERROR: "+ Evt.toString());  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            System.out.println("ERROR: "+ Evt.toString());  
        }
     return blnRes;
    }
    
    

    private boolean generaDevCompra(java.sql.Connection con,  int intCodEmp, double dblCan, double dblPorDes, int intCodItm, double dblCosUni, int intCodEmpCom ){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
            try{
                if(con!=null){
                    stmLoc=con.createStatement();
                    double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
                    double dblTotal = (dblCan * dblCosUni)- dblValDes;
                    double dlbSub =  objUti.redondear(dblTotal, objParSis.getDecimalesBaseDatos() );
                    double dlbValIva=  dlbSub * (bldivaEmp/100);
                    double dlbTot=  dlbSub + dlbValIva;
                    Glo_dblTotDevCom += dlbSub;
                    dlbSub=dlbSub*-1;
                    dlbValIva=dlbValIva*-1;
                    dlbTot=dlbTot*-1;
                    dblGenDevCom=dlbTot;
                    strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
                            " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom; 
                    stmLoc.executeUpdate(strSql);
                    stmLoc.close();
                    stmLoc=null;
                }
            }    
            catch(Exception Evt)   { 
                blnRes=false; 
                System.out.println("ERROR: "+ Evt.toString());       
            }
       return blnRes;
    }


    private boolean generaDevVenta(java.sql.Connection con, int intCodEmp, double dblCan, double dblPorDes, double dblCosUni, int intCodItm, int intCodEmpCom){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(con!=null){
                stmLoc=con.createStatement();
                double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
                double dblTotal = (dblCan * dblCosUni)- dblValDes;
                double  dlbSub =  objUti.redondear(dblTotal, objParSis.getDecimalesBaseDatos() );
                double dlbValIva=  dlbSub * (bldivaEmp/100);
                double dlbTot=  dlbSub + dlbValIva;
                dblGenDevVen=dlbTot;
                Glo_dblTotDevVen += dlbSub;
                
                strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven+"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
                       " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom; 
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(Exception Evt)   { 
            blnRes=false; 
            System.out.println("ERROR: "+ Evt.toString());       
        }
     return blnRes;
    }
    
// <editor-fold defaultstate="collapsed" desc=" /* José Marín: Se descarto  */ ">
//
//    
//    
//    private boolean ordenarListas(){
//        boolean blnRes=true;
//        try{
//            int intCodEmpTmp, intCodLocTmp, intCodTipDocTmp, intCodCliTmp, intCodBodIngEgrTmp,intCodItmGrpTmp,intCodItmTmp,intCodItmMaeTmp,intConLinArrTmp;
//            String strCodAltItmTmp, strNomItmTmp,strUniMedTmp,strCodLetTmp,strIvaTmp, strStIngEgrmerFisBodTmp;
//            Double dblCanMovTmp,dblCosUniTmp,dblPreUniTmp,dblPesItmTmp;
//            /* ============== */
//            int intCodEmpAux, intCodLocAux, intCodTipDocAux, intCodCliAux, intCodBodIngEgrAux,intCodItmGrpAux,intCodItmAux,intCodItmMaeAux,intConLinArrAux;
//            String strCodAltItmAux, strNomItmAux,strUniMedAux,strCodLetAux,strIvaAux, strStIngEgrmerFisBodAux;
//            Double dblCanMovAux,dblCosUniAux,dblPreUniAux,dblPesItmAux;
//        //Se va a aplicar el metodo de la burbuja para ordenar el arreglo arlConDatPreEmpEgr0
//             for (int i = 0; i < arlConDatPreEmpEgr.size() - 1; i++){
//                for (int j = i + 1; j < arlConDatPreEmpEgr.size(); j++)
//                {  int intCodEmp_I = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_EMP);
//                   int intCodEmp_J = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_EMP);
//                   if (intCodEmp_J < intCodEmp_I)
//                   {    
//                        //Los campos con indice "i" son salvados en unas variables temporales
//                        intCodEmpTmp = intCodEmp_I;
//                        intCodLocTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LOC);
//                        intCodTipDocTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_TIP_DOC);
//                        intCodCliTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_CLI);
//                        intCodBodIngEgrTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_BOD_ING_EGR);
//                        intCodItmGrpTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_GRP);
//                        intCodItmTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM);
//                        intCodItmMaeTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_MAE);
//                        strCodAltItmTmp = objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_ALT); 
//                        strNomItmTmp = objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_NOM_ITM); 
//                        strUniMedTmp = objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_UNI_MED_ITM); 
//                        strCodLetTmp = objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LET_ITM); 
//                        strIvaTmp = objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IVA_COM_ITM); 
//                        
//                        strStIngEgrmerFisBodTmp = objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                        
//                        dblCanMovTmp = objUti.getDoubleValueAt(arlConDatPreEmpEgr, i, INT_ARL_CAN_MOV);   
//                        dblCosUniTmp = objUti.getDoubleValueAt(arlConDatPreEmpEgr, i, INT_ARL_COS_UNI);   
//                        dblPreUniTmp = objUti.getDoubleValueAt(arlConDatPreEmpEgr, i, INT_ARL_PRE_UNI);  
//                        dblPesItmTmp = objUti.getDoubleValueAt(arlConDatPreEmpEgr, i, INT_ARL_PES_ITM);
//                        intConLinArrTmp = objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_IND_ORG);  // INDICE!!!!
//                    
//                        //Se asigna campos(i) = campos(j)
//                        intCodEmpAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_EMP);  // JOTA: Se modifico el ZafUtil agregando este metodo
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_EMP, intCodEmpAux);
//                        intCodLocAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_LOC);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LOC, intCodLocAux);
//                        intCodTipDocAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_TIP_DOC);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_TIP_DOC, intCodTipDocAux);
//                        intCodCliAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_CLI);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_CLI, intCodCliAux);
//                        intCodBodIngEgrAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_BOD_ING_EGR);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrAux);
//                        intCodItmGrpAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM_GRP);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_GRP, intCodItmGrpAux);
//                        intCodItmAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM, intCodItmAux);
//                        intCodItmMaeAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM_MAE);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_MAE, intCodItmMaeAux);
//                        strCodAltItmAux = objUti.getStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM_ALT);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_ALT, strCodAltItmAux);
//                        strNomItmAux = objUti.getStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_NOM_ITM);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_NOM_ITM, strNomItmAux);
//                        strUniMedAux = objUti.getStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_UNI_MED_ITM);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_UNI_MED_ITM, strUniMedAux);
//                        strCodLetAux = objUti.getStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_LET_ITM);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LET_ITM, strCodLetAux);
//                        strIvaAux = objUti.getStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_IVA_COM_ITM);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IVA_COM_ITM, strIvaAux);
//                        
//                        strStIngEgrmerFisBodAux=objUti.getStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD, strStIngEgrmerFisBodAux);
//                        
//                        
//                        dblCanMovAux = objUti.getDoubleValueAt(arlConDatPreEmpEgr, j, INT_ARL_CAN_MOV);// CAMBIAR  A BIGDECIMAL
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_CAN_MOV, dblCanMovAux);
//                        dblCosUniAux = objUti.getDoubleValueAt(arlConDatPreEmpEgr, j, INT_ARL_COS_UNI);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COS_UNI, dblCosUniAux);
//                        dblPreUniAux = objUti.getDoubleValueAt(arlConDatPreEmpEgr, j, INT_ARL_PRE_UNI);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PRE_UNI, dblPreUniAux);
//                        dblPesItmAux = objUti.getDoubleValueAt(arlConDatPreEmpEgr, j, INT_ARL_PES_ITM);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PES_ITM, dblPesItmAux);
//                        intConLinArrAux = objUti.getIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_IND_ORG);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IND_ORG, intConLinArrAux);
//                       
//                        //Se asigna campos(j) = campos_tmp
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_EMP, intCodEmpTmp);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_LOC, intCodLocTmp);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_TIP_DOC, intCodTipDocTmp);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_CLI, intCodCliTmp);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrTmp);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM_GRP, intCodItmGrpTmp);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM, intCodItmTmp);
//                        objUti.setIntValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM_MAE,  intCodItmMaeTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_ITM_ALT, "" + strCodAltItmTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_NOM_ITM, "" + strNomItmTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_UNI_MED_ITM, "" + strUniMedTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_COD_LET_ITM, "" + strCodLetTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_IVA_COM_ITM, "" + strIvaTmp);
//                        
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD, "" + strStIngEgrmerFisBodTmp);
//                        
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_CAN_MOV, "" + dblCanMovTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_COS_UNI, "" + dblCosUniTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_PRE_UNI, "" + dblPreUniTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_PES_ITM, "" + dblPesItmTmp);
//                        objUti.setStringValueAt(arlConDatPreEmpEgr, j, INT_ARL_IND_ORG, intConLinArrTmp);// INDICE!!!!
//                   }
//                }
//            }
//             
//             // DESPUES DE ORDENAR LOS EGRESOS SEGUN LAS EMPRESAS ES NECESARIO ORDENAR EL INGRESOS SEGUN HAYA QUEDADO EL EGRESO
//             double dblConLinArr_X,dblConLinArr_Y;
//             ArrayList arlConRegPreInvAux;
//             ArrayList arlConDatPreInvAux;
//             arlConDatPreInvAux = new ArrayList();
//             for(int x=0;x<arlConDatPreEmpEgr.size();x++){
//                 for(int y=0;y<arlConDatPreEmpIng.size();y++){
//                    dblConLinArr_X = Double.parseDouble(objUti.getStringValueAt(arlConDatPreEmpEgr, x, INT_ARL_IND_ORG));  
//                    dblConLinArr_Y = Double.parseDouble(objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_IND_ORG));
//                    if (dblConLinArr_X == dblConLinArr_Y){  /* SI EL INDICE ES EL MISMO */
//                        arlConRegPreInvAux = new ArrayList();
//                        arlConRegPreInvAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_EMP) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_LOC) );    
//                        arlConRegPreInvAux.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_TIP_DOC) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_CLI) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_BOD_ING_EGR) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_ITM_GRP) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_ITM) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_ITM_MAE) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_ITM_ALT) );
//                        arlConRegPreInvAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_NOM_ITM) );
//                        arlConRegPreInvAux.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_UNI_MED_ITM) );
//                        arlConRegPreInvAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_COD_LET_ITM) );//11
//                        arlConRegPreInvAux.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_CAN_MOV) );
//                        arlConRegPreInvAux.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_COS_UNI) );
//                        arlConRegPreInvAux.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_PRE_UNI) );
//                        arlConRegPreInvAux.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_PES_ITM) );
//                        arlConRegPreInvAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_IVA_COM_ITM) );
//                        
//                        arlConRegPreInvAux.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
//                        
//                        arlConRegPreInvAux.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreEmpIng, y, INT_ARL_IND_ORG) );
//                        arlConDatPreInvAux.add(arlConRegPreInvAux);
//                    }     
//                }
//            }
//            for(int i=0;i<arlConDatPreEmpEgr.size();i++){
//                if(objUti.getDoubleValueAt(arlConDatPreEmpEgr, i, INT_ARL_IND_ORG)!=objUti.getDoubleValueAt(arlConDatPreInvAux, i, INT_ARL_IND_ORG)){
//                    System.err.println("NO SON LOS INDICES!!! ");
//                    blnRes=false; 
//                }
//            }
//            //System.out.println("CHECK!!! ");
//            arlConDatPreEmpIng=arlConDatPreInvAux;
//        }
//        catch(Exception Evt)   { 
//            blnRes=false; 
//            System.out.println("ERROR: "+ Evt.toString());       
//        }
//     return blnRes;
//        
//    }
    //</editor-fold>
    
    
    private boolean obtenerDatosTranferencia(java.sql.Connection con,ArrayList arlDat){
            boolean blnRes=true;
            java.sql.Statement stmLoc;
            String strErr;
            java.sql.ResultSet rstLoc;
            Double dblCanTra, dblCanNec=0.0;
            try{
                if(con!=null){
                    arlConDatTraInv = new ArrayList();
                    stmLoc=con.createStatement();
                    arlConDat=arlDat;
                    System.out.println("obtenerDatosTransferencias " + arlDat.toString());
                    for(int i=0;i<arlConDat.size();i++){
                        objParSis.setCodigoEmpresa(objUti.getIntValueAt(arlConDat, i, INT_CON_COD_EMP));
                        //intCodBodPre = objUti.getIntValueAt(arlConDat, i, INT_CON_COD_BOD_GRP_ING)/* bodegaPredeterminada(con)*/;
                        intCodBodPre=bodegaPorEmpresaIngreso(objUti.getIntValueAt(arlConDat, i, INT_CON_COD_BOD_GRP_ING));
                        dblCanNec=Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM));  // CANTIDAD QUE BUSCAMOS   
                        /* OJO ENVIAR LAS DOS EN GRUPO!!! */
                        if(isMerNecConf( objUti.getIntValueAt(arlConDat, i, INT_CON_COD_BOD_GRP_ING), objUti.getIntValueAt(arlConDat, i, INT_CON_COD_BOD_GRP))){
                            strMerIngEgrFisBod="S";
                            objIsGenOD="S";
                        }
                        else{
                            strMerIngEgrFisBod="N";
                             objIsGenOD=null;
                        }
                        //////   =============================  TRANSFERENCIAS =============================   ////////////////////
                        if(dblCanNec>0){
                            strSql= " SELECT *   \n";
                            strSql+=" FROM ( \n";
                            strSql+="     SELECT a.co_emp , a.co_bod, a.co_itm,  \n";
                            strSql+="        CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END) ELSE CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END END AS nd_canDis \n";
                            strSql+="     FROM tbm_invbod as a \n" ;
                            strSql+="     INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod)  \n";
                            strSql+="     INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm )   \n";
                            strSql+="     LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni ) \n";
                            strSql+="     WHERE a.co_emp="+objUti.getIntValueAt(arlConDat, i, INT_CON_COD_EMP)+" AND a.co_itm= "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM);
                        //    strSql+="           AND a.co_bod != "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD_GRP_ING)+" ";  /*JM lo comento por que esta poniendo la de grupo y es innecesario*/
                            strSql+="           AND ( a1.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.co_bodGrp= "+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD_GRP)+"   ) \n";
                            strSql+=" ) AS x \n WHERE nd_canDis > 0  ";
                            System.out.println("SOLO TRANSFERENCIA \n" + strSql);
                            rstLoc=stmLoc.executeQuery(strSql); 
                            while(rstLoc.next()){
                                System.out.println("Encuentra.... ");
                                if(obtenerDatosItem(objUti.getIntValueAt(arlConDat, i, INT_CON_COD_EMP),Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)),
                                        Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD)),intCodBodPre)){
                                    if( rstLoc.getDouble("nd_canDis") >= Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM).toString() ) ){
                                        dblCanTra=Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM).toString());
                                        dblCanNec=0.00;  // YA NO SE NECESITA NADA MAS
                                    }else{ // SI EN EL STOCK HAY MENOS SE IRA POR TRANSFERENCIA TODO Y LA DIFERENCIA POR PRESTAMO
                                       dblCanTra=rstLoc.getDouble("nd_canDis");// TODO EN TRANSFERENCIA
                                       dblCanNec=Double.parseDouble(objUti.getStringValueAt(arlConDat, i, INT_CON_CAN_COM).toString()) - dblCanTra; //PARA SABER CUANTO QUEDARIA PENDIENTE DESPUES DE LA TRANSFERENCIA
                                    }
                                    if(obtenerTipoDocumentos(rstLoc,1)){
                                            //INGRSEAR AL CONTENEDOR PARA TRANSFERENCIAS
                                            /* PRIMERO EL EGRESO */
                                            intCodigoBodegaEgresoTrans=Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD));  // BODEGA DE EGRESO
                                            intCodigoBodegaIngresoTrans=intCodBodPre; // BODEGA DE INGRESO
                                            arlConRegTraInv = new ArrayList();
                                            arlConRegTraInv.add(INT_ARL_COD_EMP,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );
                                            intCodigoEmpresa=Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );  // CODIGO DE EMPRESA 
                                            arlConRegTraInv.add(INT_ARL_COD_LOC,intCodLocTra );     // CODIGO DEL LOCAL  
                                            arlConRegTraInv.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenTra); // CODIGO DE TIPO DE DOCUMENTO
                                            arlConRegTraInv.add(INT_ARL_COD_CLI, null); // DEBERIA SER NULL
                                            arlConRegTraInv.add(INT_ARL_COD_BOD_ING_EGR, intCodigoBodegaEgresoTrans);  // BODEGA EGRESO DE LA MERCADERIA
                                            //chrGeneraIva=strEstIva.charAt(0);
                                            chrGeneraIva='N';   /* TRANSFERENCIA NO GENERA IVA */
                                            arlConRegTraInv.add(INT_ARL_COD_ITM_GRP, intCodItmGru);  // CODIGO DEL ITEM DE GRUPO
                                            arlConRegTraInv.add(INT_ARL_COD_ITM,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) );
                                            arlConRegTraInv.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)  );
                                            arlConRegTraInv.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                            arlConRegTraInv.add(INT_ARL_NOM_ITM,strNomItm );
                                            arlConRegTraInv.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                            arlConRegTraInv.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                            arlConRegTraInv.add(INT_ARL_CAN_MOV, (dblCanTra*-1) );  // CANTIDAD POR TRANSFERENCIA
                                            arlConRegTraInv.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                            arlConRegTraInv.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO                           
                                            arlConRegTraInv.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                            arlConRegTraInv.add(INT_ARL_IVA_COM_ITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                                            arlConRegTraInv.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, strMerIngEgrFisBod);  
                                            arlConRegTraInv.add(INT_ARL_IND_ORG,Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD))); 
                                            arlConDatTraInv.add(arlConRegTraInv);

                                            // ==============  INGRESO!!!  26/May/2016 DEBE SER EL INGRESO!!! 
                                            arlConRegTraInv = new ArrayList();
                                            arlConRegTraInv.add(INT_ARL_COD_EMP,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );
                                            intCodigoEmpresa=Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_EMP) );  // CODIGO DE EMPRESA 
                                            arlConRegTraInv.add(INT_ARL_COD_LOC,intCodLocTra );     // CODIGO DEL LOCAL  
                                            arlConRegTraInv.add(INT_ARL_COD_TIP_DOC, intCodTipDocGenTra); // CODIGO DE TIPO DE DOCUMENTO
                                            arlConRegTraInv.add(INT_ARL_COD_CLI, null); // DEBERIA SER NULL
                                            arlConRegTraInv.add(INT_ARL_COD_BOD_ING_EGR, intCodBodPre);  // BODEGA EN DONDE SE INGRESA LA MERCADERIA
                                            arlConRegTraInv.add(INT_ARL_COD_ITM_GRP, intCodItmGru);  // CODIGO DEL ITEM DE GRUPO
                                            arlConRegTraInv.add(INT_ARL_COD_ITM,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM) );
                                            arlConRegTraInv.add(INT_ARL_COD_ITM_MAE,objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM_MAE)  );
                                            arlConRegTraInv.add(INT_ARL_COD_ITM_ALT, strCodAltItm); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                                            arlConRegTraInv.add(INT_ARL_NOM_ITM,strNomItm );
                                            arlConRegTraInv.add(INT_ARL_UNI_MED_ITM,strUniMedItm);
                                            arlConRegTraInv.add(INT_ARL_COD_LET_ITM, strCodAltItm2);  // CODIGO ALTERNO 2 DEL ITEM
                                            arlConRegTraInv.add(INT_ARL_CAN_MOV, (dblCanTra) );  // CANTIDAD POR TRANSFERENCIA
                                            arlConRegTraInv.add(INT_ARL_COS_UNI, dblCosUni);  // COSTO UNITARIO
                                            arlConRegTraInv.add(INT_ARL_PRE_UNI, dblPreUni);  // PRECIO UNITARIO                           
                                            arlConRegTraInv.add(INT_ARL_PES_ITM, dblPesItm);  // PESO DEL ITEM
                                            arlConRegTraInv.add(INT_ARL_IVA_COM_ITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                                            arlConRegTraInv.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, strMerIngEgrFisBod);  
                                            arlConRegTraInv.add(INT_ARL_IND_ORG,Integer.parseInt(objUti.getStringValueAt(arlConDat, i, INT_CON_COD_BOD))); 
                                            arlConDatTraInv.add(arlConRegTraInv);
                                            blnTraInv=true;
                                    }
                                    else{
                                        blnRes=false;
                                    }
                                }
                                else{
                                    strErr="Problemas con el Item:"+objUti.getStringValueAt(arlConDat, i, INT_CON_COD_ITM);
                                    System.out.println(strErr);
                                    blnRes=false;
                                }
                            }
                        }                     
                    } /* FINAL DEL FOR I */
                }
                else{
                    blnRes=false;
                }
            } 
            catch(Exception Evt) { 
                objUti.mostrarMsgErr_F1(null, Evt);  
                System.err.println("ERROR "  + Evt.toString());
                blnRes=false;
            }  
        return blnRes;
        
      }
    
    
    
    
    private int bodegaPorEmpresaIngreso(int intCodBodGrp){
        int intCodBod=0;
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
                strCadena+=" WHERE co_bodGrp="+ intCodBodGrp ;
                strCadena+=" AND co_emp="+objParSis.getCodigoEmpresa() +" AND co_empGrp="+objParSis.getCodigoEmpresaGrupo();
                rstLoc=stmLoc.executeQuery(strCadena);
                if(rstLoc.next()){
                        intCodBod=rstLoc.getInt("co_bod");
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
            intCodBod=0;
        }  
        
        return intCodBod;
    }
    
    
    
    /**
     * Ordenar los prestamos por empresa y por bodega.
     * @return 
     */
    private boolean ordenarListasJota(){
        boolean blnRes=true;
        try{
            arlConDatPreEgrInvTuv=new ArrayList();arlConDatPreEgrInvCas=new ArrayList();arlConDatPreEgrInvDim=new ArrayList();
            arlConDatPreIngInvTuv=new ArrayList();arlConDatPreIngInvCas=new ArrayList();arlConDatPreIngInvDim=new ArrayList();
             for(int i=0;i<arlConDatPreEmpEgr.size();i++){
                 if(objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_EMP)==1){//TUVAL
                    arlConRegPreEgrInvTuv = new ArrayList();
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_EMP) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LOC) );    
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_TIP_DOC) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_CLI) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_BOD_ING_EGR) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_GRP) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_MAE) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_ALT) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_NOM_ITM) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_UNI_MED_ITM) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LET_ITM) );//11
                    arlConRegPreEgrInvTuv.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_CAN_MOV) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COS_UNI) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PRE_UNI) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PES_ITM) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IVA_COM_ITM) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
                    arlConRegPreEgrInvTuv.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IND_ORG) );
                    arlConDatPreEgrInvTuv.add(arlConRegPreEgrInvTuv);
                    arlConRegPreIngInvTuv = new ArrayList();
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_EMP) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_LOC) );    
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_TIP_DOC) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_CLI) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_BOD_ING_EGR) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_GRP) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_MAE) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_ALT) );
                    arlConRegPreIngInvTuv.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_NOM_ITM) );
                    arlConRegPreIngInvTuv.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_UNI_MED_ITM) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_LET_ITM) );//11
                    arlConRegPreIngInvTuv.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_CAN_MOV) );
                    arlConRegPreIngInvTuv.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COS_UNI) );
                    arlConRegPreIngInvTuv.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_PRE_UNI) );
                    arlConRegPreIngInvTuv.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_PES_ITM) );
                    arlConRegPreIngInvTuv.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_IVA_COM_ITM) );
                    arlConRegPreIngInvTuv.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
                    arlConRegPreIngInvTuv.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_IND_ORG) );
                    arlConDatPreIngInvTuv.add(arlConRegPreIngInvTuv);
                    blnPreInvTuv=true;
                 }else if(objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_EMP)==2){
                    arlConRegPreEgrInvCas = new ArrayList();
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_EMP) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LOC) );    
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_TIP_DOC) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_CLI) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_BOD_ING_EGR) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_GRP) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_MAE) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_ALT) );
                    arlConRegPreEgrInvCas.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_NOM_ITM) );
                    arlConRegPreEgrInvCas.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_UNI_MED_ITM) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LET_ITM) );//11
                    arlConRegPreEgrInvCas.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_CAN_MOV) );
                    arlConRegPreEgrInvCas.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COS_UNI) );
                    arlConRegPreEgrInvCas.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PRE_UNI) );
                    arlConRegPreEgrInvCas.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PES_ITM) );
                    arlConRegPreEgrInvCas.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IVA_COM_ITM) );
                    arlConRegPreEgrInvCas.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
                    arlConRegPreEgrInvCas.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IND_ORG) );
                    arlConDatPreEgrInvCas.add(arlConRegPreEgrInvCas);
                    arlConRegPreIngInvCas = new ArrayList();
                    arlConRegPreIngInvCas.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_EMP) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_LOC) );    
                    arlConRegPreIngInvCas.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_TIP_DOC) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_CLI) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_BOD_ING_EGR) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_GRP) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_MAE) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_ALT) );
                    arlConRegPreIngInvCas.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_NOM_ITM) );
                    arlConRegPreIngInvCas.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_UNI_MED_ITM) );
                    arlConRegPreIngInvCas.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_LET_ITM) );//11
                    arlConRegPreIngInvCas.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_CAN_MOV) );
                    arlConRegPreIngInvCas.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COS_UNI) );
                    arlConRegPreIngInvCas.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_PRE_UNI) );
                    arlConRegPreIngInvCas.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_PES_ITM) );
                    arlConRegPreIngInvCas.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_IVA_COM_ITM) );
                    arlConRegPreIngInvCas.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
                    arlConRegPreIngInvCas.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_IND_ORG) );
                    arlConDatPreIngInvCas.add(arlConRegPreIngInvCas);
                    blnPreInvCas=true;
                 }else{
                    arlConRegPreEgrInvDim = new ArrayList();
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_EMP) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LOC) );    
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_TIP_DOC) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_CLI) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_BOD_ING_EGR) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_GRP) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_MAE) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_ITM_ALT) );
                    arlConRegPreEgrInvDim.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_NOM_ITM) );
                    arlConRegPreEgrInvDim.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_UNI_MED_ITM) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COD_LET_ITM) );//11
                    arlConRegPreEgrInvDim.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_CAN_MOV) );
                    arlConRegPreEgrInvDim.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_COS_UNI) );
                    arlConRegPreEgrInvDim.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PRE_UNI) );
                    arlConRegPreEgrInvDim.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_PES_ITM) );
                    arlConRegPreEgrInvDim.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IVA_COM_ITM) );
                    arlConRegPreEgrInvDim.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
                    arlConRegPreEgrInvDim.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreEmpEgr, i, INT_ARL_IND_ORG) );
                    arlConDatPreEgrInvDim.add(arlConRegPreEgrInvDim);
                    arlConRegPreIngInvDim = new ArrayList();
                    arlConRegPreIngInvDim.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_EMP) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_LOC) );    
                    arlConRegPreIngInvDim.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_TIP_DOC) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_CLI) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_BOD_ING_EGR) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_GRP) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_MAE) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_ITM_ALT) );
                    arlConRegPreIngInvDim.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_NOM_ITM) );
                    arlConRegPreIngInvDim.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_UNI_MED_ITM) );
                    arlConRegPreIngInvDim.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COD_LET_ITM) );//11
                    arlConRegPreIngInvDim.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_CAN_MOV) );
                    arlConRegPreIngInvDim.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_COS_UNI) );
                    arlConRegPreIngInvDim.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_PRE_UNI) );
                    arlConRegPreIngInvDim.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_PES_ITM) );
                    arlConRegPreIngInvDim.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_IVA_COM_ITM) );
                    arlConRegPreIngInvDim.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
                    arlConRegPreIngInvDim.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreEmpIng, i, INT_ARL_IND_ORG) );
                    arlConDatPreIngInvDim.add(arlConRegPreIngInvDim);
                    blnPreInvDim=true;
                 }
            }
        }
        catch(Exception Evt)   { 
            blnRes=false; 
            System.out.println("ERROR: "+ Evt.toString());       
        }
     return blnRes;
        
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc=" /* José Marín: Ya no se usa */ ">
//    
//    private boolean ordenarListasTuvCasDim(){
//        boolean blnRes=true;
//        try{
//            int intCodEmpTmp, intCodLocTmp, intCodTipDocTmp, intCodCliTmp, intCodBodIngEgrTmp,intCodItmGrpTmp,intCodItmTmp,intCodItmMaeTmp,intConLinArrTmp;
//            String strCodAltItmTmp, strNomItmTmp,strUniMedTmp,strCodLetTmp,strIvaTmp, strStIngEgrmerFisBodTmp;
//            Double dblCanMovTmp,dblCosUniTmp,dblPreUniTmp,dblPesItmTmp;
//            /* ============== */
//            int intCodEmpAux, intCodLocAux, intCodTipDocAux, intCodCliAux, intCodBodIngEgrAux,intCodItmGrpAux,intCodItmAux,intCodItmMaeAux,intConLinArrAux;
//            String strCodAltItmAux, strNomItmAux,strUniMedAux,strCodLetAux,strIvaAux, strStIngEgrmerFisBodAux;
//            Double dblCanMovAux,dblCosUniAux,dblPreUniAux,dblPesItmAux;
//            /*======================*/
//            double dblConLinArr_X,dblConLinArr_Y;
//            ArrayList arlConRegPreInvAux;
//            ArrayList arlConDatPreInvAux;
//            if(blnPreInvTuv){
//                for (int i = 0; i < arlConDatPreEgrInvTuv.size() - 1; i++){
//                    for (int j = i + 1; j < arlConDatPreEgrInvTuv.size(); j++)
//                    {  int intCodEmp_I = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_BOD_ING_EGR);
//                       int intCodEmp_J = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_BOD_ING_EGR);
//                       if (intCodEmp_J < intCodEmp_I)
//                       {    
//                            //Los campos con indice "i" son salvados en unas variables temporales
//                            intCodEmpTmp = intCodEmp_I;
//                            intCodLocTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_LOC);
//                            intCodTipDocTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_TIP_DOC);
//                            intCodCliTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_CLI);
//                            intCodBodIngEgrTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_BOD_ING_EGR);
//                            intCodItmGrpTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM_GRP);
//                            intCodItmTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM);
//                            intCodItmMaeTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM_MAE);
//                            strCodAltItmTmp = objUti.getStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM_ALT); 
//                            strNomItmTmp = objUti.getStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_NOM_ITM); 
//                            strUniMedTmp = objUti.getStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_UNI_MED_ITM); 
//                            strCodLetTmp = objUti.getStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_LET_ITM); 
//                            strIvaTmp = objUti.getStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_IVA_COM_ITM); 
//                            strStIngEgrmerFisBodTmp = objUti.getStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                            dblCanMovTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_CAN_MOV);   
//                            dblCosUniTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COS_UNI);   
//                            dblPreUniTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_PRE_UNI);  
//                            dblPesItmTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_PES_ITM);
//                            intConLinArrTmp = objUti.getIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_IND_ORG);  // INDICE!!!!
//
//                            //Se asigna campos(i) = campos(j)
//                            intCodEmpAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_EMP);  // JOTA: Se modifico el ZafUtil agregando este metodo
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_EMP, intCodEmpAux);
//                            intCodLocAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_LOC);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_LOC, intCodLocAux);
//                            intCodTipDocAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_TIP_DOC);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_TIP_DOC, intCodTipDocAux);
//                            intCodCliAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_CLI);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_CLI, intCodCliAux);
//                            intCodBodIngEgrAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_BOD_ING_EGR);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrAux);
//                            intCodItmGrpAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM_GRP);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM_GRP, intCodItmGrpAux);
//                            intCodItmAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM, intCodItmAux);
//                            intCodItmMaeAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM_MAE);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM_MAE, intCodItmMaeAux);
//                            strCodAltItmAux = objUti.getStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM_ALT);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_ITM_ALT, strCodAltItmAux);
//                            strNomItmAux = objUti.getStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_NOM_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_NOM_ITM, strNomItmAux);
//                            strUniMedAux = objUti.getStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_UNI_MED_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_UNI_MED_ITM, strUniMedAux);
//                            strCodLetAux = objUti.getStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_LET_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COD_LET_ITM, strCodLetAux);
//                            strIvaAux = objUti.getStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_IVA_COM_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_IVA_COM_ITM, strIvaAux);
//                            strStIngEgrmerFisBodAux=objUti.getStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD, strStIngEgrmerFisBodAux);
//                            dblCanMovAux = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_CAN_MOV);// CAMBIAR  A BIGDECIMAL
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_CAN_MOV, dblCanMovAux);
//                            dblCosUniAux = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COS_UNI);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_COS_UNI, dblCosUniAux);
//                            dblPreUniAux = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_PRE_UNI);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_PRE_UNI, dblPreUniAux);
//                            dblPesItmAux = objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_PES_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_PES_ITM, dblPesItmAux);
//                            intConLinArrAux = objUti.getIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_IND_ORG);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_IND_ORG, intConLinArrAux);
//
//                            //Se asigna campos(j) = campos_tmp
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_EMP, intCodEmpTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_LOC, intCodLocTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_TIP_DOC, intCodTipDocTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_CLI, intCodCliTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM_GRP, intCodItmGrpTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM, intCodItmTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM_MAE,  intCodItmMaeTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_ITM_ALT, "" + strCodAltItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_NOM_ITM, "" + strNomItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_UNI_MED_ITM, "" + strUniMedTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COD_LET_ITM, "" + strCodLetTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_IVA_COM_ITM, "" + strIvaTmp);
//
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD, "" + strStIngEgrmerFisBodTmp);
//
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_CAN_MOV, "" + dblCanMovTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_COS_UNI, "" + dblCosUniTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_PRE_UNI, "" + dblPreUniTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_PES_ITM, "" + dblPesItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvTuv, j, INT_ARL_IND_ORG, intConLinArrTmp);// INDICE!!!!
//                       }
//                    }
//                }
//
//                 // DESPUES DE ORDENAR LOS EGRESOS SEGUN LAS EMPRESAS ES NECESARIO ORDENAR EL INGRESOS SEGUN HAYA QUEDADO EL EGRESO
//                 
//                 arlConDatPreInvAux = new ArrayList();
//                 for(int x=0;x<arlConDatPreEgrInvTuv.size();x++){
//                     for(int y=0;y<arlConDatPreIngInvTuv.size();y++){
//                        dblConLinArr_X = Double.parseDouble(objUti.getStringValueAt(arlConDatPreEgrInvTuv, x, INT_ARL_IND_ORG));  
//                        dblConLinArr_Y = Double.parseDouble(objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_IND_ORG));
//                        if (dblConLinArr_X == dblConLinArr_Y){  /* SI EL INDICE ES EL MISMO */
//                            arlConRegPreInvAux = new ArrayList();
//                            arlConRegPreInvAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_EMP) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_LOC) );    
//                            arlConRegPreInvAux.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_TIP_DOC) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_CLI) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_BOD_ING_EGR) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_ITM_GRP) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_ITM_MAE) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_ITM_ALT) );
//                            arlConRegPreInvAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_NOM_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_UNI_MED_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COD_LET_ITM) );//11
//                            arlConRegPreInvAux.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_CAN_MOV) );
//                            arlConRegPreInvAux.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_COS_UNI) );
//                            arlConRegPreInvAux.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_PRE_UNI) );
//                            arlConRegPreInvAux.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_PES_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_IVA_COM_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
//                            arlConRegPreInvAux.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreIngInvTuv, y, INT_ARL_IND_ORG) );
//                            arlConDatPreInvAux.add(arlConRegPreInvAux);
//                        }     
//                    }
//                }
//                for(int i=0;i<arlConDatPreEgrInvTuv.size();i++){
//                    if(objUti.getDoubleValueAt(arlConDatPreEgrInvTuv, i, INT_ARL_IND_ORG)!=objUti.getDoubleValueAt(arlConDatPreInvAux, i, INT_ARL_IND_ORG)){
//                        System.err.println("NO SON LOS INDICES!!! ");
//                        blnRes=false; 
//                    }
//                }
//                arlConDatPreIngInvTuv=arlConDatPreInvAux;
//            }
//            
//            /* ======================================== CASTEK ==================================================*/
//            
//            if(blnPreInvCas){
//                for (int i = 0; i < arlConDatPreEgrInvCas.size() - 1; i++){
//                    for (int j = i + 1; j < arlConDatPreEgrInvCas.size(); j++)
//                    {  int intCodEmp_I = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_BOD_ING_EGR);
//                       int intCodEmp_J = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_BOD_ING_EGR);
//                       if (intCodEmp_J < intCodEmp_I)
//                       {    
//                            //Los campos con indice "i" son salvados en unas variables temporales
//                            intCodEmpTmp = intCodEmp_I;
//                            intCodLocTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_LOC);
//                            intCodTipDocTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_TIP_DOC);
//                            intCodCliTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_CLI);
//                            intCodBodIngEgrTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_BOD_ING_EGR);
//                            intCodItmGrpTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM_GRP);
//                            intCodItmTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM);
//                            intCodItmMaeTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM_MAE);
//                            strCodAltItmTmp = objUti.getStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM_ALT); 
//                            strNomItmTmp = objUti.getStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_NOM_ITM); 
//                            strUniMedTmp = objUti.getStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_UNI_MED_ITM); 
//                            strCodLetTmp = objUti.getStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_LET_ITM); 
//                            strIvaTmp = objUti.getStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_IVA_COM_ITM); 
//                            strStIngEgrmerFisBodTmp = objUti.getStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                            dblCanMovTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, i, INT_ARL_CAN_MOV);   
//                            dblCosUniTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COS_UNI);   
//                            dblPreUniTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, i, INT_ARL_PRE_UNI);  
//                            dblPesItmTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, i, INT_ARL_PES_ITM);
//                            intConLinArrTmp = objUti.getIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_IND_ORG);  // INDICE!!!!
//
//                            //Se asigna campos(i) = campos(j)
//                            intCodEmpAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_EMP);  // JOTA: Se modifico el ZafUtil agregando este metodo
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_EMP, intCodEmpAux);
//                            intCodLocAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_LOC);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_LOC, intCodLocAux);
//                            intCodTipDocAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_TIP_DOC);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_TIP_DOC, intCodTipDocAux);
//                            intCodCliAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_CLI);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_CLI, intCodCliAux);
//                            intCodBodIngEgrAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_BOD_ING_EGR);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrAux);
//                            intCodItmGrpAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM_GRP);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM_GRP, intCodItmGrpAux);
//                            intCodItmAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM, intCodItmAux);
//                            intCodItmMaeAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM_MAE);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM_MAE, intCodItmMaeAux);
//                            strCodAltItmAux = objUti.getStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM_ALT);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_ITM_ALT, strCodAltItmAux);
//                            strNomItmAux = objUti.getStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_NOM_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_NOM_ITM, strNomItmAux);
//                            strUniMedAux = objUti.getStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_UNI_MED_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_UNI_MED_ITM, strUniMedAux);
//                            strCodLetAux = objUti.getStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_LET_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COD_LET_ITM, strCodLetAux);
//                            strIvaAux = objUti.getStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_IVA_COM_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_IVA_COM_ITM, strIvaAux);
//                            strStIngEgrmerFisBodAux=objUti.getStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD, strStIngEgrmerFisBodAux);
//                            dblCanMovAux = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, j, INT_ARL_CAN_MOV);// CAMBIAR  A BIGDECIMAL
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_CAN_MOV, dblCanMovAux);
//                            dblCosUniAux = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COS_UNI);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_COS_UNI, dblCosUniAux);
//                            dblPreUniAux = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, j, INT_ARL_PRE_UNI);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_PRE_UNI, dblPreUniAux);
//                            dblPesItmAux = objUti.getDoubleValueAt(arlConDatPreEgrInvCas, j, INT_ARL_PES_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_PES_ITM, dblPesItmAux);
//                            intConLinArrAux = objUti.getIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_IND_ORG);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, i, INT_ARL_IND_ORG, intConLinArrAux);
//
//                            //Se asigna campos(j) = campos_tmp
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_EMP, intCodEmpTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_LOC, intCodLocTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_TIP_DOC, intCodTipDocTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_CLI, intCodCliTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM_GRP, intCodItmGrpTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM, intCodItmTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM_MAE,  intCodItmMaeTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_ITM_ALT, "" + strCodAltItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_NOM_ITM, "" + strNomItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_UNI_MED_ITM, "" + strUniMedTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COD_LET_ITM, "" + strCodLetTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_IVA_COM_ITM, "" + strIvaTmp);
//
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD, "" + strStIngEgrmerFisBodTmp);
//
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_CAN_MOV, "" + dblCanMovTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_COS_UNI, "" + dblCosUniTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_PRE_UNI, "" + dblPreUniTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_PES_ITM, "" + dblPesItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvCas, j, INT_ARL_IND_ORG, intConLinArrTmp);// INDICE!!!!
//                       }
//                    }
//                }
//
//
//                 arlConDatPreInvAux = new ArrayList();
//                 for(int x=0;x<arlConDatPreEgrInvCas.size();x++){
//                     for(int y=0;y<arlConDatPreIngInvCas.size();y++){
//                        dblConLinArr_X = Double.parseDouble(objUti.getStringValueAt(arlConDatPreEgrInvCas, x, INT_ARL_IND_ORG));  
//                        dblConLinArr_Y = Double.parseDouble(objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_IND_ORG));
//                        if (dblConLinArr_X == dblConLinArr_Y){  /* SI EL INDICE ES EL MISMO */
//                            arlConRegPreInvAux = new ArrayList();
//                            arlConRegPreInvAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_EMP) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_LOC) );    
//                            arlConRegPreInvAux.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_TIP_DOC) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_CLI) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_BOD_ING_EGR) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_ITM_GRP) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_ITM_MAE) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_ITM_ALT) );
//                            arlConRegPreInvAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_NOM_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_UNI_MED_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_COD_LET_ITM) );//11
//                            arlConRegPreInvAux.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_CAN_MOV) );
//                            arlConRegPreInvAux.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_COS_UNI) );
//                            arlConRegPreInvAux.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_PRE_UNI) );
//                            arlConRegPreInvAux.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_PES_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_IVA_COM_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
//                            arlConRegPreInvAux.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreIngInvCas, y, INT_ARL_IND_ORG) );
//                            arlConDatPreInvAux.add(arlConRegPreInvAux);
//                        }     
//                    }
//                }
//                for(int i=0;i<arlConDatPreEgrInvCas.size();i++){
//                    if(objUti.getDoubleValueAt(arlConDatPreEgrInvCas, i, INT_ARL_IND_ORG)!=objUti.getDoubleValueAt(arlConDatPreInvAux, i, INT_ARL_IND_ORG)){
//                        System.err.println("NO SON LOS INDICES!!! ");
//                        blnRes=false; 
//                    }
//                }
//                arlConDatPreIngInvCas=arlConDatPreInvAux;
//            }
//            
//            
//             /* ======================================== DIMULTI ==================================================*/
//            if(blnPreInvDim){
//                for (int i = 0; i < arlConDatPreEgrInvDim.size() - 1; i++){
//                    for (int j = i + 1; j < arlConDatPreEgrInvDim.size(); j++)
//                    {  int intCodEmp_I = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_BOD_ING_EGR);
//                       int intCodEmp_J = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_BOD_ING_EGR);
//                       if (intCodEmp_J < intCodEmp_I)
//                       {    
//                            //Los campos con indice "i" son salvados en unas variables temporales
//                            intCodEmpTmp = intCodEmp_I;
//                            intCodLocTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_LOC);
//                            intCodTipDocTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_TIP_DOC);
//                            intCodCliTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_CLI);
//                            intCodBodIngEgrTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_BOD_ING_EGR);
//                            intCodItmGrpTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM_GRP);
//                            intCodItmTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM);
//                            intCodItmMaeTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM_MAE);
//                            strCodAltItmTmp = objUti.getStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM_ALT); 
//                            strNomItmTmp = objUti.getStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_NOM_ITM); 
//                            strUniMedTmp = objUti.getStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_UNI_MED_ITM); 
//                            strCodLetTmp = objUti.getStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_LET_ITM); 
//                            strIvaTmp = objUti.getStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_IVA_COM_ITM); 
//                            strStIngEgrmerFisBodTmp = objUti.getStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                            dblCanMovTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, i, INT_ARL_CAN_MOV);   
//                            dblCosUniTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COS_UNI);   
//                            dblPreUniTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, i, INT_ARL_PRE_UNI);  
//                            dblPesItmTmp = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, i, INT_ARL_PES_ITM);
//                            intConLinArrTmp = objUti.getIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_IND_ORG);  // INDICE!!!!
//
//                            //Se asigna campos(i) = campos(j)
//                            intCodEmpAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_EMP);  // JOTA: Se modifico el ZafUtil agregando este metodo
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_EMP, intCodEmpAux);
//                            intCodLocAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_LOC);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_LOC, intCodLocAux);
//                            intCodTipDocAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_TIP_DOC);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_TIP_DOC, intCodTipDocAux);
//                            intCodCliAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_CLI);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_CLI, intCodCliAux);
//                            intCodBodIngEgrAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_BOD_ING_EGR);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrAux);
//                            intCodItmGrpAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM_GRP);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM_GRP, intCodItmGrpAux);
//                            intCodItmAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM, intCodItmAux);
//                            intCodItmMaeAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM_MAE);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM_MAE, intCodItmMaeAux);
//                            strCodAltItmAux = objUti.getStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM_ALT);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_ITM_ALT, strCodAltItmAux);
//                            strNomItmAux = objUti.getStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_NOM_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_NOM_ITM, strNomItmAux);
//                            strUniMedAux = objUti.getStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_UNI_MED_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_UNI_MED_ITM, strUniMedAux);
//                            strCodLetAux = objUti.getStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_LET_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COD_LET_ITM, strCodLetAux);
//                            strIvaAux = objUti.getStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_IVA_COM_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_IVA_COM_ITM, strIvaAux);
//                            strStIngEgrmerFisBodAux=objUti.getStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_EST_MER_ING_EGR_FIS_BOD, strStIngEgrmerFisBodAux);
//                            dblCanMovAux = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, j, INT_ARL_CAN_MOV);// CAMBIAR  A BIGDECIMAL
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_CAN_MOV, dblCanMovAux);
//                            dblCosUniAux = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COS_UNI);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_COS_UNI, dblCosUniAux);
//                            dblPreUniAux = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, j, INT_ARL_PRE_UNI);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_PRE_UNI, dblPreUniAux);
//                            dblPesItmAux = objUti.getDoubleValueAt(arlConDatPreEgrInvDim, j, INT_ARL_PES_ITM);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_PES_ITM, dblPesItmAux);
//                            intConLinArrAux = objUti.getIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_IND_ORG);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, i, INT_ARL_IND_ORG, intConLinArrAux);
//
//                            //Se asigna campos(j) = campos_tmp
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_EMP, intCodEmpTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_LOC, intCodLocTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_TIP_DOC, intCodTipDocTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_CLI, intCodCliTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_BOD_ING_EGR, intCodBodIngEgrTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM_GRP, intCodItmGrpTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM, intCodItmTmp);
//                            objUti.setIntValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM_MAE,  intCodItmMaeTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_ITM_ALT, "" + strCodAltItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_NOM_ITM, "" + strNomItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_UNI_MED_ITM, "" + strUniMedTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COD_LET_ITM, "" + strCodLetTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_IVA_COM_ITM, "" + strIvaTmp);
//
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_EST_MER_ING_EGR_FIS_BOD, "" + strStIngEgrmerFisBodTmp);
//
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_CAN_MOV, "" + dblCanMovTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_COS_UNI, "" + dblCosUniTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_PRE_UNI, "" + dblPreUniTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_PES_ITM, "" + dblPesItmTmp);
//                            objUti.setStringValueAt(arlConDatPreEgrInvDim, j, INT_ARL_IND_ORG, intConLinArrTmp);// INDICE!!!!
//                       }
//                    }
//                }
//
//
//                 arlConDatPreInvAux = new ArrayList();
//                 for(int x=0;x<arlConDatPreEgrInvDim.size();x++){
//                     for(int y=0;y<arlConDatPreIngInvDim.size();y++){
//                        dblConLinArr_X = Double.parseDouble(objUti.getStringValueAt(arlConDatPreEgrInvDim, x, INT_ARL_IND_ORG));  
//                        dblConLinArr_Y = Double.parseDouble(objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_IND_ORG));
//                        if (dblConLinArr_X == dblConLinArr_Y){  /* SI EL INDICE ES EL MISMO */
//                            arlConRegPreInvAux = new ArrayList();
//                            arlConRegPreInvAux.add(INT_ARL_COD_EMP, objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_EMP) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_LOC,objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_LOC) );    
//                            arlConRegPreInvAux.add(INT_ARL_COD_TIP_DOC,objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_TIP_DOC) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_CLI,objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_CLI) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_BOD_ING_EGR,objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_BOD_ING_EGR) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_GRP,objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_ITM_GRP) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM, objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_MAE, objUti.getIntValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_ITM_MAE) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_ITM_ALT, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_ITM_ALT) );
//                            arlConRegPreInvAux.add(INT_ARL_NOM_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_NOM_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_UNI_MED_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_UNI_MED_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_COD_LET_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_COD_LET_ITM) );//11
//                            arlConRegPreInvAux.add(INT_ARL_CAN_MOV, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_CAN_MOV) );
//                            arlConRegPreInvAux.add(INT_ARL_COS_UNI, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_COS_UNI) );
//                            arlConRegPreInvAux.add(INT_ARL_PRE_UNI, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_PRE_UNI) );
//                            arlConRegPreInvAux.add(INT_ARL_PES_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_PES_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_IVA_COM_ITM, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_IVA_COM_ITM) );
//                            arlConRegPreInvAux.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_EST_MER_ING_EGR_FIS_BOD) );
//                            arlConRegPreInvAux.add(INT_ARL_IND_ORG, objUti.getStringValueAt(arlConDatPreIngInvDim, y, INT_ARL_IND_ORG) );
//                            arlConDatPreInvAux.add(arlConRegPreInvAux);
//                        }     
//                    }
//                }
//                for(int i=0;i<arlConDatPreEgrInvDim.size();i++){
//                    if(objUti.getDoubleValueAt(arlConDatPreEgrInvDim, i, INT_ARL_IND_ORG)!=objUti.getDoubleValueAt(arlConDatPreInvAux, i, INT_ARL_IND_ORG)){
//                        System.err.println("NO SON LOS INDICES!!! ");
//                        blnRes=false; 
//                    }
//                }
//                arlConDatPreIngInvDim=arlConDatPreInvAux;
//            }
//            
//            
//            
//        }
//        catch(Exception Evt)   { 
//            blnRes=false; 
//            System.out.println("ERROR: "+ Evt.toString());       
//        }
//     return blnRes;
//        
//    }
    //</editor-fold>
    
    
    /**
     * Le llegan las bodegas de origen y destino BODEGAS DE GRUPO y obtiene la cofiguracion 
     * de si debe o no generar OD
     * 
     * @param intCodBodOrg
     * @param intCodBodDes
     * @return 
     */
    
    private boolean isMerNecConf(int intCodBodOrg,int intCodBodDes){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        ZafCfgBod objCfgBod;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                objCfgBod = new ZafCfgBod(objParSis,conLoc,objParSis.getCodigoEmpresaGrupo(),intCodBodOrg,intCodBodDes,Jcomponente);
                if(objCfgBod.isConEgr()){
    //                        strMerIngEgrFisBod="S";
    //                        objIsGenOD="S";
                     blnRes=true;
                }
                else{
    //                        strMerIngEgrFisBod="N";
    //                        objIsGenOD=null;
                } 
                objCfgBod=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(Exception Evt)   { 
            blnRes=false; 
            System.out.println("ERROR: "+ Evt.toString());       
        }
        return blnRes;
    }
    
    
    /* STOCK!!! */
    
    
    /***
     * Datos a Enviar al metodo ZafStkInv
     * indiceCampo: Parámetro que permitirá conocer cual es el nombre del campo que se debe actualizar
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
     * signoCampo:Parámetro que permite conocer el signo que debe aplicarse al campo (+ ó -)
     * intTipMov: Indica si el método debe ser o no ejecutado
     *                  <HTML>    
     *                      <BR> 0= El método no se debe ejecutar
     *                      <BR> 1= El método si se debe ejecutar
     *                  </HTML>
     * @param con
     * @return 
     */
    
    
    /***
     * TRANSFERENCIAS DE INVENTARIO: 
     * Para la tabla de tbm_invBod campo a trabajar:
     *      EGRESOS: nd_stkAct(0), nd_canDis(10) 
     *      INGRESOS:nd_stkAct(0)                                                                
     * 
     * @param con
     * @return 
     */
 
    private boolean generaMovimientoInventarioTransferencia(java.sql.Connection con){
        boolean blnRes=false;
        try{
            if(objIsGenOD==null){
                if(blnIsSolRes){
                    if(generaNuevoContenedorItemsTransferenciaIngreso()){
                        System.out.println("GeneraMovimientoInventarioTransferencia......INGRESO.... " + arlDatStkInvItm);
                        if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_RES, "+", 1, arlDatStkInvItm)){
                                if(generaNuevoContenedorItemsTransferenciaEgreso()){
                                    System.out.println("GeneraMovimientoInventarioTransferencia....EGRESO...... " + arlDatStkInvItm);
                                    if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "-", 0, arlDatStkInvItm)){
                                        if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_DIS, "-",1, arlDatStkInvItm)){
                                            System.out.println("Mover Inventario ZafStkInv....  ");
                                            blnRes=true;
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");blnRes=false;}
                }
                else{ /* JM: si no tiene OD, y tiene una transferencia es imporataciones o compras locales donde la mercaderia no se confirma */
                    if(generaNuevoContenedorItemsTransferenciaIngreso()){
                        System.out.println("GeneraMovimientoInventarioTransferencia......INGRESO.... " + arlDatStkInvItm);
                        if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_DIS, "+", 1, arlDatStkInvItm)){
                                if(generaNuevoContenedorItemsTransferenciaEgreso()){
                                    System.out.println("GeneraMovimientoInventarioTransferencia....EGRESO...... " + arlDatStkInvItm);
                                    if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "-", 0, arlDatStkInvItm)){
                                        if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_DIS, "-",1, arlDatStkInvItm)){
                                            System.out.println("Mover Inventario ZafStkInv....  ");
                                            blnRes=true;
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");blnRes=false;}
                }
                
            }
            else{
                if(generaNuevoContenedorItemsTransferenciaIngreso()){
                    System.out.println("GeneraMovimientoInventarioTransferencia......INGRESO.... " + arlDatStkInvItm);
                    if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_ING_BOD, "+", 1, arlDatStkInvItm)){
                            if(generaNuevoContenedorItemsTransferenciaEgreso()){
                                System.out.println("GeneraMovimientoInventarioTransferencia....EGRESO...... " + arlDatStkInvItm);
                                if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "-", 0, arlDatStkInvItm)){
                                    if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_DIS, "-",0, arlDatStkInvItm)){
                                        if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_EGR_BOD, "-",1, arlDatStkInvItm)){
                                            System.out.println("Mover Inventario ZafStkInv....  ");
                                            blnRes=true;
                                        }else{blnRes=false;}
                                    }else{blnRes=false;}
                                }else{blnRes=false;}
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
            }
            
            // <editor-fold defaultstate="collapsed" desc=" /* José Marín: HASTE EL 11/Agosto/2016 */ ">
//            if(generaNuevoContenedorItemsTransferenciaIngreso()){
//                System.out.println("GeneraMovimientoInventarioTransferencia......INGRESO.... " + arlDatStkInvItm);
//                if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
//                    if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_ING_BOD, "+", 0, arlDatStkInvItm)){
//                        if(generaNuevoContenedorItemsTransferenciaEgreso()){
//                            System.out.println("GeneraMovimientoInventarioTransferencia....EGRESO...... " + arlDatStkInvItm);
//                            if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_STK_ACT, "-", 1, arlDatStkInvItm)){
//                                if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_DIS, "-",0, arlDatStkInvItm)){
//                                    if(objStkInv.actualizaInventario(con, intCodigoEmpresa,INT_ARL_STK_INV_CAN_EGR_BOD, "-",0, arlDatStkInvItm)){
//                                        System.out.println("Mover Inventario ZafStkInv....  ");
//                                        blnRes=true;
//                                    }else{blnRes=false;}
//                                }else{blnRes=false;}
//                            }else{System.out.println("FALLA EN ZafStkInv....  ");blnRes=false;}
//                        }else{System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");blnRes=false;}
//                    }else{System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");blnRes=false;}
//                }else{System.out.println("FALLA EN ZafStkInv....  ");blnRes=false;}
//            }else{System.out.println("FALLA EN GENERAR NUEVO CONTENEDOR DE ITEMS....  ");blnRes=false;}
//
//</editor-fold>
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                System.err.println("ERROR: " + e.toString());
                blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean generaMovimientoInventarioPrestamosIngreso(java.sql.Connection con){
        boolean blnRes=false;
        try{
            System.out.println("Genera Ingreso .......... ");
            if(objIsGenOD==null){  // SI NO TIENE OD NO SE CONFIRMA
                if(blnIsSolRes){    /* Reservas */
                    if(generaNuevoContenedorItemsPrestamosIngreso()){  
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_RES, "+", 1, arlDatStkInvItm)){
                                blnRes=true;
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }
                else if (blnIsSolTraInv){ /* Solicitud de transferencia */
                    if(generaNuevoContenedorItemsPrestamosIngreso()){
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_DIS, "+", 1, arlDatStkInvItm)){
                                blnRes=true;
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }
                else{  /*VENTAS */  
                    if(generaNuevoContenedorItemsPrestamosIngreso()){
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_RES_VEN, "+", 1, arlDatStkInvItm)){
                                blnRes=true;
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }
            }
            else{
                if(generaNuevoContenedorItemsPrestamosIngreso()){
                    if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "+", 0, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_ING_BOD, "+", 1, arlDatStkInvItm)){
                            blnRes=true;
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                System.err.println("ERROR: " + e.toString());
                blnRes=false;
        }
        return blnRes;
    }
    
    private boolean generaMovimientoInventarioPrestamosEgreso(java.sql.Connection con){
        boolean blnRes=false;
        try{
            System.out.println("Genera Egreso .......... ");  
            if(objIsGenOD==null){  // SI NO TIENE OD NO SE CONFIRMA
                if(generaNuevoContenedorItemsPrestamosEgreso()){
                    if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "-", 0, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_DIS, "-", 1, arlDatStkInvItm)){
                            blnRes=true;
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
            }
            else{
                if(generaNuevoContenedorItemsPrestamosEgreso()){
                    if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_STK_ACT, "-", 0, arlDatStkInvItm)){
                        if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_DIS, "-",0, arlDatStkInvItm)){
                            if(objStkInv.actualizaInventario(con, intCodEmpMovStk,INT_ARL_STK_INV_CAN_EGR_BOD, "-", 1, arlDatStkInvItm)){
                                blnRes=true;
                            }else{blnRes=false;}
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                System.err.println("ERROR: " + e.toString());
                blnRes=false;
        }
        return blnRes;
    }
    
    /**
     *      Genera Nuevo Contenedor:
     *          nuevo contenedor de Items
     * 
     * @return 
     */
    
    private boolean generaNuevoContenedorItemsPrestamosIngreso(){
        boolean blnRes=true;
        double dblAux;
        try{
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlConDatPreInvIngAux.size();i++){
                arlRegStkInvItm = new ArrayList();
                
                intCodEmpMovStk=objUti.getIntValueAt(arlConDatPreInvIngAux, i, INT_ARL_COD_EMP);
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_COD_ITM_GRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_COD_ITM) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_COD_ITM_MAE) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_COD_LET_ITM) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_CAN_MOV));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlConDatPreInvIngAux, i,INT_ARL_COD_BOD_ING_EGR) );
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
    
       private boolean generaNuevoContenedorItemsPrestamosEgreso(){
        boolean blnRes=true;
        double dblAux;
        try{
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlConDatPreInvEgrAux.size();i++){
                arlRegStkInvItm = new ArrayList();
                
                intCodEmpMovStk=objUti.getIntValueAt(arlConDatPreInvEgrAux, i, INT_ARL_COD_EMP);
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_COD_ITM_GRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_COD_ITM) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_COD_ITM_MAE) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_COD_LET_ITM) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_CAN_MOV));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlConDatPreInvEgrAux, i,INT_ARL_COD_BOD_ING_EGR) );
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
    
    
    private boolean generaNuevoContenedorItemsTransferenciaEgreso(){
        boolean blnRes=true;
        double dblAux;
        try{
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlConDatTraInvStkEgr.size();i++){
                arlRegStkInvItm = new ArrayList();
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlConDatTraInvStkEgr, i,INT_ARL_COD_ITM_GRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlConDatTraInvStkEgr, i,INT_ARL_COD_ITM) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlConDatTraInvStkEgr, i,INT_ARL_COD_ITM_MAE) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlConDatTraInvStkEgr, i,INT_ARL_COD_LET_ITM) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlConDatTraInvStkEgr, i,INT_ARL_CAN_MOV));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlConDatTraInvStkEgr, i,INT_ARL_COD_BOD_ING_EGR));  // BODEGA
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    }
    
      private boolean generaNuevoContenedorItemsTransferenciaIngreso(){
        boolean blnRes=true;
        double dblAux;
        try{
            arlDatStkInvItm = new ArrayList();
            for(int i=0;i<arlConDatTraInvStkIng.size();i++){
                arlRegStkInvItm = new ArrayList();
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,objUti.getStringValueAt(arlConDatTraInvStkIng, i,INT_ARL_COD_ITM_GRP) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,objUti.getStringValueAt(arlConDatTraInvStkIng, i,INT_ARL_COD_ITM) );
                arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,objUti.getStringValueAt(arlConDatTraInvStkIng, i,INT_ARL_COD_ITM_MAE) );
                arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM,objUti.getStringValueAt(arlConDatTraInvStkIng, i,INT_ARL_COD_LET_ITM) );
                dblAux=Double.parseDouble(objUti.getStringValueAt(arlConDatTraInvStkIng, i,INT_ARL_CAN_MOV));
                if(dblAux<0){
                    dblAux=dblAux*-1;
                }
                arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
                arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,objUti.getStringValueAt(arlConDatTraInvStkIng, i,INT_ARL_COD_BOD_ING_EGR));  // BODEGA
                arlDatStkInvItm.add(arlRegStkInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    }
      
    public ZafMovIngEgrInv getZafMovInvEgrInv() {
        return objMovInv;
    }    
      
    private void limpiaBooleanos(){
        blnTraInv=false;
        blnPreInv=false;
        blnPreInvDim=false;
        blnPreInvTuv=false;
        blnPreInvCas=false;
    }
    
}
