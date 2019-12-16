package RecursosHumanos.ZafRecHum28;


import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Mantenimiento Departamentos por programas y usuarios
 * @author Roberto Flores
 * Guayaquil 31/03/2012
 */
public class ZafRecHum28 extends javax.swing.JInternalFrame {
  
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private ZafUtil objUti; /**/
    private MiToolBar objTooBar;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private ZafMouMotAda objMouMotAda;                                  //ToolTipText en TableHeader.
    private String strVersion=" Ver 1.01 ";

    private String strUsr="";
    private String strNomUsr="";
    private String strCodUsr="";
    private String strCodPrg="";
    private String strNomPrg="";

    private ZafVenCon objVenConProg;
    private ZafVenCon objVenConUsr;

    private final int INT_TBL_LINEA=0;
    private final int INT_TBL_CHKDEP=1;
    private final int INT_TBL_CODDEP=2;
    private final int INT_TBL_DESCOR=3;
    private final int INT_TBL_DESLAR=4;
    private final int INT_TBL_DAT_CHK_PRE = 5;                    //Casilla de verificación:  Tipo de Documento predeterminado.

    private javax.swing.JTextField txtCodUsr= new javax.swing.JTextField();

    private java.sql.Connection CONN_GLO=null;
    private java.sql.Statement  STM_GLO=null;
    private java.sql.ResultSet  rstLoc=null;
    
    private boolean blnActPosRel;
    
    private ZafTblCelEdiChk objTblCelEdiChkPre;         //Editor: JCheckBox en celda.
    
    private boolean blnPre;
    
    private boolean blnMarTodChkTblDep=true;                    //Marcar todas las casillas de verificación del JTable de departamentos.
            
