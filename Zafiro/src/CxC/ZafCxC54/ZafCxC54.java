/* 
 * ZafCxC54.java
 *
 * Created on 01 de Abril de 2009, 15:10 PM
 * Dario Cardenas
 * Programa Listado de Cobros en Efectivo vs Cierre de Caja
 * FUE MODIFICADO EL 02/Abril/2009    versio 0.1
 */
package CxC.ZafCxC54;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafDate.ZafDatePicker;//esto es para calcular el numero de dias transcurridos
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;

/**
 *
 * @author  Dario Xavier Cardenas Landin
 */
public class ZafCxC54 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable para Cobros en efectivo//
    final int INT_TBL_DAT_COB_LIN=0;                       //Lï¿½nea.
    final int INT_TBL_DAT_COB_COD_EMP=1;                   //Cï¿½digo de la empresa.
    final int INT_TBL_DAT_COB_COD_LOC=2;                   //Cï¿½digo del local.
    final int INT_TBL_DAT_COB_COD_TIP_DOC=3;               //Cï¿½digo del cliente.
    final int INT_TBL_DAT_COB_NOM_TIP_DOC=4;               //Nombre del cliente.
    final int INT_TBL_DAT_COB_COD_DOC=5;                   //Cï¿½digo del documento.
    final int INT_TBL_DAT_COB_NUM_DOC=6;                   //Nï¿½mero de documento.
    final int INT_TBL_DAT_COB_FEC_DOC=7;                   //Fecha del documento.
    final int INT_TBL_DAT_COB_VAL_DOC=8;                   //Valor del documento.
    
    //Constantes: Columnas del JTable para Cierre de Caja//
    final int INT_TBL_DAT_CIE_LIN=0;                       //Lï¿½nea.
    final int INT_TBL_DAT_CIE_COD_EMP=1;                   //Cï¿½digo de la empresa.
    final int INT_TBL_DAT_CIE_COD_LOC=2;                   //Cï¿½digo del local.
    final int INT_TBL_DAT_CIE_COD_TIP_DOC=3;               //Cï¿½digo del cliente.
    final int INT_TBL_DAT_CIE_NOM_TIP_DOC=4;               //Nombre del cliente.
    final int INT_TBL_DAT_CIE_COD_DOC=5;                   //Cï¿½digo del documento.
    final int INT_TBL_DAT_CIE_NUM_DOC=6;                   //Nï¿½mero de documento.
    final int INT_TBL_DAT_CIE_FEC_DOC=7;                   //Fecha del documento.
    final int INT_TBL_DAT_CIE_VAL_DOC=8;                   //Valor del documento.
    final int INT_TBL_DAT_CIE_SEL_ABO_REC=9;               //Linea para seleccionar
    
    //Constantes: Columnas del JTable para Cobros en efectivo//
    final int INT_TBL_DAT_LIN=0;                        //Lï¿½nea
    final int INT_TBL_DAT_COD_EMP=1;                    //Cï¿½digo de la empresa.
    final int INT_TBL_DAT_COD_LOC=2;                    //Cï¿½digo del local.
    final int INT_TBL_DAT_COD_CLI=3;                    //Cï¿½digo del cliente.
    final int INT_TBL_DAT_NOM_CLI=4;                    //Nombre del cliente.
    final int INT_TBL_DAT_COD_TIP_DOC=5;                //Porcentaje de Retencion.
    final int INT_TBL_DAT_NOM_TIP_DOC=6;                //Porcentaje de Retencion.
    final int INT_TBL_DAT_COD_DOC=7;                    //Cï¿½digo del documento.
    final int INT_TBL_DAT_NUM_DOC=8;                    //Nï¿½mero de documento.    
    final int INT_TBL_DAT_FEC_DOC=9;                    //Fecha del documento.    
    final int INT_TBL_DAT_VAL_DOC=10;                   //Valor del documento.
    final int INT_TBL_DAT_VAL_ABO=11;                   //Valor por Abonar
    
    //Variables//
    private ZafDatePicker dtpDat;                      //esto es para calcular el numero de dias transcurridos
    private ZafUtil objAux;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod, objTblModCabCobEfe, objTblModCabCieCaj, objTblModDetCobEfe;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.    
    private ZafMouMotAdaCabCobEfe objMouMotAdaCabCobEfe;                  //ToolTipText en TableHeader.
    private ZafMouMotAdaCabCieCaj objMouMotAdaCabCieCaj;                  //ToolTipText en TableHeader.
    private ZafMouMotAdaDetCobEfe objMouMotAdaDetCobEfe;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu; 
    private ZafTblEdi objTblEdi;                        //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    private ZafTblTot objTblTot, objTblTotCabCobEfe, objTblTotCabCieCaj, objTblTotDetCobEfe;           //JTable de totales.
    private Connection con, conCabCobEfe, conCabCieCaj, conDetCobEfe;
    private Statement stm, stmCabCobEfe, stmCabCieCaj, stmDetCobEfe;
    private ResultSet rst, rstCabCobEfe, rstCabCieCaj, rstDetCobEfe;
    private String strSQL="", strAux="", STRBAN="", STRBANAUX="";
    private Vector vecDatCobEfe, vecCabCobEfe, vecRegCobEfe , vecAuxCobEfe;
    private Vector vecDatCieCaj, vecCabCieCaj, vecRegCieCaj , vecAuxCieCaj;
    private Vector vecDatDetCob, vecCabDetCob, vecRegDetCob , vecAuxDetCob;
    private boolean blnCon;                             //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli,strCodEmp, strDesLarEmp,strCodTipDoc, strTipDocNom,strTipDocCor, strDesLarEmpTipDoc;             //Contenido del campo al obtener el foco.
    private int intCodLocAux, intClickCon=0;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafVenCon vcoCli,vcoEmp;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    private ZafSelFec objSelFec;
    private int CODMNUINCA=1404;                        //Variable para el Menu de Cobros en Efectivo
    private int CODMNUCIECAJ=1669;                      //Variable para el Menu de Cierres de Caja desde el Programa de Recepcion de Efectivo
    
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelEdiTxt objTblCelEdiTxtNumDoc;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc, txtFecDocDes, txtFecDocHas;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this

    private ZafTblOrd objTblOrdCobEfe, objTblOrdCieCaj, objTblOrdDetCobEfe;
    private ZafTblBus objTblBusCobEfe, objTblBusCieCaj, objTblBusDetCobEfe;
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC54(ZafParSis obj) 
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        jfrThis = this;
                
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
        txtFecDocDes = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
        txtFecDocHas = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
        
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

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFil1 = new javax.swing.JPanel();
        chkMosCobEfeDet = new javax.swing.JCheckBox();
        panFil2 = new javax.swing.JPanel();
        lblDif = new javax.swing.JLabel();
        txtDif = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        panCabCobEfe = new javax.swing.JPanel();
        spnDatCobEfe = new javax.swing.JScrollPane();
        tblDatCobEfe = new javax.swing.JTable();
        spnTotCobEfe = new javax.swing.JScrollPane();
        tblTotCobEfe = new javax.swing.JTable();
        panCabCieCaj = new javax.swing.JPanel();
        spnDatCieCaj = new javax.swing.JScrollPane();
        tblDatCieCaj = new javax.swing.JTable();
        spnTotCieCaj = new javax.swing.JScrollPane();
        tblTotCieCaj = new javax.swing.JTable();
        panDetCobEfe = new javax.swing.JPanel();
        spnDatDetCobEfe = new javax.swing.JScrollPane();
        tblDatDetCobEfe = new javax.swing.JTable();
        spnTotDetCobEfe = new javax.swing.JScrollPane();
        tblTotDetCobEfe = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana"); // NOI18N
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana"); // NOI18N
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panFil1.setLayout(null);

        chkMosCobEfeDet.setSelected(true);
        chkMosCobEfeDet.setText("Mostrar Cobros en Efectivo Detallado"); // NOI18N
        chkMosCobEfeDet.setToolTipText("Documentos por Recaudar(Abiertos)"); // NOI18N
        chkMosCobEfeDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCobEfeDetActionPerformed(evt);
            }
        });
        panFil1.add(chkMosCobEfeDet);
        chkMosCobEfeDet.setBounds(20, 110, 280, 20);

        panFil.add(panFil1, java.awt.BorderLayout.CENTER);

        panFil2.setPreferredSize(new java.awt.Dimension(10, 30));
        panFil2.setLayout(null);

        lblDif.setText("Diferencia");
        panFil2.add(lblDif);
        lblDif.setBounds(520, 5, 80, 20);

        txtDif.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panFil2.add(txtDif);
        txtDif.setBounds(605, 5, 75, 20);

        panFil.add(panFil2, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.GridLayout(1, 0));

        panCabCobEfe.setLayout(new java.awt.BorderLayout());

        spnDatCobEfe.setPreferredSize(new java.awt.Dimension(453, 403));

        tblDatCobEfe.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatCobEfe.setViewportView(tblDatCobEfe);

        panCabCobEfe.add(spnDatCobEfe, java.awt.BorderLayout.CENTER);

        spnTotCobEfe.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotCobEfe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotCobEfe.setViewportView(tblTotCobEfe);

        panCabCobEfe.add(spnTotCobEfe, java.awt.BorderLayout.SOUTH);

        panRpt.add(panCabCobEfe);

        panCabCieCaj.setLayout(new java.awt.BorderLayout());

        spnDatCieCaj.setPreferredSize(new java.awt.Dimension(453, 403));

        tblDatCieCaj.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatCieCaj.setViewportView(tblDatCieCaj);

        panCabCieCaj.add(spnDatCieCaj, java.awt.BorderLayout.CENTER);

        spnTotCieCaj.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotCieCaj.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotCieCaj.setViewportView(tblTotCieCaj);

        panCabCieCaj.add(spnTotCieCaj, java.awt.BorderLayout.SOUTH);

        panRpt.add(panCabCieCaj);

        tabFrm.addTab("Reporte", panRpt);

        panDetCobEfe.setLayout(new java.awt.BorderLayout());

        spnDatDetCobEfe.setPreferredSize(new java.awt.Dimension(453, 403));

        tblDatDetCobEfe.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatDetCobEfe.setViewportView(tblDatDetCobEfe);

        panDetCobEfe.add(spnDatDetCobEfe, java.awt.BorderLayout.CENTER);

        spnTotDetCobEfe.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotDetCobEfe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotDetCobEfe.setViewportView(tblTotDetCobEfe);

        panDetCobEfe.add(spnTotDetCobEfe, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Detalle de Cobros en Efectivo", panDetCobEfe);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar"); // NOI18N
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado."); // NOI18N
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butImp.setText("Imprimir"); // NOI18N
        butImp.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado."); // NOI18N
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

        butVisPre.setText("Vista Preliminar"); // NOI18N
        butVisPre.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado."); // NOI18N
        butVisPre.setPreferredSize(new java.awt.Dimension(110, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butCer.setText("Cerrar"); // NOI18N
        butCer.setToolTipText("Cierra la ventana."); // NOI18N
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

        lblMsgSis.setText("Listo"); // NOI18N
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void chkMosCobEfeDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCobEfeDetActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_chkMosCobEfeDetActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
        intClickCon++;
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

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:
                
        int val = objTblModCabCobEfe.getRowCountTrue();
        
        ////Verificar que el campo de Codigo del cliente/proveedor sea ingresado antes de imprimir///
        if(val == 0)
        {
            mostrarMsgInf("<HTML>No se puede Realizar dicha accion <FONT COLOR=\"blue\">(Vista Preliminar)...</FONT> <BR>Favor <FONT COLOR=\"blue\">CONSULTE DATOS</FONT> para poder realizar esta accion</HTML>");
        }        
//        if (tblDatCobEfe.getRowCount()<=0)
//        {
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente/Proveedor </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Vista Preliminar...</HTML>");
//        } 
        else 
        {
            if (objThrGUIRpt==null) {
                objThrGUIRpt=new ZafThreadGUIRpt();
                objThrGUIRpt.setIndFunEje(1);
                objThrGUIRpt.start();
            }
        }
    }//GEN-LAST:event_butVisPreActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:
        
        int val = objTblModCabCobEfe.getRowCountTrue();
        
        ////Verificar que el campo de Codigo del cliente/proveedor sea ingresado antes de imprimir///
        if(val == 0)
        {
            mostrarMsgInf("<HTML>No se puede Realizar dicha accion <FONT COLOR=\"blue\">(Imprimir)...</FONT> <BR>Favor <FONT COLOR=\"blue\">CONSULTE DATOS</FONT> para poder realizar esta accion</HTML>");
        }        
//        if(txtCodCli.getText().equals("")) {
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente/Proveedor </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Impresion...</HTML>");
//            txtCodCli.requestFocus();
//        } 
    else {
            if (objThrGUIRpt==null) {
                objThrGUIRpt=new ZafThreadGUIRpt();
                objThrGUIRpt.setIndFunEje(1);
                objThrGUIRpt.start();
            }
        }
    }//GEN-LAST:event_butImpActionPerformed

    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVisPre;
    private javax.swing.JCheckBox chkMosCobEfeDet;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCabCieCaj;
    private javax.swing.JPanel panCabCobEfe;
    private javax.swing.JPanel panDetCobEfe;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil1;
    private javax.swing.JPanel panFil2;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDatCieCaj;
    private javax.swing.JScrollPane spnDatCobEfe;
    private javax.swing.JScrollPane spnDatDetCobEfe;
    private javax.swing.JScrollPane spnTotCieCaj;
    private javax.swing.JScrollPane spnTotCobEfe;
    private javax.swing.JScrollPane spnTotDetCobEfe;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatCieCaj;
    private javax.swing.JTable tblDatCobEfe;
    private javax.swing.JTable tblDatDetCobEfe;
    private javax.swing.JTable tblTotCieCaj;
    private javax.swing.JTable tblTotCobEfe;
    private javax.swing.JTable tblTotDetCobEfe;
    private javax.swing.JTextField txtDif;
    // End of variables declaration//GEN-END:variables
   
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.//
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.2 ");
            lblTit.setText(strAux);
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker
            
            //Configurar ZafSelFec//
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha del Documento");
            objSelFec.setCheckBoxChecked(true);
            panFil1.add(objSelFec);
            objSelFec.setBounds(20, 20, 472, 72);
            
            /*Configurar Objeto para llamar al Reporte*/
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            //Configurar los JTables.
            configurarTblCabCobEfe();
            configurarTblCabCieCaj();
            configurarTblDetCobEfe();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /** Configurar el formulario. */
    private boolean configurarTblCabCobEfe()
    {
        boolean blnRes=true;
        try
        {            
            //Configurar JTable: Establecer el modelo.
            vecDatCobEfe=new Vector();    //Almacena los datos
            vecCabCobEfe=new Vector(12);  //Almacena las cabeceras
            vecCabCobEfe.clear();
            vecCabCobEfe.add(INT_TBL_DAT_COB_LIN,"");//0
            vecCabCobEfe.add(INT_TBL_DAT_COB_COD_EMP,"Cod.Emp.");//1
            vecCabCobEfe.add(INT_TBL_DAT_COB_COD_LOC,"Cod.Loc.");//2
            vecCabCobEfe.add(INT_TBL_DAT_COB_COD_TIP_DOC,"Cod.Tip.Doc.");//3
            vecCabCobEfe.add(INT_TBL_DAT_COB_NOM_TIP_DOC,"Nom.Tip.Doc.");//4
            vecCabCobEfe.add(INT_TBL_DAT_COB_COD_DOC,"Cod.Doc.");//5
            vecCabCobEfe.add(INT_TBL_DAT_COB_NUM_DOC,"Num.Doc.");//6
            vecCabCobEfe.add(INT_TBL_DAT_COB_FEC_DOC,"Fec.Doc.");//7
            vecCabCobEfe.add(INT_TBL_DAT_COB_VAL_DOC,"Val.Doc.");//8

            objTblModCabCobEfe=new ZafTblMod();
            objTblModCabCobEfe.setHeader(vecCabCobEfe);
            tblDatCobEfe.setModel(objTblModCabCobEfe);
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDatCobEfe.setRowSelectionAllowed(true);
            tblDatCobEfe.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatCobEfe,INT_TBL_DAT_COB_LIN);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatCobEfe);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatCobEfe.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatCobEfe.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_COB_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COB_COD_EMP).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COB_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COB_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COB_NOM_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COB_COD_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COB_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COB_FEC_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COB_VAL_DOC).setPreferredWidth(80);
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatCobEfe.getTableHeader().setReorderingAllowed(false);



            ocultaColCabCobEfe(INT_TBL_DAT_COB_COD_EMP);
            ocultaColCabCobEfe(INT_TBL_DAT_COB_COD_TIP_DOC);
            ocultaColCabCobEfe(INT_TBL_DAT_COB_COD_DOC);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaCabCobEfe=new ZafMouMotAdaCabCobEfe();
            tblDatCobEfe.getTableHeader().addMouseMotionListener(objMouMotAdaCabCobEfe);

            objTblBusCobEfe=new ZafTblBus(tblDatCobEfe);
            objTblOrdCobEfe=new ZafTblOrd(tblDatCobEfe);


            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COB_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COB_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COB_FEC_DOC).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_COB_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            
            //setEditable(true);
            objTblEdi=new ZafTblEdi(tblDatCobEfe);            
            
            //Configurar JTable: Establecer relaciï¿½n entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_COB_VAL_DOC};
            objTblTotCabCobEfe=new ZafTblTot(spnDatCobEfe, spnTotCobEfe, tblDatCobEfe, tblTotCobEfe, intCol);
                        
            //Libero los objetos auxiliares.
            tcmAux=null;
            objTblCelRenLbl=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** Configurar el formulario. */
    private boolean configurarTblCabCieCaj()
    {
        boolean blnRes=true;
        try
        {            
            //Configurar JTable: Establecer el modelo.
            vecDatCieCaj=new Vector();    //Almacena los datos
            vecCabCieCaj=new Vector(10);  //Almacena las cabeceras
            vecCabCieCaj.clear();
            vecCabCieCaj.add(INT_TBL_DAT_CIE_LIN,"");//0
            vecCabCieCaj.add(INT_TBL_DAT_CIE_COD_EMP,"Cod.Emp.");//1
            vecCabCieCaj.add(INT_TBL_DAT_CIE_COD_LOC,"Cod.Loc.");//2
            vecCabCieCaj.add(INT_TBL_DAT_CIE_COD_TIP_DOC,"Cod.Tip.Doc.");//3
            vecCabCieCaj.add(INT_TBL_DAT_CIE_NOM_TIP_DOC,"Nom.Tip.Doc.");//4
            vecCabCieCaj.add(INT_TBL_DAT_CIE_COD_DOC,"Cod.Doc.");//5
            vecCabCieCaj.add(INT_TBL_DAT_CIE_NUM_DOC,"Num.Doc.");//6
            vecCabCieCaj.add(INT_TBL_DAT_CIE_FEC_DOC,"Fec.Doc.");//7
            vecCabCieCaj.add(INT_TBL_DAT_CIE_VAL_DOC,"Val.Doc.");//8
            vecCabCieCaj.add(INT_TBL_DAT_CIE_SEL_ABO_REC,"");//9

            objTblModCabCieCaj=new ZafTblMod();
            objTblModCabCieCaj.setHeader(vecCabCieCaj);
            tblDatCieCaj.setModel(objTblModCabCieCaj);
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDatCieCaj.setRowSelectionAllowed(true);
            tblDatCieCaj.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatCieCaj,INT_TBL_DAT_CIE_LIN);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatCieCaj);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatCieCaj.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatCieCaj.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CIE_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CIE_COD_EMP).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_CIE_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CIE_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CIE_NOM_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CIE_COD_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CIE_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CIE_FEC_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CIE_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CIE_SEL_ABO_REC).setPreferredWidth(15);
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatCieCaj.getTableHeader().setReorderingAllowed(false);
            ocultaColCabCieCaj(INT_TBL_DAT_CIE_COD_EMP);
            ocultaColCabCieCaj(INT_TBL_DAT_CIE_COD_TIP_DOC);
            ocultaColCabCieCaj(INT_TBL_DAT_CIE_COD_DOC);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaCabCieCaj=new ZafMouMotAdaCabCieCaj();
            tblDatCieCaj.getTableHeader().addMouseMotionListener(objMouMotAdaCabCieCaj);
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblBusCieCaj=new ZafTblBus(tblDatCieCaj);
            objTblOrdCieCaj=new ZafTblOrd(tblDatCieCaj);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_CIE_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CIE_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CIE_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CIE_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            /////para activar el campo de selccion/////
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CIE_SEL_ABO_REC).setCellRenderer(objTblCelRenChkMain);
            objTblCelEdiChkMain=new ZafTblCelEdiChk(tblDatCieCaj);
            
            //setEditable(true);
            objTblEdi=new ZafTblEdi(tblDatCieCaj);            
            
            //Configurar JTable: Establecer relaciï¿½n entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CIE_VAL_DOC};
            objTblTotCabCieCaj=new ZafTblTot(spnDatCieCaj, spnTotCieCaj, tblDatCieCaj, tblTotCieCaj, intCol);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            objTblCelRenLbl=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /** Configurar el formulario. */
    private boolean configurarTblDetCobEfe()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatDetCob=new Vector();    //Almacena los datos
            vecCabDetCob=new Vector(15);  //Almacena las cabeceras
            vecCabDetCob.clear();
            vecCabDetCob.add(INT_TBL_DAT_LIN,"");//0
            vecCabDetCob.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");//1
            vecCabDetCob.add(INT_TBL_DAT_COD_LOC,"Cod.Loc.");//2
            vecCabDetCob.add(INT_TBL_DAT_COD_CLI,"Cod.Cli.");//3
            vecCabDetCob.add(INT_TBL_DAT_NOM_CLI,"Nom.Cli.");//4            
            vecCabDetCob.add(INT_TBL_DAT_COD_TIP_DOC,"Cod.Tip.Doc.");//5
            vecCabDetCob.add(INT_TBL_DAT_NOM_TIP_DOC,"Nom.Tip.Doc.");//6
            vecCabDetCob.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");//7
            vecCabDetCob.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");//8
            vecCabDetCob.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");//9
            vecCabDetCob.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");//10
            vecCabDetCob.add(INT_TBL_DAT_VAL_ABO,"Val.Abo.");//11

            objTblModDetCobEfe=new ZafTblMod();
            objTblModDetCobEfe.setHeader(vecCabDetCob);
            tblDatDetCobEfe.setModel(objTblModDetCobEfe);
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblDatDetCobEfe.setRowSelectionAllowed(true);
            tblDatDetCobEfe.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatDetCobEfe,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatDetCobEfe);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatDetCobEfe.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatDetCobEfe.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(10);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(130);//antes estaba 30
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TIP_DOC).setPreferredWidth(90);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);//antes 80
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(80);//antes 80
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatDetCobEfe.getTableHeader().setReorderingAllowed(false);
            
            ocultaColDetCobEfe(INT_TBL_DAT_COD_EMP);
            ocultaColDetCobEfe(INT_TBL_DAT_COD_TIP_DOC);
            ocultaColDetCobEfe(INT_TBL_DAT_COD_DOC);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDetCobEfe=new ZafMouMotAdaDetCobEfe();
            tblDatDetCobEfe.getTableHeader().addMouseMotionListener(objMouMotAdaDetCobEfe);
            
            //Configurar JTable: Editor de bï¿½squeda.
            objTblOrdDetCobEfe=new ZafTblOrd(tblDatDetCobEfe);
            objTblBusDetCobEfe=new ZafTblBus(tblDatDetCobEfe);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            
            //setEditable(true);
            objTblEdi=new ZafTblEdi(tblDatDetCobEfe);
            
            //Configurar JTable: Establecer relaciï¿½n entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_ABO};
            objTblTotDetCobEfe=new ZafTblTot(spnDatDetCobEfe, spnTotDetCobEfe, tblDatDetCobEfe, tblTotDetCobEfe, intCol);
                        
            //Libero los objetos auxiliares.
            tcmAux=null;
            objTblCelRenLbl=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private void ocultaColCabCobEfe(int intCol)
    {
        tblDatCobEfe.getColumnModel().getColumn(intCol).setWidth(0);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    private void mostrarColCabCobEfe(int intCol, int tamCol)
    {
        tblDatCobEfe.getColumnModel().getColumn(intCol).setWidth(tamCol);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setMaxWidth(tamCol);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setMinWidth(tamCol);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setPreferredWidth(tamCol);
        tblDatCobEfe.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    private void ocultaColCabCieCaj(int intCol)
    {
        tblDatCieCaj.getColumnModel().getColumn(intCol).setWidth(0);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    private void mostrarColCabCieCaj(int intCol, int tamCol)
    {
        tblDatCieCaj.getColumnModel().getColumn(intCol).setWidth(tamCol);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setMaxWidth(tamCol);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setMinWidth(tamCol);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setPreferredWidth(tamCol);
        tblDatCieCaj.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    private void ocultaColDetCobEfe(int intCol)
    {
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setWidth(0);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    private void mostrarColDetCobEfe(int intCol, int tamCol)
    {
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setWidth(tamCol);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setMaxWidth(tamCol);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setMinWidth(tamCol);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setPreferredWidth(tamCol);
        tblDatDetCobEfe.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    
    /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegCabCobEfe()
    {
        int intCodEmp, intCodLoc, intCodUsr, i;
        
        int intNumDia; 
        String strFecSis, strFecIni;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema.
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema.
            intCodUsr=objParSis.getCodigoUsuario();//obtiene el codigo del Usuario que ingreso al sistema.
            
            conCabCobEfe=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (conCabCobEfe!=null)
            {
                stmCabCobEfe=conCabCobEfe.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condiciï¿½n.
                strAux="";
                                      
                System.out.println(" ");
                
                ///////////para filtrar por fechas Desde y Hasta//////////////
                if (objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //BÃ³squeda por rangos
                            strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
              
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, ";
                strSQL+=" a1.ne_numDoc1, a1.ne_numDoc2, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.nd_mondoc, a1.st_imp, a1.fe_ing ";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";

                if(objParSis.getCodigoUsuario()==1){
                    strSQL+=" INNER JOIN tbr_tipDocPrg AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc)";
                }
                else{
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc)";
                }
                
                strSQL+=" WHERE a1.st_reg NOT IN ('E','I') ";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if(objParSis.getCodigoUsuario()!=1)
                    strSQL+=" AND a3.co_usr=" + intCodUsr;
                strSQL+=" AND a3.co_mnu=" + CODMNUINCA;
                strSQL+=" AND a1.st_reg NOT IN('E', 'I')";
                strSQL+=strAux;
                strSQL+=" ORDER BY a1.co_emp, a1.fe_doc, a1.co_loc, a1.co_tipdoc, a1.ne_numdoc1";
                System.out.println("---Query de registros Detallado cargarDetRegCabCobEfe(): "+ strSQL);
                
                rstCabCobEfe=stmCabCobEfe.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatCobEfe.clear();
                
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;
                
                while (rstCabCobEfe.next())
                {
                    if (blnCon)
                    {
                        vecRegCobEfe=new Vector();
                        vecRegCobEfe.add(INT_TBL_DAT_COB_LIN,"");
                        vecRegCobEfe.add(INT_TBL_DAT_COB_COD_EMP,rstCabCobEfe.getString("co_emp"));
                        vecRegCobEfe.add(INT_TBL_DAT_COB_COD_LOC,rstCabCobEfe.getString("co_loc"));
                        vecRegCobEfe.add(INT_TBL_DAT_COB_COD_TIP_DOC,rstCabCobEfe.getString("co_tipDoc"));
                        vecRegCobEfe.add(INT_TBL_DAT_COB_NOM_TIP_DOC,rstCabCobEfe.getString("tx_desCor"));
                        vecRegCobEfe.add(INT_TBL_DAT_COB_COD_DOC,rstCabCobEfe.getString("co_doc"));
                        vecRegCobEfe.add(INT_TBL_DAT_COB_NUM_DOC,rstCabCobEfe.getString("ne_numDoc1"));
                        vecRegCobEfe.add(INT_TBL_DAT_COB_FEC_DOC,rstCabCobEfe.getString("fe_doc"));
                        vecRegCobEfe.add(INT_TBL_DAT_COB_VAL_DOC,rstCabCobEfe.getString("nd_mondoc"));
                        vecDatCobEfe.add(vecRegCobEfe);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                
                //Cerrar Conexiones//
                rstCabCobEfe.close();
                stmCabCobEfe.close();
                conCabCobEfe.close();
                rstCabCobEfe=null;
                stmCabCobEfe=null;
                conCabCobEfe=null;
                
                //Asignar vectores al modelo.//
                objTblModCabCobEfe.setData(vecDatCobEfe);
                tblDatCobEfe.setModel(objTblModCabCobEfe);
                vecDatCobEfe.clear();
                
                //Calcular totales.//
                objTblTotCabCobEfe.calcularTotales();
                
                lblMsgSis.setText("En Listado de Cobros en Efectivo VS Cierres de Caja. Se encontraron " + tblDatCobEfe.getRowCount() + " registros. ");
                
                pgrSis.setValue(0);
                butCon.setText("Consultar");
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
    private boolean cargarDetRegCabCieCaj()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i;
        
        int intNumDia; 
        String strFecSis, strFecIni;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            
            conCabCieCaj=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (conCabCieCaj!=null)
            {
                stmCabCieCaj=conCabCieCaj.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condiciï¿½n.
                strAux="";
                                      
                System.out.println(" ");
                
                ///////////para filtrar por fechas Desde y Hasta//////////////
                if (objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //BÃ³squeda por rangos
                            strAux+=" AND a2.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a2.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a2.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
             
                //Obtener el nï¿½mero total de registros.
                strSQL="";
                strSQL+=" SELECT COUNT(*)";
                strSQL+=" FROM tbm_detdia AS a1";
                strSQL+=" INNER JOIN tbm_cabdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                strSQL+=" INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                strSQL+=" WHERE a1.nd_mondeb>0 ";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a4.co_mnu=" + CODMNUCIECAJ;
                strSQL+=" AND a2.st_reg NOT IN('E', 'I')";
                
                strSQL+=strAux;
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp,  a1.co_loc, a1.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.co_dia, a1.co_reg, a1.nd_mondeb, a2.tx_numdia, a2.fe_dia, a2.st_reg ";
                strSQL+=" FROM tbm_detdia AS a1";
                strSQL+=" INNER JOIN tbm_cabdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc) ";
                strSQL+=" INNER JOIN tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                strSQL+=" WHERE a1.nd_mondeb>0 ";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a4.co_mnu=" + CODMNUCIECAJ;
                strSQL+=" AND a2.st_reg NOT IN('E', 'I')";
                strSQL+=strAux;
                strSQL+=" ORDER BY a1.co_emp, a2.fe_dia, a1.co_loc, a1.co_tipdoc, a1.co_dia ";
                
                System.out.println("---Query de registros Detallado cargarDetRegCabCieCaj(): "+ strSQL);
                
                rstCabCieCaj=stmCabCieCaj.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatCieCaj.clear();
                
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                
                while (rstCabCieCaj.next())
                {
                    if (blnCon)
                    {
                        vecRegCieCaj=new Vector();
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_LIN,"");
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_COD_EMP,rstCabCieCaj.getString("co_emp"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_COD_LOC,rstCabCieCaj.getString("co_loc"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_COD_TIP_DOC,rstCabCieCaj.getString("co_tipdoc"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_NOM_TIP_DOC,rstCabCieCaj.getString("tx_descor"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_COD_DOC,rstCabCieCaj.getString("co_dia"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_NUM_DOC,rstCabCieCaj.getString("tx_numdia"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_FEC_DOC,rstCabCieCaj.getString("fe_dia"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_VAL_DOC,rstCabCieCaj.getString("nd_mondeb"));
                        vecRegCieCaj.add(INT_TBL_DAT_CIE_SEL_ABO_REC,null);
                        
                        if(rstCabCieCaj.getString("st_reg").equals("A"))
                            vecRegCieCaj.setElementAt(new Boolean(true),INT_TBL_DAT_CIE_SEL_ABO_REC);
                        
                        vecDatCieCaj.add(vecRegCieCaj);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                
                //Cerrar Conexiones//
                rstCabCieCaj.close();
                stmCabCieCaj.close();
                conCabCieCaj.close();
                rstCabCieCaj=null;
                stmCabCieCaj=null;
                conCabCieCaj=null;
                
                //Asignar vectores al modelo.//
                objTblModCabCieCaj.setData(vecDatCieCaj);
                tblDatCieCaj.setModel(objTblModCabCieCaj);
                vecDatCieCaj.clear();
                
                //Calcular totales.//
                objTblTotCabCieCaj.calcularTotales();
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
    private boolean cargarDetRegDetCobEfe()
    {
        int intCodEmp, intCodLoc, intCodUsr, i;
        
        int intNumDia; 
        String strFecSis, strFecIni;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            intCodUsr=objParSis.getCodigoUsuario();//obtiene el codigo del usuario al ingresar al sistema
            
            conDetCobEfe=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (conDetCobEfe!=null)
            {
                stmDetCobEfe=conDetCobEfe.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condiciï¿½n.
                strAux="";
                                      
                System.out.println(" ");
                
                ///////////para filtrar por fechas Desde y Hasta//////////////
                if (objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //BÃ³squeda por rangos
                            strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a4.co_emp, a4.co_loc, a4.co_cli, a4.tx_nomcli,  a5.co_tipdoc, a5.tx_descor, a5.tx_deslar, a3.co_doc, ";
                strSQL+=" a3.co_reg, a4.ne_numdoc, a4.fe_doc, a3.mo_pag as ValDoc,  a2.nd_abo as ValAbo, (a3.mo_pag+a2.nd_abo) as ValPen ";
                strSQL+=" FROM tbm_cabpag AS a1";
                strSQL+=" INNER JOIN tbm_detpag AS a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a3 ON (a2.co_emp=a3.co_emp and a2.co_locpag=a3.co_loc and a2.co_tipdocpag=a3.co_tipdoc and a2.co_docpag=a3.co_doc and a2.co_regpag=a3.co_reg) ";
                strSQL+=" INNER JOIN tbm_cabmovinv AS a4 ON (a3.co_emp=a4.co_emp and a3.co_loc=a4.co_loc and a3.co_tipdoc=a4.co_tipdoc and a3.co_doc=a4.co_doc) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a5 ON (a3.co_emp=a5.co_emp and a3.co_loc=a5.co_loc and a3.co_tipdoc=a5.co_tipdoc) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a6 ON (a1.co_emp=a6.co_emp and a1.co_loc=a6.co_loc and a1.co_tipdoc=a6.co_tipdoc) ";
                if(objParSis.getCodigoUsuario()==1){
                    strSQL+=" INNER JOIN tbr_tipDocPrg AS a7 ON (a6.co_emp=a7.co_emp AND a6.co_loc=a7.co_loc AND a6.co_tipDoc=a7.co_tipDoc) ";
                }
                else{
                    strSQL+=" INNER JOIN tbr_tipDocUsr AS a7 ON (a6.co_emp=a7.co_emp AND a6.co_loc=a7.co_loc AND a6.co_tipDoc=a7.co_tipDoc) ";
                }
                strSQL+=" WHERE a1.st_reg NOT IN ('E','I') AND a3.st_reg IN ('A','C') ";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                if(objParSis.getCodigoUsuario()!=1)
                    strSQL+=" AND a7.co_usr=" + intCodUsr;
                strSQL+=" AND a7.co_mnu=" + CODMNUINCA;
                strSQL+=strAux;
                strSQL+=" ORDER BY a1.co_emp, a4.fe_doc, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg ";
                
                System.out.println("---Query de registros Detallado cargarDetRegDetCobEfe(): "+ strSQL);
                
                rstDetCobEfe=stmDetCobEfe.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatDetCob.clear();
                
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;
                
                while (rstDetCobEfe.next())
                {
                    if (blnCon)
                    {
                        vecRegDetCob=new Vector();
                        vecRegDetCob.add(INT_TBL_DAT_LIN,"");
                        vecRegDetCob.add(INT_TBL_DAT_COD_EMP,rstDetCobEfe.getString("co_emp"));
                        vecRegDetCob.add(INT_TBL_DAT_COD_LOC,rstDetCobEfe.getString("co_loc"));
                        vecRegDetCob.add(INT_TBL_DAT_COD_CLI,rstDetCobEfe.getString("co_cli"));
                        vecRegDetCob.add(INT_TBL_DAT_NOM_CLI,rstDetCobEfe.getString("tx_nomcli"));
			vecRegDetCob.add(INT_TBL_DAT_COD_TIP_DOC,rstDetCobEfe.getString("co_tipdoc"));
                        vecRegDetCob.add(INT_TBL_DAT_NOM_TIP_DOC,rstDetCobEfe.getString("tx_descor"));
                        vecRegDetCob.add(INT_TBL_DAT_COD_DOC,rstDetCobEfe.getString("co_doc"));                        
                        vecRegDetCob.add(INT_TBL_DAT_NUM_DOC,rstDetCobEfe.getString("ne_numDoc"));
                        vecRegDetCob.add(INT_TBL_DAT_FEC_DOC,rstDetCobEfe.getString("fe_doc"));
                        vecRegDetCob.add(INT_TBL_DAT_VAL_DOC,rstDetCobEfe.getString("ValDoc"));
                        vecRegDetCob.add(INT_TBL_DAT_VAL_ABO,rstDetCobEfe.getString("ValAbo"));
                        vecDatDetCob.add(vecRegDetCob);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                
                //Cerrar Conexiones//
                rstDetCobEfe.close();
                stmDetCobEfe.close();
                conDetCobEfe.close();
                rstDetCobEfe=null;
                stmDetCobEfe=null;
                conDetCobEfe=null;
                
                //Asignar vectores al modelo.//
                objTblModDetCobEfe.setData(vecDatDetCob);
                tblDatDetCobEfe.setModel(objTblModDetCobEfe);
                vecDatDetCob.clear();
                
                //Calcular totales.//
                objTblTotDetCobEfe.calcularTotales();
                
                double dblTotCobEfe = objUti.parseDouble(objTblTotCabCobEfe.getValueAt(0, INT_TBL_DAT_COB_VAL_DOC));
                double dblTotCieCaj = objUti.parseDouble(objTblTotCabCieCaj.getValueAt(0, INT_TBL_DAT_CIE_VAL_DOC));
                double dblValDif = objUti.redondear((dblTotCobEfe - dblTotCieCaj),2);
                        
                txtDif.setText("" + Math.abs(dblValDif));
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
     * Esta funciï¿½n muestra un mensaje informativo al usuario. Se podrï¿½a utilizar
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
     * Esta funciï¿½n muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta funciï¿½n muestra un mensaje de error al usuario. Se podrï¿½a utilizar
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
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
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
                ///para cuando esta activo el chk de doc abiertos///
                if(chkMosCobEfeDet.isSelected())
                {
                    if (cargarDetRegCabCobEfe())
                    {
                        if (cargarDetRegCabCieCaj())
                        {
                            if (cargarDetRegDetCobEfe())
                            {
                                //Inicializar objetos si no se pudo cargar los datos.//
                                lblMsgSis.setText("Listo");
                                pgrSis.setValue(0);
                                butCon.setText("Consultar");
                            }
                        }
                    }
                    
                    ////Establecer el foco en el JTable sï¿½lo cuando haya datos.//
                    if (tblDatCobEfe.getRowCount()>0)
                    {
                        tabFrm.setSelectedIndex(1);
                        tblDatCobEfe.setRowSelectionInterval(0, 0);
                        tblDatCobEfe.requestFocus();
                    }
                    
                    if (tblDatCieCaj.getRowCount()>0)
                    {
                        tabFrm.setSelectedIndex(1);
                        tblDatCieCaj.setRowSelectionInterval(0, 0);
                        tblDatCieCaj.requestFocus();
                    }
                    
                    objThrGUI=null;
                }                
                else
                {
                    if (cargarDetRegCabCobEfe())
                    {
                        if (cargarDetRegCabCieCaj())
                        {
                            objTblModDetCobEfe.removeAllRows();
                            //Inicializar objetos si no se pudo cargar los datos.//
                            lblMsgSis.setText("Listo");
                            pgrSis.setValue(0);
                            butCon.setText("Consultar");
                        }
                    }
                    
                    ////Establecer el foco en el JTable sï¿½lo cuando haya datos.//
                    if (tblDatCobEfe.getRowCount()>0)
                    {
                        tabFrm.setSelectedIndex(1);
                        tblDatCobEfe.setRowSelectionInterval(0, 0);
                        tblDatCobEfe.requestFocus();
                    }
                    
                    if (tblDatCieCaj.getRowCount()>0)
                    {
                        tabFrm.setSelectedIndex(1);
                        tblDatCieCaj.setRowSelectionInterval(0, 0);
                        tblDatCieCaj.requestFocus();
                    }
                    
                    objThrGUI=null;
                }

                
        }
        
        /**
         * Esta funciï¿½n establece el indice de la funciï¿½n a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta funciï¿½n sirve para determinar
         * la funciï¿½n que debe ejecutar el Thread.
         * @param indice El indice de la funciï¿½n a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
    
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;
        
        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botï¿½n "Imprimir".
                    ///objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    ///objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botï¿½n "Vista Preliminar".
                    ///objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    ///objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt=null;
        }
        
        /**
         * Esta funciï¿½n establece el indice de la funciï¿½n a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta funciï¿½n sirve para determinar
         * la funciï¿½n que debe ejecutar el Thread.
         * @param indice El indice de la funciï¿½n a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
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
        String strRutRpt, strNomRpt, strFecHorSer, strBan="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        String strCodEmp = String.valueOf(intCodEmp);
        String strCodLoc = String.valueOf(intCodLoc);
        int intCodUsr=objParSis.getCodigoUsuario();
        
        ///////el codigo de menu del programa es 2110////////
        
        ///////el codigo de menu del reporte es 330////////
        
        
        String NomUsr = objParSis.getNombreUsuario();
        String NomUsrFun = rtnNomUsrSis();
        
        STRBAN="";
        
        STRBAN=FilSql();

        
        
        //Inicializar los parametros que se van a pasar al reporte.
        java.util.Map mapPar=new java.util.HashMap();
        
        Connection conIns;
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
                        intNumTotRpt=objRptSis.getNumeroTotalReportes();
                        for (i=0;i<intNumTotRpt;i++)
                        {
                            if (objRptSis.isReporteSeleccionado(i))
                            {
                                switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                {
                                    case 330:
                                            System.out.println("---ENTRO EN EL REPORTE...menu 330..");
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);

                                            /////////////////////////////////////////////////
                                            System.out.println("---COD_EMP: " + strCodEmp);
                                            System.out.println("---COD_LOC: " + strCodLoc);
                                            ////System.out.println("COD_CLI: " + CodCli);
                                            ////System.out.println("NOM_CLI: " + NomCli);
                                            System.out.println("---intCodUsr: " + intCodUsr);
                                            System.out.println("---NomUsr: " + NomUsr);
                                            System.out.println("---NomUsrFun: " + NomUsrFun);
                                            System.out.println("---STRBAN: " + STRBAN);
                                            System.out.println("---STRBANAUX: " + STRBANAUX);
                                            System.out.println("---CODMNUINCA: " + CODMNUINCA);
                                            System.out.println("---CODMNUCIECAJ: " + CODMNUCIECAJ);
                                            
                                            ////////////////////////////////////////////////  

                                            mapPar.put("CodEmp", "" + strCodEmp);
                                            mapPar.put("CodLoc", "" + strCodLoc);
                                            mapPar.put("CodUsr", "" + intCodUsr);
                                            mapPar.put("NomUsr", "" + NomUsrFun);
                                            mapPar.put("CodMnu", "" + CODMNUINCA);
                                            mapPar.put("CodMnuAux", "" + CODMNUCIECAJ);
                                            mapPar.put("Ban", ""+STRBAN);
                                            mapPar.put("BanAux", "" + STRBANAUX);
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
                System.out.println("Excepcion: " + e.toString());
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
    
    
    private String FilSql() 
    {
        String strAux = "", strBanAux="";
        String strFecSis, strFecIni, strFecsisFor, strFecVen;
        java.util.Date datFecSer, datFecVen, datFecAux;
        boolean blnRes=true;        
        int intCodEmp, intCodEmpGrp;
                
        datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
        intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
        
        if (datFecSer==null)
            blnRes=false;
        
        datFecAux=datFecSer;
                
        strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
        strFecsisFor=objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
        
        //Obtener la condicion.
        strAux="";
        
        ///////////para filtrar por fechas Desde y Hasta//////////////
        if (objSelFec.isCheckBoxChecked())
        {
            switch (objSelFec.getTipoSeleccion())
            {
                case 0: //Busqueda por rangos//
                    strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    strBanAux+=" AND a2.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".//
                    strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    strBanAux+=" AND a2.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".//
                    strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    strBanAux+=" AND a2.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.//
                    break;
            }
        }
        
        System.out.println("---strAux: " + strAux);
        System.out.println("---strBanAux: " + strBanAux);
        
        STRBANAUX = strBanAux;
        
        return strAux;
    }
    
    protected String rtnNomUsrSis()
    {
	java.sql.Connection conUltRegDoc;
        java.sql.Statement stmUltRegDoc;
        java.sql.ResultSet rstUltRegDoc;
        String strSQL, stRegRep="";
        int intAux=0;
        int codusr= objParSis.getCodigoUsuario();

        try
        {
               conUltRegDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
               conUltRegDoc.setAutoCommit(false);
                    if (conUltRegDoc!=null)
                    {                        
                        stmUltRegDoc=conUltRegDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        strSQL="";
                        strSQL+="SELECT co_usr, tx_usr, tx_pwd, tx_nom";
                        strSQL+=" FROM tbm_usr AS a1";
                        strSQL+=" WHERE a1.co_usr= " + codusr;
                        strSQL+=" AND a1.st_usrSis='S'";
                        strSQL+=" AND a1.st_reg='A'";
                        System.out.println("---Query para el mostrar datos de usuarios del sistema es: " + strSQL);
                        rstUltRegDoc=stmUltRegDoc.executeQuery(strSQL);
                        
                        if (rstUltRegDoc.next())
                        {
                            stRegRep = rstUltRegDoc.getString("tx_nom");
                        }
                        
                        System.out.println("---El nombre del usuario es: " + stRegRep);
                        
                        conUltRegDoc.close();
                        conUltRegDoc = null;
                        
                        stmUltRegDoc.close();
                        stmUltRegDoc = null;
                        
                        rstUltRegDoc.close();
                        rstUltRegDoc = null;
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
        return stRegRep;
    }

    
    private void calcularAboTot()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {
            intFilPro=objTblMod.getRowCountTrue();
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_ABO)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_ABO).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            //Calcular la diferencia.
            //txtMonDoc.setText("" + objUti.redondeo(dblTot,objParSis.getDecimalesMostrar()));
        }
        catch (NumberFormatException e)
        {
            //txtMonDoc.setText("[ERROR]");
        }
    }
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAdaCabCobEfe extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {            
            int intCol=tblDatCobEfe.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COB_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COB_COD_EMP:
                    strMsg="Codigo de la Empresa";
                    break;
                case INT_TBL_DAT_COB_COD_LOC:
                    strMsg="Codigo del Local";
                    break;
                case INT_TBL_DAT_COB_COD_TIP_DOC:
                    strMsg="Codigo del Tipo de Documento";
                    break;
                case INT_TBL_DAT_COB_NOM_TIP_DOC:
                    strMsg="Nombre del Tipo de Documento";
                    break;
                case INT_TBL_DAT_COB_COD_DOC:
                    strMsg="Codigo del Documento";
                    break;
                case INT_TBL_DAT_COB_NUM_DOC:
                    strMsg="Numero de Documento";
                    break;
                case INT_TBL_DAT_COB_FEC_DOC:
                    strMsg="Fecha del Documento";
                    break;
                case INT_TBL_DAT_COB_VAL_DOC:
                    strMsg="Valor del Documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatCobEfe.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAdaCabCieCaj extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {            
            int intCol=tblDatCobEfe.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_CIE_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CIE_COD_EMP:
                    strMsg="Codigo de la Empresa";
                    break;
                case INT_TBL_DAT_CIE_COD_LOC:
                    strMsg="Codigo del Local";
                    break;
                case INT_TBL_DAT_CIE_COD_TIP_DOC:
                    strMsg="Codigo del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CIE_NOM_TIP_DOC:
                    strMsg="Nombre del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CIE_COD_DOC:
                    strMsg="Codigo del Documento";
                    break;
                case INT_TBL_DAT_CIE_NUM_DOC:
                    strMsg="Numero de Documento";
                    break;
                case INT_TBL_DAT_CIE_FEC_DOC:
                    strMsg="Fecha del Documento";
                    break;
                case INT_TBL_DAT_CIE_VAL_DOC:
                    strMsg="Valor del Documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatCieCaj.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAdaDetCobEfe extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatCobEfe.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Codigo de la Empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Codigo del Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Codigo del Tipo de Documento";
                    break;
                case INT_TBL_DAT_NOM_TIP_DOC:
                    strMsg="Nombre del Tipo de Documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Codigo del Documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Numero de Documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del Documento";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del Documento";
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    strMsg="Valor Abonado o Diferencia";
                    break;                
                default:
                    strMsg="";
                    break;
            }
            tblDatDetCobEfe.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}