/*
 * ZafCom34.java
 * " Conteos de Inventario"
 * Este programa solo funciona por GRUPO LSC.
 * 
 */
package Compras.ZafCom34;
import Librerias.ZafGetDat.ZafDatItm;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom34 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN=0;                                 //Línea
    private static final int INT_TBL_DAT_COD_EMP=1;                             //Código de empresa.
    private static final int INT_TBL_DAT_COD_LOC=2;                             //Código del local.
    private static final int INT_TBL_DAT_COD_TIP_DOC=3;                         //Código del tipo de documento.
    private static final int INT_TBL_DAT_DCO_TIP_DOC=4;                         //Descripción corta del tipo de documento.
    private static final int INT_TBL_DAT_DLA_TIP_DOC=5;                         //Descripción larga del tipo de documento.
    private static final int INT_TBL_DAT_COD_DOC=6;                             //Código del documento.
    private static final int INT_TBL_DAT_NUM_DOC=7;                             //Número del documento.
    private static final int INT_TBL_DAT_FEC_DOC=8;                             //Fecha del documento.
    private static final int INT_TBL_DAT_COD_ITM_MAE=9;                         //Código del item maestro.
    private static final int INT_TBL_DAT_COD_ITM=10;                            //Código del item de la empresa. Como este programa solo funciona por GRUPO LSC, es el código del item por grupo.
    private static final int INT_TBL_DAT_COD_ALT=11;                            //Código alterno del item.
    private static final int INT_TBL_DAT_COD_LET=12;                            //Código de tres letras.
    private static final int INT_TBL_DAT_NOM_ITM=13;                            //Nombre del item.
    private static final int INT_TBL_DAT_UNI_MED=14;                            //Unidad de medida.
    private static final int INT_TBL_DAT_UBI_ITM=15;                            //Ubicación del item.
    private static final int INT_TBL_DAT_CHK_SEL_EST=16;                        //Item seleccionado
    private static final int INT_TBL_DAT_CAN_BUE_EST_ING_USR=17;                //Cantidad ingresada en buen estado ingresada por el usuario
    private static final int INT_TBL_DAT_CAN_MAL_EST_ING_USR=18;                //Cantidad ingresada en mal estado(dañada) ingresada por el usuario
    private static final int INT_TBL_DAT_CHK_CON_REA=19;                        //Estado de conteo del item.
    private static final int INT_TBL_DAT_EST_ING_IMP=20;                        //Estado del ingreso por importación.
    private static final int INT_TBL_DAT_COD_REG=21;                            //Código del registro
    
    //Otras Constantes
    private int INT_COD_BOD_INM_GRP=15;
    private static final int INT_COD_MNU_CON_CONORD=1954;                       //Código de Menú: Conteo (Con Orden).
    private static final int INT_COD_MNU_CON_SINORD=3177;                       //Código de Menú: Conteo (Sin Orden).
    private static final int INT_COD_MNU_CON_INGIMP=3181;                       //Código de Menú: Conteo (Importaciones).
    
    //Contendedor: Items ZafStkInv MovimientoStock
    private static final int INT_ARL_STK_INV_COD_ITM_GRP = 0;
    private static final int INT_ARL_STK_INV_COD_ITM_EMP = 1;
    private static final int INT_ARL_STK_INV_COD_ITM_MAE = 2;
    private static final int INT_ARL_STK_INV_COD_LET_ITM = 3;
    private static final int INT_ARL_STK_INV_CAN_ITM = 4;
    private static final int INT_ARL_STK_INV_COD_BOD_EMP = 5;
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    
    //<editor-fold defaultstate="collapsed" desc="//Indice que permite obtener el nombre del campo que se desea actualizar">
    /*
     * @param indiceNombreCampo 
     *          <HTML>
     *              <BR>  0: Actualiza en campo "nd_stkAct"
     *              <BR>  1: Actualiza en campo "nd_canPerIng"
     *              <BR>  2: Actualiza en campo "nd_canPerEgr"
     *              <BR>  3: Actualiza en campo "nd_canBodIng"
     *              <BR>  4: Actualiza en campo "nd_canBodEgr"
     *              <BR>  5: Actualiza en campo "nd_canDesIng"
     *              <BR>  6: Actualiza en campo "nd_canDesEgr"
     *              <BR>  7: Actualiza en campo "nd_canTra"
     *              <BR>  8: Actualiza en campo "nd_canRev"
     *              <BR>  9: Actualiza en campo "nd_canRes"
     *              <BR> 10: Actualiza en campo "nd_canDis"
     *          </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    //</editor-fold>
    final int INT_ARL_STK_INV_STK_ACT=0;      // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
    final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng --> transferencia afectar ingreso 
    final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr --> transferencia afectar egreso
    final int INT_ARL_STK_INV_CAN_DES_ENT_BOD=5;
    final int INT_ARL_STK_INV_CAN_DES_ENT_CLI=6;
    final int INT_ARL_STK_INV_CAN_TRA=7;
    final int INT_ARL_STK_INV_CAN_REV=8;
    final int INT_ARL_STK_INV_CAN_RES=9;
    final int INT_ARL_STK_INV_CAN_DIS=10;     // nd_canDis
    final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCan;                //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafTblCelRenChk objTblCelRenChkCon, objTblCelRenChkSel;
    private ZafTblCelEdiChk objTblCelEdiChkCon, objTblCelEdiChkSel;
    private ZafTblCelEdiTxt objTblCelEdiTxtCanBueEst, objTblCelEdiTxtCanMalEst, objTblCelEdiTxtItmLet;
    private ZafPerUsr objPerUsr;                                                //Objeto que almacena el perfil del usuario.
    private ZafStkInv objStkInv;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafVenCon vcoBod, vcoItm, vcoPed;
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private ZafImp objImp;
    private Vector vecDat, vecCab, vecReg, vecAux;  
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    
    static final int intNumPerCon=1;
    private int intNumConIng;
    
    private String strSQL, strAux;
    private String strCodAlt, strNomItm, strCodLet, strUbiItm;                  //Contenido del campo al obtener el foco.
    private String strCodBod, strNomBod;
    private String strCodPed, strPedImp;                                        //Contenido del campo al obtener el foco.
    
    private String strCodEmpIngImp, strCodLocIngImp, strCodTipDocIngImp, strCodDocIngImp, strEstIngImp;
    private String strCodEmpOrdCon, strCodLocOrdCon, strCodTipDocOrdCon, strCodDocOrdCon, strCodBodOrdCon;
    private String strFecEsqAnt="2017-09-13";  //Valida Fecha para no incrementar disponible. REAL
    
    private String strVersion=" v0.9.12";

    /** Crea una nueva instancia de la clase ZafCom34. */
    public ZafCom34(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
            
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

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        panBusBodIngImp = new javax.swing.JPanel();
        panBod = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        panIngImp = new javax.swing.JPanel();
        lblNumPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        panItm = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodItmLet = new javax.swing.JTextField();
        txtUbiItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        txtCodAltItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butLim = new javax.swing.JButton();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panFil.setPreferredSize(new java.awt.Dimension(0, 65));
        panFil.setLayout(null);

        panBusBodIngImp.setPreferredSize(new java.awt.Dimension(300, 40));
        panBusBodIngImp.setLayout(new java.awt.BorderLayout());

        panBod.setLayout(null);

        lblBod.setText("Bodega:");
        lblBod.setToolTipText("Bodega en la que se debe hacer el conteo");
        panBod.add(lblBod);
        lblBod.setBounds(10, 8, 50, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        panBod.add(txtCodBod);
        txtCodBod.setBounds(60, 8, 40, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        panBod.add(txtNomBod);
        txtNomBod.setBounds(100, 8, 246, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panBod.add(butBod);
        butBod.setBounds(346, 8, 20, 20);

        panBusBodIngImp.add(panBod, java.awt.BorderLayout.CENTER);

        panIngImp.setPreferredSize(new java.awt.Dimension(250, 60));
        panIngImp.setLayout(null);

        lblNumPed.setText("Pedido:");
        panIngImp.add(lblNumPed);
        lblNumPed.setBounds(10, 8, 70, 20);

        txtCodPed.setBackground(new java.awt.Color(240, 240, 240));
        txtCodPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPedActionPerformed(evt);
            }
        });
        txtCodPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPedFocusLost(evt);
            }
        });
        panIngImp.add(txtCodPed);
        txtCodPed.setBounds(50, 8, 10, 20);

        txtPedIngImp.setBackground(objParSis.getColorCamposObligatorios());
        txtPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPedIngImpActionPerformed(evt);
            }
        });
        txtPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusLost(evt);
            }
        });
        panIngImp.add(txtPedIngImp);
        txtPedIngImp.setBounds(60, 8, 170, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panIngImp.add(butPedImp);
        butPedImp.setBounds(230, 8, 20, 20);

        panBusBodIngImp.add(panIngImp, java.awt.BorderLayout.EAST);

        panFil.add(panBusBodIngImp);
        panBusBodIngImp.setBounds(0, 2, 670, 30);

        panItm.setPreferredSize(new java.awt.Dimension(250, 65));
        panItm.setLayout(null);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panItm.add(lblItm);
        lblItm.setBounds(10, 0, 50, 20);

        txtCodItm.setEditable(false);
        txtCodItm.setEnabled(false);
        panItm.add(txtCodItm);
        txtCodItm.setBounds(40, 2, 20, 20);

        txtCodItmLet.setToolTipText("Código del item en letras");
        txtCodItmLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmLetActionPerformed(evt);
            }
        });
        txtCodItmLet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodItmLetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodItmLetFocusLost(evt);
            }
        });
        panItm.add(txtCodItmLet);
        txtCodItmLet.setBounds(60, 2, 56, 20);

        txtUbiItm.setToolTipText("Ubicación del item");
        txtUbiItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUbiItmActionPerformed(evt);
            }
        });
        txtUbiItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUbiItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUbiItmFocusLost(evt);
            }
        });
        panItm.add(txtUbiItm);
        txtUbiItm.setBounds(116, 2, 230, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panItm.add(butItm);
        butItm.setBounds(346, 2, 20, 20);

        txtCodAltItm.setToolTipText("Código alterno del item");
        txtCodAltItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmActionPerformed(evt);
            }
        });
        txtCodAltItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusLost(evt);
            }
        });
        panItm.add(txtCodAltItm);
        txtCodAltItm.setBounds(240, 7, 30, 20);

        txtNomItm.setToolTipText("Nombre del item");
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        panItm.add(txtNomItm);
        txtNomItm.setBounds(270, 7, 50, 20);

        panFil.add(panItm);
        panItm.setBounds(0, 35, 420, 30);

        panGen.add(panFil, java.awt.BorderLayout.NORTH);

        panRpt.setLayout(new java.awt.BorderLayout());

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panRpt, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butLim.setText("Limpiar");
        butLim.setToolTipText("Cierra la ventana.");
        butLim.setPreferredSize(new java.awt.Dimension(92, 25));
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panBot.add(butLim);

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
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

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

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if(isCamValCon())
        {
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
   
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod = txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) 
        {
            if (txtCodBod.getText().equals("")) 
            {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } 
            else 
            {
                mostrarVenConBod(1);
            }
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtCodItmLet.setText("");
            txtNomItm.setText("");
            txtUbiItm.setText("");
            objTblMod.removeAllRows();
        } 
        else
            txtCodBod.setText(strCodBod);
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod = txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) 
        {
            if (txtNomBod.getText().equals("")) 
            {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } 
            else 
            {
                mostrarVenConBod(2);
            }
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtCodItmLet.setText("");
            txtNomItm.setText("");
            txtUbiItm.setText("");
            objTblMod.removeAllRows();
        } 
        else
            txtNomBod.setText(strNomBod);
    }//GEN-LAST:event_txtNomBodFocusLost

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        mostrarVenConBod(0);
        objTblMod.removeAllRows();
    }//GEN-LAST:event_butBodActionPerformed

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        txtCodAltItm.transferFocus();
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        strCodAlt=txtCodAltItm.getText();
        txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) )
        {
            if(txtCodBod.getText().length()<=0)
            {
                txtCodAltItm.setText("");
                mostrarMsgInf("<HTML>Debe ingresar un dato en el campo Bodega para poder realizar la consulta.<BR>Verifique el valor de la Bodega y vuelva a intentarlo.</HTML>");
                txtNomBod.requestFocus();
            }
            else
            {
                //Validar el contenido de la celda sólo si ha cambiado.
                if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
                {
                    if (txtCodAltItm.getText().equals(""))
                    {
                        txtCodItm.setText("");
                        txtCodAltItm.setText("");
                        txtNomItm.setText("");
                    }
                    else
                    {
                        mostrarVenConItm(1);
                    }
                }
                else
                    txtCodAltItm.setText(strCodAlt);

                if(txtCodAltItm.getText().length()>0)
                {
                }
            }
        }

    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) )
        {
            if(txtCodBod.getText().length()<=0)
            {
                txtNomItm.setText("");
                mostrarMsgInf("<HTML>Debe ingresar un dato en el campo Bodega para poder realizar la consulta.<BR>Verifique el valor de la Bodega y vuelva a intentarlo.</HTML>");
                txtNomBod.requestFocus();
            }
            else
            {
                //Validar el contenido de la celda sólo si ha cambiado.
                if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
                {
                    if (txtNomItm.getText().equals(""))
                    {
                        txtCodItm.setText("");
                        txtCodAltItm.setText("");
                        txtNomItm.setText("");
                    }
                    else
                    {
                        mostrarVenConItm(2);
                    }
                }
                else
                    txtNomItm.setText(strNomItm);

                if(txtNomItm.getText().length()>0)
                {
                }
            }
        }


    }//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        if(txtNomItm.getText().length()>0){
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodItmLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmLetActionPerformed
        txtCodItmLet.transferFocus();
    }//GEN-LAST:event_txtCodItmLetActionPerformed

    private void txtUbiItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUbiItmActionPerformed
        txtUbiItm.transferFocus();
    }//GEN-LAST:event_txtUbiItmActionPerformed

    private void txtCodItmLetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmLetFocusGained
        strCodLet=txtCodItmLet.getText();
        txtCodItmLet.selectAll();
    }//GEN-LAST:event_txtCodItmLetFocusGained

    private void txtUbiItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUbiItmFocusGained
        strUbiItm=txtUbiItm.getText();
        txtUbiItm.selectAll();
    }//GEN-LAST:event_txtUbiItmFocusGained

    private void txtCodItmLetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmLetFocusLost
        if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) )
        {
            if(txtCodBod.getText().length()<=0)
            {
                mostrarMsgInf("<HTML>Debe ingresar un dato en el campo Bodega para poder realizar la consulta.<BR>Verifique el valor de la Bodega y vuelva a intentarlo.</HTML>");
                txtNomBod.requestFocus();
                txtCodItmLet.setText("");
            }
            else
            {
                //Validar el contenido de la celda sólo si ha cambiado.
                if (!txtCodItmLet.getText().equalsIgnoreCase(strCodLet))
                {
                    if (txtCodItmLet.getText().equals(""))
                    {
                        txtCodItm.setText("");
                        txtCodAltItm.setText("");
                        txtCodItmLet.setText("");
                        txtNomItm.setText("");
                        txtUbiItm.setText("");
                    }
                    else{
                        mostrarVenConItm(3);
                    }
                }
                else{
                    txtCodItmLet.setText(strCodLet);
                }
            }
        }
        else{
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodItmLet.getText().equalsIgnoreCase(strCodLet)){
                if (txtCodItmLet.getText().equals("")){
                    txtCodItm.setText("");
                    txtCodAltItm.setText("");
                    txtCodItmLet.setText("");
                    txtNomItm.setText("");
                    txtUbiItm.setText("");
                }
                else{
                    mostrarVenConItm(3);
                }
            }
            else{
                txtCodItmLet.setText(strCodLet);
            }
        }

    }//GEN-LAST:event_txtCodItmLetFocusLost

    private void txtUbiItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUbiItmFocusLost
        if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) ){
            if(txtCodBod.getText().length()<=0){
                mostrarMsgInf("<HTML>Debe ingresar un dato en el campo Bodega para poder realizar la consulta.<BR>Verifique el valor de la Bodega y vuelva a intentarlo.</HTML>");
                txtNomBod.requestFocus();
                txtCodItmLet.setText("");
            }
            else{
                //Validar el contenido de la celda sólo si ha cambiado.
                if (!txtUbiItm.getText().equalsIgnoreCase(strUbiItm))
                {
                    if (txtUbiItm.getText().equals(""))
                    {
                        txtCodItm.setText("");
                        txtCodAltItm.setText("");
                        txtCodItmLet.setText("");
                        txtNomItm.setText("");
                        txtUbiItm.setText("");
                    }
                    else
                    {
                        mostrarVenConItm(4);
                    }
                }
                else
                    txtUbiItm.setText(strUbiItm);
            }
            
        }
        else{
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtUbiItm.getText().equalsIgnoreCase(strUbiItm))
            {
                if (txtUbiItm.getText().equals(""))
                {
                    txtCodItm.setText("");
                    txtCodAltItm.setText("");
                    txtCodItmLet.setText("");
                    txtNomItm.setText("");
                    txtUbiItm.setText("");
                }
                else
                {
                    mostrarVenConItm(4);
                }
            }
            else
                txtUbiItm.setText(strUbiItm);
        }
 

    }//GEN-LAST:event_txtUbiItmFocusLost

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if(isCamVal())
        {
            if(guardarDatos())
            {
                if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD){
                    cargarDetReg();
                }
                else
                {
                    txtCodItm.setText("");
                    txtCodItmLet.setText("");
                    txtUbiItm.setText("");
                    txtCodAltItm.setText("");
                    txtNomItm.setText("");
                    objTblMod.removeAllRows();
                    objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);      
                }
                mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
            }
            else
            {
                mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void txtCodPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPedActionPerformed
        txtCodPed.transferFocus();
    }//GEN-LAST:event_txtCodPedActionPerformed

    private void txtCodPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusGained
        strCodPed=txtCodPed.getText();
        txtCodPed.selectAll();
    }//GEN-LAST:event_txtCodPedFocusGained

    private void txtCodPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPed.getText().equalsIgnoreCase(strCodPed))
        {
            if (txtCodPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                mostrarPedidos(1);
            }
        }
        else
            txtCodPed.setText(strCodPed);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
            //objTblMod.setDataModelChanged(false);  
        }
        else
        {
            limpiarJtableDat();
        }
    }//GEN-LAST:event_txtCodPedFocusLost

    private void txtPedIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPedIngImpActionPerformed
        txtPedIngImp.transferFocus();
    }//GEN-LAST:event_txtPedIngImpActionPerformed

    private void txtPedIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusGained
        strPedImp=txtPedIngImp.getText();
        txtPedIngImp.selectAll();
    }//GEN-LAST:event_txtPedIngImpFocusGained

    private void txtPedIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtPedIngImp.getText().equalsIgnoreCase(strPedImp))
        {
            if (txtPedIngImp.getText().equals(""))
            {
                txtCodPed.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(2);
            }
        }
        else
            txtPedIngImp.setText(strPedImp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtPedIngImp.getText().length()>0)
        {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
            //objTblMod.setDataModelChanged(false);  
        }
        else
        {
            limpiarJtableDat();
        }
    }//GEN-LAST:event_txtPedIngImpFocusLost

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        configurarPedidos();
        mostrarPedidos(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        }
        else
        {
            limpiarJtableDat();
        }

    }//GEN-LAST:event_butPedImpActionPerformed

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        if (txtPedIngImp.getText().length()>0)
        {
            limpiarJtableDat();
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
            objTblMod.setDataModelChanged(false);  
        }
        else
        {
            limpiarJtableDat();
        }
    }//GEN-LAST:event_butLimActionPerformed

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Titulo de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            //Objeto de Stock de Inventario
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            //Objeto para histórico 
            objHisTblBasDat=new ZafHisTblBasDat();

            //Configurar las ZafVenCon.
            configurarVenConBod();
            configurarVenConItm();
            //configurarPedidos();
            
            //Configurar los JTables.
            configurarTblDat();
            
            //Elementos ocultos.
            txtCodItm.setVisible(false);
            txtCodAltItm.setVisible(false);
            txtNomItm.setVisible(false);
            txtCodPed.setVisible(false);
            panIngImp.setVisible(false);
            butLim.setVisible(false);
            
            //Importaciones
            if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP)
            {
                //configurarPedidos(); //Se comenta porque ya se configura la ventana cada vez que busca el pedido.  
                panBusBodIngImp.add(panIngImp, java.awt.BorderLayout.WEST);
                panIngImp.setVisible(true);
                panItm.setVisible(false);
                panBod.setVisible(false);
                butCon.setVisible(false);
                butLim.setVisible(true);
            }
            
            //Conteo Sin Orden: Será obligatorio ingresar el ítem porque los items no estan asociados a una bodega y a un usuario como en los otros tipos de conteo.
            if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD)  )
            {
                txtCodItmLet.setBackground(objParSis.getColorCamposObligatorios());
                txtUbiItm.setBackground(objParSis.getColorCamposObligatorios());
            }
            
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
            vecCab=new Vector(22);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");            
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DCO_TIP_DOC,"Des.Cor.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DLA_TIP_DOC,"Des.Lar.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_LET,"Cód.Let.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nom.Itm.");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_UBI_ITM,"Ubi.Itm.");
            vecCab.add(INT_TBL_DAT_CHK_SEL_EST,"");
            vecCab.add(INT_TBL_DAT_CAN_BUE_EST_ING_USR,"Can.Bue.Est.");
            vecCab.add(INT_TBL_DAT_CAN_MAL_EST_ING_USR,"Can.Mal.Est.");
            vecCab.add(INT_TBL_DAT_CHK_CON_REA,"Est.Con.");
            vecCab.add(INT_TBL_DAT_EST_ING_IMP,"Est.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_BUE_EST_ING_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_MAL_EST_ING_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
            
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
            
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DCO_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_DAT_DLA_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);            
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL_EST).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CAN_BUE_EST_ING_USR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAL_EST_ING_USR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CON_REA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_EST_ING_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(30);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DCO_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DLA_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_DOC, tblDat);
            
            if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_UBI_ITM, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_MAL_EST_ING_USR, tblDat);
            }
                
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_SEL_EST, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_CON_REA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT, tblDat);
            if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP){
                //Se debe mostrar el nombre del ítem cuando sea conteo de importaciones.
                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_ITM, tblDat);  
            }
            //<editor-fold defaultstate="collapsed" desc="/* comentado */">
