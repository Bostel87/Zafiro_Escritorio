/*
 * ZafImp05.java
 *
 * Created on 22 de agosto de 2013
 */
package Importaciones.ZafImp05;
import Librerias.ZafImp.ZafAjuInv;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafImp.ZafSegAjuInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp05 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_LOC=2;
    static final int INT_TBL_DAT_COD_TIP_DOC=3;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    static final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    static final int INT_TBL_DAT_COD_DOC=6;
    static final int INT_TBL_DAT_NUM_DOC=7;
    static final int INT_TBL_DAT_NUM_PED=8;
    static final int INT_TBL_DAT_FEC_DOC=9;
    static final int INT_TBL_DAT_COD_EXP=10;
    static final int INT_TBL_DAT_NOM_EXP=11;
    static final int INT_TBL_DAT_TOT_FOB_CFR=12;
    static final int INT_TBL_DAT_TOT_ARA_FOD_IVA=13;
    static final int INT_TBL_DAT_DIA_ARR=14;
    static final int INT_TBL_DAT_EST=15;
    static final int INT_TBL_DAT_CHK_ARR=16;
    static final int INT_TBL_DAT_CHK_CIE=17;
    static final int INT_TBL_DAT_BUT=18;
    static final int INT_TBL_DAT_BUT_NOT_PED=19;
    static final int INT_TBL_DAT_BUT_PED_EMB=20;
    static final int INT_TBL_DAT_CHK_AJU=21;
    static final int INT_TBL_DAT_BUT_DOC_AJU=22;           
       
    //Variables
    private ZafSelFec objSelFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafPerUsr objPerUsr;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                          //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                          //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                          //Editor: JCheckBox en celda.
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButNotPed, objTblCelRenButPedEmb, objTblCelRenButDocAju;
    private ZafTblCelEdiButGen objTblCelEdiButGen, objTblCelEdiButGenNotPed, objTblCelEdiButGenPedEmb, objTblCelEdiButGenDocAju;
    private ZafMouMotAda objMouMotAda;                                //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                      //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                      //JTable de ordenamiento.
    private ZafAjuInv objAjuInv;
    private ZafSegAjuInv objSegAjuInv;
    private ZafImp objImp;
    private ZafVenCon vcoEmp, vcoExp;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private java.util.Date datFecAux;
    
    private int intCodTipDocPedEmb, intCodDocPedEmb;
    private int intRowsDocAju=-1;
    private int intCodEmpAju, intCodLocAju, intCodTipDocAju,  intCodDocAju;
    
    private String strCodEmp, strNomEmp, strCodExp,  strNomExp;
    
    private String strVersion =" v0.1.4";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp05(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
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
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3212))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3213))
            {
                butCer.setVisible(false);
            }  
            
            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(false);
            objSelFecDoc.setCheckBoxChecked(true);
            objSelFecDoc.setTitulo("Fecha del documento");
            
            //Configurar objetos.
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarVenConEmp();
                txtCodImp.setEditable(true);
                txtNomImp.setEditable(true);
                butImp.setEnabled(true);
            }
            else{
                lblEmpImp.setVisible(false);
                txtCodImp.setVisible(false);
                txtNomImp.setVisible(false);
                butImp.setVisible(false);
            }
                        
            //Configurar los JTables.
            configurarTblDat();
            configurarVenConExp();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(22);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_EXP,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_NOM_EXP,"Exportador");
            vecCab.add(INT_TBL_DAT_TOT_FOB_CFR,"Tot.Fob.Cfr.");
            vecCab.add(INT_TBL_DAT_TOT_ARA_FOD_IVA,"Tot.Ara.Fod.Iva.");
            vecCab.add(INT_TBL_DAT_DIA_ARR,"Dia.Arr.");   
            vecCab.add(INT_TBL_DAT_EST,"Est.");
            vecCab.add(INT_TBL_DAT_CHK_ARR,"Arr.");
            vecCab.add(INT_TBL_DAT_CHK_CIE,"Cie.");
            vecCab.add(INT_TBL_DAT_BUT,"Ing.");
            vecCab.add(INT_TBL_DAT_BUT_NOT_PED,"Not.");
            vecCab.add(INT_TBL_DAT_BUT_PED_EMB,"Emb."); 
            vecCab.add(INT_TBL_DAT_CHK_AJU,"Est.");
            vecCab.add(INT_TBL_DAT_BUT_DOC_AJU,"Aju.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(108);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_TOT_ARA_FOD_IVA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DIA_ARR).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ARR).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_BUT_NOT_PED).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_BUT_PED_EMB).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AJU).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC_AJU).setPreferredWidth(35);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EXP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_AJU, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_ARA_FOD_IVA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT);
            vecAux.add("" + INT_TBL_DAT_BUT_NOT_PED);
            vecAux.add("" + INT_TBL_DAT_BUT_PED_EMB);
            vecAux.add("" + INT_TBL_DAT_BUT_DOC_AJU);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_ARR).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AJU).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ARR).setCellEditor(objTblCelEdiChk);  
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setCellEditor(objTblCelEdiChk);  
            tcmAux.getColumn(INT_TBL_DAT_CHK_AJU).setCellEditor(objTblCelEdiChk);  
                      
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE INGRESO POR IMPORTACION
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        mostrarFormularioIngresoImportacion(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE NOTA DE PEDIDO
            objTblCelRenButNotPed=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_NOT_PED).setCellRenderer(objTblCelRenButNotPed);
            objTblCelEdiButGenNotPed=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_NOT_PED).setCellEditor(objTblCelEdiButGenNotPed);
            objTblCelEdiButGenNotPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    mostrarFormularioNotaPedido(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE PEDIDO EMBARCADO
            objTblCelRenButPedEmb=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_PED_EMB).setCellRenderer(objTblCelRenButPedEmb);
            objTblCelEdiButGenPedEmb=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_PED_EMB).setCellEditor(objTblCelEdiButGenPedEmb);
            objTblCelEdiButGenPedEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        mostrarFormularioPedidoEmbarcado(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE DOCUMENTOS DE AJUSTES ASOCIADOS A LOS INGRESOS POR IMPORTACIÓN.
            objTblCelRenButDocAju=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_DOC_AJU).setCellRenderer(objTblCelRenButDocAju);
            objTblCelRenButDocAju.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButDocAju.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_DOC_AJU:
                            if(objTblMod.isChecked(objTblCelRenButDocAju.getRowRender(), INT_TBL_DAT_CHK_AJU))
                               objTblCelRenButDocAju.setText("...");
                            else
                               objTblCelRenButDocAju.setText("");
                        break;
                    }
                }
            });
            objTblCelEdiButGenDocAju=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_DOC_AJU).setCellEditor(objTblCelEdiButGenDocAju);
            objTblCelEdiButGenDocAju.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AJU))
                        objTblCelEdiButGenDocAju.setCancelarEdicion(true);
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AJU))
                        mostrarFormularioDocumentoAjuste(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento del Ingreso de Importación ";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número del Pedido del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_COD_EXP:
                    strMsg="Código del Exportador del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_NOM_EXP:
                    strMsg="Nombre del Exportador del Ingreso de Importación";
                    break;                    
                case INT_TBL_DAT_TOT_FOB_CFR:
                    strMsg="Total FOB / CFR";
                    break;
                case INT_TBL_DAT_TOT_ARA_FOD_IVA:
                    strMsg="Total de arancel, fodinfa e Iva";
                    break;
                case INT_TBL_DAT_DIA_ARR:
                    strMsg="Dias de arribo que tiene el pedido sin cerrar.";
                    break;    
                case INT_TBL_DAT_EST:
                    strMsg="Estado del ingreso por importación";
                    break;
                case INT_TBL_DAT_CHK_ARR:
                    strMsg="Indica si pedido ha sido arribado.";
                    break; 
                case INT_TBL_DAT_CHK_CIE:
                    strMsg="Indica si pedido ha sido cerrado.";
                    break;                     
                case INT_TBL_DAT_BUT:
                    strMsg="Muestra formulario del Ingreso de Importación";
                    break;
                case INT_TBL_DAT_BUT_NOT_PED:
                    strMsg="Muestra formulario de la nota de pedido";
                    break;
                case INT_TBL_DAT_BUT_PED_EMB:
                    strMsg="Muestra formulario del pedido embarcado";
                    break;
                case INT_TBL_DAT_CHK_AJU:
                    strMsg="Existe documento de ajuste asociado";
                    break;
                case INT_TBL_DAT_BUT_DOC_AJU:
                    strMsg="Muestra documentos de ajustes del pedido";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.

            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            else{
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtCodImp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtNomImp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConExp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_nom2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Nombre alterno");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("225");
            arlAncCol.add("225");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoExp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de exportadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoExp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try{               
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoExp.setCampoBusqueda(1);
                    vcoExp.show();
                    if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(0);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtCodExp.setText(strCodExp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoExp.buscar("a1.tx_nom", txtNomExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(2);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtNomExp.setText(strNomExp);
                        }
                    }
                    break;
            }

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmpImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        lblEstDoc = new javax.swing.JLabel();
        cboEstDoc = new javax.swing.JComboBox();
        chkMosNotPedAso = new javax.swing.JCheckBox();
        chkMosPedEmbAso = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panCon.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panCon.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 80));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panCon.add(panFecDoc, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(0, 250));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 10, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 30, 400, 20);

        lblEmpImp.setText("Empresa(Importador):");
        lblEmpImp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmpImp);
        lblEmpImp.setBounds(40, 60, 140, 20);

        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        panFil.add(txtCodImp);
        txtCodImp.setBounds(180, 60, 70, 20);

        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        panFil.add(txtNomImp);
        txtNomImp.setBounds(250, 60, 360, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFil.add(butImp);
        butImp.setBounds(610, 60, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblExp);
        lblExp.setBounds(40, 80, 140, 20);

        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        panFil.add(txtCodExp);
        txtCodExp.setBounds(180, 80, 70, 20);

        txtNomExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExpActionPerformed(evt);
            }
        });
        txtNomExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExpFocusLost(evt);
            }
        });
        panFil.add(txtNomExp);
        txtNomExp.setBounds(250, 80, 360, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panFil.add(butExp);
        butExp.setBounds(610, 80, 20, 20);

        lblEstDoc.setText("Estado del documento:");
        panFil.add(lblEstDoc);
        lblEstDoc.setBounds(40, 100, 140, 20);

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A:Activo", "I:Inactivo" }));
        panFil.add(cboEstDoc);
        cboEstDoc.setBounds(180, 100, 210, 20);

        chkMosNotPedAso.setSelected(true);
        chkMosNotPedAso.setText("Mostrar notas de pedidos asociadas");
        chkMosNotPedAso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosNotPedAsoActionPerformed(evt);
            }
        });
        panFil.add(chkMosNotPedAso);
        chkMosNotPedAso.setBounds(40, 140, 310, 20);

        chkMosPedEmbAso.setSelected(true);
        chkMosPedEmbAso.setText("Mostrar pedidos embarcados asociados");
        chkMosPedEmbAso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedEmbAsoActionPerformed(evt);
            }
        });
        panFil.add(chkMosPedEmbAso);
        chkMosPedEmbAso.setBounds(40, 160, 310, 20);

        panCon.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panCon);

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
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp.setText("");
            cboEstDoc.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodEmp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
        if (!txtCodImp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodImp.getText().equals("")){
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblMod.removeAllRows();
                txtCodExp.setText("");
                txtNomExp.setText("");
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodImp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodImpFocusLost

    private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
        txtNomImp.transferFocus();
    }//GEN-LAST:event_txtNomImpActionPerformed

    private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
        strNomEmp=txtNomImp.getText();
        txtNomImp.selectAll();
    }//GEN-LAST:event_txtNomImpFocusGained

    private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
        if (!txtNomImp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomImp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomImpFocusLost

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        strCodEmp=txtCodImp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butImpActionPerformed

    private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
        txtCodExp.transferFocus();
    }//GEN-LAST:event_txtCodExpActionPerformed

    private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
        strCodExp=txtCodExp.getText();
        txtCodExp.selectAll();
    }//GEN-LAST:event_txtCodExpFocusGained

    private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)){
            if (txtCodExp.getText().equals("")){
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConExp(1);
        }
        else
            txtCodExp.setText(strCodExp);
    }//GEN-LAST:event_txtCodExpFocusLost

    private void txtNomExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExpActionPerformed
        txtNomExp.transferFocus();
    }//GEN-LAST:event_txtNomExpActionPerformed

    private void txtNomExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusGained
        strNomExp=txtNomExp.getText();
        txtNomExp.selectAll();
    }//GEN-LAST:event_txtNomExpFocusGained

    private void txtNomExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomExp.getText().equalsIgnoreCase(strNomExp))
        {
            if (txtNomExp.getText().equals(""))
            {
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConExp(2);
            }
        }
        else
            txtNomExp.setText(strNomExp);
    }//GEN-LAST:event_txtNomExpFocusLost

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        strCodExp=txtCodExp.getText();
        mostrarVenConExp(0);
    }//GEN-LAST:event_butExpActionPerformed

    private void chkMosNotPedAsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosNotPedAsoActionPerformed
        if(chkMosNotPedAso.isSelected()){
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_NOT_PED, tblDat);
        }
        else{
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_NOT_PED, tblDat);
        }
    }//GEN-LAST:event_chkMosNotPedAsoActionPerformed

    private void chkMosPedEmbAsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedEmbAsoActionPerformed
        if(chkMosPedEmbAso.isSelected()){
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_PED_EMB, tblDat);
        }
        else{
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_PED_EMB, tblDat);
        }
    }//GEN-LAST:event_chkMosPedEmbAsoActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butImp;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JCheckBox chkMosNotPedAso;
    private javax.swing.JCheckBox chkMosPedEmbAso;
    private javax.swing.JLabel lblEmpImp;
    private javax.swing.JLabel lblEstDoc;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomImp;
    // End of variables declaration//GEN-END:variables
    

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
      
     private void mostrarFormularioIngresoImportacion(int fila){
        String strCodEmp=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP).toString();
        String strCodLoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString();
        String strCodTipDoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString();
        String strCodDoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString();
                                
        Importaciones.ZafImp34.ZafImp34 objImp_34=new Importaciones.ZafImp34.ZafImp34(objParSis, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc));
        this.getParent().add(objImp_34,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objImp_34.setVisible(true);
        objImp_34=null;
    }
    
    private void mostrarFormularioNotaPedido(int fila){
        try{
            
            ZafImp05_01 objImp05_01=new ZafImp05_01(objParSis);//datos de nota de pedido
            objImp05_01.setCodigoEmpresaIngresoImportacion(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP).toString()));
            objImp05_01.setCodigoLocalIngresoImportacion(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString()));
            objImp05_01.setCodigoTipoDocumentoIngresoImportacion(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString()));
            objImp05_01.setCodigoDocumentoIngresoImportacion(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString()));
            objImp05_01.cargarDocumentos();
             
            if(objImp05_01.getNumeroRegistros()==1){
                //se carga el programa Nota de Pedido
                Importaciones.ZafImp32.ZafImp32 objImp_32=new Importaciones.ZafImp32.ZafImp32(objParSis, objImp05_01.getCodigoLocalNotaPedido(), objImp05_01.getCodigoTipoDocumentoNotaPedido(), objImp05_01.getCodigoDocumentoNotaPedido());
                this.getParent().add(objImp_32,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objImp_32.setVisible(true);
                objImp_32=null;
            }
            else{
                this.getParent().add(objImp05_01,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objImp05_01.setVisible(true);
                objImp05_01=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    private void mostrarFormularioPedidoEmbarcado(int fila){
        int intCodEmpIngImp=0;
        int intCodLocIngImp=0;
        int intCodTipDocIngImp=0;
        int intCodDocIngImp=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_desCor, b1.tx_desLar";
                strSQL+="     , b1.co_tipDocIngImp, b1.co_docIngImp, COUNT(b1.co_docIngImp) AS ne_numRegPedEmb ";
                strSQL+=" FROM(";
                strSQL+="	SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL+=" 	     , a7.tx_desCor, a7.tx_desLar, a4.ne_numDoc, a4.tx_numDoc2";
                strSQL+=" 	     , a5.co_tipDoc AS co_tipDocIngImp, a5.co_doc AS co_docIngImp";
                strSQL+=" 	FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="	INNER JOIN tbm_detPedEmbImp AS a3";
                strSQL+="	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locrel AND a2.co_tipdoc=a3.co_tipDocrel AND a2.co_doc=a3.co_docrel AND a2.co_reg=a3.co_regrel";
                strSQL+="	INNER JOIN tbm_cabPedEmbImp AS a4";
                strSQL+=" 	ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" 	INNER JOIN tbm_cabMovInv AS a5";
                strSQL+=" 	ON a4.co_emp=a5.co_empRelPedEmbImp AND a4.co_loc=a5.co_locRelPedEmbImp";
                strSQL+=" 	AND a4.co_tipDoc=a5.co_tipDocRelPedEmbImp AND a4.co_doc=a5.co_docRelPedEmbImp";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a7";
                strSQL+=" 	ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc";
                strSQL+=" 	WHERE a4.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                strSQL+=" 	AND a5.co_emp=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP) + " AND a5.co_loc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC) + "";
                strSQL+="       AND a5.co_tipDoc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC) + " AND a5.co_doc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC) + "";
                strSQL+=" 	GROUP BY a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc";
                strSQL+=" 	, a7.tx_desCor, a7.tx_desLar, a4.ne_numDoc, a4.tx_numDoc2";
                strSQL+=" 	, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL+="	ORDER BY a4.co_doc, a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+="        , b1.tx_desCor, b1.tx_desLar, b1.co_tipDocIngImp, b1.co_docIngImp";                
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    if(rst.getInt("ne_numRegPedEmb")<=1){
                        intCodEmpIngImp=rst.getInt("co_emp");
                        intCodLocIngImp=rst.getInt("co_loc");
                        intCodTipDocIngImp=rst.getInt("co_tipDoc");
                        intCodDocIngImp=rst.getInt("co_doc");
                    }
                    else{
                        intCodEmpIngImp=0;
                        intCodLocIngImp=0;
                        intCodTipDocIngImp=0;
                        intCodDocIngImp=0;
                    }
                }
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                
                //se carga el programa ingreso por importacion
                Importaciones.ZafImp33.ZafImp33 objImp_33=new Importaciones.ZafImp33.ZafImp33(objParSis, intCodTipDocIngImp, intCodDocIngImp);
                this.getParent().add(objImp_33,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objImp_33.setVisible(true);
                //se limpia el objeto
                objImp_33=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }    

    private void mostrarFormularioDocumentoAjuste(int fila)
    {
        try
        {
            if(getDocumentosAjuste(fila))
            {
                if(intRowsDocAju==1)
                {
                    con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con != null)
                    {
                        //Se carga el Ajuste de Inventario
                        objAjuInv = new ZafAjuInv( javax.swing.JOptionPane.getFrameForComponent(this), objParSis, con, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, objImp.INT_COD_MNU_PRG_AJU_INV, 'c'); 
                        objAjuInv.show();
                        //this.getParent().add(objAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                        //objAjuInv.setVisible(true);
                        objAjuInv=null; 
                                                
                        con.close();
                        con=null;
                    }
                }
                else
                {          
                    //Se carga el seguimiento de documento de ajustes.
                    objSegAjuInv=new ZafSegAjuInv(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strSQL, false);
                    objSegAjuInv.show();
                    objSegAjuInv=null;
                    
                    // this.getParent().add(objSegAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                    // objSegAjuInv.setVisible(true);                    
                }
            }
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e){    objUti.mostrarMsgErr_F1(this, e);   }
    }   
    
    private boolean getDocumentosAjuste(int fila)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.fe_doc, a1.tx_numDoc2";
                strSQL+="      , 0 as co_itmMae, 0 as nd_Can, 0 as nd_canSol, 0 as nd_CanTrs, a.st_Aut ";     
                strSQL+=" FROM ( ";
                strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar ";
                strSQL+="        , a1.ne_numDoc, a1.fe_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel";
                strSQL+="        , a1.st_aut ";
                //strSQL+="        , CASE WHEN a1.st_ingImp IS NULL THEN 'P' ELSE CASE WHEN a1.st_ingImp ='T' THEN 'A'";
                //strSQL+="         ELSE CASE WHEN a1.st_ingImp IN ('D','C') THEN 'D' ELSE 'P' END END END AS st_aut ";  
                strSQL+="    FROM tbr_cabMovInv as a ";
                strSQL+="    INNER JOIN tbm_cabMovInv as a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc ";
                strSQL+="    INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc ";
                strSQL+="    WHERE a1.co_tipDoc in (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="                           and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";
                strSQL+="    AND a1.st_reg NOT IN('E'/*,'I'*/) ";  //Se comenta para que presente los ajustes denegados, los mismos que tienen st_Reg='I' 31Ago2017
                strSQL+="    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar ";
                strSQL+="           , a1.ne_numDoc, a1.fe_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a1.st_ingImp ";
                strSQL+=" ) as a ";
                strSQL+=" INNER JOIN tbm_CabMovInv as a1 ON a1.co_emp=a.co_empRel AND a1.co_loc=a.co_locRel AND a1.co_tipDoc=a.co_tipDocRel AND a1.co_doc=a.co_docRel ";
                strSQL+=" WHERE  a1.co_emp=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP); 
                strSQL+=" AND a1.co_loc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC);
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.fe_doc, a1.tx_numDoc2, a.st_Aut";
                strSQL+=" ORDER BY a1.tx_numDoc2, a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc";
                rst=stm.executeQuery(strSQL);
                intRowsDocAju=0;
                while(rst.next())
                {
                    intCodEmpAju = rst.getInt("co_emp");
                    intCodLocAju = rst.getInt("co_loc");
                    intCodTipDocAju = rst.getInt("co_tipDoc");
                    intCodDocAju = rst.getInt("co_doc");
                    intRowsDocAju=rst.getRow();
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e) {   blnRes= false;  objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e){  blnRes= false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
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
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        String strFilFec="";
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Fecha de documento
                if(objSelFecDoc.isCheckBoxChecked()){
                    switch (objSelFecDoc.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strFilFec+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strFilFec+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strFilFec+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                //Programa Listado de ingresos por importacion
                strSQL ="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_desCor, b1.tx_desLar, b1.ne_numDoc, b1.tx_numDoc2";
                strSQL+="      , b1.fe_doc, b1.co_exp, b1.tx_nomExp, b1.st_reg, b1.fe_ingIngImp, b1.st_ingImp, b1.st_tieAju";
                strSQL+="      , CASE WHEN b1.st_ingImp !='C' THEN ((current_date - b1.fe_ingIngImp)) END  as ne_diaPed ";
                strSQL+="      , b1.nd_valDoc, SUM(b3.nd_valCarPag) as nd_valCarPag";
                strSQL+=" FROM(";
                strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+=" 	     , CASE WHEN a5.co_exp IS NULL THEN a1.co_cli ELSE a5.co_exp END as co_exp";
                strSQL+=" 	     , CASE WHEN a5.co_exp IS NULL THEN a6.tx_nom ELSE a3.tx_nom END as tx_nomExp, a1.st_reg";
                strSQL+=" 	     , CAST(a1.fe_ingIngImp AS DATE) AS fe_ingIngImp ";
                strSQL+=" 	     , CASE WHEN a1.st_ingImp IS NULL THEN 'P' ELSE a1.st_ingImp END as st_ingImp ";
                strSQL+="	     , CASE WHEN a1.st_cieAjuInvIngImp IS NULL THEN 'N' ELSE a1.st_cieAjuInvIngImp END as st_tieAju";
                strSQL+="	     , SUM (a5.nd_cosTot) as nd_valDoc";
                strSQL+="       FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a5";
                strSQL+=" 	ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc AND a1.co_doc=a5.co_doc";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+="	LEFT JOIN tbm_expImp AS a3 ON a5.co_exp=a3.co_exp";
                strSQL+="	LEFT JOIN tbm_cli AS a6 ON(a1.co_emp=a6.co_emp AND a1.co_cli=a6.co_cli)";
                
                if(cboEstDoc.getSelectedIndex()==0)//se presentan todos excepto los eliminados
                    strSQL+="       WHERE a1.st_reg NOT IN ('E')";
                else if(cboEstDoc.getSelectedIndex()==1)//se presentan solo los activos
                    strSQL+="       WHERE a1.st_reg IN ('A')";
                else if(cboEstDoc.getSelectedIndex()==2)//se presentan solo los activos
                    strSQL+="       WHERE a1.st_reg IN ('I')";
                
                strAux=txtCodImp.getText();
                if (!strAux.equals(""))
                    strSQL+="       AND a1.co_emp=" + strAux + "";
                strAux=txtCodExp.getText();
                if (!strAux.equals(""))
                    strSQL+="       AND a5.co_exp=" + strAux + "";
                
                strSQL+="       " + strFilFec; 
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" 	       , a1.fe_doc, a5.co_exp, a6.tx_nom, a3.tx_nom, a1.nd_tot, a1.st_reg, a1.fe_ingIngImp, a1.st_ingImp, a1.st_cieAjuInvIngImp";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+="      SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_valCarPag";
                strSQL+="      FROM tbm_carPagMovInv as a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+="      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="      WHERE a1.co_CarPag IN(1,4,9)";
                strSQL+="      GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_exp, a1.nd_valCarPag";
                strSQL+=" ) AS b3 ON b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc AND b1.co_doc=b3.co_doc ";                
                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_desCor, b1.tx_desLar, b1.ne_numDoc, b1.tx_numDoc2";
                strSQL+=" 	 , b1.fe_doc, b1.co_exp, b1.tx_nomExp, b1.st_reg, b1.fe_ingIngImp, b1.st_ingImp, b1.st_tieAju, b1.nd_valDoc";
                strSQL+=" ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                System.out.println("cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));      //co_empIngImp
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));      //co_locIngImp
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));   //co_tipDocIngImp
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));   //tx_desCorTipDocIngImp
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));   //tx_desCorTipDocIngImp
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));      //co_docIngImp
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDoc"));   //Núm.Doc.
                        vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numDoc2"));  //Núm.Ped.
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));      //fe_doc
                        vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));      //co_exp
                        vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));   //tx_nomExp
                        vecReg.add(INT_TBL_DAT_TOT_FOB_CFR,     rst.getString("nd_valDoc"));   //nd_valDoc
                        vecReg.add(INT_TBL_DAT_TOT_ARA_FOD_IVA, rst.getString("nd_valCarPag"));//nd_valCarPag
                        vecReg.add(INT_TBL_DAT_DIA_ARR,         rst.getString("ne_diaPed"));   //Dias de pedido sin cerrar.
                        vecReg.add(INT_TBL_DAT_EST,             rst.getString("st_reg"));      //st_reg
                        vecReg.add(INT_TBL_DAT_CHK_ARR,         null);//Chk.Arr.
                        vecReg.add(INT_TBL_DAT_CHK_CIE,         null);//Chk.Cer.
                        vecReg.add(INT_TBL_DAT_BUT,             null);//Btn.Ing.Imp.
                        vecReg.add(INT_TBL_DAT_BUT_NOT_PED,     null);//Btn.Not.Ped.
                        vecReg.add(INT_TBL_DAT_BUT_PED_EMB,     null);//Btn.Ped.Emb.
                        vecReg.add(INT_TBL_DAT_CHK_AJU,         null);//Chk.Doc.Aju.
                        vecReg.add(INT_TBL_DAT_BUT_DOC_AJU,     null);//Btn.Doc.Aju.
                        
                        if(!  (  (rst.getObject("st_tieAju")==null)  || (rst.getString("st_tieAju").equals("N"))  )  )
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_AJU);   

                        if(!rst.getString("st_ingImp").equals("P"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_ARR);   
                        
                        if(rst.getString("st_ingImp").equals("C"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_CIE);   
                        
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
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
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    

    
}