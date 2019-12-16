/*
 * ZafImp08_01.java
 *
 * Created on 11 de Junio de 2014, 9:12
 *
 * v0.2 
 */
package Importaciones.ZafImp08;
import Librerias.ZafImp.ZafImp;
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
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;

/**
 *
 * @author Ingrid Lino
 * "Muestra Ventana con información de transferencias asociadas"
 */
public class ZafImp08_03 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_COD_TIP_DOC = 1;
    private static final int INT_TBL_DAT_TIP_DOC = 2;
    private static final int INT_TBL_DAT_NOM_TIP_DOC = 3;
    private static final int INT_TBL_DAT_COD_DOC = 4;
    private static final int INT_TBL_DAT_NUM_DOC = 5;
    private static final int INT_TBL_DAT_FEC_DOC = 6;
    private static final int INT_TBL_DAT_COD_BOD_DES = 7;
    private static final int INT_TBL_DAT_NOM_BOD_DES = 8;
    private static final int INT_TBL_DAT_CAN = 9;
    private static final int INT_TBL_DAT_COD_USR_TRS = 10;
    private static final int INT_TBL_DAT_USR_TRS = 11;
    private static final int INT_TBL_DAT_NOM_USR_TRS = 12;

    //Constantes: Códigos de Menú.
    private static final int INT_COD_MNU_INGIMP=2889;                   //Ingresos por Importacion.
    
    //Variables.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
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
    private int intCodEmpTra, intCodLocTra, intCodTipDocTra, intCodDocTra, intCodItmMaeTra;
    
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;

    /**
     * Constructor
     */
    public ZafImp08_03(java.awt.Frame parent, boolean modal, ZafParSis obj, int codEmpTra, int codLocTra, int codTipDocTra, int codDocTra, int codItmMae) 
    {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis = obj;
        intCodEmpTra = codEmpTra;
        intCodLocTra = codLocTra;
        intCodTipDocTra = codTipDocTra;
        intCodDocTra = codDocTra;
        intCodItmMaeTra = codItmMae;
        if (configurarFrm()) {
            cargarLisTraAso();
        }
    }
    
    /**
     * Configuración de la tabla.
     * @return 
     */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            //Inicializar objetos
            objUti = new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objTblMod = new ZafTblMod();
            
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_TIP_DOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NOM_TIP_DOC, "Tipo de Documento");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_BOD_DES, "Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_NOM_BOD_DES, "Nom.Bod.Des.");
            vecCab.add(INT_TBL_DAT_CAN, "Cantidad");
            vecCab.add(INT_TBL_DAT_COD_USR_TRS, "Cód.Usr.Trs");
            vecCab.add(INT_TBL_DAT_USR_TRS, "Usr.Trs.");
            vecCab.add(INT_TBL_DAT_NOM_USR_TRS, "Nom.Usr.Trs.");

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
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD_DES).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR_TRS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_USR_TRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR_TRS).setPreferredWidth(30);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_TRS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD_DES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_USR_TRS, tblDat);

            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

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
        } catch (Exception e) {
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
                    strMsg = "";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DAT_TIP_DOC:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_NOM_TIP_DOC:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg = "Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_BOD_DES:
                    strMsg = "Código de la bodega destino";
                    break;
                case INT_TBL_DAT_NOM_BOD_DES:
                    strMsg = "Nombre de la bodega destino";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg = "Cantidad transferida";
                    break;
                case INT_TBL_DAT_COD_USR_TRS:
                    strMsg = "Código del usuario de transferencia";
                    break;
                case INT_TBL_DAT_USR_TRS:
                    strMsg = "Usuario de transferencia";
                    break;
                case INT_TBL_DAT_NOM_USR_TRS:
                    strMsg = "Nombre del usuario de transferencia";
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
        butCan = new javax.swing.JButton();
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
        lblTit.setText("Listado de transferencias");
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

        butCan.setText("Cerrar");
        butCan.setToolTipText("Salir");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panCer.add(butCan);

        panBot.add(panCer, java.awt.BorderLayout.EAST);

        panBor.setLayout(new java.awt.BorderLayout());
        panBot.add(panBor, java.awt.BorderLayout.WEST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(651, 471));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCan;
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
     * Cargar Listado Transferencias Asociadas.
     * @return 
     */
    private boolean cargarLisTraAso() 
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL =" SELECT a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_emp as co_empTRA, a2.co_loc as co_locTra, a2.co_tipDoc as co_tipDocTra \n";
                strSQL+="       , a2.co_doc as co_docTra, a2.co_itm, a2.co_itmMae, a2.co_bod, a2.tx_nomBodOrgDes, ABS(a2.nd_can) AS nd_can, a3.nd_stkCon, a3.co_usr, a3.tx_usr, a3.tx_nom  \n";
                strSQL+="       , a4.tx_desCor, a4.tx_desLar,a2.ne_numDoc,a2.fe_doc  \n";
                strSQL+=" FROM (\n";
                strSQL+="        SELECT x2.co_empRel, x2.co_locRel, x2.co_tipDocRel, x2.co_docRel, x3.co_emp, x3.co_loc,x3.ne_numDoc,x3.fe_doc \n";
                strSQL+="             , x3.co_tipDoc, x3.co_doc,x4.co_itm,x5.co_itmMae,x4.co_bod,x4.tx_nomBodOrgDes, x4.nd_can \n";
                strSQL+="        FROM tbr_cabMovInv as x2 /* RELACION */ \n";
                strSQL+="        INNER JOIN tbm_cabMovInv as x3 ON (x2.co_emp=x3.co_emp AND x2.co_loc=x3.co_loc AND x2.co_tipDoc=x3.co_tipDoc AND x2.co_doc=x3.co_doc) /* TRINIM */\n";
                strSQL+="        INNER JOIN tbm_detMovInv as x4 ON (x3.co_emp=x4.co_emp AND x3.co_loc=x4.co_loc AND x3.co_tipDoc=x4.co_tipDoc AND x3.co_doc=x4.co_doc) /* DETALLE TRINIM*/\n";
                strSQL+="        INNER JOIN tbm_equInv as x5 ON (x4.co_emp=x5.co_emp AND x4.co_itm=x5.co_itm)\n";
                //strSQL+="        WHERE x2.co_tipDocRel IN(select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN ("+INT_COD_MNU_INGIMP+" , "+objImp.INT_COD_MNU_PRG_AJU_INV+") ) \n";
                strSQL+="        WHERE x2.co_tipDocRel IN(select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN ("+INT_COD_MNU_INGIMP+") ) \n";
                strSQL+="        AND x2.co_tipDoc NOT IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN ("+objImp.INT_COD_MNU_PRG_AJU_INV+") ) \n";
                strSQL+="        AND x4.nd_can<0 AND x3.st_reg in ('A') \n";
                strSQL+=" ) AS a2 \n";
                if(intCodTipDocTra==14 || intCodTipDocTra==245) {
                    strSQL += " INNER JOIN (\n";
                }
                else {
                    strSQL += " LEFT OUTER JOIN (\n";
                }
                strSQL += "       SELECT x1.co_empRel, x1.co_locRel,x1.co_tipDocRel, x1.co_docRel, x1.co_emp, x1.co_loc, x1.co_tipDoc, x1.co_doc,x3.co_itm,x5.co_itmMae, x3.nd_stkCon,\n";
                strSQL += "              x4.co_usr, x4.tx_usr, x4.tx_nom \n";
                strSQL += "       FROM tbm_cabOrdConInv as x1 \n";
                strSQL += "       INNER JOIN tbm_detOrdConInv as x2 ON (x1.co_emp=x2.co_emp AND x1.co_loc=x2.co_loc AND x1.co_tipDoc=x2.co_tipDoc AND x1.co_doc=x2.co_doc)\n";
                strSQL += "       INNER JOIN tbm_conInv as x3 ON (x2.co_emp=x3.co_emp AND x2.co_loc=x3.co_locRel AND x2.co_tipDoc=x3.co_tipDocRel AND x2.co_doc=x3.co_docRel AND x2.co_reg=x3.co_regRel)\n";
                strSQL += "       LEFT OUTER JOIN tbm_usr AS x4 ON (x3.co_usrResCon=x4.co_usr)\n";
                strSQL += "       INNER JOIN tbm_equInv as x5 ON (x3.co_emp=x5.co_emp AND x3.co_itm=x5.co_itm)\n";
                //strSQL += "       WHERE x1.co_tipDocRel IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN ("+INT_COD_MNU_INGIMP+" , "+objImp.INT_COD_MNU_PRG_AJU_INV+") ) \n";
                strSQL += "       WHERE x1.co_tipDocRel IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN ("+INT_COD_MNU_INGIMP+") ) \n";
                strSQL += " ) AS a3 ON (a2.co_empRel=a3.co_empRel AND a2.co_locRel=a3.co_locRel AND a2.co_tipDocRel=a3.co_tipDocRel AND a2.co_docRel=a3.co_docRel AND a2.co_itmMae=a3.co_itmMae)\n";
                strSQL += " INNER JOIN tbm_cabTipDoc as a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc) \n";
                strSQL += " WHERE a2.co_empRel=" + intCodEmpTra;
                strSQL += " AND a2.co_locRel=" + intCodLocTra;
                strSQL += " AND a2.co_tipDocRel=" + intCodTipDocTra;
                strSQL += " AND a2.co_docRel=" + intCodDocTra;
                strSQL += " AND a2.co_ItmMae=" + intCodItmMaeTra;

                System.out.println("SQL ZafImp08_01: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipDocTra"));
                    vecReg.add(INT_TBL_DAT_TIP_DOC,     rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_NOM_TIP_DOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,     rst.getString("co_docTra"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,     rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,     rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_BOD_DES, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_DAT_NOM_BOD_DES, rst.getString("tx_nomBodOrgDes"));
                    vecReg.add(INT_TBL_DAT_CAN,         rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_COD_USR_TRS, rst.getString("co_usr"));
                    vecReg.add(INT_TBL_DAT_USR_TRS,     rst.getString("tx_usr"));
                    vecReg.add(INT_TBL_DAT_NOM_USR_TRS, rst.getString("tx_nom"));
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
