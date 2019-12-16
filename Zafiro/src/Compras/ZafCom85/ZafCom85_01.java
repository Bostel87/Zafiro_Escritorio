/*
 * ZafCom85_01.java
 *
 * Created on 04 de Mayo del 2016, 10:08
 */

package Compras.ZafCom85;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
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
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
/**
 *
 * @author  Rosa Zambrano
 */
public class ZafCom85_01 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;                               //Linea.
    
    private static final int INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD = 1;            //Código de Empresa de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CODLOC_ORDDESPEDOTRBOD = 2;            //Código de Local de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CODTIPDOC_ORDDESPEDOTRBOD = 3;         //Código de Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_DESCORTIPDOC_ORDDESPEDOTRBOD = 4;      //Descripción Corta Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD = 5;      //Descripción Larga Tipo Documento de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CODDOC_ORDDESPEDOTRBOD = 6;            //Código de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_NUMDOC_ORDDESPEDOTRBOD = 7;            //Número de Ordenes de Despacho (Pedidos a Otras Bodegas).
    
    private static final int INT_TBL_DAT_CODREGITM = 8;                         //Código de registro del ítem en la Orden de Despacho.
    private static final int INT_TBL_DAT_CODEMPCODITM = 9;                      //Código de la empresa del código del ítem.
    private static final int INT_TBL_DAT_CODITM = 10;                           //Código del ítem.
    private static final int INT_TBL_DAT_CODALTITM = 11;                        //Código Alterno del ítem.
    private static final int INT_TBL_DAT_CODALTDOSITM = 12;                     //Código Alterno 2 del ítem.
    private static final int INT_TBL_DAT_NOMITM = 13;                           //Nombre del ítem.
    private static final int INT_TBL_DAT_DESCOR_UNIMED = 14;                    //Descripción corta de la unidad de medida.
    
    private static final int INT_TBL_DAT_CANITM = 15;                           //Cantidad Solicitada.
    private static final int INT_TBL_DAT_CANPENEGRBOD = 16;                     //Cantidad Pendiente de Egresar de Bodega.
    private static final int INT_TBL_DAT_CANPENEGRDES = 17;                     //Cantidad Pendiente de Egresar de Despacho.
    private static final int INT_TBL_DAT_CANTRA = 18;                           //Cantidad en Tránsito.
    private static final int INT_TBL_DAT_CANREV = 19;                           //Cantidad en Revisión.
    private static final int INT_TBL_DAT_CANPEN = 20;                           //Cantidad Pendiente.
    private static final int INT_TBL_DAT_CANCON = 21;                           //Cantidad Pendiente.
   
    private static final int INT_TBL_DAT_BUT_ORDDESPEDOTRBOD = 22;              //Botón "Ordenes de Despacho (Pedidos a Otras Bodegas)".
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;    
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblFilCab objTblFilCab;
    private ZafThreadGUI objThrGUI;
    private ZafTblMod objTblModDat;                                             //Modelo de Jtable.
    private ZafMouMotAda objMouMotAdaDat;                                       //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private Vector vecDat, vecCab, vecReg;
    private String strSQL, strAux;
    
    private String strCodSegSolTraInv="";
    private String strCodEmpSolTraInv="";
    private String strCodLocSolTraInv="";
    private String strCodTipDocSolTraInv="";
    private String strCodDocSolTraInv="";
    
    private String strVersion="v0.1";                                           //Versión del Programa.  
    
    //Constructor
    public ZafCom85_01(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm()) 
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    //Ventana de Consulta
    public ZafCom85_01(ZafParSis objZafParSis, /*String strCodSeg,*/ String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try
        {
            //Inicializar objetos.
            this.objParSis = (ZafParSis) objZafParSis.clone();
            
            /*strCodSegSolTraInv=strCodSeg;*/
            strCodEmpSolTraInv=strCodEmp;
            strCodLocSolTraInv=strCodLoc;
            strCodTipDocSolTraInv=strCodTipDoc;
            strCodDocSolTraInv=strCodDoc;
            
            initComponents();
            if (!configurarFrm()) 
                exitForm();
            
            consultar();
            
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
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText("Detalle de ítems ");
            
            //Configurar Jtable
            configurarTblDat();
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
            vecCab = new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_DAT_LIN,"");
            
            vecCab.add(INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_ORDDESPEDOTRBOD,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC_ORDDESPEDOTRBOD,"Cód.Tip.Doc.");  
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC_ORDDESPEDOTRBOD,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC_ORDDESPEDOTRBOD,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC_ORDDESPEDOTRBOD,"Núm.Ord.");
           
            vecCab.add(INT_TBL_DAT_CODREGITM,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_CODEMPCODITM,"Cód.Emp.Itm.");
            vecCab.add(INT_TBL_DAT_CODITM,"Cód.Itm.");  
            vecCab.add(INT_TBL_DAT_CODALTITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_CODALTDOSITM,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOMITM,"Ítem");
            vecCab.add(INT_TBL_DAT_DESCOR_UNIMED,"Uni.Med.");
            
            vecCab.add(INT_TBL_DAT_CANITM,"Cantidad");
            vecCab.add(INT_TBL_DAT_CANPENEGRBOD,"Can.Pen.Egr.Bod.");
            vecCab.add(INT_TBL_DAT_CANPENEGRDES,"Can.Pen.Egr.Des.");  
            vecCab.add(INT_TBL_DAT_CANTRA,"Can.Tra.");
            vecCab.add(INT_TBL_DAT_CANREV,"Can.Rev.");
            vecCab.add(INT_TBL_DAT_CANPEN,"Can.Pen.");
            vecCab.add(INT_TBL_DAT_CANCON,"Can.Con.");
            
            vecCab.add(INT_TBL_DAT_BUT_ORDDESPEDOTRBOD,"");
             
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
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_ORDDESPEDOTRBOD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_ORDDESPEDOTRBOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_ORDDESPEDOTRBOD).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC_ORDDESPEDOTRBOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC_ORDDESPEDOTRBOD).setPreferredWidth(80);
            
            tcmAux.getColumn(INT_TBL_DAT_CODREGITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODEMPCODITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODALTITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOSITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMITM).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_DESCOR_UNIMED).setPreferredWidth(40);
            
            tcmAux.getColumn(INT_TBL_DAT_CANITM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CANPENEGRBOD).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CANPENEGRDES).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CANTRA).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CANREV).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CANPEN).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CANCON).setPreferredWidth(65);
            
            tcmAux.getColumn(INT_TBL_DAT_BUT_ORDDESPEDOTRBOD).setPreferredWidth(20);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_ORDDESPEDOTRBOD, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC_ORDDESPEDOTRBOD, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOC_ORDDESPEDOTRBOD, tblDat);
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODREGITM, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMPCODITM, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODITM, tblDat);
            //objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CANPEN, tblDat);
           
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
            
             //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ORDDESPEDOTRBOD);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable:  Botones.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ORDDESPEDOTRBOD).setCellRenderer(objTblCelRenBut);
     
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ORDDESPEDOTRBOD:
                            if (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD).toString().equals("")) 
                                objTblCelRenBut.setText("");
                            else
                             objTblCelRenBut.setText("...");
                        break;
                    }
                }
            });
            
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ORDDESPEDOTRBOD).setCellEditor(objTblCelEdiButGen);

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
                            case INT_TBL_DAT_BUT_ORDDESPEDOTRBOD:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD).toString().equals("")))
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
                            case INT_TBL_DAT_BUT_ORDDESPEDOTRBOD:
                                cargarVentanaOrdDesPedOtrBod(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD).toString(), 
                                                             tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_ORDDESPEDOTRBOD).toString(),
                                                             tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC_ORDDESPEDOTRBOD).toString(), 
                                                             tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC_ORDDESPEDOTRBOD).toString() );
                                break;
                        }
                    }
                }
            });
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CANITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CANPENEGRBOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CANPENEGRDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CANTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CANREV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CANPEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CANCON).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOSITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
                        
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
                case INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD:
                    strMsg="Código de la empresa";
                    break;                
                case INT_TBL_DAT_CODLOC_ORDDESPEDOTRBOD:
                    strMsg="Código del local";
                    break;        
                case INT_TBL_DAT_CODTIPDOC_ORDDESPEDOTRBOD:
                    strMsg="Código del tipo de documento";
                    break;        
                case INT_TBL_DAT_DESCORTIPDOC_ORDDESPEDOTRBOD:
                    strMsg="Descripción corta del tipo de documento";
                    break;   
                case INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD:
                    strMsg="Descripción larga del tipo de documento";
                    break;                    
                case INT_TBL_DAT_CODDOC_ORDDESPEDOTRBOD:
                    strMsg="Código del documento";
                    break;                
                case INT_TBL_DAT_NUMDOC_ORDDESPEDOTRBOD:
                    strMsg="Número de la orden de despacho";
                    break;   
                case INT_TBL_DAT_CODREGITM:
                    strMsg="Código de registro del ítem";
                    break;
                case INT_TBL_DAT_CODEMPCODITM:
                    strMsg="Código de Empresa del código del ítem";
                    break;
                case INT_TBL_DAT_CODITM:
                    strMsg="Código del ítem";
                    break;
                case INT_TBL_DAT_CODALTITM:
                    strMsg="Código Alterno del ítem";
                    break;
                case INT_TBL_DAT_CODALTDOSITM:
                    strMsg="Código Alterno 2 del ítem";
                    break;
                case INT_TBL_DAT_NOMITM:
                    strMsg="Nombre del ítem";
                    break;
                case INT_TBL_DAT_DESCOR_UNIMED:
                    strMsg="Descripción corta de la unidad de medida";
                    break;
                case INT_TBL_DAT_CANITM:
                    strMsg="Cantidad Solicitada";
                    break;
                case INT_TBL_DAT_CANPENEGRBOD:
                    strMsg="Cantidad Pendiente de Egresar en Bodega.";
                    break;
                case INT_TBL_DAT_CANPENEGRDES:
                    strMsg="Cantidad Pendiente de Egresar en Despacho.";
                    break;
                case INT_TBL_DAT_CANTRA:
                    strMsg="Cantidad en Tránsito";
                    break;
                case INT_TBL_DAT_CANREV:
                    strMsg="Cantidad en Revisión";
                    break;
                 case INT_TBL_DAT_CANPEN:
                    strMsg="Cantidad Pendiente";
                    break;
                 case INT_TBL_DAT_CANCON:
                    strMsg="Cantidad Total Confirmada";
                    break;        
                case INT_TBL_DAT_BUT_ORDDESPEDOTRBOD:
                    strMsg="Muestra la orden de despacho (Pedidos a otras bodegas)";
                    break;   
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="/* Botón Orden de Despacho (Pedidos a Otras Bodegas) */">
    private void cargarVentanaOrdDesPedOtrBod(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        int intCodMnu=3497;
        //Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();        
    }
    //</editor-fold>
    
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
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()
        {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

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

        tabFrm.addTab("Datos", panRpt);

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
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(430, 180, 400, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        consultar();
    }//GEN-LAST:event_butConActionPerformed

    /*Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").*/
    private void consultar() 
    {
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
    }
    
    
    /* Cerrar la aplicación */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
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
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
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
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();      
                
                //Armar la sentencia SQL OrdDesPedOtrBod.
                strSQL ="";
                strSQL+=" SELECT OrdDes.co_Seg, OrdDes.CodEmpOrdDes, OrdDes.CodLocOrdDes, OrdDes.CodTipDocOrdDes, OrdDes.CodDocOrdDes, \n";        
                strSQL+="        OrdDes.tx_desCor, OrdDes.tx_DesLar, OrdDes.NumOrdDes, \n";
                strSQL+="        MovInv.CodRegMovInv , MovInv.CodItmEmpOrdDes, MovInv.tx_codAlt , MovInv.tx_codAlt2 , MovInv.tx_nomItm , MovInv.tx_uniMed, \n";
                strSQL+="        MovInv.Can, MovInv.CanPenEgrBod, MovInv.CanPenEgrDes , MovInv.CanTra , MovInv.CanRev , MovInv.CanPen,  MovInv.CanCon  \n";     
                strSQL+=" FROM \n";  
                strSQL+=" ( \n";   
                strSQL+="     SELECT a2.co_Seg, a3.co_emp as CodEmpMovInv, a3.co_loc as CodLocMovInv, a3.co_tipDoc as CodTipDocMovInv, a3.co_doc as CodDocMovInv, \n";           
                strSQL+="            a1.co_Emp as CodEmpSolTraInv, a1.co_loc as CodLocSolTraInv, a1.co_tipDoc as CodTipDocSolTraInv, a1.co_doc as CodDocSolTraInv, \n"; 
                strSQL+="            a4.co_Reg as CodRegMovInv, a4.co_itm as CodItmEmpOrdDes, a4.tx_codAlt, a4.tx_codAlt2, a4.tx_nomItm, a4.tx_uniMed, ABS( a4.nd_can) as Can, \n";
                strSQL+="            CASE WHEN a4.nd_CanEgrBod IS NULL THEN 0 ELSE  ABS(a4.nd_CanEgrBod) END as CanPenEgrBod, \n"; 
                strSQL+="            CASE WHEN a4.nd_CanDesEntCli IS NULL THEN 0 ELSE ABS(a4.nd_CanDesEntCli) END as CanPenEgrDes, \n";       
                strSQL+="            CASE WHEN a4.nd_canTra IS NULL THEN 0 ELSE ABS(a4.nd_canTra) END as CanTra, \n";
                strSQL+="            CASE WHEN a4.nd_CanRev IS NULL THEN 0 ELSE ABS(a4.nd_CanRev) END as CanRev, \n";
                strSQL+="            CASE WHEN a4.nd_CanPen IS NULL THEN 0 ELSE ABS(a4.nd_CanPen) END as CanPen,\n"; 
                strSQL+="            ABS(a4.nd_CanCon) as CanCon \n";             
                strSQL+="     FROM tbm_cabSolTraInv as a1 \n";   
                strSQL+="     INNER JOIN tbm_CabSegMovInv as a2 ON (a2.co_empRelCabSolTraInv=a1.co_emp AND a2.co_LocRelCabSolTraInv=a1.co_loc AND a2.co_tipDocRelCabSolTraInv=a1.co_tipDoc \n";
                strSQL+="     AND a2.co_DocRelCabSolTraInv=a1.co_doc)  \n";  
                strSQL+="     INNER JOIN tbm_CabMovInv as a3 ON (a3.co_empRelCabSolTraInv=a1.co_emp AND a3.co_locRelCabSolTraInv=a1.co_loc AND a3.co_tipDocRelCabSolTraInv=a1.co_tipDoc \n";
                strSQL+="     AND a3.co_docRelCabSolTraInv=a1.co_doc)  \n";
                strSQL+="     INNER JOIN tbm_detMovInv as a4 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc) \n"; 
                strSQL+="     WHERE a4.nd_can<0 \n";
                strSQL+="     GROUP BY a2.co_Seg, a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a1.co_Emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, \n";
                strSQL+="              a4.co_Reg, a4.co_itm, a4.tx_codAlt, a4.tx_codAlt2, a4.tx_nomItm, a4.tx_uniMed,  a4.nd_can, \n";
                strSQL+="              a4.nd_CanEgrBod, a4.nd_CanDesEntCli,  a4.nd_canTra, a4.nd_CanRev, a4.nd_CanPen, a4.nd_CanCon \n";
                strSQL+=" ) as MovInv  \n";
                strSQL+=" INNER JOIN   \n";
                strSQL+=" (    \n";
                strSQL+="    SELECT a3.co_Seg, a1.co_emp as CodEmpOrdDes, a1.co_loc as CodLocOrdDes, a1.co_tipDoc as CodTipDocOrdDes, a1.co_doc as CodDocOrdDes,  \n";         
                strSQL+="           a1.ne_numOrdDes as NumOrdDes, a4.tx_DesCor, a4.tx_desLar, a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel \n";   
                strSQL+="    FROM tbm_CabGuiRem as a1 \n";    
                strSQL+="    INNER JOIN tbm_detGuiRem as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";   
                strSQL+="    INNER JOIN tbm_CabSegMovInv as a3 \n";
                strSQL+="    ON (a3.co_empRelCabGuiRem=a1.co_Emp AND a3.co_locRelCabGuiRem=a1.co_loc AND a3.co_tipDocRelCabGuiRem=a1.co_tipDoc AND a3.co_docRelCabGuiRem=a1.co_Doc) \n";
                strSQL+="    INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n"; 
                strSQL+=" ) as OrdDes \n";
                strSQL+=" ON (OrdDes.co_Seg=MovInv.co_Seg AND OrdDes.co_empRel= MovInv.CodEmpMovInv AND OrdDes.co_locRel=MovInv.CodLocMovInv AND OrdDes.co_tipDocRel=MovInv.CodTipDocMovInv \n";
                strSQL+=" AND OrdDes.co_docRel=MovInv.CodDocMovInv)  \n";
                strSQL+=" WHERE MovInv.CodEmpSolTraInv="+strCodEmpSolTraInv;  
                strSQL+=" AND MovInv.CodLocSolTraInv="+strCodLocSolTraInv; 
                strSQL+=" AND MovInv.CodTipDocSolTraInv="+strCodTipDocSolTraInv; 
                strSQL+=" AND MovInv.CodDocSolTraInv="+strCodDocSolTraInv; 
                strSQL+=" GROUP BY OrdDes.co_Seg, OrdDes.CodEmpOrdDes, OrdDes.CodLocOrdDes, OrdDes.CodTipDocOrdDes, OrdDes.CodDocOrdDes, \n";        
                strSQL+="          OrdDes.tx_desCor, OrdDes.tx_DesLar, OrdDes.NumOrdDes, \n";
                strSQL+="          MovInv.CodRegMovInv, MovInv.CodItmEmpOrdDes, MovInv.tx_codAlt , MovInv.tx_codAlt2 , MovInv.tx_nomItm , MovInv.tx_uniMed, \n"; 
                strSQL+="          MovInv.Can, MovInv.CanPenEgrBod, MovInv.CanPenEgrDes , MovInv.CanTra , MovInv.CanRev , MovInv.CanCon, MovInv.CanPen \n";
                strSQL+=" ORDER BY OrdDes.NumOrdDes, MovInv.CodRegMovInv \n";
                
                System.out.println("cargarDetReg.ZafCom85_01.OrdDes: "+strSQL);   
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");

                    vecReg.add(INT_TBL_DAT_CODEMP_ORDDESPEDOTRBOD, rst.getString("CodEmpOrdDes"));
                    vecReg.add(INT_TBL_DAT_CODLOC_ORDDESPEDOTRBOD, rst.getString("CodLocOrdDes"));
                    vecReg.add(INT_TBL_DAT_CODTIPDOC_ORDDESPEDOTRBOD, rst.getString("CodTipDocOrdDes"));
                    vecReg.add(INT_TBL_DAT_DESCORTIPDOC_ORDDESPEDOTRBOD, rst.getString("tx_DesCor"));
                    vecReg.add(INT_TBL_DAT_DESLARTIPDOC_ORDDESPEDOTRBOD, rst.getString("tx_DesLar"));
                    vecReg.add(INT_TBL_DAT_CODDOC_ORDDESPEDOTRBOD, rst.getString("CodDocOrdDes"));
                    vecReg.add(INT_TBL_DAT_NUMDOC_ORDDESPEDOTRBOD, rst.getString("NumOrdDes"));

                    vecReg.add(INT_TBL_DAT_CODREGITM,rst.getString("CodRegMovInv"));
                    vecReg.add(INT_TBL_DAT_CODEMPCODITM,rst.getString("CodEmpOrdDes"));
                    vecReg.add(INT_TBL_DAT_CODITM,rst.getString("CodItmEmpOrdDes"));
                    vecReg.add(INT_TBL_DAT_CODALTITM,rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_CODALTDOSITM,rst.getString("tx_codAlt2"));                        
                    vecReg.add(INT_TBL_DAT_NOMITM,rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_DESCOR_UNIMED,rst.getString("tx_uniMed"));

                    vecReg.add(INT_TBL_DAT_CANITM,rst.getString("Can"));
                    vecReg.add(INT_TBL_DAT_CANPENEGRBOD,rst.getString("CanPenEgrBod"));
                    vecReg.add(INT_TBL_DAT_CANPENEGRDES,rst.getString("CanPenEgrDes"));
                    vecReg.add(INT_TBL_DAT_CANTRA,rst.getString("CanTra"));
                    vecReg.add(INT_TBL_DAT_CANREV,rst.getString("CanRev"));
                    vecReg.add(INT_TBL_DAT_CANPEN,rst.getString("CanPen"));
                    vecReg.add(INT_TBL_DAT_CANCON,rst.getString("CanCon"));
                    vecReg.add(INT_TBL_DAT_BUT_ORDDESPEDOTRBOD,"");

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
