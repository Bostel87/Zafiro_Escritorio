/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas.ZafHer25;

import GenOD.ZafGenOdPryTra;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Sistemas 5
 */
public class ZafHer25_MovEgrIng {
    
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
    final int INT_ARL_IND_ORG=18;   
    final int INT_ARL_COD_EMP_DES=19;
    
    
    final int INT_TBL_ITM_LIN=0;
    final int INT_TBL_ITM_EMP_ORG=1;
    final int INT_TBL_ITM_COD=2;
    final int INT_TBL_ITM_ALT2=3;
    final int INT_TBL_ITM_STK=4;
    final int INT_TBL_ITM_STKDIS=5;
    /*final int INT_TBL_ITM_CSTUNI=5;
    final int INT_TBL_ITM_CANT=6;    
    final int INT_TBL_ITM_CSTTOT=7;
    final int INT_TBL_ITM_EMP_DES=8;
    final int INT_TBL_ITM_NOM=9; */   
    final int INT_TBL_ITM_CSTUNI=6;
    final int INT_TBL_ITM_CANT=7;    
    
    final int INT_TBL_ITM_FINAL_DIS=8;    
    final int INT_TBL_ITM_FINAL_STK=9;    
    
    
    final int INT_TBL_ITM_CSTTOT=10;
    final int INT_TBL_ITM_EMP_DES=11;
    final int INT_TBL_ITM_NOM=12;    
    
    /*MODIFICADO 2017-04-20 PARA PROCESAR TRANSFERENCIAS DE BODEGAS DIFERENTE DE INMACONSA*/
    final int INT_TBL_ITM_BOD=13;
    /*MODIFICADO 2017-04-20 PARA PROCESAR TRANSFERENCIAS DE BODEGAS DIFERENTE DE INMACONSA*/
    
    private static final int INT_ARL_COD_EMP_G=0;
    private static final int INT_ARL_COD_LOC_G=1;
    private static final int INT_ARL_COD_TIP_DOC_G=2;
    private static final int INT_ARL_COD_CLI_PRV_G=3;
    private static final int INT_ARL_COD_BOD_ORI_DES_G=4;
    private static final int INT_ARL_COD_ITM_GRP_G=5;
    private static final int INT_ARL_COD_ITM_EMP_G=6;
    private static final int INT_ARL_COD_ITM_MAE_G=7;    
    private static final int INT_ARL_COD_ALT_ITM_G=8;
    private static final int INT_ARL_NOM_ITM_G=9;
    private static final int INT_ARL_UNI_MED_ITM_G=10;
    private static final int INT_ARL_COD_LET_ITM_G=11;    
    private static final int INT_ARL_CAN_ITM_G=12;
    private static final int INT_ARL_COS_UNI_G=13;//recibe el costo unitario del item tbm_inv.nd_cosUni
    private static final int INT_ARL_PRE_UNI_G=14;
    private static final int INT_ARL_PES_UNI_G=15;
    private static final int INT_ARL_IVA_COM_ITM_G=16;
    private static final int INT_ARL_EST_ING_EGR_MER_BOD_G=17;    
    
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP=0;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC=1;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC=2;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC=3;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG=4;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL=5;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL=6;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL=7;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL=8;
    private int INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL=9;   
    
    /*private int INT_ARL_CFP_COD_SRI=0;
    private int INT_ARL_CFP_COD_TIP_RET=1;
    private int INT_ARL_CFP_DES_COR_TIP_RET=2;
    private int INT_ARL_CFP_DES_LAR_TIP_RET=3;
    private int INT_ARL_CFP_POR_RET=4;
    private int INT_ARL_CFP_APL_RET=5;
    private int INT_ARL_CFP_COD_CTA=6;    
    
    private int INT_ARL_DFP_COD_EMP=0;
    private int INT_ARL_DFP_COD_PAG=1;
    private int INT_ARL_DFP_COD_REG=2;
    private int INT_ARL_DFP_DIA_CRE=3;
    private int INT_ARL_DFP_EST_SOP=4;*/
    
    
    private static final int INT_ARL_COD_EMP_H=0;
    private static final int INT_ARL_COD_LOC_H=1;
    private static final int INT_ARL_COD_TIP_DOC_H=2;
    private static final int INT_ARL_COD_CLI_PRV_H=3;
    private static final int INT_ARL_COD_BOD_ORI_DES_H=4;
    private static final int INT_ARL_COD_ITM_GRP_H=5;
    private static final int INT_ARL_COD_ITM_EMP_H=6;
    private static final int INT_ARL_COD_ITM_MAE_H=7;    
    private static final int INT_ARL_COD_ALT_ITM_H=8;
    private static final int INT_ARL_NOM_ITM_H=9;
    private static final int INT_ARL_UNI_MED_ITM_H=10;
    private static final int INT_ARL_COD_LET_ITM_H=11;    
    private static final int INT_ARL_CAN_ITM_H=12;
    private static final int INT_ARL_COS_UNI_H=13;//recibe el costo unitario del item tbm_inv.nd_cosUni
    private static final int INT_ARL_PRE_UNI_H=14;
    private static final int INT_ARL_PES_UNI_H=15;
    private static final int INT_ARL_IVA_COM_ITM_H=16;
    private static final int INT_ARL_EST_ING_EGR_MER_BOD_H=17;    
    
    
    private static final int INT_VEC_DIA_LIN=0;
    private static final int INT_VEC_DIA_COD_CTA=1;
    private static final int INT_VEC_DIA_NUM_CTA=2;
    private static final int INT_VEC_DIA_BUT_CTA=3;
    private static final int INT_VEC_DIA_NOM_CTA=4;
    private static final int INT_VEC_DIA_DEB=5;
    private static final int INT_VEC_DIA_HAB=6;
    private static final int INT_VEC_DIA_REF=7;
    private static final int INT_VEC_DIA_EST_CON=8;    
    
    
    final int INT_ARL_STK_INV_STK=0;        // nd_stk
    final int INT_ARL_STK_INV_STK_DIS=10;   // nd_stkDisp
    
    
    
    private ArrayList arlConDatPreEmpEgr, arlConDatPreEmpIng;    
    
    private int intCodEmp, intCodLoc,intCodTipDoc, intCodCliPrv, intCodDoc, intCodEmpEgr, intCodLocEgr, intCodTipDocEgr, intCodDocEgr;
    
    private BigDecimal bdeSig;
    
    private Date dtpFecDoc;
    
    private int intNumSecEmp, intNumSecGrp, intNumDocEgre, intNumDocIngr, intNumPag, intCodForPag, intNumMaxDiaCre;
    
    private ArrayList arlRegTbrDetMovInvEgr, arlDatTbrDetMovInvEgr;
    
    private ArrayList arlDatDetForPag, arlRegCabForPag, arlDatCabForPag;
    private int intCodEmpEgrGenAsiDia, intCodEmpIngGenAsiDia;
    
    ZafParSis objParSis;
    ZafUtil objUti;
    private ArrayList arlDatItm;
    private int intCodBodEgr, intCodBodIng;
    private char chrGenIva;
    
    private BigDecimal bgdValPorIva;//porcentaje de iva
    
    private BigDecimal bgdValDocSub;//subtotal
    private BigDecimal bgdValDocIva;//iva
    private BigDecimal bgdValDocTot;//total
    private int intNumDec;//variable global que tendrá el número de decimales a usar en todo el programa.    
    
    private BigDecimal bdeMarUtiComVenRel;   
    private boolean blnConEgr,blnConIng;
    
    private String strEgrGen;
    private String strIngGen;
    private ZafAsiDia objAsiDia;
    
    private int intCodForPagCliPrvIng=-1;
    private String strDesForPagCliPrvIng="";
    
