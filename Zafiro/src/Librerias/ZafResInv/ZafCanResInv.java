/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafResInv;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sistemas6
 */
public class ZafCanResInv {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafCorEle objCorEle;
    
    private java.awt.Component cmpPad;
    
    private String strVersion="Cancelacion de Reservas v0.02", strSql;
    private String strCorEleErr="sistemas6@tuvalsa.com;sistemas9@tuvalsa.com";
    
    private String strVer=" v0.03";
    
    public ZafCanResInv(ZafParSis obj, java.awt.Component componente){
         try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=componente;
            objCorEle = new ZafCorEle(objParSis);
            System.err.println(strVersion);
         }
         catch (CloneNotSupportedException e){
            System.out.println("ZafCanResInv: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
         }
     }
    
    /**
     * Metodo para cencelar una reserva por completo
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
     public boolean iniciaCancelacionReservas(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         try{
             if(conExt != null){
                if(cancelarDocumento(conExt, CodEmp, CodLoc,CodCot)){
                  if(colocarInventarioDisponible(conExt, CodEmp, CodLoc,CodCot)){
                        if(disponiblesNegativos(conExt, CodEmp, CodLoc, CodCot)){
                            blnRes=true;
                            enviarCorreoCotizacionCancelada(CodEmp,CodLoc,CodCot);
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }else{blnRes=false;}
             }
         }
         catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, Evt);
        }
        return blnRes;
     }   
    
     
     
     /**
      * Cancelacion de Documentos
      * Parciales y totales
      * @param conExt
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @return 
      */
     
     private boolean cancelarDocumento(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Statement stmLoc;
         try{
             if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";//
                strSql+=" UPDATE tbm_detCotVen SET  nd_canCan=a1.nd_canPenFac,   nd_canPenFac=0, \n";
                strSql+="                           nd_valCan=a1.nd_valCanCal  \n";
                strSql+=" FROM (  \n";
                strSql+="       SELECT a1.*, (a1.totalCalculado - a1.nd_valFac) as  nd_valCanCal  \n";
                strSql+="       FROM (  \n";
                strSql+="           SELECT a2.co_emp, a2.co_loc,a2.co_cot,a2.co_reg, a2.nd_can,  \n";
                strSql+="                  CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END AS nd_canFac,  \n";
                strSql+="                  CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END  AS nd_canCan, \n";
                strSql+="                  CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END  AS nd_canPenFac, \n";
                strSql+="                  CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END  AS nd_valFac,  \n";
                strSql+="                  CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END  AS nd_valCan,  \n";
                strSql+="                  CASE WHEN a2.nd_canAutRes IS NULL THEN 0 ELSE a2.nd_canAutRes END  AS nd_canAutRes,  \n";
		strSql+="                  SUM((ROUND( a2.nd_preUni* a2.nd_can - (a2.nd_preUni* a2.nd_can)*a2.nd_porDes/100,2) )) as totalCalculado  \n";
                strSql+="           FROM tbm_cabCotVen as a1  \n";
                strSql+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="           WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND \n";
		strSql+="                 a1.st_solResInv='P' AND a1.st_autSolResInv = 'A' AND a1.co_tipSolResInv=5 AND a1.st_reg IN ('A','U','E') \n";
                strSql+="           GROUP BY a2.co_emp, a2.co_loc,a2.co_cot,a2.co_reg    \n";
                strSql+="       ) as a1 \n";
                strSql+=" ) as a1 \n";
		strSql+=" WHERE tbm_detCotVen.co_emp=a1.co_emp AND  \n";  
		strSql+="       tbm_detCotVen.co_loc=a1.co_loc AND \n";
		strSql+="       tbm_detCotVen.co_cot=a1.co_cot AND \n";
		strSql+="       tbm_detCotVen.co_reg=a1.co_reg; \n";
                System.out.println("CancelarDocumento 1: " + strSql);
                stmLoc.executeUpdate(strSql);
                
                strSql="";
                strSql+=" UPDATE tbm_cabCotVen SET st_solResInv = 'C' , nd_valSolResInvCan = a1.nd_valPenFac, st_reg='F' \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a1.*, (a1.nd_sub-a1.nd_valFac) as nd_valPenFac \n";
                strSql+="       FROM ( \n";
                strSql+="           SELECT a2.co_emp, a2.co_loc,a2.co_cot,a1.nd_sub, SUM(a2.nd_can) AS nd_can, \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END) AS nd_canFac,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END) AS nd_canCan,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END) AS nd_canPenFac,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END) AS nd_valFac , \n";
                strSql+="                  SUM(CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END) AS nd_valCan,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canAutRes IS NULL THEN 0 ELSE a2.nd_canAutRes END) AS nd_canAutRes , \n";
                strSql+="                  SUM((ROUND( a2.nd_preUni* a2.nd_can - (a2.nd_preUni* a2.nd_can)*a2.nd_porDes/100,2) )) as totalCalculado   \n";
                strSql+="           FROM tbm_cabCotVen as a1  \n";
                strSql+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)  \n";
                strSql+="           WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND \n";
                strSql+="                 a1.st_solResInv <> 'C' AND  a1.st_autSolResInv = 'A' AND a1.co_tipSolResInv=5 AND a1.st_reg IN ('A','U','E')  \n";
		strSql+="           GROUP BY a2.co_emp, a2.co_loc,a2.co_cot, a1.nd_sub \n";
		strSql+="       ) as a1 \n";
                strSql+=" ) as a1  \n";
                strSql+=" WHERE tbm_cabCotVen.co_emp=a1.co_emp AND  \n";
                strSql+="       tbm_cabCotVen.co_loc=a1.co_loc AND  \n";
                strSql+="       tbm_cabCotVen.co_cot=a1.co_cot; \n";
                System.out.println("CancelarDocumento 2: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
         }
         catch(java.sql.SQLException e){ 
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false; 
        }
         catch (Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
         }
         return blnRes;
     }
     
