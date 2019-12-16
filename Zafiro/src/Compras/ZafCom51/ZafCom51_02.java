/**
 * ZafCom51_02.java
 *
 */
package Compras.ZafCom51;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Ventas.ZafVen21.ZafVen21_01;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author sistemas1
 */
public class ZafCom51_02 extends JInternalFrame
{
    private static final int INT_TBL_LINEA = 0;
    private static final int INT_TBL_CODEMP = 1;
    private static final int INT_TBL_CODLOC = 2;
    private static final int INT_TBL_CODTIPDOC = 3;
    private static final int INT_TBL_CODDOC = 4;
    private static final int INT_TBL_DESCOR = 5;
    private static final int INT_TBL_NUMDOC = 6;
    private static final int INT_TBL_FECDOC = 7;
    private static final int INT_TBL_CANGUI = 8;
    private static final int INT_TBL_BUTVIS = 9;
    private static final int INT_TBLDAT_LINEA = 0;
    private static final int INT_TBLDAT_CODEMP = 1;
    private static final int INT_TBLDAT_CODLOC = 2;
    private static final int INT_TBLDAT_CODTIPDOC = 3;
    private static final int INT_TBLDAT_TIPDOC = 4;
    private static final int INT_TBLDAT_CODDOC = 5;
    private static final int INT_TBLDAT_NUMORDDES = 6;
    private static final int INT_TBLDAT_FECDOC = 7;
    private static final int INT_TBLDAT_STCONPEN = 8;
    private static final int INT_TBLDAT_STCONPAR = 9;
    private static final int INT_TBLDAT_STCONTOT = 10;
    private static final int INT_TBLDAT_NDCAN = 11;
    private static final int INT_TBLDAT_NDCANCON = 12;
    private static final int INT_TBLDAT_NDCANNUNREC = 13;
    private static final int INT_TBLDAT_BODEGA = 14;
    private static final int INT_TBLDAT_BUTGUI = 15;
    
    private Connection con;
    private ZafParSis objZafParSis;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModCliRet;
    private ZafUtil objUti;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafMouMotAda1 objMouMotAda1;                //ToolTipText en TableHeader.
    public boolean blnAcepta = false;
    public boolean blnEst = false;
    private int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCoReg;
    private String strSql01 = "", strSql02 = "";
    private JInternalFrame JIntFra;