    private String strErrEspc="";

    
    public ZafHer25_MovEgrIng(ZafParSis obj, java.awt.Component parent){
    
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            intNumDec=objParSis.getDecimalesBaseDatos();
            blnConIng=false;
            blnConEgr=false;
            bgdValPorIva=objParSis.getPorcentajeIvaVentas().divide(new BigDecimal("100"),objParSis.getDecimalesMostrar(),BigDecimal.ROUND_HALF_UP)  ;
            arlDatTbrDetMovInvEgr=new ArrayList();
            arlDatCabForPag=new ArrayList();
            arlDatDetForPag=new ArrayList();
            objAsiDia = new ZafAsiDia(objParSis);
            //vecDatDia=new Vector();

            /*cmpPad=parent;
            
            
            
            
//            intTipMov=0;// 0= un solo movimiento. Ejm: Egreso  ......  1= un documento pero en el detalle cantidad por negativo y por positivo. Ejm: Transferencias
            objSegMovInv=new ZafSegMovInv(objParSis, cmpPad);
            arlDatCnfNumRecEnt=new ArrayList();
            objCnfDoc=new ZafCnfDoc(objParSis, cmpPad);
            
            
            strEgrGen="";
            strIngGen="";*/
            
            //System.out.println("ZafMovIngEgrInv:   v0.1.3");
        }
        catch (CloneNotSupportedException e){
            //System.out.println("ZafMovInv: " + e);
            //objUti.mostrarMsgErr_F1(cmpPad, e);
        }            
    }
    
    public boolean realizaMovimientoEntreEmpresas(java.sql.Connection conExt, ZafTblMod objTblModItemEgr, ZafTblMod objTblModItemIng ){
        boolean blnRes=false; 
        int intCodLocTra=-1, intCodCli=-1;
        char chrGeneraIva;
        ArrayList arlConRegPreEmpEgr, arlConRegPreEmpIng;
        chrGeneraIva='N';   /* TRANSFERENCIA/PRESTAMOS NO GENERA IVA */
        try{
            
            if(conExt!=null){
                arlConDatPreEmpEgr = new ArrayList();
                arlConDatPreEmpIng = new ArrayList();
                for(int i=0; i<objTblModItemEgr.getRowCountTrue(); i++){
                    if(((Integer)objTblModItemEgr.getValueAt(i,INT_TBL_ITM_EMP_ORG))>0){
                        int intEmpOrg=(Integer)objTblModItemEgr.getValueAt(i,INT_TBL_ITM_EMP_ORG);
                        int intEmpDes=(Integer)objTblModItemEgr.getValueAt(i,INT_TBL_ITM_EMP_DES);
                        //if(cargarDatosVariables(txtCodEmpOrg.getText(), getCodigoItemMae( objTblModItemEgr.getValueAt(i,INT_TBL_DAT_COD_ITM).toString())) ){
                            if(intEmpOrg>0){
                                if(intEmpOrg==1){
                                    intCodLocTra = 11;
                                    if(intEmpDes==2){ intCodCli = 603;}
                                    else if(intEmpDes==4){ intCodCli = 1039;}
                                }else if(intEmpOrg==2){
                                    intCodLocTra = 5;
                                    if(intEmpDes==1){ intCodCli = 2854;}
                                    else if(intEmpDes==4){ intCodCli = 789;}
                                }else if(intEmpOrg==4){
                                     intCodLocTra = 11;
                                     if(intEmpDes==1){ intCodCli = 3117;}
                                    else if(intEmpDes==2){ intCodCli = 498;}
                                }
                            }
                            
                            

                            /* PRIMERO EL EGRESO SOLICITO INGRIK */
                            arlConRegPreEmpEgr = new ArrayList();
                            arlConRegPreEmpEgr.add(INT_ARL_COD_EMP,intEmpOrg );
                            arlConRegPreEmpEgr.add(INT_ARL_COD_LOC,intCodLocTra );     // CODIGO DEL LOCAL  
                            arlConRegPreEmpEgr.add(INT_ARL_COD_TIP_DOC, 206); // CODIGO DE TIPO DE DOCUMENTO
                            arlConRegPreEmpEgr.add(INT_ARL_COD_CLI, intCodCli); // DEBERIA SER NULL
                            //arlConRegPreEmpEgr.add(INT_ARL_COD_BOD_ING_EGR, 15 );  // BODEGA EN DONDE SE EGRESA LA MERCADERIA
                            arlConRegPreEmpEgr.add(INT_ARL_COD_BOD_ING_EGR, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_BOD)  );  // BODEGA EN DONDE SE EGRESA LA MERCADERIA
                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_GRP,  getCodItmGrp(intEmpOrg, objTblModItemEgr.getValueAt(i,INT_TBL_ITM_COD).toString(), conExt) );  // CODIGO DEL ITEM DE GRUPO
                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_COD));
                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_MAE, getCodigoItemMae( intEmpOrg,objTblModItemEgr.getValueAt(i,INT_TBL_ITM_COD).toString(), conExt) );
                            arlConRegPreEmpEgr.add(INT_ARL_COD_ITM_ALT, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_ALT2) ); /* CODIGO DE LA BODEGA DONDE SE TOMA LA MERCADERIA */
                            arlConRegPreEmpEgr.add(INT_ARL_NOM_ITM,objTblModItemEgr.getValueAt(i, INT_TBL_ITM_NOM) );
                            arlConRegPreEmpEgr.add(INT_ARL_UNI_MED_ITM,getUnidadMedidaItem(intEmpOrg, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_COD).toString(),conExt) );
                            arlConRegPreEmpEgr.add(INT_ARL_COD_LET_ITM, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_ALT2));  // CODIGO ALTERNO 2 DEL ITEM
                            if(i==19){
                                System.out.println("ITM "+(Integer)objTblModItemEgr.getValueAt(i, INT_TBL_ITM_COD));
                                System.out.println("CAN MOV "+(BigDecimal)objTblModItemEgr.getValueAt(i, INT_TBL_ITM_CANT));
                            }
                            
                            arlConRegPreEmpEgr.add(INT_ARL_CAN_MOV, ((BigDecimal)objTblModItemEgr.getValueAt(i, INT_TBL_ITM_CANT)).multiply(new BigDecimal(-1)) );  // CANTIDAD POR TRANSFERENCIA EGRESA
                            arlConRegPreEmpEgr.add(INT_ARL_COS_UNI, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_CSTUNI));  // COSTO UNITARIO
                            arlConRegPreEmpEgr.add(INT_ARL_PRE_UNI, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_CSTUNI));  // PRECIO UNITARIO                           
                            arlConRegPreEmpEgr.add(INT_ARL_PES_ITM, "0");  // PESO DEL ITEM
                            arlConRegPreEmpEgr.add(INT_ARL_IVA_COM_ITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                            arlConRegPreEmpEgr.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,"N");  // ITEM EGRESA FISICAMENTE 
                            arlConRegPreEmpEgr.add(INT_ARL_IND_ORG,0); 
                            arlConRegPreEmpEgr.add(INT_ARL_COD_EMP_DES, objTblModItemEgr.getValueAt(i, INT_TBL_ITM_EMP_DES));
                            arlConDatPreEmpEgr.add(arlConRegPreEmpEgr);

                            // ==============  INGRESO!!!! 19/MaYO/2016 (ASI DEBE SER PIDIO INGRIK )
                            if(intEmpDes>0){
                                if(intEmpDes==1){
                                    intCodLocTra = 11;
                                    if(intEmpOrg==2){ intCodCli = 603;}
                                    else if(intEmpOrg==4){ intCodCli = 1039;}
                                }else if(intEmpDes==2){
                                    intCodLocTra = 5;
                                    if(intEmpOrg==1){ intCodCli = 2854;}
                                    else if(intEmpOrg==4){ intCodCli = 789;}
                                }else if(intEmpDes==4){
                                     intCodLocTra = 11;
                                     if(intEmpOrg==1){ intCodCli = 3117;}
                                    else if(intEmpOrg==2){ intCodCli = 498;}
                                }
                            }
                            arlConRegPreEmpIng = new ArrayList();
                            arlConRegPreEmpIng.add(INT_ARL_COD_EMP,intEmpDes );
                            arlConRegPreEmpIng.add(INT_ARL_COD_LOC,intCodLocTra );     // CODIGO DEL LOCAL  
                            arlConRegPreEmpIng.add(INT_ARL_COD_TIP_DOC, 205); // CODIGO DE TIPO DE DOCUMENTO
                            arlConRegPreEmpIng.add(INT_ARL_COD_CLI, intCodCli); // DEBERIA SER NULL
                            //arlConRegPreEmpIng.add(INT_ARL_COD_BOD_ING_EGR, getCodigoBodegaEmpresa(txtCodEmpDes.getText(), txtCodBodDes.getText() ));  // BODEGA EN DONDE SE INGRESA LA MERCADERIA
                            //arlConRegPreEmpIng.add(INT_ARL_COD_BOD_ING_EGR, 15 );  // BODEGA EN DONDE SE INGRESA LA MERCADERIA                        
                            arlConRegPreEmpIng.add(INT_ARL_COD_BOD_ING_EGR, objTblModItemIng.getValueAt(i, INT_TBL_ITM_BOD)  );  // BODEGA EN DONDE SE EGRESA LA MERCADERIA
                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM_GRP, getCodItmGrp(intEmpDes, objTblModItemIng.getValueAt(i,INT_TBL_ITM_COD).toString(), conExt));  // CODIGO DEL ITEM DE GRUPO
                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM, objTblModItemIng.getValueAt(i, INT_TBL_ITM_COD));
                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM_MAE, getCodigoItemMae( intEmpDes,objTblModItemIng.getValueAt(i,INT_TBL_ITM_COD).toString(), conExt) );
                            arlConRegPreEmpIng.add(INT_ARL_COD_ITM_ALT, objTblModItemIng.getValueAt(i, INT_TBL_ITM_ALT2));  
                            arlConRegPreEmpIng.add(INT_ARL_NOM_ITM, objTblModItemIng.getValueAt(i, INT_TBL_ITM_NOM)  );
                            arlConRegPreEmpIng.add(INT_ARL_UNI_MED_ITM,getUnidadMedidaItem(intEmpDes, objTblModItemIng.getValueAt(i, INT_TBL_ITM_COD).toString(),conExt));
                            arlConRegPreEmpIng.add(INT_ARL_COD_LET_ITM, objTblModItemIng.getValueAt(i, INT_TBL_ITM_ALT2));  // CODIGO ALTERNO 2 DEL ITEM
                            arlConRegPreEmpIng.add(INT_ARL_CAN_MOV,  objTblModItemIng.getValueAt(i,INT_TBL_ITM_CANT)  );  // CANTIDAD POR TRANSFERENCIA EGRESO
                            arlConRegPreEmpIng.add(INT_ARL_COS_UNI, objTblModItemIng.getValueAt(i, INT_TBL_ITM_CSTUNI));  // COSTO UNITARIO
                            arlConRegPreEmpIng.add(INT_ARL_PRE_UNI, objTblModItemIng.getValueAt(i, INT_TBL_ITM_CSTUNI));  // PRECIO UNITARIO                           
                            arlConRegPreEmpIng.add(INT_ARL_PES_ITM, "0");  // PESO DEL ITEM
                            arlConRegPreEmpIng.add(INT_ARL_IVA_COM_ITM, "N");  //TRANSFERENCIAS NO GENERA IVA
                            arlConRegPreEmpIng.add(INT_ARL_EST_MER_ING_EGR_FIS_BOD,"N");  // ITEM EGRESA FISICAMENTE 
                            arlConRegPreEmpIng.add(INT_ARL_IND_ORG,0); 
                            arlConRegPreEmpIng.add(INT_ARL_COD_EMP_DES, intEmpOrg);
                            arlConDatPreEmpIng.add(arlConRegPreEmpIng);

                            /*
                            arlSolTraReg = new ArrayList();
                            arlSolTraReg.add(INT_ARL_SOL_TRA_COD_BOD_ING, txtCodBodDes.getText() ); // BODEGA DE GRUPO
                            arlSolTraReg.add(INT_ARL_SOL_TRA_COD_BOD_EGR, txtCodBodOrg.getText() );  //BODEGA DE GRUPO DE DONDE SALE LA MERCA                                 
                            arlSolTraReg.add(INT_ARL_SOL_TRA_ITM_GRP, objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString() );  //14 
                            arlSolTraReg.add(INT_ARL_SOL_TRA_CAN,  objTblMod.getValueAt(i,INT_TBL_DAT_CAN) ); // 
                            arlSolTraReg.add(INT_ARL_SOL_PES_UNI, 0 ); // PESO POR ITEM
                            arlSolTraReg.add(INT_ARL_SOL_PES_TOT, 0 ); // PESO TOTAL
                            arlSolTraDat.add(arlSolTraReg);*/
                        //}
                        blnRes=true;
                    }
                }
                    
            }
        }
        catch(Exception  Evt){ 
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, Evt);
            strErrEspc=Evt.getMessage();
            Evt.printStackTrace();
        }  
        return blnRes;
    }
    
    
