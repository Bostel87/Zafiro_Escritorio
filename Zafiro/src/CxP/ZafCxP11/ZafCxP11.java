/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafVen21.java  
 *
 * Created on Sep 20, 2011, 11:00:03 AM
 */

package CxP.ZafCxP11;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafRptSis.ZafRptSis;

/**   
 *
 * @author jayapata
 */
public class ZafCxP11 extends javax.swing.JInternalFrame {
  ZafParSis objParSis;
  ZafUtil objUti;
  private ZafSelFec objSelFec;

  ZafVenCon objVenConCLi;
  ZafVenCon objVenConVen;

  String strVersion=" 0.1 ";

   String strCodCli="";
   String strDesCli="";
   String strCodVen="";
   String strDesVen="";
    
   private ZafRptSis objRptSis;

   private ZafThreadGUIRpt objThrGUIRpt;


    /** Creates new form ZafVen21 */
    public ZafCxP11(ZafParSis objZafParsis) {
       try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();
       
         objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

         
        initComponents();

            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            panFilCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );

            //*******************************************************************************


        this.setTitle(objParSis.getNombreMenu()+" "+ strVersion );
        lblTit.setText( objParSis.getNombreMenu() );
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }

    }





public void Configura_ventana_consulta(){

 
  configurarVenConClientes();
  configurarVenConVendedor();

}





 private boolean configurarVenConClientes() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_cli");
        arlCam.add("a.tx_nom");
        arlCam.add("a.tx_dir");
        arlCam.add("a.tx_tel");
        arlCam.add("a.tx_ide");

        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Cli.");
        arlAli.add("Dirección");
        arlAli.add("Telefono");
        arlAli.add("RUC/CI");

        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("180");
        arlAncCol.add("120");
        arlAncCol.add("80");
        arlAncCol.add("100");

        //Armar la sentencia SQL.
        String  strSQL;
        //strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbm_cli as a " +
        //" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.st_cli='S' order by a.tx_nom ";

        strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 " +
        " INNER JOIN tbm_cli as a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) " +
        " WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" " +
        " AND  a.st_reg NOT IN('I','T')  order by a.tx_nom ";

        objVenConCLi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
}

private boolean configurarVenConVendedor() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_usr");
        arlCam.add("a.tx_nom");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nombre.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("70");
        arlAncCol.add("470");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
        " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
        " where b.co_emp="+objParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

        objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFilCab = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butcli = new javax.swing.JButton();
        rdaCerr = new javax.swing.JRadioButton();
        rdaAbr = new javax.swing.JRadioButton();
        rdaabrcerr = new javax.swing.JRadioButton();
        panSur = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butVisPre = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        lblTit.setText("jLabel1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFilCab.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab.setLayout(null);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        panFilCab.add(lblCli);
        lblCli.setBounds(10, 120, 100, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panFilCab.add(txtCodCli);
        txtCodCli.setBounds(70, 120, 50, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panFilCab.add(txtNomCli);
        txtNomCli.setBounds(120, 120, 280, 20);

        butcli.setFont(new java.awt.Font("SansSerif", 0, 11));
        butcli.setText("...");
        butcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcliActionPerformed(evt);
            }
        });
        panFilCab.add(butcli);
        butcli.setBounds(400, 120, 20, 20);

        buttonGroup1.add(rdaCerr);
        rdaCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rdaCerr.setText("Mostrar los documentos cerrados");
        panFilCab.add(rdaCerr);
        rdaCerr.setBounds(10, 170, 400, 20);

        buttonGroup1.add(rdaAbr);
        rdaAbr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rdaAbr.setSelected(true);
        rdaAbr.setText("Mostrar los documentos abiertos");
        panFilCab.add(rdaAbr);
        rdaAbr.setBounds(10, 150, 390, 20);

        buttonGroup1.add(rdaabrcerr);
        rdaabrcerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rdaabrcerr.setText("Mostrar los documentos abiertos y cerrados");
        panFilCab.add(rdaabrcerr);
        rdaabrcerr.setBounds(10, 190, 450, 20);

        tabFrm.addTab("General", panFilCab);

        panCen.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butVisPre.setText("Vista Preliminar");
        butVisPre.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butVisPre.setPreferredSize(new java.awt.Dimension(92, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panSur.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents




    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         ExiForm();
}//GEN-LAST:event_butCerActionPerformed

    private void butcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcliActionPerformed
        // TODO add your handling code here:
        objVenConCLi.setTitle("Listado de Clientes");
        objVenConCLi.setCampoBusqueda(1);
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
           
        }
}//GEN-LAST:event_butcliActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strDesCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.tx_nom",txtNomCli.getText(),1);
        }else
            txtNomCli.setText(strDesCli);
}//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesCli=txtNomCli.getText();
        txtNomCli.selectAll();
}//GEN-LAST:event_txtNomCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if(txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
                BuscarCliente("a.co_cli",txtCodCli.getText(),0);
        }else
            txtCodCli.setText(strCodCli);
}//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        ExiForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:


        if(txtCodCli.getText().equals("")){
            MensajeInf("Seleccione el cliente antes consultar un documento. ");
            tabFrm.setSelectedIndex(0);
            txtCodCli.requestFocus();
        } else{

            cargarRepote(1);
        }
    }//GEN-LAST:event_butVisPreActionPerformed

    private void ExiForm(){
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }



public void BuscarCliente(String campo,String strBusqueda,int tipo){
  objVenConCLi.setTitle("Listado de Clientes");
  if(objVenConCLi.buscar(campo, strBusqueda )) {
      txtCodCli.setText(objVenConCLi.getValueAt(1));
      txtNomCli.setText(objVenConCLi.getValueAt(2));
     
  }else{
        objVenConCLi.setCampoBusqueda(tipo);
        objVenConCLi.cargarDatos();
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
           txtCodCli.setText(objVenConCLi.getValueAt(1));
           txtNomCli.setText(objVenConCLi.getValueAt(2));
         
        }else{
            txtCodCli.setText(strCodCli);
            txtNomCli.setText(strDesCli);
  }}}





    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butVisPre;
    private javax.swing.JButton butcli;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton rdaAbr;
    private javax.swing.JRadioButton rdaCerr;
    private javax.swing.JRadioButton rdaabrcerr;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtNomCli;
    // End of variables declaration//GEN-END:variables





       private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
      }



  private void cargarRepote(int intTipo){
   if (objThrGUIRpt==null)
    {
        objThrGUIRpt=new ZafThreadGUIRpt();
        objThrGUIRpt.setIndFunEje(intTipo);
        objThrGUIRpt.start();
    }
}


    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                   // objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                  //  objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                 //   objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                   // objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }



 /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        boolean blnRes=true;
        String strRutRpt, strNomRpt;
        int i, intNumTotRpt;
        String sqlAux="";
         try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
              intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {

                        System.out.println(""+Integer.parseInt(objRptSis.getCodigoReporte(i)) );


                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.


                               sqlAux = SqlRpt();


                                java.util.Map mapPar2=new java.util.HashMap();

                                mapPar2.put("co_cli", new Integer( Integer.parseInt(txtCodCli.getText()) ) );
                                mapPar2.put("txnomcli", ""+txtNomCli.getText()  );

                                mapPar2.put("strSql", sqlAux  );

                                mapPar2.put("strCamAudRpt", ""+ this.getClass().getName() + "      " +  strNomRpt +"   " + objParSis.getNombreUsuario() + "          v0.1    " );

                                mapPar2.put("SUBREPORT_DIR", strRutRpt);

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar2, intTipRpt);

                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




private String SqlRpt(){
 String strSql="";
 String sqlFil="", sqlAux="";
 try{

     switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }


     if(rdaAbr.isSelected())
          sqlAux="  valpen is not null  ";



     if(rdaCerr.isSelected())
          sqlAux="  valpen is null  ";


     if(rdaabrcerr.isSelected())
          sqlAux="";



     if(!(sqlAux.equals(""))) sqlAux=" WHERE "+sqlAux;


     strSql="select * from ( "
     + " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.co_reg, a2.tx_descor, a.ne_numdoc, a.fe_doc, a1.ne_diacre, a1.fe_ven, case when a1.nd_porret > 0 then a1.nd_porret else null end as porret "
     + " , a1.mo_pag, a1.nd_Abo , case when (a1.mo_pag + a1.nd_Abo ) != 0 THEN (a1.mo_pag + a1.nd_Abo ) else null end   as valpen "
     + " from tbm_cabmovinv as a "
     + " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
     + " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc  ) "
     + " where a.co_emp="+objParSis.getCodigoEmpresa()+" and  a.co_cli= "+txtCodCli.getText()+"  and a.st_reg not in ('I','E') and a1.st_reg In ('A','C')  "
     + " "+sqlFil+" "
     + " order by  a.co_loc, a.co_tipdoc, a.ne_numdoc, a1.co_reg  "
     + " ) as x   "+sqlAux+" ";





    }catch(Exception e ){  }
 return strSql;

}



    

}
