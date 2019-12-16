

package RecursosHumanos.ZafRecHum63;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.*;

/**Clase nueva Recursos humanos - Roles de pago - Beneficios sociales - Décimo cuarto sueldo...
 * @author  Roberto Flores
 * Guayaquil 04/07/2013
 */
public class ZafRecHum63 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_EMP=1;
    private static final int INT_TBL_DAT_NOM_EMP=2;
    private static final int INT_TBL_DAT_COD_OFI=3;
    private static final int INT_TBL_DAT_NOM_OFI=4;
    private static final int INT_TBL_DAT_COD_TRA=5;
    private static final int INT_TBL_DAT_NOM_APE_TRA=6;
    private static final int INT_TBL_DAT_FECINICON=7;
    private static final int INT_TBL_DAT_FECFINCON=8;
    private static final int INT_TBL_DIALAB=9;
    private static final int INT_TBL_VALPEN=10;
    private static final int INT_TBL_VALPAG=11;

    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                //Editor: JTextField en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDL;                                //Render: Presentar JLabel en JTable.
    
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoOfi;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;

    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;

    private String strCodEmp, strNomEmp;
    private String strCodOfi = "";
    private String strDesLarOfi = "";
    private String strCodTra = "";
    private String strNomTra = "";
    
    private ZafPerUsr objPerUsr;
    private ZafTblTot objTblTot;                 //JTable de totales.
    private ZafTblBus objTblBus;
    
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strVersion="v1.02";
    private ArrayList<String> arrLstMeses;
    private int intPosArrLstMesesRegTraAnt=0;
    private String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto"," ;Septiembre" ,"Octubre","Noviembre","Diciemrbre"}; 
