/*
 * ZafCon20.java
 *
 * Created on 02 de febrero de 2005, 17:03 PM
 */
package Compras.ZafCom20;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafReaVenComAut.ZafReaVenComAut;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom20 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_SIS=1;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=2;                     //Código alterno del ítem.
    static final int INT_TBL_DAT_COD_ALTDOS=3;                  //Código alterno 2 del ítem.
    static final int INT_TBL_DAT_BUT_ITM=4;                     //Botón del item.
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_DEC_UNI=6;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_COD_BOD_ORG=7;                 //Código de la bodega origen.
    static final int INT_TBL_DAT_BUT_BOD_ORG=8;                 //Botón de la bodega origen.
    static final int INT_TBL_DAT_DEL_BOD_ORG=9;                 //Descripción de la bodega origen.
    static final int INT_TBL_DAT_COD_BOD_DES=10;                //Código de la bodega destino.
    static final int INT_TBL_DAT_BUT_BOD_DES=11;                //Botón de la bodega destino.
    static final int INT_TBL_DAT_DEL_BOD_DES=12;                //Descripción de la bodega destino.
    static final int INT_TBL_DAT_CAN=13;                        //Cantidad.
    static final int INT_TBL_DAT_COS_UNI=14;                    //Costo unitario.
    static final int INT_TBL_DAT_COS_TOT=15;                    //Costo total.
    static final int INT_TBL_DAT_PES_UNI=16;                    //Costo unitario.
    static final int INT_TBL_DAT_PES_TOT=17;                    //Peso total.
    static final int INT_TBL_DAT_AUX_COD_SIS=18;                //Auxiliar:Código del item (Sistema).
    static final int INT_TBL_DAT_AUX_COD_BOD_ORG=19;            //Auxiliar:Código de la bodega origen.
    static final int INT_TBL_DAT_AUX_COD_BOD_DES=20;            //Auxiliar:Código de la bodega destino.
    static final int INT_TBL_DAT_AUX_CAN=21;                    //Auxiliar:Cantidad.
    static final int INT_TBL_DAT_AUX_COD_SIA=22;                //Auxiliar:Código actual del item(Sistema).
    
    static final int INT_CFE_COD_SIS=0;                         //Columnas de las filas eliminadas: Código del item.
    static final int INT_CFE_COD_BOD_ORG=1;                     //Columnas de las filas eliminadas: Código de la bodega origen.
    static final int INT_CFE_COD_BOD_DES=2;                     //Columnas de las filas eliminadas: Código de la bodega destino.
    static final int INT_CFE_CAN=3;                             //Columnas de las filas eliminadas: Cantidad.
    
    //ArrayList para consultar
    private ArrayList arlDatConTrs, arlRegConTrs;
    private static final int INT_ARL_CON_TRS_COD_EMP=0;  
    private static final int INT_ARL_CON_TRS_COD_LOC=1;   
    private static final int INT_ARL_CON_TRS_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_TRS_COD_DOC=3;  
    private int intIndiceTrs=0;
    
    //Variables generales.
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;        //Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodDes;        //Editor: JButton en celda (Bodega destino).
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoBodOrg;        //Editor: JTextField de consulta en celda (Bodega origen).
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoBodDes;        //Editor: JTextField de consulta en celda (Bodega destino).
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenCon vcoBod;                                   //Ventana de consulta "Bodega".
    private MiToolBar objTooBar;                                //Barra de botones.
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafAsiDia objAsiDia;                                //Reportes del Sistema.
    private ZafReaVenComAut objReaVenComAut;                    //Clase para generar compras/ventas automáticas.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnHayCam;                                  //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private int intSig=1;                                       //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    //Variables de la clase.
    private int intCodEmp, intCodLoc;                           //Código de la empresa y local.
    private String strEstRegRep;                                //Estado del registro a replicar.
    private String strMerIngEgrFisBodTipDoc;                    //Determina si la mercadería ingresa/egresa físicamente a Bodega según el Tipo de documento.
    private Date datFecDoc;                                     //Fecha del documento.
    private String strMsgItmSinStkSuf;                          //Mensaje "Items sin stock suficiente".
    private Boolean blnGenComVenAut;                            //Determina si se deben generar compras y ventas automáticas.
    
    private String strVersion=" v0.38";
    
    /**
     * Crea una nueva instancia de la clase ZafCom20.
     * @param obj El objeto ZafParSis.
     */
    public ZafCom20(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            objReaVenComAut=new ZafReaVenComAut(this, objParSis);
            configurarFrm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * Crea una nueva instancia de la clase ZafCom20 y carga el documento especificado.
     * @param obj El objeto ZafParSis.
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public ZafCom20(ZafParSis obj, Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento)
    {
        this(obj);
        cargarDocumento(codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento);
    }

    /**
     * Constructor utilizado como HashMap para usarlo cuando se llama a la clase en forma dinamica.
     * @author TonySanginez
     * @fecha 18/May/2016
     * @param map 
     */
    public ZafCom20(HashMap map) 
    {
        ZafParSis obj;
        Integer codigoEmpresa;
        Integer codigoLocal;
        Integer codigoTipoDocumento;
        Integer codigoDocumento;
        obj = (ZafParSis) map.get("objParSis");
        codigoEmpresa = Integer.valueOf(new String(map.get("strCodEmp").toString()));
        codigoLocal = Integer.valueOf(new String(map.get("strCodLoc").toString()));
        codigoTipoDocumento = Integer.valueOf(new String(map.get("strCodTipDoc").toString()));
        codigoDocumento = Integer.valueOf(new String(map.get("strCodDoc").toString()));
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            objReaVenComAut=new ZafReaVenComAut(this, objParSis);
            configurarFrm();
            agregarDocLis();
            cargarDocumento(codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento);
            objTooBar.setVisible(false);
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }    
    
    /**
     * Esta función carga el documento especificado.
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public void cargarDocumento(Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento)
    {
        intCodEmp=codigoEmpresa.intValue();
        intCodLoc=codigoLocal.intValue();
        txtCodTipDoc.setText(codigoTipoDocumento.toString());
        txtCodDoc.setText(codigoDocumento.toString());
        objTooBar.setVisibleModificar(false);
        objTooBar.setVisibleEliminar(false);
        objTooBar.setVisibleAnular(false);
        objTooBar.setEstado('c');
        objTooBar.consultar();
        //consultarReg();
        objTooBar.setEstado('w');
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
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
        lblPesDoc = new javax.swing.JLabel();
        txtPesDoc = new javax.swing.JTextField();
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

        setClosable(true);
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
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
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

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 24, 100, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(392, 4, 20, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
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
        txtNumDoc1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusGained(evt);
            }
        });
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

        lblPesDoc.setText("Peso (Kg):");
        lblPesDoc.setToolTipText("Peso (Kg)");
        panGenCab.add(lblPesDoc);
        lblPesDoc.setBounds(0, 44, 100, 20);

        txtPesDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtPesDoc);
        txtPesDoc.setBounds(100, 44, 100, 20);

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

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
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

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
            {
                if (txtDesLarTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesCorTipDoc.setText("");
                }
                else
                {
                    mostrarVenConTipDoc(2);
                }
            }
            else
                txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
            {
                if (txtDesCorTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesLarTipDoc.setText("");
                }
                else
                {
                    mostrarVenConTipDoc(1);
                }
            }
            else
                txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
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
        catch (Exception e)
        {
            dispose();
            //objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_exitForm

    private void txtNumDoc1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusGained
        txtNumDoc1.selectAll();
    }//GEN-LAST:event_txtNumDoc1FocusGained

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPesDoc;
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
    private javax.swing.JTextField txtPesDoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(516, 4, 148, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            objDocLis=new ZafDocLis();
            
            //Se deshabilito las opciones de Insertar, Modificar, Eliminar, Anular para el proyecto de transferencias de inventario.
            objTooBar.setVisibleInsertar(false); 
            objTooBar.setEnabledInsertar(false);
            objTooBar.setVisibleModificar(false); 
            objTooBar.setEnabledModificar(false);
            objTooBar.setVisibleEliminar(false); 
            objTooBar.setEnabledEliminar(false);
            objTooBar.setVisibleAnular(false); 
            objTooBar.setEnabledAnular(false);
            
            panBar.add(objTooBar);
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc1.setBackground(objParSis.getColorCamposObligatorios());
            txtPesDoc.setBackground(objParSis.getColorCamposSistema());
            txtMonDoc.setBackground(objParSis.getColorCamposSistema());
            //Configurar ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConItm();
            configurarVenConBod();
            //Configurar los JTables.
            configurarTblDat();
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
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
            vecCab=new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALTDOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Uni.");
            vecCab.add(INT_TBL_DAT_COD_BOD_ORG,"Cód.Bod.Org.");
            vecCab.add(INT_TBL_DAT_BUT_BOD_ORG,"");
            vecCab.add(INT_TBL_DAT_DEL_BOD_ORG,"Bod.Org.");
            vecCab.add(INT_TBL_DAT_COD_BOD_DES,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_BUT_BOD_DES,"");
            vecCab.add(INT_TBL_DAT_DEL_BOD_DES,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT,"Cos.Tot.");
            vecCab.add(INT_TBL_DAT_PES_UNI,"Pes.Uni.");
            vecCab.add(INT_TBL_DAT_PES_TOT,"Pes.Tot.");
            vecCab.add(INT_TBL_DAT_AUX_COD_SIS,"Aux.Cód.Sis.");
            vecCab.add(INT_TBL_DAT_AUX_COD_BOD_ORG,"Aux.Cód.Bod.Org.");
            vecCab.add(INT_TBL_DAT_AUX_COD_BOD_DES,"Aux.Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_AUX_CAN,"Aux.Can.");
            vecCab.add(INT_TBL_DAT_AUX_COD_SIA,"Aux.Cód.Sis.Act.");
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
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALTDOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(102);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_ORG).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_DES).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_COD_BOD_ORG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_COD_BOD_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_CAN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_COD_SIA, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_BOD_ORG);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD_ORG);
            vecAux.add("" + INT_TBL_DAT_COD_BOD_DES);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD_DES);
            vecAux.add("" + INT_TBL_DAT_CAN);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
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
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALTDOS).setCellRenderer(objTblCelRenLbl);
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
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[7];
            intColVen[0]=INT_TBL_DAT_COD_ALT;
            intColVen[1]=INT_TBL_DAT_COD_ALTDOS;
            intColVen[2]=INT_TBL_DAT_BUT_ITM;
            intColVen[3]=INT_TBL_DAT_NOM_ITM;
            intColVen[4]=INT_TBL_DAT_DEC_UNI;
            intColVen[5]=INT_TBL_DAT_BUT_BOD_ORG;
            intColVen[6]=INT_TBL_DAT_DEL_BOD_ORG;
            int intColTbl[]=new int[7];
            intColTbl[0]=INT_TBL_DAT_COD_SIS;
            intColTbl[1]=INT_TBL_DAT_COD_ALT;
            intColTbl[2]=INT_TBL_DAT_COD_ALTDOS;
            intColTbl[3]=INT_TBL_DAT_NOM_ITM;
            intColTbl[4]=INT_TBL_DAT_DEC_UNI;
            intColTbl[5]=INT_TBL_DAT_COS_UNI;
            intColTbl[6]=INT_TBL_DAT_PES_UNI;
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(2);
                    vcoItm.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoItm.isConsultaAceptada())
                    {
                        //TEMPORAL: Si el costo unitario es negativo lo cambio a cero para poder guardar el documento.
                        //Si no lo cambio da un error y no permite guardar.
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COS_UNI))<0)
                        {
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_COS_UNI);
                        }
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    }
                }
            });
           
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada())
                    {
                        //TEMPORAL: Si el costo unitario es negativo lo cambio a cero para poder guardar el documento.
                        //Si no lo cambio da un error y no permite guardar.
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COS_UNI))<0)
                        {
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_COS_UNI);
                        }
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_BOD_ORG;
            intColTbl[1]=INT_TBL_DAT_DEL_BOD_ORG;
            objTblCelEdiTxtVcoBodOrg=new ZafTblCelEdiTxtVco(tblDat, vcoBod, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setCellEditor(objTblCelEdiTxtVcoBodOrg);
            objTblCelEdiTxtVcoBodOrg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoBod.setCampoBusqueda(0);
                    vcoBod.setCriterio1(11);
                    vcoBod.limpiar();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)));
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)));
                        }
                    }
                    else
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('E', 'A')");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('E', 'A')" + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)));
                        }
                    }
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiTxtVcoBodOrg.setCancelarEdicion(true);
                        }
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            objTblCelEdiTxtVcoBodOrg.setCancelarEdicion(true);
                        }
                        else if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiTxtVcoBodOrg.setCancelarEdicion(true);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoBodOrg.isConsultaAceptada())
                    {
                        objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    }
                }
            });
            
            objTblCelEdiButVcoBodOrg=new ZafTblCelEdiButVco(tblDat, vcoBod, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setCellEditor(objTblCelEdiButVcoBodOrg);
            objTblCelEdiButVcoBodOrg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoBod.limpiar();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)));
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)));
                        }
                    }
                    else
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('E', 'A')");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('E', 'A')" + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_DES)));
                        }
                    }
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiButVcoBodOrg.setCancelarEdicion(true);
                        }
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            objTblCelEdiButVcoBodOrg.setCancelarEdicion(true);
                        }
                        else if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiButVcoBodOrg.setCancelarEdicion(true);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoBodOrg.isConsultaAceptada())
                    {
                        objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_BOD_DES;
            intColTbl[1]=INT_TBL_DAT_DEL_BOD_DES;
            objTblCelEdiTxtVcoBodDes=new ZafTblCelEdiTxtVco(tblDat, vcoBod, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setCellEditor(objTblCelEdiTxtVcoBodDes);
            objTblCelEdiTxtVcoBodDes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoBod.setCampoBusqueda(0);
                    vcoBod.setCriterio1(11);
                    vcoBod.limpiar();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)));
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)));
                        }
                    }
                    else
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('I', 'A')");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('I', 'A')" + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)));
                        }
                    }
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiTxtVcoBodDes.setCancelarEdicion(true);
                        }
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            objTblCelEdiTxtVcoBodDes.setCancelarEdicion(true);
                        }
                        else if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiTxtVcoBodDes.setCancelarEdicion(true);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoBodDes.isConsultaAceptada())
                    {
                        objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    }
                }
            });
            
            objTblCelEdiButVcoBodDes=new ZafTblCelEdiButVco(tblDat, vcoBod, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setCellEditor(objTblCelEdiButVcoBodDes);
            objTblCelEdiButVcoBodDes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoBod.limpiar();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)));
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)));
                        }
                    }
                    else
                    {
                        //Validar que no se pueda seleccionar la misma bodega como bodega origen y destino.
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)).equals(""))
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('I', 'A')");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a3.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_itm=" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)) + " AND a3.tx_natBod IN ('I', 'A')" + " AND a1.co_bod<>" + objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_BOD_ORG)));
                        }
                    }
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiButVcoBodDes.setCancelarEdicion(true);
                        }
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            objTblCelEdiButVcoBodDes.setCancelarEdicion(true);
                        }
                        else if (objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS)).equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                            objTblCelEdiButVcoBodDes.setCancelarEdicion(true);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoBodDes.isConsultaAceptada())
                    {
                        objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    }
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
                    objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                }
            });
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public boolean anular()
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public void clickAceptar()
        {
            
        }

        public void clickAnterior() 
        {
            try{
                if(arlDatConTrs.size()>0){
                    if(intIndiceTrs>0){
                        intIndiceTrs--;
                        cargarReg();
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular()
        {
            
        }

        public void clickCancelar()
        {

        }

        public void clickConsultar() 
        {
            switch (objTooBar.getEstado())
            {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    txtDesCorTipDoc.requestFocus();
                    break;
                case 'j':
                    break;
            }
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }

        public void clickEliminar()
        {

        }

        public void clickFin()
        {
            try{
                 if(arlDatConTrs.size()>0){
                    if(intIndiceTrs<arlDatConTrs.size()-1){
                        intIndiceTrs=arlDatConTrs.size()-1;
                        cargarReg();
                    }
                 }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir()
        {
            
        }

        public void clickInicio()
        {
            try{
                if(arlDatConTrs.size()>0){
                    if(intIndiceTrs>0){
                        intIndiceTrs=0;
                        cargarReg();
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar()
        {
            try
            {
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
                txtCodDoc.setEditable(false);
                txtPesDoc.setEditable(false);
                txtMonDoc.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                if (blnGenComVenAut==null)
                    blnGenComVenAut=Boolean.valueOf(generarComVenAut());
                mostrarTipDocPre();
                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
                objAsiDia.setEditable(true);
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                objAsiDia.setDiarioModificado(false);
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

        public void clickModificar()
        {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtMonDoc.setEditable(false);
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
            objAsiDia.setEditable(true);
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }

        public void clickSiguiente()
        {
            try{
                 if(arlDatConTrs.size()>0){
                    if(intIndiceTrs < arlDatConTrs.size()-1){
                        intIndiceTrs++;
                        cargarReg();
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickVisPreliminar() 
        {
        }
        
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
            try
            {
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if(arlDatConTrs.size()>0){
                   if(intIndiceTrs < arlDatConTrs.size()-1){
                        intIndiceTrs++;
                        cargarReg();
                   }
                   else
                   {
                       objTooBar.setEstadoRegistro("Eliminado");
                       limpiarFrm();
                   }
                }
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return true;
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                objTblMod.clearRowHeaderRaise();
                limpiarFrm();
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                objAsiDia.setDiarioModificado(false);
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
            return blnRes;
        }
        
        public boolean vistaPreliminar()
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }
        
        public boolean aceptar()
        {
            return true;
        }
        
        public boolean imprimir()
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }
        
        public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
            if (objTblMod.getRowCountTrue()==0)
            {
                if (mostrarMsgCon("El documento no tiene ninguna fila en el detalle.\n¿Esta seguro que desea INSERTAR el documento?", false)==javax.swing.JOptionPane.NO_OPTION)
                    return false;
            }
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
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
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!(blnHayCam || objTblMod.isDataModelChanged() || objAsiDia.isDiarioModificado()))
            {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
                return false;
            }
            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            return true;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            if (!strAux.equals("Anulado"))
            {
                //Validar que exista el stock.
                if (!isStkBodSuf())
                {
                    mostrarMsgInf("<HTML>Algunos items no tienen stock suficiente para hacer la transferencia.<BR>Revise su inventario y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            //Validar que exista el stock.
            if (!isStkBodSuf())
            {
                mostrarMsgInf("<HTML>Algunos items no tienen stock suficiente para hacer la transferencia.<BR>Revise su inventario y vuelva a intentarlo.</HTML>");
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
        
        public boolean afterInsertar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarRegIns();
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            strEstRegRep="I";
            actualizarValColAux();
            objTblMod.initRowsState();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
            objTblMod.clearDataSavedBeforeRemoveRow();
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            actualizarValColAux();
            objTblMod.initRowsState();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }

        public boolean afterEliminar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }
        
        public boolean afterCancelar()
        {
            return true;
        }

        public boolean beforeClickInicio()
        {
            return isRegPro();
        }
        
        public boolean beforeClickAnterior()
        {
            return isRegPro();
        }

        public boolean beforeClickSiguiente()
        {
            return isRegPro();
        }

        public boolean beforeClickFin()
        {
            return isRegPro();
        }

        public boolean beforeClickInsertar()
        {
            return isRegPro();
        }

        public boolean beforeClickConsultar()
        {
            return isRegPro();
        }

        public boolean beforeClickEliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickAnular()
        {
            return isRegPro();
        }

        public boolean beforeClickImprimir()
        {
            return isRegPro();
        }

        public boolean beforeClickVistaPreliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickCancelar()
        {
            return isRegPro();
        }
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int i, intCodBodOrg, intCodBodDes;
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
        //Validar que el JTable de detalle esté completo.
        objTblMod.removeEmptyRows();
        if (!objTblMod.isAllRowsComplete())
        {
            mostrarMsgInf("<HTML>El detalle del documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.changeSelection(0, INT_TBL_DAT_COD_ALT, true, true);
            tblDat.requestFocus();
            return false;
        }
        //Validar que sólo se pueda utilizar una bodega origen y una bodega destino.
        intCodBodOrg=0;
        intCodBodDes=0;
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (i==0)
            {
                intCodBodOrg=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_ORG).toString());
                intCodBodDes=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_DES).toString());
            }
            else
            {
                if (intCodBodOrg!=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_ORG).toString()))
                {
                    //Ubicar el foco en la fila seleccionada.
                    tblDat.changeSelection(i, INT_TBL_DAT_COD_BOD_ORG, false, false);
                    mostrarMsgInf("<HTML>La <FONT COLOR=\"blue\">bodega origen</FONT> de la fila seleccionada es incorrecta.<BR>Todas las filas deben tener la misma bodega origen.<BR>Corrija dicha bodega y vuelva a intentarlo.</HTML>");
                    tblDat.requestFocus();
                    return false;
                }
                else if (intCodBodDes!=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_DES).toString()))
                {
                    //Ubicar el foco en la fila seleccionada.
                    tblDat.changeSelection(i, INT_TBL_DAT_COD_BOD_DES, false, false);
                    mostrarMsgInf("<HTML>La <FONT COLOR=\"blue\">bodega destino</FONT> de la fila seleccionada es incorrecta.<BR>Todas las filas deben tener la misma bodega destino.<BR>Corrija dicha bodega y vuelva a intentarlo.</HTML>");
                    tblDat.requestFocus();
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
        //Validar el "Número de documento".
        if (!objUti.isNumeroDocumentoValido(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), 1, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), (objTooBar.getEstado()=='m'?Integer.valueOf(txtCodDoc.getText()):null), txtNumDoc1))
        {
            tabFrm.setSelectedIndex(0);
            txtNumDoc1.selectAll();
            txtNumDoc1.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
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
                if (insertarCab())
                {
                    if (insertarDet())
                    {
                        if (insertarTbrDetMovInv())
                        {
                            if (actualizarTbmInvBod(0))
                            {
                                if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))
                                {
                                    if (actualizarSecUltDocTbmCabMovInv())
                                    {
                                        if (blnGenComVenAut.booleanValue())
                                        {
                                            if (objReaVenComAut.realizaVenComAut(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                                            {
                                                if (isStkBodSuf())
                                                {
                                                    recostearItm();
                                                    con.commit();
                                                    blnRes=true;
                                                }
                                                else
                                                {
                                                    mostrarMsgInf(strMsgItmSinStkSuf);
                                                    con.rollback();
                                                }
                                            }
                                            else
                                                con.rollback();
                                        }
                                        else
                                        {
                                            if (isStkBodSuf())
                                            {
                                                recostearItm();
                                                con.commit();
                                                blnRes=true;
                                            }
                                            else
                                            {
                                                mostrarMsgInf(strMsgItmSinStkSuf);
                                                con.rollback();
                                            }
                                        }
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

    /**
     * Esta función permite consultar el registro insertado.
     * @return true: Si se pudo consultar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarRegIns()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.co_doc=" + txtCodDoc.getText();
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    rst.last();
                    objTooBar.setMenSis("Se encontró " + rst.getRow() + " registro");
                    objTooBar.setPosicionRelativa("1 / 1");
                    objTooBar.setEstadoRegistro("Activo");
                    rst.first();
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
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
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND a1.st_reg<>'E'";
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.st_reg<>'E'";
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc=" + strAux;
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc=" + strAux;
                strAux=txtNumDoc1.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc=" + strAux;
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rstCab=stmCab.executeQuery(strSQL);
                arlDatConTrs = new ArrayList();
                while(rstCab.next())
                {
                    arlRegConTrs = new ArrayList();
                    arlRegConTrs.add(INT_ARL_CON_TRS_COD_EMP,rstCab.getInt("co_emp"));
                    arlRegConTrs.add(INT_ARL_CON_TRS_COD_LOC,rstCab.getInt("co_loc"));
                    arlRegConTrs.add(INT_ARL_CON_TRS_COD_TIPDOC,rstCab.getInt("co_tipDoc"));
                    arlRegConTrs.add(INT_ARL_CON_TRS_COD_DOC,rstCab.getInt("co_doc"));
                    arlDatConTrs.add(arlRegConTrs);
                }
                stmCab.close();
                stmCab=null;
                rstCab.close();
                rstCab=null;
                conCab.close();
                conCab=null;
                
                if(arlDatConTrs.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConTrs.size()) + " registros");
                    intIndiceTrs=arlDatConTrs.size()-1;
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
     * Esta función actualiza el registro en la base de datos.
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
                if (actualizarCab())
                {
                    if (eliminarTbrDetMovInv())
                    {
                        if (eliminarDet())
                        {
                            if (insertarDet())
                            {
                                if (insertarTbrDetMovInv())
                                {
                                    if (actualizarTbmInvBod(1))
                                    {
                                        if (objAsiDia.actualizarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                        {
                                            if (isStkBodSuf())
                                            {
                                                recostearItm();
                                                con.commit();
                                                blnRes=true;
                                            }
                                            else
                                            {
                                                mostrarMsgInf(strMsgItmSinStkSuf);
                                                con.rollback();
                                            }
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
                            else
                                con.rollback();
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
    
    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
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
//                if (eliminarDet())
//                {
                    if (eliminarCab())
                    {
                        if (eliminarLogTbrDetMovInv())
                        {
                            if (actualizarTbmInvBod(2))
                            {
                                if (objAsiDia.eliminarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                                {
                                    if (isStkBodSuf())
                                    {
                                        recostearItm();
                                        con.commit();
                                        blnRes=true;
                                    }
                                    else
                                    {
                                        mostrarMsgInf(strMsgItmSinStkSuf);
                                        con.rollback();
                                    }
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
                    else
                        con.rollback();
//                }
//                else
//                    con.rollback();
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
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
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
                if (anularCab())
                {
                    if (anularTbrDetMovInv())
                    {
                        if (actualizarTbmInvBod(3))
                        {
                            if (objAsiDia.anularDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                            {
                                if (isStkBodSuf())
                                {
                                    recostearItm();
                                    con.commit();
                                    blnRes=true;
                                }
                                else
                                {
                                    mostrarMsgInf(strMsgItmSinStkSuf);
                                    con.rollback();
                                }
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

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        int intCodUsr, intUltSecGrp, intUltSecEmp, intUltReg;
        BigDecimal bgdValDoc;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el código para "ne_secGrp".
                strSQL="";
                strSQL+="SELECT a1.ne_secUltDocTbmCabMovInv";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                intUltSecGrp=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltSecGrp==-1)
                    return false;
                intUltSecGrp++;
                //Obtener el código para "ne_secEmp".
                strSQL="";
                strSQL+="SELECT a1.ne_secUltDocTbmCabMovInv";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                intUltSecEmp=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltSecEmp==-1)
                    return false;
                intUltSecEmp++;
                //Obtener el código para "co_doc".
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv (co_emp, co_loc, co_tipDoc, co_doc, ne_secGrp, ne_secEmp, ne_numCot, ne_numDoc, tx_numPed";
                strSQL+=", ne_numGui, co_dia, fe_doc, co_cli, tx_ruc, tx_nomCli, tx_dirCli, tx_telCli, tx_ciuCli, co_com, tx_nomVen, tx_ate, co_forPag";
                strSQL+=", tx_desForPag, nd_sub, nd_porIva, nd_valIva, nd_tot, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, co_cta, co_motDoc, co_mnu, tx_obs1 ,tx_obs2";
                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comMod, fe_con, tx_obs3, co_forRet, tx_vehRet, tx_choRet)";
                strSQL+=" VALUES (";
                strSQL+=intCodEmp;
                strSQL+=", " + intCodLoc;
                strSQL+=", " + txtCodTipDoc.getText();
                strSQL+=", " + intUltReg;
                strSQL+=", " + intUltSecGrp; //ne_secGrp
                strSQL+=", " + intUltSecEmp; //ne_secEmp
                strSQL+=", Null"; //ne_numCot
                strSQL+=", " + objUti.codificar(txtNumDoc1.getText(),3); //ne_numDoc
                strSQL+=", Null"; //tx_numPed
                strSQL+=", Null"; //ne_numGui
                strSQL+=", Null"; //co_dia
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_doc
                strSQL+=", Null"; //co_cli
                strSQL+=", Null"; //tx_ruc
                strSQL+=", Null"; //tx_nomCli
                strSQL+=", Null"; //tx_dirCli
                strSQL+=", Null"; //tx_telCli
                strSQL+=", Null"; //tx_ciuCli
                strSQL+=", Null"; //co_com
                strSQL+=", Null"; //tx_nomVen
                strSQL+=", Null"; //tx_ate
                strSQL+=", Null"; //co_forPag
                strSQL+=", Null"; //tx_desForPag
                bgdValDoc=BigDecimal.valueOf(objUti.parseDouble(txtMonDoc.getText())).multiply(BigDecimal.valueOf(intSig));
                strSQL+=", " + bgdValDoc; //nd_sub
                strSQL+=", 0"; //nd_porIva
                strSQL+=", 0"; //nd_valIva
                strSQL+=", " + bgdValDoc; //nd_tot
                strSQL+=", Null"; //tx_ptoPar
                strSQL+=", Null"; //tx_tra
                strSQL+=", Null"; //co_motTra
                strSQL+=", Null"; //tx_desMotTra
                strSQL+=", Null"; //co_cta
                strSQL+=", Null"; //co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                strSQL+=", 'A'"; //st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+=", " + intCodUsr; //co_usrIng
                strSQL+=", " + intCodUsr; //co_usrMod
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'"; //tx_comIng
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'"; //tx_comMod
                strSQL+=", Null"; //fe_con
                strSQL+=", Null"; //tx_obs3
                strSQL+=", Null"; //co_forRet
                strSQL+=", Null"; //tx_vehRet
                strSQL+=", Null"; //tx_choRet
                strSQL+=")";
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
    
    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet()
    {
        int i, j;
        String strCodTipDoc, strCodDoc, strCodItm, strAuxCodItm, strAuxCodItmAct;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    //Armar la sentencia SQL (Egreso).
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detMovInv (co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_numfil, co_itm, tx_codAlt, tx_codAlt2";
                    strSQL+=", tx_nomItm, tx_uniMed, co_bod, tx_nomBodOrgDes, nd_can, nd_canOrg, nd_cosUni, nd_preUni, nd_porDes, st_ivaCom, nd_tot, nd_cosTot, nd_exi";
                    strSQL+=", nd_valExi, nd_cosPro, nd_cosUniGrp, nd_cosTotGrp, nd_exiGrp, nd_valExiGrp, nd_cosProGrp, st_merIngEgrFisBod, nd_canCon, tx_obs1, co_usrCon, st_regRep, co_itmAct)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + strCodTipDoc;
                    strSQL+=", " + strCodDoc;
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + (i+1); //ne_numfil
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS); //co_itm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT)); //tx_codAlt
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALTDOS)); //tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM)); //tx_nomItm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEC_UNI)); //tx_uniMed
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG); //co_bod
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEL_BOD_DES)); //tx_nomBodOrgDes
                    strSQL+=", -" + objTblMod.getValueAt(i,INT_TBL_DAT_CAN); //tx_can
                    strSQL+=", Null"; //nd_canOrg
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUni
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_preUni
                    strSQL+=", 0"; //nd_porDes
                    strSQL+=", 'N'"; //st_ivaCom
                    strSQL+=", -1*" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_tot
                    strSQL+=", -1*" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTot
                    strSQL+=", Null"; //nd_exi
                    strSQL+=", Null"; //nd_valExi
                    strSQL+=", Null"; //nd_cosPro
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUniGrp
                    strSQL+=", -1*" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTotGrp
                    strSQL+=", Null"; //nd_exiGrp
                    strSQL+=", Null"; //nd_valExiGrp
                    strSQL+=", Null"; //nd_cosProGrp
                    strSQL+=", '" + strMerIngEgrFisBodTipDoc + "'"; //st_merIngEgrFisBod
                    strSQL+=", 0"; //nd_canCon
                    strSQL+=", Null"; //tx_obs1
                    strSQL+=", Null"; //co_usrCon
                    if (strEstRegRep.equals("") || strEstRegRep.equals("I"))
                        strSQL+=", 'I'"; //st_regRep
                    else if (strEstRegRep.equals("P") || strEstRegRep.equals("M"))
                        strSQL+=", 'M'"; //st_regRep
                    strCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                    strAuxCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString());
                    strAuxCodItmAct=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA).toString());
                    //Determinar el código actual del item.
                    if (strAuxCodItmAct.equals(""))
                    {
                        strSQL+=", " + strCodItm; //co_itmAct
                    }
                    else
                    {
                        if (strCodItm.equals(strAuxCodItm))
                            strSQL+=", " + strAuxCodItmAct; //co_itmAct
                        else
                            strSQL+=", " + strCodItm; //co_itmAct
                    }
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                    j++;
                    
                    //Armar la sentencia SQL (Ingreso).
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detMovInv (co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_numfil, co_itm, tx_codAlt, tx_codAlt2";
                    strSQL+=", tx_nomItm, tx_uniMed, co_bod, tx_nomBodOrgDes, nd_can, nd_canOrg, nd_cosUni, nd_preUni, nd_porDes, st_ivaCom, nd_tot, nd_cosTot, nd_exi";
                    strSQL+=", nd_valExi, nd_cosPro, nd_cosUniGrp, nd_cosTotGrp, nd_exiGrp, nd_valExiGrp, nd_cosProGrp, st_merIngEgrFisBod, nd_canCon, tx_obs1, co_usrCon, st_regRep, co_itmAct)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + strCodTipDoc;
                    strSQL+=", " + strCodDoc;
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + (i+1); //ne_numfil
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS); //co_itm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT)); //tx_codAlt
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALTDOS)); //tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM)); //tx_nomItm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEC_UNI)); //tx_uniMed
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES); //co_bod
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEL_BOD_ORG)); //tx_nomBodOrgDes
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN); //tx_can
                    strSQL+=", Null"; //nd_canOrg
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUni
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_preUni
                    strSQL+=", 0"; //nd_porDes
                    strSQL+=", 'N'"; //st_ivaCom
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_tot
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTot
                    strSQL+=", Null"; //nd_exi
                    strSQL+=", Null"; //nd_valExi
                    strSQL+=", Null"; //nd_cosPro
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUniGrp
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTotGrp
                    strSQL+=", Null"; //nd_exiGrp
                    strSQL+=", Null"; //nd_valExiGrp
                    strSQL+=", Null"; //nd_cosProGrp
                    strSQL+=", '" + strMerIngEgrFisBodTipDoc + "'"; //st_merIngEgrFisBod
                    strSQL+=", 0"; //nd_canCon
                    strSQL+=", Null"; //tx_obs1
                    strSQL+=", Null"; //co_usrCon
                    if (strEstRegRep.equals("") || strEstRegRep.equals("I"))
                        strSQL+=", 'I'"; //st_regRep
                    else if (strEstRegRep.equals("P") || strEstRegRep.equals("M"))
                        strSQL+=", 'M'"; //st_regRep
                    strCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                    strAuxCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString());
                    strAuxCodItmAct=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA).toString());
                    //Determinar el código actual del item.
                    if (strAuxCodItmAct.equals(""))
                    {
                        strSQL+=", " + strCodItm; //co_itmAct
                    }
                    else
                    {
                        if (strCodItm.equals(strAuxCodItm))
                            strSQL+=", " + strAuxCodItmAct; //co_itmAct
                        else
                            strSQL+=", " + strCodItm; //co_itmAct
                    }
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                    j++;
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

    /**
     * Esta función permite insertar los datos en la tabla "tbr_detMovInv".
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarTbrDetMovInv()
    {
        int i;
        String strCodTipDoc, strCodDoc;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" INSERT INTO tbr_detMovInv (co_emp, co_loc, co_tipDoc, co_doc, co_reg, st_reg, co_empRel, co_locRel, co_tipDocRel, co_docRel, co_regRel, st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + strCodTipDoc;
                    strSQL+=", " + strCodDoc;
                    strSQL+=", " + ((i*2)+1); //co_reg
                    strSQL+=", 'A'"; //st_reg
                    strSQL+="," + intCodEmp; //co_empRel
                    strSQL+=", " + intCodLoc; //co_locRel
                    strSQL+=", " + strCodTipDoc; //co_tipDocRel
                    strSQL+=", " + strCodDoc; //co_docRel
                    strSQL+=", " + ((i*2)+2); //co_regRel
                    if (strEstRegRep.equals("") || strEstRegRep.equals("I"))
                        strSQL+=", 'I'"; //st_regRep
                    else if (strEstRegRep.equals("P") || strEstRegRep.equals("M"))
                        strSQL+=", 'M'"; //st_regRep
                    strSQL+=")";
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

    /**
     * Esta función permite actualizar la tabla "tbm_invBod".
     * @param intTipOpe El tipo de operación.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operación</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmInvBod(int intTipOpe)
    {
        int i;
        BigDecimal bgdCan1, bgdCan2;
        boolean blnRes=true;
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {
                if (con!=null)
                {
                    stm=con.createStatement();
                    objTblMod.clearManualDataSavedBeforeRemoveRow();
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        bgdCan1=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN)));
                        bgdCan2=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN)));
                        switch (intTipOpe)
                        {
                            case 0: //Insertar
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct-" + bgdCan1;
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    strSQL+=", nd_canEgrBod=nd_canEgrBod+" + bgdCan1;
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + bgdCan1;
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    strSQL+=", nd_canIngBod=nd_canIngBod+" + bgdCan1;
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                break;
                            case 1: //Modificar
                                strAux=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN));
                                if (strAux.equals("M"))
                                {
                                    if (objTblMod.compareStringCells(i, INT_TBL_DAT_COD_SIS, i, INT_TBL_DAT_AUX_COD_SIS) && objTblMod.compareStringCells(i, INT_TBL_DAT_COD_BOD_ORG, i, INT_TBL_DAT_AUX_COD_BOD_ORG) && objTblMod.compareStringCells(i, INT_TBL_DAT_COD_BOD_DES, i, INT_TBL_DAT_AUX_COD_BOD_DES))
                                    {
                                        //Caso 1: El usuario sólo modificó la cantidad.
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct" + ((bgdCan1.compareTo(bgdCan2)>0)?"-" + bgdCan1.subtract(bgdCan2):"+" + bgdCan2.subtract(bgdCan1));
                                        //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                        if (strMerIngEgrFisBodTipDoc.equals("S"))
                                        {
                                            strSQL+=", nd_canEgrBod=nd_canEgrBod" + ((bgdCan1.compareTo(bgdCan2)>0)?"+" + bgdCan1.subtract(bgdCan2):"-" + bgdCan2.subtract(bgdCan1));
                                        }
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct" + ((bgdCan1.compareTo(bgdCan2)>0)?"+" + bgdCan1.subtract(bgdCan2):"-" + bgdCan2.subtract(bgdCan1));
                                        //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                        if (strMerIngEgrFisBodTipDoc.equals("S"))
                                        {
                                            strSQL+=", nd_canIngBod=nd_canIngBod" + ((bgdCan1.compareTo(bgdCan2)>0)?"+" + bgdCan1.subtract(bgdCan2):"-" + bgdCan2.subtract(bgdCan1));
                                        }
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                    }
                                    else
                                    {
                                        //Caso 2 (Paso 1): El usuario cambió algo que debe ser considerado como una eliminación.
                                        java.util.ArrayList arlAux=new java.util.ArrayList();
                                        arlAux.add(INT_CFE_COD_SIS,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS));
                                        arlAux.add(INT_CFE_COD_BOD_ORG,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_BOD_ORG));
                                        arlAux.add(INT_CFE_COD_BOD_DES,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_BOD_DES));
                                        arlAux.add(INT_CFE_CAN,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN));
                                        objTblMod.addRowDataSavedBeforeRemoveRow(arlAux);
                                        //Caso 2 (Paso 2): Considerar la modificación como una inserción.
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct-" + bgdCan1;
                                        //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                        if (strMerIngEgrFisBodTipDoc.equals("S"))
                                        {
                                            strSQL+=", nd_canEgrBod=nd_canEgrBod+" + bgdCan1;
                                        }
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct+" + bgdCan1;
                                        //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                        if (strMerIngEgrFisBodTipDoc.equals("S"))
                                        {
                                            strSQL+=", nd_canIngBod=nd_canIngBod+" + bgdCan1;
                                        }
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                    }
                                }
                                else if (strAux.equals("I"))
                                {
                                    //Caso 3: El usuario insertó una fila.
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_invBod";
                                    strSQL+=" SET nd_stkAct=nd_stkAct-" + bgdCan1;
                                    //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                    if (strMerIngEgrFisBodTipDoc.equals("S"))
                                    {
                                        strSQL+=", nd_canEgrBod=nd_canEgrBod+" + bgdCan1;
                                    }
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                    strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                    stm.executeUpdate(strSQL);
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_invBod";
                                    strSQL+=" SET nd_stkAct=nd_stkAct+" + bgdCan1;
                                    //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                    if (strMerIngEgrFisBodTipDoc.equals("S"))
                                    {
                                        strSQL+=", nd_canIngBod=nd_canIngBod+" + bgdCan1;
                                    }
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                    strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                    stm.executeUpdate(strSQL);
                                }
                                break;
                            case 2: //Eliminar
                            case 3: //Anular
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + bgdCan1;
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    strSQL+=", nd_canEgrBod=nd_canEgrBod-" + bgdCan1;
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct-" + bgdCan1;
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    strSQL+=", nd_canIngBod=nd_canIngBod-" + bgdCan1;
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                break;
                        }
                    }
                    switch (intTipOpe)
                    {
                        case 1: //Modificar
                            //Caso 4: El usuario eliminó una o más filas.
                            java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                            for (i=0;i<arlAux.size();i++)
                            {
                                bgdCan1=objUti.getBigDecimalValueAt(arlAux,i,INT_CFE_CAN);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + bgdCan1;
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    strSQL+=", nd_canEgrBod=nd_canEgrBod-" + bgdCan1;
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_BOD_ORG);
                                strSQL+=" AND co_itm=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_SIS);
                                stm.executeUpdate(strSQL);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct-" + bgdCan1;
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    strSQL+=", nd_canIngBod=nd_canIngBod-" + bgdCan1;
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_BOD_DES);
                                strSQL+=" AND co_itm=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_SIS);
                                stm.executeUpdate(strSQL);
                            }
                            arlAux=null;
                            break;
                    }
                    stm.close();
                    stm=null;
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
     * Esta función permite cargar el registro seleccionado.
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
                    objAsiDia.consultarDiario(intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
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
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a2.st_merIngEgrFisBod, a2.ne_numLin, a1.co_doc, a1.fe_doc";
                strSQL+="      , a1.ne_numDoc, a1.nd_tot, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_regRep";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
                    objTblMod.setMaxRowsAllowed(Integer.valueOf(rst.getInt("ne_numLin")), false);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    datFecDoc=rst.getDate("fe_doc");
                    dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    txtMonDoc.setText("" + rst.getBigDecimal("nd_tot").abs());
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    strAux=rst.getString("st_regRep");
                    strEstRegRep=(strAux==null)?"":strAux;
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
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceTrs+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConTrs.size()) );
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumFil, intCodItm, intCodBod, i;
        BigDecimal bgdCan, bgdCosUni, bgdPesUni;
        boolean blnRes=true;
        try
        {
//            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.ne_numFil, a1.co_itm, a1.tx_codAlt, a3.tx_codAlt2, a1.tx_nomItm, a1.tx_uniMed, a1.co_bod, a2.tx_nom, a1.nd_can, a1.nd_cosUni, a1.co_itmAct, a3.nd_pesItmKgr";
                strSQL+=" FROM tbm_detMovInv AS a1";
                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
                strSQL+=" LEFT OUTER JOIN tbm_inv AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_itmAct=a3.co_itm)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                //objTooBar.setMenSis("Cargando datos...");
                i=1;
                while (rst.next())
                {
                    intNumFil=rst.getInt("ne_numFil");
                    if (vecDat.size()==i && intNumFil==i)
                    {
                        //Ingresos de inventario.
                        vecAux=(Vector)vecDat.get(i-1);
                        intCodBod=rst.getInt("co_bod");
                        vecAux.setElementAt("" + intCodBod,INT_TBL_DAT_COD_BOD_DES);
                        vecAux.setElementAt(rst.getString("tx_nom"),INT_TBL_DAT_DEL_BOD_DES);
                        bgdCan=rst.getBigDecimal("nd_can");
                        bgdCosUni=rst.getBigDecimal("nd_cosUni");
                        bgdPesUni=rst.getBigDecimal("nd_pesItmKgr");
                        vecAux.setElementAt("" + bgdCan,INT_TBL_DAT_CAN);
                        vecAux.setElementAt("" + bgdCosUni,INT_TBL_DAT_COS_UNI);
                        vecAux.setElementAt("" + bgdCan.multiply(bgdCosUni),INT_TBL_DAT_COS_TOT);
                        vecAux.setElementAt(bgdPesUni,INT_TBL_DAT_PES_UNI);
                        vecAux.setElementAt((bgdPesUni==null?null:bgdCan.multiply(bgdPesUni)),INT_TBL_DAT_PES_TOT);
                        vecAux.setElementAt("" + intCodBod,INT_TBL_DAT_AUX_COD_BOD_DES);
                        vecAux.setElementAt("" + bgdCan,INT_TBL_DAT_AUX_CAN);
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
                        vecReg.add(INT_TBL_DAT_COD_ALTDOS,rst.getString("tx_codAlt2"));
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
                        vecReg.add(INT_TBL_DAT_PES_UNI,null);
                        vecReg.add(INT_TBL_DAT_PES_TOT,null);
                        vecReg.add(INT_TBL_DAT_AUX_COD_SIS,"" + intCodItm);
                        vecReg.add(INT_TBL_DAT_AUX_COD_BOD_ORG,"" + intCodBod);
                        vecReg.add(INT_TBL_DAT_AUX_COD_BOD_DES,null);
                        vecReg.add(INT_TBL_DAT_AUX_CAN,null);
                        vecReg.add(INT_TBL_DAT_AUX_COD_SIA,rst.getString("co_itmAct"));
                        vecDat.add(vecReg);
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
                calcularTotDoc();
                //objTooBar.setMenSis("Listo");
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab()
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
                strSQL+=" UPDATE tbm_cabMovInv";
                strSQL+=" SET ne_numDoc=" + objUti.codificar(txtNumDoc1.getText(),2);
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_doc='" + strAux + "'";
                strSQL+=", nd_sub=" + objUti.codificar(txtMonDoc.getText(),3);
                strSQL+=", nd_tot=" + objUti.codificar(txtMonDoc.getText(),3);
                strSQL+=", co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod='" + objParSis.getNombreComputadoraConDirIP() + "'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
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
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarCab()
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
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod='" + objParSis.getNombreComputadoraConDirIP() + "'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
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
     * Esta función permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_detMovInv";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
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

    /**
     * Esta función permite eliminar los datos en la tabla "tbr_detMovInv".
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarTbrDetMovInv()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbr_detMovInv";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
                strSQL+=" AND co_empRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_locRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDocRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_docRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
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

    /**
     * Esta función permite eliminar lógicamente los datos en la tabla "tbr_detMovInv".
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarLogTbrDetMovInv()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbr_detMovInv";
                strSQL+=" SET st_reg='E'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
                strSQL+=" AND co_empRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_locRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDocRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_docRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
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

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab()
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
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod='" + objParSis.getNombreComputadoraConDirIP() + "'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
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
     * Esta función permite anular los datos en la tabla "tbr_detMovInv".
     * @return true: Si se pudo anular el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularTbrDetMovInv()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbr_detMovInv";
                strSQL+=" SET st_reg='I'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
                strSQL+=" AND co_empRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP);
                strSQL+=" AND co_locRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC);
                strSQL+=" AND co_tipDocRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC);
                strSQL+=" AND co_docRel=" + objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC);
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

    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc1.setText("");
            objTblMod.removeAllRows();
            txtPesDoc.setText("");
            txtMonDoc.setText("");
            objAsiDia.inicializar();
            txaObs1.setText("");
            txaObs2.setText("");
            strEstRegRep="";
            strMerIngEgrFisBodTipDoc="";
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_merIngEgrFisBod");
            arlCam.add("a1.ne_numLin");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Est.Mer.Ing.Egr.Fis.Bod.");
            arlAli.add("Núm.Lín.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("60");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=5;
            intColOcu[1]=6;
            intColOcu[2]=7;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a2.co_itmMae");
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a3.tx_desCor");
            arlCam.add("a1.nd_stkAct");
            arlCam.add("a1.nd_cosUni");
            arlCam.add("a1.nd_pesItmKgr");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            arlAli.add("Stock");
            arlAli.add("Costo");
            arlAli.add("Peso(Kg)");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("294");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni, a1.nd_pesItmKgr";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
            strSQL+=" AND a1.st_ser='N'";
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=1;//co_itmMae
            intColOcu[1]=2;//co_itm
            intColOcu[2]=7;//nd_stkAct
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.CENTER);  //tx_desCor
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);//nd_stkAct
            vcoItm.setConfiguracionColumna(8, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);//nd_cosUni
            vcoItm.setConfiguracionColumna(9, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);//nd_pesItmKgr
            vcoItm.setCampoBusqueda(2);//tx_codAlt
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
     * mostrar las "Bodegas".
     */
    private boolean configurarVenConBod()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            arlCam.add("a2.nd_stkAct");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Stock");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("434");
            arlAncCol.add("60");
            //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct";
                strSQL+=" FROM tbm_bod AS a1";
                strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_bod";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct";
                strSQL+=" FROM tbm_bod AS a1";
                strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
                strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                strSQL+=" WHERE a3.co_emp=" + intCodEmp;
                strSQL+=" AND a3.co_loc=" + intCodLoc;
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_bod";
            }
            vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoBod.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
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
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod, a1.ne_numLin";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc1.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
                    objTblMod.setMaxRowsAllowed(Integer.valueOf(rst.getInt("ne_numLin")));
                }
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                            {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado()=='n')
                                {
                                    strAux=vcoTipDoc.getValueAt(4);
                                    txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                                }
                                intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                                strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();
                            }
                            else
                            {
                                txtDesCorTipDoc.setText(strDesCorTipDoc);
                            }
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            if (objTblMod.setMaxRowsAllowed(Integer.valueOf(vcoTipDoc.getValueAt(7))))
                            {
                                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                                if (objTooBar.getEstado()=='n')
                                {
                                    strAux=vcoTipDoc.getValueAt(4);
                                    txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                                }
                                intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                                strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();
                            }
                            else
                            {
                                txtDesLarTipDoc.setText(strDesLarTipDoc);
                            }
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
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

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc1.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        if (blnHayCam || objTblMod.isDataModelChanged() || objAsiDia.isDiarioModificado())
        {
            strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux, true))
            {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado())
                    {
                        case 'n': //Insertar
                            blnRes=objTooBar.beforeInsertar();
                            if (blnRes)
                                blnRes=objTooBar.insertar();
                            break;
                        case 'm': //Modificar
                            blnRes=objTooBar.beforeModificar();
                            if (blnRes)
                                blnRes=objTooBar.modificar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    if (objTooBar.getBotonClick()==MiToolBar.INT_BUT_CON || objTooBar.getBotonClick()==MiToolBar.INT_BUT_ELI || objTooBar.getBotonClick()==MiToolBar.INT_BUT_ANU || objTooBar.getBotonClick()==MiToolBar.INT_BUT_IMP || objTooBar.getBotonClick()==MiToolBar.INT_BUT_VIS)
                    {
                        blnRes=cargarReg();
                    }
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
                    break;
            }
        }
        return blnRes;
    }
    
    /**
     * Esta función calcula el total de la fila.
     */
    private void calcularToFil()
    {
        int intFilSel;
        BigDecimal bgdCan, bgdAux;
        intFilSel=tblDat.getSelectedRow();
        bgdCan=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN)));
        bgdAux=bgdCan.multiply(BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI))));
        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdAux, objParSis.getDecimalesBaseDatos()), intFilSel, INT_TBL_DAT_COS_TOT);
        bgdAux=bgdCan.multiply(BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_PES_UNI))));
        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdAux, objParSis.getDecimalesBaseDatos()), intFilSel, INT_TBL_DAT_PES_TOT);
    }
    
    /**
     * Esta función calcula el total del documento.
     */
    private void calcularTotDoc()
    {
        int intNumTotFil, i;
        BigDecimal bgdCosTot, bgdPesTot;
        try
        {
            bgdCosTot=BigDecimal.ZERO;
            bgdPesTot=BigDecimal.ZERO;
            intNumTotFil=objTblMod.getRowCount();
            for (i=0; i<intNumTotFil; i++)
            {
                bgdCosTot=bgdCosTot.add(BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT))));
                bgdPesTot=bgdPesTot.add(BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT))));
            }
            txtMonDoc.setText("" + objUti.redondearBigDecimal(bgdCosTot, objParSis.getDecimalesBaseDatos()));
            txtPesDoc.setText("" + objUti.redondearBigDecimal(bgdPesTot, objParSis.getDecimalesMostrar()));
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
        }
    }

    /**
     * Esta función se utiliza para regenerar el asiento de diario. 
     * La idea principal de ésta función es regenerar el asiento de diario. Pero,
     * puede darse el caso en el que el asiento de diario fue modificado manualmente
     * por el usuario. En  cuyo caso, primero se pregunta si desea regenerar el asiento
     * de diario, no regenerarlo o cancelar.
     * @return true: Si se decidió regenerar/no regenerar el asiento de diario.
     * <BR>false: Si se canceló la regeneración del asiento de diario.
     * Nota.- Como se puede apreciar la función retorna "false" sólo cuando se dió
     * click en el botón "Cancelar".
     */
    private boolean regenerarDiario()
    {
        boolean blnRes=true;
//        if (objAsiDia.getGeneracionDiario()==2)
//        {
//            strAux="¿Desea regenerar el asiento de diario?\n";
//            strAux+="El asiento de diario ha sido modificado manualmente.";
//            strAux+="\nSi desea que el sistema regenere el asiento de diario de click en SI.";
//            strAux+="\nSi desea grabar el asiento de diario tal como está de click en NO.";
//            strAux+="\nSi desea cancelar ésta operación de click en CANCELAR.";
//            switch (mostrarMsgCon(strAux, true))
//            {
//                case 0: //YES_OPTION
//                    objAsiDia.setGeneracionDiario((byte)0);
//                    objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
//                    break;
//                case 1: //NO_OPTION
//                    break;
//                case 2: //CANCEL_OPTION
//                    blnRes=false;
//            }
//        }
//        else
//        {
//            objAsiDia.setGeneracionDiario((byte)0);
//            objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
//        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar los valores de las columnas auxiliares.
     * Se utilizan columnas auxiliares para almacenar el valor que tenía la celda
     * antes de ser modificada.
     * @return true: Si se pudo actualizar los valores de las columnas auxiliares.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarValColAux()
    {
        int i;
        String strCodItm, strAuxCodItm, strAuxCodItmAct;
        boolean blnRes=true;
        try
        {
            for (i=0;i<objTblMod.getRowCountTrue();i++)
            {
                strCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                strAuxCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString());
                strAuxCodItmAct=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA).toString());
                objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS),i,INT_TBL_DAT_AUX_COD_SIS);
                objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG),i,INT_TBL_DAT_AUX_COD_BOD_ORG);
                objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES),i,INT_TBL_DAT_AUX_COD_BOD_DES);
                objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_CAN),i,INT_TBL_DAT_AUX_CAN);
                if (strAuxCodItmAct.equals(""))
                {
                    objTblMod.setValueAt(strCodItm,i,INT_TBL_DAT_AUX_COD_SIA);
                }
                else
                {
                    if (strCodItm.equals(strAuxCodItm))
                        objTblMod.setValueAt(strAuxCodItmAct,i,INT_TBL_DAT_AUX_COD_SIA);
                    else
                        objTblMod.setValueAt(strCodItm,i,INT_TBL_DAT_AUX_COD_SIA);
                }
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
     * Esta función valida que exista el stock suficiente en la bodega para hacer la transferencia.
     * @return true: Si existe el stock suficiente.
     * <BR>false: En el caso contrario.
     */
    private boolean isStkBodSuf()
    {
        int i, intNumTotFil;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                //Obtener el listado de items que se van a validar.
                intNumTotFil=objTblMod.getRowCountTrue();
                strAux="";
                switch (objTooBar.getEstado())
                {
                    case 'n':
                        for (i=0; i<intNumTotFil; i++)
                        {
                            strAux+=(i==0?"":" OR") + " (a2.co_bod=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_ORG) + " AND a2.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS) + ")";
                        }
                        break;
                    case 'm':
                        //Items que actualmente aparecen en la tabla.
                        for (i=0; i<intNumTotFil; i++)
                        {
                            if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("I"))
                            {
                                strAux+=(strAux.equals("")?"":" OR") + " (a2.co_bod=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_ORG) + " AND a2.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS) + ")";
                            }
                            else if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("M"))
                            {
                                //Validar nuevo item en la "Bodega origen".
                                strAux+=(strAux.equals("")?"":" OR") + " (a2.co_bod=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_ORG) + " AND a2.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS) + ")";
                                //Validar item anterior en la "Bodega destino".
                                strAux+=(strAux.equals("")?"":" OR") + " (a2.co_bod=" + objTblMod.getValueAt(i, INT_TBL_DAT_AUX_COD_BOD_DES) + " AND a2.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_AUX_COD_SIS) + ")";
                            }
                        }
                        //Items que estaban en la tabla pero fueron eliminados.
                        java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                        for (i=0; i<arlAux.size(); i++)
                        {
                            strAux+=(strAux.equals("")?"":" OR") + " (a2.co_bod=" + objUti.getIntValueAt(arlAux, i, INT_CFE_COD_BOD_DES) + " AND a2.co_itm=" + objUti.getIntValueAt(arlAux, i, INT_CFE_COD_SIS) + ")";
                        }
                        break;
                    case 'e':
                    case 'a':
                        for (i=0; i<intNumTotFil; i++)
                        {
                            strAux+=(i==0?"":" OR") + " (a2.co_bod=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD_DES) + " AND a2.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS) + ")";
                        }
                        break;
                }
                //Obtener el listado de items que quedaron con stock negativo luego de guardar.
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.tx_codAlt, a2.nd_stkAct";
                strSQL+=" FROM tbm_inv AS a1";
                strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" WHERE a2.co_emp=" + intCodEmp;
                if (!strAux.equals(""))
                    strSQL+=" AND (" + strAux + ")";
                strSQL+=" AND a2.nd_stkAct<0";
                rst=stm.executeQuery(strSQL);
                objTblMod.clearRowHeaderRaise();
                strMsgItmSinStkSuf="<HTML>No se puede guardar los cambios porque generaría los siguientes negativos:";
                while (rst.next())
                {
                    for (i=0; i<intNumTotFil; i++)
                    {
                        if (rst.getString("tx_codAlt").equals(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT).toString()))
                        {
                            objTblMod.addElementAtRowHeaderRaise(i, "Negativo que se generaría: " + rst.getBigDecimal("nd_stkAct"));
                            break;
                        }
                    }
                    strMsgItmSinStkSuf+="<BR><FONT COLOR=\"blue\">" + rst.getString("tx_codAlt") + ": </FONT><FONT COLOR=\"red\">" + rst.getBigDecimal("nd_stkAct") + "</FONT>";
                }
                strMsgItmSinStkSuf+="<BR>Disminuya las cantidades a transferir y vuelva a intentarlo.</HTML>";
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                tblDat.repaint();
                if (objTblMod.getSizeRowHeaderRaise()>0)
                {
                    blnRes=false;
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
     * Esta función permite recostear los itmes de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean recostearItm()
    {
        int i;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                switch (objTooBar.getEstado())
                {
                    case 'n':
                        objUti.costearDocumento(this, objParSis, con, intCodEmp, objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        break;
                    case 'm':
                        //Validar la fecha  del documento sólo si se encuentra en el modo "Modificar".
                        //Validar si se utiliza la fecha que tenia el documento inicialmente o la que tiene actualmente.
                        if (datFecDoc.compareTo(objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy"))>0)
                        {
                            for (i=0;i<objTblMod.getRowCountTrue();i++)
                            {
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, intCodEmp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            }
                        }
                        else
                        {
                            for (i=0;i<objTblMod.getRowCountTrue();i++)
                            {
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, intCodEmp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                            }
                        }
                        break;
                    default:
                        for (i=0;i<objTblMod.getRowCountTrue();i++)
                        {
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con, intCodEmp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                        }
                        break;
                }
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
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes();
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
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar=new java.util.HashMap();
//                                mapPar.put("SUBREPORT_DIR", strRutRpt);
//                                mapPar.put("co_emp", Short.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP)));
//                                mapPar.put("co_loc", Short.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC)));
//                                mapPar.put("co_tipDoc", Short.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC)));
//                                mapPar.put("co_doc", Integer.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC)));
//                                mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);
                                //TEMPORAL HASTA QUE SE MEJORE EL REPORTE EXISTENTE.
                                mapPar.put("coEmp", Integer.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_EMP)));
                                mapPar.put("coLoc", Integer.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_LOC)));
                                mapPar.put("coTipDoc", Integer.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_TIPDOC)));
                                mapPar.put("coDoc", Integer.valueOf(objUti.getIntValueAt(arlDatConTrs, intIndiceTrs, INT_ARL_CON_TRS_COD_DOC)));
                                String strCodemp="10";
                                if(txtCodTipDoc.getText().trim().equals("66")) strCodemp="2";
                                if(txtCodTipDoc.getText().trim().equals("58")) strCodemp="4";
                                mapPar.put("coEmpTra", Integer.valueOf(strCodemp));
                                //FIN TEMPORAL.
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
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar el campo "ne_secUltDocTbmCabMovInv" en la tabla "tbm_emp".
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarSecUltDocTbmCabMovInv()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_emp";
                strSQL+=" SET ne_secUltDocTbmCabMovInv=ne_secUltDocTbmCabMovInv+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" OR co_emp=" + intCodEmp;
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

    /**
     * Esta función determina si se deben generar compras y ventas automáticas luego del grabar la transferencia.
     * @return true: Indica que si se deben generar compras y ventas automáticas.
     * <BR>false: En el caso contrario.
     */
    private boolean generarComVenAut()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.st_traInvGenComVenAutEmpRel";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    if (rst.getString("st_traInvGenComVenAutEmpRel").equals("S"))
                        blnRes=true;
                }
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
                case INT_TBL_DAT_COD_ALTDOS:
                    strMsg="Código alterno 2 del item";
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
                case INT_TBL_DAT_PES_UNI:
                    strMsg="Peso unitario(Kg)";
                    break;
                case INT_TBL_DAT_PES_TOT:
                    strMsg="Peso total(Kg)";
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