/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafGenDocCobAut.ZafGenDocCobAut;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * esta es una clase implementada en el lado del cliente para 
 * la creación de una nueva factura generada por la devolución de mercadería autorizada
 * previamente
 * @param oi es un parametro de entrada, es un objeto de la clase ZafImmresion
 * @author sistemas4
 */
public class ZafGenFac {

        private ZafImp oi;
        //ZafCon oc=new ZafCon();
        Connection cnx;
        ResultSet rs;
        Statement sql;
        String query="";
        
	private Statement stmSql;
	private ResultSet rstCon;
	private String strQue="";
        private ZafFac objZafFac=new ZafFac();
        private ZafImp objZafImp=new ZafImp();
        private List<ZafFac>lstZafFac=new ArrayList<ZafFac>();
        private List<BigDecimal> lstBigTot=new ArrayList<BigDecimal>();
        StringBuffer strInsDetCabMovInv=new StringBuffer("");
        int intCodMotDoc, intCodTipPerEmp;
        HashMap<String, BigDecimal> hasValTot=new HashMap<String, BigDecimal>();
        HashMap<String, Double> hasTot=new HashMap<String, Double>();
        HashMap<String, BigDecimal> hasTotBig=new HashMap<String, BigDecimal>();
        List<ZafPagFac> lstZafPagFac=new ArrayList<ZafPagFac>();
        int intCodMotBien,intCodMotServ,intCodMotTran;
        
        
        private static final String SUBTOTAL ="subtotal";
        private static final String TOTALIVA ="totaliva";
        private static final String TOTALFACTURA ="totalfactura";
        private static final String TOTALDESCUENTO ="totaldescuento";
        private static final String TOTALCONFLETE ="totalconflete";
        private static final String TOTALSINFLETE ="totalsinflete";
        private static final String CODIGONUEVAFACTURA="codigodocfacnew";
        
        /*CMATEO Variables agregadas para manejo descuadre en volver a facturar*/
        private ZafUtil objUti=new ZafUtil();
        private double bldivaEmp=0d;
        private BigDecimal bgdIvaEmp=BigDecimal.ZERO;
        private double bldCmpSol=0d;
        private BigDecimal bgdPorCmpSol=BigDecimal.ZERO;
        private ZafParSis objZafParSis;
        private double dblRetFueGlo=0.0,dblRetIvaGlo=0.0;
        private BigDecimal bgdRetFueGlo=BigDecimal.ZERO,bgdRetIvaGlo=BigDecimal.ZERO;
        /*CMATEO Variables agregadas para manejo descuadre en volver a facturar*/
        
	private final String strVersion = " v0.3";
        
        final int INT_ARL_STK_INV_STK_DIS=10;  // nd_stkDisp
        final int INT_ARL_STK_INV_STK=0;  // nd_stk
        
    /**
     *
     * @param oi
     * 
     */
    /*public ZafGenFac(ZafImp oi) {
        this.setObjZafImp(oi);
        
    }
    
    public ZafGenFac(Connection conn){
        cnx=conn;
    }*/
        