    /** Creates new form ZafMae61 */
    public ZafRecHum28(Librerias.ZafParSis.ZafParSis obj) {
        try{
	    
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            objUti = new ZafUtil(); /**/
            objTooBar = new MiToolBar(this);
            this.getContentPane().add(objTooBar,"South");

            objTooBar.setVisibleAnular(false);
            blnActPosRel = false;
        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }


    /**
    * Carga ventanas de consulta y configuracion de la tabla
    */
    public void Configura_ventana_consulta(){
        configurarVenConProg();
        configurarVenConUsr();

        configurarForm();
    }


    private boolean configurarVenConProg(){
        boolean blnRes=true;
        String strSql="";
        try{

            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mnu");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Programa");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("494");
            //Armar la sentencia SQL.
            strSql="SELECT a1.co_mnu, a1.tx_nom "+
                    " FROM tbm_mnuSis AS a1 "+
                    " WHERE (a1.tx_tipMnu='C'or a1.tx_tipMnu='R') and a1.tx_nom!='' "+
                    " AND a1.st_reg<>'E' AND a1.tx_acc IS NOT NULL "+
                    " ORDER BY a1.tx_nom ";
            objVenConProg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de programas", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            objVenConProg.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            objVenConProg.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConUsr(){
        boolean blnRes=true;
        String strSql="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSql="select a1.co_usr, a1.tx_usr, a1.tx_nom   from tbr_perusr as a " +
                    " inner join tbm_usr as a1 on (a1.co_usr=a.co_usr) " +
                    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
                    " and a.co_mnu=null and a1.st_reg='A'  " +
                    " order by a1.tx_usr ";
            objVenConUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de usuarios", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            objVenConUsr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean configurarForm(){
        blnPre=false;
        boolean blnres=false;
        Vector vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CHKDEP," ");
        vecCab.add(INT_TBL_CODDEP,"Código");
        vecCab.add(INT_TBL_DESCOR,"Dep.");
        vecCab.add(INT_TBL_DESLAR,"Departamento");
        vecCab.add(INT_TBL_DAT_CHK_PRE, "Pred.");
   
        objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);
        
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKDEP).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODDEP).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DESCOR).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DESLAR).setPreferredWidth(150);
        tcmAux.getColumn(INT_TBL_DAT_CHK_PRE).setPreferredWidth(80);
        
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
        
        //Configurar JTable: Establecer los listener para el TableHeader.
        tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_CHKDEP);
        vecAux.add("" + INT_TBL_DAT_CHK_PRE);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        //Configurar JTable: Renderizar celdas.
        objTblCelRenChk = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKDEP).setCellRenderer(objTblCelRenChk);
        tcmAux.getColumn(INT_TBL_DAT_CHK_PRE).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk = null;

        objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
        tcmAux.getColumn(INT_TBL_CHKDEP).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_CHKDEP)){
                    if(! blnPre){
                        objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PRE);
                        blnPre=true;
                    }
                }
                else
                    objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PRE);
            }
        });

        objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
        tcmAux.getColumn(INT_TBL_DAT_CHK_PRE).setCellEditor(objTblCelEdiChkPre);
        objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {

            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_CHKDEP))
                    objTblCelEdiChkPre.setCancelarEdicion(false);
                else
                    objTblCelEdiChkPre.setCancelarEdicion(true);

            }

            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PRE)){
                    blnPre=true;
                    quitarPredeterminadoAnterior(tblDat.getSelectedRow());
                }
                else
                    blnPre=false;

            }
        });

        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

        return blnres;
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_CHKDEP)
            {
                if (blnMarTodChkTblDep)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_CHKDEP);
                    }
                    blnMarTodChkTblDep=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_CHKDEP);
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_PRE);
                    }
                    blnMarTodChkTblDep=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    private boolean quitarPredeterminadoAnterior(int fila){
        boolean blnRes=true;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE)){
                    if(i!=fila)
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_PRE);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite validar la selecciòn obligatoria de un documento
     * predeterminado antes de realizar la respectiva inserción, modificación.
     * @return true: Si existe un documento predeterminado seleccionado
     * <BR>false: En el caso contrario.
     */
    private boolean isPredeterminado() {
        boolean blnRes=false;
        try {
            for (int i=0; i<objTblMod.getRowCountTrue(); i++) {
                if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE)) {
                    blnRes = true;
                    break;
                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }
    /**
     * Esta función permite validar que el documento que se encuentra
     * como predeterminado deba ser seleccionado en relación a un tipo
     * de documento
     * @return true: Si el documento predeterminado 
     * tiene seleccionado un tipo de documento
     * <BR>false: En el caso contrario.
     *
    private boolean seleccionarPreSel() {
        boolean blnRes = true;
        intVar = 0;
        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE)) {
                if (objTblMod.isChecked(i, INT_TBL_DAT_CHK)) {
                    {
                    }
                    intVar++;
                }
            }
        }
        if (intVar == 0) {
            mostrarMsgInf("<HTML>La configuración requiere que el documento Preterminado <BR> Seleccione un tipo de documento, seleccione y  vuelva a intentarlo.</HTML>");
            blnRes = false;
        } else {
            blnRes = true;
        }
        return blnRes;
    }*/

    /**
     * Esta función permite validar la selecciòn obligatoria de un documento 
     * predeterminado antes de realizar la respectiva inserción, modificación.
     * @return true: Si existe un documento predeterminado seleccionado
     * <BR>false: En el caso contrario.
     *
    private boolean seleccionarPre() {
        boolean blnRes = true;
        intVarPre = 0;
        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE)) {
                intVarPre++;
            }
        }
        if (intVarPre == 0) {
            mostrarMsgInf("<HTML>La configuración requiere que el documento Preterminado esté seleccionado.<BR>Seleccione un tipo de documento como predeterminado y vuelva a intentarlo.</HTML>");
            blnRes = false;
        } else {
            blnRes = true;
        }
        return blnRes;
    }*/
    
    
    public void abrirCon(){
        try{
            CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

    public void CerrarCon(){
        try{
            CONN_GLO.close();
            CONN_GLO=null;
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panCabDepPrgUsr = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomPrg = new javax.swing.JTextField();
        txtCodPrg = new javax.swing.JTextField();
        butProg = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtUsr = new javax.swing.JTextField();
        txtNomUsr = new javax.swing.JTextField();
        butUsr = new javax.swing.JButton();
        panDetDepPrgUsr = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panCabDepPrgUsr.setPreferredSize(new java.awt.Dimension(100, 60));
        panCabDepPrgUsr.setLayout(null);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Programa:"); // NOI18N
        panCabDepPrgUsr.add(jLabel3);
        jLabel3.setBounds(10, 10, 110, 20);

        txtNomPrg.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrgActionPerformed(evt);
            }
        });
        txtNomPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusLost(evt);
            }
        });
        panCabDepPrgUsr.add(txtNomPrg);
        txtNomPrg.setBounds(190, 10, 460, 20);

        txtCodPrg.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrgActionPerformed(evt);
            }
        });
        txtCodPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusLost(evt);
            }
        });
        panCabDepPrgUsr.add(txtCodPrg);
        txtCodPrg.setBounds(120, 10, 70, 20);

        butProg.setText(".."); // NOI18N
        butProg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProgActionPerformed(evt);
            }
        });
        panCabDepPrgUsr.add(butProg);
        butProg.setBounds(650, 10, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Usuario:"); // NOI18N
        panCabDepPrgUsr.add(jLabel5);
        jLabel5.setBounds(10, 30, 110, 20);

        txtUsr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsrActionPerformed(evt);
            }
        });
        txtUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsrFocusLost(evt);
            }
        });
        panCabDepPrgUsr.add(txtUsr);
        txtUsr.setBounds(120, 30, 70, 20);

        txtNomUsr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomUsrActionPerformed(evt);
            }
        });
        txtNomUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomUsrFocusLost(evt);
            }
        });
        panCabDepPrgUsr.add(txtNomUsr);
        txtNomUsr.setBounds(190, 30, 460, 20);

        butUsr.setText(".."); // NOI18N
        butUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrActionPerformed(evt);
            }
        });
        panCabDepPrgUsr.add(butUsr);
        butUsr.setBounds(650, 30, 20, 20);

        panGenTabGen.add(panCabDepPrgUsr, java.awt.BorderLayout.NORTH);

        panDetDepPrgUsr.setLayout(new java.awt.BorderLayout());

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

        panDetDepPrgUsr.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGenTabGen.add(panDetDepPrgUsr, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panGenTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrgActionPerformed
        // TODO add your handling code here:
        txtNomPrg.transferFocus();
}//GEN-LAST:event_txtNomPrgActionPerformed

    private void txtNomPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusGained
        // TODO add your handling code here:
        strNomPrg=txtNomPrg.getText();
        txtNomPrg.selectAll();
}//GEN-LAST:event_txtNomPrgFocusGained

    private void txtNomPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusLost
        // TODO add your handling code here:
        if (!txtNomPrg.getText().equalsIgnoreCase(strNomPrg)) {
            if (txtNomPrg.getText().equals("")) {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else
                BuscarProg("a.tx_nom",txtNomPrg.getText(),1);
        }else
            txtNomPrg.setText(strNomPrg);
}//GEN-LAST:event_txtNomPrgFocusLost

    private void txtCodPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrgActionPerformed
        // TODO add your handling code here:
        txtCodPrg.transferFocus();
}//GEN-LAST:event_txtCodPrgActionPerformed

    private void txtCodPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusGained
        // TODO add your handling code here:
        strCodPrg=txtCodPrg.getText();
        txtCodPrg.selectAll();
}//GEN-LAST:event_txtCodPrgFocusGained

    public void BuscarProg(String campo,String strBusqueda,int tipo){
        objVenConProg.setTitle("Listado Programa");
        if(objVenConProg.buscar(campo, strBusqueda )) {
            txtCodPrg.setText(objVenConProg.getValueAt(1));
            txtNomPrg.setText(objVenConProg.getValueAt(2));
            strCodPrg=objVenConProg.getValueAt(1);
            txtCodUsr.setText("");
            txtUsr.setText("");
            txtNomUsr.setText("");
        }else{
            objVenConProg.setCampoBusqueda(tipo);
            objVenConProg.cargarDatos();
            objVenConProg.show();
            if (objVenConProg.getSelectedButton()==objVenConProg.INT_BUT_ACE) {
                txtCodPrg.setText(objVenConProg.getValueAt(1));
                txtNomPrg.setText(objVenConProg.getValueAt(2));
                strCodPrg=objVenConProg.getValueAt(1);
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else{
                txtCodPrg.setText(strCodPrg);
                txtNomPrg.setText(strNomPrg);
            }
        }
    }

    public void BuscarUsr(String campo,String strBusqueda,int tipo){
        if(!txtCodPrg.getText().equals("")){

            String strSql="Select a1.co_usr, a1.tx_usr, a1.tx_nom   from tbr_perusr as a " +
                    " inner join tbm_usr as a1 on (a1.co_usr=a.co_usr) " +
                    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
                    " and a.co_mnu="+txtCodPrg.getText()+" and a1.st_reg='A'  " +
                    " order by a1.tx_usr ";

            objVenConUsr.setTitle("Listado de Usuario");
            objVenConUsr.setSentenciaSQL(strSql);

            if(objVenConUsr.buscar(campo, strBusqueda )) {
                txtCodUsr.setText(objVenConUsr.getValueAt(1));
                txtUsr.setText(objVenConUsr.getValueAt(2));
                txtNomUsr.setText(objVenConUsr.getValueAt(3));
                strCodUsr=objVenConUsr.getValueAt(1);
            }else{

                objVenConUsr.setCampoBusqueda(tipo);
                objVenConUsr.cargarDatos();
                objVenConUsr.show();
                if (objVenConUsr.getSelectedButton()==objVenConUsr.INT_BUT_ACE) {
                    txtCodUsr.setText(objVenConUsr.getValueAt(1));
                    txtUsr.setText(objVenConUsr.getValueAt(2));
                    txtNomUsr.setText(objVenConUsr.getValueAt(3));
                    strCodUsr=objVenConUsr.getValueAt(1);
                }else{
                    txtCodUsr.setText(strCodUsr);
                    txtUsr.setText(strUsr);
                    txtNomUsr.setText(strNomUsr);
                }
            }
        }else{
            this.mostrarMsgInf("TIENE QUE ESCOJER EL PROGRAMA PRIMERO...");
        }
    }

    private void txtCodPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusLost
        // TODO add your handling code here:
        if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) {
            if (txtCodPrg.getText().equals("")) {
                txtCodPrg.setText("");
                txtNomPrg.setText("");
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else
                BuscarProg("a.co_mnu",txtCodPrg.getText(),0);
        }else
            txtCodPrg.setText(strCodPrg);
}//GEN-LAST:event_txtCodPrgFocusLost

    private void butProgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProgActionPerformed
        // TODO add your handling code here:
        objVenConProg.setTitle("Listado de Programa");
        objVenConProg.setCampoBusqueda(1);
        objVenConProg.show();
        if (objVenConProg.getSelectedButton()==objVenConProg.INT_BUT_ACE) {
            txtCodPrg.setText(objVenConProg.getValueAt(1));
            txtNomPrg.setText(objVenConProg.getValueAt(2));
            strCodPrg=objVenConProg.getValueAt(1);
            txtCodUsr.setText("");
            txtUsr.setText("");
            txtNomUsr.setText("");
        }
}//GEN-LAST:event_butProgActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusGained
        // TODO add your handling code here:
        strUsr=txtUsr.getText();
        txtUsr.selectAll();
    }//GEN-LAST:event_txtUsrFocusGained

    private void txtUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrFocusLost
        // TODO add your handling code here:
        if (!txtUsr.getText().equalsIgnoreCase(strUsr)) {
            if (txtUsr.getText().equals("")) {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else
                BuscarUsr("a1.tx_usr",txtUsr.getText(),1);
        }else
            txtUsr.setText(strUsr);
    }//GEN-LAST:event_txtUsrFocusLost

    private void butUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrActionPerformed
        // TODO add your handling code here:
        if(!txtCodPrg.getText().equals("")){
            objVenConUsr.setTitle("Listado de Usuario");

            String strSql="Select a1.co_usr, a1.tx_usr, a1.tx_nom   from tbr_perusr as a " +
                    " inner join tbm_usr as a1 on (a1.co_usr=a.co_usr) " +
                    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" " +
                    " and a.co_mnu="+txtCodPrg.getText()+" and a1.st_reg='A'  " +
                    " order by a1.tx_usr ";

            System.out.println(strSql);
            objVenConUsr.setSentenciaSQL(strSql);
            objVenConUsr.cargarDatos();
            objVenConUsr.setCampoBusqueda(1);
            objVenConUsr.show();
            if (objVenConUsr.getSelectedButton()==objVenConUsr.INT_BUT_ACE) {
                txtCodUsr.setText(objVenConUsr.getValueAt(1));
                txtUsr.setText(objVenConUsr.getValueAt(2));
                txtNomUsr.setText(objVenConUsr.getValueAt(3));
                strCodUsr=objVenConUsr.getValueAt(1);
            }
        }else{
            this.mostrarMsgInf("TIENE QUE ESCOJER EL PROGRAMA PRIMERO...");
        }
    }//GEN-LAST:event_butUsrActionPerformed

    private void txtNomUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusLost
        // TODO add your handling code here:
        if (!txtNomUsr.getText().equalsIgnoreCase(strNomUsr)) {
            if (txtNomUsr.getText().equals("")) {
                txtCodUsr.setText("");
                txtUsr.setText("");
                txtNomUsr.setText("");
            }else{
                BuscarUsr("a1.tx_nom",txtNomUsr.getText(),2);
            }
        }else
            txtNomUsr.setText(strNomUsr);
    }//GEN-LAST:event_txtNomUsrFocusLost

    private void txtNomUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomUsrActionPerformed
        // TODO add your handling code here:
        txtNomUsr.transferFocus();
    }//GEN-LAST:event_txtNomUsrActionPerformed

    private void txtUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrActionPerformed
        // TODO add your handling code here:
        txtUsr.transferFocus();
    }//GEN-LAST:event_txtUsrActionPerformed

    private void txtNomUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomUsrFocusGained
        // TODO add your handling code here:
        strNomUsr=txtNomUsr.getText();
        txtNomUsr.selectAll();
    }//GEN-LAST:event_txtNomUsrFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }
    }
    
    /**
     * Esta funciï¿½n inicializa la tabla.
     * @return true: Si se pudo inicializar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean inicializar()
    {
        boolean blnRes=true;
        txtCodPrg.setText("");
        txtNomPrg.setText("");
        txtCodUsr.setText("");
        txtNomUsr.setText("");
        txtUsr.setText("");
        objTblMod.removeAllRows();
        objTblMod.inicializarEstadoFilas();
        return blnRes;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butProg;
    private javax.swing.JButton butUsr;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCabDepPrgUsr;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDetDepPrgUsr;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodPrg;
    private javax.swing.JTextField txtNomPrg;
    private javax.swing.JTextField txtNomUsr;
    private javax.swing.JTextField txtUsr;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objZafParSis);
            setVisibleInsertar(false);
            setVisibleAnular(false);
        }

        public boolean anular()
        {
            return true;
        }

        public void clickAceptar()
        {
        
        }


    public void clickAnterior() {
    try{
    if(rstLoc != null ) {
        abrirCon();
        if(!rstLoc.isFirst()) {
                rstLoc.previous();
                refrescaDatos(CONN_GLO, rstLoc);
        }
        CerrarCon();

    }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }


    public void clickAnular()
    {

    }

    public void clickCancelar()
    {

    }

    public void clickConsultar(){
        
    }

    public void clickEliminar()
    {
        System.out.println();
    }


    public void clickFin(){
    try{
        if(rstLoc != null ){
        abrirCon();
        if(!rstLoc.isLast()){
                rstLoc.last();
                refrescaDatos(CONN_GLO, rstLoc);
        }
        CerrarCon();
    }}catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
    }

    public void clickImprimir()
    {

    }

    public void clickInicio(){
    try{
    if(rstLoc != null ){
        abrirCon();
        if(!rstLoc.isFirst()) {
                rstLoc.first();
                refrescaDatos(CONN_GLO, rstLoc);
            }
        CerrarCon();
    }}catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
    }

    public void clickInsertar(){
    }

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}


public void clickModificar(){
  try{
      
  setEditable(true);

  java.awt.Color colBack;
  colBack = txtCodPrg.getBackground();

  bloquea(txtCodPrg, colBack, false);
  bloquea(txtNomPrg, colBack, false);
  bloquea(txtUsr, colBack, false);
  bloquea(txtNomUsr, colBack, false);

  butProg.setEnabled(false);
  butUsr.setEnabled(false);

   blnPre=true;
  
  objTblMod.setDataModelChanged(false);

  }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
}



    private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }


public void clickSiguiente(){
 try{
   if(rstLoc != null ){
       abrirCon();
      if(!rstLoc.isLast()) {
               rstLoc.next();
               refrescaDatos(CONN_GLO, rstLoc);
       }
    CerrarCon();
  }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}




        public void clickVisPreliminar()
        {
        }

        public boolean consultar(){

               return _consultar(FilSql());
        }


private boolean _consultar(String strFil){
  boolean blnRes=false;
  String strSql="";
   try{
     
        abrirCon();
        if(CONN_GLO!=null) {
            STM_GLO=CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );

            if(txtCodPrg.getText().compareTo("")==0 && txtCodUsr.getText().compareTo("")==0){
                strSql="select distinct co_mnu, co_usr from tbr_depprgusr";
            
                rstLoc=STM_GLO.executeQuery(strSql);
                if(rstLoc.next()){
                setMenSis("Se encontraron " + rstLoc.getRow() + " registros");
                blnActPosRel = true;
                refrescaDatos(CONN_GLO, rstLoc);
                blnRes=true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                strSql= "select *, CASE WHEN c.co_dep is null THEN false ELSE true END as chk  from( "+
                    "select * from tbm_dep where st_reg like 'A') b " +
                    "left outer join tbr_depPrgUsr c on b.co_dep = c.co_dep  " + strFil + " " +
                    "order by b.co_dep";
            
                rstLoc=STM_GLO.executeQuery(strSql);
                if(rstLoc.next()){
                    blnActPosRel = false;
                    refrescaDatosFil(CONN_GLO, rstLoc);
                    blnRes=true;
                }else{
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    clnTextos();
                }
            }
            
      CerrarCon();
            
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}

 private String FilSql() {
    String sqlFiltro = "";
    //Agregando filtro por Numero de Cotizacion

    if(!txtCodPrg.getText().equals(""))
        sqlFiltro = sqlFiltro + " and co_mnu="+txtCodPrg.getText();

     if(!txtCodUsr.getText().equals(""))
        sqlFiltro = sqlFiltro + " and co_usr="+txtCodUsr.getText();

    return sqlFiltro ;
}

 public void  clnTextos(){
    strCodPrg=""; strNomPrg="";
    strUsr=""; strNomUsr=""; strCodUsr="";

    txtCodPrg.setText("");
    txtNomPrg.setText("");

    txtCodUsr.setText("");
    txtUsr.setText("");
    txtNomUsr.setText("");

    objTblMod.removeAllRows();

 }
 
 private boolean refrescaDatosFil(java.sql.Connection conn, java.sql.ResultSet rstDatRec ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc, rstLoc02;
    String strSql="";
    String strAux="";
    Vector vecData;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
       
        /**********CARGAR DATOS DE CABEZERA ***************/
           strSql="select a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom as usario  "+
              "from  tbr_depprgusr as a  "+
              "inner join tbm_mnusis as a2 on (a2.co_mnu=a.co_mnu)  "+
              "inner join tbm_usr as a3 on (a3.co_usr=a.co_usr) where a.co_mnu= "+txtCodPrg.getText()+"  and a.co_usr="+txtCodUsr.getText()+" " +
              "group by a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom   ";
       
        
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

       
        txtCodPrg.setText( rstLoc02.getString("co_mnu"));
        txtNomPrg.setText( rstLoc02.getString("tx_nom"));

        txtCodUsr.setText( rstLoc02.getString("co_usr"));
        txtUsr.setText( rstLoc02.getString("tx_usr"));
        txtNomUsr.setText( rstLoc02.getString("usario"));

      }
      rstLoc02.close();
      rstLoc02=null;

       /***************************************************/


        /**********CARGAR DATOS DE DETALLE ***************/
       vecData = new Vector();

           strSql="select b.co_dep, b.tx_descor, b.tx_deslar, CASE WHEN c.co_dep is null THEN false ELSE true END as chk , "+
                    "CASE WHEN c.st_reg is null THEN false WHEN(c.st_reg='P') THEN true WHEN(c.st_reg='A') THEN false END as chkpre  "+
                    " from( "+
                    "select * from tbm_dep where st_reg like 'A') b "+
                    "left outer join tbr_depPrgUsr c on b.co_dep = c.co_dep  and co_mnu= "+txtCodPrg.getText()+"  and co_usr="+txtCodUsr.getText()+ " " +
                    "order by b.co_dep";
       

        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){

               do{
                   
                   java.util.Vector vecReg = new java.util.Vector();
                   vecReg.add(INT_TBL_LINEA, "");

                   vecReg.add(INT_TBL_CHKDEP, rstLoc.getBoolean("chk"));
                   
                   vecReg.add(INT_TBL_CODDEP, rstLoc.getInt("co_dep") );
                   vecReg.add(INT_TBL_DESCOR, rstLoc.getString("tx_descor") );
                   vecReg.add(INT_TBL_DESLAR, rstLoc.getString("tx_deslar") );
                   
                   vecReg.add(INT_TBL_DAT_CHK_PRE, rstLoc.getBoolean("chkpre"));
                   
                   vecData.add(vecReg);
               }while(rstLoc.next());
       }
        rstLoc.close();
        rstLoc=null;

        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);

       /***************************************************/
        stmLoc.close();
        stmLoc=null;

        strAux="Activo";
        objTooBar.setEstadoRegistro(strAux);

        int intPosRel=rstDatRec.getRow();
        rstDatRec.last();
        if(blnActPosRel){
            objTooBar.setMenSis("Se encontraron " + rstDatRec.getRow() + " registros");
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatRec.getRow());
        }else{
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + 1);
        }
        
        rstDatRec.absolute(intPosRel);

        blnRes=true;
      }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    return blnRes;
 }
 
 private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatRec ){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc, rstLoc02;
    String strSql="";
    String strAux="";
    Vector vecData;
    try{
      if(conn!=null){
        stmLoc=conn.createStatement();
       
        /**********CARGAR DATOS DE CABEZERA ***************/

           strSql="select a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom as usario  "+
              "from  tbr_depprgusr as a  "+
              "inner join tbm_mnusis as a2 on (a2.co_mnu=a.co_mnu)  "+
              "inner join tbm_usr as a3 on (a3.co_usr=a.co_usr) where a.co_mnu= "+rstDatRec.getInt("co_mnu")+"  and a.co_usr="+rstDatRec.getInt("co_usr")+" " +
              "group by a.co_mnu, a2.tx_nom, a.co_usr, a3.tx_usr, a3.tx_nom   ";
        
       rstLoc02=stmLoc.executeQuery(strSql);
       if(rstLoc02.next()){

       
        txtCodPrg.setText( rstLoc02.getString("co_mnu"));
        txtNomPrg.setText( rstLoc02.getString("tx_nom"));

        txtCodUsr.setText( rstLoc02.getString("co_usr"));
        txtUsr.setText( rstLoc02.getString("tx_usr"));
        txtNomUsr.setText( rstLoc02.getString("usario"));

      }
      rstLoc02.close();
      rstLoc02=null;

       /***************************************************/


        /**********CARGAR DATOS DE DETALLE ***************/
       vecData = new Vector();


           strSql="select b.co_dep, b.tx_descor, b.tx_deslar, CASE WHEN c.co_dep is null THEN false ELSE true END as chk, " +
                   "CASE WHEN c.st_reg is null THEN false WHEN(c.st_reg='P') THEN true WHEN(c.st_reg='A') THEN false END as chkpre  "+
                   "from( "+
                   "select * from tbm_dep where st_reg like 'A') b "+
                   "left outer join tbr_depPrgUsr c on b.co_dep = c.co_dep  and co_mnu= "+rstDatRec.getInt("co_mnu")+"  and co_usr="+rstDatRec.getInt("co_usr")+ " " +
                   "order by b.co_dep";

        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){

               do{
                   
                   java.util.Vector vecReg = new java.util.Vector();
                   vecReg.add(INT_TBL_LINEA, "");

                   vecReg.add(INT_TBL_CHKDEP, rstLoc.getBoolean("chk"));
                   
                   vecReg.add(INT_TBL_CODDEP, rstLoc.getInt("co_dep") );
                   vecReg.add(INT_TBL_DESCOR, rstLoc.getString("tx_descor") );
                   vecReg.add(INT_TBL_DESLAR, rstLoc.getString("tx_deslar") );
                   
                   vecReg.add(INT_TBL_DAT_CHK_PRE, rstLoc.getBoolean("chkpre"));
                   
                   vecData.add(vecReg);
               }while(rstLoc.next());
       }
        rstLoc.close();
        rstLoc=null;

        objTblMod.setData(vecData);
        tblDat .setModel(objTblMod);

       /***************************************************/
        stmLoc.close();
        stmLoc=null;

        strAux="Activo";
        objTooBar.setEstadoRegistro(strAux);

        int intPosRel=rstDatRec.getRow();
        rstDatRec.last();
        if(blnActPosRel){
            objTooBar.setMenSis("Se encontraron " + rstDatRec.getRow() + " registros");
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatRec.getRow());
        }else{
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + 1);
        }
        
        rstDatRec.absolute(intPosRel);

        blnRes=true;
      }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    return blnRes;
 }

