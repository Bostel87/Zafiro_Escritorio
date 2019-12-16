

package RecursosHumanos.ZafRecHum56;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
//import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.swing.SpinnerNumberModel;

/**
 * Programación de revisión de sueldos.
 * @author  Roberto Flores
 * Guayaquil 11/01/2013
 */
public class ZafRecHum56 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_CHKSEL=1;
    private static final int INT_TBL_DAT_CO_EMP=2;
    private static final int INT_TBL_DAT_NOM_EMP=3;
    private static final int INT_TBL_DAT_COD_TRA=4;
    private static final int INT_TBL_DAT_NOM_APE_TRA=5;
    private static final int INT_TBL_DAT_ND_VALBON=6;
    private static final int INT_TBL_DAT_ND_VALMOV=7;
    private static final int INT_TBL_DAT_ND_VALTOT=8;

    

    //Variables
//    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafThreadPrgAut objThrPA;
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                            //Editor: JTextField en celda.
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                             //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                             //Renderer de Check Box para campo Laborable
    
    private ZafTblCelEdiChk objTblCelEdiChk;                                //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                               //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                             //Renderer de Check Box
    
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafRptSis objRptSis;
//    private ZafMouMotAdaMovReg objMouMotAdaMovReg;                        //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblTot objTblTot;                                            //JTable de totales.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoDep;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafVenCon vcoMotJusAsi;                                         //Ventana de consulta.
    
    private ZafTblCelRenBut objTblCelRenBut;                                //Render: Presentar JButton en JTable.

    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;                        

    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private String strCodMotJus, strDesMot;

    static final int INT_TBL_DAT_NUM_TOT_CDI=22;                            //Número total de columnas dinámicas.
    
    private ZafPerUsr objPerUsr;
    private ZafTblBus objTblBus;

    final int intJspValMin=0;
    final int intJspValMax=100;
    final int intJspValIni=0;
    final int intJspValInc=1;
    
    private String strVersion="v1.03";
    
    private boolean blnMarTodChkTblDep=true;                    //Marcar todas las casillas de verificación del JTable de departamentos.
        
    /** Crea una nueva instancia de la clase ZafRecHum53. */
    public ZafRecHum56(ZafParSis obj)
    {
        try
        {
            initComponents();
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, obj);
            
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            jspPorVarMen.setModel(new SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));
            jspAniPro.setModel(new SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));
            
            cargarPorVarMen();
            cargarAñoApe();
            
            butCon.setVisible(false);
//            butPrgAut.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(3432)){
                butCon.setVisible(true);
            }
