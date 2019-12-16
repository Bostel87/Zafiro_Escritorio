/*
 * ZafMonitorTbl.java
 *
 * Created on 13 de septiembre de 2005, 10:46
 */

package Librerias.ZafUtil;

/**
 * @creado  22/09/2005 
 * @author  IdiTrix
 */
public class ZafMonitorTbl {
    final int INT_IDX_CO_REG = 0; //CODIGO DEL ITEM
    final int INT_IDX_CO_ITM = 1; //CODIGO DEL ITEM
    final int INT_IDX_CO_BOD = 2; //CODIG DE LA BODEGA
    final int INT_IDX_CANMOV = 3; //CANTIDAD DEL MOVIMIENTO 
    final int INT_IDX_MODIFI = 4; //SE MODIFI CO EL REGISTRO
    private java.util.Vector vecData;
    private java.util.Vector vecDels;
    /** Creates a new instance of ZafMonitorTbl */
    public ZafMonitorTbl() {
        vecData = new java.util.Vector();
        vecDels = new java.util.Vector();
    }
    public void insertar(int intPos){
        if( !(intPos <0 || intPos > vecData.size())){
            vecData.add(intPos , null);
        }
    }
    public void eliminar(int intPos){
        
        if( !(intPos <0 || intPos >= vecData.size())){
            if(vecData.get(intPos)!=null)
                vecDels.add(vecData.get(intPos));
            vecData.remove(intPos );
        }
    }
    
    public void wasModified(int intPos){
        
        if( !(intPos <0 || intPos >= vecData.size())){
            if(vecData.get(intPos)!=null){
                java.util.Vector vecFila = (java.util.Vector)vecData.get(intPos);
                vecFila.set(INT_IDX_MODIFI, new Boolean(true));
            }
        }
    }
    public void agregar(java.util.Vector vecReg){
        vecData.add(vecReg);
    }
    
    public java.util.Vector getVecData(){
        return vecData;
    }
    
    public java.util.Vector getRegData(int intIdx){
        return (java.util.Vector)vecData.get(intIdx);
    }
    
    /**
     * Funciones que serviran para trabajar con los elementos 
     * eliminados
     */
    public java.util.Vector getVecDels(){
        return vecDels;
    }
    
    /**
     * Obtiene el numero de elemntos eliminador
     */
    public int  getDelsCount(){
        return vecDels.size();
    }
    
    /**
     * Obtiene valor del campo  co_reg 
     * del registro eliminado
     */
    public int getDelCoReg(int intIdx){
        try{
            String strValor = ((java.util.Vector)vecDels.get(intIdx)).get(INT_IDX_CO_REG) + "";
  	    int intCoReg = Integer.parseInt(strValor);	
            return intCoReg;
        }
        catch(NumberFormatException e){
            return 0;
        }
    }
    
    /**
     * Obtiene valor del campo  co_itm 
     * del registro eliminado 
     */
    public int getDelCoItm(int intIdx){
        try{
            String strValor = ((java.util.Vector)vecDels.get(intIdx)).get(INT_IDX_CO_ITM) + "";
  	    int intCoItm = Integer.parseInt(strValor);	
            return intCoItm;
        }
        catch(NumberFormatException e){
            return 0;
        }
    }
    
   /**
     * Obtiene valor del campo  nd_can (cantidad movida) 
     * del registro eliminado 
     */
    public double getDelCan(int intIdx){
        try{
            String strValor = ((java.util.Vector)vecDels.get(intIdx)).get(INT_IDX_CANMOV) + "";
            java.math.BigDecimal objBigDec=new java.math.BigDecimal(strValor);
            //objBigDec=objBigDec.setScale(intDecimales, java.math.BigDecimal.ROUND_HALF_UP);
            return objBigDec.doubleValue();
        }
        catch(NumberFormatException e){
            return 0;
        }
        catch (Exception e)
        {
            return 0;                        
          }                
    }

    /**
     * Obtiene valor del campo  co_bod (bodega de la que se vendio el item) 
     * del registro eliminado 
     */
    public int getDelBod(int intIdx){
        try{
            String strValor = ((java.util.Vector)vecDels.get(intIdx)).get(INT_IDX_CO_BOD) + "";
  	    int intCan = Integer.parseInt(strValor);
            return intCan;
        }
        catch(NumberFormatException e){
            return 0;
        }
    }
    