    /**
     * Creates new form ZafCom51_02
     */
    public ZafCom51_02(ZafParSis obj, String strSql1, String strSql2, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCoReg)
    {
        try 
        {
            this.objZafParSis = (ZafParSis) obj.clone();
            initComponents();
            objUti = new ZafUtil();
            objTblCelRenChk = new ZafTblCelRenChk();

            lblTit.setText("Listado de Documentos Asociados");
            this.intCodEmp = intCodEmp;
            this.intCodLoc = intCodLoc;
            this.intCodTipDoc = intCodTipDoc;
            this.intCodDoc = intCodDoc;
            this.intCoReg = intCoReg;

            this.strSql01 = strSql1;
            this.strSql02 = strSql2;
        }
        catch (CloneNotSupportedException e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public void Configura_ventana_consulta()
    {
        ConfigurarTblDat();
        configurarTblCliRet();
        if (objThrGUI == null)
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
    }

    private boolean ConfigurarTblDat() 
    {
        boolean blnRes = false;
        try 
        {
            //Configurar JTable: Establecer el modelo. 
            Vector vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "Linea");
            vecCab.add(INT_TBL_CODEMP, "Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC, "Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC, "Cod.Tip.Doc.");
            vecCab.add(INT_TBL_CODDOC, "Cod.Doc.");
            vecCab.add(INT_TBL_DESCOR, "Tip.Doc.");
            vecCab.add(INT_TBL_NUMDOC, "Num.Doc");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_CANGUI, "Cantidad");
            vecCab.add(INT_TBL_BUTVIS, "..");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LINEA);

            objTblMod.setColumnDataType(INT_TBL_CANGUI, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_BUTVIS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblDat.getColumnModel();

            objMouMotAda1 = new ZafMouMotAda1();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda1);

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DESCOR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_CANGUI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTVIS).setPreferredWidth(25);

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANGUI).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl = null;

            //Columnas Ocultas
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_BUTVIS, tblDat);

            tblDat.getTableHeader().setReorderingAllowed(false);

            tcmAux = null;

            setEditable(true);
            blnRes = true;
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private void configurarTblCliRet() 
    {
        try 
        {
            Vector vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBLDAT_LINEA, "Linea");
            vecCab.add(INT_TBLDAT_CODEMP, "Cod.Emp.");
            vecCab.add(INT_TBLDAT_CODLOC, "Cod.Loc.");
            vecCab.add(INT_TBLDAT_CODTIPDOC, "Cod.Tip.Doc.");
            vecCab.add(INT_TBLDAT_TIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBLDAT_CODDOC, "Cod.Doc.");
            vecCab.add(INT_TBLDAT_NUMORDDES, "Num.Ord.Des.");
            vecCab.add(INT_TBLDAT_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBLDAT_STCONPEN, "Pendiente");
            vecCab.add(INT_TBLDAT_STCONPAR, "Parcial");
            vecCab.add(INT_TBLDAT_STCONTOT, "Total");
            vecCab.add(INT_TBLDAT_NDCAN, "Cantidad");
            vecCab.add(INT_TBLDAT_NDCANCON, "Can.Tot.Con.");
            vecCab.add(INT_TBLDAT_NDCANNUNREC, "Can.Tot.Nun.Con.");
            vecCab.add(INT_TBLDAT_BODEGA, "Bodega");
            vecCab.add(INT_TBLDAT_BUTGUI, "");

            objTblModCliRet = new ZafTblMod();
            objTblModCliRet.setHeader(vecCab);

            tblDatCliRet.setModel(objTblModCliRet);
            //Configurar JTable: Establecer tipo de selección.
            tblDatCliRet.setRowSelectionAllowed(true);
            tblDatCliRet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDatCliRet, INT_TBL_LINEA);

            objTblModCliRet.setColumnDataType(INT_TBLDAT_NDCAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModCliRet.setColumnDataType(INT_TBLDAT_NDCANCON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModCliRet.setColumnDataType(INT_TBLDAT_NDCANNUNREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatCliRet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblDatCliRet.getColumnModel();

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatCliRet.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBLDAT_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBLDAT_TIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBLDAT_NUMORDDES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBLDAT_FECDOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBLDAT_STCONPEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLDAT_STCONPAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLDAT_STCONTOT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLDAT_NDCAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBLDAT_NDCANCON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBLDAT_NDCANNUNREC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBLDAT_BODEGA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBLDAT_BUTGUI).setPreferredWidth(20);

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBLDAT_NDCAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBLDAT_NDCANCON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBLDAT_NDCANNUNREC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBLDAT_BUTGUI);
            objTblModCliRet.addColumnasEditables(vecAux);
            vecAux = null;

             //Columnas Ocultas
            objTblMod.addSystemHiddenColumn(INT_TBLDAT_CODEMP, tblDatCliRet);
            objTblMod.addSystemHiddenColumn(INT_TBLDAT_CODLOC, tblDatCliRet);
            objTblMod.addSystemHiddenColumn(INT_TBLDAT_CODTIPDOC, tblDatCliRet);
            objTblMod.addSystemHiddenColumn(INT_TBLDAT_CODDOC, tblDatCliRet);

            //Agrupamiento
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDatCliRet.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrp = new ZafTblHeaColGrp(" DATOS DE LA ORDEN DE DESPACHO ");
            objTblHeaColGrp.setHeight(16);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_LINEA));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_CODEMP));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_CODLOC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_CODTIPDOC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_TIPDOC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_CODDOC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_NUMORDDES));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_FECDOC));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            objTblHeaColGrp = null;

            objTblHeaColGrp = new ZafTblHeaColGrp(" CONFIRMACION ");
            objTblHeaColGrp.setHeight(16);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_STCONPEN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_STCONPAR));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_STCONTOT));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_NDCAN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_NDCANCON));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_NDCANNUNREC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_BODEGA));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBLDAT_BUTGUI));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            objTblHeaColGrp = null;

            tcmAux.getColumn(INT_TBLDAT_STCONPEN).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBLDAT_STCONPAR).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBLDAT_STCONTOT).setCellRenderer(objTblCelRenChk);

            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBLDAT_BUTGUI).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() {
                int intFilSel, intColSel;

                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    intColSel = objTblCelRenBut.getColumnRender();
                    intFilSel = objTblCelRenBut.getRowRender();
                    switch (intColSel) {
                        case INT_TBLDAT_BUTGUI:
                            if (objTblModCliRet.getValueAt(intFilSel, INT_TBLDAT_STCONPEN).toString().equals("true")) {
                                objTblCelRenBut.setText("");
                            } else {
                                objTblCelRenBut.setText("...");
                            }
                            break;
                        default:
                            break;
                    }
                }
            });

            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBLDAT_BUTGUI).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;

                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel = tblDatCliRet.getSelectedRow();
                    intColSel = tblDatCliRet.getSelectedColumn();

                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBLDAT_BUTGUI:
                                if (objTblModCliRet.getValueAt(intFilSel, INT_TBLDAT_STCONPEN).toString().equals("true")) {
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                                } else {
                                    objTblCelEdiButGen.setCancelarEdicion(false);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    intFilSel = tblDatCliRet.getSelectedRow();
                    intColSel = tblDatCliRet.getSelectedColumn();

                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBLDAT_BUTGUI:
                                if (objTblModCliRet.getValueAt(intFilSel, INT_TBLDAT_STCONPEN).toString().equals("false")) //mostrarGuiasRemision(String.valueOf(intCodEmp), String.valueOf(intCodLoc), String.valueOf(intCodTipDoc), String.valueOf(intCodDoc) );
                                {
                                    mostrarGuiasRemision(objTblModCliRet.getValueAt(intFilSel, INT_TBLDAT_CODEMP).toString(),
                                                         objTblModCliRet.getValueAt(intFilSel, INT_TBLDAT_CODLOC).toString(),
                                                         objTblModCliRet.getValueAt(intFilSel, INT_TBLDAT_CODTIPDOC).toString(),
                                                         objTblModCliRet.getValueAt(intFilSel, INT_TBLDAT_CODDOC).toString());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            });

            tcmAux = null;

            tblDatCliRet.getTableHeader().setReorderingAllowed(false);

            objTblModCliRet.setModoOperacion(ZafTblMod.INT_TBL_EDI);

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private class ZafMouMotAda extends MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol = tblDatCliRet.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBLDAT_LINEA:
                    strMsg = "";
                    break;
                case INT_TBLDAT_CODEMP:
                    strMsg = "Codigo de la empresa";
                    break;
                case INT_TBLDAT_CODLOC:
                    strMsg = "Codigo de local";
                    break;
                case INT_TBLDAT_CODTIPDOC:
                    strMsg = "Codigo de tipo de documento";
                    break;
                case INT_TBLDAT_TIPDOC:
                    strMsg = "Descripcion corta del tipo de documento";
                    break;
                case INT_TBLDAT_CODDOC:
                    strMsg = "Codigo del documento";
                    break;
                case INT_TBLDAT_NUMORDDES:
                    strMsg = "Numero de orden de despacho";
                    break;
                case INT_TBLDAT_FECDOC:
                    strMsg = "Fecha de documento";
                    break;
                case INT_TBLDAT_STCONPEN:
                    strMsg = "Confirmacion pendiente";
                    break;
                case INT_TBLDAT_STCONPAR:
                    strMsg = "Confirmacion parcial";
                    break;
                case INT_TBLDAT_STCONTOT:
                    strMsg = "Confirmacion total";
                    break;
                case INT_TBLDAT_NDCAN:
                    strMsg = "Cantidad";
                    break;
                case INT_TBLDAT_NDCANCON:
                    strMsg = "Cantidad total confirmada";
                    break;
                case INT_TBLDAT_NDCANNUNREC:
                    strMsg = "Cantidad total nunca confirmada";
                    break;
                case INT_TBLDAT_BODEGA:
                    strMsg = "Bodega";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDatCliRet.getTableHeader().setToolTipText(strMsg);
        }
    }

    private class ZafMouMotAda1 extends MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODEMP:
                    strMsg = "Codigo de la empresa";
                    break;
                case INT_TBL_CODLOC:
                    strMsg = "Codigo de local";
                    break;
                case INT_TBL_CODTIPDOC:
                    strMsg = "Codigo de tipo de documento";
                    break;
                case INT_TBL_CODDOC:
                    strMsg = "Codigo del documento";
                    break;
                case INT_TBL_DESCOR:
                    strMsg = "Descripcion corta del tipo de documento";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg = "Numero del documento";
                    break;
                case INT_TBL_FECDOC:
                    strMsg = "Fecha de documento";
                    break;
                case INT_TBL_CANGUI:
                    strMsg = "Cantidad";
                    break;
                case INT_TBL_BUTVIS:
                    strMsg = "";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        } else {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panTabGenCen = new javax.swing.JPanel();
        lblListGuiRem = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCliRet = new javax.swing.JPanel();
        lblListOrdDesRel = new javax.swing.JLabel();
        panDatRel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatCliRet = new javax.swing.JTable(){
            protected javax.swing.table.JTableHeader createDefaultTableHeader(){
                return new ZafTblHeaGrp(columnModel);
            }
        }
        ;
        panSur = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de documentos asociados");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panTabGen.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.8);
        jSplitPane1.setOneTouchExpandable(true);

        panTabGenCen.setPreferredSize(new java.awt.Dimension(453, 100));
        panTabGenCen.setLayout(new java.awt.BorderLayout());

        lblListGuiRem.setText("Listado de Guias de Remision");
        panTabGenCen.add(lblListGuiRem, java.awt.BorderLayout.NORTH);

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

        panTabGenCen.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setTopComponent(panTabGenCen);

        panCliRet.setPreferredSize(new java.awt.Dimension(453, 100));
        panCliRet.setLayout(new java.awt.BorderLayout());

        lblListOrdDesRel.setText("Listado de Ordenes de Despacho (Relacionadas)");
        panCliRet.add(lblListOrdDesRel, java.awt.BorderLayout.NORTH);

        panDatRel.setLayout(new java.awt.BorderLayout());

        tblDatCliRet.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDatCliRet);

        panDatRel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panCliRet.add(panDatRel, java.awt.BorderLayout.CENTER);

        jSplitPane1.setBottomComponent(panCliRet);

        panTabGen.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel8.add(butCan);

        panSur.add(jPanel8, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo"); // NOI18N
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-675)/2, (screenSize.height-358)/2, 675, 358);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        blnEst = false;
        cerrarCon();
        Runtime.getRuntime().gc();
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        blnEst = false;
        cerrarCon();
        Runtime.getRuntime().gc();
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        blnEst = false;
        cerrarCon();
        dispose();
        Runtime.getRuntime().gc();
    }//GEN-LAST:event_formInternalFrameClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblListGuiRem;
    private javax.swing.JLabel lblListOrdDesRel;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCliRet;
    private javax.swing.JPanel panDatRel;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTabGenCen;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatCliRet;
    // End of variables declaration//GEN-END:variables

    private class ZafThreadGUI extends Thread 
    {
        @Override
        public void run() {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            cargarDat();
            objThrGUI = null;
        }
    }

    private void cargarDat() 
    {
        //Connection con = null;
        try 
        {
            con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null) {
                if (!consultar(con, strSql01)) {
                    if (!consultar(con, strSql02)) {
                        if (verificaCli(con)) {
                            cargarDatos(con);
                        }
                    }
                }
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
            }
        }
        catch (SQLException e) {   objUti.mostrarMsgErr_F1(this, e);     } 
        catch (Exception e)    {   objUti.mostrarMsgErr_F1(this, e);     } 
        finally 
        {
            try 
            {
                if (con != null)
                {
                    con.close();
                }
                con = null;
            } 
            catch (Throwable e) 
            {
                e.printStackTrace();
            }
        }
    }

    private boolean consultar(Connection con, String strSql) 
    {
        boolean blnRes = false;
        Statement stm = null;
        ResultSet rst = null;
        try
        {
            if (con != null) 
            {
                stm = con.createStatement();
                Vector vecData = new Vector();
                rst = stm.executeQuery(strSql);
                while (rst.next())
                {
                    Vector vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CODEMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTIPDOC, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_CODDOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DESCOR, rst.getString("tx_descor"));
                    vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_CANGUI, rst.getString("cangui"));
                    vecReg.add(INT_TBL_BUTVIS, "");
                    vecData.add(vecReg);
                    blnRes = true;
                }
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
            }
        }
        catch (SQLException e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        finally
        {
            try
            {
                if (rst != null) 
                {
                    rst.close();
                }
                rst = null;

                if (stm != null) 
                {
                    stm.close();
                }
                stm = null;
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        return blnRes;
    }

    private void cargarDatos(Connection con)
    {
        String strSql = "", strSql1 = "", strSql2 = "", strSql3 = "", strSql4 = "";
        String strConInv = "";
        PreparedStatement pstLoc = null, pstLoc1 = null;
        ResultSet rstLoc = null, rstLoc1 = null;

        strSql = " select co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel  "
                + " from ( "
                + " select a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel, a.co_regrel "
                + " from tbm_detguirem as a "
                + " inner join tbm_detmovinv as a2 on (a.co_emprel=a2.co_emp and a.co_locrel=a2.co_loc and a.co_tipdocrel=a2.co_tipdoc and a.co_docrel=a2.co_doc and a.co_regrel=a2.co_reg) "
                + " inner join tbm_cabmovinv as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc) "
                + " inner join tbm_cabguirem as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc and a4.co_doc=a.co_doc) "
                + " where a3.co_emp=? "
                + " and a3.co_loc=? "
                + " and a3.co_tipdoc=? "
                + " and a3.co_doc=? "
                + " and a2.co_reg=? "
                + " and a3.st_reg not in ('I','E') "
                + " and a4.st_reg = 'A' "
                + " ) as x "
                + " group by co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel ";

        strSql1 = " select x5.tx_nom, x3.tx_codalt, "
                + " round((abs(x3.nd_can)-abs(case when x3.nd_cancon is null or x3.nd_cancon = 0 then case when x3.nd_cannunrec is null or x3.nd_cannunrec = 0 then 0 else x3.nd_cannunrec end else x3.nd_cancon end)),2) as canfalegr, "
                + " x4.ne_numorddes, case when  x4.ne_numdoc > 0 then x4.ne_numdoc else null end as ne_numdoc, "
                + " case when x4.st_coninv IN ('P','E') then (case when x7.st_coninv is null then 'P' else x7.st_coninv end) else x4.st_coninv end as st_conInv, "
                + " x4.co_emp, x4.co_loc, x4.co_tipdoc, x4.co_doc, x4.fe_doc, x3.nd_can, x3.nd_cancon, x3.nd_cannunrec, x6.tx_descor "
                + " from tbr_detmovinv as x "
                + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel  and (x1.co_emp!=x.co_emp or x1.co_loc!=x.co_loc or x1.co_tipdoc!=x.co_tipdoc or x1.co_doc!=x.co_doc) ) "
                + " inner join tbm_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg) "
                + " inner join tbm_cabMovInv as x7 on (x7.co_Emp=x2.co_Emp AND x7.co_loc=x2.co_loc AND x7.co_tipDoc=x2.co_tipDoc AND x7.co_Doc=x2.co_Doc)"
                + " inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg) "
                + " inner join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc) "
                + " inner join tbm_bod as x5 on (x5.co_emp=x4.co_emp and x5.co_bod=x4.co_ptopar) "
                + " inner join tbm_cabtipdoc as x6 on (x6.co_emp=x4.co_emp and x6.co_loc=x4.co_loc and x6.co_tipdoc=x4.co_tipdoc) "
                + " where x.co_emp=? "
                + " and x.co_loc=? "
                + " and x.co_tipdoc=? "
                + " and x.co_doc=? "
                + " and x.co_reg=? "
                + " and x.st_reg = 'A' "
                + " and x4.st_reg = 'A' "
                + " order by x4.co_emp, x4.co_loc, x4.co_tipdoc, x4.co_doc ";

        strSql2 = " select a3.co_emp, a3.co_loc, a3.co_tipdoc, a4.tx_descor, a3.co_doc, a3.ne_numorddes, a1.fe_doc,"
                + " case when a3.st_coninv in ('P', 'E') then (case when a1.st_coninv is null then 'P' else a1.st_coninv end) else a3.st_coninv end as st_conInv, "
                + " a2.nd_can, a2.nd_cancon, a2.nd_cannunrec, a5.tx_nom "
                + " from tbm_detmovinv as a "
                + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                + " inner join tbm_detguirem as a2 on (a2.co_emprel=a.co_emp and a2.co_locrel=a.co_loc and a2.co_tipdocrel=a.co_tipdoc and a2.co_docrel=a.co_doc and a2.co_regrel=a.co_reg ) "
                + " inner join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) "
                + " inner join tbm_cabtipdoc as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc ) "
                + " inner join tbm_bod as a5 on (a5.co_emp=a3.co_emp and a5.co_bod=a3.co_ptopar ) "
                + " where a.co_emp=? "
                + " and a.co_loc=? "
                + " and a.co_tipdoc=? "
                + " and a.co_doc=? "
                + " and a.co_reg=? "
                //+ " and a.co_emp != "+ objZafParSis.getCodigoEmpresa() + " "
                + " and a1.st_reg not in ('I','E') "
                + " and a3.st_reg = 'A' "
                + " and a.st_meringegrfisbod='S' "
                + " order by a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc ";

        strSql3 = " select a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel, a2.co_regrel "
                + " from tbm_detmovinv as a "
                + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) "
                + " inner join tbr_detmovinv as a2 on (a2.co_emprel=a.co_emp and a2.co_locrel=a.co_loc and a2.co_tipdocrel=a.co_tipdoc and a2.co_docrel=a.co_doc and a2.co_regrel=a.co_reg and (a2.co_emp=a.co_emp ) ) "
                + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_locrel=a2.co_loc and a3.co_tipdocrel=a2.co_tipdoc and a3.co_docrel=a2.co_doc and a3.co_regrel=a2.co_reg ) "
                + " inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc ) "
                + " where a.co_emp=? "
                + " and a.co_loc=? "
                + " and a.co_tipdoc=? "
                + " and a.co_doc=? "
                + " and a.co_reg=? "
                + " and a2.st_reg='A' "
                + " and a1.st_reg not in ('I', 'E')  ";

        strSql4 = " select x5.tx_nom, x3.tx_codalt, x4.ne_numorddes, "
                + " round((abs(x3.nd_can)-abs(case when x3.nd_cancon is null or x3.nd_cancon = 0 then case when x3.nd_cannunrec is null or x3.nd_cannunrec = 0 then 0 else x3.nd_cannunrec end else x3.nd_cancon end)),2) as canfalegr,  "
                + " case when  x4.ne_numdoc > 0 then x4.ne_numdoc else null end as ne_numdoc, "
                + " case when x4.st_coninv in ('P', 'E') then (case when x7.st_coninv is null then 'P' else x7.st_coninv end) else x4.st_coninv end as st_conInv, "
                + " x4.co_emp, x4.co_loc, x4.co_tipdoc, x4.co_doc, "
                + " x4.fe_doc, x3.nd_can, x3.nd_cancon, x3.nd_cannunrec, x6.tx_descor "
                + " from tbr_detmovinv as x  "
                + " inner join tbm_detmovinv as x2 on (x2.co_emp=x.co_emp and x2.co_loc=x.co_loc and x2.co_tipdoc=x.co_tipdoc and x2.co_doc=x.co_doc and x2.co_reg=x.co_reg ) "
                + " inner join tbm_cabMovInv as x7 on (x7.co_Emp=x2.co_Emp AND x7.co_loc=x2.co_loc AND x7.co_tipDoc=x2.co_tipDoc AND x7.co_Doc=x2.co_Doc)"
                + " inner join tbm_detguirem as x3 on (x3.co_emprel=x2.co_emp and x3.co_locrel=x2.co_loc and x3.co_tipdocrel=x2.co_tipdoc and x3.co_docrel=x2.co_doc and x3.co_regrel=x2.co_reg) "
                + " inner join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc) "
                + " inner join tbm_bod as x5 on (x5.co_emp=x4.co_emp and x5.co_bod=x4.co_ptopar) "
                + " inner join tbm_cabtipdoc as x6 on (x6.co_emp=x4.co_emp and x6.co_loc=x4.co_loc and x6.co_tipdoc=x4.co_tipdoc) "
                + " where x.co_emprel=? "
                + " and x.co_locrel=? "
                + " and x.co_tipdocrel=? "
                + " and x.co_docrel=? "
                + " and x.co_regrel=? "
                + " and x.co_emp != " + objZafParSis.getCodigoEmpresa() + " "
                + " and x.st_reg = 'A' "
                + " and x4.st_reg = 'A' ";

        try {
            //Rose: Se modifica para que muestre el estado de las ordenes de despacho de las TRAINV en el punto destino.
            if (this.intCodLoc == 2 || this.intCodLoc == 5) {
                strSql = strSql3;
            } else {
                if (this.intCodTipDoc == 46 || this.intCodTipDoc == 172 || this.intCodTipDoc == 153) {
                    this.intCoReg = ((this.intCoReg % 2) == 0) ? this.intCoReg - 1 : this.intCoReg;
                }
            }

            //<editor-fold defaultstate="collapsed" desc="/* Antes de comentar Rose */">
            //if ( this.intCodTipDoc == 46 || this.intCodTipDoc == 172 || this.intCodTipDoc == 153 ) { 
            //    this.intCoReg=((this.intCoReg%2)==0)?this.intCoReg-1:this.intCoReg;
            //}
            //if ( this.intCodLoc == 2 || this.intCodLoc == 5) { 
            //    strSql=strSql3; 
            //    //this.intCoReg=((this.intCoReg%2)==0)?this.intCoReg-1:this.intCoReg;
            //}
            //</editor-fold>

            Vector vecData = new Vector();
            //System.out.println("cargarDatos: "+strSql);
            pstLoc = con.prepareStatement(strSql);
            pstLoc.setInt(1, this.intCodEmp);
            pstLoc.setInt(2, this.intCodLoc);
            pstLoc.setInt(3, this.intCodTipDoc);
            pstLoc.setInt(4, this.intCodDoc);
            pstLoc.setInt(5, this.intCoReg);
            //pstLoc.setInt(6, objZafParSis.getCodigoEmpresa());

            rstLoc = pstLoc.executeQuery();
            vecData.clear();
            boolean blnReg = false;
            int intCodReg = 0;
            while (rstLoc.next()) {
                blnReg = false;
                if ((rstLoc.getInt("co_tipdocrel") == 46 || rstLoc.getInt("co_tipdocrel") == 172 || rstLoc.getInt("co_tipdocrel") == 153)) {
                    strSql1 = strSql2;
                    intCodReg = ((rstLoc.getInt("co_regrel") % 2) == 0) ? rstLoc.getInt("co_regrel") - 1 : rstLoc.getInt("co_regrel");
                    blnReg = true;
                }
                if (rstLoc.getInt("co_locrel") == 2 || rstLoc.getInt("co_locrel") == 5) {
                    strSql1 = strSql4;
                    intCodReg = rstLoc.getInt("co_regrel");
                }
                if (rstLoc.getInt("co_tipdocrel") == 228 || rstLoc.getInt("co_tipdocrel") == 294) // Facturas //EGBOPF (Prefacturas) //Rose
                {
                    strSql1 = strSql4; //Datos de la GR.
                    intCodReg = rstLoc.getInt("co_regrel");
                }
                System.out.println("strSql1: "+strSql1);
                pstLoc1 = con.prepareStatement(strSql1);
                pstLoc1.setInt(1, rstLoc.getInt("co_emprel"));
                pstLoc1.setInt(2, rstLoc.getInt("co_locrel"));
                pstLoc1.setInt(3, rstLoc.getInt("co_tipdocrel"));
                pstLoc1.setInt(4, rstLoc.getInt("co_docrel"));
                //pstLoc1.setInt(5, rstLoc.getInt("co_regrel") );
                pstLoc1.setInt(5, intCodReg);
                //pstLoc.setInt(6, objZafParSis.getCodigoEmpresa());
                
                rstLoc1 = pstLoc1.executeQuery();
                while (rstLoc1.next()) {
                    Vector vecReg = new Vector();
                    vecReg.add(INT_TBLDAT_LINEA, "");
                    vecReg.add(INT_TBLDAT_CODEMP, rstLoc1.getString("co_emp"));
                    vecReg.add(INT_TBLDAT_CODLOC, rstLoc1.getString("co_loc"));
                    vecReg.add(INT_TBLDAT_CODTIPDOC, rstLoc1.getString("co_tipdoc"));
                    vecReg.add(INT_TBLDAT_TIPDOC, rstLoc1.getString("tx_descor"));
                    vecReg.add(INT_TBLDAT_CODDOC, rstLoc1.getString("co_doc"));
                    vecReg.add(INT_TBLDAT_NUMORDDES, rstLoc1.getString("ne_numorddes"));
                    vecReg.add(INT_TBLDAT_FECDOC, rstLoc1.getString("fe_doc"));
                    strConInv = (rstLoc1.getString("st_coninv") == null) ? "" : rstLoc1.getString("st_coninv");
                    if (strConInv != null) {
                        if (strConInv.equals("P")) {
                            vecReg.add(INT_TBLDAT_STCONPEN, true);
                            vecReg.add(INT_TBLDAT_STCONPAR, false);
                            vecReg.add(INT_TBLDAT_STCONTOT, false);
                        }
                        if (strConInv.equals("E")) {
                            vecReg.add(INT_TBLDAT_STCONPEN, false);
                            vecReg.add(INT_TBLDAT_STCONPAR, true);
                            vecReg.add(INT_TBLDAT_STCONTOT, false);
                        }
                        if (strConInv.equals("C")) {
                            vecReg.add(INT_TBLDAT_STCONPEN, false);
                            vecReg.add(INT_TBLDAT_STCONPAR, false);
                            vecReg.add(INT_TBLDAT_STCONTOT, true);
                        }
                        if (strConInv.equals("F") || strConInv.equals("")) {
                            vecReg.add(INT_TBLDAT_STCONPEN, false);
                            vecReg.add(INT_TBLDAT_STCONPAR, false);
                            vecReg.add(INT_TBLDAT_STCONTOT, false);
                        }
                    }

                    vecReg.add(INT_TBLDAT_NDCAN, rstLoc1.getString("nd_can"));
                    vecReg.add(INT_TBLDAT_NDCANCON, rstLoc1.getString("nd_cancon"));
                    vecReg.add(INT_TBLDAT_NDCANNUNREC, rstLoc1.getString("nd_cannunrec"));
                    vecReg.add(INT_TBLDAT_BODEGA, rstLoc1.getString("tx_nom"));
                    vecReg.add(INT_TBLDAT_BUTGUI, "");
                    vecData.add(vecReg);
                    //blnRes=true;
                }
            }
            objTblModCliRet.setData(vecData);
            tblDatCliRet.setModel(objTblModCliRet);

        } 
        catch (SQLException e) {  objUti.mostrarMsgErr_F1(this, e);  } 
        catch (Exception e)    {  objUti.mostrarMsgErr_F1(this, e);  }
        finally 
        {
            try 
            {
                if (rstLoc1 != null)
                {
                    rstLoc1.close();
                }
                rstLoc1 = null;

                if (pstLoc1 != null) 
                {
                    pstLoc1.close();
                }
                pstLoc1 = null;

                if (rstLoc != null)
                {
                    rstLoc.close();
                }
                rstLoc = null;

                if (pstLoc != null) 
                {
                    pstLoc.close();
                }
                pstLoc = null;
            } 
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
    }

    private boolean verificaCli(Connection con) 
    {
        boolean blnRes = false;
        PreparedStatement pstLoc = null, pstLoc2 = null;
        ResultSet rstLoc = null, rstLoc2 = null;
        String strSql = "", strSql2 = "";
        try
        {
            if (con != null) 
            {
                if (this.intCodLoc != 2 && this.intCodLoc != 5) 
                {
                    if ((this.intCodTipDoc != 46 && this.intCodTipDoc != 172 && this.intCodTipDoc != 153))
                    {
                        strSql2 = " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg "
                                + " from tbr_detmovinv as a "
                                + " where a.co_emprel=? "
                                + " and a.co_locrel=? "
                                + " and a.co_tipdocrel=? "
                                + " and a.co_docrel=? "
                                + " and a.co_regrel=? "
                                + " and a.co_emp=? ";

                        //System.out.println("ZafCom51_02.verificaCli1: " + strSql2);

                        pstLoc = con.prepareStatement(strSql2);
                        pstLoc.setInt(1, intCodEmp);
                        pstLoc.setInt(2, intCodLoc);
                        pstLoc.setInt(3, intCodTipDoc);
                        pstLoc.setInt(4, intCodDoc);
                        pstLoc.setInt(5, intCoReg);
                        pstLoc.setInt(6, intCodEmp);
                        rstLoc = pstLoc.executeQuery();
                        if (rstLoc.next()) 
                        {
                            this.intCodEmp = rstLoc.getInt("co_emp");
                            this.intCodLoc = rstLoc.getInt("co_loc");
                            this.intCodTipDoc = rstLoc.getInt("co_tipdoc");
                            this.intCodDoc = rstLoc.getInt("co_doc");
                            this.intCoReg = rstLoc.getInt("co_reg");
                        }
                    }
                }
                strSql = " select case when a.co_cli is null then a3.co_clides else a.co_cli end as co_cli, a.co_tipdoc, a4.co_empgrp "
                        + " from tbm_cabmovinv as a "
                        + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                        + " left outer join tbm_detguirem as a2 on (a1.co_emp=a2.co_emprel and a1.co_loc=a2.co_locrel and a1.co_tipdoc=a2.co_tipdocrel and a1.co_doc=a2.co_docrel and a1.co_reg=a2.co_regrel ) "
                        + " left outer join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) "
                        + " inner join tbm_cli as a4 on (a4.co_emp=a.co_emp and a4.co_cli=case when a.co_cli is null then a3.co_clides else a.co_cli end ) "
                        + " where a.co_emp=? "
                        + " and a.co_loc=? "
                        + " and a.co_tipdoc=? "
                        + " and a.co_doc=? "
                        + " group by a.co_cli, a3.co_clides,a.co_tipdoc, a4.co_empgrp ";

                //System.out.println("ZafCom51_02.verificaCli2: " + strSql);

                pstLoc2 = con.prepareStatement(strSql);
                pstLoc2.setInt(1, intCodEmp);
                pstLoc2.setInt(2, intCodLoc);
                pstLoc2.setInt(3, intCodTipDoc);
                pstLoc2.setInt(4, intCodDoc);
                rstLoc2 = pstLoc2.executeQuery();
                if (rstLoc2.next())
                {
                    if (rstLoc2.getString("co_empgrp") == null) 
                    {
                        blnRes = true;
                    } else if (rstLoc2.getInt("co_empgrp") == 0 && (rstLoc2.getInt("co_tipdoc") == 46 || rstLoc2.getInt("co_tipdoc") == 172 || rstLoc2.getInt("co_tipdoc") == 153 || rstLoc2.getInt("co_tipdoc") == 2 || rstLoc2.getInt("co_tipdoc") == 3 || rstLoc2.getInt("co_tipdoc") == 4) || rstLoc2.getInt("co_tipdoc") == 205) {
                        blnRes = true;
                    } else {
                        blnRes = false;
                    }
                    //blnRes=true;                     
                }
            }
        }
        catch (SQLException Evt) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
        catch (Exception Evt) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        finally
        {
            try 
            {
                if (rstLoc != null) {
                    rstLoc.close();
                }
                rstLoc = null;

                if (pstLoc != null) {
                    pstLoc.close();
                }
                pstLoc = null;

                if (rstLoc2 != null) {
                    rstLoc2.close();
                }
                rstLoc2 = null;

                if (pstLoc2 != null) {
                    pstLoc2.close();
                }
                pstLoc2 = null;
            } 
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return blnRes;
    }

    /**
     * Cierra conexion.
     */
    private void cerrarCon() 
    {
        try 
        {
            if (con != null) 
            {
                con.close();
            }
            con = null;
        }
        catch (Throwable e) 
        {
            con = null;
        }
    }

    private void mostrarGuiasRemision(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        String strSql = "";
        try
        {
            strSql = " select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
                    + " from tbr_detguirem as a2 "
                    + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg ) "
                    + " inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc ) "
                    + " inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
                    + " where a2.co_emp=" + strCodEmp + " and a2.co_locrel=" + strCodLoc + " and a2.co_tipdocrel=" + strCodTipDoc + " and a2.co_docrel=" + strCodDoc + " and a4.st_reg='A' "
                    + " group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
                    + " order by a4.ne_numdoc ";

            //System.out.println("ZafCom51_02.mostrarGuiasRemision: " + strSql);            

            ZafVen21_01 obj1 = new ZafVen21_01(objZafParSis, JIntFra, strSql);
            this.getParent().add(obj1, 0);
            obj1.show();

        } 
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
}
