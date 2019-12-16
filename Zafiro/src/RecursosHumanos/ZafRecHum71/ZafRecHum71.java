package RecursosHumanos.ZafRecHum71;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import com.csvreader.CsvWriter;
import com.tuval.utilities.archivos.ArchivosTuval;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Autorización de pago del décimo tercer sueldo...
 * @author  Roberto Flores
 * 02/12/2013
 */
public class ZafRecHum71 extends javax.swing.JInternalFrame
{
    
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_CODEMP=1;
    private final int INT_TBL_DAT_CODLOC=2;
    private final int INT_TBL_DAT_CODTIPDOC=3;
    private final int INT_TBL_DAT_DESCORTIPDOC=4;
    private final int INT_TBL_DAT_CODDOC=5;
    private final int INT_TBL_DAT_NUMDOC=6;
    private final int INT_TBL_DAT_FECDOC=7;
    private final int INT_TBL_DAT_BUTDTS=8;
    private final int INT_TBL_DAT_CHKAUTRRHH=9;
    private final int INT_TBL_DAT_FECAUTRRHH=10;
    private final int INT_TBL_DAT_CHKAUT=11;
    private final int INT_TBL_DAT_FECAUT=12;
    private final int INT_TBL_DAT_BUTGENARCMRL=13;
    private final int INT_TBL_DAT_FECGENARCMRL=14;
    private final int INT_TBL_DAT_BUTFORMRL=15;
    private final int INT_TBL_DAT_BUTGENARCBCO=16;
    private final int INT_TBL_DAT_FECGENARCBCO=17;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private ZafTblFilCab objTblFilCab;
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private ZafTblCelRenLbl objTblCelRenLblCru, objTblCelRenLblDocRel, objTblCelRenLblNum, objTblCelRenLblCol;
    private ZafTblBus objTblBus;
    
    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;

    private ZafSelFec objSelFec;

    private String strCodMot, strDesLarMot;

    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    
    
    private ZafTblCelRenChk objTblCelRenChkRRHH;
    private ZafTblCelEdiChk objTblCelEdiChkRRHH;
    
    private ZafTblCelRenChk objTblCelRenChkGer;
    private ZafTblCelEdiChk objTblCelEdiChkGer;

    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut    objTblCelRenBut;
    private ZafTblCelRenBut    objTblCelRenButGenArMrl;
    private ZafTblCelRenBut    objTblCelRenButGenForm;
    private ZafTblCelRenBut    objTblCelRenButGenArcBco;
    
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.

    private ZafPerUsr objPerUsr;
    private String strVersion="v1.02";

    private String strCodEmp, strNomEmp;
    
    private ZafVenCon vcoEmp;                                   //Ventana de consulta.
    