     public ZafGenFac(ZafImp oi, Connection conn, ZafParSis obj) {
        try{
            this.setObjZafImp(oi);
            cnx=conn;               
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            cargarTipEmp(oi.getEmp(), oi.getLoc(),cnx);
            
            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/
            
            /*if(objZafImp.getEmp()==2 && objZafImp.getLoc()==4){
                bldivaEmp=12.00;
            }else{*/
            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/            
                bldivaEmp=objZafParSis.getPorcentajeIvaVentas().doubleValue();
                bgdIvaEmp=objZafParSis.getPorcentajeIvaVentas();
                Calendar calFec=Calendar.getInstance();
                BigDecimal bigCmpSol=objZafParSis.getPorcentajeCmpSolVentas(obj.getCodigoEmpresa(), obj.getCodigoLocal(),calFec.getTime());
                
                bldCmpSol=(bigCmpSol!=null && bigCmpSol.compareTo(BigDecimal.ZERO)>0)?bigCmpSol.doubleValue():0.0d;
                bgdPorCmpSol=(bigCmpSol!=null && bigCmpSol.compareTo(BigDecimal.ZERO)>0)?bigCmpSol:BigDecimal.ZERO;
                
            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/                
            //}
            /*AGREGADO POR CAMBIO DE COMPENSACION SOLIDARIA*/            
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
    }
    
       /**
        * Metodo para la asignación de un nuevo número de la factura a 
        * generarse por la devolución de la venta, selecciona el último documento 
        * le agrea 1, trae este dato desde tbm_cabtipdoc la cabecera de tipos de documentos.
        * luego actualiza nuevamente esta tabla con el numero secuencial generado
        * 
        */
     public void asignaNumeroNuevaFactura(){
    
        int numDoc=0;
        int numDocGuia=0;
        try {
            
            sql=cnx.createStatement();
            query="SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc, tx_descor "
                  + " FROM tbm_cabTipDoc WHERE co_emp="+oi.getEmp()+" and co_loc="+oi.getLoc()+" and co_tipDoc ="+oi.getTipdoc();
            rs=sql.executeQuery(query);
            if (rs.next()) {
                numDoc=rs.getInt("ultnum");
            }
            rs.close();
            sql.close();
            
            query="UPDATE tbm_cabTipDoc SET ne_ultDoc="+numDoc+" WHERE co_emp="+oi.getEmp()+" and co_loc="+oi.getLoc()+" and co_tipDoc ="+oi.getTipdoc();
            sql.executeUpdate(query);
            sql.close();
            
            query=" UPDATE tbm_cabmovinv SET co_mnu=14,  ne_numdoc="+numDoc+",ne_numgui="+numDocGuia+",st_reg='A', st_imp='S' "
            + " WHERE co_emp="+oi.getEmp()+" and co_loc="+oi.getLoc()+" and co_tipDoc ="+oi.getTipdoc()+" and co_doc="+oi.getCoDoc();
            query+=" ; UPDATE tbm_cabdia SET tx_numdia='"+numDoc+"' WHERE co_emp="+oi.getEmp()+" and co_loc="+oi.getLoc()+" and co_tipDoc ="+oi.getTipdoc()+" and co_dia="+oi.getCoDoc();
            sql.executeUpdate(query);
            sql.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
    }//fin de asigna numero de factura
     
    
     /**
      * Método para para establecer el motivo de la retención, en este caso el tipo B es para la compra de un bien, tiene codigo 1
      * para todas las empresas revisadas hasta el 29/09/2014
      */
     private int formaRetencion(){
     
         int cod = 0;
         try {
             sql=cnx.createStatement();
             query="SELECT co_mot FROM tbm_motdoc WHERE co_emp="+oi.getEmp()+" AND tx_tipmot='B'";
             rs=sql.executeQuery(query);
             if (rs.next()) {
                 cod=rs.getInt("co_mot");
             }
             sql.close();
             rs.close();
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
     
         return cod;
     }//fin de forma retencion
     
     
    /**
     * Metodo que obtiene los codigos de motivos de retenciones
     * @param conTmp
     * @return 
     */ 
    private boolean formaRetencionAct(java.sql.Connection conTmp) {
        boolean blnRes = false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try {
            if (conTmp != null) {
                stmTmp = conTmp.createStatement();
                String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND tx_tipmot in ('B','S','T') ";
                rst = stmTmp.executeQuery(sql);
                while (rst.next()) {
                    //intCodMotDoc = rst.getInt(1);
                    if (rst.getString("tx_tipmot").equals("B")) {
                        intCodMotBien = rst.getInt("co_mot");
                    } else if (rst.getString("tx_tipmot").equals("S")) {
                        intCodMotServ = rst.getInt("co_mot");
                    } else if (rst.getString("tx_tipmot").equals("T")) {
                        intCodMotTran = rst.getInt("co_mot");
                    }
                    blnRes = true;
                }
                rst.close();
                stmTmp.close();
                rst = null;
                stmTmp = null;
            }
        } catch (SQLException evt) {
            //objUti.mostrarMsgErr_F1(jfrThis, Evt);
            evt.printStackTrace();
        } catch (Exception evt) {
            //objUti.mostrarMsgErr_F1(jfrThis, Evt);
            evt.printStackTrace();
        }
        return blnRes;
    }    
     
     
     /**
      * Función que retorna  el codigo del tipo de empresa, por ejemplo, si es Natural o Jurídica, 
      * o Contribuyente especial
      */
     
     private int cargaTipoEmp(){
     int cod=0;
     
         try {
             sql=cnx.createStatement();
             query="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, a1.tx_dir, a1.tx_nom as nombod  FROM  tbm_emp as a " +
            " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
            " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+oi.getLoc()+" and bod.st_reg='P')  " +
            " inner join tbm_bod as a1 on (a1.co_emp=bod.co_emp and a1.co_bod=bod.co_bod ) " +
            " where a.co_emp="+oi.getEmp();
             
             rs=sql.executeQuery(query);
             
             if (rs.next()) {
                 cod=rs.getInt("co_tipper");
             }
             
             sql.close();
             rs.close();
             
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         
     return cod;
     }//fin de carga tipo empresa
     
     
     /**
      * Método para el cálculo del total de nueva factura, sobre los items que el cliente solicitó 
      * que se incluyan en dicha transacción
      */
     
     private void calculaTotal(){
     
         BigDecimal p,c,s,su,t,po,d;
         BigDecimal i=new BigDecimal(0.12);
         BigDecimal b=new BigDecimal(100);
         List<BigDecimal>mitotal=new ArrayList<BigDecimal>();
         try {
             sql=cnx.createStatement();
             query="select nd_preuni,nd_candev,nd_pordes,co_itm from tbm_detsoldevven where co_docrel="+oi.getCoDoc()+" and nd_candev not in('0') and co_emp="+oi.getEmp() ;
             rs=sql.executeQuery(query);
             while (rs.next()) {
                 p=new BigDecimal(rs.getDouble("nd_preuni"));
                 c=new BigDecimal(rs.getDouble("nd_candev"));
                 po=new BigDecimal(rs.getDouble("nd_pordes"));
                 
                 s=p.multiply(c).setScale(2, BigDecimal.ROUND_HALF_UP);
                 d=s.multiply(po).divide(b).setScale(2, BigDecimal.ROUND_HALF_UP);
                 su=s.subtract(d).setScale(2, BigDecimal.ROUND_HALF_UP);
                 i=su.multiply(i).setScale(2, BigDecimal.ROUND_HALF_UP);
                 t=su.add(i).setScale(2, BigDecimal.ROUND_HALF_UP);
                 mitotal.add(t);
                 i=new BigDecimal(0.12).setScale(2, BigDecimal.ROUND_HALF_UP);
             }//fin de while
             
             sql.close();
             rs.close();
             System.out.println(mitotal);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
     
     }//fin de calculo
     
     private boolean verificaStock(){
     
         boolean res=false;
     
         try {
             sql=cnx.createStatement();
//             query="select nd_stkact from tbm_inv where co_itm="+oi.+" and co_emp=1";
             
         } catch (Exception e) {
         }
         
         
         return res;
     }//fin de verifica
     
     //<editor-fold defaultstate="collapsed" desc="GETTERS $ SETTERS">
     public ZafImp getOi() {
         return oi;
     }
     
     public void setOi(ZafImp oi) {
         this.oi = oi;
     }
     //</editor-fold>
    
    
    
     
	public int traeCoDoc(int numfac, int intCodEmp, int intCodLoc, int intCodTipDoc, Connection conx){
            int coDoc=0;

            try {
                    stmSql=conx.createStatement();
                    //strQue="select co_doc from tbm_cabmovinv where ne_numdoc="+objZafFac.getNe_numDoc()+" and co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
                    strQue="select co_doc from tbm_cabmovinv where ne_numdoc="+numfac+" and co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
                    rstCon=stmSql.executeQuery(strQue);
                    if (rstCon.next()) {
                            coDoc=rstCon.getInt("co_doc");
                    }

                    stmSql.close();
                    rstCon.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return coDoc;
	}
        
	private int traeCodDev(){
		
            int coDoc=0;

            try {
                    stmSql=cnx.createStatement();
                    strQue="select co_doc from tbm_cabsoldevven where co_docrel ="+objZafImp.getCoDoc();
                    if (rstCon.next()) {
                            coDoc=rstCon.getInt("co_doc");
                    }
                    stmSql.close();
                    rstCon.close();
            } catch (Exception e) {
                    System.out.println(e.getMessage());
            }

            return coDoc;
	}
        
	/**
	 * <h1>FUNCION PARA LA EXTRACCION DE DATOS DE LA FACTURA</h1>
	 * <P>esta función permite la extracción de los datos de la factura que se afecto con la devolución
	 * uso los mismos datos para la nueva factura <Strong>traigo la misma forma de pago</Strong></P>
	 */
            public void traerDat(Connection cnx){

                try {
                        stmSql=cnx.createStatement();//dd
                        strQue="select a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc,a.ne_numcot,"
                                        + "a.co_cli,a.tx_ruc,a.tx_nomcli,a.tx_dircli,a.tx_telcli,a.tx_ciucli,"
                                        + "a.co_com,a.tx_nomven,a.tx_ate,a.co_forpag,tx_desforpag,a.nd_poriva,"
                                        + "a.st_reg,a.co_forret,a.co_mnu,a.st_tipdev,a.st_regrep,a.st_imp,a.tx_vehret,a.co_mnu, "
                                        /*direccion del cliente volver a facturar*/
                                        + " b.tx_dirclivolfac, b.co_clivolfac, a.tx_choret,"
                                        + " c.tx_nom , d.tx_nom as nombre_volver_facturar, c.tx_ide, d.tx_ide as ide_volver_facturar,"
                                        /*direccion del cliente volver a facturar*/
                                        + " a.tx_ate, b.co_doc as  docsol,"
                                        + " b.co_emp as empsol, b.co_loc as locsol,b.co_tipdoc as tipsol, a.ne_numdoc, "
                                        
                                        /*CAMBIO PARA QUE TOME BIEN EL TIPO DE PERSONA DE EL NUEVO CLIENTE EN VOLVER A FACTURAR*/
                                        //+ " c.co_tipper, a3.ne_tipforpag, "
                                        + " c.co_tipper, a3.ne_tipforpag, d.co_tipper as tipo_persona_nuevo, "
                                        /*CAMBIO PARA QUE TOME BIEN EL TIPO DE PERSONA DE EL NUEVO CLIENTE EN VOLVER A FACTURAR*/
                                
                                        /*Pagos retenciones */
                                        + " b.co_locrel, b.co_emp as empsol "
                                        /*Pagos retenciones */
                                        /*Cambio del 25 julio 2019*/
                                        + " , a.tx_numped, a.tx_obs1 "
                                        /*Cambio del 25 julio 2019*/
                                        + " from tbm_cabmovinv a"
                                        + " inner join tbm_cabsoldevven b "
                                        //+ " on a.co_doc=b.co_docrel and a.co_loc=b.co_locrel and a.co_tipdoc=b.co_tipdocrel"                                
										+ " on a.co_emp=b.co_emp and a.co_doc=b.co_docrel and a.co_loc=b.co_locrel and a.co_tipdoc=b.co_tipdocrel"                                
                                        /*Join con clientes*/
                                        + " inner join tbm_cli c "
                                        + " on b.co_emp=c.co_emp and b.co_cli=c.co_cli "
                                        /*Join con clientes*/
                                
                                        /*Join con clientes volver a facturar*/
                                        + " left join tbm_cli d  "
                                        + " on d.co_emp=b.co_emp "
                                        + " and d.co_cli=b.co_clivolfac"
                                        /*Join con clientes volver a facturar*/
                                
                                        /*join con formas de pago*/
                                        + " left join tbm_cabforpag a3 "
                                        + " on (a3.co_emp=a.co_emp and a3.co_forpag=a.co_forpag) "
                                        /*join con formas de pago*/
                                        + " where b.st_aut='A' and a.co_doc="+objZafImp.getCoDoc()
                                        // 11/10/2014 se agrega para filtrar por pk de factura
                                        + " and a.co_tipdoc= "+objZafImp.getTipdoc()
                                        + " and a.co_loc= "+objZafImp.getLoc()
                                        + " and a.co_emp= "+objZafImp.getEmp()
                                        + " and b.st_volfacmersindev='S'"
                                        + " and b.st_meraceingsis='S'"
                                        + " and b.st_mersindevfac='N' "
                                        + " and b.st_reg='A' and b.st_tipdev='C'";  
                        
                                        //11/10/2014 se agrega para filtrar por pk de factura

                        rstCon=stmSql.executeQuery(strQue);
                        //System.out.println(query);
                        while (rstCon.next()) {

                                objZafFac.setCo_emp(rstCon.getInt("co_emp"));
                                objZafFac.setCo_loc(rstCon.getInt("co_loc"));
                                objZafFac.setCo_tipdoc(rstCon.getInt("co_tipdoc"));
                                objZafFac.setCo_doc(rstCon.getInt("co_doc")); 
                                objZafFac.setNe_numCot(rstCon.getInt("ne_numCot"));
                                
                                /*Direccion de volver a facturar*/
                                objZafFac.setCo_cli((rstCon.getInt("co_clivolfac")>0)?rstCon.getInt("co_clivolfac"):rstCon.getInt("co_cli"));                                
                                objZafFac.setTx_ruc((rstCon.getString("ide_volver_facturar")!=null && rstCon.getString("ide_volver_facturar").length() > 0)?rstCon.getString("ide_volver_facturar"):rstCon.getString("tx_ruc"));
                                //objZafFac.setTx_nomCli((rstCon.getString("nombre_volver_facturar")!=null && rstCon.getString("nombre_volver_facturar").length()>0)?rstCon.getString("nombre_volver_facturar"):rstCon.getString("tx_nomCli"));                                
                                objZafFac.setTx_nomCli((rstCon.getString("nombre_volver_facturar")!=null && rstCon.getString("nombre_volver_facturar").length()>0)?rstCon.getString("nombre_volver_facturar"):rstCon.getString("tx_nom"));                                
                                objZafFac.setTx_dirCli((rstCon.getString("tx_dirclivolfac")!=null && rstCon.getString("tx_dirclivolfac").length()>0)?rstCon.getString("tx_dirclivolfac"):rstCon.getString("tx_dirCli"));
                                /*Direccion de volver a facturar*/
                                
                                objZafFac.setTx_telCli(rstCon.getString("tx_telCli"));
                                objZafFac.setTx_ciuCli(rstCon.getString("tx_ciuCli"));
                                objZafFac.setCo_com(rstCon.getInt("co_com"));
                                objZafFac.setTx_nomven(rstCon.getString("tx_nomven"));
                                objZafFac.setTx_ate(rstCon.getString("tx_ate"));
                                objZafFac.setCo_forPag(rstCon.getInt("co_forPag"));
                                objZafFac.setTx_desforpag(rstCon.getString("tx_desforpag"));
                                objZafFac.setNd_porIva(rstCon.getDouble("nd_porIva"));
                                objZafFac.setSt_reg(rstCon.getString("st_reg"));
                                objZafFac.setCo_forret(rstCon.getInt("co_forret"));objZafFac.setCo_mnu(rstCon.getInt("co_mnu"));
                                objZafFac.setSt_regrep("st_regrep");objZafFac.setSt_imp(rstCon.getString("st_imp"));objZafFac.setTx_vehret(rstCon.getString("tx_vehret"));objZafFac.setCo_mnu(rstCon.getInt("co_mnu"));


                                /*Agregado para obtener los campos de relacion de la solicitud de devolucion*/
                                objZafFac.setIntCodEmpSolDev(rstCon.getInt("empsol")); objZafFac.setIntCodTipDocSolDev(rstCon.getInt("tipsol")); objZafFac.setIntCodLocSolDev(rstCon.getInt("locsol")); objZafFac.setIntCodDocSolDev(rstCon.getInt("docsol"));objZafFac.setNe_numDoc(rstCon.getInt("ne_numdoc"));
                                /*Agregado para obtener los campos de relacion de la solicitud de devolucion*/
                                /*Agregado para obtener el tipo de Persona del cliente*/
                                //objZafFac.setIntTipPer(rstCon.getInt("co_tipper"));
                                objZafFac.setIntTipPer(((rstCon.getInt("tipo_persona_nuevo") >0)?rstCon.getInt("tipo_persona_nuevo"): rstCon.getInt("co_tipper")));
                                /* Agregado para obtener el tipo de Persona del cliente
                                /* Agregado para obtener la forma de pago de la factura*/ 
                                objZafFac.setNe_tipforpag(rstCon.getInt("ne_tipforpag"));
                                
                                objZafFac.setCo_empsol(rstCon.getInt("empsol"));
                                objZafFac.setCo_locrel(rstCon.getInt("co_locrel"));
                                /* Agregado para obtener la forma de pago de la factura*/ 
                                /*Cambio del 27 de julio 2019*/
                                objZafFac.setTx_numped(rstCon.getString("tx_numped")!=null?"'"+rstCon.getString("tx_numped")+"'":null);
                                objZafFac.setTx_obs1((rstCon.getString("tx_obs1")!=null)?rstCon.getString("tx_obs1"):"");
                                /*Cambio del 27 de julio 2019*/
                                
                                
                                lstZafFac.add(objZafFac);
                        }
                        stmSql.close();
                        rstCon.close();
                } catch (Exception e) {
                        System.out.println(e.getMessage());
                }


            }//fin de traeDatos        
        

        /**
         * Metodo que calcula los subtotales de la factura.
         * @param dblPre precio del item.
         * @param dblCant cantidad del item.
         * @param dblPorDes porcentaje de descuento del item
         * @return BigDecimal con el subtotal.
         */    
	private BigDecimal calcularSubCabFac(Double dblPre,Double dblCant,Double dblPorDes){
		
		BigDecimal bigBas,bigSub,bigSub2,bigCant,bigPre,bigPorDes,bigDes;
		
		bigCant=new BigDecimal(dblCant).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigPre=new BigDecimal(dblPre).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigSub=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigSub2=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigPorDes=new BigDecimal(dblPorDes).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigBas=new BigDecimal(100).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigDes=new BigDecimal(dblPorDes).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		bigSub=bigPre.multiply(bigCant);
		bigDes=bigSub.multiply(bigPorDes).divide(bigBas);
		bigSub2=bigSub.subtract(bigDes);
		
		return bigSub2;
	}
        

	private BigDecimal calcularSubCabFac3(BigDecimal bgdPre,BigDecimal bgdCant,BigDecimal bgdPorDes){
		
		BigDecimal bigBas,bigSub=BigDecimal.ZERO,bigSub2=BigDecimal.ZERO,bigDes=BigDecimal.ZERO;
		
		//bigCant=new BigDecimal(dblCant).setScale(2,BigDecimal.ROUND_HALF_UP);
		//bigPre=new BigDecimal(dblPre).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigSub=BigDecimal.ZERO;//new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigSub2=BigDecimal.ZERO;//new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
		//bigPorDes=new BigDecimal(dblPorDes).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigBas=new BigDecimal(100).setScale(2,BigDecimal.ROUND_HALF_UP);
		//bigDes=new BigDecimal(dblPorDes).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		bigSub=bgdPre.multiply(bgdCant);
		bigDes=bigSub.multiply(bgdPorDes.divide(bigBas));
		bigSub2=bigSub.subtract(bigDes);
                bigSub2=bigSub2.setScale(2, RoundingMode.HALF_UP);
		
		return bigSub2;
	}


        
/**
         * Metodo que calcula los subtotales de la factura.
         * @param dblPre precio del item.
         * @param dblCant cantidad del item.
         * @param dblPorDes porcentaje de descuento del item
         * @return BigDecimal con el subtotal.
         */    
	private double calcularSubCabFac2(Double dblPre,Double dblCant,Double dblPorDes){
		
		double dblSub=0,dblDes=0,dblSub2=0;
                
                dblSub=dblCant * dblPre;
                dblDes=dblSub*(((dblPorDes==0.0)?0:dblPorDes)/100);
                dblSub2=dblSub-dblDes;
                dblSub2=objUti.redondear(dblSub2, 3);
                dblSub2=objUti.redondear(dblSub2, 2);
                
		return dblSub2;
	} 
        
	private double calcularDes(double dblPre,double dblCant,double dblPorDes){
		
		double dblSub=0,dblDes=0,dblSub2=0;
                
                dblSub=dblCant * dblPre;
                dblDes=dblSub*(((dblPorDes==0.0)?0:dblPorDes)/100);
//                dblSub2=objUti.redondear(dblSub2, 2);
                
		return dblDes;
	}          
    

	private BigDecimal calcularDesBig(BigDecimal bgdPre,BigDecimal bgdCant,BigDecimal bgdPorDes){
		
            BigDecimal bgdSub=BigDecimal.ZERO,bgdDes=BigDecimal.ZERO,bgdSub2=BigDecimal.ZERO;                
            bgdSub=bgdCant.multiply(bgdPre);
            bgdDes=bgdSub.multiply(((bgdPorDes.compareTo(BigDecimal.ZERO))==0?BigDecimal.ZERO:(bgdPorDes.compareTo(BigDecimal.ZERO))>0?(bgdPorDes.divide(new BigDecimal(100))):BigDecimal.ZERO)).setScale(2, RoundingMode.HALF_UP);
            return bgdDes;
	}          
        

        
	/**
	 * <H1>Cálculo del iva por cobrar</H1>
	 * Esta función <Strong>calcula el valor del IVA con respecto al subtotal de la factura</Strong>
	 * */
	private BigDecimal calcularIvaCabFac(BigDecimal bigSubTot){
		BigDecimal bigIva=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		BigDecimal bigValIva=new BigDecimal(0.12).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigIva=bigSubTot.multiply(bigValIva);
		return bigIva;
	}
	

        private Double calcularIvaCabFac2(double bigSubTot,double porIva){
            return bigSubTot*((porIva==0.0?0:porIva)/100);        
        }        
        
        private BigDecimal calcularIvaCabFac3(BigDecimal bigSubTot,BigDecimal bgdPorIva){
            return bigSubTot.multiply((bgdPorIva.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:bgdPorIva.divide(new BigDecimal(100)))).setScale(2, RoundingMode.HALF_UP);
                            
        }        
        
		
		
        private Double calcularIvaCabFac2CompSold(double bigSubTot,double porIva){
            return bigSubTot*((porIva==0.0?0:porIva)/100);        
        }        
	
        private BigDecimal calcularIvaCabFac2CompSold3(BigDecimal bigSubTot,BigDecimal bgdPorIva){
            return bigSubTot.multiply((bgdPorIva.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:bgdPorIva.divide(new BigDecimal(100)))).setScale(2, RoundingMode.HALF_UP);        
        }        
        
        
	/**
	 * <H1>Cálculo del Total de la cabecera de la factura</H1>
	 * Esta función permite el cálculo del total de la factura, esto es
	 * el subtotal mas el iva.
	 */
	private BigDecimal calcularTotCab(BigDecimal bigSubTot,BigDecimal bigIva){
		BigDecimal bigTot=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
		bigTot=bigSubTot.add(bigIva).setScale(2,BigDecimal.ROUND_HALF_UP);
		return bigTot;
	}
	
	private BigDecimal calcularSum(List<BigDecimal> bigSub){
		BigDecimal bigSuma=new BigDecimal(0);
		for (int i = 0; i < bigSub.size(); i++) {
			bigSuma=bigSuma.add(bigSub.get(i));
		}
		return bigSuma;
	}
        
	/**
	 * <H1>Asignación de un nuevo número de factura</H1>
	 * <P>Esta función consulta el <strong>último número existente en tbm_cabtipdoc</strong>, de acuerdo al tipo de documento
	 * que en el caso de la factura es 1, en relación a una determinada empresa y local,
	 * a este número se le incrementa una unidad para seguir la secuencia, y luego se actualiza nuevamente tbm_cabtipdoc
	 * y a su vez se asigna este número a la factura nueva que se registra en tbm_cabmovinv</P>
	 */
	private int asignarNumFac(){
            int intNumDoc=0;
            int intNumDocGui=0;
            try {
                    stmSql=cnx.createStatement();
                    strQue="SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc, tx_descor "
                    + " FROM tbm_cabTipDoc WHERE co_emp="+objZafImp.getEmp()+" and co_loc="+objZafImp.getLoc()+" and co_tipDoc ="+objZafImp.getTipdoc();
                    rstCon=stmSql.executeQuery(strQue);
                    if (rstCon.next()) {
                            intNumDoc=rstCon.getInt("ultnum");
                    }
                    //sql.close();
                    rstCon.close();

                    strQue="UPDATE tbm_cabTipDoc SET ne_ultDoc="+intNumDoc+" WHERE co_emp="+objZafImp.getEmp()+" and co_loc="+objZafImp.getLoc()+" and co_tipDoc ="+objZafImp.getTipdoc();
                    stmSql.executeUpdate(strQue);
                    //sql.close();

                    strQue=" UPDATE tbm_cabmovinv SET co_mnu=14,  ne_numdoc="+intNumDoc+",ne_numgui="+intNumDocGui+",st_reg='A', st_imp='S' "
                    + " WHERE co_emp="+objZafFac.getCo_emp()+" and co_loc="+objZafFac.getCo_loc()+" and co_tipDoc ="+objZafFac.getCo_tipdoc()+" and co_doc="+objZafFac.getCo_doc();
                                strQue+=" ; UPDATE tbm_cabdia SET tx_numdia='"+intNumDoc+"' WHERE co_emp="+objZafImp.getEmp()+" and co_loc="+objZafImp.getLoc()+" and co_tipDoc ="+objZafImp.getTipdoc()+" and co_dia="+objZafImp.getCoDoc();
                    stmSql.executeUpdate(strQue);
                    stmSql.close();

            } catch (Exception e) {
                    System.out.println(e.getMessage());
            }
            return intNumDoc;
	}
	/**
	 * Esta función permite extraer el último documento generado por empresa de acuerdo a la cabecera del movimiento del inventario
	 * @param coEmp
	 * @return sec
	 */
	private int traerSec(int intCodEmp,Connection cnx){
		
		int intSec=0;
		try {
			stmSql=cnx.createStatement();
			strQue="select ne_secultdoctbmcabmovinv as sec from tbm_emp where co_emp="+intCodEmp;
			rstCon=stmSql.executeQuery(strQue);
			if (rstCon.next()) {
				intSec=rstCon.getInt("sec");
			}
			stmSql.close();
			rstCon.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}
		
		
		return intSec;
		
	} 
        
        private String codificarCadQry(Object objCadena)
        {
            if (objCadena==null)
                return "Null";
            if (objCadena.toString().equals(""))
                return "Null";
            return "'" + objCadena.toString().replaceAll("'", "''") + "'";
        }  
        
	private List<BigDecimal> retornarTotCabFac(int intCodEmpSolDev, int intLocSolDev,int intTipDocSolDev,int intCodSolDev, int intCodLocFac, int intCodTipDoc, int intCodDocNueFac, Connection cnx){
	
            List<BigDecimal> lstBigtot=new ArrayList<BigDecimal>();
            hasValTot=new HashMap<String, BigDecimal>();

            //int intCodDoc ;//= traeCoDoc(objZafImp.getNumdoc());

            BigDecimal bigSubTot,bigTot,bigIva,bigSum,bigNeg;
            double dblSubTot=0.0,  dblIva=0.0;
            List<BigDecimal> lstBigSub=new ArrayList<BigDecimal>();
            bigTot=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
            bigIva=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
            bigSum=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
            bigNeg=new BigDecimal(-1).setScale(2,BigDecimal.ROUND_HALF_UP);
            int intEstIns=0;


            /*Agregado para recorrer una sola vez */
            BigDecimal bigCanVolFac= new BigDecimal(0), bigValDes= new BigDecimal(0), bigTotValDes=new BigDecimal(0);
            BigDecimal bigPreUni=new BigDecimal(0),bigPorDescuento=new BigDecimal(0);
            BigDecimal bigTotal=new BigDecimal(0),bigCosUni=new BigDecimal(0),bigCostTot=new BigDecimal(0);
            BigDecimal bigTotTra=new BigDecimal(0);
            BigDecimal bigTotSinTra=new BigDecimal(0);

            try {
                    stmSql=cnx.createStatement();

                    strQue=" select s.nd_canvolfac, inv.st_ser, d.nd_preuni,"
                    +" d.nd_pordes, d.st_ivacom, d.tx_codalt,"
                    +" d.co_itm, d.tx_nomitm, d.tx_unimed,"
                    +" d.co_bod, d.nd_cosuni, d.st_ivacom as st_iva,cs.nd_poriva,"
                    +" case when d.nd_preunivenlis is null" 
                    +" then 0 "
                    +" else d.nd_preunivenlis end as nd_preunivenlis,"  
                    +" case when d.nd_pordesvenmax is null "
                    +" then 0 "
                    +" else d.nd_pordesvenmax end as nd_pordesvenmax," 
                    +" case when d.ne_numfil is null" 
                    +" then 0 "
                    +" else d.ne_numfil end as ne_numfil, "
                    +" d.st_meringegrfisbod, d.co_reg"
                    +" from tbm_cabmovinv c  "
                    +" inner join tbm_detmovinv d on (c.co_emp=d.co_emp and c.co_loc=d.co_loc and c.co_doc=d.co_doc and c.co_tipdoc=d.co_tipdoc) "
                    +" inner join tbm_detsoldevven s on (s.co_emp= d.co_emp and s.co_locrel=d.co_loc and s.co_tipdocrel=d.co_tipdoc and s.co_docrel=d.co_doc and s.co_regrel=d.co_reg)"
                    +" inner join tbm_cabsoldevven cs on (cs.co_emp= s.co_emp and cs.co_loc=s.co_loc and cs.co_tipdoc=s.co_tipdoc and cs.co_doc=s.co_doc)"			
                    +" inner join tbm_inv inv on (s.co_emp= inv.co_emp and s.co_itm=inv.co_itm )"
                    +" where s.co_emp ="+intCodEmpSolDev+" and s.co_loc= "+intLocSolDev+" and s.co_tipdoc="+intTipDocSolDev+" and s.co_doc="+intCodSolDev +" and s.nd_canvolfac > 0";

                    
                    rstCon=stmSql.executeQuery(strQue);
                    int intNumFil=0;
                    while (rstCon.next()) {

                        //bigSubTot=calcularSubCabFac(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                        dblSubTot=calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                        bigSubTot=new BigDecimal(objUti.redondear(dblSubTot, 2)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        lstBigSub.add(bigSubTot);
                        bigSum=calcularSum(lstBigSub).setScale(2,BigDecimal.ROUND_HALF_UP);

                        if(rstCon.getString("st_iva").equals("S")){
                                //iva=calculaIvaCabecera(suma).setScale(2,BigDecimal.ROUND_HALF_UP);
                                //bigIva=bigIva.add(calcularIvaCabFac(bigSubTot).setScale(2,BigDecimal.ROUND_HALF_UP));
                             dblIva=calcularIvaCabFac2(dblSubTot, bldivaEmp);
                             bigIva= bigIva.add(new BigDecimal(dblIva));
                        }

                        bigTot=calcularTotCab(bigSum, bigIva).setScale(2,BigDecimal.ROUND_HALF_UP);

                        bigCanVolFac= new BigDecimal(rstCon.getDouble("nd_canvolfac"));
                        bigPreUni= new BigDecimal(rstCon.getDouble("nd_preuni"));
                        bigPorDescuento=new BigDecimal(rstCon.getDouble("nd_pordes"));
                        bigValDes = (bigCanVolFac.multiply(bigPreUni).compareTo(new BigDecimal(0))==0)?new BigDecimal(0):(bigCanVolFac.multiply(bigPreUni).multiply(bigPorDescuento.divide(new BigDecimal(100))));
                        bigTotValDes=bigTotValDes.add(bigValDes);
                        //bigTotal = bigCanVolFac.multiply(bigPreUni).subtract(bigValDes);
                        
                        if(rstCon.getString("st_ser").equals("T")){
                            bigTotTra=bigTotTra.add(bigSubTot);
                        }else{
                            bigTotSinTra=bigTotSinTra.add(bigSubTot);
                        }
                        
                        bigCosUni=new BigDecimal(rstCon.getDouble("nd_cosuni"));
                        bigCostTot=bigCosUni.multiply(bigCanVolFac);
                        bigCostTot= bigCostTot.multiply(new BigDecimal(-1));


                        if (intEstIns == 1) strInsDetCabMovInv.append(" UNION ALL ");

                        intNumFil++;
                        strInsDetCabMovInv.append("SELECT "+intCodEmpSolDev+","+intCodLocFac+","+intCodTipDoc+","+intCodDocNueFac+","+intNumFil+",'" +
                                    rstCon.getString("tx_codalt")+"','"+rstCon.getString("tx_codalt")+"',"+rstCon.getString("co_itm")+", "+rstCon.getString("co_itm")+", "+
                                    codificarCadQry(rstCon.getString("tx_nomitm"))+",'"+rstCon.getString("tx_unimed")+"',"+rstCon.getString("co_bod")+","+
                                    bigCanVolFac.multiply(new BigDecimal(-1))+","+/*dblTotal*/bigSubTot.multiply(new BigDecimal(-1))+", "+rstCon.getString("nd_cosuni") + ", 0 , "+bigPreUni.setScale(2, BigDecimal.ROUND_HALF_UP) + ", " +
                                    bigPorDescuento.setScale(2, java.math.BigDecimal.ROUND_HALF_UP) + ", '"+rstCon.getString("st_ivacom")+ "' " +
                                    ","+bigCostTot+",'I', '"+rstCon.getString("st_meringegrfisbod")+"', 0, "+
                                    rstCon.getString("nd_preunivenlis")+", "+rstCon.getString("nd_pordesvenmax")+", " +
                                    " "+rstCon.getString("ne_numfil")+" ");
                        intEstIns=1;				

                    }
/*                    bigSum=bigSum.multiply(bigNeg).setScale(2,BigDecimal.ROUND_HALF_UP);
                    bigIva=bigIva.multiply(bigNeg).setScale(2,BigDecimal.ROUND_HALF_UP);
                    bigTot=bigTot.multiply(bigNeg).setScale(2,BigDecimal.ROUND_HALF_UP);*/
                    hasValTot.put(SUBTOTAL,bigSum);
                    //hasValTot.put(TOTALIVA,bigIva);
                    hasValTot.put(TOTALIVA,new BigDecimal(objUti.redondear(bigIva.doubleValue(),2)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    //hasValTot.put(TOTALFACTURA,bigTot);
                    hasValTot.put(TOTALFACTURA,bigTot.setScale(2, BigDecimal.ROUND_HALF_UP));                    
                    hasValTot.put(TOTALDESCUENTO,bigTotValDes);
                    hasValTot.put(TOTALCONFLETE,bigTotTra);
                    hasValTot.put(TOTALSINFLETE,bigTotSinTra);
                    lstBigtot.add(bigSum);//total de subtotales.
                    //lstBigtot.add(bigIva);//total de iva.
                    lstBigtot.add(new BigDecimal(objUti.redondear(bigIva.doubleValue(),2)).setScale(2, BigDecimal.ROUND_HALF_UP));//total de iva.                    
                    //lstBigtot.add(bigTot);//total de facturas.
                    lstBigtot.add(bigTot.setScale(2, BigDecimal.ROUND_HALF_UP));//total de facturas.
                    //agregado para controlar el total de descuentos usados en asientos diarios.
                    lstBigtot.add(bigTotValDes);// total de Descuentos.
                    lstBigtot.add(bigTotTra);
                    lstBigtot.add(bigTotSinTra);
                    stmSql.close();
                    rstCon.close();
            } catch (SQLException e) {
                    System.out.println(e.getMessage());
            }
            return lstBigtot;
		
	}//fin de insertar   
        
        
	private int getCodDoc( String strSql, Connection cnx ){
		  int intCodDoc=0;
		  Statement stmLoc;
		  ResultSet rstLoc;
		  try{
			  stmLoc=cnx.createStatement();
		      

		         rstLoc = stmLoc.executeQuery(strSql);
		         if(rstLoc.next())
		               intCodDoc = rstLoc.getInt("co_doc");
		          rstLoc.close();
		          rstLoc=null;

		          stmLoc.close();
		          stmLoc=null;

		     
		  }catch(SQLException e){ intCodDoc=-1;  
		  }catch(Exception e){ intCodDoc=-1;; 
		  }
		   return intCodDoc;
		 }
        

        /**
	 * <H1>INGRESO DE LA CABECERA DE FACTURA</H1>
	 * <p> Este método permite el registro de datos provenientes del objeto de factura que se recibe
	 * en el constructor, la cabecera de factura ingresa en la tabla tbm_cabmovinv
	 * </p>
	 * */
	
	//public int ingresarCabMovInv(Connection cnx){
        public boolean ingresarCabMovInv(Connection cnx){
            int intNumDoc=0;
            int intSecEmp,intSecGrp;
            int intCodMnuFac=14;
            intSecEmp=traerSec(objZafImp.getEmp(),cnx);
            intSecEmp++;
            intSecGrp=traerSec(0,cnx);
            intSecGrp++;
            ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
            boolean booCab,booSol,booRel,booDet,booRet=true;
            
            /*modificado para traer la secuencia de las facturas*/
            if(objZafImp.getTipdoc()==1){
                objZafImp.setTipdoc(228); 
            }
            String strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
                      " co_emp="+objZafImp.getEmp()+" AND co_loc="+objZafImp.getLoc()+" AND co_tipDoc="+objZafImp.getTipdoc();
            int intCodDocNueFac =0;
            List<BigDecimal> lstBigTotcab=new ArrayList<BigDecimal>();
            StringBuffer strInsDet=new StringBuffer();

            try {
                    /*Agregado para recorrer la lista que se llena en traer_datos*/
                    for(int i=0;i<lstZafFac.size();i++){			
                            //
                            intCodDocNueFac =  getCodDoc( strSql,cnx);
                            
                            /*CALCULO DE SUBTOTALES E IVA ITEMS DE: TRANSPORTE, SERVICIO, NO SERVICIO*/
                            calcularSubtotalIvaxTipItm(lstZafFac.get(i).getCo_emp(), lstZafFac.get(i).getIntCodLocSolDev(), lstZafFac.get(i).getIntCodTipDocSolDev(), lstZafFac.get(i).getIntCodDocSolDev(), intCodDocNueFac,cnx);
                            
                            formaRetencionAct(cnx);
                            /*CALCULO DE LAS RETENCIONES DE ITEMS DE: TRANSPORTE, SERVICIO, NO SERVICIO*/
                            //cargaForPag(cnx, intCodMotTran, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMTRA"),hasTot.get("IVAITMTRA"), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS TRANSPORTE
                            cargaForPag(cnx, intCodMotTran, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMTRA"),(hasTot.get("IVAITMTRA") - hasTot.get("CMPSOLTRA")), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS TRANSPORTE
                            //cargaForPag(cnx, intCodMotBien, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMNOSER"),hasTot.get("IVAITMNOSER"), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS NO SERVICIO
                            cargaForPag(cnx, intCodMotBien, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMNOSER"),(hasTot.get("IVAITMNOSER") - hasTot.get("CMPSOLNOSER")), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS NO SERVICIO
                            //cargaForPag(cnx, intCodMotServ, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMSER"),hasTot.get("IVAITMSER"), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS SERVICIO
                            cargaForPag(cnx, intCodMotServ, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMSER"),(hasTot.get("IVAITMSER") - hasTot.get("CMPSOLSER")), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS SERVICIO
                            for (ZafPagFac obj: lstZafPagFac){
                                System.out.println("Monto: "+obj.getBigMontPag());
                            }                            

                            booCab=objZafGenFacDAO.booInsertarCabMovInv(lstZafFac, hasTot, intSecGrp, intSecEmp, intCodDocNueFac,bldivaEmp, cnx);
                            
                            booSol=objZafGenFacDAO.booActualizarSolDevVen(lstZafFac.get(i).getIntCodEmpSolDev(), lstZafFac.get(i).getIntCodLocSolDev(), lstZafFac.get(i).getIntCodTipDocSolDev(), lstZafFac.get(i).getIntCodDocSolDev(), cnx);
                            
                            booRel=objZafGenFacDAO.booInsertarRelCabMovInv(lstZafFac.get(i), intCodDocNueFac, cnx);
                            
                            booDet=objZafGenFacDAO.booInsertarDetCabMovInv(strInsDetCabMovInv.toString(), cnx);
                            
                            if(booCab && booSol && booRel && booDet){
                                booRet=true;
                            }else{
                                booRet=false;
                            }
                    }
                    
            } catch (Exception e) {
                    e.printStackTrace();
                    //return 0;
                    booRet=false;
            }
            return booRet;
	}//fin de ingresa cabecera
        

        
        public boolean ingresarCabMovInvBig(Connection cnx, int intCodUsuarioNOADMIN){
            int intNumDoc=0;
            int intSecEmp,intSecGrp;
            int intCodMnuFac=14;
            intSecEmp=traerSec(objZafImp.getEmp(),cnx);
            intSecEmp++;
            intSecGrp=traerSec(0,cnx);
            intSecGrp++;
            ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
            boolean booCab,booSol,booRel,booDet,booRet=true;
            
            /*modificado para traer la secuencia de las facturas*/
            if(objZafImp.getTipdoc()==1){
                objZafImp.setTipdoc(228); 
            }
            String strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
                      " co_emp="+objZafImp.getEmp()+" AND co_loc="+objZafImp.getLoc()+" AND co_tipDoc="+objZafImp.getTipdoc();
            int intCodDocNueFac =0;
            List<BigDecimal> lstBigTotcab=new ArrayList<BigDecimal>();
            StringBuffer strInsDet=new StringBuffer();

            try {
                    /*Agregado para recorrer la lista que se llena en traer_datos*/
                    for(int i=0;i<lstZafFac.size();i++){			
                            //
                            intCodDocNueFac =  getCodDoc( strSql,cnx);
                            
                            /*CALCULO DE SUBTOTALES E IVA ITEMS DE: TRANSPORTE, SERVICIO, NO SERVICIO*/
                            calcularSubtotalIvaxTipItmBgd(lstZafFac.get(i).getCo_emp(), lstZafFac.get(i).getIntCodLocSolDev(), lstZafFac.get(i).getIntCodTipDocSolDev(), lstZafFac.get(i).getIntCodDocSolDev(), intCodDocNueFac,cnx);
                            
                            formaRetencionAct(cnx);
                            /*CALCULO DE LAS RETENCIONES DE ITEMS DE: TRANSPORTE, SERVICIO, NO SERVICIO*/
                            //cargaForPag(cnx, intCodMotTran, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMTRA"),hasTot.get("IVAITMTRA"), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS TRANSPORTE
                            cargaForPagBig(cnx, intCodMotTran, lstZafFac.get(i).getCo_emp(), hasTotBig.get("SUBTOITMTRA"),(hasTotBig.get("IVAITMTRA").subtract(hasTotBig.get("CMPSOLTRA"))), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS TRANSPORTE
                            //cargaForPag(cnx, intCodMotBien, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMNOSER"),hasTot.get("IVAITMNOSER"), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS NO SERVICIO
                            cargaForPagBig(cnx, intCodMotBien, lstZafFac.get(i).getCo_emp(), hasTotBig.get("SUBTOITMNOSER"),(hasTotBig.get("IVAITMNOSER").subtract(hasTotBig.get("CMPSOLNOSER"))), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS NO SERVICIO
                            //cargaForPag(cnx, intCodMotServ, lstZafFac.get(i).getCo_emp(), hasTot.get("SUBTOITMSER"),hasTot.get("IVAITMSER"), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS SERVICIO
                            cargaForPagBig(cnx, intCodMotServ, lstZafFac.get(i).getCo_emp(), hasTotBig.get("SUBTOITMSER"),(hasTotBig.get("IVAITMSER").subtract(hasTotBig.get("CMPSOLSER"))), lstZafFac.get(0).getIntTipPer(),lstZafFac.get(0).getNe_tipforpag());//ITEMS SERVICIO
                            for (ZafPagFac obj: lstZafPagFac){
                                System.out.println("Monto: "+obj.getBigMontPag());
                            }                            
                            //Esto movi Bostel
                            booCab=objZafGenFacDAO.booInsertarCabMovInv(lstZafFac, hasTotBig, intSecGrp, intSecEmp, intCodDocNueFac,bldivaEmp, cnx);
                            //booCab=objZafGenFacDAO.booInsertarCabMovInvBig(lstZafFac, hasTotBig, intSecGrp, intSecEmp, intCodDocNueFac,bldivaEmp, cnx,intCodUsuarioNOADMIN);
                            
                            booSol=objZafGenFacDAO.booActualizarSolDevVen(lstZafFac.get(i).getIntCodEmpSolDev(), lstZafFac.get(i).getIntCodLocSolDev(), lstZafFac.get(i).getIntCodTipDocSolDev(), lstZafFac.get(i).getIntCodDocSolDev(), cnx);
                            
                            booRel=objZafGenFacDAO.booInsertarRelCabMovInv(lstZafFac.get(i), intCodDocNueFac, cnx);
                            
                            booDet=objZafGenFacDAO.booInsertarDetCabMovInv(strInsDetCabMovInv.toString(), cnx);
                            
                            if(booCab && booSol && booRel && booDet){
                                booRet=true;
                            }else{
                                booRet=false;
                            }
                    }
                    
            } catch (Exception e) {
                    e.printStackTrace();
                    //return 0;
                    booRet=false;
            }
            return booRet;
	}//fin de ingresa cabecera        

        
        /**
         * Metodo que inserta el asiento diario
         * @param intCodEmpFac codigo de la empresa
         * @param intCodLocFac codigo del local
         * @param intCodTipDocFac codito del tipo de documento.
         * @param intCodDocFac codigo de la factura.
         * @param cnx conexion.
         * @return boolean indicando el exito de la operacion.
         */
	public boolean insertarAsiDiaCnt(int intCodEmpFac, int intCodLocFac, int intCodTipDocFac,int intCodDocFac,Connection cnx,ZafParSis objZafParSis ){
            boolean booRet=false;
            Date datAct=null;
            int intAnioAct, intMesAct;
            StringBuffer strSqlInsCab=new StringBuffer("");
            ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();

            try{
                    stmSql=cnx.createStatement();

                    Calendar calFecAct;
                    calFecAct=Calendar.getInstance();
                    intAnioAct=calFecAct.get(Calendar.YEAR);
                    intMesAct = calFecAct.get(Calendar.MONTH)+1;
                    List<ZafCiePerCnt> lisCiePer= objZafGenFacDAO.obtenerCiePerCnt(intCodEmpFac, intAnioAct,cnx);
                    for(ZafCiePerCnt ciePer:lisCiePer){
                        if(ciePer.getStrTipCierre().equals("M")){
                            if(intAnioAct==ciePer.getIntAnio()){
                                    if(intMesAct==ciePer.getIntMes()){
                                            booRet=false;
                                            return booRet;
                                    }
                            }
                        }else{
                                booRet=false;
                                return booRet;
                        }
                    }
                    SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formato1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strCalFecAct=formato.format(calFecAct.getTime());
                    String strCalFecHorAct=formato1.format(calFecAct.getTime());  
                        
                    booRet=objZafGenFacDAO.insertarCabAsiDia(cnx,intCodEmpFac, intCodLocFac, intCodTipDocFac, intCodDocFac, strCalFecAct, strCalFecHorAct);
                    if(booRet==false){
                        return booRet;
                    }
                    /*obtener el periodo actual yyyymm*/
                    int intAnioPer=calFecAct.get(Calendar.YEAR);
                    String strMesPer=String.valueOf(calFecAct.get(Calendar.MONTH)+1);
                    if(strMesPer.length()==1){
                            strMesPer="0"+strMesPer;
                    }
                    String strPerAct=String.valueOf(intAnioPer)+strMesPer;
                    /*obtener el periodo actual yyyymm*/                    
                    booRet=objZafGenFacDAO.insertarDetDiaCnt(hasTot, objZafImp, intCodEmpFac, intCodLocFac, intCodTipDocFac, intCodDocFac, Integer.valueOf(strPerAct), cnx,objZafParSis);
                    if(booRet==false){
                        return booRet;
                    }                    
            }catch(Exception ex){
                    ex.printStackTrace();
                    booRet=false;
            }finally{

            }

            return booRet;
	}
        
        
        
        
        /**
         * Metodo que inserta el asiento diario
         * @param intCodEmpFac codigo de la empresa
         * @param intCodLocFac codigo del local
         * @param intCodTipDocFac codito del tipo de documento.
         * @param intCodDocFac codigo de la factura.
         * @param cnx conexion.
         * @return boolean indicando el exito de la operacion.
         */
	public boolean insertarAsiDiaCntBig(int intCodEmpFac, int intCodLocFac, int intCodTipDocFac,int intCodDocFac,Connection cnx,ZafParSis objZafParSis ){
            boolean booRet=false;
            Date datAct=null;
            int intAnioAct, intMesAct;
            StringBuffer strSqlInsCab=new StringBuffer("");
            ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();

            try{
                    stmSql=cnx.createStatement();

                    Calendar calFecAct;
                    calFecAct=Calendar.getInstance();
                    intAnioAct=calFecAct.get(Calendar.YEAR);
                    intMesAct = calFecAct.get(Calendar.MONTH)+1;
                    List<ZafCiePerCnt> lisCiePer= objZafGenFacDAO.obtenerCiePerCnt(intCodEmpFac, intAnioAct,cnx);
                    for(ZafCiePerCnt ciePer:lisCiePer){
                        if(ciePer.getStrTipCierre().equals("M")){
                            if(intAnioAct==ciePer.getIntAnio()){
                                    if(intMesAct==ciePer.getIntMes()){
                                            booRet=false;
                                            return booRet;
                                    }
                            }
                        }else{
                                booRet=false;
                                return booRet;
                        }
                    }
                    SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formato1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strCalFecAct=formato.format(calFecAct.getTime());
                    String strCalFecHorAct=formato1.format(calFecAct.getTime());  
                        
                    booRet=objZafGenFacDAO.insertarCabAsiDia(cnx,intCodEmpFac, intCodLocFac, intCodTipDocFac, intCodDocFac, strCalFecAct, strCalFecHorAct);
                    if(booRet==false){
                        return booRet;
                    }
                    /*obtener el periodo actual yyyymm*/
                    int intAnioPer=calFecAct.get(Calendar.YEAR);
                    String strMesPer=String.valueOf(calFecAct.get(Calendar.MONTH)+1);
                    if(strMesPer.length()==1){
                            strMesPer="0"+strMesPer;
                    }
                    String strPerAct=String.valueOf(intAnioPer)+strMesPer;
                    /*obtener el periodo actual yyyymm*/                    
                    booRet=objZafGenFacDAO.insertarDetDiaCntBig(hasTotBig, objZafImp, intCodEmpFac, intCodLocFac, intCodTipDocFac, intCodDocFac, Integer.valueOf(strPerAct), cnx,objZafParSis);
                    if(booRet==false){
                        return booRet;
                    }                    
            }catch(Exception ex){
                    ex.printStackTrace();
                    booRet=false;
            }finally{

            }

            return booRet;
	}        
	
	
	public boolean insertarDetDiaCnt(List lisDatTot, ZafImp genOgi, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac,int intCodDocFac,int intPerAct, Connection cnx){
            boolean booRetorno=true;
            ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
            Statement stm=null;
		
            /*
              totales.add(suma);//total de subtotales.0
              totales.add(iva);//total de iva.1
              totales.add(total);//total de facturas.2
              totales.add(bigTotValDes);//total de descuentos.3
              totales.add(numerofactura);//numero de factura nueva.4
              totales.add(bigTotalTransporte);//
             */
            try{
                String strSqlInsUpd=new String("");
                String srtSqlSal=new String("");
                ZafCtaCtb_dat objCtaCnt= new ZafCtaCtb_dat(genOgi, 228,cnx);


                String srtSql="INSERT INTO tbm_detdia(co_emp, co_loc, co_tipDoc, co_dia, co_reg, co_cta, nd_mondeb, nd_monhab )"+
                " VALUES("+intCodEmpFac+", "+intCodLocFac+", "+intCodTipDocFac+", "+intCodDocFac+", ";


                if(((BigDecimal)(lisDatTot.get(2))).compareTo(new BigDecimal(0)) > 0){
                 strSqlInsUpd+=srtSql+" 1,"+objCtaCnt.getCtaDeb()+","+((BigDecimal)lisDatTot.get(2)).setScale(2, RoundingMode.HALF_UP)+", 0 )  ; ";
                srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+lisDatTot.get(2)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDeb()+" AND co_per="+intPerAct+";";
                strSqlInsUpd+=srtSqlSal+";";
                }
                if(((BigDecimal)(lisDatTot.get(3))).compareTo(new BigDecimal(0)) > 0){
                        strSqlInsUpd+=srtSql+" 2,"+objCtaCnt.getCtaDescVentas()+","+((BigDecimal)lisDatTot.get(3)).setScale(2, RoundingMode.HALF_UP)+", 0 );";
                    srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+lisDatTot.get(3)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDescVentas()+" AND co_per="+intPerAct;
                    strSqlInsUpd+=srtSqlSal+"; ";
                }

                if(((BigDecimal)lisDatTot.get(3)).add((BigDecimal)lisDatTot.get(0)).compareTo(new BigDecimal(0)) > 0){
                    strSqlInsUpd+=srtSql+" 3, "+objCtaCnt.getCtaHab()+", 0, "+((BigDecimal)lisDatTot.get(3)).add((BigDecimal)lisDatTot.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP)+" ) ; ";
                    srtSqlSal=objZafGenFacDAO.obtenerCadActSalCta(hasValTot.get(TOTALDESCUENTO).add(hasValTot.get(TOTALFACTURA)).multiply(new BigDecimal(-1)),intCodEmpFac,objCtaCnt.getCtaHab(),intPerAct);
                    //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+(((BigDecimal)lisDatTot.get(3)).add((BigDecimal)lisDatTot.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(-1)))+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaHab()+" AND co_per="+intPerAct+";";
                    strSqlInsUpd+=srtSqlSal+"; ";
                }

                if(((BigDecimal)(lisDatTot.get(1))).compareTo(new BigDecimal(0)) > 0){
                        
                        strSqlInsUpd+=srtSql+" 4,"+objCtaCnt.getCtaIvaVentas()+", 0,"+((BigDecimal)lisDatTot.get(1)).setScale(2, RoundingMode.HALF_UP)+" );";                                
                        //strSqlInsUpd+=srtSql+" 4,"+objZafParSis.getCodigoCuentaContableIvaVentas()+", 0,"+((BigDecimal)lisDatTot.get(1)).setScale(2, RoundingMode.HALF_UP)+" );";
                        objZafGenFacDAO.obtenerCadActSalCta(hasValTot.get(TOTALIVA),intCodEmpFac,objCtaCnt.getCtaIvaVentas(),intPerAct);
                        //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+lisDatTot.get(1)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaIvaVentas()+" AND co_per="+intPerAct+";";
                    strSqlInsUpd+=srtSqlSal+"; ";
                }		

                String strSQL="";
                //Actualiza cuentas padre del periodo
                for(int j=6; j>1 ; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intPerAct + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intPerAct + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    strSqlInsUpd+=strSQL+" ;  ";			
                }
                /*Actualiza saldos de cuenta de resultados de la empresa tbm_empresa*/
                strSQL="";
                strSQL="UPDATE tbm_salCta";
                strSQL+=" SET nd_salCta=b1.nd_salCta";
                strSQL+=" FROM (";
                strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intPerAct + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
                strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
                strSQL+=" AND a2.co_per=" + intPerAct + "";
                strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                strSQL+=" ) AS b1";
                strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                strSqlInsUpd+=strSQL+" ;  ";

                for(int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intPerAct + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intPerAct + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    strSqlInsUpd+=strSQL+" ;  ";
                    //stmLoc.executeUpdate(strSQL);
               }   
                stm=cnx.createStatement();
                stm.executeUpdate(strSqlInsUpd);
            }catch(Exception ex){
                ex.printStackTrace();
                booRetorno=false;
            }
            return booRetorno;
	}
	


    public ZafImp getObjZafImp() {
        return objZafImp;
    }

    public void setObjZafImp(ZafImp objZafImp) {
        this.objZafImp = objZafImp;
    }
    

    
    /**
     * Metodo para obtener los dias de pago de una forma de pago y si soporta cheque
     * @return List<HashMap<String,Object>>
     */
    private List<HashMap<String,Object>> obtenerDiaPagForPag(Connection cnx){
        
        ResultSet rstLoc;
        HashMap<String, Object> hasForPag=new HashMap<String, Object>();
        List <HashMap<String,Object>> lstHasForPag=new ArrayList<HashMap<String, Object>>();
        int intCant=0;
        ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
        try{
           if(cnx!=null){
                rstLoc=objZafGenFacDAO.obtenerDiaCreForPag(lstZafFac.get(0).getCo_empsol(), lstZafFac.get(0).getCo_forPag(),cnx);
                int x=0;
                while(rstLoc.next()){
                    hasForPag=new HashMap<String, Object>();
                    hasForPag.put("diascredito", rstLoc.getInt(1));
                    hasForPag.put("soportechq", rstLoc.getString("st_sop"));
                    lstHasForPag.add(hasForPag);
                }
                rstLoc.close();
                rstLoc=null;
            }
        }catch(Exception e) {  
           System.out.println(""+e );  
        }        
        return lstHasForPag;
    }
    
    
    public boolean insertarPagFac(Connection cnx){
        Calendar calFecAct2=Calendar.getInstance();
        //List<ZafPagFac> lstZafPagFac=new ArrayList<ZafPagFac>();
        ZafPagFac objZafPagFac;
        boolean booRetorno=true;
        BigDecimal bigRetFle=BigDecimal.ZERO, bigRetFleF=BigDecimal.ZERO, bigRetIva=BigDecimal.ZERO;
        String strSqlPolRet;
        ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
        BigDecimal bigPago=BigDecimal.ZERO, bigPagos=BigDecimal.ZERO;
        try{
            SimpleDateFormat fmt=new SimpleDateFormat("dd/MM/yyyy");
            Date datFecAct=fmt.parse(fmt.format(new Date()));
            SimpleDateFormat fmtStr=new SimpleDateFormat("yyyy/MM/dd");
            String strFechaActual= fmtStr.format(new Date());
            if(BigDecimal.valueOf((Double)hasTot.get("TOTFAC")).compareTo(BigDecimal.ZERO)>0){//Total de factura
                int intCantPag=objZafGenFacDAO.obtenerCantPagForPag(lstZafFac.get(0).getCo_forPag(), lstZafFac.get(0).getCo_empsol(),cnx);//Obtiene la cantidad de pagos de la forma de pago de la factura TBM_CABFORPAG
                List<HashMap<String, Object>> lstHasForPag=obtenerDiaPagForPag(cnx);// Obtiene los dia de pago de la factura en TBM_DETFORPAG
                int intVal=lstHasForPag.size()-(lstHasForPag.size()- intCantPag);
                double dblTotRet=dblRetFueGlo+ dblRetIvaGlo;
                BigDecimal bigBasPag= ((BigDecimal.valueOf((Double)hasTot.get("TOTFAC")).subtract(new BigDecimal(dblTotRet)).subtract(BigDecimal.valueOf((Double)hasTot.get("IVACOMSOL"))))).setScale(2, RoundingMode.HALF_UP);
                for(int i=0; i<intVal;i++){
                    Calendar calFecAct=Calendar.getInstance();
                    objZafPagFac=new ZafPagFac();
                    int intDiaCre= (Integer)lstHasForPag.get(i).get("diascredito");
                    String strSopChq= (String)lstHasForPag.get(i).get("soportechq");
                    if(intDiaCre>0){
                        calFecAct.add(java.util.Calendar.DATE, intDiaCre);
                    }
                    objZafPagFac.setDatFecVec(calFecAct.getTime());
                    objZafPagFac.setIntDiaCrd(intDiaCre);
                    bigPagos=((intCantPag==0)? BigDecimal.ZERO:(bigBasPag.divide(new BigDecimal(intCantPag),2,RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP);
                    bigPago=bigPago.add(bigPagos);
                    
                    if(i==(intVal-1)){
                        //bigPagos=  bigPagos.add((BigDecimal.valueOf((Double)hasTot.get("TOTFAC")).subtract(bigPago.add(new BigDecimal(dblTotRet)).setScale(2, BigDecimal.ROUND_HALF_UP)).setScale(2, RoundingMode.HALF_UP))).setScale(BigDecimal.ROUND_HALF_UP);                        
                        bigPagos=  bigPagos.add((BigDecimal.valueOf((Double)hasTot.get("TOTFAC")).subtract(BigDecimal.valueOf((Double)hasTot.get("IVACOMSOL"))).subtract(bigPago.add(new BigDecimal(dblTotRet)).setScale(2, BigDecimal.ROUND_HALF_UP)).setScale(2, RoundingMode.HALF_UP))).setScale(BigDecimal.ROUND_HALF_UP);                        
                        
                    }
                    objZafPagFac.setBigPorRet(BigDecimal.ZERO);
                    objZafPagFac.setBigMontPag(bigPagos.setScale(2, RoundingMode.HALF_UP));
                    objZafPagFac.setIntCodTipRet(0);
                    objZafPagFac.setStrSopChq(strSopChq);
                    lstZafPagFac.add(objZafPagFac);
                }
                
                Date datFecCor=null;
                for(ZafPagFac objZaf1:lstZafPagFac){
                    if(objZaf1.getBigPorRet().compareTo(BigDecimal.ZERO)==0){
                        datFecCor=objZaf1.getDatFecVec();
                        break;
                    }
                }
                Calendar calFecCor=Calendar.getInstance();
                calFecCor.setTime(datFecCor);
                String strFecFinAniAct="";
                if(calFecCor.get(Calendar.YEAR) > calFecAct2.get(Calendar.YEAR)){
                    System.out.println("Anio "+calFecAct2.get(Calendar.YEAR));                    
                    strFecFinAniAct="31/12/"+calFecAct2.get(Calendar.YEAR);
                    datFecCor=new Date(strFecFinAniAct);
                }
                for(ZafPagFac objZaf2:lstZafPagFac){
                    if(objZaf2.getBigPorRet().compareTo(BigDecimal.ZERO)>0){
                        objZaf2.setDatFecVec(/*new Date(strFecFinAniAct)*/datFecCor);
                    }
                }
                String StrSqlInsPagMov="";
                int x=1;
                
                Statement stmInsPagMov= cnx.createStatement();

                for(ZafPagFac objZaf:lstZafPagFac){
                    objZafGenFacDAO.booInsertarPagFac(objZaf, lstZafFac.get(0), ((Double)hasTot.get("CODFAC")).intValue(), x, cnx);
                    x++;
                }
                /*AGREGADO PARA COMPENSACION SOLIDARIA*/
                booRetorno= insertarPagFacCompSol(lstZafFac.get(0), ((Double)hasTot.get("CODFAC")).intValue(), x, cnx);
                /*AGREGADO PARA COMPENSACION SOLIDARIA*/
            }
        }catch(Exception ex){
            booRetorno=false;
            ex.printStackTrace();
        }
        return booRetorno;
    }
    
    public boolean insertarPagFacBig(Connection cnx){
        Calendar calFecAct2=Calendar.getInstance();
        //List<ZafPagFac> lstZafPagFac=new ArrayList<ZafPagFac>();
        ZafPagFac objZafPagFac;
        boolean booRetorno=true;
        BigDecimal bigRetFle=BigDecimal.ZERO, bigRetFleF=BigDecimal.ZERO, bigRetIva=BigDecimal.ZERO;
        String strSqlPolRet;
        ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
        BigDecimal bigPago=BigDecimal.ZERO, bigPagos=BigDecimal.ZERO;
        try{
            SimpleDateFormat fmt=new SimpleDateFormat("dd/MM/yyyy");
            Date datFecAct=fmt.parse(fmt.format(new Date()));
            SimpleDateFormat fmtStr=new SimpleDateFormat("yyyy/MM/dd");
            String strFechaActual= fmtStr.format(new Date());
            if(hasTotBig.get("TOTFAC").compareTo(BigDecimal.ZERO)>0){//Total de factura
                int intCantPag=objZafGenFacDAO.obtenerCantPagForPag(lstZafFac.get(0).getCo_forPag(), lstZafFac.get(0).getCo_empsol(),cnx);//Obtiene la cantidad de pagos de la forma de pago de la factura TBM_CABFORPAG
                List<HashMap<String, Object>> lstHasForPag=obtenerDiaPagForPag(cnx);// Obtiene los dia de pago de la factura en TBM_DETFORPAG
                int intVal=lstHasForPag.size()-(lstHasForPag.size()- intCantPag);
                BigDecimal bgdTotRet=bgdRetFueGlo.add(bgdRetIvaGlo);
                BigDecimal bigBasPag= ((hasTotBig.get("TOTFAC").subtract(bgdTotRet).subtract(hasTotBig.get("IVACOMSOL")))).setScale(2, RoundingMode.HALF_UP);
                for(int i=0; i<intVal;i++){
                    Calendar calFecAct=Calendar.getInstance();
                    objZafPagFac=new ZafPagFac();
                    int intDiaCre= (Integer)lstHasForPag.get(i).get("diascredito");
                    String strSopChq= (String)lstHasForPag.get(i).get("soportechq");
                    if(intDiaCre>0){
                        calFecAct.add(java.util.Calendar.DATE, intDiaCre);
                    }
                    objZafPagFac.setDatFecVec(calFecAct.getTime());
                    objZafPagFac.setIntDiaCrd(intDiaCre);
                    bigPagos=((intCantPag==0)? BigDecimal.ZERO:(bigBasPag.divide(new BigDecimal(intCantPag),2,RoundingMode.HALF_UP))).setScale(2, RoundingMode.HALF_UP);
                    bigPago=bigPago.add(bigPagos);
                    
                    if(i==(intVal-1)){
                        //bigPagos=  bigPagos.add((BigDecimal.valueOf((Double)hasTot.get("TOTFAC")).subtract(bigPago.add(new BigDecimal(dblTotRet)).setScale(2, BigDecimal.ROUND_HALF_UP)).setScale(2, RoundingMode.HALF_UP))).setScale(BigDecimal.ROUND_HALF_UP);                        
                        //bigPagos=  bigPagos.add((hasTotBig.get("TOTFAC").subtract(hasTotBig.get("IVACOMSOL")).subtract(bigPago.add(bgdTotRet).setScale(2, BigDecimal.ROUND_HALF_UP)).setScale(2, RoundingMode.HALF_UP))).setScale(BigDecimal.ROUND_HALF_UP);                        
                          bigPagos=  bigPagos.add((hasTotBig.get("TOTFAC").subtract(hasTotBig.get("IVACOMSOL")).subtract(bigPago.add(bgdTotRet).setScale(2, BigDecimal.ROUND_HALF_UP)).setScale(2, RoundingMode.HALF_UP)));                       
                        
                    }
                    objZafPagFac.setBigPorRet(BigDecimal.ZERO);
                    objZafPagFac.setBigMontPag(bigPagos.setScale(2, RoundingMode.HALF_UP));
                    objZafPagFac.setIntCodTipRet(0);
                    objZafPagFac.setStrSopChq(strSopChq);
                    lstZafPagFac.add(objZafPagFac);
                }
                
                Date datFecCor=null;
                for(ZafPagFac objZaf1:lstZafPagFac){
                    if(objZaf1.getBigPorRet().compareTo(BigDecimal.ZERO)==0){
                        datFecCor=objZaf1.getDatFecVec();
                        break;
                    }
                }
                Calendar calFecCor=Calendar.getInstance();
                calFecCor.setTime(datFecCor);
                String strFecFinAniAct="";
                if(calFecCor.get(Calendar.YEAR) > calFecAct2.get(Calendar.YEAR)){
                    System.out.println("Anio "+calFecAct2.get(Calendar.YEAR));                    
                    strFecFinAniAct="31/12/"+calFecAct2.get(Calendar.YEAR);
                    datFecCor=new Date(strFecFinAniAct);
                }
                for(ZafPagFac objZaf2:lstZafPagFac){
                    if(objZaf2.getBigPorRet().compareTo(BigDecimal.ZERO)>0){
                        objZaf2.setDatFecVec(datFecCor);
                    }
                }
                String StrSqlInsPagMov="";
                int x=1;
                
                Statement stmInsPagMov= cnx.createStatement();

                for(ZafPagFac objZaf:lstZafPagFac){
                    objZafGenFacDAO.booInsertarPagFac(objZaf, lstZafFac.get(0), ((BigDecimal)hasTotBig.get("CODFAC")).intValue(), x, cnx);
                    x++;
                }
                /*AGREGADO PARA COMPENSACION SOLIDARIA*/
                booRetorno= insertarPagFacCompSol(lstZafFac.get(0), ((BigDecimal)hasTotBig.get("CODFAC")).intValue(), x, cnx);
                /*AGREGADO PARA COMPENSACION SOLIDARIA*/
            }
        }catch(Exception ex){
            booRetorno=false;
            ex.printStackTrace();
        }
        return booRetorno;
    }    
    
    
    public boolean insertarPagFacCompSol(ZafFac objFac, int intCodFac, int intReg, Connection cnx){
        boolean booRet=true;
        String StrSqlInsPagMov="";
        Statement stmInsPag=null;
        try{
//        strSQL = "INSERT INTO tbm_pagmovinv(co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag,ne_diagra,nd_abo,st_sop,st_entsop,st_pos,st_reg , st_regrep, tx_tipreg ) " +
//        " values("+ intCodEmpDevVen+","+intCodLocDevVen+","+txtCodTipDoc.getText()+","+intCodDocDevVen+", 2 ,"+
//        VarC+",'"+strFecha+"',"+txtComSol.getText()+","+VarC+","+txtComSol.getText()+",'"+VarN+"','"+VarN+"','"+VarN+"','"+VarA+"','I','S')"; 
        if(objFac.getCo_emp()==2 && objFac.getCo_loc()==4 && bgdPorCmpSol.compareTo(BigDecimal.ZERO)>0){        
            StrSqlInsPagMov="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
            " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_abo, st_sop,st_entsop, st_pos,st_reg , st_regrep, tx_tipreg ) VALUES ("+
            objFac.getCo_emp()+","+objFac.getCo_loc()+","+ objFac.getCo_tipdoc()+","+intCodFac+","+intReg+",0,current_date,"+
             //+hasTot.get("IVACOMSOL")*(-1)+",0,"+hasTot.get("IVACOMSOL")+",'N','N','N','A','I','S')"; 
            hasTotBig.get("IVACOMSOL").multiply(new BigDecimal(-1))+",0,"+hasTotBig.get("IVACOMSOL")+",'N','N','N','A','I','S')"; 
            Date datFecServ=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos());

            stmInsPag=cnx.createStatement();
            stmInsPag.executeUpdate(StrSqlInsPagMov);

            ZafGenDocCobAut zafGenComSol= new ZafGenDocCobAut(objZafParSis, null);
            booRet=zafGenComSol.generaDocumentoCobroAutomatico(cnx,objFac.getCo_emp(),objFac.getCo_loc(),objFac.getCo_tipdoc(), intCodFac, datFecServ, 1);
            
        }
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
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
            //lstItmInv.add(dblCan<0?(dblCan*-1):dblCan);
            lstItmInv.add((Double.valueOf(dblCan<0?(dblCan*-1):dblCan)).toString());
            lstItmInv.add(Integer.valueOf(intCodBod).toString());
            lstItm.add(lstItmInv);
         }         
     }catch(Exception ex){
         ex.printStackTrace();     
     }
     return lstItm;
 }    
    
    
    public boolean actualizarStock(Connection cnx){
        String strQuery="";
        boolean booRet=true;
        Statement stmQuery;
        ResultSet rstDetSolDev=null, rstVerStkNeg=null;
        StringBuffer strConsulta=new StringBuffer("");
        StringBuffer strActInv=new StringBuffer("");
        StringBuffer[] strArrRes=new StringBuffer[2];
        ZafMetImp objZafMetImp=new ZafMetImp();
        int intFilasResultSolDev=0; // contador de filas de items que st_Ser='N'
        try{
            rstDetSolDev=objZafMetImp.obtenerDetSolDev(lstZafFac.get(0).getIntCodEmpSolDev(), lstZafFac.get(0).getIntCodLocSolDev(), lstZafFac.get(0).getIntCodTipDocSolDev(), lstZafFac.get(0).getIntCodDocSolDev(),cnx);
            while(rstDetSolDev.next()){
                ++intFilasResultSolDev;
                strArrRes=objZafMetImp.actualizarDetItmSol(rstDetSolDev.getBigDecimal("nd_canvolfac"), rstDetSolDev.getInt("co_itm"), lstZafFac.get(0).getIntCodEmpSolDev(), rstDetSolDev.getInt("co_bod"), strConsulta, strActInv);
                strActInv=strArrRes[0];
                strConsulta=strArrRes[1];
                /*AGREGADO PARA STOCK DISPONIBLE LIBRERIAS NUEVAS*/
                ZafStkInv invStock= new ZafStkInv(objZafParSis);
                if(invStock.actualizaInventario(cnx, objZafParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK_DIS, "-", 1, obtenerArrItmInv(cnx, objZafParSis.getCodigoEmpresa(), rstDetSolDev.getInt("co_itm"),rstDetSolDev.getBigDecimal("nd_canvolfac").doubleValue(),rstDetSolDev.getInt("co_bod")))){
                    if(!invStock.actualizaInventario(cnx, objZafParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK, "-", 0, obtenerArrItmInv(cnx, objZafParSis.getCodigoEmpresa(), rstDetSolDev.getInt("co_itm"),rstDetSolDev.getBigDecimal("nd_canvolfac").doubleValue(),rstDetSolDev.getInt("co_bod")))){
                                  return false;
                    }
                }else{   
                    return false;
                }                
                /*AGREGADO PARA STOCK DISPONIBLE LIBRERIAS NUEVAS */                
            }
            /*Statement stmAct=cnx.createStatement();
            stmAct.executeUpdate(strActInv.toString());*/
            if(intFilasResultSolDev>0){
                rstVerStkNeg=objZafMetImp.verificarStockNegativos(strConsulta,cnx);
                if(rstVerStkNeg!=null && rstVerStkNeg.next()){
                    booRet=false;
                }
            }
            rstDetSolDev.close();
            rstDetSolDev=null;

            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }
    
    
    public boolean asignarNumFacNue(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,Connection cnx){
        ZafMetImp objZafMetImp=new ZafMetImp();
        return objZafMetImp.asignarNumFacNue(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,cnx);
    }
    
    
    
    public boolean cargarTipEmp(int intCodEmp, int intCodLoc,Connection cnx ){
        boolean blnRes=false;
        ResultSet rstEmp;
        ZafGenFacDAO objZafGenFacDAO;
        String sSql,strDes="";
        try{
           objZafGenFacDAO=new ZafGenFacDAO();
           rstEmp=objZafGenFacDAO.obtenerTipEmp(intCodEmp, intCodLoc,cnx);
           if(rstEmp.next()){
               intCodTipPerEmp = rstEmp.getInt("co_tipper");
               strDes=rstEmp.getString("tx_descor");
               //bldivaEmp = rstEmp.getDouble("poriva");
               
           }
           rstEmp.close();
           rstEmp = null;
           objZafGenFacDAO=null;
           blnRes=true;
       }catch(SQLException e){  
           e.printStackTrace();
       }
       catch(Exception e){   
           e.printStackTrace();
       }
        return blnRes;
    }    
    
    
    private Map calcularSubtotalIvaxTipItm(int intCodEmpSolDev, int intLocSolDev,int intTipDocSolDev,int intCodSolDev, int intCodDocNueFac,Connection cnx){
	
            List<BigDecimal> lstBigtot=new ArrayList<BigDecimal>();
            hasTot=new HashMap<String, Double>();
            ResultSet rstCon=null;
            Statement stmSql=null;            
            double dblSubTotItmTra=0.0,dblSubTotItmSer=0.0,dblSubTotItmNoSer=0.0, dblSubTot=0.0, dblSutotxItm=0;
            double dblSubTotItmNoSer2=0.0, dblSubTotItmSer2=0.0,dblSubTotItmTra2=0.0, dblSubTotGrbIva=0.0, dblSubTotIvaCero=0.0, dblBasImpIvaCer=0, dblBasImpIvaGrab=0;
            double dblIvaItmTra=0.0,dblIvaItmSer=0.0,dblIvaItmNoSer=0.0, dblIvaTot=0.0;
            double dblIvaItmTraCompSol=0.0,dblIvaItmSerCompSol=0.0,dblIvaItmNoSerCompSol=0.0, dblIvaTotCompSol=0.0;
            double dblPorIvaDet=0.0d;
            double dblDesItmTra=0.0,dblDesItmSer=0.0,dblDesItmNoSer=0.0, dblDesTot=0.0;
            double dblTotFac=0.0, dblTotFacSinCom=0.0;
            int intNumFil=0,intEstIns=0;
            
            
            ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();

            try {

                    rstCon=objZafGenFacDAO.obtenerDatDevDet(intCodEmpSolDev, intLocSolDev, intTipDocSolDev, intCodSolDev, cnx);

                    while (rstCon.next()) {
                        dblBasImpIvaCer=0; 
                        dblBasImpIvaGrab=0;

                        if(rstCon.getString("st_ser").equals("T")){
                            dblSubTotItmTra=dblSubTotItmTra+calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));                            
                            //dblSubTotItmTra=dblSubTotItmTra+calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes"));                                                        
                            
                            dblSubTotItmTra2=calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                            
                            dblSubTotItmTra=objUti.redondear(dblSubTotItmTra, 2);
                            
                            dblSubTotItmTra2=objUti.redondear(dblSubTotItmTra2, 2);
                            
                            dblDesItmTra+= calcularDes(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                            if(rstCon.getString("st_iva").equals("S")){
                                
                                //dblIvaItmTra=calcularIvaCabFac2(dblSubTotItmTra, bldivaEmp);
                                
                                dblIvaItmTra+=calcularIvaCabFac2(dblSubTotItmTra2, bldivaEmp);
                                
                                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
				dblIvaItmTraCompSol+=objUti.redondear(calcularIvaCabFac2CompSold(dblSubTotItmTra2, bldCmpSol),2);
                                dblSubTotGrbIva+=dblSubTotItmTra2;
                                dblBasImpIvaGrab=dblSubTotItmTra2;
                                dblPorIvaDet=bldivaEmp;
                                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                            }else{
                                dblSubTotIvaCero+=dblSubTotItmTra2;
                                dblBasImpIvaCer=dblSubTotItmTra2;
                                dblPorIvaDet=0;
                            }
                        }else if(rstCon.getString("st_ser").equals("N")){
                            dblSubTotItmNoSer=dblSubTotItmNoSer+calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));                                                                                                               
                            
                            dblSubTotItmNoSer2=calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                            dblSubTotItmNoSer=objUti.redondear(dblSubTotItmNoSer, 2);
                            
                            dblSubTotItmNoSer2=objUti.redondear(dblSubTotItmNoSer2, 2);
                            
                            dblDesItmNoSer+=calcularDes(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                            if(rstCon.getString("st_iva").equals("S")){
                                //dblIvaItmNoSer=calcularIvaCabFac2(dblSubTotItmNoSer, bldivaEmp);
                                dblIvaItmNoSer+=calcularIvaCabFac2(dblSubTotItmNoSer2, bldivaEmp);
                                
                                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
				//dblIvaItmNoSerCompSol+=objUti.redondear(calcularIvaCabFac2CompSold(dblSubTotItmNoSer2, bldCmpSol),2);
                                /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/
                                dblIvaItmNoSerCompSol+=calcularIvaCabFac2CompSold(dblSubTotItmNoSer2, bldCmpSol);
                                /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/
                                dblSubTotGrbIva+=dblSubTotItmNoSer2;
                                dblBasImpIvaGrab=dblSubTotItmNoSer2;
                                dblPorIvaDet=bldivaEmp;
                                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                            }else{
                                dblSubTotIvaCero+=dblSubTotItmNoSer2;
                                dblBasImpIvaCer=dblSubTotItmNoSer2;
                                dblPorIvaDet=0;
                            }
                        }else if(rstCon.getString("st_ser").equals("S")){
                            dblSubTotItmSer=dblSubTotItmSer+calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));                                                                               
                            dblSubTotItmSer2=calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                            dblSubTotItmSer=objUti.redondear(dblSubTotItmSer, 2);
                            
                            dblSubTotItmSer2=objUti.redondear(dblSubTotItmSer2, 2);
                            
                            dblDesItmSer+=calcularDes(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                            if(rstCon.getString("st_iva").equals("S")){
                                //dblIvaItmSer=calcularIvaCabFac2(dblSubTotItmSer, bldivaEmp);
                                dblIvaItmSer+=calcularIvaCabFac2(dblSubTotItmSer2, bldivaEmp);
                                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
				dblIvaItmSerCompSol+=calcularIvaCabFac2CompSold(dblSubTotItmSer2, bldCmpSol);
                                dblSubTotGrbIva+=dblSubTotItmSer2;
                                dblBasImpIvaGrab=dblSubTotItmSer2;
                                dblPorIvaDet=bldivaEmp;
                                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                            }else{
                                dblSubTotIvaCero+=dblSubTotItmSer2;
                                dblBasImpIvaCer=dblSubTotItmSer2;
                                dblPorIvaDet=0;
                            }
                        }
                        dblSutotxItm=calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));
                        
                        intNumFil++;
                        if(intEstIns==1) strInsDetCabMovInv.append(" UNION ALL ");
                        // lstZafFac.get(i).getCo_loc(), ((lstZafFac.get(i).getCo_tipdoc()==1)?228:lstZafFac.get(i).getCo_tipdoc()
                        
                        BigDecimal bigCostTot=BigDecimal.valueOf(rstCon.getDouble("nd_cosuni")).multiply(BigDecimal.valueOf(rstCon.getDouble("nd_canvolfac"))).multiply(new BigDecimal(-1));
                        /*bigCostTot=bigCosUni.multiply(bigCanVolFac);
                        bigCostTot= bigCostTot.multiply(new BigDecimal(-1));*/
                        
                        
                        strInsDetCabMovInv.append("SELECT "+intCodEmpSolDev+","+lstZafFac.get(0).getCo_loc()+","+lstZafFac.get(0).getCo_tipdoc()+","+intCodDocNueFac+","+intNumFil+",'" +
                                    rstCon.getString("tx_codalt")+"','"+rstCon.getString("tx_codalt")+"',"+rstCon.getString("co_itm")+", "+rstCon.getString("co_itm")+", "+
                                    codificarCadQry(rstCon.getString("tx_nomitm"))+",'"+rstCon.getString("tx_unimed")+"',"+rstCon.getString("co_bod")+","+
                                    rstCon.getDouble("nd_canvolfac")*(-1)+","+/*dblTotal*/(dblSutotxItm*(-1))+", "+rstCon.getString("nd_cosuni") + ", 0 , "+rstCon.getDouble("nd_preuni") + ", " +
                                    //rstCon.getDouble("nd_pordes") + ", '"+rstCon.getString("st_ivacom")+ "' " +
                                    rstCon.getDouble("nd_pordes") + 
                                    ","+bigCostTot+",'I', '"+rstCon.getString("st_meringegrfisbod")+"', 0, "+
                                    rstCon.getString("nd_preunivenlis")+", "+rstCon.getString("nd_pordesvenmax")+", " +
                                    //" "+rstCon.getString("ne_numfil")+" ");
                                    " "+rstCon.getString("ne_numfil")+" "+","+dblBasImpIvaGrab*(-1)+","+dblBasImpIvaCer*(-1)+","+dblPorIvaDet);
                        intEstIns=1;
                        
                    }
                    dblSubTot+=dblSubTotItmTra+dblSubTotItmNoSer+dblSubTotItmSer;
                    dblSubTot=objUti.redondear(dblSubTot, 2);
                    dblIvaTot+=dblIvaItmTra+dblIvaItmNoSer+dblIvaItmSer;
                    dblIvaTot= objUti.redondear(dblIvaTot, 2);
                    
                    /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/
                    dblIvaItmNoSer=objUti.redondear(dblIvaItmNoSer,2);
                    dblIvaItmNoSerCompSol=objUti.redondear(dblIvaItmNoSerCompSol,2);
                    /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/
                    
                    /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                    /*CAMBIO TEMPORAL 16-05-2017 CHEMOMATEO*/
                    //dblIvaTotCompSol+=dblIvaItmTraCompSol+dblIvaItmNoSerCompSol+dblIvaItmSerCompSol;
                    dblIvaTotCompSol=calcularIvaCabFac2CompSold(dblSubTotGrbIva, bldCmpSol);;
                    dblIvaTotCompSol=objUti.redondear(dblIvaTotCompSol, 2);
                    
                    
                    /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                    
                    dblDesTot+=dblDesItmTra+dblDesItmNoSer+dblDesItmSer;
                    
                    /*dblTotFac=(dblSubTot+dblIvaTot)-dblIvaTotCompSol;
                    dblTotFacSinCom=(dblSubTot+dblIvaTot);*/
                    dblTotFac=(dblSubTot+dblIvaTot);
                    dblTotFacSinCom=(dblSubTot+dblIvaTot)-dblIvaTotCompSol;                    
                    dblTotFac=objUti.redondear(dblTotFac, 2);
                    hasTot.put("SUBTOITMTRA", dblSubTotItmTra);
                    hasTot.put("IVAITMTRA", dblIvaItmTra);
                    hasTot.put("CMPSOLTRA", dblIvaItmTraCompSol);
                    hasTot.put("SUBTOITMNOSER", dblSubTotItmNoSer);
                    hasTot.put("IVAITMNOSER", dblIvaItmNoSer);
                    hasTot.put("CMPSOLNOSER", dblIvaItmNoSerCompSol);
                    hasTot.put("SUBTOITMSER", dblSubTotItmSer);
                    hasTot.put("IVAITMSER", dblIvaItmSer);
                    hasTot.put("CMPSOLSER", dblIvaItmSerCompSol);                    
                    hasTot.put("SUBTOTAL", dblSubTot);
                    hasTot.put("IVA", dblIvaTot);
                    hasTot.put("TOTFAC", dblTotFac);
                    hasTot.put("TOTFACSINCOM",dblTotFacSinCom);
                    hasTot.put("TOTDES", dblDesTot);
                    hasTot.put("CODFAC", new Double(intCodDocNueFac).doubleValue());
                    
                    /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
		    hasTot.put("IVACOMSOL", dblIvaTotCompSol);
                    hasTot.put("SUBTOTGRBIVA", dblSubTotGrbIva);
                    hasTot.put("SUBTOTIVACERO", dblSubTotIvaCero);
                    hasTot.put("PORCOMSOL", bldCmpSol);
                    /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                    
                    //stmSql.close();
                    rstCon.close();
            } catch (SQLException e) {
                    System.out.println(e.getMessage());
            }
            return hasTot;
		
	}//fin de insertar   
    
    

    private Map calcularSubtotalIvaxTipItmBgd(int intCodEmpSolDev, int intLocSolDev,int intTipDocSolDev,int intCodSolDev, int intCodDocNueFac,Connection cnx){
	
        List<BigDecimal> lstBigtot=new ArrayList<BigDecimal>();
        hasTot=new HashMap<String, Double>();
        ResultSet rstCon=null;
        Statement stmSql=null;            
        BigDecimal bgdSubTotItmTra=BigDecimal.ZERO,bgdSubTotItmSer=BigDecimal.ZERO,bgdSubTotItmNoSer=BigDecimal.ZERO, bgdSubTot=BigDecimal.ZERO, bgdSutotxItm=BigDecimal.ZERO;
        BigDecimal bgdSubTotItmNoSer2=BigDecimal.ZERO, bgdSubTotItmSer2=BigDecimal.ZERO,bgdSubTotItmTra2=BigDecimal.ZERO, bgdSubTotGrbIva=BigDecimal.ZERO, bgdSubTotIvaCero=BigDecimal.ZERO, bgdBasImpIvaCer=BigDecimal.ZERO, bgdBasImpIvaGrab=BigDecimal.ZERO;
        BigDecimal bgdIvaItmTra=BigDecimal.ZERO,bgdIvaItmSer=BigDecimal.ZERO,bgdIvaItmNoSer=BigDecimal.ZERO, bgdIvaTot=BigDecimal.ZERO;
        BigDecimal bgdIvaItmTraCompSol=BigDecimal.ZERO,bgdIvaItmSerCompSol=BigDecimal.ZERO,bgdIvaItmNoSerCompSol=BigDecimal.ZERO, bgdIvaTotCompSol=BigDecimal.ZERO;
        double dblPorIvaDet=0.0d;
        BigDecimal bgdDesItmTra=BigDecimal.ZERO,bgdDesItmSer=BigDecimal.ZERO,bgdDesItmNoSer=BigDecimal.ZERO, bgdDesTot=BigDecimal.ZERO;
        BigDecimal bgdTotFac=BigDecimal.ZERO, bgdTotFacSinCom=BigDecimal.ZERO;
        int intNumFil=0,intEstIns=0;


        ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();

        try {

                rstCon=objZafGenFacDAO.obtenerDatDevDet(intCodEmpSolDev, intLocSolDev, intTipDocSolDev, intCodSolDev, cnx);

                while (rstCon.next()) {
                    bgdBasImpIvaCer=BigDecimal.ZERO; 
                    bgdBasImpIvaGrab=BigDecimal.ZERO;

                    if(rstCon.getString("st_ser").equals("T")){
                        //dblSubTotItmTra=dblSubTotItmTra+calcularSubCabFac2(rstCon.getDouble("nd_preuni"), rstCon.getDouble("nd_canvolfac"),rstCon.getDouble("nd_pordes"));                            
                        bgdSubTotItmTra=bgdSubTotItmTra.add(calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes")));                                                        

                        bgdSubTotItmTra2=calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes"));

                        bgdSubTotItmTra=bgdSubTotItmTra.setScale(2, RoundingMode.HALF_UP);

                        bgdSubTotItmTra2=bgdSubTotItmTra2.setScale(2, RoundingMode.HALF_UP);

                        bgdDesItmTra=bgdDesItmTra.add(calcularDesBig(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes")));
                        if(rstCon.getString("st_iva").equals("S")){

                            //dblIvaItmTra=calcularIvaCabFac2(dblSubTotItmTra, bldivaEmp);

                            bgdIvaItmTra=bgdIvaItmTra.add(bgdIvaItmTra.add(calcularIvaCabFac3(bgdSubTotItmTra2, bgdIvaEmp)));

                            /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                            bgdIvaItmTraCompSol=calcularIvaCabFac2CompSold3(bgdSubTotItmTra2, bgdPorCmpSol);
                            bgdSubTotGrbIva=bgdSubTotGrbIva.add(bgdSubTotItmTra2);
                            bgdBasImpIvaGrab=bgdSubTotItmTra2;
                            dblPorIvaDet=bldivaEmp;
                            /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                        }else{
                            bgdSubTotIvaCero=bgdSubTotIvaCero.add(bgdSubTotItmTra2);
                            bgdBasImpIvaCer=bgdSubTotItmTra2;
                            dblPorIvaDet=0;
                        }
                    }else if(rstCon.getString("st_ser").equals("N")){
                        bgdSubTotItmNoSer=bgdSubTotItmNoSer.add(calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes")));                                                                                                               

                        bgdSubTotItmNoSer2=calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes"));
                        bgdSubTotItmNoSer=bgdSubTotItmNoSer.setScale(2, RoundingMode.HALF_UP);

                        bgdSubTotItmNoSer2=bgdSubTotItmNoSer2.setScale(2, RoundingMode.HALF_UP);

                        bgdDesItmNoSer =bgdDesItmNoSer.add(calcularDesBig(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes")));
                        if(rstCon.getString("st_iva").equals("S")){
                            //dblIvaItmNoSer=calcularIvaCabFac2(dblSubTotItmNoSer, bldivaEmp);
                            bgdIvaItmNoSer=bgdIvaItmNoSer.add(calcularIvaCabFac3(bgdSubTotItmNoSer2, bgdIvaEmp));

                            /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                            //dblIvaItmNoSerCompSol+=objUti.redondear(calcularIvaCabFac2CompSold(dblSubTotItmNoSer2, bldCmpSol),2);
                            /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/
                            bgdIvaItmNoSerCompSol=bgdIvaItmNoSerCompSol.add(calcularIvaCabFac2CompSold3(bgdSubTotItmNoSer2, bgdPorCmpSol));
                            /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/
                            bgdSubTotGrbIva=bgdSubTotGrbIva.add(bgdSubTotItmNoSer2);
                            bgdBasImpIvaGrab=bgdSubTotItmNoSer2;
                            dblPorIvaDet=bldivaEmp;
                            /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                        }else{
                            bgdSubTotIvaCero= bgdSubTotIvaCero.add(bgdSubTotItmNoSer2);
                            bgdBasImpIvaCer=bgdSubTotItmNoSer2;
                            dblPorIvaDet=0;
                        }
                    }else if(rstCon.getString("st_ser").equals("S")){
                        bgdSubTotItmSer=bgdSubTotItmSer.add(calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes")));                                                                               
                        bgdSubTotItmSer2=calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes"));
                        bgdSubTotItmSer=bgdSubTotItmSer.setScale(2, RoundingMode.HALF_UP);

                        bgdSubTotItmSer2=bgdSubTotItmSer2.setScale(2, RoundingMode.HALF_UP);

                        bgdDesItmSer=bgdDesItmSer.add(calcularDesBig(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes")));
                        if(rstCon.getString("st_iva").equals("S")){
                            //dblIvaItmSer=calcularIvaCabFac2(dblSubTotItmSer, bldivaEmp);
                            bgdIvaItmSer=bgdIvaItmSer.add(calcularIvaCabFac3(bgdSubTotItmSer2, bgdIvaEmp));
                            /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                            bgdIvaItmSerCompSol=bgdIvaItmSerCompSol.add(calcularIvaCabFac2CompSold3(bgdSubTotItmSer2, bgdPorCmpSol));
                            bgdSubTotGrbIva=bgdSubTotGrbIva.add(bgdSubTotItmSer2);
                            bgdBasImpIvaGrab=bgdSubTotItmSer2;
                            dblPorIvaDet=bldivaEmp;
                            /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                        }else{
                            bgdSubTotIvaCero=bgdSubTotIvaCero.add(bgdSubTotItmSer2);
                            bgdBasImpIvaCer=bgdSubTotItmSer2;
                            dblPorIvaDet=0;
                        }
                    }
                    bgdSutotxItm=calcularSubCabFac3(rstCon.getBigDecimal("nd_preuni"), rstCon.getBigDecimal("nd_canvolfac"),rstCon.getBigDecimal("nd_pordes"));

                    intNumFil++;
                    if(intEstIns==1) strInsDetCabMovInv.append(" UNION ALL ");
                    // lstZafFac.get(i).getCo_loc(), ((lstZafFac.get(i).getCo_tipdoc()==1)?228:lstZafFac.get(i).getCo_tipdoc()

                    BigDecimal bigCostTot=BigDecimal.valueOf(rstCon.getDouble("nd_cosuni")).multiply(BigDecimal.valueOf(rstCon.getDouble("nd_canvolfac"))).multiply(new BigDecimal(-1));
                    /*bigCostTot=bigCosUni.multiply(bigCanVolFac);
                    bigCostTot= bigCostTot.multiply(new BigDecimal(-1));*/


                    strInsDetCabMovInv.append("SELECT "+intCodEmpSolDev+","+lstZafFac.get(0).getCo_loc()+","+lstZafFac.get(0).getCo_tipdoc()+","+intCodDocNueFac+","+intNumFil+",'" +
                                rstCon.getString("tx_codalt")+"','"+rstCon.getString("tx_codalt")+"',"+rstCon.getString("co_itm")+", "+rstCon.getString("co_itm")+", "+
                                codificarCadQry(rstCon.getString("tx_nomitm"))+",'"+rstCon.getString("tx_unimed")+"',"+rstCon.getString("co_bod")+","+
                                rstCon.getBigDecimal("nd_canvolfac").multiply(new BigDecimal(-1))+","+/*dblTotal*/(bgdSutotxItm.multiply(new BigDecimal(-1)))+", "+rstCon.getString("nd_cosuni") + ", 0 , "+rstCon.getBigDecimal("nd_preuni") + ", " +
                                //rstCon.getDouble("nd_pordes") + ", '"+rstCon.getString("st_ivacom")+ "' " +
                                rstCon.getBigDecimal("nd_pordes") + 
                                ","+bigCostTot+",'I', '"+rstCon.getString("st_meringegrfisbod")+"', 0, "+
                                rstCon.getString("nd_preunivenlis")+", "+rstCon.getString("nd_pordesvenmax")+", " +
                                //" "+rstCon.getString("ne_numfil")+" ");
                                " "+rstCon.getString("ne_numfil")+" "+","+bgdBasImpIvaGrab.multiply(new BigDecimal(-1))+","+bgdBasImpIvaCer.multiply(new BigDecimal(-1))+","+dblPorIvaDet);
                    intEstIns=1;

                }
                bgdSubTot=bgdSubTot.add(bgdSubTotItmTra.add(bgdSubTotItmNoSer).add(bgdSubTotItmSer));
                bgdSubTot=bgdSubTot.setScale(2, RoundingMode.HALF_UP);
                bgdIvaTot=bgdIvaTot.add(bgdIvaItmTra.add(bgdIvaItmNoSer).add(bgdIvaItmSer));
                bgdIvaTot= bgdIvaTot.setScale(2, RoundingMode.HALF_UP);
                /*AGREGADO PARA EL CAMBIO DE REGRESO AL 12% DE IVA EL 1 DE JUNIO 2017*/
                bgdIvaTot=calcularIvaCabFac3(bgdSubTotGrbIva, bgdIvaEmp);
                /*AGREGADO PARA EL CAMBIO DE REGRESO AL 12% DE IVA EL 1 DE JUNIO 2017*/

                /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/
                bgdIvaItmNoSer=bgdIvaItmNoSer.setScale(2, RoundingMode.HALF_UP);
                bgdIvaItmNoSerCompSol=bgdIvaItmNoSerCompSol.setScale(2, RoundingMode.HALF_UP);
                /*CAMBIADO MOMENTANEAMENTE 03-03-2017*/

                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                /*CAMBIO TEMPORAL 16-05-2017 CHEMOMATEO*/
                //dblIvaTotCompSol+=dblIvaItmTraCompSol+dblIvaItmNoSerCompSol+dblIvaItmSerCompSol;
                bgdIvaTotCompSol=calcularIvaCabFac2CompSold3(bgdSubTotGrbIva, bgdPorCmpSol);;
                bgdIvaTotCompSol=bgdIvaTotCompSol.setScale(2, RoundingMode.HALF_UP);


                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/

                bgdDesTot=bgdDesTot.add(bgdDesItmTra.add(bgdDesItmNoSer).add(bgdDesItmSer));

                /*dblTotFac=(dblSubTot+dblIvaTot)-dblIvaTotCompSol;
                dblTotFacSinCom=(dblSubTot+dblIvaTot);*/
                bgdTotFac=(bgdSubTot.add(bgdIvaTot));
                bgdTotFacSinCom=(bgdSubTot.add(bgdIvaTot)).subtract(bgdIvaTotCompSol);                    
                bgdTotFac=bgdTotFac.setScale(2, RoundingMode.HALF_UP);
                hasTotBig.put("SUBTOITMTRA", bgdSubTotItmTra);
                hasTotBig.put("IVAITMTRA", bgdIvaItmTra);
                hasTotBig.put("CMPSOLTRA", bgdIvaItmTraCompSol);
                hasTotBig.put("SUBTOITMNOSER", bgdSubTotItmNoSer);
                hasTotBig.put("IVAITMNOSER", bgdIvaItmNoSer);
                hasTotBig.put("CMPSOLNOSER", bgdIvaItmNoSerCompSol);
                hasTotBig.put("SUBTOITMSER", bgdSubTotItmSer);
                hasTotBig.put("IVAITMSER", bgdIvaItmSer);
                hasTotBig.put("CMPSOLSER", bgdIvaItmSerCompSol);                    
                hasTotBig.put("SUBTOTAL", bgdSubTot);
                hasTotBig.put("IVA", bgdIvaTot);
                hasTotBig.put("TOTFAC", bgdTotFac);
                hasTotBig.put("TOTFACSINCOM",bgdTotFacSinCom);
                hasTotBig.put("TOTDES", bgdDesTot);
                hasTotBig.put("CODFAC", new BigDecimal(intCodDocNueFac));

                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/
                hasTotBig.put("IVACOMSOL", bgdIvaTotCompSol);
                hasTotBig.put("SUBTOTGRBIVA", bgdSubTotGrbIva);
                hasTotBig.put("SUBTOTIVACERO", bgdSubTotIvaCero);
                hasTotBig.put("PORCOMSOL", bgdPorCmpSol);
                /*AGREGADO POR CAMBIO COMPENSACION SOLIDARIA*/

                //stmSql.close();
                rstCon.close();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return hasTotBig;
		
    }//fin de insertar 
    
    
    
    /**
     * Metodo que obtiene las valores por las retenciones.
     * @param conn conexion
     * @param intCodMot codigo del motivo documento
     * @param intCodEmp codigo de la empresa
     * @param dblSubTot subtotal sobre el que se calcula la retencion subtotal.
     * @param dblTotIva iva sobre el que se calcula la retenciones en el iva.
     * @param intCodTipPerCli 
     */
    private void cargaForPag(java.sql.Connection conn, int intCodMot, int intCodEmp, double dblSubTot, double dblTotIva, int intCodTipPerCli, int intTipForPag) {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL = "";
        ZafGenFacDAO objGenFacDAO=new ZafGenFacDAO();
        ZafPagFac objZafPagFac;
        double dblRetFue = 0, dblRetIva = 0;
        
        try {

            if (conn != null) {
                
                if(dblSubTot > 0.0){

                    stm = conn.createStatement();
                    String strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

                    SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");

                    Date datFecAct=fmt.parse(strFecSisBase);

                    rst=objGenFacDAO.obtenerPolRetSri(intCodEmp, intCodMot, intCodTipPerEmp, strFecSisBase, intCodTipPerCli, cnx);
                    if (intTipForPag!=4){

                        while (rst.next()) {

                            objZafPagFac=new ZafPagFac();
                            objZafPagFac.setIntDiaCrd(0);
                            objZafPagFac.setDatFecVec(datFecAct);

                            java.util.Vector vecReg = new java.util.Vector();
                            if (rst.getString("tx_aplret").equals("S")) {                        
                                dblRetFue = objUti.redondear((dblSubTot * (rst.getDouble("nd_porret") / 100)), 2);
                                objZafPagFac.setBigMontPag(new BigDecimal(dblRetFue).setScale(2, BigDecimal.ROUND_HALF_UP));
                                objZafPagFac.setBigPorRet(new BigDecimal(rst.getDouble("nd_porret")));
                                objZafPagFac.setIntDiaGra(0);
                                objZafPagFac.setStrSopChq("N");
                                dblRetFueGlo += dblRetFue;
                            }
                            if (rst.getString("tx_aplret").equals("I")) {
                                if (dblTotIva > 0) {                            
                                    dblRetIva = objUti.redondear((dblTotIva * (rst.getDouble("nd_porret") / 100)), 2);
                                    objZafPagFac.setBigMontPag(new BigDecimal(dblRetIva).setScale(2, BigDecimal.ROUND_HALF_UP));
                                    objZafPagFac.setBigPorRet(new BigDecimal(rst.getDouble("nd_porret")));
                                    objZafPagFac.setIntDiaGra(0);
                                    objZafPagFac.setStrSopChq("N");
                                 dblRetIvaGlo += dblRetIva;

                                }
                            }
                            objZafPagFac.setIntCodTipRet(Integer.parseInt(rst.getString("co_tipret")));
                            lstZafPagFac.add(objZafPagFac);

                        }
                    }
                    rst.close();
                    rst = null;
                }

            }
        } catch (SQLException Evt) {
            Evt.printStackTrace();
            //objUti.mostrarMsgErr_F1(jfrThis, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            //objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }   
    
    
    
/**
     * Metodo que obtiene las valores por las retenciones.
     * @param conn conexion
     * @param intCodMot codigo del motivo documento
     * @param intCodEmp codigo de la empresa
     * @param dblSubTot subtotal sobre el que se calcula la retencion subtotal.
     * @param dblTotIva iva sobre el que se calcula la retenciones en el iva.
     * @param intCodTipPerCli 
     */
    private void cargaForPagBig(java.sql.Connection conn, int intCodMot, int intCodEmp, BigDecimal bgdSubTot, BigDecimal bgdTotIva, int intCodTipPerCli, int intTipForPag) {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL = "";
        ZafGenFacDAO objGenFacDAO=new ZafGenFacDAO();
        ZafPagFac objZafPagFac;
        BigDecimal bgdRetFue = BigDecimal.ZERO, bgdRetIva = BigDecimal.ZERO;
        
        try {

            if (conn != null) {
                
                if(bgdSubTot.compareTo(BigDecimal.ZERO) > 0){

                    stm = conn.createStatement();
                    String strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

                    SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");

                    Date datFecAct=fmt.parse(strFecSisBase);

                    rst=objGenFacDAO.obtenerPolRetSri(intCodEmp, intCodMot, intCodTipPerEmp, strFecSisBase, intCodTipPerCli, cnx);
                    if (intTipForPag!=4){

                        while (rst.next()) {

                            objZafPagFac=new ZafPagFac();
                            objZafPagFac.setIntDiaCrd(0);
                            objZafPagFac.setDatFecVec(datFecAct);

                            java.util.Vector vecReg = new java.util.Vector();
                            if (rst.getString("tx_aplret").equals("S")) {       
                                //dblRetFue = objUti.redondear((dblSubTot * (rst.getDouble("nd_porret") / 100)), 2);
                                bgdRetFue = bgdSubTot.multiply(rst.getBigDecimal("nd_porret").divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
                                objZafPagFac.setBigMontPag(bgdRetFue);
                                objZafPagFac.setBigPorRet(rst.getBigDecimal("nd_porret"));
                                objZafPagFac.setIntDiaGra(0);
                                objZafPagFac.setStrSopChq("N");
                                bgdRetFueGlo = bgdRetFueGlo.add(bgdRetFue);
                            }
                            if (rst.getString("tx_aplret").equals("I")) {
                                /*CALCULO DEL IVA AGREGADO*/
                                BigDecimal bgdIva=calcularIvaCabFac3(bgdSubTot, bgdIvaEmp);
                                BigDecimal bgdCompSol=calcularIvaCabFac2CompSold3(bgdSubTot, bgdPorCmpSol);
                                BigDecimal bgdIvaSinComp=bgdIva.subtract(bgdCompSol).setScale(2, RoundingMode.HALF_UP);
                                /*CALCULO DEL IVA AGREGADO*/
                                //if (bgdTotIva.compareTo(BigDecimal.ZERO) > 0) {
                                if (bgdIvaSinComp.compareTo(BigDecimal.ZERO) > 0) {
                                    System.out.println("hola prin");
                                    bgdRetIva = (bgdIvaSinComp.multiply(rst.getBigDecimal("nd_porret").divide(new BigDecimal(100)))).setScale(2, RoundingMode.HALF_UP);
                                    objZafPagFac.setBigMontPag(bgdRetIva);
                                    objZafPagFac.setBigPorRet(rst.getBigDecimal("nd_porret"));
                                    objZafPagFac.setIntDiaGra(0);
                                    objZafPagFac.setStrSopChq("N");
                                    //dblRetIvaGlo += dblRetIva;
                                    bgdRetIvaGlo=bgdRetIvaGlo.add(bgdRetIva);

                                }
                            }
                            objZafPagFac.setIntCodTipRet(Integer.parseInt(rst.getString("co_tipret")));
                            lstZafPagFac.add(objZafPagFac);

                        }
                    }
                    rst.close();
                    rst = null;
                }

            }
        } catch (SQLException Evt) {
            Evt.printStackTrace();
            //objUti.mostrarMsgErr_F1(jfrThis, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            //objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }     
    
    

    public HashMap<String, Double> getHasTot() {
        return hasTot;
    }

    public void setHasTot(HashMap<String, Double> hasTot) {
        this.hasTot = hasTot;
    }

    public HashMap<String, BigDecimal> getHasTotBig() {
        return hasTotBig;
    }

    public void setHasTotBig(HashMap<String, BigDecimal> hasTotBig) {
        this.hasTotBig = hasTotBig;
    }
    
    
   
    
    
}//fin de clase
