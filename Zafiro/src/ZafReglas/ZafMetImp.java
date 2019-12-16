/*
 * Esta clase contiene los métodos que se invocan para insersión de la Órden de Despacho
 * en las clases de tbm_cabGuiRem y tbm_cab_movinv que respectivamente son las guías de remisión
 * y la cabecera del movimiento del inventario, que es también donde se generó la factura.
 */
package ZafReglas;

import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author sistemas4
 */
public class ZafMetImp {
    private ZafCliTra ocli=new ZafCliTra();
    private ZafTraBean ot=new ZafTraBean();
    private ZafImp impresion;
    //ZafCon oc=new ZafCon();
    ResultSet rs;
    Statement sql;
    String query="";
    int intEmp;
    int intLoc;
    private final String strVersion = " v0.4";
    private ZafUtil objUti=new ZafUtil();
    
    
    public ZafMetImp(ZafTraBean obt){
        this.setOt(obt);
    }
    
    public ZafMetImp(ZafImp impresion) {
        this.setImpresion(impresion);
    }

    public ZafMetImp(){
    
    }
    
    /*public ZafMetImp(ZafImp impresion,ZafCon con) {
        this.setImpresion(impresion);
        oc=con;
    } */   
    
    public void impresionNormal2(Connection con) throws SQLException{
            int res=0;
            int intBodPtoPda=obtenerBodPtoPda(con);
            ZafGenGuiRem objZafGuiRem=new ZafGenGuiRem();
            ZafReglas.ZafGuiRem.ZafGenGuiRem objGuiRem=new ZafReglas.ZafGuiRem.ZafGenGuiRem();
            int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(con, impresion.getEmp(), intBodPtoPda);
            String[] strRet=objZafGuiRem.obtenerRptImpOD(intCodBodGrp);          
            
			if(strRet[2]!=null && !strRet[2].equals("") && strRet[3]!=null && !strRet[3].equals("") ){
				sql=con.createStatement();
				query="select ne_ultnumorddes as numOrdDes from tbm_loc where co_emp="+strRet[2]+" and co_loc="+strRet[3];
				rs=sql.executeQuery(query);

				if (rs.next()) {
					res=rs.getInt("numOrdDes");
					res++;
				}
				sql.close();
				actualizarUltDoc2(res, con, strRet[2], strRet[3]);
				int coDoc=obtenerDocGui(con);
				actualizarCabGuiRem2(res, con, coDoc);
				ZafImpOrd od=new ZafImpOrd();             
				con.commit();
				//od.impresionGuiaRemAutBod2(con, impresion.getEmp(), impresion.getLoc(), 102, coDoc,intBodPtoPda);        
				objGuiRem.enviarPulsoImpresionGui("imprimeODLocal"+"-"+impresion.getEmp()+"-"+impresion.getLoc()+"-102-"+coDoc+"-"+intBodPtoPda, 6000);
            } 
        
    }
    
    /**
     * Genera las OD locales por Autorizacion de ordenes de despacho.
     * @param con conexion de acceso a datos.
     * @throws SQLException Excepcion de SQL.
     */
    public void impresionNormal2AutOD(Connection con) throws SQLException{
            int res=0;            
            int oo=0;
            int intBodPtoPda=obtenerBodPtoPdaAutOD(con);
            ZafReglas.ZafGuiRem.ZafGenGuiRem objGuiRem=new ZafReglas.ZafGuiRem.ZafGenGuiRem();
            ZafGenGuiRem objZafGuiRem=new ZafGenGuiRem();
            int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(con, impresion.getEmp(), intBodPtoPda);
            String[] strRet=objZafGuiRem.obtenerRptImpOD(intCodBodGrp);          
            
            sql=con.createStatement();
            query="select ne_ultnumorddes as numOrdDes from tbm_loc where co_emp="+strRet[2]+" and co_loc="+strRet[3];
            rs=sql.executeQuery(query);

            if (rs.next()) {
                res=rs.getInt("numOrdDes");
                res++;
            }
            sql.close();
            actualizarUltDoc2(res, con, strRet[2], strRet[3]);
            //int coDoc=obtenerDocGui(con);
            actualizarCabGuiRem2(res, con, impresion.getCoDoc());
            ZafImpOrd od=new ZafImpOrd();             
            con.commit();
            objGuiRem.enviarPulsoImpresionGui("imprimeODLocal"+"-"+impresion.getEmp()+"-"+impresion.getLoc()+"-102-"+impresion.getCoDoc()+"-"+intBodPtoPda, 6000);
            //od.impresionGuiaRemAutBod2(con, impresion.getEmp(), impresion.getLoc(), 102, impresion.getCoDoc(),intBodPtoPda);
    }    
    