public boolean getDatoEgresoIngreso(Connection con, Date dtpFechaDocumento, char chrGenerarIva
            , ArrayList arlDatosItemEgreso, ArrayList arlDatosItemIngreso
            /*, int codEmpSol, int codLocSol, int codTipDocSol, int codDocSol*/
            , String tipMovSeg/*, Object estGenOrdDes, ZafCfgBod objetoConfigBodega*/, BigDecimal bgdTotal)
    
    {
        //int intCodEmpEgr=0, intCodLocEgr=0, intCodTipDocEgr=0, intCodDocEgr=0;
        boolean blnRes=false;
        //objEstDocGenOrdDes=estGenOrdDes;
        ZafGenOdPryTra objGenOrdDes=new ZafGenOdPryTra();
        
        intCodBodEgr=-1;//nombre de bodega de egreso
        //intCodBodIng=-1;//nombre de bodega de ingreso
        intCodBodIng=15;
        String strSQL="";
        java.sql.Statement stm=null;
        ZafUtil objUti=new ZafUtil();
        ResultSet rst=null;
        
        try{
            if(con!=null){
                arlDatTbrDetMovInvEgr.clear();
//                intTipMov=0;
                dtpFecDoc=dtpFechaDocumento;
                
                chrGenIva=chrGenerarIva;
                
               
                stm=con.createStatement();
                
                if(arlDatosItemEgreso.size()>0){//Egreso
                    intCodEmpEgrGenAsiDia=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_EMP);
                    //intCodBodEgr=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_BOD_ORI_DES);
                    intCodBodEgr=15;
                }
                
                if(arlDatosItemIngreso.size()>0){//Ingreso
                    intCodEmpIngGenAsiDia=objUti.getIntValueAt(arlDatosItemIngreso, 0, INT_ARL_COD_EMP);
                    //intCodBodIng=objUti.getIntValueAt(arlDatosItemIngreso, 0, INT_ARL_COD_BOD_ORI_DES);
                    intCodBodIng=15;
                }
                
                //objCfgBod=objetoConfigBodega;
                //System.out.println("getDatoEgresoIngreso - objCfgBod:" + objCfgBod);
                
                
                if(arlDatosItemEgreso.size()>0){
                    intCodEmp=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_EMP_G);
                    intCodLoc=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_LOC_G);
                    intCodTipDoc=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_TIP_DOC_G);
                    intCodCliPrv=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_CLI_PRV_G);
                    intCodEmpEgr=intCodEmp;
                    intCodLocEgr=intCodLoc;
                    intCodTipDocEgr=intCodTipDoc;
                    
                    //intCodBodRegUno=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_BOD);
                    //System.out.println("ZafMovInv.getDatoEgresoIngreso EGRESO: " + intCodBodRegUno);
                    arlDatItm=arlDatosItemEgreso;

                    intCodEmpEgr=intCodEmp;

                    //código
                    strSQL="";
                    //strSQL+="SELECT MAX(a1.co_doc) AS co_doc FROM tbm_cabMovInv AS a1";
                    strSQL+=" select case when max(a1.co_doc) is null then 1 else (max(a1.co_doc)+1) end  AS co_doc FROM tbm_cabMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        //intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"":(rst.getString("co_doc").equals("")?"":rst.getString("co_doc")))) + 1;
                        intCodDoc=rst.getInt("co_doc");
                        intCodDocEgr=intCodDoc;
                    }else
                        return false;

                    //Egreso para armar la relacion con el INBOPR
                    for(int h=0; h<arlDatosItemEgreso.size(); h++){
                        arlRegTbrDetMovInvEgr=new ArrayList();
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP, intCodEmp);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC, intCodLoc);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC, intCodTipDoc);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC, intCodDoc);
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG, (h+1));
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL, "");
                        arlRegTbrDetMovInvEgr.add(INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL, "");
                        arlDatTbrDetMovInvEgr.add(arlRegTbrDetMovInvEgr);
                    }                    
                    
                    //número
                    strSQL="";
                    strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumDocEgre=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"":(rst.getString("ne_ultDoc").equals("")?"":rst.getString("ne_ultDoc")))) + 1;
                    else
                        return false;

                    //signo para generar el documento
                    strSQL="";
                    strSQL+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        bdeSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?"1":"-1");
                    else
                        return false;

                    //para obtener secuencia de empresa
                    strSQL="";
                    strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                    strSQL+=" FROM tbm_emp WHERE co_emp=" + intCodEmp + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumSecEmp=rst.getInt("ne_numSecEmp");                    

                    //para obtener secuencia de grupo
                    strSQL="";
                    strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                    strSQL+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumSecGrp=rst.getInt("ne_numSecGrp");
                                        
                    //--Forma de pago
                    strSQL="";
                    strSQL+="SELECT a8.co_forPag, a8.tx_des AS tx_nomForPag, a8.ne_numPag , max(a9.ne_diacre) as diaCre";
                    strSQL+=" FROM tbm_cabForPag AS a8 LEFT OUTER JOIN tbm_forPagCli AS a7";
                    strSQL+=" ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                    strSQL+=" ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                    
                    strSQL+=" INNER JOIN tbm_detforpag as a9";
                    strSQL+=" ON (a8.co_emp=a9.co_emp and a8.co_forpag=a9.co_forpag)";
                    
                    
                    strSQL+=" WHERE a8.co_emp=" + intCodEmp + " AND a6.st_reg NOT IN('E','I') AND a6.co_cli=" + intCodCliPrv + "";
                    strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                    strSQL+=" ORDER BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                    //System.out.println("Forma de pago: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        intNumPag=rst.getInt("ne_numPag");
                        intCodForPag=rst.getInt("co_forPag");
                        intNumMaxDiaCre=rst.getInt("diaCre");
                    }
                    else
                        return false;
                    
                    int intEmpDes=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_EMP_DES);   
                    BigDecimal bgdTotEgr=bgdTotal.abs().multiply(bdeSig);
                    if(generarSQLDocumentoEgreso(con,bgdTotEgr, intEmpDes)){
                        //intCodEmpEgr=intCodEmp; intCodLocEgr=intCodLoc; intCodTipDocEgr=intCodTipDoc; intCodDocEgr=intCodDoc;
                        //INGRESO
                        arlDatItm=arlDatosItemIngreso;
                        if(arlDatItm.size()>0){
                            intCodEmp=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_EMP_G);
                            intCodLoc=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_LOC_G);
                            intCodTipDoc=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_TIP_DOC_G);
                            intCodCliPrv=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_CLI_PRV_G);
