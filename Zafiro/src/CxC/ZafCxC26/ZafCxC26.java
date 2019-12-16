/*
 * ZafCxC26.java
 *
 * Created on 05 de junio de 2007
 * DATOS DEL PROPIETARIO DE LA EMPRESA
 * CREADO POR DARIO CARDENAS LANDIN 05/06/2007
 * PASADO A PRODUCCION EL 12/SEP/2007
 */

package CxC.ZafCxC26;
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
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;

/**
 *
 * @author  leo
 */
public class ZafCxC26 extends javax.swing.JInternalFrame 
{
    
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_NOM_ACC=1;
    final int INT_TBL_DAT_COD_CLI=2;

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
    private ZafMouMotAda objMouMotAda;          //ToolTipText en TableHeader.
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecuciï¿½n del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodCli, strDesLarCli,strIdeCli, strDirCli, strDesCorCta, strDesLarCta;    

    private ZafVenCon vcoCli;                           //Ventana de consulta.
    
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
    
    private int j=1;    
    
    private ZafTblEdi objTblEdi;
    
    
    private double dblMonDoc=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;

    
    private int intSig=1;                              //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicaciï¿½n de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    
    /** Crea una nueva instancia de la clase ZafCon01. */
    public ZafCxC26(ZafParSis obj)
    {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            
            dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            panCabDoc.add(dtpFecDoc);
            dtpFecDoc.setBounds(220, 150, 100, 20);            
            
            objTooBar=new mitoolbar(this);
            panBar.add(objTooBar);//llama a la barra de botones
                        
            objTooBar.setVisibleInsertar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            
            
            if (!configurarFrm())
                exitForm();

        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
    }
    
