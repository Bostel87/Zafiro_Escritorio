/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package RecursosHumanos.ZafRecHum41;

import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Roberto Flores
 * Guayaquil 28/05/2013
 */
public class ZafRecHum41 extends javax.swing.JInternalFrame {

  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private ZafUtil objUti; /**/
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/

  private ZafThreadGUI objThrGUI;
  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;

  private String strVersion=" v1.03 ";
  
  private ZafVenCon vcoEmp;                                               //Ventana de consulta.
  private ZafVenCon vcoTra;
  
  private String strCodTipDoc="";
  private String strDesCodTipDoc="";
  private String strDesLarTipDoc="";
  private String strCodLoc="";
  private String strDesLoc="";
  private String strCodUsr="";
  private String strUsr="";

  private String strCodEmp, strNomEmp;
  private String strCodDep = "";
  private String strDesLarDep = "";
  private String strCodTra = "";
  private String strNomTra = "";
  private String strCodMotJus, strDesMot;
    
  private int intCanReg=0;
  
  private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;
  
  private ZafPerUsr objPerUsr;
  
  private Librerias.ZafDate.ZafDatePicker txtFecDes;
  private Librerias.ZafDate.ZafDatePicker txtFecHas;

    /** Creates new form ZafRecHum41 */
    public ZafRecHum41(Librerias.ZafParSis.ZafParSis obj) {
          try{ /**/
              this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();

              initComponents();
              objPerUsr=new ZafPerUsr(objZafParSis);
              objUti = new ZafUtil();
              
              
//              if(objZafParSis.getCodigoUsuario()!=1){
//                  butVisPre.setVisible(Boolean.FALSE);
//                  butImp.setVisible(Boolean.FALSE);
//                  butCerr.setVisible(Boolean.FALSE);
//                  
//                  if(objPerUsr.isOpcionEnabled(intCanReg)){
//
//                  }
//                  
//              }
              
              this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
              lblTit.setText(objZafParSis.getNombreMenu());  /**/

              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
                           
              Calendar cal = Calendar.getInstance();
              
              txtFecDes = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
              txtFecDes.setBackground(objZafParSis.getColorCamposObligatorios());
              txtFecDes.setPreferredSize(new java.awt.Dimension(70, 20));
              txtFecDes.setText(01, cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
              panFil.add(txtFecDes);
              txtFecDes.setBounds(150, 130, 92, 20);
              txtFecDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
              txtFecDes.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                      txtFecActionPerformed(evt);
                  }

                private void txtFecActionPerformed(ActionEvent evt) {
                    txtFecDes.transferFocus();
                }
              });
             
              txtFecHas = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
              txtFecHas.setBackground(objZafParSis.getColorCamposObligatorios());
              txtFecHas.setPreferredSize(new java.awt.Dimension(70, 20));
              txtFecHas.setText(cal.getMaximum(Calendar.DATE), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
              panFil.add(txtFecHas);
              txtFecHas.setBounds(300, 130, 92, 20);
              txtFecHas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
              txtFecHas.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                      txtFecActionPerformed(evt);
                  }