     private boolean colocarInventarioDisponible(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
          java.sql.Statement stmLoc;
         try{
             if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_invBod SET nd_canDis=CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END + a.nd_canCan, \n";
                strSql+="                       nd_canRes=CASE WHEN nd_canRes IS NULL THEN 0 ELSE nd_canRes END - a.nd_canCan  \n";
                strSql+=" FROM ( \n";
                strSql+="       select a2.tx_codAlt, a1.co_emp, a1.co_loc, a1.co_cot,a2.co_bod, a2.co_itm, a2.nd_can,   \n";
                strSql+="              CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END  AS nd_canCan  ";
                strSql+="       from tbm_cabCotVen as a1  \n";
                strSql+="       inner join tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="       inner join tbm_invBod as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm AND a2.co_bod=a3.co_bod) \n";
                strSql+="       where  a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"   \n";
                strSql+="       order by a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg	 \n";
                strSql+=" )as a  \n";
                strSql+=" WHERE tbm_invBod.co_emp=a.co_emp AND tbm_invBod.co_itm=a.co_itm AND tbm_invBod.co_bod=a.co_bod; \n";
                strSql+=" \n";
                 System.out.println("colocarInventarioDisponible: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
         }
         catch(java.sql.SQLException e){ 
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false; 
        }
         catch (Exception e) {
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
         }
         return blnRes;
     }
     
