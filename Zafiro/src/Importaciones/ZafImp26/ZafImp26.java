/*
 * ZafImp26.java
 *
 * Created on 04 de abril de 2017
 */

package Importaciones.ZafImp26;
import Librerias.ZafImp.ZafAjuInv;
import Librerias.ZafImp.ZafImp;
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
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafImp26 extends javax.swing.JInternalFrame
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
    static final int INT_TBL_DAT_DIAPEN_CIE=10;
    static final int INT_TBL_DAT_EST_ING_IMP=11;
    static final int INT_TBL_DAT_CHK_CIE_PED=12;
    static final int INT_TBL_DAT_COD_USR=13;
    static final int INT_TBL_DAT_TXT_USR=14;
    static final int INT_TBL_DAT_NOM_USR=15;
    static final int INT_TBL_DAT_FEC_CIE=16;
    static final int INT_TBL_DAT_BUT=17;
    
    private int INT_TBL_MNU_CIE_IMP=4161;
    
    //Código de Tipo de Documento
    private static final int INT_COD_TIPDOC_INIMPO=14;        //Tipo de Documento Ingreso por Importación.
    private static final int INT_COD_TIPDOC_INBOCL=245;       //Tipo de Documento Ingreso por Compras Locales.        
           
    //Variables
    private ZafSelFec objSelFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                  //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                  //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButGen objTblCelEdiBut;               //Render: Editar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                  //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                  //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                        //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                        //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblEdi objTblEdi;                              //Editor: Editor del JTable.
    private ZafTblBus objTblBus;                              //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                              //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;
    private ZafVenCon vcoPed;                                 //Ventana de consulta "Pedidos".
    private ZafImp objImp;
    private ZafAjuInv objAjuInv;   
    private java.util.Date datFecAux;   
    private Connection con;
    private Statement stm;
    private ResultSet rst;
   
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strSQL, strAux, strEst;
    private String strMsg, strMsjSolPen;                       //Mensajes Informativos.
    private String strCodPed, strNumPed, strPedImp;            //Contenido del campo al obtener el foco.
    
    private int intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp;
    
    private String strVersion=" v0.1.2 ";
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp26(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else
            {
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
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiBut = new ZafTblCelEdiButGen();
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if(objParSis.getCodigoMenu()==INT_TBL_MNU_CIE_IMP){
                if (!objPerUsr.isOpcionEnabled(4162))
                {
                    butCon.setVisible(false);
                }
                if (!objPerUsr.isOpcionEnabled(4163))
                {
                    butGua.setVisible(false);                
                }
                if (!objPerUsr.isOpcionEnabled(4164))
                {
                    butCer.setVisible(false);
                }            
            }
            else{
                if (!objPerUsr.isOpcionEnabled(4383))
                {
                    butCon.setVisible(false);
                }
                if (!objPerUsr.isOpcionEnabled(4384))
                {
                    butGua.setVisible(false);                
                }
                if (!objPerUsr.isOpcionEnabled(4385))
                {
                    butCer.setVisible(false);
                }                
            }
                 
            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(true);
            objSelFecDoc.setCheckBoxChecked(false);
            objSelFecDoc.setTitulo("Fecha del documento");
            
            //Configurar objetos.
            txtCodPed.setVisible(false);
                   
            //Configurar los JTables.
            configurarTblDat();
            
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
            vecCab=new Vector(18);  //Almacena las cabeceras
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
            vecCab.add(INT_TBL_DAT_DIAPEN_CIE,"Dias.Pen.Cie.");
            vecCab.add(INT_TBL_DAT_EST_ING_IMP,"Est.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CHK_CIE_PED,"Est.Cie.");
            vecCab.add(INT_TBL_DAT_COD_USR,"Cód.Usr.");
            vecCab.add(INT_TBL_DAT_TXT_USR,"Usr.");
            vecCab.add(INT_TBL_DAT_NOM_USR,"Nom.Usr.");
            vecCab.add(INT_TBL_DAT_FEC_CIE,"Fec.Cie.");
            vecCab.add(INT_TBL_DAT_BUT,"");

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
            tcmAux.getColumn(INT_TBL_DAT_DIAPEN_CIE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST_ING_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE_PED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TXT_USR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(108);
            tcmAux.getColumn(INT_TBL_DAT_FEC_CIE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(35);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TXT_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_CIE, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
 
           //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);   
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
           
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_CIE_PED);
            vecAux.add("" + INT_TBL_DAT_BUT);
            objTblMod.addColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE_PED).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE_PED).setCellEditor(objTblCelEdiChk);  
            
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                        if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN).equals("C"))
                        {      
                            objTblCelEdiChk.setCancelarEdicion(true);
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_CIE_PED);
                        }
                        else
                        {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_CIE_PED))
                        {                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_CIE_PED);
                        }
                        else 
                        {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_CIE_PED);
                        }
                    }
                }
            });
            
            //Configurar JTable:  Botones.
            tcmAux.getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT:
                            if(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_COD_EMP).toString().equals(""))
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiBut);

            objTblCelEdiBut.addTableEditorListener(new ZafTableAdapter() 
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
                            case INT_TBL_DAT_BUT:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString().equals("")))
                                   objTblCelEdiBut.setCancelarEdicion(true);
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
                            case INT_TBL_DAT_BUT:
                                mostrarIngImp(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString(), 
                                              tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString(),
                                              tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC).toString(), 
                                              tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString() );
                                break;
                        }
                    }
                }
            }); 
            
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
                    strMsg="Código de la empresa del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número del Pedido del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_DIAPEN_CIE:
                    strMsg="Dias que tiene el pedido sin cerrarse desde el día en que arribo.";
                    break;                
                case INT_TBL_DAT_EST_ING_IMP:
                    strMsg="Estado del ingreso por Importación.";
                    break;
                case INT_TBL_DAT_CHK_CIE_PED:
                    strMsg="Indica si Ingreso por Importación ha sido cerrado";
                    break;                 
                case INT_TBL_DAT_COD_USR:
                    strMsg="Código del usuario que realizó el cierre del Ingreso por Importación";
                    break;
               case INT_TBL_DAT_TXT_USR:
                    strMsg="Usuario que realizó el cierre del Ingreso por Importación";
                    break;     
               case INT_TBL_DAT_NOM_USR:
                    strMsg="Nombre del usuario que realizó el cierre del Ingreso por Importación";
                    break;    
               case INT_TBL_DAT_FEC_CIE:
                    strMsg="Fecha de cierre del Ingreso por Importación";
                    break;   
               case INT_TBL_DAT_BUT:
                    strMsg="Muestra formulario del ingreso por importación";
                    break;
               default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos".
     */
    private boolean configurarPedidos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+=" FROM tbm_cabMovInv AS a1";
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc ";
            strSQL+=" WHERE a1.st_Reg='A' ";
            //Codigo Tipo de Documento
            strSQL+=" AND a1.co_tipDoc IN ( select co_tipDoc from ";
            if(objParSis.getCodigoUsuario()==1) {  strSQL+="  tbr_tipDocPrg ";  }  else  { strSQL+="  tbr_tipDocUsr ";   }
            strSQL+="     where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_mnu="+objParSis.getCodigoMenu();
            if(objParSis.getCodigoUsuario()!=1) {    strSQL+="  and co_usr="+objParSis.getCodigoUsuario();             }
            strSQL+=" )";
            
            //Se muestran pedidos arribados, en proceso de conteo, cerrados y pedidos antiguos adaptados al nuevo esquema.        
            strSQL+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) NOT IN ('N', 'P') ";
            
            /*Filtros*/
            if(!chkMosDocCer.isSelected()){
                //Si no se selecciona mostrar pedidos cerrados, no se mostrarán los pedidos cerrados.
                strSQL+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) NOT IN ('C') ";
            }
           
            strSQL+=" ORDER BY a1.fe_doc, a1.tx_numDoc2";
            
            //Ocultar columnas.
            int intColOcu[]=new int[4];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            
            vcoPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPed.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarPedidos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPed.setCampoBusqueda(5);
                    vcoPed.setVisible(true);
                    if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp = Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp = Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp = Integer.parseInt(vcoPed.getValueAt(3));
                    }
                    break;
                 case 1: //Búsqueda directa por "Código Pedido".
                    if (vcoPed.buscar("a1.co_doc", txtCodPed.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(3);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                        }
                        else
                        {
                            txtCodPed.setText(strCodPed); 
                        }
                    }
                    break;
               
                case 2: //Búsqueda directa por "Número Documento".
                    if (vcoPed.buscar("a1.ne_numDoc", txtNumDocIngImp.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(4);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                        }
                        else
                        {
                            txtNumDocIngImp.setText(strNumPed); 
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoPed.buscar("a1.tx_numDoc2", txtPedIngImp.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(5);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                        }
                        else
                        {
                            txtPedIngImp.setText(strPedImp);
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
    
    private void mostrarIngImp(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        Compras.ZafCom77.ZafCom77 objCom_77=new Compras.ZafCom77.ZafCom77(objParSis, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc));
        this.getParent().add(objCom_77,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objCom_77.setVisible(true);
        objCom_77=null;
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
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        chkMosDocCer = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
        optTod.setBounds(10, 10, 400, 14);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 30, 400, 14);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("Cliente");
        panFil.add(lblPed);
        lblPed.setBounds(40, 60, 70, 20);

        txtCodPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPedActionPerformed(evt);
            }
        });
        txtCodPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPedFocusLost(evt);
            }
        });
        panFil.add(txtCodPed);
        txtCodPed.setBounds(80, 60, 45, 20);

        txtNumDocIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocIngImpActionPerformed(evt);
            }
        });
        txtNumDocIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusLost(evt);
            }
        });
        panFil.add(txtNumDocIngImp);
        txtNumDocIngImp.setBounds(125, 60, 80, 20);

        txtPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPedIngImpActionPerformed(evt);
            }
        });
        txtPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusLost(evt);
            }
        });
        panFil.add(txtPedIngImp);
        txtPedIngImp.setBounds(205, 60, 440, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panFil.add(butPedImp);
        butPedImp.setBounds(645, 60, 20, 20);

        chkMosDocCer.setText("Mostrar pedidos cerrados.");
        chkMosDocCer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkMosDocCerItemStateChanged(evt);
            }
        });
        chkMosDocCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocCerActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocCer);
        chkMosDocCer.setBounds(40, 100, 210, 20);

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

        butGua.setText("Guardar");
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
            txtCodPed.setText("");
            txtNumDocIngImp.setText("");
            txtPedIngImp.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        consultar();
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

    private void txtCodPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPedActionPerformed
        txtCodPed.transferFocus();
    }//GEN-LAST:event_txtCodPedActionPerformed

    private void txtCodPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusGained
        strCodPed=txtCodPed.getText();
        txtCodPed.selectAll();
    }//GEN-LAST:event_txtCodPedFocusGained

    private void txtCodPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPed.getText().equalsIgnoreCase(strCodPed))
        {
            if (txtCodPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(1);
            }
        }
        else
        txtCodPed.setText(strCodPed);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodPedFocusLost

    private void txtNumDocIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocIngImpActionPerformed
        txtNumDocIngImp.transferFocus();
    }//GEN-LAST:event_txtNumDocIngImpActionPerformed

    private void txtNumDocIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusGained
        strNumPed=txtNumDocIngImp.getText();
        txtNumDocIngImp.selectAll();
    }//GEN-LAST:event_txtNumDocIngImpFocusGained

    private void txtNumDocIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusLost
        if (!txtNumDocIngImp.getText().equalsIgnoreCase(strNumPed))
        {
            if (txtNumDocIngImp.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(2);
            }
        }
        else
            txtNumDocIngImp.setText(strNumPed);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNumDocIngImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNumDocIngImpFocusLost

    private void txtPedIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPedIngImpActionPerformed
        txtPedIngImp.transferFocus();
    }//GEN-LAST:event_txtPedIngImpActionPerformed

    private void txtPedIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusGained
        strPedImp=txtPedIngImp.getText();
        txtPedIngImp.selectAll();
    }//GEN-LAST:event_txtPedIngImpFocusGained

    private void txtPedIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtPedIngImp.getText().equalsIgnoreCase(strPedImp))
        {
            if (txtPedIngImp.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(3);
            }
        }
        else
            txtPedIngImp.setText(strPedImp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtPedIngImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtPedIngImpFocusLost

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        configurarPedidos();
        mostrarPedidos(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butPedImpActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(isCamVal())
                {
                    if (guardar())
                    {
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                        cargarDetReg();     //consultar(); 
                    }
                    else
                    {
                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nVerifique los pedidos a cerrar.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                        cargarDetReg(); 
                    }
                }
                else
                    mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        
    }//GEN-LAST:event_butGuaActionPerformed

    private void chkMosDocCerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkMosDocCerItemStateChanged
        if(chkMosDocCer.isSelected())
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_chkMosDocCerItemStateChanged

    private void chkMosDocCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocCerActionPerformed
        if(chkMosDocCer.isSelected())
        {
            objTblMod.removeAllRows();
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_TXT_USR, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_USR, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_FEC_CIE, tblDat);
            //objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT, tblDat);
        }
        else
        {
            objTblMod.removeAllRows();
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TXT_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_CIE, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT, tblDat);
        }
    }//GEN-LAST:event_chkMosDocCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butPedImp;
    private javax.swing.JCheckBox chkMosDocCer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPed;
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
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNumDocIngImp;
    private javax.swing.JTextField txtPedIngImp;
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
    
    private void consultar()
    {
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
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            strAux="";
            if (con!=null)
            {
                stm=con.createStatement();
                //<editor-fold defaultstate="collapsed" desc="/* Filtros */">
                //Fecha de documento
                if(objSelFecDoc.isCheckBoxChecked())
                {
                    switch (objSelFecDoc.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                //Codigo Tipo de Documento
                strAux+=" AND a1.co_tipDoc IN ( select co_tipDoc from ";
                if(objParSis.getCodigoUsuario()==1) {  strAux+="  tbr_tipDocPrg ";  }  else  { strAux+="  tbr_tipDocUsr ";   }
                strAux+="     where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_mnu="+objParSis.getCodigoMenu();
                if(objParSis.getCodigoUsuario()!=1) {    strAux+="  and co_usr="+objParSis.getCodigoUsuario();             }
                strAux+=" )";
                
                /*Filtros*/
                //Se muestran pedidos arribados, en proceso de conteo, cerrados y pedidos antiguos adaptados al nuevo esquema.        
                strAux+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) NOT IN ('N', 'P' ";
                if(!chkMosDocCer.isSelected()){
                    strAux+=" , 'C' "; //Si no se selecciona mostrar pedidos cerrados, se ocultarán los pedidos cerrados.
                }
                strAux+=" ) ";
                
                //Búsqueda por Pedido
                if(txtCodPed.getText().length()>0){
                    strAux+=" AND a1.co_emp=" + intCodEmpIngImp + " AND a1.co_loc=" + intCodLocIngImp + " AND a1.co_tipDoc=" + intCodTipDocIngImp + " AND a1.co_doc=" + txtCodPed.getText() + "";
                }
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.* , CASE WHEN a1.st_ingImp != 'C' THEN (current_date - a1.fe_ingIngImp) END  as ne_diaCer ";
                strSQL+=" FROM (";
                strSQL+="         SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+="              , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END as st_ingImp";
                strSQL+="              , CAST(CASE WHEN a1.fe_ingIngImp IS NULL THEN a1.fe_doc ELSE a1.fe_ingIngImp END AS DATE) AS fe_ingIngImp";
                strSQL+="              , a1.fe_ultModIngImp, a1.co_usrUltModIngImp, a3.tx_usr, a3.tx_nom as tx_nomUsr";
                strSQL+="         FROM tbm_cabMovInv AS a1 ";
                strSQL+="         INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc";
                strSQL+="         LEFT OUTER JOIN tbm_usr AS a3 ON a3.co_usr=a1.co_usrUltModIngImp";
                strSQL+="         WHERE a1.st_Reg in ('A') ";
                strSQL+="         "+strAux; //Contiene los filtros de arriba
                strSQL+=" ) as a1";
                strSQL+=" ORDER BY a1.st_ingImp, a1.fe_ingIngImp, a1.tx_numDoc2";
                System.out.println("cierrePedido: "+strSQL);
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
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));         //co_empIngImp
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));         //co_locIngImp
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));      //co_tipDocIngImp
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));      //tx_desCorTipDocIngImp
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));      //tx_desCorTipDocIngImp
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));         //co_docIngImp
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDoc"));      //Núm.Doc.
                        vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numDoc2"));     //Núm.Ped.
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));         //fe_doc
                        vecReg.add(INT_TBL_DAT_DIAPEN_CIE,      rst.getString("ne_diaCer"));      //dias pendiente de cerrar pedido desde que arribo. 
                        vecReg.add(INT_TBL_DAT_EST_ING_IMP,     rst.getString("st_ingImp"));      //Estado del ingreso por importación.
                        vecReg.add(INT_TBL_DAT_CHK_CIE_PED,     null);                            //Est.Cie.
                        vecReg.add(INT_TBL_DAT_COD_USR,         rst.getString("co_usrUltModIngImp"));//co_usr que realizó el cierre.
                        vecReg.add(INT_TBL_DAT_TXT_USR,         rst.getString("tx_usr"));         //Usuario que realizó conteo.
                        vecReg.add(INT_TBL_DAT_NOM_USR,         rst.getString("tx_nomUsr"));      //nombre del usuario que realizó conteo.
                        vecReg.add(INT_TBL_DAT_FEC_CIE,         rst.getString("fe_ultModIngImp"));//Fecha de cierre
                        vecReg.add(INT_TBL_DAT_BUT,     null);                                    //Btn.Ing.Imp.

                        if ( rst.getString("st_ingImp").equals("C") ) 
                        {
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_CIE_PED);
                            vecReg.setElementAt(rst.getString("st_ingImp"), INT_TBL_DAT_LIN);
                        }
                        
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
 
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        for (int i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("M"))
            {
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_CIE_PED))
                {
                    return true;
                }
            }
        }
        return false;
    }
  
    /**
     * Esta función guarda el cierre del pedido en la base de datos.
     * @return true: Si se pudo cerrar el pedido.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar()
    {
        boolean blnGua=true;
        boolean blnRes=false;
        try
        {
            lblMsgSis.setText("Guardando datos...");
            pgrSis.setIndeterminate(true);
            strMsg="";
            strMsjSolPen="";
            
            for (int i=0; i<objTblMod.getRowCountTrue();i++)
            {
                if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("M"))
                {
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_CIE_PED))
                    {
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        con.setAutoCommit(false);
                        if (con!=null)
                        {
                            if (validaSolicitudesTransferenciasPendientes(i))
                            {
                                if (validaEstadoPedido(i))
                                {
                                    if (generarAjusteInventario(i))
                                    {
                                        blnRes=true; 
                                        con.commit();
                                    }
                                    else
                                        con.rollback(); 
                                }
                                else
                                     con.rollback(); 
                            }
                            else
                                 con.rollback(); 

                            if(!blnRes)
                            {
                                blnGua=false;
                            }
                            con.close();
                            con=null;
                        }
                    }
                }
            }
            //Mensajes
            if(strMsjSolPen.length()>0)
            {
                strMsg ="<html> Los siguientes pedidos tienen solicitudes de transferencias pendientes de autorizar. <BR><BR>" ;
                strMsg+=" <table BORDER=1><tr><td> Tipo Documento </td> <td> Núm.Ped. </td>";
                strMsg+=""+ strMsjSolPen + "  ";
                strMsg+=" </table><BR>";
                strMsg+="No se puede realizar esta operación. <html>";
                String strTit="Mensaje del sistema Zafiro";
                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);        
            }

            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);    
            pgrSis.setIndeterminate(false);
            //Inicializo el estado de las filas.
            objTblMod.initRowsState();
            objTblMod.setDataModelChanged(false);
        }
        catch (java.sql.SQLException e)
        {
            blnGua=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnGua=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnGua;
    }

    /**
     * Esta función valida que no existan solicitudes de transferencias pendientes de autorizar para proceder a realizar el cierre del pedido.
     * @return true: Si no existen solicitudes de transferencias pendientes de autorizar.
     * <BR>false: En el caso contrario.
     */
    private boolean validaSolicitudesTransferenciasPendientes(int fila)
    {
        boolean blnRes=false;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
    
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a.co_EmpRelCabSolTraInv, a.co_locRelCabSolTraInv, a.co_tipDocRelCabSolTraInv,  a.co_docRelCabSolTraInv, a1.st_aut";
                strSQL+=" FROM tbr_CabSolTraInvCabMovInv as a";
                strSQL+=" INNER JOIN tbm_CabSolTraInv as a1 ON (a.co_EmpRelCabSolTraInv=a1.co_emp AND  a.co_locRelCabSolTraInv=a1.co_loc ";
                strSQL+="                                       AND a.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a.co_docRelCabSolTraInv=a1.co_doc)";
                strSQL+=" WHERE a1.st_aut IS NULL AND (CASE WHEN a1.st_conInv IS NULL THEN 'P' ELSE a1.st_conInv END ) IN ('P') ";
                strSQL+=" AND a.co_EmpRelCabMovInv=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_EMP);        //co_emp
                strSQL+=" AND a.co_locRelCabMovInv=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_LOC);        //co_loc  
                strSQL+=" AND a.co_tipDocRelCabMovInv=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC); //co_tipDoc  
                strSQL+=" AND a.co_docRelCabMovInv=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_DOC);        //co_doc  
                rst=stm.executeQuery(strSQL);
                if(!rst.next())
                {
                   blnRes=true;
                }
                
                if(!blnRes)
                {
                   strMsjSolPen+="<tr> <td>" + tblDat.getValueAt(fila, INT_TBL_DAT_DES_COR_TIP_DOC)  + " </td>";
                   strMsjSolPen+="     <td>" + tblDat.getValueAt(fila, INT_TBL_DAT_NUM_PED)  + " </td>";
                   strMsjSolPen+="</tr> ";
                }
                
                rst.close();
                rst=null;
                stm.close();
                stm=null;
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
    
        
    /**
     * Esta función valida que el pedido no haya sido cerrado.
     * @return true: Si el pedido esta abierto, se puede continuar con el proceso de cerrar pedido.
     * <BR>false: En el caso contrario.
     */
    private boolean validaEstadoPedido(int fila)
    {
        boolean blnRes=false;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
    
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT * FROM tbm_cabMovInv as a1";
                strSQL+=" WHERE (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END ) IN ('N', 'P', 'C')";
                strSQL+=" AND a1.co_Emp=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_EMP);        //co_emp
                strSQL+=" AND a1.co_loc=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_LOC);        //co_loc  
                strSQL+=" AND a1.co_tipDoc=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC); //co_tipDoc  
                strSQL+=" AND a1.co_doc=" + tblDat.getValueAt(fila, INT_TBL_DAT_COD_DOC);        //co_doc  
                rst=stm.executeQuery(strSQL);
                if(!rst.next())
                {
                   blnRes=true;
                }

                rst.close();
                rst=null;
                stm.close();
                stm=null;
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
    

   
    /**
     * Esta función permite generar ajustes de inventario del pedido a cerrar.
     * @return true: Si se pudieron realizar los ajustes necesarios al pedido.
     * <BR>false: En el caso contrario.
     */
    private boolean generarAjusteInventario(int fila)
    {
        boolean blnRes=false;
        int intCodTipDocAju=0;
    
        try
        {
            if (con!=null)
            {
                switch(Integer.parseInt(tblDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString()))
                {
                    case INT_COD_TIPDOC_INIMPO:
                        intCodTipDocAju=objImp.INT_COD_TIP_DOC_AJU_ING_IMP;
                        break;
                    case INT_COD_TIPDOC_INBOCL:
                        intCodTipDocAju=objImp.INT_COD_TIP_DOC_AJU_COM_LOC;
                        break;                        
                }

                objAjuInv=new ZafAjuInv(objParSis, con, Integer.parseInt(tblDat.getValueAt(fila, INT_TBL_DAT_COD_EMP).toString())
                        , Integer.parseInt(tblDat.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString())
                        , Integer.parseInt(tblDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString())
                        , Integer.parseInt(tblDat.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString())
                        , intCodTipDocAju, objImp.INT_COD_MNU_PRG_AJU_INV, 'n'
                        );                

                if(objAjuInv.isAjuPro())
                {
                    blnRes=true;
                    //El estado st_cieAjuInvIngImp se actualizará en la librería ZafImp.ZafAjuInv (Ajuste de Inventario). // S = Si tiene ajuste, N = Cuando no tiene ajuste.
                    //El estado st_IngImp se actualizará en la librería ZafImp.ZafAjuInv (Ajuste de Inventario). // C= Cerrado.
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
}