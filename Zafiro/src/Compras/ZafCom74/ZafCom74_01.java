/*
 * ZafCom74_01.java
 *
 * v0.1 
 */
package Compras.ZafCom74;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import java.math.BigDecimal;
import java.math.BigInteger;
/**
 *
 * @author  Ingrid
 */
public class ZafCom74_01 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_CAR_PAG=1;
    final int INT_TBL_DAT_NOM_CAR_PAG=2;
    final int INT_TBL_DAT_DESCOR_TIP_CAR_PAG=3;
    final int INT_TBL_DAT_DESLAR_TIP_CAR_PAG=4;
    final int INT_TBL_DAT_UBI_CAR_PAG=5;
    
    //ArrayList: Ubicación
    private ArrayList arlReg, arlDat;
    final int INT_ARL_DAT_COD_CAR_PAG=0;
    final int INT_ARL_DAT_NOM_CAR_PAG=1;
    final int INT_ARL_DAT_DESCOR_TIP_CAR_PAG=2;
    final int INT_ARL_DAT_DESLAR_TIP_CAR_PAG=3;
    final int INT_ARL_DAT_UBI_CAR_PAG=4;
    
    //ArrayList para arreglo: Tipo Cargos
    private ArrayList arlDatCboTipCar;
    private static final int INT_ARL_CBO_TIP_CAR_IND=0;  
    private static final int INT_ARL_CBO_TIP_CAR_DESCOR=1;  
    private static final int INT_ARL_CBO_TIP_CAR_DESLAR=2;  

    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafMouMotAda objMouMotAda;
    private ZafTblBus objTblBus;
    private ZafTblOrd objTblOrd;    
    private ZafThreadGUI objThrGUI;
    private Vector vecReg, vecDat, vecCab;
    private String strSQL, strAux;
    private String strNomCarNew, strTipCarNew;
    public int intButPre;
    private int intUbiNew;
    private int intCodCar;
    private char chrOptTooBar;

    /* Constructor */
    public ZafCom74_01(java.awt.Frame parent, boolean modal, ZafParSis obj, int codigoCargo, String nombreNuevoCargo, String tipoNuevoCargo, int nuevaUbicacion, char opcionTooBar, ArrayList arregloTipoCargo) 
    {
        super(parent, modal);
        initComponents();
        objParSis=obj;
        intCodCar=codigoCargo;
        strNomCarNew=nombreNuevoCargo;
        strTipCarNew=tipoNuevoCargo;
        intUbiNew=nuevaUbicacion;
        arlDatCboTipCar=arregloTipoCargo;
        intButPre=0;//no se ha seleccionado el boton
        chrOptTooBar=opcionTooBar;
        //Inicializar objetos.
        objUti=new ZafUtil();
        if(configurarTblDat()){
            if(cargarCargosPagar()){
            }
        }
        arlDat=new ArrayList();
    }

    public ZafCom74_01(java.awt.Frame parent, boolean modal, ZafParSis obj, int codigoCargo) 
    {
        super(parent, modal);
        initComponents();
        objParSis=obj;
        intButPre=0;//no se ha seleccionado el boton
        intCodCar=codigoCargo;
        //Inicializar objetos.
        objUti=new ZafUtil();
        configurarTblDat();
        arlDat=new ArrayList();
    }
    
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_CAR_PAG,"Código");
            vecCab.add(INT_TBL_DAT_NOM_CAR_PAG,"Cargo");
            vecCab.add(INT_TBL_DAT_DESCOR_TIP_CAR_PAG,"Tip.Car.");
            vecCab.add(INT_TBL_DAT_DESLAR_TIP_CAR_PAG,"Tipo Cargo");
            vecCab.add(INT_TBL_DAT_UBI_CAR_PAG,"Ubicación");
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_CAR_PAG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CAR_PAG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DESCOR_TIP_CAR_PAG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DESLAR_TIP_CAR_PAG).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_UBI_CAR_PAG).setPreferredWidth(55);
           
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_TIP_CAR_PAG).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_CAR_PAG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_DESCOR_TIP_CAR_PAG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UBI_CAR_PAG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

            //Configurar JTable: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_CAR_PAG, tblDat);

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
                case INT_TBL_DAT_COD_CAR_PAG:
                    strMsg="Código del cargo";
                    break;
                case INT_TBL_DAT_NOM_CAR_PAG:
                    strMsg="Nombre del cargo";
                    break;
                case INT_TBL_DAT_DESCOR_TIP_CAR_PAG:
                    strMsg="Tipo de cargo (Descripción Corta)";
                    break;
                case INT_TBL_DAT_DESLAR_TIP_CAR_PAG:
                    strMsg="Tipo de cargo (Descripción Larga)";
                    break;                    
                case INT_TBL_DAT_UBI_CAR_PAG:
                    strMsg="Orden/Ubicación del cargo";
                    break;
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

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        panEspTblDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panOrd = new javax.swing.JPanel();
        butArr = new javax.swing.JButton();
        butAba = new javax.swing.JButton();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panEspBot = new javax.swing.JPanel();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Ubicación de Cargos a Pagar ");
        lblTit.setPreferredSize(new java.awt.Dimension(153, 20));
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());
        panGenDet.add(panEspTblDat, java.awt.BorderLayout.WEST);

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

        panOrd.setPreferredSize(new java.awt.Dimension(35, 100));
        panOrd.setLayout(null);

        butArr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleArr_7_16x16.gif"))); // NOI18N
        butArr.setToolTipText("Ubicación hacia arriba.");
        butArr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butArrActionPerformed(evt);
            }
        });
        panOrd.add(butArr);
        butArr.setBounds(5, 140, 25, 30);

        butAba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleAba_7_16x16.gif"))); // NOI18N
        butAba.setToolTipText("Ubicación hacia abajo.");
        butAba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAbaActionPerformed(evt);
            }
        });
        panOrd.add(butAba);
        butAba.setBounds(5, 170, 25, 30);

        panGenDet.add(panOrd, java.awt.BorderLayout.EAST);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panEspBot.setPreferredSize(new java.awt.Dimension(25, 20));
        panEspBot.setLayout(null);
        panBot.add(panEspBot);

        panBar.add(panBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lblMsgSis.setPreferredSize(new java.awt.Dimension(28, 18));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(225, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if(cargarDatosArreglo()){
            dispose();
        }
        else{
            mostrarMsgInf("<HTML>Los datos no se generaron correctamente para ser enviados al formulario principal.<BR>Intente Aceptar nuevamente.<HTML>");
        }
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intButPre=2;//se presiono cancelar
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            intButPre=2;//se presiono la X de cerrar formulario
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void butArrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butArrActionPerformed
        int intFil=tblDat.getSelectedRow();
        int intFilArr=(intFil-1);

        String strTipCar="", strTipCarDes="", strNomCarPag="", strCodCarPag="", strUbiCarPag="";
        if(intFil>0){
            strCodCarPag=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CAR_PAG).toString();
            strNomCarPag=objTblMod.getValueAt(intFil, INT_TBL_DAT_NOM_CAR_PAG).toString();
            strTipCar=objTblMod.getValueAt(intFil, INT_TBL_DAT_DESCOR_TIP_CAR_PAG).toString();
            strTipCarDes=objTblMod.getValueAt(intFil, INT_TBL_DAT_DESLAR_TIP_CAR_PAG).toString();
            strUbiCarPag=objTblMod.getValueAt(intFil, INT_TBL_DAT_UBI_CAR_PAG).toString();
            
            objTblMod.insertRow(intFilArr);
            objTblMod.setValueAt(strCodCarPag, intFilArr, INT_TBL_DAT_COD_CAR_PAG);
            objTblMod.setValueAt(strNomCarPag, intFilArr, INT_TBL_DAT_NOM_CAR_PAG);
            objTblMod.setValueAt(strTipCar, intFilArr, INT_TBL_DAT_DESCOR_TIP_CAR_PAG);
            objTblMod.setValueAt(strTipCarDes, intFilArr, INT_TBL_DAT_DESLAR_TIP_CAR_PAG);
            objTblMod.setValueAt(strUbiCarPag, intFilArr, INT_TBL_DAT_UBI_CAR_PAG);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblMod.removeRow(intFil+1);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();

            tblDat.setRowSelectionInterval(intFilArr, intFilArr);
            tblDat.requestFocus();
        }
    }//GEN-LAST:event_butArrActionPerformed

    private void butAbaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAbaActionPerformed
        int intFil=tblDat.getSelectedRow();
        int intFilAba=(intFil+1);
        String strTipCar="", strTipCarDes="", strNomCarPag="", strCodCarPag="", strUbiCarPag="";
        if(intFil<(objTblMod.getRowCountTrue()-1)){
            strCodCarPag=objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CAR_PAG).toString();
            strNomCarPag=objTblMod.getValueAt(intFil, INT_TBL_DAT_NOM_CAR_PAG).toString();
            strTipCar=objTblMod.getValueAt(intFil, INT_TBL_DAT_DESCOR_TIP_CAR_PAG).toString();
            strTipCarDes=objTblMod.getValueAt(intFil, INT_TBL_DAT_DESLAR_TIP_CAR_PAG).toString();
            strUbiCarPag=objTblMod.getValueAt(intFil, INT_TBL_DAT_UBI_CAR_PAG).toString();

            objTblMod.insertRow(intFilAba+1);
            objTblMod.setValueAt(strCodCarPag, (intFilAba+1), INT_TBL_DAT_COD_CAR_PAG);
            objTblMod.setValueAt(strNomCarPag, (intFilAba+1), INT_TBL_DAT_NOM_CAR_PAG);
            objTblMod.setValueAt(strTipCar, (intFilAba+1), INT_TBL_DAT_DESCOR_TIP_CAR_PAG);
            objTblMod.setValueAt(strTipCarDes, (intFilAba+1), INT_TBL_DAT_DESLAR_TIP_CAR_PAG);
            objTblMod.setValueAt(strUbiCarPag, (intFilAba+1), INT_TBL_DAT_UBI_CAR_PAG);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblMod.removeRow(intFil);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();

            tblDat.setRowSelectionInterval(intFilAba, intFilAba);
            tblDat.requestFocus();
        }
    }//GEN-LAST:event_butAbaActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAba;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butArr;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEspBot;
    private javax.swing.JPanel panEspTblDat;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panOrd;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
       
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa utilizar
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
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar ésta clase
     * ya que si no sólo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            //Limpiar objetos.
            if (!cargarCargosPagar()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objTblMod.setDataModelChanged(true);
            objThrGUI=null;
        }
    }
    
    private boolean cargarCargosPagar(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_carPag, a1.tx_nom, a1.tx_tipcarpag, a1.ne_ubi";
                strSQL+=" FROM tbm_carPagImp AS a1";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" ORDER BY a1.ne_ubi";
                System.out.println("SQL cargarCargosPagar: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                int R=0;
                while (rst.next()){
                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CAR_PAG,  rst.getString("co_carPag"));
                    vecReg.add(INT_TBL_DAT_NOM_CAR_PAG,  rst.getString("tx_nom"));
                     
                    strAux=rst.getString("tx_tipCarPag");
                    vecReg.add(INT_TBL_DAT_DESCOR_TIP_CAR_PAG,  strAux);
                                       
                    //Se asigna descripción larga del tipo de cargo, de acuerdo a la descripción corta.
                    for(int i=0; i<arlDatCboTipCar.size(); i++)
                    {
                        if(strAux.equals(objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESCOR)))
                        {
                            vecReg.add(INT_TBL_DAT_DESLAR_TIP_CAR_PAG,  objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESLAR) );
                            break;
                        }
                    }   
                    vecReg.add(INT_TBL_DAT_UBI_CAR_PAG,  rst.getString("ne_ubi"));
                    vecDat.add(vecReg);
                }

                if(chrOptTooBar=='n'){
                    //se agrega el nuevo cargo
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_CAR_PAG,  "");
                    vecReg.add(INT_TBL_DAT_NOM_CAR_PAG,  strNomCarNew);
                    
                    //Se asigna descripción corta del tipo de cargo, de acuerdo a la descripción larga del nuevo cargo.
                    for(int i=0; i<arlDatCboTipCar.size();i++)
                    {
                        if(strTipCarNew.equals(objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESLAR)))
                        {
                            vecReg.add(INT_TBL_DAT_DESCOR_TIP_CAR_PAG,  objUti.getStringValueAt(arlDatCboTipCar, i, INT_ARL_CBO_TIP_CAR_DESCOR) );
                            break;
                        }
                    }
                    vecReg.add(INT_TBL_DAT_DESLAR_TIP_CAR_PAG,  strTipCarNew);
                    vecReg.add(INT_TBL_DAT_UBI_CAR_PAG,  intUbiNew);
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

                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0){
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
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

    private boolean cargarDatosArreglo(){
        boolean blnRes=true;
        String strCodCarPagNew="";
        try{
            //Se regenera el ordenamiento
            int j=1;
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //if(chrOptTooBar=='n'){
                    strCodCarPagNew=objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG).toString();
                    //Cuando es un cargo nuevo, se asigna como ubicación el número de la fila.
                    if(strCodCarPagNew.equals("")){
                        intUbiNew=j;//ubicación del nuevo cargo a pagar. 
                    }
                    else{
                        if(strCodCarPagNew.equals(String.valueOf(intCodCar))){
                            intUbiNew=j;
                        }
                    }
                //}
                objTblMod.setValueAt(""+j, i, INT_TBL_DAT_UBI_CAR_PAG);
                j++;
            }
            arlDat.clear();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                arlReg=new ArrayList();
                arlReg.add(INT_ARL_DAT_COD_CAR_PAG,         objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG).toString());
                arlReg.add(INT_ARL_DAT_NOM_CAR_PAG,         objTblMod.getValueAt(i, INT_TBL_DAT_NOM_CAR_PAG).toString());
                arlReg.add(INT_ARL_DAT_DESCOR_TIP_CAR_PAG,  objTblMod.getValueAt(i, INT_TBL_DAT_DESCOR_TIP_CAR_PAG).toString());
                arlReg.add(INT_ARL_DAT_DESLAR_TIP_CAR_PAG,  objTblMod.getValueAt(i, INT_TBL_DAT_DESLAR_TIP_CAR_PAG).toString());
                arlReg.add(INT_ARL_DAT_UBI_CAR_PAG,         objTblMod.getValueAt(i, INT_TBL_DAT_UBI_CAR_PAG).toString());
                arlDat.add(arlReg);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarCargosPagarArreglo(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            //Limpiar vector de datos.
            vecDat.clear();
            //Obtener los registros.
            for(int i=0; i<arlDat.size(); i++){
                vecReg=new Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_COD_CAR_PAG,     objUti.getStringValueAt(arlDat, i, INT_ARL_DAT_COD_CAR_PAG));
                vecReg.add(INT_TBL_DAT_NOM_CAR_PAG,     objUti.getStringValueAt(arlDat, i, INT_ARL_DAT_NOM_CAR_PAG));
                vecReg.add(INT_TBL_DAT_DESCOR_TIP_CAR_PAG,     objUti.getStringValueAt(arlDat, i, INT_ARL_DAT_DESCOR_TIP_CAR_PAG));
                vecReg.add(INT_TBL_DAT_DESLAR_TIP_CAR_PAG, objUti.getStringValueAt(arlDat, i, INT_ARL_DAT_DESLAR_TIP_CAR_PAG));
                vecReg.add(INT_TBL_DAT_UBI_CAR_PAG,     objUti.getStringValueAt(arlDat, i, INT_ARL_DAT_UBI_CAR_PAG));
                vecDat.add(vecReg);
            }                
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
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
    
    public int getBotonPresionado() {
        return intButPre;
    }

    public void setBotonPresionado(int intButPre) {
        this.intButPre = intButPre;
    }

    public ArrayList getArregloDatosCargosPagar() {
        return arlDat;
    }

    public void setArregloDatosCargosPagar(ArrayList arlDat) {
        this.arlDat = arlDat;
        cargarCargosPagarArreglo();
    }

    public int getUbicacionCargoPagarNuevo() {
        return intUbiNew;
    }



  

}