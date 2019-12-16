/*
 * ZafMae03.java
 *
 * Created on March 11, 2010, 11:47 PM
 */
package Compras.ZafCom72;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
/**
 *
 * @author  Gigi
 */
public class ZafCom72 extends javax.swing.JInternalFrame 
{
    //Constantes: Datos Arancel
    final int INT_TBL_DAT_ARA_LIN=0;
    final int INT_TBL_DAT_ARA_POR_ARA=1;
    final int INT_TBL_DAT_ARA_FEC_DES=2;
    final int INT_TBL_DAT_ARA_FEC_HAS=3;
        
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_PAR_ARA=0;  
    private int intIndiceCon=0;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblFilCab objTblFilCab;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoParAra;
    private MiToolBar objTooBar;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private java.util.Date datFecAux;
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnHayCam;
    private String strSQL, strAux;

    /** Creates new form ZafCom72 */
    public ZafCom72(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            vecDat=new Vector();

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                configurarFrm();
                agregarDocLis();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.2");
            lblTit.setText(strAux);
            txtCodParAra.setBackground(objParSis.getColorCamposSistema());
            txtDesParAra.setBackground(objParSis.getColorCamposObligatorios());

            configurarVenConParAra();
            configurarTblDatAra();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /** Configurar el Tabla de Arancel. */
    private boolean configurarTblDatAra() {
        boolean blnRes = true;
        try {
            //Inicializar objetos.
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(4);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_ARA_LIN, "");
            vecCab.add(INT_TBL_DAT_ARA_POR_ARA, "%");
            vecCab.add(INT_TBL_DAT_ARA_FEC_DES, "Desde");
            vecCab.add(INT_TBL_DAT_ARA_FEC_HAS, "Hasta");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatAra.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDatAra.setRowSelectionAllowed(true);
            tblDatAra.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatAra);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatAra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatAra.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_ARA_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ARA_POR_ARA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ARA_FEC_DES).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_ARA_FEC_HAS).setPreferredWidth(90);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatAra.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatAra);
            tcmAux.getColumn(INT_TBL_DAT_ARA_LIN).setCellRenderer(objTblFilCab);

            objTblBus=new ZafTblBus(tblDatAra);
            objTblOrd=new ZafTblOrd(tblDatAra);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatAra.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_ARA_POR_ARA);
            vecAux.add("" + INT_TBL_DAT_ARA_FEC_DES);
            vecAux.add("" + INT_TBL_DAT_ARA_FEC_HAS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_ARA_POR_ARA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ARA_FEC_DES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ARA_FEC_HAS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDatAra);
            tcmAux.getColumn(INT_TBL_DAT_ARA_FEC_DES).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_ARA_FEC_HAS).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt=null;

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDatAra);
            tcmAux.getColumn(INT_TBL_DAT_ARA_POR_ARA).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                    objTblMod.removeEmptyRows();
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                }
            });

            //Liberar objeto.
            tcmAux=null;

        } catch (Exception e) {
            blnRes = false;
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
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDatAra.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_ARA_POR_ARA:
                    strMsg = "% Arancel";
                    break;
                case INT_TBL_DAT_ARA_FEC_DES:
                    strMsg = "Fecha de vigencia desde(dd/MM/yyyy)";
                    break;
                case INT_TBL_DAT_ARA_FEC_HAS:
                    strMsg = "Fecha de vigencia hasta(dd/MM/yyyy)";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDatAra.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Partidas Arancelarias".
     */
    private boolean configurarVenConParAra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_parara");
            arlCam.add("a1.tx_par");
            arlCam.add("a1.tx_des");
            arlCam.add("a1.tx_destna");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Descripción");
            arlAli.add("Descripcion TNAN");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("100");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_parara, a1.tx_par, a1.tx_des, a1.tx_destna";
            strSQL+=" FROM tbm_pararaimp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_parara";
            
            vcoParAra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de grupo padre", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoParAra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConParAra(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoParAra.setCampoBusqueda(1);
                    vcoParAra.show();
                    if (vcoParAra.getSelectedButton() == vcoParAra.INT_BUT_ACE) {
                        txtCodParAra.setText(vcoParAra.getValueAt(1));
                        txtNomParAra.setText(vcoParAra.getValueAt(2));
                        txtDesParAra.setText(vcoParAra.getValueAt(3));
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFrmFil = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        lblCodParAra = new javax.swing.JLabel();
        txtCodParAra = new javax.swing.JTextField();
        butCodParAra = new javax.swing.JButton();
        lblNomParAra = new javax.swing.JLabel();
        txtNomParAra = new javax.swing.JTextField();
        lblCosISD = new javax.swing.JLabel();
        lblDesParAra = new javax.swing.JLabel();
        txtDesParAra = new javax.swing.JTextField();
        lblDesTna = new javax.swing.JLabel();
        spnDesTna = new javax.swing.JScrollPane();
        txaDesTna = new javax.swing.JTextArea();
        lblObs1 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        optCosSi = new javax.swing.JRadioButton();
        optCosNo = new javax.swing.JRadioButton();
        panAra = new javax.swing.JPanel();
        spnDatAra = new javax.swing.JScrollPane();
        tblDatAra = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panFrmFil.setLayout(new java.awt.BorderLayout());

        panFil.setLayout(null);

        lblCodParAra.setText("Código:");
        panFil.add(lblCodParAra);
        lblCodParAra.setBounds(10, 6, 110, 20);
        panFil.add(txtCodParAra);
        txtCodParAra.setBounds(160, 6, 90, 20);

        butCodParAra.setText("...");
        butCodParAra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodParAraActionPerformed(evt);
            }
        });
        panFil.add(butCodParAra);
        butCodParAra.setBounds(250, 5, 22, 23);

        lblNomParAra.setText("Partida:");
        panFil.add(lblNomParAra);
        lblNomParAra.setBounds(10, 28, 140, 20);
        panFil.add(txtNomParAra);
        txtNomParAra.setBounds(160, 28, 140, 20);

        lblCosISD.setText("ISD (Costo):");
        panFil.add(lblCosISD);
        lblCosISD.setBounds(340, 28, 100, 20);

        lblDesParAra.setText("Descripción:");
        panFil.add(lblDesParAra);
        lblDesParAra.setBounds(10, 50, 130, 20);
        panFil.add(txtDesParAra);
        txtDesParAra.setBounds(160, 50, 400, 38);

        lblDesTna.setText("Descripción TNAN:");
        panFil.add(lblDesTna);
        lblDesTna.setBounds(10, 90, 150, 20);

        txaDesTna.setLineWrap(true);
        spnDesTna.setViewportView(txaDesTna);

        panFil.add(spnDesTna);
        spnDesTna.setBounds(160, 90, 400, 40);

        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación1:");
        panFil.add(lblObs1);
        lblObs1.setBounds(10, 132, 100, 20);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panFil.add(spnObs1);
        spnObs1.setBounds(160, 132, 400, 110);

        optCosSi.setText("Si");
        optCosSi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optCosSiItemStateChanged(evt);
            }
        });
        panFil.add(optCosSi);
        optCosSi.setBounds(445, 28, 50, 20);

        optCosNo.setText("No");
        optCosNo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optCosNoItemStateChanged(evt);
            }
        });
        panFil.add(optCosNo);
        optCosNo.setBounds(510, 28, 50, 20);

        panFrmFil.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panFrmFil);

        panAra.setLayout(new java.awt.BorderLayout());

        tblDatAra.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatAra.setViewportView(tblDatAra);

        panAra.add(spnDatAra, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Arancel", panAra);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(0, 70));
        panBar.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
}//GEN-LAST:event_exitForm