/**
 * validad campos requeridos antes de insertar o modificar
 * @return  true si esta todo bien   false   falta algun dato
 */
      private boolean validaCampos(){
          
          if(txtCodPrg.getText().equals("") ){
              tabGen.setSelectedIndex(0);
              mostrarMsgInf("El campo << Programa >> es obligatorio.\nEscoja y vuelva a intentarlo.");
              txtCodPrg.requestFocus();
              return false;
          }

          if(txtCodUsr.getText().equals("") ){
              tabGen.setSelectedIndex(0);
              mostrarMsgInf("El campo << Usuario >> es obligatorio.\nEscoja y vuelva a intentarlo.");
              txtCodUsr.requestFocus();
              return false;
          }
          
          if ( ! isPredeterminado()) {
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Se requiere haber seleccionado un departamento como predeterminado.<BR>Seleccione algún departamento como predeterminado y vuelva a intentarlo.</HTML>");
            return false;
            }
          
           if (objTblMod.getRowCountChecked(INT_TBL_CHKDEP)<=0) {
            tabGen.setSelectedIndex(0);
            mostrarMsgInf("<HTML>No se ha seleccionado ningún departamento.<BR>Seleccione algún tipo de documento y vuelva a intentarlo o cancele el proceso.</HTML>");
            return false;
        }
          
          return true;
      }



