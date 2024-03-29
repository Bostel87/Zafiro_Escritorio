/*
 * ZafCon03.java
 *
 * Created on 24 de enero de 2006, 11:06
 */

package Contabilidad.ZafCon17;

import java.sql.*;
//para ireports
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperCompileManager;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author  jsalazar
 */
public class ZafCon17 extends javax.swing.JInternalFrame {
     Librerias.ZafUtil.ZafUtil objUti;
     Librerias.ZafParSis.ZafParSis objZafParSis;
     private Librerias.ZafDate.ZafDatePicker txtFecIni,txtFecFin;
     private String strSQL,strAux;
     private String strPeriodoDesde = "";
     private String strPeriodoHasta = "";  
     private final String VERSION = " v0.2";     
     
    /** Creates new form ZafCon03 */
     public ZafCon17(Librerias.ZafParSis.ZafParSis obj) {
        initComponents();
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objUti = new Librerias.ZafUtil.ZafUtil();
        this.setTitle(objZafParSis.getNombreMenu()+VERSION);
        txtCodCta.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodAlt.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomCta.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipCta.setVisible(false);
        EnabledBoxCtas(true);
        String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
        //Fecha Inicial
        txtFecIni = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
        txtFecIni.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecIni.setText(strFecSis);
        panCen.add(txtFecIni);
        txtFecIni.setBounds(120, 25, 92, 20);        
        //Fecha Final
        txtFecFin = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
        txtFecFin.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecFin.setText(strFecSis);
        panCen.add(txtFecFin);
        txtFecFin.setBounds(350, 25, 92, 20);                
        this.pack();
        this.setBounds(10,10, 625,295);
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }

    }
    
        
    private void MsgInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro.- Contabilidad";            
            obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    private void MsgError(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro.- Contabilidad";            
            obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panCen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panCta = new javax.swing.JPanel();
        txtCodCta = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTipCta = new javax.swing.JTextField();
        panBut = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        panCen.setLayout(null);

        jLabel1.setText("Periodo Inicial");
        panCen.add(jLabel1);
        jLabel1.setBounds(30, 30, 80, 15);

        jLabel2.setText("Periodo Final");
        panCen.add(jLabel2);
        jLabel2.setBounds(250, 30, 80, 15);

        panCta.setLayout(null);

        panCta.setBorder(new javax.swing.border.TitledBorder("Cuenta"));
        txtCodCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaActionPerformed(evt);
            }
        });

        panCta.add(txtCodCta);
        txtCodCta.setBounds(40, 40, 60, 21);

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });

        panCta.add(txtCodAlt);
        txtCodAlt.setBounds(100, 40, 90, 21);

        txtNomCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaActionPerformed(evt);
            }
        });

        panCta.add(txtNomCta);
        txtNomCta.setBounds(190, 40, 310, 21);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });

        panCta.add(butCta);
        butCta.setBounds(500, 40, 20, 20);

        jLabel3.setText("Codigo Cuenta");
        panCta.add(jLabel3);
        jLabel3.setBounds(100, 20, 110, 15);

        jLabel4.setText("Cod. Sist.");
        panCta.add(jLabel4);
        jLabel4.setBounds(40, 20, 60, 15);

        jLabel5.setText("Descripcion");
        panCta.add(jLabel5);
        jLabel5.setBounds(270, 20, 80, 15);

        panCta.add(txtTipCta);
        txtTipCta.setBounds(10, 40, 20, 21);

        panCen.add(panCta);
        panCta.setBounds(30, 60, 550, 80);

        getContentPane().add(panCen);
        panCen.setBounds(0, 10, 640, 160);

        panBut.setLayout(null);

        butAce.setText("Consultar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });

        panBut.add(butAce);
        butAce.setBounds(170, 10, 90, 30);

        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        panBut.add(butCer);
        butCer.setBounds(270, 10, 90, 30);

        getContentPane().add(panBut);
        panBut.setBounds(30, 190, 550, 50);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-624)/2, (screenSize.height-294)/2, 624, 294);
    }//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_butCerActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:
        if (verificar()){
            BuscarRegistros();
        }
    }//GEN-LAST:event_butAceActionPerformed

    private void txtNomCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaActionPerformed
        // TODO add your handling code here:
        FndCta(txtNomCta.getText(),3);
    }//GEN-LAST:event_txtNomCtaActionPerformed

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodAlt.getText(),2);
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCta.getText(),1);
    }//GEN-LAST:event_txtCodCtaActionPerformed

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        // TODO add your handling code here:
        FndCta("",0);
    }//GEN-LAST:event_butCtaActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panCta;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtNomCta;
    private javax.swing.JTextField txtTipCta;
    // End of variables declaration//GEN-END:variables

    private boolean verificar(){
        int intFecIni[] = txtFecIni.getFecha(txtFecIni.getText());        
	int intFecFin[] = txtFecFin.getFecha(txtFecFin.getText());

        if (intFecIni[2]!=intFecFin[2]){
            MsgError("Las fechas no pueden ser de periodos diferentes.");
            return false;            
        }
        if (intFecIni[1]>intFecFin[1]){
            MsgError("La fecha final no puede ser menor que la fecha inicial.");
            return false;
        }   
        if (txtTipCta.getText().equals("")){
            MsgError("Registre la cuenta a consultar.");
            return false;            
        }
        return true;
    }
    private String getFiltroCta(){
        String strFiltro = "";
        strAux = "";
        try{
            java.sql.Connection con=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    java.sql.Statement stm=con.createStatement(); 
                    String sSQL = "";
                    sSQL = " Select tx_niv1,tx_niv2,tx_niv3,tx_niv4,tx_niv5,tx_niv6 " +
                           " from tbm_placta where co_emp = " + objZafParSis.getCodigoEmpresa() +" and st_reg='A' and co_cta =" + (txtCodCta.getText().equals("")?"0": txtCodCta.getText())+
                           " Order by co_cta";
                    java.sql.ResultSet rst  = stm.executeQuery(sSQL);                    
                    if (rst.next()){                                                                        
                        for (int i=1;i<=6;i++){
                            if (rst.getString("tx_niv"+i)!=null){
                              if (!rst.getString("tx_niv"+i).trim().equals("")){
                                if (!strAux.equals("")) strAux += ",";
                                strFiltro += " and tx_niv"+i +"='"+rst.getString("tx_niv"+i)+"'";
                                strAux    += " tx_niv" + i ;
                              }
                            }
                        }                                
                    }
                    rst.close();
                    stm.close();
                    con.close();
                }
            }catch(java.sql.SQLException Evt){ 
                con.close();
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(this, Evt);
        }            
        return strFiltro;
    }
    
    private void BuscarRegistros(){
        
        
        try{
            java.sql.Connection con=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            String strFechaInicial = "'"+ objUti.formatearFecha(txtFecIni.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'";
            String strFechaFinal   = "'"+ objUti.formatearFecha(txtFecFin.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'";
            int intFecIni[] = txtFecIni.getFecha(txtFecIni.getText());        
            int intFecFin[] = txtFecFin.getFecha(txtFecFin.getText());
            if ((intFecIni[1]-1)>0){
                strPeriodoDesde = "" + intFecIni[2] + "01";
                strPeriodoHasta = "" + intFecFin[2] + (intFecFin[1]<10?"0"+intFecFin[1]:""+intFecFin[1]);
            }else{
                strPeriodoDesde = "" + intFecIni[2] + "00" ;
                strPeriodoHasta = "" + intFecFin[2] + "00";        
            }
            double dblSaldo=0,dblSaldoAnt=0;
            try{
                if (con!=null){
                    Statement stm = con.createStatement();
                    ResultSet rst;
                    strSQL = " Select sum(nd_salcta) as saldo "+
                             " from tbm_placta as pla left outer join tbm_salcta as sal on (pla.co_emp = sal.co_emp and pla.co_cta = sal.co_cta) " +
                             " where pla.co_emp = " + objZafParSis.getCodigoEmpresa() +" and pla.co_cta=" + txtCodCta.getText() + " and (co_per>="+strPeriodoDesde+" and co_per<="+strPeriodoHasta+") " ;
//                    System.out.println (strSQL ); 
                    rst = stm.executeQuery(strSQL);
                    if (rst.next()){
                        dblSaldoAnt = rst.getDouble("saldo");                        
                    }
                    rst.close();
                    if ((intFecIni[0]-1)>0){
                        strPeriodoDesde = "" + intFecIni[2] +"/"+ (intFecIni[1]<10?"0"+intFecIni[1]:""+ intFecIni[1] )+"/01";
                        strPeriodoHasta = "" + intFecIni[2] +"/"+ (intFecIni[1]<10?"0"+intFecIni[1]:""+ intFecIni[1] )+"/"+ (intFecIni[0]-1<10?"0"+(intFecIni[0]-1):""+ (intFecIni[0]-1));
                        strSQL = " Select sum(nd_mondeb-nd_monhab) as saldo "+
                                 " From tbm_cabdia as cab " +
                                 " left outer join tbm_detdia as det on (cab.co_emp = det.co_emp and cab.co_loc = det.co_loc and cab.co_tipdoc = det.co_tipdoc and cab.co_dia=det.co_dia) " +                                
                                 " left outer join tbm_placta  as plan  on(plan.co_emp = det.co_emp  and plan.co_cta=det.co_cta)  " +
                                 " Where cab.co_emp ="+objZafParSis.getCodigoEmpresa()+" and cab.st_reg ='A' and plan.tx_tipcta='D' and cab.fe_dia BETWEEN  '"+strPeriodoDesde+"' AND '"+strPeriodoHasta+"' and det.co_cta=" + txtCodCta.getText();
//                        System.out.println (strSQL ); 
                        rst = stm.executeQuery(strSQL);
                        if (rst.next()){
                            dblSaldoAnt += rst.getDouble("saldo");                        
                        }
                        rst.close();    
                    }
                    strSQL = " Select sum(nd_mondeb-nd_monhab) as saldo"+
                             " From tbm_cabdia as cab " +
                             " left outer join tbm_detdia as det on (cab.co_emp = det.co_emp and cab.co_loc = det.co_loc and cab.co_tipdoc = det.co_tipdoc and cab.co_dia=det.co_dia) " +                                
                             " left outer join tbm_placta  as plan  on(plan.co_emp = det.co_emp  and plan.co_cta=det.co_cta)  " +
                             " Where cab.co_emp ="+objZafParSis.getCodigoEmpresa() +" and det.co_cta=" +txtCodCta.getText()+" and cab.st_reg ='A' and plan.tx_tipcta='D' and fe_dia BETWEEN "+strFechaInicial+" AND "+strFechaFinal +
                             " Group by det.co_cta";
//                    System.out.println (strSQL ); 
                    rst = stm.executeQuery(strSQL);
                    if (rst.next()){
                        dblSaldo = rst.getDouble("saldo");                        
                    }
                    rst.close();
                    JasperDesign jasperDesign = JRXmlLoader.load("C://Zafiro//Reportes_impresos//Rpt_ZafCon17.jrxml");
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    Map parameters = new HashMap();
                    parameters.put("co_emp", new Integer(objZafParSis.getCodigoEmpresa()));
                    parameters.put("tx_nomemp", objZafParSis.getNombreEmpresa());
                    parameters.put("fecha_rep", objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos()));
                    parameters.put("fecha_ini", strFechaInicial);
                    parameters.put("fecha_fin", strFechaFinal);
                    parameters.put("tx_codcta", txtCodAlt.getText());
                    parameters.put("tx_nomcta", txtNomCta.getText());
                    parameters.put("co_codcta", txtCodCta.getText());
//                    parameters.put("nd_saldoant", new Double(dblSaldoAnt));
//                    parameters.put("nd_saldoacum", new Double(objUti.redondear(dblSaldoAnt+dblSaldo,objZafParSis.getDecimalesMostrar())));
                    JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);

                    //para vista preliminar
                    JasperViewer.viewReport(report, false);                
                                                                
                    }
                    con.close();        
//                    MsgInf("Consulta realizada con exito");
                
            }catch(java.sql.SQLException Evt){ 
                con.close();
                objUti.mostrarMsgErr_F1(this, Evt);
            }
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(this, Evt);
        }            
    }
    
    private void EnabledBoxCtas(boolean blnEstado){
        txtCodCta.setEnabled(blnEstado);
        txtCodAlt.setEnabled(blnEstado);
        txtNomCta.setEnabled(blnEstado);
        butCta.setVisible(blnEstado);   
    }
   
    private void FndCta(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Alias,Descripcion,Debe/Haber,Tipo","co_cta , tx_codcta,tx_deslar , tx_natcta,tx_tipcta",
             "select co_cta, tx_codcta,tx_deslar, tx_natcta, tx_tipcta from tbm_placta  " +
                " where   co_emp = " + objZafParSis.getCodigoEmpresa()+ " and tx_tipcta='D' and st_reg='A' ",strBusqueda, 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFnd.setTitle("Listado Cuentas");
        
        switch (TipoBusqueda)
        {
            case 1:                                        
                 objFnd.setSelectedCamBus(0);
                if(!objFnd.buscar("co_cta = " + strBusqueda))
                    objFnd.show();
                 break;
            case 2:
                 objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_codcta = '" + strBusqueda+"'"))
                    objFnd.show();                 
                 break;
            case 3:
                 objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_deslar = '" + strBusqueda+"'"))
                    objFnd.show();                 
                 break;
            default:
                objFnd.show();
                break;             
         }
        if(!objFnd.GetCamSel(1).equals("")){
            txtCodCta.setText(objFnd.GetCamSel(1).toString());
            txtCodAlt.setText(objFnd.GetCamSel(2).toString());
            txtNomCta.setText(objFnd.GetCamSel(3).toString());
            txtTipCta.setText(objFnd.GetCamSel(5).toString());
        }
    }   
}
