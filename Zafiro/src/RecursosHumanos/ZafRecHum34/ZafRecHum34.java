package RecursosHumanos.ZafRecHum34;

import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

/**
 * Ingresos/Egresos programados de empleados
 *
 * @author Roberto Flores Guayaquil 19/06/2012
 */
public class ZafRecHum34 extends javax.swing.JInternalFrame {

    private java.sql.Connection CONN_GLO = null;
    private java.sql.Statement STM_GLO = null;
    private java.sql.ResultSet rstCab = null;

    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblEdi objTblEdi;                                                       //Editor: Editor del JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                           //Editor: JTextField en celda.
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafDate.ZafDatePicker txtFecIni;
    private ZafMouMotAda objMouMotAda;                                                 //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                                 //PopupMenu: Establecer PeopuMenú en JTable.
    private java.util.Date datFecAux;

//  private ZafDtePckEdi objZafDtePckEdi;
    private String strAux = "";
    private String strCodTra = "";
    private String strCodEgr = "";
    private String strCodEmp = "";
    private String strNomTra = "";
    private String strCodRub = "";
    private String strNomRub = "";

    private Vector vecCab = new Vector();
    private Vector vecDat;

    private ZafUtil objUti;
    private mitoolbar objTooBar;

    private boolean blnHayCam = false;

    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_FECHA = 1;
    private static final int INT_TBL_CUOTA = 2;
    private static final int INT_TBL_CHKANL = 3;

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst;//, rstCab;60

    private ZafVenCon vcoTra;
    private ZafVenCon vcoRub;

    private Vector vecAux;

//  private String strFormat="d/m/y";
    boolean blnMsjFor = false;

    private boolean blnEstCarIngEgrPrg = false;

    private String strVersion = "  v1.28";

    private int intCoReg = -1;
    /**
     * Creates new form revisionTecMer
     */
    public ZafRecHum34(Librerias.ZafParSis.ZafParSis obj) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            objUti = new ZafUtil();

            if (objZafParSis.getCodigoMenu() == 3356) {
                optInde.setVisible(false);
                lblTipEgr.setText("Tipo de ingreso:");
                lblValEgr.setText("Valor del ingreso:");
            }

            objTooBar = new mitoolbar(this);
            this.getContentPane().add(objTooBar, "South");

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            
            PanTabGen.add(txtFecDoc);
            