    private boolean disponiblesNegativos(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
       boolean blnRes=true;
       java.sql.Statement stmLoc;
       java.sql.ResultSet rstLoc;
       try{
           if(conExt!=null){
              stmLoc = conExt.createStatement();
              strSql="";
              strSql+=" SELECT a2.tx_codAlt, a1.co_emp, a1.co_loc, a1.co_cot, a2.co_itm, a2.nd_can, a3.nd_canDis \n";
              strSql+=" FROM tbm_cabCotVen as a1  \n";
              strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
              strSql+=" INNER JOIN tbm_invBod as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm AND a2.co_bod=a3.co_bod) \n";
              strSql+=" WHERE  a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"   \n";
              strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a2.co_itm \n";
               System.out.println("disponiblesNegativos:" + strSql);
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){
                  if(rstLoc.getDouble("nd_canDis")<0){
                      blnRes=false;
                  }
              }
              if(blnRes==false){
                  System.out.println("Negativo Cancelacion");
                  objCorEle.enviarCorreoMasivo(strCorEleErr, "RESERVAS CANCELACIONES: Negativos ", strSql);
              }
              stmLoc.close();
              stmLoc=null;
              blnRes=true;
          }
       }
       catch(java.sql.SQLException e){ 
          objUti.mostrarMsgErr_F1(cmpPad, e);
          blnRes=false; 
      }
       catch (Exception e) {
          objUti.mostrarMsgErr_F1(cmpPad, e);
          blnRes=false;
       }
       return blnRes;
   }
        
    private ZafNotCorEle objNotCorEle;  
    private static final int INT_ARL_COD_CFG_FAC_MAN_CAN=10;
        
    private void enviarCorreoCotizacionCancelada(int CodEmp, int CodLoc, int CodCot){
       String strAux="", strMensaje,strTit,strFin,strNomCli="",strNomLoc="",strFecCot="",strCodCot="",strCorEleDes="";
       java.sql.Connection conLoc;
       java.sql.Statement stmLoc;
       java.sql.ResultSet rstLoc;        
       try{
           conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(conLoc!=null){
               stmLoc=conLoc.createStatement();
               strSql="";
               strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.co_cli,a1.fe_cot, a3.tx_nom as tx_nomCli, a2.co_reg, \n";
               strSql+="        a2.co_itm,a2.tx_codAlt,a6.tx_codAlt2,CASE WHEN a7.st_impOrd='S' THEN a7.tx_ubi ELSE '' end as tx_ubi, \n";
               strSql+="        ROUND(a2.nd_can,2) as nd_can, \n";
               strSql+="        ROUND(CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END,2) AS nd_canFac, \n";
               strSql+="        ROUND(CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END,2) AS nd_canCan, \n";
               strSql+="          CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END AS nd_valFac, \n";
               strSql+="          CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END AS nd_valCan, \n";
               strSql+="          a4.tx_usr, a4.tx_corEle,a5.tx_nom as tx_nomLoc, \n";
               strSql+="        ROUND(a2.nd_can - (CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END) ,2) as cancelar   \n";
               strSql+=" FROM tbm_cabCotVen as a1 \n";
               strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
               strSql+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
               strSql+=" INNER JOIN tbm_usr as a4 ON (a1.co_usrIng=a4.co_usr) \n";
               strSql+=" INNER JOIN tbm_loc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc) \n";
               strSql+=" INNER JOIN tbm_inv as a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)  \n";
               strSql+=" INNER JOIN tbm_invBod as a7 ON (a2.co_emp=a7.co_emp AND a2.co_bod=a7.co_bod AND a2.co_itm=a7.co_itm) \n";
               strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n";
               strSql+=" ORDER BY a2.co_reg \n"; 
               System.out.println("enviarCorreoCotizacionCancelada: " + strSql);
               rstLoc = stmLoc.executeQuery(strSql);
               while(rstLoc.next()){
                   strNomCli=rstLoc.getString("tx_nomCli");
                   strNomLoc=rstLoc.getString("tx_nomLoc");
                   strFecCot=rstLoc.getString("fe_cot");
                   strCodCot=rstLoc.getString("co_cot");
                   strCorEleDes=rstLoc.getString("tx_corEle");

                   strAux+="<tr><td> "+rstLoc.getString("tx_codAlt")+" </td>";
                   strAux+=" <td> "+rstLoc.getString("tx_codAlt2")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("tx_ubi")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("nd_can")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("nd_canFac")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("cancelar")+" </td> </tr>";
               }
               strTit="<html> Notificación de Cancelación de Mercadería Reservada. <BR><BR>" ;// CAMBIA
               strTit+=" <b> Local: </b>"+ strNomLoc +" <BR>";
               strTit+=" <b> Cotizacion: </b>"+ strCodCot +" <BR>";
               strTit+=" <b> Cliente: </b>"+ strNomCli +" <BR>";
               strTit+=" <b> Fecha Cotizacion: </b>"+ strFecCot +" <BR><BR>";
               strTit+=" <table BORDER=1><tr><td> Item </td><td> Cod.Alt.2 </td><td> Ubicación </td><td> Cant.Cotizada </td><td> Cant.Facturada </td><td> Cant.Cancelada </tr>";  
               strFin=" </table><BR> <html>";
               strMensaje=strTit+strAux+strFin;
                 objCorEle.enviarCorreoMasivo(strCorEleDes+";sistemas6@tuvalsa.com", strTituloCancelacion, strMensaje);
               objNotCorEle = new ZafNotCorEle(objParSis);
               objNotCorEle.enviarNotificacionCorreoElectronico(CodEmp, CodLoc, INT_ARL_COD_CFG_FAC_MAN_CAN, strMensaje) ;
               objNotCorEle = null;
               rstLoc.close();
               rstLoc=null;
               stmLoc.close();
               stmLoc=null;
               conLoc.close();
               conLoc=null;
           }

        }
        catch(java.sql.SQLException e){ 
           objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e) {
           objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
     
     
     private String strTituloCancelacion="Reservas de inventario con facturación manual (Canceladas manualmente)";
     
    
    
}