    /***************************************************************************
     *
     *    METODOS PARA TRABAJAR CON LOS DATOS DEL VECDATA
     *
     **************************************************************************/
    
    /**
     * Obtiene el numero de elemntos en el vector
     */
    public int  getCount(){
        return vecData.size();
    }
    
    /**
     * Obtiene valor del campo  co_reg 
     * del registro 
     */
    public int getCoReg(int intIdx){
        try{
            String strValor = ((java.util.Vector)vecData.get(intIdx)).get(INT_IDX_CO_REG) + "";
  	    int intCoReg = Integer.parseInt(strValor);	
            return intCoReg;
        }
        catch(NumberFormatException e){
            return 0;
        }
        catch (Exception e)
        {
            return 0;                        
          }        
    }
    /**
     * Obtiene el codigo del item
     * devuelve -1 si el item fue insertadp
     */
    public int getCoItm(int intIdx){
        int intRes =-1;
    
        if( !(intIdx <0 || intIdx >= vecData.size()))
            if(vecData.get(intIdx)!=null)
                 intRes = Integer.parseInt(((java.util.Vector)vecData.elementAt(intIdx)).elementAt(INT_IDX_CO_ITM) + "") ;
            
        return intRes;
    }
        
    /**
     * Obtiene el cantidad vendida de item 
     * devuelve 0 si es insertado
     */
    public double getCanMov(int intIdx){
        try{
            double dblRes =0;

            if( !(intIdx <0 || intIdx >= vecData.size()))
                if(vecData.get(intIdx)!=null)
                     dblRes = Double.parseDouble(((java.util.Vector)vecData.elementAt(intIdx)).elementAt(INT_IDX_CANMOV) + "") ;

            return dblRes;

//                java.math.BigDecimal objBigDec=new java.math.BigDecimal(strValToDbl);
//                objBigDec=objBigDec.setScale(intNumDec, java.math.BigDecimal.ROUND_HALF_UP);
//                return objBigDec.doubleValue();
        }
        catch (NumberFormatException e)
        {
            return 0;                        
          }
        catch (Exception e)
        {
            return 0;                        
          }        
    }
    
    /**
     * Obtiene valor del campo  co_bod (bodega de la que se vendio el item) 
     * del registro 
     */
    public int getBod(int intIdx){
        try{
            String strValor = ((java.util.Vector)vecData.get(intIdx)).get(INT_IDX_CO_BOD) + "";
  	    int intCan = Integer.parseInt(strValor);
            return intCan;
        }
        catch(NumberFormatException e){
            return 0;
        }
        catch (Exception e)
        {
            return 0;                        
          }        
    }
    
    /**
     * Devuelve verdadero si el registro que corresponde al 
     * indice enviado como parametro es un registro insertado
     */
    public boolean isRegIns(int intIdx){
        boolean blnres =true;

        if( !(intIdx <0 || intIdx >= vecData.size()))
            if(vecData.get(intIdx)!=null)            
                blnres =false;
        return blnres ;
    }
    /**
     * Devuelve verdadero si el registro que corresponde al 
     * indice enviado como parametro es un registro que ha sido modificado
     */
    public boolean isModificado(int intIdx){
        boolean blnres =false;

        if( !(intIdx <0 || intIdx >= vecData.size()))
            if(vecData.get(intIdx)!=null){
                String strValor =((java.util.Vector)vecData.get(intIdx)).get(INT_IDX_MODIFI) + "";
                if(strValor.equalsIgnoreCase("true")){
                    blnres =true;    
                }
            }
        
        return blnres ;
    }

    /**
     * Devuelve verdadero si el indice enviado como parametro
     * coincide con la posicion originarl que tenia este registro en el detalle
     * (en el campo co_reg)
     * indice enviado como parametro es un registro que ha sido modificado
     */
    public boolean isPosicionOriginal(int intIdx){
        boolean blnres =false;
        if(getCoReg(intIdx-1)== intIdx){
            blnres =true;    
        }
        return blnres ;
    }
    
    
        
    public void removeAll(){
        vecData.clear();
        vecDels.clear();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