//            if(objPerUsr.isOpcionEnabled(3299)){
//                butPrgAut.setVisible(true);
//            }
            if(objPerUsr.isOpcionEnabled(3434)){
                butCer.setVisible(true);
            }
            
            
            
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //{objUti.mostrarMsgErr_F1(this, e);}
        }
    }
    
    private boolean cargarPorVarMen(){
    boolean blnRes=true;
    java.sql.Connection con = null; 
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null; 
    String strSQL="";

    try{
        con =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        if(con!=null){ 
            
            stmLoc=con.createStatement();
            
            strSQL="select nd_porvaringegrmenprgtra from tbm_emp where co_emp="+objParSis.getCodigoEmpresa();
            rstLoc=stmLoc.executeQuery(strSQL);
            while(rstLoc.next()){
                jspPorVarMen.setValue(rstLoc.getDouble("nd_porvaringegrmenprgtra"));
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
    
    private boolean cargarAñoApe(){
        boolean blnRes=true;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        String strSQL="";

        try{
            con =java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){ 

                stmLoc=con.createStatement();

                strSQL="select ne_ani as ne_ani from tbm_aniCreSis where co_emp="+objParSis.getCodigoEmpresa() + " order by ne_ani desc";
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    jCmbAniPro.addItem(rstLoc.getString("ne_ani"));
                }
                jCmbAniPro.setSelectedIndex(0);
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
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jChkEmpMov = new javax.swing.JCheckBox();
        jChkEmpNoBonMov = new javax.swing.JCheckBox();
        jChkEmpBon = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        panCabRpt = new javax.swing.JPanel();
        jspAniPro = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jspPorVarMen = new javax.swing.JSpinner();
        jCmbAniPro = new javax.swing.JComboBox();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butPrgAut = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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
        optTod.setBounds(10, 80, 330, 20);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setSelected(true);
        optFil.setText("Sólo los empleados que cumplan con el criterio seleccionado");
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
        optFil.setBounds(10, 100, 370, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 120, 100, 20);

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
        txtCodEmp.setBounds(140, 120, 60, 20);

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
        txtNomEmp.setBounds(200, 120, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 120, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 140, 100, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(140, 140, 60, 20);

        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(200, 140, 250, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(450, 140, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 160, 100, 20);

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
        txtCodTra.setBounds(140, 160, 60, 20);

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
        txtNomTra.setBounds(200, 160, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 160, 20, 20);

        jChkEmpMov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkEmpMov.setSelected(true);
        jChkEmpMov.setText("Mostrar los empleados que tienen movilizacón");
        jChkEmpMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jChkEmpMovMouseClicked(evt);
            }
        });
        jChkEmpMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkEmpMovActionPerformed(evt);
            }
        });
        panFil.add(jChkEmpMov);
        jChkEmpMov.setBounds(40, 226, 420, 23);

        jChkEmpNoBonMov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkEmpNoBonMov.setText("Mostrar empleados que no tienen bono ni movilización");
        jChkEmpNoBonMov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jChkEmpNoBonMovMouseClicked(evt);
            }
        });
        jChkEmpNoBonMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkEmpNoBonMovActionPerformed(evt);
            }
        });
        panFil.add(jChkEmpNoBonMov);
        jChkEmpNoBonMov.setBounds(40, 190, 420, 23);

        jChkEmpBon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkEmpBon.setSelected(true);
        jChkEmpBon.setText("Mostrar los empleados que tienen bono");
        jChkEmpBon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jChkEmpBonMouseClicked(evt);
            }
        });
        jChkEmpBon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkEmpBonActionPerformed(evt);
            }
        });
        panFil.add(jChkEmpBon);
        jChkEmpBon.setBounds(40, 208, 420, 23);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panRpt.setLayout(new java.awt.BorderLayout());

        panCabRpt.setPreferredSize(new java.awt.Dimension(0, 80));
        panCabRpt.setLayout(null);

        jspAniPro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        panCabRpt.add(jspAniPro);
        jspAniPro.setBounds(290, 40, 90, 20);
        jspAniPro.setVisible(Boolean.FALSE);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Año a programar:"); // NOI18N
        panCabRpt.add(jLabel7);
        jLabel7.setBounds(10, 40, 190, 20);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel8.setText("Porcentaje de variación mensual:"); // NOI18N
        panCabRpt.add(jLabel8);
        jLabel8.setBounds(10, 20, 190, 20);

        jspPorVarMen.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        panCabRpt.add(jspPorVarMen);
        jspPorVarMen.setBounds(200, 20, 90, 20);

        jCmbAniPro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCmbAniPro.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panCabRpt.add(jCmbAniPro);
        jCmbAniPro.setBounds(200, 40, 90, 20);

        panRpt.add(panCabRpt, java.awt.BorderLayout.PAGE_START);

        panGenDet.setLayout(new java.awt.BorderLayout());

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
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panGenDet.add(spnTot, java.awt.BorderLayout.SOUTH);

        panRpt.add(panGenDet, java.awt.BorderLayout.CENTER);

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

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda el \"Porcentaje de variación mensual\"");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butPrgAut.setText("Prg.Aut.");
        butPrgAut.setToolTipText("Realiza la \"Programación anual automática de bono y movilización\" para los empleados seleccionados.");
        butPrgAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrgAutActionPerformed(evt);
            }
        });
        panBot.add(butPrgAut);

        butVisPre.setText("Vista preliminar");
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
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

        if(!jChkEmpNoBonMov.isSelected()){
            if(!jChkEmpBon.isSelected() && !jChkEmpMov.isSelected()){
                jChkEmpBon.setSelected(Boolean.TRUE);
                jChkEmpMov.setSelected(Boolean.TRUE);
            }
        }
        
        
        if (objThrGUI==null) {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

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

    private void butPrgAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrgAutActionPerformed
        if (objThrPA==null){
                objThrPA=new ZafThreadPrgAut();
            }
            else{
                objThrPA=null;
                objThrPA=new ZafThreadPrgAut();
            }
            objThrPA.start();
            
    }//GEN-LAST:event_butPrgAutActionPerformed
   
//    private boolean guardarDat(){
//        boolean blnRes=true;
//        java.sql.Connection con = null; 
//        java.sql.Statement stmLoc = null;
//        java.sql.ResultSet rstLoc = null;
//        String strSql="";
//        
//        try{
//            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
//      
//            if(con!=null){
//                
//                con.setAutoCommit(false);
//                stmLoc=con.createStatement();
//                
//                for(int i=0; i<tblDat.getRowCount();i++){
//                    System.out.println("i = " + i + " ---> " + tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString());
//                    if(tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0){
//                        
//                        Object objCodMot=objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT);
//                        String strCoMot="";
//                        if(objCodMot!=null){
//                            strCoMot=objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT).toString();
//                            if(strCoMot.equals("")){
//                                strCoMot="null";
//                            }
//                        }else{
//                            strCoMot="null";
//                        }
//                        
//                        
//                        String strJusFal="";
//                        if(objTblMod.getValueAt(i, INT_TBL_DAT_CHK_JUS_FAL).equals(true)){
//                            strJusFal=objUti.codificar("S");
//                        }else{
//                            strJusFal="null";
//                        }
//                        
//                        strSql="";
//                        strSql+="select * from tbm_cabconasitra";
//                        strSql+=" where co_tra=" + tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
//                        strSql+=" and fe_dia="+objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_FECHA).toString());
//                        rstLoc=stmLoc.executeQuery(strSql);
//                        
//                        
//                        
//                        if(rstLoc.next()){
//                            strSql="";
//                            strSql+="UPDATE tbm_cabconasitra SET";
//                            strSql+=" ho_entjus= "+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_ENT_JUS)) +" , ";
//                            strSql+=" ho_saljus= "+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_SAL_JUS)) +" , ";
//                            strSql+=" st_jusfal= "+ strJusFal +" , ";
//                            strSql+=" co_motjusfal= "+ strCoMot  +" , ";
//                            strSql+=" tx_obsjusfal= "+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) +" , ";
//                            strSql+=" fe_jusfal= current_timestamp" + " , ";
//                            strSql+=" co_usrjusfal= " + objParSis.getCodigoUsuario() +" , ";
//                            strSql+=" tx_comjusfal= " + objUti.codificar(objParSis.getDireccionIP());
//                            strSql+=" WHERE";
//                            strSql+=" co_tra= " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
//                            strSql+=" and fe_dia= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA));
//                            System.out.println("act falta:" + strSql);
//                            stmLoc.executeUpdate(strSql);
//                        }else{
//                            strSql="";
//                            strSql+="INSERT INTO tbm_cabconasitra(";
//                            strSql+=" co_tra, fe_dia, st_jusfal, tx_obsjusfal, fe_jusfal,";
//                            strSql+=" co_usrjusfal, tx_comjusfal,  ho_entjus,";
//                            strSql+=" ho_saljus, co_motjusfal)";
//                            strSql+=" VALUES (";
//                            strSql+= objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString() + " , ";
//                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA)) + " , ";
//                            strSql+= strJusFal + " , ";
//                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
//                            strSql+=" current_timestamp" + " , ";
//                            strSql+= objParSis.getCodigoUsuario() + " , ";
//                            strSql+= objUti.codificar(objParSis.getDireccionIP()) + " , ";
//                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_ENT_JUS)) +" , ";
//                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_SAL_JUS)) +" , ";
//                            strSql+= strCoMot  +")";
//                            System.out.println("insert falta:" + strSql);
//                            stmLoc.executeUpdate(strSql);
//                        }
//                    }
//                }
//                
//                if(blnRes){
//                    con.commit();
//                    //Inicializo el estado de las filas.
//                    objTblMod.initRowsState();
//                    objTblMod.setDataModelChanged(false);
//                }
//            }
//        }catch(java.sql.SQLException Evt) {
//            try {
//                con.rollback();
//            } catch (SQLException ex) {
//                objUti.mostrarMsgErr_F1(this, Evt);
//            }
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        }catch(Exception Evt) {
//            try {
//                con.rollback();
//            } catch (SQLException ex) {
//                objUti.mostrarMsgErr_F1(this, Evt);
//            }
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, Evt);
//        }finally {
//        try{stmLoc.close();}catch(Throwable ignore){}
//        try{rstLoc.close();}catch(Throwable ignore){}
//        try{con.close();}catch(Throwable ignore){}
//        }
//    return blnRes;
//}
    
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

   }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtNomDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
            
        }
        optTod.setSelected(true);
        optFil.setSelected(false);