    public ZafCxC26(ZafParSis obj, Integer codigoCliente, String nombreCliente, Integer consulta, Integer modifica)
    {        
        this(obj);
        
        int c=0, m=0;
        c = Integer.parseInt(consulta.toString());
        m = Integer.parseInt(modifica.toString());
        
        if(codigoCliente==null )
        {
            ///
            System.out.println("SI ENTRO...");
            ///limpiarFrm();
            if(m>=1)
            {
                System.out.println("SI...");
                objTooBar.setEstado('n');                
                txtCodCli.setEditable(false);
                txtCodCli.setText(codigoCliente.toString());
                txtNomCli.setText(nombreCliente.toString());
                txtCedPasPro.requestFocus();
            }
            ///objTooBar.setEstado('w');
        }
        else
        {
            System.out.println("NO ENTRO...");
            txtCodCli.setText(codigoCliente.toString());
            //txtCodDoc.setText(codigoRegistro.toString());

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
        
        lblTit.setText("Propietario y Accionistas de la Empresa...");
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
        spnDat = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panCabDoc = new javax.swing.JPanel();
        lblCedPasPro = new javax.swing.JLabel();
        txtCedPasPro = new javax.swing.JTextField();
        lblNomPro = new javax.swing.JLabel();
        txtNomPro = new javax.swing.JTextField();
        lblFecConEmp = new javax.swing.JLabel();
        lblCli = new javax.swing.JLabel();
        txtNomCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        butPro = new javax.swing.JButton();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDomPro = new javax.swing.JTextField();
        lblDomPro = new javax.swing.JLabel();
        lblTelPro = new javax.swing.JLabel();
        txtTelPro = new javax.swing.JTextField();
        lblNacPro = new javax.swing.JLabel();
        txtNacPro = new javax.swing.JTextField();
        lblTipActEmp = new javax.swing.JLabel();
        txtTipActEmp = new javax.swing.JTextField();
        lblObs = new javax.swing.JLabel();
        spnObs = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        panCenDat = new javax.swing.JPanel();
        panAcc = new javax.swing.JPanel();
        spnAcc = new javax.swing.JScrollPane();
        tblAcc = new javax.swing.JTable();
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

        panFil.setLayout(new java.awt.BorderLayout());

        panCabDoc.setLayout(null);

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 250));
        lblCedPasPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCedPasPro.setText("Ced/Pasaporte:");
        lblCedPasPro.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblCedPasPro);
        lblCedPasPro.setBounds(10, 30, 105, 15);

        txtCedPasPro.setPreferredSize(new java.awt.Dimension(100, 20));
        panCabDoc.add(txtCedPasPro);
        txtCedPasPro.setBounds(118, 30, 100, 20);

        lblNomPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomPro.setText("Nombre Propietario:");
        lblNomPro.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblNomPro);
        lblNomPro.setBounds(10, 54, 105, 15);

        txtNomPro.setPreferredSize(new java.awt.Dimension(6, 20));
        panCabDoc.add(txtNomPro);
        txtNomPro.setBounds(118, 54, 265, 20);

        lblFecConEmp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecConEmp.setText("Fecha de Constitucion de la Empresa");
        lblFecConEmp.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblFecConEmp);
        lblFecConEmp.setBounds(10, 150, 210, 15);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        lblCli.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCli);
        lblCli.setBounds(4, 6, 70, 15);

        txtNomCli.setMaximumSize(null);
        txtNomCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });

        panCabDoc.add(txtNomCli);
        txtNomCli.setBounds(184, 6, 170, 20);

        txtCodCli.setMaximumSize(null);
        txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });

        panCabDoc.add(txtCodCli);
        txtCodCli.setBounds(118, 6, 66, 20);

        butPro.setFont(new java.awt.Font("SansSerif", 1, 12));
        butPro.setText("...");
        butPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProActionPerformed(evt);
            }
        });

        panCabDoc.add(butPro);
        butPro.setBounds(356, 6, 24, 20);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        panCabDoc.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(106, 4, 0, 0);

        txtDomPro.setMaximumSize(null);
        txtDomPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtDomPro);
        txtDomPro.setBounds(118, 78, 265, 20);

        lblDomPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDomPro.setText("Domicilio Propietario");
        lblDomPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblDomPro);
        lblDomPro.setBounds(10, 78, 105, 15);

        lblTelPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelPro.setText("Telefonos Propietario");
        lblTelPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblTelPro);
        lblTelPro.setBounds(10, 102, 105, 15);

        txtTelPro.setMaximumSize(null);
        txtTelPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtTelPro);
        txtTelPro.setBounds(118, 102, 265, 20);

        lblNacPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNacPro.setText("Nacionalidad Propietario");
        lblNacPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblNacPro);
        lblNacPro.setBounds(10, 126, 105, 15);

        txtNacPro.setMaximumSize(null);
        txtNacPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNacPro);
        txtNacPro.setBounds(118, 126, 100, 20);

        lblTipActEmp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipActEmp.setText("Tipo de Actividad de la Empresa");
        lblTipActEmp.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblTipActEmp);
        lblTipActEmp.setBounds(10, 174, 210, 15);

        txtTipActEmp.setMaximumSize(null);
        txtTipActEmp.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtTipActEmp);
        txtTipActEmp.setBounds(220, 174, 298, 20);

        lblObs.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs.setText("Observaci\u00f3n:");
        lblObs.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs.setPreferredSize(new java.awt.Dimension(92, 8));
        panCabDoc.add(lblObs);
        lblObs.setBounds(10, 198, 92, 23);

        spnObs.setViewportView(txaObs1);

        panCabDoc.add(spnObs);
        spnObs.setBounds(118, 198, 400, 45);

        panFil.add(panCabDoc, java.awt.BorderLayout.NORTH);

        panCenDat.setLayout(new java.awt.BorderLayout());

        panCenDat.setPreferredSize(new java.awt.Dimension(20, 110));
        panAcc.setLayout(new java.awt.BorderLayout());

        panAcc.setPreferredSize(new java.awt.Dimension(20, 120));
        panAcc.setAutoscrolls(true);
        spnAcc.setBorder(new javax.swing.border.TitledBorder("Accionistas"));
        spnAcc.setPreferredSize(new java.awt.Dimension(20, 100));
        spnAcc.setAutoscrolls(true);
        tblAcc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnAcc.setViewportView(tblAcc);

        panAcc.add(spnAcc, java.awt.BorderLayout.CENTER);

        panCenDat.add(panAcc, java.awt.BorderLayout.NORTH);

        panFil.add(panCenDat, java.awt.BorderLayout.CENTER);

        spnDat.setViewportView(panFil);

        tabFrm.addTab("General", spnDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
        // TODO add your handling code here:
        mostrarVenConCli(0);
        if (txtCodCli.getText().equals(""))
        {
            objTblModAcc.removeAllRows();
        }
    }//GEN-LAST:event_butProActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblModAcc.removeAllRows();
            }
            else
            {
                mostrarVenConCli(2);
                if (txtCodCli.getText().equals(""))
                {
                    objTblModAcc.removeAllRows();
                }
            }
        }
        else
            txtNomCli.setText(strDesLarCli); 
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblModAcc.removeAllRows();
            }
            else
            {
                mostrarVenConCli(1);
                if (txtCodCli.getText().equals(""))
                {
                    objTblModAcc.removeAllRows();
                }
            }
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Identificacion");
            arlAli.add("Nombre");
            arlAli.add("Direccion");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");

            /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S'  AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
                System.out.println("---QUERY PARA CONCLI---: " + strSQL);
            }
            else
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM  tbr_cliloc AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.st_cli='S' AND a2.st_reg='A'";
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
            
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strDesLarCli);
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
    