//                            intCodBodRegUno=objUti.getIntValueAt(arlDatItm, 0, INT_ARL_COD_BOD);
//                            System.out.println("ZafMovInv.getDatoEgresoIngreso BODEGA INGRESO " + intCodBodRegUno);
                            
                            
                            
                            //código
                            strSQL="";
                            //strSQL+="SELECT MAX(a1.co_doc) AS co_doc FROM tbm_cabMovInv AS a1";
                            strSQL+=" select case when max(a1.co_doc) is null then 1 else (max(a1.co_doc)+1) end  AS co_doc FROM tbm_cabMovInv AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            //System.out.println("codigo " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                //intCodDoc=(Integer.parseInt(rst.getObject("co_doc")==null?"":(rst.getString("co_doc").equals("")?"":rst.getString("co_doc")))) + 1;
                                intCodDoc=rst.getInt("co_doc");
                            else
                                return false;
                            
                            //Ingreso para armar la relacion con el EGBOPR
                            for(int h=0; h<arlDatosItemIngreso.size(); h++){
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL,""+intCodEmp);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL,""+ intCodLoc);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL,""+ intCodTipDoc);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL,""+ intCodDoc);
                                objUti.setStringValueAt(arlDatTbrDetMovInvEgr, h, INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL,""+ (h+1));
                            }
                                                                                    
                            //número
                            strSQL="";
                            strSQL+="SELECT a1.ne_ultDoc FROM tbm_cabTipDoc AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumDocIngr=(Integer.parseInt(rst.getObject("ne_ultDoc")==null?"":(rst.getString("ne_ultDoc").equals("")?"":rst.getString("ne_ultDoc")))) + 1;
                            else
                                return false;

                            //signo para generar el documento
                            strSQL="";
                            strSQL+="SELECT a1.tx_natdoc FROM tbm_cabTipDoc AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                            strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                bdeSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?"1":"-1");
                            else
                                return false;

                            //para obtener secuencia de empresa
                            strSQL="";
                            strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecEmp";
                            strSQL+=" FROM tbm_emp WHERE co_emp=" + intCodEmp + "";
                            //System.out.println("para obtener secuencia de empresa " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumSecEmp=rst.getInt("ne_numSecEmp");

                            //para obtener secuencia de grupo
                            strSQL="";
                            strSQL+="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS ne_numSecGrp";
                            strSQL+=" FROM tbm_emp WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                            //System.out.println("para obtener secuencia de grupo " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                                intNumSecGrp=rst.getInt("ne_numSecGrp");
                            
                            //--Forma de pago
                            
                           
                            strSQL="";
                            strSQL+="SELECT a8.co_forPag, a8.tx_des AS tx_nomForPag, a8.ne_numPag , max(a9.ne_diacre) as diaCre";
                            strSQL+=" FROM tbm_cabForPag AS a8 LEFT OUTER JOIN tbm_forPagCli AS a7";
                            strSQL+=" ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                            strSQL+=" LEFT OUTER JOIN tbm_cli AS a6";
                            strSQL+=" ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                            strSQL+=" INNER JOIN tbm_detforpag as a9";
                            strSQL+=" ON (a8.co_emp=a9.co_emp and a8.co_forpag=a9.co_forpag)";
                            strSQL+=" WHERE a8.co_emp=" + intCodEmp + " AND a6.st_reg NOT IN('E','I') AND a6.co_cli=" + intCodCliPrv + "";
                            strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                            strSQL+=" ORDER BY a8.co_forPag, a8.tx_des, a8.ne_numPag";
                            //System.out.println("Forma de pago: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                intNumPag=rst.getInt("ne_numPag");
                                intCodForPag=rst.getInt("co_forPag");
                                //intNumMaxDiaCre=rst.getInt("diaCre");
                            }
                            else
                                return false;

//     
                            //Limpiar objetos de conexion a db
                            stm.close();
                            stm=null;
                            rst.close();
                            rst=null;
                            intEmpDes=objUti.getIntValueAt(arlDatosItemEgreso, 0, INT_ARL_COD_EMP_DES);
                            BigDecimal bgdTotIng=bgdTotal.abs().multiply(bdeSig);
                            if(generarSQLDocumentoIngreso(con,bgdTotIng,intEmpDes )){
                                //blnRes=true;
                                if(actualizarStk(con,  arlDatosItemEgreso, arlDatosItemIngreso))
                                {
                                    blnRes=true;
                                }
                                else{
                                    blnRes=false;
                                }
                            }   
                        }
                    }
                }
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            strErrEspc=e.getMessage();
            System.err.println("ERROR SQL ingreso/egreso: " + e.toString());
        }
        catch(Exception e){
            blnRes=false;
            strErrEspc=e.getMessage();
            System.err.println("ERROR ingreso/egreso: " + e.toString());
        }
        return blnRes;
    }      

    private int obtenerCodForPag(int intCodEmpEgr, int intCodEmpIng){
        int intRetCodForPag=0;
        if(intCodEmpEgr==1){
            if(intCodEmpIng==2){ 
                intRetCodForPag = 2;
            }
            else if(intCodEmpIng==4){
                intRetCodForPag = 2;
            }
        }else if(intCodEmpEgr==2){
            if(intCodEmpIng==1){ 
                intRetCodForPag = 8;
            }
            else if(intCodEmpIng==4){ 
                intRetCodForPag = 8;
            }
        }else if(intCodEmpEgr==4){
             if(intCodEmpIng==1){ 
                intRetCodForPag = 8;
             }
            else if(intCodEmpIng==2){ 
                intRetCodForPag = 31;
            }
        }
        return intRetCodForPag;
    }

    private boolean insertar_tbmCabMovInv(Connection con, int tipoTransaccion, BigDecimal bgdTotal, int intEmpDes){
        boolean blnRes=true;
        String strRucCliPrv="";
        String strNomCliPrv="";
        String strDirCliPrv="";
        String strTelCliPrv="";
        int intCodForPagCliPrv=-1;
        //int intCodForPagCliPrvIng=-1;
        String strDesForPagCliPrv="";
        //String strDesForPagCliPrvIng="";
        //int intCodBodGrpOri=-1, intCodBodGrpDes=-1;
        String strSQL="";
        ResultSet rst=null;
        Statement stm=null;
        
        try{
            
            if(con!=null){
                stm=con.createStatement();
                
                if( (tipoTransaccion==0) || (tipoTransaccion==2) ){//egreso o ingreso
                    
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a1.co_forPag, a2.tx_des";
                    strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_cabForPag AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_cli=" + intCodCliPrv + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        strRucCliPrv=rst.getString("tx_ide");
                        strNomCliPrv=rst.getString("tx_nom");
                        strDirCliPrv=rst.getString("tx_dir");
                        strTelCliPrv=rst.getString("tx_tel");
                        if(tipoTransaccion==0){
                            intCodForPagCliPrv=rst.getInt("co_forPag");
                            strDesForPagCliPrv=rst.getString("tx_des");
                            intCodForPagCliPrvIng= obtenerCodForPag(intCodEmp, intEmpDes);
                            strDesForPagCliPrvIng=strDesForPagCliPrv;
                        }
                        rst.close();
                        rst=null;
                    }

                    if( (tipoTransaccion==0) ){//egreso
                        if(intCodEmpIngGenAsiDia==2){
                            strRucCliPrv="";
                            strNomCliPrv="";
                            strDirCliPrv="";
                            strTelCliPrv="";
                            
                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==1) ){
                                //UIO
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=1)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }

                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==4) ){
                                //MANTA
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=2)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }

                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==12) ){
                                //STO DOMINGO
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=3)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }

                            if( (intCodEmpIngGenAsiDia==2) && (intCodBodIng==28) ){
                                //CUENCA
                                strSQL="";
                                strSQL+=" SELECT a1.co_cli, a1.tx_nom, a1.tx_ide, a2.tx_dir, a2.tx_tel1 AS tx_tel, a1.co_tipper  ";
                                strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_dircli AS a2";
                                strSQL+=" ON(a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a2.co_reg=4)";
                                strSQL+=" WHERE a1.co_emp=" + intCodEmpEgrGenAsiDia + " AND a1.co_cli=" + intCodCliPrv + "";
                                strSQL+=";";
                            }
                            
                            //System.out.println("Datos de direccion: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                strRucCliPrv=rst.getString("tx_ide");
                                strNomCliPrv=rst.getString("tx_nom");
                                strDirCliPrv=rst.getString("tx_dir");
                                strTelCliPrv=rst.getString("tx_tel");
                                rst.close();
                                rst=null;
                            }
                        }                                           
                    }
                }
                
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, ne_numcot, ne_numdoc, ne_numgui, fe_doc, co_cli, tx_ruc, tx_nomcli, tx_dircli, ";                         
                strSQL+="   tx_telcli,co_forpag, tx_desforpag, nd_sub, nd_tot, tx_obs1, st_reg, fe_ing,  co_usring,  tx_numped,";
                strSQL+="   ne_secgrp, ne_secemp, nd_valiva, co_mnu, st_tipdev, st_imp, st_regrep, st_coninv, ";
                strSQL+="   st_autingegrinvcon,st_creguirem,st_coninvtraaut,";
                strSQL+="   st_excdocconvencon, st_docconmersaldemdebfac, ne_numvecrecdoc,tx_coming, tx_commod, nd_tasint, ne_tipcal, ne_unitie";
                //strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv, tx_tipmov, st_genOrdDes";
                strSQL+=")";
                strSQL+="   (";
                strSQL+="    SELECT ";
                strSQL+="" + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="0, ";//ne_numcot
                if (tipoTransaccion==0){
                    strSQL+="" + intNumDocEgre + ",";//ne_numdoc
                }else if (tipoTransaccion==2){
                    strSQL+="" + intNumDocIngr + ",";//ne_numdoc
                }
                strSQL+="0, ";//ne_numgui
                //strSQL+=" '" + objUti.formatearFecha(dtpFecDoc.toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL+="'"+ objUti.formatearFecha(dtpFecDoc, objParSis.getFormatoFechaBaseDatos()) +"', "; //fe_doc
                if( (tipoTransaccion==0) || (tipoTransaccion==2) ){//egreso o ingreso
                    strSQL+="" + intCodCliPrv + ",";//co_cli
                    strSQL+="" + objUti.codificar(strRucCliPrv) + ",";//tx_ruc
                    strSQL+="" + objUti.codificar(strNomCliPrv) + ",";//tx_nomcli
                    strSQL+="" + objUti.codificar(strDirCliPrv) + ",";//tx_dircli
                    strSQL+="" + objUti.codificar(strTelCliPrv) + ",";//tx_telcli
                    if(tipoTransaccion==0){
                        strSQL+="" + objUti.codificar(intCodForPagCliPrv) + ",";//co_forpag
                        strSQL+="" + objUti.codificar(strDesForPagCliPrv) + ",";//tx_desforpag
                    }else if(tipoTransaccion==2){
                        strSQL+="" + objUti.codificar(intCodForPagCliPrvIng) + ",";//co_forpag
                        strSQL+="" + objUti.codificar(strDesForPagCliPrvIng) + ",";//tx_desforpag
                    }
                }
                else{
                    strSQL+="Null,";//co_cli
                    strSQL+="Null,";//tx_ruc
                    strSQL+="Null,";//tx_nomcli
                    strSQL+="Null,";//tx_dircli
                    strSQL+="Null,";//tx_telcli
                    strSQL+="Null,";//co_forpag
                    strSQL+="Null,";//tx_desforpag
                }

                strSQL+=bgdTotal+",";//nd_sub
                strSQL+=bgdTotal+",";//nd_tot
                strSQL+="'Generado por Zafiro: '||" + " CURRENT_TIMESTAMP" + ",";//tx_obs1
                strSQL+="'A'" + ",";//st_reg
                strSQL+=" CURRENT_TIMESTAMP" + ",";//fe_ing
                //strSQL+=" CURRENT_TIMESTAMP" + ",";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                //strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                
                if (tipoTransaccion==2){//INGRESO
                    strSQL+="" + intNumDocEgre + ",";//tx_numped
                }else{                
                    strSQL+="Null,";//tx_numped   No almacena nada en este momento sino cuando se obtengan los dos tipos de documentos
                }
                strSQL+="" + intNumSecGrp + ",";//ne_secgrp
                strSQL+="" + intNumSecEmp + ",";//ne_secemp
                strSQL+="0,";//nd_valiva
                strSQL+="" + objParSis.getCodigoMenu() + ",";//co_mnu
                
                //st_tipdev, st_imp, st_regrep, 
                strSQL+="'C',";//st_tipdev
                strSQL+="'S',";//st_imp
                strSQL+="'P',";//st_regrep
                if((tipoTransaccion==2) ){
                    strSQL+="'P',";//st_coninv
                }else{
                    strSQL+="Null,";//st_coninv
                }
                //st_autingegrinvcon,st_creguirem,st_coninvtraaut
                strSQL+="'N','N','N',";//st_autingegrinvcon,st_creguirem,st_coninvtraaut
                strSQL+="'S','N',0,";//st_excdocconvencon, st_docconmersaldemdebfac, ne_numvecrecdoc
                
                strSQL+="" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";//tx_coming
                strSQL+="" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";//tx_commod
                
                
                //System.out.println("1-insertar_tbmCabMovInv");
                
                
                strSQL+="0,1,1";  // nd_tasint, ne_tipcal, ne_unitie
                
                
                strSQL+=");";
                //System.out.println("insertar_tbmCabMovInv: " + strSQL);
                
                //para obtener secuencia de empresa
                strSQL+=" UPDATE tbm_emp";
                strSQL+=" SET ne_secUltDocTbmCabMovInv=(ne_secUltDocTbmCabMovInv+1)";
                strSQL+=" WHERE co_emp=" + intCodEmp + "";
                strSQL+=";";
                //System.out.println("aumentar secuencia empresa: " + strSQL);
                //para obtener secuencia de grupo
                strSQL+=" UPDATE tbm_emp";
                strSQL+=" SET ne_secUltDocTbmCabMovInv=(ne_secUltDocTbmCabMovInv+1)";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=";";
                //System.out.println("aumentar secuencia grupo: " + strSQL);
                
                stm.executeUpdate(strSQL);
             
                stm.close();
                stm=null;

            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - insertar_tbmCabMovInv SQL: " + e);
            strErrEspc=e.getMessage();