                private void txtFecActionPerformed(ActionEvent evt) {
                    txtFecHas.transferFocus();
                }
              });
              
         //}catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
          }catch (Exception e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }

     /**
      * Carga ventanas de consulta y configuracion de la tabla
      */

    public void Configura_ventana_consulta(){
        configurarVenConTra();
        configurarVenConEmp();
    }
    
    
    
    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            //Armar la sentencia SQL.

            String strSQL="";
            if(objZafParSis.getCodigoUsuario()==1){
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");
            
            String strSQL="";
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where  co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}

  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(10, 60, 100, 20);

        txtCodEmp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(110, 60, 60, 20);

        txtNomEmp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(170, 60, 380, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(550, 60, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Rango de fechas:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(10, 100, 100, 20);

        txtCodTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(110, 80, 60, 20);

        txtNomTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(170, 80, 380, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(550, 80, 20, 20);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel8.setText("Desde:"); // NOI18N
        panFil.add(jLabel8);
        jLabel8.setBounds(110, 130, 50, 20);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel9.setText("Hasta:"); // NOI18N
        panFil.add(jLabel9);
        jLabel9.setBounds(260, 130, 50, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Empleado:"); // NOI18N
        panFil.add(jLabel7);
        jLabel7.setBounds(10, 80, 100, 20);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butVisPre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVisPre.setText("Vista Preliminar");
        butVisPre.setPreferredSize(new java.awt.Dimension(90, 23));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        jPanel5.add(butVisPre);

        butImp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butImp.setText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(90, 23));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        jPanel5.add(butImp);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDocActionPerformed(java.awt.event.ActionEvent evt) {
//        txtFec.transferFocus();
    } 
    
    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        // TODO add your handling code here:
         exitForm();
}//GEN-LAST:event_butCerrActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    public void BuscarEmp(String campo,String strBusqueda,int tipo){
        
        vcoEmp.setTitle("Listado de Empresas");
        if(vcoEmp.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
  }}}
    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
  }}}
    
    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (txtNomEmp.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
            {
                if (txtNomEmp.getText().equals(""))
                {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                }
                else
                {
                    mostrarVenConEmp(2);
                }
            }
            else
            txtNomEmp.setText(strNomEmp);
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
            txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra=txtCodTra.getText();
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:

        cargarRepote(1);

        //         java.sql.Connection conIns;
        //         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
        //         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
        //         String sqlAux="";
        //         String strNomUsr="";
        //         String strFecHorSer="";
        //         try{
            //             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            //            if(conIns!=null){
                //
                //                if(System.getProperty("os.name").equals("Linux")){
                    //                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
                    //                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
                    //                }
                //
                //                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
                //                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
                //
                //                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
                //                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
                //
                //
                //                 switch (objSelFec.getTipoSeleccion())
                //                 {
                    //                    case 0: //Búsqueda por rangos
                    //                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 1: //Fechas menores o iguales que "Hasta".
                    //                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 2: //Fechas mayores o iguales que "Desde".
                    //                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 3: //Todo.
                    //                        break;
                    //                 }
                //
                //                  if(!(txtCodLoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
                    //                  }else{
                    //                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
                    //                  }
                //
                //                  if(!(txtCodTipDoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                    //                  }
                //
                //
                //                 if(!(txtCodUsr.getText().equals(""))){
                    //                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
                    //                 }
                //
                //
                //                 //Obtener la fecha y hora del servidor.
                //                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                //                    if (datFecAux!=null){
                    //                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                    //                      datFecAux=null;
                    //                    }
                //                    strNomUsr=objZafParSis.getNombreUsuario();
                //
                //                   Map parameters = new HashMap();
                //
                //                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
                //                   parameters.put("strAux",  sqlAux );
                //                   parameters.put("nomusr", strNomUsr );
                //                   parameters.put("fecimp", strFecHorSer );
                //                   parameters.put("SUBREPORT", subReport);
                //
                //                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
                //                  JasperViewer.viewReport(report, false);
                //
                //                  conIns.close();
                //                  conIns=null;
                //
                //        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
        //          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }

    }//GEN-LAST:event_butVisPreActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:

        cargarRepote(0);

        //         java.sql.Connection conIns;
        //         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
        //         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
        //         String sqlAux="";
        //         String strNomUsr="";
        //         String strFecHorSer="";
        //         try{
            //             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            //            if(conIns!=null){
                //
                //                if(System.getProperty("os.name").equals("Linux")){
                    //                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafRecHum09/ZafRecHum09.jrxml";
                    //                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafRecHum09/ZafCxC59_01.jrxml";
                    //                }
                //
                //                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
                //                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
                //
                //                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
                //                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
                //
                //
                //                 switch (objSelFec.getTipoSeleccion())
                //                 {
                    //                    case 0: //Búsqueda por rangos
                    //                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 1: //Fechas menores o iguales que "Hasta".
                    //                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 2: //Fechas mayores o iguales que "Desde".
                    //                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    //                        break;
                    //                    case 3: //Todo.
                    //                        break;
                    //                 }
                //
                //                  if(!(txtCodLoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
                    //                  }else {
                    //                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
                    //                  }
                //
                //                  if(!(txtCodTipDoc.getText().equals(""))){
                    //                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                    //                  }
                //
                //
                //                 if(!(txtCodUsr.getText().equals(""))){
                    //                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
                    //                 }
                //
                //
                //                   //Obtener la fecha y hora del servidor.
                //                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                //                    if (datFecAux!=null){
                    //                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                    //                      datFecAux=null;
                    //                    }
                //                    strNomUsr=objZafParSis.getNombreUsuario();
                //
                //                   Map parameters = new HashMap();
                //
                //                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
                //                   parameters.put("strAux",  sqlAux );
                //                   parameters.put("nomusr", strNomUsr );
                //                   parameters.put("fecimp", strFecHorSer );
                //                   parameters.put("SUBREPORT", subReport);
                //
                //                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
                //                  JasperManager.printReport(report,false);
                //
                //                  conIns.close();
                //                  conIns=null;
                //
                //        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
        //          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }
    }//GEN-LAST:event_butImpActionPerformed

