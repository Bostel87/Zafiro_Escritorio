/*
 * ZafVeh07.java
 *
 * Created on Septiembre 2, 2013, 11:47 PM
 */
package Vehiculos.ZafVeh07;
import Vehiculos.ZafVeh08.ZafVeh08;
import Vehiculos.ZafVeh09.ZafVeh09;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;

/**
 *
 * @author  José Marín
 */
public class ZafVeh07 extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private String strAux;
    private boolean blnHayCam;
    private ZafDocLis objDocLis;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL;
    public String strWhere;
    private java.util.Date datFecAux;
    
    private String strVersion="v0.7";
    //version Jose Marin 25/Jun/2014
    
    private String strDocCod;
    private String strDocNom;
    private String strVehCod;
    private String strVehNom;
    private String strDesCorTipDoc;
    private String strChoCod;
    private String strChoNom;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafThreadGUI objThrGUI;
    private Date datFecDoc;                                     //Fecha del documento.
    
    //FECHA 
    private ZafDatePicker dtpFecDoc;
    
    private ZafVenCon vcoDoc;//j
    private ZafVenCon vcoVeh;//j
    private ZafVenCon vcoCho;//j
    private Vehiculos.ZafVeh08.ZafVeh08 objVeh08;
    private Vehiculos.ZafVeh09.ZafVeh09 objVeh09;

    /** Creates new form ZafMae03 */
    public ZafVeh07(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            configurarFrm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

     public ZafVeh07(Librerias.ZafParSis.ZafParSis obj,ZafVeh08 obj1,String strCodDoc) {
      try
       {
          this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          objParSis.setCodigoMenu(3740);          
          initComponents();
          //configurarFrm();
           objVeh08=  obj1;
           objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            //panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle("Tickets de combustible " + strVersion);
            lblTit.setText(strAux);
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            //fin config zafDatePicker
            txtCodVeh.setBackground(objParSis.getColorCamposObligatorios());
            txtNomVeh.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNomDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtValCal.setBackground(objParSis.getColorCamposSistema());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtUltKil.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc2.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDocGas.setBackground(objParSis.getColorCamposSistema());
            txtNomCho.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCho.setBackground(objParSis.getColorCamposObligatorios());
            //no estaba en el diseño de la pantalla 
            txtCodCom.setVisible(false);
            txtValCom.setVisible(false);
            txtCodDoc.setVisible(false);
            txtUniCom.setVisible(false);
            txtRenVeh.setVisible(false);
            configurarDocumentos();
            configurarVehiculos();
    //        System.out.println("Numero de documento a buscar:::..." + strCodDoc);
//            objTooBar.clickConsultar();
            txtCodDoc2.setText(strCodDoc);
//            objTooBar.aceptar();
            
            consultarReg();
            cargarReg();
            cargarCabReg();
            objTooBar.setEstado('l');
            //objTooBar.modificar();
            //afterConsultar();
      //      System.out.println("configurarFrmDesdeConstructor::..");
            //
          this.setClosable(true);
          this.setMaximizable(true);
          this.setIconifiable(true);          
	//  this.getContentPane().add(objTooBar,"South");          
    //      System.out.println("--> "+objParSis.getCodigoMenu() );
          tabFrm.setSelectedIndex(1);
       }
       catch(CloneNotSupportedException e)
       {objUti.mostrarMsgErr_F1(this, e);}  
       catch(Exception e)
       {e.printStackTrace();}  
      }
     
     
      public ZafVeh07(Librerias.ZafParSis.ZafParSis obj,ZafVeh09 obj1,String strCodDoc) {
      try
       {
          this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          objParSis.setCodigoMenu(3740);          
          initComponents();
          //configurarFrm();
          objVeh09=  obj1;
          objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            //panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle("Tickets de combustible " + strVersion);
            lblTit.setText(strAux);
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            //fin config zafDatePicker
            txtCodVeh.setBackground(objParSis.getColorCamposObligatorios());
            txtNomVeh.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNomDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtValCal.setBackground(objParSis.getColorCamposSistema());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtUltKil.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc2.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDocGas.setBackground(objParSis.getColorCamposSistema());
            txtNomCho.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCho.setBackground(objParSis.getColorCamposObligatorios());
            //no estaba en el diseño de la pantalla 
            txtCodCom.setVisible(false);
            txtValCom.setVisible(false);
            txtCodDoc.setVisible(false);
            txtUniCom.setVisible(false);
            txtRenVeh.setVisible(false);
            configurarDocumentos();
            configurarVehiculos();
  //          System.out.println("Numero de documento a buscar:::..." + strCodDoc);
//            objTooBar.clickConsultar();
            txtCodDoc2.setText(strCodDoc);
//            objTooBar.aceptar();
            
            consultarReg();
            cargarReg();
            cargarCabReg();
            objTooBar.setEstado('l');
            //objTooBar.modificar();
            //afterConsultar();
   //         System.out.println("configurarFrmDesdeConstructor::..");
            //
          this.setClosable(true);
          this.setMaximizable(true);
          this.setIconifiable(true);          
	//  this.getContentPane().add(objTooBar,"South");          
 //         System.out.println("--> "+objParSis.getCodigoMenu() );
          tabFrm.setSelectedIndex(1);
       }
       catch(CloneNotSupportedException e)
       {objUti.mostrarMsgErr_F1(this, e);}  
       catch(Exception e)
       {e.printStackTrace();}  
      }
    
//    /** This method is called from within the constructor to
//     * initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is
//     * always regenerated by the Form Editor.
//     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        txtTipVeh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNomDoc = new javax.swing.JTextField();
        butDoc = new javax.swing.JButton();
        txtTipCom = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        panFil1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCodPro1 = new javax.swing.JTextField();
        txtDesLarPro1 = new javax.swing.JTextField();
        lblObs2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblObs4 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        txtDesCorPai1 = new javax.swing.JTextField();
        butPai1 = new javax.swing.JButton();
        lblMarca1 = new javax.swing.JLabel();
        txtCod1 = new javax.swing.JTextField();
        txtNom1 = new javax.swing.JTextField();
        butPrg1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblMarca2 = new javax.swing.JLabel();
        txtCodVeh = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNomVeh = new javax.swing.JTextField();
        butVeh = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtValMan = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtAntKil = new javax.swing.JTextField();
        txtValCal = new javax.swing.JTextField();
        txtUltKil = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtValEfe = new javax.swing.JTextField();
        txtValDoc = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblMarca3 = new javax.swing.JLabel();
        txtCodDoc2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtValDocGas = new javax.swing.JTextField();
        txtValCom = new javax.swing.JTextField();
        txtCodCom = new javax.swing.JTextField();
        txtDesCorDoc = new javax.swing.JTextField();
        txtUniCom = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtCodCho = new javax.swing.JTextField();
        txtNomCho = new javax.swing.JTextField();
        butCho = new javax.swing.JButton();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs6 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        spnObs3 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        txtRenVeh = new javax.swing.JTextField();

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
                formInternalFrameOpened(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setPreferredSize(new java.awt.Dimension(0, 70));
        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 400));

        panFil.setPreferredSize(new java.awt.Dimension(0, 330));
        panFil.setLayout(null);

        txtTipVeh.setEnabled(false);
        panFil.add(txtTipVeh);
        txtTipVeh.setBounds(140, 70, 260, 19);

        jLabel5.setText("Tipo de vehículo:");
        panFil.add(jLabel5);
        jLabel5.setBounds(5, 70, 130, 15);

        txtNomDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDocActionPerformed(evt);
            }
        });
        txtNomDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDocFocusLost(evt);
            }
        });
        panFil.add(txtNomDoc);
        txtNomDoc.setBounds(190, 10, 210, 19);

        butDoc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butDoc.setText("...");
        butDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDocActionPerformed(evt);
            }
        });
        panFil.add(butDoc);
        butDoc.setBounds(400, 10, 20, 20);

        txtTipCom.setEnabled(false);
        panFil.add(txtTipCom);
        txtTipCom.setBounds(140, 90, 260, 19);

        jLabel4.setText("Combustible:");
        panFil.add(jLabel4);
        jLabel4.setBounds(5, 90, 100, 15);

        panFil1.setLayout(null);

        jLabel6.setText("Código:");
        panFil1.add(jLabel6);
        jLabel6.setBounds(10, 8, 110, 15);
        panFil1.add(txtCodPro1);
        txtCodPro1.setBounds(140, 6, 90, 19);
        panFil1.add(txtDesLarPro1);
        txtDesLarPro1.setBounds(140, 50, 440, 19);

        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Código de país:");
        panFil1.add(lblObs2);
        lblObs2.setBounds(10, 30, 100, 14);

        jLabel7.setText("Descripción larga:");
        panFil1.add(jLabel7);
        jLabel7.setBounds(10, 50, 130, 15);

        lblObs4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs4.setText("Observación:");
        panFil1.add(lblObs4);
        lblObs4.setBounds(10, 190, 100, 14);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panFil1.add(spnObs2);
        spnObs2.setBounds(140, 190, 440, 60);

        txtDesCorPai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorPai1ActionPerformed(evt);
            }
        });
        txtDesCorPai1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorPai1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorPai1FocusLost(evt);
            }
        });
        panFil1.add(txtDesCorPai1);
        txtDesCorPai1.setBounds(140, 26, 90, 19);

        butPai1.setText("...");
        butPai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPai1ActionPerformed(evt);
            }
        });
        panFil1.add(butPai1);
        butPai1.setBounds(590, 20, 20, 20);

        lblMarca1.setText("Marca:");
        panFil1.add(lblMarca1);
        lblMarca1.setBounds(10, 70, 39, 15);
        panFil1.add(txtCod1);
        txtCod1.setBounds(140, 70, 90, 19);
        panFil1.add(txtNom1);
        txtNom1.setBounds(230, 70, 160, 19);

        butPrg1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butPrg1.setText("...");
        butPrg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrg1ActionPerformed(evt);
            }
        });
        panFil1.add(butPrg1);
        butPrg1.setBounds(390, 70, 20, 20);

        jLabel8.setText("Tipo de vehiculo:");
        panFil1.add(jLabel8);
        jLabel8.setBounds(10, 90, 120, 15);

        jRadioButton5.setText("Carro");
        panFil1.add(jRadioButton5);
        jRadioButton5.setBounds(140, 90, 59, 23);

        jRadioButton6.setText("Moto");
        panFil1.add(jRadioButton6);
        jRadioButton6.setBounds(200, 90, 60, 23);

        jRadioButton7.setText("Montacarga");
        panFil1.add(jRadioButton7);
        jRadioButton7.setBounds(260, 90, 114, 23);

        jRadioButton8.setText("Otro");
        panFil1.add(jRadioButton8);
        jRadioButton8.setBounds(370, 90, 70, 23);

        jLabel9.setText("Peso Soportado (Kg):");
        panFil1.add(jLabel9);
        jLabel9.setBounds(10, 120, 130, 15);
        panFil1.add(jTextField3);
        jTextField3.setBounds(140, 120, 90, 19);
        panFil1.add(jTextField4);
        jTextField4.setBounds(380, 120, 90, 19);

        jLabel10.setText("Tipo de combustible:");
        panFil1.add(jLabel10);
        jLabel10.setBounds(240, 120, 150, 15);

        panFil.add(panFil1);
        panFil1.setBounds(0, 0, 0, 0);

        lblMarca2.setText("Valor calculado:");
        panFil.add(lblMarca2);
        lblMarca2.setBounds(5, 150, 110, 15);

        txtCodVeh.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVehActionPerformed(evt);
            }
        });
        txtCodVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVehFocusLost(evt);
            }
        });
        panFil.add(txtCodVeh);
        txtCodVeh.setBounds(140, 30, 50, 19);

        jLabel13.setText("Vehículo:");
        panFil.add(jLabel13);
        jLabel13.setBounds(5, 30, 130, 15);

        txtNomVeh.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVehActionPerformed(evt);
            }
        });
        txtNomVeh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVehFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVehFocusLost(evt);
            }
        });
        panFil.add(txtNomVeh);
        txtNomVeh.setBounds(190, 30, 210, 19);

        butVeh.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butVeh.setText("...");
        butVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVehActionPerformed(evt);
            }
        });
        panFil.add(butVeh);
        butVeh.setBounds(400, 30, 20, 20);

        jLabel11.setText("Tipo de documento:");
        panFil.add(jLabel11);
        jLabel11.setBounds(5, 10, 130, 15);

        jLabel14.setText("Valor manual:");
        panFil.add(jLabel14);
        jLabel14.setBounds(5, 170, 100, 15);

        txtValMan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValMan.setEnabled(false);
        txtValMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValManActionPerformed(evt);
            }
        });
        txtValMan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValManFocusLost(evt);
            }
        });
        panFil.add(txtValMan);
        txtValMan.setBounds(140, 170, 90, 19);

        jLabel15.setText("Fecha del documento:");
        panFil.add(jLabel15);
        jLabel15.setBounds(422, 10, 140, 15);

        txtAntKil.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAntKil.setEnabled(false);
        panFil.add(txtAntKil);
        txtAntKil.setBounds(140, 110, 90, 19);

        txtValCal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValCal.setEnabled(false);
        panFil.add(txtValCal);
        txtValCal.setBounds(140, 150, 90, 19);

        txtUltKil.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtUltKil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltKilActionPerformed(evt);
            }
        });
        txtUltKil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUltKilFocusLost(evt);
            }
        });
        panFil.add(txtUltKil);
        txtUltKil.setBounds(140, 130, 90, 19);

        txtCodDoc.setEnabled(false);
        txtCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDocActionPerformed(evt);
            }
        });
        txtCodDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDocFocusLost(evt);
            }
        });
        panFil.add(txtCodDoc);
        txtCodDoc.setBounds(120, 10, 20, 19);

        jLabel16.setText("Kilometraje actual:");
        panFil.add(jLabel16);
        jLabel16.setBounds(5, 130, 130, 15);

        jLabel17.setText("Valor efectivo:");
        panFil.add(jLabel17);
        jLabel17.setBounds(5, 190, 100, 15);

        txtValEfe.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValEfe.setEnabled(false);
        txtValEfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValEfeActionPerformed(evt);
            }
        });
        txtValEfe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValEfeFocusLost(evt);
            }
        });
        panFil.add(txtValEfe);
        txtValEfe.setBounds(140, 190, 90, 19);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.setEnabled(false);
        panFil.add(txtValDoc);
        txtValDoc.setBounds(140, 210, 90, 19);

        jLabel18.setText("Valor del documento:");
        panFil.add(jLabel18);
        jLabel18.setBounds(5, 210, 110, 15);

        jLabel19.setText("Kilometraje anterior:");
        panFil.add(jLabel19);
        jLabel19.setBounds(5, 110, 130, 15);

        jLabel20.setText("Número del documento:");
        panFil.add(jLabel20);
        jLabel20.setBounds(422, 30, 150, 15);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panFil.add(txtNumDoc);
        txtNumDoc.setBounds(575, 30, 90, 19);

        lblMarca3.setText("Código del documento:");
        panFil.add(lblMarca3);
        lblMarca3.setBounds(422, 50, 150, 15);

        txtCodDoc2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc2.setEnabled(false);
        panFil.add(txtCodDoc2);
        txtCodDoc2.setBounds(575, 50, 90, 19);

        jLabel21.setText("Valor Gasolinera:");
        panFil.add(jLabel21);
        jLabel21.setBounds(5, 230, 110, 15);

        txtValDocGas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDocGas.setEnabled(false);
        panFil.add(txtValDocGas);
        txtValDocGas.setBounds(140, 230, 90, 19);

        txtValCom.setEnabled(false);
        panFil.add(txtValCom);
        txtValCom.setBounds(400, 90, 20, 19);

        txtCodCom.setEnabled(false);
        panFil.add(txtCodCom);
        txtCodCom.setBounds(120, 90, 20, 19);

        txtDesCorDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorDocActionPerformed(evt);
            }
        });
        txtDesCorDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorDocFocusLost(evt);
            }
        });
        panFil.add(txtDesCorDoc);
        txtDesCorDoc.setBounds(140, 10, 50, 19);

        txtUniCom.setEnabled(false);
        panFil.add(txtUniCom);
        txtUniCom.setBounds(420, 90, 20, 19);

        jLabel22.setText("Chofer:");
        panFil.add(jLabel22);
        jLabel22.setBounds(5, 50, 130, 15);

        txtCodCho.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodChoActionPerformed(evt);
            }
        });
        txtCodCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodChoFocusLost(evt);
            }
        });
        panFil.add(txtCodCho);
        txtCodCho.setBounds(140, 50, 50, 19);

        txtNomCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomChoActionPerformed(evt);
            }
        });
        txtNomCho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomChoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomChoFocusLost(evt);
            }
        });
        panFil.add(txtNomCho);
        txtNomCho.setBounds(190, 50, 210, 19);

        butCho.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCho.setText("...");
        butCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butChoActionPerformed(evt);
            }
        });
        panFil.add(butCho);
        butCho.setBounds(400, 50, 20, 20);

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs6.setText("Observación2:");
        lblObs6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs6);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        txaObs.setLineWrap(true);
        spnObs1.setViewportView(txaObs);

        txaObs1.setLineWrap(true);
        spnObs3.setViewportView(txaObs1);

        javax.swing.GroupLayout panGenTotObsLayout = new javax.swing.GroupLayout(panGenTotObs);
        panGenTotObs.setLayout(panGenTotObsLayout);
        panGenTotObsLayout.setHorizontalGroup(
            panGenTotObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panGenTotObsLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panGenTotObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnObs1)
                    .addComponent(spnObs3)))
        );
        panGenTotObsLayout.setVerticalGroup(
            panGenTotObsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panGenTotObsLayout.createSequentialGroup()
                .addComponent(spnObs1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(spnObs3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panFil.add(panGenTot);
        panGenTot.setBounds(5, 250, 660, 70);

        txtRenVeh.setEnabled(false);
        panFil.add(txtRenVeh);
        txtRenVeh.setBounds(230, 150, 30, 19);

        jScrollPane1.setViewportView(panFil);

        tabFrm.addTab("General", jScrollPane1);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
// TODO add your handling code here:

}//GEN-LAST:event_formInternalFrameOpened

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
                //Cerrar la conexión si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e){
            dispose();
        }
}//GEN-LAST:event_exitForm

    private void butPrg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrg1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butPrg1ActionPerformed

    private void butPai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPai1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butPai1ActionPerformed

    private void txtDesCorPai1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorPai1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorPai1FocusLost

    private void txtDesCorPai1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorPai1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorPai1FocusGained

    private void txtDesCorPai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorPai1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesCorPai1ActionPerformed

    private void butDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDocActionPerformed
        // TODO add your handling code here:
        strDocCod=txtCodDoc.getText();
        mostrarDocumentos(0);
    }//GEN-LAST:event_butDocActionPerformed

    private void txtNomDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusLost
        // TODO add your handling code here:
        if (!txtNomDoc.getText().equalsIgnoreCase(strDocNom))
        {
            if (txtNomDoc.getText().equals(""))
            {
                txtCodDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            {
                mostrarDocumentos(2);
            }
        }
        else
        txtNomDoc.setText(strDocNom);
    }//GEN-LAST:event_txtNomDocFocusLost

    private void txtNomDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusGained
        // TODO add your handling code here:
        strDocNom=txtNomDoc.getText();
        txtNomDoc.selectAll();
    }//GEN-LAST:event_txtNomDocFocusGained

    private void txtNomDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDocActionPerformed
        // TODO add your handling code here:
        txtNomDoc.transferFocus();
    }//GEN-LAST:event_txtNomDocActionPerformed

    private void txtCodDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDocFocusLost
        // TODO add your handling code here:
        if (!txtCodDoc.getText().equalsIgnoreCase(strDocCod)){
            if (txtCodDoc.getText().equals("")){
                txtCodDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            mostrarDocumentos(1);
        }
        else
        txtCodDoc.setText(strDocCod);
    }//GEN-LAST:event_txtCodDocFocusLost

    private void txtCodDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDocFocusGained
        // TODO add your handling code here:
        strDocCod=txtCodDoc.getText();
        txtCodDoc.selectAll();
    }//GEN-LAST:event_txtCodDocFocusGained

    private void txtCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDocActionPerformed
        // TODO add your handling code here:
        txtCodDoc.transferFocus();
    }//GEN-LAST:event_txtCodDocActionPerformed

    private void txtCodVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVehActionPerformed
         //txtCodVeh.transferFocus();
        txtUltKil.requestFocus();
    }//GEN-LAST:event_txtCodVehActionPerformed

    private void txtCodVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusGained
        strVehCod=txtCodVeh.getText();
        txtCodVeh.selectAll();
    }//GEN-LAST:event_txtCodVehFocusGained

    private void txtCodVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVehFocusLost
        if (!txtCodVeh.getText().equalsIgnoreCase(strVehCod)){
            if (txtCodVeh.getText().equals("")){
                txtCodVeh.setText("");
                txtNomVeh.setText("");
            }
            else
            mostrarVehiculos(1);
        }
        else
            txtCodVeh.setText(strVehCod);
        txtValCal.setText("");
        txtValDoc.setText("");
        txtValDocGas.setText("");
        txtUltKil.setText("");
        txtValMan.setText("");
        txtValEfe.setText("");
        txaObs.setText("");
        txaObs1.setText("");
    }//GEN-LAST:event_txtCodVehFocusLost

    private void txtNomVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVehActionPerformed
        txtNomVeh.transferFocus();
    }//GEN-LAST:event_txtNomVehActionPerformed

    private void txtNomVehFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVehFocusGained
        strVehNom=txtNomVeh.getText();
        txtNomVeh.selectAll();
    }//GEN-LAST:event_txtNomVehFocusGained

    private void txtNomVehFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVehFocusLost
        if (!txtNomVeh.getText().equalsIgnoreCase(strVehNom))
        {
            if (txtNomVeh.getText().equals(""))
            {
                txtCodVeh.setText("");
                txtNomVeh.setText("");
                }
            else
            {
                mostrarVehiculos(2);
            }
        }
        else
        txtNomVeh.setText(strVehNom);
        txtValCal.setText("");
        txtValDoc.setText("");
        txtValDocGas.setText("");
        txtUltKil.setText("");
        txtValMan.setText("");
        txtValEfe.setText("");
        txaObs.setText("");
        txaObs1.setText("");
    }//GEN-LAST:event_txtNomVehFocusLost

    private void butVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVehActionPerformed
        // TODO add your handling code here:
        configurarVehiculos();  // ESTO NECESITA ACTUALIZARCE PARA EL KILOMETRAJE
        strVehCod=txtCodVeh.getText();
        mostrarVehiculos(0);
        txtValCal.setText("");
        txtValDoc.setText("");
        txtValDocGas.setText("");
        txtUltKil.setText("");
        txtValMan.setText("");
        txtValEfe.setText("");
        txaObs.setText("");
        txaObs1.setText("");
    }//GEN-LAST:event_butVehActionPerformed

    private void txtDesCorDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorDocActionPerformed
        txtDesCorDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorDocActionPerformed

    private void txtDesCorDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorDocFocusGained
        strDesCorTipDoc=txtDesCorDoc.getText();
        txtDesCorDoc.selectAll();
    }//GEN-LAST:event_txtDesCorDocFocusGained

    private void txtDesCorDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorDocFocusLost
        if (!txtDesCorDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorDoc.getText().equals(""))
            {
                txtCodDoc.setText("");
                txtDesCorDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            {
                mostrarDocumentos(1);
            }
        }
        else
            txtDesCorDoc.setText(strDesCorTipDoc);     
    }//GEN-LAST:event_txtDesCorDocFocusLost

    private void txtCodChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodChoActionPerformed
        // TODO add your handling code here:
        txtCodCho.transferFocus(); 
    }//GEN-LAST:event_txtCodChoActionPerformed

    private void txtCodChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodChoFocusGained
        // TODO add your handling code here:
        strChoCod=txtCodCho.getText();
        txtCodCho.selectAll();
    }//GEN-LAST:event_txtCodChoFocusGained

    private void txtCodChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodChoFocusLost
        // TODO add your handling code here:
        if (!txtCodCho.getText().equalsIgnoreCase(strChoCod)){
            if (txtCodCho.getText().equals("")){
                txtCodCho.setText("");
                txtNomCho.setText("");
            }
            else
            mostrarChoferes(1);
        }
        else
        txtCodCho.setText(strChoCod);
    }//GEN-LAST:event_txtCodChoFocusLost

    private void txtNomChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomChoActionPerformed
        // TODO add your handling code here:
         txtNomCho.transferFocus();
    }//GEN-LAST:event_txtNomChoActionPerformed

    private void txtNomChoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomChoFocusGained
        // TODO add your handling code here:
        strChoNom=txtNomCho.getText();
        txtNomCho.selectAll();
    }//GEN-LAST:event_txtNomChoFocusGained

    private void txtNomChoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomChoFocusLost
        // TODO add your handling code here:
          if (!txtNomCho.getText().equalsIgnoreCase(strChoNom))
        {
            if (txtNomCho.getText().equals(""))
            {
                txtCodCho.setText("");
                txtNomCho.setText("");
                }
            else
            {
                mostrarChoferes(2);
            }
        }
        else
            txtNomCho.setText(strChoNom);
    }//GEN-LAST:event_txtNomChoFocusLost

    private void butChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butChoActionPerformed
        // TODO add your handling code here:
        strChoCod=txtCodCho.getText();
        mostrarChoferes(0);
    }//GEN-LAST:event_butChoActionPerformed

    private void txtUltKilFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUltKilFocusLost
        // TODO add your handling code here:
        double ren,cap,temp,kil1,kil2=0,valCom;
        BigDecimal bgdRen,bgdTemp, bgdKil1, bgdKil2, bgdValCom;
        String aux;
        boolean ok=true;
        if(txtUltKil.getText().equals("0"))
            txtUltKil.setText("");
        if(txtUltKil.getText().length()>0)
        {
            if(txtAntKil.getText().length()>0) {
                kil1=Double.parseDouble(txtAntKil.getText());
                bgdKil1=BigDecimal.valueOf(Double.parseDouble(txtAntKil.getText()));
            }
            else {
                kil1=0;
                bgdKil1=BigDecimal.valueOf(0);
            }
            if(txtRenVeh.getText().length()>0) {
                ren=Double.parseDouble(txtRenVeh.getText());
                bgdRen=BigDecimal.valueOf(Double.parseDouble(txtRenVeh.getText()));
            }
            else {
                ren=0;
                bgdRen=BigDecimal.valueOf(0);
            }
            if(txtUltKil.getText().length()>0) {
                kil2=Double.parseDouble(txtUltKil.getText());
                bgdKil2=BigDecimal.valueOf(Double.parseDouble(txtUltKil.getText()));
            }
            else{
                bgdKil2=BigDecimal.valueOf(0);
            }
            if(txtValCom.getText().length()>0) {
                valCom=Double.parseDouble(txtValCom.getText());
                bgdValCom=BigDecimal.valueOf(Double.parseDouble(txtValCom.getText()));
            }
            else {
                bgdValCom=BigDecimal.valueOf(0);
                valCom=0;
            }
            if(txtCodVeh.getText().length()>0){
                if(kil2<=kil1){
                    ok=false;
                    mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Kilometraje actual</FONT> No puede ser menor o igual al <FONT COLOR=\"blue\">Kilometraje anterior</FONT><BR>Escriba un nuevo kilometraje y vuelva a intentarlo.</HTML>");
                    txtUltKil.setText("");
                    txtUltKil.requestFocus();
                    txtValCal.setText("");
                    txtValDoc.setText("");
                    txtValDocGas.setText("");
                }
            }
            else{
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Vehículo </FONT> es obligatorio.<BR>Escriba una descripción larga y vuelva a intentarlo.</HTML>");
                txtNomVeh.requestFocus();
                txtValCal.setText("");
                txtValDoc.setText("");
                txtValDocGas.setText("");
                ok=false;
            }
            if(ok==true){
     //           System.out.println("/bdgRen :"+ bgdRen);
                temp=((kil2-kil1)/ren)*valCom;
                bgdTemp=(bgdKil2.subtract(bgdKil1));
                bgdTemp=bgdTemp.divide(bgdRen,0);
                bgdTemp=bgdTemp.multiply(bgdValCom);
      //          System.out.println("BigDecimal antes ROUND: " + bgdTemp);
                bgdTemp=objUti.redondearBigDecimal(bgdTemp, 2);  //temp=valCal
        //        System.out.println("BigDecimal: " + bgdTemp);
          //      System.out.println("temp: " + bgdTemp + " Kill2 " + bgdKil2+ " kill1 " + bgdKil1 + " ren " + bgdRen + " valCom " + bgdValCom);
                temp=objUti.redondear(temp, 2); 
                aux=String.valueOf(temp);
                 System.out.println("aux: " + aux);
                 txtValCal.setText(aux);
                     //Si es gas 
                 if(txtUniCom.getText().equals("E")) {
                     temp=Double.parseDouble(txtValCom.getText());
                     temp=objUti.redondear(temp, 2);
                     aux=String.valueOf(temp);
                     txtValCal.setText(aux);
                 }

                 txtValDoc.setText(aux);
                 txtValDocGas.setText(aux);
                 //Se activan estas caas de texto 
                 txtValMan.setEnabled(true);
                 txtValEfe.setEnabled(true);
            }
        }
        else{
            txtValCal.setText("");
            txtValDoc.setText("");
            txtValDocGas.setText("");
        }
    }//GEN-LAST:event_txtUltKilFocusLost

    private void txtValManFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValManFocusLost
        // TODO add your handling code here:
        double valDoc=0, valMan=0, valEfe=0, valCal=0;
        BigDecimal bgdValDoc,bgdValEfe, bgdValCom,bgdValCal;
        String aux;
