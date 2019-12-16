package RecursosHumanos.ZafRecHum26;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_dep;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenDep;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenUsr;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Roberto Flores Guayaquil
 */
public class ZafRecHum26 extends javax.swing.JInternalFrame 
{
    private final int INT_TBL_LINEA = 0;                                        //Índice de columna Linea.
    private final int INT_TBL_CODUSR = 1;                                       //Índice de columna Código de Usuario.
    private final int INT_TBL_USR = 2;                                          //Índice de columna Usuario.
    private final int INT_TBL_BUTUSR = 3;                                       //Índice de columna Botón de Usuario.
    private final int INT_TBL_NOMUSR = 4;                                       //Índice de columna Nombre de Usuario.
    private final int INT_TBL_JERUSR = 5;                                       //Índice de columna Jerarquía.
    private final int INT_TBL_CHKOBLSEGJER = 6;                                 //Índice de columna Obligatorio Seguir Jerarquía.
    private final int INT_TBL_COREG = 7;

    Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                                //Editor: Editor del JTable.
    private ZafUtil objUti;
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                                 //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                                 //Renderer de Check Box para campo Laborable
    private ZafMouMotAda objMouMotAda;                                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PeopuMenú en JTable.

    private ZafVenCon vcoDep;                                                   //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoUsr;                                                   //Ventana de consulta "Item".
    private ZafVenUsr zafVenUsr;
    
    private MiToolBar tooBar;                                                   //Barra de herramientas

    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                    //Editor: JTextField en celda.

    boolean blnChangeData = false;
    private boolean blnMod;                                                     //Indica si han habido cambios en el documento
    private boolean blnConsDat;

    private boolean blnEsCon;
    private boolean blnActChkMod;                                               //Indica si se Activa o Inactiva el verificar cambios en el documento
    private boolean blnCas = false;                                             //control caso consulta eliminacion ingresando un departamento

    String strBeforeValue, strAfterValue;

    private int intPagAct;                                                      //Indice actual de los registros de la consulta
    private int intPagFin;                                                      //Ultimo indice de los registros de la consulta

    private List<Tbm_dep> lisTbm_dep;                                           //Lista con datos de consulta general

    private Tbm_dep tbm_dep;                                                    //Pojo de Departamentos
    private Tbm_dep tbm_deppar;                                                 //Parametros de Consulta para Empleados

    private DocLis docLis;                                                      //Listener que indica si han habido cambios en el documento

    private String strTx_TipHorAut;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private ZafTblFilCab objTblFilCab;

    private String strNomDep = "";
    private String strCodDep = "";

    private ZafTblCelEdiTxtVco objTblCelEdiTxtVco;                              //Editor: JTextField de consulta en celda.

