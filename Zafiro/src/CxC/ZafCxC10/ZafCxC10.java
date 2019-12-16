/*
 * ZafCxC10.java 
 * PROGRAMA : NOTAS DE DEBITO Y NOTAS DE CREDITO PARA CLIENTES Y PROVEEDORES
 * Created on 27 de noviembre de 2004, 12:50 PM * 
 * CREADO POR ING. INGRID LINO
 */
package CxC.ZafCxC10;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.DriverManager;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafCtaCtb;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ZafCxC10 extends javax.swing.JInternalFrame 
{
    //Constante IVA
    private double dblPorIva=0.00;                                              //Porcentaje del IVA. Ej.:12.00
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private ZafDocLis objDocLis;
    private ZafAsiDia objAsiDia;
    private ZafVenCon vcoTipDoc;                                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCli;                                                   //Ventana de consulta "Cliente".
    private ZafVenCon vcoCta;                                                   //Ventana de consulta "Cuenta".
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;
    private ZafCtaCtb objCtaCtb;                                                //Objeto que obtiene porcentaje del iva.                
    private ZafDatePicker txtFecDoc;
    private java.util.Date datFecAux;                                           //Auxiliar: Para almacenar fechas.
    javax.swing.JInternalFrame jfrThis;                                         //Hace referencia a this   
    
    private boolean blnHayCam;                                                  //Determina si hay cambios en el formulario.
    private int intCodMnu = 0;
    private int intSig = 1;
    private double VALPAGASO = 0.00;
    private String strSQL, strAux;
    private String strDesLarTipDoc, strDesCorTipDoc, strDesCorCta, strUbiCta, strDesLarCta, strAutCta;
    private String strCodCli, strIdeCli, strDirCli, strDesLarCli;
    private String strVersion = " v5.5 ";

    /**
     * Crea una nueva instancia de la clase ZafCxC10.
     */
    public ZafCxC10(ZafParSis obj)
    {
        try 
        {
            this.objParSis = obj;
            jfrThis = this;
            
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) jfrThis.getParent()), "d/m/y");
                txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
                txtFecDoc.setText("");
                panCabDoc.add(txtFecDoc);
                txtFecDoc.setBounds(560, 10, 110, 20);

                butImp.setVisible(false);
                butVisPre.setVisible(false);

                txtCodTipDoc.setVisible(false);
                txtCodCta.setVisible(false);
                txtNumCta.setVisible(false);

                //Ejemplo para Agregar un Boton a la Barra de Botones.
                objTooBar = new MiToolBar(this);
                //objTooBar.agregarSeparador();
                //objTooBar.agregarBoton(butImp);
                //objTooBar.agregarSeparador();
                //objTooBar.agregarBoton(butVisPre);
                panBar.add(objTooBar);//llama a la barra de botones

                //Configurar ventana cuando sea Nota de Credito o Debito para Proveedor///
                if (objParSis.getCodigoMenu() == 296 || objParSis.getCodigoMenu() == 306 || objParSis.getCodigoMenu() == 1414) 
                {   
                    lblSubIvaCer.setVisible(false);
                    txtSubIvaCer.setVisible(false);
                    chkIva.setVisible(false);
                    txtIva.setVisible(false);
                    lblTot.setVisible(false);
                    txtTot.setVisible(false);
                    txtNumSer.setVisible(false);
                    txtNumSec.setVisible(false);
                    txtNumAut.setVisible(false);
                    lblNumAut.setVisible(false);
                    lblNumSec.setVisible(false);
                    lblNumSer.setVisible(false);
                    jScrollPane1.setVisible(false);
                }else{
                    //lblMonDoc.setText("Subtotal: ");
                   lblMonDoc.setText("Subtotal IVA: ");
                }

                if (!configurarFrm()) {
                    exitForm();
                }
                configurarTblFacPrv();
                objCtaCtb  = new ZafCtaCtb(objParSis);
                if(objParSis.getCodigoMenu()==296 || objParSis.getCodigoMenu()==306)
                    dblPorIva= objCtaCtb.getPorcentajeIvaVentas();
                else if(objParSis.getCodigoMenu()==1414 || objParSis.getCodigoMenu()==1424)
                    dblPorIva= objCtaCtb.getPorcentajeIvaCompras();

                
                objAsiDia = new ZafAsiDia(objParSis);
                panDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

                ///////asiento mejorado////
                objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                    public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                        if (txtCodTipDoc.getText().equals("")) {
                            objAsiDia.setCodigoTipoDocumento(-1);
                        } else {
                            objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                        }
                    }
                });
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e) {        this.setTitle(this.getTitle() + " [ERROR]");     }

        //agregarDocLis();
    }

    /**
     * Crea una nueva instancia de la clase ZafCxC10.
     */
    public ZafCxC10(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu) 
    {
        try 
        {
            this.objParSis = obj;
            jfrThis = this;
            
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            
            initComponents();
             
            objCtaCtb  = new ZafCtaCtb(objParSis);
            if(objParSis.getCodigoMenu()==296 || objParSis.getCodigoMenu()==306)
                dblPorIva= objCtaCtb.getPorcentajeIvaVentas();
            else if(objParSis.getCodigoMenu()==1414 || objParSis.getCodigoMenu()==1424)
                dblPorIva= objCtaCtb.getPorcentajeIvaCompras();
            
            
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) jfrThis.getParent()), "d/m/y");

            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panCabDoc.add(txtFecDoc);
            txtFecDoc.setBounds(552, 4, 92, 20);

            butImp.setVisible(false);
            butVisPre.setVisible(false);

            ////Ejemplo para Agregar un Boton a la Barra de Botones///
            objTooBar = new MiToolBar(this);
            panBar.add(objTooBar);//llama a la barra de botones

            txtCodTipDoc.setText("" + codigoTipoDocumento);
            txtCodDoc.setText("" + codigoDocumento);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);

            txtCodTipDoc.setVisible(false);
            txtCodCta.setVisible(false);
            txtNumCta.setVisible(false);
            
            ////Configurar ventana cuando sea Nota de Credito o Debito para Proveedor///
            if (objParSis.getCodigoMenu() == 296 || objParSis.getCodigoMenu() == 306 || objParSis.getCodigoMenu() == 1414) 
            {
                chkIva.setVisible(false);
                txtIva.setVisible(false);
                lblTot.setVisible(false);
                txtTot.setVisible(false);
            }

            if (!configurarFrm()) {
                exitForm();
            }

            objAsiDia = new ZafAsiDia(objParSis);
            panDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

            ///////asiento mejorado////
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objAsiDia.setCodigoTipoDocumento(-1);
                    } else {
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                }
            });

            consultarReg();
            configurarTblFacPrv();
            objTooBar.setVisible(false);

        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }

        //agregarDocLis();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      bgrTipCta = new javax.swing.ButtonGroup();
      bgrNatCta = new javax.swing.ButtonGroup();
      panFrm = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();
      tabFrm = new javax.swing.JTabbedPane();
      panDat = new javax.swing.JPanel();
      panCabDoc = new javax.swing.JPanel();
      lblTipDoc = new javax.swing.JLabel();
      txtCodTipDoc = new javax.swing.JTextField();
      txtDesCorTipDoc = new javax.swing.JTextField();
      txtDesLarTipDoc = new javax.swing.JTextField();
      butTipDoc = new javax.swing.JButton();
      lblCta = new javax.swing.JLabel();
      txtCodCta = new javax.swing.JTextField();
      txtNumCta = new javax.swing.JTextField();
      txtDesCorCta = new javax.swing.JTextField();
      txtNomCta = new javax.swing.JTextField();
      butCta = new javax.swing.JButton();
      lblCli = new javax.swing.JLabel();
      txtCodCli = new javax.swing.JTextField();
      txtNomCli = new javax.swing.JTextField();
      butCli = new javax.swing.JButton();
      lblCodDoc = new javax.swing.JLabel();
      txtCodDoc = new javax.swing.JTextField();
      lblFecDoc = new javax.swing.JLabel();
      lblNumAltUno = new javax.swing.JLabel();
      txtNumAltUno = new javax.swing.JTextField();
      lblNumAltDos = new javax.swing.JLabel();
      txtNumAltDos = new javax.swing.JTextField();
      lblMonDoc = new javax.swing.JLabel();
      txtMonDoc = new javax.swing.JTextField();
      chkIva = new javax.swing.JCheckBox();
      txtIva = new javax.swing.JTextField();
      lblTot = new javax.swing.JLabel();
      txtTot = new javax.swing.JTextField();
      butVisPre = new javax.swing.JButton();
      butImp = new javax.swing.JButton();
      jScrollPane1 = new javax.swing.JScrollPane();
      tblFacPrv = new javax.swing.JTable();
      lblNumSer = new javax.swing.JLabel();
      txtNumSer = new javax.swing.JTextField();
      lblNumSec = new javax.swing.JLabel();
      txtNumSec = new javax.swing.JTextField();
      lblNumAut = new javax.swing.JLabel();
      txtNumAut = new javax.swing.JTextField();
      txtSubIvaCer = new javax.swing.JTextField();
      lblSubIvaCer = new javax.swing.JLabel();
      spnObs = new javax.swing.JScrollPane();
      panObs = new javax.swing.JPanel();
      panLbl = new javax.swing.JPanel();
      lblObs1 = new javax.swing.JLabel();
      lblObs2 = new javax.swing.JLabel();
      panTxa = new javax.swing.JPanel();
      spnObs1 = new javax.swing.JScrollPane();
      txaObs1 = new javax.swing.JTextArea();
      spnObs2 = new javax.swing.JScrollPane();
      txaObs2 = new javax.swing.JTextArea();
      panDia = new javax.swing.JPanel();
      panBar = new javax.swing.JPanel();

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
      getContentPane().setLayout(new java.awt.GridLayout(1, 0));

      panFrm.setLayout(new java.awt.BorderLayout());

      lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
      lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      lblTit.setText("Título de la ventana");
      panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

      panDat.setPreferredSize(new java.awt.Dimension(600, 80));
      panDat.setLayout(null);

      panCabDoc.setPreferredSize(new java.awt.Dimension(400, 225));
      panCabDoc.setLayout(null);

      lblTipDoc.setText("Tipo de documento:");
      panCabDoc.add(lblTipDoc);
      lblTipDoc.setBounds(4, 10, 120, 14);
      panCabDoc.add(txtCodTipDoc);
      txtCodTipDoc.setBounds(120, 10, 20, 20);

      txtDesCorTipDoc.setMaximumSize(null);
      txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
      txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesCorTipDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesCorTipDocFocusLost(evt);
         }
      });
      txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesCorTipDocActionPerformed(evt);
         }
      });
      panCabDoc.add(txtDesCorTipDoc);
      txtDesCorTipDoc.setBounds(140, 10, 80, 20);

      txtDesLarTipDoc.setMaximumSize(null);
      txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
      txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesLarTipDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesLarTipDocFocusLost(evt);
         }
      });
      txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesLarTipDocActionPerformed(evt);
         }
      });
      panCabDoc.add(txtDesLarTipDoc);
      txtDesLarTipDoc.setBounds(220, 10, 170, 20);

      butTipDoc.setText("...");
      butTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butTipDocActionPerformed(evt);
         }
      });
      panCabDoc.add(butTipDoc);
      butTipDoc.setBounds(390, 10, 20, 20);

      lblCta.setText("Cuenta:");
      lblCta.setPreferredSize(new java.awt.Dimension(110, 15));
      panCabDoc.add(lblCta);
      lblCta.setBounds(4, 30, 110, 15);

      txtCodCta.setMaximumSize(null);
      txtCodCta.setPreferredSize(new java.awt.Dimension(70, 20));
      txtCodCta.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodCtaFocusLost(evt);
         }
      });
      txtCodCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodCtaActionPerformed(evt);
         }
      });
      panCabDoc.add(txtCodCta);
      txtCodCta.setBounds(80, 30, 20, 20);

      txtNumCta.setMaximumSize(null);
      txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
      panCabDoc.add(txtNumCta);
      txtNumCta.setBounds(100, 30, 70, 20);

      txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesCorCtaFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesCorCtaFocusLost(evt);
         }
      });
      txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesCorCtaActionPerformed(evt);
         }
      });
      panCabDoc.add(txtDesCorCta);
      txtDesCorCta.setBounds(140, 30, 80, 20);

      txtNomCta.setMaximumSize(null);
      txtNomCta.setPreferredSize(new java.awt.Dimension(70, 20));
      txtNomCta.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNomCtaFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNomCtaFocusLost(evt);
         }
      });
      txtNomCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNomCtaActionPerformed(evt);
         }
      });
      panCabDoc.add(txtNomCta);
      txtNomCta.setBounds(220, 30, 170, 20);

      butCta.setText("...");
      butCta.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCtaActionPerformed(evt);
         }
      });
      panCabDoc.add(butCta);
      butCta.setBounds(390, 30, 20, 20);

      lblCli.setText("Cliente:");
      lblCli.setPreferredSize(new java.awt.Dimension(110, 15));
      panCabDoc.add(lblCli);
      lblCli.setBounds(4, 50, 100, 15);

      txtCodCli.setMaximumSize(null);
      txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
      txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCodCliFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodCliFocusLost(evt);
         }
      });
      txtCodCli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodCliActionPerformed(evt);
         }
      });
      panCabDoc.add(txtCodCli);
      txtCodCli.setBounds(140, 50, 80, 20);

      txtNomCli.setMaximumSize(null);
      txtNomCli.setPreferredSize(new java.awt.Dimension(70, 20));
      txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNomCliFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNomCliFocusLost(evt);
         }
      });
      txtNomCli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNomCliActionPerformed(evt);
         }
      });
      panCabDoc.add(txtNomCli);
      txtNomCli.setBounds(220, 50, 170, 20);

      butCli.setText("...");
      butCli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCliActionPerformed(evt);
         }
      });
      panCabDoc.add(butCli);
      butCli.setBounds(390, 50, 20, 20);

      lblCodDoc.setText("Código del documento:");
      lblCodDoc.setPreferredSize(new java.awt.Dimension(110, 15));
      panCabDoc.add(lblCodDoc);
      lblCodDoc.setBounds(4, 70, 130, 20);

      txtCodDoc.setMaximumSize(null);
      txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
      panCabDoc.add(txtCodDoc);
      txtCodDoc.setBounds(140, 70, 120, 20);

      lblFecDoc.setText("Fecha del documento:");
      lblFecDoc.setPreferredSize(new java.awt.Dimension(100, 15));
      panCabDoc.add(lblFecDoc);
      lblFecDoc.setBounds(430, 10, 130, 15);

      lblNumAltUno.setText("Número alterno 1:");
      lblNumAltUno.setPreferredSize(new java.awt.Dimension(100, 15));
      panCabDoc.add(lblNumAltUno);
      lblNumAltUno.setBounds(430, 30, 130, 15);

      txtNumAltUno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtNumAltUno.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNumAltUnoActionPerformed(evt);
         }
      });
      panCabDoc.add(txtNumAltUno);
      txtNumAltUno.setBounds(560, 30, 110, 18);

      lblNumAltDos.setText("Número alterno 2:");
      lblNumAltDos.setPreferredSize(new java.awt.Dimension(100, 15));
      panCabDoc.add(lblNumAltDos);
      lblNumAltDos.setBounds(430, 49, 130, 15);

      txtNumAltDos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtNumAltDos.setMaximumSize(null);
      txtNumAltDos.setPreferredSize(new java.awt.Dimension(70, 20));
      panCabDoc.add(txtNumAltDos);
      txtNumAltDos.setBounds(560, 49, 110, 18);

      lblMonDoc.setText("Monto del documento:");
      lblMonDoc.setPreferredSize(new java.awt.Dimension(100, 15));
      panCabDoc.add(lblMonDoc);
      lblMonDoc.setBounds(430, 87, 130, 15);

      txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtMonDoc.setMaximumSize(null);
      txtMonDoc.setPreferredSize(new java.awt.Dimension(70, 20));
      txtMonDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtMonDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtMonDocFocusLost(evt);
         }
      });
      panCabDoc.add(txtMonDoc);
      txtMonDoc.setBounds(560, 87, 110, 18);

      chkIva.setText("I.V.A.");
      chkIva.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            chkIvaActionPerformed(evt);
         }
      });
      panCabDoc.add(chkIva);
      chkIva.setBounds(470, 106, 80, 20);

      txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCabDoc.add(txtIva);
      txtIva.setBounds(560, 106, 110, 18);

      lblTot.setText("Total Documento:");
      panCabDoc.add(lblTot);
      lblTot.setBounds(430, 125, 130, 20);

      txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCabDoc.add(txtTot);
      txtTot.setBounds(560, 125, 110, 18);

      butVisPre.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
      butVisPre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/printpreview.gif"))); // NOI18N
      butVisPre.setText("Vista Pre.");
      butVisPre.setToolTipText("Vista Previa para Formulario");
      butVisPre.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butVisPreActionPerformed(evt);
         }
      });
      panCabDoc.add(butVisPre);
      butVisPre.setBounds(310, 100, 110, 33);

      butImp.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
      butImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/print.gif"))); // NOI18N
      butImp.setText("Imprimir");
      butImp.setToolTipText("Impresion Directa para Formulario");
      butImp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butImpActionPerformed(evt);
         }
      });
      panCabDoc.add(butImp);
      butImp.setBounds(300, 70, 110, 33);

      tblFacPrv.setModel(new javax.swing.table.DefaultTableModel(
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
      jScrollPane1.setViewportView(tblFacPrv);

      panCabDoc.add(jScrollPane1);
      jScrollPane1.setBounds(0, 150, 670, 100);

      lblNumSer.setText("Número de serie:");
      lblNumSer.setPreferredSize(new java.awt.Dimension(110, 15));
      lblNumSer.setVerifyInputWhenFocusTarget(false);
      panCabDoc.add(lblNumSer);
      lblNumSer.setBounds(4, 93, 130, 15);

      txtNumSer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtNumSer.setMaximumSize(null);
      txtNumSer.setPreferredSize(new java.awt.Dimension(70, 20));
      txtNumSer.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNumSerFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNumSerFocusLost(evt);
         }
      });
      panCabDoc.add(txtNumSer);
      txtNumSer.setBounds(140, 90, 120, 20);

      lblNumSec.setText("Número de secuencia:");
      lblNumSec.setPreferredSize(new java.awt.Dimension(100, 15));
      panCabDoc.add(lblNumSec);
      lblNumSec.setBounds(4, 112, 130, 15);

      txtNumSec.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtNumSec.setMaximumSize(null);
      txtNumSec.setPreferredSize(new java.awt.Dimension(70, 20));
      txtNumSec.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNumSecFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNumSecFocusLost(evt);
         }
      });
      panCabDoc.add(txtNumSec);
      txtNumSec.setBounds(140, 110, 120, 20);

      lblNumAut.setText("Número de autorización");
      lblNumAut.setAlignmentX(0.5F);
      lblNumAut.setPreferredSize(new java.awt.Dimension(100, 15));
      panCabDoc.add(lblNumAut);
      lblNumAut.setBounds(4, 130, 140, 15);

      txtNumAut.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtNumAut.setMaximumSize(null);
      txtNumAut.setPreferredSize(new java.awt.Dimension(70, 20));
      txtNumAut.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNumAutFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNumAutFocusLost(evt);
         }
      });
      panCabDoc.add(txtNumAut);
      txtNumAut.setBounds(140, 130, 120, 20);

      txtSubIvaCer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtSubIvaCer.setMaximumSize(null);
      txtSubIvaCer.setPreferredSize(new java.awt.Dimension(70, 20));
      txtSubIvaCer.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtSubIvaCerFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtSubIvaCerFocusLost(evt);
         }
      });
      panCabDoc.add(txtSubIvaCer);
      txtSubIvaCer.setBounds(560, 68, 110, 18);

      lblSubIvaCer.setText("Subtotal Base 0:");
      lblSubIvaCer.setPreferredSize(new java.awt.Dimension(100, 15));
      panCabDoc.add(lblSubIvaCer);
      lblSubIvaCer.setBounds(430, 68, 130, 15);

      panDat.add(panCabDoc);
      panCabDoc.setBounds(0, 0, 679, 250);

      spnObs.setPreferredSize(new java.awt.Dimension(100, 80));

      panObs.setPreferredSize(new java.awt.Dimension(300, 60));
      panObs.setLayout(new java.awt.BorderLayout());

      panLbl.setPreferredSize(new java.awt.Dimension(92, 18));
      panLbl.setLayout(new java.awt.GridLayout(2, 1));

      lblObs1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
      lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
      lblObs1.setText("Observación 1:");
      lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
      lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
      lblObs1.setPreferredSize(new java.awt.Dimension(52, 15));
      panLbl.add(lblObs1);

      lblObs2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
      lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
      lblObs2.setText("Observación 2:");
      lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
      lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
      lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
      lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
      panLbl.add(lblObs2);

      panObs.add(panLbl, java.awt.BorderLayout.WEST);

      panTxa.setPreferredSize(new java.awt.Dimension(3, 15));
      panTxa.setLayout(new java.awt.GridLayout(2, 1, 10, 1));

      spnObs1.setMinimumSize(new java.awt.Dimension(23, 25));
      spnObs1.setPreferredSize(new java.awt.Dimension(6, 25));

      txaObs1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
      txaObs1.setPreferredSize(new java.awt.Dimension(4, 20));
      spnObs1.setViewportView(txaObs1);

      panTxa.add(spnObs1);

      spnObs2.setPreferredSize(new java.awt.Dimension(6, 25));

      txaObs2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
      spnObs2.setViewportView(txaObs2);

      panTxa.add(spnObs2);

      panObs.add(panTxa, java.awt.BorderLayout.CENTER);

      spnObs.setViewportView(panObs);

      panDat.add(spnObs);
      spnObs.setBounds(0, 250, 670, 70);

      tabFrm.addTab("General", panDat);

      panDia.setLayout(new java.awt.BorderLayout());
      tabFrm.addTab("Asiento de Diario", panDia);

      panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

      panBar.setPreferredSize(new java.awt.Dimension(0, 60));
      panBar.setLayout(new java.awt.BorderLayout());
      panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

      getContentPane().add(panFrm);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-692)/2, (screenSize.height-458)/2, 692, 458);
   }// </editor-fold>//GEN-END:initComponents

    private void chkIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIvaActionPerformed
        
        double ValSubIvaCer = 0, ValSubIvaGra = 0, ValIva = 0, ValTot = 0;
        
        if (chkIva.isSelected() == true) 
        {
            ValSubIvaCer = Double.parseDouble(txtSubIvaCer.getText());
            ValSubIvaGra = Double.parseDouble(txtMonDoc.getText());
            ValIva = objUti.redondear((ValSubIvaGra * ((dblPorIva / 100))), 2);
            txtIva.setText("" + ValIva);
            ValTot = ValSubIvaCer + ValSubIvaGra + ValIva;
            txtTot.setText("" + ValTot);
        }
        else 
        {
            txtIva.setText("0.0");
            txtTot.setText("" + txtMonDoc.getText());
        }
        calculaTotal();
    }//GEN-LAST:event_chkIvaActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.setIndFunEje(1);
            objThrGUI.start();
        }
}//GEN-LAST:event_butImpActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.setIndFunEje(1);
            objThrGUI.start();
        }
}//GEN-LAST:event_butVisPreActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtNomCta.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtMonDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonDocFocusLost
        //calculaTotal();
        calcularTotDoc_FocusLost();
    }//GEN-LAST:event_txtMonDocFocusLost

    private void txtMonDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonDocFocusGained
        //objAsiDia.generarDiario(txtTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
    }//GEN-LAST:event_txtMonDocFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
                txtMonDoc.setText("");
            } else {
                mostrarVenConCli(2);
                if (txtCodCli.getText().equals("")) {
                    txtMonDoc.setText("");
                }
            }
        } else {
            txtNomCli.setText(strDesLarCli);
        }
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesLarCli = txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli = txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtNomCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtNomCta.getText().equalsIgnoreCase(strDesLarCta)) {
            if (txtNomCta.getText().equals("")) {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
            } else {
                mostrarVenConCta(2);
            }
        } else {
            txtNomCta.setText(strDesLarCta);
        }
    }//GEN-LAST:event_txtNomCtaFocusLost

    private void txtNomCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaFocusGained
        strDesLarCta = txtNomCta.getText();
        txtNomCta.selectAll();
    }//GEN-LAST:event_txtNomCtaFocusGained

    private void txtNomCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaActionPerformed
        txtNomCta.transferFocus();
    }//GEN-LAST:event_txtNomCtaActionPerformed

    private void calculaTotal() 
    {
        double dblvalor;   
        if (isChangeValueTotal(txtMonDoc, txtIva, txtTot))
        {
            if (objUti.isNumero(txtMonDoc.getText())) {
                if (chkIva.isSelected()) {
                    dblvalor = objUti.redondear(Double.parseDouble(txtMonDoc.getText().equals("") ? "0" : txtMonDoc.getText()) * (dblPorIva / 100), objParSis.getDecimalesMostrar());
                    txtIva.setText(String.valueOf(dblvalor));
                } else {
                    txtIva.setText("0.0");
                }
                dblvalor =  objUti.redondear( Double.parseDouble((txtSubIvaCer.getText().equals("") ? "0" : txtSubIvaCer.getText())) + Double.parseDouble((txtMonDoc.getText().equals("") ? "0" : txtMonDoc.getText())) + Double.parseDouble((txtIva.getText().equals("") ? "0" : txtIva.getText())), objParSis.getDecimalesMostrar() );
                txtTot.setText(String.valueOf(dblvalor));
            } else {
                ///txtMonDoc.setText("0.0");
                txtIva.setText("0.0");
                txtTot.setText("0.0");
            }
        }
    }

    private boolean isChangeValueTotal(javax.swing.JTextField txtSub, javax.swing.JTextField txtIva, javax.swing.JTextField txtTot) 
    {
        if (objUti.redondear(Double.parseDouble(txtMonDoc.getText()) + Double.parseDouble(txtIva.getText()), objParSis.getDecimalesMostrar()) != Double.parseDouble(txtTot.getText())) 
            return true;
        else 
            return false;
    }

    private boolean regenerarDiario() 
    {
        boolean blnRes = true;
        if (objAsiDia.getGeneracionDiario() == 2) 
        {
            strAux = "¿Desea regenerar el asiento de diario?\n";
            strAux += "El asiento de diario ha sido modificado manualmente.";
            strAux += "\nSi desea que el sistema regenere el asiento de diario de click en SI.";
            strAux += "\nSi desea grabar el asiento de diario tal como esta de click en NO.";
            strAux += "\nSi desea cancelar ésta operación de click en CANCELAR.";
            switch (mostrarMsgCon(strAux)) 
            {
                case 0: //YES_OPTION
                    objAsiDia.setGeneracionDiario((byte) 0);
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3), objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3));
                    break;
                case 1: //NO_OPTION
                    break;
                case 2: //CANCEL_OPTION
                    blnRes = false;
            }
        }
        else 
        {
            objAsiDia.setGeneracionDiario((byte) 0);
            objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3), objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3));
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada
     * para mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        int intCodUsr = objParSis.getCodigoUsuario();
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Codigo");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");

            if (intCodUsr == 1) 
            {
                ///Armar la sentencia SQL.///
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                ///System.out.println("---Query Ventana consulta usuario ADMIN:--configurarVenConTipDoc()-- "+strSQL);
            } 
            else
            {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario();
                ///System.out.println("---Query Ventana consulta usuario VARIOS:--configurarVenConTipDoc()-- "+strSQL);
            }

            //Ocultar columnas.//
            int intColOcu[] = new int[1];
            intColOcu[0] = 5;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) {       blnRes = false;        objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una búsqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se está buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado() == 'n') 
                        {
                            strAux = vcoTipDoc.getValueAt(4);
                            txtNumAltUno.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                        }
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);

                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_descor", txtDesCorTipDoc.getText())) 
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado() == 'n') 
                        {
                            strAux = vcoTipDoc.getValueAt(4);
                            txtNumAltUno.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                        }
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') 
                            {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumAltUno.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                        } 
                        else 
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripciï¿½n larga".
                    if (vcoTipDoc.buscar("a1.tx_deslar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado() == 'n') 
                        {
                            strAux = vcoTipDoc.getValueAt(4);
                            txtNumAltUno.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                        }
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    }
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado() == 'n') 
                            {
                                strAux = vcoTipDoc.getValueAt(4);
                                txtNumAltUno.setText("" + (objUti.isNumero(strAux) ? Integer.parseInt(strAux) + 1 : 1));
                            }
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                        }
                        else 
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                default:
                    txtCodTipDoc.requestFocus();
                    break;

            }
        } 
        catch (Exception e) {         blnRes = false;         objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada
     * para mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli() 
    {
        boolean blnRes = true;

        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");

            //Armar la sentencia SQL.
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())) 
            {
                strSQL = "";
                strSQL += "SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL += " FROM tbm_cli AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.st_reg='A'";
                strSQL += " ORDER BY a1.tx_nom";
                //System.out.println("---QUERY PARA CONCLI---: " + strSQL);
            } 
            else 
            {
                strSQL = "";
                strSQL += "SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL += " FROM  tbr_cliloc AS a1";
                strSQL += " INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.st_reg='A'";
                strSQL += " ORDER BY a2.tx_nom";
                //System.out.println("---QUERY PARA CONCLI POR LOCAL---: " + strSQL);
            }

            vcoCli = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        } 
        catch (Exception e) {       blnRes = false;         objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una búsqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se estï¿½ buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opciï¿½n que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus) 
    {
        boolean blnRes = true;
        try {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton() == vcoCli.INT_BUT_ACE) 
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Código".
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
                        if (vcoCli.getSelectedButton() == vcoCli.INT_BUT_ACE)
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
                case 2: //Búsqueda directa por "Descripción larga".
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
                        if (vcoCli.getSelectedButton() == vcoCli.INT_BUT_ACE)
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
        catch (Exception e) {    blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada
     * para mostrar las "Cuentas".
     */
    private boolean configurarVenConCta() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a2.tx_ubiCta");
            arlCam.add("a1.st_aut");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Ubicación");
            arlAli.add("Autorización");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 5;
            vcoCta = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
        } 
        catch (Exception e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una búsqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se está buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCta(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            //Validar que sólo se pueda seleccionar la "Cuenta" si se seleccionó el "Tipo de documento".
            if (txtCodTipDoc.getText().equals("")) 
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtNomCta.setText("");
                strUbiCta = "";
                strAutCta = "";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else 
            {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut";
                strSQL += " FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL += " AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL += " ORDER BY a1.tx_codCta";
                vcoCta.setSentenciaSQL(strSQL);
                switch (intTipBus) 
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtNomCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                            regenerarDiario();
                            ///actualizarGlo();
                            txtNumAltUno.selectAll();
                            txtNumAltUno.requestFocus();
                        }
                        break;
                    case 1: //Búsqueda directa por "Número de cuenta".
                        if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText())) 
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtNomCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                            regenerarDiario();
                            ///actualizarGlo();
                            txtNumAltUno.selectAll();
                            txtNumAltUno.requestFocus();
                        } 
                        else
                        {
                            vcoCta.setCampoBusqueda(0);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) 
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtNomCta.setText(vcoCta.getValueAt(3));
                                strUbiCta = vcoCta.getValueAt(4);
                                strAutCta = vcoCta.getValueAt(5);
                                regenerarDiario();
                                ///actualizarGlo();
                                txtNumAltUno.selectAll();
                                txtNumAltUno.requestFocus();
                            }
                            else
                            {
                                txtDesCorCta.setText(strDesCorCta);
                            }
                        }
                        break;
                    case 2: //Búsqueda directa por "Descripción larga".
                        if (vcoCta.buscar("a1.tx_desLar", txtNomCta.getText())) 
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtNomCta.setText(vcoCta.getValueAt(3));
                            strUbiCta = vcoCta.getValueAt(4);
                            strAutCta = vcoCta.getValueAt(5);
                            regenerarDiario();
                            ///actualizarGlo();
                            txtNumAltUno.selectAll();
                            txtNumAltUno.requestFocus();
                        }
                        else 
                        {
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtNomCta.setText(vcoCta.getValueAt(3));
                                strUbiCta = vcoCta.getValueAt(4);
                                strAutCta = vcoCta.getValueAt(5);
                                regenerarDiario();
                                ///actualizarGlo();
                                txtNumAltUno.selectAll();
                                txtNumAltUno.requestFocus();
                            } 
                            else 
                            {
                                txtNomCta.setText(strDesLarCta);
                            }
                        }
                        break;
                }
            }
        }
        catch (Exception e) {       blnRes = false;        objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
  

    private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta)) {
            if (txtDesCorCta.getText().equals("")) {
                txtCodCta.setText("");
                txtNomCta.setText("");
            } else {
                mostrarVenConCta(1);
            }
        } else {
            txtDesCorCta.setText(strDesCorCta);
        }
    }//GEN-LAST:event_txtDesCorCtaFocusLost

    private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
        strDesCorCta = txtDesCorCta.getText();
        txtDesCorCta.selectAll();
    }//GEN-LAST:event_txtDesCorCtaFocusGained

    private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
        txtDesCorCta.transferFocus();
    }//GEN-LAST:event_txtDesCorCtaActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtCodCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaActionPerformed

    }//GEN-LAST:event_txtCodCtaActionPerformed

    private void txtCodCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCtaFocusLost
        Librerias.ZafConsulta.ZafConsulta objFndCta
                = new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
                        "Codigo,Descripcion,Codigo Cuenta", "tbm_placta.co_cta,tbm_placta.tx_deslar,tbm_placta.tx_codcta",
                        "select co_cta,tx_deslar,tx_codcta from tbm_placta where co_emp = " + objParSis.getCodigoEmpresa(), txtCodCta.getText(),
                        objParSis.getStringConexion(),
                        objParSis.getUsuarioBaseDatos(),
                        objParSis.getClaveBaseDatos()
                );

        objFndCta.setTitle("Listado de Cuentas");

        if (!txtCodCta.getText().equals("")) {
            if (objUti.isNumero(txtCodCta.getText())) {
                if (objFndCta.buscar("co_cta = " + txtCodCta.getText())) {
                    txtCodCta.setText(objFndCta.GetCamSel(1));
                    txtNomCta.setText(objFndCta.GetCamSel(2));
                } else {
                    txtCodCta.setText("");
                    txtNomCta.setText("");
                }
            } else {
                txtCodCta.setText("");
                txtNomCta.setText("");
            }
        }
    }//GEN-LAST:event_txtCodCtaFocusLost

    
    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtNomCta.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost


    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            } else {
                mostrarVenConCli(1);
            }
        } else {
            txtCodCli.setText(strCodCli);
        }


    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

     private void txtNumAltUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumAltUnoActionPerformed
         // TODO add your handling code here:
     }//GEN-LAST:event_txtNumAltUnoActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        if (txtCodCli.getText().equals("")) {
            txtMonDoc.setText("");
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
    }//GEN-LAST:event_butCtaActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try 
        {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                //Cerrar la conexión si está abierta.
                if (rstCab != null) 
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e) {        dispose();      }
    }//GEN-LAST:event_exitForm

    private void txtNumSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumSerFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumSerFocusGained

    private void txtNumSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumSerFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumSerFocusLost

    private void txtNumSecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumSecFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumSecFocusGained

    private void txtNumSecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumSecFocusLost
        String strNum=txtNumSec.getText();
                    if (strNum.length() > 0 && strNum.length() != 9) {
                            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Secuencia</FONT> debe tener 9 digitos.<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                            txtNumSec.setFocusable(true);
                            txtNumSec.setText("");
                    }
    }//GEN-LAST:event_txtNumSecFocusLost

    private void txtNumAutFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumAutFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumAutFocusGained

    private void txtNumAutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumAutFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumAutFocusLost

   private void txtSubIvaCerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSubIvaCerFocusGained
      // TODO add your handling code here:
   }//GEN-LAST:event_txtSubIvaCerFocusGained

   private void txtSubIvaCerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSubIvaCerFocusLost
      calcularTotDoc_FocusLost();
   }//GEN-LAST:event_txtSubIvaCerFocusLost

    /**
     * Cerrar la aplicación.
     */
    private void exitForm() {
        dispose();
    }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.ButtonGroup bgrNatCta;
   private javax.swing.ButtonGroup bgrTipCta;
   private javax.swing.JButton butCli;
   private javax.swing.JButton butCta;
   private javax.swing.JButton butImp;
   private javax.swing.JButton butTipDoc;
   private javax.swing.JButton butVisPre;
   private javax.swing.JCheckBox chkIva;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JLabel lblCli;
   private javax.swing.JLabel lblCodDoc;
   private javax.swing.JLabel lblCta;
   private javax.swing.JLabel lblFecDoc;
   private javax.swing.JLabel lblMonDoc;
   private javax.swing.JLabel lblNumAltDos;
   private javax.swing.JLabel lblNumAltUno;
   private javax.swing.JLabel lblNumAut;
   private javax.swing.JLabel lblNumSec;
   private javax.swing.JLabel lblNumSer;
   private javax.swing.JLabel lblObs1;
   private javax.swing.JLabel lblObs2;
   private javax.swing.JLabel lblSubIvaCer;
   private javax.swing.JLabel lblTipDoc;
   private javax.swing.JLabel lblTit;
   private javax.swing.JLabel lblTot;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panCabDoc;
   private javax.swing.JPanel panDat;
   private javax.swing.JPanel panDia;
   private javax.swing.JPanel panFrm;
   private javax.swing.JPanel panLbl;
   private javax.swing.JPanel panObs;
   private javax.swing.JPanel panTxa;
   private javax.swing.JScrollPane spnObs;
   private javax.swing.JScrollPane spnObs1;
   private javax.swing.JScrollPane spnObs2;
   private javax.swing.JTabbedPane tabFrm;
   private javax.swing.JTable tblFacPrv;
   private javax.swing.JTextArea txaObs1;
   private javax.swing.JTextArea txaObs2;
   private javax.swing.JTextField txtCodCli;
   private javax.swing.JTextField txtCodCta;
   private javax.swing.JTextField txtCodDoc;
   private javax.swing.JTextField txtCodTipDoc;
   private javax.swing.JTextField txtDesCorCta;
   private javax.swing.JTextField txtDesCorTipDoc;
   private javax.swing.JTextField txtDesLarTipDoc;
   private javax.swing.JTextField txtIva;
   private javax.swing.JTextField txtMonDoc;
   private javax.swing.JTextField txtNomCli;
   private javax.swing.JTextField txtNomCta;
   private javax.swing.JTextField txtNumAltDos;
   private javax.swing.JTextField txtNumAltUno;
   private javax.swing.JTextField txtNumAut;
   private javax.swing.JTextField txtNumCta;
   private javax.swing.JTextField txtNumSec;
   private javax.swing.JTextField txtNumSer;
   private javax.swing.JTextField txtSubIvaCer;
   private javax.swing.JTextField txtTot;
   // End of variables declaration//GEN-END:variables

    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        intCodMnu = objParSis.getCodigoMenu();
        this.setTitle(objParSis.getNombreMenu() + strVersion);
        if (intCodMnu == 296) {
            lblTit.setText(objParSis.getNombreMenu() + " (CTAS X COBRAR)");
        }

        if (intCodMnu == 306) {
            lblTit.setText(objParSis.getNombreMenu() + " (CTAS X COBRAR)");
        }

        if (intCodMnu == 1414) {
            lblTit.setText(objParSis.getNombreMenu() + " (CTAS X PAGAR)");
        }

        if (intCodMnu == 1424) {
            lblTit.setText(objParSis.getNombreMenu() + " (CTAS X PAGAR)");
        }

        txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtCodCta.setBackground(objParSis.getColorCamposObligatorios());
        txtNomCta.setBackground(objParSis.getColorCamposObligatorios());
        txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
        txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
        txtCodDoc.setBackground(objParSis.getColorCamposSistema());
        txtNumAltUno.setBackground(objParSis.getColorCamposObligatorios());
        txtFecDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtDesCorCta.setBackground(objParSis.getColorCamposObligatorios());
        txtMonDoc.setBackground(objParSis.getColorCamposObligatorios());
        txtNumSer.setBackground(objParSis.getColorCamposObligatorios());
        txtNumSec.setBackground(objParSis.getColorCamposObligatorios());
        txtNumAut.setBackground(objParSis.getColorCamposObligatorios());

        configurarVenConTipDoc();
        configurarVenConCli();
        configurarVenConCta();

        //para generar el reporte.
        objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

        txtIva.setEditable(false);
        txtTot.setEditable(false);
        return blnRes;
    }

    private boolean isRegPro() {
        boolean blnRes = true;
//        strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
//        strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
//        switch (mostrarMsgCon(strAux))
//        {
//            case 0: //YES_OPTION
//                switch (objTooBar.getEstado()) {
//                    case 'n': //Insertar
//                        blnRes = objTooBar.insertar();
//                        break;
//                    case 'm': //Modificar
//                        blnRes = objTooBar.modificar();
//                        break;
//                }
//                break;
//            case 1: //NO_OPTION
//                blnHayCam = false;
//                blnRes = true;
//                break;
//            case 2: //CANCEL_OPTION
//                blnRes = false;
//                break;
//        }
        return blnRes;
    }

    private boolean mostrarCtaPre()
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                strSQL += " FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL += " AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL += " AND a2.st_reg='S'";
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodCta.setText(rst.getString("co_cta"));
                    txtDesCorCta.setText(rst.getString("tx_codCta"));
                    txtNomCta.setText(rst.getString("tx_desLar"));
                    strUbiCta = rst.getString("tx_ubiCta");
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
            }
        } 
        catch (java.sql.SQLException e) {       blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    private boolean mostrarTipDocPre() 
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                //Armar la sentencia SQL.//
                strSQL = "";
                strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_tipDoc=";
                strSQL += " (";
                strSQL += " SELECT co_tipDoc";
                strSQL += " FROM tbr_tipDocPrg";
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND st_reg='S'";
                strSQL += " )";
                //System.out.println("---CxC10---mostrarTipDocPre: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumAltUno.setText("" + (rst.getInt("ne_ultDoc") + 1));
                    intSig = (rst.getString("tx_natDoc").equals("I") ? 1 : -1);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
            }
        } 
        catch (java.sql.SQLException e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private boolean insertarReg() 
    {
        boolean blnRes = false;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
               //System.out.println("insertarReg ");
                if (insertarCabMovInv()) {
                    if (insertarPagMovInv()) {
                        if (insertarDetRegNotDeb()) {
                            if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"))) {
                            if (objUti.set_ne_ultDoc_tbm_cabTipDoc(this, con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtNumAltUno.getText()))) {
                                con.commit();
                                blnRes = true;
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                        }else{
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }

                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean anularCab() {
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_cabPag";
                strSQL += " SET st_reg='I'";
                strSQL += ", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL += ", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } 
        catch (java.sql.SQLException e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);     } 
        catch (Exception e) {       blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private boolean anularCabMovInv() 
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) 
            {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_cabMovInv";
                strSQL += " SET st_reg='I'";
                strSQL += ", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL += ", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL += " AND co_doc=" + txtCodDoc.getText() + "";
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        }
        catch (java.sql.SQLException e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      } 
        catch (Exception e) {          blnRes = false;          objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    private boolean anularPagMovInv() 
    {
        boolean blnRes = true;
        try 
        {
            if (con != null)
            {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_pagMovInv";
                strSQL += " SET st_reg='I'";
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL += " AND co_doc=" + txtCodDoc.getText() + "";
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } 
        catch (java.sql.SQLException e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);       } 
        catch (Exception e) {           blnRes = false;          objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    private boolean anularReg() 
    {
        boolean blnRes = false;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) 
            {
                if (consultarPagAso()) {
                    if (VALPAGASO == 0) {
                        if (anularCabMovInv()) {
                            if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) {
                                con.commit();
                                blnRes = true;
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                    } else {
                        mostrarMsgInf("El documento posee un PAGO ASOCIADO.\nNo es posible ELIMINAR un documento con Abono asociado.");
                    }
                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } 
        catch (java.sql.SQLException e) {        objUti.mostrarMsgErr_F1(this, e);     } 
        catch (Exception e) {        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private boolean eliminarDet()
    {
        boolean blnRes = true;
        try
        {
            if (con != null) 
            {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "DELETE FROM tbm_detPag";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                //System.out.println("en eliminarDet:" + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);        }
        catch (Exception e) {            blnRes = false;            objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    private boolean eliminarReg() 
    {
        boolean blnRes = false;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (consultarPagAso()) {
                    if (VALPAGASO == 0) {
                        if (eliminarPagMovInv()) {
                            if (eliminarCabMovInv()) {
                                if (objAsiDia.eliminarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))) {
                                    con.commit();
                                    blnRes = true;
                                } else {
                                    con.rollback();
                                }
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                    } else {
                        mostrarMsgInf("El documento posee un PAGO ASOCIADO.\nNo es posible ELIMINAR un documento con Abono asociado.");
                    }
                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean actualizarCabMovInv()
    {
        boolean blnRes = true;
        BigDecimal bdeSub;
        
        try 
        {
            if (con != null) 
            {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_cabmovinv";
                strSQL += " SET co_cli=" + txtCodCli.getText() + ",";
                strSQL += " tx_nomcli='" + txtNomCli.getText() + "',";
                strSQL += " ne_numdoc=" + txtNumAltUno.getText() + ",";
                
                if (txtSubIvaCer.getText().trim().equals(""))
                {
                   txtSubIvaCer.setText("0");
                }
                
                if (txtMonDoc.getText().trim().equals(""))
                {
                   txtMonDoc.setText("0");
                }
                
                if (objParSis.getCodigoMenu() == 1424) {
                    strSQL += " nd_sub=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3) + ", ";
                    strSQL += " nd_valiva=" + objUti.codificar((objUti.isNumero(txtIva.getText()) ? "" + (intSig * Double.parseDouble(txtIva.getText())) : "0"), 3) + ", ";
                    strSQL += " nd_tot=" + objUti.codificar((objUti.isNumero(txtTot.getText()) ? "" + (intSig * Double.parseDouble(txtTot.getText())) : "0"), 3) + ", ";
                    if (chkIva.isSelected()) 
                    {   //strSQL += " nd_poriva=" +(dblPorIva / 100)+ ", "; //Se comento esta linea en la version v5.3 (Dennis Betancourt - 20/Sep/2018)
                        strSQL += " nd_poriva=" + dblPorIva + ", ";
                    }
                    else{
                        strSQL += " nd_poriva=0.00 , ";
                    }
                }
                else
                {
                    //strSQL += " nd_sub=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3) + ", ";
                    bdeSub = (new BigDecimal(txtSubIvaCer.getText())).add(new BigDecimal(txtMonDoc.getText()));
                    strSQL += " nd_sub=" + bdeSub;
                }
                strSQL += " co_cta=" + txtCodCta.getText() + ",";
                strSQL += " tx_obs1=" + objUti.codificar(txaObs1.getText()) + ",";
                strSQL += " tx_obs2=" + objUti.codificar(txaObs2.getText()) + ",";
                strSQL += " fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "', ";
                strSQL += " co_usrMod=" + objParSis.getCodigoUsuario() + ",";
                strSQL += " nd_subIvaCer = " + new BigDecimal(txtSubIvaCer.getText()) + ","; //nd_subIvaCer
                strSQL += " nd_subIvaGra = " + new BigDecimal(txtMonDoc.getText()); //nd_subIvaGra
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL += " AND co_doc=" + txtCodDoc.getText() + "";
                //System.out.println("---el update de cabmovinv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        }
        catch (java.sql.SQLException e) {        blnRes = false;         objUti.mostrarMsgErr_F1(this, e);      } 
        catch (Exception e) {          blnRes = false;          objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    private boolean actualizarPagMovInv() 
    {
        boolean blnRes = true;
        try
        {
            if (con != null)
            {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_pagmovinv";
                if (objParSis.getCodigoMenu() == 1424) {
                    strSQL += " SET mo_pag=" + objUti.codificar((objUti.isNumero(txtTot.getText()) ? "" + (intSig * Double.parseDouble(txtTot.getText())) : "0"), 3) + ", ";
                    strSQL += " tx_numchq=" + objUti.codificar(txtNumSec.getText())+ ", ";
                    strSQL += " tx_numser=" + objUti.codificar(txtNumSer.getText()) + ", ";
                    strSQL += " tx_numautsri=" + objUti.codificar(txtNumAut.getText());
                }else{
                    strSQL += " SET mo_pag=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3);
                }
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL += " AND co_doc=" + txtCodDoc.getText() + "";
                //System.out.println("el update de pagmovinv:" + strSQL);
                //System.out.println("paso los updates");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        }
        catch (java.sql.SQLException e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      } 
        catch (Exception e) {          blnRes = false;          objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de
     * herramientas contiene los botones que realizan las diferentes operaciones
     * del sistema. Es decir, insertar, consultar, modificar, eliminar, etc.
     * Ademï¿½s de los botones de navegaciï¿½n que permiten desplazarse al
     * primero, anterior, siguiente y ï¿½ltimo registro.
     */
    private class MiToolBar extends ZafToolBar {

        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
            super(ifrFrm, objParSis);
        }

        public boolean anular() {
            if (!anularReg()) {
                return false;
            }
            if (!anularDetNotDeb()) {
                return false;
            }
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam = false;
            return true;
        }

        public void clickAceptar() {
        }

        public void clickAnterior() {
            try {
                if (!rstCab.isFirst()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            rstCab.previous();
                            cargarReg();
                        }
                    } else {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }

        }

        public void clickAnular() {
        }

        public void clickCancelar() {
        }

        public void clickConsultar() {
            butImp.setEnabled(true);
            butVisPre.setEnabled(true);
        }

        public void clickEliminar() {
            txtCodDoc.setEnabled(true);
            txtCodDoc.requestFocus();
        }

        public void clickFin() {
            try {
                if (!rstCab.isLast()) {
                    if (blnHayCam || objAsiDia.isDiarioModificado()) {
                        if (isRegPro()) {
                            rstCab.last();
                            cargarReg();
                        }
                    } else {
                        rstCab.last();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir() {
        }

        public void clickInicio() {
            try {
                if (!rstCab.isFirst()) {
                    if (blnHayCam || objAsiDia.isDiarioModificado()) {
                        if (isRegPro()) {
                            rstCab.first();
                            cargarReg();
                        }
                    } else {
                        rstCab.first();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }

        }

        public void clickInsertar() {
            objTooBar.setEnabledVistaPreliminar(true);
            objTooBar.setEnabledImprimir(true);
            try {
                if (blnHayCam) {
                    isRegPro();
                }
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                limpiarFrm();
                txtCodDoc.setEditable(false);
                txtMonDoc.setEditable(true);

                butImp.setEnabled(true);
                butVisPre.setEnabled(true);

                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                txtFecDoc.setText(objUti.formatearFecha(datFecAux, "dd/MM/yyyy"));
                datFecAux = null;
                mostrarTipDocPre();
                if (!txtCodTipDoc.getText().equals("")) {
                    mostrarCtaPre();
                }
                objAsiDia.setEditable(true);
                txtNumAltUno.selectAll();
                txtNumAltUno.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);

                ///Para no editar los valores deliva y total///
                txtIva.setEnabled(false);
                txtTot.setEnabled(false);
                
                objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar() {
            txtCodTipDoc.setEnabled(false);
            txtDesCorTipDoc.setEnabled(false);
            butTipDoc.setEnabled(false);
            txtDesLarTipDoc.setEnabled(false);
            txtCodCta.setEnabled(false);
            txtCodDoc.setEnabled(false);
            txtFecDoc.setEnabled(false);
            txtCodCta.selectAll();
            txtCodCta.requestFocus();
            objAsiDia.setEditable(true);

            butImp.setEnabled(true);
            butVisPre.setEnabled(true);
            objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
        }

        public void clickSiguiente() {
            try {
                if (!rstCab.isLast()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            rstCab.next();
                            cargarReg();
                        }
                    } else {
                        rstCab.next();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickVisPreliminar() {
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean eliminar() {
            try {
                //System.out.println("estoy en eliminar");
                if (!eliminarReg()) {
                    //System.out.println("pase eliminar reg");
                    return false;
                }
                
                if (!eliminarDetNotDeb()) {
                    return false;
                }
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) {
                    rstCab.next();
                    //System.out.println("la siguiente linea llama a cargarReg");
                    cargarReg();
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                return true;
            }
            return true;
        }

        public boolean insertar() {
            if (!insertarReg()) {
                return false;
            }

            return true;

        }

        public boolean modificar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }

            if (!isCamVal()) {
                return false;
            }

            boolean blnRes = false;
            try {
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                if (con != null) {
                    if (actualizarCabMovInv()) {
                        if (actualizarPagMovInv()) {
                            if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumAltUno.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy"), "A")) {
                                con.commit();
                                blnRes = true;
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }
                }
                con.close();
                con = null;
                objTooBar.setEstado('w');
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        //***********************************************************************************
        public boolean vistaPreliminar()
        {
            if (objThrGUI == null) 
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        //***********************************************************************************
        public boolean afterVistaPreliminar()
        {
            return true;
        }
        
        //***********************************************************************************
        public boolean imprimir() 
        {
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            objAsiDia.setDiarioModificado(false);
            blnHayCam = false;
            consultarReg();

            return true;
        }

        public boolean afterModificar() {
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
            boolean blnRes = true;
            try {
                if (blnHayCam || objAsiDia.isDiarioModificado()) {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') {
                        if (!isRegPro()) {
                            return false;
                        }
                    }
                }
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam = false;
            return blnRes;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean beforeInsertar() {
            if (!isCamVal()) {
                return false;
            }
            if (objAsiDia.getGeneracionDiario() == 2) {
                return regenerarDiario();
            }
            return true;

        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeEliminar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento ya estï¿½ ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }
    }

    private boolean isCamVal() {
        String strAux;
        
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba un tipo de documento y vuelva a intentarlo.</HTML>");
            txtCodTipDoc.requestFocus();
            return false;
        }

        if (txtDesLarTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento para la cuenta y vuelva a intentarlo.</HTML>");
            txtDesLarTipDoc.requestFocus();
            return false;
        }

        if (txtCodCta.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Código de Cuenta</FONT> es obligatorio.<BR>Escriba un Código de Cuenta y vuelva a intentarlo.</HTML>");
            txtCodCta.requestFocus();
            return false;
        }

        if (txtNomCta.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Cuenta</FONT> es obligatorio.<BR>Escriba un Nombre de Cuenta y vuelva a intentarlo.</HTML>");
            txtNomCta.requestFocus();
            return false;
        }

        if (txtCodCli.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Código de Cliente</FONT> es obligatorio.<BR>Escriba un Código de Cliente y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }

        if (txtNomCli.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Cliente</FONT> es obligatorio.<BR>Escriba un Nombre de Cliente y vuelva a intentarlo.</HTML>");
            txtNomCli.requestFocus();
            return false;
        }

        if (txtNumAltUno.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Nota de Crï¿½dito</FONT> es obligatorio.<BR>Escriba un Número de Nota de Crï¿½dito y vuelva a intentarlo.</HTML>");
            txtNumAltUno.requestFocus();
            return false;
        }

        if (txtSubIvaCer.getText().equals("") && txtMonDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            strAux =  "<HTML>El campo <FONT COLOR=\"blue\">Subtotal Base 0</FONT> o <FONT COLOR=\"blue\">Subtotal IVA</FONT> es obligatorio.<BR>";
            strAux += "Escriba un valor por lo menos en alguno de estos campos y vuelva a intentarlo.</HTML>";
            mostrarMsgInf(strAux);
            txtMonDoc.requestFocus();
            return false;
        }
        if (objParSis.getCodigoMenu() == 1424) {
            if (txtNumSer.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de serie</FONT> es obligatorio.<BR>Escriba un Número de serie y vuelva a intentarlo.</HTML>");
            txtNumSer.requestFocus();
            return false;
            } 
            if (txtNumSec.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de secuencia</FONT> es obligatorio.<BR>Escriba un Número de secuencia y vuelva a intentarlo.</HTML>");
            txtNumSec.requestFocus();
            return false;
            }  
            if (txtNumAut.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de autorización</FONT> es obligatorio.<BR>Escriba un Número de autorización y vuelva a intentarlo.</HTML>");
            txtNumAut.requestFocus();
            return false;
            } 
            Double total=Double.valueOf(txtTot.getText());
            Double factura=0.00;
            if (tblFacPrv != null && tblFacPrv.getRowCount() > 0) {
                    for (int i = 0; i < tblFacPrv.getRowCount(); i++) {
                        if (objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC) != null) {
                             factura = Double.valueOf(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString());
                        }
                    }
                }
            if (total>factura) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Total</FONT> no debe ser mayor que el valor de la factura<BR>Ingrese un valor menor o igual y vuelva a intentarlo.</HTML>");
                txtMonDoc.requestFocus();
                txtMonDoc.setText("");
                calculaTotal();
            }
            if (total<=0.00) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Monto</FONT> no debe ser menor o igual a cero<BR>Ingrese un valor y vuelva a intentarlo.</HTML>");
                txtMonDoc.requestFocus();
                txtMonDoc.setText("");
                calculaTotal();
            }
        }
        //Validar que el "Diario está cuadrado".//
        if (!objAsiDia.isDiarioCuadrado())
        {
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "EL diario no esta cuadrado, no se puede grabar";
            obj.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        ////Configurar ventana cuando sea Nota de Credito o Debito para Proveedor///
        if (objParSis.getCodigoMenu() == 296 || objParSis.getCodigoMenu() == 306 || objParSis.getCodigoMenu() == 1414) {
            //Validar que el "Monto del diario" sea igual al monto del documento.
            if (!objAsiDia.isDocumentoCuadrado(txtMonDoc.getText())) {
                javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                String strTit, strMsg;
                strTit = "Mensaje del sistema Zafiro";
                strMsg = "El valor del documento no coincide con el valor del asiento de diario. Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.";
                obj.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } else {
            //Validar que el "Monto del diario" sea igual al monto del documento.
            if (!objAsiDia.isDocumentoCuadrado(txtTot.getText())) {
                javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                String strTit, strMsg;
                strTit = "Mensaje del sistema Zafiro";
                strMsg = "El valor del documento no coincide con el valor del asiento de diario. Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.";
                obj.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }

        //Validar que el "Código alterno" no se repita.
        if (!txtNumAltUno.getText().equals("")) {
            strSQL = "";
            strSQL += "SELECT a1.ne_numdoc";
            strSQL += " FROM tbm_cabMovInv AS a1 ";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL += " AND a1.ne_numdoc='" + txtNumAltUno.getText().replaceAll("'", "''") + "'";
            strSQL += " AND a1.st_reg<>'E'";
            if (objTooBar.getEstado() == 'm') {
                strSQL += " AND a1.co_doc<>" + txtCodDoc.getText();
            }
            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El número de documento <FONT COLOR=\"blue\">" + txtNumAltUno.getText() + "</FONT> ya existe.<BR>Escriba un número de documento diferente y vuelva a intentarlo.</HTML>");
                txtNumAltUno.selectAll();
                txtNumAltUno.requestFocus();
                return false;
            }
        }

        return true;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podrï¿½a
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta funciï¿½n muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta funciï¿½n muestra un mensaje de advertencia al usuario. Se podrï¿½a
     * utilizar para mostrar al usuario un mensaje que indique que los datos se
     * han cargado con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifï¿½quelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    private boolean insertarCab() {
        int intCodUsr, intUltReg;
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                intCodUsr = objParSis.getCodigoUsuario();
                //Obtener el cï¿½digo del ï¿½ltimo registro.
                strSQL = "";
                strSQL += "SELECT MAX(a1.co_doc)";
                strSQL += " FROM tbm_cabPag AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg == -1) {
                    return false;
                }
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);

                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, fe_ven, ne_numDoc1, ne_numDoc2, co_cta, co_cli";
                strSQL += ", tx_ruc, tx_nomCli, tx_dirCli, nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                strSQL += " VALUES (";
                strSQL += objParSis.getCodigoEmpresa();
                strSQL += ", " + objParSis.getCodigoLocal();
                strSQL += ", " + txtCodTipDoc.getText();
                strSQL += ", " + intUltReg;
                strSQL += ", '" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL += ", '" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL += ", " + objUti.codificar(txtNumAltUno.getText(), 2);
                strSQL += ", " + objUti.codificar(txtNumAltDos.getText(), 2);
                strSQL += ", " + txtCodCta.getText();
                strSQL += ", " + objUti.codificar(txtCodCli.getText(), 2);
                strSQL += ", " + objUti.codificar(strIdeCli);
                strSQL += ", " + objUti.codificar(txtNomCli.getText());
                strSQL += ", " + objUti.codificar(strDirCli);
                strSQL += ", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3);
                strSQL += ", " + objUti.codificar(txaObs1.getText());
                strSQL += ", " + objUti.codificar(txaObs2.getText());
                strSQL += ", 'A'";
                strAux = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL += ", '" + strAux + "'";
                strSQL += ", '" + strAux + "'";
                strSQL += ", " + intCodUsr;
                strSQL += ", " + intCodUsr;
                strSQL += ")";
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean insertarCabMovInv() {
        int  intUltReg, intUltOrd;
        boolean blnRes = true;
        int j = 1;
        BigDecimal bdeSub;
        
        try {
            if (con != null) {

                //System.out.println("insertarCabMovInv ");
                stm = con.createStatement();

                //Obtener el codigo del último registro.
                strSQL = "";
                strSQL += "SELECT MAX(a1.co_doc)";
                strSQL += " FROM tbm_cabmovinv AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg == -1) {
                    return false;
                }
                intUltReg++;

                strSQL = "";
                strSQL += "SELECT MAX(a1.ne_numDoc)";
                strSQL += " FROM tbm_cabmovinv AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltOrd = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                //System.out.println("EL ULTIMO REGISTRO ES:" + intUltOrd);
                if (intUltOrd == -1) {
                    return false;
                }
                intUltOrd++;

                txtCodDoc.setText("" + intUltReg);

                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbm_cabmovinv(co_emp, co_loc, co_tipDoc, co_doc, ne_numCot, ne_numdoc, tx_numped, ne_numgui, fe_doc, co_cli, tx_ruc, tx_nomcli, tx_dircli,tx_telcli";
                strSQL += ", tx_ciucli, co_com, tx_nomven, tx_ate, co_forpag, tx_desforpag, nd_sub, nd_tot,nd_valiva, nd_poriva, tx_ptopar, tx_tra, co_mottra, tx_desmottra, co_cta, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, co_mnu, nd_subIvaCer, nd_subIvaGra)";
                strSQL += " VALUES (";
                strSQL += objParSis.getCodigoEmpresa();  //co_emp
                strSQL += ", " + objParSis.getCodigoLocal();  //co_loc
                strSQL += ", " + txtCodTipDoc.getText(); //co_tipDOc
                strSQL += ", " + intUltReg; //co_doc
                //strSQL+=", " + intUltOrd;
                strSQL += ", Null";  //ne_numCot
                //strSQL+=", Null";
                strSQL += ", " + txtNumAltUno.getText(); //ne_numDoc
                strSQL += ", Null";//tx_numPed
                ////strSQL+=", " + objUti.codificar(txtNumAltDos.getText());
                strSQL += ", Null"; //ne_numGui
                strSQL += ", '" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL += ", " + objUti.codificar(txtCodCli.getText(), 2);  //co_cli
                strSQL += ", Null";  //tx_ruc
                strSQL += ", " + objUti.codificar(txtNomCli.getText()); //tx_nomCli
                strSQL += ", Null";//tx_dirCli
                strSQL += ", Null";//tx_celCli
                strSQL += ", Null";//tx_ciuCli
                strSQL += ", Null";//co_com
                strSQL += ", Null";//tx_nomVen
                strSQL += ", Null";//tx_tx_ate
                strSQL += ", 1"; //co_forPag  // José Marín 04/Jul/2014
                strSQL += ", Null";//tx_desforpag
                
                if (txtSubIvaCer.getText().trim().equals(""))
                {
                   txtSubIvaCer.setText("0");
                }
                
                if (txtMonDoc.getText().trim().equals(""))
                {
                   txtMonDoc.setText("0");
                }
                
                if (objParSis.getCodigoMenu() == 1424)
                {
                //strSQL += ", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3);//nd_sub
                bdeSub = (new BigDecimal(txtSubIvaCer.getText())).add(new BigDecimal(txtMonDoc.getText()));
                strSQL += ", " + bdeSub;//nd_sub
                strSQL += ", " + objUti.codificar((objUti.isNumero(txtTot.getText()) ? "" + (intSig * Double.parseDouble(txtTot.getText())) : "0"), 3);//nd_tot
                strSQL += ", " + objUti.codificar((objUti.isNumero(txtIva.getText()) ? "" + (intSig * Double.parseDouble(txtIva.getText())) : "0"), 3);//nd_valiva
                if (chkIva.isSelected()) 
                {  //strSQL += ", "+(dblPorIva / 100);//nd_poriva. Se comento esta linea en la version v5.3 (Dennis Betancourt - 20/Sep/2018)
                   strSQL += ", " + dblPorIva; //nd_poriva
                } else {
                   strSQL += ", 0.0";//nd_poriva
                }
                }
                else
                {
                strSQL += ", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3);//nd_sub
                strSQL += ", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3);//nd_tot
                strSQL += ", null" ;//nd_valiva
                strSQL += ", Null";//nd_poriva
                }
                strSQL += ", Null";//tx_ptopar
                strSQL += ", Null";//tx_tra
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", " + txtCodCta.getText();
                strSQL += ", " + objUti.codificar(txaObs1.getText());
                strSQL += ", " + objUti.codificar(txaObs2.getText());
                strSQL += ", 'A'";
                strSQL += ", '" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL += ", '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL += ", " + objParSis.getCodigoUsuario();
                strSQL += ", " + objParSis.getCodigoUsuario();
                strSQL += ", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL += "," + new BigDecimal(txtSubIvaCer.getText()) + ""; //nd_subIvaCer
                strSQL += "," + new BigDecimal(txtMonDoc.getText()) + ""; //nd_subIvaGra
                strSQL += ")";
                //System.out.println("---FUNCION INSERTAR Tbm_Cagmovinv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean insertarPagMovInv() 
    {
        int intCodUsr, intUltReg;
        boolean blnRes = true;
        int j = 1;
        int intCodRet = 0;
        double dblValRet = 0.00;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                intCodUsr = objParSis.getCodigoUsuario();

                //Obtener el codigo del ï¿½ltimo registro.
                strSQL = "";
                strSQL += "SELECT MAX(a1.co_doc)";
                strSQL += " FROM tbm_pagmovinv AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg == -1) {
                    return false;
                }
                intUltReg++;

                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbm_pagmovinv(co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_diacre, fe_ven, co_tipret, nd_porret, mo_pag, ne_diagra";
                strSQL += ", nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq,tx_numser,tx_numautsri, fe_recchq, fe_venchq, nd_monchq, co_prochq, st_reg)";
                strSQL += " VALUES (";
                strSQL += objParSis.getCodigoEmpresa();
                strSQL += ", " + objParSis.getCodigoLocal();
                strSQL += ", " + txtCodTipDoc.getText();
                /////strSQL+=", " + txtCodDoc.getText();
                strSQL += ", " + intUltReg;
                strSQL += ", " + j;
                strSQL += ", " + new Integer(0);
                strSQL += ", '" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                ///strSQL+=", null" ; ///original///
                ///strSQL+=", null" ; ///original///
                strSQL += ", " + intCodRet; ///prueba///
                strSQL += ", " + dblValRet; ///prueba///
                if (objParSis.getCodigoMenu() == 1424) {
                strSQL += ", " + objUti.codificar((objUti.isNumero(txtTot.getText()) ? "" + (intSig * Double.parseDouble(txtTot.getText())) : "0"), 3);
                }else{
                strSQL += ", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText()) ? "" + (intSig * Double.parseDouble(txtMonDoc.getText())) : "0"), 3);
                }
                strSQL += ", " + new Integer(0);
                strSQL += ", " + new Integer(0);
                strSQL += ", 'N'";
                strSQL += ", 'N'";
                strSQL += ", 'N'";//st_pos
                strSQL += ", Null";//co_banchq
                strSQL += ", Null";//tx_numctachq
                if (objParSis.getCodigoMenu() == 1424) {
                strSQL += ", " + objUti.codificar(txtNumSec.getText());//tx_numchq
                strSQL += ", " + objUti.codificar(txtNumSer.getText());//tx_numser
                strSQL += ", " + objUti.codificar(txtNumAut.getText());//tx_numautsri
                }else{
                strSQL += ", Null";//tx_numchq
                strSQL += ", Null";//tx_numser
                strSQL += ", Null";//tx_numautsri
                }
                strSQL += ", Null";//fe_recchq
                strSQL += ", Null";//fe_venchq
                strSQL += ", Null";//nd_monchq
                strSQL += ", Null";//co_prochq
                strSQL += ", 'A'";//st_reg
                strSQL += ")";
                //System.out.println("---FUNCION INSERTAR Tbm_Pagmovinv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } 
        catch (java.sql.SQLException e) {         blnRes = false;          objUti.mostrarMsgErr_F1(this, e);      } 
        catch (Exception e) {          blnRes = false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }

    private boolean insertarDet() 
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc;
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                intCodEmp = objParSis.getCodigoEmpresa();
                intCodLoc = objParSis.getCodigoLocal();
                strCodTipDoc = txtCodTipDoc.getText();
                strCodDoc = txtCodDoc.getText();
                j = 1;

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                strSQL += ", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                strSQL += " VALUES (";
                strSQL += "" + intCodEmp;
                strSQL += ", " + intCodLoc;
                strSQL += ", " + strCodTipDoc;
                strSQL += ", " + strCodDoc;
                strSQL += ", " + j;
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ", Null";
                strSQL += ")";
                stm.executeUpdate(strSQL);
                j++;

                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean consultarReg() {
        int intCodEmp, intCodLoc;
        boolean blnRes = true;
        try {
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodLoc = objParSis.getCodigoLocal();

            conCab = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (conCab != null) {
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if (txtCodTipDoc.getText().equals("")) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL += " , a1.co_usrIng, b5.tx_usr AS tx_nomUsrIng";
                    strSQL += " , a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                    strSQL += " FROM (          (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS b5 ON a1.co_usrIng=b5.co_usr)";
                    strSQL += "                  LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                    strSQL += "      )";

                    strSQL += " LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL += " LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL += " AND a5.co_mnu=" + objParSis.getCodigoMenu();
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL += " , a1.co_usrIng, a5.tx_usr AS tx_nomUsrIng";
                    strSQL += " , a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                    strSQL += " FROM (          (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_usrIng=a5.co_usr)";
                    strSQL += "                  LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                    strSQL += "      )";
                    strSQL += " LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                }

                strAux = txtCodTipDoc.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_tipDoc = " + strAux + "";
                }
                strAux = txtCodCta.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_cta = " + strAux + "";
                }
                strAux = txtCodCli.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_cli = " + strAux + "";
                }
                if (txtFecDoc.isFecha()) {
                    strSQL += " AND a1.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                strAux = txtCodDoc.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_doc = " + strAux + "";
                }
                strAux = txtNumAltUno.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.ne_numdoc = " + strAux + "";
                }
                strSQL += " AND a1.st_reg<>'E'";
                strSQL += " ORDER BY a1.co_loc,  a1.co_doc, a1.fe_doc, a1.co_tipDoc";

                //System.out.println("---SQL consultarReg(): " + strSQL);

                rstCab = stmCab.executeQuery(strSQL);
                if (rstCab.next()) {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    //strSQLCon = strSQL;
                } else {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarReg() {
        boolean blnRes = true;
        try {
            if (cargarCabReg()) {
                objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam = false;
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean cargarCabReg() {
        int intPosRel;
        BigDecimal bdeSubIvaCer, bdeSubIvaGra, bdeValIva;
        boolean blnRes = true;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                txtSubIvaCer.setText("");
                txtMonDoc.setText("");
                strSQL = "";
                //--------------------------------------------------------------------------------------------//
                strSQL += "select distinct";
                strSQL += " a2.co_tipdoc, a2.tx_descor, a2.tx_deslar, a2.tx_natdoc";//tambien se trae la naturaleza del documento
                strSQL += ", a4.co_cta, a4.tx_codcta, a4.tx_deslar as deslarcta";
                strSQL += ", a1.co_cli, a1.tx_nomcli, a1.tx_ruc, a1.tx_dircli";
                strSQL += ", a1.co_doc, a1.fe_doc, a1.ne_numdoc, a1.ne_numgui, a1.nd_sub, a1.nd_subIvaCer, a1.nd_subIvaGra, a1.nd_tot, a1.nd_valiva, a1.nd_poriva";
                strSQL += ", a1.tx_obs1, a1.tx_obs2, a1.st_reg ";
                if (objParSis.getCodigoMenu() == 1424) {
                    strSQL+=", a10.tx_numchq,a10.tx_numser,a10.tx_numautsri";
                }
                strSQL += "  from tbm_cabmovinv as a1";
                if (objParSis.getCodigoMenu() == 1424) {
                    strSQL += " inner join tbm_pagmovinv as a10 on (a1.co_emp=a10.co_emp and a1.co_loc=a10.co_loc and a1.co_tipdoc=a10.co_tipdoc and a1.co_doc=a10.co_doc)";
                }
                
                strSQL += " inner join tbm_cabtipdoc as a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                
                strSQL += " inner join tbr_tipdocprg as a3 on (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc)";
                
                strSQL += " inner join tbm_placta as a4 on (a1.co_emp=a4.co_emp and a1.co_cta=a4.co_cta)";
                strSQL += " WHERE ";
                strSQL += " a1.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL += " and a1.co_loc=" + rstCab.getString("co_loc") + "";
                strSQL += " AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL += " and a1.st_reg <> 'E'";

                //System.out.println("---SQL EN cargarCabReg(): " + strSQL);

                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    strAux = rst.getString("co_tipdoc");
                    txtCodTipDoc.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_descor");
                    txtDesCorTipDoc.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_deslar");
                    txtDesLarTipDoc.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_natDoc");
                    intSig = (strAux.equals("I") ? 1 : -1);
                    strAux = rst.getString("co_doc");
                    txtCodDoc.setText((strAux == null) ? "" : strAux);
                    txtFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy"));
                    strAux = rst.getString("ne_numdoc");
                    txtNumAltUno.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("ne_numgui");
                    txtNumAltDos.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("co_cta");
                    txtCodCta.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_codcta");
                    txtDesCorCta.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("deslarcta");
                    txtNomCta.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("co_cli");
                    txtCodCli.setText((strAux == null) ? "" : strAux);
                    strIdeCli = rst.getString("tx_ruc");
                    strAux = rst.getString("tx_nomcli");
                    txtNomCli.setText((strAux == null) ? "" : strAux);
                    strDirCli = rst.getString("tx_dircli");
                    
                    bdeSubIvaCer = rst.getBigDecimal("nd_subIvaCer") == null? new BigDecimal(0) :rst.getBigDecimal("nd_subIvaCer");
                    bdeSubIvaCer = objUti.redondearBigDecimal(bdeSubIvaCer, objParSis.getDecimalesMostrar());
                    bdeSubIvaGra = rst.getBigDecimal("nd_subIvaGra") == null? new BigDecimal(0) :rst.getBigDecimal("nd_subIvaGra");
                    bdeSubIvaGra = objUti.redondearBigDecimal(bdeSubIvaGra, objParSis.getDecimalesMostrar());
                    bdeValIva = rst.getBigDecimal("nd_valIva") == null? new BigDecimal(0) :rst.getBigDecimal("nd_valIva");
                    bdeValIva = objUti.redondearBigDecimal(bdeValIva.abs(), objParSis.getDecimalesMostrar());
                    
                    if ( bdeSubIvaCer.equals(new BigDecimal("0.00")) && bdeSubIvaGra.equals(new BigDecimal("0.00")) && bdeValIva.equals(new BigDecimal("0.00")) )
                    {  //No hay valor de IVA.
                       txtSubIvaCer.setText("" + Math.abs(rst.getDouble("nd_sub")));
                    }
                    else if ( bdeSubIvaCer.equals(new BigDecimal("0.00")) && bdeSubIvaGra.equals(new BigDecimal("0.00")) && !bdeValIva.equals(new BigDecimal("0.00")) )
                    {  //Si hay valor de IVA.
                       txtMonDoc.setText("" + Math.abs(rst.getDouble("nd_sub")));
                    }
                    else
                    {  txtSubIvaCer.setText(bdeSubIvaCer.toString());
                       txtMonDoc.setText(bdeSubIvaGra.toString());
                    }
                    
                    strAux = rst.getString("tx_obs1");
                    txaObs1.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_obs2");
                    txaObs2.setText((strAux == null) ? "" : strAux);
                    
                    //
                    if (objParSis.getCodigoMenu() == 1424) {
                       strAux = rst.getString("tx_numser");
                       txtNumSer.setText((strAux == null) ? "" : strAux);
                       strAux = rst.getString("tx_numchq");
                       txtNumSec.setText((strAux == null) ? "" : strAux);
                       strAux = rst.getString("tx_numautsri");
                       txtNumAut.setText((strAux == null) ? "" : strAux);
                       txtTot.setText("" + Math.abs(rst.getDouble("nd_tot")));
                       txtIva.setText("" + Math.abs(rst.getDouble("nd_valiva")));

                       if (rst.getDouble("nd_poriva")!=0) {
                           chkIva.setSelected(true);
                       }else{
                           chkIva.setSelected(false);
                       }
                    }
                    //
                    
                    //Mostrar el estado del registro.
                    strAux = rst.getString("st_reg");
                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    }
                    if (strAux.equals("E")) {
                        strAux = "Eliminado";
                    }
                    if (strAux.equals("I")) {
                        strAux = "Anulado";
                    }

                    objTooBar.setEstadoRegistro(strAux);
                    if (cargarDet(rstCab.getString("co_emp"), rstCab.getString("co_loc"), rstCab.getString("co_tipDoc"), rstCab.getString("co_doc"))) {
                        blnRes=true;
                    }else{
                        objTooBar.setEstadoRegistro(" ");
                        limpiarFrm();
                        blnRes = false;
                    }
                } else {
                    ////objTooBar.setEstadoRegistro("Eliminado");
                    objTooBar.setEstadoRegistro(" ");
                    limpiarFrm();
                    blnRes = false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst = null;
            stm = null;
            con = null;
            //Mostrar la posiciï¿½n relativa del registro.
            intPosRel = rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean consultarPagAso() {
        boolean blnRes = true;
        double dblValPagAso = 0.00;
        try {
            if (con != null) {
                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                //Obtener la fecha del servidor.//
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }

                //Armar la sentencia SQL.//
                strSQL = "";
                strSQL += " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.mo_pag, a1.nd_abo AS Abono";
                strSQL += " FROM tbm_pagmovinv AS a1";
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL += " AND co_doc=" + txtCodDoc.getText() + "";
                //System.out.println("---select pagmovinv para verificar pagos asociados: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    dblValPagAso = rst.getDouble("Abono");
                }
                VALPAGASO = dblValPagAso;

                stm.close();
                stm = null;
                datFecAux = null;
                rst.close();
                rst = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

  
    private boolean eliminarCab()
    {
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_cabPag";
                strSQL += " SET st_reg='E'";     //////////Eliminacion Logica/////////
                strSQL += ", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL += ", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean eliminarPagMovInv() {
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_pagmovinv";
                strSQL += " SET st_reg='E' ";  ///, mo_pag= mo_pag + " + (objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (Double.parseDouble(txtMonDoc.getText())):"0"),3));                
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL += " AND co_doc=" + txtCodDoc.getText() + "";
                //System.out.println("el update de pagmovinv para eliminarReg: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean eliminarCabMovInv() {
        boolean blnRes = true;
        try {
            if (con != null) {
                stm = con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_CabMovInv";
                strSQL += " SET st_reg='E'";
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL += " AND co_doc=" + txtCodDoc.getText() + "";
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;

    }

    /**
     * Esta función permite limpiar el formulario.
     *
     * @return true: Si se pudo limpiar la ventana sin ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            txtCodTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCta.setText("");
            txtNomCta.setText("");
            txtDesCorTipDoc.setText("");
            txtDesCorCta.setText("");
            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodDoc.setText("");
            txtNumAltUno.setText("");
            txtNumAltDos.setText("");
            txtMonDoc.setText("");
            txtFecDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            txtNumCta.setText("");
            chkIva.setSelected(false);
            txtIva.setText("");
            txtTot.setText("");
            objAsiDia.inicializar();
            objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_NO_EDI);
            strNumSerFacPrv="";
            strNumAutSriFacPrv="";
            strNumFecCadFacPrv="";
            objTblModFacPrv.removeAllRows();
            intNumRegFacPrv=0;
            txtNumSec.setText("");
            txtNumSer.setText("");
            txtNumAut.setText("");
        }
        catch (Exception e) {     
            blnRes = false;      }
        return blnRes;
    }

    /**
     * Esta clase implementa la interface DocumentListener que observa los
     * cambios que se presentan en los objetos de tipo texto. Por ejemplo:
     * JTextField, JTextArea, etc. Se la usa en el sistema para determinar si
     * existe algï¿½n cambio que se deba grabar antes de abandonar uno de los
     * modos o desplazarse a otro registro. Por ejemplo: si se ha hecho cambios
     * a un registro y quiere cancelar o moverse a otro registro se presentarï¿½
     * un mensaje advirtiendo que si no guarda los cambios los perderï¿½.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }

    /**
     * Esta funciï¿½n se encarga de agregar el listener "DocumentListener" a los
     * objetos de tipo texto para poder determinar si su contenido a cambiado o
     * no.
     */
    private void agregarDocLis() 
    {
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumAltUno.getDocument().addDocumentListener(objDocLis);
        txtCodCta.getDocument().addDocumentListener(objDocLis);
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodCli.getDocument().addDocumentListener(objDocLis);
        txtMonDoc.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }

    /**
     * Esta funciï¿½n se encarga de agregar el listener "DocumentListener" a los
     * objetos de tipo texto para poder determinar si su contenido a cambiado o
     * no.
     */
    private void consultarCamUsr() {
        strAux = "¿Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux += "Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";

        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'm': //Modificar
                        //System.out.println("Modo Modificar");
                        break;
                    case 'e': //Eliminar
                        //System.out.println("Modo Eliminar");
                        break;
                    case 'n': //insertar
                        //System.out.println("Modo Ninguno");
//                        objTooBar.insertar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                break;
            case 2: //CANCEL_OPTION
                break;
        }
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
     * un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar
     * ï¿½sta clase ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha
     * terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread {

        private int intIndFun;

        public ZafThreadGUI() {
            intIndFun = 0;
        }

        public void run() {
            switch (intIndFun) {
                case 0: //Boton "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(0);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Boton "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI = null;
        }

        /**
         * Esta funciï¿½n establece el indice de la funciï¿½n a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta funciï¿½n
         * sirve para determinar la funciï¿½n que debe ejecutar el Thread.
         *
         * @param indice El indice de la funciï¿½n a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFun = indice;
        }
    }

    /**
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
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
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes = true;

        //Inicializar los parametros que se van a pasar al reporte.
        java.util.Map mapPar = new java.util.HashMap();
        String strSQLRep = "", strSQLSubRep = "";
        try 
        {
            objRptSis.cargarListadoReportes();
            objRptSis.show();
            if (objRptSis.getOpcionSeleccionada() == objRptSis.INT_OPC_ACE) {

                //Obtener la fecha y hora del servidor.
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                datFecAux = null;

                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                            //formularios
                            case 70://"Notas de crédito (Tuval, Dimulti, Quito)..."
                            case 71://"Notas de débito (Tuval, Dimulti, Quito)"
                            case 311://"Notas de crédito (Manta, Santo Domingo)..."
                            case 312://"Notas de débito (Manta, Santo Domingo)..."
                            case 350://"Notas de crédito (Industrias Cosenco)..."
                            case 351://"Notas de débito (Industrias Cosenco)..."
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                strSQL = "";
                                strSQL += " select tx_nom,tx_dir,tx_ide,tx_tel,fe_doc,ne_numgui, total , tx_obs2";
                                strSQL += " ,case when ((iva=0) or (iva is null)) then 0 else iva end as iva";
                                strSQL += " ,case when ((iva=0) or (iva is null)) then total else sub end as sub";
                                strSQL += " from (";
                                strSQL += " 	select tx_nom,tx_dir,tx_ide,tx_tel,fe_doc,ne_numgui, total , tx_obs2, iva , (total -iva) as sub from (";
                                strSQL += " 		select";
                                strSQL += " 		a.co_Emp,a.co_loc, a.co_tipdoc, a.co_doc,";
                                strSQL += " 		b.tx_nom,b.tx_dir,b.tx_ide,b.tx_tel";
                                strSQL += " 		,a.fe_doc,a.ne_numgui,  abs(a.nd_sub) as total,a.tx_obs2";
                                strSQL += " 		,";
                                strSQL += " 		(select case when ((nd_monhab = 0 ) or (nd_monhab is null )) then nd_mondeb else nd_monhab end  as iva   from tbm_detdia as x";
                                strSQL += " 		 inner join tbm_placta as pla on (pla.co_emp=x.co_emp and pla.co_cta=x.co_cta and  ((pla.tx_codcta='2.01.06.02.04') or (pla.tx_codcta='1.01.07.03.01')) )";
                                strSQL += " 		 where x.co_emp=a.co_Emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_dia=a.co_doc ) as  iva";
                                strSQL += " 		from tbm_cabmovinv as a";
                                strSQL += " 		inner join tbm_cli as b on (a.co_emp=b.co_emp and a.co_cli=b.co_cli)";
                                strSQL += " 		where";
                                strSQL += " 		a.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL += " 		and a.co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL += " 		and a.co_tipdoc=" + txtCodTipDoc.getText() + "";
                                strSQL += " 		and a.co_doc=" + txtCodDoc.getText() + "";
                                strSQL += " 	) as x";
                                strSQL += " ) as  x";
                                //System.out.println("formulario: " + strSQL + " / " + strRutRpt + " / " + strNomRpt);
                                strSQLRep = strSQL;

                                mapPar.put("strSQLRep", "" + strSQLRep);
                                //System.out.println("strRutRpt" + strRutRpt);
                                //System.out.println("strNomRpt" + strNomRpt);

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                            case 392://notas de crédito(CxC - diarios)
                            case 393://notas de débito(CxC - diarios)
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);

                                strSQL = "";
                                strSQL += " SELECT a3.co_emp, a3.tx_nom AS tx_nomEmp, a3.tx_dir AS tx_dirEmp";
                                strSQL += " , a3.tx_ruc AS tx_rucEmp, a4.tx_desLar AS tx_nomCiu, a5.tx_desLar AS tx_nomPai";
                                strSQL += " , a8.tx_nom AS tx_nomPrg, a1.ne_numDoc, a1.co_cli, a1.tx_nomCli, a1.co_ben";
                                strSQL += " , a1.tx_benChq, a1.fe_doc, a1.co_com, a6.tx_usr AS tx_usrCom, a6.tx_nom AS tx_nomCom";
                                strSQL += " , CASE WHEN a1.nd_sub IS NULL THEN 0 ELSE ABS(a1.nd_sub) END AS nd_sub";
                                strSQL += " , CASE WHEN a1.nd_valIva IS NULL THEN 0 ELSE ABS(a1.nd_valIva) END AS nd_valIva";
                                strSQL += " , CASE WHEN a1.nd_tot IS NULL THEN 0 ELSE ABS(a1.nd_tot) END AS nd_tot";
                                strSQL += " , a11.tx_glo, a1.tx_obs2";
                                strSQL += " FROM (tbm_cabMovInv AS a1  INNER JOIN tbm_emp AS a3 ON a1.co_emp=a3.co_emp)";
                                strSQL += " INNER JOIN tbm_loc AS a2";
                                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                                strSQL += " INNER JOIN tbm_ciu AS a4";
                                strSQL += " ON a2.co_ciu=a4.co_ciu";
                                strSQL += " INNER JOIN tbm_pai AS a5";
                                strSQL += " ON a4.co_pai=a5.co_pai";
                                strSQL += " LEFT OUTER JOIN tbm_usr AS a6";
                                strSQL += " ON a1.co_com=a6.co_usr";
                                strSQL += " INNER JOIN tbm_cabDia AS a11";
                                strSQL += " ON a1.co_emp=a11.co_emp AND a1.co_loc=a11.co_loc AND a1.co_tipDoc=a11.co_tipDoc AND a1.co_doc=a11.co_dia";
                                strSQL += " LEFT OUTER JOIN tbm_mnusis AS a8";
                                strSQL += " ON a1.co_mnu=a8.co_mnu";
                                strSQL += " WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                                strSQL += " AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                                strSQL += " AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                strSQL += " AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                                strSQLRep = strSQL;
                                //System.out.println("diarios: " + strSQL + " / " + strRutRpt + " / " + strNomRpt);

                                strSQL = "";
                                strSQL += "select a3.tx_codcta, a3.tx_deslar, round(a2.nd_mondeb,2) as nd_mondeb, round(a2.nd_monhab,2) as nd_monhab";
                                strSQL += " from tbm_cabdia as a1, tbm_detdia as a2, tbm_placta as a3";
                                strSQL += " where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc";
                                strSQL += " and a1.co_tipdoc=a2.co_tipdoc";
                                strSQL += " and a1.co_dia=a2.co_dia";
                                strSQL += " and a2.co_emp=a3.co_emp";
                                strSQL += " and a2.co_cta=a3.co_cta";
                                strSQL += " and a1.co_emp=" + rstCab.getString("co_emp") + "";
                                strSQL += " and a1.co_loc=" + rstCab.getString("co_loc") + "";
                                strSQL += " and a1.co_tipdoc=" + rstCab.getString("co_tipDoc") + "";
                                strSQL += " and a1.co_dia=" + rstCab.getString("co_doc") + "";
                                strSQLSubRep = strSQL;
                                //System.out.println("diarios: " + strSQL + " / " + strRutRpt + " / " + strNomRpt);

                                mapPar.put("strSQLRep", strSQLRep);
                                mapPar.put("strSQLSubRep", strSQLSubRep);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("strCamAudRpt", "Ing:" + (rstCab.getString("tx_nomUsrIng") + " / Mod:" + rstCab.getString("tx_nomUsrMod") + " / Imp:" + objParSis.getNombreUsuario()) + "      Imp:" + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + "");
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;

                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    Vector vecCab,vecAux;
    //Constantes: Columnas del JTable Facturas de Proveedores
    final int INT_TBL_FAC_PRV_DAT_LIN=0;
    final int INT_TBL_FAC_PRV_DAT_NUM_FAC=1;
    final int INT_TBL_FAC_PRV_DAT_FEC_FAC=2;
    final int INT_TBL_FAC_PRV_DAT_VAL_FAC=3;
    final int INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA=4;
    final int INT_TBL_FAC_PRV_DAT_NUM_SER=5;
    final int INT_TBL_FAC_PRV_DAT_NUM_AUT=6;
    final int INT_TBL_FAC_PRV_DAT_FEC_CAD=7;
    final int INT_TBL_FAC_PRV_DAT_SUB=8;
    final int INT_TBL_FAC_PRV_DAT_IVA=9;
    final int INT_TBL_FAC_PRV_DAT_NUM_REG=10;
    final int INT_TBL_FAC_PRV_DAT_VAL_PND_PAG=11;
    ZafTblMod objTblModFacPrv;
    private Vector vecDatFacPrv, vecRegFacPrv;
    ZafTblPopMnu objTblPopMnuFacPrv;
    ZafTblCelRenLbl objTblCelRenLbl;
    private ZafMouMotAdaFacPrv objMouMotAdaFacPrv;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiTxt objTblCelEdiTxtFacPrv;
    private ZafTblCelEdiTxt objTblCelEdiTxtValFac;
    private int intNumRegFacPrv;
    private ZafTblCelEdiTxt objTblCelEdiTxtValFacSinIva;
    private ZafTblCelEdiTxt objTblCelEdiTxtFecCad, objTblCelEdiTxtNumAutSriFacPrv,objTblCelEdiTxtNumSer;
    private String strNumSerFacPrv;
    private String strNumAutSriFacPrv,strNumFecCadFacPrv;
    private ZafTblCelEdiTxt objTblCelEdiTxtNumFac;
    /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblFacPrv(){
        boolean blnRes=true;
        vecDatFacPrv=new Vector();
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(12);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FAC_PRV_DAT_LIN,"");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_FAC,"Núm.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_FEC_FAC,"Fec.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_FAC,"Val.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA,"Val.Sin.Iva.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_SER,"Núm.Ser.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_AUT,"Núm.Aut.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_FEC_CAD,"Fec.Cad.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_SUB,"Sub.Fac.Prv.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_IVA,"Iva.Fac.Prv");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_REG,"#Reg.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG,"Val.Pnd.Pag.");

            objTblModFacPrv=new ZafTblMod();
            objTblModFacPrv.setHeader(vecCab);


            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModFacPrv.setColumnDataType(INT_TBL_FAC_PRV_DAT_VAL_FAC, objTblModFacPrv.INT_COL_DBL, new Integer(0), null);


            //Configurar JTable: Establecer el modelo de la tabla.
            tblFacPrv.setModel(objTblModFacPrv);
            //Configurar JTable: Establecer tipo de seleccián.
            tblFacPrv.setRowSelectionAllowed(true);
            tblFacPrv.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuFacPrv=new ZafTblPopMnu(tblFacPrv);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFacPrv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFacPrv.getColumnModel();
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_SUB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG).setPreferredWidth(60);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFacPrv.addSystemHiddenColumn(INT_TBL_FAC_PRV_DAT_NUM_REG, tblFacPrv);
            objTblModFacPrv.addSystemHiddenColumn(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG, tblFacPrv);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFacPrv.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFacPrv=new ZafCxC10.ZafMouMotAdaFacPrv();
            tblFacPrv.getTableHeader().addMouseMotionListener(objMouMotAdaFacPrv);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_FAC);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_AUT);
            objTblModFacPrv.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_FAC);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_AUT);
            objTblModFacPrv.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModFacPrv.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelEdiTxtFacPrv=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_FAC).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setCellEditor(objTblCelEdiTxtFacPrv);
            objTblCelEdiTxtFacPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strFecFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strFecFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_FAC)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString();
                    if(!strFecFacPrv.equals("")){
                        //dtpFecDoc.setText(strFecFacPrv);
                    }
                    //if (buscarFactura(intFil)) {
                        //objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                    //}
                    
                }

                
            });
//            objTblCelEdiTxtFacPrv=null;


            objTblCelEdiTxtValFac=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellEditor(objTblCelEdiTxtValFac);
            objTblCelEdiTxtValFac.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeTotFacPrv=new BigDecimal("0");
                BigDecimal bdeSubFacPrv=new BigDecimal("0");
                BigDecimal bdeIvaFacPrv=new BigDecimal("0");
                //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
                BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );

                BigDecimal bdeValFacPrvBef=new BigDecimal("0");
                Object objValIvaPrvBef=new BigDecimal("0");
                Object objValSubPrvBef=new BigDecimal("0");
                int intNumRegFacPrvExi=-1;
                BigDecimal bdeFacPrvSinIva=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    intFil=tblFacPrv.getSelectedRow();
                    bdeValFacPrvBef=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    objValSubPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_SUB);
                    objValIvaPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_IVA);
                    if(validaFechaVencimiento(intFil)){
                        objTblCelEdiTxtValFac.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxtValFac.setCancelarEdicion(true);
                    intNumRegFacPrvExi=Integer.parseInt(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString()));

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    System.out.println("intFil: " + intFil);
                    cargarValorIvaFacturaProveedor();
                    bdeFacPrvSinIva=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString()));

                    if(bdeTotFacPrv.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                        objTblModFacPrv.removeEmptyRows();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        if(sumFacPrv_equals_totDct()){


                            //calcular iva
                            BigDecimal bdeFacIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                            
                            bdeIvaFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeFacIva);
                            bdeIvaFacPrv=objUti.redondearBigDecimal(bdeIvaFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeIvaFacPrv, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            //calcular subtotal --)
                            bdeSubFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).add(bdeFacPrvSinIva);
                            bdeSubFacPrv=objUti.redondearBigDecimal(bdeSubFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeSubFacPrv, intFil, INT_TBL_FAC_PRV_DAT_SUB);

                            if(intNumRegFacPrvExi==0){
                                intNumRegFacPrv++;
                                objTblModFacPrv.setValueAt("" + intNumRegFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);
                            }
                        }
                        else{
                            mostrarMsgInf("<HTML>La suma de las facturas del proveedor exceden el valor del documento.<BR>Verifique y vuelva a intentarlo</HTML>");

                            //objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG);

                            objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                            objTblModFacPrv.setValueAt(objValSubPrvBef, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            objTblModFacPrv.setValueAt(objValIvaPrvBef, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);

                        }

                    }

                    //if (buscarFactura(intFil)) {
                        //objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                    //}


                }
            });




            objTblCelEdiTxtValFacSinIva=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellEditor(objTblCelEdiTxtValFacSinIva);
            objTblCelEdiTxtValFacSinIva.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeTotFacPrv=new BigDecimal("0");
                BigDecimal bdeFacPrvSinIva=new BigDecimal("0");
                BigDecimal bdeSubFacPrv=new BigDecimal("0");
                BigDecimal bdeIvaFacPrv=new BigDecimal("0");
                //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
                BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                
                BigDecimal bdeValFacPrvBef=new BigDecimal("0");
                Object objValIvaPrvBef=new BigDecimal("0");
                Object objValSubPrvBef=new BigDecimal("0");
                int intNumRegFacPrvExi=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                    bdeValFacPrvBef=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    objValSubPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_SUB);
                    objValIvaPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_IVA);
                    if(validaFechaVencimiento(intFil)){
                        objTblCelEdiTxtValFacSinIva.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxtValFacSinIva.setCancelarEdicion(true);
                    intNumRegFacPrvExi=Integer.parseInt(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString()));
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    bdeFacPrvSinIva=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString()));

                    if(bdeTotFacPrv.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                        objTblModFacPrv.removeEmptyRows();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        if(sumFacPrv_equals_totDct()){
                            //calcular iva
                            BigDecimal bdeFacIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                            bdeIvaFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeFacIva);
                            bdeIvaFacPrv=objUti.redondearBigDecimal(bdeIvaFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeIvaFacPrv, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            //calcular subtotal --)
                            bdeSubFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).add(bdeFacPrvSinIva);
                            bdeSubFacPrv=objUti.redondearBigDecimal(bdeSubFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeSubFacPrv, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            if(intNumRegFacPrvExi==0){
                                intNumRegFacPrv++;
                                objTblModFacPrv.setValueAt("" + intNumRegFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);
                            }
                            //cargarFormaPagoFacturaProveedor();
                        }
                        else{
                            mostrarMsgInf("<HTML>La suma de las facturas del proveedor exceden el valor del documento.<BR>Verifique y vuelva a intentarlo</HTML>");
                            objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                            objTblModFacPrv.setValueAt(objValSubPrvBef, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            objTblModFacPrv.setValueAt(objValIvaPrvBef, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);
                        }

                    }
                   // if (buscarFactura(intFil)) {
                        //objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                    //}
                }
            });


            objTblCelEdiTxtNumSer=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setCellEditor(objTblCelEdiTxtNumSer);
            objTblCelEdiTxtNumSer.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strTblNumSerFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strTblNumSerFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_SER)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_SER).toString();
                    if(!strTblNumSerFacPrv.equals("")){
                        strNumSerFacPrv=strTblNumSerFacPrv;
                        objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                     }
                   // if (buscarFactura(intFil)) {
                        //objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                   // }
                }
            });


            objTblCelEdiTxtNumAutSriFacPrv=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setCellEditor(objTblCelEdiTxtNumAutSriFacPrv);
            objTblCelEdiTxtNumAutSriFacPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strTblNumAutSriFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strTblNumAutSriFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT).toString();
                    if(!strTblNumAutSriFacPrv.equals("")){
                        strNumAutSriFacPrv=strTblNumAutSriFacPrv;
                        objTblModFacPrv.setValueAt(strNumAutSriFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT);
                    }
                    //if (buscarFactura(intFil)) {
                        //objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                    //}
                }
            });


            objTblCelEdiTxtFecCad=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setCellEditor(objTblCelEdiTxtFecCad);
            objTblCelEdiTxtFecCad.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strTblNumFecCadFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strTblNumFecCadFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD).toString();
                    if(!strTblNumFecCadFacPrv.equals("")){
                        strNumFecCadFacPrv=strTblNumFecCadFacPrv;
                        objTblModFacPrv.setValueAt(strNumFecCadFacPrv, intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD);
                     }
                    //if (buscarFactura(intFil)) {
                        //objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                    //}
                }
            });

            objTblCelEdiTxtNumFac=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setCellEditor(objTblCelEdiTxtNumFac);
            objTblCelEdiTxtNumFac.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strNumFacPrvIng="";
                String strTblNumSerFacPrv="";
                String strTblNumAutSriFacPrv="";
                String strTblNumFecCadFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strNumFacPrvIng=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_FAC)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_FAC).toString();
                    if (!txtCodCli.getText().equals("")) {
                        if (buscarFactura(intFil)) {
                        //objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                    }
                        if(!strNumFacPrvIng.equals("")){
//                        if( (!strNumSerFacPrv.equals("")) && (!strNumAutSriFacPrv.equals(""))  && (!strNumFecCadFacPrv.equals("")) ){
//                            objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
//                            objTblModFacPrv.setValueAt(strNumAutSriFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT);
//                            objTblModFacPrv.setValueAt(strNumFecCadFacPrv, intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD);
//                        }
                    }
                    }else{
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                        vecDatFacPrv.clear();
                        objTblModFacPrv.setData(vecDatFacPrv);
                        tblFacPrv.setModel(objTblModFacPrv);
                    }
                }
            });




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
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaFacPrv extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblFacPrv.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_FAC_PRV_DAT_NUM_FAC:
                    strMsg="Número de factura";
                    break;
                case INT_TBL_FAC_PRV_DAT_FEC_FAC:
                    strMsg="Fecha de la factura(dd/MM/yyyy)";
                    break;
                case INT_TBL_FAC_PRV_DAT_VAL_FAC:
                    strMsg="Valor de la factura";
                    break;
                case INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA:
                    strMsg="Valor de la factura sin iva";
                    break;
                case INT_TBL_FAC_PRV_DAT_NUM_SER:
                    strMsg="Número de serie";
                    break;
                case INT_TBL_FAC_PRV_DAT_NUM_AUT:
                    strMsg="Número de autorización del SRI";
                    break;
                case INT_TBL_FAC_PRV_DAT_FEC_CAD:
                    strMsg="Fecha de caducidad(MM/yyyy)";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblFacPrv.getTableHeader().setToolTipText(strMsg);
        }
    }
    private boolean validaFechaVencimiento(int fila){
        boolean blnRes=true;
//        int intFecSis[];
//        int intFecVncChq[];
//        String strFecRecChq="";
//        String strFecVenChq="";
//        try{
//            dtpFecVenFacPrv.setText("");strFecVenChq="";
//            //fecha del sistema para comparar q no sea mayor a 2 años la fecha q se ingresa con la fecha del sistema
//            intFecSis=dtpFecAct.getFecha(dtpFecAct.getText());
//            strFecVenChq=objTblModFacPrv.getValueAt(fila, INT_TBL_FAC_PRV_DAT_FEC_FAC)==null?"":objTblModFacPrv.getValueAt(fila, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString();
//            if( ! strFecVenChq.toString().equals("")){
//                dtpFecVenFacPrv.setText(objUti.formatearFecha(strFecVenChq.trim(),"dd/MM/yyyy", "dd/MM/yyyy"));
//                if(dtpFecVenFacPrv.isFecha()){
//                    intFecVncChq=dtpFecVenFacPrv.getFecha(dtpFecVenFacPrv.getText());
//                    if(   (intFecVncChq[2]) > ((intFecSis[2]))  ){
//                        mostrarMsgInf("La fecha ingresada en una factura de proveedor está fuera del presente año");
//                        blnRes=false;
//                    }
//                }
//                else{
//                    strFecVenChq=strFecVenChq+ "/" + intFecSis[2];
//                    dtpFecVenFacPrv.setText(objUti.formatearFecha(strFecVenChq.trim(),"dd/MM/yyyy", "dd/MM/yyyy"));
//                    if(dtpFecVenFacPrv.isFecha()){
//                        intFecVncChq=dtpFecVenFacPrv.getFecha(dtpFecVenFacPrv.getText());
//                        strFecVenChq="" + intFecVncChq[0] + "/" + (intFecVncChq[1]<=9?"0" + intFecVncChq[1]:"" + intFecVncChq[1]) + "/" + (intFecSis[2]);
//                        dtpFecVenFacPrv.setText(strFecVenChq);
//                        tblDat.setValueAt(strFecVenChq, fila, INT_TBL_FAC_PRV_DAT_FEC_FAC);
//                    }
//                    else{
//                        mostrarMsgInf("Para hacer editable este campo debe ingresar primero la fecha de la factura");
//                        blnRes=false;
//                    }
//                }
//            }
//            else{
//                mostrarMsgInf("Para hacer editable este campo debe ingresar primero la fecha de la factura");
//                blnRes=false;
//            }
//
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
        return blnRes;
    }
    private boolean cargarValorIvaFacturaProveedor(){
        boolean blnRes=true;
//        BigDecimal bdeSub=new BigDecimal(BigInteger.ZERO);
//        BigDecimal bdeSumSub=new BigDecimal(BigInteger.ZERO);
//        try{
//            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//                bdeSub=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB)==null?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString());
//                if(! objTblMod.isChecked(i, INT_TBL_GRL_DAT_EST_IVA)){
//                    bdeSumSub=bdeSumSub.add(bdeSub);
//                }
//            }
//            objTblModFacPrv.setValueAt(bdeSumSub, 0, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA);
//
//            System.out.println("valror iva: " + bdeSumSub);
//
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=true;
//        }
        return blnRes;
    }
    
    private boolean sumFacPrv_equals_totDct(){
        boolean blnRes=false;
        BigDecimal bdeValFacPrv=new BigDecimal("0");
        BigDecimal bdeTotFacPrv=new BigDecimal("0");
        BigDecimal bdeTotDocPag=new BigDecimal("0");
        try{
            bdeTotDocPag=new BigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()));
            for(int i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                bdeValFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                bdeTotFacPrv=bdeTotFacPrv.add(bdeValFacPrv);
            }

            //txtFacPrvDif.setText("" + bdeTotDocPag.subtract(bdeTotFacPrv));


            if(bdeTotDocPag.compareTo(bdeTotFacPrv)>=0)
                blnRes=true;

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    private boolean buscarFactura(int intFil) {
        Boolean blnRes=false;
        Boolean blnFac=false;
        try{
            
            BigDecimal bdeSubFacPrv=new BigDecimal(BigInteger.ZERO);
            BigDecimal bdeTotFacPrv=new BigDecimal(BigInteger.ZERO);
            BigDecimal bdeSubIvaCer=new BigDecimal(BigInteger.ZERO);
            //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
            //BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
            BigDecimal bdeIvaFacPrv=new BigDecimal(BigInteger.ZERO);
             String strNum=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_FAC).toString();
                    if (strNum.length() > 0 && strNum.length() != 9) {
                            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Fac.</FONT> debe tener 9 digitos.<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                            vecDatFacPrv.clear();
                            objTblModFacPrv.setData(vecDatFacPrv);
                            tblFacPrv.setModel(objTblModFacPrv);
                    }else{
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            try{
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" , a2.tx_numChq AS ne_numFac, a2.fe_venChq AS fe_fac";
                    strSQL+=" , (case when nd_subIvaCer is not null then nd_subIvaCer else 0 end) as nd_subIvaCer";
                    strSQL+=" , a2.nd_monChq AS nd_valFac";
                    strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad";
                    strSQL+=" FROM tbm_cabRecDoc AS a1";
                    strSQL+=" INNER JOIN tbm_detRecDoc AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbr_detRecDocCabMovInv AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                    strSQL+=" WHERE a3.co_empRel=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a3.co_locRel=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" and a2.tx_numChq='" +objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_FAC)  + "'";
                    strSQL+=" AND a2.co_cli=" + txtCodCli.getText() + "";
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" , a2.tx_numChq, a2.fe_venChq, a2.nd_subIvaCer, a2.nd_monChq";
                    strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad";
                    strSQL+=" ORDER BY a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatFacPrv.clear();
                    Date datJun = new Date("06/01/2016");//Junio del 2016
                    Date datJunFin = new Date("06/01/2017");// Junio del 2017
                    //Obtener los registros.
                    while (rst.next()){
                        vecRegFacPrv=new Vector();
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_LIN, "");
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_FAC, rst.getString("ne_numFac"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_FEC_FAC, objUti.formatearFecha(rst.getDate("fe_fac"),"dd/MM/yyyy"));
                         Date datSelFec = new Date(objUti.formatearFecha(rst.getDate("fe_fac"),"MM/dd/yyyy"));
                        if (((datSelFec.after(datJun) || datSelFec.equals(datJun)) && datSelFec.before(datJunFin))) {
                            dblPorIva=14.00;
                        }else{
                            dblPorIva= objCtaCtb.getPorcentajeIvaCompras();
                        }
                        bdeTotFacPrv=objUti.redondearBigDecimal(rst.getBigDecimal("nd_valFac"), objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_FAC, bdeTotFacPrv);
                        bdeSubIvaCer=objUti.redondearBigDecimal(rst.getBigDecimal("nd_subIvaCer"), objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA, bdeSubIvaCer);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_SER, rst.getString("tx_numSer"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_AUT, rst.getString("tx_numAutSri"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_FEC_CAD, rst.getString("tx_fecCad"));
//                      bdeSubFacPrv=bdeTotFacPrv.divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
//                      bdeIvaFacPrv=bdeTotFacPrv.subtract(bdeSubFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_SUB, bdeSubFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_IVA, bdeIvaFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_REG, rst.getString("co_reg"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG, "");
                        vecDatFacPrv.add(vecRegFacPrv);
                        intNumRegFacPrv=rst.getInt("co_reg");   
                        blnFac=true;
                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    //Asignar vectores al modelo.
                    objTblModFacPrv.setData(vecDatFacPrv);
                    tblFacPrv.setModel(objTblModFacPrv);
                    vecDatFacPrv.clear();
                    if (!blnFac) {
                           mostrarMsgInf("No se encontro factura asociada para el proveedor.");
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
                    }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(ZafCxC10.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blnRes;
    }
    private boolean cargarDet(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        Boolean blnRes=false;
        try{
            Connection conLoc;
            Statement stmLoc;
            ResultSet rstLoc;
            BigDecimal bdeSubIvaCer=new BigDecimal(BigInteger.ZERO);
            BigDecimal bdeValIva=new BigDecimal(BigInteger.ZERO);
            BigDecimal bdeSubFacPrv=new BigDecimal(BigInteger.ZERO);
            BigDecimal bdeTotFacPrv=new BigDecimal(BigInteger.ZERO);
            //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
            //BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
            BigDecimal bdeIvaFacPrv=new BigDecimal(BigInteger.ZERO);
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            try{
                if (conLoc!=null){
                    stmLoc=conLoc.createStatement();
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" , a2.tx_numChq AS ne_numFac, a2.fe_venChq AS fe_fac, a2.nd_monChq AS nd_valFac, nd_subIvaCer";
                    strSQL+=" , ( (case when a2.nd_monChq is not null then a2.nd_monChq else 0 end) - (case when a2.nd_valIva is not null then a2.nd_valIva else 0 end) ) as nd_subFacPrv";
                    strSQL+=" , a2.nd_valIva, a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad";
                    strSQL+=" FROM tbm_cabRecDoc AS a1";
                    strSQL+=" INNER JOIN tbm_detRecDoc AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbr_detRecDocCabMovInv AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                    strSQL+=" WHERE a3.co_empRel=" + strCodEmp + "";
                    strSQL+=" AND a3.co_locRel=" + strCodLoc+ "";
                    strSQL+=" AND a3.co_tipDocRel=" + strCodTipDoc + "";
                    strSQL+=" AND a3.co_docRel=" + strCodDoc + "";
                    strSQL+=" AND a2.co_cli=" + txtCodCli.getText() + "";
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" , a2.tx_numChq, a2.fe_venChq, a2.nd_monChq";
                    strSQL+=" , a2.nd_subIvaCer, a2.nd_valIva";
                    strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad";
                    strSQL+=" ORDER BY a2.co_reg";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatFacPrv.clear();
                    
                    //Obtener los registros.
                    while (rstLoc.next()){
                        vecRegFacPrv=new Vector();
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_LIN, "");
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_FAC, rstLoc.getString("ne_numFac"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_FEC_FAC, objUti.formatearFecha(rstLoc.getDate("fe_fac"),"dd/MM/yyyy"));
                       
                        bdeTotFacPrv=objUti.redondearBigDecimal(rstLoc.getBigDecimal("nd_valFac"), objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_FAC, bdeTotFacPrv);
                        
                        bdeSubIvaCer = rstLoc.getBigDecimal("nd_subIvaCer") == null? new BigDecimal(0) :rstLoc.getBigDecimal("nd_subIvaCer");
                        bdeSubIvaCer = objUti.redondearBigDecimal(bdeSubIvaCer, objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA, bdeSubIvaCer);
                        
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_SER, rstLoc.getString("tx_numSer"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_AUT, rstLoc.getString("tx_numAutSri"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_FEC_CAD, rstLoc.getString("tx_fecCad"));
//                      bdeSubFacPrv=bdeTotFacPrv.divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
//                      bdeIvaFacPrv=bdeTotFacPrv.subtract(bdeSubFacPrv);
                        
                        bdeSubFacPrv = objUti.redondearBigDecimal(rstLoc.getBigDecimal("nd_subFacPrv"), objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_SUB, bdeSubFacPrv);
                        
                        bdeValIva = rstLoc.getBigDecimal("nd_valIva") == null? new BigDecimal(0) :rstLoc.getBigDecimal("nd_valIva");
                        bdeValIva = objUti.redondearBigDecimal(bdeValIva, objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_IVA, bdeValIva);
                        
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_REG, rstLoc.getString("co_reg"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG, "");
                        vecDatFacPrv.add(vecRegFacPrv);
                        intNumRegFacPrv=rstLoc.getInt("co_reg");   
                    }
                    
                    rstLoc.close();
                    stmLoc.close();
                    rstLoc=null;
                    stmLoc=null;
                    //Asignar vectores al modelo.
                    objTblModFacPrv.setData(vecDatFacPrv);
                    tblFacPrv.setModel(objTblModFacPrv);
                    vecDatFacPrv.clear();
                    blnRes=true;
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
            
        }
        catch (SQLException ex)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, ex);
        }
        return blnRes;
    }
    private boolean insertarDetRegNotDeb() {
        boolean blnRes = false;
        try {
            if (con != null) {
                if (objParSis.getCodigoMenu()==1424) {
                    if (tblFacPrv != null && tblFacPrv.getRowCount() > 0) {
                    for (int i = 0; i < tblFacPrv.getRowCount(); i++) {
                        if (objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC) != null) {
                            stm = con.createStatement();
                            strSQL = "";
                            strSQL += "INSERT INTO tbr_detRecDocCabMovInv(";
                            strSQL += "co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRel,co_locRel,";
                            strSQL += " co_tipDocRel,co_docRel,nd_valAsi,st_reg,st_regRep)";
                            strSQL += " SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, ";
                            strSQL += objParSis.getCodigoEmpresa() + " as co_empRel, ";
                            strSQL += objParSis.getCodigoLocal() + " as co_locRel, ";
                            strSQL += txtCodTipDoc.getText() + " as co_tipdocrel, ";
                            strSQL += txtCodDoc.getText() + " as co_docrel, ";
                            strSQL += " a2.nd_monChq AS nd_valFac,a3.st_reg, a3.st_regrep ";
                            strSQL += " FROM tbm_cabRecDoc AS a1 INNER JOIN tbm_detRecDoc AS a2 ON a1.co_emp=a2.co_emp ";
                            strSQL += " AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                            strSQL += " INNER JOIN tbr_detRecDocCabMovInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc ";
                            strSQL += " AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg ";
                            strSQL += " WHERE a3.co_empRel= " + objParSis.getCodigoEmpresa();
                            strSQL += " AND a3.co_locRel= " + objParSis.getCodigoLocal();
                            //strSQL += " AND a3.co_tipDocRel=74 AND a3.co_docRel=353 ";
                            strSQL += " and a2.tx_numChq='" + objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC) + "'";
                            strSQL += " AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                            stm.executeUpdate(strSQL);
                            blnRes = true;
                        }
                    }
                    if (stm!=null) {
                        stm.close();
                        stm = null;
                    }
                }
                }else{
                                blnRes = true;
                }
                
                
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean eliminarDetNotDeb()
    {
        boolean blnRes=true;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (tblFacPrv != null && tblFacPrv.getRowCount() > 0) {
                    for (int i = 0; i < tblFacPrv.getRowCount(); i++) {
                        if (objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC) != null) {
                            stm = con.createStatement();
                            //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbr_detRecDocCabMovInv";
                strSQL+=" SET st_reg='E'";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.st_reg";
                strSQL+=" 	FROM tbr_detRecDocCabMovInv AS a1";
                strSQL+="       WHERE a1.co_empRel=" + objParSis.getCodigoEmpresa();
                strSQL+="       AND a1.co_locRel=" + objParSis.getCodigoLocal();
                strSQL+="       AND a1.co_tipDocRel=" + txtCodTipDoc.getText();
                strSQL+="       AND a1.co_docRel=" + txtCodDoc.getText();
                strSQL+=" 	AND a1.st_reg IN('A')";
                strSQL+=" 	) AS x";
                strSQL+=" WHERE tbr_detRecDocCabMovInv.co_empRel=x.co_empRel AND tbr_detRecDocCabMovInv.co_locRel=x.co_locRel ";
                strSQL+=" AND tbr_detRecDocCabMovInv.co_tipdocRel=x.co_tipdocRel AND tbr_detRecDocCabMovInv.co_docRel=x.co_docRel";
                strSQL+=";";
                stm.executeUpdate(strSQL);
                con.commit();
                blnRes = true;
                        }
                    }
                }
               con.close();
               con = null; 
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
    private boolean anularDetNotDeb()
    {
        boolean blnRes=true;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (tblFacPrv != null && tblFacPrv.getRowCount() > 0) {
                    for (int i = 0; i < tblFacPrv.getRowCount(); i++) {
                        if (objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC) != null) {
                            stm = con.createStatement();
                            //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbr_detRecDocCabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.st_reg";
                strSQL+=" 	FROM tbr_detRecDocCabMovInv AS a1";
                strSQL+="       WHERE a1.co_empRel=" + objParSis.getCodigoEmpresa();
                strSQL+="       AND a1.co_locRel=" + objParSis.getCodigoLocal();
                strSQL+="       AND a1.co_tipDocRel=" + txtCodTipDoc.getText();
                strSQL+="       AND a1.co_docRel=" + txtCodDoc.getText();
                strSQL+=" 	AND a1.st_reg IN('A')";
                strSQL+=" 	) AS x";
                strSQL+=" WHERE tbr_detRecDocCabMovInv.co_empRel=x.co_empRel AND tbr_detRecDocCabMovInv.co_locRel=x.co_locRel ";
                strSQL+=" AND tbr_detRecDocCabMovInv.co_tipdocRel=x.co_tipdocRel AND tbr_detRecDocCabMovInv.co_docRel=x.co_docRel";
                strSQL+=";";
                stm.executeUpdate(strSQL);
                con.commit();
                blnRes = true;
                        }
                    }
                }
               con.close();
               con = null; 
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
    
    private void calcularTotDoc_FocusLost()
    {
        txtTot.setText("" + txtSubIvaCer.getText() + txtMonDoc.getText());
        objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
        
        if (objParSis.getCodigoMenu() == 1424) {
            Double dblvalor;
            if (objUti.isNumero(txtMonDoc.getText()))
            {   if (chkIva.isSelected()) {
                    dblvalor = objUti.redondear(Double.parseDouble(txtMonDoc.getText().equals("") ? "0" : txtMonDoc.getText()) * (dblPorIva / 100), objParSis.getDecimalesMostrar());
                    txtIva.setText(String.valueOf(dblvalor));
                } else {
                    txtIva.setText("0.0");
                }
                dblvalor = objUti.redondear( Double.parseDouble((txtSubIvaCer.getText().equals("") ? "0" : txtSubIvaCer.getText())) + Double.parseDouble((txtMonDoc.getText().equals("") ? "0" : txtMonDoc.getText())) + Double.parseDouble((txtIva.getText().equals("") ? "0" : txtIva.getText())), objParSis.getDecimalesMostrar() );
                txtTot.setText(String.valueOf(dblvalor));
            }
            else if (objUti.isNumero(txtSubIvaCer.getText()))
            {
               dblvalor = objUti.redondear( Double.parseDouble((txtSubIvaCer.getText().equals("") ? "0" : txtSubIvaCer.getText())) + Double.parseDouble((txtMonDoc.getText().equals("") ? "0" : txtMonDoc.getText())) + Double.parseDouble((txtIva.getText().equals("") ? "0" : txtIva.getText())), objParSis.getDecimalesMostrar() );
               txtTot.setText(String.valueOf(dblvalor));
            }
            else 
            {
                ///txtMonDoc.setText("0.0");
                txtIva.setText("0.0");
                txtTot.setText("0.0");
            }
            Double total=Double.valueOf(txtTot.getText());
            Double factura=0.00;
            if (tblFacPrv != null && tblFacPrv.getRowCount() > 0) {
                    for (int i = 0; i < tblFacPrv.getRowCount(); i++) {
                        if (objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC) != null) {
                             factura = Double.valueOf(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString());
                        }
                    }
                    if (factura>0.00) {
                    if (total>factura) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Total</FONT> no debe ser mayor que el valor de la factura<BR>Ingrese un valor menor o igual y vuelva a intentarlo.</HTML>");
                txtMonDoc.requestFocus();
                txtMonDoc.setText("");
                //calculaTotal();
                }
            }else{
                mostrarMsgInf("<HTML> <FONT COLOR=\"blue\"></FONT> Debe Ingresar una factura asociada antes de ingresar el valor.<BR></HTML>");
                txtNumSer.requestFocus();
                txtMonDoc.setText("");    
                txtTot.setText("");
                txtIva.setText("");
            }
            }
        }   
    } //Funcion calcularTotDoc_FocusLost()
}