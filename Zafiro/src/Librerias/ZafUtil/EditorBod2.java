/*
 * editorCantidad.java
 *
 * Created on 18 de julio de 2005, 15:53
 */

package Librerias.ZafUtil;

/**
 * Clase para manejar el editor de la bodega en una factura o compra
 * @author Iditrix
 * @version 2.0
 */ 
public abstract class EditorBod2 extends javax.swing.JTextField implements javax.swing.table.TableCellEditor, Librerias.ZafUtil.ActionToDo {
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    private javax.swing.table.DefaultTableModel objTblMod;
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private boolean blnIsInActionPerfomed=false;
    private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit
    private int INT_TBL_CO_EDT;//Columna que tendra el codigo del sistema del item
    private int INT_TBL_CODITM;//Columna que tendra el codigo del sistema del item
    private int INT_TBL_STKBOD; // Columan de la tabla que tendra el stock actual de la bodega
    private Librerias.ZafUtil.ZafMonitorTbl objMon;    
    

    /** Crea una nueva instancia de la clase EditorCodItm. */
    public EditorBod2(javax.swing.JTable tabla, int intColEditar, int intColNext, int intColCodItm, int intColStkBod, Librerias.ZafParSis.ZafParSis objZafParSis, Librerias.ZafUtil.ZafMonitorTbl objMon)
    {
        intColSigue   = intColNext;
        INT_TBL_CODITM= intColCodItm;
        INT_TBL_CO_EDT= intColEditar;
        INT_TBL_STKBOD= intColStkBod; // Columan de la tabla que tendra el stock actual de la bodega
        this.objZafParSis = objZafParSis;          
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
        try{
            //Convertir el valor a int.
            this.setText("" + Integer.parseInt(this.getText()));            
            Librerias.ZafConsulta.ZafConsulta objConItm = getListaItm(this.getText());
            objConItm.setTitle("Listado de Productos");
            preActionToDo();         

            if (objConItm.buscar("invbod.co_bod = " + this.getText() ))
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
        }
        catch (NumberFormatException e)
        {
            setValCelEdi(false);
        } 
        
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
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(1),tblDat.getSelectedRow(),INT_TBL_CO_EDT);
                tblDat.setValueAt((objSrc==null)?null: new Double(objSrc.GetCamSel(3))  ,tblDat.getSelectedRow(), INT_TBL_STKBOD);
                
                if(objSrc!=null)
                    posActionToDo();            
            }    

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
                mostrarVenCon();
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
        System.out.println("Ji ji ji "); 
        strConCel=this.getText();
        this.selectAll();
    }
            
        
        

    private int getBodPrede(){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia            
        int intBodPre = 0;
        String sSQL;
        try{//odbc,usuario,password
            java.sql.Connection conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conExi!=null){ 
                stmInv=conExi.createStatement();

                sSQL=  " select co_bod   " +
                       " from tbr_bodloc " +
                       " where           " +
                       " co_emp =        " + objZafParSis.getCodigoEmpresa() + " and "+  
                       " co_loc =        " + objZafParSis.getCodigoLocal()   + " and " +
                       " st_reg ='P'";  

                rstInv=stmInv.executeQuery(sSQL);

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
        return intBodPre;
    }

    /**
     * Obtiene un objeto listo para hacer el show
     * @param  strDesBusqueda String que aparecera en la ventana como descripcion de la busqueda
     * @return Librerias.ZafConsulta.ZafConsulta listo para hacer un show
     */
    public Librerias.ZafConsulta.ZafConsulta getListaItm(String strDesBusqueda){
            Librerias.ZafConsulta.ZafConsulta  obj = 
            new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
             "Codigo,Nombre,Stock Actual","tbm_bod.co_bod, tbm_bod.tx_nom , invbod.nd_stkact",
             "select distinct bod.co_bod, bod.tx_nom , invbod.nd_stkact from tbr_bodLoc as bodloc, tbm_bod as bod , tbm_invbod as invbod"   +
             " where " +
             "   bod.co_emp =  " + objZafParSis.getCodigoEmpresa() +
             "   and bodloc.co_loc = " + objZafParSis.getCodigoLocal() + 
             "   and bodloc.co_emp = bod.co_emp " +
             "   and bodloc.co_bod = bod.co_bod " +
             "   and invbod.co_bod = bodloc.co_bod " +
             "   and invbod.co_emp =  bod.co_emp "   +
                 "   and invbod.co_itm = " + tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM)
             , strDesBusqueda, 
             objZafParSis.getStringConexion(), 
             objZafParSis.getUsuarioBaseDatos(), 
             objZafParSis.getClaveBaseDatos()
             );
            obj.setTitle("Listado de Bodegas ");            
            return obj;
    }
  }    
