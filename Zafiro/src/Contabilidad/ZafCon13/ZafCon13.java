/*
 * ZafCon13.java
 *
 * Created on 02 de octubre de 2006, 11:38 AM
 */

package Contabilidad.ZafCon13;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafAsiDia.ZafAsiDia;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

//para ireports
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint; 
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


/**
 *
 * @author  Eddye Lino
 */
public class ZafCon13 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;                //Lánea
    final int INT_TBL_DAT_COD_SIS=1;            //Cádigo de la cuenta (Sistema).
    final int INT_TBL_DAT_NIV_CTA=2;            //Nivel de la cuenta.
    final int INT_TBL_DAT_PAD_CTA=3;            //Padre de la cuenta.
    final int INT_TBL_DAT_COD_CTA=4;            //Cádigo de la cuenta (Contable).
    final int INT_TBL_DAT_NOM_CTA=5;            //Nombre de la cuenta.
    final int INT_TBL_DAT_SAL_MEN=6;            //Saldo mensual.
    final int INT_TBL_DAT_SAL_ACU=7;            //Saldo acumulado.
    final int INT_TBL_DAT_TIP_CTA=8;            //Tipo de cuenta.
    
    //Constantes: Array List de la exportacion de datos
    final int INT_ARL_DAT_COD_ENT=0;            //Codigo del ente del mercado de valores.
    final int INT_ARL_DAT_COD_CTA=1;            //Codigo de la cuenta contable de ultimo nivel.
    final int INT_ARL_DAT_VAL_CTA=2;            //Valor de la cuenta contable.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PopupMená en JTable.
    private ZafTblBus objTblBus;                        //Editor de básqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafPerUsr objPerUsr;
    private ZafRptSis objRptSis;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafThreadGUIExportar objThrGUIExp;
    private ZafDatePicker dtpFecDoc;
    private java.util.Date datFecAux;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
 
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    private boolean blnButExp;
    javax.swing.JInternalFrame jfrThis;                 //Hace referencia a this
    private String strFecAux;
    private int intNIVELES=7;
    private double dblSalMenImp;
    private double dblSalAcuImp;

    //private String strNivRep;
    //private String strFecCor;
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCon13(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            //objParSis=(ZafParSis)obj.clone();
            
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
//            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");

            objPerUsr=new ZafPerUsr(objParSis);
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            blnButExp = false;
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      panFrm = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();
      tabFrm = new javax.swing.JTabbedPane();
      panFil = new javax.swing.JPanel();
      panFilFec = new javax.swing.JPanel();
      optDia = new javax.swing.JRadioButton();
      optMes = new javax.swing.JRadioButton();
      cboMesCor = new javax.swing.JComboBox();
      cboAnoCor = new javax.swing.JComboBox();
      panFil2 = new javax.swing.JPanel();
      lblNiv = new javax.swing.JLabel();
      cboNiv = new javax.swing.JComboBox();
      chkMerVal = new javax.swing.JCheckBox();
      chkSalCer = new javax.swing.JCheckBox();
      chkRemayo = new javax.swing.JCheckBox();
      panRpt = new javax.swing.JPanel();
      panRptReg = new javax.swing.JPanel();
      spnDat = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      spnTot = new javax.swing.JScrollPane();
      tblTot = new javax.swing.JTable();
      panBar = new javax.swing.JPanel();
      panBot = new javax.swing.JPanel();
      butCon = new javax.swing.JButton();
      butCer = new javax.swing.JButton();
      butImp = new javax.swing.JButton();
      butExp = new javax.swing.JButton();
      panBarEst = new javax.swing.JPanel();
      lblMsgSis = new javax.swing.JLabel();
      panPrgSis = new javax.swing.JPanel();
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

      lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
      lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      lblTit.setText("Título de la ventana");
      panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

      panFil.setLayout(new java.awt.BorderLayout());

      panFilFec.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de corte"));
      panFilFec.setPreferredSize(new java.awt.Dimension(100, 150));
      panFilFec.setLayout(null);

      optDia.setText("Día");
      optDia.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            optDiaActionPerformed(evt);
         }
      });
      panFilFec.add(optDia);
      optDia.setBounds(53, 20, 50, 20);

      optMes.setSelected(true);
      optMes.setText("Mes");
      optMes.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            optMesStateChanged(evt);
         }
      });
      optMes.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            optMesActionPerformed(evt);
         }
      });
      panFilFec.add(optMes);
      optMes.setBounds(53, 42, 50, 20);
      panFilFec.add(cboMesCor);
      cboMesCor.setBounds(110, 42, 180, 20);
      panFilFec.add(cboAnoCor);
      cboAnoCor.setBounds(292, 42, 64, 20);

      panFil.add(panFilFec, java.awt.BorderLayout.CENTER);

      panFil2.setPreferredSize(new java.awt.Dimension(100, 240));
      panFil2.setLayout(null);

      lblNiv.setText("Nivel:");
      panFil2.add(lblNiv);
      lblNiv.setBounds(10, 6, 100, 20);
      panFil2.add(cboNiv);
      cboNiv.setBounds(110, 6, 180, 20);

      chkMerVal.setText("Mostrar formato de Plan de Cuentas de Mercado de Valores.");
      chkMerVal.setToolTipText("Si está seleccionado muestra el Formato seleccionado, caso contrario muestra el plan de cuentas normal de la empresa.");
      chkMerVal.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            chkMerValStateChanged(evt);
         }
      });
      panFil2.add(chkMerVal);
      chkMerVal.setBounds(10, 40, 500, 23);

      chkSalCer.setSelected(true);
      chkSalCer.setText("No incluir cuentas con saldo cero");
      panFil2.add(chkSalCer);
      chkSalCer.setBounds(10, 70, 290, 23);

      chkRemayo.setText("Remayorizar");
      panFil2.add(chkRemayo);
      chkRemayo.setBounds(10, 100, 290, 23);
      chkRemayo.getAccessibleContext().setAccessibleName("");

      panFil.add(panFil2, java.awt.BorderLayout.SOUTH);

      tabFrm.addTab("Filtro", panFil);

      panRpt.setLayout(new java.awt.BorderLayout());

      panRptReg.setLayout(new java.awt.BorderLayout(0, 1));

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
      spnDat.setViewportView(tblDat);

      panRptReg.add(spnDat, java.awt.BorderLayout.CENTER);

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

      panRptReg.add(spnTot, java.awt.BorderLayout.SOUTH);

      panRpt.add(panRptReg, java.awt.BorderLayout.CENTER);

      tabFrm.addTab("Reporte", panRpt);

      panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

      panBar.setLayout(new java.awt.BorderLayout());

      panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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

      butImp.setText("Imprimir");
      butImp.setPreferredSize(new java.awt.Dimension(92, 25));
      butImp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butImpActionPerformed(evt);
         }
      });
      panBot.add(butImp);

      butExp.setText("Exportar");
      butExp.setToolTipText("Exporta datos de acuerdo al filtro especificado.");
      butExp.setPreferredSize(new java.awt.Dimension(92, 25));
      butExp.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butExpActionPerformed(evt);
         }
      });
      panBot.add(butExp);

      panBar.add(panBot, java.awt.BorderLayout.CENTER);

      panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
      panBarEst.setLayout(new java.awt.BorderLayout());

      lblMsgSis.setText("Listo");
      lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

      panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
      panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
      panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

      pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      pgrSis.setBorderPainted(false);
      pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
      pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
      panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

      panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

      panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

      panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

      getContentPane().add(panFrm);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
   }// </editor-fold>//GEN-END:initComponents

    private boolean isProRep() 
    {
//            if(!(imprimir())){
//                mostrarMsgInf("<HTML>El reporte no se pudo generar correctamente<BR>Intentelo nuevamente.</HTML>");
//                return false;
//            }   
//            return true;
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;            
        }    
    
    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        if(chkMerVal.isSelected())
        {
            mostrarMsgInf("La opción de Imprimir no está habilitada para el formato Mercado de Valores.");
        }     
        else
        {
               isProRep();
        }
    }//GEN-LAST:event_butImpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
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
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="áEstá seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void optMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optMesActionPerformed
        if(optMes.isSelected())
            optDia.setSelected(false);
        else
            optDia.setSelected(true);
    }//GEN-LAST:event_optMesActionPerformed

    private void optDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDiaActionPerformed
        if(optDia.isSelected())
            optMes.setSelected(false);
        else
            optMes.setSelected(true);
    }//GEN-LAST:event_optDiaActionPerformed

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        if(chkMerVal.isSelected())
        {
            //Realizar acción de acuerdo a la etiqueta del botón ("Exportar" o "Detener").
            if (butExp.getText().equals("Exportar")) 
            {
                blnButExp = true;
                if (objThrGUIExp == null) 
                {
                    objThrGUIExp = new ZafThreadGUIExportar();
                    objThrGUIExp.start();
                }
            }
            else 
            {
                blnButExp = false;
            }
        
        }
        else
        {
            mostrarMsgInf("La opción de Exportar sólo está habilitada para el formato Mercado de Valores.");
        }
       
    }//GEN-LAST:event_butExpActionPerformed

    private void chkMerValStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMerValStateChanged
        if(chkMerVal.isSelected())
        {
            optMes.setSelected(true);
            optDia.setSelected(false);
            optDia.setEnabled(false);
            butImp.setEnabled(false);
            butExp.setEnabled(true);
            limpiarJtableDat();
        }
        else
        {
            optDia.setEnabled(true);
            butImp.setEnabled(true);
            butExp.setEnabled(false);
        }
    }//GEN-LAST:event_chkMerValStateChanged

    private void optMesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optMesStateChanged
        if(chkMerVal.isSelected())
        {
            optMes.setSelected(true);
            optDia.setSelected(false);
        }
    }//GEN-LAST:event_optMesStateChanged

    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton butCer;
   private javax.swing.JButton butCon;
   private javax.swing.JButton butExp;
   private javax.swing.JButton butImp;
   private javax.swing.JComboBox cboAnoCor;
   private javax.swing.JComboBox cboMesCor;
   private javax.swing.JComboBox cboNiv;
   private javax.swing.JCheckBox chkMerVal;
   private javax.swing.JCheckBox chkRemayo;
   private javax.swing.JCheckBox chkSalCer;
   private javax.swing.JLabel lblMsgSis;
   private javax.swing.JLabel lblNiv;
   private javax.swing.JLabel lblTit;
   private javax.swing.JRadioButton optDia;
   private javax.swing.JRadioButton optMes;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panBarEst;
   private javax.swing.JPanel panBot;
   private javax.swing.JPanel panFil;
   private javax.swing.JPanel panFil2;
   private javax.swing.JPanel panFilFec;
   private javax.swing.JPanel panFrm;
   private javax.swing.JPanel panPrgSis;
   private javax.swing.JPanel panRpt;
   private javax.swing.JPanel panRptReg;
   private javax.swing.JProgressBar pgrSis;
   private javax.swing.JScrollPane spnDat;
   private javax.swing.JScrollPane spnTot;
   private javax.swing.JTabbedPane tabFrm;
   private javax.swing.JTable tblDat;
   private javax.swing.JTable tblTot;
   // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar el combo "Mes de corte".
            cboMesCor.addItem("Enero");
            cboMesCor.addItem("Febrero");
            cboMesCor.addItem("Marzo");
            cboMesCor.addItem("Abril");
            cboMesCor.addItem("Mayo");
            cboMesCor.addItem("Junio");
            cboMesCor.addItem("Julio");
            cboMesCor.addItem("Agosto");
            cboMesCor.addItem("Septiembre");
            cboMesCor.addItem("Octubre");
            cboMesCor.addItem("Noviembre");
            cboMesCor.addItem("Diciembre");
            cboMesCor.setSelectedIndex(0);
//            //Configurar el combo "Aáo de corte".
//            cboAnoCor.addItem("2005");
//            cboAnoCor.addItem("2006");
//            cboAnoCor.addItem("2007");
//            cboAnoCor.addItem("2008");
//            cboAnoCor.addItem("2009");
//            cboAnoCor.addItem("2010");
//            cboAnoCor.setSelectedIndex(4);
            
            cargarAnioCreados();
            
            
            
            //Configurar el combo "Nivel".
            cboNiv.addItem("1");
            cboNiv.addItem("2");
            cboNiv.addItem("3");
            cboNiv.addItem("4");
            cboNiv.addItem("5");
            cboNiv.addItem("6");
            cboNiv.addItem("7"); 
            cboNiv.setSelectedIndex(6);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.9.5 ");
            lblTit.setText(strAux);
            //Configurar objetos.
