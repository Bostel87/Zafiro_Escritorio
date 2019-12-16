/*
 * ZafVenCon.java
 *
 * Created on 02 de abril de 2006, 20:26 PM
 */

package Librerias.ZafVenCon;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
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
public class ZafVenCon extends javax.swing.JDialog
{
    //Constantes:
    public static final int INT_BUT_CAN=0;              /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;              /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/
    public static final int INT_OPE_Y=0;                /**Un valor para getOperador: Indica "Operador lógico Y".*/
    public static final int INT_OPE_O=1;                /**Un valor para getOperador: Indica "Operador lógico O".*/
    public static final int INT_FOR_GEN=0;              /**Un valor para setConfiguracionColumna: Indica "Formato general".*/
    public static final int INT_FOR_NUM=1;              /**Un valor para setConfiguracionColumna: Indica "Formato numérico".*/
    public static final int INT_FOR_FEC=2;              /**Un valor para setConfiguracionColumna: Indica "Formato de fechas".*/
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
//    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQLAdi, strConSQLFil, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    //Variables de la clase.
    private int intButSelDlg;                           //Botón seleccionado en el JDialog.
    private String strTit;                              //Título del JDialog.
    private String strSenSQL;                           //Sentencia SQL.
    private String strAliCamSenSQL;                     //Alias de los campos de la sentencia SQL.
    private ArrayList arlCam;                           //Campos a mostrar en el JComboBox.
    private ArrayList arlAli;                           //Alias de los campos a mostrar en el JComboBox.
    private ArrayList arlAncCol;                        //Ancho de las columnas.
    private int intColOcu[];                            //Columnas ocultas.
    private boolean blnPgrInd;                          //JProgressBar indeterminado: true=Indeterminado; false=Determinado.
    /*Agregado por Christian Mateo para control en insertar Devoluciones de ventas el tipo de documento sea electronico*/
    public int intModoEje=0;
    /*Agregado por Christian Mateo para control en insertar Devoluciones de ventas el tipo de documento sea electronico*/
    
    /**
     * Este constructor crea una instancia de la clase ZafVenCon.
     * @param padre El formulario padre.
     * @param parametros El objeto ZafParSis.
     * @param titulo El título del JDialog.
     * @param sentenciaSQL La sentencia SQL.
     * @param campos El listado de campos que se mostrará en el JComboBox de campos.
     * @param alias El alias de los campos que se mostrará en el JComboBox de campos.
     * @param anchoColumnas El arreglo que contiene el ancho de las columnas.
     */
    public ZafVenCon(java.awt.Frame padre, ZafParSis parametros, String titulo, String sentenciaSQL, ArrayList campos, ArrayList alias, ArrayList anchoColumnas)
    {
        this(padre, parametros, titulo, sentenciaSQL, campos, alias, anchoColumnas, null);
    }
    