public boolean insertar(){
    return true;
}


private boolean getVerificaExit(java.sql.Connection conn){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();

         strSql="SELECT co_mnu FROM tbr_bodlocprgusr WHERE  " +
         " co_emp="+objZafParSis.getCodigoEmpresa()+" and  co_loc="+objZafParSis.getCodigoLocal()+" and  co_mnu="+txtCodPrg.getText()+" " +
         " and co_usr= "+txtCodUsr.getText()+" ";
         rstLoc=stmLoc.executeQuery(strSql);
         if(rstLoc.next()){
             mostrarMsgInf("YA EXISTE DATOS INGRESADOS PARA ESTE USUARIO ");
             blnRes=false;
         }
         rstLoc.close();
         rstLoc=null;
         stmLoc.close();
         stmLoc=null;
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

public boolean eliminar() {
        boolean blnRes = false;
        java.sql.Connection conn;
        try {
            
            if(validaCampos()){

                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if(conn!=null){
                    conn.setAutoCommit(false);
    
                    if(eliminarCab(conn)){
                        conn.commit();
                        blnRes=true;
                    }else conn.rollback();

                    conn.close();
                    conn=null;
                    clnTextos();
                    objTooBar.setEstado('l');
                    
                }
            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;            
    }

    private boolean eliminarCab(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();

                strSql="DELETE FROM tbr_depPrgUsr where co_mnu="+txtCodPrg.getText()+" " +
                       " and co_usr="+txtCodUsr.getText()+"  ";
                stmLoc.executeUpdate(strSql);

                stmLoc.close();
                stmLoc=null;
                
                blnRes=true;
            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }
    
    public boolean modificar(){
        boolean blnRes=false;
        java.sql.Connection conn;
        try{
            if(validaCampos()){

                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if(conn!=null){
                    conn.setAutoCommit(false);
    
                    if(modificarCab(conn)){
                        conn.commit();
                        blnRes=true;
                    }else conn.rollback();

                    conn.close();
                    conn=null;
                }
            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }

    private boolean modificarCab(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        String strCodDep="";
        
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                
                strSql="DELETE FROM tbr_depPrgUsr where co_mnu="+txtCodPrg.getText()+" " +
                       " and co_usr="+txtCodUsr.getText()+"  ";
                stmLoc.executeUpdate(strSql);

                for(int i=0; i<tblDat.getRowCount(); i++){
                    if(tblDat.getValueAt(i, INT_TBL_CHKDEP).toString().equals("true")){

                        strCodDep=tblDat.getValueAt(i, INT_TBL_CODDEP).toString();
                        String strStReg="";
                        if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE))
                                strStReg += ", 'P'";//st_reg
                            else
                                strStReg += ", 'A'";//st_reg
                        
                        strSql="INSERT INTO tbr_depPrgUsr(co_mnu, co_usr, co_dep,st_reg) " +
                                " VALUES("+txtCodPrg.getText()+" , " +txtCodUsr.getText()+" , "+strCodDep +  strStReg +")";
                        
                        stmLoc.executeUpdate(strSql);
                    }
                }

                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }


        public boolean cancelar()
        {
           boolean blnRes=true;

            try {

                if (rstLoc!=null) {
                    rstLoc.close();
                    if (STM_GLO!=null){
                        STM_GLO.close();
                        STM_GLO=null;
                    }
                    rstLoc=null;

                }
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            

            return blnRes;
        }

        public boolean vistaPreliminar()
        {
            return true;
        }

        public boolean aceptar()
        {
            return true;
        }

        public boolean imprimir()
        {
            return true;
        }

        public boolean beforeInsertar(){
            boolean blnRes=true;

            return blnRes;
        }

        public boolean beforeConsultar(){
            boolean blnRes=true;

            return blnRes;
        }

        public boolean beforeModificar()
        {

            return true;
        }

        public boolean beforeEliminar()
        {

            return true;
        }

        public boolean beforeAnular()
        {
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }

        public boolean afterInsertar()
        {
            objTooBar.setEstado('w');
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
            return true;
        }

        public boolean afterEliminar()
        {
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }

        public boolean afterCancelar()
        {
            return true;
        }

    }
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_CHKDEP:
                    strMsg=" ";
                    break;
                case INT_TBL_CODDEP:
                    strMsg="Código del departamento";
                    break;
                case INT_TBL_DESCOR:
                    strMsg="Descripción corta del departamento";
                    break;
                case INT_TBL_DESLAR:
                    strMsg="Descripción larga del departamento";
                    break;
                case INT_TBL_DAT_CHK_PRE:
                    strMsg = "Departamento predeterminado";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


}