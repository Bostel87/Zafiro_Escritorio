/*
 * ZafCxC28.java
 *
 * Created on 11 de junio de 2007
 * REFERENCIAS COMERCIALES
 * CREADO POR DARIO CARDENAS LANDIN 11/06/2007
 * PASADO A PRODUCCION EL 12/SEP/2007
 */
package CxC.ZafCxC28;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;

/**
 *
 * @author  leo
 */
public class ZafCxC28 extends javax.swing.JInternalFrame 
{

    boolean blnCambios = false; 
    private Librerias.ZafDate.ZafDatePicker dtpFecDoc;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDatAcc, vecCabAcc;
    private Vector vecDatPnd, vecCabPnd;
    private boolean blnHayCam;          //Determina si hay cambios en el formulario.
    private ZafColNumerada objColNumExc, objColNumPnd;
    private ZafPopupMenu objPopMnu;
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecuciï¿½n del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodCli, strDesLarCli,strIdeCli, strDirCli, strDesCorCta, strDesLarCta, strSolCre;    

    private java.util.Vector vecRegAcc, vecRegPnd;
    private mitoolbar objTooBar;
    private String sSQLCon;
    private java.sql.Connection conCab, conDet, con, conCnsDet,conAnu;

    private java.sql.Statement stmCab, stmDet, stm, stmCnsDet, stmAnu;
    private java.sql.ResultSet rstCab, rstDet, rst, rstCnsDet;
    private tblHilo objHilo;
    private ZafTblMod objTblModAcc;
    
    
    //creacion de check
    private ZafTblCelRenChk objTblCelRenChkExc;
    private ZafTblCelRenChk objTblCelRenChkPnd;
    private ZafTblCelEdiChk objTblCelEdiChkExc;
    private ZafTblCelEdiChk objTblCelEdiChkPnd;
    
    //creacion de label
    private ZafTblCelRenLbl objTblCelRenLblExc;
    private ZafTblCelRenLbl objTblCelRenLblPnd;
    
    //creacion de txt
    private ZafTblCelEdiTxt objTblCelEdiTxtExc;
    private ZafTblCelEdiTxt objTblCelEdiTxtPnd;
    
    private int j=1, CODEMP=0;    
    
    private ZafTblEdi objTblEdi;
    private ZafVenCon vcoSolCre;                           //Ventana de consulta.
    