//Si encuentra un valor manual//  valDoc = valMan
//Si ENCUENTRA VALOR MANUAL Y VALOR EFECTIVO//  valDoc= valMan - valEfe 
//Si ENCUENTRA VALOR EFECTIVO//  valDoc=valCal - valEfe
        if(txtValMan.getText().equals("0"))
            txtValMan.setText("");
        if(txtValMan.getText().length()>0){
            if(txtValEfe.getText().length()>0){
                valEfe=Double.parseDouble(txtValEfe.getText());
                bgdValEfe=BigDecimal.valueOf(Double.parseDouble(txtValEfe.getText()));
                valDoc=valEfe;
                bgdValDoc=bgdValEfe;
                valDoc=objUti.redondear(valDoc, 2);
                bgdValDoc=objUti.redondearBigDecimal(bgdValDoc, 2);
            }
            else{
                aux=txtValMan.getText();
                txtValDoc.setText(aux);
                txtValDocGas.setText(aux);
            }
        }
        else if(!(txtValMan.getText().length()>0) && txtValEfe.getText().length()<=0)
        {
            aux=txtValCal.getText();
            txtValDoc.setText(aux);
            txtValDocGas.setText(aux);
        }
        else if(!(txtValMan.getText().length()>0) && txtValEfe.getText().length()>0)
        {
            valCal=Double.parseDouble(txtValCal.getText());
            bgdValCal=BigDecimal.valueOf(Double.parseDouble(txtValCal.getText()));
                valEfe=Double.parseDouble(txtValEfe.getText());
                bgdValEfe=BigDecimal.valueOf(Double.parseDouble(txtValEfe.getText()));
                valDoc=valCal-valEfe;
                bgdValDoc=bgdValCal.subtract(bgdValEfe);
                valDoc=objUti.redondear(valDoc, 2);
                bgdValDoc=objUti.redondearBigDecimal(bgdValDoc,2);
                aux=String.valueOf(bgdValDoc);
                txtValDoc.setText(aux);
                txtValDocGas.setText(aux);  
        }
    }//GEN-LAST:event_txtValManFocusLost

    private void txtValEfeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValEfeFocusLost
        // TODO add your handling code here:
        double valDoc=0, valMan=0, valEfe=0, valCal=0;
        BigDecimal bgdValDoc,bgdValEfe, bgdValCom,bgdValCal,bgdValMan;
        String aux="";
        if(txtValEfe.getText().equals("0"))
            txtValEfe.setText("");
        if(txtValEfe.getText().length()>0){
            if(txtValEfe.getText().length()>0 && txtValMan.getText().length()>0){
                bgdValMan=BigDecimal.valueOf(Double.parseDouble(txtValMan.getText()));
                bgdValEfe=BigDecimal.valueOf(Double.parseDouble(txtValEfe.getText()));
                bgdValDoc=bgdValMan.subtract(bgdValEfe);
                bgdValDoc=objUti.redondearBigDecimal(bgdValDoc, 2);
            aux=String.valueOf(bgdValDoc);
            }
            else if(txtValEfe.getText().length()>0){
                bgdValCal=BigDecimal.valueOf(Double.parseDouble(txtValCal.getText()));
                bgdValEfe=BigDecimal.valueOf(Double.parseDouble(txtValEfe.getText()));
                bgdValDoc=bgdValCal.subtract(bgdValEfe);
                bgdValDoc=objUti.redondearBigDecimal(bgdValDoc, 2);
            aux=String.valueOf(bgdValDoc);
            }
            txtValDoc.setText(aux);
            txtValDocGas.setText(aux);  
        }
        else if(!(txtValMan.getText().length()>0) && txtValEfe.getText().length()<=0){
            aux=txtValCal.getText();
            txtValDoc.setText(aux);
            txtValDocGas.setText(aux);
        }
        else if(txtValMan.getText().length()>0 && txtValEfe.getText().length()<=0)
        {
            aux=txtValMan.getText();
            txtValDoc.setText(aux);
            txtValDocGas.setText(aux);
        }
    }//GEN-LAST:event_txtValEfeFocusLost

    private void txtUltKilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltKilActionPerformed
        // TODO add your handling code here:
        txaObs.requestFocus();
    }//GEN-LAST:event_txtUltKilActionPerformed

    private void txtValManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValManActionPerformed
        // TODO add your handling code here:
        txaObs.requestFocus();
    }//GEN-LAST:event_txtValManActionPerformed

    private void txtValEfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValEfeActionPerformed
        // TODO add your handling code here:
        txaObs.requestFocus();
    }//GEN-LAST:event_txtValEfeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCho;
    private javax.swing.JButton butDoc;
    private javax.swing.JButton butPai1;
    private javax.swing.JButton butPrg1;
    private javax.swing.JButton butVeh;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblMarca1;
    private javax.swing.JLabel lblMarca2;
    private javax.swing.JLabel lblMarca3;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblObs4;
    private javax.swing.JLabel lblObs6;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil1;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAntKil;
    private javax.swing.JTextField txtCod1;
    private javax.swing.JTextField txtCodCho;
    private javax.swing.JTextField txtCodCom;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodDoc2;
    private javax.swing.JTextField txtCodPro1;
    private javax.swing.JTextField txtCodVeh;
    private javax.swing.JTextField txtDesCorDoc;
    private javax.swing.JTextField txtDesCorPai1;
    private javax.swing.JTextField txtDesLarPro1;
    private javax.swing.JTextField txtNom1;
    private javax.swing.JTextField txtNomCho;
    private javax.swing.JTextField txtNomDoc;
    private javax.swing.JTextField txtNomVeh;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtRenVeh;
    private javax.swing.JTextField txtTipCom;
    private javax.swing.JTextField txtTipVeh;
    private javax.swing.JTextField txtUltKil;
    private javax.swing.JTextField txtUniCom;
    private javax.swing.JTextField txtValCal;
    private javax.swing.JTextField txtValCom;
    private javax.swing.JTextField txtValDoc;
    private javax.swing.JTextField txtValDocGas;
    private javax.swing.JTextField txtValEfe;
    private javax.swing.JTextField txtValMan;
    // End of variables declaration//GEN-END:variables

    // jota recuerda los 2 +