    /**
     * Este constructor crea una instancia de la clase ZafVenCon.
     * @param padre El formulario padre.
     * @param parametros El objeto ZafParSis.
     * @param titulo El título del JDialog.
     * @param sentenciaSQL La sentencia SQL.
     * @param campos El listado de campos que se mostrará en el JComboBox de campos.
     * @param alias El alias de los campos que se mostrará en el JComboBox de campos.
     * @param anchoColumnas El arreglo que contiene el ancho de las columnas.
     * @param columnasOcultas La columnas que se deben ocultar.
     */
    public ZafVenCon(java.awt.Frame padre, ZafParSis parametros, String titulo, String sentenciaSQL, ArrayList campos, ArrayList alias, ArrayList anchoColumnas, int[] columnasOcultas)
    {
        super(padre, true);
        initComponents();
        //Inicializar objetos.
        objParSis=parametros;
        strTit=titulo;
        strSenSQL=sentenciaSQL;
        arlCam=campos;
        arlAli=alias;
        arlAncCol=anchoColumnas;
        intColOcu=columnasOcultas;
        strConSQLAdi="";
        intButSelDlg=INT_BUT_CAN;
        blnPgrInd=true;
        if (!configurarFrm())
            exitForm();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrCri = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblCam = new javax.swing.JLabel();
        cboCam = new javax.swing.JComboBox();
        cboCri1 = new javax.swing.JComboBox();
        txtCri1 = new javax.swing.JTextField();
        optY = new javax.swing.JRadioButton();
        optO = new javax.swing.JRadioButton();
        cboCri2 = new javax.swing.JComboBox();
        txtCri2 = new javax.swing.JTextField();
        lblCri1 = new javax.swing.JLabel();
        lblCri2 = new javax.swing.JLabel();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setLayout(null);

        panGenCab.setPreferredSize(new java.awt.Dimension(10, 84));
        lblCam.setText("Campo:");
        lblCam.setToolTipText("M\u00e1ximo porcentaje de descuento");
        panGenCab.add(lblCam);
        lblCam.setBounds(0, 4, 100, 20);

        panGenCab.add(cboCam);
        cboCam.setBounds(100, 4, 360, 20);

        cboCri1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCri1ActionPerformed(evt);
            }
        });

        panGenCab.add(cboCri1);
        cboCri1.setBounds(100, 24, 180, 20);

        txtCri1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        txtCri1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCri1FocusLost(evt);
            }
        });

        panGenCab.add(txtCri1);
        txtCri1.setBounds(280, 24, 180, 20);

        optY.setSelected(true);
        optY.setText("Y");
        bgrCri.add(optY);
        optY.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panGenCab.add(optY);
        optY.setBounds(100, 44, 180, 20);

        optO.setText("O");
        bgrCri.add(optO);
        optO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panGenCab.add(optO);
        optO.setBounds(280, 44, 180, 20);

        cboCri2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCri2ActionPerformed(evt);
            }
        });

        panGenCab.add(cboCri2);
        cboCri2.setBounds(100, 64, 180, 20);

        txtCri2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCri2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCri2FocusLost(evt);
            }
        });

        panGenCab.add(txtCri2);
        txtCri2.setBounds(280, 64, 180, 20);

        lblCri1.setText("Criterio 1:");
        lblCri1.setToolTipText("M\u00e1ximo porcentaje de descuento");
        panGenCab.add(lblCri1);
        lblCri1.setBounds(0, 24, 100, 20);

        lblCri2.setText("Criterio 2:");
        lblCri2.setToolTipText("M\u00e1ximo porcentaje de descuento");
        panGenCab.add(lblCri2);
        lblCri2.setBounds(0, 64, 100, 20);

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
        butAce.setToolTipText("Acepta la opci\u00f3n seleccionada.");
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

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }//GEN-END:initComponents

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        limpiar();
    }//GEN-LAST:event_butLimActionPerformed

    private void cboCri2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCri2ActionPerformed
        txtCri2FocusLost(null);
    }//GEN-LAST:event_cboCri2ActionPerformed

    private void cboCri1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCri1ActionPerformed
        txtCri1FocusLost(null);
    }//GEN-LAST:event_cboCri1ActionPerformed

    private void txtCri2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri2FocusLost
        //Validar los campos que sólo aceptan valores numéricos.
        switch (cboCri2.getSelectedIndex())
        {
            case 3: //es mayor que
            case 4: //es mayor o igual que
            case 5: //es menor que
            case 6: //es menor o igual que
                if (!objUti.isNumero(txtCri2.getText()))
                {
                    txtCri2.setText("");
                }
                break;
        }
    }//GEN-LAST:event_txtCri2FocusLost

    private void txtCri1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri1FocusLost
        //Validar los campos que sólo aceptan valores numéricos.
        switch (cboCri1.getSelectedIndex())
        {
            case 3: //es mayor que
            case 4: //es mayor o igual que
            case 5: //es menor que
            case 6: //es menor o igual que
                if (!objUti.isNumero(txtCri1.getText()))
                {
                    txtCri1.setText("");
                }
                break;
        }
    }//GEN-LAST:event_txtCri1FocusLost

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
            txtCri1.requestFocus();
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
            evt.consume();
            butAceActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void txtCri2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri2FocusGained
        txtCri2.selectAll();
    }//GEN-LAST:event_txtCri2FocusGained

    private void txtCri1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCri1FocusGained
        txtCri1.selectAll();
    }//GEN-LAST:event_txtCri1FocusGained

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCanActionPerformed

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
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrCri;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLim;
    private javax.swing.JComboBox cboCam;
    private javax.swing.JComboBox cboCri1;
    private javax.swing.JComboBox cboCri2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCam;
    private javax.swing.JLabel lblCri1;
    private javax.swing.JLabel lblCri2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optO;
    private javax.swing.JRadioButton optY;
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
    private javax.swing.JTextField txtCri1;
    private javax.swing.JTextField txtCri2;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        int i;
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle(strTit + " v0.10");
            lblTit.setText(strTit);
            //Configurar objetos.
            for (i=0;i<arlAli.size();i++)
            {
                cboCam.addItem(arlAli.get(i));
            }
            cboCri1.addItem("");
            cboCri1.addItem("es igual a");
            cboCri1.addItem("no es igual a");
            cboCri1.addItem("es mayor que");
            cboCri1.addItem("es mayor o igual que");
            cboCri1.addItem("es menor que");
            cboCri1.addItem("es menor o igual que");
            cboCri1.addItem("comienza por");
            cboCri1.addItem("no comienza por");
            cboCri1.addItem("termina con");
            cboCri1.addItem("no termina con");
            cboCri1.addItem("contiene");
            cboCri1.addItem("no contiene");
            cboCri1.setSelectedIndex(11);
            cboCri2.addItem("");
            cboCri2.addItem("es igual a");
            cboCri2.addItem("no es igual a");
            cboCri2.addItem("es mayor que");
            cboCri2.addItem("es mayor o igual que");
            cboCri2.addItem("es menor que");
            cboCri2.addItem("es menor o igual que");
            cboCri2.addItem("comienza por");
            cboCri2.addItem("no comienza por");
            cboCri2.addItem("termina con");
            cboCri2.addItem("no termina con");
            cboCri2.addItem("contiene");
            cboCri2.addItem("no contiene");
            //Obtener los campos de la cabecera de la sentencia SQL.
            strAliCamSenSQL=remplazarAliTodCam();
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add("");
            for (i=0;i<arlAli.size();i++)
            {
                vecCab.add(arlAli.get(i));
            }
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
            tcmAux.getColumn(0).setPreferredWidth(30);
            for (i=0;i<arlAncCol.size();i++)
            {
                tcmAux.getColumn(i+1).setPreferredWidth(Integer.parseInt(arlAncCol.get(i).toString()));
            }
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            if (intColOcu!=null)
            {
                for (i=0;i<intColOcu.length;i++)
                {
                    tcmAux.getColumn(intColOcu[i]).setWidth(0);
                    tcmAux.getColumn(intColOcu[i]).setMaxWidth(0);
                    tcmAux.getColumn(intColOcu[i]).setMinWidth(0);
                    tcmAux.getColumn(intColOcu[i]).setPreferredWidth(0);
                    tcmAux.getColumn(intColOcu[i]).setResizable(false);
                }
            }
