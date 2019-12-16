/*
 * ZafCxC45.java
 *
 * Created on 28 de agosto de 2008, 02:25 PM
 */
package CxC.ZafCxC45;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
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
public class ZafCxC45 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=2;                     //Código del local.
    static final int INT_TBL_DAT_COD_TIP_DOC=3;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_COD_DOC=4;                     //Código del documento (Sistema).
    static final int INT_TBL_DAT_COD_REG=5;                     //Código del registro (Sistema).
    static final int INT_TBL_DAT_NUM_DOC=6;                     //Número del documento.
    static final int INT_TBL_DAT_FEC_DOC=7;                     //Fecha del documento.
    static final int INT_TBL_DAT_FEC_VEN=8;                     //Fecha de vencimiento.
    static final int INT_TBL_DAT_FEC_PAG=9;                     //Fecha de pago.
    static final int INT_TBL_DAT_VAL_DOC=10;                    //Valor del documento.
    static final int INT_TBL_DAT_ABO_DOC=11;                    //Abono del documento.
    static final int INT_TBL_DAT_DIA_CRE=12;                    //Días de crédito.
    static final int INT_TBL_DAT_DIA_PAG=13;                    //Días de pago.
    static final int INT_TBL_DAT_DIA_GRA=14;                    //Días de gracia.
    static final int INT_TBL_DAT_NUM_TOT_POS=15;                //Número total de solicitudes de postergacion
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnCarDatAut;                               //Cargar datos automáticamente.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private boolean blnUtiCliEmp;                               //Utilizar clientes por empresa.
    //Variables de la clase.
    private Integer intCodEmp;
    private Integer intCodCli;
    private String strIdeCli;
   
    /**
     * Crea una nueva instancia de la clase ZafCxC45. 
     * @param obj El objeto ZafParSis.
     */
    public ZafCxC45(ZafParSis obj) 
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        blnCarDatAut=false;
    }
    
    /**
     * Este constructor crea una instancia de la clase ZafCxC45 y al mismo tiempo carga 
     * automáticamente el historial de pagos del cliente especificado de acuerdo al código
     * de la empresa y el código del cliente.
     * @param obj El objeto ZafParSis.
     * @param codigoEmpresa El código de la empresa.
     * @param codigoCliente El código del cliente.
     */
    public ZafCxC45(ZafParSis obj, Integer codigoEmpresa, Integer codigoCliente)
    {
        this(obj);
        intCodEmp=codigoEmpresa;
        intCodCli=codigoCliente;
        blnCarDatAut=true;
    }
    
    /**
     * Este constructor crea una instancia de la clase ZafCxC45 y al mismo tiempo carga 
     * automáticamente el historial de pagos del cliente especificado de acuerdo a la identificación del cliente.
     * @param obj El objeto ZafParSis.
     * @param identificacionCliente La identificación del cliente.
     */
    public ZafCxC45(ZafParSis obj, String identificacionCliente)
    {
        this(obj);
        strIdeCli=identificacionCliente;
        blnCarDatAut=true;
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
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panFil.add(lblCli);
        lblCli.setBounds(4, 4, 120, 20);

        txtCodCli.setToolTipText("Código del cliente");
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
        panFil.add(txtCodCli);
        txtCodCli.setBounds(124, 4, 56, 20);

        txtIdeCli.setToolTipText("Identificación del cliente");
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        panFil.add(txtIdeCli);
        txtIdeCli.setBounds(180, 4, 100, 20);

        txtDesLarCli.setToolTipText("Nombre del cliente");
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
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(280, 4, 360, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(640, 4, 20, 20);

        tabFrm.addTab("Filtro", panFil);

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

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        //Cargar datos automáticamente sólo si es necesario.
        if (blnCarDatAut)
        {
            if (intCodCli!=null)
            {
                //Cargar los datos para el cliente especificado.
                txtCodCli.setText(intCodCli.toString());
                mostrarVenConCli(1);
            }
            else if (strIdeCli!=null)
            {
                //Cargar los datos para el cliente especificado.
                txtIdeCli.setText(strIdeCli);
                mostrarVenConCli(2);
            }
            blnCon=true;
            butConActionPerformed(null);
        }
    }//GEN-LAST:event_formInternalFrameOpened

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
                    objThrGUI.setIndFunEje(0);
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
    txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
    strCodCli=txtCodCli.getText();
    txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
    {
        if (txtCodCli.getText().equals(""))
        {
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
        }
        else
        {
            mostrarVenConCli(1);
        }
    }
    else
        txtCodCli.setText(strCodCli);
}//GEN-LAST:event_txtCodCliFocusLost