//            txtCodItm.setVisible(false);
            //Configurar los JTables.
            configurarTblDat();


            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            
            panFilFec.add(dtpFecDoc);
            dtpFecDoc.setBounds(110, 20, 120, 20);


            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

                        

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura el JTable "tblDat".
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
            vecCab=new Vector(9);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_NIV_CTA,"Nivel");
            vecCab.add(INT_TBL_DAT_PAD_CTA,"Cód.Cta.Pad.");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre");
            vecCab.add(INT_TBL_DAT_SAL_MEN,"Sal.Men.");
            vecCab.add(INT_TBL_DAT_SAL_ACU,"Sal.Acu.");
            vecCab.add(INT_TBL_DAT_TIP_CTA,"Tip.Cta.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(377);
            tcmAux.getColumn(INT_TBL_DAT_SAL_MEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_SAL_ACU).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIP_CTA).setPreferredWidth(40);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setResizable(false);
//
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setResizable(false);
//
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setResizable(false);


            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NIV_CTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PAD_CTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_CTA, tblDat);


            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_SAL_MEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_ACU).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_SAL_MEN, INT_TBL_DAT_SAL_ACU};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);                      

            
            if (objParSis.getCodigoMenu()==342){
                strAux="Diferencias";
                objTblTot.setValueAt(""+strAux,0,INT_TBL_DAT_NOM_CTA);
            }
            else{
                strAux="Utilidad/Pérdida";
                objTblTot.setValueAt(""+strAux,0,INT_TBL_DAT_NOM_CTA);


                if(objParSis.getCodigoUsuario()==1){
                    objTblTot.insertMoreRows(2);
                    spnTot.setPreferredSize(new java.awt.Dimension(454, 50));
                }
                else{
                    if(objPerUsr.isOpcionEnabled(2764)){
                        objTblTot.insertMoreRows(2);
                        spnTot.setPreferredSize(new java.awt.Dimension(454, 50));
                    }
                }

            }
            
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
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNiv, j;
        double dblSalMen, dblSalAcu, dblTotMen, dblTotAcu;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condicián.
                strAux="";
                if(chkSalCer.isSelected())
                {
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strAux+=" HAVING SUM(c1.nd_salMen)<>0 OR SUM(c1.nd_salAcu)<>0";
                    else
                        strAux+=" AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                }
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                
                //INICIO: Mejorar esto:
                strFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), "yyyy-MM-dd");
                //  objUti.remayorizar(this, objParSis, obtienFechRemayor(strFecAux), strFecAux, "yyyy-MM-dd");
                //FIN: Mejorar esto.
                
                //<editor-fold defaultstate="collapsed" desc="/* GRUPO */">
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: MES */">
                    if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
                    {
                        //Obtener los datos del "Grupo".
                        strSQL="";
                        strSQL+="SELECT c1.ne_niv, c1.tx_codCta, c1.tx_desLar, SUM(c1.nd_salMen) AS nd_salMen, SUM(c1.nd_salAcu) AS nd_salAcu, c1.tx_tipCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN (";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_salCta AS b1";
                        
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" WHERE b1.co_per<=" + strFecCor;
                        else  //Estado de Resultados.
                            strSQL+=" WHERE (b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor + ")";
                        
                        //Excluir las cuentas de "Bodega" de la empresas.
                        // strSQL+=" AND NOT ( (b1.co_emp=1 AND b1.co_cta=407) OR (b1.co_emp=1 AND b1.co_cta=408) OR (b1.co_emp=1 AND b1.co_cta=409) OR (b1.co_emp=1 AND b1.co_cta=410) OR (b1.co_emp=2 AND b1.co_cta=74) OR (b1.co_emp=2 AND b1.co_cta=75) OR (b1.co_emp=2 AND b1.co_cta=76) OR (b1.co_emp=3 AND b1.co_cta=357) OR (b1.co_emp=3 AND b1.co_cta=358) OR (b1.co_emp=3 AND b1.co_cta=359) OR (b1.co_emp=3 AND b1.co_cta=360) OR (b1.co_emp=4 AND b1.co_cta=381) OR (b1.co_emp=4 AND b1.co_cta=383) OR (b1.co_emp=4 AND b1.co_cta=384) )";
                        strSQL+=" AND NOT ( (b1.co_emp=1 AND b1.co_cta=406) OR (b1.co_emp=1 AND b1.co_cta=407) OR (b1.co_emp=1 AND b1.co_cta=408) OR (b1.co_emp=1 AND b1.co_cta=409) OR (b1.co_emp=1 AND b1.co_cta=410) OR (b1.co_emp=2 AND b1.co_cta=73) OR (b1.co_emp=2 AND b1.co_cta=74) OR (b1.co_emp=2 AND b1.co_cta=75) OR (b1.co_emp=2 AND b1.co_cta=76) OR (b1.co_emp=3 AND b1.co_cta=356) OR (b1.co_emp=3 AND b1.co_cta=357) OR (b1.co_emp=3 AND b1.co_cta=358) OR (b1.co_emp=3 AND b1.co_cta=359) OR (b1.co_emp=3 AND b1.co_cta=360) OR (b1.co_emp=4 AND b1.co_cta=380) OR (b1.co_emp=4 AND b1.co_cta=381) OR (b1.co_emp=4 AND b1.co_cta=383) OR (b1.co_emp=4 AND b1.co_cta=384) )";
                        //Excluir las cuentas de "Costo de venta" de la empresas.
                        //strSQL+=" AND NOT ( (b1.co_emp=1 AND b1.co_cta=1447) OR (b1.co_emp=2 AND b1.co_cta=559) OR (b1.co_emp=3 AND b1.co_cta=1346) OR (b1.co_emp=4 AND b1.co_cta=1386) )";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta";
                        strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" WHERE a2.co_per=" + strFecCor;
                        strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                        
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        else //Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=") AS c1";
                        strSQL+=" GROUP BY c1.ne_niv, c1.tx_codCta, c1.tx_desLar, c1.tx_tipCta";
                        strSQL+=strAux;
                        strSQL+=" ORDER BY c1.tx_codCta"; 
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: DIA */">
                    else  //a una fecha que no es mes completo Ejm. 2006-01-12
                    {
                        strSQL="";
                        strSQL+="SELECT 0 AS co_emp, e1.ne_niv, e1.tx_codCta, e1.tx_desLar, e1.tx_tipCta, SUM(nd_salAcu) AS nd_salAcu, SUM(nd_salMen) AS nd_salMen";
                        strSQL+=" FROM(";
                        strSQL+="SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, b1.tx_tipCta";
                        strSQL+=" , ( (CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b3.nd_salCta IS NULL THEN 0 ELSE b3.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salAcu";
                        strSQL+=" 		, ((CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salMen";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, 0 AS nd_salCta";
                        strSQL+=" 	FROM tbm_plaCta AS a1 WHERE a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="       AND a1.st_reg NOT IN('I','E') ";
                        
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+="         AND a1.tx_niv1 IN ('1', '2', '3')";
                        else   //Estado de Resultados.
                            strSQL+="         AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=" 	ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b1";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 		 SELECT b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 			, SUM(salAct) AS nd_salCta FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct";
                        strSQL+=" 			FROM tbm_cabdia as a1 ";
                        strSQL+=" 			INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        
                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND (a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "')";
                        
                        strSQL+=" 			and a1.st_reg='A' AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 		 ) AS b2";
                        strSQL+=" 		 GROUP BY b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 		 ORDER BY b2.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 	    SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a3.nd_salCta AS nd_salCta";
                        strSQL+=" 	     FROM tbm_plaCta AS a1";
                        strSQL+=" 	     INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" 	     INNER JOIN (";
                        strSQL+=" 	     SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                        strSQL+=" 	     FROM tbm_salCta AS b1";
                        strSQL+=" 	     WHERE b1.co_emp<>" + objParSis.getCodigoEmpresa() + "";

                        if (objParSis.getCodigoMenu()==342)  //Balance General.
                            strSQL+=" AND b1.co_per<=" + getPeriodoAnterior() + "";  //200512
                        else  //Estado de Resultados.
                        {
                            strSQL+=" AND (b1.co_per>=" + getPeriodoInicialEstadoResultados() + " AND b1.co_per<" + getPeriodoFinalEstadoResultados() + ")"; 
                            //strSQL+=" AND (b1.co_per>=200601 AND b1.co_per<200602)";
                        }   
                        
                        strSQL+=" 	     GROUP BY b1.co_emp, b1.co_cta";
                        strSQL+=" 	     ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" 	     WHERE a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	     AND a2.co_per=" + getPeriodoAnterior() + "";    //periodos menores a la fecha de corte
                        strSQL+=" 	     AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1) + "";

                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        else  //Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";

                        strSQL+="                  AND a1.tx_tipCta='D'";
                        strSQL+=" 	     AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                        strSQL+=" 	     ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_emp=b3.co_emp AND b1.co_cta=b3.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, SUM(b2.nd_salCta) AS nd_salCta FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_ctaRes AS co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar";
                        strSQL+=" 	FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaRes=a2.co_cta";
                        strSQL+=" 	WHERE a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as nd_salCta";
                        strSQL+=" 			FROM tbm_cabdia as a1 INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";

                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//solo el mes del corte Ejm: corte al 2008/01/15   -> between 2008/01/01 and 2008/01/15

                        strSQL+=" 			and a1.st_reg='A'";
                        strSQL+="                         AND a4.tx_niv1 IN ('4', '5', '6', '7', '8')";
                        strSQL+="                         AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			ORDER BY a4.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar";
                        strSQL+=" ) AS b4";
                        strSQL+=" ON b1.co_emp=b4.co_emp AND b1.co_cta=b4.co_cta";

                        strSQL+=" ORDER BY b1.tx_codCta";
                        strSQL+=" ) AS e1";

                        //strSQL+=" WHERE e1.tx_codCta LIKE '8.01.06%'";

                        strSQL+=" GROUP BY e1.ne_niv, e1.tx_codCta, e1.tx_desLar, e1.tx_tipCta";
                        strSQL+=" ORDER BY e1.tx_codCta";
                    }
                    //</editor-fold>
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="/* EMPRESAS */">
                else
                {
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: MES */">
                    if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        //strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                        strSQL+=" SELECT a1.co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar, a1.tx_tipCta, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN (";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_salCta AS b1";
                        strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        
                        if (objParSis.getCodigoMenu()==342)  //Balance General.
                            strSQL+=" AND b1.co_per<=" + strFecCor;
                        else //Estado de Resultados.
                            strSQL+=" AND (b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor + ")";
                        
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta";
                        strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_per=" + strFecCor;
                        //strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                        strSQL+=" AND a2.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                        
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        else //Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=strAux;
                        //strSQL+=" ORDER BY a1.tx_codCta";
                        strSQL+=" ORDER BY a2.tx_codCta";
                        rst=stm.executeQuery(strSQL);
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: DIA */">
                    else //a una fecha que no es mes completo Ejm. 2006-01-12
                    {
                        strSQL="";
                        strSQL+="SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, b1.tx_tipCta";
                        strSQL+=" , ( (CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b3.nd_salCta IS NULL THEN 0 ELSE b3.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salAcu";
                        strSQL+=" 		, ((CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salMen";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, 0 AS nd_salCta";
                        strSQL+=" 	FROM tbm_plaCta AS a1 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="         AND a1.st_reg NOT IN('I','E') ";
                        if (objParSis.getCodigoMenu()==342){//Balance General.
                            strSQL+="         AND a1.tx_niv1 IN ('1', '2', '3')";
                        }
                        else{//Estado de Resultados.
                            strSQL+="         AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        }
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=" 	ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b1";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 		 SELECT b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 			, SUM(salAct) AS nd_salCta FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct";
                        strSQL+=" 			FROM tbm_cabdia as a1 ";
                        strSQL+=" 			INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND (a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "')";
                        strSQL+=" 			and a1.st_reg='A' AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 		 ) AS b2";
                        strSQL+=" 		 GROUP BY b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 		 ORDER BY b2.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 	    SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a3.nd_salCta AS nd_salCta";
                        strSQL+=" 	     FROM tbm_plaCta AS a1";
                        strSQL+=" 	     INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" 	     INNER JOIN (";
                        strSQL+=" 	     SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                        strSQL+=" 	     FROM tbm_salCta AS b1";
                        strSQL+=" 	     WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";

                        if (objParSis.getCodigoMenu()==342){//Balance General.
                            strSQL+=" AND b1.co_per<=" + getPeriodoAnterior() + "";//200512
                        }
                        else{//Estado de Resultados.
                            strSQL+=" AND (b1.co_per>=" + getPeriodoInicialEstadoResultados() + " AND b1.co_per<" + getPeriodoFinalEstadoResultados() + ")";
                            //strSQL+=" AND (b1.co_per>=200601 AND b1.co_per<200602)";
                        }
                        strSQL+=" 	     GROUP BY b1.co_emp, b1.co_cta";
                        strSQL+=" 	     ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" 	     WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	     AND a2.co_per=" + getPeriodoAnterior() + "";//periodos menores a la fecha de corte
                        strSQL+=" 	     AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1) + "";

                        if (objParSis.getCodigoMenu()==342){//Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        }
                        else{//Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        }
                        strSQL+="                  AND a1.tx_tipCta='D'";
                        strSQL+=" 	     AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                        strSQL+=" 	     ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_emp=b3.co_emp AND b1.co_cta=b3.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, SUM(b2.nd_salCta) AS nd_salCta FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_ctaRes AS co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar";
                        strSQL+=" 	FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaRes=a2.co_cta";
                        strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as nd_salCta";
                        strSQL+=" 			FROM tbm_cabdia as a1 INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";


                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//solo el mes del corte Ejm: corte al 2008/01/15   -> between 2008/01/01 and 2008/01/15

                        strSQL+=" 			and a1.st_reg='A'";
                        strSQL+="                         AND a4.tx_niv1 IN ('4', '5', '6', '7', '8')";
                        strSQL+="                         AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			ORDER BY a4.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar";
                        strSQL+=" ) AS b4";
                        strSQL+=" ON b1.co_emp=b4.co_emp AND b1.co_cta=b4.co_cta";

                        strSQL+=" ORDER BY b1.tx_codCta";
                    }
                    //</editor-fold>
                }
                //</editor-fold>
                System.out.println("SQL-BG/ER:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                dblTotMen=0;
                dblTotAcu=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            vecReg.add(INT_TBL_DAT_COD_SIS,null);
                        else
                            vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_cta"));
                        intNiv=rst.getInt("ne_niv");
                        vecReg.add(INT_TBL_DAT_NIV_CTA,"" + intNiv);
                        
                        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            vecReg.add(INT_TBL_DAT_PAD_CTA,null);
                        else
                            vecReg.add(INT_TBL_DAT_PAD_CTA,rst.getString("ne_pad"));
                        
                        vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("tx_codCta"));
                        
                        //Generar la tabulación para el campo "tx_desLar".
                        strAux="";
                        for (j=1; j<intNiv; j++)
                            strAux+="        ";
                        vecReg.add(INT_TBL_DAT_NOM_CTA,strAux + rst.getString("tx_desLar"));
                        dblSalMen=rst.getDouble("nd_salMen");
                        dblSalAcu=rst.getDouble("nd_salAcu");
                        vecReg.add(INT_TBL_DAT_SAL_MEN,"" + dblSalMen);
                        vecReg.add(INT_TBL_DAT_SAL_ACU,"" + dblSalAcu);
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            vecReg.add(INT_TBL_DAT_TIP_CTA,"N");
                        else
                            vecReg.add(INT_TBL_DAT_TIP_CTA,"" + rst.getString("tx_tipCta"));

                        if (intNiv==1)
                        {
                            System.out.println("si");
                            dblTotMen+=dblSalMen;
                            dblTotAcu+=dblSalAcu;
                        }
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
//                //Calcular totales.
//                objTblTot.setValueAt("" + dblTotMen, 0, INT_TBL_DAT_SAL_MEN);
//                objTblTot.setValueAt("" + dblTotAcu, 0, INT_TBL_DAT_SAL_ACU);
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);

                if( ! optMes.isSelected())
                {
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(generaSaldosCuentasPadresGrupo()){
                            if(chkSalCer.isSelected()){
                                if(quitarCtasSaldoCero()){
                                    if(calculaTotales()){

                                    }
                                }
                            }
                        }
                    }
                    else{
                        if(generaSaldosCuentasPadres()){
                            if(chkSalCer.isSelected()){
                                if(quitarCtasSaldoCero()){
                                    if(calculaTotales()){

                                    }
                                }
                            }
                        }
                    }
                }

                if(optMes.isSelected())
                {
                    //Calcular totales.
                    objTblTot.setValueAt("" + dblTotMen, 0, INT_TBL_DAT_SAL_MEN);
                    objTblTot.setValueAt("" + dblTotAcu, 0, INT_TBL_DAT_SAL_ACU);
                    if(objParSis.getCodigoMenu()==343){
                        if(objPerUsr.isOpcionEnabled(2764)){
                            if(!cargarCuentaBalance()){
                                mostrarMsgInf("La información de la cuenta de balance no se generó correctamente.");
                            }
                        }
                    }
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
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * "Formato: Mercado de Valores"
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegMerVal()
    {
        int intNiv, j;
        double dblSalMen, dblSalAcu, dblTotMen, dblTotAcu;
        java.sql.Statement  stmLocAux=null;
        java.sql.ResultSet  rstLocAux=null;
        boolean blnRes=true;
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
                strAux="";
                if(chkSalCer.isSelected())
                {
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strAux+=" HAVING SUM(c1.nd_salMen)<>0 OR SUM(c1.nd_salAcu)<>0";
                    else
                        strAux+=" AND (a3.nd_salCta<>0 OR a4.nd_salCta<>0)";
                }
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                
                //INICIO: Mejorar esto:
                strFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), "yyyy-MM-dd");
                //  objUti.remayorizar(this, objParSis, obtienFechRemayor(strFecAux), strFecAux, "yyyy-MM-dd");
                //FIN: Mejorar esto.
                
                //<editor-fold defaultstate="collapsed" desc="/* GRUPO */">
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: MES */">
                    if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
                    {
                        //Obtener los datos del "Grupo".
                        strSQL="";
                        strSQL+="SELECT c1.ne_niv, c1.tx_codCta, c1.tx_desLar, SUM(c1.nd_salMen) AS nd_salMen, SUM(c1.nd_salAcu) AS nd_salAcu, c1.tx_tipCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN (";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_salCta AS b1";
                        
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" WHERE b1.co_per<=" + strFecCor;
                        else  //Estado de Resultados.
                            strSQL+=" WHERE (b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor + ")";
                        
                        //Excluir las cuentas de "Bodega" de la empresas.
                        // strSQL+=" AND NOT ( (b1.co_emp=1 AND b1.co_cta=407) OR (b1.co_emp=1 AND b1.co_cta=408) OR (b1.co_emp=1 AND b1.co_cta=409) OR (b1.co_emp=1 AND b1.co_cta=410) OR (b1.co_emp=2 AND b1.co_cta=74) OR (b1.co_emp=2 AND b1.co_cta=75) OR (b1.co_emp=2 AND b1.co_cta=76) OR (b1.co_emp=3 AND b1.co_cta=357) OR (b1.co_emp=3 AND b1.co_cta=358) OR (b1.co_emp=3 AND b1.co_cta=359) OR (b1.co_emp=3 AND b1.co_cta=360) OR (b1.co_emp=4 AND b1.co_cta=381) OR (b1.co_emp=4 AND b1.co_cta=383) OR (b1.co_emp=4 AND b1.co_cta=384) )";
                        strSQL+=" AND NOT ( (b1.co_emp=1 AND b1.co_cta=406) OR (b1.co_emp=1 AND b1.co_cta=407) OR (b1.co_emp=1 AND b1.co_cta=408) OR (b1.co_emp=1 AND b1.co_cta=409) OR (b1.co_emp=1 AND b1.co_cta=410) OR (b1.co_emp=2 AND b1.co_cta=73) OR (b1.co_emp=2 AND b1.co_cta=74) OR (b1.co_emp=2 AND b1.co_cta=75) OR (b1.co_emp=2 AND b1.co_cta=76) OR (b1.co_emp=3 AND b1.co_cta=356) OR (b1.co_emp=3 AND b1.co_cta=357) OR (b1.co_emp=3 AND b1.co_cta=358) OR (b1.co_emp=3 AND b1.co_cta=359) OR (b1.co_emp=3 AND b1.co_cta=360) OR (b1.co_emp=4 AND b1.co_cta=380) OR (b1.co_emp=4 AND b1.co_cta=381) OR (b1.co_emp=4 AND b1.co_cta=383) OR (b1.co_emp=4 AND b1.co_cta=384) )";
                        //Excluir las cuentas de "Costo de venta" de la empresas.
                        strSQL+=" AND NOT ( (b1.co_emp=1 AND b1.co_cta=1447) OR (b1.co_emp=2 AND b1.co_cta=559) OR (b1.co_emp=3 AND b1.co_cta=1346) OR (b1.co_emp=4 AND b1.co_cta=1386) )";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta";
                        strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" WHERE a2.co_per=" + strFecCor;
                        strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                        
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        else //Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=") AS c1";
                        strSQL+=" GROUP BY c1.ne_niv, c1.tx_codCta, c1.tx_desLar, c1.tx_tipCta";
                        strSQL+=strAux;
                        strSQL+=" ORDER BY c1.tx_codCta"; 
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: DIA */">
                    else  //a una fecha que no es mes completo Ejm. 2006-01-12
                    {
                        strSQL="";
                        strSQL+="SELECT 0 AS co_emp, e1.ne_niv, e1.tx_codCta, e1.tx_desLar, e1.tx_tipCta, SUM(nd_salAcu) AS nd_salAcu, SUM(nd_salMen) AS nd_salMen";
                        strSQL+=" FROM(";
                        strSQL+="SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, b1.tx_tipCta";
                        strSQL+=" , ( (CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b3.nd_salCta IS NULL THEN 0 ELSE b3.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salAcu";
                        strSQL+=" 		, ((CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salMen";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, 0 AS nd_salCta";
                        strSQL+=" 	FROM tbm_plaCta AS a1 WHERE a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="       AND a1.st_reg NOT IN('I','E') ";
                        
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+="         AND a1.tx_niv1 IN ('1', '2', '3')";
                        else   //Estado de Resultados.
                            strSQL+="         AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=" 	ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b1";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 		 SELECT b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 			, SUM(salAct) AS nd_salCta FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct";
                        strSQL+=" 			FROM tbm_cabdia as a1 ";
                        strSQL+=" 			INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        
                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND (a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "')";
                        
                        strSQL+=" 			and a1.st_reg='A' AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 		 ) AS b2";
                        strSQL+=" 		 GROUP BY b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 		 ORDER BY b2.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 	    SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a3.nd_salCta AS nd_salCta";
                        strSQL+=" 	     FROM tbm_plaCta AS a1";
                        strSQL+=" 	     INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" 	     INNER JOIN (";
                        strSQL+=" 	     SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                        strSQL+=" 	     FROM tbm_salCta AS b1";
                        strSQL+=" 	     WHERE b1.co_emp<>" + objParSis.getCodigoEmpresa() + "";

                        if (objParSis.getCodigoMenu()==342)  //Balance General.
                            strSQL+=" AND b1.co_per<=" + getPeriodoAnterior() + "";  //200512
                        else  //Estado de Resultados.
                        {
                            strSQL+=" AND (b1.co_per>=" + getPeriodoInicialEstadoResultados() + " AND b1.co_per<" + getPeriodoFinalEstadoResultados() + ")"; 
                            //strSQL+=" AND (b1.co_per>=200601 AND b1.co_per<200602)";
                        }   
                        
                        strSQL+=" 	     GROUP BY b1.co_emp, b1.co_cta";
                        strSQL+=" 	     ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" 	     WHERE a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	     AND a2.co_per=" + getPeriodoAnterior() + "";    //periodos menores a la fecha de corte
                        strSQL+=" 	     AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1) + "";

                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        else  //Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";

                        strSQL+="                  AND a1.tx_tipCta='D'";
                        strSQL+=" 	     AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                        strSQL+=" 	     ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_emp=b3.co_emp AND b1.co_cta=b3.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, SUM(b2.nd_salCta) AS nd_salCta FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_ctaRes AS co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar";
                        strSQL+=" 	FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaRes=a2.co_cta";
                        strSQL+=" 	WHERE a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as nd_salCta";
                        strSQL+=" 			FROM tbm_cabdia as a1 INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp<>" + objParSis.getCodigoEmpresa() + "";

                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//solo el mes del corte Ejm: corte al 2008/01/15   -> between 2008/01/01 and 2008/01/15

                        strSQL+=" 			and a1.st_reg='A'";
                        strSQL+="                         AND a4.tx_niv1 IN ('4', '5', '6', '7', '8')";
                        strSQL+="                         AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			ORDER BY a4.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar";
                        strSQL+=" ) AS b4";
                        strSQL+=" ON b1.co_emp=b4.co_emp AND b1.co_cta=b4.co_cta";

                        strSQL+=" ORDER BY b1.tx_codCta";
                        strSQL+=" ) AS e1";

                        //strSQL+=" WHERE e1.tx_codCta LIKE '8.01.06%'";

                        strSQL+=" GROUP BY e1.ne_niv, e1.tx_codCta, e1.tx_desLar, e1.tx_tipCta";
                        strSQL+=" ORDER BY e1.tx_codCta";
                    }
                    //</editor-fold>
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="/* EMPRESAS */">
                else
                {
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: MES */">
                    if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a4.nd_salCta as nd_salMen, a3.nd_salCta as nd_salAcu, ";
                        strSQL+="        (substring(a1.tx_codCta from 1 for 1 ) || '.' || substring(a1.tx_codCta from 2 for 100 )) as tx_codCta2 ";     
                        strSQL+=" FROM tbm_detForplaCta AS a1 ";
                        strSQL+=" INNER JOIN ";
                        strSQL+=" ( ";
                        strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta ";
                        strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
                        strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+="    AND b1.co_for=1 ";
                        if (objParSis.getCodigoMenu()==342)   //Balance General.
                            strSQL+=" AND b1.co_per<=" + strFecCor;
                        else  //Estado de Resultados.
                            strSQL+=" AND (b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor + ")";
                        //strSQL+="    AND b1.nd_SalCta>0    ";
                        strSQL+="    GROUP BY b1.co_emp, b1.co_for, b1.co_cta ";
                        strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_for=a3.co_for AND a1.co_cta=a3.co_cta) ";
                        strSQL+=" INNER JOIN ";
                        strSQL+=" ( ";
                        strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, b1.nd_salCta ";
                        strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
                        strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+="    AND b1.co_for=1 ";
                        strSQL+="    AND b1.co_per=" + strFecCor;
                        strSQL+=" ) AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_for=a4.co_for AND a1.co_cta=a4.co_cta) ";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_For=1 ";
                        strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        else //Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        
                        if(chkSalCer.isSelected())
                            strSQL+=" AND (a3.nd_salCta<>0 OR a4.nd_salCta<>0)";
                        strSQL+=" AND a1.tx_tipCta = 'C' ";
                        strSQL+=" ORDER BY tx_codCta2 ";
                  
                        rst=stm.executeQuery(strSQL);
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="/* Filtro: DIA */">
                    else //a una fecha que no es mes completo Ejm. 2006-01-12
                    {
                        strSQL="";
                        strSQL+="SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, b1.tx_tipCta";
                        strSQL+=" , ( (CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b3.nd_salCta IS NULL THEN 0 ELSE b3.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salAcu";
                        strSQL+=" 		, ((CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END)";
                        strSQL+=" 		+ (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salMen";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, 0 AS nd_salCta";
                        strSQL+=" 	FROM tbm_plaCta AS a1 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="         AND a1.st_reg NOT IN('I','E') ";
                        if (objParSis.getCodigoMenu()==342){//Balance General.
                            strSQL+="         AND a1.tx_niv1 IN ('1', '2', '3')";
                        }
                        else{//Estado de Resultados.
                            strSQL+="         AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        }
                        strSQL+=" AND a1.st_reg='A'";
                        strSQL+=" 	ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b1";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 		 SELECT b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 			, SUM(salAct) AS nd_salCta FROM(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct";
                        strSQL+=" 			FROM tbm_cabdia as a1 ";
                        strSQL+=" 			INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND (a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "')";
                        strSQL+=" 			and a1.st_reg='A' AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 		 ) AS b2";
                        strSQL+=" 		 GROUP BY b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar";
                        strSQL+=" 		 ORDER BY b2.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" 	    SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a3.nd_salCta AS nd_salCta";
                        strSQL+=" 	     FROM tbm_plaCta AS a1";
                        strSQL+=" 	     INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" 	     INNER JOIN (";
                        strSQL+=" 	     SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                        strSQL+=" 	     FROM tbm_salCta AS b1";
                        strSQL+=" 	     WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";

                        if (objParSis.getCodigoMenu()==342){//Balance General.
                            strSQL+=" AND b1.co_per<=" + getPeriodoAnterior() + "";//200512
                        }
                        else{//Estado de Resultados.
                            strSQL+=" AND (b1.co_per>=" + getPeriodoInicialEstadoResultados() + " AND b1.co_per<" + getPeriodoFinalEstadoResultados() + ")";
                            //strSQL+=" AND (b1.co_per>=200601 AND b1.co_per<200602)";
                        }
                        strSQL+=" 	     GROUP BY b1.co_emp, b1.co_cta";
                        strSQL+=" 	     ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                        strSQL+=" 	     WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	     AND a2.co_per=" + getPeriodoAnterior() + "";//periodos menores a la fecha de corte
                        strSQL+=" 	     AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1) + "";

                        if (objParSis.getCodigoMenu()==342){//Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        }
                        else{//Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        }
                        strSQL+="                  AND a1.tx_tipCta='D'";
                        strSQL+=" 	     AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                        strSQL+=" 	     ORDER BY a1.tx_codCta";
                        strSQL+=" ) AS b3";
                        strSQL+=" ON b1.co_emp=b3.co_emp AND b1.co_cta=b3.co_cta";
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+=" SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar, SUM(b2.nd_salCta) AS nd_salCta FROM(";
                        strSQL+=" 	SELECT a1.co_emp, a1.co_ctaRes AS co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar";
                        strSQL+=" 	FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                        strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaRes=a2.co_cta";
                        strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 			SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as nd_salCta";
                        strSQL+=" 			FROM tbm_cabdia as a1 INNER JOIN tbm_detdia AS a2";
                        strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        strSQL+=" 			INNER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta";
                        strSQL+=" 			where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";


                        if (dtpFecDoc.isFecha())
                            strSQL+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//solo el mes del corte Ejm: corte al 2008/01/15   -> between 2008/01/01 and 2008/01/15

                        strSQL+=" 			and a1.st_reg='A'";
                        strSQL+="                         AND a4.tx_niv1 IN ('4', '5', '6', '7', '8')";
                        strSQL+="                         AND a4.tx_tipCta='D'";
                        strSQL+=" 			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar";
                        strSQL+=" 			ORDER BY a4.tx_codCta";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar";
                        strSQL+=" ) AS b4";
                        strSQL+=" ON b1.co_emp=b4.co_emp AND b1.co_cta=b4.co_cta";

                        strSQL+=" ORDER BY b1.tx_codCta";
                    }
                    //</editor-fold>
                }
                //</editor-fold>
                System.out.println("SQL-Cabecera:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                dblTotMen=0;
                dblTotAcu=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Cuentas Cabecera */">
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            vecReg.add(INT_TBL_DAT_COD_SIS,null);
                        else
                            vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_cta"));
                        intNiv=rst.getInt("ne_niv");
                        vecReg.add(INT_TBL_DAT_NIV_CTA,"" + intNiv);
                        
                        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            vecReg.add(INT_TBL_DAT_PAD_CTA,null);
                        else
                            vecReg.add(INT_TBL_DAT_PAD_CTA,rst.getString("ne_pad"));
                        
                        vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("tx_codCta"));
                        
                        //Generar la tabulacián para el campo "tx_desLar".
                        strAux="";
                        for (j=1; j<intNiv; j++)
                            strAux+="        ";
                        vecReg.add(INT_TBL_DAT_NOM_CTA,strAux + rst.getString("tx_desLar"));
                        dblSalMen=rst.getDouble("nd_salMen");
                        dblSalAcu=rst.getDouble("nd_salAcu");
                        vecReg.add(INT_TBL_DAT_SAL_MEN,"" + dblSalMen);
                        vecReg.add(INT_TBL_DAT_SAL_ACU,"" + dblSalAcu);
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            vecReg.add(INT_TBL_DAT_TIP_CTA,"N");
                        else
                            vecReg.add(INT_TBL_DAT_TIP_CTA,"" + rst.getString("tx_tipCta"));

                        if (intNiv==1)
                        {
                            System.out.println("si");
                            dblTotMen+=dblSalMen;
                            dblTotAcu+=dblSalAcu;
                        }
                        vecDat.add(vecReg);
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Cuentas de Detalle */">
                        //Armar la sentencia SQL (Cuentas Detalles).
                        stmLocAux = con.createStatement();    
                        strSQL="";
                        strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a4.nd_salCta as nd_salMen, a3.nd_salCta as nd_salAcu ";
                        strSQL+=" FROM tbm_detForplaCta AS a1 ";
                        strSQL+=" INNER JOIN ";
                        strSQL+=" ( ";
                        strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta ";
                        strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
                        strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+="    AND b1.co_for=1 ";
                        if (objParSis.getCodigoMenu()==342)   //Balance General.
                            strSQL+=" AND b1.co_per<=" + strFecCor;
                        else  //Estado de Resultados.
                            strSQL+=" AND (b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor + ")";
                        //strSQL+="    AND b1.nd_SalCta>0    ";
                        strSQL+="    GROUP BY b1.co_emp, b1.co_for, b1.co_cta ";
                        strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_for=a3.co_for AND a1.co_cta=a3.co_cta) ";
                        strSQL+=" INNER JOIN ";
                        strSQL+=" ( ";
                        strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, b1.nd_salCta ";
                        strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
                        strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+="    AND b1.co_for=1 ";
                        strSQL+="    AND b1.co_per=" + strFecCor;
                        strSQL+=" ) AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_for=a4.co_for AND a1.co_cta=a4.co_cta) ";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_For=1 ";
                        strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                        if (objParSis.getCodigoMenu()==342) //Balance General.
                            strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                        else //Estado de Resultados.
                            strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                        if(chkSalCer.isSelected())
                            strSQL+=" AND (a3.nd_salCta<>0 OR a4.nd_salCta<>0)";
                        strSQL+=" AND a1.tx_tipCta = 'D' ";
                        strSQL+=" AND a1.co_ctaDet>0 ";
                        strSQL+=" AND a1.ne_pad="+rst.getInt("co_cta");
                        strSQL+=" ORDER BY a1.tx_codCta, a1.tx_DesLar ";
                        
                        System.out.println("SQL-Cuentas Detalle:"+strSQL);
                        rstLocAux = stmLocAux.executeQuery(strSQL);

                        while (rstLocAux.next()) 
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                                vecReg.add(INT_TBL_DAT_COD_SIS,null);
                            else
                                vecReg.add(INT_TBL_DAT_COD_SIS,rstLocAux.getString("co_cta"));
                            intNiv=rstLocAux.getInt("ne_niv");
                            vecReg.add(INT_TBL_DAT_NIV_CTA,"" + intNiv);

                            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                                vecReg.add(INT_TBL_DAT_PAD_CTA,null);
                            else
                                vecReg.add(INT_TBL_DAT_PAD_CTA,rstLocAux.getString("ne_pad"));

                            vecReg.add(INT_TBL_DAT_COD_CTA,rstLocAux.getString("tx_codCta"));

                            //Generar la tabulacián para el campo "tx_desLar".
                            strAux="";
                            for (j=1; j<intNiv; j++)
                                strAux+="        ";
                            vecReg.add(INT_TBL_DAT_NOM_CTA,strAux + rstLocAux.getString("tx_desLar"));
                            dblSalMen=rstLocAux.getDouble("nd_salMen");
                            dblSalAcu=rstLocAux.getDouble("nd_salAcu");
                            vecReg.add(INT_TBL_DAT_SAL_MEN,"" + dblSalMen);
                            vecReg.add(INT_TBL_DAT_SAL_ACU,"" + dblSalAcu);
                            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                                vecReg.add(INT_TBL_DAT_TIP_CTA,"N");
                            else
                                vecReg.add(INT_TBL_DAT_TIP_CTA,"" + rstLocAux.getString("tx_tipCta"));

                            if (intNiv==1)
                            {
                                System.out.println("si");
                                dblTotMen+=dblSalMen;
                                dblTotAcu+=dblSalAcu;
                            }
                            vecDat.add(vecReg);
                        }
                        rstLocAux.close();
                        stmLocAux.close();
                        rstLocAux = null;
                        stmLocAux = null;
                        //</editor-fold>
                        
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
//                //Calcular totales.
//                objTblTot.setValueAt("" + dblTotMen, 0, INT_TBL_DAT_SAL_MEN);
//                objTblTot.setValueAt("" + dblTotAcu, 0, INT_TBL_DAT_SAL_ACU);
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);

//                if( ! optMes.isSelected())
//                {
//                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//                        if(generaSaldosCuentasPadresGrupo()){
//                            if(chkSalCer.isSelected()){
//                                if(quitarCtasSaldoCero()){
//                                    if(calculaTotales()){
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    else{
//                        if(generaSaldosCuentasPadres()){
//                            if(chkSalCer.isSelected()){
//                                if(quitarCtasSaldoCero()){
//                                    if(calculaTotales()){
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }

                if(optMes.isSelected())
                {
                    //Calcular totales.
                    objTblTot.setValueAt("" + dblTotMen, 0, INT_TBL_DAT_SAL_MEN);
                    objTblTot.setValueAt("" + dblTotAcu, 0, INT_TBL_DAT_SAL_ACU);
                    if(objParSis.getCodigoMenu()==343){
                        if(objPerUsr.isOpcionEnabled(2764)){
                            if(!cargarCuentaBalanceMerVal()){
                                mostrarMsgInf("La información de la cuenta de balance no se generó correctamente.");
                            }
                        }
                    }
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
    
    
    private boolean calculaTotales(){
        boolean blnRes=true;
        BigDecimal bdeSalMen=new BigDecimal("0");
        BigDecimal bdeSalAcu=new BigDecimal("0");
        int intNivCta=-1;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intNivCta=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA).toString());
                if(intNivCta==1){
                    bdeSalMen=bdeSalMen.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString())  );
                    bdeSalAcu=bdeSalAcu.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString())  );
                }
            }
            
            objTblTot.setValueAt("" + bdeSalMen, 0, INT_TBL_DAT_SAL_MEN);
            objTblTot.setValueAt("" + bdeSalAcu, 0, INT_TBL_DAT_SAL_ACU);


        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

                    



    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
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
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta funcián muestra un mensaje de error al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
//Dennis Borrar
//    /**
//     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
//     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
//     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
//     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
//     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
//     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
//     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
//     */
//    private class ZafThreadGUI extends Thread
//    {
//        public void run()
//        {
//            if (chkMerVal.isSelected() && blnButExp == false)
//            {
//                if (!cargarDetRegMerVal())
//                {
//                    //Inicializar objetos si no se pudo cargar los datos.
//                    lblMsgSis.setText("Listo");
//                    pgrSis.setValue(0);
//                    butCon.setText("Consultar");
//                }
//            }
//            else if (blnButExp == true && (!exportarDatos()))
//            {
//                    //Inicializar objetos si no se pudo cargar los datos.
//                    lblMsgSis.setText("Listo");
//                    pgrSis.setValue(0);
//                    butExp.setText("Exportar");
//            }
//            else if (!chkMerVal.isSelected())
//            {
//                if (!cargarDetReg())
//                {
//                    //Inicializar objetos si no se pudo cargar los datos.
//                    lblMsgSis.setText("Listo");
//                    pgrSis.setValue(0);
//                    butCon.setText("Consultar");
//                }
//            }
//            
//            //Establecer el foco en el JTable sálo cuando haya datos.
//            if (tblDat.getRowCount()>0 && blnButExp == false)
//            {
//                tabFrm.setSelectedIndex(1);
//                tblDat.setRowSelectionInterval(0, 0);
//                tblDat.requestFocus();
//            }
//            objThrGUI=null;
//        }
//    }
    

  
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if(chkMerVal.isSelected())
            {
                if (!cargarDetRegMerVal())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
            }
            else
            {
                if (chkRemayo.isSelected())
                {
                   remayorizar();
                }
                
                if (!cargarDetReg())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
            }
            
            //Establecer el foco en el JTable sÃ¡lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIExportar extends Thread
    {
        public void run()
        {
            if(chkMerVal.isSelected())
            {
                if (!exportarDatos())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butExp.setText("Exportar");
                }
            }
            objThrGUIExp=null;
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
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Cádigo del Sistema";
                    break;
                case INT_TBL_DAT_NIV_CTA:
                    strMsg="Nivel de la cuenta";
                    break;
                case INT_TBL_DAT_PAD_CTA:
                    strMsg="Cuenta padre";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Cádigo de la cuenta";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_SAL_MEN:
                    strMsg="Saldo mensual";
                    break;
                case INT_TBL_DAT_SAL_ACU:
                    strMsg="Saldo acumulado";
                    break;
                default:
                    strMsg=null;
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 
    

    public boolean imprimir() {                
        int intNumTotReg, intNiv, i, j;
        double dblSalMen, dblSalAcu, dblTotMen, dblTotAcu;
        boolean blnRes=true;
        String strNivRep="";
        Connection conIns;
        try
        {
            conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        try
        {
            if(conIns!=null){
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                //INICIO: Mejorar esto:
                String strFecDes, strFecHas;
                //FIN: Mejorar esto.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    //Obtener los datos del "Grupo".
                    intNumTotReg=0;
                }
                else{                                                                              
                         //Balance General.
                        JasperDesign jasperDesign = JRXmlLoader.load("C://Zafiro//Reportes_impresos//RptZafCon13.jrxml");
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);                                                

                        // Second, create a map of parameters to pass to the report.
                        Map parameters = new HashMap();
                        parameters.put("codEmp", ""+objParSis.getCodigoEmpresa());
                        parameters.put("nomEmp", ""+objParSis.getNombreEmpresa());
                        parameters.put("fecCor", ""+strFecCor);
                        parameters.put("cboNiv", ""+(cboNiv.getSelectedIndex()+1));                        
                          if (objParSis.getCodigoMenu()==342){
                              parameters.put("nomRep", "BALANCE GENERAL");
                              parameters.put("tipRep", "1");//PARA BALANCE GENERAL EL DATO ES UNO(1)
                              parameters.put("titTot", "Diferencias");
                          }
                          else{
                              parameters.put("nomRep", "ESTADO DE RESULTADOS");
                              parameters.put("tipRep", "0");//PARA ESTADO DE RESULTADOS EL DATO ES CERO(0)
                              parameters.put("titTot", "Utilidad/Párdida");
                              parameters.put("aniIniCor", "" + strFecCorIni );
                          }
                        parameters.put("aniCor", "" + (cboAnoCor.getSelectedItem()));
                        parameters.put("nomMes", "" + cboMesCor.getSelectedItem().toString().toUpperCase());
                        
                        
                        if(chkSalCer.isSelected()){
                            parameters.put("chkSel", "1");                            
                        }
                        else
                            parameters.put("chkSel", "0");
                         
                        if(chkMerVal.isSelected())
                            saldosTotalesMerVal();
                        else
                            saldosTotales();
                        
                        parameters.put("salTotMen", new Double(dblSalMenImp));
                        parameters.put("salTotAcu", new Double(dblSalAcuImp));                                                
                        
                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);

                        //para imprimir
                        //JasperManager.printReport(report, true);                                                                 
                        JasperViewer.viewReport(report, false);                          

                }                
            }
        }
        catch (JRException e)
        {
            System.out.println("Excepcián: " + e.toString());
        }
           
        }

        catch(SQLException ex)
        {
            System.out.println("Error al conectarse a la base");
        }                        
        return true;
     }       
    
    
    /**
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresiï¿½n directa.
     * <LI>1: Impresiï¿½n directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {

            String strRutRpt, strNomRpt;
            int i, intNumTotRpt;
            boolean blnRes=true;
            String strSQLRep="", strSQLSubRep="";
            String strFecHorSer="";
            //Inicializar los parametros que se van a pasar al reporte.
            java.util.Map mapPar=new java.util.HashMap();

            Connection conIns;
            String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
            String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
            try
            {
                conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                try
                {
                    if(conIns!=null)
                    {
                        objRptSis.cargarListadoReportes();
                        objRptSis.show();
                        if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
                        {
                            //Obtener la fecha y hora del servidor.
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            if (datFecAux==null)
                                return false;
                            strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                            datFecAux=null;

                            intNumTotRpt=objRptSis.getNumeroTotalReportes();
                            for (i=0;i<intNumTotRpt;i++)
                            {
                                if (objRptSis.isReporteSeleccionado(i))
                                {
                                    switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                    {
                                        case 155://BG
                                                strRutRpt=objRptSis.getRutaReporte(i);
                                                strNomRpt=objRptSis.getNombreReporte(i);

                                                mapPar.put("codEmp", new Integer(""+objParSis.getCodigoEmpresa()));
                                                mapPar.put("nomEmp", ""+objParSis.getNombreEmpresa());
                                                mapPar.put("fecCor", new Integer(""+strFecCor));
                                                mapPar.put("cboNiv", new Integer(""+(cboNiv.getSelectedIndex()+1)));                        
                                                mapPar.put("nomRep", "BALANCE GENERAL");
                                                mapPar.put("tipRep", new Integer("1"));//PARA BALANCE GENERAL EL DATO ES UNO(1)
                                                mapPar.put("titTot", "Diferencias");
                                                mapPar.put("aniCor", new Integer("" + (cboAnoCor.getSelectedItem())));
                                                mapPar.put("nomMes", "" + cboMesCor.getSelectedItem().toString().toUpperCase());
                                                if(chkSalCer.isSelected()){
                                                    mapPar.put("chkSel", new Integer("1"));                            
                                                }
                                                else
                                                    mapPar.put("chkSel", new Integer("0"));
                                                
                                                
                                                if(chkMerVal.isSelected())
                                                   saldosTotalesMerVal();
                                                else
                                                   saldosTotales();
                                                
                                                
                                                mapPar.put("salTotMen", new BigDecimal(dblSalMenImp));
                                                mapPar.put("salTotAcu", new BigDecimal(dblSalAcuImp));   
                                                mapPar.put("strCamAudRpt", "" + objParSis.getNombreUsuario() + "   -   " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "");
                                                
                                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                        case 156://ER
                                                strRutRpt=objRptSis.getRutaReporte(i);
                                                strNomRpt=objRptSis.getNombreReporte(i);

                                                mapPar.put("codEmp", new Integer(""+objParSis.getCodigoEmpresa()));
                                                mapPar.put("nomEmp", ""+objParSis.getNombreEmpresa());
                                                mapPar.put("fecCor", new Integer(""+strFecCor));
                                                mapPar.put("cboNiv", new Integer(""+(cboNiv.getSelectedIndex()+1)));                        
                                                mapPar.put("nomRep", "ESTADO DE RESULTADOS");
                                                mapPar.put("tipRep", new Integer("0"));//PARA ESTADO DE RESULTADOS EL DATO ES CERO(0)
                                                mapPar.put("titTot", "Utilidad/Párdida");
                                                mapPar.put("aniIniCor", new Integer("" + strFecCorIni ));
                                                
                                                mapPar.put("aniCor", new Integer("" + (cboAnoCor.getSelectedItem())));
                                                mapPar.put("nomMes", "" + cboMesCor.getSelectedItem().toString().toUpperCase());

                                                if(chkSalCer.isSelected()){
                                                    mapPar.put("chkSel", new Integer("1"));                            
                                                }
                                                else
                                                    mapPar.put("chkSel", new Integer("0"));
                                                
                                                if(chkMerVal.isSelected())
                                                   saldosTotalesMerVal();
                                                else
                                                   saldosTotales();
                                                
                                                mapPar.put("salTotMen", new BigDecimal(dblSalMenImp));
                                                mapPar.put("salTotAcu", new BigDecimal(dblSalAcuImp));   
                                                mapPar.put("strCamAudRpt", "" + objParSis.getNombreUsuario() + "   -   " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "");
                                                
                                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    conIns.close();
                    conIns=null;
                }
                catch (Exception e)
                {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, e);
                }
            }
            catch(SQLException ex)
            {
                System.out.println("Error al conectarse a la base");
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, ex);
            }
            return blnRes;
    }
    
    
    

    private void saldosTotales()
    {
        int intNumTotReg, intNiv, i, j;
        double dblSalMen, dblSalAcu, dblTotMen=0.00, dblTotAcu=0.00;
        //String strFecCor;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                //INICIO: Mejorar esto:
                String strFecDes, strFecHas;
//                if (cboMesCor.getSelectedIndex()>=8)
//                {
//                    objUti.remayorizar(this, objParSis, "2006/09/01", "2006/10/31", "yyyy/MM/dd");
//                }
                //FIN: Mejorar esto.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    intNumTotReg=0;
                }
                else
                {                                        
                    if(chkSalCer.isSelected()){
                        strAux=" HAVING SUM(b1.nd_salCta)<>0";
                    }
                    else
                        strAux="";
                    
                    if (objParSis.getCodigoMenu()==342)
                    {
                        //Balance General.
                        strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                    }
                    else
                    {
                        //Estado de Resultados.
                        strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                    }
                    //Armar la sentencia SQL.
                    strSQL="";
                    //strSQL+="SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                    strSQL+="SELECT a1.co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_salCta AS b1";
                    strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                    if (objParSis.getCodigoMenu()==342){
                        strSQL+=" AND b1.co_per<=" + strFecCor;
                    }
                    else{
                        strSQL+=" AND b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor;
                    }
                    strSQL+=" GROUP BY b1.co_emp, b1.co_cta";
                    strSQL+=strAux;
                    strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_per=" + strFecCor;
                    //strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                    strSQL+=" AND a2.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                    
                    if (objParSis.getCodigoMenu()==342)
                    {
                        //Balance General.
                        strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                    }
                    else
                    {
                        //Estado de Resultados.
                        strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                    }
                    //strSQL+=" ORDER BY a1.tx_codCta";
                    strSQL+=" ORDER BY a2.tx_codCta";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                dblTotMen=0;
                dblTotAcu=0;
                while (rst.next())
                {
                        intNiv=rst.getInt("ne_niv");
                        //Generar la tabulacián para el campo "tx_desLar".
                        strAux="";
                        for (j=1; j<intNiv; j++)
                            strAux+="        ";
                        dblSalMen=rst.getDouble("nd_salMen");
                        dblSalAcu=rst.getDouble("nd_salAcu");
                        if (intNiv==1)
                        {
                            dblTotMen+=dblSalMen;
                            dblTotAcu+=dblSalAcu;
                        }
    
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Calcular totales.
                dblSalMenImp=dblTotMen;
                dblSalAcuImp=dblTotAcu;
                
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
    
    
    private void saldosTotalesMerVal()
    {
        int intNumTotReg, intNiv, i, j;
        double dblSalMen, dblSalAcu, dblTotMen=0.00, dblTotAcu=0.00;
        //String strFecCor;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                //INICIO: Mejorar esto:
                String strFecDes, strFecHas;
//                if (cboMesCor.getSelectedIndex()>=8)
//                {
//                    objUti.remayorizar(this, objParSis, "2006/09/01", "2006/10/31", "yyyy/MM/dd");
//                }
                //FIN: Mejorar esto.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    intNumTotReg=0;
                }
                else
                {                                        
                    if(chkSalCer.isSelected()){
                        strAux=" HAVING SUM(b1.nd_salCta)<>0";
                    }
                    else
                        strAux="";
                    
                    if (objParSis.getCodigoMenu()==342)
                    {
                        //Balance General.
                        strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                    }
                    else
                    {
                        //Estado de Resultados.
                        strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                    }
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                    strSQL+=" FROM tbm_detForplaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCtaForPlaCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_salCtaForPlaCta AS b1";
                    strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                    if (objParSis.getCodigoMenu()==342){
                        strSQL+=" AND b1.co_per<=" + strFecCor;
                    }
                    else{
                        strSQL+=" AND b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor;
                    }
                    strSQL+=" GROUP BY b1.co_emp, b1.co_cta";
                    strSQL+=strAux;
                    strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_per=" + strFecCor;
                    strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                    if (objParSis.getCodigoMenu()==342)
                    {
                        //Balance General.
                        strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                    }
                    else
                    {
                        //Estado de Resultados.
                        strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                    }
                    strSQL+=" ORDER BY a1.tx_codCta";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                dblTotMen=0;
                dblTotAcu=0;
                while (rst.next())
                {
                        intNiv=rst.getInt("ne_niv");
                        //Generar la tabulacián para el campo "tx_desLar".
                        strAux="";
                        for (j=1; j<intNiv; j++)
                            strAux+="        ";
                        dblSalMen=rst.getDouble("nd_salMen");
                        dblSalAcu=rst.getDouble("nd_salAcu");
                        if (intNiv==1)
                        {
                            dblTotMen+=dblSalMen;
                            dblTotAcu+=dblSalAcu;
                        }
    
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Calcular totales.
                dblSalMenImp=dblTotMen;
                dblSalAcuImp=dblTotAcu;
                
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
    
    
    
//    private String obtienFechRemayor(String strFechIni){
//      
//      java.sql.Statement stmTmp;
//      java.sql.ResultSet rstTmp;
//      String strSQLTmp;
//      String strFecIni="";
//        try
//        {
//            java.sql.Connection conTmp=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
//            if (conTmp!=null){
//                stmTmp=conTmp.createStatement();
//                strSQLTmp="";
//                strSQLTmp+="select cast('" + strFechIni + "' as date)-125 as fecha";
////                System.out.println("FECHA DE INICIO: "+strSQLTmp);
//                rstTmp=stmTmp.executeQuery(strSQLTmp);                    
//                if(rstTmp.next()){
//                    strFecIni=rstTmp.getString("fecha");
//                }
//                conTmp.close();                conTmp=null;
//                rstTmp.close();                rstTmp=null;
//                stmTmp.close();                stmTmp=null;
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return strFecIni;
//    
//        
//    }
    
    
    
    private boolean cargarAnioCreados(){
        boolean blnRes=true;
        int intUltAni=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, ne_ani";
                strSQL+=" FROM tbm_anicresis";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" ORDER BY ne_ani";
                
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    cboAnoCor.addItem("" + rst.getString("ne_ani"));
                    intUltAni++;
                }
                cboAnoCor.setSelectedIndex(intUltAni);
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }


    private String getFechaInicioMesCorte(String fechaCorte){
        Connection conFecCor;
        Statement stmFecCor;
        ResultSet rstFecCor;
        String strFecIniMesCor="";

        try{
            java.util.Calendar cal=java.util.Calendar.getInstance();
            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));

            cal.set(java.util.Calendar.DATE, cal.getActualMinimum(cal.DAY_OF_MONTH)    );
            strFecIniMesCor=objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy");


        }
//        catch (java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(this, e);
//        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecIniMesCor;

    }



    private String getPeriodoAnterior(){
        String strPerAnt="";
        int intFecPerAnt[];
        try{
//            java.util.Calendar cal=java.util.Calendar.getInstance();
//            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));
//            cal.set(java.util.Calendar.MONTH,cal.get((cal.MONTH))-1);
//            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy"));
//
//            System.out.println("periodo anterior:  "+ objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy") );
//            System.out.println("arreglo: " + intFecPerAnt[2] +  "/" + intFecPerAnt[1] );

//            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );

            

            java.util.Calendar cal=java.util.Calendar.getInstance();
            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));
            //cal.set(java.util.Calendar.MONTH, cal.get((cal.MONTH))-1);

            cal.add(java.util.Calendar.MONTH, -1);

            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy"));

            System.out.println("periodo anterior:  "+ objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy") );
            System.out.println("arreglo: " + intFecPerAnt[2] +  "/" + intFecPerAnt[1] );

            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<=9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );


        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strPerAnt;
    }


    private String getPeriodoInicialEstadoResultados(){
        String strPerAnt="";
        int intFecPerAnt[];
        try{
            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
            strPerAnt="" + (intFecPerAnt[2] + "01" );
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strPerAnt;
    }


    private String getPeriodoFinalEstadoResultados(){
        String strPerAnt="";
        int intFecPerAnt[];
        try{
            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<=9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strPerAnt;
    }


    private boolean generaSaldosCuentasPadresGrupo(){
        boolean blnRes=true;
        //VARIABLES PARA EL DETALLE(O SEA QUIENES SE VAN A IR SUMANDO)
        BigDecimal bdeValMen=new BigDecimal("0");
        BigDecimal bdeValAcu=new BigDecimal("0");
        BigDecimal bdeSumMen=new BigDecimal("0");
        BigDecimal bdeSumAcu=new BigDecimal("0");
        //INT_TBL_DAT_TIP_CTA

        String strNumCtaIni="", strNumCtaFin="";
        String strSubNumCtaIni="", strSubNumCtaFin="";
        String strProIni="", strProFin="";
        int intNivCtaIni=-1, intNivCtaFin=-1;
        
        try{

            for(int p=intNIVELES;p>1;p--){
                //for(int p=6;p>1;p--){
                for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                    intNivCtaIni=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA).toString());

                    if(intNivCtaIni==p){
                        strNumCtaIni=objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA).toString();
                        strSubNumCtaIni=strNumCtaIni.substring(0,( (1 + ((p-1)*3) ) - 3));//el numero de cuenta donde se va a colocar el valor obtenido
                        strProIni=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CTA).toString();

                        if(strProIni.equals("N")){
                            for(int k=(objTblMod.getRowCountTrue()-1); k>=0; k--){
                                strProFin=objTblMod.getValueAt(k, INT_TBL_DAT_TIP_CTA).toString();
                                if(strProFin.equals("N")){
                                    intNivCtaFin=Integer.parseInt(objTblMod.getValueAt(k, INT_TBL_DAT_NIV_CTA).toString());
                                    System.out.println("intNivCtaFin: " + intNivCtaFin);
                                    if(intNivCtaFin==p){

                                        strNumCtaFin=objTblMod.getValueAt(k, INT_TBL_DAT_COD_CTA).toString();
                                        System.out.println("strNumCtaFin: " + strNumCtaFin);

                                        strSubNumCtaFin=strNumCtaFin.substring(0,( (1 + ((p-1)*3) ) - 3));
                                        System.out.println("strSubNumCtaFin: " + strSubNumCtaFin);
                                        if(strSubNumCtaIni.equals(strSubNumCtaFin)){
                                            bdeValMen=new BigDecimal("" + objTblMod.getValueAt(k, INT_TBL_DAT_SAL_MEN).toString()==null?"0":objTblMod.getValueAt(k, INT_TBL_DAT_SAL_MEN).toString().toString());
                                            bdeSumMen=bdeSumMen.add(bdeValMen);


                                            bdeValAcu=new BigDecimal("" + objTblMod.getValueAt(k, INT_TBL_DAT_SAL_ACU).toString()==null?"0":objTblMod.getValueAt(k, INT_TBL_DAT_SAL_ACU).toString().toString());
                                            bdeSumAcu=bdeSumAcu.add(bdeValAcu);

                                            objTblMod.setValueAt("S", k, INT_TBL_DAT_TIP_CTA);
                                        }
                                    }
                                }
                            }
                            for(int g=(objTblMod.getRowCountTrue()-1); g>=0; g--){
                                strNumCtaFin=objTblMod.getValueAt(g, INT_TBL_DAT_COD_CTA).toString();//reutilizo las variables para no crear otras
                                if(strSubNumCtaIni.equals(strNumCtaFin)){
                                    objTblMod.setValueAt(bdeSumMen, g, INT_TBL_DAT_SAL_MEN);
                                    objTblMod.setValueAt(bdeSumAcu, g, INT_TBL_DAT_SAL_ACU);
                                    bdeSumMen=new BigDecimal("0");
                                    bdeSumAcu=new BigDecimal("0");
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean generaSaldosCuentasPadres(){
        boolean blnRes=true;
        String strLin="";
        //VARIABLES PARA EL DETALLE(O SEA QUIENES SE VAN A IR SUMANDO)
        int intCodCtaPadDetIni=-1;
        int intCodCtaPadDetFin=-1;
        BigDecimal bdeValMen=new BigDecimal("0");
        BigDecimal bdeValAcu=new BigDecimal("0");
        BigDecimal bdeSumMen=new BigDecimal("0");
        BigDecimal bdeSumAcu=new BigDecimal("0");

        //VARIABLES DE CABECERA(O SEA DE LA CUENTA DONDE SE VA A COLOCAR LO QUE SE SUMO EN LAS DE DETALLE)
        int intCodCtaCab=-1;
        int intPriNiv=0;//captura cual es el primer nivel(este nivel es el q se busca.
        int intNivCta=0;//contiene la cuenta padre que es la q se buscara para ir sumando.

        try{
            for(int p=intNIVELES;p>0;p--){
                //for(int p=6;p>0;p--){
                for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                    intNivCta=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA).toString());
                    if(intNivCta==p){
                        if(intPriNiv==0){
                            intCodCtaPadDetIni=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString());
                            intPriNiv++;
                        }
                        intCodCtaPadDetFin=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString());

                        if(i>0){
                            intCodCtaCab=Integer.parseInt(objTblMod.getValueAt((i-1), INT_TBL_DAT_COD_SIS)==null?"0":objTblMod.getValueAt((i-1), INT_TBL_DAT_COD_SIS).toString());
                        }
                        else{
                            intCodCtaCab=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS).toString());
                        }

                        if(intCodCtaPadDetIni==intCodCtaCab){
                            bdeValMen=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString().toString());
                            bdeSumMen=bdeSumMen.add(bdeValMen);
                            bdeValAcu=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString().toString());
                            bdeSumAcu=bdeSumAcu.add(bdeValAcu);

                            objTblMod.setValueAt(bdeSumMen, (i-1), INT_TBL_DAT_SAL_MEN);
                            objTblMod.setValueAt(bdeSumAcu, (i-1), INT_TBL_DAT_SAL_ACU);
                            intPriNiv=0;
                            bdeSumMen=new BigDecimal("0");
                            bdeSumAcu=new BigDecimal("0");
                        }
                        else{
                            if(intCodCtaPadDetIni==intCodCtaPadDetFin){
                                bdeValMen=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString().toString());
                                bdeSumMen=bdeSumMen.add(bdeValMen);
                                bdeValAcu=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString().toString());
                                bdeSumAcu=bdeSumAcu.add(bdeValAcu);
                            }
                        }
                    }


                }
            }

        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    private boolean quitarCtasSaldoCero(){
        boolean blnRes=true;
        BigDecimal bdeSumMen=new BigDecimal("0");
        BigDecimal bdeSumAcu=new BigDecimal("0");
        BigDecimal bdeValCer=new BigDecimal("0");
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            for(int i=(objTblMod.getRowCountTrue()-1);i>=0;i--){
                bdeSumMen=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString());
                bdeSumAcu=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString());
                if( (bdeSumMen.compareTo(bdeValCer)==0) && (bdeSumAcu.compareTo(bdeValCer)==0) ){
                    objTblMod.removeRow(i);
                }
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

        }catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;

    }


    private boolean cargarCuentaBalance(){
        System.out.println("CargarCuentaBalance");
        boolean blnRes=true;
        BigDecimal bdeDifMenER=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeDifAcuER=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeDifMenBal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeDifAcuBal=new BigDecimal(BigInteger.ZERO);
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" , a1.tx_tipCta, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                strSQL+=" INNER JOIN (";
                strSQL+="    SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                strSQL+="    FROM tbm_salCta AS b1";
                strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="    AND b1.co_per<=" + strFecCor + "";
                strSQL+="     GROUP BY b1.co_emp, b1.co_cta";
                strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_per=" + strFecCor + "";
                strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                if(objParSis.getCodigoEmpresa()==1)
                    strSQL+=" AND a1.co_cta=236";//tuval
                else if(objParSis.getCodigoEmpresa()==2)
                    strSQL+=" AND a1.co_cta=36";//castek
                else if(objParSis.getCodigoEmpresa()==4)
                    strSQL+=" AND a1.co_cta=157";//dimulti
                strSQL+=" AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                strSQL+=" ORDER BY a1.tx_codCta";
                System.out.println("Query.cargarCuentaBalance:"+strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    objTblTot.setValueAt(rst.getString("co_cta"), 1, INT_TBL_DAT_COD_SIS);
                    objTblTot.setValueAt(rst.getString("ne_niv"), 1, INT_TBL_DAT_NIV_CTA);
                    objTblTot.setValueAt(rst.getString("ne_pad"), 1, INT_TBL_DAT_PAD_CTA);
                    objTblTot.setValueAt(rst.getString("tx_codCta"), 1, INT_TBL_DAT_COD_CTA);
                    objTblTot.setValueAt(rst.getString("tx_desLar"), 1, INT_TBL_DAT_NOM_CTA);
                    objTblTot.setValueAt(rst.getString("nd_salMen"), 1, INT_TBL_DAT_SAL_MEN);
                    objTblTot.setValueAt(rst.getString("nd_salAcu"), 1, INT_TBL_DAT_SAL_ACU);
                    objTblTot.setValueAt(rst.getString("tx_tipCta"), 1, INT_TBL_DAT_TIP_CTA);
                }

                bdeDifMenER=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_SAL_MEN)==null?"0":objTblTot.getValueAt(0, INT_TBL_DAT_SAL_MEN).toString());
                bdeDifAcuER=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_SAL_ACU)==null?"0":objTblTot.getValueAt(0, INT_TBL_DAT_SAL_ACU).toString());

                bdeDifMenBal=new BigDecimal(objTblTot.getValueAt(1, INT_TBL_DAT_SAL_MEN)==null?"0":objTblTot.getValueAt(1, INT_TBL_DAT_SAL_MEN).toString());
                bdeDifAcuBal=new BigDecimal(objTblTot.getValueAt(1, INT_TBL_DAT_SAL_ACU)==null?"0":objTblTot.getValueAt(1, INT_TBL_DAT_SAL_ACU).toString());

                objTblTot.setValueAt("Total", 2, INT_TBL_DAT_NOM_CTA);
                objTblTot.setValueAt(bdeDifMenER.add(bdeDifMenBal), 2, INT_TBL_DAT_SAL_MEN);
                objTblTot.setValueAt(bdeDifAcuER.add(bdeDifAcuBal), 2, INT_TBL_DAT_SAL_ACU);

                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarCuentaBalanceMerVal()
    {
        System.out.println("CargarCuentaBalance");
        boolean blnRes=true;
        BigDecimal bdeDifMenER=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeDifAcuER=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeDifMenBal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeDifAcuBal=new BigDecimal(BigInteger.ZERO);
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                strSQL="";
                strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar";
                strSQL+="        , a1.tx_tipCta, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                strSQL+=" FROM tbm_DetForPlaCta AS a1";
                strSQL+=" INNER JOIN tbm_salCtaForPlaCta as a2 ON (a1.co_emp=a2.co_emp  AND a1.co_for=a2.co_for AND a1.co_cta=a2.co_cta) ";
                strSQL+=" INNER JOIN (";
                strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                strSQL+="    FROM tbm_salCtaForPlaCta AS b1";
                strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="    AND b1.co_per<=" + strFecCor + "";
                strSQL+="     GROUP BY b1.co_emp, b1.co_for, b1.co_cta";
                strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp  AND a1.co_for=a3.co_for AND a1.co_cta=a3.co_cta) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_per=" + strFecCor + "";
                strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                if(objParSis.getCodigoEmpresa()==1)
                    strSQL+=" AND a1.co_ctaDet=236";//tuval
                else if(objParSis.getCodigoEmpresa()==2)
                    strSQL+=" AND a1.co_ctaDet=36";//castek
                else if(objParSis.getCodigoEmpresa()==4)
                    strSQL+=" AND a1.co_ctaDet=157";//dimulti
                strSQL+=" AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0)";
                strSQL+=" ORDER BY a1.tx_codCta";
                System.out.println("Query.cargarCuentaBalance:"+strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    objTblTot.setValueAt(rst.getString("co_cta"), 1, INT_TBL_DAT_COD_SIS);
                    objTblTot.setValueAt(rst.getString("ne_niv"), 1, INT_TBL_DAT_NIV_CTA);
                    objTblTot.setValueAt(rst.getString("ne_pad"), 1, INT_TBL_DAT_PAD_CTA);
                    objTblTot.setValueAt(rst.getString("tx_codCta"), 1, INT_TBL_DAT_COD_CTA);
                    objTblTot.setValueAt(rst.getString("tx_desLar"), 1, INT_TBL_DAT_NOM_CTA);
                    objTblTot.setValueAt(rst.getString("nd_salMen"), 1, INT_TBL_DAT_SAL_MEN);
                    objTblTot.setValueAt(rst.getString("nd_salAcu"), 1, INT_TBL_DAT_SAL_ACU);
                    objTblTot.setValueAt(rst.getString("tx_tipCta"), 1, INT_TBL_DAT_TIP_CTA);
                }

                bdeDifMenER=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_SAL_MEN)==null?"0":objTblTot.getValueAt(0, INT_TBL_DAT_SAL_MEN).toString());
                bdeDifAcuER=new BigDecimal(objTblTot.getValueAt(0, INT_TBL_DAT_SAL_ACU)==null?"0":objTblTot.getValueAt(0, INT_TBL_DAT_SAL_ACU).toString());

                bdeDifMenBal=new BigDecimal(objTblTot.getValueAt(1, INT_TBL_DAT_SAL_MEN)==null?"0":objTblTot.getValueAt(1, INT_TBL_DAT_SAL_MEN).toString());
                bdeDifAcuBal=new BigDecimal(objTblTot.getValueAt(1, INT_TBL_DAT_SAL_ACU)==null?"0":objTblTot.getValueAt(1, INT_TBL_DAT_SAL_ACU).toString());

                objTblTot.setValueAt("Total", 2, INT_TBL_DAT_NOM_CTA);
                objTblTot.setValueAt(bdeDifMenER.add(bdeDifMenBal), 2, INT_TBL_DAT_SAL_MEN);
                objTblTot.setValueAt(bdeDifAcuER.add(bdeDifAcuBal), 2, INT_TBL_DAT_SAL_ACU);

                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    //<editor-fold defaultstate="collapsed" desc="/* DENNIS - BOTÓN EXPORTAR */">
    private boolean exportarDatos()
    {
        boolean blnRes = true, blnRes2;
        String strLin, strCodEmiVal, strFecCorInf, strPer, strCodEnt, strCodCta, strRutFilTxt;
        int i, intTotDiaMes, intNumRegBalGen, intNumRegEstRes;
        BigDecimal bdeValCta, bdeTotAcuNiv1;
        ArrayList arlRegBalGen, arlDatBalGen, arlRegEstRes, arlDatEstRes;
        Vector vecDat;

        try
        {          
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (con != null)
            {  
                stm = con.createStatement();
                GregorianCalendar calendar = new GregorianCalendar();

                switch (cboMesCor.getSelectedIndex() + 1)
                {  case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                      intTotDiaMes = 31;
                      break;

                   case 4: case 6: case 9: case 11:
                      intTotDiaMes = 30;
                      break;

                   default:
                      //El mes es febrero
                      if (calendar.isLeapYear(Integer.parseInt(cboAnoCor.getSelectedItem().toString())))
                      {  //El año es bisiesto
                         intTotDiaMes = 29;
                      }
                      else
                      {  //El año no es bisiesto
                         intTotDiaMes = 28;
                      }
                }

                strFecCorInf = intTotDiaMes + "/" + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1)) + "/" + cboAnoCor.getSelectedItem();
                strPer = "7005"; //Periodo semestral
                strCodEmiVal = "";

                strSQL =  "SELECT tx_codalt as tx_codent from tbm_cabForPlaCta ";
                strSQL += "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " ";
                strSQL += "and co_for = 1 ";
                rst = stm.executeQuery(strSQL);

                if (rst.next())
                   strCodEmiVal = rst.getString("tx_codent");

                arlDatBalGen = new ArrayList();
                intNumRegBalGen = 0;
                bdeValCta = new BigDecimal("0");
                bdeTotAcuNiv1 = new BigDecimal("0");
                vecDat = new Vector();
                blnRes2 = procesoExportarDatos("BG", vecDat);

                for (i = 0; i < vecDat.size(); i++)
                {  strCodCta = objUti.getStringValueAt(vecDat, i, INT_TBL_DAT_COD_CTA);

                   if (strCodCta.equals("1") || strCodCta.equals("2") || strCodCta.equals("3"))
                   {  bdeValCta = objUti.getBigDecimalValueAt(vecDat, i, INT_TBL_DAT_SAL_ACU);
                      bdeTotAcuNiv1 = bdeTotAcuNiv1.add(bdeValCta);
                   }

                   if (objUti.getStringValueAt(vecDat, i, INT_TBL_DAT_TIP_CTA).equals("D"))
                   {  arlRegBalGen = new ArrayList();
                      arlRegBalGen.add(INT_ARL_DAT_COD_ENT, "" + strCodEmiVal);
                      arlRegBalGen.add(INT_ARL_DAT_COD_CTA, "" + objUti.getStringValueAt(vecDat, i, INT_TBL_DAT_COD_CTA));
                      arlRegBalGen.add(INT_ARL_DAT_VAL_CTA, "" + objUti.getBigDecimalValueAt(vecDat, i, INT_TBL_DAT_SAL_ACU));
                      arlDatBalGen.add(arlRegBalGen);
                      intNumRegBalGen++;
                   }
                }

                bdeTotAcuNiv1 = objUti.redondearBigDecimal(bdeTotAcuNiv1, objParSis.getDecimalesMostrar());

                if (! bdeTotAcuNiv1.equals(new BigDecimal("0.00")))
                {  mostrarMsgInf("<HTML>No se puede exportar datos debido a que el Balance general no está cuadrado</HTML>");
                   blnButExp = false;
                   return false;
                }

                arlDatEstRes = new ArrayList();
                intNumRegEstRes = 0;
                blnRes2 = procesoExportarDatos("ER", vecDat);

                for (i = 0; i < vecDat.size(); i++)
                {  
                    if (objUti.getStringValueAt(vecDat, i, INT_TBL_DAT_TIP_CTA).equals("D"))
                    {  
                        arlRegEstRes = new ArrayList();
                        arlRegEstRes.add(INT_ARL_DAT_COD_ENT, "" + strCodEmiVal);
                        arlRegEstRes.add(INT_ARL_DAT_COD_CTA, "" + objUti.getStringValueAt(vecDat, i, INT_TBL_DAT_COD_CTA));
                        arlRegEstRes.add(INT_ARL_DAT_VAL_CTA, "" + objUti.getBigDecimalValueAt(vecDat, i, INT_TBL_DAT_SAL_ACU));
                        arlDatEstRes.add(arlRegEstRes);
                        intNumRegEstRes++;
                    }
                }

                //String strRutFilTxt=objParSis.getDirectorioSistema();
                //File f = new File(strRutFilTxt + "/supercias.txt");

                if (System.getProperty("os.name").equals("Linux"))
                   strRutFilTxt = "/Zafiro/";
                else
                   strRutFilTxt = "C:\\Zafiro\\";

                File f = new File(strRutFilTxt + "supercias.txt");

                FileWriter fw = new FileWriter(f);
                strLin = strCodEmiVal + "\t" + strFecCorInf + "\t" + strPer; //1ra. linea de cabecera del archivo a generar
                fw.write(strLin + "\n");
                fw.append("\r\n");

                strLin = "estados_financieros" + "\t" + "4" + "\t" + (3 + intNumRegBalGen + intNumRegEstRes); //2da. linea de cabecera del archivo a generar
                fw.write(strLin + "\n");
                fw.append("\r\n");

                strLin = "montos_negociados_titulos_genericos" + "\t" + "0" + "\t" + "0"; //3ra. linea de cabecera del archivo a generar
                fw.write(strLin + "\n");
                fw.append("\r\n");

                for (i = 0; i < arlDatBalGen.size(); i++)
                {  strCodEnt = objUti.getStringValueAt(arlDatBalGen, i, INT_ARL_DAT_COD_ENT);
                   strCodCta = objUti.getStringValueAt(arlDatBalGen, i, INT_ARL_DAT_COD_CTA);
                   bdeValCta = objUti.getBigDecimalValueAt(arlDatBalGen, i, INT_ARL_DAT_VAL_CTA);
                   bdeValCta = objUti.redondearBigDecimal(bdeValCta, objParSis.getDecimalesMostrar());
                   strLin = strCodEnt + "\t" + strCodCta + "\t" + bdeValCta;
                   fw.write(strLin + "\n");
                   fw.append("\r\n");
                }

                for (i = 0; i < arlDatEstRes.size(); i++)
                {  strCodEnt = objUti.getStringValueAt(arlDatEstRes, i, INT_ARL_DAT_COD_ENT);
                   strCodCta = objUti.getStringValueAt(arlDatEstRes, i, INT_ARL_DAT_COD_CTA);
                   bdeValCta = objUti.getBigDecimalValueAt(arlDatEstRes, i, INT_ARL_DAT_VAL_CTA);
                   bdeValCta = objUti.redondearBigDecimal(bdeValCta, objParSis.getDecimalesMostrar());
                   strLin = strCodEnt + "\t" + strCodCta + "\t" + bdeValCta;
                   fw.write(strLin + "\n");
                   fw.append("\r\n");
                }

               BufferedWriter bw = new BufferedWriter(fw);
               PrintWriter salida = new PrintWriter(bw);
               //System.out.println("EXPORTAR: "+salida);
               salida.close();
               mostrarMsgInf("<HTML>El archivo se generó exitosamente en <FONT COLOR=\"blue\">" + strRutFilTxt + "supercias.txt" + "</FONT></HTML>");
            } //if (con != null)

            blnButExp = false;
        } //try

        catch (Exception e)
        {  objUti.mostrarMsgErr_F1(this, e);
           blnRes = false;
           blnButExp = false;
        }

        return blnRes;
    } //Funcion exportarDatos()
    
    private boolean procesoExportarDatos(String strEstFin, Vector vecDat)
    {
       boolean blnRes = true;
       int intNiv, j;
       double dblSalMen, dblSalAcu, dblTotMen, dblTotAcu;
       java.sql.Statement stmLocAux = null;
       java.sql.ResultSet rstLocAux = null;
       
       try
       {    
            pgrSis.setIndeterminate(true);
            butExp.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
       
            if (con != null)
            {  stm = con.createStatement();
               strAux="";

               if (chkSalCer.isSelected())
               {
                   if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                       strAux+=" HAVING SUM(c1.nd_salMen)<>0 OR SUM(c1.nd_salAcu)<>0";
                   else
                       strAux+=" AND (a3.nd_salCta<>0 OR a4.nd_salCta<>0)";
               }

               String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
               String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";

               strSQL="";
               strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a4.nd_salCta as nd_salMen, a3.nd_salCta as nd_salAcu, ";
               strSQL+="        (substring(a1.tx_codCta from 1 for 1 ) || '.' || substring(a1.tx_codCta from 2 for 100 )) as tx_codCta2 ";     
               strSQL+=" FROM tbm_detForplaCta AS a1 ";
               strSQL+=" INNER JOIN ";
               strSQL+=" ( ";
               strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta ";
               strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
               strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
               strSQL+="    AND b1.co_for=1 ";

               if (strEstFin.equals("BG")) //Balance General
                   strSQL+=" AND b1.co_per<=" + strFecCor;
               else  //Estado de Resultados
                   strSQL+=" AND (b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor + ")";

               //strSQL+="    AND b1.nd_SalCta>0    ";
               strSQL+="    GROUP BY b1.co_emp, b1.co_for, b1.co_cta ";
               strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_for=a3.co_for AND a1.co_cta=a3.co_cta) ";
               strSQL+=" INNER JOIN ";
               strSQL+=" ( ";
               strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, b1.nd_salCta ";
               strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
               strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
               strSQL+="    AND b1.co_for=1 ";
               strSQL+="    AND b1.co_per=" + strFecCor;
               strSQL+=" ) AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_for=a4.co_for AND a1.co_cta=a4.co_cta) ";
               strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
               strSQL+=" AND a1.co_For=1 ";
               strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);

               if (strEstFin.equals("BG")) //Balance General
                   strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
               else //Estado de Resultados
                   strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";

               if (chkSalCer.isSelected())
                   strSQL+=" AND (a3.nd_salCta<>0 OR a4.nd_salCta<>0)";

               strSQL+=" AND a1.tx_tipCta = 'C' ";
               strSQL+=" ORDER BY tx_codCta2 ";

               rst=stm.executeQuery(strSQL);
               //Limpiar vector de datos.
               vecDat.clear();
               //Obtener los registros.
               lblMsgSis.setText("Exportando datos...");
               dblTotMen=0;
               dblTotAcu=0;

               while (rst.next())
               {
                   if (blnButExp)
                   {
                       //<editor-fold defaultstate="collapsed" desc="/* Cuentas Cabecera */">
                       vecReg=new Vector();
                       vecReg.add(INT_TBL_DAT_LIN,"");

                       if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                           vecReg.add(INT_TBL_DAT_COD_SIS,null);
                       else
                           vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_cta"));

                       intNiv=rst.getInt("ne_niv");
                       vecReg.add(INT_TBL_DAT_NIV_CTA,"" + intNiv);

                       if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                           vecReg.add(INT_TBL_DAT_PAD_CTA,null);
                       else
                           vecReg.add(INT_TBL_DAT_PAD_CTA,rst.getString("ne_pad"));

                       vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("tx_codCta"));

                       //Generar la tabulacion para el campo "tx_desLar".
                       strAux="";

                       for (j=1; j<intNiv; j++)
                           strAux+="        ";

                       vecReg.add(INT_TBL_DAT_NOM_CTA,strAux + rst.getString("tx_desLar"));
                       dblSalMen=rst.getDouble("nd_salMen");
                       dblSalAcu=rst.getDouble("nd_salAcu");
                       vecReg.add(INT_TBL_DAT_SAL_MEN,"" + dblSalMen);
                       vecReg.add(INT_TBL_DAT_SAL_ACU,"" + dblSalAcu);

                       if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                           vecReg.add(INT_TBL_DAT_TIP_CTA,"N");
                       else
                           vecReg.add(INT_TBL_DAT_TIP_CTA,"" + rst.getString("tx_tipCta"));

                       if (intNiv==1)
                       {
                           //System.out.println("si");
                           dblTotMen+=dblSalMen;
                           dblTotAcu+=dblSalAcu;
                       }
                       vecDat.add(vecReg);
                       //</editor-fold>

                       //<editor-fold defaultstate="collapsed" desc="/* Cuentas de Detalle */">
                       //Armar la sentencia SQL (Cuentas Detalles).
                       stmLocAux = con.createStatement();    
                       strSQL="";
                       strSQL+=" SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a4.nd_salCta as nd_salMen, a3.nd_salCta as nd_salAcu ";
                       strSQL+=" FROM tbm_detForplaCta AS a1 ";
                       strSQL+=" INNER JOIN ";
                       strSQL+=" ( ";
                       strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta ";
                       strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
                       strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                       strSQL+="    AND b1.co_for=1 ";

                       if (strEstFin.equals("BG")) //Balance General
                           strSQL+=" AND b1.co_per<=" + strFecCor;
                       else  //Estado de Resultados
                           strSQL+=" AND (b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor + ")";

                       //strSQL+="    AND b1.nd_SalCta>0    ";
                       strSQL+="    GROUP BY b1.co_emp, b1.co_for, b1.co_cta ";
                       strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_for=a3.co_for AND a1.co_cta=a3.co_cta) ";
                       strSQL+=" INNER JOIN ";
                       strSQL+=" ( ";
                       strSQL+="    SELECT b1.co_emp, b1.co_for, b1.co_cta, b1.nd_salCta ";
                       strSQL+="    FROM tbm_salCtaForPlaCta AS b1 ";
                       strSQL+="    WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                       strSQL+="    AND b1.co_for=1 ";
                       strSQL+="    AND b1.co_per=" + strFecCor;
                       strSQL+=" ) AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_for=a4.co_for AND a1.co_cta=a4.co_cta) ";
                       strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                       strSQL+=" AND a1.co_For=1 ";
                       strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);

                       if (strEstFin.equals("BG")) //Balance General
                           strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                       else //Estado de Resultados
                           strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";

                       if (chkSalCer.isSelected())
                           strSQL+=" AND (a3.nd_salCta<>0 OR a4.nd_salCta<>0)";

                       strSQL+=" AND a1.tx_tipCta = 'D' ";
                       strSQL+=" AND a1.co_ctaDet>0 ";
                       strSQL+=" AND a1.ne_pad="+rst.getInt("co_cta");
                       strSQL+=" ORDER BY a1.tx_codCta, a1.tx_DesLar ";

                       //System.out.println("SQL-Cuentas Detalle:"+strSQL);
                       rstLocAux = stmLocAux.executeQuery(strSQL);

                       while (rstLocAux.next()) 
                       {
                           vecReg=new Vector();
                           vecReg.add(INT_TBL_DAT_LIN,"");

                           if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                               vecReg.add(INT_TBL_DAT_COD_SIS,null);
                           else
                               vecReg.add(INT_TBL_DAT_COD_SIS,rstLocAux.getString("co_cta"));

                           intNiv=rstLocAux.getInt("ne_niv");
                           vecReg.add(INT_TBL_DAT_NIV_CTA,"" + intNiv);

                           if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                               vecReg.add(INT_TBL_DAT_PAD_CTA,null);
                           else
                               vecReg.add(INT_TBL_DAT_PAD_CTA,rstLocAux.getString("ne_pad"));

                           vecReg.add(INT_TBL_DAT_COD_CTA,rstLocAux.getString("tx_codCta"));

                           //Generar la tabulacián para el campo "tx_desLar".
                           strAux="";

                           for (j=1; j<intNiv; j++)
                               strAux+="        ";

                           vecReg.add(INT_TBL_DAT_NOM_CTA,strAux + rstLocAux.getString("tx_desLar"));
                           dblSalMen=rstLocAux.getDouble("nd_salMen");
                           dblSalAcu=rstLocAux.getDouble("nd_salAcu");
                           vecReg.add(INT_TBL_DAT_SAL_MEN,"" + dblSalMen);
                           vecReg.add(INT_TBL_DAT_SAL_ACU,"" + dblSalAcu);

                           if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                               vecReg.add(INT_TBL_DAT_TIP_CTA,"N");
                           else
                               vecReg.add(INT_TBL_DAT_TIP_CTA,"" + rstLocAux.getString("tx_tipCta"));

                           if (intNiv==1)
                           {
                               //System.out.println("si");
                               dblTotMen+=dblSalMen;
                               dblTotAcu+=dblSalAcu;
                           }
                           vecDat.add(vecReg);
                       }
                       rstLocAux.close();
                       stmLocAux.close();
                       rstLocAux = null;
                       stmLocAux = null;
                       //</editor-fold>
                   } //if (blnCon)
                   else
                   {
                       break;
                   }
               } //while (rst.next())
            } //if (con != null)
            
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            if (blnButExp)
                lblMsgSis.setText("Se exportaron " + tblDat.getRowCount() + " registros.");
            else
                lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
            butExp.setText("Exportar");
            pgrSis.setIndeterminate(false);
       } 
       
       catch (Exception e)  
       {  lblMsgSis.setText("Listo");
          objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;     
       }
       
       return blnRes;
    } //Funcion procesoExportarDatos()
    //</editor-fold>
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
//                case 0: //Botón "Imprimir".
//                    generarRpt(0);
//                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
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
     * Limpia el Jtable.
     * Esta función es utilizada cada vez que se elija formato mercado de valores.
     */
    private void limpiarJtableDat()
    {
        vecDat.clear();
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
    }
    
    /**
     * Esta funcion permite mayorizar/desmayorizar las cuentas de acuerdo al criterio seleccionado.
     * @return true: Si se pudo realizar el proceso.
     * <BR>false: En el caso contrario.
     */
    private boolean remayorizar()
    {
        boolean blnRes=true, blnResRemayo;
        int intAnoCor;
        
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Remayorizando cuentas...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (con != null)
            {  ZafAsiDia objAsiDia = new ZafAsiDia(objParSis);
               intAnoCor = Integer.parseInt(cboAnoCor.getSelectedItem().toString());;
               blnResRemayo = objAsiDia.remayorizarPeriodo(con, objParSis.getCodigoEmpresa(), intAnoCor, (cboMesCor.getSelectedIndex()) + 1, false);
               
               if (blnResRemayo == false)
               {
                  mostrarMsgInf("<HTML>Se presentó un error en el proceso de remayorización</HTML>");
               }
            }
            
            con.close();
            con = null;
            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            butCon.setText("Consultar");
        } //try
        
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            butCon.setText("Consultar");
        }
        return blnRes;
    } //Funcion remayorizar()
}