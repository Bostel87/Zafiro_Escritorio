/**
 * Clase para obtener los datos del item 
 */
package Librerias.ZafGetDat;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 * @author Jota
 */
public class ZafDatItm
{
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strSQL;
    private String strVersion="v0.07";
    
    /* JM Se agrego para el proyecto de Codigos L con bloqueo 19/Jul/2019 */
    private ArrayList arlReg, arlDat;
    private static int INT_ARL_COD_EMP=0;
    private static int INT_ARL_COD_ITM=1;
    private static int INT_ARL_ALT_ITM=2;
    private int intCantItems=-1;
    
    private static int INT_DIA_LIB_ITM=10;
    /* JM Se agrego para el proyecto de Codigos L con bloqueo 19/Jul/2019 */

    public ZafDatItm(ZafParSis obj, java.awt.Component parent){
        try{
            System.out.println("ZafDatItm "+strVersion);
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    /**
     * Metodo para verificar si los terminales L en una cotizacion estan libres
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    public boolean verificarCodigosL(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        try{
            if(revisarItemsTerminalL(CodEmp, CodLoc, CodCot)){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Modifica los terminales L que esten ocupados en una cotizacion y los reemplaza con uno libre, 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    public boolean modificaItemTerminalOcupado(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null) {
                conLoc.setAutoCommit(false);
                if(iniciaProcesoTerminalL(conLoc, CodEmp, CodLoc, CodCot)){
                    blnRes=true;
                    conLoc.commit();
                }
                else{
                    conLoc.rollback();
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Se marcan para liberar los terminales L despues de generada una factura
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    public boolean preLiberarItems(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(modificarItemsL(conExt, CodEmp, CodLoc, CodCot)){
                    blnRes=true;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Marcar los items de la cotizacion para ser liberados despues de 10 dias segun lo solicitado por LSC y WCampoverde
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean modificarItemsL(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" UPDATE tbm_inv SET fe_libItm = current_date + "+INT_DIA_LIB_ITM+"   \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a1.co_emp, a1.co_itm \n";
                strSQL+="       FROM tbm_equInv as a1 \n";
                strSQL+="       INNER JOIN ( \n";
                strSQL+="               SELECT a2.co_itmMae \n";
                strSQL+="               FROM tbm_detCotVen as a1 \n";
                strSQL+="               INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="               INNER JOIN tbm_inv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) \n";
                strSQL+="               WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" AND a3.tx_codAlt like '%L' AND a3.st_ser='N' AND  \n";
                strSQL+="                      LENGTH(a3.tx_codALt) = 4 ";
                strSQL+="       ) as a2 ON (a1.co_itmMae=a2.co_itmMae) \n";
                strSQL+="       ORDER BY a1.co_emp, a1.co_itm \n";
                strSQL+=" ) as a1  \n";
                strSQL+=" WHERE tbm_inv.co_emp=a1.co_emp AND tbm_inv.co_itm=a1.co_itm  ;   \n";
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    /**
     * Marca los terminales L como ocupados
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    public boolean marcarItemsOcupados(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                if(terminalesOcupados(conLoc, CodEmp, CodLoc, CodCot)){
                    blnRes=true;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean iniciaProcesoTerminalL(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(obtenerCantidadTerminalesLNecesarios(conExt, CodEmp, CodLoc, CodCot)){
                    if(modificarItemsCotizacion(conExt, CodEmp, CodLoc, CodCot)){
                        if(terminalesOcupados(conExt, CodEmp, CodLoc, CodCot)){
                            blnRes=true;
                        }
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Marcar los items de la cotizacion como ocupados
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean terminalesOcupados(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" UPDATE tbm_inv SET st_itmOcu = 'S', fe_bloItm=CURRENT_DATE  \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a1.co_emp, a1.co_itm \n";
                strSQL+="       FROM tbm_equInv as a1 \n";
                strSQL+="       INNER JOIN ( \n";
                strSQL+="               SELECT a2.co_itmMae \n";
                strSQL+="               FROM tbm_detCotVen as a1 \n";
                strSQL+="               INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="               INNER JOIN tbm_inv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) \n";
                strSQL+="               WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" AND a3.tx_codAlt like '%L' AND  \n";
                strSQL+="                      a3.st_ser='N' AND LENGTH(a3.tx_codALt) = 4 ";
                strSQL+="       ) as a2 ON (a1.co_itmMae=a2.co_itmMae) \n";
                strSQL+="       ORDER BY a1.co_emp, a1.co_itm \n";
                strSQL+=" ) as a1  \n";
                strSQL+=" WHERE tbm_inv.co_emp=a1.co_emp AND tbm_inv.co_itm=a1.co_itm  ;   \n";
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * Se cambia en el detalle de la cotizacion el codigo del item y el codigo alterno
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    private boolean modificarItemsCotizacion(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strUpd="";
        int i=0;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot, a2.co_reg, a2.co_itm, a2.tx_codAlt, a3.st_itmOcu \n";
                strSQL+=" FROM tbm_cabCotVen as a1 \n";
                strSQL+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" AND a3.st_ser='N' AND a2.tx_codAlt like '%L' and \n";
                strSQL+="       a3.st_itmOcu='S' AND LENGTH(a3.tx_codALt) = 4 ";
                strSQL+=" ORDER BY a2.co_reg ";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    strUpd+=" UPDATE tbm_detCotVen SET co_itm="+objUti.getIntValueAt(arlDat, i, INT_ARL_COD_ITM)+", \n";
                    strUpd+="                          tx_codAlt="+objUti.codificar(objUti.getStringValueAt(arlDat, i, INT_ARL_ALT_ITM))+", \n";
                    strUpd+="                          tx_codAlt2="+objUti.codificar(objUti.getStringValueAt(arlDat, i, INT_ARL_ALT_ITM))+" \n";
                    strUpd+=" WHERE co_emp="+rstLoc.getInt("co_emp")+" AND co_loc="+rstLoc.getInt("co_loc")+" AND co_cot="+rstLoc.getInt("co_cot")+" AND co_reg="+rstLoc.getInt("co_reg")+"; ";
                    i++;
                }
                System.out.println("strSQL \n" + strUpd);
                stmLoc.executeUpdate(strUpd);
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    /**
     * Obtiene la cantidad de items terminal L que son necesarias para modificar la cotizacion, con el numero de items necesarios 
     * lleno un arrayList de items que se usaran para modificar la cotizacion y poner como ocupados los items
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    
    
    private boolean obtenerCantidadTerminalesLNecesarios(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT COUNT(a2.co_reg)  as tantos \n";
                strSQL+=" FROM tbm_cabCotVen as a1 \n";
                strSQL+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" AND a3.st_ser='N' AND a2.tx_codAlt like '%L' AND  \n";
                strSQL+="       a3.st_itmOcu='S' AND LENGTH(a3.tx_codALt) = 4 ";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCantItems=rstLoc.getInt("tantos");
                }
                rstLoc.close();
                rstLoc=null;
                
                arlDat=new java.util.ArrayList(); 
                
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.st_itmOcu \n";
                strSQL+=" FROM tbm_inv as a1 \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.st_ser='N' and  a1.tx_codAlt like '%L' AND st_itmOcu IS NULL  AND  \n";
                strSQL+=" LENGTH(a1.tx_codALt) = 4  \n";
                strSQL+=" ORDER BY a1.tx_codALt  \n";
                strSQL+=" LIMIT "+intCantItems+"   \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    arlReg=new java.util.ArrayList();
                    arlReg.add(INT_ARL_COD_EMP, rstLoc.getInt("co_emp"));
                    arlReg.add(INT_ARL_COD_ITM, rstLoc.getInt("co_itm")); 
                    arlReg.add(INT_ARL_ALT_ITM, rstLoc.getString("tx_codAlt"));
                    arlDat.add(arlReg);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                
                
                if(intCantItems!=arlDat.size()){
                    blnRes=false;
                    mostrarMsgInf("No hay suficientes terminales L disponibles, comuniquese con el Departamento de Sistemas...");
                }
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(cmpPad,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    
    private boolean revisarItemsTerminalL(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot, a2.co_reg, a2.co_itm, a2.tx_codAlt, a3.st_itmOcu \n";
                strSQL+=" FROM tbm_cabCotVen as a1 \n";
                strSQL+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)  \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" AND a3.st_ser='N' AND a2.tx_codAlt like '%L' and a3.st_itmOcu='S' \n";
                strSQL+="       AND LENGTH(a3.tx_codALt) = 4 ";
                System.out.println("revisarItemsTerminalL " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                     blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    /**
     * Metodo para obtener el codigo del Item en Grupo
     * @param intCodEmp Codigo de la empresa
     * @param intCodItm Codigo del item en la empresa enviada
     * @return 
     */
    public int getCodigoItemGrupo(int intCodEmp, int intCodItm){
        int intCodItmGrp=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_itm \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_itmMae = ( \n";
                strSQL+="                        select co_itmMae  \n";
                strSQL+="                        from tbm_Equinv as a1 \n";
                strSQL+="                        where co_emp="+intCodEmp+" and co_itm="+intCodItm+")  \n";
                strSQL+=" and x1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmGrp=rstLoc.getInt("co_itm");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            intCodItmGrp=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            intCodItmGrp=-1;
        }
        return intCodItmGrp;
    }

    /**
     * Metodo para obtener el codigo maestro del item
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */        
    public int getCodigoMaestroItem(int intCodEmp, int intCodItm){
        int intCodItmMae=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.co_itmMae \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" and x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmMae=rstLoc.getInt("co_itmMae");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            intCodItmMae=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            intCodItmMae=-1;
        }
        return intCodItmMae;
    }

    /**
     * Metodo para obtener el Nombre del Item
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getNombreItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT  tx_nomItm \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_nomItm");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener el Codigo Alterno del Item '870-125I'
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getCodigoAlternoItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT  tx_codAlt \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_codAlt");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener la unidad de medida del Item PZS
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
     public String getUnidadMedidaItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT  x1.tx_codAlt, a3.tx_desCor \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" LEFT OUTER JOIN tbm_var as a3 ON (x1.co_uni=a3.co_reg) ";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_desCor");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

     /**
      * Metodo para obtener el peso del Item en Kilogramos
      * @param intCodEmp
      * @param intCodItm
      * @return 
      */
     public double getPesoItem(int intCodEmp, int intCodItm){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN nd_pesItmKgr IS NULL THEN 0 ELSE nd_pesItmKgr END as nd_pesItmKgr \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_pesItmKgr");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            dblRes=-1.00;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
             dblRes=-1.00;
        }
        return dblRes;
    }