            txtFecDoc.setBounds(580, 8, 92, 20);
            //txtFecDoc.setEnabled(false);
            txtFecDoc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
            txtFecDoc.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtFecDocActionPerformed(evt);
                }
            });

            txtFecIni = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecIni.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecIni.setText("");
            PanTabGen.add(txtFecIni);
            txtFecIni.setBounds(580, 50, 92, 20);
            //txtFecDoc.setEnabled(false);
            txtFecIni.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecIni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

            txtFecIni.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtNumCuoFocusLost(null);
                }
            });

            txtFecIni.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println("entro mouseclicked....");
                    txtFecMouseCliked(e);
                }

            });

            this.setTitle(objZafParSis.getNombreMenu() + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }
    }

    /**
     * Creates new form ZafRecHum27
     */
    public ZafRecHum34(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodEgr, String strCodTra) {
        try {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());

            this.strCodEmp = strCodEmp;
            this.strCodEgr = strCodEgr;
            this.strCodTra = strCodTra;
            blnEstCarIngEgrPrg = true;

            objUti = new ZafUtil();
            objTooBar = new ZafRecHum34.mitoolbar(this);

            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            PanTabGen.add(txtFecDoc);
            txtFecDoc.setBounds(580, 8, 92, 20);

            txtFecIni = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecIni.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecIni.setText("");
            PanTabGen.add(txtFecIni);
            txtFecIni.setBounds(580, 50, 92, 20);
            //txtFecDoc.setEnabled(false);
            txtFecIni.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecIni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

            txtFecIni.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtNumCuoFocusLost(null);
                }
            });

            txtFecIni.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println("entro mouseclicked....");
                    txtFecMouseCliked(e);
                }

            });

            objTooBar.setVisible(false);
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void txtFecDocActionPerformed(java.awt.event.ActionEvent evt) {
        txtFecDoc.transferFocus();
    }

    private void txtFecMouseCliked(java.awt.event.MouseEvent e) {
        calculaCuotasDetalle(txtNumCuo.getText());
    }

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    public void abrirCon() {
        try {
            CONN_GLO = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void CerrarCon() {
        try {
            CONN_GLO.close();
            CONN_GLO = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    private boolean configurarTblDat() {

        boolean blnRes = true;
        try {

            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_FECHA, "Fecha");
            vecCab.add(INT_TBL_CUOTA, "Cuota");
            vecCab.add(INT_TBL_CHKANL, "Anular");

            objTblMod = new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_CUOTA, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux = new java.util.ArrayList();
            arlAux.add("" + INT_TBL_FECHA);
            arlAux.add("" + INT_TBL_CUOTA);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux = null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Tamaño de las celdas
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FECHA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CUOTA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CHKANL).setPreferredWidth(40);

            //Configurar JTable: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_FECHA);
            vecAux.add("" + INT_TBL_CUOTA);
            vecAux.add("" + INT_TBL_CHKANL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            //objZafDtePckEdi = new ZafDtePckEdi(strFormat);
            //tcmAux.getColumn(INT_TBL_FECHA).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormat));
            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_FECHA).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel = 0;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    try {
                        boolean blnIsFecha = true;
                        String fec[] = objTblMod.getValueAt(intFilSel, INT_TBL_FECHA).toString().split("/");

                        if (fec == null) {
                            blnIsFecha = false;
                        }

                        java.util.Calendar CalVal = java.util.Calendar.getInstance();

                        /*
                         * Si el aÃ±o es menor que 1900 no es una fecha valida
                         * y se retorna false y salimos de la funcion
                         */
                        if (Integer.valueOf(fec[2]) < 1900) {
                            //mostrarMsgInf("AÑO");
                            objTblMod.setValueAt("", intFilSel, INT_TBL_FECHA);
                        }

                        CalVal.set(java.util.Calendar.YEAR, Integer.parseInt(fec[2]));


                        /*
                         * Si el Mes no esta entre 1 y 12 no es una fecha vÃ¡lida
                         * salimos de la funcion retornando false
                         */
                        if (Integer.valueOf(fec[1]) < 1 || Integer.valueOf(fec[1]) > 12) {
                            //mostrarMsgInf("MES");
                            objTblMod.setValueAt("", intFilSel, INT_TBL_FECHA);
                        }

                        CalVal.set(java.util.Calendar.MONTH, Integer.parseInt(fec[1]) - 1);


                        /*
                         * Si el Dia no esta entre 1 y el maximo dia definido para ese mes
                         * salimos de la funcion retornando false
                         */
                        if (Integer.valueOf(fec[0]) < 1 || Integer.valueOf(fec[0]) > CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)) {
                            //mostrarMsgInf("DIA");
                            objTblMod.setValueAt("", intFilSel, INT_TBL_FECHA);
                        }
                    } catch (Exception e) {
                        objTblMod.setValueAt("", intFilSel, INT_TBL_FECHA);
                    }
                }
            });

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_FECHA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CUOTA).setCellRenderer(objTblCelRenLbl);

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CUOTA).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (optDef.isSelected()) {
                        if (optValCuoIgu.isSelected() || optValCuoDes.isSelected()) {
                            calcularDiferencia();
                        }
                    }
                }
            });

            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new ZafTblCelRenChk();

            tcmAux.getColumn(INT_TBL_CHKANL).setCellRenderer(objTblCelRenChk);
            final Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKANL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    java.sql.Connection con = null;
                    java.sql.Statement stmLoc = null;
                    java.sql.ResultSet rstLoc = null;
                    intFilSel = tblDat.getSelectedRow();
                    try {
                        con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                        if (con != null) {

                            stmLoc = con.createStatement();

                            if ((Boolean) objTblMod.getValueAt(intFilSel, INT_TBL_CHKANL)) {

                                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());

                                String patron = "dd/MM/yyyy";
                                SimpleDateFormat formato = new SimpleDateFormat(patron);
                                //System.out.println(formato.format(datFecAux));
                                String strSplit[] = formato.format(datFecAux).split("/");
                                Calendar calObjAux = java.util.Calendar.getInstance();
                                calObjAux.set(Integer.valueOf(strSplit[2]), Integer.valueOf(strSplit[1]) - 1, Integer.valueOf(strSplit[0]));
                                java.util.Date datFecAct = calObjAux.getTime();

                                String strFecEva = objTblMod.getValueAt(intFilSel, INT_TBL_FECHA).toString();
                                String strSplitFecEva[] = strFecEva.split("/");
                                Calendar calFecEva = Calendar.getInstance();
                                calFecEva.set(Integer.valueOf(strSplitFecEva[2]), Integer.valueOf(strSplitFecEva[1]) - 1, Integer.valueOf(strSplitFecEva[0]));
                                java.util.Date datFecEva = calFecEva.getTime();

                                String strSql = "SELECT * FROM tbm_feccorrolpag \n"
                                        + "WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "  \n"
                                        + "AND ne_ani = " + calObjAux.get(Calendar.YEAR) + " \n"
                                        + "AND ne_mes = " + (calObjAux.get(Calendar.MONTH) + 1) + " \n"
                                        + "AND ne_per = 1 \n"
                                        + "order by co_emp, ne_ani, ne_mes, ne_per ";
                                rstLoc = stmLoc.executeQuery(strSql);
                                java.util.Date datFecCorHas = null;
                                while (rstLoc.next()) {
                                    datFecCorHas = rstLoc.getDate("fe_has");
                                }

                                boolean bln = false;
                                if (datFecAct != null && datFecEva != null && datFecCorHas != null) {

                                    String strSplitFecCor[] = formato.format(datFecCorHas).split("/");

                                    if (datFecEva.getTime() > datFecCorHas.getTime()) {
                                        objTblMod.setValueAt(Boolean.TRUE, intFilSel, INT_TBL_CHKANL);
                                        bln = true;
                                        intCoReg = intFilSel;
                                    } else {

                                        boolean blnVerMesAct = false;
                                        if (Integer.parseInt(strSplitFecEva[2]) == Integer.parseInt(strSplit[2]) && Integer.parseInt(strSplitFecEva[1]) - 1 == Integer.parseInt(strSplit[1]) - 1) {
                                            if (Integer.parseInt(strSplitFecEva[0]) <= Integer.parseInt(strSplit[0])) {
                                                blnVerMesAct = true;
                                                bln = true;
                                                intCoReg = intFilSel;
                                            }
                                        }

                                        if (!blnVerMesAct) {

                                            long dif = datFecAct.getTime() - datFecEva.getTime();

                                            if (dif >= 0) {
                                                //                                               objTblMod.setValueAt(Boolean.TRUE, intFilSel, INT_TBL_CHKANL);
                                                dif = datFecAct.getTime() - datFecCorHas.getTime();
                                                if (dif > 0) {
                                                    objTblMod.setValueAt(Boolean.TRUE, intFilSel, INT_TBL_CHKANL);
                                                    bln = true;
                                                    intCoReg = intFilSel;
                                                } else {
                                                    objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_CHKANL);
                                                }
                                            } else {
                                                objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_CHKANL);
                                            }

                                        }

                                    }
                                }
                                for (int intFil = intFilSel; intFil < objTblMod.getRowCount(); intFil++) {
                                    objTblMod.setValueAt(bln, intFil, INT_TBL_CHKANL);
                                }
                            } else {
                                for (int intFil = intFilSel; intFil < objTblMod.getRowCount(); intFil++) {
                                    objTblMod.setValueAt(Boolean.FALSE, intFil, INT_TBL_CHKANL);
                                }
                            }
                        }
                    } catch (Exception e) {

                    } finally {
                        try {
                            rstLoc.close();
                        } catch (Throwable ignore) {
                        }
                        try {
                            stmLoc.close();
                        } catch (Throwable ignore) {
                        }
                        try {
                            con.close();
                        } catch (Throwable ignore) {
                        }
                    }
                }
            });

            objTblCelRenLbl = null;
            objTblCelEdiTxt = null;
            //Configurar JTable: Modo de operación del JTable.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void calcularDiferencia() {
        double dblValEgr = objUti.parseDouble(txtValEgr.getText());
        double dblValPosColCuo = 0;
        for (int intFil = 0; intFil < objTblMod.getRowCount(); intFil++) {
            dblValPosColCuo += objUti.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_CUOTA));
        }

        double dblDif = dblValEgr - dblValPosColCuo;
        txtDif.setText(String.valueOf(objUti.redondear(dblDif, objZafParSis.getDecimalesMostrar())));
    }

    public void Configura_ventana_consulta() {

        configurarVenConTra();
        configurarVenConRub();

        configurarTblDat();

        if (blnEstCarIngEgrPrg) {

            vecDat = null;
            cargarDatos();

        }
    }

    private boolean cargarDatos() {
        boolean blnRes = true;
        try {
            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab != null) {

                if (cargarCabReg()) {
                    if (cargarDetReg()) {
                        blnRes = true;
                    }
                }
//            objAsiDia.setDiarioModificado(false);
                blnHayCam = false;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     *
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg() {
        int intPosRel;
        boolean blnRes = true;
        String strSQL = "";
        java.util.Date datFecDoc;
        java.util.Date datPriCuo;

        try {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "select a.*,(c.tx_ape || ' ' || c.tx_nom) as tx_nomtra,e.co_rub,e.tx_nom as tx_nomrub,b.st_Reg as st_regTra FROM tbm_cabingegrprgtra AS a";
                strSQL += " inner join tbm_tra c on (c.co_tra=a.co_tra)";
                strSQL += " inner join tbm_rubRolPagEmp d on (d.co_rub=a.co_rub and d.co_emp=a.co_emp)";
                strSQL += " inner join tbm_rubRolPag e on (d.co_rub=e.co_rub)";
                strSQL += " inner join tbm_traemp b on (b.co_tra=c.co_tra and a.co_emp=b.co_emp)";
                strSQL += " where a.co_emp = " + strCodEmp;
                strSQL += " AND a.co_egr=" + strCodEgr;
                strSQL += " AND a.co_tra=" + strCodTra;
                //System.out.println("query desde zafrechum55: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    strAux = rst.getString("co_tra");
                    txtCodTra.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("tx_nomtra");
                    txtNomTra.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("co_rub");
                    txtCodRub.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("tx_nomrub");
                    txtNomRub.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("co_egr");
                    txtCodDoc.setText((strAux == null) ? "" : strAux);

                    datFecDoc = rst.getDate("fe_doc");
                    txtFecDoc.setText(objUti.formatearFecha(datFecDoc, "dd/MM/yyyy"));

                    datPriCuo = rst.getDate("fe_pricuo");
                    txtFecIni.setText(objUti.formatearFecha(datPriCuo, "dd/MM/yyyy"));

                    txtValEgr.setText("" + objUti.redondear(rst.getDouble("nd_valegrprg"), objZafParSis.getDecimalesMostrar()));

                    strAux = rst.getString("ne_numcuo");
                    txtNumCuo.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("tx_obs1");
                    txtObs1.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_obs2");
                    txtObs2.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("tx_tipegrprg");
                    if (strAux.equals("I")) {
                        optInde.setSelected(true);
                        optValTodCuo.setSelected(true);
                        optValCuoIgu.setSelected(false);
                        optValCuoDes.setSelected(false);
                        optDef.setSelected(false);
                    } else if (strAux.equals("D")) {
                        optDef.setSelected(true);
                        optInde.setSelected(false);
                        strAux = rst.getString("tx_tipcuo");
                        if (strAux.equals("M")) {
                            optValTodCuo.setSelected(true);
                            optValCuoIgu.setSelected(false);
                            optValCuoDes.setSelected(false);
                        } else if (strAux.equals("I")) {
                            optValTodCuo.setSelected(false);
                            optValCuoIgu.setSelected(true);
                            optValCuoDes.setSelected(false);
                        } else if (strAux.equals("D")) {
                            optValTodCuo.setSelected(false);
                            optValCuoIgu.setSelected(false);
                            optValCuoDes.setSelected(true);
                        }

                    }

                    //Mostrar el estado del registro.
                    strAux = rst.getString("st_reg");
                    if (strAux.equals("A")) {
                        strAux = "Activo";
                    } else if (strAux.equals("I")) {
                        strAux = "Anulado";
                    } else {
                        strAux = "Otro";
                    }
                    objTooBar.setEstadoRegistro(strAux);
                    strAux = rst.getString("st_regTra");
                    verificaSiEsEmpleadoInactivo(strAux);
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
//                    clnTextos();
                    blnRes = false;
                }
            }

            //Mostrar la posición relativa del registro.
            intPosRel = rst.getRow();
            //rst.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rst.getRow());
//            rst.absolute(intPosRel);

//            rst.close();
//            stm.close();
//            con.close();
//            rst=null;
//            stm=null;
//            con=null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     *
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {

        String strSQL;
        boolean blnRes = true;
        java.util.Date datFecCuo;
        try {
//            objTooBar.setMenSis("Obteniendo datos...");
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "select * from tbm_detingegrprgtra";
                strSQL += " where co_emp = " + rst.getString("co_emp");
                strSQL += " and co_egr = " + rst.getString("co_egr");
                strSQL += " ORDER BY co_emp, co_egr, co_reg";
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat = new Vector();
                //vecDat.clear();
                //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
                while (rst.next()) {
                    Vector vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    datFecCuo = rst.getDate("fe_cuo");
                    vecReg.add(INT_TBL_FECHA, objUti.formatearFecha(datFecCuo, "dd/MM/yyyy"));
                    vecReg.add(INT_TBL_CUOTA, objUti.parseDouble(rst.getObject("nd_valcuo")));
                    String strStReg = rst.getString("st_reg");
                    if (strStReg == null) {
                        vecReg.add(INT_TBL_CHKANL, false);
                    } else {

                        if (strStReg.compareTo("A") == 0) {
                            vecReg.add(INT_TBL_CHKANL, false);
                        } else if (strStReg.compareTo("I") == 0) {
                            vecReg.add(INT_TBL_CHKANL, true);
                        }
                    }
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
                vecDat.clear();
//                objTooBar.setMenSis("Listo");
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        butTra = new javax.swing.JButton();
        lblFecIni = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        lblNumCuo = new javax.swing.JLabel();
        lblRub = new javax.swing.JLabel();
        txtCodRub = new javax.swing.JTextField();
        txtNomRub = new javax.swing.JTextField();
        butRub = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        lblTipCuo = new javax.swing.JLabel();
        txtNumCuo = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        lblTipEgr = new javax.swing.JLabel();
        optDef = new javax.swing.JRadioButton();
        optInde = new javax.swing.JRadioButton();
        optValTodCuo = new javax.swing.JRadioButton();
        optValCuoIgu = new javax.swing.JRadioButton();
        optValCuoDes = new javax.swing.JRadioButton();
        lblFecDoc = new javax.swing.JLabel();
        lblValEgr = new javax.swing.JLabel();
        txtValEgr = new javax.swing.JTextField();
        lblDif = new javax.swing.JLabel();
        txtDif = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        panDetTraDep = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            //protected javax.swing.table.JTableHeader createDefaultTableHeader()
            //{
                //return new ZafTblHeaGrp(columnModel);
                //}
        };
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs3 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        spnObs4 = new javax.swing.JScrollPane();
        txtObs2 = new javax.swing.JTextArea();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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
                formInternalFrameClosing(evt);
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
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        panCen.setLayout(new java.awt.BorderLayout());

        tabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 150));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Empleado:"); // NOI18N
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        butTra.setText("jButton1"); // NOI18N
        butTra.setPreferredSize(new java.awt.Dimension(20, 20));
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        PanTabGen.add(butTra);
        butTra.setBounds(420, 10, 20, 20);

        lblFecIni.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecIni.setText("Fecha de inicio:"); // NOI18N
        PanTabGen.add(lblFecIni);
        lblFecIni.setBounds(450, 50, 130, 20);

        txtCodTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodTra);
        txtCodTra.setBounds(140, 10, 70, 20);

        lblNumCuo.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumCuo.setText("Número de cuotas:"); // NOI18N
        PanTabGen.add(lblNumCuo);
        lblNumCuo.setBounds(450, 90, 140, 20);

        lblRub.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblRub.setText("Rubro:"); // NOI18N
        PanTabGen.add(lblRub);
        lblRub.setBounds(10, 30, 110, 20);

        txtCodRub.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodRub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodRubActionPerformed(evt);
            }
        });
        txtCodRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodRubFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodRubFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodRub);
        txtCodRub.setBounds(140, 30, 70, 20);

        txtNomRub.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomRubActionPerformed(evt);
            }
        });
        txtNomRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomRubFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomRubFocusLost(evt);
            }
        });
        PanTabGen.add(txtNomRub);
        txtNomRub.setBounds(210, 30, 210, 20);

        butRub.setText(".."); // NOI18N
        butRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRubActionPerformed(evt);
            }
        });
        butRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                butRubFocusGained(evt);
            }
        });
        PanTabGen.add(butRub);
        butRub.setBounds(420, 30, 20, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("Código del Documento:"); // NOI18N
        PanTabGen.add(lblNumDoc);
        lblNumDoc.setBounds(450, 30, 140, 20);

        lblTipCuo.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipCuo.setText("Tipo de cuotas:"); // NOI18N
        PanTabGen.add(lblTipCuo);
        lblTipCuo.setBounds(10, 80, 120, 20);

        txtNumCuo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumCuo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumCuoActionPerformed(evt);
            }
        });
        txtNumCuo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumCuoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumCuoFocusLost(evt);
            }
        });
        txtNumCuo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumCuoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumCuoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumCuoKeyTyped(evt);
            }
        });
        PanTabGen.add(txtNumCuo);
        txtNumCuo.setBounds(580, 90, 92, 20);
        //txtNumCuo.getDocument().addDocumentListener(new MyDocumentListener(this));

        txtCodDoc.setEditable(false);
        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDocActionPerformed(evt);
            }
        });
        txtCodDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDocFocusLost(evt);
            }
        });
        PanTabGen.add(txtCodDoc);
        txtCodDoc.setBounds(580, 30, 92, 20);

        lblTipEgr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipEgr.setText("Tipo de egreso:"); // NOI18N
        PanTabGen.add(lblTipEgr);
        lblTipEgr.setBounds(10, 52, 110, 20);

        optDef.setText("Definido");
        optDef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optDefActionPerformed(evt);
            }
        });
        PanTabGen.add(optDef);
        optDef.setBounds(140, 52, 80, 20);

        optInde.setText("Indefinido");
        optInde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optIndeActionPerformed(evt);
            }
        });
        PanTabGen.add(optInde);
        optInde.setBounds(230, 52, 100, 20);

        optValTodCuo.setText("Aplicar el mismo valor a todas las cuotas");
        optValTodCuo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optValTodCuoActionPerformed(evt);
            }
        });
        PanTabGen.add(optValTodCuo);
        optValTodCuo.setBounds(140, 80, 300, 20);

        optValCuoIgu.setText("Dividir el valor en cuotas iguales");
        optValCuoIgu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optValCuoIguActionPerformed(evt);
            }
        });
        PanTabGen.add(optValCuoIgu);
        optValCuoIgu.setBounds(140, 100, 179, 20);

        optValCuoDes.setText("Dividir el valor en cuotas desiguales");
        optValCuoDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optValCuoDesActionPerformed(evt);
            }
        });
        PanTabGen.add(optValCuoDes);
        optValCuoDes.setBounds(140, 120, 300, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha del Documento:"); // NOI18N
        PanTabGen.add(lblFecDoc);
        lblFecDoc.setBounds(450, 10, 130, 20);

        lblValEgr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblValEgr.setText("Valor del egreso:"); // NOI18N
        PanTabGen.add(lblValEgr);
        lblValEgr.setBounds(450, 70, 140, 20);

        txtValEgr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtValEgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValEgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValEgrActionPerformed(evt);
            }
        });
        txtValEgr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValEgrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValEgrFocusLost(evt);
            }
        });
        txtValEgr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValEgrKeyPressed(evt);
            }
        });
        PanTabGen.add(txtValEgr);
        txtValEgr.setBounds(580, 70, 92, 20);

        lblDif.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDif.setText("Diferencia:"); // NOI18N
        PanTabGen.add(lblDif);
        lblDif.setBounds(450, 110, 140, 20);

        txtDif.setEditable(false);
        txtDif.setBackground(objZafParSis.getColorCamposSistema());
        txtDif.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDif.setText("0.0");
        txtDif.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDifFocusGained(evt);
            }
        });
        txtDif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDifActionPerformed(evt);
            }
        });
        PanTabGen.add(txtDif);
        txtDif.setBounds(580, 110, 92, 20);

        txtNomTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        PanTabGen.add(txtNomTra);
        txtNomTra.setBounds(210, 10, 210, 20);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panDetTraDep.setLayout(new java.awt.BorderLayout());

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
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        scrTbl.setViewportView(tblDat);

        panDetTraDep.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panDetTraDep, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 80));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:"); // NOI18N
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txtObs1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs1.setLineWrap(true);
        spnObs3.setViewportView(txtObs1);

        panTxa.add(spnObs3);

        txtObs2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObs2.setLineWrap(true);
        spnObs4.setViewportView(txtObs2);

        panTxa.add(spnObs4);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        panTabGen.add(panObs, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    public void calculaCuotasDetalle(String action) {

        boolean blnRes = false;

        try {
            String patron = "dd/MM/yyyy";
            SimpleDateFormat formato = new SimpleDateFormat(patron);

            if (!action.equals("")) {

//            if(Integer.valueOf(action)>60){
//                String strMsg = "<HTML>El <FONT COLOR=\"blue\">Número de Cuotas</FONT> excede a 5 años (60 meses).</HTML> \n";
//                strMsg+="¿Está seguro que desea asignar ese valor?";
//                javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
//                String strTit;
//                strTit="Mensaje del sistema Zafiro";
//                if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) != 0 ) {
//                    txtNumCuo.selectAll();
//                    txtNumCuo.requestFocus();
//                }
//                else{
                int intCanFil = Integer.valueOf(action);
                if (intCanFil <= 0) {
                    txtNumCuo.setText("");
                    return;
                }
                if (optValTodCuo.isSelected() || optValCuoIgu.isSelected() || optValCuoDes.isSelected()) {
                    objTblMod.setRowCount(intCanFil);
                } else {
                    return;
                }

                if (optValCuoIgu.isSelected()) {//Dividir el valor en cuotas iguales

                    int FechaIni[] = txtFecIni.getFecha(txtFecIni.getText());
                    double dblCuo = calcularCuotas(intCanFil);
                    double dblCanRea = 0;

                    for (int intFil = 0; intFil < intCanFil; intFil++) {

                        if (intFil < intCanFil - 1) {
                            dblCanRea += objUti.redondear(dblCuo, objZafParSis.getDecimalesMostrar());
                        }

                        objTblMod.setValueAt(objUti.redondear(dblCuo, objZafParSis.getDecimalesMostrar()), intFil, INT_TBL_CUOTA);
                        Calendar cal = Calendar.getInstance();
                        cal.set(FechaIni[2], (FechaIni[1] - 1) + intFil, 1);
                        java.util.Date datFec = cal.getTime();
                        objTblMod.setValueAt(formato.format(datFec), intFil, INT_TBL_FECHA);
                        objTblMod.setValueAt(Boolean.FALSE, intFil, INT_TBL_CHKANL);
                    }

                    double dblValEgrRed = objUti.redondear(objUti.parseDouble(txtValEgr.getText()), objZafParSis.getDecimalesMostrar());
                    double dblUltCuo = objUti.redondear(dblValEgrRed - dblCanRea, objZafParSis.getDecimalesMostrar());

                    objTblMod.setValueAt(dblUltCuo, intCanFil - 1, INT_TBL_CUOTA);
                }

                if (optValTodCuo.isSelected()) {//Aplicar el mismo valor a todas las cuotas

                    int FechaIni[] = txtFecIni.getFecha(txtFecIni.getText());

                    for (int intFil = 0; intFil < intCanFil; intFil++) {

                        objTblMod.setValueAt(objUti.redondear(objUti.parseDouble(txtValEgr.getText()), objZafParSis.getDecimalesMostrar()), intFil, INT_TBL_CUOTA);
                        Calendar cal = Calendar.getInstance();
                        cal.set(FechaIni[2], (FechaIni[1] - 1) + intFil, 1);
                        java.util.Date datFec = cal.getTime();
                        objTblMod.setValueAt(formato.format(datFec), intFil, INT_TBL_FECHA);
                        objTblMod.setValueAt(Boolean.FALSE, intFil, INT_TBL_CHKANL);
                    }
                }

                if (optValCuoDes.isSelected()) {//Aplicar el mismo valor a todas las cuotas

                    int FechaIni[] = txtFecIni.getFecha(txtFecIni.getText());

                    for (int intFil = 0; intFil < intCanFil; intFil++) {

                        Calendar cal = Calendar.getInstance();
                        cal.set(FechaIni[2], (FechaIni[1] - 1) + intFil, 1);
                        java.util.Date datFec = cal.getTime();
                        objTblMod.setValueAt(formato.format(datFec), intFil, INT_TBL_FECHA);
                        objTblMod.setValueAt(Boolean.FALSE, intFil, INT_TBL_CHKANL);
                    }
                }

                if (optDef.isSelected()) {
                    if (optValCuoIgu.isSelected() || optValCuoDes.isSelected()) {
                        calcularDiferencia();
                    }
                }

                blnRes = true;

//                }
//            }
            } else {
                objTblMod.removeAllRows();
            }
        } catch (NumberFormatException ex) {
            //blnMsjFor=true;
            blnRes = false;
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de cuotas</FONT> contiene formato érroneo..<BR>Escriba o seleccione un Número de cuotas y vuelva a intentarlo.</HTML>");
            txtNumCuo.setText("");
            txtDif.setText("0.0");
            txtNumCuo.requestFocus();
            objTblMod.removeAllRows();
        } catch (Exception ex) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, ex);
        }
        //return blnRes;
    }

    private double calcularCuotas(int intNumCuo) {

        double dblCuo = 0;
        if (optDef.isSelected()) {//Tipo de egreso: Definido
            if (optValCuoIgu.isSelected()) {//Dividir el valor en cuotas iguales
                double dblValEgr = objUti.parseDouble(txtValEgr.getText());
                dblCuo = objUti.parseDouble(dblValEgr / intNumCuo);
            }
        }
        return dblCuo;
    }