//    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            
            //Inicializar objetos.
//            System.out.println("configurarFrm::..");
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);  //Titulo y version
            lblTit.setText(strAux);
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            //fin config zafDatePicker
            txtCodVeh.setBackground(objParSis.getColorCamposObligatorios());
            txtNomVeh.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNomDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtValCal.setBackground(objParSis.getColorCamposSistema());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc2.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDocGas.setBackground(objParSis.getColorCamposSistema());
            txtUltKil.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCho.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCho.setBackground(objParSis.getColorCamposObligatorios());
            //no estaba en el diseño de la pantalla 
            txtCodCom.setVisible(false);
            txtValCom.setVisible(false);
            txtCodDoc.setVisible(false);
            txtUniCom.setVisible(false);
            txtRenVeh.setVisible(false);
            
//            //VALORES NO EDITABLES ANTES DE QUE SE HAGA EL CALCULO
//            txtValMan.setEnabled(false);
//            txtValEfe.setEnabled(false);
//            ///////////////////////////////////////
            
            
            configurarDocumentos();
            configurarVehiculos();
            configurarChoferes();
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

 

    
//    /**
//     * Esta clase implementa la interface DocumentListener que observa los cambios que
//     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
//     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
//     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
//     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
//     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
//     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
    
//    /**
//     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
//     * de tipo texto para poder determinar si su contenido a cambiado o no.
//     */
    private void agregarDocLis()
    {
        txtCodVeh.getDocument().addDocumentListener(objDocLis);
        txtTipVeh.getDocument().addDocumentListener(objDocLis);
        txtNomCho.getDocument().addDocumentListener(objDocLis);
        txtAntKil.getDocument().addDocumentListener(objDocLis);
        txtUltKil.getDocument().addDocumentListener(objDocLis);
        txtValCal.getDocument().addDocumentListener(objDocLis);
        txtValMan.getDocument().addDocumentListener(objDocLis);
        txtValEfe.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtValDocGas.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txaObs.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
    } 
    
