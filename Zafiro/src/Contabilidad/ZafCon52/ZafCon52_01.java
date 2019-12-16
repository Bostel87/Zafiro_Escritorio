/*
 * Ventana para Consultas de Listado de Documentos Recibidos...
 * Version 0.1 creado el 19 de Mayo del 2008
 * Created on 16 de Mayo del 2008, 15:35 PM
 * 
 * Creado por: Dario Cardenas
 */
package Contabilidad.ZafCon52;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;

/**
 *
 * @author  Darío Cárdenas
 */
public class ZafCon52_01 extends javax.swing.JDialog
{  
    //Constantes: Columnas del JTable:
    final int INT_TBL_LINEA        =0;            //LINEA DE NUMEROS DE REGISTROS EN LA TABLA 0   
    final int INT_TBL_COD_EMP      =1;            //Codigo de LOCAL///1
    final int INT_TBL_COD_LOC      =2;            //Codigo de LOCAL///2
    final int INT_TBL_COD_TIP_DOC  =3;            //CODIGO DEL TIPO DE DOCUMENTO///3
    final int INT_TBL_NOM_TIP_DOC  =4;            //NOMBRE DEL TIPO DE DOCUMENTO///4
    final int INT_TBL_COD_DOC      =5;            //CODIGO DEL DOCUMENTO///5
    final int INT_TBL_COD_REG      =6;            //CODIGO DE REGISTRO///6
    final int INT_TBL_NUM_DOC      =7;            //NUMERO DEL DOCUMENTO///7
    final int INT_TBL_FEC_DOC      =8;            //FECHA DE DOCUMENTO///8
    final int INT_TBL_DIA_CRE      =9;            //DIAS DE CREDITO///9
    final int INT_TBL_FEC_VEN      =10;           //FECHA DE VENCIMIENTO///10
    final int INT_TBL_VAL_DOC      =11;           //VALOR DE DOCUMENTO///11
    final int INT_TBL_VAL_PEN      =12;           //VALOR PENDIENTE DE DOCUMENTO///12
    final int INT_TBL_VAL_CHQ      =13;           //FECHA DE RECEPCION DEL CHEQUE///13
    final int INT_TBL_EST_REG      =14;           //ESTADO DE REGISTRO///14
    
    //Constantes:
    public static final int INT_BUT_CAN=0;        /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;        /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/

    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;

    javax.swing.JInternalFrame jfrThis;                //Hace referencia a this
    
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    
    private int NUMTOTFACAPL=0;                         //Código de la cuenta bancaria.
    private int intButSelDlg;                           //Botón seleccionado en el JDialog.
    private int INTCODCLI;
    
    private String STRNUMBANCHQ="", STRNUMCTACHQ="", STRNUMCHQ="";
    private String strSQL, strAux;

    
    /** Creates new form ZafCon52_01 */
    public ZafCon52_01(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);
        initComponents();        
                    
        //Inicializar objetos.
        objParSis=obj;
        
        intButSelDlg=INT_BUT_CAN;
        
