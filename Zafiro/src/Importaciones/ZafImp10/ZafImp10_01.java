/*
 * ZafImp10_01.java
 *
 * Created on 11 de Mayo de 2018, 10:00
 *
 * v0.2 
 */
package Importaciones.ZafImp10;
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
import Librerias.ZafTblUti.ZafTblOrd.ZafHeaRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import static Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd.INT_TBL_ASC;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;

/**
 *
 * @author Ingrid Lino
 * "Muestra Ventana con información de transferencias asociadas"
 */
public class ZafImp10_01 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_NUM_CON = 1;
    private static final int INT_TBL_DAT_COD_EMP = 2;
    private static final int INT_TBL_DAT_COD_LOC = 3;
    private static final int INT_TBL_DAT_COD_TIP_DOC = 4;
    private static final int INT_TBL_DAT_COD_DOC = 5;
    private static final int INT_TBL_DAT_COD_REG = 6;
    private static final int INT_TBL_DAT_COD_ITM = 7;
    private static final int INT_TBL_DAT_COD_ITM_MAE = 8;
    private static final int INT_TBL_DAT_CAN_CON = 9;
    private static final int INT_TBL_DAT_COD_USR_CON = 10;
    private static final int INT_TBL_DAT_USR_CON = 11;
    private static final int INT_TBL_DAT_NOM_USR_CON = 12;
    private static final int INT_TBL_DAT_FEC_CON = 13;
    private static final int INT_TBL_DAT_OBS_CON = 14;
    
    //Variables.
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
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
    private ZafHeaRenLbl objHeaRenLbl;                  //Header: Render para mostrar el ordenamiento.
    
    private Vector vecReg, vecDat, vecCab;
    private int intCodEmpCon, intCodLocCon, intCodTipDocCon, intCodDocCon, intCodItmMaeCon;
    private String strSQL;

    /**
     * Constructor
     */
    public ZafImp10_01(java.awt.Frame parent, boolean modal, ZafParSis obj, int codEmpCon, int codLocCon, int codTipDocCon, int codDocCon, int codItmMaeCon) 
    {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis = obj;
        intCodEmpCon = codEmpCon;
        intCodLocCon = codLocCon;
        intCodTipDocCon = codTipDocCon;
        intCodDocCon = codDocCon;
        intCodItmMaeCon = codItmMaeCon;
        if (configurarFrm()) {
            cargarHisItmCon();
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
            vecCab.add(INT_TBL_DAT_NUM_CON, "Conteo");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG, "Cód.Reg.");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_CAN_CON, "Can.Con.");
            vecCab.add(INT_TBL_DAT_COD_USR_CON, "Cód.Usr.Con.");
            vecCab.add(INT_TBL_DAT_USR_CON, "Usr.Con.");
            vecCab.add(INT_TBL_DAT_NOM_USR_CON, "Nom.Usr.Con.");
            vecCab.add(INT_TBL_DAT_FEC_CON, "Fec.Con.");
            vecCab.add(INT_TBL_DAT_OBS_CON, "Observación");

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
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR_CON).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_USR_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR_CON).setPreferredWidth(110);     
            tcmAux.getColumn(INT_TBL_DAT_FEC_CON).setPreferredWidth(125);
            tcmAux.getColumn(INT_TBL_DAT_OBS_CON).setPreferredWidth(100);      

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_CON, tblDat);
            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CAN_CON};
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
                case INT_TBL_DAT_NUM_CON:
                    strMsg = "Número de conteo";
                    break;                    
                case INT_TBL_DAT_COD_EMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg = "Código del local";
                    break;                    
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg = "Código del documento";
                    break;                    
                case INT_TBL_DAT_COD_REG:
                    strMsg = "Código del registro";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código del item maestro";
                    break;
                case INT_TBL_DAT_CAN_CON:
                    strMsg = "Cantidad contada";
                    break;
                case INT_TBL_DAT_COD_USR_CON:
                    strMsg = "Código del usuario de conteo";
                    break;
                case INT_TBL_DAT_USR_CON:
                    strMsg = "Usuario de conteo";
                    break;
                case INT_TBL_DAT_NOM_USR_CON:
                    strMsg = "Nombre del usuario de conteo";
                    break;
                case INT_TBL_DAT_FEC_CON:
                    strMsg = "Fecha de conteo realizado";
                    break;  
                case INT_TBL_DAT_OBS_CON:
                    strMsg = "Observación de conteo";
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
        butCer = new javax.swing.JButton();

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
        lblTit.setText("Histórico de conteos");
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

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(651, 471));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        dispose();
    }//GEN-LAST:event_butCerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCer;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
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
     * Cargar Histórico de conteos por item.
     * @return 
     */
    private boolean cargarHisItmCon() 
    {
        boolean blnRes = true;
        int intNumCon=0;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT x.* ";
                strSQL+=" FROM (";
                strSQL+="     SELECT a1.co_emp AS co_empCon, a1.co_locRel AS co_locCon, a1.co_tipDocRel AS co_tipDocCon";
                strSQL+="          , a1.co_docRel AS co_docCon, a1.co_regRel AS co_regCon";
                strSQL+="          , a1.co_itm, a2.co_itmMae, a1.nd_stkCon AS nd_canCon, a1.fe_reaCon";
                strSQL+="          , a3.co_usr as co_usrCon, a3.tx_usr as tx_usrCon, a3.tx_nom as tx_nomUsrCon, a1.tx_obs1";  
                strSQL+="     FROM (tbh_conInv AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+="     INNER JOIN tbm_usr AS a3 ON a1.co_usrResCon=a3.co_usr";
                strSQL+="     WHERE a1.co_emp=" + intCodEmpCon;
                strSQL+="     AND a1.co_locRel=" + intCodLocCon;
                strSQL+="     AND a1.co_tipDocRel=" + intCodTipDocCon;
                strSQL+="     AND a1.co_docRel=" + intCodDocCon; 
                strSQL+="     AND a2.co_itmMae=" + intCodItmMaeCon;
                strSQL+="  ) as x";
                strSQL+="  ORDER BY x.fe_reaCon";               
                System.out.println("cargarHisItmCon: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) 
                {
                    intNumCon++;
                    System.out.println("conteo: "+intNumCon);
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_NUM_CON, "Conteo "+intNumCon);
                    vecReg.add(INT_TBL_DAT_COD_EMP,     rst.getString("co_empCon"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,     rst.getString("co_locCon"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipDocCon"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,     rst.getString("co_docCon"));
                    vecReg.add(INT_TBL_DAT_COD_REG,     rst.getString("co_regCon"));
                    vecReg.add(INT_TBL_DAT_COD_ITM,     rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE, rst.getString("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_CAN_CON,     rst.getString("nd_canCon"));
                    vecReg.add(INT_TBL_DAT_COD_USR_CON, rst.getString("co_usrCon"));
                    vecReg.add(INT_TBL_DAT_USR_CON,     rst.getString("tx_usrCon"));
                    vecReg.add(INT_TBL_DAT_NOM_USR_CON, rst.getString("tx_nomUsrCon"));
                    vecReg.add(INT_TBL_DAT_FEC_CON,     rst.getString("fe_reaCon")); 
                    vecReg.add(INT_TBL_DAT_OBS_CON,     rst.getString("tx_obs1"));
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
                //Se ordena para que los conteos se presenten de forma descendente por fecha de realización de conteo.
                
                objTblOrd.ordenar(INT_TBL_DAT_FEC_CON); 
                objTblOrd.cambiarIconoOrdenamiento(INT_TBL_DAT_FEC_CON, objTblOrd.INT_TBL_DES);
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
