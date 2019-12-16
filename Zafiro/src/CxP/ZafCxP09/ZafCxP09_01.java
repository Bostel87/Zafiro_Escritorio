/*
 * ZafCxP09_01.java
 *
 * Created on Nov 12, 2010, 9:30:32 AM
 */
package CxP.ZafCxP09;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Vector;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author jayapata
 */
public class ZafCxP09_01 extends javax.swing.JDialog
{
    //Constantes
    private static final int INT_TBL_LIN  = 0;     // NUMERO DE LINEA
    private static final int INT_TBL_CHK  = 1;     // SELECCION DE LA FACTURA
    private static final int INT_TBL_COEMP= 2;     // CODIGO DE LA EMPRESA
    private static final int INT_TBL_COLOC= 3;     // CODIGO DEL LOCAL
    private static final int INT_TBL_CODTIP=4;     // CODIGO DEL TIPO DOCUMENTO
    private static final int INT_TBL_CODDOC=5;     // CODIGO DEL DOCUMENTO
    private static final int INT_TBL_DCTDOC=6;     // DESCRIPCION DEL TIPO DE DOCUMENTO CORTA
    private static final int INT_TBL_DLTDOC=7;     // DESCRIPCION DEL TIPO DE DOCUMENTO LARGA
    private static final int INT_TBL_NUMDOC=8;     // NOMERO DEL DOCUMENTO
    private static final int INT_TBL_FECDOC=9;     // FECHA DEL DOCUMENTO
    private static final int INT_TBL_VALDOC=10;    // VALOR DEL PAGO
    private static final int INT_TBL_VALPEN=11;    // VALOR PENDIENTE
    private static final int INT_TBL_VALAPL=12;    // VALOR APLICADO
    private static final int INT_TBL_VALASI=13;
    private static final int INT_TBL_ESTVALASI=14;    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                    //Editor: Editor del JTable.
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblBus objTblBus;                     //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                     //JTable de ordenamiento.    
    private ZafMouMotAda objMouMotAda;
    private ZafThreadGUI objThrGUI;
    private Vector vecCab, vecReg, vecDat, vecAux;
   
    boolean blnEst=false;
    boolean blnAcepta=false;
    
    BigDecimal bgdMonChq= BigDecimal.ZERO;
    BigDecimal bgdMinAjuCenAut= BigDecimal.ZERO;
    BigDecimal bgdMaxAjuCenAut= BigDecimal.ZERO;

    private String strSQL="", strAux="";
    private String Str_RegSel[];
    private String strFacSel="", strNumDocSel="";

    /** Creates new form ZafCxP09_01 */
    public ZafCxP09_01(java.awt.Frame parent, boolean modal, ZafParSis obj, String strSql, BigDecimal bgdMonChqTbl, BigDecimal bgdMinAjuCenAutRec, BigDecimal bgdMaxAjuCenAutRec)
    {
        super(parent, modal);
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();   
            strSQL=strSql;
            bgdMonChq=bgdMonChqTbl;
            bgdMinAjuCenAut=bgdMinAjuCenAutRec;
            bgdMaxAjuCenAut=bgdMaxAjuCenAutRec;            

            initComponents();
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }    

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti = new ZafUtil(); 
            
            //Configurar JTable
            configurarTblDat();
            