private void butCodParAraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodParAraActionPerformed
    mostrarVenConParAra(0);
}//GEN-LAST:event_butCodParAraActionPerformed

    private void optCosSiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optCosSiItemStateChanged
        if (optCosSi.isSelected())
            optCosNo.setSelected(false);
    }//GEN-LAST:event_optCosSiItemStateChanged

    private void optCosNoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optCosNoItemStateChanged
        if (optCosNo.isSelected())
            optCosSi.setSelected(false);
    }//GEN-LAST:event_optCosNoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCodParAra;
    private javax.swing.JLabel lblCodParAra;
    private javax.swing.JLabel lblCosISD;
    private javax.swing.JLabel lblDesParAra;
    private javax.swing.JLabel lblDesTna;
    private javax.swing.JLabel lblNomParAra;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optCosNo;
    private javax.swing.JRadioButton optCosSi;
    private javax.swing.JPanel panAra;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panFrmFil;
    private javax.swing.JScrollPane spnDatAra;
    private javax.swing.JScrollPane spnDesTna;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatAra;
    private javax.swing.JTextArea txaDesTna;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCodParAra;
    private javax.swing.JTextField txtDesParAra;
    private javax.swing.JTextField txtNomParAra;
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodParAra.setText("");
            txtNomParAra.setText("");
            txtDesParAra.setText("");
            txaDesTna.setText("");
            txaObs1.setText("");
            optCosSi.setSelected(false);
            optCosNo.setSelected(false);
            objTblMod.removeAllRows();
        }
        catch (Exception e){
            blnRes=false;
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
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
        txtCodParAra.getDocument().addDocumentListener(objDocLis);
        txtNomParAra.getDocument().addDocumentListener(objDocLis);
        txtDesParAra.getDocument().addDocumentListener(objDocLis);
    } 

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if (txtDesParAra.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Descripción larga</FONT> es obligatorio.<BR>Escriba una descripción larga y vuelva a intentarlo.</HTML>");
            txtDesParAra.requestFocus();
            return false;
        }
                
        if ( !(optCosSi.isSelected() || optCosNo.isSelected())  ){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">ISD(Costo)</FONT> es obligatorio.<BR>Seleccione una opción y vuelva a intentarlo.</HTML>");
            optCosSi.requestFocus();
            return false;
        }
        
        return true;
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

        public void clickInicio() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickAnterior() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            try{
                if (blnHayCam){
                    isRegPro();
                }
                limpiarFrm();
                txtCodParAra.setEditable(false);
                txtNomParAra.selectAll();
                txtNomParAra.requestFocus();

                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

                //Inicializar las variables que indican cambios.
                blnHayCam=false;                
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {
        }

        public void clickModificar() {
            txtCodParAra.setEditable(false);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            cargarReg();
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarTabGen();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceCon<arlDatCon.size()-1){
                    intIndiceCon++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }                
            return true;
        }

        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
                        
            if (!isCamVal())
                blnRes=false;
            
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
//            if (strAux.equals("Anulado")){
//                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
//                blnRes=false;
//            }
            return blnRes;
        }

        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El grupo está ELIMINADO.\nNo es posible anular un grupo eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El grupo ya está ANULADO.\nNo es posible anular un grupo anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            consultarReg();
            objTooBar.setEstado('w');
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            this.setEstado('w');
            cargarReg();
            objTooBar.setEstado('w');
            blnHayCam=false;
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        

        
    }


    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                strSQL="";
                strSQL+=" SELECT a1.co_parAra";
                strSQL+=" FROM tbm_pararaimp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E')";

                strAux=txtCodParAra.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_parAra=" + strAux.replaceAll("'", "''") + "";

                strAux=txtNomParAra.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND LOWER(a1.tx_par) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

                strAux=txtDesParAra.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND LOWER(a1.tx_des) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

                strAux=txaDesTna.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND LOWER(a1.tx_destna) LIKE '" + strAux.replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";

                strSQL+=" ORDER BY a1.co_parara";
                
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_PAR_ARA, rst.getInt("co_parara"));
                    arlDatCon.add(arlRegCon);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarTabGen()){
                if (cargarTabAra()){
                    blnRes=true;
                }
            }
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabGen(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_parara, tx_par, tx_des, tx_destna, tx_obs1, st_reg, fe_ing";
                strSQL+="      , fe_ultmod, co_usring, co_usrmod, st_isdCos";
                strSQL+=" FROM tbm_pararaimp AS a1";
                strSQL+=" WHERE a1.co_parara=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_PAR_ARA);
                strSQL+=" AND a1.st_reg NOT IN('E')";
                System.out.println("cargarTabGen: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_parara");
                    txtCodParAra.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_par");
                    txtNomParAra.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_des");
                    txtDesParAra.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_destna");
                    txaDesTna.setText((strAux==null)?"":strAux);

                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));

                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    
                    //Costo ISD
                    if (rst.getString("st_isdCos") == null) {
                        optCosSi.setSelected(false);
                        optCosNo.setSelected(false);
                    }
                    else if (rst.getString("st_isdCos").equals("S") ){
                        optCosSi.setSelected(true);
                    }
                    else if (rst.getString("st_isdCos").equals("N") ){
                        optCosNo.setSelected(true);
                    }
                }
                else{
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
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
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
    private boolean cargarTabAra() {
        boolean blnRes = true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null) {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_parara, a2.co_reg, a2.nd_ara, a2.fe_vigdes, a2.fe_vighas";
                strSQL+=" FROM tbm_pararaimp AS a1 INNER JOIN tbm_vigpararaimp AS a2";
                strSQL+="  ON a1.co_parara=a2.co_parara";
                strSQL+=" WHERE a1.co_parAra=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_PAR_ARA);
                strSQL+=" AND a1.st_reg NOT IN('E')";
                strSQL+=" ORDER BY co_reg";

                System.out.println("cargarTabAra: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while(rst.next()) {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_ARA_LIN, "");
                    vecReg.add(INT_TBL_DAT_ARA_POR_ARA, rst.getString("nd_ara"));
                    vecReg.add(INT_TBL_DAT_ARA_FEC_DES, objUti.formatearFecha(rst.getDate("fe_vigdes"), "dd/MM/yyyy"));
                    if((rst.getObject("fe_vighas")==null?"":(rst.getString("fe_vighas").equals("")?"":rst.getObject("fe_vighas"))).equals(""))
                        vecReg.add(INT_TBL_DAT_ARA_FEC_HAS, "");
                    else
                        vecReg.add(INT_TBL_DAT_ARA_FEC_HAS, objUti.formatearFecha(rst.getDate("fe_vighas"), "dd/MM/yyyy"));
                    vecDat.add(vecReg);
                }
                System.out.println("cargarTabAra: " + strSQL);
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDatAra.setModel(objTblMod);
                vecDat.clear();                
            }
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertar_tbmParAraImp()){
                    if (insertar_tbmVigParAraImp()){
                        con.commit();
                        blnRes=true;
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
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmParAraImp(){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_parara)";
                strSQL+=" FROM tbm_parAraImp AS a1";
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodParAra.setText("" + intUltReg);

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_pararaimp(";
                strSQL+="co_parara, tx_par, tx_des, tx_destna, tx_obs1, st_reg,";
                strSQL+="fe_ing, fe_ultmod, co_usring, co_usrmod, st_isdCos)";
                strSQL+=" VALUES (";
                strSQL+="" + txtCodParAra.getText(); //co_parara
                strSQL+=", " + objUti.codificar(txtNomParAra.getText()) + ""; //tx_par
                strSQL+=", " + objUti.codificar(txtDesParAra.getText()) + ""; //tx_des
                strSQL+=", " + objUti.codificar(txaDesTna.getText()) + ""; //tx_destna
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + ""; //tx_obs1
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod
                if(optCosSi.isSelected())
                    strSQL+=", 'S'";//st_isdCos 
                else
                    strSQL+=", 'N'";//st_isdCos 
                strSQL+=");";
                System.out.println("insertar: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmVigParAraImp(){
        boolean blnRes=true;
        int j=1;
        String strUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_vigpararaimp(";
                    strSQL+=" co_parara, co_reg, nd_ara, fe_vigdes, fe_vighas)";
                    strSQL+=" VALUES (";
                    strSQL+="" + txtCodParAra.getText(); //co_parara
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_ARA_POR_ARA), 3); //nd_ara
                    strAux=objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_DAT_ARA_FEC_DES).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_vigdes
                    //if(objTblMod.getValueAt(i, INT_TBL_DAT_ARA_FEC_HAS).toString().equals("")){
                    if((objTblMod.getValueAt(i, INT_TBL_DAT_ARA_FEC_HAS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_ARA_FEC_HAS).toString()).equals("")){
                        strSQL+=", Null"; //fe_vighas
                    }
                    else{
                        strAux=objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_DAT_ARA_FEC_HAS).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos());
                        strSQL+=", '" + strAux + "'"; //fe_vighas
                    }

                    strSQL+=");";
                    strUpd+=strSQL;
                    j++;
                }
                System.out.println("insertar_tbmVigParAraImp: " + strUpd);
                stm.executeUpdate(strUpd);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
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
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizar_tbmParAraImp()){
                    if(eliminar_tbmVigParAraImp()){
                        if(insertar_tbmVigParAraImp()){
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
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmParAraImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();

                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_parAraImp";
                strSQL+=" SET tx_par=" + objUti.codificar(txtNomParAra.getText()) + "";
                strSQL+=", tx_des=" + objUti.codificar(txtDesParAra.getText()) + "";
                strSQL+=", tx_destna=" + objUti.codificar(txaDesTna.getText()) + "";
                if(optCosSi.isSelected())
                    strSQL+=", st_isdCos='S'";
                else
                    strSQL+=", st_isdCos='N'";
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_parara=" + txtCodParAra.getText() +"";

                System.out.println("actualizar_tbmParAraImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmVigParAraImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_vigpararaimp";
                strSQL+=" WHERE co_parara=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_PAR_ARA);
                System.out.println("eliminar_tbmVigParAraImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (anular_tbmParAraImp()){
                    con.commit();
                    blnRes=true;
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
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmParAraImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_pararaimp";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_parara=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_PAR_ARA);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (eliminar_tbmParAraImp()){
                    con.commit();
                    blnRes=true;
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
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmParAraImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_pararaimp";
                strSQL+=" SET st_reg='E'";
                strSQL+=" WHERE co_parara=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_PAR_ARA);
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












}
