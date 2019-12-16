/*
 * ZafTblCelEdiTxt.java
 *
 * Created on 25 de julio de 2005, 03:02 PM
 */

package Librerias.ZafUtil;


import Librerias.ZafVenCon.ZafVenCon;  //************
import java.util.ArrayList;


/**
 * Esta clase establece el editor a utilizar en la celda de codigo de item en ventas o compras,
 * basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JTextField.
 * @author Iditrix
 * @version 3.1
 *
 */
public abstract class EditorCodItm3 extends javax.swing.JTextField implements javax.swing.table.TableCellEditor, Librerias.ZafUtil.ActionToDo 
{
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    private javax.swing.table.DefaultTableModel objTblMod;
    private Librerias.ZafParSis.ZafParSis objZafParSis;
  
   
      
   
    
    
    private boolean blnIsInActionPerfomed=false;
    private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit
    private int INT_TBL_CO_ALT;//Columna que tendra el codigo del sistema del item
    private int INT_TBL_CODITM;//Columna que tendra el codigo del sistema del item
    private int INT_TBL_DESITM; // Columan de la tabla que tendra la descripcion del Item actual
    private int INT_TBL_UNIDAD; // Columna de la tabla que tendra la unidad del producto
    private int INT_TBL_PREUNI; // Columna de la tabla que tendra el Precio Unitario
    private int INT_TBL_BLNIVA; // Columna de la tabla que tendra si el producto lleva Iva o no
    
    private int INT_TBL_CODBOD;            //Codigo de la bodega
    private int INT_TBL_CANMOV;     //Cantidad de Movimiento
    private int INT_TBL_TOTAL;     // Total de la venta o compra del producto
    private int INT_TBL_PORDES;   ///Porcentaje de descuento
    private int INT_TBL_PRE_UNI2;
    
    
    private Librerias.ZafUtil.ZafMonitorTbl objMon;    
    ZafVenCon objVenCon2; //*****************  
    
  
    