    private boolean blnPas2=false;
    private boolean blnPas3=false;
    private boolean blnPas4=false;
    private boolean blnPas5=false;
    
    
    public ZafRecHum71(ZafParSis obj)
    {
        try{
            initComponents();
            this.objParSis=obj;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            
            if(objParSis.getCodigoMenu()==3653){
                if(objPerUsr.isOpcionEnabled(3654)){
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(3655)){
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(3656)){
                    butCer.setVisible(true);
                }
            }else if(objParSis.getCodigoMenu()==3792){
                if(objPerUsr.isOpcionEnabled(3793)){
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(3794)){
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(3795)){
                    butCer.setVisible(true);
                }
            }else if(objParSis.getCodigoMenu()==3810){
                if(objPerUsr.isOpcionEnabled(3811)){
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(3812)){
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(3813)){
                    butCer.setVisible(true);
                }
            }

            if (!configurarFrm())
                exitForm(); 
            
            agregarColTblDat();
            configurarVenConEmp();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panFecCor = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panFecCor.setPreferredSize(new java.awt.Dimension(100, 100));
        panFecCor.setLayout(null);
        jPanel1.add(panFecCor, java.awt.BorderLayout.NORTH);

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(0, 200));
        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 4, 400, 14);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 20, 400, 14);

        lblLoc.setText("Empresa:");
        lblLoc.setToolTipText("Proveedor");
        panFil.add(lblLoc);
        lblLoc.setBounds(14, 48, 100, 20);

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
        txtCodEmp.setBounds(130, 50, 60, 20);

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
        txtNomEmp.setBounds(190, 50, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(440, 50, 20, 20);

        jPanel1.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", jPanel1);

        panRpt.setLayout(new java.awt.BorderLayout());

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(385, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setToolTipText("Cierra la ventana.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
                   /*Permite obtener un log de la tabla tbm_grpvar
 *
 */    
                        
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else{
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicaci�n. */
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

private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
    // TODO add your handling code here:
    if(optTod.isSelected()){
        optFil.setSelected(false);
    }
    else{
        optFil.setSelected(true);
    }
}//GEN-LAST:event_optTodActionPerformed

private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
    // TODO add your handling code here:
    if(optFil.isSelected()){
        optTod.setSelected(false);
    }
    else
        optTod.setSelected(true);
}//GEN-LAST:event_optFilActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

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
    
    private boolean guardarDat(){
        boolean blnRes=false;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocAux = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
      
            if(con!=null){
                
                con.setAutoCommit(false);
                stmLoc=con.createStatement();
                stmLocAux=con.createStatement();
                int intFilSel=tblDat.getSelectedRow();
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    Object obj = tblDat.getValueAt(i, INT_TBL_DAT_LIN);
                    if(obj!=null){
                        
                    if((Boolean)tblDat.getValueAt(i, INT_TBL_DAT_CHKAUTRRHH)==true){//&& tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString()!=null
                        blnPas3=false;
                        blnPas2=true;
                    }else{
                        blnPas3=false;
                    }
                    
                    if(blnPas2){
                    strSql="";
                    strSql+="select  st_autrechum, fe_autrechum \n";
                    strSql+=" from tbm_cabrolpag where co_emp = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n";
                    strSql+=" and co_loc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n";
                    strSql+=" and co_tipdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n";
                    strSql+=" and co_doc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n";
                    strSql+=" and ne_numdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n";
                    System.out.println("veryfiedblnPas2: " + strSql);
                    rstLoc=stmLoc.executeQuery(strSql);
                    while(rstLoc.next()){
                        String strStAutRecHum=rstLoc.getString("st_autrechum");
                        if(strStAutRecHum==null){
                            strSql="";
                            strSql+="update tbm_cabrolpag  set st_autrechum='S' , fe_autrechum = current_timestamp, co_usrautrechum = "+objParSis.getCodigoUsuario()+" ,  \n";
                            strSql+=" tx_comautrechum = " + objUti.codificar(objParSis.getDireccionIP())+" \n";
                            strSql+=" where co_emp = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n";
                            strSql+=" and co_loc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n";
                            strSql+=" and co_tipdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n";
                            strSql+=" and co_doc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n";
                            strSql+=" and ne_numdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n";
                            System.out.println("updateblnPas2: " + strSql);
                            stmLocAux.executeUpdate(strSql);
                            blnRes=true;
                        }
                    }
                }
              }   
             }
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    Object obj = tblDat.getValueAt(i, INT_TBL_DAT_LIN);
                    if(obj!=null){
                        
                    if((Boolean)tblDat.getValueAt(i, INT_TBL_DAT_CHKAUT)==true){//&& tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString()!=null
                        blnPas3=true;
                        blnPas2=false;
                    }else{
                        blnPas3=false;
                    }
                    
                    if(blnPas3){
                        strSql="";
                        strSql+="select  st_autger, fe_autger \n";
                        strSql+=" from tbm_cabrolpag where co_emp = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n";
                        strSql+=" and co_loc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n";
                        strSql+=" and co_tipdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n";
                        strSql+=" and co_doc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n";
                        strSql+=" and ne_numdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n";
                        System.out.println("veryfiedblnPas2: " + strSql);
                        rstLoc=stmLoc.executeQuery(strSql);
                        while(rstLoc.next()){
                            String strStAutGer=rstLoc.getString("st_autger");
                            if(strStAutGer==null){
                                strSql="";
                                strSql+="update tbm_cabrolpag  set st_autger='S' , fe_autger = current_timestamp , co_usrautger = "+objParSis.getCodigoUsuario()+" ,  \n";
                                strSql+=" tx_comautger = " + objUti.codificar(objParSis.getDireccionIP())+" , \n";
                                strSql+=" st_reg = 'A' \n";
                                strSql+=" where co_emp = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n";
                                strSql+=" and co_loc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n";
                                strSql+=" and co_tipdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n";
                                strSql+=" and co_doc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n";
                                strSql+=" and ne_numdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n";
                                System.out.println("updateblnPas2: " + strSql);
                                stmLocAux.executeUpdate(strSql);
                                blnRes=true;

                                //st_reg = 'A' para el asiento de diario
                                strSql="";
                                strSql+="update tbm_cabdia set st_reg = 'A' "+" \n";
                                strSql+="where co_emp =  "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n";
                                strSql+="and co_loc =  "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n";
                                strSql+="and co_tipdoc =  "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n";
                                strSql+="and co_dia =  "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n";
                                strSql+="and tx_numdia =  "+objUti.codificar(tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString())+" \n";
                                System.out.println("updateTbmCabDia: " + strSql);
                                stmLocAux.executeUpdate(strSql);
                                blnRes=true;
                            }
                        }
                    }
                    }
                }
            }
            if(blnRes){
                con.commit();
                objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);
            }
        }catch(java.sql.SQLException Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
        try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
        try{stmLocAux.close();stmLocAux=null;}catch(Throwable ignore){}
        try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
        try{con.close();con=null;}catch(Throwable ignore){}
        }
    return blnRes;
}
    
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        if (objTblMod.isDataModelChanged())
        {
//            if(!verificaUsuario()){
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                        if(guardarDat()){
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                            objThrGUI=null;
                            if (objThrGUI==null){
                                objThrGUI=new ZafThreadGUI();
                                objThrGUI.start();
                            }
                            
                        }
                            
                        else{
                            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
                        }
                }
        }else{
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        //consultarRepEmp();
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        //txtCodEmp.selectAll();
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
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
        //txtNomEmp.transferFocus();
        txtNomEmp.transferFocus();

    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
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
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void habilitarChkBox(boolean blnOptFil, boolean blnOptTod){
        optFil.setSelected(blnOptFil);
        optTod.setSelected(blnOptTod);
    }
    
    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFecCor;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtNomEmp;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarReg()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }            
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        String strSQL="";
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
            arlAncCol.add("100");
            
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
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
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
                    
            strAux=objParSis.getNombreMenu() + " " + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            lblTit.setForeground(Color.BLACK);

            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_BUTDTS,"");
            vecCab.add(INT_TBL_DAT_CHKAUTRRHH,"Autorizar");
            vecCab.add(INT_TBL_DAT_FECAUTRRHH,"Fecha");
            vecCab.add(INT_TBL_DAT_CHKAUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_FECAUT,"Fecha");
            vecCab.add(INT_TBL_DAT_BUTGENARCMRL,"Archivo");
            vecCab.add(INT_TBL_DAT_FECGENARCMRL,"Fec.Gen.Arc.");
            vecCab.add(INT_TBL_DAT_BUTFORMRL,"Formulario");
            vecCab.add(INT_TBL_DAT_BUTGENARCBCO,"Archivo");
            vecCab.add(INT_TBL_DAT_FECGENARCBCO,"Fec.Gen.Arc.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);            
                          
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            
            //Tama�o de las celdas
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_BUTDTS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTRRHH).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECAUTRRHH).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECAUT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTGENARCMRL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECGENARCMRL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTFORMRL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUTGENARCBCO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECGENARCBCO).setPreferredWidth(70);
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUTFORMRL, tblDat);

            tblDat.getTableHeader().setReorderingAllowed(false);                        
            objTblBus=new ZafTblBus(tblDat);

            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            objTblOrd=new ZafTblOrd(tblDat);

            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panFecCor.add(objSelFec);
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
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCru=new ZafTblCelRenLbl();
            objTblCelRenLblCru.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCru.setTipoFormato(objTblCelRenLblCru.INT_FOR_NUM);
            objTblCelRenLblCru.setFormatoNumerico("####",false,true);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setCellRenderer(objTblCelRenLblCru);
            objTblCelRenLblCru=null;

            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTDTS);
            vecAux.add("" + INT_TBL_DAT_CHKAUTRRHH);
            vecAux.add("" + INT_TBL_DAT_CHKAUT);
            vecAux.add("" + INT_TBL_DAT_BUTGENARCMRL);
            vecAux.add("" + INT_TBL_DAT_BUTFORMRL);
            vecAux.add("" + INT_TBL_DAT_BUTGENARCBCO);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTDTS).setCellRenderer(zafTblDocCelRenBut);
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTDTS).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_DAT_BUTDTS:

                         break;
                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {

                         case INT_TBL_DAT_BUTDTS:

                             llamarPantallaDTS( String.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMP))
                                    ,String.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC))
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()
                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()
                                     ,objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()
                                 );
                             
                         break;

                        }
                }}
           });
 
            
            objTblCelRenChkRRHH = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTRRHH).setCellRenderer(objTblCelRenChkRRHH);
            objTblCelEdiChkRRHH=new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTRRHH).setCellEditor(objTblCelEdiChkRRHH);
            objTblCelEdiChkRRHH.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDat.getSelectedRow();
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                strSql+=" select case (st_autrechum is null) when true then false::boolean else true::boolean end as blnAut from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                                strSql+=" and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                                strSql+=" and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                                strSql+=" and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                                strSql+=" and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                                rst=stm.executeQuery(strSql);
                                while(rst.next()){
                                    boolean bln =(Boolean)rst.getBoolean("blnAut"); 
                                    if(!(bln)){//falso no esta autorizado //verdadero esta autorizado
    //                                    objTblCelEdiChkGer.setCancelarEdicion(true);
                                        strBlqIni="";
                                    }else{
                                        strBlqIni="B";
                                    }
                                }
                            }
                        
                        Object objAut = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                        boolean blnAut=(Boolean)objAut;
                        
                        if(blnAut &&strBlqIni.compareTo("B")==0){
                            objTblCelEdiChkRRHH.setCancelarEdicion(true);
                            objTblCelEdiChkGer.setCancelarEdicion(true);
                        }else{
                            if(objPerUsr.isOpcionEnabled(3818) || objPerUsr.isOpcionEnabled(3796) || objPerUsr.isOpcionEnabled(3814)){
                            objTblCelEdiChkGer.setCancelarEdicion(true);
//                            if(con!=null){
//                                con.setAutoCommit(false);
//                                stm=con.createStatement();
//                                strSql+=" select case (st_autrechum is null) when true then false::boolean else true::boolean end as blnAut from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
//                                strSql+=" and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
//                                strSql+=" and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
//                                strSql+=" and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
//                                strSql+=" and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
//                                rst=stm.executeQuery(strSql);
//                                while(rst.next()){
//                                    boolean bln =(Boolean)rst.getBoolean("blnAut"); 
//                                    if(!(bln)){//falso no esta autorizado //verdadero esta autorizado
//    //                                    objTblCelEdiChkGer.setCancelarEdicion(true);
//                                        blnPas2=false;
//                                    }else{
//                                        blnPas2=true;
//                                    }
//                                }
//                            }
                            }else{
                                objTblCelEdiChkRRHH.setCancelarEdicion(true);
                                objTblCelEdiChkGer.setCancelarEdicion(true);
                            }
                        }
                        
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    objTblCelEdiChkGer.setCancelarEdicion(true);
                    if((Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH)==true){
                        blnPas2=true;
                        blnPas3=false;
                    }else{
                        blnPas2=false;
                        blnPas3=false;
                    }
                }
            });
            

            objTblCelRenChkGer = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT).setCellRenderer(objTblCelRenChkGer);
            objTblCelEdiChkGer=new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT).setCellEditor(objTblCelEdiChkGer);
            objTblCelEdiChkGer.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql="";
                    String strBlqIni="";
                    intFilSel=tblDat.getSelectedRow();
                    
                    try{
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                strSql+=" select case (st_autger is null) when true then false::boolean else true::boolean end as blnAut from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                                strSql+=" and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                                strSql+=" and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                                strSql+=" and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                                strSql+=" and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                                rst=stm.executeQuery(strSql);
                                while(rst.next()){
                                    boolean bln =(Boolean)rst.getBoolean("blnAut"); 
                                    if(!(bln)){//falso no esta autorizado //verdadero esta autorizado
    //                                    objTblCelEdiChkGer.setCancelarEdicion(true);
                                        strBlqIni="";
                                    }else{
                                        strBlqIni="B";
                                    }
                                }
                            }
                        
                        Object objAut = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHKAUT);
                        boolean blnAut=(Boolean)objAut;
                        
                        if(blnAut &&strBlqIni.compareTo("B")==0){
                            objTblCelEdiChkRRHH.setCancelarEdicion(true);
                            objTblCelEdiChkGer.setCancelarEdicion(true);
                        }else{
                            if(objPerUsr.isOpcionEnabled(3819) || objPerUsr.isOpcionEnabled(3797) || objPerUsr.isOpcionEnabled(3815)){
                                boolean bln = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                                if(bln){
                                    blnPas2=true;
                                    if(!blnPas2){
                                        objTblCelEdiChkGer.setCancelarEdicion(true);
                                    }else{
                                        blnPas3=true;
                                        
                                    }
                                }else{
                                    objTblCelEdiChkGer.setCancelarEdicion(true);
                                }
                            }else{
                                objTblCelEdiChkGer.setCancelarEdicion(true);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }
                }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     if((Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUT)==true){
                        blnPas3=true;
                        blnPas2=false;
                    }else{
                        blnPas3=false;
                    }
                }
            });
            