//    private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.
    private static int[] intValDCSDblPos;
    private static int[] intDiaLabPos;
    private static int[] intCabDCS = {
        57 , 58, 59 }; 
    
  private ZafTblCelRenLbl objTblCelRenLbl2;
  final java.awt.Color colFonCol =new java.awt.Color(255,255,0);
    
    /** Crea una nueva instancia de la clase ZafRecHum62. */
    public ZafRecHum63(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
            cargarAños();
            
            butCon.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3582)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(3583)){
                butCer.setVisible(true);
            }

            if (!configurarFrm())
                exitForm();
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
            int intLstAni=0;
            strSQL="select distinct ne_ani from tbm_bensocmentra order by ne_ani asc";
            rstLoc=stmLoc.executeQuery(strSQL);
            while(rstLoc.next()){
                cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
                intLstAni=rstLoc.getInt("ne_ani")+1;
            }
            cboPerAAAA.addItem(intLstAni);
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
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLblOfi = new javax.swing.JLabel();
        txtCodOfi = new javax.swing.JTextField();
        txtNomOfi = new javax.swing.JTextField();
        butOfi = new javax.swing.JButton();
        jLblTipTra = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jLblTra = new javax.swing.JLabel();
        jChBAdmin = new javax.swing.JCheckBox();
        jChBVen = new javax.swing.JCheckBox();
        jChBOtr = new javax.swing.JCheckBox();
        jLblEmp1 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        jRBSO = new javax.swing.JRadioButton();
        jRBCI = new javax.swing.JRadioButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 110, 330, 20);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 130, 370, 20);

        jLblEmp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLblEmp.setText("Empresa:"); // NOI18N
        panFil.add(jLblEmp);
        jLblEmp.setBounds(40, 150, 100, 20);

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
        txtCodEmp.setBounds(140, 150, 60, 20);

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
        txtNomEmp.setBounds(200, 150, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 150, 20, 20);

        jLblOfi.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLblOfi.setText("Oficina:"); // NOI18N
        panFil.add(jLblOfi);
        jLblOfi.setBounds(40, 170, 100, 20);

        txtCodOfi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodOfiActionPerformed(evt);
            }
        });
        txtCodOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusLost(evt);
            }
        });
        panFil.add(txtCodOfi);
        txtCodOfi.setBounds(140, 170, 60, 20);

        txtNomOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomOfiActionPerformed(evt);
            }
        });
        txtNomOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusLost(evt);
            }
        });
        panFil.add(txtNomOfi);
        txtNomOfi.setBounds(200, 170, 250, 20);

        butOfi.setText(".."); // NOI18N
        butOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOfiActionPerformed(evt);
            }
        });
        panFil.add(butOfi);
        butOfi.setBounds(450, 170, 20, 20);

        jLblTipTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLblTipTra.setText("Tipo de trabajador:"); // NOI18N
        panFil.add(jLblTipTra);
        jLblTipTra.setBounds(40, 210, 100, 20);

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
        txtCodTra.setBounds(140, 190, 60, 20);

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
        txtNomTra.setBounds(200, 190, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 190, 20, 20);

        jLblTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLblTra.setText("Empleado:"); // NOI18N
        panFil.add(jLblTra);
        jLblTra.setBounds(40, 190, 100, 20);

        jChBAdmin.setSelected(true);
        jChBAdmin.setText("Administrativo");
        jChBAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChBAdminActionPerformed(evt);
            }
        });
        panFil.add(jChBAdmin);
        jChBAdmin.setBounds(140, 210, 120, 23);

        jChBVen.setSelected(true);
        jChBVen.setText("Ventas");
        jChBVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChBVenActionPerformed(evt);
            }
        });
        panFil.add(jChBVen);
        jChBVen.setBounds(260, 210, 90, 23);

        jChBOtr.setSelected(true);
        jChBOtr.setText("Otros");
        jChBOtr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChBOtrActionPerformed(evt);
            }
        });
        panFil.add(jChBOtr);
        jChBOtr.setBounds(350, 210, 100, 23);

        jLblEmp1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLblEmp1.setText("Año:"); // NOI18N
        panFil.add(jLblEmp1);
        jLblEmp1.setBounds(14, 90, 120, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        panFil.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 90, 60, 20);

        jRBSO.setText("Sierra y Oriente");
        jRBSO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBSOActionPerformed(evt);
            }
        });
        panFil.add(jRBSO);
        jRBSO.setBounds(340, 90, 140, 23);

        jRBCI.setText("Costa e Insular");
        jRBCI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBCIActionPerformed(evt);
            }
        });
        panFil.add(jRBCI);
        jRBCI.setBounds(230, 90, 110, 23);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

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

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
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

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;

            if(configuraTbl()){
                agregarColTblDat();
                if(validarCamObl()){
                    if (objThrGUI==null)
                    {
                        objThrGUI=new ZafThreadGUI();
                        objThrGUI.start();
                    }            
                }else{
                    lblMsgSis.setText("Listo");
                    pgrSis.setIndeterminate(false);
                }
            }
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamObl(){
        boolean blnOk = true;

        if(cboPerAAAA.getSelectedItem().toString().compareTo("Año")==0){
            cboPerAAAA.requestFocus();
            cboPerAAAA.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Año</FONT> no ha sido seleccionado.<BR>Seleccione un año y vuelva a intentarlo.</HTML>");
            return false;
        }
        return blnOk;
    }
    
    
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
   
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

   }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodOfi.setText("");
            txtNomOfi.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
        optTod.setSelected(true);
        optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodOfi.setText("");
            txtNomOfi.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
            
        }
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

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

    private void txtCodOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodOfiActionPerformed
        txtCodOfi.transferFocus();
    }//GEN-LAST:event_txtCodOfiActionPerformed

    private void txtCodOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusGained
        // TODO add your handling code here:
        strCodOfi = txtCodOfi.getText();
        txtCodOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodOfiFocusGained

    private void txtCodOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusLost
        // TODO add your handling code here:
        if (!txtCodOfi.getText().equalsIgnoreCase(strCodOfi)) {
            if (txtCodOfi.getText().equals("")) {
                txtCodOfi.setText("");
                txtNomOfi.setText("");
            } else {
                BuscarOfi("a1.co_ofi", txtCodOfi.getText(), 0);
            }
        } else {
            txtCodOfi.setText(strCodOfi);
        }
    }//GEN-LAST:event_txtCodOfiFocusLost

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
    
    public void BuscarOfi(String campo,String strBusqueda,int tipo){
        
        vcoOfi.setTitle("Listado de Oficinas");
        if(vcoOfi.buscar(campo, strBusqueda )) {
            txtCodOfi.setText(vcoOfi.getValueAt(1));
            txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
            vcoOfi.setCampoBusqueda(tipo);
            vcoOfi.cargarDatos();
            vcoOfi.show();
            if (vcoOfi.getSelectedButton()==vcoOfi.INT_BUT_ACE) {
                txtCodOfi.setText(vcoOfi.getValueAt(1));
                txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
                txtCodOfi.setText(strCodOfi);
                txtNomOfi.setText(strDesLarOfi);
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
    
    private void txtNomOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomOfiActionPerformed
        // TODO add your handling code here:
        txtNomOfi.transferFocus();
    }//GEN-LAST:event_txtNomOfiActionPerformed

    private void txtNomOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusGained
        // TODO add your handling code here:
        strDesLarOfi=txtNomOfi.getText();
        txtNomOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
        
    }//GEN-LAST:event_txtNomOfiFocusGained

    private void txtNomOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusLost
        // TODO add your handling code here:
        if (txtNomOfi.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomOfi.getText().equalsIgnoreCase(strDesLarOfi))
            {
                if (txtNomOfi.getText().equals(""))
                {
                    txtCodOfi.setText("");
                    txtNomOfi.setText("");
                }
                else
                {
                    mostrarVenConOfi(2);
                }
            }
            else
                txtNomOfi.setText(strDesLarOfi);
        }
    }//GEN-LAST:event_txtNomOfiFocusLost

    private void butOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOfiActionPerformed
        strCodOfi=txtCodOfi.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConOfi(0);
        
    }//GEN-LAST:event_butOfiActionPerformed
    
    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
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
        optFil.setSelected(true);
        optTod.setSelected(false);
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
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void jRBCIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBCIActionPerformed
        // TODO add your handling code here:
        jRBSO.setSelected(false);
        jRBCI.setSelected(true);
    }//GEN-LAST:event_jRBCIActionPerformed

    private void jRBSOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBSOActionPerformed
        // TODO add your handling code here:
        jRBCI.setSelected(false);
        jRBSO.setSelected(true);
    }//GEN-LAST:event_jRBSOActionPerformed

    private void jChBAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChBAdminActionPerformed
        // TODO add your handling code here:
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_jChBAdminActionPerformed

    private void jChBVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChBVenActionPerformed
        // TODO add your handling code here:
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_jChBVenActionPerformed

    private void jChBOtrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChBOtrActionPerformed
        // TODO add your handling code here:
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_jChBOtrActionPerformed

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
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butOfi;
    private javax.swing.JButton butTra;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JCheckBox jChBAdmin;
    private javax.swing.JCheckBox jChBOtr;
    private javax.swing.JCheckBox jChBVen;
    private javax.swing.JLabel jLblEmp;
    private javax.swing.JLabel jLblEmp1;
    private javax.swing.JLabel jLblOfi;
    private javax.swing.JLabel jLblTipTra;
    private javax.swing.JLabel jLblTra;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRBCI;
    private javax.swing.JRadioButton jRBSO;
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
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodOfi;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomOfi;
    private javax.swing.JTextField txtNomTra;
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

            int intOpSel=1;
            
            if(objParSis.getCodigoEmpresa()==1){
                intOpSel=1;
                jRBCI.setSelected(true);
                jRBSO.setSelected(false);
            }else if(objParSis.getCodigoEmpresa()==2){
                intOpSel=2;
                jRBCI.setSelected(false);
                jRBSO.setSelected(true);
            }else if(objParSis.getCodigoEmpresa()==4){
                intOpSel=1;
                jRBCI.setSelected(true);
                jRBSO.setSelected(false);
            }else if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                jRBCI.setSelected(true);
                jRBSO.setSelected(false);
            }
            
            insertarMesesPerCom(intOpSel);

            //Configurar las ZafVenCon.
            configurarVenConOfi();
            configurarVenConTra();
            configurarVenConEmp();
            
            //Configurar los JTables.
            configuraTbl();
            agregarColTblDat();
            
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
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean insertarMesesPerCom(int intOpSel){
        boolean blnRes=false;
        try{
            arrLstMeses = new ArrayList<String>();
            switch(intOpSel){
                case 1:
                    arrLstMeses = new ArrayList<String>();
                    arrLstMeses.add("Marzo");
                    arrLstMeses.add("Abril");
                    arrLstMeses.add("Mayo");
                    arrLstMeses.add("Junio");
                    arrLstMeses.add("Julio");
                    arrLstMeses.add("Agosto");
                    arrLstMeses.add("Septiembre");
                    arrLstMeses.add("Octubre");
                    arrLstMeses.add("Noviembre");
                    arrLstMeses.add("Diciembre");
                    arrLstMeses.add("Enero");
                    arrLstMeses.add("Febrero");
                    intPosArrLstMesesRegTraAnt=10;
                    blnRes = true;
                    break;
                case 2:
                    arrLstMeses = new ArrayList<String>();
                    arrLstMeses.add("Agosto");
                    arrLstMeses.add("Septiembre");
                    arrLstMeses.add("Octubre");
                    arrLstMeses.add("Noviembre");
                    arrLstMeses.add("Diciembre");
                    arrLstMeses.add("Enero");
                    arrLstMeses.add("Febrero");
                    arrLstMeses.add("Marzo");
                    arrLstMeses.add("Abril");
                    arrLstMeses.add("Mayo");
                    arrLstMeses.add("Junio");
                    arrLstMeses.add("Julio");
                    blnRes=true;
                    intPosArrLstMesesRegTraAnt=5;
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraTbl(){

        boolean blnRes=false;
        
        try{

            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_OFI,"Cód.Ofi.");
            vecCab.add(INT_TBL_DAT_NOM_OFI,"Oficina");
            vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_FECINICON, "Fec.Ini.Con.");
            vecCab.add(INT_TBL_DAT_FECFINCON, "Fec.Fin.Con.");
            vecCab.add(INT_TBL_DIALAB,"Día.Lab.");
            vecCab.add(INT_TBL_VALPEN,"Pendiente");
            vecCab.add(INT_TBL_VALPAG,"Pagado");

            int intOpSel = 0;
            if(jRBCI.isSelected()){
                insertarMesesPerCom(1);
            }else if (jRBSO.isSelected()){
                insertarMesesPerCom(2);
            }
            //asigno columnas para calculo de totales
            int intCol[]= new int[28];
            int intCon = 0;
            
            intCol[intCon]=INT_TBL_VALPEN;
            intCon++;
            intCol[intCon]=INT_TBL_VALPAG;
            intCon++;
            
            intValDCSDblPos=new int[50];
            intDiaLabPos=new int[50];
            int intDiaPos=0;
            int intValDblPos=0;
            for (Iterator<String> it = arrLstMeses.iterator(); it.hasNext();it.next()){
                
                int intSize=vecCab.size();
                vecCab.add(intSize, "SBU");
                intValDCSDblPos[intValDblPos]=intSize;
                intValDblPos++;
                
                intSize=vecCab.size();
                vecCab.add(intSize, "Día.Lab.");
                intDiaLabPos[intDiaPos]=intSize;
                intDiaPos++;
                
                intSize=vecCab.size();
                vecCab.add(intSize, "Pendiente");
                intValDCSDblPos[intValDblPos]=intSize;
                intValDblPos++;
                intCol[intCon]=vecCab.size()-1;
                intCon++;
                
                intSize=vecCab.size();
                vecCab.add(intSize, "Pagado");
                intValDCSDblPos[intValDblPos]=intSize;
                intValDblPos++;
                intCol[intCon]=vecCab.size()-1;
                intCon++;
            }
            
            int intSize=vecCab.size();
            vecCab.add(intSize, "Día.Lab.");
            intDiaLabPos[intDiaPos]=intSize;
            intDiaPos++;
            
            intSize=vecCab.size();
            vecCab.add(intSize, "Pendiente");
            intValDCSDblPos[intValDblPos]=intSize;
            intCol[intCon++]=intSize;
            intValDblPos++;

            intSize=vecCab.size();
            vecCab.add(intSize, "Pagado");
            intValDCSDblPos[intValDblPos]=intSize;
            intCol[intCon++]=intSize;
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
        
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el msenú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(260);
            tcmAux.getColumn(INT_TBL_DAT_FECINICON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FECFINCON).setPreferredWidth(80);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_OFI, tblDat);
            objTblMod.addSystemHiddenColumn(vecCab.size()-1, tblDat);
            objTblMod.addSystemHiddenColumn(vecCab.size()-2, tblDat);
            objTblMod.addSystemHiddenColumn(vecCab.size()-3, tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DIALAB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VALPAG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            //objTblCelRenLbl=null;
//            
            objTblCelRenLblDL=new ZafTblCelRenLbl();
            objTblCelRenLblDL.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDL.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLblDL.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            

            objTblCelRenLbl2=new ZafTblCelRenLbl();
            objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl2.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);     
            tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setCellRenderer(objTblCelRenLbl2);

            
            objTblCelRenLbl2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {

                    int intCell=objTblCelRenLbl2.getRowRender();

                    //                intCantRubAct
                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_LIN).toString();
//
                    if(str.equals("S")){
                        objTblCelRenLbl2.setBackground(colFonCol);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    }else {
                        objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    }
                }
                public void afterRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.

                    int intCell=objTblCelRenLbl2.getRowRender();

                    //                intCantRubAct
                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_LIN).toString();