//        jChkEmpTieFal.setSelected(false);
//        jChkEmpNoTieFal.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtNomDep.setText("");
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

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        // TODO add your handling code here:
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        // TODO add your handling code here:
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtNomDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

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
    
    public void BuscarDep(String campo,String strBusqueda,int tipo){
        
        vcoDep.setTitle("Listado de Departamentos");
        if(vcoDep.buscar(campo, strBusqueda )) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton()==vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
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
    
//    public void BuscarMot(String campo,String strBusqueda,int tipo){
//        
//        vcoMotJusAsi.setTitle("Listado de Motivos");
//        if(vcoMotJusAsi.buscar(campo, strBusqueda )) {
//            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
//            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
//        }else{
//            vcoMotJusAsi.setCampoBusqueda(tipo);
//            vcoMotJusAsi.cargarDatos();
//            vcoMotJusAsi.show();
//            if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE) {
//                txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
//                txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
//        }else{
//                txtCodMot.setText(strCodDep);
//                txtDesMot.setText(strDesLarDep);
//  }}}
    
    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed
        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
        strDesLarDep=txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
        
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
        if (txtNomDep.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep))
            {
                if (txtNomDep.getText().equals(""))
                {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                }
                else
                {
                    mostrarVenConDep(2);
                }
            }
            else
                txtNomDep.setText(strDesLarDep);
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep=txtCodDep.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConDep(0);
        
    }//GEN-LAST:event_butDepActionPerformed

    
    
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

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

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
                   //google objTooBar.setEnabledVistaPreliminar(true);
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
        String strRutRpt, strNomRpt, strFecHorSer="", strCamAudRpt = "";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String strSql = "", sqlFil = "", sqlAux = "";
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
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {

                            default:

                                //Inicializar los parametros que se van a pasar al reporte.

                                //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }

                                int intAño=Integer.valueOf(jCmbAniPro.getSelectedItem().toString());
//                                int intMes=cboPerMes.getSelectedIndex();
//                                int intNePer=Integer.valueOf(jCmbAniPro.getSelectedItem().toString());
                                                               
//                                if(!(txtCodEmp.getText().equals(""))){
//                                    sqlAux+=" AND c.co_emp="+txtCodEmp.getText()+" ";
//                                }
//
//                                if(!(txtCodDep.getText().equals(""))){
//                                      sqlAux+=" AND c.co_dep="+txtCodDep.getText()+" ";
//                                    }
//
//                                if(!(txtCodTra.getText().equals(""))){
//                                    sqlAux+=" AND c.co_tra="+txtCodTra.getText()+" ";
//                                }
//
//                                String strSqlDep="";
//                                if(objParSis.getCodigoUsuario()!=1){
//                                    strSqlDep="left outer join tbr_depprgusr g on(g.co_dep=c.co_dep and c.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objParSis.getCodigoUsuario()+" "+
//                                                             "and co_mnu="+objParSis.getCodigoMenu()+")) ";
//                                }
//
//                                String strSqlEmp="";
//                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//                                    strSqlEmp=" b.co_emp not in (0,3) ";
//                                }else{
//                                    strSqlEmp=" b.co_emp in ("+ objParSis.getCodigoEmpresa() +") ";
//                                }

                               
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v1.0    ";

//                                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//                   
//                                    if(objZafParSis.getCodigoUsuario()==1){
//                                        strSql="";
//                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
//                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
//                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and a.co_emp=c.co_emp)";
//                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub)";
//                                        strSql+=" where a.ne_ani="+intAño;
//                                        strSql+=" and a.ne_mes="+intMes;
//                                        strSql+=" and a.ne_per="+intNePer;
//                                        strSql+=" " + sqlAux + " ";
//                                    }else{
//                                        
//                                        strSql="";
//                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
//                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
//                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and a.co_emp=c.co_emp)";
//                                        strSql+=" " + strSqlDep + " ";
//                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub)";
//                                        strSql+=" where a.ne_ani="+intAño;
//                                        strSql+=" and a.ne_mes="+intMes;
//                                        strSql+=" and a.ne_per="+intNePer;
//                                        strSql+=" " + sqlAux + " ";
//                                    }
//                                }else{
//                    
//                                    if(objZafParSis.getCodigoUsuario()==1){
//                                        strSql="";
//                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
//                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
//                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and a.co_emp=c.co_emp and c.co_emp="+objZafParSis.getCodigoEmpresa()+")";
//                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub)";
//                                        strSql+=" where a.ne_ani="+intAño;
//                                        strSql+=" and a.ne_mes="+intMes;
//                                        strSql+=" and a.ne_per="+intNePer;
//                                        strSql+=" " + sqlAux + " ";
//                                    }else{
//                                        
//                                        strSql="";
//                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
//                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
//                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and a.co_emp=c.co_emp and c.co_emp="+objZafParSis.getCodigoEmpresa()+")";
//                                        strSql+=" " + strSqlDep + " ";
//                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub)";
//                                        strSql+=" where a.ne_ani="+intAño;
//                                        strSql+=" and a.ne_mes="+intMes;
//                                        strSql+=" and a.ne_per="+intNePer;
//                                        strSql+=" " + sqlAux + " ";
//                                    }
//                                }
//                                
//                                strSql+=" group by a.co_emp,c.co_emp,a.co_rub,c.co_tra,c.co_dep,a.ne_ani,a.ne_mes,a.ne_per,d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub,b.tx_ape,b.tx_nom";
//                                //strSql+=" order by a.co_emp, a.ne_ani, a.ne_mes, empleado,a.co_rub,tx_tiprub desc";
//                                strSql+=" order by a.co_emp, a.ne_ani, a.ne_mes, empleado,tx_tiprub desc";
                                
                                
//                                String strQFDS="";
//                                if(intNePer==1){
//                                    strQFDS="Primera Quincena";
//                                }else if(intNePer==2){
//                                    strQFDS="Segunda Quincena";
//                                }
                                
//                                String strPeriodo=intAño+" - "+ cboPerMes.getSelectedItem().toString() + " - " + strQFDS;
                                
                                String strSqlCoTraElg=null;
                                int intFlgCoTraElg=0;
                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                                    
                                    strSqlCoTraElg="";
                                    strSql+="select co_emp, b.co_tra, (tx_ape || ' ' || tx_nom) as empleado from tbm_traemp a";
                                    strSql+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                                    strSql+=" where a.co_emp in (select distinct co_emp from tbm_emp where co_emp!=0)";
                                    strSql+=" and a.co_tra in (";
                                    
                                    for(int intFil=0; intFil<objTblMod.getRowCount();intFil++){
                        
                                        if(objTblMod.getValueAt(intFil, INT_TBL_DAT_CHKSEL).equals(true)){
                                            strSqlCoTraElg+= objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TRA).toString() + " ";
                                            intFlgCoTraElg++;
                                        }
                                    }
                                    
                                }else{
                                    strSqlCoTraElg="";
                                    strSql+="select co_emp, b.co_tra, (tx_ape || ' ' || tx_nom) as empleado from tbm_traemp a";
                                    strSql+=" inner join tbm_tra b on (b.co_tra=a.co_tra)";
                                    strSql+=" where a.co_emp="+objParSis.getCodigoEmpresa();
                                    strSql+=" and a.co_tra in (";
                                    
                                    for(int intFil=0; intFil<objTblMod.getRowCount();intFil++){
                        
                                        if(objTblMod.getValueAt(intFil, INT_TBL_DAT_CHKSEL).equals(true)){
                                            strSqlCoTraElg+= objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TRA).toString() + " ";
                                            intFlgCoTraElg++;
                                        }
                                    }
                                    
                                }
                                
                                
                                
                                if(strSqlCoTraElg.compareTo("")==0){
//                                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
//                                    txtDesCorTipDoc.requestFocus();
                                    return false;
                                }else{
                                    strSqlCoTraElg=strSqlCoTraElg.substring(0,strSqlCoTraElg.length()-1);
                                    if(intFlgCoTraElg%2==0){
                                        strSqlCoTraElg=strSqlCoTraElg.replace(" ", ",");
                                    }else{
                                        strSqlCoTraElg=strSqlCoTraElg.replace(" ", ",");
                                    }
                                        
                                    strSqlCoTraElg+=")";
                                    strSql+=strSqlCoTraElg;
                                }
                                strSql+="order by empleado";
                                
                                System.out.println("vista reporte:" +strSql);
                                
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strsql", strSql);
//                                mapPar.put("empresa", objParSis.getNombreEmpresa() );
//                                mapPar.put("periodo", strPeriodo);
                                mapPar.put("fecha", datFecAux);
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("aniprg", intAño);
                                //mapPar.put("prueba",vecDataCon);
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
        }catch (Exception e){
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void jChkEmpMovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jChkEmpMovMouseClicked
        // TODO add your handling code here:
//        jChkNovFuePer.setSelected(false);
    }//GEN-LAST:event_jChkEmpMovMouseClicked

    private void jChkEmpNoBonMovMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jChkEmpNoBonMovMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jChkEmpNoBonMovMouseClicked

    private void jChkEmpBonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jChkEmpBonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jChkEmpBonMouseClicked

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
//        if(txtMinGra.getText().equals("")){
//            txtMinGra.setText(" ");
//        }
    }//GEN-LAST:event_tblDatFocusGained

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        java.sql.Connection con = null;
        try{
            
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
                {
                    if(actualizaNdPorVarIngEgrMenPrgTra(con)){
                        con.commit();
                        mostrarMsgInf("La operación se realizó con éxito.");
                    }else{
                        con.rollback();
                        mostrarMsgErr("Ocurrió un error al realizar la operación.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                    }
                }
            }
        }catch(SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
            try{con.close();}catch(Throwable ignore){}
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void jChkEmpNoBonMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkEmpNoBonMovActionPerformed
        // TODO add your handling code here:
        jChkEmpBon.setSelected(Boolean.FALSE);
        jChkEmpMov.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jChkEmpNoBonMovActionPerformed

    private void jChkEmpBonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkEmpBonActionPerformed
        // TODO add your handling code here:
        jChkEmpNoBonMov.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jChkEmpBonActionPerformed

    private void jChkEmpMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkEmpMovActionPerformed
        // TODO add your handling code here:
        jChkEmpNoBonMov.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jChkEmpMovActionPerformed

    private boolean actualizaNdPorVarIngEgrMenPrgTra(Connection con){
        
        boolean blnRes=false;
        
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
        try{
            stmLoc=con.createStatement();
            strSql+="update tbm_emp set nd_porvaringegrmenprgtra = " + jspPorVarMen.getValue();
            strSql+=" where co_emp = " + objParSis.getCodigoEmpresa();
            stmLoc.executeUpdate(strSql);
            blnRes=true;
        }catch(SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
            try{stmLoc.close();}catch(Throwable ignore){}
            try{rstLoc.close();}catch(Throwable ignore){}
        }

        return blnRes;
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
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butPrgAut;
    private javax.swing.JButton butTra;
    private javax.swing.JButton butVisPre;
    private javax.swing.JCheckBox jChkEmpBon;
    private javax.swing.JCheckBox jChkEmpMov;
    private javax.swing.JCheckBox jChkEmpNoBonMov;
    private javax.swing.JComboBox jCmbAniPro;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSpinner jspAniPro;
    private javax.swing.JSpinner jspPorVarMen;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCabRpt;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
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
            
            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            configurarVenConJustificarMotivos();

            //Configurar los JTables.
            configuraTbl();
//            agregarColTblDat();
            
            
             //Configurar ZafSelFec:
//            objSelFec=new ZafSelFec();
//            objSelFec.setTitulo("Rango de fechas");
//            objSelFec.setCheckBoxVisible(false);
//            objSelFec.setCheckBoxChecked(false);
//            panCab.add(objSelFec);
//            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
//            Librerias.ZafDate.ZafDatePicker txtFecDoc;
//            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
//            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
//            txtFecDoc.setHoy();
//            java.util.Calendar objFec = java.util.Calendar.getInstance();
//            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
//            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
//            if(fecDoc!=null){
//                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
//                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
//                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
//            }
//            java.util.Calendar objFecPagActual = objFec.getInstance();
//            objFecPagActual.setTime(objFec.getTime());
//            objFecPagActual.add(java.util.Calendar.DATE, -30 );
//
//            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
//            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
//            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
//            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
//            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
//
//            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //*******************************************************************************

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraTbl(){
        
        boolean blnRes=false;
        
        try{
            
        /*
          private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_CHKSEL=1;
    private static final int INT_TBL_DAT_CO_EMP=2;
    private static final int INT_TBL_DAT_NOM_EMP=3;
    private static final int INT_TBL_DAT_COD_TRA=5;
    private static final int INT_TBL_DAT_NOM_APE_TRA=6;
    private static final int INT_TBL_DAT_ND_VALBON=7;
    private static final int INT_TBL_DAT_ND_VALMOV=8;
    private static final int INT_TBL_DAT_ND_VALTOT=9;
         */
        
        //Configurar JTable: Establecer el modelo.
        vecCab = new Vector();
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_LIN,"");
        vecCab.add(INT_TBL_DAT_CHKSEL,"");
        vecCab.add(INT_TBL_DAT_CO_EMP,"Cód.Emp.");
        vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
        vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
        vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
        vecCab.add(INT_TBL_DAT_ND_VALBON,"Rubro1");
        vecCab.add(INT_TBL_DAT_ND_VALMOV,"Rubro2");
        vecCab.add(INT_TBL_DAT_ND_VALTOT,"Total");

        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
        objTblMod.setColumnDataType(INT_TBL_DAT_ND_VALBON, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_DAT_ND_VALMOV, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_DAT_ND_VALTOT, objTblMod.INT_COL_DBL, new Integer(0), null);
        
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
        tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DAT_CO_EMP).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(300);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALBON).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALMOV).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALTOT).setPreferredWidth(80);
        
        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CO_EMP, tblDat);
        //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);
        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//        objMouMotAda=new ZafMouMotAda();