    public void impresionNormal(Connection con) throws SQLException{

        
       int res=0;
       
        
        try {
            con.setAutoCommit(false);
            //sql= oc.ocon.createStatement();
            
            
            if (((impresion.getEmp()==1 && impresion.getLoc()==10) || (impresion.getEmp()==2 && impresion.getLoc()==5))
                     || (impresion.getEmp()==4 && impresion.getLoc()==10)) {
//                impresion.setEmp(1);
//                impresion.setLoc(10);
                intEmp=1;
                intLoc=10;
                
                
                sql=con.createStatement();
                query="select ne_ultnumorddes as numOrdDes from tbm_loc where co_emp="+intEmp+" and co_loc="+intLoc;
                rs=sql.executeQuery(query);

                if (rs.next()) {
                    res=rs.getInt("numOrdDes");
                    res++;

                }
                sql.close();
                System.out.println(query);
                
                
            }else{
            
            
            sql=con.createStatement();
            query="select ne_ultnumorddes as numOrdDes from tbm_loc where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc();
            rs=sql.executeQuery(query);
            
            if (rs.next()) {
                res=rs.getInt("numOrdDes");
                res++;
                
            }
            sql.close();
            System.out.println(query);}
            
            //con.commit();
        
        
        
        System.out.println("mi empresa"+impresion.getEmp()+"    miLocal:  "+impresion.getLoc()+"  mitipodoc: "+impresion.getTipdoc()+"  miNumdoc:  "+impresion.getNumdoc()+"  miOD es: "+res);
        if (intLoc==10) {
            actualizarUltDoc_inm(res, con);
            actualizarCabGuiRem(res,con);
            ZafImpOrd od=new ZafImpOrd();
            int codbod=traerCodBod(con);
            int codoc=traefa(con);
            int cotipdoc=traerTipDocGuiRem(codoc,con);
            if (impresion.getLoc()==10) {
                impresion.setTipdoc(102);
                cotipdoc=102;
            }
            
            
            con.commit();
            
            
            
            //od.impresionGuiaRemAutBod2(oc.ocon, impresion.getEmp(), impresion.getLoc(), impresion.getTipdoc(), impresion.getCoDoc(),codbod);
            od.impresionGuiaRemAutBod2(con, impresion.getEmp(), impresion.getLoc(), cotipdoc, codoc,codbod);
            //od.impresionGuiaRemAutBod2(oc.ocon, impresion.getEmp(), impresion.getLoc(), impresion.getTipdoc(), codoc,codbod); 
        
        }else{
            actualizarUltDoc(res,con);
            actualizarCabGuiRem(res,con);
            ZafImpOrd od=new ZafImpOrd();
            int codbod=traerCodBod(con);
            int codoc=traefa(con);
            int cotipdoc=traerTipDocGuiRem(codoc,con);
            if (impresion.getLoc()==10) {
                impresion.setTipdoc(102);
                cotipdoc=102;
            }
            con.commit();
            //od.impresionGuiaRemAutBod2(oc.ocon, impresion.getEmp(), impresion.getLoc(), impresion.getTipdoc(), impresion.getCoDoc(),codbod);
            od.impresionGuiaRemAutBod2(con, impresion.getEmp(), impresion.getLoc(), cotipdoc, codoc,codbod);
            //od.impresionGuiaRemAutBod2(oc.ocon, impresion.getEmp(), impresion.getLoc(), impresion.getTipdoc(), codoc,codbod); 
        
        }
        
        
        }catch (Exception e) {
            System.out.println(e.getMessage());
            con.rollback();
        }finally{
            try{
                rs.close();
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        
        
        
     //metodos();   
       
    }//fin de impresion normal
    
  
    
    /**
     * Este método actualiza tbm_loc que es donde se encuentra almacenada la última 
     *Orden de Despacho por empresa y local, recibe
     * @param res donde se almacenó la generación de la secuencia de la órden de despacho.
     */
    private void actualizarUltDoc(int res,Connection con) {

        try {
           //sql=oc.ocon.createStatement();
           sql=con.createStatement();
           query="update tbm_loc set ne_ultnumorddes="+res+" where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc();
           sql.executeUpdate(query);
           sql.close();
           
            System.out.println("el registro en tbm_loc se actualizo a: "+res);
            System.out.println("consulta que acualiza tbmloc: "+query+" y la forma de pago es: "+impresion.getFpago());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }    
    }//fin de ActualizaTbmLoc
    
    
    
    private void actualizarUltDoc2(int res,Connection con,String strEmp, String strLoc) {

        try {
           //sql=oc.ocon.createStatement();
           sql=con.createStatement();
           query="update tbm_loc set ne_ultnumorddes="+res+" where co_emp="+Integer.parseInt(strEmp)+" and co_loc="+Integer.parseInt(strLoc);
           sql.executeUpdate(query);
           sql.close();
           
            System.out.println("el registro en tbm_loc se actualizo a: "+res);
            System.out.println("consulta que acualiza tbmloc: "+query+" y la forma de pago es: "+impresion.getFpago());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            objUti.mostrarMsgErr_F1(null, e);
        }    
    }//fin de ActualizaTbmLoc    
    
    
    /**
     * Este método actualiza tbm_loc que es donde se encuentra almacenada la última 
     *Orden de Despacho por empresa y local, recibe
     * @param res donde se almacenó la generación de la secuencia de la órden de despacho.
     */
    private void actualizarUltDoc_inm(int res,Connection con) {

        try {
           //sql=oc.ocon.createStatement();
           sql=con.createStatement();
           query="update tbm_loc set ne_ultnumorddes="+res+" where co_emp="+intEmp+" and co_loc="+intLoc;
           sql.executeUpdate(query);
           sql.close();
           
            System.out.println("el registro en tbm_loc se actualizo a: "+res);
            System.out.println("consulta que acualiza tbmloc: "+query+" y la forma de pago es: "+impresion.getFpago());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }    
    }//fin de ActualizaTbmLoc
    
    
    
    /**
     * Método para la actualización de la cabecera de la guía de remisión, se actualiza el campo ne_ultorddes, recibe
     * @param res donde se almacenó la secuencia de la órden de despacho generada
     */
    
  private void actualizarCabGuiRem(int res,Connection con) {
       
      int f=0;
      
//      if (impresion.getTipdoc()==46) {
//          //f=impresion.getCoDoc();
//          f=impresion.getNumdoc();
//          System.out.println("el codigo del documento para generar la OD desde transferencias es: "+f);
//      }else{
      
      f=traefa(con);
      
      if (f==0) {
          res--;
          actualizarUltDoc(res,con);
          System.out.println("Factura de orígen vacia");
      }else{
      
      //}
        try {
            //sql=oc.ocon.createStatement();
            sql=con.createStatement();
            /*if (impresion.getTipdoc()==46) {
                query="UPDATE tbm_cabguirem SET fe_doc=current_date, fe_initra=current_date, fe_tertra=current_date, st_imporddes='S', fe_orddes=current_date,  ne_numorddes="+res+" WHERE co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc=101 and co_doc="+f;
            }*/
            /*if (impresion.getTipdoc()==228) 
            {*/
                query="UPDATE tbm_cabguirem SET fe_doc=current_date, fe_initra=current_date, fe_tertra=current_date, st_imporddes='S', fe_orddes=current_date,  ne_numorddes="+res+" WHERE co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc=102 and co_doc="+f;
            //}
            sql.executeUpdate(query);
            sql.close();
             actualizarCabMovInv(res,f,con);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("el registro en tbm_cabguirem se actualizo a: "+f+" con: "+res);
        System.out.println("consulta que acualiza cabguirem: "+query);
      }
  }

  
 private void actualizarCabGuiRem2(int res,Connection con, int intCodOD) {
       
        try {
            sql=con.createStatement();
            //query="UPDATE tbm_cabguirem SET fe_doc=current_date, fe_initra=current_date, fe_tertra=current_date, st_imporddes='S', fe_orddes=current_date,  ne_numorddes="+res+" WHERE co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc=102 and co_doc="+intCodOD;
            query="UPDATE tbm_cabguirem SET fe_doc=current_date, fe_initra=current_date, fe_tertra=current_date,  fe_orddes=current_date,  fe_ultmod=current_timestamp,   ne_numorddes="+res+" WHERE co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc=102 and co_doc="+intCodOD;
            sql.executeUpdate(query);
            sql.close();
             actualizarCabMovInv(res,intCodOD,con);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            objUti.mostrarMsgErr_F1(null, e);
        }
        System.out.println("el registro en tbm_cabguirem se actualizo a: "+intCodOD+" con: "+res);
        System.out.println("consulta que acualiza cabguirem: "+query);
      
  }  
  
  /**
   * Metodo para la actualización de la cabecera del movimiento del inventario, para que actualice el campo ne_numorddes, recibe
   * @param res que es la OD generada y
   * @param f que trae el codigo de la guía 
   */
  private void actualizarCabMovInv(int res,int f,Connection con) throws SQLException{

         //sql=oc.ocon.createStatement();
         sql=con.createStatement();
        //actualizo cabmovinv
        try {
            query="UPDATE tbm_cabmovinv set ne_numorddes="+res+" from ( " +
            "   SELECT co_emprel, co_locrel, co_tipdocrel, co_docrel  FROM tbm_detguirem  WHERE co_emp="+impresion.getEmp()+" AND co_loc="+impresion.getLoc()+" AND co_tipdoc=102 " +
            "    AND co_doc="+f+" GROUP BY co_emprel, co_locrel, co_tipdocrel, co_docrel "+
            "  ) as x  where tbm_cabmovinv.co_emp=x.co_emprel and tbm_cabmovinv.co_loc=x.co_locrel and tbm_cabmovinv.co_tipdoc=x.co_tipdocrel and tbm_cabmovinv.co_doc=x.co_docrel ";
            sql.executeUpdate(query);
            sql.close();
            System.out.println("consulta que acualiza cabmovinv: "+query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            objUti.mostrarMsgErr_F1(null, e);
        }
  }
  /**
   * <h1>TRAE NUMERO DE FACTURA</h1>
   * <p>Esta función permite traer el número de la factura desde el campo tx_datdocoriguirem que tiene 
   * una cadena concatenada del tipo de documento FACVEN y el número de la factura</p>
   * @return 
   */
  public int traefa(Connection con){
  
      ResultSet rs;
          int f = 0;
      try {
          //sql=oc.ocon.createStatement();
          sql=con.createStatement();
          String co="";
          
          co="select co_doc from tbm_cabguirem where substr(tx_datdocoriguirem,9,10)='"+impresion.getNumdoc()+"' and co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc();
          
          rs=sql.executeQuery(co);
          if (rs.next()) {
              f=rs.getInt("co_doc");
          }
          rs.close();
          sql.close();  
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
        return f;
  }//fin de trae
  
  
    public int obtenerDocGui(Connection con){
  
      ResultSet rs;
          int f = 0;
      try {
          //sql=oc.ocon.createStatement();
          sql=con.createStatement();
          String co="";
          
          //co="select co_doc from tbm_cabguirem where substr(tx_datdocoriguirem,9,10)='"+impresion.getNumdoc()+"' and co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc=102";
        co= " select     distinct b.co_doc"+
            " from       tbm_cabguirem b "+
            " inner join tbm_detguirem c on b.co_doc=c.co_doc and b.co_tipdoc=c.co_tipdoc and b.co_loc=c.co_loc and b.co_emp = c.co_emp"+
            " inner join tbm_cabmovinv d on c.co_docrel=d.co_doc and c.co_locrel=d.co_loc and c.co_tipdocrel=d.co_tipdoc and c.co_emp= d.co_emp"+
            " where      d.co_emp="+impresion.getEmp()+" and d.co_loc="+impresion.getLoc()+" and d.co_tipdoc = "+impresion.getTipdoc()+"and d.co_doc="+impresion.getCoDoc();
          
          
          rs=sql.executeQuery(co);
          if (rs.next()) {
              f=rs.getInt("co_doc");
          }
          rs.close();
          sql.close();  
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
        return f;
  }//fin de trae  
    
    

    
  
  public int obtenerBodPtoPda(Connection con){
      ResultSet rs;
      int codBod = 0;
      try {
          sql=con.createStatement();
          String co="";
          
        co= " select     distinct b.co_ptopar"+
            " from       tbm_cabguirem b "+
            " inner join tbm_detguirem c on b.co_doc=c.co_doc and b.co_tipdoc=c.co_tipdoc and b.co_loc=c.co_loc and b.co_emp = c.co_emp"+
            " inner join tbm_cabmovinv d on c.co_docrel=d.co_doc and c.co_locrel=d.co_loc and c.co_tipdocrel=d.co_tipdoc and c.co_emp= d.co_emp"+
            " where      d.co_emp="+impresion.getEmp()+" and d.co_loc="+impresion.getLoc()+" and d.co_tipdoc = "+impresion.getTipdoc()+" and d.co_doc="+impresion.getCoDoc();
          
          
          rs=sql.executeQuery(co);
          if (rs.next()) {
              codBod=rs.getInt("co_ptopar");
          }
          rs.close();
          sql.close();  
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
      return codBod;  
  
  }
  

  public int obtenerBodPtoPdaAutOD(Connection con){
      ResultSet rs;
      int codBod = 0;
      try {
          sql=con.createStatement();
          String co="";
          
        co= " select     distinct b.co_ptopar"+
            " from       tbm_cabguirem b "+
            " inner join tbm_detguirem c on b.co_doc=c.co_doc and b.co_tipdoc=c.co_tipdoc and b.co_loc=c.co_loc and b.co_emp = c.co_emp"+
            " inner join tbm_cabmovinv d on c.co_docrel=d.co_doc and c.co_locrel=d.co_loc and c.co_tipdocrel=d.co_tipdoc and c.co_emp= d.co_emp"+
            " where      b.co_emp="+impresion.getEmp()+" and b.co_loc="+impresion.getLoc()+" and b.co_tipdoc = "+impresion.getTipdoc()+" and b.co_doc="+impresion.getCoDoc();
          
          
          rs=sql.executeQuery(co);
          if (rs.next()) {
              codBod=rs.getInt("co_ptopar");
          }
          rs.close();
          sql.close();  
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
      return codBod;  
  
  }  
 
    
    public ZafImp getImpresion() {
        return impresion;
    }

    public void setImpresion(ZafImp impresion) {
        this.impresion = impresion;
    }
    
    
  
        public StringBuffer[] actualizarDetItmSol(BigDecimal bigCantStk, int intItm, int intEmp, int intBod, StringBuffer strConsulta, StringBuffer strActInv){
            StringBuffer[] StrArrRet=new StringBuffer[2];
            strActInv.append("UPDATE TBM_INV SET st_regrep='M', nd_stkact=nd_stkact+"+bigCantStk.multiply(new BigDecimal(-1)) +" where co_emp="+intEmp+" and co_itm="+intItm+";");
            strActInv.append("UPDATE TBM_INVBOD SET nd_stkact=nd_stkact+"+bigCantStk.multiply(new BigDecimal(-1)) +", nd_canegrbod="+bigCantStk.multiply(new BigDecimal(-1))+" where co_emp="+intEmp+" and co_itm="+intItm+" and co_bod="+intBod+";");
            if(strConsulta.length()>0){
                strConsulta.append(" UNION ALL ");
            }
            strConsulta.append(" SELECT co_emp, co_itm, nd_stkact FROM tbm_invbod AS a WHERE a.co_emp="+intEmp+" AND a.co_itm="+intItm+" AND a.co_bod="+intBod+" ");
            StrArrRet[0]=strActInv;
            StrArrRet[1]=strConsulta;
            return StrArrRet;
        }
    
        public ResultSet obtenerDetSolDev(int intEmpSol, int intCodLocSol, int intTipDocSol, int intCodDocSol, Connection cnx){
            String strQuery="";
            Statement stmQuery=null;
            ResultSet rstDetSolDev=null;
            try{
                strQuery="SELECT  a2.st_ser, a.nd_canvolfac, a.nd_preuni, a.nd_pordes , a.st_iva "+
                    ",a1.tx_codalt, a1.co_itm, a1.tx_nomitm, a1.tx_unimed, a1.co_bod, a1.nd_cosuni, a1.st_ivacom, a1.nd_preunivenlis"+
                    ",a1.nd_pordesvenmax, a1.ne_numfil, a1.st_meringegrfisbod "+
                    " FROM tbm_detsoldevven as a "+
                    " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel ) "+
                    " INNER JOIN tbm_inv AS a2 ON (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm ) "+
                    " WHERE a.co_emp= "+intEmpSol+
                    " and a.co_loc= "+intCodLocSol+
                    " and a.co_tipdoc= "+intTipDocSol+
                    " and a.co_doc= "+intCodDocSol+
                    " and a.nd_canvolfac  > 0 "+
                    " and a2.st_ser='N' ";
                stmQuery=cnx.createStatement();
                rstDetSolDev=stmQuery.executeQuery(strQuery);
                return rstDetSolDev;
            }catch(Exception ex){
                ex.printStackTrace();
                return null;
            }
    
        }
    
        public ResultSet verificarStockNegativos(StringBuffer strCnsStkNeg,Connection cnx){
            ResultSet rsRetStk=null;
            Statement stVerStkNeg=null;
            String strSql="SELECT x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm, sum(x.nd_stkact)  as nd_stkact FROM( "+strCnsStkNeg.toString()+" ) AS x  LEFT JOIN tbm_inv AS inv ON(inv.co_emp=x.co_emp AND inv.co_itm=x.co_itm) WHERE x.nd_stkact < 0 GROUP BY x.co_emp, x.co_itm, inv.tx_codalt, inv.tx_nomitm ";
            try{
                stVerStkNeg=cnx.createStatement();
                rsRetStk=stVerStkNeg.executeQuery(strSql);
                return rsRetStk;
            }catch(Exception ex){
                ex.printStackTrace();
                return null;
            }
        }
    
//        public ResultSet obtenerPolRetSri(int intCodEmp, int intCodMotDoc, int intCodTipPerEmp, String strFecAct, int intTipPerFact){
//            ResultSet rsPolRet=null;
//            String strSqlPolRet="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,"+
//                          "nd_porret,tx_aplret,co_cta "+
//                          " FROM tbm_polret as polret "+
//                          " left outer join tbm_motdoc as mot "+
//                          " on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) "+
//                          " left outer join tbm_cabtipret as tipret "+
//                          " on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)"+
//                          " WHERE polret.co_emp = "+ intCodEmp +
//                          " and co_mot = "+intCodMotDoc+
//                          " and co_sujret = " + intCodTipPerEmp +
//                          " and co_ageret  = "+intTipPerFact+" "+
//                          " and polret.st_reg='A'  AND  '"+strFecAct+"'"+
//                          "  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
//            try{
//                Statement stmPolRet= oc.ocon.createStatement();
//                rsPolRet=stmPolRet.executeQuery(strSqlPolRet);
//            }catch(Exception ex){
//                ex.printStackTrace();
//            }
//            return rsPolRet;
//        }
    
        public boolean asignarNumFacNue(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, Connection cnx){
            boolean blnRes=false;
            Statement stmLoc;
            ResultSet rstLoc;
            String strSql="";
            int intNumDocFac=0;
            int intNumDocGuia=0;
            try {
               if(cnx!=null) {
                   stmLoc = cnx.createStatement();
    
                   strSql="SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc, tx_descor "
                   + " FROM tbm_cabTipDoc WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipDoc ="+intCodTipDoc;
                   rstLoc = stmLoc.executeQuery(strSql);
                   if(rstLoc.next()){
                       intNumDocFac = rstLoc.getInt("ultnum");
                   }
                   rstLoc.close();
                   rstLoc=null;
    
                   strSql="UPDATE tbm_cabTipDoc SET ne_ultDoc="+intNumDocFac+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipDoc ="+intCodTipDoc;
                   stmLoc.executeUpdate(strSql);
    
    
                   strSql=" UPDATE tbm_cabmovinv SET co_mnu=14,  ne_numdoc="+intNumDocFac+",ne_numgui="+intNumDocGuia+",st_reg='A', st_imp='S' "
                   + " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipDoc ="+intCodTipDoc+" and co_doc="+intCodDoc;
                   strSql+=" ; UPDATE tbm_cabdia SET tx_numdia='"+intNumDocFac+"' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipDoc ="+intCodTipDoc+" and co_dia="+intCodDoc;
    
                   /*if(blnTieGuiRem)
                     strSql+="; UPDATE tbm_cabguirem SET fe_doc=current_date,  tx_datdocoriguirem='"+strDatdocoriguirem+"' WHERE co_emp="+intCodEmp+" " +
                     " and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDocGuia+" and co_doc="+intCodDocGui;*/
    
                   //System.out.println("--> "+strSql );
                   stmLoc.executeUpdate(strSql);
    
                  stmLoc.close();
                  stmLoc=null;
                  blnRes=true;
                }
            }catch(SQLException e){
                blnRes=false;
            }catch(Exception e)  {
                blnRes=false;
            }
            return blnRes;
       }
   
    
//public void insertarCabOrdDesTrans(){
//
//    String strDirBod="",strDocRel="TRAINV-"+ot.getCoDoc(),dirclides="";
//    int intCodBod=26,strcoclides=ot.getIntCoBodDes(),codoc=0,intTipDoc=101;
//    int intNumGuiaRem=0;
//    //intNumGuiaRem=traerUltOrdDes();
//    ocli=traerDatEmp();
//    codoc=traerUltDocGuia();
//    strDirBod=traerDesBodOri();
//    int clides=traerCliDes();
//    dirclides=traerDesBodDes();
//    try
//        {
//           
//                /*sql=oc.ocon.createStatement();
//                query="INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc ,fe_orddes "+
//                " , ne_numorddes, ne_numdoc,  tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
//                " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
//                " , tx_vehret, tx_choret ,tx_datdocoriguirem , co_clides, co_ptodes, st_imporddes ) "+
//                " VALUES( "+ot.getEmp()+", "+ot.getLoc()+","+intTipDoc+","+codoc+", current_date, current_date  "+  // '"+strFecDoc+"' " +
//                " ,"+intNumGuiaRem+", 0,'"+ocli.getTx_ruc()+ "','"+ocli.getTx_nom()+"','"+dirclides+"' " +
//                " ,'"+ocli.getTx_tel()+"', '', 'N' ,'A', current_timestamp , 1 " +
//                " ,'P', current_date, current_date, '', 'I' , "+ot.getIntCoBodOri()+", '"+strDirBod+"'  " +
//                " , '', '', '"+strDocRel+"', "+clides+", "+strcoclides+", 'S'  )";
//                query+=" ; UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumGuiaRem+"  WHERE co_emp="+ot.getEmp()+" " +
//                " and co_loc="+ot.getLoc()+" and co_tipdoc="+ot.getTipdoc()+" and co_doc="+ot.getCoDoc();
//                */
//                //verificar datos de la empresa destino ocli.getTx_ruc()
//                sql=oc.ocon.createStatement();
//                query="INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc ,fe_orddes "+
//                " , ne_numorddes, ne_numdoc,  tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
//                " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
//                " , tx_vehret, tx_choret ,tx_datdocoriguirem , co_clides, co_ptodes, st_imporddes ) "+
//                " VALUES( "+ot.getEmp()+", "+ot.getLoc()+","+intTipDoc+","+codoc+", current_date, current_date  "+  // '"+strFecDoc+"' " +
//                " ,"+intNumGuiaRem+", 0,'"+ocli.getTx_ruc()+ "','"+ocli.getTx_nom()+"','"+dirclides+"' " +
//                " ,'"+ocli.getTx_tel()+"', '', 'N' ,'A', current_timestamp , 1 " +
//                " ,'P', current_date, current_date, '', 'I' , "+ot.getIntCoBodOri()+", '"+strDirBod+"'  " +
//                " , '', '', '"+strDocRel+"', "+clides+", "+strcoclides+", 'S'  )";
//                query+=" ; UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumGuiaRem+"  WHERE co_emp="+ot.getEmp()+" " +
//                " and co_loc="+ot.getLoc()+" and co_tipdoc="+ot.getTipdoc()+" and co_doc="+ot.getCoDoc();
//               
//                sql.executeUpdate(query);
////                System.out.println("Eddye (2038): " + strSQL01);
//                sql.close();
//                
//            
//        }
//        catch (java.sql.SQLException Evt)
//        {
//            System.out.println("" + Evt);
//         }
//      
//
//
//}//fin de insertar caborddestrans

//public void ingresarDetOrdDesTra(){
//    int c=traerCodItmTra();
//    ot.setCo_itm(c);
//    int intTipDoc=101,codoc=0;
//    //traer desde el detmovinv la clave para ponerla en los docrel y fin
//    codoc=traerUltDocGuia()-1;
//    try {
//        sql=oc.ocon.createStatement();
//        query="INSERT INTO tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel,co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm, tx_unimed, nd_can, st_regrep, tx_obs1, st_meregrfisbod )"
//                + "VALUES("+ot.getEmp()+","+ot.getLoc()+","+intTipDoc+","+codoc+","+ot.getCoreg()+","+ot.getEmp()+","+ot.getLoc()+","+ot.getTipdoc()+","+ot.getCoDoc()+","+ot.getCoreg()+","+ot.getCo_itm()+",'"+ot.getTx_codalt()+"','"+ot.getTx_nomitm()+"','"+ot.getTx_unimed()+"',"+ot.getNd_can()+",'"+ot.getSt_regrep()+"','"+ot.getTx_obs1()+"','"+ot.getSt_meregrfisbod()+"')";
//        sql.executeUpdate(query);
//        sql.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//
//
//}//fin de ingresar det
//private int traerUltOrdDes(Connection con){
//
//    int res=0;
//    
//    try {
//        sql= con.createStatement();
//
//            query="select ne_ultnumorddes as numOrdDes from tbm_loc where co_emp="+ot.getEmp()+" and co_loc="+ot.getLoc();
//            rs=sql.executeQuery(query);
//            
//            if (rs.next()) {
//                res=rs.getInt("numOrdDes");
//                res++;
//                
//            }
//            sql.close();
//            rs.close();
//            actualizarUltNumOd(res);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//return res;
//}//fin  

//private ZafCliTra traerDatEmp(){
//
//    
//
//    try {
//        sql=oc.ocon.createStatement();
//        /*query="select * from tbm_emp where co_emp="+ot.getEmp();*/
//              query="SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, "
//                    + " case when a.co_emp=1 then "
//                    + " case when x.co_bod in (6, 7) then 1039 ELSE "                   //DIMULTI
//                    + " case when x.co_bod in (8, 10, 12, 21, 22, 23) then 603 ELSE "   //CASTEK
//                    + " case when x.co_bod in (1, 15, 20, 9) then 3516 ELSE null "      //TUVAL //9
//                    + " END END END "
//                    + " else "
//                    + " case when a.co_emp=2 then "
//                    + " case when x.co_bod IN (1, 4, 12, 21, 22, 23) then 446 ELSE "    //CASTEK
//                    + " case when x.co_bod IN (3, 15, 20, 10) then 2854 else "          //TUVAL//10
//                    + " case when x.co_bod in (8, 9) then 789 else null "               //DIMULTI
//                    + " END END end "
//                    + " else "
//                    + " case when a.co_emp=4 then "
//                    + " case when x.co_bod IN (7, 9, 11, 21, 22, 23) then 498 ELSE "    //CASTEK
//                    + " case when x.co_bod IN (2, 15, 20, 8) then 3117  else "          //TUVAL//8
//                    + " case when x.co_bod in (1, 6) then 886  else null "              //DIMULTI
//                    + " END END END END END "
//                    + " END  AS coclides, "
//                    + " case when a.co_emp=1 then "
//                    + " case when x.co_bod=7 then 'DIMULTI' ELSE " 
//                    + " case when x.co_bod=8 then 'CASTEK - QUITO' ELSE "
//                    + " case when x.co_bod=10 then 'CASTEK - MANTA' ELSE "
//                    + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO' ELSE "
//                    + " case when x.co_bod in (1, 15) then 'TUVAL' ELSE "
//                    + " case when x.co_bod=9 then 'Navisur' ELSE "
//                    + " case when x.co_bod in (6, 20, 21, 22, 23) then a.tx_nom "       //EXPOSICION
//                    + " END END END END END END END"
//                    + " else "
//                    + " case when a.co_emp=2 then "
//                    + " case when x.co_bod = 1 then 'CASTEK - QUITO ' ELSE "
//                    + " case when x.co_bod in (8, 20, 21, 22, 23) then a.tx_nom ELSE "  //EXPOSICION
//                    + " case when x.co_bod in (3, 15) then 'TUVAL ' else "
//                    + " case when x.co_bod = 10 then 'NAVISUR' ELSE "//--
//                    + " case when x.co_bod=4 then 'CASTEK - MANTA ' ELSE "
//                    + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO ' ELSE "
//                    + " case when x.co_bod=9 then 'DIMULTI' "
//                    + " END END END END END END END"
//                    + " else "
//                    + " case when a.co_emp=4 then "
//                    + " case when x.co_bod=7 then 'CASTEK - QUITO ' ELSE "
//                    + " case when x.co_bod IN (2, 15) then 'TUVAL ' else "
//                    + " case when x.co_bod=9 then 'CASTEK - MANTA ' ELSE "
//                    + " case when x.co_bod=11 then 'CASTEK - SANTO DOMINGO' ELSE "
//                    + " case when x.co_bod = 1 then 'DIMULTI' ELSE "
//                    + " case when x.co_bod= 8 then 'NAVISUR' ELSE "//--
//                    + " case when x.co_bod in (6, 20, 21, 22, 23) then a.tx_nom "       //EXPOSICION    
//                    + " END END END END END END END END END"
//                    + " END  AS EMPRESA, "
//                    + " case when a.co_emp=1 then "
//                    + " case when x.co_bod in (6, 7) then '992372427001' ELSE "                   //DIMULTI
//                    + " case when x.co_bod IN (8, 10, 12, 21, 22, 23) then '992329432001' ELSE "  //CASTEK
//                    + " case when x.co_bod IN (1, 15, 20, 9) then '990281866001' ELSE NULL "      //TUVAL//9
//                    + " END END END "
//                    + " else "
//                    + " case when a.co_emp=2 then "
//                    + " case when x.co_bod IN (1, 4, 12, 21, 22, 23) then '992329432001' ELSE "   //CASTEK
//                    + " case when x.co_bod in (3, 15, 20, 10) then '990281866001 ' else "         //TUVAL//10
//                    + " case when x.co_bod in (8, 9) then '992372427001' "                        //DIMULTI
//                    + " END END END "
//                    + " else "
//                    + " case when a.co_emp=4 then "
//                    + " case when x.co_bod IN (7, 9, 11, 21, 22, 23) then '992329432001' ELSE "   //CASTEK  
//                    + " case when x.co_bod IN (2, 15, 20, 8) then '990281866001' else "           //TUVAL//8
//                    + " case when x.co_bod in (1, 6) then '992372427001' "                        //DIMULTI
//                    + " END END END END END "
//                    + " END  AS RUCEMP "
//                    + " FROM (  SELECT distinct(co_bod) , co_emp "
//                    + " FROM tbm_detmovinv "
//                    + " WHERE co_emp=" +ot.getEmp()+""
//                    + " and co_loc=" +ot.getLoc()+""
//                    + " and co_tipdoc=" +ot.getTipdoc()+""
//                    + " and co_doc=" +ot.getCoDoc()+""
//                    + " and nd_can>0) as x "
//                    + " INNER JOIN tbm_bod as a on (a.co_emp=x.co_emp and a.co_bod=x.co_bod) ";
//                    
//        rs=sql.executeQuery(query);
//        while (rs.next()) {
//            
//            /*ocli.setTx_ruc(rs.getString("tx_ruc")) ;
//            ocli.setTx_nom(rs.getString("tx_nom"));
//            ocli.setTx_dir(rs.getString("tx_dir"));
//            ocli.setTx_tel(rs.getString("tx_tel"));
//            */
//            
//            
//            ocli.setTx_ruc(rs.getString("rucemp")) ;
//            ocli.setTx_nom(rs.getString("tx_nom"));
//            ocli.setTx_dir(rs.getString("tx_dir"));
//            ocli.setTx_tel(rs.getString("tx_tel"));
//             
//        }
//        sql.close();
//        rs.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//
//
//return ocli;
//}//fin de traer rucemp

//private int traerCodItmTra(){
//    int cod=0;
//    
//    try {
//        sql=oc.ocon.createStatement();
//        query="select co_itm from tbm_inv where co_emp="+ot.getEmp()+" and tx_codalt='"+ot.getTx_codalt()+"'";
//        rs=sql.executeQuery(query);
//        if (rs.next()) {
//            cod=rs.getInt("co_itm");
//        }
//        sql.close();
//        rs.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    
//    return cod;
//}//fin de traer itm


//private int traerUltDocGuia(){
//
//    int c=0;
//        
//        try {
//        sql=oc.ocon.createStatement();
//        query="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabguirem WHERE " +
//                    " co_emp="+ot.getEmp()+" AND co_loc="+ot.getLoc()+" AND co_tipDoc=101";
//        rs=sql.executeQuery(query);
//            if (rs.next()) {
//                c=rs.getInt("co_doc");
//            }
//            sql.close();
//            rs.close();
//            query="";
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    
//    
//    
//    return c;
//}//trae ultdoc


// private void actualizarUltNumOd(int res) {
//
//        try {
//           sql=oc.ocon.createStatement();
//           query="update tbm_loc set ne_ultnumorddes="+res+" where co_emp="+ot.getEmp()+" and co_loc="+ot.getLoc();
//           sql.executeUpdate(query);
//           sql.close();
//           
//            System.out.println("el registro en tbm_loc se actualizo a: "+res);
//            System.out.println("consulta que acualiza tbmloc: "+query+" y la forma de pago es: "+impresion.getFpago());
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }    
//    }//fin de ActualizaTbmLoc


 
 
 
//  private String traerDesBodOri() {
//
//      String des="";
//        try {
//           sql=oc.ocon.createStatement();
//           query="select tx_dir from tbm_bod where co_emp="+ot.getEmp()+" and co_bod="+ot.getIntCoBodOri();
//           rs=sql.executeQuery(query);
//            if (rs.next()) {
//                des=rs.getString("tx_dir");
//            }
//           sql.close();
//           rs.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }    
//        return des;
//    }//fin de traerDes
 
 
 
  
//  private String traerDesBodDes() {
//
//      String des="";
//        try {
//           sql=oc.ocon.createStatement();
//           query="select tx_dir from tbm_bod where co_emp="+ot.getEmp()+" and co_bod="+ot.getIntCoBodDes();
//           rs=sql.executeQuery(query);
//            if (rs.next()) {
//                des=rs.getString("tx_dir");
//            }
//           sql.close();
//           rs.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }    
//        return des;
//    }//fin de traerDes
 
  
//  private int traerCliDes(){
//  
//      int cli=0;
//      
//      try {
//          sql=oc.ocon.createStatement();
//          query="SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, "
//                    + " case when a.co_emp=1 then "
//                    + " case when x.co_bod in (6, 7) then 1039 ELSE "                   //DIMULTI
//                    + " case when x.co_bod in (8, 10, 12, 21, 22, 23) then 603 ELSE "   //CASTEK
//                    + " case when x.co_bod in (1, 15, 20, 9) then 3516 ELSE null "      //TUVAL //9
//                    + " END END END "
//                    + " else "
//                    + " case when a.co_emp=2 then "
//                    + " case when x.co_bod IN (1, 4, 12, 21, 22, 23) then 446 ELSE "    //CASTEK
//                    + " case when x.co_bod IN (3, 15, 20, 10) then 2854 else "          //TUVAL//10
//                    + " case when x.co_bod in (8, 9) then 789 else null "               //DIMULTI
//                    + " END END end "
//                    + " else "
//                    + " case when a.co_emp=4 then "
//                    + " case when x.co_bod IN (7, 9, 11, 21, 22, 23) then 498 ELSE "    //CASTEK
//                    + " case when x.co_bod IN (2, 15, 20, 8) then 3117  else "          //TUVAL//8
//                    + " case when x.co_bod in (1, 6) then 886  else null "              //DIMULTI
//                    + " END END END END END "
//                    + " END  AS coclides, "
//                    + " case when a.co_emp=1 then "
//                    + " case when x.co_bod=7 then 'DIMULTI' ELSE " 
//                    + " case when x.co_bod=8 then 'CASTEK - QUITO' ELSE "
//                    + " case when x.co_bod=10 then 'CASTEK - MANTA' ELSE "
//                    + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO' ELSE "
//                    + " case when x.co_bod in (1, 15) then 'TUVAL' ELSE "
//                    + " case when x.co_bod=9 then 'Navisur' ELSE "
//                    + " case when x.co_bod in (6, 20, 21, 22, 23) then a.tx_nom "       //EXPOSICION
//                    + " END END END END END END END"
//                    + " else "
//                    + " case when a.co_emp=2 then "
//                    + " case when x.co_bod = 1 then 'CASTEK - QUITO ' ELSE "
//                    + " case when x.co_bod in (8, 20, 21, 22, 23) then a.tx_nom ELSE "  //EXPOSICION
//                    + " case when x.co_bod in (3, 15) then 'TUVAL ' else "
//                    + " case when x.co_bod = 10 then 'NAVISUR' ELSE "//--
//                    + " case when x.co_bod=4 then 'CASTEK - MANTA ' ELSE "
//                    + " case when x.co_bod=12 then 'CASTEK - SANTO DOMINGO ' ELSE "
//                    + " case when x.co_bod=9 then 'DIMULTI' "
//                    + " END END END END END END END"
//                    + " else "
//                    + " case when a.co_emp=4 then "
//                    + " case when x.co_bod=7 then 'CASTEK - QUITO ' ELSE "
//                    + " case when x.co_bod IN (2, 15) then 'TUVAL ' else "
//                    + " case when x.co_bod=9 then 'CASTEK - MANTA ' ELSE "
//                    + " case when x.co_bod=11 then 'CASTEK - SANTO DOMINGO' ELSE "
//                    + " case when x.co_bod = 1 then 'DIMULTI' ELSE "
//                    + " case when x.co_bod= 8 then 'NAVISUR' ELSE "//--
//                    + " case when x.co_bod in (6, 20, 21, 22, 23) then a.tx_nom "       //EXPOSICION    
//                    + " END END END END END END END END END"
//                    + " END  AS EMPRESA, "
//                    + " case when a.co_emp=1 then "
//                    + " case when x.co_bod in (6, 7) then '992372427001' ELSE "                   //DIMULTI
//                    + " case when x.co_bod IN (8, 10, 12, 21, 22, 23) then '992329432001' ELSE "  //CASTEK
//                    + " case when x.co_bod IN (1, 15, 20, 9) then '990281866001' ELSE NULL "      //TUVAL//9
//                    + " END END END "
//                    + " else "
//                    + " case when a.co_emp=2 then "
//                    + " case when x.co_bod IN (1, 4, 12, 21, 22, 23) then '992329432001' ELSE "   //CASTEK
//                    + " case when x.co_bod in (3, 15, 20, 10) then '990281866001 ' else "         //TUVAL//10
//                    + " case when x.co_bod in (8, 9) then '992372427001' "                        //DIMULTI
//                    + " END END END "
//                    + " else "
//                    + " case when a.co_emp=4 then "
//                    + " case when x.co_bod IN (7, 9, 11, 21, 22, 23) then '992329432001' ELSE "   //CASTEK  
//                    + " case when x.co_bod IN (2, 15, 20, 8) then '990281866001' else "           //TUVAL//8
//                    + " case when x.co_bod in (1, 6) then '992372427001' "                        //DIMULTI
//                    + " END END END END END "
//                    + " END  AS RUCEMP "
//                    + " FROM (  SELECT distinct(co_bod) , co_emp "
//                    + " FROM tbm_detmovinv "
//                    + " WHERE co_emp=" +ot.getEmp()+""
//                    + " and co_loc=" +ot.getLoc()+""
//                    + " and co_tipdoc=" +ot.getTipdoc()+""
//                    + " and co_doc=" +ot.getCoDoc()+""
//                    + " and nd_can>0) as x "
//                    + " INNER JOIN tbm_bod as a on (a.co_emp=x.co_emp and a.co_bod=x.co_bod) ";
//          rs=sql.executeQuery(query);
//          if (rs.next()) {
//              cli=rs.getInt("coclides");
//              //cli=rs.getInt("co_bod");
//          }
//          sql.close();
//          rs.close();
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//      
//      
//      
//      return cli;
//  }//fin de traeCliDes
  
  
//  private int traerEmpBod(){
//  
//      int em=0;
//      
//      try {
//          sql=oc.ocon.createStatement();
//          query="select * from tbm_bod where co_bod=";
//          rs=sql.executeQuery(query);
//          if (rs.next()) {
//              em=rs.getInt("co_emp");
//          }
//          sql.close();
//          rs.close();
//      } catch (Exception e) {
//      }
//      
//      
//      return em;
//      
//  }//fin de traer emp
  
  
 /**
  * Función que determina si una factura determinada, ya tiene una orden de despacho asignada
     * @param con
  */
  public boolean validarOD(Connection con){
      boolean resp=false;
      String strQuery;
      try {
          sql=con.createStatement();
          //query=null;
          //strQuery="select ne_numorddes from tbm_cabmovinv where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and ne_numdoc="+impresion.getNumdoc();
           strQuery="select ne_numorddes from tbm_cabmovinv where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc="+impresion.getTipdoc()+" and co_doc="+impresion.getCoDoc();		  
          rs=sql.executeQuery(strQuery);
          if (rs.next()) {
              Integer ord=rs.getInt("ne_numorddes");
              if (ord != null && ord!=0) {
                  resp=true;
              }
          }
          sql.close();
          rs.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
      
      return resp;
  }//fin de valida orden de despacho
  
  
  
  public boolean validarExiRegOD(Connection con){
      boolean resp=false;
      Statement sql=null;
      String strQuery;
      ResultSet rs=null;
      try {
          sql=con.createStatement();
          strQuery="select co_emprel from tbm_detguirem where co_emprel="+impresion.getEmp()+" and co_locrel="+impresion.getLoc()+" and co_tipdocrel="+impresion.getTipdoc()+" and co_docrel="+impresion.getCoDoc();		  
          rs=sql.executeQuery(strQuery);
          if (rs.next()) {
              Integer ord=rs.getInt("co_emprel");
              if (ord != null && ord!=0) {
                  resp=true;
              }
          }
          sql.close();
          rs.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
      
      return resp;
  }//fin de valida orden de despacho  
  
  
  
  /**
   * Metodo para obtener la factura origen.
   * @param co_emp codigo de empresa de una factura volver a facturar.
   * @param co_loc codigo de local de una factura volver a facturar.
   * @param co_tipdoc codigo del tipo de documento de una factura volver a facturar.
   * @param co_doc codigo del documento de una factura volver a facturar
   * @return 
   */
  public ResultSet obtenerFacOrg(Connection con,int co_emp, int co_loc, int co_tipdoc, int co_doc){
      String strSql="";
      ResultSet rs=null;
      Statement stm=null;
      try{
          strSql="select c.co_emp, c.co_loc, c.co_tipdoc, min(c.co_doc) as co_doc \n" +
                    " from tbm_Cabmovinv as c, (\n" +
                    "				select ne_numcot, co_emp, co_loc, co_tipdoc, co_doc\n" +
                    "				from tbm_Cabmovinv \n" +
                    "				where co_emp="+co_emp+" \n" +
                    "				and co_loc="+co_loc+"\n" +
                    "				and co_tipdoc="+co_tipdoc+"\n" +
                    "				and co_doc="+co_doc+") as d\n" +
                    " where c.ne_numcot=d.ne_numcot\n" +
                    " and c.co_emp=d.co_emp\n" +
                    " and c.co_loc=d.co_loc\n" +
                    " and c.co_tipdoc=d.co_tipdoc\n" +
                    " group by c.co_emp, c.co_loc, c.co_tipdoc";
          stm=con.createStatement();
          rs=stm.executeQuery(strSql);
          
      }catch(Exception ex){
          ex.printStackTrace();
      }       
      return rs;
  }  
  
  
  /**
   * Metodo que valida la od en la pantalla de autorizacion
   * @param con conexion conn
   * @return boolean.
   */
  public boolean validarODxAut(Connection con){
      boolean resp=false;
      String strQuery;
      try {
          sql=con.createStatement();
          //query=null;
          //strQuery="select ne_numorddes from tbm_cabmovinv where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and ne_numdoc="+impresion.getNumdoc();
           strQuery="select ne_numorddes from tbm_cabguirem where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc="+impresion.getTipdoc()+" and co_doc="+impresion.getCoDoc();		  
          rs=sql.executeQuery(strQuery);
          if (rs.next()) {
              Integer ord=rs.getInt("ne_numorddes");
              if (ord != null && ord!=0) {
                  resp=true;
              }
          }
          sql.close();
          rs.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
      
      return resp;
  }//fin de valida orden de despacho  
  
  private int traerCodBod(Connection con){
      int codBod=0;
     
      try {
          
      sql=con.createStatement();
      query="select b.co_bod,* from tbm_cabmovinv a inner join tbm_detmovinv b " +
            "on a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc " +
            "where a.co_emp="+impresion.getEmp()+" and a.co_loc="+impresion.getLoc()+" and a.ne_numdoc="+impresion.getNumdoc();
      rs=sql.executeQuery(query);
          if (rs.next()) {
              codBod=rs.getInt("co_bod");
          }
          sql.close();
          rs.close();
          
      } catch (Exception e) {
          e.printStackTrace();
      }
      return codBod;
  }//
  
  
  private int traerTipDocGuiRem(int codoc,Connection con){
      int tipdoc=0;
      
      try {
          sql=con.createStatement();
          query="select co_tipdoc from tbm_cabguirem where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_doc="+codoc;
          rs=sql.executeQuery(query);
          if (rs.next()) {
              tipdoc=rs.getInt("co_tipdoc");
          }
          sql.close();
          rs.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
      
      return tipdoc;
  }//fin de traerTipDocGuiRem
  
  
  /**
   * <h2>Traer número de factura</h2>
   * metodo que trae el número de la factura electrónica, es usado en cxc11 para la generación de la OD
     * @param con
   */
  public int traerNumDocFacElec(Connection con){
      int numdoc=0;
         try {
          sql=con.createStatement();
          query="select ne_numdoc from tbm_cabmovinv where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_doc="+impresion.getCoDoc()+" and co_tipdoc="+impresion.getTipdoc();
          rs=sql.executeQuery(query);
          if (rs.next()) {
              numdoc=rs.getInt("ne_numdoc");
          }
          sql.close();
          rs.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
         return numdoc;
  }//fin de traerNumDocFacElec
  
  
  public boolean verificarOrd(Connection con){
      boolean resp=false;
      Statement sql=null;
      String query="";
      ResultSet rs=null;
      int numdoc=0;
       try {
          sql=con.createStatement();
          /*query="select ne_numorddes from tbm_cabmovinv where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and ne_numdoc="+impresion.getNumdoc()+" and co_tipdoc="+impresion.getTipdoc();*/
		  query="select ne_numorddes from tbm_cabmovinv where co_emp="+impresion.getEmp()+" and co_loc="+impresion.getLoc()+" and co_tipdoc="+impresion.getTipdoc()+" and co_doc="+impresion.getCoDoc();		  
          rs=sql.executeQuery(query);
          if (rs.next()) {
              numdoc=rs.getInt("ne_numorddes");
              if (numdoc!=0 ) {
                  resp=true;
              }              
          }
          sql.close();
          rs.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return resp;
  }
 
    public ZafTraBean getOt() {
        return ot;
    }

    public void setOt(ZafTraBean ot) {
        this.ot = ot;
    }

}//fin de clase
