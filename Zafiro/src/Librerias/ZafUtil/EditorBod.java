/*
 * editorCantidad.java
 *
 * Created on 18 de julio de 2005, 15:53
 */

package Librerias.ZafUtil;

/**
 * Clase para manejar el editor de texto
 * @author Iditrix
 * @version 1.5
 */ 
  public class EditorBod extends javax.swing.DefaultCellEditor {
        
        final javax.swing.JTextField txtInt;
        private int intColEdit;  //Columna a editar   
        private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit
        private int intColCodItem;//Columna que tendra el objeto objRepInv
        private int INT_TBL_STKBOD; // Columan de la tabla que tendra el stock actual de la bodega
        private javax.swing.JTable tblMaestro ;// Tabla en donde se trabajara
        private Librerias.ZafUtil.ZafUtil objUtil;
        private Librerias.ZafParSis.ZafParSis objZafParSis;
        private javax.swing.JInternalFrame  frmPrincipal = null;
        
        private String strVal = "0";//Contiene el valor actual de la fila
        /**
         *  Construye un nuevo objeto que maneja las cantidades
         *  y realiza todas las validaciones segun haya ocurra algun evento o no
         *          @param intColEditar - Columna a editar  (columna ke se edita)
         *          @param intColNext - Columna que tendra el foco despues de haber editado correctamente la intColEdit
         *          @param intColCodItm - Columna donde esta el codigo de item 
         *          @param intColStkBod - Columna donde esta el codigo de item 
         *          @param tblMaster Tabla en la que se trabajara
         *          @param objZafParSis Objeto Parametros del Sistema         
         */
        public EditorBod(int intColEditar, int intColNext, int intColCodItm, int intColStkBod, javax.swing.JTable tblMaster, Librerias.ZafParSis.ZafParSis objZafParSis) {
            super(new javax.swing.JCheckBox());
            intColEdit   = intColEditar;
            intColSigue  = intColNext;
            intColCodItem= intColCodItm;
            INT_TBL_STKBOD= intColStkBod;
            tblMaestro   = tblMaster;
            txtInt       = new javax.swing.JTextField();
            objUtil      = new Librerias.ZafUtil.ZafUtil();
            this.objZafParSis = objZafParSis;            
            /**
             * Listener para que cuando el usuario de click en la celda, se seleccione 
             * todo el contenido de la misma.
             */
            txtInt.addFocusListener(new java.awt.event.FocusAdapter() {
//                private int RowEditing = 0 ;
                 public void focusGained(java.awt.event.FocusEvent evt) {
//                    RowEditing = tblMaestro.getEditingRow();
                  }
                public void focusLost(java.awt.event.FocusEvent evt) {
//                    if(RowEditing!=-1 && intColEdit!=-1)
//                        tblMaestro.setValueAt(getValor(strVal), RowEditing  ,intColEdit);            
                  }
             });            
             
            txtInt.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Librerias.ZafConsulta.ZafConsulta objConBod = getListaItm("");
                    String str = txtInt.getText();
                    
                     if(objUtil.isNumero(str)){
                        if(!objConBod.buscar("invbod.co_bod = " + str)){
                            objConBod.show();

                            if(objConBod.acepto() &&  objUtil.isNumero(str)){
                                txtInt.setText(objConBod.GetCamSel(1));

                                if( objConBod.GetCamSel(3).equals("null") || objConBod.GetCamSel(3).equals(""))
                                   tblMaestro.setValueAt(new Double("0"),tblMaestro.getSelectedRow(), INT_TBL_STKBOD);
                                else
                                   tblMaestro.setValueAt(new Double(objConBod.GetCamSel(3)),tblMaestro.getSelectedRow(),INT_TBL_STKBOD);                        

                                fireEditingStopped();  
                                tblMaestro.requestFocus();
                                tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);
                                tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                            }
                           
                        }else{
                                strVal = str;
                                if( objConBod.GetCamSel(3).equals("null") || objConBod.GetCamSel(3).equals(""))
                                   tblMaestro.setValueAt(new Double("0"),tblMaestro.getSelectedRow(), INT_TBL_STKBOD);
                                else
                                   tblMaestro.setValueAt(new Double(objConBod.GetCamSel(3)),tblMaestro.getSelectedRow(),INT_TBL_STKBOD);                        

                                fireEditingStopped();  
                                tblMaestro.requestFocus();
                                tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);
                                tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                        }
                         
                     }
               }
            });
            
            this.editorComponent = txtInt;
        }
        
        public EditorBod(int intColEditar, int intColNext, int intColCodItm, int intColStkBod, javax.swing.JTable tblMaster, Librerias.ZafParSis.ZafParSis objZafParSis, javax.swing.JInternalFrame  frmPrincipal) {
            this(intColEditar, intColNext, intColCodItm, intColStkBod, tblMaster, objZafParSis);
            this.frmPrincipal = frmPrincipal;
        }
        
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
        public void cancelCellEditing(){
            super.cancelCellEditing();
            tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColEdit, false, false);                        
            
        }
        public Object getCellEditorValue() {
            return getValor((txtInt.getText().trim().equals(""))?"0":txtInt.getText());
        }
        /**
         * Obtiene un Integer a partir de un String
         * @param strVal Valor en String
         * @return Integer con el valor redondeado a intDecimales o el valor en cero si es que lo que digito no es numerico
         */
        private Integer getValor(String strVal){
            Integer intNum = new Integer(0);
            if(objUtil.isNumero(strVal))
                intNum  = new Integer(Integer.parseInt(strVal));
            return intNum ;            
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
        
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            strVal = (value == null)?getBodPrede()+"":value.toString();
            ((javax.swing.JTextField) editorComponent).setText(strVal);
            ((javax.swing.JTextField) editorComponent).setFont( new java.awt.Font("SansSerif", 0, 11));
            ((javax.swing.JTextField) editorComponent).selectAll();

            return editorComponent;
        }
        /**
         * Obtiene un objeto listo para hacer el show
         * @param  strDesBusqueda String que aparecera en la ventana como descripcion de la busqueda
         * @return Librerias.ZafConsulta.ZafConsulta listo para hacer un show
         */
        public Librerias.ZafConsulta.ZafConsulta getListaItm(String strDesBusqueda){
                Librerias.ZafConsulta.ZafConsulta  obj = 
                new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(frmPrincipal),
                 "Codigo,Nombre,Stock Actual","tbm_bod.co_bod, tbm_bod.tx_nom , invbod.nd_stkact",
                 "select distinct bod.co_bod, bod.tx_nom , invbod.nd_stkact from tbr_bodLoc as bodloc, tbm_bod as bod , tbm_invbod as invbod"   +
                 " where " +
                 "   bod.co_emp =  " + objZafParSis.getCodigoEmpresa() +
                 "   and bodloc.co_loc = " + objZafParSis.getCodigoLocal() + 
	         "   and bodloc.co_emp = bod.co_emp " +
	         "   and bodloc.co_bod = bod.co_bod " +
                 "   and invbod.co_bod = bodloc.co_bod " +
	         "   and invbod.co_emp =  bod.co_emp "   +
                     "   and invbod.co_itm = " + tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColCodItem)
                 , strDesBusqueda, 
                 objZafParSis.getStringConexion(), 
                 objZafParSis.getUsuarioBaseDatos(), 
                 objZafParSis.getClaveBaseDatos()
                 );
                obj.setTitle("Listado de Bodegas ");            
                return obj;
        }
    }    