//        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
        
        //Configurar JTable: Establecer los listener para el TableHeader.
        tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_DAT_CHKSEL);
        objTblMod.addColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        objTblEdi=new ZafTblEdi(tblDat);
        
        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellRenderer(zafTblCelRenChkLab);
        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellEditor(zafTblCelEdiChkLab);
        

        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
//        tcmAux.getColumn(INT_TBL_DAT_MIN_SEC_ASI).setCellRenderer(objTblCelRenLbl);
//        tcmAux.getColumn(INT_TBL_DAT_MIN_SEC_SUG).setCellRenderer(objTblCelRenLbl);
            
        objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
        tcmAux.getColumn(INT_TBL_DAT_ND_VALBON).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALBON).setCellEditor(objTblCelEdiTxt);
        
        tcmAux.getColumn(INT_TBL_DAT_ND_VALMOV).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALMOV).setCellEditor(objTblCelEdiTxt);
        
        tcmAux.getColumn(INT_TBL_DAT_ND_VALTOT).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn(INT_TBL_DAT_ND_VALTOT).setCellEditor(objTblCelEdiTxt);
        
        objTblCelRenLbl=null;
        objTblCelEdiTxt=null;
        
       //Configurar JTable: Editor de búsqueda.
//       objTblBus=new ZafTblBus(tblDat);
       
       int intCol[]= new int[3];
       intCol[0]=6;
       intCol[1]=7;
       intCol[2]=8;
       objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
       
       objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
       blnRes=true;
                
        }catch(Exception e) {  e.printStackTrace(); blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHKSEL)
            {
                if (blnMarTodChkTblDep)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHKSEL);
                    }
                    blnMarTodChkTblDep=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHKSEL);