private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
    txtIdeCli.transferFocus();
}//GEN-LAST:event_txtIdeCliActionPerformed

private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
    strIdeCli=txtIdeCli.getText();
    txtIdeCli.selectAll();
}//GEN-LAST:event_txtIdeCliFocusGained

private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli))
    {
        if (txtIdeCli.getText().equals(""))
        {
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
        }
        else
        {
            mostrarVenConCli(2);
        }
    }
    else
        txtIdeCli.setText(strIdeCli);
}//GEN-LAST:event_txtIdeCliFocusLost

private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
    txtDesLarCli.transferFocus();
}//GEN-LAST:event_txtDesLarCliActionPerformed

private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
    strDesLarCli=txtDesLarCli.getText();
    txtDesLarCli.selectAll();
}//GEN-LAST:event_txtDesLarCliFocusGained

private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
    {
        if (txtDesLarCli.getText().equals(""))
        {
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
        }
        else
        {
            mostrarVenConCli(3);
        }
    }
    else
        txtDesLarCli.setText(strDesLarCli);
}//GEN-LAST:event_txtDesLarCliFocusLost

private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
    mostrarVenConCli(0);
}//GEN-LAST:event_butCliActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtIdeCli;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha del documento");
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 24, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            if (objParSis.getCodigoMenu()==1050)
                strAux=objParSis.getNombreMenu();
            else
                strAux="Historial de pago de clientes...";
            this.setTitle(strAux + " v0.1.1");
            lblTit.setText(strAux);
            blnUtiCliEmp=objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario());
            //Configurar objetos.
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtIdeCli.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCli.setBackground(objParSis.getColorCamposObligatorios());
            //Configurar las ZafVenCon.
            configurarVenConCli();
            //Configurar los JTables.
            configurarTblDat();
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                txtCodCli.setEditable(false);
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
            vecCab=new Vector(16);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_FEC_PAG,"Fec.Pag");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_ABO_DOC,"Val.Abo.");
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Día.Cré.");
            vecCab.add(INT_TBL_DAT_DIA_PAG,"Día.Pag.");
            vecCab.add(INT_TBL_DAT_DIA_GRA,"Día.Gra.");
            vecCab.add(INT_TBL_DAT_NUM_TOT_POS,"Núm.Tot.Pos.");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PAG).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_ABO_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DIA_PAG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DIA_GRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_TOT_POS).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_PAG, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ABO_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_PAG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_GRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_TOT_POS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_ABO_DOC};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        String strConSQLFec="";
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
                strConSQL="";
                if (objSelFec.isCheckBoxChecked())
                {
                    strConSQLFec="";
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strConSQLFec+=" AND b1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strConSQLFec+=" AND b1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strConSQLFec+=" AND b1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                    strConSQL+=strConSQLFec;
                }
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.co_reg, e1.ne_numDoc, e1.fe_doc, e1.fe_ing";
                    strSQL+=" , e1.fe_ven, e1.fe_pag, e1.mo_pag, e1.nd_abo, e1.ne_diaCre, e1.ne_diaPag, e1.ne_diaGra, e2.ne_numTotPos";
                    strSQL+=" FROM(";

                    strSQL+="SELECT b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.co_reg, b1.ne_numDoc, b1.fe_doc, b1.fe_ing, b2.fe_ven, b3.fe_pag, b2.mo_pag, b3.nd_abo, b2.ne_diaCre, (b3.fe_pag-b1.fe_doc) AS ne_diaPag, b4.ne_diaGra";
                    strSQL+=" FROM tbm_cabMovInv AS b1";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
                    strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, a1.fe_doc AS fe_pag, a1.fe_ing, a2.nd_abo";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" RIGHT OUTER JOIN tbm_pagMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg)";
                    strSQL+=" INNER JOIN tbm_cabMovInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)";
                    strSQL+=" WHERE a1.st_reg='A'";
                    strSQL+=" AND a4.tx_ruc='" + txtIdeCli.getText().replaceAll("'", "''").toLowerCase() + "'";
                    strSQL+=" AND (a3.nd_porRet=0 OR a3.nd_porRet IS NULL)";
                    strSQL+=" AND a4.st_reg IN ('A','R','C','F') AND a3.st_reg IN ('A','C')";
                    strSQL+=" ) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag)";
                    strSQL+=" INNER JOIN tbm_cli AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_cli=b4.co_cli)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_loc=b5.co_loc AND b1.co_tipDoc=b5.co_tipDoc)";
                    strSQL+=" WHERE b1.tx_ruc='" + txtIdeCli.getText().replaceAll("'", "''").toLowerCase() + "'";
                    strSQL+=" AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL)";
                    strSQL+=" AND b1.st_reg IN ('A','R','C','F') AND b2.st_reg IN ('A','C')";
                    strSQL+=" AND b5.ne_mod=1 AND b5.tx_natDoc='E'";
                    strSQL+=strConSQL;
                    strSQL+=" ORDER BY b1.fe_doc, b1.fe_ing, b2.co_reg, b3.fe_pag, b3.fe_ing, b3.co_reg";


                    strSQL+=" ) AS e1";
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.co_reg, COUNT(x.tx_numChq) AS ne_numTotPos FROM(";
                    strSQL+=" 		SELECT a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a7.co_reg, a3.co_pos, a2.tx_numChq";
                    strSQL+=" 		FROM tbm_cabRecDoc AS a1 INNER JOIN (tbm_detRecDoc AS a2";
                    strSQL+=" 		       INNER JOIN tbr_detRecDocpagMovInv AS a6";
                    strSQL+=" 		       ON a2.co_emp=a6.co_emp AND a2.co_loc=a6.co_loc AND a2.co_tipDoc=a6.co_tipDoc";
                    strSQL+=" 		       AND a2.co_doc=a6.co_doc AND a2.co_reg=a6.co_reg";
                    strSQL+=" 		       INNER JOIN tbm_pagMovInv AS a7";
                    strSQL+=" 		       ON a6.co_empRel=a7.co_emp AND a6.co_locRel=a7.co_loc AND a6.co_tipDocRel=a7.co_tipDoc";
                    strSQL+=" 		       AND a6.co_docRel=a7.co_doc AND a6.co_regRel=a7.co_reg";
                    strSQL+=" 		       INNER JOIN tbm_cabMovInv AS a9";
                    strSQL+=" 		       ON a9.co_emp=a7.co_emp AND a9.co_loc=a7.co_loc AND a9.co_tipDoc=a7.co_tipDoc";
                    strSQL+=" 		       AND a9.co_doc=a7.co_doc";
                    strSQL+=" 		)";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN    tbm_posChqRecDoc AS a3";
                    strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                    strSQL+=" 		AND a2.co_reg=a3.co_reg";
                    strSQL+="  		and a9.st_reg not in ('E','I')  and a7.st_reg  in ('A','C')   AND a3.st_solpos IN('A')";
                    strSQL+="  		Group by a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a7.co_reg, a3.co_pos, a2.tx_numChq";
                    strSQL+=" 		ORDER BY a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a3.co_pos";
                    strSQL+=" 	) AS x";
                    strSQL+=" 	group by x.co_emp, x.co_loc, x.co_tipDoc, x.co_reg, x.co_doc";
                    strSQL+=" ) AS e2";
                    strSQL+=" ON e1.co_emp=e2.co_emp AND e1.co_loc=e2.co_loc ";
                    strSQL+=" AND e1.co_tipDoc=e2.co_tipDoc AND e1.co_doc=e2.co_doc AND e1.co_reg=e2.co_reg";
                    strSQL+=" ORDER BY e1.fe_doc, e1.fe_ing, e1.co_reg, e1.fe_pag, e1.fe_ing, e1.co_reg";
                    System.out.println("strSQL: " + strSQL);

                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT e1.co_emp, e1.co_loc, e1.co_tipDoc, e1.co_doc, e1.co_reg, e1.ne_numDoc, e1.fe_doc, e1.fe_ing";
                    strSQL+=" , e1.fe_ven, e1.fe_pag, e1.mo_pag, e1.nd_abo, e1.ne_diaCre, e1.ne_diaPag, e1.ne_diaGra, e2.ne_numTotPos";
                    strSQL+=" FROM(";

                    strSQL+="SELECT b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.co_reg, b1.ne_numDoc, b1.fe_doc, b1.fe_ing,  b2.fe_ven, b3.fe_pag, b2.mo_pag, b3.nd_abo, b2.ne_diaCre, (b3.fe_pag-b1.fe_doc) AS ne_diaPag, b4.ne_diaGra";
                    strSQL+=" FROM tbm_cabMovInv AS b1";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
                    strSQL+=" LEFT OUTER JOIN (";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, a1.fe_doc AS fe_pag, a1.fe_ing, a2.nd_abo";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" RIGHT OUTER JOIN tbm_pagMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg)";
                    strSQL+=" INNER JOIN tbm_cabMovInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    if (!blnUtiCliEmp)
                        strSQL+=" AND a4.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" AND a4.co_cli=" + txtCodCli.getText();
                    strSQL+=" AND (a3.nd_porRet=0 OR a3.nd_porRet IS NULL)";
                    strSQL+=" AND a4.st_reg IN ('A','R','C','F') AND a3.st_reg IN ('A','C')";
                    strSQL+=" ) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag)";
                    strSQL+=" INNER JOIN tbm_cli AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_cli=b4.co_cli)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_loc=b5.co_loc AND b1.co_tipDoc=b5.co_tipDoc)";
                    strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                    if (!blnUtiCliEmp)
                        strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND b1.co_cli=" + txtCodCli.getText();
                    strSQL+=" AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL)";
                    strSQL+=" AND b1.st_reg IN ('A','R','C','F') AND b2.st_reg IN ('A','C')";
                    strSQL+=" AND b5.ne_mod=1 AND b5.tx_natDoc='E'";
                    strSQL+=strConSQL;
                    strSQL+=" ORDER BY b1.fe_doc, b1.fe_ing, b2.co_reg, b3.fe_pag, b3.fe_ing, b3.co_reg";

                    strSQL+=" ) AS e1";
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.co_reg, COUNT(x.tx_numChq) AS ne_numTotPos FROM(";
                    strSQL+=" 		SELECT a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a7.co_reg, a3.co_pos, a2.tx_numChq";
                    strSQL+=" 		FROM tbm_cabRecDoc AS a1 INNER JOIN (tbm_detRecDoc AS a2";
                    strSQL+=" 		       INNER JOIN tbr_detRecDocpagMovInv AS a6";
                    strSQL+=" 		       ON a2.co_emp=a6.co_emp AND a2.co_loc=a6.co_loc AND a2.co_tipDoc=a6.co_tipDoc";
                    strSQL+=" 		       AND a2.co_doc=a6.co_doc AND a2.co_reg=a6.co_reg";
                    strSQL+=" 		       INNER JOIN tbm_pagMovInv AS a7";
                    strSQL+=" 		       ON a6.co_empRel=a7.co_emp AND a6.co_locRel=a7.co_loc AND a6.co_tipDocRel=a7.co_tipDoc";
                    strSQL+=" 		       AND a6.co_docRel=a7.co_doc AND a6.co_regRel=a7.co_reg";
                    strSQL+=" 		       INNER JOIN tbm_cabMovInv AS a9";
                    strSQL+=" 		       ON a9.co_emp=a7.co_emp AND a9.co_loc=a7.co_loc AND a9.co_tipDoc=a7.co_tipDoc";
                    strSQL+=" 		       AND a9.co_doc=a7.co_doc";
                    strSQL+=" 		)";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN    tbm_posChqRecDoc AS a3";
                    strSQL+=" 		ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                    strSQL+=" 		AND a2.co_reg=a3.co_reg";
                    strSQL+="  		and a9.st_reg not in ('E','I')  and a7.st_reg  in ('A','C')   AND a3.st_solpos IN('A')";
                    strSQL+="  		Group by a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a7.co_reg, a3.co_pos, a2.tx_numChq";
                    strSQL+=" 		ORDER BY a9.co_emp, a9.co_loc, a9.co_tipDoc, a9.co_doc, a3.co_pos";
                    strSQL+=" 	) AS x";
                    strSQL+=" 	group by x.co_emp, x.co_loc, x.co_tipDoc, x.co_reg, x.co_doc";
                    strSQL+=" ) AS e2";
                    strSQL+=" ON e1.co_emp=e2.co_emp AND e1.co_loc=e2.co_loc ";
                    strSQL+=" AND e1.co_tipDoc=e2.co_tipDoc AND e1.co_doc=e2.co_doc AND e1.co_reg=e2.co_reg";
                    strSQL+=" ORDER BY e1.fe_doc, e1.fe_ing, e1.co_reg, e1.fe_pag, e1.fe_ing, e1.co_reg";
                    System.out.println("strSQL: " + strSQL);




                    rst=stm.executeQuery(strSQL);
                }
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
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_FEC_PAG,rst.getString("fe_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_ABO_DOC,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_DAT_DIA_PAG,rst.getString("ne_diaPag"));
                        vecReg.add(INT_TBL_DAT_DIA_GRA,rst.getString("ne_diaGra"));
                        vecReg.add(INT_TBL_DAT_NUM_TOT_POS,rst.getString("ne_numTotPos"));
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
                //Calcular totales.
                objTblTot.calcularTotales();
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
        //Validar el "Item".
        if (txtIdeCli.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
            txtDesLarCli.requestFocus();
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
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT ' ' AS co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir";
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.co_emp, MAX(a2.co_cli) AS co_cli, a2.tx_ide";
                strSQL+=" FROM (";
                strSQL+=" SELECT MIN(co_emp) AS co_emp, tx_ide";
                strSQL+=" FROM tbm_cli";
                strSQL+=" GROUP BY tx_ide";
                strSQL+=" ) AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)";
                strSQL+=" GROUP BY a2.co_emp, a2.tx_ide";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)";
                strSQL+=" AND b2.st_cli='S' AND b2.st_reg='A'";
                strSQL+=" ORDER BY b2.tx_nom";
            }
            else
            {
                //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
                if (blnUtiCliEmp)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
            }
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
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(7);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtIdeCli.setText(vcoCli.getValueAt(2));
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
                case 0:
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
                        tabFrm.setSelectedIndex(1);
                        tblDat.setRowSelectionInterval(0, 0);
                        tblDat.requestFocus();
                    }
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
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código del registro";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_FEC_PAG:
                    strMsg="Fecha de pago";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_ABO_DOC:
                    strMsg="Abono";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_DAT_DIA_PAG:
                    strMsg="Días que se demoró el cliente para pagar la deuda";
                    break;
                case INT_TBL_DAT_DIA_GRA:
                    strMsg="Días de gracia";
                    break;
                case INT_TBL_DAT_NUM_TOT_POS:
                    strMsg="Número total de postergaciones";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
