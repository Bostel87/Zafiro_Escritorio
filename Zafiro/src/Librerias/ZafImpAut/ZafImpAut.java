/*
 * ZafImpAut  
 *
 * Created on Jun 16 2017, 10:00 AM
 */
package Librerias.ZafImpAut;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;


public class ZafImpAut extends javax.swing.JPanel 
{
    private ZafParSis objParSis;
    private ZafCorEle objCorEle;
    private ZafUtil objUti;
    private String strVersion = " v 0.1", strCorEleErr="sistemas6@tuvalsa.com", strSql ;
    private ZafCfgSer objCfgSer;
    private int intCodSer=16;// Impresiones mateo
    private GenOD.ZafGenOdPryTra objGenOD;

    /**
     * Creates a new instance of ZafNotAle
     */
    public ZafImpAut(ZafParSis obj) 
    {
        try{
            objParSis = obj;
            objUti = new ZafUtil();  
            objCfgSer = new ZafCfgSer(objParSis);
            objCfgSer.cargaDatosIpHostServicios(0, intCodSer);
            objGenOD = new GenOD.ZafGenOdPryTra();
            System.out.println("ZafImpAut: " + strVersion);
            
            
            //Crear y correr el hilo que controla la memoria.
            hilo();
            
            
          
            
        }
        catch (Exception e) {    
            objUti.mostrarMsgErr_F1(this, e);    
        }
    }
    private ZafThrConMem objThrConMem;
    
    private void hilo(){
        try{
            objThrConMem = new ZafThrConMem();
            objThrConMem.start();
        }
        catch (Exception e) {    
            objUti.mostrarMsgErr_F1(this, e);    
        }
    }

    private void matarHilo(){
        try{
            
            
            if (objThrConMem!=null){
                objThrConMem.interrupt();
//                objThrConMem.stop();
                objThrConMem=null;
               
            }
        }
        catch (Exception e) {    
            System.err.println("matarHilo " + e.toString());
        }
    }
    
    
             
 
    /**
     * Esta clase crea un hilo que muestra el consumo de memoria en un
     * JProgressBar.
     */
    private class ZafThrConMem extends Thread 
    {
        public ZafThrConMem() {
        }

        @Override
        public void run(){
            try {
                 if(impresionesAutomaticas()){
                     matarHilo();
                     System.out.println("ZafThrConMem ok..!!");
                 }
                 else{
                     matarHilo();
                     System.out.println("ZafThrConMem No se corrio..!!");
                 }
            }
            catch (Exception e) {    
                System.err.println("Excepción: " + e.toString());    
                objCorEle.enviarCorreoMasivo(strCorEleErr,"ZafImpAut: "+strVersion,"ERROR: " + e.toString());
            }
        }
         
    }
     
 
    private boolean impresionesAutomaticas(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc != null){
                if(usuariosDespacho(conLoc)){
                    if(imprimeODpendientes(conLoc)){
                        System.out.println("OK IMPRIMIOO");
                        blnRes=true;
                    }
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch(Exception e){
            objCorEle.enviarCorreoMasivo(strCorEleErr,"ZafImpAut: "+strVersion,"ERROR: " + e.toString());
            blnRes=false;            
        }
        return blnRes;
        
    }
    
    
    private boolean usuariosDespacho(java.sql.Connection conExt){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT co_usr, tx_usr, co_grpUsr, tx_nom  ";
                strSql+=" FROM tbm_usr ";
                strSql+=" WHERE co_usr="+objParSis.getCodigoUsuario()+" AND co_grpUsr IN (14) AND st_reg='A' ";
                 
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objCorEle.enviarCorreoMasivo(strCorEleErr,"ZafImpAut: "+strVersion,"ERROR: " + Evt.toString());
            blnRes=false; 
        }
        catch(Exception e){
            objCorEle.enviarCorreoMasivo(strCorEleErr,"ZafImpAut: "+strVersion,"ERROR: " + e.toString());
            blnRes=false;            
        }
        return blnRes;
        
    }
    
    
    
    private boolean imprimeODpendientes(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a4.co_emp,a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.fe_doc, a4.co_cli, a4.tx_nomCli,  \n";
                strSql+="        a1.co_emp as co_empOD, a1.co_loc as co_locOD, a1.co_tipDoc as co_tipDocOC, a1.co_doc as co_docOD, \n";
                strSql+="        a1.ne_numOrdDes, a1.co_cliDes, tx_nomCliDes, a1.st_imp,a1.st_impOrdDes , a5.st_autSolResInv  \n";
                strSql+=" FROM tbm_cabGuiRem as a1  \n";
                strSql+=" INNER JOIN tbm_detGuiRem as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
                strSql+="                                    a1.co_tipDoc=a2.co_tipDoc AND a1.co_Doc=a2.co_doc)    \n";
                strSql+=" INNER JOIN tbm_detMovInv as a3 ON (a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND \n";
                strSql+="                                    a2.co_tipDOcRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc AND a2.co_regRel=a3.co_reg) \n";
                strSql+=" INNER JOIN tbm_cabMovInv as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND  \n";
                strSql+="                                    a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)  \n";
                strSql+=" INNER JOIN tbm_cabCotVen as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.ne_numCot=a5.co_cot)	 \n";
                strSql+=" WHERE a4.fe_doc >= current_Date -1  and a1.st_imp='N' and a4.st_genOrdDes='S' and a4.st_ordDesGen='S' AND \n";
                strSql+="       a5.st_reg='F' AND a5.st_autSolResInv IS NOT NULL and a1.st_impOrdDes = 'N'   \n";
                strSql+=" GROUP BY a4.co_emp,a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.fe_doc, a4.co_cli, a4.tx_nomCli , \n";
                strSql+="          a1.co_emp , a1.co_loc , a1.co_tipDoc, a1.co_doc , a1.ne_numDoc,  \n";
                strSql+="          a1.co_cliDes, tx_nomCliDes, a1.st_imp,a1.st_impOrdDes ,a5.st_autSolResInv  \n";
                strSql+=" \n";
                
	        rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    objGenOD.imprimirOdLocal(conExt, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipDoc"), rstLoc.getInt("co_doc"), objCfgSer.getIpHost());// PARA IMPRIMIR LA OD 
                    System.out.println("imprimi...");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objCorEle.enviarCorreoMasivo(strCorEleErr,"ZafImpAut: "+strVersion,"ERROR: " + Evt.toString());
            blnRes=false; 
        }
        catch(Exception e){
            objCorEle.enviarCorreoMasivo(strCorEleErr,"ZafImpAut: "+strVersion,"ERROR: " + e.toString());
            blnRes=false;            
        }
        return blnRes;
        
    }
    
}
