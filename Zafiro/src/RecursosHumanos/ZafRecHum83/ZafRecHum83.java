package RecursosHumanos.ZafRecHum83;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * Configuración masiva de empleados...
 * @author  Tony Sanginez
 */
public class ZafRecHum83 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN         =0;    //Línea
    private static final int INT_TBL_DAT_CODEMP      =1;    //Codigo Empresa
    private static final int INT_TBL_DAT_NOMEMP      =2;    //Nombre Empresa
    private static final int INT_TBL_DAT_CODTRA      =3;    //Codigo Empleado
    private static final int INT_TBL_DAT_NOMTRA      =4;    //Nombre y Apellido Empleado
    private static final int INT_TBL_DAT_DEC_TER_ACU =5;    //Décimo tercer acumulado 
    private static final int INT_TBL_DAT_DEC_TER_MEN =6;    //Décimo tercer mensualizado
    private static final int INT_TBL_DAT_DEC_CUA_ACU =7;    //Décimo cuarto acumulado 
    private static final int INT_TBL_DAT_DEC_CUA_MEN =8;    //Décimo cuarto mensualizado
    private static final int INT_TBL_DAT_REC_COM     =9;    //Recibe Comisiones
    private static final int INT_TBL_DAT_EST         =10;   //Estado del registro para controlar si es cargado o no.
    private static final int INT_TBL_DAT_NUM_TOT_CDI = 10;
   
    //Variables
    private ZafUtil         objUti;
    private ZafTblMod       objTblMod;
    private ZafTblPopMnu    objTblPopMnu;                       //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI    objThrGUI;
    private Connection      con;
    private Statement       stm;
    private ResultSet       rst;
    private String          strSQL, strAux;
    private Vector          vecDat, vecCab;
    private boolean         blnCon;                             //true: Continua la ejecuci�n del hilo.
    
    private ZafMouMotAda    objMouMotAda;

    private String          strCodTra = "";
    private String          strNomTra = "";
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblEdi       objTblEdi;  
    private ZafTblCelRenChk zafTblCelRenChk;                   //Renderer de Check Box para las empresas
    private ZafTblCelEdiChk zafTblCelEdiChk;                   //Editor de Check Box para las empresas
    
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                                         //Editor de Check Box para campo Laborable
    private Librerias.ZafParSis.ZafParSis objParSis;
    private ZafPerUsr       objPerUsr;
    private ZafTblCelRenLbl objTblCelRenLbl;
    
    public int              intPosAct;
    private String          strVersion="v0.2";
    private String          strCodEmp, strNomEmp;
    private ZafVenCon       vcoEmp;                                   //Ventana de consulta.
    private ZafVenCon       vcoTra;  //Ventana de consulta.
    public ZafRecHum83(ZafParSis obj)
    {
        try{
            ZafTblCelRenChkDat = new ZafTblCelRenChk();
            this.objParSis=(ZafParSis)obj.clone();
            initComponents();
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            
            this.setTitle(objParSis.getNombreMenu() + strVersion );
            lblTit.setText(objParSis.getNombreMenu());
            
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(4114)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(4115)){
                butGua.setVisible(true);
            }        
            if(objPerUsr.isOpcionEnabled(4116)){
                butCer.setVisible(true);
            }
            if (!configurarFrm())
                exitForm(); 
               agregarColTblDat();
             //Configurar las ZafVenCon. Ventanas de consulta
            configurarVenConTra();
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
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
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

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(0, 200));
        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 4, 400, 14);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 20, 400, 14);

        lblLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblLoc.setText("Empresa:");
        lblLoc.setToolTipText("Proveedor");
        panFil.add(lblLoc);
        lblLoc.setBounds(30, 50, 100, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(150, 50, 60, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(210, 50, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(460, 50, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        jLabel6.setToolTipText("");
        panFil.add(jLabel6);
        jLabel6.setBounds(30, 70, 100, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(150, 70, 60, 20);

        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(210, 70, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(460, 70, 20, 20);

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

        setBounds(0, 0, 700, 411);
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
    if(optTod.isSelected()){
          optFil.setSelected(false);
          txtCodEmp.setText("");
          txtNomEmp.setText("");
          txtCodTra.setText("");
          txtNomTra.setText("");
    }
    else{
        optFil.setSelected(true);
    }
}//GEN-LAST:event_optTodActionPerformed

private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
    if(optFil.isSelected()){
        optTod.setSelected(false);
    }
    else
        optTod.setSelected(true);
}//GEN-LAST:event_optFilActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
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
        final Date datFecHoy = objParSis.getFechaHoraServidorIngresarSistema();
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        if(con!=null){          
                con.setAutoCommit(false);
                stmLoc=con.createStatement();
                int intFilSel=tblDat.getSelectedRow(); 
          for(int i=0; i<tblDat.getRowCount(); i++){
              if (objTblMod.getValueAt(i, INT_TBL_DAT_LIN)!=null) {
                  if(tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0){
                        strSql="";
                        strSql+=" UPDATE tbm_traemp SET  ";
                        if (tblDat.getValueAt(i, INT_TBL_DAT_DEC_TER_ACU).equals(true)) {
                          strSql+=" tx_forpagdectersue= NULL ";
                        }else{
                          strSql+=" tx_forpagdectersue= 'M' ";
                        }
                        if (tblDat.getValueAt(i, INT_TBL_DAT_DEC_CUA_ACU).equals(true)) {
                          strSql+=" , tx_forpagdeccuasue= null";
                        }else {
                          strSql+=" , tx_forpagdeccuasue= 'M'";
                        }
                        if (tblDat.getValueAt(i, INT_TBL_DAT_REC_COM).equals(true)) {
                          strSql+=" , st_reccomven= 'S'";
                        }else{
                          strSql+=" , st_reccomven= NULL";
                        }
                        strSql+=" WHERE co_tra = " + tblDat.getValueAt(i, INT_TBL_DAT_CODTRA).toString()+ " ";
                        strSql+=" AND co_emp = " + tblDat.getValueAt(i, INT_TBL_DAT_CODEMP).toString()+ " ";
                        stmLoc.executeUpdate(strSql);
                        blnRes=true;        
                  }}
              }
        }
        if(blnRes){
            con.commit();
            objTblMod.initRowsState();
            objTblMod.setDataModelChanged(false);
            new RRHHDao(objUti, objParSis).callServicio9();//tony
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
        if (objTblMod.isDataModelChanged())
        {
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
                            mostrarMsgInf("No se ha realizado ningún cambio que pueda guardarse.");
                        }
                }
        }else{
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
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
    
    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
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
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
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
        optFil.setSelected(true);
        optTod.setSelected(false);
        configurarVenConTra();
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

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
    private javax.swing.JButton butTra;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

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
    /** Configurar el formulario. */
    private boolean configurarFrm() throws ParseException
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DAT_CODTRA,"Código");
            vecCab.add(INT_TBL_DAT_NOMTRA,"Empleado");
            vecCab.add(INT_TBL_DAT_DEC_TER_ACU,"Acumulado");
            vecCab.add(INT_TBL_DAT_DEC_TER_MEN,"Mensualizado");
            vecCab.add(INT_TBL_DAT_DEC_CUA_ACU,"Acumulado");
            vecCab.add(INT_TBL_DAT_DEC_CUA_MEN,"Mensualizado");
            vecCab.add(INT_TBL_DAT_REC_COM,"Comisiones");
            vecCab.add(INT_TBL_DAT_EST,"");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
	    
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafRecHum83.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_DEC_TER_ACU);
            vecAux.add("" + INT_TBL_DAT_DEC_TER_MEN);
            vecAux.add("" + INT_TBL_DAT_DEC_CUA_ACU);
            vecAux.add("" + INT_TBL_DAT_DEC_CUA_MEN);
            vecAux.add("" + INT_TBL_DAT_REC_COM);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;   
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);
            objTblPopMnu.setEliminarFilaEnabled(false);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);    
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
        
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CODTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setCellRenderer(objTblCelRenLbl);
            //Tamaño de las celdas  
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOMEMP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CODTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOMTRA).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TER_ACU).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TER_MEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DEC_CUA_ACU).setPreferredWidth(100);    
            tcmAux.getColumn(INT_TBL_DAT_DEC_CUA_MEN).setPreferredWidth(100);    
            tcmAux.getColumn(INT_TBL_DAT_REC_COM).setPreferredWidth(100);   
            tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(20);

            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(20);

            zafTblCelEdiChk = new ZafTblCelEdiChk(tblDat);

            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);

                
                tcmAux.getColumn(INT_TBL_DAT_DEC_CUA_MEN).setCellRenderer(zafTblCelRenChk);
                tcmAux.getColumn(INT_TBL_DAT_DEC_CUA_MEN).setCellEditor(zafTblCelEdiChk);
                tcmAux.getColumn(INT_TBL_DAT_REC_COM).setCellEditor(zafTblCelEdiChk);
                tcmAux.getColumn(INT_TBL_DAT_REC_COM).setCellRenderer(zafTblCelRenChk);
                tcmAux.getColumn(INT_TBL_DAT_DEC_TER_MEN).setCellRenderer(zafTblCelRenChk);
                tcmAux.getColumn(INT_TBL_DAT_DEC_TER_MEN).setCellEditor(zafTblCelEdiChk);
                zafTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();//fila
                ZafTblMod objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
                if(objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_ACU).equals(false)){
                    if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_MEN).equals(false)) {
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_ACU);
                    }else {
                        tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_ACU);
                    }
                }else if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_ACU).equals(true)) {
                     if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_MEN).equals(true)) {
                         tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_ACU);
                    }else{
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_ACU);
                     }    
                    }
                if(objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_ACU).equals(false)){
                    if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_MEN).equals(false)) {
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_ACU);
                    }else {
                        tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_ACU);
                    }
                }else if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_ACU).equals(true)) {
                     if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_MEN).equals(true)) {
                         tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_ACU);
                    }else{
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_ACU);
                     }    
                    }
                }
               });
                zafTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
                tcmAux.getColumn(INT_TBL_DAT_DEC_TER_ACU).setCellRenderer(zafTblCelRenChk);
                tcmAux.getColumn(INT_TBL_DAT_DEC_TER_ACU).setCellEditor(zafTblCelEdiChk);
                tcmAux.getColumn(INT_TBL_DAT_DEC_CUA_ACU).setCellRenderer(zafTblCelRenChk);
                tcmAux.getColumn(INT_TBL_DAT_DEC_CUA_ACU).setCellEditor(zafTblCelEdiChk);
                zafTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();//fila
                ZafTblMod objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
                if(objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_MEN).equals(false)){
                    if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_ACU).equals(false)) {
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_MEN);
                    }else {
                        tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_MEN);
                    }
                }else if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_MEN).equals(true)) {
                     if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_TER_ACU).equals(true)) {
                         tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_MEN);
                    }else{
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_TER_MEN);
                     }    
                    }
                 if(objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_MEN).equals(false)){
                    if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_ACU).equals(false)) {
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_MEN);
                    }else {
                        tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_MEN);
                    }
                }else if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_MEN).equals(true)) {
                     if (objTblMod.getValueAt(intFil, INT_TBL_DAT_DEC_CUA_ACU).equals(true)) {
                         tblDat.setValueAt(false, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_MEN);
                    }else{
                        tblDat.setValueAt(true, intFil, ZafRecHum83.INT_TBL_DAT_DEC_CUA_MEN);
                     }    
                    }
                }
               });
                
                
              
        Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
        Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        objTblCelRenChk=null;
        objTblCelEdiChk=null; 
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        blnRes=true;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
                case INT_TBL_DAT_NOMEMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_CODTRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOMTRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_DEC_TER_ACU:
                    strMsg="Acumulado";
                    break;
                case INT_TBL_DAT_DEC_TER_MEN:
                    strMsg="Mensualizado";
                    break;
                case INT_TBL_DAT_DEC_CUA_ACU:
                    strMsg="Acumulado";
                    break;
                case INT_TBL_DAT_DEC_CUA_MEN:
                    strMsg="Mensualizado";
                    break;
                case INT_TBL_DAT_REC_COM:
                    strMsg="Comisiones";
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
            }
        }
    }
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
            }
        }
     }
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
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                          txtCodTra.setText("");
                          txtNomTra.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodTra.setText("");
                            txtNomTra.setText("");
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
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodTra.setText("");
                            txtNomTra.setText("");
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
        int intNumReg=0;
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con!=null){
            vecDat = new Vector();
            stm=con.createStatement();
                
                strSQL="";
                String sqlFilEmp="";
                if(txtCodEmp.getText().compareTo("")!=0){
                    sqlFilEmp+= " AND a.co_emp  = " + txtCodEmp.getText() + " ";
                }
                
                String sqlFilTra="";
                if(txtCodTra.getText().compareTo("")!=0){
                    sqlFilTra+= " AND b.co_tra  = " + txtCodTra.getText() + " ";
                }
                
                String strOrdBy=" Order by co_emp, co_tra ";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+=" select a.*, c.tx_nom as nomEmp, b.tx_nom as nomTra, b.tx_ape as apeTra\n";
                    strSQL+=" from tbm_traemp a\n";
                    strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)\n";
                    strSQL+=" inner join tbm_emp c on (a.co_emp=c.co_emp)\n";
                    strSQL+= " where a.st_reg='A' and b.st_reg='A' and c.st_reg='A'\n";
                    strSQL+=strAux + " \n ";
                    strSQL+=sqlFilTra + " \n ";
                    strSQL+=sqlFilEmp + " \n ";
                    strSQL+=strOrdBy + " \n ";
                }else{
                   strSQL="";
                    strSQL+=" select a.*, c.tx_nom as nomEmp, b.tx_nom as nomTra, b.tx_ape as apeTra\n";
                    strSQL+=" from tbm_traemp a\n";
                    strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)\n";
                    strSQL+=" inner join tbm_emp c on (a.co_emp=c.co_emp)\n";
                    strSQL+= " where a.st_reg='A' and b.st_reg='A' and c.st_reg='A'\n";
                    strSQL+=" and a.co_emp = " + objParSis.getCodigoEmpresa() + " \n ";
                    strSQL+=strAux + " \n ";
                    strSQL+=sqlFilTra + " \n ";
                    strSQL+=sqlFilEmp + " \n ";
                    strSQL+=strOrdBy + " \n ";
                }
                
                rst=stm.executeQuery(strSQL);
                
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;

                lblMsgSis.setText("Listo");
                int intCodTra=0;
               while(rst.next()){
                
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_CODEMP,rst.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_NOMEMP,rst.getString("nomEmp"));
                vecReg.add(INT_TBL_DAT_CODTRA,rst.getInt("co_tra"));
                vecReg.add(INT_TBL_DAT_NOMTRA,rst.getString("apeTra") + " " + rst.getString("nomTra"));
                if(rst.getString("tx_forpagdectersue")!=null){
                        if(rst.getString("tx_forpagdectersue").compareTo("M")==0){
                            vecReg.add(INT_TBL_DAT_DEC_TER_ACU, Boolean.FALSE );
                            vecReg.add(INT_TBL_DAT_DEC_TER_MEN, Boolean.TRUE );
                        }else{
                            vecReg.add(INT_TBL_DAT_DEC_TER_ACU, Boolean.FALSE );
                            vecReg.add(INT_TBL_DAT_DEC_TER_MEN, Boolean.FALSE );
                        }
                 }else{
                     vecReg.add(INT_TBL_DAT_DEC_TER_ACU, Boolean.TRUE );
                     vecReg.add(INT_TBL_DAT_DEC_TER_MEN, Boolean.FALSE );
                 }
                if(rst.getString("tx_forpagdeccuasue")!=null){
                        if(rst.getString("tx_forpagdeccuasue").compareTo("M")==0){
                            vecReg.add(INT_TBL_DAT_DEC_CUA_ACU, Boolean.FALSE );
                            vecReg.add(INT_TBL_DAT_DEC_CUA_MEN, Boolean.TRUE );
                        }else{
                            vecReg.add(INT_TBL_DAT_DEC_CUA_ACU, Boolean.FALSE );
                            vecReg.add(INT_TBL_DAT_DEC_CUA_MEN, Boolean.FALSE );
                        }
                 }else{
                     vecReg.add(INT_TBL_DAT_DEC_CUA_ACU, Boolean.TRUE );
                     vecReg.add(INT_TBL_DAT_DEC_CUA_MEN, Boolean.FALSE );
                 }
                if(rst.getString("st_reccomven")!=null){
                        if(rst.getString("st_reccomven").compareTo("S")==0){
                            vecReg.add(INT_TBL_DAT_REC_COM, Boolean.TRUE );
                        }else{
                            vecReg.add(INT_TBL_DAT_REC_COM, Boolean.FALSE );
                        }
                 }else{
                     vecReg.add(INT_TBL_DAT_REC_COM, Boolean.FALSE );
                 }
                vecReg.add(INT_TBL_DAT_EST,"");
                //Validación para cuando un trabajador se encuentre en varias empresas y evitar que se repita el mismo trabajador visualmente.
                if (intCodTra!=rst.getInt("co_tra")) {
                    vecDat.add(vecReg);
                    i++;
                }
                intCodTra= rst.getInt("co_tra");
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
            butCon.setText("Consultar");
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
            butCon.setText("Consultar");
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
            //Alias de los campos.  
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            
            String strSQL="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                if (txtCodEmp.getText().length()>0) {
                    //Se filtra por medio del st_horFij para identificar a los empleados que no tienen horario fijo
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) " +
                       "where b.st_reg like 'A'  " +
                       "and b.co_emp = " + txtCodEmp.getText() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";         
                }else{
                       //Se filtra por medio del st_horFij para identificar a los empleados que no tienen horario fijo
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) " +
                       "where b.st_reg like 'A'  " +
                       "group by a.co_tra,a.tx_ape,a.tx_nom "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";         
                }
                }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) " +
                       "where b.st_reg like 'A' and co_emp = "+ objParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
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
            arlAncCol.add("110");
            
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
    private boolean agregarColTblDat() {
        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        java.awt.Color colFonCol;
        boolean blnRes = true;

        try {
            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();

            for (i = 0; i < 1; i++) {

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Décimo tercer sueldo");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DEC_TER_ACU + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DEC_TER_MEN + i * INT_TBL_DAT_NUM_TOT_CDI));
                
                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Décimo cuarto sueldo");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DEC_CUA_ACU + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DEC_CUA_MEN + i * INT_TBL_DAT_NUM_TOT_CDI));


            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}