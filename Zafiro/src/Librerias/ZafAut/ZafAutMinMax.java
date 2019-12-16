/*
 * ZafAutMinMax.java
 *
 * Created on 16 de enero de 2006, 18:02
 */

package Librerias.ZafAut;

/**
 *
 * @author  ireyes
 */
public class ZafAutMinMax {
    private Librerias.ZafParSis.ZafParSis objZafParSis;    
    private javax.swing.JInternalFrame jfrThis;     
    private Librerias.ZafUtil.ZafUtil objUtil;

    /** Creates a new instance of ZafAutMinMax */
    public ZafAutMinMax(javax.swing.JInternalFrame jfrThis, Librerias.ZafParSis.ZafParSis objZafParSis) {
        this.objZafParSis = objZafParSis;
        this.jfrThis      = jfrThis;       
        objUtil = new Librerias.ZafUtil.ZafUtil ();
        
    }

   
    /**
     * Retorna la lista de controles por los que se esta 
     * pidiendo autorizacion y que no habian pasado.
     * Adicional retorna el minimo y maximo permitido
     * para autorizar por este usuario.
     */ 
    public boolean chkCtlsCotWithMinAndMax( int intCoDoc, String strTblCabAut, String strTblDetAut){

        java.util.Vector vecCtls = new java.util.Vector();
        boolean blnIsCorrect=true;
        java.sql.Connection conCtls;
        
        try{
            
                    conCtls=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                         
                    if (conCtls!=null){
                        java.sql.Statement stmStDoc = conCtls.createStatement();

                        String strSql = " select det.st_cum, reg.tx_descor, reg.tx_obs1, reg.co_reg,reg.tx_nomfun , doc.nd_par1, doc.nd_par2   " +
                                        " from ((" + strTblCabAut + " as cab inner join " + strTblDetAut + " as det" +
                                        "      ON   (cab.co_emp = det.co_emp and cab.co_loc = det.co_loc  and cab.co_cot = det.co_cot and cab.co_aut = det.co_aut))" +
                                        "       inner join tbm_regneg as reg On ( det.co_regneg = reg.co_reg and cab.co_emp = reg.co_emp and cab.co_loc = reg.co_loc ))" +
                                        "       left outer join tbm_autdoc as doc On ( doc.co_emp = cab.co_emp and doc.co_loc = cab.co_loc and doc.co_reg = reg.co_reg and doc.co_usr=" +objZafParSis.getCodigoUsuario()+ ")" +
                                        " where " +
                                        "      cab.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        "      cab.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                        "      cab.co_cot = " + intCoDoc  + " and  " +
                                        "      det.st_cum = 'N'               and  " +
                                        "      cab.co_aut = (" +
                                                    "       select  max(co_aut) from " +
                                                    "           " + strTblCabAut +
                                                    "       where " +
                                                    "           co_emp =  " + objZafParSis.getCodigoEmpresa() + " and " +
                                                    "           co_loc =  " + objZafParSis.getCodigoLocal()   + " and " +
                                                    "           co_cot =  " + intCoDoc + 
                                                    ") " ;
 /*
                        String strSql = " select det.st_cum, reg.tx_descor, reg.tx_obs1, reg.co_reg, reg.tx_nomfun   " +
                                        " from ((" + strTblCabAut + " as cab inner join " + strTblDetAut + " as det" +
                                        "      ON   (cab.co_emp = det.co_emp and cab.co_loc = det.co_loc  and cab.co_cot = det.co_cot and cab.co_aut = det.co_aut))" +
                                        "       inner join tbm_regneg as reg On ( det.co_regneg = reg.co_reg and cab.co_emp = reg.co_emp and cab.co_loc = reg.co_loc ))" +
                                        " where " +
                                        "      cab.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        "      cab.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                        "      cab.co_cot = " + intCoDoc  + " and  " +
                                        "      det.st_cum = 'N'               and  " +
                                        "      cab.co_aut = (" +
                                                    "       select  max(co_aut) from " +
                                                    "           " + strTblCabAut +
                                                    "       where " +
                                                    "           co_emp =  " + objZafParSis.getCodigoEmpresa() + " and " +
                                                    "           co_loc =  " + objZafParSis.getCodigoLocal()   + " and " +
                                                    "           co_cot =  " + intCoDoc + 
                                                    ") " ;
                        
                        
  */                        
                      
                         
                        java.sql.ResultSet rstCtls = stmStDoc.executeQuery(strSql);
                        String strNomFun = "", strDblMax, strDblMin;
                        
                        
                        
                        //System.out.println("Query : " + strSql);
                        
                        while(rstCtls.next() && blnIsCorrect){
                            
                            java.util.Vector vecDetCtl = new java.util.Vector();
                            String strStCum  = rstCtls.getString("st_cum")+"",
                                   strDesCor = rstCtls.getString("tx_descor")+"",
                                   strObs1   = rstCtls.getString("tx_obs1")+"" ,
                                   strCoReg  = rstCtls.getString("co_reg")+"" ;
                                   
                                   strDblMin    = rstCtls.getString("nd_par1");
                                   strDblMax    = rstCtls.getString("nd_par2");
                                   strNomFun = rstCtls.getString("tx_nomfun")+"";
                                   
                                   blnIsCorrect = invocarFuncion(strNomFun, strDesCor, intCoDoc , strDblMin, strDblMax );
                                   
                                   //Agregando los datos de esta funcion
                                    
                                   vecDetCtl.add(new Boolean(strStCum.equalsIgnoreCase("N")));
                                   vecDetCtl.add(strDesCor);
                                   vecDetCtl.add(strObs1);
                                   vecDetCtl.add(strCoReg);
                                   vecDetCtl.add(strNomFun);

                                   vecCtls.add(vecDetCtl);                            
                                   
                        }
                        
                        System.out.println(vecCtls);
                        conCtls.close();
                        conCtls = null;
                    }            
        }
        catch (java.sql.SQLException e)
        {
            objUtil.mostrarMsgErr_F1(jfrThis, e);
            blnIsCorrect = false;
        }
        catch (Exception e)
        {
            objUtil.mostrarMsgErr_F1(jfrThis, e);
            blnIsCorrect = false;
        }
//        return false;
        return blnIsCorrect;        
    }

