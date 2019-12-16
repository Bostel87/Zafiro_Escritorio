/*
 * ZafCom23.java 
 *
 * Created on 02 de febrero de 2005, 17:03 PM     
 */
package Compras.ZafCom23;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafAsiDia.ZafAsiDia;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author  Eddye Lino 
 */
public class ZafCom23_TRA extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;                        //Línea
    final int INT_TBL_DAT_COD_SIS=1;                    //Código del item (Sistema).
    final int INT_TBL_DAT_COD_ALT=2;                    //Código del item (Alterno).
    final int INT_TBL_DAT_BUT_ITM=3;                    //Botón del item.
    final int INT_TBL_DAT_NOM_ITM=4;                    //Nombre del item.
    final int INT_TBL_DAT_DEC_UNI=5;                    //Descripción corta de la unidad de medida.
    final int INT_TBL_DAT_COD_BOD_ORG=6;                //Código de la bodega origen.
    final int INT_TBL_DAT_BUT_BOD_ORG=7;                //Botón de la bodega origen.
    final int INT_TBL_DAT_DEL_BOD_ORG=8;                //Descripción de la bodega origen.
    final int INT_TBL_DAT_COD_BOD_DES=9;                //Código de la bodega destino.
    final int INT_TBL_DAT_BUT_BOD_DES=10;               //Botón de la bodega destino.
    final int INT_TBL_DAT_DEL_BOD_DES=11;               //Descripción de la bodega destino.
    final int INT_TBL_DAT_CAN=12;                       //Cantidad.
    final int INT_TBL_DAT_COS_UNI=13;                   //Costo unitario.
    final int INT_TBL_DAT_COS_TOT=14;                   //Costo total.
    final int INT_TBL_DAT_AUX_COD_SIS=15;               //Auxiliar:Código del item (Sistema).
    final int INT_TBL_DAT_AUX_COD_BOD_ORG=16;           //Auxiliar:Código de la bodega origen.
    final int INT_TBL_DAT_AUX_COD_BOD_DES=17;           //Auxiliar:Código de la bodega destino.
    final int INT_TBL_DAT_AUX_CAN=18;                   //Auxiliar:Cantidad.
    
    final int INT_CFE_COD_SIS=0;                        //Columnas de las filas eliminadas: Código del item.
    final int INT_CFE_COD_BOD_ORG=1;                    //Columnas de las filas eliminadas: Código de la bodega origen.
    final int INT_CFE_COD_BOD_DES=2;                    //Columnas de las filas eliminadas: Código de la bodega destino.
    final int INT_CFE_CAN=3;                            //Columnas de las filas eliminadas: Cantidad.
    
    //Variables generales.
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
  
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    private ZafTblCelEdiBut objTblCelEdiBut;            //Editor: JButton en celda.
    private ZafTblCelEdiBut objTblCelEdiButBodOrg;      //Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiBut objTblCelEdiButBodDes;      //Editor: JButton en celda (Bodega destino).
    private ZafTblCelEdiTxtCon objTblCelEdiTxtCon;      //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConBod;   //Editor: JTextField de consulta en celda (Bodegas).
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblModLis objTblModLis;                  //Detectar cambios de valores en las celdas.
   
    private ZafAsiDia objAsiDia;
  
    private Connection  conCab, CONN_GLO=null,CONN_GLO_LOC=null;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux ;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    //Variables de la clase.
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;  // Para trabajar con la informacion de tipo de documento 
    
     public boolean blnAcepta = false;
     public boolean blnEst=false;
     javax.swing.JDialog jfrThis; //Hace referencia a this 
     int intCodMnuPrg=0;
     int intCodEmpGrp=0; 
     int intCodCot;
     final int intCodLocGrp=1;
      int intTipDocFac=0;
      int intCodDocFac=0;
      String strNumFacAnu="";
      String strNumTranRelFacAnu="";
      int intCodEmp=0;
      int intCodLoc=0;
      
    /** Crea una nueva instancia de la clase ZafCon06. */    
    //public ZafVen01_TRA(ZafParSis obj)
       public ZafCom23_TRA(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj ) 
       {
             super(parent, modal);
        try
        {
                
            initComponents();  
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
            
            intCodEmp = objParSis.getCodigoEmpresa();
	    intCodLoc = objParSis.getCodigoLocal();
	    
            objUti=new ZafUtil();
            
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);   
            jfrThis = this;
             
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    public ZafCom23_TRA(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCodEmpTrans, int intCodLocTrans, int intCodTipDoc, int intCodDoc) 
    {
        super(parent, modal);
        try    
        {
                
            initComponents();  
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            
            intCodEmp = intCodEmpTrans;
	    intCodLoc = intCodLocTrans;
	    intTipDocFac=intCodTipDoc;
            intCodDocFac=intCodDoc;
	    
            objUti=new ZafUtil();
            objAsiDia=new ZafAsiDia(objParSis);
            
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);   
            jfrThis = this;
             
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    public void Configura_ventana_consulta() 
    {
        if (!configurarFrm())
        {
            exitForm();
        }
        cargarDetReg();
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
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Título de la ventana");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 64));
        panGenCab.setLayout(null);

        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(100, 4, 56, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 100, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 24, 80, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(156, 4, 236, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(416, 4, 100, 20);

        lblNumDoc1.setText("Número de documento:");
        lblNumDoc1.setToolTipText("Número de documento");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(416, 24, 100, 20);

        txtNumDoc1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtNumDoc1);
        txtNumDoc1.setBounds(516, 24, 148, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 4, 32, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panGenCab.add(lblMonDoc);
        lblMonDoc.setBounds(416, 44, 100, 20);

        txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtMonDoc);
        txtMonDoc.setBounds(516, 44, 148, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

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
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        jPanel1.setMinimumSize(new java.awt.Dimension(40, 10));
        jPanel1.setPreferredSize(new java.awt.Dimension(60, 45));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Cancelar.");
        jButton1.setActionCommand("Cerrar");
        jButton1.setMaximumSize(new java.awt.Dimension(100, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        panFrm.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
          cerrarVentana(); 
    }//GEN-LAST:event_formWindowClosing

    public boolean ValidaFormaRet() 
    {
        if (txtCodDoc.getText().equals("")) 
        {
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "<< Es obligatorio que la Transferencia sea guardada  >>";
            obj.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }  
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           cerrarVentana(); 
    }//GEN-LAST:event_jButton1ActionPerformed
    
     private void cerrarVentana()
     {
        String strMsg = "¿Está Seguro que desea cancelar el proceso.?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
           cerrarObj();
           System.gc(); 
           dispose(); 
        }    
    }
   
    public boolean acepta() {
        return blnEst;
    }

    public void cerrarObj() 
    {
        try 
        {
            objUti = null;
        }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
    }
      
    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

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
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
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
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtMonDoc;
    private javax.swing.JTextField txtNumDoc1;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(516, 4, 148, 20);
            //Inicializar objetos.
        
            //objUti=new ZafUtil();
            //objAsiDia=new ZafAsiDia(objParSis);
            // System.out.println(""+ objParSis );
       
            // panBar.add(objTooBar);
            this.setTitle(objParSis.getNombreMenu() + " v0.4");
            lblTit.setText(objParSis.getNombreMenu());
            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtMonDoc.setBackground(objParSis.getColorCamposSistema());
            datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux, "dd/MM/yyyy"));
            datFecAux = null;
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(19);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Código");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_COD_BOD_ORG,"Cód.Bod.Org.");
            vecCab.add(INT_TBL_DAT_BUT_BOD_ORG,"");
            vecCab.add(INT_TBL_DAT_DEL_BOD_ORG,"Bod.Org.");
            vecCab.add(INT_TBL_DAT_COD_BOD_DES,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_BUT_BOD_DES,"");
            vecCab.add(INT_TBL_DAT_DEL_BOD_DES,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT,"Cos.Tot.");
            vecCab.add(INT_TBL_DAT_AUX_COD_SIS,"Aux.Cód.Sis.");
            vecCab.add(INT_TBL_DAT_AUX_COD_BOD_ORG,"Aux.Cód.Bod.Org.");
            vecCab.add(INT_TBL_DAT_AUX_COD_BOD_DES,"Aux.Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_AUX_CAN,"Aux.Can.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_ALT);
            arlAux.add("" + INT_TBL_DAT_COD_BOD_ORG);
            arlAux.add("" + INT_TBL_DAT_COD_BOD_DES);
            arlAux.add("" + INT_TBL_DAT_CAN);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            arlAux=new java.util.ArrayList();
            arlAux.add(INT_CFE_COD_SIS,"" + INT_TBL_DAT_AUX_COD_SIS);
            arlAux.add(INT_CFE_COD_BOD_ORG,"" + INT_TBL_DAT_AUX_COD_BOD_ORG);
            arlAux.add(INT_CFE_COD_BOD_DES,"" + INT_TBL_DAT_AUX_COD_BOD_DES);
            arlAux.add(INT_CFE_CAN,"" + INT_TBL_DAT_AUX_CAN);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
          //  objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(132);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_ORG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_DES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setResizable(false);
  
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            //vecAux.add("" + INT_TBL_DAT_COD_ALT);
           // vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_BOD_ORG);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD_ORG);
            vecAux.add("" + INT_TBL_DAT_COD_BOD_DES);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD_DES);
           // vecAux.add("" + INT_TBL_DAT_CAN);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_ORG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_DES).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" INNER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
            int intColVen[]=new int[5];
            intColVen[0]=2;
            intColVen[1]=3;
            intColVen[2]=4;
            intColVen[3]=5;
            intColVen[4]=7;
            int intColTbl[]=new int[5];
            intColTbl[0]=INT_TBL_DAT_COD_SIS;
            intColTbl[1]=INT_TBL_DAT_COD_ALT;
            intColTbl[2]=INT_TBL_DAT_NOM_ITM;
            intColTbl[3]=INT_TBL_DAT_DEC_UNI;
            intColTbl[4]=INT_TBL_DAT_COS_UNI;
            objTblCelEdiTxtCon=new ZafTblCelEdiTxtCon("Listado de inventario", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni", "Maestro,Sistema,Alterno,Nombre,Unidad,Stock,Costo", intColVen, intColTbl);
            objTblCelEdiTxtCon.setIndiceCampoBusqueda(2);
            objTblCelEdiTxtCon.setCampoBusqueda("LOWER(a1.tx_codAlt)", objTblCelEdiTxtCon.INT_CAM_STR);
            objTblCelEdiTxtCon.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellEditor(objTblCelEdiTxtCon);
            objTblCelEdiTxtCon.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Convertir la cadena a minúscula para evitar problemas con mayúsculas/minúsculas.
                    objTblCelEdiTxtCon.setText(objTblCelEdiTxtCon.getText().toLowerCase());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtCon.isConsultaAceptada())
                    {
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
            });
            
            objTblCelEdiBut=new ZafTblCelEdiBut("Listado de inventario", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni", "Maestro,Sistema,Alterno,Nombre,Unidad,Stock,Costo", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularToFil();
                    calcularTotDoc();
                    objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                }
            });
            intColVen=null;
            intColTbl=null;
              
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_bod, a1.tx_nom";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" INNER JOIN tbr_bodLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
            strSQL+=" AND a2.co_loc=" +intCodLocGrp;
            strSQL+=" AND a1.st_reg='A'";
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_BOD_ORG;
            intColTbl[1]=INT_TBL_DAT_DEL_BOD_ORG;
            objTblCelEdiTxtConBod=new ZafTblCelEdiTxtCon("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom", "Código,Nombre", intColVen, intColTbl);
            objTblCelEdiTxtConBod.setIndiceCampoBusqueda(1);
            objTblCelEdiTxtConBod.setCampoBusqueda("a1.co_bod", objTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtConBod.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setCellEditor(objTblCelEdiTxtConBod);
            objTblCelEdiTxtConBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiTxtConBod.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                   
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                   
                }
                
            });
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
            strSQL+=" INNER JOIN tbr_bodLoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
            strSQL+=" WHERE a1.co_emp=" +intCodEmpGrp;
            strSQL+=" AND a3.co_loc=" +intCodLocGrp;
            strSQL+=" AND a1.st_reg='A'";
            objTblCelEdiButBodOrg=new ZafTblCelEdiBut("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom, a2.nd_stkAct", "Código,Nombre,Stock", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setCellEditor(objTblCelEdiButBodOrg);
            objTblCelEdiButBodOrg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiButBodOrg.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                    
                }
                
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                   
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                   
                }
                
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiButBodOrg.setCondicionesSQL(" AND a2.co_itm=" + strAux);
                }
            });
            intColVen=null;
            intColTbl=null;
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_bod, a1.tx_nom";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" INNER JOIN tbr_bodLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
            strSQL+=" AND a2.co_loc=" +intCodLocGrp;
            strSQL+=" AND a1.st_reg='A'";
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_BOD_DES;
            intColTbl[1]=INT_TBL_DAT_DEL_BOD_DES;
            objTblCelEdiTxtConBod=new ZafTblCelEdiTxtCon("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom", "Código,Nombre", intColVen, intColTbl);
            objTblCelEdiTxtConBod.setIndiceCampoBusqueda(1);
            objTblCelEdiTxtConBod.setCampoBusqueda("a1.co_bod", objTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtConBod.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setCellEditor(objTblCelEdiTxtConBod);
            objTblCelEdiTxtConBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiTxtConBod.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
            });
               
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
            strSQL+=" INNER JOIN tbr_bodLoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
            strSQL+=" AND a3.co_loc=" +intCodLocGrp;
            strSQL+=" AND a1.st_reg='A'";
            objTblCelEdiButBodDes=new ZafTblCelEdiBut("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom, a2.nd_stkAct", "Código,Nombre,Stock", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setCellEditor(objTblCelEdiButBodDes);
            objTblCelEdiButBodDes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiButBodDes.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiButBodDes.setCondicionesSQL(" AND a2.co_itm=" + strAux);
                }
            });
            intColVen=null;
            intColTbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularToFil();
                    calcularTotDoc();
                    objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                }
            });
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public boolean insertar() {
        return true;
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
       
        for (int intRowVal = 0; intRowVal < tblDat.getRowCount(); intRowVal++) {

            if (tblDat.getValueAt(intRowVal, INT_TBL_DAT_COD_SIS) != null) {

                double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_DAT_COS_UNI) == null) ? "0" : tblDat.getValueAt(intRowVal, INT_TBL_DAT_COS_UNI).toString());

                if (dblCan <= 0.00) {
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                    String strTit, strMsg;

                    strTit = "Mensaje del sistema Zafiro";
                    strMsg = "Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.changeSelection(intRowVal, INT_TBL_DAT_COS_UNI, true, true);
                    return false;
                }
            }
        }
        
        //Validar que el "Diario esté cuadrado".
        if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que el "Monto del diario" sea igual al monto del documento.
        if (!objAsiDia.isDocumentoCuadrado(txtMonDoc.getText()))
        {
            mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
            txtMonDoc.selectAll();
            txtMonDoc.requestFocus();
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumFil, intCodItm, intCodBod, i, intRegDoc=0 ;
        double dblCan, dblCosUni;
        boolean blnRes=true;
        try
        {
//            objTooBar.setMenSis("Obteniendo datos...");
            CONN_GLO_LOC=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (CONN_GLO_LOC!=null)
            {
                stm=CONN_GLO_LOC.createStatement();
                //Armar la sentencia SQL.  
                strSQL="";
                
                strSQL="select a1.co_tipdoc, td.tx_descor, td.tx_deslar,    a1.ne_numFil, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.tx_uniMed, a1.co_bod, a2.tx_nom, a1.nd_can, a1.nd_cosUni " +
                " ,b.ne_numdoc, b.co_doc, b.tx_obs1,  b.tx_obs2  FROM tbm_detMovInv AS a1  " +
                " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a1.co_emp AND b.co_loc=a1.co_loc AND b.co_tipdoc=a1.co_tipdoc AND b.co_doc=a1.co_doc) "+
                " inner join tbm_cabTipDoc as td on (td.co_emp=a1.co_emp and td.co_loc=1  and  td.co_tipdoc=a1.co_tipdoc) "+    
                " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) " +
                " where a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+" and a1.co_tipdoc="+intTipDocFac+" and a1.co_doc="+intCodDocFac+"  " +
                " ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                
                 
                System.out.println(""+ strSQL );
                
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros. 
//                objTooBar.setMenSis("Cargando datos...");
                i=1;
                while (rst.next())
                {
                    
                    strNumTranRelFacAnu=rst.getString("ne_numdoc");
                    
                    if(intRegDoc==0){
                     txtCodTipDoc.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
                     txtDesCorTipDoc.setText(((rst.getString("tx_descor")==null)?"":rst.getString("tx_descor")));
                     txtDesLarTipDoc.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                     
                    
                     txtNumDoc1.setText(((rst.getString("ne_numdoc")==null)?"":rst.getString("ne_numdoc")));
                     txtCodDoc.setText(((rst.getString("co_doc")==null)?"":rst.getString("co_doc")));
                     
                     txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                     txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));        
                             
                     intRegDoc=1;
                    }    
                    
                    
                    intNumFil=rst.getInt("ne_numFil");
                    if (vecDat.size()==i && intNumFil==i)
                    {
                        //Ingresos de inventario.
                        vecAux=(Vector)vecDat.get(i-1);
                        intCodBod=rst.getInt("co_bod");
                        vecAux.setElementAt("" + intCodBod,INT_TBL_DAT_COD_BOD_DES);
                        vecAux.setElementAt(rst.getString("tx_nom"),INT_TBL_DAT_DEL_BOD_DES);
                        dblCan=rst.getDouble("nd_can");
                        dblCosUni=rst.getDouble("nd_cosUni");
                        vecAux.setElementAt("" + dblCan,INT_TBL_DAT_CAN);
                        vecAux.setElementAt("" + dblCosUni,INT_TBL_DAT_COS_UNI);
                        vecAux.setElementAt("" + (dblCan*dblCosUni),INT_TBL_DAT_COS_TOT);
                        vecAux.setElementAt("" + intCodBod,INT_TBL_DAT_AUX_COD_BOD_DES);
                        vecAux.setElementAt("" + dblCan,INT_TBL_DAT_AUX_CAN);
                        i++;
                    }
                    else
                    {
                        //Egresos de inventario.
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        intCodItm=rst.getInt("co_itm");
                        vecReg.add(INT_TBL_DAT_COD_SIS,"" + intCodItm);
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_BUT_ITM,null);
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_uniMed"));
                        intCodBod=rst.getInt("co_bod");
                        vecReg.add(INT_TBL_DAT_COD_BOD_ORG,"" + intCodBod);
                        vecReg.add(INT_TBL_DAT_BUT_BOD_ORG,null);
                        vecReg.add(INT_TBL_DAT_DEL_BOD_ORG,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_COD_BOD_DES,null);
                        vecReg.add(INT_TBL_DAT_BUT_BOD_DES,null);
                        vecReg.add(INT_TBL_DAT_DEL_BOD_DES,null);
                        vecReg.add(INT_TBL_DAT_CAN,null);
                        vecReg.add(INT_TBL_DAT_COS_UNI,null);
                        vecReg.add(INT_TBL_DAT_COS_TOT,null);
                        vecReg.add(INT_TBL_DAT_AUX_COD_SIS,"" + intCodItm);
                        vecReg.add(INT_TBL_DAT_AUX_COD_BOD_ORG,"" + intCodBod);
                        vecReg.add(INT_TBL_DAT_AUX_COD_BOD_DES,null);
                        vecReg.add(INT_TBL_DAT_AUX_CAN,null);
                       // vecReg.add(INT_TBL_DAT_AUX_COD_SIA,rst.getString("co_itmAct"));
                        vecDat.add(vecReg);
                    }
//                    if (vecDat.size()==i && intNumFil==i)
//                    {
//                        //Ingresos de inventario.
//                        vecAux=(Vector)vecDat.get(i-1); 
//                        intCodBod=rst.getInt("co_bod");
//                        vecAux.setElementAt("" + intCodBod, INT_TBL_DAT_COD_BOD_ORG );
//                        vecAux.setElementAt(rst.getString("tx_nom"),INT_TBL_DAT_DEL_BOD_ORG);
//                        
//                        dblCan=rst.getDouble("nd_can");
//                         System.out.println("Cantidad:  "+ dblCan );
//                        
//                        
//                        dblCosUni=rst.getDouble("nd_cosUni");
//                        vecAux.setElementAt("" + dblCan,INT_TBL_DAT_CAN);
//                        vecAux.setElementAt("" + dblCosUni,INT_TBL_DAT_COS_UNI);
//                        vecAux.setElementAt("" + (dblCan*dblCosUni),INT_TBL_DAT_COS_TOT);
//                        vecAux.setElementAt("" + intCodBod,INT_TBL_DAT_AUX_COD_BOD_DES);
//                        vecAux.setElementAt("" + dblCan,INT_TBL_DAT_AUX_CAN);
//                        i++;
//                    }
//                    else
//                    {
//                        //Egresos de inventario.
//                        vecReg=new Vector();
//                        vecReg.add(INT_TBL_DAT_LIN,"");
//                        intCodItm=rst.getInt("co_itm");
//                        vecReg.add(INT_TBL_DAT_COD_SIS,"" + intCodItm);
//                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
//                        vecReg.add(INT_TBL_DAT_BUT_ITM,null);
//                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
//                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_uniMed"));
//                        
//                        vecReg.add(INT_TBL_DAT_COD_BOD_ORG,null);
//                        vecReg.add(INT_TBL_DAT_BUT_BOD_ORG,null);
//                        vecReg.add(INT_TBL_DAT_DEL_BOD_ORG,null);
//                        
//                        intCodBod=rst.getInt("co_bod");
//                        vecReg.add(INT_TBL_DAT_COD_BOD_DES ,"" + intCodBod);
//                        vecReg.add(INT_TBL_DAT_BUT_BOD_DES ,null);
//                        vecReg.add(INT_TBL_DAT_DEL_BOD_DES ,rst.getString("tx_nom"));
//                        
//                        vecReg.add(INT_TBL_DAT_CAN,null);
//                        vecReg.add(INT_TBL_DAT_COS_UNI,null);
//                        vecReg.add(INT_TBL_DAT_COS_TOT,null);
//                        vecReg.add(INT_TBL_DAT_AUX_COD_SIS,"" + intCodItm);
//                        vecReg.add(INT_TBL_DAT_AUX_COD_BOD_ORG,"" + intCodBod);
//                        vecReg.add(INT_TBL_DAT_AUX_COD_BOD_DES,null);
//                        vecReg.add(INT_TBL_DAT_AUX_CAN,null);
//                        vecDat.add(vecReg);
//                    }
                }
                rst.close();
                stm.close();
                
                rst=null;
                stm=null;
             
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                
                   calcularTotDoc();
                   
                 //  objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                   
                CONN_GLO_LOC.close();
                CONN_GLO_LOC =null;
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
 

   
    
    /**
     * Esta función calcula el total de la fila.
     */
    private void calcularToFil()
    {
        int intFilSel;
        intFilSel=tblDat.getSelectedRow();
        objTblMod.setValueAt("" + objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN))*objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI)), intFilSel, INT_TBL_DAT_COS_TOT);
    }
    
    /**
     * Esta función calcula el total del documento.
     */
    private void calcularTotDoc()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {
            intFilPro=objTblMod.getRowCount();
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            //Calcular la diferencia.
            txtMonDoc.setText("" + objUti.redondear(dblTot,objParSis.getDecimalesBaseDatos()));
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
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
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_COD_BOD_ORG:
                    strMsg="Código de la bodega origen";
                    break;
                case INT_TBL_DAT_DEL_BOD_ORG:
                    strMsg="Nombre de la bodega origen";
                    break;
                case INT_TBL_DAT_COD_BOD_DES:
                    strMsg="Código de la bodega destino";
                    break;
                case INT_TBL_DAT_DEL_BOD_DES:
                    strMsg="Nombre de la bodega destino";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg="Costo total";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularTotDoc();
                    objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
    
}