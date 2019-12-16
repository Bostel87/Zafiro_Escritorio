/*
 * ZafVenConCxC01.java
 *
 * Created on 18 de septiembre de 2007, 10:26 AM
 */

package CxP.ZafCxP13;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCxP13_VenCon extends javax.swing.JDialog
{
    //Constantes:
    public static final int INT_BUT_CAN=0;                      /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;                      /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/
    
    //Constantes: Columnas del JTable:
    public static final int INT_TBL_DAT_LIN=0;                  //Línea.
    public static final int INT_TBL_DAT_CHK=1;                  //Casilla de verificación.
    public static final int INT_TBL_DAT_COD_CLI=2;             //Codigo del beneficiario.
    public static final int INT_TBL_DAT_NOM_CLI=3;             //Nombre del beneficiario.
    public static final int INT_TBL_DAT_COD_BEN=4;             //Codigo del beneficiario.
    public static final int INT_TBL_DAT_NOM_BEN=5;             //Nombre del beneficiario.
    public static final int INT_TBL_DAT_COD_LOC=6;              //Código del local.
    public static final int INT_TBL_DAT_COD_TIP_DOC=7;          //Código del tipo de documento.
    public static final int INT_TBL_DAT_DEC_TIP_DOC=8;          //Descripción corta del tipo de documento.
    public static final int INT_TBL_DAT_DEL_TIP_DOC=9;          //Descripción larga del tipo de documento.
    public static final int INT_TBL_DAT_COD_DOC=10;              //Código del documento (Sistema).
    public static final int INT_TBL_DAT_NUM_DOC=11;              //Número del documento.
    public static final int INT_TBL_DAT_FEC_DOC=12;              //Fecha del documento.
    public static final int INT_TBL_DAT_VAL_DOC=13;             //Valor del documento.
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Cliente".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    //Variables de la clase.
    private int intButSelDlg;                                   //Botón seleccionado en el JDialog.
    private String strTit;                                      //Título del JDialog.
    private boolean blnMarTodChkTblDat=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private String strCodTipDoc;
    
    /**
     * Este constructor crea una instancia de la clase ZafVenConCxC01.
     * @param padre El formulario padre.
     * @param parametros El objeto ZafParSis.
     * @param titulo El título del JDialog.
     */
    public ZafCxP13_VenCon(java.awt.Frame padre, ZafParSis parametros, String titulo)
    {
        this(padre, parametros, titulo, "");
    }
    
    /**
     * Este constructor crea una instancia de la clase ZafVenConCxC01.
     * @param padre El formulario padre.
     * @param parametros El objeto ZafParSis.
     * @param titulo El título del JDialog.
     * <BR>true: Sólo se mostrará los documentos donde la forma de pago sea "Tarjeta de crédito".
     * <BR>false: Sólo se mostrará los documentos donde la forma de pago no sea "Tarjeta de crédito".
     */
    public ZafCxP13_VenCon(java.awt.Frame padre, ZafParSis parametros, String titulo, String strPrm_CodTipDoc)
    {
        super(padre, true);
        initComponents();
        //Inicializar objetos.
        objParSis=parametros;
        strTit=titulo;
        strCodTipDoc = strPrm_CodTipDoc;
        intButSelDlg=INT_BUT_CAN;
        if (!configurarFrm())
            exitForm();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      bgrCri = new javax.swing.ButtonGroup();
      panFrm = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();
      panGen = new javax.swing.JPanel();
      panGenCab = new javax.swing.JPanel();
      lblCli = new javax.swing.JLabel();
      txtCodCli = new javax.swing.JTextField();
      txtDesLarCli = new javax.swing.JTextField();
      butCli = new javax.swing.JButton();
      lblNumDoc = new javax.swing.JLabel();
      txtNumDoc = new javax.swing.JTextField();
      panGenDet = new javax.swing.JPanel();
      spnDat = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      panBar = new javax.swing.JPanel();
      panBot = new javax.swing.JPanel();
      butCon = new javax.swing.JButton();
      butLim = new javax.swing.JButton();
      butAce = new javax.swing.JButton();
      butCan = new javax.swing.JButton();
      panBarEst = new javax.swing.JPanel();
      lblMsgSis = new javax.swing.JLabel();
      jPanel6 = new javax.swing.JPanel();
      pgrSis = new javax.swing.JProgressBar();

      setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowActivated(java.awt.event.WindowEvent evt) {
            formWindowActivated(evt);
         }
         public void windowClosing(java.awt.event.WindowEvent evt) {
            exitForm(evt);
         }
      });

      panFrm.setLayout(new java.awt.BorderLayout());

      lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
      lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      lblTit.setText("Título de la ventana");
      panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

      panGen.setLayout(new java.awt.BorderLayout());

      panGenCab.setPreferredSize(new java.awt.Dimension(10, 140));
      panGenCab.setLayout(null);

      lblCli.setText("Cliente:");
      lblCli.setToolTipText("Bodega");
      panGenCab.add(lblCli);
      lblCli.setBounds(0, 4, 100, 20);

      txtCodCli.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtCodCli.setToolTipText("Código del cliente/proveedor");
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
      txtCodCli.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
            txtCodCliKeyPressed(evt);
         }
      });
      panGenCab.add(txtCodCli);
      txtCodCli.setBounds(100, 4, 56, 20);

      txtDesLarCli.setToolTipText("Cliente/Proveedor");
      txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesLarCliActionPerformed(evt);
         }
      });
      txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesLarCliFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesLarCliFocusLost(evt);
         }
      });
      panGenCab.add(txtDesLarCli);
      txtDesLarCli.setBounds(156, 4, 236, 20);

      butCli.setText("...");
      butCli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCliActionPerformed(evt);
         }
      });
      panGenCab.add(butCli);
      butCli.setBounds(392, 4, 20, 20);

      lblNumDoc.setText("Num. Doc:");
      lblNumDoc.setToolTipText("Bodega");
      panGenCab.add(lblNumDoc);
      lblNumDoc.setBounds(0, 112, 70, 20);

      txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtNumDoc.setToolTipText("Código del cliente/proveedor");
      txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNumDocActionPerformed(evt);
         }
      });
      txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNumDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNumDocFocusLost(evt);
         }
      });
      panGenCab.add(txtNumDoc);
      txtNumDoc.setBounds(80, 112, 80, 20);

      panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

      panGenDet.setLayout(new java.awt.BorderLayout(0, 1));

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
      ) {
         boolean[] canEdit = new boolean [] {
            true, false, true, true
         };

         public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
         }
      });
      tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
            tblDatKeyPressed(evt);
         }
      });
      tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            tblDatMouseClicked(evt);
         }
      });
      spnDat.setViewportView(tblDat);

      panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

      panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

      panFrm.add(panGen, java.awt.BorderLayout.CENTER);

      panBar.setLayout(new java.awt.BorderLayout());

      panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

      butCon.setText("Consultar");
      butCon.setToolTipText("Ejecuta la consulta de acuerdo al criterio seleccionado.");
      butCon.setPreferredSize(new java.awt.Dimension(92, 25));
      butCon.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butConActionPerformed(evt);
         }
      });
      panBot.add(butCon);

      butLim.setText("Limpiar");
      butLim.setToolTipText("Limpia el cuadro de dialogo.");
      butLim.setPreferredSize(new java.awt.Dimension(92, 25));
      butLim.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butLimActionPerformed(evt);
         }
      });
      panBot.add(butLim);

      butAce.setText("Aceptar");
      butAce.setToolTipText("Acepta la opción seleccionada.");
      butAce.setPreferredSize(new java.awt.Dimension(92, 25));
      butAce.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butAceActionPerformed(evt);
         }
      });
      panBot.add(butAce);

      butCan.setText("Cancelar");
      butCan.setToolTipText("Cierra el cuadro de dialogo.");
      butCan.setPreferredSize(new java.awt.Dimension(92, 25));
      butCan.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCanActionPerformed(evt);
         }
      });
      panBot.add(butCan);

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
      pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
      pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
      jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

      panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

      panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

      panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

      getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
   }// </editor-fold>//GEN-END:initComponents

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        strCodCli=txtCodCli.getText();
        mostrarVenConCli(0);
        //Limpiar sólo si el cliente ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            objTblMod.removeAllRows();
            lblMsgSis.setText("Listo");
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
            //Limpiar sólo si el cliente ha cambiado.
            if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
            {
                objTblMod.removeAllRows();
                lblMsgSis.setText("Listo");
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(1);
            }
            //Limpiar sólo si el cliente ha cambiado.
            if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
            {
                objTblMod.removeAllRows();
                lblMsgSis.setText("Listo");
            }
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        limpiar();
    }//GEN-LAST:event_butLimActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (tblDat.getRowCount()>0)
        {
            if (tblDat.getSelectedRow()==-1)
            {
                tblDat.setRowSelectionInterval(0,0);
            }
            tblDat.requestFocus();
        }
        else
        {
            txtCodCli.requestFocus();
        }
    }//GEN-LAST:event_formWindowActivated

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        intButSelDlg=INT_BUT_CAN;
        dispose();
    }//GEN-LAST:event_exitForm

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        intButSelDlg=INT_BUT_ACE;
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Cerrar el JDialog al dar doble click.
        if (evt.getClickCount()==2)
        {
            butAceActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatMouseClicked

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Cerrar el JDialog al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            //evt.setKeyCode(0);
            evt.consume();
            butAceActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCanActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (isCamVal())
        {
            //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
            if (butCon.getText().equals("Consultar"))
            {
                blnCon=true;
                if (objThrGUI==null)
                {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }            
            }
            else
            {
                blnCon=false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

   private void txtCodCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodCliKeyPressed
      // TODO add your handling code here:
      if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode())
      {
         //CodCliFocusLost();
         txtDesLarCli.transferFocus();
      }
   }//GEN-LAST:event_txtCodCliKeyPressed

   private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_txtNumDocActionPerformed

   private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
      // TODO add your handling code here:
      txtNumDoc.selectAll();
   }//GEN-LAST:event_txtNumDocFocusGained

   private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
      // TODO add your handling code here:
   }//GEN-LAST:event_txtNumDocFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.ButtonGroup bgrCri;
   private javax.swing.JButton butAce;
   private javax.swing.JButton butCan;
   private javax.swing.JButton butCli;
   private javax.swing.JButton butCon;
   private javax.swing.JButton butLim;
   private javax.swing.JPanel jPanel6;
   private javax.swing.JLabel lblCli;
   private javax.swing.JLabel lblMsgSis;
   private javax.swing.JLabel lblNumDoc;
   private javax.swing.JLabel lblTit;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panBarEst;
   private javax.swing.JPanel panBot;
   private javax.swing.JPanel panFrm;
   private javax.swing.JPanel panGen;
   private javax.swing.JPanel panGenCab;
   private javax.swing.JPanel panGenDet;
   private javax.swing.JProgressBar pgrSis;
   private javax.swing.JScrollPane spnDat;
   private javax.swing.JTable tblDat;
   private javax.swing.JTextField txtCodCli;
   private javax.swing.JTextField txtDesLarCli;
   private javax.swing.JTextField txtNumDoc;
   // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            panGenCab.add(objSelFec);
            objSelFec.setBounds(0, 34, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle(strTit + " v1.0");
            lblTit.setText(strTit);
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCli.setBackground(objParSis.getColorCamposObligatorios());
            //Configurar las ZafVenCon.
            configurarVenConCli();
            //Configurar los JTables.
            configurarTblDat();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(13);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Nom.Cli.");
            vecCab.add(INT_TBL_DAT_COD_BEN,"Cód.Ben.");
            vecCab.add(INT_TBL_DAT_NOM_BEN,"Nom.Ben.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_BEN).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(19);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatHeaMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            //Establecer el modo de operación del JTable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
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
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            
            //Ocultar columnas.
            int intColOcu[]=new int[4];
            intColOcu[0]=4;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
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
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblDatHeaMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK)
            {
                if (blnMarTodChkTblDat)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Esta función obtiene el título de la ventana de consulta.
     * @return El título de la ventana de consulta.
     */
    public String getTitulo()
    {
        return strTit;
    }
    
    /**
     * Esta función establece el título de la ventana de consulta.
     * @param titulo El título de la ventana de consulta.
     */
    public void setTitulo(String titulo)
    {
        strTit=titulo;
        this.setTitle(strTit);
        lblTit.setText(strTit);
    }

    /**
     * Esta función carga los datos en la ventana de consulta.
     */
    public void cargarDatos()
    {
        butConActionPerformed(null);
    }

    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar (INT_BUT_CAN).
     * <LI>1: Click en el botón Aceptar (INT_BUT_ACE).
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getSelectedButton()
    {
        return intButSelDlg;
    }

    /**
     * Esta función obtiene el valor que se encuentra en la posición especificada.
     * @param row La fila de la que se desea obtener el valor.
     * @param col La columna de la que se desea obtener el valor.
     * @return El valor que se encuentra en la posición especificada.
     */
    public String getValueAt(int row, int col)
    {
        if (row!=-1)
            return objUti.parseString(objTblMod.getValueAt(row, col));
        else
            return "";
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        String strAux;
        
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                
                if (!txtNumDoc.getText().equals(""))
                {
                   strConSQL+=" AND a1.ne_numDoc = " + txtNumDoc.getText();
                }
                else
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strConSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strConSQL+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strConSQL+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                //Armar la sentencia SQL.
                strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_descor as tx_desCorTipDoc, a2.tx_deslar as tx_desLarTipDoc,";
                strSQL += " a1.co_cli, a1.tx_nomcli, a1.co_ben, a1.tx_benchq, a1.ne_numdoc, a1.fe_doc, a1.nd_tot, a3.st_reg, a3.nd_porret, a3.st_autpag,";
                strSQL += " a3.mo_pag, a3.nd_abo, abs(a3.mo_pag + a3.nd_abo) as valPen " + "\n";
                strSQL += "FROM tbm_cabmovinv as a1 " + "\n";
                strSQL += "INNER JOIN tbm_cabtipdoc as a2 on a1.co_emp = a2.co_emp and a1.co_loc = a2.co_loc and a1.co_tipdoc = a2.co_tipdoc " + "\n";
                strSQL += "INNER JOIN tbm_pagmovinv as a3 on a1.co_emp = a3.co_emp and a1.co_loc = a3.co_loc and a1.co_tipdoc = a3.co_tipdoc and a1.co_doc = a3.co_doc " + "\n";
                strSQL += "WHERE a1.st_reg = 'A' and a3.st_reg in ('A','C') and a3.nd_porret = 0";
                strSQL += " and a1.co_emp = " + objParSis.getCodigoEmpresa();
                strSQL += " and a1.co_tipdoc = " + strCodTipDoc + " and a1.co_cli = " + txtCodCli.getText() + " and abs(a3.mo_pag + a3.nd_abo) > 0 ";
                strSQL += strConSQL + " " + "\n";
                strSQL += "ORDER BY co_emp, co_loc, co_tipdoc, co_doc";
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,null);
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomcli"));
                        strAux = rst.getString("co_ben") == null? "" :rst.getString("co_ben");
                        vecReg.add(INT_TBL_DAT_COD_BEN,strAux);
                        strAux = rst.getString("tx_benchq") == null? "" :rst.getString("tx_benchq");
                        vecReg.add(INT_TBL_DAT_NOM_BEN,strAux);
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCorTipDoc"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLarTipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("nd_tot"));
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                blnMarTodChkTblDat=true;
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
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
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if (txtCodCli.getText().equals(""))
        {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
           txtCodCli.requestFocus();
           return false;
        }
        
        return true;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,"Mensaje del sistema Zafiro",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función obtiene las filas que han sido seleccionadas por el usuario en el JTable.
     * @return Un arreglo de enteros que contiene la(s) fila(s) seleccionadas.
     */
    public int[] getFilasSeleccionadas()
    {
        int i=0, j=0;
        int intRes[];
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                j++;
            }
        }
        intRes=new int[j];
        j=0;
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                intRes[j]=i;
                j++;
            }
        }
        return intRes;
    }
    
    /**
     * Esta función obtiene el código del cliente seleccionado en la ventana de consulta.
     * @return El código del cliente selecoiondo en la ventana de consulta
     */
    public String getCodigoCliente()
    {
        return txtCodCli.getText();
    }
    
    /**
     * Esta función obtiene el nombre del cliente seleccionado en la ventana de consulta
     * @return El título de la ventana de consulta.
     */
    public String getNombreCliente()
    {
        return txtDesLarCli.getText();
    }
    
    public void setTipoConsulta()
    {
        vcoCli.setCondicionesSQL(" AND a1.st_cli='S'");
    }

    /**
     * Esta función establece la fila especificada como una fila procesada. Lo que hace internamente la función
     * es almacenar una letra "P" en la primera columna del JTable. A través de éste valor es posible determinar
     * si la fila ya fue procesada anteriormente o si todavía no ha sido procesada.
     * @param fila La fila que se marcará como procesada.
     */
    public void setFilaProcesada(int fila)
    {
        objTblMod.setValueAt("P", fila, INT_TBL_DAT_LIN);
    }

    /**
     * Esta función limpia la ventana de consulta. Es decir, la ventana de consulta
     * queda como si todavía no se hubiera consultado nada.
     */
    public void limpiar()
    {
        txtCodCli.setText("");
        txtDesLarCli.setText("");
        objSelFec.setCheckBoxChecked(true);
        txtNumDoc.setText("");
        objTblMod.removeAllRows();
        lblMsgSis.setText("Listo");
        txtNumDoc.requestFocus();
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
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
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
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;   
                case INT_TBL_DAT_COD_BEN:
                    strMsg="Código del beneficiario";
                    break;
                case INT_TBL_DAT_NOM_BEN:
                    strMsg="Nombre del beneficiario";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}