/*
 * ZafTblCelEdiTxtVco.java
 *
 * Created on 09 de abril de 2006, 20:44 PM
 * v0.3
 */
 
package Librerias.ZafTblUti.ZafTblCelEdiTxtVco2;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import javax.swing.event.EventListenerList;
import Librerias.ZafVenCon.ZafVenCon;

/**
 * Esta clase establece el editor a utilizar en la celda basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JTextField. Al presionar la tecla ENTER
 * o al perder el foco se consulta en la base de datos si lo ingresado existe o no. Si  existe se muestran
 * los demás campos asociados. Caso contrario, se presenta una ventana de consulta para que el usuario 
 * seleccione los datos que desea utilizar en la tabla.
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelEdiTxtVco" es la instancia de "ZafTblCelEdiTxtVco".
 * <PRE>
 *            objTblCelEdiTxtVco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
 *               public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   vcoItm.setCampoBusqueda(2);
 *                   vcoTipDoc.setCriterio1(11);
 *               }
 *               public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   if (objTblCelEdiTxtVco.isConsultaAceptada())
 *                       txtGlo.setText("Seleccionó: " + objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()));
 *                   else
 *                       txtGlo.setText("Canceló");
 *               }
 *               public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   if (tblDat.getSelectedRow()==2)
 *                       objTblCelEdiTxtCon.setCancelarEdicion(true);
 *                   else
 *                       objTblCelEdiTxtCon.setCancelarEdicion(false);
 *               }
 *               public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   //Escriba su código aquí...
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que necesita.
 * @author  Eddye Lino
 */
public class ZafTblCelEdiTxtVco2 extends javax.swing.JTextField implements javax.swing.table.TableCellEditor
{
    //Constantes:
//    public static final int INT_CAM_NUM=0;      /**Un valor para setCampoBusqueda: Indica "Campo numérico".*/
//    public static final int INT_CAM_STR=1;      /**Un valor para setCampoBusqueda: Indica "Campo cadena".*/
    private static final int INT_BEF_EDI=0;     /**Antes de editar: Indica "Before edit".*/
    private static final int INT_AFT_EDI=1;     /**Después de editar: Indica "After edit".*/
    private static final int INT_BEF_CON=2;     /**Antes de consultar: Indica "Before consultar".*/
    private static final int INT_AFT_CON=3;     /**Después de consultar: Indica "After consultar".*/
    //Variables:
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private ZafVenCon vcoGen;                   //Ventana de consulta "General".
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    private int intColVen[], intColTbl[];
    private boolean blnBusExe;                  //Determina si la búsqueda ya fue ejecutada.
    private boolean blnConAce;                  //Determina si se aceptó/canceló la consulta.
    private boolean blnCanEdi;                  //Determina si se debe cancelar la edición de la celda.
    protected EventListenerList objEveLisLis=new EventListenerList();
    
     private Librerias.ZafParSis.ZafParSis objZafParSis;
     
