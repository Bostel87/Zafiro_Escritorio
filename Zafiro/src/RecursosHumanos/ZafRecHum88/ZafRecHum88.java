package RecursosHumanos.ZafRecHum88;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
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
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Horas extraordinarias no marcadas...
 * @author  Tony Sanginez
 */
public class ZafRecHum88 extends javax.swing.JInternalFrame
{
    
    private final int INT_TBL_DAT_LIN    =0;    //Línea
    private final int INT_TBL_DAT_FEC    =1;    //Fecha
    private final int INT_TBL_DAT_CODEMP =2;    //Codigo Empresa
    private final int INT_TBL_DAT_NOMEMP =3;    //Nombre Empresa
    private final int INT_TBL_DAT_COD    =4;    //Codigo Empleado
    private final int INT_TBL_DAT_EMP    =5;    //Nombre y Apellido Empleado
    private final int INT_TBL_DAT_LAB    =6;    //Laborado
    private final int INT_TBL_DAT_OBS    =7;    //Observación
    private final int INT_TBL_DAT_BUT_OBS=8;    //Button Descripción
    private final int INT_TBL_DAT_EST    =9;    //Estado del registro para controlar si es cargado o no.
   
    //Variables
    private ZafUtil         objUti;
    private ZafTblMod       objTblMod;
    private ZafTblPopMnu    objTblPopMnu;                       //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI    objThrGUI;
    private Connection      con;
    private Statement       stm;
    private ResultSet       rst;
    private String          strSQL, strAux;
    private ZafTblCelRenChk zafTblCelRenChkLab;                 //Renderer de Check Box para campo Laborable
    private Vector          vecDat, vecCab;
    private boolean         blnCon;                             //true: Continua la ejecuci�n del hilo.
    
    private ZafMouMotAda    objMouMotAda;

    private String          strCodTra = "";
    private String          strNomTra = "";
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblEdi       objTblEdi;  
    
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                                         //Editor de Check Box para campo Laborable
    private Librerias.ZafParSis.ZafParSis objParSis;
    private ZafPerUsr       objPerUsr;
    private ZafTblCelRenLbl objTblCelRenLbl;
    
    public int              intPosAct;
    
    private String          strVersion="v0.2";

    private String          strCodEmp, strNomEmp;
    
    private ZafVenCon       vcoEmp;                                   //Ventana de consulta.
    private ZafVenCon       vcoTra;  //Ventana de consulta.
    public ZafRecHum88(ZafParSis obj)
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
            
            if(objPerUsr.isOpcionEnabled(4096)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(4097)){
                butGua.setVisible(true);
            }        
            if(objPerUsr.isOpcionEnabled(4098)){
                butCer.setVisible(true);
            }
            if (!configurarFrm())
                exitForm(); 
            
            //Cargar Anio y Mes
            cargarPeriodo();       
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
        panFecCor = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
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