private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

        if(validarCamObl()){
            tabGen.setSelectedIndex(1);  
        }else{
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
            }
        
        objThrGUI=null;
    }
}

    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamObl(){
        String strNomCam = null;
        boolean blnOk = true;

        if(txtCodEmp.getText().length()==0){
            txtCodEmp.requestFocus();
            txtCodEmp.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Empresa</FONT> es obligatorio.<BR>Escriba o seleccione una empresa y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(txtCodTra.getText().length()==0){
            txtCodTra.requestFocus();
            txtCodTra.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Empleado</FONT> es obligatorio.<BR>Escriba o seleccione una empresa y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        return blnOk;
    }

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butTra;
    private javax.swing.JButton butVisPre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
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
        String strRutRpt, strNomRpt, strNomUsr="",  strFecHorSer="", strCamAudRpt = "";
        String strNomEmp="", strPago="", strPeriodo="", strImpresión="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String sqlAux="", strSql = "";
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
                        
                        //Inicializar los parametros que se van a pasar al reporte.

                        String[] strArrFecDes = txtFecDes.getText().split("/");
                        String[] strArrFecHas = txtFecHas.getText().split("/");

                        int intAño=Calendar.getInstance().get(Calendar.YEAR);
                        int intMes=0;
                        int intPer = 2;
                        System.out.println("codigo menu: " + Integer.parseInt(objRptSis.getCodigoReporte(i)));
                        
                        if(objZafParSis.getCodigoMenu()==3259){
                            strCodTipDoc="192";
                        }else if(objZafParSis.getCodigoMenu()==3540){
                            strCodTipDoc="199";
                        }else if(objZafParSis.getCodigoMenu()==3543){
                            strCodTipDoc="202";
                        }
                        
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            //case 415:
                            default:
//                            case 450:
                                strNomEmp=objZafParSis.getNombreEmpresa();
                                strPago="Fin de mes";
                                strPeriodo="Fin de mes" + " " + intAño;
                                
                                 //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }
                                
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                               
                                String strFecDes = strArrFecDes[2] + "-" + strArrFecDes[1] + "-" + strArrFecDes[0];
                                String strFecHas = strArrFecHas[2] + "-" + strArrFecHas[1] + "-" + strArrFecHas[0];
                                
                                strSql=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_tra, \n" +
                                        " (b.tx_ape || ' ' || b.tx_nom) as empleado, round(d.nd_valRubSinDes,2) as nd_valIESS, e.ne_numDiaLab, c.fe_ing, c.ne_per, \n"+
                                        " (\n"+
                                        " SELECT CASE WHEN c.ne_mes=1 THEN 'Enero'    \n"+ //Rose
                                        " ELSE CASE WHEN c.ne_mes=2 THEN 'Febrero'    \n"+
                                        " ELSE CASE WHEN c.ne_mes=3 THEN 'Marzo'      \n"+    
                                        " ELSE CASE WHEN c.ne_mes=4 THEN 'Abril'      \n"+      
                                        " ELSE CASE WHEN c.ne_mes=5 THEN 'Mayo'       \n"+     
                                        " ELSE CASE WHEN c.ne_mes=6 THEN 'Junio'      \n"+     
                                        " ELSE CASE WHEN c.ne_mes=7 THEN 'Julio'      \n"+     
                                        " ELSE CASE WHEN c.ne_mes=8 THEN 'Agosto'     \n"+     
                                        " ELSE CASE WHEN c.ne_mes=9 THEN 'Septiembre' \n"+
                                        " ELSE CASE WHEN c.ne_mes=10 THEN 'Octubre'   \n"+  
                                        " ELSE CASE WHEN c.ne_mes=11 THEN 'Noviembre' \n"+ 
                                        " ELSE CASE WHEN c.ne_mes=12 THEN 'Diciembre' \n"+
                                        " END    END    END    END    END    END    END    END    END    END    END    END "+
                                        "|| ' ' || (c.ne_ani::VARCHAR)) as strPeriodo  \n"+
                                        "from tbm_detrolpag a\n" +
                                        "INNER JOIN tbm_tra b on (a.co_tra=b.co_tra) \n" +
                                        "INNER JOIN tbm_cabrolpag c on (c.co_emp=a.co_emp and c.co_loc=a.co_loc and c.co_tipdoc=a.co_tipdoc and c.co_doc=a.co_doc)\n" +
                                        "INNER JOIN tbm_ingegrmentra d on (d.co_emp=a.co_emp and d.co_tra=a.co_tra and d.ne_ani=c.ne_ani and d.ne_mes=c.ne_mes and d.ne_per=0 and d.co_rub = 1)\n" +
                                        "LEFT OUTER JOIN tbm_datgeningegrmentra e on (e.co_emp=a.co_emp and e.co_tra=a.co_tra and e.ne_ani=c.ne_ani and e.ne_mes=c.ne_mes and e.ne_per=0)\n" +
                                        "WHERE a.co_emp="+txtCodEmp.getText()+"\n" +
                                        "AND a.co_loc="+objZafParSis.getCodigoLocal()+"\n" +
                                        "AND a.co_tipdoc="+strCodTipDoc+"\n" +
                                        "AND a.co_tra="+txtCodTra.getText()+"\n" +
                                        "AND date(c.fe_ing) BETWEEN "+objUti.codificar(strFecDes)+" AND "+objUti.codificar(strFecHas)+"\n" +
                                        "AND c.ne_per=2\n" +
                                        "AND c.st_reg = 'A'\n" +
                                        "GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc,a.co_tra, empleado, d.nd_valRubSinDes, e.ne_numDiaLab, c.fe_ing, c.ne_ani,c.ne_mes,c.ne_per, c.st_reg\n" +
                                        "ORDER BY empleado, c.fe_ing";
                                
                                System.out.println("vista previa SUE: " + strSql);
                                
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strsql", strSql );
                                mapPar.put("co_emp", objZafParSis.getCodigoEmpresa() );
                                mapPar.put("ne_ani", intAño );
                                mapPar.put("ne_mes", intMes );
                                mapPar.put("ne_per", intPer );
                                mapPar.put("empresa", strNomEmp );
                                mapPar.put("pago", strPago );
                                mapPar.put("periodo", null );
                                mapPar.put("impresion", strFecHorSer );
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                
                                /*java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("coemp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("strAux",  sqlAux );
                                mapPar.put("nomusr", strNomUsr );
                                mapPar.put("fecimp", strFecHorSer );
                                mapPar.put("SUBREPORT_DIR", strRutRpt );
                                mapPar.put("strCamAudRpt", "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                */

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
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
}