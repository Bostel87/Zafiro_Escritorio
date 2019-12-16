package RecursosHumanos.ZafRecHum20;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * Pantalla de Empleados por horario de trabajo
 * @author Roberto Flores
 * Guayaquil 07/11/2011
 */
public class ZafRecHum20 extends javax.swing.JInternalFrame {

    private final String strVer = " v 1.3 ";                   //Versión de clase
    private ZafRecHum20Bean zafRecHum20Bean;                   //Bean manejador de éste JInternalFrame
    private String[] strTblRepHdrTooTip;                       //Tooltips de la cabecera de las columnas del reporte
    public ZafTblMod zafTblModRep;                             //Table Model para la Tabla de Reportes
    private ZafTblCelEdiTxt zafTblCelEdiTxt;                   //Editor de Textos
    private ZafTblCelRenChk zafTblCelRenChk;                   //Renderer de Check Box para las empresas
    private ZafTblCelEdiChk zafTblCelEdiChk;                   //Editor de Check Box para las empresas

    public static final int INT_REP_LINEA = 0;                 //Índice de columna "Línea" de la Tabla de Reportes
    public static final int INT_REP_CODTRA = 1;                //Índice de columna "Cod.Trabajador" de la Tabla de Reportes
    public static final int INT_REP_APETRA = 2;                //Indice de columna "Apellido Trabajador" de la Tabla de Reportes
    public static final int INT_REP_NOMTRA = 3;                //Indice de columna "Nombre Trabajador" de la Tabla de Reportes
    public static final int INT_REP_CHKHORFIJ = 4;             //Indice de columna "Horario Fijo" de la Tabla de Reportes

    public boolean blnMod;                                     //Indica si han habido cambios en el documento
    private boolean blnConsDat;
    
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;

    /** Creates new form ZafRecHum20 */
    public ZafRecHum20(ZafParSis obj) {
        
        try{
        zafRecHum20Bean = new ZafRecHum20Bean(this, obj);
        strTblRepHdrTooTip = new String[]{null,"Código del Empleado","Nombres del Empleado","Apellidos del Empleado","¿Tiene horario fijo?"};
        initComponents();
        setTitle(zafRecHum20Bean.getZafParSis().getNombreMenu()+strVer);
        initTblRep();
        tblRep.getModel().addTableModelListener(new MyTableModelListener(tblRep));
        tblRep.addMouseListener(new MouseAdapter()
        {
            TableColumnModel tcmAux = tblRep.getColumnModel();
            boolean blnV = false;
            public void mouseClicked(MouseEvent e)
            {
                int fila = tblRep.rowAtPoint(e.getPoint());
                int columna = tblRep.columnAtPoint(e.getPoint());
                int columnaMax = tblRep.getColumnCount();
                ZafTblMod objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblRep.getModel();

                for(int j=INT_REP_CHKHORFIJ+1;j<objTblMod.getColumnCount();j++){

                    if(objTblMod.getValueAt(fila, j).equals(false)){
                        tblRep.setValueAt(false, fila, ZafRecHum20.INT_REP_CHKHORFIJ);
                    }else{
                        tblRep.setValueAt(true, fila, ZafRecHum20.INT_REP_CHKHORFIJ);
                        j=objTblMod.getColumnCount();
                    }
                }
                
                /*if(blnV){
                    if(objTblMod.getValueAt(fila, 4).equals(false)){
                        for(int i = 4; i < (columnaMax); i++){
                            objTblMod.setValueAt(Boolean.valueOf(false), fila, i);

                        }
                        blnV = false;
                    }
                }else{
                    if(objTblMod.getValueAt(fila, columna).equals(true)){
                        objTblMod.setValueAt(Boolean.valueOf(true), fila, 4);
                        blnV = false;
                    }else{
                        objTblMod.setValueAt(Boolean.valueOf(false), fila, 4);
                        blnV = false;
                    }
                }*/
                
                //************************************************************
                
                /*int intNumRow = zafTblModRep.getRowCount();
                int intNumCol = zafTblModRep.getColumnCount();
                int intNumColDin = intNumCol - (ZafRecHum20.INT_REP_APETRA+1);

                for(int j=0;j<intNumColDin;j++){

                    if(columna!=intNumColDin+j){
                        tblRep.setValueAt(false, fila, ZafRecHum20.INT_REP_CHKHORFIJ+j);
                    }
                }*/

                //************************************************************
            }
        });
        
        zafRecHum20Bean.habilitarCom(false, false, false, false, false, false, false);
        blnMod = false;
        blnConsDat = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optCri = new javax.swing.JRadioButton();
        lblEmp = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        pnlNomDesHas = new javax.swing.JPanel();
        lblNomDes = new javax.swing.JLabel();
        txtNomDes = new javax.swing.JTextField();
        lblNomHas = new javax.swing.JLabel();
        txtNomHas = new javax.swing.JTextField();
        pnlApeDesHas = new javax.swing.JPanel();
        lblApeDes = new javax.swing.JLabel();
        txtApeDes = new javax.swing.JTextField();
        lblApeHas = new javax.swing.JLabel();
        txtApeHas = new javax.swing.JTextField();
        panRep = new javax.swing.JPanel();
        scrRep = new javax.swing.JScrollPane();
        tblRep = new JTable(){
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return strTblRepHdrTooTip[realIndex];
                    }
                };
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Empleados por horario de trabajo");
        getContentPane().add(lblTit, java.awt.BorderLayout.PAGE_START);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 40, 180, 20);

