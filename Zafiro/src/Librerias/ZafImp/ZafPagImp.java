/*
 * ZafPagImp.java
 *
 * Created on 08 de Agosto del 2018, 15:09
 */
package Librerias.ZafImp;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
/**
 *
 * @author sistemas 9
 */
public class ZafPagImp
{
    //Constantes
    //ArrayList: Datos de Pagos
    public ArrayList arlDatDatPag, arlRegDatPag;
    public static final int INT_TBL_ARL_PAG_COD_EMP=0;  
    public static final int INT_TBL_ARL_PAG_COD_LOC=1;   
    public static final int INT_TBL_ARL_PAG_COD_TIPDOC=2;  
    public static final int INT_TBL_ARL_PAG_COD_DOC=3;  
    public static final int INT_TBL_ARL_PAG_VAL_DOC=4;  
    public static final int INT_TBL_ARL_PAG_COD_BAN=5;  
    public int intIndiceConPag=0;   
    
    //ArrayList: Datos de Pedido Embarcado
    private ArrayList arlDatDatPedEmb, arlRegDatPedEmb;
    private static final int INT_TBL_ARL_PED_COD_EMP=0;  
    private static final int INT_TBL_ARL_PED_COD_LOC=1;  
    private static final int INT_TBL_ARL_PED_COD_TIPDOC=2;  
    private static final int INT_TBL_ARL_PED_COD_DOC=3;  
    private int intIndicePedEmb=0;       
    
    //ArrayList: Datos de Cuenta de Pedido 
    private ArrayList arlDatCtaPed, arlRegCtaPed;
    private static final int INT_TBL_ARL_CTA_PED_COD_CTA=0;  
    private static final int INT_TBL_ARL_CTA_PED_NUM_CTA=1;  
    private static final int INT_TBL_ARL_CTA_PED_NOM_CTA=2;  
    private static final int INT_TBL_ARL_CTA_PED_VAL_CTA=3;  
    private int intIndiceCtaPed=0;          
    
    //Constantes: Documento de tranferencias bancarias
    private int INT_COD_TIP_DOC_TRS=-1;
    private int INT_COD_DOC_TRS=-1;  
    private int INT_NUM_DOC_TRS=-1;
    
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
    
    //Variables
    private Connection con;
    private ResultSet rst;
    private Statement stm;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private ZafSegMovInv objSegMovInv;
    private java.awt.Component cmpThis;
    private ZafImp objZafImp;
    
    private int INT_COD_EMP_OTR_MOV_BAN;
    private int INT_COD_LOC_OTR_MOV_BAN;
    private int INT_COD_TIP_DOC_OTR_MOV_BAN;
    private int INT_COD_DOC_OTR_MOV_BAN=-1;
    private int INT_NUM_DOC_OTR_MOV_BAN=-1; 
    
    private int intTipErr;
    private int intCodUsr;
    private int intCodCtaBan, intCodCtaIvaImp;
    private String strNumCtaBan, strNumCtaIvaImp;
    private String strNomCtaBan, strNomCtaIvaImp;
    private String strFecDia;
    
    private String strSQL="", strAux="", strGloAsiDia="";
    private String strVer=" v0.2.3";
  
