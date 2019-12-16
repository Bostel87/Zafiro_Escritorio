/*
 * ZafHer01.java
 *
 * Created on 26 de octubre de 2005, 14:24
 */

package Herramientas.ZafHer01;

/**
 * @author  ireyes
 */
public class ZafHer01 extends javax.swing.JInternalFrame {
    private ZafHer01.MiTBar objTBAR ;
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private javax.swing.JInternalFrame jfrThis ;
    private Librerias.ZafUtil.ZafUtil objUtil;
    /**
     * Objetos SQL  para realizar la 
     * consulta de la clave de los registros 
     */
    private java.sql.Connection conDoc;
    private java.sql.Statement  stmDoc;
    private java.sql.ResultSet  rstDoc;
    
    /** Creates new form ZafHer01 */
    public ZafHer01(Librerias.ZafParSis.ZafParSis objZafParSis) {
        this.objZafParSis = objZafParSis;
        jfrThis = this;
        objUtil = new Librerias.ZafUtil.ZafUtil();
        initComponents();
        objTBAR = new ZafHer01.MiTBar(this);
        this.getContentPane().add(objTBAR,"South");
        limpiarTextos(this.getContentPane());
        objTBAR.setVisibleAnular(false);
        this.setTitle(objZafParSis.getNombreMenu()+" Ver 0.1");
       addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                   objTBAR.salida();
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });          
        
                
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Info = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        txtDesCor = new javax.swing.JTextField();
        spnDesLar = new javax.swing.JScrollPane();
        txtDesLar = new javax.swing.JTextArea();
        txtCodMnu = new javax.swing.JTextField();
        txtDesMnu = new javax.swing.JTextField();
        butConMnu = new javax.swing.JButton();
        txtFuncion = new javax.swing.JTextField();
        cboAutorizacion = new javax.swing.JComboBox();
        spnObservacion = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        cboSts = new javax.swing.JComboBox();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Codigo:");
        Info.setLayout(null);

        jLabel1.setText("Codigo:");
        Info.add(jLabel1);
        jLabel1.setBounds(30, 20, 60, 15);

        jLabel2.setText("Descripci\u00f3n Corta:");
        Info.add(jLabel2);
        jLabel2.setBounds(30, 50, 110, 15);

        jLabel3.setText("Descripci\u00f3n Larga:");
        Info.add(jLabel3);
        jLabel3.setBounds(30, 85, 110, 15);

        jLabel4.setText("Programa:");
        Info.add(jLabel4);
        jLabel4.setBounds(30, 106, 60, 15);

        jLabel5.setText("Funci\u00f3n:");
        Info.add(jLabel5);
        jLabel5.setBounds(30, 128, 50, 15);

        jLabel6.setText("Autorizaci\u00f3n:");
        Info.add(jLabel6);
        jLabel6.setBounds(30, 150, 90, 15);

        jLabel7.setText("Observaci\u00f3n:");
        Info.add(jLabel7);
        jLabel7.setBounds(30, 193, 80, 15);

        jLabel8.setText("Estado del Registro:");
        Info.add(jLabel8);
        jLabel8.setBounds(30, 214, 120, 15);

        txtCod.setBackground(objZafParSis.getColorCamposSistema());
        txtCod.setText("jTextField1");
        Info.add(txtCod);
        txtCod.setBounds(170, 20, 140, 20);

        txtDesCor.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCor.setText("jTextField2");
        Info.add(txtDesCor);
        txtDesCor.setBounds(170, 42, 310, 20);

        spnDesLar.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        txtDesLar.setBackground(objZafParSis.getColorCamposObligatorios());
        spnDesLar.setViewportView(txtDesLar);

        Info.add(spnDesLar);
        spnDesLar.setBounds(170, 64, 310, 40);

        txtCodMnu.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodMnu.setText("jTextField1");
        txtCodMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMnuActionPerformed(evt);
            }
        });
        txtCodMnu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMnuFocusLost(evt);
            }
        });

        Info.add(txtCodMnu);
        txtCodMnu.setBounds(170, 106, 40, 20);

        txtDesMnu.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesMnu.setText("jTextField2");
        txtDesMnu.setPreferredSize(new java.awt.Dimension(55, 21));
        txtDesMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesMnuActionPerformed(evt);
            }
        });

        Info.add(txtDesMnu);
        txtDesMnu.setBounds(234, 106, 246, 21);

        butConMnu.setText("...");
        butConMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConMnuActionPerformed(evt);
            }
        });

        Info.add(butConMnu);
        butConMnu.setBounds(212, 106, 20, 20);

        txtFuncion.setText("jTextField1");
        Info.add(txtFuncion);
        txtFuncion.setBounds(170, 128, 140, 20);

        cboAutorizacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "S", "N" }));
        Info.add(cboAutorizacion);
        cboAutorizacion.setBounds(170, 150, 140, 19);

        spnObservacion.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spnObservacion.setViewportView(txtObservacion);

        Info.add(spnObservacion);
        spnObservacion.setBounds(170, 172, 310, 40);

        cboSts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "I" }));
        Info.add(cboSts);
        cboSts.setBounds(170, 214, 140, 19);

        jTabbedPane1.addTab("General", Info);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }//GEN-END:initComponents

    private void butConMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConMnuActionPerformed
        // TODO add your handling code here:
        // Pantalla de consulta para buscar usuarios.
        Librerias.ZafConsulta.ZafConsulta objFndMnu = getFndMnu("", 0);
        objFndMnu.show();
        if(objFndMnu.acepto()){
            txtCodMnu.setText(objFndMnu.GetCamSel(1));
            txtDesMnu.setText(objFndMnu.GetCamSel(2));
        }        
    }//GEN-LAST:event_butConMnuActionPerformed

    private void txtDesMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesMnuActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        boolean blnEncontro = false;
        Librerias.ZafConsulta.ZafConsulta objFndMnu = getFndMnu(txtDesMnu.getText(), 2);
        if(txtDesMnu.getText().equals(""))
           objFndMnu.show();
        else
           if(!objFndMnu.buscar("tx_nom = '" + txtDesMnu.getText() + "'" ))
               objFndMnu.show();
           else
               blnEncontro = true;
        
        if(objFndMnu.acepto() || blnEncontro){
            txtCodMnu.setText(objFndMnu.GetCamSel(1));
            txtDesMnu.setText(objFndMnu.GetCamSel(2));
        }                    
    }//GEN-LAST:event_txtDesMnuActionPerformed

    private void txtCodMnuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMnuFocusLost
        boolean blnEncontro = false;
        Librerias.ZafConsulta.ZafConsulta objFndUsr = getFndMnu(txtCodMnu.getText(), 0);
        if(!txtCodMnu.getText().trim().equals(""))
            if(!objFndUsr.buscar("co_mnu = " + txtCodMnu.getText() + " " )){
                txtCodMnu.setText("");
                txtDesMnu.setText("");
            }else{
                txtCodMnu.setText(objFndUsr.GetCamSel(1));
                txtDesMnu.setText(objFndUsr.GetCamSel(2));
            }
        else{
                txtCodMnu.setText("");
                txtDesMnu.setText("");
        }
    }//GEN-LAST:event_txtCodMnuFocusLost

    private void txtCodMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMnuActionPerformed
        // TODO add your handling code here:
        boolean blnEncontro = false;        
        
        Librerias.ZafConsulta.ZafConsulta objFndMnu = getFndMnu(txtCodMnu.getText(), 0);
        if(txtCodMnu.getText().equals("")){
           objFndMnu.show();
        }else{
            if(!objFndMnu.buscar("co_mnu = " + txtCodMnu.getText() ))
               objFndMnu.show();
            else
               blnEncontro = true;        
        }
        if(objFndMnu.acepto()|| blnEncontro ){
            txtCodMnu.setText(objFndMnu.GetCamSel(1));
            txtDesMnu.setText(objFndMnu.GetCamSel(2));
        }                
    }//GEN-LAST:event_txtCodMnuActionPerformed
    
    
    private Librerias.ZafConsulta.ZafConsulta getFndMnu(String strTexBus, int intCamBus){
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
         new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this) ,
           "Codigo,Nombre","co_mnu,tx_nom",
                "select co_mnu , tx_nom from tbm_mnusis ", strTexBus, 
                 objZafParSis.getStringConexion(), 
                 objZafParSis.getUsuarioBaseDatos(), 
                 objZafParSis.getClaveBaseDatos()
             );
        objFnd.setTitle("Listado Programas del Sistema");
        objFnd.setSelectedCamBus(intCamBus);
        return objFnd;
   }
    //Funcion que limpia los componentes de esta ventana
    private void limpiarTextos(java.awt.Container conComponentes) 
    {
	java.awt.Component[] comps = conComponentes.getComponents();
        for (int i = 0; i < comps.length; i++) 
        {       
                if ((comps[i] instanceof java.awt.Container) && !(comps[i] instanceof javax.swing.JToolBar)) 
                {
                	limpiarTextos((java.awt.Container)comps[i]);
		}                 
                if (comps[i] instanceof javax.swing.text.JTextComponent) 
                {
                    javax.swing.text.JTextComponent txtpru = (javax.swing.text.JTextComponent)comps[i];
                    txtpru.setText("");
                }
//                    if (comps[i] instanceof javax.swing.JList ||
//                        comps[i] instanceof javax.swing.JButton ||
//                        comps[i] instanceof javax.swing.JTable ||
//                        comps[i] instanceof javax.swing.JRadioButton ||
//                        comps[i] instanceof javax.swing.JCheckBox ||
//                        comps[i] instanceof javax.swing.JSpinner)
                if (comps[i] instanceof javax.swing.JComboBox ){
                    javax.swing.JComboBox cbo = (javax.swing.JComboBox)comps[i];
                    cbo.setSelectedIndex(-1);
                    
                    if(comps[i] == cboSts){
                        
                    }
                }
                if (comps[i] instanceof javax.swing.JTable )
                {   
                    
                    javax.swing.JTable tblDat = (javax.swing.JTable)comps[i];
                    ((javax.swing.table.DefaultTableModel)tblDat.getModel()).setRowCount(0);
                }
                
         }
    }  
    
    private void refrescaDatos(){
           java.sql.Connection conCon;
           try{
                                    
               if(rstDoc != null){
                   conCon =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                   if (conCon!=null){
                         
                      /**    
                       * Ejecutando consulta
                       */
                      java.sql.Statement stmAutDoc = conCon.createStatement(); 

                      /* Ejecutando el Consulta */
                      java.sql.ResultSet rstAutDoc = stmAutDoc.executeQuery(                            
                                "SELECT doccab.co_reg, doccab.tx_descor, doccab.tx_deslar, doccab.tx_nomfun," +
                                "       doccab.tx_obs1, doccab.co_mnu, mnu.tx_nom, doccab.st_aut, doccab.st_reg " +
                                "FROM  tbm_regneg as doccab inner join tbm_mnusis as mnu on (mnu.co_mnu = doccab.co_mnu)" +
                                " where " +
                                     " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                     " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                     " co_reg = " + rstDoc.getInt("co_reg")
                        );
                      if(rstAutDoc.next()){
                        txtCod.setText(rstAutDoc.getInt("co_reg")+"");
                        txtDesCor.setText(rstAutDoc.getString("tx_descor")+"");
                        txtDesLar.setText(rstAutDoc.getString("tx_deslar")+"");
                        txtFuncion.setText(rstAutDoc.getString("tx_nomfun")+"");
                        txtCodMnu.setText(rstAutDoc.getInt("co_mnu")+"");
                        txtDesMnu.setText(rstAutDoc.getString("tx_nom")+"");
                        txtObservacion.setText(rstAutDoc.getString("tx_obs1")+"");
                        String strStAut =rstAutDoc.getString("st_aut");
                        if(strStAut!=null)
                            cboAutorizacion.setSelectedIndex((strStAut.equals("S"))?0:1);
                        else
                            cboAutorizacion.setSelectedIndex(-1);
                        
                        String strReg =rstAutDoc.getString("st_reg");
                        if(strReg!=null)
                            cboSts.setSelectedIndex((strReg.equals("A"))?0:1);
                        else
                            cboSts.setSelectedIndex(-1);
                        
                      }
                      
                     conCon.close();
                     conCon = null;
                   }   
               }
           }              
           catch(java.sql.SQLException Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           }
           catch(Exception Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
           }            

    }
    
        
    
    class MiTBar extends Librerias.ZafToolBar.ZafToolBar{
        //Filtro de la consulta actual.
        String strFiltro="";
	public MiTBar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis , objZafParSis);
        }

        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() {
            return true;
        }

        public boolean afterModificar() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }
        public boolean beforeAceptar() {
            return true;
        }
        
        public boolean beforeAnular() {
            return true;
        }
        
        public boolean beforeCancelar() {
            return true;
        }
        
        public boolean beforeConsultar() {
            return true;
        }
        
        public boolean beforeEliminar() {
            return true;
        }
        
        public boolean beforeImprimir() {
            return true;
        }
        
        public boolean beforeInsertar() {
            return true;
        }
        
        public boolean beforeModificar() {
            return true;
        }
        
        public boolean beforeVistaPreliminar() {
            return true;
        }        

        public boolean anular() {
            return true;
        }

        public boolean cancelar() {
            return true;
        }

        public void clickAceptar() {
        }

            public void clickInicio() {
                try{
                    
//                    if(blnCambios){
//                        switch (Mensaje()){
//                            case 0://Guardar los cambios
//                                if(modificar()){
//                                    rstDocCab.first();
//                                    refrescaDatos();
//                                }
//                                break;
//                            case 1://No guardar los cambios
//                                rstDocCab.first();
//                                refrescaDatos();
//                                break;
//                            default:
//                                //No hace nada se mantiene como estaba
//                        }
//                        
//                    }else{
                            rstDoc.first();
                            refrescaDatos();
//                    }
                }
                   catch(java.sql.SQLException Evt)
                   {
                         objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                    }

                    catch(Exception Evt)
                    {
                         objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                    }                                       
                
            }
            public void clickAnterior() {
                try{
                    if(!rstDoc.isFirst()){
//                            if(blnCambios){
//                                switch (Mensaje()){
//                                    case 0://Guardar los cambios
//                                        int intRowActual = rstDocCab.getRow();
//                                        if(modificar()){
//                                            rstDocCab.absolute(intRowActual);
//                                            rstDocCab.previous();
//                                            refrescaDatos();
//                                        }
//                                        break;
//                                    case 1://No guardar los cambios
//                                        rstDocCab.previous();
//                                        refrescaDatos();
//                                        break;
//                                    default:
//                                        //No hace nada se mantiene como estaba
//                                }
//
//                            }else{
                                    rstDoc.previous();
                                    refrescaDatos();
//                            }
                    }
                    
                }
                catch(java.sql.SQLException Evt)
                {
                    objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                }

                catch(Exception Evt)
                {
                    objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                 }                       
            }
            public void clickSiguiente() {
                try{
                    if(!rstDoc.isLast()){
//                            if(blnCambios){
//                                switch (Mensaje()){
//                                    case 0://Guardar los cambios
//                                        int intRowActual = rstDocCab.getRow(), intCo_Cot = Integer.parseInt(txtDoc.getText());
//                                        if(modificar()){
//                                            rstDocCab.absolute(intRowActual);
//                                            refrescaDatos();
//                                            if(intCo_Cot== Integer.parseInt(txtDoc.getText()))
//                                                rstDocCab.next();
//                                            refrescaDatos();
//                                        }
//                                        break;
//                                    case 1://No guardar los cambios
//                                        rstDocCab.next();
//                                        refrescaDatos();
//                                        break;
//                                    default:
//                                        //No hace nada se mantiene como estaba
//                                }
//
//                            }else{
                                    rstDoc.next();
                                    refrescaDatos();
//                            }
                     }
                }
                catch(java.sql.SQLException Evt)
                {
                      objUtil.mostrarMsgErr_F1(jfrThis, Evt); 
                }

                catch(Exception Evt)
                {
                      objUtil.mostrarMsgErr_F1(jfrThis, Evt); 
                 }                                       
            }
            public void clickFin() {
                try{
//                    if(blnCambios){
//                        switch (Mensaje()){
//                            case 0://Guardar los cambios
//                                if(modificar()){
//                                    rstDocCab.last();
//                                    refrescaDatos();
//                                }
//                                break;
//                            case 1://No guardar los cambios
//                                rstDocCab.last();
//                                refrescaDatos();
//                                break;
//                            default:
//                                //No hace nada se mantiene como estaba
//                        }
//                        
//                    }else{
                            rstDoc.last();
                            refrescaDatos();
//                    }
                 }
                   catch(java.sql.SQLException Evt)
                   {
                            objUtil.mostrarMsgErr_F1(jfrThis, Evt); 
                    }

                    catch(Exception Evt)
                    {
                            objUtil.mostrarMsgErr_F1(jfrThis, Evt); 
                    }                                       
            }        

        
        public void clickAnular() {
        }

        public void clickCancelar() {
                limpiarTextos(jfrThis.getContentPane());
                cierraConnections();                
        }

        public void clickConsultar() {
            
            /**
             * Consultando segun parametros ingresados 
             * en pantalla.
             */ 
            
            
        }

        
        
        public void clickEliminar() {
        }


        public void clickImprimir() {
        }


        public void clickInsertar() {
            limpiarTextos(jfrThis.getContentPane());
        }

        public void clickModificar() {
            java.awt.Color colBack = txtCod.getBackground();
            txtCod.setEditable(false);
            txtCod.setBackground(colBack);
            
        }


        public void clickVisPreliminar() {
        }

        public boolean consultar() {
           try{
               
                if(conDoc == null)
                        conDoc=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

                if(conDoc.isClosed())
                        conDoc=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                            
               
               if (conDoc!=null){

                  /**    
                   * Ejecutando consulta
                   */
                  stmDoc = conDoc.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );
                  String strCon =  "SELECT co_reg " +
                                    "FROM  tbm_regneg as doccab inner join tbm_mnusis as mnu ON (doccab.co_mnu = mnu.co_mnu)" +
                                   "WHERE  " +
                                   "      doccab.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                   "      doccab.co_loc = " + objZafParSis.getCodigoLocal()    ;

                  rstDoc = stmDoc.executeQuery(strCon + getParCon());    
                  
                  if(rstDoc.next()){
                      rstDoc.last();
                      //setMenSis("Se encontraron " + rstDocCab.getRow() + " registros");
                      refrescaDatos();
                  }
                  else{
                      setMenSis("0 Registros encontrados");
                      limpiarTextos(jfrThis.getContentPane());
                      cierraConnections();                                    
                      return false;
                  }                  
               }   
           }              
           catch(java.sql.SQLException Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
           }
           catch(Exception Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }            
            return true;
        }

        public boolean eliminar() {
           java.sql.Connection conDel;
           try{
               conDel =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

               if (conDel!=null){

                conDel.setAutoCommit(false);
                
                java.sql.PreparedStatement pstDel = conDel.prepareStatement(
                        "delete from tbm_regneg  " +
                             " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                             "       co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                             "       co_reg = " + txtCod.getText() 
                                ); 
                
                /* Ejecutando el insert */
                pstDel.executeUpdate();

                conDel.commit();
                conDel.setAutoCommit(true);
                conDel.close();
                conDel = null;
               }   
           }              
           catch(java.sql.SQLException Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
           }
           catch(Exception Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }            
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean insertar() {
            
//                          if(!validaCampos())
//                              return false;
           java.sql.Connection conIns;
           try{
               conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

               if (conIns!=null){

                conIns.setAutoCommit(false);

                int intMaxDoc = 0 ;
                java.sql.Statement stmMaxDoc = conIns.createStatement();
                String sSQLMaxDoc= "SELECT Max(co_reg) as co_reg  FROM tbm_regneg where " +
                                   " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                   " and co_loc = " + objZafParSis.getCodigoLocal();

                java.sql.ResultSet rstMaxDoc = stmMaxDoc.executeQuery(sSQLMaxDoc);

                if(rstMaxDoc.next()){
                    intMaxDoc = rstMaxDoc.getInt("co_reg");
                }
                intMaxDoc++;
                
                java.sql.PreparedStatement pstDetAutDoc = conIns.prepareStatement(
                        "INSERT INTO  tbm_regneg" +
                             "(co_emp, co_loc, co_reg, " + //CAMPOS PrimayKey
                             " tx_descor," +
                             " tx_deslar," +
                             " co_mnu , " +
                             " tx_nomfun," +
                             " st_aut, " +
                             " tx_obs1, " +
                             " st_reg, " +
                             " fe_ing, " +
                             " co_usring " +
                             "  )" +
                             "VALUES ( "  +
                         objZafParSis.getCodigoEmpresa() + " ,  " +
                         objZafParSis.getCodigoLocal()   + " ,  " + 
                         intMaxDoc                       + " , '" + 
                         getCorrectString(txtDesCor.getText())+ "', '" +
                         getCorrectString(txtDesLar.getText())+ "',  " +
                         txtCodMnu.getText()             + " , '" +
                         getCorrectString(txtFuncion.getText())+ "',  '" +
                         ((cboAutorizacion.getSelectedIndex()==0)?"S":"N") + "',  '" +
                         getCorrectString(txtObservacion.getText())+ "',  '" +
                         ((cboSts.getSelectedIndex()==1)?"I":"A") + "',  CURRENT_TIMESTAMP, " +
                         objZafParSis.getCodigoUsuario() + " " +
                         " )"); 

                /* Ejecutando el insert */
                pstDetAutDoc.executeUpdate();
                txtCod.setText(intMaxDoc+"");
                conIns.commit();
                conIns.setAutoCommit(true);
                conIns.close();
                conIns = null;
               }   
           }              
           catch(java.sql.SQLException Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
           }
           catch(Exception Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }            
            return true;
        }

        public boolean modificar() {
//                          if(!validaCampos())
//                              return false;
           java.sql.Connection conIns;
           try{
               conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

               if (conIns!=null){

                conIns.setAutoCommit(false);
                
                java.sql.PreparedStatement pstDetAutDoc = conIns.prepareStatement(
                        "update tbm_regneg set " +
                             " tx_descor ='" +getCorrectString(txtDesCor.getText()) +"' ,  " +
                             " tx_deslar ='" +getCorrectString(txtDesLar.getText()) + "',  " +
                             " co_mnu    = " + txtCodMnu.getText()                  + " ,  " +
                             " tx_nomfun ='" +getCorrectString(txtFuncion.getText())+ "',  " +
                             " st_aut    ='" +((cboAutorizacion.getSelectedIndex()==0)?"S":"N") + "',  " + 
                             " tx_obs1   ='" +getCorrectString(txtObservacion.getText())+ "',  " +
                             " st_reg    ='" +((cboSts.getSelectedIndex()==1)?"I":"A")  + "',  " +
                             " fe_ultmod    = CURRENT_TIMESTAMP , " +
                             " co_usrmod = " + objZafParSis.getCodigoUsuario() + 
                             " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                             "       co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                             "       co_reg = " + txtCod.getText() 
                                ); 

                /* Ejecutando el insert */
                pstDetAutDoc.executeUpdate();

                conIns.commit();
                conIns.setAutoCommit(true);
                conIns.close();
                conIns = null;
               }   
           }              
           catch(java.sql.SQLException Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
           }
           catch(Exception Evt)
           {    
                objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }            
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }
        private String getCorrectString(String strOriginal){
            return strOriginal.replaceAll("'","''");
        }
        /**
         * Obtiene los parametros que 
         * el usuario ingreso  en pantalla 
         * para realizar 
         * la consulta
         */
        private String getParCon(){
            String strParCon = "";
                //Agregando filtro por codigo de Tipo de Documento
                if(!txtCod.getText().equals(""))
                    strParCon = strParCon + " and DocCab.co_reg LIKE '" + txtCod.getText()+ "'";
                
                //Agregando filtro por Numero de Documento
                if(!txtDesCor.getText().equals(""))
                    strParCon = strParCon + " and DocCab.tx_descor LIKE '" + txtDesCor.getText()+ "'";

                if(!txtDesLar.getText().equals(""))
                    strParCon = strParCon + " and DocCab.tx_deslar LIKE '" + txtDesLar.getText()+ "'";

                if(!txtCodMnu.getText().equals(""))
                    strParCon = strParCon + " and DocCab.co_mnu LIKE '" + txtCodMnu.getText()+ "'";
            
                /**
                 * Poner para que filtre por nombre del codigo de  menu
                 */ 
                if(!txtCodMnu.getText().equals(""))
                    strParCon = strParCon + " and mnu.tx_nom LIKE '" + txtDesMnu.getText()+ "'";
            
                if(!txtFuncion.getText().equals(""))
                    strParCon = strParCon + " and DocCab.tx_nomfun LIKE '" + txtFuncion.getText()+ "'";
            
                if(cboAutorizacion.getSelectedIndex()!=-1)
                    strParCon = strParCon + " and DocCab.st_aut LIKE '" + cboAutorizacion.getSelectedItem()+ "'";
            
                if(!txtObservacion.getText().equals(""))
                    strParCon = strParCon + " and DocCab.tx_obs1 LIKE '" + txtObservacion.getText()+ "'";
            
            return strParCon;
        }
        
        private void cierraConnections(){
                try{
                    if(conDoc!=null)
                        if(!conDoc.isClosed()){
                            conDoc.close();
                            conDoc = null;
                            rstDoc = null;
                            stmDoc = null;
                        }
                }

                catch(java.sql.SQLException Evt)
               {
                      objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                }

                catch(Exception Evt)
                {
                      objUtil.mostrarMsgErr_F1(jfrThis, Evt);
                }                 

        }           

    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Info;
    private javax.swing.JButton butConMnu;
    private javax.swing.JComboBox cboAutorizacion;
    private javax.swing.JComboBox cboSts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JScrollPane spnDesLar;
    private javax.swing.JScrollPane spnObservacion;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtCodMnu;
    private javax.swing.JTextField txtDesCor;
    private javax.swing.JTextArea txtDesLar;
    private javax.swing.JTextField txtDesMnu;
    private javax.swing.JTextField txtFuncion;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
    
}