      javax.swing.JTextField valor = new javax.swing.JTextField();
      
      

      
      
      
    /** Crea una nueva instancia de la clase EditorCodItm. */
    public EditorCodItm3(javax.swing.JTable tabla, int intColItmAlt, int intColNext, int intColCodItm, int intColDesItm, int intColUnidad, int intColPreUni, int intColBlnIva, Librerias.ZafParSis.ZafParSis objZafParSis, Librerias.ZafUtil.ZafMonitorTbl objMon, int intCodBod, int intCanMov, int inttot, int intpordes, int intPreuni2,Librerias.ZafVenCon.ZafVenCon objVenCon2)
  //  public EditorCodItm3(javax.swing.JTable tabla, int intColItmAlt, int intColNext, int intColCodItm, int intColDesItm, int intColUnidad, int intColPreUni, int intColBlnIva, Librerias.ZafParSis.ZafParSis objZafParSis, Librerias.ZafUtil.ZafMonitorTbl objMon, int intCodBod, int intCanMov, int inttot, int intpordes, int intPreuni2)
   {   
          
           
        intColSigue   = intColNext;
        INT_TBL_CODITM= intColCodItm;
        INT_TBL_DESITM= intColDesItm;
        INT_TBL_PREUNI= intColPreUni; // Columna de la tabla que tendra el Precio Unitario
        INT_TBL_UNIDAD= intColUnidad;
        INT_TBL_BLNIVA= intColBlnIva; // Columna de la tabla que tendra si el producto lleva Iva o no        
        INT_TBL_CO_ALT= intColItmAlt;
        INT_TBL_CODBOD = intCodBod;
        INT_TBL_CANMOV = intCanMov;
        INT_TBL_TOTAL = inttot;
        INT_TBL_PORDES = intpordes;
        INT_TBL_PRE_UNI2 = intPreuni2;
        
        
        
        this.objZafParSis = objZafParSis;
        this.objVenCon2 = objVenCon2; //*****
        this.objMon  =  objMon;
        tblDat=tabla;
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
       
      
        
        
        
        
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                              
                thisActionPerformed(evt);
               
            }
        });
        //Seleccionar el texto cuando se obtenga el foco.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
               thisFocusGained(evt);
            }
        });
        
        //Agregar el "FocusListener" que avisará al JTable que se ha cancelado la edición de la celda.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(!blnIsInActionPerfomed)
                    ActualizarPrecio();
                    thisActionPerformed(null);
                   
            }
        });
        
        
        
   }
    
    /**
     * Esta función adiciona el listener que notificará cuando el editor se detenga
     * o cancele la edición de la celda.
     */
    
    public void ActualizarPrecio(){
        int CodItm = 0;
        if(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODITM) != null) {
            CodItm =  Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODITM).toString());
         
         String sql ="select nd_prevta1 from tbm_inv where co_itm =  "+ CodItm +" and co_emp="+ + objZafParSis.getCodigoEmpresa();
          try{
                     java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                     if (conUni!=null){
                              java.sql.Statement stmUni = conUni.createStatement();
                              java.sql.ResultSet rst = stmUni.executeQuery(sql);
                              rst.next();
                                   
                                 if(rst.getString(1) == null )
                                            tblDat.setValueAt(new Double("0.00"),tblDat.getSelectedRow(),INT_TBL_PREUNI);
                                         else
                                               tblDat.setValueAt(new Double(rst.getString(1)),tblDat.getSelectedRow(),INT_TBL_PREUNI);
                                    
                                    
                                 //*****///******    
                                     double topre = Double.parseDouble(rst.getString(1));
                                     String GG =  "0.00";
                                      if(  tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CANMOV) != null)
                                         GG =  tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CANMOV).toString();
                                     double Can = Double.parseDouble(GG);
                                     double Tot = topre * Can;
                                     String DD = "0.00";                            
                                     if( tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_PORDES) != null)
                                           DD =  tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_PORDES).toString();
                                     double PD = Double.parseDouble(DD);
                                     Tot =  Tot - (Tot * (PD/100));
                                     GG=String.valueOf(Tot);
                                     tblDat.setValueAt(new Double(GG),tblDat.getSelectedRow(),INT_TBL_TOTAL);
                                //******////****
                                        
                              
                                    stmUni.close();
                                    rst.close();
                                    conUni.close();
                                    stmUni = null;
                                    rst=null;
                                    conUni = null;
                          }
                 }
                 catch(java.sql.SQLException Evt)
                { System.out.println(Evt);  }
                              
        }
    }
    
    
    
    public void addCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        this.selectAll();
        objLinLis.add(l);
    }
    
    public void cancelCellEditing()
    {
        
    }
    
    /**
     * Esta función devuelve el nuevo valor que debe aparecer en la celda que se estaba editando.
     * Por ejemplo, si el editor era un "JComboBox" devolverá la opción que se seleccionó en
     * dicho combo.
     */
    public Object getCellEditorValue()
    {
        return this.getText();
    }
    
    /**
     * Esta función devuelve el "Component" utilizado para editar la celda. Por ejemplo, si el 
     * editor es un "JComboBox" devolverá el combo que se utilizará como editor de la celda.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column)
    {
        this.setText((value==null)?"":value.toString());
        return this;
    }
    
    /**
     * Esta función devuelve "true" si se desea que la celda sea editable y "false" en caso contrario.
     * Si se desea que apenas se de click en la celda aparezca el editor se debe quitar la validación
     * y sólo dejar la línea "return true;".
     */
    public boolean isCellEditable(java.util.EventObject anEvent)
    {
        //Validar que sólo se muestre el editor cuando se da doble click.
        if (anEvent instanceof java.awt.event.MouseEvent) 
        {
            if (((java.awt.event.MouseEvent)anEvent).getClickCount()!=2)
                return false;
        }            
        return true;
    }
    
    /**
     * Esta función remueve el listener que notifica cuando el editor se detiene
     * o cancele la edición de la celda.
     */
    public void removeCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        objLinLis.remove(l);
    }
    
    /**
     * Esta función devuelve "true" si se desea que la fila de la celda que se está editando sea
     * seleccionada y "false" en caso contrario.
     */
    public boolean shouldSelectCell(java.util.EventObject anEvent)
    {
        return true;
    }
    
    /**
     * Esta función debe devolver "true" cuando los datos que está introduciendo el usuario son correctos y false
     * en el caso contrario. Por ejemplo, puede ser que se esté utilizando un editor para ingreso de fechas y el
     * usuario ha ingresado 31/Feb/2005 en cuyo caso deveríamos devolver "false" para obligar al usuario a que
     * ingrese una fecha válida.
     */
    public boolean stopCellEditing()
    {
        super.fireActionPerformed();

        return true;
    }
    
   
    
    /**
     * Nombre de la función: Mostrar ventana de consulta.
     * Muestra la ventana de consulta para que el usuario seleccione la opción que desee.
     */
    
    public void LLenarDatos(Librerias.ZafVenCon.ZafVenCon objVenCon){
                 int intNumFil=tblDat.getSelectedRow();
                  
                 tblDat.setValueAt((objVenCon==null)?null:objVenCon.getValueAt(1),intNumFil,INT_TBL_CO_ALT);
                 tblDat.setValueAt((objVenCon==null)?null:objVenCon.getValueAt(2),intNumFil,INT_TBL_CODITM);
                 tblDat.setValueAt((objVenCon==null)?null:objVenCon.getValueAt(3),intNumFil,INT_TBL_DESITM);
                 tblDat.setValueAt((objVenCon==null)?null:objVenCon.getValueAt(7),intNumFil,INT_TBL_UNIDAD);

                 Boolean blnIva = new Boolean(false);
                   if(objVenCon.getValueAt(6).equals("S"))
                         blnIva = new Boolean(true);
                   tblDat.setValueAt(getBodPrede()+"",intNumFil,INT_TBL_CODBOD);
                   
                  
                    float preuni = Float.parseFloat(objVenCon2.getValueAt(5)); 
                    tblDat.setValueAt(preuni+"",intNumFil,INT_TBL_PREUNI);       //******
                  
                    tblDat.setValueAt(preuni+"",intNumFil,INT_TBL_PRE_UNI2);       //******
               
                    tblDat.setValueAt((objVenCon==null)?null:blnIva,intNumFil,INT_TBL_BLNIVA);
                  
                   
                   //*Aqui se encarga de colocar el porsentaje que puso en la primera celda
                       if(tblDat.getValueAt(0,INT_TBL_PORDES) != null)
                             tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
 
                       
                   
                   //*****///*****
                     
                       String GG =  "0.00";
                           if(  tblDat.getValueAt(intNumFil,INT_TBL_CANMOV) != null)
                                 GG =  tblDat.getValueAt(intNumFil,INT_TBL_CANMOV).toString();
                              float Can = Float.parseFloat(GG);
                              float Tot = preuni * Can;
                              String DD = "0.00";                            
                               if( tblDat.getValueAt(intNumFil,INT_TBL_PORDES) != null)
                                   DD =  tblDat.getValueAt(intNumFil,INT_TBL_PORDES).toString();
                            
                              float PD = Float.parseFloat(DD);
                                   Tot =  Tot - (Tot * (PD/100));
                    
                               tblDat.setValueAt(Tot+"",intNumFil,INT_TBL_TOTAL);
                  //*****///*********
    }
    
    
    
    
    private void mostrarVenCon()
    {
  //   configurarVenConProducto();
     
        if (objVenCon2.buscar("a7.tx_codAlt", this.getText()))
        {
           LLenarDatos(objVenCon2);
        }
        else {
            objVenCon2.cargarDatos();
            objVenCon2.show();
            if (objVenCon2.getSelectedButton()==objVenCon2.INT_BUT_ACE)
            {
                 LLenarDatos(objVenCon2);
            }
           
        }
     
      
      // objVenCon2.dispose();
      // objVenCon2=null;
       System.gc(); 
    }    
    
    
    
    /**
     * Nombre de la función: Establecer valor de la celda editada.
     * Establece el valor de la celda luego de utilizar el editor siempre y cuando el argumento que
     * reciba sea verdadero en caso contrario deja el valor que tenía la celda antes de ser editada.
     * @param hayCambios El estado de la celda que se está editando.
     * <BR>True: Si el contenido de la celda ha cambiado se avisa a los suscriptores de que se ha 
     * terminado la edición y que hay que establecer el nuevo valor de la celda.
     * <BR>False: Se avisa a los suscriptores que se ha cancelado la edición y que se debe dejar
     * el valor que tenía la celda tal y como estaba antes de la edición de la misma.
     */
    protected void setValCelEdi(boolean hayCambios)
    {
        javax.swing.event.ChangeEvent evt=new javax.swing.event.ChangeEvent(this);
        javax.swing.event.CellEditorListener objCelEdiLis;
        int  i, intRow = tblDat.getSelectedRow() ;
        for (i=0;i<objLinLis.size();i++)
        {        
            objCelEdiLis=(javax.swing.event.CellEditorListener)objLinLis.get(i);
            if (hayCambios)
                objCelEdiLis.editingStopped(evt);
            else
                objCelEdiLis.editingCanceled(evt);
            tblDat.changeSelection(intRow, intColSigue, false, false);
            tblDat.requestFocus();

        }
        
    }
    

    
    
    
    
    /*
     * Obtiene el Iva que se debe cobrar en la empresa actual
     */
    public String getUnidad(String int_co_itm){
           /*
            * Obteniendo la unidad del producto
            */
        
      
           String strDesUni = "";
           try{
                    java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    if (conUni!=null){
                        java.sql.Statement stmUni = conUni.createStatement();

                        String sSQL= "SELECT var.tx_descor " +
                                        " from tbm_inv as inv " +
                                        "	inner join tbm_var as var on (inv.co_uni = var.co_reg) "+ 
                                        " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                                        "       var.co_grp = 5  and inv.co_itm = " + int_co_itm ; 

                        java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                        if(rstUni.next())
                            strDesUni = (rstUni.getString("tx_descor")==null)?"":rstUni.getString("tx_descor");

                        rstUni.close();
                        stmUni.close();
                        conUni.close();
                        rstUni = null;
                        stmUni = null;
                        conUni = null;
                    }
           }
           catch(java.sql.SQLException Evt)
           {
                    System.out.println(Evt);
                    return strDesUni+"";
            }
            catch(Exception Evt)
            {
                    System.out.println(Evt);
                    return strDesUni+"";
            }                  

           return strDesUni;
    }  
        
    /**
     /* Esta función se ejecuta cuando se hace el "ActionPerformed" del JTextField.
     */
    
   
    
    
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {   blnIsInActionPerfomed=true;
        //Validar el contenido de la celda sólo si ha cambiado.
      
              
        if (!this.getText().equals(strConCel))
        {   
           //Si se borra el contenido de la celda hay que borrar el contenido de las celdas asociadas.
            if (this.getText()==null || this.getText().equals(""))
            {
               
            }
            else
            {
               mostrarVenCon();
            }
        }     
        setValCelEdi(false);        
        
        blnIsInActionPerfomed=false;
    }
    
    
    //** obtengo el codigo de la bogeda  **/
      private int getBodPrede(){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia            
        int intBodPre = 0;
        String sSQLBod;
        try{//odbc,usuario,password
            java.sql.Connection conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conExi!=null){ 
                stmInv=conExi.createStatement();

                sSQLBod=  " select co_bod   " +
                       " from tbr_bodloc " +
                       " where           " +
                       " co_emp =        " + objZafParSis.getCodigoEmpresa() + " and "+  
                       " co_loc =        " + objZafParSis.getCodigoLocal()   + " and " +
                       " st_reg ='P'";  
    
                rstInv=stmInv.executeQuery(sSQLBod);

                if (rstInv.next())
                    intBodPre = rstInv.getInt("co_bod");

               
                rstInv.close();
                stmInv.close();
                conExi.close();
                rstInv = null;
                stmInv = null;
                conExi = null;                    
            }
        }
        catch(java.sql.SQLException Evt)
        {
            return -1;
        }
        catch(Exception Evt)
        {
            return -1;
        }       
        
     //   System.out.println(intBodPre);
        return intBodPre;
    }
      
      
    
    /**
     * Esta función hace que se seleccione el texto del JTextField cuando el JTextField
     * obtiene el foco.
     */
    private void thisFocusGained(java.awt.event.FocusEvent evt)
    {
        strConCel=this.getText();
        this.selectAll();
    }
    
        
    /**
     * Obtiene un objeto listo para hacer el show
     * @param  strDesBusqueda String que aparecera en la ventana como descripcion de la busqueda
     * @return Librerias.ZafConsulta.ZafConsulta listo para hacer un show
     */
//    public Librerias.ZafConsulta.ZafConsulta getListaItm(String strDesBusqueda){
//       
//          Librerias.ZafConsulta.ZafConsulta  obj = 
//            new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
//             "Codigo,CodSistema,Nombre,Stock,Precio,Iva?","tx_codalt, co_itm, tx_nomItm, nd_stkAct, nd_preVta1, st_ivaVen",
//             "select tx_codalt,co_itm,tx_nomItm,nd_stkAct,nd_preVta1,st_ivaVen from tbm_inv " +
//             "where co_emp = " + objZafParSis.getCodigoEmpresa() , strDesBusqueda, 
//             objZafParSis.getStringConexion(), 
//             objZafParSis.getUsuarioBaseDatos(), 
//             objZafParSis.getClaveBaseDatos()
//             );            
////             "where co_emp = 1"  , strDesBusqueda, 
//          //"jdbc:postgresql://200.125.90.169:5432/Zafiro2005", "postgres","" );
//            obj.setTitle("Listado de Productos ");            
//            return obj;
//    }    
    
}