//            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmCabMovInv: " + e);
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
            strErrEspc=e.getMessage();
            
        }
        return blnRes;
    }


/**
     * Función que permite realizar la inserción de la detalle
     * @param con: Especifíca la conexión que se desea utilizar
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
    */
    private boolean insertar_tbmDetMovInv(Connection con, int tipoOperacion){
        boolean blnRes=true;
        int j=0;
        BigDecimal bgdCanItm;
        BigDecimal bgdPreItm;
        String strSQLIns="";
        String strEstIva="";
        BigDecimal bgdTotItm=BigDecimal.ZERO;
        BigDecimal bgdIntSig;
        String strNomBodDet="";
        int intNumFil=0;
        String strEstIngEgrMerBod="";
        //arlDatCnfNumRecEnt.clear();
        
        int intCodBodGrpOri=-1, intCodBodGrpDes=-1;
        
        String strSQL="";
        Statement stm=null;
        ResultSet rst=null;
        
        
        //boolean blnValConEgr, blnValConIng;
        
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQLIns="";
                bgdValDocSub=new BigDecimal("0");
                bgdValDocIva=new BigDecimal("0");
                bgdValDocTot=new BigDecimal("0");

                //System.out.println("arlDatItm: " + arlDatItm.toString());
                //System.out.println("tipoOperacion: " + tipoOperacion);
                for(int i=0; i<arlDatItm.size(); i++){
                    
//                    blnValConEgr=false;
//                    blnValConIng=false;
                    
                    
                    if((i%2)==0)//es impar
                        intNumFil++;
                    
                    //nombre de bodega origen
                    strSQL="";
                    strSQL+="SELECT a1.tx_nom AS tx_nomBod FROM tbm_bod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_bod=" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES_H)) + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        strNomBodDet=rst.getString("tx_nomBod");
                    else
                        return false;

                    //Egreso, Ingreso, Factura, Orden de Compra
                    //--aqui se inserta en positivo porque la cantidad se la recibe en postivo
                    j++;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detmovinv(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt,";
                    strSQL+="   tx_nomitm, tx_unimed, co_bod, nd_can, nd_canorg, nd_cosuni, nd_preuni,";
                    strSQL+="   nd_pordes, st_ivacom, nd_exi, nd_valexi, st_reg, nd_cancon, tx_obs1,";
                    //strSQL+="   co_usrcon, ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro,";
                    strSQL+="   co_usrcon, nd_tot, tx_codalt2, nd_costot, nd_cospro,";
                    strSQL+="   nd_cosunigrp, nd_costotgrp, nd_exigrp, nd_valexigrp, nd_cosprogrp,";
                    strSQL+="   nd_candev, ";
                    strSQL+="   st_regrep,";
                    strSQL+="   co_itmact, co_locrelsoldevven, co_tipdocrelsoldevven,";
                    strSQL+="   co_docrelsoldevven, co_regrelsoldevven, ";
                    strSQL+="   st_meringegrfisbod,";
                    strSQL+="   nd_cannunrec,";
                    strSQL+="   co_locrelsolsaltemmer, co_tipdocrelsolsaltemmer, co_docrelsolsaltemmer,";
                    strSQL+="   co_regrelsolsaltemmer, nd_preunivenlis, nd_pordesvenmax, tx_nombodorgdes";
                    /*strSQL+="   nd_cantotmalest, nd_cantotmalestpro, nd_cantotnunrecpro, st_cliretemprel,";
                    strSQL+="   fe_retemprel, tx_perretemprel, tx_vehretemprel, tx_plavehretemprel,";
                    strSQL+="   tx_obscliretemprel, nd_ara, nd_preuniimp, nd_valtotfobcfr, nd_cantonmet,";
                    strSQL+="   nd_valfle, nd_valcfr, nd_valtotara, nd_valtotgas, nd_canutiorddis";*/
                    
                    strEstIngEgrMerBod=objUti.getObjectValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD_H)==null?"":objUti.getStringValueAt(arlDatItm, i, INT_ARL_EST_ING_EGR_MER_BOD_H);
                    
                    /*strSQL+=", nd_canEgrBod";
                    strSQL+=", nd_canIngBod";
                    strSQL+=", nd_canPen";
                    strSQL+=", st_meringegrfisbod";*/
                    
                    strSQL+=")";
                    strSQL+="(";
                    strSQL+="   SELECT ";
                    strSQL+="" + intCodEmp + ",";//co_emp
                    strSQL+="" + intCodLoc + ",";//co_loc
                    strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                    strSQL+="" + intCodDoc + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP_H) + ",";//co_itm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ALT_ITM_H)) + ",";//tx_codalt
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_NOM_ITM_H)) + ",";//tx_nomitm
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_UNI_MED_ITM_H)) + ",";//tx_unimed
                    //strSQL+="" + intCodBodRegUno + ",";//co_bod
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES_H)) + ",";//co_bod
                    bgdCanItm=new BigDecimal(objUti.getObjectValueAt(arlDatItm, i, INT_ARL_CAN_ITM_H)==null?"0":(objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM_H).equals("")?"0":objUti.getStringValueAt(arlDatItm, i, INT_ARL_CAN_ITM_H)));
                    strSQL+="" + bgdCanItm + ",";//nd_can
                    strSQL+="Null,";//nd_canorg
                    bgdPreItm=objUti.redondearBigDecimal(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COS_UNI_H), intNumDec);
                    //System.out.println("bgdPreItm-ANTES: " + bgdPreItm);
                    strEstIva=objUti.getStringValueAt(arlDatItm, i, INT_ARL_IVA_COM_ITM);
                    bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec);                 

                    if(chrGenIva=='S'){
                        
                        if(getMarUtiMovRel(tipoOperacion)){
                            bgdPreItm=objUti.redondearBigDecimal((bgdPreItm.multiply(bdeMarUtiComVenRel)),objParSis.getDecimalesBaseDatos());
                            //System.out.println("bgdPreItm-DESPUES: " + bgdPreItm);
                        }
                        else
                            blnRes=false;
                        
                        bgdTotItm=objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), intNumDec);
                        
                        if(strEstIva.equals("S")){
                            bgdValDocIva=bgdValDocIva.add(objUti.redondearBigDecimal((bgdTotItm.multiply(bgdValPorIva)), objParSis.getDecimalesMostrar()));//al final tendrá el valor del iva del documento
                        }
                        
                    }
                    
                    strSQL+="" + bgdPreItm + ",";//nd_cosuni
                    strSQL+="" + bgdPreItm + ",";//nd_preuni
                    strSQL+="0,";//nd_pordes     por disposición de Eddye para FACVENE, FACCOM, INGBOPR, EGBOPR, TRANSFERENCIAS EN GENERAL, no se debe realizar descuento  

                    strSQL+="" + objUti.codificar(strEstIva) + ",";//st_ivacom                
                    strSQL+="Null, ";//nd_exi
                    strSQL+="Null,";//nd_valexi
                    strSQL+="Null,";//st_reg
                    strSQL+="0,";//nd_cancon
                    strSQL+="Null,";//tx_obs1
                    strSQL+="Null,";//co_usrcon
                    //strSQL+="" + intNumFil + ",";//ne_numfil
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar()) + ", ";//nd_tot
                    strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_LET_ITM)) + ",";//tx_codalt2
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar()) + ",";//nd_costot
                    strSQL+="Null,";//nd_cospro
                    strSQL+="" + bgdPreItm + ",";//nd_cosunigrp
                    strSQL+="" + objUti.redondearBigDecimal((bgdCanItm.multiply(bgdPreItm)), objParSis.getDecimalesMostrar()) + ",";//nd_costotgrp
                    strSQL+="Null,";//nd_exigrp
                    strSQL+="Null,";//nd_valexigrp
                    strSQL+="Null,";//nd_cosprogrp
                    strSQL+="0, ";//nd_candev
                    strSQL+="'P', ";//st_regrep
                    strSQL+="" + objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_ITM_EMP_H) + ",";//co_itmact
                    strSQL+="Null,";//co_locrelsoldevven
                    strSQL+="Null,";//co_tipdocrelsoldevven
                    strSQL+="Null,";//co_docrelsoldevven
                    strSQL+="Null,";//co_regrelsoldevven
                    strSQL+="'N',";//st_meringegrfisbod                    
                    strSQL+=" 0,";//nd_cannunrec
                    strSQL+="Null,";//co_locrelsolsaltemmer
                    strSQL+="Null,";//co_tipdocrelsolsaltemmer
                    strSQL+="Null,";//co_docrelsolsaltemmer
                    strSQL+="Null,";//co_regrelsolsaltemmer
                    strSQL+="Null,";//nd_preunivenlis
                    strSQL+="Null,";//nd_pordesvenmax
    //                strSQL+="" + objUti.codificar(strNomBodRegUno) + ",";//tx_nombodorgdes
                    strSQL+="" + objUti.codificar(strNomBodDet); //+ ",";//tx_nombodorgdes
                    //3.- Validación por terminal del item
                    
                    
                        //System.out.println("********************************************************************");
                        //System.out.println("strEstIngEgrMerBod: " + strEstIngEgrMerBod);
                    

                    ////////////////////////////////////////////////////////////////
                    strSQL+=");";
                    //System.out.println("**--++ :)  insertar_tbmDetMovInv: " + strSQL);
                    strSQLIns+=strSQL;
                    
                  
                }
                
