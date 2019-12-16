/*
 * ZafMae20.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */

package Librerias.ZafGenFacAut;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafMovIngEgrInv.ZafReaMovInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;



/**
 *
 * @author  José Marín 
 */
public class ZafGenFacAutAdm extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private Connection con;
    private ZafGenFacAut objGenFacAut;
    private ZafReaMovInv objReaMovInv;
    private ZafCorEle objCorEle;
    private String strSql;
    
        public ZafGenFacAutAdm(ZafParSis obj) {
            try{
                objParSis=obj;
                objGenFacAut = new ZafGenFacAut(objParSis,this);
                objReaMovInv = new ZafReaMovInv(objParSis, this);
                objCorEle = new ZafCorEle(objParSis);
                initComponents();
                
                lblVersion.setText((objGenFacAut.strVer));
                lblObjReaMovInv.setText((objReaMovInv.strVer));
            }
            catch (Exception e){
                this.setTitle(this.getTitle() + " [ERROR]");
            }

        }
        
 
        
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        optGenOD = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        panFac = new javax.swing.JPanel();
        txtCodSeg = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        butGua = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        btnConsulta = new javax.swing.JButton();
        txtCodEmpCot = new javax.swing.JTextField();
        txtCodLocCot = new javax.swing.JTextField();
        txtCodCot = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblObjReaMovInv = new javax.swing.JLabel();
        panDet = new javax.swing.JPanel();
        txtCodDoc = new javax.swing.JTextField();
        txtCodEmp = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        btnGenOD = new javax.swing.JButton();
        optGenODLocal = new javax.swing.JRadioButton();
        optAsignaNumeracionOD = new javax.swing.JRadioButton();
        optGenODPrestamos = new javax.swing.JRadioButton();
        optGenODTrans = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        optGenGuiTermL = new javax.swing.JRadioButton();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        btnSolTra = new javax.swing.JToggleButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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
                exitForm(evt);
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panRpt.setLayout(new java.awt.GridLayout(1, 0));

        panFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panFac.setLayout(null);
        panFac.add(txtCodSeg);
        txtCodSeg.setBounds(20, 170, 60, 20);

        jLabel1.setText("SEGUIMIENTO");
        panFac.add(jLabel1);
        jLabel1.setBounds(20, 130, 80, 30);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panFac.add(butGua);
        butGua.setBounds(20, 210, 80, 25);

        jLabel3.setText("GENERAR FACTURA - ACTUALIZAR FACTURA INICIO");
        panFac.add(jLabel3);
        jLabel3.setBounds(20, 20, 310, 20);

        jLabel8.setText("Cambiar estado en la cotizacion en caso de factura al final L");
        panFac.add(jLabel8);
        jLabel8.setBounds(20, 50, 290, 14);

        jLabel2.setText("AGREGAR EL SEGUIMIENTO PARA GENERAR LA FACTURA ");
        panFac.add(jLabel2);
        jLabel2.setBounds(20, 290, 290, 14);

        lblVersion.setText("jLabel9");
        panFac.add(lblVersion);
        lblVersion.setBounds(20, 240, 290, 14);

        btnConsulta.setText("Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        panFac.add(btnConsulta);
        btnConsulta.setBounds(233, 80, 90, 23);
        panFac.add(txtCodEmpCot);
        txtCodEmpCot.setBounds(20, 110, 60, 20);
        panFac.add(txtCodLocCot);
        txtCodLocCot.setBounds(90, 110, 60, 20);
        panFac.add(txtCodCot);
        txtCodCot.setBounds(160, 110, 60, 20);

        jButton1.setText("Listo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panFac.add(jButton1);
        jButton1.setBounds(240, 110, 80, 23);

        lblObjReaMovInv.setText("jLabel9");
        panFac.add(lblObjReaMovInv);
        lblObjReaMovInv.setBounds(20, 270, 280, 14);

        panRpt.add(panFac);

        panDet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panDet.setLayout(null);
        panDet.add(txtCodDoc);
        txtCodDoc.setBounds(220, 50, 60, 20);
        panDet.add(txtCodEmp);
        txtCodEmp.setBounds(10, 50, 60, 20);
        panDet.add(txtCodLoc);
        txtCodLoc.setBounds(80, 50, 60, 20);
        panDet.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(150, 50, 60, 20);

        btnGenOD.setText("OD");
        btnGenOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenODActionPerformed(evt);
            }
        });
        panDet.add(btnGenOD);
        btnGenOD.setBounds(20, 260, 80, 23);

        optGenOD.add(optGenODLocal);
        optGenODLocal.setSelected(true);
        optGenODLocal.setText("GENERA OD LOCAL");
        panDet.add(optGenODLocal);
        optGenODLocal.setBounds(20, 90, 170, 23);

        optGenOD.add(optAsignaNumeracionOD);
        optAsignaNumeracionOD.setText("ASIGNA NUMERACION OD LOCAL");
        panDet.add(optAsignaNumeracionOD);
        optAsignaNumeracionOD.setBounds(20, 120, 210, 23);

        optGenOD.add(optGenODPrestamos);
        optGenODPrestamos.setText("GENERA OD PRESTAMOS");
        panDet.add(optGenODPrestamos);
        optGenODPrestamos.setBounds(20, 150, 200, 23);

        optGenOD.add(optGenODTrans);
        optGenODTrans.setText("GENERA OD TRANSFERENCIAS");
        panDet.add(optGenODTrans);
        optGenODTrans.setBounds(20, 180, 200, 23);

        jLabel4.setText("co_doc");
        panDet.add(jLabel4);
        jLabel4.setBounds(220, 30, 50, 14);

        jLabel5.setText("co_emp");
        panDet.add(jLabel5);
        jLabel5.setBounds(10, 30, 50, 14);

        jLabel6.setText("co_loc");
        panDet.add(jLabel6);
        jLabel6.setBounds(80, 30, 50, 14);

        jLabel7.setText("co_tipDoc");
        panDet.add(jLabel7);
        jLabel7.setBounds(150, 30, 50, 14);

        optGenOD.add(optGenGuiTermL);
        optGenGuiTermL.setText("GENERA GUIA TERMINAL L (solo items L)");
        panDet.add(optGenGuiTermL);
        optGenGuiTermL.setBounds(20, 210, 300, 23);

        panRpt.add(panDet);

        tabFrm.addTab("Jota", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Jota");

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton5.setText("Costear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        panBot.add(jButton5);

        btnSolTra.setText("SolTra");
        btnSolTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolTraActionPerformed(evt);
            }
        });
        panBot.add(btnSolTra);

        jButton4.setText("PRESTAMOS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        panBot.add(jButton4);

        jButton3.setText("Fac.Rel.");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panBot.add(jButton3);

        jButton2.setText("Stock");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panBot.add(jButton2);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                if(con!=null){
                    con.close();
                    con=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }        
}//GEN-LAST:event_exitForm

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if(guardarAutorizacion()){
            mostrarMsgInf("Listo.");
        }
        else{
            mostrarMsgInf("No guardo.");
        }
        

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void btnGenODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenODActionPerformed
        // TODO add your handling code here:
        
        generaOrdenDespacho();
    }//GEN-LAST:event_btnGenODActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        // TODO add your handling code here:
        cotizacionesEnProceso();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(!txtCodEmpCot.getText().equals("") && !txtCodLocCot.getText().equals("") && !txtCodCot.getText().equals("")){
            cotizacionesModificar();
        }
        else{
            mostrarMsgCon("INCOMPLETO ");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        llamarVenAdminRel();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        negativosInventario();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        llamarVenAdminPrestamos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSolTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolTraActionPerformed
        // TODO add your handling code here:
        llamarVenAdminSolicitudTransferencia();
    }//GEN-LAST:event_btnSolTraActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        llamarVenAdminCostearDocumento();
    }//GEN-LAST:event_jButton5ActionPerformed

    
    private void llamarVenAdminCostearDocumento(){
        ZafCostearDocumento objCostearDocumento = new  ZafCostearDocumento(objParSis);
        this.getParent().add(objCostearDocumento,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objCostearDocumento.show();
    }
    
    
    private void negativosInventario(){
        java.sql.Connection conLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc.setAutoCommit(false);
            if(querysIngrid(conLoc)){
                if(querysIngrid4(conLoc)){
                    if(disponible(conLoc)){
                        conLoc.commit();
                        System.out.println("OK Negativos");
                        mostrarMsgCon("OK Stock! ");
                    }else{conLoc.rollback();}
                }else{conLoc.rollback();}
            }else{conLoc.rollback();}
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e) {
            
            System.err.println("ERROR: " + e);
            
        } catch (Exception e) {
            
            System.err.println("ERROR: " + e);
        }  
        
    }
    
    
    private boolean disponible(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conn!=null){
                System.out.println("Paso3");
                stmLoc=conn.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_invBod SET nd_canDis=a.nd_stkAct ";
                strSql+=" FROM ( ";
                strSql+="   select a2.tx_codAlt,a1.*";
                strSql+="   from tbm_invBod as a1 ";
                strSql+="   inner join tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)  ";
                strSql+="   where a1.nd_canDis > a1.nd_stkAct and a1.co_emp>0  ";
                strSql+=" )as a WHERE  ";
                strSql+=" tbm_invBod.co_emp=a.co_emp AND tbm_invBod.co_itm=a.co_itm AND tbm_invBod.co_bod=a.co_bod ";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e) {
            
            System.err.println("ERROR: " + e);
            blnRes=false;
            
        } catch (Exception e) {
             
            System.err.println("ERROR: " + e);
            blnRes=false;
        }  
        return blnRes;
    }
    
    
      private void llamarVenAdminRel(){
        ZafGenFacAutAdmGenRel objGeneraFacturaRelacionada = new  ZafGenFacAutAdmGenRel(objParSis);
        this.getParent().add(objGeneraFacturaRelacionada,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objGeneraFacturaRelacionada.show();
    }
    
      
      private void llamarVenAdminSolicitudTransferencia(){
        ZafGenSolTra_Adm objGeneraSolicitudTransferencia = new  ZafGenSolTra_Adm(objParSis);
        this.getParent().add(objGeneraSolicitudTransferencia,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objGeneraSolicitudTransferencia.show();
      }
      
      
      private void llamarVenAdminPrestamos(){
        ZafGenPreEmp_Adm objGeneraPrestamosRelacionada = new  ZafGenPreEmp_Adm(objParSis);
        this.getParent().add(objGeneraPrestamosRelacionada,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objGeneraPrestamosRelacionada.show();
    }
      
    private void cotizacionesEnProceso(){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conCot;
        String strCadena, strCor="";
        try{
            conCot =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
            if(conCot!=null){
                stmLoc=conCot.createStatement();
                strCadena="";
                strCadena+=" select * from tbm_cabCotVen where st_reg='E' and fe_cot > '2016-08-01' order by fe_cot ASC ";
                rstLoc = stmLoc.executeQuery(strCadena);
                while(rstLoc.next()){
                   strCor+="<HTML> EMPRESA "+ rstLoc.getString("co_emp");
                   strCor+=" LOCAL  "+ rstLoc.getString("co_loc");
                   strCor+=" COTIZACION "+ rstLoc.getString("co_cot");
                   strCor+="  <BR> </HTML>";
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                objCorEle.enviarCorreoMasivo("sistemas2@tuvalsa.com;sistemas6@tuvalsa.com", "Reporte Cotizaciones", strCor);
            }
            conCot.close();
            conCot=null;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    private void cotizacionesModificar(){
        java.sql.Statement stmLoc;
        java.sql.Connection conCot;
        String strCadena, strCor="";
        try{
            conCot =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
            if(conCot!=null){
                stmLoc=conCot.createStatement();
                strCadena="";
                strCadena+=" UPDATE tbm_cabCotVen SET st_reg='L' WHERE co_emp="+txtCodEmpCot.getText()+" and co_loc="+txtCodLocCot.getText()+" and co_cot="+txtCodCot.getText()+" ";
                stmLoc.executeUpdate(strCadena);               
                stmLoc.close();
                stmLoc=null;
                objCorEle.enviarCorreoMasivo("sistemas2@tuvalsa.com;sistemas6@tuvalsa.com", "Reporte Cotizaciones ", strCadena);
            }
            conCot.close();
            conCot=null;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    java.sql.Connection conLoc;
    public void conectar(){
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());        
            conLoc.setAutoCommit(false);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }      
    
    static ZafGenOdPryTra uuu= null;
    
    private void generaOrdenDespacho()
    {
        boolean booResp=false;
        boolean booResp2=true;
        try{
            conectar();
            uuu=new ZafGenOdPryTra();
            if(optGenODPrestamos.isSelected()){
                booResp=uuu.generarOrdDesxPrexProyTra(conLoc, Integer.parseInt(txtCodEmp.getText()), Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
            }
            else if(optGenODTrans.isSelected()){
                booResp= uuu.generarODTraPryTra(conLoc, Integer.parseInt(txtCodEmp.getText()), Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
            }
            else if (optAsignaNumeracionOD.isSelected()){
                booResp=uuu.generarNumOD(conLoc, Integer.parseInt(txtCodEmp.getText()), Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()),false);
            }else if(optGenGuiTermL.isSelected()){
                booResp=uuu.generarTermL(conLoc,Integer.parseInt(txtCodEmp.getText()), Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) );                
                booResp2=false;
            }else { // GENERA OD LOCAL 
                booResp=uuu.generarODLocal(conLoc, Integer.parseInt(txtCodEmp.getText()), Integer.parseInt(txtCodLoc.getText()), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), true);
            }
            if( booResp2){
                if(booResp){
                    conLoc.commit();
                }else{
                    conLoc.rollback();
                }
            }
            conLoc.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }     
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnGenOD;
    private javax.swing.JToggleButton btnSolTra;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butGua;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblObjReaMovInv;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JRadioButton optAsignaNumeracionOD;
    private javax.swing.JRadioButton optGenGuiTermL;
    private javax.swing.ButtonGroup optGenOD;
    private javax.swing.JRadioButton optGenODLocal;
    private javax.swing.JRadioButton optGenODPrestamos;
    private javax.swing.JRadioButton optGenODTrans;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFac;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtCodCot;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodEmpCot;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodLocCot;
    private javax.swing.JTextField txtCodSeg;
    private javax.swing.JTextField txtCodTipDoc;
    // End of variables declaration//GEN-END:variables

//    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
    
  
 
      
    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

    
    
    
    
    
    
    /**
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    

  

 
 

 
 
 
    
     
    /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    
    private boolean guardarAutorizacion(){
       boolean blnRes=false;
       try{
               con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
               con.setAutoCommit(false);
               if(objGenFacAut.iniciarProcesoGeneraFactura(con, Integer.parseInt(txtCodSeg.getText())) ){
                    con.commit();
                    blnRes=true;
                }
                else{
                    con.rollback();
                }
               con.close();
               con=null;
               
       }
       catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
       return blnRes;
    }
    
    

    
    private boolean querysIngrid4 (java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conn!=null){
                System.out.println("Paso2");
                stmLoc=conn.createStatement();
                strSql="";
                strSql+=" update tbm_inv set nd_stkact=a.sum FROM ( select * from(  select * from(  ";
                strSql+="  select co_itm,nd_stkact from tbm_inv where co_emp=0  ) as a  ,  ( ";
                strSql+=" select co_itm as coitm, sum(nd_stkact) from tbm_invbod where co_emp=0 group by co_itm ";
                strSql+=" ) as b where b.coitm=a.co_itm ) as x where x.nd_stkact <> sum  ) AS a where tbm_inv.co_emp=0 and tbm_inv.co_itm=a.coitm; ";
                strSql+=" update tbm_inv set nd_stkact=a.sum FROM ( select * from(  select * from(   select co_itm,nd_stkact from tbm_inv where co_emp=1 ";
                strSql+="  ) as a , (   select co_itm as coitm, sum(nd_stkact) from tbm_invbod where co_emp=1 group by co_itm ";
                strSql+=" ) as b where b.coitm=a.co_itm ) as x where x.nd_stkact <> sum  ";
                strSql+=" ) AS a where tbm_inv.co_emp=1 and tbm_inv.co_itm=a.coitm ; ";
                strSql+=" update tbm_inv set nd_stkact=a.sum FROM ( select * from(  select * from( ";
                strSql+="  select co_itm,nd_stkact from tbm_inv where co_emp=2  ) as a ,  ";
                strSql+=" (  select co_itm as coitm, sum(nd_stkact) from tbm_invbod where co_emp=2 group by co_itm ";
                strSql+=" ) as b where b.coitm=a.co_itm ) as x where x.nd_stkact <> sum ";
                strSql+=" ) AS a where tbm_inv.co_emp=2 and tbm_inv.co_itm=a.coitm ; ";
                strSql+=" update tbm_inv set nd_stkact=a.sum FROM ( select * from(  select * from(  ";
                strSql+="  select co_itm,nd_stkact from tbm_inv where co_emp=3  ) as a  ,  ";
                strSql+=" (  select co_itm as coitm, sum(nd_stkact) from tbm_invbod where co_emp=3 group by co_itm ";
                strSql+=" ) as b where b.coitm=a.co_itm ) as x where x.nd_stkact <> sum  ";
                strSql+=" ) AS a where tbm_inv.co_emp=3 and tbm_inv.co_itm=a.coitm ; ";
                strSql+=" update tbm_inv set nd_stkact=a.sum FROM ( select * from(  select * from(  ";
                strSql+="  select co_itm,nd_stkact from tbm_inv where co_emp=4  ) as a ,  ";
                strSql+=" ( select co_itm as coitm, sum(nd_stkact) from tbm_invbod where co_emp=4 group by co_itm ) as b where b.coitm=a.co_itm";
                strSql+=" ) as x where x.nd_stkact <> sum ) AS a where tbm_inv.co_emp=4 and tbm_inv.co_itm=a.coitm; ";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
        }
        catch (java.sql.SQLException e) {
             
            System.err.println("ERROR: " + e);
            blnRes=false;
            
        } catch (Exception e) {
             
            System.err.println("ERROR: " + e);
            blnRes=false;
        }  
        return blnRes;
    }
    
    
    private ArrayList arlDatBod;
    
    
    private boolean querysIngrid(java.sql.Connection conn){
        boolean blnRes=false;
        int intCodEmp=0, intCodBod=0;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            if(conn!=null){
                System.out.println("Paso1");
                stmLoc=conn.createStatement();
                for(int i=0;i<3;i++){
                    switch (i)
                    {
                        case 0: intCodEmp=1; break;
                        case 1: intCodEmp=2; break;
                        case 2: intCodEmp=4; break;
                    }
                    arlDatBod = new ArrayList();
                    strSql="";
                    strSql+=" SELECT co_bod ";
                    strSql+=" FROM tbm_bod WHERE co_emp="+intCodEmp+ " AND st_reg='A' ORDER BY co_bod";
                    rstLoc=stmLoc.executeQuery(strSql);
                    while(rstLoc.next()){
                        arlDatBod.add(rstLoc.getInt("co_bod"));
                    }
                    rstLoc.close();
                    rstLoc=null;
                    for(int r=0;r<arlDatBod.size();r++){
                        intCodBod=Integer.parseInt(arlDatBod.get(r).toString());
                        System.out.println("EMPRESA " + intCodEmp + " BODEGA " + intCodBod);
                        strSql="";
                        strSql+=" update tbm_invbod set nd_stkact=a.mov FROM ( ";
                        strSql+=" select * from ( ";
                        strSql+="       select w.co_itm ";
                        strSql+="              , CASE WHEN x.mov IS NULL THEN 0 ELSE x.mov END AS mov ";
                        strSql+="            , CASE WHEN y.sum IS NULL THEN 0 ELSE y.sum END AS sum";
                        strSql+="       from ( ";
                        strSql+="           select c.co_itm from tbm_inv as c ";
                        strSql+="                   where  c.st_reg not in ('I','E','X') ";
                        strSql+="               and c.co_emp="+intCodEmp+" and c.st_ser='N' ";
                        strSql+="            group by c.co_itm ";
                        strSql+="       ) as  w";
                        strSql+="   LEFT OUTER JOIN( ";
                        strSql+="       select sum(b.nd_can) as mov,b.co_itm from tbm_cabmovinv as a ";
                        strSql+="       inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)   ";
                        strSql+="       inner join tbm_inv as c on(b.co_emp=c.co_emp and b.co_itm=c.co_itm) ";
                        strSql+="       where  a.st_reg not in ('I','E','X')    and  ( a.st_tipdev='C' or  a.st_tipdev is null ) ";
                        strSql+="       and a.co_emp="+intCodEmp+" and c.st_ser='N'   and  b.co_bod="+intCodBod+"   ";
                        strSql+="       group by b.co_itm ";
                        strSql+="   ) as  x ";
                        strSql+="   ON w.co_itm=x.co_itm ";
                        strSql+="   LEFT OUTER JOIN( ";
                        strSql+="       select  sum(nd_stkact) AS sum,co_itm as coitm from tbm_invbod where co_emp="+intCodEmp+" and co_bod="+intCodBod+"  ";
                        strSql+="       group by co_itm ";
                        strSql+="   ) as y  ";
                        strSql+="   ON w.co_itm=y.coitm  ";
                        strSql+=" ) as x ";
                        strSql+=" WHERE x.mov<>x.sum  ";
			strSql+=" ) AS a  ";
                        strSql+=" WHERE tbm_invbod.co_emp="+intCodEmp+" and tbm_invbod.co_bod="+intCodBod+" and tbm_invbod.co_itm=a.co_itm; ";
                	 stmLoc.executeUpdate(strSql);
		    }
                }
                stmLoc.close();
                stmLoc=null;
                blnRes=true; // OK OK OK OK 
            }
        }
        catch (java.sql.SQLException e) {
             
            System.err.println("ERROR: " + e);
            blnRes=false;
            
        } catch (Exception e) {
             
            System.err.println("ERROR: " + e);
            blnRes=false;
        }  
        return blnRes;
    }
    
    
}