    public ZafPagImp(ZafParSis obj, java.awt.Component padre)
    {
        objParSis=obj;
        cmpThis=padre;
        
        //Inicializar objetos.
        objUti = new ZafUtil();
        objAsiDia = new ZafAsiDia(objParSis);
        objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(cmpThis));        
        //System.out.println("ZafPagImp "+strVer);
    }   
    
    public boolean insertar(Connection conExt, ArrayList arregloDatos) 
    {   
        boolean blnRes=false;
        arlDatDatPag=arregloDatos;
        try
        {
            con=conExt;
            if(con!=null)
            {
                if(configurarMovBan()){
                    if(insertarCabOtrMovBan()){
                        if(insertarDetOtrMovBan()){
                            if(actualizarPagMovInv()){ 
                                if(actualizarCabMovInv()){
                                    if(insertarDiario()){
                                        if(insertarDocGenOtrMovBan()){
                                            if(insertarCarTraBanDia()){
                                                if(insertarCabPag()){
                                                    if(insertarDetPag()){
                                                        if(actualizarCiePagImp()){
                                                            if(insertarCabSegMovInv()){
                                                                blnRes=true;
                                                            }else intTipErr=12;
                                                        }else intTipErr=11;
                                                    }else intTipErr=10;
                                                }else intTipErr=9;
                                            }else intTipErr=8;
                                        } else intTipErr=7;
                                    }else intTipErr=6;
                                } else intTipErr=5;
                            }else intTipErr=4;
                        }else intTipErr=3;
                    }else intTipErr=2;
                } else intTipErr=0;
            }
        }  
        catch(Exception e)  {  objUti.mostrarMsgErr_F1(cmpThis, e);  blnRes=false;  }        
        return blnRes;                  
    }  
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg
     */
    public void mostrarMsgErr()
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit, strMsg="";
        strTit="Mensaje del sistema Zafiro";
        switch(intTipErr){
            case 0:
                strMsg="No se puede realizar el pago de arancel de un Pedido Embarcado que ya tiene Ingreso por Importación.";
                break;      
            case 1:
                strMsg="Error al modificar cargos del Pedido Embarcado.";
                break;   
            case 2:   
                strMsg="Error al generar Transferencia Bancaria: Cabecera.";
                break;
            case 3:
                strMsg="Error al generar Transferencia Bancaria: Detalle.";
                break;
            case 4:
                strMsg="Error al actualizar abono en los pagos del Documento por Pagar de Arancel.";
                break;                    
            case 5:
                strMsg="Error al actualizar usuario y fecha de autorización en Documento por Pagar de Arancel.";
                break;                      
            case 6:
            strMsg="Error al generar Transferencia Bancaria: TRBADA.";
                break;   
            case 7:
                strMsg="Error al generar Relacion de Transferencia Bancaria con Documento por Pagar de Arancel.";
                break; 
            case 8:
                strMsg="Error al generar Carta de Transferencia Bancaria.";
                break;  
            case 9:
                strMsg="Error al generar Pagos: Cabecera.";
                break;  
            case 10:
                strMsg="Error al generar Pagos: Detalle.";
                break;     
            case 11:
                strMsg="Error al realizar el cierre de pagos de impuestos.";
                break;     
            case 12:
                strMsg="Error al relacionar Documento por pagar con transferencia bancaria.";
                break;                     
        }
        oppMsg.showMessageDialog(cmpThis,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }    
    
    /**
     * Función que configura los datos para realizar los pagos de impuestos.
     * @return true: Si se pudo configurar los datos.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarMovBan(){
        boolean blnRes=true;
        try{
            /* Inicializa Variables */
            INT_COD_EMP_OTR_MOV_BAN=objParSis.getCodigoEmpresaGrupo();
            INT_COD_LOC_OTR_MOV_BAN=1;
            INT_COD_TIP_DOC_OTR_MOV_BAN=175; //TRBAGR        
            INT_COD_DOC_OTR_MOV_BAN=-1;
            INT_NUM_DOC_OTR_MOV_BAN=-1;
            intCodUsr=objParSis.getCodigoUsuario();
            vecDatDia = new Vector();
            
            INT_COD_TIP_DOC_TRS=objZafImp.INT_COD_TIP_DOC_TRA_BAN_ARA;
            
            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux!=null)
                strFecDia=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos());              
            
            /* Obtiene pedido embarcado asociado al DxP */
            if(!getDatPedEmb())
                return false;
            
             /* Valida que no exista Ingreso por Importación del Pedido Embarcado */
            for(int i=0; i<arlDatDatPedEmb.size();i++){
                /* Se valida que NO exista ingreso de importación generado, porque los pagos de aranceles solo deben realizarse antes del Ingreso por importación.*/
                if(objZafImp.existeIngresoImportacion( objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_EMP)
                                                     , objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_LOC)
                                                     , objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_TIPDOC)
                                                     , objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_DOC))
                   )
                {
                   return false;            
                }
            }
            

        }
        catch(Exception e)  {  objUti.mostrarMsgErr_F1(cmpThis, e);  blnRes=false;  }
        return blnRes;     
    }
    
    /**
     * Función que obtiene la pk del pedido embarcado del Documento por Pagar (OPIMIM)
     * @return 
     */
    private boolean getDatPedEmb()
    {  
        boolean blnRes=false;    
        try
        {    
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL                
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_carPagPedEmbImp AS a1 ";
                strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a3 ON (a1.co_emp=a3.co_empRelPedEmb AND a1.co_loc=a3.co_locRelPedEmb AND a1.co_tipDoc=a3.co_tipDocRelPedEmb AND a1.co_doc=a3.co_docRelPedEmb AND a1.co_reg=a3.co_regRelPedEmb) ";
                strSQL+=" AND a3.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP) + "";
                strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC) + ""; 
                strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC) + "";
                strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC) + "";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                //System.out.println("getDatPedEmb: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatDatPedEmb = new ArrayList();
                while(rst.next()){   
                    arlRegDatPedEmb = new ArrayList();
                    arlRegDatPedEmb.add(INT_TBL_ARL_PED_COD_EMP, rst.getInt("co_emp"));
                    arlRegDatPedEmb.add(INT_TBL_ARL_PED_COD_LOC, rst.getInt("co_loc"));
                    arlRegDatPedEmb.add(INT_TBL_ARL_PED_COD_TIPDOC, rst.getInt("co_tipDoc"));
                    arlRegDatPedEmb.add(INT_TBL_ARL_PED_COD_DOC, rst.getInt("co_doc"));
                    arlDatDatPedEmb.add(arlRegDatPedEmb);
                    blnRes=true;  
                }  
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }        
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }  
    
    /**
     * Esta función permite insertar la cabecera de Movimientos Bancarios.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabOtrMovBan()
    {
        boolean blnRes=false;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener el último código de documento.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc)+1 as co_doc";
                strSQL+=" FROM tbm_cabOtrMovBan AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_OTR_MOV_BAN;
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_OTR_MOV_BAN;
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_OTR_MOV_BAN;
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    INT_COD_DOC_OTR_MOV_BAN=rst.getInt("co_doc");
                }
                rst.close();
                rst=null;
                
                //Obtener el último número de documento.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.ne_ultDoc)+1 as ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + INT_COD_EMP_OTR_MOV_BAN;
                strSQL+=" AND a1.co_loc=" + INT_COD_LOC_OTR_MOV_BAN;
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_OTR_MOV_BAN;
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    INT_NUM_DOC_OTR_MOV_BAN=rst.getInt("ne_ultDoc");
                }
                rst.close();
                rst=null;                

                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_cabOtrMovBan (";
                strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, co_mnu ";
                strSQL+="   , st_imp, st_pro, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod ";
                strSQL+="   , co_usring, co_usrmod, tx_coming, tx_comultmod, st_regrep) ";
                strSQL+=" SELECT "+INT_COD_EMP_OTR_MOV_BAN+" as co_emp";
                strSQL+="      , "+INT_COD_LOC_OTR_MOV_BAN+" as co_loc";
                strSQL+="      , "+INT_COD_TIP_DOC_OTR_MOV_BAN+" as co_tipDoc";
                strSQL+="      , "+INT_COD_DOC_OTR_MOV_BAN+" as co_doc";
                strSQL+="      , "+INT_NUM_DOC_OTR_MOV_BAN+" as ne_numdoc";
                strSQL+="      , CURRENT_DATE as fe_doc";
                strSQL+="      , a.co_mnu, 'N' as st_imp, 'C' as st_pro, a.tx_obs1, a.tx_obs2, 'A' as st_reg";
                strSQL+="      , CURRENT_TIMESTAMP as fe_ing";
                strSQL+="      , CURRENT_TIMESTAMP as fe_ultMod";
                strSQL+="      , "+intCodUsr+" as co_usring";
                strSQL+="      , "+intCodUsr+" as co_usrmod";
                strSQL+="      , "+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+" as tx_coming";
                strSQL+="      , "+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+" as tx_comultmod";
                strSQL+="      , 'I' as st_regrep";
                strSQL+=" FROM tbm_cabMovInv AS a";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);  
                strSQL+=" AND a.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+=" ;";
                
                //Actualiza el último número de documento.
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + INT_COD_EMP_OTR_MOV_BAN;
                strSQL+=" AND co_loc=" + INT_COD_LOC_OTR_MOV_BAN;
                strSQL+=" AND co_tipDoc=" + INT_COD_TIP_DOC_OTR_MOV_BAN + ";";
                //System.out.println("insertarCabOtrMovBan: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDetOtrMovBan(){
        boolean blnRes=false;
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                // Obtiene Valor del Documento
                BigDecimal bgdValDoc=objUti.getBigDecimalValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_VAL_DOC);
                
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_detotrmovban(";
                strSQL+="      co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tiptra, co_emptra";
                strSQL+="    , co_ctatra, nd_valtra, st_regrep, fe_car, co_ctaBanEgr";
                strSQL+="    , co_ctaBanIng, st_mosParRes, tx_motTra";
                strSQL+=" )";
                strSQL+=" SELECT "+INT_COD_EMP_OTR_MOV_BAN+" AS co_emp";
                strSQL+="      , "+INT_COD_LOC_OTR_MOV_BAN+" AS co_loc";
                strSQL+="      , "+INT_COD_TIP_DOC_OTR_MOV_BAN+" AS co_tipDoc";
                strSQL+="      , "+INT_COD_DOC_OTR_MOV_BAN+" AS co_doc";
                strSQL+="      , "+j+" AS co_Reg";
                strSQL+="      , 'A' AS tx_tiptra";  /*Tipo de transacción: A=Aranceles */
                strSQL+="      , a.co_emp AS co_emptra";
                strSQL+="      , a1.co_ctaAutPag AS co_ctatra";                    
                strSQL+="      , "+bgdValDoc+" AS nd_valtra";     
                strSQL+="      , 'I' AS st_regrep";  
                strSQL+="      , a1.fe_venChqAutPag AS fe_car";  
                strSQL+="      , Null AS co_ctaBanEgr"; 
                strSQL+="      , Null AS co_ctaBanIng"; 
                strSQL+="      , a.tx_ate AS st_mosParRes";
                strSQL+="      , Null AS tx_motTra"; 
                strSQL+=" FROM tbm_cabMovInv AS a";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_Doc";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+=" AND a.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+=" ;";
                //System.out.println("insertarDetOtrMovBan: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar abono en pagos del DxP
     * Actualiza montos de pagos y abonos, etc. Ej: Cheques, etc.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarPagMovInv(){
        boolean blnRes=false;
        try{
            if (con!=null){
                stm=con.createStatement();
        
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_pagMovInv SET nd_abo=nd_abo+x.nd_valDoc";
                strSQL+=" FROM (";
                strSQL+="     SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a1.co_reg, (a.nd_tot*-1) as nd_valDoc";
                strSQL+="     FROM tbm_cabMovInv AS a";
                strSQL+="     INNER JOIN tbm_pagMovInv as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc ";
                strSQL+="     WHERE a.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+="     AND a.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+="     AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+="     AND a.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+="     AND a1.co_reg=1 ";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_pagMovInv.co_emp=x.co_emp AND tbm_pagMovInv.co_loc=x.co_loc";
                strSQL+=" AND tbm_pagMovInv.co_tipDoc=x.co_tipDoc AND tbm_pagMovInv.co_doc=x.co_doc AND tbm_pagMovInv.co_reg=x.co_reg;";
                //System.out.println("actualizarPagMovInv: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }   
    
    /**
     * Esta función permite actualizar fecha y usuario de modificación del DxP
     * Esto sirve para presentar en reporte de asiento de diario quien autoriza DxP.
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabMovInv(){
        boolean blnRes=false;
        try{
            if (con!=null){
                stm=con.createStatement();
        
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabMovInv ";
                strSQL+=" SET co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+="   , fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+="   , tx_comMod="+objUti.codificar(objParSis.getNombreComputadoraConDirIP());
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC);                 
                strSQL+=" ;";        
                //System.out.println("actualizarCabMovInv: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }      
        
    private boolean insertarDiario(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(cargarCtaCon())
                {
                    if(generarAsiDia())
                    {
                        if (objAsiDia.insertarDiario(con, objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP)
                                                        , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC)
                                                        , INT_COD_TIP_DOC_TRS, String.valueOf(INT_NUM_DOC_TRS), objUti.parseDate(strFecDia,objParSis.getFormatoFechaBaseDatos())))  
                        {
                            if(actualizarCabDia()){
                                //System.out.println("insertarDiario");
                                blnRes=true;
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Función carga cuentas contables
     * @return 
     */
    private boolean cargarCtaCon()
    {  
        boolean blnRes=false;  
        intCodCtaBan=0;
        intCodCtaIvaImp=0;
        strNumCtaBan="";
        strNumCtaIvaImp="";
        strNomCtaBan="";
        strNomCtaIvaImp="";
        try
        {    
            if (con!=null){
                stm=con.createStatement();

                /* Cuenta Pedido */
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.co_cta AS co_ctaPed, a5.tx_codCta AS tx_numCtaPed, a5.tx_desLar AS tx_nomCtaPed, a2.nd_valPagPed";
                strSQL+=" FROM tbm_pagMovInv AS a1";
                strSQL+=" INNER JOIN  ( ";  
                strSQL+="     SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a1.co_empRelPedEmb, a1.co_locRelPedEmb, a1.co_tipDocRelPedEmb, a1.co_docRelPedEmb, SUM(a.nd_tot) AS nd_valPagPed";
                strSQL+="     FROM tbm_detConIntMovInv AS a";
                strSQL+="     INNER JOIN tbr_detConIntCarPagInsImp AS a1";
                strSQL+="     ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc AND a.co_reg=a1.co_Reg";
                strSQL+="     INNER JOIN tbm_carPagPedEmbImp AS a2 ON a1.co_empRelPedEmb=a2.co_emp AND a1.co_locRelPedEmb=a2.co_loc AND a1.co_tipDocRelPedEmb=a2.co_tipDoc AND a1.co_docRelPedEmb=a2.co_doc AND a1.co_regRelPedEmb=a2.co_Reg";
                strSQL+="     INNER JOIN tbm_carPagImp AS a3 ON a2.co_carPag=a3.co_CarPag ";
                strSQL+="     WHERE a3.tx_tipCarPag NOT IN ('I') "; /*No IVA*/
                strSQL+="     GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a1.co_empRelPedEmb, a1.co_locRelPedEmb, a1.co_tipDocRelPedEmb, a1.co_docRelPedEmb";
                strSQL+=" ) AS a2";  
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabPedEmbImp AS a4";
                strSQL+=" ON a2.co_empRelPedEmb=a4.co_emp AND a2.co_locRelPedEmb=a4.co_loc AND a2.co_tipDocRelPedEmb=a4.co_tipDoc AND a2.co_docRelPedEmb=a4.co_doc";
                strSQL+=" INNER JOIN tbm_plaCta AS a5 ON a4.co_imp=a5.co_emp AND a4.co_ctaAct=a5.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.co_cta, a5.tx_codCta, a5.tx_desLar, a2.nd_valPagPed";
                //System.out.println("Cuentas Pedido: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCtaPed = new ArrayList();
                while(rst.next()){   
                    arlRegCtaPed = new ArrayList();
                    arlRegCtaPed.add(INT_TBL_ARL_CTA_PED_COD_CTA, rst.getString("co_ctaPed"));
                    arlRegCtaPed.add(INT_TBL_ARL_CTA_PED_NUM_CTA, rst.getString("tx_numCtaPed"));
                    arlRegCtaPed.add(INT_TBL_ARL_CTA_PED_NOM_CTA, rst.getString("tx_nomCtaPed"));
                    arlRegCtaPed.add(INT_TBL_ARL_CTA_PED_VAL_CTA, rst.getString("nd_valPagPed"));
                    arlDatCtaPed.add(arlRegCtaPed);
                }  
                rst.close();
                rst=null;
                
                /* Cuenta Bancos */
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_ctaAutPag AS co_ctaBan, a2.tx_codCta AS tx_numCtaBan, a2.tx_desLar AS tx_nomCtaBan";
                strSQL+=" FROM tbm_pagMovInv AS a1";
                strSQL+=" INNER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp AND a1.co_ctaAutPag=a2.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_ctaAutPag, a2.tx_codCta, a2.tx_desLar";
                //System.out.println("Cuenta Banco: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){   
                    intCodCtaBan=rst.getInt("co_ctaBan");
                    strNumCtaBan=rst.getString("tx_numCtaBan");
                    strNomCtaBan=rst.getString("tx_nomCtaBan");                    
                }  
                else
                    return false;                        
                rst.close();
                rst=null;                

                /* Cuenta de Iva Importaciones */
                intCodCtaIvaImp=objParSis.getCodCtaConImp(objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP)
                                                        , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC)
                                                        , 4, objUti.parseDate(strFecDia, objParSis.getFormatoFechaBaseDatos()) );                    
                strSQL ="";
                strSQL+=" SELECT * FROM(";
                strSQL+="    SELECT a1.co_cta as co_ctaIvaImp, a1.tx_codCta as tx_numCtaIvaImp, a1.tx_desLar as tx_nomCtaIvaImp";
                strSQL+="    FROM tbm_plaCta AS a1  "; 
                strSQL+="    WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+="    AND a1.co_cta="+intCodCtaIvaImp;
                strSQL+="    GROUP BY a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" ) as x ";
                //System.out.println("Cuenta Iva: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){   
                    intCodCtaIvaImp=rst.getInt("co_ctaIvaImp");
                    strNumCtaIvaImp=rst.getString("tx_numCtaIvaImp");
                    strNomCtaIvaImp=rst.getString("tx_nomCtaIvaImp");
                }   
                else
                    return false;                        
                rst.close();
                rst=null;    

                //Código de diario
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_dia)+1 as co_dia";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_TRS;
                //System.out.println("Código Diario: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){   
                    INT_COD_DOC_TRS=rst.getInt("co_dia"); 
                }  
                else
                    return false;
                rst.close();
                rst=null;  

                //Número de diario
                strSQL ="";
                strSQL+=" SELECT MAX(a1.ne_ultDoc)+1 as ne_numDia";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_TRS;
                //System.out.println("Numero Diario: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){   
                    INT_NUM_DOC_TRS=rst.getInt("ne_numDia");
                    INT_NUM_DOC_TRS++;
                }    
                else
                    return false;                        
                rst.close();
                rst=null;

                //Glosa
                strSQL ="";
                strSQL+=" SELECT a1.tx_glo";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);
                strSQL+=" AND a1.co_dia=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){   
                    strGloAsiDia=rst.getString("tx_glo");
                }    
                rst.close();
                rst=null;    
                blnRes=true;                
            }
                    
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }        
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }        
    
    /**
     * Genera Asiento de Diario del documento "Pago Impuestos"
     * @return 
     */
    private boolean generarAsiDia()
    {  
        boolean blnRes=false;
        ResultSet rstLoc;
        Statement stmLoc;
        BigDecimal bgdValDoc=BigDecimal.ZERO;
        BigDecimal bgdValIva=BigDecimal.ZERO;
        BigDecimal bgdValSub=BigDecimal.ZERO;
        try
        {
            /* Obtiene valores para asiento de diario*/
            // Obtiene Valor del Documento
            bgdValDoc=objUti.getBigDecimalValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_VAL_DOC);
            
            // Obtiene Valor del IVA 
            stmLoc=con.createStatement();
            strSQL ="";
            strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a5.tx_tipCarPag, a2.tx_des as tx_nomImp, SUM(a2.nd_tot) AS nd_ValIva";
            strSQL+=" FROM tbm_cabMovInv AS a1";
            strSQL+=" INNER JOIN tbm_detConIntMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a3";
            strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_Reg";
            strSQL+=" INNER JOIN tbm_carPagPedEmbImp AS a4 ON a3.co_empRelPedEmb=a4.co_emp AND a3.co_locRelPedEmb=a4.co_loc AND a3.co_tipDocRelPedEmb=a4.co_tipDoc AND a3.co_docRelPedEmb=a4.co_Doc AND a3.co_RegRelPedEmb=a4.co_Reg";
            strSQL+=" INNER JOIN tbm_carPagImp AS a5 ON (a4.co_carPag=a5.co_carPag) ";        
            strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
            strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
            strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);
            strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
            strSQL+=" AND a5.tx_tipCarPag='I' /* Solo cargos de Tipo Iva, van a la cuenta de IVA */";
            strSQL+=" AND a1.st_reg NOT IN('E') ";
            strSQL+=" GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a5.tx_tipCarPag,  a2.tx_des";
            //System.out.println("getValorIVA: " + strSQL);
            rstLoc=stmLoc.executeQuery(strSQL);
            if(rstLoc.next()){  
                bgdValIva=objUti.redondearBigDecimal( (rstLoc.getBigDecimal("nd_ValIva")==null?BigDecimal.ZERO:rstLoc.getBigDecimal("nd_ValIva")) , objParSis.getDecimalesBaseDatos());
            }  
            rstLoc.close();
            rstLoc=null;   
            stmLoc.close();
            stmLoc=null;              
            
            /* Genera Asiento de Diario */
            if( bgdValDoc.compareTo(BigDecimal.ZERO)>0 )
            {
                //Se inicializa el asiento de diario
                objAsiDia.inicializar();
                vecDatDia.clear();
                
                //Debe: Pedido
                for(int i=0; i<arlDatCtaPed.size();i++)
                {
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" +  objUti.getStringValueAt(arlDatCtaPed, i, INT_TBL_ARL_CTA_PED_COD_CTA));
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" +  objUti.getStringValueAt(arlDatCtaPed, i, INT_TBL_ARL_CTA_PED_NUM_CTA));
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" +  objUti.getStringValueAt(arlDatCtaPed, i, INT_TBL_ARL_CTA_PED_NOM_CTA));
                    vecRegDia.add(INT_VEC_DIA_DEB,     "" +  objUti.getStringValueAt(arlDatCtaPed, i, INT_TBL_ARL_CTA_PED_VAL_CTA));
                    vecRegDia.add(INT_VEC_DIA_HAB,     "0");
                    vecRegDia.add(INT_VEC_DIA_REF,     "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);                
                }

                //Debe: Iva
                if( bgdValIva.compareTo(BigDecimal.ZERO)>0 )  
                {   /*Solo se genera asiento de diario con cuenta Iva, cuando se paga el impuesto del iva*/
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaIvaImp);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaIvaImp);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaIvaImp);
                    vecRegDia.add(INT_VEC_DIA_DEB,     ""+ bgdValIva );
                    vecRegDia.add(INT_VEC_DIA_HAB,     "0");
                    vecRegDia.add(INT_VEC_DIA_REF,     "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);                        
                }

                //Haber: Bancos
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaBan);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaBan);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaBan);
                vecRegDia.add(INT_VEC_DIA_DEB,     "0" );
                vecRegDia.add(INT_VEC_DIA_HAB,     ""+ bgdValDoc);
                vecRegDia.add(INT_VEC_DIA_REF,     "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //System.out.println("asiento diario: "+vecDatDia.toString());
                blnRes=true;                
            }
            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)2);
            objAsiDia.setGlosa(strGloAsiDia);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar fecha y usuario de modificación del TRBADA
     * Esto sirve para presentar en reporte de asiento de diario quien autoriza DxP.
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabDia(){
        boolean blnRes=false;
        try{
            if (con!=null){
                stm=con.createStatement();
        
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabDia ";
                strSQL+=" SET co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+=" , fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND co_tipDoc=" + INT_COD_TIP_DOC_TRS;      
                strSQL+=" AND tx_numDia='" + String.valueOf(INT_NUM_DOC_TRS)+"' ";                 
                strSQL+=" ;";        
                //System.out.println("actualizarCabDia: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }       
    
    private boolean insertarDocGenOtrMovBan(){
        boolean blnRes=false;
        int j=1, intCodSec=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                //Obtiene el último código de secuencia.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_sec)+1 AS co_sec";
                strSQL+=" FROM tbm_docGenOtrMovBan AS a1";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodSec=rst.getInt("co_sec");
                rst.close();
                rst=null;
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" INSERT INTO tbm_docGenOtrMovBan(";
                strSQL+="       co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_sec, co_emprelcabmovinv ";
                strSQL+="     , co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv ";
                strSQL+="     , co_emprelcabpag, co_locrelcabpag, co_tipdocrelcabpag, co_docrelcabpag ";
                strSQL+="     , co_emprelcabdia, co_locrelcabdia, co_tipdocrelcabdia, co_docrelcabdia, st_regrep)";
                strSQL+=" VALUES (";
                strSQL+="  " + INT_COD_EMP_OTR_MOV_BAN;       //co_emp
                strSQL+=", " + INT_COD_LOC_OTR_MOV_BAN;       //co_loc
                strSQL+=", " + INT_COD_TIP_DOC_OTR_MOV_BAN; //co_tipdoc
                strSQL+=", " + INT_COD_DOC_OTR_MOV_BAN; //co_doc
                strSQL+=", " + j;                  //co_reg
                strSQL+=", " + intCodSec;          //co_sec
                strSQL+=", " + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP) + "";    //co_emprelcabmovinv
                strSQL+=", " + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC) + "";    //co_locrelcabmovinv
                strSQL+=", " + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC) + ""; //co_tipdocrelcabmovinv
                strSQL+=", " + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC) + "";    //co_docrelcabmovinv
                strSQL+=", Null"; //co_emprelcabpag
                strSQL+=", Null"; //co_locrelcabpag
                strSQL+=", Null"; //co_tipdocrelcabpag
                strSQL+=", Null"; //co_docrelcabpag
                strSQL+=", " + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP) + "";    //co_emprelcabdia
                strSQL+=", " + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC) + "";    //co_locrelcabdia
                strSQL+=", " + INT_COD_TIP_DOC_TRS + ""; //co_tipdocrelcabdia
                strSQL+=", " + INT_COD_DOC_TRS + "";     //co_docrelcabdia
                strSQL+=", 'I'"; //st_regrep
                strSQL+=");";
                //System.out.println("insertarDocGenOtrMovBan: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }            
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }  
    
    private boolean insertarCarTraBanDia(){ 
        boolean blnRes=false;
        String strValPal="";
        try{
            if(con!=null){
                stm=con.createStatement();
                //Obtiene
                strSQL ="";
                strSQL+=" SELECT abs(a.nd_valCar) AS nd_valtra";
                strSQL+=" FROM tbm_cabMovInv AS a";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+=" AND a.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strValPal= objUti.codificar(getValorPalabras(rst.getString("nd_valtra"))); //tx_valtrapal
                }
                rst.close();
                rst=null;

                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_carTraBanDia(";
                strSQL+="      co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_ctabanegr, co_ctabaning";
                strSQL+="     , fe_car, nd_valtra, tx_valtrapal, st_mosparresp";
                strSQL+=" )";
                strSQL+=" SELECT "+objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP)+" AS co_emp";
                strSQL+="      , "+objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC)+" AS co_loc";
                strSQL+="      , "+INT_COD_TIP_DOC_TRS+" AS co_tipDoc";
                strSQL+="      , "+INT_COD_DOC_TRS+" AS co_dia";
                strSQL+="      , 1 AS co_reg";
                strSQL+="      , a1.co_ctaban AS co_ctabanegr";
                strSQL+="      , a1.co_ctaban AS co_ctabaning";
                strSQL+="      , a5.fe_venChqAutPag AS fe_car";  
                strSQL+="      , abs(a.nd_valCar) AS nd_valtra";   //nd_ValCar
                strSQL+="      , "+strValPal+" AS tx_valtrapal";   //Cantidad en palabras.
                //strSQL+="      , CAST(' ' AS CHARACTER VARYING) AS tx_valtrapal";    
                strSQL+="      , a.tx_ate AS st_mosParRes";
                strSQL+=" FROM tbm_ctaban AS a1 INNER JOIN tbm_var AS a2 ON (a1.co_ban=a2.co_reg AND a2.co_grp=8)";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaPrvLoc=a3.co_cli AND a3.st_reg NOT IN('E','I')  )";
                strSQL+=" LEFT OUTER JOIN tbm_expImp AS a4 ON (a1.co_ctaExp=a4.co_exp AND a4.st_reg  NOT IN('E','I')  )";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a ON a1.co_emp=a.co_emp AND a3.co_cli=a.co_cli"; 
                strSQL+=" INNER JOIN tbm_pagMovInv AS a5 ON a.co_emp=a5.co_emp AND a.co_loc=a5.co_loc AND a.co_tipDoc=a5.co_tipDoc AND a.co_doc=a5.co_doc AND a1.co_cta=a5.co_ctaAutPag";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+=" AND a.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+=" ;";                
                //System.out.println("insertarCarTraBanDia: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }          
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }      
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabPag()
    {
        boolean blnRes=false;
        BigDecimal bgdValDoc=BigDecimal.ZERO;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();              
                //Obtener Valor del Documento
                bgdValDoc=objUti.getBigDecimalValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_VAL_DOC);

                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_cabPag (";
                strSQL+="     co_emp, co_loc, co_tipDoc, co_doc, ne_numDoc1, ne_numDoc2";
                strSQL+="   , fe_doc, nd_monDoc, co_mnu, st_imp, st_regRep, st_Reg";
                strSQL+="   , co_usrIng, fe_ing, tx_comIng)";
                strSQL+=" SELECT a.co_emp";
                strSQL+="      , a.co_loc";
                strSQL+="      , "+INT_COD_TIP_DOC_TRS+" as co_tipDoc";
                strSQL+="      , "+INT_COD_DOC_TRS+" as co_doc";
                strSQL+="      , "+INT_NUM_DOC_TRS+" as ne_numDoc1";
                strSQL+="      , "+INT_NUM_DOC_TRS+" as ne_numDoc2";
                strSQL+="      , CURRENT_DATE as fe_doc";
                strSQL+="      , ("+bgdValDoc+"*-1) as nd_monDoc";
                strSQL+="      , a.co_mnu";
                strSQL+="      , 'N' as st_imp";
                strSQL+="      , 'I' AS st_regRep";
                strSQL+="      , 'A' as st_reg";  
                strSQL+="      , "+intCodUsr+" as co_usring";
                strSQL+="      , CURRENT_TIMESTAMP as fe_ing";
                strSQL+="      , "+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+" as tx_coming";
                strSQL+=" FROM tbm_cabMovInv AS a";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);  
                strSQL+=" AND a.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+=" ;";
                //System.out.println("insertarCabPag: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDetPag(){
        boolean blnRes=false;
        BigDecimal bgdValDoc=BigDecimal.ZERO;
        try{
            if (con!=null){
                stm=con.createStatement();

                //Obtener Valor del Documento
                bgdValDoc=objUti.getBigDecimalValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_VAL_DOC);                
        
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" INSERT INTO tbm_detPag(";
                strSQL+="      co_emp, co_loc, co_tipDoc, co_doc, co_Reg";
                strSQL+="    , co_locPag, co_tipDocPag, co_DocPag, co_RegPag";
                strSQL+="    , nd_abo, st_regRep, st_Reg";
                strSQL+=" )";
                strSQL+=" SELECT a.co_emp";
                strSQL+="      , a.co_loc";
                strSQL+="      , "+INT_COD_TIP_DOC_TRS+" as co_tipDoc";
                strSQL+="      , "+INT_COD_DOC_TRS+" as co_doc";
                strSQL+="      , a1.co_reg";
                strSQL+="      , a.co_loc as co_locPag";
                strSQL+="      , a.co_tipDoc as co_tipDocPag";
                strSQL+="      , a.co_doc as co_docPag";
                strSQL+="      , a1.co_reg as co_regPag";
                strSQL+="      , ("+bgdValDoc+"*-1) as nd_abo";
                strSQL+="      , 'I' as st_regrep";  
                strSQL+="      , 'A' as st_Reg";  
                strSQL+=" FROM tbm_cabMovInv AS a";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_Doc";
                strSQL+=" WHERE a.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP);
                strSQL+=" AND a.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC);      
                strSQL+=" AND a.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC); 
                strSQL+=" ;";
                //System.out.println("insertarDetPag: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }       
    
    /**
     * Esta función permite actualizar el cierre de pagos de impuestos de importaciones.
     * El cierre de pago del pedido se va a realizar cuando se autoriza el pago.
     * @return true: Si se pudo actualizar el cierre de pagos.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCiePagImp(){
        boolean blnRes=false;
        try{
            if (con!=null){
                for(int i=0; i<arlDatDatPedEmb.size();i++){
                    //Armar la sentencia SQL.
                    stm=con.createStatement();
                    strSQL ="";
                    strSQL+=" UPDATE tbm_cabPedEmbImp SET st_ciePagImp='S'";
                    strSQL+=" , fe_ciePagImp=" + objParSis.getFuncionFechaHoraBaseDatos() + "";
                    strSQL+=" , co_usrCiePagImp=" + objParSis.getCodigoUsuario();                    
                    strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_EMP);
                    strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_LOC);
                    strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_TIPDOC);
                    strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatDatPedEmb, i, INT_TBL_ARL_PED_COD_DOC);
                    strSQL+=" ;";        
                    //System.out.println("actualizarCiePagImp ("+i+"): "+strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;                    
                }
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e)   { 
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }        
    
    /**
     * Función que permite guardar relaciones en el seguimiento.
     * Relación Documento por Pagar con TRBADA.
     * @return 
     */
    private boolean insertarCabSegMovInv(){
        boolean blnRes=false;
        Object objCodSeg=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                strSQL="";
                strSQL+=" SELECT *FROM(";
                strSQL+=" 	SELECT a3.co_seg AS co_segPagImp, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+="         ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+=" 	      AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv)";
                strSQL+=" 	WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP) + "";
                strSQL+=" 	AND a1.co_loc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC) + "";
                strSQL+=" 	AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC) + "";
                strSQL+=" 	AND a1.co_doc=" + objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC) + "";
                strSQL+=" ) AS b1";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSeg=rst.getObject("co_segPagImp");  //Ya existe seguimiento del pedido embarcado.
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;                
                
                objSegMovInv=new ZafSegMovInv(objParSis, cmpThis);
                //Insertar en Seguimiento - Relación: Transferencia Bancaria vs Documento por pagar
                if(objSegMovInv.setSegMovInvCompra(con, objCodSeg 
                                                   ,  8
                                                   , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP)
                                                   , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC)
                                                   , INT_COD_TIP_DOC_TRS, INT_COD_DOC_TRS, "Null"
                                                   , 5
                                                   , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_EMP)
                                                   , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_LOC)
                                                   , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_TIPDOC)
                                                   , objUti.getIntValueAt(arlDatDatPag, intIndiceConPag, INT_TBL_ARL_PAG_COD_DOC) , "Null"
                   ))                
                {   
                    blnRes=true;
                } 
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    private String getValorPalabras(String valorConvertir){
        String valPal="";
        try{
            Librerias.ZafUtil.ZafNumLetra numero;
            double cantidad= objUti.redondear(valorConvertir, objParSis.getDecimalesMostrar());
            String decimales=String.valueOf(cantidad).toString();
            decimales=decimales.substring(decimales.indexOf('.') + 1);
            decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
            int deci= Integer.parseInt(decimales);
            int m_pesos=(int)cantidad;
            numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
            String res = numero.convertirLetras(m_pesos);
            res = res+" "+decimales+"/100  DOLARES";
            res=res.toUpperCase();
            valPal=res;
            numero=null;
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(cmpThis, e);   }
        return valPal;
    } 
    
    /**
     * Función que valida si ya existe un Pago de Arancel (Impuesto) del pedido Embarcado.
     * Valida todos los documentos por pagar de aranceles, sea que estén autorizados o no.
     * @param codigoEmpresaPedidoEmbarcado
     * @param codigoLocalPedidoEmbarcado
     * @param codigoTipoDocumentoPedidoEmbarcado
     * @param codigoDocumentoPedidoEmbarcado
     * @return true: Si existe Pago Arancel
     * <BR> false: Caso contrario.
     */
    public boolean existePagoArancel(int codigoEmpresaPedidoEmbarcado, int codigoLocalPedidoEmbarcado
                                      , int codigoTipoDocumentoPedidoEmbarcado, int codigoDocumentoPedidoEmbarcado) {
        boolean blnRes=false;
        Connection conPagAra;
        Statement stmPagAra;
        ResultSet rstPagAra;
        try{
            conPagAra=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPagAra!=null){
                stmPagAra=conPagAra.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabMovInv AS a1"; 
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc AND a3.co_reg=a4.co_reg)";
                strSQL+=" INNER JOIN tbm_cabPedEmbImp AS a7 ON (a4.co_empRelPedEmb=a7.co_emp AND a4.co_locRelPedEmb=a7.co_loc AND a4.co_tipDocRelPedEmb=a7.co_tipDoc AND a4.co_docRelPedEmb=a7.co_doc)";
                strSQL+=" WHERE a1.st_reg NOT IN ('E', 'I')";
                strSQL+=" AND a1.co_tipDoc IN ("+objZafImp.INT_COD_TIP_DOC_PAG_IMP+")"; //Valida tipos de documentos de impuestos.
                strSQL+=" AND a7.co_emp=" + codigoEmpresaPedidoEmbarcado;
                strSQL+=" AND a7.co_loc=" + codigoLocalPedidoEmbarcado;
                strSQL+=" AND a7.co_tipDoc=" + codigoTipoDocumentoPedidoEmbarcado;
                strSQL+=" AND a7.co_doc=" + codigoDocumentoPedidoEmbarcado;
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc"; 
                //System.out.println("existePagoArancel: " + strSQL);
                rstPagAra=stmPagAra.executeQuery(strSQL);
                if(rstPagAra.next())
                    blnRes=true;

                conPagAra.close();
                conPagAra=null;
                stmPagAra.close();
                stmPagAra=null;
                rstPagAra.close();
                rstPagAra=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=true;
        }
        return blnRes;
    }            
        
    /**
     * Función que valida si ya existe una transferencia bancaria por arancel de importación del pedido Embarcado.
     * @param codigoEmpresaPedidoEmbarcado
     * @param codigoLocalPedidoEmbarcado
     * @param codigoTipoDocumentoPedidoEmbarcado
     * @param codigoDocumentoPedidoEmbarcado
     * @return true: Si existe Ingreso por Importación
     * <BR> false: Caso contrario.
     */
    public boolean existeTransferenciaBancariaArancel(int codigoEmpresaPedidoEmbarcado, int codigoLocalPedidoEmbarcado
                                                    , int codigoTipoDocumentoPedidoEmbarcado, int codigoDocumentoPedidoEmbarcado) {
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
                strSQL+="      , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a4.co_empRelPedEmb, a4.co_locRelPedEmb, a4.co_tipDocRelPedEmb, a4.co_docRelPedEmb";     
                strSQL+=" FROM tbm_cabDia as a";
                strSQL+=" INNER JOIN tbm_docGenOtrMovBan AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)";
                strSQL+=" WHERE a.st_reg NOT IN ('E', 'I')";
                strSQL+=" AND a4.co_empRelPedEmb="+codigoEmpresaPedidoEmbarcado;
                strSQL+=" AND a4.co_locRelPedEmb="+codigoLocalPedidoEmbarcado;
                strSQL+=" AND a4.co_tipDocRelPedEmb="+codigoTipoDocumentoPedidoEmbarcado;
                strSQL+=" AND a4.co_docRelPedEmb="+codigoDocumentoPedidoEmbarcado;
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia";   
                strSQL+="        , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc";  
                strSQL+="        , a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a4.co_empRelPedEmb, a4.co_locRelPedEmb, a4.co_tipDocRelPedEmb, a4.co_docRelPedEmb";
                //System.out.println("existeTransferenciaBancariaArancel: " + strSQL);
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
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=true;
        }
        return blnRes;
    }      

    /**
     * Función que valida si ya existe un cierre de pagos de impuestos del pedido Embarcado.
     * @param codigoEmpresaPedidoEmbarcado
     * @param codigoLocalPedidoEmbarcado
     * @param codigoTipoDocumentoPedidoEmbarcado
     * @param codigoDocumentoPedidoEmbarcado
     * @return true: Si existe Ingreso por Importación
     * <BR> false: Caso contrario.
     */
    public boolean existeCierrePagoImpuestos(int codigoEmpresaPedidoEmbarcado, int codigoLocalPedidoEmbarcado
                                           , int codigoTipoDocumentoPedidoEmbarcado, int codigoDocumentoPedidoEmbarcado) {
        boolean blnRes=false;
        Connection conPagImp;
        Statement stmPagImp;
        ResultSet rstPagImp;
        try{
            conPagImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPagImp!=null){
                stmPagImp=conPagImp.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.st_ciePagImp";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1"; 
                strSQL+=" WHERE a1.st_reg NOT IN ('E', 'I')";
                strSQL+=" AND (CASE WHEN a1.st_ciePagImp IS NULL THEN 'P' ELSE a1.st_ciePagImp END) NOT IN ('N')"; //Muestra los pedidos cerrados de pagos de impuestos.
                strSQL+=" AND a1.co_emp=" + codigoEmpresaPedidoEmbarcado; 
                strSQL+=" AND a1.co_loc=" + codigoLocalPedidoEmbarcado;
                strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumentoPedidoEmbarcado;
                strSQL+=" AND a1.co_doc=" + codigoDocumentoPedidoEmbarcado;
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.st_ciePagImp";                
                //System.out.println("existeCierrePagoImpuestos: " + strSQL);
                rstPagImp=stmPagImp.executeQuery(strSQL);
                if(rstPagImp.next())
                    blnRes=true;

                conPagImp.close();
                conPagImp=null;
                stmPagImp.close();
                stmPagImp=null;
                rstPagImp.close();
                rstPagImp=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpThis, e);
            blnRes=true;
        }
        return blnRes;
    } 
    



}