//            objTblCelRenBut=new ZafTblCelRenBut();
//            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTGENARCMRL).setCellRenderer(objTblCelRenBut);
            objTblCelRenButGenArMrl = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTGENARCMRL).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUTGENARCMRL, "...") {
                public void butCLick() {
                    int intFilSel = tblDat.getSelectedRow();
//                    blnPas2 = (Boolean)tblDat.getValueAt(intSelFil, INT_TBL_DAT_CHKAUTRRHH);
                    blnPas3 = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUT);
                    if(objPerUsr.isOpcionEnabled(3820) || objPerUsr.isOpcionEnabled(3798) || objPerUsr.isOpcionEnabled(3816)){
                        if(blnPas3){
                            System.out.println("SE REALIZA EL PROCESO DE ARCHIVO CSV");
                            //actualizo fecha de elaboracion archivo mrl.. es la base para los demas permisos
                            java.sql.Connection con = null;
                            java.sql.Statement stm = null;
                            java.sql.Statement stmAux = null;
                            java.sql.ResultSet rst = null;
                            boolean blnRes=false;
                            String strSql="";
                            try{
                                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                                if(con!=null){
                                    con.setAutoCommit(false);
                                    stm=con.createStatement();
                                    stmAux=con.createStatement();
                                    
                                    String strArc = directorioArchivoCSV();
                                            
                                    if(strArc!=null){
                                        
                                        if(generaArchivoDTSMRL(strArc)){
                                        
                                            strSql+=" select fe_genarcmrl as  fe_genarcmrl ";
                                            strSql+=" from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                                            strSql+=" and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                                            strSql+=" and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                                            strSql+=" and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                                            strSql+=" and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                                            rst=stm.executeQuery(strSql);
                                            while(rst.next()){
                                                String strFeGenArcMrl=rst.getString("fe_genarcmrl");
                                                if(strFeGenArcMrl==null){
                                                    strSql="";
                                                    strSql+="update tbm_cabrolpag  set fe_genarcmrl = current_timestamp , co_usrgenarcmrl = "+objParSis.getCodigoUsuario()+" , \n";
                                                    strSql+=" tx_comgenarcmrl = " + objUti.codificar(objParSis.getDireccionIP())+" \n";
                                                    strSql+=" where co_emp = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n";
                                                    strSql+=" and co_loc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n";
                                                    strSql+=" and co_tipdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n";
                                                    strSql+=" and co_doc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n";
                                                    strSql+=" and ne_numdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n";
                                                    System.out.println("updateblnPas2: " + strSql);
                                                    stmAux.executeUpdate(strSql);
                                                    blnRes=true;

                                                    objThrGUI=null;
                                                    if (objThrGUI==null){
                                                        objThrGUI=new ZafThreadGUI();
                                                        objThrGUI.start();
                                                    }

                                                }
                                            }
                                        }else{
                                            mostrarMsgErr("ARCHIVO NO FUE GENERADO");
                                        }
                                    }else{
                                        mostrarMsgErr("NO SE HA ELEGIDO UN DIRECTORIO VALIDO");
                                    }
                                }
                                if(blnRes){
                                    con.commit();
                                    mostrarMsgInf("ARCHIVO GENERADO CORRECTAMENTE");
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                objUti.mostrarMsgErr_F1(ZafRecHum71.this, e);
                            }
                            finally {
                                try{rst.close();rst=null;}catch(Throwable ignore){}
                                try{stm.close();stm=null;}catch(Throwable ignore){}
                                try{stmAux.close();stmAux=null;}catch(Throwable ignore){}
                                try{con.close();con=null;}catch(Throwable ignore){}
                            }
                        }else{
                            mostrarMsgInf("NO TIENE PERMISOS PARA GENERAR EL ARCHIVO PARA EL MRL");
                        }
                    }else{
                        mostrarMsgInf("NO TIENE PERMISOS PARA GENERAR EL ARCHIVO PARA EL MRL");
                    }
                }
            };

            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTFORMRL).setCellRenderer(objTblCelRenBut);
            objTblCelRenChk=null;
            
            
            objTblCelRenButGenArcBco = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTGENARCBCO).setCellRenderer(zafTblDocCelRenBut);
            zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUTGENARCBCO, "...") {
                int intFilSel = tblDat.getSelectedRow();
                public void butCLick() {
                    int intFilSel = tblDat.getSelectedRow();
//                    blnPas2 = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                    blnPas3 = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUT);
                    
                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.Statement stmAux = null;
                    java.sql.Statement stmAux2 = null;
                    java.sql.ResultSet rst = null;
                    java.sql.ResultSet rstAux = null;
                    boolean blnRes=false;
                    String strSql="";
                    try{
                        if(objPerUsr.isOpcionEnabled(3821) || objPerUsr.isOpcionEnabled(3799) || objPerUsr.isOpcionEnabled(3817)){
                            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                            if(con!=null){
                                con.setAutoCommit(false);
                                stm=con.createStatement();
                                stmAux=con.createStatement();
                                stmAux2=con.createStatement();
//                                strSql+=" select fe_genarcmrl as fe_genarcmrl from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
//                                strSql+=" and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
//                                strSql+=" and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
//                                strSql+=" and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
//                                strSql+=" and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
//                                rst=stm.executeQuery(strSql);
//                                while(rst.next()){
//                                    String strFeGenArcMrl=rst.getString("fe_genarcmrl");
//                                    if(strFeGenArcMrl==null){
//                                        blnPas4 =false;
//                                        mostrarMsgInf("NO TIENE PERMISOS PARA GENERAR EL ARCHIVO PARA LA ACREEDITACION BANCARIA");
//                                    }else{
                                        blnPas4=true;
                                        if(blnPas3&&blnPas4){

                                            String strArc = directorioArchivoTXT();
                                            
                                             if(strArc!=null){
                                                 
                                                 if(generaArchivoDSCBAN(strArc)){
                                                
                                                    strSql="";
                                                    strSql+=" select fe_genarcban as  fe_genarcban ";
                                                    strSql+=" from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                                                    strSql+=" and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                                                    strSql+=" and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                                                    strSql+=" and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                                                    strSql+=" and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                                                    rstAux=stmAux.executeQuery(strSql);
                                                        while(rstAux.next()){
                                                            String strFeGenArcBan=rstAux.getString("fe_genarcban");
                                                            if(strFeGenArcBan==null){
                                                                strSql="";
                                                                strSql+="update tbm_cabrolpag  set fe_genarcban = current_timestamp , co_usrgenarcban = "+objParSis.getCodigoUsuario()+" , \n";
                                                                strSql+=" tx_comgenarcban = " + objUti.codificar(objParSis.getDireccionIP())+" \n";
                                                                strSql+=" where co_emp = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n";
                                                                strSql+=" and co_loc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n";
                                                                strSql+=" and co_tipdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n";
                                                                strSql+=" and co_doc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n";
                                                                strSql+=" and ne_numdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n";
                                                                System.out.println("updateblnPas2: " + strSql);
                                                                stmAux2.executeUpdate(strSql);
                                                                blnRes=true;

                                                                objThrGUI=null;
                                                                if (objThrGUI==null){
                                                                    objThrGUI=new ZafThreadGUI();
                                                                    objThrGUI.start();
                                                                }

                                                            }
                                                        }
                                                 }else{
                                                     mostrarMsgErr("ARCHIVO NO FUE GENERADO");
                                                 }
                                             }else{
                                                 mostrarMsgErr("NO SE HA ELEGIDO UN DIRECTORIO VALIDO");
                                             }
                                        }else{
                                            mostrarMsgInf("NO TIENE PERMISOS PARA GENERAR EL ARCHIVO PARA LA ACREEDITACION BANCARIA");
                                        }
//                                    }
//                                }
                            }
                            if(blnRes){
                                con.commit();
                                mostrarMsgInf("ARCHIVO GENERADO CORRECTAMENTE");
                            }
                        }else{
                            mostrarMsgInf("NO TIENE PERMISOS PARA GENERAR EL ARCHIVO PARA LA ACREEDITACION BANCARIA");
                        }
                        
                    }catch(Exception e){
                        e.printStackTrace();
                        objUti.mostrarMsgErr_F1(ZafRecHum71.this, e);
                    }
                    finally {
                        try{rst.close();rst=null;}catch(Throwable ignore){}
                        try{stm.close();stm=null;}catch(Throwable ignore){}
                        try{con.close();con=null;}catch(Throwable ignore){}
                    }
                }
            };
           
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private String directorioArchivoTXT()
    {
        boolean blnRes=true;
        String strArc=null;
        try
        {
            JFileChooser objFilCho=new JFileChooser();
            objFilCho.setDialogTitle("Guardar");
            objFilCho.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(!System.getProperty("os.name").equals("Linux")){
                objFilCho.setCurrentDirectory(new File("C:\\"));
            }else{
                objFilCho.setCurrentDirectory(new File("/tmp"));
            }
            FileNameExtensionFilter objFilNamExt=new FileNameExtensionFilter("Archivos TXT", "txt");
            objFilCho.setFileFilter(objFilNamExt);
            if (objFilCho.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
            {
                strArc=objFilCho.getSelectedFile().getPath();
                //Si no tiene la extensión "txt" agregarsela.
                if (!strArc.toLowerCase().endsWith(".txt"))
                    strArc+=".txt";
            }
            else
            {
                System.out.println("El usuario canceló");
            }
        }
        catch(Exception e)
        {
            System.out.println("Excepción: " + e.toString());
            blnRes=false;
        }
        return strArc;
    } 
    
    private String directorioArchivoCSV()
    {
        boolean blnRes=true;
        String strArc=null;
        try
        {
            JFileChooser objFilCho=new JFileChooser();
            objFilCho.setDialogTitle("Guardar");
            objFilCho.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(!System.getProperty("os.name").equals("Linux")){
                objFilCho.setCurrentDirectory(new File("C:\\"));
            }else{
                objFilCho.setCurrentDirectory(new File("/tmp"));
            }
            FileNameExtensionFilter objFilNamExt=new FileNameExtensionFilter("Archivos CSV", "csv");
            objFilCho.setFileFilter(objFilNamExt);
            if (objFilCho.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
            {
                strArc=objFilCho.getSelectedFile().getPath();
                //Si no tiene la extensión "txt" agregarsela.
                if (!strArc.toLowerCase().endsWith(".csv"))
                    strArc+=".csv";
            }
            else
            {
                System.out.println("El usuario canceló");
            }
        }
        catch(Exception e)
        {
            System.out.println("Excepción: " + e.toString());
            blnRes=false;
        }
        return strArc;
    } 
    
    private boolean agregarColTblDat()
    {
        
        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;

        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();
            
            objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//            
            objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 1: Décimo cuarto sueldo");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODEMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODLOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESCORTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUMDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTDTS));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            
            objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 2: Revisión RRHH/Contable");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTRRHH));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECAUTRRHH));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            
            objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 3: Autorización");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUT));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECAUT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            
            objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 4: MRL");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTGENARCMRL));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECGENARCMRL));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTFORMRL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            
            objTblHeaColGrpEmp=new ZafTblHeaColGrp("Paso 5: Banco");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTGENARCBCO));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECGENARCBCO));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

        }catch(Exception e){
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean generaArchivoDSCBAN(String strArc){
        boolean blnRes=true;
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql="";
        
        String strTab="	";
        
        int intFilSel = tblDat.getSelectedRow();
        
        String strCodigoOrientacion="PA";
        String strContraPartida="";//1ERA QUINC MAY 2012;
        String strMoneda="USD";
        String strValor="";
        String strFormaPago="CTA";
        String strTipoCuenta="";
        String strNumeroCuenta="";
        String strReferencia=strContraPartida;
        String strTipoIDBeneficiario="C";
        String strNumeroIDClienteBeneficiario="";//cedula
        String strNombreBeneficiario="";
        String strCodigoInstitucionFinanciera="32";
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                Calendar cal = Calendar.getInstance();
                File archivoSueldos = null;
                
//                String strRutArcLog = "/home/sistemas2/NetBeansProjects/Zafiro_escritorio/Zafiro/src/RecursosHumanos/ZafRecHum37/";
//                if(!System.getProperty("os.name").equals("Linux")){
////                    strRutArcLog="C:\\Users\\intel\\Desktop\\DCS"+objParSis.getNombreEmpresa()+cal.get(Calendar.YEAR)+".txt";
//                    strRutArcLog="D:\\Users\\sistemas2\\Desktop\\ARCHIVOSDCS\\DCS"+objParSis.getNombreEmpresa()+cal.get(Calendar.YEAR)+".txt";
////                    "D:\\Users\\sistemas2\\Desktop\\ARCHIVOSDCS\\DCS"
//                }
                
                String strRutArcLog = strArc;
                
                strContraPartida="DTS"+cal.get(Calendar.YEAR);
                strReferencia=strContraPartida;
                
                archivoSueldos = new File(strRutArcLog);
                archivoSueldos.delete();
                ArchivosTuval logSueldos = new ArchivosTuval(strRutArcLog);
                String strLog = new String("windows-1252");
                
                strSql="";
                strSql+="select a4.tx_ide as cedula, a4.tx_ape as apellidos, a4.tx_nom as nombres, a4.tx_sex, a2.*, a3.tx_tipctaban, a3.tx_numctaban \n" +
                        "from tbm_cabrolpag a1\n" +
                        "inner join tbm_detrolpag a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc)\n" +
                        "inner join tbm_traemp a3 on (a3.co_emp=a2.co_emp and a3.co_tra=a2.co_tra)\n" +
                        "inner join tbm_tra a4 on (a4.co_tra=a3.co_tra)\n" +
                        "where a1.co_emp = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n" +
                        "and a1.co_loc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n" +
                        "and a1.co_tipdoc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n" +
                        "and a1.co_doc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n" +
                        "and a1.ne_numdoc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n"+
                        "and a3.tx_tipctaban is not null";
                rst=stm.executeQuery(strSql);
                
                while(rst.next()){
                    
                    strTipoCuenta=rst.getString("tx_tipctaban");
                    strNumeroCuenta=rst.getString("tx_numctaban");
                    
                    if(strTipoCuenta.equals("A")){
                        strTipoCuenta="AHO";
                    }else{
                        strTipoCuenta="CTE";
                    }
                    
                    strNumeroIDClienteBeneficiario=rst.getString("cedula");
                    
                   strValor=objUti.parseString(objUti.redondear(rst.getString("nd_valrub"),objParSis.getDecimalesMostrar()));

                   strValor=retornaValorStr(strValor);

                   strNombreBeneficiario=rst.getString("apellidos") + " " + rst.getString("nombres");
                    if(Double.parseDouble(strValor)>0){
                        strLog=strCodigoOrientacion+strTab+strContraPartida+strTab+strMoneda+strTab+strValor+strTab+strFormaPago+strTab+strTipoCuenta+strTab;
                        strLog+=strNumeroCuenta+strTab+strReferencia+strTab+strTipoIDBeneficiario+strTab+strNumeroIDClienteBeneficiario+strTab;
                        strLog+=strNombreBeneficiario+strTab+strCodigoInstitucionFinanciera;
                        System.out.println(strLog);
                        logSueldos.println(strLog);
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
    
    private String retornaValorStr(String strValor){
        String cadena=strValor;
        String cadena2="";
        int car=cadena.indexOf(".");
        int limite=0;
        if (car>0)
            cadena2=cadena.substring(car+1);

        limite=(cadena2.length() > 0)?cadena2.length():2;


        if(limite%2!=0){
            for (int i =0; i< limite; i++)
                cadena+="0";
        }


        cadena=cadena.replace(".","");
        strValor=cadena;
        return strValor;
    }
    
    private boolean generaArchivoDTSMRL(String strArc){
        boolean blnRes=true;
        CsvWriter csvOutput=null;
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        java.sql.ResultSet rstLoc = null;
        java.sql.Statement stmLoc = null;
        String strSql="";
        int intFilSel = tblDat.getSelectedRow();
        
        try{
            int intNeTipPro=0;
            int intNePer=0;
            if(objParSis.getCodigoMenu()==3653){
                    intNeTipPro=1;
                }else if(objParSis.getCodigoMenu()==3792){
                    intNeTipPro=2;
                }else if(objParSis.getCodigoMenu()==3810){
                    intNeTipPro=3;
                }
            
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                stmLoc=con.createStatement();
                Calendar cal = Calendar.getInstance();
//                String outputFile = "C:\\Users\\intel\\Desktop\\DCS"+objParSis.getNombreEmpresa()+cal.get(Calendar.YEAR)+".csv";
//                String outputFile = "D:\\Users\\sistemas2\\Desktop\\ARCHIVOSDCS\\DCS"+objParSis.getNombreEmpresa()+cal.get(Calendar.YEAR)+".csv";
                String outputFile = strArc;
//                D:\Users\sistemas2\Desktop\ARCHIVOSDCS
                
                boolean alreadyExists = new File(outputFile).exists();

                if(alreadyExists){
                    File ficheroUsuarios = new File(outputFile);
                    ficheroUsuarios.delete();
                } 
                csvOutput = new CsvWriter(new FileWriter(outputFile, true), ';');
                
                strSql="";
                strSql+="SELECT ne_ani FROM tbm_cabrolpag " + " \n ";
                strSql+="where co_emp = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP).toString() +" \n ";
                strSql+="AND co_loc = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC).toString() +" \n ";
                strSql+="AND co_tipdoc = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString() +" \n ";
                strSql+="AND co_doc = " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC).toString() +" \n ";
                strSql+=" AND ne_numdoc = "+tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intNePer=rstLoc.getInt("ne_ani");
                }
                
                csvOutput.write("Cédula");
                csvOutput.write("Nombres");
                csvOutput.write("Apellidos");
                csvOutput.write("Genero (Masculino=M ï¿½ Femenino=F)");
                csvOutput.write("OcupaciÃ³n");
                csvOutput.write("Total_ganado");
                csvOutput.write("Días laborados");
                csvOutput.write("Tipo de Deposito(Pago Directo=P,Acreditación en Cuenta=A,Retencion Pago Directo=RP,Retencion Acreditación en Cuenta=RA)");
                csvOutput.write("Solo si el trabajador posee JORNADA PARCIAL PERMANENTE ponga una X");
                csvOutput.write("DETERMINE EN HORAS LA JORNADA PARCIAL PERMANENTE SEMANAL ESTIPULADO EN EL CONTRATO");
                csvOutput.write("Solo si su trabajador posee algun tipo de discapacidad ponga una X");
                csvOutput.write("Ingrese el valor retenido");
                csvOutput.endRecord();

                strSql="";
                strSql+="select a4.tx_ide as cedula, a4.tx_ape as apellidos, a4.tx_nom as nombres, a4.tx_sex, a2.*, a3.tx_tipctaban, a3.tx_numctaban , a3.st_consub , a3.st_jorpar , a3.st_dis \n" +
                        ", (select count(*) from tbm_traemp a where a.co_tra=a3.co_tra and a.st_reg='A') as traActEmp \n" +
                        "from tbm_cabrolpag a1\n" +
                        "inner join tbm_detrolpag a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc)\n" +
                        "inner join tbm_traemp a3 on (a3.co_emp=a2.co_emp and a3.co_tra=a2.co_tra)\n" +
                        "inner join tbm_tra a4 on (a4.co_tra=a3.co_tra)\n" +
                        "where a1.co_emp = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString()+" \n" +
                        "and a1.co_loc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString()+" \n" +
                        "and a1.co_tipdoc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString()+" \n" +
                        "and a1.co_doc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()+" \n" +
                        "and a1.ne_numdoc = "+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()+" \n";
                rst=stm.executeQuery(strSql);
                
                while(rst.next()){
                    
                    csvOutput.write(String.valueOf(rst.getLong("cedula")));
                    csvOutput.write(rst.getString("nombres"));
                    csvOutput.write(rst.getString("apellidos"));
                    csvOutput.write(rst.getString("tx_sex"));
                    csvOutput.write(String.valueOf(rst.getLong("tx_codcomsec")));
                    //total ganado
                    
                    String strEmpAux="";
                    if(rst.getInt("traActEmp")>1){
                        strEmpAux=" and a.co_emp = " + rst.getInt("co_emp")+ "\n";
                    }
                    
                    strSql="";
                    if(intNeTipPro==1 || intNeTipPro==3){
                        strSql="select sum (nd_valPer)*12 as nd_totalGanado from ( " + "\n";
                        strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue , " + "\n";
                        strSql+="(round(b.nd_valdectersue,2) +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valPer  " + "\n";
                        strSql+="from tbm_datgeningegrmentra a " + "\n";
                        strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
                        strSql+="where a.co_tra = "+rst.getString("co_tra")  + "\n";
                        strSql+=strEmpAux;
                        strSql+="AND ((a.ne_ani in ("+(intNePer-1) +") AND a.ne_mes in (12)))"  + " \n"; 
                        strSql+="UNION"  + " \n"; 
                        strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue , " + "\n";
                        strSql+="(round(b.nd_valdectersue,2) +   case (a.nd_comvenies is null) when true then 0 else round((a.nd_comvenies/12),2) end )  as nd_valPer  " + "\n";
                        strSql+="from tbm_datgeningegrmentra a " + "\n";
                        strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")"  + " \n"; 
                        strSql+="where a.co_tra = "+rst.getString("co_tra")  + "\n";
                        strSql+=strEmpAux;
                        strSql+="AND ((a.ne_ani in ("+intNePer +") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))"  + " \n"; 
                        strSql+=") as x";
                        }else if(intNeTipPro==2){
                        strSql+="select sum (nd_valPer)*12 as nd_totalGanado from ( " + " \n"; 
                        strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue , " + "\n";
                        strSql+="(round(b.nd_valdectersue,2) +   case (a.nd_comvenbon is null) when true then 0 else round((a.nd_comvenbon/12),2) end )  as nd_valPer " + "\n";
                        strSql+="from tbm_datgeningegrmentra a " + "\n";
                        strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")"  + " \n"; 
                        strSql+="where a.co_tra = "+rst.getString("co_tra")  + "\n";
                        strSql+=strEmpAux;
                        strSql+="AND ((a.ne_ani in ("+intNePer +") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))"  + " \n"; 
                        strSql+=") as x";
                    }
                        /*else if(intNeTipPro==3){
                        strSql+="select sum (nd_valdectersue)*12 as nd_totalGanado from ( " + " \n"; 
                        strSql+="select a.co_emp , a.co_tra , a.ne_ani , a.ne_mes , a.ne_per , a.st_rolpaggen , a.ne_numdialab , b.ne_tippro, b.tx_tiptra , b.nd_sue , b.nd_valdectersue , b.nd_valpagdectersue " + "\n";
                        strSql+="from tbm_datgeningegrmentra a " + "\n";
                        strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")"  + " \n"; 
                        strSql+="where a.co_tra = "+rst.getString("co_tra")  + "\n";
                        strSql+=strEmpAux;
                        strSql+="AND ((a.ne_ani in ("+intNePer +") AND a.ne_mes in (1,2,3,4,5,6,7,8,9,10,11)))"  + " \n"; 
                        strSql+=") as x";
                    }*/

                    
                    rstLoc=stmLoc.executeQuery(strSql);
                    String strNdValRub="";
                    if(rstLoc.next()){
                        strNdValRub=String.valueOf(objUti.redondear(rstLoc.getString("nd_totalGanado"), objParSis.getDecimalesMostrar()));
                    }
                    
                    csvOutput.write(strNdValRub);
                    
                    csvOutput.write(rst.getString("ne_numtotdialab"));
                    String strTipPag=rst.getString("tx_tipctaban");
                    if(strTipPag==null){
                        csvOutput.write("C");
                    }else{
                        csvOutput.write("A");
                    }
                    String strStJorPar=rst.getString("st_jorpar");
                    if(strStJorPar==null){
                        csvOutput.write("");
                    }else{
                        csvOutput.write("X");
                    }
                    // horas semanales
                    if(strStJorPar==null){
                        csvOutput.write("");
                    }else{
                        int intHorSem= 8 * 5;
                        intHorSem=intHorSem/2;
                        csvOutput.write(String.valueOf(intHorSem));
                    }
                    
                    String strStDis=rst.getString("st_dis");
                    if(strStDis==null){
                        csvOutput.write("");
                    }else{
                        csvOutput.write("X");
                    }

                    csvOutput.write("");
                    csvOutput.endRecord();
                }
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }finally{
            csvOutput.close();
            try{rst.close();rst=null;}catch(Throwable ignore){}
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        
        return blnRes;
    }
    
    private void llamarPantallaDTS(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc  , String strNumDoc){
        RecursosHumanos.ZafRecHum70.ZafRecHum70 obj1 = new  RecursosHumanos.ZafRecHum70.ZafRecHum70(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc , strNumDoc);
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                  strMsg="";
                  break;
                case INT_TBL_DAT_CODEMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOC:
                    strMsg="Código del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOC:
                    strMsg="Descripción corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CODDOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUMDOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FECDOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_BUTDTS:
                    strMsg="Muestra el Décimo tercer sueldo";
                    break;
                case INT_TBL_DAT_CHKAUTRRHH:
                    strMsg="Autorizar";
                    break;
                case INT_TBL_DAT_FECAUTRRHH:
                    strMsg="Fecha de autorización";
                    break;
                case INT_TBL_DAT_CHKAUT:
                    strMsg="Autorizar";
                    break;
                case INT_TBL_DAT_FECAUT:
                    strMsg="Fecha de autorización";
                    break;
                case INT_TBL_DAT_BUTGENARCMRL:
                    strMsg="Generación del archivo para el MRL";
                    break;
                case INT_TBL_DAT_FECGENARCMRL:
                    strMsg="Fecha de generación del archivo";
                    break;
                case INT_TBL_DAT_BUTFORMRL:
                    strMsg="Formulario para el MRL";
                    break;
                case INT_TBL_DAT_BUTGENARCBCO:
                    strMsg="Generación del archivo para el Banco";
                    break;
                case INT_TBL_DAT_FECGENARCBCO:
                    strMsg="Fecha de generación del archivo";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
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
 
    
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(cargarDetReg()){
                }
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
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
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");
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
//                        txtCodCli.setEditable(true);
//                        txtNomCli.setEditable(true);
//                        butCli.setEnabled(true);
//                        vcoLoc.limpiar();
//                        vcoCli.limpiar();
//                        vcoVen.limpiar();
//                        txtCodLoc.setText("");
//                        txtNomLoc.setText("");
//                        txtCodCli.setText("");
//                        txtRucCli.setText("");
//                        txtNomCli.setText("");
//                        txtCodVen.setText("");
//                        txtAliVen.setText("");
//                        txtNomVen.setText("");

                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
//                            txtCodCli.setEditable(true);
//                            txtNomCli.setEditable(true);
//                            butCli.setEnabled(true);
//                            vcoLoc.limpiar();
//                            vcoCli.limpiar();
//                            vcoVen.limpiar();
//                            txtCodLoc.setText("");
//                            txtNomLoc.setText("");
//                            txtCodCli.setText("");
//                            txtRucCli.setText("");
//                            txtNomCli.setText("");
//                            txtCodVen.setText("");
//                            txtAliVen.setText("");
//                            txtNomVen.setText("");

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
 
    private boolean cargarDetReg(){
        boolean blnRes=true;
        int i;
        strAux="";
        String strEstConf="";
        int intNumReg=0;

        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con!=null){
 

                switch (objSelFec.getTipoSeleccion()){
                    case 0: //B�squeda por rangos
                        strAux+=" AND (a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux+=" AND (a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux+=" AND (a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }
                stm=con.createStatement();
                
                strSQL="";
                String sqlFilEmp="";
                if(txtCodEmp.getText().compareTo("")!=0){
                    sqlFilEmp+= " AND a1.co_emp  = " + txtCodEmp.getText() + " ";
                }

                int intCoTipDoc=0;
                if(objParSis.getCodigoMenu()==3653){
                    intCoTipDoc=211;
                }else if(objParSis.getCodigoMenu()==3792){
                    intCoTipDoc=212;
                }else if(objParSis.getCodigoMenu()==3810){
                    intCoTipDoc=213;
                }
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, a1.st_reg, a1.fe_doc, a1.fe_ing, a1.tx_reg, \n ";
                    strSQL+="a1.st_autrechum, a1.fe_autrechum, a1.co_usrautrechum, a1.st_autger, a1.fe_autger, a1.co_usrautger, a1.tx_comautger, \n";
                    strSQL+="a1.fe_genarcmrl, a1.co_usrgenarcmrl, a1.tx_comgenarcmrl, a1.fe_genarcban, a1.co_usrgenarcban, a1.tx_comgenarcban,  \n";
                    strSQL+="a2.tx_descor, a2.tx_deslar \n";
                    strSQL+="from tbm_cabrolpag a1 \n ";
                    strSQL+="INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc \n ";
//                    strSQL+="where a1.co_emp = " + objParSis.getCodigoEmpresa() + " \n ";
//                    strSQL+="and a1.co_loc = " + objParSis.getCodigoLocal() + " \n ";
                    if(objParSis.getCodigoUsuario()==207){
                        strSQL+="WHERE a1.co_tipdoc IN ( 211 ) " + "\n";
                    }else{
                        strSQL+="WHERE a1.co_tipdoc IN ( "+intCoTipDoc+" ) " + "\n";
                    }
//                    strSQL+="and a1.co_tipdoc = " + intCoTipDoc + " \n ";
                    strSQL+=strAux + " \n ";
                    strSQL+=sqlFilEmp + " \n ";
                    strSQL+="ORDER BY a1.co_tipdoc , a1.fe_doc";
                }else{
                    strSQL="";
                    strSQL+="select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, a1.st_reg, a1.fe_doc, a1.fe_ing, a1.tx_reg, \n ";
                    strSQL+="a1.st_autrechum, a1.fe_autrechum, a1.co_usrautrechum, a1.st_autger, a1.fe_autger, a1.co_usrautger, a1.tx_comautger, \n";
                    strSQL+="a1.fe_genarcmrl, a1.co_usrgenarcmrl, a1.tx_comgenarcmrl, a1.fe_genarcban, a1.co_usrgenarcban, a1.tx_comgenarcban,  \n";
                    strSQL+="a2.tx_descor, a2.tx_deslar \n";
                    strSQL+="from tbm_cabrolpag a1 \n ";
                    strSQL+="INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc \n ";
                    strSQL+="where a1.co_emp = " + objParSis.getCodigoEmpresa() + " \n ";
                    strSQL+="and a1.co_loc = " + objParSis.getCodigoLocal() + " \n ";
                    strSQL+="and a1.co_tipdoc = " + intCoTipDoc + " \n ";
                    if(objParSis.getCodigoUsuario()==207){
                        strSQL+="and a2.co_tipdoc IN ( 211 ) " + "\n";
                    }else{
                        strSQL+="and a1.co_tipdoc IN ( "+intCoTipDoc+" ) " + "\n";
                    }
                    strSQL+=strAux + " \n ";
                    strSQL+=sqlFilEmp + " \n ";
                    strSQL+="ORDER BY a1.co_tipdoc , a1.fe_doc";
                }
                
                System.out.println("SQL cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;

                lblMsgSis.setText("Listo");
               
                if (rst.next()){
                        vecDat = new Vector();
                    do{
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CODEMP,rst.getInt("co_emp"));
                        vecReg.add(INT_TBL_DAT_CODLOC,rst.getInt("co_loc"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC,rst.getInt("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC,rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_DAT_CODDOC,rst.getInt("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUMDOC,rst.getInt("ne_numdoc"));
                        vecReg.add(INT_TBL_DAT_FECDOC,rst.getString("fe_doc"));
                        
                        vecReg.add(INT_TBL_DAT_BUTDTS,"...");
                        
                        String strAux=rst.getString("st_autrechum");
                        if(strAux==null){
                            vecReg.add(INT_TBL_DAT_CHKAUTRRHH,false);
                        }else{
                            vecReg.add(INT_TBL_DAT_CHKAUTRRHH,true);
                        }
                        
                        vecReg.add(INT_TBL_DAT_FECAUTRRHH,rst.getString("fe_autrechum"));
                        
                        strAux=rst.getString("st_autger");
                        if(strAux==null){
                            vecReg.add(INT_TBL_DAT_CHKAUT,false);
                        }else{
                            vecReg.add(INT_TBL_DAT_CHKAUT,true);
                        }
                        
                        vecReg.add(INT_TBL_DAT_FECAUT,rst.getString("fe_autger"));
                        
                        
                        vecReg.add(INT_TBL_DAT_BUTGENARCMRL,"...");
                        
                        vecReg.add(INT_TBL_DAT_FECGENARCMRL,rst.getString("fe_genarcmrl"));
                        
                        vecReg.add(INT_TBL_DAT_BUTFORMRL,"...");
                        
                        vecReg.add(INT_TBL_DAT_BUTGENARCBCO,"...");
                        vecReg.add(INT_TBL_DAT_FECGENARCBCO,rst.getString("fe_genarcban"));
                        vecDat.add(vecReg);
                        i++;
                    }while(rst.next());
                }
                
                pgrSis.setValue(i);
                intNumReg=i;

                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);               

                pgrSis.setValue(0);
                butCon.setText("Consultar");
                objTblMod.initRowsState();

                lblMsgSis.setText("Se encontraron " + intNumReg + " registros.");
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    public void Configura_ventana_consulta(){

//        configurarVenConDep();
//        configurarVenConMotHorSupExt();
   }
}