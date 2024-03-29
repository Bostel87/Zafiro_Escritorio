/*
 * ZafCon03.java
 *
 * Created on 16 de enero de 2005, 17:10 PM
 */
package Contabilidad.ZafCon10;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCon10 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_SIS=1;                     //Código de la cuenta (Sistema).
    static final int INT_TBL_DAT_COD_ALT=2;                     //Código de la cuenta (Alterno).
    static final int INT_TBL_DAT_COD_CTA=3;                     //Código de la cuenta (Contable).
    static final int INT_TBL_DAT_NOM_CTA=4;                     //Nombre de la cuenta.
    static final int INT_TBL_DAT_NAT_CTA=5;                     //Naturaleza de la cuenta.
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafPopupMenu objPopMnu;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecTipCta, vecNatCta, vecEstReg;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    
    /** Crea una nueva instancia de la clase ZafCon10. */
    public ZafCon10(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
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
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        lblTipCta = new javax.swing.JLabel();
        lblNatCta = new javax.swing.JLabel();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        cboNatCta = new javax.swing.JComboBox();
        cboTipCta = new javax.swing.JComboBox();
        optFil = new javax.swing.JRadioButton();
        panNomCta = new javax.swing.JPanel();
        lblNomCtaDes = new javax.swing.JLabel();
        txtNomCtaDes = new javax.swing.JTextField();
        lblNomCtaHas = new javax.swing.JLabel();
        txtNomCtaHas = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todas las cuentas");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        lblTipCta.setText("Tipo de cuenta:");
        panFil.add(lblTipCta);
        lblTipCta.setBounds(24, 104, 176, 20);

        lblNatCta.setText("Naturaleza de la cuenta:");
        lblNatCta.setToolTipText("Naturaleza de la cuenta:");
        panFil.add(lblNatCta);
        lblNatCta.setBounds(24, 124, 176, 20);

        lblEstReg.setText("Estado del registro:");
        lblEstReg.setToolTipText("Estado del registro:");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(24, 144, 176, 20);

        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });
        panFil.add(cboEstReg);
        cboEstReg.setBounds(200, 144, 180, 20);

        cboNatCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNatCtaActionPerformed(evt);
            }
        });
        panFil.add(cboNatCta);
        cboNatCta.setBounds(200, 124, 180, 20);

        cboTipCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipCtaActionPerformed(evt);
            }
        });
        panFil.add(cboTipCta);
        cboTipCta.setBounds(200, 104, 180, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo las cuentas que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        panNomCta.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de cuenta"));
        panNomCta.setLayout(null);

        lblNomCtaDes.setText("Desde:");
        panNomCta.add(lblNomCtaDes);
        lblNomCtaDes.setBounds(12, 20, 44, 20);

        txtNomCtaDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCtaDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCtaDesFocusLost(evt);
            }
        });
        panNomCta.add(txtNomCtaDes);
        txtNomCtaDes.setBounds(56, 20, 220, 20);

        lblNomCtaHas.setText("Hasta:");
        panNomCta.add(lblNomCtaHas);
        lblNomCtaHas.setBounds(284, 20, 44, 20);

        txtNomCtaHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCtaHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCtaHasFocusLost(evt);
            }
        });
        panNomCta.add(txtNomCtaHas);
        txtNomCtaHas.setBounds(328, 20, 220, 20);

        panFil.add(panNomCta);
        panNomCta.setBounds(24, 44, 560, 52);

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

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

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
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        if (cboEstReg.getSelectedIndex()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void cboNatCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNatCtaActionPerformed
        if (cboNatCta.getSelectedIndex()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_cboNatCtaActionPerformed

    private void cboTipCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipCtaActionPerformed
        if (cboTipCta.getSelectedIndex()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_cboTipCtaActionPerformed

    private void txtNomCtaHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaHasFocusLost
        if (txtNomCtaHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtNomCtaHasFocusLost

    private void txtNomCtaDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaDesFocusLost
        if (txtNomCtaDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCtaHas.getText().length()==0)
                txtNomCtaHas.setText(txtNomCtaDes.getText());
        }
    }//GEN-LAST:event_txtNomCtaDesFocusLost

    private void txtNomCtaHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaHasFocusGained
        txtNomCtaHas.selectAll();
    }//GEN-LAST:event_txtNomCtaHasFocusGained

    private void txtNomCtaDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaDesFocusGained
        txtNomCtaDes.selectAll();
    }//GEN-LAST:event_txtNomCtaDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtNomCtaDes.setText("");
            txtNomCtaHas.setText("");
            cboTipCta.setSelectedIndex(0);
            cboNatCta.setSelectedIndex(0);
            cboEstReg.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
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
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JComboBox cboNatCta;
    private javax.swing.JComboBox cboTipCta;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNatCta;
    private javax.swing.JLabel lblNomCtaDes;
    private javax.swing.JLabel lblNomCtaHas;
    private javax.swing.JLabel lblTipCta;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCta;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtNomCtaDes;
    private javax.swing.JTextField txtNomCtaHas;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.2");
            lblTit.setText(strAux);
            //Configurar el combo "Tipo de cuenta".
            vecTipCta=new Vector();
            vecTipCta.add("");
            vecTipCta.add("C");
            vecTipCta.add("D");
            cboTipCta.addItem("(Todas)");
            cboTipCta.addItem("Cabecera");
            cboTipCta.addItem("Detalle");
            cboTipCta.setSelectedIndex(0);
            //Configurar el combo "Naturaleza de la cuenta".
            vecNatCta=new Vector();
            vecNatCta.add("");
            vecNatCta.add("D");
            vecNatCta.add("H");
            cboNatCta.addItem("(Todas)");
            cboNatCta.addItem("Debe");
            cboNatCta.addItem("Haber");
            cboNatCta.setSelectedIndex(0);
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("Activo");
            cboEstReg.addItem("Inactivo");
            cboEstReg.setSelectedIndex(0);            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(5);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Código");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Alterno");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre");
            vecCab.add(INT_TBL_DAT_NAT_CTA,"Naturaleza");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
