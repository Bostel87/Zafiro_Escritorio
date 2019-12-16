package Librerias.ZafSegMovInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import javax.swing.JInternalFrame;
/**
 *
 * @author sistemas3
 * 
 * versión v0.1.2         07/Dic/2017
 */
public class ZafSegMovInv {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component jifCnt;
    private String strSQL="";
    private Statement stm;
    private ResultSet rst;
    private Object objCodSegInsAnt;//Contiene el código de seguimiento de la instancia anterior
    
    public ZafSegMovInv(ZafParSis obj, java.awt.Component padre){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            jifCnt=padre;
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
    }
    

    /**
     * Función que permite insertar en la tabla de seguimientos
     * @param con Conexión a la base de datos
     * @param codigoEmpresaSolicitud Código de empresa de la solicitud
     * @param codigoLocalSolicitud Código de local de la solicitud
     * @param codigoTipoDocumentoSolicitud Código de tipo de documento de la solicitud
     * @param codigoDocumentoSolicitud Código de documento de la solicitud
     * @param indiceTabla 
     *          <HTML>
     *              <BR>  0: Inserta en tabla "tbm_cabNotPedPreImp"
     *              <BR>  1: Inserta en campo "tbm_cabNotPedImp"
     *              <BR>  2: Inserta en campo "tbm_cabPedEmbImp"
     *              <BR>  3: Inserta en campo "tbm_cabCotVen"
     *              <BR>  4: Inserta en campo "tbm_cabSolTraInv"
     *              <BR>  5: Inserta en campo "tbm_cabMovInv"
     *              <BR>  6: Inserta en campo "tbm_cabGuiRem"
     *              <BR>  7: Inserta en campo "tbm_cabIngEgrMerBod"
     * *            <BR>  8: Inserta en campo "tbm_cabDia"
     * *            <BR>  9: Inserta en campo "tbm_carPagPedEmbImp"
     *          </HTML>
     * @param codigoEmpresa Código de la empresa de la tabla a insertar
     * @param codigoLocal Código de la local de la tabla a insertar
     * @param codigoTipoDocumento Código de tipo de documento de la tabla a insertar
     *      <BR> Cuando es la tabla de Cotizaciones este campo debe enviarse como Null
     * @param codigoDocumento Código de documento de la tabla a insertar
     * @return 
     */
    public boolean setSegMovInvCompra(Connection con
            , int codigoEmpresaSolicitud, int codigoLocalSolicitud, int codigoTipoDocumentoSolicitud, int codigoDocumentoSolicitud
            , int indiceTabla, Object codigoEmpresa, Object codigoLocal, Object codigoTipoDocumento, Object codigoDocumento
            ){
        boolean blnRes=true;
        int intCodSeg=-1;
        int intUltCodReg=0;
        int indTbl=indiceTabla;
        Object objCodEmp=codigoEmpresa;
        Object objCodLoc=codigoLocal;
        Object objCodTipDoc=codigoTipoDocumento;
        Object objCodDoc=codigoDocumento;
        try{
            if(con!=null){
                stm=con.createStatement();                
                strSQL="";
                strSQL+=" SELECT a1.co_seg FROM tbm_cabSegMovInv AS a1 ";
                strSQL+=" WHERE a1.co_emprelcabsoltrainv=" + codigoEmpresaSolicitud + "";
                strSQL+=" AND a1.co_locrelcabsoltrainv=" + codigoLocalSolicitud + "";
                strSQL+=" AND a1.co_tipdocrelcabsoltrainv=" + codigoTipoDocumentoSolicitud + "";
                strSQL+=" AND a1.co_docrelcabsoltrainv=" + codigoDocumentoSolicitud + "";
                strSQL+=" GROUP BY a1.co_seg";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodSeg=rst.getInt("co_seg");
                    
                    strSQL="";
                    strSQL+=" SELECT MAX(co_reg) AS co_reg ";
                    strSQL+=" FROM tbm_cabSegMovInv";
                    strSQL+=" WHERE co_seg=" + intCodSeg + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intUltCodReg=rst.getInt("co_reg");
                    intUltCodReg++;

                    strSQL="";
                    strSQL+=" INSERT INTO tbm_cabSegMovInv(";
                    strSQL+="   co_seg, co_reg, ";
                    strSQL+="   co_emprelcabnotpedpreimp, co_locrelcabnotpedpreimp, co_tipdocrelcabnotpedpreimp, co_docrelcabnotpedpreimp,";//tbm_cabNotPedPreImp - 4
                    strSQL+="   co_emprelcabnotpedimp, co_locrelcabnotpedimp, co_tipdocrelcabnotpedimp, co_docrelcabnotpedimp,";//tbm_cabNotPedImp - 4
                    strSQL+="   co_emprelcabpedembimp, co_locrelcabpedembimp, co_tipdocrelcabpedembimp, co_docrelcabpedembimp,";//tbm_cabPedEmbImp -4 
                    strSQL+="   co_emprelcabcotven, co_locrelcabcotven, co_cotrelcabcotven,";//tbm_cabCotVen - 3
                    strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv,";//tbm_cabSolTraInv -4 
                    strSQL+="   co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv,";//tbm_cabMovInv - 4
                    strSQL+="   co_emprelcabguirem, co_locrelcabguirem, co_tipdocrelcabguirem, co_docrelcabguirem,";//tbm_cabGuiRem - 4
                    strSQL+="   co_emprelcabingegrmerbod, co_locrelcabingegrmerbod, co_tipdocrelcabingegrmerbod, co_docrelcabingegrmerbod";//tbm_cabIngEgrMerBod - 4
                    strSQL+=")";
                    strSQL+="(  SELECT " + intCodSeg + ", " + intUltCodReg + ", ";
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedPreImp - 4
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedImp - 4
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabPedEmbImp -4 
                    strSQL+="   Null, Null, Null,";//tbm_cabCotVen - 3
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabSolTraInv -4 
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabMovInv - 4
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabGuiRem - 4
                    strSQL+="   Null, Null, Null, Null";//tbm_cabIngEgrMerBod - 4
                    strSQL+=")";
                    strSQL+=";";
                    strSQL+=" UPDATE tbm_cabSegMovInv";
                    strSQL+=" SET";
                    switch(indTbl){
                        case 0://tbm_cabNotPedPreImp
                            strSQL+=" co_emprelcabnotpedpreimp=" + objCodEmp + "";
                            strSQL+=", co_locrelcabnotpedpreimp=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabnotpedpreimp=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabnotpedpreimp=" + objCodDoc + "";
                            break;
                        case 1://tbm_cabNotPedImp
                            strSQL+=" co_emprelcabnotpedimp=" + objCodEmp + "";
                            strSQL+=", co_locrelcabnotpedimp=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabnotpedimp=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabnotpedimp=" + objCodDoc + "";
                            break;
                        case 2://tbm_cabPedEmbImp
                            strSQL+=" co_emprelcabpedembimp=" + objCodEmp + "";
                            strSQL+=", co_locrelcabpedembimp=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabpedembimp=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabpedembimp=" + objCodDoc + "";
                            break;
                        case 3://tbm_cabCotVen
                            strSQL+=" co_emprelcabcotven=" + objCodEmp + "";
                            strSQL+=", co_locrelcabcotven=" + objCodLoc + "";
                            strSQL+=", co_cotrelcabcotven=" + objCodDoc + "";
                            break;
                        case 4://tbm_cabSolTraInv
                            strSQL+=" co_emprelcabsoltrainv=" + objCodEmp + "";
                            strSQL+=", co_locrelcabsoltrainv=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabsoltrainv=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabsoltrainv=" + objCodDoc + "";
                            break;
                        case 5://tbm_cabMovInv
                            strSQL+=" co_emprelcabmovinv=" + objCodEmp + "";
                            strSQL+=", co_locrelcabmovinv=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabmovinv=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabmovinv=" + objCodDoc + "";
                            break;
                        case 6://tbm_cabGuiRem
                            strSQL+=" co_emprelcabguirem=" + objCodEmp + "";
                            strSQL+=", co_locrelcabguirem=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabguirem=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabguirem=" + objCodDoc + "";
                            break;
                        case 7://tbm_cabIngEgrMerBod
                            strSQL+=" co_emprelcabingegrmerbod=" + objCodEmp + "";
                            strSQL+=", co_locrelcabingegrmerbod=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabingegrmerbod=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabingegrmerbod=" + objCodDoc + "";
                            break;
//                        case 8://tbm_cabDia
//                            strSQL+=" co_empRelCabDia=" + objCodEmp + "";
//                            strSQL+=", co_locRelCabDia=" + objCodLoc + "";
//                            strSQL+=", co_tipDocRelCabDia=" + objCodTipDoc + "";
//                            strSQL+=", co_diaRelCabDia=" + objCodDoc + "";
//                            break;
//                        case 9://tbm_carPagPedEmbImp
//                            strSQL+=" co_empRelCarPagPedEmbImp=" + objCodEmp + "";
//                            strSQL+=", co_locRelCarPagPedEmbImp=" + objCodLoc + "";
//                            strSQL+=", co_tipDocRelCarPagPedEmbImp=" + objCodTipDoc + "";
//                            strSQL+=", co_docRelCarPagPedEmbImp=" + objCodDoc + "";
//                            break;
                        default://
                            break;
                    }
                    strSQL+=" WHERE co_seg=" + intCodSeg + " AND co_reg=" + intUltCodReg + "";
                    strSQL+=";";
                    stm.executeUpdate(strSQL);
                    rst.close();
                    rst=null;
                }
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - setSegMovInvCompra SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - setSegMovInvCompra: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite insertar en la tabla de seguimientos
     * @param con Conexión a la base de datos
     * @param codigoSeguimiento Código de seguimiento
     * @param indiceTabla 
     *          <HTML>
     *              <BR>  0: Inserta en tabla "tbm_cabNotPedPreImp"
     *              <BR>  1: Inserta en campo "tbm_cabNotPedImp"
     *              <BR>  2: Inserta en campo "tbm_cabPedEmbImp"
     *              <BR>  3: Inserta en campo "tbm_cabCotVen"
     *              <BR>  4: Inserta en campo "tbm_cabSolTraInv"
     *              <BR>  5: Inserta en campo "tbm_cabMovInv"
     *              <BR>  6: Inserta en campo "tbm_cabGuiRem"
     *              <BR>  7: Inserta en campo "tbm_cabIngEgrMerBod"
     *              <BR>  8: Inserta en campo "tbm_cabDia"
     *              <BR>  9: Inserta en campo "tbm_carPagPedEmbImp"
     *          </HTML>
     * @param codigoEmpresa Código de la empresa de la tabla a insertar
     * @param codigoLocal Código de la local de la tabla a insertar
     * @param codigoTipoDocumento Código de tipo de documento de la tabla a insertar
     *      <BR> Cuando es la tabla de Cotizaciones este campo debe enviarse como Null
     * @param codigoDocumento Código de documento de la tabla a insertar
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    public boolean setSegMovInvCompra(Connection con
            , Object codigoSeguimiento, int indiceTabla, Object codigoEmpresa, Object codigoLocal, Object codigoTipoDocumento, Object codigoDocumento
            ){
        boolean blnRes=true;
        Object objCodSeg=codigoSeguimiento;
        int intUltCodReg=0;
        int indTbl=indiceTabla;
        Object objCodEmp=codigoEmpresa;
        Object objCodLoc=codigoLocal;
        Object objCodTipDoc=codigoTipoDocumento;
        Object objCodDoc=codigoDocumento;
        try{
            if(con!=null){
                stm=con.createStatement();
                if(objCodSeg==null){
                    strSQL="";
                    strSQL+=" SELECT (MAX((CASE WHEN co_seg IS NULL THEN 0 ELSE co_seg END))+1) AS co_seg ";
                    strSQL+=" FROM tbm_cabSegMovInv";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        objCodSeg=rst.getObject("co_seg");
                }
                    
                strSQL="";
                strSQL+=" SELECT MAX(co_reg) AS co_reg ";
                strSQL+=" FROM tbm_cabSegMovInv";
                strSQL+=" WHERE co_seg=" + objCodSeg + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltCodReg=rst.getInt("co_reg");
                intUltCodReg++;

                strSQL="";
                strSQL+=" INSERT INTO tbm_cabSegMovInv(";
                strSQL+="   co_seg, co_reg, ";
                strSQL+="   co_emprelcabnotpedpreimp, co_locrelcabnotpedpreimp, co_tipdocrelcabnotpedpreimp, co_docrelcabnotpedpreimp,";//tbm_cabNotPedPreImp - 4
                strSQL+="   co_emprelcabnotpedimp, co_locrelcabnotpedimp, co_tipdocrelcabnotpedimp, co_docrelcabnotpedimp,";//tbm_cabNotPedImp - 4
                strSQL+="   co_emprelcabpedembimp, co_locrelcabpedembimp, co_tipdocrelcabpedembimp, co_docrelcabpedembimp,";//tbm_cabPedEmbImp -4 
                strSQL+="   co_emprelcabcotven, co_locrelcabcotven, co_cotrelcabcotven,";//tbm_cabCotVen - 3
                strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv,";//tbm_cabSolTraInv -4 
                strSQL+="   co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv,";//tbm_cabMovInv - 4
                strSQL+="   co_emprelcabguirem, co_locrelcabguirem, co_tipdocrelcabguirem, co_docrelcabguirem,";//tbm_cabGuiRem - 4
                strSQL+="   co_emprelcabingegrmerbod, co_locrelcabingegrmerbod, co_tipdocrelcabingegrmerbod, co_docrelcabingegrmerbod";//tbm_cabIngEgrMerBod - 4
                strSQL+=")";
                strSQL+="(  SELECT " + objCodSeg + ", " + intUltCodReg + ", ";
                strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedPreImp - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedImp - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabPedEmbImp -4 
                strSQL+="   Null, Null, Null,";//tbm_cabCotVen - 3
                strSQL+="   Null, Null, Null, Null,";//tbm_cabSolTraInv -4 
                strSQL+="   Null, Null, Null, Null,";//tbm_cabMovInv - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabGuiRem - 4
                strSQL+="   Null, Null, Null, Null";//tbm_cabIngEgrMerBod - 4
                strSQL+=")";
                strSQL+=";";
                strSQL+=" UPDATE tbm_cabSegMovInv";
                strSQL+=" SET";
                switch(indTbl){
                    case 0://tbm_cabNotPedPreImp
                        strSQL+=" co_emprelcabnotpedpreimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabnotpedpreimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabnotpedpreimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabnotpedpreimp=" + objCodDoc + "";
                        break;
                    case 1://tbm_cabNotPedImp
                        strSQL+=" co_emprelcabnotpedimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabnotpedimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabnotpedimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabnotpedimp=" + objCodDoc + "";
                        break;
                    case 2://tbm_cabPedEmbImp
                        strSQL+=" co_emprelcabpedembimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabpedembimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabpedembimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabpedembimp=" + objCodDoc + "";
                        break;
                    case 3://tbm_cabCotVen
                        strSQL+=" co_emprelcabcotven=" + objCodEmp + "";
                        strSQL+=", co_locrelcabcotven=" + objCodLoc + "";
                        strSQL+=", co_cotrelcabcotven=" + objCodDoc + "";
                        break;
                    case 4://tbm_cabSolTraInv
                        strSQL+=" co_emprelcabsoltrainv=" + objCodEmp + "";
                        strSQL+=", co_locrelcabsoltrainv=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabsoltrainv=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabsoltrainv=" + objCodDoc + "";
                        break;
                    case 5://tbm_cabMovInv
                        strSQL+=" co_emprelcabmovinv=" + objCodEmp + "";
                        strSQL+=", co_locrelcabmovinv=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabmovinv=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabmovinv=" + objCodDoc + "";
                        break;
                    case 6://tbm_cabGuiRem
                        strSQL+=" co_emprelcabguirem=" + objCodEmp + "";
                        strSQL+=", co_locrelcabguirem=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabguirem=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabguirem=" + objCodDoc + "";
                        break;
                    case 7://tbm_cabIngEgrMerBod
                        strSQL+=" co_emprelcabingegrmerbod=" + objCodEmp + "";
                        strSQL+=", co_locrelcabingegrmerbod=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabingegrmerbod=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabingegrmerbod=" + objCodDoc + "";
                        break;
//                    case 8://tbm_cabDia
//                        strSQL+=" co_empRelCabDia=" + objCodEmp + "";
//                        strSQL+=", co_locRelCabDia=" + objCodLoc + "";
//                        strSQL+=", co_tipDocRelCabDia=" + objCodTipDoc + "";
//                        strSQL+=", co_docRelCabDia=" + objCodDoc + "";
//                        break;
//                    case 9://tbm_carPagPedEmbImp
//                        strSQL+=" co_empRelCarPagPedEmbImp=" + objCodEmp + "";
//                        strSQL+=", co_locRelCarPagPedEmbImp=" + objCodLoc + "";
//                        strSQL+=", co_tipDocRelCarPagPedEmbImp=" + objCodTipDoc + "";
//                        strSQL+=", co_docRelCarPagPedEmbImp=" + objCodDoc + "";
//                        break;                        
                    default://
                        break;
                }
                strSQL+=" WHERE co_seg=" + objCodSeg + " AND co_reg=" + intUltCodReg + "";
                strSQL+=";";
                stm.executeUpdate(strSQL);
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - setSegMovInvCompra SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - setSegMovInvCompra: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   
    
    

    /**
     * Función que permite establecer los campos de tablas principal y tablas relacionadas para ser ingresadas dentro del mismo registro
     * @param con Conexión a la base de datos
     * @param codigoSeguimiento Código de Seguimiento
     * @param indiceTabla Índice de la tabla donde se deben guardar los datos
     * @param codigoEmpresa  Código de empresa que genera el movimiento
     * @param codigoLocal  Código de local que genera el movimiento
     * @param codigoTipoDocumento  Código de tipo de documento que genera el movimiento
     * @param codigoDocumento  Código de documento que genera el movimiento
     * @param indiceTablaRelacionada  Código de empresa relacionada
     * @param codigoEmpresaRelacionada  Código de local relacionada
     * @param codigoLocalRelacionada  Código de tipo de documento relacionada
     * @param codigoTipoDocumentoRelacionada  Código de tipo de documento relacionada
     * @param codigoDocumentoRelacionada  Código de documento relacionada
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    public boolean setSegMovInvCompra(Connection con, Object codigoSeguimiento
            , int indiceTabla, Object codigoEmpresa, Object codigoLocal, Object codigoTipoDocumento, Object codigoDocumento, Object codigoRegistro
            , int indiceTablaRelacionada, Object codigoEmpresaRelacionada, Object codigoLocalRelacionada, Object codigoTipoDocumentoRelacionada, Object codigoDocumentoRelacionada, Object codigoRegistroRelacionada
            ){
        boolean blnRes=true;
        Object objCodSeg=codigoSeguimiento;
        int intUltCodReg=0;
        //tabla 1
        int indTbl=indiceTabla;
        Object objCodEmp=codigoEmpresa;
        Object objCodLoc=codigoLocal;
        Object objCodTipDoc=codigoTipoDocumento;
        Object objCodDoc=codigoDocumento;
        Object objCodReg=codigoRegistro;
        //tabla 2 - relacionada
        int indTblRel=indiceTablaRelacionada;
        Object objCodEmpRel=codigoEmpresaRelacionada;
        Object objCodLocRel=codigoLocalRelacionada;
        Object objCodTipDocRel=codigoTipoDocumentoRelacionada;
        Object objCodDocRel=codigoDocumentoRelacionada;
        Object objCodRegRel=codigoRegistroRelacionada;
        System.out.println("setSegMovInvCompra*******************************");
        try{
            if(con!=null){
                System.out.println("**A");
                stm=con.createStatement();
                if(objCodSeg==null){
                    strSQL="";
                    strSQL+=" SELECT (MAX((CASE WHEN co_seg IS NULL THEN 0 ELSE co_seg END))+1) AS co_seg ";
                    strSQL+=" FROM tbm_cabSegMovInv";
                    System.out.println("SQL - objCodSeg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        objCodSeg=rst.getObject("co_seg");
                    
                    System.out.println("objCodSeg: " + objCodSeg);
                }
                System.out.println("objCodSeg**: " + objCodSeg);
                    
                strSQL="";
                strSQL+=" SELECT MAX(co_reg) AS co_reg ";
                strSQL+=" FROM tbm_cabSegMovInv";
                strSQL+=" WHERE co_seg=" + objCodSeg + "";
                System.out.println("MAX**: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltCodReg=rst.getInt("co_reg");
                intUltCodReg++;

                strSQL="";
                strSQL+=" INSERT INTO tbm_cabSegMovInv(";
                strSQL+="   co_seg, co_reg, ";
                strSQL+="   co_emprelcabnotpedpreimp, co_locrelcabnotpedpreimp, co_tipdocrelcabnotpedpreimp, co_docrelcabnotpedpreimp,";//tbm_cabNotPedPreImp - 4
                strSQL+="   co_emprelcabnotpedimp, co_locrelcabnotpedimp, co_tipdocrelcabnotpedimp, co_docrelcabnotpedimp,";//tbm_cabNotPedImp - 4
                strSQL+="   co_emprelcabpedembimp, co_locrelcabpedembimp, co_tipdocrelcabpedembimp, co_docrelcabpedembimp,";//tbm_cabPedEmbImp -4 
                strSQL+="   co_emprelcabcotven, co_locrelcabcotven, co_cotrelcabcotven,";//tbm_cabCotVen - 3
                strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv,";//tbm_cabSolTraInv -4 
                strSQL+="   co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv,";//tbm_cabMovInv - 4
                strSQL+="   co_emprelcabguirem, co_locrelcabguirem, co_tipdocrelcabguirem, co_docrelcabguirem,";//tbm_cabGuiRem - 4
                strSQL+="   co_emprelcabingegrmerbod, co_locrelcabingegrmerbod, co_tipdocrelcabingegrmerbod, co_docrelcabingegrmerbod";//tbm_cabIngEgrMerBod - 4
                strSQL+=")";
                strSQL+="(  SELECT " + objCodSeg + ", " + intUltCodReg + ", ";
                strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedPreImp - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedImp - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabPedEmbImp -4 
                strSQL+="   Null, Null, Null,";//tbm_cabCotVen - 3
                strSQL+="   Null, Null, Null, Null,";//tbm_cabSolTraInv -4 
                strSQL+="   Null, Null, Null, Null,";//tbm_cabMovInv - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabGuiRem - 4
                strSQL+="   Null, Null, Null, Null";//tbm_cabIngEgrMerBod - 4
                strSQL+=")";
                strSQL+=";";
                strSQL+=" UPDATE tbm_cabSegMovInv";
                strSQL+=" SET";
                switch(indTbl){
                    case 0://tbm_cabNotPedPreImp
                        strSQL+=" co_emprelcabnotpedpreimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabnotpedpreimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabnotpedpreimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabnotpedpreimp=" + objCodDoc + "";
                        break;
                    case 1://tbm_cabNotPedImp
                        strSQL+=" co_emprelcabnotpedimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabnotpedimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabnotpedimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabnotpedimp=" + objCodDoc + "";
                        break;
                    case 2://tbm_cabPedEmbImp
                        strSQL+=" co_emprelcabpedembimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabpedembimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabpedembimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabpedembimp=" + objCodDoc + "";
                        break;
                    case 3://tbm_cabCotVen
                        strSQL+=" co_emprelcabcotven=" + objCodEmp + "";
                        strSQL+=", co_locrelcabcotven=" + objCodLoc + "";
                        strSQL+=", co_cotrelcabcotven=" + objCodDoc + "";
                        break;
                    case 4://tbm_cabSolTraInv
                        strSQL+=" co_emprelcabsoltrainv=" + objCodEmp + "";
                        strSQL+=", co_locrelcabsoltrainv=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabsoltrainv=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabsoltrainv=" + objCodDoc + "";
                        break;
                    case 5://tbm_cabMovInv
                        strSQL+=" co_emprelcabmovinv=" + objCodEmp + "";
                        strSQL+=", co_locrelcabmovinv=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabmovinv=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabmovinv=" + objCodDoc + "";
                        break;
                    case 6://tbm_cabGuiRem
                        strSQL+=" co_emprelcabguirem=" + objCodEmp + "";
                        strSQL+=", co_locrelcabguirem=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabguirem=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabguirem=" + objCodDoc + "";
                        break;
                    case 7://tbm_cabIngEgrMerBod
                        strSQL+=" co_emprelcabingegrmerbod=" + objCodEmp + "";
                        strSQL+=", co_locrelcabingegrmerbod=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabingegrmerbod=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabingegrmerbod=" + objCodDoc + "";
                        break;
                    case 8://tbm_cabDia
                        strSQL+=" co_empRelCabDia=" + objCodEmp + "";
                        strSQL+=", co_locRelCabDia=" + objCodLoc + "";
                        strSQL+=", co_tipDocRelCabDia=" + objCodTipDoc + "";
                        strSQL+=", co_diaRelCabDia=" + objCodDoc + "";
                        break;
                    case 9://tbm_carPagPedEmbImp
                        strSQL+=" co_empRelCarPagPedEmbImp=" + objCodEmp + "";
                        strSQL+=", co_locRelCarPagPedEmbImp=" + objCodLoc + "";
                        strSQL+=", co_tipDocRelCarPagPedEmbImp=" + objCodTipDoc + "";
                        strSQL+=", co_docRelCarPagPedEmbImp=" + objCodDoc + "";
                        strSQL+=", co_regRelCarPagPedEmbImp=" + objCodReg + "";
                        break;                        
                    default://
                        break;
                }
                //relacionadas
                switch(indTblRel){
                    case 0://tbm_cabNotPedPreImp
                        strSQL+=", co_emprelcabnotpedpreimp=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabnotpedpreimp=" + objCodLocRel + "";
                        strSQL+=", co_tipdocrelcabnotpedpreimp=" + objCodTipDocRel + "";
                        strSQL+=", co_docrelcabnotpedpreimp=" + objCodDocRel + "";
                        break;
                    case 1://tbm_cabNotPedImp
                        strSQL+=", co_emprelcabnotpedimp=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabnotpedimp=" + objCodLocRel + "";
                        strSQL+=", co_tipdocrelcabnotpedimp=" + objCodTipDocRel + "";
                        strSQL+=", co_docrelcabnotpedimp=" + objCodDocRel + "";
                        break;
                    case 2://tbm_cabPedEmbImp
                        strSQL+=", co_emprelcabpedembimp=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabpedembimp=" + objCodLocRel + "";
                        strSQL+=", co_tipdocrelcabpedembimp=" + objCodTipDocRel + "";
                        strSQL+=", co_docrelcabpedembimp=" + objCodDocRel + "";
                        break;
                    case 3://tbm_cabCotVen
                        strSQL+=", co_emprelcabcotven=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabcotven=" + objCodLocRel + "";
                        strSQL+=", co_cotrelcabcotven=" + objCodDocRel + "";
                        break;
                    case 4://tbm_cabSolTraInv
                        strSQL+=", co_emprelcabsoltrainv=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabsoltrainv=" + objCodLocRel + "";
                        strSQL+=", co_tipdocrelcabsoltrainv=" + objCodTipDocRel + "";
                        strSQL+=", co_docrelcabsoltrainv=" + objCodDocRel + "";
                        break;
                    case 5://tbm_cabMovInv
                        strSQL+=", co_emprelcabmovinv=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabmovinv=" + objCodLocRel + "";
                        strSQL+=", co_tipdocrelcabmovinv=" + objCodTipDocRel + "";
                        strSQL+=", co_docrelcabmovinv=" + objCodDocRel + "";
                        break;
                    case 6://tbm_cabGuiRem
                        strSQL+=", co_emprelcabguirem=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabguirem=" + objCodLocRel + "";
                        strSQL+=", co_tipdocrelcabguirem=" + objCodTipDocRel + "";
                        strSQL+=", co_docrelcabguirem=" + objCodDocRel + "";
                        break;
                    case 7://tbm_cabIngEgrMerBod
                        strSQL+=", co_emprelcabingegrmerbod=" + objCodEmpRel + "";
                        strSQL+=", co_locrelcabingegrmerbod=" + objCodLocRel + "";
                        strSQL+=", co_tipdocrelcabingegrmerbod=" + objCodTipDocRel + "";
                        strSQL+=", co_docrelcabingegrmerbod=" + objCodDocRel + "";
                        break;
                    case 8://tbm_cabDia
                        strSQL+=", co_empRelCabDia=" + objCodEmpRel + "";
                        strSQL+=", co_locRelCabDia=" + objCodLocRel + "";
                        strSQL+=", co_tipDocRelCabDia=" + objCodTipDocRel + "";
                        strSQL+=", co_diaRelCabDia=" + objCodDocRel + "";
                        break;
                    case 9://tbm_carPagPedEmbImp
                        strSQL+=", co_empRelCarPagPedEmbImp=" + objCodEmpRel + "";
                        strSQL+=", co_locRelCarPagPedEmbImp=" + objCodLocRel + "";
                        strSQL+=", co_tipDocRelCarPagPedEmbImp=" + objCodTipDocRel + "";
                        strSQL+=", co_docRelCarPagPedEmbImp=" + objCodDocRel + "";
                        strSQL+=", co_regRelCarPagPedEmbImp=" + objCodRegRel + "";
                        break;                        
                    default://
                        break;
                }
                strSQL+=" WHERE co_seg=" + objCodSeg + " AND co_reg=" + intUltCodReg + "";
                System.out.println("setSegMovInvCompra: " + strSQL);
                strSQL+=";";
                stm.executeUpdate(strSQL);
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - setSegMovInvCompra SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - setSegMovInvCompra: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido (instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior al Pedido Embarcado(Instancia anterior: Nota de Pedido)
     * <BR> false Caso contrario
     */
    public boolean getCodSegInsAnt(Connection conexion, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        boolean blnRes=false;
        Object objCodSegNotPed=null;
        Object objCodSegPedEmb=null;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT *FROM(";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPed, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" 	FROM (tbm_cabNotPedImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabNotPedImp AND a1.co_loc=a3.co_locRelCabNotPedImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedImp AND a1.co_doc=a3.co_docRelCabNotPedImp)";
                strSQL+=" 	INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" 	, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a2.co_regRel";
                strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	WHERE a1.co_emp=" + codigoEmpresa + "";
                strSQL+=" 	AND a1.co_loc=" + codigoLocal + "";
                strSQL+=" 	AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                strSQL+=" 	AND a1.co_doc=" + codigoDocumento + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_reg=b2.co_regRel";
                System.out.println("getCodSegInsAnt: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegNotPed=rst.getObject("co_segNotPed");
                    objCodSegPedEmb=rst.getObject("co_segPedEmb");
                }
                
                if(objCodSegPedEmb==null){//no existe seguimiento por tanto se debe ingresar 
                    blnRes=true;
                    objCodSegInsAnt=objCodSegNotPed;
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
            System.out.println("Error - getCodSegInsAnt SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - getCodSegInsAnt: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    public Object getObjCodSegInsAnt() {
        return objCodSegInsAnt;
    }
    
    
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido (instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior al Pedido Embarcado(Instancia anterior: Nota de Pedido)
     * <BR> false Caso contrario
     */
    public boolean getCodSegIngImp(Connection conexion, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        boolean blnRes=false;
        Object objCodSegNotPed=null;
        Object objCodSegPedEmb=null;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_segNotPed, b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+=" , b2.co_segPedEmb, b2.co_locRel, b2.co_tipdocRel, b2.co_docRel";
                strSQL+="  FROM(";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPed, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" 	FROM (tbm_cabNotPedImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabNotPedImp AND a1.co_loc=a3.co_locRelCabNotPedImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedImp AND a1.co_doc=a3.co_docRelCabNotPedImp)";
                strSQL+=" 	INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" 	, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a2.co_regRel";
                strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	WHERE a1.co_emp=" + codigoEmpresa + "";
                strSQL+=" 	AND a1.co_loc=" + codigoLocal + "";
                strSQL+=" 	AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                strSQL+=" 	AND a1.co_doc=" + codigoDocumento + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_reg=b2.co_regRel";
                strSQL+=" GROUP BY b1.co_segNotPed, b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+=" , b2.co_segPedEmb, b2.co_locRel, b2.co_tipdocRel, b2.co_docRel";
                System.out.println("getCodSegInsAnt: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegNotPed=rst.getObject("co_segNotPed");
                    objCodSegPedEmb=rst.getObject("co_segPedEmb");
                }
                
                if(objCodSegPedEmb==null){//no existe seguimiento por tanto se debe ingresar 
                    blnRes=true;
                    objCodSegInsAnt=objCodSegNotPed;
                }
                else{//ya existe en el seguimiento la nota de pedido por tanto no se debe hacer nada
                    objCodSegInsAnt=objCodSegPedEmb;
                    blnRes=true;
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - getCodSegInsAnt SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - getCodSegInsAnt: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    
}
