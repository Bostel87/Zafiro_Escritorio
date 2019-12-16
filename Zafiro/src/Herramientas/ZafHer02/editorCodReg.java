/*
 * ZafTblCelEdiTxt.java
 *
 * Created on 25 de julio de 2005, 03:02 PM
 */

package Herramientas.ZafHer02;

/**
 * Esta clase establece el editor a utilizar en la celda de codigo de item en ventas o compras,
 * basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JTextField.
 * @author Iditrix
 * @version 2.0
 *
 */
public abstract class editorCodReg extends javax.swing.JTextField implements javax.swing.table.TableCellEditor, Librerias.ZafUtil.ActionToDo 
{
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    private javax.swing.table.DefaultTableModel objTblMod;
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private boolean blnIsInActionPerfomed=false;
    private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit
    private int INT_TBL_CODREG;//Columna que tendra el codigo del sistema del item
    private int INT_TBL_DESCOR;//Columna que tendra el codigo del sistema del item
    private int INT_TBL_DESLAR; // Columan de la tabla que tendra la descripcion del Item actual
    private int INT_TBL_OBS; // COluman de la tabla que tendra el valor de observacion
    

    /** Crea una nueva instancia de la clase EditorCodItm. */
    public editorCodReg(javax.swing.JTable tabla, int intColCodReg, int intColNext, int intColDesCor, int intColDesLar, int intColObs, Librerias.ZafParSis.ZafParSis objZafParSis)
    {
        intColSigue   = intColNext;
        INT_TBL_CODREG= intColCodReg;
        INT_TBL_DESCOR= intColDesCor;
        INT_TBL_DESLAR= intColDesLar;
        INT_TBL_OBS   = intColObs ;
        this.objZafParSis = objZafParSis;          
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
        Librerias.ZafConsulta.ZafConsulta objConItm = getListaItm(this.getText());
        preActionToDo();         
        
        if (objConItm.buscar("co_reg = " + this.getText() ))
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
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(1),intRow,INT_TBL_CODREG);
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(2),intRow,INT_TBL_DESCOR);
              //Poniendo La descripcion de producto, que el usuario eligio en la consulta, en la tabla 
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(3),intRow,INT_TBL_DESLAR);
                tblDat.setValueAt((objSrc==null)?null:objSrc.GetCamSel(3),intRow,INT_TBL_OBS);

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
             "Codigo,Nombre,Descripcion,Observacion,Menu","co_reg, tx_descor, tx_deslar, tx_obs1, co_mnu ",
             "select co_reg, tx_descor, tx_deslar, tx_obs1, co_mnu from   tbm_regneg " +
             "where co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
             "      co_loc = " + objZafParSis.getCodigoLocal()   , strDesBusqueda, 
             objZafParSis.getStringConexion(), 
             objZafParSis.getUsuarioBaseDatos(), 
             objZafParSis.getClaveBaseDatos()
             );            
//             "where co_emp = 1"  , strDesBusqueda, 
          //"jdbc:postgresql://200.125.90.169:5432/Zafiro2005", "postgres","" );
            obj.setTitle("Listado de Reglas de Negocio ");            
            return obj;
    }    
    
}