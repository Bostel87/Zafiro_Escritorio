/*
 * ZafSegAjuInv.java
 *
 * Created on 18 de Enero del 2017, 17h08
 */
package Librerias.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafSegAjuInv extends javax.swing.JDialog 
{
    //Constantes
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;               //Linea.
    private static final int INT_TBL_DAT_CODEMP = 1;            //Código de Empresa.
    private static final int INT_TBL_DAT_CODLOC = 2;            //Código de Local.
    private static final int INT_TBL_DAT_CODTIPDOC = 3;         //Código de Tipo Documento.
    private static final int INT_TBL_DAT_DESCORTIPDOC = 4;      //Descripción Corta Tipo Documento.
    private static final int INT_TBL_DAT_DESLARTIPDOC = 5;      //Descripción Larga Tipo Documento.
    private static final int INT_TBL_DAT_CODDOC = 6;            //Código de documento.
    private static final int INT_TBL_DAT_NUMDOC = 7;            //Número de documento.
    private static final int INT_TBL_DAT_NUMPED = 8;            //Número de Pedido.
    private static final int INT_TBL_DAT_FECDOC = 9;            //Fecha del documento.
    private static final int INT_TBL_DAT_CODITMMAE = 10;        //Código de maestro del item.
    private static final int INT_TBL_DAT_CAN_AJU= 11;           //Cantidad de Ajuste (Ingreso/Egreso)
    private static final int INT_TBL_DAT_CAN_SOL= 12;           //Cantidad de Solicitudes de transferencia (Ajuste)
    private static final int INT_TBL_DAT_CAN_TRS= 13;           //Cantidad de Transferencias (Ajuste)
    private static final int INT_TBL_DAT_EST_AUT= 14;           //Estado de Autorización del documento de ajuste.
    private static final int INT_TBL_DAT_BUT = 15;              //Botón "Documentos de Ajuste".
    
    //JTable: Tabla de Detalle Ajustes
    private static final int INT_TBL_DET_LIN = 0;               //Linea.
    private static final int INT_TBL_DET_CODEMP = 1;            //Código de Empresa.
    private static final int INT_TBL_DET_CODLOC = 2;            //Código de Local.
    private static final int INT_TBL_DET_CODTIPDOC = 3;         //Código de Tipo Documento.
    private static final int INT_TBL_DET_DESCORTIPDOC = 4;      //Descripción Corta Tipo Documento.
    private static final int INT_TBL_DET_DESLARTIPDOC = 5;      //Descripción Larga Tipo Documento.
    private static final int INT_TBL_DET_CODDOC = 6;            //Código de documento.
    private static final int INT_TBL_DET_NUMDOC = 7;            //Número de documento.
    private static final int INT_TBL_DET_FECDOC = 8;            //Fecha del documento.
    private static final int INT_TBL_DET_CAN= 9;                //Cantidad.
    private static final int INT_TBL_DET_EST_AUT= 10;           //Estado de Autorización del documento.
    private static final int INT_TBL_DET_BUT = 11;              //Botón "Documentos".
    
    //Constantes: Código de Menú.
    private static final int INT_COD_MNU_TRANSFER=779;                  //Solicitudes de transferencias: Importaciones

    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;    
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButDet;
    private ZafTblCelEdiButGen objTblCelEdiButGen, objTblCelEdiButGenDet;
    private ZafTblFilCab objTblFilCab;
    private ZafThreadGUI objThrGUI;
    private ZafTblMod objTblModDat, objTblModDet;                                             //Modelo de Jtable.
    private ZafMouMotAda objMouMotAdaDat;                                       //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                                //JTable de totales.
    private ZafAjuInv objAjuInv;   
    private ZafImp objImp;
    private boolean blnDetAju;                                                  //true: Continua la ejecución del hilo.
    private Vector vecDat, vecCab, vecReg;
    private String strSQL, strAux;
    
    private String strVer="v0.1.1";                                             //Versión del Programa.  

    /**
     * 
     * @param parent Formulario Padre.
     * @param modal Indica si se bloquean las demas ventanas o no.
     * @param obj Parametros del sistema.
     * @param sentencia Query que contiene datos de documentos de ajustes.
     * @param blnDetAju true: Indica si debe presentarse la informacion del documento de ajuste por detalle. false: Presenta solo datos de cabecera.
     */
    public ZafSegAjuInv( java.awt.Frame parent, boolean modal, ZafParSis obj, String sentencia, boolean blnDetAju)  
    {
        super(parent, modal);
        
        //Inicializar objetos.
        this.objParSis = obj;

        strSQL=sentencia;
        this.blnDetAju=blnDetAju;

        initComponents();
        if (!configurarFrm()) 
        {
            exitForm();
        }
        consultar();
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelRenButDet = new ZafTblCelRenBut();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            objTblCelEdiButGenDet = new ZafTblCelEdiButGen();
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText("Listado de documentos de ajustes");
            
            //Configurar Jtable
            configurarTblDat();            
            configurarTblDetAju();
            
            if(!blnDetAju)
            {
                panDet.removeAll();
            }
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC,"Cód.Tip.Doc.");  
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUMPED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODITMMAE,"Cod.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_CAN_AJU,"Cantidad");
            vecCab.add(INT_TBL_DAT_CAN_SOL,"Can.Sol.");
            vecCab.add(INT_TBL_DAT_CAN_TRS,"Can.Trs.");
            vecCab.add(INT_TBL_DAT_EST_AUT,"Est.Aut.");
            vecCab.add(INT_TBL_DAT_BUT,"");
             
            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);
            
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
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUMPED).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CODITMMAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_EST_AUT).setPreferredWidth(50);            
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(20);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLisAju());
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODITMMAE, tblDat);
            
            if(!blnDetAju)
            {
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CAN_AJU, tblDat);
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CAN_SOL, tblDat);
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CAN_TRS, tblDat);
            }
           
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDat=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Formato Columna Estado de Autorización.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_EST_AUT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
            
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
                            if (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP).toString().equals("")) 
                                objTblCelRenBut.setText("");
                            else
                             objTblCelRenBut.setText("...");
                        break;
                    }
                }
            });
            
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);

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
                            case INT_TBL_DAT_BUT:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
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
                                mostrarFormularioDocumentoAjuste(Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP).toString()), 
                                                    Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC).toString()),
                                                    Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString()), 
                                                    Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC).toString()) );
                                break;
                        }
                    }
                }
            });
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
                        
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
 
    /**
     * ToolTips de Jtable: Datos.
     */
     private class ZafMouMotAda extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODEMP:
                    strMsg="Código de la empresa";
                    break;                
                case INT_TBL_DAT_CODLOC:
                    strMsg="Código del local";
                    break;        
                case INT_TBL_DAT_CODTIPDOC:
                    strMsg="Código del tipo de documento";
                    break;        
                case INT_TBL_DAT_DESCORTIPDOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;   
                case INT_TBL_DAT_DESLARTIPDOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;                    
                case INT_TBL_DAT_CODDOC:
                    strMsg="Código del documento";
                    break;                
                case INT_TBL_DAT_NUMDOC:
                    strMsg="Número del documento";
                    break;   
                case INT_TBL_DAT_NUMPED:
                    strMsg="Número de Pedido";
                    break;
                case INT_TBL_DAT_FECDOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_CODITMMAE:
                    strMsg="Código maestro del ítem";
                    break;
                case INT_TBL_DAT_CAN_AJU:
                    strMsg="Cantidad de Ajuste (+)Ingreso | Egreso(-) ";
                    break;
                case INT_TBL_DAT_CAN_SOL:
                    strMsg="Cantidad de Solicitudes de transferencias del Ajuste";
                    break;
                case INT_TBL_DAT_CAN_TRS:
                    strMsg="Cantidad de Transferencias del Ajuste";
                    break;                    
                case INT_TBL_DAT_EST_AUT:
                    strMsg=" Estado de Autorización. P: Pendiente; E: En proceso; A: Autorizado; D: Denegado. ";
                    break;                            
                case INT_TBL_DAT_BUT:
                    strMsg="Muestra documento de ajuste";
                    break;   
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="/* Botón muestra "Documento de Ajuste" */">
    private void mostrarFormularioDocumentoAjuste(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        Connection conLoc; 
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                //Se carga el programa Ajuste de Inventario
                objAjuInv = new ZafAjuInv( javax.swing.JOptionPane.getFrameForComponent(this), objParSis, conLoc, CodEmp, CodLoc, CodTipDoc, CodDoc, objImp.INT_COD_MNU_PRG_AJU_INV, 'c'); 
                objAjuInv.show();
                //this.getParent().add(objAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                //objAjuInv.setVisible(true);
                objAjuInv=null;    
                conLoc.close();
                conLoc=null;     
            }
        }        
        catch (java.sql.SQLException e)  {  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e);   }
    }
    //</editor-fold>
    
    /**
     * Esta función configura el JTable "tblDet".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDetAju()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(12);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DET_LIN,"");
            vecCab.add(INT_TBL_DET_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DET_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DET_CODTIPDOC,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_DET_DESCORTIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DET_DESLARTIPDOC,"Tipo de documento");
            vecCab.add(INT_TBL_DET_CODDOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DET_NUMDOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DET_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DET_CAN,"Cantidad");
            vecCab.add(INT_TBL_DET_EST_AUT,"Est.Aut.");
            vecCab.add(INT_TBL_DET_BUT,"");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModDet=new ZafTblMod();
            objTblModDet.setHeader(vecCab);
            tblDet.setModel(objTblModDet);
            //Configurar JTable: Establecer tipo de selección.
            tblDet.setRowSelectionAllowed(true);
            tblDet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDet);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDet.getColumnModel();
            tcmAux.getColumn(INT_TBL_DET_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_CODEMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_CODLOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_CODTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_DESCORTIPDOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_DESLARTIPDOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_DET_CODDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_NUMDOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DET_FECDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_CAN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_EST_AUT).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DET_BUT).setPreferredWidth(20);
   
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDet.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_CODEMP, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_CODLOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_CODTIPDOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_CODDOC, tblDet);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDet.getTableHeader().addMouseMotionListener(new ZafMouMotAdaDet());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDet);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DET_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DET_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDet);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DET_BUT);
            objTblModDet.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable:  Botones.
            tcmAux.getColumn(INT_TBL_DET_BUT).setCellRenderer(objTblCelRenButDet);
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DET_BUT).setCellEditor(objTblCelEdiButGenDet);

            objTblCelEdiButGenDet.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    panDoc.removeAll();
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblDet.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DET_BUT:
                                mostrarFormularioDocumento(tblDet.getValueAt(tblDet.getSelectedRow(), INT_TBL_DET_CODEMP).toString(), 
                                                    tblDet.getValueAt(tblDet.getSelectedRow(), INT_TBL_DET_CODLOC).toString(),
                                                    tblDet.getValueAt(tblDet.getSelectedRow(), INT_TBL_DET_CODTIPDOC).toString(), 
                                                    tblDet.getValueAt(tblDet.getSelectedRow(), INT_TBL_DET_CODDOC).toString() );
                                break;
                        }
                    }
                }
            });
            
            //Habilitar edición tabla.
            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);
            
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
     * ToolTips de Jtable: Detalle Ajuste.
     */
     private class ZafMouMotAdaDet extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DET_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DET_CODEMP:
                    strMsg="Código de la empresa";
                    break;                
                case INT_TBL_DET_CODLOC:
                    strMsg="Código del local";
                    break;        
                case INT_TBL_DET_CODTIPDOC:
                    strMsg="Código del tipo de documento";
                    break;        
                case INT_TBL_DET_DESCORTIPDOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;   
                case INT_TBL_DET_DESLARTIPDOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;                    
                case INT_TBL_DET_CODDOC:
                    strMsg="Código del documento";
                    break;                
                case INT_TBL_DET_NUMDOC:
                    strMsg="Número del documento";
                    break;   
                case INT_TBL_DET_FECDOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DET_CAN:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_DET_EST_AUT:
                    strMsg=" Estado de Autorización. P: Pendiente; E: En proceso; A: Autorizado; D: Denegado. ";
                    break;                            
                case INT_TBL_DET_BUT:
                    strMsg="Muestra documento";
                    break;   
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLisAju implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            if (!lsm.isSelectionEmpty())
            {
                if (chkDetAju.isSelected())
                    cargarDetAju();
                else
                    objTblModDet.removeAllRows();
            }
        }
    }
    
    private void mostrarFormularioDocumento(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        String strCodMnu = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.tx_Acc, a2.co_tipDoc, a1.co_mnu";
                strSQL+=" FROM tbm_mnusis as a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_mnu=a2.co_mnu)";
                strSQL+=" WHERE a2.co_mnu IN ("+INT_COD_MNU_TRANSFER+", "+objImp.INT_COD_MNU_PRG_SOTRINI+", "+objImp.INT_COD_MNU_PRG_SOTRINC+") ";
                strSQL+=" AND a2.co_Emp= " + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal(); 
                strSQL+=" AND a2.co_tipdoc= " + strCodTipDoc;
                strSQL+=" GROUP BY a1.tx_Acc, a2.co_tipDoc, a1.co_mnu";
                
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    strCodMnu = rst.getString("co_mnu");
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
            invocarClase(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, Integer.valueOf(strCodMnu));
        }
        catch (Exception Evt) 
        {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        
    }
    
    private void invocarClase(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, int intCodMnu) 
    {
        switch (intCodMnu)
        {
            case 779:
                Compras.ZafCom20.ZafCom20 objTrs = new Compras.ZafCom20.ZafCom20(objParSis, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc)
                                                                               , Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)) ; 
                panDoc.add(objTrs, java.awt.BorderLayout.CENTER);
                objTrs.show();
                tabFrm.setSelectedIndex(1);
                objTrs=null;
                break;
                
            case 3456:
                //Compras.ZafCom91.ZafCom91 objSol = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ; 
                Importaciones.ZafImp21.ZafImp21 objSol = new Importaciones.ZafImp21.ZafImp21(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ; 
                panDoc.add(objSol, java.awt.BorderLayout.CENTER);
                objSol.show();
                tabFrm.setSelectedIndex(1);
                objSol=null;
                break;
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        sppFrm = new javax.swing.JSplitPane();
        panCab = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panDet = new javax.swing.JPanel();
        chkDetAju = new javax.swing.JCheckBox();
        spnDet = new javax.swing.JScrollPane();
        tblDet = new javax.swing.JTable();
        panDoc = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panCer = new javax.swing.JPanel();
        butCer = new javax.swing.JButton();
        panBor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setPreferredSize(new java.awt.Dimension(600, 471));
        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de documentos de ajustes de inventario");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(690, 500));

        panDat.setPreferredSize(new java.awt.Dimension(454, 923));
        panDat.setLayout(new java.awt.BorderLayout());

        sppFrm.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppFrm.setPreferredSize(new java.awt.Dimension(454, 923));

        panCab.setMinimumSize(new java.awt.Dimension(267, 150));
        panCab.setPreferredSize(new java.awt.Dimension(452, 500));
        panCab.setLayout(new java.awt.BorderLayout());

        spnDat.setAutoscrolls(true);
        spnDat.setPreferredSize(new java.awt.Dimension(452, 200));

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

        panCab.add(spnDat, java.awt.BorderLayout.CENTER);

        sppFrm.setTopComponent(panCab);

        panDet.setPreferredSize(new java.awt.Dimension(452, 422));
        panDet.setLayout(new java.awt.BorderLayout());

        chkDetAju.setText("Mostrar el movimiento del documento seleccionado"); // NOI18N
        chkDetAju.setPreferredSize(new java.awt.Dimension(269, 20));
        chkDetAju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDetAjuActionPerformed(evt);
            }
        });
        panDet.add(chkDetAju, java.awt.BorderLayout.NORTH);

        tblDet.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet.setViewportView(tblDet);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        sppFrm.setBottomComponent(panDet);

        panDat.add(sppFrm, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Datos", panDat);

        panDoc.setPreferredSize(new java.awt.Dimension(600, 402));
        panDoc.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("", panDoc);

        PanFrm.add(tabFrm, java.awt.BorderLayout.LINE_START);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butCer.setText("Cerrar");
        butCer.setToolTipText("Salir");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panCer.add(butCer);

        panBot.add(panCer, java.awt.BorderLayout.EAST);

        panBor.setLayout(new java.awt.BorderLayout());
        panBot.add(panBor, java.awt.BorderLayout.WEST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-716)/2, (screenSize.height-489)/2, 716, 489);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        dispose();
    }//GEN-LAST:event_butCerActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void chkDetAjuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDetAjuActionPerformed
        if (chkDetAju.isSelected()){
            if(objTblModDat.getRowCount()>0)
            {
                cargarDetAju();
            }
            else
                chkDetAju.setSelected(false);
        }
        else{
            objTblModDet.removeAllRows();
        }
    }//GEN-LAST:event_chkDetAjuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCer;
    private javax.swing.JCheckBox chkDetAju;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBor;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCer;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDoc;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JSplitPane sppFrm;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDet;
    // End of variables declaration//GEN-END:variables

    /*Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").*/
    private void consultar() 
    {
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
    }
    
        /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
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
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    
    private void exitForm() 
    {
        dispose();
    } 
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();  
                //System.out.println("cargarDetReg.ZafImpDocAju: "+strSQL);   
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CODEMP,       rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_CODLOC,       rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_CODTIPDOC,    rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DESCORTIPDOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DESLARTIPDOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_CODDOC,       rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUMDOC,       rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_NUMPED,       rst.getString("tx_numDoc2"));
                    vecReg.add(INT_TBL_DAT_FECDOC,       rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_CODITMMAE,    rst.getString("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_CAN_AJU,      rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_CAN_SOL,      rst.getString("nd_canSol"));
                    vecReg.add(INT_TBL_DAT_CAN_TRS,      rst.getString("nd_canTrs"));
                    vecReg.add(INT_TBL_DAT_EST_AUT,      rst.getString("st_Aut"));
                    vecReg.add(INT_TBL_DAT_BUT,"");
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                vecDat.clear();
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
    
       /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetAju()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();  
                strSQL ="";
                strSQL+=" SELECT a1.co_Emp, a1.co_loc, a1.co_tipDoc, a1.co_Doc, a2.tx_DesCor, a2.tx_desLar, a1.ne_numDoc, a1.fe_Doc, a1.st_Aut, a1.nd_can"; 
                strSQL+=" FROM (";   
                strSQL+="       SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.ne_numDoc, a.fe_Doc, a1.co_reg, a3.co_itmMae, a1.nd_Can ";      
                strSQL+=" 	   , CASE WHEN a.st_aut IS NULL THEN 'P' ELSE a.st_aut END as st_aut "; 
                strSQL+="        FROM (";
                strSQL+=" 	      tbm_CabSolTraInv as a INNER JOIN tbm_cfgtipdocutiproaut as x ";
                strSQL+=" 	      ON (a.co_emp=x.co_emp AND a.co_loc=x.co_loc AND a.co_tipDoc=x.co_tipDoc AND x.co_Cfg in (2002, 2003) AND a.st_Reg IN ('A')) ";
                strSQL+="        )";
                strSQL+="       INNER JOIN tbr_cabSolTraInvCabMovInv as a2"; 
                strSQL+="       ON (a2.co_EmpRelCabSolTraInv=a.co_emp AND a2.co_locRelCabSolTraInv=a.co_loc AND a2.co_tipDocRelCabSolTraInv=a.co_TipDoc AND a2.co_docRelCabSolTraInv=a.co_doc ";
                strSQL+="       AND a2.co_empRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP).toString();
                strSQL+="       AND a2.co_locRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC).toString();
                strSQL+="       AND a2.co_tipDocRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString();
                strSQL+="       AND a2.co_docRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC).toString()+" )";
                strSQL+="       INNER JOIN tbm_detSolTraInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_Doc )";   
                strSQL+="       INNER JOIN tbm_equInv as a3 ON (a3.co_emp=a.co_emp AND a3.co_itm=a1.co_itm) ";   
                strSQL+="       WHERE a3.co_itmMae="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODITMMAE).toString();
                
                strSQL+="       UNION ALL";
                strSQL+="       SELECT a.co_Emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.fe_Doc, a1.co_Reg, a3.co_itmMae, ABS(a1.nd_Can) ";  
                strSQL+=" 	   , CASE WHEN a.st_aut IS NULL THEN '' ELSE a.st_aut END as st_aut ";            		           
                strSQL+="       FROM (";
                strSQL+=" 	    tbm_CabMovInv as a INNER JOIN tbm_cfgtipdocutiproaut as x ";
                strSQL+=" 	    ON (a.co_emp=x.co_emp AND a.co_loc=x.co_loc AND a.co_tipDoc=x.co_tipDoc AND  x.co_Cfg in (17, 18) AND a.st_Reg IN ('A'))"; 
                strSQL+="       )";   
                strSQL+="       INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ";
                strSQL+="       ON (a2.co_EmpRelCabSolTraInv=a.co_EmpRelCabSolTraInv AND a2.co_locRelCabSolTraInv=a.co_locRelCabSolTraInv";   
                strSQL+=" 	AND a2.co_tipDocRelCabSolTraInv=a.co_tipDocRelCabSolTraInv AND a2.co_docRelCabSolTraInv=a.co_docRelCabSolTraInv ";
                strSQL+=" 	AND a2.co_empRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP).toString();
                strSQL+=" 	AND a2.co_locRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC).toString();
                strSQL+="       AND a2.co_tipDocRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString();
                strSQL+=" 	AND a2.co_docRelCabMovInv="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC).toString()+"  )";
                strSQL+="       INNER JOIN tbm_DetMovInv as a1  ON (a1.co_Emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_Doc=a.co_Doc AND  a1.nd_Can<0 ) "; 
                strSQL+="       INNER JOIN tbm_equInv as a3 ON (a3.co_emp=a.co_emp AND a3.co_itm=a1.co_itm) ";   
                strSQL+="       WHERE a3.co_itmMae="+objTblModDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODITMMAE).toString();
                strSQL+=" ) as a1 ";
                strSQL+=" INNER JOIN tbm_CabTipDoc a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc)";
                
                System.out.println("cargarDetAju.ZafImpDocAju: "+strSQL);   
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DET_LIN,"");
                    vecReg.add(INT_TBL_DET_CODEMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DET_CODLOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DET_CODTIPDOC, rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DET_DESCORTIPDOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DET_DESLARTIPDOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DET_CODDOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DET_NUMDOC, rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DET_FECDOC,rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DET_CAN,rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DET_EST_AUT,rst.getString("st_Aut"));
                    vecReg.add(INT_TBL_DET_BUT,"");
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModDet.setData(vecDat);
                tblDet.setModel(objTblModDet);
                vecDat.clear();
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
