/*
 * Created on 13 de agosto de 2008, 10:26
 */

package Ventas.ZafVen26;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author  jayapata
 */
public class ZafVen26 extends javax.swing.JInternalFrame
{
    //Constantes: Jtable
    private static final int INT_TBL_DAT_LIN =0; 
    private static final int INT_TBL_DAT_CODEMP=1;
    private static final int INT_TBL_DAT_CODLOC=2;
    private static final int INT_TBL_DAT_NOMLOC=3;
    private static final int INT_TBL_DAT_CODTIPDOC=4;
    private static final int INT_TBL_DAT_DESCOR_TIPDOC=5;
    private static final int INT_TBL_DAT_DESLAR_TIPDOC=6;
    private static final int INT_TBL_DAT_CODDOC =7;
    private static final int INT_TBL_DAT_NUMDOC =8;
    private static final int INT_TBL_DAT_FECDOC =9;
    private static final int INT_TBL_DAT_CODCLI =10;
    private static final int INT_TBL_DAT_NOMCLI =11;
    private static final int INT_TBL_DAT_USRSOL =12;
    private static final int INT_TBL_DAT_FECAUT =13;
    private static final int INT_TBL_DAT_TXT_OBSAUT =14;
    private static final int INT_TBL_DAT_BUT_OBSAUT =15;
    private static final int INT_TBL_DAT_BUTSOL =16;
   
    //Variables
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
    private ZafTblBus objTblBus;                                 //Editor de búsqueda.
    private ZafTblPopMnu objTblPopMnu;                           //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblOrd objTblOrd;                                 //JTable de ordenamiento.
    private ZafMouMotAda objMouMotAda;                           //ToolTipText en TableHeader.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafSelFec objSelFec;                                //Selector de Fecha.
    
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private boolean blnCon;                                      //true: Continua la ejecución del hilo.
    private String strSQL, strAux;
    private String strVersion=" v0.4 ";
    private Vector vecCab, vecDat, vecReg;
     
     
    /** Creates new form ListadoSolDevVenAut */
    public ZafVen26(ZafParSis obj) 
    {
       try
       {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm()) 
                exitForm();
       }
       catch (CloneNotSupportedException e){    objUti.mostrarMsgErr_F1(this, e);    }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
          
