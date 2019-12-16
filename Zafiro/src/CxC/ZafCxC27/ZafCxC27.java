/*
 * ZafCxC27.java
 *
 * Created on 07 de junio de 2007
 * CONTACTOS DE LA EMPRESA
 * CREADO POR DARIO CARDENAS LANDIN 08/06/2007
 * PASADO A PRODUCCION EL 12/SEP/2007
 */

package CxC.ZafCxC27;
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
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg; //para la ventana de dialogo
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;

/**
 *
 * @author  leo
 */
public class ZafCxC27 extends javax.swing.JInternalFrame 
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
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecuciï¿½n del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodCli, strDesLarCli,strIdeCli, strDirCli, strDesCorCta, strDesLarCta;
    private java.util.Vector vecRegAcc, vecRegPnd;
    private mitoolbar objTooBar;
    private String sSQLCon;
    private java.sql.Connection conCab, conDet, con, conCnsDet,conAnu;
    private ZafVenCon vcoCli;                           //Ventana de consulta.
    
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
    
    
    private double dblMonDoc=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;

    
    private int intSig=1;                              //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicaciï¿½n de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    
    /** Crea una nueva instancia de la clase ZafCon01. */
    public ZafCxC27(ZafParSis obj) 
    {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();       
            
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
    
    public ZafCxC27(ZafParSis obj, Integer codigoEmpresa, Integer codigoCliente, String nombreCliente, Integer codigoRegistro, Integer consulta, Integer modifica)
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
            ///
            System.out.println("ENTRO...POR IF---ZAFCXC27---7 CAMPOS---");
            ///limpiarFrm();
            if(m>=1)
            {
                System.out.println("SI...");
                objTooBar.setEstado('n');                
                txtCodDoc.setEditable(false);
                txtCodCli.setText(codigoCliente.toString());
                txtNomCli.setText(nombreCliente.toString());
                txtNomCon.requestFocus();
            }
            ///objTooBar.setEstado('w');
        }
        else
        {
            System.out.println("ENTRO...POR ELSE");
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
        
        lblTit.setText("Contactos en la Empresa...");
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
        panCabDoc = new javax.swing.JPanel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNomCon = new javax.swing.JLabel();
        txtNomCon = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtNomCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        butPro = new javax.swing.JButton();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtCarCon = new javax.swing.JTextField();
        lblCarCon = new javax.swing.JLabel();
        lblTelCon1 = new javax.swing.JLabel();
        txtTelCon1 = new javax.swing.JTextField();
        lblEmail1 = new javax.swing.JLabel();
        txtEmail1 = new javax.swing.JTextField();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        lblTelCon2 = new javax.swing.JLabel();
        txtTelCon2 = new javax.swing.JTextField();
        lblTelCon3 = new javax.swing.JLabel();
        txtTelCon3 = new javax.swing.JTextField();
        txtEmail2 = new javax.swing.JTextField();
        lblEmail2 = new javax.swing.JLabel();
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

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 320));
        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Codigo:");
        lblCodDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblCodDoc);
        lblCodDoc.setBounds(10, 37, 105, 15);

        txtCodDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        panCabDoc.add(txtCodDoc);
        txtCodDoc.setBounds(118, 35, 66, 20);

        lblNomCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomCon.setText("Nombre Contacto:");
        lblNomCon.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblNomCon);
        lblNomCon.setBounds(10, 67, 105, 15);

        txtNomCon.setPreferredSize(new java.awt.Dimension(6, 20));
        panCabDoc.add(txtNomCon);
        txtNomCon.setBounds(118, 65, 355, 20);

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
        txtNomCli.setBounds(184, 6, 265, 20);

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
        butPro.setBounds(450, 6, 24, 20);

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

        txtCarCon.setMaximumSize(null);
        txtCarCon.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtCarCon);
        txtCarCon.setBounds(118, 95, 355, 20);

        lblCarCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCarCon.setText("Cargo Contacto:");
        lblCarCon.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCarCon);
        lblCarCon.setBounds(10, 97, 105, 15);

        lblTelCon1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelCon1.setText("Tlf. Contacto1:");
        lblTelCon1.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblTelCon1);
        lblTelCon1.setBounds(10, 127, 105, 15);

        txtTelCon1.setMaximumSize(null);
        txtTelCon1.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtTelCon1);
        txtTelCon1.setBounds(118, 125, 100, 20);

        lblEmail1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEmail1.setText("Email1:");
        lblEmail1.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblEmail1);
        lblEmail1.setBounds(10, 157, 105, 15);

        txtEmail1.setMaximumSize(null);
        txtEmail1.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtEmail1);
        txtEmail1.setBounds(118, 155, 220, 20);

        lblObs1.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observaci\u00f3n:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 8));
        panCabDoc.add(lblObs1);
        lblObs1.setBounds(10, 190, 92, 23);

        spnObs1.setViewportView(txaObs1);

        panCabDoc.add(spnObs1);
        spnObs1.setBounds(118, 190, 550, 50);

        lblTelCon2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelCon2.setText("Tlf. Contacto2:");
        lblTelCon2.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblTelCon2);
        lblTelCon2.setBounds(250, 127, 105, 15);

        txtTelCon2.setMaximumSize(null);
        txtTelCon2.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtTelCon2);
        txtTelCon2.setBounds(340, 125, 100, 20);

        lblTelCon3.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTelCon3.setText("Tlf. Contacto3:");
        lblTelCon3.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblTelCon3);
        lblTelCon3.setBounds(480, 127, 105, 15);

        txtTelCon3.setMaximumSize(null);
        txtTelCon3.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtTelCon3);
        txtTelCon3.setBounds(570, 125, 100, 20);

        txtEmail2.setMaximumSize(null);
        txtEmail2.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtEmail2);
        txtEmail2.setBounds(450, 155, 220, 20);

        lblEmail2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblEmail2.setText("Email2:");
        lblEmail2.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblEmail2);
        lblEmail2.setBounds(400, 157, 105, 15);

        panDat.add(panCabDoc, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void txtCodTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTipDocActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
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

    
private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
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
                strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
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
//            //strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            
//            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
//                strSQL+=" WHERE a1.co_emp=" + CODEMP;
//            else
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            
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
//                        txtNomCon.requestFocus();
//                    }
//                    break;
//                case 1: //Bï¿½squeda directa por "Cï¿½digo".
//                    if (objVenCon.buscar("LOWER(a1.co_cli) LIKE '" + txtCodCli.getText() + "'"))
//                    {
//                        txtCodCli.setText(objVenCon.GetCamSel(1));
//                        strIdeCli=objVenCon.GetCamSel(2);
//                        txtNomCli.setText(objVenCon.GetCamSel(3));
//                        strDirCli=objVenCon.GetCamSel(4);
//                        txtNomCon.requestFocus();
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
//                            txtNomCon.requestFocus();
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
//                        txtNomCon.requestFocus();
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
//                            txtNomCon.requestFocus();
//                        }
//                        else
//                        {
//                            txtNomCli.setText(strDesLarCli);
//                        }
//                    }
//                    break;
//                default:
//                    txtCodCli.requestFocus();
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
    


    
    
    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed


    private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
        mostrarVenConCli(0);
        if (txtCodCli.getText().equals(""))
        {
//            objTblModAcc.removeAllRows();
            txtCodCli.requestFocus();
        }
    }//GEN-LAST:event_butProActionPerformed
    
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
    private javax.swing.JLabel lblCarCon;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblEmail1;
    private javax.swing.JLabel lblEmail2;
    private javax.swing.JLabel lblNomCon;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTelCon1;
    private javax.swing.JLabel lblTelCon2;
    private javax.swing.JLabel lblTelCon3;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCarCon;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtEmail1;
    private javax.swing.JTextField txtEmail2;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomCon;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtTelCon1;
    private javax.swing.JTextField txtTelCon2;
    private javax.swing.JTextField txtTelCon3;
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
               System.out.println("SE CONSULTO CAB DE UN REGISTRO CONSULTADO");
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
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intultdoc = ultCodDoc();
            System.out.println("---el ultimo codigo de documento es: " + intultdoc );
            
            codmodprg = rtnMod();
            System.out.println("---el modulo activo de este programa es: " + codmodprg );
            
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a2.co_emp, a1.co_loc, a2.co_cli, a2.tx_nom, a1.ne_mod, a1.co_reg, a1.tx_nom as tx_nomcon, a1.tx_car, a1.tx_tel1, a1.tx_tel2, a1.tx_tel3, a1.tx_corele1, a1.tx_corele2, a1.tx_obs1, a1.st_reg, ";
                strSQL+=" a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbm_concli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                ///strSQL+=" WHERE a2.co_emp = " + intCodEmp + "";
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a2.co_emp=" + CODEMP;
                else
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND a2.co_cli = " + txtCodCli.getText() + "";
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_reg = '" + strAux + "'";
                
                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a2. co_cli";
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
                System.out.println("SE CARGO CABECERA Y DETALLE DE UN REGISTRO CONSULTADO");
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
                strSQL+="SELECT a2.co_emp, a2.co_cli, a2.tx_nom, a1.ne_mod, a1.co_reg, a1.tx_nom as tx_nomcon, a1.tx_car, a1.tx_tel1, a1.tx_tel2, a1.tx_tel3, a1.tx_corele1, a1.tx_corele2, a1.tx_obs1, a1.st_reg, ";
                strSQL+=" a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod ";
                strSQL+=" FROM tbm_concli AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a2.co_emp = " + rstCab.getString("co_emp") + "";
                strSQL+=" AND a2.co_cli = " + rstCab.getString("co_cli") + "";
                strSQL+=" AND a1.co_reg = " + rstCab.getString("co_reg") + "";
                strSQL+=" AND a1.st_reg <> 'E'";
                strSQL+=" ORDER BY a1.co_reg ";
                
                System.out.println("---CargarCabReg: " +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {                   
                    txtCodCli.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtNomCli.setText(((rst.getString("tx_nom")==null)?"":rst.getString("tx_nom")));
                    txtCodDoc.setText(((rst.getString("co_reg")==null)?"":rst.getString("co_reg")));
                    txtNomCon.setText(((rst.getString("tx_nomcon")==null)?"":rst.getString("tx_nomcon")));
                    txtCarCon.setText(((rst.getString("tx_car")==null)?"":rst.getString("tx_car")));
                    txtTelCon1.setText(((rst.getString("tx_tel1")==null)?"":rst.getString("tx_tel1")));
                    txtTelCon2.setText(((rst.getString("tx_tel2")==null)?"":rst.getString("tx_tel2")));
                    txtTelCon3.setText(((rst.getString("tx_tel3")==null)?"":rst.getString("tx_tel3")));
                    txtEmail1.setText(((rst.getString("tx_corele1")==null)?"":rst.getString("tx_corele1")));
                    txtEmail2.setText(((rst.getString("tx_corele2")==null)?"":rst.getString("tx_corele2")));
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
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_concli";
                strSQL+=" (co_emp, co_loc, co_cli, ne_mod, co_reg, tx_nom, tx_car, tx_tel1, tx_tel2, tx_tel3, tx_corEle1, tx_corEle2, tx_obs1, ";
                strSQL+=" st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) ";
                strSQL+=" VALUES (";
                strSQL+=" "  + objParSis.getCodigoEmpresa();
                strSQL+=", " + objParSis.getCodigoLocal();
                strSQL+=", " + txtCodCli.getText();
                strSQL+=", '3'";
                strSQL+=", " + ultcoddoc;
                strSQL+=", '" + txtNomCon.getText()  + "'";
                strSQL+=", '" + txtCarCon.getText()  + "'";
                strSQL+=", '" + txtTelCon1.getText() + "'";
                strSQL+=", '" + txtTelCon2.getText() + "'";
                strSQL+=", '" + txtTelCon3.getText() + "'";
                strSQL+=", '" + txtEmail1.getText()  + "'";
                strSQL+=", '" + txtEmail2.getText()  + "'";
                strSQL+=", '" + txaObs1.getText()    + "'";
                strSQL+=", 'A'";
                strSQL+=", '" + strFecSis + "'";
                strSQL+=", '" + strFecSis + "'";
                strSQL+=", " + objParSis.getCodigoUsuario();
                strSQL+=", " + objParSis.getCodigoUsuario();
                strSQL+=")";
                System.out.println("---SQL del Insert tbm_concli: " + strSQL);
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
                strSQL+="UPDATE tbm_concli";
                strSQL+=" SET ";
                strSQL+=" st_reg = 'E'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_cli=" + rstCab.getString("co_cli");
                strSQL+=" AND co_reg=" + txtCodDoc.getText();
                System.out.println("---SQL del EliminarReg tbm_concli: " + strSQL);
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
                strSQL+="UPDATE tbm_concli";
                strSQL+=" SET ";
                strSQL+=" st_reg = 'I'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";                
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_cli=" + rstCab.getString("co_cli");
                strSQL+=" AND co_reg=" + txtCodDoc.getText();
                System.out.println("---SQL del AnularReg tbm_concli: " + strSQL);
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
                strSQL+="UPDATE tbm_concli";
                strSQL+=" SET ";
                strSQL+=" tx_nom = '" + txtNomCon.getText() + "'";
                strSQL+=", tx_car = '" + txtCarCon.getText() + "'";
                strSQL+=", tx_tel1 = '" + txtTelCon1.getText() + "'";
                strSQL+=", tx_tel2 = '" + txtTelCon2.getText() + "'";
                strSQL+=", tx_tel3 = '" + txtTelCon3.getText() + "'";
                strSQL+=", tx_corele1 = '" + txtEmail1.getText() + "'";
                strSQL+=", tx_corele2 = '" + txtEmail2.getText() + "'";
                strSQL+=", tx_obs1 = '" + txaObs1.getText() + "'";
                strSQL+=", fe_ultmod = '" + strFecSis + "'";
                strSQL+=", co_usrmod = '" + objParSis.getCodigoUsuario() + "'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_cli=" + rstCab.getString("co_cli");
                strSQL+=" AND co_reg=" + txtCodDoc.getText();
                System.out.println("---SQL del update tbm_concli: " + strSQL);
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
            this.setTitle(objParSis.getNombreMenu()+ " V 0.1.6");
            lblTit.setText(""+objParSis.getNombreMenu());

            configurarVenConCli();
            
            //asigno el color de fondo de los campos 
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtNomCon.setBackground(objParSis.getColorCamposObligatorios());
            
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
            txtCodCli.setText("");
            txtNomCli.setText("");            
            txtCodDoc.setText("");
            txtNomCon.setText("");            
            txtCarCon.setText("");           
            txtTelCon1.setText("");
            txtTelCon2.setText("");
            txtTelCon3.setText("");
            txtEmail1.setText("");
            txtEmail2.setText("");
            txaObs1.setText("");
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
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cï¿½digo de Cliente</FONT> es obligatorio.<BR>Escriba un cï¿½digo de cliente y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }

        if(txtNomCon.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Contacto </FONT> es obligatorio.<BR>Escriba un Nombre de Contacto y vuelva a intentarlo.</HTML>");
            txtNomCon.requestFocus();
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
                sqlDoc+="select max(b2.co_reg) as ultdoc ";
                sqlDoc+=" from tbm_cli as b1 ";
                sqlDoc+=" inner join tbm_concli as b2 ON ";
                sqlDoc+=" (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli) ";
                ///sqlDoc+=" where b1.co_emp=" + objParSis.getCodigoEmpresa() + ""; 
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    sqlDoc+=" WHERE b1.co_emp=" + CODEMP;
                else
                    sqlDoc+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                
                sqlDoc+=" and b1.co_cli=" + txtCodCli.getText() + "";
                System.out.println(" ---query ultcoddoc: " + sqlDoc);
                intUltDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), sqlDoc);
                ///intUltDoc++;
                rstNumDoc=stmNumDoc.executeQuery(sqlDoc);      
                if(rstNumDoc.next())
                    ///txtCodDoc.setText(String.valueOf(intUltDoc));
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
                txtCodCli.requestFocus();
                txtCodDoc.setEditable(false);
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
            txtCodDoc.setEditable(false);
            txtNomCon.requestFocus();
            butPro.setEnabled(false);
            consultarReg();
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
                
                if(txtNomCon.getText().equals("") )
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
            if(txtCodCli.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cï¿½digo de Cliente</FONT> es obligatorio.<BR>Escriba un cï¿½digo de cliente y vuelva a intentarlo.</HTML>");
                txtCodCli.requestFocus();
                return false;
            }
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