//                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_PRE);
                    }
                    blnMarTodChkTblDep=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    

//    private boolean agregarColTblDat()
//    {
//
//        int i, intNumFil, intNumColTblDat;
//        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
//        objTblHeaGrp.setHeight(16*2);
//        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
//        java.awt.Color colFonCol;
//        boolean blnRes=true;
//
//        try
//        {
//
//            intNumFil=objTblMod.getRowCountTrue();
//            intNumColTblDat=objTblMod.getColumnCount();
//
//            for (i=0; i<1; i++)
//            {
//                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
//                objTblHeaColGrpEmp.setHeight(16);
////                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKSEL+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CO_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECHA+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_APE_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Horario de trabajo");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_EST+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_EST+i*INT_TBL_DAT_NUM_TOT_CDI));
//
//                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Marcaciones");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_MAR+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_MAR+i*INT_TBL_DAT_NUM_TOT_CDI));
//                
//                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Justificación de faltas");
//                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_FAL+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_JUS_FAL+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_JUS+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_JUS+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_MOT+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_MOT+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_MOT+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_OBS+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_OBS+i*INT_TBL_DAT_NUM_TOT_CDI));
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
//            }
//        }catch(Exception e){
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
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
            objTblTot.calcularTotales();
        }

        objThrGUI=null;
    }
}
    
    private class ZafThreadPrgAut extends Thread{
    public void run(){

        int anio = Integer.valueOf(jCmbAniPro.getSelectedItem().toString());
        if (anio == Calendar.getInstance().get(Calendar.YEAR)) {
            if (mostrarMsgCon("El sistema asignará aleatoriamente el Bono y Movilización a los empleados seleccionados.\n¿Está seguro que desea realizar esta operación?") == 0) {
                Calendar cal = Calendar.getInstance();

                if (procesaDatos(cal.get(Calendar.MONTH) + 1)) {
                    mostrarMsgInf("Programación automática realizada con éxito");
//                for(int intMes=0; intMes<=12; intMes++){
//                    
//                    java.util.Calendar CalVal =  java.util.Calendar.getInstance();
//                    CalVal.set(java.util.Calendar.MONTH,intMes);
//                    int intDiasMes=CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
//                    System.out.println("intMes: " + intMes + "----->"+intDiasMes);
//                    
//                }
                }
            }
        }else{
            mostrarMsgInf("Solo Puede Realizar Programaciones del año actual.");
        
        }
        objThrPA=null;
    }
}
    
    private boolean procesaDatos(int intRecValMesOrgAux){
//        String[] arrVar={"=","+","-","+","-","+","-","+","-","+","-","+"};
        String[] arrVar={"=","-","+","-","+","-","+","-","+","-","+","-"};
        java.sql.Connection con = null;
        java.sql.Statement stmLoc=null;
        java.sql.Statement stmLocAux=null;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstLocAux = null;
        String strFeIniCon=null;
        String strFeFinPerPruCon=null;
        String strFeFinCon=null;
        String strFeReaFinCon=null;
        boolean blnRes=false;
        String strSql="";
        
        try{
            RRHHDao dao = new RRHHDao(objUti, objParSis);
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                stmLoc = con.createStatement();
                stmLocAux = con.createStatement();
                for(int intFil=0; intFil<objTblMod.getRowCount();intFil++){
                    if(objTblMod.getValueAt(intFil, INT_TBL_DAT_CHKSEL).equals(true)){                        
                        
                        double dblBon=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_ND_VALBON)), objParSis.getDecimalesMostrar());
                        double dblMov=objUti.redondear(objUti.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_ND_VALMOV)), objParSis.getDecimalesMostrar());
                        double dblPor= Double.valueOf(jspPorVarMen.getValue().toString() );
                                               
                        if (!dao.generarBoM(con, objTblMod.getValueAt(intFil, INT_TBL_DAT_CO_EMP).toString(),
                                objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TRA).toString(), 
                                    dblBon, dblPor, 6)) {
                            blnRes = false;
                            break;
                        }
                        if (!dao.generarBoM(con, objTblMod.getValueAt(intFil, INT_TBL_DAT_CO_EMP).toString(),
                                objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TRA).toString(), 
                                    dblMov, dblPor, 7)) {
                            blnRes = false;
                            break;
                        }
                    }
                }
                blnRes=true;
            }
            con.commit();
            dao.callServicio9();
        }catch(SQLException e){
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }catch(Exception e){
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
            try{con.close();}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    
    
    /**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.Statement stmLocAux=null;
  java.sql.ResultSet rstLoc = null;
  java.sql.ResultSet rstLocAux = null;
  String strSql="", sqlAux="";
  Vector vecDataCon;

  try{
   
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          
      if(conn!=null){
          
          
          stmLoc=conn.createStatement();
          stmLocAux=conn.createStatement();
          vecDataCon = new Vector();

          
          String sqlFil="";
             if(!(txtCodEmp.getText().equals(""))){
                sqlAux+=" AND b.co_emp="+txtCodEmp.getText()+" ";
            }

            if(!(txtCodDep.getText().equals(""))){
                  sqlAux+=" AND b.co_dep="+txtCodDep.getText()+" ";
                }

            if(!(txtCodTra.getText().equals(""))){
                sqlAux+=" AND b.co_tra="+txtCodTra.getText()+" ";
            }

            String strSqlDep="";
            if(objParSis.getCodigoUsuario()!=1){
                strSqlDep="inner join tbr_depprgusr g on(g.co_dep=b.co_dep and b.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objParSis.getCodigoUsuario()+" "+
                                         "and co_mnu="+objParSis.getCodigoMenu()+")) ";
            }

//            String strSqlEmp="";
//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//                //strSqlEmp=" and d.co_emp not in (0,4) ";
//                strSqlEmp=" b.co_emp not in (0,3) ";
//            }else{
//                //strSqlEmp=" and d.co_emp in ("+ objParSis.getCodigoEmpresa() +") ";
//                strSqlEmp=" b.co_emp in ("+ objParSis.getCodigoEmpresa() +") ";
//            }
//          
          

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                
                strSql+="select b.co_emp, d.tx_nom as empresa ,a.co_rub, b.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado , ";
                strSql+=" a.nd_valrub from tbm_suetra a  ";
                strSql+=" inner join tbm_traemp b on (b.co_tra=a.co_tra and b.co_emp=a.co_emp and b.st_reg='A') ";
                strSql+=" inner join tbm_tra c on (c.co_tra=b.co_tra) ";
                strSql+=" inner join tbm_emp d on (d.co_emp=a.co_emp) ";
                strSql+=" " + strSqlDep + " ";
                strSql+=" where co_rub in (6,7) ";
                strSql+=" and a.co_tra in(";
//                select distinct co_tra from tbm_suetra where co_rub in (6,7) and nd_valrub is not null)   ";
//                strSql+=" order by c.tx_ape || ' ' || c.tx_nom, b.co_emp, a.co_rub asc, b.co_tra";
                
            }else{
                
                strSql+=" select b.co_emp, d.tx_nom as empresa ,a.co_rub, b.co_tra, (c.tx_ape || ' ' || c.tx_nom) as empleado , a.nd_valrub from tbm_suetra a ";
                strSql+=" inner join tbm_traemp b on (b.co_tra=a.co_tra and b.co_emp=a.co_emp and b.st_reg='A')";
                strSql+=" inner join tbm_tra c on (c.co_tra=b.co_tra)";
                strSql+=" inner join tbm_emp d on (d.co_emp=b.co_emp)";
                strSql+=" " + strSqlDep + " ";
                strSql+=" where a.co_emp="+objParSis.getCodigoEmpresa();
                strSql+=" and co_rub in (6,7) and a.co_tra in(";
                
            }
          
          
          if(jChkEmpNoBonMov.isSelected()){
              strSql+=" select distinct co_tra from tbm_suetra where co_emp = b.co_emp and co_rub in (6,7) and nd_valrub is null";
              strSql+=" and co_tra not in (select distinct co_tra from tbm_suetra where co_emp = b.co_emp and co_rub in (6,7) and nd_valrub is not null)";
              strSql+=" )"; 
          }else{
              
              if(jChkEmpBon.isSelected() && jChkEmpMov.isSelected()){
                  strSql+=" select distinct co_tra from tbm_suetra where co_emp = b.co_emp and co_rub in (6,7) and nd_valrub is not null)";
              }
              if(jChkEmpBon.isSelected() && !jChkEmpMov.isSelected()){
                  strSql+=" select distinct co_tra from tbm_suetra where co_emp = b.co_emp and co_rub in (6) and nd_valrub is not null)";
              }
              if(!jChkEmpBon.isSelected() && jChkEmpMov.isSelected()){
                  strSql+=" select distinct co_tra from tbm_suetra where co_emp = b.co_emp and co_rub in (7) and nd_valrub is not null)";
              }
          }
          strSql+=" " + sqlAux+" ";
          strSql+="group by b.co_emp, d.tx_nom  ,a.co_rub, b.co_tra, (c.tx_ape || ' ' || c.tx_nom) , a.nd_valrub ";
          strSql+=" order by c.tx_ape || ' ' || c.tx_nom, b.co_emp, a.co_rub asc, b.co_tra";
              
          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
              
          vecDat = new Vector();

          int intCoTraIn=-1;
          double dblNdValTot = 0;
          java.util.Vector vecReg = null;
          
            while(rstLoc.next()){

                if(rstLoc.getInt("co_tra")==intCoTraIn){
                    vecReg.add(INT_TBL_DAT_ND_VALMOV,rstLoc.getDouble("nd_valrub"));
                    dblNdValTot+=rstLoc.getDouble("nd_valrub");
                    vecReg.add(INT_TBL_DAT_ND_VALTOT,dblNdValTot);
                    vecDataCon.add(vecReg);
                    intCoTraIn =-1;
                    dblNdValTot = 0;
                }else{
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHKSEL,Boolean.FALSE);
                    vecReg.add(INT_TBL_DAT_CO_EMP,rstLoc.getInt("co_emp"));
                    vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("empresa"));
                    vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getInt("co_tra"));
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("empleado"));
                    vecReg.add(INT_TBL_DAT_ND_VALBON,rstLoc.getDouble("nd_valrub"));
                    dblNdValTot = rstLoc.getDouble("nd_valrub");
                    intCoTraIn = rstLoc.getInt("co_tra");
                }
            }

            objTblMod.setData(vecDataCon);
            tblDat .setModel(objTblMod);          

            lblMsgSis.setText("Listo");
            //Asignar vectores al modelo.
            //objTblMod.setData(vecDat);
            //tblDat.setModel(objTblMod);
            tabFrm.setSelectedIndex(1);
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                
            pgrSis.setIndeterminate(false);
            vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
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
    

private long calcularDiferenciaFechas(String strFecDes, String strFecHas){
    
    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
    strFecDes = strFecDes.replaceAll("-", "");
    strFecHas = strFecHas.replaceAll("-", "");
    int intFecDesAño = Integer.valueOf(strFecDes.substring(0, 4));
    int intFecDesMes = Integer.valueOf(strFecDes.substring(4, 6));;
    int intFecDesDia = Integer.valueOf(strFecDes.substring(6, 8));

    int intFecHasAño = Integer.valueOf(strFecHas.substring(0, 4));
    int intFecHasMes = Integer.valueOf(strFecHas.substring(4, 6));;
    int intFecHasDia = Integer.valueOf(strFecHas.substring(6, 8));

    Calendar calFecDes = new GregorianCalendar(intFecDesAño, intFecDesMes-1, intFecDesDia);
    Calendar calFecHas = new GregorianCalendar(intFecHasAño, intFecHasMes-1, intFecHasDia);

    java.sql.Date datFecDes = new java.sql.Date(calFecDes.getTimeInMillis());
    java.sql.Date datFecHas = new java.sql.Date(calFecHas.getTimeInMillis());

    return ( datFecHas.getTime() - datFecDes.getTime() )/MILLSECS_PER_DAY;

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
                case INT_TBL_DAT_CHKSEL:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CO_EMP:
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
                case INT_TBL_DAT_ND_VALBON:
                    strMsg="Bono";
                    break;
                case INT_TBL_DAT_ND_VALMOV:
                    strMsg="Movilización";
                    break;
                case INT_TBL_DAT_ND_VALTOT:
                    strMsg="Total(Bono+Movilización)";
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
     
//     private boolean mostrarVenConMot(int intTipBus){
//        boolean blnRes=true;
//        try{
//            switch (intTipBus){
//                case 0: //Mostrar la ventana de consulta.
//                    vcoMotJusAsi.setCampoBusqueda(2);
//                    vcoMotJusAsi.show();
//                    if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE){
//                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
//                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
//                    }
//                    break;
//                case 1: //Búsqueda directa por "Número de cuenta".
//                    if (vcoMotJusAsi.buscar("a1.co_mot", txtCodMot.getText())){
//                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
//                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
//                    }
//                    else{
//                        vcoMotJusAsi.setCampoBusqueda(0);
//                        vcoMotJusAsi.setCriterio1(11);
//                        vcoMotJusAsi.cargarDatos();
//                        vcoMotJusAsi.show();
//                        if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE){
//                            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
//                            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
//                        }
//                        else{
//                            txtCodMot.setText(strCodMotJus);
//                        }
//                    }
//                    break;
//                case 2: //Búsqueda directa por "Descripción larga".
//                    if (vcoMotJusAsi.buscar("a1.tx_deslar", txtDesMot.getText())){
//                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
//                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
//                    }
//                    else{
//                        vcoMotJusAsi.setCampoBusqueda(1);
//                        vcoMotJusAsi.setCriterio1(11);
//                        vcoMotJusAsi.cargarDatos();
//                        vcoMotJusAsi.show();
//                        if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE){
//                            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
//                            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
//                        }
//                        else{
//                            txtDesMot.setText(strDesMot);
//                        }
//                    }
//                    break;
//            }
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
     
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
            arlAncCol.add("414");
            //Armar la sentencia SQL.

//            String strAux = "";
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                strAux = " and a1.co_emp in ("+objParSis.getCodigoEmpresa() +")";
//            }
            
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
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')" + strAux;
//                strSQL+=" ORDER BY a1.co_emp";
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
    private boolean mostrarVenConDep(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
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
    private boolean configurarVenConDep()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            
            String strSQL="";
            if(objParSis.getCodigoUsuario()==1){
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+")";
            }
            

            vcoDep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objParSis.getCodigoEmpresa() + " " +
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
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConJustificarMotivos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_motjus");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("120");
            //Armar la sentencia SQL.
            String strSQL="";
            strSQL+="SELECT a1.co_motjus,a1.tx_desLar";
            strSQL+=" FROM tbm_motjusconasi AS a1";
            strSQL+=" WHERE a1.st_reg like 'A'";
            strSQL+=" ORDER BY a1.co_motjus";
                
            vcoMotJusAsi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de motivos de justificaciones de asistencias", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMotJusAsi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoMotJusAsi.setCampoBusqueda(1);
            vcoMotJusAsi.setCriterio1(7);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
}