//    /**
//     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
//     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
//     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
//     * que permiten desplazarse al primero, anterior, siguiente y último registro.
//     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio() {
            try{
//                System.out.println("clickInicio");
                if (!rstCab.isFirst()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() {
            try{
//                System.out.println("clickAnterior::.. ");
                if (!rstCab.isFirst()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickSiguiente() {
            try{
//                System.out.println("clickSiguiente::..");
                if (!rstCab.isLast()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try{
//                System.out.println("clickFin:::... ");
                if (!rstCab.isLast()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            try{
     //           System.out.println("clickInsertar:::...");
 
                if (blnHayCam){
                    isRegPro();
                }
                limpiarFrm();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                mostrarTipDocPre();
                txtCodVeh.requestFocus();
                blnHayCam=false;                
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {            
        }

        public void clickModificar() {
  //          System.out.println("clickModificar::..");
            txtDesCorDoc.setEditable(false);
            txtNomDoc.setEditable(false);
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
   //         System.out.println("clickAnular:::..");
            cargarCabReg();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
   //         System.out.println("insertar:::...");
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
           System.out.println("consultar:::..");
            consultarReg();
            return true;
        }

        public boolean modificar() {
      //      System.out.println("modificar:::...");
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean eliminar() {
            try{
         //       System.out.println("eliminar::...");
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()){
                    rstCab.next();
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }
            return true;
        }

        public boolean anular() {
  //          System.out.println("anular:::.");
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean imprimir() {
  //          System.out.println("imprimir");
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }

        public boolean vistaPreliminar() {
   //         System.out.println("vistaPreliminar");
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
       //         System.out.println("cancelar:::..");
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null){
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
    //        System.out.println("beforeInsertar:::...");
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
     //       System.out.println("beforeModificar:::...");
            strAux=objTooBar.getEstadoRegistro();
            if (!(blnHayCam))
            {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulados.");
                blnRes=false;
            }
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (!isCamVal())
                return false;
            return blnRes;
        }

        public boolean beforeEliminar() {
     //       System.out.println("beforeEliminar::...");
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
   //         System.out.println("beforeAnular:::...");
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
    //        System.out.println("beforeImprimir");
            if(objParSis.getCodigoUsuario()!=1){
                if(consultarEstadoImpresion()){
                    if(consultarAutorizacion()){
                        return true;
                    }
                }
            }
            else {// si es administrador solo se mira si esta autorizado
                if(consultarAutorizacion())
                    return true;
            }
            return false;
        }

        public boolean beforeVistaPreliminar() {
    //        System.out.println("beforeVistaPreliminar");
            if(objParSis.getCodigoUsuario()!=1){
                if(consultarEstadoImpresion()){
                    if(consultarAutorizacion()){
                        return true;
                    }
                }
            }
            else {// si es administrador solo se mira si esta autorizado
                if(consultarAutorizacion())
                    return true;
            }
            return false;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
     //       System.out.println("afterInsertar:..");
            this.setEstado('w');
            blnHayCam=false;
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            objTooBar.setEstado('w');          
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
     //       System.out.println("afterConsultar:::...");
            return true;
        }

        public boolean afterModificar() {
       //     System.out.println("afterModificar:::...");
            blnHayCam=false;
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            objTooBar.setEstado('w');
            cargarReg();
            //regresar();
            objTooBar.afterConsultar();
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
       //     System.out.println("afterImprimir");
            if(objParSis.getCodigoUsuario()!=1){
                cambiarEstadoImpresion();
            }
            return true;
        }

        public boolean afterVistaPreliminar() {
      //      System.out.println("afterVistaPreliminar");
            if(objParSis.getCodigoUsuario()!=1){
                cambiarEstadoImpresion();
            }
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }
    }



//    /**
//     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
//     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
//     * debe llenar o corregir.
//     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

//    /**
//     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
//     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
//     * seleccionando una de las opciones que se presentan.
//     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

//    /**
//     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
//     * para mostrar al usuario un mensaje que indique que los datos se han cargado
//     * con errores y que debe revisar dichos datos.
//     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Número de documento <FONT COLOR=\"red\">ya en uso </FONT>. <BR>.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    

//    /**
//     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
//     * de tipo texto para poder determinar si su contenido a cambiado o no.
//     */
    private boolean isRegPro()
    {
  //      System.out.println("isRegPro:::..");
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    
//    /**
//     * Esta función permite limpiar el formulario.
//     * @return true: Si se pudo limpiar la ventana sin ningún problema.
//     * <BR>false: En el caso contrario.
//     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
     //       System.out.println("limpiarFrm");
            txtCodVeh.setText("");
            txtNomVeh.setText("");
            txtCodDoc.setText("");
            txtDesCorDoc.setText("");
            txtNomDoc.setText("");
            txtTipCom.setText("");
            txtCodDoc2.setText("");
            txtTipVeh.setText("");
            txtAntKil.setText("");
            txtUltKil.setText("");
            txtValCal.setText("");
            txtValMan.setText("");
            txtValEfe.setText("");
            txtValDoc.setText("");
            txtValDocGas.setText("");
            txaObs.setText("");
            txaObs1.setText("");
            txtNumDoc.setText("");
            dtpFecDoc.setText("");
            txtCodCho.setText("");
            txtNomCho.setText("");
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    

//    /**
//     * Esta función inserta el registro en la base de datos.
//     * @return true: Si se pudo insertar el registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
    //        System.out.println("insertarReg::...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertarPro()){
                    con.commit();
                    blnRes=true;           
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
//    /**
//     * Esta función permite insertar la cabecera de un registro.
//     * @return true: Si se pudo insertar la cabecera del registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean insertarPro(){
        int intUltReg;
        double ren,cap,temp,kil1,kil2,valMan,valEfe,valDoc,valCom,tempExi;
        String aux,aux2;
        boolean blnRes=true, exist=true;
        try{
  //          System.out.println("insertarPro::...");
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabTicCom AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc= (SELECT co_tipDoc FROM tbr_tipDocPrg";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
		strSQL+=" AND st_reg='S'";
                strSQL+=" )";
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                aux2=String.valueOf(intUltReg);
                txtCodDoc2.setText(aux2);
                //Obtener el numero de doc último registro SI NO LO INGRESA EL USUARIO.
                if(txtNumDoc.getText().length()<=0){
                    strSQL="";
                    strSQL+=" SELECT MAX(a1.ne_numDoc)";
                    strSQL+=" FROM tbm_cabTicCom AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc= (SELECT co_tipDoc FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltReg==-1)
                        return false;
                    intUltReg++;
                    aux2=String.valueOf(intUltReg);
                    txtNumDoc.setText(aux2);
                }
                //Validacion de numero de documento 
                if(txtNumDoc.getText().length()>0){
                    strSQL="";
                    strSQL+=" SELECT case when a1.ne_NumDoc IS NULL then '0' else a1.ne_NumDoc end";
                    strSQL+=" FROM tbm_cabTicCom a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc="+ objParSis.getCodigoLocal()+ " AND ";
                    strSQL+=" a1.ne_NumDoc=" + txtNumDoc.getText() + " AND a1.co_tipDoc in( ";
                    strSQL+=" SELECT co_tipDoc FROM tbr_tipDocPrg WHERE co_emp="+ objParSis.getCodigoEmpresa();
                    strSQL+=" and co_loc="+ objParSis.getCodigoLocal()+ " and co_mnu="+ objParSis.getCodigoMenu() + ")";
                    tempExi=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
      //              System.out.println("tempExi:" + tempExi + " Query: " + strSQL);
                    if (tempExi>0){
                        mostrarMsgAdv("No. de ticket ya existe... ?");
                        exist=false;
                        return false;
                    }                    
                }
                //Armar la sentencia SQL.,
                if(exist==true)
                {
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_cabTicCom (co_emp,co_loc,co_tipDoc,co_doc,fe_doc,"; 
                    strSQL+=" ne_numDoc,co_veh,co_com,nd_pre,ne_kilAnt,ne_kilAct,nd_valCal,";
                    strSQL+=" nd_valMan,nd_valEfe,nd_valDoc,nd_valDocGas,tx_obs1,tx_obs2,co_cho,";
                    if(txtValMan.getText().length()>0)
                        strSQL+=" st_aut,";
                    strSQL+=" st_reg,fe_ing,co_usrIng)";        
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa();
                    strSQL+=", " + objParSis.getCodigoLocal();
                    strSQL+=", " + objUti.codificar(txtCodDoc.getText(),2);
                    strSQL+=", " + objUti.codificar(txtCodDoc2.getText(),2);
                    strSQL+=",'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; 
                    strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);
                    strSQL+=", " + objUti.codificar(txtCodVeh.getText(),2);
                    strSQL+=", " + objUti.codificar(txtCodCom.getText(),2);
                    strSQL+=", " + objUti.codificar(txtValCom.getText(),2);
                    if(txtAntKil.getText().length()>0)
                        strSQL+=", " + objUti.codificar(txtAntKil.getText(),2);
                    else
                        strSQL+=",0";
                    strSQL+=", " + objUti.codificar(txtUltKil.getText(),2);
                    strSQL+=", " + objUti.codificar(txtValCal.getText(),2);
                    strSQL+=", " + objUti.codificar(txtValMan.getText(),2);
                    strSQL+=", " + objUti.codificar(txtValEfe.getText(),2);
                    strSQL+=", " + objUti.codificar(txtValDoc.getText(),2);
                    strSQL+=", " + objUti.codificar(txtValDocGas.getText(),2);
                    strSQL+=", " + objUti.codificar(txaObs.getText());
                    strSQL+=", " + objUti.codificar(txaObs1.getText()); 
                    strSQL+=", " + objUti.codificar(txtCodCho.getText()); 
                    if(txtValMan.getText().length()>0)
                        strSQL+=",'P'";
                    strSQL+=",'A','" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                    strSQL+=", " + objParSis.getCodigoUsuario() + ");";
                    strSQL+=" UPDATE tbm_veh SET ne_ultKil=" + objUti.codificar(txtUltKil.getText(),2) + "";
                    strSQL+=" ,fe_ultMod='"+ objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                    strSQL+=" ,co_usrMod="+ objParSis.getCodigoUsuario();                    
                    strSQL+=" WHERE co_veh=" + objUti.codificar(txtCodVeh.getText(),2) + ";";
                    strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1";
                    strSQL+=" ,fe_ultMod='"+ objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                    strSQL+=" ,co_usrMod="+ objParSis.getCodigoUsuario();
                    strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa()+ " and co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" and co_tipDoc=( SELECT co_tipDoc FROM tbr_tipDocPrg WHERE co_emp="+ objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + " AND co_mnu=3730";
                    strSQL+=" AND st_reg='S' );";
        //            System.out.println("insertarPro:.. " + strSQL);
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


//    /**
//     * Esta función actualiza el registro en la base de datos.
//     * @return true: Si se pudo actualizar el registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
     //       System.out.println("actualizarReg::..."); 
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarPro()){
                    con.commit();
                   blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
//    /**
//     * Esta función permite actualizar la cabecera de un registro.
//     * @return true: Si se pudo actualizar la cabecera del registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean actualizarPro(){
        boolean blnRes=true, exist=true;
//        double ren,cap,temp,kil1,kil2,valMan,valEfe,valDoc,valCom,tempExi;
        String aux;
        try{
  //          System.out.println("actualizarPro:::..");
            if (con!=null){

                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
               
                if(exist==true)
                {
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="UPDATE tbm_cabTicCom";
                    strSQL+=" SET"; 
                    if(txtCodDoc.getText().length()>0)
                        strSQL+=" co_tipDoc=" + objUti.codificar(txtCodDoc.getText()) + ",";
                    if(dtpFecDoc.getText().length()>0)
                        strSQL+=" fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                    if(txtNumDoc.getText().length()>0)
                        strSQL+=" ne_numDoc=" + objUti.codificar(txtNumDoc.getText(),2) + ",";
                        strSQL+=" tx_obs1=" + objUti.codificar(txaObs.getText()) + ",";
                    if(txtCodVeh.getText().length()>0)
                        strSQL+=" co_veh=" + objUti.codificar(txtCodVeh.getText(),2) + ",";
                    if(txtCodCom.getText().length()>0)
                       strSQL+=" co_com=" + objUti.codificar(txtCodCom.getText(),2) + ",";
                    if(txtValCom.getText().length()>0)
                        strSQL+=" nd_pre=" + objUti.codificar(txtValCom.getText(),2) + ",";
                    if(txtAntKil.getText().length()>0)
                        strSQL+=" ne_kilAnt=" + objUti.codificar(txtAntKil.getText(),2)  + ",";
                    if(txtUltKil.getText().length()>0)
                        strSQL+=" ne_kilAct=" + objUti.codificar(txtUltKil.getText(),2) + ",";
                        strSQL+=" nd_valCal=" + objUti.codificar(txtValCal.getText(),2) + ",";
                        strSQL+=" nd_valMan=" + objUti.codificar(txtValMan.getText(),2) + ",";
                        strSQL+=" nd_valEfe=" + objUti.codificar(txtValEfe.getText(),2) + ",";
                        strSQL+=" nd_valDoc=" + objUti.codificar(txtValDoc.getText(),2) + ",";
                        strSQL+=" nd_valDocGas=" + objUti.codificar(txtValDocGas.getText(),2) + ",";
                        strSQL+=" co_cho=" + objUti.codificar(txtCodCho.getText(),2) + ",";
                        strSQL+=" tx_obs2=" + objUti.codificar(txaObs1.getText()) + ",";                
                    if(txtValMan.getText().length()>0)
                        strSQL+=" st_aut='P',";
                    strSQL+=" fe_ultMod='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"',";
                    strSQL+=" co_usrMod=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" where co_doc=" + txtCodDoc2.getText() + " ";
                    strSQL+=" AND co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" ;";
                    strSQL+=" UPDATE tbm_veh SET ne_ultKil=" + objUti.codificar(txtUltKil.getText(),2) + "";
                    strSQL+=" ,fe_ultMod='"+ objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                    strSQL+=" ,co_usrMod="+ objParSis.getCodigoUsuario();
                    strSQL+=" WHERE co_veh=" + objUti.codificar(txtCodVeh.getText(),2) + ";";
        //            System.out.println("actualizarPro:." + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                }                
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
//    /**
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            String strAuxCon="";
            System.out.println("consultarReg::::....");
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                                
                if(dtpFecDoc.isFecha())
                    strAuxCon+=" and a1.fe_doc='" +  objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if(txtCodVeh.getText().length()>0)
                    strAuxCon+=" and a1.co_veh=" + txtCodVeh.getText();
                if(txtCodCho.getText().length()>0)
                    strAuxCon+=" and a1.co_cho=" + txtCodCho.getText();
                
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT distinct a1.co_doc,a1.co_emp,a1.co_loc,a1.co_tipdoc";
                    strSQL+=" FROM tbm_cabTicCom a1";
                    strSQL+=" WHERE a1.st_reg='A' ";
                    strAux=txtNumDoc.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a1.ne_NumDoc=" + strAux + "";
                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    if(objParSis.getCodigoMenu()==3740 && txtCodDoc2.getText().length()>0) // viene de Autorizacion 
                        strSQL+=" AND a1.co_doc=" + txtCodDoc2.getText();
                    strSQL+=" AND a1.co_TipDoc=( " ;
                    strSQL+=" SELECT co_tipDoc FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=3730 )";  // co_mnu=3730  // es TicketsDeCombustible
                    strSQL+=strAuxCon;
                    strSQL+=" ORDER BY a1.co_doc";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT DisTINCT a1.co_doc,a1.co_emp,a1.co_loc,a1.co_tipdoc";
                    strSQL+=" FROM tbm_cabTicCom a1";
                    strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp AND";
                    strSQL+=" a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" INNER JOIN tbr_tipDocUsr as a3 ON (a1.co_emp=a3.co_emp AND";
                    strSQL+=" a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND";
                    strSQL+=" a2.co_mnu=a3.co_mnu)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND a2.co_mnu=3730 AND a1.st_reg='A'";
                    if(objParSis.getCodigoMenu()==3740 && txtCodDoc2.getText().length()>0) // viene de AutorizacionDeTicketsDeCombustible 
                        strSQL+=" AND a1.co_doc=" + txtCodDoc2.getText();
                    strSQL+=strAuxCon;
               }
                System.out.println("consultarReg: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    //rstCab.first();
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

//    /**
//     * Esta función permite cargar el registro seleccionado.
//     * @return true: Si se pudo cargar el registro.
//     * <BR>false: En el caso contrario.
//     */
   
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            System.out.print("cargarReg:::..");
            if (cargarCabReg()){
               
            }
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
//    /**
//     * Esta función permite cargar la cabecera del registro seleccionado.
//     * @return true: Si se pudo cargar la cabecera del registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
           System.out.println("cargarCabReg:::.."); 
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT DISTINCT a1.co_doc,a3.co_tipDoc,a3.tx_desCor as tx_desCorDoc,a3.tx_desLar as tx_desLarTipDoc,a2.co_veh,a2.tx_desLar as tx_desLarVeh, \n";
                strSQL+="                 CASE WHEN a2.tx_tipVeh like 'O' THEN 'Otros' WHEN a2.tx_tipVeh like 'C' THEN 'Carro' \n";
                strSQL+="                 WHEN a2.tx_tipVeh like 'N' THEN 'Montacargas' WHEN a2.tx_tipVeh like 'M' THEN 'Moto' END AS tx_tipVeh, \n";
                strSQL+="                 a4.co_com,a4.tx_desLar as tx_desLarCom,a1.ne_kilAnt,a1.ne_kilAct,a2.nd_renVeh, \n";
                strSQL+="                 ROUND(a1.nd_valCal,2) as nd_valCal, \n";
                strSQL+="                 ROUND(a1.nd_valMan,2) as nd_valMan, \n";
                strSQL+="                 ROUND(a1.nd_valEfe,2) as nd_valEfe, \n";
                strSQL+="                 ROUND(a1.nd_valDoc,2) as nd_valDoc,ROUND(a1.nd_valDocGas,2) as nd_valDocGas, \n";
                strSQL+="                 a1.tx_obs1,a1.tx_obs2,a1.fe_doc,a1.ne_numDoc,a1.nd_pre,a4.tx_tipUniMed, \n";
                strSQL+="                 a5.co_tra, (a5.tx_ape || ' ' ||a5.tx_nom) as nombre  \n";
                strSQL+=" FROM tbm_cabTicCom as a1 \n";
                strSQL+=" INNER JOIN tbm_veh as a2 ON (a1.co_veh=a2.co_veh) \n";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_tipDoc=a3.co_tipDoc) \n";
                strSQL+=" LEFT OUTER JOIN tbm_comVeh as a4 ON (a1.co_com=a4.co_com) \n";
                strSQL+=" LEFT OUTER JOIN tbm_tra as a5 ON (a1.co_cho=a5.co_tra) \n";
                strSQL+=" WHERE a1.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
      //          System.out.println("cargarCabReg: " + strSQL);
                strWhere=strSQL;
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_veh");
                    txtCodVeh.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_desLarVeh");
                    txtNomVeh.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_tipVeh");
                    txtTipVeh.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_tipDoc");
                    txtCodDoc.setText((strAux==null)?"":strAux); 
                    strAux=rst.getString("tx_desCorDoc");
                    txtDesCorDoc.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_desLarTipDoc");
                    txtNomDoc.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_com");
                    txtCodCom.setText((strAux==null)?"":strAux);   
                    strAux=rst.getString("nd_pre");
                    txtValCom.setText((strAux==null)?"":strAux);   
                    strAux=rst.getString("tx_desLarCom");
                    txtTipCom.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("ne_kilAnt");
                    txtAntKil.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("ne_kilAct");
                    txtUltKil.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("nd_valCal");
                    txtValCal.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("nd_valMan");
                    txtValMan.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("nd_valEfe");
                    txtValEfe.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("nd_valDoc");
                    txtValDoc.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("nd_valDocGas");
                    txtValDocGas.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_obs1");
                    txaObs.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_obs2");
                    txaObs1.setText((strAux==null)?"":strAux);                    
                    datFecDoc=rst.getDate("fe_doc");
                    dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));                    
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);       
                    strAux=rst.getString("tx_tipUniMed");
                    txtUniCom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_tra");
                    txtCodCho.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("nombre");
                    txtNomCho.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("nd_renVeh");
                    txtRenVeh.setText((strAux==null)?"":strAux);
                    }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



//    /**
//     * Esta función obtiene la descripción larga del estado del registro.
//     * @param estado El estado del registro. Por ejemplo: A, I, etc.
//     * @return La descripción larga del estado del registro.
//     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
//     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    

//    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

//    /**
//     * Esta función determina si los campos son válidos.
//     * @return true: Si los campos son válidos.
//     * <BR>false: En el caso contrario.
//     */
    private boolean isCamVal(){
        //Validar el "Número de documento".
    //    System.out.println("isCamVal..");
        if (!isNumeroDocumentoValido(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), 1, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodDoc.getText()), (objTooBar.getEstado()=='m'?Integer.valueOf(txtCodDoc2.getText()):null), txtNumDoc))
        {
     //       System.out.println("Validacion del doc");
            tabFrm.setSelectedIndex(0);
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
            return false;
        }
        if (txtNomVeh.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Vehículo </FONT> es obligatorio.<BR>Escriba una descripción larga y vuelva a intentarlo.</HTML>");
            txtNomVeh.requestFocus();
            return false;
        }   
        if (txtNomDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Tipo de documento </FONT> es obligatorio.<BR>Escriba una descripción larga y vuelva a intentarlo.</HTML>");
            txtNomDoc.requestFocus();
            return false;
        }
        if (txtNomCho.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Chofer </FONT> es obligatorio.<BR>Escriba un Chofer y vuelva a intentarlo.</HTML>");
            txtNomCho.requestFocus();
            return false;
        }
        if (txtUltKil.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Ultimo Kilometraje </FONT> es obligatorio.<BR>Escriba un kilometraje vuelva a intentarlo.</HTML>");
            txtUltKil.requestFocus();
            return false;
        }
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(Integer.parseInt(txtAntKil.getText()) >= Integer.parseInt(txtUltKil.getText()))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Kilometraje actual</FONT> No puede ser menor o igual al <FONT COLOR=\"blue\">Kilometraje anterior</FONT><BR>Escriba un nuevo kilometraje y vuelva a intentarlo.</HTML>");
            txtUltKil.requestFocus();
            return false;
        }
        return true;
    }
    

//    /**
//     * Esta función anula el registro de la base de datos.
//     * @return true: Si se pudo anular el registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
    //        System.out.println("anularReg:::..");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (anularGrp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

//    /**
//     * Esta función permite anular la cabecera de un registro.
//     * @return true: Si se pudo anular la cabecera del registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean anularGrp(){
        boolean blnRes=true;
        try{
       //     System.out.println("anularGrp:::....");
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTicCom";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_doc=" + rstCab.getString("co_doc");
                strSQL+=" and co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and co_tipDoc=215";
       //         System.out.println("anularGrp" + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
//    /**
//     * Esta función elimina el registro de la base de datos.
//     * @return true: Si se pudo eliminar el registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
    //        System.out.println("eliminarReg:::...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (eliminarGrp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
//    /**
//     * Esta función permite eliminar la cabecera de un registro.
//     * @return true: Si se pudo eliminar la cabecera del registro.
//     * <BR>false: En el caso contrario.
//     */
    private boolean eliminarGrp(){
        boolean blnRes=true;
        try{
    //        System.out.println("eliminarGrp::...");
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTicCom";
                strSQL+=" SET st_reg='E'";
                strSQL+=" WHERE co_doc=" + rstCab.getString("co_doc");
                strSQL+=" and co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and co_tipDoc=215";
           //     System.out.println("eliminarGrp" + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
        
  //  mostrarVehiculos

private boolean mostrarVehiculos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
   //         System.out.println("mostrarVehiculos::::....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVeh.setCampoBusqueda(1);
                    vcoVeh.setVisible(true);
                   if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Vehiculo".
                    if (vcoVeh.buscar("a1.co_veh", txtCodVeh.getText())){
                         txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                    }
                    else
                    {
                        vcoVeh.setCampoBusqueda(0);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.setVisible(true);
                        if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVeh.setText(vcoVeh.getValueAt(1));
                            txtNomVeh.setText(vcoVeh.getValueAt(3));
                        }
                        else
                        {
                            txtCodVeh.setText(strVehCod);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoVeh.buscar("a1.tx_desLar", txtNomVeh.getText()))
                    {
                         txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                    }
                    else
                    {
                         vcoVeh.setCampoBusqueda(2);
                        vcoVeh.setCriterio1(11);
                        vcoVeh.cargarDatos();
                        vcoVeh.setVisible(true);
                        if (vcoVeh.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                        txtCodVeh.setText(vcoVeh.getValueAt(1));
                        txtNomVeh.setText(vcoVeh.getValueAt(3));
                        }
                        else
                        {
                            txtNomVeh.setText(strVehNom);
                        }
                    }
                    break;
            }
            cargarVehCom();
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /*
     * llenar la consulta de Vehiculos
     */
    
    private boolean configurarVehiculos()
    {
        boolean blnRes=true;
        try
        {
      //      System.out.println("configurarVehiculos");
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_veh");
            arlCam.add("a1.tx_pla");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Placa");
            arlAli.add("Descripción");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+=" SELECT a1.co_veh,a1.tx_pla,a1.tx_desLar \n";
                strSQL+=" FROM tbm_veh as a1  \n";
                strSQL+=" ORDER BY a1.co_veh \n";
            }
            else{
                strSQL="";
                strSQL+=" SELECT a1.co_veh,a1.tx_pla,a1.tx_desLar \n";
                strSQL+=" FROM tbm_veh as a1  \n";
                strSQL+=" LEFT OUTER JOIN tbm_comVeh as a2 ON (a1.co_com=a2.co_com) \n";
                strSQL+=" LEFT OUTER JOIN tbm_tra as a3 ON (a1.co_cho=a3.co_tra) \n";
                strSQL+=" INNER JOIN tbr_vehLocPrgUsr as a4 ON (a1.co_veh=a4.co_veh) \n";
                strSQL+=" WHERE a1.st_reg ='A' and a1.tx_tipVeh <> 'O' \n";
                strSQL+=" AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " \n";
                strSQL+=" AND a4.co_loc=" + objParSis.getCodigoLocal() + " \n";
                strSQL+=" and A4.co_mnu=" + objParSis.getCodigoMenu() + " \n";
                strSQL+=" and A4.co_usr=" + objParSis.getCodigoUsuario()+ " \n";
                strSQL+=" AND A4.st_reg IN ('A','P') \n";
                strSQL+=" ORDER BY a1.co_veh \n"; 
            }
            
            
            System.out.println("configurarVehiculos:.." + strSQL);
            vcoVeh=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vehiculos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVeh.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
  //  mostrarDocumentos

private boolean mostrarDocumentos(int intTipBus)
    {
       boolean blnRes=true;
        try
        {
    //        System.out.println("mostrarDocumentos::::....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDoc.setCampoBusqueda(0);
                    vcoDoc.setVisible(true);
                   if (vcoDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDoc.setText(vcoDoc.getValueAt(1));
                        txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                        txtNomDoc.setText(vcoDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por descripcion corta".
                     if (vcoDoc.buscar("a1.tx_desCor", txtDesCorDoc.getText())){
                        txtCodDoc.setText(vcoDoc.getValueAt(1));
                        txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                        txtNomDoc.setText(vcoDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoDoc.setCampoBusqueda(1);
                        vcoDoc.setCriterio1(11);
                        vcoDoc.cargarDatos();
                        vcoDoc.setVisible(true);
                        if (vcoDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDoc.setText(vcoDoc.getValueAt(1));
                            txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                            txtNomDoc.setText(vcoDoc.getValueAt(3));
                        }
                        else
                        {
                            txtCodDoc.setText(strDocCod);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoDoc.buscar("a1.tx_desLar", txtNomDoc.getText()))
                    {
                         txtCodDoc.setText(vcoDoc.getValueAt(1));
                         txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                         txtNomDoc.setText(vcoDoc.getValueAt(3));
                    }
                    else
                    {
                         vcoDoc.setCampoBusqueda(2);
                        vcoDoc.setCriterio1(11);
                        vcoDoc.cargarDatos();
                        vcoDoc.setVisible(true);
                        if (vcoDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDoc.setText(vcoDoc.getValueAt(1));
                            txtDesCorDoc.setText(vcoDoc.getValueAt(2));
                            txtNomDoc.setText(vcoDoc.getValueAt(3));
                        }
                        else
                        {
                            txtNomDoc.setText(strDocNom);
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


    /*
     * llenar la consulta de Vehiculos
     */
    
    private boolean configurarDocumentos()
    {
        boolean blnRes=true;
        try
        {
    //        System.out.println("configurarDocumentos");
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Corta");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){//Admin
                strSQL="";
                strSQL+=" SELECT DISTINCT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                strSQL+=" FROM tbm_cabTipDoc as a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.st_reg='A' and a2.co_mnu=3730 and a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" ORDER BY a1.co_tipDoc";
            }
            else{
                strSQL="";
                strSQL+=" SELECT DISTINCT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar ";
                strSQL+=" FROM tbm_cabTipDoc as a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp and ";
                strSQL+=" a1.co_loc=a2.co_loc and a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbr_tipDocUsr as a3 ON (a1.co_emp=a3.co_emp and";
                strSQL+=" a1.co_loc=a3.co_loc and a1.co_tipDoc=a3.co_tipDoc and a2.co_mnu=a3.co_mnu)";
                strSQL+=" WHERE a1.st_reg='A' and a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" and a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and a3.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.co_tipDoc";
            }
      //      System.out.println("configurarDocumentos:.." + strSQL);
            vcoDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vehiculos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /*
     * llenar la consulta de Vehiculos
     */
    
    private boolean configurarTickets()
    {
        boolean blnRes=true;
        try
        {
    //        System.out.println("configurarTickets");
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Corta");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT DISTINCT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
            strSQL+=" FROM tbm_cabTipDoc as a1";
            strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_tipDoc=a2.co_tipDoc)";
            strSQL+=" WHERE a1.st_reg='A' and a2.co_mnu=3730 and a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal();
            if(txtCodDoc.getText().length()>0)
                strSQL+=" and a1.co_tipDoc=" + txtCodDoc.getText();
            strSQL+=" ORDER BY a1.co_tipDoc";
   //         System.out.println("configurarDocumentos:.." + strSQL);
            vcoDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vehiculos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

      
//    /**
//     * Esta función muestra el tipo de documento predeterminado del programa.
//     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
//     * <BR>false: En el caso contrario.
//     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc \n";
                    strSQL+=" FROM tbm_cabTipDoc AS a1 \n";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " \n";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + " \n";
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" ( \n" ;
                    strSQL+="   SELECT co_tipDoc \n";
                    strSQL+="   FROM tbr_tipDocPrg \n";
                    strSQL+="   WHERE co_emp=" + objParSis.getCodigoEmpresa() + " \n";
                    strSQL+="   AND co_loc=" + objParSis.getCodigoLocal() + " \n";
                    strSQL+="   AND co_mnu=3730";
                    strSQL+="   AND st_reg='S' \n";
                    strSQL+=" )";
     //               System.out.println("DocumentoPredeterminado...." + strSQL);
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc \n";
                    strSQL+=" FROM tbm_cabTipDoc AS a1 \n";
                    strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
                    strSQL+="                                    a1.co_tipDoc=a2.co_tipDoc) \n";
                    strSQL+=" INNER JOIN tbr_tipDocUsr as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND \n";
                    strSQL+="                                    a1.co_tipDoc=a3.co_tipDoc AND a2.co_mnu=a3.co_mnu) \n";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " \n";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + " \n";
                    strSQL+=" AND a2.co_mnu=3730 \n";
                    strSQL+=" AND a2.st_reg='S' and a3.co_usr=" + objParSis.getCodigoUsuario()+ " \n";
                    strSQL+=" ";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorDoc.setText(rst.getString("tx_desCor"));
                    txtNomDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                }
                else
                {
                    mostrarMsgAdv("No tiene ningun documento autorizado...");
                    objTooBar.setEnabledAceptar(false);
                    return false;
                }
                 
                //VALORES NO EDITABLES ANTES DE QUE SE HAGA EL CALCULO
                txtValMan.setEnabled(false);
                txtValEfe.setEnabled(false);
                ///////////////////////////////////////
            
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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

  /*
     * llenar la consulta de choferes
     */
    
    private boolean configurarChoferes(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("nombre");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("250");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT distinct a2.co_tra, (a2.tx_ape || ' ' ||a2.tx_nom) as nombre \n";
            strSQL+=" FROM tbm_traEmp as a1 \n";
            strSQL+=" INNER JOIN tbm_tra as a2 ON (a1.co_tra = a2.co_tra) \n";
            //strSQL+=" WHERE a1.st_reg = 'A' and a2.st_reg='A' \n";  
            strSQL+=" WHERE a1.st_reg IN ('A','I') and a2.st_reg='A' \n";  //TEMPORAL HASTA INTEGRAR COSENCO EN LA BASE DE ZAFIRO2006
            strSQL+=" ORDER BY nombre \n";
   //         System.out.println("configurarChoferes:.." + strSQL);
            vcoCho=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Choferes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCho.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

 
///**
// * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
// * registro de la base de datos. El tipo de básqueda determina si se debe hacer
// * una básqueda directa (No se muestra la ventana de consulta a menos que no
// * exista lo que se está buscando) o presentar la ventana de consulta para que
// * el usuario seleccione la opcián que desea utilizar.
// * @param intTipBus El tipo de básqueda a realizar.
// * @return true: Si no se presentá ningán problema.
// * <BR>false: En el caso contrario.
// */
    private boolean mostrarChoferes(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
    //        System.out.println("mostrarChoferes:.....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCho.setCampoBusqueda(1);
                    vcoCho.show();
                    if (vcoCho.getSelectedButton()==vcoCho.INT_BUT_ACE){
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtNomCho.setText(vcoCho.getValueAt(2));
                    }
                    break;
               case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoCho.buscar("a1.co_tra", txtCodCho.getText())){
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtNomCho.setText(vcoCho.getValueAt(2));
                    }
                    else{
                        vcoCho.setCampoBusqueda(0);
                        vcoCho.setCriterio1(11);
                        vcoCho.cargarDatos();
                        vcoCho.show();
                        if (vcoCho.getSelectedButton()==vcoCho.INT_BUT_ACE){
                            txtCodCho.setText(vcoCho.getValueAt(1));
                            txtNomCho.setText(vcoCho.getValueAt(2));
                        }
                        else{
                            txtCodCho.setText(strChoCod);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCho.buscar("nombre", txtNomCho.getText())){
                        txtCodCho.setText(vcoCho.getValueAt(1));
                        txtNomCho.setText(vcoCho.getValueAt(2));
                    }
                    else{
                        vcoCho.setCampoBusqueda(2);
                        vcoCho.setCriterio1(11);
                        vcoCho.cargarDatos();
                        vcoCho.show();
                        if (vcoCho.getSelectedButton()==vcoCho.INT_BUT_ACE)
                        {
                            txtCodCho.setText(vcoCho.getValueAt(1));
                            txtNomCho.setText(vcoCho.getValueAt(2));
                        }
                        else{
                            txtNomCho.setText(strChoNom);
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
//////////////////////
///ZafUtil/isNumeroDocumentoValido
/////////////////////
    
    
public boolean isNumeroDocumentoValido(java.awt.Component padre, String stringConexion, String usuario, String clave, int tabla, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, Integer codigoDocumento, javax.swing.JTextField numeroDocumento)
{
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        boolean blnRes=true;
        try
        {
            //Validar que el "Número de documento" no esté vacío.
            if (numeroDocumento.getText().equals(""))
            {
                if (javax.swing.JOptionPane.showConfirmDialog(padre, "<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>¿Desea que el Sistema le asigne el número de documento?</HTML>", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                {
                    con=java.sql.DriverManager.getConnection(stringConexion, usuario, clave);
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        //Obtener el último número de documento ingresado en "tbm_cabTicCom".
                        strSQL="";
                        strSQL+="SELECT a1.ne_numDoc";
                        strSQL+=" FROM tbm_cabTicCom AS a1";
                        strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                        strSQL+=" AND a1.co_loc=" + codigoLocal;
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        rst=stm.executeQuery(strSQL);
                        if (rst.next())
                        {
                            numeroDocumento.setText("" + (rst.getInt("ne_numDoc")+1));
                        }
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                    }
                }
                blnRes=false;
            }
            else
            {
                con=java.sql.DriverManager.getConnection(stringConexion, usuario, clave);
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Validar que el "Código alterno" no se repita.
                    strSQL="";
                    switch (tabla)
                    {
                        case 1: //tbm_cabTicCom
                            strSQL="";
                            strSQL+="SELECT a1.ne_numDoc";
                            strSQL+=" FROM tbm_cabTicCom a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                            strSQL+=" AND a1.co_loc=" + codigoLocal;
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            strSQL+=" AND a1.ne_numdoc=" + numeroDocumento.getText();
                            if (codigoDocumento!=null)
                                strSQL+=" AND a1.co_doc<>" + codigoDocumento.intValue();
                            break;
                    }
                    rst=stm.executeQuery(strSQL);
                    if (rst.next())
                    {
                        blnRes=false;
                    }
                    rst.close();
                    rst=null;
                    if (blnRes==false)
                    {
                        if (javax.swing.JOptionPane.showConfirmDialog(padre, "<HTML>El número de documento <FONT COLOR=\"blue\">" + numeroDocumento.getText() + "</FONT> ya existe.<BR>¿Desea que el Sistema le asigne el número de documento?</HTML>", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            //Obtener el último número de documento ingresado en "tbm_cabTipDoc".
                            strSQL="";
                            strSQL+="SELECT MAX(a1.ne_numDoc) as ne_numDoc";
                            strSQL+=" FROM tbm_cabTicCom AS a1";
                            strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                            strSQL+=" AND a1.co_loc=" + codigoLocal;
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            rst=stm.executeQuery(strSQL);
                            if (rst.next())
                            {
                                txtNumDoc.setText("" + (rst.getInt("ne_numDoc")+1));
                            }
                            rst.close();
                            rst=null;
                        }
                    }
                    stm.close();
                    con.close();
                    stm=null;
                    con=null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }


///**
//* Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
//* Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
//* es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
//* llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
//* ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
//* momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
//* ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
//*/
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }
        
///**
// * Esta función establece el indice de la función a ejecutar. En la clase Thread
// * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
// * la función que debe ejecutar el Thread.
// * @param indice El indice de la función a ejecutar.
// */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }

///**
//* Esta función permite generar el reporte de acuerdo al criterio seleccionado.
//* @param intTipRpt El tipo de reporte a generar.
//* <BR>Puede tomar uno de los siguientes valores:
//* <UL>
//* <LI>0: Impresión directa.
//* <LI>1: Impresión directa (Cuadro de dialogo de impresión).
//* <LI>2: Vista preliminar.
//* </UL>
//* @return true: Si se pudo generar el reporte.
//* <BR>false: En el caso contrario.
//*/
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
  //          System.out.println("generarRpt");
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
//                            case 19:
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("co_emp", objParSis.getCodigoEmpresa());
                                mapPar.put("co_loc", objParSis.getCodigoLocal());
                                mapPar.put("co_tipDoc", Integer.valueOf(txtCodDoc.getText()));
                                mapPar.put("co_doc", Integer.valueOf(txtCodDoc2.getText()));
                                mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);
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
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
public boolean consultarEstadoImpresion()
{
    String aux="";
    boolean blnRes=false;
    try
    {
        con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        if (con!=null){
            stm=con.createStatement();
            strSQL="";
            strSQL+=" SELECT a1.st_imp";
            strSQL+=" FROM tbm_cabTicCom as a1";
            strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp and";
            strSQL+="                                      a1.co_loc=a2.co_loc and a1.co_tipDoc=a2.co_tipDoc)";
            strSQL+=" INNER JOIN tbr_tipDocUsr as a3 ON (a1.co_emp=a3.co_emp and";
            strSQL+=" a1.co_loc=a3.co_loc and a1.co_tipDoc=a3.co_tipDoc and";
            strSQL+=" a2.co_mnu=a3.co_mnu)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a2.co_mnu=3730 AND a3.co_usr=" + objParSis.getCodigoUsuario();
            strSQL+=" and a1.co_doc=" + txtCodDoc2.getText();
            strSQL+=" ";
   //         System.out.println("consultarEstadoImpresion" + strSQL);
            rst=stm.executeQuery(strSQL);
            if (rst.next()){
                aux=rst.getString("st_imp");
            }
            if(aux.equals("N"))
                blnRes=true;
            else
                mostrarMsgAdv("Documento ya ha sido impreso...");
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            
        }
        return blnRes;
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


public boolean cambiarEstadoImpresion()
{
    boolean blnRes=false;
    try
    {
        con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        if (con!=null){
            stm=con.createStatement();
            strSQL="";
            strSQL+=" UPDATE tbm_cabTicCom";
            strSQL+=" SET st_imp='S'";
            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND co_tipDoc=215";
            strSQL+=" and co_doc=" + txtCodDoc2.getText();
            strSQL+="; ";
   //         System.out.println("cambiarEstadoImpresion" + strSQL);
            stm.executeUpdate(strSQL);
            stm.close();
            con.close();
            stm=null;
            con=null;
        }
        return blnRes;
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



public boolean consultarAutorizacion()
{
    boolean blnRes=false;
    try
    {
        if(txtValMan.getText().length()>0)
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" select st_aut";
                strSQL+=" from tbm_cabTicCom";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=215";
                strSQL+=" AND co_doc=" + txtCodDoc2.getText();
                strSQL+=" and st_aut='A'";
      //          System.out.println("consultarAutorización: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    return true;
                }
                else{
                    mostrarMsgAdv("Documento necesita autorización...");
                    return false;
                }
                
                
            }
        }
        else
            return true;
        return blnRes;
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
//
    public boolean cargarVehCom()
    {
    boolean blnRes=false;
    try
    {
        if(txtCodVeh.getText().length()>0)
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_veh,a1.tx_pla,a1.tx_desLar, a2.co_com,a2.tx_desLar as tx_desCom,";
                strSQL+=" a2.tx_tipUniMed,ROUND(a2.nd_pre,4) as nd_pre,";
                strSQL+=" CASE WHEN a1.ne_ultKil IS NULL THEN '0' else a1.ne_ultKil end as ne_ultKil,";
                strSQL+=" CASE WHEN a1.tx_tipVeh like 'O' THEN 'Otros'";
                strSQL+=" WHEN a1.tx_tipVeh like 'C' THEN 'Carro'";
                strSQL+=" WHEN a1.tx_tipVeh like 'N' THEN 'Montacargas'";
                strSQL+=" WHEN a1.tx_tipVeh like 'M' THEN 'Moto' END AS tx_tipVeh ,";
                strSQL+=" a3.co_tra, (a3.tx_ape || ' ' ||a3.tx_nom) as nombre,";
                strSQL+=" ROUND(a1.nd_renVeh,4) as nd_renVeh";
                strSQL+=" FROM tbm_veh as a1";
                strSQL+=" LEFT OUTER JOIN tbm_comVeh as a2 ON (a1.co_com=a2.co_com)";
                strSQL+=" LEFT OUTER JOIN tbm_tra as a3 ON (a1.co_cho=a3.co_tra)";
                strSQL+=" WHERE a1.st_reg ='A' and a1.tx_tipVeh <> 'O'";
                strSQL+=" and a1.co_veh=" + txtCodVeh.getText();
     //           System.out.println("CargarVehCom: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_com");
                    txtCodCom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCom");
                    txtTipCom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tipVeh");
                    txtTipVeh.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("nd_pre");
                    txtValCom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tipUniMed");
                    txtUniCom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_ultKil");
                    txtAntKil.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("nd_renVeh");
                    txtRenVeh.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_tra");
                    txtCodCho.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("nombre");
                    txtNomCho.setText((strAux==null)?"":strAux);
                   blnRes=true;
                }
            }
            else
                blnRes=false;
            }
        con.close();
        rst.close();
        stm.close();
        stm=null;
        con=null;
        rst=null;
        return blnRes;
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
}