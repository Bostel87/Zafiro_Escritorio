/*
 * ZafImp28_02.java
 *
 * Created on 28 de Diciembre de 2016, 16h43
 *
 * v0.1
 */
package Importaciones.ZafImp28;

import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de solicitudes de transferencias asociadas"
 */
public class ZafImp28_02 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_FEC_DOC = 1;
    private static final int INT_TBL_DAT_COD_TIP_DOC = 2;
    private static final int INT_TBL_DAT_DES_COR = 3;
    private static final int INT_TBL_DAT_DES_LAR = 4;
    private static final int INT_TBL_DAT_COD_DOC = 5;
    private static final int INT_TBL_DAT_NUM_DOC = 6;
    private static final int INT_TBL_DAT_COD_BODDES = 7;
    private static final int INT_TBL_DAT_NOM_BODDES = 8;
    private static final int INT_TBL_DAT_CAN = 9;
    private static final int INT_TBL_DAT_COD_USR = 10;
    private static final int INT_TBL_DAT_TXT_USR = 11;
    private static final int INT_TBL_DAT_NOM_USR = 12;
    private static final int INT_TBL_DAT_EST_AUT = 13;
    private static final int INT_TBL_DAT_CHK_ISDOCAJU = 14;

    //Variables.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCen, objTblCelRenLblNum;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChkDocAju;      //Render: Presentar JCheckBox en JTable.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafImp objImp;
    
    //Variables de la clase.
    public String strObs;
    private Vector vecReg, vecDat, vecCab;
    private int intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, intCodItmMaeIngImp;
    
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;

    /**
     * Constructor
     */
    public ZafImp28_02(java.awt.Frame parent, boolean modal, ZafParSis obj, int codEmpIngImp, int codLocIngImp, int codTipDocIngImp, int codDocIngImp, int codItmMae) 
    {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis = obj;
        intCodEmpIngImp = codEmpIngImp;
        intCodLocIngImp = codLocIngImp;
        intCodTipDocIngImp = codTipDocIngImp;
        intCodDocIngImp = codDocIngImp;
        intCodItmMaeIngImp = codItmMae;
        objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
        if (configurarTblDat()) {
            cargarLisSolTraAso();
        }

    }
    
    /**
     * Configuración de la tabla.
     * @return 
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR, "Tipo de Documento");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_COD_BODDES, "Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_NOM_BODDES, "Nom.Bod.Des.");
            vecCab.add(INT_TBL_DAT_CAN, "Cantidad");
            vecCab.add(INT_TBL_DAT_COD_USR, "Cód.Usr.");
            vecCab.add(INT_TBL_DAT_TXT_USR, "Usuario");
            vecCab.add(INT_TBL_DAT_NOM_USR, "Nom.Usr.");
            vecCab.add(INT_TBL_DAT_EST_AUT, "Est.Aut.");
            vecCab.add(INT_TBL_DAT_CHK_ISDOCAJU, "..");
            
            objUti = new ZafUtil();

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_BODDES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BODDES).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TXT_USR).setPreferredWidth(72);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_EST_AUT).setPreferredWidth(44);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ISDOCAJU).setPreferredWidth(20);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BODDES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_ISDOCAJU, tblDat);

            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BODDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TXT_USR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(objTblMod.isChecked(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_CHK_ISDOCAJU)){
                        objTblCelRenLbl.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
                     
            //Formato Columna Estado de Autorización.
            objTblCelRenLblCen = new ZafTblCelRenLbl();
            objTblCelRenLblCen.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_EST_AUT).setCellRenderer(objTblCelRenLblCen);            
            objTblCelRenLblCen.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(objTblMod.isChecked(objTblCelRenLblCen.getRowRender(), INT_TBL_DAT_CHK_ISDOCAJU)){
                        objTblCelRenLblCen.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenLblCen.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            //Formato Columna Cantidad
            objTblCelRenLblNum = new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLblNum);
            objTblCelRenLblNum.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(objTblMod.isChecked(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_CHK_ISDOCAJU)){
                        objTblCelRenLblNum.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDocAju=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_ISDOCAJU).setCellRenderer(objTblCelRenChkDocAju);
            objTblCelRenChkDocAju=null;    
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CAN};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_DAT_LIN:
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #FFDDBB'>&nbsp;</td><td>&nbsp;</td><td>Indica que son solicitudes de \"Documentos de Ajustes\".</td></tr>"; //Color Crema
                    strMsg+="</table><br></html>";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg = "Fecha del documento";
                    break;                    
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg = "Número del documento";
                    break;
                case INT_TBL_DAT_COD_BODDES:
                    strMsg = "Código de la bodega destino";
                    break;
                case INT_TBL_DAT_NOM_BODDES:
                    strMsg = "Nombre de la bodega destino";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg = "Cantidad solicitada";
                    break;
                case INT_TBL_DAT_COD_USR:
                    strMsg = "Código del usuario solicitante";
                    break;
                case INT_TBL_DAT_TXT_USR:
                    strMsg = "Usuario de solicitud";
                    break;
                case INT_TBL_DAT_NOM_USR:
                    strMsg = "Nombre del usuario de solicitud";
                    break;
                case INT_TBL_DAT_EST_AUT:
                    strMsg = "Estado de Autorización de la solicitud. A=Autorizada; P=Pendiente; D=Denegada.";
                    break;  
                case INT_TBL_DAT_CHK_ISDOCAJU:
                    strMsg = "Indica si movimiento pertenece a un ajuste o no. ";
                    break;                      
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panCer = new javax.swing.JPanel();
        butCer = new javax.swing.JButton();
        panBor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listado de Items según movimiento de importaciones...");
        setMinimumSize(new java.awt.Dimension(161, 157));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de solicitudes de transferencias");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnTot, java.awt.BorderLayout.SOUTH);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butCer.setText("Cerrar");
        butCer.setToolTipText("Salir");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panCer.add(butCer);

        panBot.add(panCer, java.awt.BorderLayout.EAST);

        panBor.setLayout(new java.awt.BorderLayout());
        panBot.add(panBor, java.awt.BorderLayout.WEST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(516, 389));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        dispose();
    }//GEN-LAST:event_butCerActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCer;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBor;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCer;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    

    /**
     * Cargar Listado Solicitudes de Transferencias Asociadas.
     * @return 
     */
    private boolean cargarLisSolTraAso() 
    {
        boolean blnRes = true;
        String strAuxTipDocAju="";
        int intCodLocGrp=1;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                //Obtener condicion
                strAuxTipDocAju="select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_loc="+intCodLocGrp+" and co_mnu="+objImp.INT_COD_MNU_PRG_AJU_INV;
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT CASE WHEN a2.co_tipDocRelCabMovInv IN ("+strAuxTipDocAju+") THEN 'S' ELSE 'N' END AS st_IsDocAju";
                strSQL+="      , a.fe_doc, a.co_emp, a.co_loc, a.co_tipDoc, a5.tx_desCor, a5.tx_desLar, a.co_doc, a.ne_numDoc, a.co_bodDes\n";
                strSQL+="      , a7.tx_nom as tx_nomBodDes, a1.nd_Can, a6.co_usr, a6.tx_usr, a6.tx_nom AS tx_nomUsr\n";
                strSQL+="      , CASE WHEN a.st_aut IS NULL THEN 'P' ELSE a.st_aut END as st_aut\n";
                strSQL+=" FROM tbm_CabSolTraInv as a \n";
                strSQL+=" INNER JOIN tbm_detSolTraInv as a1 ON a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipdoc AND a.co_doc=a1.co_Doc\n";
                strSQL+=" INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ON a2.co_EmpRelCabSolTraInv=a.co_emp AND a2.co_locRelCabSolTraInv=a.co_loc AND a2.co_tipDocRelCabSolTraInv=a.co_tipdoc AND a2.co_docRelCabSolTraInv=a.co_Doc \n";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a5 ON a.co_emp=a5.co_emp AND a.co_loc=a5.co_loc AND a.co_tipDoc=a5.co_tipDoc\n";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON a.co_usrIng=a6.co_usr\n";
                strSQL+=" INNER JOIN tbm_bod as a7 ON (a.co_emp=a7.co_emp AND a.co_bodDes=a7.co_bod)\n"; 
                strSQL+=" INNER JOIN tbm_equInv AS a3 ON a.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm\n";
                strSQL+=" WHERE a.st_reg in ('A') ";
                //strSQL+=" AND (CASE WHEN a.st_aut IS NULL THEN 'P' ELSE a.st_aut END) NOT IN ('D')\n";  //Se comenta para que muestre todas las solicitudes, asi hayan sido denegadas.
                strSQL+=" AND EXISTS\n";
                strSQL+=" (\n";
                strSQL+="   SELECT * FROM(\n";
                strSQL+="      SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.co_emp AS co_empRel, a.co_loc as co_locRel, a.co_tipDoc AS co_tipDocRel, a.co_Doc AS co_docRel\n";
                strSQL+="      FROM tbm_CabMovInv AS a\n";
                strSQL+="      WHERE a.co_emp="+intCodEmpIngImp+" AND a.co_loc="+intCodLocIngImp+" AND a.co_tipDoc="+intCodTipDocIngImp+" AND a.co_doc="+intCodDocIngImp;
                strSQL+="      UNION ALL \n";
                strSQL+="      SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel\n";
                strSQL+="      FROM tbr_CabMovInv AS a\n";
                strSQL+="      WHERE a.co_empRel="+intCodEmpIngImp+" AND a.co_locRel="+intCodLocIngImp+" AND a.co_tipDocRel="+intCodTipDocIngImp+" AND a.co_docRel="+intCodDocIngImp;
                strSQL+="      AND a.co_tipDoc IN ("+strAuxTipDocAju+")";
                strSQL+="   ) AS a4 \n";
                strSQL+="   WHERE a2.co_empRelCabMovInv=a4.co_emp AND a2.co_locRelCabMovInv=a4.co_loc AND a2.co_tipDocRelCabMovInv=a4.co_tipDoc AND a2.co_docRelCabMovInv=a4.co_doc\n";
                strSQL+=" )\n";
                strSQL+=" AND a3.co_itmMae="+intCodItmMaeIngImp;
                strSQL+=" ORDER BY a.fe_doc, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc \n";
                
                System.out.println("SQL ZafImp28_04: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_FEC_DOC, rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_COD_BODDES, rst.getString("co_bodDes"));
                    vecReg.add(INT_TBL_DAT_NOM_BODDES, rst.getString("tx_nomBodDes"));
                    vecReg.add(INT_TBL_DAT_CAN, rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_COD_USR, rst.getString("co_usr"));
                    vecReg.add(INT_TBL_DAT_TXT_USR, rst.getString("tx_usr"));
                    vecReg.add(INT_TBL_DAT_NOM_USR, rst.getString("tx_nomUsr"));
                    vecReg.add(INT_TBL_DAT_EST_AUT, rst.getString("st_aut"));
                    vecReg.add(INT_TBL_DAT_CHK_ISDOCAJU, null);

                    if(!  (  (rst.getObject("st_IsDocAju")==null)  || (rst.getString("st_IsDocAju").equals("N"))  )  )
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_ISDOCAJU); 
                    
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                objTblTot.calcularTotales();
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



  
}
