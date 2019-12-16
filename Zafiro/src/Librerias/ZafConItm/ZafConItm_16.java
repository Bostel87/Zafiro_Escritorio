/*
 * ZafConItm_16.java 
 *
 * Created on Oct 07, 2015, 17:00 PM
 */
package Librerias.ZafConItm;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.*;

/**
 *
 * @author sistemas9
 */
public class ZafConItm_16 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable
    final int INT_TBL_LIN = 0;                       //Línea
    final int INT_TBL_CODBOD = 1;                    // Código de Bodega.
    final int INT_TBL_NOMBOD = 2;                    // Nombre de Bodega.
    final int INT_TBL_CODITM = 3;                    // Código del Ítem.
    final int INT_TBL_CODALTITM = 4;                 // Código Alterno del Ítem.
    final int INT_TBL_CODLETITM = 5;                 // Código Alterno 2 del Ítem.
    final int INT_TBL_NOMITM = 6;                    // Nombre del Ítem.
    final int INT_TBL_UBIITM = 7;                    // Ubicación del Ítem.
    final int INT_TBL_CODUSR = 8;                    // Código del usuario responsable del conteo.
    final int INT_TBL_NOMUSR = 9;                    // Usuario responsable del conteo.
    final int INT_TBL_BUTBUS = 10;                   // Botón de Búsqueda.

    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    private ZafTblTot objTblTotDocCon;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private String strAux;
    private ArrayList arlDat;
    javax.swing.JDesktopPane dskGen;

    /**
     * Creates new form ZafConItm_16
     */
    public ZafConItm_16(java.awt.Frame parent, boolean modal, java.util.ArrayList vector, ZafParSis obj, javax.swing.JDesktopPane dskGe) 
    {
        super(parent, modal);
        objParSis = obj;
        initComponents();
        arlDat = vector;
        dskGen=dskGe;
        configurarFrm();
        cargarDatos();
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            //Inicializar objetos.
            objUti = new ZafUtil();
            strAux = "Listado de Items Pendientes de Conteo de Inventario ";
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN, "");
            vecCab.add(INT_TBL_CODBOD, "Cód.Bod.");
            vecCab.add(INT_TBL_NOMBOD, "Nom.Bod.");
            vecCab.add(INT_TBL_CODITM, "Cód.Itm.");
            vecCab.add(INT_TBL_CODALTITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_CODLETITM, "Cód.Let.Itm.");
            vecCab.add(INT_TBL_NOMITM, "Item");
            vecCab.add(INT_TBL_UBIITM, "Ubicación");
            vecCab.add(INT_TBL_CODUSR, "Cód.Usr.");
            vecCab.add(INT_TBL_NOMUSR, "Usuario");
            vecCab.add(INT_TBL_BUTBUS, "...");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat, INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODALTITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODLETITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_UBIITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CODUSR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMUSR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTBUS).setPreferredWidth(25);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CODBOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODALTITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_NOMITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODUSR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_BUTBUS, tblDat);

            //Configurar JTable: Editor de búsqueda.
            ZafTblBus objTblBus = new ZafTblBus(tblDat);
            //Configurar JTable: Ordenamiento.
            ZafTblOrd objTblOrd = new ZafTblOrd(tblDat);
            //Configurar JTable: Reordenamiento de Columna.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
           //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BUTBUS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);

            objTblCelRenBut = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBUS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_CODLETITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UBIITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //new ButOrdCon(tblDat, INT_TBL_BUTBUS);
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblDat);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Consulta Orden Conteo */">
//    private class ButOrdCon extends Librerias.ZafTableColBut.ZafTableColBut_uni 
//    {
//        public ButOrdCon(javax.swing.JTable tbl, int intIdx) 
//        {
//            super(tbl, intIdx, "Orden de Conteo");
//        }
//
//        public void butCLick()
//        {
//            int intCol = tblDat.getSelectedRow();
//            String strCodBod = tblDat.getValueAt(intCol, INT_TBL_CODBOD).toString();
//            String strCodItm = tblDat.getValueAt(intCol, INT_TBL_CODITM).toString();
//            String strCodUsrResCon = tblDat.getValueAt(intCol, INT_TBL_CODUSR).toString();
//            invocarClase("Ventas.ZafVen01.ZafVen01", strCodBod, strCodItm, strCodUsrResCon); //rose ojo
//        }
//    }
//
//    private boolean invocarClase(String clase, String strCodBod, String strCodItm, String strCodUsrResCon) 
//    {
//        boolean blnRes = true;
//        try 
//        {
//            //Obtener el constructor de la clase que se va a invocar.
//            Class objVen = Class.forName(clase);
//            Class objCla[] = new Class[4];
//            objCla[0] = objParSis.getClass();
//            objCla[1] = new Integer(0).getClass();
//            objCla[2] = new Integer(0).getClass();
//            objCla[3] = new Integer(0).getClass();
//
//            System.out.println("Clase : " + objCla);
//            java.lang.reflect.Constructor objCon = objVen.getConstructor(objCla);
//            //Inicializar el constructor que se obtuvo.
//            Object objObj[] = new Object[4];
//            objObj[0] = objParSis;
//            objObj[1] = new Integer(Integer.parseInt(strCodBod));
//            objObj[2] = new Integer(Integer.parseInt(strCodItm));
//            objObj[3] = new Integer(Integer.parseInt(strCodUsrResCon));
//
//            javax.swing.JInternalFrame ifrVen;
//            ifrVen = (javax.swing.JInternalFrame) objCon.newInstance(objObj);
//
//            dskGen.add(ifrVen, javax.swing.JLayeredPane.DEFAULT_LAYER);
//            ifrVen.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
//                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
//                }
//
//                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
//                    System.gc();
//                }
//
//                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
//                }
//
//                public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
//                }
//
//                public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
//                }
//
//                public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
//                }
//
//                public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
//                }
//            });
//            ifrVen.show();
//        } catch (ClassNotFoundException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        } catch (NoSuchMethodException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        } catch (SecurityException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        } catch (InstantiationException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        } catch (IllegalAccessException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        } catch (IllegalArgumentException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        } catch (java.lang.reflect.InvocationTargetException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
//</editor-fold>
    
    private void cargarDatos() 
    {
        vecDat.clear();
        for (int i = 0; i < arlDat.size(); i++) 
        {
            vecReg = new Vector();
            vecReg.add(INT_TBL_LIN, "");
            vecReg.add(INT_TBL_CODBOD, objUti.getStringValueAt(arlDat, i, 0));
            vecReg.add(INT_TBL_NOMBOD, objUti.getStringValueAt(arlDat, i, 1));
            vecReg.add(INT_TBL_CODITM, objUti.getStringValueAt(arlDat, i, 2));
            vecReg.add(INT_TBL_CODALTITM, objUti.getStringValueAt(arlDat, i, 3));
            vecReg.add(INT_TBL_CODLETITM, objUti.getStringValueAt(arlDat, i, 4));
            vecReg.add(INT_TBL_NOMITM, objUti.getStringValueAt(arlDat, i, 5));
            vecReg.add(INT_TBL_UBIITM, objUti.getStringValueAt(arlDat, i, 6));
            vecReg.add(INT_TBL_CODUSR, objUti.getStringValueAt(arlDat, i, 7));
            vecReg.add(INT_TBL_NOMUSR, objUti.getStringValueAt(arlDat, i, 8));
            vecReg.add(INT_TBL_BUTBUS, "");
            vecDat.add(vecReg);
        }
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        vecDat.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        ScrollDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        jPanel2.add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel4.add(butCan);

        panBot.add(jPanel4, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        jPanel2.add(panBar, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(53, 50));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(459, 300));

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 24));
        panGenCab.setLayout(null);
        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

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
        ScrollDat.setViewportView(tblDat);

        panDat.add(ScrollDat, java.awt.BorderLayout.CENTER);

        panGen.add(panDat, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", panGen);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        setBounds(800, 600, 416, 397);
    }// </editor-fold>//GEN-END:initComponents


    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        dispose();
    }//GEN-LAST:event_butCanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollDat;
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

}