        bgrFil.add(optCri);
        optCri.setText("Solo los empleados que cumplan el criterio seleccionado");
        optCri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optCriItemStateChanged(evt);
            }
        });
        panFil.add(optCri);
        optCri.setBounds(10, 60, 380, 20);

        lblEmp.setText("Empleado:");
        panFil.add(lblEmp);
        lblEmp.setBounds(40, 100, 70, 20);

        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(110, 100, 70, 20);
        panFil.add(txtNomTra);
        txtNomTra.setBounds(180, 100, 210, 20);

        butTra.setText("...");
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(390, 100, 20, 20);

        pnlNomDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));
        pnlNomDesHas.setLayout(null);

        lblNomDes.setText("Desde:");
        pnlNomDesHas.add(lblNomDes);
        lblNomDes.setBounds(10, 30, 50, 20);

        txtNomDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDesActionPerformed(evt);
            }
        });
        txtNomDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDesFocusLost(evt);
            }
        });
        pnlNomDesHas.add(txtNomDes);
        txtNomDes.setBounds(60, 30, 80, 20);

        lblNomHas.setText("Hasta:");
        pnlNomDesHas.add(lblNomHas);
        lblNomHas.setBounds(150, 30, 50, 20);

        txtNomHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomHasActionPerformed(evt);
            }
        });
        pnlNomDesHas.add(txtNomHas);
        txtNomHas.setBounds(200, 30, 80, 20);

        panFil.add(pnlNomDesHas);
        pnlNomDesHas.setBounds(30, 140, 300, 70);

        pnlApeDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Apellido"));
        pnlApeDesHas.setLayout(null);

        lblApeDes.setText("Desde:");
        pnlApeDesHas.add(lblApeDes);
        lblApeDes.setBounds(10, 30, 50, 20);

        txtApeDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApeDesActionPerformed(evt);
            }
        });
        txtApeDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtApeDesFocusLost(evt);
            }
        });
        pnlApeDesHas.add(txtApeDes);
        txtApeDes.setBounds(60, 30, 80, 20);

        lblApeHas.setText("Hasta:");
        pnlApeDesHas.add(lblApeHas);
        lblApeHas.setBounds(150, 30, 50, 20);

        txtApeHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApeHasActionPerformed(evt);
            }
        });
        pnlApeDesHas.add(txtApeHas);
        txtApeHas.setBounds(200, 30, 80, 20);

        panFil.add(pnlApeDesHas);
        pnlApeDesHas.setBounds(350, 140, 300, 70);

        tabGen.addTab("Filtro", panFil);

        panRep.setLayout(new java.awt.BorderLayout());

        tblRep.setModel(new javax.swing.table.DefaultTableModel(
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
        tblRep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRepMouseClicked(evt);
            }
        });
        scrRep.setViewportView(tblRep);

        panRep.add(scrRep, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Reporte", panRep);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

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

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.PAGE_END);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void initTblRep(){
        //Títulos de Cabecera
        Vector vecCabRep=new Vector();
        vecCabRep.clear();
        vecCabRep.add(INT_REP_LINEA, "");
        vecCabRep.add(INT_REP_CODTRA, "Codigo");
        vecCabRep.add(INT_REP_APETRA, "Apellidos");
        vecCabRep.add(INT_REP_NOMTRA, "Nombres");
        vecCabRep.add(INT_REP_CHKHORFIJ, "Horario Fijo");

        List<Tbm_hortra> lisTbm_hortra = zafRecHum20Bean.consultarLisGenCabHorTra();

        final int intColFij = 5;
        strTblRepHdrTooTip = new String[intColFij+lisTbm_hortra.size()];
        strTblRepHdrTooTip[0] = null;
        strTblRepHdrTooTip[1] = "Código del Empleado";
        strTblRepHdrTooTip[2] = "Apellidos del Empleado";
        strTblRepHdrTooTip[3] = "Nombres del Empleado";
        strTblRepHdrTooTip[4] = "¿Tiene horario fijo?";

        int cont = 5;
        if(lisTbm_hortra!=null){
            for(Tbm_hortra tmp:lisTbm_hortra){
                if(tmp.getStrTx_nom().length()>=10){
                    strTblRepHdrTooTip[cont]=tmp.getStrTx_nom();
                }else{
                    strTblRepHdrTooTip[cont]=null;
                }
                vecCabRep.add(vecCabRep.size(), tmp.getStrTx_nom());
                cont++;
            }
        }

        zafTblModRep = new ZafTblMod();
        zafTblModRep.setHeader(vecCabRep);
        zafTblModRep.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        tblRep.setModel(zafTblModRep);
        tblRep.setRowSelectionAllowed(true);
        tblRep.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        new ZafColNumerada(tblRep,INT_REP_LINEA);

        tblRep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Columnas editables
        Vector vecColEdi = new Vector();
        //vecColEdi.add("" + INT_REP_CHKHORFIJ);

        //Ancho de columnas
        TableColumnModel tcmAux = tblRep.getColumnModel();
        tcmAux.getColumn(INT_REP_LINEA).setPreferredWidth(40);
        tcmAux.getColumn(INT_REP_CODTRA).setPreferredWidth(40);
        tcmAux.getColumn(INT_REP_APETRA).setPreferredWidth(170);
        tcmAux.getColumn(INT_REP_NOMTRA).setPreferredWidth(170);
        tcmAux.getColumn(INT_REP_CHKHORFIJ).setPreferredWidth(60);

        zafTblCelRenChk = new ZafTblCelRenChk();
        zafTblCelEdiChk = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_REP_CHKHORFIJ).setCellRenderer(zafTblCelRenChk);
        tcmAux.getColumn(INT_REP_CHKHORFIJ).setCellEditor(zafTblCelEdiChk);
        if(lisTbm_hortra!=null){
            for(int i=1;i<=lisTbm_hortra.size();i++){
                

                //tcmAux.getColumn(INT_REP_APETRA+i).setPreferredWidth(100);
                tcmAux.getColumn(INT_REP_CHKHORFIJ+i).setCellRenderer(zafTblCelRenChk);
                tcmAux.getColumn(INT_REP_CHKHORFIJ+i).setCellEditor(zafTblCelEdiChk);
                zafTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    
                    int intMaxCol=tblRep.getColumnCount();
                    
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     int intCol = tblRep.getSelectedColumn();//columna
                    int intFil = tblRep.getSelectedRow();//fila 
                    int columnaMax = tblRep.getColumnCount();
                ZafTblMod objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblRep.getModel();

                for(int j=INT_REP_CHKHORFIJ+1;j<objTblMod.getColumnCount();j++){

                    if(objTblMod.getValueAt(intFil, j).equals(false)){
                        tblRep.setValueAt(false, intFil, ZafRecHum20.INT_REP_CHKHORFIJ);
                    }else{
                        tblRep.setValueAt(true, intFil, ZafRecHum20.INT_REP_CHKHORFIJ);
                        j=objTblMod.getColumnCount();
                    }
                }   
                }     
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    int intCol = tblRep.getSelectedColumn();//columna
                    int intFil = tblRep.getSelectedRow();//fila
                    for(int x=intColFij; x < intMaxCol; x++){
                        
                        if(x!=intCol){
                           
                            tblRep.setValueAt( new Boolean(false), intFil , x);
                        }
                    }
                    int columnaMax = tblRep.getColumnCount();
                ZafTblMod objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblRep.getModel();

                for(int j=INT_REP_CHKHORFIJ+1;j<objTblMod.getColumnCount();j++){

                    if(objTblMod.getValueAt(intFil, j).equals(false)){
                        tblRep.setValueAt(false, intFil, ZafRecHum20.INT_REP_CHKHORFIJ);
                    }else{
                        tblRep.setValueAt(true, intFil, ZafRecHum20.INT_REP_CHKHORFIJ);
                        j=objTblMod.getColumnCount();
                    }
                }   
                }
               });

                vecColEdi.add("" + (INT_REP_CHKHORFIJ+i));
                tcmAux.getColumn(INT_REP_CHKHORFIJ+i).setPreferredWidth(60);
            }
        }

        //Acciones para el manejo de Tablas
        new ZafTblEdi(tblRep);

        zafTblCelEdiTxt = new ZafTblCelEdiTxt(tblRep);
        zafTblCelEdiTxt.getDocument().addDocumentListener(zafRecHum20Bean.getDocLis());
        tcmAux.getColumn(INT_REP_CODTRA).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_REP_APETRA).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_REP_NOMTRA).setCellEditor(zafTblCelEdiTxt);
        zafTblCelEdiTxt = null;
        zafTblModRep.setColumnasEditables(vecColEdi);
        vecColEdi = null;

        ZafTblPopMnu ZafTblPopMn = new ZafTblPopMnu(tblRep);
        ZafTblPopMn.setEliminarFilaVisible(true);
        
        //Configurar JTable: Establecer el ordenamiento en la tabla.
                objTblOrd=new ZafTblOrd(tblRep);
        
        //Configurar JTable: Editor de búsqueda.
        objTblBus=new ZafTblBus(tblRep);
        
        tcmAux = null;

    }

    public class MyTableModelListener implements TableModelListener {
    JTable table;

    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    MyTableModelListener(JTable table) {
        this.table = table;
    }

    public void tableChanged(TableModelEvent e) {

        int firstRow = e.getFirstRow();
        int lastRow = e.getLastRow();
        int mColIndex = e.getColumn();

        if(!blnConsDat){
            switch (e.getType()) {

              case TableModelEvent.UPDATE:
                  blnMod = true;
                if (firstRow == TableModelEvent.HEADER_ROW) {
                    if (mColIndex == TableModelEvent.ALL_COLUMNS) {
                        // A column was added
                    } else {
                        // Column mColIndex in header changed
                    }
                }
                break;
            }
        }
        blnConsDat = false;
    }
 }

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        blnConsDat = true;
        butGua.setEnabled(true);
        zafRecHum20Bean.consultarRep();
}//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        zafRecHum20Bean.grabarTraEmp();
}//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed

        if(blnMod){
            String strMsg = "Existen cambios sin guardar! \nEstá Seguro que desea cerrar este programa?";
            String strTit = "Mensaje del sistema Zafiro";
            boolean blnOk = (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
            if(blnOk){
                dispose();
            }
        }else{
             String strTit, strMsg;
             strTit="Mensaje del sistema Zafiro";
             strMsg="¿Está seguro que desea cerrar este programa?";
             if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
             {
                 dispose();
             }
         }
}//GEN-LAST:event_butCerActionPerformed

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        zafRecHum20Bean.mostrarVenTra();
    }//GEN-LAST:event_butTraActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        blnConsDat = true;
        zafRecHum20Bean.consultarRep();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        zafRecHum20Bean.habilitarCom(false, false, false, false, false, false, false);
        txtCodTra.setText("");
        txtNomTra.setText("");
        txtNomDes.setText("");
        txtNomHas.setText("");
        txtApeDes.setText("");
        txtApeHas.setText("");
    }//GEN-LAST:event_optTodItemStateChanged

    private void optCriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optCriItemStateChanged
        zafRecHum20Bean.habilitarCom(true, false, true, true, true, true, true);
        txtCodTra.requestFocus();
    }//GEN-LAST:event_optCriItemStateChanged

    private void txtNomDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDesActionPerformed
        zafRecHum20Bean.consultarRep();
    }//GEN-LAST:event_txtNomDesActionPerformed

    private void txtNomHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomHasActionPerformed
        zafRecHum20Bean.consultarRep();
    }//GEN-LAST:event_txtNomHasActionPerformed

    private void txtApeDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApeDesActionPerformed
        zafRecHum20Bean.consultarRep();
    }//GEN-LAST:event_txtApeDesActionPerformed

    private void txtApeHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApeHasActionPerformed
        zafRecHum20Bean.consultarRep();
    }//GEN-LAST:event_txtApeHasActionPerformed

    private void txtNomDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusLost
        txtNomHas.setText(txtNomDes.getText());
        txtNomHas.selectAll();
    }//GEN-LAST:event_txtNomDesFocusLost

    private void txtApeDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApeDesFocusLost
        txtApeHas.setText(txtApeDes.getText());
        txtApeHas.selectAll();
    }//GEN-LAST:event_txtApeDesFocusLost

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void tblRepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRepMouseClicked

    }//GEN-LAST:event_tblRepMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    public javax.swing.JButton butGua;
    public javax.swing.JButton butTra;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblApeDes;
    private javax.swing.JLabel lblApeHas;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomDes;
    private javax.swing.JLabel lblNomHas;
    private javax.swing.JLabel lblTit;
    public javax.swing.JRadioButton optCri;
    public javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JPanel pnlApeDesHas;
    private javax.swing.JPanel pnlNomDesHas;
    private javax.swing.JScrollPane scrRep;
    public javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblRep;
    public javax.swing.JTextField txtApeDes;
    public javax.swing.JTextField txtApeHas;
    public javax.swing.JTextField txtCodTra;
    public javax.swing.JTextField txtNomDes;
    public javax.swing.JTextField txtNomHas;
    public javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

}