private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
    txtCodTra.transferFocus();
}//GEN-LAST:event_txtCodTraActionPerformed

private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
    strCodTra = txtCodTra.getText();
    txtCodTra.selectAll();
}//GEN-LAST:event_txtCodTraFocusGained

private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
    if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
        if (txtCodTra.getText().equals("")) {
            txtCodTra.setText("");
            txtNomTra.setText("");
        } else {
            BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
        }
    } else {
        txtCodTra.setText(strCodTra);
    }

    calculaCuotasDetalle(txtNumCuo.getText());
}//GEN-LAST:event_txtCodTraFocusLost

    public void BuscarTra(String campo, String strBusqueda, int tipo) {

        vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda)) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        } else {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            } else {
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
            }
        }
        verificaSiEsEmpleadoInactivo(vcoTra.getValueAt(4));
    }

private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
    strCodTra = txtCodTra.getText();
    mostrarVenConTra(0);
}//GEN-LAST:event_butTraActionPerformed

    private boolean mostrarVenConTra(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        verificaSiEsEmpleadoInactivo(vcoTra.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                    } else {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        } else {
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    verificaSiEsEmpleadoInactivo(vcoTra.getValueAt(4));
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                    } else {
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        } else {
                            txtNomTra.setText(strNomTra);
                        }
                    }
                    verificaSiEsEmpleadoInactivo(vcoTra.getValueAt(4));
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConRub(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoRub.setCampoBusqueda(1);
                    vcoRub.show();
                    if (vcoRub.getSelectedButton() == vcoRub.INT_BUT_ACE) {
                        txtCodRub.setText(vcoRub.getValueAt(1));
                        txtNomRub.setText(vcoRub.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoRub.buscar("a1.co_rub", txtCodRub.getText())) {
                        txtCodRub.setText(vcoRub.getValueAt(1));
                        txtNomRub.setText(vcoRub.getValueAt(2));
                    } else {
                        vcoRub.setCampoBusqueda(0);
                        vcoRub.setCriterio1(11);
                        vcoRub.cargarDatos();
                        vcoRub.show();
                        if (vcoRub.getSelectedButton() == vcoRub.INT_BUT_ACE) {
                            txtCodRub.setText(vcoRub.getValueAt(1));
                            txtNomRub.setText(vcoRub.getValueAt(2));
                        } else {
                            txtCodRub.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoRub.buscar("a1.tx_nom", txtNomRub.getText())) {
                        txtCodRub.setText(vcoRub.getValueAt(1));
                        txtNomRub.setText(vcoRub.getValueAt(2));
                    } else {
                        vcoRub.setCampoBusqueda(1);
                        vcoRub.setCriterio1(11);
                        vcoRub.cargarDatos();
                        vcoRub.show();
                        if (vcoRub.getSelectedButton() == vcoRub.INT_BUT_ACE) {
                            txtCodRub.setText(vcoRub.getValueAt(1));
                            txtNomRub.setText(vcoRub.getValueAt(2));
                        } else {
                            txtNomRub.setText(strNomTra);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    // TODO add your handling code here:
    try {

        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {

            if (rstCab != null) {
                rstCab.close();
                rstCab = null;
            }

            if (rst != null) {
                rstCab.close();
                rstCab = null;
            }

            if (STM_GLO != null) {
                STM_GLO.close();
                STM_GLO = null;
            }

            if (con != null) {
                con.close();
                con = null;
            }

            if (conCab != null) {
                conCab.close();
                conCab = null;
            }

            if (stm != null) {
                stm.close();
                stm = null;
            }

            if (stmCab != null) {
                stmCab.close();
                stmCab = null;
            }

            System.gc();
            dispose();
        }
    } catch (java.sql.SQLException e) {
        objUti.mostrarMsgErr_F1(this, e);
    } catch (Exception Evt) {
        objUti.mostrarMsgErr_F1(this, Evt);
    }
}//GEN-LAST:event_formInternalFrameClosing

    private void txtCodRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodRubActionPerformed
        txtCodRub.transferFocus();
    }//GEN-LAST:event_txtCodRubActionPerformed

    private void txtCodRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRubFocusGained
        // TODO add your handling code here:
        strCodRub = txtCodRub.getText();
        txtCodRub.selectAll();
    }//GEN-LAST:event_txtCodRubFocusGained

    private void txtCodRubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRubFocusLost
        // TODO add your handling code here:
        if (!txtCodRub.getText().equalsIgnoreCase(strCodRub)) {
            if (txtCodRub.getText().equals("")) {
                txtCodRub.setText("");
                txtNomRub.setText("");
            } else {
                BuscarRub("a1.co_rub", txtCodRub.getText(), 0);
            }
        } else {
            txtCodRub.setText(strCodRub);
        }

        calculaCuotasDetalle(txtNumCuo.getText());
    }//GEN-LAST:event_txtCodRubFocusLost

    public void BuscarRub(String campo, String strBusqueda, int tipo) {
        vcoRub.setTitle("Listado de Egresos Programados");
        if (vcoRub.buscar(campo, strBusqueda)) {
            txtCodRub.setText(vcoRub.getValueAt(1));
            txtNomRub.setText(vcoRub.getValueAt(2));
        } else {
            vcoRub.setCampoBusqueda(tipo);
            vcoRub.cargarDatos();
            vcoRub.show();
            if (vcoRub.getSelectedButton() == vcoRub.INT_BUT_ACE) {
                txtCodRub.setText(vcoRub.getValueAt(1));
                txtNomRub.setText(vcoRub.getValueAt(2));
            } else {
                txtCodRub.setText(strCodRub);
                txtNomRub.setText(strNomRub);
            }
        }
    }

    private void txtNomRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomRubActionPerformed
        // TODO add your handling code here:
        txtNomRub.transferFocus();
    }//GEN-LAST:event_txtNomRubActionPerformed

    private void txtNomRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRubFocusGained
        // TODO add your handling code here:
        strNomRub = txtNomRub.getText();
        txtNomRub.selectAll();
    }//GEN-LAST:event_txtNomRubFocusGained

    private void txtNomRubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRubFocusLost
        // TODO add your handling code here:
        if (txtNomRub.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomRub.getText().equalsIgnoreCase(strNomRub)) {
                if (txtNomRub.getText().equals("")) {
                    txtCodRub.setText("");
                    txtNomRub.setText("");
                } else {
                    mostrarVenConRub(2);
                }
            } else {
                txtNomRub.setText(strNomRub);
            }
        }

        calculaCuotasDetalle(txtNumCuo.getText());
    }//GEN-LAST:event_txtNomRubFocusLost

    private void butRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRubActionPerformed
        strCodRub = txtCodRub.getText();
        mostrarVenConRub(0);
    }//GEN-LAST:event_butRubActionPerformed

    private void butRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butRubFocusGained

    }//GEN-LAST:event_butRubFocusGained

    private void txtNumCuoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCuoFocusGained
        // TODO add your handling code here:
        txtNumCuo.selectAll();
    }//GEN-LAST:event_txtNumCuoFocusGained

    private void optIndeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optIndeActionPerformed
        // TODO add your handling code here:
        optDef.setSelected(false);
        optInde.setSelected(true);
        txtNumCuo.setEditable(false);
        optValTodCuo.setSelected(true);
        optValCuoIgu.setSelected(false);
        optValCuoDes.setSelected(false);
        optValCuoIgu.setEnabled(false);
        optValCuoDes.setEnabled(false);
        optValTodCuo.setEnabled(false);
        objTblMod.removeAllRows();
        txtNumCuo.setText("");

    }//GEN-LAST:event_optIndeActionPerformed

    private void optValTodCuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optValTodCuoActionPerformed
        // TODO add your handling code here:
        optDef.setSelected(true);
        optInde.setSelected(false);
        optValTodCuo.setSelected(true);
        optValCuoIgu.setSelected(false);
        optValCuoDes.setSelected(false);

        if (!blnMsjFor) {
            //txtNumCuoFocusLost(null);
            calculaCuotasDetalle(txtNumCuo.getText());
        }
    }//GEN-LAST:event_optValTodCuoActionPerformed

    private void txtValEgrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValEgrFocusGained
        // TODO add your handling code here:
        txtValEgr.selectAll();
    }//GEN-LAST:event_txtValEgrFocusGained

    private void txtDifFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDifFocusGained
        // TODO add your handling code here:
        txtDif.selectAll();
    }//GEN-LAST:event_txtDifFocusGained

    private void optDefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDefActionPerformed
        // TODO add your handling code here:
        optDef.setSelected(true);
        optInde.setSelected(false);
        txtNumCuo.setEditable(true);
        optValTodCuo.setEnabled(true);
        optValTodCuo.setSelected(false);
        optValCuoIgu.setEnabled(true);
        optValCuoDes.setEnabled(true);
        if (optValTodCuo.isSelected() || optValCuoIgu.isSelected()) {
            calculaCuotasDetalle(txtNumCuo.getText());
        }
    }//GEN-LAST:event_optDefActionPerformed

    private void optValCuoIguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optValCuoIguActionPerformed
        // TODO add your handling code here:
        optDef.setSelected(true);
        optInde.setSelected(false);
        optValTodCuo.setSelected(false);
        optValCuoIgu.setSelected(true);
        optValCuoDes.setSelected(false);

        if (!blnMsjFor) {
            calculaCuotasDetalle(txtNumCuo.getText());
        }
    }//GEN-LAST:event_optValCuoIguActionPerformed

    private void optValCuoDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optValCuoDesActionPerformed
        // TODO add your handling code here:
        optDef.setSelected(true);
        optInde.setSelected(false);
        optValTodCuo.setSelected(false);
        optValCuoIgu.setSelected(false);
        optValCuoDes.setSelected(true);
        if (objTblMod.getRowCount() > 0) {
            for (int intFil = 0; intFil < objTblMod.getRowCount(); intFil++) {
                objTblMod.setValueAt("", intFil, INT_TBL_CUOTA);
            }
        }

        if (!blnMsjFor) {
            calculaCuotasDetalle(txtNumCuo.getText());
        }

    }//GEN-LAST:event_optValCuoDesActionPerformed

    private void txtValEgrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValEgrFocusLost
        // TODO add your handling code here:

        try {

            if (!txtValEgr.getText().equals("")) {
                double dblValEgrRed = Double.parseDouble(txtValEgr.getText());
                dblValEgrRed = objUti.redondear(objUti.parseDouble(txtValEgr.getText()), objZafParSis.getDecimalesMostrar());
                if (dblValEgrRed < 0) {
                    txtValEgr.setText("");
                    txtValEgr.requestFocus();
                    objTblMod.removeAllRows();
                } else {

                    if (dblValEgrRed > 10000) {
                        String strMsg = "<HTML>El <FONT COLOR=\"blue\">Valor del Egreso</FONT> es mayor a $10.000.</HTML> \n";
                        strMsg += "¿Está seguro que desea asignar ese valor?";
                        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                        String strTit;
                        strTit = "Mensaje del sistema Zafiro";
                        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {

                            txtValEgr.setText(String.valueOf(dblValEgrRed));
                            calculaCuotasDetalle(txtNumCuo.getText());
                        } else {
                            txtValEgr.selectAll();
                            txtValEgr.requestFocus();
                        }
                    } else {
                        txtValEgr.setText(String.valueOf(dblValEgrRed));
                        calculaCuotasDetalle(txtNumCuo.getText());
                    }
                }
            } else {
                objTblMod.removeAllRows();
            }
        } catch (NumberFormatException e) {
            txtValEgr.setText("");
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Valor del Egreso</FONT> contiene formato érroneo..<BR>Escriba o seleccione un Valor del Egreso y vuelva a intentarlo.</HTML>");
            txtValEgr.requestFocus();
            objTblMod.removeAllRows();
        }


    }//GEN-LAST:event_txtValEgrFocusLost

    private void txtNumCuoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCuoFocusLost
