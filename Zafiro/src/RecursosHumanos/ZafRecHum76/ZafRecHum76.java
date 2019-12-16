package RecursosHumanos.ZafRecHum76;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafVenFun.ZafVenFun;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import com.tuval.utilities.archivos.ArchivosTuval;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Recursos humanos - IESS - Generación de archivos para subir al IESS...
 * @author  Roberto Flores
 * Guayaquil 18/01/2014
 */
public class ZafRecHum76 extends javax.swing.JInternalFrame
{
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;  
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoOfi;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private ZafPerUsr objPerUsr;
    private String strVersion="v1.0";
    
    private boolean blnExiInfoFal=false;
        
    /** Crea una nueva instancia de la clase ZafRecHum42. */
    public ZafRecHum76(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);

            butCer.setVisible(false);
            
//            if(objPerUsr.isOpcionEnabled(3263)){
//                butCon.setVisible(true);
//            }

//            if(objPerUsr.isOpcionEnabled(3264)){
//                butCer.setVisible(true);
//            }
            
            cargarAños();
            
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
    }

    private boolean cargarAños(){
        boolean blnRes=true;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        String strSQL="";

        try{
            con =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){ 
            
                stmLoc=con.createStatement();
            
                strSQL="select distinct ne_ani from tbm_datGenIngEgrMenTra order by ne_ani desc";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
                }
            }
        }catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{rstLoc.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
            try{con.close();}catch(Throwable ignore){}
        }
        return blnRes;
  } 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnDiaNLab = new javax.swing.JButton();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setTitle("Título de la ventana");
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setAutoscrolls(true);
        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panFil.setLayout(null);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panCab.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 60, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panCab.add(cboPerMes);
        cboPerMes.setBounds(210, 60, 100, 20);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel9.setText("Período:"); // NOI18N
        panCab.add(jLabel9);
        jLabel9.setBounds(10, 60, 110, 20);

        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 100);

        jButton2.setText("4.- Aviso de variación de sueldo por extras");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panFil.add(jButton2);
        jButton2.setBounds(0, 100, 680, 23);

        btnDiaNLab.setText("9.- Aviso de días no laborados");
        btnDiaNLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDiaNLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiaNLabActionPerformed(evt);
            }
        });
        panFil.add(btnDiaNLab);
        btnDiaNLab.setBounds(0, 120, 680, 23);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
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

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void btnDiaNLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiaNLabActionPerformed
        // TODO add your handling code here:
        try{
            if(validarCamObl()){
                ZafVenFun zafVenFun = new ZafVenFun(objParSis, objUti);
                FileNameExtensionFilter objFilNamExt=new FileNameExtensionFilter("Archivos TXT", "txt");
                String strArc = zafVenFun.directorioArchivo(objFilNamExt);
                if(strArc==null){
                      mostrarMsgErr("NO SE PUDO GENERAR EL ARCHIVO");
                  }else{
                    if(generaArchivoAvisoDiasNoLaborados(strArc)){
                        mostrarMsgInf("Archivo generado correctamente");
                    }else{
                        mostrarMsgInf("Archivo no fue generado correctamente");
                    }
                }
            }
        }catch (FileNotFoundException ex) {
            Logger.getLogger(ZafRecHum76.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZafRecHum76.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
            Logger.getLogger(ZafRecHum76.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        String strDir = directorioArchivo
    }//GEN-LAST:event_btnDiaNLabActionPerformed

    private boolean generaArchivoAvisoVarSueExt(String strArc) throws FileNotFoundException, IOException{
        boolean blnRes=true;
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.Statement stmAux = null;
        java.sql.ResultSet rst = null;
        java.sql.ResultSet rstAux = null;
        String strSql="";
        String strDlm=";";
        File archivoDiaNLab = null;
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                stmAux=con.createStatement();
                archivoDiaNLab = new File(strArc);
                archivoDiaNLab.delete();
                ArchivosTuval logDiaNLab = new ArchivosTuval(strArc);
                String strLog = new String("windows-1252");
                strSql+="SELECT a.co_emp , a.co_loc , a.co_tipdoc , a.co_doc , b.tx_ruc , b.tx_nom as empresa, c.tx_secdoc FROM tbm_cabrolpag a"+"\n";
                strSql+="INNER JOIN tbm_emp b ON (b.co_emp=a.co_emp)"+"\n";
                strSql+="INNER JOIN tbm_loc c ON (c.co_emp=a.co_emp AND c.co_loc=a.co_loc)"+"\n";
                strSql+="WHERE a.co_emp = "+objParSis.getCodigoEmpresa()+" AND a.co_loc = "+objParSis.getCodigoLocal()+" AND a.co_tipdoc = 192 "+"\n";
                strSql+="AND a.ne_ani = "+cboPerAAAA.getSelectedItem().toString()+" AND a.ne_mes = "+cboPerMes.getSelectedIndex() +"\n";
                strSql+="AND a.ne_per = 2 AND a.st_reg = 'A'";
                rst=stm.executeQuery(strSql);
                if(rst.next()){
                    strSql="";
                    strSql+="SELECT b.co_emp,a.co_tra,(c.tx_ape || ' ' || c.tx_nom) as empleado,c.tx_ide as cedula,sum(nd_valrub) as nd_valHorExt "+"\n";
                    strSql+="FROM tbm_detrolpag a "+"\n";
                    strSql+="INNER JOIN tbm_traemp b ON (b.co_emp=a.co_emp AND b.co_tra=a.co_tra) "+"\n";
                    strSql+="INNER JOIN tbm_tra c ON (c.co_tra=a.co_tra) "+"\n";
                    strSql+="WHERE a.co_emp = "+rst.getInt("co_emp") +"\n";
                    strSql+="AND a.co_loc = "+rst.getInt("co_loc") +"\n";
                    strSql+="AND a.co_tipdoc = "+rst.getInt("co_tipdoc") +"\n";
                    strSql+="AND a.co_doc = "+rst.getInt("co_doc") +"\n";
                    strSql+="AND a.co_rub in (2,3) "+"\n";
                    strSql+="GROUP BY  b.co_emp , a.co_tra , empleado , cedula "+"\n";
                    strSql+="ORDER BY empleado "+"\n";
                    
                    rstAux=stmAux.executeQuery(strSql);
                    
                    while(rstAux.next()){
                        if(rstAux.getDouble("nd_valHorExt")>0){
                            strLog=rst.getString("tx_ruc")+strDlm+rst.getString("tx_secdoc")+strDlm+cboPerAAAA.getSelectedItem().toString()+strDlm;
                            String strMesAct=String.valueOf(cboPerMes.getSelectedIndex());
                            if(strMesAct.length()==1){
                                strMesAct="0"+strMesAct;
                            }
    //                        String strAux[]=rst.getString("fe_des").split("-");
//                            String strFeIniDiaNLabTra=rst.getString("fe_des").replace("-", "");
                            strLog+=strMesAct+strDlm+"INS"+strDlm+rstAux.getString("cedula")+strDlm+rstAux.getDouble("nd_valHorExt")+strDlm+"O";
                            System.out.println(strLog);
                            logDiaNLab.println(strLog);
                        }
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }finally{
            try{rst.close();rst=null;}catch(Throwable ignore){}
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        
        return blnRes;
    }
    
    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamObl(){
//        String strNomCam = null;
        boolean blnOk = true;

        if(cboPerAAAA.getSelectedItem().toString().compareTo("Año")==0){
            cboPerAAAA.requestFocus();
            cboPerAAAA.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Año</FONT> no ha sido seleccionado.<BR>Seleccione un año y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(cboPerMes.getSelectedItem().toString().compareTo("Mes")==0){
            cboPerMes.requestFocus();
            cboPerMes.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Mes</FONT> no ha sido seleccionado.<BR>Seleccione un mes y vuelva a intentarlo.</HTML>");
            return false;
        }

        return blnOk;
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            if(validarCamObl()){
                ZafVenFun zafVenFun = new ZafVenFun(objParSis, objUti);
                FileNameExtensionFilter objFilNamExt=new FileNameExtensionFilter("Archivos TXT", "txt");
                String strArc = zafVenFun.directorioArchivo(objFilNamExt);
                if(strArc==null){
                      mostrarMsgErr("NO SE PUDO GENERAR EL ARCHIVO");
                  }else{
                    if(generaArchivoAvisoVarSueExt(strArc)){
                        mostrarMsgInf("Archivo generado correctamente");
                    }else{
                        mostrarMsgInf("Archivo no fue generado correctamente");
                    }
                }
            }
        }catch (FileNotFoundException ex) {
            Logger.getLogger(ZafRecHum76.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZafRecHum76.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
            Logger.getLogger(ZafRecHum76.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
   
    private boolean generaArchivoAvisoDiasNoLaborados(String strArc) throws FileNotFoundException, IOException{
        boolean blnRes=true;
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.Statement stmAux = null;
        java.sql.ResultSet rst = null;
        java.sql.ResultSet rstAux = null;
        String strSql="";
        String strDlm=";";
        File archivoDiaNLab = null;
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                stmAux=con.createStatement();
                archivoDiaNLab = new File(strArc);
                archivoDiaNLab.delete();
                ArchivosTuval logDiaNLab = new ArchivosTuval(strArc);
                String strLog = new String("windows-1252");
                strSql+="select a.co_emp , a.tx_ruc , a.tx_nom , b.tx_secdoc , c.fe_des from tbm_emp  a"+" \n";
                strSql+="INNER JOIN tbm_loc b ON (b.co_emp=a.co_emp)"+" \n";
                strSql+="INNER JOIN tbm_feccorrolpag c ON (c.co_emp=a.co_emp AND ne_ani ="+cboPerAAAA.getSelectedItem().toString()+"  AND ne_mes = "+cboPerMes.getSelectedIndex()+" AND ne_per=1)"+" \n";
                strSql+="WHERE a.co_emp = "+objParSis.getCodigoEmpresa()+" \n";
                strSql+="AND b.co_loc = "+objParSis.getCodigoLocal();
                rst=stm.executeQuery(strSql);
                if(rst.next()){
                    strSql="";
                    strSql+="SELECT co_emp,co_tra,empleado,cedula,sum(ne_numdiafal) as ne_numdiafaltra FROM ( "+"\n";
                    strSql+="SELECT b.co_emp,a.co_tra,(c.tx_ape || ' ' || c.tx_nom) as empleado,c.tx_ide as cedula,(COUNT(fe_dia)*2) as ne_numdiafal "+"\n";
                    strSql+="FROM tbm_cabconasitra a "+"\n";
                    strSql+="INNER JOIN tbm_traemp b ON (b.co_tra=a.co_tra) "+"\n";
                    strSql+="INNER JOIN tbm_tra c ON (c.co_tra=a.co_tra) "+"\n";
                    strSql+="WHERE b.co_emp="+objParSis.getCodigoEmpresa()+"\n";
                    strSql+="AND b.st_reg='A' "+"\n";
                    strSql+="AND a.fe_dia BETWEEN (SELECT fe_des FROM tbm_feccorrolpag WHERE co_emp=b.co_emp AND ne_ani="+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes="+cboPerMes.getSelectedIndex()+" AND ne_per=1) "+"\n";
                    strSql+="AND (SELECT fe_has FROM tbm_feccorrolpag WHERE co_emp=b.co_emp AND ne_ani="+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes="+cboPerMes.getSelectedIndex()+" AND ne_per=2) "+"\n";
                    strSql+="AND (a.ho_ent IS NULL AND a.ho_sal IS NULL) "+"\n";
                    strSql+="AND NOT (EXTRACT(DOW FROM fe_dia ) in (6,7)) "+"\n";
                    strSql+="AND a.st_jusfal IS NULL "+"\n";
                    strSql+="AND b.co_hor IS NOT NULL "+"\n";
                    strSql+="GROUP BY b.co_emp , a.co_tra , empleado , cedula "+"\n";
                    strSql+="UNION "+"\n";
                    strSql+="SELECT b.co_emp,a.co_tra,(c.tx_ape || ' ' || c.tx_nom) as empleado,c.tx_ide as cedula,COUNT(fe_dia) as ne_numdiafal "+"\n";
                    strSql+="FROM tbm_cabconasitra a "+"\n";
                    strSql+="INNER JOIN tbm_traemp b ON (b.co_tra=a.co_tra) "+"\n";
                    strSql+="INNER JOIN tbm_tra c ON (c.co_tra=a.co_tra) "+"\n";
                    strSql+="WHERE b.co_emp="+objParSis.getCodigoEmpresa()+"\n";
                    strSql+="AND b.st_reg='A' "+"\n";
                    strSql+="AND a.fe_dia BETWEEN (SELECT fe_des FROM tbm_feccorrolpag WHERE co_emp=b.co_emp AND ne_ani="+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes="+cboPerMes.getSelectedIndex()+" AND ne_per=1) "+"\n";
                    strSql+="AND (SELECT fe_has FROM tbm_feccorrolpag WHERE co_emp=b.co_emp AND ne_ani="+cboPerAAAA.getSelectedItem().toString()+" AND ne_mes="+cboPerMes.getSelectedIndex()+" AND ne_per=2) "+"\n";
                    strSql+="AND (a.ho_ent IS NULL AND a.ho_sal IS NULL) "+"\n";
                    strSql+="AND NOT (EXTRACT(DOW FROM fe_dia ) IN (6,7)) "+"\n";
                    strSql+="AND a.st_jusfal LIKE 'P' "+"\n";
                    strSql+="AND b.co_hor IS NOT NULL "+"\n";
                    strSql+="GROUP BY b.co_emp , a.co_tra , empleado , cedula "+"\n";
                    strSql+=") AS x "+"\n";
                    strSql+="GROUP BY x.co_emp , x.co_tra , empleado , cedula "+"\n";
                    strSql+="ORDER BY empleado";
                    
                    rstAux=stmAux.executeQuery(strSql);
                    
                    while(rstAux.next()){
                        strLog=rst.getString("tx_ruc")+strDlm+rst.getString("tx_secdoc")+strDlm+cboPerAAAA.getSelectedItem().toString()+strDlm;
                        String strMesAct=String.valueOf(cboPerMes.getSelectedIndex());
                        if(strMesAct.length()==1){
                            strMesAct="0"+strMesAct;
                        }
                        String strFeIniDiaNLabTra=rst.getString("fe_des").replace("-", "");
                        strLog+=strMesAct+strDlm+"MND"+strDlm+rstAux.getString("cedula")+strDlm+strFeIniDiaNLabTra+strDlm+rstAux.getString("ne_numdiafaltra");
                        System.out.println(strLog);
                        logDiaNLab.println(strLog);
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }finally{
            try{rst.close();rst=null;}catch(Throwable ignore){}
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        
        return blnRes;
    }
        
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnDiaNLab;
    private javax.swing.JButton butCer;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " " + strVersion);
            lblTit.setText(strAux);

             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setBounds(4, 20, 472, 72);

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
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
        /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean validaCampos()
    {
        //Validar el "Año".
        int intPerAAAA=cboPerAAAA.getSelectedIndex();
        if(intPerAAAA<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año</FONT> es obligatorio.<BR>Seleccione el año y vuelva a intentarlo.</HTML>");
            cboPerAAAA.requestFocus();
            return false;
        }
        //Validar el "Mes".
        int intPerMes=cboPerMes.getSelectedIndex();
        if(intPerMes<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes</FONT> es obligatorio.<BR>Seleccione el mes y vuelva a intentarlo.</HTML>");
            cboPerMes.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean existeFaltasPeriodo(){
        return blnExiInfoFal;
    }
}