//            switch (objParSis.getCodigoMenu())
//            {
//                case 852: //Listado de documentos de ventas...
//                case 238: //Listado de documentos de inventario...
//                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
//                    if (!(objPerUsr.isOpcionEnabled(2504) || objPerUsr.isOpcionEnabled(2505)))
//                    {
//                        //2504: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
//                        //2505: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_ING, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_ING, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_ING, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_MOD, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_MOD, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_MOD, tblDat);
//                    }
//                    break;
//            }
            //</editor-fold>
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkCon=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_CON_REA).setCellRenderer(objTblCelRenChkCon);
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkCon=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CON_REA).setCellEditor(objTblCelEdiChkCon);
            objTblCelEdiChkCon.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeValAplEvn=new BigDecimal(0);
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkSel=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL_EST).setCellRenderer(objTblCelRenChkSel);
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkSel=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SEL_EST).setCellEditor(objTblCelEdiChkSel);
            objTblCelEdiChkSel.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeValAplEvn=new BigDecimal(0);
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //cantidad en buen estado
            objTblCelEdiTxtCanBueEst=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_BUE_EST_ING_USR).setCellEditor(objTblCelEdiTxtCanBueEst);
            objTblCelEdiTxtCanBueEst.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objParSis.getCodigoMenu()!=INT_COD_MNU_CON_INGIMP)
                    {
                        if(!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                            if(intNumConIng==intNumPerCon){
                                objTblCelEdiTxtCanBueEst.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>Está permitido seleccionar " + intNumPerCon + " item por transacción.</HTML>");
                            }
                            else
                                objTblCelEdiTxtCanBueEst.setCancelarEdicion(false);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    Object objCanBueEst=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                    Object objCanMalEst=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_MAL_EST_ING_USR);
                    if( (objCanBueEst==null) || (objCanBueEst.toString().equals("")) ){
                        if( (objCanMalEst==null) || (objCanMalEst.toString().equals("")) ){
                            if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                                objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST);
                                intNumConIng--;
                            }
                        }
                        else{
                            if(!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                                intNumConIng++;
                                objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST);
                            }
                        }
                    }
                    else{
                        if(!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                            intNumConIng++;
                            objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST);
                        }
                    }
                    //Valida que sea un numero lo que se ingrese en cantidad contada en buen estado
                    if(!objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR).toString()) )
                    {
                        objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                        tblDat.editCellAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                    }
                    //Valida que sea un numero positivo lo que se ingrese en cantidad contada en buen estado.
                    if(Double.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR).toString())<=0)
                    {
                        objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                        tblDat.editCellAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                    }
                }
            });
            
            //cantidad en mal estado
            objTblCelEdiTxtCanMalEst=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAL_EST_ING_USR).setCellEditor(objTblCelEdiTxtCanMalEst);
            objTblCelEdiTxtCanMalEst.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objParSis.getCodigoMenu()!=INT_COD_MNU_CON_INGIMP)
                    {
                        if(!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                            if(intNumConIng==intNumPerCon){
                                objTblCelEdiTxtCanMalEst.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>Está permitido seleccionar " + intNumPerCon + " item por transacción.</HTML>");
                            }
                            else
                                objTblCelEdiTxtCanMalEst.setCancelarEdicion(false);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    Object objCanMalEst=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_MAL_EST_ING_USR);
                    Object objCanBueEst=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                    if( (objCanMalEst==null) || (objCanMalEst.toString().equals("")) ){
                        if( (objCanBueEst==null) || (objCanBueEst.toString().equals("")) ){
                            if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                                objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST);
                                intNumConIng--;
                            }
                        }
                        else{
                            if(!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                                intNumConIng++;
                                objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST);
                            }
                        }
                    }
                    else{
                        if(!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
                            intNumConIng++;
                            objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST);
                        }
                    }
                    //Valida que sea un numero lo que se ingrese en cantidad contada en mal estado
                    if(!objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_MAL_EST_ING_USR).toString()) )
                    {
                        objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_MAL_EST_ING_USR);
                        tblDat.editCellAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_MAL_EST_ING_USR);
                    }
                }
            });
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
                  
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LET).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCan=new ZafTblCelRenLbl();
            objTblCelRenLblCan.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCan.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblCan.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_BUE_EST_ING_USR).setCellRenderer(objTblCelRenLblCan);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAL_EST_ING_USR).setCellRenderer(objTblCelRenLblCan);
            objTblCelRenLblCan=null;
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_BUE_EST_ING_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            objTblCelEdiTxtItmLet=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET).setCellEditor(objTblCelEdiTxtItmLet);
            objTblCelEdiTxtItmLet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strCodItmLet="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    java.awt.Color colFonRoj=new java.awt.Color(255,172,165);
                    java.awt.Color colFonBla=new java.awt.Color(255,255,255);
                    strCodItmLet=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LET)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LET).toString();
                    if(!objTblCelEdiTxtItmLet.getText().equals(""))
                    {
                        if(isValExiItm(strCodItmLet))
                        {                            
                            objTblMod.setValueAt("", intFil, INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                            tblDat.editCellAt(intFil, INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                        }
                        else
                        {
                            strAux="<HTML>El item no se encuentra registrado.<BR>¿Está seguro que desea continuar?</HTML>";
                            switch (mostrarMsgCon(strAux, true))
                            {
                                case 0: //YES_OPTION
                                    objTblMod.setValueAt("", intFil, INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                                    tblDat.editCellAt(intFil, INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                                    break;
                                default:
                                    objTblMod.setValueAt("", intFil, INT_TBL_DAT_COD_LET);  
                                    objTblMod.setValueAt("", intFil, INT_TBL_DAT_CAN_BUE_EST_ING_USR);     
                                    tblDat.editCellAt(intFil, INT_TBL_DAT_COD_LET);
                                    break;
                            }
                        }
                        
                        //<editor-fold defaultstate="collapsed" desc="comentado">
                        //objTblMod.setValueAt("", intFil, INT_TBL_DAT_CAN_BUE_EST_ING_USR);
                        //tblDat.editCellAt(intFil, INT_TBL_DAT_CAN_BUE_EST_ING_USR);     
                        //if(isValExiItm(strCodItmLet))
                        //{
                        //    objTblCelRenLbl.setBackground(colFonBla);
                        //    objTblCelRenLblCan.setBackground(colFonBla);
                        //}
                        //else
                        //{
                        //    objTblCelRenLbl.setBackground(colFonRoj);
                        //    objTblCelRenLblCan.setBackground(colFonRoj);
                        //}
                        //</editor-fold>
                    }
                }
            });
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP)
                vecAux.add("" + INT_TBL_DAT_COD_LET);
            vecAux.add("" + INT_TBL_DAT_CAN_BUE_EST_ING_USR);
            vecAux.add("" + INT_TBL_DAT_CAN_MAL_EST_ING_USR);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DCO_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DLA_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código maestro";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del ítem de la empresa (Grupo).";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_COD_LET:
                    strMsg="Código de letras";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_UBI_ITM:
                    strMsg="Ubicación del item";
                    break;
                case INT_TBL_DAT_CAN_BUE_EST_ING_USR:
                    strMsg="Cantidad en buen estado ingresada por el usuario al momento de realizar el conteo del item";
                    break;
                case INT_TBL_DAT_CAN_MAL_EST_ING_USR:
                    strMsg="Cantidad ingresada en mal estado por el usuario al momento de realizar el conteo del item";
                    break;
                case INT_TBL_DAT_CHK_CON_REA:
                    strMsg="Estado de conteo del item";
                    break;
                case INT_TBL_DAT_EST_ING_IMP:
                    strMsg="Estado del ingreso por importación";
                    break;                    
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código del registro";
                    break;

                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBod() 
    {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega");
            arlAli.add("Nombre de Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
            }
            else{
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1 INNER JOIN tbr_bodLocPrgUsr AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
            }            

            vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } 
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
     /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBod(int intTipBus) 
    {
        boolean blnRes = true;
        try {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBod.setCampoBusqueda(2);
                    vcoBod.show();
                    if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtNomBod.setText(strNomBod);
                        }
                    }
                    break;
            }
        } 
        catch (Exception e) 
        {
            blnRes = false;
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
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a2.co_itmMae");
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            arlCam.add("c1.tx_ubi");
            arlCam.add("c1.co_bod");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Letra");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            arlAli.add("Ubicación");
            arlAli.add("Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("56");
            arlAncCol.add("70");
            arlAncCol.add("40");
            arlAncCol.add("240");
            arlAncCol.add("56");
            arlAncCol.add("70");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="	SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor";
            strSQL+=" 	, c1.tx_ubi, c1.co_bod";
            strSQL+=" 	FROM ((tbm_inv AS a1 INNER JOIN tbm_invBod AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm)";
            strSQL+=" 	INNER JOIN tbm_bod AS a5 ON c1.co_emp=a5.co_emp AND c1.co_bod=a5.co_bod";
            if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) ){
                strSQL+=" 	INNER JOIN tbr_bodEmpBodRel AS a6 ON a5.co_emp=a6.co_empRel AND a5.co_bod=a6.co_bodRel";
                strSQL+=" 	AND a6.st_reg='E' AND a6.co_emp=" + objParSis.getCodigoEmpresa() + "";
            }

            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=9;
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            //vcoItm.setCampoBusqueda(1);
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
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try{
            
            strAux="";
            if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) )
                strAux+=" AND a6.co_bod=" + txtCodBod.getText() + "";

            else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP)
                 strAux+=" 	AND a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a5.co_bod=" + strCodBodOrdCon + "";
            
            //strAux+=" 	AND a5.co_emp=" + objParSis.getCodigoEmpresa() + " AND a5.co_bod=" + txtCodBod.getText() + "";
            strAux+=" 	)";
            strAux+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strAux+=" 	LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
            strAux+=" 	WHERE (a1.tx_codAlt NOT LIKE ('%L') AND a1.tx_codAlt NOT LIKE ('%l') )";
            strAux+=" 	AND a1.st_reg='A' AND a5.st_reg='A' ";
            strAux+="	ORDER BY a2.co_itmMae";
            vcoItm.setCondicionesSQL(strAux);

            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(4);//codigo alterno
                    vcoItm.show();
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                        txtCodItm.setText(vcoItm.getValueAt(3));
                        txtCodAltItm.setText(vcoItm.getValueAt(4));
                        txtCodItmLet.setText(vcoItm.getValueAt(5));
                        txtNomItm.setText(vcoItm.getValueAt(6));
                        txtUbiItm.setText(vcoItm.getValueAt(8));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(3));
                        txtCodAltItm.setText(vcoItm.getValueAt(4));
                        txtCodItmLet.setText(vcoItm.getValueAt(5));
                        txtNomItm.setText(vcoItm.getValueAt(6));
                        txtUbiItm.setText(vcoItm.getValueAt(8));
                    }
                    else{
                        vcoItm.setCampoBusqueda(4);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(3));
                            txtCodAltItm.setText(vcoItm.getValueAt(4));
                            txtCodItmLet.setText(vcoItm.getValueAt(5));
                            txtNomItm.setText(vcoItm.getValueAt(6));
                            txtUbiItm.setText(vcoItm.getValueAt(8));
                        }
                        else{
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(3));
                        txtCodAltItm.setText(vcoItm.getValueAt(4));
                        txtCodItmLet.setText(vcoItm.getValueAt(5));
                        txtNomItm.setText(vcoItm.getValueAt(6));
                        txtUbiItm.setText(vcoItm.getValueAt(8));
                    }
                    else{
                        vcoItm.setCampoBusqueda(5);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(3));
                            txtCodAltItm.setText(vcoItm.getValueAt(4));
                            txtCodItmLet.setText(vcoItm.getValueAt(5));
                            txtNomItm.setText(vcoItm.getValueAt(6));
                            txtUbiItm.setText(vcoItm.getValueAt(8));
                        }
                        else{
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;

                case 3: //Búsqueda directa por "Código en letras".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodItmLet.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(3));
                        txtCodAltItm.setText(vcoItm.getValueAt(4));
                        txtCodItmLet.setText(vcoItm.getValueAt(5));
                        txtNomItm.setText(vcoItm.getValueAt(6));
                        txtUbiItm.setText(vcoItm.getValueAt(8));
                    }
                    else{
                        vcoItm.setCampoBusqueda(4);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(3));
                            txtCodAltItm.setText(vcoItm.getValueAt(4));
                            txtCodItmLet.setText(vcoItm.getValueAt(5));
                            txtNomItm.setText(vcoItm.getValueAt(6));
                            txtUbiItm.setText(vcoItm.getValueAt(8));
                        }
                        else{
                            txtCodItmLet.setText(strCodLet);
                        }
                    }
                    break;
                case 4: //Búsqueda directa por "Ubicación del item".
                    if (vcoItm.buscar("c1.tx_ubi", txtUbiItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(3));
                        txtCodAltItm.setText(vcoItm.getValueAt(4));
                        txtCodItmLet.setText(vcoItm.getValueAt(5));
                        txtNomItm.setText(vcoItm.getValueAt(6));
                        txtUbiItm.setText(vcoItm.getValueAt(8));
                    }
                    else{
                        vcoItm.setCampoBusqueda(7);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(3));
                            txtCodAltItm.setText(vcoItm.getValueAt(4));
                            txtCodItmLet.setText(vcoItm.getValueAt(5));
                            txtNomItm.setText(vcoItm.getValueAt(6));
                            txtUbiItm.setText(vcoItm.getValueAt(8));
                        }
                        else{
                            txtUbiItm.setText(strUbiItm);
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
     * mostrar los "Pedidos".
     */
    private boolean configurarPedidos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            arlCam.add("a1.co_imp");
            arlCam.add("a1.co_empOrdCon");
            arlCam.add("a1.co_locOrdCon");
            arlCam.add("a1.co_tipDocOrdCon");
            arlCam.add("a1.co_docOrdCon");
            arlCam.add("a1.co_bodOrdCon");
            arlCam.add("a1.st_ingImp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            arlAli.add("Cód.Imp.");
            arlAli.add("Cód.Emp.Ord.Con.");
            arlAli.add("Cód.Loc.Ord.Con.");
            arlAli.add("Cód.Tip.Doc.Ord.Con.");
            arlAli.add("Cód.Doc.Ord.Con.");
            arlAli.add("Cód.Bod.Ord.Con.");
            arlAli.add("Est.Ing.Imp.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("100");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.st_Reg, a1.fe_Doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+="      , a1.ne_tipNotPed, a1.co_exp, a1.nd_tot, a1.nd_pesTotKgr, a1.st_imp, a1.tx_obs1, a1.tx_obs2";            
            strSQL+="      , a1.st_ingImp, a1.tx_desCor, a1.tx_desLar, a1.tx_natDoc, a1.co_imp, a1.tx_nomImp, a1.co_exp, a1.tx_nomExp, a1.tx_aliExp";
            strSQL+="      , a1.co_empOrdCon, a1.co_locOrdCon, a1.co_tipDocOrdCon, a1.co_docOrdCon, a1.co_bodOrdCon";
            strSQL+=" FROM ";
            strSQL+=" (";
            strSQL+="     SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.st_Reg, a1.fe_Doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+="          , a1.ne_tipNotPed, a1.nd_tot, a1.nd_pesTotKgr, a1.st_imp, a1.tx_obs1, a1.tx_obs2  ";          
            strSQL+="          , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END AS st_ingImp ";
            strSQL+="          , a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp as co_imp, a3.tx_nom as tx_nomImp, a4.co_exp, a4.tx_nom AS tx_nomExp, a4.tx_nom2 AS tx_aliExp";
            strSQL+="          , a.co_emp AS co_empOrdCon, a.co_loc AS co_locOrdCon, a.co_tipDoc AS co_tipDocOrdCon, a.co_doc AS co_docOrdCon, a.co_bod AS co_bodOrdCon ";
            strSQL+="     FROM tbm_cabOrdConInv AS a";
            strSQL+="     INNER JOIN tbm_cabMovInv AS a1 ON (a1.co_emp=a.co_empRel AND a1.co_loc=a.co_locRel AND a1.co_tipDoc=a.co_tipDocRel AND a1.co_doc=a.co_docRel )";
            strSQL+="     INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
            strSQL+="     INNER JOIN tbm_emp AS a3 ON (a3.co_emp=a1.co_emp)";
            strSQL+="     LEFT OUTER JOIN tbm_expImp AS a4 ON(a4.co_exp=a1.co_exp)";
            strSQL+="     WHERE a.st_Reg IN ('A') AND a1.st_reg IN ('A') AND a1.co_mnu IN("+objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+")";
            //Se mostrarán solo los pedidos A=Arribados, B=En proceso de conteo en Bodega. Nota Importante: Los pedidos cerrados no deben presentarse.
            strSQL+="     AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) IN ('A', 'B') ";
            strSQL+=" ) as a1";
            strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.st_Reg, a1.fe_Doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+="        , a1.ne_tipNotPed, a1.co_exp, a1.nd_tot, a1.nd_pesTotKgr, a1.st_imp, a1.tx_obs1, a1.tx_obs2 ";        
            strSQL+="        , a1.st_ingImp, a1.tx_desCor, a1.tx_desLar, a1.tx_natDoc, a1.co_imp, a1.tx_nomImp, a1.co_exp, a1.tx_nomExp, a1.tx_aliExp";
            strSQL+="        , a1.co_empOrdCon, a1.co_locOrdCon, a1.co_tipDocOrdCon, a1.co_docOrdCon, a1.co_bodOrdCon";
            strSQL+=" ORDER BY a1.fe_doc, a1.tx_numDoc2";
            //System.out.println("configurarPedidos: "+strSQL);
            //Ocultar columnas.
            int intColOcu[]=new int[12];
            intColOcu[0]=1;   //co_emp
            intColOcu[1]=2;   //co_loc
            intColOcu[2]=3;   //co_tipDoc
            intColOcu[3]=4;   //co_doc
            intColOcu[4]=5;   //ne_numDoc
            intColOcu[5]=7;   //co_imp
            intColOcu[6]=8;   //co_empOrdCon
            intColOcu[7]=9;   //co_locOrdCon
            intColOcu[8]=10;  //co_tipDocOrdCon
            intColOcu[9]=11;  //co_docOrdCon
            intColOcu[10]=12; //co_bodOrdCon
            intColOcu[11]=13; //st_ingImp
       
            vcoPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPed.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoPed.setConfiguracionColumna(6, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   /**
    * Función que permite mostrar la información del Ingreso por Importación
    * @return true Si se pudo realizar la operación
    * <BR>false caso contrario
    */
    private boolean mostrarPedidos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPed.setCampoBusqueda(5);
                    vcoPed.setVisible(true);
                    if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        strCodEmpIngImp = vcoPed.getValueAt(1);
                        strCodLocIngImp = vcoPed.getValueAt(2);
                        strCodTipDocIngImp = vcoPed.getValueAt(3);
                        strCodDocIngImp = vcoPed.getValueAt(4);
                        strPedImp = vcoPed.getValueAt(6);
                        strCodEmpOrdCon = vcoPed.getValueAt(8);
                        strCodLocOrdCon = vcoPed.getValueAt(9);
                        strCodTipDocOrdCon = vcoPed.getValueAt(10);
                        strCodDocOrdCon = vcoPed.getValueAt(11);
                        strCodBodOrdCon = vcoPed.getValueAt(12);
                        strEstIngImp = vcoPed.getValueAt(13);
                        
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        txtCodBod.setText(vcoPed.getValueAt(12));
                        
                        limpiarJtableDat();
                    }
                    break;
                 case 1: //Búsqueda directa por "Código Pedido".
                    if (vcoPed.buscar("a1.co_doc", txtCodPed.getText()))
                    {
                        strCodEmpIngImp = vcoPed.getValueAt(1);
                        strCodLocIngImp = vcoPed.getValueAt(2);
                        strCodTipDocIngImp = vcoPed.getValueAt(3);
                        strCodDocIngImp = vcoPed.getValueAt(4);
                        strPedImp = vcoPed.getValueAt(6);
                        strCodEmpOrdCon = vcoPed.getValueAt(8);
                        strCodLocOrdCon = vcoPed.getValueAt(9);
                        strCodTipDocOrdCon = vcoPed.getValueAt(10);
                        strCodDocOrdCon = vcoPed.getValueAt(11);
                        strCodBodOrdCon = vcoPed.getValueAt(12);
                        strEstIngImp = vcoPed.getValueAt(13);
                        
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        txtCodBod.setText(vcoPed.getValueAt(12));
                        
                        limpiarJtableDat();
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(3);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            strCodEmpIngImp = vcoPed.getValueAt(1);
                            strCodLocIngImp = vcoPed.getValueAt(2);
                            strCodTipDocIngImp = vcoPed.getValueAt(3);
                            strCodDocIngImp = vcoPed.getValueAt(4);
                            strPedImp = vcoPed.getValueAt(6);
                            strCodEmpOrdCon = vcoPed.getValueAt(8);
                            strCodLocOrdCon = vcoPed.getValueAt(9);
                            strCodTipDocOrdCon = vcoPed.getValueAt(10);
                            strCodDocOrdCon = vcoPed.getValueAt(11);
                            strCodBodOrdCon = vcoPed.getValueAt(12);
                            strEstIngImp = vcoPed.getValueAt(13);

                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            txtCodBod.setText(vcoPed.getValueAt(12));
                            
                            limpiarJtableDat();
                        }
                        else
                        {
                            txtCodPed.setText(strCodPed); 
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoPed.buscar("a1.tx_numDoc2", txtPedIngImp.getText()))
                    {
                        strCodEmpIngImp = vcoPed.getValueAt(1);
                        strCodLocIngImp = vcoPed.getValueAt(2);
                        strCodTipDocIngImp = vcoPed.getValueAt(3);
                        strCodDocIngImp = vcoPed.getValueAt(4);
                        strPedImp = vcoPed.getValueAt(6);
                        strCodEmpOrdCon = vcoPed.getValueAt(8);
                        strCodLocOrdCon = vcoPed.getValueAt(9);
                        strCodTipDocOrdCon = vcoPed.getValueAt(10);
                        strCodDocOrdCon = vcoPed.getValueAt(11);
                        strCodBodOrdCon = vcoPed.getValueAt(12);
                        strEstIngImp = vcoPed.getValueAt(13);

                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        txtCodBod.setText(vcoPed.getValueAt(12));
                        
                        limpiarJtableDat();
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(5);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            strCodEmpIngImp = vcoPed.getValueAt(1);
                            strCodLocIngImp = vcoPed.getValueAt(2);
                            strCodTipDocIngImp = vcoPed.getValueAt(3);
                            strCodDocIngImp = vcoPed.getValueAt(4);
                            strPedImp = vcoPed.getValueAt(6);
                            strCodEmpOrdCon = vcoPed.getValueAt(8);
                            strCodLocOrdCon = vcoPed.getValueAt(9);
                            strCodTipDocOrdCon = vcoPed.getValueAt(10);
                            strCodDocOrdCon = vcoPed.getValueAt(11);
                            strCodBodOrdCon = vcoPed.getValueAt(12);
                            strEstIngImp = vcoPed.getValueAt(13);

                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            txtCodBod.setText(vcoPed.getValueAt(12));
                            
                            limpiarJtableDat();
                        }
                        else
                        {
                            txtPedIngImp.setText(strPedImp);
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
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butLim;
    private javax.swing.JButton butPedImp;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBusBodIngImp;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panIngImp;
    private javax.swing.JPanel panItm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodItmLet;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtPedIngImp;
    private javax.swing.JTextField txtUbiItm;
    // End of variables declaration//GEN-END:variables

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }       

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
    
     /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
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
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Número de documento <FONT COLOR=\"red\">ya en uso </FONT>. <BR>.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    
   /**
     * Limpia el Jtable 
     * Esta función es utilizada cada vez que no exista pedido seleccionado
     */
    private void limpiarJtableDat()
    {
        objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        vecDat.clear();
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        objTblMod.removeAllRows();
        objTblMod.initRowsState();
        objTblMod.setDataModelChanged(false);  
    }
    
   /**
     * Esta función valida los filtros al momento de realizar la consulta
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamValCon()
    {
        //Para Conteo sin orden sea obligatorio....para conteo con orden no y conteo de importaciones porque ya los items estan asociados a una bodega y a un usuario
        if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD)  )
        {
            if(txtCodItm.getText().length()<=0)
            {
                mostrarMsgInf("<HTML>El item que desea contar no ha sido ingresado<BR>Ingrese el item que desea contar vuelva a intentarlo.</HTML>");
                return false;
            }
        }
        
        if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) )
        {
            if(txtCodBod.getText().length()<=0)
            {
                txtCodItm.setText("");
                txtCodItmLet.setText("");
                txtUbiItm.setText("");
                txtCodAltItm.setText("");
                txtNomItm.setText("");
                mostrarMsgInf("<HTML>El campo Bodega es obligatorio.<BR>Ingrese la Bodega y vuelva a intentarlo.</HTML>");
                txtNomBod.requestFocus();
                return false;
            }
        }
        
        return true;
    }   
        
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP) 
        {
            if((txtCodPed.getText().trim().length()<=0))
            { 
                limpiarJtableDat();
                mostrarMsgInf("<HTML>El número de Pedido debe ser seleccionado<BR>Seleccione el número de Pedido y vuelva a intentarlo.</HTML>");
                return false;
            }
            
            //Valida que pedido no haya sido cerrado.
            if(!validaPedido()){
                limpiarJtableDat();
                mostrarMsgInf("<HTML>El pedido <FONT COLOR=\"blue\">"+txtPedIngImp.getText()+" </FONT> ha sido cerrado.<BR>No se puede procesar ninguna solicitud de este pedido.</HTML>");
                return false;
            }
            
        }
        if(!objTblMod.isDataModelChanged())
        {
            mostrarMsgInf("<HTML>Debe realizar cambios para poder guardarlos.<BR>Ingrese la cantidad contada en el item y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(!objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_SEL_EST)){
            mostrarMsgInf("<HTML>Debe realizar cambios para poder guardarlos.<BR>Ingrese la cantidad contada en el item y vuelva a intentarlo.</HTML>");
            return false;
        }
        return true;
    }
    
    /**
     * Esta función determina si el ítem existe en el inventario.
     * @return true: Si el ítem existe
     * <BR>false: En el caso contrario.
     */
    private boolean isValExiItm(String codItmLet) 
    {
        boolean blnRes = false;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm as co_itmGrp, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor as tx_desCorUniMed";
                strSQL+="	 , CAST('' AS CHARACTER VARYING) as tx_ubi, c1.co_bod, " + objParSis.getCodigoEmpresaGrupo() + " AS co_empGrp";
                strSQL+=" FROM ((tbm_inv AS a1 INNER JOIN tbm_invBod AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm)";
                strSQL+=" 	 INNER JOIN tbm_bod AS a5 ON c1.co_emp=a5.co_emp AND c1.co_bod=a5.co_bod";
                strSQL+=" 	 AND a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a5.co_bod=" + strCodBodOrdCon + "";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
                strSQL+=" WHERE (a1.tx_codAlt NOT LIKE ('%L') AND a1.tx_codAlt NOT LIKE ('%l') )";
                strSQL+=" AND a1.st_reg='A' AND a5.st_reg='A' ";
                strSQL+=" AND a1.tx_codAlt2="+objUti.codificar(codItmLet.trim().toUpperCase())+"";
                strSQL+=" ORDER BY a2.co_itmMae";
                rst = stm.executeQuery(strSQL);

                if (rst.next()) 
                {
                    objTblMod.setValueAt(rst.getString("co_itmMae"), tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM_MAE);
                    objTblMod.setValueAt(rst.getString("co_itmGrp"), tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM);
                    objTblMod.setValueAt(rst.getString("tx_codAlt"), tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT);
                    objTblMod.setValueAt(rst.getString("tx_codAlt2"), tblDat.getSelectedRow(), INT_TBL_DAT_COD_LET);
                    objTblMod.setValueAt(rst.getString("tx_nomItm"), tblDat.getSelectedRow(), INT_TBL_DAT_NOM_ITM);
                    objTblMod.setValueAt(rst.getString("tx_desCorUniMed"), tblDat.getSelectedRow(), INT_TBL_DAT_UNI_MED);
                    objTblMod.setValueAt(rst.getString("tx_ubi"), tblDat.getSelectedRow(), INT_TBL_DAT_UBI_ITM);
                    return true;
                } 
                else
                {
                    objTblMod.setValueAt(new Integer("0"), tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM_MAE);
                    objTblMod.setValueAt(new Integer("0"), tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM);
                    objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT);
                    //objTblMod.setValueAt(codItmLet, tblDat.getSelectedRow(), INT_TBL_DAT_COD_LET);
                    objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_NOM_ITM);
                    objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_UNI_MED);
                    objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_UBI_ITM);
                    return false;
                }
            }
            con.close();
            rst.close();
            stm.close();
            con=null;
            rst=null;
            stm=null;
        }
        catch (java.sql.SQLException e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    /**
     * Valida si el pedido fue ingresado en el esquema anterior o en el nuevo.
     * @return TRUE: Si pedido esquema nuevo.
     * false: Esquema antiguo, antes del 13Sep2017
     */
    private boolean validaFechaPedido(java.sql.Connection conn) 
    {
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        boolean blnRes=false;
        try
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                strSQL ="";
                strSQL+=" SELECT fe_doc, fe_ing FROM tbm_cabMovInv ";
                strSQL+=" WHERE st_reg IN ('A')";
                strSQL+=" AND co_emp="+strCodEmpIngImp;
                strSQL+=" AND co_loc="+strCodLocIngImp;
                strSQL+=" AND co_tipDoc="+strCodTipDocIngImp;
                strSQL+=" AND co_doc="+strCodDocIngImp;
                strSQL+=" AND CAST(fe_ing AS DATE)>='"+strFecEsqAnt+"'";
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()) 
                {
                    //Esquema Nuevo
                    blnRes=true;  
                }
            }
            rstLoc.close();
            stmLoc.close();
            rstLoc=null;
            stmLoc=null;
        }
        catch (java.sql.SQLException e) { blnRes=false;   objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
        /**
     * Valida que pedido no haya sido cerrado, ni pendiente de arribo antes de insertar.
     * @return TRUE: Si pedido no ha sido cerrado o pendiente de arribo.
     */
    private boolean validaPedido() 
    {
        java.sql.Connection conLoc=null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        boolean blnRes=false;
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.st_ingImp FROM tbm_CabMovInv as a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.st_reg='A' ";
                strSQL+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END ) NOT IN ('N', 'P', 'C')  ";
                strSQL+=" AND a1.co_emp="+strCodEmpIngImp;
                strSQL+=" AND a1.co_loc="+strCodLocIngImp;
                strSQL+=" AND a1.co_tipDoc="+strCodTipDocIngImp;
                strSQL+=" AND a1.co_doc="+strCodDocIngImp;
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()) 
                {
                    blnRes=true;  
                }
            }
            rstLoc.close();
            stmLoc.close();
            conLoc.close();
            rstLoc=null;
            stmLoc=null;
            conLoc=null;
        }
        catch (java.sql.SQLException e) { blnRes=false;   objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);      }
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
                tabFrm.setSelectedIndex(0);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        String strAuxItm="";
        intNumConIng=0;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //item
                if(txtCodItm.getText().toString().length()>0)
                    strAuxItm+=" AND a1.co_itm=" + txtCodItm.getText() + "";
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT b1.co_itmMae, b1.co_itmGrp, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCorUniMed, b1.tx_desLarUniMed";
                strSQL+=", b1.tx_codAlt2, b1.tx_ubi, b1.co_empGrp";
                if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP) ){//con orden y por importacion
                    strSQL+=", b2.fe_solCon, b2.co_emp, b2.co_reg";
                    strSQL+=", b2.co_locRel, b2.co_tipDocRel, b2.co_docRel, b2.tx_desCorTipDoc, b2.tx_desLarTipDoc, b2.st_conRea, b2.ne_numDoc, b2.fe_doc";
                }
                else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD){//sin orden
                    strSQL+=", null AS fe_solCon, null AS co_emp, null AS co_reg";
                    strSQL+=", null AS co_locRel, null AS co_tipDocRel, null AS co_docRel, null AS tx_desCorTipDoc, null AS tx_desLarTipDoc, null AS st_conRea, null AS ne_numDoc, null AS fe_doc";
                }                   
                strSQL+=" FROM(";                
                strSQL+="	SELECT a1.co_itmMae, a1.co_emp, a1.co_itm AS co_itmEmp, a1.tx_codAlt, a1.tx_nomItm, a1.tx_desCorUniMed, a1.tx_desLarUniMed";
                strSQL+=" 	     , a1.tx_codAlt2, a1.tx_ubi, a1.co_bod, a1.co_empGrp, a2.co_itm AS co_itmGrp";
                strSQL+=" 	FROM(";
                
                //con orden y sin orden
                if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD))
                {
                    strSQL+="           SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor AS tx_desCorUniMed, a3.tx_desLar AS tx_desLarUniMed";
                    strSQL+="                , a1.tx_codAlt2, c1.tx_ubi, c1.co_bod, a6.co_emp AS co_empGrp";
                    strSQL+="           FROM ((tbm_inv AS a1 INNER JOIN tbm_invBod AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm)";
                    strSQL+="           INNER JOIN tbm_bod AS a5 ON c1.co_emp=a5.co_emp AND c1.co_bod=a5.co_bod";
                    strSQL+="           INNER JOIN tbr_bodEmpBodRel AS a6 ON a5.co_emp=a6.co_empRel AND a5.co_bod=a6.co_bodRel";
                    strSQL+="           AND a6.st_reg='E' AND a6.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="           AND a6.co_bod=" + txtCodBod.getText() + "";
                    strSQL+="           )";
                    strSQL+="           INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+="           LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
                    strSQL+="           WHERE (a1.tx_codAlt NOT LIKE ('%L') AND a1.tx_codAlt NOT LIKE ('%l') )";
                    strSQL+="           AND a1.st_reg='A' AND a5.st_reg='A'";
                }
                else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP) //por importacion
                {
                     strSQL+=" 		SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor AS tx_desCorUniMed, a3.tx_desLar AS tx_desLarUniMed           ";
                     strSQL+=" 		     , a1.tx_codAlt2,  CAST('' AS CHARACTER VARYING) AS tx_ubi, 0 AS co_bod, " + objParSis.getCodigoEmpresa() + " AS co_empGrp";
                     strSQL+=" 		FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                     strSQL+=" 		LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
                     strSQL+=" 		WHERE (a1.tx_codAlt NOT LIKE ('%L') AND a1.tx_codAlt NOT LIKE ('%l') )";
                     strSQL+=" 		AND a1.st_reg='A' AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
                strSQL+="           " + strAuxItm + "";
                strSQL+=" 		ORDER BY a2.co_itmMae";
                strSQL+="	) AS a1";
                strSQL+=" 	INNER JOIN( SELECT *FROM tbm_equInv AS a1 ) AS a2 ON a1.co_empGrp=a2.co_emp AND a1.co_itmMae=a2.co_itmMae";
                strSQL+=" ) AS b1";
                
                if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP) )//con orden e importacion
                {
                    if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD)
                        strSQL+=" INNER JOIN(";
                    else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP)
                        strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT x.fe_solCon, y.co_emp, y.co_itm, y.co_reg, y.co_locRel, y.co_tipDocRel, y.co_docRel ";
                    strSQL+="	      ,y.tx_desCorTipDoc, y.tx_desLarTipDoc, y.st_conRea, y.co_itmMae, y.ne_numDoc, y.fe_doc";
                    strSQL+="	FROM(";
                    strSQL+="         SELECT MAX(fe_solCon) as fe_solCon, co_emp, co_itm";
                    strSQL+="         FROM tbm_conInv WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="         AND st_conRea NOT IN('I','E') ";
                    if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) )
                        strSQL+="         AND co_bod=" + txtCodBod.getText() + "";
                    else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP)
                        strSQL+="         AND co_bod=" + strCodBodOrdCon + "";
                        
                    if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD){
                        //strSQL+="         AND st_conRea='N'";  //Comentado en version v0.9.8 por Ingrid Lino segun lo solicitado por Miguel.
                        strSQL+="         AND co_usrrescon=" + objParSis.getCodigoUsuario() + "";
                    }
                    strSQL+="         GROUP BY co_emp, co_itm ORDER BY co_itm, fe_solCon";
                    strSQL+="	) AS x";
                    strSQL+="  	INNER JOIN(";
                    strSQL+=" 	      SELECT a1.co_emp, a1.co_itm, a1.fe_solCon, a1.co_reg";
                    strSQL+="		   , a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                    strSQL+=" 		   , a2.tx_desCor AS tx_desCorTipDoc, a2.tx_desLar AS tx_desLarTipDoc, a1.st_conRea, b2.co_itmMae, a3.ne_numDoc, a3.fe_doc";
                    strSQL+=" 	      FROM ( ((tbm_inv AS b1 INNER JOIN tbm_equInv AS b2 ON b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                    strSQL+=" 		     INNER JOIN tbm_conInv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm)";
                    strSQL+=" 		     INNER JOIN (tbm_cabOrdConInv AS a3";//left sin orden e inner con orden                                
                    strSQL+="		                 INNER JOIN tbm_cabTipDoc AS a2 ON a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc";
                    strSQL+="		     ) ON a1.co_emp=a3.co_emp AND a1.co_locRel=a3.co_loc AND a1.co_tipDocRel=a3.co_tipDoc AND a1.co_docRel=a3.co_doc AND a3.st_reg NOT IN('I','E')";
                    strSQL+=" 	      )";
                    strSQL+=" 	      WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="	      AND b1.st_reg NOT IN('I','E')";
                    
                    if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD){
                        strSQL+="	      AND a1.st_conRea='N'";
                        strSQL+="	      AND a1.co_usrResCon=" + objParSis.getCodigoUsuario() + "";
                    }

                    if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) )
                        strSQL+="	      AND a1.co_bod=" + txtCodBod.getText() + "";
                    
                    if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP){
                        strSQL+="	      AND a3.co_emp=" + strCodEmpOrdCon + "";
                        strSQL+="	      AND a3.co_loc=" + strCodLocOrdCon + "";
                        strSQL+="	      AND a3.co_tipDoc=" + strCodTipDocOrdCon + "";
                        strSQL+="	      AND a3.co_doc=" + strCodDocOrdCon + "";
                    }
                    
                    strSQL+="	      ORDER BY a1.co_itm";
                    strSQL+="  	) AS y ON x.co_emp=y.co_emp AND x.co_itm=y.co_itm AND x.fe_solCon=y.fe_solCon";
                    strSQL+=" 	ORDER BY x.co_itm";
                    strSQL+=" ) AS b2 ON b1.co_empGrp=b2.co_emp AND b1.co_itmMae=b2.co_itmMae";
                }
                
                
                strSQL+=" GROUP BY b1.co_itmMae, b1.co_itmGrp, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCorUniMed, b1.tx_desLarUniMed";
                strSQL+="        , b1.tx_codAlt2, b1.tx_ubi, b1.co_empGrp";
                if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP) ){//con orden y por importacion
                    strSQL+="    , b2.fe_solCon, b2.co_emp, b2.co_reg";
                    strSQL+="    , b2.co_locRel, b2.co_tipDocRel, b2.co_docRel, b2.tx_desCorTipDoc, b2.tx_desLarTipDoc, b2.st_conRea, b2.ne_numDoc, b2.fe_doc";
                }              
                if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP) ){//con orden e importacion : porque si existe e codigoRegistro
                    strSQL+=" ORDER BY b2.co_reg";
                }
                else{
                    strSQL+=" ORDER BY b1.tx_codAlt";
                }
                
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
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_empGrp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_locRel"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDocRel"));
                        vecReg.add(INT_TBL_DAT_DCO_TIP_DOC,rst.getString("tx_desCorTipDoc"));
                        vecReg.add(INT_TBL_DAT_DLA_TIP_DOC,rst.getString("tx_desLarTipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_docRel"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itmGrp"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_LET,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("tx_desCorUniMed"));
                        vecReg.add(INT_TBL_DAT_UBI_ITM,rst.getString("tx_ubi"));
                        vecReg.add(INT_TBL_DAT_CHK_SEL_EST,null);
                        vecReg.add(INT_TBL_DAT_CAN_BUE_EST_ING_USR,null);
                        vecReg.add(INT_TBL_DAT_CAN_MAL_EST_ING_USR,null);
                        vecReg.add(INT_TBL_DAT_CHK_CON_REA,null);
                        vecReg.add(INT_TBL_DAT_EST_ING_IMP,null);
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        
                        strAux=rst.getObject("st_conRea")==null?"N":rst.getString("st_conRea");
                        vecReg.setElementAt((strAux.equals("S")?true:false), INT_TBL_DAT_CHK_CON_REA);
                        
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
                objTblMod.initRowsState();
                vecDat.clear();
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
    * Función que permite guardar los conteos realizados
    * @return 
    */ 
   private boolean guardarDatos()
   {
       boolean blnRes=false;
       try
       {
           con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(con!=null)
           {
                con.setAutoCommit(false);
                if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD))
                {
                    if(actualiza_tbmConInv())
                    {
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
                }
                else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_INGIMP) 
                {
                    if(actualizaConteoImportaciones())
                    {
                        if(actualiza_tbmCabMovInv())
                        {
                            if(generaConItmTbmInvBod())
                            {
                                if(objStkInv.actualizaInventario(con, Integer.parseInt(strCodEmpIngImp),INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm))
                                { 
                                    //Se decrementa la cantidad por ingresar en Importaciones.
                                    if(objStkInv.actualizaInventario(con, Integer.parseInt(strCodEmpIngImp),INT_ARL_STK_INV_CAN_ING_BOD, "-", 0, arlDatStkInvItm))
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
                        else
                            con.rollback();   
                    }
                    else
                        con.rollback();  
                    
                    arlDatStkInvItm.clear();
                }
                con.close();
                con=null;
           }
           
       }
       catch(java.sql.SQLException e)
       {
           blnRes=false;
       }
       catch(Exception e)
       {
           blnRes=false;
       }
       return blnRes;
   }
    
   /**
    * Función que permite guardar la información en la tabla de conteos
    * @return true si se pudo realizar la operación
    * <BR> false caso contrario
    */ 
   private boolean actualiza_tbmConInv()
   {
       boolean blnRes=true;
       BigDecimal bdeStkAct=new BigDecimal("0");
       BigDecimal bdeCanSob=new BigDecimal("0");
       BigDecimal bdeCanFalBodAso=new BigDecimal("0");
       BigDecimal bdeCanPndIngBod=new BigDecimal("0");
       BigDecimal bdeCanPndEgrBod=new BigDecimal("0");
       BigDecimal bdeCanPndIngDes=new BigDecimal("0");
       BigDecimal bdeCanPndEgrDes=new BigDecimal("0");
       BigDecimal bdeCanBueEst=new BigDecimal("0");
       BigDecimal bdeCanMalEst=new BigDecimal("0");
       int intUltReg=-1;
       String strSQLUpd="";
       String strFecUltMod;
       try
       {
           if(con!=null){
                stm=con.createStatement();
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());               
               
                for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_SEL_EST))
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Datos y cantidades del ítem (tbm_invBod) */">
                        //Datos del ítem
                        strSQL ="";
                        strSQL+=" SELECT b1.nd_stkAct, b1.co_itmMae, b2.co_emp, b2.co_itm, b2.nd_canSob, b3.nd_canFalBodAso";
                        //Stock
                        strSQL+=" FROM( ";
                        strSQL+="     SELECT SUM(z.nd_stkAct) AS nd_stkAct, z.co_itmMae ";
                        strSQL+="     FROM( ";
                        strSQL+="  	SELECT x.* FROM( ";
                        strSQL+="  		SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae";
                        strSQL+="  		FROM tbm_invBod AS a1 ";
                        strSQL+="  		INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+="  		WHERE a2.co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + "";
                        strSQL+="  	) AS x ";
                        strSQL+="  	INNER JOIN( ";
                        strSQL+="  		SELECT co_emp, co_bod";
                        strSQL+="  		FROM tbr_bodEmpBodGrp";
                        strSQL+="  		WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="               AND co_bodGrp=" + txtCodBod.getText() + "";
                        strSQL+="  		ORDER BY co_emp";
                        strSQL+="  	) AS y ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                        strSQL+="     ) AS z";
                        strSQL+="     GROUP BY z.co_itmMae";
                        strSQL+="  ) AS b1";
                        //--sobrante-grupo
                        strSQL+=" LEFT OUTER JOIN(";
                        strSQL+="  	SELECT a1.co_emp, a1.co_itm, a2.co_itmMae, a3.nd_cansob";
                        strSQL+="  	FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                        strSQL+="  	INNER JOIN tbm_invBod AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                        strSQL+="  	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="       AND a3.co_bod=" + txtCodBod.getText() ;
                        strSQL+="       AND a2.co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + "";
                        strSQL+=" ) AS b2 ON b1.co_itmMae=b2.co_itmMae";
                        //--faltante
                        strSQL+="  LEFT OUTER JOIN(";
                        strSQL+="       SELECT c1.co_emp, c1.co_bodRel, c1.co_itmMae, SUM(c1.nd_stkAct) AS nd_canFalBodAso ";
                        strSQL+=" 	FROM(	";
                        strSQL+=" 	    SELECT a1.co_emp, a1.co_bod, a1.co_empRel, a1.co_bodRel, a4.co_itm, a5.co_itmMae, a2.nd_stkAct ";
                        strSQL+="           FROM tbr_bodEmpBodRel AS a1 ";
                        strSQL+="           INNER JOIN tbr_bodEmpBodGrp AS b2 ON a1.co_empRel=b2.co_empGrp AND a1.co_bodRel=b2.co_bodGrp";
                        strSQL+="           INNER JOIN (tbm_invBod AS a2 INNER JOIN tbm_inv AS a4 ON a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm";
                        strSQL+=" 	                INNER JOIN tbm_equInv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm    ";
                        strSQL+="           )ON b2.co_emp=a2.co_emp AND b2.co_bod=a2.co_bod";
                        strSQL+=" 	    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_bod=" + txtCodBod.getText() + "";
                        strSQL+="           AND a1.st_reg='F' AND a5.co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + "";
                        strSQL+="       ) AS c1";
                        strSQL+="       GROUP BY c1.co_emp, c1.co_bodRel, c1.co_itmMae";
                        strSQL+="  ) AS b3 ON b1.co_itmMae=b3.co_itmMae";
                        rst=stm.executeQuery(strSQL);
                        while(rst.next())
                        {
                            bdeStkAct=new BigDecimal(rst.getObject("nd_stkAct")==null?"0":(rst.getString("nd_stkAct").equals("")?"0":rst.getString("nd_stkAct")));
                            bdeCanSob=new BigDecimal(rst.getObject("nd_canSob")==null?"0":(rst.getString("nd_canSob").equals("")?"0":rst.getString("nd_canSob")));
                            bdeCanFalBodAso=new BigDecimal(rst.getObject("nd_canFalBodAso")==null?"0":(rst.getString("nd_canFalBodAso").equals("")?"0":rst.getString("nd_canFalBodAso")));
                        }
                        rst.close();
                        rst=null;
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="/* Cantidades por Ingresar/Egresar (tbm_DetMovInv) */">
                        //Obtener cantidades por Egresar
                        strSQL="";
                        strSQL+=" SELECT SUM( b1.nd_canPenConBod) as nd_canPenConBod, SUM(b1.nd_canPenConDes) as nd_canPenConDes \n";
                        strSQL+="  FROM (  \n";
                        strSQL+="    SELECT y.*, CASE WHEN y.co_bodGrp in (15,3) THEN y.nd_canPenBod ELSE 0 END as nd_canPenConBod  \n";
                        strSQL+="              , CASE WHEN y.co_bodGrp in (15,3) THEN y.nd_canPenDes ELSE y.nd_canPenBod END as nd_canPenConDes   \n";
                        strSQL+="    FROM (  \n";
                        strSQL+="      SELECT x.* , x1.co_bodGrp, x2.co_itmMae FROM  (  \n";
                        strSQL+=" 		 SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.co_itm, a2.co_bod, a2.ne_numDoc, a2.st_conInv, a2.tx_tipMov, \n";
                        strSQL+="                       a.co_emp as CodEmpOrd, a.co_loc as CodLocOrd, a.co_tiPDoc as CodTipDocOrd, a.co_doc as CodDocOrd, a.ne_numOrdDes,  \n";
                        strSQL+=" 			a2.st_merIngegrfisbod, a2.tx_codAlt, a2.tx_codAlt2, abs(a2.nd_can) as nd_canMov,   \n";                       
                        strSQL+="                       CASE WHEN a2.nd_canEgrBod IS NULL THEN 0 ELSE (a2.nd_canEgrBod*-1) END as nd_canPenBod , \n";
                        strSQL+="                       CASE WHEN a2.nd_CanDesEntCli IS NULL THEN 0 ELSE (a2.nd_CanDesEntCli*-1) END as nd_canPenDes \n";
                        strSQL+=" 		 FROM tbm_CabGuiRem as a  \n";
                        strSQL+=" 		 INNER JOIN tbm_detGuiRem as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tiPDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc) \n";
                        strSQL+="                INNER JOIN  \n";
                        strSQL+="                ( \n";
                        strSQL+=" 		    SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.ne_numDoc, a1.co_reg, a1.co_itm, a1.co_bod, a.st_conInv, a.tx_tipMov,  \n";
                        strSQL+=" 			   a1.st_merIngegrfisbod, a1.tx_codAlt, a1.tx_codAlt2, a1.nd_can, a1.nd_canEgrBod, a1.nd_CanDesEntCli , a.st_reg, a.st_conInv as st_conInvMov \n";
                        strSQL+=" 		    FROM tbm_cabMovInv as a \n";
                        strSQL+=" 	            INNER JOIN tbm_detMovInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tiPDoc=a.co_tipDoc AND a1.co_doc=a.co_doc) \n";
                        strSQL+="                ) as a2 ON (a2.co_Emp=a1.co_empRel AND a2.co_loc=a1.co_locRel AND a2.co_TipDoc=a1.co_tipDocRel AND a2.co_doc=a1.co_docRel AND a2.co_reg=a1.co_RegRel) \n";   	
                        strSQL+=" 		 WHERE a2.nd_Can<0 AND a2.st_reg IN('A') AND a2.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod IN('S')  \n"; 
                        strSQL+=" 		 AND ( CASE WHEN a2.st_conInvMov IS NULL THEN 'P' ELSE a2.st_conInvMov END  ) NOT IN ('F') AND a.st_reg IN('A') 	 \n";
                        strSQL+=" 		 GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.co_itm, a2.co_bod, a2.ne_numDoc, a2.st_conInv, a2.tx_tipMov,  \n";
                        strSQL+="                         a.co_emp, a.co_loc, a.co_tiPDoc, a.co_doc, a.ne_numOrdDes,  \n";
                        strSQL+=" 			  a2.st_merIngegrfisbod, a2.tx_codAlt, a2.tx_codAlt2, a2.nd_can, a2.nd_canEgrBod, a2.nd_CanDesEntCli \n";
                        strSQL+=" 		 ORDER BY a.ne_numOrdDes, a.co_emp, a.co_loc, a.co_tiPDoc, a.co_doc \n";
                        strSQL+="       ) as x  \n";      
                        strSQL+="       INNER JOIN  \n";
                        strSQL+="       ( \n";
                        strSQL+="          SELECT co_emp, co_bod, co_bodGrp FROM tbr_bodEmpBodGrp  \n";
                        strSQL+="          WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "  AND co_bodGrp=" + txtCodBod.getText() + " \n";
                        strSQL+="       ) as x1 ON (x1.co_emp=x.co_emp AND x1.co_bod=x.co_bod)   \n"; 
                        strSQL+="       INNER JOIN  \n";
                        strSQL+="       ( \n";
                        strSQL+=" 	   SELECT co_emp, co_itm, co_itmMae FROM tbm_equInv  \n";
                        strSQL+="          WHERE co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + "  \n";
                        strSQL+="       ) as x2 ON (x2.co_emp=x.co_emp AND x2.co_itm=x.co_itm)  \n";      
                        strSQL+="     ) AS y  \n";
                        strSQL+="     ORDER BY y.co_itmMae, y.ne_numDoc, y.co_tipdoc, y.co_doc   \n";   
                        strSQL+="  ) AS b1  \n";
                        strSQL+="  WHERE (b1.nd_canPenConBod>0 or b1.nd_canPenConDes>0) \n";
                        rst=stm.executeQuery(strSQL);
                        while (rst.next())
                        {
                            bdeCanPndEgrBod = new BigDecimal(rst.getObject("nd_canPenConBod")==null?"0":(rst.getString("nd_canPenConBod").equals("")?"0":rst.getString("nd_canPenConBod"))); 
                            bdeCanPndEgrDes = new BigDecimal(rst.getObject("nd_canPenConDes")==null?"0":(rst.getString("nd_canPenConDes").equals("")?"0":rst.getString("nd_canPenConDes"))); 
                        }
                        rst.close();
                        rst=null;

                        //Obtener cantidades por Ingresar
                        strSQL ="";
                        strSQL+=" SELECT SUM( b1.nd_canPenConBod) as nd_canPenConBod, SUM(b1.nd_canPenConDes) as nd_canPenConDes \n";
                        strSQL+="  FROM (  \n";
                        strSQL+="    SELECT y.*, CASE WHEN y.tx_tipMov='R' THEN y.nd_canPenBod ELSE 0 END as nd_canPenConBod  \n";
                        strSQL+="              , CASE WHEN y.tx_tipMov='R' THEN 0 ELSE y.nd_canPenBod END as nd_canPenConDes  \n";
                        strSQL+="    FROM (  \n";
                        strSQL+="      SELECT x.* , x1.co_bodGrp, x2.co_itmMae FROM  (  \n";
                        strSQL+="             SELECT a.tx_tipMov, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.co_cli, a.tx_nomCli, a.st_conInv, \n";
                        strSQL+="                    a1.co_Reg, a1.co_bod, a1.co_itm, a1.nd_can as nd_canMov,  \n";
                        strSQL+="                    CASE WHEN a1.nd_canIngBod IS NULL THEN 0 ELSE a1.nd_canIngBod END as nd_canPenBod  \n";
                        strSQL+="             FROM tbm_cabMovInv as a  \n";
                        strSQL+="             INNER JOIN tbm_detMovInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc) \n";  
                        strSQL+="             WHERE a.st_reg IN('A') AND a1.nd_Can>0 AND a.tx_tipMov IS NOT NULL AND a1.st_merIngEgrFisBod IN('S') \n";
                        strSQL+="             AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END  ) NOT IN ('F') \n";
                        strSQL+="             GROUP BY a.tx_tipMov, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.co_cli, a.tx_nomCli, a.st_conInv,  \n";                    
                        strSQL+="                      a1.co_Reg, a1.co_bod, a1.co_itm, a1.nd_can , a1.nd_canIngBod \n";
                        strSQL+="             ORDER BY a.co_emp, a.co_loc, a.co_tipdoc, a.ne_numDoc, a.co_doc, a1.co_Reg \n";
                        strSQL+="       ) as x   \n";     
                        strSQL+="       INNER JOIN  \n";
                        strSQL+="       ( \n";
                        strSQL+="          SELECT co_emp, co_bod, co_bodGrp FROM tbr_bodEmpBodGrp  \n";
                        strSQL+="          WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "  AND co_bodGrp=" + txtCodBod.getText() + " \n";
                        strSQL+="       ) as x1 ON (x1.co_emp=x.co_emp AND x1.co_bod=x.co_bod) \n"; 
                        strSQL+="       INNER JOIN  \n";
                        strSQL+="       ( \n";
                        strSQL+=" 	   SELECT co_emp, co_itm, co_itmMae FROM tbm_equInv  \n";
                        strSQL+="          WHERE co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + "  \n";
                        strSQL+="       ) as x2 ON (x2.co_emp=x.co_emp AND x2.co_itm=x.co_itm)    \n"; 
                        strSQL+="     ) AS y  \n";
                        strSQL+="     ORDER BY y.co_itmMae, y.ne_numDoc, y.co_tipdoc, y.co_doc   \n";   
                        strSQL+="  ) AS b1  \n";
                        strSQL+="  WHERE (b1.nd_canPenConBod>0 or b1.nd_canPenConDes>0)   \n";
                        rst=stm.executeQuery(strSQL);
                        while (rst.next())
                        {
                            bdeCanPndIngBod = new BigDecimal(rst.getObject("nd_canPenConBod")==null?"0":(rst.getString("nd_canPenConBod").equals("")?"0":rst.getString("nd_canPenConBod"))); 
                            bdeCanPndIngDes = new BigDecimal(rst.getObject("nd_canPenConDes")==null?"0":(rst.getString("nd_canPenConDes").equals("")?"0":rst.getString("nd_canPenConDes"))); 
                        }
                        rst.close();
                        rst=null;
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="/* Obtener el máximo de los registros */">
                        //Obtener el maximo registro de tbm_conInv
                        strSQL ="";
                        strSQL+=" SELECT (MAX(a1.co_reg)+1) AS co_regConInv";
                        strSQL+=" FROM tbm_conInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        rst=stm.executeQuery(strSQL);
                        if (rst.next())
                            intUltReg=rst.getInt("co_regConInv");
                        //</editor-fold>

                        //Cantidades de Contadas
                        bdeCanBueEst=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_BUE_EST_ING_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_BUE_EST_ING_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_BUE_EST_ING_USR).toString()));
                        bdeCanMalEst=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MAL_EST_ING_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MAL_EST_ING_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MAL_EST_ING_USR).toString()));

                        //<editor-fold defaultstate="collapsed" desc="/* Conteo de Inventario (tbm_ConInv) */">                                           
                        if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) //Conteo de items(con orden)
                        {
                            strSQL ="";
                            strSQL+=" UPDATE tbm_conInv";
                            strSQL+=" SET fe_reacon='" + strFecUltMod + "'";
                            strSQL+=" 	, nd_stksis=" + bdeStkAct;
                            strSQL+=" 	, nd_canIngBod=" + bdeCanPndIngBod;
                            strSQL+=" 	, nd_canEgrBod=" + bdeCanPndEgrBod;
                            strSQL+=" 	, nd_canTra=" + bdeCanPndIngDes;
                            strSQL+=" 	, nd_canDesEntCli=" + bdeCanPndEgrDes;
                            strSQL+=" 	, nd_cansob=" + bdeCanSob;             //nd_cansob
                            strSQL+=" 	, nd_canfalbodaso=" + bdeCanFalBodAso; //nd_canfalbodaso
                            strSQL+=" 	, nd_stkcon=" + bdeCanBueEst;
                            strSQL+=" 	, nd_canmalest=" + bdeCanMalEst;
                            strSQL+=" 	, st_conrea='S'";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND co_bod=" + txtCodBod.getText() + "";
                            strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + ";";
                        }
                        else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) //Conteo de items(sin orden)
                        {
                            strSQL ="";
                            strSQL+=" INSERT INTO tbm_conInv(";
                            strSQL+="      co_emp, co_reg, co_usrrescon, co_bod, co_itm, co_locrel, co_tipdocrel ";
                            strSQL+="    , co_docrel, co_regrel, fe_solcon, fe_reacon, nd_stksis, nd_caningbod, nd_canegrbod ";
                            strSQL+="    , nd_canTra, nd_CanDesEntCli, nd_stkcon, st_conrea, tx_obs1, st_regrep, nd_cansob ";
                            strSQL+="    , nd_canmalest, nd_canfalbodaso)";
                            strSQL+=" VALUES (";
                            strSQL+="  " + objParSis.getCodigoEmpresa() + "";   //co_emp
                            strSQL+=" ," + intUltReg + "";                      //co_reg
                            strSQL+=" ," + objParSis.getCodigoUsuario() + "";   //co_usrrescon
                            strSQL+=" ," + txtCodBod.getText() + "";            //co_bod
                            strSQL+=" ," + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";//co_itm
                            strSQL+=" , null";                                  //co_locrel
                            strSQL+=" , null";                                  //co_tipdocrel
                            strSQL+=" , null";                                  //co_docrel
                            strSQL+=" , null";                                  //co_regrel
                            strSQL+=" , null";                                  //fe_solcon
                            strSQL+=" ,'" + strFecUltMod + "'";                 //fe_reacon
                            strSQL+=" ," + bdeStkAct + "";                      //nd_stksis
                            strSQL+=" ," + bdeCanPndIngBod + "";                //nd_caningbod
                            strSQL+=" ," + bdeCanPndEgrBod + "";                //nd_canegrbod
                            strSQL+=" ," + bdeCanPndIngDes + "";                //nd_canTra
                            strSQL+=" ," + bdeCanPndEgrDes + "";                //nd_canDesEntCli
                            strSQL+=" ," + bdeCanBueEst + "";                   //nd_stkcon
                            strSQL+=" , 'S'";                                   //st_conrea
                            strSQL+=" , null";                                  //tx_obs1
                            strSQL+=" , 'I'";                                   //st_regrep
                            strSQL+=" , " + bdeCanSob + "";                     //nd_cansob
                            strSQL+=" , " + bdeCanMalEst + "";                  //nd_canmalest
                            strSQL+=" , " + bdeCanFalBodAso + "";               //nd_canfalbodaso
                            strSQL+=");";                            
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="/* Documentos Pendientes de Ingresar Y Egresar */">
                        if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) || (objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD) ) //importaciones no guarda cantidades pendientes de confirmar de egreso ni ingreso
                        {
                            //tbm_coninvdocegrbod [Egresos]
                            strSQL+="INSERT INTO tbm_coninvdocegrbod(co_emp, co_con, co_emprel, co_locrel, co_tipdocrel, co_docrel,";
                            strSQL+="                                nd_canPenConBod, nd_canPenConDes, st_regrep)";
                            strSQL+="\n(";
                            strSQL+=" SELECT " + objParSis.getCodigoEmpresa() + ", ";
                            if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD) )//Conteo de items(con orden) 
                               strSQL+=" " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + "";
                            else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD)//Conteo de items(sin orden)
                                strSQL+=" " + intUltReg + "";
                            strSQL+=" , b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.nd_canPenConBod, b1.nd_canPenConDes, 'I'  \n";
                            strSQL+="  FROM (  \n";
                            strSQL+="    SELECT y.*, CASE WHEN y.co_bodGrp in (15,3) THEN y.nd_canPenBod ELSE 0 END as nd_canPenConBod  \n";
                            strSQL+="              , CASE WHEN y.co_bodGrp in (15,3) THEN y.nd_canPenDes ELSE y.nd_canPenBod END as nd_canPenConDes   \n";
                            strSQL+="    FROM (  \n";
                            strSQL+="      SELECT x.* , x1.co_bodGrp, x2.co_itmMae FROM  (  \n";
                            strSQL+=" 		 SELECT a.co_emp, a.co_loc, a.co_tiPDoc, a.co_doc, a2.co_emp as CodEmpMov, a2.co_loc as CodLocMov, a2.co_tipDoc as CodTipDocMov, a2.co_doc as CodDocMov,  \n";
                            strSQL+="                   a2.ne_numDoc, a2.st_conInv, a2.tx_tipMov,  a.ne_numOrdDes, a2.co_reg, a2.co_itm, a2.co_bod, \n";
                            strSQL+=" 			a2.st_merIngegrfisbod, a2.tx_codAlt, a2.tx_codAlt2, abs(a2.nd_can) as nd_canMov,  \n";                        
                            strSQL+="                   CASE WHEN a2.nd_canEgrBod IS NULL THEN 0 ELSE (a2.nd_canEgrBod*-1) END as nd_canPenBod , \n";
                            strSQL+="                   CASE WHEN a2.nd_CanDesEntCli IS NULL THEN 0 ELSE (a2.nd_CanDesEntCli*-1) END as nd_canPenDes \n";
                            strSQL+=" 		 FROM tbm_CabGuiRem as a  \n";
                            strSQL+=" 		 INNER JOIN tbm_detGuiRem as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tiPDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc) \n";
                            strSQL+="            INNER JOIN \n";
                            strSQL+="            ( \n";
                            strSQL+=" 		    SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.ne_numDoc, a1.co_reg, a1.co_itm, a1.co_bod, a.st_conInv, a.tx_tipMov,  \n";
                            strSQL+=" 			   a1.st_merIngegrfisbod, a1.tx_codAlt, a1.tx_codAlt2, a1.nd_can, a1.nd_canEgrBod, a1.nd_CanDesEntCli , a.st_reg, a.st_conInv as st_conInvMov \n";
                            strSQL+=" 		    FROM tbm_cabMovInv as a \n";
                            strSQL+=" 	            INNER JOIN tbm_detMovInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tiPDoc=a.co_tipDoc AND a1.co_doc=a.co_doc) \n";
                            strSQL+="            ) as a2 ON (a2.co_Emp=a1.co_empRel AND a2.co_loc=a1.co_locRel AND a2.co_TipDoc=a1.co_tipDocRel AND a2.co_doc=a1.co_docRel AND a2.co_reg=a1.co_RegRel)  \n";
                            strSQL+=" 		 WHERE a2.nd_Can<0 AND a2.st_reg IN('A') AND a2.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod IN('S')  \n"; 
                            strSQL+=" 		 AND ( CASE WHEN a2.st_conInvMov IS NULL THEN 'P' ELSE a2.st_conInvMov END  ) NOT IN ('F') AND a.st_reg IN('A')  \n";	
                            strSQL+=" 		 GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.co_itm, a2.co_bod, a2.ne_numDoc, a2.st_conInv, a2.tx_tipMov,  \n";
                            strSQL+="                     a.co_emp, a.co_loc, a.co_tiPDoc, a.co_doc, a.ne_numOrdDes, \n"; 
                            strSQL+=" 			  a2.st_merIngegrfisbod, a2.tx_codAlt, a2.tx_codAlt2, a2.nd_can, a2.nd_canEgrBod, a2.nd_CanDesEntCli \n";
                            strSQL+=" 		 ORDER BY a.ne_numOrdDes, a.co_emp, a.co_loc, a.co_tiPDoc, a.co_doc \n";
                            strSQL+="       ) as x    \n";    
                            strSQL+="       INNER JOIN  \n";
                            strSQL+="       ( \n";
                            strSQL+="          SELECT co_emp, co_bod, co_bodGrp FROM tbr_bodEmpBodGrp  \n";
                            strSQL+="        WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + " AND co_bodGrp=" + txtCodBod.getText() + " \n";
                            strSQL+="       ) as x1 ON (x1.co_emp=x.co_emp AND x1.co_bod=x.co_bod)  \n";
                            strSQL+="       INNER JOIN  \n";
                            strSQL+="       ( \n";
                            strSQL+=" 	       SELECT co_emp, co_itm, co_itmMae FROM tbm_equInv  \n";
                            strSQL+="          WHERE co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + " \n";
                            strSQL+="       ) as x2 ON (x2.co_emp=x.co_emp AND x2.co_itm=x.co_itm) \n";     
                            strSQL+="     ) AS y  \n";  
                            strSQL+="     ORDER BY y.co_itmMae, y.ne_numDoc, y.co_tipdoc, y.co_doc   \n";   
                            strSQL+=" ) AS b1  \n";
                            strSQL+="  WHERE (b1.nd_canPenConBod>0 or b1.nd_canPenConDes>0)   \n";
                            strSQL+="  ORDER BY b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.nd_canPenConBod, b1.nd_canPenConDes  \n";
                            strSQL+="); ";

                            //tbm_coninvdocingbod  [Ingresos]
                            strSQL+="INSERT INTO tbm_coninvdocingbod(";
                            strSQL+="             co_emp, co_con, co_emprel, co_locrel, co_tipdocrel, co_docrel,";
                            strSQL+="             nd_canPenConBod, nd_canPenConDes, st_regrep)";
                            strSQL+="\n(";
                            strSQL+=" SELECT " + objParSis.getCodigoEmpresa() + ", ";
                            if( (objParSis.getCodigoMenu()==INT_COD_MNU_CON_CONORD)  )//Conteo de items(con orden) 
                               strSQL+="        " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + " ";
                            else if(objParSis.getCodigoMenu()==INT_COD_MNU_CON_SINORD)//Conteo de items(sin orden)
                                strSQL+=" " + intUltReg + "";                             
                            strSQL+="  , b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.nd_canPenConBod, b1.nd_canPenConDes, 'I'  \n";
                            strSQL+="  FROM (  \n";
                            strSQL+="    SELECT y.*, CASE WHEN y.tx_tipMov='R' THEN y.nd_canPenBod ELSE 0 END as nd_canPenConBod  \n";
                            strSQL+="              , CASE WHEN y.tx_tipMov='R' THEN 0 ELSE y.nd_canPenBod END as nd_canPenConDes  \n";
                            strSQL+="    FROM (  \n";
                            strSQL+="      SELECT x.* , x1.co_bodGrp, x2.co_itmMae FROM  (  \n";

                            //inicio - nuevo
                            strSQL+="           SELECT a1.tx_tipMov, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc, a1.co_cli, a1.tx_nomCli, a1.st_conInv, ";
                            strSQL+="                  a1.co_bod, a1.co_itm, SUM(a1.nd_canMov) AS nd_canMov,  SUM(a1.nd_canPenBod) AS nd_canPenBod";
                            strSQL+="           FROM(";  
                            //fin-nuevo
                            strSQL+="             SELECT a.tx_tipMov, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.co_cli, a.tx_nomCli, a.st_conInv, \n";
                            strSQL+="                    a1.co_Reg, a1.co_bod, a1.co_itm, a1.nd_can as nd_canMov,  \n";
                            strSQL+="                    CASE WHEN a1.nd_canIngBod IS NULL THEN 0 ELSE a1.nd_canIngBod END as nd_canPenBod  \n";
                            strSQL+="             FROM tbm_cabMovInv as a  \n";
                            strSQL+="             INNER JOIN tbm_detMovInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc)  \n";
                            strSQL+="             WHERE a.st_reg IN('A') AND a1.nd_Can>0 AND a.tx_tipMov IS NOT NULL AND a1.st_merIngEgrFisBod IN('S')  \n"; 
                            strSQL+="             AND ( CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END  ) NOT IN ('F') \n";
                            strSQL+="             GROUP BY a.tx_tipMov, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.co_cli, a.tx_nomCli, a.st_conInv,  \n";                    
                            strSQL+="                      a1.co_Reg, a1.co_bod, a1.co_itm, a1.nd_can , a1.nd_canIngBod \n";
                            strSQL+="             ORDER BY a.co_emp, a.co_loc, a.co_tipdoc, a.ne_numDoc, a.co_doc, a1.co_Reg \n";
                            //inicio - nuevo
                            strSQL+="           ) AS a1";
                            strSQL+="           GROUP BY a1.tx_tipMov, a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc, a1.co_cli ";
                            strSQL+="                  , a1.tx_nomCli, a1.st_conInv, a1.co_bod, a1.co_itm";
                            //fin-nuevo

                            strSQL+="       ) as x   \n";     
                            strSQL+="       INNER JOIN  \n";
                            strSQL+="       ( \n"; 
                            strSQL+="          SELECT co_emp, co_bod, co_bodGrp FROM tbr_bodEmpBodGrp \n";  
                            strSQL+="          WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + " AND co_bodGrp=" + txtCodBod.getText() + " \n"; 
                            strSQL+="       ) as x1 ON (x1.co_emp=x.co_emp AND x1.co_bod=x.co_bod)  \n"; 
                            strSQL+="       INNER JOIN  \n"; 
                            strSQL+="       ( \n"; 
                            strSQL+=" 	       SELECT co_emp, co_itm, co_itmMae FROM tbm_equInv  \n"; 
                            strSQL+="          WHERE co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + " \n";
                            strSQL+="       ) as x2 ON (x2.co_emp=x.co_emp AND x2.co_itm=x.co_itm)  \n";    
                            strSQL+="     ) AS y    \n"; 
                            strSQL+="     ORDER BY y.co_itmMae, y.ne_numDoc, y.co_tipdoc, y.co_doc   \n";    
                            strSQL+="  ) AS b1  \n"; 
                            strSQL+="  WHERE (b1.nd_canPenConBod>0 or b1.nd_canPenConDes>0)   \n"; 
                            strSQL+="  ORDER BY b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.nd_canPenConBod, b1.nd_canPenConDes  \n"; 
                            strSQL+="); ";
                        }
                        //</editor-fold>

                        strSQLUpd+=strSQL;
                    }
                }
                System.out.println("okkkkk: "+strSQLUpd);
                stm.executeUpdate(strSQLUpd);
                stm.close();
                stm=null;
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_conInv", "tbh_conInv", "WHERE a1.fe_reaCon='" + strFecUltMod + "' AND a1.co_usrResCon=" + objParSis.getCodigoUsuario());
                System.out.println("Historico:  ");
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
     * Función que permite guardar la información de los conteos realizados en el area de importaciones.
     * @return true si se pudo realizar la operación
     * <BR> false caso contrario
     */
    private boolean actualizaConteoImportaciones()
    {
        boolean blnRes=true;
        BigDecimal bdeStkAct=new BigDecimal("0");
        BigDecimal bdeCanBueEst=new BigDecimal("0");
        BigDecimal bdeCanMalEst=new BigDecimal("0");
        int intUltReg=-1, intUltReg_DetOrdConInv=-1;
        String strSQLUpd="";
        String strFecUltMod;
        try
        {
            if(con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                
                for(int i=0; i<objTblMod.getRowCount(); i++)
                {
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_SEL_EST))
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* Datos y cantidades del ítem (tbm_invBod) */">
                        //Datos del ítem
                        strSQL ="";
                        strSQL+=" SELECT b1.nd_stkAct, b1.co_itmMae";
                        //Stock
                        strSQL+=" FROM( ";
                        strSQL+="     SELECT SUM(z.nd_stkAct) AS nd_stkAct, z.co_itmMae ";
                        strSQL+="     FROM( ";
                        strSQL+="  	SELECT x.* FROM( ";
                        strSQL+="  		SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae";
                        strSQL+="  		FROM tbm_invBod AS a1 ";
                        strSQL+="  		INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+="  		WHERE a2.co_itmMae=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) + "";
                        strSQL+="  	) AS x ";
                        strSQL+="  	INNER JOIN( ";
                        strSQL+="  		SELECT co_emp, co_bod";
                        strSQL+="  		FROM tbr_bodEmpBodGrp";
                        strSQL+="  		WHERE co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                        strSQL+="               AND co_bodGrp=" + txtCodBod.getText() + "";
                        strSQL+="  		ORDER BY co_emp";
                        strSQL+="  	) AS y ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                        strSQL+="     ) AS z";
                        strSQL+="     GROUP BY z.co_itmMae";
                        strSQL+="  ) AS b1";
                        rst=stm.executeQuery(strSQL);
                        while(rst.next())
                        {
                             bdeStkAct=new BigDecimal(rst.getObject("nd_stkAct")==null?"0":(rst.getString("nd_stkAct").equals("")?"0":rst.getString("nd_stkAct")));
                        }
                        rst.close();
                        rst=null;
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Obtener el máximo de los registros */">
                        //Obtener el maximo registro de tbm_conInv
                        strSQL ="";
                        strSQL+=" SELECT (MAX(a1.co_reg)+1) AS co_regConInv";
                        strSQL+=" FROM tbm_conInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        rst=stm.executeQuery(strSQL);
                        if (rst.next()){
                            intUltReg=rst.getInt("co_regConInv");
                        }
                        
                        //Obtener el maximo registro de tbm_detOrdConInv
                        strSQL ="";
                        strSQL+=" SELECT (MAX(a1.co_reg)+1) AS co_regOrdConInv";
                        strSQL+=" FROM tbm_detOrdConInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + strCodEmpOrdCon;
                        strSQL+=" AND a1.co_loc=" + strCodLocOrdCon;
                        strSQL+=" AND a1.co_tipDoc=" + strCodTipDocOrdCon;
                        strSQL+=" AND a1.co_doc=" + strCodDocOrdCon;
                        rst=stm.executeQuery(strSQL);
                        if (rst.next()){
                            intUltReg_DetOrdConInv=rst.getInt("co_regOrdConInv");
                        }
                        rst.close();
                        rst=null; 
                        //</editor-fold>
                          
                        //Cantidades Contadas.
                        bdeCanBueEst=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_BUE_EST_ING_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_BUE_EST_ING_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_BUE_EST_ING_USR).toString()));
                        //bdeCanMalEst=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MAL_EST_ING_USR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MAL_EST_ING_USR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_MAL_EST_ING_USR).toString()));
                   
                        strSQL ="";  
                        //<editor-fold defaultstate="collapsed" desc="/* Conteo de Inventario (tbm_ConInv, tbm_detOrdConInv) */">                                           
                        //Si el item TIENE CONTEO (UPDATE tbm_conInv)
                        if(!objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("0"))
                        {
                            strSQL+=" UPDATE tbm_conInv";
                            strSQL+=" SET nd_stkcon=(CASE WHEN nd_stkcon IS NULL THEN 0 ELSE nd_stkcon END) + " + bdeCanBueEst + "";
                            strSQL+="   , nd_canMalEst=(CASE WHEN nd_canMalEst IS NULL THEN 0 ELSE nd_canMalEst END) + " + bdeCanMalEst + "";
                            strSQL+="   , st_conrea='S'";
                            strSQL+="   , nd_stksis=" + bdeStkAct + "";
                            strSQL+="   , co_usrResCon=" + objParSis.getCodigoUsuario() + "";
                            strSQL+="   , fe_reacon='" + strFecUltMod + "'";
                            strSQL+=" WHERE co_emp=" + strCodEmpOrdCon + "";
                            strSQL+=" AND co_locRel=" + strCodLocOrdCon + "";
                            strSQL+=" AND co_tipDocRel=" + strCodTipDocOrdCon + "";
                            strSQL+=" AND co_docRel=" + strCodDocOrdCon + "";
                            strSQL+=" AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";
                            strSQL+=" AND EXISTS(";
                            strSQL+="     SELECT a4.* FROM tbm_cabOrdConInv AS a1";
                            strSQL+="     INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                            strSQL+="     INNER JOIN tbm_conInv AS a4 ON(a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)";
                            strSQL+="     WHERE a1.st_reg='A' ";
                            strSQL+="     AND a1.co_empRel=" + strCodEmpIngImp + "";
                            strSQL+="     AND a1.co_locRel=" + strCodLocIngImp + "";
                            strSQL+="     AND a1.co_tipDocRel=" + strCodTipDocIngImp + "";
                            strSQL+="     AND a1.co_docRel=" + strCodDocIngImp + "";
                            strSQL+="     AND a4.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";
                            strSQL+=" )";
                            strSQL+=";";                         
                        }
                        
                        //Si el item NO TIENE CONTEO (INSERT tbm_detOrdConInv)
                        strSQL+=" INSERT INTO tbm_detordconinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm)";
                        strSQL+=" (";
                        strSQL+="     SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                        strSQL+="          , " + intUltReg_DetOrdConInv + " AS co_reg";
                        if(!objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("0"))
                            strSQL+="          , " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + " AS co_itm";
                        else
                            strSQL+="          , CAST(Null AS INTEGER)  AS co_itm";
                        strSQL+="     FROM tbm_cabOrdConInv AS a1";
                        strSQL+="     INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                        strSQL+="     WHERE a1.co_empRel=" + strCodEmpIngImp + "";
                        strSQL+="     AND a1.co_locRel=" + strCodLocIngImp + "";
                        strSQL+="     AND a1.co_tipDocRel=" + strCodTipDocIngImp + "";
                        strSQL+="     AND a1.co_docRel=" + strCodDocIngImp + "";
                        strSQL+="     AND a1.st_reg='A'";
                        strSQL+="     AND NOT EXISTS(";
                        strSQL+="	  SELECT a3.* FROM tbm_cabOrdConInv AS a1";
                        strSQL+="	  INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                        strSQL+="	  WHERE a1.st_reg='A'";
                        strSQL+="	  AND a1.co_empRel=" + strCodEmpIngImp + "";
                        strSQL+="	  AND a1.co_locRel=" + strCodLocIngImp +"";
                        strSQL+="	  AND a1.co_tipDocRel=" + strCodTipDocIngImp + "";
                        strSQL+="	  AND a1.co_docRel=" + strCodDocIngImp + "";
                        strSQL+="	  AND a3.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";
                        strSQL+="     )";
                        strSQL+="     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                        strSQL+=" )";
                        strSQL+=";";
                        
                        //Si el item NO TIENE CONTEO (INSERT tbm_conInv)
                        //Si el item no ha sido contado, no es necesario adicionar el valor en cantidad contada y mal estado. Se crea el conteo con la cantidad contada en el momento.
                        strSQL+=" INSERT INTO tbm_conInv(";
                        strSQL+="       co_emp, co_reg, co_usrrescon, co_bod, co_itm, co_locrel, co_tipdocrel,co_docrel, co_regrel,";
                        strSQL+="       fe_solcon, fe_reacon, nd_stksis, nd_stkcon, nd_canMalEst, st_conrea, st_regrep, tx_obs1 ";
                        strSQL+=" )";
                        strSQL+=" (";
                        if(!objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("0"))
                            strSQL+="     SELECT a1.co_emp";
                        else
                            strSQL+="     SELECT DISTINCT a1.co_emp";
                        strSQL+="            , " + intUltReg + " AS co_reg";
                        strSQL+="            , " + objParSis.getCodigoUsuario() + " AS co_usrrescon";
                        strSQL+="            , " + txtCodBod.getText() + " AS co_bod";
                        if(!objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("0"))
                            strSQL+="            , " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + " AS co_itm"; 
                        else
                            strSQL+="            , CAST(Null AS INTEGER) AS co_itm"; 
                        strSQL+="            , a1.co_loc, a1.co_tipDoc, a1.co_doc"; //datos de la orden de conteo
                        strSQL+="            , " + intUltReg_DetOrdConInv + " AS co_regRel";
                        strSQL+="            , CAST(Null AS DATE) AS fe_solcon";
                        strSQL+="            , '" + strFecUltMod + "' AS fe_reacon";
                        strSQL+="            , " + bdeStkAct + " AS nd_stksis";
                        strSQL+="            , " + bdeCanBueEst + " AS nd_stkcon";
                        strSQL+="            , " + bdeCanMalEst + " AS nd_canmalest";
                        strSQL+="            , 'S' AS st_conrea,'I' AS st_regrep";
                        if(!objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("0"))
                            strSQL+="            , Null AS tx_obs1 ";
                        else
                            strSQL+="            , "+objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET))+" AS tx_obs1 ";
                        strSQL+="     FROM tbm_cabOrdConInv AS a1";
                        strSQL+="     INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                        strSQL+="     WHERE a1.co_empRel=" + strCodEmpIngImp + "";
                        strSQL+="     AND a1.co_locRel=" + strCodLocIngImp + "";
                        strSQL+="     AND a1.co_tipDocRel=" + strCodTipDocIngImp + "";
                        strSQL+="     AND a1.co_docRel=" + strCodDocIngImp + "";
                        strSQL+="     AND a1.st_reg='A'";
                        if(!objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("0"))
                            strSQL+="     AND a3.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";
                        strSQL+="     AND NOT EXISTS(";
                        strSQL+="	  SELECT a4.* FROM tbm_cabOrdConInv AS a1";
                        strSQL+="	  INNER JOIN tbm_cabMovInv AS a2 ON(a1.co_empRel=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_doc)";
                        strSQL+="	  INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                        strSQL+="	  INNER JOIN tbm_conInv AS a4 ON(a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)";
                        strSQL+="	  WHERE a1.st_reg='A' ";
                        strSQL+="	  AND a1.co_emp=" + strCodEmpOrdCon + "";
                        strSQL+="	  AND a1.co_loc=" + strCodLocOrdCon + "";
                        strSQL+="	  AND a1.co_tipDoc=" + strCodTipDocOrdCon + "";
                        strSQL+="	  AND a1.co_doc=" + strCodDocOrdCon + "";
                        strSQL+="	  AND a4.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";
                        strSQL+="     )";
                        strSQL+=" )";
                        strSQL+=";";
                        //</editor-fold>
   
                        strSQLUpd+=strSQL;
                    }
                }
                System.out.println("Importaciones conteo OK: "+strSQLUpd);
                stm.executeUpdate(strSQLUpd);
                stm.close();
                stm=null;
                //Inserta Histórico: Sólo se registra el histórico de los ítems existentes.
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_conInv", "tbh_conInv", "WHERE a1.fe_reaCon='" + strFecUltMod + "' AND a1.co_usrResCon=" + objParSis.getCodigoUsuario()+" AND a1.co_itm IS NOT NULL");
                System.out.println("historico de importaciones");
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
    * Función que permite actualizar tbm_cabMovInv 
    * Actualiza estado de ingreso de importación indicando que el pedido esta en proceso de conteo.
    * @return true si se pudo realizar la operación
    * <BR> false caso contrario
    */ 
   private boolean actualiza_tbmCabMovInv()
   {
       boolean blnRes=true;
       try
       {
           if(con!=null)
           {
               stm=con.createStatement();
                
               //Actualiza el pedido indicando que esta en proceso de conteo de inventario.
               strSQL =" UPDATE tbm_cabMovInv";
               strSQL+=" SET st_ingImp='B'";
               strSQL+=" WHERE co_emp=" + strCodEmpIngImp;      //co_emp
               strSQL+=" AND co_loc=" + strCodLocIngImp;        //co_loc  
               strSQL+=" AND co_tipDoc=" + strCodTipDocIngImp;  //co_tipDoc  
               strSQL+=" AND co_doc=" + strCodDocIngImp;        //co_doc  
               strSQL+=" AND (CASE WHEN st_ingImp IS NULL THEN 'N' ELSE st_ingImp END ) IN ('A')";   //Solo cuando el pedido no haya sido contado.
               strSQL+=" ; ";
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
    * Función que genera contenedor con datos de los ítems que tendran movimientos en el disponible.
    * Solo se agregara disponible de los ítems que estan en el ingreso por importación.
    * @return 
    */
    private boolean generaConItmTbmInvBod()
    {
        boolean blnRes=true;
        String strCodLetItm="";
        int intCodItmGrp=0, intCodItmMae=0, intCodBodEmp=0, intCodItmEmp=0;
        BigDecimal bgdCanConBueEstAct= new BigDecimal ("0");
        BigDecimal bgdAuxDis= new BigDecimal ("0");
        BigDecimal bgdCanTotCon= new BigDecimal ("0");
        BigDecimal bgdCanIngImp= new BigDecimal ("0");
        BigDecimal bgdCanStkEmp= new BigDecimal ("0");
        BigDecimal bgdCanDisEmp= new BigDecimal ("0");
        try
        {
            if(con!=null)
            {
                arlDatStkInvItm = new ArrayList(); 
                
                //Valida que no sean pedidos antiguos, ya que estos han incrementado el disponible cuando se realizó el ingreso por importación.
                if(validaFechaPedido(con))
                {
                    System.out.println("Esquema Nuevo");
                    for(int i=0; i<objTblMod.getRowCount(); i++)
                    {
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_SEL_EST))
                        {
                             //Valida que el item existe en el inventario.
                             if(!objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("0"))
                             {
                                 //Datos del Item
                                 intCodItmEmp = objStkInv.getCodItmEmp(Integer.parseInt(strCodEmpIngImp), Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString()));
                                 intCodBodEmp=getCodigoBodegaEmpresa( Integer.parseInt(strCodEmpIngImp), Integer.parseInt(strCodBodOrdCon) );
                                 intCodItmGrp=objStkInv.getCodItmGrp(Integer.parseInt(strCodEmpIngImp), intCodItmEmp);
                                 intCodItmMae=objStkInv.getCodMaeItm(Integer.parseInt(strCodEmpIngImp), intCodItmEmp);
                                 strCodLetItm=objStkInv.getCodLetItm(Integer.parseInt(strCodEmpIngImp), intCodItmEmp);
                                 
                                 bgdCanStkEmp = BigDecimal.valueOf(objStkInv.getStkItmEmp(con, intCodItmMae, Integer.parseInt(strCodBodOrdCon), Integer.parseInt(strCodEmpIngImp) ));
                                 bgdCanDisEmp = BigDecimal.valueOf(objStkInv.getDisItmEmp(con, intCodItmMae, Integer.parseInt(strCodBodOrdCon), Integer.parseInt(strCodEmpIngImp) ));
                                 
                                 bgdCanConBueEstAct = BigDecimal.valueOf( Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_BUE_EST_ING_USR).toString()));
                                 bgdCanTotCon=getCantidadTotalContada(con, intCodItmGrp);  
                                 bgdCanIngImp=getCantidadItemIngImp(intCodItmEmp);
                                 
                                 //Se valida que solo se pueda establecer CanDis a los ítems que aparecen en el INIMPO.
                                 if( bgdCanIngImp.compareTo(new BigDecimal("0"))> 0 )
                                 {
                                     //Formula para Disponible a actualizar
                                     bgdAuxDis=bgdCanTotCon.compareTo(bgdCanIngImp)<=0 ? bgdCanConBueEstAct:( bgdCanIngImp.subtract(bgdCanTotCon.subtract(bgdCanConBueEstAct)) ); 
                                     bgdAuxDis=bgdAuxDis.compareTo(new BigDecimal("0"))<=0 ? new BigDecimal("0"): bgdAuxDis;
                                 }
                                 else
                                 {
                                     bgdAuxDis=new BigDecimal("0");
                                 }
                                  
                                 //Si la cantidad a actualizar en el disponible es mayor a 0.
                                 if( bgdAuxDis.compareTo(new BigDecimal("0"))> 0 )
                                 {
                                    //Valida que Disponible no sea mayor que el stock. 
                                    if( bgdCanStkEmp.compareTo(bgdCanDisEmp.add(bgdAuxDis))>= 0 )
                                    {
                                        if(intCodItmEmp==0 || intCodItmGrp==0 || intCodItmMae==0 || intCodBodEmp==0 || strCodLetItm.equals(""))
                                        {
                                            //Error...Datos a enviar en el arreglo arlDatStkInvItm estan incorrectos.
                                            blnRes=false;
                                        }
                                        else
                                        {
                                            arlRegStkInvItm = new ArrayList();
                                            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP, intCodItmGrp);
                                            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP, intCodItmEmp);
                                            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE, intCodItmMae);
                                            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodLetItm);
                                            arlRegStkInvItm.add(INT_ARL_STK_INV_CAN_ITM, bgdAuxDis );
                                            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP, intCodBodEmp );
                                            arlDatStkInvItm.add(arlRegStkInvItm);
                                        }
                                    }
                                    else
                                    {
                                        //Error...Stock es menor al disponible.
                                        blnRes=false;
                                    }
                                 }
                             }
                        }
                    }
                }
                else
                    System.out.println("Pedido Antiguo");
            }
        }
        catch(Exception e)
        {
           objUti.mostrarMsgErr_F1(null, e);
           blnRes=false;
        }
        return blnRes;
    } 
   
    private int getCodigoBodegaEmpresa(int intCodEmp, int intCodBodGrp)
    {
        int intCodBodEmp=0;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null)
            {
                stmLoc=conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT co_bod as co_bodEmp FROM tbr_bodEmpBodGrp as a ";
                strSQL+=" WHERE a.co_empGrp ="+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a.co_bodGrp ="+intCodBodGrp;
                strSQL+=" AND a.co_Emp ="+intCodEmp;
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                {
                    intCodBodEmp=rstLoc.getInt("co_bodEmp");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            intCodBodEmp=0;
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            intCodBodEmp=0;
        }
        return intCodBodEmp;
    }
        
    private BigDecimal getCantidadTotalContada(java.sql.Connection conn, int intCodItmGrp)
    {
       BigDecimal bgdCanTotCon= new BigDecimal ("0");
       java.sql.Statement stmLoc;
       java.sql.ResultSet rstLoc;
       try
       {
           if (conn != null)
           {
                stmLoc=conn.createStatement();
                strSQL ="";
                strSQL+=" SELECT nd_stkcon as nd_canTotCon FROM tbm_conInv ";
                strSQL+=" WHERE co_emp=" + strCodEmpOrdCon + "";
                strSQL+=" AND co_locRel=" + strCodLocOrdCon + "";
                strSQL+=" AND co_tipDocRel=" + strCodTipDocOrdCon + "";
                strSQL+=" AND co_docRel=" + strCodDocOrdCon + "";
                strSQL+=" AND co_itm=" + intCodItmGrp + "";  //Codigo Item Empresa donde se guarda la Orden de Conteo, es decir Codigo de Grupo.
                strSQL+=" AND EXISTS(";
                strSQL+="     SELECT a4.* FROM tbm_cabOrdConInv AS a1";
                strSQL+="     INNER JOIN tbm_detOrdConInv AS a3 ON(a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                strSQL+="     INNER JOIN tbm_conInv AS a4 ON(a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel AND a3.co_reg=a4.co_regRel)";
                strSQL+="     WHERE a1.st_reg='A'";
                strSQL+="     AND a1.co_empRel=" + strCodEmpIngImp + "";
                strSQL+="     AND a1.co_locRel=" + strCodLocIngImp + "";
                strSQL+="     AND a1.co_tipDocRel=" + strCodTipDocIngImp + "";
                strSQL+="     AND a1.co_docRel=" + strCodDocIngImp + "";
                strSQL+="     AND a4.co_itm=" + intCodItmGrp + "";  //Codigo Item Grupo
                strSQL+=" )";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                {
                    bgdCanTotCon=rstLoc.getBigDecimal("nd_canTotCon");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
           }
       }
       catch (java.sql.SQLException e)
       {
            objUti.mostrarMsgErr_F1(this, e);
            bgdCanTotCon=new BigDecimal ("0");
       }
       catch(Exception e)
       {
           objUti.mostrarMsgErr_F1(null, e);
           bgdCanTotCon=new BigDecimal ("0");
       }
       return bgdCanTotCon;
    }
       
 
    
    private BigDecimal getCantidadItemIngImp(int intCodItmEmpIngImp)
    {
       BigDecimal bgdCanIngImp= new BigDecimal ("0");
       java.sql.Connection conLoc;
       java.sql.Statement stmLoc;
       java.sql.ResultSet rstLoc;
       try
       {
           conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if (conLoc != null)
           {
                stmLoc=conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT nd_can as nd_canIngImp FROM tbm_detMovInv ";
                strSQL+=" WHERE co_emp=" + strCodEmpIngImp + "";
                strSQL+=" AND co_loc=" + strCodLocIngImp + "";
                strSQL+=" AND co_tipDoc=" + strCodTipDocIngImp + "";
                strSQL+=" AND co_doc=" + strCodDocIngImp + "";
                strSQL+=" AND co_itm=" + intCodItmEmpIngImp + "";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                {
                    bgdCanIngImp=rstLoc.getBigDecimal("nd_canIngImp");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
           }
           conLoc.close();
           conLoc=null;
       }
       catch (java.sql.SQLException e)
       {
            objUti.mostrarMsgErr_F1(this, e);
            bgdCanIngImp = new BigDecimal ("0");
       }
       catch(Exception e)
       {
           objUti.mostrarMsgErr_F1(null, e);
           bgdCanIngImp = new BigDecimal ("0");
       }
       return bgdCanIngImp;
    }

    
    
    
   
}