//        // TODO add your handling code here:

        try {

            if (!txtNumCuo.getText().equals("")) {
                int intNumCuo = Integer.parseInt(txtNumCuo.getText());
                if (intNumCuo <= 0) {
                    txtNumCuo.setText("");
                    txtNumCuo.requestFocus();
                    objTblMod.removeAllRows();
                } else {

                    if (intNumCuo > 60) {
                        String strMsg = "<HTML>El <FONT COLOR=\"blue\">Número de Cuota</FONT> excede a 5 años (60 meses).</HTML> \n";
                        strMsg += "¿Está seguro que desea asignar ese valor?";
                        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                        String strTit;
                        strTit = "Mensaje del sistema Zafiro";
                        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == 0) {

                            txtNumCuo.setText(String.valueOf(intNumCuo));
                            calculaCuotasDetalle(txtNumCuo.getText());
                        } else {
                            txtNumCuo.selectAll();
                            txtNumCuo.requestFocus();
                        }
                    } else {
                        txtNumCuo.setText(String.valueOf(intNumCuo));
                        calculaCuotasDetalle(txtNumCuo.getText());
                    }
                }
            } else {
                objTblMod.removeAllRows();
            }
        } catch (NumberFormatException e) {
            txtNumCuo.setText("");
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Cuotas</FONT> contiene formato érroneo..<BR>Escriba o seleccione un Número de Cuota y vuelva a intentarlo.</HTML>");
            txtNumCuo.requestFocus();
            objTblMod.removeAllRows();
        }


    }//GEN-LAST:event_txtNumCuoFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra = txtNomTra.getText();
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) {
                if (txtNomTra.getText().equals("")) {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                } else {
                    mostrarVenConTra(2);
                }
            } else {
                txtNomTra.setText(strNomTra);
            }
        }

        calculaCuotasDetalle(txtNumCuo.getText());
    }//GEN-LAST:event_txtNomTraFocusLost

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseMoved

    private void txtNumCuoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumCuoKeyPressed

    }//GEN-LAST:event_txtNumCuoKeyPressed

    private void txtValEgrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValEgrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValEgrKeyPressed

    private void txtNumCuoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumCuoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumCuoKeyReleased

    private void txtNumCuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumCuoActionPerformed
        // TODO add your handling code here:
        txtNumCuo.transferFocus();

    }//GEN-LAST:event_txtNumCuoActionPerformed

    private void txtNumCuoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumCuoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumCuoKeyTyped

    private void txtValEgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValEgrActionPerformed
        // TODO add your handling code here:
        txtValEgr.transferFocus();
    }//GEN-LAST:event_txtValEgrActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDatFocusGained

    private void txtCodDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDocFocusGained
        // TODO add your handling code here:
        txtCodDoc.selectAll();
    }//GEN-LAST:event_txtCodDocFocusGained

    private void txtCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDocActionPerformed
        // TODO add your handling code here:
        txtCodDoc.transferFocus();
    }//GEN-LAST:event_txtCodDocActionPerformed

    private void txtCodDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDocFocusLost
        // TODO add your handling code here:
        calculaCuotasDetalle(txtNumCuo.getText());
    }//GEN-LAST:event_txtCodDocFocusLost

    private void txtDifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDifActionPerformed
        // TODO add your handling code here:
        txtDif.transferFocus();
    }//GEN-LAST:event_txtDifActionPerformed

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst) {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JButton butRub;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecIni;
    private javax.swing.JLabel lblNumCuo;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblRub;
    private javax.swing.JLabel lblTipCuo;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTipEgr;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValEgr;
    public javax.swing.JRadioButton optDef;
    public javax.swing.JRadioButton optInde;
    public javax.swing.JRadioButton optValCuoDes;
    public javax.swing.JRadioButton optValCuoIgu;
    public javax.swing.JRadioButton optValTodCuo;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetTraDep;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane spnObs4;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodRub;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtDif;
    private javax.swing.JTextField txtNomRub;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextField txtNumCuo;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextArea txtObs2;
    private javax.swing.JTextField txtValEgr;
    // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje) {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        obj.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void verificaSiEsEmpleadoInactivo(String strEstadoEmpleado) {
        System.out.println(strEstadoEmpleado);
        if (strEstadoEmpleado.equals("I")) {
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);
        }else{
            objTooBar.setVisibleAnular(true);
            objTooBar.setVisibleEliminar(true);
            objTooBar.setVisibleModificar(true);
        }
    }

    public class mitoolbar extends ZafToolBar {

        public mitoolbar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objZafParSis);
        }

        public boolean anular() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }
                    if (strAux.equals("Anulado")) {
                        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                        blnRes = true;
                    }
                    if (!blnRes) {
                        if (anularReg(conn)) {
                            conn.commit();
                            new RRHHDao(objUti, objZafParSis).callServicio9();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Anulado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        blnRes = false;
                    }

                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean anularReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";

            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "UPDATE tbm_cabingegrprgtra SET st_reg='I', fe_ultMod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", co_usrMod=" + objZafParSis.getCodigoUsuario()
                            + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_egr = " + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;

                    blnRes = true;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public void clickAceptar() {
            setEstadoBotonMakeFac();
            
        }

        public void clickAnterior() {
            try {
                if (!rstCab.isFirst()) {
                    rstCab.previous();
                    cargarReg();
                    
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() {
            try {
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInicio() {
            try {
                if (!rstCab.isFirst()) {
                    rstCab.first();
                    cargarReg();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try {
                if (!rstCab.isLast()) {
                    rstCab.last();
                    cargarReg();
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular() {
       
            if (!permiteMod()) {

                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Registro no puede ser anulado por un usuario que no lo ingreso.<BR></HTML>");
                setVisibleModificar(Boolean.FALSE);
                setVisibleAnular(Boolean.FALSE);
                setVisibleEliminar(Boolean.FALSE);
                //        return false;
            }
             configurarVenConTraActivos();
        }

        public void clickConsultar() {
            clnTextos();
            noEditable(false);
            //configurarVenConTra();
            if (objTooBar.getEstado()=='x') {
                configurarVenConTraActivos();
            }else if (objTooBar.getEstado()=='c') {
                configurarVenConTra();
            }else{
                   configurarVenConTraActivos();
            }
            
            bloquea(txtNumCuo, false);
        }

        public void clickEliminar() {   
            if (!permiteMod()) {
                
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Registro no puede ser eliminado por un usuario que no lo ingreso.<BR></HTML>");
                setVisibleModificar(Boolean.FALSE);
                setVisibleAnular(Boolean.FALSE);
                setVisibleEliminar(Boolean.FALSE);
                //        return false;
            }
            configurarVenConTraActivos();
        }

        public void clickInsertar() {
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSql = "";
            configurarVenConTraActivos();
            try {
                clnTextos();
                //noEditable(false);

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtCodDoc.setBackground(colBack);
                txtDif.setEditable(false);
                txtDif.setBackground(colBack);

                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                java.util.Date dateObj = datFecAux;
                java.util.Calendar calObj = java.util.Calendar.getInstance();
                calObj.setTime(dateObj);
                txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));
                
                txtFecIni.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));

                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {
                    stmLoc = con.createStatement();
                    strSql = "SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_doc  FROM tbm_cabingegrprgtra WHERE "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa();
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) {
                        txtCodDoc.setText("" + (rstLoc.getInt("co_doc")));//THERE
                    }
                    rstLoc.close();
                    rstLoc = null;
                }

                if (rstCab != null) {
                    rstCab.close();
                    rstCab = null;
                }

                txtFecDoc.setEnabled(false);//tony Nuevo cambió para evitar que se guarde en fechas diferentes.
                txtFecIni.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void setEstadoBotonMakeFac() {
            switch (getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }

        public boolean eliminar() {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();
                    if (strAux.equals("Eliminado")) {
                        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                        blnRes = true;
                    }

                    if (!blnRes) {
                        if (eliminarReg(conn)) {
                            conn.commit();
                            new RRHHDao(objUti, objZafParSis).callServicio9();
                            blnRes = true;
                            objTooBar.setEstadoRegistro("Eliminado");
                            blnHayCam = false;
                        } else {
                            conn.rollback();
                        }
                    } else {
                        blnRes = false;
                    }

                    conn.close();
                    conn = null;
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private boolean eliminarReg(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc = null;
            String strSql = "";
            try {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    strSql = "DELETE FROM tbm_detingegrprgtra  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_egr = " + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                    strSql = "DELETE FROM tbm_cabingegrprgtra  WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_egr = " + txtCodDoc.getText();
                    stmLoc.executeUpdate(strSql);

                }

                stmLoc.close();
                stmLoc = null;
                blnRes = true;
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        /**
         * Esta función determina si los campos son válidos.
         *
         * @return true: Si los campos son válidos.
         * <BR>false: En el caso contrario.
         */
        private boolean validaCampos() {// TODO add your handling code here:

            //Validar el "Tipo de documento".
            if (txtCodTra.getText().equals("")) {
                //tabFrm.setSelectedIndex(0);
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Empleado</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtCodTra.requestFocus();
                return false;
            }
            //Validar el "Rubro".
            if (txtCodRub.getText().equals("")) {
                //tabFrm.setSelectedIndex(0);
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Rubro</FONT> es obligatorio.<BR>Escriba o seleccione un rubro y vuelva a intentarlo.</HTML>");
                txtCodTra.requestFocus();
                return false;
            }
            //Validar la "Fecha del Documento".
            if (!txtFecDoc.isFecha()) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de documento y vuelva a intentarlo.</HTML>");
                txtFecDoc.requestFocus();
                return false;
            }
            //Validar la "Fecha de inicio".
            if (!txtFecIni.isFecha()) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de inicio</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de inicio y vuelva a intentarlo.</HTML>");
                txtFecDoc.requestFocus();
                return false;
            }
            //Validar el "Valor del egreso".
            if (txtValEgr.getText().equals("")) {
                //tabFrm.setSelectedIndex(0);
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Valor del egreso</FONT> es obligatorio.<BR>Escriba o seleccione el valor del egreso y vuelva a intentarlo.</HTML>");
                txtCodTra.requestFocus();
                return false;
            } else {
                try {
                    double dblNe_ValEgr = Double.parseDouble(txtValEgr.getText());
                } catch (NumberFormatException e) {

                    tabGen.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Valor del egreso</FONT> contiene formato érroneo..<BR>Escriba o seleccione el valor del egreso y vuelva a intentarlo.</HTML>");
                    txtValEgr.requestFocus();
                    return false;
                }
            }
            if (optDef.isSelected()) {
                if (!optValTodCuo.isSelected() && !optValCuoIgu.isSelected() && !optValCuoDes.isSelected()) {
                    tabGen.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de cuota</FONT> es obligatorio.<BR>Escriba o seleccione el tipo de cuota y vuelva a intentarlo.</HTML>");
                    txtCodTra.requestFocus();
                    return false;
                }
            }

            try {

                if (optDef.isSelected()) {
                    int intNe_NumCuo = Integer.parseInt(txtNumCuo.getText());
                    if (intNe_NumCuo < 0) {
                        tabGen.setSelectedIndex(0);
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de cuotas</FONT> debe ser mayor a cero..<BR>Escriba o seleccione el número de cuota y vuelva a intentarlo.</HTML>");
                        txtNumCuo.requestFocus();
                        return false;
                    }
                }

            } catch (NumberFormatException e) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de cuotas</FONT> contiene formato érroneo..<BR>Escriba o seleccione el valor del egreso y vuelva a intentarlo.</HTML>");
                txtNumCuo.requestFocus();
                return false;
            }

            double dblTotCuo = 0;
            if (optDef.isSelected()) {

                double dblDif = objUti.parseDouble(txtDif.getText());
                if (dblDif != 0) {
                    tabGen.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>Hay una diferencia de <FONT COLOR=\"blue\">" + txtDif.getText() + "</FONT></HTML>Escriba o seleccione una diferencia y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            return true;
        }

        public boolean insertar() {
            boolean blnRes = false;

            try {
                if (validaCampos()) {
                    if (insertarReg()) {
                        blnRes = true;
                    }
                }

            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public boolean insertarReg() {
            boolean blnRes = true;
            java.sql.Connection con = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            int intCodDoc = 0;

            try {
                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {
                    con.setAutoCommit(false);
                    stmLoc = con.createStatement();

                    String strSQL = "SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_doc  FROM tbm_cabingegrprgtra WHERE "
                            + " co_emp=" + objZafParSis.getCodigoEmpresa();
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intCodDoc = rstLoc.getInt("co_doc");
                    }
                    rstLoc.close();
                    rstLoc = null;

                    if (insertarCabEgr(con, intCodDoc)) {
                        if (optDef.isSelected()) {
                            if (insertarDetEgr(con, intCodDoc)) {
                                con.commit();
                                new RRHHDao(objUti, objZafParSis).callServicio9();
                            } else {
                                con.rollback();
                                blnRes = false;
                            }
                        } else if (optInde.isSelected()) {
                            con.commit();
                            new RRHHDao(objUti, objZafParSis).callServicio9();
                        }
                    } else {
                        con.rollback();
                        blnRes = false;
                    }

                    con.close();
                    con = null;
                }
            } catch (java.sql.SQLException Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    con.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        public boolean insertarDetEgr(java.sql.Connection con, int intCodDoc) {
            boolean blnRes = true;
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            try {
                stmLoc = con.createStatement();

                for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++) {

                    int FechaCuo[] = new int[3];
                    String strFec[] = tblDat.getValueAt(intFil, INT_TBL_FECHA).toString().split("/");
                    FechaCuo[2] = Integer.parseInt(strFec[2]);
                    FechaCuo[1] = Integer.parseInt(strFec[1]);
                    FechaCuo[0] = Integer.parseInt(strFec[0]);
                    String strFechaCuo = "#" + FechaCuo[2] + "/" + FechaCuo[1] + "/" + FechaCuo[0] + "#";
                    String strStReg = "";
                    if (tblDat.getValueAt(intFil, INT_TBL_CHKANL).equals(true)) {
                        strStReg = "I";
                    } else {
                        strStReg = "A";
                    }

                    strSql = "INSERT INTO tbm_detingegrprgtra(co_emp, co_egr, co_reg, fe_cuo, nd_valcuo, st_reg) "
                            + "VALUES ( "
                            + objZafParSis.getCodigoEmpresa() + " , "
                            + intCodDoc + " , "
                            + (intFil + 1) + " , "
                            + "'" + strFechaCuo + "' , "
                            + objUti.parseDouble(tblDat.getValueAt(intFil, INT_TBL_CUOTA)) + " , "
                            + objUti.codificar(strStReg)
                            + " )";

                    //System.out.println("insert detalle indefinido: " + strSql);
                    stmLoc.executeUpdate(strSql);
                }

                //actualizar la fecha de ultima cuota de la cabecera
                if (optDef.isSelected()) {
                    int FechaCuo[] = new int[3];
                    String strFec[] = tblDat.getValueAt(tblDat.getRowCount() - 1, INT_TBL_FECHA).toString().split("/");
                    FechaCuo[2] = Integer.parseInt(strFec[2]);
                    FechaCuo[1] = Integer.parseInt(strFec[1]);
                    FechaCuo[0] = Integer.parseInt(strFec[0]);
                    String strFechaCuo = "#" + FechaCuo[2] + "/" + FechaCuo[1] + "/" + FechaCuo[0] + "#";

                    strSql = "update tbm_cabingegrprgtra set fe_ultcuo = '" + strFechaCuo + "' where co_emp = " + objZafParSis.getCodigoEmpresa()
                            + " and co_egr = " + intCodDoc;
                    stmLoc.executeUpdate(strSql);
                }

            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        public boolean insertarCabEgr(java.sql.Connection con, int intCodDoc) {
            boolean blnRes = false;
            String strSql = "";
            java.sql.Statement stmLoc = null;
            java.sql.Statement stmLocAut = null;
            java.sql.ResultSet rstLocAut = null;
            try {

                stmLoc = con.createStatement();

                if (!objTblMod.isAllRowsComplete()) {
                    mostrarMsgInf("<HTML>El detalle del documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.changeSelection(0, INT_TBL_FECHA, true, true);
                    tblDat.requestFocus();
                    return false;
                }

                int FechaDoc[] = txtFecDoc.getFecha(txtFecDoc.getText());
                String strFechaDoc = "#" + FechaDoc[2] + "/" + FechaDoc[1] + "/" + FechaDoc[0] + "#";

                txtFecIni.setDia(01);
                int FechaIni[] = txtFecIni.getFecha(txtFecIni.getText());
                String strFechaIni = "#" + FechaIni[2] + "/" + FechaIni[1] + "/" + FechaIni[0] + "#";

                String strTx_tipEgrPrg = "";
                if (optDef.isSelected()) {
                    strTx_tipEgrPrg = "D";
                }
                if (optInde.isSelected()) {
                    strTx_tipEgrPrg = "I";
                }

                String strTx_tipCuo = "";

                if (optDef.isSelected()) {
                    if (optValTodCuo.isSelected()) {
                        strTx_tipCuo = "M";
                    }
                    if (optValCuoIgu.isSelected()) {
                        strTx_tipCuo = "I";
                    }
                    if (optValCuoDes.isSelected()) {
                        strTx_tipCuo = "D";
                    }
                }

                String strSt_Reg = "A"; //registo activo

                String strNe_NumCuo = null;
                if (!txtNumCuo.getText().equals("") && !txtNumCuo.getText().equals(null)) {
                    strNe_NumCuo = txtNumCuo.getText();
                }

                strSql = "INSERT INTO tbm_cabingegrprgtra(co_emp, co_egr, co_tra, co_rub, fe_doc, tx_tipegrprg, fe_priCuo, "
                        + "fe_ultCuo, tx_obscan, nd_valegrprg, ne_numcuo, tx_tipcuo, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, fe_utiPag) "
                        + "VALUES ( "
                        + objZafParSis.getCodigoEmpresa() + " , "
                        + intCodDoc + " , "
                        + txtCodTra.getText() + " , "
                        + txtCodRub.getText() + " , "
                        + "'" + strFechaDoc + "' , "
                        + objUti.codificar(strTx_tipEgrPrg) + " , "
                        + "'" + strFechaIni + "' , "
                        + " null , "
                        + " null , "
                        + objUti.parseDouble(txtValEgr.getText()) + " , "
                        + strNe_NumCuo + " , "
                        + objUti.codificar(strTx_tipCuo) + " , "
                        + objUti.codificar(txtObs1.getText()) + " , "
                        + objUti.codificar(txtObs2.getText()) + ", "
                        + objUti.codificar(strSt_Reg) + " , "
                        + " current_timestamp , "
                        + " null , "
                        + objZafParSis.getCodigoUsuario() + " , "
                        + " null, current_timestamp )";

                //System.out.println("insert tipo de egreso indefinido: " + strSql);
                stmLoc.executeUpdate(strSql);

                blnRes = true;

            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLocAut.close();
                } catch (Throwable ignore) {
                }
                try {
                    rstLocAut.close();
                } catch (Throwable ignore) {
                }
            }
            return blnRes;
        }

        public boolean modificar() {
            boolean blnRes = false;
            java.sql.Connection conn = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSQL = "";
            try {

                strAux = objTooBar.getEstadoRegistro();

                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
                    return false;
                }
                if (strAux.equals("Anulado")) {
                    MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
                    return false;
                }

                if (validaCampos()) {
                    con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (con != null) {
                        con.setAutoCommit(false);
                        stmLoc = con.createStatement();

                        if (modificarCab(con)) {

                            if (optDef.isSelected()) {
                                if (modificarDet(con, intCoReg)) {
                                    con.commit();
                                    new RRHHDao(objUti, objZafParSis).callServicio9();
                                    blnRes = true;
                                } else {
                                    con.rollback();
                                }
                            } else if (optInde.isSelected()) {
                                con.commit();
                                blnRes = true;
                                new RRHHDao(objUti, objZafParSis).callServicio9();
                            }

                        } else {
                            con.rollback();
                        }

                    }
                }

            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    con.close();
                } catch (Throwable ignore) {
                }
            }

            return blnRes;

        }

        public boolean permiteMod() {
            boolean blnRes = false;

            java.sql.Connection conn = null;
            java.sql.Statement stmLoc = null;
            java.sql.Statement stmLocAux = null;
            java.sql.ResultSet rstLoc = null;
            java.sql.ResultSet rstLocAux = null;
            String strSQL = "";

            try {

                con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {

                    stmLoc = con.createStatement();
                    stmLocAux = con.createStatement();

                    strSQL = "";
                    strSQL += "SELECT DISTINCT co_usring FROM tbm_cabingegrprgtra \n"
                            + "WHERE co_emp = " + objZafParSis.getCodigoEmpresa() + "\n"
                            + "AND co_egr = " + txtCodDoc.getText();
                    rstLoc = stmLoc.executeQuery(strSQL);

                    if (rstLoc.next()) {

                        if (objZafParSis.getCodigoUsuario() == 1) {
                            return true;
                        } else {
                            if (rstLoc.getInt("co_usring") == objZafParSis.getCodigoUsuario()) {
                                blnRes = true;
                            }
                        }
                    }

//                    Calendar cal = Calendar.getInstance();
//                    int intNeAni = cal.get(Calendar.YEAR);
//                    int intNeMes = cal.get(Calendar.MONTH)-1;
//                    int intNePer=-1;
//                    
//                    strSQL="";
//                    strSQL="select DISTINCT st_rolpaggen from tbm_feccorrolpag \n " + 
//                           "WHERE co_emp = "+objZafParSis.getCodigoEmpresa() + " \n " + 
//                           "AND ne_ani = " + cal.get(Calendar.YEAR) + " \n " + 
//                           "AND ne_mes = " + (cal.get(Calendar.MONTH)-1) + " \n " +
//                           "AND "+objUti.codificar(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)-1)+"-"+cal.get(Calendar.DAY_OF_MONTH))+" BETWEEN fe_des AND fe_has";
//                    rstLocAux = stmLocAux.executeQuery(strSQL);
//                    
//                    if(rstLocAux.next()){
//                        String strStRolPagGen=rstLocAux.getString("st_rolpaggen");
//                        if(strStRolPagGen!=null){
//                            if(strStRolPagGen.compareTo("S")==0){
//                                blnRes=true;
//                            }
//                        }
//                    }
//                }
                }
                
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } finally {
                try {
                    rstLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    con.close();
                } catch (Throwable ignore) {
                }
            }

            return blnRes;
        }

        public boolean modificarCab(java.sql.Connection conn) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSQL = "";
            String strFechaDoc = "";
            String strFecSis = "";

            try {
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    int FechaDoc[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    strFechaDoc = "#" + FechaDoc[2] + "/" + FechaDoc[1] + "/" + FechaDoc[0] + "#";

                    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());

                    txtFecIni.setDia(01);
                    int FechaIni[] = txtFecIni.getFecha(txtFecIni.getText());
                    String strFechaIni = "#" + FechaIni[2] + "/" + FechaIni[1] + "/" + FechaIni[0] + "#";

                    String strTx_tipEgrPrg = "";
                    if (optDef.isSelected()) {
                        strTx_tipEgrPrg = "D";
                    }
                    if (optInde.isSelected()) {
                        strTx_tipEgrPrg = "I";
                    }

                    String strTx_tipCuo = "";

                    if (optDef.isSelected()) {
                        if (optValTodCuo.isSelected()) {
                            strTx_tipCuo = "M";
                        }
                        if (optValCuoIgu.isSelected()) {
                            strTx_tipCuo = "I";
                        }
                        if (optValCuoDes.isSelected()) {
                            strTx_tipCuo = "D";
                        }
                    }

                    //int intNe_NumCuo = Integer.parseInt(txtNumCuo.getText());
                    String strNe_NumCuo = null;
                    if (!txtNumCuo.getText().equals("") && !txtNumCuo.getText().equals(null)) {
                        strNe_NumCuo = txtNumCuo.getText();
                    }

                    strSQL = "UPDATE tbm_cabingegrprgtra "
                            + "SET  co_tra=" + txtCodTra.getText() + ",co_rub=" + txtCodRub.getText() + ", fe_doc='" + strFechaDoc + "', tx_tipegrprg=" + objUti.codificar(strTx_tipEgrPrg) + ", "
                            + "fe_pricuo='" + strFechaIni + "',  nd_valegrprg=" + objUti.parseDouble(txtValEgr.getText()) + ", ne_numcuo=" + strNe_NumCuo + ", "
                            + "tx_tipcuo=" + objUti.codificar(strTx_tipCuo) + ", tx_obs1=" + objUti.codificar(txtObs1.getText()) + ", tx_obs2=" + objUti.codificar(txtObs2.getText()) + ", "
                            + "fe_ultmod= current_timestamp, co_usrmod= " + objZafParSis.getCodigoUsuario() + " "
                            + "WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " and co_egr=" + txtCodDoc.getText();

                    stmLoc.executeUpdate(strSQL);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public boolean modificarDet(java.sql.Connection conn, int intCoReg) {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql = "";

            try {
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    strSql = "delete from tbm_detingegrprgtra where co_emp=" + objZafParSis.getCodigoEmpresa()
                            + " and co_egr = " + txtCodDoc.getText()
                            + " and co_reg >= " + intCoReg;
                    stmLoc.executeUpdate(strSql);

                    for (int intFil = intCoReg; intFil < tblDat.getRowCount(); intFil++) {

                        int FechaCuo[] = new int[3];
                        String strFec[] = tblDat.getValueAt(intFil, INT_TBL_FECHA).toString().split("/");
                        FechaCuo[2] = Integer.parseInt(strFec[2]);
                        FechaCuo[1] = Integer.parseInt(strFec[1]);
                        FechaCuo[0] = Integer.parseInt(strFec[0]);
                        String strFechaCuo = "#" + FechaCuo[2] + "/" + FechaCuo[1] + "/" + FechaCuo[0] + "#";

                        String strStReg = "";

                        if ((Boolean) tblDat.getValueAt(intFil, INT_TBL_CHKANL)) {
                            strStReg = "I";
                        } else {
                            strStReg = "A";
                        }

                        strSql = "INSERT INTO tbm_detingegrprgtra(co_emp, co_egr, co_reg, fe_cuo, nd_valcuo, st_reg) "
                                + "VALUES ( "
                                + objZafParSis.getCodigoEmpresa() + " , "
                                + txtCodDoc.getText() + " , "
                                + (intFil + 1) + " , "
                                + "'" + strFechaCuo + "' , "
                                + objUti.parseDouble(tblDat.getValueAt(intFil, INT_TBL_CUOTA)) + " , " + objUti.codificar(strStReg) + " )";

                        //System.out.println("insert detalle indefinido: " + strSql);
                        stmLoc.executeUpdate(strSql);
                    }

                    //actualizar la fecha de ultima cuota de la cabecera
                    if (optDef.isSelected()) {
                        int FechaCuo[] = new int[3];
                        String strFec[] = tblDat.getValueAt(tblDat.getRowCount() - 1, INT_TBL_FECHA).toString().split("/");
                        FechaCuo[2] = Integer.parseInt(strFec[2]);
                        FechaCuo[1] = Integer.parseInt(strFec[1]);
                        FechaCuo[0] = Integer.parseInt(strFec[0]);
                        String strFechaCuo = "#" + FechaCuo[2] + "/" + FechaCuo[1] + "/" + FechaCuo[0] + "#";

                        strSql = "update tbm_cabingegrprgtra set fe_ultcuo = '" + strFechaCuo + "' where co_emp = " + objZafParSis.getCodigoEmpresa()
                                + " and co_egr = " + txtCodDoc.getText();
                        stmLoc.executeUpdate(strSql);
                    }

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;

                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                blnRes = false;
                Evt.printStackTrace();
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        private void noEditable(boolean blnEst) {
            txtObs1.setEditable(false);
            txtObs2.setEditable(false);

        }

        public void clnTextos() {

            strCodTra = "";
            strCodRub = "";

            txtCodTra.setText("");
            txtNomTra.setText("");
            txtCodRub.setText("");
            txtNomRub.setText("");

            txtFecDoc.setText("");
            txtFecIni.setText("");

            txtCodDoc.setText("");
            txtValEgr.setText("");
            txtNumCuo.setText("");
            txtDif.setText("0.0");

            txtObs1.setText("");
            txtObs2.setText("");

            optDef.setSelected(false);
            optInde.setSelected(false);
            optValTodCuo.setSelected(false);
            optValCuoIgu.setSelected(false);
            optValCuoDes.setSelected(false);
            objTblMod.removeAllRows();
        }

        public boolean cancelar() {
            boolean blnRes = true;

            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') {
                        if (!isRegPro()) {
                            return false;
                        }
                    }
                }
                if (rstCab != null) {
                    rstCab.close();
                    if (STM_GLO != null) {
                        STM_GLO.close();
                        STM_GLO = null;
                    }
                    rstCab = null;

                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            blnHayCam = false;

            return blnRes;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {

            return true;
        }

        public boolean afterConsultar() {

            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');

            return true;
        }

        public boolean afterModificar() {

            this.setEstado('w');
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        /**
         * Esta función muestra un mensaje "showConfirmDialog". Presenta las
         * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
         * hacer el sistema seleccionando una de las opciones que se presentan.
         */
        private int mostrarMsgCon(String strMsg) {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            String strTit;
            strTit = "Mensaje del sistema Zafiro";
            return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
        }

        /**
         * Esta función se encarga de agregar el listener "DocumentListener" a
         * los objTooBars de tipo texto para poder determinar si su contenido a
         * cambiado o no.
         */
        private boolean isRegPro() {
            boolean blnRes = true;
            String strAux = "¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux += "Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux)) {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado()) {
                        case 'n': //Insertar
                            blnRes = objTooBar.insertar();
                            break;
                        case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    objTblMod.setDataModelChanged(false);
                    blnHayCam = false;
                    blnRes = true;
                    break;
                case 2: //CANCEL_OPTION
                    blnRes = false;
                    break;
            }
            return blnRes;
        }

        public boolean consultar() {
            /*
             * Esto Hace en caso de que el modo de operacion sea Consulta
             */
            //return _consultar(FilSql());
            consultarReg();
            return true;
        }

        /**
         * Esta función permite consultar los registros de acuerdo al criterio
         * seleccionado.
         *
         * @return true: Si se pudo consultar los registros.
         * <BR>false: En el caso contrario.
         */
        private boolean consultarReg() {
            boolean blnRes = true;
            String strSQL = "";
            try {
                conCab = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conCab != null) {
                    stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    //Validar que sólo se muestre los documentos asignados al programa.
                    if (txtCodTra.getText().equals("")) {

                        if (objZafParSis.getCodigoMenu() == 3356) {
                            strSQL += "select *,b.st_reg as st_regTra FROM tbm_cabingegrprgtra AS a";
                            strSQL += " inner join tbm_tra c on (c.co_tra=a.co_tra)";
                            strSQL += " inner join tbm_rubRolPagEmp d on (d.co_rub=a.co_rub and d.co_emp=a.co_emp)";
                            strSQL += " inner join tbm_rubRolPag e on (d.co_rub=e.co_rub and e.tx_tiprub='I')";
                            strSQL += " inner join tbm_traemp b on(c.co_tra=b.co_tra and a.co_emp=b.co_emp)";//Tony
                            strSQL += " where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSQL += " AND a.st_reg<>'E'";
                        } else {
                            strSQL += "select *,b.st_reg as st_regTra FROM tbm_cabingegrprgtra AS a";
                            strSQL += " inner join tbm_tra c on (c.co_tra=a.co_tra)";
                            strSQL += " inner join tbm_rubRolPagEmp d on (d.co_rub=a.co_rub and d.co_emp=a.co_emp)";
                            strSQL += " inner join tbm_rubRolPag e on (d.co_rub=e.co_rub and e.tx_tiprub='E')";
                            strSQL += " inner join tbm_traemp b on(c.co_tra=b.co_tra and a.co_emp=b.co_emp)";//Tony
                            strSQL += " where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSQL += " AND a.st_reg<>'E'";
                        }

                    } else {

                        if (objZafParSis.getCodigoMenu() == 3356) {
                            strSQL += "select *,b.st_reg as st_regTra FROM tbm_cabingegrprgtra AS a";
                            strSQL += " inner join tbm_tra c on (c.co_tra=a.co_tra)";
                            strSQL += " inner join tbm_rubRolPagEmp d on (d.co_rub=a.co_rub and d.co_emp=a.co_emp)";
                            strSQL += " inner join tbm_rubRolPag e on (d.co_rub=e.co_rub and e.tx_tiprub='I')";
                            strSQL += " inner join tbm_traemp b on(c.co_tra=b.co_tra and a.co_emp=b.co_emp)";//Tony
                            strSQL += " where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSQL += " AND a.st_reg<>'E'";
                            strSQL += " AND a.co_tra = " + txtCodTra.getText();
                        } else {
                            strSQL += "select *,b.st_reg as st_regTra FROM tbm_cabingegrprgtra AS a";
                            strSQL += " inner join tbm_tra c on (c.co_tra=a.co_tra)";
                            strSQL += " inner join tbm_rubRolPagEmp d on (d.co_rub=a.co_rub and d.co_emp=a.co_emp)";
                            strSQL += " inner join tbm_rubRolPag e on (d.co_rub=e.co_rub and e.tx_tiprub='E')";
                            strSQL += " inner join tbm_traemp b on(c.co_tra=b.co_tra and a.co_emp=b.co_emp)";//Tony
                            strSQL += " where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                            strSQL += " AND a.st_reg<>'E'";
                            strSQL += " AND a.co_tra = " + txtCodTra.getText();
                        }

                    }

//                strAux=txtCodTra.getText();
//                if (!strAux.equals(""))
//                    strSQL+=" AND a.co_tra=" + strAux;
                    if (txtFecDoc.isFecha()) {
                        strSQL += " AND a.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(), "dd/MM/yyyy", objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    }
                    if (txtFecIni.isFecha()) {
                        strSQL += " AND a.fe_priCuo='" + objUti.formatearFecha(txtFecIni.getText(), "dd/MM/yyyy", objZafParSis.getFormatoFechaBaseDatos()) + "'";
                    }
                    strAux = txtCodDoc.getText();
                    if (!strAux.equals("")) {
                        strSQL += " AND a.co_egr=" + strAux;
                    }
                    strAux = txtCodRub.getText();
                    if (!strAux.equals("")) {
                        strSQL += " AND a.co_rub=" + strAux;
                    }

                    strSQL += " ORDER BY a.co_emp, a.co_egr";
                    rstCab = stmCab.executeQuery(strSQL);
                    if (rstCab.next()) {
                        rstCab.last();
                        objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        rstCab.first();
                        cargarReg();
//                    strSQLCon=strSQL;
                    } else {
                        mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                        clnTextos();
                        objTooBar.setEstado('l');
                        objTooBar.setMenSis("Listo");
                    }
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        /**
         * Esta función permite cargar el registro seleccionado.
         *
         * @return true: Si se pudo cargar el registro.
         * <BR>false: En el caso contrario.
         */
        private boolean cargarReg() {
            boolean blnRes = true;
            try {
                if (cargarCabReg()) {
                    if (cargarDetReg()) {
                        blnRes = true;
                    }
                }
//            objAsiDia.setDiarioModificado(false);
                blnHayCam = false;
            } catch (Exception e) {
                blnRes = false;
            }
            return blnRes;
        }

        /**
         * Esta función permite cargar la cabecera del registro seleccionado.
         *
         * @return true: Si se pudo cargar la cabecera del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean cargarCabReg() {
            int intPosRel;
            boolean blnRes = true;
            String strSQL = "";
            java.util.Date datFecDoc;
            java.util.Date datPriCuo;

            try {
                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {
                    stm = con.createStatement();
                    strSQL = "";
                    strSQL += "select a.*,(c.tx_ape || ' ' || c.tx_nom) as tx_nomtra,e.co_rub,e.tx_nom as tx_nomrub,b.st_Reg as st_regTra FROM tbm_cabingegrprgtra AS a";
                    strSQL += " inner join tbm_tra c on (c.co_tra=a.co_tra)";
                    strSQL += " inner join tbm_rubRolPagEmp d on (d.co_rub=a.co_rub and d.co_emp=a.co_emp)";
                    strSQL += " inner join tbm_rubRolPag e on (d.co_rub=e.co_rub)";
                    strSQL += " inner join tbm_traemp b on (b.co_tra=c.co_tra and a.co_emp=b.co_emp)";
                    strSQL += " where a.co_emp = " + rstCab.getString("co_emp");
                    strSQL += " AND a.co_egr=" + rstCab.getString("co_egr");
                    strSQL += " AND a.co_tra=" + rstCab.getString("co_tra");

                    rst = stm.executeQuery(strSQL);
                    if (rst.next()) {
                        strAux = rst.getString("co_tra");
                        txtCodTra.setText((strAux == null) ? "" : strAux);

                        strAux = rst.getString("tx_nomtra");
                        txtNomTra.setText((strAux == null) ? "" : strAux);

                        strAux = rst.getString("co_rub");
                        txtCodRub.setText((strAux == null) ? "" : strAux);

                        strAux = rst.getString("tx_nomrub");
                        txtNomRub.setText((strAux == null) ? "" : strAux);

                        strAux = rst.getString("co_egr");
                        txtCodDoc.setText((strAux == null) ? "" : strAux);

                        datFecDoc = rst.getDate("fe_doc");
                        txtFecDoc.setText(objUti.formatearFecha(datFecDoc, "dd/MM/yyyy"));

                        datPriCuo = rst.getDate("fe_pricuo");
                        txtFecIni.setText(objUti.formatearFecha(datPriCuo, "dd/MM/yyyy"));

                        txtValEgr.setText("" + objUti.redondear(rst.getDouble("nd_valegrprg"), objZafParSis.getDecimalesMostrar()));

                        strAux = rst.getString("ne_numcuo");
                        txtNumCuo.setText((strAux == null) ? "" : strAux);

                        strAux = rst.getString("tx_obs1");
                        txtObs1.setText((strAux == null) ? "" : strAux);
                        strAux = rst.getString("tx_obs2");
                        txtObs2.setText((strAux == null) ? "" : strAux);

                        strAux = rst.getString("tx_tipegrprg");
                        if (strAux.equals("I")) {
                            optInde.setSelected(true);
                            optValTodCuo.setSelected(true);
                            optValCuoIgu.setSelected(false);
                            optValCuoDes.setSelected(false);
                            optDef.setSelected(false);
                        } else if (strAux.equals("D")) {
                            optDef.setSelected(true);
                            optInde.setSelected(false);
                            strAux = rst.getString("tx_tipcuo");
                            if (strAux.equals("M")) {
                                optValTodCuo.setSelected(true);
                                optValCuoIgu.setSelected(false);
                                optValCuoDes.setSelected(false);
                            } else if (strAux.equals("I")) {
                                optValTodCuo.setSelected(false);
                                optValCuoIgu.setSelected(true);
                                optValCuoDes.setSelected(false);
                            } else if (strAux.equals("D")) {
                                optValTodCuo.setSelected(false);
                                optValCuoIgu.setSelected(false);
                                optValCuoDes.setSelected(true);
                            }

                        }

                        //Mostrar el estado del registro.
                        strAux = rst.getString("st_reg");
                        if (strAux.equals("A")) {
                            strAux = "Activo";
                        } else if (strAux.equals("I")) {
                            strAux = "Anulado";
                        } else {
                            strAux = "Otro";
                        }
                        objTooBar.setEstadoRegistro(strAux);
                        strAux = rst.getString("st_regTra");
                        verificaSiEsEmpleadoInactivo(strAux);
                    } else {
                        objTooBar.setEstadoRegistro("Eliminado");
                        clnTextos();
                        blnRes = false;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Mostrar la posición relativa del registro.
                intPosRel = rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        /**
         * Esta función permite cargar el detalle del registro seleccionado.
         *
         * @return true: Si se pudo cargar el detalle del registro.
         * <BR>false: En el caso contrario.
         */
        private boolean cargarDetReg() {

            String strSQL;
            boolean blnRes = true;
            java.util.Date datFecCuo;
            try {
//            objTooBar.setMenSis("Obteniendo datos...");
                con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (con != null) {
                    stm = con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += "select * from tbm_detingegrprgtra";
                    strSQL += " where co_emp = " + rstCab.getString("co_emp");
                    strSQL += " and co_egr = " + rstCab.getString("co_egr");
                    strSQL += " ORDER BY co_emp, co_egr, co_reg";
                    rst = stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
                    while (rst.next()) {
                        Vector vecReg = new Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        datFecCuo = rst.getDate("fe_cuo");
                        vecReg.add(INT_TBL_FECHA, objUti.formatearFecha(datFecCuo, "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_CUOTA, objUti.parseDouble(rst.getObject("nd_valcuo")));
                        boolean bln = false;
                        if (rst.getString("st_reg").compareTo("I") == 0) {
                            bln = true;
                        }
                        vecReg.add(INT_TBL_CHKANL, bln);
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
                    if (isPreCancelado()) {
                        objTooBar.setVisibleModificar(false);
                    }
                    vecDat.clear();
                }
            } catch (java.sql.SQLException e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }

        private boolean isPreCancelado() {
            boolean blnRes = false;
            try {

                if ((Boolean) objTblMod.getValueAt(objTblMod.getRowCount() - 1, INT_TBL_CHKANL)) {
                    return true;
                }
            } catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, e);
            }

            return blnRes;
        }

        private void mostrarEstado(String strStReg) {
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if (strStReg.equals("I")) {
                setEstadoRegistro("Anulado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            } else {
                if (strStReg.equals("E")) {
                    setEstadoRegistro("Eliminado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                } else {
                    setEstadoRegistro("");
                }
            }
        }

        public void clickModificar() {
            if (permiteMod()) {
                setEditable(true);

                java.awt.Color colBack;
                colBack = txtCodDoc.getBackground();
                txtCodDoc.setEditable(false);
                txtDif.setBackground(colBack);
                txtDif.setEditable(false);
                this.setEnabledConsultar(false);

                objTblMod.setDataModelChanged(false);
                blnHayCam = false;
            } else {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Registro no puede ser modificado por un usuario que no lo ingreso.<BR></HTML>");
                setVisibleModificar(Boolean.FALSE);
                setVisibleAnular(Boolean.FALSE);
                setVisibleEliminar(Boolean.FALSE);
//        return false;
            }
            configurarVenConTraActivos();
            txtFecDoc.setEnabled(false);
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickCancelar() {
            setVisibleModificar(Boolean.TRUE);
            setVisibleAnular(Boolean.TRUE);
            setVisibleEliminar(Boolean.TRUE);
        }

        public void cierraConnections() {

        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeImprimir() {

            return true;
        }

        public boolean beforeInsertar() {
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeVistaPreliminar() {

            return true;
        }
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_FECHA:
                    strMsg = "Fecha";
                    break;
                case INT_TBL_CUOTA:
                    strMsg = "Cuota";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable {
        System.gc();
        super.finalize();
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            arlAncCol.add("40");

            String strSQL = "";
            if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom,b.st_reg from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra)"+// where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
//                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) "
//                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
//                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
//                       "order by (a.tx_ape || ' ' || a.tx_nom)";
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom,b.st_reg from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) "
                        + " where co_emp = " + objZafParSis.getCodigoEmpresa() //+ " and b.st_reg like 'A' "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";

            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Rubros de egresos programados".
     */
    private boolean configurarVenConRub() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_rub");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            String strSQL = "";

            if (objZafParSis.getCodigoMenu() == 3356) {
                strSQL = "";
                strSQL = "select co_rub,tx_nom,tx_obs1 from tbm_rubrolpag "
                        + "where st_reg like 'A' and tx_tiprub like 'I' and st_egrprg like 'S'";
            } else {
                strSQL = "";
                strSQL = "select co_rub,tx_nom,tx_obs1 from tbm_rubrolpag "
                        + "where st_reg like 'A' and tx_tiprub like 'E' and st_egrprg like 'S'";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoRub = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Egresos Programados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTraActivos() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            arlAncCol.add("40");

            String strSQL = "";
            if (objZafParSis.getCodigoEmpresa() == objZafParSis.getCodigoEmpresaGrupo()) {
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom,b.st_reg from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
//                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) "
//                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
//                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
//                       "order by (a.tx_ape || ' ' || a.tx_nom)";
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom,b.st_reg from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) "
                        + " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and b.st_reg like 'A' "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";

            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}
