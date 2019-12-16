/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import Librerias.ZafParSis.ZafParSis;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class ZafGenFacDAO {
    
    
   public ResultSet obtenerDiaCreForPag(int intCodEmpSol, int intCodForPag, Connection cnx){
       ResultSet rsDiaPago=null;  
       String strSQL3 = "SELECT A2.ne_diaCre, A2.st_sop" +
                " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where A1.co_forPag = "  +intCodForPag+// Clausulas Where para las tablas maestras
                " and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                " and A1.co_emp = " + intCodEmpSol + // Consultando en la empresa en la ke se esta trabajando
                " and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando     
         try{
            Statement st=cnx.createStatement();
            rsDiaPago=st.executeQuery(strSQL3);
         }catch(Exception ex){
             ex.printStackTrace();
         }
         return rsDiaPago;
   }
   
   public ResultSet obtenerTipEmp(int intCodEmp, int intCodLoc, Connection con){
       ResultSet rsRetorno=null;
       
       String strSql="select b.co_tipper , b.tx_descor , a.nd_ivaven as poriva  , bod.co_bod, a1.tx_dir, a1.tx_nom as nombod  FROM  tbm_emp as a " +
            " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
            " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+intCodLoc+" and bod.st_reg='P')  " +
            " inner join tbm_bod as a1 on (a1.co_emp=bod.co_emp and a1.co_bod=bod.co_bod ) " +
            " where a.co_emp="+intCodEmp;
       try{
           Statement st=con.createStatement();
           rsRetorno=st.executeQuery(strSql);
       }catch(Exception ex){
           ex.printStackTrace();
       }
       return rsRetorno;
   }
   
   
    public int obtenerCantPagForPag(int intCodForPag, int intEmpSol, Connection cnx){
     Statement stmLoc;
     ResultSet rstLoc;
     //ZafCon objZafCon=new ZafCon();
     int intCant=0;
     try{
             stmLoc=cnx.createStatement();
             String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
             " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
             " Where A1.co_forPag = "  +intCodForPag+// Clausulas Where para las tablas maestras
             "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
             "       and A1.co_emp = " + intEmpSol + // Consultando en la empresa en la ke se esta trabajando
             "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando


             rstLoc= stmLoc.executeQuery(sSQL3);
             rstLoc.next();
             intCant = rstLoc.getInt(1);
             rstLoc.close();
             rstLoc=null;
             stmLoc.close();
             stmLoc=null;
         
     }catch(Exception e) {  
        System.out.println(""+e );  
        e.printStackTrace();
     }
      return intCant;
    }    
    
    

    public int obtenerMotDoc( int intCodEmp, Connection cnx ){
        int intRetCodMot=0;
        Statement stmTmp;       
        ResultSet rst;
        try {
            
                //stmTmp = objZafCon.ocon.createStatement();
                stmTmp = cnx.createStatement();
                String sql = "SELECT co_mot FROM tbm_motdoc WHERE co_emp="+intCodEmp+" AND tx_tipmot='B'";
                rst=stmTmp.executeQuery(sql);
                while(rst.next()){
                    intRetCodMot = rst.getInt(1);
                }

                rst.close();
                stmTmp.close();
                rst=null;
                stmTmp=null;
            
        
        }catch(SQLException e){  
            e.printStackTrace();
        }catch(Exception e )  {  
            e.printStackTrace();
        }        
        return intRetCodMot;
    }   
    
    public List obtenerCiePerCnt(int codEmp, int codAnio,Connection cnx){
        List<ZafCiePerCnt> lisZafCiePerCnt=new ArrayList<ZafCiePerCnt>();
        StringBuffer strSql=new StringBuffer("");
        //ZafCon objZafCon=new ZafCon();
        Statement stmSql=null;
        try{
                strSql.append("select a1.ne_ani, a2.ne_mes, a1.tx_tipCie");
                strSql.append(" from tbm_cabciesis as a1 left outer join tbm_detciesis as a2");
                strSql.append(" on a1.co_emp=a2.co_emp and a1.ne_ani=a2.ne_ani");
                strSql.append(" where a1.co_emp="+codEmp);
                strSql.append(" and a1.ne_ani="+codAnio);
                stmSql=cnx.createStatement();
                ResultSet rs=stmSql.executeQuery(strSql.toString());
                while(rs.next()){
                        ZafCiePerCnt cieCie=new ZafCiePerCnt();
                        cieCie.setIntAnio(rs.getInt("ne_ani"));
                        cieCie.setIntMes( rs.getInt("ne_mes"));
                        cieCie.setStrTipCierre(rs.getString("tx_tipCie"));
                        lisZafCiePerCnt.add(cieCie);
                }
        }catch(Exception ex){
              ex.printStackTrace();
        }finally{
            try{
                //cnx.close();
                //objZafCon=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return lisZafCiePerCnt;
    }    
    
    public boolean insertarCabAsiDia(Connection con, int intEmp, int intLoc, int intTipDoc, int intDoc, String strCalFecAct , String strCalFecHorAct){
        StringBuffer strSqlInsCab=new StringBuffer("");
        Statement stmSql=null;
        boolean booRet=true;
        try{
            strSqlInsCab.append(" insert into tbm_cabdia (co_emp, co_loc, co_tipDoc, co_dia, fe_dia, tx_glo,fe_ing, co_usrIng, fe_ultMod, co_usrMod )");
            strSqlInsCab.append(" values("+intEmp+", "+intLoc+", "+intTipDoc+", "+intDoc+",'"+strCalFecAct+"','','"+strCalFecHorAct+"',null,'"+strCalFecHorAct+"', null )");
            stmSql=con.createStatement();
            stmSql.executeUpdate(strSqlInsCab.toString());
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }
    
    
    public boolean insertarDetDiaCnt(Map hashMapTot, ZafImp genOgi, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac,int intCodDocFac,int intPerAct, Connection cnx, ZafParSis objZafParSis){
        boolean booRetorno=true;
        ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
        Statement stm=null;
        int intCodCta=0;
        String strCodCta="", strNomCta="";

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


            //if((BigDecimal.valueOf((Double)hashMapTot.get("TOTFAC"))).compareTo(new BigDecimal(0)) > 0){
            //if((BigDecimal.valueOf((Double)hashMapTot.get("TOTFACSINCOM"))).compareTo(new BigDecimal(0)) > 0){
            if((BigDecimal.valueOf((Double)hashMapTot.get("TOTFAC"))).compareTo(new BigDecimal(0)) > 0){
                //strSqlInsUpd+=srtSql+" 1,"+objCtaCnt.getCtaDeb()+","+(BigDecimal.valueOf((Double)hashMapTot.get("TOTFAC"))).setScale(2, RoundingMode.HALF_UP)+", 0 )  ; ";
                //strSqlInsUpd+=srtSql+" 1,"+objCtaCnt.getCtaDeb()+","+(BigDecimal.valueOf((Double)hashMapTot.get("TOTFACSINCOM"))).setScale(2, RoundingMode.HALF_UP)+", 0 )  ; ";
                strSqlInsUpd+=srtSql+" 1,"+objCtaCnt.getCtaDeb()+","+(BigDecimal.valueOf((Double)hashMapTot.get("TOTFAC"))).setScale(2, RoundingMode.HALF_UP)+", 0 )  ; ";                
                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTFAC")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDeb()+" AND co_per="+intPerAct+";";
                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTFACSINCOM")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDeb()+" AND co_per="+intPerAct+";";
                srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTFAC")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDeb()+" AND co_per="+intPerAct+";";                
                strSqlInsUpd+=srtSqlSal+";";
            }
            
            /*AGREGADO POR CAMBIO EN COMPENSACION SOLIDARIA*/
            /*CUENTA DE DESCUENTO REEMPLAZAR POR LA CUENTA DE VENTAS IVA 0% */
           // if((BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).compareTo(new BigDecimal(0)) > 0){
                //strSqlInsUpd+=srtSql+" 2,"+objCtaCnt.getCtaDescVentas()+","+(BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).setScale(2, RoundingMode.HALF_UP)+", 0 );";
                if(intCodEmpFac==1){
                    intCodCta=4680;
                    strCodCta="4.01.01.04";
                    strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                }else if(intCodEmpFac==2){
                    intCodCta=2303;
                    strCodCta="4.01.01.04";
                    strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                }else if(intCodEmpFac==4){
                    intCodCta=3210;
                    strCodCta="4.01.01.04";
                    strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                }

                if((BigDecimal.valueOf((Double)hashMapTot.get("SUBTOTIVACERO"))).compareTo(new BigDecimal(0)) > 0){
                    strSqlInsUpd+=srtSql+" 2,"+intCodCta+",0,"+(BigDecimal.valueOf((Double)hashMapTot.get("SUBTOTIVACERO"))).setScale(2, RoundingMode.HALF_UP)+" );";
                    //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTDES")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDescVentas()+" AND co_per="+intPerAct;
                    srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("SUBTOTIVACERO")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+intCodCta+" AND co_per="+intPerAct;                
                    strSqlInsUpd+=srtSqlSal+"; ";
                }
           // }
            /*AGREGADO POR CAMBIO EN COMPENSACION SOLIDARIA*/
            

            if((BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).add(BigDecimal.valueOf((Double)hashMapTot.get("SUBTOTAL"))).compareTo(new BigDecimal(0)) > 0){
                //strSqlInsUpd+=srtSql+" 3, "+objCtaCnt.getCtaHab()+", 0, "+(BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).add(BigDecimal.valueOf((Double)hashMapTot.get("SUBTOTAL"))).setScale(2, BigDecimal.ROUND_HALF_UP)+" ) ; ";
                strSqlInsUpd+=srtSql+" 3, "+objCtaCnt.getCtaHab()+", 0, "+(BigDecimal.valueOf((Double)hashMapTot.get("SUBTOTGRBIVA"))).setScale(2, BigDecimal.ROUND_HALF_UP)+" ) ; ";                
                //srtSqlSal=objZafGenFacDAO.obtenerCadActSalCta((BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).add((BigDecimal.valueOf((Double)(hashMapTot.get("TOTFAC"))))).multiply(new BigDecimal(-1)),intCodEmpFac,objCtaCnt.getCtaHab(),intPerAct);
                srtSqlSal=objZafGenFacDAO.obtenerCadActSalCta((BigDecimal.valueOf((Double)hashMapTot.get("SUBTOTGRBIVA"))).multiply(new BigDecimal(-1)),intCodEmpFac,objCtaCnt.getCtaHab(),intPerAct);
                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+(((BigDecimal)lisDatTot.get(3)).add((BigDecimal)lisDatTot.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(-1)))+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaHab()+" AND co_per="+intPerAct+";";
                strSqlInsUpd+=srtSqlSal+"; ";
            }

            if((BigDecimal.valueOf((Double)(hashMapTot.get("IVA")))).compareTo(new BigDecimal(0)) > 0){
                    //strSqlInsUpd+=srtSql+" 4,"+objCtaCnt.getCtaIvaVentas()+", 0,"+(BigDecimal.valueOf((Double)hashMapTot.get("IVA"))).setScale(2, RoundingMode.HALF_UP)+" );";
                    strSqlInsUpd+=srtSql+" 4,"+objZafParSis.getCodigoCuentaContableIvaVentas()+", 0,"+(BigDecimal.valueOf((Double)hashMapTot.get("IVA"))).setScale(2, RoundingMode.HALF_UP)+" );";                
                    objZafGenFacDAO.obtenerCadActSalCta((BigDecimal.valueOf((Double)hashMapTot.get("IVA"))),intCodEmpFac,objCtaCnt.getCtaIvaVentas(),intPerAct);
                    //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+lisDatTot.get(1)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaIvaVentas()+" AND co_per="+intPerAct+";";
                strSqlInsUpd+=srtSqlSal+"; ";
            }		
			

            /*if(intCodEmpFac==2){
                if(intCodLocFac==4 || intCodLocFac==10){
                    if(strIde.length()==10){
                        if((BigDecimal.valueOf((Double)(hashMapTot.get("IVACOMSOL")))).compareTo(new BigDecimal(0)) > 0){                    
                                strSqlInsUpd+=srtSql+" 5,"+objCtaCnt.getCtaIvaVentas()+","+(BigDecimal.valueOf((Double)hashMapTot.get("IVACOMSOL"))).setScale(2, RoundingMode.HALF_UP)+",0 );";
                                objZafGenFacDAO.obtenerCadActSalCta((BigDecimal.valueOf((Double)hashMapTot.get("IVACOMSOL"))).multiply(new BigDecimal(-1)),intCodEmpFac,objCtaCnt.getCtaIvaVentas(),intPerAct);
                                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+lisDatTot.get(1)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaIvaVentas()+" AND co_per="+intPerAct+";";
                            strSqlInsUpd+=srtSqlSal+"; ";
                        }		
                    }
                }
            }*/
			

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
    
    
    public boolean insertarDetDiaCntBig(Map hashMapTot, ZafImp genOgi, int intCodEmpFac, int intCodLocFac, int intCodTipDocFac,int intCodDocFac,int intPerAct, Connection cnx, ZafParSis objZafParSis){
        boolean booRetorno=true;
        ZafGenFacDAO objZafGenFacDAO=new ZafGenFacDAO();
        Statement stm=null;
        int intCodCta=0;
        String strCodCta="", strNomCta="";

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


            //if((BigDecimal.valueOf((Double)hashMapTot.get("TOTFAC"))).compareTo(new BigDecimal(0)) > 0){
            //if((BigDecimal.valueOf((Double)hashMapTot.get("TOTFACSINCOM"))).compareTo(new BigDecimal(0)) > 0){
            if((((BigDecimal)hashMapTot.get("TOTFAC"))).compareTo(new BigDecimal(0)) > 0){
                //strSqlInsUpd+=srtSql+" 1,"+objCtaCnt.getCtaDeb()+","+(BigDecimal.valueOf((Double)hashMapTot.get("TOTFAC"))).setScale(2, RoundingMode.HALF_UP)+", 0 )  ; ";
                //strSqlInsUpd+=srtSql+" 1,"+objCtaCnt.getCtaDeb()+","+(BigDecimal.valueOf((Double)hashMapTot.get("TOTFACSINCOM"))).setScale(2, RoundingMode.HALF_UP)+", 0 )  ; ";
                strSqlInsUpd+=srtSql+" 1,"+objCtaCnt.getCtaDeb()+","+((BigDecimal)hashMapTot.get("TOTFAC")).setScale(2, RoundingMode.HALF_UP)+", 0 )  ; ";                
                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTFAC")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDeb()+" AND co_per="+intPerAct+";";
                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTFACSINCOM")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDeb()+" AND co_per="+intPerAct+";";
                /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTFAC")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDeb()+" AND co_per="+intPerAct+";";                
                //strSqlInsUpd+=srtSqlSal+";";
                /*comentado por trigger que mayoriza*/
            }
            
            /*AGREGADO POR CAMBIO EN COMPENSACION SOLIDARIA*/
            /*CUENTA DE DESCUENTO REEMPLAZAR POR LA CUENTA DE VENTAS IVA 0% */
           // if((BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).compareTo(new BigDecimal(0)) > 0){
                //strSqlInsUpd+=srtSql+" 2,"+objCtaCnt.getCtaDescVentas()+","+(BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).setScale(2, RoundingMode.HALF_UP)+", 0 );";
                if(intCodEmpFac==1){
                    intCodCta=4680;
                    strCodCta="4.01.01.04";
                    strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                }else if(intCodEmpFac==2){
                    intCodCta=2303;
                    strCodCta="4.01.01.04";
                    strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                }else if(intCodEmpFac==4){
                    intCodCta=3210;
                    strCodCta="4.01.01.04";
                    strNomCta="VENTAS MERCADERIAS GQUIL T/. 0%";
                }

                if(((BigDecimal)hashMapTot.get("SUBTOTIVACERO")).compareTo(new BigDecimal(0)) > 0){
                    strSqlInsUpd+=srtSql+" 2,"+intCodCta+",0,"+((BigDecimal)hashMapTot.get("SUBTOTIVACERO")).setScale(2, RoundingMode.HALF_UP)+" );";
                    //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("TOTDES")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaDescVentas()+" AND co_per="+intPerAct;
                    /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
                    //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+hashMapTot.get("SUBTOTIVACERO")+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+intCodCta+" AND co_per="+intPerAct;                
                    //strSqlInsUpd+=srtSqlSal+"; ";
                    /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
                }
           // }
            /*AGREGADO POR CAMBIO EN COMPENSACION SOLIDARIA*/
            

            if((((BigDecimal)hashMapTot.get("TOTDES"))).add(((BigDecimal)hashMapTot.get("SUBTOTAL"))).compareTo(new BigDecimal(0)) > 0){
                //strSqlInsUpd+=srtSql+" 3, "+objCtaCnt.getCtaHab()+", 0, "+(BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).add(BigDecimal.valueOf((Double)hashMapTot.get("SUBTOTAL"))).setScale(2, BigDecimal.ROUND_HALF_UP)+" ) ; ";
                strSqlInsUpd+=srtSql+" 3, "+objCtaCnt.getCtaHab()+", 0, "+(((BigDecimal)hashMapTot.get("SUBTOTGRBIVA"))).setScale(2, BigDecimal.ROUND_HALF_UP)+" ) ; ";                
                //srtSqlSal=objZafGenFacDAO.obtenerCadActSalCta((BigDecimal.valueOf((Double)hashMapTot.get("TOTDES"))).add((BigDecimal.valueOf((Double)(hashMapTot.get("TOTFAC"))))).multiply(new BigDecimal(-1)),intCodEmpFac,objCtaCnt.getCtaHab(),intPerAct);
                /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
                //srtSqlSal=objZafGenFacDAO.obtenerCadActSalCta((((BigDecimal)hashMapTot.get("SUBTOTGRBIVA"))).multiply(new BigDecimal(-1)),intCodEmpFac,objCtaCnt.getCtaHab(),intPerAct);
                //ya estaba comentada//srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+(((BigDecimal)lisDatTot.get(3)).add((BigDecimal)lisDatTot.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(-1)))+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaHab()+" AND co_per="+intPerAct+";";
                //strSqlInsUpd+=srtSqlSal+"; ";
                /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
            }

            if((((BigDecimal)(hashMapTot.get("IVA")))).compareTo(new BigDecimal(0)) > 0){
                    //strSqlInsUpd+=srtSql+" 4,"+objCtaCnt.getCtaIvaVentas()+", 0,"+(BigDecimal.valueOf((Double)hashMapTot.get("IVA"))).setScale(2, RoundingMode.HALF_UP)+" );";
                    strSqlInsUpd+=srtSql+" 4,"+objZafParSis.getCodigoCuentaContableIvaVentas()+", 0,"+(((BigDecimal)hashMapTot.get("IVA"))).setScale(2, RoundingMode.HALF_UP)+" );";                
                    /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
                    //objZafGenFacDAO.obtenerCadActSalCta((((BigDecimal)hashMapTot.get("IVA"))),intCodEmpFac,objCtaCnt.getCtaIvaVentas(),intPerAct);
                    //ya estaba comentada//srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+lisDatTot.get(1)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaIvaVentas()+" AND co_per="+intPerAct+";";
                   //strSqlInsUpd+=srtSqlSal+"; ";
                  /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
            }		
			

            /*if(intCodEmpFac==2){
                if(intCodLocFac==4 || intCodLocFac==10){
                    if(strIde.length()==10){
                        if((BigDecimal.valueOf((Double)(hashMapTot.get("IVACOMSOL")))).compareTo(new BigDecimal(0)) > 0){                    
                                strSqlInsUpd+=srtSql+" 5,"+objCtaCnt.getCtaIvaVentas()+","+(BigDecimal.valueOf((Double)hashMapTot.get("IVACOMSOL"))).setScale(2, RoundingMode.HALF_UP)+",0 );";
                                objZafGenFacDAO.obtenerCadActSalCta((BigDecimal.valueOf((Double)hashMapTot.get("IVACOMSOL"))).multiply(new BigDecimal(-1)),intCodEmpFac,objCtaCnt.getCtaIvaVentas(),intPerAct);
                                //srtSqlSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+lisDatTot.get(1)+"  WHERE co_emp="+intCodEmpFac+" AND co_cta="+objCtaCnt.getCtaIvaVentas()+" AND co_per="+intPerAct+";";
                            strSqlInsUpd+=srtSqlSal+"; ";
                        }		
                    }
                }
            }*/

            /*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/            

            String strSQL="";
            //Actualiza cuentas padre del periodo
//            for(int j=6; j>1 ; j--){
//                strSQL="";
//                strSQL+="UPDATE tbm_salCta";
//                strSQL+=" SET nd_salCta=b1.nd_salCta";
//                strSQL+=" FROM (";
//                strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intPerAct + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
//                strSQL+=" FROM tbm_plaCta AS a1";
//                strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
//                strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
//                strSQL+=" AND a1.ne_niv=" + j;
//                strSQL+=" AND a2.co_per=" + intPerAct + "";
//                strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
//                strSQL+=" ) AS b1";
//                strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
//                strSqlInsUpd+=strSQL+" ;  ";			
//            }
//            /*Actualiza saldos de cuenta de resultados de la empresa tbm_empresa*/
//            strSQL="";
//            strSQL="UPDATE tbm_salCta";
//            strSQL+=" SET nd_salCta=b1.nd_salCta";
//            strSQL+=" FROM (";
//            strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intPerAct + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
//            strSQL+=" FROM tbm_plaCta AS a1";
//            strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
//            strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
//            strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
//            strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
//            strSQL+=" AND a2.co_per=" + intPerAct + "";
//            strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
//            strSQL+=" ) AS b1";
//            strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
//            strSqlInsUpd+=strSQL+" ;  ";
//
//            for(int j=6; j>1; j--){
//                strSQL="";
//                strSQL+="UPDATE tbm_salCta";
//                strSQL+=" SET nd_salCta=b1.nd_salCta";
//                strSQL+=" FROM (";
//                strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intPerAct + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
//                strSQL+=" FROM tbm_plaCta AS a1";
//                strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
//                strSQL+=" WHERE a1.co_emp=" + intCodEmpFac;
//                strSQL+=" AND a1.ne_niv=" + j;
//                strSQL+=" AND a2.co_per=" + intPerAct + "";
//                strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
//                strSQL+=" ) AS b1";
//                strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
//                strSqlInsUpd+=strSQL+" ;  ";
//                //stmLoc.executeUpdate(strSQL);
//           }   
/*comentado por trigger que mayoriza CMATEO 07-FEB-2019*/
            stm=cnx.createStatement();
            stm.executeUpdate(strSqlInsUpd);
        }catch(Exception ex){
            ex.printStackTrace();
            booRetorno=false;
        }
        return booRetorno;
    }    
    
    
    public String obtenerCadActSalCta(BigDecimal bigSalCta,int intCodEmp, int intCta, int intPer){
        String srtSqlActSal="UPDATE tbm_salcta SET nd_salcta=nd_salcta+"+bigSalCta+"  WHERE co_emp="+intCodEmp+" AND co_cta="+intCta+" AND co_per="+intPer+";";
        return srtSqlActSal;
    }    
    
    /**
     * Metodo que obtiene las politicas de retencion del SRI.
     * @param intCodEmp Codigo de empresa.
     * @param intCodMotDoc codigo de motivo.
     * @param intCodTipPerEmp codigo de tipo de persona empresa.
     * @param strFecAct fecha actual.
     * @param intTipPerFact codigo agente retencion.
     * @param cnx conexion.
     * @return Resultset con las politicas.
     */
    public ResultSet obtenerPolRetSri(int intCodEmp, int intCodMotDoc, int intCodTipPerEmp, String strFecAct, int intTipPerFact, Connection cnx){
        ResultSet rsPolRet=null;
        //ZafCon objZafCon=new ZafCon();
        String strSqlPolRet="SELECT tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,"+
                      "nd_porret,tx_aplret,co_cta "+
                      " FROM tbm_polret as polret "+
                      " left outer join tbm_motdoc as mot "+
                      " on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) "+
                      " left outer join tbm_cabtipret as tipret "+
                      " on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)"+
                      " WHERE polret.co_emp = "+ intCodEmp +
                      " and co_mot = "+intCodMotDoc+
                      " and co_sujret = " + intCodTipPerEmp +
                      " and co_ageret  = "+intTipPerFact+" "+
                      " and polret.st_reg='A'  AND  '"+strFecAct+"'"+
                      "  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
        
        try{
            Statement stmPolRet= cnx.createStatement();
            rsPolRet=stmPolRet.executeQuery(strSqlPolRet);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rsPolRet;
    }    
    
    //public boolean booInsertarCabMovInv(List<ZafFac> lstZafFac, Map hasTot, int intSecGrp, int intSecEmp, int intCodDocNueFac,Connection cnx){
    //    boolean booRetn=true;
    //    int i=0;
    //    int intCodMnuFac=14, intNumDoc=0;        
     //   try{
     //       String strQue="insert into tbm_cabmovinv (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
     //                     " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
    //                      " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
    //                      " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
    //                      " ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem) values("+lstZafFac.get(i).getCo_emp()+", "+lstZafFac.get(i).getCo_loc()+", "+
    //                                  ((lstZafFac.get(i).getCo_tipdoc()==1)?228:lstZafFac.get(i).getCo_tipdoc())+", "+intCodDocNueFac+", "+"CURRENT_TIMESTAMP"+", //"+lstZafFac.get(i).getCo_cli()+" ,'"+lstZafFac.get(i).getCo_com()+"','"+lstZafFac.get(i).getTx_ate()+"','"+
    //                                  lstZafFac.get(i).getTx_nomCli()+"','"+lstZafFac.get(i).getTx_dirCli()+"','"+lstZafFac.get(i).getTx_ruc()+ //"','"+lstZafFac.get(i).getTx_telCli()+"','"+
    //                                  lstZafFac.get(i).getTx_ciuCli()+ "','"+lstZafFac.get(i).getTx_nomven()+"',"+intNumDoc+" ,"+lstZafFac.get(i).getNe_numCot()+","+
    //                                  " '' ,'Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() //+"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ (Double)hasTot.get("SUBTOTAL")*(-1)+ " ,"+lstZafFac.get(i).getNd_porIva()+" //,"+(Double)hasTot.get("TOTFAC")*(-1)/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
    //                                  /*Double.parseDouble(lstBigTotcab.get(1).toString())*(-1) */(Double)hasTot.get("IVA")*(-1)+ " , "+lstZafFac.get(i).getCo_forPag()+" //,'"+lstZafFac.get(i).getTx_desforpag()+"', "+"CURRENT_TIMESTAMP"+", "+
    //                                  " 1, "+"CURRENT_TIMESTAMP"+", null , "+lstZafFac.get(i).getCo_forret()+", '"+lstZafFac.get(i).getTx_vehret()+"'," +
    //                                  " '"+lstZafFac.get(i).getTx_choret()+"', 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'N', "+intCodMnuFac+" "+
    //                                  " ,'S', 'N', null ,'S') ";
    //        Statement stmSql=cnx.createStatement();
    //        stmSql.executeUpdate(strQue);
    //        stmSql.close();
    //        stmSql=null;        
    //    }catch(Exception ex){
    //        ex.printStackTrace();
    //        booRetn=false;
    //    }
    //    return booRetn;    
    //}
	
	
	public boolean booInsertarCabMovInv(List<ZafFac> lstZafFac, Map hasTot, int intSecGrp, int intSecEmp, int intCodDocNueFac,double dblPorIva,Connection cnx){
        boolean booRetn=true;
        int i=0;
        int intCodMnuFac=14, intNumDoc=0;        
        Double bigValComSol=0.0;
        try{
            /*if(lstZafFac.get(i).getCo_emp()==2){//CASTEK
                if(lstZafFac.get(i).getCo_loc()==4 || lstZafFac.get(i).getCo_loc()==12){
                    if(lstZafFac.get(i).getTx_ruc().length()==10){
                        bigValComSol=(Double)hasTot.get("IVACOMSOL");
                    }
                }
            }*/
            String strQue="insert into tbm_cabmovinv (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
                          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
                    
                          /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
                          //" tx_obs1, tx_obs2, nd_sub, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
                          /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                    
                          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
                          //" ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem) values("+lstZafFac.get(i).getCo_emp()+", "+lstZafFac.get(i).getCo_loc()+", "+
                          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem, nd_porcomsol, nd_valcomsol, nd_subivacer, nd_subivagra) values("+lstZafFac.get(i).getCo_emp()+", "+lstZafFac.get(i).getCo_loc()+", "+
                                      ((lstZafFac.get(i).getCo_tipdoc()==1)?228:lstZafFac.get(i).getCo_tipdoc())+", "+intCodDocNueFac+", "+"CURRENT_TIMESTAMP"+", "+lstZafFac.get(i).getCo_cli()+" ,'"+lstZafFac.get(i).getCo_com()+"','"+lstZafFac.get(i).getTx_ate()+"','"+
                                      lstZafFac.get(i).getTx_nomCli()+"','"+lstZafFac.get(i).getTx_dirCli()+"','"+lstZafFac.get(i).getTx_ruc()+ "','"+lstZafFac.get(i).getTx_telCli()+"','"+
                                      lstZafFac.get(i).getTx_ciuCli()+ "','"+lstZafFac.get(i).getTx_nomven()+"',"+intNumDoc+" ,"+lstZafFac.get(i).getNe_numCot()+","+
                                      //" '' ,'Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() +"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ (Double)hasTot.get("SUBTOTAL")*(-1)+ " ,14.00 ,"+(((Double)hasTot.get("TOTFAC")*(-1)))/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
                                      
                                      /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                                      " '' ,'Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() +"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ (Double)hasTot.get("SUBTOTAL")*(-1)+ " ,"+dblPorIva+","+(((Double)hasTot.get("TOTFAC")*(-1)))/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
                                      //" '' ,'Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() +"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ (Double)hasTot.get("SUBTOTAL")*(-1)+ ","+(((Double)hasTot.get("TOTFAC")*(-1)))/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
                                      /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                    
                                      /*Double.parseDouble(lstBigTotcab.get(1).toString())*(-1) */(Double)hasTot.get("IVA")*(-1)+ " , "+lstZafFac.get(i).getCo_forPag()+" ,'"+lstZafFac.get(i).getTx_desforpag()+"', "+"CURRENT_TIMESTAMP"+", "+
                                      " 1, "+"CURRENT_TIMESTAMP"+", null , "+lstZafFac.get(i).getCo_forret()+", '"+lstZafFac.get(i).getTx_vehret()+"'," +
                                      " '"+lstZafFac.get(i).getTx_choret()+"', 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'N', "+intCodMnuFac+" "+
				      //" ,'S', 'N', null ,'S') ";
                                      " ,'S', 'N', null ,'S',"+(Double)hasTot.get("PORCOMSOL")+","+(Double)hasTot.get("IVACOMSOL")+","+(Double)hasTot.get("SUBTOTIVACERO")*(-1) +","+(Double)hasTot.get("SUBTOTGRBIVA")* (-1)+ ") ";
            
            Statement stmSql=cnx.createStatement();
            stmSql.executeUpdate(strQue);
            stmSql.close();
            stmSql=null;        
        }catch(Exception ex){
            ex.printStackTrace();
            booRetn=false;
        }
        return booRetn;    
    }
    
        
    public boolean booInsertarCabMovInvBig(List<ZafFac> lstZafFac, Map hasTot, int intSecGrp, int intSecEmp, int intCodDocNueFac,double dblPorIva,Connection cnx, int intCodUsrNOADMIN){
        boolean booRetn=true;
        int i=0;
        int intCodMnuFac=14, intNumDoc=0;        
        Double bigValComSol=0.0;
        try{
            /*if(lstZafFac.get(i).getCo_emp()==2){//CASTEK
                if(lstZafFac.get(i).getCo_loc()==4 || lstZafFac.get(i).getCo_loc()==12){
                    if(lstZafFac.get(i).getTx_ruc().length()==10){
                        bigValComSol=(Double)hasTot.get("IVACOMSOL");
                    }
                }
            }*/
            String strQue="insert into tbm_cabmovinv (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
                          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
                    
                          /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
                          //" tx_obs1, tx_obs2, nd_sub, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
                          /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                    
                          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
                          //" ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem) values("+lstZafFac.get(i).getCo_emp()+", "+lstZafFac.get(i).getCo_loc()+", "+
                          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv , st_creguirem, nd_porcomsol, nd_valcomsol, nd_subivacer, nd_subivagra) values("+lstZafFac.get(i).getCo_emp()+", "+lstZafFac.get(i).getCo_loc()+", "+
                                      ((lstZafFac.get(i).getCo_tipdoc()==1)?228:lstZafFac.get(i).getCo_tipdoc())+", "+intCodDocNueFac+", "+"CURRENT_TIMESTAMP"+", "+lstZafFac.get(i).getCo_cli()+" ,'"+lstZafFac.get(i).getCo_com()+"','"+lstZafFac.get(i).getTx_ate()+"','"+
                                      lstZafFac.get(i).getTx_nomCli()+"','"+lstZafFac.get(i).getTx_dirCli()+"','"+lstZafFac.get(i).getTx_ruc()+ "','"+lstZafFac.get(i).getTx_telCli()+"','"+
                                      lstZafFac.get(i).getTx_ciuCli()+ "','"+lstZafFac.get(i).getTx_nomven()+"',"+intNumDoc+" ,"+lstZafFac.get(i).getNe_numCot()+","+
                                      //" '' ,'Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() +"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ (Double)hasTot.get("SUBTOTAL")*(-1)+ " ,14.00 ,"+(((Double)hasTot.get("TOTFAC")*(-1)))/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
                                      
                                      /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                                      //" '' ,'Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() +"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ ((BigDecimal)hasTot.get("SUBTOTAL")).multiply(new BigDecimal(-1))+ " ,"+dblPorIva+","+((((BigDecimal)hasTot.get("TOTFAC")).multiply(new BigDecimal(-1))))/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
                                      "'"+lstZafFac.get(i).getTx_obs1()+"','Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() +"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ ((BigDecimal)hasTot.get("SUBTOTAL")).multiply(new BigDecimal(-1))+ " ,"+dblPorIva+","+((((BigDecimal)hasTot.get("TOTFAC")).multiply(new BigDecimal(-1))))/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
                                      //" '' ,'Generado por volver a facturar. Factura origen #"+ lstZafFac.get(i).getNe_numDoc() +"',"+/*Double.parseDouble(lstBigTotcab.get(0).toString())*(-1) */ (Double)hasTot.get("SUBTOTAL")*(-1)+ ","+(((Double)hasTot.get("TOTFAC")*(-1)))/*Double.parseDouble(lstBigTotcab.get(2).toString())*(-1) */+", "+
                                      /* CAMBIO POR COMPENSACION SOLIDARIA SE PONE DEPRECATED EL CAMPO ND_PORIVA */
                    
                                      /*Double.parseDouble(lstBigTotcab.get(1).toString())*(-1) */((BigDecimal)hasTot.get("IVA")).multiply(BigDecimal.valueOf(-1))+ " , "+lstZafFac.get(i).getCo_forPag()+" ,'"+lstZafFac.get(i).getTx_desforpag()+"', "+"CURRENT_TIMESTAMP"+", "+
                                      intCodUsrNOADMIN+", "+"CURRENT_TIMESTAMP"+", null , "+lstZafFac.get(i).getCo_forret()+", '"+lstZafFac.get(i).getTx_vehret()+"'," +
                                      //" '"+lstZafFac.get(i).getTx_choret()+"', 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'N', "+intCodMnuFac+" "+
                                        " '"+lstZafFac.get(i).getTx_choret()+"', 'A', "+intSecGrp+", "+intSecEmp+","+lstZafFac.get(i).getTx_numped()+", 'I' ,'C' , 'N', "+intCodMnuFac+" "+                    
				      //" ,'S', 'N', null ,'S') ";
                                      " ,'S', 'N', null ,'S',"+((BigDecimal)hasTot.get("PORCOMSOL"))+","+((BigDecimal)hasTot.get("IVACOMSOL"))+","+((BigDecimal)hasTot.get("SUBTOTIVACERO")).multiply(new BigDecimal(-1)) +","+((BigDecimal)hasTot.get("SUBTOTGRBIVA")).multiply(BigDecimal.valueOf(-1))+ ") ";
            
            Statement stmSql=cnx.createStatement();
            stmSql.executeUpdate(strQue);
            stmSql.close();
            stmSql=null;        
        }catch(Exception ex){
            ex.printStackTrace();
            booRetn=false;
        }
        return booRetn;    
    }        
    
    
    public boolean booActualizarSolDevVen(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, Connection cnx){
    
        String strSqlUpd="UPDATE tbm_cabsoldevven SET st_mersindevfac='S' WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+"; ";
        boolean booRet=true;
        try{
            Statement stmSql=cnx.createStatement();
            stmSql.executeUpdate(strSqlUpd);
            stmSql.close();
            stmSql=null;    
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }
    
    public boolean booInsertarRelCabMovInv(ZafFac objZafFac, int intCodDocNueFac,Connection cnx){
        boolean booRet=true;
        try{
            String strSqlInsTbr=" INSERT INTO tbr_cabmovinv(co_emprel, co_locrel, co_tipdocrel, co_docrel, st_reg, co_emp, co_loc, co_tipdoc, co_doc ) "
                      + " SELECT co_emp, co_loc, co_tipdoc, co_doc , 'A', "+objZafFac.getCo_emp()+", "+objZafFac.getCo_loc()+", "+((objZafFac.getCo_tipdoc()==1)?228:objZafFac.getCo_tipdoc())+", "+intCodDocNueFac+" "
                      + " FROM tbm_cabmovinv where co_emp="+objZafFac.getCo_emp()+" and co_locrelsoldevven="+objZafFac.getIntCodLocSolDev()+" and co_tipdocrelsoldevven="+objZafFac.getIntCodTipDocSolDev()+" and co_docrelsoldevven= "+objZafFac.getIntCodDocSolDev()+" and st_reg='A' ";

            Statement stmSql=cnx.createStatement();
            stmSql.executeUpdate(strSqlInsTbr);
            stmSql.close();
            stmSql=null;
        }catch(Exception ex){
            booRet=false;
            ex.printStackTrace();
        }
        return booRet;
    }
    
    public boolean booInsertarDetCabMovInv(String strDetIns, Connection cnx){
        boolean booRet=true;
        StringBuffer strInsDet=new StringBuffer("");
        try{
            strInsDet.append(" INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
                    " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
                    //" co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
                    " co_bod, nd_can,nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes " +//<==Campos que aparecen en la parte inferior del 1er Tab
                    //",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil )"+ strDetIns);
                    ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_preunivenlis, nd_pordesvenmax , ne_numfil, nd_basimpivagra, nd_basimpivacer,nd_poriva  )"+ strDetIns);
            Statement stmSql=cnx.createStatement();
            stmSql.executeUpdate(strInsDet.toString());
            stmSql.close();
            stmSql=null;
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }
            
    
    
    public ResultSet obtenerDatDevDet(int intCodEmpSolDev, int intLocSolDev, int intTipDocSolDev, int intCodSolDev, Connection cnx){
        ResultSet rst=null;
        try{
            String strQue=" select s.nd_canvolfac, inv.st_ser, d.nd_preuni,"
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
            Statement stm=cnx.createStatement();
            rst=stm.executeQuery(strQue); 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rst;
    }
    
    public boolean booInsertarPagFac(ZafPagFac objPagFac, ZafFac objFac, int intCodFac, int intInd, Connection cnx){
        boolean booRet=false;
        Statement stmInsPagMov=null;
        SimpleDateFormat fmtStr=new SimpleDateFormat("yyyy/MM/dd");        
        try{
            String StrSqlInsPagMov="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, " + //CAMPOS PrimayKey
            " ne_diaCre, fe_ven, mo_pag, ne_diaGra, nd_porRet ,st_regrep , st_sop" +//<==
            " ,co_tipret ) VALUES ("+
            objFac.getCo_emp()+", "+objFac.getCo_loc()+", "+((objFac.getCo_tipdoc()==1)?228:objFac.getCo_tipdoc()) +", "+/*lstBigTot.get(6)*/intCodFac +", "+(intInd)+", "+
            objPagFac.getIntDiaCrd()+", '"+fmtStr.format(objPagFac.getDatFecVec()) +"',"+
            objPagFac.getBigMontPag().multiply(new BigDecimal(-1)).setScale(2, RoundingMode.HALF_UP)+","+//objUti.redondear( getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON)), 2 ) * -1)+", "+
            objPagFac.getIntDiaGra()+", "+
            objPagFac.getBigPorRet()+", 'I', '"+objPagFac.getStrSopChq()+"', "+
            (objPagFac.getIntCodTipRet()==0?null:objPagFac.getIntCodTipRet())+" ) ";

            stmInsPagMov=cnx.createStatement();
            stmInsPagMov.executeUpdate(StrSqlInsPagMov);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return booRet;
    }
    
}