//
                    if(str.equals("S")){
                        objTblCelRenLbl2.setBackground(colFonCol);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    }else {
                        objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                        objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                    }
                }
            });

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);

            int intP=INT_TBL_VALPAG+1;
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            for (int i=0; i<1; i++)
            {
                int p=1;
                for(int x=intP;x<tblDat.getColumnCount();x++){

                    for(int intPosVal = 0; intPosVal<intValDCSDblPos.length;intPosVal++){
                        if(intValDCSDblPos[intPosVal]==x){
                            objTblMod.setColumnDataType(x, objTblMod.INT_COL_DBL, new Integer(0), null);
                            tcmAux.getColumn(x).setCellRenderer(objTblCelRenLbl);
                            tcmAux.getColumn(x).setCellEditor(objTblCelEdiTxt);
                        }
                    }
                    
                    for(int intPosVal = 0; intPosVal<intDiaLabPos.length;intPosVal++){
                        if(intDiaLabPos[intPosVal]==x){
                             objTblMod.setColumnDataType(x+i, objTblMod.INT_COL_INT, new Integer(0), null);
                             tcmAux.getColumn(x+i).setCellRenderer(objTblCelRenLblDL);
                        }
                    }
                    
                    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                        int intFilSel;//=tblDat.getSelectedRow();
                        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }

                        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }
                    });
                }
            }
           
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);

            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
                
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes=true;
        }catch(Exception e) { e.printStackTrace(); blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
    }
    
    private boolean agregarColTblDat(){

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
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_EMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_OFI));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_OFI));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TRA));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_APE_TRA));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECINICON));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECFINCON));

            objTblHeaColGrpEmp=new ZafTblHeaColGrp("Totales");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DIALAB));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_VALPEN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_VALPAG));

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);


            int intCont=INT_TBL_VALPAG+1;

            for(Iterator<String> it = arrLstMeses.iterator(); it.hasNext();){
                String strMes = it.next().toString();
                System.out.println("mes: " + strMes);
                objTblHeaColGrpEmp=new ZafTblHeaColGrp(strMes);
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                for (i=0; i<4 ; i++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(intCont));
                    intCont++;
                }
            }

            objTblHeaColGrpEmp=new ZafTblHeaColGrp("Totales");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            for(int intPos=0; intPos<intCabDCS.length;intPos++){
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(intCabDCS[intPos]));
//                            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(57));
            }
                
        }catch(Exception e){
            blnRes=false;
            e.printStackTrace();
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
   
    private class ZafThreadGUI extends Thread{
        public void run(){

            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            if (!cargarDat())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                //lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }else{
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
            }

            objThrGUI=null;
        }
    }
    
    /**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.ResultSet rstLoc = null;
  java.sql.Statement stmLocAux=null;
  java.sql.ResultSet rstLocAux = null;
  String strSql="";
  String sqlFilEmp = "";
  Vector vecDataCon;
  try{
   
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          
      if(conn!=null){
          
          stmLoc=conn.createStatement();
          stmLocAux = conn.createStatement();
          vecDataCon = new Vector();

          if(txtCodEmp.getText().compareTo("")!=0){
              sqlFilEmp+= " and a1.co_emp  = " + txtCodEmp.getText() + " ";
          }

          if(txtCodTra.getText().compareTo("")!=0){
              sqlFilEmp+= " AND a1.co_tra  = " + txtCodTra.getText() + " ";
          }

          if(txtCodOfi.getText().compareTo("")!=0){
              sqlFilEmp+= " AND a1.co_ofi  = " + txtCodOfi.getText() + " ";
          }
          
          String strEmp="";
          if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
              strEmp=" and a1.co_emp = "+ objParSis.getCodigoEmpresa();
          }
          
          
          String strFecHas="";
          String strFecDes="";
          Calendar cal = Calendar.getInstance();
          
          String strFilBenDes = "";
          String strFilBenHas = "";
          String strSqlOfi="";
          
          double dblValPenTra=0;
          double dblValPagTra=0;
          
          if(jRBCI.isSelected()){
              cal.set(Calendar.MONTH, 1);
              strFecHas = cboPerAAAA.getSelectedItem().toString() + "-02-"+cal.getActualMaximum(Calendar.DATE);
              
              cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1));
              cal.set(Calendar.MONTH, 2);
              strFecDes = cal.get(Calendar.YEAR) + "-03-"+cal.getActualMinimum(Calendar.DATE);
              strFilBenDes = "AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (3,4,5,6,7,8,9,10,11,12))";
              strFilBenHas = "OR (a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2)))";
              
              strSqlOfi = " AND a1.co_ofi IN (1,3,4,5,6)";
              
          }else if (jRBSO.isSelected()){
              cal.set(Calendar.MONTH, 6);
              strFecHas = cboPerAAAA.getSelectedItem().toString() + "-07-"+cal.getActualMaximum(Calendar.DATE);
              
              cal.set(Calendar.YEAR, (Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1));
              cal.set(Calendar.MONTH, 7);
              strFecDes = cal.get(Calendar.YEAR) + "-08-"+cal.getActualMinimum(Calendar.DATE);
              strFilBenDes = "AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (8,9,10,11,12))";
              strFilBenHas = "OR (a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2,3,4,5,6,7)))";
              
              strSqlOfi = " AND a1.co_ofi IN (2)";
          }
          
          String strTipTra = "";
          String strEstConf = "";
          if(optFil.isSelected()){
                    
              if(jChBAdmin.isSelected()) { if(strEstConf.equals("")) strEstConf+="'A'"; else strEstConf+=",'A'"; }
              if(jChBVen.isSelected()) { if(strEstConf.equals("")) strEstConf+="'V'"; else strEstConf+=",'V'"; }
              if(jChBOtr.isSelected()) { if(strEstConf.equals("")) strEstConf+="null"; else strEstConf+=",null"; }
                    
              if(!strEstConf.equals("")){
                  strTipTra+=" and a1.tx_tiptra IN ("+strEstConf+") ";
              }
          }
          
          
          strSql="";
          strSql+=" SELECT distinct a.co_emp, a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as empleado, a.co_emp, c.tx_nom as empresa, a1.co_ofi, \n ";
          strSql+="d.tx_nom as oficina, a1.fe_inicon, a1.fe_reafincon,  case ((current_date-a1.fe_inicon)/360)=0 when true then true else false  end as blnIsTraNew  , " + "\n";
          strSql+="((current_date-a1.fe_inicon)/360) as ne_numAnioLab , extract (MONTH from a1.fe_inicon) as ne_mesEnt, \n ";
          strSql+="extract (YEAR from a1.fe_inicon) as ne_aniEnt, \n";
          strSql+="(select sum((case(nd_valdeccuasue is null) when true then 0 else nd_valdeccuasue end ) -(case(nd_valpagdeccuasue is null) when true then 0 else nd_valpagdeccuasue end ) ) as valDecCuaSue " + "\n";
//          strSql+="case ( (EXTRACT (MONTH FROM fe_inicon)="+intMes+") AND (EXTRACT (YEAR FROM fe_inicon)="+intAño+") ) when true then true else false end as blnIsTraNew , " + "\n";
          strSql+="from tbm_datgeningegrmentra a  \n ";
          strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro=1) \n ";
          strSql+="where a.co_emp = a1.co_emp \n ";
          strSql+="and a.co_tra =a1.co_tra \n ";
//          if(objParSis.getCodigoEmpresa()==1 || objParSis.getCodigoEmpresa()==4){
//              strSql+="AND  ( ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (3,4,5,6,7,8,9,10,11,12))) \n ";
//              strSql+="OR ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2))) )) as nd_valdeccuasue  \n ";
//          }else{
//              strSql+="AND  ( ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (8,9,10,11,12))) \n ";
//              strSql+="OR ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2,3,4,5,6,7))) )) as nd_valdeccuasue  \n ";
//          }
          if(jRBCI.isSelected()){
              strSql+="AND  ( ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (3,4,5,6,7,8,9,10,11,12))) \n ";
              strSql+="OR ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2))) )) as nd_valdeccuasue  \n ";
          }
          if(jRBSO.isSelected()){
              strSql+="AND  ( ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1)+") AND a.ne_mes in (8,9,10,11,12))) \n ";
              strSql+="OR ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString()))+") AND a.ne_mes in (1,2,3,4,5,6,7))) )) as nd_valdeccuasue  \n ";
          }
          strSql+="from tbm_bensocmentra a \n";
          strSql+="INNER JOIN tbm_traemp a1 on (a1.co_emp = a.co_emp and a1.co_tra = a.co_tra) \n";
          strSql+="INNER JOIN tbm_tra b on (b.co_tra=a.co_tra) \n";
          strSql+="INNER JOIN tbm_emp c on (c.co_emp=a.co_emp) \n";
          strSql+="LEFT OUTER JOIN tbm_ofi d on (d.co_ofi=a1.co_ofi) \n";
          strSql+="WHERE ((a1.fe_inicon<="+objUti.codificar(strFecDes) +" and a1.st_reg = 'A') or (a1.fe_inicon is null and a1.st_reg='A') or (a1.fe_inicon between "+objUti.codificar(strFecDes) +" and "+objUti.codificar(strFecHas) +")) \n";
          strSql+=" AND a1.st_reg = 'A' " + "\n";
          strSql+=" " + strSqlOfi + "  \n";
          strSql+=" " + sqlFilEmp + "  \n";
          strSql+=" " + strEmp + "  \n";
          strSql+=" " + strTipTra + "  \n";
          strSql+= " " + strFilBenDes + "  \n";
          strSql+= " " + strFilBenHas + "  \n";
          strSql+="ORDER BY a.co_emp, empleado";
              
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
              
          vecDat = new Vector();
          while(rstLoc.next()){

//              if(rstLoc.getDouble("nd_valdeccuasue")>0){
              
                java.util.Vector vecReg = new java.util.Vector();
                Boolean bln=(Boolean)rstLoc.getBoolean("blnIsTraNew");
                if(bln){
                    vecReg.add(INT_TBL_DAT_LIN,"S");
                }else{
                    vecReg.add(INT_TBL_DAT_LIN,"");
                }
//                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_COD_EMP,rstLoc.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("empresa"));
                vecReg.add(INT_TBL_DAT_COD_OFI,rstLoc.getInt("co_ofi"));
                vecReg.add(INT_TBL_DAT_NOM_OFI,rstLoc.getString("oficina"));
                vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getInt("co_tra"));
                if(rstLoc.getInt("co_tra")==101){
                    System.out.println("ES UN NUEVO .... ");
                }
                System.out.println("EMPLEADO: "  + rstLoc.getString("empleado"));
                vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("empleado"));
                vecReg.add(INT_TBL_DAT_FECINICON,rstLoc.getString("fe_inicon"));
                vecReg.add(INT_TBL_DAT_FECFINCON,rstLoc.getString("fe_reafincon"));
                vecReg.add(INT_TBL_DIALAB,"");
                vecReg.add(INT_TBL_VALPEN,"");
                vecReg.add(INT_TBL_VALPAG,"");

                int intNeTipPro = 0;
                if(objParSis.getCodigoMenu()==3581){
                    intNeTipPro=1;
                }else if(objParSis.getCodigoMenu()==3605){
                    intNeTipPro=2;
                }else if(objParSis.getCodigoMenu()==3608){
                    intNeTipPro=3;
                }

                strSql="";
                strSql+="select * from tbm_datgeningegrmentra a " + "\n";
                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")" + "\n";
                strSql+="where a.co_emp = "+objParSis.getCodigoEmpresa() + "\n";
                strSql+="and a.co_tra = "+rstLoc.getString("co_tra")  + "\n";
//                if(objParSis.getCodigoEmpresa()==1 || objParSis.getCodigoEmpresa()==4){
//                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND a.ne_mes in (3,4,5,6,7,8,9,10,11,12)))"  + " \n"; 
//                }else if(objParSis.getCodigoEmpresa()==2){
//                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND a.ne_mes in (8,9,10,11,12)))"  + " \n"; 
//                }

                if(jRBCI.isSelected()){
                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND a.ne_mes in (3,4,5,6,7,8,9,10,11,12)))"  + " \n"; 
                }
                if(jRBSO.isSelected()){
                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())-1) +") AND a.ne_mes in (8,9,10,11,12)))"  + " \n"; 
                }

                strSql+="UNION"  + " \n"; 

                strSql+="select * from tbm_datgeningegrmentra a " + "\n";
                strSql+="left outer join tbm_bensocmentra b on (b.co_emp=a.co_emp and b.co_tra=a.co_tra and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_tippro="+intNeTipPro+")"  + " \n"; 
                strSql+="where a.co_emp = "+objParSis.getCodigoEmpresa() + "\n";
                strSql+="and a.co_tra = "+rstLoc.getString("co_tra")  + "\n";
//                if(objParSis.getCodigoEmpresa()==1 || objParSis.getCodigoEmpresa()==4){
//                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND a.ne_mes in (1,2)))"  + " \n"; 
//                }else{
//                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND a.ne_mes in (1,2,3,4,5,6,7)))"  + " \n"; 
//                }

                if(jRBCI.isSelected()){
                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND a.ne_mes in (1,2)))"  + " \n"; 
                }
                if(jRBSO.isSelected()){
                    strSql+="AND ((a.ne_ani in ("+(Integer.parseInt(cboPerAAAA.getSelectedItem().toString())) +") AND a.ne_mes in (1,2,3,4,5,6,7)))"  + " \n"; 
                }
                

                System.out.println("q consulTra: " + strSql);
                rstLocAux = stmLocAux.executeQuery(strSql);
                int intNeMesPos=0;
                
                double dblValPenDCSTra=0;
                double dblValPagDCSTra=0;
                int intDiaLabTra=0;
                        
                while(rstLocAux.next()){
                    
                    
                
                    String strNdValDecCuaSue = rstLocAux.getString("nd_valdeccuasue");
                    String strFeIniCon = rstLoc.getString("fe_inicon");
                    if(strFeIniCon==null){
                        
//                        if(strNdValDecCuaSue==null){
//                            vecReg.add(vecReg.size(), null);
//                            vecReg.add(vecReg.size(), null);
//                            vecReg.add(vecReg.size(), null);
//                            vecReg.add(vecReg.size(), null);
//                        }else{
//                            vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valSbu"));
//                            vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
//                            vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecCuaSue"));
//                            vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecCuaSue"));
//                            dblValPenDCSTra+=rstLocAux.getDouble("nd_valDecCuaSue");
//                            dblValPagDCSTra+=rstLocAux.getDouble("nd_valPagDecCuaSue");
//                            intNeMesPos++;
//                        }
                        do{
                                  strNdValDecCuaSue = rstLocAux.getString("nd_valdeccuasue");
                                  if(strNdValDecCuaSue==null){
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      intNeMesPos++;
                                  }else{
                                      vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valSbu"));
                                      vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
                                      intDiaLabTra+=rstLocAux.getInt("ne_numdialab");
                                      vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecCuaSue"));
                                      vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecCuaSue"));
                                      dblValPenDCSTra+=rstLocAux.getDouble("nd_valDecCuaSue");
                                      dblValPagDCSTra+=rstLocAux.getDouble("nd_valPagDecCuaSue");
                                      intNeMesPos++;
                                  }
                              }while(rstLocAux.next());
                        

                    }else{

                          if(rstLoc.getInt("ne_numAnioLab")==0){
                          if(rstLoc.getInt("ne_mesent")==1){

                              do{
                                  strNdValDecCuaSue = rstLocAux.getString("nd_valdeccuasue");
                                  if(strNdValDecCuaSue==null){
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      intNeMesPos++;
                                  }else{
                                      vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valSbu"));
                                      vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
                                      intDiaLabTra+=rstLocAux.getInt("ne_numdialab");
                                      vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecCuaSue"));
                                      vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecCuaSue"));
                                      dblValPenDCSTra+=rstLocAux.getDouble("nd_valDecCuaSue");
                                      dblValPagDCSTra+=rstLocAux.getDouble("nd_valPagDecCuaSue");
                                      intNeMesPos++;
                                  }
                              }while(rstLocAux.next());

                          }else if(rstLoc.getInt("ne_mesent")>1){

                              cal = Calendar.getInstance();
                              int intMesAct = cal.get(Calendar.MONTH);

                              if(intMesAct<rstLoc.getInt("ne_mesent")){

                                      do{
                                           strNdValDecCuaSue = rstLocAux.getString("nd_valdeccuasue");
                                          if(strNdValDecCuaSue==null){
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                               intNeMesPos++;
                                          }else{
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valSbu"));
                                              vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
                                              intDiaLabTra+=rstLocAux.getInt("ne_numdialab");
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecCuaSue"));
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecCuaSue"));
                                              dblValPenDCSTra+=rstLocAux.getDouble("nd_valDecCuaSue");
                                              dblValPagDCSTra+=rstLocAux.getDouble("nd_valPagDecCuaSue");
                                              intNeMesPos++;
                                          }
                                  }while(rstLocAux.next());
                              }
                              else if(intMesAct==rstLoc.getInt("ne_mesent")){

                                  int intPosValMax=0;
                                  if(objParSis.getCodigoEmpresa()==1 ||objParSis.getCodigoEmpresa()==4){
                                      intPosValMax=((rstLoc.getInt("ne_mesent")-1)-2);
                                      for(int intFil=0;intFil<intPosValMax;intFil++){

                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      vecReg.add(vecReg.size(), null);
                                      intNeMesPos++;
                                      }
                                  }
                                  do{
                                      strNdValDecCuaSue = rstLocAux.getString("nd_valdeccuasue");
                                      if(strNdValDecCuaSue==null){
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              intNeMesPos++;
                                          }else{
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valSbu"));
                                              vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
                                              intDiaLabTra+=rstLocAux.getInt("ne_numdialab");
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecCuaSue"));
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecCuaSue"));
                                              dblValPenDCSTra+=rstLocAux.getDouble("nd_valDecCuaSue");
                                              dblValPagDCSTra+=rstLocAux.getDouble("nd_valPagDecCuaSue");
                                              intNeMesPos++;
                                          }
                                  }while(rstLocAux.next());

                              }
                              else{

                                  int intPosValMax=0;
                                  if(objParSis.getCodigoEmpresa()==1 ||objParSis.getCodigoEmpresa()==4){
                                      intPosValMax=((rstLoc.getInt("ne_mesent")-1)-2);
                                      for(int intFil=0;intFil<intPosValMax;intFil++){
                                          vecReg.add(vecReg.size(), null);
                                          vecReg.add(vecReg.size(), null);
                                          vecReg.add(vecReg.size(), null);
                                          vecReg.add(vecReg.size(), null);
                                          intNeMesPos++;
                                      }
                                  }

                                  do{
                                      strNdValDecCuaSue = rstLocAux.getString("nd_valdeccuasue");
                                          if(strNdValDecCuaSue==null){
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              vecReg.add(vecReg.size(), null);
                                              intNeMesPos++;
                                          }else{
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valSbu"));
                                              vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
                                              intDiaLabTra+=rstLocAux.getInt("ne_numdialab");
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecCuaSue"));
                                              vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecCuaSue"));
                                              dblValPenDCSTra+=rstLocAux.getDouble("nd_valDecCuaSue");
                                              dblValPagDCSTra+=rstLocAux.getDouble("nd_valPagDecCuaSue");
                                              intNeMesPos++;
                                          }
                                  }while(rstLocAux.next());
                              }
                          }

                      }else{
                          if(strNdValDecCuaSue==null){
                            vecReg.add(vecReg.size(), null);
                            vecReg.add(vecReg.size(), null);
                            vecReg.add(vecReg.size(), null);
                            vecReg.add(vecReg.size(), null);
                            intNeMesPos++;
                        }else{
                            vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valSbu"));
                            vecReg.add(vecReg.size(),rstLocAux.getInt("ne_numdialab"));
                            intDiaLabTra+=rstLocAux.getInt("ne_numdialab");
                            vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valDecCuaSue"));
                            vecReg.add(vecReg.size(),rstLocAux.getDouble("nd_valPagDecCuaSue"));
                            dblValPenDCSTra+=rstLocAux.getDouble("nd_valDecCuaSue");
                            dblValPagDCSTra+=rstLocAux.getDouble("nd_valPagDecCuaSue");
                            intNeMesPos++;
                        }
                      }
                    }
                }

                if(intNeMesPos<12){
                    for(int intFil=intNeMesPos; intFil<arrLstMeses.size();intFil++){
                        vecReg.add(vecReg.size(), null);
                        vecReg.add(vecReg.size(), null);
                        vecReg.add(vecReg.size(), null);
                        vecReg.add(vecReg.size(), null);
                    }
                }
                if(vecReg.size()!=56 ){
                    System.err.println(rstLoc.getString("empleado"));
                }
                
                
                if((dblValPenDCSTra-dblValPagDCSTra)>0){
                    vecReg.add(vecReg.size(), intDiaLabTra);
                    vecReg.add(vecReg.size(), objUti.redondear(dblValPenDCSTra,objParSis.getDecimalesMostrar()));
                    vecReg.add(vecReg.size(), objUti.redondear(dblValPagDCSTra,objParSis.getDecimalesMostrar()));
                    vecReg.set(INT_TBL_DIALAB,intDiaLabTra);
                    vecReg.set(INT_TBL_VALPEN,objUti.redondear(dblValPenDCSTra,objParSis.getDecimalesMostrar()));
                    vecReg.set(INT_TBL_VALPAG,objUti.redondear(dblValPagDCSTra,objParSis.getDecimalesMostrar()));
                }else{
                    vecReg.add(vecReg.size(), intDiaLabTra);
                    vecReg.add(vecReg.size(), null);
                    vecReg.add(vecReg.size(), null);
                    vecReg.set(INT_TBL_DIALAB,intDiaLabTra);
                    vecReg.set(INT_TBL_VALPEN,"");
                    vecReg.set(INT_TBL_VALPAG,"");
                }

                dblValPenDCSTra=0;
                dblValPagDCSTra=0;
                intDiaLabTra=0;
                vecDataCon.add(vecReg);
          }

        objTblMod.setData(vecDataCon);
        tblDat .setModel(objTblMod); 
        objTblTot.calcularTotales();
        lblMsgSis.setText("Listo");
        tabFrm.setSelectedIndex(1);
        lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
        pgrSis.setIndeterminate(false);
        vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  Evt.printStackTrace();objUti.mostrarMsgErr_F1(this, Evt);  }
  finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{rstLocAux.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{stmLocAux.close();}catch(Throwable ignore){}
        try{conn.close();}catch(Throwable ignore){}
    }
    System.gc();
    return blnRes;
}

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;    
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break; 
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_FECINICON:
                    strMsg="Fecha de ingreso del empleado";
                    break;
                case INT_TBL_DAT_FECFINCON:
                    strMsg="Fecha de salida del empleado";
                    break;
                case INT_TBL_DIALAB:
                    strMsg="Días laborados";
                    break;
                case INT_TBL_VALPEN:
                    strMsg="Valor Pendiente";
                    break;
                case INT_TBL_VALPAG:
                    strMsg="Valor Pagado";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConOfi(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoOfi.setCampoBusqueda(2);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.buscar("a1.co_ofi", txtCodOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(1);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoOfi.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoOfi.buscar("a1.tx_nom", txtNomOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(2);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
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

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConOfi()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_ofi");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            String strSQL="select co_ofi, tx_nom from tbm_ofi where st_reg='A'";

            vcoOfi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Oficinas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoOfi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   
    public String retomaMes(int Mes) 
    { 
        String retornaMes = meses[Mes-1]; 
        return retornaMes; 
    } 
}