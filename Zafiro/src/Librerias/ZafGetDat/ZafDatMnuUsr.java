/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGetDat;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author Sistemas6
 */
public class ZafDatMnuUsr {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strVersion=" v 0.02", strSql;
    
    private int INT_ARL_DAT_COD_EMP=0;
    private int INT_ARL_DAT_COD_LOC=1;
    private int INT_ARL_DAT_COD_USR=2;
    private int INT_ARL_DAT_DES_ACC=3;
    private int INT_ARL_DAT_UBI_X=4;
    private int INT_ARL_DAT_UBI_Y=5;
    private int INT_ARL_DAT_NOM_MNU=6;
    
    
    public ZafDatMnuUsr(ZafParSis obj, java.awt.Component parent){
        try{
//            System.out.println("ZafDatMnuUsr "+strVersion);
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    /**
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodUsr
     * @param Nombre
     * @param DesLar
     * @param arlDatosPerfil
     * @return 
     */
    
    public boolean guardarPerfil(int CodEmp, int CodLoc, int CodUsr, String Nombre,String DesLar, ArrayList arlDatosPerfil){
        boolean blnRes=false;
        try{
            java.sql.Connection conLoc;
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                conLoc.setAutoCommit(false);
                if(insertarRegistro(conLoc, CodEmp, CodLoc, CodUsr, Nombre, DesLar, arlDatosPerfil)){
                    blnRes=true;
                    conLoc.commit();
                }
                else{
                    conLoc.rollback();
                    mostrarMsgInf("Error al guardar el perfil... ");
                }
                conLoc.close();
                conLoc=null;
            }
        }
         catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodUsr
     * @param Nombre
     * @param DesLar
     * @param arlDatosPerfil
     * @return 
     */
    
    private boolean insertarRegistro(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodUsr, String Nombre,String DesLar, ArrayList arlDatosPerfil){
        boolean blnRes=true;
         
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCodPer=-1;
        try{
            if (conExt!=null) {
                stmLoc=conExt.createStatement();
                
                strSql="";
                strSql+=" SELECT CASE WHEN MAX(co_per) IS NULL THEN 0 ELSE MAX(co_per) END as max ";
                strSql+=" FROM tbm_cabPerUsr ";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_usr="+CodUsr+"; ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodPer = rstLoc.getInt("max");
                }
                rstLoc.close();
                rstLoc = null;
                intCodPer++;
                strSql="";
                strSql+=" INSERT INTO tbm_cabPerUsr (co_emp, co_loc, co_usr, co_per, tx_desCor, tx_desLar, st_reg, co_usrIng, fe_ing)  ";
                strSql+=" VALUES ("+CodEmp+", "+CodLoc+", "+CodUsr+", "+intCodPer+", "+objUti.codificar(Nombre, 1)+", "+ objUti.codificar(DesLar, 1) +", 'A' ,"+objParSis.getCodigoUsuario()+",CURRENT_TIMESTAMP ); ";
                stmLoc.executeUpdate(strSql);
                
                for(int i=0;i<arlDatosPerfil.size();i++){
                    strSql="";
                    strSql+=" INSERT INTO tbm_detPerUsr (co_emp, co_loc, co_usr, co_per, co_reg, tx_acc, ne_corX, ne_corY, tx_nom  ) \n";
                    strSql+=" VALUES ( "+objUti.getIntValueAt(arlDatosPerfil, i, INT_ARL_DAT_COD_EMP)+", "+objUti.getIntValueAt(arlDatosPerfil, i, INT_ARL_DAT_COD_LOC)+", ";
                    strSql+=" "+objUti.getIntValueAt(arlDatosPerfil, i, INT_ARL_DAT_COD_USR)+", "+intCodPer+","+(i+1)+",";
                    strSql+=" "+objUti.codificar(objUti.getStringValueAt(arlDatosPerfil, i, INT_ARL_DAT_DES_ACC),0)+",";
                    strSql+=" "+objUti.codificar(objUti.getIntValueAt(arlDatosPerfil, i, INT_ARL_DAT_UBI_X),2)+",";
                    strSql+=" "+objUti.codificar(objUti.getIntValueAt(arlDatosPerfil, i, INT_ARL_DAT_UBI_Y),2)+", ";
                    strSql+=" " +objUti.codificar(objUti.getStringValueAt(arlDatosPerfil, i, INT_ARL_DAT_NOM_MNU),0)+ " ); ";
//                    System.out.println(" "+ strSql);
                    stmLoc.executeUpdate(strSql);
                }
                
                strSql="";
                strSql+=" UPDATE tbm_detPerUsr SET co_mnu = a1.co_mnu  \n";
                strSql+=" FROM(   \n";
                strSql+="   SELECT a1.* \n";
                strSql+="   FROM ( \n";
                strSql+="       SELECT a1.co_emp, a1.co_loc, a1.co_usr, a1.co_per, a1.co_reg, a2.co_mnu   ";
                strSql+="       FROM tbm_detPerUsr as a1      ";
                strSql+="       INNER JOIN tbm_mnuSis as a2 ON (a1.tx_acc=a2.tx_acc AND a1.tx_nom=a2.tx_nom) ";
                strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_usr="+CodUsr+" AND a1.co_per="+intCodPer+" ";
                strSql+="       ) as a1 \n";
                strSql+=" ) as a1 ";
                strSql+=" WHERE tbm_detPerUsr.co_emp=a1.co_emp AND  \n";
                strSql+="       tbm_detPerUsr.co_loc=a1.co_loc AND   \n";
                strSql+="        tbm_detPerUsr.co_usr=a1.co_usr AND   \n";
                strSql+="       tbm_detPerUsr.co_per=a1.co_per AND   \n";
                strSql+="       tbm_detPerUsr.co_reg=a1.co_reg ;   \n";
//                System.out.println(" "+ strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodUsr
     * @param CodEsc Codigo del escritorio
     * @param DesCor Descripcion corta
     * @param DesLar Descripcion larga 
     * @return 
     */

    public boolean modificarEscritorio(java.sql.Connection conExt,int CodEmp, int CodLoc, int CodUsr, int CodEsc, String DesCor, String DesLar){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(updateEscritorio(conExt, CodEmp, CodLoc, CodUsr, CodEsc, DesCor, DesLar)){
                    blnRes=true;
                }
            }
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(cmpPad, Evt); 
        }
        return blnRes; 
    }
    
    
    /**
     * Eliminación logica 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodUsr
     * @param CodEsc
     * @return 
     */
    public boolean eliminarEscritorio(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodUsr, int CodEsc){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(anularEscritorio(conExt, CodEmp, CodLoc, CodUsr, CodEsc)){
                    blnRes=true;
                }
            }
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(cmpPad, Evt); 
        }
        return blnRes; 
    }
    
    
    
    private boolean anularEscritorio(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodUsr, int CodEsc){
         boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabPerUsr SET st_reg = 'I',  ";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_usr="+CodUsr+" AND co_per="+CodEsc+" ; ";
               
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(cmpPad, Evt); 
        }
        return blnRes; 
    }
    
     
     /**
      * Metodo para modificar los datos de un escritorio.
      * @param conExt
      * @param CodEmp
      * @param CodLoc
      * @param CodUsr
      * @param CodEsc
      * @param DesCor
      * @param DesLar
      * @return 
      */
     
    private boolean updateEscritorio(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodUsr, int CodEsc, String DesCor, String DesLar){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabPerUsr SET tx_desCor = "+objUti.codificar(DesCor,0)+", tx_desLar="+objUti.codificar(DesLar,0)+" ";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_usr="+CodUsr+" AND co_per="+CodEsc+" ; ";
                
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(cmpPad, Evt); 
        }
        return blnRes; 
     }
     
    
}