//                System.out.println("****************************************************************");
//                System.out.println("bgdValDocSub:" + bgdValDocSub);
//                System.out.println("bgdValDocTot:" + bgdValDocTot);
//                System.out.println("bgdValDocIva:" + bgdValDocIva);
//                System.out.println("****************************************************************");
                

                
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(java.sql.SQLException e){
            System.out.println("Error - insertar_tbmDetMovInv SQL: " + e);
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            strErrEspc=e.getMessage();
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmDetMovInv: " + e);
            strErrEspc=e.getMessage();
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean actualizar_tbmCabMovInvEgr(Connection con){
        boolean booRet=false;
        Statement stm=null;
        String strUpdate="";
        try{
            stm=con.createStatement();
            strUpdate=" Update tbm_cabmovinv set tx_numped="+intNumDocIngr+
                      " where co_emp="+intCodEmpEgr+
                      " and co_loc="+intCodLocEgr+
                      " and co_tipdoc="+intCodTipDocEgr+
                      " and co_doc="+intCodDocEgr;
            stm.executeUpdate(strUpdate);
            booRet=true;
        }catch(SQLException ex){
            //ex.printStackTrace();
            strErrEspc=ex.getMessage();
            booRet=false;
        }catch(Exception e){
            strErrEspc=e.getMessage();
            booRet=false;
        }
        return booRet;
    }    
    
/**
     * Función que permite realizar la inserción en la tabla de pagos
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
     */
    private boolean insertar_tbmPagMovInv(Connection con, BigDecimal bgdTotal){
        boolean blnRes=true;
        String strTipRetApl="";
        int j=0;
        BigDecimal bgdMonPag=new BigDecimal("0");
        BigDecimal bgdMonTotAplRet=new BigDecimal("0");
        String strSQLIns="";
        BigDecimal bgdValPenDocTot=new BigDecimal("0");
        BigDecimal bgdValAplDocTot=new BigDecimal("0");//Valor que se ha aplicado del total pendiente(Total - retenciones)
        BigDecimal bgdValCuoPag=new BigDecimal("0");//valor de la cuota que se debe cancelar
        BigDecimal bgdValCuoPagAcu=new BigDecimal("0");//valor de la cuota que ya se ha "pagado"
        String strSQL="";
        Statement stm=null;
        try{
            stm=con.createStatement();
            //registros de retenciones en tbm_pagMovInv
//            System.out.println("arlDatCabForPag:" + arlDatCabForPag.toString());
//            System.out.println("arlDatDetForPag:" + arlDatDetForPag.toString());
            
            //for(int i=0; i<arlDatCabForPag.size(); i++){
                j++;
                strSQL="";
                strSQL+="INSERT INTO tbm_pagmovinv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, ";
                strSQL+="   co_doc, co_reg, ne_diacre, ";
                strSQL+="   fe_ven, co_tipret, nd_porret,  ";
                strSQL+="   tx_aplret, mo_pag, ne_diagra,  ";
                strSQL+="   nd_abo, st_sop,st_entsop,  ";
                strSQL+="   st_pos, co_banchq, tx_numctachq, ";
                strSQL+="   tx_numchq, fe_recchq,fe_venchq, ";
                strSQL+="   nd_monchq, co_prochq, st_reg, ";
                strSQL+="   st_regrep, fe_ree, co_usrree,  ";
                strSQL+="   tx_comree, st_autpag, co_ctaautpag, ";
                strSQL+="   tx_obs1, tx_codsri, nd_basimp,";
                strSQL+="   tx_numser, tx_numautsri, tx_feccad, ";
                strSQL+="   fe_venchqautpag, nd_valiva, tx_tipreg";
                strSQL+=" ) (";
                strSQL+=" SELECT " + intCodEmp + ",";//co_emp
                strSQL+="" + intCodLoc + ",";//co_loc
                strSQL+="" + intCodTipDoc + ",";//co_tipdoc
                strSQL+="" + intCodDoc + ",";//co_doc
                strSQL+="" + j + ",";//co_reg
                strSQL+=""+intNumMaxDiaCre+",";//ne_diacre
                strSQL+=" ( SELECT cast(" + objUti.codificar(objUti.formatearFecha(dtpFecDoc, "yyyy-MM-dd")) + " as Date) + " + intNumMaxDiaCre + "),";                
                strSQL+="0,";//co_tipret
                strSQL+="0,";//nd_porret
                strSQL+="Null,";//tx_aplret
                
                //bgdMonTotAplRet=objUti.redondearBigDecimal((bgdMonTotAplRet.add(bgdMonPag)), objParSis.getDecimalesMostrar());                                                            //Valor aplicado por retenciones
                strSQL+="" + objUti.redondearBigDecimal(bgdTotal, objParSis.getDecimalesMostrar()) + ",";//mo_pag
                strSQL+="0,";//ne_diagra
                strSQL+="0,";//nd_abo
                strSQL+="'N',";//st_sop
                strSQL+="'N',";//st_entsop
                strSQL+="'N',";//st_pos
                strSQL+="Null,";//co_banchq
                strSQL+="Null,";//tx_numctachq
                strSQL+="Null,";//tx_numchq
                strSQL+="Null,";//fe_recchq
                strSQL+="Null,";//fe_venchq
                strSQL+="Null,";//nd_monchq
                strSQL+="Null,";//co_prochq
                strSQL+="'A',";//st_reg
                strSQL+="'I',";//st_regrep
                strSQL+="Null,";//fe_ree
                strSQL+="Null,";//co_usrree
                strSQL+="Null,";//tx_comree
                strSQL+="'N',";//st_autpag
                strSQL+="Null,";//co_ctaautpag
                strSQL+="Null,";//tx_obs1
                strSQL+="Null,";//tx_codsri
                //strSQL+="" + objUti.codificar(objUti.getStringValueAt(arlDatCabForPag, i, INT_ARL_CFP_COD_SRI)) + ",";//tx_codsri
                //strSQL+="" + objUti.redondearBigDecimal(bgdMonPag, objParSis.getDecimalesMostrar()) + ",";//nd_basimp
                strSQL+="0,";
                strSQL+="Null,";//tx_numser
                strSQL+="Null,";//tx_numautsri
                strSQL+="Null,";//tx_feccad
                strSQL+="Null,";//fe_venchqautpag
                //strSQL+="" + bgdValDocIva + ",";//nd_valiva
                strSQL+="Null,";//nd_valiva
                strSQL+="Null";//tx_tipreg
                strSQL+=");";
                //System.out.println("insertar_tbmPagMovInv: " + strSQL);
                strSQLIns+=strSQL;
            //}
            
            bgdValPenDocTot=bgdValDocTot.subtract(bgdMonTotAplRet);//Valor del documento - Valor aplicado por retenciones
            

            stm.executeUpdate(strSQLIns);
            
//            System.out.println("---------------------------");
//            System.out.println("bgdValDocTot: " + bgdValDocTot);
//            System.out.println("bgdValCuoPagAcu: " + bgdValCuoPagAcu);
//            System.out.println("bgdMonTotAplRet: " + bgdMonTotAplRet);
            
            
        }
        catch(java.sql.SQLException e){
            System.out.println("Error SQL - insertar_tbmPagMovInv SQL: " + e);
            strErrEspc=e.getMessage();
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - insertar_tbmPagMovInv SQL: " + e);
            strErrEspc=e.getMessage();
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }   
    
    private boolean actualizarStk(Connection conIns,/* int intCodEmpIng,  */ArrayList arlDatosItemEgreso, ArrayList arlDatosItemIngreso){
        boolean booRet=true;
        ZafStkInv invStock= new ZafStkInv(objParSis);
        ZafUtil objUti=new ZafUtil();
        int intCodEmp=0, intCodItm, intCodBod;
        double dblCanMov=0;
        for(int i=0; i<arlDatosItemEgreso.size();i++){
            intCodEmp=objUti.getIntValueAt(arlDatosItemEgreso, i, INT_ARL_COD_EMP);
            intCodItm=objUti.getIntValueAt(arlDatosItemEgreso, i, INT_ARL_COD_ITM);
            intCodBod=objUti.getIntValueAt(arlDatosItemEgreso, i, INT_ARL_COD_BOD_ING_EGR);
            dblCanMov=objUti.getDoubleValueAt(arlDatosItemEgreso, i, INT_ARL_CAN_MOV);
            dblCanMov=Math.abs(dblCanMov);
/*            System.out.println("EMPRESA "+intCodEmp);
            System.out.println("COD ITEM "+intCodItm);
            System.out.println("COD BODEGA "+intCodBod);
            System.out.println("CANTIDAD "+dblCanMov);*/

            if(invStock.actualizaInventario(conIns, intCodEmp, INT_ARL_STK_INV_STK_DIS, "-", 1, obtenerArrItmInv(conIns, intCodEmp, intCodItm,dblCanMov,intCodBod))){
              if(!invStock.actualizaInventario(conIns, intCodEmp, INT_ARL_STK_INV_STK, "-", 0, obtenerArrItmInv(conIns, intCodEmp, intCodItm,dblCanMov,intCodBod))){
                    booRet= false;       
                    break;
              }else{
                if(validaNegativosStock(conIns, intCodEmp, intCodItm, intCodBod)){
                    strErrEspc="Se va a generar negativos en Stock en el item "+intCodItm+" de la empresa "+intCodEmp;
                    booRet=false;
                    break;
                }
              }
            }else{
              strErrEspc="Se va a generar negativos en Stock en el item "+intCodItm+" de la empresa "+intCodEmp;
              booRet= false;
              break;
            }    
            
                
        }
        
        if(booRet){
            for(int i=0; i<arlDatosItemIngreso.size();i++){
                intCodEmp=objUti.getIntValueAt(arlDatosItemIngreso, i, INT_ARL_COD_EMP);
                intCodItm=objUti.getIntValueAt(arlDatosItemIngreso, i, INT_ARL_COD_ITM);
                intCodBod=objUti.getIntValueAt(arlDatosItemIngreso, i, INT_ARL_COD_BOD_ING_EGR);
                dblCanMov=objUti.getDoubleValueAt(arlDatosItemIngreso, i, INT_ARL_CAN_MOV);
                dblCanMov=Math.abs(dblCanMov);
    /*            System.out.println("EMPRESA "+intCodEmp);
                System.out.println("COD ITEM "+intCodItm);
                System.out.println("COD BODEGA "+intCodBod);
                System.out.println("CANTIDAD "+dblCanMov);*/
                if(invStock.actualizaInventario(conIns, intCodEmp, INT_ARL_STK_INV_STK_DIS, "+", 1, obtenerArrItmInv(conIns, intCodEmp, intCodItm,dblCanMov,intCodBod))){
                  if(!invStock.actualizaInventario(conIns, intCodEmp, INT_ARL_STK_INV_STK, "+", 0, obtenerArrItmInv(conIns, intCodEmp, intCodItm,dblCanMov,intCodBod))){
                        booRet= false;
                  }
                }else{
                  booRet= false;
                }    

            } 
        }
        //booRet=true;
        return booRet;
    }
    
    
    private boolean validaNegativosStock(Connection cnx,int intCodEmp, int intCodItm, int intCodBod){
        String strSQL="";
        Statement stmVal=null;
        ResultSet rsValNeg=null;
        boolean booExNeg=false;
        try{
            strSQL=" SELECT a3.nd_stkAct, a3.nd_canDis \n" +
                   " FROM tbm_invBod AS a3 \n" +
                   " WHERE a3.co_emp="+intCodEmp+" AND a3.co_itm="+intCodItm+" AND a3.co_bod="+intCodBod+" \n"+
                   //" AND a2.st_ser='N' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S') AND (a3.nd_stkAct<0 OR a3.nd_canDis<0) \";"
                   " AND (a3.nd_stkAct<0 OR a3.nd_canDis<0) ";
            stmVal=cnx.createStatement();
            rsValNeg=stmVal.executeQuery(strSQL);
            if(rsValNeg.next()){
                booExNeg=true;
            }
            stmVal.close();
            rsValNeg.close();
            stmVal=null;
            rsValNeg=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return booExNeg;
    }
    
/**
     * Función que permite generar el diario del egreso
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDiaEgr(int codigoEmpresaDocumento, int codigoEmpresaCliente, BigDecimal bgdValDoc){
        boolean blnRes=true;
        Vector vecRegDia=new Vector();
        Vector vecDatDia=new Vector();
        try{
//            System.out.println("EGRESO");
//            System.out.println("codigoEmpresaDocumento:" + codigoEmpresaDocumento);
//            System.out.println("codigoEmpresaCliente:" + codigoEmpresaCliente);
            //EMPRESA TUVAL - CLIENTE CASTEK
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==2){//Castek                    
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3548");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
        
            }
            
            //EMPRESA TUVAL - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==4){//Dimulti                   
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3549");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
            }
            
        
            //EMPRESA CASTEK - CLIENTE TUVAL
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==1){//Tuval
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1543");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TRANSFERENCIA RELACIONADA TUVAL S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
            }
    
            //EMPRESA CASTEK - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==4){//Dimulti
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1544");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
            }

            //EMPRESA DIMULTI - CLIENTE TUVAL
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==1){//Tuval
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2553");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TUVAL S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
            }
            
            //EMPRESA DIMULTI - CLIENTE CASTEK
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==2){//Castek
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2552");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
            }
            
            objAsiDia.setDetalleDiario(vecDatDia);
            //System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            
            objAsiDia.setGeneracionDiario((byte)2);
            //System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
        }
        catch(Exception e){
//            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
            strErrEspc=e.getMessage();
        }
        return blnRes;
    }    
    
    
/**
     * Función que permite generar el diario cuando es un ingreso
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDiaIng(int codigoEmpresaDocumento, int codigoEmpresaCliente, BigDecimal bgdValDoc){
        boolean blnRes=true;
        Vector vecRegDia=new Vector();
        Vector vecDatDia=new Vector();

        try{
            //EMPRESA TUVAL - CLIENTE CASTEK
//            System.out.println("INGRESO");
//            System.out.println("codigoEmpresaDocumento:" + codigoEmpresaDocumento);
//            System.out.println("codigoEmpresaCliente:" + codigoEmpresaCliente);
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==2){//Castek                    
                    
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3548");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
        
            }
            
            //EMPRESA TUVAL - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==1){//Tuval
                if(codigoEmpresaCliente==4){//Dimulti                   
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3000");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "3549");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                }
            }
            
        
            //EMPRESA CASTEK - CLIENTE TUVAL
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==1){//Tuval
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1543");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.88");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TUVAL S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
            }
    
            //EMPRESA CASTEK - CLIENTE DIMULTI
            if(codigoEmpresaDocumento==2){//Castek
                if(codigoEmpresaCliente==4){//Dimulti
                    
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1277");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "1544");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DIMULTI S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    

                }
            }

            //EMPRESA DIMULTI - CLIENTE TUVAL
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==1){//Tuval

                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2553");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.89");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "TUVAL S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                }
            }
            
            //EMPRESA DIMULTI - CLIENTE CASTEK
            if(codigoEmpresaDocumento==4){//Dimulti
                if(codigoEmpresaCliente==2){//Castek
                    
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2253");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.06.01.15");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "BODEGA GENERAL");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "2552");//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "1.01.03.01.98");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "CASTEK S.A.");
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdValDoc.abs());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                    
                }
            }
            
            objAsiDia.setDetalleDiario(vecDatDia);
            //System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
            //System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(cmpPad, e);  
            strErrEspc=e.getMessage();
            blnRes=false;
        }
        return blnRes;
    }    
    
    
/**
     * Función que permite realizar la inserción de la relación entre el detalle del egreso y del ingreso
     * @param con: Especifíca la conexión que se desea utilizar
     * @return true: Si se pudo realizar la operación
     * <BR> false: caso contrario
    */
    private boolean inserta_TbrDetMovInv(Connection con){
        boolean blnRes=true;
        int j=0;
        String strSQLIns="",strSQL="";
        Statement stm=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<arlDatTbrDetMovInvEgr.size(); i++){
                    j++;
                    strSQL="";
                    strSQL+=" INSERT INTO tbr_detmovinv(";
                    strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, co_reg,";
                    strSQL+="   co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel";
                    strSQL+=" , st_reg) (SELECT ";
                    strSQL+=" " + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP) + "";//co_emp
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC) + "";//co_loc
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC) + "";//co_tipdoc
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC) + "";//co_doc
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG) + "";//co_reg
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_EMP_REL) + "";//co_emprel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_LOC_REL) + "";//co_locrel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_TIP_DOC_REL) + "";//co_tipdocrel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_DOC_REL) + "";//co_docrel
                    strSQL+="," + objUti.getStringValueAt(arlDatTbrDetMovInvEgr, i, INT_ARL_TBR_DET_MOV_INV_EGR_COD_REG_REL) + "";//co_regrel
                    strSQL+=",'A');";//st_reg
                    strSQLIns+=strSQL;
                }
                //System.out.println("inserta_TbrDetMovInv: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }

        }
        catch(java.sql.SQLException e){
            System.out.println("Error - inserta_TbrDetMovInv SQL: " + e);
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            strErrEspc=e.getMessage();
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - inserta_TbrDetMovInv: " + e);
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            strErrEspc=e.getMessage();
            blnRes=false;
        }
        return blnRes;
    }    



    private boolean generarSQLDocumentoEgreso(Connection con, BigDecimal bgdTotal, int intEmpDes){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 0,bgdTotal, intEmpDes)){
                    if(insertar_tbmDetMovInv(con, 0)){                            
                        if(insertar_tbmPagMovInv(con,bgdTotal)){
//                                if(objCnfDoc.setCamNotIngEgrMerBodega(con, arlDatCnfNumRecEnt)){
                            if(genDiaEgr(intCodEmpEgrGenAsiDia, intEmpDes,bgdTotal)){
                                if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, String.valueOf(intCodDoc), String.valueOf(intNumDocEgre), dtpFecDoc)){
                                    //System.out.println("generarSQLDocumentoEgreso");
                                    blnRes=true;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoEgreso: " + e);
//            objUti.mostrarMsgErr_F1(cmpPad, e);
            strErrEspc=e.getMessage();
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite generar el documento de ingreso/egreso dependiendo de la naturaleza del tipo de documento
     * @return true: Si se pudo realizar la operación
     * <BR> false: Caso contrario
     */
    private boolean generarSQLDocumentoIngreso(Connection con,BigDecimal bgdTotal, int intEmpDes){
        boolean blnRes=false;
        //iniDia();
        try{
            if(con!=null){
                if(insertar_tbmCabMovInv(con, 2, bgdTotal, intEmpDes)){
                    if(actualizar_tbmCabMovInvEgr(con)){
                        if(insertar_tbmDetMovInv(con, 1)){
                            if(insertar_tbmPagMovInv(con,bgdTotal)){
                                if(inserta_TbrDetMovInv(con)){
                                    if(genDiaIng(intEmpDes, intCodEmpEgrGenAsiDia, bgdTotal)){
                                        if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, String.valueOf(intCodDoc), String.valueOf(intNumDocIngr), dtpFecDoc)){
                                            //System.out.println("generarSQLDocumentoIngreso");
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
        catch(Exception e){
            System.out.println("Error - generarSQLDocumentoIngreso: " + e);
//            objUti.mostrarMsgErr_F1(cmpPad, e);
            strErrEspc=e.getMessage();
            blnRes=false;
        }
        return blnRes;
    }    
    

    private int getCodigoItemMae( int intEmp, String strCodItm, Connection cnx){
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strSQL="";
        int intCodTipDoc=0;
        try{
            //conLoc = conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (cnx!=null)
            {
                stmLoc=cnx.createStatement();
                strSQL="";
                strSQL="";
                strSQL+=" SELECT x1.co_itmMae \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intEmp+" and x1.co_itm="+strCodItm+" \n";
                //System.out.println("getCodigoItemMae " +strSQL );
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodTipDoc=rstLoc.getInt("co_itmMae");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                
            }
            //conLoc.close();
            //conLoc=null;
        }
        catch (java.sql.SQLException e){         
            //objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            //objUti.mostrarMsgErr_F1(this, e);       
        }
        return intCodTipDoc;
    }  
    
    private String getUnidadMedidaItem(int intCodEmp,String strCodItem, Connection cnx){
        String strDesUndMed="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strSQL="";             
        try{
            if (cnx != null) {
                stmLoc=cnx.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.co_itm,x3.tx_desCor \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" INNER JOIN tbm_inv as x2 ON (x1.co_emp=x2.co_emp AND x1.co_itm=x2.co_itm)";
                strSQL+=" INNER JOIN tbm_var as x3 ON (x2.co_uni=x3.co_reg)";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+strCodItem+" \n";
                //System.out.println("getNombreItemAlterno " +strSQL );
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                        strDesUndMed=rstLoc.getString("tx_desCor");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(null, e);
            strDesUndMed="-1";
        }
        return strDesUndMed;
    } 
    
    private String getCodItmGrp(int intCodEmp,String strCodItem, Connection cnx){
        String strDesUndMed="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strSQL="";             
        try{
            if (cnx != null) {
                stmLoc=cnx.createStatement();
                strSQL="";
                strSQL+=" select * \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" where co_itmmae=(";
                strSQL+="   select co_itmmae ";
                strSQL+="   from tbm_equInv ";
                strSQL+="   WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+strCodItem+") \n";
                strSQL+=" and co_emp=0";
                //System.out.println("getNombreItemAlterno " +strSQL );
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                        strDesUndMed=rstLoc.getString("tx_desLar");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(null, e);
            strDesUndMed="-1";
        }
        return strDesUndMed;
    }
    
    private boolean getMarUtiMovRel(int tipoOperacion){
        boolean blnRes=false;
        bdeMarUtiComVenRel=BigDecimal.ZERO;
        try{
//            System.out.println("****getMarUtiMovRel ");
//            System.out.println("intCodEmpEgrGenAsiDia: " + intCodEmpEgrGenAsiDia);
//            System.out.println("intCodEmpIngGenAsiDia: " + intCodEmpIngGenAsiDia);
            
            if((tipoOperacion==0) ||  (tipoOperacion==1)){//egreso ||  ingreso
                //VENTA ENTRE RELACIONADAS:
                if( (intCodEmpEgrGenAsiDia==1) && (intCodEmpIngGenAsiDia==2) ){//TUVAL VENDE A CASTEK:
                    bdeMarUtiComVenRel=new BigDecimal("1.02");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==1) && (intCodEmpIngGenAsiDia==4) ){//TUVAL VENDE A DIMULTI:
                    bdeMarUtiComVenRel=new BigDecimal("1.02");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==2) && (intCodEmpIngGenAsiDia==1) ){//CASTEK VENDE A TUVAL:
                    bdeMarUtiComVenRel=new BigDecimal("1.05");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==2) && (intCodEmpIngGenAsiDia==4) ){//CASTEK VENDE A DIMULTI:
                    bdeMarUtiComVenRel=new BigDecimal("1.05");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==4) && (intCodEmpIngGenAsiDia==1) ){//DIMULTI VENDE A TUVAL:
                    bdeMarUtiComVenRel=new BigDecimal("1.03");
                    blnRes=true;
                }
                else if( (intCodEmpEgrGenAsiDia==4) && (intCodEmpIngGenAsiDia==2) ){//DIMULTI VENDE A CASTEK:
                    bdeMarUtiComVenRel=new BigDecimal("1.02");
                    blnRes=true;
                }
            }
            else if(tipoOperacion==2)//transferencia
                blnRes=true;

            //System.out.println("bdeMarUtiComVenRel: " + bdeMarUtiComVenRel);
            
            
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    } 
    
/**
     * 
     * @param codigoEmpresa
     * @param codigoBodegaEmpresa
     * @return 
     */
    private int getCodBodGrp(int codigoEmpresa, int codigoBodegaEmpresa, Connection cnx){
        //Connection conCodBodGrp;
        Statement stmCodBodGrp;
        ResultSet rstCodBodGrp;
        String strCodBodGrp="";
        int intCodBodGrp=-1;
        try{
            //conCodBodGrp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(cnx!=null){
                stmCodBodGrp=cnx.createStatement();
                strCodBodGrp="";
                strCodBodGrp+=" SELECT a2.co_bodGrp";
                strCodBodGrp+=" FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                strCodBodGrp+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strCodBodGrp+=" WHERE a1.co_emp=" + codigoEmpresa + " AND a1.co_bod=" + codigoBodegaEmpresa + "";
                rstCodBodGrp=stmCodBodGrp.executeQuery(strCodBodGrp);
                if(rstCodBodGrp.next())
                    intCodBodGrp=rstCodBodGrp.getInt("co_bodGrp");
                
                /*cnx.close();
                cnx=null;*/
                stmCodBodGrp.close();
                stmCodBodGrp=null;
                rstCodBodGrp.close();
                rstCodBodGrp=null;
            }
        }
        catch(java.sql.SQLException e){
            //objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            //objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return intCodBodGrp;
    }   
    
    private boolean getBodegasTransfer(){
        boolean blnRes=true;
        intCodBodEgr=-1;
        intCodBodIng=-1;
        try{
            if(arlDatItm.size()>0){
                for(int i=0; i<arlDatItm.size();i++){
                    if((i%2)==0){//es impar - egreso
                        if(intCodBodEgr==-1)
                            //intCodBodEgr=Integer.parseInt(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES));
                            intCodBodEgr=15;
                    }
                    if((i%2)!=0){//es par  - ingreso
                        if(intCodBodIng==-1)
                            //intCodBodIng=Integer.parseInt(objUti.getStringValueAt(arlDatItm, i, INT_ARL_COD_BOD_ORI_DES));
                            intCodBodIng=15;
                    }
                    
                    
                }
            }
        }
        catch (Exception e){
            blnRes=false;
            //objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }   

    public ArrayList getArlConDatPreEmpEgr() {
        return arlConDatPreEmpEgr;
    }

    public ArrayList getArlConDatPreEmpIng() {
        return arlConDatPreEmpIng;
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

    public String getStrErrEspc() {
        return strErrEspc;
    }
    

}