    private double dblMonDoc=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;

    
    private int intSig=1;                              //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicaciï¿½n de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    
    /** Crea una nueva instancia de la clase ZafCon01. */
    public ZafCxC28(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            
            ////fecha///
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            panCabDoc.add(dtpFecDoc);
            dtpFecDoc.setBounds(418, 55, 100, 20);
            
            
            objTooBar=new mitoolbar(this);
            panBar.add(objTooBar);//llama a la barra de botones
           
            if (!configurarFrm())
                exitForm();

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
    }
    
    
    public ZafCxC28(ZafParSis obj, Integer codigoEmpresa, Integer codigoSolicitud, Integer codigoCliente, String nombreCliente, Integer codigoRegistro, Integer consulta, Integer modifica)
    {
        this(obj);
        int c=0, m=0, intCodEmp=0;
        int intCodEmpGrp=0, intCodEmpNew=0;
        
        c = Integer.parseInt(consulta.toString());
        m = Integer.parseInt(modifica.toString());
        intCodEmp = Integer.parseInt(codigoEmpresa.toString());
        CODEMP = intCodEmp;
        
        //////////////////////////////////////////////////////
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            System.out.println("---ZAFCXC23--intCodEmpGrp--: " + intCodEmpGrp);
            intCodEmpNew = objParSis.getCodigoEmpresa();
            System.out.println("---ZAFCXC23--intCodEmpNew--: " + intCodEmpNew);

            objTooBar.setVisibleAceptar(true);
            objTooBar.setVisibleCancelar(true);
            objTooBar.setVisibleModificar(true);
            objTooBar.setVisibleConsultar(true);

            if(intCodEmpGrp == intCodEmpNew)
                objTooBar.setVisibleInsertar(false);
            else
                objTooBar.setVisibleInsertar(true);
        /////////////////////////////////////////////////////
        
        if(codigoCliente==null || codigoRegistro==null)
        {
            ///System.out.println("SI ENTRO...");
            
            if(m>=1)
            {
                ///System.out.println("SI...");
                objTooBar.setEstado('n');                
                txtCodDoc.setEditable(false);
                txtSolCre.setText(codigoSolicitud.toString());
                txtCodCli.setText(codigoCliente.toString());
                txtNomCli.setText(nombreCliente.toString());
                txtNomRefCom.requestFocus();
            }
        }
        else
        {
            txtSolCre.setText(codigoSolicitud.toString());
            txtCodCli.setText(codigoCliente.toString());
            txtCodDoc.setText(codigoRegistro.toString());
                    
            if(c>=1)
            {
                objTooBar.setEstado('c');
                objTooBar.consultar();
                objTooBar.setEstado('w');
            }

            if(m>=1)
            {
                objTooBar.setEstado('x');
                objTooBar.consultar();
                objTooBar.setEstado('m');
            }
            
            else
            {
                objTooBar.setEstado('x');
                objTooBar.consultar();
                objTooBar.setEstado('m');
            }
        }
        lblTit.setText("Refencias Comerciales...");
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        panCabDoc = new javax.swing.JPanel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNomRefCom = new javax.swing.JLabel();
        txtNomRefCom = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtNomCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        butSolCre = new javax.swing.JButton();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtCarInf = new javax.swing.JTextField();
        lblCarInf = new javax.swing.JLabel();
        lblMatPro = new javax.swing.JLabel();
        txtTelRef = new javax.swing.JTextField();
        lblPlaCre = new javax.swing.JLabel();
        txtPlaRef = new javax.swing.JTextField();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        lblCupRef = new javax.swing.JLabel();
        txtCupRef = new javax.swing.JTextField();
        lblPro = new javax.swing.JLabel();
        txtInf = new javax.swing.JTextField();
        txtForPag = new javax.swing.JTextField();
        lblForPag = new javax.swing.JLabel();
        txtSolCre = new javax.swing.JTextField();
        lblSolCre = new javax.swing.JLabel();
        jrbSi = new javax.swing.JRadioButton();
        jrbNo = new javax.swing.JRadioButton();
        lblCal = new javax.swing.JLabel();
        lblInf = new javax.swing.JLabel();
        jCbxCal = new javax.swing.JComboBox();
        lblTieRef = new javax.swing.JLabel();
        txtTieRef = new javax.swing.JTextField();
        lblNomRefCom1 = new javax.swing.JLabel();
        txtMatPro = new javax.swing.JTextField();
        lblTelRef = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
        setPreferredSize(new java.awt.Dimension(116, 210));
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Informaci\u00f3n del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panCabDoc.setLayout(null);

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 420));
        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Codigo:");
        lblCodDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblCodDoc);
        lblCodDoc.setBounds(10, 57, 105, 15);

        txtCodDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        panCabDoc.add(txtCodDoc);
        txtCodDoc.setBounds(118, 55, 66, 20);

        lblNomRefCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomRefCom.setText("Ref. Comercial:");
        lblNomRefCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblNomRefCom);
        lblNomRefCom.setBounds(10, 82, 105, 15);

        txtNomRefCom.setPreferredSize(new java.awt.Dimension(6, 20));
        panCabDoc.add(txtNomRefCom);
        txtNomRefCom.setBounds(118, 80, 400, 20);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        lblCli.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCli);
        lblCli.setBounds(10, 32, 70, 15);

        txtNomCli.setMaximumSize(null);
        txtNomCli.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNomCli);
        txtNomCli.setBounds(185, 30, 333, 20);

        txtCodCli.setMaximumSize(null);
        txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtCodCli);
        txtCodCli.setBounds(118, 30, 66, 20);

        butSolCre.setFont(new java.awt.Font("SansSerif", 1, 12));
        butSolCre.setText("...");
        butSolCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolCreActionPerformed(evt);
            }
        });

        panCabDoc.add(butSolCre);
        butSolCre.setBounds(185, 5, 24, 20);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        txtCodTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipDocActionPerformed(evt);
            }
        });

        panCabDoc.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(106, 4, 0, 0);

        txtCarInf.setMaximumSize(null);
        txtCarInf.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtCarInf);
        txtCarInf.setBounds(118, 305, 355, 20);

        lblCarInf.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCarInf.setText("Cargo Informante:");
        lblCarInf.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCarInf);
        lblCarInf.setBounds(10, 307, 105, 15);

        lblMatPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMatPro.setText("Mat. que Provee:");
        lblMatPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblMatPro);
        lblMatPro.setBounds(10, 107, 105, 15);

        txtTelRef.setMaximumSize(null);
        txtTelRef.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtTelRef);
        txtTelRef.setBounds(368, 105, 150, 20);

        lblPlaCre.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPlaCre.setText("Plazo Referencia:");
        lblPlaCre.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblPlaCre);
        lblPlaCre.setBounds(10, 182, 105, 15);

        txtPlaRef.setMaximumSize(null);
        txtPlaRef.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtPlaRef);
        txtPlaRef.setBounds(118, 180, 150, 20);

        lblObs1.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observaci\u00f3n:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 8));
        panCabDoc.add(lblObs1);
        lblObs1.setBounds(10, 330, 92, 23);

        spnObs1.setViewportView(txaObs1);

        panCabDoc.add(spnObs1);
        spnObs1.setBounds(118, 330, 355, 40);

        lblCupRef.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCupRef.setText("Cupo Referencia:");
        lblCupRef.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCupRef);
        lblCupRef.setBounds(10, 157, 105, 15);

        txtCupRef.setMaximumSize(null);
        txtCupRef.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtCupRef);
        txtCupRef.setBounds(118, 155, 150, 20);

        lblPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPro.setText("Protestos:");
        lblPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblPro);
        lblPro.setBounds(10, 232, 105, 15);

        txtInf.setMaximumSize(null);
        txtInf.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtInf);
        txtInf.setBounds(118, 280, 355, 20);

        txtForPag.setMaximumSize(null);
        txtForPag.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtForPag);
        txtForPag.setBounds(118, 205, 150, 20);

        lblForPag.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblForPag.setText("Forma de Pago:");
        lblForPag.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblForPag);
        lblForPag.setBounds(10, 207, 105, 15);

        txtSolCre.setMaximumSize(null);
        txtSolCre.setPreferredSize(new java.awt.Dimension(70, 20));
        txtSolCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSolCreActionPerformed(evt);
            }
        });
        txtSolCre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolCreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolCreFocusLost(evt);
            }
        });

        panCabDoc.add(txtSolCre);
        txtSolCre.setBounds(118, 5, 66, 20);

        lblSolCre.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSolCre.setText("Solicitud Credito:");
        lblSolCre.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblSolCre);
        lblSolCre.setBounds(4, 6, 110, 15);

        jrbSi.setText("Si");
        jrbSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbSiActionPerformed(evt);
            }
        });

        panCabDoc.add(jrbSi);
        jrbSi.setBounds(118, 230, 45, 23);

        jrbNo.setSelected(true);
        jrbNo.setText("No");
        jrbNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbNoActionPerformed(evt);
            }
        });

        panCabDoc.add(jrbNo);
        jrbNo.setBounds(160, 230, 45, 23);

        lblCal.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCal.setText("Calificacion:");
        lblCal.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCal);
        lblCal.setBounds(10, 257, 105, 15);

        lblInf.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblInf.setText("Informante:");
        lblInf.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblInf);
        lblInf.setBounds(10, 282, 105, 15);

        jCbxCal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(Ninguna)", "Excelente", "Muy Bueno", "Bueno", "Normal", "Regular", "Malo", "Pesimo" }));
        panCabDoc.add(jCbxCal);
        jCbxCal.setBounds(118, 255, 140, 20);

        lblTieRef.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTieRef.setText("Tiempo Referencia:");
        lblTieRef.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblTieRef);
        lblTieRef.setBounds(10, 133, 105, 15);

        txtTieRef.setMaximumSize(null);
        txtTieRef.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtTieRef);
        txtTieRef.setBounds(118, 130, 150, 20);

        lblNomRefCom1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomRefCom1.setText("Fecha Referencia:");
        lblNomRefCom1.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblNomRefCom1);
        lblNomRefCom1.setBounds(300, 57, 105, 15);

        txtMatPro.setMaximumSize(null);
        txtMatPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtMatPro);
        txtMatPro.setBounds(118, 105, 150, 20);

        lblTelRef.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelRef.setText("Tlf. Referencia:");
        lblTelRef.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblTelRef);
        lblTelRef.setBounds(280, 107, 80, 15);

        spnDat.setViewportView(panCabDoc);

        panDat.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void jrbNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbNoActionPerformed
        // TODO add your handling code here:
        jrbSi.setSelected(false);
        jrbNo.setSelected(true);
    }//GEN-LAST:event_jrbNoActionPerformed

    private void jrbSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbSiActionPerformed
        // TODO add your handling code here:
        jrbSi.setSelected(true);
        jrbNo.setSelected(false);
    }//GEN-LAST:event_jrbSiActionPerformed

    private void txtSolCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolCreFocusLost
    //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtSolCre.getText().equalsIgnoreCase(strSolCre))
        {
            if (txtSolCre.getText().equals(""))
            {
                txtSolCre.setText("");
                objTblModAcc.removeAllRows();
            }
            else
            {
                mostrarVenConSolCre(1);                
                if (txtSolCre.getText().equals(""))
                    txtSolCre.requestFocus();
                else
                    txtNomRefCom.requestFocus();
                
                cargarDatCli();
            }
        }
        else
            txtSolCre.setText(strSolCre);
    }//GEN-LAST:event_txtSolCreFocusLost

    private void txtSolCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolCreFocusGained
        // TODO add your handling code here:
        strSolCre=txtSolCre.getText();
        txtSolCre.selectAll();
    }//GEN-LAST:event_txtSolCreFocusGained

    private void txtSolCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSolCreActionPerformed
        // TODO add your handling code here:
        txtSolCre.transferFocus();
    }//GEN-LAST:event_txtSolCreActionPerformed

    private void txtCodTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTipDocActionPerformed

    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConSolCre()
    {
        boolean blnRes=true;
        try
        {   
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_sol");
            arlCam.add("a1.fe_sol");
            arlCam.add("a1.co_cli");
            arlCam.add("a2.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod. Sol.");
            arlAli.add("Fec. Sol.");
            arlAli.add("Cod. Cli.");
            arlAli.add("Nom. Cli.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("60");
            arlAncCol.add("300");

            /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                strSQL="";
                strSQL+="SELECT a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom";
                strSQL+=" FROM tbm_solcre as a1";
                strSQL+=" INNER JOIN tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli)";
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
                if(intSig==1)
                {
                    ///strTipCliAux="Clientes";
                    strSQL+=" AND a2.st_cli='S' and a1.st_reg='A'";
                }else{
                    ///strTipCliAux="Proveedores";
                    strSQL+=" AND a2.st_prv='S' and a1.st_reg='A'";
                }
                
                strSQL+=" ORDER BY a2.tx_nom";
                
                System.out.println("---QUERY PARA CONCLI---: " + strSQL);
            }
            else
            {                
                strSQL="";
                strSQL+="SELECT a1.co_sol, a1.fe_sol, a1.co_cli, a2.tx_nom";
                strSQL+=" FROM tbm_solcre as a1";
                strSQL+=" INNER JOIN tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli)";
                strSQL+=" INNER JOIN tbr_cliloc as a3 on (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli)";
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + CODEMP;
                else
                {
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal();
                }
                
                if(intSig==1)
                {
                    ///strTipCliAux="Clientes";
                    strSQL+=" AND a2.st_cli='S' and a1.st_reg='A'";
                }else
                {
                    ///strTipCliAux="Proveedores";
                    strSQL+=" AND a2.st_prv='S' and a1.st_reg='A'";
                }
                
                strSQL+=" ORDER BY a2.tx_nom";
                
                System.out.println("---QUERY PARA CONCLI POR LOCAL---: " + strSQL);
            }
            
/*Ultimo query para consultar Clientes*/            
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            if (objParSis.getCodigoMenu()==319)
//                strSQL+=" AND a1.st_cli='S'";
//            else
//                strSQL+=" AND a1.st_prv='S'";
//            strSQL+=" ORDER BY a1.tx_nom";
            
            vcoSolCre=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoSolCre.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
            ///vcoSolCre.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConSolCre(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoSolCre.setCampoBusqueda(2);
                    vcoSolCre.show();
                    if (vcoSolCre.getSelectedButton()==vcoSolCre.INT_BUT_ACE)
                    {
                        txtSolCre.setText(vcoSolCre.getValueAt(1));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Codigo".
                    if (vcoSolCre.buscar("a1.co_sol", txtSolCre.getText()))
                    {
                        txtSolCre.setText(vcoSolCre.getValueAt(1));
                    }
                    else
                    {
                        vcoSolCre.setCampoBusqueda(0);
                        vcoSolCre.setCriterio1(11);
                        vcoSolCre.cargarDatos();
                        vcoSolCre.show();
                        if (vcoSolCre.getSelectedButton()==vcoSolCre.INT_BUT_ACE)
                        {
                            txtSolCre.setText(vcoSolCre.getValueAt(1));
                        }
                        else
                        {
                            txtSolCre.setText(strSolCre);
                        }
                    }
                    break;
                default:
                    txtSolCre.requestFocus();
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
    
    
    
    

    private void butSolCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolCreActionPerformed
        ///mostrarVenConCli(0);
        mostrarVenConSolCre(0);
        if (txtSolCre.getText().equals(""))
            txtSolCre.requestFocus();
        else
            txtNomRefCom.requestFocus();
    }//GEN-LAST:event_butSolCreActionPerformed
    
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
       String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexiï¿½n si estï¿½ abierta.
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
        catch (java.sql.SQLException e)
        {
            dispose();
        }                        
    }//GEN-LAST:event_exitForm
    
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butSolCre;
    private javax.swing.JComboBox jCbxCal;
    private javax.swing.JRadioButton jrbNo;
    private javax.swing.JRadioButton jrbSi;
    private javax.swing.JLabel lblCal;
    private javax.swing.JLabel lblCarInf;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCupRef;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblInf;
    private javax.swing.JLabel lblMatPro;
    private javax.swing.JLabel lblNomRefCom;
    private javax.swing.JLabel lblNomRefCom1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblPlaCre;
    private javax.swing.JLabel lblPro;
    private javax.swing.JLabel lblSolCre;
    private javax.swing.JLabel lblTelRef;
    private javax.swing.JLabel lblTieRef;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCarInf;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCupRef;
    private javax.swing.JTextField txtForPag;
    private javax.swing.JTextField txtInf;
    private javax.swing.JTextField txtMatPro;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomRefCom;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtPlaRef;
    private javax.swing.JTextField txtSolCre;
    private javax.swing.JTextField txtTelRef;
    private javax.swing.JTextField txtTieRef;
    // End of variables declaration//GEN-END:variables

    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifï¿½quelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }   
    
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            if (consultarCabReg())
            {
               ///System.out.println("SE CONSULTO CAB DE UN REGISTRO CONSULTADO");
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    

    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarCabReg()
    {
        int intCodEmp, intCodLoc, intultdoc;
        boolean blnRes=true;
        String codmodprg="";
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intultdoc = ultCodDoc();
            
            codmodprg = rtnMod();
            ///System.out.println("---el modulo activo de este programa es: " + codmodprg );
            
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nomref, a1.tx_matproref, a1.tx_tel, a1.tx_tie, a1.tx_cupcre, ";
                strSQL+=" a1.tx_placre, a1.tx_forpag, a1.st_pro, a1.tx_cal, a1.tx_inf, a1.tx_carinf, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbm_refcomsolcre as a1 ";
                strSQL+=" INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                ///strSQL+=" WHERE a2.co_emp = " + intCodEmp + "";
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND a2.co_sol = " + txtSolCre.getText() + "";
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_ref = '" + strAux + "'";
                
                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a2. co_sol";
                System.out.println("---ConsultarCabReg: " +strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {                    
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningï¿½n registro que cumpla el criterio de bï¿½squeda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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

    /**
     * Esta funciï¿½n permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                ///System.out.println("SE CARGO CABECERA Y DETALLE DE UN REGISTRO CONSULTADO");
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a3.co_cli, a3.tx_nom as tx_nomcli, a1.co_ref, a1.fe_ref, a1.tx_nom as tx_nomref, a1.tx_matproref, a1.tx_tel, a1.tx_tie, a1.tx_cupcre, ";
                strSQL+=" a1.tx_placre, a1.tx_forpag, a1.st_pro, a1.tx_cal, a1.tx_inf, a1.tx_carinf, a1.tx_obs1, a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbm_refcomsolcre as a1 ";
                strSQL+=" INNER JOIN tbm_solcre as a2 ON (a1.co_emp=a2.co_emp and a1.co_sol=a2.co_sol) ";
                strSQL+=" INNER JOIN tbm_cli    as a3 ON (a2.co_emp=a3.co_emp and a2.co_cli=a3.co_cli) ";
                strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                strSQL+=" AND a2.co_sol = " + rstCab.getString("co_sol") + "";
                strSQL+=" AND a1.co_ref = " + rstCab.getString("co_ref") + "";
                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a1.co_ref ";
                
                System.out.println("---CargarCabReg: " +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {                   
                    txtSolCre.setText(((rst.getString("co_sol")==null)?"":rst.getString("co_sol")));
                    txtCodCli.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("tx_nomcli")==null)?"":rst.getString("tx_nomcli")));
                    txtCodDoc.setText(((rst.getString("co_ref")==null)?"":rst.getString("co_ref")));
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_ref"),"dd/MM/yyyy"));
                    txtNomRefCom.setText(((rst.getString("tx_nomref")==null)?"":rst.getString("tx_nomref")));
                    txtMatPro.setText(((rst.getString("tx_matproref")==null)?"":rst.getString("tx_matproref")));
                    txtTelRef.setText(((rst.getString("tx_tel")==null)?"":rst.getString("tx_tel")));
                    txtTieRef.setText(((rst.getString("tx_tie")==null)?"":rst.getString("tx_tie")));
                    txtCupRef.setText(((rst.getString("tx_cupcre")==null)?"":rst.getString("tx_cupcre")));
                    txtPlaRef.setText(((rst.getString("tx_placre")==null)?"":rst.getString("tx_placre")));
                    txtForPag.setText(((rst.getString("tx_forpag")==null)?"":rst.getString("tx_forpag")));
                    
                    /////Para saber el estado del protesto////
                    String strPro= rst.getString("st_pro");
                    if(strPro.equals("S"))
                    {
                        jrbSi.setSelected(true);
                        jrbNo.setSelected(false);
                    }
                    if(strPro.equals("N"))
                    {
                        jrbNo.setSelected(true);
                        jrbSi.setSelected(false);
                    }
                    else
                    {
                        jrbNo.setSelected(false);
                        jrbSi.setSelected(false);
                    }
                    
                    ////Para saber la clasificacion de la persona///
                    String val = rst.getString("tx_cal")==""?"0":rst.getString("tx_cal");
                    int intCal= Integer.parseInt(val);
                    switch(intCal)
                    ///switch(val)
                    {
                        case 0:
                            jCbxCal.setSelectedIndex(0);
                            break;
                        case 1:
                            jCbxCal.setSelectedIndex(1);
                            break;
                        case 2:
                            jCbxCal.setSelectedIndex(2);
                            break;
                        case 3:
                            jCbxCal.setSelectedIndex(3);
                            break;
                        case 4:
                            jCbxCal.setSelectedIndex(4);
                            break;
                        case 5:
                            jCbxCal.setSelectedIndex(5);
                            break;
                        case 6:
                            jCbxCal.setSelectedIndex(6);
                            break;
                        case 7:
                            jCbxCal.setSelectedIndex(7);
                            break;
                    }
                    
                    txtInf.setText(((rst.getString("tx_inf")==null)?"":rst.getString("tx_inf")));
                    txtCarInf.setText(((rst.getString("tx_carinf")==null)?"":rst.getString("tx_carinf")));
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    
                    //Mostrar el estado del registro.
                    strAux=rstCab.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";                    
                    else if (strAux.equals("I") )
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posiciï¿½n relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);            
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
    
    /**
     * Esta funciï¿½n permite cargar los Datos del Cliente Seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatCli()
    {
        int intPosRel, intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
/*                
select a1.co_emp, a1.co_sol, a2.co_cli, a2.tx_nom as tx_nomcli  
from tbm_solcre as a1
inner join tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli)
WHERE a1.co_emp = 1 AND a1.co_sol = 432 AND a1.st_reg <> 'E' 
ORDER BY a1.co_sol                
*/                
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_sol, a2.co_cli, a2.tx_nom as tx_nomcli ";
                strSQL+=" FROM tbm_solcre as a1 ";
                strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_sol = " + txtSolCre.getText() + "";
                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a1.co_sol ";
                
                System.out.println("---cargarDatCli: " +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {                   
                    ///txtSolCre.setText(((rst.getString("co_sol")==null)?"":rst.getString("co_sol")));
                    txtCodCli.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("tx_nomcli")==null)?"":rst.getString("tx_nomcli")));
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;                      
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
        
    /**
     * Esta funciï¿½n permite insertar un registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (insertarCabReg())
                {
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
    

    private boolean insertarCabReg()
    {
        boolean blnRes=true;
        int ultcoddoc;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                ///obtener el ultimo registro ingresado///
                ultcoddoc = ultCodDoc();
                ultcoddoc++;                
                txtCodDoc.setText(String.valueOf(ultcoddoc));
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_refcomsolcre";
                strSQL+=" (co_emp, co_sol, co_ref, fe_ref, tx_nom, tx_matproref, tx_tel, tx_tie, tx_cupcre, tx_placre, tx_forpag, ";
                strSQL+=" st_pro, tx_cal, tx_inf, tx_carinf, tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod ) ";
                strSQL+=" VALUES (";
                strSQL+=" "  + objParSis.getCodigoEmpresa();            ////co_emp
                strSQL+=", " + txtSolCre.getText();                     ///co_sol
                strSQL+=", " + ultcoddoc;                               ///co_ref
                strSQL+=", '" + strAux + "'";                           ///fe_ref
                strSQL+=", '" + txtNomRefCom.getText() + "'";           ///tx_nom
                strSQL+=", '" + txtMatPro.getText() + "'";              ///tx_matproref
                strSQL+=", '" + txtTelRef.getText() + "'";              ///tx_tel
                strSQL+=", '" + txtTieRef.getText() + "'";              ///tx_tie
                strSQL+=", '" + txtCupRef.getText() + "'";              ///tx_cupcre
                strSQL+=", '" + txtPlaRef.getText() + "'";              ///tx_placre
                strSQL+=", '" + txtForPag.getText() + "'";              ///tx_forpag
                
                //////Para protestos///////
                if(jrbSi.isSelected())
                    strSQL+=", 'S'" ;                                   ///st_pro
                else
                {
                    if(jrbNo.isSelected())
                        strSQL+=", 'N'" ;                               ///st_pro
                    else
                        strSQL+=", 'N'" ;                               ///st_pro
                }
                
                /////Para clasificacion////
                switch(jCbxCal.getSelectedIndex())                      ///tx_cal
                {
                    case 0:
                        strSQL+=", '0'" ;
                        break;
                    case 1:
                        strSQL+=", '1'" ;
                        break;
                    case 2:
                        strSQL+=", '2'" ;
                        break;
                    case 3:
                        strSQL+=", '3'" ;
                        break;
                    case 4:
                        strSQL+=", '4'" ;
                        break;
                    case 5:
                        strSQL+=", '5'" ;
                        break;
                    case 6:
                        strSQL+=", '6'" ;
                        break;
                    case 7:
                        strSQL+=", '7'" ;
                        break;
                    default:
                        strSQL+=", '0'" ;
                        break;
                }
                
                strSQL+=", '" + txtInf.getText()  + "'";                ///tx_inf
                strSQL+=", '" + txtCarInf.getText()  + "'";             ///tx_carinf
                strSQL+=", '" + txaObs1.getText()    + "'";             ///tx_obs1
                strSQL+=", 'A'";                                        ///st_reg
                strSQL+=", '" + strFecSis + "'";                        ///fe_ing
                strSQL+=", null ";                                      ///fe_ultmod
                strSQL+=", " + objParSis.getCodigoUsuario();            ///co_usring
                strSQL+=", null ";                                      ///co_urmod
                strSQL+=")";
                System.out.println("---SQL del Insert tbm_refcomsolcre: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println("---Error1-insertarcabReg()--- ");
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println("---Error2-insertarcabReg()--- ");
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funciï¿½n permite eliminar un registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (eliminarCabReg())
                {
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
    
    private boolean eliminarCabReg()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_refcomsolcre";
                strSQL+=" SET ";
                strSQL+=" st_reg = 'E'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_sol=" + rstCab.getString("co_sol");
                strSQL+=" AND co_ref=" + txtCodDoc.getText();
                System.out.println("---SQL del EliminarReg tbm_RefComSolCre: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
    
    /**
     * Esta funciï¿½n permite anular un registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (anularCabReg())
                {
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
    
    
    private boolean anularCabReg()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_refcomsolcre";
                strSQL+=" SET ";
                strSQL+=" st_reg = 'I'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_sol=" + rstCab.getString("co_sol");
                strSQL+=" AND co_ref=" + txtCodDoc.getText();
                System.out.println("---SQL del AnularReg tbm_RefComSolCre: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
        
    
    /**
     * Esta funciï¿½n actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarCabReg())
                {
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
    
    
    private boolean actualizarCabReg()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_refcomsolcre";
                strSQL+=" SET ";
                strSQL+=" tx_nom = '" + txtNomRefCom.getText() + "'";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_ref = '" + strAux + "'";
                strSQL+=", tx_matproref = '" + txtMatPro.getText() + "'";
                strSQL+=", tx_tel = '" + txtTelRef.getText() + "'";
                strSQL+=", tx_tie = '" + txtTieRef.getText() + "'";
                strSQL+=", tx_cupCre = '" + txtCupRef.getText() + "'";
                strSQL+=", tx_plaCre = '" + txtPlaRef.getText() + "'";
                strSQL+=", tx_forpag = '" + txtForPag.getText() + "'";

                /// estados de cheques protestados ///
                if(jrbSi.isSelected())
                    strSQL+=", st_pro = 'S'" ;
                else
                {
                    if(jrbNo.isSelected())
                        strSQL+=", st_pro = 'N'" ;
                    else
                        strSQL+=", st_pro = 'N'" ;
                        
                }
                
                /// tipo de clasificacion para el cliente ///
                switch(jCbxCal.getSelectedIndex())
                {
                    case 0:
                        strSQL+=", tx_cal = '0'" ;
                        break;
                    case 1:
                        strSQL+=", tx_cal = '1'" ;
                        break;
                    case 2:
                        strSQL+=", tx_cal = '2'" ;
                        break;
                    case 3:
                        strSQL+=", tx_cal = '3'" ;
                        break;
                    case 4:
                        strSQL+=", tx_cal = '4'" ;
                        break;
                    case 5:
                        strSQL+=", tx_cal = '5'" ;
                        break;
                    case 6:
                        strSQL+=", tx_cal = '6'" ;
                        break;
                    case 7:
                        strSQL+=", tx_cal = '7'" ;
                        break;
                    default:
                        strSQL+=", tx_cal = '0'" ;
                        break;
                }
                
                strSQL+=", tx_inf = '" + txtInf.getText() + "'";
                strSQL+=", tx_carinf = '" + txtCarInf.getText() + "'";
                strSQL+=", tx_obs1 = '" + txaObs1.getText() + "'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_sol=" + rstCab.getString("co_sol");
                strSQL+=" AND co_ref=" + txtCodDoc.getText();
                System.out.println("---SQL del update tbm_refcomsolcre: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
        

    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes=true;
        try 
        {
            
            //titulo de la ventana
            this.setTitle(objParSis.getNombreMenu()+ " V 0.1.7");
            lblTit.setText(""+objParSis.getNombreMenu());
            
            //Configuracion de la Ventana de Consulta de Solicitud de Credito//
            configurarVenConSolCre();
            
            //asigno el color de fondo de los campos 
            txtSolCre.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtNomRefCom.setBackground(objParSis.getColorCamposObligatorios());
            
        }
        catch(Exception e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
        //////////////////////////
    }
     
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_EDI);
        }
        else
        {
            objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_NO_EDI);            
        }
    }   
   
    
 //PARA LA BARRA DE HERRAMIENTAS
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            dtpFecDoc.setText("");
            txtSolCre.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");            
            txtCodDoc.setText("");
            txtNomRefCom.setText("");            
            txtMatPro.setText("");
            txtTelRef.setText("");
            txtTieRef.setText("");
            txtCupRef.setText("");
            txtPlaRef.setText("");
            txtForPag.setText("");
            txtInf.setText("");
            txtCarInf.setText("");
            txaObs1.setText("");
            jrbSi.setSelected(false);
            jrbNo.setSelected(false);
            jCbxCal.setSelectedIndex(-1);
            objTooBar.setEstadoRegistro("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
  
     private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    private void consultarCamUsr()
    {
        strAux="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
        
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'm': //Modificar
                        System.out.println("Modo Modificar");
                        break;
                    case 'e': //Eliminar
                        System.out.println("Modo Eliminar");
                        break;
                    case 'n': //insertar
                        System.out.println("Modo Ninguno");
//                        objTooBar.insertar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                break;
            case 2: //CANCEL_OPTION
                break;
        }
    //nuevos metodos del toolbar
                    
    }   

    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
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


    /**
     *Esta funcion valida si los campos obligatorios han sido completados correctamente
     *@return True si los campos obligatorios se llenaron correctamente.
     */
    private boolean isCamVal()
    {
        if(txtSolCre.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Solicitud de Credito</FONT> es obligatorio.<BR>Escriba un cï¿½digo de solicitud de credito y vuelva a intentarlo.</HTML>");
            txtSolCre.requestFocus();
            return false;
        }

        if(txtNomRefCom.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Contacto </FONT> es obligatorio.<BR>Escriba un Nombre de Contacto y vuelva a intentarlo.</HTML>");
            txtNomRefCom.requestFocus();
            return false;
        }

        return true;
    }
    
    protected int ultCodDoc()
    {
        int intUltDoc=0;
        boolean blnRes=true;
        java.sql.Connection conNumDoc;
        java.sql.Statement stmNumDoc;
        java.sql.ResultSet rstNumDoc;
        try{            
            conNumDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNumDoc!=null)
            {            
                stmNumDoc=conNumDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlDoc="";
                sqlDoc+="select max(b1.co_ref) as ultdoc ";
                sqlDoc+=" from tbm_refcomsolcre as b1 ";
                sqlDoc+=" inner join tbm_solcre as b2 ON ";
                sqlDoc+=" (b1.co_emp=b2.co_emp AND b1.co_sol=b2.co_sol) ";
                ///sqlDoc+=" where b1.co_emp=" + objParSis.getCodigoEmpresa() + ""; 
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    sqlDoc+=" WHERE b1.co_emp=" + CODEMP;
                else
                    sqlDoc+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                
                sqlDoc+=" and b2.co_sol=" + txtSolCre.getText() + "";
                
                System.out.println(" ---query ultcoddoc: " + sqlDoc);
                intUltDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), sqlDoc);
                ///intUltDoc++;
                rstNumDoc=stmNumDoc.executeQuery(sqlDoc);      
                if(rstNumDoc.next())
                    System.out.println("el ultimo codigo del documento es:"+ intUltDoc);
            }
            conNumDoc.close();
            conNumDoc=null;
            stmNumDoc=null;
            rstNumDoc=null;
            
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
        return intUltDoc;
    }
    
    //////funcion para un futuro///////
    protected String rtnMod()
    {
        String modprg="";
        int intUltDoc=0;
        boolean blnRes=true;
        java.sql.Connection conModDoc;
        java.sql.Statement stmModDoc;
        java.sql.ResultSet rstModDoc;
        try{            
            conModDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conModDoc!=null)
            {            
                stmModDoc=conModDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlDoc="";
                sqlDoc+="select max(a2.ne_mod) as modulo ";
                sqlDoc+=" from tbr_tipdocprg as a1 ";
                sqlDoc+=" inner join tbm_cabtipdoc as a2 ON ";
                sqlDoc+=" (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc) ";
                ///sqlDoc+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    sqlDoc+=" WHERE a1.co_emp=" + CODEMP;
                else
                    sqlDoc+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
                sqlDoc+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                
                System.out.println(" ---query modulo del programa: " + sqlDoc);
                rstModDoc=stmModDoc.executeQuery(sqlDoc);      
                if(rstModDoc.next())
                    ///txtCodDoc.setText(String.valueOf(intUltDoc));
                    modprg = rstModDoc.getString("modulo");
                    System.out.println("el modulo del menu es:"+ modprg);
            }
            conModDoc.close();
            conModDoc=null;
            stmModDoc=null;
            rstModDoc=null;
            
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
        return modprg;
    }

    public class mitoolbar extends ZafToolBar
    {
            public mitoolbar(javax.swing.JInternalFrame jfrThis){
                super(jfrThis, objParSis);
            }

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
                txtSolCre.requestFocus();
                txtCodCli.setEditable(false);
                txtNomCli.setEditable(false);
                txtCodDoc.setEditable(false);
                jCbxCal.setSelectedIndex(0);
                ///jrbNo.setSelected(true);
                
                ////establecer fecha para la referencia///
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                blnHayCam=false;
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

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
        
            return true;
        }
        
        public void clickEliminar(){
            consultarCabReg();
        }
        
        public boolean eliminar()
        {
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    mostrarMsgInf("El documento ya estï¿½ ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }
        
        public void clickAnular(){
            consultarCabReg();
        }
        
        public boolean anular()
        {
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Anulado"))
                {
                    mostrarMsgInf("El documento ya estï¿½ ANULADO.\n No es posible ANULAR un documento anulado.");
                    return false;
                }
                if (!anularReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Anulado");
                    ///limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }


        public void clickConsultar()
        {
            txtSolCre.requestFocus();
            txtCodCli.setEditable(false);
            txtNomCli.setEditable(false);
        }
            
            
       public boolean consultar() 
       {
           consultarReg();
           return true;
       }
            
                                    
       public void clickModificar()
       {
            txtSolCre.setEditable(false);
            txtCodCli.setEditable(false);
            txtNomCli.setEditable(false);
            txtCodDoc.setEditable(false);
            txtNomRefCom.requestFocus();
            butSolCre.setEnabled(false);
            consultarCabReg();
       }
       
       
       public boolean modificar()
       {                           
            if (!actualizarReg())
                return false;
            return true;

       }           

     public void clickInicio() {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            limpiarFrm();
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        limpiarFrm();
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
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            limpiarFrm();
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        limpiarFrm();
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
        try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            limpiarFrm();
                            rstCab.next();
                            cargarReg();
                        }
                            
                    }
                    else
                    {
                        limpiarFrm();
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
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            
                            limpiarFrm();
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        limpiarFrm();
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
            
     public void clickImprimir(){
     }
     
     public void clickVisPreliminar(){
     }
     
     public void clickAceptar(){
          limpiarFrm();
     }

     public void clickCancelar()
     {
          limpiarFrm();
     }

     private int Mensaje()
     {
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
                strMsg+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";

                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
                
     }
     private void MensajeValidaCampo(String strNomCampo){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
     }
            

     private boolean validaCampos()
     {
                
         if(txtCodCli.getText().equals("") )
                {
                   MensajeValidaCampo("Cliente");
                   txtCodCli.requestFocus();
                   return false;
                }
                
                if(txtNomRefCom.getText().equals("") )
                {
                   MensajeValidaCampo("Nombre Contacto");
                   txtCodCli.requestFocus();
                   return false;
                }
                                                           
         return true; 
     }
     
        public boolean vistaPreliminar() {
            return true;
        }        
        
        public boolean afterVistaPreliminar() {
            return true;
        }        
        
        public boolean imprimir() {
            return true;
        }        
        
        public boolean afterAceptar() {
            return true;
        }        
        
        public boolean afterInsertar() {
            blnHayCam=false;
            ///objTooBar.setEstado('n');
            this.setEstado('w');
            blnHayCam=false;
            ///limpiarFrm();
            consultarCabReg();
            return true;
        }
        
        public boolean afterModificar() {
            blnHayCam=false;
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean aceptar() {                        
            return true;
        }
        
        public boolean cancelar() 
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
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
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
        
        public boolean afterConsultar() {
            return true;
        }
     
    
      public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeConsultar()
        {
//            if(txtCodCli.getText().equals(""))
//            {
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cï¿½digo de Cliente</FONT> es obligatorio.<BR>Escriba un cï¿½digo de cliente y vuelva a intentarlo.</HTML>");
//                txtCodCli.requestFocus();
//                return false;
//            }
             return true;
        }

        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento estï¿½ ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento estï¿½ ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;

            return true;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya estï¿½ ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento estï¿½ ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya estï¿½ ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }     
   }      

    class tblHilo extends Thread{
            public void run(){
                ///System.out.println("antes de llamar a monDoc");
                ///System.out.println("despues de llamar a monDoc");
            }
     }

    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
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
}