    /**
     * Retorna la lista de controles por los que se esta 
     * pidiendo autorizacion y que no habian pasado.
     * Adicional retorna el minimo y maximo permitido
     * para autorizar por este usuario.
     */ 
    public boolean chkCtlsDocWithMinAndMax( int intCoDoc, int intCodTipDoc, String strTblCabAut, String strTblDetAut){

        java.util.Vector vecCtls = new java.util.Vector();
        boolean blnIsCorrect=true;
        java.sql.Connection conCtls;
        
        try{
            
                    conCtls=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                         
                    if (conCtls!=null){
                        java.sql.Statement stmStDoc = conCtls.createStatement();

                        String strSql = " select det.st_cum, reg.tx_descor, reg.tx_obs1, reg.co_reg,reg.tx_nomfun , doc.nd_par1, doc.nd_par2   " +
                                        " from ((" + strTblCabAut + " as cab inner join " + strTblDetAut + " as det" +
                                        "      ON   (cab.co_emp = det.co_emp and cab.co_loc = det.co_loc  and cab.co_doc = det.co_doc and cab.co_aut = det.co_aut))" +
                                        "       inner join tbm_regneg as reg On ( det.co_regneg = reg.co_reg and cab.co_emp = reg.co_emp and cab.co_loc = reg.co_loc ))" +
                                        "       left outer join tbm_autdoc as doc On ( doc.co_emp = cab.co_emp and doc.co_loc = cab.co_loc and doc.co_reg = reg.co_reg and doc.co_usr=" +objZafParSis.getCodigoUsuario()+ ")" +
                                        " where " +
                                        "      cab.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        "      cab.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                        "      cab.co_doc = " + intCoDoc  + " and  " +
                                        "      cab.co_tipdoc= " + intCodTipDoc + " and  " +
                                        "      det.st_cum = 'N'               and  " +
                                        "      cab.co_aut = (" +
                                                    "       select  max(co_aut) from " +
                                                    "           " + strTblCabAut +
                                                    "       where " +
                                                    "           co_emp =  " + objZafParSis.getCodigoEmpresa() + " and " +
                                                    "           co_loc =  " + objZafParSis.getCodigoLocal()   + " and " +
                                                    "           co_doc =  " + intCoDoc + " and " + 
                                                    "           co_tipdoc = " +  intCodTipDoc +
                                                    ") " ;
   
                      
                         
                        java.sql.ResultSet rstCtls = stmStDoc.executeQuery(strSql);
                        String strNomFun = "", strDblMax, strDblMin;
                        
                        
                        
                        //System.out.println("Query : " + strSql);
                        
                        while(rstCtls.next() && blnIsCorrect){
                            
                            java.util.Vector vecDetCtl = new java.util.Vector();
                            String strStCum  = rstCtls.getString("st_cum")+"",
                                   strDesCor = rstCtls.getString("tx_descor")+"",
                                   strObs1   = rstCtls.getString("tx_obs1")+"" ,
                                   strCoReg  = rstCtls.getString("co_reg")+"" ;
                                   
                                   strDblMin    = rstCtls.getString("nd_par1");
                                   strDblMax    = rstCtls.getString("nd_par2");
                                   strNomFun = rstCtls.getString("tx_nomfun")+"";
                                   
                                   blnIsCorrect = invocarFuncionDoc(strNomFun, strDesCor, intCoDoc, intCodTipDoc , strDblMin, strDblMax);
                                   
                                   //Puede borrar estos datos del vector solo se utilizan para presentar 
                                   //a manera de prueba
                                    
                                   vecDetCtl.add(new Boolean(strStCum.equalsIgnoreCase("N")));
                                   vecDetCtl.add(strDesCor);
                                   vecDetCtl.add(strObs1);
                                   vecDetCtl.add(strCoReg);
                                   vecDetCtl.add(strNomFun);

                                   vecCtls.add(vecDetCtl);                            
                                   
                        }
                        
                        System.out.println(vecCtls);
                        conCtls.close();
                        conCtls = null;
                    }            
        }
        catch (java.sql.SQLException e)
        {
            objUtil.mostrarMsgErr_F1(jfrThis, e);
            blnIsCorrect = false;
        }
        catch (Exception e)
        {
            objUtil.mostrarMsgErr_F1(jfrThis, e);
            blnIsCorrect = false;
        }
//        return false;
        return blnIsCorrect;        
    }

    
    