        if (!configurarFrm())
            exitForm();
    }
    
    
    public ZafCon52_01(java.awt.Frame parent, boolean modal, ZafParSis obj, int intcodcli, String strcodban, String strnumctaban, String strnumchq)
    {
        super(parent, modal);
        initComponents();        
                    
        //Inicializar objetos.
        objParSis=obj;
        
        INTCODCLI = intcodcli;
        STRNUMBANCHQ = strcodban;
        STRNUMCTACHQ = strnumctaban;
        STRNUMCHQ = strnumchq;
                
        intButSelDlg=INT_BUT_CAN;
        
        if (!configurarFrm())
            exitForm();
    }
    

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux="Consulta de Documentos Recibidos";
            this.setTitle(strAux + "v0.1");
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            configurarTblDat();
        }
        catch(Exception e)
        {
            blnRes=false;
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
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecDat.clear();
            
            vecCab.add(INT_TBL_LINEA,"");//0
            vecCab.add(INT_TBL_COD_EMP,"Cod.Emp.");///1
            vecCab.add(INT_TBL_COD_LOC,"Cod.Loc.");///2
            vecCab.add(INT_TBL_COD_TIP_DOC,"Cod.Tip.Doc.");///3
            vecCab.add(INT_TBL_NOM_TIP_DOC,"Nom.Tip.Doc.");///4
            vecCab.add(INT_TBL_COD_DOC,"Cod.Doc.");///5
            vecCab.add(INT_TBL_COD_REG,"Cod.Reg.");///6
            vecCab.add(INT_TBL_NUM_DOC,"Num.Doc.");///7
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc.");///8
            vecCab.add(INT_TBL_DIA_CRE,"Dia.Cre.");///9
            vecCab.add(INT_TBL_FEC_VEN,"Fec.Ven.");///10
            vecCab.add(INT_TBL_VAL_DOC,"Val.Doc.");///11
            vecCab.add(INT_TBL_VAL_PEN,"Val.Pnd.");///12
            vecCab.add(INT_TBL_VAL_CHQ,"Val.Chq.");///13
            vecCab.add(INT_TBL_EST_REG,"Est.Reg.");///14
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LINEA);

            //Configurar JTable: Establecer el menú de contexto.
            //objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();            
            
            ///renderizador para centrar los datos numericos///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true); 
            objTblCelRenLbl.setFormatoNumerico("###,###,##0.00");            
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ///renderizador para centrar los datos de las columnas y color obligatorio///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblDat.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_VEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            tblDat.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(20);//0
            tblDat.getColumnModel().getColumn(INT_TBL_COD_EMP).setPreferredWidth(35);///2
            tblDat.getColumnModel().getColumn(INT_TBL_COD_LOC).setPreferredWidth(35);///2
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(50);///3
            tblDat.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setPreferredWidth(60);///4
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(10);///5
            tblDat.getColumnModel().getColumn(INT_TBL_COD_REG).setPreferredWidth(10);///6
            tblDat.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);///7
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_DOC).setPreferredWidth(90);///8
            tblDat.getColumnModel().getColumn(INT_TBL_DIA_CRE).setPreferredWidth(40);///9
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_VEN).setPreferredWidth(90);///10
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_DOC).setPreferredWidth(80);///11
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_PEN).setPreferredWidth(80);///12
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setPreferredWidth(80);///13
            tblDat.getColumnModel().getColumn(INT_TBL_EST_REG).setPreferredWidth(30);///14
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_EMP, tblDat);            
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_REG, tblDat);
            
            ///renderizador para alinear a la derecha los datos de las columnas y color obligatorio///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);            
            
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
//                case INT_TBL_DAT_LIN:
//                    strMsg="";
//                    break;
//                case INT_TBL_DAT_SAL_FEC:
//                    strMsg="Saldo a fecha del cheque";
//                    break;
//                case INT_TBL_DAT_VAL_CHQ:
//                    strMsg="Monto del cheque";
//                    break;
//                case INT_TBL_DAT_SAL_CON:
//                    strMsg="Saldo Contable";
//                    break;
//                case INT_TBL_DAT_FEC_VEN:
//                    strMsg="Fecha de vencimiento de chque";
//                    break;
//                case INT_TBL_DAT_FEC_DOC:
//                    strMsg="Fecha de giro del cheque";
//                    break;
//                case INT_TBL_DAT_NOM_CLI:
//                    strMsg="Nombre del Proveedor";
//                    break;
//                case INT_TBL_DAT_NUM_EGR:
//                    strMsg="Numero de Egreso";
//                    break;
//                case INT_TBL_DAT_NUM_LOC:
//                    strMsg="Numero de Local";
//                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAceptar = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitFrm(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(454, 404));

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

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAceptar.setText("Aceptar");
        butAceptar.setToolTipText("Cierra el cuadro de dialogo");
        butAceptar.setPreferredSize(new java.awt.Dimension(92, 25));
        butAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceptarActionPerformed(evt);
            }
        });
        panBot.add(butAceptar);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(683, 403));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void exitFrm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitFrm
        intButSelDlg=INT_BUT_CAN;
        dispose();
    }//GEN-LAST:event_exitFrm

    private void butAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceptarActionPerformed
        intButSelDlg=INT_BUT_ACE;
        dispose();
    }//GEN-LAST:event_butAceptarActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (tblDat.getRowCount()>0)
        {
            if (tblDat.getSelectedRow()==-1)
            {
                tblDat.setRowSelectionInterval(0,0);
            }
            tblDat.requestFocus();
        }
        cargarDetReg();
    }//GEN-LAST:event_formWindowActivated
    
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAceptar;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
   
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
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
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
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            cargarDetReg();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        int intNumTotReg=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT COUNT(*)";
                strSQL+=" FROM (";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a1.tx_nomcli, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre";
                strSQL+=", a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq";
                strSQL+=", a4.tx_descor AS a4_tx_descor, a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a2.st_reg";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli = " + INTCODCLI;
                
                if(!STRNUMBANCHQ.equals(""))
                    strSQL+=" AND a2.co_banchq = " + STRNUMBANCHQ;
                
                if(!STRNUMCTACHQ.equals(""))
                    strSQL+=" AND a2.tx_numctachq = " + STRNUMCTACHQ;
                
                strSQL+=" AND a2.tx_numchq = " + STRNUMCHQ;
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                //CxC (Todos los cobros que no correspondan a retenciones).
                strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                ///strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0 ";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=" ) AS A1";
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                NUMTOTFACAPL = intNumTotReg;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a1.tx_nomcli, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre";
                strSQL+=", a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq";
                strSQL+=", a4.tx_descor AS a4_tx_descor, a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a2.st_reg";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli= " + INTCODCLI;
                
                if(!STRNUMBANCHQ.equals(""))
                    strSQL+=" AND a2.co_banchq = " + STRNUMBANCHQ;
                
                if(!STRNUMCTACHQ.equals(""))
                    strSQL+=" AND a2.tx_numctachq = " + STRNUMCTACHQ;
                
                strSQL+=" AND a2.tx_numchq = " + STRNUMCHQ;
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                //CxC (Todos los cobros que no correspondan a retenciones).
                strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                ///strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0 ";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                rst=stm.executeQuery(strSQL);
                
                ///while(rst.next())
                for(int i=0;rst.next();i++)
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");//0
                    vecReg.add(INT_TBL_COD_EMP,rst.getString("co_emp"));//1
                    vecReg.add(INT_TBL_COD_LOC,rst.getString("co_loc"));//2
                    vecReg.add(INT_TBL_COD_TIP_DOC,rst.getString("co_tipDoc"));//3
                    vecReg.add(INT_TBL_NOM_TIP_DOC,rst.getString("tx_desCor"));//4
                    vecReg.add(INT_TBL_COD_DOC,rst.getString("co_doc"));//5
                    vecReg.add(INT_TBL_COD_REG,rst.getString("co_reg"));//6
                    vecReg.add(INT_TBL_NUM_DOC,rst.getString("ne_numDoc"));//7
                    vecReg.add(INT_TBL_FEC_DOC,rst.getString("fe_doc"));//8
                    vecReg.add(INT_TBL_DIA_CRE,rst.getString("ne_diaCre"));//9
                    vecReg.add(INT_TBL_FEC_VEN,rst.getString("fe_ven"));//10
                    vecReg.add(INT_TBL_VAL_DOC,rst.getString("mo_pag"));//11
                    vecReg.add(INT_TBL_VAL_PEN,rst.getString("nd_pen"));//12
                    vecReg.add(INT_TBL_VAL_CHQ,rst.getString("nd_monChq"));//13
                    vecReg.add(INT_TBL_EST_REG,rst.getString("st_reg"));//14
                    vecDat.add(vecReg);
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
            }
        }
        catch (java.sql.SQLException e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    


    
}
