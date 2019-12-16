/*
 * ZafTblCelEdiTxt.java
 *
 * Created on 25 de julio de 2005, 03:02 PM
 */

package Librerias.ZafUtil;
import java.sql.*;  //************
import java.util.Map;  /************
/**
 * Esta clase establece el editor a utilizar en la celda de codigo de item en ventas o compras,
 * basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JTextField.
 * @author Iditrix
 * @version 2.1
 *
 */
public abstract class EditorCodItm2 extends javax.swing.JTextField implements javax.swing.table.TableCellEditor, Librerias.ZafUtil.ActionToDo 
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
    private Librerias.ZafUtil.ZafMonitorTbl objMon;    
    

    /** Crea una nueva instancia de la clase EditorCodItm. */
    public EditorCodItm2(javax.swing.JTable tabla, int intColItmAlt, int intColNext, int intColCodItm, int intColDesItm, int intColUnidad, int intColPreUni, int intColBlnIva, Librerias.ZafParSis.ZafParSis objZafParSis, Librerias.ZafUtil.ZafMonitorTbl objMon)
    {   
        
      
        
        intColSigue   = intColNext;
        INT_TBL_CODITM= intColCodItm;
        INT_TBL_DESITM= intColDesItm;
        INT_TBL_PREUNI= intColPreUni; // Columna de la tabla que tendra el Precio Unitario
        INT_TBL_UNIDAD= intColUnidad;
        INT_TBL_BLNIVA= intColBlnIva; // Columna de la tabla que tendra si el producto lleva Iva o no        
        INT_TBL_CO_ALT= intColItmAlt;
        this.objZafParSis = objZafParSis;          
        this.objMon  =  objMon;
        tblDat=tabla;
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
       
        
        
        
        
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("1"+  evt.getID() );
                thisActionPerformed(evt);
               
            }
        });
        //Seleccionar el texto cuando se obtenga el foco.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                System.out.println("2");
                thisFocusGained(evt);
            }
        });
        //Agregar el "FocusListener" que avisará al JTable que se ha cancelado la edición de la celda.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                System.out.println("3");
                if(!blnIsInActionPerfomed)
                    thisActionPerformed(null);
            }
        });
        
   }
    
    /**
     * Esta función adiciona el listener que notificará cuando el editor se detenga
     * o cancele la edición de la celda.
     */
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
    private void mostrarVenCon()
    {
       //Librerias.ZafConsulta.ZafConsulta objConItm = getListaItm(this.getText());
        
        
        /* ESTE CODIGO REENPLAZA AL ANTERIOR, ENVEZ DE LLAMAR A ZafConsulta LLAMA  A ZafConItm
         */
         
        
         String strDesBusqueda = "";
         Librerias.ZafConsulta.ZafConItm obj = new Librerias.ZafConsulta.ZafConItm(javax.swing.JOptionPane.getFrameForComponent(this) ,strDesBusqueda, objZafParSis);
         obj.setTitle("Listado Productos ");
        
         obj.buscar2(this.getText());
         obj.show();
         
                if(obj.acepto()){   
                      //Poniendo el codigo de producto, que el usuario eligio en la consulta, en la tabla 
                           tblDat.setValueAt((obj==null)?null:obj.GetCamSel(1),tblDat.getSelectedRow(),INT_TBL_CO_ALT);
                           tblDat.setValueAt((obj==null)?null:obj.GetCamSel(2),tblDat.getSelectedRow(),INT_TBL_CODITM);
             
                        //Poniendo La descripcion de producto, que el usuario eligio en la consulta, en la tabla 
                         tblDat.setValueAt((obj==null)?null:obj.GetCamSel(3),tblDat.getSelectedRow(),INT_TBL_DESITM);
                         tblDat.setValueAt((obj==null)?null:getUnidad(obj.GetCamSel(2)),tblDat.getSelectedRow(),INT_TBL_UNIDAD);

                           
                           Boolean blnIva = new Boolean(false);

                        if(obj.GetCamSel(7).equals("S"))
                             blnIva = new Boolean(true);
                             
                             //tblDat.setValueAt(new Double(obj.GetCamSel(6)),tblCotDet.getSelectedRow(),INT_TBL_PREUNI);
                             
                              //tblCotDet.setValueAt(getBodPrede()+"",tblCotDet.getSelectedRow(),INT_TBL_CODBOD);
                     
                           
                           
                             tblDat.setValueAt((obj==null)?null:new Double((obj.GetCamSel(6).equals(""))?"0":obj.GetCamSel(6)),tblDat.getSelectedRow(),INT_TBL_PREUNI);
                             tblDat.setValueAt((obj==null)?null:blnIva,tblDat.getSelectedRow(),INT_TBL_BLNIVA);
                    
                
                }
           
         obj = null;
         
         /*
        objConItm.setTitle("Listado de Productos ");
        preActionToDo();         
        if (objConItm.buscar("LOWER(tx_codalt) = LOWER('" + this.getText() + "')"))
        {          
            setDatos(objConItm);
        }
        else
        {          
            objConItm.show();
            if (objConItm.acepto())
            {
               setDatos(objConItm);
            }
            else
            {
                setValCelEdi(false);
            }
        }
        objConItm=null;
        */
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
    

    /**
     * Asigna los datos a las celdas segun el 
     * codigo del item que se selecciono
     */
    private void setDatos(Librerias.ZafConsulta.ZafConsulta objSrc){
            
            if((objSrc!=null)?!objSrc.GetCamSel(1).equals(strConCel):true){
                int intRow = tblDat.getSelectedRow();
                if(objMon!=null){
                    objMon.eliminar(intRow);
                    objMon.insertar(intRow);
                }
            
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(1),tblDat.getSelectedRow(),INT_TBL_CO_ALT);
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(2),tblDat.getSelectedRow(),INT_TBL_CODITM);
              //Poniendo La descripcion de producto, que el usuario eligio en la consulta, en la tabla 
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(3),tblDat.getSelectedRow(),INT_TBL_DESITM);

                tblDat.setValueAt((objSrc==null)?null:getUnidad(objSrc.GetCamSel(2)),tblDat.getSelectedRow(),INT_TBL_UNIDAD);

                Boolean blnIva = new Boolean(false);
                if(objSrc!=null)
                    if(objSrc.GetCamSel(6).equals("S"))
                        blnIva = new Boolean(true);

                tblDat.setValueAt((objSrc==null)?null:new Double((objSrc.GetCamSel(5).equals(""))?"0":objSrc.GetCamSel(5)),tblDat.getSelectedRow(),INT_TBL_PREUNI);
                tblDat.setValueAt((objSrc==null)?null:blnIva,tblDat.getSelectedRow(),INT_TBL_BLNIVA);

                if(objSrc!=null)
                    posActionToDo();            
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
     * Esta función se ejecuta cuando se hace el "ActionPerformed" del JTextField.
     */
    
   
    
    
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {   blnIsInActionPerfomed=true;
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!this.getText().equals(strConCel))
        {   
           //Si se borra el contenido de la celda hay que borrar el contenido de las celdas asociadas.
            if (this.getText()==null || this.getText().equals(""))
            {
                setDatos(null);
            }
            else
            {
               ///////////////////////////////////////// 
               
                String ValBus = this.getText();
                ValBus = ValBus.toLowerCase();
                String Ssql ="SELECT count(a7.tx_codAlt) as fila  FROM ( SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen FROM ( SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct FROM tbm_equInv AS b1 INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer) WHERE b3.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND b3.co_loc="+ objZafParSis.getCodigoLocal() +" GROUP BY b1.co_itmMae ) AS a1, ( SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen FROM tbm_inv AS c1 INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm) WHERE c2.co_emp="+ objZafParSis.getCodigoEmpresa() +" ) AS a2 WHERE a1.co_itmMae=a2.co_itmMae ) AS a7 WHERE LOWER(a7.tx_codAlt)  = '"+ ValBus +"'";
                
                String  Ssql2 ="SELECT a7.tx_codAlt, a7.co_itm, a7.tx_nomItm, a7.nd_stkAct, a7.nd_stkTot, a7.nd_preVta1, a7.st_ivaVen FROM ( SELECT a2.tx_codAlt, a2.co_itm, a2.tx_nomItm, a1.nd_stkAct, a1.nd_stkAct AS nd_stkTot, a2.nd_preVta1, a2.st_ivaVen FROM ( SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct FROM tbm_equInv AS b1 INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer) WHERE b3.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND b3.co_loc="+ objZafParSis.getCodigoLocal() +" GROUP BY b1.co_itmMae ) AS a1, ( SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c1.nd_preVta1, c1.st_ivaVen FROM tbm_inv AS c1 INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm) WHERE c2.co_emp="+ objZafParSis.getCodigoEmpresa() +" ) AS a2 WHERE a1.co_itmMae=a2.co_itmMae ) AS a7 WHERE LOWER(a7.tx_codAlt)  = '"+ ValBus +"'";
                System.out.println("AQUI.."+ objZafParSis.getClaveBaseDatos() );
                
                 try{
                     java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                     if (conUni!=null){
                              java.sql.Statement stmUni = conUni.createStatement();
                              java.sql.ResultSet rst = stmUni.executeQuery(Ssql);
                              rst.next();
                              int VarCan= Integer.parseInt(rst.getString(1));
                              if(VarCan == 0 )
                                      mostrarVenCon();
                                else {  
                                      rst = stmUni.executeQuery(Ssql2);
                                      rst.next();
                                      tblDat.setValueAt(rst.getString(1),tblDat.getSelectedRow(),INT_TBL_CO_ALT);
                                      tblDat.setValueAt(rst.getString(2),tblDat.getSelectedRow(),INT_TBL_CODITM);
                                      tblDat.setValueAt(rst.getString(3),tblDat.getSelectedRow(),INT_TBL_DESITM);
                                      tblDat.setValueAt(new Double(rst.getString(6)),tblDat.getSelectedRow(),INT_TBL_PREUNI);
                                      Boolean blnIva = new Boolean(false);
                                     if(rst.getString(7).equals("S"))
                                         blnIva = new Boolean(true);
                                      tblDat.setValueAt(blnIva,tblDat.getSelectedRow(),INT_TBL_BLNIVA);
                                      tblDat.setValueAt(getUnidad(rst.getString(2)),tblDat.getSelectedRow(),INT_TBL_UNIDAD);
                           
                                    } 
                                    conUni.close();
                                    conUni = null;
                          }
                 }
                 catch(java.sql.SQLException Evt)
                { System.out.println(Evt);  }
                
                //////////////////////////////////////////
                
            }
            
        }
        setValCelEdi(false);        
        
        blnIsInActionPerfomed=false;
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
    public Librerias.ZafConsulta.ZafConsulta getListaItm(String strDesBusqueda){
       
          Librerias.ZafConsulta.ZafConsulta  obj = 
            new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
             "Codigo,CodSistema,Nombre,Stock,Precio,Iva?","tx_codalt, co_itm, tx_nomItm, nd_stkAct, nd_preVta1, st_ivaVen",
             "select tx_codalt,co_itm,tx_nomItm,nd_stkAct,nd_preVta1,st_ivaVen from tbm_inv " +
             "where co_emp = " + objZafParSis.getCodigoEmpresa() , strDesBusqueda, 
             objZafParSis.getStringConexion(), 
             objZafParSis.getUsuarioBaseDatos(), 
             objZafParSis.getClaveBaseDatos()
             );            
//             "where co_emp = 1"  , strDesBusqueda, 
          //"jdbc:postgresql://200.125.90.169:5432/Zafiro2005", "postgres","" );
            obj.setTitle("Listado de Productos ");            
            return obj;
    }    
    
}