            Str_RegSel = new String[3];
        } 
        catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  

    /**
     * Configura la tabla donde se presenta la información de las facturas pendientes de pago.
     * @return true: Si todo está bien
     * <BR> false: Si hay algún error
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();  //Almacena las cabeceras
            vecDat=new Vector();
            vecCab.clear();            
            vecCab.add(INT_TBL_LIN,   "");
            vecCab.add(INT_TBL_CHK,   " ");
            vecCab.add(INT_TBL_COEMP, "Cód.Emp");
            vecCab.add(INT_TBL_COLOC, "Cód.Loc");
            vecCab.add(INT_TBL_CODTIP,"Cód.TipDoc");
            vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
            vecCab.add(INT_TBL_DCTDOC,"Des.Cor");
            vecCab.add(INT_TBL_DLTDOC,"Des.Lar");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL_VALDOC,"Val.Doc");
            vecCab.add(INT_TBL_VALPEN,"Val.Pen");
            vecCab.add(INT_TBL_VALAPL,"Val.Apl");
            vecCab.add(INT_TBL_VALASI,"Val.Asi");
            vecCab.add(INT_TBL_ESTVALASI,"Est.Val.Asi");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer la fila de cabecera.
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);            

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);

            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHK).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DCTDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DLTDOC).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VALAPL).setPreferredWidth(60);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COEMP, tblDat);             
            objTblMod.addSystemHiddenColumn(INT_TBL_COLOC, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIP, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOC, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_VALASI, tblDat);    
            objTblMod.addSystemHiddenColumn(INT_TBL_ESTVALASI, tblDat);    

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CHK);
            if(objParSis.getCodigoMenu()==1942) {
                vecAux.add("" + INT_TBL_VALAPL);
            }
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);                

            //Configurar JTable: Renderizador
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFil = tblDat.getSelectedRow();
                    if(objParSis.getCodigoUsuario()!=1){
                        String strEstAsi = (objTblMod.getValueAt(intFil, INT_TBL_ESTVALASI)==null?"N":(objTblMod.getValueAt(intFil, INT_TBL_ESTVALASI).equals("")?"N":objTblMod.getValueAt(intFil, INT_TBL_ESTVALASI).toString()));
                        if(strEstAsi.equals("S"))
                            objTblCelEdiChk.setCancelarEdicion(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.isChecked(intFil, INT_TBL_CHK))  {
                        BigDecimal bgdValPen=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_VALPEN)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_VALPEN).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_VALPEN).toString()));
                        objTblMod.setValueAt(""+bgdValPen, intFil, INT_TBL_VALAPL);
                    }
                    else{
                        objTblMod.setValueAt("0", intFil, INT_TBL_VALAPL);
                    }
                    calcularSubTot();
                }
            });

            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_VALAPL).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFil = tblDat.getSelectedRow();
                    if(objParSis.getCodigoUsuario()!=1){
                        String strEstAsi = (objTblMod.getValueAt(intFil, INT_TBL_ESTVALASI)==null?"N":(objTblMod.getValueAt(intFil, INT_TBL_ESTVALASI).equals("")?"N":objTblMod.getValueAt(intFil, INT_TBL_ESTVALASI).toString()));
                        if(strEstAsi.equals("S"))
                            objTblCelEdiTxt.setCancelarEdicion(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    BigDecimal bgdValApl=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_VALAPL)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_VALAPL).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_VALAPL).toString()));                    
                    BigDecimal bgdValPen=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_VALPEN)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_VALPEN).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_VALPEN).toString()));
        
                   if(objParSis.getCodigoMenu()==1749)
                    {
                        BigDecimal bgdDif=objUti.redondearBigDecimal((bgdValPen.subtract(bgdValApl)), 4);
                        if( !(( bgdDif.compareTo(bgdMinAjuCenAut) >= 0 ) && ( bgdDif.compareTo(bgdMaxAjuCenAut) <= 0 ))   )
                        {
                            mostrarMsgInf("EL VALOR APLICADO ESTA FUERA DE RANGO DE AJUSTE DE CENTAVOS.");
                            objTblMod.setValueAt("", intFil, INT_TBL_VALAPL );
                            objTblMod.setValueAt( new Boolean(false), intFil, INT_TBL_CHK );
                        }
                        else{
                            if(bgdValApl.compareTo(BigDecimal.ZERO) > 0 ) 
                                objTblMod.setValueAt( new Boolean(true), intFil, INT_TBL_CHK );
                            else 
                                objTblMod.setValueAt( new Boolean(false), intFil, INT_TBL_CHK );
                        }
                    }
                    else{
                        if(bgdValApl.compareTo(bgdValPen) > 0){
                            //mostrarMsgInf("EL VALOR QUE ESTA APLICANDO ES MAYOR AL VALOR PENDIENTE..");
                            //objTblMod.setValueAt( new Boolean(true), intFil, INT_TBL_CHK );

                            mostrarMsgInf("NO PUEDE SER MAYOR EL VALOR APLICADO CON EL VALOR PENDIENTE..");
                            objTblMod.setValueAt("", intFil, INT_TBL_VALAPL );
                            objTblMod.setValueAt( new Boolean(false), intFil, INT_TBL_CHK );
                        }
                        else{  
                            if(bgdValApl.compareTo(BigDecimal.ZERO) > 0 ) 
                                objTblMod.setValueAt( new Boolean(true), intFil, INT_TBL_CHK );
                            else 
                                objTblMod.setValueAt( new Boolean(false), intFil, INT_TBL_CHK );
                        }
                    }
                    calcularSubTot();
                }
            });

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn( INT_TBL_VALDOC ).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn( INT_TBL_VALPEN ).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn( INT_TBL_VALAPL ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }  
        return blnRes;
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_LIN:
                    strMsg="";
                    break;
                case INT_TBL_CHK:
                    strMsg="Selección.";
                    break;
                case INT_TBL_DCTDOC:
                    strMsg="Descripción corta del tipo documento.";
                    break;
                case INT_TBL_DLTDOC:
                    strMsg="Descripción larga del tipo documento.";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    

    /**
     * Calcula el total de las facturas selecciondas.
     */
    private void calcularSubTot(){
        BigDecimal bgdValTot = BigDecimal.ZERO;
        for(int i=0; i<tblDat.getRowCount(); i++)
        {
            if(tblDat.getValueAt(i, INT_TBL_CHK).toString().equals("true"))
            {
                BigDecimal bgdValPen=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_VALAPL)==null?"0":(objTblMod.getValueAt(i, INT_TBL_VALAPL).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_VALAPL).toString()));
                bgdValTot=bgdValTot.add(bgdValPen);
            }
        }

        if(bgdValTot.compareTo(bgdMonChq)>0 ){
            mostrarMsgInf("HA SOBREPASADO EL VALOR APLICADO CORRIGA E INTENTELO DE NUEVO ");
        }

        bgdValTot= objUti.redondearBigDecimal(bgdValTot, 3);
        txtValTotFac.setText(""+bgdValTot);
    }

    public boolean acepta(){
        return blnEst;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panDat = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFrm = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JPanel();
        panTot = new javax.swing.JPanel();
        txtValTotFac = new javax.swing.JTextField();
        panSur = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        spnFrm.setLayout(new java.awt.BorderLayout());

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

        spnFrm.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setLayout(new java.awt.BorderLayout());

        txtValTotFac.setEditable(false);
        txtValTotFac.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValTotFac.setText("0.00");
        txtValTotFac.setPreferredSize(new java.awt.Dimension(80, 20));
        panTot.add(txtValTotFac);

        spnTot.add(panTot, java.awt.BorderLayout.EAST);

        spnFrm.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", spnFrm);

        panDat.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panDat, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(90, 23));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(90, 23));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panSur.add(panBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(650, 400));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        cargarDat();
    }//GEN-LAST:event_formWindowOpened

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if( objUti.redondearBigDecimal( txtValTotFac.getText(), 2).compareTo(bgdMonChq)  > 0 )
        {
            mostrarMsgInf("HA SOBREPASADO EL VALOR APLICADO CORRIGA E INTENTELO DE NUEVO ");
        }
        else{
            strFacSel=""; strNumDocSel="";
            for(int i=0; i<objTblMod.getRowCountTrue(); i++)
            {
                if(objTblMod.isChecked(i, INT_TBL_CHK))
                {
                    if(!strFacSel.equals("")) 
                        strFacSel+=" UNION ALL ";

                    strFacSel+=" SELECT "+objTblMod.getValueAt(i, INT_TBL_COEMP).toString()+" as coemp";
                    strFacSel+="       ,"+objTblMod.getValueAt(i, INT_TBL_COLOC).toString()+" as coloc";
                    strFacSel+="       ,"+objTblMod.getValueAt(i, INT_TBL_CODTIP).toString()+" as cotipdoc";
                    strFacSel+="       ,"+objTblMod.getValueAt(i, INT_TBL_CODDOC).toString()+" as codoc";
                    strFacSel+="       ,"+objTblMod.getValueAt(i, INT_TBL_VALAPL).toString()+" as valapl";
                    //strFacSel+="       ,"+objTblMod.getValueAt(i, INT_TBL_VALPEN).toString()+" as valpen";

                    if(!strNumDocSel.equals("")) 
                        strNumDocSel+=",";
                    strNumDocSel+=objTblMod.getValueAt(i, INT_TBL_NUMDOC).toString();
                }
            }

            Str_RegSel[0] = strFacSel;
            Str_RegSel[1] = strNumDocSel;
            Str_RegSel[2] = txtValTotFac.getText();

            blnAcepta = true;
            blnEst=true;

            dispose();
        }
}//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitForm();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTot;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JPanel spnFrm;
    private javax.swing.JPanel spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtValTotFac;
    // End of variables declaration//GEN-END:variables

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public String GetCamSel(int Idx){
        if(!(Str_RegSel==null)){
            if(Idx <= 0 || Idx > Str_RegSel.length)
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length) ;
            else
                return Str_RegSel[Idx-1];
        }
        else{
            return "";
        }
    }
    
    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }

    private void cargarDat()
    {
       if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }

    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            cargarDatCla(strSQL);
            objThrGUI=null;
        }
    }

    /**
     * Función que se encarga de cargar la información en la tabla
     * @param strSql Query que recibe para cargar la tabla
     * @return true si esta correcto y false  si hay algun error 
     */
    private boolean cargarDatCla(String strSql)
    {
        boolean blnRes=false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        BigDecimal bgdValTot=BigDecimal.ZERO;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
            if(conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                //Limpiar vector de datos.
                vecDat.clear();
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CHK,    new Boolean( (rstLoc.getString("est").equals("S")?true:false) ) );
                    vecReg.add(INT_TBL_COEMP,  rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_COLOC,  rstLoc.getString("co_loc") );
                    vecReg.add(INT_TBL_CODTIP, rstLoc.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                    vecReg.add(INT_TBL_DCTDOC, rstLoc.getString("tx_descor") );
                    vecReg.add(INT_TBL_DLTDOC, rstLoc.getString("tx_deslar") );
                    vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
                    vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
                    vecReg.add(INT_TBL_VALDOC, rstLoc.getString("nd_tot") );
                    vecReg.add(INT_TBL_VALPEN, rstLoc.getString("ndvalpen") );
                    vecReg.add(INT_TBL_VALAPL, rstLoc.getString("valapl")  );
                    vecReg.add(INT_TBL_VALASI, rstLoc.getString("ndvalasi")  );
                    vecReg.add(INT_TBL_ESTVALASI, rstLoc.getString("estvalasi")  );
                    vecDat.add(vecReg);
                    bgdValTot=bgdValTot.add(rstLoc.getBigDecimal("valapl"));
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;                

                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                bgdValTot = objUti.redondearBigDecimal(bgdValTot, 2 );
                txtValTotFac.setText(""+bgdValTot);

                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
            }
        }
        catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        System.gc();
        return blnRes;
    }    




}
