/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;


import java.io.Serializable;

/**
 * Esta clase recibe los parametros para la creacion del objeto que se usará 
 * dentro de las clases de impresión de las órdenes de despacho que vienen por la
 * cotización de la venta
 * @author sistemas4
 */
public class ZafImp implements Serializable{

  
    private int emp;
    private int loc;
    private int tipdoc;
    private int numdoc;
    
    private int coDoc;
    private int fpago;
    private char tipo; //campo del nuevo objeto para saber si es emisor o receptor en la transferencia o prestamo
    private int comnu;
    private int intCocli;
    private int intCoBodOri;//para OD por transferencias desde bodProy
    private int intCoBodDes;//para OD por transferencias desde bodProy
    
    
    /**
     * Este constructor sirve para la recepcion de los parámetros que vienen de la cotización de la venta donde
     * es relevante la forma de pago para ver que se va a realizar, ejm. si es credito a 30 días, la orden de despacho se genera 
     * automáticamente, sino, dicha OD se generará al momento que el cliente paque en otras modalidades.
     */
      public ZafImp(int emp, int loc, int tipdoc, int numdoc, int fpago) {
        this.setEmp(emp);
        this.setLoc(loc);
        this.setTipdoc(tipdoc);
        this.setNumdoc(numdoc);
        this.setFpago(fpago);
      }
    
      public ZafImp(int emp, int loc, int tipdoc, int numdoc, int fpago, int cocli) {
        this.setEmp(emp);
        this.setLoc(loc);
        this.setTipdoc(tipdoc);
        this.setNumdoc(numdoc);
        this.setFpago(fpago);
        this.setIntCocli(cocli);
      }
      
      /**
       *constructor para otros eventos diferentes de cotizacion de venta, recepción de cheques por ejemplo
       */
     public ZafImp(int emp, int loc, int tipdoc, int numdoc) {
        this.setEmp(emp);
        this.setLoc(loc);
        this.setTipdoc(tipdoc);
        this.setNumdoc(numdoc);
        
      }
     
     
     public ZafImp(int emp, int loc, int tipdoc, int codoc, boolean boor) {
        this.setEmp(emp);
        this.setLoc(loc);
        this.setTipdoc(tipdoc);
        this.setCoDoc(codoc);
        
      }
     
    /**
     * constructor para las transferencias o prestamos siendo tipo un emisor o receptor de ingresos o egresos de bodegas
     */
    
      public ZafImp(int emp, int loc, int tipdoc, int numdoc,char tipo) {
        this.setEmp(emp);
        this.setLoc(loc);
        this.setTipdoc(tipdoc);
        this.setNumdoc(numdoc);
        this.setTipo(tipo);
        
      }
    
     public ZafImp(int emp, int loc, int tipdoc, int numdoc, int fpago, int cocli,int comnu) {
        this.setEmp(emp);
        this.setLoc(loc);
        this.setTipdoc(tipdoc);
        this.setNumdoc(numdoc);
        this.setFpago(fpago);
        this.setIntCocli(cocli);
        this.setComnu(comnu);
      }  

   
     
     
     
     
    /**
     * Constructor vacío
     */
    public ZafImp() {
    }
  
     
    public int getEmp() {
        return emp;
    }

    public void setEmp(int emp) {
        this.emp = emp;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getTipdoc() {
        return tipdoc;
    }

    public void setTipdoc(int tipdoc) {
        this.tipdoc = tipdoc;
    }

    public int getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(int numdoc) {
        this.numdoc = numdoc;
    }

    public int getFpago() {
        return fpago;
    }

    public void setFpago(int fpago) {
        this.fpago = fpago;
    }

    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getCoDoc() {
        return coDoc;
    }

    public void setCoDoc(int coDoc) {
        this.coDoc = coDoc;
    }

    public int getComnu() {
        return comnu;
    }

    public void setComnu(int comnu) {
        this.comnu = comnu;
    }

    public int getIntCocli() {
        return intCocli;
    }

    public void setIntCocli(int intCocli) {
        this.intCocli = intCocli;
    }

    public int getIntCoBodOri() {
        return intCoBodOri;
    }

    public void setIntCoBodOri(int intCoBodOri) {
        this.intCoBodOri = intCoBodOri;
    }

    public int getIntCoBodDes() {
        return intCoBodDes;
    }

    public void setIntCoBodDes(int intCoBodDes) {
        this.intCoBodDes = intCoBodDes;
    }

   
   
}
