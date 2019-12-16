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
 * @author jayapata
 * @version 2.2
 *
 */
public abstract class EditorCodItm4 extends javax.swing.JTextField implements javax.swing.table.TableCellEditor, Librerias.ZafUtil.ActionToDo 
{
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    private javax.swing.table.DefaultTableModel objTblMod;
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    
    private boolean blnIsInActionPerfomed=false;
    private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit
    private int INT_TBL_COD_PRO;//Columna que tendra el codigo proveedor
    private int INT_TBL_NOM_PRO;//Columna que tendra el nombre del proveedor
    
    private Librerias.ZafUtil.ZafMonitorTbl objMon;    
    ZafVenCon objVenCon; //*****************  
    
      javax.swing.JTextField valor = new javax.swing.JTextField();
      
       private boolean configurarVenConProveedor()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            arlCam.add("a1.tx_ide");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Prv.");
            arlAli.add("Dirección");
            arlAli.add("RUC/CI");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("190");
            arlAncCol.add("220");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="select a1.co_cli,a1.tx_nom,a1.tx_dir,a1.tx_ide from tbm_cli as a1 where  a1.st_prv = 'S'";
             strSQL+=" and a1.co_emp ="+ objZafParSis.getCodigoEmpresa() +" order by a1.tx_nom";
            
            objVenCon=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e);
            //objutil.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
      
      
    /** Crea una nueva instancia de la clase EditorCodItm. */
   // public EditorCodItm4(javax.swing.JTable tabla, int intColCodPro, int intColNomPro,Librerias.ZafParSis.ZafParSis objZafParSis, Librerias.ZafUtil.ZafMonitorTbl objMon, Librerias.ZafVenCon.ZafVenCon objVenCon)
    public EditorCodItm4(javax.swing.JTable tabla, int intColCodPro, int intColNomPro,Librerias.ZafParSis.ZafParSis objZafParSis, Librerias.ZafUtil.ZafMonitorTbl objMon)
    {   
          
           
        INT_TBL_COD_PRO = intColCodPro;
        INT_TBL_NOM_PRO = intColNomPro;
        
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
                 tblDat.setValueAt((objVenCon==null)?null:objVenCon.getValueAt(1),tblDat.getSelectedRow(),INT_TBL_COD_PRO);
                 tblDat.setValueAt((objVenCon==null)?null:objVenCon.getValueAt(2),tblDat.getSelectedRow(),INT_TBL_NOM_PRO);
      }
 
    
      
      
    private void mostrarVenCon()
    {
       configurarVenConProveedor();   
        if (objVenCon.buscar("a1.tx_nom", this.getText()))
        {
           LLenarDatos(objVenCon);
        }
        else {
            objVenCon.setCampoBusqueda(1);
            objVenCon.cargarDatos();
            objVenCon.show();
            if (objVenCon.getSelectedButton()==objVenCon.INT_BUT_ACE)
            {
                 LLenarDatos(objVenCon);
            }
           
        }
          objVenCon.dispose();
         objVenCon=null;
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
    /*
     * Obtiene el Iva que se debe cobrar en la empresa actual
     */
   
    
    
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {   blnIsInActionPerfomed=true;
        //Validar el contenido de la celda sólo si ha cambiado.
      
              
        if (!this.getText().equals(strConCel))
        {   
           //Si se borra el contenido de la celda hay que borrar el contenido de las celdas asociadas.
            if (this.getText()==null || this.getText().equals(""))
            {
                //setDatos(null);
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