//            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            objMouMotAda=new ZafMouMotAda();
//            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(0).setCellRenderer(objTblFilCab);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Editor de ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            //Configurar JTable: Modo de operación del JTable.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
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
     * Esta función obtiene la sentencia SQL utilizada por la ventana de consulta.
     * @return La sentencia SQL utilizada por la ventana de consulta.
     */
    public String getSentenciaSQL()
    {
        return strSenSQL;
    }
    
    /**
     * Esta función establece la sentencia SQL utilizada por la ventana de consulta.
     * @param sentenciaSQL La sentencia SQL utilizada por la ventana de consulta.
     */
    public void setSentenciaSQL(String sentenciaSQL)
    {
        strSenSQL=sentenciaSQL;
    }
    
    /**
     * Esta función obtiene los campos utilizados por la ventana de consulta.
     * @return Los campos utilizados por la ventana de consulta.
     */
    public ArrayList getCampos()
    {
        return arlCam;
    }
    
    /**
     * Esta función establece los campos utilizados por la ventana de consulta.
     * @param campos Los campos utilizados por la ventana de consulta.
     */
    public void setCampos(ArrayList campos)
    {
        arlCam=campos;
    }
    
    /**
     * Esta función obtiene el alias de los campos utilizado como cabecera
     * de las columnas de la ventana de consulta.
     * @return El alias de los campos utilizados por la ventana de consulta.
     */
    public ArrayList getAliasCampos()
    {
        return arlAli;
    }
    
    /**
     * Esta función establece el alias de los campos utilizado como cabecera
     * de las columnas de la ventana de consulta.
     * @param campos El alias de los campos utilizados por la ventana de consulta.
     */
    public void setAliasCampos(ArrayList alias)
    {
        arlAli=alias;
    }
    
    /**
     * Esta función obtiene el indice de las columnas ocultas de la ventana de consulta.
     * @return El arreglo que contiene el indice de las columnas ocultas.
     */
    public int[] getColumnasOcultas()
    {
        return intColOcu;
    }
    
    /**
     * Esta función establece el indice de las columnas ocultas de la ventana de consulta.
     * @param columnas El arreglo que contiene el indice de las columnas ocultas.
     */
    public void setColumnasOcultas(int columnas[])
    {
        int i, intCol;
        intColOcu=columnas;
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        for (i=0;i<intColOcu.length;i++)
        {
            intCol=intColOcu[i];
            tcmAux.getColumn(intCol).setWidth(0);
            tcmAux.getColumn(intCol).setMaxWidth(0);
            tcmAux.getColumn(intCol).setMinWidth(0);
            tcmAux.getColumn(intCol).setPreferredWidth(0);
            tcmAux.getColumn(intCol).setResizable(false);
        }
        tcmAux=null;
    }
    
    /**
     * Esta función obtiene el indice del campo por el cual se va a efectuar la búsqueda.
     * El campo de búsqueda es una condición adicional que se agrega a la sentencia
     * SQL ingresada previamente.
     * @return El indice del campo por el cual se va a efectuar la búsqueda.
     */
    public int getCampoBusqueda()
    {
        return cboCam.getSelectedIndex();
    }
    
    /**
     * Esta función establece el indice del campo por el cual se va a efectuar la búsqueda.
     * El campo de búsqueda es una condición adicional que se agrega a la sentencia
     * SQL ingresada previamente.
     * @param indice El indice del campo por el cual se va a efectuar la búsqueda.
     */
    public void setCampoBusqueda(int indice)
    {
        if (indice>=0 && indice<cboCam.getItemCount())
            cboCam.setSelectedIndex(indice);
    }

    /**
     * Esta función obtiene el texto del campo de búsqueda seleccionado en el JComboBox "Campo".
     * @return El texto del campo de búsqueda seleccionado en el JComboBox.
     */
    public String getTextoCampoBusqueda()
    {
        return arlCam.get(cboCam.getSelectedIndex()).toString();
    }
    
    /**
     * Esta función obtiene el indice del primer criterio por el cual se va a efectuar la búsqueda.
     * @return El indice del primer criterio utilizado para efectuar la búsqueda.
     * <BR>Puede retornar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Indice</I></TD><TD><I>Descripción</I></TD></TR>
     *     <TR><TD>0</TD><TD></TD></TR>
     *     <TR><TD>1</TD><TD>es igual a</TD></TR>
     *     <TR><TD>2</TD><TD>no es igual a</TD></TR>
     *     <TR><TD>3</TD><TD>es mayor que</TD></TR>
     *     <TR><TD>4</TD><TD>es mayor o igual que</TD></TR>
     *     <TR><TD>5</TD><TD>es menor que</TD></TR>
     *     <TR><TD>6</TD><TD>es menor o igual que</TD></TR>
     *     <TR><TD>7</TD><TD>comienza por</TD></TR>
     *     <TR><TD>8</TD><TD>no comienza por</TD></TR>
     *     <TR><TD>9</TD><TD>termina con</TD></TR>
     *     <TR><TD>10</TD><TD>no termina con</TD></TR>
     *     <TR><TD>11</TD><TD>contiene</TD></TR>
     *     <TR><TD>12</TD><TD>no contiene</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public int getCriterio1()
    {
        return cboCri1.getSelectedIndex();
    }
    
    /**
     * Esta función establece el indice del primer criterio por el cual se va a efectuar la búsqueda.
     * @param indice El indice del primer criterio utilizado para efectuar la búsqueda.
     * <BR>Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Indice</I></TD><TD><I>Descripción</I></TD></TR>
     *     <TR><TD>0</TD><TD></TD></TR>
     *     <TR><TD>1</TD><TD>es igual a</TD></TR>
     *     <TR><TD>2</TD><TD>no es igual a</TD></TR>
     *     <TR><TD>3</TD><TD>es mayor que</TD></TR>
     *     <TR><TD>4</TD><TD>es mayor o igual que</TD></TR>
     *     <TR><TD>5</TD><TD>es menor que</TD></TR>
     *     <TR><TD>6</TD><TD>es menor o igual que</TD></TR>
     *     <TR><TD>7</TD><TD>comienza por</TD></TR>
     *     <TR><TD>8</TD><TD>no comienza por</TD></TR>
     *     <TR><TD>9</TD><TD>termina con</TD></TR>
     *     <TR><TD>10</TD><TD>no termina con</TD></TR>
     *     <TR><TD>11</TD><TD>contiene</TD></TR>
     *     <TR><TD>12</TD><TD>no contiene</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public void setCriterio1(int indice)
    {
        if (indice>=0 && indice<cboCri1.getItemCount())
            cboCri1.setSelectedIndex(indice);
    }
    
    /**
     * Esta función obtiene el indice del segundo criterio por el cual se va a efectuar la búsqueda.
     * @return El indice del segundo criterio utilizado para efectuar la búsqueda.
     * <BR>Puede retornar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Indice</I></TD><TD><I>Descripción</I></TD></TR>
     *     <TR><TD>0</TD><TD></TD></TR>
     *     <TR><TD>1</TD><TD>es igual a</TD></TR>
     *     <TR><TD>2</TD><TD>no es igual a</TD></TR>
     *     <TR><TD>3</TD><TD>es mayor que</TD></TR>
     *     <TR><TD>4</TD><TD>es mayor o igual que</TD></TR>
     *     <TR><TD>5</TD><TD>es menor que</TD></TR>
     *     <TR><TD>6</TD><TD>es menor o igual que</TD></TR>
     *     <TR><TD>7</TD><TD>comienza por</TD></TR>
     *     <TR><TD>8</TD><TD>no comienza por</TD></TR>
     *     <TR><TD>9</TD><TD>termina con</TD></TR>
     *     <TR><TD>10</TD><TD>no termina con</TD></TR>
     *     <TR><TD>11</TD><TD>contiene</TD></TR>
     *     <TR><TD>12</TD><TD>no contiene</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public int getCriterio2()
    {
        return cboCri2.getSelectedIndex();
    }
    
    /**
     * Esta función establece el indice del segundo criterio por el cual se va a efectuar la búsqueda.
     * @param indice El indice del segundo criterio utilizado para efectuar la búsqueda.
     * <BR>Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Indice</I></TD><TD><I>Descripción</I></TD></TR>
     *     <TR><TD>0</TD><TD></TD></TR>
     *     <TR><TD>1</TD><TD>es igual a</TD></TR>
     *     <TR><TD>2</TD><TD>no es igual a</TD></TR>
     *     <TR><TD>3</TD><TD>es mayor que</TD></TR>
     *     <TR><TD>4</TD><TD>es mayor o igual que</TD></TR>
     *     <TR><TD>5</TD><TD>es menor que</TD></TR>
     *     <TR><TD>6</TD><TD>es menor o igual que</TD></TR>
     *     <TR><TD>7</TD><TD>comienza por</TD></TR>
     *     <TR><TD>8</TD><TD>no comienza por</TD></TR>
     *     <TR><TD>9</TD><TD>termina con</TD></TR>
     *     <TR><TD>10</TD><TD>no termina con</TD></TR>
     *     <TR><TD>11</TD><TD>contiene</TD></TR>
     *     <TR><TD>12</TD><TD>no contiene</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public void setCriterio2(int indice)
    {
        if (indice>=0 && indice<cboCri2.getItemCount())
            cboCri2.setSelectedIndex(indice);
    }
    
    /**
     * Esta función obtiene el operador lógico utilizado para efectuar la búsqueda.
     * @return El operador lógico utilizado para efectuar la búsqueda.
     * <BR>Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>0: Operador Y (INT_OPE_Y).
     * <LI>1: Operador O (INT_OPE_O).
     * </UL>
     */
    public int getOperador()
    {
        if (optY.isSelected())
            return INT_OPE_Y;
        else
            return INT_OPE_O;
    }
    
    /**
     * Esta función establece el operador lógico utilizado para efectuar la búsqueda.
     * @param operador El operador lógico utilizado para efectuar la búsqueda.
     * <BR>Puede recibir uno de los siguientes valores:
     * <UL>
     * <LI>0: Operador Y (INT_OPE_Y).
     * <LI>1: Operador O (INT_OPE_O).
     * </UL>
     */
    public void setOperador(int operador)
    {
        if (operador==INT_OPE_Y)
            optY.setSelected(true);
        else
            optO.setSelected(true);
    }
    
    /**
     * Esta función obtiene el valor del primer criterio utilizado para efectuar la búsqueda.
     * @return El valor del primer criterio utilizado para efectuar la búsqueda.
     * <BR>Nota.- No es necesario validar caracteres especiales como la comilla simple o
     * convertir el campo y su valor a minúscula porque la clase ya se encarga de eso.
     */
    public String getValorCriterio1()
    {
        return txtCri1.getText();
    }
    
    /**
     * Esta función establece el valor del primer criterio utilizado para efectuar la búsqueda.
     * @param valor El valor del primer criterio utilizado para efectuar la búsqueda.
     * <BR>Nota.- No es necesario validar caracteres especiales como la comilla simple o
     * convertir el campo y su valor a minúscula porque la clase ya se encarga de eso.
     */
    public void setValorCriterio1(String valor)
    {
        txtCri1.setText(valor);
    }
    
    /**
     * Esta función obtiene el valor del segundo criterio utilizado para efectuar la búsqueda.
     * @return El valor del primer criterio utilizado para efectuar la búsqueda.
     * <BR>Nota.- No es necesario validar caracteres especiales como la comilla simple o
     * convertir el campo y su valor a minúscula porque la clase ya se encarga de eso.
     */
    public String getValorCriterio2()
    {
        return txtCri2.getText();
    }
    
    /**
     * Esta función establece el valor del segundo criterio utilizado para efectuar la búsqueda.
     * @param valor El valor del segundo criterio utilizado para efectuar la búsqueda.
     * <BR>Nota.- No es necesario validar caracteres especiales como la comilla simple o
     * convertir el campo y su valor a minúscula porque la clase ya se encarga de eso.
     */
    public void setValorCriterio2(String valor)
    {
        txtCri2.setText(valor);
    }

    /**
     * Esta función establece la configuración de la columna especificada.
     * @param columna La columna que se desea configurar.
     * @param alineamiento El alineamiento horizontal de la columna.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: CENTER
     * <LI>2: LEFT.
     * <LI>4: RIGHT.
     * <LI>10: LEADING.
     * <LI>11: TRAILING.
     * </UL>
     * Si lo desea puede utilizar las constantes de javax.swing.SwingConstants o de javax.swing.JLabel.
     */
    public void setConfiguracionColumna(int columna, int alineamiento)
    {
        setConfiguracionColumna(columna, alineamiento, INT_FOR_GEN, null, true, false);
    }
    
    /**
     * Esta función establece la configuración de la columna especificada.
     * @param columna La columna que se desea configurar.
     * @param alineamiento El alineamiento horizontal de la columna.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: CENTER
     * <LI>2: LEFT.
     * <LI>4: RIGHT.
     * <LI>10: LEADING.
     * <LI>11: TRAILING.
     * </UL>
     * Si lo desea puede utilizar las constantes de javax.swing.SwingConstants o de javax.swing.JLabel.
     * @param tipoFormato El tipo de formato de la columna.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Formato general (INT_FOR_GEN).
     * <LI>1: Formato numérico (INT_FOR_NUM).
     * <LI>2: Formato de fechas (INT_FOR_FEC).
     * </UL>
     * @param formato El formato que se debe aplicar a la column. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     */
    public void setConfiguracionColumna(int columna, int alineamiento, int tipoFormato, String formato)
    {
        setConfiguracionColumna(columna, alineamiento, tipoFormato, formato, true, false);
    }
    
    /**
     * Esta función establece la configuración de la columna especificada.
     * @param columna La columna que se desea configurar.
     * @param alineamiento El alineamiento horizontal de la columna.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: CENTER
     * <LI>2: LEFT.
     * <LI>4: RIGHT.
     * <LI>10: LEADING.
     * <LI>11: TRAILING.
     * </UL>
     * Si lo desea puede utilizar las constantes de javax.swing.SwingConstants o de javax.swing.JLabel.
     * @param tipoFormato El tipo de formato de la columna.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Formato general (INT_FOR_GEN).
     * <LI>1: Formato numérico (INT_FOR_NUM).
     * <LI>2: Formato de fechas (INT_FOR_FEC).
     * </UL>
     * @param formato El formato que se debe aplicar a la column. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     * @param mostrarCero Se aplica si el número a formatear es un cero.
     * Puede tomar los siguientes valores:
     * <UL>
     * <LI>true: Se mostrará el cero formateado.
     * <LI>false: Se mostrará una cadena vacía.
     * </UL>
     * @param negativoRojo Se aplica si el número a formatear es negativo.
     * Puede tomar los siguientes valores:
     * <UL>
     * <LI>true: Se mostrará el número de color rojo.
     * <LI>false: Se mostrará el número de color normal.
     * </UL>
     */
    public void setConfiguracionColumna(int columna, int alineamiento, int tipoFormato, String formato, boolean mostrarCero, boolean negativoRojo)
    {
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(alineamiento);
        objTblCelRenLbl.setTipoFormato(tipoFormato);
        objTblCelRenLbl.setFormatoNumerico(formato,mostrarCero,negativoRojo);
        tcmAux.getColumn(columna).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;
        tcmAux=null;
    }

    /**
     * Esta función carga los datos de acuerdo a la configuración que tiene la ventana de consulta.
     * <BR>Se puede haber configurado la ventana de consulta de la siguiente forma:
     * <UL>
     * <LI>Campo: tx_nomCli
     * <LI>Criterio 1: comienza por
     * <LI>Valor del criterio 1: A
     * </UL>
     * En cuyo caso la ventana de consulta cargará todos los clientes cuyo nombre comience con la letra "A".
     */
    public void cargarDatos()
    {
        butConActionPerformed(null);
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    public boolean buscar(String campo, String valor)
    {
        int j;
        int intPosClaOrd;   //Posición de la clausula " order by ".
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                txtCri1.setText(valor);
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT " + strAliCamSenSQL;
                strSQL+=" FROM (";
                //Insertar la condición adicional a la sentencia SQL base.
                intPosClaOrd=strSenSQL.toLowerCase().lastIndexOf(" order by ");
                if (intPosClaOrd==-1)
                {
                    strSQL+=strSenSQL + strConSQLAdi;
                }
                else
                {
                    strSQL+=strSenSQL.substring(0, intPosClaOrd) + strConSQLAdi + strSenSQL.substring(intPosClaOrd);
                }
                strSQL+=" ) AS zaf";
                strSQL+=" WHERE LOWER(CAST(" + remplazarAliCam(campo) + " AS VARCHAR)) LIKE '" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "'";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                if (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(0,"");
                    for (j=1;j<=arlCam.size();j++)
                        vecReg.add(j,rst.getString(j));
                    vecDat.add(vecReg);
                }
                else
                {
                    blnRes=false;
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
                //Seleccionar la primera fila en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0)
                {
                    tblDat.setRowSelectionInterval(0, 0);
                }
                lblMsgSis.setText("Listo");
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
     * Esta función obtiene el valor que se encuentra en la posición especificada
     * en la fila que seleccionó el usuario en el JDialog.
     * @param col La columna que se desea obtener.
     * @return El valor que se encuentra en la posición especificada.
     */
    public String getValueAt(int col)
    {
        int intFilSel;
        intFilSel=tblDat.getSelectedRow();
        if (intFilSel!=-1)
            return objUti.parseString(objTblMod.getValueAt(intFilSel, col));
        else
            return "";
    }
    
    /**
     * Esta función determina si la barra de progreso que se presenta en la esquina inferior
     * derecha de la ventana de consulta se comporta como una barra de progreso indeterminada.
     * De manera predeterminada se comporta como una barra de progreso indeterminada.
     * @return true: Si la barra de progreso se comporta como una barra indeterminada.
     * <BR>false: En el caso contrario.
     */
    public boolean isBarraProgresoIndeterminada()
    {
        return blnPgrInd;
    }
    
    /**
     * Esta función establece si la barra de progreso que se presenta en la esquina inferior
     * derecha de la ventana de consulta se comporta como una barra de progreso indeterminada.
     * De manera predeterminada se comporta como una barra de progreso indeterminada.
     * @param indeterminada Valor booleano que determina si la barra de progreso se comporta 
     * como una barra indeterminada. 
     */
    public void setBarraProgresoIndeterminada(boolean indeterminada)
    {
        blnPgrInd=indeterminada;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumTotReg, i, j;
        int intPosClaOrd;   //Posición de la clausula " order by ".
        String strCam="", strOpeLog="";
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQLFil="";
                if (!txtCri1.getText().equals(""))
                {
                    strCam=remplazarAliCam(arlCam.get(cboCam.getSelectedIndex()).toString());
                    switch (cboCri1.getSelectedIndex())
                    {
                        case 1: //es igual a
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 2: //no es igual a
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 3: //es mayor que
                            strConSQLFil+=" WHERE " + strCam + ">" + txtCri1.getText().replaceAll("'", "''");
                            break;
                        case 4: //es mayor o igual que
                            strConSQLFil+=" WHERE " + strCam + ">=" + txtCri1.getText().replaceAll("'", "''");
                            break;
                        case 5: //es menor que
                            strConSQLFil+=" WHERE " + strCam + "<" + txtCri1.getText().replaceAll("'", "''");
                            break;
                        case 6: //es menor o igual que
                            strConSQLFil+=" WHERE " + strCam + "<=" + txtCri1.getText().replaceAll("'", "''");
                            break;
                        case 7: //comienza por
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                        case 8: //no comienza por
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                        case 9: //termina con
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '%" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 10: //no termina con
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '%" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 11: //contiene
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '%" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                        case 12: //no contiene
                            strConSQLFil+=" WHERE LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '%" + txtCri1.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                    }
                }
                if (!txtCri2.getText().equals(""))
                {
                    strOpeLog=optY.isSelected()?"AND":"OR";
                    switch (cboCri2.getSelectedIndex())
                    {
                        case 1: //es igual a
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 2: //no es igual a
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 3: //es mayor que
                            strConSQLFil+=" " + strOpeLog + " " + strCam + ">" + txtCri2.getText().replaceAll("'", "''");
                            break;
                        case 4: //es mayor o igual que
                            strConSQLFil+=" " + strOpeLog + " " + strCam + ">=" + txtCri2.getText().replaceAll("'", "''");
                            break;
                        case 5: //es menor que
                            strConSQLFil+=" " + strOpeLog + " " + strCam + "<" + txtCri2.getText().replaceAll("'", "''");
                            break;
                        case 6: //es menor o igual que
                            strConSQLFil+=" " + strOpeLog + " " + strCam + "<=" + txtCri2.getText().replaceAll("'", "''");
                            break;
                        case 7: //comienza por
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                        case 8: //no comienza por
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                        case 9: //termina con
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '%" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 10: //no termina con
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '%" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "'";
                            break;
                        case 11: //contiene
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) LIKE '%" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                        case 12: //no contiene
                            strConSQLFil+=" " + strOpeLog + " LOWER(CAST(" + strCam + " AS VARCHAR)) NOT LIKE '%" + txtCri2.getText().replaceAll("'", "''").toLowerCase() + "%'";
                            break;
                    }
                }
                if (blnPgrInd)
                {
                    pgrSis.setIndeterminate(true);
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT " + strAliCamSenSQL;
                    strSQL+=" FROM (";
                    /*CMATEO SE AGREGA PARA CONTROLAR EN INSERCION LAS DEVOLUCIONES DE VENTAS SOLO DOCUMENTOS ELECTRONICOS*/
                    if(objParSis.getCodigoMenu()==1918){// cuando sea devolucion de ventas y en modo insertar que solo muestre el tipo de documento default
                        if(intModoEje==5){
                            if(objParSis.getCodigoUsuario()!=1){
                                strSenSQL+=" and a1.st_reg='S'";
                            }
                        }
                    }
                    /*CMATEO SE AGREGA PARA CONTROLAR EN INSERCION LAS DEVOLUCIONES DE VENTAS SOLO DOCUMENTOS ELECTRONICOS*/
                    //Insertar la condición adicional a la sentencia SQL base.
                    intPosClaOrd=strSenSQL.toLowerCase().lastIndexOf(" order by ");
                    if (intPosClaOrd==-1)
                    {
                        strSQL+=strSenSQL + strConSQLAdi;
                    }
                    else
                    {
                        strSQL+=strSenSQL.substring(0, intPosClaOrd) + strConSQLAdi + strSenSQL.substring(intPosClaOrd);
                    }
                    strSQL+=" ) AS zaf";
                    strSQL+=strConSQLFil;
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
                            vecReg.add(0,"");
                            for (j=1;j<=arlCam.size();j++)
                                vecReg.add(j,rst.getString(j));
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
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                    //Habilitar el botón "Aceptar" sólo si hay registros.
                    if (tblDat.getRowCount()>0)
                        butAce.setEnabled(true);
                    else
                        butAce.setEnabled(false);
                    pgrSis.setIndeterminate(false);
                }
                else
                {
                    //Obtener el número total de registros.
                    strSQL="";
                    strSQL+="SELECT COUNT(*)";
                    strSQL+=" FROM (";
                    //Insertar la condición adicional a la sentencia SQL base.
                    intPosClaOrd=strSenSQL.toLowerCase().lastIndexOf(" order by ");
                    if (intPosClaOrd==-1)
                    {
                        strSQL+=strSenSQL + strConSQLAdi;
                    }
                    else
                    {
                        strSQL+=strSenSQL.substring(0, intPosClaOrd) + strConSQLAdi + strSenSQL.substring(intPosClaOrd);
                    }
                    strSQL+=" ) AS zaf";
                    strSQL+=strConSQLFil;
                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT " + strAliCamSenSQL;
                    strSQL+=" FROM (";
                    //Insertar la condición adicional a la sentencia SQL base.
                    if (intPosClaOrd==-1)
                    {
                        strSQL+=strSenSQL + strConSQLAdi;
                    }
                    else
                    {
                        strSQL+=strSenSQL.substring(0, intPosClaOrd) + strConSQLAdi + strSenSQL.substring(intPosClaOrd);
                    }
                    strSQL+=" ) AS zaf";
                    strSQL+=strConSQLFil;
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    lblMsgSis.setText("Cargando datos...");
                    pgrSis.setMinimum(0);
                    pgrSis.setMaximum(intNumTotReg);
                    pgrSis.setValue(0);
                    i=0;
                    while (rst.next())
                    {
                        if (blnCon)
                        {
                            vecReg=new Vector();
                            vecReg.add(0,"");
                            for (j=1;j<=arlCam.size();j++)
                                vecReg.add(j,rst.getString(j));
                            vecDat.add(vecReg);
                            i++;
                            pgrSis.setValue(i);
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
                    if (intNumTotReg==tblDat.getRowCount())
                        lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                    else
                        lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                    //Habilitar el botón "Aceptar" sólo si hay registros.
                    if (tblDat.getRowCount()>0)
                        butAce.setEnabled(true);
                    else
                        butAce.setEnabled(false);
                    pgrSis.setValue(0);
                }
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
     * Esta función remplaza el alias de todos los campos de la sentencia SQL utilizada
     * para obtener los datos que se presentan en la ventana de consulta.
     * @return La cadena que contiene todos los alias reemplazados.
     * <BR>Nota.- El alias utilizado por el sistema es "zaf.".
     */
    private String remplazarAliTodCam()
    {
        int intPos, i;
        String strRes;
        strRes="";
        try
        {
            for (i=0; i<arlCam.size();i++)
            {
                strAux=arlCam.get(i).toString();
                intPos=strAux.indexOf(".");
                if (intPos>0)
                    strAux=strAux.substring(intPos+1);
                if (i==0)
                    strRes+="zaf." + strAux;
                else
                    strRes+=", zaf." + strAux;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    /**
     * Esta función remplaza el alias del campo especificado.
     * @return La cadena que contiene el campo con el nuevo alias.
     * <BR>Nota.- El alias utilizado por el sistema es "zaf.".
     */
    private String remplazarAliCam(String strCam)
    {
        int intPos;
        String strRes;
        try
        {
            intPos=strCam.indexOf(".");
            if (intPos>0)
                strCam=strCam.substring(intPos+1);
            strRes="zaf." + strCam;
        }
        catch (IndexOutOfBoundsException e)
        {
            strRes="";
        }
        return strRes;
    }

    /**
     * Esta función obtiene las condiciones adicionales que se pueden agregar antes
     * de efectuar la consulta a la base de datos. En la mayoría de los casos es
     * suficiente el uso del método "setCampoBusqueda". Pero, hay ocasiones en las
     * que hay que adicionar otras condiciones de manera dinámica.
     * @return Las condiciones adicionales que se agregarán a la sentencia SQL.
     */
    public String getCondicionesSQL()
    {
        return strConSQLAdi;
    }
    
    /**
     * Esta función establece las condiciones adicionales que se pueden agregar antes
     * de efectuar la consulta a la base de datos. En la mayoría de los casos es
     * suficiente el uso del método "setCampoBusqueda". Pero, hay ocasiones en las
     * que hay que adicionar otras condiciones de manera dinámica.
     * <BR>Por ejemplo, podríamoms necesitar que se busque los clientes de acuerdo a
     * la ciudad seleccionada en un JComboBox. Para éste ejemplo la función debería
     * recibir " AND co_ciu=3". Donde "3" representa la ciudad que se encuentra
     * seleccionada en el JComboBox.
     * @param condiciones Las condiciones adicionales que se agregarán a la sentencia SQL.
     */
    public void setCondicionesSQL(String condiciones)
    {
        strConSQLAdi=condiciones;
    }

    /**
     * Esta función limpia la ventana de consulta. Es decir, la ventana de consulta
     * queda como si todavía no se hubiera consultado nada.
     */
    public void limpiar()
    {
        txtCri1.setText("");
        txtCri2.setText("");
        optY.setSelected(true);
        cboCri2.setSelectedIndex(0);
        objTblMod.removeAllRows();
        lblMsgSis.setText("Listo");
        txtCri1.requestFocus();
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
    
}