//            objPopMnu=new ZafPopupMenu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(60);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(100);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(350);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_SIS).setResizable(false);
            //Configurar JTable: Centrar columnas.
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
        String strNiv;
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                if (txtNomCtaDes.getText().length()>0 || txtNomCtaHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a1.tx_desLar) BETWEEN '" + txtNomCtaDes.getText().replaceAll("'", "''") + "' AND '" + txtNomCtaHas.getText().replaceAll("'", "''") + "') OR LOWER(a1.tx_desLar) LIKE '" + txtNomCtaHas.getText().replaceAll("'", "''") + "%')";
                if (cboTipCta.getSelectedIndex()>0)
                    strAux+=" AND a1.tx_tipCta='" + vecTipCta.get(cboTipCta.getSelectedIndex()) + "'";
                if (cboNatCta.getSelectedIndex()>0)
                    strAux+=" AND a1.tx_natCta='" + vecNatCta.get(cboNatCta.getSelectedIndex()) + "'";
                if (cboEstReg.getSelectedIndex()>0)
                    strAux+=" AND a1.st_reg='" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codAlt, a1.tx_codCta, a1.tx_desLar, a1.tx_natCta";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=strAux;
                strSQL+=" ORDER BY a1.tx_codCta";
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
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_NAT_CTA,rst.getString("tx_natCta"));
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
    
    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if (!((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow()==-1)))
            {
                strAux="Contabilidad.ZafCon01.ZafCon01";
                objParSis.setNombreMenu("Plan de cuentas...");
                if (!strAux.equals(""))
                    invocarClase(strAux);
            }       
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean invocarClase(String clase)
    {
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[2];
            objCla[0]=objParSis.getClass();
            objCla[1]=new Integer(0).getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[2];
            objObj[0]=objParSis;
            objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SIS).toString());
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.show();
        }
        catch (ClassNotFoundException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
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
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
}