//    private boolean mostrarVenConCli(int intTipBus)
//    {
//        String strAli, strCam, strTipCliAux;
//        Librerias.ZafConsulta.ZafConsulta objVenCon;
//        boolean blnRes=true;
//        try
//        {
//            strAli="Cï¿½digo, Identificaciï¿½n, Nombre, Direcciï¿½n";
//            strCam="a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            if(intSig==1){
//                strTipCliAux="Clientes";
//                strSQL+=" AND a1.st_cli='S' and st_reg='A'";
//            }else{
//                strTipCliAux="Proveedores";
//                strSQL+=" AND a1.st_prv='S' and st_reg='A'";
//            }
//            
//            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            objVenCon.setTitle("Listado de "+ strTipCliAux);
//            switch (intTipBus)
//            {
//                case 0: //Mostrar la ventana de consulta.
//                    objVenCon.show();
//                    if (objVenCon.acepto())
//                    {
//                        txtCodCli.setText(objVenCon.GetCamSel(1));
//                        strIdeCli=objVenCon.GetCamSel(2);
//                        txtNomCli.setText(objVenCon.GetCamSel(3));
//                        strDirCli=objVenCon.GetCamSel(4);
//                    }
//                    break;
//                case 1: //Bï¿½squeda directa por "Cï¿½digo".
//                    if (objVenCon.buscar("LOWER(a1.co_cli) LIKE '" + txtCodCli.getText() + "'"))
//                    {
//                        txtCodCli.setText(objVenCon.GetCamSel(1));
//                        strIdeCli=objVenCon.GetCamSel(2);
//                        txtNomCli.setText(objVenCon.GetCamSel(3));
//                        strDirCli=objVenCon.GetCamSel(4);
//                    }
//                    else
//                    {
//                        objVenCon.setFiltroConsulta(txtCodCli.getText());
//                        objVenCon.setSelectedTipBus(2);
//                        objVenCon.setSelectedCamBus(0);
//                        objVenCon.show();
//                        if (objVenCon.acepto())
//                        {
//                            txtCodCli.setText(objVenCon.GetCamSel(1));
//                            strIdeCli=objVenCon.GetCamSel(2);
//                            txtNomCli.setText(objVenCon.GetCamSel(3));
//                            strDirCli=objVenCon.GetCamSel(4);
//                        }
//                        else
//                        {
//                            txtCodCli.setText(strCodCli);
//                        }
//                    }
//                    break;
//                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
//                    if (objVenCon.buscar("LOWER(a1.tx_nom) LIKE '" + txtNomCli.getText().toLowerCase() + "'"))
//                    {
//                        txtCodCli.setText(objVenCon.GetCamSel(1));
//                        strIdeCli=objVenCon.GetCamSel(2);
//                        txtNomCli.setText(objVenCon.GetCamSel(3));
//                        strDirCli=objVenCon.GetCamSel(4);
//                    }
//                    else
//                    {
//                        objVenCon.setFiltroConsulta(txtNomCli.getText());
//                        objVenCon.setSelectedTipBus(2);
//                        objVenCon.setSelectedCamBus(2);
//                        objVenCon.show();
//                        if (objVenCon.acepto())
//                        {
//                            txtCodCli.setText(objVenCon.GetCamSel(1));
//                            strIdeCli=objVenCon.GetCamSel(2);
//                            txtNomCli.setText(objVenCon.GetCamSel(3));
//                            strDirCli=objVenCon.GetCamSel(4);
//                        }
//                        else
//                        {
//                            txtNomCli.setText(strDesLarCli);
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
    private javax.swing.JButton butPro;
    private javax.swing.JLabel lblCedPasPro;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblDomPro;
    private javax.swing.JLabel lblFecConEmp;
    private javax.swing.JLabel lblNacPro;
    private javax.swing.JLabel lblNomPro;
    private javax.swing.JLabel lblObs;
    private javax.swing.JLabel lblTelPro;
    private javax.swing.JLabel lblTipActEmp;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAcc;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panCenDat;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JScrollPane spnAcc;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblAcc;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCedPasPro;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDomPro;
    private javax.swing.JTextField txtNacPro;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomPro;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtTelPro;
    private javax.swing.JTextField txtTipActEmp;
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
               System.out.println("SE CONSULTO CAB - DET DE UN REGISTRO CONSULTADO");
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
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom, tx_idePro, tx_nomPro, tx_dirPro, tx_telPro, tx_nacPro, fe_conEmp, tx_tipActEmp, tx_obsPro, st_reg";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                ///strSQL+=" AND a1.co_cli = " + txtCodCli.getText() + "";
                
                strAux=txtCodCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli = '" + strAux + "'";
                
                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a1. co_cli";
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
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarDetReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            conDet=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conDet!=null)
            {
                stmDet=conDet.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom ";
                strSQL+=" FROM tbm_accEmpCli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                strAux=txtCodCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli = '" + strAux + "'";
                
                strSQL+=" AND a2.st_reg <> 'E'";
                System.out.println("---ConsultarDetReg: " +strSQL);
                rstDet=stmDet.executeQuery(strSQL);
                if (rstDet.next())
                {
                    cargarDetReg();
                    strSQLCon=strSQL;
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
                if (cargarDetReg())
                {
                    System.out.println("SE CARGO CABECERA Y DETALLE DE UN REGISTRO CONSULTADO");
                }
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
                strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom, tx_idePro, tx_nomPro, tx_dirPro, tx_telPro, tx_nacPro, fe_conEmp, tx_tipActEmp, tx_obsPro, st_reg";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp = " + rstCab.getString("co_emp") + "";
                strSQL+=" AND a1.co_cli = " + rstCab.getString("co_cli") + "";
                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a1.co_cli ";
                System.out.println("---CargarCabReg: " +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {                   
                    txtCodCli.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                    txtCedPasPro.setText(((rst.getString("tx_idePro")==null)?"":rst.getString("tx_idePro")));
                    txtNomPro.setText(((rst.getString("tx_nomPro")==null)?"":rst.getString("tx_nomPro")));
                    txtDomPro.setText(((rst.getString("tx_dirPro")==null)?"":rst.getString("tx_dirPro")));
                    txtTelPro.setText(((rst.getString("tx_telPro")==null)?"":rst.getString("tx_telPro")));
                    txtNacPro.setText(((rst.getString("tx_nacPro")==null)?"":rst.getString("tx_nacPro")));
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_conEmp"),"dd/MM/yyyy"));
                    txtTipActEmp.setText(((rst.getString("tx_tipActEmp")==null)?"":rst.getString("tx_tipActEmp")));
                    txaObs1.setText(((rst.getString("tx_obsPro")==null)?"":rst.getString("tx_obsPro")));

                    //Mostrar el estado del registro.
                    strAux=rstCab.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";                    
                    else if (strAux.equals("I") )  /////&& strAux.equals("E"))   //////Eliminacion Logica////////
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
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_cli, a1.tx_nom ";
                strSQL+=" FROM tbm_accEmpCli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_cli = " + rstCab.getString("co_cli") + "";
                strSQL+=" AND a2.st_reg <> 'E'";
                System.out.println("---CargarDetReg:"+strSQL);
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    vecCabAcc=new Vector();
                    vecCabAcc.add(INT_TBL_DAT_LIN, "");
                    vecCabAcc.add(INT_TBL_DAT_NOM_ACC, rst.getString("tx_nom"));
                    vecCabAcc.add(INT_TBL_DAT_COD_CLI, rst.getString("co_cli"));
                    vecDatAcc.add(vecCabAcc);
                }
            }
            ///limpiar y cerrar el resulset///
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Asignar vectores al modelo.
            objTblModAcc.setData(vecDatAcc);
            tblAcc.setModel(objTblModAcc);
            ///vecDatAcc.clear();
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
            this.setTitle(objParSis.getNombreMenu()+ " V 0.1.5");
            lblTit.setText(""+objParSis.getNombreMenu());

            //asigno el color de fondo de los campos 
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            
            configurarVenConCli();
            
            /////////////////////////////////////////////////////////////////////////////////
            //Configurar JTable: Establecer el modelo para Tabla de Credito            
            vecDatAcc=new Vector();      //Almacena los datos
            vecCabAcc=new Vector(5);     //16);    //Almacena la cabeceras
            vecCabAcc.clear();
            vecCabAcc.add(INT_TBL_DAT_LIN,"");
            vecCabAcc.add(INT_TBL_DAT_NOM_ACC,"Nom. Acc.");
            vecCabAcc.add(INT_TBL_DAT_COD_CLI,"Cod. Cli.");

            objTblModAcc=new ZafTblMod();
            objTblModAcc.setHeader(vecCabAcc);

            tblAcc.setModel(objTblModAcc);
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblAcc.setRowSelectionAllowed(true);
            tblAcc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumPnd=new ZafColNumerada(tblAcc,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblAcc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_NOM_ACC).setPreferredWidth(500);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblAcc.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setMaxWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setMinWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(0);
            tblAcc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setResizable(false);
            
            ///////////////////////////////////////////////////////////////////////////////////////////////
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_NOM_ACC);
            objTblModAcc.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblAcc);            
            setEditable(false);

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
   
    /*para adicionar el tooltip
     **/
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblAcc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_NOM_ACC:
                    strMsg="Nombre del Accionista";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Codigo del Cliente";
                    break;
            }
            tblAcc.getTableHeader().setToolTipText(strMsg);
        }
    }

 /////////////////////////////////////////////////////////////////////////
 //PARA LA BARRA DE HERRAMIENTAS
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            dtpFecDoc.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");            
            txtCedPasPro.setText("");
            txtNomPro.setText("");            
            txtDomPro.setText("");           
            txtTelPro.setText("");
            txtNacPro.setText("");
            txaObs1.setText("");
            txtTipActEmp.setText("");
            objTblModAcc.removeAllRows();
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
        
        if(txtCodCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cï¿½digo de Proveedor</FONT> es obligatorio.<BR>Escriba un cï¿½digo de proveedor y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }

        if(txtNomCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Proveedor</FONT> es obligatorio.<BR>Escriba un nombre de proveedor y vuelva a intentarlo.</HTML>");            
            txtNomPro.requestFocus();
            return false;
        }

        if(dtpFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba una fecha de documento y vuelva a intentarlo.</HTML>");            
            dtpFecDoc.requestFocus();
            return false;
        }
        
        return true;
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
                    if (eliminarDetAcc())
                    {
                        if (insertarDetAcc())
                        {
                            con.commit();
                            blnRes=true;
                        }                        
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
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
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cli";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET ";
                strSQL+=" tx_idePro = '" + txtCedPasPro.getText() + "'";
                strSQL+=", tx_nomPro = '" + txtNomPro.getText() + "'";
                strSQL+=", tx_dirPro = '" + txtDomPro.getText() + "'";
                strSQL+=", tx_telPro = '" + txtTelPro.getText() + "'";
                strSQL+=", tx_nacPro = '" + txtNacPro.getText() + "'";
                
                boolean fec = dtpFecDoc.isFecha();
                if(fec == false)
                    strAux=null;
                else
                    strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                
                System.out.println("--- fecha strAux ---tbm_cli_Acc---: " + strAux);                
                
                if(strAux == null || strAux == "")
                {
                    strSQL+=", fe_conEmp = " + strAux;
                }
                else
                {
                    strSQL+=", fe_conEmp = '" + strAux + "'";
                }
                
                
                
//                if(dtpFecDoc.getText().equals(""))
//                {
//                    strSQL+=", fe_conEmp = ' '";
//                }
//                else
//                {
//                    strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());                
//                    strSQL+=", fe_conEmp = '" + strAux + "'";
//                }
                
                strSQL+=", tx_tipActEmp = '" + txtTipActEmp.getText() + "'";
                strSQL+=", tx_obsPro = '" + txaObs1.getText() + "'";                
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_cli=" + rstCab.getString("co_cli");
                System.out.println("---SQL del update tbm_cli: " + strSQL);
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
     *Esta funcion elimina la cabecera del documento en la base de datos en la tabla tbm_cabpag
     *@return true: si elimina
     *<BR>false: caso contarrio
     */
    private boolean eliminarDetAcc()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_accEmpCli";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_cli=" + rstCab.getString("co_cli");
                System.out.println("---EliminarDetReg: " +strSQL);
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
     * Esta funciï¿½n permite Insertar el  detalle de Accionistas por cada cliente.
     * @return true: Si se pudo Insertar el detalle del cliente.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDetAcc()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc, strNatDoc="";
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();

                int intvalCountTrue = objTblModAcc.getRowCountTrue();
                System.out.println(" ---intvalCountTrue: " + intvalCountTrue);
                
                
                int intvalCount = objTblModAcc.getRowCount();
                System.out.println(" ---intvalCount: " + intvalCount);
                
                ///for (i=0;i<objTblModAcc.getRowCountTrue();i++)
                for (i=0; i<objTblModAcc.getRowCountTrue(); i++)
                {   
                    strSQL="";
                    strSQL+="INSERT INTO tbm_accEmpCli (co_emp, co_cli, co_reg, tx_nom ) ";
                    strSQL+=" VALUES (";
                    strSQL+="" + rstCab.getString("co_emp");
                    strSQL+=", " + rstCab.getString("co_cli");
                    strSQL+=", " + (i+1);
                    strSQL+=", '" + objTblModAcc.getValueAt(i,INT_TBL_DAT_NOM_ACC);
                    strSQL+=" ')";
                    System.out.println(" ---Insertar Detalle Accionistas: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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
    
    public class mitoolbar extends ZafToolBar
    {
            public mitoolbar(javax.swing.JInternalFrame jfrThis){
                super(jfrThis, objParSis);
            }

        public void clickInsertar(){
            
        }
        public boolean insertar(){
            return true;
        }
        public void clickEliminar(){
            consultarReg();
        }
        public boolean eliminar(){
            return true;
        }
        public void clickAnular(){
            consultarReg();
        }
        public boolean anular(){
            return true;
        }
        
        public void clickConsultar()
        {             
            txtCodCli.requestFocus();
        }
            
            
       public boolean consultar() 
       {
           consultarReg();
           return true;
       }
            
                                    
       public void clickModificar()
       {
            txtCodCli.setEditable(false);
            txtNomCli.setEditable(false);
            butPro.setEnabled(false);
            objTblModAcc.setModoOperacion(objTblModAcc.INT_TBL_INS);
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
                    if (blnHayCam /*|| objAsiDia.isDiarioModificado()*/)
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
                vecDatAcc.clear();                
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
                vecDatAcc.clear();
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
                vecDatAcc.clear();
                if (!rstCab.isLast())
                {
                    if (blnHayCam /*|| objAsiDia.isDiarioModificado()*/)
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
          //deshabilita los campos de la suma de los abonos 

          objTblModAcc.removeAllRows();
     }

     public void clickCancelar()
     {
          limpiarFrm();
          vecDatAcc.clear();
          //deshabilita los campos de la suma de los abonos
          objTblModAcc.removeAllRows();
     }

     private int Mensaje(){
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
            

     private boolean validaCampos(){               
                if(txtCodCli.getText().equals("") ){
                   MensajeValidaCampo("Cliente");
                   txtCodCli.requestFocus();
                   return false;
               }
               if(!dtpFecDoc.isFecha()){
                   MensajeValidaCampo("Fecha de Documento");
                   dtpFecDoc.requestFocus();
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
            objTooBar.setEstado('n');
            blnHayCam=false;
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
        
        public boolean cancelar() {
            return true;
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
                System.out.println("antes de llamar a monDoc");
                System.out.println("despues de llamar a monDoc");
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