    /**
     * Metodo para obtener el codigo de 3 letras del item XLD
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getCodigoLetraItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2 \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_codAlt2");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener el codigo de 3 letras del item XLD
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getIsItemServicio(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.st_ser \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("st_ser");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener el DISPONIBLE DE UN ITEM POR GRUPO y BODEGA DE GRUPO
     * El disponible retornado es la sumatoria del disponible de todas las empresas existentes.
     * @param conExt Conexion 
     * @param CodItmMae Código Maestro del ítem.
     * @param CodBodGrp Código de Bodega en el Grupo, bodega de la que se desea obtener el disponible.
     * @return 
     */
     public double getDisponibleItemGrupo(java.sql.Connection conExt, int CodItmMae, int CodBodGrp){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_canDis) as nd_canDis,a.tx_codAlt \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod,  a4.co_empGrp, a4.co_bodGrp,a2.tx_codAlt, \n";
                strSQL+="              CASE WHEN a1.nd_canDis IS NULL THEN 0 ELSE a1.nd_canDis END as nd_canDis \n";
                strSQL+="       FROM tbm_invBod AS a1   \n";
                strSQL+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="       INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod) \n";
                strSQL+="       WHERE a3.co_itmMae IN (  "+CodItmMae+"  )  \n";
                strSQL+=" ) as a \n";
                strSQL+=" WHERE a.co_bodGrp = ( "+CodBodGrp+" ) \n";
                strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_canDis");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            dblRes=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            dblRes=-1;
        }
        return dblRes;
     }