    public void Mensaje(String strMsg){
        String strTit;
        strTit="Mensaje del sistema Zafiro";

        //javax.swing.JOptionPane obj =new javax.swing.JOptionPane();

        //obj.showConfirmDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
        javax.swing.JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
        //javax.swing.JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    
   /**
    *  Invoca un funcion psada como parametro  
    */
    private boolean invocarFuncion(String funcion, String strDesCor, int intCodDoc, String strDblMin , String strDblMax)
    {
        boolean blnRes=true;
        try
        {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };            
            java.lang.reflect.Method objMet=this.getClass().getMethod(funcion, types);
            Object objParams[] = new Object[4];
            objParams[0] = strDesCor ;
            objParams[1] = new Integer(intCodDoc);
            objParams[2] = strDblMin;
            objParams[3] = strDblMax;
            Boolean bl = new Boolean(objMet.invoke(this, objParams )+"") ;
            return bl.booleanValue();
        }
        catch (NoSuchMethodException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (SecurityException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        return blnRes;
    }    
        
   /**
    *  Invoca un funcion psada como parametro  
    */
    private boolean invocarFuncionDoc(String funcion, String strDesCor, int intCodDoc, int intCodTipDoc, String strDblMin , String strDblMax)
    {
        boolean blnRes=true;
        try
        {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };            
            java.lang.reflect.Method objMet=this.getClass().getMethod(funcion, types);
            Object objParams[] = new Object[5];
            objParams[0] = strDesCor ;
            objParams[1] = new Integer(intCodDoc);
            objParams[2] = new Integer(intCodTipDoc);
            objParams[3] = strDblMin;
            objParams[4] = strDblMax;
            Boolean bl = new Boolean(objMet.invoke(this, objParams )+"") ;
            return bl.booleanValue();
        }
        catch (NoSuchMethodException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (SecurityException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
        {
            blnRes=false;
            System.out.println(e);
        }
        return blnRes;
    }    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
