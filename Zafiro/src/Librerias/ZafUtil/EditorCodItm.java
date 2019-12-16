/*
 * editorCantidad.java
 *
 * Created on 18 de julio de 2005, 15:53
 */

package Librerias.ZafUtil;

/**
 * Clase para manejar el editor de texto
 * @author Iditrix
 * @version 1.2
 */ 
  public abstract class EditorCodItm extends javax.swing.DefaultCellEditor implements Librerias.ZafUtil.ActionToDo{
        
        final javax.swing.JTextField txtString;
        private int intColEdit;  //Columna a editar   
        private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit
        private int INT_TBL_CODITM;//Columna que tendra el codigo del sistema del item
        private int INT_TBL_DESITM; // Columan de la tabla que tendra la descripcion del Item actual
        private int INT_TBL_UNIDAD; // Columna de la tabla que tendra la unidad del producto
        private int INT_TBL_PREUNI; // Columna de la tabla que tendra el Precio Unitario
        private int INT_TBL_BLNIVA; // Columna de la tabla que tendra si el producto lleva Iva o no
        private javax.swing.JTable tblMaestro ;// Tabla en donde se trabajara
        private Librerias.ZafUtil.ZafUtil objUtil;
        private Librerias.ZafParSis.ZafParSis objZafParSis;
        private javax.swing.JInternalFrame  frmPrincipal = null;
        
        
        private String strVal = "";//Contiene el valor actual de la fila
        /**
         *     Construye un nuevo objeto que maneja las cantidades
         *     y realiza todas las validaciones segun haya ocurra algun evento o no
         * @param intColEditar - Columna a editar  (columna ke se edita)
         * @param intColNext - Columna que tendra el foco despues de haber editado correctamente la intColEdit
         * @param intColCodItm - Columna donde esta el codigo de item 
         * @param intColDesItm - Columna donde esta el codigo de item 
         * @param intColPreUni - Columna donde esta el precio untiario
         * @param intColBlnIva - Columna donde si el producto tiene iva o no 
         * @param tblMaster Tabla en la que se trabajara
         * @param objZafParSis Objeto Parametros del Sistema         
         */
            public EditorCodItm(int intColEditar, int intColNext, int intColCodItm, int intColDesItm, int intColUnidad, int intColPreUni, int intColBlnIva, javax.swing.JTable tblMaster, Librerias.ZafParSis.ZafParSis objZafParSis) {
            super(new javax.swing.JCheckBox());
            intColEdit    = intColEditar;
            intColSigue   = intColNext;
            INT_TBL_CODITM= intColCodItm;
            INT_TBL_DESITM= intColDesItm;
            INT_TBL_PREUNI= intColPreUni; // Columna de la tabla que tendra el Precio Unitario
            INT_TBL_UNIDAD= intColUnidad;
            INT_TBL_BLNIVA= intColBlnIva; // Columna de la tabla que tendra si el producto lleva Iva o no
            tblMaestro   = tblMaster;
            txtString       = new javax.swing.JTextField();
            objUtil      = new Librerias.ZafUtil.ZafUtil();
            this.objZafParSis = objZafParSis;            
             
            txtString.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    String str = txtString.getText();
                    Librerias.ZafConsulta.ZafConsulta objConItm = getListaItm(""+ str);
                    preActionToDo();
                    if(!objConItm.buscar("LOWER(tx_codalt) = LOWER('" + str + "')") ){
                        objConItm.show();
                        if(objConItm.acepto()){
                            txtString.setText(objConItm.GetCamSel(1));
                            setDatos(objConItm);
                            fireEditingStopped();  
                            tblMaestro.requestFocus();
                            tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);
                            tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                        }
                    }else{
                            txtString.setText(objConItm.GetCamSel(1));
                            setDatos(objConItm);
                            fireEditingStopped();  
                            tblMaestro.requestFocus();
                            tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);
                            tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                    }
               }
            });
            
            this.editorComponent = txtString;
        }
            public EditorCodItm(int intColEditar, int intColNext, int intColCodItm, int intColDesItm, int intColUnidad, int intColPreUni, int intColBlnIva, javax.swing.JTable tblMaster, Librerias.ZafParSis.ZafParSis objZafParSis, javax.swing.JInternalFrame frmPrincipal){
                this(intColEditar, intColNext, intColCodItm, intColDesItm, intColUnidad, intColPreUni, intColBlnIva, tblMaster, objZafParSis);    
                this.frmPrincipal = frmPrincipal;
            }
        /**
         * Asigna los datos a las celdas segun el 
         * codigo del item que se selecciono
         */
        private void setDatos(Librerias.ZafConsulta.ZafConsulta objSrc){
            
                tblMaestro.setValueAt(objSrc.GetCamSel(2),tblMaestro.getSelectedRow(),INT_TBL_CODITM);
              //Poniendo La descripcion de producto, que el usuario eligio en la consulta, en la tabla 
                tblMaestro.setValueAt(objSrc.GetCamSel(3),tblMaestro.getSelectedRow(),INT_TBL_DESITM);
                tblMaestro.setValueAt(getUnidad(objSrc.GetCamSel(2)),tblMaestro.getSelectedRow(),INT_TBL_UNIDAD);
                Boolean blnIva = new Boolean(false);

                if(objSrc.GetCamSel(6).equals("S"))
                    blnIva = new Boolean(true);

                tblMaestro.setValueAt(new Double((objSrc.GetCamSel(5).equals(""))?"0":objSrc.GetCamSel(5)),tblMaestro.getSelectedRow(),INT_TBL_PREUNI);
                tblMaestro.setValueAt(blnIva,tblMaestro.getSelectedRow(),INT_TBL_BLNIVA);
            
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
        protected void fireEditingStopped() {
            super.fireEditingStopped();
            posActionToDo();            
        }
        public void cancelCellEditing(){
            super.cancelCellEditing();
            tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColEdit, false, false);                        
            
        }
        public Object getCellEditorValue() {
            return getItem(""+txtString.getText());
        }
        /**
         * Verifica si el item ingresado es correcto. Y trae todos sus datos
         * @param intCodArtAlt un String con el codigo alterno
         * @return String vacio si item no existe y si existe un string con item
         */
        public String getItem(String intCodArtAlt){
            java.sql.Statement stmInv;   //Statement para el recosteo
            java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
            String sSQL, strValReturn = "";

            try{//odbc,usuario,password
                java.sql.Connection conExi=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conExi!=null){                
                    stmInv=conExi.createStatement();

                    sSQL=  " Select *     " +
                           " From tbm_inv " +
                           " where        " +
                           " co_emp =     " + objZafParSis.getCodigoEmpresa() + " and "+  
                           " tx_codalt = '" + intCodArtAlt + "'";  

                    rstInv=stmInv.executeQuery(sSQL);

                    if (rstInv.next()){
                        int intCODITM = rstInv.getInt("co_itm");
                        tblMaestro.setValueAt(""+intCODITM,tblMaestro.getSelectedRow(),INT_TBL_CODITM);
                        tblMaestro.setValueAt((rstInv.getString("tx_nomItm")==null)?"":rstInv.getString("tx_nomItm"),tblMaestro.getSelectedRow(),INT_TBL_DESITM);
                        tblMaestro.setValueAt(getUnidad(""+intCODITM),tblMaestro.getSelectedRow(),INT_TBL_UNIDAD);
                        tblMaestro.setValueAt(new Double(rstInv.getDouble("nd_preVta1")),tblMaestro.getSelectedRow(),INT_TBL_PREUNI);
                        
                        Boolean blnIva = new Boolean(false);
                        if(((rstInv.getString("st_ivaVen")==null)?"":rstInv.getString("st_ivaVen")).equals("S"))
                            blnIva = new Boolean(true);
                        
                        tblMaestro.setValueAt(blnIva,tblMaestro.getSelectedRow(),INT_TBL_BLNIVA);
                        
                        strValReturn = intCodArtAlt;
                    }else{
                        tblMaestro.setValueAt("",tblMaestro.getSelectedRow(),INT_TBL_CODITM);
                        tblMaestro.setValueAt("",tblMaestro.getSelectedRow(),INT_TBL_DESITM);
                        tblMaestro.setValueAt("",tblMaestro.getSelectedRow(),INT_TBL_UNIDAD);
                        tblMaestro.setValueAt(new Double("0"),tblMaestro.getSelectedRow(),INT_TBL_PREUNI);
                        Boolean blnIva = new Boolean(false);
                        tblMaestro.setValueAt(blnIva,tblMaestro.getSelectedRow(),INT_TBL_BLNIVA);
                    }
                    

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
                return "";
            }

            catch(Exception Evt)
            {
                return "";
            }       
            return strValReturn ;
        }        

        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            strVal = (value == null)?"":value.toString();
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
                 "Codigo,CodSistema,Nombre,Stock,Precio,Iva?","tx_codalt, co_itm, tx_nomItm, nd_stkAct, nd_preVta1, st_ivaVen",
                 "select tx_codalt,co_itm,tx_nomItm,nd_stkAct,nd_preVta1,st_ivaVen from tbm_inv " +
                 "where co_emp = " + objZafParSis.getCodigoEmpresa() , strDesBusqueda, 
                 objZafParSis.getStringConexion(), 
                 objZafParSis.getUsuarioBaseDatos(), 
                 objZafParSis.getClaveBaseDatos()
                 );            
                obj.setTitle("Listado de Productos ");            
                return obj;
        }
    }    