        panFecCor.setPreferredSize(new java.awt.Dimension(100, 40));
        panFecCor.setLayout(null);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Período:"); // NOI18N
        panFecCor.add(jLabel7);
        jLabel7.setBounds(10, 10, 110, 20);

        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panFecCor.add(cboPerAAAA);
        cboPerAAAA.setBounds(120, 10, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cboPerMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerMesActionPerformed(evt);
            }
        });
        panFecCor.add(cboPerMes);
        cboPerMes.setBounds(190, 10, 100, 20);

        jPanel1.add(panFecCor, java.awt.BorderLayout.NORTH);

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

        setBounds(0, 0, 700, 450);
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
                      if (objTblMod.getValueAt(i, INT_TBL_DAT_EST)=="") {
                          Date datFecReg = (Date)tblDat.getValueAt(i, INT_TBL_DAT_FEC);
                          final long diferencia = ( datFecHoy.getTime()-datFecReg.getTime())/MILLSECS_PER_DAY;
                          if (diferencia>30) {
                          }else if (objTblMod.getValueAt(i, INT_TBL_DAT_LAB).equals(true)) {
                          if (objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null) {
                          }
                          else{
                        strSql="";
                        strSql+="UPDATE tbm_cabconasitra  SET st_jusFal='L', tx_obsJusFal=" + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
                        strSql+=" fe_jusFal = current_timestamp , ";
                        strSql+=" co_usrJusFal = " + objParSis.getCodigoUsuario() + " , ";
                        strSql+=" tx_comJusFal = " + objUti.codificar(objParSis.getDireccionIP()) + " ";
                        strSql+=" WHERE co_tra = " + tblDat.getValueAt(i, INT_TBL_DAT_COD).toString()+ " ";
                        strSql+=" AND fe_dia = " + objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_FEC)) + " ";
                        stmLoc.executeUpdate(strSql);
                        blnRes=true;        
                        }}//else if (objTblMod.getValueAt(i, INT_TBL_DAT_LAB).equals(false) && !objTblMod.getValueAt(i,INT_TBL_DAT_OBS).equals("")) {
                           //mostrarMsgInf("No se ha confirmado si laboró. Campo observación con datos. Registro no se guardará.");
                          //}
                      }
                    }  
              }
              }
        }
        if(blnRes){
            con.commit();
            new RRHHDao(objUti, objParSis).callServicio9();
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

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed
    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void cboPerMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerMesActionPerformed
    }//GEN-LAST:event_cboPerMesActionPerformed

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
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
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
    /** Configurar el formulario. */
    private boolean configurarFrm() throws ParseException
    {
        boolean blnRes=true;
        final Date datFecHoy = objParSis.getFechaHoraServidorIngresarSistema();
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;//Variable utilizada para calcular los dias
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_FEC,"Fecha");
            vecCab.add(INT_TBL_DAT_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD,"Código");
            vecCab.add(INT_TBL_DAT_EMP,"Empleado");
            vecCab.add(INT_TBL_DAT_LAB,"Laborado");
            vecCab.add(INT_TBL_DAT_OBS,"Observación");
            vecCab.add(INT_TBL_DAT_BUT_OBS,"...");
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
            objMouMotAda=new ZafRecHum88.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_OBS);
            vecAux.add("" + INT_TBL_DAT_LAB);
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
            tcmAux.getColumn(INT_TBL_DAT_COD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setCellRenderer(objTblCelRenLbl);
            //Tamaño de las celdas  
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FEC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOMEMP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_EMP).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_LAB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(20);    
            tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(20);

            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(20);

            zafTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_LAB).setCellRenderer(zafTblCelRenChkLab);

            zafTblCelEdiChkLab = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_LAB).setCellEditor(zafTblCelEdiChkLab); 

            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);

            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LAB).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    Date datFecReg = (Date)objTblMod.getValueAt(intCol, INT_TBL_DAT_FEC);
                    final long diferencia = ( datFecHoy.getTime()-datFecReg.getTime())/MILLSECS_PER_DAY; 
                    if (objTblMod.getValueAt(intCol, INT_TBL_DAT_EST)=="") {
                    if (diferencia>30) {
                        objTblCelEdiChk.setCancelarEdicion(true);
                        objTblMod.setValueAt("C", intCol, INT_TBL_DAT_EST);
                    }else if (objTblMod.getValueAt(intCol, INT_TBL_DAT_LAB).equals(true)) {
                        if (objTblMod.getValueAt(intCol, INT_TBL_DAT_LIN)==null) {
                            objTblCelEdiChk.setCancelarEdicion(true);
                            objTblMod.setValueAt("C", intCol, INT_TBL_DAT_EST);
                        }
                    }
                    }else{
                            objTblCelEdiChk.setCancelarEdicion(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                }
            });
        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(zafTblDocCelRenBut);
        ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_OBS, "Observación") {
            public void butCLick() {
                int intSelFil = tblDat.getSelectedRow();
                String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS).toString());
                Date datFecReg = (Date)objTblMod.getValueAt(intSelFil, INT_TBL_DAT_FEC);
                final long diferencia = ( datFecHoy.getTime()-datFecReg.getTime())/MILLSECS_PER_DAY; 
                Boolean blnBut=false;
                //Validaciones. No se puede modificar un registro pasado los 7 dias ni un registro que ya este guardado como laborado. //Esto se modifico temporalmente para que se pueda modificar en 3 semanas osea 21 dias
                if (objTblMod.getValueAt(intSelFil, INT_TBL_DAT_EST)=="") {
                if (diferencia>30) {
                    blnBut= false;
                    objTblMod.setValueAt("C", intSelFil, INT_TBL_DAT_EST);
                }else if (objTblMod.getValueAt(intSelFil, INT_TBL_DAT_LAB).equals(true)) {
                    if (objTblMod.getValueAt(intSelFil, INT_TBL_DAT_LIN)==null) {
                           blnBut= false;
                           objTblMod.setValueAt("C", intSelFil, INT_TBL_DAT_EST);
                    }else{
                           blnBut= true;
                    }
                }else{
                           blnBut= true;
                }    
                }else{
                           blnBut= false;
                }
                ZafRecHum88_01 zafRecHum88_01 = new ZafRecHum88_01(JOptionPane.getFrameForComponent(ZafRecHum88.this), true, strObs,blnBut);
                zafRecHum88_01.show();
                if (zafRecHum88_01.getAceptar()) {
                        tblDat.setValueAt(zafRecHum88_01.getObser(), intSelFil, INT_TBL_DAT_OBS);
                }
            }
        };
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
                case INT_TBL_DAT_FEC:
                    strMsg="Fecha";
                    break;
                case INT_TBL_DAT_NOMEMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_EMP:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_LAB:
                    strMsg="Laborado";
                    break;
                case INT_TBL_DAT_BUT_OBS:
                    strMsg="Muestra la observación de la hora extraordinaria no marcada";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación de la hora extraordinaria no marcada";
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
                            txtCodTra.setText("");
                            txtNomTra.setText("");
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
                        txtCodTra.setText("");
                        txtNomTra.setText("");
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
                            txtCodTra.setText("");
                            txtNomTra.setText("");
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
        int intNumReg=0;
        GregorianCalendar calRanIni = new GregorianCalendar();
        GregorianCalendar calRanFin = new GregorianCalendar();
        calRanIni.set(Integer.valueOf(cboPerAAAA.getSelectedItem().toString()), cboPerMes.getSelectedIndex(), -3);
        calRanFin.set(Integer.valueOf(cboPerAAAA.getSelectedItem().toString()), cboPerMes.getSelectedIndex()+1, -3);
        System.out.println(calRanIni.getTime());
        System.out.println(calRanFin.getTime());
        System.out.println(objUti.formatearFecha(calRanIni.getTime(), objParSis.getFormatoFechaBaseDatos()));
        System.out.println(objUti.formatearFecha(calRanFin.getTime(),  objParSis.getFormatoFechaBaseDatos()));
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con!=null){
            vecDat = new Vector();
                        strAux+=" AND (a.fe_dia >= '" + objUti.formatearFecha(calRanIni.getTime(), objParSis.getFormatoFechaBaseDatos())+ "')";
                        strAux+=" AND (a.fe_dia < '" + objUti.formatearFecha(calRanFin.getTime(),  objParSis.getFormatoFechaBaseDatos()) + "')";
                stm=con.createStatement();
                
                strSQL="";
                String sqlFilEmp="";
                if(txtCodEmp.getText().compareTo("")!=0){
                    sqlFilEmp+= " AND b.co_emp  = " + txtCodEmp.getText() + " ";
                }
                
                String sqlFilTra="";
                if(txtCodTra.getText().compareTo("")!=0){
                    sqlFilTra+= " AND b.co_tra  = " + txtCodTra.getText() + " ";
                }
                String strGroupBy=" ORDER BY d.co_tra,a.fe_dia ";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+=" select d.co_tra,d.tx_ape || ' ' || d.tx_nom as nomTra,a.st_jusfal,a.tx_obsjusfal,a.fe_dia,b.co_emp,c.tx_nom as nomEmp\n";
                    strSQL+="from tbm_cabConAsiTra a\n";
                    strSQL+="inner join tbm_tra d on (d.co_tra=a.co_tra)\n";
                    strSQL+="inner join tbm_traEmp b on (d.co_tra=b.co_tra)\n";
                    strSQL+="inner join tbm_Emp c on (c.co_emp=b.co_emp) \n ";
                    strSQL+="where EXTRACT(DOW FROM fe_dia)=6 \n ";
                    strSQL+="and b.st_reg like 'A' and b.st_horFij like 'N'";
                    strSQL+=strAux + " \n ";
                    strSQL+=sqlFilTra + " \n ";
                    strSQL+=sqlFilEmp + " \n ";
                    strSQL+=strGroupBy + " \n ";
                }else{
                    strSQL="";
                    strSQL+=" select d.co_tra,d.tx_ape || ' ' || d.tx_nom as nomTra,a.st_jusfal,a.tx_obsjusfal,a.fe_dia,b.co_emp,c.tx_nom as nomEmp\n";
                    strSQL+="from tbm_cabConAsiTra a\n";
                    strSQL+="inner join tbm_tra d on (d.co_tra=a.co_tra)\n";
                    strSQL+="inner join tbm_traEmp b on (d.co_tra=b.co_tra)\n";
                    strSQL+="inner join tbm_Emp c on (c.co_emp=b.co_emp) \n ";
                    strSQL+="where EXTRACT(DOW FROM fe_dia)=6 \n ";
                    strSQL+="and b.st_reg like 'A' and b.st_horFij like 'N'";
                    strSQL+="and b.co_emp = " + objParSis.getCodigoEmpresa() + " \n ";
                    strSQL+=strAux + " \n ";
                    strSQL+=sqlFilTra + " \n ";
                    strSQL+=sqlFilEmp + " \n ";
                    strSQL+=strGroupBy + " \n ";
                }
                
                rst=stm.executeQuery(strSQL);
                
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;

                lblMsgSis.setText("Listo");
                int intCodTra=0;
                Date datFecDia = new Date();
               while(rst.next()){
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_FEC,rst.getDate("fe_dia"));
                vecReg.add(INT_TBL_DAT_CODEMP,rst.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_NOMEMP,rst.getString("nomEmp"));
                vecReg.add(INT_TBL_DAT_COD,rst.getInt("co_tra"));
                vecReg.add(INT_TBL_DAT_EMP,rst.getString("nomTra"));
                if(rst.getString("st_jusfal")!=null){
                        if(rst.getString("st_jusfal").compareTo("L")==0){
                            vecReg.add(INT_TBL_DAT_LAB, Boolean.TRUE );
                        }else{
                            vecReg.add(INT_TBL_DAT_LAB, Boolean.FALSE );
                        }
                 }else{
                     vecReg.add(INT_TBL_DAT_LAB, Boolean.FALSE );
                 }
                vecReg.add(INT_TBL_DAT_OBS,rst.getString("tx_obsjusfal"));
                vecReg.add(INT_TBL_DAT_BUT_OBS,"...");
                vecReg.add(INT_TBL_DAT_EST,"");
                //Validación para cuando un trabajador se encuentre en varias empresas y evitar que se repita el mismo trabajador visualmente.
                if (intCodTra!=rst.getInt("co_tra") || !datFecDia.equals(rst.getDate("fe_dia"))) {
                    vecDat.add(vecReg);
                    i++;
                }
                intCodTra= rst.getInt("co_tra");
                datFecDia= rst.getDate("fe_dia");
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
     * Esta funcion permite carga el ultimo periodo. (Anio y Mes)
     */
    private void cargarPeriodo(){
    java.sql.Connection con = null; 
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null; 
    String strSQL="";

    try{
        con =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        if(con!=null){ 
            stmLoc=con.createStatement();
            strSQL=" select  ne_ani,ne_mes from tbm_ingegrmentra ";
            strSQL = strSQL  + " group by ne_ani,ne_mes order by ne_ani desc, ne_mes desc";
            rstLoc=stmLoc.executeQuery(strSQL);
            DefaultComboBoxModel model = new DefaultComboBoxModel();//Creo un model para luego cargarlo en un combobox
            String strMes = "";
            Boolean blnUltFec=true; 
            while(rstLoc.next()){
                if (blnUltFec) {
                strMes = rstLoc.getString("ne_mes");    
                blnUltFec=false;
                }
                String strAnio = rstLoc.getString("ne_ani");
                if(model.getIndexOf(strAnio) == -1 ) { //Pregunto si existe el anio para que no se repita al momento de llenarlo en el combobox
                    model.addElement(strAnio);
                    cboPerAAAA.addItem(strAnio);
                }
            }
            int intMes=Integer.valueOf(strMes);
            cboPerMes.setSelectedIndex(intMes-1);
        }
    }catch (java.sql.SQLException Evt) {
        objUti.mostrarMsgErr_F1(this, Evt);
    } catch (Exception Evt) {
        objUti.mostrarMsgErr_F1(this, Evt);
    }finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
    }
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
                       "where b.st_reg like 'A' and b.st_horFij like 'N' " +
                       "and b.co_emp = " + txtCodEmp.getText() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";         
                }else{
                       //Se filtra por medio del st_horFij para identificar a los empleados que no tienen horario fijo
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) " +
                       "where b.st_reg like 'A' and b.st_horFij like 'N' " +
                       "group by a.co_tra,a.tx_ape,a.tx_nom "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";         
                }
                }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) " +
                       "where b.st_reg like 'A' and b.st_horFij like 'N' and co_emp = "+ objParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
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
}