    /**
     * Creates new form pantalla
     */
    public ZafRecHum26(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            if(objZafParSis.getCodigoMenu()==3019)
            {
                initComponents();

                this.setTitle(objZafParSis.getNombreMenu() + " v1.1");
                lblTit.setText(objZafParSis.getNombreMenu());

                initTooBar(this);
                panBar.add(getTooBar());

                objUti = new ZafUtil();

                tbm_dep = new Tbm_dep();
                tbm_deppar = new Tbm_dep();

                tblDat.getModel().addTableModelListener(new MyTableModelListener(tblDat));

                intPagAct = 0;
                intPagFin = 0;
                lisTbm_dep = null;

                if (objZafParSis.getCodigoMenu() == 3019) {
                    this.strTx_TipHorAut = "S";
                } else if (objZafParSis.getCodigoMenu() == 3027) {
                    this.strTx_TipHorAut = "E";
                }

                blnMod = false;
                blnConsDat = false;
                blnActChkMod = false;

                configurarVenConDep();
                configurarVenConUsr();
                //Configurar los JTables.
                configurarTblDat();
            }
            else 
            {
                mostrarMsgInf("Aun NO esta habilitado el programa de Horas Extraordinarias.");
                dispose();
            }

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");

            String strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 4;
            //intColOcu[1]=6;
            //intColOcu[2]=7;
            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public class MyTableModelListener implements TableModelListener
    {

        JTable table;

        // It is necessary to keep the table since it is not possible to determine the table from the event's source
        MyTableModelListener(JTable table) {
            this.table = table;
        }

        public void tableChanged(TableModelEvent e) {

            if (!blnConsDat) {
                switch (e.getType()) {
                    case TableModelEvent.UPDATE:
                        blnMod = true;
                        break;
                }
            }
            blnConsDat = false;

        }
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los
     * objetos de tipo texto para poder determinar si su contenido a cambiado o
     * no.
     */
    public void agregarDocLis() 
    {
       
    }

    
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(22);  //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODUSR, "Cód.Usr.");
            vecCab.add(INT_TBL_USR, "Usuario");
            vecCab.add(INT_TBL_BUTUSR, "...");
            vecCab.add(INT_TBL_NOMUSR, "Nombre");
            vecCab.add(INT_TBL_JERUSR, "Jerarquía");
            vecCab.add(INT_TBL_CHKOBLSEGJER, "Obl.Seg.Jer.");
            vecCab.add(INT_TBL_COREG, "Cód.Reg.");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_JERUSR, objTblMod.INT_COL_INT, new Integer(1), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux = new java.util.ArrayList();
            //arlAux.add("" + INT_TBL_USR);
            arlAux.add("" + INT_TBL_JERUSR);
            //arlAux.add("" + INT_TBL_CHKOBLSEGJER);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux = null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODUSR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_USR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTUSR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMUSR).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_JERUSR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHKOBLSEGJER).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_COREG).setPreferredWidth(70);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_JERUSR).setResizable(false);
            tcmAux.getColumn(INT_TBL_CHKOBLSEGJER).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CODUSR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_COREG, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_USR);
            vecAux.add("" + INT_TBL_BUTUSR);
            vecAux.add("" + INT_TBL_JERUSR);
            vecAux.add("" + INT_TBL_CHKOBLSEGJER);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            //tcmAux.getColumn(INT_TBL_CODUSR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_JERUSR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CHKOBLSEGJER).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("####", true, true);
            tcmAux.getColumn(INT_TBL_JERUSR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_USR).setCellRenderer(objTblCelRenLbl);

           
            int intColVen[] = new int[3];
            intColVen[0] = 1;
            intColVen[1] = 2;
            intColVen[2] = 3;

            int intColTbl[] = new int[3];
            intColTbl[0] = INT_TBL_CODUSR;
            intColTbl[1] = INT_TBL_NOMUSR;
            intColTbl[2] = INT_TBL_USR;

            objTblCelEdiTxtVco = new ZafTblCelEdiTxtVco(tblDat, vcoUsr, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_USR).setCellEditor(objTblCelEdiTxtVco);
            objTblCelEdiTxtVco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoUsr.setCampoBusqueda(3);
                    vcoUsr.setCriterio1(11);

                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                  
                }
            });

            objTblCelRenLbl = null;

            zafTblCelRenChkLab = new ZafTblCelRenChk();
            zafTblCelRenChkLab.setBackground(objZafParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_CHKOBLSEGJER).setCellRenderer(zafTblCelRenChkLab);
            zafTblCelEdiChkLab = new ZafTblCelEdiChk();

            tcmAux.getColumn(INT_TBL_CHKOBLSEGJER).setCellEditor(zafTblCelEdiChkLab);

            objTblCelEdiTxt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_JERUSR).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    blnChangeData = false;
                    if (tblDat.getValueAt(intNumFil, tblDat.getSelectedColumn()) != null) {
                        strBeforeValue = tblDat.getValueAt(intNumFil, tblDat.getSelectedColumn()).toString();
                    } else {
                        strBeforeValue = "";
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }

            });

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTUSR).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUTUSR, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    int intFil = tblDat.getRowCount();

                    vcoUsr.limpiar();
                    vcoUsr.setCampoBusqueda(2);
                    vcoUsr.setVisible(true);
                    if (vcoUsr.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                        tblDat.setValueAt(vcoUsr.getValueAt(1), intSelFil, INT_TBL_CODUSR);
                        tblDat.setValueAt(vcoUsr.getValueAt(2), intSelFil, INT_TBL_NOMUSR);
                        tblDat.setValueAt(vcoUsr.getValueAt(3), intSelFil, INT_TBL_USR);

                       
                        String strUsrMod = tblDat.getValueAt(intSelFil, INT_TBL_USR).toString();
                        int intCantUsrRep = 0;
                        for (int fil = 0; fil < intFil; fil++) {

                            Object obj = tblDat.getValueAt(fil, INT_TBL_USR);
                            if (obj != null) {
                                String strUsrRec = obj.toString();
                                if (strUsrRec.compareTo(strUsrMod) == 0) {
                                    intCantUsrRep++;
                                    if (intCantUsrRep > 1) {
                                        mostrarMsgInf("HA INGRESADO MAS DE UNA VEZ EL MISMO USUARIO");
                                        objTblMod.removeRow(tblDat.getSelectedRow());
                                        fil = intFil;
                                    }
                                }
                            }
                        }
                    }
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Usuarios".
     */
    private boolean configurarVenConUsr() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("co_usr");
            arlCam.add("tx_nom");
            //arlCam.add("tx_desLar");
            arlCam.add("tx_usr");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //arlAli.add("Grupo");
            arlAli.add("Usuario");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("240");
            //arlAncCol.add("70");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            String strSQL = "SELECT u.co_usr,u.tx_nom,g.tx_desLar,u.tx_usr,u.st_usrSis,u.st_reg,g.co_grp FROM tbm_usr as u, tbm_grpUsr as g WHERE g.co_grp = u.co_grpUsr and u.st_reg like 'A' and u.co_usr not in (1) order by u.co_usr";

            //Ocultar columnas.
            int intColOcu[] = new int[3];
            //intColOcu[0]=3;
            //intColOcu[1]=2;
            //intColOcu[2]=6;
            vcoUsr = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;

            vcoUsr.setCampoBusqueda(2);
        } catch (Exception e) {
            e.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panFilTabGen = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCab = new javax.swing.JPanel();
        lblEmp = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        butSub = new javax.swing.JButton();
        butBaj = new javax.swing.JButton();
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

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        scrTbl.setPreferredSize(new java.awt.Dimension(0, 403));

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
        tblDat.setColumnSelectionAllowed(true);
        scrTbl.setViewportView(tblDat);

        panCab.setPreferredSize(new java.awt.Dimension(0, 55));
        panCab.setLayout(null);

        lblEmp.setText("Departamento:");
        panCab.add(lblEmp);
        lblEmp.setBounds(4, 25, 100, 20);

        txtCodDep.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.setMinimumSize(new java.awt.Dimension(4, 20));
        txtCodDep.setPreferredSize(new java.awt.Dimension(4, 20));
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panCab.add(txtCodDep);
        txtCodDep.setBounds(110, 25, 70, 20);

        txtNomDep.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomDep.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomDep.setMinimumSize(new java.awt.Dimension(4, 20));
        txtNomDep.setPreferredSize(new java.awt.Dimension(4, 20));
        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        panCab.add(txtNomDep);
        txtNomDep.setBounds(180, 25, 370, 20);

        butDep.setText("...");
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panCab.add(butDep);
        butDep.setBounds(550, 25, 20, 20);

        butSub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleArr_7_16x16.gif"))); // NOI18N
        butSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSubActionPerformed(evt);
            }
        });

        butBaj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Imagenes/FleAba_7_16x16.gif"))); // NOI18N
        butBaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBajActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panFilTabGenLayout = new javax.swing.GroupLayout(panFilTabGen);
        panFilTabGen.setLayout(panFilTabGenLayout);
        panFilTabGenLayout.setHorizontalGroup(
            panFilTabGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilTabGenLayout.createSequentialGroup()
                .addComponent(panCab, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panFilTabGenLayout.createSequentialGroup()
                .addComponent(scrTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panFilTabGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(butSub)
                    .addComponent(butBaj))
                .addGap(27, 27, 27))
        );
        panFilTabGenLayout.setVerticalGroup(
            panFilTabGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilTabGenLayout.createSequentialGroup()
                .addComponent(panCab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panFilTabGenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFilTabGenLayout.createSequentialGroup()
                        .addComponent(butSub)
                        .addGap(4, 4, 4)
                        .addComponent(butBaj))
                    .addComponent(scrTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );

        tabGen.addTab("General", panFilTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm


    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConDep(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) 
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    } 
                    else 
                    {
                        vcoDep.setCampoBusqueda(0);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        } 
                        else 
                        {
                            txtCodDep.setText(strCodDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText())) 
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    } 
                    else
                    {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strNomDep);
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

    private void butSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSubActionPerformed
        int row = tblDat.getSelectedRow();
        if (row > 0 && row < objTblMod.getRowCount() - 1) {
            objTblMod.moveRow(row, row - 1);
            tblDat.addRowSelectionInterval(row - 1, row - 1);
            tblDat.setColumnSelectionInterval(0, tblDat.getColumnCount() - 1);
        }
    }//GEN-LAST:event_butSubActionPerformed

    private void butBajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBajActionPerformed
        int row = tblDat.getSelectedRow();
        int rw = objTblMod.getRowCount() - 2;
        System.out.println("rw: " + rw);
        if (row >= 0 && row < rw) {
            objTblMod.moveRow(row, row + 1);
            tblDat.addRowSelectionInterval(row + 1, row + 1);
            tblDat.setColumnSelectionInterval(0, tblDat.getColumnCount() - 1);
        }
    }//GEN-LAST:event_butBajActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        strNomDep = txtNomDep.getText();
        txtNomDep.selectAll();
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        if (txtNomDep.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strNomDep)) 
            {
                if (txtNomDep.getText().equals("")) 
                {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                } else {
                    mostrarVenConDep(2);
                }
            } else {
                txtNomDep.setText(strNomDep);
            }
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        if (txtCodDep.isEditable()) 
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) 
            {
                if (txtCodDep.getText().equals("")) 
                {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                } else {
                    mostrarVenConDep(1);
                }
            } else {
                txtCodDep.setText(strCodDep);
            }
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Verifica si existen cambios en el registro actual. Pregunta si desea
     * guardar cambios.
     *
     * @return Retorna true si continua con la accion o false si se detiene.
     */
    private boolean verificarCamReg() 
    {
        boolean blnOk = true;

        if (blnMod) {
            String strMsg = "Existen cambios sin guardar! \nEstá Seguro que desea continuar?";
            String strTit = "Mensaje del sistema Zafiro";
            blnOk = (JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
        }
        if (blnOk) {
            blnMod = false;
            blnActChkMod = false;
        }

        return blnOk;
    }

    
    //*********************************************************************************************************************
    public void setEditable(boolean editable) {
        if (editable == true) {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

        } else {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }

    private void habilitarCom(boolean blnTxtCodDep, boolean blnTxtNomDep, boolean blnButDep) 
    {
        txtCodDep.setEditable(blnTxtCodDep);
        txtNomDep.setEditable(blnTxtNomDep);
        butDep.setEnabled(blnButDep);
    }

    /**
     * Inicializa la barra de herramientas
     *
     * @param zafRecHum21
     */
    public void initTooBar(ZafRecHum26 zafRecHum26) 
    {
        tooBar = new MiToolBar(zafRecHum26);
        tooBar.setVisibleInsertar(false);
        tooBar.setVisibleAnular(false);
    }

    /**
     * @return the tooBar
     */
    public MiToolBar getTooBar() {
        return tooBar;
    }

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de
     * herramientas contiene los botones que realizan las diferentes operaciones
     * del sistema. Es decir, insertar, consultar, modificar, eliminar, etc.
     * Además de los botones de navegación que permiten desplazarse al primero,
     * anterior, siguiente y último registro.
     */
    public class MiToolBar extends ZafToolBar {

        public MiToolBar(JInternalFrame objIntFra) {
            super(objIntFra, objZafParSis);
        }

        private void mostrarEstado() 
        {
            setEstado('w');//l-c-x-y-z-n-m-e-a-w
            if (tbm_dep.getStrSt_Reg().equals("E")) 
            {
                setEstadoRegistro("Eliminado");
                setEnabledModificar(false);
                setEnabledEliminar(false);
                setEnabledAnular(false);
            } 
            else 
            {
                if (tbm_dep.getStrSt_Reg().equals("I")) 
                {
                    setEstadoRegistro("Anulado");
                    setEnabledModificar(false);
                    setEnabledEliminar(false);
                    setEnabledAnular(false);
                } 
                else 
                {
                    setEstadoRegistro("");
                }
            }
        }

        /**
         * Pagineo de Base. Permite consultar registros página a página según
         * consulta. Optimiza espacio de memoria al tener un registro cargado a
         * la vez. Minimiza el riesgo de dejar conecciones, statements y
         * cursores abiertos.
         */
        private void pagineoBas() 
        {
            List<Tbm_dep> listmp = null;

            java.sql.Connection conn;
            java.sql.Statement stmLoc = null;
            PreparedStatement preSta = null;
            ResultSet resSet = null;
            String strSql = null;
            Vector vecDataCon = null;

            try 
            {

                if (lisTbm_dep != null) {

                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {

                        stmLoc = conn.createStatement();
                        strSql = "select * from tbm_dep ";
                        strSql += "where (? is null or co_dep = ?) ";
                        strSql += "and (? is null or lower(tx_descor) like lower(?)) ";
                        strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
                        strSql += "and (? is null or lower(st_reg) like lower(?)) ";
                        strSql += "order by co_dep ";
                        strSql += "limit ? offset ?";

                        int intCanReg = 1;
                        int intRegIni = intPagAct;

                        /*System.out.println("consulta realizada: " + strSql);
                         resSet=stmLoc.executeQuery(strSql);*/
                        preSta = conn.prepareStatement(strSql);

                        if (tbm_deppar.getIntCo_Dep() > 0) {
                            preSta.setInt(1, tbm_deppar.getIntCo_Dep());
                            preSta.setInt(2, tbm_deppar.getIntCo_Dep());
                        } else {
                            preSta.setNull(1, Types.INTEGER);
                            preSta.setNull(2, Types.INTEGER);
                        }

                        if (tbm_deppar.getStrTx_DesCor() != null) {
                            preSta.setString(3, tbm_deppar.getStrTx_DesCor().replace('*', '%'));
                            preSta.setString(4, tbm_deppar.getStrTx_DesCor().replace('*', '%'));
                        } else {
                            preSta.setNull(3, Types.VARCHAR);
                            preSta.setNull(4, Types.VARCHAR);
                        }

                        if (tbm_deppar.getStrTx_DesLar() != null) {
                            preSta.setString(5, tbm_deppar.getStrTx_DesLar().replace('*', '%'));
                            preSta.setString(6, tbm_deppar.getStrTx_DesLar().replace('*', '%'));
                        } else {
                            preSta.setNull(5, Types.VARCHAR);
                            preSta.setNull(6, Types.VARCHAR);
                        }

                        if (tbm_deppar.getStrSt_Reg() != null) {
                            preSta.setString(7, tbm_deppar.getStrSt_Reg().replace('*', '%'));
                            preSta.setString(8, tbm_deppar.getStrSt_Reg().replace('*', '%'));
                        } else {
                            preSta.setNull(7, Types.VARCHAR);
                            preSta.setNull(8, Types.VARCHAR);
                        }

                        //limit 23
                        if (intCanReg > 0) {
                            preSta.setInt(9, intCanReg);
                        } else {
                            preSta.setNull(9, Types.INTEGER);
                        }
                        //offset 24
                        preSta.setInt(10, intRegIni);

                        resSet = preSta.executeQuery();

                        if (resSet.next()) {
                            listmp = new ArrayList<Tbm_dep>();
                            do {
                                Tbm_dep tmp = new Tbm_dep();
                                tmp.setIntCo_Dep(resSet.getInt("co_dep"));
                                tmp.setStrTx_DesCor(resSet.getString("tx_descor"));
                                tmp.setStrTx_DesLar(resSet.getString("tx_deslar"));
                                tmp.setStrSt_Reg(resSet.getString("st_reg"));
                                listmp.add(tmp);
                            } while (resSet.next());
                        }

                        if (listmp != null) {
                            tbm_dep = listmp.get(0);

                            if (tbm_dep.getIntCo_Dep() > 0) {
                                txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
                            } else {
                                txtCodDep.setText("");
                            }

                            txtNomDep.setText(tbm_dep.getStrTx_DesLar());

                            mostrarEstado();
                            setPosicionRelativa("" + (intPagAct + 1) + " / " + (intPagFin + 1));

                            strSql = "select a.*,b.tx_usr, b.tx_nom from tbm_usrAutHorSupExtDep a inner join tbm_usr b on a.co_usr = b.co_usr "
                                    + "where a.co_dep = ? and a.tx_tiphoraut like '" + strTx_TipHorAut + "' "
                                    + "order by ne_jeraut, co_reg";

                            preSta = conn.prepareStatement(strSql);

                            if (Integer.parseInt(txtCodDep.getText()) > 0) {
                                preSta.setInt(1, intPagAct + 1);
                            } else {
                                preSta.setNull(1, Types.INTEGER);
                            }

                            resSet = preSta.executeQuery();

                            if (resSet.next()) {
                                //listmp = new ArrayList<Tbm_dep>();
                                vecDataCon = new Vector();
                                do {

                                    Vector vecReg = new Vector();
                                    vecReg.add(INT_TBL_LINEA, "");
                                    vecReg.add(INT_TBL_CODUSR, resSet.getInt("co_usr"));
                                    vecReg.add(INT_TBL_USR, resSet.getString("tx_usr"));
                                    vecReg.add(INT_TBL_BUTUSR, "..");
                                    vecReg.add(INT_TBL_NOMUSR, resSet.getString("tx_nom"));
                                    if (resSet.getString("ne_jeraut").compareTo("0") == 0) {
                                        vecReg.add(INT_TBL_JERUSR, "");
                                    } else {
                                        vecReg.add(INT_TBL_JERUSR, resSet.getInt("ne_jeraut"));
                                    }

                                    //boolean blnOblSegJer= (Boolean) resSet.getString("st_oblsegjeraut").equals("S")?true:false;
                                    vecReg.add(INT_TBL_CHKOBLSEGJER, (Boolean) resSet.getString("st_oblsegjeraut").equals("S") ? true : false);
                                    vecReg.add(INT_TBL_COREG, resSet.getInt("co_reg"));
//                                  vecReg.add(INT_TBL_AUX_CODUSR, resSet.getInt("co_usr"));
//                                  vecReg.add(INT_TBL_AUX_CODREG, resSet.getInt("co_reg"));
                                    vecDataCon.add(vecReg);
                                } while (resSet.next());
                            }

                            if (vecDataCon != null) {
                                if (vecDataCon.size() > 0) {
                                    objTblMod.setData(vecDataCon);
                                    blnMod = false;
                                }
                            } else {
                                vecDataCon = new Vector();
                                objTblMod.setData(vecDataCon);
                                blnMod = false;
                            }
                        }
                    }

                }

            } catch (Exception ex) {
                objUti.mostrarMsgErr_F1(this, ex);
                ex.printStackTrace();
            } finally {
                try {
                    stmLoc.close();
                } catch (Throwable ignore) {
                }
                try {
                    resSet.close();
                } catch (Throwable ignore) {
                }
                try {
                    preSta.close();
                } catch (Throwable ignore) {
                }
            }
        }

        /**
         * Pagineo de Memoria. Permite recorrer una lista cargada previamente
         * con los datos de la consulta. Minimiza el riesgo de dejar
         * conecciones, statements y cursores abiertos. El espacio ocupado en
         * memoria depende de la cantidad de registros que retorna la consulta.
         */
        private void pagineoMem() {
            tbm_dep = lisTbm_dep.get(intPagAct);
            //sincronizarFraPoj();

            if (tbm_dep.getIntCo_Dep() > 0) {
                txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
            } else {
                txtCodDep.setText("");
            }

            txtNomDep.setText(tbm_dep.getStrTx_DesLar());

            mostrarEstado();
            setPosicionRelativa("" + (intPagAct + 1) + " / " + (intPagFin + 1));
        }

        public void clickInicio() {
            if (intPagAct > 0) {
                if (verificarCamReg()) {
                    intPagAct = 0;
                    pagineoBas();
                }
            }
        }

        public void clickAnterior() {
            if (intPagAct > 0) {
                if (verificarCamReg()) {
                    intPagAct--;
                    pagineoBas();
                }
            }
        }

        public void clickSiguiente() {
            if (intPagAct < intPagFin) {
                if (verificarCamReg()) {
                    intPagAct++;
                    pagineoBas();
                }
            }
        }

        public void clickFin() {
            if (intPagAct < intPagFin) {
                if (verificarCamReg()) {
                    intPagAct = intPagFin;
                    pagineoBas();
                }
            }
        }

        public void clickInsertar() {
            blnActChkMod = true;
            blnEsCon = false;
        }

        public void clickConsultar() {
            txtCodDep.requestFocus();
            blnEsCon = true;
            habilitarCom(true, true, true);
        }

        public void clickModificar() 
        {
            habilitarCom(false, false, false);
            blnActChkMod = true;
        }

        public void clickEliminar() {
        }

        public void clickAnular() {
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
            System.out.println();
        }

        public boolean insertar() {
            return true;
        }

        /*
         * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
         * para mostrar al usuario un mensaje que indique el campo que es invalido y que
         * debe llenar o corregir.
         * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
         */
        private void mostrarMsgInf(String strMsg)
        {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            String strTit;
            strTit = "Mensaje del sistema Zafiro";
            oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

        public boolean consultar() 
        {
            boolean blnOk = false;
            java.sql.Connection conn = null;
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;
            String strSql = "", sqlAux = "";
            Vector vecDataCon = new Vector();;

            try 
            {
                tbm_deppar = (Tbm_dep) tbm_dep.clone();

                conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn != null) {

                    stmLoc = conn.createStatement();

                    if (txtCodDep.getText().compareTo("") == 0) {
                        strSql = "select * from tbm_dep a order by co_dep desc";

                        blnCas = false;
                    } else {
                        lisTbm_dep = null;
                        strSql = "select a.* , b.*, c.tx_usr, c.tx_nom from tbm_dep a "
                                + "inner join tbm_usrAutHorSupExtDep b on a.co_dep = b.co_dep and a.st_reg like 'A' and a.co_dep = " + Integer.valueOf(txtCodDep.getText().trim()) + " "
                                + "and b.tx_tiphoraut like '" + strTx_TipHorAut + "' "
                                + "inner join tbm_usr c on c.co_usr=b.co_usr "
                                + "order by a.co_dep desc, ne_jeraut, co_reg";

                        blnCas = true;
                    }

                    //System.out.println("consultar(): " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);

                    if (blnCas) {
                        //consulta ingresando un departamento
                        if (rstLoc.next()) {

                            lisTbm_dep = new ArrayList<Tbm_dep>();
                            tbm_dep = new Tbm_dep();
                            tbm_dep.setIntCo_Dep(rstLoc.getInt("co_dep"));
                            tbm_dep.setStrTx_DesCor(rstLoc.getString("tx_descor"));
                            tbm_dep.setStrTx_DesLar(rstLoc.getString("tx_deslar"));
                            tbm_dep.setStrSt_Reg(rstLoc.getString("st_reg"));
                            lisTbm_dep.add(tbm_dep);

                            do {

                                Vector vecReg = new Vector();
                                vecReg.add(INT_TBL_LINEA, "");
                                vecReg.add(INT_TBL_CODUSR, rstLoc.getInt("co_usr"));
                                vecReg.add(INT_TBL_USR, rstLoc.getString("tx_usr"));
                                vecReg.add(INT_TBL_BUTUSR, "..");
                                vecReg.add(INT_TBL_NOMUSR, rstLoc.getString("tx_nom"));

                                String strNe_jeraut = rstLoc.getString("ne_jeraut");
                                int intNe_jeraut = 1;
                                if (strNe_jeraut != null) {
                                    intNe_jeraut = rstLoc.getInt("ne_jeraut");
                                }
                                vecReg.add(INT_TBL_JERUSR, intNe_jeraut);

                                String strSt_oblsegjeraut = rstLoc.getString("st_oblsegjeraut");
                                boolean blnOblSegJer = false;
                                if (strSt_oblsegjeraut != null) {
                                    if (strSt_oblsegjeraut.equals("S")) {
                                        blnOblSegJer = true;
                                    }
                                }
                                vecReg.add(INT_TBL_CHKOBLSEGJER, blnOblSegJer);
                                vecReg.add(INT_TBL_COREG, rstLoc.getInt("co_reg"));

                                vecDataCon.add(vecReg);
                            } while (rstLoc.next());

                        }
                    } else {
                        lisTbm_dep = new ArrayList<Tbm_dep>();

                        while (rstLoc.next()) {

                            tbm_dep = new Tbm_dep();
                            tbm_dep.setIntCo_Dep(rstLoc.getInt("co_dep"));
                            tbm_dep.setStrTx_DesCor(rstLoc.getString("tx_descor"));
                            tbm_dep.setStrTx_DesLar(rstLoc.getString("tx_deslar"));
                            tbm_dep.setStrSt_Reg(rstLoc.getString("st_reg"));
                            lisTbm_dep.add(tbm_dep);

                        }
                        while (rstLoc.next());

                        tbm_dep = new Tbm_dep();
                        tbm_dep.setIntCo_Dep(lisTbm_dep.get(0).getIntCo_Dep());
                        strSql = "select a.* , b.*, c.tx_usr, c.tx_nom from tbm_dep a "
                                + "inner join tbm_usrAutHorSupExtDep b on a.co_dep = b.co_dep and a.st_reg like 'A' and a.co_dep = " + tbm_dep.getIntCo_Dep() + " "
                                + "and b.tx_tiphoraut like '" + strTx_TipHorAut + "' "
                                + "inner join tbm_usr c on c.co_usr=b.co_usr "
                                + "order by a.co_dep desc, ne_jeraut, co_reg";

                        System.out.println("consulta realizada2: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);

                        if (rstLoc.next()) {

                            do {

                                Vector vecReg = new Vector();
                                vecReg.add(INT_TBL_LINEA, "");
                                vecReg.add(INT_TBL_CODUSR, rstLoc.getInt("co_usr"));
                                vecReg.add(INT_TBL_USR, rstLoc.getString("tx_usr"));
                                vecReg.add(INT_TBL_BUTUSR, "..");
                                vecReg.add(INT_TBL_NOMUSR, rstLoc.getString("tx_nom"));

                                String strNe_jeraut = rstLoc.getString("ne_jeraut");
                                int intNe_jeraut = 1;
                                if (strNe_jeraut != null) {
                                    intNe_jeraut = rstLoc.getInt("ne_jeraut");
                                }
                                vecReg.add(INT_TBL_JERUSR, intNe_jeraut);

                                String strSt_oblsegjeraut = rstLoc.getString("st_oblsegjeraut");
                                boolean blnOblSegJer = false;
                                if (strSt_oblsegjeraut != null) {
                                    if (strSt_oblsegjeraut.equals("S")) {
                                        blnOblSegJer = true;
                                    }
                                }
                                vecReg.add(INT_TBL_CHKOBLSEGJER, blnOblSegJer);

                                vecReg.add(INT_TBL_COREG, rstLoc.getInt("co_reg"));
                                vecDataCon.add(vecReg);
                            } while (rstLoc.next());
                        }
                    }
                }

                if (lisTbm_dep != null) {
                    if (!blnCas) {
                        intPagAct = intPagFin = lisTbm_dep.size() - 1;
                        setPosicionRelativa("" + (intPagAct + 1) + " / " + (intPagFin + 1));
                        tbm_dep = lisTbm_dep.get(0);

                        if (tbm_dep.getIntCo_Dep() > 0) {
                            txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
                        } else {
                            txtCodDep.setText("");
                        }

                        txtNomDep.setText(tbm_dep.getStrTx_DesLar());
                        mostrarEstado();
                        blnOk = true;
                    } else {

                        if (tbm_dep != null) {
                            intPagAct = intPagFin = lisTbm_dep.size() - 1;
                            setPosicionRelativa("" + (intPagAct + 1) + " / " + (intPagFin + 1));
                            tbm_dep = lisTbm_dep.get(0);

                            if (tbm_dep.getIntCo_Dep() > 0) {
                                txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
                            } else {
                                txtCodDep.setText("");
                            }

                            txtNomDep.setText(tbm_dep.getStrTx_DesLar());
                            mostrarEstado();
                            lisTbm_dep = null;
                        }
                    }
                } else {

                    setPosicionRelativa("1/1");
                    tbm_dep = null;
                    tbm_dep = new Tbm_dep();

                    tbm_dep.setIntCo_Dep(Integer.parseInt(txtCodDep.getText().toString()));
                    tbm_dep.setStrSt_Reg("A");

                    mostrarEstado();
                    vecDataCon = new Vector();
                    objTblMod.setData(vecDataCon);

                    lisTbm_dep = null;
                    blnMod = false;
                }

                if (vecDataCon != null) {
                    if (vecDataCon.size() > 0) {
                        objTblMod.setData(vecDataCon);
                        blnMod = false;
                    }
                } else {
                    vecDataCon = new Vector();
                    objTblMod.setData(vecDataCon);
                    blnMod = false;
                }

                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;

                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblMod.setDataModelChanged(false);
            } catch (Exception ex) {
                objUti.mostrarMsgErr_F1(this, ex);
                ex.printStackTrace();
            }
            return blnOk;
        }

        /**
         * valida campos requeridos antes de insertar o modificar
         *
         * @return true si esta todo bien false falta algun dato
         */
        private boolean validaCampos() {

            if (txtCodDep.getText().equals("")) {
                tabGen.setSelectedIndex(0);
                mostrarMsgInf("El campo << Usuario >> es obligatorio.\nEscoja y vuelva a intentarlo.");
                txtCodDep.requestFocus();
                return false;
            }
            return true;
        }

        //@Override
        public boolean modificar() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                if (validaCampos()) {

                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);

                        if (modificarCab(conn)) {
                            conn.commit();
                            blnRes = true;
                        } else {
                            conn.rollback();
                        }

                        conn.close();
                        conn = null;
                    }
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

        // ordenar elementos del arreglo con el método burbuja 
        public void ordenamBurbuja(int arreglo2[]) 
        {
            // ciclo para controlar número de pasadas 
            for (int pasada = 1; pasada < arreglo2.length; pasada++) 
            {
                // ciclo para controlar número de comparaciones 
                for (int elemento = 0;
                        elemento < arreglo2.length - 1;
                        elemento++) {

                    // comparar elementos uno a uno e intercambiarlos si 
                    // el primer elemento es mayor que el segundo 
                    if (arreglo2[elemento] > arreglo2[elemento + 1]) {
                        intercambiar(arreglo2, elemento, elemento + 1);
                    }

                } // fin del ciclo para controlar las comparaciones 

            } // fin del ciclo para controlar las pasadas 

        } // fin del método ordenamBurbuja 

        // Intercambiar dos elementos de un arreglo 
        public void intercambiar(int arreglo3[], int primero, int segundo) 
        {
            // área temporal de almacenamiento para intercambiar 
            int almacen;
            almacen = arreglo3[primero];
            arreglo3[primero] = arreglo3[segundo];
            arreglo3[segundo] = almacen;
        }

        private boolean modificarCab(java.sql.Connection conn) 
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strSql;

            try 
            {
                if (conn != null) 
                {
                    int intCodRegMax = 0;

                    objTblMod.removeEmptyRows();
                    if (!objTblMod.isAllRowsComplete()) 
                    {
                        mostrarMsgInf("<HTML>El detalle del documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                        tblDat.setRowSelectionInterval(0, 0);
                        tblDat.changeSelection(0, INT_TBL_USR, true, true);
                        tblDat.requestFocus();
                        return false;
                    }
                    stmLoc = conn.createStatement();
                    //Eliminar Registros para insertarlos nuevamente.
                    strSql="";                    
                    strSql = "delete from tbm_usrAutHorSupExtDep where co_dep = " + txtCodDep.getText() /*+ " and tx_TipHorAut like '"+strTx_TipHorAut+"'"*/;
                    for (int intFil = 0; intFil < tblDat.getRowCount() - 1; intFil++) 
                    {
                        tblDat.setValueAt("I", intFil, INT_TBL_LINEA); //Estado Indica que se debe insertar.
                    }
                    stmLoc.executeUpdate(strSql);
                    //System.out.println("DeleteModificar: "+strSql);
                    
                    /*
                    //Verificar si existen registros asignados al Dpto, despues de haber eliminado los registros de acuerdo al tipo de Horas Extras (Suplementarias/Extraordinarias).
                    //La tabla tbm_usrAutHorSupExtDep tiene pk (co_dep, co_Reg) por tanto debe realizarse esta validación.
                    strSql="";
                    strSql = "select max(co_reg) as max from tbm_usrAutHorSupExtDep where co_dep=" + txtCodDep.getText() ;
                    stmLoc.executeQuery(strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    if (rstLoc.next()) 
                    {
                        intCodRegMax = rstLoc.getInt("max");
                        //System.out.println("Registro Maximo:" + intCodRegMax);
                    }
                    */
                    
                    int intJer[] = new int[tblDat.getRowCount() - 1];
                    for (int i = 0; i < tblDat.getRowCount() - 1; i++) 
                    {
                        intJer[i] = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_JERUSR).toString());
                    }

                    ordenamBurbuja(intJer);
                    int intCont = 1;
                    boolean blnSecVal = false;
                    for (int i = 0; i < intJer.length; i++) 
                    {
                        if (intJer[i] <= intCont) 
                        {
                            blnSecVal = true;
                            intCont++;
                        }
                        else 
                        {
                            mostrarMsgInf("JERARQUIA NO TIENE SECUENCIA VALIDA ");
                            return false;
                        }
                    }

                    int intOp = tblDat.getRowCount() - 1;
                    for (int intFil = 0; intFil < tblDat.getRowCount() - 1; intFil++) 
                    {
                        String strSt_oblSegJerAut = "";
                        boolean bln = false;

                        Object obj = tblDat.getValueAt(intFil, INT_TBL_CODUSR);
                        if (obj == null) 
                        {
                            mostrarMsgInf("<HTML>El detalle del documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                            tblDat.setRowSelectionInterval(0, 0);
                            tblDat.changeSelection(0, INT_TBL_USR, true, true);
                            tblDat.requestFocus();
                            return false;
                        }

                        int intCodUsr = Integer.parseInt(tblDat.getValueAt(intFil, INT_TBL_CODUSR).toString());
                        if (tblDat.getValueAt(intFil, INT_TBL_CHKOBLSEGJER) != null && tblDat.getValueAt(intFil, INT_TBL_CHKOBLSEGJER).toString().compareTo("") != 0) {
                            bln = (Boolean) tblDat.getValueAt(intFil, INT_TBL_CHKOBLSEGJER);
                            if (bln) {
                                strSt_oblSegJerAut = "S";
                            } else {
                                strSt_oblSegJerAut = "N";
                            }
                        } else {
                            strSt_oblSegJerAut = "N";
                        }
                        String str = tblDat.getValueAt(intFil, INT_TBL_LINEA).toString();
                        
                        if (tblDat.getValueAt(intFil, INT_TBL_LINEA).toString().compareTo("M") == 0) //Modificación
                        {
                            strSql = "update tbm_usrAutHorSupExtDep set co_usr = " + intCodUsr + ",ne_jeraut=" + Integer.valueOf(tblDat.getValueAt(intFil, INT_TBL_JERUSR).toString()) + ",st_oblsegjeraut='" + strSt_oblSegJerAut + "' "
                                    + "where co_dep = " + txtCodDep.getText() + " and co_reg = " + tblDat.getValueAt(intFil, INT_TBL_COREG).toString() + " "
                                    + "and tx_tiphoraut like '" + strTx_TipHorAut + "'";
                            System.out.println("modificarCab()-Query-Modificación: " + strSql);
                            stmLoc.executeUpdate(strSql);

                        }
                        else if (tblDat.getValueAt(intFil, INT_TBL_LINEA).toString().compareTo("I") == 0)  //Inserción.
                        {
                            intCodRegMax++;                            
                            tblDat.setValueAt(intCodRegMax,intFil, INT_TBL_COREG);

                            strSql = "INSERT INTO tbm_usrAutHorSupExtDep "
                                    + " VALUES(" + txtCodDep.getText() + " , " + tblDat.getValueAt(intFil, INT_TBL_COREG).toString() + " , '" + strTx_TipHorAut + "' , " + intCodUsr + " , "
                                    + Integer.valueOf(tblDat.getValueAt(intFil, INT_TBL_JERUSR).toString()) + " , '" + strSt_oblSegJerAut + "')";
                            //System.out.println("modificarCab()-Query-Inserción: " + strSql);
                            stmLoc.executeUpdate(strSql);
                            tblDat.setValueAt("",intFil, INT_TBL_LINEA);
                        }
                    }
                    //rstLoc.close();
                    stmLoc.close();
                    rstLoc = null;
                    stmLoc = null;
                    blnRes = true;

                }
            } catch (java.sql.SQLException Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        //@Override
        public boolean eliminar() 
        {
            boolean blnRes = false;
            java.sql.Connection conn;
            try {
                if (validaCampos()) {

                    conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if (conn != null) {
                        conn.setAutoCommit(false);

                        if (eliminarCab(conn)) {
                            conn.commit();
                            blnRes = true;
                        } else {
                            conn.rollback();
                        }

                        conn.close();
                        conn = null;
                    }
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

        public boolean eliminarCab(java.sql.Connection conn)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql = "";

            try 
            {
                if (conn != null) {
                    stmLoc = conn.createStatement();

                    String strTx_TipHorAut = "";
                    if (objZafParSis.getCodigoMenu() == 3019) {
                        strTx_TipHorAut = "S";
                    } else if (objZafParSis.getCodigoMenu() == 3027) {
                        strTx_TipHorAut = "E";
                    }

                    strSql = "DELETE FROM tbm_usrAutHorSupExtDep where co_dep=" + txtCodDep.getText() + " and tx_tiphoraut like '" + strTx_TipHorAut + "'";

                    stmLoc.executeUpdate(strSql);

                    stmLoc.close();
                    stmLoc = null;
                    blnRes = true;
                }
            } catch (java.sql.SQLException Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            } catch (Exception Evt) {
                Evt.printStackTrace();
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            return blnRes;
        }

        public boolean anular() {
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

        public boolean cancelar() 
        {
            boolean blnOk = verificarCamReg();

            if (blnOk) {
                tbm_dep = null;
                tbm_dep = new Tbm_dep();

                if (tbm_dep.getIntCo_Dep() > 0) {
                    txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
                } else {
                    txtCodDep.setText("");
                }
                txtNomDep.setText(tbm_dep.getStrTx_DesLar());
                objTblMod.removeAllRows();
                blnMod = false;
            }

            return blnOk;

        }

        @Override
        public boolean beforeClickInsertar() {
            return verificarCamReg();

        }

        @Override
        public boolean beforeClickEliminar() {
            return verificarCamReg();
        }

        @Override
        public boolean beforeClickAnular() {
            return verificarCamReg();

        }

        public boolean beforeInsertar() {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAnular() {
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
            setEstado('w');
            blnMod = false;
            blnActChkMod = false;
            return true;
        }

        public boolean afterConsultar() {
            if (tbm_dep != null) {
                mostrarEstado();
            }
            return true;
        }

        public boolean afterModificar() {
            blnMod = false;
            blnActChkMod = false;
            return true;
        }

        public boolean afterEliminar()
        {
            boolean blnOk = verificarCamReg();
            if (blnOk) {
                if (!blnCas) {
                    blnOk = false;
                    tbm_dep = null;
                    tbm_dep = new Tbm_dep();
                    objTblMod.removeAllRows();
                    blnMod = false;
                    blnOk = true;
                } else {
                    tbm_dep = null;
                    tbm_dep = new Tbm_dep();
                    if (tbm_dep.getIntCo_Dep() > 0) {
                        txtCodDep.setText(String.valueOf(tbm_dep.getIntCo_Dep()));
                        txtNomDep.setText(tbm_dep.getStrTx_DesLar());
                    } else {
                        txtCodDep.setText("");
                        txtNomDep.setText("");
                    }

                    objTblMod.removeAllRows();
                    blnMod = false;
                    setEstado('l');
                    blnOk = true;
                }

            }
            return blnOk;
        }

        public boolean afterAnular() {
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
     * Listener que indica si han habido cambios en el documento
     */
    public class DocLis implements DocumentListener {

        public void changedUpdate(DocumentEvent evt) 
        {
            if (blnActChkMod) {
                blnMod = true;
                blnActChkMod = false;
            }
        }

        public void insertUpdate(DocumentEvent evt) {
            if (blnActChkMod) {
                blnMod = true;
                blnActChkMod = false;
            }
        }

        public void removeUpdate(DocumentEvent evt) 
        {
            if (blnActChkMod) {
                blnMod = true;
                blnActChkMod = false;
            }
        }
    }

    //<editor-fold defaultstate="colllapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBaj;
    public javax.swing.JButton butDep;
    private javax.swing.JButton butSub;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    public javax.swing.JTextField txtCodDep;
    public javax.swing.JTextField txtNomDep;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    //ToolTips
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODUSR:
                    strMsg = "Código del usuario";
                    break;
                case INT_TBL_USR:
                    strMsg = "Usuario";
                    break;
                case INT_TBL_BUTUSR:
                    strMsg = "";
                    break;
                case INT_TBL_NOMUSR:
                    strMsg = "Nombre del usuario";
                    break;
                case INT_TBL_JERUSR:
                    strMsg = "Jerarquía de la autorización";
                    break;
                case INT_TBL_CHKOBLSEGJER:
                    strMsg = "¿Es obligatorio seguir la jerarquía?";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}