            //Permisos de Usuarios
            objPerUsr=new ZafPerUsr(objParSis);
             
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3351)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3352)) 
            {
                butCer.setVisible(false);
            }
            
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false); 
            
            objSelFec.setTitulo("Fecha de Documento ");
            panFil.add(objSelFec);
            objSelFec.setBounds(6, 4, 490, 75);
            configurarFechaDesde();
            
            //Configurar los JTables.
            configurarTblDat();
            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    private boolean configurarTblDat() 
    {
        boolean blnRes = false;
        try 
        {
             //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CODEMP, "Cod.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC, "Cod.Loc.");
            vecCab.add(INT_TBL_DAT_NOMLOC, "Local.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC, "Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESCOR_TIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLAR_TIPDOC, "Tipo Documento");
            vecCab.add(INT_TBL_DAT_CODDOC, "Cod.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC, "Num.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODCLI, "Cod.Cli.");
            vecCab.add(INT_TBL_DAT_NOMCLI, "Des.Cli");
            vecCab.add(INT_TBL_DAT_USRSOL, "Usr.Sol");
            vecCab.add(INT_TBL_DAT_FECAUT, "Fec.Aut");
            vecCab.add(INT_TBL_DAT_TXT_OBSAUT, "Obs.Aut");
            vecCab.add(INT_TBL_DAT_BUT_OBSAUT, "Obs.Aut.");
            vecCab.add(INT_TBL_DAT_BUTSOL, "...");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DESCOR_TIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESLAR_TIPDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMCLI).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_USRSOL).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_FECAUT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TXT_OBSAUT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUTSOL).setPreferredWidth(20);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd = new ZafTblOrd(tblDat);
            
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi objZafTblEdi = new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);  
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_OBSAUT);
            vecAux.add("" + INT_TBL_DAT_BUTSOL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable:  Botones.
            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUTSOL).setCellRenderer(objTblCelRenBut);
            //objTblCelRenBut = null;
            
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUTSOL:
                            if(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP).toString().equals(""))
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                            break;
                        case INT_TBL_DAT_BUT_OBSAUT:
                            if(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_TXT_OBSAUT).toString().equals(""))
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                            break; 
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUTSOL).setCellEditor(objTblCelEdiButGen);

            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUTSOL:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break; 
                           case INT_TBL_DAT_BUT_OBSAUT:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TXT_OBSAUT).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break; 
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUTSOL:
                                cargarVentanaSolDev(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP).toString(), 
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC).toString(),
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString(), 
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC).toString() );
                                break;
                            case INT_TBL_DAT_BUT_OBSAUT:
                                cargarVentanaObsAut(tblDat.getSelectedRow(), INT_TBL_DAT_TXT_OBSAUT );
                                break;
                        }
                    }
                }
            });

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            tcmAux = null;
            blnRes = true;

        }
        catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
  
     
    //<editor-fold defaultstate="collapsed" desc="/* Botón Solicitud de Devolución */">
    private void cargarVentanaSolDev(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        //(objParSis,  intCodEmp, intCodLoc,  intCodTipDoc , intCodDoc, 2 );
        Ventas.ZafVen11.ZafVen11 obj = new Ventas.ZafVen11.ZafVen11(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* Botón Observacion Autorizacion Solicitud */">
    private void cargarVentanaObsAut( int intFil, int intCol)
    {
        String strObs = (tblDat.getValueAt(intFil, intCol) == null ? "" : tblDat.getValueAt(intFil, intCol).toString());
        Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs obj = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs(JOptionPane.getFrameForComponent(this), true, strObs);
        obj.show();
        if (obj.getAceptar()) 
        {
            tblDat.setValueAt(obj.getObser(), intFil, intCol);
        }
        obj = null;
    }
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
           int intCol=tblDat.columnAtPoint(evt.getPoint());
           String strMsg="";
           switch (intCol)
           {
               case INT_TBL_DAT_LIN:
                   strMsg="";
                   break;
               case INT_TBL_DAT_CODEMP:
                   strMsg="Código Empresa.";
                   break;
               case INT_TBL_DAT_CODLOC:
                   strMsg="Código Local.";
                   break;
               case INT_TBL_DAT_NOMLOC:
                   strMsg="Local";
                   break;
               case INT_TBL_DAT_CODTIPDOC:
                   strMsg="Código tipo de Documento.";
                   break;
               case INT_TBL_DAT_DESCOR_TIPDOC:
                   strMsg="Descripción corta del tipo de documento.";
                   break;
               case INT_TBL_DAT_DESLAR_TIPDOC:
                   strMsg="Descripción larga del tipo de documento.";
                   break;
               case INT_TBL_DAT_CODDOC:
                   strMsg="Código del documento.";
                   break; 
               case INT_TBL_DAT_NUMDOC:
                   strMsg="Número del documento.";
                   break;
               case INT_TBL_DAT_FECDOC:
                   strMsg="Fecha del documento.";
                   break;
               case INT_TBL_DAT_CODCLI:
                   strMsg="Código del cliente.";
                   break; 
               case INT_TBL_DAT_NOMCLI:
                   strMsg="Nombre del Cliente.";
                   break;
               case INT_TBL_DAT_USRSOL:
                   strMsg="Usuario que solicita la devolución.";
                   break;
               case INT_TBL_DAT_FECAUT:
                   strMsg="Fecha de Autorización.";
                   break;
               case INT_TBL_DAT_TXT_OBSAUT:
                   strMsg="Observación de Autorización.";
                   break;
               case INT_TBL_DAT_BUT_OBSAUT:
                   strMsg="Visualizar la Observación de Autorización.";
                   break;
               case INT_TBL_DAT_BUTSOL:
                   strMsg="Visualizar la Solicitud de devolución.";
                   break;
               default:
                   strMsg="";
                   break;
           }
           tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /* Configurar Fecha Desde 1 mes atras. */
    private void configurarFechaDesde()
    {
        ZafDatePicker txtFecDoc;
        txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
        txtFecDoc.setPreferredSize(new Dimension(70, 20));
        txtFecDoc.setHoy();
        Calendar objFec = Calendar.getInstance();
        ZafDatePicker dtePckPag =  new ZafDatePicker(new JFrame(),"d/m/y");
        int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
        if (fecDoc!=null)
        {
            objFec.set(Calendar.DAY_OF_MONTH, fecDoc[0]);
            objFec.set(Calendar.MONTH, fecDoc[1] - 1);
            objFec.set(Calendar.YEAR, fecDoc[2]);
        }
        Calendar objFecPagActual = Calendar.getInstance();
        objFecPagActual.setTime(objFec.getTime());
        objFecPagActual.add(Calendar.DATE, -30 );

        dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH)+1);
        dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
        String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
        java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
        objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(10, 475));
        panFil.setLayout(null);
        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
    
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
         exitForm(null);
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon = true;
            if (objThrGUI == null)
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else
        {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed
    
    /* Cerrar la aplicación */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) 
    {                          
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }   
    
    private void exitForm() 
    {
        dispose();
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando
     * un JProgressBar con lo cual el usuario estaría informado en todo momento
     * de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        @Override
        public void run()
        {
            if (!cargarDetReg(sqlConFil()))
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    private String sqlConFil() 
    {
        String sqlFil="";
         
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Fecha del documento. */">
        if(objSelFec.isCheckBoxChecked() )
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil+=" AND a.fe_doc <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil+=" AND a.fe_doc >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }
        //</editor-fold>
      
        System.out.println("Filtro: "+sqlFil);
        return sqlFil;
    }
    
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();      
                
                //Armar la sentencia SQL.
                strSQL =" SELECT x.* FROM ( \n";
                strSQL+="    SELECT a2.tx_nom as tx_nomLoc, a.co_emp, a.co_loc, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.ne_numdoc,  \n";
                strSQL+="           a.fe_doc ,a.co_cli, a.tx_nomcli, a.tx_nomusrsol, a.fe_aut, a.tx_obsAut \n";
                strSQL+="           , ( \n";
                strSQL+="                CASE WHEN a.st_tipDev IN ('C') THEN  a.st_recMerDev = 'S' \n";
                strSQL+="                AND CASE WHEN a.st_revTec IN ('S') THEN a.st_revTecMerDev='S' ELSE  1=1 END \n";
                strSQL+="                AND a.st_revBodMerDev = 'S'  AND a.st_merAceIngSis = 'S' \n";
                strSQL+="                AND CASE WHEN a.st_exiMerRec IN ('S') THEN a.st_merRecDevCli='S' ELSE  1=1 END  \n";
                strSQL+="                ELSE  a.st_merAceIngSis = 'S' END  \n";
                strSQL+="             ) as estado \n";
                strSQL+="    FROM tbm_cabsoldevven AS a \n";
                strSQL+="    INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc) \n"; 
                strSQL+="    INNER JOIN tbm_loc as a2 ON (a2.co_Emp=a.co_emp AND a2.co_loc=a.co_loc) \n"; 
                strSQL+="    WHERE a.st_reg='A' AND a.st_aut='A' /*AND a.st_tipdev='C'*/  \n";
                strSQL+="    AND a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+"\n";
                strSQL+="    "+strFil+"\n";
                strSQL+=" ) as x \n"; 
                strSQL+=" WHERE  x.estado=false \n";
                strSQL+=" ORDER BY fe_doc, ne_numdoc \n";
                
                System.out.println("cargarDetReg: "+strSQL);   
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CODEMP, rst.getString("co_emp") );
                        vecReg.add(INT_TBL_DAT_CODLOC, rst.getString("co_loc") );
                        vecReg.add(INT_TBL_DAT_NOMLOC, rst.getString("tx_nomLoc") );
                        vecReg.add(INT_TBL_DAT_CODTIPDOC, rst.getString("co_tipdoc") );
                        vecReg.add(INT_TBL_DAT_DESCOR_TIPDOC, rst.getString("tx_descor") );
                        vecReg.add(INT_TBL_DAT_DESLAR_TIPDOC, rst.getString("tx_deslar") );
                        vecReg.add(INT_TBL_DAT_CODDOC, rst.getString("co_doc") );
                        vecReg.add(INT_TBL_DAT_NUMDOC, rst.getString("ne_numdoc") );
                        vecReg.add(INT_TBL_DAT_FECDOC, rst.getString("fe_doc") );
                        vecReg.add(INT_TBL_DAT_CODCLI, rst.getString("co_cli") );
                        vecReg.add(INT_TBL_DAT_NOMCLI, rst.getString("tx_nomcli") );
                        vecReg.add(INT_TBL_DAT_USRSOL, rst.getString("tx_nomusrsol") );
                        vecReg.add(INT_TBL_DAT_FECAUT, rst.getString("fe_aut") );
                        vecReg.add(INT_TBL_DAT_TXT_OBSAUT, rst.getString("tx_obsAut") );
                        vecReg.add(INT_TBL_DAT_BUT_OBSAUT,"...");
                        vecReg.add(INT_TBL_DAT_BUTSOL,"...");
                        vecDat.add(vecReg);
                    }
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
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
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
