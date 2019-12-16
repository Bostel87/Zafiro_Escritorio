/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase que sirve para las confirmaciones de Egreso Automatico.
 * @author CheMateo
 */
public class ZafCnfEgrDAO {
    
    private ZafUtil objUti=new ZafUtil();
    /**
     * Metodo que obtiene las ordenes de despacho que tienen solo items S o items Terminal L
     * @param fecDesdeItemsServ
     * @param intCodBodGrp  Codigo de la Bodega del Grupo
     * @param objZafCon Objeto de conexion.
     * @param intCodEmpOD Codigo de la Empresa Orden de Despacho.
     * @param inCodLocOD Codigo del local de la Orden de Despacho.
     * @param intCodTipDocOD Codigo del Tipo de Documento Orden de despacho.
     * @param intCodDocOD Codigo de la orden de despacho.
     * @return 
     */
    public ResultSet obtenerODCnfEgrAut(Connection objZafCon,String fecDesdeItemsServ,int intCodBodGrp, int intCodEmpOD, int intCodLocOD, int intCodTipDocOD, int intCodDocOD){
        
        //ZafCon objZafCon;
        String strSql;
        ResultSet rstLoc=null;
        Statement stmLoc;
        
        try{
            //objZafCon=new ZafCon();
            
            stmLoc=objZafCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
            strSql = " SELECT * FROM ( "
            + " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_ptopar as cobod, a.fe_doc, "
            + "        ( select count(x1.st_ser) "
            + "          from tbm_detguirem as x "
            + "          inner join tbm_inv as x1 "
            + "          on (x1.co_emp=x.co_emp and x1.co_itm=x.co_itm ) "
            + "          where x.co_emp=a.co_emp "
            + "          and x.co_loc=a.co_loc "
            + "          and x.co_tipdoc=a.co_tipdoc "
            + "          and x.co_doc=a.co_doc "
            + "          and x1.st_Ser in ('N', 'S') ) as exiitmNoSer "
            + " from tbm_cabguirem as a "
            + " INNER JOIN tbr_bodEmpBodGrp AS a6 "
            + " ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
            + " WHERE ( a6.co_empGrp=0 AND a6.co_bodGrp = "+intCodBodGrp+" ) "
            + " and a.ne_numdoc = 0 "
            + " and a.ne_numorddes > 0 "
            + " and a.st_coninv='F' " //indica que no tiene ingreso - egreso de bodega
            + " and a.st_reg='A' "
            + " and a.st_tieguisec='N' "
            + " ) as x "
            + " where exiitmNoSer > 0 "
            + " and x.co_emp="+intCodEmpOD //
            + " and x.co_loc="+intCodLocOD
            + " and x.co_tipdoc="+intCodTipDocOD
            + " and x.co_doc="+intCodDocOD
            + " and fe_doc > '" + fecDesdeItemsServ+"'" ;
            rstLoc=stmLoc.executeQuery(strSql);
     
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rstLoc;        
    }
    
    
    /**
     * Obtiene el maximo codigo de documento
     * @param intCodEmpG codigo de empresa
     * @param intCodLocG codigo de local
     * @param intTipDocConfEgr tipo de documento
     * @param objZafCon conexion 
     * @return int ultimo documento
     */
    public int obtenerMaxConfEgr(int intCodEmpG, int intCodLocG, int intTipDocConfEgr, Connection objZafCon ){
        int intMax=0;
        ResultSet rstLoc=null;
        Statement stmLoc;
        try{
            stmLoc= objZafCon.createStatement();
            String strSql="SELECT case when (Max(co_doc)+1) is null then 1 else Max(co_doc)+1 end as co_doc  FROM tbm_cabingegrmerbod WHERE " +
            " co_emp="+intCodEmpG+" and co_loc="+intCodLocG+" "+
            " and co_tipDoc="+intTipDocConfEgr;
            rstLoc = stmLoc.executeQuery(strSql);
            if(rstLoc.next()){
                intMax=rstLoc.getInt("co_doc");
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intMax;
    }
    
    
    /**
     * Obtiene el ultimo numero de documento
     * @param intCodEmpG codigo de empresa 
     * @param intCodLocG codigo de local
     * @param intTipDocConfEgr codigo de tipo de documento
     * @param objZafCon conexion
     * @return  int con el ultimo numero de documento
     */
    public int obtenerUltDoc(int intCodEmpG, int intCodLocG, int intTipDocConfEgr, Connection objZafCon){
        
        int intRetUltDoc=0;
        ResultSet rstLoc;
        Statement stmLoc;        
        try{
            stmLoc=objZafCon.createStatement();
            String strSql="SELECT CASE WHEN (ne_ultdoc+1) is null THEN 1 ELSE ne_ultdoc+1 END AS numDoc FROM tbm_cabtipdoc " +
            " WHERE co_emp="+intCodEmpG+" and co_loc="+intCodLocG+" and co_tipdoc="+intTipDocConfEgr;
            rstLoc = stmLoc.executeQuery(strSql);
            if(rstLoc.next())
                intRetUltDoc = rstLoc.getInt("numDoc");
            rstLoc.close();
            rstLoc=null;    
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return intRetUltDoc;
    }

    
    /**
     * Metodo que inserta los datos de la cabecera de la confirmacion y tablas relacionadas.
     * @param objZafCon conexion
     * @param intCodEmpG codigo de empresa de la OD.
     * @param intCodLocG codigo de local de la OD.
     * @param intCodTipDocG codigo del tipo de documento de la OD.
     * @param intCodDocG codigo del documento OD.
     * @param intCodBodG codigo de la bodega.
     * @param intCodTipDocC codigo de tipo de documento confirmacion. 
     * @param intCodDocC codigo de documento confirmacion.
     * @param intNumDocC numero de documento de la confirmacion.
     * @return boolean si tuvo exito o no la operacion.
     */
    public boolean ingCabConfEgr(Connection objZafCon, int intCodEmpG, int intCodLocG, int intCodTipDocG, int intCodDocG, int intCodBodG,  int intCodTipDocC, int intCodDocC, int intNumDocC ){
        boolean booRes=true;
        java.sql.Statement stmLoc;
        String strSql="";
        try{
           if(objZafCon!=null){
               stmLoc=objZafCon.createStatement();

               strSql="INSERT INTO tbm_cabingegrmerbod( co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, " +
               " co_locrelguirem, co_tipdocrelguirem, co_docrelguirem, co_mnu, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, co_usring , tx_tipCon,tx_idetra,tx_nomtra) "+
               " VALUES( "+intCodEmpG+", "+intCodLocG+", "+intCodTipDocC+", "+intCodDocC+" " +
               " ,current_date, "+intNumDocC+", "+intCodBodG+", " +
               " "+intCodLocG+", "+intCodTipDocG+", "+intCodDocG+", "+
               " 2205, 'N', 'Confirmacion Automatica por el Sistema.', 'Esto se realiza para casos donde todos los items son terminales L  ', "+
               " 'A',  current_timestamp, 1, 'T'," +(intCodEmpG==1?"'0990281866001'":(intCodEmpG==2)?"'0992329432001'":(intCodEmpG==4)?"'0992372427001'":"")+","+(intCodEmpG==1?"'TUVAL S.A.'":(intCodEmpG==2)?"'CASTEK S.A.'":(intCodEmpG==4)?"'DIMULTI S.A.'":"")+")";

               strSql+=" ; UPDATE tbm_cabguirem SET st_tieguisec='S'  WHERE co_emp="+intCodEmpG+" " +
               " AND co_loc="+intCodLocG+" AND co_tipdoc="+intCodTipDocG+" and co_doc="+intCodDocG+" ";

               strSql+=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocC+" WHERE co_emp="+intCodEmpG+" " +
               " AND co_loc="+intCodLocG+" AND co_tipdoc="+intCodTipDocC;
               stmLoc.execute(strSql);

             stmLoc.close();
             stmLoc=null;
             

            }
        }catch(java.sql.SQLException ex) { 
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
            booRes=false;
            
        }
        return booRes;
    }    
    
    /**
     * Metod que inserta los registros de los detalles de la confirmacion.
     * @param objZafCon objeto conexion.
     * @param intCodEmpG codigo de la empresa de la confirmacion.
     * @param intCodLocG codigo del local de la confirmacion.
     * @param intCodTipDocC codigo del tipo de documento de la confirmacion.
     * @param intCodDocC codigo de documento de la confirmacion.
     * @param intCodTipDocG codigo de tipo de documento de OD.
     * @param intCodDocG codigo de documento de la OD.
     * @param intCodBodG codigo de la bodega de confirmacion.
     * @param rstLoc Resultset con los detalles de la orden a confirmar.
     * @return boolean indica si la operacion tuvo exito.
     */
    public boolean insertarDetConfEgr(Connection objZafCon,int intCodEmpG, int intCodLocG, int intCodTipDocC, int intCodDocC, int intCodTipDocG,int intCodDocG,int intCodBodG,ResultSet rstLoc){
        boolean booRet=true;
        String strSql="";
        Statement stmUpd=null;
        try{
            stmUpd=objZafCon.createStatement();
            while(rstLoc.next()){
                strSql+="INSERT INTO tbm_detingegrmerbod"
               + "( co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrelguirem, co_tipdocrelguirem, "+
                " co_docrelguirem, co_regrelguirem, co_itm, co_bod,  nd_can, tx_obs1, nd_cannunrec ) "+
                " VALUES("+intCodEmpG+", "+intCodLocG+", "+intCodTipDocC+", "+intCodDocC+" " +
                " ,"+rstLoc.getInt("co_reg")+", "+intCodLocG+", "+intCodTipDocG+", "+intCodDocG+", "+
                " "+rstLoc.getInt("co_reg")+",  "+rstLoc.getInt("co_itm")+", "+intCodBodG+"  " +
                " ,0, '', 0 ) ";

                strSql+=" ; UPDATE tbm_detguirem SET co_usrCon=1 "
                +" ,nd_cantotguisec = case when nd_cantotguisec is null then 0 else nd_cantotguisec end + "+rstLoc.getString("cangui")+"  "
                +" WHERE  co_emp="+intCodEmpG+" and co_loc="+intCodLocG+" " +
                " and co_tipdoc="+intCodTipDocG+" and co_doc="+intCodDocG+" "+
                " and co_reg="+rstLoc.getInt("co_reg")+";";
            }
            stmUpd.execute(strSql);
            stmUpd.close();
            stmUpd=null;            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRet;
    }
    
    
    /**
     * Metodo que obtiene los detalles de la orden de despacho.
     * @param intCodEmpG codigo de la empresa de la OD.
     * @param intCodLocG codigo del local de la OD.
     * @param intCodTipDocG codigo del tipo de documento de la OD.
     * @param intCodDocG codigo del documento de la OD.
     * @param objZafCon conexion acceso a datos.
     * @return 
     */
    public ResultSet obtenerDetDespConf(int intCodEmpG, int intCodLocG, int intCodTipDocG,int intCodDocG,Connection objZafCon){    
        ResultSet rstRetDet=null;
        String strSql="";
        Statement stmDet=null;
        try{
            stmDet=objZafCon.createStatement();
            strSql="SELECT a.co_reg, a.co_itm, abs(a.nd_can) as cangui "
            + "FROM tbm_detguirem as a"
            + " inner join tbm_inv as a1 "
            + "on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm )  "
            + " where a.co_emp="+intCodEmpG+" "
            + "and a.co_loc="+intCodLocG+" and a.co_tipdoc="+intCodTipDocG+" and a.co_doc="+intCodDocG+" and a1.st_Ser in ('N','S') ";
            rstRetDet=stmDet.executeQuery(strSql);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rstRetDet;    
    }
    
}