/**
     * Metodo para obtener el DISPONIBLE DE UN ITEM POR GRUPO y BODEGA DE GRUPO
     * El disponible retornado es la sumatoria del disponible de todas las empresas existentes.
     * @param conExt Conexion 
     * @param CodItmMae Código Maestro del ítem.
     * @param CodBodGrp Código de Bodega en el Grupo, bodega de la que se desea obtener el disponible.
     * @return 
     */
     public double getDisponibleItemEmpresa(java.sql.Connection conExt,int CodEmp, int CodItmMae, int CodBodGrp){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_canDis) as nd_canDis,a.tx_codAlt \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod, \n";
                strSQL+="              CASE WHEN a1.nd_canDis IS NULL THEN 0 ELSE a1.nd_canDis END as nd_canDis, ";
                strSQL+="              a4.co_empGrp, a4.co_bodGrp,a2.tx_codAlt  ";
                strSQL+="       FROM tbm_invBod AS a1   \n";
                strSQL+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="       INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod) \n";
                strSQL+="       WHERE a3.co_itmMae IN ( "+CodItmMae+" ) AND a1.co_emp="+CodEmp+" \n";
                strSQL+=" ) as a \n";
                strSQL+=" WHERE a.co_bodGrp = ( "+CodBodGrp+" ) \n";
                strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                System.out.println("getDisponibleItemEmpresa: " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_canDis");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            dblRes=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            dblRes=-1;
        }
        return dblRes;
     }
     
     
     
     public boolean isObligatorioModificarNombreDelItem(int CodEmp, int CodItm, String NombreDelItem){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;       
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN a1.st_oblCamNomItm IS NULL THEN 'N' ELSE a1.st_oblCamNomItm END AS st_oblCamNomItm, \n";
                strSQL+="        a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm  \n";
                strSQL+=" FROM tbm_inv as a1  \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_itm="+CodItm+" \n";
                strSQL+=" \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    System.out.println("NombreDelItem cotizacion: " + NombreDelItem);
                    System.out.println("nombre del item base: "+ objUti.codificar(rstLoc.getString("tx_nomItm")) );
                    if(rstLoc.getString("st_oblCamNomItm").equals("S")){
                        if(NombreDelItem.equals(objUti.codificar(rstLoc.getString("tx_nomItm")))){
                            blnRes=true;
                        }
                        else{
                            System.out.println("st_oblCamNomItm::: Si es obligatorio pero si esta cambiado ");
                        }
                    }
                }
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
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
     }
     
     
     
     
    /**
     * Metodo para obtener el codigo del Item en Grupo
     * @param intCodEmp Empresa en la que se quiere el codigo del item.
     * @param intCodItm Codigo del item maestro.
     * @return 
     */
    public int getCodigoItemEmpresa(int intCodEmp, int intCodItmMae){
        int intCodItm=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.co_itm \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_itmMae = "+intCodItmMae+" ";
                strSQL+=" and x1.co_emp="+intCodEmp+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItm=rstLoc.getInt("co_itm");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            intCodItm=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            intCodItm=-1;
        }
        return intCodItm;
    }

     
         
}