    /** 
     * Crea una nueva instancia de la clase ZafTblCelEdiBut.
     * @param tabla El objeto JTable a quien devolverá los datos la ventana de consulta.
     * @param ventana La ventana de consulta.
     * @param columnasVentana Las columnas de la ventana que se presentarán en el JTable.
     * <BR>Nota.- Las columnas de la ventana empiezan desde la posición 1.
     * @param columnasTabla Las columnas del JTable donde se presentarán los campos devueltos por la ventana de consulta.
     * <BR>Nota.- Las columnas de la tabla empiezan desde la posición 0.
     * <BR>Por ejemplo:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>columnasVentana</I></TD><TD><I>columnasTabla</I></TD><TD><I>Significado</I></TD></TR>
     *     <TR><TD>1</TD><TD>1</TD><TD>El campo 1 de la ventana de consulta se mostrará en la columna 1 de la tabla.</TD></TR>
     *     <TR><TD>2</TD><TD>2</TD><TD>El campo 2 de la ventana de consulta se mostrará en la columna 2 de la tabla.</TD></TR>
     *     <TR><TD>3</TD><TD>4</TD><TD>El campo 3 de la ventana de consulta se mostrará en la columna 4 de la tabla.</TD></TR>
     * </TABLE>
     * </CENTER>
     * <BR>Nota.- No es necesario que todas las columnas devueltas por la ventana de consulta sean presentadas en la tabla.
     */
    public ZafTblCelEdiTxtVco2(javax.swing.JTable tabla, ZafVenCon ventana, int[] columnasVentana, int[] columnasTabla, Librerias.ZafParSis.ZafParSis objZafParSis)
    {
        tblDat=tabla;
        vcoGen=ventana;
        intColVen=columnasVentana;
        intColTbl=columnasTabla;
        blnConAce=false;
        blnCanEdi=false;
        blnBusExe=true;
         this.objZafParSis = objZafParSis;
        
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
               // if (!blnBusExe)
               //     thisActionPerformed(null);
                   
                ActualizarPrecio();
             
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
        //Permitir de manera predeterminada la edición de la celda.
        blnCanEdi=false;
        //Generar evento "beforeEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_BEF_EDI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (blnCanEdi)
            return false;
        else
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
    
    private void ActualizarPrecio(){
        fireTableEditorListener(new ZafTableEvent(this), INT_BEF_CON);
         String CodItm = this.getText();
         String sql ="select nd_prevta1,st_ivaven  from tbm_inv where  tx_codalt ='"+CodItm+"' and co_emp="+ + objZafParSis.getCodigoEmpresa();
         try{
                     java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                     if (conUni!=null){
                          java.sql.Statement stmUni = conUni.createStatement();
                              java.sql.ResultSet rst = stmUni.executeQuery(sql);
                              if(rst.next()) {
                                  float floPreuni=0;
                                  if(rst.getString(1) != null ) floPreuni=rst.getFloat(1);
                               
                                   tblDat.setValueAt( floPreuni+"" , tblDat.getSelectedRow(), intColTbl[2]);
                                   String striva = rst.getString(2);
                                   tblDat.setValueAt( striva , tblDat.getSelectedRow(), intColTbl[4]);
                                   blnConAce=true; 
                              }       
                              stmUni.close();
                             rst.close();
                             conUni.close();
                             stmUni = null;
                             rst=null;
                             conUni = null;
                    }
             }
             catch(java.sql.SQLException Evt) { System.out.println(Evt);  }
           setValCelEdi(false);
        //Generar evento "afterEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_AFT_EDI);
        
        blnBusExe=true;
    }
    
    
    
    private void mostrarVenCon()
    {
        //Generar evento "beforeConsultar()".
        fireTableEditorListener(new ZafTableEvent(this), INT_BEF_CON);
        //Inicializar la variable que almacena si se aceptó/canceló la consulta.
        blnConAce=true;
        //Búsqueda directa por "Campo seleccionado".
        if (vcoGen.buscar(vcoGen.getTextoCampoBusqueda(), this.getText()))
        {
            int i, intFilSel;
            intFilSel=tblDat.getSelectedRow();
            for (i=0; i<intColVen.length; i++)
                tblDat.setValueAt(vcoGen.getValueAt(intColVen[i]), intFilSel, intColTbl[i]);
        }
        else 
        {  
            vcoGen.cargarDatos();
            vcoGen.show();
            if (vcoGen.getSelectedButton()==vcoGen.INT_BUT_ACE)
            {
                int i, intFilSel;
                intFilSel=tblDat.getSelectedRow();
                for (i=0; i<intColVen.length; i++)
                    tblDat.setValueAt(vcoGen.getValueAt(intColVen[i]), intFilSel, intColTbl[i]);
            }
            else
            {
                setValCelEdi(false);
                blnConAce=false;
            }
        }
        //Generar evento "afterConsultar()".
        fireTableEditorListener(new ZafTableEvent(this), INT_AFT_CON);
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
    private void setValCelEdi(boolean hayCambios)
    {
        javax.swing.event.ChangeEvent evt=new javax.swing.event.ChangeEvent(this);
        javax.swing.event.CellEditorListener objCelEdiLis;
        int i;
        for (i=0;i<objLinLis.size();i++)
        {
            objCelEdiLis=(javax.swing.event.CellEditorListener)objLinLis.get(i);
            if (hayCambios)
                objCelEdiLis.editingStopped(evt);
            else
                objCelEdiLis.editingCanceled(evt);
        }
    }

    /**
     * Esta función valida los datos que se ingresan en la celda. Si los datos son válidos
     * se cargan los datos adicionales. Caso contrario, se muestra la ventana de consulta.
     */
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!this.getText().equals(strConCel))
        {
            //Si se borra el contenido de la celda hay que borrar el contenido de las celdas asociadas.
            if (this.getText()==null || this.getText().equals(""))
            {
                int i, intFilSel;
                intFilSel=tblDat.getSelectedRow();
                for (i=0; i<intColVen.length; i++)
                    tblDat.setValueAt(null, intFilSel, intColTbl[i]);
            }
            else
            {
                mostrarVenCon();
            }
        }
        setValCelEdi(false);
        //Generar evento "afterEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_AFT_EDI);
        blnBusExe=true;
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
     * Esta función obtiene el arreglo que contiene las columnas de la ventana
     * de consulta que se utilizarán en la tabla.
     * @return Las columnas de la ventana de consulta que se utilizarán en la tabla.
     * <BR>Nota.- Las columnas de la ventana empiezan desde la posición 1.
     */
    public int[] getColumnasVentana()
    {
        return intColVen;
    }
    
    /**
     * Esta función establece el arreglo que contiene las columnas de la ventana
     * de consulta que se utilizarán en la tabla.
     * @param columnas Las columnas de la ventana de consulta que se utilizarán en la tabla.
     * <BR>Nota.- Las columnas de la ventana empiezan desde la posición 1.
     */
    public void setColumnasVentana(int columnas[])
    {
        intColVen=columnas;
    }
    
    /**
     * Esta función obtiene el arreglo que contiene las columnas de la tabla
     * donde se presentarán los campos devueltos por la ventana de consulta.
     * @return Las columnas de la tabla donde se presentarán los campos devueltos
     * por la ventana de consulta.
     * <BR>Nota.- Las columnas de la tabla empiezan desde la posición 0.
     */
    public int[] getColumnasTabla()
    {
        return intColTbl;
    }
    
    /**
     * Esta función establece el arreglo que contiene las columnas de la tabla
     * donde se presentarán los campos devueltos por la ventana de consulta.
     * @param columnas Las columnas de la tabla donde se presentarán los campos
     * devueltos por la ventana de consulta.
     * <BR>Nota.- Las columnas de la tabla empiezan desde la posición 0.
     */
    public void setColumnasTabla(int columnas[])
    {
        intColTbl=columnas;
    }
    
    /**
     * Esta función adiciona el listener que controlará los eventos de edición.
     * @param listener El objeto que implementa los métodos de la interface "ZafTableListener".
     */
    public void addTableEditorListener(ZafTableListener listener)
    {
        objEveLisLis.add(ZafTableListener.class, listener);
    }

    /**
     * Esta función adiciona el listener que controlará los eventos de edición.
     * @param listener El objeto que implementa los métodos de la interface "ZafTableListener".
     */
    public void removeTableEditorListener(ZafTableListener listener)
    {
        objEveLisLis.remove(ZafTableListener.class, listener);
    }
    
    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafTableEvent".
     * @param metodo El método que será invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Método</I></TD></TR>
     *     <TR><TD>INT_BEF_EDI</TD><TD>Invoca al métod "beforeEdit" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_EDI</TD><TD>Invoca al métod "afterEdit" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_CON</TD><TD>Invoca al métod "beforeConsultar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_CON</TD><TD>Invoca al métod "afterConsultar" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireTableEditorListener(ZafTableEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafTableListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_EDI:
                        ((ZafTableListener)obj[i+1]).beforeEdit(evt);
                        break;
                    case INT_AFT_EDI:
                        ((ZafTableListener)obj[i+1]).afterEdit(evt);
                        break;
                    case INT_BEF_CON:
                        ((ZafTableListener)obj[i+1]).beforeConsultar(evt);
                        break;
                    case INT_AFT_CON:
                        ((ZafTableListener)obj[i+1]).afterConsultar(evt);
                        break;
                }
            }
        }
    }
 
    /**
     * Esta función determina si la consulta ha sido aceptada o cancelada.
     * @return true: Si se aceptó la consulta.
     * <BR>false: En el caso contrario.
     */
    public boolean isConsultaAceptada()
    {
        return blnConAce;
    }
    
    /**
     * Esta función establece si se debe cancelar la edición de la celda.
     * @param edicion Puede tomar los siguientes valores:
     * <UL>
     * <LI>true: Cancela la edición de la celda.
     * <LI>false: Permite la edición de la celda.
     * </UL>
     */
    public void setCancelarEdicion(boolean edicion)
    {
        blnCanEdi=edicion;
    }
    
}