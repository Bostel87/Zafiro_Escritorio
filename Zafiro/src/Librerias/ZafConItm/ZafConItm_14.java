/* @author jayapata 
 * ZafConItm_14.java
 * "IMPRIMIR LAS ORDENES DE COMPRA NO IMPRESAS".
 * Created on Oct 21, 2011, 12:17:13 PM
 */

package Librerias.ZafConItm;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

public class ZafConItm_14 extends javax.swing.JDialog 
{
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    private ZafThreadGUI objThrGUI;
    private ZafThreadImpOC objThrImpOC;
    private ZafRptSis objRptSisCom;
    private final int intCodMnuOrdCom = 45;
    private Vector vecAux, vecCab, vecReg, vecDat;
    int intCodTipDocOrdCom;
    String strImpDirectaOC = "compras";
    
    final int INT_TBL_LIN = 0;
    final int INT_TBL_CODEMP = 1;
    final int INT_TBL_CODLOC = 2;
    final int INT_TBL_CODTIPDOC = 3;
    final int INT_TBL_CODDOC = 4;
    final int INT_TBL_TXTIPDC = 5;
    final int INT_TBL_TXTIPDL = 6;
    final int INT_TBL_NUMDOC = 7;
    final int INT_TBL_FECDOC = 8;
    final int INT_TBL_NOMCLI = 9;
    final int INT_TBL_BUTIMP = 10;
 
    //<editor-fold defaultstate="collapsed" desc="/* Cosntructor ZafConItm_14() */">
    public ZafConItm_14(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);

        objParSis = obj;
        objUti = new ZafUtil();

        initComponents();
        intCodTipDocOrdCom=2;
        objRptSisCom = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objParSis);
        configurarFrm();
        cargarDoc();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* ConfigurarFrm(): Configura Formulario */">
    private boolean configurarFrm()
    {
        boolean blnRes = true;
        try 
        {
            //Configurar objetos.
            vecDat = new Vector();
            
            //Almacena las cabeceras
            vecCab = new Vector();   
            vecCab.clear();
            vecCab.add(INT_TBL_LIN, "");
            vecCab.add(INT_TBL_CODEMP, "Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC, "Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC, "Cod.TipDoc.");
            vecCab.add(INT_TBL_CODDOC, "Cod.Doc.");
            vecCab.add(INT_TBL_TXTIPDC, "Des.Cor.");
            vecCab.add(INT_TBL_TXTIPDL, "Des.Lar.");
            vecCab.add(INT_TBL_NUMDOC, "Num.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_NOMCLI, "Nom.Cli.");
            vecCab.add(INT_TBL_BUTIMP, "");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar Columnas Ocultas
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODEMP);
            arlColHid.add("" + INT_TBL_CODLOC);
            arlColHid.add("" + INT_TBL_CODTIPDOC);
            arlColHid.add("" + INT_TBL_CODDOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TXTIPDC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_TXTIPDL).setPreferredWidth(145);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_BUTIMP).setPreferredWidth(25);

            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BUTIMP);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            objTblCelRenBut = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTIMP).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;
            ButImpDoc butImpDoc = new ButImpDoc(tblDat, INT_TBL_BUTIMP);        //Boton Imprime Documento
            objTblCelRenBut = null;

            //Configurar JTable: Editor de la tabla.
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* ButImpDoc: Boton que Imprime Documento */" >
    private class ButImpDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni 
    {
        public ButImpDoc(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl, intIdx, "Documento");
        }

        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString();
            String strCodLoc = tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString();
            String strCodTipDoc = tblDat.getValueAt(intCol, INT_TBL_CODTIPDOC).toString();
            String strCodDoc = tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString();

            if (objThrImpOC == null) 
            {
                objThrImpOC = new ZafThreadImpOC(Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc));
                objThrImpOC.start();
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* ImprimeOrdCom: Funcion que permite Imprimir la Orden de Compra */">
    public boolean imprimeOrdCom(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
        java.sql.Connection conn;
        java.sql.Statement stm;
        String strSql = "";
        String strRutImgLogo = "", strRutRpt="";
        int intNumTotRpt = 0;
        String DIRECCION_REPORTE_COMPRA  = "//Zafiro/Reportes/Compras/ZafCom02/ZafRptCom02.jasper";
                
        try 
        {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stm = conn.createStatement();

                objRptSisCom.cargarListadoReportes(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodMnuOrdCom, objParSis.getCodigoUsuario());
                objRptSisCom.setVisible(true);
                if (objRptSisCom.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) 
                {
                    intNumTotRpt = objRptSisCom.getNumeroTotalReportes();
                    for (int i = 0; i < intNumTotRpt; i++) 
                    {
                        if (objRptSisCom.isReporteSeleccionado(i)) 
                        {
                            switch (Integer.parseInt(objRptSisCom.getCodigoReporte(i)))
                            {
                                case 19:
                                default:
                                    strRutRpt = objRptSisCom.getRutaReporte(i);
                                    strRutImgLogo = objRptSisCom.getRutaReporte(i);
                                    if (objParSis.getCodigoEmpresa() == 1) {
                                        strRutImgLogo += "Logos/logTuv.png";
                                    } else if (objParSis.getCodigoEmpresa() == 2) {
                                        strRutImgLogo += "Logos/logCas.png";
                                    } else {
                                        strRutImgLogo += "Logos/logDim.png";
                                    }

                                    Map parameters = new HashMap();
                                    parameters.put("codEmp", new Integer(intCodEmp));
                                    parameters.put("codLoc", new Integer(intCodLoc));
                                    parameters.put("CodTipDoc", new Integer(intCodTipDoc));
                                    parameters.put("codDoc", new Integer(intCodDoc));
                                    parameters.put("RUTA_LOGO", strRutImgLogo); //Rose

                                    if ((intCodEmp == 1) || (intCodEmp == 4) || ((intCodEmp == 2) && objParSis.getCodigoLocal() != 6)) 
                                    {
                                        if (System.getProperty("os.name").equals("Linux"))
                                        {
                                            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                                            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                                            JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_COMPRA, parameters, conn);
                                            javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strImpDirectaOC, null);
                                            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                                            printServiceAttributeSet.add(printerName);
                                            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                                            objJRPSerExp.exportReport();
                                        }
                                        else //Windows
                                        {
                                            impresionOrdenCompra(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                                        }
                                    } 
                                    else
                                    {
                                        JasperPrint report = JasperFillManager.fillReport(strRutRpt, parameters, conn);
                                        JasperPrintManager.printReport(report, true);
                                    }

                                    strSql = "UPDATE tbm_cabmovinv set st_imp='S' where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + " ";
                                    stm.executeUpdate(strSql);
                                    break;
                            }
                        }
                    }
                }
                stm.close();
                stm = null;
                conn.close();
                conn = null;

                butRefrescarActionPerformed(null);
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (JRException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* CargarDoc(): Carga Documento a Imprimir */">
    private void cargarDoc() 
    {
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSql = "";
        boolean blnCerrarPan = true;
        try 
        {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stm = conn.createStatement();

                vecDat.clear();
                strSql = " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor, a1.tx_deslar, a.ne_numdoc, a.fe_doc, a.tx_nomcli "
                       + " FROM tbm_cabmovinv as a "
                       + " INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc =a.co_tipdoc ) "
                       + " WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + " AND a.co_loc=" + objParSis.getCodigoLocal() + " AND a.co_tipdoc="+intCodTipDocOrdCom+ "AND a.st_imp='N' ";
                rst = stm.executeQuery(strSql);
                System.out.println("Listado de OC Pendientes de Impresion - cargarDoc(): "+strSql);
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CODEMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTIPDOC, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_CODDOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_TXTIPDC, rst.getString("tx_descor"));
                    vecReg.add(INT_TBL_TXTIPDL, rst.getString("tx_deslar"));
                    vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_NOMCLI, rst.getString("tx_nomcli"));
                    vecReg.add(INT_TBL_BUTIMP, "");
                    blnCerrarPan = false;
                    vecDat.add(vecReg);
                }
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

                rst.close();
                stm.close();
                conn.close();
                rst = null;
                stm = null;
                conn = null;

                if (blnCerrarPan) 
                {
                    this.dispose();
                }
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* ImpresionOrdenCompra(): Imprime Orden de Compra - Windows */">
    public boolean impresionOrdenCompra(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
        boolean blnRes = true;
        boolean blnLstRep = false;
        String strRutRpt, strNomRpt;
        String strRutImgLogo = " ";
        Connection conIns = conn;
        int i, intNumTotRpt;
        int intTipRpt = 1;
        System.out.println("ZafComItm_14.impresionOrdenCompra-Windows: CodTipDoc: " + intCodTipDoc + " CoDoc: " + intCodDoc);
        try 
        {
            blnLstRep = existeRep(conIns, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodMnuOrdCom, objParSis.getCodigoUsuario());
            if (blnLstRep)
            {
                objRptSisCom.cargarListadoReportes(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodMnuOrdCom, objParSis.getCodigoUsuario());
                objRptSisCom.setVisible(true);
                if (objRptSisCom.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) 
                {
                    intNumTotRpt = objRptSisCom.getNumeroTotalReportes();
                    for (i = 0; i < intNumTotRpt; i++) 
                    {
                        if (objRptSisCom.isReporteSeleccionado(i)) 
                        {
                            switch (Integer.parseInt(objRptSisCom.getCodigoReporte(i)))
                            {
                                case 19:
                                default:
                                    strRutRpt = objRptSisCom.getRutaReporte(i);
                                    strNomRpt = objRptSisCom.getNombreReporte(i);
                                    System.out.println("ZafComItm_14.impresionOrdenCompra-Windows: strRutRpt: " + strRutRpt + "" + strNomRpt);

                                    strRutImgLogo = objRptSisCom.getRutaReporte(i);
                                    if (objParSis.getCodigoEmpresa() == 1) {
                                        strRutImgLogo += "Logos/logTuv.png";
                                    } else if (objParSis.getCodigoEmpresa() == 2) {
                                        strRutImgLogo += "Logos/logCas.png";
                                    } else {
                                        strRutImgLogo += "Logos/logDim.png";
                                    }
                                    
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    Map params = new HashMap();
                                    params.put("codEmp", new Integer(intCodEmp));
                                    params.put("codLoc", new Integer(intCodLoc));
                                    params.put("CodTipDoc", new Integer(intCodTipDoc));
                                    params.put("codDoc", new Integer(intCodDoc));
                                    params.put("RUTA_LOGO", strRutImgLogo);     //Rose

                                    objRptSisCom.generarReporte(strRutRpt, strNomRpt, params, intTipRpt);
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* ExisteRep(): Verifica si existe Reporte de acuerdo a los permisos de Usuario */">
    private boolean existeRep(Connection conn, int codEmp, int codLoc, int codMnu, int codUsr)
    {
        boolean blnRes = false;
        ResultSet rst;
        PreparedStatement pst;

        String strSql = " select count(*) as cantmnu "
                      + " from tbm_rptSis as a1 "
                      + " inner join tbr_rptSisUsr as a2 on (a1.co_rpt=a2.co_rpt) "
                      + " where a2.co_emp= ? "
                      + " and a2.co_loc= ? "
                      + " and a2.co_mnu= ? "
                      + " and a2.co_usr= ? "
                      + " and a1.st_reg='A' "
                      + " and a2.st_reg in ('A','S') ";
        try 
        {
            if (codUsr == 1) 
            {
                return true;
            }
            pst = conn.prepareStatement(strSql);
            pst.setLong(1, codEmp);
            pst.setLong(2, codLoc);
            pst.setLong(3, codMnu);
            pst.setLong(4, codUsr);
            rst = pst.executeQuery();
            
            if (rst.next()) 
            {
                if (rst.getInt("cantmnu") > 0) {
                    blnRes = true;
                } else {
                    blnRes = false;
                }
            }
        } 
        catch (SQLException e) {
            blnRes = false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes = false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butRefrescar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setText("LISTADO DE ORDENES DE COMPRA PENDIENTES DE IMPRESION");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblDat);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel3);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        butRefrescar.setText("Refrescar");
        butRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRefrescarActionPerformed(evt);
            }
        });
        jPanel5.add(butRefrescar, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5, java.awt.BorderLayout.EAST);
        jPanel4.add(jPanel6, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel4, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-679)/2, (screenSize.height-436)/2, 679, 436);
    }// </editor-fold>//GEN-END:initComponents

    private void butRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRefrescarActionPerformed
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butRefrescarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butRefrescar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    private class ZafThreadGUI extends Thread 
    {
        public ZafThreadGUI() 
        {
        }

        @Override
        public void run() 
        {
            cargarDoc();
            objThrGUI = null;
        }
    }

    private class ZafThreadImpOC extends Thread 
    {
        int intCodEmp = 0;
        int intCodLoc = 0;
        int intCodTipDoc = 0;
        int intCodDoc = 0;

        public ZafThreadImpOC()
        {
        }

        public ZafThreadImpOC(int codEmp, int codLoc, int codTipDoc, int codDoc) 
        {
            intCodEmp = codEmp;
            intCodLoc = codLoc;
            intCodTipDoc = codTipDoc;
            intCodDoc = codDoc;
        }

        @Override
        public void run() 
        {
            imprimeOrdCom(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            objThrImpOC = null;
        }
    }
}